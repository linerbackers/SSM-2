package com.taotao.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.impl.JedisClientSingle;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
/**
 * 首页左列表商品分类展示
 * @author Administrator
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	@Autowired
	private JedisClientSingle jedisClient;
	
	@Override
	public CatResult getItemCatList() {
		CatResult catResult=new CatResult();
		catResult.setData(getItemList(0));
		return catResult;
	}
	
	private List<?> getItemList(long parentId){
		//取缓存
		try {
			String hget = jedisClient.hget(parentId+"", parentId+"");
			if(StringUtils.isNotBlank(hget)){
				List<CatNode> jsonToList = JsonUtils.jsonToList(hget, CatNode.class);
				return jsonToList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemCatExample example=new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(example);
		List resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : selectByExample) {
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName(
							"<a href='/products/'" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				catNode.setItem(getItemList(tbItemCat.getId()));
				resultList.add(catNode);
			} else {
				resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		
		try {
			//添加缓存
			for(int i=0;i<resultList.size();i++){
				if(resultList.get(i) instanceof CatNode){
					String objectToJson = JsonUtils.objectToJson(resultList);
					jedisClient.hset(parentId+"", parentId+"",objectToJson);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

}
