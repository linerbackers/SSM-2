package com.taotao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;

@Service
public class TbItemService {
	@Autowired
	TbItemMapper tbItemMapper;
	
	@Autowired
	TbItemCatMapper tbItemCatMapper;
	@Autowired
	TbItemDescMapper tbItemDescMapper;
	
	 public TbItem selectByPrimaryKey(Long id){
		 TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		 return tbItem;
	 }
	 
	 /**
	  * 查询商品列表
	  * @param page
	  * @param rows
	  * @return
	  */
	 
	 public EUDataGridResult getItemList(int page,int rows){
		 TbItemExample example=new TbItemExample();
		 //分页设置
		 PageHelper.startPage(page, rows);
		 //查询商品列表
		 List<TbItem> selectByExample = tbItemMapper.selectByExample(example);
		 //设置结果集
		 EUDataGridResult result=new EUDataGridResult();
		 result.setRows(selectByExample);
		 //取出记录总条数
		 PageInfo<TbItem> tbItemtotal=new PageInfo<>(selectByExample);
		 result.setTotal((int) tbItemtotal.getTotal());
		 
		 return result;
	 }

	 /**
	  * 功能：根据parentId查询商品分类列表。
		参数：parentId
		返回值：返回tree所需要的数据结构，是一个节点列表。
		可以创建一个tree node的pojo表示节点的数据，也可以使用map。
		List<TreeNode>
	  * @return
	  */
	public List<TreeNode> selectItemCat(long parentId) {

		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		Criteria createCriteria = tbItemCatExample.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(tbItemCatExample);
		//把分类列表转换成TreeNode列表
		List<TreeNode> tree=new ArrayList<>();
		for(TbItemCat tbItemCat:selectByExample){
		//	TreeNode node=new TreeNode(tbItemCat.getParentId(), tbItemCat.getName(), tbItemCat.getIsParent()?"closed":"open");
			TreeNode node = new TreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			tree.add(node);
		}
		return tree;
		
	}
	
	
	public void saveItemCat(TbItem tbItem,String desc){
		//获得商品id
		long id = IDUtils.genItemId();
		//添加商品信息
		tbItem.setId(id);
		//商品状态，1-正常，2-下架，3-删除
		tbItem.setStatus((byte)1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItemMapper.insert(tbItem);
		
		//添加商品描述
		//创建TbItemDesc对象
				TbItemDesc itemDesc = new TbItemDesc();
				//获得一个商品id
				itemDesc.setItemId(id);
				itemDesc.setItemDesc(desc);
				itemDesc.setCreated(new Date());
				itemDesc.setUpdated(new Date());
				//插入数据
		tbItemDescMapper.insert(itemDesc);
	}
	
	/**
	 * 删除选中的列表商品
	 * @param ids
	 */
	public void delete(Long ids){
		tbItemDescMapper.deleteByPrimaryKey(ids);
		tbItemMapper.deleteByPrimaryKey(ids);
	}
	
	/**
	 * 查询需要修改的商品
	 * @param ids
	 * @return
	 */
	public TbItem query(Long ids){
		TbItem selectByPrimaryKey = tbItemMapper.selectByPrimaryKey(ids);
		return selectByPrimaryKey;
	}
	
	public TbItemDesc querydesc(Long ids){
		TbItemDesc selectByPrimaryKey = tbItemDescMapper.selectByPrimaryKey(ids);
		return selectByPrimaryKey;
	}
}
