package com.sopia;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class CreatorHtml {
	public static void callHtml(String path,String fileName,String htmldirName){
//		String str = "http://localhost:8080/hszx/newsIndexView_"+fileName+"&_path="+path+".shtm";
//		System.out.println(str);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String contextPath = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
		String str = basePath+path+"&htmlName="+fileName+"&tableName="+htmldirName;
		request.setAttribute("htmlName", fileName);//http://localhost:8080/yunguanli/newsIndexView.shtml?news.id=1460@ntype.id=1&htmlName=newsIndexView1460&tableName=newsindexview&path=newsIndexView.shtml?news.id=1460@ntype.id=1
		request.setAttribute("tableName",htmldirName);
		//str = basePath+path;
		int httpRequest;
		try {

			URL url = new URL(str);
			URLConnection connection = url.openConnection();
			connection.connect();
			HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
			httpRequest = httpURLConnection.getResponseCode();
			if(httpRequest!=httpURLConnection.HTTP_OK){
				System.out.println("没有连接成功");
			}else{
				System.out.println("连接成功");
				httpURLConnection.disconnect();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
