package com.sopia.common.register;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 解密授权文件
 * @author Administrator
 *
 */
public class EP {
	private static String[] encode={"S1O","O3P","P2I","I9A","A4E","E6L","L5S"};
	private static int[] pwd ={3,6,7,3,1,9,2,2};
	public static String ep(String ep){
		ep = ep.replace("-","M");
		String ep1 = "";
		for (int i = 0; i < ep.length(); i++) {
			String s = encode[i%7]; 
			ep1 =ep1+ ep.charAt(i)+s;
		}
		return ep1;
	}
	public static String unep(String macinfo) throws Exception{
//		String ep = unepFromFile(filename);
		String ep1 ="" ;
		for (int i = 0; i < macinfo.length(); i++) {
			if(i%4==0)
				ep1 = ep1+macinfo.substring(i,i+1);
		}
		return ep1.replace("M", "-");
	}
	public static String unepFromFile(String filename)throws Exception{
		String ep = "";
		File f = new File(filename);
		InputStream is = new FileInputStream(f);
		int size = is.available();
		for (int i = 0; i < size; i++) {
			ep = ep+(char)(is.read()-pwd[i%8]) ;
		}
		is.close();
		return ep;
	}

}
