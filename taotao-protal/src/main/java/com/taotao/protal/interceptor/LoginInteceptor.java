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

	//��Ϊmvc�������޷�����spring�����������ԣ�.properties�ļ���springɨ��ģ�
	//����ֻ�ܷ���service���ȡ����
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//��handlerִ��֮ǰ����
		//���ؾ���handler�Ƿ�ִ�У����true,��ִ�С�false,��ִ��
		//��cookie��ȡtoken
		String cookieValue = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//����token��ȡ�û���Ϣ������ssoϵͳ�Ľӿڡ�
		TbUser tbUser = userServiceImpl.getUserByToken(cookieValue);
		if(tbUser==null){
			//��ת����¼ҳ�棬���û������url��Ϊ�������ݸ���¼ҳ�档
			response.sendRedirect(userServiceImpl.SSO_BASE_URL+userServiceImpl.SSO_PAGE_LOGIN+
					"?redirect=" + request.getRequestURL());//request.getRequestURL() ����ȫ·���� http://localhost:8080/jqueryLearn/resources/request.jsp 
			return false;
		}
		//ȡ���û���Ϣ������
		//���û���Ϣ����request�����У�ҵ������
		request.setAttribute("user", tbUser);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//��handlerִ��֮�󣬷���ModelAndView֮ǰ�������쳣�õ�
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//�ڷ���ModelAndView֮��
	}

}
