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
		//Ϊ�˱�֤���ܵļ����ԣ���Ҫ��Resultת����json��ʽ���ַ����� ����ֻ���ڹȸ��������ʹ��
		String objectToJson = JsonUtils.objectToJson(resultMap);
		return objectToJson;
	}
}
