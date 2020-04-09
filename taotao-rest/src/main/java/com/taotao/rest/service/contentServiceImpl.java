package com.taotao.rest.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
/**
 * ��ȡ����λͼƬ��Ϣ
 * @author Administrator
 *
 */
@Service
public class contentServiceImpl implements contentService{
	
	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	@Override
	public List<TbContent> getContentList(Long contentId) {
		//�ӻ�����ȡ����
		//ע�⣺��ӻ��棬����ȡ���棬������Ӱ�쵽������ҵ���߼����������ִ�л�������׳��쳣������Ӧ��
		//�����쳣�����߹���Ա����ϵͳ�������ǲ���ִ������Ĵ��롣
		//�����������õ����汾��jedis
		try {
			//�򻺴���ȡ����
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentId+"");
			if(StringUtils.isNotBlank(result)){
				return JsonUtils.jsonToList(result, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example=new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(contentId);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		//�򻺴����������
		try {
			//��listת�����ַ���
			//������������hset��Ƚ����������
			String objectToJson = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, contentId+"", objectToJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
