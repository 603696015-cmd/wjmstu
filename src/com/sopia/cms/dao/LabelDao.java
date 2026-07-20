package com.sopia.cms.dao;

import java.util.List;

import com.sopia.cms.LabelModel;
import com.sopia.cms.entities.Label;
import com.sopia.cms.entities.Template;
import com.sopia.common.ElException;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;

public interface LabelDao {

	public abstract void addLabel(Label lb) throws ElException;

	public abstract Label getLabel(int id) throws ElException;
	
	public List<Label> listAllLabel()throws ElException;
	
	public void deleteLabel(int id) throws ElException;
	
	public void updateLabel(Label lb) throws ElException;
	
	/*********获取数据操作**********/
	public List<NewsType> getNewsTypesAll()throws ElException;  
	public List<News> getNews(LabelModel lbm) throws ElException;
	
	public List<Forum> getForums(LabelModel lbm)throws ElException;
	public List<ForumBlockType> getForumBlockTypeAll() throws ElException;
	
	public List<Knowledge> getknowledge(LabelModel lbm) throws ElException;
	public List<KnowledgeType> getKnowledgeType() throws ElException ;
	
	public List<Course> getCourse(LabelModel lbm) throws ElException;
	public List<CourseType> getCourseType() throws ElException;
}