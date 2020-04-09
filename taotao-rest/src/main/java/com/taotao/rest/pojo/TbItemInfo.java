package com.taotao.rest.pojo;

import com.taotao.pojo.TbItem;

public class TbItemInfo extends TbItem {

	public String[] getImages() {
		String image = getImage();
		if(image!=null){
			String[] split = image.split(",");
			return split;
		}
		return null;
	}
}
