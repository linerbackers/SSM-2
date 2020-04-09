package com.taotao.protal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.protal.service.ContentService;


@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/index")
	public String index(Model model){
		String contentList = contentService.getContentList();
		model.addAttribute("ad1", contentList);
		return "index";
	}
	
}
