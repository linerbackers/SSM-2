package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	@RequestMapping("content/query/list")
	@ResponseBody
	public EUDataGridResult contentQuery(long categoryId,int page,int rows){
		EUDataGridResult contentQuery = contentService.contentQuery(categoryId, page, rows);
		return contentQuery;
	}
	
	@RequestMapping("/content-add")
	public String toContentAdd(){
		return "content-add";
	}
	
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult contentSave(TbContent tbcontent){
		TaotaoResult result = contentService.contentSave(tbcontent);
		return result;
	}
}
