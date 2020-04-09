package com.taotao.search.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
/**
 * 功能：接收查询条件。查询条件及分页条件（page、rows），创建一个SolrQuery对象。
 * 指定查询条件、分页条件、默认搜索域、高亮显示。
 * 调用dao层执行查询。得到查询结果计算总页数。返回SearchResult对象。
 * @author Administrator
 *
 */
@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private SearchDao searchDao;
	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		//创建查询对象
		SolrQuery solrQuery=new SolrQuery();
		//设置查询条件
		solrQuery.setQuery(queryString);
		//设置分页
		solrQuery.setStart((page-1)*rows);
		solrQuery.setRows(rows);
		//设置搜索域
		solrQuery.set("df","item_keywords");
		//设置高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		//执行查询
		SearchResult searchResult = searchDao.search(solrQuery);
		//计算查询结果总页数
		long recordCount = searchResult.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		searchResult.setPageCount(pageCount);
		searchResult.setCurPage(page);
		return searchResult;
	}

}