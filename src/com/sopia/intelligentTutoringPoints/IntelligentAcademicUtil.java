package com.sopia.intelligentTutoringPoints;

import com.sopia.common.ElException;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.intelligentTutoringPoints.dao.IntelligentAcademicDao;

/**
 * 智能辅导分之学习成绩Util
 * @author TMK
 *
 */
public class IntelligentAcademicUtil {
	public final static IntelligentAcademicDao intelligentAcademicDao = (IntelligentAcademicDao)SpringContextUtil.getBean(IntelligentTutoringPointsConstants.INTELLIGENT_ACADEMIC);

	/**
	 * 章节考试
	 * @param userid
	 * @param roomid
	 * @param myexampaperid
	 * @param classid
	 * @throws ElException
	 */
	public static void intelligentAcademic(int userid,int roomid,int classid,int courseid,int pageid,int myexampaperid) throws ElException{
		intelligentAcademicDao.intelligentAcademic(userid,roomid,classid,courseid,pageid,myexampaperid);
	}
	
	/**
	 * 课程考试
	 * @param userid
	 * @param roomid
	 * @param classid
	 * @param courseid
	 * @param myexampaperid
	 * @param classType
	 * @throws ElException
	 */
	public static void intelligentAcademicCourse(int userid,int roomid,int classid,int courseid,int myexampaperid,int classType) throws ElException{
		intelligentAcademicDao.intelligentAcademicCourse(userid,roomid,classid,courseid,myexampaperid,classType);
	}
}
