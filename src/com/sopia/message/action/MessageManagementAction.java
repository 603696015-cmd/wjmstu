package com.sopia.message.action;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.bookinfo.dao.BookInfoDao;
import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.bookinfo.entities.Bookinfo;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.common.CheckHtml;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.courseman.entities.CourseType;
import com.sopia.message.dao.MessageManagementDao;

public class MessageManagementAction extends  BaseAction {
	private   int typeid;//哪一种商品
	private   int type;//类别id
	private   int userComment;//留言id
	private BookInfoDao bookInfoDao; 
	private BookTypeTree bookTypeTree;
	private BookTypeTree btype;
	private MessageManagementDao   mmDao;
	private List<CourseComment>   lc;//评论列表
	private CourseComment         courseComment;//评论
	private CourseType ctypeTree;
	private CourseType ctype;
	private CourseTypeDao ctypeDao;
	private Timestamp  start;//修改开始时间
	private Timestamp  end;//修改结束时间
	private ElClType cltypeTree;
	private ElClType cltype;
	private ElClTypeDao elClTypeDao;
	private int          flag;
	
	
	
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}
	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}
	public ElClType getCltypeTree() {
		return cltypeTree;
	}
	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}
	public ElClType getCltype() {
		return cltype;
	}
	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}
	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}
	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	public MessageManagementDao getMmDao() {
		return mmDao;
	}
	public void setMmDao(MessageManagementDao mmDao) {
		this.mmDao = mmDao;
	}
	public List<CourseComment> getLc() {
		return lc;
	}
	public void setLc(List<CourseComment> lc) {
		this.lc = lc;
	}
	public CourseComment getCourseComment() {
		return courseComment;
	}
	public void setCourseComment(CourseComment courseComment) {
		this.courseComment = courseComment;
	}
	public CourseType getCtypeTree() {
		return ctypeTree;
	}
	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}
	public CourseType getCtype() {
		return ctype;
	}
	public void setCtype(CourseType ctype) {
		this.ctype = ctype;
	}
	public BookInfoDao getBookInfoDao() {
		return bookInfoDao;
	}
	public void setBookInfoDao(BookInfoDao bookInfoDao) {
		this.bookInfoDao = bookInfoDao;
	}
	public BookTypeTree getBookTypeTree() {
		return bookTypeTree;
	}
	public void setBookTypeTree(BookTypeTree bookTypeTree) {
		this.bookTypeTree = bookTypeTree;
	}
	public BookTypeTree getBtype() {
		return btype;
	}
	public void setBtype(BookTypeTree btype) {
		this.btype = btype;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getUserComment() {
		return userComment;
	}
	public void setUserComment(int userComment) {
		this.userComment = userComment;
	}
	
	/**
	 * 得到课程信息的用户评论列表
	 * @return
	 * @throws Throwable
	 */
	public  String   courseuserCommentList_init() throws ElException{
		ctypeTree = ctypeDao.getCourseLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		if(ctype==null||ctype.getId()<=0){
			ctype=ctypeTree;
		}else{
			ctype=ctypeDao.getCtypeById(ctype.getId());
		}
		lc=mmDao.userCommentList( ctype,courseComment ,start,end,getPageNow(),getPageSize());
		for (CourseComment c : lc) {
			//截取长度
			c.setContent((c.getContent().length() > 20) ? c.getContent().substring(0, 17)+ "..." : c.getContent()) ;
		}
		count = mmDao.userCommentListSize( ctype,courseComment ,start,end);
		return  "courseuserCommentList_init_success";
	}
	/**
	 * 审核留言
	 * @return
	 * @throws ElException
	 */
	public String   courseuserCommentAudit() throws ElException{		
		mmDao.auditUserComment(courseComment.getId());
		if(flag==0){
			return courseuserCommentList_init();
		}else{
			return classserCommentList_init();
			
		}
	}
	/**
	 * 删除留言
	 * @return
	 * @throws ElException
	 */
	public String   deleuserComment() throws ElException{
		mmDao.deleUserComment(courseComment.getId());
		if(flag==0){
			return courseuserCommentList_init();
		}else{
			return classserCommentList_init();
		}
	}
	/**
	 * 浏览留言
	 * @return
	 * @throws ElException
	 */
	public  String  courseuserCommentView() throws ElException{
		
		courseComment=mmDao.commentView(courseComment.getId());
	
			return  "courseuserCommentView_success";

	}
	
	
	/**
	 * 得到培训班信息的用户评论
	 * @return
	 * @throws Throwable
	 */
	public  String   classserCommentList_init() throws ElException{
		cltypeTree = elClTypeDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		

		if (cltype == null || cltype.getId() <= 0) {
				cltype = cltypeTree;
		} else {
			cltype = elClTypeDao.getClTypeById(cltype.getId());
		}
		lc=mmDao.userClassCommentList(cltype, courseComment, start, end,getPageNow(),getPageSize());
		for (CourseComment c : lc) {
			//截取长度
			c.setContent((c.getContent().length() > 120) ? c.getContent().substring(0, 117)+ "..." : c.getContent()) ;
		}
		count = mmDao.userClassCommentListSize(cltype, courseComment, start, end);
		return  "classserCommentList_init_success";
	}

	
	
	
	
	

}
