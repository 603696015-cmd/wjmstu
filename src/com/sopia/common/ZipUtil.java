package com.sopia.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.sopia.cms.IoUtil;
import com.sopia.questionman.entities.StuffLib;

public class ZipUtil {
	static final int BUFFER = 1024;
	private static final Log logger=LogFactory.getLog(ZipUtil.class);
	/**
	 * 解压缩zip文件
	 * 
	 * @param fileName
	 *            要解压的文件名 包含路径 如："c:\\test.zip"
	 * @param filePath
	 *            解压后存放文件的路径 如："c:\\temp"
	 * @throws Exception
	 */
	public static void unZip(File fileName, String filePath) throws Exception {
		ZipFile zipFile = new ZipFile(fileName.toString(), "GBK"); // 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
		Enumeration emu = zipFile.getEntries();

		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			if (entry.isDirectory()) {
				//new File(filePath + entry.getName()).mkdirs();
				boolean bool = new File(filePath + entry.getName()).mkdirs();
				if(!bool){
					logger.error(fileName+"文件夹创建失败！");
				}
				continue;
			}
			BufferedInputStream bis = new BufferedInputStream(zipFile
					.getInputStream(entry));

			File file = new File(filePath + entry.getName());
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				boolean bool=parent.mkdirs();
	    		if(!bool){
	    			logger.error(parent.getPath()+"文件夹创建失败！");
	    		}
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);

			byte[] buf = new byte[BUFFER];
			int len = 0;
			while ((len = bis.read(buf, 0, BUFFER)) != -1) {
				fos.write(buf, 0, len);
			}
			bos.flush();
			bos.close();
			bis.close();
		}
		zipFile.close();
	}

	public static List<StuffLib> listStuffs(String folder) throws ElException {
		List<StuffLib> qss = new ArrayList<StuffLib>();
		try {
			File f = new File(folder);
			if (f != null) {
				File[] fs = f.listFiles();
				if (fs != null)
					for (int i = 0; i < fs.length; i++) {
						StuffLib qs = new StuffLib(0, getName(fs[i].getName()));
						qs.setDescription("");
						qs.setFileext(fs[i].isDirectory() ?"":J2EEFileUtil
								.getExtention(fs[i].getName()));
						// qs.setModifytime(fs[i].);
						// qs.setCreatetime(rs.getTimestamp(6));
						qs.setLength(fs[i].length());
						qs.setType(fs[i].isDirectory() ? 8 : -1);
						qss.add(qs);
					}
			}

		} catch (Exception e) {
			throw new ElException(e);
		}
		return qss;
	}

	private static String getName(String filename) {
		if (filename == null)
			return "";
		filename = filename.replaceAll("\\\\", "/");
		return filename.substring(filename.lastIndexOf("/") + 1, filename
				.lastIndexOf(".")<0?filename.length():filename.lastIndexOf("."));
	}
	// public static void unZip(String fileName) throws Exception {
	// File file= new File(fileName);
	// unZip(file,file.getParent());
	// }
	// public static void unZip(File file) throws Exception {
	// unZip(file,file.getParent()+"/");
	// }
	// public static void main(String[] args) {
	// try {
	// unZip("f:\\手机铃声1.zip");
	// } catch (Exception e) {
	// }
	// }
}
