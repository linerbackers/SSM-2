package com.taotao.rest.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.dao.impl.JedisClientSingle;
/**
 * 后台内容管理同步缓存
 * @author Administrator
 *
 */
@Service
public class RedisServiceImpl implements RedisService{

	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Override
	public TaotaoResult syncContent(long contentCId) {
		try {
			long hdel = jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCId+"");
		} catch (Exception e) {
			TaotaoResult.build(500, ExceptionUtils.getMessage(e));
		}		
		return TaotaoResult.ok();
	}

}
