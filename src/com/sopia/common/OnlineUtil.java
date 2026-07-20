package com.sopia.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.studyman.entities.MyCPage;

public class OnlineUtil {
	private static final Log logger = LogFactory.getLog(OnlineUtil.class);
	private final static Map<String, HttpSession> online = new HashMap<String, HttpSession>();

	public synchronized static boolean checkUser(String userid) {
		if (!online.containsKey(userid))
			return false;
		try {
			HttpSession session = online.get(userid);
			if (null != session
					&& null != session.getAttribute(ElConstants.SESSION_USERID)) {
				String userid_ = "";
				try {
					userid_ = session.getAttribute(ElConstants.SESSION_USERID)
							.toString();
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (userid_.equals(userid))
					return true;
			}
		} catch (Exception e) {
			logger.error("检查系统账号出错",e);
			return false;
		}

		return false;
	}

	public synchronized static String getUserSessionid(String userid) {
		if (!online.containsKey(userid))
			return null;
		try {
			HttpSession session = online.get(userid);
			if (null != session)
				return session.getId();
		} catch (Exception e) {
			logger.error("获取在线账号的sessionid错误",e);
		}
		return null;
	}

	public synchronized static void addOnlineUser(String userid,
			HttpSession session) {
		if (!checkUser(userid)) {
			online.put(userid, session);
		}
	}

	public synchronized static void removeOnlineUser(String userid) {
		online.remove(userid);
	}

	public synchronized static void setStudyInfo(int classid,int courseid,int cpid,HttpSession session) {
		session.setAttribute("studyinfo_classid", classid);
		session.setAttribute("studyinfo_courseid", courseid);
		session.setAttribute("studyinfo_cpid", cpid);
		session.setAttribute("studyinfo_time",System.currentTimeMillis() );
	}
	/**
	 * 
	 * @param classid
	 * @param courseid
	 * @param cpid
	 * @param stuRid 学员学习记录id
	 * @param session
	 */
	public synchronized static void setStudyInfo(int classid,int courseid,int cpid,int stuRid,HttpSession session) {
		session.setAttribute("studyinfo_classid", classid);
		session.setAttribute("studyinfo_courseid", courseid);
		session.setAttribute("studyinfo_cpid", cpid);
		session.setAttribute("studyinfo_time",System.currentTimeMillis());
		session.setAttribute("studyinfo_rid", stuRid);
	}
	public synchronized static MyCPage getSessionMycpage(HttpSession session){
		MyCPage myCpage=new MyCPage();
		if(session.getAttribute("studyinfo_classid")!=null&&session.getAttribute("studyinfo_courseid")!=null&&session.getAttribute("studyinfo_cpid")!=null&&session.getAttribute("studyinfo_time")!=null&&session.getAttribute("studyinfo_rid")!=null){
			myCpage.setClassid(Integer.parseInt(session.getAttribute("studyinfo_classid").toString()));
			myCpage.setCourseid(Integer.parseInt(session.getAttribute("studyinfo_courseid").toString()));
			myCpage.setCpid(Integer.parseInt(session.getAttribute("studyinfo_cpid").toString()));
			myCpage.setStudyinfo_time(Long.parseLong(session.getAttribute("studyinfo_time").toString()));
			myCpage.setStudyinfo_rid(Integer.parseInt(session.getAttribute("studyinfo_rid").toString()));
		}
		return myCpage;
	}
	public synchronized static void removeStudyInfo( HttpSession session) {
		session.removeAttribute("studyinfo_classid" );
		session.removeAttribute("studyinfo_courseid" );
		session.removeAttribute("studyinfo_cpid" );
		session.removeAttribute("studyinfo_time" );
		session.removeAttribute("studyinfo_rid");
	}

	/** 检查当前是否有学习课程（检查打开的时候的时间与session中的时间是否相符合）。
	 * @param classid
	 * @param courseid
	 * @param cpid
	 * @param session
	 * @return
	 */
	public synchronized static boolean checksStudyInfo(long time,HttpSession session) {
//		if(session.getAttribute("studyinfo_classid")==null&&session.getAttribute("studyinfo_courseid")==null&&session.getAttribute("studyinfo_cpid")==null)
//			return false;
//		int classid_ = getIntValue(session.getAttribute("studyinfo_classid").toString());
//		int courseid_ = getIntValue(session.getAttribute("studyinfo_courseid").toString());
//		int cpid_ = getIntValue(session.getAttribute("studyinfo_cpid").toString());
//		if(classid==classid_&&courseid==courseid_&&cpid==cpid_)
//			return true;
		if(session.getAttribute("studyinfo_time")==null)
			return false;
		if(getLongValue(session.getAttribute("studyinfo_time").toString())==time){
			return true;
		}
		return false;
	}
	public synchronized static boolean checksStudyInfo(int classid,int courseid,int cpid,HttpSession session) {
//		if(session.getAttribute("studyinfo_classid")==null&&session.getAttribute("studyinfo_courseid")==null&&session.getAttribute("studyinfo_cpid")==null)
//			return false;
//		int classid_ = getIntValue(session.getAttribute("studyinfo_classid").toString());
//		int courseid_ = getIntValue(session.getAttribute("studyinfo_courseid").toString());
//		int cpid_ = getIntValue(session.getAttribute("studyinfo_cpid").toString());
//		if(classid==classid_&&courseid==courseid_&&cpid==cpid_)
			return true;
//		if(session.getAttribute("studyinfo_time")==null)
//			return false;
//		if(getLongValue(session.getAttribute("studyinfo_time").toString())==time){
//			return true;
//		}
//		return false;
	}
	/**检查当前是否有学习课程。
	 * @param session
	 * @return
	 */
	public synchronized static boolean checksStudyInfo(HttpSession session) {
		if(session.getAttribute("studyinfo_time")!=null||session.getAttribute("studyinfo_classid")!=null||session.getAttribute("studyinfo_courseid")!=null||session.getAttribute("studyinfo_cpid")!=null)
			return true;
		if(session.getAttribute("studyinfo_classid")==null)
			return false;
		if(session.getAttribute("studyinfo_courseid")==null)
			return false;
		if(session.getAttribute("studyinfo_cpid")==null)
			return false;
		return false;
	}
	public static int getIntValue(String str){
		try {
			if(str==null)
				return 0;
			return  Integer.parseInt(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	public static long getLongValue(String str){
		try {
			if(str==null)
				return 0;
			return Long.parseLong(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0l;
	}
	public static int usercount(){
		return online==null?0:online.size();
	}
}
