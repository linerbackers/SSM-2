package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	public EUDataGridResult contentQuery(long categoryId,int page,int rows);
	
	public TaotaoResult contentSave(TbContent tbcontent);
	
}
