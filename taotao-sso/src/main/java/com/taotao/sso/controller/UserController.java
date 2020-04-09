package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
/**
 *用户Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	/**
	 * 从url中接收两个参数，调用Service进行校验，
	 * 在调用Service之前，先对参数进行校验，例如type必须是1、2、3其中之一。返回TaotaoResult。需要支持jsonp。
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback){
		TaotaoResult result=null;
		if(StringUtils.isBlank(param)){
			result= TaotaoResult.build(400, "校验内容不能为空");
		}
		if(type!=1&&type!=2&&type!=3){
			result= TaotaoResult.build(400, "校验内容类型不能为空");
		}
		
		//校验错误
		if(result!=null){
			if(StringUtils.isNotBlank(callback)){
				MappingJacksonValue jsonvalue=new MappingJacksonValue(result);
				jsonvalue.setJsonpFunction(callback);
				return jsonvalue;
			}else{
				return result;
			}
		}
		
		result = userService.checkData(param, type);
		
		if(StringUtils.isNotBlank(callback)){
			MappingJacksonValue jsonvalue=new MappingJacksonValue(result);
			jsonvalue.setJsonpFunction(callback);
			return jsonvalue;
		}else{
			return result;
		}
	}
	
	@RequestMapping("/register")
	@ResponseBody
	public TaotaoResult createUser(TbUser user){
			TaotaoResult result = userService.createUser(user);
			return result;
	}
	
	//用户登录
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult userLogin(String username, String password,HttpServletRequest request,HttpServletResponse response) {
		try {
			TaotaoResult result = userService.userLogin(username, password,request,response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping("/token/{token}")
	@ResponseBody
		public Object getUserByToken(@PathVariable String token, String callback) {
		
		TaotaoResult userByToken = userService.getUserByToken(token);
		if(StringUtils.isNotBlank(callback)){
			MappingJacksonValue jsonvalue=new MappingJacksonValue(userByToken);
			jsonvalue.setJsonpFunction(callback);
			return jsonvalue;
		}
		return userByToken;
	}
	
	@RequestMapping("logout/{token}")
	@ResponseBody
	public Object logout(@PathVariable String token,String callback){
		TaotaoResult result=null;
		result= userService.logout(token);
		if(StringUtils.isNotBlank(callback)){
			MappingJacksonValue jsonvalue=new MappingJacksonValue(result);
			jsonvalue.setJsonpFunction(callback);
			return jsonvalue;
		}
		return result;
	}
}
