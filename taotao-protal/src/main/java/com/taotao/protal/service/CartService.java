package com.taotao.protal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.protal.dao.CartItem;

public interface CartService {

	public TaotaoResult addCartItem(long itemId, Integer num, 
			HttpServletRequest request, HttpServletResponse response);
	
	public List<CartItem> getcartItemList(HttpServletRequest request,HttpServletResponse response);

	public List<CartItem> updatecartItemCount(HttpServletRequest request,HttpServletResponse response,long itemId,Integer num);

	public List<CartItem> deleteCartItem(HttpServletRequest request,HttpServletResponse response,long itemId);
}
