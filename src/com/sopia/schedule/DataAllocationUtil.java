package com.sopia.schedule;

import java.sql.Timestamp;

import com.sopia.schedule.entities.dataallocation.DataAllocation;

public class DataAllocationUtil {
	public static int KAITONG = 9;
	/**
	 * 根据数据库中状态获取中文
	 * @param status
	 * @return
	 */
	public static String getTDAStatus(int status){
		String str = "";
		if(status == 1){
			str = "已分配";
		}else if(status == 2){
			str = "已审核";
		}else if(status == 3){
			str = "未通过";
		}else if(status == 4){
			str = "未申请";
		}else{
			str = "审核中";
		}
		return str;
	}
	
	/**
	 * 判断当前时间是否在分配时间内
	 * @param dataAllocation
	 * @return
	 */
	public static boolean checkNowDataIsInDataAllocation(DataAllocation dataAllocation){
		boolean flag = false;
		long nowdata = getNowDate();
		long begintime = dataAllocation.getBegintime().getTime();
		long endtime = dataAllocation.getEndtime().getTime();
		if(nowdata<begintime || nowdata>endtime){
			flag = false;
		}else{
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static long getNowDate(){
		return System.currentTimeMillis();
	}
	

}
