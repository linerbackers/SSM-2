package com.taotao.protal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbUser;
import com.taotao.protal.dao.CartItem;
import com.taotao.protal.pojo.Order;
import com.taotao.protal.service.CartService;
import com.taotao.protal.service.OrderService;
/**
 * ¶©µ¥
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request,HttpServletResponse
			response,Model model){
		List<CartItem> getcartItemList = cartService.getcartItemList(request, response);
		model.addAttribute("cartList", getcartItemList);
		return "/order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrderCart(Order order,Model model,HttpServletRequest request){
		TbUser user=(TbUser) request.getAttribute("user");
		order.setBuyerNick(user.getUsername());
		order.setUserId(user.getId());
		String createOrder = orderService.createOrder(order);
		model.addAttribute("orderId", createOrder);
		model.addAttribute("payment", order.getPayment());
		model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "success";
	}
}
