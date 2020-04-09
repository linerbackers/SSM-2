package com.taotao.service;

import java.util.List;


import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;

public interface ContentCategoryService {

	public List<EUTreeNode> getCategoryList(long parentId);
	
	public TaotaoResult insertContentCategory(long parentId,String name);
	
	public TaotaoResult deleteContentCatList(long id);
	
	public void updateContentCatList(Long id, String name); 
}
