package com.sopia.mock.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 只做工具类不做其他处理
 * @author zahj
 *
 */
public class TxtReplaceCss {
	
	private static final String REGEX = "url\\(\".*png\"\\)";
	
	/**
	 * 读取文件内存
	 * @param fileName
	 */
	public static String writeTxt(String fileName){
		File io = new File(fileName);
		//判定文件是否存在
		if(!io.exists()){
			return "文件不存在请检查路径";
		}
	
		StringBuffer sb = new StringBuffer();
		String line = null;
		Pattern p = Pattern.compile("^.*png\\)|.*jpg\\)$");//("^.*png\"\\)|.*jpg\"\\)$")
		
		try {
			//读取流
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while((line = br.readLine())!=null){
				if(line.indexOf("(") != -1 && line.indexOf(")") != -1){
					String str = line.substring(line.indexOf("("), line.indexOf(")")+1);
					if(p.matcher(str).matches()){
						//进行替换处理
						String newStr ="(../../mock/image/exam/" +str.substring(str.indexOf("(")+1);
						line = line.replace(str, newStr);
					}
				
				}
				
				System.out.println(line);
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		
		TxtReplaceCss.writeTxt("D:\\dk\\simulation.css");
		/*String str = ".logo {background:url(\"mn001.png\")/*tpa=http://mock.tangce.cn/images/mn001.png; width:247px; height:94px; position:absolute; top:0; left:15px;}";
		 Pattern p = Pattern.compile(TxtReplaceCss.REGEX);
		 Matcher m = p.matcher(str);
		if( m.matches()){
			System.out.println("fdafaf");
			
		}*/
		
/*		System.out.println(TxtReplaceCss.REGEX);*/
	}

}
