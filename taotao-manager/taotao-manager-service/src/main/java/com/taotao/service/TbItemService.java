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
	  * ��ѯ��Ʒ�б�
	  * @param page
	  * @param rows
	  * @return
	  */
	 
	 public EUDataGridResult getItemList(int page,int rows){
		 TbItemExample example=new TbItemExample();
		 //��ҳ����
		 PageHelper.startPage(page, rows);
		 //��ѯ��Ʒ�б�
		 List<TbItem> selectByExample = tbItemMapper.selectByExample(example);
		 //���ý����
		 EUDataGridResult result=new EUDataGridResult();
		 result.setRows(selectByExample);
		 //ȡ����¼������
		 PageInfo<TbItem> tbItemtotal=new PageInfo<>(selectByExample);
		 result.setTotal((int) tbItemtotal.getTotal());
		 
		 return result;
	 }

	 /**
	  * ���ܣ�����parentId��ѯ��Ʒ�����б�
		������parentId
		����ֵ������tree����Ҫ�����ݽṹ����һ���ڵ��б�
		���Դ���һ��tree node��pojo��ʾ�ڵ�����ݣ�Ҳ����ʹ��map��
		List<TreeNode>
	  * @return
	  */
	public List<TreeNode> selectItemCat(long parentId) {

		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		Criteria createCriteria = tbItemCatExample.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(tbItemCatExample);
		//�ѷ����б�ת����TreeNode�б�
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
		//�����Ʒid
		long id = IDUtils.genItemId();
		//�����Ʒ��Ϣ
		tbItem.setId(id);
		//��Ʒ״̬��1-������2-�¼ܣ�3-ɾ��
		tbItem.setStatus((byte)1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		tbItemMapper.insert(tbItem);
		
		//�����Ʒ����
		//����TbItemDesc����
				TbItemDesc itemDesc = new TbItemDesc();
				//���һ����Ʒid
				itemDesc.setItemId(id);
				itemDesc.setItemDesc(desc);
				itemDesc.setCreated(new Date());
				itemDesc.setUpdated(new Date());
				//��������
		tbItemDescMapper.insert(itemDesc);
	}
	
	/**
	 * ɾ��ѡ�е��б���Ʒ
	 * @param ids
	 */
	public void delete(Long ids){
		tbItemDescMapper.deleteByPrimaryKey(ids);
		tbItemMapper.deleteByPrimaryKey(ids);
	}
	
	/**
	 * ��ѯ��Ҫ�޸ĵ���Ʒ
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
