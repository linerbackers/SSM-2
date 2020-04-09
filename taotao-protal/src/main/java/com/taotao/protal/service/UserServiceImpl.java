package com.taotao.protal.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;
/**
 * 功能：根据token换取用户信息，需要调用sso系统的服务。返回TbUser对象。如果没有就返回null。
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_TOKEN_URL}")
	private String SSO_TOKEN_URL;
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;
	
	@Override
	public TbUser getUserByToken(String token) {
			String doGet = HttpClientUtil.doGet(SSO_BASE_URL+SSO_TOKEN_URL+token);
			if(StringUtils.isNotBlank(doGet)){
				TaotaoResult formatToPojo = TaotaoResult.formatToPojo(doGet, TbUser.class);
				if(formatToPojo.getStatus()==200){
					TbUser tbUser = (TbUser) formatToPojo.getData();
					return tbUser;
				}
			}
		return null;
	}

}
