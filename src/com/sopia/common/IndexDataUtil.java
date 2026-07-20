package com.sopia.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.entities.Forum;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.newsandmess.entities.News;

public class IndexDataUtil {
	private static final Log logger = LogFactory.getLog(IndexDataUtil.class);
	private static Map<String, List<News>> indexNewsList;
	private static Map<String, List<Knowledge>> indexKnowledgeList;
	private static Map<String, List<Forum>> indexForumList;
	private static Map<String, List<Course>> indexCourseList;
	private static Map<String, List<ExamRoom>> indexExamRoomList;
	private static Map<String, List<ElClass>> indexElClassList;
	private static List<Department> indexDep;
	private static News indexPopNews;//首页弹窗信息
	private FrontDao frontDao=null;
	private ForumAdminDao forumAdminDao=null;
	
	public News getIndexPopNews() {
		if(null==indexPopNews){
			this.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		}
		return indexPopNews;
	}
	/**
	 * 加载首页新闻数据
	 * @throws ElException
	 */
	private void loadIndexNewsList() throws ElException {
		//新闻类别说明：10：新闻动态   11：经验交流   12：教学公告   13：帮助中心
		//新闻热度说明：0：普通   1：推荐   2:热门   3：重点   4：头条
		//获取 推荐 的 新闻动态
		indexNewsList=new HashMap<String, List<News>>();
		List<News> xydts = frontDao.listNewsByNsidhot(0, 11, ElConstants.NSTYLE_XWDT, ElConstants.HOT_TJ);
		if (xydts != null){
			for (int i = 0; i < xydts.size(); ++i) {
				String name = xydts.get(i).getTitle();
				if (i == 0){
					xydts.get(i).setTitle((name.length() > 11) ? name.substring(0,10) : name);
				}else{
					xydts.get(i).setTitle((name.length() > 18) ? name.substring(0,17)+ "..." : name);
				}
			}
		}
		indexNewsList.put("xydts", xydts);
		//获取 推荐 的 经验交流
		List<News> szlls = this.frontDao.listNewsByNsidhot(0, 11, ElConstants.NSTYLE_JYJL, ElConstants.HOT_TJ);
		if (szlls != null){
			for (int i = 0; i < szlls.size(); ++i) {
				String name =  szlls.get(i).getTitle();
				if (i == 0){
					szlls.get(i).setTitle((name.length() > 11) ? name.substring(0,10) : name);
				}else{
					szlls.get(i).setTitle((name.length() > 18) ? name.substring(0,17)+ "..." : name);
				}
			}
		}
		indexNewsList.put("szlls", szlls);
		//获取 推荐 的 教学公告
		List<News> zxzxs = this.frontDao.listNewsByNsidhot(0, 4, ElConstants.NSTYLE_JXGG, ElConstants.HOT_TJ);
		if (zxzxs != null) {
			for (int i = 0; i < zxzxs.size(); ++i) {
				String name = zxzxs.get(i).getTitle();
				if (i == 0){
					zxzxs.get(i).setTitle((name.length() > 11) ? name.substring(0,9)+ "..." : name);
				}else {
					zxzxs.get(i).setTitle((name.length() > 14) ? name.substring(0,13)+ "..." : name);
				}
				String content = zxzxs.get(i).getContent();
//				content = CheckHtml.getString(content);
				content = StringUtil.htmlParse_(content);
				zxzxs.get(i).setContent((content.length() > 21) ? content.substring(0, 19)+ "..." : content);
			}
		}
		indexNewsList.put("zxzxs", zxzxs);
		// 教学公告_图文_重点
		List<News> zxzxs_tw_zd = this.frontDao.listNewsByNsidhot(0, 1, ElConstants.NSTYLE_JXGG, ElConstants.HOT_ZD);
		if (zxzxs_tw_zd != null) {
			for (int i = 0; i < zxzxs_tw_zd.size(); ++i) {
				String name = zxzxs_tw_zd.get(i).getTitle();
				String Content = zxzxs_tw_zd.get(i).getContent();
				zxzxs_tw_zd.get(i).setTitle((name.length() > 11) ? name.substring(0, 9)+ "..." : name);
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				zxzxs_tw_zd.get(i).setContent((Content.length() > 21) ? Content.substring(0, 19)+ "..." : Content);
			}
		}
		indexNewsList.put("zxzxs_tw_zd", zxzxs_tw_zd);
		// 教学公告_文本_推荐
		List<News> zxzxs_wb_tj = this.frontDao.listNewsByTidhot(4, 0, ElConstants.NTYPE_JXGG, ElConstants.HOT_TJ);
		if (zxzxs_wb_tj != null) {
			for (int i = 0; i < zxzxs_wb_tj.size(); ++i) {
				String name = zxzxs_wb_tj.get(i).getTitle();
				zxzxs_wb_tj.get(i).setTitle((name.length() > 14) ? name.substring(0, 13)+ "..." : name);
			}
		}
		indexNewsList.put("zxzxs_wb_tj", zxzxs_wb_tj);
		//获取根节点下的最新新闻（首页上的flash用到）
		List<News> zxNews = this.frontDao.listNewsByTid(3, 0, 1, true, "");//不用截取?
		indexNewsList.put("zxNews", zxNews);
		// top居中的头条(根类别下)
		List<News> zxNewss = this.frontDao.listNewsByTidhot(2, 0, 1, ElConstants.HOT_TT);
		if (zxNewss != null) {
			for (int i = 0; i < zxNewss.size(); ++i) {
				String name = zxNewss.get(i).getTitle();
				zxNewss.get(i).setTitle((name.length() > 20) ? name.substring(0, 20)+ "..." : name);
				String content = ((News) zxNewss.get(i)).getContent();
//				content = CheckHtml.getString(content);
				content = StringUtil.htmlParse_(content);
				zxNewss.get(i).setContent((content.length() > 56) ? content.substring(0, 53)+ "..." : content);
			}
		}
		indexNewsList.put("zxNewss", zxNewss);
		// top居中的头条 (一图文 3文本)(根节点下)
		List<News> zxNewss_tw_wb = this.frontDao.listNewsByTidhot(4, 0, 1, ElConstants.HOT_TT);
		if (zxNewss_tw_wb != null) {
			for (int i = 0; i < zxNewss_tw_wb.size(); ++i) {
				String name = zxNewss_tw_wb.get(i).getTitle();
				if (i == 0){
					zxNewss_tw_wb.get(i).setTitle((name.length() > 20) ? name.substring(0,20)+ "..." : name);
				}else {
					zxNewss_tw_wb.get(i).setTitle((name.length() > 14) ? name.substring(0,13)+ "..." : name);
				}
				String Content = zxNewss_tw_wb.get(i).getContent();
				//Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				zxNewss_tw_wb.get(i).setContent((Content.length() > 56) ? Content.substring(0, 53)+ "..." : Content);
			}
		}
		indexNewsList.put("zxNewss_tw_wb", zxNewss_tw_wb);
		// 帮助中心 推荐
		List<News> zxlxxy = this.frontDao.listHotNnowsByNewsStyle(5, 0, ElConstants.NSTYLE_BZZX, ElConstants.HOT_TJ);
		if(zxlxxy!=null){
			for (int i = 0; i < zxlxxy.size(); ++i) {
				String name = zxlxxy.get(i).getTitle();
				String Content = zxlxxy.get(i).getContent() == null ? "": zxlxxy.get(i).getContent();
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				zxlxxy.get(i).setTitle((name.length() > 10) ? name.substring(0, 10)+ "..." : name);
				zxlxxy.get(i).setContent((Content.length() > 20) ? Content.substring(0,20)+ "..." : Content);
			}
		}
		indexNewsList.put("zxlxxy", zxlxxy);
		// 帮助中心_New （图文——重点）
		List<News> zxlxxy_tw_zd = this.frontDao.listHotNnows(1, 0, ElConstants.NTYPE_BZZX, ElConstants.HOT_ZD);
		if(zxlxxy_tw_zd!=null){
			for (int i = 0; i < zxlxxy_tw_zd.size(); ++i) {
				String name = zxlxxy_tw_zd.get(i).getTitle();
				String Content =  zxlxxy_tw_zd.get(i).getContent() == null ? "": zxlxxy_tw_zd.get(i).getContent();
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				zxlxxy_tw_zd.get(i).setTitle((name.length() > 10) ? name.substring(0, 10)+ "..." : name);
				zxlxxy_tw_zd.get(i).setContent((Content.length() > 20) ? Content.substring(0,20)+ "..." : Content);
			}
		}
		indexNewsList.put("zxlxxy_tw_zd", zxlxxy_tw_zd);
		// 帮助中心_New （文本——推荐）
		List<News> zxlxxy_wb_tj = this.frontDao.listHotNnows(4, 0, ElConstants.NTYPE_BZZX, ElConstants.HOT_TJ);
		if(zxlxxy_wb_tj!=null){
			for (int i = 0; i < zxlxxy_wb_tj.size(); ++i) {
				String name = ((News) zxlxxy_wb_tj.get(i)).getTitle();
				String Content = zxlxxy_wb_tj.get(i).getContent() == null ? "": zxlxxy_wb_tj.get(i).getContent();
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				zxlxxy_wb_tj.get(i).setTitle((name.length() > 15) ? name.substring(0, 15)+ "..." : name);
			}
		}
		indexNewsList.put("zxlxxy_wb_tj", zxlxxy_wb_tj);
		// 下载中心_文本 4条 推荐
		List<News> zxxzzx = this.frontDao.listNewsByTidhot(4, 0, ElConstants.NTYPE_XXZX, ElConstants.HOT_TJ);
		if(zxxzzx!=null){
			for (int i = 0; i < zxxzzx.size(); ++i) {
				String name = ((News) zxxzzx.get(i)).getTitle();
				String Content = zxxzzx.get(i).getContent() == null ? "": zxxzzx.get(i).getContent();
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				zxxzzx.get(i).setTitle((name.length() > 15) ? name.substring(0, 15)+ "..." : name);
				zxxzzx.get(i).setContent((Content.length() > 20) ? Content.substring(0,20)+ "..." : Content);
			}
		}
		indexNewsList.put("zxxzzx", zxxzzx);
		// 下载中心_图文重点 1条
		List<News> zxxzzx_tw_zd = this.frontDao.listNewsByTidhot(1, 0, ElConstants.NTYPE_XXZX, ElConstants.HOT_ZD);
		if(zxxzzx_tw_zd!=null){
			for (int i = 0; i < zxxzzx_tw_zd.size(); ++i) {
				String name = zxxzzx_tw_zd.get(i).getTitle();
				String Content = zxxzzx_tw_zd.get(i).getContent() == null ? "": zxxzzx_tw_zd.get(i).getContent();
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				zxxzzx_tw_zd.get(i).setTitle((name.length() > 10) ? name.substring(0, 10)+ "..." : name);
				zxxzzx_tw_zd.get(i).setContent((Content.length() > 20) ? Content.substring(0,20)+ "..." : Content);
			}
		}
		indexNewsList.put("zxxzzx_tw_zd", zxxzzx_tw_zd);
		indexPopNews = this.frontDao.getNewsInPop();
	}
	/**
	 * 加载首页帖子信息
	 * @throws ElException
	 */
	private void loadIndexForumList() throws ElException {
		indexForumList=new HashMap<String, List<Forum>>();
		// 推荐交流文章
		List<Forum> rmforums = this.forumAdminDao.listForumsByRm(0, 7);
		if (rmforums != null) {
			for (int i = 0; i < rmforums.size(); ++i) {
				String name = rmforums.get(i).getTitle();
				rmforums.get(i).setTitle((name.length() > 15) ? name.substring(0, 15)+ "..." : name);
			}
		}
		indexForumList.put("rmforums", rmforums);
		// 最新交流文章
		List<Forum> zxforums = this.forumAdminDao.listForumsByZx(7, 0);
		if (zxforums != null) {
			for (int i = 0; i < zxforums.size(); ++i) {
				String name = zxforums.get(i).getTitle();
				zxforums.get(i).setTitle((name.length() > 15) ? name.substring(0, 15)+ "..." : name);
			}
		}
		indexForumList.put("zxforums", zxforums);
	}
	/**
	 * 加载首页课程信息
	 * @throws ElException
	 */
	private void loadIndexCourseList() throws ElException {
		indexCourseList=new HashMap<String, List<Course>>();
		// 最新课程(根节点下)
		List<Course> zxCourses = this.frontDao.listCourseByType(11, 0, 1, true);
		if (zxCourses != null){
			for (int i = 0; i < zxCourses.size(); ++i) {
				String name = zxCourses.get(i).getName();
				zxCourses.get(i).setName((name.length() > 15) ? name.substring(0, 14)+ "..." : name);
			}
		}
		indexCourseList.put("zxCourses", zxCourses);
		// 热门课程
		List<Course> hotCourses = this.frontDao.listCourseByHot(4, 0, 2);// 2热门
		if (hotCourses != null){
			for (int i = 0; i < hotCourses.size(); ++i) {
				String name = hotCourses.get(i).getName();
				hotCourses.get(i).setName((name.length() > 11) ? name.substring(0, 10)+ "..." : name);
			}
		}
		indexCourseList.put("hotCourses", hotCourses);
		// 最新课程
		List<Course> newCourses = this.frontDao.listCourseByNewTime(4, 0, 1);// 1可申请 0不可申请
		if (newCourses != null){
			for (int i = 0; i < newCourses.size(); ++i) {
				String name = newCourses.get(i).getName();
				newCourses.get(i).setName((name.length() > 11) ? name.substring(0, 10)+ "..." : name);
			}
		}
		indexCourseList.put("newCourses", newCourses);
	}
	/**
	 * 加载首页资源信息
	 * @throws ElException
	 */
	private void loadIndexKnowledgeList() throws ElException {
		indexKnowledgeList=new HashMap<String, List<Knowledge>>();
		//最新资源
		List<Knowledge> zxKnows = this.frontDao.listZxKnows(8, 0);
		if(zxKnows!=null){
			for (int i = 0; i < zxKnows.size(); ++i) {
				String name = zxKnows.get(i).getTitle();
				zxKnows.get(i).setTitle((name.length() > 13) ? name.substring(0, 11)+ "..." : name);
				name = zxKnows.get(i).getContent();
				name = CheckHtml.getString(name);
				zxKnows.get(i).setContent((name.length() > 21) ? name.substring(0, 20)+ "..." : name);
			}
		}
		indexKnowledgeList.put("zxKnows", zxKnows);
		// 推荐资源
		List<Knowledge> tjKnows = this.frontDao.listHotKnows(8, 0, 1);
		if(tjKnows!=null){
			for (int i = 0; i < tjKnows.size(); ++i) {
				String name = tjKnows.get(i).getTitle();
				String Content = tjKnows.get(i).getContent();
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				tjKnows.get(i).setTitle((name.length() > 10) ? name.substring(0, 10)+ "..." : name);
				tjKnows.get(i).setContent((Content.length() > 20) ? Content.substring(0,20)+ "..." : Content);
			}
		}
		indexKnowledgeList.put("tjKnows", tjKnows);
		// 推荐资源_New (第一条 图文 hot 为重点)
		List<Knowledge> tjKnows_tw_zd = this.frontDao.listHotKnows(1, 0, 3);
		if(tjKnows_tw_zd!=null){
			for (int i = 0; i < tjKnows_tw_zd.size(); ++i) {
				String name = tjKnows_tw_zd.get(i).getTitle();
				String Content = tjKnows_tw_zd.get(i).getContent();
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				tjKnows_tw_zd.get(i).setTitle((name.length() > 10) ? name.substring(0, 10)+ "..." : name);
				tjKnows_tw_zd.get(i).setContent((Content.length() > 20) ? Content.substring(0, 20) + "...": Content);
			}
		}
		indexKnowledgeList.put("tjKnows_tw_zd", tjKnows_tw_zd);
		// 推荐资源_New (剩余4条 图文 hot 为推荐)
		List<Knowledge> tjKnows_wb_tj = this.frontDao.listHotKnows(5, 0, 1);
		if(tjKnows_wb_tj!=null){
			for (int i = 0; i < tjKnows_wb_tj.size(); ++i) {
				String name = tjKnows_wb_tj.get(i).getTitle();
				String Content = tjKnows_wb_tj.get(i).getContent();
//				Content = CheckHtml.getString(Content);
				Content = StringUtil.htmlParse_(Content);
				tjKnows_wb_tj.get(i).setTitle((name.length() > 15) ? name.substring(0, 15)+ "..." : name);
			}
		}
		indexKnowledgeList.put("tjKnows_wb_tj", tjKnows_wb_tj);
	}
	/**
	 * 加载首页考场信息
	 * @throws ElException
	 */
	private void loadIndexExamRoomList() throws ElException {
		indexExamRoomList=new HashMap<String, List<ExamRoom>>();
		// 在线考场
		List<ExamRoom> newErooms = this.frontDao.listExamRoomByNewTime(4, 0, 1);// 1可申请 0不可申请
		if (newErooms != null){
			for (int i = 0; i < newErooms.size(); ++i) {
				String name = newErooms.get(i).getTitle();
				newErooms.get(i).setTitle((name.length() > 11) ? name.substring(0, 10)+ "..." : name);
			}
		}
		indexExamRoomList.put("newErooms", newErooms);
	}
	/**
	 * 加载首页培训班信息
	 * @throws ElException
	 */
	private void loadIndexElClassList() throws ElException {
		indexElClassList=new HashMap<String, List<ElClass>>();
		// 在线培训班
		List<ElClass> newelclasss = this.frontDao.listClassByNewTime(4, 0, 1);// 1可申请 0不可申请
		if (newelclasss != null){
			for (int i = 0; i < newelclasss.size(); ++i) {
				String name = newelclasss.get(i).getName();
				newelclasss.get(i).setName((name.length() > 11) ? name.substring(0, 10)+ "..." : name);
			}
		}
		indexElClassList.put("newelclasss", newelclasss);
	}
	/**加载二级部门
	 * @throws ElException
	 */
	private void loadIndexDept() throws ElException {
		indexDep = this.frontDao.listDeptByIssp();
	}
	/**
	 * 加载首页所有数据
	 * @throws ElException
	 */
	private void loadIndexAll() throws ElException {
		this.loadIndexNewsList();
		this.loadIndexForumList();
		this.loadIndexKnowledgeList();
		this.loadIndexCourseList();
		this.loadIndexExamRoomList();
		this.loadIndexElClassList();
		this.loadIndexDept();
	}
	/**
	 * 根据模块加载首页信息
	 * @param model
	 * @throws ElException
	 */
	public void loadIndexInfo(int model){
		if(this.frontDao==null){
			this.frontDao=(FrontDao)SpringContextUtil.getBean("frontDao");
		}
		if(this.forumAdminDao==null){
			this.forumAdminDao=(ForumAdminDao)SpringContextUtil.getBean("forumAdminDao");
		}
		try {
			if(model==ElConstants.INDEX_MODEL_ALL){
				this.loadIndexAll();
			}else if(model==ElConstants.INDEX_MODEL_NEWS){
				this.loadIndexNewsList();
			}else if(model==ElConstants.INDEX_MODEL_FORUM){
				this.loadIndexForumList();
			}else if(model==ElConstants.INDEX_MODEL_KNOWLEDGE){
				this.loadIndexKnowledgeList();
			}else if(model==ElConstants.INDEX_MODEL_COURSE){
				this.loadIndexCourseList();
			}else if(model==ElConstants.INDEX_MODEL_EXAMROOM){
				this.loadIndexExamRoomList();
			}else if(model==ElConstants.INDEX_MODEL_DEP){
				this.loadIndexDept();
			}else {
				this.loadIndexElClassList();
			}
		} catch (ElException e) {
			logger.error("加载首页数据出错！！！",e);
		}
	}
	/**
	 * 获取首页新闻数据
	 * @param key
	 * @return
	 */
	public List<News> getIndexNewsList(String key){
		if (null == indexNewsList){
			this.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS);
		}
		if(null!=indexNewsList){
			return indexNewsList.get(key);
		}
		return null;
	}
	/**
	 * 获取首页帖子数据
	 * @param key
	 * @return
	 */
	public List<Forum> getIndexForumList(String key){
		if (null == indexForumList){
			this.loadIndexInfo(ElConstants.INDEX_MODEL_FORUM);
		}
		if(null!=indexForumList){
			return indexForumList.get(key);
		}
		return null;
	}
	/**
	 * 获取二级部门
	 * @param key
	 * @return
	 */
	public List<Department> getIndexDeps(){
		try {
			if (null == indexDep){
				this.loadIndexDept();
			}
			if(null!=indexDep){
				return indexDep;
			}
		} catch (Exception e) {
			logger.error("加载首页数据出错！！！",e);
		}
		return null;
	}
	/**
	 * 获取首页资料数据
	 * @param key
	 * @return
	 */
	public List<Knowledge> getIndexKnowledgeList(String key){
		if (null == indexKnowledgeList){
			this.loadIndexInfo(ElConstants.INDEX_MODEL_KNOWLEDGE);
		}
		if(null!=indexKnowledgeList){
			return indexKnowledgeList.get(key);
		}
		return null;
	}
	/**
	 * 获取首页课程数据
	 * @param key
	 * @return
	 */
	public List<Course> getIndexCourseList(String key){
		if (null == indexCourseList){
			this.loadIndexInfo(ElConstants.INDEX_MODEL_COURSE);
		}
		if(null!=indexCourseList){
			return indexCourseList.get(key);
		}
		return null;
	}
	/**
	 * 获取首页考场数据
	 * @param key
	 * @return
	 */
	public List<ExamRoom> getIndexExamroomList(String key){
		if (null == indexExamRoomList){
			this.loadIndexInfo(ElConstants.INDEX_MODEL_EXAMROOM);
		}
		if(null!=indexExamRoomList){
			return indexExamRoomList.get(key);
		}
		return null;
	}
	/**
	 * 获取首页培训班数据
	 * @param key
	 * @return
	 */
	public List<ElClass> getIndexElclassList(String key){
		if (null == indexElClassList){
			this.loadIndexInfo(ElConstants.INDEX_MODEL_ELCLASS);
		}
		if(null!=indexElClassList){
			return indexElClassList.get(key);
		}
		return null;
	}
}
