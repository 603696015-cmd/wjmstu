package com.sopia.studyman.dao.impl;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.LineNumberReader;

public class UtilMacAddress {

//	public String getMACAddress(String ipAddress) {
//		String str = "";
//		String strMAC = "";
//		String macAddress = "";
//		try {
//			Process pp = Runtime.getRuntime().exec("nbtstat -a " + ipAddress);
//			InputStreamReader ir = new InputStreamReader(pp.getInputStream());	
//			LineNumberReader input = new LineNumberReader(ir);	
//			for (int i = 1; i < 500; i++) {			
//				str = input.readLine();
//				if (str != null) {
//					if (str.indexOf("MAC Address") > 1) {
//						strMAC = str.substring(str.indexOf("MAC Address") + 14,str.length());
//						//break;
//					}
//				}
//			}
//		} catch (IOException ex) {
//			return "Can't Get MAC Address!";
//		}
//		if (strMAC.length() < 17) {
//			return "Error!";
//		}
//		macAddress = strMAC.substring(0, 2) + ":" + strMAC.substring(3, 5)+ ":" + strMAC.substring(6, 8) + ":" + strMAC.substring(9, 11)+ ":" + strMAC.substring(12, 14) + ":"+ strMAC.substring(15, 17);
//		return macAddress;
//	}

	public static String procAll(String str) {
		return procStringEnd(procFirstMac(procAddress(str)));
	}

	public static String procAddress(String str) {
		int indexof = str.indexOf("Physical Address");
		if (indexof > 0) {
			return str.substring(indexof, str.length());
		}
		return str;
	}

	public static String procFirstMac(String str) {
		int indexof = str.indexOf(":");
		if (indexof > 0) {
				return str.substring(indexof + 1, str.length()).trim();
		}
		return str;

	}

	public static String procStringEnd(String str) {
		int indexof = str.indexOf("\r");
		if (indexof > 0) {
			return str.substring(0, indexof).trim();
		}
		return str;
	}
	
	/**
	 * 检测ip是否在有效段
	 * @param myIpAddr
	 * @param ipStrat
	 * @param ipEnd
	 * @return
	 */
	public boolean checkIpAddr(String myIpAddr,String ipStrat,String ipEnd){
		if(myIpAddr==null){
			return false;
		}
		myIpAddr=myIpAddr.replace(".", ":");
		ipStrat=ipStrat.replace(".", ":");
		ipEnd=ipEnd.replace(".", ":");
		//拆分
		String[] myIpArray=myIpAddr.split(":");
		String[] ipStratArray=ipStrat.split(":");
		String[] ipEndArray=ipEnd.split(":");
		//比较
		for (int i = 0; i < 2; i++) {
			if(!myIpArray[i].equals(ipStratArray[i])){
				return false;
			}
		}
//		//转换成整形
//		int myIpInt=Integer.parseInt(myIpArray[2]+myIpArray[3]);
//		int ipStratInt=Integer.parseInt(ipStratArray[2]+ipStratArray[3]);
//		int ipEndInt=Integer.parseInt(ipEndArray[2]+ipEndArray[3]);
		//转换成整形
		int myIpInt=Integer.parseInt(myIpArray[2]);
		int ipStratInt=Integer.parseInt(ipStratArray[2]);
		int ipEndInt=Integer.parseInt(ipEndArray[2]);
		if(myIpInt>=ipStratInt&&myIpInt<=ipEndInt){
			//第3段OK
			myIpInt=Integer.parseInt(myIpArray[3]);
			ipStratInt=Integer.parseInt(ipStratArray[3]);
			ipEndInt=Integer.parseInt(ipEndArray[3]);
			if(myIpInt>=ipStratInt&&myIpInt<=ipEndInt){
				//OK
				return true;
			}
		}
		return false;
	}
}
