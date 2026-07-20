package com.sopia.cms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElException;

public class IoUtil {
	private static final Log logger=LogFactory.getLog(IoUtil.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public Map<String, String> getJspPathList(String path) {
		Map<String, String> fileMap = new Hashtable<String, String>();
		List<String> templateList = new ArrayList<String>();
		File file = new File(path + "/template");
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isDirectory()) {
				// jspPathList.add(files[i].getAbsolutePath());
				templateList.add(files[i].getName());
			}
		}

		File jspFile = new File(path + "/elfrontman");
		File[] jspFiles = jspFile.listFiles();
		for (int i = 0; i < jspFiles.length; i++) {
			if (!jspFiles[i].isDirectory()) {
				// jspPathList.add(files[i].getAbsolutePath());
				if (!templateList.contains(jspFiles[i].getName())) {
					fileMap.put(jspFiles[i].getName(), jspFiles[i]
							.getAbsolutePath());
				}
			}
		}
		return fileMap;
	}

	/**
	 * 获取模板文本
	 * 
	 * @param path
	 * @return
	 */
	public String GetTemplate(String path) throws Exception {
		File infile = new File(path);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(infile), "utf-8"));
		String reading;
		StringBuffer sb = new StringBuffer();
		while ((reading = in.readLine()) != null) {
			sb.append(reading + "\r\n");
		}
		in.close();
		return sb.toString();
	}

	/**
	 * 获取标签
	 * 
	 * @param tplContent
	 * @return
	 */
	public List<String> getLabels(String tplContent) {
		List<String> lbs = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\{CMS_.*\\}");
		Matcher matcher = pattern.matcher(tplContent);
		while (matcher.find()) {// 获取标签
			for (int i = 0; i <= matcher.groupCount(); i++) {
				// System.out.println(matcher.group(i));// 正则获取的标签
				String lb = matcher.group(i);
				if (!lbs.contains(lb))
					lbs.add(lb);
			}
		}
		return lbs;
	}

	/**
	 * 把标签中的信息用对象保存
	 * 
	 * @param lbs
	 * @return
	 */
	public Hashtable<String, LabelModel> getLabelInfo(List<String> lbs)
			throws Exception {
		Hashtable<String, LabelModel> lbInfo = new Hashtable<String, LabelModel>();
		for (String lb : lbs) {
			LabelModel lm = new LabelModel();
			Pattern stylePtn = Pattern.compile("class=[a-z0-9]+");
			Matcher styleMch = stylePtn.matcher(lb);
			String styles = "";
			while (styleMch.find()) {
				styles += styleMch.group(0).replace("class=", "") + " ";
			}

			Pattern patterns = Pattern.compile("[^_{}(class=)+]+");
			Matcher matchers = patterns.matcher(lb);
			int infoCount = 0;
			while (matchers.find()) {
				if (infoCount == 1) {
					lm.setLabelType(matchers.group(0).toUpperCase());
				} else if (infoCount == 2) {
					lm.setModelType(matchers.group(0).toUpperCase());
				} else if (infoCount == 3) {
					lm.setModelId(Integer.parseInt(matchers.group(0)
							.toUpperCase().replace("ID", "")));
				} else if (infoCount == 4) {
					lm.setRecord(Integer.parseInt(matchers.group(0)));
				} else if (infoCount == 5) {
					lm.setTitleLength(Integer.parseInt(matchers.group(0)));
				} else if (infoCount == 6) {
					lm.setContentType(Integer.parseInt(matchers.group(0)));
				} else if (infoCount == 7) {
					lm.setRow(Integer.parseInt(matchers.group(0)));
				} else if (infoCount == 8) {
					try {
						int titleLength = Integer.parseInt(matchers.group(0));
						lm.setContentLength(titleLength);
					} catch (Exception e) {
					}
				}
				infoCount++;
			}
			lm.setStyles(styles);
			lbInfo.put(lb, lm);
		}
		return lbInfo;
	}

	/**
	 * 标签文本替换
	 * 
	 * @param content
	 * @param ht
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String convertTxt(String content, Hashtable<String, String> ht) {
		Enumeration e = ht.keys();
		while (e.hasMoreElements()) {
			String key = e.nextElement().toString();
			// System.out.println(ht.get(key));
			content = content.replaceAll("\\" + key, ht.get(key));
		}
		return content;
	}

	/**
	 * 截取字符串
	 * 
	 * @param txt
	 * @param length
	 * @return
	 */
	public String getSubString(String txt, int length) {
		if (length == 0) {
			length = 20;
		}
		if (null == txt) {
			return "";
		}else{
			txt=removeHtml(txt);
			if (txt.length() > length) {
				txt = txt.substring(0, length) + "...";
			}
		} 
		return txt;
	}

	public String removeHtml(String txt) {
		if (null == txt) {
			return "";
		} else { 
			txt = txt.replaceAll("(<\\s*/?\\s*\\w+\\s*>)|(&\\w+;)","");
			return txt;
		}
	}
	/**
	 * 保存文档
	 * 
	 * @param content
	 * @param path
	 * @throws Exception
	 */
	public void saveDoc(String content, String path) throws Exception {
		// copyFile(path, path+".bak");//备份文件
		File outfile = new File(path);
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outfile), "utf-8")));
		out.println(content);// 写入到流中
		out.close();
		boolean bool=outfile.createNewFile();
		if(!bool){
			logger.error(path+"文件保存失败！");
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @throws Exception
	 */
	public void copyFile(String oldPath, String newPath) throws Exception {
		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPath);
		System.out.println(oldPath);
		System.out.println(newPath);
		if (oldfile.exists()) {
			// 文件存在时
			InputStream inStream = new FileInputStream(oldPath); // 读入原文件
			FileOutputStream fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; // 字节数 文件大小
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
			fs.close();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public void deleteFile(String filePath) throws Exception {
		File file = new File(filePath);
		if (file.exists()) {
			boolean bool=file.delete();
			if(!bool){
				//throw new ElException("文件删除失败！");
				logger.error(filePath+"文件删除失败！");
			}
		}
	}
}
