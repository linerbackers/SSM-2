package com.taotao.protal.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.protal.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_ORDER}")
	private String ORDER_CREATE_ORDER;
	@Override
	public String createOrder(Order order) {
		//调用taotao-order的服务提交订单。
		String doPostJson = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_ORDER,JsonUtils.objectToJson(order));
		TaotaoResult format = TaotaoResult.format(doPostJson);
		if(format.getStatus()==200){
			Integer orderId = (Integer) format.getData();
			return orderId.toString();
		}
		return null;
	}

}
