package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@ResponseBody
	@RequestMapping("/list")
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0") Long parentId){
		List<EUTreeNode> categoryList = contentCategoryService.getCategoryList(parentId);
		return categoryList;
	}
	
	@ResponseBody
	@RequestMapping("/create")
	public TaotaoResult contentCatListCreate(long parentId, String name){
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/delete/")
	public TaotaoResult deleteContentCatList(long id){
		TaotaoResult result = contentCategoryService.deleteContentCatList(id);
		return result;
	}
	
	@RequestMapping("/update")
	public void updateContentCatList(Long id, String name){
		contentCategoryService.updateContentCatList(id, name);
	}
}
