package com.sopia.courseman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.getFloat;
import com.sopia.courseman.dao.CourseCommentDao;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.duman.entities.ELUser;
import com.sopia.shopping.entities.CourseOrder;
import com.sopia.studyman.dao.impl.StudyClassDaoImpl;

public class CourseCommentDaoImpl implements CourseCommentDao {
	private static final Log logger = LogFactory
			.getLog(StudyClassDaoImpl.class);

	public List<CourseComment> getCourseAllComment(int courseid, int ctype,
			int pageNow, int pageSize) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseComment> myComment = new ArrayList<CourseComment>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select * from (select t.*, rownum rn from (select userid,content,"
					+ " commentdate,commentpoint,eu.realname ,elt.touxiang from course_comment cc left "
					+ " join eluser  eu on cc.userid=eu.id left join  eluser_touxiang elt on elt.id=eu.id where cc.type="
					+ ctype
					+ " and cc.courseid=? and cc.status=1 order by"
					+ " commentdate  asc)t where rownum <= ? ) where rn>=? ";
			ps = ct.prepareStatement(sql);

			ps.setInt(1, courseid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseComment c = new CourseComment();
				c.setCommentpoint(rs.getInt(4));
				c.setCommentdate(rs.getTimestamp(3));
				c.setContent(rs.getString(2));
				ELUser e = new ELUser();
				e.setId(rs.getInt(1));
				e.setRealname(rs.getString(5));
				e.setTouxiang(rs.getString(6));
				c.setUser(e);
				myComment.add(c);

			}

			return myComment;
		} catch (Exception e) {
			logger.error("查询课程的评论列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public int getCourseAllCommentSize(int courseid, int ctype)
			throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select count(*) from (select userid from course_comment  "
					+ " cc left join eluser  eu on "
					+ " cc.userid=eu.id  where cc.type="
					+ ctype
					+ " and cc.courseid=? and cc.status=1 order by commentdate  asc)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("查询课程的评论列表大小出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public CourseComment getCourseCommentPoint(int courseid, int ctype)
			throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select SUM(CASE WHEN cc.courseid ="
					+ courseid
					+ "  THEN 1 ELSE 0 END ) AS allc ,"
					+ " SUM(CASE WHEN cc.commentpoint =1 THEN 1 ELSE 0 END ) AS one  ,"
					+ " SUM(CASE WHEN cc.commentpoint =2 THEN 1 ELSE 0 END) AS two,"
					+ " SUM(CASE WHEN cc.commentpoint =3 THEN 1 ELSE 0 END) AS three,"
					+ " SUM(CASE WHEN cc.commentpoint =4 THEN 1 ELSE 0 END) AS four,"
					+ " SUM(CASE WHEN cc.commentpoint =5 THEN 1 ELSE 0 END) AS five,"
					+ " AVG(cc.commentpoint) from course_comment cc where  cc.type="
					+ ctype + " and cc.courseid =" + courseid + ""
					+ " and cc.status=1 ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			CourseComment c = new CourseComment();
			c.setCount(rs.getInt(1));
			c.setOne(rs.getInt(2));
			c.setTwo(rs.getInt(3));
			c.setThree(rs.getInt(4));
			c.setFour(rs.getInt(5));
			c.setFive(rs.getInt(6));
			c.setAvg(getFloat.GetFloatOne(rs.getFloat(7)));
			return c;

		} catch (Exception e) {
			logger.error("查询课程的评论星级出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void saveCourseComment(CourseComment ccomment) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " insert into course_comment(content,status,userid,courseid,commentdate,"
					+ " commentpoint,type) values(?,?,?,?,sysdate,?,?)";

			ps = ct.prepareStatement(sql);
			ps.setString(1, ccomment.getContent());
			ps.setInt(2, ccomment.getStatus());
			ps.setInt(3, ccomment.getUserid());
			ps.setInt(4, ccomment.getCourseid());
			ps.setInt(5, ccomment.getCommentpoint());
			ps.setInt(6, ccomment.getType());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("查询课程的评论星级出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void saveShopComment(CourseComment ccomment) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " insert into course_comment(content,status,userid,courseid,commentdate,"
					+ " commentpoint,type,shopid) values(?,?,?,?,sysdate,?,?,?)";

			ps = ct.prepareStatement(sql);
			ps.setString(1, ccomment.getContent());
			ps.setInt(2, ccomment.getStatus());
			ps.setInt(3, ccomment.getUserid());
			ps.setInt(4, ccomment.getCourseid());
			ps.setInt(5, ccomment.getCommentpoint());
			ps.setInt(6, ccomment.getType());
			ps.setInt(7, ccomment.getPfmsUser().getUserId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("查询评论星级出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<ELUser> getEluserByCourseid(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> elusers = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select  el.id,el.username,el.realname,el.headphoto,sc.starttime from study_course sc left join eluser el on sc.userid=el.id  where courseid=? and classid=0");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while(rs.next()){
				ELUser el = new ELUser();
				el.setId(rs.getInt(1));
				el.setUsername(rs.getString(2));
				el.setRealname(rs.getString(3));
				el.setTouxiang(rs.getString(4));
				el.setBaoming(rs.getTimestamp(5));
				elusers.add(el);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elusers;
	}

}
