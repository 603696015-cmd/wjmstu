package com.msc.applet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.msc.applet.HttpUtils.CallBack;

/**
 * <p>
 * 上传文件的applet
 * </p>
 * 
 * @author jiahaijiang
 * 
 */
public class UploadRecordFile {
	private String filePath;
	private String fileName;
	private String url;

	private UploadRecordFile() {
	}

	public UploadRecordFile(String filePath, String fileName, String url) {
		this.filePath = filePath;
		this.fileName = fileName;
		this.url = url;
	}

	public void send() {
		try {
			String filePath = getFilePath();
			String fileName = getFileName();
			String urlstring = getUrl();
			if(urlstring.indexOf("?")!=-1){
				 urlstring = urlstring + "&fileName="+ URLEncoder.encode(fileName, "utf-8");
			}else{
				 urlstring = urlstring + "?fileName="+ URLEncoder.encode(fileName, "utf-8");
			}
			// 网络路径很重要
			URL url1 = new URL(urlstring);

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		System.out.println(this.filePath);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		System.out.println(this.fileName);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		System.out.println(this.url);
	}

	 //将文件转换成Byte数组
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void main(String[] args) throws IOException {

//  	UploadRecordFile up = new UploadRecordFile("C:\\record\\20201_442300_16557.pcm","20201_442300_16557.pcm","http://localhost:8877/record/RecordUploadServlet");
//  	up.send();
//		
		byte[] f = getBytesByFile("c:/record/20201_442440_22615.pcm");
		
		try {
			HttpUtils.doPostAsyn("http://localhost:8877/record/RecordUploadServlet",f, new CallBack() {
				@Override
				public void onRequestComplete(String result) {
					System.out.println(result);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(URLEncoder.encode("20201_442300_16557.pcm", "utf-8"));
	}
}