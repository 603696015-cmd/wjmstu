package com.sopia.courseman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adl.parsers.dom.ADLItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CoursePageDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.ScormCourse;
import com.sopia.openmeetings.Rooms;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.questionman.tags.PracticePaper;

public class CoursePageDaoImpl implements CoursePageDao {
	private static final Log logger = LogFactory
			.getLog(CoursePageDaoImpl.class);

	public void addCoursePage(CoursePage cp) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CPAGE_ADD));
			ps.setInt(1, cp.getCourse().getId());
			ps.setString(2, cp.getTitle());
			ps.setInt(3, cp.getType());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setString(5, cp.getPage());
			ps.setInt(6, maxSortIdInCp(cp.getCourse().getId()) + 1);// cp.getSortid());;
			ps.setString(7, cp.getPage_url());
			ps.setInt(8, cp.getProperty());
			ps.setInt(9, cp.getQueryTime());
			ps.setInt(10, cp.getDuring());
			ps.setInt(11, cp.getSkipable());
			ps.executeUpdate();
			ps.close();
			setCourseDuring(cp.getCourse().getId());

		} catch (Exception e) {
			logger.error("ÃÌº”øŒ≥ÃÕ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * ÃÌº”øŒ≥Ã’¬Ω⁄
	 * 
	 * @param cp
	 * @throws ElException
	 */
	public void addCoursePage2(CoursePage cp) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
//					.prepareStatement("insert into course_page (courseid,title,type,createtime,page, sortid,page_url,property,querytime,during,skipable,getcredit,identifier,during_s,prerequisites,html5) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					.prepareStatement("insert into course_page (courseid,title,type,createtime,page, sortid,page_url,property,querytime,during,skipable,getcredit,identifier,during_s,prerequisites,html5,islive,isfree,roomid,isnull) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, cp.getCourse().getId());
			ps.setString(2, cp.getTitle());
			ps.setInt(3, cp.getType());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setString(5, cp.getPage());
			ps.setInt(6, maxSortIdInCp(cp.getCourse().getId()) + 1);// cp.getSortid());;
			ps.setString(7, cp.getPage_url());
			ps.setInt(8, cp.getProperty());
			ps.setInt(9, cp.getQueryTime());
			ps.setInt(10, cp.getDuring());
			ps.setInt(11, cp.getSkipable());
			ps.setInt(12, cp.getGetcredit());
			ps.setString(13, cp.getIdentifier());
			ps.setString(14, cp.getDuring_s());
			ps.setString(15, cp.getPrerequisites());
			ps.setString(16, cp.getHtml5());
			ps.setInt(17, cp.getIslive());
			ps.setInt(18, cp.getIsfree());
			ps.setInt(19, cp.getRoom().getId());
			ps.setInt(20, cp.getIsNull());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('course_page') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select course_page_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error(" ˝æ›ø‚≈‰÷√”–ŒÛ,«Î»∑»œ «∑ÒŒ™oracle,mysqlªÚ’ﬂsqlserver ˝æ›ø‚°£");
				throw new ElException(" ˝æ›ø‚≈‰÷√”–ŒÛ£°£°£°");
			}
			if (rs.next())
				cp.setId(rs.getInt(1));
			ps.close();
			setCourseDuring(cp.getCourse().getId());

		} catch (Exception e) {
			logger.error("ÃÌº”øŒ≥ÃÕ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int maxSortIdInCp(int courseid) throws ElException {
		int sortid = 0;
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CPAGE_QUERY_MAX_SORTID));
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next())
				sortid = rs.getInt(1);

		} catch (Exception e) {
			logger.error("ªÒ»°Õ¯“≥÷–µƒsortid£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sortid;
	}

	public int getCDuringAScpage(int courseid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int during = 0;
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("select sum(cp.during) from course_page cp where cp.courseid = ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				during = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("ÃÌº”øŒ≥ÃÕ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return during;
	}

	public List<CoursePage> listCps(int courseid) throws ElException {
		List<CoursePage> cps = new ArrayList<CoursePage>();
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.CPAGE_QUERY_LIST_BYCID));
			ps = ct
					.prepareStatement("select cp.id,cp.title,cp.courseid,cp.type,cp.createtime,cp.modifytime,cp.sortid,cp.property,cp.during,cp.getcredit,"
							+ " pp.id ppid,pp.title pptitle"
							+
							// " er.id erid, er.title ertitle " +
							" from course_page cp left join practicepaper pp on cp.id=pp.cpid "
							+
							// " left join exam_room er on
							// er.courseid=cp.courseid and er.cpid=cp.id " +
							" where cp.courseid = ?   order by cp.sortid");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			com.sopia.courseman.entities.PracticePaper pracp = null;
			while (rs.next()) {
				CoursePage cp = new CoursePage(rs.getInt(1), rs.getString(2));
				cp.setCourse(new Course(rs.getInt(3)));
				cp.setType(rs.getInt(4));
				cp.setCreatetime(rs.getTimestamp(5));
				cp.setModifytime(rs.getTimestamp(6));
				cp.setSortid(rs.getInt(7));
				cp.setProperty(rs.getInt(8));
				cp.setDuring(rs.getInt(9));
				cp.setGetcredit(rs.getInt("getcredit"));
				pracp = new com.sopia.courseman.entities.PracticePaper();
				pracp.setId(rs.getInt("ppid"));
				pracp.setTitle(rs.getString("pptitle"));
				cp.setPracp(pracp);
				// cp.setExamRoom(new
				// ExamRoom(rs.getInt("erid"),rs.getString("ertitle")));
				cps.add(cp);
			}

		} catch (Exception e) {
			logger.error("ªÒ»°Õ¯“≥÷–µƒsortid£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cps;
	}

	public List<PracticePaper> listPps(int courseid) throws ElException {

		return null;
	}

	public CoursePage getCp(int id) throws ElException {
		CoursePage cp = null;
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.CPAGE_QUERY_BYID));
			ps = ct
//					.prepareStatement("select id,title,page,type,page_url,property,sortid,querytime,skipable,during,courseid,getcredit,html5 from course_page where id= ?");
					.prepareStatement("select id,title,page,type,page_url,property,sortid,querytime,skipable,during,courseid,getcredit,html5,islive,isfree,roomid,isnull from course_page where id= ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				cp = new CoursePage(rs.getInt(1), rs.getString(2));
				cp.setPage(rs.getString(3));
				cp.setType(rs.getInt(4));
				cp.setPage_url(rs.getString(5));
				cp.setProperty(rs.getInt(6));
				cp.setSortid(rs.getInt(7));
				cp.setQueryTime(rs.getInt(8));
				cp.setSkipable(rs.getInt(9));
				cp.setDuring(rs.getInt(10));
				cp.setCourse(new Course(rs.getInt(11)));
				cp.setGetcredit(rs.getInt("getcredit"));
				cp.setHtml5(rs.getString(13));
				cp.setIslive(rs.getInt(14));
				cp.setIsfree(rs.getInt(15));
				cp.setRoom(new Rooms(rs.getInt(16)));
				cp.setIsNull(rs.getInt(17));
			}
		} catch (Exception e) {
			logger.error("ªÒ»°Õ¯“≥£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cp;
	}

	public void alterCp(CoursePage cp) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CPAGE_ALTER));
			ps.setString(1, cp.getTitle());
			ps.setInt(2, cp.getType());
			ps.setString(3, cp.getPage());
			ps.setString(4, cp.getPage_url());
			ps.setInt(5, cp.getProperty());
			ps.setInt(6, cp.getQueryTime());
			ps.setInt(7, cp.getDuring());
			ps.setInt(8, cp.getSkipable());
			ps.setInt(9, cp.getId());
			ps.executeUpdate();
			ps.close();

			setCourseDuring(cp.getCourse().getId());

		} catch (Exception e) {
			logger.error("–ﬁ∏ƒøŒ≥ÃÕ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * –ﬁ∏ƒøŒ≥Ã’¬Ω⁄
	 * 
	 * @param cp
	 * @throws ElException
	 */
	public void alterCp2(CoursePage cp) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
//					.prepareStatement("update course_page set title=?,type=?,page=?,page_url=?,property=?,querytime=?,during=?,skipable=?,getcredit=?,html5=? where id = ?");
					.prepareStatement("update course_page set title=?,type=?,page=?,page_url=?,property=?,querytime=?,during=?,skipable=?,getcredit=?,html5=?,islive=?,isfree=?,pic_g=?,pic_l=?,pic_h=?,isnull=? where id = ?");
			ps.setString(1, cp.getTitle());
			ps.setInt(2, cp.getType());
			ps.setString(3, cp.getPage());
			ps.setString(4, cp.getPage_url());
			ps.setInt(5, cp.getProperty());
			ps.setInt(6, cp.getQueryTime());
			ps.setInt(7, cp.getDuring());
			ps.setInt(8, cp.getSkipable());
			ps.setInt(9, cp.getGetcredit());
			ps.setString(10, cp.getHtml5());
			ps.setInt(11, cp.getIslive());
			ps.setInt(12, cp.getIsfree());
			ps.setString(13, cp.getPic_g());
			ps.setString(14, cp.getPic_l());
			ps.setString(15, cp.getPic_h());
			ps.setInt(16, cp.getIsNull());
			ps.setInt(17, cp.getId());
			ps.executeUpdate();
			ps.close();

			setCourseDuring(cp.getCourse().getId());

		} catch (Exception e) {
			logger.error("–ﬁ∏ƒøŒ≥ÃÕ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int getFirstCpId(int courseid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CPAGE_QUERY_FIRST_BYCID));
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("øŒ≥Ãµ⁄“ªÕ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void sortCps(int courseid, int sortid, int upordown)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			if (upordown == ElConstants.SORT_UP) {
				upSort(ct, courseid, sortid);

			} else {
				downSort(ct, courseid, sortid);
			}
		} catch (Exception e) {
			logger.error("“∆∂ØÕ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void sortRoom(int roomid, int sortid, int upordown,int courseid,int cpid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			if (upordown == ElConstants.SORT_UP) {
				upSort_room(ct, roomid, sortid,courseid,cpid);

			} else {
				downSort_room(ct, roomid, sortid,courseid,cpid);
			}
		} catch (Exception e) {
			logger.error("“∆∂ØÕ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void upSort(Connection ct, int courseid, int sortid)
			throws ElException {
		try {
			Statement st = ct.createStatement();
			if (sortid > 0) {
				String sql = "select id from course_page where courseid = "
						+ courseid + " and sortid = " + (sortid - 1);
				ResultSet rs = st.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update course_page set sortid=sortid-1 "
							+ " where courseid = " + courseid + " and sortid="
							+ sortid;
					st.executeUpdate(sql);
					sql = "update course_page set sortid=sortid+1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);
				}
			}
			st.close();

		} catch (Exception e) {
			logger.error("Õ¯“≥…œ“∆ ß∞‹£°", e);
			throw new ElException("Õ¯“≥…œ“∆ ß∞‹", e);
		}
	}

	private void downSort(Connection ct, int courseid, int sortid)
			throws ElException {
		try {
			Statement st = ct.createStatement();
			String sql = "select max(sortid) from course_page where courseid= "
					+ courseid;
			ResultSet rs = st.executeQuery(sql);
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select id from course_page where courseid = " + courseid
						+ " and sortid = " + (sortid + 1);
				rs = st.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update course_page set sortid=sortid+1 "
							+ " where courseid = " + courseid + " and sortid="
							+ sortid;
					st.executeUpdate(sql);
					sql = "update course_page set sortid=sortid-1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);
				}
			}
			st.close();
		} catch (Exception e) {
			logger.error("Õ¯“≥œ¬“∆ ß∞‹£°", e);
			throw new ElException("Õ¯“≥œ¬“∆ ß∞‹", e);
		}
	}

	private void upSort_room(Connection ct, int roomid, int sortid,int courseid,int cpid)
			throws ElException {
		try {
			Statement st = ct.createStatement();
			if (sortid > 0) {
				String sql = "select id from exam_room where courseid = "
						+ courseid + " and cpid = "+cpid+" and classid=0 and sortid = " + (sortid - 1);
				ResultSet rs = st.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update exam_room set sortid=sortid-1 "
							+ " where id = " + roomid ;
					st.executeUpdate(sql);
					sql = "update exam_room set sortid=sortid+1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);
				}
			}
			st.close();

		} catch (Exception e) {
			logger.error("Õ¯“≥…œ“∆ ß∞‹£°", e);
			throw new ElException("Õ¯“≥…œ“∆ ß∞‹", e);
		}
	}

	private void downSort_room(Connection ct, int roomid, int sortid,int courseid,int cpid)
			throws ElException {
		try {
			Statement st = ct.createStatement();
			String sql = "select max(sortid) from exam_room where courseid= "
					+ courseid + " and cpid = " + cpid  + " and classid=0 ";
			ResultSet rs = st.executeQuery(sql);
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select id from exam_room where courseid = " + courseid
						+ " and cpid="+cpid+" and classid=0 and sortid = " + (sortid + 1);
				rs = st.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update exam_room set sortid=sortid+1 "
							+ " where id=" + roomid;
					st.executeUpdate(sql);
					sql = "update exam_room set sortid=sortid-1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);
				}
			}
			st.close();
		} catch (Exception e) {
			logger.error("Õ¯“≥œ¬“∆ ß∞‹£°", e);
			throw new ElException("Õ¯“≥œ¬“∆ ß∞‹", e);
		}
	}

	public void deleteCp(int cpid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// Ω´¥Û”⁄∏√idµƒÕ¯“≥…œ“∆
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CPAGE_QUERY_CIDANDSID));
			ps.setInt(1, cpid);
			rs = ps.executeQuery();
			int courseid = 0;
			int sortid = 0;
			if (rs.next()) {
				courseid = rs.getInt(1);
				sortid = rs.getInt(2);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CPAGE_BIGSORT_SET));
			ps.setInt(1, sortid);
			ps.setInt(2, courseid);
			ps.executeUpdate();
			// TODO …æ≥˝¡∑œ∞//—ßœ∞
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CPAGE_DELETE));
			ps.setInt(1, cpid);
			ps.executeUpdate();
			setCourseDuring(courseid);

		} catch (Exception e) {
			logger.error("…æ≥˝Õ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setCourseDuring(int courseid) throws ElException {

		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select islink from course where id = ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			int islink = 0;
			if (rs.next()) {
				islink = rs.getInt(1);
			}
			rs.close();
			ps.close();
			// ÷ª”–±Í◊ºøŒ≥Ãµƒ ±≥§≤≈∏˘æ›’¬Ω⁄±‰ªØ
			if (islink == 0) {
				ps = ct
						.prepareStatement("update course set during = ? where id = ?");
				ps.setInt(1, getCDuringAScpage(courseid));
				ps.setInt(2, courseid);
				ps.executeUpdate();
			}

		} catch (Exception e) {
			logger.error("…æ≥˝Õ¯“≥ ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addSCItem(ScormCourse ci) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			// ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("INSERT INTO
			// COURSE_ITEM(identifier,name,url,cid,during,prerequisites,sequence
			// ) values(?,?,?,?,?,?,?)");
			// ps.setString(1, ci.getIdentifier());
			// ps.setString(2, ci.getName());
			// ps.setString(3, ci.getUrl());
			// ps.setInt(4, ci.getCourse().getId());
			// ps.setString(5, ci.getDuring());
			// ps.setString(6, ci.getPrerequisites());
			// ps.setInt(7, ci.getSequence());
			// ps.executeUpdate();
		} catch (Exception e) {
			logger.error("scorm item saved failed ß∞‹£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkPageCanlearn(int sortid, int courseid, int userid,int cpid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct
					.prepareCall("{call checkpagecanlearn(?,?,?,?,?)}");
			cs.setInt(1, sortid);
			cs.setInt(2, courseid);
			cs.setInt(3, cpid);
			cs.setInt(4, userid);
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			flag = cs.getBoolean(5);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public CoursePage getBeginCPage(int courseid, int sortid)
			throws ElException {
		CoursePage cp = null;
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.CPAGE_QUERY_BYID));
			ps = ct
					.prepareStatement("select id,title,page,type,page_url,property,sortid,querytime,skipable,during,courseid,getcredit from course_page where courseid= ? and sortid=?");
			ps.setInt(1, courseid);
			ps.setInt(2, sortid);
			rs = ps.executeQuery();
			if (rs.next()) {
				cp = new CoursePage(rs.getInt(1), rs.getString(2));
				cp.setPage(rs.getString(3));
				cp.setType(rs.getInt(4));
				cp.setPage_url(rs.getString(5));
				cp.setProperty(rs.getInt(6));
				cp.setSortid(rs.getInt(7));
				cp.setQueryTime(rs.getInt(8));
				cp.setSkipable(rs.getInt(9));
				cp.setDuring(rs.getInt(10));
				cp.setCourse(new Course(rs.getInt(11)));
				cp.setGetcredit(rs.getInt("getcredit"));
			}
		} catch (Exception e) {
			logger.error("ªÒ»°Õ¯“≥£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cp;
	}

	public void addStuff(String addr, String title, int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("insert into knowledge_stuff(stuffaddr,title,cpageid) values(?,?,?)");
			ps.setString(1, addr);
			ps.setString(2, title);
			ps.setInt(3, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("ªÒ»°Õ¯“≥÷–µƒsortid£°", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<StuffLib> getStuffs(int cpageid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<StuffLib> stuffs = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,stuffaddr,title from knowledge_stuff where cpageid=?");
			ps.setInt(1, cpageid);
			rs = ps.executeQuery();
			while(rs.next()){
				StuffLib stuff = new StuffLib();
				stuff.setId(rs.getInt(1));
				stuff.setDescription(rs.getString(2));
				stuff.setTitle(rs.getString(3));
				stuffs.add(stuff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuffs;
	}

	public void alterStuff(int id, String stuffaddr, String title)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update knowledge_stuff set stuffaddr=?,title=? where id=?");
			ps.setString(1, stuffaddr);
			ps.setString(2, title);
			ps.setInt(3, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteStuffByid(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from knowledge_stuff where id=?");
			ps.setInt(1, id);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
