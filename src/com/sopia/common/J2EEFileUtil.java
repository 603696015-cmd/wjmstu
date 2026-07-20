package com.sopia.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

/**
 * 文件上传工具类
 * @author Administrator
 *
 */
public class J2EEFileUtil {
	private static final int BUFFER_SIZE = 16 * 1024;
	private static final Log logger = LogFactory.getLog(J2EEFileUtil.class);
	public static String getRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}

	// 文件上传
	
	// 文件复制
	// 这个方法有问题，复制的文件和源文件大小不一样
	// 已废弃，请使用copy_()方法
	private static void copy(File src, File dst) throws Exception {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			logger.error("文件复制错误",e);
			throw e;
		}
	}
	
	// 修改文件上传方法
	@SuppressWarnings("unused")
	private static void copy_(File src, File dst) throws Exception {
		try {
			InputStream in = null;
			OutputStream out = null;
//			long l =src.length();
//			BUFFER_SIZE = (int)l;
			System.out.println((int)src.length()/1024);
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						(int)src.length());
				out = new BufferedOutputStream(new FileOutputStream(dst),
						(int)src.length());
				byte[] buffer = new byte[(int)src.length()];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			logger.error("文件复制错误",e);
			throw e;
		}
	}

	// 取后缀名
	public static String getExtention(String fileName) {
		if(fileName==null)
			return "";
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1).toLowerCase();
	}

	/**
	 * 上传文件
	 * @param upFile 要上传的文件
	 * @param ext 文件的扩展名
	 * @param folder 文件的路径（相对于根）
	 * @param newName 新文件名
	 * @throws Exception
	 */
	public static void upload(File upFile, String ext, String folder,
			String newName) throws Exception {
		try {
			File imageFile = new File(getRealPath("/") + folder);
			if (!imageFile.exists()){
				boolean bool = imageFile.mkdirs();
				if(!bool){
					logger.error(imageFile.getName()+"文件夹创建失败！");
				}
			}
			File desFile = new File(getRealPath("/") + folder + "/" + newName
					+ "." + ext);
//			copy(upFile, desFile);
			copy_(upFile, desFile);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 上传文件  was下
	 * @param upFile 要上传的文件
	 * @param ext 文件的扩展名
	 * @param folder 文件的路径（相对于根）
	 * @param newName 新文件名
	 * @throws Exception
	 */
	public static void upload_was(File upFile, String ext, String folder,
			String newName) throws Exception {
		try { 
			//File imageFile_was = new File(getRealPath("/")); //获取到项目的发布路径 ， 在was下面发布路径下面还会存在一个一个war包。 war包里才是项目源
			File imageFile = new File(getRealPath("/") + "/"+ folder);//所有在此处要获取带项目发布路径下的war源下的 folder 名是否存在 
			if (!imageFile.exists())
				imageFile.mkdirs();
			if (!imageFile.exists()){
				boolean bool = imageFile.mkdirs();
				if(!bool){
					logger.error(imageFile.getName()+"文件夹创建失败！");
				}
			}
			File desFile = new File(getRealPath("/") + "/" +  folder + "/" + newName
					+ "." + ext);
			copy(upFile, desFile);
		} catch (Exception e) {
			throw e;
		}
	}
	public static void createFolder(String path)throws Exception {
		try {
			File f = new File(getRealPath("/") + path);
			if (!f.exists()) {
				boolean bool = f.mkdirs();
				if(!bool){
					logger.error(path+"文件夹创建失败！");
				}
			}
		} catch (Exception e) {
			logger.error("创建文件夹错误",e);
			throw e;
		}
	} 
	public static void deleteFolder(String path) throws Exception {
		try {
			File f = new File(getRealPath("/") + path);
			if (f.exists()) {
				File[] fs = f.listFiles();
				for (File file : fs) {
					if (file.exists()){
						boolean bool=file.delete();
						if(!bool){
							logger.error(file.getPath()+"文件删除失败！");
						}
					}
				}
				boolean bool=f.delete();
				if(!bool){
					logger.error(f.getPath()+"文件删除失败！");
				}
			}
		} catch (Exception e) {
			logger.error("删除文件错误",e);
			throw e;
		}
	}

	public static void deleteFile(String path, String fileName, String ext)
			throws Exception {
		try {
			File f = new File(getRealPath("/") + path + "/" + fileName + "."
					+ ext);
			if (f.exists()) {
				boolean bool=f.delete();
				if(!bool){
					logger.error(f.getPath()+"文件删除失败！");
				}
			}
		} catch (Exception e) {
			logger.error("删除文件错误",e);
			throw e;
		}
	}
	public static boolean fileIsexists(String fileName){
		File f = new File(getRealPath("/") +"elstuffs/" + fileName);
		if (f.exists()) return true;
		return false;
	}
	public static void rename(String oldfile,String newfile){
		if(!fileIsexists(oldfile)) return ;
		File f = new File(getRealPath("/") +"elstuffs/" + oldfile);
		boolean bool=f.renameTo(new File(getRealPath("/") +"elstuffs/"+newfile)) ;
		if(!bool){
			logger.error(f.getPath()+"文件移动失败！");
		}
	}
	
	/**
	 * 上传文件==针对险种、设备评估
	 * @param upFile 要上传的文件
	 * @param ext 文件的扩展名
	 * @param folder 文件的路径（相对于根）
	 * @param newName 新文件名
	 * @throws Exception
	 */
	public static void upload_xianzhong(File upFile, String ext, String folder,
			String newName) throws Exception {
		try {
			File imageFile = new File(getRealPath("/") + folder);
			if (!imageFile.exists()){
				boolean bool = imageFile.mkdirs();
				if(!bool){
					logger.error(imageFile.getName()+"文件夹创建失败！");
				}
			}
			File desFile = new File(getRealPath("/") + folder + "/" + newName
					+ "." + ext);
			copy_(upFile, desFile);
		} catch (Exception e) {
			throw e;
		}
	}
}
