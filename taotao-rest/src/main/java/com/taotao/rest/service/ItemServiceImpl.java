package com.taotao.rest.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.TbItemInfo;
/**
 * REDIS_ITEM_KEY + ":" + itemId + ":base"
 * 此种命名方式，是redis官网推荐的方式，
 * 存储方式相当于：REDIS_ITEM_KEY（相当于库），itemId（相当于表），base（相当于字段），找到对应的值，
 * 因为hset无法设置有效期。
 * @author Administrator
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private int REDIS_ITEM_EXPIRE;
	@Autowired
	private JedisClient jedisClient;
	//接收商品id，根据商品id查询商品基本信息。返回一个商品的pojo，使用taotaoResult包装返回。
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		//查询缓存
		try {
			String string = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			if (StringUtils.isNotBlank(string)) {
				TbItem jsonToPojo = JsonUtils.jsonToPojo(string, TbItem.class);
				return TaotaoResult.ok(jsonToPojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		
		//增加缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(tbItem));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(tbItem);
	}

	
	//接收商品id根据商品id查询商品描述。返回商品描述的pojo。使用TaotaoResult包装。需要添加缓存逻辑
	public TaotaoResult getItemDesc(long itemId){
		//查询缓存
		try {
			String string = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			if(StringUtils.isNotBlank(string)){
				TbItemDesc jsonToPojo = JsonUtils.jsonToPojo(string, TbItemDesc.class);
				return TaotaoResult.ok(jsonToPojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		
		//放入缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(tbItemDesc));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(tbItemDesc);
	}
	
	//需要从tb_item_param_item表中根据商品id取出商品的规格参数信息。返回pojo对象，使用TaotaoResult包装。
	public TaotaoResult getItemParam(long itemId){
		//查询缓存
		try {
			String string = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			if(StringUtils.isNotBlank(string)){
				TbItemParamItem jsonToPojo = JsonUtils.jsonToPojo(string, TbItemParamItem.class);
				return TaotaoResult.ok(jsonToPojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemParamItem tbItemParamItem = tbItemParamItemMapper.selectByPrimaryKey(itemId);
		
		//添加缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(tbItemParamItem));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(tbItemParamItem);
	}
}
