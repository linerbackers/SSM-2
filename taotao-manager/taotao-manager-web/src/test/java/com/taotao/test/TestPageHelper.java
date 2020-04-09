package com.taotao.test;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {
	
	@Test
	public void testPageHelper(){
		//创建一个spring容器
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext.xml");
		//从spring容器中获得Mapper的代理对象
		TbItemMapper bean = applicationContext.getBean(TbItemMapper.class);
		TbItemExample tbItemExample = new TbItemExample();
		//分页处理
		PageHelper.startPage(2, 10);
		//执行查询，并分页
		List<TbItem> selectByExample = bean.selectByExample(tbItemExample);
		for (TbItem tbItem : selectByExample) {
			System.out.println(tbItem.getTitle());
		}
		
		PageInfo<TbItem> tbItem=new PageInfo<>(selectByExample);
		long total = tbItem.getTotal();
		System.out.println("共有商品："+total+"条");
	}
}
