package com.taotao.rest.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface contentService {

	public List<TbContent> getContentList(Long contentId);
	
}
