package com.sopia.security.util;

public class SecurityUtil {
	
	public static String getStringFromStringArray(String[] ary,String regexType){
		String result = "";
		if(ary != null && ary.length>0){
			for(int i=0;i<ary.length;i++){
				if(i == ary.length - 1)
					result += ary[i];
				else 
					result += ary[i] + regexType;
			}
		}
		return result;
	}
	
	public static String[] getStringArrayFromString(String str,String regexType){
		String[] array = null;
		if(str != null && !str.equals("")){
			array = str.split(regexType);
		}
		return array;
	}

}
