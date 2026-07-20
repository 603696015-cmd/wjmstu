package com.sopia.pfms.action;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.message.dao.MessageManagementDao;
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.MessageBoardDao;

public class MessageBoardAction extends BaseAction {
	
	public MessageBoardDao messageBoardDao;
	public BaoxianProductDao baoxianProductDao;
	private MessageManagementDao   mmDao;
	public List<CourseComment> messageList;
	public int count;
	public CourseComment courseComment;
	public int roleId;
	private Timestamp starttime;
	private Timestamp endtime;
	private int id;
	private int type;//判断全部列表
	private int type1;//判断是对店铺的留言还是对店铺商品的留言
	
	public String myMessageList() throws ElException{
		messageList = messageBoardDao.messageList(type1,courseComment,getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize(),starttime,endtime);
		count = messageBoardDao.messageCount(type1,courseComment,getSessionIntValue(ElConstants.SESSION_USERID),starttime,endtime);
		return "myMessageList";
	}
	
	public String allMessageList() throws ElException{
		messageList = messageBoardDao.allMessageList(courseComment,getPageNow(), getPageSize(),starttime,endtime);
		count = messageBoardDao.allMessageCount(courseComment,starttime,endtime);
		return "allMessageList";
	}
	
	public String deleMessageComment() throws ElException{
		messageBoardDao.deleMessageComment(courseComment.getId());
		String resultPage = "";
		if(type == 1){
			resultPage = "deleMessageComment_inall_success";
		}else{
			resultPage = "deleMessageComment_success";
		}
		return resultPage;
	}
	
	public String courseCommentPass() throws ElException{
		messageBoardDao.courseCommentPass(courseComment);
		String resultPage = "";
		if(type == 1){
			resultPage = "courseCommentPass_inall_success";
		}else{
			resultPage = "courseCommentPass_success";
		}
		return resultPage;
	}
	
	public String courseCommentNotPass() throws ElException{
		messageBoardDao.courseCommentNotPass(courseComment);
		String resultPage = "";
		if(type == 1){
			resultPage = "courseCommentNotPass_inall_success";
		}else{
			resultPage = "courseCommentNotPass_success";
		}
		return resultPage;
	}
	
	public String showMessageInit() throws ElException{
		courseComment=mmDao.commentView(courseComment.getId());
		return "showMessageInit_success";
	}


	public MessageBoardDao getMessageBoardDao() {
		return messageBoardDao;
	}


	public void setMessageBoardDao(MessageBoardDao messageBoardDao) {
		this.messageBoardDao = messageBoardDao;
	}


	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}


	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
	}


	public List<CourseComment> getMessageList() {
		return messageList;
	}


	public void setMessageList(List<CourseComment> messageList) {
		this.messageList = messageList;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}

	public CourseComment getCourseComment() {
		return courseComment;
	}


	public void setCourseComment(CourseComment courseComment) {
		this.courseComment = courseComment;
	}


	public int getRoleId() {
		return roleId;
	}


	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


	public Timestamp getStarttime() {
		return starttime;
	}


	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}


	public Timestamp getEndtime() {
		return endtime;
	}


	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType1() {
		return type1;
	}

	public void setType1(int type1) {
		this.type1 = type1;
	}

	public MessageManagementDao getMmDao() {
		return mmDao;
	}

	public void setMmDao(MessageManagementDao mmDao) {
		this.mmDao = mmDao;
	}
	
	

}
