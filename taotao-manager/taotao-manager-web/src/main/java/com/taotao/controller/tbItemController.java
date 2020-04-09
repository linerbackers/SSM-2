package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.TbItemService;


@Controller
@RequestMapping("/item")
public class tbItemController {
	
	@Autowired
	TbItemService tbItemService;
										//PathVariable 表示接收请求路径的参数 (url参数注解， 一般用于从url中获取参数)
//	@RequestMapping("/list/{itemId}")     //参数itmeId 与@RequestMapping 中itmeId 一样，就不需要指定 value 
//	public @ResponseBody TbItem selectByPrimaryKey(@PathVariable Long itemId){
//		TbItem selectByPrimaryKey = tbItemService.selectByPrimaryKey(itemId);
//		
//		return selectByPrimaryKey;
//	}
	/**
	 * 首页登录
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	/**
	 * 页面切换
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String page(@PathVariable String page){
		return page;
	}
	
	/**
	 * 列表展示 接收页面传递过来的参数page、rows。返回json格式的数据。EUDataGridResult
	需要使用到@ResponseBody注解。
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody EUDataGridResult list(int page,int rows){
		return tbItemService.getItemList(page, rows);
	}
	
	/**
	 * 展示商品类目
	 * @return
	 */
	@RequestMapping("/cat/list")
	public @ResponseBody List<TreeNode> showItemCat(@RequestParam(value="id",defaultValue="0")long parentId){
		 List<TreeNode> selectItemCat = tbItemService.selectItemCat(parentId);
		 
		return selectItemCat;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveItemCat(TbItem tbItem , String desc){
		tbItemService.saveItemCat(tbItem,desc);
		return TaotaoResult.ok();
	}
}
