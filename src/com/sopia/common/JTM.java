package com.sopia.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sopia.duman.entities.ELUser;
 
public class JTM {
	
	private static final String KEY = "3kKai120815dRIU20uVHANG4L8c8JH"; 

	public static String getJTM_cer(String str) throws ElException { 

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式 
		
		String cer = str+KEY+df.format(new Date()); 
		
		return getJTM_MD5Str(cer).toUpperCase();
	} 
	  
	public static String getJTM_MD5Str(String str) {
		MessageDigest messageDigest = null;  
		try { 
			messageDigest = MessageDigest.getInstance("MD5");  
			messageDigest.reset();  
			messageDigest.update(str.getBytes("UTF-8")); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace(); 
			}  
			byte[] byteArray = messageDigest.digest(); 
			StringBuffer md5StrBuff = new StringBuffer();  
			for(int i = 0; i < byteArray.length; i++) {
				if(Integer.toHexString(0xFF & byteArray[i]).length() == 1){
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i])); 
				}else{ 
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i])); 
				} 
			}  
			return md5StrBuff.toString(); 
	} 
	public static void main(String[] args){
		try {
			String cer = JTM.getJTM_cer(String.valueOf(29520));
			System.out.println(cer);
		} catch (ElException e) {
			e.printStackTrace();
		}
	}
}
