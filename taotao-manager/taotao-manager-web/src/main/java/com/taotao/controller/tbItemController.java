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
										//PathVariable ��ʾ��������·���Ĳ��� (url����ע�⣬ һ�����ڴ�url�л�ȡ����)
//	@RequestMapping("/list/{itemId}")     //����itmeId ��@RequestMapping ��itmeId һ�����Ͳ���Ҫָ�� value 
//	public @ResponseBody TbItem selectByPrimaryKey(@PathVariable Long itemId){
//		TbItem selectByPrimaryKey = tbItemService.selectByPrimaryKey(itemId);
//		
//		return selectByPrimaryKey;
//	}
	/**
	 * ��ҳ��¼
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	/**
	 * ҳ���л�
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String page(@PathVariable String page){
		return page;
	}
	
	/**
	 * �б�չʾ ����ҳ�洫�ݹ����Ĳ���page��rows������json��ʽ�����ݡ�EUDataGridResult
	��Ҫʹ�õ�@ResponseBodyע�⡣
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody EUDataGridResult list(int page,int rows){
		return tbItemService.getItemList(page, rows);
	}
	
	/**
	 * չʾ��Ʒ��Ŀ
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
