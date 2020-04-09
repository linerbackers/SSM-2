package com.taotao.rest.pojo;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 首页左列表商品分类展示的节点
 * @author Administrator
 *
 */
public class CatNode {

	//用 n 取代 name
	@JsonProperty("n")
	private String name;
	
	@JsonProperty("u")
	private String url;
	
	@SuppressWarnings("rawtypes")
	@JsonProperty("i")
	private List item;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@SuppressWarnings("rawtypes")
	public List getItem() {
		return item;
	}

	@SuppressWarnings("rawtypes")
	public void setItem(List list) {
		this.item = list;
	}
	
	
}
