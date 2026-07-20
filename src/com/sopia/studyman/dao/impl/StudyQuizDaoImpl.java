package com.sopia.studyman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.common.OracleClob;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.dao.impl.EroomDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.ErPara;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.Examprac;
import com.sopia.courseman.entities.PracticePaper;
import com.sopia.courseman.entities.SimexamPaper;
import com.sopia.courseman.entities.MultiUserPapers;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.UnitRanking;
import com.sopia.questionman.QtypeUtil;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.QuestionLib;
import com.sopia.studyman.StudyConstants;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyEprac;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyPractice;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.MyRoomRecord;
import com.sopia.studyman.entities.PointsRecord;

public class StudyQuizDaoImpl implements StudyQuizDao {
	private static final Log logger = LogFactory.getLog(StudyQuizDaoImpl.class);

	public List<MyPractice> listMyPracpapers(int userid, int courseid, int cpid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyPractice> myP = new ArrayList<MyPractice>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(StudyConstants.STUDY_PPAPER_QUERY_BYUIDANDCIDANDPID));
			ps.setInt(1, courseid);
			ps.setInt(2, cpid);
			// ps.setInt(3, userid);

			rs = ps.executeQuery();
			while (rs.next()) {
				PracticePaper pp = new PracticePaper(rs.getInt(1));
				MyPractice mp = new MyPractice();
				// pp.id, ep.id,ep.title,pp.sortid,pp.skipable
				pp.setExamPaper(new ExamPaper(rs.getInt(2), rs.getString(3)));
				pp.setSortid(rs.getInt(4));
				pp.setSkipable(rs.getInt(5));
				pp.setCourse(new Course(courseid));
				mp.setPpaper(pp);
				// PreparedStatement ps1 = ct
				// .prepareStatement(ElQuerySql
				// .getSQL(StudyConstants.STUDY_PPAPER_QUERY_BYPPIDANDUID));
				// ps1.setInt(1, pp.getId());
				// ps1.setInt(2, userid);
				// ResultSet rs1 = ps1.executeQuery();
				// if (rs1.next()) {
				// mp.setMyScore(rs1.getFloat(1));
				// // mp.setLasttime(rs1.getTimestamp(2));
				// }
				// rs1.close();
				// ps1.close();
				myP.add(mp);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myP;
	}

	public ExamRoom getExamRoomByUandC(int courseid, int userid)
			throws ElException {
		ExamRoom er = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select er.id,er.courseid,er.createrid,er.title, er.description,er.location , er.begintime,er.endtime "
							+ " from EXAM_ROOM er left join study_quizinfo ra on er.id = ra.roomid "
							+ " where ra.userid = ? and er.courseid = ? ");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				er = new ExamRoom(rs.getInt(1), rs.getString(4));
				er.setCourse(new Course(rs.getInt(2)));
				er.setCreater(new ELUser(rs.getInt(3)));
				er.setDescription(rs.getString(5));
				er.setLocation(rs.getString(6));
				er.setBegintime(rs.getTimestamp(7));
				er.setEndtime(rs.getTimestamp(8));

			}
		} catch (Exception e) {
			logger.error("锟揭的课程匡拷锟皆筹拷锟轿筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}

	public void saveQuizPaper(MyExamPaper myExamPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_QPAPER_SAVE));
			// ps = ct
			// .prepareStatement(" update study_quizinfo set myAnswer =
			// ?,passTime = ? where id = ?");

			// ps.setString(1, myExamPaper.getMyAnswer());
			ps.setInt(2, myExamPaper.getPassTime());
			ps.setInt(3, myExamPaper.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟芥考锟皆达拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷时锟斤拷
	 * 
	 * @param myExamPaper
	 * @throws ElException
	 */
	public void saveQuizPaperPasstime(MyExamPaper myExamPaper)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set passTime = ? where id = ?");
			ps.setInt(1, myExamPaper.getPassTime());
			ps.setInt(2, myExamPaper.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int intoQuizPaper(int uid, int roomid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int sqid = 0;
		try {
			ct = DBConnection.getConnection();

			// ps = ct.prepareStatement("insert into
			// study_quizinfo(userid,roomid,begintime,endtime,epid)
			// values(?,?,?,?,?)");
			ps = ct
					.prepareStatement("insert into study_quizinfo(userid,roomid,begintime,endtime,epid,status) values(?,?,?,?,?,0)");// 默锟较匡拷锟斤拷锟斤拷
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, epid);
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('study_quizinfo') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select studyquizinfo_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("锟斤拷菘锟斤拷锟斤拷锟斤拷锟斤拷锟�,锟斤拷确锟斤拷锟角凤拷为oracle,mysql锟斤拷锟斤拷sqlserver锟斤拷菘狻�");
				throw new ElException("锟斤拷菘锟斤拷锟斤拷锟斤拷锟斤拷螅。锟斤拷锟�");
			}
			if (rs.next()) {
				sqid = rs.getInt(1);
			}
			rs.close();
			ps = ct
					.prepareStatement("select courseid from exam_room where id = ?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();

			int courseid = 0;
			if (rs.next()) {
				courseid = rs.getInt(1);
			}

			if (courseid != 0 && sqid != 0) {
				ps = ct
						.prepareStatement("update study_course set sqiid=? where courseid = ? and userid = ?");
				ps.setInt(1, sqid);
				ps.setInt(2, courseid);
				ps.setInt(3, uid);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟诫考锟皆筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sqid;
	}
	
	/**
	 * wsj1025锟睫革拷 锟斤拷影嗉�
	
	* @Title: intoQuizPaper  
	
	* @Description: TODO 
	
	* @param @param uid
	* @param @param roomid
	* @param @param epid
	* @param @param classid
	* @param @param userid
	* @param @return
	* @param @throws ElException      
	
	* @return int     
	
	* @throws
	 */
	public int intoQuizPaper(int uid, int roomid, int epid,int classid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int sqid = 0;
		try {
			ct = DBConnection.getConnection();

			// ps = ct.prepareStatement("insert into
			// study_quizinfo(userid,roomid,begintime,endtime,epid)
			// values(?,?,?,?,?)");
			ps = ct
					.prepareStatement("insert into study_quizinfo(userid,roomid,begintime,endtime,epid,classid,status) values(?,?,?,?,?,?,0)");// 默锟较匡拷锟斤拷锟斤拷
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, epid);
			ps.setInt(6, classid);
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('study_quizinfo') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select studyquizinfo_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("锟斤拷菘锟斤拷锟斤拷锟斤拷锟斤拷锟�,锟斤拷确锟斤拷锟角凤拷为oracle,mysql锟斤拷锟斤拷sqlserver锟斤拷菘狻�");
				throw new ElException("锟斤拷菘锟斤拷锟斤拷锟斤拷锟斤拷螅。锟斤拷锟�");
			}
			if (rs.next()) {
				sqid = rs.getInt(1);
			}
			rs.close();
			ps = ct
					.prepareStatement("select courseid from exam_room where id = ?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();

			int courseid = 0;
			if (rs.next()) {
				courseid = rs.getInt(1);
			}

			if (courseid != 0 && sqid != 0) {
				ps = ct
						.prepareStatement("update study_course set sqiid=? where courseid = ? and userid = ?");
				ps.setInt(1, sqid);
				ps.setInt(2, courseid);
				ps.setInt(3, uid);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟诫考锟皆筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sqid;
	}

	/**
	 * 锟斤拷锟絪tudy_quizinfo锟斤拷息(锟接班级)
	 * 
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @throws ElException
	 */
	public void intoQuizPaper(int uid, int roomid, int epid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("insert into study_quizinfo(userid,roomid,begintime,endtime,epid,classid) values(?,?,?,?,?,?)");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, epid);
			ps.setInt(6, classid);
			// ps.setInt(7, mrrid);
			ps.executeUpdate();
			ps.close();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('study_quizinfo') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select studyquizinfo_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("锟斤拷菘锟斤拷锟斤拷锟斤拷锟斤拷锟�,锟斤拷确锟斤拷锟角凤拷为oracle,mysql锟斤拷锟斤拷sqlserver锟斤拷菘狻�");
				throw new ElException("锟斤拷菘锟斤拷锟斤拷锟斤拷锟斤拷螅。锟斤拷锟�");
			}
			int sqid = 0;
			if (rs.next()) {
				sqid = rs.getInt(1);
			}
			rs.close();
			ps.close();
			ps = ct
					.prepareStatement("select courseid from exam_room where id = ?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();

			int courseid = 0;
			if (rs.next()) {
				courseid = rs.getInt(1);
			}
			rs.close();
			ps.close();
			if (courseid != 0 && sqid != 0) {
				// 锟叫讹拷锟角凤拷为锟襟定的匡拷锟斤拷
				EroomDao ed = new EroomDaoImpl();
				if (ed.checkExamRoomIsBand(courseid, classid)) {
					// 锟角绑定匡拷锟斤拷
					ps = ct
							.prepareStatement("update study_course set sqiid=? where courseid = ? and userid = ? and classid=?");
					ps.setInt(1, sqid);
					ps.setInt(2, courseid);
					ps.setInt(3, uid);
					ps.setInt(4, classid);
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟诫考锟皆筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * public void deleteQuiz(int uid, int roomid, int epid) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * try { ct = DBConnection.getConnection(); // ps = ct //
	 * .prepareStatement("select courseid from exam_room where id = ?"); //
	 * ps.setInt(1, roomid); // rs = ps.executeQuery(); // if (rs.next()) { //
	 * ps = ct // .prepareStatement("update study_course set sqiid=0 where
	 * userid = // ? and courseid = ?"); // ps.setInt(1, uid); // ps.setInt(2,
	 * rs.getInt(1)); // ps.executeUpdate(); // } ps = ct
	 * .prepareStatement("delete from study_quizinfo where userid = ? and roomid = ?
	 * and epid = ?"); ps.setInt(1, uid); ps.setInt(2, roomid); ps.setInt(3,
	 * epid); ps.executeUpdate(); } catch (Exception e) {
	 * logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e); throw new ElException(e); } finally {
	 * DBConnection.closeConnectInfo(ct, ps, rs); } }
	 */
	// public void deleteQuiz(int uid, int roomid, int epid) throws ElException
	// {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// // ps = ct
	// // .prepareStatement("select courseid from exam_room where id = ?");
	// // ps.setInt(1, roomid);
	// // rs = ps.executeQuery();
	// // if (rs.next()) {
	// // ps = ct
	// // .prepareStatement("update study_course set sqiid=0 where userid =
	// // ? and courseid = ?");
	// // ps.setInt(1, uid);
	// // ps.setInt(2, rs.getInt(1));
	// // ps.executeUpdate();
	// // }
	// int joinway = 0;//锟斤拷询锟斤拷要删锟斤拷锟斤拷锟斤拷锟斤拷锟捷的参加凤拷式
	// ps = ct
	// .prepareStatement("select joinway from study_room where userid = ? and
	// roomid = ?");
	// ps.setInt(1, uid);
	// ps.setInt(2, roomid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// joinway = rs.getInt(1);
	// }
	// rs = ps.executeQuery();
	// //锟斤拷删锟斤拷锟斤拷锟斤拷锟捷革拷锟铰碉拷一锟斤拷锟斤拷锟斤拷锟斤拷录
	// if(joinway == CourseConstants.EXAMROOM_SQFS_SQ){//锟斤拷锟斤拷潜锟斤拷锟斤拷锟斤拷锟斤拷彀∫拷锟揭伙拷锟斤拷录锟斤拷删锟斤拷学员
	// ps = ct
	// .prepareStatement("insert into exam_appliedfor(eroomid,userid,epid)
	// values(?,?,?)");
	// ps.setInt(1, uid);
	// ps.setInt(2, roomid);
	// ps.setInt(3, epid);
	// ps.executeUpdate();
	// }
	//			
	// ps = ct
	// .prepareStatement("delete from study_quizinfo where userid = ? and roomid
	// = ? and epid = ?");
	// ps.setInt(1, uid);
	// ps.setInt(2, roomid);
	// ps.setInt(3, epid);
	// ps.executeUpdate();
	// ps.close();
	// ps = ct
	// .prepareStatement("delete from study_room where roomid=? and userid=?");
	// ps.setInt(1, roomid);
	// ps.setInt(2, uid);
	// ps.executeUpdate();
	//			
	// } catch (Exception e) {
	// logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }
	/**
	 * 删锟斤拷学员锟皆撅拷锟斤拷锟斤拷锟斤拷锟斤拷
	 */
	public void deleteQuiz(int uid, int roomid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int joinway = 0;// 锟斤拷询锟斤拷要删锟斤拷锟斤拷锟斤拷锟斤拷锟捷的参加凤拷式
			ps = ct
					.prepareStatement("select joinway from study_room where userid = ? and roomid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				joinway = rs.getInt(1);
			}
			rs.close();
			ps.close();
			// //锟斤拷删锟斤拷锟斤拷锟斤拷锟捷革拷锟铰碉拷一锟斤拷锟斤拷锟斤拷锟斤拷录
			// if(joinway ==
			// CourseConstants.EXAMROOM_SQFS_SQ){//锟斤拷锟斤拷潜锟斤拷锟斤拷锟斤拷锟斤拷彀∫拷锟揭伙拷锟斤拷录锟斤拷删锟斤拷学员
			// ps = ct.prepareStatement("insert into
			// exam_appliedfor(eroomid,userid,epid) values(?,?,?)");
			// ps.setInt(1, uid);
			// ps.setInt(2, roomid);
			// ps.setInt(3, epid);
			// ps.executeUpdate();
			// ps.close();
			// }
			// 锟斤拷锟轿加凤拷式锟角匡拷锟斤拷锟斤拷锟� 锟斤拷锟斤拷锟斤拷锟斤拷删锟斤拷
			// if (joinway == CourseConstants.EXAMROOM_FPFS_SQ) {
			// 删锟斤拷学员锟皆撅拷
			ps = ct
					.prepareStatement("delete from study_exampaper where userid = ? and roomid = ? and epid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setInt(3, epid);
			ps.executeUpdate();
			ps.close();
			// 锟斤拷询锟斤拷锟斤拷学员锟斤拷锟斤拷没锟斤拷锟皆�?锟斤拷锟矫伙拷芯痛涌锟斤拷锟斤拷锟斤拷瞥锟�
			// ps = ct.prepareStatement("select count(*) from study_exampaper
			// where userid = ? and roomid = ? and epid = ?");
			ps = ct
					.prepareStatement("select count(epid) from study_exampaper where userid = ? and roomid = ? ");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			// ps.setInt(3, epid);
			rs = ps.executeQuery();
			if (rs.next()) {
				// 锟狡筹拷锟斤拷锟斤拷
				if (rs.getInt(1) == 0) {
					rs.close();
					ps.close();
					ps = ct
							.prepareStatement("delete from study_room where roomid=? and userid=?");
					ps.setInt(1, roomid);
					ps.setInt(2, uid);
					ps.executeUpdate();
				}
			}
			rs.close();
			ps.close();
			// 为锟剿憋拷锟斤拷锟斤拷菘锟斤拷啵拷锟斤拷锟缴撅拷锟窖г憋拷锟斤拷
			ps = ct
					.prepareStatement("delete from study_quizinfo where userid = ? and roomid = ? and epid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setInt(3, epid);
			ps.executeUpdate();
			// }
		} catch (Exception e) {
			logger.error("删锟斤拷学员锟皆撅拷锟斤拷锟斤拷锟斤拷锟教筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean hasInQuizPaper(int uid, int roomid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_quizinfo where  userid = ? and roomid = ? and epid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setInt(3, epid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷猿锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷锟�
	 * 
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean hasInQuizPaper(int uid, int roomid, int epid, int classid,
			int mrrid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_quizinfo where  userid = ? and roomid = ? and epid = ? and classid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setInt(3, epid);
			ps.setInt(4, classid);
			// ps.setInt(5, mrrid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷猿锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 锟斤拷取锟矫匡拷锟斤拷锟轿加碉拷锟斤拷
	 * 
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> geteRoomUserByUid(int roomid, int epid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> user = new ArrayList();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select userid from study_quizinfo where roomid = ? and epid = ? and classid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser us = new ELUser();
				us.setId(rs.getInt(1));
				user.add(us);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷猿锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return user;
	}

	public void submitQuizPaper(MyExamPaper myExamPaper) throws ElException {
		System.out.println(myExamPaper.getClassId());
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// myExamPaper.setStatus(3);
			myExamPaper.setStatus(2);
			setQuizPaperStatus(myExamPaper);
			saveQuizPaperPasstime(myExamPaper);
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call sr_setscore(?)");
			ps.setInt(1, myExamPaper.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟皆达拷锟斤拷锟�1
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void setQuizPaperExamCount(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set myexamcount =myexamcount+1 where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟皆达拷锟斤拷锟斤拷0
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void setQuizPaperExamCountO(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set myexamcount =0 where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟矫达拷锟阶刺�
	 */
	public void setQuizPaperStatus(MyExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set status =?,passtime=0 where id = ?");
			ps.setInt(1, examPaper.getStatus());
			ps.setInt(2, examPaper.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void requiz(int uid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set myAnswer ='',passtime=0,status=1,myScore=0,quiztime=quiztime+1 where userid = ? and roomid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public MyExamPaper getMyEpById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper mep = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("SELECT SQI.ID,SQI.USERID,EU.REALNAME,SQI.ROOMID,ER.TITLE,"
							+ " SQI.EPID,SQI.PASSTIME,SQI.STATUS,"
							+ "SQI.MYSCORE,SQI.ENDTIME,SQI.ISPASSED,ER.BEGINTIME,ER.ENDTIME,SQI.JIASHI,SQI.PASSTIME_JS,SQI.PRACTIMES,SQI.PRACSCORE, ER.TYPE,SQI.CLASSID,"
							+ "ER.ISMACBAND,ER.ISIPLIMIT,ER.IPSTART,ER.IPEND,ER.VALID,SQI.MYEXAMCOUNT,ER.EXAMCOUNT,ER.MARKINGMANNER,SQI.BEGINTIME,EP.TITLE EPTITLE,ER.CACHEEPSIZE,er.epqsort ,sr.classid "
							+ "FROM STUDY_QUIZINFO SQI LEFT JOIN ELUSER EU ON SQI.USERID = EU.ID "
							+ "LEFT JOIN EXAM_ROOM ER ON ER.ID = SQI.ROOMID " +
							"LEFT JOIN EXAMPAPER EP ON SQI.EPID=EP.ID "+
							"LEFT JOIN study_room sr ON SQI.roomid=sr.roomid "
							+ "WHERE SQI.ID = ? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				mep.setId(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2), rs.getString(3)));
				ExamRoom er = new ExamRoom(rs.getInt(4), rs.getString(5));
				er.setExamcount(rs.getInt(26));
				er.setMarkingManner(rs.getInt(27));
				mep.setExamRoom(er);
				mep.setExamPaper(new ExamPaper(rs.getInt(6), rs.getString(29)));
				mep.setPassTime(rs.getInt(7));
				mep.setStatus(rs.getInt(8));
				mep.setMyScore(rs.getFloat(9));
				mep.setEndtime(rs.getTimestamp(10));
				mep.setIspassed(rs.getInt(11));
				er.setBegintime(rs.getTimestamp(12));
				er.setEndtime(rs.getTimestamp(13));
				mep.setJiashi(rs.getInt(14));
				mep.setPassTime_js(rs.getInt(15));
				mep.setPractimes(rs.getInt(16));
				mep.setPracscore(rs.getFloat(17));
				er.setType(rs.getInt(18));
				mep.setClassId(rs.getInt(19));
				er.setIsMacBand(rs.getInt(20));
				er.setIsIpLimit(rs.getInt(21));
				er.setIpStart(rs.getString(22));
				er.setIpEnd(rs.getString(23));
				er.setValid(rs.getInt(24));
				mep.setMyexamcount(rs.getInt(25));
				mep.setBegintime(rs.getTimestamp(28));
				er.setCacheepsize(rs.getInt(30));
				er.setEpqsort(rs.getInt(31));
				mep.setClassId(rs.getInt(32));
				// 锟斤拷前锟斤拷习锟斤拷锟杰ｏ拷锟窖凤拷锟斤拷
				/*
				 * PreparedStatement ps1 = ct .prepareStatement("select
				 * erp.pracscore,erp.practimes from exam_reps erp where erp.epid = ?
				 * and erp.roomid= ?"); ps1.setInt(1,
				 * mep.getExamPaper().getId()); ps1.setInt(2, er.getId());
				 * ResultSet rs1 = ps1.executeQuery(); if (rs1.next()) {
				 * mep.getExamPaper().setPracscore(rs1.getInt(1));
				 * mep.getExamPaper().setPractimes(rs1.getInt(2)); }
				 * rs1.close(); ps1.close();
				 */
			}
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mep;
	}

	public Float getMyScore(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Float MyScore = 0.0f;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sqi.myScore from study_quizinfo sqi left join ELUSER eu on sqi.userid = eu.id "
							+ "left join exam_room er on er.id = sqi.roomid "
							+ "where sqi.id = ? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				MyScore = rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return MyScore;
	}

	/**
	 * 锟斤拷取学员mac锟斤拷址
	 */
	public String getStudyMacAdr(int userid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select macAddress from study_room where userid=? and roomid=?");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			// ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员mac锟斤拷址锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return "no";
	}

	// public void updateStudyExamTime(MyExamPaper myExamPaper) throws
	// ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement("update study_quizinfo set examyestime=? where
	// id=?");
	// ps.setTimestamp(1, myExamPaper.getExamyestime());
	// ps.setInt(2, myExamPaper.getId());
	// ps.executeUpdate();
	// } catch (Exception e) {
	// logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷?", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }

	/**
	 * 锟斤拷锟斤拷欠锟斤拷锟斤拷锟斤拷锟窖г憋拷诟玫锟斤拷锟斤拷峡锟斤拷锟�
	 * 
	 * @param myExamPaper
	 * @param userid
	 * @param macAddress
	 * @return
	 * @throws ElException
	 */
	public Timestamp getMyComputerYesTime(MyExamPaper myExamPaper, int userid,
			String macAddress) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sqi.examyestime from (select * from study_room where userid!=? and roomid=? and macaddress=?) sr left join study_quizinfo sqi on sr.roomid=sqi.roomid where sqi.userid=sr.userid and sqi.epid=? and sqi.examyestime is not null order by sqi.examyestime desc ");
			ps.setInt(1, userid);
			ps.setInt(2, myExamPaper.getExamRoom().getId());
			ps.setString(3, macAddress);
			ps.setInt(4, myExamPaper.getExamPaper().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getTimestamp(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
	}

	public void updateStudyMacAddr(int userid, int roomid, String macAddr)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_room set macAddress=? where userid=? and roomid=?");
			ps.setString(1, macAddr);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			// ps.setInt(4, classid);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷mac锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员ip锟斤拷锟斤拷菘锟�
	 * 
	 * @param userid
	 * @param roomid
	 * @param classid
	 * @param ipAddr
	 * @throws ElException
	 */
	public void updateStudyIpAddr(int userid, int roomid, String ipAddr)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_room set ipAddress=? where userid=? and roomid=?");
			ps.setString(1, ipAddr);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			// ps.setInt(4, classid);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷ip锟斤拷址锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public MyExamPaper getMySimEpByUandR(int uid, int epid, int cid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper mep = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ssi.id,ssi.userid,ssi.epid,ssi.myAnswer,ssi.passTime,ssi.status,ssi.myScore,ssi.endtime,"
							+ "eu.realname "
							+ "from STUDENT_SIMINFO ssi left join ELUSER eu on eu.id = ssi.userid where epid = ? and userid = ? and courseid = ?");
			ps.setInt(1, epid);
			ps.setInt(2, uid);
			ps.setInt(3, cid);
			rs = ps.executeQuery();
			if (rs.next()) {
				mep.setId(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2), rs.getString(9)));
				mep.setExamPaper(new ExamPaper(rs.getInt(3)));
				// mep.setMyAnswer(rs.getString(4));
				mep.setPassTime(rs.getInt(5));
				mep.setStatus(rs.getInt(6));
				mep.setMyScore(rs.getFloat(7));
				mep.setEndtime(rs.getTimestamp(8));
			}
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mep;
	}

	public boolean checkQuizPaper(int uid, int roomid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_QPAPER_CHECK));
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setInt(3, status);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷猿锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 锟斤拷锟矫凤拷值锟斤拷锟皆撅拷亩锟接︼拷锟侥匡拷锟�
	 * 
	 * @param epid
	 *            锟斤拷锟斤拷锟斤拷id
	 * @param qid
	 *            锟斤拷锟斤拷id
	 * @param socre
	 *            锟斤拷值
	 * @throws ElException
	 */
	public void setStudyQuestionScore(int epid, int qid, int blockid,
			float socre) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_questions set myscore=? where sqid=? and qid=? and blockid=?");
			ps.setFloat(1, socre);
			ps.setInt(2, epid);
			ps.setInt(3, qid);
			ps.setInt(4, blockid);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("锟斤拷锟矫凤拷值锟斤拷锟皆撅拷亩锟接︼拷锟侥匡拷铣锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤到锟皆撅拷亩锟接︼拷锟侥匡拷锟�
	 * 
	 * @param epid
	 *            锟斤拷锟斤拷锟斤拷id
	 * @param qid
	 *            锟斤拷锟斤拷id
	 * @param socre
	 *            锟斤拷值
	 * @throws ElException
	 */
	public void setStudyQuestionPiyu(int epid, int qid, int blockid,
			String piyu) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_questions set piyu=? where sqid=? and qid=? and blockid=?");
			ps.setString(1, piyu);
			ps.setInt(2, epid);
			ps.setInt(3, qid);
			ps.setInt(4, blockid);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("锟斤拷锟矫凤拷值锟斤拷锟皆撅拷亩锟接︼拷锟侥匡拷铣锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
	/**
	 * 锟斤拷锟斤拷锟皆撅拷拇锟斤拷锟斤拷锟杰凤拷
	 * 
	 * @param sqid
	 *            锟皆撅拷id
	 * @param blockid
	 *            锟斤拷锟絠d
	 * @param socre
	 *            锟斤拷值
	 * @throws ElException
	 */
	public void setStudyBlocksScore(int sqid, int blockid, float socre)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_blocks set myscore=? where sqid=? and blockid=? ");
			ps.setFloat(1, socre);
			ps.setInt(2, sqid);
			ps.setInt(3, blockid);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟皆撅拷拇锟斤拷锟斤拷锟杰分筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// public void setFinalScore(int id, float score) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// float erpassgrade = 0;
	// float passgrade = 0;
	// float erscore = 0;
	// ps = ct
	// .prepareStatement(ElQuerySql
	// .getSQL(StudyConstants.STUDY_QPAPER_FINALSCORE_SET_C_PASSGRADE));
	// ps.setInt(1, id);// 锟斤拷锟斤拷锟斤拷锟斤拷值锟斤拷学锟街★拷
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// erpassgrade = rs.getFloat(1);
	// erscore = rs.getFloat(2);
	// }
	// float epscore = 0;
	// ps = ct
	// .prepareStatement(ElQuerySql
	// .getSQL(StudyConstants.STUDY_QPAPER_FINALSCORE_SET_EP_SOCORE));
	// ps.setInt(1, id);// 锟皆撅拷拇锟斤拷郑锟斤拷芊锟�
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// epscore = rs.getFloat(1);
	// passgrade = rs.getFloat(2);
	// }
	// rs.close();
	// boolean cpassed = false;
	// if (epscore == 0)
	// epscore = 1;
	// if (score * 100 / epscore >= passgrade)
	// cpassed = true;
	// else
	// erscore = 0;
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(StudyConstants.STUDY_QPAPER_FINALSCORE_SET));
	// ps.setFloat(1, score);// 锟斤拷锟斤拷锟皆撅拷梅郑锟酵拷锟�
	// ps.setInt(2, cpassed ? 1 : 0);
	// // ps.setFloat(3, erscore);
	// ps.setInt(3, id);
	// ps.executeUpdate();
	// // TODO 锟皆撅拷锟斤拷锟斤拷
	//
	// // 锟斤拷锟斤拷
	// ps = ct
	// .prepareStatement("select roomid,userid from study_quizinfo where id =
	// ?");
	// int userid = 0;
	// int roomid = 0;
	// ps.setInt(1, id);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// roomid = rs.getInt(1);
	// userid = rs.getInt(2);
	// }
	// ps = ct
	// .prepareStatement("select sum(ep.ep_tscore) from exam_reps reps left join
	// exampaper ep on ep.id = reps.epid where reps.roomid =?");
	// ps.setInt(1, roomid);
	// rs = ps.executeQuery();
	// float er_tscore = 0f;
	// if (rs.next()) {
	// er_tscore = rs.getFloat(1);
	// }
	// if (er_tscore == 0)
	// er_tscore = 1;
	//
	// ps = ct
	// .prepareStatement(" select sum(myscore) from study_quizinfo where roomid
	// = ? and userid = ?");
	// ps.setInt(1, roomid);
	// ps.setInt(2, userid);
	// rs = ps.executeQuery();
	// epscore = 0;
	// if (rs.next())
	// epscore = rs.getFloat(1);
	// ps = ct
	// .prepareStatement("update study_room set myscore = ?,status =3,ispassed =
	// ? where roomid = ? and userid = ?");
	// ps.setFloat(1, epscore);
	// if (epscore * 100 / er_tscore >= erpassgrade) {
	// cpassed = true;
	// PreparedStatement ps1 = ct.prepareStatement("select ispassed from
	// study_quizinfo where roomid = ? and userid = ?");
	// ps1.setInt(1, roomid);
	// ps1.setInt(2, userid);
	// ResultSet rs1 = ps1.executeQuery();
	// while (rs1.next()) {
	// if(rs1.getInt(1)==0)
	// {
	// cpassed = false;
	// break;
	// }
	// }
	// rs1.close();
	// ps1.close();
	// } else
	// cpassed = false;
	//
	// ps.setInt(2, cpassed ? 1 : 0);
	// ps.setInt(3, roomid);
	// ps.setInt(4, userid);
	// ps.executeUpdate();
	// ps = ct.prepareStatement("call sr_seteroom(?,?)");
	// ps.setInt(1, userid);
	// ps.setInt(2, roomid);
	// ps.executeUpdate();
	// } catch (Exception e) {
	// logger.error("锟结交锟斤拷模锟�", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }

	public void setSimFinalScore(int courseid, int epid, int userid, int score)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_SPAPER_FINALSCORE_SET));
			ps.setFloat(1, score);
			ps.setInt(2, userid);
			ps.setInt(3, courseid);
			ps.setInt(4, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟结交锟斤拷模锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyExamPaper> listSimResult(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_SPAPER_RESULT));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2)));
				mep.setExamPaper(new ExamPaper(rs.getInt(3), rs.getString(10)));
				mep.setCourse(new Course(rs.getInt(4), rs.getString(8)));
				// mep.getCourse().setPassgrade(rs.getInt(9));
				// TODO 锟轿筹拷通锟斤拷锟斤拷锟斤拷float????
				mep.getCourse().setCreater(new ELUser(1, rs.getString(9)));
				mep.setStatus(rs.getInt(5));
				mep.setMyScore(rs.getInt(6));
				mep.setEndtime(rs.getTimestamp(7));
				int eptscore = rs.getInt(11);
				float passgrade = 60.0f;// mep.getCourse().getPassgrade();
				float myscore = (float) mep.getMyScore();
				if (eptscore > 0)
					mep.setIspassed((myscore / eptscore) * 100 > passgrade ? 1
							: 0);
				MyCourse myCourse = new MyCourse();
				myCourse.setStatus(rs.getInt(12));
				mep.setMyCourse(myCourse);
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟揭碉拷模锟解考锟皆成硷拷锟斤拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public MyExamPaper getMyEpByCid(int cid, int uid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper mep = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sqi.status,sqi.myScore,sqi.endtime,er.title from ROOM_ASSIGN ra "
							+ "left join study_quizinfo sqi on ra.roomid = sqi.roomid "
							+ "left join EXAM_ROOM er on ra.roomid = er.id "
							+ "where ra.courseid = ? and sqi.userid = ?");
			ps.setInt(1, cid);
			ps.setInt(2, uid);
			rs = ps.executeQuery();
			if (rs.next()) {
				mep.setStatus(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				// mep.setEndtime(rs.getDate(3));
				mep.setExamRoom(new ExamRoom(0, rs.getString(4)));
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷纬坛杉锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mep;
	}

	public List<MyExamPaper> listmyQuizResult(int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_RESULT));
			ps = ct
					.prepareStatement("select er.id,er.title,sqi.epid,sqi.status,sqi.myScore,sqi.begintime,sqi.endtime,ep.ep_tscore,c.passgrade from exam_room er,exampaper ep,course c,study_quizinfo sqi where er.courseid =? and c.id=er.courseid and sqi.epid=ep.id  and sqi.userid= ? and er.id=sqi.roomid");
			ps.setInt(1, courseid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			// er.id,er.title,sqi.epid,sqi.status,sqi.myScore,sqi.begintime,sqi.endtime
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setExamRoom(new ExamRoom(rs.getInt(1), rs.getString(2)));
				mep.setExamPaper(new ExamPaper(rs.getInt(3)));
				mep.setStatus(rs.getInt(4));
				mep.setMyScore(rs.getInt(5));
				mep.setBegintime(rs.getTimestamp(6));
				if (mep.getStatus() != StudyConstants.STUDY_QPAPER_STATUS_TESTING)
					mep.setEndtime(rs.getTimestamp(7));
				float eptscore = rs.getFloat(8);
				float passgrade = rs.getFloat(9);
				float myscore = (float) mep.getMyScore();
				if (eptscore > 0)
					mep.setIspassed((myscore / eptscore) * 100 > passgrade ? 1
							: 0);
				// mep.setMySort(rs.getInt(10) + 1);
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟揭的匡拷锟皆成硷拷锟斤拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public boolean checkSimPaper(int uid, int epid, int status, int cid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from STUDENT_SIMINFO where  userid = ? "
							+ "and epid = ? and status = ? and courseid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, epid);
			ps.setInt(3, status);
			ps.setInt(4, cid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("锟斤拷锟侥ｏ拷饪硷拷锟阶刺拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void intoSimPaper(int uid, int epid, int cid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into STUDENT_SIMINFO(userid,epid,status,courseid) values(? ,?,?,?)");
			ps.setInt(1, uid);
			ps.setInt(2, epid);
			ps.setInt(3, 1);
			ps.setInt(4, cid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟诫考锟皆筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void saveSimPaper(MyExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update STUDENT_SIMINFO set myAnswer = ?,passTime = ? where "
							+ " userid = ? and epid = ?");
			// ps.setString(1, examPaper.getMyAnswer());
			ps.setInt(2, examPaper.getPassTime());
			ps.setInt(3, examPaper.getTester().getId());
			ps.setInt(4, examPaper.getExamPaper().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷模锟斤拷锟斤拷锟皆达拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void submitSimPaper(MyExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update STUDENT_SIMINFO set myAnswer = ?,status=2 where "
							+ "epid = ? and userid = ? and courseid =?");
			// ps.setString(1, examPaper.getMyAnswer());
			ps.setInt(2, examPaper.getExamPaper().getId());
			ps.setInt(3, examPaper.getTester().getId());
			ps.setInt(4, examPaper.getCourse().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟侥ｏ拷饪硷拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean hasInSimPaper(int uid, int epid, int cid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from STUDENT_SIMINFO where  userid = ? and epid = ? and courseid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, epid);
			ps.setInt(3, cid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷猿锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void resimpaper(int uid, int courseid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update student_siminfo set myAnswer ='',passtime=0,status=1,myScore=0 where userid = ? and courseid = ? and epid = ?");
			ps.setInt(1, uid);
			ps.setInt(2, courseid);
			ps.setInt(3, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<SimexamPaper> listMySimEp(int course, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<SimexamPaper> simEps = new ArrayList<SimexamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_SIMPAPER_MYLIST));
			ps.setInt(1, course);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				SimexamPaper sep = new SimexamPaper(rs.getInt(1));
				sep.setExamPaper(new ExamPaper(rs.getInt(2)));
				sep.setCourse(new Course(rs.getInt(3)));
				sep.setBegintime(rs.getTimestamp(4));
				sep.setEndtime(rs.getTimestamp(5));
				simEps.add(sep);
			}
		} catch (Exception e) {
			logger.error("锟揭碉拷模锟斤拷锟叫�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return simEps;
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
			logger.error("锟斤拷锟斤拷欠锟斤拷锟窖э拷陆锟斤拷锟斤拷锟斤拷", e);
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
			logger.error("锟斤拷前锟铰斤拷学习锟斤拷锟斤拷锟�", e);
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
			ps = ct
					.prepareStatement("select top 1 sc.cpid from study_cpage sc,course_page cp where sc.cpid= cp.id  and sc.userid = ? and cp.courseid= ? order by sc.endtime desc ");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
		} catch (Exception e) {
			logger.error("锟斤拷前锟铰斤拷学习锟斤拷锟斤拷锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}

	public boolean checkQpaperIsFinish(int roomid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// TODO 锟斤拷锟斤拷锟斤拷courseid应锟斤拷没锟斤拷锟斤拷锟斤拷
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_QPAPER_FINISH_CHECK));
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				float myscore = rs.getFloat(1);
				float epscore = rs.getFloat(2);
				// 选锟斤拷纬锟酵拷锟斤拷锟�
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(StudyConstants.STUDY_QPAPER_COURSE_SCORE_BYRID));
				ps.setInt(1, roomid);
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
			logger.error("锟斤拷锟轿程匡拷锟斤拷锟角凤拷通锟斤拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 锟斤拷锟斤拷锟较�
	 */
	public boolean checkPpaperIsFinish(int ppid, int userid, int classid)
			throws ElException {
		// TODO 锟斤拷习锟角凤拷通锟斤拷
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_PPAPER_QUERY_BYPPIDANDUID));
			ps.setInt(1, ppid);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next())
				b = true;

		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷锟窖э拷纬锟斤拷锟斤拷锟斤拷", e);
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
			logger.error("锟斤拷始锟斤拷习锟斤拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷锟�
	 */
	public List<MyExamPaper> listMyRecentQuiz(int userid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_QPAPER_RECENT_LIST));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mc = new MyExamPaper(rs.getInt(1));
				ExamRoom er = new ExamRoom(rs.getInt(2), rs.getString(3));
				er.setLocation(rs.getString(4));
				er.setBegintime(rs.getTimestamp(5));
				er.setEndtime(rs.getTimestamp(6));
				mc.setExamRoom(er);
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	// public List<MyExamPaper> listMyQuiz(int userid, int pageNow, int
	// pageSize)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
	// try {
	// // String sql="select * from (select t.*, rownum rn from ( select
	// // er.id erid,er.title,er.location, er.begintime,er.endtime
	// // erendtime,c.id cid,c.name,c.status cstatus,sqi.id
	// // sqid,sqi.myscore,sqi.status sqistatus,sqi.endtime
	// // sqiendtime,sqi.ispassed,el.realname from (select * from exam_room
	// // where iscommon = 0) er join eluser el on er.createrid=el.id left
	// // join study_quizinfo sqi on er.id = sqi.roomid left join course c
	// // on c.id = er.courseid left join study_course ca on
	// // ca.courseid=c.id and sqi.userid=ca.userid where sqi.userid = ?
	// // order by er.begintime desc )t where rownum <= ? ) where rn>=?";
	// String sql = "select * from (select t.*, rownum rn from ( select er.id
	// erid,er.title,er.location, er.begintime,er.endtime erendtime,c.id
	// cid,c.name,c.status cstatus,sqi.id sqid,sqi.myscore,sqi.status
	// sqistatus,sqi.endtime sqiendtime,sqi.ispassed,c.creater from (select *
	// from exam_room where iscommon = 0) er left join study_quizinfo sqi on
	// er.id = sqi.roomid left join course c on c.id = er.courseid left join
	// study_course ca on ca.courseid=c.id and sqi.userid=ca.userid where
	// sqi.userid = ? order by er.begintime desc )t where rownum <= ? ) where
	// rn>=?";
	// ct = DBConnection.getConnection();
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(StudyConstants.STUDY_QPAPER_LIST));
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, userid);
	// ps.setInt(2, pageNow);
	// ps.setInt(3, pageSize);
	// rs = ps.executeQuery();
	// UserDao ud = new UserDaoImpl();
	// ELUser creater = null;
	// Course course = null;
	// while (rs.next()) {
	// ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
	// er.setLocation(rs.getString(3));
	// er.setBegintime(rs.getTimestamp(4));
	// er.setEndtime(rs.getTimestamp(5));
	//
	// MyExamPaper mc = new MyExamPaper();
	// mc.setExamRoom(er);
	// course = new Course();
	// course.setId(rs.getInt(6));
	// course.setName(rs.getString(7));
	// // mc.setCourse(new Course(rs.getInt(6), rs.getString(7)));
	// MyCourse myCourse = new MyCourse();
	// myCourse.setStatus(rs.getInt(8));
	// mc.setMyCourse(myCourse);
	// mc.setId(rs.getInt(9));
	// mc.setMyScore(rs.getFloat(10));
	// mc.setStatus(rs.getInt(11));
	// mc.setEndtime(rs.getTimestamp(12));
	// mc.setIspassed(rs.getInt(13));
	// creater = ud.getUserById(rs.getInt("creater"));
	// course.setCreater(creater);
	// mc.setCourse(course);
	// myBxc.add(mc);
	//
	// }
	// } catch (Exception e) {
	// logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return myBxc;
	// }

	/**
	 * 锟斤拷示My锟斤拷业锟斤拷锟斤拷锟斤拷息
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	// public List<MyExamPaper> listMyQuiz2(int userid, int pageNow, int
	// pageSize)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
	// try {
	// // String sql="select * from (select t.*, rownum rn from ( select
	// // er.id erid,er.title,er.location, er.begintime,er.endtime
	// // erendtime,c.id cid,c.name,c.status cstatus,sqi.id
	// // sqid,sqi.myscore,sqi.status sqistatus,sqi.endtime
	// // sqiendtime,sqi.ispassed,el.realname from (select * from exam_room
	// // where iscommon = 0) er join eluser el on er.createrid=el.id left
	// // join study_quizinfo sqi on er.id = sqi.roomid left join course c
	// // on c.id = er.courseid left join study_course ca on
	// // ca.courseid=c.id and sqi.userid=ca.userid where sqi.userid = ?
	// // order by er.begintime desc )t where rownum <= ? ) where rn>=?";
	// // String sql="select * from (select t.*, rownum rn from (select
	// // er.id,er.title,c.name,c.creater,sc.starttime,sc.finishtime from
	// // exam_room er inner join course c on er.courseid=c.id inner join
	// // study_course sc on c.id=sc.courseid where er.id=sc.roomid)t where
	// // rownum <= ? ) where rn>=?";
	// // String sql="select * from (select t.*, rownum rn from (select
	// // er.id erid,er.title,er.location, er.begintime,er.endtime
	// // erendtime,c.id cid,c.name,c.status cstatus,sqi.id
	// // sqid,sqi.myscore,sqi.status sqistatus,sqi.endtime
	// // sqiendtime,sqi.ispassed,c.creater,ec.id classid,ec.name className
	// // from (select * from exam_room where iscommon = 0) er inner join
	// // study_quizinfo sqi on er.id = sqi.roomid inner join course c on
	// // c.id = er.courseid left join elclass ec on ec.id=sqi.classid
	// // where sqi.userid = ? order by er.begintime desc )t where rownum
	// // <= ? ) where rn>=?";
	// String sql = "select * from (select t.*, rownum rn from (select er.id
	// erid,er.title,er.location, er.begintime,er.endtime erendtime,c.id
	// cid,c.name,c.status cstatus,sqi.id sqid,sqi.myscore,sqi.status
	// sqistatus,sqi.endtime sqiendtime,sqi.ispassed,c.creater,ec.id
	// classid,ec.name className from (select * from exam_room where iscommon =
	// 0 and isband=1) er inner join study_quizinfo sqi on er.id = sqi.roomid
	// inner join course c on c.id = er.courseid left join elclass ec on
	// ec.id=sqi.classid where sqi.userid = ? and er.bandclassid=sqi.classid
	// order by er.begintime desc )t where rownum <= ? ) where rn>=?";
	// ct = DBConnection.getConnection();
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(StudyConstants.STUDY_QPAPER_LIST));
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, userid);
	// ps.setInt(2, pageNow);
	// ps.setInt(3, pageSize);
	// rs = ps.executeQuery();
	// UserDao ud = new UserDaoImpl();
	// ELUser creater = null;
	// Course course = null;
	// while (rs.next()) {
	// ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
	// er.setLocation(rs.getString(3));
	// er.setBegintime(rs.getTimestamp(4));
	// er.setEndtime(rs.getTimestamp(5));
	//
	// MyExamPaper mc = new MyExamPaper();
	// mc.setExamRoom(er);
	// course = new Course();
	// course.setId(rs.getInt(6));
	// course.setName(rs.getString(7));
	// course.setClassid(rs.getInt("classid"));
	// course.setClassName(rs.getString("className"));
	// // mc.setCourse(new Course(rs.getInt(6), rs.getString(7)));
	// MyCourse myCourse = new MyCourse();
	// myCourse.setStatus(rs.getInt(8));
	// mc.setMyCourse(myCourse);
	// mc.setId(rs.getInt(9));
	// mc.setMyScore(rs.getFloat(10));
	// mc.setStatus(rs.getInt(11));
	// mc.setEndtime(rs.getTimestamp(12));
	// mc.setIspassed(rs.getInt(13));
	// creater = ud.getUserById(rs.getInt("creater"));
	// course.setCreater(creater);
	// mc.setCourse(course);
	// mc.setBindingId(getbandingeroom(mc));
	// myBxc.add(mc);
	//
	// }
	// } catch (Exception e) {
	// logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return myBxc;
	// }
	/**
	 * 锟斤拷示My锟斤拷业锟斤拷锟斤拷锟斤拷息(锟斤拷锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷锟较�)
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listMyQuiz3(int userid, int type, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
		String sql = "";
		try {
			// String sql="select * from (select t.*, rownum rn from (select
			// distinct er.id erid, er.title,er.location,
			// er.begintime,er.endtime erendtime,c.id cid,c.name,c.status
			// cstatus,c.creater,er.bandclassid bandclassid,ec.name className
			// from (select * from exam_room where iscommon = 0 and isband=1) er
			// inner join course c on c.id = er.courseid inner join (select *
			// from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id
			// left join elclass ec on er.bandclassid=ec.id order by
			// er.begintime desc)t where rownum <= ? ) where rn>=?";
			if (type == 1) {
				sql = "select * from (select t.*, rownum rn from ("
						+ "select distinct er.id erid, er.title,er.location, er.begintime,er.endtime erendtime,"
						+ "c.id cid,c.name,c.status cstatus,c.creater,er.bandclassid bandclassid,ec.name className,er.type from "
						+ "(select * from exam_room where iscommon=1 and  valid != 9 and type = 1 and uvalid = 1) er inner join course c on c.id = er.courseid "
						+ "inner join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id left join elclass ec on er.bandclassid=ec.id "
						+ "where sqi.classid=er.bandclassid order by er.begintime desc"
						+ ")t where rownum <= ? ) where rn>=?";
			} else {

				sql = "select * from (select t.*, rownum rn from ("
						+ "select distinct er.id erid, er.title,er.location, er.begintime,er.endtime erendtime,"
						+ "c.id cid,c.name,c.status cstatus,c.creater,er.bandclassid bandclassid,ec.name className,er.type from "
						+ "(select * from exam_room where iscommon=1 and isNormal = 1 and  valid != 9 and type != 1) er inner join course c on c.id = er.courseid "
						+ "inner join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id left join elclass ec on er.bandclassid=ec.id "
						+ "where sqi.classid=er.bandclassid order by er.begintime desc"
						+ ")t where rownum <= ? ) where rn>=?";
			}
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_LIST));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			UserDao ud = new UserDaoImpl();
			ELUser creater = null;
			Course course = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setType(rs.getInt(13));

				MyExamPaper mc = new MyExamPaper();
				mc.setExamRoom(er);
				course = new Course();
				course.setId(rs.getInt(6));
				course.setName(rs.getString(7));
				course.setClassid(rs.getInt("bandclassid"));
				course.setClassName(rs.getString("className"));
				// mc.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				// MyCourse myCourse = new MyCourse();//锟剿讹拷锟斤拷锟斤拷锟斤拷锟斤拷没锟矫碉拷
				// myCourse.setStatus(rs.getInt(8));
				// mc.setMyCourse(myCourse);
				// mc.setId(rs.getInt(9));
				// mc.setMyScore(rs.getFloat(10));
				// mc.setStatus(rs.getInt(11));
				// mc.setEndtime(rs.getTimestamp(12));
				// mc.setIspassed(rs.getInt(13));
				creater = ud.getUserById(rs.getInt("creater"));
				course.setCreater(creater);
				mc.setCourse(course);
				mc.setBindingId(getbandingeroom(mc));
				myBxc.add(mc);

			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	// /**
	// * 锟斤拷示My锟斤拷业锟斤拷锟斤拷锟斤拷息(锟斤拷锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷锟较�)
	// *
	// * @param userid
	// * @param pageNow
	// * @param pageSize
	// * @return
	// * @throws ElException
	// */
	// public List<MyExamPaper> listMyQuiz4(int userid, int type, int pageNow,
	// int pageSize) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
	// String sql = "";
	// try {
	// // String sql="select * from (select t.*, rownum rn from (select
	// // distinct er.id erid, er.title,er.location,
	// // er.begintime,er.endtime erendtime,c.id cid,c.name,c.status
	// // cstatus,c.creater,er.bandclassid bandclassid,ec.name className
	// // from (select * from exam_room where iscommon = 0 and isband=1) er
	// // inner join course c on c.id = er.courseid inner join (select *
	// // from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id
	// // left join elclass ec on er.bandclassid=ec.id order by
	// // er.begintime desc)t where rownum <= ? ) where rn>=?";
	// // if (type == 1) {
	// // sql = "select * from (select t.*, rownum rn from ("
	// // + "select distinct er.id erid, er.title,er.location,
	// er.begintime,er.endtime erendtime,"
	// // + "c.id cid,c.name,c.status cstatus,c.creater,er.bandclassid
	// bandclassid,ec.name className,er.type from "
	// // + "(select * from exam_room where classid=0 and valid != 9 and type =
	// 1 and uvalid = 1) er inner join course c on c.id = er.courseid "
	// // + "inner join (select * from study_quizinfo where userid = ?) sqi on
	// sqi.roomid =er.id left join elclass ec on er.bandclassid=ec.id "
	// // + "where sqi.classid=er.bandclassid order by er.begintime desc"
	// // + ")t where rownum <= ? ) where rn>=?";
	// // } else {
	//
	// // sql = "select * from (select t.*, rownum rn from ("
	// // + "select distinct er.id erid, er.title,er.location,
	// er.begintime,er.endtime erendtime,"
	// // + "c.id cid,c.name,c.status cstatus,c.creater,er.bandclassid
	// bandclassid,ec.name className,er.type from "
	// // + "(select * from exam_room where classid=0 and isNormal = 1 and valid
	// != 9 and type != 1) er inner join course c on c.id = er.courseid "
	// // + "inner join (select * from study_exampaper where userid = ?) sqi on
	// sqi.roomid =er.id left join elclass ec on er.bandclassid=ec.id "
	// // + "where sqi.classid=er.bandclassid order by er.begintime desc"
	// // + ")t where rownum <= ? ) where rn>=?";
	// sql = "select * from (select t.*, rownum rn from ("
	// + "select er.id erid, er.title,er.location, er.begintime,er.endtime
	// erendtime,c.id cid,c.name,c.status cstatus,c.creater,er.bandclassid
	// bandclassid,ec.name className,sr.myscore,sr.ispassed,count(sep.epid) " +
	// " from (select * from exam_room where classid=0 and valid != 9) er " +
	// " inner join course c on c.id = er.courseid " +
	// " inner join (select epid,roomid from study_exampaper where userid = ?
	// and classid=0) sep on sep.roomid =er.id " +
	// " left join elclass ec on er.bandclassid=ec.id " +
	// " inner join study_room sr on er.id=sr.roomid and userid=? " +
	// " group by er.id, er.title,er.location,
	// er.begintime,er.endtime,c.id,c.name,c.status,c.creater,er.bandclassid,ec.name,sr.myscore,sr.ispassed
	// order by er.begintime desc"
	// + ")t where rownum <= ? ) where rn>=?";
	// // }
	// ct = DBConnection.getConnection();
	// // ps = ct.prepareStatement(ElQuerySql
	// // .getSQL(StudyConstants.STUDY_QPAPER_LIST));
	// ps = ct.prepareStatement(sql);
	// ps.setInt(1, userid);
	// ps.setInt(2, userid);
	// ps.setInt(3, pageNow);
	// ps.setInt(4, pageSize);
	// rs = ps.executeQuery();
	// UserDao ud = new UserDaoImpl();
	// ELUser creater = null;
	// Course course = null;
	// while (rs.next()) {
	// ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
	// er.setLocation(rs.getString(3));
	// er.setBegintime(rs.getTimestamp(4));
	// er.setEndtime(rs.getTimestamp(5));
	// // er.setType(rs.getInt(13));
	// MyExamPaper mc = new MyExamPaper();
	// mc.setExamRoom(er);
	// course = new Course();
	// course.setId(rs.getInt(6));
	// course.setName(rs.getString(7));
	// course.setClassid(rs.getInt("bandclassid"));
	// course.setClassName(rs.getString("className"));
	// // mc.setCourse(new Course(rs.getInt(6), rs.getString(7)));
	// // MyCourse myCourse = new MyCourse();//锟剿讹拷锟斤拷锟斤拷锟斤拷锟斤拷没锟矫碉拷
	// // myCourse.setStatus(rs.getInt(8));
	// // mc.setMyCourse(myCourse);
	// // mc.setId(rs.getInt(9));
	// // mc.setMyScore(rs.getFloat(10));
	// // mc.setStatus(rs.getInt(11));
	// // mc.setEndtime(rs.getTimestamp(12));
	// // mc.setIspassed(rs.getInt(13));
	// creater = ud.getUserById(rs.getInt("creater"));
	// course.setCreater(creater);
	// mc.setCourse(course);
	// //mc.setBindingId(getbandingeroom(mc));
	// myBxc.add(mc);
	// }
	// } catch (Exception e) {
	// logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return myBxc;
	// }

	/**
	 * 锟斤拷示My锟斤拷业锟斤拷锟斤拷锟斤拷息(锟斤拷锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷锟较�)
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listMyQuiz(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> myBxc = new ArrayList<MyRoom>();
		try {
			String sql = "select * from (select t.*, rownum rn from ("
					+ "select er.id erid, er.title,er.location, er.begintime,er.endtime erendtime,c.id cid,c.name,c.status cstatus,c.creater,er.bandclassid bandclassid,ec.name className,sr.myscore,sr.ispassed,count(sep.epid),er.mainimg  "
					+ " from (select * from exam_room where classid=0  and  valid != 9) er "
					+ " inner join course c on c.id = er.courseid "
					+ " inner join (select epid,roomid from study_exampaper where userid = ? and classid=0) sep on sep.roomid =er.id "
					+ " left join elclass ec on er.bandclassid=ec.id "
					+ " inner join study_room sr on er.id=sr.roomid and userid=? "
					+ " group by er.id, er.title,er.location, er.begintime,er.endtime,c.id,c.name,c.status,c.creater,er.bandclassid,ec.name,sr.myscore,sr.ispassed,er.mainimg order by er.begintime desc"
					+ ")t where rownum <= ? ) where rn>=?";
			// }
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			UserDao ud = new UserDaoImpl();
			ELUser creater = null;
			Course course = null;
			MyRoom mr = null;
			while (rs.next()) {
				mr = new MyRoom();
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setMainimg(rs.getString("mainimg"));
				course = new Course();
				course.setId(rs.getInt(6));
				course.setName(rs.getString(7));
				course.setClassid(rs.getInt("bandclassid"));
				course.setClassName(rs.getString("className"));
				creater = ud.getUserById(rs.getInt("creater"));
				course.setCreater(creater);
				er.setCourse(course);
				mr.setExamroom(er);
				mr.setMyScore(rs.getFloat(12));
				mr.setIspassed(rs.getInt(13));
				mr.setEpsize(rs.getInt(14));
				myBxc.add(mr);
			}
		} catch (Exception e) {
			logger.error("锟斤拷示My锟斤拷业锟斤拷锟斤拷锟斤拷息(锟斤拷锟斤拷示锟斤拷锟斤拷锟斤拷锟斤拷锟较�)锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyExamPaper> listMyQuiz3(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
		String sql = "";
		try {
			// String sql="select * from (select t.*, rownum rn from (select
			// distinct er.id erid, er.title,er.location,
			// er.begintime,er.endtime erendtime,c.id cid,c.name,c.status
			// cstatus,c.creater,er.bandclassid bandclassid,ec.name className
			// from (select * from exam_room where iscommon = 0 and isband=1) er
			// inner join course c on c.id = er.courseid inner join (select *
			// from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id
			// left join elclass ec on er.bandclassid=ec.id order by
			// er.begintime desc)t where rownum <= ? ) where rn>=?";
			sql = "select * from (select t.*, rownum rn from ("
					+ "select distinct er.id erid, er.title,er.location, er.begintime,er.endtime erendtime,"
					+ "c.id cid,c.name,c.status cstatus,c.creater,er.bandclassid bandclassid,ec.name className,er.type from "
					+ "(select * from exam_room where iscommon=1 and isNormal = 1 and  valid != 9 and type != 1) er inner join course c on c.id = er.courseid "
					+ "inner join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id left join elclass ec on er.bandclassid=ec.id "
					+ "where sqi.classid=er.bandclassid order by er.begintime desc"
					+ ")t where rownum <= ? ) where rn>=?";

			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_LIST));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			UserDao ud = new UserDaoImpl();
			ELUser creater = null;
			Course course = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setType(rs.getInt(13));

				MyExamPaper mc = new MyExamPaper();
				mc.setExamRoom(er);
				course = new Course();
				course.setId(rs.getInt(6));
				course.setName(rs.getString(7));
				course.setClassid(rs.getInt("bandclassid"));
				course.setClassName(rs.getString("className"));
				// mc.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				// MyCourse myCourse = new MyCourse();//锟剿讹拷锟斤拷锟斤拷锟斤拷锟斤拷没锟矫碉拷
				// myCourse.setStatus(rs.getInt(8));
				// mc.setMyCourse(myCourse);
				// mc.setId(rs.getInt(9));
				// mc.setMyScore(rs.getFloat(10));
				// mc.setStatus(rs.getInt(11));
				// mc.setEndtime(rs.getTimestamp(12));
				// mc.setIspassed(rs.getInt(13));
				creater = ud.getUserById(rs.getInt("creater"));
				course.setCreater(creater);
				mc.setCourse(course);
				mc.setBindingId(getbandingeroom(mc));
				myBxc.add(mc);

			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 锟斤拷锟斤拷锟轿程的斤拷业锟缴硷拷锟叫憋拷
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listMyQuiz4(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
		String sql = "";
		try {
			// String sql="select * from (select t.*, rownum rn from (select
			// distinct er.id erid, er.title,er.location,
			// er.begintime,er.endtime erendtime,c.id cid,c.name,c.status
			// cstatus,c.creater,er.bandclassid bandclassid,ec.name className
			// from (select * from exam_room where iscommon = 0 and isband=1) er
			// inner join course c on c.id = er.courseid inner join (select *
			// from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id
			// left join elclass ec on er.bandclassid=ec.id order by
			// er.begintime desc)t where rownum <= ? ) where rn>=?";
			sql = "select * from (select t.*, rownum rn from ("
					+ "select distinct er.id erid, er.title,er.location, er.begintime,er.endtime erendtime,"
					+ "c.id cid,c.name,c.status cstatus,c.creater,er.bandclassid bandclassid,ec.name className,er.type from "
					+ "(select * from exam_room where classid=0 and isNormal = 1 and  valid != 9 and type != 1) er inner join course c on c.id = er.courseid "
					+ "inner join (select * from study_exampaper where userid = ?) sqi on sqi.roomid =er.id left join elclass ec on er.bandclassid=ec.id "
					+ "where sqi.classid=er.bandclassid order by er.begintime desc"
					+ ")t where rownum <= ? ) where rn>=?";

			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_LIST));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			UserDao ud = new UserDaoImpl();
			ELUser creater = null;
			Course course = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setType(rs.getInt(13));

				MyExamPaper mc = new MyExamPaper();
				mc.setExamRoom(er);
				course = new Course();
				course.setId(rs.getInt(6));
				course.setName(rs.getString(7));
				course.setClassid(rs.getInt("bandclassid"));
				course.setClassName(rs.getString("className"));
				// mc.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				// MyCourse myCourse = new MyCourse();//锟剿讹拷锟斤拷锟斤拷锟斤拷锟斤拷没锟矫碉拷
				// myCourse.setStatus(rs.getInt(8));
				// mc.setMyCourse(myCourse);
				// mc.setId(rs.getInt(9));
				// mc.setMyScore(rs.getFloat(10));
				// mc.setStatus(rs.getInt(11));
				// mc.setEndtime(rs.getTimestamp(12));
				// mc.setIspassed(rs.getInt(13));
				creater = ud.getUserById(rs.getInt("creater"));
				course.setCreater(creater);
				mc.setCourse(course);
				mc.setBindingId(getbandingeroom(mc));
				myBxc.add(mc);

			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	// public List<MyExamPaper> listMyQuiz(int userid, int status, int pageNow,
	// int pageSize) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select er.id,er.title,er.location,
	// er.begintime,er.endtime,c.id,c.name,c.status,sqi.id "
	// + "from (select * from exam_room where iscommon = 0) er "
	// + "left join study_quizinfo sqi on er.id = sqi.roomid "
	// + "left join (select c1.id ,c1.name,ca.status from course c1 left join
	// course_apply ca on ca.courseid=c1.id and ca.status = ? and ca.userid= ?)
	// c on c.id = er.courseid "
	// + "where sqi.userid = ? order by er.begintime desc limit ?,?");
	// ps.setInt(1, status);
	// ps.setInt(2, userid);
	// ps.setInt(3, userid);
	// ps.setInt(4, pageNow);
	// ps.setInt(5, pageSize);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
	// er.setLocation(rs.getString(3));
	// er.setBegintime(rs.getTimestamp(4));
	// er.setEndtime(rs.getTimestamp(5));
	// MyExamPaper mc = new MyExamPaper();
	// mc.setExamRoom(er);
	// mc.setCourse(new Course(rs.getInt(6), rs.getString(7)));
	// mc.setStatus(rs.getInt(8));
	// mc.setId(rs.getInt(9));
	// myBxc.add(mc);
	// }
	// } catch (Exception e) {
	// logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return myBxc;
	// }

	public List<MyExamPaper> listMypaperByRidanUid(int userid, int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ep.id epid,ep.title,sqi.id sqiid,sqi.status,prac.id,prac.title,"
							+ "sqi.practimes mypract,sqi.pracscore myprasc,erp.practimes erppractimes,erp.pracscore erppracscore,sqi.myexamcount,erp.quizlook,erp.scorelook"
							+ " from study_quizinfo sqi left join exam_reps erp on erp.epid = sqi.epid and erp.roomid = sqi.roomid"
							+ " left join exampaper ep on ep.id = erp.epid left join exampaper prac on prac.id = erp.pracid where sqi.userid =? "
							+ "and sqi.roomid= ?");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				MyExamPaper mc = new MyExamPaper();
				mc.setId(rs.getInt(3));
				ExamPaper prac = new ExamPaper(rs.getInt(5), rs.getString(6));
				mc.setPractimes(rs.getInt(7));
				mc.setPracscore(rs.getFloat(8));
				prac.setPractimes(rs.getInt(9));
				prac.setPracscore(rs.getFloat(10));
				mc.setMyexamcount(rs.getInt("myexamcount"));
				ep.setPrac(prac);
				ep.setQuizlook(rs.getInt("quizlook"));
				ep.setScorelook(rs.getInt("scorelook"));
				mc.setExamPaper(ep);
				mc.setStatus(rs.getInt(4));
				if (rs.getInt(4) != 2 || rs.getInt(4) != 3) {
					mc.setMyScore(getMyScore(rs.getInt(3)));
				}
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;

	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟较�
	 * 
	 * @param userid
	 * @param roomid
	 * @param mrrid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listMypaperByRidanUid(int userid, int roomid,
			int mrrid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select ep.id epid,ep.title,sqi.id
			// sqiid,sqi.status,prac.id,prac.title,"
			// + "sqi.practimes mypract,sqi.pracscore myprasc,erp.practimes
			// erppractimes,erp.pracscore erppracscore,sqi.quizcount
			// myexamcount,erp.quizlook,erp.scorelook,sqi.ispassed"
			// + " from study_exampaper sqi left join exam_reps erp on erp.epid
			// = sqi.epid and erp.roomid = sqi.roomid"
			// + " left join exampaper ep on ep.id = erp.epid left join
			// exampaper prac on prac.id = erp.pracid where sqi.userid =? "
			// + "and sqi.roomid= ? ");
			ps = ct
					.prepareStatement("select ep.id epid,ep.title,sqi.myscore,sqi.status,"
							+ "sqi.quizcount myexamcount,erp.quizlook,erp.scorelook,sqi.ispassed,erp.quizcount,sqi.avgscore,sqi.maxscore,sqi.isdel,erp.passmanner,min(sq.status),ep.during,sqi.classid  "
							+ " from study_exampaper sqi left join exam_reps erp on erp.epid = sqi.epid and erp.roomid = sqi.roomid"
							+ " left join exampaper ep on ep.id = erp.epid left join study_quizinfo sq on sq.roomid =sqi.roomid and sqi.userid=sq.userid and sqi.epid = sq.epid where sqi.userid =? and sqi.roomid= ?  "
							+ "  group by  ep.id,ep.title,sqi.myscore,sqi.status,  sqi.quizcount ,erp.quizlook,erp.scorelook,sqi.ispassed,erp.quizcount,sqi.avgscore,sqi.maxscore,sqi.isdel,erp.passmanner,ep.during,erp.sortid,sqi.classid   order by erp.sortid");

			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			// ps.setInt(3, mrrid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				MyExamPaper mc = new MyExamPaper();
				mc.setId(rs.getInt("epid"));
				// ExamPaper prac = new ExamPaper(rs.getInt(5),
				// rs.getString(6));
				// mc.setPractimes(rs.getInt(7));
				// mc.setPracscore(rs.getFloat(8));
				// prac.setPractimes(rs.getInt(9));
				// prac.setPracscore(rs.getFloat(10));
				mc.setStatus(rs.getInt(4));
				mc.setMyexamcount(rs.getInt(5));
				// ep.setPrac(prac);
				ep.setQuizlook(rs.getInt(6));
				ep.setScorelook(rs.getInt(7));
				ep.setQuizcount(rs.getInt(9));
				// mc.setStatus(rs.getInt(4));
				// if (mc.getStatus() != 2 || mc.getStatus() != 3) {
				// mc.setMyScore(getMyScore(rs.getInt(3)));
				mc.setMyScore(rs.getFloat(3));
				mc.setAvgscore(rs.getFloat(10));
				mc.setMaxscore(rs.getFloat(11));
				// }
				mc.setIspassed(rs.getInt(8));
				mc.setIsdel(rs.getInt(12));
				ep.setPassmanner(rs.getInt(13));
				mc.setMinstatus(rs.getInt(14));
				ep.setDuring(rs.getInt(15));
				// mc.setId(getMypaperIdByRidanUid(userid, roomid, ep.getId(),
				// mc.getMyexamcount(), ep.getQuizcount()));
				mc.setClassId(rs.getInt(16));
//				mc.setWd(rs.getInt(17));
				mc.setExamPaper(ep);
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public int getMypaperIdByRidanUid(int userid, int roomid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int i = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sqi.quizcount myexamcount,erp.quizcount,sqi.avgscore,sqi.maxscore,sqi.classid "
							+ " from study_exampaper sqi left join exam_reps erp on erp.epid = sqi.epid and erp.roomid = sqi.roomid"
							+ " where sqi.userid =? and sqi.roomid= ? and sqi.epid = ? ");

			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, epid);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) < rs.getInt(2)) // 未锟斤拷锟斤拷锟斤拷锟�
				{// if (!hasInQuizPaper(userid, roomid, epid )) {
					i = intoQuizPaper(userid, roomid, epid);
					ps = ct
							.prepareStatement("update study_exampaper set quizcount = quizcount+1 where userid = ? and epid = ? and roomid = ?");
					ps.setInt(1, userid);
					ps.setInt(2, epid);
					ps.setInt(3, roomid);
					ps.executeUpdate();
					ps.close();
				} else {
					ps = ct
							.prepareStatement("select max(id) from study_quizinfo where userid = ? and epid = ? and roomid = ? and (status=0 or status =1) order by begintime");
					ps.setInt(1, userid);
					ps.setInt(2, epid);
					ps.setInt(3, roomid);
					rs = ps.executeQuery();
					if (rs.next()) {
						i = rs.getInt(1);
					}
					// i= -1;
				}
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return i;
	}

	public List<MyExamPaper> listMyExampapers(int userid, int roomid, int epid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select sqi.id sqid, sqi.myScore,sqi.endtime,sqi.status ,sqi.ispassed ,reps.scorelook,reps.quizlook "
					+ " from study_quizinfo sqi join exam_reps reps on reps.roomid=sqi.roomid and reps.epid=sqi.epid where sqi.roomid= ? and userid = ? and sqi.epid = ? order by sqi.id desc";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, epid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				mep.setEndtime(rs.getTimestamp(3));
				mep.setStatus(rs.getInt(4));
				mep.setIspassed(rs.getInt(5));
				mep.setExamPaper(new ExamPaper());
				mep.getExamPaper().setScorelook(rs.getInt(6));
				mep.getExamPaper().setQuizlook(rs.getInt(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟介看锟斤拷锟脚的匡拷锟皆成硷拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public MyExamPaper getMyExampaper(int userid, int roomid, int epid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper myExamPaper = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select a.* from ("
					+ "select sqi.id sqid, sqi.myScore,sqi.endtime,sqi.status ,sqi.ispassed ,reps.scorelook,reps.quizlook,reps.passgrade "
					+ " from study_quizinfo sqi join exam_reps reps on reps.roomid=sqi.roomid and reps.epid=sqi.epid where sqi.roomid= ? and userid = ? and sqi.epid = ? order by sqi.myscore desc,sqi.endtime desc"
					+ ") a where rownum=1";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, epid);
			rs = ps.executeQuery();
			if (rs.next()) {
				myExamPaper = new MyExamPaper(rs.getInt(1));
				myExamPaper.setMyScore(rs.getFloat(2));
				myExamPaper.setEndtime(rs.getTimestamp(3));
				myExamPaper.setStatus(rs.getInt(4));
				myExamPaper.setIspassed(rs.getInt(5));
				myExamPaper.setExamPaper(new ExamPaper());
				myExamPaper.getExamPaper().setScorelook(rs.getInt(6));
				myExamPaper.getExamPaper().setQuizlook(rs.getInt(7));
				myExamPaper.getExamPaper().setPassgrade(rs.getFloat(8));
				myExamPaper.getExamPaper().setId(epid);
			}
		} catch (Exception e) {
			logger.error("锟介看锟斤拷锟脚的匡拷锟皆成硷拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myExamPaper;
	}

	// public List<MyExamPaper> listMypaperByRidanUid(int userid, int roomid,int
	// mrrid) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement("select ep.id epid,ep.title,sqi.id
	// sqiid,sqi.status,prac.id,prac.title,"
	// + "sqi.practimes mypract,sqi.pracscore myprasc,erp.practimes
	// erppractimes,erp.pracscore
	// erppracscore,sqi.myexamcount,erp.quizlook,erp.scorelook,sqi.ispassed"
	// + " from study_quizinfo sqi left join exam_reps erp on erp.epid =
	// sqi.epid and erp.roomid = sqi.roomid"
	// + " left join exampaper ep on ep.id = erp.epid left join exampaper prac
	// on prac.id = erp.pracid where sqi.userid =? "
	// + "and sqi.roomid= ? ");
	// ps.setInt(1, userid);
	// ps.setInt(2, roomid);
	// // ps.setInt(3, mrrid);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
	// MyExamPaper mc = new MyExamPaper();
	// mc.setId(rs.getInt(3));
	// ExamPaper prac = new ExamPaper(rs.getInt(5), rs.getString(6));
	// mc.setPractimes(rs.getInt(7));
	// mc.setPracscore(rs.getFloat(8));
	// prac.setPractimes(rs.getInt(9));
	// prac.setPracscore(rs.getFloat(10));
	// mc.setMyexamcount(rs.getInt(11));
	// ep.setPrac(prac);
	// ep.setQuizlook(rs.getInt(12));
	// ep.setScorelook(rs.getInt(13));
	// mc.setExamPaper(ep);
	// mc.setStatus(rs.getInt(4));
	// if(rs.getInt(4) != 2 || rs.getInt(4) != 3){
	// mc.setMyScore(getMyScore(rs.getInt(3)));
	// }
	// mc.setIspassed(rs.getInt(14));
	// myBxc.add(mc);
	// }
	// } catch (Exception e) {
	// logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷?", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return myBxc;
	// }
	public List<MyRoom> listErsWithoutC(int userid, int type, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> myBxc = new ArrayList<MyRoom>();
		String sql = "";
		try {
			// if (type == 1) { // 选锟斤拷式锟斤拷锟斤拷
			// sql = "select * from (select t.*,rownum rn from ("
			// + "select er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.id) "
			// + "sqicount,sr.myscore,el.realname , er.type,er.valid
			// ervalid,er.uvalid eruvalid,er.isnormal erisnormal,er.svalid
			// ersvalid,sr.ispassed,er.isApplication from (select * from
			// exam_room where iscommon=1 and valid != 9 and type = 1 and svalid
			// = 5) er join eluser el on"
			// + " er.createrid=el.id left join study_room sr on er.id =
			// sr.roomid left join (select * from study_quizinfo where userid =
			// ?) "
			// + "sqi on sqi.roomid =sr.roomid where sr.userid =? group by
			// er.id,er.title,er.location ,"
			// + "er.begintime,er.endtime,sr.status,sr.myscore,el.realname,
			// er.type,er.valid,er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication
			// "
			// + ") t where rownum <=?) where rn>=?";
			// } else {
			// sql = "select * from (select t.*,rownum rn from ("
			// + "select er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.epid) "
			// + "sqicount,sr.myscore,el.realname , er.type,er.valid
			// ervalid,er.uvalid eruvalid,er.isnormal erisnormal,er.svalid
			// ersvalid,sr.ispassed,er.isApplication from (select * from
			// exam_room where iscommon=1 and valid != 9 and isNormal = 1 and
			// type != 1 ) er join eluser el on"
			// + " er.createrid=el.id left join study_room sr on er.id =
			// sr.roomid left join (select * from study_exampaper where userid =
			// ?) "
			// + "sqi on sqi.roomid =sr.roomid where sr.userid =? group by
			// er.id,er.title,er.location ,"
			// + "er.begintime,er.endtime,sr.status,sr.myscore,el.realname,
			// er.type,er.valid,er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication
			// ) t where rownum <=?) where rn>=?";
			
			//classid=-3锟斤拷锟斤拷锟斤拷锟绞撅拷
			sql = "select * from (select t.*,rownum rn from ("
					+ " select erinfo.id erid,erinfo.title,erinfo.location ,erinfo.begintime,erinfo.endtime,erinfo.status,"
					+ " erinfo.sqicount,erinfo.myscore,erinfo.realname , erinfo.type,erinfo.valid ervalid,erinfo.uvalid eruvalid,"
					+ " erinfo.isnormal erisnormal,erinfo.svalid ersvalid,erinfo.ispassed,erinfo.isApplication,erinfo.examcount, erinfo.joinway,erinfo.mainimg "
					+
					// ", count(srr.id) srrcount " +
					" from (select er.id ,er.title,er.location ,er.begintime,er.endtime,sr.status,count(sqi.epid) sqicount,sr.myscore,el.realname , "
					+ " er.type,er.valid ,er.uvalid ,er.isnormal ,er.svalid ,sr.ispassed,er.isApplication,er.examcount,sr.joinway,er.mainimg "
					+ " from (select * from exam_room  where iscommon=1 and valid != 9 and isNormal = 1  and  type != 1 and classid!=-3 ) er "
					+ " join eluser el on er.createrid=el.id left join study_room sr on er.id = sr.roomid and sr.joinway!=3 "
					+ " left join (select * from study_exampaper where userid = ?) sqi on sqi.roomid =sr.roomid where sr.userid=? and sr.status!=-1 "
					+ " group by er.id,er.title,er.location ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname, er.type,er.valid,"
					+ " er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication,er.examcount,sr.joinway,er.mainimg) erinfo "
					+
					// " left join (select * from study_room_record where
					// userid=?) srr on erinfo.id=srr.roomid" +
					" group by erinfo.id ,erinfo.title,erinfo.location ,"
					+ " erinfo.begintime,erinfo.endtime,erinfo.status,erinfo.sqicount,erinfo.myscore,erinfo.realname , erinfo.type,erinfo.valid ,erinfo.uvalid ,erinfo.isnormal ,"
					+ " erinfo.svalid ,erinfo.ispassed,erinfo.isApplication,erinfo.examcount,erinfo.joinway,erinfo.mainimg "
					+
					// ",srr.roomid" +
					" ) t where rownum <=?) where rn>=?";
			// }
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_MROOM_WITHOUTCOURSE));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			// ps.setInt(3, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			ELUser creater = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt("ervalid"));
				er.setUvalid(rs.getInt("eruvalid"));
				er.setIsnormal(rs.getInt("erisnormal"));
				er.setSvalid(rs.getInt("ersvalid"));
				creater = new ELUser();
				creater.setRealname(rs.getString("realname"));
				er.setCreater(creater);
				er.setIsApplication(rs.getInt("isApplication"));
				MyRoom mc = new MyRoom();
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setEpsize(rs.getInt(7));
				mc.setMyScore(rs.getFloat(8));
				// if (this.getExamIsNoKao(userid, er.getId())) {
				mc.setIspassed(rs.getInt("ispassed"));
				// } else {
				// mc.setIspassed(3);
				// }
				er.setExamcount(rs.getInt("examcount"));
				// mc.setSrrcount(rs.getInt("srrcount"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 锟斤拷锟斤拷锟绞撅拷
	 */
	public List<MyRoom> listQuesWithoutC(int userid, int type, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> myBxc = new ArrayList<MyRoom>();
		String sql = "";
		try {
			sql = "select * from (select t.*,rownum rn from ("
					+ " select erinfo.id erid,erinfo.title,erinfo.location ,erinfo.begintime,erinfo.endtime,erinfo.status,"
					+ " erinfo.sqicount,erinfo.myscore,erinfo.realname , erinfo.type,erinfo.valid ervalid,erinfo.uvalid eruvalid,"
					+ " erinfo.isnormal erisnormal,erinfo.svalid ersvalid,erinfo.ispassed,erinfo.isApplication,erinfo.examcount, erinfo.joinway,erinfo.stuViewResult "
					+
					// ", count(srr.id) srrcount " +
					" from (select er.id ,er.title,er.location ,er.begintime,er.endtime,sr.status,count(sqi.epid) sqicount,sr.myscore,el.realname , "
					+ " er.type,er.valid ,er.uvalid ,er.isnormal ,er.svalid ,sr.ispassed,er.isApplication,er.examcount,sr.joinway,er.stuViewResult stuViewResult "
					+ " from (select * from exam_room  where iscommon=1 and valid != 9 and isNormal = 1  and  type != 1 and classid=-3) er "
					+ " join eluser el on er.createrid=el.id left join study_room sr on er.id = sr.roomid and sr.joinway!=3 "
					+ " left join (select * from study_exampaper where userid = ?) sqi on sqi.roomid =sr.roomid where sr.userid=? and sr.status!=-1 "
					+ " group by er.id,er.title,er.location ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname, er.type,er.valid,"
					+ " er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication,er.examcount,sr.joinway,er.stuViewResult) erinfo "
					+
					// " left join (select * from study_room_record where
					// userid=?) srr on erinfo.id=srr.roomid" +
					" group by erinfo.id ,erinfo.title,erinfo.location ,"
					+ " erinfo.begintime,erinfo.endtime,erinfo.status,erinfo.sqicount,erinfo.myscore,erinfo.realname , erinfo.type,erinfo.valid ,erinfo.uvalid ,erinfo.isnormal ,"
					+ " erinfo.svalid ,erinfo.ispassed,erinfo.isApplication,erinfo.examcount,erinfo.joinway,erinfo.stuViewResult "
					+
					// ",srr.roomid" +
					" ) t where rownum <=?) where rn>=?";
			// }
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_MROOM_WITHOUTCOURSE));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			// ps.setInt(3, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			ELUser creater = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt("ervalid"));
				er.setUvalid(rs.getInt("eruvalid"));
				er.setIsnormal(rs.getInt("erisnormal"));
				er.setSvalid(rs.getInt("ersvalid"));
				creater = new ELUser();
				creater.setRealname(rs.getString("realname"));
				er.setCreater(creater);
				er.setIsApplication(rs.getInt("isApplication"));
				MyRoom mc = new MyRoom();
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setEpsize(rs.getInt(7));
				mc.setMyScore(rs.getFloat(8));
				// if (this.getExamIsNoKao(userid, er.getId())) {
				mc.setIspassed(rs.getInt("ispassed"));
				// } else {
				// mc.setIspassed(3);
				// }
				er.setExamcount(rs.getInt("examcount"));
				// mc.setSrrcount(rs.getInt("srrcount"));
				er.setStuViewResult(rs.getInt("stuViewResult"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟揭的碉拷锟斤拷锟绞撅拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	// 锟斤拷锟斤拷锟斤拷锟斤拷锟揭的匡拷锟斤拷
	public List<MyRoom> study_index_listErsWithoutC(int userid, int number,
			boolean ifBuy) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> myBxc = new ArrayList<MyRoom>();
		String sql = "";
		String sqlAppend = "";
		try {
			if (ifBuy) {
				sqlAppend = " and sr.joinway!=3 ";
			} else {
				sqlAppend = " and sr.joinway=3 ";
			}
			// if (type == 1) { // 选锟斤拷式锟斤拷锟斤拷
			// sql = "select * from (select t.*,rownum rn from ("
			// + "select er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.id) "
			// + "sqicount,sr.myscore,el.realname , er.type,er.valid
			// ervalid,er.uvalid eruvalid,er.isnormal erisnormal,er.svalid
			// ersvalid,sr.ispassed,er.isApplication from (select * from
			// exam_room where iscommon=1 and valid != 9 and type = 1 and svalid
			// = 5) er join eluser el on"
			// + " er.createrid=el.id left join study_room sr on er.id =
			// sr.roomid left join (select * from study_quizinfo where userid =
			// ?) "
			// + "sqi on sqi.roomid =sr.roomid where sr.userid =? group by
			// er.id,er.title,er.location ,"
			// + "er.begintime,er.endtime,sr.status,sr.myscore,el.realname,
			// er.type,er.valid,er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication
			// "
			// + ") t where rownum <=?) where rn>=?";
			// } else {
			// sql = "select * from (select t.*,rownum rn from ("
			// + "select er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.epid) "
			// + "sqicount,sr.myscore,el.realname , er.type,er.valid
			// ervalid,er.uvalid eruvalid,er.isnormal erisnormal,er.svalid
			// ersvalid,sr.ispassed,er.isApplication from (select * from
			// exam_room where iscommon=1 and valid != 9 and isNormal = 1 and
			// type != 1 ) er join eluser el on"
			// + " er.createrid=el.id left join study_room sr on er.id =
			// sr.roomid left join (select * from study_exampaper where userid =
			// ?) "
			// + "sqi on sqi.roomid =sr.roomid where sr.userid =? group by
			// er.id,er.title,er.location ,"
			// + "er.begintime,er.endtime,sr.status,sr.myscore,el.realname,
			// er.type,er.valid,er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication
			// ) t where rownum <=?) where rn>=?";
			sql = "select t.*,rownum rn from ("
					+ " select erinfo.id erid,erinfo.title,erinfo.location ,erinfo.begintime,erinfo.endtime,erinfo.status,"
					+ " erinfo.sqicount,erinfo.myscore,erinfo.realname , erinfo.type,erinfo.valid ervalid,erinfo.uvalid eruvalid,"
					+ " erinfo.isnormal erisnormal,erinfo.svalid ersvalid,erinfo.ispassed,erinfo.isApplication,erinfo.examcount"
					+
					// ", count(srr.id) srrcount " +
					" from (select er.id ,er.title,er.location ,er.begintime,er.endtime,sr.status,count(sqi.epid) sqicount,sr.myscore,el.realname , "
					+ " er.type,er.valid ,er.uvalid ,er.isnormal ,er.svalid ,sr.ispassed,er.isApplication,er.examcount "
					+ " from (select * from exam_room  where iscommon=1 and valid != 9 and isNormal = 1  and  type != 1 ) er "
					+ " join eluser el on er.createrid=el.id left join study_room sr on er.id = sr.roomid  "
					+ sqlAppend
					+ " left join (select * from study_exampaper where userid = ?) sqi on sqi.roomid =sr.roomid where sr.userid=? and sr.status!=-1 "
					+ " group by er.id,er.title,er.location ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname, er.type,er.valid,"
					+ " er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication,er.examcount) erinfo "
					+
					// " left join (select * from study_room_record where
					// userid=?) srr on erinfo.id=srr.roomid" +
					" group by erinfo.id ,erinfo.title,erinfo.location ,"
					+ " erinfo.begintime,erinfo.endtime,erinfo.status,erinfo.sqicount,erinfo.myscore,erinfo.realname , erinfo.type,erinfo.valid ,erinfo.uvalid ,erinfo.isnormal ,"
					+ " erinfo.svalid ,erinfo.ispassed,erinfo.isApplication,erinfo.examcount"
					+
					// ",srr.roomid" +
					" ) t where rownum <=?";
			// }
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_MROOM_WITHOUTCOURSE));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, number);
			rs = ps.executeQuery();
			ELUser creater = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt("ervalid"));
				er.setUvalid(rs.getInt("eruvalid"));
				er.setIsnormal(rs.getInt("erisnormal"));
				er.setSvalid(rs.getInt("ersvalid"));
				creater = new ELUser();
				creater.setRealname(rs.getString("realname"));
				er.setCreater(creater);
				er.setIsApplication(rs.getInt("isApplication"));
				MyRoom mc = new MyRoom();
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setEpsize(rs.getInt(7));
				mc.setMyScore(rs.getFloat(8));
				mc.setIspassed(rs.getInt("ispassed"));
				er.setExamcount(rs.getInt("examcount"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟斤拷锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 锟叫断革拷学员锟矫匡拷锟斤拷锟角凤拷未锟斤拷锟皆癸拷
	 * 
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public boolean getExamIsNoKao(int userid, int roomid) throws ElException {
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sum(sqi.myexamcount) from study_quizinfo sqi left join exam_reps rep on sqi.epid=rep.epid where sqi.roomid=rep.roomid and sqi.userid=? and sqi.roomid=? ");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error("锟叫断革拷学员锟矫匡拷锟斤拷锟角凤拷未锟斤拷锟皆癸拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<MyRoom> listErsWithoutC(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> myBxc = new ArrayList<MyRoom>();
		try {
			String sql = "select * from (select t.*,rownum rn from ("
					+ "select er.id erid,er.title,er.location ,er.begintime,er.endtime,sr.status,count(sqi.id) "
					+ "sqicount,sr.myscore,el.realname , er.type from (select * from exam_room  where iscommon=1 and isNormal = 1 and valid != 9 and  type != 1 ) er join eluser el on"
					+ " er.createrid=el.id left join study_room sr on er.id = sr.roomid left join (select * from study_quizinfo where userid = ?) "
					+ "sqi on sqi.roomid =sr.roomid where  sr.userid =? group by er.id,er.title,er.location ,"
					+ "er.begintime,er.endtime,sr.status,sr.myscore,el.realname, er.type ) t where rownum <=?) where rn>=?";

			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_MROOM_WITHOUTCOURSE));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			ELUser creater = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setType(rs.getInt(10));

				creater = new ELUser();
				creater.setRealname(rs.getString("realname"));
				er.setCreater(creater);
				MyRoom mc = new MyRoom();
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setEpsize(rs.getInt(7));
				mc.setMyScore(rs.getFloat(8));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyRoom> onloadUcenterStudy(int userid)// hwc
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> myBxc = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_MROOM_WITHOUTCOURSE_UCENTERONLOAD));

			// String sql="select * from (select * from (select t.*,rownum rn
			// from (select er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.id)
			// sqicount,sr.myscore,el.realname from (select * from exam_room
			// where iscommon=1) er join eluser el on er.createrid=el.id left
			// join study_room sr on er.id = sr.roomid left join (select * from
			// study_quizinfo where userid = ?)sqi on sqi.roomid =sr.roomid
			// where sr.userid =? and er.valid= 1 group by
			// er.id,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname )t)
			// order by begintime desc )where rownum <=1";
			String sql = "select * from (select * from (select t.*,rownum rn from (select er.id erid,er.title,er.location ,er.begintime,er.endtime,sr.status,count(sqi.id) sqicount,sr.myscore,el.realname from (select * from exam_room where iscommon=1) er join eluser el on er.createrid=el.id left join study_room sr on er.id = sr.roomid left join (select * from  study_quizinfo where userid = ?)sqi on sqi.roomid =sr.roomid where  sr.userid =? and er.valid= 5 group by er.id,er.title,er.location ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname )t) order by begintime desc )where rownum <=3";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				user = new ELUser();
				user.setRealname(rs.getString("realname"));
				er.setCreater(user);
				MyRoom mc = new MyRoom();
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setEpsize(rs.getInt(7));
				mc.setMyScore(rs.getFloat(8));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 学员锟皆讹拷锟斤拷取锟斤拷锟斤拷锟斤拷诺锟斤拷锟皆�(锟斤拷习)
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_depAssign(int userid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call examprac_dep_set(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("学员锟皆讹拷锟斤拷取锟斤拷锟斤拷锟斤拷诺锟斤拷锟皆达拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 学员锟皆讹拷锟斤拷取锟斤拷锟斤拷锟斤拷值锟斤拷锟皆�(锟斤拷锟斤拷)
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_examJingzhongAssign(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call exam_jingzhong_set(?)");
			ps.setInt(1, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("学员锟皆讹拷锟斤拷取锟斤拷锟斤拷锟斤拷值锟斤拷锟皆�(锟斤拷锟斤拷)锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷取学员锟侥匡拷锟皆ｏ拷锟斤拷页锟斤拷
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> onloadUcenterStudy(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> myBxc = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();

			// String sql="select * from (select * from (select * from (select
			// t.*,rownum rn from (select er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.id)
			// sqicount,sr.myscore,el.realname from (select * from exam_room
			// where iscommon=1) er join eluser el on er.createrid=el.id left
			// join study_room sr on er.id = sr.roomid left join (select * from
			// study_quizinfo where userid = ?)sqi on sqi.roomid =sr.roomid
			// where sr.userid =? and er.valid= 5 group by
			// er.id,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname )t)
			// order by begintime desc )where rownum <=?) where rn>=?";
			// String sql="select * from (select t.*,rownum rn from (select
			// er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.id)
			// sqicount,sr.myscore,el.realname,er.valid ervalid,er.isnormal
			// erisnormal,er.type ertype,er.uvalid eruvalid from (select * from
			// exam_room where iscommon=1) er join eluser el on
			// er.createrid=el.id left join study_room sr on er.id = sr.roomid
			// left join (select * from study_quizinfo where userid = ?)sqi on
			// sqi.roomid =sr.roomid where sr.userid =? and er.valid!=9 and
			// ((er.isnormal=1 and er.type!=1) or (er.type=1 and uvalid=1))
			// group by er.id,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname,er.valid,er.isnormal,er.type,er.uvalid
			// order by begintime desc)t where rownum <=?) where rn>=?";
			// //((er.isnormal=1 and er.type!=1) or (er.type=1 and svalid=5))
			/*
			 * String sql = "select * from (select t.*,rownum rn from ( select *
			 * from (" + " select er.id erid,er.title,er.location
			 * ,er.begintime,er.endtime,el.realname,er.type ertype,er.valid
			 * ervalid,er.uvalid eruvalid," + "er.isnormal erisnormal,er.svalid
			 * ersvalid,er.isApplication,sr.status,count(sqi.id)
			 * sqicount,sr.myscore,(select sum(reps.quizcount) from
			 * study_exampaper sep join exam_reps reps on reps.epid=sep.epid and
			 * reps.roomid=sep.roomid where sep.userid = ? And sep.roomid =
			 * er.id) cancount,min(sqi.status) minstatus " + " from (select *
			 * from exam_room where iscommon=1 and endtime>sysdate and begintime<sysdate)
			 * er join " + " eluser el on er.createrid=el.id " + " left join
			 * study_room sr on er.id = sr.roomid " + " left join (select * from
			 * study_quizinfo where userid = ?) sqi on sqi.roomid =sr.roomid " + // "
			 * left join () sep on sep.roomid =sr.roomid " + //" where sr.userid =?
			 * and er.valid!=9 and er.isnormal=1 and sr.status!=-1 group by
			 * er.id,er.title,er.location
			 * ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname,er.valid,er.isnormal,er.type,er.uvalid,er.svalid,er.isApplication " + "
			 * where ((sr.userid =? and sr.status!=-1) or er.isApplication=2)
			 * and er.valid!=9 and er.isnormal=1 group by
			 * er.id,er.title,er.location
			 * ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname,er.valid,er.isnormal,er.type,er.uvalid,er.svalid,er.isApplication " + "
			 * order by begintime desc)t1 where ((t1.cancount != t1.sqicount or
			 * minstatus != 2) or t1.isApplication = 2) )t where rownum <=?)
			 * where rn>=?";
			 */
			String sql = "select * from (select t.*, rownum rn from ( select * from (select er.id erid,er.title, er.location, er.begintime,er.endtime, el.realname, er.type ertype, er.valid ervalid, er.uvalid eruvalid, er.isnormal erisnormal, er.svalid ersvalid, er.isApplication, sr.status status,  "
					+ " count(sqi.id) sqicount, sr.myscore , (select sum(reps.quizcount) from study_exampaper sep join exam_reps reps on reps.epid = sep.epid and reps.roomid = sep.roomid where sep.userid = ?  And sep.roomid = er.id) cancount, min(sqi.status) minstatus from (select * from exam_room"
					+ " where iscommon = 1  and endtime > sysdate and begintime < sysdate) er join eluser el on er.createrid = el.id  left join study_room sr on er.id = sr.roomid left join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid = sr.roomid where sr.userid = ? and "
					+ "er.valid != 9  and er.isnormal = 1 and sr.status != -1 group by er.id, er.title, er.location,er.begintime, er.endtime, sr.status, sr.myscore, el.realname, er.valid, er.isnormal, er.type, er.uvalid, er.svalid, er.isApplication order by begintime desc ) t1 "
					+ " where t1.cancount != t1.sqicount  or minstatus != 2"
					+ " union  select er.id erid,er.title,er.location,er.begintime,er.endtime,el.realname,er.type ertype,er.valid ervalid,er.uvalid eruvalid,er.isnormal erisnormal,er.svalid ersvalid,"
					+ " er.isApplication,   0 status,  0 sqicount, 0 myscore, 0 cancount,0 minstatus from (select * from exam_room where iscommon = 1 and endtime > sysdate and begintime < sysdate) er join eluser el on er.createrid = el.id where er.isApplication=2 and er.id not in(select roomid from study_room where userid =?)  ) t where rownum <= ?) where rn >= ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			ps.setInt(4, userid);
			ps.setInt(5, pageNow);
			ps.setInt(6, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			String examTitle = "";
			while (rs.next()) {
				examTitle = rs.getString(2);
				if (examTitle.length() > 28) {
					examTitle = examTitle.substring(0, 28) + "...";
				}
				ExamRoom er = new ExamRoom(rs.getInt(1), examTitle);
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				user = new ELUser();
				user.setRealname(rs.getString(6));
				er.setCreater(user);
				er.setType(rs.getInt(7));
				er.setValid(rs.getInt(8));
				er.setUvalid(rs.getInt(9));
				er.setIsnormal(rs.getInt(10));
				er.setSvalid(rs.getInt(11));
				er.setIsApplication(rs.getInt(12));
				er.setExamcount(rs.getInt(16));
				MyRoom mc = new MyRoom();
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(13));
				// mc.setEpsize(rs.getInt(7));
				mc.setMycount(rs.getInt(14));
				mc.setMyScore(rs.getFloat(15));
				mc.setMinstatus(rs.getInt(17));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 锟斤拷取学员锟侥匡拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int onloadUcenterStudyCount(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			// String sql="select count(*) from (select * from exam_room where
			// iscommon=1) er join eluser el on er.createrid=el.id left join
			// study_room sr on er.id = sr.roomid left join (select * from
			// study_quizinfo where userid = ?)sqi on sqi.roomid =sr.roomid
			// where sr.userid =? and er.valid= 5 group by
			// er.id,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname";
			// String sql = "select count(*) from (select er.id
			// erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname " +
			// " from (select * from exam_room where iscommon=1 and
			// endtime>sysdate and begintime<sysdate) er " +
			// " join eluser el on er.createrid=el.id left " +
			// " join study_room sr on er.id = sr.roomid " +
			// //" left join (select * from study_quizinfo where userid = ?)sqi
			// on sqi.roomid =sr.roomid " +
			// " where sr.userid =? and er.valid!=9 and er.isnormal=1 and
			// sr.status!=-1 )t";
			/*
			 * String sql="select count(*) from ( select * from (" + " select
			 * er.id erid,er.title,er.location
			 * ,er.begintime,er.endtime,el.realname,er.type ertype,er.valid
			 * ervalid,er.uvalid eruvalid," + "er.isnormal erisnormal,er.svalid
			 * ersvalid,er.isApplication,sr.status,count(sqi.id)
			 * sqicount,sr.myscore,(select sum(reps.quizcount) from
			 * study_exampaper sep join exam_reps reps on reps.epid=sep.epid and
			 * reps.roomid=sep.roomid where sep.userid = ? And sep.roomid =
			 * er.id) cancount,min(sqi.status) minstatus " + " from (select *
			 * from exam_room where iscommon=1 and endtime>sysdate and begintime<sysdate)
			 * er join " + " eluser el on er.createrid=el.id " + " left join
			 * study_room sr on er.id = sr.roomid " + " left join (select * from
			 * study_quizinfo where userid = ?) sqi on sqi.roomid =sr.roomid " + // "
			 * left join () sep on sep.roomid =sr.roomid " + " where ((sr.userid =?
			 * and sr.status!=-1) or er.isApplication=2) and er.valid!=9 and
			 * er.isnormal=1 group by er.id,er.title,er.location
			 * ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname,er.valid,er.isnormal,er.type,er.uvalid,er.svalid,er.isApplication " + "
			 * order by begintime desc)t1 where ((t1.cancount != t1.sqicount or
			 * minstatus != 2) or t1.isApplication = 2) )t";
			 */
			String sql = "select count(*) from ( select * from (select er.id erid,er.title, er.location, er.begintime,er.endtime, el.realname, er.type ertype, er.valid ervalid, er.uvalid eruvalid, er.isnormal erisnormal, er.svalid ersvalid, er.isApplication, sr.status status,  "
					+ " count(sqi.id) sqicount, sr.myscore , (select sum(reps.quizcount) from study_exampaper sep join exam_reps reps on reps.epid = sep.epid and reps.roomid = sep.roomid where sep.userid = ?  And sep.roomid = er.id) cancount, min(sqi.status) minstatus from (select * from exam_room"
					+ " where iscommon = 1  and endtime > sysdate and begintime < sysdate) er join eluser el on er.createrid = el.id  left join study_room sr on er.id = sr.roomid left join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid = sr.roomid where sr.userid = ? and "
					+ "er.valid != 9  and er.isnormal = 1 and sr.status != -1 group by er.id, er.title, er.location,er.begintime, er.endtime, sr.status, sr.myscore, el.realname, er.valid, er.isnormal, er.type, er.uvalid, er.svalid, er.isApplication order by begintime desc ) t1 "
					+ " where t1.cancount != t1.sqicount  or minstatus != 2"
					+ " union  select er.id erid,er.title,er.location,er.begintime,er.endtime,el.realname,er.type ertype,er.valid ervalid,er.uvalid eruvalid,er.isnormal erisnormal,er.svalid ersvalid,"
					+ " er.isApplication,   0 status,  0 sqicount, 0 myscore, 0 cancount,0 minstatus from (select * from exam_room where iscommon = 1 and endtime > sysdate and begintime < sysdate) er join eluser el on er.createrid = el.id where er.isApplication=2 and er.id not in(select roomid from study_room where userid =?)  ) t ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			ps.setInt(4, userid);
			// ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员锟侥匡拷锟斤拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取学员某锟斤拷锟斤拷锟斤拷锟斤拷锟叫达拷锟斤拷锟斤拷锟�
	 * 
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getStudyEroomAllCount(int userid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(id) from study_quizinfo sqi where roomid=? and userid=? ");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员某锟斤拷锟斤拷锟斤拷锟斤拷锟叫达拷锟斤拷锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取学员锟斤拷锟叫匡拷锟剿匡拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getEroomAllCount(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_room sr left join exam_room er on sr.roomid=er.id where sr.userid=? and er.valid!=9 and er.iscommon=1 ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员锟斤拷锟叫匡拷锟剿匡拷锟斤拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取学员锟斤拷通锟斤拷目锟斤拷丝锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getEroomPassedCount(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_room sr left join exam_room er on sr.roomid=er.id where sr.userid=? and er.valid!=9 and er.iscommon=1 and sr.ispassed=1");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员锟斤拷通锟斤拷目锟斤拷丝锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷说目锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @return
	 * @throws ElException
	 */
	public int getEroomEndCount() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from exam_room where valid=3");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷说目锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取学员未锟斤拷始锟侥匡拷锟剿匡拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getEroomNoCount(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_room sr left join exam_room er on sr.roomid=er.id where sr.userid=? and er.valid!=9 and ((er.isnormal=1 and er.type!=1) or (er.type=1 and svalid=5)) and er.isnormal=1 and er.begintime>sysdate");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员未锟斤拷始锟侥匡拷锟剿匡拷锟斤拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ExamRoom> listcanapplyrooms(int erlibid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from (select er.id , er.title,
			// er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type
			// ,row_number() over( order by er.begintime desc)rownum from
			// exam_room er left join eroom_lib erlib on erlib.id=er.erlibid
			// where er.iscommon=1 and er.type=2)t where t.rownum between ? and
			// ?");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CANAPPLYROOM_LIST));

			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setEpsize(rs.getInt(10));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int listcanapplyroomsSize(int erlibid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from exam_room er left join eroom_lib lib on lib.id = er.erlibid where  er.iscommon=1 and er.type=2 ");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public MyRoom getMyErsWithoutC(int roomid, int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoom mc = new MyRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select er.id erid,er.title,er.type,er.begintime,er.endtime,sr.status,count(sqi.id) sqicount,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount  from (select * from exam_room  where iscommon=1) er "
							+ "left join study_room sr on er.id = sr.roomid left join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid =sr.roomid where  sr.userid =? and er.id =? group by er.id ,er.title,er.type,er.begintime,er.endtime,sr.status,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount ");
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setType(rs.getInt(3));
				// er.setPrac(new ExamPaper(rs.getInt(4), rs.getString(5)));
				// er.setPractimes(rs.getInt(6));
				// er.setPracscore(rs.getInt(7));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setIsMacBand(rs.getInt("ismacband"));
				er.setIsIpLimit(rs.getInt("isiplimit"));
				er.setSvalid(rs.getInt("svalid"));
				er.setExamcount(rs.getInt("examcount"));
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setValid(rs.getInt(8));
				// mc.setPractimes(rs.getInt(7));
				// mc.setPracscore(rs.getInt(8));
				mc.setEpsize(rs.getInt(7));

			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mc;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷息
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public MyRoom getMyErsWithoutR(int roomid, int userid, int iscommon)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoom mc = new MyRoom();
		try {
			ct = DBConnection.getConnection();
			String sql = " select er.id erid,er.title,er.type,er.begintime,er.endtime,sr.status,count(sqi.epid) sqicount,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount,er.isxianzhikaopin,er.examsforday,er.jiangeshijian  from ";
			if (iscommon == -1) {
				sql += " exam_room er join study_room sr on er.id = sr.roomid join (select * from study_exampaper where userid = ?) sqi on sqi.roomid =sr.roomid where  sr.userid =? and er.id =? group by er.id ,er.title,er.type,er.begintime,er.endtime,sr.status,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount,er.isxianzhikaopin,er.examsforday,er.jiangeshijian ";
			} else {
				sql += "(select * from exam_room  where iscommon=?) er join study_room sr on er.id = sr.roomid join (select * from study_exampaper where userid = ?) sqi on sqi.roomid =sr.roomid where  sr.userid =? and er.id =? group by er.id ,er.title,er.type,er.begintime,er.endtime,sr.status,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount ,er.isxianzhikaopin,er.examsforday,er.jiangeshijian";
			}
			ps = ct.prepareStatement(sql);
			int idx = 0;
			if (iscommon != -1) {
				ps.setInt(1, iscommon);
				idx = 1;
			}

			ps.setInt(idx + 1, userid);
			// ps.setInt(2, mrrid);
			ps.setInt(idx + 2, userid);
			ps.setInt(idx + 3, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setType(rs.getInt(3));
				// er.setPrac(new ExamPaper(rs.getInt(4), rs.getString(5)));
				// er.setPractimes(rs.getInt(6));
				// er.setPracscore(rs.getInt(7));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setIsMacBand(rs.getInt("ismacband"));
				er.setIsIpLimit(rs.getInt("isiplimit"));
				er.setSvalid(rs.getInt("svalid"));
				er.setExamcount(rs.getInt("examcount"));
				er.setIsxianzhikaopin(rs.getInt("isxianzhikaopin"));
				er.setExamsforday(rs.getInt("examsforday"));
				er.setJiangeshijian(rs.getDouble("jiangeshijian"));
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setValid(rs.getInt(8));
				// mc.setPractimes(rs.getInt(7));
				// mc.setPracscore(rs.getInt(8));
				mc.setEpsize(rs.getInt(7));

			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mc;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷息
	 */
	public MyRoom getMyErsWithoutC(int roomid, int userid, int iscommon)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoom mc = new MyRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select er.id erid,er.title,er.type,er.begintime,er.endtime,sr.status,count(sqi.id) sqicount,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount from (select * from exam_room  where iscommon=?) er "
							+ "left join study_room sr on er.id = sr.roomid left join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid =sr.roomid where  sr.userid =? and er.id =? group by er.id ,er.title,er.type,er.begintime,er.endtime,sr.status,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount  ");
			ps.setInt(1, iscommon);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			ps.setInt(4, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setType(rs.getInt(3));
				// er.setPrac(new ExamPaper(rs.getInt(4), rs.getString(5)));
				// er.setPractimes(rs.getInt(6));
				// er.setPracscore(rs.getInt(7));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setIsMacBand(rs.getInt("ismacband"));
				er.setIsIpLimit(rs.getInt("isiplimit"));
				er.setSvalid(rs.getInt("svalid"));
				er.setExamcount(rs.getInt("examcount"));
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setValid(rs.getInt(8));
				// mc.setPractimes(rs.getInt(7));
				// mc.setPracscore(rs.getInt(8));
				mc.setEpsize(rs.getInt(7));

			}
		} catch (Exception e) {
			logger.error(" 锟斤拷取锟斤拷锟斤拷锟斤拷息锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mc;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷息
	 * 
	 * @param roomid
	 * @param userid
	 * @param iscommon
	 * @param mrrid
	 * @return
	 * @throws ElException
	 */
	public MyRoom getMyErsWithoutR(int roomid, int userid, int iscommon,
			int mrrid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoom mc = new MyRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select er.id erid,er.title,er.type,er.begintime,er.endtime,sr.status,count(sqi.epid) sqicount,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount from (select * from exam_room  where iscommon=?) er "
							+ " join study_room sr on er.id = sr.roomid join (select * from study_exampaper where userid =?) sqi on sqi.roomid =sr.roomid where  sr.userid =? and er.id =? group by er.id ,er.title,er.type,er.begintime,er.endtime,sr.status,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount  ");
			ps.setInt(1, iscommon);
			ps.setInt(2, userid);
			// ps.setInt(3, mrrid);
			ps.setInt(3, userid);
			ps.setInt(4, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setType(rs.getInt(3));
				// er.setPrac(new ExamPaper(rs.getInt(4), rs.getString(5)));
				// er.setPractimes(rs.getInt(6));
				// er.setPracscore(rs.getInt(7));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setIsMacBand(rs.getInt("ismacband"));
				er.setIsIpLimit(rs.getInt("isiplimit"));
				er.setSvalid(rs.getInt("svalid"));
				er.setExamcount(rs.getInt("examcount"));
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setValid(rs.getInt(8));
				// mc.setPractimes(rs.getInt(7));
				// mc.setPracscore(rs.getInt(8));
				mc.setEpsize(rs.getInt(7));

			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷息锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mc;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷息
	 */
	public List<MyRoom> getMyErsWithoutCS(int roomid, List<ELUser> elUsers,
			int iscommon) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> mcList = new ArrayList<MyRoom>();
		MyRoom mc = new MyRoom();
		try {
			String users = "";
			for (int i = 0; i > elUsers.size(); i++) {
				users = users + "" + elUsers.get(i).getId() + ",";
			}
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select er.id erid,er.title,er.type,er.begintime,er.endtime,sr.status,count(sqi.id) sqicount from (select * from exam_room  where iscommon=?) er "
							+ "left join study_room sr on er.id = sr.roomid left join (select * from study_quizinfo where userid in ("
							+ users
							+ ")) sqi on sqi.roomid =sr.roomid where  sr.userid in ("
							+ users
							+ ") and er.id =? group by er.id ,er.title,er.type,er.begintime,er.endtime,sr.status ");
			ps.setInt(1, iscommon);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setType(rs.getInt(3));
				// er.setPrac(new ExamPaper(rs.getInt(4), rs.getString(5)));
				// er.setPractimes(rs.getInt(6));
				// er.setPracscore(rs.getInt(7));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				// mc.setPractimes(rs.getInt(7));
				// mc.setPracscore(rs.getInt(8));
				mc.setEpsize(rs.getInt(7));
				mcList.add(mc);
			}
		} catch (Exception e) {
			logger.error(" 锟斤拷取锟斤拷锟斤拷锟斤拷息锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mcList;
	}

	public int listErsWithoutCSize(int userid, int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if (type == 1) {// 选锟斤拷式锟斤拷锟斤拷
				sql = "select sum(c) from ("
						+ "(select count(*) as c from (select * from exam_room where iscommon=1 and  valid != 9 and type = 1 and svalid = 5) er left join study_room sqi on er.id = sqi.roomid and sqi.joinway!=3 where sqi.userid = ?)"
						+ ")";
			} else {
				sql = "select sum(c) from ("
						+ "(select count(*) as c from (select * from exam_room where iscommon=1 and isNormal = 1 and  valid != 9 and type != 1) er left join study_room sqi on er.id = sqi.roomid and sqi.joinway!=3 where sqi.userid = ? and sqi.status!=-1)"
						+ ")";
			}
			ps = ct.prepareStatement(sql);
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_WITHOUTCOURSE_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listQuesWithoutCSize(int userid, int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if (type == 1) {
				sql = "select sum(c) from ("
						+ "(select count(*) as c from (select * from exam_room where iscommon=1 and  valid != 9 and type = 1 and svalid = 5 and classid=-3) er left join study_room sqi on er.id = sqi.roomid and sqi.joinway!=3 where sqi.userid = ?)"
						+ ")";
			} else {
				sql = "select sum(c) from ("
						+ "(select count(*) as c from (select * from exam_room where iscommon=1 and isNormal = 1 and  valid != 9 and type != 1 and classid=-3) er left join study_room sqi on er.id = sqi.roomid and sqi.joinway!=3 where sqi.userid = ? and sqi.status!=-1)"
						+ ")";
			}
			ps = ct.prepareStatement(sql);
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_WITHOUTCOURSE_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的碉拷锟斤拷锟绞撅拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取锟斤拷锟剿匡拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷删锟斤拷
	 * 
	 * @param userid
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public int listErsWithoutCSize2(int userid, int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if (type == 1) {// 选锟斤拷式锟斤拷锟斤拷
				sql = "select sum(c) from ("
						+ "(select count(*) as c from (select * from exam_room where iscommon=1 and type = 1 and uvalid = 1) er left join study_room sqi on er.id = sqi.roomid where sqi.userid = ?)"
						+ ")";
			} else {
				sql = "select sum(c) from ("
						+ "(select count(*) as c from (select * from exam_room where iscommon=1 and isNormal = 1 and type != 1) er left join study_room sqi on er.id = sqi.roomid where sqi.userid = ?)"
						+ ")";
			}
			ps = ct.prepareStatement(sql);
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_WITHOUTCOURSE_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listErsWithoutCSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// String sql = "select sum(c) from ("
			// + "(select count(*) as c from (select * from exam_room where
			// iscommon=1 and isNormal = 1 and valid != 9 and type != 1) er left
			// join study_room sqi on er.id = sqi.roomid where sqi.userid = ?)"
			// + ")";
			String sql = "select sum(c) from ("
					+ "(select count(*) as c from (select * from exam_room where iscommon=1 and isNormal = 1 ) er left join study_room sqi on er.id = sqi.roomid where sqi.userid = ?)"
					+ ")";
			ps = ct.prepareStatement(sql);
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_WITHOUTCOURSE_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listMyQuizSize(int userid, int type, String sql)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			if (type == 1) {
				sql = " select count(distinct er.id) from "
						+ "study_quizinfo sqi right join  exam_room er on er.id =sqi.roomid  where sqi.userid = ?  and iscommon=0 and  valid != 9 and type = 1 and uvalid = 1"
						+ " and sqi.classid=er.bandclassid ";
			} else {
				sql = " select count(distinct er.id) from "
						+ "study_quizinfo sqi right join  exam_room er on er.id =sqi.roomid  where sqi.userid = ?  and iscommon=0 and isNormal = 1 and  valid != 9 and type != 1   "
						+ " and sqi.classid=er.bandclassid ";
			}
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(" select count(*) from study_quizinfo
			// sqi left join exam_room er on er.id =sqi.roomid where sqi.userid
			// = ? and iscommon = 0");//
			// ps = ct.prepareStatement(" select count(distinct er.id) from
			// study_quizinfo sqi right join exam_room er on er.id =sqi.roomid
			// where sqi.userid = ? and iscommon = 0 and er.isband=1 and
			// sqi.classid=er.bandclassid");//
			ps = ct.prepareStatement(sql);//
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷锟斤拷锟轿程斤拷业锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @param type
	 * @param sql
	 * @return
	 * @throws ElException
	 */
	public int listMyQuizSize2(int userid, int type, String sql)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// if (type == 1) {
			// sql = " select count(distinct er.id) from "
			// + "study_quizinfo sqi right join exam_room er on er.id
			// =sqi.roomid where sqi.userid = ? and er.classid=0 and valid != 9
			// and type = 1 and uvalid = 1"
			// + " and sqi.classid=er.bandclassid ";
			// } else {
			// sql = " select count(distinct er.id) from "
			// + "study_exampaper sqi right join exam_room er on er.id
			// =sqi.roomid where sqi.userid = ? and er.classid=0 and isNormal =
			// 1 and valid != 9 and type != 1 "
			// + " and sqi.classid=er.bandclassid ";
			sql = "select count(er.id) from exam_room er inner join study_room sr on er.id=sr.roomid where er.classid=0  and  er.valid != 9 and er.isNormal = 1 and sr.userid=?";
			// }
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(" select count(*) from study_quizinfo
			// sqi left join exam_room er on er.id =sqi.roomid where sqi.userid
			// = ? and iscommon = 0");//
			// ps = ct.prepareStatement(" select count(distinct er.id) from
			// study_quizinfo sqi right join exam_room er on er.id =sqi.roomid
			// where sqi.userid = ? and iscommon = 0 and er.isband=1 and
			// sqi.classid=er.bandclassid");//
			ps = ct.prepareStatement(sql);//
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟轿程斤拷业锟斤拷锟皆筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listMyQuizSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(" select count(*) from study_quizinfo
			// sqi left join exam_room er on er.id =sqi.roomid where sqi.userid
			// = ? and iscommon = 0");//
			// ps = ct.prepareStatement(" select count(distinct er.id) from
			// study_quizinfo sqi right join exam_room er on er.id =sqi.roomid
			// where sqi.userid = ? and iscommon = 0 and er.isband=1 and
			// sqi.classid=er.bandclassid");//
			ps = ct
					.prepareStatement(" select count(distinct er.id) from "
							+ "study_quizinfo sqi right join  exam_room er on er.id =sqi.roomid  where sqi.userid = ?  and iscommon = 0   "
							+ "and valid != 9 and sqi.classid=er.bandclassid ");//
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷锟斤拷锟轿程斤拷业锟缴硷拷锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int listMyQuizSize2(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(" select count(*) from study_quizinfo
			// sqi left join exam_room er on er.id =sqi.roomid where sqi.userid
			// = ? and iscommon = 0");//
			// ps = ct.prepareStatement(" select count(distinct er.id) from
			// study_quizinfo sqi right join exam_room er on er.id =sqi.roomid
			// where sqi.userid = ? and iscommon = 0 and er.isband=1 and
			// sqi.classid=er.bandclassid");//
			ps = ct
					.prepareStatement(" select count(distinct er.id) from "
							+ "study_exampaper sqi right join  exam_room er on er.id =sqi.roomid  where sqi.userid = ?  and er.classid = 0   "
							+ "and valid != 9 and sqi.classid=er.bandclassid ");//
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟轿程斤拷业锟缴硷拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listMyQuizSize(int userid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_quizinfo sqi left join (select * from exam_room where iscommon = 0) er on er.id =sqi.roomid "
							+ "left join course_apply ca on ca.courseid=er.courseid and ca.userid = sqi.userid "
							+ "where sqi.userid = ? and ca.status = ?");
			ps.setInt(1, userid);
			ps.setInt(2, status);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	// public List<MyExamPaper> listMyQuiz(int userid, int status)
	// throws ElException {
	// return listMyQuiz(userid, status, 0, listMyQuizSize(userid, status));
	// }

	/**
	 * 锟斤拷锟斤拷猿锟斤拷锟�
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int listMyRecentQuizSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_QPAPER_RECENT_LIST_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/*
	 * public List<ExamRoom> listMyErWithOutCourse(int userid, int pn, int pS)
	 * throws ElException { PreparedStatement ps = null; ResultSet rs = null;
	 * Connection ct = null; List<ExamRoom> ers = new ArrayList<ExamRoom>();
	 * try { ct = DBConnection.getConnection(); ps =
	 * ct.prepareStatement(ElQuerySql
	 * .getSQL(StudyConstants.STUDY_QPAPER_WITHOUTCOURSE)); ps.setInt(1,
	 * userid); ps.setInt(2, pn); ps.setInt(3, pS); rs = ps.executeQuery();
	 * while (rs.next()) { ExamRoom er = new ExamRoom(rs.getInt(1),
	 * rs.getString(2)); er.setSupervisor(new ELUser(rs.getInt(3),
	 * rs.getString(4))); er.setBegintime(rs.getTimestamp(5));
	 * er.setEndtime(rs.getTimestamp(6)); er.setLocation(rs.getString(7)); //
	 * er.setCourse(new Course(rs.getInt(8))); ers.add(er); } } catch (Exception
	 * e) { logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e); throw new ElException(e); } finally {
	 * DBConnection.closeConnectInfo(ct, ps, rs); } return ers; }
	 */

	/*
	 * public int listMyErWithOutCourseSize(int userid) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * try { ct = DBConnection.getConnection(); ps =
	 * ct.prepareStatement(ElQuerySql
	 * .getSQL(StudyConstants.STUDY_QPAPER_WITHOUTCOURSE_SIZE)); ps.setInt(1,
	 * userid); rs = ps.executeQuery(); if (rs.next()) { return rs.getInt(1); } }
	 * catch (Exception e) { logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e); throw new
	 * ElException(e); } finally { DBConnection.closeConnectInfo(ct, ps, rs); }
	 * return 0; }
	 */
	public List<MyExamPaper> quizpapwithoutC_result_list(int roomid, int pN,
			int pS) throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_QPAPER_WITHOUTCOURSE_RESULT));
			ps.setInt(1, roomid);
			ps.setInt(2, pN);
			ps.setInt(3, pS);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2)));
				mep.setExamRoom(new ExamRoom(rs.getInt(3), rs.getString(8)));
				mep.setExamPaper(new ExamPaper(rs.getInt(4)));
				mep.setStatus(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEndtime(rs.getTimestamp(7));
				mep.setBegintime(rs.getTimestamp(9));
				mep.setScore(rs.getFloat(10));
				mep.getExamRoom().setScore(rs.getFloat(11));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟轿程匡拷锟皆筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public List<MyExamPaper> listErsWithoutC_result_detail(int userid,
			int roomid) throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select sqi.id ,sqi.userid, sqi.roomid, sqi.epid, sqi.status, sqi.myScore,"
							+ "sqi.endtime,er.title,sqi.begintime,er.score erscore,ep.title from  study_quizinfo sqi left join EXAM_ROOM er on sqi.roomid = er.id "
							+ "left join exampaper ep on ep.id = sqi.epid where sqi.roomid = ? and sqi.userid = ? and  er.iscommon=1 order by sqi.endtime");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2)));
				mep.setExamRoom(new ExamRoom(rs.getInt(3), rs.getString(8)));
				mep.setExamPaper(new ExamPaper(rs.getInt(4), rs.getString(11)));
				mep.setStatus(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEndtime(rs.getTimestamp(7));
				mep.setBegintime(rs.getTimestamp(9));
				mep.getExamRoom().setScore(rs.getFloat(10));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟轿程匡拷锟皆筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	/**
	 * 锟介看锟斤拷锟皆成硷拷锟斤拷锟斤拷业锟斤拷锟皆ｏ拷
	 * 
	 * @param userid
	 * @param roomid
	 * @param iscommon
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listErsWithoutC_result_detail(int userid,
			int roomid, int iscommon) throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select sqi.id ,sqi.userid, sqi.roomid, sqi.epid, sqi.status, sqi.myScore,"
							+ "sqi.endtime,er.title,sqi.begintime,er.score erscore,ep.title,sqi.ispassed from  study_quizinfo sqi left join EXAM_ROOM er on sqi.roomid = er.id "
							+ "left join exampaper ep on ep.id = sqi.epid where sqi.roomid = ? and sqi.userid = ? and  er.iscommon=? order by sqi.endtime");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, iscommon);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2)));
				mep.setExamRoom(new ExamRoom(rs.getInt(3), rs.getString(8)));
				mep.setExamPaper(new ExamPaper(rs.getInt(4), rs.getString(11)));
				mep.setStatus(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEndtime(rs.getTimestamp(7));
				mep.setBegintime(rs.getTimestamp(9));
				mep.getExamRoom().setScore(rs.getFloat(10));
				mep.setIspassed(rs.getInt("ispassed"));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟轿程匡拷锟皆筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public List<MyRoom> listErsWithoutC_result(int userid, int pageNow,
			int pageSize) throws ElException {

		List<MyRoom> meps = new ArrayList<MyRoom>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 锟侥撅拷
			// String sql = "select * from (select t.* ,rownum rn from(select
			// mr.userid,mr.roomid
			// ,er.title,mr.ispassed,mr.status,sum(sqi.myscore),count(sqi.id),er.begintime,er.endtime,er.createrid
			// from study_room mr left join exam_room er on er.id = mr.roomid
			// left join (select * from study_quizinfo where userid =?) sqi on
			// sqi.roomid = er.id where mr.userid = ? and iscommon=1 and
			// er.isNormal = 1 and er.valid != 9 group by mr.userid,mr.roomid
			// ,er.title,mr.ispassed,mr.status,er.begintime,er.endtime,er.createrid)
			// t where rownum <=? )where rn >=?";
			String sql = "select * from (select t.* ,rownum rn from(select mr.userid,mr.roomid ,er.title,mr.ispassed,mr.status,sum(sqi.myscore),count(sqi.id),er.begintime,er.endtime,er.createrid from study_room mr left join exam_room er on er.id = mr.roomid left join (select * from  study_quizinfo where userid =?) sqi on sqi.roomid = er.id  where mr.userid = ? and iscommon=1 and er.isNormal = 1 group by  mr.userid,mr.roomid ,er.title,mr.ispassed,mr.status,er.begintime,er.endtime,er.createrid) t where rownum <=? )where  rn >=?";
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select * from (select t.* ,rownum rn
			// from(select mr.userid,mr.roomid
			// ,er.title,mr.ispassed,mr.status,sum(sqi.myscore),count(sqi.id),er.begintime,er.endtime
			// from study_room mr left join exam_room er on er.id = mr.roomid "
			// + "left join (select * from study_quizinfo where userid =?) sqi
			// on sqi.roomid = er.id where mr.userid = ? group by
			// mr.userid,mr.roomid
			// ,er.title,mr.ispassed,mr.status,er.begintime,er.endtime) t where
			// rownum <=? )where rn >=?");

			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			UserDao ud = new UserDaoImpl();
			ELUser creater = null;
			while (rs.next()) {
				MyRoom mep = new MyRoom();
				mep.setTester(new ELUser(rs.getInt(1)));
				ExamRoom room = new ExamRoom(rs.getInt(2), rs.getString(3));
				room.setBegintime(rs.getTimestamp(8));
				room.setEndtime(rs.getTimestamp(9));
				creater = ud.getUserById(rs.getInt("createrid"));
				room.setCreater(creater);
				mep.setExamroom(room);
				mep.setIspassed(rs.getInt(4));
				mep.setStatus(rs.getInt(5));
				mep.setMyScore(rs.getFloat(6));
				mep.setEpsize(rs.getInt(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟轿程匡拷锟皆筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public List<MyEprac> listmyexamprac(int useid, int begin, int end)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyEprac> xx = new ArrayList<MyEprac>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_MYEXAMPRAC_LIST));
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from (select epr.id,epr.title,epr.begintime,epr.endtime,count(eprq.id) pracsize,"
							+ "sum(eprq.myscore) pracscore,max(eprq.myscore) pracmax,el.realname,epr.valid,epr.praccount from examprac epr join eluser el on epr.userid=el.id "
							+ "left join examprac_assign epra on epra.eprid= epr.id left join eprac_quizinfo eprq on eprq.pracid = epr.id "
							+ "and eprq.userid = epra.userid where epra.userid =? and epr.valid in (1,3) group by epr.id,epr.title,epr.begintime,epr.endtime,"
							+ "epr.epid,el.realname,epr.valid,epr.praccount order by epr.id )t where rownum <=?) where rn>=?");

			ps.setInt(1, useid);
			ps.setInt(2, begin);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			ELUser creater = null;
			while (rs.next()) {
				MyEprac mepr = new MyEprac();
				Examprac epr = new Examprac();
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				creater = new ELUser();
				creater.setRealname(rs.getString("realname"));
				epr.setUser(creater);
				epr.setPracCount(rs.getInt("praccount"));
				mepr.setPrac(epr);
				mepr.setTimes(rs.getInt(5));
				mepr.setTotalscore(rs.getFloat(6));
				mepr.setMaxscore(rs.getFloat(7));
				mepr.setAvgscore(mepr.getTimes() == 0 ? 0 : mepr
						.getTotalscore()
						/ mepr.getTimes());
				mepr.setValid(rs.getInt("valid"));
				xx.add(mepr);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return xx;
	}

	public MyEprac getmyexamprac(int useid, int pracid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyEprac mepr = new MyEprac();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement(" select
			// epr.id,epr.title,epr.begintime,epr.endtime,count(eprq.id)
			// pracsize,"
			// + "sum(eprq.myscore) pracscore,max(eprq.myscore) pracmax from
			// examprac_assign epra left join examprac epr on epra.eprid= epr.id
			// left join exampaper ep on epr.epid=ep.id "
			// + "left join eprac_quizinfo eprq on eprq.pracid = epr.id and
			// eprq.userid = epra.userid where epra.userid =? and epr.id = ?
			// group by epr.id,epr.title,epr.begintime,epr.endtime,epr.epid");
			ps = ct
					.prepareStatement("select epr.id,epr.title,epr.begintime,epr.endtime,count(eprq.id) pracsize,"
							+ "sum(eprq.myscore) pracscore,max(eprq.myscore) pracmax,ep.ep_tscore*(epr.passgrade/100) passScore from examprac_assign epra left join examprac epr on epra.eprid= epr.id "
							+ "left join exampaper ep on epr.epid=ep.id left join eprac_quizinfo eprq on eprq.pracid = epr.id and eprq.userid = epra.userid where epra.userid =? and epr.id =? group by epr.id,epr.title,epr.begintime,epr.endtime,epr.epid,ep.ep_tscore,epr.passgrade");
			ps.setInt(1, useid);
			ps.setInt(2, pracid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Examprac epr = new Examprac();
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				mepr.setPrac(epr);
				mepr.setTimes(rs.getInt(5));
				mepr.setTotalscore(rs.getFloat(6));
				mepr.setMaxscore(rs.getFloat(7));
				mepr.setAvgscore(mepr.getTimes() == 0 ? 0 : mepr
						.getTotalscore()
						/ mepr.getTimes());
				mepr.setPassScore(rs.getFloat("passScore"));
				mepr.setPassCount(this.getMyepracPassCount(useid, pracid));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mepr;
	}

	/**
	 * 锟斤拷取学员锟斤拷锟斤拷习通锟斤拷锟斤拷锟�
	 * 
	 * @param useid
	 * @param pracid
	 * @return
	 * @throws ElException
	 */
	public int getMyepracPassCount(int useid, int pracid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from examprac epr left join exampaper ep on epr.epid=ep.id left join eprac_quizinfo eprq on eprq.pracid = epr.id where eprq.userid=? and epr.id=? and eprq.myscore>=ep.ep_tscore*(epr.passgrade/100) ");
			ps.setInt(1, useid);
			ps.setInt(2, pracid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员锟斤拷锟斤拷习通锟斤拷锟斤拷锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public MyExamPaper getmyexamprac(int useid, int pracid, long starttime)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper m = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,userid,passtime  from eprac_quizinfo where userid = ? and pracid = ? and starttime=? ");
			ps.setInt(1, useid);
			ps.setInt(2, pracid);
			ps.setLong(3, starttime);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.setId(rs.getInt(1));
				m.setTester(new ELUser(rs.getInt(2)));
				m.setPassTime(rs.getInt(3));
				// m.setPrac(new Examprac(rs.getInt(3),""));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	public MyExamPaper getmyexampracbyid(int pid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper m = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eprq.id,eprq.userid,eprq.pracid,epr.title  from eprac_quizinfo eprq left join examprac epr on epr.id = eprq.pracid where eprq.id=? ");
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.setId(rs.getInt(1));
				m.setTester(new ELUser(rs.getInt(2)));
				m.setExamprac(new Examprac(rs.getInt(3), rs.getString(4)));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	public void intomyexamprac(MyEprac eprac) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into eprac_quizinfo(userid,pracid,starttime) values(?,?,?)");
			ps.setInt(1, eprac.getTester().getId());
			ps.setInt(2, eprac.getPrac().getId());
			ps.setLong(3, eprac.getStarttime());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Question getQuestionByPrac(int pqiid, Question question)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Question q = new Question();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,epq.myanswer,epq.sortid,epq.blockid from eprac_questions epq  "
							+ "left join question q on epq.qid = q.id left join question_lib qlb on q.qlibid = qlb.id "
							+ "where epq.blockid = ? and epq.pracqid = ? and q.id= ?");
			ps.setInt(1, question.getEpblock().getId());
			ps.setInt(2, pqiid);
			ps.setInt(3, question.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setStuAnswer(rs.getString(13));
				q.setSortid(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(rs.getInt(15)));
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	public List<MyExamPaper> listMpracExampapers(int pracid, int userid,
			int pn, int pss) throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select pqi.id , pqi.myscore,pqi.starttime,pqi.endtime from eprac_quizinfo pqi where pqi.userid = ? and pqi.pracid = ? order by pqi.endtime desc");
			ps.setInt(1, userid);
			ps.setInt(2, pracid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				mep.setBegintime(new Timestamp(rs.getLong(3)));
				mep.setEndtime(rs.getTimestamp(4));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟轿程匡拷锟皆筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public boolean checkPracQuestion(int pqiid, Question question)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from eprac_questions where pracqid=? and blockid =? and qid= ?");
			ps.setInt(1, pqiid);
			ps.setInt(2, question.getEpblock().getId());
			ps.setInt(3, question.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				b = true;
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	public void insertPracQuestion(int pqiid, Question q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (!checkPracQuestion(pqiid, q)) {
				ps = ct
						.prepareStatement("insert into eprac_questions(pracqid ,blockid,qid,myanswer,sortid) values(?,?,?,?,?)");
				ps.setInt(1, pqiid);
				ps.setInt(2, q.getEpblock().getId());
				ps.setInt(3, q.getId());
				ps.setString(4, q.getStuAnswer());
				ps.setInt(5, q.getSortid());
				ps.executeUpdate();
				ps.close();
			}
			ps = ct
					.prepareStatement("select * from eprac_blocks where pracqid=? and blockid =?  ");
			ps.setInt(1, pqiid);
			ps.setInt(2, q.getEpblock().getId());
			rs = ps.executeQuery();
			if (!rs.next()) {
				ps.close();
				ps = ct
						.prepareStatement("insert into eprac_blocks(pracqid,blockid ) values(  ?,? )");
				ps.setInt(1, pqiid);
				ps.setInt(2, q.getEpblock().getId());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void updatePracQuestion(int pqiid, Question q) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eprac_questions set myanswer =? where pracqid=? and blockid=? and qid =?");
			ps.setString(1, q.getStuAnswer());
			ps.setInt(2, pqiid);
			ps.setInt(3, q.getEpblock().getId());
			ps.setInt(4, q.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ExamPaper getMyPracPaper(int praqid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
					.prepareStatement("select  ep.id,ep.title,ep.description,ep.userid, ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit, ep.ep_tscore,ep.ep_realscore,ep.queryurl,eq.myscore,eu.realname,epr.passgrade "
							+ "from exampaper ep left join examprac epr on epr.epid = ep.id left join eprac_quizinfo eq on eq.pracid = epr.id left join eluser eu on eu.id = ep.userid where eq.id = ? ");
			ps.setInt(1, praqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4), rs.getString(14)));
				ep.setShowmod(rs.getInt(5));
				ep.setDuring(rs.getInt(6));
				ep.setModifytime(rs.getTimestamp(7));
				ep.setCreatetime(rs.getTimestamp(8));
				ep.setOpentimelimit(rs.getBoolean(9));
				ep.setEp_tscore(rs.getFloat(10));
				ep.setEp_realscore(rs.getFloat(11));
				ep.setQueryurl(rs.getString(12));
				ep.setEpBlocks(getPracBlockByEpRidUid(praqid, ep));
				ep.setMep_tscore(rs.getFloat(13));
				ep.setPassgrade(rs.getFloat(15));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	private List<ExamPaperBlock> getPracBlockByEpRidUid(int praqid, ExamPaper ep)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,epb.random,epb.rulestring,epb.realscore,"
							+ "eprb.myscore from exampaperblock epb left join eprac_blocks eprb on eprb.blockid = epb.id and eprb.pracqid= ? where epb.exampaperid=? order by epb.sortid asc");
			ps.setInt(1, praqid);
			ps.setInt(2, ep.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
				epb.setExamPaper(new ExamPaper(rs.getInt(1)));
				epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				epb.setRandom(rs.getInt(9));
				epb.setRealqamount(epblockReqalqumunt(epb.getId(), epb
						.getRandom()));
				epb.setRulestring(rs.getString(10));
				epb.setRealscore(rs.getFloat(11));
				epb.setQuestions(listquestionByEpRidUid(praqid, epb.getId(), 0,
						ep));
				epb.setMyscore(rs.getFloat(12));
				epbs.add(epb);
			}
		} catch (Exception e) {
			logger.error("锟斤拷询锟斤拷锟斤拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}

	private List<Question> listquestionByEpRidUid(int praqid, int blockid,
			int parentid, ExamPaper ep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain ,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype, epq.myanswer,epq.sortid,epq.myscore,epq.status mystatus from eprac_questions epq  "
							+ "left join question q on epq.qid = q.id where epq.blockid = ? and epq.pracqid = ? and q.parentid = ? order by epq.sortid asc");
			ps.setInt(1, blockid);
			ps.setInt(2, praqid);
			ps.setInt(3, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setCreatetime(rs.getTimestamp(6));
				q.setModifytime(rs.getTimestamp(7));
				q.setQlevel(rs.getInt(8));
				q.setAnswer(rs.getString(9));
				q.setQtype(rs.getInt(10));
				q.setStuAnswer(rs.getString(11));
				q.setSortid(rs.getInt(12));
				q.setMyScore(rs.getFloat(13));
				if (q.getQtype() == 7) {
					q.setChilds(listquestionByEpRidUid(praqid, blockid, q
							.getId(), ep));
				} else if (q.getQtype() == 11 || q.getQtype() == 6) {
					ep.setMepZscore(ep.getMepZscore() + q.getMyScore());
				} else
					ep.setMepKscore(ep.getMepKscore() + q.getMyScore());
				q.setMystatus(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(blockid));
				q.setRulestring(getQRulestrByREBid(0, q));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;

	}

	public void submitPracPaper(MyExamPaper examPaper) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eprac_quizinfo set passtime =? ,endtime = ? where id = ?");
			ps.setInt(1, examPaper.getPassTime());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, examPaper.getId());
			ps.executeUpdate();
			ps.close();
			ps = null;
			ps = ct.prepareStatement("call eprac_setscore(?)");
			ps.setInt(1, examPaper.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public int listmyexampracsize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(epr.id) from examprac epr left join examprac_assign epra on epra.eprid= epr.id where  epra.userid = ? and epr.valid in (1,3)");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return 0;
	}

	public void updateStudyQuestion(int sqid, Question q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct
					.prepareStatement("update study_questions set myanswer =?,status = 2,opstatus=?,qindex=?,voice_answer=?,sentence_text=? where sqid=? and blockid =? and qid= ?");
			if(q.getQtype() == 17){//锟斤拷色锟斤拷锟斤拷只锟杰憋拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷址锟�
				ps.setString(1, q.getSingleStuAnswer());
			}else{
				ps.setString(1, q.getStuAnswer());
				System.out.println(q.getStuAnswer());
			}
			ps.setInt(2, q.getOpstatus());
			ps.setInt(3, (q.getQindex() ==0)||(q.getQindex() ==1) ?1:q.getQindex());
			ps.setString(4, q.getVoiceAnswer());
			ps.setString(5, q.getSentenceText());
			ps.setInt(6, sqid);
			ps.setInt(7, q.getEpblock().getId());
			ps.setInt(8, q.getId());
			ps.executeUpdate();
			ps.close();
			ps = null;
			ps = ct.prepareStatement("call sr_questionscore_set_save(?,?,?,?,?,?,?)");
			ps.setInt(1, sqid);
			ps.setInt(2, q.getEpblock().getId());
			ps.setInt(3, q.getId());
			//qindex锟窖凤拷锟斤拷
			if(q.getQindex()<=1){
				ps.setInt(4, 1);
			}else{
				ps.setInt(4, 2);
			}
			ps.setInt(5, q.getHasVoice());
			if(q.getQtype()==17||q.getQtype()==19||q.getQtype()==20){//锟斤拷拽锟解、锟斤拷锟斤拷锟解、锟斤拷色锟斤拷锟斤拷
				ps.setDouble(6, q.getSimilary());
			}else{
				ps.setDouble(6, 0);
			}
			ps.setInt(7, SystemConfOp.getIntValue(ElConstants.SYSTEM_SIMILARITY));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void updateStudyQuestionVoiceText(int sqid, Question q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement("update study_questions set voice_answer=? where sqid=? and blockid =? and qid= ?");
			ps.setString(1, q.getVoiceAnswer());
			ps.setInt(2, sqid);
			ps.setInt(3, q.getEpblock().getId());
			ps.setInt(4, q.getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("锟斤拷锟铰达拷锟斤拷锟斤拷锟铰硷拷锟斤拷谋锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public String getStudyQuestionVoiceText(int sqid, Question q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String voiceText = "";
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement("select voice_answer from study_questions where sqid=? and blockid =? and qid= ?");
			ps.setInt(1, sqid);
			ps.setInt(2, q.getEpblock().getId());
			ps.setInt(3, q.getId());
			rs = ps.executeQuery();
			if(rs.next()){
				voiceText = rs.getString(1) == null?"":rs.getString(1);
			}
			
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞讹拷锟斤拷谋锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return voiceText;
	}

	/**
	 * 锟斤拷锟铰达拷锟解（锟斤拷锟斤拷锟斤拷锟斤拷锟街ｏ拷锟绞硷拷锟斤拷
	 * 
	 * @param sqid
	 * @param q
	 * @throws ElException
	 */
	public void updateStudyQuestionOther(int sqid, Question q)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_questions set status = 2,opstatus=? where sqid=? and blockid =? and qid= ?");
			ps.setInt(1, q.getOpstatus());
			ps.setInt(2, sqid);
			ps.setInt(3, q.getEpblock().getId());
			ps.setInt(4, q.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟铰达拷锟解（锟斤拷锟斤拷锟斤拷锟斤拷锟街ｏ拷锟绞硷拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void insertStudyQuestion(int sqid, Question q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// if (!checkStudyQuestion(sqid, q)) {
			ps = ct
					.prepareStatement("insert into study_questions(sqid,blockid,qid,myanswer,sortid,status,opstatus) values(?,?,?,?,?,-1,?)");
			ps.setInt(1, sqid);
			ps.setInt(2, q.getEpblock().getId());
			ps.setInt(3, q.getId());
			ps.setString(4, q.getStuAnswer());
			ps.setInt(5, q.getSortid());
			ps.setInt(6, q.getOpstatus());
			ps.executeUpdate();
			// }
			ps.close();
			ps = null;
			ps = ct
					.prepareStatement("select * from study_blocks where sqid=? and blockid =?  ");
			ps.setInt(1, sqid);
			ps.setInt(2, q.getEpblock().getId());
			rs = ps.executeQuery();
			if (!rs.next()) {
				rs.close();
				ps.close();
				ps = ct
						.prepareStatement("insert into study_blocks(sqid,blockid ) values( ?,? )");
				ps.setInt(1, sqid);
				ps.setInt(2, q.getEpblock().getId());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkStudyQuestion(int sqid, Question q) throws ElException {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_questions where sqid=? and blockid =? and qid= ? ");
			ps.setInt(1, sqid);
			ps.setInt(2, q.getEpblock().getId());
			ps.setInt(3, q.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				b = true;
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	public boolean checkStudyQuestionSort(int sqid, Question q)
			throws ElException {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sq.* from study_questions sq left join question q on q.id = sq.qid where sq.sqid=? and sq.blockid =? and sq.sortid=? and q.parentid = 0");
			ps.setInt(1, sqid);
			ps.setInt(2, q.getEpblock().getId());
			ps.setInt(3, q.getSortid());
			rs = ps.executeQuery();
			if (rs.next()) {
				b = true;
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	/**
	 * 锟斤拷锟介长锟斤拷锟斤拷锟侥撅拷锟斤拷示锟斤拷锟斤拷
	 * 
	 * @param sqid
	 * @param isLeader
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getMyExamPaper(int sqid, int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
					.prepareStatement("select ep.id,ep.title,ep.description,ep.userid ,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit, ep.ep_tscore,ep.queryurl, reps.passgrade,eu.realname,reps.quizlook,reps.scorelook "
							+ " from exampaper ep left join study_quizinfo sqi on ep.id = sqi.epid "
							+ "left join exam_reps reps on reps.roomid = sqi.roomid and reps.epid = sqi.epid "
							+ "left join eluser eu on eu.id = ep.userid where sqi.id = ?");

			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4), rs.getString(13)));
				ep.setShowmod(rs.getInt(5));
				ep.setDuring(rs.getInt(6));
				ep.setModifytime(rs.getTimestamp(7));
				ep.setCreatetime(rs.getTimestamp(8));
				ep.setOpentimelimit(rs.getBoolean(9));
				ep.setEp_tscore(rs.getFloat(10));
				// ep.setEp_realscore(rs.getInt(11));
				ep.setQueryurl(rs.getString(11));
				ep.setEpBlocks(getBlockByEpRidUid_(sqid, ep, userid));
				// ep.setMep_tscore(rs.getFloat(12));
				ep.setPassgrade(rs.getFloat(12));
				ep.setQuizlook(rs.getInt(14));
				ep.setScorelook(rs.getInt(15));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	/**
	 * 锟斤拷取锟皆撅拷锟斤拷锟�
	 * 
	 * @param sqid
	 * @param ep
	 * @return
	 * @throws ElException
	 */
	private List<ExamPaperBlock> getBlockByEpRidUid_(int sqid, ExamPaper ep,
			int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,"
							+ "epb.random,epb.rulestring,epb.realscore,sqb.myscore from exampaperblock epb left join study_blocks sqb on sqb.blockid = epb.id where epb.exampaperid=? and sqb.sqid= ? order by epb.sortid asc");
			ps.setInt(1, ep.getId());
			ps.setInt(2, sqid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
				epb.setExamPaper(new ExamPaper(rs.getInt(1)));
				epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				epb.setRandom(rs.getInt(9));
				epb.setRealqamount(epblockReqalqumunt(epb.getId(), epb
						.getRandom()));
				epb.setRulestring(rs.getString(10));
				epb.setRealscore(rs.getFloat(11));
				epb.setQuestions(listquestionByEpRidUid_(sqid, epb.getId(), 0,
						ep, userid));
				epb.setMyscore(rs.getFloat(12));
				epbs.add(epb);
			}
		} catch (Exception e) {
			logger.error("锟斤拷询锟斤拷锟斤拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}

	/**
	 * 锟斤拷取小锟斤拷
	 * 
	 * @param sqid
	 * @param blockid
	 * @param parentid
	 * @param ep
	 * @return
	 * @throws ElException
	 */
	private List<Question> listquestionByEpRidUid_(int sqid, int blockid,
			int parentid, ExamPaper ep, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain ,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype, epq.myanswer,epq.sortid,epq.myscore,epq.status,q.scoreper,epq.opstatus,mup.score from study_questions epq  "
							+ "left join question q on epq.qid = q.id "
							+ " left join multiUserPapers mup on mup.sqid=epq.sqid and mup.blockid=epq.blockid and mup.qid=epq.qid and mup.userid=? "
							+ " where epq.blockid = ? and epq.sqid =?  and q.parentid = ? order by q.id asc");
			ps.setInt(1, userid);
			ps.setInt(2, blockid);
			ps.setInt(3, sqid);
			ps.setInt(4, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setCreatetime(rs.getTimestamp(6));
				q.setModifytime(rs.getTimestamp(7));
				q.setQlevel(rs.getInt(8));
				q.setAnswer(rs.getString(9));
				q.setQtype(rs.getInt(10));
				q.setStuAnswer(rs.getString(11));
				q.setSortid(rs.getInt(12));
				// q.setMyScore(rs.getFloat(13));
				if (q.getQtype() == 6 || q.getQtype() == 11) {
					q.setMyScore(rs.getFloat(17));
				} else {
					q.setMyScore(rs.getFloat(13));
				}
				if (q.getQtype() == 7) {
					// 锟斤拷锟斤拷锟斤拷小锟斤拷锟饺★拷锟�
					q.setChilds(listquestionByEpRidUid_(sqid, blockid, q
							.getId(), ep, userid));
				} else if (q.getQtype() == 11 || q.getQtype() == 6) {
					ep.setMepZscore(ep.getMepZscore() + q.getMyScore());
				} else
					ep.setMepKscore(ep.getMepKscore() + q.getMyScore());
				q.setMystatus(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(blockid));
				q.setRulestring(getQRulestrByREBid(sqid, q));
				q.setScoreper(rs.getInt("scoreper"));
				q.setOpstatus(rs.getInt(16));
				// q.setMyScore(rs.getFloat(17));
				// q.setMultiUserPapers(listUserMarkInfo(sqid, blockid,
				// q.getId()));
				qs.add(q);
			}
			ps.close();
			ps = null;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;

	}

	public ExamPaper getMyExamPaper(int sqid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
					.prepareStatement("select ep.id,ep.title,ep.description,ep.userid ,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit, ep.ep_tscore,ep.queryurl, reps.passgrade,eu.realname,reps.quizlook,reps.scorelook,ep.questiontotalcount "
							+ " from exampaper ep left join study_quizinfo sqi on ep.id = sqi.epid "
							+ "left join exam_reps reps on reps.roomid = sqi.roomid and reps.epid = sqi.epid "
							+ "left join eluser eu on eu.id = ep.userid where sqi.id = ?");

			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4), rs.getString(13)));
				ep.setShowmod(rs.getInt(5));
				ep.setDuring(rs.getInt(6));
				ep.setModifytime(rs.getTimestamp(7));
				ep.setCreatetime(rs.getTimestamp(8));
				ep.setOpentimelimit(rs.getBoolean(9));
				ep.setEp_tscore(rs.getFloat(10));
				// ep.setEp_realscore(rs.getInt(11));
				ep.setQueryurl(rs.getString(11));
				ep.setEpBlocks(getBlockByEpRidUid_(sqid, ep));
				// ep.setMep_tscore(rs.getFloat(12));
				ep.setPassgrade(rs.getFloat(12));
				ep.setQuizlook(rs.getInt(14));
				ep.setScorelook(rs.getInt(15));
				ep.setQuestionTotalCount(rs.getInt(16));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public ExamPaper getMyExamPaperInfo(int sqid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
					.prepareStatement("select ep.id,ep.title,ep.description,ep.userid ,ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit, ep.ep_tscore,ep.queryurl, reps.passgrade,eu.realname,reps.quizlook,reps.scorelook "
							+ " from exampaper ep left join study_quizinfo sqi on ep.id = sqi.epid "
							+ "left join exam_reps reps on reps.roomid = sqi.roomid and reps.epid = sqi.epid "
							+ "left join eluser eu on eu.id = ep.userid where sqi.id = ?");

			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4), rs.getString(13)));
				ep.setShowmod(rs.getInt(5));
				ep.setDuring(rs.getInt(6));
				ep.setModifytime(rs.getTimestamp(7));
				ep.setCreatetime(rs.getTimestamp(8));
				ep.setOpentimelimit(rs.getBoolean(9));
				ep.setEp_tscore(rs.getFloat(10));
				// ep.setEp_realscore(rs.getInt(11));
				ep.setQueryurl(rs.getString(11));
				// ep.setEpBlocks(getBlockByEpRidUid_(sqid, ep));
				// ep.setMep_tscore(rs.getFloat(12));
				ep.setPassgrade(rs.getFloat(12));
				ep.setQuizlook(rs.getInt(14));
				ep.setScorelook(rs.getInt(15));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public ExamPaper getMyExamPaper_(int sqid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
					.prepareStatement("select ep.id,ep.title ,ep.during, ep.opentimelimit, ep.ep_tscore,ep.queryurl, reps.passgrade "
							+ " from exampaper ep left join study_quizinfo sqi on ep.id = sqi.epid "
							+ "left join exam_reps reps on reps.roomid = sqi.roomid and reps.epid = sqi.epid where sqi.id = ?");

			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDuring(rs.getInt(3));
				ep.setOpentimelimit(rs.getBoolean(4));
				ep.setEp_tscore(rs.getFloat(5));
				ep.setQueryurl(rs.getString(6));
				ep.setPassgrade(rs.getFloat(7));
				ep.setEpBlocks(getBlockByEpRidUid_1(sqid, ep));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	/**
	 * 锟斤拷取锟皆撅拷锟斤拷锟�
	 * 
	 * @param sqid
	 * @param ep
	 * @return
	 * @throws ElException
	 */
	private List<ExamPaperBlock> getBlockByEpRidUid_(int sqid, ExamPaper ep)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,"
							+ "epb.random,epb.rulestring,epb.realscore,sqb.myscore from exampaperblock epb left join study_blocks sqb on sqb.blockid = epb.id where epb.exampaperid=? and sqb.sqid= ? order by epb.sortid asc");
			ps.setInt(1, ep.getId());
			ps.setInt(2, sqid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
				epb.setExamPaper(new ExamPaper(rs.getInt(1)));
				epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				epb.setRandom(rs.getInt(9));
				epb.setRealqamount(epblockReqalqumunt(epb.getId(), epb
						.getRandom()));
				epb.setRulestring(rs.getString(10));
				epb.setRealscore(rs.getFloat(11));
				epb.setQuestions(listquestionByEpRidUid_(sqid, epb.getId(), 0,
						ep));
				epb.setMyscore(rs.getFloat(12));
				epbs.add(epb);
			}
		} catch (Exception e) {
			logger.error("锟斤拷询锟斤拷锟斤拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}

	/**
	 * 锟斤拷取锟皆撅拷锟斤拷锟�
	 * 
	 * @param sqid
	 * @param ep
	 * @return
	 * @throws ElException
	 */
	private List<ExamPaperBlock> getBlockByEpRidUid_1(int sqid, ExamPaper ep)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,"
							+ "epb.random,epb.rulestring,epb.realscore,sqb.myscore,epb.answertime from exampaperblock epb left join study_blocks sqb on sqb.blockid = epb.id where epb.exampaperid=? and sqb.sqid= ? order by epb.sortid asc");
			ps.setInt(1, ep.getId());
			ps.setInt(2, sqid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
				epb.setExamPaper(new ExamPaper(rs.getInt(1)));
				epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				epb.setRandom(rs.getInt(9));
				// epb.setRealqamount(epblockReqalqumunt(epb.getId(), epb
				// .getRandom()));
				epb.setRulestring(rs.getString(10));
				epb.setRealscore(rs.getFloat(11));
				epb.setQuestions(listquestionByEpRidUid_1(sqid, epb.getId(), 0,
						ep));
				epb.setMyscore(rs.getFloat(12));
				epb.setAnswerTime(rs.getInt(13));
				epbs.add(epb);
			}
		} catch (Exception e) {
			logger.error("锟斤拷询锟斤拷锟斤拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}

	/**
	 * 锟斤拷取小锟斤拷
	 * 
	 * @param sqid
	 * @param blockid
	 * @param parentid
	 * @param ep
	 * @return
	 * @throws ElException
	 */
	private List<Question> listquestionByEpRidUid_1(int sqid, int blockid,
			int parentid, ExamPaper ep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.qtype,epq.status,epq.opstatus,epq.sortid,q.fasheng_question from study_questions epq  "
//							+ "left join question q on epq.qid = q.id where epq.blockid = ? and epq.sqid =?  and q.parentid = 0 order by q.id asc");
							+ "left join question q on epq.qid = q.id where epq.blockid = ? and epq.sqid =?  and q.parentid = 0 order by epq.sortid asc");
			ps.setInt(1, blockid);
			ps.setInt(2, sqid);
			// ps.setInt(3, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1));// , rs.getString(2)
				// q.setContent(rs.getString(3));
				// q.setSubject(rs.getString(4));
				// q.setQexplain(rs.getString(5));
				// q.setCreatetime(rs.getTimestamp(6));
				// q.setModifytime(rs.getTimestamp(7));
				// q.setQlevel(rs.getInt(8));
				// q.setAnswer(rs.getString(9));
				q.setQtype(rs.getInt(2));
				// q.setStuAnswer(rs.getString(11));
				// q.setSortid(rs.getInt(12));
				// q.setMyScore(rs.getFloat(13));
				// if (q.getQtype() == 7) {
				// //锟斤拷锟斤拷锟斤拷小锟斤拷锟饺★拷锟�
				// q.setChilds(listquestionByEpRidUid_1(sqid, blockid, q
				// .getId(),ep));
				// }else
				// if(q.getQtype()==11||q.getQtype()==6){
				// ep.setMepZscore(ep.getMepZscore()+q.getMyScore());
				// }else
				// ep.setMepKscore(ep.getMepKscore()+q.getMyScore());
				q.setMystatus(rs.getInt(3));
				q.setEpblock(new ExamPaperBlock(blockid));
				// q.setRulestring(getQRulestrByREBid(sqid, q));
				// q.setScoreper(rs.getInt("scoreper"));
				q.setOpstatus(rs.getInt(4));
				q.setSortid(rs.getInt(5));
				// q.setMultiUserPapers(listUserMarkInfo(sqid, blockid,
				// q.getId()));
				q.setFashengQuestion(rs.getString(6));
				qs.add(q);
			}
			ps.close();
			ps = null;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;

	}

	/**
	 * 锟斤拷取小锟斤拷
	 * 
	 * @param sqid
	 * @param blockid
	 * @param parentid
	 * @param ep
	 * @return
	 * @throws ElException
	 */
	private List<Question> listquestionByEpRidUid_(int sqid, int blockid,
			int parentid, ExamPaper ep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain ,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype, epq.myanswer,epq.sortid,epq.myscore,epq.status,q.scoreper,epq.opstatus, " +
							" q.fasheng_question,q.right_answer,q.media_file,q.model_voice,q.model_voice_text,q.voice_path,q.fen_content,nvl(epq.piyu,'  ')  from study_questions epq  "
							+ "left join question q on epq.qid = q.id where epq.blockid = ? and epq.sqid =?  and q.parentid = ? order by epq.sortid asc");
			ps.setInt(1, blockid);
			ps.setInt(2, sqid);
			ps.setInt(3, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setCreatetime(rs.getTimestamp(6));
				q.setModifytime(rs.getTimestamp(7));
				q.setQlevel(rs.getInt(8));
				q.setAnswer(rs.getString(9));
				q.setQtype(rs.getInt(10));
				q.setStuAnswer(rs.getString(11));
				q.setSortid(rs.getInt(12));
				q.setMyScore(rs.getFloat(13));
				if (q.getQtype() == 7) {
					// 锟斤拷锟斤拷锟斤拷小锟斤拷锟饺★拷锟�
					q.setChilds(listquestionByEpRidUid_(sqid, blockid, q
							.getId(), ep));
				} else if (q.getQtype() == 11 || q.getQtype() == 6) {
					ep.setMepZscore(ep.getMepZscore() + q.getMyScore());
				} else
					ep.setMepKscore(ep.getMepKscore() + q.getMyScore());
				q.setMystatus(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(blockid));
				q.setRulestring(getQRulestrByREBid(sqid, q));
				q.setScoreper(rs.getInt("scoreper"));
				q.setOpstatus(rs.getInt(16));
				q.setMultiUserPapers(listUserMarkInfo(sqid, blockid, q
								.getId()));
				q.setFashengQuestion(rs.getString(17));
				q.setRightAnswer(rs.getString(18));
				q.setMediaFile(rs.getString(19));
				q.setModelVoice(rs.getString(20));
				q.setModelVoiceText(rs.getString(21));
				q.setVoicePath(rs.getString(22));
				q.setFenContent(rs.getString(23));
				q.setPiyu(rs.getString(24));
				qs.add(q);
			}
			ps.close();
			ps = null;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;

	}

	/**
	 * 锟斤拷取锟斤拷锟介长锟斤拷锟侥撅拷锟斤拷息
	 * 
	 * @param sqid
	 * @param blockid
	 * @param parentid
	 * @param ep
	 * @return
	 * @throws ElException
	 */
	private List<MultiUserPapers> listUserMarkInfo(int sqid, int blockid,
			int qid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MultiUserPapers> mps = new ArrayList<MultiUserPapers>();
		Connection ct = null;
		MyExamPaper myep = this.getMyEpById(sqid);
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.username,eu.realname from exam_rappraises erp left join eluser eu on erp.userid=eu.id where roomid=? and isleader=0 ");
			ps.setInt(1, myep.getExamRoom().getId());
			rs = ps.executeQuery();
			MultiUserPapers mup = null;
			while (rs.next()) {
				mup = new MultiUserPapers();
				mup.setElUser(new ELUser(rs.getInt(1), rs.getString(2), rs
						.getString(3)));
				mup.setScore(getQuestionMarkInfo(sqid, blockid, qid, mup
						.getElUser().getId()));
				mps.add(mup);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟介长锟斤拷锟侥撅拷锟斤拷息锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mps;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟接︼拷锟斤拷木锟斤拷锟斤拷
	 * 
	 * @param sqid
	 * @param blockid
	 * @param qid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	private float getQuestionMarkInfo(int sqid, int blockid, int qid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select score from multiUserPapers mup where mup.userid=? and mup.sqid=? and mup.blockid=? and mup.qid=? ");
			ps.setInt(1, userid);
			ps.setInt(2, sqid);
			ps.setInt(3, blockid);
			ps.setInt(4, qid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟接︼拷锟斤拷木锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷锟斤拷实锟斤拷锟斤拷锟斤拷
	 * 
	 * @param blockid
	 * @param random
	 * @return
	 * @throws ElException
	 */
	private int epblockReqalqumunt(int blockid, int random) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int i = 0;
		try {
			ct = DBConnection.getConnection();
			if (random == 0)
				ps = ct
						.prepareStatement("select count(*) from exampaperblockquestion eq where eq.blockid= ?");
			else
				ps = ct
						.prepareStatement("select sum(eplevel1+eplevel2+eplevel3+eplevel4+eplevel5+eplevel) from exampaper_random eq where eq.blockid= ?");
			ps.setInt(1, blockid);
			rs = ps.executeQuery();
			if (rs.next()) {
				i = rs.getInt(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		} catch (Exception e) {
			logger.error("锟斤拷询锟斤拷锟斤拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return i;
	}

	public Question getQuestionByREBid(int sqid, Question question)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Question q = new Question();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,epq.myanswer,epq.sortid,epq.blockid,epq.opstatus,epq.myscore,epb.title epbtitle,q.fen_Content,epq.atime,epq.status,q.fwsize," +
									"q.fasheng_question,q.model_voice,q.media_file,q.voice_path,q.model_voice_text,q.stem_text,q.front_half_media_file,epq.voice_answer,epq.sentence_text from study_questions epq  "
							+ "left join question q on epq.qid = q.id left join question_lib qlb on q.qlibid = qlb.id left join exampaperblock epb on epb.id=epq.blockid "
							+ "where epq.sqid = ? and epq.blockid = ? and q.id= ?");
			ps.setInt(1, sqid);
			ps.setInt(2, question.getEpblock().getId());
			ps.setInt(3, question.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				q = new Question(rs.getInt(1), rs.getString(2));
				q.setMyExamPaperid(sqid);//锟皆撅拷id
				//q.setVoiceFile(QtypeUtil.getVoiceFile(q));
//				q.setStuVoiceText(QtypeUtil.getStuVoiceText(q));
				q.setStuVoiceText(rs.getString(30)==null?"":rs.getString(30));
				
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setStuAnswer(rs.getString(13));
				q.setSortid(rs.getInt(14));
				q
						.setEpblock(new ExamPaperBlock(rs.getInt(15), rs
								.getString(18)));
				q.setOpstatus(rs.getInt(16));
				q.setMyScore(rs.getFloat(17));
				q.setFenContent(rs.getString(19));
				q.setAtime(rs.getInt(20));
				q.setStatus(rs.getInt(21));
				q.setFwsize(rs.getInt(22));
				q.setFashengQuestion(rs.getString(23)==null?"":rs.getString(23));
				q.setModelVoice(rs.getString(24)==null?"":rs.getString(24));
				q.setMediaFile(rs.getString(25)==null?"":rs.getString(25));
				q.setVoicePath(rs.getString(26)==null?"":rs.getString(26));
				q.setModelVoiceText(rs.getString(27)==null?"":rs.getString(27));
				q.setStemText(rs.getString(28)==null?"":rs.getString(28));
				q.setFrontHalfMediaFile(rs.getString(29)==null?"":rs.getString(29));
//				if(q.getStemText()!=null&&!q.getStemText().equals("")){
//					q.setStemText(CheckHtml.getString(q.getStemText()));
//				}
				q.setVoiceAnswer(rs.getString(30)==null?"":rs.getString(30));
				q.setSentenceText(rs.getString(31)==null?"":rs.getString(31));
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	public Question getQuestionBySortBid(int sqid, Question question)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Question q = new Question();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,epq.myanswer,epq.sortid,epq.blockid,epq.opstatus,epq.myscore,epb.title epbtitle,q.fasheng_question  from study_questions epq  "
							+ "left join question q on epq.qid = q.id left join question_lib qlb on q.qlibid = qlb.id left join exampaperblock epb on epb.id=epq.blockid "
							+ "where epq.sqid = ? and epq.blockid = ? and epq.sortid= ?");
			ps.setInt(1, sqid);
			ps.setInt(2, question.getEpblock().getId());
			ps.setInt(3, question.getSortid());
			rs = ps.executeQuery();
			if (rs.next()) {
				q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setStuAnswer(rs.getString(13));
				q.setSortid(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(rs.getInt(15), rs
								.getString(18)));
				q.setOpstatus(rs.getInt(16));
				q.setMyScore(rs.getFloat(17));
				q.setFashengQuestion(rs.getString(19));
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	public String getQRulestrByREBid(int sqid, Question question)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String rulestring = "";
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select random,rulestring from exampaperblock where id = ?");
			ps.setInt(1, question.getEpblock().getId());
			rs = ps.executeQuery();
			int random = 0;
			if (rs.next()) {
				random = rs.getInt(1);
				rulestring = rs.getString(2);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (random == 0) {
				ps = ct
						.prepareStatement("select ebq.rulestring from exampaperblockquestion ebq where ebq.blockid =? and ebq.questionid =?");
				ps.setInt(1, question.getEpblock().getId());
				ps.setInt(2, question.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					rulestring = rs.getString(1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return rulestring;
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷习
	 * 
	 */

	public MyEprac getmyexamqprac(int useid, int qpracid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyEprac mepr = new MyEprac();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select epr.id,epr.title,epr.begintime,epr.endtime,count(eprq.id) qpracsize,"
							+ "sum(eprq.myscore) qpracscore,max(eprq.myscore) qpracmax from examqprac_assign epra left join examqprac epr on epra.eprid= epr.id "
							+ "left join eqprac_quizinfo eprq on eprq.qpracid = epr.id and eprq.userid = epra.userid where epra.userid =? and epr.id = ? group by epr.id,epr.title,epr.begintime,epr.endtime,epr.epid");
			ps.setInt(1, useid);
			ps.setInt(2, qpracid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Examprac epr = new Examprac();
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				mepr.setPrac(epr);
				mepr.setTimes(rs.getInt(5));
				mepr.setTotalscore(rs.getFloat(6));
				mepr.setMaxscore(rs.getFloat(7));
				mepr.setAvgscore(mepr.getTimes() == 0 ? 0 : mepr
						.getTotalscore()
						/ mepr.getTimes());
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mepr;
	}

	public MyExamPaper getmyexamprac(int praqid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper m = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select starttime,endtime,myscore,passtime,ispassed,eu.id euid,eu.username,eu.realname,eu.shenfenzheng from eprac_quizinfo eq left join eluser eu on eu.id=eq.userid where eq.id = ?");
			ps.setInt(1, praqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.setBegintime(new Timestamp(rs.getLong(1)));
				m.setEndtime(rs.getTimestamp(2));
				m.setMyScore(rs.getFloat(3));
				m.setPassTime(rs.getInt(4));
				m.setIspassed(rs.getInt(5));
				// m.setqprac(new Examqprac(rs.getInt(3),""));
				m.setTester(new ELUser(rs.getInt(6), rs.getString(7), rs
						.getString(8)));
				m.getTester().setShenfenzheng(rs.getString(9));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	public MyExamPaper getmyexamqprac(int useid, int qpracid, long starttime)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper m = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,userid  from eqprac_quizinfo where userid = ? and sqid = ? and starttime=? ");
			ps.setInt(1, useid);
			ps.setInt(2, qpracid);
			ps.setLong(3, starttime);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.setId(rs.getInt(1));
				m.setTester(new ELUser(rs.getInt(2)));
				// m.setqprac(new Examqprac(rs.getInt(3),""));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	public MyExamPaper getmyexamqpracbyid(int pid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper m = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select id, reps.pracid from exam_reps reps left join  study_quizinfo sqi on sqi.roomid=reps.roomid and sqi.epid = reps.epid  where sqi.id=?");
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			if (rs.next()) {
				m.setId(rs.getInt(1));
				m.setExamPaper(new ExamPaper(rs.getInt(2)));
				// m.setqprac(new Examqprac(rs.getInt(3),""));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	public void intomyexamqprac(MyEprac eqprac) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into eqprac_quizinfo(userid,sqid,starttime) values(?,?,?)");
			ps.setInt(1, eqprac.getTester().getId());
			ps.setInt(2, eqprac.getPrac().getId());
			ps.setLong(3, eqprac.getStarttime());
			ps.executeUpdate();
			ps.close();
			// if ("oracle".equals(SystemConfOp
			// .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			ps = ct
					.prepareStatement("select eqprac_quizinfo_sequence.currval from dual ");
			rs = ps.executeQuery();
			// }
			if (rs.next())
				eqprac.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Question getQuestionByqprac(int pqiid, Question question)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Question q = new Question();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,epq.myanswer,epq.sortid,epq.blockid from eqprac_questions epq  "
							+ "left join question q on epq.qid = q.id left join question_lib qlb on q.qlibid = qlb.id "
							+ "where epq.blockid = ? and epq.qpracqid = ? and q.id= ?");
			ps.setInt(1, question.getEpblock().getId());
			ps.setInt(2, pqiid);
			ps.setInt(3, question.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setStuAnswer(rs.getString(13));
				q.setSortid(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(rs.getInt(15)));
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	public List<MyExamPaper> listMqpracExampapers(int qpracid, int userid,
			int pn, int pss) throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select pqi.id , pqi.myscore,pqi.starttime,pqi.endtime from eqprac_quizinfo pqi where pqi.userid = ? and pqi.qpracid = ? order by pqi.endtime desc");
			ps.setInt(1, userid);
			ps.setInt(2, qpracid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				mep.setBegintime(new Timestamp(rs.getLong(3)));
				mep.setEndtime(rs.getTimestamp(4));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟轿程匡拷锟皆筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public boolean checkqpracQuestion(int pqiid, Question question)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from eqprac_questions where qpracqid=? and blockid =? and qid= ?");
			ps.setInt(1, pqiid);
			ps.setInt(2, question.getEpblock().getId());
			ps.setInt(3, question.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void insertqpracQuestion(int pqiid, Question q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (!checkqpracQuestion(pqiid, q)) {
				ps = ct
						.prepareStatement("insert into eqprac_questions(qpracqid ,blockid,qid,myanswer,sortid) values(?,?,?,?,?)");
				ps.setInt(1, pqiid);
				ps.setInt(2, q.getEpblock().getId());
				ps.setInt(3, q.getId());
				ps.setString(4, q.getStuAnswer());
				ps.setInt(5, q.getSortid());
				ps.executeUpdate();
			}
			ps = ct
					.prepareStatement("select * from eqprac_blocks where qpracqid=? and blockid =?  ");
			ps.setInt(1, pqiid);
			ps.setInt(2, q.getEpblock().getId());
			rs = ps.executeQuery();
			if (!rs.next()) {
				ps = ct
						.prepareStatement("insert into eqprac_blocks(qpracqid,blockid ) values(  ?,? )");
				ps.setInt(1, pqiid);
				ps.setInt(2, q.getEpblock().getId());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void updateqpracQuestion(int pqiid, Question q) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eqprac_questions set myanswer =? where qpracqid=? and blockid=? and qid =?");
			ps.setString(1, q.getStuAnswer());
			ps.setInt(2, pqiid);
			ps.setInt(3, q.getEpblock().getId());
			ps.setInt(4, q.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ExamPaper getMyqpracPaper(int praqid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
					.prepareStatement("select  ep.id,ep.title,ep.description,ep.userid, ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit, ep.ep_tscore,ep.ep_realscore,ep.queryurl,eqp.myscore "
							+ "from exam_reps reps left join exampaper ep on ep.id = reps.pracid left join study_quizinfo eq on eq.roomid = reps.roomid and eq.epid = reps.epid left join eqprac_quizinfo eqp on eqp.sqid = eq.id where eqp.id = ? ");
			ps.setInt(1, praqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4)));
				ep.setShowmod(rs.getInt(5));
				ep.setDuring(rs.getInt(6));
				ep.setModifytime(rs.getTimestamp(7));
				ep.setCreatetime(rs.getTimestamp(8));
				ep.setOpentimelimit(rs.getBoolean(9));
				ep.setEp_tscore(rs.getFloat(10));
				ep.setEp_realscore(rs.getFloat(11));
				ep.setQueryurl(rs.getString(12));
				ep.setEpBlocks(getqpracBlockByEpRidUid__(praqid, ep.getId()));
				ep.setMep_tscore(rs.getFloat(13));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	private List<ExamPaperBlock> getqpracBlockByEpRidUid__(int praqid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,epb.random,epb.rulestring,epb.realscore,"
							+ "eprb.myscore from exampaperblock epb left join eqprac_blocks eprb on eprb.blockid = epb.id and eprb.qpracqid= ? where epb.exampaperid=? order by epb.sortid asc");
			ps.setInt(1, praqid);
			ps.setInt(2, epid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
				epb.setExamPaper(new ExamPaper(rs.getInt(1)));
				epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				epb.setRandom(rs.getInt(9));
				epb.setRealqamount(epblockReqalqumunt(epb.getId(), epb
						.getRandom()));
				epb.setRulestring(rs.getString(10));
				epb.setRealscore(rs.getFloat(11));
				epb.setQuestions(listquestionByEpRidUid__(praqid, epb.getId(),
						0));
				epb.setMyscore(rs.getFloat(12));
				epbs.add(epb);
			}
		} catch (Exception e) {
			logger.error("锟斤拷询锟斤拷锟斤拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}

	private List<Question> listquestionByEpRidUid__(int praqid, int blockid,
			int parentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain ,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype, epq.myanswer,epq.sortid,epq.myscore,epq.status mystatus from eqprac_questions epq  "
							+ "left join question q on epq.qid = q.id where epq.blockid = ? and epq.qpracqid = ? and q.parentid = ? order by epq.sortid asc");
			ps.setInt(1, blockid);
			ps.setInt(2, praqid);
			ps.setInt(3, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setCreatetime(rs.getTimestamp(6));
				q.setModifytime(rs.getTimestamp(7));
				q.setQlevel(rs.getInt(8));
				q.setAnswer(rs.getString(9));
				q.setQtype(rs.getInt(10));
				q.setStuAnswer(rs.getString(11));
				q.setSortid(rs.getInt(12));
				if (q.getQtype() == 7) {
					q.setChilds(listquestionByEpRidUid__(praqid, blockid, q
							.getId()));
				}
				q.setMyScore(rs.getFloat(13));
				q.setMystatus(rs.getInt(14));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;

	}

	public void submitqpracPaper(MyExamPaper examPaper) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call eqprac_setscore(?,?)");
			ps.setInt(1, examPaper.getId());
			ps.setInt(2, examPaper.getExamPaper().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<MyExamPaper> listMpracExamqpapers(int pracid, int userid,
			int pn, int pS) throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select pqi.id , pqi.myscore,pqi.starttime,pqi.endtime from eqprac_quizinfo pqi where pqi.userid = ? and pqi.qpracid = ? order by pqi.endtime desc");
			ps.setInt(1, userid);
			ps.setInt(2, pracid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				mep.setBegintime(new Timestamp(rs.getLong(3)));
				mep.setEndtime(rs.getTimestamp(4));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟轿程匡拷锟皆筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public boolean checkcpracQuestion(int pqiid, Question question)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from cprac_questions where qpracqid=? and blockid =? and qid= ?");
			ps.setInt(1, pqiid);
			ps.setInt(2, question.getEpblock().getId());
			ps.setInt(3, question.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public ExamPaper getMycpracPaper(int praqid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
					.prepareStatement("select ep.id,ep.title,ep.description,ep.userid, ep.showmod,ep.during,ep.modifytime,ep.createtime,ep.opentimelimit, ep.ep_tscore,ep.ep_realscore,ep.queryurl,eqp.myscore,eu.realname,pp.passgrade "
							+ "from practicepaper pp left join exampaper ep on ep.id = pp.epid left join cprac_quizinfo eqp on eqp.ppid = pp.id left join eluser eu on eu.id = ep.userid where eqp.id = ? ");
			ps.setInt(1, praqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setDescription(rs.getString(3));
				ep.setElUser(new ELUser(rs.getInt(4), rs.getString(14)));
				ep.setShowmod(rs.getInt(5));
				ep.setDuring(rs.getInt(6));
				ep.setModifytime(rs.getTimestamp(7));
				ep.setCreatetime(rs.getTimestamp(8));
				ep.setOpentimelimit(rs.getBoolean(9));
				ep.setEp_tscore(rs.getFloat(10));
				ep.setEp_realscore(rs.getFloat(11));
				ep.setQueryurl(rs.getString(12));
				ep.setEpBlocks(getqpracBlockByEpRidUid___(praqid, ep));
				ep.setMep_tscore(rs.getFloat(13));
				ep.setPassgrade(rs.getFloat(15));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public MyExamPaper getmycprac(int pracid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper ep = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
			// .prepareStatement("select starttime,endtime,myscore,passtime from
					// cprac_quizinfo eqp where id = ? ");
					.prepareStatement("select eqp.starttime,eqp.endtime,eqp.myscore,eqp.passtime,eqp.classid,p.courseid,p.cpid from cprac_quizinfo eqp left join practicepaper p on p.id=eqp.ppid where eqp.id =?");
			ps.setInt(1, pracid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep.setBegintime(new Timestamp(rs.getLong(1)));
				ep.setEndtime(rs.getTimestamp(2));
				ep.setMyScore(rs.getFloat(3));
				ep.setPassTime(rs.getInt(4));
				ep.setClassId(rs.getInt(5));
				ep.setCourse(new Course(rs.getInt(6)));
				ep.setId(rs.getInt(7));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public MyExamPaper getmycprac(int pracid, int useid, int classid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper ep = new MyExamPaper();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(QuestionConstants.EXAMPAPER_QUERY_BYID));
			ps = ct
					.prepareStatement("select eqp.id from cprac_quizinfo eqp where ppid = ? and userid = ? and classid = ?");
			ps.setInt(1, pracid);
			ps.setInt(2, useid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public void deletecpracBlQuestion(int mid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from cprac_questions where QPRACQID = ?");
			ps.setInt(1, mid);
			ps.executeUpdate();
			ps.close();
			ps = ct
					.prepareStatement("delete from cprac_blocks where QPRACQID = ?");
			ps.setInt(1, mid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private List<ExamPaperBlock> getqpracBlockByEpRidUid___(int praqid,
			ExamPaper ep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaperBlock> epbs = new ArrayList<ExamPaperBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epb.exampaperid,epb.title,epb.description,epb.type,epb.questionamount,epb.eachscore,epb.sortid,epb.id,epb.random,epb.rulestring,epb.realscore,"
							+ "eprb.myscore from exampaperblock epb left join cprac_blocks eprb on eprb.blockid = epb.id and eprb.qpracqid= ? where epb.exampaperid=? order by epb.sortid asc");
			ps.setInt(1, praqid);
			ps.setInt(2, ep.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaperBlock epb = new ExamPaperBlock(rs.getInt(8));
				epb.setExamPaper(new ExamPaper(rs.getInt(1)));
				epb.setTitle(rs.getString(2));
				epb.setDescription(rs.getString(3));
				epb.setType(rs.getInt(4));
				epb.setQuestionamount(rs.getInt(5));
				epb.setEachscore(rs.getFloat(6));
				epb.setSortid(rs.getInt(7));
				epb.setRandom(rs.getInt(9));
				epb.setRealqamount(epblockReqalqumunt(epb.getId(), epb
						.getRandom()));
				epb.setRulestring(rs.getString(10));
				epb.setRealscore(rs.getFloat(11));
				epb.setQuestions(listquestionByEpRidUid___(praqid, epb.getId(),
						0, ep));
				epb.setMyscore(rs.getFloat(12));
				epbs.add(epb);
			}
		} catch (Exception e) {
			logger.error("锟斤拷询锟斤拷锟斤拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epbs;
	}

	private List<Question> listquestionByEpRidUid___(int praqid, int blockid,
			int parentid, ExamPaper ep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain ,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype, epq.myanswer,epq.sortid,epq.myscore,epq.status mystatus from cprac_questions epq  "
							+ "left join question q on epq.qid = q.id where epq.blockid = ? and epq.qpracqid = ? and q.parentid = ? order by epq.sortid asc");
			ps.setInt(1, blockid);
			ps.setInt(2, praqid);
			ps.setInt(3, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setCreatetime(rs.getTimestamp(6));
				q.setModifytime(rs.getTimestamp(7));
				q.setQlevel(rs.getInt(8));
				q.setAnswer(rs.getString(9));
				q.setQtype(rs.getInt(10));
				q.setStuAnswer(rs.getString(11));
				q.setSortid(rs.getInt(12));
				q.setMyScore(rs.getFloat(13));
				if (q.getQtype() == 7) {
					q.setChilds(listquestionByEpRidUid___(praqid, blockid, q
							.getId(), ep));
				} else {
					if (q.getQtype() == 11 || q.getQtype() == 6) {
						ep.setMepZscore(ep.getMepZscore() + q.getMyScore());
					} else
						ep.setMepKscore(ep.getMepKscore() + q.getMyScore());
				}
				q.setMystatus(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(blockid));
				q.setRulestring(getQRulestrByREBid(0, q));
				qs.add(q);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;

	}

	public Question getQuestionBycprac(int pqiid, Question question)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Question q = new Question();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain, q.qlibid,q.modifytime,"
							+ "q.createtime,q.qlevel,q.answer,q.qtype,qlb.name,epq.myanswer,epq.sortid,epq.blockid from cprac_questions epq  "
							+ "left join question q on epq.qid = q.id left join question_lib qlb on q.qlibid = qlb.id "
							+ "where epq.blockid = ? and epq.qpracqid = ? and q.id= ?");
			ps.setInt(1, question.getEpblock().getId());
			ps.setInt(2, pqiid);
			ps.setInt(3, question.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlib(new QuestionLib(rs.getInt(6), rs.getString(12)));
				q.setCreatetime(rs.getTimestamp(7));
				q.setModifytime(rs.getTimestamp(8));
				q.setQlevel(rs.getInt(9));
				q.setAnswer(rs.getString(10));
				q.setQtype(rs.getInt(11));
				q.setStuAnswer(rs.getString(13));
				q.setSortid(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(rs.getInt(15)));
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return q;
	}

	public void insertcpracQuestion(int pqiid, Question q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// if (!checkqpracQuestion(pqiid, q)) {
			ps = ct
					.prepareStatement("insert into cprac_questions(qpracqid ,blockid,qid,myanswer,sortid) values(?,?,?,?,?)");
			ps.setInt(1, pqiid);
			ps.setInt(2, q.getEpblock().getId());
			ps.setInt(3, q.getId());
			ps.setString(4, q.getStuAnswer());
			ps.setInt(5, q.getSortid());
			ps.executeUpdate();
			// }
			ps.close();

			ps = ct
					.prepareStatement("select * from cprac_blocks where qpracqid=? and blockid =?  ");
			ps.setInt(1, pqiid);
			ps.setInt(2, q.getEpblock().getId());
			rs = ps.executeQuery();
			if (!rs.next()) {// 锟斤拷锟斤拷锟斤拷锟�
				ps = ct
						.prepareStatement("insert into cprac_blocks(qpracqid,blockid ) values(  ?,? )");
				ps.setInt(1, pqiid);
				ps.setInt(2, q.getEpblock().getId());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void intomycourseprac(MyEprac eprac) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into cprac_quizinfo(userid,ppid,starttime,classid) values(?,?,?,?)");
			ps.setInt(1, eprac.getTester().getId());
			ps.setInt(2, eprac.getPrac().getId());
			ps.setLong(3, eprac.getStarttime());
			ps.setInt(4, eprac.getClassid());
			ps.executeUpdate();
			// if ("oracle".equals(SystemConfOp
			// .getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
			ps = ct
					.prepareStatement("select cprac_quizinfo_sequence.currval from dual ");
			rs = ps.executeQuery();
			// }
			if (rs.next())
				eprac.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void submitcpracPaper(MyExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update cprac_quizinfo set passtime = ?,endtime=? where id = ?");
			ps.setInt(1, examPaper.getPassTime());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, examPaper.getId());
			ps.executeUpdate();
			ps.close();
			ps = null;
			ps = ct.prepareStatement("call cprac_setscore(?,?,?)");
			ps.setInt(1, examPaper.getId());
			ps.setInt(2, examPaper.getExamPaper().getId());
			ps.setInt(3, examPaper.getRecordid());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addCpracPaper_record(MyExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into cprac_quizinfo_record(sqid,begintime,status) values(?,?,0)");
			ps.setInt(1, examPaper.getId());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('course') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select cprac_quizinfo_record_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("锟斤拷菘锟斤拷锟斤拷锟斤拷锟斤拷锟�,锟斤拷确锟斤拷锟角凤拷为oracle,mysql锟斤拷锟斤拷sqlserver锟斤拷菘狻�");
				throw new ElException("锟斤拷菘锟斤拷锟斤拷锟斤拷锟斤拷螅。锟斤拷锟�");
			}
			if (rs.next())
				examPaper.setRecordid(rs.getInt(1));
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void updateCpracPaper_record(MyExamPaper examPaper)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update cprac_quizinfo_record set endtime =? ,myscore=?,status=1 where id =?");
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setFloat(2, examPaper.getMyScore());
			ps.setInt(3, examPaper.getRecordid());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void updatecpracQuestion(int pqiid, Question q) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update cprac_questions set myanswer =? where qpracqid=? and blockid=? and qid =?");
			ps.setString(1, q.getStuAnswer());
			ps.setInt(2, pqiid);
			ps.setInt(3, q.getEpblock().getId());
			ps.setInt(4, q.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int getbandingeroom(MyExamPaper mep) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eroomid  from class_course where classid =? and courseid =? and binding = 1");
			ps.setInt(1, mep.getCourse().getClassid());
			ps.setInt(2, mep.getCourse().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟叫憋拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	// 锟斤拷询锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟轿程匡拷锟斤拷锟斤拷锟斤拷训锟洁考锟斤拷锟斤拷锟斤拷锟斤拷源锟斤拷锟斤拷锟斤拷锟�
	public int getExamRoomType(String where) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select type from exam_room " + where);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆筹拷锟斤拷锟斤拷锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟侥撅拷锟斤拷锟斤拷冒锟斤拷锟街碉拷锟�
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_marking(int mepid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call sr_setscore_marking(?)");
			ps.setInt(1, mepid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟侥撅拷锟斤拷锟斤拷冒锟斤拷锟街碉拷瘸锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷取锟斤拷学员某锟斤拷锟斤拷锟斤拷锟斤拷锟叫硷拷录
	 * 
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public List<MyRoomRecord> listStudyRoomRecord(int userid, int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoomRecord> mrrList = new ArrayList<MyRoomRecord>();
		MyRoomRecord mrr = null;
		// try {
		// ct = DBConnection.getConnection();
		// ps = ct.prepareStatement("select
		// id,userid,roomid,ispassed,status,myscore from study_room_record where
		// userid=? and roomid=? order by id ");
		// ps.setInt(1, userid);
		// ps.setInt(2, roomid);
		// rs=ps.executeQuery();
		// while(rs.next()){
		// mrr=new MyRoomRecord();
		// mrr.setId(rs.getInt(1));
		// mrr.setElUser(new ELUser(rs.getInt(2)));
		// mrr.setExamRoom(new ExamRoom(rs.getInt(3)));
		// mrr.setIspassed(rs.getInt(4));
		// mrr.setStatus(rs.getInt(5));
		// mrr.setMyScore(rs.getFloat(6));
		// mrrList.add(mrr);
		// }
		// } catch (Exception e) {
		// logger.error("锟斤拷取锟斤拷学员某锟斤拷锟斤拷锟斤拷锟斤拷锟叫硷拷录锟斤拷锟�?", e);
		// throw new ElException(e);
		// } finally {
		// DBConnection.closeConnectInfo(ct, ps, rs);
		// }
		return mrrList;
	}

	/**
	 * 锟斤拷取锟斤拷学员某锟斤拷锟斤拷锟斤拷锟斤拷锟叫硷拷录(锟斤拷锟斤拷)
	 * 
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public List<MyRoomRecord> listStudyRoomRecordSqinfo(int userid, int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoomRecord> mrrList = new ArrayList<MyRoomRecord>();
		MyRoomRecord mrr = null;
		// try {
		// ct = DBConnection.getConnection();
		// ps = ct.prepareStatement("select
		// id,userid,roomid,ispassed,status,myscore from study_room_record where
		// userid=? and roomid=? order by id ");
		// ps.setInt(1, userid);
		// ps.setInt(2, roomid);
		// rs=ps.executeQuery();
		// while(rs.next()){
		// mrr=new MyRoomRecord();
		// mrr.setId(rs.getInt(1));
		// mrr.setElUser(new ELUser(rs.getInt(2)));
		// mrr.setExamRoom(new ExamRoom(rs.getInt(3)));
		// mrr.setIspassed(rs.getInt(4));
		// mrr.setStatus(rs.getInt(5));
		// mrr.setMyScore(rs.getFloat(6));
		// mrr.setMyExamPapers(this.listStudyQuizInfo(userid, roomid,
		// mrr.getId()));
		// mrrList.add(mrr);
		// }
		// } catch (Exception e) {
		// logger.error("锟斤拷取锟斤拷学员某锟斤拷锟斤拷锟斤拷锟斤拷锟叫硷拷录(锟斤拷锟斤拷)锟斤拷锟�?", e);
		// throw new ElException(e);
		// } finally {
		// DBConnection.closeConnectInfo(ct, ps, rs);
		// }
		return mrrList;
	}

	/**
	 * 锟斤拷取某学员锟斤拷某锟斤拷锟斤拷某锟斤拷录锟侥达拷锟�
	 * 
	 * @param userid
	 * @param roomid
	 * @param mrrid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listStudyQuizInfo(int userid, int roomid, int mrrid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select sqi.id sqid, sqi.myScore,sqi.endtime,sqi.status ,sqi.ispassed,eprs.id epid1,eprs.title  from ( select * from study_exampaper erps1  left join exampaper ep1 on ep1.id = erps1.epid where erps1.roomid =? and erps1.userid=?) eprs left  join (select * from study_quizinfo  where  userid = ?) sqi on eprs.id = sqi.epid and eprs.roomid = sqi.roomid order by eprs.id ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, userid);
			// ps.setInt(4, mrrid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setMyScore(rs.getFloat(2));
				mep.setEndtime(rs.getTimestamp(3));
				mep.setStatus(rs.getInt(4));
				mep.setIspassed(rs.getInt(5));
				mep.setExamPaper(new ExamPaper(rs.getInt(6), rs.getString(7)));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取某学员锟斤拷某锟斤拷锟斤拷某锟斤拷录锟侥达拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	/**
	 * 锟斤拷锟窖г憋拷锟斤拷锟斤拷募锟铰�(锟斤拷锟截此讹拷锟斤拷)
	 * 
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public MyRoomRecord addStudyRoomRecord(int userid, int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoomRecord myRoomRecord = new MyRoomRecord();
		// try {
		// ct = DBConnection.getConnection();
		// ps = ct.prepareStatement("insert into
		// study_room_record(userid,roomid) values(?,?)");
		// ps.setInt(1, userid);
		// ps.setInt(2, roomid);
		// ps.executeUpdate();
		// ps.close();
		// ps = ct.prepareStatement("select study_room_record_sequence.currval
		// from dual ");
		// rs=ps.executeQuery();
		// if(rs.next()){
		// myRoomRecord.setId(rs.getInt(1));
		// myRoomRecord.setExamRoom(new ExamRoom(roomid));
		// //锟斤拷锟斤拷锟斤拷锟绞泵伙拷锟� 锟斤拷省锟斤拷
		// }
		// } catch (Exception e) {
		// logger.error("锟斤拷锟窖г憋拷锟斤拷锟斤拷募锟铰硷拷锟斤拷?", e);
		// throw new ElException(e);
		// } finally {
		// DBConnection.closeConnectInfo(ct, ps, rs);
		// }
		return myRoomRecord;
	}

	/**
	 * 锟斤拷锟窖г憋拷锟斤拷锟斤拷锟皆撅拷锟铰�
	 * 
	 * @param userid
	 * @param epid
	 * @param roomid
	 * @param classid
	 * @param isdel
	 * @throws ElException
	 */
	public void addStudyExamPaper(int userid, int epid, int roomid,
			int classid, int isdel) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_exampaper(userid,epid,roomid,classid,isdel) values(?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, epid);
			ps.setInt(3, roomid);
			ps.setInt(4, classid);
			ps.setInt(5, isdel);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟窖г憋拷锟斤拷锟斤拷锟皆撅拷锟铰硷拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟窖г憋拷锟斤拷锟斤拷锟皆撅拷锟铰�
	 * 
	 * @param userid
	 * @param epid
	 * @throws ElException
	 */
	public void addStudyExamPaper(int userid, int epid, int roomid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_exampaper(userid,epid,roomid,classid) values(?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, epid);
			ps.setInt(3, roomid);
			ps.setInt(4, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟窖г憋拷锟斤拷锟斤拷锟皆撅拷锟铰硷拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员锟角凤拷锟斤拷锟斤拷烁锟斤拷跃锟�
	 * 
	 * @param userid
	 * @param epid
	 * @param roomid
	 * @param classid
	 * @throws ElException
	 */
	public boolean checkStudyExamPaper(int userid, int epid, int roomid,
			int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_exampaper where userid=? and epid=? and roomid=? and classid=?");
			ps.setInt(1, userid);
			ps.setInt(2, epid);
			ps.setInt(3, roomid);
			ps.setInt(4, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟角凤拷锟斤拷锟斤拷烁锟斤拷跃锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 锟斤拷取学员锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷跃锟�
	 */
	public List<ExamPaper> listStudyExamPaper(int userid, int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epid from study_exampaper where userid=? and roomid=? ");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			ExamPaper ep = null;
			while (rs.next()) {
				ep = new ExamPaper(rs.getInt(1));
				eps.add(ep);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷跃锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}

	/**
	 * 锟斤拷取学员锟侥匡拷锟斤拷锟侥参加凤拷式
	 * 
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getStudyEroomJoinway(int userid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select joinway from study_room where userid=? and roomid=? ");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取学员锟侥匡拷锟斤拷锟侥参加凤拷式锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 2;
	}

	/**
	 * 锟斤拷取学员锟斤拷锟斤拷锟侥硷拷录锟斤拷
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getMyEroomRecordCount(int roomid, int userid) throws ElException {
		// PreparedStatement ps = null;
		// ResultSet rs = null;
		// Connection ct = null;
		// try {
		// ct = DBConnection.getConnection();
		// ps = ct.prepareStatement("select count(id) from study_room_record
		// where roomid=? and userid=?");
		// ps.setInt(1, roomid);
		// ps.setInt(2, userid);
		// rs = ps.executeQuery();
		// if (rs.next()) {
		// return rs.getInt(1);
		// }
		// } catch (Exception e) {
		// logger.error("锟斤拷取学员锟斤拷锟斤拷锟侥硷拷录锟斤拷锟斤拷?", e);
		// throw new ElException(e);
		// } finally {
		// DBConnection.closeConnectInfo(ct, ps, rs);
		// }
		return 0;
	}

	/**
	 * 锟斤拷锟斤拷学员锟斤拷锟斤拷状态(锟斤拷锟斤拷锟皆撅拷状态)
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public void setStudyEroomStatus(int roomid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// 锟饺诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟窖г憋拷锟斤拷锟斤拷跃锟�
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epid from study_exampaper where roomid=? and userid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				this.setMyExamPaperStatus(roomid, rs.getInt(1), userid);// 锟斤拷锟斤拷学员锟皆撅拷状态
			}
			// 锟斤拷锟斤拷学员锟斤拷锟斤拷状态
			this.setMyRoomStatus(roomid, userid);
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷状态(锟斤拷锟斤拷锟皆撅拷状态)锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员锟斤拷锟斤拷状态
	 * 
	 * @param roomid
	 * @param userid
	 * @throws ElException
	 */
	private void setMyRoomStatus(int roomid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int status = 0;
		// 状态说锟斤拷
		// 0锟斤拷缺锟斤拷
		// 1锟斤拷未锟斤拷锟斤拷
		// 2锟斤拷锟斤拷锟斤拷锟疥：锟斤拷锟斤拷锟疥、全锟斤拷未锟斤拷锟斤拷
		// 3锟斤拷锟斤拷锟斤拷锟叫ｏ拷锟斤拷锟斤拷锟疥、锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		// 4锟斤拷锟斤拷锟斤拷锟侥ｏ拷锟斤拷锟斤拷锟疥，全锟斤拷锟斤拷锟斤拷锟斤拷
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select count(*) t1count from study_exampaper where userid=? and roomid=? and status=0) t1,"
							+ "(select count(*) t2count from study_exampaper where userid=? and roomid=? and status=1 and isdel!=1) t2,"
							+ "(select count(*) t3count from study_exampaper where userid=? and roomid=? and status=2 and isdel!=1) t3,"
							+ "(select count(*) t4count from study_exampaper where userid=? and roomid=? and status=3 and isdel!=1) t4");
			for (int i = 0; i < 4; i++) {
				ps.setInt(i * 2 + 1, userid);
				ps.setInt(i * 2 + 2, roomid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 0 && rs.getInt(2) == 0 && rs.getInt(3) == 0
						&& rs.getInt(4) > 0) {// 锟斤拷锟斤拷锟斤拷
					status = 4;
				} else if (rs.getInt(1) == 0 && rs.getInt(2) == 0
						&& rs.getInt(3) > 0 && rs.getInt(4) == 0) {// 锟斤拷锟斤拷锟斤拷
					status = 2;
				} else {
					if (rs.getInt(1) == 0 && rs.getInt(2) == 0
							&& rs.getInt(3) >= 0 && rs.getInt(4) > 0) {// 锟斤拷锟斤拷锟斤拷锟侥碉拷
						status = 3;
					} else if (rs.getInt(1) == 0 && rs.getInt(2) == 0
							&& rs.getInt(3) == 0 && rs.getInt(4) == 0) {// 缺锟斤拷
						status = 0;
					} else {
						status = 1;
					}
				}
			}
			this.updateStudyEroomStatus(roomid, userid, status);
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷状态锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员锟斤拷锟斤拷状态
	 * 
	 * @param roomid
	 * @param epid
	 * @param userid
	 * @param status
	 * @throws ElException
	 */
	public void updateStudyEroomStatus(int roomid, int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_room set status=? where userid=? and roomid=?");
			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷状态锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员锟皆撅拷状态
	 * 
	 * @param roomid
	 * @param epid
	 * @param userid
	 * @throws ElException
	 */
	private void setMyExamPaperStatus(int roomid, int epid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int status = 0;
		// 状态说锟斤拷
		// 0.未锟轿硷拷 1.锟斤拷锟斤拷锟斤拷 2.锟斤拷锟斤拷锟斤拷 3.锟斤拷锟斤拷锟斤拷
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select count(*) t1count from study_quizinfo where userid=? and epid=? and roomid=? and status=0) t1,"
							+ "(select count(*) t2count from study_quizinfo where userid=? and epid=? and roomid=? and status=1) t2,"
							+ "(select count(*) t3count from study_quizinfo where userid=? and epid=? and roomid=? and status=2) t3,"
							+ "(select count(*) t4count from study_quizinfo where userid=? and epid=? and roomid=? and status=3) t4");
			for (int i = 0; i < 4; i++) {
				ps.setInt(i * 3 + 1, userid);
				ps.setInt(i * 3 + 2, epid);
				ps.setInt(i * 3 + 3, roomid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(4) > 0) {// 锟斤拷锟斤拷锟斤拷锟侥碉拷
					status = 3;
				} else if (rs.getInt(3) > 0) {// 锟斤拷锟斤拷锟斤拷锟斤拷锟�
					status = 2;
				} else if (rs.getInt(2) > 0) {// 锟叫匡拷锟斤拷锟叫碉拷
					status = 1;
				}
			}
			this.updateStudyExamPaperStatus(roomid, epid, userid, status);
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟皆撅拷状态锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员锟皆撅拷状态
	 * 
	 * @param roomid
	 * @param epid
	 * @param userid
	 * @param status
	 * @throws ElException
	 */
	public void updateStudyExamPaperStatus(int roomid, int epid, int userid,
			int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_exampaper set status=? where userid=? and roomid=? and epid=?");
			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			ps.setInt(4, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟皆撅拷状态锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员锟皆撅拷状态
	 * 
	 * @param roomid
	 * @param epid
	 * @param userid
	 * @param status
	 * @throws ElException
	 */
	public void updateStudyQuizInfoStatus(int roomid, int epid, int userid,
			int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set status=? where userid=? and roomid=? and epid=?");
			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			ps.setInt(4, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟皆撅拷状态锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员锟皆撅拷锟斤拷锟斤拷锟�
	 * 
	 * @param userid
	 * @param roomid
	 * @param epid
	 * @throws ElException
	 */
	public void setStudyExampaperQuizcount(int userid, int roomid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_exampaper set quizcount=(select count(id) from study_quizinfo where roomid=? and userid=? and epid=?) where roomid=? and userid=? and epid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, epid);
			ps.setInt(4, roomid);
			ps.setInt(5, userid);
			ps.setInt(6, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟皆撅拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟窖г憋拷锟斤拷约锟铰�(学员锟斤拷习锟斤拷锟皆硷拷锟轿筹拷锟铰斤拷锟斤拷习锟斤拷录)
	 * 
	 * @param sqid
	 * @throws ElException
	 */
	public int addStudyQuizinfoRecord(int sqid, String tableName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into " + tableName
					+ "(sqid,begintime,status) values(?,?,?)");
			ps.setInt(1, sqid);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, 1);
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("select " + tableName
					+ "_sequence.currval from dual");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟窖г憋拷锟斤拷约锟铰�(学员锟斤拷习锟斤拷锟皆硷拷锟轿筹拷锟铰斤拷锟斤拷习锟斤拷录)锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷锟斤拷学员锟斤拷锟皆硷拷录(学员锟斤拷习锟斤拷锟皆硷拷锟轿筹拷锟铰斤拷锟斤拷习锟斤拷录)状态
	 * 
	 * @param sqid
	 * @param status
	 * @param endtime
	 * @param tableName
	 * @throws ElException
	 */
	public void updateStudyQuizinfoRecordStatus(int id, int status,
			Timestamp endtime, String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update " + tableName
					+ " set status=?,endtime=? where id=?");
			ps.setInt(1, status);
			ps.setTimestamp(2, endtime);
			ps.setInt(3, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟皆硷拷录(学员锟斤拷习锟斤拷锟皆硷拷锟轿筹拷锟铰斤拷锟斤拷习锟斤拷录)状态锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟铰革拷学员锟斤拷锟叫斤拷锟斤拷时锟斤拷为锟秸的匡拷锟皆硷拷录(学员锟斤拷习锟斤拷锟皆硷拷锟轿筹拷锟铰斤拷锟斤拷习锟斤拷录)
	 * 
	 * @param userid
	 * @param endtime
	 * @param tableName
	 * @throws ElException
	 */
	public void updateStudyQuizinfoRecordStatus(int userid, Timestamp endtime,
			String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update "
							+ tableName
							+ " set status=?,endtime=? where sqid in(select id from study_quizinfo where userid=?) and endtime is null");
			ps.setInt(1, 0);
			ps.setTimestamp(2, endtime);
			ps.setInt(3, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟铰革拷学员锟斤拷锟叫斤拷锟斤拷时锟斤拷为锟秸的匡拷锟皆硷拷录(学员锟斤拷习锟斤拷锟皆硷拷锟轿筹拷锟铰斤拷锟斤拷习锟斤拷录)锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷涌锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷锟剿的匡拷锟斤拷学员锟斤拷锟斤拷锟斤拷息
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public void addStudyRoomApply(int roomid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_room_apply(roomid,userid,status,createtime) values(?,?,?,?)");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, 1);
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.error("锟斤拷涌锟斤拷锟斤拷锟斤拷锟斤拷锟揭拷锟剿的匡拷锟斤拷学员锟斤拷锟斤拷锟斤拷息锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟窖г憋拷欠锟斤拷丫锟斤拷锟斤拷锟�
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudyRoomApply(int roomid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_room_apply where roomid = ? and userid = ?");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟窖г憋拷欠锟斤拷丫锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 锟斤拷锟斤拷学员锟斤拷锟斤拷锟斤拷锟斤拷状态
	 * 
	 * @param erid
	 * @param epid
	 * @param delStatus
	 * @throws ElException
	 */
	public void udpateStudyRoomApplyStatus(int roomid, int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_room_apply set status=? where roomid = ? and userid =?");
			ps.setInt(1, status);
			ps.setInt(2, roomid);
			ps.setInt(3, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷锟斤拷锟斤拷状态锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟斤拷锟斤拷学员锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞憋拷锟�
	 * 
	 * @param userid
	 * @param roomid
	 * @param begintime
	 * @throws ElException
	 */
	public void updateStudyExamBegintime(int userid, int roomid,
			Timestamp begintime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_room set begintime=? where userid=? and roomid=?");
			ps.setTimestamp(1, begintime);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷学员锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞憋拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 取锟矫客癸拷锟斤拷拇锟斤拷锟矫凤拷 锟酵癸拷锟斤拷锟叫碉拷选2锟斤拷锟斤拷选4锟斤拷锟叫讹拷1锟斤拷锟绞硷拷9锟斤拷锟斤拷锟斤拷10锟斤拷锟斤拷锟斤拷8锟斤拷锟斤拷锟�5 type in 1,2,4,5,8,9,10
	public float getMyExamPapermepKscore(int sqid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		float mepKscore = 0.0f;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select sum(sb.myscore) from study_blocks sb , exampaperblock epb where sb.blockid=epb.id and sb.sqid=? and epb.type in (1,2,4,5,8,9,10)");

			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				mepKscore = rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟皆撅拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mepKscore;
	}

	/**
	 * 锟斤拷锟斤拷欠锟斤拷丫锟斤拷木锟�
	 * 
	 * @param sqid
	 * @return
	 * @throws ElException
	 */
	public boolean checkStudy_score(int userId, int sqid, int blockid, int qid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select sqid from multiUserPapers mp where mp.userid=? and mp.sqid=? and mp.blockid=? and mp.qid=? ");

			ps.setInt(1, userId);
			ps.setInt(2, sqid);
			ps.setInt(3, blockid);
			ps.setInt(4, qid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷丫锟斤拷木锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 锟侥撅拷锟斤拷峤伙拷锟斤拷锟�
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_scoreAdd(int userId, int sqid, int Blockid, int Qid,
			float Score) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into multiUserPapers(userId,sqid,Blockid,Qid,Score) VALUES(?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, sqid);
			ps.setInt(3, Blockid);
			ps.setInt(4, Qid);
			ps.setFloat(5, Score);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟结交锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟侥撅拷锟斤拷锟铰凤拷锟斤拷
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_scoreUpdate(int userId, int sqid, int Blockid, int Qid,
			float Score) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update multiUserPapers set Score=? where userid=? and sqid=? and blockid=? and qid=? ";
			ps = ct.prepareStatement(sql);
			ps.setFloat(1, Score);
			ps.setInt(2, userId);
			ps.setInt(3, sqid);
			ps.setInt(4, Blockid);
			ps.setInt(5, Qid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟侥撅拷锟斤拷锟铰凤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	
	/**
	 * 锟侥撅拷锟斤拷峤伙拷锟斤拷锟�
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_piyuAdd(int userId, int sqid, int Blockid, int Qid,
			String piyu) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into multiUserPapers(userId,sqid,Blockid,Qid,piyu) VALUES(?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, sqid);
			ps.setInt(3, Blockid);
			ps.setInt(4, Qid);
			ps.setString(5, piyu);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟结交锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 锟侥撅拷锟斤拷锟铰凤拷锟斤拷
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public void study_piyuUpdate(int userId, int sqid, int Blockid, int Qid,
			String piyu) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update multiUserPapers set piyu=? where userid=? and sqid=? and blockid=? and qid=? ";
			ps = ct.prepareStatement(sql);
			ps.setString(1,piyu);
			ps.setInt(2, userId);
			ps.setInt(3, sqid);
			ps.setInt(4, Blockid);
			ps.setInt(5, Qid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟侥撅拷锟斤拷锟铰凤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
	/**
	 * 锟叫断碉拷前锟矫伙拷锟斤拷锟酵ｏ拷锟斤拷通锟侥撅拷锟斤拷员锟斤拷锟斤拷锟侥撅拷锟介长锟酵筹拷锟斤拷锟斤拷锟斤拷员锟斤拷
	 * 
	 * @param userid
	 * @param depid
	 * @throws ElException
	 */
	public int study_isLeader(int userId, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int n = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select isLeader from exam_rappraises where userId=? and roomid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				n = rs.getInt(1);
			}

		} catch (Exception e) {
			logger.error("锟叫断碉拷前锟矫伙拷锟斤拷锟酵筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return n;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷未锟斤拷锟侥达拷锟斤拷锟斤拷锟�
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getEroomAllQuizcount(int roomid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_quizinfo sqi where sqi.roomid=? and sqi.epid=? and sqi.status=2 ");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷未锟斤拷锟侥达拷锟斤拷锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取锟矫伙拷锟斤拷锟侥达拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getUserReadexampaperCount(int roomid, int epid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(distinct sqi.id) from multiUserPapers mup inner join study_quizinfo sqi on mup.sqid=sqi.id where sqi.roomid=? and sqi.epid=? and mup.userid=? and sqi.status=2 ");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟矫伙拷锟斤拷锟侥达拷锟斤拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取一锟斤拷未锟斤拷锟侥的达拷?状态为锟斤拷锟斤拷锟斤拷
	 * 
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public int getStudyExamPaper(int myExamPaperid, int roomid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			int tempid = getStudyExamPaper_(myExamPaperid, roomid, epid);
			if (tempid > 0) {
				return tempid;
			}
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from study_quizinfo where roomid=? and epid=? and status=2 order by id asc ");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int myepid = rs.getInt(1);
				// if(myepid!=myExamPaperid){
				return myepid;
				// }
			}
		} catch (Exception e) {
			logger.error("锟斤拷取一锟斤拷未锟斤拷锟侥的达拷?状态为锟斤拷锟斤拷锟金）筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取一锟斤拷未锟斤拷锟侥的达拷?状态为锟斤拷锟斤拷锟斤拷,id锟斤拷锟节碉拷前锟皆撅拷)
	 * 
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public int getStudyExamPaper_(int myExamPaperid, int roomid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from study_quizinfo where roomid=? and epid=? and status=2 and id>? order by id asc ");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			ps.setInt(3, myExamPaperid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取一锟斤拷未锟斤拷锟侥的达拷?状态为锟斤拷锟斤拷锟斤拷,id锟斤拷锟节碉拷前锟皆�?锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取一锟斤拷未锟斤拷锟侥的达拷?状态为锟斤拷锟斤拷锟斤拷
	 * 
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public int getStudyExamPaper(int myExamPaperid, int roomid, int epid,
			int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			int tempid = getStudyExamPaper_(myExamPaperid, roomid, epid, userid);
			if (tempid > 0) {
				return tempid;
			}
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select sqi.id from study_quizinfo sqi
			// inner join multiuserpapers mup on sqi.id=mup.sqid where
			// sqi.roomid=? and sqi.epid=? and sqi.status=2 and mup.userid=?
			// order by id asc ");
			ps = ct
					.prepareStatement("select sqi.id from study_quizinfo sqi left join multiuserpapers mup on sqi.id=mup.sqid where sqi.roomid=? and sqi.epid=? and sqi.status=2 and mup.sqid is null order by id asc ");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			// ps.setInt(3, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int myepid = rs.getInt(1);
				// if(myepid!=myExamPaperid){
				return myepid;
				// }
			}
		} catch (Exception e) {
			logger.error("锟斤拷取一锟斤拷未锟斤拷锟侥的达拷?状态为锟斤拷锟斤拷锟金）筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 锟斤拷取一锟斤拷未锟斤拷锟侥的达拷?状态为锟斤拷锟斤拷锟斤拷,id锟斤拷锟节碉拷前锟皆撅拷)
	 * 
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public int getStudyExamPaper_(int myExamPaperid, int roomid, int epid,
			int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sqi.id from study_quizinfo sqi left join multiuserpapers mup on sqi.id=mup.sqid where sqi.roomid=? and sqi.epid=? and sqi.status=2 and sqi.id>? and mup.sqid is null order by id asc ");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			ps.setInt(3, myExamPaperid);
			// ps.setInt(4, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取一锟斤拷未锟斤拷锟侥的达拷?状态为锟斤拷锟斤拷锟斤拷,id锟斤拷锟节碉拷前锟皆�?锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public String checkPassEroomeps(ErPara erpara, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb = new StringBuffer();
		try {
			ct = DBConnection.getConnection();
			int roomid = erpara.getExamRoom().getId();
			int epid = erpara.getExamPaper().getId();
			if (erpara.getIsPassed() != -1) {// 锟叫讹拷锟角凤拷通锟斤拷
				ps = ct
						.prepareStatement("select * from study_exampaper where roomid = "
								+ roomid
								+ " and userid = "
								+ userid
								+ " and epid = "
								+ epid
								+ " and ispassed = "
								+ (erpara.getIsPassed() == 1 ? 1 : 0));
				rs = ps.executeQuery();
				if (!rs.next()) {
					sb.append("锟斤拷锟斤拷锟皆撅拷通锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷希锟�");
				}
				rs.close();
				ps.close();
			}
			ps = ct
					.prepareStatement("select * from study_exampaper where roomid = "
							+ roomid
							+ " and userid = "
							+ userid
							+ " and epid = "
							+ epid
							+ " and quizcount "
							+ erpara.getExamCountTerm()
							+ " "
							+ erpara.getExamCount());
			rs = ps.executeQuery();
			if (!rs.next()) {
				sb.append("锟斤拷锟斤拷锟皆�?锟皆达拷锟斤拷锟较ｏ拷");
			}
			rs.close();
			ps.close();
			ps = ct
					.prepareStatement("select * from study_exampaper where roomid = "
							+ roomid
							+ " and userid = "
							+ userid
							+ " and epid = "
							+ epid
							+ " and avgscore "
							+ erpara.getAvgScoreTerm()
							+ " "
							+ erpara.getAvgScore());
			rs = ps.executeQuery();
			if (!rs.next()) {
				sb.append("锟斤拷锟斤拷锟皆�?锟斤拷平锟斤拷植锟斤拷锟较ｏ拷");
			}
			rs.close();
			ps.close();
			ps = ct
					.prepareStatement("select * from study_exampaper where roomid = "
							+ roomid
							+ " and userid = "
							+ userid
							+ " and epid = "
							+ epid
							+ " and maxscore "
							+ erpara.getMaxScoreTerm()
							+ " "
							+ erpara.getMaxScore());
			rs = ps.executeQuery();
			if (!rs.next()) {
				sb.append("锟斤拷锟斤拷锟皆�?锟斤拷锟斤拷叻植锟斤拷锟较ｏ拷");
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			logger.error("", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sb.length() <= 0 ? "锟斤拷锟�" : sb.toString();
	}

	public String checkPassErooms(ErPara erpara, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer sb = new StringBuffer();
		try {
			ct = DBConnection.getConnection();
			int roomid = erpara.getExamRoom().getId();
			if (erpara.getIsPassed() != -1) {// 锟叫讹拷锟角凤拷通锟斤拷
				ps = ct
						.prepareStatement("select * from study_room where roomid = "
								+ roomid
								+ " and userid = "
								+ userid
								+ " and ispassed = "
								+ (erpara.getIsPassed() == 1 ? 1 : 0));
				rs = ps.executeQuery();
				if (!rs.next()) {
					sb.append("锟斤拷锟斤拷通锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷希锟�");
				}
				rs.close();
				ps.close();
			}
			ps = ct.prepareStatement("select * from study_room where roomid = "
					+ roomid + " and userid = " + userid + " and myscore "
					+ erpara.getExamScoreTerm() + " " + erpara.getExamScore());
			rs = ps.executeQuery();
			if (!rs.next()) {
				sb.append("锟斤拷锟斤拷锟缴硷拷锟斤拷锟斤拷锟斤拷锟斤拷希锟�");
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			logger.error("", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sb.length() <= 0 ? "锟斤拷锟�" : sb.toString();
	}

	public void setquizinfo(int sqid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call setquizinfo(?)");
			ps.setInt(1, sqid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Question> listQuizQuestions(int sqid, int blockid, int pN)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from (select q.id,q.title ,q.content,q.subject,q.qexplain,"
							+ " q.qlevel,q.answer,q.qtype, epq.myanswer,epq.sortid,epq.myscore,q.scoreper,q.fasheng_question "
							+ " from study_questions epq  "
							+ " left join question q on epq.qid = q.id where epq.blockid = ? and epq.sqid =?  and q.parentid = 0 order by epq.sortid asc)t where rownum<=?) where rn>?");
			ps.setInt(1, blockid);
			ps.setInt(2, sqid);
			ps.setInt(3, pN * 10 + 10);
			ps.setInt(4, pN * 10);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlevel(rs.getInt(6));
				q.setAnswer(rs.getString(7));
				q.setQtype(rs.getInt(8));
				q.setStuAnswer(rs.getString(9));
				q.setSortid(rs.getInt(10));
				q.setMyScore(rs.getFloat(11));
				if (q.getQtype() == 7) {
					// 锟斤拷锟斤拷锟斤拷小锟斤拷锟饺★拷锟�
					q.setChilds(listQuizQuestions_(sqid, blockid, q.getId()));
				}
				// q.setMystatus(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(blockid));
				q.setRulestring(getQRulestrByREBid(sqid, q));
				q.setScoreper(rs.getInt("scoreper"));
				q
						.setMultiUserPapers(listUserMarkInfo(sqid, blockid, q
								.getId()));
				q.setFashengQuestion(rs.getString(13));
				qs.add(q);
			}
			ps.close();
			ps = null;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷小锟斤拷
	 * 
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	private List<Question> listQuizQuestions_(int sqid, int blockid,
			int parentid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Question> qs = new ArrayList<Question>();
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select q.id,q.title ,q.content,q.subject,q.qexplain,"
							+ "q.qlevel,q.answer,q.qtype, epq.myanswer,epq.sortid,epq.myscore,q.scoreper from study_questions epq  "
							+ "left join question q on epq.qid = q.id where epq.blockid = ? and epq.sqid =?  and q.parentid = ? order by epq.sortid asc");
			ps.setInt(1, blockid);
			ps.setInt(2, sqid);
			ps.setInt(3, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Question q = new Question(rs.getInt(1), rs.getString(2));
				q.setContent(rs.getString(3));
				q.setSubject(rs.getString(4));
				q.setQexplain(rs.getString(5));
				q.setQlevel(rs.getInt(6));
				q.setAnswer(rs.getString(7));
				q.setQtype(rs.getInt(8));
				q.setStuAnswer(rs.getString(9));
				q.setSortid(rs.getInt(10));
				q.setMyScore(rs.getFloat(11));
				if (q.getQtype() == 7) {
					// 锟斤拷锟斤拷锟斤拷小锟斤拷锟饺★拷锟�
					q.setChilds(listQuizQuestions(sqid, blockid, q.getId()));
				}
				// q.setMystatus(rs.getInt(14));
				q.setEpblock(new ExamPaperBlock(blockid));
				q.setRulestring(getQRulestrByREBid(sqid, q));
				q.setScoreper(rs.getInt("scoreper"));
				q
						.setMultiUserPapers(listUserMarkInfo(sqid, blockid, q
								.getId()));
				qs.add(q);
			}
			ps.close();
			ps = null;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷锟饺★拷锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return qs;
	}

	/**
	 * 锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷锟�
	 * 
	 * @param uid
	 * @param roomid
	 * @param epid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean hasInQuizPaper(int uid, int roomid, int epid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_quizinfo where  userid = ? and roomid = ? and epid = ? and classid=?");
			ps.setInt(1, uid);
			ps.setInt(2, roomid);
			ps.setInt(3, epid);
			ps.setInt(4, classid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷猿锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<Integer> getuserid(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Integer> ids = new ArrayList<Integer>();
		try {
			int userid = 0;
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_exampaper where roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {

				userid = rs.getInt("userid");
				ids.add(userid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ids;
	}

	/**
	 * 锟窖伙拷学锟街的课筹拷锟斤拷
	 * 
	 * @param userid
	 * @param classid
	 * @param classType
	 *            锟斤拷训锟斤拷目纬锟斤拷锟斤拷锟斤拷锟斤拷? 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE_AT 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getKc_courseXF(int userid, int classid, String classType)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			if (classType.equals("CLASS_COURSE_AT")) {// 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷
				sql = " select count(*) from (select  cc.courseid   from CLASS_COURSE_at cc  ,study_course sc ,study_quizinfo sqi "
						+ " where   sc.classid = cc.classid and sc.courseid = cc.courseid and sqi.id=sc.sqiid "
						+ " and sqi.userid = sc.userid  and sqi.roomid = cc. eroomid and cc.classid = ? and sc.userid = ? "
						+ "  and ((cc.getcredit = 2 and sc.passed = 1 )or (cc.getcredit = 2 and sqi.ispassed = 1) "
						+ "or (cc.getcredit = 1 and sc.passed != 0)) group by cc.courseid)";
			} else {
				sql = " select count(*) from (select  cc.courseid   from CLASS_COURSE cc  ,study_course sc ,study_quizinfo sqi "
						+ " where   sc.classid = cc.classid and sc.courseid = cc.courseid and sqi.id=sc.sqiid "
						+ " and sqi.userid = sc.userid  and sqi.roomid = cc. eroomid  and cc.classid = ? and sc.userid = ? "
						+ " and ((cc.getcredit = 2 and sc.passed = 1 )or (cc.getcredit = 2 and sqi.ispassed = 1)"
						+ " or (cc.getcredit = 1 and sc.passed != 0))  group by cc.courseid)";
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟窖伙拷学锟街的课筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	// ------------------------锟斤拷锟较低筹拷锟绞硷拷锟�
	/**
	 * 锟斤拷锟皆成硷拷锟接分碉拷 平锟斤拷杉锟�
	 * 
	 * @param userid
	 * @param classid
	 * @param classType
	 *            锟斤拷训锟斤拷目纬锟斤拷锟斤拷锟斤拷锟斤拷? 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE_AT 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public float getKC_CJ_AVG(int userid, int classid, String classType)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		float averageGrade = 0;
		int i = 0;// 锟斤拷锟斤拷
		try {
			String sql = "";
			if (classType.equals("CLASS_COURSE_AT")) {
				sql = "select sqi.myScore from (select * from CLASS_COURSE_AT where classid =?   and userid = ? and getcredit = 2 ) cc "
						+ "left join study_course sc  on cc.courseid = sc.courseid left join course c on sc.courseid = c.id "
						+ "left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid "
						+ "left join exam_room er on sqi.roomid=er.id where sc.userid =? and sc.classid=? "; // and
				// sqi.ispassed
				// = 1
			} else {
				sql = "select  sqi.myScore from  CLASS_COURSE cc left join study_course sc  on cc.courseid = sc.courseid "
						+ "left join study_quizinfo sqi on sqi.id=sc.sqiid  where  cc.classid = sc.classid "
						+ " and cc.classid=? and sc.userid =?  and cc.getcredit  = 2 ";// and
				// sqi.ispassed
				// = 1
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			if (classType.equals("CLASS_COURSE_AT")) {
				ps.setInt(1, classid);
				ps.setInt(2, userid);
				ps.setInt(3, userid);
				ps.setInt(4, classid);
			} else {
				ps.setInt(1, classid);
				ps.setInt(2, userid);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				averageGrade = averageGrade + rs.getFloat(1);
				i++;
			}
			if (i != 0)
				averageGrade = averageGrade / i;
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆成硷拷锟接分碉拷平锟斤拷杉锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return averageGrade;
	}

	/**
	 * 锟斤拷询学时锟接凤拷
	 * 
	 * @param userid
	 * @param classid
	 * @param classType
	 *            锟斤拷训锟斤拷目纬锟斤拷锟斤拷锟斤拷锟斤拷? 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE_AT 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE
	 * @return list get锟斤拷0锟斤拷=锟斤拷锟斤拷锟窖憋拷锟� get(1)=锟斤拷锟斤拷锟斤拷
	 * @throws ElException
	 */
	public List getXs_period(int userid, int classid, String classType)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List XS = new ArrayList();
		String sqlw = "";
		try {
			if (classType.equals("CLASS_COURSE_AT")) {
				sqlw = " and sc.userid = cc.userid ";
			}
			String sql = "";
			// passtime_2 锟斤拷实锟斤拷学习时锟斤拷 passtime 锟芥定锟斤拷要学习锟斤拷时锟斤拷
			// sql="select (sum(passtime)/60),trunc((sum(passtime)/60)/60-24,1)
			// from study_course sc where sc.classid =? " +
			sql = "select trunc((sum(passtime_2)/60/45),1),trunc((sum(passtime)/60)/60-24,1) from study_course sc where sc.classid =? "
					+ "and courseid in ( select courseid from "
					+ classType
					+ " cc where sc.classid = cc.classid "
					+ sqlw
					+ ") "
					+ "and userid = ? group by passtime ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				XS.add(rs.getString(1) != null ? rs.getString(1) : 0);
				XS.add(rs.getString(2) != null && rs.getFloat(2) >= 0 ? rs
						.getFloat(2) : 0.0f);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟窖伙拷学锟街的课筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return XS;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷习锟侥课筹拷锟斤拷失锟斤拷
	 * 
	 * @param userid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getLX_course(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = " select count(*)  from (select courseid from study_cpage sc left join practicepaper pp on sc.cpid = pp.cpid left join cprac_quizinfo cs on pp.id = cs.ppid "
					+ "where cs.userid = ? and sc.classid = ? group by pp.courseid) ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷习锟侥课筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟斤拷纬锟斤拷锟侥ｏ拷锟斤拷目纬锟斤拷锟斤拷锟�
	 * 
	 * @param userid
	 * @param classid
	 * @param classType
	 *            锟斤拷训锟斤拷目纬锟斤拷锟斤拷锟斤拷锟斤拷? 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE_AT 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getMk_Model(int userid, int classid, String classType)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = "select count(*) from (select courseid from practicepaper pp ,cprac_quizinfo cq where pp.id = cq.ppid and courseid in "
					+ " (select courseid from "
					+ classType
					+ " where classid = ? ) and cq.userid = ? and pp.cpid =0 "
					+ " group by courseid) ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷习锟侥课筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟窖伙拷学锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @param classid
	 * @param classType
	 *            锟斤拷训锟斤拷目纬锟斤拷锟斤拷锟斤拷锟斤拷? 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE_AT 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getXF_credits(int userid, int classid, String classType)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			if (classType.equals("CLASS_COURSE_AT")) {// 锟斤拷锟斤拷锟斤拷训
				// sql="select sum(setcredit) from CLASS_COURSE_AT cc , eluser u
				// ,
				// elclass c,study_class sc" +
				// " where sc.userid = cc.userid and cc.classid = c.id and
				// c.creater
				// = u.id and sc.classid = cc.classid " +
				// " and cc.classid = ? and cc.userid = ? and sc.certificateno
				// is
				// not null";
				sql = "select sum(cc.setcredit) from study_course sc left join study_quizinfo sqi on sc.sqiid=sqi.id  "
						+ " left join CLASS_COURSE_AT cc on sc.classid = cc.classid "
						+ "where sc.userid = cc.userid and cc.courseid = sc.courseid and sqi.classid=? and sc.userid =? and sqi.ispassed=1";// 锟斤拷锟斤拷锟斤拷训锟斤拷只锟叫匡拷锟斤拷sqi.ispassed=1
			} else {// 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷
				// sql="select sum(setcredit) from CLASS_COURSE cc ,study_class
				// sc"
				// +
				// " where sc.userid = cc.userid and sc.classid = cc.classid " +
				// " and cc.classid = ? and sc.userid = ? and sc.certificateno
				// is
				// not null";
				sql = "select sum(cc.setcredit) from study_course sc left join study_quizinfo sqi on sc.sqiid=sqi.id  left join CLASS_COURSE cc "
						+ "on sc.classid = cc.classid where   cc.courseid = sc.courseid and sqi.classid=? and sc.userid =?  "
						+ "and (sqi.ispassed = 1  or sc.passed=1 or ( sqi.ispassed=1 and sc.passed=1))";
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷模锟斤拷锟侥课筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟斤拷锟绞记的课筹拷锟斤拷
	 * 
	 * @param userid
	 * @param classid
	 * @param classType
	 *            锟斤拷训锟斤拷目纬锟斤拷锟斤拷锟斤拷锟斤拷? 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE_AT 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public int getBj_course(int userid, int classid, String classType)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = "  select count(co.courseid) from (select  courseid  "
					+ "from course_note where courseid in (select courseid from "
					+ classType + "  where classid= ? )"
					+ " and userid =?   group by  courseid ) co";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷习锟侥课筹拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟窖凤拷锟斤拷锟斤拷锟斤拷锟斤拷 锟斤拷锟斤拷 锟斤拷锟斤拷锟街�
	 * 
	 * @param userid
	 * @param valid
	 *            (valid == 1时 锟斤拷取锟斤拷锟斤拷说锟街� == 0 全锟斤拷知识)
	 * @return
	 * @throws ElException
	 */
	public int getSc_releaseORaudit(int userid, int valid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			if (valid == 1) {// 锟斤拷锟斤拷锟�
				sql = "select count(id) from knowledge  where userid = ? and valid = 1"
						+ " and createtime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
						+ "  and createtime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			} else {// 全锟斤拷
				sql = "select count(id) from knowledge  where userid = ?"
						+ " and createtime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
						+ "  and createtime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟窖凤拷锟斤拷锟斤拷锟斤拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟狡硷拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @param hot
	 *            1 为锟狡硷拷
	 * @return
	 * @throws ElException
	 */
	public int getBtj_article(int userid, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = "select count(id) from knowledge  where userid = ? and hot = ?"
					+ " and createtime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
					+ "  and createtime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, hot);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟窖凤拷锟斤拷锟斤拷锟斤拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟斤拷缺锟斤拷锟斤拷氐梅锟� --锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param userid
	 * @param valid
	 *            1 为锟斤拷锟斤拷锟�
	 * @return
	 * @throws ElException
	 */
	public int getBxz_audit(int userid, int valid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = "select count(*) from knowledge where userid = ? and valid = ?"
					+ " and createtime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
					+ "  and createtime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, valid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷缺锟斤拷锟斤拷氐梅锟絖锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟斤拷缺锟斤拷锟斤拷氐梅锟� 锟斤拷锟斤拷锟剿达拷
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getBxz_people(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = "select count(*) from downloadinfo di, knowledge kl where di.typeid = kl.id and di.type = 1 and kl.userid = ? and di.userid != ?"
					+ // type =1 锟斤拷knowledge锟斤拷锟� and di.userid != ? 锟角诧拷锟斤拷锟斤拷锟皆硷拷锟斤拷锟斤拷
					" and di.DownloadTime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
					+ "  and di.DownloadTime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷缺锟斤拷锟斤拷氐梅锟絖锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟截得凤拷 --锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getXz_audit(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = "select count(*) from downloadinfo where userid = ? "
					+ " and DownloadTime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
					+ "  and DownloadTime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷缺锟斤拷锟斤拷氐梅锟絖锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷 or 通锟斤拷锟斤拷
	 * 
	 * @param userid
	 * @param valid
	 *            (valid = 1 锟斤拷取通锟斤拷锟斤拷锟斤拷锟� = 0 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟� 锟斤拷询全锟斤拷锟斤拷锟斤拷)
	 * @return
	 * @throws ElException
	 */
	public int getFt_postORpass(int userid, int valid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			if (valid == 1) {// 锟斤拷通锟斤拷
				sql = "select count(*) from forum where creater = ? and valid = 1"
						+ " and createtime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
						+ "  and createtime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			} else {// 全锟斤拷
				sql = "select count(*) from forum where creater = ?"
						+ " and createtime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
						+ "  and createtime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷 or 通锟斤拷锟斤拷 失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟皆达拷锟斤拷
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getFy_speech(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = "select count(*) from ftopic where creater = ? "
					+ "  and createtime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
					+ "  and createtime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆达拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟斤拷染锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param userid
	 * @param hot
	 *            1为锟斤拷锟斤拷锟斤拷
	 * @return
	 * @throws ElException
	 */
	public int getJh_jht(int userid, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = "select count(*) from forum where creater = ? and hot = ?"
					+ " and createtime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
					+ "  and createtime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, hot);
			rs = ps.executeQuery();
			if (rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 锟斤拷锟斤拷鹊锟铰斤拷锟斤拷锟�
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int getDl_login(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "";
			sql = " select count(*) from eluserloginInfo where lognumber <=10 and userid = ? "
					+ // //锟斤拷锟较低筹拷锟斤拷萍臃执锟斤拷锟� 每锟斤拷只锟斤拷10锟轿加分硷拷录
					"  and logintime < (SELECT TRUNC(SYSDATE,'YYYY')+367 FROM DUAL)"
					+ "  and logintime > (SELECT TRUNC(SYSDATE,'YYYY') FROM DUAL)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷陆锟斤拷锟斤拷失锟杰ｏ拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public boolean checkPointsRecord(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from POINTS_RECORD where  userid = ? and classid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷旨锟铰硷拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void alterPointsRecord(PointsRecord precord) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update POINTS_RECORD set CSCORE =? ,FSCORE = ?,totalscore = ? where userid = ? and classid = ?");
			ps.setFloat(1, precord.getCscore());
			ps.setFloat(2, precord.getFscore());
			ps.setFloat(3, precord.getFscore() + precord.getCscore());
			ps.setInt(4, precord.getUser().getId());
			ps.setInt(5, precord.getElclass().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void intoPointsRecord(PointsRecord precord) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into POINTS_RECORD(userid,CLASSID,CSCORE,FSCORE,totalscore) values(?,?,?,?,?)");
			ps.setInt(1, precord.getUser().getId());
			ps.setInt(2, precord.getElclass().getId());
			ps.setFloat(3, precord.getCscore());
			ps.setFloat(4, precord.getFscore());
			ps.setFloat(5, precord.getFscore() + precord.getCscore());
			System.out.println(precord.getFscore() + precord.getCscore()
					+ "---------------------------------------");
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷录锟斤拷殖锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * (只锟斤拷锟皆匡拷锟斤拷目)锟斤拷锟皆成硷拷锟接分碉拷 平锟斤拷杉锟�
	 * 
	 * @param userid
	 * @param classid
	 * @param classType
	 *            锟斤拷训锟斤拷目纬锟斤拷锟斤拷锟斤拷锟斤拷? 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE_AT 锟斤拷锟斤拷锟斤拷训锟斤拷锟斤拷CLASS_COURSE
	 * @return
	 * @throws ElException
	 */
	public float getKC_CJ_AVG_(int userid, int classid, String classType)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		float averageGrade = 0;
		int i = 0;// 锟斤拷锟斤拷
		try {
			String sql = "";
			if (classType.equals("CLASS_COURSE_AT")) {
				sql = "select sqi.myScore from (select * from CLASS_COURSE_AT where classid =?   and userid = ? and getcredit = 2 "
						+ "  ) cc "
						+ // and status = 0 锟皆匡拷
						"left join study_course sc  on cc.courseid = sc.courseid left join course c on sc.courseid = c.id "
						+ "left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid "
						+ "left join exam_room er on sqi.roomid=er.id where sc.userid =? and sc.classid=?  and sqi.ispassed=1"; // and
				// sqi.ispassed
				// = 1
			} else {
				sql = "select  sqi.myScore from  CLASS_COURSE cc left join study_course sc  on cc.courseid = sc.courseid "
						+ "left join study_quizinfo sqi on sqi.id=sc.sqiid  where  cc.classid = sc.classid "
						+ " and cc.classid=? and sc.userid =?  and cc.getcredit  = 2  and cc.status = 0";// and
				// status
				// = 0
				// 锟皆匡拷
				// and
				// sqi.ispassed
				// = 1
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			if (classType.equals("CLASS_COURSE_AT")) {
				ps.setInt(1, classid);
				ps.setInt(2, userid);
				ps.setInt(3, userid);
				ps.setInt(4, classid);
			} else {
				ps.setInt(1, classid);
				ps.setInt(2, userid);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				averageGrade = averageGrade + rs.getFloat(1);
				i++;
			}
			averageGrade = averageGrade / i;
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟皆成硷拷锟接分碉拷平锟斤拷杉锟绞э拷埽锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return averageGrade;
	}

	/**
	 * 锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-锟斤拷锟桔合得凤拷
	 * 
	 * @param classid
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public float getBasedScore(int classid, String depids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		float score = 0.0f;
		String sql = "";
		try {
			sql = "	select sum(nvl(pr.CSCORE,0 ) + nvl(pr.FSCORE,0 ) + nvl(pr.ADDSCORE,0 ))/"
					+ "(select  count(pr_.userid) from  study_class sc_,POINTS_RECORD pr_,eluser elu_ ,department dep_ where"
					+ " sc_.userid = pr_.userid and sc_.classid = pr_.classid and pr_.userid = elu_.id "
					+ "and elu_.depid = dep_.id and pr_.classid = ? "
					+ "and dep_.id in ("
					+ depids
					+ ") "
					+ "and elu_.zhuanyezigejibie  not like '%锟竭硷拷%' and  elu_.peixunleibie !='锟竭硷拷职锟斤拷') "
					+ "from study_class sc, POINTS_RECORD pr,eluser elu, department dep "
					+ "where pr.userid = elu.id and elu.depid = dep.id and sc.userid = pr.userid and sc.classid = pr.classid"
					+ " and pr.classid = ? and  dep.id in ("
					+ depids
					+ ") and elu.zhuanyezigejibie not like '%锟竭硷拷%' and  elu.peixunleibie !='锟竭硷拷职锟斤拷' group by sc.classid";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				score = rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-锟斤拷锟桔合得分筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return score;
	}

	/**
	 * 锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-学锟斤拷锟轿得凤拷
	 * 
	 * @param classid
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public float getDegreeScore(int classid, String depids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		float score = 0.0f;
		float ReturnScore = 0.0f;
		float Totalscore = 0.0f;
		int UnitSum = 0;
		int TitleSum = 0; // 锟竭硷拷职锟狡硷拷锟斤拷锟斤拷锟斤拷
		String sql = "";
		try {
			// sql ="select nvl(elu.feixueli,0) ,nvl(elu.xueli ,0)," +
			// "(select count(pr_.userid) from study_class sc_,POINTS_RECORD
			// pr_,eluser elu_,department dep_ where " +
			// "sc_.userid = pr_.userid and sc_.classid = pr_.classid and
			// elu_.depid = dep_.id " +
			// "and pr_.userid = elu_.id and pr_.classid = ? and dep_.id
			// in("+depids+") and elu_.zhuanyezigejibie ='锟竭硷拷' " +
			// "and elu_.zhuanyezigejibie = '锟斤拷呒锟�' ) zhicheng " +
			// "from study_class sc, POINTS_RECORD pr,eluser elu, department dep
			// " +
			// "where pr.userid = elu.id and elu.depid = dep.id and sc.userid =
			// pr.userid and sc.classid = pr.classid " +
			// "and pr.classid = ? and dep.id in("+depids+") ";
			// 锟铰伙拷止锟斤拷锟�
			sql = "select nvl(elu.feixueli,0) ,nvl(elu.xueli ,0),"
					+ "(select  count(pr_.userid) from  study_class sc_,POINTS_RECORD pr_,eluser elu_,department dep_  where "
					+ "sc_.userid = pr_.userid and sc_.classid = pr_.classid and elu_.depid = dep_.id "
					+ "and pr_.userid = elu_.id and pr_.classid = ? and dep_.id in("
					+ depids
					+ ") and (elu_.zhuanyezigejibie  like '%锟竭硷拷%' "
					+ " or  elu_.peixunleibie ='锟竭硷拷职锟斤拷') ) zhicheng "
					+ "from study_class sc, POINTS_RECORD pr,eluser elu, department dep "
					+ "where pr.userid = elu.id and elu.depid = dep.id and sc.userid = pr.userid and sc.classid = pr.classid "
					+ "and pr.classid = ? and  dep.id in("
					+ depids
					+ ") and elu.zhuanyezigejibie not like '%锟竭硷拷%' and  elu.peixunleibie !='锟竭硷拷职锟斤拷' ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				score = getXueLiScore(rs.getString(1)) >= getXueLiScore(rs
						.getString(2)) ? getXueLiScore(rs.getString(1))
						: getXueLiScore(rs.getString(2));
				Totalscore = Totalscore + score;
				UnitSum = UnitSum + 1;
				TitleSum = rs.getInt(3);
			}
			// 学锟斤拷锟轿得凤拷 锟斤拷 学锟斤拷锟斤拷锟杰和筹拷锟皆ｏ拷锟斤拷位锟斤拷锟斤拷锟斤拷呒锟街帮拷锟斤拷锟斤拷锟�
			if (UnitSum - TitleSum > 0)
				ReturnScore = Totalscore / (UnitSum - TitleSum);
			else
				ReturnScore = 0.0f;
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-学锟斤拷锟轿得分筹拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ReturnScore;
	}

	private float getXueLiScore(String XUELI) throws ElException {
		XUELI = XUELI == null ? "锟斤拷学锟斤拷" : XUELI;
		if (XUELI.equals("锟斤拷锟叫硷拷锟斤拷锟斤拷") || XUELI.equals("锟斤拷锟斤拷") || XUELI.equals("锟斤拷专")
				|| XUELI.equals("锟斤拷学锟斤拷"))
			return 0.5f;
		else if (XUELI.equals("锟斤拷专"))
			return 1.0f;
		else if (XUELI.equals("锟斤拷锟斤拷"))
			return 2.0f;
		else if (XUELI.equals("硕士锟叫撅拷锟斤拷"))
			return 3.0f;
		else if (XUELI.equals("锟斤拷士锟叫撅拷锟斤拷"))
			return 4.0f;
		else
			return 0.0f;
	}

	/**
	 * 锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-职锟狡硷拷锟斤拷梅锟�
	 * 
	 * @param classid
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public float getTitleScore(int classid, String depids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		float score = 0.0f;
		float ReturnScore = 0.0f;
		float Totalscore = 0.0f;
		int UnitSum = 0;
		int TitleSum = 0;
		String sql = "";
		try {
			// sql ="select nvl(elu.zhuanyezigejibie,0) ,(select
			// count(pr_.userid) from study_class sc_,POINTS_RECORD pr_,eluser
			// elu_,department dep_ " +
			// "where sc_.userid = pr_.userid and sc_.classid = pr_.classid and
			// elu_.depid = dep_.id " +
			// "and pr_.userid = elu_.id and pr_.classid = ? and dep_.id
			// in("+depids+") and elu_.zhuanyezigejibie ='锟竭硷拷' " +
			// "and elu_.zhuanyezigejibie = '锟斤拷呒锟�' ) zhicheng from study_class
			// sc, POINTS_RECORD pr,eluser elu, department dep " +
			// "where pr.userid = elu.id and elu.depid = dep.id and sc.userid =
			// pr.userid and sc.classid = pr.classid " +
			// "and pr.classid = ? and dep.id in("+depids+") ";
			// 锟睫革拷为锟铰的伙拷止锟斤拷锟�
			sql = "select nvl(elu.zhuanyezigejibie,0) ,(select  count(pr_.userid) from  study_class sc_,POINTS_RECORD pr_,eluser elu_,department dep_ "
					+ "where sc_.userid = pr_.userid and sc_.classid = pr_.classid and elu_.depid = dep_.id "
					+ "and pr_.userid = elu_.id and pr_.classid = ? and dep_.id in("
					+ depids
					+ ") and (elu.zhuanyezigejibie like '%锟竭硷拷%' "
					+ "or  elu.peixunleibie ='锟竭硷拷职锟斤拷')) zhicheng from study_class sc, POINTS_RECORD pr,eluser elu, department dep "
					+ "where pr.userid = elu.id and elu.depid = dep.id and sc.userid = pr.userid and sc.classid = pr.classid "
					+ "and pr.classid = ? and  dep.id in("
					+ depids
					+ ")  and elu.zhuanyezigejibie not like '%锟竭硷拷%' and  elu.peixunleibie !='锟竭硷拷职锟斤拷'";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				score = getTitleScore(rs.getString(1));
				Totalscore = Totalscore + score;
				UnitSum = UnitSum + 1;
				TitleSum = rs.getInt(2);
			}
			// 职锟狡硷拷锟斤拷梅郑锟街帮拷苹锟斤拷锟杰和筹拷锟皆ｏ拷锟斤拷位锟斤拷锟斤拷锟斤拷呒锟街帮拷锟斤拷锟斤拷锟�
			if (UnitSum - TitleSum > 0)
				ReturnScore = Totalscore / (UnitSum - TitleSum);
			else
				ReturnScore = 0.0f;
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-职锟狡硷拷锟斤拷梅殖锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ReturnScore;
	}

	private float getTitleScore(String TITLE) throws ElException {
		TITLE = TITLE == null ? "锟斤拷" : TITLE;
		if (TITLE.equals("锟斤拷"))
			return 0.0f;
		if (TITLE.equals("锟斤拷锟斤拷"))
			return 1.0f;
		else if (TITLE.equals("锟叫硷拷"))
			return 2.0f;
		else if (TITLE.equals("锟竭硷拷") || TITLE.equals("锟斤拷呒锟�"))
			return 4.0f;
		else
			return 0.0f;
	}

	/**
	 * 锟斤拷证锟角凤拷锟街该碉拷位锟斤拷锟斤拷锟斤拷锟斤拷录
	 * 
	 * @param classid
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public boolean checkUnitRank(int classid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			sql = "select classid from  elunit_ranking where classid = ? and depid = ? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷欠锟斤拷丫锟斤拷锟斤拷肟硷拷猿锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public UnitRanking getUnitRank(int classid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		UnitRanking ur = new UnitRanking();
		try {
			sql = "select classid,depid,passing,basedScore,DegreeScore,TitleScore,TotalScore,AddCent,FinalScore from  elunit_ranking where classid = ? and depid = ? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ur.setElclass(new ElClass(rs.getInt(1)));
				ur.setUnit(new Department(rs.getInt(2)));
				ur.setPassing(rs.getDouble(3));
				ur.setBasedScore(rs.getFloat(4));
				ur.setDegreeScore(rs.getFloat(5));
				ur.setTitleScore(rs.getFloat(6));
				ur.setTotalScore(rs.getFloat(7));
				ur.setAddCent(rs.getFloat(8));
				ur.setFinalScore(rs.getFloat(9));
			}

		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ur;
	}

	public void UpdateUnitRank(UnitRanking UnitRank) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update elunit_ranking set passing = ?,basedScore= ?,DegreeScore= ?,TitleScore= ?,"
							+ "TotalScore= ?,AddCent= ?,FinalScore= ? where classid = ? and depid = ?");
			ps.setDouble(1, UnitRank.getPassing());
			ps.setFloat(2, UnitRank.getBasedScore());
			ps.setFloat(3, UnitRank.getDegreeScore());
			ps.setFloat(4, UnitRank.getTitleScore());
			ps.setFloat(5, UnitRank.getTotalScore());
			ps.setFloat(6, UnitRank.getAddCent());
			ps.setFloat(7, UnitRank.getFinalScore());
			ps.setInt(8, UnitRank.getElclass().getId());
			ps.setInt(9, UnitRank.getUnit().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟铰碉拷位锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void insertUnitRank(UnitRanking UnitRank) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into elunit_ranking(classid,depid,passing,basedScore,DegreeScore,TitleScore,TotalScore,AddCent,FinalScore)"
							+ " values(?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, UnitRank.getElclass().getId());
			ps.setInt(2, UnitRank.getUnit().getId());
			ps.setDouble(3, UnitRank.getPassing());
			ps.setFloat(4, UnitRank.getBasedScore());
			ps.setFloat(5, UnitRank.getDegreeScore());
			ps.setFloat(6, UnitRank.getTitleScore());
			ps.setFloat(7, UnitRank.getTotalScore());
			ps.setFloat(8, UnitRank.getAddCent());
			ps.setFloat(9, UnitRank.getFinalScore());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟接碉拷位锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<UnitRanking> getUnitRanks(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int ranking = 0;
		List<UnitRanking> unitRanks = null;
		try {
			sql = "select er.classid,er.depid,edp.passing,er.basedScore,er.DegreeScore,er.TitleScore,er.TotalScore,"
					+ "er.AddCent,er.FinalScore,dep.name from  elunit_ranking er, department dep ,elclass_dep_passing edp "
					+ "where dep.id = edp.depid and dep.parentid = 1 and  dep.id!=98 and edp.classid =er.classid and er.depid = dep.id and er.classid = ?  order by er.FinalScore desc ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);

			rs = ps.executeQuery();
			while (rs.next()) {
				if (unitRanks == null) {
					unitRanks = new ArrayList<UnitRanking>();
				}
				ranking += 1;
				UnitRanking ur = new UnitRanking();
				ur.setElclass(new ElClass(rs.getInt(1)));
				ur.setUnit(new Department(rs.getInt(2), rs.getString(10)));
				ur.setPassing(rs.getDouble(3));
				ur.setBasedScore(rs.getFloat(4));
				ur.setDegreeScore(rs.getFloat(5));
				ur.setTitleScore(rs.getFloat(6));
				ur.setTotalScore(rs.getFloat(7));
				ur.setAddCent(rs.getFloat(8));
				ur.setFinalScore(rs.getFloat(9));
				ur.setRanking(ranking);
				unitRanks.add(ur);

			}

		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷锟斤拷斜锟斤拷锟较拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return unitRanks;
	}

	/**
	 * 锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-学锟斤拷锟轿得凤拷锟斤拷锟斤拷
	 * 
	 * @param classid
	 * @param depids
	 * @return
	 * @throws ElException
	 */
	public UnitRanking getDegreeScoreDetails(int classid, String depids)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		UnitRanking ur = new UnitRanking();
		float score = 0.0f;
		String sql = "";
		try {
			// sql =" select elu.xueli,elu.feixueli " +
			// "from study_class sc, eluser elu, department dep where " +
			// "and elu.depid = dep.id and sc.userid = pr.userid " +
			// "and sc.classid = pr.classid and pr.classid = ? and dep.id
			// in("+depids+")";

			sql = "  select elu.xueli,elu.feixueli "
					+ "from eluser elu  left join department dep on elu.depid = dep.id  "
					+ "where  dep.id in(" + depids + ")";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);

			rs = ps.executeQuery();
			ur.setElclass(new ElClass(classid));
			while (rs.next()) {
				score = getXueLiScore(rs.getString(1)) >= getXueLiScore(rs
						.getString(2)) ? getXueLiScore(rs.getString(1))
						: getXueLiScore(rs.getString(2));
				if (score == 0.5)// 锟斤拷专锟斤拷锟斤拷
					ur.setXl_dz_(ur.getXl_dz_() + 1);
				else if (score == 1.0)// 锟斤拷专
					ur.setXl_dz(ur.getXl_dz() + 1);
				else if (score == 2.0)// 锟斤拷锟斤拷
					ur.setXl_bk(ur.getXl_bk() + 1);
				else if (score == 3.0)// 硕士锟叫撅拷锟斤拷
					ur.setXl_ss(ur.getXl_ss() + 1);
				else if (score == 4.0)// 锟斤拷士锟叫撅拷锟斤拷
					ur.setXl_bs(ur.getXl_bs() + 1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-学锟斤拷锟轿得凤拷锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ur;
	}

	/**
	 * 锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-职锟狡硷拷锟斤拷梅锟斤拷锟斤拷锟�
	 * 
	 * @param classid
	 * @param depids
	 * @return
	 * @throws ElException
	 */
	public UnitRanking getTitleScoreDetails(int classid, String depids)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		UnitRanking ur = new UnitRanking();
		float score = 0.0f;
		String sql = "";
		try {
			// sql ="select elu.zhuanyezigejibie " +
			// "from study_class sc, POINTS_RECORD pr,eluser elu, department dep
			// where pr.userid = elu.id " +
			// "and elu.depid = dep.id and sc.userid = pr.userid and sc.classid
			// = pr.classid and pr.classid = ? " +
			// "and dep.id in("+depids+") ";
			sql = "select elu.zhuanyezigejibie from eluser elu  left "
					+ " join department dep on elu.depid = dep.id  "
					+ " where    dep.id in(" + depids + ")";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);

			rs = ps.executeQuery();
			ur.setElclass(new ElClass(classid));
			while (rs.next()) {
				score = getTitleScore(rs.getString(1));
				if (score == 0.0) // 锟斤拷
					ur.setZc_w(ur.getZc_w() + 1);
				else if (score == 1.0)// 锟斤拷锟斤拷
					ur.setZc_cj(ur.getZc_cj() + 1);
				else if (score == 2.0)// 锟叫硷拷
					ur.setZc_zj(ur.getZc_zj() + 1);
				else if (score == 4.0)// 锟竭硷拷 锟斤拷呒锟�
					ur.setZc_gj(ur.getZc_gj() + 1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷位锟斤拷锟斤拷锟斤拷邪锟�-职锟狡硷拷锟斤拷梅锟斤拷锟斤拷锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ur;
	}

	public List<MyRoom> listBuyErooms(int userid, int type, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> myBxc = new ArrayList<MyRoom>();
		String sql = "";
		try {
			// if (type == 1) { // 选锟斤拷式锟斤拷锟斤拷
			// sql = "select * from (select t.*,rownum rn from ("
			// + "select er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.id) "
			// + "sqicount,sr.myscore,el.realname , er.type,er.valid
			// ervalid,er.uvalid eruvalid,er.isnormal erisnormal,er.svalid
			// ersvalid,sr.ispassed,er.isApplication from (select * from
			// exam_room where iscommon=1 and valid != 9 and type = 1 and svalid
			// = 5) er join eluser el on"
			// + " er.createrid=el.id left join study_room sr on er.id =
			// sr.roomid left join (select * from study_quizinfo where userid =
			// ?) "
			// + "sqi on sqi.roomid =sr.roomid where sr.userid =? group by
			// er.id,er.title,er.location ,"
			// + "er.begintime,er.endtime,sr.status,sr.myscore,el.realname,
			// er.type,er.valid,er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication
			// "
			// + ") t where rownum <=?) where rn>=?";
			// } else {
			// sql = "select * from (select t.*,rownum rn from ("
			// + "select er.id erid,er.title,er.location
			// ,er.begintime,er.endtime,sr.status,count(sqi.epid) "
			// + "sqicount,sr.myscore,el.realname , er.type,er.valid
			// ervalid,er.uvalid eruvalid,er.isnormal erisnormal,er.svalid
			// ersvalid,sr.ispassed,er.isApplication from (select * from
			// exam_room where iscommon=1 and valid != 9 and isNormal = 1 and
			// type != 1 ) er join eluser el on"
			// + " er.createrid=el.id left join study_room sr on er.id =
			// sr.roomid left join (select * from study_exampaper where userid =
			// ?) "
			// + "sqi on sqi.roomid =sr.roomid where sr.userid =? group by
			// er.id,er.title,er.location ,"
			// + "er.begintime,er.endtime,sr.status,sr.myscore,el.realname,
			// er.type,er.valid,er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication
			// ) t where rownum <=?) where rn>=?";
			sql = "select * from (select t.*,rownum rn from ("
					+ " select erinfo.id erid,erinfo.title,erinfo.location ,erinfo.begintime,erinfo.endtime,erinfo.status,"
					+ " erinfo.sqicount,erinfo.myscore,erinfo.realname , erinfo.type,erinfo.valid ervalid,erinfo.uvalid eruvalid,"
					+ " erinfo.isnormal erisnormal,erinfo.svalid ersvalid,erinfo.ispassed,erinfo.isApplication,erinfo.examcount, erinfo.joinway,erinfo.mainimg "
					+
					// ", count(srr.id) srrcount " +
					" from (select er.id ,er.title,er.location ,er.begintime,er.endtime,sr.status,count(sqi.epid) sqicount,sr.myscore,el.realname , "
					+ " er.type,er.valid ,er.uvalid ,er.isnormal ,er.svalid ,sr.ispassed,er.isApplication,er.examcount,sr.joinway,er.mainimg "
					+ " from (select * from exam_room  where iscommon=1 and valid != 9 and isNormal = 1  and  type != 1 ) er "
					+ " join eluser el on er.createrid=el.id left join study_room sr on er.id = sr.roomid and sr.joinway=3 "
					+ " left join (select * from study_exampaper where userid = ?) sqi on sqi.roomid =sr.roomid where sr.userid=? and sr.status!=-1 "
					+ " group by er.id,er.title,er.location ,er.begintime,er.endtime,sr.status,sr.myscore,el.realname, er.type,er.valid,"
					+ " er.uvalid,er.isnormal,er.svalid,sr.ispassed,er.isApplication,er.examcount,sr.joinway,er.mainimg) erinfo "
					+
					// " left join (select * from study_room_record where
					// userid=?) srr on erinfo.id=srr.roomid" +
					" group by erinfo.id ,erinfo.title,erinfo.location ,"
					+ " erinfo.begintime,erinfo.endtime,erinfo.status,erinfo.sqicount,erinfo.myscore,erinfo.realname , erinfo.type,erinfo.valid ,erinfo.uvalid ,erinfo.isnormal ,"
					+ " erinfo.svalid ,erinfo.ispassed,erinfo.isApplication,erinfo.examcount,erinfo.joinway,erinfo.mainimg"
					+
					// ",srr.roomid" +
					" ) t where rownum <=?) where rn>=?";
			// }
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_MROOM_WITHOUTCOURSE));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, userid);
			// ps.setInt(3, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			ELUser creater = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setLocation(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt("ervalid"));
				er.setUvalid(rs.getInt("eruvalid"));
				er.setIsnormal(rs.getInt("erisnormal"));
				er.setSvalid(rs.getInt("ersvalid"));
				creater = new ELUser();
				creater.setRealname(rs.getString("realname"));
				er.setCreater(creater);
				er.setIsApplication(rs.getInt("isApplication"));
				er.setMainimg(rs.getString("mainimg"));
				MyRoom mc = new MyRoom();
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setEpsize(rs.getInt(7));
				mc.setMyScore(rs.getFloat(8));
				// if (this.getExamIsNoKao(userid, er.getId())) {
				mc.setIspassed(rs.getInt("ispassed"));
				// } else {
				// mc.setIspassed(3);
				// }
				er.setExamcount(rs.getInt("examcount"));
				// mc.setSrrcount(rs.getInt("srrcount"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public int listBuyEroomsSize(int userid, int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if (type == 1) {// 选锟斤拷式锟斤拷锟斤拷
				sql = "select sum(c) from ("
						+ "(select count(*) as c from (select * from exam_room where iscommon=1 and  valid != 9 and type = 1 and svalid = 5) er left join study_room sqi on er.id = sqi.roomid and sqi.joinway=3 where sqi.userid = ?)"
						+ ")";
			} else {
				sql = "select sum(c) from ("
						+ "(select count(*) as c from (select * from exam_room where iscommon=1 and isNormal = 1 and  valid != 9 and type != 1) er left join study_room sqi on er.id = sqi.roomid and sqi.joinway=3 where sqi.userid = ? and sqi.status!=-1)"
						+ ")";
			}
			ps = ct.prepareStatement(sql);
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StudyConstants.STUDY_QPAPER_WITHOUTCOURSE_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int getExamRoomid(int roomid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select epid from study_exampaper where userid=? and roomid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟絩oomid锟斤拷userid锟斤拷取锟皆撅拷id锟斤拷锟�?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 锟斤拷锟斤拷陆锟阶刺�
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
			logger.error("锟斤拷询状态锟斤拷锟斤拷", e);
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
			logger.error("锟斤拷前锟铰斤拷学习锟斤拷锟斤拷锟�", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mps;
	}

	public void quizpaper_begin(int userid, int classid, int courseid,
			int pageid, int myexampaperid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call quizpaper_begin(?,?,?,?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, courseid);
			ps.setInt(4, pageid);
			ps.setInt(5, myexampaperid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷始锟斤拷锟皆硷拷录失锟斤拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void quizpaper_end( int myexampaperid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call quizpaper_end(?)}");
			ps.setInt(1, myexampaperid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟皆硷拷录失锟斤拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
//--------------wsj 1016锟睫革拷---------------------------------------
	/**
	 * 锟矫碉拷锟矫伙拷前一锟轿匡拷锟斤拷时锟斤拷锟诫当前时锟斤拷锟街�
	 */
	public MyExamPaper beforetime_now(int userid,int roomid,int myexampaperid) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Double time_Dvalue = 0.0;
		MyExamPaper ep =null;
		try {
			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement(" select round((sysdate-sqi.endtime)*24*60) from study_quizinfo sqi where sqi.userid=? and sqi.roomid=? and sqi.epid=? ");
			ps= ct.prepareStatement(
				"select round((sysdate - sqi.endtime) * 24*60),endtime, sysdate - sqi.endtime\n" +
				"  from study_quizinfo sqi\n" + 
				" where sqi.userid = ?" + 
				"   and sqi.roomid = ?" + 
				"   and sqi.epid = ?" + 
				"   and sqi.id = (select max(id) from study_quizinfo where sqi.userid = ?" + 
				"   and sqi.roomid = ?" + 
				"   and sqi.epid = ?)");

			ps.setInt(1, userid);
			ps.setInt(2,  roomid);
			ps.setInt(3, myexampaperid);
			ps.setInt(4, userid);
			ps.setInt(5,  roomid);
			ps.setInt(6, myexampaperid);
			rs = ps.executeQuery();
			while(rs.next()){
				ep = new MyExamPaper();
				ep.setTime_Dvalue(rs.getDouble(1));
			}
		} catch (Exception e) {
			logger.error("锟斤拷询状态锟斤拷锟斤拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	/**
	 * 锟矫碉拷锟矫伙拷锟斤拷锟届考锟皆达拷锟斤拷
	 */
	public MyExamPaper countforday(int userid,int roomid,int myexampaperid) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int countforday = 0;
		MyExamPaper ep =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(id) from study_quizinfo sqi where to_char(sqi.endtime,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') and sqi.userid=? and sqi.roomid=? and sqi.epid=? ");
			ps.setInt(1, userid);
			ps.setInt(2,  roomid);
			ps.setInt(3, myexampaperid);
			rs = ps.executeQuery();
			while(rs.next()){
				ep = new MyExamPaper();
				ep.setCountforday(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("锟斤拷询状态锟斤拷锟斤拷", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}


	public int checkQuestionCanNext(int myexampaperid, int blockid,
			int questionid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int canNext = 0;
		float myscore = 0f;
		int atime = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "select myscore,atime from study_questions where sqid=? and blockid=? and qid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, myexampaperid);
			ps.setInt(2, blockid);
			ps.setInt(3, questionid);
			rs = ps.executeQuery();
			if (rs.next()) {
				myscore = rs.getFloat(1);
				atime = rs.getInt(2);
				if(myscore == 0f){
					if(atime >= 2){
						canNext = 1;
					}
				}else{
					canNext = 1;
				}
			}
		} catch (Exception e) {
			logger.error("锟斤拷锟斤拷锟斤拷转锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return canNext;
	}
	
	
//wsj1023锟睫革拷---------------------------------------------------

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷锟斤拷锟窖碉拷锟�
	 * 
	 * @param userid
	 * @param roomid
	 * @param mrrid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> listMypaperByRidanUid(int userid, int roomid,
			int mrrid,int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> myBxc = new ArrayList<MyExamPaper>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select ep.id epid,ep.title,sqi.id
			// sqiid,sqi.status,prac.id,prac.title,"
			// + "sqi.practimes mypract,sqi.pracscore myprasc,erp.practimes
			// erppractimes,erp.pracscore erppracscore,sqi.quizcount
			// myexamcount,erp.quizlook,erp.scorelook,sqi.ispassed"
			// + " from study_exampaper sqi left join exam_reps erp on erp.epid
			// = sqi.epid and erp.roomid = sqi.roomid"
			// + " left join exampaper ep on ep.id = erp.epid left join
			// exampaper prac on prac.id = erp.pracid where sqi.userid =? "
			// + "and sqi.roomid= ? ");
			ps = ct
					.prepareStatement("select ep.id epid,ep.title,sqi.myscore,sqi.status,"
							+ "sqi.quizcount myexamcount,erp.quizlook,erp.scorelook,sqi.ispassed,erp.quizcount,sqi.avgscore,sqi.maxscore,sqi.isdel,erp.passmanner,min(sq.status),ep.during,sqi.classid "
							+ " from study_exampaper sqi left join exam_reps erp on erp.epid = sqi.epid and erp.roomid = sqi.roomid"
							+ " left join exampaper ep on ep.id = erp.epid left join study_quizinfo sq on sq.roomid =sqi.roomid and sqi.userid=sq.userid and sqi.epid = sq.epid where sqi.userid =? and sqi.roomid= ?  and sqi.classid=? "
							+ "  group by  ep.id,ep.title,sqi.myscore,sqi.status,  sqi.quizcount ,erp.quizlook,erp.scorelook,sqi.ispassed,erp.quizcount,sqi.avgscore,sqi.maxscore,sqi.isdel,erp.passmanner,ep.during,erp.sortid,sqi.classid order by erp.sortid");

			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			// ps.setInt(3, mrrid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				MyExamPaper mc = new MyExamPaper();
				mc.setId(rs.getInt("epid"));
				// ExamPaper prac = new ExamPaper(rs.getInt(5),
				// rs.getString(6));
				// mc.setPractimes(rs.getInt(7));
				// mc.setPracscore(rs.getFloat(8));
				// prac.setPractimes(rs.getInt(9));
				// prac.setPracscore(rs.getFloat(10));
				mc.setStatus(rs.getInt(4));
				mc.setMyexamcount(rs.getInt(5));
				// ep.setPrac(prac);
				ep.setQuizlook(rs.getInt(6));
				ep.setScorelook(rs.getInt(7));
				ep.setQuizcount(rs.getInt(9));
				// mc.setStatus(rs.getInt(4));
				// if (mc.getStatus() != 2 || mc.getStatus() != 3) {
				// mc.setMyScore(getMyScore(rs.getInt(3)));
				mc.setMyScore(rs.getFloat(3));
				mc.setAvgscore(rs.getFloat(10));
				mc.setMaxscore(rs.getFloat(11));
				// }
				mc.setIspassed(rs.getInt(8));
				mc.setIsdel(rs.getInt(12));
				ep.setPassmanner(rs.getInt(13));
				mc.setMinstatus(rs.getInt(14));
				ep.setDuring(rs.getInt(15));
				// mc.setId(getMypaperIdByRidanUid(userid, roomid, ep.getId(),
				// mc.getMyexamcount(), ep.getQuizcount()));
				mc.setClassId(rs.getInt(16));
				mc.setExamPaper(ep);
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟斤拷息锟斤拷锟斤拷锟斤拷训锟斤拷
	 * 
	 * @param roomid 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public MyRoom getMyErsWithoutR(int roomid, int userid, int iscommon,String classid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoom mc = new MyRoom();
		try {
			ct = DBConnection.getConnection();
			String sql = " select er.id erid,er.title,er.type,er.begintime,er.endtime,sr.status,count(sqi.epid) sqicount,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount,er.isxianzhikaopin,er.examsforday,er.jiangeshijian  from ";
			if (iscommon == -1) {
				sql += " exam_room er join study_room sr on er.id = sr.roomid join (select * from study_exampaper where userid = ?) sqi on sqi.roomid =sr.roomid where  sr.userid =? and er.id =? group by er.id ,er.title,er.type,er.begintime,er.endtime,sr.status,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount,er.isxianzhikaopin,er.examsforday,er.jiangeshijian ";
			} else {
				sql += "(select * from exam_room  where iscommon=?) er join study_room sr on er.id = sr.roomid join (select * from study_exampaper where userid = ? and classid=? ) sqi on sqi.roomid =sr.roomid where  sr.userid =? and er.id =? and sr.classid=? group by er.id ,er.title,er.type,er.begintime,er.endtime,sr.status,er.valid,er.ismacband,er.isiplimit,er.svalid,er.examcount ,er.isxianzhikaopin,er.examsforday,er.jiangeshijian";
			}
			ps = ct.prepareStatement(sql);
			int idx = 0;
			if (iscommon != -1) {
				ps.setInt(1, iscommon);
				idx = 1;
			}

			ps.setInt(idx + 1, userid);
			// ps.setInt(2, mrrid);
			ps.setString(idx + 2, classid);
			ps.setInt(idx + 3, userid);
			ps.setInt(idx+4, roomid);
			ps.setString(idx+5, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setType(rs.getInt(3));
				// er.setPrac(new ExamPaper(rs.getInt(4), rs.getString(5)));
				// er.setPractimes(rs.getInt(6));
				// er.setPracscore(rs.getInt(7));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setIsMacBand(rs.getInt("ismacband"));
				er.setIsIpLimit(rs.getInt("isiplimit"));
				er.setSvalid(rs.getInt("svalid"));
				er.setExamcount(rs.getInt("examcount"));
				er.setIsxianzhikaopin(rs.getInt("isxianzhikaopin"));
				er.setExamsforday(rs.getInt("examsforday"));
				er.setJiangeshijian(rs.getDouble("jiangeshijian"));
				mc.setExamroom(er);
				mc.setStatus(rs.getInt(6));
				mc.setValid(rs.getInt(8));
				// mc.setPractimes(rs.getInt(7));
				// mc.setPracscore(rs.getInt(8));
				mc.setEpsize(rs.getInt(7));

			}
		} catch (Exception e) {
			logger.error("锟揭的课筹拷锟叫憋拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mc;
	}
	
	/**锟皆撅拷锟结交锟斤拷锟斤拷锟斤拷梅值龋锟�
	 * @param examPaper
	 * @throws ElException
	 */
	public void submitQuizPaper_wsj(MyExamPaper myExamPaper,int classid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// myExamPaper.setStatus(3);
			myExamPaper.setStatus(2);
			setQuizPaperStatus(myExamPaper);
			saveQuizPaperPasstime(myExamPaper);
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call sr_setscore_wsj(?,?)");
			ps.setInt(1, myExamPaper.getId());
			ps.setInt(2,classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("锟斤拷煽锟斤拷源锟斤拷锟斤拷?", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}}
		
		/**
		 * 锟斤拷锟斤拷学员锟皆撅拷锟斤拷锟斤拷锟�
		 * 
		 * @param userid
		 * @param roomid
		 * @param epid
		 * @throws ElException
		 */
		public void setStudyExampaperQuizcount_wsj(int userid, int roomid, int epid,int classid)
		throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			try {
				ct = DBConnection.getConnection();
				ps = ct
						.prepareStatement("update study_exampaper set quizcount=" +
								" (select count(id) from study_quizinfo sqi  " +
								" where sqi.roomid=? and sqi.userid=? and sqi.epid=? and sqi.classid=? ) " +
								"where roomid=? and userid=? and epid=? and classid=?");
				ps.setInt(1, roomid);
				ps.setInt(2, userid);
				ps.setInt(3, epid);
				ps.setInt(4, classid);
				ps.setInt(5, roomid);
				ps.setInt(6, userid);
				ps.setInt(7, epid);
				ps.setInt(8, classid);
				ps.executeUpdate();
			} catch (Exception e) {
				logger.error("锟斤拷锟斤拷学员锟皆撅拷锟斤拷锟斤拷锟斤拷锟�?", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
		
	public int getMypaperIdByRidanUid_wsj(int userid, int roomid, int epid,int classid)
		throws ElException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	int i = 0;
	try {
		ct = DBConnection.getConnection();
		ps = ct
				.prepareStatement("select sqi.quizcount myexamcount,erp.quizcount,sqi.avgscore,sqi.maxscore,sqi.classid "
						+ " from study_exampaper sqi left join exam_reps erp on erp.epid = sqi.epid and erp.roomid = sqi.roomid"
						+ " where sqi.userid =? and sqi.roomid= ? and sqi.epid = ? and sqi.classid=? ");

		ps.setInt(1, userid);
		ps.setInt(2, roomid);
		ps.setInt(3, epid);
		ps.setInt(4, classid);
		rs = ps.executeQuery();
		if (rs.next()) {
			if (rs.getInt(1) < rs.getInt(2)) // 未锟斤拷锟斤拷锟斤拷锟�
			{// if (!hasInQuizPaper(userid, roomid, epid )) {
//				i = intoQuizPaper(userid, roomid, epid);
				i = intoQuizPaper(userid, roomid, epid,classid,userid);
				ps = ct
						.prepareStatement("update study_exampaper set quizcount = quizcount+1 where userid = ? and epid = ? and roomid = ? and classid=?");
				ps.setInt(1, userid);
				ps.setInt(2, epid);
				ps.setInt(3, roomid);
				ps.setInt(4, classid);
				ps.executeUpdate();
				ps.close();
			} else {
				ps = ct
						.prepareStatement("select max(id) from study_quizinfo where userid = ? and epid = ? and roomid = ? and (status=0 or status =1)  and classid=? order by begintime");
				ps.setInt(1, userid);
				ps.setInt(2, epid);
				ps.setInt(3, roomid);
				ps.setInt(4, classid);
				rs = ps.executeQuery();
				if (rs.next()) {
					i = rs.getInt(1);
				}
				// i= -1;
			}
		}
	} catch (Exception e) {
		logger.error("锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟较拷锟斤拷?", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	return i;
	}
	/**
	 * 锟斤拷取锟斤拷锟铰匡拷锟斤拷学员锟缴硷拷锟斤拷息
	 */
	public MyRoom getMyStudyRoomInfo(int roomid, int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoom myroom  = new MyRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select  sr.ispassed,sr.begintime,sr.myscore from study_room sr  where roomid=? and userid=? and classid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if(rs.next()){
				myroom.setIspassed(rs.getInt("ispassed"));
				myroom.setBegintime(rs.getTimestamp("begintime"));
				myroom.setMyScore(rs.getFloat("myscore"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myroom;
	}

	public List<Question> getQid(int sqid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Question> ques = new ArrayList<Question>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from STUDY_QUESTIONS where sqid=? order by qid desc");
			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			while(rs.next()){
				Question q = new Question();
				q.setId(rs.getInt(3));
				q.setAnswer(rs.getString(7));
				ques.add(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ques;
	}

	public void addStudyQuestion(int sqid, int qid, String myanswer)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update STUDY_QUESTIONS set myanswer=? where sqid=? and qid=?");
			ps.setString(1, myanswer);
			ps.setInt(2, sqid);
			ps.setInt(3, qid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void updateNoAnswerQz(int sqid, int qid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update STUDY_QUESTIONS set myscore=0 where sqid=? and qid=?");
			ps.setInt(1, sqid);
			ps.setInt(2, qid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
}
	














