package com.taotao.sso.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private int SSO_SESSION_EXPIRE;
	/**
	 * 接收两个参数：内容、内容类型。根据内容类型查询tb_user表返回Taotaoresult对象。
	 * Data属性值：返回数据，true：数据可用，false：数据不可用
	 */
	@Override
	public TaotaoResult checkData(String content, Integer type) {
		
		TbUserExample example=new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		if(1==type){
			createCriteria.andUsernameEqualTo(content);
		}
		if(2==type){
			createCriteria.andPhoneEqualTo(content);
		}
		if(3==type){
			createCriteria.andEmailEqualTo(content);
		}
		List<TbUser> selectByExample = tbUserMapper.selectByExample(example);
		if(selectByExample.size()>0){
			return TaotaoResult.ok(false);
		}
		return TaotaoResult.ok(true);
	}
	@Override
	public TaotaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		try {
			int insert = tbUserMapper.insert(user);
			if(insert!=1){
				return TaotaoResult.build(400, "注册失败");
			}
		} catch (Exception e) {
			return TaotaoResult.build(500, "注册异常");
		}
		return TaotaoResult.build(200,"注册成功");
	}
	
	
	@Override
	public TaotaoResult userLogin(String username, String password,HttpServletRequest request,HttpServletResponse response) {
		TbUserExample example=new TbUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbUser> tbusers = tbUserMapper.selectByExample(example);
		TbUser tbUser = tbusers.get(0);
		//对比密码

		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码错误");
		}
		//先把密码清空掉
		tbUser.setPassword(null);
		//生成token
		String token = UUID.randomUUID().toString();
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(tbUser));
		//设置session的过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		
		//添加写cookie逻辑，cookie的有效期是关闭浏览器就失效
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		//返回token
		return TaotaoResult.ok(token);
	}
	
	@Override
	public TaotaoResult getUserByToken(String token) {
			String string = jedisClient.get(REDIS_USER_SESSION_KEY + ":"+token);
			TbUser tbUser = JsonUtils.jsonToPojo(string, TbUser.class);
			TbUserExample example=new TbUserExample();
			example.createCriteria().andUsernameEqualTo(tbUser.getUsername())
			.andPhoneEqualTo(tbUser.getPhone());
			List<TbUser> selectByExample = tbUserMapper.selectByExample(example);
			if(selectByExample.size()<=0){
				return TaotaoResult.build(400, "用户不对或者密码过期");
			}
			selectByExample.get(0).setPassword(null);
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		return TaotaoResult.build(200,null, JsonUtils.objectToJson(selectByExample.get(0)));
	}

	public TaotaoResult logout(String token){
		String string = jedisClient.get(REDIS_USER_SESSION_KEY+":"+token);
		if(StringUtils.isNotBlank(string)){
			jedisClient.expireat(REDIS_USER_SESSION_KEY+":"+token, 0);
			return TaotaoResult.build(200, "OK");
		}
		return TaotaoResult.build(500, "error");
	}
}
