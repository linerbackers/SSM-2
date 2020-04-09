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
 * ����������ʽ����redis�����Ƽ��ķ�ʽ��
 * �洢��ʽ�൱�ڣ�REDIS_ITEM_KEY���൱�ڿ⣩��itemId���൱�ڱ���base���൱���ֶΣ����ҵ���Ӧ��ֵ��
 * ��Ϊhset�޷�������Ч�ڡ�
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
	//������Ʒid��������Ʒid��ѯ��Ʒ������Ϣ������һ����Ʒ��pojo��ʹ��taotaoResult��װ���ء�
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		//��ѯ����
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
		
		//���ӻ���
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(tbItem));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(tbItem);
	}

	
	//������Ʒid������Ʒid��ѯ��Ʒ������������Ʒ������pojo��ʹ��TaotaoResult��װ����Ҫ��ӻ����߼�
	public TaotaoResult getItemDesc(long itemId){
		//��ѯ����
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
		
		//���뻺��
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(tbItemDesc));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(tbItemDesc);
	}
	
	//��Ҫ��tb_item_param_item���и�����Ʒidȡ����Ʒ�Ĺ�������Ϣ������pojo����ʹ��TaotaoResult��װ��
	public TaotaoResult getItemParam(long itemId){
		//��ѯ����
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
		
		//��ӻ���
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(tbItemParamItem));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(tbItemParamItem);
	}
}
