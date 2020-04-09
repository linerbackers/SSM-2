package com.taotao.service;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;

/**
 * 功能：接收controller层传递过来的图片对象，把图片上传到ftp服务器。给图片生成一个新的名字。返回一个封装成功失败的消息hashmap ,也可以pojo
        参数：MultiPartFile uploadFile
 * @author Administrator
 *
 */
@Service
public class PictureService {
	@Value("${host}")
	String host;
	@Value("${port}")
	Integer port;
	@Value("${username1}")
	String username1;
	@Value("${password}")
	String password;
	@Value("${basePath}")
	String basePath;
	@Value("${ftp_image_url}")
	String ftp_image_url;
	public Map<Object, Object> uploadPicture(MultipartFile multipartFile){
		Map<Object, Object> re= new HashMap<>();
		try {
			//取原始文件名
			String originalFilename = multipartFile.getOriginalFilename();
			//后缀名
			String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
			//新文件名
			String newName = IDUtils.genImageName();
			newName=newName+substring;
			//上传图片
			String date = new DateTime().toString("/yyyy/MM/dd");
			boolean result=FtpUtil.uploadFile(host, port, username1, password, basePath, date, newName, multipartFile.getInputStream());
			if(!result){
				re.put("error",1);
				re.put("message", "错误消息");
				return re;
			}
			re.put("error", 0);
			re.put("url", ftp_image_url+date+"/"+newName);
			return re;
		} catch (Exception e) {
			re.put("error",1);
			re.put("message", "错误消息");
			return re;
		}
	}
}
