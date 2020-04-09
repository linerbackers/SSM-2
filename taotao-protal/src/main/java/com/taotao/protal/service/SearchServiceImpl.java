package com.taotao.protal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.protal.dao.SearchResult;
import com.taotao.protal.dao.TbItemInfo;

@Service
public class SearchServiceImpl implements SearchService{

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	@Override
	public SearchResult search(String queryString, int page) throws Exception {
		Map<String, String> param=new HashMap<>();
		param.put("q",queryString);
		param.put("p", page+"");
		//调用taotao-search服务
		String jsonData = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		TaotaoResult formatToPojo = TaotaoResult.formatToPojo(jsonData, SearchResult.class);
		SearchResult data=null;
		if(formatToPojo.getStatus().equals(200)){
			data= (SearchResult) formatToPojo.getData();
		}
		return data;
	}
	
	//接收商品id，调用taotao-rest的服务，查询商品的基本信息。
	//得到一个json字符串。需要把json转换成java对象。然后在jsp页面渲染。
	@Override
	public TbItemInfo getItemById(Long itemId) throws Exception {
		String doGet = HttpClientUtil.doGet(REST_BASE_URL+ITME_INFO_URL+itemId);
		TbItemInfo tbItem=null;
		if(StringUtils.isNotBlank(doGet)){
			TaotaoResult formatToPojo = TaotaoResult.formatToPojo(doGet, TbItemInfo.class);
			if(formatToPojo.getStatus().equals(200)){
				 tbItem = (TbItemInfo) formatToPojo.getData();
			}
		}
		return tbItem;
	}
	
	/**
	 * 接收商品id，调用taotao-rest的服务根据商品id查询商品描述信息。
	 * 得到json数据。把json转换成java对象从java对象中把商品描述取出来。返回商品描述字符串。
	 */
	
	public String getItemDescById(Long itemId) throws Exception{
		String doGet = HttpClientUtil.doGet(REST_BASE_URL+ITEM_DESC_URL+itemId);
		TbItemDesc data=null;
		if(StringUtils.isNotBlank(doGet)){
			TaotaoResult formatToPojo = TaotaoResult.formatToPojo(doGet, TbItemDesc.class);
			if(formatToPojo.getStatus().equals(200)){
				 data=  (TbItemDesc) formatToPojo.getData();
				 String itemDesc = data.getItemDesc();
				 return itemDesc;
			}
		}
		return null;
	}
	
	/**
	 * 接收商品id，根据商品id查询规格参数的数据，调用服务端的方法，返回json数据。
	 * 把json转换成java对象，根据java对象生成html片段，返回
	 */
	
	public String getItemParam(Long itemId) {
		String doGet = HttpClientUtil.doGet(REST_BASE_URL+ITEM_PARAM_URL+itemId);
		if(StringUtils.isNotBlank(doGet)){
			TaotaoResult formatToPojo = TaotaoResult.formatToPojo(doGet, TaotaoResult.class);
			if(formatToPojo.getStatus().equals(200)){
				TbItemParamItem data = (TbItemParamItem) formatToPojo.getData();
				String paramData = data.getParamData();
				//生成html
				// 把规格参数json数据转换成java对象
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				//返回html片段
				return sb.toString();
			}
		}
		return "";
	}
}
