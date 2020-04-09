package com.test.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisSingle {

	/**
	 * 单机版jedis
	 */
	@Test
	public void test1(){
		Jedis jedis=new Jedis("10.0.0.211",6379);
		jedis.set("1", "one");
		String string = jedis.get("1");
		System.out.println(string);
		jedis.close();
	}
	
	@Test
	public void test2(){
		JedisPool jedisPool=new JedisPool("10.0.0.211", 6379);
		Jedis resource = jedisPool.getResource();
		resource.set("1", "onewww");
		String string = resource.get("1");
		System.out.println(string);
		jedisPool.close();
	}
	
	//集群版
	@Test
	public void test3(){
		HashSet<HostAndPort> node=new HashSet<>();
		node.add(new HostAndPort("10.0.0.211", 7001));
		node.add(new HostAndPort("10.0.0.211", 7002));
		node.add(new HostAndPort("10.0.0.211", 7003));
		node.add(new HostAndPort("10.0.0.211", 7004));
		node.add(new HostAndPort("10.0.0.211", 7005));
		node.add(new HostAndPort("10.0.0.211", 7006));
		JedisCluster jedisCluster=new JedisCluster(node);
		jedisCluster.set("2", "11111111");
		String string = jedisCluster.get("1");
		System.out.println(string);
		jedisCluster.close();
	}
	
	
	//单机测试
	@Test
	public void testSpringJedis(){
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool bean = (JedisPool) classPathXmlApplicationContext.getBean("redisClient");
		Jedis resource = bean.getResource();
		resource.set("20180718", "20180718");
		System.out.println(resource.get("20180718"));
		classPathXmlApplicationContext.close();
	}
	
	//测试集群
	@Test
	public void testSpringCluster(){
		ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
		JedisCluster bean = (JedisCluster) classPathXmlApplicationContext.getBean("jedisCluster");
		bean.set("22121", "xxxxxx");
		System.out.println(bean.get("22121"));
		bean.close();
	}
}
