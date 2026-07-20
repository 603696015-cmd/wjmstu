package com.sopia.newsandmess.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsStyle;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.questionman.entities.StuffLib;

public interface NewsDao {
	public void addNewstype(NewsType ntype) throws ElException;
	public void alterNewstype(NewsType ntype) throws ElException;
	public void deleteNtype(int id) throws ElException;
	
	public void addNewsstyle(NewsStyle nstyle) throws ElException;
	public void alterNewsstyle(NewsStyle nstyle) throws ElException;
	public void deleteNewsstyle(int id) throws ElException;
	public NewsStyle getNstyleByid(int id)throws ElException;
	
	public NewsType getNtypeTree(int from, int stop, boolean constop) throws ElException;
	
	public int addNewstype2(NewsType ntype) throws ElException;
	public void update_status(int newsid,int status)throws ElException;
	
	/**
	 * 查询 新闻公告栏目
	 * @param from
	 * @param stop
	 * @param constop
	 * @param userid
	 * @param isShared 是否需要加载共享节点，true需要，false不需要。
	 * @param permtype 管理权限和使用权限 这些就直接传表名方便处理
	 * @return
	 * @throws ElException
	 */
	public NewsType getNtypeTreeByPerOrShar(int from, int stop, boolean constop,String userid,boolean isShared,String permtype) throws ElException;
	
	public NewsType getNtypeRoot()throws ElException;
	
	public NewsType getNtypeByid(int id)throws ElException;
	
	public void addNews(News news) throws ElException;

	public void alterNews(News news) throws ElException;

	public void deleteNews(int id) throws ElException;

	public News getNewsById(int id) throws ElException; 

	public List<News> getNewsByUid(int userid,int nid,int pageNow,int pageSize) throws ElException;
	
	
	public List<News> getNewsByUidByPerOrShar(String userid,int nid,NewsType ntypeTree,Integer status,String title,int pageNow,int pageSize) throws ElException;//hwc
	
	public List<News> getNewsByUidByPerOrShar(String userid,int nid,NewsType ntypeTree,Integer status,int pageNow,int pageSize) throws ElException;//hwc
	
	public int getNewsCountByUid(int userid,int nid)throws ElException;

	public int getNewsCountByUidByPerOrShar(String userid,int nid,NewsType ntypeTree,Integer status,String title)throws ElException;
	
	public int getNewsCountByUidByPerOrShar(String userid,int nid,NewsType ntypeTree,Integer status)throws ElException;
	
	public List<NewsType> getNtypesByPid(int pid) throws ElException ;
	
	public List<NewsStyle> getNstyles() throws ElException ;
	
	/**
	 * 查询当前人员所在的二级部门的管理员
	 * @author jiahaijiang
	 * @param deptid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> findUserByMyDeptid(int deptid)throws ElException;

	//修改新闻公告浏览数
	public void updateNewsBrowseforById(int newsid)throws ElException;
	//组合新闻搜索
	public List<News> listCombinationNew(int userid,NewsType ntypeTree,News news, int pageNow, int pageSize)throws ElException;
	public int listCombinationNewCount(int userid,NewsType ntypeTree,News news, int pageNow, int pageSize)throws ElException;
	public List<NewsType> listNewsType()throws ElException;
	
	//tree   hwc   
	public NewsType getNtypeTree(int userid, String op, int stopid,	boolean containStop) throws ElException ;
	//搜索资讯
	public List<News> SearchNews(News news,NewsType ntypeTree, int pageNow,int pageSize) throws ElException;
	public int SearchNewsCount(News news,NewsType ntypeTree, int pageNow,int pageSize) throws ElException;
	/**
	 * 获取所有信息栏目
	 * @return
	 * @throws ElException
	 */
	public List<NewsType> getAllNewsType() throws ElException;
	/**
	 * 根据新闻id集合更新新闻的热度
	 * @param newIds
	 * @param hot
	 * @throws ElException
	 */
	public void updateNewsHot(String newIds,int hot) throws ElException;	
	/**
	 * 新闻增加附件
	 * @param addr
	 * @param Newsid
	 * @param title
	 * @throws ElException
	 */
	public void addKstuff(String addr, int Newsid, String title)
	throws ElException;
	/**
	 * 添加新闻公告(有第2状态)
	 * @param news
	 * @throws ElException
	 */
	public int  addNews2(News news) throws ElException; 
	/**
	 * 根据session用户查出他所能操作的新闻
	 * @param userid
	 * @param nid
	 * @param ntypeTree
	 * @param status
	 * @param pageNow
	 * @param pageSize
	 * @return id
	 * @throws ElException
	 */
	public List<News> listFabuNewses(int status_tow,NewsType ntypeTree, int nid ,int pageNow, int pageSize) throws ElException;
	public List<News> getNewsByUidByPerOrShar2(String userid, int nid,
			NewsType ntypeTree,Integer status, int pageNow, int pageSize,String displayStatus) throws ElException;
	/**
	 * 根据条件获取新闻数量
	 * @param userid
	 * @param nid
	 * @param ntypeTree
	 * @param status
	 * @param displayStatus
	 * @return
	 * @throws ElException
	 */
	public int getNewsCountByUidByPerOrShar2(String userid, int nid,
			NewsType ntypeTree,Integer status,String displayStatus) throws ElException;
	/**
	 * 搜索新闻信息
	 * @param userid
	 * @param ntypeTree
	 * @param news
	 * @param pageNow
	 * @param pageSize
	 * @param displayStatus
	 * @return
	 * @throws ElException
	 */
	public List<News> listCombinationNew2(int userid,NewsType ntypeTree,News news, int pageNow, int pageSize,String displayStatus)throws ElException;
	/**
	 * 搜索新闻的条数
	 * @param userid
	 * @param ntypeTree
	 * @param news
	 * @param pageNow
	 * @param pageSize
	 * @param displayStatus
	 * @return
	 * @throws ElException
	 */
	public int listCombinationNewCount2(int userid,NewsType ntypeTree,News news,String displayStatus)throws ElException;
	/**
	 * 更新新闻的第2状态
	 * @param newsid
	 * @param status_tow
	 * @throws ElException
	 */
	public void updateNewsStatus(int newsid, int status_tow) throws ElException;
	/**
	 * 保存新闻当前状态
	 * @param newsid
	 * @param status_tow
	 * @throws ElException
	 */
	public void updateNewsAstatus(int newsid, int astatus_tow) throws ElException;
	/**
	 * 设置新闻弹窗
	 * @param newIds
	 * @param hot
	 * @throws ElException
	 */
	public void NewsSetpop(String newIds) throws ElException;
	/**
	 * 获取弹窗新闻
	 */
	public News getNewsInPop(NewsType ntypeTree,int ntypeid) throws ElException;
	/**
	 * 取消弹窗新闻
	 * @throws ElException
	 */
	public void update_newsIspop() throws ElException;
	/**
	 * 获取弹窗新闻
	 */
//	public News getNewsInPop() throws ElException;
	/**
	 * 删除新闻
	 * @param newIds
	 * @throws ElException
	 */
	public void delNews(String newIds) throws ElException;
	/**
	 * 获取待审核的新闻数量
	 * @return
	 * @throws ElException
	 */
	public int getNewsEndCount() throws ElException;
	/**
	 * 获取新闻附件
	 * @param newsid
	 * @return
	 * @throws ElException
	 */
	public List<StuffLib> listKstuff(int newsid) throws ElException;
	/**
	 * 修改新闻附件
	 * @param title
	 * @param id
	 * @throws ElException
	 */
	public void alterNstuff(String title, int id) throws ElException ;
	/**
	 * 新闻附件下载源
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public StuffLib getNStuffLib(int id) throws ElException;
	/**
	 * 删除新闻附件
	 * @param id
	 * @throws ElException
	 */
	public void deleteNewsStuff(int id) throws ElException;
	/**
	 * 更新新闻库的父节点
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateNewstypeParentid(int pid, int npid) throws ElException;
	/**
	 * 更新新闻的类别id
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateNewsParentid(int pid, int npid) throws ElException;
	/**
	 * 删除新闻类别，包含下级资源
	 * @param id
	 * @throws ElException
	 */
	public void deleteNewsTypeAndSub(int id) throws ElException;
	
	/*
	 * 根据发布时间查询前N条
	 */
	public List<News> getNewsByNum(int number) throws ElException;
	/*
	 * 根据id范围查询
	 */
	
	public List<News> getNewsByids(int start,int end)throws ElException;
	/*
	 * 根据新闻Ntypeid查询
	 */
	public List<News> getNewsByNtypeId(int ntid)throws ElException;
	/**
	 * 获取弹窗新闻
	 */
	public News getNewsInPop() throws ElException;
	
	public void updateNewsIsHtmlById(int id)throws ElException;
	
	public List<News> getNewsByIsHtml(int ishtml)throws ElException;
	
	public List<News> getAllNews()throws ElException;
	
	public List<News> listNews() throws ElException;
	
	public List<News> getTjNews(int ntid,int hot) throws ElException;
	public List<News> getTjNews(int hot) throws ElException;
}
