package com.sopia.cms.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.cms.CMSConstants;
import com.sopia.cms.LabelModel;
import com.sopia.cms.dao.LabelDao;
import com.sopia.cms.entities.Label;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.knowledgeman.entities.KnowledgeType;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;

public class LabelDaoImpl implements LabelDao {
	private static final Log logger = LogFactory.getLog(LabelDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sopia.cms.impl.LabelDao#addLabel(com.sopia.cms.entities.Label)
	 */
	public void addLabel(Label lb) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSLABEL_ADD));
			ps.setString(1, lb.getName());
			ps.setString(2, lb.getCode());
			ps.setString(3, lb.getStyle());
			ps.setInt(4, lb.getModelId());
			ps.setString(5, lb.getModelType());
			ps.setString(6, lb.getViewType());
			ps.setInt(7, lb.getRecord());
			ps.setInt(8, lb.getContentType());
			ps.setInt(9, lb.getTitleLength());
			ps.setInt(10, lb.getRow());
			ps.setInt(11, lb.getContentLength());
			ps.setString(12, lb.getRemark());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("添加自定义标签失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void updateLabel(Label lb) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSLABEL_UPDATE));
			ps.setString(1, lb.getName());
			ps.setString(2, lb.getCode());
			ps.setString(3, lb.getStyle());
			ps.setInt(4, lb.getModelId());
			ps.setString(5, lb.getModelType());
			ps.setString(6, lb.getViewType());
			ps.setInt(7, lb.getRecord());
			ps.setInt(8, lb.getContentType());
			ps.setInt(9, lb.getTitleLength());
			ps.setInt(10, lb.getRow());
			ps.setInt(11, lb.getContentLength());
			ps.setString(12, lb.getRemark());
			ps.setInt(13, lb.getId());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("修改自定义标签失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Label getLabel(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Label label = new Label();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSLABEL_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				label.setId(rs.getInt(1));
				label.setName(rs.getString(2));
				label.setCode(rs.getString(3));
				label.setStyle(rs.getString(4));
				label.setModelId(rs.getInt(5));
				label.setModelType(rs.getString(6));
				label.setViewType(rs.getString(7));
				label.setRecord(rs.getInt(8));
				label.setContentType(rs.getInt(9));
				label.setTitleLength(rs.getInt(10));
				label.setRow(rs.getInt(11));
				label.setContentLength(rs.getInt(12));
				label.setRemark(rs.getString(13));
			}
		} catch (Exception e) {
			logger.error("查询自定义标签失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return label;
	}

	public List<Label> listAllLabel() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Label> labelList = new ArrayList<Label>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSLABEL_QUERY_ALL));
			rs = ps.executeQuery();
			while (rs.next()) {
				Label label = new Label();
				label.setId(rs.getInt(1));
				label.setName(rs.getString(2));
				label.setCode(rs.getString(3));
				label.setStyle(rs.getString(4));
				label.setModelId(rs.getInt(5));
				label.setModelType(rs.getString(6));
				label.setViewType(rs.getString(7));
				label.setRecord(rs.getInt(8));
				label.setContentType(rs.getInt(9));
				label.setTitleLength(rs.getInt(10));
				label.setRow(rs.getInt(11));
				label.setContentLength(rs.getInt(12));
				label.setRemark(rs.getString(13));
				labelList.add(label);
			}
		} catch (Exception e) {
			logger.error("查询自定义标签列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return labelList;
	}

	public void deleteLabel(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSLABEL_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("删除自定义标签失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
 

	/** ******************新闻************************ */
	public List<News> getNews(LabelModel lbm) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newsList = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from news  where ntid=? and hot=? and rownum<=?");
			ps.setInt(1, lbm.getModelId());
			ps.setInt(2, lbm.getContentType());
			ps.setInt(3, lbm.getRecord());
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt(1));
				news.setTitle(rs.getString(2));
				news.setMainimg(rs.getString(6));
				newsList.add(news);
			}
		} catch (Exception e) {
			logger.error("课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newsList;
	}
	public List<NewsType> getNewsTypesAll() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<NewsType> newsTypeList = new ArrayList<NewsType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSNEWSTYPE_QUERY_ALL));
			rs = ps.executeQuery();
			while (rs.next()) {
				NewsType newsType = new NewsType();
				newsType.setId(rs.getInt(1));
				newsType.setName(rs.getString(2));
				newsTypeList.add(newsType);
			}
		} catch (Exception e) {
			logger.error("新闻类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newsTypeList;
	}

	/**
	 * 论坛类型
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<ForumBlockType> getForumBlockTypeAll() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ForumBlockType> forumBlockTypeList = new ArrayList<ForumBlockType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CMSConstants.CMSFORUMBLOCKTYPE_QUERY_ALL));
			rs = ps.executeQuery();
			while (rs.next()) {
				ForumBlockType forumBlockType = new ForumBlockType();
				forumBlockType.setId(rs.getInt(1));
				forumBlockType.setName(rs.getString(2));
				forumBlockTypeList.add(forumBlockType);
			}
		} catch (Exception e) {
			logger.error("论坛类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return forumBlockTypeList;
	}

	public List<Forum> getForums(LabelModel lbm) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> forumList = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select forum.*  from forum where fblockid=? and hot=? and rownum<=?");
			ps.setInt(1,lbm.getModelId());
			ps.setInt(2,lbm.getContentType());
			ps.setInt(3,lbm.getRecord());
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum forum = new Forum();
				forum.setId(rs.getInt(1));
				forum.setTitle(rs.getString(2));
				forumList.add(forum);
			}
		} catch (Exception e) {
			logger.error("论坛列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return forumList;
	}

	/** ******************知识************************ */ 
	public List<KnowledgeType> getKnowledgeType() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<KnowledgeType> knowledgeTypeList = new ArrayList<KnowledgeType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from knowledgetype"); 
			rs = ps.executeQuery();
			while (rs.next()) {
				KnowledgeType knowledgeType = new KnowledgeType();
				knowledgeType.setId(rs.getInt(1));
				knowledgeType.setName(rs.getString(2)); 
				knowledgeTypeList.add(knowledgeType);
			}
		} catch (Exception e) {
			logger.error("知识列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return knowledgeTypeList;
	}
	public List<Knowledge> getknowledge(LabelModel lbm) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> knowledgeList = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from knowledge where kltypeid=? and hot=? and rownum<=?");
			ps.setInt(1,lbm.getModelId());
			ps.setInt(2,lbm.getContentType());
			ps.setInt(3,lbm.getRecord());
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge knowledge = new Knowledge();
				knowledge.setId(rs.getInt(1));
				knowledge.setTitle(rs.getString(2));
				knowledge.setMainimg(rs.getString(10));
				knowledgeList.add(knowledge);
			}
		} catch (Exception e) {
			logger.error("知识列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return knowledgeList;
	}
	/** ******************课程************************ */
	public List<CourseType> getCourseType() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseType> courseTypeList = new ArrayList<CourseType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from course_type"); 
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseType courseType = new CourseType();
				courseType.setId(rs.getInt(1));
				courseType.setName(rs.getString(2)); 
				courseTypeList.add(courseType);
			}
		} catch (Exception e) {
			logger.error("课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courseTypeList;
	}
	public List<Course> getCourse(LabelModel lbm) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> courseList = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from course  where ctypeid=? and rownum<=?");
			ps.setInt(1, lbm.getContentType());
			ps.setInt(2, lbm.getRecord());
			rs = ps.executeQuery();
			while (rs.next()) {
				Course course = new Course();
				course.setId(rs.getInt(1));
				course.setName(rs.getString(2));
				course.setMainimg(rs.getString(6));
				courseList.add(course);
			}
		} catch (Exception e) {
			logger.error("课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courseList;
	}
}
