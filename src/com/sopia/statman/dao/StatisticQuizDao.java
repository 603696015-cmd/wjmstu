package com.sopia.statman.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.ErepBlock;
import com.sopia.courseman.entities.EroomBatch;
import com.sopia.courseman.entities.EroomBlock;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.entities.MyBatchRoom;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyEprac;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;

public interface StatisticQuizDao {
	public MyExamPaper getMyEpByCid(int cid, int uid) throws ElException;

//	public List<ExamRoom> listquziseach(ExamRoom er) throws ElException;

	public List<ExamRoom> listquziseach(ExamRoom er, EroomLib eroomTree,
			int role,Department dep, int pageNow, int pageSize) throws ElException;// hwc

	public int listquziseachCount(ExamRoom er, EroomLib eroomTree, int role,Department dep)
			throws ElException;// hwc

//	public int listquziseachCount(ExamRoom er) throws ElException;

//	public List<ExamRoom> listquziseach(ExamRoom er, EroomLib eroomLibTree,
//			int[] libids, int pageNow, int pageSize) throws ElException;

	// public ExamRoom getERbyId(int id) throws ElException;
//	public int listquziseachCount(ExamRoom er, EroomLib eroomLibTree,
//			int[] libids) throws ElException;

	public int getERUserByDepidAndERid(int roomid, int depid)
			throws ElException;

	/**
	 * Description: 学员考场（详情）列表
	 * 
	 * @Version1.0 2012-7-2 下午12:05:19 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquiz_detail_view(int roomid) throws ElException;

	/**
	 * Description: 成绩汇总，人员，试卷成绩等信息输出 分页显示
	 * 
	 * @Version1.0 2012-7-17 下午04:25:06 by 闻益舜（wenyishun110@163.com）创建
	 * @param depTree
	 * @param depid
	 * @param roomid
	 * @param role
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquiz_detail_view_Page(Department depTree,
			int depid, int roomid, int role, int pageNow, int pageSize)
			throws ElException;// hwc

	/**
	 * Description:成绩汇总，人员，试卷成绩等信息输出
	 * 
	 * @Version1.0 2012-7-17 下午04:24:19 by 闻益舜（wenyishun110@163.com）创建
	 * @param depTree
	 * @param depid
	 * @param roomid
	 * @param role
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquiz_detail_view_Page(Department depTree,
			int depid, int roomid, int role) throws ElException;// hwc
	/**带搜索条件
	 * @param depTree
	 * @param depid
	 * @param room
	 * @param role
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquiz_detail_view_Page(Department depTree,
			int depid, ExamRoom room,String ids ) throws ElException;// hwc

	/**
	 * Description: 成绩汇总，人员，试卷成绩等信息输出 参考人员数量
	 * 
	 * @Version1.0 2012-7-17 下午04:24:44 by 闻益舜（wenyishun110@163.com）创建
	 * @param depTree
	 * @param depid
	 * @param roomid
	 * @param role
	 * @return
	 * @throws ElException
	 */
	public int listquiz_detail_view_Count(Department depTree, int depid,
			int roomid, int role) throws ElException;// hwc

	/**
	 * Description: 按考场，账号id 获取考试答卷（study_exampaper)信息
	 * 
	 * @Version1.0 2012-7-2 上午11:53:38 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> list_detail_view_quizpaper(int roomid, int userid)
			throws ElException;
	/**
	 * 考试统计（查看详情列表页）
	 * @param depTree
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquizblock_detail_view(Department depTree,
			 ExamRoom examRoom,String ids,int pageNow,int pageSize) throws ElException;
	/**
	 * 考试统计数量（查看详情列表页）
	 * @param depTree
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listquizblock_detail_view_size(Department depTree,
			ExamRoom examRoom,String ids) throws ElException;
	/**
	 * Description: 按考场，账号id 获取考试答卷大题信息
	 * 
	 * @Version1.0 2012-7-2 上午11:53:38 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> list_detail_view_quizblock(EroomBlock erblock, int userid)
			throws ElException;

	/**
	 * 按试卷id,考场id,以及学院账号id来获取某场考试的某分试卷的答卷记录 Description:
	 * 
	 * @Version1.0 2012-7-3 下午05:51:19 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @param epid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> list_read_quizpaper(int roomid, int epid,
			int userid) throws ElException;

	public List<MyExamPaper> listquizpaper_detail_view(int roomid, int userid)
			throws ElException;

	/**
	 * Description:考场试卷统计
	 * 
	 * @Version1.0 2012-7-17 下午01:54:52 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @param deps
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listquiz_detail_paper_view(String roomids,
			List<Department> deps, Department dep) throws ElException;

	/**
	 * Description: 获取考场考试人数及缺考人数
	 * 
	 * @Version1.0 2012-7-17 下午03:01:44 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getQuiz_detail_paper_view(Department dep, int roomid)
			throws ElException;
	
	/**获取考场批次考试人数及缺考人数
	 * @param dep
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public EroomBatch getQuizBatch_detail_paper_view(Department dep, int batchid)
			throws ElException;

	public List<ExamPaper> listSimEpByDid(int depid, ExamPaper ep)
			throws ElException;

	public int getSimUserBydepid(int epid, int depid, int cid)
			throws ElException;

	public List<MyExamPaper> listSimEps(int depid, int epid, int cid)
			throws ElException;

	public ExamPaper getEPbyEpidAndCid(int epid, int cid) throws ElException;

	// public List<MyEprac> listexamprac(int depid, Examprac examprac,
	// boolean sub, int begin, int end) throws ElException;

	// public int listexampracsize(int depid, Examprac examprac, boolean sub)
	// throws ElException;

	// public List<MyEprac> listexamprac(int depid, int pracid, boolean sub,
	// int begin, int end) throws ElException;

	// public List<MyEprac> listexamprac(int depid, int pracid, boolean sub)
	// throws ElException;

	// public int listexampracsize(int depid, int pracid, boolean sub)
	// throws ElException;

	public Examprac getexamprac_gk(int depid, int pracid, boolean sub)
			throws ElException;

	/**
	 * 练习统计部门比较
	 * 
	 * @param pracid
	 * @param deps
	 * @return
	 * @throws ElException
	 */
	// public List<Department> listPracEval(int pracid,List<Department> deps)
	// throws ElException;
	public ExamRoom geteroom_gk(int depid, String roomids, boolean sub)
			throws ElException;

	public List<Department> listEroomEval(Department depTree, String roomids,
			List<Department> deps) throws ElException;

	/**
	 * Description:考场中各类试卷列表
	 * 
	 * @Version1.0 2012-7-18 上午09:10:57 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listEroomExampapers(String roomids,ElNode depTree) throws ElException;

	/**考场批次统计，批次列表
	 * @param userid
	 * @param dep
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<EroomBatch> listErbatchs(int userid,Department dep, int pageNow, int pageSize)
			throws ElException;
	/**获取考场批次中的考场id
	 * @param erbid
	 * @return
	 * @throws ElException
	 */
	public String getEroomsByErbid(int erbid) throws ElException ;
	/**考场批次统计，批次数量
	 * @param userid
	 * @param dep
	 * @return
	 * @throws ElException
	 */
	public int listErbatchsSize(int userid,Department dep) throws ElException;

	public EroomBatch getErbatch_gk(int erbid) throws ElException;

	public List<ExamRoom> listErbRooms(int erbid) throws ElException;

	public List<ExamRoom> listErbRoomsPage(int erbid, int pageNow, int pageSize)
			throws ElException;// hwc

	public List<ExamRoom> listErbRoomsPage(int erbid) throws ElException;

	public int listErbRoomsCount(int erbid) throws ElException;// hwc

	public List<MyBatchRoom> listErbquiz_detail_view(int bid)
			throws ElException;

	// ---->>修改前2-23 public List<MyBatchRoom> listErbquiz_detail_view_Page(int
	// bid ,int pageNow , int pageSize) throws ElException;//hwc/wys
	public List<MyRoom> listErbquiz_detail_view_Page(int bid, int pageNow,
			int pageSize) throws ElException;// hwc/wys

	// ---->>修改前2-23 public List<MyBatchRoom> listErbquiz_detail_view_Page(int
	// bid ) throws ElException;//hwc Export EXCEL/wys

	public List<MyRoom> listErbquiz_detail_view_Page(int bid)
			throws ElException;// hwc Export EXCEL/wys

	public List<MyExamPaper> listErbExampapers(int bid) throws ElException;// /wys

	public int listErbquiz_detail_view_Count(int bid) throws ElException;// hwc/wys

	public List<Department> listErBEval(int bid, List<Department> list)
			throws ElException;

	/**考试统计-部门比较中的考试信息
	 * @param depid
	 * @param roomid
	 * @param sub
	 * @return
	 * @throws ElException
	 */
	public ExamRoom geteroom_gk_2(int depid, String roomid, boolean sub)
			throws ElException;

	/**
	 * 练习统计
	 * 
	 * @param depid
	 * @param pracid
	 * @param sub
	 * @return
	 * @throws ElException
	 */
	public Examprac getexamprac_gk2(int depid, int pracid, boolean sub)
			throws ElException;

	/**
	 * 获取练习统计详情人员列表
	 * 
	 * @param depid
	 * @param pracid
	 * @param sub
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	// public List<MyEprac> listexamprac2(int depid, int pracid, boolean sub,
	// int begin, int end) throws ElException;
	/**
	 * 获取练习统计详情数量
	 * 
	 * @param depid
	 * @param pracid
	 * @param sub
	 * @return
	 * @throws ElException
	 */
	// public int listexampracsize2(int depid, int pracid, boolean sub)
	// throws ElException;
	/**
	 * 考核各单位情况汇总表 按部门，试卷
	 * 
	 * @param roomid
	 * @param depid
	 * @param sub
	 * @return
	 * @throws ElException
	 */
	public Department listEroomEval2(String roomids, List<ExamPaper> eps,
			int depid, boolean sub) throws ElException;

	/**
	 * 根据部门的及格率排序
	 * 
	 * @param deps
	 * @return
	 */
	public List<Department> getDepSortByRatio(List<Department> deps);

	/**
	 * Description: 考核各工种情况汇总表 按部门，试卷
	* @Version1.0 2012-7-18 下午03:20:36 by 闻益舜（wenyishun110@163.com）创建
	 * @param roomid
	 * @param eps
	 * @param depid
	 * @param sub
	 * @return
	 * @throws ElException
	 */
	public BaseDatat listEroomEval_jz(String roomids, List<ExamPaper> eps,
			int jzid,Department depTree, boolean sub) throws ElException;

	/**
	 * 练习统计查询练习列表
	 * 
	 * @param tree
	 * @param examprac
	 * @param sublibs
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyEprac> listexamprac(Examprac examprac, int pageNow,
			int pageSize) throws ElException;

	/**
	 * 练习统计查询练习列表数量
	 * 
	 * @param tree
	 * @param examprac
	 * @param sublibs
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listexampracSize(Examprac examprac) throws ElException;

	/**
	 * 练习统计部门比较
	 * 
	 * @param pracid
	 * @param dep
	 * @return
	 * @throws ElException
	 */
	public Department listPracEval(int pracid, Department dep)
			throws ElException;

	/**
	 * 获取练习统计详情人员列表
	 * 
	 * @param depid
	 * @param pracid
	 * @param sub
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	public List<MyEprac> listexamprac(ElNode tree, int pracid, boolean consub,
			int pageNow, int pageSize) throws ElException;

	/**
	 * 获取练习统计详情人员列表数量
	 * 
	 * @param depid
	 * @param pracid
	 * @param sub
	 * @param begin
	 * @param end
	 * @return
	 * @throws ElException
	 */
	public int listexampracSize(ElNode tree, int pracid, boolean consub)
			throws ElException;
	/**
	 * 查询学员考场信息（阅卷列表页）
	 * @param roomid
	 * @param myRoom
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquiz_detail_view(int roomid,MyRoom myRoom,int pageNow, int pageSize) throws ElException;
	/**
	 * 查询学员考场信息数量（阅卷列表页）
	 * @param roomid
	 * @param myRoom
	 * @return
	 * @throws ElException
	 */
	public int listquiz_detail_viewSize(int roomid,MyRoom myRoom) throws ElException;
	/**
	 * 考试统计（查看详情列表页）
	 * @param depTree
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listquiz_detail_view(Department depTree,
			 ExamRoom examRoom,String ids,int pageNow,int pageSize) throws ElException;
	/**
	 * 考试统计数量（查看详情列表页）
	 * @param depTree
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listquiz_detail_view_size(Department depTree,
			ExamRoom examRoom,String ids) throws ElException;
	/**获取考场模块列表
	 * @param userid
	 * @param dep
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<EroomBlock> listErblocks(int userid,Department dep, int pageNow, int pageSize)
			throws ElException;
	/**获取考场模块数量
	 * @param userid
	 * @param dep
	 * @return
	 * @throws ElException
	 */
	public int listErblocksSize(int userid,Department dep) throws ElException;
	
	
	/**
	 * 给我的课程添加考试时间和成绩
	 * @param classid
	 * @param userid
	 * @param mycourse
	 * @return
	 * @throws ElException
	 */
	public MyCourse getFinishtimeByScore(int classid,int userid,MyCourse mycourse) throws ElException;
	
	/**
	 * 给我的课程添加考试时间和成绩
	 * @param userid
	 * @param myCPage
	 * @return
	 * @throws ElException
	 */
	public MyCPage getFinishtimeByScorePage(int courseid,int userid,MyCPage myCPage) throws ElException;


	/**
	 * 个人学分查询  wsj20131202
	 */
	public List<MyClass> personScoreList(int userid) throws ElException;
	
	/**
	 * 部门学分查询  wsj20131202
	 */
	public List<ElClass> depScoreList(ElNode tree, int sublibs) throws ElException;
}
