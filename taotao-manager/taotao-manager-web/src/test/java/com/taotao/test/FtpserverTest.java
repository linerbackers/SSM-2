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
		//����һ��ftpClient����
		FTPClient ftpClient=new FTPClient();
		

			//����ftp���ӣ�Ĭ�϶˿�21
			ftpClient.connect("10.0.0.211",21);
			ftpClient.enterLocalActiveMode();    //����ģʽ
			//��¼ftp����ʹ���û���������
			ftpClient.login("ftpuser", "ftpuser");
			//��ȡ�����ļ�
			FileInputStream instream=new FileInputStream(new File("F:\\unti22tled.png"));
			//�����ϴ�����������·��
			ftpClient.changeWorkingDirectory("/home/ftpuser/www/images/");
			//�����ϴ���ʽ
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			//�ļ��ϴ�
			ftpClient.storeFile("111111.png",instream);
			//�ر�·��
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
