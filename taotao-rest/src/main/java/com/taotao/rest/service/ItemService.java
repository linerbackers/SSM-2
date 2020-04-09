package com.taotao.rest.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

public interface ItemService {

	public TaotaoResult getItemBaseInfo(long itemId);
	public TaotaoResult getItemDesc(long itemId);
	public TaotaoResult getItemParam(long itemId) ;
}
