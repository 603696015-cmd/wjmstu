package com.sopia.studyman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseNote;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.PracticePaper;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.schedule.entities.Eluser;
import com.sopia.studyman.StudyConstants;
import com.sopia.studyman.dao.StudyCourseDao;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyCourseRecord;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyPractice;
import com.sopia.studyman.entities.MyRoom;

public class StudyCourseDaoImpl implements StudyCourseDao {
	private static final Log logger = LogFactory
			.getLog(StudyCourseDaoImpl.class);

	public void addCnote(CourseNote cnote) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CNOTE_ADD));
			ps.setInt(1, cnote.getCourse().getId());
			ps.setInt(2, cnote.getCreater().getId());
			ps.setString(3, cnote.getContent());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setString(5, cnote.getTitle());
			ps.setFloat(6, cnote.getScore());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加笔记出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<CourseNote> listCnotes(int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseNote> cnotes = new ArrayList<CourseNote>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CNOTE_LIST));
			ps.setInt(1, courseid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseNote cnot = new CourseNote(rs.getInt(1), rs.getString(2),
						rs.getTimestamp(3), rs.getTimestamp(4));
				cnot.setScore(rs.getFloat(5));
				cnot.setTitle(rs.getString(6));
				cnot.setStatus(rs.getInt(7));
				cnotes.add(cnot);
			}
		} catch (Exception e) {
			logger.error("添加笔记出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cnotes;
	}

	public List<CourseNote> listMyCnotes(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseNote> cnotes = new ArrayList<CourseNote>();

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select cn.id,cn.content,cn.createtime,cn.modifytime,cn.score,cn.title,cn.status,c.id,c.name,cn.content from course_note cn left join course c on c.id = cn.courseid where cn.userid =? order by cn.modifytime desc");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseNote cnot = new CourseNote(rs.getInt(1), rs.getString(2),
						rs.getTimestamp(3), rs.getTimestamp(4));
				cnot.setScore(rs.getFloat(5));
				cnot.setTitle(rs.getString(6));
				cnot.setStatus(rs.getInt(7));
				cnot.setContent(rs.getString(10));
				cnot.setCourse(new Course(rs.getInt(8), rs.getString(9)));
				cnotes.add(cnot);
			}
		} catch (Exception e) {
			logger.error("添加笔记出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cnotes;
	}

	public void submitCnotes(CourseNote cnote) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update course_note set status = 1,createtime= ?,score = ?,modifytime= ?  where id = ?");
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setFloat(2, cnote.getScore());
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, cnote.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加笔记出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteCnote(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CNOTE_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除笔记出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public CourseNote getCnoteByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CourseNote note = new CourseNote();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CNOTE_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				note = new CourseNote(rs.getInt(1), rs.getString(2), rs
						.getTimestamp(3), rs.getTimestamp(4));
				note.setTitle(rs.getString(5));
				note.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				note.setScore(rs.getFloat(8));
			}
		} catch (Exception e) {
			logger.error("添加笔记出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return note;
	}

	public void alterCnote(CourseNote cnote) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CNOTE_ALTER));
			ps.setString(1, cnote.getContent());
			ps.setString(2, cnote.getTitle());
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, cnote.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加笔记出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int listMyCourseSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from study_course sc left join course c on sc.courseid = c.id where sc.userid = ? and sc.classid=0 and c.status != 9";
			ps = ct.prepareStatement(sql);

			ps.setInt(1, userid);

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listMyCepingCourseSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from study_course sc left join course c on sc.courseid = c.id where sc.userid = ? and sc.classid=-4  and  c.status != 9";
			ps = ct.prepareStatement(sql);

			ps.setInt(1, userid);

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<MyCourse> listMyCourse(int userid, int status)
			throws ElException {
		return listMyCourse(userid, status, 0, listMyCourseSize(userid, status));
	}

	public List<MyCourse> listMyCourse(int userid) throws ElException {
		return listMyCourse(userid, listMyCourseSize(userid), 0);
	}

	public int listMyCourseSize(int userid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_QUERY_SIZE_BYUID));

			ps.setInt(1, userid);
			ps.setInt(2, status);

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listMyObCourseSize(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_course where userid=? and classid=?");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<MyCourse> listMyCourse(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			// buffer.append("select * from (select t.*, rownum rn from (select
			// c.id cid, c.name,c.creater, eu.realname,c.credit, ")
			// .append(" c.during,c.teachername,sc.passtime/60
			// passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id
			// sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime
			// ")
			// .append(" from study_course sc left join course c on sc.courseid
			// = c.id ")
			// .append(" left join eluser eu on c.creater = eu.id left join
			// study_quizinfo sqi on sqi.id=sc.sqiid where sc.userid = ? and
			// sc.status != 3 order by sc.status asc, c.createtime desc) t where
			// rownum <= ? ) where rn>=?");//status = 1 经过人员审核 and sc.classid=0
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id cid, c.name,c.creater, eu.realname,c.credit, ")
					.append(
							" c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime,sc.classid,sc.jieyeid ,c.mainimg ")
					.append(
							" from study_course sc left join course c on sc.courseid = c.id  ")
					.append(
							" left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid where sc.userid = ?  and sc.classid=0 and c.status != 9  order by sc.status asc, c.createtime desc) t where rownum <= ? ) where rn>=?");// status
			// = 1
			// 经过人员审核
			// hwc
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				// 43 现场管理与现场改善实务 1 管理员 60 710 32 4 1
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				// c.setPassgrade(rs.getFloat(5));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				c.setJieye(rs.getInt("jieyeid"));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				mc.setMyCredit(rs.getFloat(12));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				mc.getMyExamPaper().setMyScore(rs.getInt(14));
				mc.getMyExamPaper().setIspassed(rs.getInt(15));

				// c.setRoomstart(rs.getTimestamp(16));
				// c.setRoomend(rs.getTimestamp(17));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				mc.setClassId(rs.getInt("classid"));
				c.setMainimg(rs.getString("mainimg"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyCourse> listMyCepingCourse(int userid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			// buffer.append("select * from (select t.*, rownum rn from (select
			// c.id cid, c.name,c.creater, eu.realname,c.credit, ")
			// .append(" c.during,c.teachername,sc.passtime/60
			// passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id
			// sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime
			// ")
			// .append(" from study_course sc left join course c on sc.courseid
			// = c.id ")
			// .append(" left join eluser eu on c.creater = eu.id left join
			// study_quizinfo sqi on sqi.id=sc.sqiid where sc.userid = ? and
			// sc.status != 3 order by sc.status asc, c.createtime desc) t where
			// rownum <= ? ) where rn>=?");//status = 1 经过人员审核 and sc.classid=0
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id cid, c.name,c.creater, eu.realname,c.credit, ")
					.append(
							" c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime,sc.classid  ")
					.append(
							" from study_course sc left join course c on sc.courseid = c.id  ")
					.append(
							" left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid where sc.userid = ?  and c.status != 9 and sc.classid=-4  order by sc.status asc, c.createtime desc) t where rownum <= ? ) where rn>=?");// status
			// = 1
			// 经过人员审核
			// and
			// sc.classid=0
			// c.isNormal
			// ==
			// 1已開通
			// and
			// sc.status
			// != 3
			// ?
			// hwc
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				// 43 现场管理与现场改善实务 1 管理员 60 710 32 4 1
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				// c.setPassgrade(rs.getFloat(5));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				mc.setMyCredit(rs.getFloat(12));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				mc.getMyExamPaper().setMyScore(rs.getInt(14));
				mc.getMyExamPaper().setIspassed(rs.getInt(15));

				// c.setRoomstart(rs.getTimestamp(16));
				// c.setRoomend(rs.getTimestamp(17));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				mc.setClassId(rs.getInt("classid"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyCourse> listMyCourse(int userid, int status, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_QUERY_BYUID));

			ps.setInt(1, userid);
			ps.setInt(2, status);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				// 43 现场管理与现场改善实务 1 管理员 60 710 32 4 1
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				// c.setPassgrade(rs.getFloat(5));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				// er.setId(rs.getInt(12));
				// mc.setExamRoom(er);
				mc.setMyCredit(rs.getFloat(12));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				mc.getMyExamPaper().setMyScore(rs.getInt(14));
				mc.getMyExamPaper().setIspassed(rs.getInt(15));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyCourse> listMyCreditCourse(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			// TODO 通过考试才能拿到学分？
			ps = ct
					.prepareStatement("select c.id,c.name,c.credit,ca.mycredit,ca.sqiid,sqi.myscore,sqi.ispassed,sqi.status,ca.status from study_course ca left join course c on ca.courseid = c.id "
							+ "left join study_quizinfo sqi on sqi.id = ca.sqiid  where ca.userid = ? ");
			ps.setInt(1, userid);

			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCredit(rs.getInt(3));
				MyCourse mc = new MyCourse();
				mc.setCourse(c);
				mc.setMyCredit(rs.getFloat(4));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(5)));
				mc.getMyExamPaper().setMyScore(rs.getInt(6));
				mc.getMyExamPaper().setIspassed(rs.getInt(7));
				mc.getMyExamPaper().setStatus(rs.getInt(8));
				mc.setStatus(rs.getInt(9));
				mc.setUser(new ELUser(userid));
				myBxc.add(mc);
				// if (checkCourseQuizIsPassed(c.getId(), userid))
				// mc.setMyCredit(c.getCredit());

			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public boolean checkMyCPage(MyCPage myCPage) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CPAGE_CHECK));
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpage().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测是否已学章节情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean checkMyCPage(MyCPage myCPage, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select * from study_cpage where userid = ? and cpid = ? and classid=?");
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpage().getId());
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测是否已学章节情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public MyCPage getMyCPage(int userid, int cpid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyCPage mp = new MyCPage();
		try {
			ct = DBConnection.getConnection();
			// String sql="select passtime,passed from study_cpage where userid
			// = ? and cpid = ? and classid=?";
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CPAGE_QUERY_BYUIDANDCPID));
			ps.setInt(1, userid);
			ps.setInt(2, cpid);
			rs = ps.executeQuery();
			if (rs.next()) {
				mp.setPasstime(rs.getInt(1));
				mp.setPassed(rs.getBoolean(2));
				mp.setCpage(new CoursePage(cpid));
			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mp;
	}

	/**
	 * 获取标准课程某章节信息(加班级)
	 */
	public MyCPage getMyCPage(int userid, int cpid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyCPage mp = new MyCPage();
		try {
			ct = DBConnection.getConnection();
			// String sql="select passtime,passed from study_cpage where userid
			// = ? and cpid = ? and classid=?";
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CPAGE_QUERY_BYUIDANDCPID));
			// ps=ct.prepareStatement("select
			// passtime,passed,passtime_2,myscore,passed2,userid from
			// study_cpage where userid = ? and cpid = ? and classid=?");
			// ps=ct.prepareStatement("select
			// sc.passtime,sc.passed,sc.passtime_2,sc.myscore,sc.passed2,sc.userid,pp.id
			// ppid,pp.title pptitle,pp.passgrade,ep.id epid,ep.ep_realscore
			// from study_cpage sc left join practicepaper pp on sc.cpid=pp.cpid
			// left join exampaper ep on ep.id=pp.epid where sc.userid = ? and
			// sc.cpid = ? and sc.classid=?");
			ps = ct
					.prepareStatement("select passtime,se.passed,se.passtime_2,se.myscore,se.passed2,se.userid,pp.id ppid,pp.title pptitle,"
							+ "pp.passgrade,ep.id epid,ep.ep_tscore,se.lessonLocation,se.lessonStatus,se.sessionTime from study_cpage se left join practicepaper pp on se.cpid=pp.cpid "
							+ "left join exampaper ep on ep.id=pp.epid where se.userid = ? and se.cpid = ? and se.classid=?");
			// ps=ct.prepareStatement("select
			// sc.passtime,sc.passed,sc.passtime_2,sc.myscore,sc.passed2,sc.userid
			// from study_cpage sc where sc.userid = ? and sc.cpid = ? and
			// sc.classid=?");
			ps.setInt(1, userid);
			ps.setInt(2, cpid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			PracticePaper pracp = null;
			ExamPaper examp = null;
			if (rs.next()) {
				mp.setPasstime(rs.getInt("passtime"));
				mp.setPassed(rs.getBoolean(2));
				mp.setCpage(new CoursePage(cpid));
				mp.setPasstime2(rs.getInt("passtime_2"));
				mp.setMyscore(rs.getFloat("myscore"));
				mp.setPassed2(rs.getInt("passed2"));
				mp.setUser(new ELUser(rs.getInt("userid")));
				pracp = new PracticePaper();
				pracp.setId(rs.getInt("ppid"));
				pracp.setTitle(rs.getString("pptitle"));
				examp = new ExamPaper(rs.getInt("epid"));
				examp.setEp_tscore(rs.getFloat("ep_tscore"));
				pracp.setExamPaper(examp);
				pracp.setPassgrade(rs.getFloat("passgrade"));
				mp.setLessonLocation(rs.getString(12));
				mp.setLessonStatus(rs.getString(13));
				mp.setLessonLocation(rs.getString(14));
				mp.setPracp(pracp);
			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mp;
	}

	public int getMyLastCpage(int userid, int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CPAGE_LASTID));
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}

	/**
	 * 获取最后学习时间的章节id
	 * 
	 * @param userid
	 * @param courseid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getMyLastCpage(int userid, int courseid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sc.cpid from study_cpage sc,course_page cp where sc.cpid= cp.id  and sc.userid = ? and cp.courseid= ? and sc.classid=? order by sc.endtime desc");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}

	/**
	 * 更新学员的最后学习时间
	 * 
	 * @param userid
	 * @param courseid
	 * @throws ElException
	 */
	public void cPageFinishSet(int userid, int cpid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_cpage set endtime=? where userid=? and cpid=? and classid=?");
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, userid);
			ps.setInt(3, cpid);
			ps.setInt(4, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置课程完成失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void intoMyCPage(MyCPage myCPage) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CPAGE_ADD));
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec into_scp ?,?");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call into_scp (?,?)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call into_scp (?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpage().getId());
			// ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("进入章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void intoMyCPage(MyCPage myCPage, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CPAGE_ADD));
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec into_scp ?,?");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call into_scp (?,?)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				// ps = ct.prepareStatement("call into_scp (?,?)");
				ps = ct.prepareStatement("call into_scp2(?,?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpage().getId());
			ps.setInt(3, classid);
			// ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();// 卡住？？？ 没有commit
		} catch (Exception e) {
			logger.error("进入章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void saveMyCPage(MyCPage myCPage) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CPAGE_PASSTIME_SET));
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec s_cpage ?,?,?");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call s_cpage (?,?,?)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call s_cpage (?,?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			// ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpage().getId());
			ps.setInt(3, 60);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void saveMyCourseStudy(MyCPage myCPage) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CPAGE_PASSTIME_SET));
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec s_cpage ?,?,?");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call s_cpage (?,?,?)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call sc_cpage (?,?,?,?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			// ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpid());
			ps.setInt(3, myCPage.getCourseid());
			ps.setInt(4, myCPage.getClassid());
			if (myCPage.getPasstime() < 2) {
				myCPage.setPasstime(0);
			}
			ps.setInt(5, myCPage.getPasstime());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void saveMyCPage(MyCPage myCPage, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CPAGE_PASSTIME_SET));
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec s_cpage ?,?,?");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call s_cpage (?,?,?)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				// ps = ct.prepareStatement("call s_cpage (?,?,?)");
				ps = ct.prepareStatement("call s_cpage2(?,?,?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			// ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpage().getId());
			ps.setInt(3, 60);
			ps.setInt(4, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 设置学员章节的相关信息
	 * 
	 * @param myCPage
	 * @param classid
	 * @throws ElException
	 */
	// public void setStudyCpageInfo(MyCPage myCPage, int classid, int courseid,
	// int during) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("update study_cpage set passtime=(select during*60 from
	// course_page where id=?),passed=1 where userid=? and cpid=?");
	// ps.setInt(1, myCPage.getCpage().getId());
	// ps.setInt(2, myCPage.getUser().getId());
	// ps.setInt(3, myCPage.getCpage().getId());
	// ps.executeUpdate();
	// int passtime = 0;
	// double process = 0;
	// passtime = this.getStudyCoursePm(myCPage, classid, courseid)
	// + during * 60;
	// process = passtime / (this.getCourseDr(courseid) * 60);
	//
	// } catch (Exception e) {
	// logger.error("保存学员的实际学习时长出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }
	public int getStudyCoursePm(MyCPage myCPage, int classid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sum(passtime) from (select * from study_cpage where userid =? and classid=? and cpid!=?) sc left join course_page cp on cp.id = sc.cpid where cp.courseid=?");
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, classid);
			ps.setInt(3, myCPage.getCpage().getId());
			ps.setInt(4, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("保存学员的实际学习时长出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int getCourseDr(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select during from course where id=?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("保存学员的实际学习时长出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 保存课程章节
	 * 
	 * @param myCPage
	 * @param classid
	 * @param ispassed
	 *            为1：代表考过了
	 * @param during
	 * @throws ElException
	 */
	public void saveMyCPage(MyCPage myCPage, int classid, int ispassed,
			int passtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_CPAGE_PASSTIME_SET));
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec s_cpage ?,?,?");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call s_cpage (?,?,?)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				// ps = ct.prepareStatement("call s_cpage (?,?,?)");
				ps = ct.prepareStatement("call s_cpage2(?,?,?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			// ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpage().getId());
			ps.setInt(3, passtime);
			ps.setInt(4, classid);
			// ps.setInt(5, ispassed);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新学员课程章节练习的成绩
	 * 
	 * @param myCPage
	 * @param score
	 * @param classid
	 * @throws ElException
	 */
	public void updateStudyCpageCcore(MyCPage myCPage, float score, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_cpage set myscore=? where userid=? and cpid=? and classid=?");
			ps.setFloat(1, score);
			ps.setInt(2, myCPage.getUser().getId());
			ps.setInt(3, myCPage.getCpage().getId());
			ps.setInt(4, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存学员的实际学习时长出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 设置考过
	 * 
	 * @param myCPage
	 * @param classid
	 * @throws ElException
	 */
	public void updateStudyCpagePassed2(MyCPage myCPage, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_cpage set passed2=? where userid=? and cpid=? and classid=?");
			ps.setFloat(1, 1);
			ps.setInt(2, myCPage.getUser().getId());
			ps.setInt(3, myCPage.getCpage().getId());
			ps.setInt(4, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存学员的实际学习时长出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 保存学员课程章节的实际学习时长
	 * 
	 * @param myCourse
	 * @param classid
	 * @throws ElException
	 */
	public void setStudyCpagePasstime2(MyCPage myCPage, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_cpage set passtime_2=passtime_2+60 where userid=? and cpid=? and classid=?");
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpage().getId());
			ps.setInt(3, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存学员的实际学习时长出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void saveMyCourse(MyCourse myCourse) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_PASSTIME_SET));
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec s_course ?,?,?");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call s_course (?,?,?)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				// ps = ct.prepareStatement("call s_course (?,?,?)");
				ps = ct.prepareStatement("call s_course (?,?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			// ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(1, myCourse.getUser().getId());
			ps.setInt(2, myCourse.getCourse().getId());
			ps.setInt(3, 60);

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void saveMyCourse(MyCourse myCourse, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_PASSTIME_SET));
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec s_course ?,?,?");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call s_course (?,?,?)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				// ps = ct.prepareStatement("call s_course (?,?,?)");
				ps = ct.prepareStatement("call s_course2 (?,?,?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			// ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(1, myCourse.getUser().getId());
			ps.setInt(2, myCourse.getCourse().getId());
			ps.setInt(3, 60);
			ps.setInt(4, classid);

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 保存学员的实际学习时长
	 * 
	 * @param myCourse
	 * @param classid
	 * @throws ElException
	 */
	public void setStudyCoursePasstime2(MyCourse myCourse, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_course set passtime_2=passtime_2+60 where userid=? and courseid=? and classid=?");
			ps.setInt(1, myCourse.getUser().getId());
			ps.setInt(2, myCourse.getCourse().getId());
			ps.setInt(3, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存学员的实际学习时长出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setCPagePassed(MyCPage myCPage) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CPAGE_PASSED_SET));
			ps.setInt(1, myCPage.getPasstime());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, myCPage.getUser().getId());
			ps.setInt(4, myCPage.getCpage().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置已学章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setCoursePassed(MyCourse myCPage) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_PASSED_SET));
			ps.setInt(1, myCPage.getPasstime());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, myCPage.getUser().getId());
			ps.setInt(4, myCPage.getCourse().getId());
			// logger.info(myCPage.getUser().getId() + "=="
			// + myCPage.getCourse().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置已学章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean courseIsPassed(MyCourse myCourse) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select passed from study_course where courseid = ? and userid = ?");
			ps.setInt(1, myCourse.getCourse().getId());
			ps.setInt(2, myCourse.getUser().getId());
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getBoolean(1);
		} catch (Exception e) {
			logger.error("设置已学章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<MyCPage> listCpsbyCUid(int courseid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCPage> mps = new ArrayList<MyCPage>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CPAGE_QUERY_BYUIDANDCID));
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyCPage mp = new MyCPage();
				CoursePage cp = new CoursePage(rs.getInt(1), rs.getString(2));
				cp.setSkipable(rs.getInt(3));
				mp.setPasstime(rs.getInt(4));
				boolean passed = rs.getBoolean(5);
				mp.setPassed(passed);
				mp.setBegintime(rs.getTimestamp(6));
				if (passed)
					mp.setEndtime(rs.getTimestamp(7));
				cp.setSortid(rs.getInt(8));
				cp.setProperty(rs.getInt(9));
				mp.setCpage(cp);
				mps.add(mp);
			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mps;
	}

	/**
	 * 查看章节学习情况（绑定班级）
	 * 
	 * @param courseid
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<MyCPage> listCpsbyCUid(int courseid, int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCPage> mps = new ArrayList<MyCPage>();
		try {
			ct = DBConnection.getConnection();
			// ps=ct.prepareStatement("select
			// cp.id,cp.title,cp.skipable,sc.passtime,sc.passed,sc.begintime,sc.endtime,cp.sortid,cp.property
			// from course_page cp left join ( select * from study_cpage where
			// userid=? ) sc on cp.id=sc.cpid where cp.courseid=? and
			// sc.classid=? order by cp.sortid");
			// ps=ct.prepareStatement("select
			// cp.id,cp.title,cp.skipable,sc.passtime,sc.passed,sc.begintime,sc.endtime,cp.sortid,cp.property
			// from course_page cp left join ( select * from study_cpage where
			// userid=? and classid=? ) sc on cp.id=sc.cpid where cp.courseid=?
			// order by cp.sortid");
			ps = ct
					.prepareStatement("select cp.id,cp.title,cp.skipable,sc.passtime,sc.passed,sc.begintime,sc.endtime,cp.sortid,cp.property,pp.id ppid,pp.title pptitle,pp.epid ppepid from course_page cp left join ( select * from study_cpage where userid=? and classid=? ) sc on cp.id=sc.cpid left join practicepaper pp on sc.cpid=pp.cpid where cp.courseid=? order by cp.sortid");
			ps.setInt(1, userid);
			// ps.setInt(2, courseid);
			ps.setInt(2, classid);
			ps.setInt(3, courseid);
			rs = ps.executeQuery();
			List<MyPractice> mppList = null;
			MyPractice mpp = null;
			while (rs.next()) {
				MyCPage mp = new MyCPage();
				CoursePage cp = new CoursePage(rs.getInt(1), rs.getString(2));
				cp.setSkipable(rs.getInt(3));
				mp.setPasstime(rs.getInt(4));
				boolean passed = rs.getBoolean(5);
				mp.setPassed(passed);
				mp.setBegintime(rs.getTimestamp(6));
				if (passed)
					mp.setEndtime(rs.getTimestamp(7));
				cp.setSortid(rs.getInt(8));
				cp.setProperty(rs.getInt(9));
				mp.setCpage(cp);
				mpp = new MyPractice();
				mpp.setPpaper(new PracticePaper(rs.getInt("ppid"), rs
						.getString("pptitle")));
				mppList = new ArrayList<MyPractice>();
				mppList.add(mpp);
				mp.setMyPracs(mppList);
				mp.setExamPaper(new ExamPaper(rs.getInt("ppepid")));
				mps.add(mp);
			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mps;
	}

	public List<MyCPage> listCpsbyCUid_wjm(int courseid, int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCPage> mps = new ArrayList<MyCPage>();
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select cp.id,cp.title,cp.skipable,sc.passtime,sc.passed,sc.begintime,sc.endtime,cp.sortid,cp.property  ," +
//					" er.id erid ,er.title ertitle,er.cpid ,cp.courseid " +
//					" from course_page cp,exam_room er ,study_cpage sc " +
//					" where cp.id=er.cpid and cp.courseid=er.courseid and cp.id=sc.cpid and sc.userid=? and cp.courseid=?" +
//					" order by cp.sortid");
			String sql ="select " +
			" cp.id,cp.title,cp.skipable,sc.passtime,sc.passed,sc.begintime,sc.endtime,cp.sortid,cp.property  ," +
			" er.id erid ,er.title ertitle,er.cpid ercpid," +
			" cp.courseid,sc.process,cp.during,sc.passtime_2,sc.passed2, " +
			" se.epid," +
			" cp.pic_g,cp.pic_l,cp.pic_h," +
			" cp.isnull  " +
			" from course_page cp  " +
			" left join study_cpage sc on cp.id = sc.cpid  and sc.userid = ? and sc.classid=? " +
			" left join exam_room er on cp.id = er.cpid and cp.courseid = er.courseid and er.sortid=1" +
			" left join study_exampaper se on se.roomid=er.id and se.userid=?  and se.epid!=0" + 
			" where   cp.courseid = ? " +
			" order by cp.sortid";
			ps = ct.prepareStatement(sql);
			logger.info(sql);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, userid);
			ps.setInt(4, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyCPage mp = new MyCPage();
				CoursePage cp = new CoursePage(rs.getInt(1), rs.getString(2));
				cp.setPic_g(rs.getString(19));
				cp.setPic_l(rs.getString(20));
				cp.setPic_h(rs.getString(21));
				cp.setSkipable(rs.getInt(3));
				cp.setIsNull(rs.getInt(22));
				mp.setPasstime(rs.getInt(4));
				boolean passed = rs.getBoolean(5);
				mp.setPassed(passed);
				mp.setBegintime(rs.getTimestamp(6));
				if (passed)
					mp.setEndtime(rs.getTimestamp(7));
				cp.setSortid(rs.getInt(8));
				cp.setProperty(rs.getInt(9));
				mp.setCpage(cp);
				mp.setCourseid(rs.getInt(13));
				mp.setProcess(rs.getFloat(14));
				cp.setDuring(rs.getInt(15));
				mp.setPasstime2(rs.getInt(16));
				mp.setPassed2(rs.getInt(17));
				mp.setExamPaper(new ExamPaper(rs.getInt(18)));
				ExamRoom examRoom = new ExamRoom(rs.getInt(10),rs.getString(11));
				examRoom.setCpid(rs.getInt(12));
				mp.setExamRoom(examRoom);
				
				mps.add(mp);
			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mps;
	}
	

	/**
	 * 获取学员课程章节信息
	 * 
	 * @param courseid
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<MyCPage> getStudyCpageInfo(int courseid, int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCPage> mps = new ArrayList<MyCPage>();
		try {
			ct = DBConnection.getConnection();
			// ps=ct.prepareStatement("select cp.id cpid,cp.title
			// cptitle,cp.type cptype,pp.id ppid,pp.title
			// pptitle,sp.myscore,sp.passed2,cp.getcredit,sp.passtime,sp.passtime_2,cp.during
			// cpduring from course_page cp left join study_cpage sp on
			// cp.id=sp.cpid left join practicepaper pp on cp.id=pp.cpid where
			// sp.userid=? and cp.courseid=? and classid=?");
			ps = ct
					.prepareStatement("select cp.id cpid,cp.title cptitle,cp.type cptype,pp.id ppid,pp.title pptitle,sp.myscore,sp.passed2,"
							+ "cp.getcredit,sp.passtime,sp.passtime_2,cp.during cpduring,ep.id epid,ep.title eptitle,sp.process from course_page cp left join study_cpage sp on cp.id=sp.cpid left join practicepaper pp on cp.id=pp.cpid left join exampaper ep on pp.epid=ep.id where sp.userid=? and cp.courseid=? and classid=? order by cp.sortid");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			PracticePaper pp = null;
			while (rs.next()) {
				MyCPage mp = new MyCPage();
				CoursePage cp = new CoursePage(rs.getInt(1), rs.getString(2));
				cp.setType(rs.getInt(3));
				pp = new PracticePaper(rs.getInt(4), rs.getString(5));
				mp.setMyscore(rs.getFloat(6));
				mp.setPassed2(rs.getInt(7));
				cp.setGetcredit(rs.getInt(8));
				mp.setPasstime(rs.getInt(9));
				mp.setPasstime2(rs.getInt(10));
				cp.setDuring(rs.getInt(11));
				pp.setExamPaper(new ExamPaper(rs.getInt(12), rs.getString(13)));
				mp.setProcess(rs.getFloat(14));
				mp.setCpage(cp);
				mp.setPracp(pp);
				mps.add(mp);
			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mps;
	}

	public boolean checkMyCourse(MyCourse myCourse) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_CHECK));
			ps.setInt(1, myCourse.getUser().getId());
			ps.setInt(2, myCourse.getCourse().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				b = true;
			}
		} catch (Exception e) {
			logger.error("检测是否已学课程情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	/**
	 * 查询课程申请状态
	 * 
	 * @author jiahaijiang
	 * @param myCourse
	 * @return
	 * @throws ElException
	 */
	public int checkMyCourseValid(MyCourse myCourse) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_CHECK));
			ps.setInt(1, myCourse.getUser().getId());
			ps.setInt(2, myCourse.getCourse().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("valid");
			} else {
				return 2;
			}

		} catch (Exception e) {
			logger.error("检测是否已学课程情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// public boolean checkMySelectCourse(int userid, int courseid)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(StudyConstants.STUDY_COURSE_SELECT_CHECK));
	// ps.setInt(1, userid);
	// ps.setInt(2, courseid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return true;
	// }
	// } catch (Exception e) {
	// logger.error("检测是选修学课程情况！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return false;
	// }

	public void intoMyCourse(MyCourse myCourse) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec into_sc ?,?,0,1");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call into_sc (?,?,0,1)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call s_course (?,?,?)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			ps.setInt(1, myCourse.getUser().getId());
			ps.setInt(2, myCourse.getCourse().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("进入课程学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 检测课程是否完成
	 */
	public boolean checkCourseIsFinish(int courseid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CPAGE_QUERY_BYCID));
			// 检测所有的网页是否学习完成
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (!checkCpageIsFinish(rs.getInt(1), userid)) {
					return false;
				}
			}
			// 检测课程练习是否完成
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_PPAPER_QUERY_BYCID));
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int ppid = rs.getInt(1);
				if (!checkPpaperIsFinish(ppid, userid))
					return false;
			}
		} catch (Exception e) {
			logger.error("检测是否已学课程情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return true;
	}

	/**
	 * 网页学习检测
	 */
	public boolean checkCpageIsFinish(int cpid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CPAGE_QUERY_BYUIDANDCPID));
			ps.setInt(1, userid);
			ps.setInt(2, cpid);
			rs = ps.executeQuery();
			// 网页是否学习
			boolean isstudy = false;
			if (rs.next()) {
				isstudy = rs.getBoolean(2);
			}
			if (!isstudy)
				return false;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_PPAPER_QUERY_BYCPID));
			ps.setInt(1, cpid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int ppid = rs.getInt(1);
				if (!checkPpaperIsFinish(ppid, userid)) {
					return false;
				}
			}

		} catch (Exception e) {
			logger.error("检测是否已学课程情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return true;
	}

	/**
	 * 检查某人某课程考试是否通过
	 * 
	 * @param courseid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	/*
	 * private boolean checkCourseQuizIsPassed(int courseid, int userid) throws
	 * ElException { PreparedStatement ps = null; ResultSet rs = null;
	 * Connection ct = null; try { ct = DBConnection.getConnection(); ps = ct
	 * .prepareStatement("select sqi.ispassed from study_quizinfo sqi ,exam_room
	 * er where er.id = sqi.roomid and er.courseid= ? and sqi.userid=?");
	 * ps.setInt(1, courseid); ps.setInt(2, userid); rs = ps.executeQuery(); if
	 * (rs.next()) return rs.getBoolean(1); } catch (Exception e) {
	 * logger.error("检测课程考试是否通过！", e); throw new ElException(e); } finally {
	 * DBConnection.closeConnectInfo(ct, ps, rs); } return false; }
	 */
	/**
	 * 检测课程是否通过
	 */
	public boolean checkCourseIsPassed(int courseid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			// 检测课程是否通过
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_FINISH_CHECH));
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next())
				b = rs.getBoolean(1);
			if (b)
				return b;
			else {
				// 未通过
				b = this.checkCourseIsFinish(courseid, userid);
				if (b) {
					// 检测试卷（找到场次）
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(StudyConstants.STUDY_COURSE_ROOMID_BYCUID));
					ps.setInt(1, userid);
					ps.setInt(2, courseid);
					if (rs.next()) {
						b = this.checkQpaperIsFinish(rs.getInt(1), userid);
						if (b) {
							this.courseFinishSet(userid, courseid);
						}
					}
				} else {
					return false;
				}
			}

		} catch (Exception e) {
			logger.error("检测是否已学课程情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	public void intoPpaper(int ppid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_PPAPER_ADD));
			ps.setInt(1, ppid);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("开始练习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Course> listAllCourseFromSuper(int userid, int depid,
			String name, int ctid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_CANAPPLY_FROM_SUPER));
			ps.setInt(1, CourseConstants.COURSE_STATUS_HASOPENED);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, depid);
			// ps.setInt(4, userid);
			ps.setInt(4, ctid);
			ps.setInt(5, pageNow);
			ps.setInt(6, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				css.add(c);
			}
		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	public List<Course> listAllCourseFromThis(int userid, int depid,
			String name, int ctid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from course_type where id =?");
			int lid = 0, rid = 0;
			ps.setInt(1, ctid);
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_CANAPPLY_FROM_THIS));
			ps.setInt(1, CourseConstants.COURSE_STATUS_HASOPENED);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, depid);
			// ps.setInt(4, userid);
			ps.setInt(4, lid);
			ps.setInt(5, rid);
			ps.setInt(6, pageNow);
			ps.setInt(7, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setDuring(rs.getInt(11));
				c.setIslink(rs.getInt(12));
				c.setRoomstart(rs.getTimestamp(13));
				c.setRoomend(rs.getTimestamp(14));
				c.setTeacherName(rs.getString(15));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	public int listAllCourseSizeFromSuper(int userid, int depid, String name,
			int ctid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(StudyConstants.STUDY_COURSE_CANAPPLY_FROM_SUPER_SIZE));
			ps.setInt(1, CourseConstants.COURSE_STATUS_HASOPENED);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, depid);
			ps.setInt(4, userid);
			ps.setInt(5, ctid);

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listAllCourseSizeFromThis(int userid, int depid, String name,
			int ctid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from course_type where id =?");
			int lid = 0, rid = 0;
			ps.setInt(1, ctid);
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(StudyConstants.STUDY_COURSE_CANAPPLY_FROM_THIS_SIZE));
			ps.setInt(1, CourseConstants.COURSE_STATUS_HASOPENED);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, depid);
			// ps.setInt(4, userid);
			ps.setInt(4, lid);
			ps.setInt(5, rid);

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void studyApplyCourse(int userid, int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// int status = 1;
			// // 查看到课程的类别
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_APPLY_CTYPE));
			// ps.setInt(1, SystemConfOp
			// .getIntValue(ElConstants.STUDY_COURSE_CTYPE_B));
			// ps.setInt(2, courseid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// status = CourseConstants.COURSE_STUDY_STATUS_BX;
			// }
			// rs.close();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_APPLY_CTYPE));
			// ps.setInt(1, SystemConfOp
			// .getIntValue(ElConstants.STUDY_COURSE_CTYPE_Z));
			// ps.setInt(2, courseid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// status = CourseConstants.COURSE_STUDY_STATUS_ZX;
			// }
			// rs.close();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_APPLY_CTYPE));
			// ps.setInt(1, SystemConfOp
			// .getIntValue(ElConstants.STUDY_COURSE_CTYPE_X));
			// ps.setInt(2, courseid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// status = CourseConstants.COURSE_STUDY_STATUS_XX;
			// }
			// rs.close();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_APPLY));
			// ps.setInt(1, courseid);
			// ps.setInt(2, userid);
			// // ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			// ps.setInt(3, status);
			// int valid = 3;
			// if
			// (!SystemConfOp.getBooleanValue(ElConstants.STUDY_COURSE_NEED_SH))
			// valid = 1;
			// ps.setInt(4, valid);
			// ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("exec into_sc ?,?,0,1");

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call into_sc (?,?,0,1)");
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("call into_sc (?,?,0,1)");
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("课程审核！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkPpaperIsFinish(int ppid, int userid) throws ElException {
		// TODO 练习是否通过！
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_PPAPER_QUERY_BYPPIDANDUID));
			ps.setInt(1, ppid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;

		} catch (Exception e) {
			logger.error("检测是否已学课程情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean checkQpaperIsFinish(int qpid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// TODO 场次中courseid应该没有作用
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_QPAPER_FINISH_CHECK));
			ps.setInt(1, qpid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				int myscore = rs.getInt(1);
				int epscore = rs.getInt(2);
				// 选择课程通过线
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(StudyConstants.STUDY_QPAPER_COURSE_SCORE_BYRID));
				ps.setInt(1, qpid);
				ResultSet rs1 = ps.executeQuery();
				if (rs.next()) {
					int passgrade = rs1.getInt(1);
					if (myscore >= (epscore * passgrade / 100))
						return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("检测课程考试是否通过！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	// private boolean checkClassPassed
	// 已废弃
	public boolean classCanGraduate(int userid, int classid,
			int cloptionalcredit) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 1.比修课修完
			boolean bx = true;
			boolean xx = true;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_CORUSE_BYSTATUS));
			ps.setInt(1, classid);
			ps.setInt(2, CourseConstants.COURSE_STUDY_STATUS_BX);
			rs = ps.executeQuery();
			while (rs.next()) {
				int courseid = rs.getInt(1);
				bx = checkCourseIsPassed(courseid, userid);
			}
			ps.close();
			rs.close();
			// 2.选修课修完
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_CORUSE_BYSTATUS));
			ps.setInt(1, classid);
			ps.setInt(2, CourseConstants.COURSE_STUDY_STATUS_XX);
			rs = ps.executeQuery();
			int score = 0;
			while (rs.next()) {
				int courseid = rs.getInt(1);
				xx = checkCourseIsPassed(courseid, userid);
				score += rs.getInt(2);
				if (score >= cloptionalcredit) {
					xx = true;
					break;
				} else
					xx = false;
			}
			ps.close();
			rs.close();
			return bx && xx;
		} catch (Exception e) {
			logger.error("可结业班级列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// return false;
	}

	public void courseFinishSet(int userid, int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_FINISH_SET));
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, userid);
			ps.setInt(3, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置课程完成失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
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
					.getSQL(StudyConstants.STUDY_COURSE_PH));
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course course = new Course(rs.getInt(1), rs.getString(2));
				course.setCtype(new CourseType(rs.getInt(3), rs.getString(4)));
				course.setCreater(new ELUser(rs.getInt(5), rs.getString(6)));
				course.setCreatetime(rs.getTimestamp(7));
				course.setUserCount(rs.getInt(8));
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

	public int listPhCourseSize() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_PH_SIZE));
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

	public void study_course_delete(int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_DELETE));
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean study_course_delete_check(int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_DELETE_CHECK));
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/*
	 * public MyCourse getMyCourseInfo(int userid) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * MyCourse m = new MyCourse(); try {
	 * m.setBxc_time(myCourseInfo_time(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_BX));
	 * m.setBxc_xxtime(myCourseInfo_xxtime(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_BX));
	 * m.setXxc_time(myCourseInfo_time(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_XX));
	 * m.setXxc_xxtime(myCourseInfo_xxtime(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_XX));
	 * m.setZxc_time(myCourseInfo_time(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_ZX));
	 * m.setZxc_xxtime(myCourseInfo_xxtime(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_ZX));
	 * 
	 * m.setBxep_count(myCourseInfo_ep_count(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_BX));
	 * m.setBxep_score(myCourseInfo_ep_score(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_BX));
	 * m.setZxep_count(myCourseInfo_ep_count(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_ZX));
	 * m.setZxep_score(myCourseInfo_ep_score(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_ZX));
	 * m.setXxep_count(myCourseInfo_ep_count(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_XX));
	 * m.setXxep_score(myCourseInfo_ep_score(userid,
	 * CourseConstants.COURSE_STUDY_STATUS_XX)); // m.setQtep_count(qtep_count); //
	 * m.setQtep_score(qtep_count); // m.setCpep_count(cpep_count); //
	 * m.setCpep_score(cpep_count) ct = DBConnection.getConnection(); ps = ct
	 * .prepareStatement("select count(*) from exam_room er , study_quizinfo ra
	 * where ra.userid = ? and er.iscommon = 1"); ps.setInt(1, userid); rs =
	 * ps.executeQuery(); if (rs.next()) { m.setQtep_count(rs.getInt(1)); } ct =
	 * DBConnection.getConnection(); ps = ct .prepareStatement("select
	 * sum(sqi.myscore) from exam_room ra,study_quizinfo sqi where sqi.userid = ?
	 * and ra.iscommon = 1 and ra.id = sqi.roomid"); ps.setInt(1, userid); rs =
	 * ps.executeQuery(); if (rs.next()) { m.setQtep_score(rs.getInt(1)); } ct =
	 * DBConnection.getConnection(); ps = ct .prepareStatement("select count(*)
	 * from troom_assign ra where ra.userid = ? "); ps.setInt(1, userid); rs =
	 * ps.executeQuery(); if (rs.next()) { m.setCpep_count(rs.getInt(1)); } ct =
	 * DBConnection.getConnection(); ps = ct .prepareStatement("select
	 * sum(sqi.myscore) from troom_epinfo sqi where sqi.userid = ? ");
	 * ps.setInt(1, userid); rs = ps.executeQuery(); if (rs.next()) {
	 * m.setCpep_score(rs.getInt(1)); } } catch (Exception e) {
	 * logger.error("我的课程列表出错！", e); throw new ElException(e); } finally {
	 * DBConnection.closeConnectInfo(ct, ps, rs); } return m; }
	 */
	public MyCourse getMyCourseInfo(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyCourse m = new MyCourse();
		try {
			// m.setXxc_time(myCourseInfo_time(userid,
			// CourseConstants.COURSE_STUDY_STATUS_XX));
			// m.setXxc_xxtime(myCourseInfo_xxtime(userid,
			// CourseConstants.COURSE_STUDY_STATUS_XX));
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	// private int myCourseInfo_time(int userid, int status) throws ElException
	// {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select sum(c.during) from course c,course_apply ca
	// where c.id = ca.courseid and ca.userid = ? and ca.status = ?");
	// ps.setInt(1, userid);
	// ps.setInt(2, status);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("我的课程列表出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }

	// private int myCourseInfo_xxtime(int userid, int status) throws
	// ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select sum(sc.passtime) from course_apply
	// ca,study_course sc where ca.courseid = sc.courseid and ca.userid = ? and
	// ca.status = ?");
	// ps.setInt(1, userid);
	// ps.setInt(2, status);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1) / 60;
	// }
	// } catch (Exception e) {
	// logger.error("我的课程列表出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }
	//
	// private int myCourseInfo_ep_count(int userid, int status)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select count(*) from course_apply ca,study_quizinfo
	// ra where ra.userid = ca.userid and ra.userid = ? and ca.status = ?");
	// ps.setInt(1, userid);
	// ps.setInt(2, status);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("我的课程列表出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }
	//
	// private int myCourseInfo_ep_score(int userid, int status)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select sqi.myscore from course_apply ca left join
	// exam_room ra on ra.courseid = ca.courseid left join study_quizinfo sqi
	// on sqi.roomid= ra.id and ca.userid = ? and ca.status = ?");
	// ps.setInt(1, userid);
	// ps.setInt(2, status);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("我的课程列表出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }

	public MyCourse getMyStudyCourse(int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyCourse m = new MyCourse();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sc.passed,sc.passtime,sc.process,c.name cname from study_course sc left join course c on c.id=sc.courseid where sc.userid =? and courseid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.setPassed(rs.getBoolean(1));
				m.setPasstime(rs.getInt(2));
				m.setProcess(rs.getFloat("process"));
				m.setCourse(new Course(courseid, rs.getString("cname")));
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	public MyCourse getMyStudyCourse(int userid, int courseid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyCourse m = new MyCourse();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sc.passed,sc.passtime,sc.process,c.name cname,sc.passtime_2,c.during cduring,sc.lessonLocation,sc.lessonStatus,sc.sessionTime from study_course sc left join course c on c.id=sc.courseid where sc.userid =? and courseid = ? and classid=?");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			Course course = null;
			if (rs.next()) {
				m.setPassed(rs.getBoolean(1));
				m.setPasstime(rs.getInt(2));
				m.setProcess(rs.getFloat("process"));
				m.setPasstime2(rs.getInt("passtime_2"));
				course = new Course(courseid, rs.getString("cname"));
				course.setDuring(rs.getInt("cduring"));
				m.setLessonLocation(rs.getString(7));
				m.setLessonStatus(rs.getString(8));
				m.setSessionTime(rs.getString(9));
				m.setCourse(course);
				m.setUser(new ELUser(userid));
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	public int study_cppasstime(int userid, int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_COURSE_CPAGE_PASSTIME));
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 自动设置全局培训班
	 */
	public void atuoSetGlobalClass(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from elclass where global =1 ");

			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				// int group2 = rs.getInt(2);

				// graduateClassApplay(userid, id);

				if (checkClassInUser(userid, id)) {

				} else {
					ps = ct
							.prepareStatement("insert into class_apply (classid,userid,applyDate,status ) values(?,?,?,? )");
					ps.setInt(1, id);
					ps.setInt(2, userid);
					ps.setDate(3, new Date(System.currentTimeMillis()));
					ps.setInt(4, 2);
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("自动分配培训班", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkClassInUser(int userid, int classid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from class_apply where userid = ? and classid =?");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<MyCourse> listMyAllCourse(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			String sql = "select * from (select t.*, rownum rn from (select c.id cid, c.name,c.creater, eu.realname,c.credit,c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime,sc.classid from study_course sc left join course c on sc.courseid = c.id	left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid where sc.userid = ? order by sc.status asc, c.createtime desc) t where rownum <= ? ) where rn>=?";
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_QUERYWITHOUTSTATUS_BYUID));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				// 43 现场管理与现场改善实务 1 管理员 60 710 32 4 1
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				// c.setPassgrade(rs.getFloat(5));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getFloat(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				mc.setMyCredit(rs.getFloat(12));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				mc.getMyExamPaper().setMyScore(rs.getInt(14));
				mc.getMyExamPaper().setIspassed(rs.getInt(15));
				// c.setRoomstart(rs.getTimestamp(16));
				// c.setRoomend(rs.getTimestamp(17));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setClassid(rs.getInt(20));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyCourse> study_index_listMyAllCourse(int userid, int number)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			String sql = "select t.*, rownum rn from ("
					+ "select c.id cid, c.name,c.creater, eu.realname,c.credit,"
					+ "c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,"
					+ "c.islink,sc.mycredit,c.roomstart,c.roomend,sc.starttime,sc.finishtime,sc.classid from study_course sc left join course c on sc.courseid = c.id	left join eluser eu on c.creater = eu.id  where sc.userid = ?   order by sc.status asc, c.createtime desc) t where rownum <= ? ";
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_QUERYWITHOUTSTATUS_BYUID));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, number);

			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				// 43 现场管理与现场改善实务 1 管理员 60 710 32 4 1
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				// c.setPassgrade(rs.getFloat(5));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				mc.setMyCredit(rs.getFloat(12));
				// mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				// mc.getMyExamPaper().setMyScore(rs.getInt(14));
				// mc.getMyExamPaper().setIspassed(rs.getInt(15));
				// c.setRoomstart(rs.getTimestamp(16));
				// c.setRoomend(rs.getTimestamp(17));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setClassid(rs.getInt("classid"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("个人中心首页我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyCourse> listMyCourseByClassid(int userid, int classid,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();

		try {
			String sql = "select * from (select t.*, rownum rn from (select c.id cid, c.name,c.creater, eu.realname,c.credit,c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime,sc.classid,sc.jieyeid,er.id as erid,er.courseid from study_course sc left join course c on sc.courseid = c.id	left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid left join exam_room er on er.courseid=c.id where sc.userid=? and sc.classid = ? order by sc.status asc, c.createtime desc) t where rownum <= ? ) where rn>=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();

			while (rs.next()) {
				// 标准课程
				// 43 现场管理与现场改善实务 1 管理员 60 710 32 4 1
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				// c.setPassgrade(rs.getFloat(5));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				mc.setMyCredit(rs.getFloat(12));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				mc.getMyExamPaper().setMyScore(rs.getInt(14));
				mc.getMyExamPaper().setIspassed(rs.getInt(15));
				// c.setRoomstart(rs.getTimestamp(16));
				// c.setRoomend(rs.getTimestamp(17));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setClassid(rs.getInt(20));
				c.setJieye(rs.getInt("jieyeid"));
				mc.setExamRoom(new ExamRoom(rs.getInt("erid")));
				mc.getExamRoom().setId(rs.getInt("erid"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return myBxc;
	}

	public int listMyAllCourseSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(StudyConstants.STUDY_COURSE_QUERYWITHOUTSTATUS_SIZE_BYUID));

			ps.setInt(1, userid);

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Integer> listMyAllCourse(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			String sql = "select c.id cid, c.name,c.creater, eu.realname,c.credit,c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime from study_course sc left join course c on sc.courseid = c.id	left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid where sc.userid = ? order by sc.status asc, c.createtime desc";
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_COURSE_QUERYWITHOUTSTATUS_BYUID));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);

			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				// 43 现场管理与现场改善实务 1 管理员 60 710 32 4 1
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				// c.setPassgrade(rs.getFloat(5));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				mc.setMyCredit(rs.getFloat(12));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				mc.getMyExamPaper().setMyScore(rs.getInt(14));
				mc.getMyExamPaper().setIspassed(rs.getInt(15));
				// c.setRoomstart(rs.getTimestamp(16));
				// c.setRoomend(rs.getTimestamp(17));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		int alltime = 0;
		int truetime = 0;
		List<Integer> li = new ArrayList<Integer>();
		for (MyCourse myCourse : myBxc) {
			alltime += myCourse.getCourse().getDuring();
			truetime += myCourse.getPasstime();
		}
		li.add(alltime);
		li.add(truetime);
		return li;
	}

	public List<Course> listAllCourseFromThis(CourseType ctypeTree, int userid,
			int depid, String name, int ctid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select lid,rid from course_type where id =?");
			// int lid = 0, rid = 0;
			// ps.setInt(1, ctid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// lid = rs.getInt(1);
			// rid = rs.getInt(2);
			// }
			// rs.close();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,c.name,c.ctypeid,")
					.append(
							" c.status,c.createtime,c.modifytime,c.creater,ct.name ctmane,u.realname,c.credit,")
					.append(
							" c.during,c.islink,c.roomstart,c.roomend,c.teacherName from course c, COURSE_TYPE ct,ELUSER u,DEPARTMENT dep")
					.append(
							" where c.ctypeid=ct.id and c.creater = u.id and u.depid=dep.id and c.status = ?")
					.append(
							" and c.name like ?  and ct.id in("
									+ createPerTypeId(ctypeTree, ctid)
									+ ") order by c.createtime desc)t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, CourseConstants.COURSE_STATUS_HASOPENED);
			ps.setString(2, "%" + name + "%");
			// ps.setInt(3, depid);
			// ps.setInt(4, userid);
			// ps.setInt(4, lid);
			// ps.setInt(5, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setDuring(rs.getInt(11));
				c.setIslink(rs.getInt(12));
				c.setRoomstart(rs.getTimestamp(13));
				c.setRoomend(rs.getTimestamp(14));
				c.setTeacherName(rs.getString(15));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("我要选课列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	public int listAllCourseSizeFromThis(CourseType ctypeTree, int userid,
			int depid, String name, int ctid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select lid,rid from course_type where id =?");
			// int lid = 0, rid = 0;
			// ps.setInt(1, ctid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// lid = rs.getInt(1);
			// rid = rs.getInt(2);
			// }
			// rs.close();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select count(*) from (select c.id,c.name,c.ctypeid,")
					.append(
							" c.status,c.createtime,c.modifytime,c.creater,ct.name ctmane,u.realname,c.credit,")
					.append(
							" c.during from course c, COURSE_TYPE ct,ELUSER u,DEPARTMENT dep")
					.append(
							" where c.ctypeid=ct.id and c.creater = u.id and u.depid=dep.id and c.status = ?")
					.append(
							" and c.name like ? and dep.id=?  and ct.id in("
									+ createPerTypeId(ctypeTree, ctid)
									+ ") order by c.createtime desc)t ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, CourseConstants.COURSE_STATUS_HASOPENED);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, depid);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我要选课列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String createPerTypeId(CourseType ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = getCourseTypeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree.getChild() != null) {
				return createTypeId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * 
	 * @author jiahaijiang
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private CourseType getCourseTypeById(List<CourseType> listType, int ctid) {
		CourseType ctypeTree = null;
		for (CourseType type : listType) {
			if (type.getId() != ctid) {
				ctypeTree = getCourseTypeById(type.getChild(), ctid);
				if (ctypeTree != null) {
					return ctypeTree;
				}
			} else {
				ctypeTree = type;
				return ctypeTree;
			}
		}
		return ctypeTree;
	}

	/**
	 * 构建有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @return
	 */
	private String createTypeId(List<CourseType> listType, int id) {
		String ids = id + "";
		for (CourseType type : listType) {
			ids = ids + "," + createTypeId(type.getChild(), type.getId());
		}
		return ids;
	}

	public void saveMyCourse_S(MyCourse myCourse) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_course set lessonLocation = ? , lessonStatus= ? ,sessionTime = ? where userid = ? and courseid= ? and classid=?");

			ps.setString(1, myCourse.getLessonLocation());
			ps.setString(2, myCourse.getLessonStatus());
			ps.setString(3, myCourse.getSessionTime());
			ps.setInt(4, myCourse.getUser().getId());
			ps.setInt(5, myCourse.getCourse().getId());
			ps.setInt(6, myCourse.getClassId());
			ps.executeUpdate();
			// ps.close();
			// if ("mssql".equals(SystemConfOp
			// .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			// ps = ct.prepareStatement("exec s_course ?,?,?");
			//
			// } else if ("mysql".equals(SystemConfOp
			// .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			// ps = ct.prepareStatement("call s_course (?,?,?)");
			// } else if ("oracle".equals(SystemConfOp
			// .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			// ps = ct.prepareStatement("call s_course2_s (?,?,?,?)");
			// }
			// ps.setInt(1, myCourse.getUser().getId());
			// ps.setInt(2, myCourse.getCourse().getId());
			// ps.setInt(3, myCourse.getPasstime());
			// ps.setInt(4, myCourse.getClassId());
			// ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void saveMyCPage_S(MyCPage myCPage) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_cpage set lessonLocation = ? , lessonStatus= ? ,sessionTime = ? where userid = ? and cpid= ? and classid=?");
			ps.setString(1, myCPage.getLessonLocation());
			ps.setString(2, myCPage.getLessonStatus());
			ps.setString(3, myCPage.getSessionTime());
			ps.setInt(4, myCPage.getUser().getId());
			ps.setInt(5, myCPage.getCpage().getId());
			ps.setInt(6, myCPage.getClassid());
			ps.executeUpdate();
			ps.close();
			// if ("mssql".equals(SystemConfOp
			// .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			// ps = ct.prepareStatement("exec s_cpage ?,?,?");
			//
			// } else if ("mysql".equals(SystemConfOp
			// .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			// ps = ct.prepareStatement("call s_cpage (?,?,?)");
			// } else if ("oracle".equals(SystemConfOp
			// .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			// ps = ct.prepareStatement("call s_cpage2_s(?,?,?,?)");
			// }
			// ps.setInt(1, myCPage.getUser().getId());
			// ps.setInt(2, myCPage.getCpage().getId());
			// ps.setInt(3, myCPage.getPasstime());
			// ps.setInt(4, myCPage.getClassid());
			// ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 添加学员课程学习记录
	 * 
	 * @param myCourseRecord
	 * @throws ElException
	 */
	// public void addStudyCourseRecord(MyCourseRecord myCourseRecord) throws
	// ElException{
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement("insert into
	// study_course_record(scid,cpid,userid,begintime,status)
	// values(?,?,?,?,?)");
	// ps.setInt(1, myCourseRecord.getMyCourse().getCourse().getId());
	// ps.setInt(2, myCourseRecord.getCoursePage().getId());
	// ps.setInt(3, myCourseRecord.getEluser().getId());
	// ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
	// ps.setInt(5, 1);
	// ps.executeUpdate();
	// } catch (Exception e) {
	// logger.error("添加学员课程学习记录出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }
	/**
	 * 添加学员课程学习记录
	 * 
	 * @param myCourseRecord
	 * @throws ElException
	 */
	public int addStudyCourseRecord(int courseid, int classid, int cpid,
			int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_course_record(courseid,classid,cpid,userid,begintime,status) values(?,?,?,?,?,?)");
			ps.setInt(1, courseid);
			ps.setInt(2, classid);
			ps.setInt(3, cpid);
			ps.setInt(4, userid);
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setInt(6, 1);
			ps.executeUpdate();
			ps.close();
			ps = ct
					.prepareStatement("select study_course_record_sequence.currval from dual ");
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加学员课程学习记录出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	/**
	 * 更新学员课程学习记录的状态和退出时间
	 * 
	 * @param status
	 * @param endtime
	 *            退出时间（如果退出时间等于null，那么这条数据记录有误，可能是服务器重启造成）
	 * @throws ElException
	 */
	public void updateStudyCourseRecordStatusByid(int recordId, int status,
			Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_course_record set status=?,endtime=? where id=?");
			ps.setInt(1, status);
			ps.setTimestamp(2, endtime);
			ps.setInt(3, recordId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新学员课程学习记录的状态和退出时间出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新学员课程学习记录的状态和退出时间
	 * 
	 * @param status
	 * @param endtime
	 *            退出时间（如果退出时间等于null，那么这条数据记录有误，可能是服务器重启造成）
	 * @throws ElException
	 */
	public void updateStudyCourseRecordStatus(int courseid, int classid,
			int cpid, int userid, int status, Timestamp endtime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_course_record set status=?,endtime=? "
							+ " where id=(select * from (select id from study_course_record where courseid=? and classid=? and cpid=? and userid=? and endtime is null order by begintime asc) where rownum=1)");
			ps.setInt(1, status);
			ps.setTimestamp(2, endtime);
			ps.setInt(3, courseid);
			ps.setInt(4, classid);
			ps.setInt(5, cpid);
			ps.setInt(6, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新学员课程学习记录的状态和退出时间出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新该学员所有结束时间为空的学习记录（用户退出和session销毁时有调用）
	 * 
	 * @param userid
	 * @param status
	 * @param endtime
	 * @throws ElException
	 */
	public void updateStudyCourseRecordStatus(int userid, int status,
			Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_course_record set status=?,endtime=? where userid=? and endtime is null");
			ps.setInt(1, status);
			ps.setTimestamp(2, endtime);
			ps.setInt(3, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新该学员所有结束时间为空的学习记录出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 保存学习记录的学习时间
	 * 
	 * @param id
	 * @param passtime
	 * @throws ElException
	 */
	public void saveStudyCourseRecordPasstime(int id, int passtime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_course_record set passtime=passtime+? where id=?");
			if (passtime < 2) {
				passtime = 0;
			}
			ps.setInt(1, passtime);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存学习记录的学习时间出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void insertCepingCourse(int userid, int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call into_sc10(?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("调用JTM接口返回课程ids，插入课程分配表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void deleteCePingCoursesByUseridAndClassid(int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from study_course where userid=? and classid=?");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("调用JTM接口插入课程分配表时，删除上次插入的课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}
	/**
	 * 检查章节状态
	 */
	public int checkPass(int userid,MyCPage myCPage) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int flag = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select passed  from study_cpage where userid=? and cpid=?");
			ps.setInt(1, userid);
			ps.setInt(2,  myCPage.getCpid());
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)==1){
					flag=1;
				}
			}
		} catch (Exception e) {
			logger.error("查询状态出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public List<MyCPage> myCPages(int userid,MyCPage myCPage)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCPage> mps = new ArrayList<MyCPage>();
		try {
			ct = DBConnection.getConnection();
			// ps=ct.prepareStatement("select cp.id cpid,cp.title
			// cptitle,cp.type cptype,pp.id ppid,pp.title
			// pptitle,sp.myscore,sp.passed2,cp.getcredit,sp.passtime,sp.passtime_2,cp.during
			// cpduring from course_page cp left join study_cpage sp on
			// cp.id=sp.cpid left join practicepaper pp on cp.id=pp.cpid where
			// sp.userid=? and cp.courseid=? and classid=?");
			ps = ct
					.prepareStatement("select distinct(er.id), cp.id,ers.epid"+
										 " from exam_room er, course_page cp, exam_reps ers"+
										 " where cp.id = er.cpid(+)"+
										"   and er.id = ers.roomid(+)"+
										"   and cp.id = ?");
//			ps.setInt(1, userid);
			ps.setInt(1, myCPage.getCpid());
//			ps.setInt(3, myCPage.getClassid());
			rs = ps.executeQuery();
			while (rs.next()) {
				MyCPage mp = new MyCPage();
//				MyRoom myroom = new MyRoom();
				ExamRoom er = new ExamRoom();
				er.setId(rs.getInt(1));
				ExamPaper ep =new ExamPaper();
				ep.setId(rs.getInt(3));
				
//				ELUser el = new ELUser();
//				el.setId(rs.getInt(3));
//				mp.setUser(el);
//				CoursePage cp = new CoursePage(rs.getInt(1), rs.getString(2));
//				cp.setType(rs.getInt(3));
//				pp = new PracticePaper(rs.getInt(4), rs.getString(5));
//				mp.setMyscore(rs.getFloat(6));
//				mp.setPassed2(rs.getInt(7));
//				cp.setGetcredit(rs.getInt(8));
//				mp.setPasstime(rs.getInt(9));
//				mp.setPasstime2(rs.getInt(10));
//				cp.setDuring(rs.getInt(11));
//				pp.setExamPaper(new ExamPaper(rs.getInt(12), rs.getString(13)));
//				mp.setProcess(rs.getFloat(14));
//				mp.setCpage(cp);
//				mp.setPracp(pp);
//				myroom.setExamroom(er);
				mp.setExamRoom(er);
				mp.setExamPaper(ep);
				mp.setCpid(rs.getInt(2));
				mps.add(mp);
			}
		} catch (Exception e) {
			logger.error("当前章节学习情况！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mps;
	}

	public int getNextCpid(int classid, int courseid,int cpid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call getnextcpid(?,?,?,?)}");  
			cs.setInt(1, classid);
			cs.setInt(2, courseid);
			cs.setInt(3, cpid);
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getInt(4));
			cpid = cs.getInt(4);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cpid;
	}

	
	public void updateStudyCourse(MyCourse mycourse) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update study_course set passtime=?,process=?,mycredit=? where userid=? and courseid=? and classid=?");
			ps.setInt(1, mycourse.getPasstime());
			ps.setFloat(2, mycourse.getProcess());
			ps.setFloat(3, mycourse.getMyCredit());
			ps.setInt(4, mycourse.getUser().getId());
			ps.setInt(5, mycourse.getCourse().getId());
			ps.setInt(6, mycourse.getClassId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	//在用户打开时插入study_course一条记录
	public void insertOneRecord(int userid,int courseid)throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into study_course(userid,courseid,starttime,finishtime) values(?,?,?,to_date('2080-12-31','yyyy-mm-dd'))");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	//检测用户记录是否插入
	public boolean  isRecord(int userid,int courseid)throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag=false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from study_course where userid=? and courseid=? ");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if(rs.next()){
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	
	public void updateStudyNextCpage(MyCPage myCPage) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call sc_cpage_setnull (?,?,?,?)");
			ps.setInt(1, myCPage.getUser().getId());
			ps.setInt(2, myCPage.getCpid());
			ps.setInt(3, myCPage.getCourseid());
			ps.setInt(4, myCPage.getClassid());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存章节学习！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
