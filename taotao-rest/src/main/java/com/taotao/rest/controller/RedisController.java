package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.rest.service.RedisService;
/**
 * 后台内容管理同步缓存
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/content/{contentCid}")
	public TaotaoResult contentCacheSync(@PathVariable long contentCid){
		TaotaoResult syncContent = redisService.syncContent(contentCid);
		return syncContent;
	}
}
