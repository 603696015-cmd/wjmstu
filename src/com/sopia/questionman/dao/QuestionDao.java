package com.sopia.questionman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionArt;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.studyman.entities.MyExamPaper;

public interface QuestionDao {

	/**
	 * 题库树
	 * 
	 * @param userId
	 * @param stopid
	 * @param isContainStop
	 * @return
	 * @throws ElException
	 */
	public QuestionLib getQlibTree(int id, int userId, int stopid,
			boolean isContainStop) throws ElException;

	public QuestionLib getQlibTree(int userId, String op, int stopid,
			boolean isContainStop) throws ElException;

	public QuestionLib getQLbRoot() throws ElException;

	public int getQlibId(String name, int userid) throws ElException;

	
	/**
	 * 求出材料小题加起来的百分比
	 * @param id
	 * @return
	 */
	public int minorproblem_Sum_scroe(int id) throws ElException ;

	/**
	 * 添加题库
	 * 
	 * @param questionLib
	 * @throws ElException
	 */
	public void addQuestionLib(QuestionLib questionLib) throws ElException;

	public void setQLBparent(int id, int parentid, int userid)
			throws ElException;

	// public int addQLib(QuestionLib questionLib) throws ElException;

	/**
	 * 获得id的题库
	 * 
	 * @param id
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public QuestionLib getQLbById(int id) throws ElException;

	/**
	 * 修改题库信息
	 * 
	 * @param qlb
	 * @param userid
	 * @throws ElException
	 */
	public void alterQLB(QuestionLib qlb) throws ElException;

	/**
	 * 删除id的题库
	 * 
	 * @param id
	 * @param userid
	 * @throws ElException
	 */
	public void deleteQLB(int id, int userid) throws ElException;

	public List<QuestionLib> listChild(int id, int userid) throws ElException;

	public List<QuestionLib> listQlibs(int roleid, int userid)
			throws ElException;

	/**
	 * 添加试题
	 * 
	 * @param question
	 * @throws ElException
	 */
	public void addQuestion(Question question) throws ElException;
	public int getLowerIdById(int parentid , String name) throws ElException;
	/**
	 * 试题列表，不包含下级目录
	 * 
	 * @param userid
	 * @param qlbid
	 * @param qtype
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */

	public List<Question> listMyQuestions(String title, int libfrom, int type,
			boolean conSub, int pn, int ps) throws ElException;
	/**
	 *  无分页用于导出
	 */
	public List<Question> listMyQuestions(String title, int libfrom, int type,
			boolean conSub) throws ElException;
	
	public List<Question> listMyQuestions(String title, int libfrom, int type,
			boolean conSub,String sqlw, int pn, int ps) throws ElException;

	public int listMyQuestionsSize(String title, int libfrom, int type,
			boolean conSub) throws ElException;
	
	public int listMyQuestionsSize(String title, int libfrom, int type,
			boolean conSub,String sqlw) throws ElException;

	public List<Question> question_list_listMyQuestions(QuestionLib qlbTree,String title, int libfrom, int type,
			boolean conSub, int pn, int ps) throws ElException;
	
	public List<Question> question_list_listMyQuestions(QuestionLib qlbTree,String title, int libfrom, int type,
			boolean conSub) throws ElException;
	
	public int question_list_listMyQuestionsSize(QuestionLib qlbTree,String title, int libfrom, int type,
			boolean conSub) throws ElException;

	public List<Question> listQuestionWithOutSubLevel(String title, int userid,
			int qlbid, int qtype, int level) throws ElException;

	public Question getQbyId(int id) throws ElException;

	public void setQuestionStatus(int id ,int status) throws ElException;
	
	public void alterQuestion(Question question) throws ElException;
	public void alterQuestionScorepre(Question question) throws ElException ;
	public void deleteQuestion(int qid) throws ElException;

	/**
	 * 通过材料题id获取材料题小题
	 * @param pid
	 * @return
	 * @throws ElException
	 */
	public List<Question> getQChildbyPid(int pid) throws ElException;

	public Question getQuestionByid(int id) throws ElException;

	public Question getQuestionByid(int id, int blockid, int random)
			throws ElException;

	public int getMaxQsort(int qid) throws ElException;

	public void questionChildSort(int qid, int sortid, int upordown)
			throws ElException;

	public void questionChildDelete(int id) throws ElException;

//	public int addQstuff(StuffLib qs) throws ElException;

//	public List<StuffLib> getStuffs(StuffLib stuff, int userid, int pageNow,
//			int pageSize) throws ElException;

//	public int getStuffsCount(StuffLib stuff, int userid) throws ElException;

//	public List<StuffLib> listStuffs(StuffLib stuff, String order, String ot)
//			throws ElException;

//	public List<StuffLib> listMyStuffs(StuffLib stuff, int userid)
//			throws ElException;

//	public void setStuffShared(int stuffid,int shared) throws ElException;
//	public void setStuffsize(int stuffid,long size) throws ElException;
//	public long getStuffParentSize(int id)throws ElException;
//	public long getStuffChildsSize(int id)throws ElException;
//	public int getStuffOpStatus(int stuffid,int userid,int roleid,int type)throws ElException;
//	public List<ELUser> getStuffOpUsers(int stuffid) throws ElException;

//	public void addStuffOpusers(int userid, int stuffid) throws ElException;

//	public void deleteStuffOpusers(int userid, int stuffid) throws ElException;

//	public boolean checkStuffOpUsers(int userid, int stuffid)
//			throws ElException;

//	public StuffLib getStuffbyId(int id, int userid) throws ElException;

//	public void setStuffParent(StuffLib stuffLib) throws ElException;

//	public void setStuffParent(StuffLib stuffLib, List<StuffLib> list)
//			throws ElException;

//	public StuffLib getStuffFolderTree() throws ElException;

//	public StuffLib getStuffFolderTree(int userid) throws ElException;
//	public StuffLib listFolderShared() throws ElException;
//	public void alter(StuffLib qs) throws ElException;

//	public void deleteQs(int id, int userid) throws ElException;

	public void addOpusers(String type, int userid, int depid)
			throws ElException;

	public void deleteOpusers(String type, int userid, int depid)
			throws ElException;

	public boolean checkOpUsers(String type, int userid, int depid)
			throws ElException;

	public List<ELUser> getOpUsers(String type, int depid) throws ElException;

	public List<QuestionArt> listQarts(String title, int begin, int end)
			throws ElException;

	public int listQartsSize(String title) throws ElException;

	public void deleteQart(int id) throws ElException;

	public void alterQart(QuestionArt qart) throws ElException;

	public QuestionArt getQart(int id) throws ElException;

	public void addQart(QuestionArt qart) throws ElException;

	// 导出试题
	public List<Question> getQuestionList(Question question) throws ElException;

	// 导出试题库
	public List<QuestionLib> getQuestionlibList() throws ElException;
	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException;
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserUseGrant(int userId) throws ElException;
	/**
	 * 删除用户可使用的权限
	 */
//	public void deleteStuffUseusers(int userId) throws ElException;
	/**
	 * 用于材料题的添加小题 
	 * @param title
	 * @param libfrom
	 * @param type
	 * @param conSub
	 * @param pn
	 * @param pS
	 * @return
	 * @throws ElException
	 */
	public List<Question> listMyQuestions2(String title, int libfrom, int type,
			boolean conSub, int pn, int pS) throws ElException;
	/**
	 * 用于材料题的添加小题 
	 * @param title
	 * @param libfrom
	 * @param type
	 * @param conSub
	 * @param pn
	 * @param pS
	 * @return
	 * @throws ElException
	 */
	public int listMyQuestionsSize2(String title, int libfrom, int type,
			boolean conSub) throws ElException;
	/**
	 * 用于材料题的添加小题
	 * @param qlbTree
	 * @param title
	 * @param libfrom
	 * @param type
	 * @param conSub
	 * @return
	 * @throws ElException
	 */
	public List<Question> question_list_listMyQuestions2(QuestionLib qlbTree,
			String title, int libfrom, int type, boolean conSub, int pn, int pS)
			throws ElException;
	/**
	 * 用于材料题的添加小题
	 * @param qlbTree
	 * @param title
	 * @param libfrom
	 * @param type
	 * @param conSub
	 * @return
	 * @throws ElException
	 */
	public int question_list_listMyQuestionsSize2(QuestionLib qlbTree,
			String title, int libfrom, int type, boolean conSub)
			throws ElException;
	/**
	 * 按id串查询。 
	 * @param ids 
	 * @return
	 * @throws ElException
	 */
	public List<Question> getselectQbyIds(String ids,int pageNow,int pageSize) throws ElException ;
	public int getselectQbyIdsSize(String ids) throws ElException;
	/**
	 * 用于选择试题导出
	 */
	public List<Question> getselectQbyIds(String ids) throws ElException;
	/**
	 * 设置上级题库
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void setQLBparent2(int pid, int npid) throws ElException;
	/**
	 * 设置上级试题
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void setQLparent(int pid, int npid) throws ElException;
	/**
	 * 删除试题库
	 * @param id
	 * @throws ElException
	 */
	public void deleteQlibAndSub(int id) throws ElException;
	/**
	 * 优化加搜索后的listMyQuestions
	 * @param question
	 * @param libfrom
	 * @param conSub
	 * @param pn
	 * @param pS
	 * @return
	 * @throws ElException
	 */
//	public List<Question> listMyQuestions(Question question, int libfrom,boolean conSub, int pn, int pS) throws ElException;
	public List<Question> listMyQuestions(ElNode dep, int subdep,Question q, int pageNow, int pageSize) throws ElException;
	/**
	 * Description: 试题导出节点符合天剑的试题导出查询
	* @Version1.0 2012-7-9 下午02:44:06 by 闻益舜（wenyishun110@163.com）创建
	 * @param dep
	 * @param subdep
	 * @param q
	 * @return
	 * @throws ElException
	 */
	public List<Question> listMyQuestions(ElNode dep, int subdep,Question q) throws ElException;
//	public int listMyQuestionsSize(Question question, int libfrom,boolean conSub) throws ElException;
	public int listMyQuestionsSize(ElNode dep, int subdep, Question question) throws ElException;
	/**
	 * 无分页用于导出(优化加搜索后的listMyQuestions)
	 * @param question
	 * @param libfrom
	 * @param conSub
	 * @return
	 * @throws ElException
	 */
	public List<Question> listMyQuestions(Question question, int libfrom,boolean conSub) throws ElException;
	/**
	 * 获取素材的信息以及所有父信息
	 * @param id
	 * @param userid
	 * @return
	 * @throws ElException
	 */
//	public StuffLib getStuffbyId2(int id, int userid) throws ElException;
	/**
	 * 判断地址是否本机
	 * @param url
	 * @param contextPath
	 * @return
	 * @throws ElException
	 */
	public boolean checkUrlIsLocal(String url,String contextPath,String serverName) throws ElException;
	/**
	 * 获取题库的左右id
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public QuestionLib getQuestionLibLRid(int id) throws ElException;
	/**
	 * 查询考场里面所有的试题
	 * @param question
	 * @return
	 * @throws ElException
	 */
	public List<Question> listEroomQuestion(int roomid,Question question,int pageNow,int pageSize) throws ElException;
	/**
	 * 查询考场里面所有的试题数量
	 * @param question
	 * @return
	 * @throws ElException
	 */
	public int listEroomQuestionSize(int roomid,Question question) throws ElException;
	/**
	 * 根据考场id和题目id获取这个考场，这道题所在的答卷
	 * @param roomid
	 * @param qid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listSqidByRidQid(int roomid,int qid) throws ElException;
	/**
	 * 根据答卷和题目id更新学员所作答的题目分数
	 * @param qid
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public void updateStudyQuestionScore(int qid,int sqid,float score) throws ElException;
	/**
	 * 更新题库类别状态
	 * @param id
	 * @param userid
	 * @throws ElException
	 */
	public void deleteQLibNot(int id) throws ElException;
	/**
	 * 删除试题库
	 * @param id
	 * @throws ElException
	 */
	public void deleteQlibAndSubNot(int id) throws ElException;
	/**
	 * 查询试题库，标题后加题库内题目数量
	 * @param id
	 * @param userId
	 * @param stopid
	 * @param isContainStop
	 * @param sublibs 是否包含下级
	 * @return
	 * @throws ElException
	 */
	public QuestionLib getQlibTree(int id, int userId, int stopid,
			boolean isContainStop,int sublibs,int qtype) throws ElException;
	/**
	 * 查询试题库，标题后加题库内题目数量(非超级管理员通道)
	 * @param userid
	 * @param op
	 * @param stopid
	 * @param isContainStop
	 * @param sublibs
	 * @param qtype
	 * @return
	 * @throws ElException
	 */
	public QuestionLib getQlibTree(int userid, String op, int stopid,
			boolean isContainStop,int sublibs,int qtype) throws ElException;
	/**
	 * 检测试题是否重复
	 * @param question
	 * @throws ElException
	 */
	public boolean checkQuestionIsRepeat(Question question) throws ElException;
	
//---------------------------------wjm 1013修改------------------------------------------
	public List<Question> listMyQuestions_wjm(String title, int libfrom, int type,
			boolean conSub,String sqlw, int pn, int ps) throws ElException;

	public int listMyQuestionsSize_wjm(String title, int libfrom, int type,
			boolean conSub,String sqlw) throws ElException;
}
