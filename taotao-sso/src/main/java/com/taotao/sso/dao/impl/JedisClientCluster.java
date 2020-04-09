package com.taotao.sso.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.sso.dao.JedisClient;

import redis.clients.jedis.JedisCluster;
/**
 * redis集群
 * @author Administrator
 *
 */
public class JedisClientCluster implements JedisClient{

	@Autowired
	private JedisCluster jedisClientCluster;
	//集群不能关闭连接，否则其他节点也不能用
	@Override
	public String get(String key) {
		return jedisClientCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisClientCluster.set(key, value);
	}

	@Override
	public String hget(String hkey, String field) {
		return jedisClientCluster.hget(hkey, field);
	}

	@Override
	public Long hset(String hkey, String field, String value) {
		return jedisClientCluster.hset(hkey, field, value);
	}

	@Override
	public long incr(String key) {
		return jedisClientCluster.incr(key);
	}

	@Override
	public Long expire(String key, int second) {
		return jedisClientCluster.expire(key, second);
	}

	@Override
	public long ttl(String key) {
		return jedisClientCluster.ttl(key);
	}

	@Override
	public Long del(String key) {
		return jedisClientCluster.del(key);
	}

	@Override
	public long hdel(String hkey, String field) {
		return jedisClientCluster.hdel(hkey, field);
	}

	@Override
	public Long expireat(String key, int second) {
		
		return jedisClientCluster.expireAt(key, second);
	}

}
