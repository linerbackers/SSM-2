package com.taotao.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;

@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public EUDataGridResult contentQuery(long categoryId,int page, int rows) {
		TbContentExample example=new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		//分页设置
		PageHelper.startPage(page,rows);
		//查询商品列表
		List<TbContent> selectByExample = tbContentMapper.selectByExample(example);
		//设置结果集
		EUDataGridResult result=new EUDataGridResult();
		result.setRows(selectByExample);
		//取出记录总条数
		PageInfo<TbContent> tbcontenttotal=new PageInfo<>(selectByExample);
		result.setTotal((int)tbcontenttotal.getTotal());
		return result;
	}
/**
 * 同步缓存
 */
	@Override
	public TaotaoResult contentSave(TbContent content) {
		//补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insert(content);
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}


}
