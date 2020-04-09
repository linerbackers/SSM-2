package com.taotao.order.dao;

public interface JedisClient {
	String get(String key);
	String set(String key,String value);
	String hget(String hkey,String field);//hashmap ȡֵ��ֵ
	Long hset(String hkey,String field,String value);
	long incr(String key);//������
	Long expire(String key,int second);//����key��Ч��
	long ttl(String key);//�鿴key����Ч��
	Long del(String key);
	long hdel(String hkey,String field);
}
