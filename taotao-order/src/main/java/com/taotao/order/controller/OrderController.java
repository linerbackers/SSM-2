package com.taotao.order.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;


@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@RequestMapping(value="/create")
	@ResponseBody
	public TaotaoResult CreateOrder(@RequestBody Order order){
		//根据接口要求，传入的参数是一个json形式的字符串
		//request发送过来键值对形式的字符串需要通过requestBody转换成order对象。
		
		//response只能接受字符串形式，我们返回对象的话，它需要把对象解析成字符串，所以我们用到jackson这个包
		//把对象转换成json字符串的形式。
		TaotaoResult createOrder = orderService.createOrder(order, order.getOrderItems(),order.getOrderShipping());
		return createOrder;
	}
	
	
}
