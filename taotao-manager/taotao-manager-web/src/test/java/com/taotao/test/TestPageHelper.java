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
		//����һ��spring����
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext.xml");
		//��spring�����л��Mapper�Ĵ������
		TbItemMapper bean = applicationContext.getBean(TbItemMapper.class);
		TbItemExample tbItemExample = new TbItemExample();
		//��ҳ����
		PageHelper.startPage(2, 10);
		//ִ�в�ѯ������ҳ
		List<TbItem> selectByExample = bean.selectByExample(tbItemExample);
		for (TbItem tbItem : selectByExample) {
			System.out.println(tbItem.getTitle());
		}
		
		PageInfo<TbItem> tbItem=new PageInfo<>(selectByExample);
		long total = tbItem.getTotal();
		System.out.println("������Ʒ��"+total+"��");
	}
}
