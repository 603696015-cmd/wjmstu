package com.sopia.schedule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.sopia.common.J2EEFileUtil;
import com.sopia.schedule.entities.ModuleZDY;

public class ZDYTemplateUtil {

	public static String ADD = "add";
	public static String UPDATE = "update";
	public static String VIEW = "view";

	public static String PATH = "\\admin\\etcperfect\\template\\";
	public static String USERPATH = "C:\\template\\";
	public static String SHOWUSERPATH = "C:/template/";

	public static String getUploadValueByUploadType(int uploadtype) {
		String str = "";
		if (uploadtype == 1)
			str = ADD;
		else if (uploadtype == 2)
			str = UPDATE;
		else if (uploadtype == 3)
			str = VIEW;
		return str;
	}

	public static String getExtByUploadType(int uploadtype) {
		String ext = "";
		if (uploadtype == 4) {
			ext = "css";
		} else {
			ext = "jsp";
		}

		return ext;
	}

	public static boolean checkIfUploadByUploadType(String foldername,
			String uploadtype, String jspName) {
		boolean flag = false;
		File file = new File(J2EEFileUtil.getRealPath("/") + PATH  + foldername);
		if (file.exists()) {
			File[] filelist = file.listFiles();
			for (int i = 0; i < filelist.length; i++) {
				if (!filelist[i].isDirectory()) {
					if (filelist[i].getName().equals(jspName)) {
						flag = true;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	public static String createColumnTBHTMLName(String column) {
		// <wysLib:TBHTMLName iname="LJR_ZW"></wysLib:TBHTMLName>
		return "<wysLib:TBHTMLName iname=\"" + column
				+ "\"></wysLib:TBHTMLName>";
	}

	public static String createColumnTBHTML(String column, int uploadType) {
		String str = "";
		if (uploadType == 1) {
			// <wysLib:TBHTML_ADD iname="LJR_XB"></wysLib:TBHTML_ADD>
			str = "<wysLib:TBHTML_" + ADD.toUpperCase() + " iname=\"" + column
					+ "\"></wysLib:TBHTML_" + ADD.toUpperCase() + ">";
		} else if (uploadType == 2) {
			// <wysLib:TBHTML_UPDATE iname="LJR_XB"></wysLib:TBHTML_UPDATE>
			str = "<wysLib:TBHTML_" + UPDATE.toUpperCase() + " iname=\""
					+ column + "\"></wysLib:TBHTML_" + UPDATE.toUpperCase()
					+ ">";
		} else if (uploadType == 3) {
			// <wysLib:TBHTML_VIEW iname="LJR_XB"></wysLib:TBHTML_VIEW>
			str = "<wysLib:TBHTML_" + VIEW.toUpperCase() + " iname=\"" + column
					+ "\"></wysLib:TBHTML_" + VIEW.toUpperCase() + ">";
		}
		return str;
	}

	/**
	 * 判断某文件夹是否存在，不存在则创建
	 * @param folder
	 * @return
	 * @throws IOException
	 */
	public static boolean checkFolderIsExist(String folder) throws IOException {
		boolean flag = false;
		File file = new File(USERPATH + folder);
		if (file.exists()) {
			flag = true;
		} else {
			file.mkdirs();
			flag = true;
		}
		return flag;
	}

	/**
	 * 创建txt文件
	 * 
	 * @throws IOException
	 */
	public static boolean creatTxtFile(String folder, String tablename,
			int uploadType) throws IOException {
		boolean flag = false;
		File file = new File(USERPATH + folder);
		if (file.exists()) {
			File filename = new File(USERPATH
					+ folder + "\\" + tablename + "_"
					+ getUploadValueByUploadType(uploadType) + ".txt");
			if (!filename.exists()) {
				filename.createNewFile();
				flag = true;
			}else{
				flag = true;
			}
		} else {
			file.createNewFile();
			flag = true;
		}

		return flag;
	}
	
	public static String formateColumnToHTML(String column, int uploadType){
		/**
		 * <tr>
			<td>
				<wysLib:TBHTMLName iname="LJR_SZBM"></wysLib:TBHTMLName>
			</td>
			<td>
				<wysLib:TBHTML_ADD iname="LJR_SZBM"></wysLib:TBHTML_ADD>
			</td>
		</tr>
		 */
		return "<tr>" + "\n"+
					"<td>" + "\n"+
					createColumnTBHTMLName(column)+"\n"+
					"<td/>"+"\n"+
					"<td>" +"\n"+
					createColumnTBHTML(column,uploadType)+"\n"+
					"<td/>"+"\n"+
				"</tr>"+"\n";
	}

	/** 
	* 写文件 
	* @param newStr 新内容 
	* @throws IOException 
	*/ 
	public static boolean writeTxtFile(String folder,int uploadType,String newStr) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			String filenameTemp = USERPATH + folder + "\\" + folder + "_" + getUploadValueByUploadType(uploadType) + ".txt";
			
			File file = new File(filenameTemp);
			//文件存在则清空文件
			if(file.exists()){
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("");
				bw.close();
				fw.close();
			}
			
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

//			buf.append(newStr);
			String[] arr = newStr.split("\n");
			for(int i=0;i<arr.length;i++){
				buf.append(arr[i]+"\r\n");
			}

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			flag = true;
		} catch (IOException e1) {
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}
	

}
