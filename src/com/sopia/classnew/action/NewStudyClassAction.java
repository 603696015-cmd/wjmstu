package com.sopia.classnew.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseCommentDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.statman.dao.ShoppingCartDao;
import com.sopia.studyman.dao.StudyClassDao;

public class NewStudyClassAction extends BaseAction{
	
	private ElClass 			elclass;
	private StudyClassDao 		studyClassDao;
	private CourseComment 		courseComment;
	private List<CourseComment> listcc;
	private CourseCommentDao 	courseCommentDao;
	private int        			ctype ;//评论类型
	private List<Course> 		bxCourses;
	private List<Course> 		xxCourses;
	private ClassDao 			classDao;
	private CourseComment 		userComment;//当前用户提交的评论
	private ShoppingCartDao 	shoppingCartDao;
	private int 				myclass;
	private int 				myclassorder;
	private boolean audit;//审核判定
	private ShoppingDao  	shoppingDao;
	
	
	
	public ShoppingDao getShoppingDao() {
		return shoppingDao;
	}


	public void setShoppingDao(ShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}


	public boolean isAudit() {
		return audit;
	}


	public void setAudit(boolean audit) {
		this.audit = audit;
	}


	public CourseComment getUserComment() {
		return userComment;
	}


	public void setUserComment(CourseComment userComment) {
		this.userComment = userComment;
	}


	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}


	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}


	public int getMyclass() {
		return myclass;
	}


	public void setMyclass(int myclass) {
		this.myclass = myclass;
	}


	public int getMyclassorder() {
		return myclassorder;
	}


	public void setMyclassorder(int myclassorder) {
		this.myclassorder = myclassorder;
	}


	public ClassDao getClassDao() {
		return classDao;
	}


	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}


	public ElClass getElclass() {
		return elclass;
	}


	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}


	public StudyClassDao getStudyClassDao() {
		return studyClassDao;
	}


	public void setStudyClassDao(StudyClassDao studyClassDao) {
		this.studyClassDao = studyClassDao;
	}


	public CourseComment getCourseComment() {
		return courseComment;
	}


	public void setCourseComment(CourseComment courseComment) {
		this.courseComment = courseComment;
	}


	public List<CourseComment> getListcc() {
		return listcc;
	}


	public void setListcc(List<CourseComment> listcc) {
		this.listcc = listcc;
	}


	public CourseCommentDao getCourseCommentDao() {
		return courseCommentDao;
	}


	public void setCourseCommentDao(CourseCommentDao courseCommentDao) {
		this.courseCommentDao = courseCommentDao;
	}


	public int getCtype() {
		return ctype;
	}


	public void setCtype(int ctype) {
		this.ctype = ctype;
	}


	public List<Course> getBxCourses() {
		return bxCourses;
	}


	public void setBxCourses(List<Course> bxCourses) {
		this.bxCourses = bxCourses;
	}


	public List<Course> getXxCourses() {
		return xxCourses;
	}


	public void setXxCourses(List<Course> xxCourses) {
		this.xxCourses = xxCourses;
	}


	/**
	 * 学员申请培训班级列表
	 * 
	 * @return
	 * @throws ElException
	 */  
	public String newclass_view2() throws ElException { 
		audit=SystemConfOp.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		//得到培训班信息
		elclass =shoppingDao.getApplyForeElclassById(elclass.getId());  
		//2得到评论星级信息
//		courseComment=courseCommentDao.getCourseCommentPoint(elclass.getId(),ctype);
		//3得到用户评论信息
		
//		listcc=courseCommentDao.getCourseAllComment(elclass.getId(),ctype,getPageNow6(), getPageSize6());
//		count=courseCommentDao.getCourseAllCommentSize(elclass.getId(),ctype);
		if(shoppingCartDao.checkUserClass(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))myclass=1;
		if(shoppingCartDao.checkUserClassOrder(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))myclassorder=1;
		bxCourses = classDao.listClassCourses(Integer.valueOf(elclass.getId()), CourseConstants.COURSE_STUDY_STATUS_BX);  
		xxCourses = classDao.listClassCourses(Integer.valueOf(elclass.getId()),CourseConstants.COURSE_STUDY_STATUS_XX);
		return "newclass_view2";
	}
	/**
	 * 培训班评论
	 * @return
	 * @throws ElException 
	 */
	public String saveClassComment() throws ElException{
		
		
		//1保存培训班评论信息
		//设置用户id
		userComment.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		//设置评论类型为 培训班
		userComment.setType(2);
		//设置评论状态为审核
		audit=SystemConfOp.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		if(audit){//如果需要审核
			userComment.setStatus(2);
		}else{
			
			userComment.setStatus(1);
			
		}

		//设置培训班id
		userComment.setCourseid(elclass.getId());
		courseCommentDao.saveCourseComment(userComment);
		if(shoppingCartDao.checkUserClass(elclass.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))myclass=1;
		userComment=null;
		return newclass_view2();
	}

}
