package com.sopia.courseman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.entities.MessageBoard;

public interface CourseCommentDao {
//	/**
//	 * 得到该课程的所有评论信息
//	 * @param courseid
//	 * @return
//	 * @throws ElException
//	 */
//	public  List<MessageBoard> getCourseAllComment(int courseid,int ctype ,int pageNow, int pageSize) throws ElException; 
//	public int getCourseAllCommentSize(int courseid,int ctype ) throws ElException;
//	
//	/**
//	 * 得到评论星级
//	 * @param courseid
//	 * @return
//	 * @throws ElException
//	 */
//	public  MessageBoard  getCourseCommentPoint(int courseid,int ctype) throws ElException; 
//	/**
//	 * 保存评论
//	 * @param ccomment
//	 * @throws ElException
//	 */
//	public void saveCourseComment(MessageBoard message) throws ElException;
	
	
	/**
	 * 得到该课程的所有评论信息
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public  List<CourseComment> getCourseAllComment(int courseid,int ctype ,int pageNow, int pageSize) throws ElException; 
	public int getCourseAllCommentSize(int courseid,int ctype ) throws ElException;
	
	/**
	 * 得到评论星级
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public  CourseComment  getCourseCommentPoint(int courseid,int ctype) throws ElException; 
	/**
	 * 保存评论
	 * @param ccomment
	 * @throws ElException
	 */
	public void saveCourseComment(CourseComment ccomment) throws ElException;
	
	
	/**
	 * 保存对店铺的评论
	 * @param ccomment
	 * @throws ElException
	 */
	public void saveShopComment(CourseComment ccomment) throws ElException;
	
	/**
	 * 得到购买该课程的人员信息
	 */
	public List<ELUser> getEluserByCourseid(int courseid)throws ElException; 
}
