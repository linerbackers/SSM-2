package com.taotao.rest.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.rest.dao.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * µ¥»úredis
 * @author Administrator
 *
 */
public class JedisClientSingle implements JedisClient {

	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public String get(String key) {
		Jedis resource = jedisPool.getResource();
		 String string = resource.get(key);
		 resource.close();
		 return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis resource = jedisPool.getResource();
		 String set = resource.set(key,value);
		 resource.close();
		 return set;
	}

	@Override
	public String hget(String hkey, String field) {
		Jedis resource = jedisPool.getResource();
		 String hget =resource.hget(hkey, field);
		 resource.close();
		 return hget;
	}

	@Override
	public Long hset(String hkey, String field, String value) {
		Jedis resource = jedisPool.getResource();
		Long hset = resource.hset(hkey, field, value);
		resource.close();
		return hset;
	}

	@Override
	public long incr(String key) {
		Jedis resource = jedisPool.getResource();
		Long incr = resource.incr(key);
		resource.close();
		return incr;
	}

	@Override
	public Long expire(String key, int second) {
		Jedis resource = jedisPool.getResource();
		 Long expire = resource.expire(key, second);
		 resource.close();
		 return expire;
	}

	@Override
	public long ttl(String key) {
		Jedis resource = jedisPool.getResource();
		 Long ttl = resource.ttl(key);
		 resource.close();
		 return ttl;
	}

	@Override
	public Long del(String key) {
		Jedis resource = jedisPool.getResource();
		Long del = resource.del(key);
		resource.close();
		return del;
	}

	@Override
	public long hdel(String hkey, String field) {
		Jedis resource = jedisPool.getResource();
		Long del = resource.hdel(hkey,field);
		resource.close();
		return del;
	}

}
