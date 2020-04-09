package com.taotao.order.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.pojo.TbUser;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;
	@Override
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {
		//插入订单号
		//获得订单号
		String string = jedisClient.get(ORDER_GEN_KEY);
		
		//如果没有值，赋一个初始化的值，业务要求
		 if(StringUtils.isBlank(string)){
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		 }
		 //补全order属性
		 long orderId = jedisClient.incr(ORDER_GEN_KEY);
		 order.setOrderId(orderId+"");
		 order.setStatus(1);
		 Date date=new Date();
		 order.setCreateTime(date);
		 order.setUpdateTime(date);
		 order.setBuyerRate(0);
		 tbOrderMapper.insert(order);
		 
		 //插入订单明细
		 for(TbOrderItem tbOrderItem:itemList){
			long incr = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			tbOrderItem.setId(incr+"");
			tbOrderItem.setOrderId(orderId+"");
			 tbOrderItemMapper.insert(tbOrderItem);
		 }
		
		 
		 //插入物流信息
		 orderShipping.setOrderId(orderId+"");
		 orderShipping.setUpdated(date);
		 orderShipping.setCreated(date);
		 tbOrderShippingMapper.insert(orderShipping);
		return TaotaoResult.ok(orderId);
	}

}
