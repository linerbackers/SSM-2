package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

@Controller
@RequestMapping("/pic")
public class PictureServiceController {
	@Autowired
	PictureService pictureService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public String uploadPicture(MultipartFile uploadFile){
		
		Map<?, ?> resultMap = pictureService.uploadPicture(uploadFile);
		//为了保证功能的兼容性，需要把Result转换成json格式的字符串。 否则只能在谷歌浏览器能使用
		String objectToJson = JsonUtils.objectToJson(resultMap);
		return objectToJson;
	}
}
