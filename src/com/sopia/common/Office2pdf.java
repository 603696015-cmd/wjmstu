package com.sopia.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;

import com.sopia.ElConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.questionman.dao.StuffDao;
import com.sopia.questionman.entities.StuffLib;

public class Office2pdf {
	
	private static OfficeManager officeManager;
//	private static String OFFICE_HOME = "D:/openOffice";
//	private static String PDF2SWF_PATH = "C:/Program Files/SWFTools/pdf2swf.exe";
//	private static String OFFICE_HOME = SystemConfOp.getValue(ElConstants.SYSTEM_CONF_OFFICE_HOME);
//	private static String PDF2SWF_PATH = SystemConfOp.getValue(ElConstants.SYSTEM_CONF_PDF2SWF_PATH);
	private static int port[] = { 8100 };

	public String convert2PDF(String inputFile) {
		String msg="";
		try {
			if (inputFile.endsWith(".txt")) {
				String odtFile = FileUtils.getFilePrefix(inputFile) + ".odt";
				if (new File(odtFile).exists()) {
					//System.out.println("odt文件已存在！");
					inputFile = odtFile;
				} else {
					try {
						FileUtils.copyFile(inputFile, odtFile);
						inputFile = odtFile;
					} catch (FileNotFoundException e) {
//						System.out.println("文档不存在！");
//						e.printStackTrace();
						return "文档不存在！";
					}
				}
			}
			String pdfFile = FileUtils.getFilePrefix(inputFile) + ".pdf";
			startService();
			OfficeDocumentConverter converter = new OfficeDocumentConverter(
					officeManager);
			
			if(!inputFile.endsWith(".pdf")){
				converter.convert(new File(inputFile), new File(pdfFile));
			}
			stopService();
			//转换pdf成功后，转换pdf第一页为jpg缩略图
			PdfToImages.tranfer1(pdfFile, 1);
		} catch (Exception e) {
			// TODO: handle exception
			//可能openOffice，没装记录到数据库保存状态为1
			return "转换文档为pdf文件失败！";
		}
		
		return msg;
	}
	public String convert2SWF(String inputFile) {
//		File pdfFile = new File(inputFile);
		File swfFile = new File(FileUtils.getFilePrefix(inputFile) + ".swf");
		System.out.println(swfFile.getPath());
//		if(!inputFile.endsWith(".pdf")){
//			//System.out.println("文件格式非PDF！");
//			return "";
//		}
//		if(!pdfFile.exists()){
//			//System.out.println("PDF文件不存在！");
//			return "";
//		}
//		if(swfFile.exists()){
//			//System.out.println("SWF文件已存在！");
//			return "";
//		}
		String command = SystemConfOp.getValue(ElConstants.SYSTEM_CONF_PDF2SWF_PATH) +" \""+inputFile+"\" -o "+swfFile+" -T 9 -f";
		try {
			Process p = Runtime.getRuntime().exec(command);
			InputStream is=new BufferedInputStream(p.getInputStream());
			StringBuffer buffer=new StringBuffer();
			int c=0;
			while(true){
				if(-1==(c=is.read())){
					break;
				}
				buffer.append((char)c);
			}
			System.out.println(":::"+buffer.toString());
		} catch (IOException e) {
			//e.printStackTrace();
			//System.out.println("转换文档为swf文件失败！");
			return "转换文档为swf文件失败！";
		}
		return "";
		
	}
	public String doc2swf(String file,StuffLib stuff) throws ElException{
		String msg=convert2PDF(file);
		StuffDao stuffDao=(StuffDao)SpringContextUtil.getBean("stuffDao");
		if("".equals(msg)){
			String pdfFile = FileUtils.getFilePrefix(file) + ".pdf";
			msg=convert2SWF(pdfFile);
			if(!"".equals(msg)){
				//转换失败，可能是命令调用有误，或者pdf2swf软件没安装，或者pdf2swf路径配置有误
				stuffDao.updateStuffStatus(stuff.getId(), 1);
			}
		}else{
			//转换失败，可能是openOffice软件没安装，或者openOffice路径配置有误
			stuffDao.updateStuffStatus(stuff.getId(), 2);
		}
		return msg;
	}
	public static void main(String[] args) {
//		Office2pdf of = new Office2pdf();
//		of.doc2swf("F:/temp/user3.xls");
//		of.doc2swf("F:/temp/会议纪要.doc");
//		of.doc2swf("F:/temp/需求记录.txt");
		OfficeManager officeManager_;
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		try {
			configuration.setOfficeHome("C:/Program Files (x86)/OpenOffice.org 3");// 设置OpenOffice.org安装目录
			configuration.setPortNumbers(port); // 设置转换端口，默认为8100
			configuration.setTaskExecutionTimeout(1000 * 60 * 5L);// 设置任务执行超时为5分钟
			configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);// 设置任务队列超时为24小时
	
			officeManager_ = configuration.buildOfficeManager();
			officeManager_.start(); // 启动服务
			OfficeDocumentConverter converter = new OfficeDocumentConverter(
					officeManager_);
//			converter.convert(new File("C:/Users/Administrator.ZGC-20121214TTP/Desktop/外经贸/13年1月/系统模块的自定义字段管理0102（陶铭科）.docx"), 
//					new File("C:/Users/Administrator.ZGC-20121214TTP/Desktop/外经贸/13年1月/系统模块的自定义字段管理0102（陶铭科）.pdf"));
			converter.convert(new File("F:/apache-tomcat-6.0.36/webapps/wjm/elstuffs/1193/系统模块的自定义字段管理0102（陶铭科）.docx"), 
					new File("F:/apache-tomcat-6.0.36/webapps/wjm/elstuffs/1193/系统模块的自定义字段管理0102（陶铭科）.pdf"));
			if (officeManager_ != null) {
				officeManager_.stop();
			}
		} catch (Exception ce) {
		}
	}
	public static void startService() {
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		try {
			configuration.setOfficeHome(SystemConfOp.getValue(ElConstants.SYSTEM_CONF_OFFICE_HOME));// 设置OpenOffice.org安装目录
			configuration.setPortNumbers(port); // 设置转换端口，默认为8100
			configuration.setTaskExecutionTimeout(1000 * 60 * 5L);// 设置任务执行超时为5分钟
			configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);// 设置任务队列超时为24小时
	
			officeManager = configuration.buildOfficeManager();
			officeManager.start(); // 启动服务
		} catch (Exception ce) {
		}
	}
	public static void stopService() {
		if (officeManager != null) {
			officeManager.stop();
		}
	}
}
/**
 * jodconverter-core-3.0-beta-4.jar commons-io-1.4.jar juh-3.2.1.jar
 * jurt-3.2.1.jar ridl-3.2.1.jar unoil-3.2.1.jar
 */
