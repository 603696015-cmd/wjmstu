package com.sopia.knowledgeman.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.MyLogin;
import com.sopia.forumman.entities.Forum;
import com.sopia.knowledgeman.entities.DownloadInfo;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.questionman.entities.StuffLib;

public interface KnowledgeDao {
	public int addKltype(KnowledgeType klType) throws ElException;
	
	public KnowledgeType getKnowledgeLibTree(int userid, String op, int stopid,boolean containStop) throws ElException;
	
	public KnowledgeType getKltypeTree(int fromid, int stopid,boolean containStop) throws ElException;

	public KnowledgeType getKltypeById(int id) throws ElException;

	// public List<Knowledge> listKls(int typeid)throws ElException;
	public List<Knowledge> listKls(int depid, boolean depcon, int pageNow,
			int pageSize, String name) throws ElException;

	public List<Knowledge> listKls(int depid, int type, boolean depcon,
			int pageNow, int pageSize, String name) throws ElException;

	public int listKlsSize(int depid, boolean depcon, String name)
			throws ElException;

	public int listKlsSize(int depid, int type, boolean depcon, String name)
			throws ElException;

	public void addKl(Knowledge kl) throws ElException;

	public void addKstuff(String addr, int kid, String title)
			throws ElException;
	public void deleteKstuff(int id) throws ElException;
	public void alterKstuff(String title, int id) throws ElException;

	public List<StuffLib> listKstuff(int klid) throws ElException;
	
	//获取知识附件的地址
	public List<StuffLib> listKstuff() throws ElException;

	public StuffLib getKStuffLib(int id) throws ElException;

	public void addKltype_dep(int kltypeid, int depid) throws ElException;

	public void deleteKltypedep(int kltypeid) throws ElException;

	public void alterKl(Knowledge kl) throws ElException;

	public boolean checkKltype_dep(int kltypeid, int depid) throws ElException;

	public List<KnowledgeType> listKltsByDepId(int depid) throws ElException;
	public List<KnowledgeType> listKltsByDepIdNew(int userid,int shared) throws ElException;
	public List<KnowledgeType> listKltsByDepIdNew(int userid) throws ElException;

	public List<Department> listDepByKltypeId(int kltypeid) throws ElException;

	public void alterKltype(KnowledgeType kltype) throws ElException;

	// /------------
	public List<Knowledge> listMyKls(int userid, int pageNow, int pageSize)
			throws ElException;
	public List<Knowledge> listMyKlsNew(int userid,String type,String title, int pageNow, int pageSize)
	throws ElException;
//	public List<Knowledge> listMyKlsNew(int userid ,KnowledgeType kltypeTree,int ktid ,String type,String title, int pageNow, int pageSize)
//	throws ElException ;
	public int listMyklsSize(int userid) throws ElException;
//	public int listMyklsSizeNew(int userid ,KnowledgeType kltypeTree,int ktid ,String type,String title) throws ElException;
	public int listMyklsSizeNew(int userid,String type,String title) throws ElException;

	public void deleteKl(int id) throws ElException;

	public Knowledge getKlById(int id) throws ElException;

	public List<Knowledge> listKlsByType(int typeid, int pageNow, int pageSize)
			throws ElException;

	public int listKlsByTypeSize(int typeid) throws ElException;

//	public List<Knowledge> listKlsByTitle(String title, int pageNow,
//			int pageSize) throws ElException;

	public int listKlsByTitleSize(String title) throws ElException;

	public List<Knowledge> listKlByHot(int hot, int pageNow, int pageSize)
			throws ElException;

	public List<Knowledge> listKlByReadTime(int pageNow, int pageSize)
			throws ElException;
	public int listShKlsByPerOrSharSize(KnowledgeType kltypeTree,int depid,String type,String title) throws ElException;
	public void setKlReadtime(int id) throws ElException;

	public void setKlhotSet(int id, int hot) throws ElException;

	public List<Knowledge> listShKls(int depid, int pageNow, int pageSize)
			throws ElException;
	
	public List<Knowledge> listShKlsByPerOrShar(KnowledgeType kltypeTree,int depid, int pageNow, int pageSize,String type,String title)
	throws ElException;

	public int listShKlsSize(int depid) throws ElException;

	public void klShSet(int klid) throws ElException;

	public List<Knowledge> listShmKls(int depid, int pageNow, int pageSize)
			throws ElException;

	public int listShmKlsSize(int depid) throws ElException;
	
	/**
	 * 查询 知识类别管理
	 * @param from
	 * @param stop
	 * @param constop
	 * @param userid
	 * @param isShared 是否需要加载共享节点，true需要，false不需要。
	 * @param permtype 管理权限和使用权限 这些就直接传表名方便处理
	 * @return
	 * @throws ElException
	 */
	public KnowledgeType getKltypeTreeByPerOrShar(int from, int stop, boolean constop,String userid,boolean isShared,String permtype) throws ElException;
	
	
	//栏目推荐知识
	public List<Knowledge> listKlByHot2(int hot, int pageNow, int pageSize)
	throws ElException ;
	//知识组合搜索
	public List<Knowledge> listCombinationKlsNew(int userid,String type,Knowledge knowledge, int pageNow, int pageSize)
	throws ElException;
	//hwc
	public List<Knowledge> listCombinationKlsNew(KnowledgeType kltypeTree,int ktid ,int userid,String type,Knowledge knowledge, int pageNow, int pageSize)
	throws ElException;
	
	//知识组合搜索行数
	public int listCombinationKlsNewCount(int userid,String type,Knowledge knowledge, int pageNow, int pageSize)
		throws ElException;
	//hwc
	public int listCombinationKlsNewCount(KnowledgeType kltypeTree,int ktid ,int userid,String type,Knowledge knowledge, int pageNow, int pageSize)
	throws ElException;
	//知识栏目
	public List<KnowledgeType> listKnowledgeType()throws ElException;
	//搜索知识
	public List<Knowledge> listKlsByType(Knowledge knowledge, int pageNow, int pageSize)
	throws ElException;
	//搜索知识
	public int listKlsByTypeCount(Knowledge knowledge, int pageNow, int pageSize)
	throws ElException;
	
	public String KTTypeById(KnowledgeType ctypeTree, int ctid);
	/**
	 * 根据资源类别获取资源id集合
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getKlByKltype(int typeid) throws ElException;
	/**
	 * 更新资源的父id
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateKnowledgePid(int pid,int npid) throws ElException;
	/**
	 * 根据标题获取资料集合
	 * @param title
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Knowledge> listKlsByTitle_list(String title, int pageNow,
			int pageSize) throws ElException;
	public List<Knowledge> listKlsByType_list(Knowledge knowledge, int pageNow, int pageSize)
	throws ElException;
	/**
	 * 资料检索列表
	 * @param kltypeTree
	 * @param ktid
	 * @param userid
	 * @param type
	 * @param knowledge
	 * @return
	 * @throws ElException
	 */
	public int listCombinationKlsNewCount2(KnowledgeType kltypeTree,int ktid ,int userid,String type,Knowledge knowledge)
	throws ElException;
	/**
	 * 查询我发布的资料
	 * @param userid
	 * @param kltypeTree
	 * @param knowledge
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Knowledge> listMyKlsNew(int userid ,KnowledgeType kltypeTree,Knowledge knowledge, int pageNow, int pageSize)throws ElException;
	public List<Knowledge> listMyKlsNew(int depid , int pageNow, int pageSize)
	throws ElException;
	/**
	 * 查询我发布的资料数量
	 * @param userid
	 * @param kltypeTree
	 * @param knowledge
	 * @return
	 * @throws ElException
	 */
	public int listMyklsSizeNew(int userid ,KnowledgeType kltypeTree,Knowledge knowledge) throws ElException;
	/**
	 * 添加资料部门信息
	 * @param dataId 资料或者部门的id
	 * @param status 1：代表资料 2：代表部门
	 * @throws ElException
	 */
	public void addKnowledgeDep(int dataId,int status)throws ElException;
	/**
	 *  删除资料部门信息
	 * @throws ElException
	 */
	public void deleteKnowledgeDep()throws ElException;
	/**
	 * 获取资料部门表中的所有部门
	 * @return
	 * @throws ElException
	 */
	public List<Department> listKnowledgeDepd()throws ElException;
	/**
	 * 获取资料部门表中的所有资料
	 * @return
	 * @throws ElException
	 */
	public List listKnowledgeDepk() throws ElException;
	/**
	 * 获取前台需要显示的资源库
	 * @param op
	 * @param stopid
	 * @param containStop
	 * @return
	 * @throws ElException
	 */
	public KnowledgeType getKnowledgeLibTree_index(int stopid,boolean containStop) throws ElException;
	/**
	 * 检测用户是否有部门所对应分配的资料权限
	 * @param userid
	 * @throws ElException
	 */
	public boolean checkUserKnowledgeDep(int userid) throws ElException;
	/**
	 * 根据id检查该资料在资料部门中是否存在
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public boolean checkKnowledgeDepK(int id) throws ElException;
	
	/**
	 * 判断有没有当前月份的资料夹
	 * @param value
	 * @return
	 * @throws ElException
	 */
	public int checkKnowledgeForMonth(String value) throws ElException;
	/**
	 * 获取下载信息表userid用户下载得分次数
	 * @param type
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getDownloadInfoIsAddCent(int type, int userid) throws ElException ;
	/**
	 * 练习、模考、笔记加分明细
	 * @param userid
	 * @param classid
	 * @param classType 培训班的课程有两个表。 自主培训班是CLASS_COURSE_AT 其余培训班是CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public List<Course> getLX_MK_BJ_Integra_viewList(int userid, int classid, String classType) throws ElException ;
	/**
	 * 登陆详情
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyLogin> getDL_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException;
	public int getDL_Integra_viewListSize(int userid) throws ElException ;
	/**
	 * 发帖精华详情
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Forum> getFTJH_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException;
	public int getFTJH_Integra_viewListSize(int userid) throws ElException;
	/**
	 * 我的下载详情
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<DownloadInfo> getXZ_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException;
	public int getXZ_Integra_viewListSize(int userid) throws ElException;
	/**
	 * 知识库积分查看详情集合
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Knowledge> getKl_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException;
	/**
	 * 知识库积分查看详情Size
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getKl_Integra_viewListSize(int userid) throws ElException;
	/**
	 * 知识库被下载积分查看详情
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Knowledge> getBXZ_Integra_viewList(int userid, int pageNow, int pageSize) throws ElException;
	public int getBXZ_Integra_viewListSize(int userid) throws ElException;
	/**
	 * 知识库被用户下载详情
	 * @param kid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getBXZ_XQ_Integra_viewList(int kid, int pageNow, int pageSize) throws ElException;
	public int getBXZ_XQ_Integra_viewListSize(int kid) throws ElException;
	
	/**
	 * 全文检索  根据检索出来的文件名称获得knowledge
	 */
	public List<Knowledge> listKlByStuffAddr(String stuffaddr)throws ElException;
	
	public List<Knowledge> getTjKls(int kltype,int hot)throws ElException;
	public List<Knowledge> getTjKls(int hot)throws ElException;
}
