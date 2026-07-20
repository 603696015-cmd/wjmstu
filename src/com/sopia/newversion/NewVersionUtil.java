package com.sopia.newversion;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sopia.studyman.entities.MyCourse;

public class NewVersionUtil {
	//北京市卫生局新版个人中心首页学时进度
	public static Map<String,Object> getCourseProcess(List<MyCourse> list){
		Map<String,Object> map = new HashMap<String,Object>();
		DecimalFormat df = new DecimalFormat("0.0");
		if(list!=null&&list.size()>0){
			int zong_xueshi = 0;//总学时
			int learned_xueshi = 0;//已学时长
			double process = 0.0;//所占比例
			for(int i=0;i<list.size();i++){
				zong_xueshi += list.get(i).getCourse().getDuring();
				learned_xueshi += list.get(i).getPasstime();
				process += list.get(i).getProcess();
			}
			map.put("zong_xueshi", zong_xueshi);
			map.put("learned_xueshi", learned_xueshi);
			map.put("process",df.format(process/list.size()));
		}else{
			map.put("zong_xueshi", 0);
			map.put("learned_xueshi", 0);
			map.put("process", 0);
		}
		return map;
	}
	
	//将证书的0001转为1
	public static int getCerificateNo(String str){
		
		int value = Integer.parseInt(str);
		return value;
	}
	
}
