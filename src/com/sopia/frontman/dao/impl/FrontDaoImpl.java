package com.sopia.frontman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.assistman.entities.Poll;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.frontman.FrontConstants;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.knowledgeman.entities.Knowledge;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsStyle;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.StuffLib;

public class FrontDaoImpl implements FrontDao {
	private static final Log logger = LogFactory.getLog(FrontDaoImpl.class);

	public List<Course> listCourseByType(int pageNow, int pageSize, int type,
			boolean subcon) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			if (subcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_CTYPE_LRID));
				ps.setInt(1, type);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_COURSE_SUB_BYTYPE));
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_COURSE_BYTYPE));
				ps.setInt(1, type);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				Course course = new Course(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setCreater(new ELUser(0, rs.getString(4)));
				course.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				course.setMainimg(rs.getString(8));
				course.setTeacherName(rs.getString(9));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}

	public Course listCourseByTypeHot(int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Course course = new Course();
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql.getSQL(FrontConstants.FRONT_COURSE_BYHOT_LIMIT1));
			ps.setInt(1, type);
			rs = ps.executeQuery();
			if(rs.next()) {
				course = new Course(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setCreater(new ELUser(0, rs.getString(4)));
				course.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				course.setMainimg(rs.getString(8));
				course.setTeacherName(rs.getString(9));
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return course;
	}
	/**
	 * 根据类别和热度获取新闻信息
	 * @param pageNow
	 * @param pageSize
	 * @param typeid
	 * @param hot
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByTidhot(int pageNow, int pageSize, int typeid,
			int hot) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_NTYPE_LRID));
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct
			.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_NEWS_LIST_BYHOT));
			ps.setInt(1, hot);
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(6));
				//news.setContent(new OracleBlob().getContent(rs.getBlob(7)));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(7)));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}

	public List<Course> listCourseByHot(int pageNow, int pageSize, int hot)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_COURSE_BYHOT));
			ps.setInt(1, hot);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				Course course = new Course(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setCreater(new ELUser(0, rs.getString(4)));
				course.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				course.setMainimg(rs.getString(8));
				course.setTeacherName(rs.getString(9));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	public List<Course> listCourseByHot(int pageNow, int pageSize, int hot,int depid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();

		//	ps = ct.prepareStatement(ElQuerySql
		//			.getSQL(FrontConstants.FRONT_COURSE_BYHOT));
			String sql = "select * from (select t.*, rownum rn from( select c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername,d.id depid from course c,eluser eu,course_type ct,department d where c.ctypeid = ct.id and c.creater =eu.id and c.status=5  and eu.depid=d.id and depid=? order by c.createtime desc  )t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql);
		//	ps.setInt(1, hot);
			ps.setInt(1, depid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				Course course = new Course(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setCreater(new ELUser(0, rs.getString(4)));
				course.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				course.setMainimg(rs.getString(8));
				course.setTeacherName(rs.getString(9));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	
	public List<Course> listCourseByNewTime(int pageNow, int pageSize,int isapplication)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select * from (select t.*, rownum rn from(  select c.id,c.name,c.description, eu.realname,c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername " +
					"from course c,eluser eu,course_type ct  where c.ctypeid = ct.id and c.creater =eu.id and c.status=5 and isapplication = ?  " +
					"order by c.createtime desc   )t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, isapplication);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				Course course = new Course(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setCreater(new ELUser(0, rs.getString(4)));
				course.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				course.setMainimg(rs.getString(8));
				course.setTeacherName(rs.getString(9));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	public List<Course> listCourseByNewTime(int pageNow, int pageSize,int isapplication,int depid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select * from (select t.*, rownum rn from(  select c.id,c.name,c.description, eu.realname,c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername,d.id depid" +
					" from course c,eluser eu,course_type ct,department d  where c.ctypeid = ct.id and c.creater =eu.id and c.status=5 and eu.depid=d.id and isapplication = ? and depid=? " +
					"order by c.createtime desc   )t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, isapplication);
			ps.setInt(2, depid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				Course course = new Course(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setCreater(new ELUser(0, rs.getString(4)));
				course.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				course.setMainimg(rs.getString(8));
				course.setTeacherName(rs.getString(9));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	
	public List<ElClass> listClassByNewTime(int pageNow, int pageSize,int isapplication)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> zxc = new ArrayList<ElClass>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "  select * from (select t.*, rownum rn from(select c.id,c.name,c.description, eu.realname,c.cltype,ct.name ctname,c.createtime,c.mainimg " +
					"from elclass c,eluser eu,elclasstype ct  where c.cltype = ct.id and c.creater =eu.id and c.status=5 and isapplication = ? " +
					" order by c.createtime desc )t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, isapplication);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				ElClass elclass = new ElClass(rs.getInt(1), rs.getString(2));
				elclass.setDescription(rs.getString(3));
				elclass.setCreater(new ELUser(0, rs.getString(4)));
				elclass.setCltype(new ElClType(rs.getInt(5), rs.getString(6)));
				elclass.setCreatetime(rs.getTimestamp(7));
				elclass.setMainimg(rs.getString(8)); 
				zxc.add(elclass);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	
	public List<ElClass> listClassByNewTime(int pageNow, int pageSize,int isapplication,int depid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> zxc = new ArrayList<ElClass>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "  select * from (select t.*, rownum rn from(select c.id,c.name,c.description, eu.realname,c.cltype,ct.name ctname,c.createtime,c.mainimg,d.id depid  " +
					"from elclass c,eluser eu,elclasstype ct,department d  where c.cltype = ct.id and c.creater =eu.id and c.status=5 and isapplication = ? and eu.depid=d.id and depid=?" +
					" order by c.createtime desc )t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, isapplication);
			ps.setInt(2, depid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				ElClass elclass = new ElClass(rs.getInt(1), rs.getString(2));
				elclass.setDescription(rs.getString(3));
				elclass.setCreater(new ELUser(0, rs.getString(4)));
				elclass.setCltype(new ElClType(rs.getInt(5), rs.getString(6)));
				elclass.setCreatetime(rs.getTimestamp(7));
				elclass.setMainimg(rs.getString(8)); 
				zxc.add(elclass);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	
	public List<ExamRoom> listExamRoomByNewTime(int pageNow, int pageSize,int isapplication)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> zxc = new ArrayList<ExamRoom>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select * from (select t.*, rownum rn from( select c.id,c.title,c.description, eu.realname,c.erlibid,ct.name ctname,c.mainimg " +
					"from exam_room c,eluser eu,eroom_lib ct where c.erlibid = ct.id and c.createrid =eu.id and c.valid=5 and isapplication = ?  " +
					"order by c.begintime desc )t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, isapplication);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				ExamRoom eroom = new ExamRoom(rs.getInt(1), rs.getString(2));
				eroom.setDescription(rs.getString(3));
				eroom.setCreater(new ELUser(0, rs.getString(4)));
				eroom.setEroomLib(new EroomLib(rs.getInt(5), rs.getString(6))); 
				eroom.setMainimg(rs.getString(7)); 
				zxc.add(eroom);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	
	public List<ExamRoom> listExamRoomByNewTime(int pageNow, int pageSize,int isapplication,int depid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> zxc = new ArrayList<ExamRoom>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select * from (select t.*, rownum rn from( select c.id,c.title,c.description, eu.realname,c.erlibid,ct.name ctname,c.mainimg,d.id depid  " +
					"from exam_room c,eluser eu,eroom_lib ct,department d where c.erlibid = ct.id and c.createrid =eu.id and c.valid=5 and isapplication = ? and eu.depid=d.id and depid=? " +
					"order by c.begintime desc )t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql); 
			ps.setInt(1, isapplication);
			ps.setInt(2, depid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// c.id,c.name,c.description,eu.realname,c.ctypeid,ct.name
				ExamRoom eroom = new ExamRoom(rs.getInt(1), rs.getString(2));
				eroom.setDescription(rs.getString(3));
				eroom.setCreater(new ELUser(0, rs.getString(4)));
				eroom.setEroomLib(new EroomLib(rs.getInt(5), rs.getString(6))); 
				eroom.setMainimg(rs.getString(7)); 
				zxc.add(eroom);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}

	public int listCourseCountByType(int type, boolean subcon)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (subcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_CTYPE_LRID));
				ps.setInt(1, type);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_COURSE_SUB_SIZE_BYTYPE));
				ps.setInt(1, lid);
				ps.setInt(2, rid);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_COURSE_SIZE_BYTYPE));
				ps.setInt(1, type);
			}
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Course> listPhCourse(int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_COURSE_PH));
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course course = new Course(rs.getInt(1), rs.getString(2));
				course.setUserCount(rs.getInt(3));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}

	public List<ExamRoom> listExamRooms(int eplibid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> zxc = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select lid,rid from exampaperlib where id = ? and userid = 1");
			ps.setInt(1, eplibid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps = ct
			.prepareStatement("select * from (select er.id,er.title,er.createrid,eu.realname ,er.begintime,er.endtime,er.eplibid ,epl.name,er.description,row_number() over (order by er.begintime desc) rownum "
					+ " from exam_room er,eluser eu , exampaperlib epl where epl.id = er.eplibid and eu.id = er.createrid and iscommon=1  and epl.userid = 1 and  epl.lid>=? and epl.rid<=?) t where t.rownum between ? and ?");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom course = new ExamRoom(rs.getInt(1), rs.getString(2));
				course.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				course.setBegintime(rs.getTimestamp(5));
				course.setEndtime(rs.getTimestamp(6));
				course.setDescription(rs.getString(9));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}

	public int listExamRoomsSize(int eplibid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select lid,rid from exampaperlib where id = ? and userid = 1");
			ps.setInt(1, eplibid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps = ct
			.prepareStatement(" select count(*) "
					+ " from exam_room er,eluser eu , exampaperlib epl where epl.id = er.eplibid and eu.id = er." +
			"rid and iscommon=1  and epl.userid = 1 and  epl.lid>=? and epl.rid<=?");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> listPhUsers(int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> users = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_USER_PH));
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
//				eu.setXfscore(rs.getInt(4));
				users.add(eu);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return users;
	}
	
	/**
	 * 获取弹窗新闻
	 */
	public News getNewsInPop() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		News news = new News();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select n.id,n.title,n.releasetime,n.ntid ,nt.name,n.hot,elu.realname,n.status,n.content,n.status_tow from news n,newstype nt,ELUSER elu where nt.id = n.ntid and n.userid=elu.id and ispop=1 "); //"+createPerTypeId(ntypeTree,ntypeid)+"
			rs = ps.executeQuery();
			if (rs.next()) {
				String title=rs.getString("title");
				if(title!=null&&title.length()>18){
					title=title.substring(0,18)+"...";
				}
				news = new News(rs.getInt(1), title);
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				news.setOwner(user);
				news.setStatus(rs.getInt(8));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(9)));
				news.setStatus_tow(rs.getInt("status_tow"));
//				if(news.getContent().length()>100){
//					news.setContent(news.getContent().substring(0,100)+"...");
//				}
			}
		} catch (Exception e) {
			logger.error("获取弹窗新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return news;
	}

	/**
	 * 根据标题和类别获取最新的新闻
	 * @param pageNow
	 * @param pageSize
	 * @param typeid
	 * @param subcon
	 * @param title
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByTid(int pageNow, int pageSize, int typeid,
			boolean subcon, String title) throws ElException {//首页用了这个方法
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			if (subcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NTYPE_LRID));
				ps.setInt(1, typeid);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NEWS_SUB_BYTID));
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, lid);
				ps.setInt(3, rid);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NEWS_BYTID));
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, typeid);
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(6));
				//news.setContent(new OracleBlob().getContent(rs.getBlob(7)));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(7)));//--/
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	
	/**
	 * 根据标题获取新闻集合
	 * @param pageNow
	 * @param pageSize
	 * @param typeid
	 * @param subcon
	 * @param title
	 * @return
	 * @throws ElException
	 */
	public List<News> listNewsByTid_list(int pageNow, int pageSize, int typeid,
			boolean subcon, String title) throws ElException {//内容只截取500
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			if (subcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NTYPE_LRID));
				ps.setInt(1, typeid);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NEWS_SUB_BYTID));
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, lid);
				ps.setInt(3, rid);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NEWS_BYTID));
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, typeid);
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(6));
				//news.setContent(new OracleBlob().getContent(rs.getBlob(7)));
				news.setContent(new OracleBlob().getContent_list(rs.getBlob(7)));//--/
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}

	public List<News> listNewsByTidHot(int typeid, int hot, int pageNow,
			int pageSize) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_NTYPE_LRID));
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_NEWS_SUB_BYTIDHOT));
			ps.setInt(1, hot);
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(6));
				news.setContent(new OracleBlob().getContent(rs.getBlob(7)));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}

	public int listNewsCountByTid(int typeid, boolean subcon, String title)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (subcon) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NTYPE_LRID));
				ps.setInt(1, typeid);
				rs = ps.executeQuery();
				int lid = 0;
				int rid = 0;
				if (rs.next()) {
					lid = rs.getInt(2);
					rid = rs.getInt(3);
				}
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NEWS_SUB_SIZE_BYTID));
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, lid);
				ps.setInt(3, rid);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(FrontConstants.FRONT_NEWS_SIZE_BYTID));
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, typeid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public ElClass indexCountInfo() throws ElException {
		ElClass class1 = new ElClass();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from eluser ");
			rs = ps.executeQuery();
			if (rs.next()) {
				// 总人数
				class1.setXxCount(rs.getInt(1));
			}
			rs.close();
			ps = ct
			.prepareStatement("select count(*) from course where status=1 ");
			rs = ps.executeQuery();
			if (rs.next()) {
				// 总课程数
				class1.setXxCredit(rs.getInt(1));
			}
			rs.close();
			ps = ct.prepareStatement("select count(*) from study_course ");
			rs = ps.executeQuery();
			if (rs.next()) {
				// x学习人数
				class1.setBxCount(rs.getInt(1));
			}

			rs.close();
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return class1;
	}

	public List<Knowledge> listZxKnows(int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> knows = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_KNOWLEDGE_ZX));

			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kn = new Knowledge(rs.getInt(1), rs.getString(2));
				kn.setContent(rs.getString(3));
				knows.add(kn);

			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return knows;
	}

	public List<Knowledge> listKnowsByType(int type, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> knows = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_KNOWLEDGE_BYTYPE));
			ps.setInt(1, type);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Knowledge kn = new Knowledge(rs.getInt(1), rs.getString(2));
				kn.setMainimg(rs.getString(4));
				kn.setWendang(rs.getString(5));
				kn.setCreatetime(rs.getDate(6));
				knows.add(kn);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return knows;
	}

	public Knowledge listKnowByType(int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Knowledge kn = new Knowledge();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement(ElQuerySql.getSQL(FrontConstants.FRONT_KNOWLEDGE_BYTYPE_LIMIT1));
			ps.setInt(1, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				kn = new Knowledge(rs.getInt(1), rs.getString(2));
				kn.setMainimg(rs.getString(3));
				kn.setWendang(rs.getString(4));
				kn.setContent(rs.getString(5));
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kn;
	}

	public List<Knowledge> listHotKnows(int pageNow, int pageSize, int hot)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> knows = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_KNOWLEDGE_BYHOT));

			ps.setInt(1, hot);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {  

				//Knowledge kn = new Knowledge(rs.getInt(1), rs.getString(2),new OracleBlob().getContent(rs.getBlob(3)), rs.getString(4));
				Knowledge kn = new Knowledge(rs.getInt(1), rs.getString(2),new OracleBlob().getContent_index(rs.getBlob(3)), rs.getString(4));
				knows.add(kn);

			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return knows;
	}
	/**
	 * 获取最新的新闻
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<News> listZxNews(int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newsList = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select  id,title,releasetime from news where status=3 order by releasetime desc) t where rownum <= ? ) where rn>=? ");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			News news=null;
			String title="";
			while (rs.next()) {
				title=rs.getString("title");
				if(title!=null&&title.length()>20){
					title=title.substring(0,20);
				}
				news=new News(rs.getInt("id"),title);
				news.setReleasetime(rs.getTimestamp("releasetime"));
				newsList.add(news);

			}
		} catch (Exception e) {
			logger.error("获取最新的新闻出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newsList;
	}
	
	/**
	 * 获取最新推荐的新闻
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<News> listZxNews(int pageNow, int pageSize,int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newsList = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select  id,title,releasetime from news where status=3 and hot=? order by releasetime desc) t where rownum <= ? ) where rn>=? ");
			ps.setInt(1, hot);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			News news=null;
			String title="";
			while (rs.next()) {
				title=rs.getString("title");
				if(title!=null&&title.length()>20){
					title=title.substring(0,20);
				}
				news=new News(rs.getInt("id"),title);
				news.setReleasetime(rs.getTimestamp("releasetime"));
				newsList.add(news);

			}
		} catch (Exception e) {
			logger.error("获取最新推荐的新闻出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newsList;
	}

	public List<Department> listPhDeps(int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_DEP_PH));
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setUserCredit(rs.getInt(3));
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<Course> listCourseByName(int pageNow, int pageSize, String name)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_COURSE_BYNAME));
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course course = new Course(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setCreater(new ELUser(0, rs.getString(4)));
				course.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				course.setMainimg(rs.getString(8));
				course.setTeacherName(rs.getString(9));
				String kj_appendix = rs.getString(10);
				kj_appendix = kj_appendix == null ? "assist_plan_stuff_download.action?fileName="
						: (kj_appendix.indexOf("//") < 0 ? "assist_plan_stuff_download.action?fileName="
								+ kj_appendix
								: kj_appendix);
				course.setKj_appendix(kj_appendix);
				kj_appendix = rs.getString(11);
				kj_appendix = kj_appendix == null ? "assist_plan_stuff_download.action?fileName="
						: (kj_appendix.indexOf("//") < 0 ? "assist_plan_stuff_download.action?fileName="
								+ kj_appendix
								: kj_appendix);
				course.setJy_appendix(kj_appendix);
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}

	public int listCourseByNameSize(String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_COURSE_BYNAME_SIZE));
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ElClass> listClassByName(int pageNow, int pageSize, String name)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> zxc = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_CLASS_BYNAME));
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass course = new ElClass(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setMainimg(rs.getString(4));
				course.setCreater(new ELUser(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}

	public int listClassByNameSize(String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_CLASS_BYNAME_SIZE));
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<StuffLib> listStuff(String title, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<StuffLib> qss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_STUFF_LIST_BYTITLE));
			ps.setString(1, "%" + title + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib qs = new StuffLib(rs.getInt(1), rs.getString(2));
				qs.setDescription(rs.getString(3));
				qs.setFileext(rs.getString(4));
				qs.setModifytime(new Date(rs.getTimestamp(5).getTime()));
				qs.setCreatetime(new Date(rs.getTimestamp(6).getTime()));

				long length = 0;
				try {
					length = new Long(rs.getString(7));
				} catch (Exception e) {
				}
				qs.setLength(length);
				qs.setType(rs.getInt(8));
				qs.setOwner(new ELUser(rs.getInt(9), rs.getString(10)));
				qss.add(qs);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qss;
	}

	public int listStuffCount(String title) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_STUFF_LIST_BYTITLE_SIZE));
			ps.setString(1, "%" + title + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取资料出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ElClass> listClassByTid(int pageNow, int pageSize, int tid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> zxc = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();

			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_CLTYPE_LRID));
			ps.setInt(1, tid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_CLASS_BYTID));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass course = new ElClass(rs.getInt(1), rs.getString(2));
				course.setDescription(rs.getString(3));
				course.setMainimg(rs.getString(4));
				course.setCreater(new ELUser(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				zxc.add(course);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}

	public int listClassByTidSize(int tid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_CLTYPE_LRID));
			ps.setInt(1, tid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_CLASS_BYTID_SIZE));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public boolean checkUserInClass(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_CLASS_USER_CHECK));
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	// public List<CourseType> listCtype(int pageNow, int pageSize)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<CourseType> deps = new ArrayList<CourseType>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(FrontConstants.FRONT_CTYPE_LIST));
	// ps.setInt(1, pageNow*pageSize);
	// ps.setInt(2, pageSize);
	// rs = ps.executeQuery();
	// while(rs.next()) {
	// CourseType dep = new CourseType(rs.getInt(1),rs.getString(2));
	// deps.add(dep);
	// }
	// } catch (Exception e) {
	// logger.error("学员排行列表！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return deps;
	// }
	//搜索资讯
	public List<News> SearchNews(String title, int pageNow,int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from(select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where  n.title like ? and nt.id = n.ntid and eu.id = n.userid order by n.releasetime desc) t where rownum <= ? ) where rn>=?");
			ps.setString(1, "%" + title + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(6));
				news.setContent(new OracleBlob().getContent(rs.getBlob(7)));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
//	public int SearchNewsCountByTid(String title)throws ElException{
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<News> newses = new ArrayList<News>();
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select count(*) from news n,newstype nt,eluser eu where  n.title like ? and nt.id = n.ntid and eu.id = n.userid");
//			ps.setString(1, "%" + title + "%");
//			rs = ps.executeQuery();
//			if(rs.next()){
//				return rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("学员排行列表！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return 0;
//	}  

	//最新推荐的帮助中心
	public List<News> listHotNnows(int pageNow, int pageSize, int hot)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();

			//ps = ct.prepareStatement("select * from (select t.*,rownum rn from(select n.id,n.title,n.mainimg,elu.realname,nt.name,n.releasetime,n.content  from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id and nt.id in (4) and n.hot=?   order by n.releasetime desc) t where rownum <=?)where rn>=?");
			ps = ct.prepareStatement("select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime, n.ntid ,nt.name,n.hot,elu.realname,n.status,n.mainimg,n.content  from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id and nt.id in (15) and n.hot=? and n.status=3  order by n.releasetime desc) t where rownum <=?)where rn>=?");
			ps.setInt(1, hot);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(9));
				news.setOwner(new ELUser(0, rs.getString(7)));
				news.setReleasetime(rs.getTimestamp(3));
				news.setContent(new OracleBlob().getContent(rs.getBlob(10))); 
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	public List<News> listHotNnows(int pageNow, int pageSize,int type, int hot)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_NTYPE_LRID));
			ps.setInt(1, type);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct.prepareStatement("select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime, n.ntid ,nt.name,n.hot,elu.realname,n.status,n.mainimg,n.content  from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id  and n.hot=? and nt.lid>=? and nt.rid <=? and n.status=3  order by n.releasetime desc) t where rownum <=?)where rn>=?");
			ps.setInt(1, hot);
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(9));
				news.setOwner(new ELUser(0, rs.getString(7)));
				news.setReleasetime(rs.getTimestamp(3));
//				news.setContent(new OracleBlob().getContent(rs.getBlob(10))); 
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(10)));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	public List<NewsType> getNewsType()throws ElException{
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<NewsType> list=new ArrayList<NewsType>();
		try{
			ct=DBConnection.getConnection();
			ps=ct.prepareStatement("select id,name from newstype order by id");
			rs=ps.executeQuery();
			while(rs.next()){
				NewsType newsType=new NewsType();
				newsType.setId(rs.getInt(1));
				newsType.setName(rs.getString(2));
				list.add(newsType);
			}
			return list;
		}catch(Exception e){
			logger.error("首页访问次数增加失败！",e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Course> listCourseByName(int pageNow, int pageSize, Course course)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();

			/*ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_COURSE_BYNAME));
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);*/
			sqlstr+="select * from (select t.*, rownum rn from(select c.id,c.name,c.description,eu.realname, c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername,c.kj_appendix, c.jy_appendix from course c,eluser eu,course_type ct";
			//where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and (c.name like ? or c.description like ?) order by c.createtime desc ) t where rownum <= ? ) where rn>=?
			sqlstr+=" where c.ctypeid = ct.id and c.creater =eu.id and c.status=1";
			sqlstr+=course==null?"":course.getName()==null?"":" and c.name like '%"+course.getName()+"%'";
			sqlstr+=course==null?"":course.getDescription()==null?"":"or c.description like '%"+course.getDescription()+"%')";
			sqlstr+=course==null?"":course.getCtype()==null?"":course.getCtype().getId()==0||course.getCtype().getId()==-1?"":" and ct.id="+course.getCtype().getId();
			sqlstr+=" order by c.createtime desc ) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps=ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course cs = new Course(rs.getInt(1), rs.getString(2));
				cs.setDescription(rs.getString(3));
				cs.setCreater(new ELUser(0, rs.getString(4)));
				cs.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				cs.setCreatetime(rs.getTimestamp(7));
				cs.setMainimg(rs.getString(8));
				cs.setTeacherName(rs.getString(9));
				String kj_appendix = rs.getString(10);
				kj_appendix = kj_appendix == null ? "assist_plan_stuff_download.action?fileName="
						: (kj_appendix.indexOf("//") < 0 ? "assist_plan_stuff_download.action?fileName="
								+ kj_appendix
								: kj_appendix);
				cs.setKj_appendix(kj_appendix);
				kj_appendix = rs.getString(11);
				kj_appendix = kj_appendix == null ? "assist_plan_stuff_download.action?fileName="
						: (kj_appendix.indexOf("//") < 0 ? "assist_plan_stuff_download.action?fileName="
								+ kj_appendix
								: kj_appendix);
				cs.setJy_appendix(kj_appendix);
				zxc.add(cs);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	public List<Course> listCourseByName(int pageNow, int pageSize, Course course,CourseType ctypeTree)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();

			/*ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_COURSE_BYNAME));
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);*/
			sqlstr+="select * from (select t.*, rownum rn from(select c.id,c.name,c.description,eu.realname, c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername,c.kj_appendix, c.jy_appendix from course c,eluser eu,course_type ct";
			//where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and (c.name like ? or c.description like ?) order by c.createtime desc ) t where rownum <= ? ) where rn>=?
			sqlstr+=" where c.ctypeid = ct.id and c.creater =eu.id and c.status=5";
			sqlstr+=course==null?"":course.getName()==null?"":" and c.name like '%"+course.getName()+"%'";
			sqlstr+=course==null?"":course.getDescription()==null?"":"or c.description like '%"+course.getDescription()+"%')";
			sqlstr+=course==null?"":course.getCtype()==null?"":course.getCtype().getId()==0||course.getCtype().getId()==-1?"":" and ct.id in  ("+createPerTypeId(ctypeTree,course.getCtype().getId())+")";
			sqlstr+=" order by c.createtime desc ) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps=ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course cs = new Course(rs.getInt(1), rs.getString(2));
				cs.setDescription(rs.getString(3));
				cs.setCreater(new ELUser(0, rs.getString(4)));
				cs.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				cs.setCreatetime(rs.getTimestamp(7));
				cs.setMainimg(rs.getString(8));
				cs.setTeacherName(rs.getString(9));
				String kj_appendix = rs.getString(10);
				kj_appendix = kj_appendix == null ? "assist_plan_stuff_download.action?fileName="
						: (kj_appendix.indexOf("//") < 0 ? "assist_plan_stuff_download.action?fileName="
								+ kj_appendix
								: kj_appendix);
				cs.setKj_appendix(kj_appendix);
				kj_appendix = rs.getString(11);
				kj_appendix = kj_appendix == null ? "assist_plan_stuff_download.action?fileName="
						: (kj_appendix.indexOf("//") < 0 ? "assist_plan_stuff_download.action?fileName="
								+ kj_appendix
								: kj_appendix);
				cs.setJy_appendix(kj_appendix);
				zxc.add(cs);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}
	public int listCourseByNameSize(int pageNow, int pageSize, Course course,CourseType ctypeTree)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();
			sqlstr+="select count(*) from (select t.*, rownum rn from(select c.id,c.name,c.description,eu.realname, c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername,c.kj_appendix, c.jy_appendix from course c,eluser eu,course_type ct";
			//where c.ctypeid = ct.id and c.creater =eu.id and c.status=1 and (c.name like ? or c.description like ?) order by c.createtime desc ) t where rownum <= ? ) where rn>=?
			sqlstr+=" where c.ctypeid = ct.id and c.creater =eu.id and c.status=1";
			sqlstr+=course==null?"":course.getName()==null?"":" and c.name like '%"+course.getName()+"%'";
			sqlstr+=course==null?"":course.getDescription()==null?"":"or c.description like '%"+course.getDescription()+"%')";
			sqlstr+=course==null?"":course.getCtype()==null?"":course.getCtype().getId()==0||course.getCtype().getId()==-1?"":" and ct.id in  ("+createPerTypeId(ctypeTree,course.getCtype().getId())+")";
			sqlstr+=" order by c.createtime desc ) t )";
			ps=ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	//搜索资讯
	public List<News> SearchNews(News news, int pageNow,int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();
			sqlstr+="select * from (select t.*, rownum rn from(select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where ";
			sqlstr+="nt.id = n.ntid and eu.id = n.userid ";
			sqlstr+=news==null?"":" and n.title like '%"+news.getTitle().trim()+"%'";
			sqlstr+=news==null?"":news.getNtype()==null?"":news.getNtype().getId()==0?"":" and nt.id="+news.getNtype().getId();
			sqlstr+=" order by n.releasetime desc) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				News ns = new News(rs.getInt(1), rs.getString(2));
				ns.setMainimg(rs.getString(3));
				ns.setOwner(new ELUser(0, rs.getString(4)));
				ns.setReleasetime(rs.getTimestamp(6));
				ns.setContent(rs.getString(7));
				newses.add(ns);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	public int SearchNewsCountByTid(News news)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();
			sqlstr+="select count(*) from (select t.*, rownum rn from(select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where ";
			sqlstr+="nt.id = n.ntid and eu.id = n.userid";
			sqlstr+=news==null?"":" and n.title like '%"+news.getTitle().trim()+"%'";
			sqlstr+=news==null?"":news.getNtype()==null?"":news.getNtype().getId()==0?"":" and nt.id="+news.getNtype().getId();
			sqlstr+=" order by n.releasetime desc) t)";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery(sqlstr);
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}  
	public void updateFlow()throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sqlstr="update flow_statistics set homevisit=homevisit+1";
			ps = ct.prepareStatement(sqlstr);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("首页访问总数统计失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	private String createPerTypeId(CourseType ctypeTree, int ctid){
		if(ctypeTree!=null){
			if(ctypeTree.getId()!=ctid){
				ctypeTree = getCourseTypeById(ctypeTree.getChild(),ctid);
			}
			if(ctypeTree.getChild()!=null){
				return createTypeId(ctypeTree.getChild(),ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		}else{
			return null;
		}
	}
	private CourseType getCourseTypeById(List<CourseType> listType,int ctid){
		CourseType courseType=null;
		for(CourseType type:listType){
			if(type.getId()!=ctid){
				courseType =  getCourseTypeById(type.getChild(),ctid);
				if(courseType!=null){
					return courseType;
				}
			}else{
				courseType = type;
				return courseType;
			}
		}
		return courseType;
	}
	private String createTypeId(List<CourseType> listType,int id){
		String ids=id+"";
		for(CourseType type:listType){
			ids=ids+","+createTypeId(type.getChild(),type.getId());
		}
		return ids;
	}


	public List<News> listHotNnowsByNewsStyle(int pageNow, int pageSize,
			int styleid, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime, n.ntid ,n.hot,elu.realname,n.status,n.mainimg,n.content,n.nsid,ns.name  from news n,ELUSER elu,newsstyle ns where  n.userid=elu.id  and n.hot=? and n.nsid=ns.id and n.nsid=? and n.status=3  order by n.releasetime desc) t where rownum <=?)where rn>=?");
			ps.setInt(1, hot);
			ps.setInt(2, styleid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(8));
				news.setOwner(new ELUser(0, rs.getString(6)));
				news.setReleasetime(rs.getTimestamp(3));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(9)));
				NewsStyle nstyle = new NewsStyle();
				nstyle.setId(rs.getInt(10));
				nstyle.setName(rs.getString(11));
				news.setNstyle(nstyle);
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("首页帮助中心列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}

	public List<News> listNewsByNsidByDepthot(int pageNow, int pageSize,
			int styleid, int deptid, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select * from (select t.*, rownum rn from  " +     
        " (select n.id,n.title,n.mainimg,n.userid,n.releasetime,n.content from news n,newsstyle ns where n.status=3 and n.hot=? and n.nsid=ns.id and n.nsid=? and n.userid in " +
        " (select id from eluser where depid in(select id from department dep inner join " +
        " (select lid,rid from department dep where id=?) t2 on dep.lid>=t2.lid and dep.rid<=t2.rid)))t " +
		" where rownum <= ?  and rownum>=? ) order by releasetime desc");
			ps.setInt(1, hot);
			ps.setInt(2, styleid);
			ps.setInt(3, deptid);
			ps.setInt(4, pageSize);
			ps.setInt(5, pageNow);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(5));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(6)));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("根据新闻类型、部门ID，列出新闻！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}

	public List<News> listNewsByNsidhot(int pageNow, int pageSize, int styleid,
			int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select n.id,n.title,n.mainimg,eu.realname,n.releasetime,n.content,n.nsid,ns.name  from news n,eluser eu,newsstyle ns where  n.hot =? and n.status=3  and   n.nsid=?  and eu.id = n.userid and n.nsid=ns.id order by n.releasetime desc )t " +
//					" where rownum <= ? ) where rn>=? ");
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select n.id,n.title,n.mainimg,eu.realname,n.releasetime,n.content,n.nsid,ns.name  from news n,eluser eu,newsstyle ns where  n.hot =? and n.status=3    and eu.id = n.userid and n.nsid=ns.id order by n.releasetime desc )t " +
			" where rownum <= ? ) where rn>=? ");
			ps.setInt(1, hot);
			//ps.setInt(2, styleid);
			ps.setInt(2, pageSize);
			ps.setInt(3, pageNow);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(5));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(6)));
				NewsStyle nstyle = new NewsStyle();
				nstyle.setId(rs.getInt(7));
				nstyle.setName(rs.getString(8));
				news.setNstyle(nstyle);
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("根据新闻类型和热度取得新闻！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}

	public List<Knowledge> listHotKnowsByDept(int pageNow, int pageSize,
			int hot, int deptid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Knowledge> knows = new ArrayList<Knowledge>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select * from (select t.*, rownum rn from " +
					" (select kl.id,kl.title,kl.content,kl.mainimg from " +
					" knowledge kl, knowledgetype klt,eluser el  where kl.kltypeid = klt.id and  kl.hot=? and kl.valid=1 and kl.userid=el.id and kl.userid in " +
					" (select id from eluser where depid in(select id from department dep inner join (select lid,rid from department dep where id=?) t2 on dep.lid>=t2.lid and dep.rid<=t2.rid))  " +
					" order by kl.createtime desc)t where rownum <= ? ) where rn>=? ");
			ps.setInt(1, hot);
			ps.setInt(2, deptid);
			ps.setInt(3, pageSize);
			ps.setInt(4, pageNow);
			rs = ps.executeQuery();
			while (rs.next()) {  
				Knowledge kn = new Knowledge(rs.getInt(1), rs.getString(2),new OracleBlob().getContent_index(rs.getBlob(3)), rs.getString(4));
				knows.add(kn);
			}
		} catch (Exception e) {
			logger.error("根据部门、热度获取知识！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return knows;
	}

	public List<Department> listDeptByIssp() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> list = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select id,name,description from department where issp=1 and status !=1");
			rs = ps.executeQuery();
			while (rs.next()) {  
				Department dept = new Department();
				dept.setId(rs.getInt(1));
				dept.setName(rs.getString(2));
				dept.setDescription(rs.getString(3));
				list.add(dept);
			}
		} catch (Exception e) {
			logger.error("获得所有二级页面的部门信息！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public List<News> listNewsByTidNewTime(int pageNow, int pageSize, int typeid) throws ElException { 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_NTYPE_LRID));
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			String sql="select * from (select t.*, rownum rn from ( select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content " +
					"from news n,newstype nt,eluser eu where  n.status=3  and   nt.lid>=? and nt.rid<=? and nt.id = n.ntid and eu.id = n.userid " +
					"  order by n.releasetime desc )t where rownum <= ? ) where rn>=?  ";
			ps = ct
			.prepareStatement(sql); 
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(6));
				//news.setContent(new OracleBlob().getContent(rs.getBlob(7)));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(7)));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	public List<News> listNewsByTidNewTime(int pageNow, int pageSize, int typeid, int depid) throws ElException { 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(FrontConstants.FRONT_NTYPE_LRID));
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			String sql="select * from (select t.*, rownum rn from ( select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content,d.id depid " +
					" from news n,newstype nt,eluser eu,department d where  n.status=3  and   nt.lid>=? and nt.rid<=? and nt.id = n.ntid and eu.id = n.userid and d.id=eu.depid and depid=?" +
					"  order by n.releasetime desc )t where rownum <= ? ) where rn>=?  ";
			ps = ct
			.prepareStatement(sql); 
			System.out.println(sql);
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, depid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setMainimg(rs.getString(3));
				news.setOwner(new ELUser(0, rs.getString(4)));
				news.setReleasetime(rs.getTimestamp(6));
				//news.setContent(new OracleBlob().getContent(rs.getBlob(7)));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(7)));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	/**
	 * 获取前10排行榜 
	 * @return
	 * @throws ElException
	 */
	public List<Department> getElclassDepPassing_phDeps(int classid,int pageNow,int pageSize) throws ElException { 
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Department> depList = new ArrayList<Department>();
		String classSql = "";
		try {    
			if(classid != 0){
				classSql = " and classid = "+classid;
			}
			String sql="select * from ( select t1.* ,rownum rn from(select edp.passing,dep.id,dep.name,edp.classid  from elclass_dep_passing edp,department dep " +
					"where  dep.id = edp.depid and dep.parentid = 1 "+classSql+" order by edp.passing desc  ) t1 where rownum <=? ) where rn >=?";
			ct = DBConnection.getConnection(); 
			ps=ct.prepareStatement(sql);  
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery(); 
			while (rs.next()) { 
				Department  dep = new Department(rs.getInt(2),rs.getString(3));
				dep.setRatioPassing_(rs.getDouble(1)); 
				dep.setUserCount(getClassEval_CountNumberOfPeople(rs.getInt(2), rs.getInt(4)));
				dep.setUserCount_(getClassEval_Pass_CountNumberOfPeople(rs.getInt(2), rs.getInt(4)));
				if(dep.getId() != 98){
					depList.add(dep);
				}
			}
		} catch (Exception e) { 
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return depList;
	}
	/**
	 * （培训班概况比较用到）
	 * 获取某部门下某培训班总人数 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_CountNumberOfPeople(int depidt,int classid) throws ElException { 
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int CountNumber = 0;
		try {   

			ct = DBConnection.getConnection(); 
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depidt);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			
			String sql="select count(*)  from study_class ca,department de,eluser el " +
					"where ca.userid = el.id and el.depid = de.id and ca.classid=? and de.lid >= ? and de.rid <= ? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, dep.getLid());
			ps.setInt(3, dep.getRid());
			rs = ps.executeQuery();
			if (rs.next()) { 
				CountNumber = rs.getInt(1);
			}
		} catch (Exception e) { 
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return CountNumber;
	}
	/**
	 * （培训班概况比较用到）
	 * 获取某部门下某培训班通过人数
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_Pass_CountNumberOfPeople(int depId,int classId) throws ElException { 
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int CountNumber = 0;
		try {   
			ct = DBConnection.getConnection(); 
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depId);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			} 
			ps.close();
			rs.close();
			
			String sql="select count(*)  from study_class ca,department de,eluser el " +
					"where ca.userid = el.id and el.depid = de.id and ca.classid=?   " +
					"and de.lid >= ? and de.rid <= ? and ca.certificateno is not null";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, classId); 
			ps.setInt(2, dep.getLid());
			ps.setInt(3, dep.getRid());
			rs = ps.executeQuery();
			if (rs.next()) { 
				CountNumber = rs.getInt(1);
			}
		} catch (Exception e) { 
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return CountNumber;
	}

	public void addUserIsPoll(int pollid, int userid) throws ElException {
		// TODO Auto-generated method stub
		
	}

	public Poll getPoolMaxId() throws ElException {
		// TODO Auto-generated method stub
		return null;
	} 
	
//	/**
//	 * 获取投票信息
//	 * @param id
//	 * @return
//	 * @throws ElException
//	 */
//	public Poll getPoolMaxId() throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		Poll pl = new Poll();
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(
//					"select pol.id,\n" +
//					"       pol.title,\n" + 
//					"       pol.remack,\n" + 
//					"       pol.begintime,\n" + 
//					"       pol.endtime,\n" + 
//					"       pol.stuviewresult,\n" + 
//					"       pol.status,\n" + 
//					"       q.id,\n" + 
//					"       q.title,\n" + 
//					"       q.subject,\n" + 
//					"       q.qtype,\n" + 
//					"       pol.hot\n" + 
//					"  from pollinfo pol, question q\n" + 
//					" where pol.qid = q.id(+)\n" + 
//					"   and pol.id = (select max(id) from pollinfo)\n" + 
//					"   and pol.status = 2");
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				pl = new Poll(rs.getInt(1), rs.getString(2));
//				pl.setRemack(rs.getString(3));
//				pl.setBegintime(rs.getTimestamp(4));
//				pl.setEndtime(rs.getTimestamp(5));
//				pl.setStuViewResult(rs.getInt(6));
//				pl.setStatus(rs.getInt(7));
//				pl.setQuestion(new Question(rs.getInt(8),rs.getString(9)));
//				pl.getQuestion().setSubject(rs.getString(10));
//				pl.getQuestion().setQtype(rs.getInt(11));
//				pl.setHot(rs.getInt(12));
//			}
//		} catch (Exception e) {
//			logger.error("获取投票信息失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return pl;
//	}
//	
//	/**
//	 * 添加用户为已投票
//	 * @param pollid
//	 * @param userid
//	 * @throws ElException
//	 */
//	public void addUserIsPoll(int pollid,int userid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("insert into  poll_assign values(?,?,1)");
//			ps.setInt(1, pollid);
//			ps.setInt(2, userid);
//			ps.executeUpdate();
//		} catch (Exception e) {
//			logger.error("添加用户为已投票失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}
}
