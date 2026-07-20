package com.sopia.pfms.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.dao.PfmsNewsDao;
import com.sopia.pfms.entities.PfmsUser;

public class PfmsNewsAction extends BaseAction {
	private PfmsNewsDao pfmsNewsDao;
	private NewsDao newsDao;
	private IndexDao indexDao;
	private NewsType ntypeTree;
	private NewsType ntype;
	private List<News> newsList;
	private int count;
	private News news;
	private PfmsUser pfmsUser;
	private IndexDataUtil indexDataUtil;
	private String newsesids;

	public String getNewsesids() {
		return newsesids;
	}

	public void setNewsesids(String newsesids) {
		this.newsesids = newsesids;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}
	
	public IndexDao getIndexDao() {
		return indexDao;
	}

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}

	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}

	public String newsList() throws ElException{
		ntypeTree = newsDao.getNtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		
		int nid = ntype == null ? ntypeTree.getId() : (ntype.getId() == 0 ? 1
				: ntype.getId());
		newsList = pfmsNewsDao.newsList(getSessionIntValue(ElConstants.SESSION_USERID),nid,ntypeTree,news,
				getPageNow(), getPageSize());
		count = pfmsNewsDao.newsListCount(getSessionIntValue(ElConstants.SESSION_USERID), nid, ntypeTree, news);
		return "pfmsNews_success";
	}
	
	public String pfms_news_addInit() throws ElException{
		ntypeTree = newsDao.getNtypeTree(
				getSessionIntValue(ElConstants.SESSION_USERID),
				ElConstants.TREE_FIANL, true);
		if(ntypeTree.getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的新闻库");
			 return "error"; 
		}
		return "pfms_news_addInit";
	}
	
	public String pfms_news_add() throws ElException{
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
		
		return "pfms_news_add_success";
	}
	
	public String alterPicture_init() throws ElException{
		pfmsUser = indexDao.getUser(getSessionIntValue(ElConstants.SESSION_USERID), false);
		return "alterPicture_init";
	}
	
	public String alterPicture() throws ElException{
		indexDao.alterPictures(pfmsUser,getSessionIntValue(ElConstants.SESSION_USERID));
		return "alterPicture_success";
	}
	
	public String deleteNewses() throws ElException{
		if(newsesids!=null){
			String[] newsesidss=newsesids.split(",");
			for (int i = 0; i < newsesidss.length; i++) {
				newsDao.deleteNews(Integer.parseInt(newsesidss[i]));
			}
		}
		return "deleteNewses";
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public PfmsNewsDao getPfmsNewsDao() {
		return pfmsNewsDao;
	}

	public void setPfmsNewsDao(PfmsNewsDao pfmsNewsDao) {
		this.pfmsNewsDao = pfmsNewsDao;
	}
	public List<News> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
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
	

}
