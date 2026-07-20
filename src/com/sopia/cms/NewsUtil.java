package com.sopia.cms;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.sopia.cms.dao.LabelDao;
import com.sopia.courseman.entities.Course;
import com.sopia.forumman.entities.Forum;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.newsandmess.entities.News;

public class NewsUtil {
	@SuppressWarnings("unchecked")
	public void newsJsp(String jspTempPath, String modelPartPath,
			String savePath,LabelDao labelDao) throws Exception {
		IoUtil iu = new IoUtil();
		String jspTemp = iu.GetTemplate(jspTempPath);
		List<String> lbs = iu.getLabels(jspTemp);
		Hashtable<String, LabelModel> lbInfo = iu.getLabelInfo(lbs);
		Enumeration e = lbInfo.keys();
		Hashtable<String, String> lbTxt = new Hashtable<String, String>();
		NewsUtil conUtil = new NewsUtil();
		while (e.hasMoreElements()) {
			String key = e.nextElement().toString();
			LabelModel lbm = lbInfo.get(key);
			String txt="";
			// 其他模块的判断
			if (lbm.getModelType().equals("XW")) {
				// 列表类型判断
				if (lbm.getLabelType().equals("TW")) {
					txt = NewsUtil.isNullStr(conUtil.getTWNews(labelDao, lbm));
				} else if (lbm.getLabelType().equals("LB")) {
					txt = NewsUtil.isNullStr(conUtil.getLBNews(labelDao, lbm));
				}
			}else if(lbm.getModelType().equals("LT")){
				if (lbm.getLabelType().equals("LB")) {
					txt = NewsUtil.isNullStr(conUtil.getLBLT(labelDao, lbm));
				}
			}else if(lbm.getModelType().equals("ZS")){
				if (lbm.getLabelType().equals("LB")) {
					txt = NewsUtil.isNullStr(conUtil.getLBZS(labelDao, lbm));
				}else if (lbm.getLabelType().equals("TW")){
					txt = NewsUtil.isNullStr(conUtil.getTWZS(labelDao, lbm));
				}
			}else if(lbm.getModelType().equals("KC")){
				if (lbm.getLabelType().equals("LB")) {
					txt =NewsUtil.isNullStr(conUtil.getLBKC(labelDao, lbm));
				}else if (lbm.getLabelType().equals("TW")){
					txt =NewsUtil.isNullStr(conUtil.getTWKC(labelDao, lbm));
				}
			}
			lbTxt.put(key, txt);
		}
		String resultContent = iu.convertTxt(jspTemp, lbTxt);
		iu.saveDoc(resultContent, savePath);
	}

	/**
	 * 新闻信息-图文
	 * @param frontDao
	 * @param lbm
	 * @return
	 * @throws Exception
	 */
	public String getTWNews(LabelDao labelDao, LabelModel lbm) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<News> newsList = labelDao.getNews(lbm);
		int sque = 1;
		for (News news : newsList) {  
			sb.append("<A class=content1 href=\"newsIndexView.action?news.id="+ news.getId() + "\"");
			sb.append("target=_blank><IMG border=0");
			sb.append("src=\"" + news.getMainimg()
					+ "\" width=70 height=50></A>"); 
			sb.append("<A href=\"newsIndexView.action?news.id=" + news.getId()
					+ "\" target=_blank> ");
			sb.append( new IoUtil().getSubString(news.getTitle(), lbm
							.getTitleLength()) + "</A>"); 
			sb.append("<div>"+new IoUtil().getSubString(news.getContent(), lbm
							.getContentLength())  );
			sb.append("</div>");
		}
		return sb.toString();
	}

	/**
	 * 新闻信息-列表
	 * @param frontDao
	 * @param lbm
	 * @return
	 * @throws Exception
	 */
	public String getLBNews(LabelDao labelDao, LabelModel lbm) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<News> newsList = labelDao.getNews(lbm);
		for (News news : newsList) {
			sb.append("<A href=\"newsIndexView.action?news.id=" + news.getId()+"\" target=_blank>"
							+ new IoUtil().getSubString(news.getTitle(),
									lbm.getTitleLength()));
			sb.append("</A>");
		}
		return sb.toString();
	} 

	
	/**
	 * 论坛-列表
	 * @param labelDao
	 * @param lbm
	 * @return
	 * @throws Exception
	 */
	public String getLBLT(LabelDao labelDao, LabelModel lbm) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Forum> forumList = labelDao.getForums(lbm);
		int sque = 1;
		for (Forum forum : forumList) { 
			sb
					.append("<A href=\"forumView.action?forum.id=" + forum.getId()+"\" target=_blank>"
							+ new IoUtil().getSubString(forum.getTitle(),
									lbm.getTitleLength()));
			sb.append("</A>"); 
		}
		return sb.toString();
	}
	/**
	 * 知识-列表
	 * @param labelDao
	 * @param lbm
	 * @return
	 * @throws Exception
	 */
	public String getLBZS(LabelDao labelDao, LabelModel lbm) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Knowledge> knowledgeList = labelDao.getknowledge(lbm);
		int sque = 1;
		for (Knowledge knowledge : knowledgeList) { 
			sb
					.append("<A href=\"knowledge_center_view.action?knowledge.id=" + knowledge.getId()+"\" target=_blank>"
							+ new IoUtil().getSubString(knowledge.getTitle(),
									lbm.getTitleLength()));
			sb.append("</A>"); 
		}
		return sb.toString();
	}
	public String getTWZS(LabelDao labelDao, LabelModel lbm) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Knowledge> knowledgeList = labelDao.getknowledge(lbm);
		int sque = 1;
		for (Knowledge knowledge : knowledgeList) { 
			sb
					.append("<A class=content1 href=\"knowledge_center_view.action?knowledge.id="+ knowledge.getId() + "\"");
			sb.append("<A class=content1 href=\"knowledge_center_view.action?knowledge.id="+ knowledge.getId() + "\"");
			sb.append("target=_blank><IMG border=0");
			sb.append("src=\"" + knowledge.getMainimg()
					+ "\" width=70 height=50></A>"); 
			sb.append("<A href=\"knowledge_center_view.action?knowledge.id=" + knowledge.getId()
					+ "\" target=_blank> ");
			sb.append( new IoUtil().getSubString(knowledge.getTitle(), lbm
							.getTitleLength()) + " </A>"); 
			sb.append( new IoUtil().getSubString(knowledge.getContent(), lbm
							.getContentLength()) + " "); 
		}
		return sb.toString();
	}
	/**
	 * 课程-列表
	 * @param labelDao
	 * @param lbm
	 * @return
	 * @throws Exception
	 */
	public String getLBKC(LabelDao labelDao, LabelModel lbm) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Course> courseList = labelDao.getCourse(lbm);
		int sque = 1;
		for (Course course : courseList) { 
			sb
					.append("<A href=\"courseIndexView.action?course.id=" + course.getId()+"\" target=_blank>"
							+ new IoUtil().getSubString(course.getName(),
									lbm.getTitleLength()));
			sb.append("</A>"); 
		}
		return sb.toString();
	} 
	public String getTWKC(LabelDao labelDao, LabelModel lbm) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Course> courseList = labelDao.getCourse(lbm);
		int sque = 1;
		for (Course course : courseList) { 
			sb
					.append("<A class=content1 href=\"courseIndexView.action?course.id="+ course.getId() + "\"");
			sb.append("<A class=content1 href=\"courseIndexView.action?course.id="+ course.getId() + "\"");
			sb.append("target=_blank><IMG border=0");
			sb.append("src=\"" + course.getMainimg()
					+ "\" width=70 height=50></A>"); 
			sb.append("<A href=\"courseIndexView.action?course.id=" + course.getId()
					+ "\" target=_blank> ");
			sb.append( new IoUtil().getSubString(course.getName(), lbm
							.getTitleLength()) + " </A>");
			 
			sb.append( new IoUtil().getSubString(course.getDescription(), lbm
							.getContentLength()) + " ");
			 
		}
		return sb.toString();
	} 
	/**
	 * 判断数据是否为空
	 * @param recordStr
	 * @return
	 */
	public static String isNullStr(String recordStr){
		if(null==recordStr||recordStr.trim().equals("")){
			recordStr="暂无数据！";
		}
		return recordStr;
	} 
}
