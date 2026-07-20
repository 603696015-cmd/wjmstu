package com.sopia.common;

import java.io.UnsupportedEncodingException;

public class OracleECov {
	public static String asc2gb(String asc){ 
		String ret; 
		if(asc==null)return asc; 
		try{ 
			ret=new String(asc.getBytes("ISO8859_1"),"UTF-8");
		}catch(UnsupportedEncodingException e){ 
			ret=asc; 
		} 
		return ret; 
	}
	public static String gb2asc(String gb){ 
		String ret; 
		if(gb==null)return gb; 
		try{ 
			ret=new String(gb.getBytes("UTF-8"),"ISO8859_1");
		}catch(UnsupportedEncodingException e){ 
		ret=gb; 
		} 
			return ret; 
		}	
	public static int byte2int(byte b){ 
		return ((-1)>>>24)&b; 
	}
}
