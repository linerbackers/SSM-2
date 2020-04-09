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
		//��������
		SolrServer server=new HttpSolrServer("http://10.0.0.211:8080/solr");
		//����document�ĵ�
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title","������Ʒ1");
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
		//����һ����ѯ����
		SolrQuery query=new SolrQuery();
		//���ò�ѯ����
		query.setQuery("*:*");
		query.setStart(10);
		query.setRows(100);
		//ִ�в�ѯ
		QueryResponse query2 = server.query(query);
		//ȡ��ѯ���
		SolrDocumentList results = query2.getResults();
		System.out.println("����ȡ��ѯ��¼"+results.getNumFound()+"��");
		for(SolrDocument solrDocument:results){
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
		}
	}
}
