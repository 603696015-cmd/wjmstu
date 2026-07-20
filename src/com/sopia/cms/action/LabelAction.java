package com.sopia.cms.action;

import java.util.List;

import com.sopia.BaseAction; 
import com.sopia.cms.dao.LabelDao;
import com.sopia.cms.entities.Label;
import com.sopia.cms.impl.LabelDaoImpl; 
import com.sopia.common.ElException;
import com.sopia.courseman.entities.CourseType;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.newsandmess.entities.NewsType; 

public class LabelAction extends BaseAction {
	private Label label;
	private List<Label> labelList;
	private LabelDao labelDao;
	private List<NewsType> newsTypeList;
	private List<KnowledgeType> knowledgeTypeList;
	private List<CourseType> courseTypeList;
	private List<ForumBlockType> forumBlockTypeList;
	
	
	public List<KnowledgeType> getKnowledgeTypeList() {
		return knowledgeTypeList;
	}

	public void setKnowledgeTypeList(List<KnowledgeType> knowledgeTypeList) {
		this.knowledgeTypeList = knowledgeTypeList;
	}

	public List<CourseType> getCourseTypeList() {
		return courseTypeList;
	}

	public void setCourseTypeList(List<CourseType> courseTypeList) {
		this.courseTypeList = courseTypeList;
	}

	public List<ForumBlockType> getForumBlockTypeList() {
		return forumBlockTypeList;
	}

	public void setForumBlockTypeList(List<ForumBlockType> forumBlockTypeList) {
		this.forumBlockTypeList = forumBlockTypeList;
	}

	public List<NewsType> getNewsTypeList() {
		return newsTypeList;
	}

	public void setNewsTypeList(List<NewsType> newsTypeList) {
		this.newsTypeList = newsTypeList;
	}

	public LabelDao getLabelDao() {
		return labelDao;
	}

	public void setLabelDao(LabelDao labelDao) {
		this.labelDao = labelDao;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}  
	
	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}

	public String label_addInit()throws ElException{
		labelDao=new LabelDaoImpl();
		newsTypeList=labelDao.getNewsTypesAll();
		courseTypeList=labelDao.getCourseType();
		knowledgeTypeList=labelDao.getKnowledgeType();
		forumBlockTypeList=labelDao.getForumBlockTypeAll();
		return "label_add";
	}
	
	public String label_add()throws ElException{  
		StringBuffer sbCode=new StringBuffer("{CMS_"+label.getViewType());
		sbCode.append("_"+label.getModelType());
		sbCode.append("_ID"+label.getModelId());
		sbCode.append("_"+label.getRecord());
		sbCode.append("_"+label.getTitleLength());
		sbCode.append("_"+label.getContentType());
		sbCode.append("_"+label.getRow());
		sbCode.append("_class="+label.getStyle()+"}");
		
		label.setCode(sbCode.toString());		
		labelDao=new LabelDaoImpl();
		labelDao.addLabel(label);
		return "label_list";
	}
	
	public String label_alterInit()throws ElException{
		labelDao=new LabelDaoImpl(); 
		newsTypeList=labelDao.getNewsTypesAll();
		courseTypeList=labelDao.getCourseType();
		knowledgeTypeList=labelDao.getKnowledgeType();
		forumBlockTypeList=labelDao.getForumBlockTypeAll();
		label=labelDao.getLabel(label.getId());
		return "label_alter";
	}
	
	public String label_alter()throws ElException{
		labelDao=new LabelDaoImpl();
		StringBuffer sbCode=new StringBuffer("{CMS_"+label.getViewType());
		sbCode.append("_"+label.getModelType());
		sbCode.append("_ID"+label.getModelId());
		sbCode.append("_"+label.getRecord());
		sbCode.append("_"+label.getTitleLength());
		sbCode.append("_"+label.getContentType());
		sbCode.append("_"+label.getRow());
		sbCode.append("_class="+label.getStyle()+"}");
		
		label.setCode(sbCode.toString());		
		labelDao.updateLabel(label);
		return "label_list";
	}
	
	public String label_list()throws ElException{
		labelDao=new LabelDaoImpl();
		labelList=labelDao.listAllLabel();
		return "label_list";
	}
	public String label_deleteById()throws ElException{
		labelDao=new LabelDaoImpl();
		labelDao.deleteLabel(label.getId());
		return "label_list";
	} 
}
