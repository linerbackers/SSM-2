package com.taotao.rest.pojo;

import java.util.List;
/**
 * ��ҳ���б���Ʒ����չʾ�Ľ����list
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
