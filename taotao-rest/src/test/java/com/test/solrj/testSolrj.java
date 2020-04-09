package com.test.solrj;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class testSolrj {

	@Test
	public void test() {
		//创建服务
		SolrServer server=new HttpSolrServer("http://10.0.0.211:8080/solr");
		//创建document文档
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title","测试商品1");
		document.addField("item_price", "10000");
		try {
			server.add(document);
			server.commit();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
		SolrServer server=new HttpSolrServer("http://10.0.0.211:8080/solr");
//		server.deleteById(ids)
		try {
			server.deleteByQuery("*:*");
			server.commit();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3()throws Exception{
		SolrServer server=new HttpSolrServer("http://10.0.0.211:8080/solr");
		//创建一个查询对象
		SolrQuery query=new SolrQuery();
		//设置查询条件
		query.setQuery("*:*");
		query.setStart(10);
		query.setRows(100);
		//执行查询
		QueryResponse query2 = server.query(query);
		//取查询结果
		SolrDocumentList results = query2.getResults();
		System.out.println("共获取查询记录"+results.getNumFound()+"条");
		for(SolrDocument solrDocument:results){
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
		}
	}
}
