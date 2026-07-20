package com.sopia.questionman.dao;

import java.util.List;

import javax.servlet.jsp.el.ELException;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.ExamPaperLib;
import com.sopia.questionman.entities.ExampaperRandom;
import com.sopia.questionman.entities.Question;
import com.sopia.studyman.entities.MyExamPaper;

public interface ExamPaperDao {
	// 试卷管理------------------
	/**
	 * 判断试卷的评分规则
	 */
	public String checkExampaper(int id) throws ElException;
	
	/**
	 * 复制试卷
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public int copyExampaper(int id) throws ElException;
	
	/**
	 * 试卷库树
	 * 
	 * @param userId
	 * @param stopid
	 * @param isContainStop
	 * @return
	 * @throws ElException
	 */
	public ExamPaperLib epLibTree(int id, int userId, int stopid,
			boolean isContainStop) throws ElException;

	public ExamPaperLib epLibTree(String op, int userId, int stopid,
			boolean isContainStop) throws ElException;

	/**
	 * 添加试卷库
	 * 
	 * @param questionLib
	 * @throws ElException
	 */
	public void addepLib(ExamPaperLib examPaperLib) throws ElException;

	/**
	 * 获得id的试卷库
	 * 
	 * @param id
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public ExamPaperLib getEpLById(int id) throws ElException;
	/**
	 * 获取试卷库列表。 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaperLib> getExampaperlib() throws ElException;

	/**
	 * 修改试卷库信息
	 * 
	 * @param qlb
	 * @param userid
	 * @throws ElException
	 */
	public void alterEpl(ExamPaperLib examPaperLib, int userid)
			throws ElException;

	/**
	 * 删除id的试卷库
	 * 
	 * @param id
	 * @param userid
	 * @throws ElException
	 */
	public void deleteEpl(int id) throws ElException;

	/**
	 * 设置上级试卷库
	 * 
	 * @param id
	 * @param parentid
	 * @param userid
	 * @throws ElException
	 */
	public void setEplparent(int id, int parentid, int userid)
			throws ElException;

	/**
	 * 得到直接下级试卷库列表
	 * 
	 * @param id
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaperLib> listEpChild(int id) throws ElException;

	public ExamPaperLib getEPLRoot() throws ElException;

	public int getExamPaperId(String title, int userid) throws ElException;

	/**
	 * 添加试卷
	 * @param examPaper
	 * @throws ElException
	 */
	public void addExamPaper(ExamPaper examPaper) throws ElException;

	// public ExamPaper getExamPaperById(int id, int userid) throws ElException;

	/**按试卷id获取试卷基本信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getExamPaperById(int id) throws ElException;

	public void alterExamPaper(ExamPaper examPaper) throws ElException;

	public List<ExamPaper> listEpsByEplId(int eplid, String title,
			boolean conSub, int pageNow, int pageSize) throws ElException;

	public List<ExamPaper> listEpsByEplId(int eplid, String title,
			boolean conSub,String sqlw, int pageNow, int pageSize) throws ElException;

//	public int listEpsByEpIdSize(int eplid, String title, boolean conSub)
//			throws ElException;
	
	public int listEpsByEpIdSize(int eplid, String title, boolean conSub,String sqlw)
	throws ElException;

	public void deleteExamPaper(int id) throws ElException;
	
	public void setExamPaperStatus(int id,int status) throws ElException;
	
	/**
	 * 设置试卷总题目数量
	 * @param id
	 * @throws ElException
	 */
	public void setExamPaperQuestionTotalCount(int id) throws ElException;
	
	public List<ExamPaper> exampaper_list_listEpsByEplId(ExamPaperLib  eplTree,int eplid, String title,
			boolean conSub, int pageNow, int pageSize)throws ElException ;
	
	public int exampaper_list_listEpsByEpIdSize(ExamPaperLib  eplTree,int eplid, String title, boolean conSub)
	throws ElException;

	/**试卷大题列表
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaperBlock> listEpBlockByEpid(int epid) throws ElException;

	// public List<ExamPaperBlock> listEpBlockByEpidRandom(int epid)
	// throws ElException;
	/**
	 * 根据试卷ID获取设置总分与实际总分
	 * @param 
	 * @throws ElException
	 */
	public ExamPaper getQuestionScoreAndRealScore(int epid) throws ElException;
	/**
	 * 添加大题
	 * @param examPaperBlock
	 * @throws ElException
	 */
	public void addExamPaperBlock(ExamPaperBlock examPaperBlock)
			throws ElException;

	/**
	 * 修改大题
	 * @param examPaperBlock
	 * @throws ElException
	 */
	public void alterExamPaperBlock(ExamPaperBlock examPaperBlock)
			throws ElException;

	// public int getEpBlockMaxSortId(int epid) throws ElException;
	/**
	 * Description: 按id获取大题
	* @Version1.0 2012-7-8 上午10:19:33 by 闻益舜（wenyishun110@163.com）创建
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ExamPaperBlock getEpbById(int id) throws ElException;

	// public ExamPaperBlock getEpbWithRandomById(int id) throws ElException;

	public void deleteEpb(int id) throws ElException;

	/**大题规则为手工出题时，到相关库查找已添加的小题列表
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<Question> listEpBlockQusetionsByBepbId(int id)
			throws ElException;

	/*
	 * public List<Question> listQuestionWithOutSub(String title ,int userid,
	 * int qlbid, int qtype, int pageNow, int pageSize) throws ElException;
	 * public List<Question> listQuestionWithOutSub(String title ,int userid,
	 * int qlbid, int qtype ) throws ElException;
	 */
	public boolean haveTheQuestion(int eqbid, int qid) throws ElException;

	public void addEpbQuestion(int eqbid, int qid, float score)
			throws ElException;

	// public int getEpbQuestionMaxSortid(int eqbid, int qid) throws
	// ElException;
	public void deleteEpbQuestion(int qid, int epbid) throws ElException;

	/**获取材料题小题
	 * @param pid
	 * @return
	 * @throws ElException
	 */
	public List<Question> getQChildbyPid(int pid) throws ElException;

	public void addEpbRandom(ExampaperRandom epb) throws ElException;

	/**大题为随机出题方式时的，试题出题方式列表（题库，难度系数）
	 * @param blockid
	 * @return
	 * @throws ElException
	 */
	public List<ExampaperRandom> listEpbRandom(int blockid) throws ElException;

	public void deleteEpbRandom(int id) throws ElException;

	public void sortEpbQs(int blockid, int sotid, int upordown)
			throws ElException;

	public void sortEpBlock(int epid, int qid, int upordown) throws ElException;

	public boolean checkQuestionSize(int blockid) throws ElException;

	// public int getRandomQSize(int qlib, int type, int level, int subop)
	// throws ElException;
	//
	// public List<Question> getRandomQ(int qlib, int type, int level, int
	// subop)
	// throws ElException;

	public ExampaperRandom getEPRandomById(int id) throws ElException;

	public void alterEpbRandom(ExampaperRandom epb) throws ElException;

	public List<MyExamPaper> listEprquiz(int epid, int pn, int ps)
			throws ElException;

	public int listEprquizSize(int epid) throws ElException;

	public ExamPaperBlock getEpbWithQuestionsById(int id) throws ElException;

	public Question getEpBlockQusetionsByBepbId(int epbid, int id)
			throws ElException;

	public void alterEpBlockQusetionrule(int epbid, int id, String rule)
			throws ElException;

	public void alterEpBlockrule(int epbid, String rule) throws ElException;

	public List<ExampaperRandom> getEPRandomsBy(int qlib, int type, int fwsize)
		throws ElException;
	/**
	 * Description: 获取出题规则中符合条件的各个难度 的数目
	* @Version1.0 2012-7-8 下午03:56:04 by 闻益舜（wenyishun110@163.com）创建
	 * @param qlib
	 * @param type
	 * @param subop
	 * @return
	 * @throws ElException
	 */
	public ExampaperRandom getEPRandomBy(int qlib, int type, int subop)
			throws ElException;
	/**
	 * Description: 获取出题规则中符合条件的各个难度 的数目(打字题)
	* @Version1.0 2012-7-8 下午03:56:04 by 闻益舜（wenyishun110@163.com）创建
	 * @param qlib
	 * @param type
	 * @param subop
	 * @return
	 * @throws ElException
	 */
	public ExampaperRandom getEPRandomBy(int qlib, int type, int subop,int fwsize)
			throws ElException;

	/**生成试卷，按试卷出题规则生成出符合条件的试卷，
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getEPAllInfoById(int id) throws ElException;

	public void addOpusers(String type, int userid, int depid)
			throws ElException;

	public void deleteOpusers(String type, int userid, int depid)
			throws ElException;

	public boolean checkOpUsers(String type, int userid, int depid)
			throws ElException;

	public List<ELUser> getOpUsers(String type, int depid) throws ElException;
	
	public ExamPaper getEPAllInfoById(int id,int roomId) throws ElException;//
	
	public ExamPaper getExamPaperById(int id,int roomId) throws ElException;//hdl
	
	public int getRoomId(int roomId) throws ElException;//
	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException;
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserUseGrant(int userId) throws ElException;
	/**
	 * 根据树查出试卷内容
	 * @param etree
	 * @param eplid
	 * @param title
	 * @param conSub
	 * @param sqlw
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> listEpsByEplId(ExamPaperLib etree,int eplid, String title,
			boolean conSub,String sqlw, int pageNow, int pageSize) throws ElException;
	/*
	 * 根据树查出试卷内容(non-Javadoc)
	 * @see com.sopia.questionman.dao.ExamPaperDao#listEpsByEpIdSize(int, java.lang.String, boolean, java.lang.String)
	 */
	public int listEpsByEpIdSize(ExamPaperLib eplTree,int eplid, String title, boolean conSub,String sqlw)
	throws ElException;
	/**
	 * 设置父id
	 * @param id
	 * @param parentid
	 * @throws ElException
	 */
	public void setEplparent2(int id, int parentid)
	throws ElException;
	/**
	 * 设置试卷父id
	 * @param id
	 * @param parentid
	 * @throws ElException
	 */
	public void setEpparent(int pid, int npid) throws ElException;
	/**
	 * 删除试卷库以及下级试卷库和试卷
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public void deleteEpAndSub(int id) throws ElException;
	/**
	 * 优化过后的 listEpsByEplId
	 * 另补全搜索功能
	 * @param eplid 
	 * @param examPaper
	 * @param conSub
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 * 2012-4-3
	 */
//	public List<ExamPaper> listEpsByEplId(int eplid, ExamPaper examPaper,
//			boolean conSub, int pageNow, int pageSize) throws ElException;
	
	/**
	 * @param dep
	 * @param subdep
	 * @param examPaper
	 * @param pageNow
	 * @param pageSize
	 * @param status 如果status等于0 就把已创建全部查出来
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> listEpsByEplId(ElNode dep, int subdep,ExamPaper examPaper, int pageNow, int pageSize,int status) throws ElException;


//	public int listEpsByEpIdSize(int eplid, ExamPaper examPaper, boolean conSub)
//			throws ElException;
	
	/**
	 * @param dep
	 * @param subdep
	 * @param examPaper
	 * @param status 如果status等于0 就把已创建全部查出来
	 * @return
	 * @throws ElException
	 */
	public int listEpsByEpIdSize(ElNode dep, int subdep, ExamPaper examPaper,int status) throws ElException;
	/**
	 * 获取考场所有的试卷
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> listEroomExamPaper(int roomid) throws ElException;
	/**
	 * 设置试卷为不可编辑
	 * @param epid
	 * @throws ElException
	 */
	public void updateExampaperIseditor(int epid) throws ElException;
	/**
	 * 假删除试卷类别
	 * @param id
	 * @throws ElException
	 */
	public void deleteEpAndSubNot(int id) throws ElException;
	/**
	 * 更新试卷库的状态
	 * @param id
	 * @throws ElException
	 */
	public void deleteEplNot(int id) throws ElException;
	/*
	 * 检测大题题库是否存在
	 */
	public boolean checkEpbRandom(int qlibid, int blockid) throws ElException;
	/**
	 * 更新试卷大题规则
	 * @param epb
	 * @throws ElException
	 */
	public void updateEpbRandom(ExampaperRandom epb) throws ElException;
	/**
	 * 获取考场中用户分配的所有试卷
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> listEroomExamPaper(int roomid,int userid) throws ElException;
	/**
	 * 根据考场id获取到所有该考场试卷
	 * @param roomId
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> getExamPaperByRoomId(int roomId) throws ElException;
	
	/**
	 * 获取试卷分数（用大题和来计算，因为当前只有在保存答卷的时候才会写入答卷成绩）
	 * @param exampaperid
	 * @return
	 * @throws ElException
	 */
	public float getMyEpBlocksScore(int exampaperid) throws ElException; 
	
	/**
	 * 判断题目是否得分
	 * @param qid
	 * @param blockid
	 * @param sqid
	 * @param qindex
	 * @return
	 * @throws ElException
	 */
	public boolean checkMyQuestionIsGetScore(int qid,int blockid,int sqid,int qindex) throws ElException;
	
	/**
	 * 根据试卷ID和blockid获取答错题数量
	 * @param myexampaperid
	 * @param blockid
	 * @return
	 * @throws ElException
	 */
	public int getWrongQuesSizeByBlockid(int myexampaperid,int blockid) throws ElException;
	
	/**
	 * 添加试题到数据库中
	 * @param questions
	 */
	public void addPaperData(int examId,String questions)throws ElException;
	
	/**
	 * 通过试卷ID，音频类型(1为音乐类型，2为阅读)
	 * @param epid
	 * @param type
	 * @return
	 * @throws ELException
	 */
	public List<ExamPaperBlock> listEpBlockByEpidAndType(int epid,int type)throws ElException; 
	
}
