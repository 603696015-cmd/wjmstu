package com.sopia.intelligentTutoringPoints;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sopia.common.ElException;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.intelligentTutoringPoints.dao.IntelligentWeekDao;
import com.sopia.peixunBatch.dao.PeixunBatchDao;

/**
 * 智能辅导分周学习时间Util
 * @author TMK
 *
 */
public class IntelligentWeekUtil {
	public final static IntelligentWeekDao intelligentWeekDao = (IntelligentWeekDao)SpringContextUtil.getBean(IntelligentTutoringPointsConstants.INTELLIGENT_WEEK);
	public final static PeixunBatchDao peixunBatchDao = (PeixunBatchDao)SpringContextUtil.getBean(IntelligentTutoringPointsConstants.PEIXUNBATCHDAO);
	public static Calendar cal =Calendar.getInstance();
	public static HttpSession getSession() {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return session;
	}
	/**
	 * 获取开始学习所在周的开始时间和结束时间
	 * @param day
	 * @return
	 */
	public static Map<String,String> getDayWeekBeginAandEnd(Timestamp day){
		Map<String,String> map = new HashMap<String,String>();
		map.put("begintime", getWeekBegintime());
		map.put("endtime", getWeekEndtime());
		return map;
	}
	
	/**
	 * 获取当天所在周的开始时间 格式“20130823”
	 * @return
	 */
	public static String getWeekBegintime(){
		cal =Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		
		return String.valueOf(year) + getRealMonth(month) + getRealDay(day);
	}
	
	/**
	 * 获取当天所在周的结束时间 格式“20130829”
	 * @return
	 */
	public static String getWeekEndtime(){
		cal =Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		
		return String.valueOf(year) + getRealMonth(month) + getRealDay(day);
	}
	
	/**
	 * 获取月份  格式为“08”
	 * @param month
	 * @return
	 */
	public static String getRealMonth(int month){
		if(month<10){
			return "0" + month;
		}else{
			return String.valueOf(month);
		}
	}
	/**
	 * 获取日  格式为“23”
	 * @param day
	 * @return
	 */
	public static String getRealDay(int day){
		if(day<10){
			return "0" + day;
		}else{
			return String.valueOf(day);
		}
	}
	
	public static void main(String[] args) {	
		System.out.println(getWeekBegintime());
	}
	
	/**
	 * 开始学习
	 * @param userid
	 * @param classid
	 * @param courseid
	 * @param pageid
	 * @param studyCourseRecordId
	 * @throws ElException
	 */
	public static void learnBegin(int userid,int classid,int courseid,int pageid,int studyCourseRecordId) throws ElException{
		intelligentWeekDao.intelligentLearnWeekBegin(userid,classid,courseid,pageid,studyCourseRecordId);
	}
	
	/**
	 * 结束学习
	 * @param userid
	 * @param classid
	 * @param courseid
	 * @param pageid
	 * @param studyCourseRecordId
	 * @throws ElException
	 */
	public static void learnEnd(int userid,int classid,int courseid,int pageid,int studyCourseRecordId) throws ElException{
		intelligentWeekDao.intelligentLearnWeekEnd(userid,classid,courseid,pageid,studyCourseRecordId);
	}
}
