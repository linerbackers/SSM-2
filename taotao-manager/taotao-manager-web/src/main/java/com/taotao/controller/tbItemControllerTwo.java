package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.TbItemService;

@Controller
@RequestMapping("/rest")
public class tbItemControllerTwo {

	@Autowired
	TbItemService tbItemService;
	
	@RequestMapping("/item/delete")
	@ResponseBody
	public TaotaoResult delete(Long ids){
		tbItemService.delete(ids);
		return TaotaoResult.ok();
	}
	
	@RequestMapping("/item/query/item/desc/{ids}")
	@ResponseBody
	public TbItemDesc querydesc(@PathVariable Long ids){
		TbItemDesc tbItemDesc = tbItemService.querydesc(ids);
		return tbItemDesc;
	}
	
	@RequestMapping("/page/item-edit")
	@ResponseBody
	public TbItem query(Long ids){
		TbItem tbItem = tbItemService.query(ids);
		return tbItem;
	}
}
