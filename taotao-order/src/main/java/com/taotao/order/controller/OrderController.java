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
		//���ݽӿ�Ҫ�󣬴���Ĳ�����һ��json��ʽ���ַ���
		//request���͹�����ֵ����ʽ���ַ�����Ҫͨ��requestBodyת����order����
		
		//responseֻ�ܽ����ַ�����ʽ�����Ƿ��ض���Ļ�������Ҫ�Ѷ���������ַ��������������õ�jackson�����
		//�Ѷ���ת����json�ַ�������ʽ��
		TaotaoResult createOrder = orderService.createOrder(order, order.getOrderItems(),order.getOrderShipping());
		return createOrder;
	}
	
	
}
