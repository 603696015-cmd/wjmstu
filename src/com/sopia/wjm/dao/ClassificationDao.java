package com.sopia.wjm.dao;

import java.util.List;
import java.util.Map;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.wjm.entities.Classification;
import com.sopia.wjm.entities.ELUserClassification;

public interface ClassificationDao {
	
	/**
	 * 获取定级列表
	 * @return
	 * @throws ElException
	 */
	public List<Classification> list_classification() throws ElException;
	
	/**
	 * 根据name修改定级信息
	 * @param classification
	 * @throws ElException
	 */
	public void updateClassificationByName(Classification classification) throws ElException;
	
	
	/**
	 * 获取系统定级考场id
	 * @return
	 * @throws ElException
	 */
	public int getRoomid() throws ElException;
	
	/**
	 * 修改定级考场
	 * 数据表为eluser_classification
	 * @param roomid
	 * @throws ElException
	 */
	public void updateRoomid(int roomid) throws ElException;
	
	/**
	 * 学生学习查询
	 * @param searchDep
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @param returnIds
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStudents(Department searchDep,ELUser elUser,int pageNow,int pageSize,String returnIds) throws ElException;
	
	/**
	 * 学生学习查询
	 * @param searchDep
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @param returnIds
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStudents(Department searchDep,ELUser elUser,Map<String,Object> params,int pageNow,int pageSize,String returnIds) throws ElException;
	/**
	 * 学生学习查询Count
	 * @param searchDep
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @param returnIds
	 * @return
	 * @throws ElException
	 */
	public int getStudentsCount(Department searchDep,ELUser elUser,int pageNow,int pageSize,String returnIds) throws ElException;
	
	/**
	 * 学生学习查询Count
	 * @param searchDep
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @param returnIds
	 * @return
	 * @throws ElException
	 */
	public int getStudentsCount(Department searchDep,ELUser elUser,Map<String,Object> params,int pageNow,int pageSize,String returnIds) throws ElException;
	/**
	 * 章节或者课程考试统计
	 * @param searchDep
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getPageexamInfo(Department searchDep,ELUser elUser,int pageNow,int pageSize,int type) throws ElException;
	/**
	 * 章节或者考试统计size
	 * @param searchDep
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public int getPageexamInfoCount(Department searchDep,ELUser elUser,int pageNow,int pageSize,int type) throws ElException;
	
	/**
	 * 获取用户定级记录
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public ELUserClassification getElUserClassificationByUserid(int userid,int roomid) throws ElException;
	
	/**
	 * 更新用户定级信息记录
	 * @param userid
	 * @param roomid
	 * @param classificationname
	 * @throws ElException
	 */
	public void updateElUserClassificationByUserid(int userid,int roomid,String classificationname,int time) throws ElException;
	
//	/**
//	 * 保存用户定级信息
//	 * @param roomid
//	 * @param classiciationName
//	 * @param userid
//	 * @param type
//	 * @throws ElException
//	 */
//	public void insertToEluserClassification(int roomid,String classiciationName,int userid,int type) throws ElException;
//	
//	/**
//	 * 学员申请重新定级
//	 * @param userid
//	 * @param roomid
//	 * @param type
//	 * @throws ElException
//	 */
//	public void updateEluserClassification(int userid,int roomid,int type) throws ElException;
	
	
	/**
	 * 定级前插入一条异常信息
	 */
	public void addExceptionData(int userid,int roomid,int time) throws ElException;
	
	
	/**
	 * 考试批次分配给该用户
	 * @param userid
	 * @throws ElException
	 */
	public void checkUserIsAssignToErbatch(int userid) throws ElException;
	
	/**
	 * 获取考试批次进度
	 * @param erbatchid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public float getErbatchProcess(int erbatchid,int userid) throws ElException;
	
	/**
	 * 根据考场批次ID获取所有考场
	 * @param erbatchid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listEroomsByErbatchid(int erbatchid,int userid,int pageNow,int pageSize) throws ElException;
	/**
	 * 根据考场批次ID获取所有考场Size
	 * @param erbatchid
	 * @return
	 * @throws ElException
	 */
	public int listEroomsSizeByErbatchid(int erbatchid,int userid) throws ElException;
	
	/**
	 * 分配学拼音培训班
	 * @param userid
	 * @param classid
	 * @throws ElException
	 */
	public void addPinyinClass(int userid,int classid) throws ElException;
	//wjm0212修改
	/**
	 * 定级插入一条异常信息
	 */
	public void addExceptionData_new(int userid,int roomid,int time) throws ElException;
	
	//判断是否已顶级
	public boolean isDingji(int userid) throws ElException;
	
}
