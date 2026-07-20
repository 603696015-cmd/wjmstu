/**
 * 
 */
package com.sopia.record.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.sopia.record.service.MscRecodServiceImpl;
import com.sopia.studyman.AudioConverterUtil;

/**
 * 录音上传servlet
 * 
 * @author jiahaijiang
 */
public class RecordUploadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("开始上传录音文件！");
		InputStream in = request.getInputStream();

		String filename = new String(request.getParameter("fileName")
				.getBytes(), "utf-8");
		String realPath = request.getRealPath("/elstuffs/audio");
		String filePath=realPath+ File.separator + filename;
		System.out.println(filePath);
		File f = new File(filePath);
		FileOutputStream fos = new FileOutputStream(f);
		IOUtils.copy(in, fos);
		fos.close();
		in.close();
		//上面代码完成录音文件的上传功能
		
		
		//下面是语音解析的例子
		//实例化语音解析接口，在这个地方我是直接用new的方式，也可以改用spring注入的方式
		MscRecodServiceImpl msc = MscRecodServiceImpl.getMscObj();
		String recognize = msc.recognize(filePath);
		System.out.println("解析语音："+recognize);
		
		//上传完成后转码pcm到wma
//		if(recognize!=null && !recognize.equals("")){
//			String filenamewma = filename.substring(0,filename.lastIndexOf(".")) + ".wma";
//			String filePathwma = realPath+ File.separator + filenamewma;
//			try {
//				AudioConverterUtil.convertAudioFiles(filePath, filePathwma);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		String filenamewma = filename.substring(0,filename.lastIndexOf(".")) + ".wma";
		String filePathwma = realPath+ File.separator + filenamewma;
		try {
			AudioConverterUtil.convertAudioFiles(filePath, filePathwma);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
