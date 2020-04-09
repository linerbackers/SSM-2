package com.taotao.protal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.protal.service.UserServiceImpl;

public class LoginInteceptor implements HandlerInterceptor {

	//因为mvc子容器无法访问spring父容器的属性（.properties文件是spring扫描的）
	//所以只能访问service层获取属性
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//在handler执行之前处理、
		//返回决定handler是否执行，如果true,则执行。false,则不执行
		//从cookie中取token
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//根据token换取用户信息，调用sso系统的接口。
		TbUser tbUser = userServiceImpl.getUserByToken(cookieValue);
		if(tbUser==null){
			//跳转到登录页面，把用户请求的url作为参数传递给登录页面。
			response.sendRedirect(userServiceImpl.SSO_BASE_URL+userServiceImpl.SSO_PAGE_LOGIN+
					"?redirect=" + request.getRequestURL());//request.getRequestURL() 返回全路径， http://localhost:8080/jqueryLearn/resources/request.jsp 
			return false;
		}
		//取到用户信息，放行
		//把用户信息放入request请求中（业务需求）
		request.setAttribute("user", tbUser);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//在handler执行之后，返回ModelAndView之前，处理异常用的
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//在返回ModelAndView之后
	}

}
