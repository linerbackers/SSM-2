package com.taotao.rest.pojo;

import java.util.List;
/**
 * 首页左列表商品分类展示的结果集list
 * @author Administrator
 *
 */
public class CatResult {

	@SuppressWarnings("rawtypes")
	private List data;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	
}
