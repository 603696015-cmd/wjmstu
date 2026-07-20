package com.sopia.talentman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyKTRoom;
import com.sopia.studyman.entities.MyKTRoomC;
import com.sopia.studyman.entities.MyZTRoom;
import com.sopia.talentman.TalentConstants;
import com.sopia.talentman.dao.TalentDao;
import com.sopia.talentman.entities.KTRoom;
import com.sopia.talentman.entities.KTRoomColl;
import com.sopia.talentman.entities.ZTRoom;

public class TalentDaoImpl implements TalentDao {
	private static final Log logger = LogFactory.getLog(TalentDaoImpl.class);

	public List<ELUser> listExpert(ELUser user) throws ElException {
		List<ELUser> elUsers = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_EXPERT_LIST));
			rs = ps.executeQuery();
			while (rs.next()) {
				// eu.id,eu.realname,eu.age,eu.major,eu.studyDir,eu.company,c.name,eu.protitle
//				ELUser elUser = new ELUser(rs.getInt(1), rs.getString(2));
//				elUser.setAge(rs.getInt(3));
//				elUser.setMajor(rs.getString(4));
//				elUser.setStudyDir(rs.getString(5));
				// elUser.setCompany(new Company(rs.getInt(1),
				// rs.getString(6)));
//				elUser.setProtitle(rs.getString(7));
//
//				elUsers.add(elUser);
			}
		} catch (Exception e) {
			logger.error("人才库-专家列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUsers;
	}

	public void addTRoomColl(KTRoomColl troomcoll) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TRCOLL_ADD));
			ps.setString(1, troomcoll.getTitle());
			ps.setString(2, troomcoll.getDescription());
			ps.setInt(3, troomcoll.getCreater().getId());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-添加场次集失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<KTRoomColl> listTroomColls(int creater, int pageNow,
			int pageSize) throws ElException {
		List<KTRoomColl> trcs = new ArrayList<KTRoomColl>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TRCOLL_LIST));
			ps.setInt(1, creater);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				KTRoomColl trc = new KTRoomColl(rs.getInt(1), rs.getString(2));
				trc.setDescription(rs.getString(3));
				trc.setCreatetime(rs.getTimestamp(4));
				trcs.add(trc);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trcs;
	}

	public int listTroomCollsSize(int creater) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TRCOLL_LIST_SIZE));
			ps.setInt(1, creater);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public KTRoomColl getTRCbyId(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		KTRoomColl trc = new KTRoomColl();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TRCOLL_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				trc = new KTRoomColl(rs.getInt(1), rs.getString(2));
				trc.setDescription(rs.getString(3));
				trc.setCreatetime(rs.getTimestamp(4));
				trc.setCreater(new ELUser(rs.getInt(5), rs.getString(6)));
			}
		} catch (Exception e) {
			logger.error("人才库-场次集获取失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trc;
	}

	public void alterTRoomColl(KTRoomColl troomcoll) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TRCOLL_ALTER));
			ps.setString(1, troomcoll.getTitle());
			ps.setString(2, troomcoll.getDescription());
			ps.setInt(3, troomcoll.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-添加场次集失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<KTRoom> listTroomByTRCId(int cid) throws ElException {
		List<KTRoom> trs = new ArrayList<KTRoom>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_LIST_BYTRCID));
			ps.setInt(1, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				KTRoom tr = new KTRoom(rs.getInt(1), rs.getString(2));
				tr.setDescription(rs.getString(3));
				tr.setBegintime(rs.getTimestamp(4));
				tr.setEndtime(rs.getTimestamp(5));
				tr.setExampaper(new ExamPaper(rs.getInt(6), rs.getString(8)));
				// tr.setNorm(rs.getString(7));
				tr.setTrcoll(new KTRoomColl(rs.getInt(7)));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trs;
	}

	public void addTRoom(KTRoom room) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_ADD));
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setString(1, room.getTitle());
			ps.setString(2, room.getDescription());
			ps.setTimestamp(3, room.getBegintime());
			ps.setTimestamp(4, room.getEndtime());
			ps.setInt(5, room.getExampaper().getId());
			// ps.setString(6, room.getNorm());
			ps.setInt(6, room.getTrcoll().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public KTRoom getTRoomById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		KTRoom tr = new KTRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				tr = new KTRoom(rs.getInt(1), rs.getString(2));
				tr.setDescription(rs.getString(3));
				tr.setBegintime(rs.getTimestamp(4));
				tr.setEndtime(rs.getTimestamp(5));
				tr.setExampaper(new ExamPaper(rs.getInt(6), rs.getString(8)));
				// tr.setNorm(rs.getString(7));
				tr.setTrcoll(new KTRoomColl(rs.getInt(7), rs.getString(9)));
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tr;
	}

	public void alterTRoom(KTRoom room) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_ALTER));
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setString(1, room.getTitle());
			ps.setString(2, room.getDescription());
			ps.setTimestamp(3, room.getBegintime());
			ps.setTimestamp(4, room.getEndtime());
			ps.setInt(5, room.getExampaper().getId());
			// ps.setString(6, room.getNorm());
			// ps.setInt(6, room.getTrcoll().getId());
			ps.setInt(6, room.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> listAssignUsers(int rid, int pageNow, int pageSize)
			throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		List<ELUser> assignedUsers = new ArrayList<ELUser>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_ASSIGNED_USER_LIST));
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, rid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(5));
				eu.setDepartment(new Department(rs.getInt(3), rs.getString(4)));
				assignedUsers.add(eu);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return assignedUsers;
	}

	public int listAssignUsersSize(int rid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(TalentConstants.TALENT_TROOM_ASSIGNED_USER_LIST_SIZE));
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);

			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public boolean checkUserInTr(int userid, int trid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_CHECK_USERINTR));
			ps.setInt(1, trid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;

			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void troomAssign2User(int userid, int trid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_ASSIGN2USER));
			ps.setInt(1, trid);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void troomUAssign2User(int userid, int trid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_UASSIGN2USER));
			ps.setInt(1, trid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyKTRoomC> listTroomByUid(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyKTRoomC> trs = new ArrayList<MyKTRoomC>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_LIST_BYSTUID));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyKTRoomC mkc = new MyKTRoomC();
				KTRoomColl tr = new KTRoomColl(rs.getInt(1), rs.getString(2));
				tr.setDescription(rs.getString(3));
				tr.setCreatetime(rs.getTimestamp(4));
				tr.setCreater(new ELUser(rs.getInt(5), rs.getString(6)));
				mkc.setTotalscore(rs.getInt(7));
				mkc.setQuizcount(rs.getInt(8));
				mkc.setTroomcoll(tr);
				trs.add(mkc);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trs;
	}

	public int listTroomByUidSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_LIST_BYTSTUID_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> listTSByTRid(int trid, int userid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		List<ELUser> eus = new ArrayList<ELUser>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_USER_DEPLRID));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			int depid = 0;
			if (rs.next()) {
				depid = rs.getInt(1);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_TS_USER_LIST));
			ps.setInt(1, trid);
			ps.setInt(2, userid);
			ps.setInt(3, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(5));
				eu.setDepartment(new Department(rs.getInt(3), rs.getString(4)));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public List<ELUser> listXJByTRid(int trid, int userid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		List<ELUser> eus = new ArrayList<ELUser>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_USER_DEPLRID));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_XJ_USER_LIST));
			ps.setInt(1, trid);
			ps.setInt(2, userid);
			ps.setInt(3, lid);
			ps.setInt(4, rid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(5));
				eu.setDepartment(new Department(rs.getInt(3), rs.getString(4)));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public MyKTRoomC getMkTroomByTid(int id, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyKTRoomC mkc = new MyKTRoomC();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_BYTSTUID));
			ps.setInt(1, userid);
			ps.setInt(2, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				KTRoomColl tr = new KTRoomColl(rs.getInt(1), rs.getString(2));
				tr.setDescription(rs.getString(3));
				tr.setCreatetime(rs.getTimestamp(4));
				tr.setCreater(new ELUser(rs.getInt(5), rs.getString(6)));
				mkc.setTotalscore(rs.getInt(7));
				mkc.setQuizcount(rs.getInt(8));
				mkc.setTroomcoll(tr);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mkc;
	}

	public MyExamPaper getKtroomPaper(int userid, int qtroomid)
			throws ElException {
		MyExamPaper trs = new MyExamPaper();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select te.myAnswer,tr.epid from troom_epinfo te,troom tr where tr.id = te.trid and tr.id = ? and te.userid = ?");
			ps.setInt(1, qtroomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
//				trs.setMyAnswer(rs.getString(1));
				trs.setExamPaper(new ExamPaper(rs.getInt(2)));
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trs;
	}

	public List<MyKTRoom> listMkTroomByTid(int id, int userid)
			throws ElException {
		List<MyKTRoom> trs = new ArrayList<MyKTRoom>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_MYTROOM_LIST_BYSTUID));
			ps.setInt(1, userid);
			ps.setInt(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				KTRoom tr = new KTRoom(rs.getInt(1), rs.getString(2));
				tr.setDescription(rs.getString(3));
				tr.setBegintime(rs.getTimestamp(4));
				tr.setEndtime(rs.getTimestamp(5));
				tr.setExampaper(new ExamPaper(rs.getInt(6), rs.getString(8)));
				// tr.setNorm(rs.getString(7));
				tr.setTrcoll(new KTRoomColl(rs.getInt(7)));
				MyKTRoom myKTRoom = new MyKTRoom();
				myKTRoom.setTroom(tr);
				myKTRoom.setMyScore(rs.getInt(9));
				trs.add(myKTRoom);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trs;
	}

	public void evalTroom(MyZTRoom myZTRoom) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_EVAL));
			// trid,evaler,tester,evaldetail,evaltype,evaltime
			ps.setInt(1, myZTRoom.getZtroom().getId());
			ps.setInt(2, myZTRoom.getEvaler().getId());
			ps.setInt(3, myZTRoom.getTester().getId());
			ps.setString(4, myZTRoom.getEvaldetail());
			ps.setInt(5, myZTRoom.getEvaltype());
			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_SCORE_SET));
			ps.setInt(1, myZTRoom.getTester().getId());
			ps.setInt(2, myZTRoom.getZtroom().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterevalTroom(MyZTRoom myZTRoom) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_EVAL_ALTER));
			// trid,evaler,tester,evaldetail,evaltype,evaltime
			ps.setString(1, myZTRoom.getEvaldetail());
			// ps.setInt(2, myZTRoom.getEvaltype());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, myZTRoom.getZtroom().getId());
			ps.setInt(4, myZTRoom.getEvaler().getId());
			ps.setInt(5, myZTRoom.getTester().getId());
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_SCORE_SET));
			ps.setInt(1, myZTRoom.getTester().getId());
			ps.setInt(2, myZTRoom.getZtroom().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkevalTroom(MyZTRoom myZTRoom) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_EVAL_CHECK));
			// trid,evaler,tester,evaldetail,evaltype,evaltime
			// ps.setString(1, myZTRoom.getEvaldetail());
			// ps.setInt(2, myZTRoom.getEvaltype());
			// ps.setTimestamp(2, myZTRoom.getEvaltime());
			ps.setInt(1, myZTRoom.getZtroom().getId());
			ps.setInt(2, myZTRoom.getEvaler().getId());
			ps.setInt(3, myZTRoom.getTester().getId());
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void evalquizsave(MyExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_EVAL_QUIZ_SAVE));
//			ps.setString(1, examPaper.getMyAnswer());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, examPaper.getExamRoom().getId());
			ps.setInt(4, examPaper.getTester().getId());

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("完成测评考试答卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void evalquizsubmit(MyExamPaper examPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_EVAL_QUIZ_SUBMIT));
//			ps.setString(1, examPaper.getMyAnswer());
			ps.setInt(2, examPaper.getPassTime());
			ps.setInt(3, examPaper.getExamRoom().getId());
			ps.setInt(4, examPaper.getTester().getId());

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("完成测评考试答卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void intoTroomEp(int uid, int troomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_EVAL_QUIZ_INTO));
			ps.setInt(1, uid);
			ps.setInt(2, troomid);
			ps.setInt(3, 1);
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("进入考试出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean hasInTRoom(int uid, int troomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_EVAL_QUIZ_CHECKIN));
			ps.setInt(1, uid);
			ps.setInt(2, troomid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("检测是否已经进入考试出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public MyZTRoom getMyTRoomByUidAndTRid(int uid, int trid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyZTRoom m = new MyZTRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_MYSCORE));
			ps.setInt(1, uid);
			ps.setInt(2, trid);
			rs = ps.executeQuery();
			if (rs.next()) {
				// m.setMyScore(rs.getInt(1));
				m.setZjScore(rs.getInt(2));
				m.setTsScore(rs.getInt(3));
				m.setSjscore(rs.getInt(4));
			}
		} catch (Exception e) {
			logger.error("检测是否已经进入考试出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return m;
	}

	public List<MyZTRoom> listMyTroomsByUandT(int depid, int subdep, ELUser eu,
			KTRoom troom, int pageNow, int pageSize) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<MyZTRoom> ms = new ArrayList<MyZTRoom>();
//		try {
//			String username = "";
//			String realname = "";
//			String email = "";
//			String rtitle = troom == null ? "" : troom.getTitle().trim();
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//				if (null != eu.getEmail())
//					email = eu.getEmail().trim();
//			}
//			ct = DBConnection.getConnection();
//			if (subdep == ElConstants.SUBOP_YES) {
//				Department dep = new Department();
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//				ps.setInt(1, depid);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					dep.setId(rs.getInt(1));
//					dep.setLid(rs.getInt(2));
//					dep.setRid(rs.getInt(3));
//				}
//				ps.close();
//				rs.close();
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(TalentConstants.TALENT_TROOM_MYSCORE_SUB_LIST));
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setString(3, "%" + email + "%");
//				ps.setInt(4, dep.getLid());
//				ps.setInt(5, dep.getRid());
//				ps.setString(6, "%" + rtitle + "%");
//				ps.setInt(7, pageNow);
//				ps.setInt(8, pageSize);
//			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(TalentConstants.TALENT_TROOM_MYSCORE_LIST));
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setString(3, "%" + email + "%");
//				ps.setInt(4, depid);
//				ps.setString(5, "%" + rtitle + "%");
//				ps.setInt(6, pageNow);
//				ps.setInt(7, pageSize);
//			}
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				ELUser elUser = new ELUser();
//				elUser.setId(rs.getInt(1));
//				elUser.setUsername(rs.getString(2));
//				elUser.setRealname(rs.getString(3));
//				elUser.setUserno(rs.getString(4));
//				elUser.setEmail(rs.getString(5));
//				elUser.setRole(new ElRole(rs.getInt(6), rs.getString(10)));
//				// elUser.setCompany(new Company(rs.getInt(7),
//				// rs.getString(8)));
//				elUser.setDepartment(new Department(rs.getInt(8), rs
//						.getString(9)));
//
//				MyZTRoom m = new MyZTRoom();
//				m.setTester(elUser);
//				// m.setMyScore(rs.getInt(12));
//				m.setZjScore(rs.getInt(12));
//				m.setTsScore(rs.getInt(13));
//				m.setSjscore(rs.getInt(14));
//				// m.setTroom(new KTRoom(rs.getInt(16), rs.getString(17)));
//				ms.add(m);
//			}
//		} catch (Exception e) {
//			logger.error("检测是否已经进入考试出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return ms;
			return null;
	}

	public int listMyTroomsByUandTSize(int depid, int subdep, ELUser eu,
			KTRoom troom) throws ElException {/*
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			String email = "";
			String rtitle = troom == null ? "" : troom.getTitle().trim();
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
				if (null != eu.getEmail())
					email = eu.getEmail().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
				Department dep = new Department();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
				}
				ps.close();
				rs.close();
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(TalentConstants.TALENT_TROOM_MYSCORE_SUB_LIST_SIZE));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + email + "%");
				ps.setInt(4, dep.getLid());
				ps.setInt(5, dep.getRid());
				ps.setString(6, "%" + rtitle + "%");
			} else {
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(TalentConstants.TALENT_TROOM_MYSCORE_LIST_SIZE));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + email + "%");
				ps.setInt(4, depid);
				ps.setString(5, "%" + rtitle + "%");
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("检测是否已经进入考试出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	*/
		return 0;
	}

	public void addZtroom(ZTRoom ztroom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_ADD));
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setString(1, ztroom.getTitle());
			ps.setString(2, ztroom.getDescription());
			ps.setTimestamp(3, ztroom.getBegintime());
			ps.setTimestamp(4, ztroom.getEndtime());
			ps.setString(5, ztroom.getNorm());
			ps.setInt(6, ztroom.getCreater().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int listZtroomByUid(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_LIST_BYUID_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("人才库-客观场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ZTRoom> listZtroomByUid(int userid, int pageNow, int pageSize)
			throws ElException {
		List<ZTRoom> trs = new ArrayList<ZTRoom>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_LIST_BYUID));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ZTRoom tr = new ZTRoom(rs.getInt(1), rs.getString(2));
				tr.setDescription(rs.getString(3));
				tr.setBegintime(rs.getTimestamp(4));
				tr.setEndtime(rs.getTimestamp(5));
				tr.setNorm(rs.getString(6));
				trs.add(tr);
			}
		} catch (Exception e) {
			logger.error("人才库-客观场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trs;
	}

	public void alterZtroom(ZTRoom ztroom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_ALTER));
			ps.setString(1, ztroom.getTitle());
			ps.setString(2, ztroom.getDescription());
			ps.setTimestamp(3, ztroom.getBegintime());
			ps.setTimestamp(4, ztroom.getEndtime());
			ps.setString(5, ztroom.getNorm());
			ps.setInt(6, ztroom.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ZTRoom getZtroomById(int id) throws ElException {
		ZTRoom tr = new ZTRoom();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				tr = new ZTRoom(rs.getInt(1), rs.getString(2));
				tr.setDescription(rs.getString(3));
				tr.setBegintime(rs.getTimestamp(4));
				tr.setEndtime(rs.getTimestamp(5));
				tr.setNorm(rs.getString(6));
				tr.setCreater(new ELUser(rs.getInt(7), rs.getString(8)));
			}
		} catch (Exception e) {
			logger.error("人才库-客观场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tr;
	}

	public List<ELUser> listAssignZUsers(int zrid, int pageNow, int pageSize)
			throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		List<ELUser> assignedUsers = new ArrayList<ELUser>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_ASSIGNED_ZUSER_LIST));
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, zrid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(5));
				eu.setDepartment(new Department(rs.getInt(3), rs.getString(4)));
				assignedUsers.add(eu);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return assignedUsers;
	}

	public int listAssignZUsersSize(int zrid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(TalentConstants.TALENT_ZTROOM_ASSIGNED_ZUSER_LIST_SIZE));
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, zrid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public boolean checkZUserInTr(int userid, int trid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_CHECK_USERINTR));
			ps.setInt(1, trid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void ztroomAssign2User(int userid, int trid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_ASSIGN2USER));
			ps.setInt(1, trid);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void ztroomUAssign2User(int userid, int trid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_UASSIGN2USER));
			ps.setInt(1, trid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyZTRoom> listMyZtroomByStuId(int userid, int pageNow,
			int pageSize) throws ElException {
		List<MyZTRoom> trs = new ArrayList<MyZTRoom>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_MYZTROOM_LIST_BYSTID));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ZTRoom tr = new ZTRoom(rs.getInt(1), rs.getString(2));
				tr.setDescription(rs.getString(3));
				tr.setBegintime(rs.getTimestamp(4));
				tr.setEndtime(rs.getTimestamp(5));
				tr.setNorm(rs.getString(6));
				MyZTRoom m = new MyZTRoom();
				tr.setCreater(new ELUser(rs.getInt(7), rs.getString(8)));
				m.setZtroom(tr);
				m.setZjScore(rs.getInt(9));
				m.setTsScore(rs.getInt(10));
				m.setSjscore(rs.getInt(11));
				trs.add(m);
			}
		} catch (Exception e) {
			logger.error("人才库-客观场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trs;
	}

	public int listMyZTroomByStuIdSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_MYZTROOM_LIST_BYSTID_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("人才库-客观场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public MyZTRoom getMZTroomByTETId(MyZTRoom myZTRoom) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		MyZTRoom mt = new MyZTRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_EVAL_EVALDETAIL));
			ps.setInt(1, myZTRoom.getZtroom().getId());
			ps.setInt(2, myZTRoom.getEvaler().getId());
			ps.setInt(3, myZTRoom.getTester().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				mt.setEvaldetail(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mt;
	}

	public List<MyKTRoomC> listStatKtroom(int trid) throws ElException {/*
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyKTRoomC> trs = new ArrayList<MyKTRoomC>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_TROOM_STAT_LIST));
			ps.setInt(1, trid);
			rs = ps.executeQuery();
			while (rs.next()) {
				// eu.id
				// ,eu.realname,eu.username,eu.depid,dep.name,eu.sex,eu.age,(select
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				eu.setSex(rs.getString(6));
				eu.setAge(rs.getInt(7));
				MyKTRoomC mkc = new MyKTRoomC();
				mkc.setTotalscore(rs.getInt(8));
				mkc.setQuizcount(rs.getInt(9));
				mkc.setTester(eu);
				trs.add(mkc);
			}
		} catch (Exception e) {
			logger.error("添加新调查失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trs;
	*/
		return null;
	}

	public List<MyZTRoom> listMyZtroomStat(int trid) throws ElException {/*
		List<MyZTRoom> trs = new ArrayList<MyZTRoom>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(TalentConstants.TALENT_ZTROOM_STAT_LIST));
			ps.setInt(1, trid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				eu.setSex(rs.getString(6));
				eu.setAge(rs.getInt(7));
				MyZTRoom m = new MyZTRoom();
				// tr.setCreater(new ELUser(rs.getInt(7),rs.getString(8)));
				// m.setZtroom(tr);
				m.setZjScore(rs.getInt(8));
				m.setTsScore(rs.getInt(9));
				m.setSjscore(rs.getInt(10));
				m.setTester(eu);
				trs.add(m);
			}
		} catch (Exception e) {
			logger.error("人才库-客观场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return trs;
	*/
		return null;
	}
}
