package com.taotao.protal.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.protal.dao.CartItem;

@Service
public class CartServiceImpl implements CartService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;
	/**
	 * 功能： 添加购物车商品
	 */
	@Override
	public TaotaoResult addCartItem(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		// 商品信息
		CartItem cartItem = null;
		// 购物车商品列表
		List<CartItem> cartItemList = getCarItemList(request);
		// 判断购物车商品列表中是否存在此商品
		for (CartItem cartitem : cartItemList) {
			if (itemId == cartitem.getId()) {
				cartitem.setNum(cartitem.getNum() + num);
				cartItem = cartitem;
				break;
			}
		}
		if (cartItem == null) {
			cartItem = new CartItem();
			String doGet = HttpClientUtil.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
			TaotaoResult formatToPojo = TaotaoResult.formatToPojo(doGet, TbItem.class);
			if ((formatToPojo.getStatus()).equals(200)) {
				TbItem data = (TbItem) formatToPojo.getData();
				cartItem.setId(data.getId());
				cartItem.setTitle(data.getTitle());
				cartItem.setPrice(data.getPrice());
				cartItem.setNum(num);
				cartItem.setImage(data.getImage() == null ? "" : data.getImage().split(",")[0]);
			}
			cartItemList.add(cartItem);
		}
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList));
		return TaotaoResult.ok();
	}

	/**
	 *  从cookie中取商品列表
	 * @param request
	 * @return
	 */
	public List<CartItem> getCarItemList(HttpServletRequest request){
		String cookieValue = CookieUtils.getCookieValue(request, "TT_CART",true);
		if(StringUtils.isBlank(cookieValue)){
			return new ArrayList<>();
		}
		try {
			List<CartItem> tbItemList = (List<CartItem>) JsonUtils.jsonToList(cookieValue, CartItem.class);
			return tbItemList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public List<CartItem> getcartItemList(HttpServletRequest request,HttpServletResponse response){
		List<CartItem> carItemList = getCarItemList(request);
		return carItemList;
	}

	@Override
	public List<CartItem> updatecartItemCount(HttpServletRequest request,HttpServletResponse response,long itemId,Integer num) {
		List<CartItem> carItemList = getCarItemList(request);
		if(carItemList.size()>0){
			for(CartItem cartItem:carItemList){
				if(itemId==cartItem.getId()){
					cartItem.setNum(num);
					break;
				}
			}
			// 把购物车列表写入cookie
			CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(carItemList));
			return carItemList;
		}
		return null;
	}

	@Override
	public List<CartItem> deleteCartItem(HttpServletRequest request, HttpServletResponse response, long itemId) {
		// 购物车商品列表
		List<CartItem> cartItemList = getCarItemList(request);
		if (cartItemList.size() > 0) {
			// 判断购物车商品列表中是否存在此商品
			for (CartItem cartitem : cartItemList) {
				if (itemId == cartitem.getId()) {
					cartItemList.remove(cartitem);
					break;
				}
			}
		}
		
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList));
		return cartItemList;
	}
}
