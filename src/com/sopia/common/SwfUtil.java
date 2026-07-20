package com.sopia.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.common.spring.SpringContextUtil;
import com.sopia.questionman.entities.StuffLib;

public class SwfUtil {
	public static final Log logger=LogFactory.getLog(SwfUtil.class);
	private String cmdStr="";
	
	/**
	 * 获取项目根绝对路径
	 * @param path
	 * @return
	 */
	public static String getRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}
	/**
	 * 把资源文件转换成swf
	 * @param stuff
	 * @return
	 */
	public boolean toSwf(StuffLib stuff) throws ElException{
		//判断是否是指定的格式
		String fileext=stuff.getFileext().toLowerCase();
		if(!"doc".equals(fileext)&&!"xls".equals(fileext)&&!"ppt".equals(fileext)&&!"pdf".equals(fileext)){
			//此格式不用转换!
			return true;
		}
		//判断系统是否windows
		String os = System.getProperty("os.name");
		if (!os.startsWith("Windows")) {
			//系统不支持文件转换swf!
			return true;
		}
		StringBuffer buffer=null;
		cmdStr="p2fServer "+getRealPath("elstuffs/"+stuff.getPath()+"."+stuff.getFileext());
		//创建目标目录
		File file=new File(getRealPath("/")+"swffile");
		if (!file.exists()){
			boolean bool = file.mkdirs();
			if(!bool){
				logger.error(file.getName()+"文件夹创建失败！");
			}
		}
		file=new File(file.getPath()+"/"+stuff.getId()+".swf");
		cmdStr+=" "+file.getPath();
		try {
			Process process = Runtime.getRuntime().exec(cmdStr);
			InputStream is=new BufferedInputStream(process.getInputStream());
			buffer=new StringBuffer();
			int c=0;
			while(true){
				if(-1==(c=is.read())){
					break;
				}
				buffer.append((char)c);
			}
		} catch (IOException e) {
			logger.error("cmd命令调用有误！", e);
			//throw new ElException(e);
			return false;
		}
		return true;
	}
	
	/**
	 * 把资源文件转换成swf
	 * @param stuff
	 * @return
	 */
	public String toSwf_(StuffLib stuff) throws ElException{
		//判断是否是指定的格式
		String fileext=stuff.getFileext().toLowerCase();
		if(!"doc".equals(fileext)&&!"xls".equals(fileext)&&!"ppt".equals(fileext)&&!"pdf".equals(fileext)
				&&!"xlsx".equals(fileext)&&!"pptx".equals(fileext)&&!"docx".equals(fileext)
				&&!"txt".equals(fileext)){
			//此格式不用转换!
			return "";
		}
		String file=getRealPath("/elstuffs/"+stuff.getPath()+"."+stuff.getFileext());
		Office2pdf office2pdf=(Office2pdf)SpringContextUtil.getBean("office2pdf");
		return office2pdf.doc2swf(file,stuff);
		
	}
	
	/**
	 * 判断转换后的swf文件是否存在
	 * @param filePath
	 * @return
	 */
	public boolean checkSwfFileIsExist(File file){
		boolean flag = false;
		if (file.exists() && file.isFile()) {
			flag = true;
		}
		return flag;
	}
}
