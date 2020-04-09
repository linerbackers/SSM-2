package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

//@Repository、@Service、@Controller，它们分别对应存储层Bean，业务层Bean，和展示层Bean
//@Repository用于标注数据访问组件，即DAO组件
@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//返回值对象
		SearchResult searchResult=new SearchResult();
		//根据索引条件查询数据库
		QueryResponse query2 = solrServer.query(query);
		//获取查询结果
		SolrDocumentList results = query2.getResults();
		//取查询结果总数量
		searchResult.setRecordCount(results.getNumFound());
		//商品列表
		List<Item> itemList=new ArrayList<>();
		//取高亮显示
		Map<String,Map<String,List<String>>> hightlighting=query2.getHighlighting();
		//取商品列表
		for(SolrDocument solrDocument:results){
			Item item = new Item();
			item.setId((String) solrDocument.get("id"));
			//取高亮显示的结果
			List<String> list = hightlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size()>0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			//添加的商品列表
			itemList.add(item);
		}
		searchResult.setItemList(itemList);
		return searchResult;
	}
}
