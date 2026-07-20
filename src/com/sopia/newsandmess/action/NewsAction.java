package com.sopia.newsandmess.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction;
import com.sopia.CreatorHtml;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.dao.impl.MessageDaoImpl;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsStyle;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.questionman.entities.StuffLib;

public class NewsAction extends BaseAction {
	private NewsType ntypeTree;
	private NewsType ntype;
	private NewsDao newsDao;
	private List<News> newses;
	private News news;
	private ELUser elUser;
	private List<NewsType> lnts;
	private String displayStatus;
	private int newsOp;//新闻操作
	private int newsIsDel;//下属新闻类别与子类别操作  1.并入上级类别  2.与本类别同时删除
	private IndexDataUtil indexDataUtil;
	private String Return;
	private List<NewsStyle> lnss;
	private NewsStyle nstyle;
	public NewsStyle getNstyle() {
		return nstyle;
	}

	public void setNstyle(NewsStyle nstyle) {
		this.nstyle = nstyle;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public int getNewsIsDel() {
		return newsIsDel;
	}

	public void setNewsIsDel(int newsIsDel) {
		this.newsIsDel = newsIsDel;
	}

	public int getNewsOp() {
		return newsOp;
	}

	public void setNewsOp(int newsOp) {
		this.newsOp = newsOp;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public List<NewsType> getLnts() {
		return lnts;
	}

	public void setLnts(List<NewsType> lnts) {
		this.lnts = lnts;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public List<News> getNewses() {
		return newses;
	}

	public void setNewses(List<News> newses) {
		this.newses = newses;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public String newstype_list() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			// ntypeTree = newsDao
			// .getNtypeTreeByPerOrShar(
			// ElConstants.TREE_ROOT,
			// ElConstants.TREE_FIANL,
			// true,
			// String
			// .valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
			// false, "newstype_op_type");
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}

		return "newstype_list";
	}
	
	//新闻栏目列表
	public String newsstyle_list() throws ElException {
		lnss = newsDao.getNstyles();
		return "newsstyle_list";
	}

	public String newsstyle_addInit() throws ElException {
		return "newsstyle_add";
	}
	public String newsstyle_add() throws ElException {
		newsDao.addNewsstyle(nstyle);
		return "newsstyle_add_success";
	}
	public String newstype_addInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if(ntypeTree.getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的新闻库");
			 return "error"; 
		}
		return "newstype_add";
	}

	public String newsstyle_view() throws ElException {
		nstyle = newsDao.getNstyleByid(nstyle.getId());
		return "newsstyle_view";
	}
	
	public String newsstyle_alterInit() throws ElException {
		nstyle = newsDao.getNstyleByid(nstyle.getId());
		return "newsstyle_alter";
	}
	
	public String newsstyle_alter() throws ElException {
		newsDao.alterNewsstyle(nstyle);
		return "newsstyle_alter_success";
	}
	
	public String newsstyle_delete() throws ElException {
		newsDao.deleteNewsstyle(nstyle.getId());
		return "newsstyle_list";
	}

	public String newstype_add() throws ElException {
		newsDao.addNewstype(ntype);
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("newstype");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_NEWSTYPE,
				ElLoggerConstants.LOG_TYPE_ADD, ntype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ntype.getId());//**//**//
		return "newstype_add_success";
	}

	/**新闻类别修改
	 * Description: 
	* @Version1.0 2012-7-13 上午08:51:46 by 闻益舜（wenyishun110@163.com）创建
	 * @return
	 * @throws ElException
	 */
	public String newstype_alterInit() throws ElException {

		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		ntype = newsDao.getNtypeByid(ntype.getId());
		ntype
				.setOpusers(ctypeDao.getOpUsers("newstype_op_type", ntype
						.getId()));
//		ntype.setUseusers(ctypeDao.getOpUsers("newstype_use_type", ntype
//				.getId()));
		return "newstype_alter";
	}

	private RoleDao roleDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 新闻树的修改
	 * @return
	 * @throws ElException
	 */
	public String newstype_alter() throws ElException {
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(ntype.getId(), ntype.getParent().getId(), "newstype")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return "error";
		}
		newsDao.alterNewstype(ntype);
		ens.updatetlrid("newstype");
//		if (null != ntype.getOpusers()) {
//			for (int i = 0; i < ntype.getOpusers().size(); i++) {
//				// 这样的写法不好，循环去操作数据会产生性能问题，由于时间关系先暂时参考试题库的代码这样处理（使用量不是很大的话也没什么问题）。
//				// 当出现性能问题时，可以把这段代码改掉，减少数据库的链接次数和做批量处理。备注：jiahaijiang
//				if (!ctypeDao.checkOpUsers("NEWSTYPE_OP_TYPE", ntype
//						.getOpusers().get(i).getId(), ntype.getId()))
//					ctypeDao.addOpusers("NEWSTYPE_OP_TYPE", ntype.getOpusers()
//							.get(i).getId(), ntype.getId());
//				roleDao.setUserfunc(ntype.getOpusers().get(i).getId(),
//						"newstype_list", 0);
//				roleDao.setUserfunc(ntype.getOpusers().get(i).getId(),
//						"newstype_addInit", 0);
//				roleDao.setUserfunc(ntype.getOpusers().get(i).getId(), "admin",
//						0);
//			}
//		}
//		if (null != ntype.getUseusers()) {
//			for (int i = 0; i < ntype.getUseusers().size(); i++) {
//				if (!ctypeDao.checkOpUsers("NEWSTYPE_USE_TYPE", ntype
//						.getUseusers().get(i).getId(), ntype.getId()))
//					ctypeDao.addOpusers("NEWSTYPE_USE_TYPE", ntype
//							.getUseusers().get(i).getId(), ntype.getId());
//			}
//		}
		ntype=newsDao.getNtypeByid(ntype.getId());
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		if(ntype!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_NEWSTYPE,
					ElLoggerConstants.LOG_TYPE_ALTER, ntype.getName(),
					ElLoggerConstants.LOG_RES_SUCC,ntype.getId());
		}
		return "newstype_alter_success";
	}

	private CourseTypeDao ctypeDao;

	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}

	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}

	public String newstype_view() throws ElException {
		if(ntype==null||ntype.getId()<=0){	
			setElmessage("您需要查看的新闻类别不存在,请重新选择！");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		ntype = newsDao.getNtypeByid(ntype.getId());
		ntype.setOpusers(ctypeDao.getOpUsers("newstype_op_type", ntype.getId()));
//		ntype.setUseusers(ctypeDao.getOpUsers("newstype_use_type", ntype
//				.getId()));
		return "newstype_view";
	}

	/**
	 * 删除新闻类别
	 * @return
	 * @throws ElException
	 */
	public String newstype_delete() throws ElException {
		//newsDao.deleteNtype(ntype.getId());
		if(ntype.getId()<=1){
			setElmessage("不能删除根类别!");
			return "error";
		}
		ntype=newsDao.getNtypeByid(ntype.getId());
		if(newsIsDel==1){
			//并入上级
			newsDao.updateNewstypeParentid(ntype.getId(), ntype.getParent().getId());
			newsDao.updateNewsParentid(ntype.getId(), ntype.getParent().getId());
			newsDao.deleteNtype(ntype.getId());
		}else{
			//一起删除
			newsDao.deleteNewsTypeAndSub(ntype.getId());
		}
		//更新新闻树左右id
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("newstype");
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_NEWSTYPE,
				ElLoggerConstants.LOG_TYPE_DELETE, ntype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ntype.getId());
		return "newstype_list";
	}

	private String optype;

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public String newstype_delete_user() throws ElException {
		ctypeDao.deleteOpusers(optype, elUser.getId(), ntype.getId());
		roleDao.checkUserfunc(elUser.getId(), "newstype_list",
				"NEWSTYPE_OP_TYPE");
		roleDao.checkUserfunc(elUser.getId(), "newstype_addInit",
				"NEWSTYPE_OP_TYPE");
		roleDao.checkUserfunc(elUser.getId(), "admin", "NEWSTYPE_OP_TYPE");

		return null;
	}

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	// TODO 删除栏目
	/**
	 * @return
	 * @throws ElException
	 */
	public String news_list() throws ElException { 
		if (getRequest().getParameter("updatestatus") != null) {
			if (status == null)
				status = 2;
			newsDao.update_status(news.getId(), status);
			news = null;
		}
		String myUserId = String
				.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			// ntypeTree = newsDao
			// .getNtypeTreeByPerOrShar(
			// ElConstants.TREE_ROOT,
			// ElConstants.TREE_FIANL,
			// true,
			// String
			// .valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
			// true, "NEWSTYPE_USE_TYPE");
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);

		}
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1
				: ntype.getId());
		// int nid = ntype == null ? newsDao.getNtypeRoot().getId() :
		// (ntype.getId() == 0 ? 1 : ntype.getId());
		// ntype= newsDao.getNtypeByid(nid);
		// getPageSize()=getPageSize()==0?10:getPageSize();

		// String title = news.getTitle();
		String title = "";
		if (news != null) {
//			title = news.getTitle() == null ? "" : news.getTitle();
//			newses = newsDao.getNewsByUidByPerOrShar("1", 1, ntypeTree, null,
//					title, getPageNow(), getPageSize());
//			count = newsDao.getNewsCountByUidByPerOrShar("1", 1, ntypeTree,
//					null, title);
			newses = newsDao.listCombinationNew(0, ntypeTree, news, getPageNow(),//引用组合搜索
			getPageSize());
			count = newsDao.listCombinationNewCount(0, ntypeTree, news,
			getPageNow(), getPageSize());
		} else {
			newses = newsDao.getNewsByUidByPerOrShar(myUserId, nid, ntypeTree,
					null, getPageNow(), getPageSize());
			count = newsDao.getNewsCountByUidByPerOrShar(myUserId, nid,
					ntypeTree, null);
		}
		//查出新闻所有类型
		List<NewsType> newsTypeList=newsDao.getAllNewsType();
		getRequest().setAttribute("newsTypeList", newsTypeList);
		
//		newses = newsDao.listCombinationNew(0, ntypeTree, news, getPageNow(),
//				getPageSize());
//		count = newsDao.listCombinationNewCount(0, ntypeTree, news,
//				getPageNow(), getPageSize());
		return "news_list";
	}
	/**
	 * 更新新闻的状态
	 * @return
	 * @throws ElException
	 */
	public String upNewsStatus() throws ElException {
		news=newsDao.getNewsById(news.getId());
		//根据操作获取要更新的状态
		int status_tow=0;
		String resultPage="newsManage_list";
		int opType=0;//存放系统日志的操作类型
		if(newsOp==1){
			//申请初审的操作,状态改为2：初审中
			status_tow=2;
			opType=2;
			resultPage="newsManage_list";
		}else if(newsOp==2){
			//初审通过，状态改为4:终审等待中
			status_tow=4;
			opType=6;
			resultPage="news_early_trial_list";
			//发送短消息到申请人员那里
//			Message message=new Message();
//			message.setMess_title("初审通过短消息！");
//			message.setMess_content("你申请的新闻["+news.getTitle()+"]得到["+elUser.getRealname()+"]的初审,并且通过了！！！");
//			message.setMess_time(new Date());
//			message.setMess_from(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
//			message.setMess_to(new ElLogger().getSyslogInUser(1, 2, news.getTitle()));
//			messageDao.insertMess(message);
			new MessageDaoImpl().insertMessInApply(news.getTitle(),ElLoggerConstants.LOG_MOD_NEWS, ElLoggerConstants.LOG_TYPE_VALID, getSessionIntValue(ElConstants.SESSION_USERID), 1);
			//也相当于申请终审
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_NEWS,
					ElLoggerConstants.LOG_TYPE_VALID2,news.getTitle(),
					ElLoggerConstants.LOG_RES_SUCC,news.getId());
		}else if(newsOp==3){
			//初审不通过，状态改为3：初审不通过
			status_tow=3;
			opType=7;
			resultPage="news_early_trial_list";
			new MessageDaoImpl().insertMessInApply(news.getTitle(),ElLoggerConstants.LOG_MOD_NEWS, ElLoggerConstants.LOG_TYPE_VALID, getSessionIntValue(ElConstants.SESSION_USERID), 2);
		}else if(newsOp==4){
			//终审通过，状态改为6：已发布
			status_tow=6;
			opType=8;
			resultPage="news_end_trial_list";
			new MessageDaoImpl().insertMessInApply(news.getTitle(),ElLoggerConstants.LOG_MOD_NEWS, ElLoggerConstants.LOG_TYPE_VALID2, getSessionIntValue(ElConstants.SESSION_USERID), 3);
		}else if(newsOp==5){
			//终审不通过，状态改为5：终审未通过
			status_tow=5;
			opType=9;
			resultPage="news_end_trial_list";
			new MessageDaoImpl().insertMessInApply(news.getTitle(),ElLoggerConstants.LOG_MOD_NEWS, ElLoggerConstants.LOG_TYPE_VALID2, getSessionIntValue(ElConstants.SESSION_USERID), 4);
		}else if(newsOp==6){
			//申请删除新闻，状态改为7：删除等待中
			//注意：此时要把新闻当前状态保存
			news=newsDao.getNewsById(news.getId());
			newsDao.updateNewsAstatus(news.getId(), news.getStatus_tow());
			status_tow=7;
			opType=10;
			resultPage="news_del_trial_list";
		}else if(newsOp==7){
			//确认删除，直接删除该新闻
			newsDao.deleteNews(news.getId());
			opType=5;
			//刷新首页新闻模块
			indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
			return "news_del_handle_list";
		}else if(newsOp==8){
			//不许删除，状态还原到以前
			news=newsDao.getNewsById(news.getId());
			status_tow=news.getAstatus_tow();
			opType=11;
			resultPage="news_del_handle_list";
		}else if(newsOp==9){
			//批量申请删除
			//注意：此时要把新闻当前状态保存
			//获取传上来的新闻id集合
			String[] newIdArray=newIds.split(",");
			for (int i = 0; i < newIdArray.length; i++) {
				news=newsDao.getNewsById(Integer.parseInt(newIdArray[i]));
				newsDao.updateNewsAstatus(news.getId(), news.getStatus_tow());
				newsDao.updateNewsStatus(news.getId(), 7);
			}
			//刷新首页新闻模块
			indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
			return "combinationSearchNews";
		}
		//获取新闻id，更新其第2状态
		newsDao.updateNewsStatus(news.getId(), status_tow);
		if(newsOp==4){
			int modelstatus = SystemConfOp.getIntValue(ElConstants.MODEL_WORKING);
				int listnumber = SystemConfOp.getIntValue(ElConstants.LIST_PAGE_NUMBER);
				//modelstatus==1 栏目页及内容页都生成HTML
				//modelstatus==2 只生成内容页 位置 catalogue/id/id.html
				if(modelstatus == 1){
					return "newsindexAction";
				}
				if(modelstatus == 2){
					return "newsindexviewAction";
				}
				if(modelstatus == 3){
					String htmlName = "newsIndex";
					String dirName = "newsindex";
					String path = "mode_action.shtml?mode.id=3&mode.bindtypeid=1&mode.typeid=1";
					CreatorHtml.callHtml(path,htmlName,dirName);
				}
		}
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		//添加到系统日志
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_NEWS,
				opType,news.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,news.getId());
		return resultPage;
	}
	
	
	/**
	 * 新闻管理
	 * @return
	 * @throws ElException
	 */
	public String newsManage_list() throws ElException {
		//newsDao.update_status(news.getId(), status);
		//保存需要显示的状态:所有
		this.displayStatus="";
		//获取新闻树
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1 : ntype.getId());
		if (news != null) {
			if(news.getNstyle()!=null){
				nstyle = newsDao.getNstyleByid(news.getNstyle().getId());
				news.setNstyle(nstyle);
			}
			newses = newsDao.listCombinationNew2(0, ntypeTree, news, getPageNow(),//引用组合搜索
			getPageSize(),displayStatus);
			count = newsDao.listCombinationNewCount2(0, ntypeTree, news,displayStatus);
		} else {
			String myUserId = String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
			newses = newsDao.getNewsByUidByPerOrShar2(myUserId, nid, ntypeTree,
					null, getPageNow(), getPageSize(),displayStatus);
			count = newsDao.getNewsCountByUidByPerOrShar2(myUserId, nid,
					ntypeTree, null,displayStatus);
		}
		lnss = newsDao.getNstyles();
		return "newsManage_list";
	}
	
	/**
	 * 新闻初审列表
	 * @return
	 * @throws ElException
	 */
	public String news_early_trial_list() throws ElException {
		//保存需要显示的状态:2,4,5
		this.displayStatus="2,4,5";
		//获取新闻树
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1 : ntype.getId());
		if (news != null) {
			newses = newsDao.listCombinationNew2(0, ntypeTree, news, getPageNow(),//引用组合搜索
			getPageSize(),displayStatus);
			count = newsDao.listCombinationNewCount2(0, ntypeTree, news,displayStatus);
		} else {
			String myUserId = String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
			newses = newsDao.getNewsByUidByPerOrShar2(myUserId, nid, ntypeTree,
					null, getPageNow(), getPageSize(),displayStatus);
			count = newsDao.getNewsCountByUidByPerOrShar2(myUserId, nid,
					ntypeTree, null,displayStatus);
		}
		lnss = newsDao.getNstyles();
		return "news_early_trial_list";
	}
	
	/**
	 * 新闻终审列表
	 * @return
	 * @throws ElException
	 */
	public String news_end_trial_list() throws ElException {
		//保存需要显示的状态:4,6
		//this.displayStatus="4,6";
		if(this.displayStatus==null){
			this.displayStatus="";
		}
		//获取新闻树
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1 : ntype.getId());
		if (news != null) {
			newses = newsDao.listCombinationNew2(0, ntypeTree, news, getPageNow(),//引用组合搜索
			getPageSize(),displayStatus);
			count = newsDao.listCombinationNewCount2(0, ntypeTree, news,displayStatus);
		} else {
			String myUserId = String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
			newses = newsDao.getNewsByUidByPerOrShar2(myUserId, nid, ntypeTree,
					null, getPageNow(), getPageSize(),displayStatus);
			count = newsDao.getNewsCountByUidByPerOrShar2(myUserId, nid,
					ntypeTree, null,displayStatus);
		}
		lnss = newsDao.getNstyles();
		return "news_end_trial_list";
	}
	
	public String news_del() throws ElException {
		//删除新闻
		if(newIds!=null){
			newsDao.delNews(newIds);
		}
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		return "news_end_trial_list";
	}
	
	/**
	 * 申请删除新闻
	 * @return
	 * @throws ElException
	 */
	public String news_del_trial_list() throws ElException {
		//保存需要显示的状态:所有
		this.displayStatus="";
		//获取新闻树
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1 : ntype.getId());
		if (news != null) {
			newses = newsDao.listCombinationNew2(0, ntypeTree, news, getPageNow(),//引用组合搜索
			getPageSize(),displayStatus);
			count = newsDao.listCombinationNewCount2(0, ntypeTree, news,displayStatus);
		} else {
			String myUserId = String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
			newses = newsDao.getNewsByUidByPerOrShar2(myUserId, nid, ntypeTree,
					null, getPageNow(), getPageSize(),displayStatus);
			count = newsDao.getNewsCountByUidByPerOrShar2(myUserId, nid,
					ntypeTree, null,displayStatus);
		}
		lnss = newsDao.getNstyles();
		return "news_del_trial_list";
	}
	
	/**
	 * 处理删除申请
	 * @return
	 * @throws ElException
	 */
	public String news_del_handle_list() throws ElException {
		//保存需要显示的状态:7.删除等待中
		this.displayStatus="7";
		//获取新闻树
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1 : ntype.getId());
		if (news != null) {
			newses = newsDao.listCombinationNew2(0, ntypeTree, news, getPageNow(),//引用组合搜索
			getPageSize(),displayStatus);
			count = newsDao.listCombinationNewCount2(0, ntypeTree, news,displayStatus);
		} else {
			String myUserId = String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
			newses = newsDao.getNewsByUidByPerOrShar2(myUserId, nid, ntypeTree,
					null, getPageNow(), getPageSize(),displayStatus);
			count = newsDao.getNewsCountByUidByPerOrShar2(myUserId, nid,
					ntypeTree, null,displayStatus);
		}
		lnss = newsDao.getNstyles();
		return "news_del_handle_list";
	}
	
	/**
	 * 新闻弹窗列表
	 * @return
	 * @throws ElException
	 */
	public String news_setpop_list() throws ElException {
		//保存需要显示的状态:6 已发布
		this.displayStatus="6,";//逗号有用
		//获取新闻树
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1 : ntype.getId());
		if (news != null) {
			newses = newsDao.listCombinationNew2(0, ntypeTree, news, getPageNow(),//引用组合搜索
			getPageSize(),displayStatus);
			count = newsDao.listCombinationNewCount2(0, ntypeTree, news,displayStatus);
		} else {
			String myUserId = String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
			newses = newsDao.getNewsByUidByPerOrShar2(myUserId, nid, ntypeTree,
					null, getPageNow(), getPageSize(),displayStatus);
			count = newsDao.getNewsCountByUidByPerOrShar2(myUserId, nid,
					ntypeTree, null,displayStatus);
		}
		lnss = newsDao.getNstyles();
		//获取弹窗新闻
		News newspop=newsDao.getNewsInPop(ntypeTree, nid);
		getRequest().setAttribute("newspop",newspop);
		return "news_setpop_list";
	}

	public String update_status() throws ElException {
		newsDao.update_status(news.getId(), status);
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		return news_list();
	}

	private Department depTree;
	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	} 
	
	public String news_addInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		} 
		if(ntypeTree.getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的新闻库");
			 return "error"; 
		}
		
		
		if(getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
			depTree = departmentDao.getDepTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		}else{
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		lnss = newsDao.getNstyles();
		return "news_add";
	}

	public String news_assignlist() throws ElException {
		String myUserId = String
				.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			// ntypeTree = newsDao
			// .getNtypeTreeByPerOrShar(
			// ElConstants.TREE_ROOT,
			// ElConstants.TREE_FIANL,
			// true,
			// String
			// .valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
			// true, "NEWSTYPE_USE_TYPE");
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);

		}
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1
				: ntype.getId());
		// int nid = ntype == null ? newsDao.getNtypeRoot().getId() :
		// (ntype.getId() == 0 ? 1 : ntype.getId());
		newses = newsDao.getNewsByUidByPerOrShar(myUserId, nid, ntypeTree, 2,
				getPageNow(), getPageSize());
		count = newsDao.getNewsCountByUidByPerOrShar(myUserId, nid, ntypeTree,
				2);
		return "news_assignlist";
	}

	public String newsassign() throws ElException {
		newsDao.update_status(news.getId(), status);
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		return news_assignlist();
	}

	public String news_add() throws ElException {
		news.setOwner(new ELUser(
						getSessionIntValue(ElConstants.SESSION_USERID)));
		//添加后第2状态为1：制作中,显示状态-1
		news.setStatus(-1);
		news.setStatus_tow(1);
		//newsDao.addNews(news);
		int id = newsDao.addNews2(news);
		
		String staddr[] = getRequest().getParameterValues("news.stuffs.description");
		String sttitle[] = getRequest().getParameterValues("news.stuffs.title");
		if (null != staddr) {
			for (int i = 0; i < staddr.length; i++) {
				String title = sttitle[i]==null||"".equals(sttitle[i].trim())?staddr[i].substring(staddr[i].lastIndexOf("/")+1):sttitle[i];
				newsDao.addKstuff(staddr[i], id ,title);
			}
		}
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_NEWS,
				ElLoggerConstants.LOG_TYPE_ADD,news.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,news.getId()); 
		//return "news_add_success";
		return "newsManage_list";
	}

	public String news_delete() throws ElException {
		newsDao.deleteNews(news.getId());
		/*
		 * newses = newsDao.getNewsByUid(
		 * getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(),
		 * getPageSize());
		 */
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		news=newsDao.getNewsById(news.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_NEWS,
				ElLoggerConstants.LOG_TYPE_DELETE,news.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,news.getId());
		return "news_delete";
	}
	
	public String newsStuff_delete() throws ElException {
		//newsDao.deleteNews(news.getId());
		newsDao.deleteNewsStuff(stuff.getId());
		return "news_alterInit";
	}

	public String news_alterInit() throws ElException {
		//判断状态是否可以编辑或者隐藏参数
		String isOk=getRequest().getParameter("isOk");
		if(isOk==null||!"1".equals(isOk.trim())){
			setElmessage("请正确操作新闻编辑！！！");
			return "error";
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		news = newsDao.getNewsById(news.getId());
		news.setStuffs(newsDao.listKstuff(news.getId()));
		lnss = newsDao.getNstyles();
		return "news_alter";
	}

	/**
	 * 我的新闻 取数据的规则为：当前人员所在的二级部门的管理员有使用权限的节点的新闻
	 * 
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	public String mynews() throws ElException {
		String userids = "";
		String myUserId = String
				.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
		int nid = ntype == null ? newsDao.getNtypeRoot().getId() : (ntype
				.getId() == 0 ? 1 : ntype.getId());
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			List<ELUser> userList = newsDao
					.findUserByMyDeptid(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT));
			for (ELUser user : userList) {
				if (!userids.equals("")) {
					userids += ",";
				}
				userids += user.getId();
			}
			myUserId = userids;
			if (!userids.equals("")) {
				ntypeTree = newsDao.getNtypeTreeByPerOrShar(
						ElConstants.TREE_ROOT, ElConstants.TREE_FIANL, true,
						myUserId, true, "NEWSTYPE_USE_TYPE");
			}
		}
		if (!myUserId.equals("")) {
			newses = newsDao.getNewsByUidByPerOrShar(myUserId, nid, ntypeTree,
					3, getPageNow(), getPageSize());
			count = newsDao.getNewsCountByUidByPerOrShar(myUserId, nid,
					ntypeTree, 3);
		}
		return "mynews";
	}

	public String combinationSearchNewsInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		lnss = newsDao.getNstyles();
		// lnts.add(ntypeTree) ;
		// lnts=newsDao.listNewsType();
		return "combinationSearchNews";
	}

	// 新闻组合搜素
	public String combinationSearchNews() throws ElException {
		/*
		 * String myUserId = String
		 * .valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
		 */
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			// ntypeTree = newsDao
			// .getNtypeTreeByPerOrShar(
			// ElConstants.TREE_ROOT,
			// ElConstants.TREE_FIANL,
			// true,
			// String
			// .valueOf(getSessionIntValue(ElConstants.SESSION_USERID)),
			// true, "NEWSTYPE_USE_TYPE");
			ntypeTree = newsDao.getNtypeTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true);
		}
		if (getRequest().getParameter("str") == null) {
			getSession().setAttribute("csn", news);
		} else {
			news = (News) getSession().getAttribute("csn");
		}
		getRequest().setAttribute("csstr", "combinationSearchNews");
		/*
		 * int nid = ntype == null ? newsDao.getNtypeRoot().getId() : (ntype
		 * .getId() == 0 ? 1 : ntype.getId()); news.getNtype().setId(nid);
		 */
//		newses = newsDao.listCombinationNew(0, ntypeTree, news, getPageNow(),
//				getPageSize());
//		count = newsDao.listCombinationNewCount(0, ntypeTree, news,
//				getPageNow(), getPageSize());
		newses = newsDao.listCombinationNew2(0, ntypeTree, news, getPageNow(),
				getPageSize(),"");
		count = newsDao.listCombinationNewCount2(0, ntypeTree, news,"");
		return "news_list";
	}
	
	private String newIds;

	public String getNewIds() {
		return newIds;
	}

	public void setNewIds(String newIds) {
		this.newIds = newIds;
	}
	
	public String upNewHot() throws ElException {
		newsDao.updateNewsHot(newIds, news.getHot());
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		String resultPage=getRequest().getParameter("resultPage");
		if(resultPage!=null){
			if("1".equals(resultPage)){
				return "combinationSearchNews";//重定向到action
			}else{
				return "news_end_trial_list";//重定向到action
			}
		}
		return "combinationSearchNews";//重定向到action
	}
	/**
	 * 设置新闻弹窗
	 * @return
	 * @throws ElException
	 */
	public String newsSetpop() throws ElException {
		//newsDao.updateNewsHot(newIds, news.getHot());
		newsDao.NewsSetpop(newIds);
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		return "news_setpop_list";
	}
	/**
	 * 取消弹窗新闻
	 * @return
	 * @throws ElException
	 */
	public String news_popNo() throws ElException {
		newsDao.update_newsIspop();
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		return "news_setpop_list";
	}

	public String news_alter() throws ElException {
		newsDao.alterNews(news);
		String staddr[] = getRequest().getParameterValues("news.stuffs.description");
		String sttitle[] = getRequest().getParameterValues("news.stuffs.title");
		String stid[] = getRequest().getParameterValues("news.stuffs.id");
		// 修改部分
		int addfrom = 0;
		if (stid != null) {
			addfrom = stid.length;
			for (int i = 0; i < stid.length; i++) {
				String title = sttitle[i]==null||"".equals(sttitle[i].trim())?staddr[i].substring(staddr[i].lastIndexOf("/")+1):sttitle[i];
				newsDao.alterNstuff(title, getIntValue(stid[i]));
			}
		}
		if (null != staddr) {
			for (int i = addfrom; i < staddr.length; i++) {
				String title = sttitle[i]==null||"".equals(sttitle[i].trim())?staddr[i].substring(staddr[i].lastIndexOf("/")+1):sttitle[i];
				newsDao.addKstuff(staddr[i], news.getId(),title);
			}
		}
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		news=newsDao.getNewsById(news.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_NEWS,
				ElLoggerConstants.LOG_TYPE_ALTER,news.getTitle(),
				ElLoggerConstants.LOG_RES_SUCC,news.getId());
		//return "news_alter_success";
		return "newsManage_list";
	}
	private int getIntValue(String value) {
		if (value == null)
			return 0;
		if (("").equals(value.trim()))
			return 0;
		int valuei = 0;
		try {
			valuei = new Integer(value).intValue();
		} catch (Exception e) {
			logger.error("数字转换错误",e);
		}
		return valuei;
	}

	private StuffLib stuff;
	String filename;
	InputStream inputStream;
	private static final Log logger = LogFactory.getLog(NewsAction.class);
	public String download_nstuff() throws ElException {
		try {
			stuff = newsDao.getNStuffLib(stuff.getId());
			filename = stuff.getDescription();// stuff.getId()+"."
												// +stuff.getFileext();
			filename = filename.substring(filename.indexOf("elstuffs"));

			String path = ServletActionContext.getServletContext().getRealPath(
					filename);
			String fileext = filename.substring(filename.lastIndexOf("."));
			String filename1 = stuff.getTitle() + fileext;
			filename = new String(filename1.getBytes(), "ISO8859-1");
			try {
				inputStream = new FileInputStream(path);

			} catch (Exception e) {
				logger.error("文档下载失败", e);
				throw new ElException("下载资料出错", e);
			}
		} catch (Exception e) {
			logger.error("文档下载失败", e);
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "download_nstuff";
	}
	public String news_view() throws ElException {
		news = newsDao.getNewsById(news.getId());

		return "news_view";
	}

	public String mynews_view() throws ElException {
		news = newsDao.getNewsById(news.getId());

		return "mynews_view";
	}

	public NewsType getNtypeTree() {
		return ntypeTree;
	}

	public void setNtypeTree(NewsType ntypeTree) {
		this.ntypeTree = ntypeTree;
	}

	public NewsType getNtype() {
		return ntype;
	}

	public void setNtype(NewsType ntype) {
		this.ntype = ntype;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public StuffLib getStuff() {
		return stuff;
	}

	public void setStuff(StuffLib stuff) {
		this.stuff = stuff;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public static Log getLogger() {
		return logger;
	}

	public List<NewsStyle> getLnss() {
		return lnss;
	}

	public void setLnss(List<NewsStyle> lnss) {
		this.lnss = lnss;
	}

}
