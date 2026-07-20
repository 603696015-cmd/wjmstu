package com.sopia.pfms.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.CourseComment;

public interface MessageBoardDao {
	
	/**
	 * 我的留言列表
	 * @param type1	如果type1是1表示查询的是对商品的留言
	 * @param courseComment
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws ElException
	 */
	public List<CourseComment> messageList(int type1,CourseComment courseComment,int userid,int pageNow,int pageSize,Timestamp starttime,Timestamp endtime) throws ElException;
	
	/**
	 * 我的留言列表 如果type1是1表示查询的是对商品的留言
	 * @param type1
	 * @param courseComment
	 * @param userid
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws ElException
	 */
	public int messageCount(int type1,CourseComment courseComment,int userid,Timestamp starttime,Timestamp endtime) throws ElException;
	
	
	/**
	 * 根据编号删除留言
	 * @param id
	 * @throws ElException
	 */
	public void deleMessageComment(int id) throws ElException;
	
	
	/**
	 * 审核通过 
	 * @param courseComment
	 * @throws ElException
	 */
	public void courseCommentPass(CourseComment courseComment) throws ElException;
	
	/**
	 * 审核不通过 
	 * @param courseComment
	 * @throws ElException
	 */
	public void courseCommentNotPass(CourseComment courseComment) throws ElException;
	
	/**
	 * 全部留言列表
	 * @param courseComment
	 * @param pageNow
	 * @param pageSize
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws ElException
	 */
	public List<CourseComment> allMessageList(CourseComment courseComment,int pageNow,int pageSize,Timestamp starttime,Timestamp endtime) throws ElException;

	/**
	 * 全部留言列表
	 * @param courseComment
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws ElException
	 */
	public int allMessageCount(CourseComment courseComment,Timestamp starttime,Timestamp endtime) throws ElException;
}
