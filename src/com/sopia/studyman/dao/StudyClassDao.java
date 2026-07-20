package com.sopia.studyman.dao;

import java.util.List;

import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.ErPara;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyRoom;

public interface StudyClassDao {


	public List<MyClass> listCanGraduateClass(int userid) throws ElException;

	public List<MyClass> listGraduatedClass(int userid) throws ElException;

	public List<MyClass> listMyStudyClass(int userid) throws ElException;
	
	public List<MyClass> listMyStudyClass(int userid, int pageNow,int pageSize) throws ElException;
	/**
	 * 个人中心我的培训班
	 * @param userid
	 * @param number
	 * @return
	 * @throws ElException
	 */
	public List<MyClass> study_index_listMyStudyClass(int userid, int number) throws ElException;
	
	public int listMyStudyClassSize(int userid) throws ElException;
	
	public List<MyClass> OnloacUcenterMyclass(int userid) throws ElException;//hwc

	// public boolean classGraduate(int userid,int classid)throws ElException;
	// public void graduateClassDelete(int userid, int classid) throws
	// ElException ;

	public List<MyCourse> listMyClassCourse(int clid, int userid, int status)
			throws ElException;

	public List<MyCourse> listMyClassCourseStat(int clid, int userid, int status)throws ElException;
	
	public List<MyCourse> listMyClassCourseStat(int clid, int userid, int eroomid, int status )	throws ElException;
	
	public boolean classCanGraduate(int userid, int classid,
			int cloptionalcredit) throws ElException;
	
	/**
	 * 获取最新一期的本年度的培训班
	 * @param year
	 * @return
	 * @throws ElException
	 */
	public MyClass getNianjianClass(int year) throws ElException;



	public void graduateClassApplay(int userid, int classid) throws ElException;

	public List<MyClass> listMyGraduatedClass(int userid, int status)
			throws ElException;
	public List<MyClass> listMyGraduatedClassByNo(String no, int status)
			throws ElException;
	
	public List<MyClass> listMyGraduatedClass(int userid, int status, int pageNow,int pageSize)
			throws ElException;

	public int listMyGraduatedClassSize(int userid, int status) throws ElException;
	
	public MyClass getCraduateClass(int userid, int classid) throws ElException;

	public void setMyPassclass(int userid, int classid) throws ElException;
	public void setMyPassclass2(int userid, int classid,int roomid) throws ElException;
	
	//选修课的状态修改
	public void updateXX(int courseid,int xx_status)throws ElException;
	/**
	 * 获取我的培训班课程信息
	 * @param clid
	 * @param userid
	 * @param eroomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyClassCourseStat(int clid, int userid,
			String eroomid, int status) throws ElException;
	/**
	 * 获取我的培训班课程信息2
	 * @param clid
	 * @param userid
	 * @param eroomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyClassCourseStat2(int clid, int userid,
			String eroomid, int status) throws ElException;
	/**
	 * 获取学员在培训班是否获取证书（2：ok）
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getStudyClassStatus(int userid,int classid) throws ElException;
	/**
	 * 查看学员证书
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public MyClass getCraduateClass2(int userid, int classid) throws ElException;
	/**
	 * 获取学员所有培训班(分页)
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyClass> OnloacUcenterMyclass(int userid, int pageNow,int pageSize) throws ElException ;
	/**
	 * 获取学员所有培训班数量
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int OnloacUcenterMyclassCount(int userid) throws ElException ;
	/**
	 * 获取学员已结业培训班数量
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getClassYesCount(int userid) throws ElException;
	/**
	 * 获取学员培训班数量
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getClassAllCount(int userid) throws ElException;
	/**
	 * 获取我的培训班课程信息（去掉已删除的）
	 * @param clid
	 * @param userid
	 * @param eroomid
	 * @param status
	 * @param sqlwhe自定义sql
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyClassCourseStat3(int clid, int userid,
			String eroomid, int status,String sqlwhe) throws ElException;
	/**
	 * 获取可申请的培训班信息(显示状态  制作中 与 已通过的)
	 * @param tree
	 * @param cltid
	 * @param elClass
	 * @param role
	 * @param sqlw 用于添加额外条件   例：" and elc.id in ( 1,2,3) " 用于获取 符合条件的班级
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getApplyForeElclass(ElClType tree, int cltid,ElClass elClass ,int role,String sqlw, int pageNow,int pageSize) throws ElException;
	public int getApplyForeElclassSize(ElClType tree, int cltid,ElClass elClass ,int role ,String sqlw) throws ElException;
	/**
	 * 获取申请条件的培训班
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public ElClass getApplyForeElclassById(int classid) throws ElException;
	/**
	 * 该用户是否已申请过
	 * @param classid
	 * @param userid
	 * @return  
	 * @throws ElException
	 */
	public boolean checkClassIsUser(int classid,int userid)throws ElException;
	/**
	 * 获取已申请的培训班
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> registeredElclass(int userid, int pageNow,int pageSize)throws ElException;
	public int registeredElclassSize(int userid)throws ElException;
	/**
	 * 获取已申请的考场
	 * @param userid
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> registeredEroom(int userid, int pageNow,int pageSize)throws ElException;
	public int registeredEroomSize(int userid)throws ElException;
	/**
	 * 获取待审核的培训班数量
	 * @return
	 * @throws ElException
	 */
	public int getClassEndCount() throws ElException;
	/**
	 * 获取该培训班中所有人员
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listStudyByClass(int classid) throws ElException;
	/**
	 * 获取该学员该培训班没有拿证的详细原因
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public MyClass getStudyClassNoPassRemack(int userid,int classid) throws ElException;
	/**
	 * 更新学员培训班的状态
	 * @param userid
	 * @param classid
	 * @param status
	 * @throws ElException
	 */
	public void updateStudyClassStatus(int userid, int classid,int status) throws ElException;
	/**
	 * 获取可报名的培训班数量
	 * @param userid
	 * @param roleid
	 * @return
	 * @throws ElException
	 */
	public int getClassAppcount(int userid,int roleid) throws ElException;
	/**
	 * 添加可申请且需要审核的培训班学员报名信息
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public void addStudyClassApply(int classid,int userid) throws ElException;
	/**
	 * 检测学员是否已经报名（培训班）
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyClassApply(int classid, int userid) throws ElException;
	/**
	 * 更新学员培训班报名状态
	 * @param erid
	 * @param epid
	 * @param delStatus
	 * @throws ElException
	 */
	public void udpateStudyClassApplyStatus(int classid,int userid,int status) throws ElException;
	/**
	 * 获取学员培训班证书编号
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getStudyClassCertificateno(int classid, int userid) throws ElException;
	/**根据报名条件检查考场试卷情况
	 * @param eroomRegistration
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public String checkPassClasss(ClassPara erpara,int userid)throws ElException;
	/**
	 * 获取我的培训班课程信息（去掉已删除的）
	 * @param clid
	 * @param userid
	 * @param eroomid
	 * @param status
	 * @param sqlwhe自定义sql
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> listMyClassCourseStat3(int clid, int userid,
			String eroomid,String tableName, int status,String sqlwhe) throws ElException;
	public void setMyPassclass_at(int userid, int classid) throws ElException;
	/*
	 * 通过classid =1040  and userid = 3589 and courseid = 610
	 * 删除CLASS_COURSE_AT 
	 */
	public void delete_CLASS_COURSE_AT(int classid,int userid,int courseid) throws ElException;
	
	
	public List<MyCourse> getCourses(int classid,int userid) throws ElException;
	public List<MyCourse> getCourses_wjm(int classid,int userid) throws ElException;
	public MyClass getStudyClassStatus(int userid) throws ElException;
	
	//检查是否选班
	public int getIsChangeclass( int userid) throws ElException;
	
	//拿证培训班
	public MyClass getNaZhengClass(int year) throws ElException ;
	
	//判断用户是否购买年检培训班
	public int isNianjianClass(int uesrid) throws ElException ;
	//根据证书号查询
	public MyClass getZhengShuByNo(int year,int classid,int no) throws ElException ;
	public int getZhengShuByNo(int year,int classid,int no,int userid) throws ElException ;
	//获得培训班考场通过情况
	public int getElclassIsPass(int userid,int classid) throws ElException ;
	//得到选修课已获得的学分
	public int getcountXFforXX(int userid,int classid ) throws ElException ;
	//查看培训班是否有未通过的必修课程
	public int isNoPassBX(int userid,int classid)throws ElException ;
	//获得培训班关联考场相关信息
	public MyRoom myRoom(int userid,int classid)throws ElException ;
	//获得必修课总学分
	public int countScoreBX(int classid)throws ElException ;
	//获得必修课已得到的学分
	public int  getScoreBX(int userid,int classid)throws ElException ;
	//根据证书号、身份证查询
	public MyClass getZhengShuByNoIdCard(int year,int classid,int no,String idcard) throws ElException ;
	//判断培训班是否绑定考场
	public boolean isBindEroom(int classid) throws ElException ;
	//获得本栏目推荐培训班
	public List<ElClass> getTjElclass(int ctid,int hot)throws ElException;
	
	public List<ElClass> getTjElclass(int hot)throws ElException;

}
