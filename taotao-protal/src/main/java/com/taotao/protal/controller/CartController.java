package com.taotao.protal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.protal.dao.CartItem;
import com.taotao.protal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	/*
	 * ��ӹ��ﳵ��Ʒ
	 */
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable long itemId,@RequestParam(defaultValue="1") Integer num,
			HttpServletRequest request,HttpServletResponse response){
			cartService.addCartItem(itemId, num, request, response);
			return "cartSuccess";
	}
	
	/**
	 * ��cookie�аѹ��ﳵ�б�ȡ������û�в��������ع��ﳵ�е���Ʒ�б�
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> getcartItemList = cartService.getcartItemList(request, response);
		model.addAttribute("cartList", getcartItemList);
		return "cart";
	}
	/**
	 * ֱ�������޸���Ʒ����
	 * @param itemId
	 * @param num
	 * @return
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	public String updateCount(@PathVariable long itemId,@PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response,Model model){
		List<CartItem> getcartItemList = cartService.updatecartItemCount(request,response, itemId, num);
		model.addAttribute("cartList", getcartItemList);
		return "cart";
	}
	
	@RequestMapping("/delete/{cartId}")
	public String deleteCartItem(@PathVariable long cartId,Model model,HttpServletRequest request, HttpServletResponse response){
		List<CartItem> getcartItemList = cartService.deleteCartItem(request, response, cartId);
		model.addAttribute("cartList", getcartItemList);
		return "cart";
	}
}
