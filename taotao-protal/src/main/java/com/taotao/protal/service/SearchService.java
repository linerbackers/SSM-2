package com.taotao.protal.service;

import com.taotao.pojo.TbItem;
import com.taotao.protal.dao.SearchResult;
import com.taotao.protal.dao.TbItemInfo;

public interface SearchService {

	public SearchResult search(String queryString, int page) throws Exception;
	
	public TbItemInfo getItemById(Long itemId) throws Exception;
	
	public String getItemDescById(Long itemId) throws Exception;
	
	public String getItemParam(Long itemId);
	
}
