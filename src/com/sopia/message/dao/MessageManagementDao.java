package com.sopia.message.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.courseman.entities.CourseType;

public interface MessageManagementDao {
	/**
	 * 得到图书用户留言留言列表
	 * @param type
	 * @param tree
	 * @param btid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	public  List<CourseComment>   userCommentList(CourseType  tree,CourseComment    courseComment, Timestamp  start,Timestamp end , int pageNow, int pageSize)throws ElException;
	public  int   userCommentListSize(CourseType  tree,CourseComment courseComment, 
			Timestamp  start,Timestamp end)throws ElException;
	/**
	 * 审核用户留言
	 * @param id
	 * @param type
	 * @throws ElException
	 */
	public  void     auditUserComment(int id)throws ElException;
	/**
	 * 删除用户留言
	 * @param id
	 * @param type
	 * @throws ElException
	 */
	public  void     deleUserComment(int id)throws ElException;
	/**
	 * 通过id查询课程留言
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public CourseComment    commentView(int id) throws ElException;
	/**
	 * 获得培训班留言
	 * @param tree
	 * @param courseComment
	 * @param start
	 * @param end
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<CourseComment> userClassCommentList(ElNode tree ,CourseComment courseComment, 
			Timestamp  start,Timestamp end,int pageNow, int pageSize) throws ElException ;
	public int userClassCommentListSize(ElNode tree ,CourseComment courseComment, 
			Timestamp  start,Timestamp end) throws ElException ;
	
	
}
