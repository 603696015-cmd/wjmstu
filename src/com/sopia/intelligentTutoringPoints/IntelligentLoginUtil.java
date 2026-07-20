package com.sopia.intelligentTutoringPoints;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.intelligentTutoringPoints.dao.IntelligentLoginDao;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLogin;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;
/**
 * 智能辅导分登录Util
 * @author TMK
 *
 */
public class IntelligentLoginUtil {
	//登录加分减分DAO
	public final static IntelligentLoginDao intelligentLoginDao = (IntelligentLoginDao)SpringContextUtil.getBean(IntelligentTutoringPointsConstants.INTELLIGENT_LOGIN);
	public final static PeixunBatchDao peixunBatchDao = (PeixunBatchDao)SpringContextUtil.getBean(IntelligentTutoringPointsConstants.PEIXUNBATCHDAO);
	//一天的毫秒数
	public final static int ONEDAYMILLISECOND = 1000 * 60 * 60 * 24;
	public static HttpSession getSession() {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return session;
	}
	
	
	/**
	 * 判断最近一次登录离当前时间是否超过3天
	 * @return
	 */
	public static boolean check3dayNotLogin(Timestamp lastLoginTime){
		boolean flag = false;
		double daynumber = (System.currentTimeMillis() - lastLoginTime.getTime())/ONEDAYMILLISECOND;
		if(daynumber>3){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 获取当前时间Timestamp
	 * @return
	 */
	public static Timestamp getSystemCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 用户登录
	 * @return -1：3天未登录   1：当天第一次登录   0：当天不是第一次登录
	 * @throws ElException 
	 */
	public static IntelligentLogin intelligentLogin(int userid) throws ElException{
		//获取上一次登录时间
		Timestamp lastLoginTime = null;
		lastLoginTime = intelligentLoginDao.getLastLoginTime(userid);
		//是否3天未登录
		boolean notLogin3day = false;
		if(lastLoginTime!=null && check3dayNotLogin(lastLoginTime)){
			notLogin3day = true;
		}
		//判断是否有正在学习的培训班
		PeixunBatch peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		ElClass elClass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),(Integer) getSession().getAttribute(ElConstants.SESSION_USERID),0);
		IntelligentLogin login = null;
		if(elClass!=null&&elClass.getId()>0){
			login = intelligentLoginDao.intelligentLogin(userid,notLogin3day,elClass.getId());
			getSession().setAttribute(IntelligentTutoringPointsConstants.SESSION_LOGINID, login.getId());
		}
		return login;
	}
	/**
	 * 退出登录
	 * @param userid
	 * @throws ElException
	 */
	public static void intelligentLoginOut(int userid) throws ElException{
		//判断是否有正在学习的培训班
		PeixunBatch peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		ElClass elClass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),userid,0);
		if(elClass!=null&&elClass.getId()>0){
			Object sLoginId = getSession().getAttribute(IntelligentTutoringPointsConstants.SESSION_LOGINID);
			int loginId = 0;
			if(sLoginId!=null&&!String.valueOf(sLoginId).equals("")){
				loginId = (Integer)getSession().getAttribute(IntelligentTutoringPointsConstants.SESSION_LOGINID);
			}
			if(loginId>0){
				intelligentLoginDao.intelligentLoginOut(userid,loginId);
				getSession().removeAttribute(IntelligentTutoringPointsConstants.SESSION_LOGINID);
			}
		}
	}
}
