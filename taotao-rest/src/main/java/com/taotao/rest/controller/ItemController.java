package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	//������Ʒid����Service��ѯ��Ʒ��Ϣ��������Ʒ����ʹ��TaotaoResult��װ��
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBaseInfo(@PathVariable long itemId){
		TaotaoResult itemBaseInfo = itemService.getItemBaseInfo(itemId);
		return itemBaseInfo;
	}
	
	//������Ʒid����������Service��ѯ��Ʒ����������TaotaoResult.
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable long itemId){
		TaotaoResult itemDesc = itemService.getItemDesc(itemId);
		return itemDesc;
	}
	
	//������Ʒid����Service����TaotaoResult��
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable long itemId){
		TaotaoResult itemParam=itemService.getItemParam(itemId);
		return itemParam;
	}
	
}
