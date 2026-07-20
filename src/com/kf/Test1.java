package com.kf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Test1 {

	public static void main(String[] args) {
		try {
			String urlstring = "http://localhost:8877/record/RecordUploadServlet";
			// 网络路径很重要
			URL url1 = new URL(urlstring);
			
			String filePath = "c:/record/20201_442280_16557.pcm";

			// 打开打开SOCKET链接
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setRequestMethod("POST");
			conn.setAllowUserInteraction(true);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(true);
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			System.out.println("当前上传路径为::"+urlstring);
			File jpgFile = new File(filePath);
			if (jpgFile.isFile()) {
				// 建立文件的输入流
				FileInputStream fileInputStream = null;
				fileInputStream = new FileInputStream(jpgFile);
				BufferedInputStream bis = new BufferedInputStream(
						fileInputStream);
				OutputStream os = conn.getOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(os);
				int len = 0;
				byte[] bty = new byte[4096];
				while ((len = bis.read(bty, 0, 4096)) != -1) {
					bos.write(bty, 0, len);
					bos.flush();

				}
				bos.close();
				bis.close();
				System.out.println(conn.getContentType() + ": "+ conn.getResponseCode());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
