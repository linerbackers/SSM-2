package com.taotao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample example=new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<TbContentCategory> selectByExample = tbContentCategoryMapper.selectByExample(example);	
		List<EUTreeNode> list=new ArrayList<>();
		for(TbContentCategory tbContentCategory:selectByExample){
			EUTreeNode tree=new EUTreeNode();
			tree.setId(tbContentCategory.getId());
			tree.setText(tbContentCategory.getName());
			tree.setState(tbContentCategory.getIsParent()?"closed":"open");
			list.add(tree);
		}
		return list;
	}
	
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {

		TbContentCategory tbContentCategory=new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		
		tbContentCategoryMapper.insert(tbContentCategory);
		
		//查看父节点的isParent列是否为true,如果不是true则改成true
		TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()){
			parentCat.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return TaotaoResult.ok(tbContentCategory);
	}

	@Override
	public TaotaoResult deleteContentCatList(long id) {
		
		TbContentCategoryExample example=new TbContentCategoryExample();
		example.createCriteria().andIdEqualTo(id);
		TbContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		TbContentCategoryExample example2=new TbContentCategoryExample();
		example2.createCriteria().andParentIdEqualTo(contentCategory.getParentId());
		tbContentCategoryMapper.deleteByExample(example);
		List<TbContentCategory> selectByExample = tbContentCategoryMapper.selectByExample(example2);
		
		//判断parentId对应的记录下是否有子节点。如果没有子节点，需要把parentId对应的记录的isparent改成false
		 List<TbContentCategory> contentCategoryList = selectByExample;
		if(contentCategoryList.size()==0){
			TbContentCategory content = tbContentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
			content.setIsParent(false);
			tbContentCategoryMapper.updateByPrimaryKey(content);
		}
		return TaotaoResult.ok();
	}

	@Override
	public void updateContentCatList(Long id, String name) {
		TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		tbContentCategory.setName(name);
		tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
	}
	
	

}
