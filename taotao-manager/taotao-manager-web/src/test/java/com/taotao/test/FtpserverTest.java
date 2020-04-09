package com.taotao.test;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FtpserverTest {
	@Test
	public void test() throws Exception{
		//创建一个ftpClient对象
		FTPClient ftpClient=new FTPClient();
		

			//创建ftp连接，默认端口21
			ftpClient.connect("10.0.0.211",21);
			ftpClient.enterLocalActiveMode();    //主动模式
			//登录ftp服务，使用用户名和密码
			ftpClient.login("ftpuser", "ftpuser");
			//读取本地文件
			FileInputStream instream=new FileInputStream(new File("F:\\unti22tled.png"));
			//设置上传到服务器的路径
			ftpClient.changeWorkingDirectory("/home/ftpuser/www/images/");
			//设置上传格式
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			//文件上传
			ftpClient.storeFile("111111.png",instream);
			//关闭路径
			ftpClient.logout();
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testUtils() throws FileNotFoundException{
		FileInputStream input=new FileInputStream(new File("F:\\untitled.jpg"));
		FtpUtil utils=new FtpUtil();
		String host="10.0.0.211";
		int port=21;
		String username="root";
		String password="111111";
		String basePath="/home/images/";
		String filePath="/2018/05/26";
		String filename="20180526.jpg";
		
		utils.uploadFile(host, port, username, password, basePath, filePath, filename, input);
	}
}
