package com.taotao.protal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.protal.dao.SearchResult;
import com.taotao.protal.dao.TbItemInfo;
import com.taotao.protal.service.SearchService;



@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,
			@RequestParam(defaultValue="1")int page,Model model){
		SearchResult search=null;
		try {
			queryString=new String(queryString.getBytes("iso8859-1"), "utf-8");
			search = searchService.search(queryString, page);
			long totalPages = search.getPageCount();
			model.addAttribute("query", queryString);
			model.addAttribute("page", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("itemList", search.getItemList());
			return "search";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "search";
	}
	
	/**
	 * 接收页面传递过来的商品id，
	 * 调用Service查询商品基本信息。传递给jsp页面。返回逻辑视图，展示商品详情页面。
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	public String getItemById(@PathVariable long itemId,Model model){
		TbItemInfo itemById;
		try {
			itemById =  searchService.getItemById(itemId);
			model.addAttribute("item", itemById);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "item";
	}
	
	/**
	 * 接收商品id，调用Service查询商品的描述信息，返回一个字符串，是商品描述的片段。需要使用@ResponseBody。
	 */
	
	@RequestMapping(value="/item/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId) {
		String string=null;
		try {
			string = searchService.getItemDescById(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}
	
	/**
	 * 页面的ajax请求Controller，请求的url://item/param/{itemId}.html
响应一个字符串。规格参数的片段。@ResponseBody。
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId) {
		String string = searchService.getItemParam(itemId);
		return string;
	}
}
