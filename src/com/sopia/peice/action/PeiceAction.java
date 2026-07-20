package com.sopia.peice.action;

import java.io.UnsupportedEncodingException;
import java.util.List;


import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.peice.dao.PeiceDao;
import com.sopia.peice.entities.Peice;

public class PeiceAction extends BaseAction {
	private PeiceDao peiceDao;
	private Peice    peice;
	private List<Peice> cp;
	private Course   course;//课程
	private int   stype;//搜索分类
	private CourseType ctype;
	private CourseType ctypeTree;//课程类型树
	private CourseTypeDao ctypeDao;//
	private int      pt;//价格类型
	private float wpeice;
	private String upd;	
	private int biaoshi;//判断是否需要修改
	private int setstatus;//需要修改的状态值
	private String ifadmin;
	
	
	public String getIfadmin() {
		return ifadmin;
	}

	public void setIfadmin(String ifadmin) {
		this.ifadmin = ifadmin;
	}

	public int getSetstatus() {
		return setstatus;
	}

	public void setSetstatus(int setstatus) {
		this.setstatus = setstatus;
	}

	public int getBiaoshi() {
		return biaoshi;
	}

	public void setBiaoshi(int biaoshi) {
		this.biaoshi = biaoshi;
	}

	public String getUpd() {
		return upd;
	}

	public void setUpd(String upd) {
		this.upd = upd;
	}

	public float getWpeice() {
		return wpeice;
	}

	public void setWpeice(float wpeice) {
		this.wpeice = wpeice;
	}

	public int getPt() {
		return pt;
	}

	public void setPt(int pt) {
		this.pt = pt;
	}

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseType getCtype() {
		return ctype;
	}

	public void setCtype(CourseType ctype) {
		this.ctype = ctype;
	}

	public CourseType getCtypeTree() {
		return ctypeTree;
	}

	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}

	public int getStype() {
		return stype;
	}

	public void setStype(int stype) {
		this.stype = stype;
	}

	public List<Peice> getCp() {
		return cp;
	}

	public void setCp(List<Peice> cp) {
		this.cp = cp;
	}



	public Peice getPeice() {
		return peice;
	}

	public void setPeice(Peice peice) {
		this.peice = peice;
	}

	public PeiceDao getPeiceDao() {
		return peiceDao;
	}

	public void setPeiceDao(PeiceDao peiceDao) {
		this.peiceDao = peiceDao;
	}
	
	/**
	 * 课程定价列表
	 * 列表显示该操作人所创建的课程
	 * @return 
	 * @throws ElException 
	 * @throws UnsupportedEncodingException 
	 */
	public String peice_myallcourselist() throws ElException, UnsupportedEncodingException{
		//如果价格状态存在，则修改价格
		if(pt==1||pt==2){
			peice_change();
			pt=0;
		}
		//如果表示=1 则申请审核价格
		if(biaoshi==1){
			peiceDao.peice_Submit(course.getId());
		}
	
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID),"op",ElConstants.TREE_FIANL, true);
		}
		
		if(ctype==null||ctype.getId()<=0){
			ctype=ctypeTree;
		}else{
			ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		String name = course == null ? "" : course.getName();//搜索课程名字
		
		String myUserId = Integer.toString(getSessionIntValue(ElConstants.SESSION_USERID));
		String status="0,9";//创建完成的课程
		cp=peiceDao.getMyAll( ctype , 1 ,name, status, myUserId, stype,role, getPageNow(), getPageSize());
		count = peiceDao.getMyAllSize( ctype , 1 ,name, status, myUserId, stype,role);
		return "peice_myallcourselist_success";
	}
	/**
	 * 课程定价审核列表
	 * 列表显示非制作状态的所有课程
	 * @return
	 * @throws ElException
	 * @throws UnsupportedEncodingException 
	 */
	public String peice_AuditList() throws ElException{
		//如果价格状态存在，则修改价格
		if(pt==1||pt==2){
			peice_change();
			pt=0;
			course=null;
		}
		if(course!=null&&course.getId()>0&&setstatus!=0){//如果setstatus!=0 则审核价格
			int myUserId = getSessionIntValue(ElConstants.SESSION_USERID);
			peiceDao.peice_audit(course.getId(), myUserId,setstatus);
			course=null;
		}
		/**
		 * <bean id="elClassPeiceDao" 

class="com.sopia.elclasspeice.dao.impl.ElClassPeiceDaoImpl"></bean>
		 */
		
		int type = ctype == null || ctype.getId() <= 0 ? -1 : ctype.getId(); 
		int role = getSessionIntValue(ElConstants.SESSION_ROLE);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else { 
			ctypeTree = ctypeDao.getCourseLibTree(getSessionIntValue(ElConstants.SESSION_USERID),"op",ElConstants.TREE_FIANL, true);
		}
		if(ctype==null||ctype.getId()<=0){
			ctype=ctypeTree;
		}else{
			ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		
		String name = course == null ? "" : course.getName();//搜索课程名字
		
	
		String status="0,9";//创建完成的课程
		cp=peiceDao.peice_AuditList(ctype, type, name, status, stype, role, getPageNow(), getPageSize());
		count = peiceDao.peice_AuditListSize(ctype, type, name, status, stype, role);
		return "peice_AuditList_success";
		
	}
	/**
	 * 课程定价修改
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ELException 
	 */
	public void peice_change()throws ElException{
		peiceDao.peice_change(wpeice, course.getId(), pt);
		

	}
	
		
	
	
	
}
