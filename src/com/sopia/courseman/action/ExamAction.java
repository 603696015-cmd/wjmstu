package com.sopia.courseman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.ExamDao;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyExamPaper;

public class ExamAction extends BaseAction {
	private List<ExamRoom> examRooms;
	private ExamDao examDao;
	private ExamRoom examRoom;
	private UserDao userDao;
	private ELUser elUser;
	private List<BaseDatat> jingzhongs;
	private List<BaseDatat> zhiwus;
	private List<BaseDatat> zhijis;
	private List<BaseDatat> gangweis;
	private List<BaseDatat> dishis;
	private List<MyExamPaper> myExamPapers;

	public List<ExamRoom> getExamRooms() {
		return examRooms;
	}

	public void setExamRooms(List<ExamRoom> examRooms) {
		this.examRooms = examRooms;
	}

	public ExamDao getExamDao() {
		return examDao;
	}

	public void setExamDao(ExamDao examDao) {
		this.examDao = examDao;
	}

	public ExamRoom getExamRoom() {
		return examRoom;
	}

	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}

	public List<MyExamPaper> getMyExamPapers() {
		return myExamPapers;
	}

	public void setMyExamPapers(List<MyExamPaper> myExamPapers) {
		this.myExamPapers = myExamPapers;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public List<BaseDatat> getJingzhongs() {
		return jingzhongs;
	}

	public void setJingzhongs(List<BaseDatat> jingzhongs) {
		this.jingzhongs = jingzhongs;
	}

	public List<BaseDatat> getZhiwus() {
		return zhiwus;
	}

	public void setZhiwus(List<BaseDatat> zhiwus) {
		this.zhiwus = zhiwus;
	}

	public List<BaseDatat> getZhijis() {
		return zhijis;
	}

	public void setZhijis(List<BaseDatat> zhijis) {
		this.zhijis = zhijis;
	}

	public List<BaseDatat> getGangweis() {
		return gangweis;
	}

	public void setGangweis(List<BaseDatat> gangweis) {
		this.gangweis = gangweis;
	}

	public List<BaseDatat> getDishis() {
		return dishis;
	}

	public void setDishis(List<BaseDatat> dishis) {
		this.dishis = dishis;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * ¿¼ÊÔ´ð¾íµÄ×éºÏËÑË÷³õÊ¼»¯
	 * @return
	 * @throws ElException
	 */
	public String exam_quiz_seachInit() throws ElException {
		jingzhongs=userDao.getBaseDatatByTypeid(1);
		zhiwus=userDao.getBaseDatatByTypeid(2);
		zhijis=userDao.getBaseDatatByTypeid(3);
		gangweis=userDao.getBaseDatatByTypeid(4);
		dishis=userDao.getBaseDatatByTypeid(5);
		return "exam_quiz_seachInit";
	}
	/**
	 * ¿¼ÊÔ´ð¾í¸Å¿öÍ³¼Æ
	 * @return
	 * @throws ElException
	 */
	public String exam_quiz_Overview() throws ElException {
		if(examRooms!=null){
			String ids="";
			for (int i = 0; i < examRooms.size(); i++) {
				if(i==examRooms.size()-1){
					ids+=examRooms.get(i).getId()+"";
				}else{
					ids+=examRooms.get(i).getId()+",";
				}
			}
			examRoom=examRoom==null?new ExamRoom():examRoom;
			examRoom.setEroomIds(ids);
		}
		examRoom=examDao.getExamQuizOverview(examRoom, elUser);
		return "exam_quiz_Overview";
	}
	/**
	 * ¿¼ÊÔ´ð¾í¸Å¿öÏêÇé
	 * @return
	 * @throws ElException
	 */
	public String exam_quiz_Detail() throws ElException {
		if(examRooms!=null){
			String ids="";
			for (int i = 0; i < examRooms.size(); i++) {
				if(i==examRooms.size()-1){
					ids+=examRooms.get(i).getId()+"";
				}else{
					ids+=examRooms.get(i).getId()+",";
				}
			}
			examRoom=examRoom==null?new ExamRoom():examRoom;
			examRoom.setEroomIds(ids);
		}
		myExamPapers=examDao.getExamQuizDetail(examRoom, elUser,getPageNow(), getPageSize());
		count=examDao.getExamQuizDetailSize(examRoom, elUser);
		return "exam_quiz_Detail";
	}
}
