package com.taotao.rest.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
/**
 * 获取大广告位图片信息
 * @author Administrator
 *
 */
@Service
public class contentServiceImpl implements contentService{
	
	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Override
	public List<TbContent> getContentList(Long contentId) {
		//从缓存中取内容
		//注意：添加缓存，或者取缓存，都不能影响到正常的业务逻辑，即：如果执行缓存操作抛出异常，我们应该
		//捕获异常，告诉管理员或者系统，而不是不能执行下面的代码。
		//在这里我们用单机版本的jedis
		try {
			//向缓存中取内容
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentId+"");
			if(StringUtils.isNotBlank(result)){
				return JsonUtils.jsonToList(result, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example=new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(contentId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		//向缓存中添加内容
		try {
			//把list转换成字符串
			//在这里我们用hset会比较整齐好整理。
			String objectToJson = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentId+"", objectToJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
