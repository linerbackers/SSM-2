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
 * ���ܣ�����controller�㴫�ݹ�����ͼƬ���󣬰�ͼƬ�ϴ���ftp����������ͼƬ����һ���µ����֡�����һ����װ�ɹ�ʧ�ܵ���Ϣhashmap ,Ҳ����pojo
        ������MultiPartFile uploadFile
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
			//ȡԭʼ�ļ���
			String originalFilename = multipartFile.getOriginalFilename();
			//��׺��
			String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
			//���ļ���
			String newName = IDUtils.genImageName();
			newName=newName+substring;
			//�ϴ�ͼƬ
			String date = new DateTime().toString("/yyyy/MM/dd");
			boolean result=FtpUtil.uploadFile(host, port, username1, password, basePath, date, newName, multipartFile.getInputStream());
			if(!result){
				re.put("error",1);
				re.put("message", "������Ϣ");
				return re;
			}
			re.put("error", 0);
			re.put("url", ftp_image_url+date+"/"+newName);
			return re;
		} catch (Exception e) {
			re.put("error",1);
			re.put("message", "������Ϣ");
			return re;
		}
	}
}
