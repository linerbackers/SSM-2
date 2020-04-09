package com.taotao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.contentService;
/**
 * 获取大广告位图片信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/content")
public class contentController {

	@Autowired
	private contentService contentservice;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public TaotaoResult getContentList(@PathVariable(value="contentCategoryId") Long contentId) {
		try{
			List<TbContent> contentList = contentservice.getContentList(contentId);
			return TaotaoResult.ok(contentList);
		}catch(Exception e){
			e.printStackTrace();
			return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
		}
	}
}
