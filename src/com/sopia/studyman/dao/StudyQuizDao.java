package com.sopia.studyman.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.ErPara;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.SimexamPaper;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.UnitRanking;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.Question;
import com.sopia.studyman.entities.MyEprac;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyPractice;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.MyRoomRecord;
import com.sopia.studyman.entities.PointsRecord;

public interface StudyQuizDao {
	public List<MyPractice> listMyPracpapers(int userid, int courseid, int cpid)
			throws ElException;

	public ExamRoom getExamRoomByUandC(int courseid, int userid)
			throws ElException;

	public void saveQuizPaper(MyExamPaper examPaper) throws ElException;

	public int intoQuizPaper(int uid, int roomid, int epid) throws ElException;

	public boolean hasInQuizPaper(int uid, int roomid, int epid)
			throws ElException;

	public void deleteQuiz(int uid, int roomid, int epid) throws ElException;

	public void requiz(int uid, int roomid) throws ElException;

	/**试卷提交（计算得分等）
	 * @param examPaper
	 * @throws ElException
	 */
	public void submitQuizPaper(MyExamPaper examPaper) throws ElException;

	public void setQuizPaperStatus(MyExamPaper examPaper) throws ElException;

	/**
	 * 获取考生答卷
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public MyExamPaper getMyEpById(int id) throws ElException;

	public boolean checkQuizPaper(int uid, int roomid, int status)
			throws ElException;

	public List<SimexamPaper> listMySimEp(int course, int userid)
			throws ElException;

	public boolean checkSimPaper(int uid, int roomid, int status, int cid)
			throws ElException;

	public void saveSimPaper(MyExamPaper examPaper) throws ElException;

	public void intoSimPaper(int uid, int epid, int cid) throws ElException;

	public void submitSimPaper(MyExamPaper examPaper) throws ElException;

	public boolean hasInSimPaper(int uid, int epid, int cid) throws ElException;

	public void resimpaper(int uid, int courseid, int epid) throws ElException;

	public MyExamPaper getMySimEpByUandR(int uid, int epid, int cid)
			throws ElException;

	// public void setFinalScore(int id, float score) throws ElException;

	public void setSimFinalScore(int courseid, int epid, int userid, int score)
			throws ElException;

	public List<MyExamPaper> listSimResult(int userid) throws ElException;

	public List<MyExamPaper> listmyQuizResult(int userid, int courseid)
			throws ElException;

	public MyExamPaper getMyEpByCid(int cid, int uid) throws ElException;

	public void intoPpaper(int ppid, int userid) throws ElException;

	public List<MyExamPaper> listMyRecentQuiz(int userid, int pageNow,
			int pageSize) throws ElException;

	// public List<MyExamPaper> listMyQuiz(int userid, int pageNow, int
	// pageSize)
	// throws ElException;

	public int listMyQuizSize(int userid) throws ElException;

	public List<MyExamPaper> listMypaperByRidanUid(int userid, int roomid)
			throws ElException;

	public List<MyExamPaper> listMyExampapers(int userid, int roomid, int epid)
			throws ElException;
	public MyExamPaper getMyExampaper(int userid, int roomid, int epid)
		throws ElException;

	public List<MyRoom> listErsWithoutC(int userid, int type, int pageNow,
			int pageSize) throws ElException;
	
	public List<MyRoom> listQuesWithoutC(int userid, int type, int pageNow,
			int pageSize) throws ElException;
	
	public List<MyRoom> listBuyErooms(int userid,int type,int pageNow,int pageSize) throws ElException;
	/**
	 * 个人中心我的考试
	 * @param userid
	 * @param number
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> study_index_listErsWithoutC(int userid, int number,boolean ifBuy) throws ElException;

	public List<MyRoom> listErsWithoutC(int userid, int pageNow, int pageSize)
			throws ElException;

	public List<MyRoom> onloadUcenterStudy(int userid)// hwc
			throws ElException;

	public List<ExamRoom> listcanapplyrooms(int userid, int pn, int ps)
			throws ElException;

	public int listcanapplyroomsSize(int userid) throws ElException;

	public MyRoom getMyErsWithoutC(int roomid, int userid) throws ElException;

	public int listErsWithoutCSize(int userid, int type) throws ElException;
	
	public int listQuesWithoutCSize(int userid, int type) throws ElException;
	
	public int listBuyEroomsSize(int userid,int type) throws ElException;

	public int listErsWithoutCSize(int userid) throws ElException;

	// public List<MyExamPaper> listMyQuiz(int userid, int status)
	// throws ElException;

	// public List<MyExamPaper> listMyQuiz(int userid, int status, int pageNow,
	// int pageSize) throws ElException;

	public int listMyQuizSize(int userid, int status) throws ElException;

	public int listMyQuizSize(int userid, int type, String sql)
			throws ElException;

	public int listMyRecentQuizSize(int userid) throws ElException;

	public List<MyExamPaper> quizpapwithoutC_result_list(int roomid, int pn,
			int ps) throws ElException;

	public List<MyRoom> listErsWithoutC_result(int userid, int pageNow,
			int pageSize) throws ElException;

	public List<MyExamPaper> listErsWithoutC_result_detail(int userid,
			int roomid) throws ElException;

	/**
	 * 检查课程答卷是否存在
	 * 
	 * @param ppid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkPpaperIsFinish(int ppid, int userid, int classid)
			throws ElException;

	public List<MyEprac> listmyexamprac(int useid, int begin, int end)
			throws ElException;

	public MyEprac getmyexamprac(int useid, int pracid) throws ElException;

	public MyExamPaper getmyexamprac(int useid, int pracid, long starttime)
			throws ElException;

	public MyExamPaper getmyexampracbyid(int pid) throws ElException;

	public void intomyexamprac(MyEprac eprac) throws ElException;

	public List<MyExamPaper> listMpracExampapers(int pracid, int userid,
			int pn, int ps) throws ElException;

	public boolean checkPracQuestion(int pqiid, Question question)
			throws ElException;

	public void insertPracQuestion(int pqiid, Question question)
			throws ElException;

	public Question getQuestionByPrac(int pqiid, Question q) throws ElException;

	public void updatePracQuestion(int pqiid, Question question)
			throws ElException;

	public void submitPracPaper(MyExamPaper examPaper) throws ElException;

	public int listmyexampracsize(int userid) throws ElException;

	/**获取考试答卷
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getMyExamPaper(int sqid) throws ElException;
	/**获取考试答卷
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getMyExamPaperInfo(int sqid) throws ElException;
	
	/**获取考试答卷获取试题id及 状态
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getMyExamPaper_(int sqid) throws ElException;

	/**获取考试答卷的客观得分
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public float getMyExamPapermepKscore(int sqid) throws ElException;

	/**为答卷插入试题
	 * @param sqid
	 * @param q
	 * @throws ElException
	 */
	public void insertStudyQuestion(int sqid, Question q) throws ElException;

	/**获取练习答卷
	 * @param praqid
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getMyPracPaper(int praqid) throws ElException;

	/**获取操作题内容
	 * @param sqid
	 * @param q
	 * @return
	 * @throws ElException
	 */
	public Question getQuestionByREBid(int sqid, Question q) throws ElException;

	/**
	 * 或考生答卷中某试题的评分规则。
	 * 
	 * @param sqid
	 * @param question
	 * @return
	 * @throws ElException
	 */
	public String getQRulestrByREBid(int sqid, Question question)
			throws ElException;

	/**
	 * 更新答题表中试题信息
	 * 
	 * @param sqid
	 * @param q
	 * @throws ElException
	 */
	public void updateStudyQuestion(int sqid, Question q) throws ElException;
	
	/**
	 * 更新答题表中录音文本
	 * @param sqid
	 * @param q
	 * @throws ElException
	 */
	public void updateStudyQuestionVoiceText(int sqid,Question q) throws ElException;
	
	/**
	 * 获取答题表中语音识别文本
	 * @param sqid
	 * @param q
	 * @return
	 * @throws ElException
	 */
	public String getStudyQuestionVoiceText(int sqid,Question q) throws ElException;

	/**
	 * 检测答卷试题表中是否存在答卷答题中的小题
	 * 
	 * @param sqid
	 * @param q
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyQuestion(int sqid, Question q) throws ElException;

	/**
	 * 检测答卷试题表中是否存在答卷小题序号。
	 * 
	 * @param sqid
	 * @param q
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyQuestionSort(int sqid, Question q)
			throws ElException;

	public MyExamPaper getmyexamqpracbyid(int pid) throws ElException;

	public void intomyexamqprac(MyEprac eprac) throws ElException;

	public List<MyExamPaper> listMpracExamqpapers(int pracid, int userid,
			int pn, int ps) throws ElException;

	public boolean checkqpracQuestion(int pqiid, Question question)
			throws ElException;

	public void insertqpracQuestion(int pqiid, Question question)
			throws ElException;

	public Question getQuestionByqprac(int pqiid, Question q)
			throws ElException;

	public void updateqpracQuestion(int pqiid, Question question)
			throws ElException;

	public void submitqpracPaper(MyExamPaper examPaper) throws ElException;

	public ExamPaper getMyqpracPaper(int praqid) throws ElException;

	/**
	 * @param praqid获取练习答卷信息。
	 * @return
	 * @throws ElException
	 */
	public MyExamPaper getmyexamprac(int praqid) throws ElException;

	public MyEprac getmyexamqprac(int useid, int pracid) throws ElException;

	public MyExamPaper getmyexamqprac(int useid, int pracid, long starttime)
			throws ElException;

	/**
	 * 进入课程练习
	 * 
	 * @param eprac
	 * @throws ElException
	 */
	public void intomycourseprac(MyEprac eprac) throws ElException;

	/**
	 * 检查课程练习中的小题是否存在
	 * 
	 * @param pqiid
	 * @param question
	 * @return
	 * @throws ElException
	 */
	public boolean checkcpracQuestion(int pqiid, Question question)
			throws ElException;

	/**
	 * 删除上次课程练习答卷的大题及小题
	 * 
	 * @param mid
	 * @throws ElException
	 */
	public void deletecpracBlQuestion(int mid) throws ElException;

	/**
	 * 课程练习答卷小题插入
	 * 
	 * @param pqiid
	 * @param question
	 * @throws ElException
	 */
	public void insertcpracQuestion(int pqiid, Question question)
			throws ElException;

	public Question getQuestionBycprac(int pqiid, Question q)
			throws ElException;

	/**
	 * 课程练习答卷小题更新
	 * 
	 * @param pqiid
	 * @param question
	 * @throws ElException
	 */
	public void updatecpracQuestion(int pqiid, Question question)
			throws ElException;

	/**
	 * 提交课程练习。
	 * 
	 * @param examPaper
	 * @throws ElException
	 */
	public void submitcpracPaper(MyExamPaper examPaper) throws ElException;

	/**
	 * 课程练习记录添加
	 * 
	 * @param examPaper
	 * @return
	 * @throws ElException
	 */
	public void addCpracPaper_record(MyExamPaper examPaper) throws ElException;

	/**
	 * 更新课程练习记录
	 * 
	 * @param examPaper
	 * @throws ElException
	 */
	public void updateCpracPaper_record(MyExamPaper examPaper)
			throws ElException;

	/**
	 * 课程练习查看答卷（全部大题，小题信息）
	 * 
	 * @param praqid
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getMycpracPaper(int praqid) throws ElException;

	/**
	 * 课程练习查看答卷（基本信息）
	 * 
	 * @param pracid
	 * @return
	 * @throws ElException
	 */
	public MyExamPaper getmycprac(int pracid) throws ElException;

	/**
	 * 课程练习答卷获取
	 * 
	 * @param useid
	 * @param pracid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public MyExamPaper getmycprac(int useid, int pracid, int classid)
			throws ElException;

	/**
	 * 检测是否已经进入考试
	 * 
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean hasInQuizPaper(int uid, int roomid, int epid, int classid,
			int mrrid) throws ElException;
	
	/**
	 * 检测是否已经进入考试
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean hasInQuizPaper(int uid, int roomid, int epid,int classid)throws ElException;

	/**
	 * 添加study_quizinfo信息(加班级)
	 * 
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @throws ElException
	 */
	public void intoQuizPaper(int uid, int roomid, int epid, int classid)
			throws ElException;

	/**
	 * 显示My结业考试信息
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	// public List<MyExamPaper> listMyQuiz2(int userid, int pageNow, int
	// pageSize)
	// throws ElException;
	/**
	 * 获取学员绑定的mac
	 * 
	 * @return
	 * @throws ElException
	 */
	public String getStudyMacAdr(int userid, int roomid) throws ElException;

	/**
	 * 更新学员绑定的mac
	 * 
	 * @param userid
	 * @param roomid
	 * @param classid
	 * @param macAddr
	 * @throws ElException
	 */
	public void updateStudyMacAddr(int userid, int roomid, String macAddr)
			throws ElException;

	/**
	 * 更新学员ip到数据库
	 * 
	 * @param userid
	 * @param roomid
	 * @param classid
	 * @param ipAddr
	 * @throws ElException
	 */
	public void updateStudyIpAddr(int userid, int roomid, String ipAddr)
			throws ElException;

	/**
	 * 显示My结业考试信息(先显示考场相关信息)
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listMyQuiz3(int userid, int pageNow, int pageSize)
			throws ElException;

	public List<MyExamPaper> listMyQuiz3(int userid, int type, int pageNow,
			int pageSize) throws ElException;

	/**
	 * 获取考场信息
	 */
	public MyRoom getMyErsWithoutC(int roomid, int userid, int iscommon)
			throws ElException;

	/**
	 * 获取考场信息
	 */
	public List<MyRoom> getMyErsWithoutCS(int roomid, List<ELUser> elUsers,
			int iscommon) throws ElException;

	/**
	 * 查看考试成绩（结业考试）
	 * 
	 * @param userid
	 * @param roomid
	 * @param iscommon
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listErsWithoutC_result_detail(int userid,
			int roomid, int iscommon) throws ElException;

	/**
	 * 获取学员的考试（分页）
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> onloadUcenterStudy(int userid, int pageNow, int pageSize)
			throws ElException;

	/**
	 * 获取学员的考试数量
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int onloadUcenterStudyCount(int userid) throws ElException;

	/**
	 * 获取学员所有考核考场数量
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getEroomAllCount(int userid) throws ElException;

	/**
	 * 获取学员未开始的考核考场数量
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getEroomNoCount(int userid) throws ElException;

	/**
	 * 获取考核考场数量（包括已删除）
	 * 
	 * @param userid
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public int listErsWithoutCSize2(int userid, int type) throws ElException;

	/**
	 * 设置分值到试卷的对应题目上
	 * 
	 * @param epid
	 *            试题答卷id
	 * @param qid
	 *            试题id
	 * @param socre
	 *            分值
	 * @throws ElException
	 */
	public void setStudyQuestionScore(int epid, int qid, int blockid,
			float socre) throws ElException;
	
	/**
	 * 设置批语到试卷的对应题目上
	 * 
	 * @param epid
	 *            试题答卷id
	 * @param qid
	 *            试题id

	 * @throws ElException
	 */
	public void setStudyQuestionPiyu(int epid, int qid, int blockid,
			String piyu) throws ElException;

	/**
	 * 设置试卷的大题的总分
	 * 
	 * @param sqid
	 *            试卷id
	 * @param blockid
	 *            题块id
	 * @param socre
	 *            分值
	 * @throws ElException
	 */
	public void setStudyBlocksScore(int sqid, int blockid, float socre)
			throws ElException;

	/**
	 * 考试次数加1
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void setQuizPaperExamCount(int id) throws ElException;// 已废弃

	/**
	 * 考试次数清0
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void setQuizPaperExamCountO(int id) throws ElException;

	/**
	 * 获取该考场参加的人
	 * 
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> geteRoomUserByUid(int roomid, int epid, int classid)
			throws ElException;

	/**
	 * 获取待审核的考场数量
	 * 
	 * @return
	 * @throws ElException
	 */
	public int getEroomEndCount() throws ElException;

	/**
	 * 显示My结业考试信息(先显示考场相关信息)
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	// public List<MyExamPaper> listMyQuiz4(int userid, int type, int pageNow,
	// int pageSize) throws ElException;
	/**
	 * 单纯课程结业考试
	 * 
	 * @param userid
	 * @param type
	 * @param sql
	 * @return
	 * @throws ElException
	 */
	public int listMyQuizSize2(int userid, int type, String sql)
			throws ElException;

	/**
	 * 单纯课程的结业成绩列表
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listMyQuiz4(int userid, int pageNow, int pageSize)
			throws ElException;

	/**
	 * 单纯课程结业成绩数量
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int listMyQuizSize2(int userid) throws ElException;

	/**
	 * 学员自动获取分配给部门的资源(练习)
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_depAssign(int userid, int depid) throws ElException;

	/**
	 * 阅卷后设置版块分值等
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_marking(int mepid) throws ElException;

	/**
	 * 获取该学员某考场的所有记录
	 * 
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public List<MyRoomRecord> listStudyRoomRecord(int userid, int roomid)
			throws ElException;

	/**
	 * 添加学员考场的记录(返回此对象)
	 * 
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public MyRoomRecord addStudyRoomRecord(int userid, int roomid)
			throws ElException;

	/**
	 * 添加学员分配的试卷记录
	 * 
	 * @param userid
	 * @param epid
	 * @throws ElException
	 */
	public void addStudyExamPaper(int userid, int epid, int roomid, int classid)
			throws ElException;

	/**
	 * 检测该学员是否分配了该试卷
	 * 
	 * @param userid
	 * @param epid
	 * @param roomid
	 * @param classid
	 * @throws ElException
	 */
	public boolean checkStudyExamPaper(int userid, int epid, int roomid,
			int classid) throws ElException;

	/**
	 * 获取学员的考场的参加方式
	 * 
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getStudyEroomJoinway(int userid, int roomid) throws ElException;

	/**
	 * 获取学员分配的所有试卷
	 */
	public List<ExamPaper> listStudyExamPaper(int userid, int roomid)
			throws ElException;

	/**
	 * 获取考场答卷信息
	 * 
	 * @param userid
	 * @param roomid
	 * @param mrrid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listMypaperByRidanUid(int userid, int roomid,
			int mrrid) throws ElException;

	public int getMypaperIdByRidanUid(int userid, int roomid, int epid)
			throws ElException;

	/**
	 * 获取考场信息
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public MyRoom getMyErsWithoutR(int roomid, int userid, int iscommon)
			throws ElException;

	/**
	 * 获取该学员某考场的所有记录(包含答卷)
	 * 
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public List<MyRoomRecord> listStudyRoomRecordSqinfo(int userid, int roomid)
			throws ElException;

	/**
	 * 获取某学员在某考场某记录的答卷
	 * 
	 * @param userid
	 * @param roomid
	 * @param mrrid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listStudyQuizInfo(int userid, int roomid, int mrrid)
			throws ElException;

	/**
	 * 更新答题（搜索，打字，邮件）
	 * 
	 * @param sqid
	 * @param q
	 * @throws ElException
	 */
	public void updateStudyQuestionOther(int sqid, Question q)
			throws ElException;

	/**
	 * 获取考场信息
	 * 
	 * @param roomid
	 * @param userid
	 * @param iscommon
	 * @param mrrid
	 * @return
	 * @throws ElException
	 */
	// public MyRoom getMyErsWithoutR(int roomid, int userid, int iscommon,
	// int mrrid) throws ElException;
	/**
	 * 保存答卷时间
	 * 
	 * @param myExamPaper
	 * @throws ElException
	 */
	public void saveQuizPaperPasstime(MyExamPaper myExamPaper)
			throws ElException;

	/**
	 * 获取学员考场的记录数
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getMyEroomRecordCount(int roomid, int userid) throws ElException;

	/**
	 * 设置学员考场状态(包括试卷状态)
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public void setStudyEroomStatus(int roomid, int userid) throws ElException;

	/**
	 * 设置学员试卷所考次数
	 * 
	 * @param userid
	 * @param roomid
	 * @param epid
	 * @throws ElException
	 */
	public void setStudyExampaperQuizcount(int userid, int roomid, int epid)
			throws ElException;
	

	/**
	 * 添加学员考试记录(学员练习，以及课程章节练习记录)
	 * 
	 * @param sqid
	 * @throws ElException
	 */
	public int addStudyQuizinfoRecord(int sqid, String tableName)
			throws ElException;

	/**
	 * 更新学员考试记录(学员练习，以及课程章节练习记录)状态
	 * 
	 * @param sqid
	 * @param status
	 * @param endtime
	 * @param tableName
	 * @throws ElException
	 */
	public void updateStudyQuizinfoRecordStatus(int sqid, int status,
			Timestamp endtime, String tableName) throws ElException;

	/**
	 * 更新该学员所有结束时间为空的考试记录(学员练习，以及课程章节练习记录)
	 * 
	 * @param userid
	 * @param endtime
	 * @param tableName
	 * @throws ElException
	 */
	public void updateStudyQuizinfoRecordStatus(int userid, Timestamp endtime,
			String tableName) throws ElException;

	/**
	 * 显示My结业考试信息(先显示考场相关信息)
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listMyQuiz(int userid, int pageNow, int pageSize)
			throws ElException;

	/**
	 * 添加可申请且需要审核的考场学员报名信息
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public void addStudyRoomApply(int roomid, int userid) throws ElException;

	/**
	 * 检测学员是否已经报名
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyRoomApply(int roomid, int userid)
			throws ElException;

	/**
	 * 更新学员考场报名状态
	 * 
	 * @param erid
	 * @param epid
	 * @param delStatus
	 * @throws ElException
	 */
	public void udpateStudyRoomApplyStatus(int roomid, int userid, int status)
			throws ElException;

	/**
	 * 获取学员已通过的考核考场数量
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getEroomPassedCount(int userid) throws ElException;
	/**
	 * 学员自动获取分配给警种的资源(考场)
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_examJingzhongAssign(int userid) throws ElException;
	/**
	 * 更新学员考场的最后考试时间
	 * @param userid
	 * @param roomid
	 * @param begintime
	 * @throws ElException
	 */
	public void updateStudyExamBegintime(int userid, int roomid,
			Timestamp begintime) throws ElException;
	/**
	 * 添加学员分配的试卷记录
	 * @param userid
	 * @param epid
	 * @param roomid
	 * @param classid
	 * @param isdel
	 * @throws ElException
	 */
	public void addStudyExamPaper(int userid, int epid, int roomid, int classid,int isdel)
			throws ElException;
	/**
	 * 获取学员某考场的所有答卷数量
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getStudyEroomAllCount(int userid,int roomid) throws ElException;
	/**
	 * 检测是否已经阅卷
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudy_score(int userId,int sqid,int blockid,int qid) throws ElException ;
	/**
	 * 阅卷后提交分数
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_scoreAdd(int userId,int sqid,int Blockid,int Qid, float Score) throws ElException ;
	/**
	 * 阅卷后更新分数
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_scoreUpdate(int userId,int sqid,int Blockid,int Qid, float Score) throws ElException ;
	
	/**
	 * 阅卷后提交批语
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_piyuAdd(int userId,int sqid,int Blockid,int Qid, String piyu) throws ElException ;
	/**
	 * 阅卷后更新批语
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_piyuUpdate(int userId,int sqid,int Blockid,int Qid, String piyu) throws ElException ;
	/**
	 * 判断当前用户类型（普通阅卷人员或者阅卷组长和超级管理员）
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public int study_isLeader(int userId,int roomid) throws ElException;
	/**
	 * 非组长调用阅卷显示调用
	 * @param sqid
	 * @param isLeader
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getMyExamPaper(int sqid,int userid) throws ElException;
	/**
	 * 获取考场所有未批阅答卷数量
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getEroomAllQuizcount(int roomid,int epid) throws ElException;
	/**
	 * 获取一份未批阅的答卷（状态为以作答）
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public int getStudyExamPaper(int myExamPaperid,int roomid,int epid) throws ElException;
	/**
	 * 获取用户批阅答卷的数量
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getUserReadexampaperCount(int roomid,int epid,int userid) throws ElException;
	/**
	 * 获取一份未批阅的答卷（状态为以作答）
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public int getStudyExamPaper(int myExamPaperid,int roomid,int epid,int userid) throws ElException;
	
	/**根据报名条件检查考场情况
	 * @param eroomRegistration
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public String checkPassErooms(ErPara erpara,int userid)throws ElException;
	
	/**根据报名条件检查考场试卷情况
	 * @param eroomRegistration
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public String checkPassEroomeps(ErPara erpara,int userid)throws ElException;
	/**设置考试答卷
	 * @param eroomRegistration
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public void setquizinfo(int sqid)throws ElException;
	/**获取大题小题
	 * @param sqid
	 * @param userid
	 * @param epBlock
	 * @return
	 * @throws ElException
	 */
	public List<Question> listQuizQuestions(int sqid,int blockid,int pN)throws ElException;

	/**
	 * 根据roomid得到userid
	 */
	public List<Integer> getuserid(int roomid) throws ElException;
	
	/**
	 * 已获学分的课程数 
	 * @param userid
	 * @param classid
	 * @param classType 培训班的课程有两个表。 自主培训班是CLASS_COURSE_AT 其余培训班是CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getKc_courseXF(int userid, int classid ,String classType)throws ElException;
	
	//------------积分系统
	/**
	 * 考试成绩加分的 平均成绩
	 * @param userid
	 * @param classid
	 * @param classType 培训班的课程有两个表。 自主培训班是CLASS_COURSE_AT 其余培训班是CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public float getKC_CJ_AVG(int userid, int classid ,String classType)throws ElException;
	
	
	/**
	 * 查询学时加分   
	 * @param userid
	 * @param classid
	 * @param classType 培训班的课程有两个表。 自主培训班是CLASS_COURSE_AT 其余培训班是CLASS_COURSE
	 * @return  list  get（1）=已完成学时数 get(2)=超过数
	 * @throws ElException
	 */
	public List getXs_period(int userid, int classid,String classType)throws ElException;
	/**
	 * 获取已做练习的课程数失败
	 * @param userid
	 * @param classid 
	 * @return
	 * @throws ElException
	 */
	public int getLX_course(int userid, int classid)throws ElException;
	/**
	 * 做过课程内模考的课程数量
	 * @param userid
	 * @param classid
	 * @param classType 培训班的课程有两个表。 自主培训班是CLASS_COURSE_AT 其余培训班是CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getMk_Model(int userid, int classid ,String classType)throws ElException;
	
	/**
	 * 已获学分数
	 * @param userid
	 * @param classid
	 * @param classType CLASS_COURSE_AT(自主培训类型) CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getXF_credits(int userid, int classid,String classType)throws ElException;
	/**
	 * 已做笔记的课程数
	 * @param userid
	 * @param classid
	 * @param classType 培训班的课程有两个表。 自主培训班是CLASS_COURSE_AT 其余培训班是CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getBj_course(int userid, int classid ,String classType)throws ElException;
	
	/**
	 * 已发布文章数 或者 已审核知识
	 * @param userid
	 * @param valid   (valid == 1时  获取已审核的知识   == 0 全部知识)
	 * @return
	 * @throws ElException
	 */
	public int getSc_releaseORaudit(int userid , int valid)throws ElException ;
	
	/**
	 * 被推荐的文章数 
	 * @param userid 
	 * @param hot   1 为推荐
	 * @return
	 * @throws ElException
	 */
	public int getBtj_article(int userid , int hot)throws ElException;
	
	/**
	 * 本年度被下载得分   --已审核文章数 
	 * @param userid
	 * @param valid  1 为已审核
	 * @return
	 * @throws ElException
	 */
	public int getBxz_audit(int userid , int valid)throws ElException;
	/**
	 * 本年度被下载得分   下载人次  
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getBxz_people(int userid)throws ElException;
	/**
	 * 下载得分    --下载文章数   
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getXz_audit(int userid)throws ElException;
	
	/**
	 * 发帖数 or 通过数 
	 * @param userid
	 * @param valid  (valid = 1 获取通过的帖子   = 0 不加入该条件。 查询全部帖子)
	 * @return
	 * @throws ElException
	 */
	public int getFt_postORpass(int userid , int valid)throws ElException;
	/**
	 * 发言次数
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getFy_speech(int userid)throws ElException;
	/**
	 * 精华帖数量
	 * @param userid
	 * @param hot  1为精华帖
	 * @return
	 * @throws ElException
	 */
	public int getJh_jht(int userid , int hot)throws ElException;
	/**
	 * 本年度登陆次数
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getDl_login(int userid)throws ElException;
	/**
	 * 验证积分记录表是否存在这条数据
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkPointsRecord(int classid, int userid)
	throws ElException ;
	/**
	 * 修改记录积分
	 * @param precord
	 * @throws ElException
	 */
	public void alterPointsRecord(PointsRecord precord) throws ElException ;
	/**
	 * 增加记录积分
	 * @param precord
	 * @throws ElException
	 */
	public void intoPointsRecord(PointsRecord precord) throws ElException ;
	/**
	 * (只算以考科目)考试成绩加分的 平均成绩
	 * @param userid
	 * @param classid
	 * @param classType 培训班的课程有两个表。 自主培训班是CLASS_COURSE_AT 其余培训班是CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public float getKC_CJ_AVG_(int userid, int classid ,String classType)throws ElException;
	/**
	 * 获取单位积分排行榜-基础综合得分
	 * @param classid
	 * @param depids
	 * @return
	 * @throws ElException
	 */
	public float getBasedScore(int classid, String depids) throws ElException;
	/**
	 * 获取单位积分排行榜-学历层次得分
	 * @param classid
	 * @param depids
	 * @return
	 * @throws ElException
	 */
	public float getDegreeScore(int classid, String depids) throws ElException ;
	/**
	 * 获取单位积分排行榜-职称级别得分
	 * @param classid
	 * @param depids
	 * @return
	 * @throws ElException
	 */
	public float getTitleScore(int classid, String depids) throws ElException ;
	/**
	 * 验证是否又该单位积分排名记录
	 * @param classid
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public boolean checkUnitRank(int classid, int depid)throws ElException;
	/** 
	 * 获取单位积分排名信息  (按培训班 单位 查询)
	 * @param classid
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public UnitRanking getUnitRank(int classid, int depid)throws ElException ;
	/**
	 * 更新单位积分排名信息
	 * @param UnitRank
	 * @throws ElException
	 */
	public void UpdateUnitRank(UnitRanking UnitRank) throws ElException;
	/**
	 * 增加单位积分排名信息
	 * @param UnitRank
	 * @throws ElException
	 */
	public void insertUnitRank(UnitRanking UnitRank) throws ElException ;
	/**
	 * 获取单位积分排名列表信息  (按培训班 查询)
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<UnitRanking> getUnitRanks(int classid)throws ElException;
	/**
	 * 获取单位积分排行榜-学历层次得分详情
	 * @param classid
	 * @param depids
	 * @return
	 * @throws ElException
	 */
	public UnitRanking getDegreeScoreDetails(int classid, String depids) throws ElException;
	/**
	 * 获取单位积分排行榜-职称级别得分详情
	 * @param classid
	 * @param depids
	 * @return
	 * @throws ElException
	 */
	public UnitRanking getTitleScoreDetails(int classid, String depids) throws ElException ;
	
	/**
	 * 根据roomid和userid获取试卷id
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getExamRoomid(int roomid,int userid) throws ElException;
	
	/**
	 * 获取一道小题
	 * @param sqid
	 * @param q
	 * @return
	 * @throws ElException
	 */
	public Question getQuestionBySortBid(int sqid, Question q) throws ElException;
	
	/**
	 * 开始考试记录
	 * @param userid
	 * @param classid
	 * @param courseid
	 * @param pageid
	 * @param myexampaperid
	 * @throws ElException
	 */
	public void quizpaper_begin(int userid,int classid,int courseid,int pageid,int myexampaperid) throws ElException;
	/**
	 * 结束考试记录
	 * @param myexampaperid
	 * @throws ElException
	 */
	public void quizpaper_end(int myexampaperid) throws ElException;
	/**
	 * 得到用户前一次考试时间与当前时间差值
	 */
	public MyExamPaper beforetime_now(int userid,int roomid,int myexampaperid) throws ElException;
	/**
	 * 得到用户当天考试次数
	 */
	public MyExamPaper countforday(int userid,int roomid,int myexampaperid) throws ElException;
	
	
	/**
	 * 允许跳转到下一题的条件
	 * @param myexampaperid
	 * @param blockid
	 * @param questionid
	 * @return
	 * @throws ElException
	 */
	public int checkQuestionCanNext(int myexampaperid,int blockid,int questionid) throws ElException;
	//wsj1023修改--------------------------------------------
	/**
	 * 获取考场答卷信息关联培训班
	 * 
	 * @param userid
	 * @param roomid
	 * @param mrrid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listMypaperByRidanUid(int userid, int roomid,
			int mrrid,int classid) throws ElException;
	/**
	 * 获取考场信息关联培训班
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public MyRoom getMyErsWithoutR(int roomid, int userid, int iscommon,String classid)
			throws ElException ;
	
	/**试卷提交（计算得分等）
	 * @param examPaper
	 * @throws ElException
	 */
	public void submitQuizPaper_wsj(MyExamPaper examPaper,int classid) throws ElException;
	
	/**
	 * 设置学员试卷所考次数
	 * 
	 * @param userid
	 * @param roomid
	 * @param epid
	 * @throws ElException
	 */
	public void setStudyExampaperQuizcount_wsj(int userid, int roomid, int epid,int classid)
			throws ElException;
	
	public int getMypaperIdByRidanUid_wsj(int userid, int roomid, int epid,int classid)
	throws ElException;
	/**
	 * 获取线下考场学员成绩信息
	 */
	public MyRoom getMyStudyRoomInfo(int roomid,int userid,int classid)throws ElException;
	
	public List<Question> getQid(int sqid)throws ElException;
	
	public void addStudyQuestion(int sqid,int qid,String myanswer)throws ElException;
	
	public void updateNoAnswerQz(int sqid, int qid) throws ElException;
}
