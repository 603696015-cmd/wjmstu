package com.sopia.courseman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.impl.ClassDaoImpl;
import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.CRE_note;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ErPara;
import com.sopia.courseman.entities.EroomBatch;
import com.sopia.courseman.entities.EroomBatchLib;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.ExamRoomAuditDescribes;
import com.sopia.courseman.entities.Examprac;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Typelrid;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.Question;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.SimpleRemack;

public class EroomDaoImpl extends ElNodeDao implements EroomDao {
	private static final Log logger = LogFactory.getLog(EroomDaoImpl.class);

	public int addEroomLib(EroomLib eroomLib) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			// addNode(ct, eroomLib, "eroom_lib", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOMLIB_ADD));
			ps.setString(1, eroomLib.getName());
			ps.setString(2, eroomLib.getDescription());
			ps.setInt(3, eroomLib.getParent().getId());
			ps.setInt(4, eroomLib.getLid());
			ps.setInt(5, eroomLib.getRid());
			ps.executeUpdate();
			// TODO 获取 刚添加的id
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('eroom_lib') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select eroomlib_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next()) {
				eroomLib.setId(rs.getInt(1));
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void alterEroomLib(EroomLib eroomLib) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// alterNode(ct, eroomLib, "eroom_lib", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOMLIB_ALTER));
			ps.setString(1, eroomLib.getName());
			ps.setString(2, eroomLib.getDescription());
			ps.setInt(3, eroomLib.getParent().getId());
			ps.setInt(4, eroomLib.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void deleteeroomLib(Connection ct, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {// TODO 删除课程类别
			// 删除节点信息
			// EroomLib eroomLib = new EroomLib(id);
			// deleteNode(ct, eroomLib, "eroom_lib", "1 = 1");
			// 删除基本信息
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOMLIB_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除课程类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新考场库的状态
	 * 
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deleteeroomLibNot(int id) throws ElException {
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eroom_lib set status=1,lid=0,rid=0 where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新考场库的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteEroomLib(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 删除课程类别
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from eroom_lib where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();

			/*
			 * int parentid = 0; ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(CourseConstants.EROOMLIB_QUERY_BYID)); ps.setInt(1, id);
			 * rs = ps.executeQuery(); if (rs.next()) { parentid = rs.getInt(4); }
			 * rs.close(); // 将该类别下课程设置成上级类别 ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(CourseConstants.EROOMLIB_EROOM_QUERY_BYCTID));
			 * ps.setInt(1, id); rs = ps.executeQuery(); while (rs.next()) {
			 * PreparedStatement ps1 = ct.prepareStatement(ElQuerySql
			 * .getSQL(CourseConstants.EROOMLIB_EROOM_EROOMLIB_SET));
			 * ps1.setInt(1, parentid); ps1.setInt(2, rs.getInt(1));
			 * ps1.executeUpdate(); } rs.close(); // 将该类别下类别设置成上级类别 ps =
			 * ct.prepareStatement(ElQuerySql
			 * .getSQL(CourseConstants.EROOMLIB_PARENT_SET)); ps.setInt(1,
			 * parentid); ps.setInt(2, id);
			 * 
			 * deleteeroomLib(ct, id);
			 */

		} catch (Exception e) {
			logger.error("删除课程类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteEroomLibAndSub(int id) throws ElException {
		// TODO 删除课程类别
	}

	// hwc3
	public EroomLib getEroomLibById(int id) throws ElException {
		EroomLib eroomLib = new EroomLib();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.EROOMLIB_QUERY_BYID));
			ps = ct
					.prepareStatement("select ct1.id,ct1.name,ct1.description,ct1.parentid,ct2.name,ct1.lid,ct1.rid from "
							+ " eroom_lib ct1 left join eroom_lib ct2 on ct1.parentid = ct2.id and ct2.status!=1 where ct1.id= ? and ct1.status!=1");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				eroomLib.setId(rs.getInt(1));
				eroomLib.setName(rs.getString(2));
				eroomLib.setDescription(rs.getString(3));
				eroomLib.setParent(new EroomLib(rs.getInt(4), rs.getString(5)));
				eroomLib.setLid(rs.getInt(6));
				eroomLib.setRid(rs.getInt(7));
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eroomLib;
	}

	public List<EroomLib> getEroomLibChilds(int parentid) throws ElException {
		List<EroomLib> cts = new ArrayList<EroomLib>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOMLIB_QUERY_CHILD));
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				EroomLib eroomLib = new EroomLib();
				eroomLib.setId(rs.getInt(1));
				eroomLib.setName(rs.getString(2));
				eroomLib.setParent(new EroomLib(rs.getInt(3)));
				eroomLib.setLid(rs.getInt(4));
				eroomLib.setRid(rs.getInt(5));
				cts.add(eroomLib);
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cts;
	}

	public EroomLib getEroomLibRoot() throws ElException {
		EroomLib eroomLib = new EroomLib();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOMLIB_QUERY_BYPID));
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			while (rs.next()) {
				eroomLib.setId(rs.getInt(1));
				eroomLib.setName(rs.getString(2));
				eroomLib.setParent(new EroomLib(rs.getInt(3)));
				eroomLib.setLid(rs.getInt(4));
				eroomLib.setRid(rs.getInt(5));
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eroomLib;
	}

	public EroomLib getEroomLibTree(int from, int stop, boolean containStop)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		EroomLib cltype = null;
		try {
			if (from == 0) {
				cltype = getEroomLibRoot();
			} else {
				cltype = getEroomLibById(from);
			}
			ct = DBConnection.getConnection();
			cltype
					.setChild(getChilds(ct, cltype.getId(), stop, containStop,
							0));
		} catch (Exception e) {
			logger.error("培训班类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}

	// hwc2
	private EroomLib getEroomLibTree(int from, int stop, boolean containStop,
			int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		EroomLib cltype = null;
		try {
			cltype = getEroomLibById(from);
			if (cltype == null || cltype.getId() == 0) {
				return cltype;
			}
			cltype.setLevel(level);
			ct = DBConnection.getConnection();
			cltype.setChild(getChilds(ct, cltype.getId(), stop, containStop,
					level));
		} catch (Exception e) {
			logger.error("培训班类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}

	// hwc1
	public EroomLib getEroomLibTree(int userid, String op, int stopid,
			boolean containStop) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// EroomLib dep = op.equals("op") ? new EroomLib(1, "可操作的考场库")
		// : new EroomLib(1, "可使用的考场库");
		EroomLib dep = new EroomLib(ElConstants.USER_OP_LIB, "可操作的考场库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from eroomlib_" + op
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<EroomLib> list = new ArrayList<EroomLib>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					EroomLib depc = getEroomLibTree(depid, stopid, containStop,
							1);
					if (depc == null || depc.getId() == 0) {
						continue;
					}
					depc.setParent(dep);
					list.add(depc);
					nlist.add(depc);
				}
			}
			dep.setChild(list);
			dep.setNchild(nlist);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	// hwc4
	private List<EroomLib> getChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<EroomLib> deps = new ArrayList<EroomLib>();
		// PreparedStatement ps = ct.prepareStatement(ElQuerySql
		// .getSQL(CourseConstants.EROOMLIB_QUERY_BYPID));
		PreparedStatement ps = ct
				.prepareStatement("select id,name,parentid,lid,rid from eroom_lib where parentid=? and status!=1 order by id");
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			EroomLib dep = new EroomLib(rstemp.getInt(1), rstemp.getString(2));
			// dep.setDescription(rstemp.getString(3));
			dep.setParent(new EroomLib(rstemp.getInt(3)));
			dep.setLevel(level);
			if (dep.getId() != stop)
				dep.setChild(getChilds(ct, dep.getId(), stop, containStop,
						level));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		}
		ps.close();
		rstemp.close();
		return deps;
	}

	public void addOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into eroomlib_" + type
					+ "_user(userid,depid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkOpUsers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from eroomlib_" + type
					+ "_user where userid = ? and depid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from eroomlib_" + type
					+ "_user where userid = ? and depid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> getOpUsers(String type, int depid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from eroomlib_"
							+ type
							+ "_user du left join eluser eu on eu.id = du.userid where du.depid = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return us;
	}

	public List<ExamRoom> listErWithoutCourse(int erlibid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOMLIB_LRID));// select id,lid,rid
			// from eroom_lib
			// where id=?
			ps.setInt(1, erlibid);
			rs = ps.executeQuery();
			int lid = 0, rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOM_WHITHOUT_COURSE));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setValid(rs.getInt(10));
				er.setEpsize(rs.getInt(11));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * eroomLibTree 权限树 erlibid 树id Hwc
	 */
	public List<ExamRoom> listErWithoutCourse(EroomLib eroomLibTree,
			int erlibid, int role, String sqlW, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();

		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			// String sql="";//--/
			// ps = ct.prepareStatement("select * from (select t.* ,rownum rn
			// from (select er.id , er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid)"
			// +
			// " from exam_room er left join eroom_lib erlib on
			// erlib.id=er.erlibid left join exam_reps erep on erep.roomid =
			// er.id ," +
			// " (select * from eroom_lib where id in("+ids+") ) ct where
			// er.iscommon=1 and ct.id=er.erlibid " +
			// " group by er.id , er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid
			// order by er.begintime desc)t where rownum<=?) where rn>=?");
			String sql = "select * from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid),el.realname";
			sql += " ,er.avalid,er.uvalid from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id ,";
			sql += " (select * from eroom_lib where  id in("
					+ ids
					+ ") ) ct where er.iscommon=1 and er.valid!=9 and ct.id=er.erlibid "
					+ sqlW;
			sql += " group by er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,el.realname,er.avalid,er.uvalid order by er.begintime desc)t where rownum<=?) where rn>=?";
			// 增加考场创建者一列进行显示
			// ps = ct.prepareStatement("select * from (select t.* ,rownum rn
			// from (select er.id , er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid),el.realname"
			// +
			// " ,er.avalid,er.uvalid from eluser el inner join exam_room er on
			// el.id=er.createrid left join eroom_lib erlib on
			// erlib.id=er.erlibid left join exam_reps erep on erep.roomid =
			// er.id ," +
			// " (select * from eroom_lib where id in("+ids+") ) ct where
			// er.iscommon=1 and er.valid!=9 and ct.id=er.erlibid " +sqlW+
			// " group by er.id , er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,el.realname,er.avalid,er.uvalid
			// order by er.begintime desc)t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow); // inner join eluser el on
			// er.createrid=el.id
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setValid(rs.getInt(10));
				er.setEpsize(rs.getInt(11));
				er.setAvalid(rs.getInt(13));
				er.setUvalid(rs.getInt(14));
				user = new ELUser();
				user.setRealname(rs.getString("realname"));
				er.setCreater(user);
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 分配考生list显示 eroomLibTree 权限树 erlibid 树id Hwc
	 */
	// public List<ExamRoom> listErWithoutCourse(EroomLib eroomLibTree,int
	// erlibid, int role ,String sqlW,ExamRoom examRoom, int pageNow,
	// int pageSize) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<ExamRoom> ers = new ArrayList<ExamRoom>();
	//		
	// String x = Integer.toString(erlibid);
	// String ids = createExamRoomLibId(eroomLibTree,erlibid);
	// if(role != 1 && !ids.equals(x) )//角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
	// ,当角色不为1时ids的只有一个根节点时也不截取
	// ids = erlibid == 1?ids.substring(x.length()+1,ids.length()):ids;
	// //当id等于虚拟根时,从所有的id中去掉虚拟根id
	// try {
	// ct = DBConnection.getConnection();
	// String sql="select * from (select t.* ,rownum rn from (select er.id ,
	// er.title, er.begintime,
	// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid),el.realname";
	// sql+=" ,er.avalid,er.uvalid,c.id cid,c.name cname,er.isApplication from
	// eluser el inner join exam_room er on el.id=er.createrid left join course
	// c on c.id=er.courseid left join eroom_lib erlib on erlib.id=er.erlibid
	// left join exam_reps erep on erep.roomid = er.id ,";
	// sql+=" (select * from eroom_lib where id in("+ids+") ) ct where
	// er.valid!=9 and ct.id=er.erlibid " +sqlW;
	// if(examRoom!=null){
	// if(examRoom.getTitle()!=null&&!examRoom.getTitle().equals("")){
	// sql+= " and er.title like '%"+examRoom.getTitle()+"%'";
	// }
	// if(examRoom.getValid()!=-1){
	// sql+=" and er.valid="+examRoom.getValid();
	// }
	// if(examRoom.getBegintime()!=null){
	// sql+=" and er.begintime >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss")
	// .format(examRoom.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
	// }
	// if(examRoom.getEndtime()!=null){
	// sql+=" and er.endtime <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss")
	// .format(examRoom.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
	// }
	// if(examRoom.getClassid()==-1){
	// sql+=" and er.classid=-1";
	// }else if(examRoom.getClassid()==0){
	// sql+=" and er.classid=0";
	// }else if(examRoom.getClassid()==1){
	// sql+=" and er.classid>0";
	// }
	// }else{
	// sql+=" and er.classid=-1";
	// }
	// sql+=" group by er.id , er.title, er.begintime,
	// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,el.realname,er.avalid,er.uvalid,c.id,c.name,er.isApplication
	// order by er.begintime desc)t where rownum<=?) where rn>=?";
	// //增加考场创建者一列进行显示
	// // ps = ct.prepareStatement("select * from (select t.* ,rownum rn from
	// (select er.id , er.title, er.begintime,
	// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid),el.realname"
	// +
	// // " ,er.avalid,er.uvalid from eluser el inner join exam_room er on
	// el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid left
	// join exam_reps erep on erep.roomid = er.id ," +
	// // " (select * from eroom_lib where id in("+ids+") ) ct where
	// er.iscommon=1 and er.valid!=9 and ct.id=er.erlibid " +sqlW+
	// // " group by er.id , er.title, er.begintime,
	// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,el.realname,er.avalid,er.uvalid
	// order by er.begintime desc)t where rownum<=?) where rn>=?");
	// ps=ct.prepareStatement(sql);
	// ps.setInt(1, pageNow); //inner join eluser el on er.createrid=el.id
	// ps.setInt(2, pageSize);
	// rs = ps.executeQuery();
	// ELUser user=null;
	// while (rs.next()) {
	// ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
	// er.setBegintime(rs.getTimestamp(3));
	// er.setEndtime(rs.getTimestamp(4));
	// er.setLocation(rs.getString(5));
	// er.setPassgrade(rs.getFloat(6));
	// er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
	// er.setType(rs.getInt(9));
	// er.setValid(rs.getInt(10));
	// er.setEpsize(rs.getInt(11));
	// er.setAvalid(rs.getInt(13));
	// er.setUvalid(rs.getInt(14));
	// er.setIsApplication(rs.getInt("isApplication"));
	// user=new ELUser();
	// user.setRealname(rs.getString("realname"));
	// er.setCreater(user);
	// er.setCourse(new Course(rs.getInt("cid"), rs.getString("cname")));
	// //er.setUsersize(getEroomUsers("valids", rs.getInt(1)).size());//复核人员人数
	// er.setUsersize(this.getExamAllStudy(er.getId()));
	// er.setUsize(checkEroomIsUsers("valids", rs.getInt(1))== true ? 1 : 0);
	// er.setPlanNumber(getEroomPlanNumber(rs.getInt(1)));
	// ers.add(er);
	// }
	// } catch (Exception e) {
	// logger.error("获取考试场次列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return ers;
	// }
	public List<ExamRoom> listErWithoutCourse(int[] erids,
			EroomLib eroomLibTree, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		// int lid = 0, rid = 0;
		String str = "";
		// List<int[]> ints=new ArrayList<int[]>();
		int minint = 1;
		int maxint = 1;
		int j = 0;
		int[] is = new int[1000];
		try {
			ct = DBConnection.getConnection();
			if (erids != null) {
				for (int i = 0; i < erids.length; i++) {
					if (erids.length == 1)
						str += erids[i];
					str += createEroomTypeId(eroomLibTree, erids[i]).equals(
							"null") ? ("," + erids[i]) : createEroomTypeId(
							eroomLibTree, erids[i]);
				}
				ps = ct
						.prepareStatement("select id,lid,rid from eroom_lib where id in ("
								+ str + ") order by lid");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (j == 0)
						minint = rs.getInt(2);
					is[j] = rs.getInt(2);
					is[j + 1] = rs.getInt(3);
					j += 2;
				}
				rs.close();
			}
			for (int x = 0; x < is.length; x++) {
				if (is[x] > maxint) {
					maxint = is[x];
				}
			}
			// for (int[] integers : ints) {
			ps = ct
					.prepareStatement("select * from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id where er.iscommon=1 and erlib.lid>=? and erlib.rid<=?  group by er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid order by er.begintime desc)t where rownum<=?) where rn>=?");
			ps.setInt(1, minint);
			ps.setInt(2, maxint);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setValid(rs.getInt(10));
				er.setEpsize(rs.getInt(11));
				ers.add(er);
			}
			// }

		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int listErWithoutCourseSize(int[] erids, EroomLib eroomLibTree,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// List<ExamRoom> ers = new ArrayList<ExamRoom>();
		// int lid = 0, rid = 0;
		String str = "";
		// List<int[]> ints=new ArrayList<int[]>();
		int minint = 1;
		int maxint = 1;
		int j = 0;
		int[] is = new int[1000];
		try {
			ct = DBConnection.getConnection();
			if (erids != null) {
				for (int i = 0; i < erids.length; i++) {
					if (erids.length == 1)
						str += erids[i];
					str += createEroomTypeId(eroomLibTree, erids[i]).equals(
							"null") ? ("," + erids[i]) : createEroomTypeId(
							eroomLibTree, erids[i]);
				}
				ps = ct
						.prepareStatement("select id,lid,rid from eroom_lib where id in ("
								+ str + ") order by lid");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (j == 0)
						minint = rs.getInt(2);
					is[j] = rs.getInt(2);
					is[j + 1] = rs.getInt(3);
					j += 2;
				}
				rs.close();
			}
			for (int x = 0; x < is.length; x++) {
				if (is[x] > maxint) {
					maxint = is[x];
				}
			}
			// for (int[] integers : ints) {
			ps = ct
					.prepareStatement("select count(*) from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id where er.iscommon=1 and erlib.lid>=? and erlib.rid<=?  group by er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid order by er.begintime desc)t where rownum<=?) where rn>=?");
			ps.setInt(1, minint);
			ps.setInt(2, maxint);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
			// }

		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listErWithoutCourseSize(int erlibid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOMLIB_LRID));
			ps.setInt(1, erlibid);
			rs = ps.executeQuery();
			int lid = 0, rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from exam_room er left join eroom_lib lib on lib.id = er.erlibid where  er.iscommon=1 and lib.lid>=? and lib.rid<=? ");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listErWithoutCourseSize(EroomLib eroomLibTree, int erlibid,
			int role, String sqlW) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x)) {// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from  (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid)"
							+ " from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id ,"
							+ " (select * from eroom_lib where  id in("
							+ ids
							+ ") ) ct where er.iscommon=1 and er.valid!=9 and ct.id=er.erlibid"
							+ sqlW
							+ " group by er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid order by er.begintime desc)");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 分配考生list数量
	 * 
	 * @param eroomLibTree
	 * @param erlibid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	// public int listErWithoutCourseSize(EroomLib eroomLibTree,int erlibid ,
	// int role,String sqlW,ExamRoom examRoom) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	//		
	//
	// String x = Integer.toString(erlibid);
	// String ids = createExamRoomLibId(eroomLibTree,erlibid);
	// if(role != 1 && !ids.equals(x) ){//角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
	// ,当角色不为1时ids的只有一个根节点时也不截取
	// ids = erlibid == 1?ids.substring(x.length()+1,ids.length()):ids;
	// //当id等于虚拟根时,从所有的id中去掉虚拟根id
	// }
	// try {
	// ct = DBConnection.getConnection();
	// String sql="select count(*) from (select er.id , er.title, er.begintime,
	// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid)
	// from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left
	// join exam_reps erep on erep.roomid = er.id , (select * from eroom_lib
	// where id in("+ids+") ) ct where er.valid!=9 and ct.id=er.erlibid ";
	// //sql+=" from eluser el inner join exam_room er on el.id=er.createrid
	// left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep
	// on erep.roomid = er.id ,";
	// //sql+=" (select * from eroom_lib where id in("+ids+") ) ct where
	// er.iscommon=1 and er.valid!=9 and ct.id=er.erlibid " +sqlW;
	// if(examRoom!=null){
	// if(examRoom.getTitle()!=null&&!examRoom.getTitle().equals("")){
	// sql+= " and er.title like '%"+examRoom.getTitle()+"%'";
	// }
	// if(examRoom.getValid()!=-1){
	// sql+=" and er.valid="+examRoom.getValid();
	// }
	// if(examRoom.getBegintime()!=null){
	// sql+=" and er.begintime >= to_date('"+ new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss")
	// .format(examRoom.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
	// }
	// if(examRoom.getEndtime()!=null){
	// sql+=" and er.endtime <= to_date('"+ new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss")
	// .format(examRoom.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
	// }
	// if(examRoom.getClassid()==-1){
	// sql+=" and er.classid=-1";
	// }else if(examRoom.getClassid()==0){
	// sql+=" and er.classid=0";
	// }else if(examRoom.getClassid()==1){
	// sql+=" and er.classid>0";
	// }
	// }else{
	// sql+=" and er.classid=-1";
	// }
	// sql+=" group by er.id , er.title, er.begintime,
	// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid
	// order by er.begintime desc)";
	// ps = ct.prepareStatement(sql);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("获取考试场次列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }
	public void addExamRoom(ExamRoom examRoom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.EROOM_ADD));
			ps = ct
					.prepareStatement("insert into exam_room( courseid,createrid, title,description, location, begintime, endtime,iscommon, passgrade,score,erlibid,type,isMacBand,isIpLimit,ipStart,ipEnd,classid,isApplication,examcount,markingManner,mainimg,passmanner,depName,jingzhong,pwdneed,pwdtime,pwd,cacheepsize,cacheeprefresh,epqsort,autoassign,cpid,sortid,islink,isxianzhikaopin,examsforday,jiangeshijian,stuViewResult) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, examRoom.getCourse().getId());
			ps.setInt(2, examRoom.getCreater().getId());
			ps.setString(3, examRoom.getTitle());
			ps.setString(4, examRoom.getDescription());
			ps.setString(5, examRoom.getLocation());
			ps.setTimestamp(6, examRoom.getBegintime());
			ps.setTimestamp(7, examRoom.getEndtime());
			ps.setInt(8, examRoom.getIscommon());
			ps.setFloat(9, examRoom.getPassgrade());
			ps.setFloat(10, examRoom.getScore());
			ps.setInt(11, examRoom.getEroomLib().getId());
			ps.setInt(12, examRoom.getType());
			// ps.setInt(13, examRoom.getPrac().getId());
			// ps.setInt(14, examRoom.getPractimes());
			// ps.setInt(15, examRoom.getPracscore());
			ps.setInt(13, examRoom.getIsMacBand());
			ps.setInt(14, examRoom.getIsIpLimit());
			ps.setString(15, examRoom.getIpStart());
			ps.setString(16, examRoom.getIpEnd());
			ps.setInt(17, examRoom.getClassid());
			ps.setInt(18, examRoom.getIsApplication());
			ps.setInt(19, examRoom.getExamcount());
			ps.setInt(20, examRoom.getMarkingManner());
			ps.setString(21, examRoom.getMainimg());
			ps.setInt(22, examRoom.getPassmanner());
			ps.setString(23, examRoom.getDepName());
			ps.setString(24, examRoom.getJingzhong());
			// ps.setInt(23, examRoom.getErtype());
			ps.setInt(25, examRoom.getPwdneed());
			ps.setTimestamp(26, examRoom.getPwdtime());
			ps.setString(27, examRoom.getPwd());
			ps.setInt(28, examRoom.getCacheepsize());
			ps.setInt(29, examRoom.getCacheeprefresh());
			ps.setInt(30, examRoom.getEpqsort());
			ps.setInt(31, examRoom.getAutoAssign());
			ps.setInt(32, examRoom.getCpid());
			//判断是否为章节考场，如果是章节考场的话，插入sortid
			if(examRoom.getCpid()>0){
				int sortid=this.maxExamRoomSortid(examRoom.getCourse().getId(), examRoom.getCpid());
				ps.setInt(33, sortid+1);
			}else{
				ps.setInt(33, 0);
			}
			ps.setInt(34, examRoom.getIslink());
			ps.setInt(35, examRoom.getIsxianzhikaopin());
			ps.setInt(36, examRoom.getExamsforday());
			ps.setDouble(37, examRoom.getJiangeshijian());
			ps.setInt(38, examRoom.getStuViewResult());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('exam_room') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select examroom_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				examRoom.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteExamRoom(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOM_DELETE));
			ps.setInt(1, id);
			// TODO 场次相关信息删除
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterExamRoom(ExamRoom examRoom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.EROOM_ALTER));
			ps = ct
					.prepareStatement("update exam_room set title = ?,description = ?, location = ?,  begintime = ?, endtime  = ?,passgrade=?,score= ?,erlibid=?,type=?,valid=?,isMacBand =?,isIpLimit=?,ipStart=?,ipEnd=?,examcount=?,markingManner=?,mainimg=?,isApplication=?,passmanner=?,depName=?,jingzhong=?,pwdneed=?,pwdtime=?," +
							"pwd=?,cacheepsize=?,cacheeprefresh=?,epqsort=?,autoassign=?,isxianzhikaopin=?,examsforday=?,jiangeshijian=?,stuViewResult=?  where id = ?");
			ps.setString(1, examRoom.getTitle());
			ps.setString(2, examRoom.getDescription());
			ps.setString(3, examRoom.getLocation());
			ps.setTimestamp(4, examRoom.getBegintime());
			ps.setTimestamp(5, examRoom.getEndtime());
			ps.setFloat(6, examRoom.getPassgrade());
			ps.setFloat(7, examRoom.getScore());
			ps.setInt(8, examRoom.getEroomLib().getId());
			ps.setInt(9, examRoom.getType());
			// ps.setInt(10, examRoom.getPrac().getId());
			// ps.setInt(11, examRoom.getPractimes());
			// ps.setInt(12, examRoom.getPracscore());
			ps.setInt(10, examRoom.getValid());
			ps.setInt(11, examRoom.getIsMacBand());
			ps.setInt(12, examRoom.getIsIpLimit());
			ps.setString(13, examRoom.getIpStart());
			ps.setString(14, examRoom.getIpEnd());
			ps.setInt(15, examRoom.getExamcount());
			ps.setInt(16, examRoom.getMarkingManner());
			ps.setString(17, examRoom.getMainimg());
			ps.setInt(18, examRoom.getIsApplication());
			ps.setInt(19, examRoom.getPassmanner());
			// ps.setInt(20, examRoom.getErtype());
			ps.setString(20, examRoom.getDepName());
			ps.setString(21, examRoom.getJingzhong());
			ps.setInt(22, examRoom.getPwdneed());
			ps.setTimestamp(23, examRoom.getPwdtime());
			ps.setString(24, examRoom.getPwd());
			ps.setInt(25, examRoom.getCacheepsize());
			ps.setInt(26, examRoom.getCacheeprefresh());
			ps.setInt(27, examRoom.getEpqsort());
			ps.setInt(28, examRoom.getAutoAssign());
			ps.setInt(29, examRoom.getIsxianzhikaopin());
			ps.setInt(30, examRoom.getExamsforday());
			ps.setDouble(31, examRoom.getJiangeshijian());
			ps.setInt(32, examRoom.getStuViewResult());
			ps.setInt(33, examRoom.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ExamRoom getExamRoomByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom er = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.EROOM_QUERY_BYID));
			ps = ct
					.prepareStatement("select er.id , er.title,er.begintime, er.endtime,er.location,er.courseid, c.name cname , er.description,"
							+ "er.passgrade,er.score,er.erlibid,er.type,er.valid ,erl.name,er.uvalid ,er.avalid,er.isMacBand,er.isIpLimit,er.ipStart,er.ipEnd,"
							+ "er.classid,er.isApplication,er.examcount,er.markingManner,er.mainimg,er.passmanner,er.iscommon,er.createrid,er.depName,er.jingzhong,pwdneed,pwdtime,pwd,cacheepsize,cacheeprefresh,epqsort,autoassign,er.islink" +
								" ,er.isxianzhikaopin,er.examsforday,er.jiangeshijian,er.stuViewResult  " +
									" from exam_room er left join course c on c.id= er.courseid left join eroom_lib erl on erl.id=er.erlibid where er.id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				er.setDescription(rs.getString(8));
				er.setPassgrade(rs.getFloat(9));
				er.setScore(rs.getInt(10));
				er.setEroomLib(new EroomLib(rs.getInt(11), rs.getString(14)));
				er.setType(rs.getInt(12));
				// er.setPrac(new ExamPaper(rs.getInt(13), rs.getString(14)));
				// er.setPractimes(rs.getInt(15));
				// er.setPracscore(rs.getInt(16));
				er.setValid(rs.getInt(13));
				er.setUvalid(rs.getInt(15));
				er.setAvalid(rs.getInt(16));
				er.setIsMacBand(rs.getInt(17));
				er.setIsIpLimit(rs.getInt(18));
				er.setIpStart(rs.getString(19));
				er.setIpEnd(rs.getString(20));
				er.setClassid(rs.getInt(21));
				er.setIsApplication(rs.getInt(22));
				er.setExamcount(rs.getInt(23));
				er.setMarkingManner(rs.getInt(24));
				er.setMainimg(rs.getString(25));
				er.setPassmanner(rs.getInt(26));
				er.setIscommon(rs.getInt(27));
				er.setCreater(new ELUser(rs.getInt(28)));
				er.setDepName(rs.getString(29));
				er.setJingzhong(rs.getString(30));
				er.setPwdneed(rs.getInt(31));
				er.setPwdtime(rs.getTimestamp(32));
				er.setPwd(rs.getString(33));
				er.setCacheepsize(rs.getInt(34));
				er.setCacheeprefresh(rs.getInt(35));
				er.setEpqsort(rs.getInt(36));
				er.setAutoAssign(rs.getInt(37));
				er.setIslink(rs.getInt(38));
				er.setIsxianzhikaopin(rs.getInt(39));
				er.setExamsforday(rs.getInt(40));
				er.setJiangeshijian(rs.getDouble(41));
				er.setStuViewResult(rs.getInt(42));
			}
		} catch (Exception e) {
			logger.error("获取考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}
	
	public ExamRoom getExamRoomByid_cisco(int id,int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom er = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.EROOM_QUERY_BYID));
			ps = ct
					.prepareStatement("select er.id , er.title,er.begintime, er.endtime,er.location,er.courseid, c.name cname , er.description,"
							+ "er.passgrade,er.score,er.erlibid,er.type,er.valid ,erl.name,er.uvalid ,er.avalid,er.isMacBand,er.isIpLimit,er.ipStart,er.ipEnd,"
							+ "eae.classid,er.isApplication,er.examcount,er.markingManner,er.mainimg,er.passmanner,er.iscommon,er.createrid,er.depName,er.jingzhong,pwdneed,pwdtime,pwd,cacheepsize,cacheeprefresh,epqsort," +
									"eae.firstlearnlaterexam,eae.standardline " +
									" from exam_room er left join course c on c.id= er.courseid left join eroom_lib erl on erl.id=er.erlibid left join elclass_assign_examroom eae on er.id=eae.examroomid where  er.id=? and eae.classid=? ");
			ps.setInt(1, id);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				er.setDescription(rs.getString(8));
				er.setPassgrade(rs.getFloat(9));
				er.setScore(rs.getInt(10));
				er.setEroomLib(new EroomLib(rs.getInt(11), rs.getString(14)));
				er.setType(rs.getInt(12));
				er.setValid(rs.getInt(13));
				er.setUvalid(rs.getInt(15));
				er.setAvalid(rs.getInt(16));
				er.setIsMacBand(rs.getInt(17));
				er.setIsIpLimit(rs.getInt(18));
				er.setIpStart(rs.getString(19));
				er.setIpEnd(rs.getString(20));
				er.setClassid(rs.getInt(21));
				er.setIsApplication(rs.getInt(22));
				er.setExamcount(rs.getInt(23));
				er.setMarkingManner(rs.getInt(24));
				er.setMainimg(rs.getString(25));
				er.setPassmanner(rs.getInt(26));
				er.setIscommon(rs.getInt(27));
				er.setCreater(new ELUser(rs.getInt(28)));
				er.setDepName(rs.getString(29));
				er.setJingzhong(rs.getString(30));
				er.setPwdneed(rs.getInt(31));
				er.setPwdtime(rs.getTimestamp(32));
				er.setPwd(rs.getString(33));
				er.setCacheepsize(rs.getInt(34));
				er.setCacheeprefresh(rs.getInt(35));
				er.setEpqsort(rs.getInt(36));
				er.setFirstLearnLaterExam(rs.getInt(37));
				er.setStandardLine(rs.getInt(38));
			}
		} catch (Exception e) {
			logger.error("获取考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}

	public List<ELUser> listCanAssignToRoomUsers(int roomid) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOM_QUERY_CAN_ASSIGNUSER));
			ps.setInt(1, roomid);
			// ps.setInt(2, roomid); and ra.roomid = ?
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser();
				eu.setId(rs.getInt(1));
				eu.setRealname(rs.getString(2));
				eu.setDepartment(new Department(0, rs.getString(3)));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("可分配场次用户列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public List<ELUser> listCanAssignToRoomUsers_bk(int roomid, int bkroomid)
			throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select u.id,u.realname,d.name from ELUSER u "
							+ "left join DEPARTMENT d on u.depid = d.id where u.id "
							+ "not in(select ra.userid from study_quizinfo ra where ra.roomid=? ) and u.id "
							+ "in(select ra.userid from study_quizinfo ra where ra.roomid=? and ra.ispassed !=1 ) ");
			ps.setInt(1, roomid);
			ps.setInt(2, bkroomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser();
				eu.setId(rs.getInt(1));
				eu.setRealname(rs.getString(2));
				eu.setDepartment(new Department(0, rs.getString(3)));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("可分配场次用户列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public List<ELUser> listAssignToRoomUsers(int roomid) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOM_QUERY_ASSIGNEDUSER));
			// ps.setInt(1, courseid);
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser();
				eu.setId(rs.getInt(1));
				eu.setRealname(rs.getString(2));
				eu.setDepartment(new Department(0, rs.getString(3)));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("可分配场次用户列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public List<ExamRoom> listExamRoom(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOM_QUERY_BYCID));
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6)));
				er.setPassgrade(rs.getFloat(7));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 根据课程id获取他的所有考场信息
	 * 
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoom2(int courseid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select er.id , er.title,er.begintime, er.endtime,er.location,er.courseid,er.passgrade,er.isband,er.bandclassid,er.valid,er.uvalid from exam_room er where er.courseid=? and er.cpid=0 ");
			ps.setInt(1, courseid);
//			ps.setInt(2, classid);
			rs = ps.executeQuery();
			ElClass elclass = null;
			ClassDao cd = new ClassDaoImpl();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6)));
				er.setPassgrade(rs.getFloat(7));
				er.setIsBand(rs.getInt("isband"));
				er.setBandClassid(rs.getInt("bandclassid"));
				er.setValid(rs.getInt(10));
				er.setUvalid(rs.getInt(11));
				if (er.getIsBand() == 1) {
					// 获取班级名
					if (er.getBandClassid() == 0) {
						er.setBandClassName("已绑定到当前课程");
					} else {
						elclass = cd.getClassById(er.getBandClassid());
						er.setBandClassName("已绑定培训班" + elclass.getName());
					}
				}
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 获取该培训班中所有被绑定的考场
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoomByClass(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select er.id , er.title,er.begintime, er.endtime,er.location,er.courseid,er.passgrade,er.isband,er.bandclassid,er.valid,er.uvalid from exam_room er where bandclassid=?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6)));
				er.setPassgrade(rs.getFloat(7));
				er.setIsBand(rs.getInt("isband"));
				er.setBandClassid(rs.getInt("bandclassid"));
				er.setValid(rs.getInt(10));
				er.setUvalid(rs.getInt(11));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取该培训班中所有被绑定的考场失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 我创建的考试场次
	 */
	/*
	 * public List<ExamRoom> listMyExamRoom(int userid, String title, int
	 * pageNow, int pageSize) throws ElException { PreparedStatement ps = null;
	 * ResultSet rs = null; Connection ct = null; List<ExamRoom> ers = new
	 * ArrayList<ExamRoom>(); try { ct = DBConnection.getConnection(); ps =
	 * ct.prepareStatement(ElQuerySql
	 * .getSQL(CourseConstants.EROOM_QUERY_BYUID)); ps.setInt(1, userid);
	 * ps.setString(2, "%" + title + "%"); ps.setInt(3, pageNow); ps.setInt(4,
	 * pageSize); rs = ps.executeQuery(); while (rs.next()) { ExamRoom er = new
	 * ExamRoom(rs.getInt(1), rs.getString(2));
	 * er.setBegintime(rs.getTimestamp(3)); er.setEndtime(rs.getTimestamp(4));
	 * er.setLocation(rs.getString(5)); er.setCourse(new Course(rs.getInt(6),
	 * rs.getString(7))); er.setIscommon(rs.getInt(8)); ers.add(er); } } catch
	 * (Exception e) { logger.error("获取考试场次列表失败！", e); throw new ElException(e); }
	 * finally { DBConnection.closeConnectInfo(ct, ps, rs); } return ers; }
	 */
	/**
	 * 我创建的考试场次
	 */
	public List<ExamRoom> listMyExamRoom(int userid, String title, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			if (userid == 0) { // 显示全部考场
				ps = ct
						.prepareStatement("select * from (select t.*, rownum rn from (select er.id , er.title,  er.begintime, er.endtime,er.location,er.courseid,c.name,er.iscommon,er.valid from exam_room er left join course c on c.id = er.courseid where  er.title like ? order by er.begintime desc ) t where rownum <= ? ) where rn>=? ");
				ps.setString(1, "%" + title + "%");
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			} else {// 显示我创建的考场
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(CourseConstants.EROOM_QUERY_BYUID));
				ps.setInt(1, userid);
				ps.setString(2, "%" + title + "%");
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				//
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				er.setIscommon(rs.getInt(8));
				er.setValid(rs.getInt(9));
				er.setUvalid(rs.getInt(10));
				er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public List<ExamRoom> listMyExamRoom(int userid, ExamRoom examRoom,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlstr = "";
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(CourseConstants.EROOM_QUERY_BYUID));
			 */
			sqlstr += "select * from (select t.*, rownum rn from (select er.id , er.title,  er.begintime, er.endtime,er.location,er.courseid,c.name,er.iscommon,"
					+ "er.valid,er.uvalid,erlib.id erid,erlib.name ername,el.id elid,el.realname realname,er.classid from (exam_room er left join course c on "
					+ "c.id = er.courseid) left join eroom_lib erlib on erlib.id=er.erlibid left join eluser el on el.id=er.createrid "
					+ "where  er.title like '%";
			if (examRoom != null) {
				sqlstr += examRoom.getTitle() == null ? "" + "%'" : examRoom
						.getTitle()
						+ "%'";
			}
			sqlstr += userid == 0 ? "" : " and er.createrid=" + userid;
			if (examRoom != null) {
				// if(examRoom.getTitle()!=null&&examRoom.getTitle().equals("")){
				// sqlstr+= " and er.title like '%'"+examRoom.getTitle()+"%'";
				// }
				if (examRoom.getValid() != -1) {
					sqlstr += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sqlstr += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sqlstr += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sqlstr += " and er.classid=-1";
				}
				if (examRoom.getClassid() == 0) {
					sqlstr += " and er.classid=0";
				}
				if (examRoom.getClassid() == 1) {
					sqlstr += " and er.classid>0";
				}
			}
			if (examRoom != null) {
				sqlstr += examRoom.getEroomLib() == null ? ""
						: examRoom.getEroomLib().getId() == 0 ? ""
								: "and erlib.lid>=(select lid from eroom_lib where id="
										+ examRoom.getEroomLib().getId()
										+ ") and erlib.rid<=(select rid from eroom_lib where id="
										+ examRoom.getEroomLib().getId() + ")";
			}
			sqlstr += " order by er.begintime desc ) t where rownum <= "
					+ pageNow + " ) where rn>=" + pageSize;
			ps = ct.prepareStatement(sqlstr);
			//
			// ps.setInt(1, userid);
			// ps.setString(2, "%" + title + "%");
			// ps.setInt(2, pageNow);
			// ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				//
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				er.setIscommon(rs.getInt(8));
				er.setValid(rs.getInt(9));
				er.setUvalid(rs.getInt(10));
				er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				er.setCreater(new ELUser(rs.getInt("elid"), rs
						.getString("realname")));
				er.setEroomLib(new EroomLib(rs.getInt("erid"), rs
						.getString("ername")));
				er.setClassid(rs.getInt("classid"));
				er.setUsersize(this.getExamAllStudy(er.getId()));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 获取考场所有学员数
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getExamAllStudy(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_room where roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考场所有学员数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取考场的数量
	 * 
	 * @param userid
	 * @param examRoom
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listMyExamRoomCount(int userid, ExamRoom examRoom)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlstr = "";
		try {
			ct = DBConnection.getConnection();
			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(CourseConstants.EROOM_QUERY_BYUID));
			 */
			sqlstr += "select count(*) from (exam_room er left join course c on c.id = er.courseid) left join eroom_lib erlib on erlib.id=er.erlibid "
					+ "where  er.title like '%";
			if (examRoom != null) {
				sqlstr += examRoom.getTitle() == null ? "" + "%'" : examRoom
						.getTitle()
						+ "%'";
			}
			sqlstr += userid == 0 ? "" : " and er.createrid=" + userid;
			if (examRoom != null) {
				// if(examRoom.getTitle()!=null&&examRoom.getTitle().equals("")){
				// sqlstr+= " and er.title like '%'"+examRoom.getTitle()+"%'";
				// }
				if (examRoom.getValid() != -1) {
					sqlstr += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sqlstr += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sqlstr += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sqlstr += " and er.classid=-1";
				}
				if (examRoom.getClassid() == 0) {
					sqlstr += " and er.classid=0";
				}
				if (examRoom.getClassid() == 1) {
					sqlstr += " and er.classid>0";
				}
			}
			if (examRoom != null) {
				sqlstr += examRoom.getEroomLib() == null ? ""
						: examRoom.getEroomLib().getId() == 0 ? ""
								: "and erlib.lid>=(select lid from eroom_lib where id="
										+ examRoom.getEroomLib().getId()
										+ ") and erlib.rid<=(select rid from eroom_lib where id="
										+ examRoom.getEroomLib().getId() + ")";
			}
			// sqlstr+=" order by er.begintime desc ) t where rownum <=
			// "+pageNow+" ) where rn>="+pageSize;
			ps = ct.prepareStatement(sqlstr);
			//
			// ps.setInt(1, userid);
			// ps.setString(2, "%" + title + "%");
			// ps.setInt(2, pageNow);
			// ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			if (rs.next()) {
				//
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<String> getSupervisorrealname(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<String> supervisorrs = new ArrayList<String>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select realname from eluser where id in(select userid from exam_rappraises where roomid=?)");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				supervisorrs.add(rs.getString(1));
			}
		} catch (Exception e) {
			logger.error("获取监考员错误", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return supervisorrs;
	}

	public int listMyExamRoomSize(int userid, String title) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (userid == 0) { // 全部考场
				ps = ct
						.prepareStatement("select count(*) from exam_room er left join course c on c.id = er.courseid where  er.title like ? ");
				ps.setInt(1, userid);
				ps.setString(2, "%" + title + "%");
			} else { // 我创建的考场
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(CourseConstants.EROOM_QUERY_BYUID_SIZE));
				ps.setInt(1, userid);
				ps.setString(2, "%" + title + "%");
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ExamRoom> listMyDepExamRoom(int depid, String title,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOM_QUERY_BYDEPID));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setString(3, "%" + title + "%");
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				er.setBegintime(rs.getTimestamp(5));
				er.setEndtime(rs.getTimestamp(6));
				er.setLocation(rs.getString(7));
				er.setCourse(new Course(rs.getInt(8), rs.getString(9)));
				er.setEroomLib(new EroomLib(rs.getInt(10), rs.getString(11)));
				er.setIscommon(rs.getInt(12));
				er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int listMyDepExamRoomSize(int depid, String title)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOM_QUERY_BYDEPID_SIZE));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setString(3, "%" + title + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Examprac> listexamprac(int useid, int begin, int end)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Examprac> xx = new ArrayList<Examprac>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.EXAMPRAC_LIST));
			ps = ct
					.prepareStatement("select * from (select t.* ,rownum rn from (select epr.id,epr.title,epr.begintime,epr.endtime,count(epra.userid) xx,ep.id epid,ep.title eptitle,epr.valid from examprac epr left join exampaper ep on epr.epid=ep.id left join examprac_assign epra on epra.eprid= epr.id where epr.userid =? group by  epr.id,epr.title,epr.begintime,epr.endtime,ep.id,ep.title,epr.valid order by id)t where rownum <=?)  where rn>=?");
			ps.setInt(1, useid);
			ps.setInt(2, begin);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				Examprac epr = new Examprac();
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				epr.setUsersize(rs.getInt(5));
				epr.setExamPaper(new ExamPaper(rs.getInt("epid"), rs
						.getString("eptitle")));
				epr.setValid(rs.getInt(8));
				xx.add(epr);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return xx;
	}

	public List<Examprac> listexampracvalid(int begin, int end)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Examprac> xx = new ArrayList<Examprac>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			// .prepareStatement("select * from (select t.* ,rownum rn from
					// (select
					// epr.id,epr.title,epr.begintime,epr.endtime,epr.valid,count(epra.userid)
					// xx,el.username,dep.name from examprac epr left join
					// examprac_assign epra on epra.eprid= epr.id left join
					// eluser el on
					// epr.userid = el.id left join department dep on
					// el.depid=dep.id
					// group by
					// epr.id,epr.title,epr.begintime,epr.endtime,epr.valid,el.username,dep.name
					// order by id)t where rownum <=?) where rn>=?");
					.prepareStatement("select * from (select t.* ,rownum rn from (select epr.id,epr.title,epr.begintime,epr.endtime,epr.valid,count(epra.userid) xx,el.username,dep.name,ep.id epid,ep.title eptitle from examprac epr left join exampaper ep on epr.epid=ep.id left join examprac_assign epra on epra.eprid= epr.id left join eluser el on epr.userid = el.id left join department dep on el.depid=dep.id group by  epr.id,epr.title,epr.begintime,epr.endtime,epr.valid,el.username,dep.name,ep.id,ep.title order by id)t where rownum <=?)  where rn>=?");
			ps.setInt(1, begin);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				Examprac epr = new Examprac();
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				epr.setValid(rs.getInt(5));
				epr.setUsersize(rs.getInt(6));
				epr.setExamPaper(new ExamPaper(rs.getInt("epid"), rs
						.getString("eptitle")));
				ELUser user = new ELUser();
				user.setUsername(rs.getString(7));
				user.setDanwei(rs.getString(8));// 借用， 此处为部门名称
				epr.setUser(user);

				xx.add(epr);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return xx;
	}

	public int listexampracvalidsize() throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			// String sql="select count(id) from examprac epr where epr.valid
			// =0";
			String sql = "select count(id) from examprac epr";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public void exampracSh(int roomid, int valid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update examprac set valid= ? where id = ?");
			ps.setInt(1, valid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public int listexampracsize(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(id) from examprac where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return 0;
	}

	/**
	 * 添加练习
	 */
	public void addexamprac(Examprac examprac) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("insert into
			// examprac(title,description,begintime,endtime,epid,userid)
			// values(?,?,?,?,?,?)");
			// ps = ct.prepareStatement("insert into
			// examprac(title,description,begintime,endtime,epid,userid,praccount)
			// values(?,?,?,?,?,?,?)");
			ps = ct
					.prepareStatement("insert into examprac(title,description,begintime,endtime,epid,userid,praccount,passgrade) values(?,?,?,?,?,?,?,?)");
			ps.setString(1, examprac.getTitle());
			ps.setString(2, examprac.getDescription());
			ps.setTimestamp(3, examprac.getBegintime());
			ps.setTimestamp(4, examprac.getEndtime());
			ps.setInt(5, examprac.getExamPaper().getId());
			ps.setInt(6, examprac.getUser().getId());
			ps.setInt(7, examprac.getPracCount());
			ps.setInt(8, examprac.getPassgrade());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterexamprac(Examprac examprac) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update examprac set title=?,description=?,begintime=?,endtime=?,epid=?,praccount=?,passgrade=? where id = ? ");
			ps.setString(1, examprac.getTitle());
			ps.setString(2, examprac.getDescription());
			ps.setTimestamp(3, examprac.getBegintime());
			ps.setTimestamp(4, examprac.getEndtime());
			ps.setInt(5, examprac.getExamPaper().getId());
			ps.setInt(6, examprac.getPracCount());
			ps.setInt(7, examprac.getPassgrade());
			ps.setInt(8, examprac.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void deleteexamprac(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from examprac where id = ? ");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	/**
	 * 根据id获取练习信息
	 */
	public Examprac getexamprac(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Examprac epr = new Examprac();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epr.id,epr.title,epr.begintime,epr.endtime,epr.epid,ep.title,epr.valid,epr.praccount,epr.passgrade from examprac epr left join exampaper ep on ep.id = epr.epid where epr.id = ? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				epr.setId(rs.getInt(1));
				epr.setTitle(rs.getString(2));
				epr.setBegintime(rs.getTimestamp(3));
				epr.setEndtime(rs.getTimestamp(4));
				epr.setExamPaper(new ExamPaper(rs.getInt(5), rs.getString(6)));
				epr.setValid(rs.getInt(7));
				epr.setPracCount(rs.getInt("praccount"));
				epr.setPassgrade(rs.getInt("passgrade"));
			}
		} catch (Exception e) {
			logger.error("根据id获取练习信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epr;
	}

	public List<ELUser> listassignedepracusers(int eprid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> eus = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epra.userid,eu.realname,eu.username,dep.id,dep.name from examprac_assign epra left join "
							+ "eluser eu on eu.id = epra.userid left join department dep on dep.id = eu.depid where epra.eprid = ? ");
			ps.setInt(1, eprid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	/**
	 * 练习人员查看
	 * 
	 * @param eprid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listassignedepracusers(int eprid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> eus = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from(select t.*,rownum rn from ( select epra.userid,eu.realname,eu.username,dep.id,dep.name from examprac_assign epra left join "
							+ "eluser eu on eu.id = epra.userid left join department dep on dep.id = eu.depid where epra.eprid = ? )t where rownum<=? ) where rn>=? ");
			ps.setInt(1, eprid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("练习人员查看失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	/**
	 * 练习人员数量查看
	 * 
	 * @param eprid
	 * @return
	 * @throws ElException
	 */
	public int listassignedepracusersSize(int eprid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from examprac_assign epra left join "
							+ "eluser eu on eu.id = epra.userid left join department dep on dep.id = eu.depid where epra.eprid = ? ");
			ps.setInt(1, eprid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("练习人员数量查看！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> listcanassignepracusers(int eprid) throws ElException {
		return null;
	}

	public boolean checkepracuser(int eprid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from examprac_assign where eprid = ? and userid = ? ");
			ps.setInt(1, eprid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void addepracuser(int eprid, int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into examprac_assign( eprid ,userid)values(?,?)");
			ps.setInt(1, eprid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteepracuser(int eprid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from examprac_assign where  eprid=? and userid =? ");

			ps.setInt(1, eprid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addEroomusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into exam_" + type
					+ " (userid,roomid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 添加考场阅卷组长
	 */
	public void addEroomusers(String type, int userid, int roomid, int isHeader)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			// ct.setAutoCommit(false);

			/*
			 * ps = ct.prepareStatement("insert into exam_" + type + "
			 * (userid,roomid) values('"+userid+"','"+depid+"')");
			 */
			String insertSql = "insert into exam_" + type
					+ " (userid,roomid,isLeader) values(?,?,?)";
			// String updateSql="update exam_" + type+" set isLeader=1 where
			// userId='"+isHeader+"' and roomId='"+depid+"'";
			ps = ct.prepareStatement(insertSql);
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.setInt(3, isHeader);

			ps.executeUpdate();

			// ct.setAutoCommit(true);

		} catch (Exception e) {
			logger.error("添加考场阅卷组长出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 修改考场阅卷组长
	 */
	public void UpdateEroomusers(String type, int userid, int roomid,
			int isLeader) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String updateSql = "update exam_" + type
					+ " set isLeader=? where  userId=? and roomId=?";
			ps = ct.prepareStatement(updateSql);
			ps.setInt(1, isLeader);
			ps.setInt(2, userid);
			ps.setInt(3, roomid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改考场阅卷组长出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkEroomUsers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from exam_" + type
					+ "  where userid = ? and roomid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean checkEroomIsUsers(String type, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from exam_" + type
					+ "  where roomid = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteEroomusers(String type, int userid, int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from exam_" + type
					+ "  where userid = ? and roomid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> getEroomUsers(String type, int roomid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from exam_"
							+ type
							+ "  du left join eluser eu on eu.id = du.userid where du.roomid = ?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return us;
	}

	public List<ELUser> getEroomUsers_ZuZhang(String type, int roomid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username,du.isLeader from exam_"
							+ type
							+ "  du left join eluser eu on eu.id = du.userid where du.roomid = ?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname())) {
					user.setRealname(rs.getString(3));
				}
				user.setIsLeader(rs.getInt(4));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return us;
	}

	public void addEroomeps(int erid, int epid, int pracid, int practime,
			float pracscore, float passgrade, int stuview) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into exam_reps(roomid,epid,pracid,practimes,pracscore, passgrade,stuview) values(?,?,?,?,?,?,?)");
			ps.setInt(1, erid);
			ps.setInt(2, epid);
			ps.setInt(3, pracid);
			ps.setInt(4, practime);
			ps.setFloat(5, pracscore);
			ps.setFloat(6, passgrade);
			ps.setInt(7, stuview);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 添加考场中的试卷
	 * 
	 * @param erid
	 * @param epid
	 * @param pracid
	 * @param practime
	 * @param pracscore
	 * @param passgrade
	 * @param stuview
	 * @param quizlook
	 * @param scorelook
	 * @throws ElException
	 */
	public void addEroomeps(int erid, int epid, int pracid, int practime,
			float pracscore, float passgrade, int stuview, int quizlook,
			int scorelook, int quizcount, int passmanner, int sortid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into exam_reps(roomid,epid,pracid,practimes,pracscore, passgrade,stuview,quizlook,scorelook,quizcount,passmanner,sortid) values(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, erid);
			ps.setInt(2, epid);
			ps.setInt(3, pracid);
			ps.setInt(4, practime);
			ps.setFloat(5, pracscore);
			ps.setFloat(6, passgrade);
			ps.setInt(7, stuview);
			ps.setInt(8, quizlook);
			ps.setInt(9, scorelook);
			ps.setInt(10, quizcount);
			ps.setInt(11, passmanner);
			ps.setInt(12, sortid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加考场中的试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ExamPaper getEroomeps(int roomid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select pracscore,practimes from exam_reps where epid = ? and roomid = ?");
			ps.setInt(1, epid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep.setPracscore(rs.getInt(1));
				ep.setPractimes(rs.getInt(2));
			}
		} catch (Exception e) {
			logger.error("查看试卷信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	public void alterEroomeps(int roomid, int epid, int pracid, int practime,
			float pracscore, float passgrade, int stuview) throws ElException {

		// 先查出该场次的原id，然后进行修改
		// 先获取原id
		// ExamPaperDao epp = new ExamPaperDaoImpl();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_reps set pracid=?,practimes=?,pracscore=?, passgrade=?,stuview=? where roomid =? and epid =?");
			ps.setInt(1, pracid);
			ps.setInt(2, practime);
			ps.setFloat(3, pracscore);
			ps.setFloat(4, passgrade);
			ps.setInt(5, stuview);
			ps.setInt(6, roomid);
			ps.setInt(7, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新考场中的试卷
	 * 
	 * @param roomid
	 * @param epid
	 * @param pracid
	 * @param practime
	 * @param pracscore
	 * @param passgrade
	 * @param stuview
	 * @param quizlook
	 * @param scorelook
	 * @throws ElException
	 */
	public void alterEroomeps(int roomid, int epid, int pracid, int practime,
			float pracscore, float passgrade, int stuview, int quizlook,
			int scorelook, int quizcount, int passmanner) throws ElException {
		// ExamPaperDao epp=new ExamPaperDaoImpl();
		// ExamPaper examPaper=epp.getExamPaperById(epid, roomid);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_reps set pracid=?,practimes=?,pracscore=?, passgrade=?,stuview=?,quizlook=?,scorelook=?,quizcount=?,passmanner=? where roomid =? and epid =?");
			ps.setInt(1, pracid);
			ps.setInt(2, practime);
			ps.setFloat(3, pracscore);
			ps.setFloat(4, passgrade);
			ps.setInt(5, stuview);
			ps.setInt(6, quizlook);
			ps.setInt(7, scorelook);
			ps.setInt(8, quizcount);
			ps.setInt(9, passmanner);
			ps.setInt(10, roomid);
			ps.setInt(11, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新考场中的试卷出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterEroomeps(int roomid, int epid, int pracid, int practime,
			float pracscore, float passgrade, int stuview, int pid)
			throws ElException {

		// 先查出该场次的原id，然后进行修改
		// 先获取原id
		// ExamPaperDao epp = new ExamPaperDaoImpl();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("update exam_reps set
			// pracid=?,practimes=?,pracscore=?, passgrade=?,stuview=? where
			// roomid =? and epid =?");
			// ps.setInt(1, pracid);
			// ps.setInt(2, practime);
			// ps.setFloat(3, pracscore);
			// ps.setFloat(4, passgrade);
			// ps.setInt(5, stuview);
			// ps.setInt(6, roomid);
			// ps.setInt(7, epid);
			String sql = "update exam_reps set epid=?,pracid=?,practimes=?,pracscore=?, passgrade=?,stuview=? where roomid =? and epid =?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, epid);
			ps.setInt(2, pracid);
			ps.setInt(3, practime);
			ps.setFloat(4, pracscore);
			ps.setFloat(5, passgrade);
			ps.setInt(6, stuview);
			ps.setInt(7, roomid);
			ps.setInt(8, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkEroomeps(int roomid, int epid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from exam_reps where roomid = ? and epid = ?");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 删除考场试卷（假）
	 */
	// public void deleteEroomeps(int erid, int epid) throws ElException {
	//
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// // ps = ct
	// // .prepareStatement("delete from exam_reps where roomid = ? and
	// // epid =?");
	// ps = ct
	// .prepareStatement("update exam_reps set status=1 where roomid = ? and
	// epid =?");
	// ps.setInt(1, erid);
	// ps.setInt(2, epid);
	// ps.executeUpdate();
	// } catch (Exception e) {
	// logger.error("删除考场试卷（假）出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }
	/**
	 * 更新考场试卷的状态
	 * 
	 * @param erid
	 * @param epid
	 * @param status
	 * @throws ElException
	 */
	public void udpateEroomepStatus(int erid, int epid, int status)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("delete from exam_reps where roomid = ? and
			// epid =?");
			ps = ct
					.prepareStatement("update exam_reps set status=? where roomid = ? and epid =?");
			ps.setInt(1, status);
			ps.setInt(2, erid);
			ps.setInt(3, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新考场试卷的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 恢复学员试卷删除状态
	 * 
	 * @param userid
	 * @param roomid
	 * @throws ElException
	 */
	public void udpateStudyepStatus(int userid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_exampaper set isdel=0 where userid = ? and roomid =?");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("恢复学员试卷删除状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新学员试卷状态
	 * 
	 * @param erid
	 * @param epid
	 * @param delStatus
	 * @throws ElException
	 */
	public void udpateStudyepStatus(int erid, int epid, int delStatus)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_exampaper set isdel=? where roomid = ? and epid =?");
			ps.setInt(1, delStatus);
			ps.setInt(2, erid);
			ps.setInt(3, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新学员试卷状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新学员考场状态
	 * 
	 * @param erid
	 * @param epid
	 * @param delStatus
	 * @throws ElException
	 */
	public void udpateStudyRoomStatus(int roomid, int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_room set status=? where roomid = ? and userid =?");
			ps.setInt(1, status);
			ps.setInt(2, roomid);
			ps.setInt(3, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新学员考场状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 删除试卷的练习
	 * 
	 * @param erid
	 * @param epid
	 * @throws ElException
	 */
	public void deleteEroomepsLx(int erid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_reps set pracid=0 where roomid = ? and epid =?");
			ps.setInt(1, erid);
			ps.setInt(2, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ExamPaper> getEroomeps(int erid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> us = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ep.id epid,ep.title eptitle,pep.id praid,pep.title,erp.pracscore,erp.practimes,erp.passgrade,erp.stuview,ep.ep_tscore,pep.ep_tscore,erp.quizlook,erp.scorelook,erp.status,erp.quizcount,erp.passmanner,erp.sortid "
							+ " from exam_reps erp left join exampaper ep on ep.id = erp.epid "
							+ "left join exampaper pep on pep.id = erp.pracid where erp.roomid= ? order by erp.sortid");
			ps.setInt(1, erid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper user = new ExamPaper(rs.getInt(1), rs.getString(2));
				ExamPaper ep = new ExamPaper(rs.getInt(3), rs.getString(4));
				ep.setEp_tscore(rs.getInt(10));
				user.setPrac(ep);
				user.setPracscore(rs.getInt(5));
				user.setPractimes(rs.getInt(6));
				user.setPassgrade(rs.getInt(7));
				user.setStuview(rs.getInt(8));
				user.setEp_tscore(rs.getInt(9));
				user.setQuizlook(rs.getInt("quizlook"));
				user.setScorelook(rs.getInt("scorelook"));
				user.setStatus(rs.getInt("status"));
				user.setQuizcount(rs.getInt(14));
				user.setPassmanner(rs.getInt(15));
				user.setSortid(rs.getInt(16));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看试卷信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return us;
	}

	public List<MyRoom> listEroomtesters(int roomid, int pageNow, int pageSize)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> us = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from(select t.*,rownum rn from (select eu.id euid,eu.realname,eu.username,dep.id depid,dep.name,count(sqi.epid),eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,sr.status "
							+ "from study_room sr left join eluser eu on sr.userid = eu.id left join department dep on dep.id = eu.depid "
							+ "left join study_exampaper sqi on  eu.id = sqi.userid where sqi.roomid= sr.roomid and sr.roomid =? group by eu.id ,eu.realname,eu.username,dep.id  ,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,sr.status)t where rownum<=? ) where rn>=?");
			ps.setInt(1, roomid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom myRoom = new MyRoom();
				myRoom.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				myRoom.getTester().setUsername(rs.getString(3));
				myRoom.getTester().setDepartment(
						new Department(rs.getInt(4), rs.getString(5)));
				myRoom.setEpsize(rs.getInt(6));
				myRoom.getTester().setShenfenzheng(rs.getString(7));
				myRoom.getTester().setSex(rs.getString(8));
				myRoom.getTester().setJingzhong(rs.getInt(9));
				myRoom.getTester().setShengri(rs.getDate(10));
				myRoom.getTester().setAge(rs.getInt(11));
				myRoom.setStatus(rs.getInt(12));
				us.add(myRoom);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	/**
	 * 获取可申请考场所有未审核的人员
	 * 
	 * @param roomid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listEroomNoAuditUser(ELUser eu, ExamRoom room,
			Department dep, boolean consub, int status, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> us = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from(select t.*,rownum rn from
			// (select eu.id euid,eu.realname,eu.username,dep.id
			// depid,dep.name,count(sqi.epid),eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl(
			// floor(to_char(sysdate,'yyyy'
			// ))-floor(to_char(shengri,'yyyy')),-1) age_,sr.status "
			// + "from study_room_ sr left join eluser eu on sr.userid = eu.id
			// left join department dep on dep.id = eu.depid "
			// + "left join study_exampaper sqi on eu.id = sqi.userid where
			// sr.status=-1 and sqi.roomid= sr.roomid and sr.roomid =? group by
			// eu.id ,eu.realname,eu.username,dep.id
			// ,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,sr.status)t
			// where rownum<=? ) where rn>=?");
			// ps = ct.prepareStatement("select * from(select t.*,rownum rn from
			// (select eu.id euid,eu.realname,eu.username,dep.id
			// depid,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl(
			// floor(to_char(sysdate,'yyyy'
			// ))-floor(to_char(shengri,'yyyy')),-1) age_,sr.status "
			// + "from study_room_apply sr left join eluser eu on sr.userid =
			// eu.id left join department dep on dep.id = eu.depid "
			// + " where sr.status in(1,2,3) and sr.roomid =? order by
			// sr.status,sr.createtime)t where rownum<=? ) where rn>=?");
			String sql = "select * from(select t.*,rownum rn from (select eu.id euid,eu.realname,eu.username,dep.id depid,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,sr.status "
					+ "from study_room_apply sr left join eluser eu on sr.userid = eu.id inner join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", dep, consub)
					+ ") dep on dep.id = eu.depid " + " where ";
			if (status != -1)
				sql += "sr.status=" + status + " and ";
			if (eu != null && null != eu.getRealname())
				sql += "eu.realname like '%" + eu.getRealname().trim()
						+ "%' and ";
			if (eu != null && null != eu.getUsername())
				sql += "eu.username like '%" + eu.getUsername().trim()
						+ "%' and ";

			sql += " sr.roomid =? order by sr.status,sr.createtime,eu.id)t where rownum<=? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, room.getId());
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom myRoom = new MyRoom();
				myRoom.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				myRoom.getTester().setUsername(rs.getString(3));
				myRoom.getTester().setDepartment(
						new Department(rs.getInt(4), rs.getString(5)));
				// myRoom.setEpsize(rs.getInt(6));
				myRoom.getTester().setShenfenzheng(rs.getString(6));
				myRoom.getTester().setSex(rs.getString(7));
				myRoom.getTester().setJingzhong(rs.getInt(8));
				myRoom.getTester().setShengri(rs.getDate(9));
				myRoom.getTester().setAge(rs.getInt(10));
				myRoom.setStatus(rs.getInt(11));
				us.add(myRoom);
			}
		} catch (Exception e) {
			logger.error("获取可申请考场所有未审核的人员出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	public int listEroomtesterssize(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(*) from (select count(sr.userid) from study_room sr left join eluser eu on sr.userid = eu.id left join department dep on dep.id = eu.depid "
							+ "left join study_exampaper sqi on  eu.id = sqi.userid where sqi.roomid= sr.roomid and sr.roomid =? group by  eu.id)");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	/**
	 * 获取可申请考场所有未审核的人员数量
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int listEroomNoAuditUserSize(ELUser eu, ExamRoom room,
			Department dep, boolean consub, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(" select count(userid) from
			// study_room_apply sr where sr.roomid=? and sr.status in(1,2,3)");
			String sql = " select count( *) from (select eu.id euid,eu.realname,eu.username,dep.id depid,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,sr.status "
					+ "from study_room_apply sr left join eluser eu on sr.userid = eu.id inner join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", dep, consub)
					+ ") dep on dep.id = eu.depid " + " where ";
			if (status != -1)
				sql += "sr.status=" + status + " and ";
			if (eu != null && null != eu.getRealname())
				sql += "eu.realname like '%" + eu.getRealname().trim()
						+ "%' and ";
			if (eu != null && null != eu.getUsername())
				sql += "eu.username like '%" + eu.getUsername().trim()
						+ "%' and ";

			sql += " sr.roomid =? order by sr.status,sr.createtime,eu.id)t ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, room.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取可申请考场所有未审核的人员数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	/**
	 * 根据用户id和课程id获取所对应的classid
	 * 
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getStudyCourseInClass(int userid, int courseid)
			throws ElException {
		List<ElClass> classList = new ArrayList<ElClass>();
		ElClass elClass = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sc.classid,ec.name from study_course sc inner join elclass ec on sc.classid=ec.id where userid = ? and courseid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				elClass = new ElClass();
				elClass.setId(rs.getInt(1));
				if (elClass.getId() == 0) {
					elClass.setName("单独分配而来");
				} else {
					elClass.setName(rs.getString(2));
				}
				classList.add(elClass);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classList;
	}

	/**
	 * 根据课程id获取所对应的classid
	 * 
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getStudyCourseInClass(int courseid) throws ElException {
		List<ElClass> classList = new ArrayList<ElClass>();
		ElClass elClass = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select distinct sc.classid,ec.name from study_course sc left join elclass ec on sc.classid=ec.id where courseid = ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				elClass = new ElClass();
				elClass.setId(rs.getInt(1));
				if (elClass.getId() == 0) {
					elClass.setName("单独分配而来");
				} else {
					elClass.setName(rs.getString(2));
				}
				classList.add(elClass);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classList;
	}

	public List<ExamRoom> getExamRoomByCourseid(int courseid)
			throws ElException {
		List<ExamRoom> eroomList = new ArrayList<ExamRoom>();
		ExamRoom examRoom = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,title from exam_room where courseid=? and iscommon=0 and classid is not null");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				examRoom = new ExamRoom();
				examRoom.setId(rs.getInt("id"));
				examRoom.setTitle(rs.getString("title"));
				eroomList.add(examRoom);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eroomList;
	}

	/**
	 * 学员的某一班级（课程考试）分配到考场
	 * 
	 * @param roomid
	 * @param userid
	 * @param valid
	 * @param classid
	 * @throws ElException
	 */
	public void adduser2eroom(int roomid, int userid, int valid, int classid,
			int joinway) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_room(roomid,userid,status,classid,joinway) values(?,?,?,?,?)");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, 0);
			ps.setInt(4, classid);
			ps.setInt(5, joinway);
			ps.executeUpdate();
			// 更新学员报名状态
			((StudyQuizDao) SpringContextUtil.getBean("studyQuizDao"))
					.udpateStudyRoomApplyStatus(roomid, userid, 3);
		} catch (Exception e) {
			logger.error("学员的某一班级（课程考试）分配到考场出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 学员的某一班级（课程考试）分配到考场
	 * 
	 * @param roomid
	 * @param userid
	 * @param valid
	 * @param classid
	 * @throws ElException
	 */
	public void addusereroom(int roomid, int userid, int valid, int classid,
			int joinway) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_room(roomid,userid,status,classid,joinway) values(?,?,?,?,?)");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, valid);
			ps.setInt(4, classid);
			ps.setInt(5, joinway);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("学员的某一班级（课程考试）分配到考场出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void adduser2eroom(int roomid, int userid, int valid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_room(roomid,userid,status) values(?,?,?)");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, 0);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void adduser2eroom_cisco(int classid,int roomid, int userid, int valid,
			int joinway) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into study_room(roomid,userid,status,joinway,classid) values(?,?,?,?,?)");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, 0);
			ps.setInt(4, joinway);
			ps.setInt(5, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	/**
	 * 分配用户到考场
	 */
	public void adduser2eroom(int roomid, int userid) throws ElException {
		adduser2eroom(roomid, userid, 1);
	}
	
	public void adduser2eroom_cisco(int classid,int roomid, int userid,int joinway) throws ElException {
		adduser2eroom_cisco(classid,roomid, userid, 1,joinway);
	}

	/**
	 * 检查用户是否已经分配到该考场
	 */
	public boolean checkuser2eroom(int roomid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_room where roomid = ? and userid = ?");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;

	}

	/**
	 * 检查用户是否已经分配到该考场
	 */
	public boolean checkuser2eroom(int roomid, int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_room where roomid = ? and userid = ? ");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	/**
	 * 检查用户是否通过该培训班分配到该考场
	 */
	public boolean checkusereroom(int roomid, int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_room where roomid = ? and userid = ? and classid=? ");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteuser2eroom(int roomid, int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from study_room where roomid = ? and userid = ?");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// public List<ExamPaper> getEroomepwithusizes(int roomid) throws
	// ElException {
	//
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<ExamPaper> us = new ArrayList<ExamPaper>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select ep.id,ep.title ,count(sqi.id) usize from
	// exam_reps erp left join exampaper ep on ep.id = erp.epid left join
	// study_quizinfo sqi on sqi.roomid=erp.roomid and sqi.epid = ep.id where
	// erp.roomid=? group by ep.id,ep.title");
	// ps.setInt(1, roomid);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ExamPaper user = new ExamPaper(rs.getInt(1), rs.getString(2));
	// user.setEp_kscore(rs.getInt(3));
	// us.add(user);
	// }
	// } catch (Exception e) {
	// logger.error("查看部门信息出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return us;
	// }
	// 2012-6-5 黄东林改写
	// 因为当前加了1张学员分配试卷的表，所以连接的不在是study_quizInfo,而是study_exampaper
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sopia.courseman.dao.EroomDao#getEroomepwithusizes(int)
	 */
	public List<ExamPaper> getEroomepwithusizes(int roomid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> us = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ep.id,ep.title ,count(sep.epid) usize,erp.status,erp.passgrade,ep.ep_tscore from exam_reps erp left join exampaper ep on ep.id = erp.epid  left join study_exampaper sep on sep.roomid=erp.roomid and sep.epid = ep.id where erp.roomid=? group by ep.id,ep.title,erp.status,erp.passgrade,ep.ep_tscore");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper user = new ExamPaper(rs.getInt(1), rs.getString(2));
				user.setUsersize(rs.getInt(3));
				user.setStatus(rs.getInt(4));
				user.setPassgrade(rs.getFloat(5));
				user.setEp_tscore(rs.getFloat(6));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	/**
	 * 获取考场中的所有试卷（不包括已删除的）
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<ExamPaper> getEroomEps(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> us = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ep.id,ep.title ,count(sep.epid) usize,erp.status from exam_reps erp left join exampaper ep on ep.id = erp.epid  left join study_exampaper sep on sep.roomid=erp.roomid and sep.epid = ep.id where erp.roomid=? and erp.status=0 group by ep.id,ep.title,erp.status");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamPaper user = new ExamPaper(rs.getInt(1), rs.getString(2));
				user.setUsersize(rs.getInt(3));
				user.setStatus(rs.getInt(4));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("获取考场中的所有试卷（不包括已删除的）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	public List<ELUser> listroom2userbyurid(int epid, int roomid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> eus = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select sqi.userid,eu.realname,eu.username,dep.id,dep.name,sqi.practimes,sqi.pracscore from study_quizinfo sqi left join "
							+ "eluser eu on eu.id = sqi.userid left join department dep on dep.id = eu.depid where sqi.epid = ? and sqi.roomid= ? ");
			ps.setInt(1, epid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				eu.setPractimes(rs.getInt(6));
				eu.setPracscore(rs.getInt(7));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	// public List<ELUser> listroom2userbyurid(int epid, int roomid ,int
	// pageNow,int pageSize)
	// throws ElException {
	//
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<ELUser> eus = new ArrayList<ELUser>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select * from (select t.* ,rownum rn from (select
	// sqi.userid,eu.realname,eu.username,dep.id,dep.name,sqi.practimes,sqi.pracscore
	// from study_quizinfo sqi left join "
	// + "eluser eu on eu.id = sqi.userid left join department dep on dep.id =
	// eu.depid where sqi.epid = ? and sqi.roomid= ? )t where rownum<= ?) where
	// rn>=? ");
	// ps.setInt(1, epid);
	// ps.setInt(2, roomid);
	// ps.setInt(3, pageNow);
	// ps.setInt(4, pageSize);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
	// eu.setUsername(rs.getString(3));
	// eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
	// eu.setPractimes(rs.getInt(6));
	// eu.setPracscore(rs.getInt(7));
	// eus.add(eu);
	// }
	// } catch (Exception e) {
	// logger.error("获取考试场次列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return eus;
	// }

	/**
	 * 查询考场试卷的人员
	 */
	// (把连接表study_quizInfo改成了study_exampaer,然后注掉2个信息显示)
	public List<ELUser> listroom2userbyurid(int epid, int roomid, int pageNow,
			int pageSize) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> eus = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from (select t.* ,rownum rn
			// from (select
			// sqi.userid,eu.realname,eu.username,dep.id,dep.name,sqi.practimes,sqi.pracscore
			// from study_quizinfo sqi left join "
			// + "eluser eu on eu.id = sqi.userid left join department dep on
			// dep.id = eu.depid where sqi.epid = ? and sqi.roomid= ? )t where
			// rownum<= ?) where rn>=? ");
			ps = ct
					.prepareStatement("select * from (select t.* ,rownum rn from (select sqi.userid,eu.realname,eu.username,dep.id,dep.name,sr.joinway from study_exampaper sqi left join "
							+ "eluser eu on eu.id = sqi.userid left join department dep on dep.id = eu.depid left join study_room sr on sr.roomid=sqi.roomid where sqi.epid = ? and sqi.roomid= ? and sr.userid=sqi.userid )t where rownum<= ?) where rn>=?  ");
			ps.setInt(1, epid);
			ps.setInt(2, roomid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				// eu.setPractimes(rs.getInt(6));
				// eu.setPracscore(rs.getInt(7));
				eu.setJoinwayInt(rs.getInt(6));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	// public int listroom2userbyuridSize(int epid, int roomid)throws
	// ElException {// hwc
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// int x = 0;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select count(*) from study_quizinfo sqi left join "
	// + "eluser eu on eu.id = sqi.userid left join department dep on dep.id =
	// eu.depid where sqi.epid = ? and sqi.roomid= ? ");
	// ps.setInt(1, epid);
	// ps.setInt(2, roomid);
	// rs = ps.executeQuery();
	// if(rs.next()) {
	// x=rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("获取考试场次列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return x;
	// }

	public int listroom2userbyuridSize(int epid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_exampaper sqi left join "
							+ "eluser eu on eu.id = sqi.userid left join department dep on dep.id = eu.depid where sqi.epid = ? and sqi.roomid= ? ");
			ps.setInt(1, epid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	public List<ExamRoom> listExamRoomValid(int userid, int role, String sqlW,
			int pageNow, int pageSize) throws ElException {// hwc

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from (select er.id , er.title,
			// er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name
			// ,row_number() over( order by er.begintime desc)rownum from
			// exam_room er left join eroom_lib erlib on erlib.id=er.erlibid
			// where er.iscommon=1 and erlib.lid>=? and erlib.rid<=?)t where
			// t.rownum between ? and ?");
			if (role != 1) {
				ps = ct
						.prepareStatement(" select * from (select t.* ,rownum rn from (select  er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
								+ ", er.type,er.uvalid,el.realname,er.valid,cl.name clname,er.classid,co.name coname,er.courseid from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid "
								+ " left join course co on er.courseid = co.id  left join elclass cl on er.classid = cl.id left join exam_valids ev on ev.roomid = er.id "
								+ "where ev.userid =? "
								+ sqlW
								+ ")t where rownum<= ?) where rn>=? ");
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			} else {// 超级管理员通道
				sqlW = "where 1=1 " + sqlW;
				ps = ct
						.prepareStatement(" select * from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
								+ ", er.type,er.uvalid,el.realname,er.valid,cl.name clname,er.classid,co.name coname,er.courseid from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid "
								+ "left join course co on er.courseid = co.id  left join elclass cl on er.classid = cl.id  "
								+ sqlW + " )t where rownum<= ? ) where rn>=? ");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setUvalid(rs.getInt(10));
				er.setValid(rs.getInt(12));
				er.setElclass(new ElClass(rs.getInt(14), rs.getString(13)));
				er.setCourse(new Course(rs.getInt(16), rs.getString(15)));
				user = new ELUser();
				user.setRealname(rs.getString("realname"));
				er.setCreater(user);
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 场次复核list
	 * 
	 * @param userid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoomValid(int userid, int role, String sqlW,
			ExamRoom examRoom, int pageNow, int pageSize) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			String sql = "";
			String sql2 = "";
			if (examRoom != null) {
				if (examRoom.getElclass() != null
						&& examRoom.getElclass().getName() != null
						&& !examRoom.getElclass().getName().equals("")) { // 培训班名称
					sql2 += " and cl.name like '%"
							+ examRoom.getElclass().getName() + "%'";
				}
				if (examRoom.getCourse() != null
						&& examRoom.getCourse().getName() != null
						&& !examRoom.getCourse().getName().equals("")) {// 课程名称
					sql2 += " and c.name like '%"
							+ examRoom.getCourse().getName() + "%'";
				}
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {// 考场名称
					sql2 += " and er.title like '%" + examRoom.getTitle()
							+ "%'";
				}
				if (examRoom.getType() != -1) {// 考场类型
					sql2 += " and er.type=" + examRoom.getType();
				}
				if (examRoom.getValid() != -1) {// 考场状态
					sql2 += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sql2 += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql2 += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sql2 += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sql2 += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sql2 += " and er.classid>0";
				}
			} else {
				sql2 += " and er.classid=-1";
			}
			sql2 += " and (cl.isApplication=0 or cl.isApplication is null)";// 结业考场复核列表页只显示分配式的培训班里面的考场
			if (role != 1) {
				// ps = ct
				// .prepareStatement(" select * from (select t.* ,rownum rn from
				// (select er.id , er.title, er.begintime,
				// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
				// + ", er.type,er.uvalid,el.realname,er.valid from eluser el
				// inner join exam_room er on el.id=er.createrid left join
				// eroom_lib erlib on erlib.id=er.erlibid left join exam_valids
				// ev on ev.roomid = er.id "
				// + "where ev.userid =? "+sqlW +")t where rownum<= ?) where
				// rn>=? ");
				sql = " select * from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name , "
						+ "er.type,er.uvalid,el.realname,er.valid,c.id cid,c.name cname,cl.id clid, cl.name clname,er.svalid,er.isApplication,er.depname,er.jingzhong from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid left join course c on c.id=er.courseid left join elclass cl on cl.id=er.classid left join exam_valids ev on ev.roomid = er.id "
						+ "where ev.userid=? ";
				sql += sqlW;
				sql += sql2;
				sql += " )t where rownum<= ? ) where rn>=? ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			} else {// 超级管理员通道
				sqlW = " where 1=1 " + sqlW;
				sql = " select * from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name ,"
						+ " er.type,er.uvalid,el.realname,er.valid,c.id cid,c.name cname,cl.id clid, cl.name clname,er.svalid,er.isApplication,er.depname,er.jingzhong  from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid left join course c on c.id=er.courseid left join elclass cl on cl.id=er.classid";
				sql += sqlW;
				sql += sql2;
				sql += " )t where rownum<= ? ) where rn>=? ";
				ps = ct.prepareStatement(sql);
				// ps.setInt(1, userid);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setUvalid(rs.getInt(10));
				er.setValid(rs.getInt(12));
				user = new ELUser();
				user.setRealname(rs.getString(11));
				er.setCreater(user);
				er.setCourse(new Course(rs.getInt(13), rs.getString(14)));
				er.setElclass(new ElClass(rs.getInt(15), rs.getString(16)));
				er.setUsersize(this.getExamAllStudy(er.getId()));
				er.setSvalid(rs.getInt(17));
				er.setUserid(checkEroomIsUsers("valids", rs.getInt(1)));
				er.setIsApplication(rs.getInt(18));
				er.setPlanNumber(getEroomPlanNumber(rs.getInt(1)));
				er.setDepName(rs.getString(19));
				er.setJingzhong(rs.getString(20));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public List<ExamRoom> listExamRoomSelectings(int userid, int role,
			String sqlW, int pageNow, int pageSize) throws ElException {// hwc

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from (select er.id , er.title,
			// er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name
			// ,row_number() over( order by er.begintime desc)rownum from
			// exam_room er left join eroom_lib erlib on erlib.id=er.erlibid
			// where er.iscommon=1 and erlib.lid>=? and erlib.rid<=?)t where
			// t.rownum between ? and ?");
			if (role != 1) {
				ps = ct
						.prepareStatement(" select * from (select t.* ,rownum rn from (select  er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
								+ ", er.type,er.uvalid,el.realname,er.valid ,cl.name clname,er.classid,co.name coname,er.courseid  from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid "
								+ " left join course co on er.courseid = co.id  left join elclass cl on er.classid = cl.id left join exam_selectings ev on ev.roomid = er.id "
								+ "where ev.userid =? "
								+ sqlW
								+ ")t where rownum<= ?) where rn>=? ");
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			} else {// 超级管理员通道
				sqlW = "where 1=1 " + sqlW;
				ps = ct
						.prepareStatement(" select * from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
								+ ", er.type,er.uvalid,el.realname,er.valid ,cl.name clname,er.classid,co.name coname,er.courseid  from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid"
								+ " left join course co on er.courseid = co.id  left join elclass cl on er.classid = cl.id  "
								+ sqlW + " )t where rownum<= ? ) where rn>=? ");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setUvalid(rs.getInt(10));
				er.setValid(rs.getInt(12));
				er.setElclass(new ElClass(rs.getInt(14), rs.getString(13)));
				er.setCourse(new Course(rs.getInt(16), rs.getString(15)));
				user = new ELUser();
				user.setRealname(rs.getString("realname"));
				er.setCreater(user);
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 人员选拔list
	 */
	public List<ExamRoom> listExamRoomSelectings(int userid, int role,
			String sqlW, ExamRoom examRoom, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			String sql = "";
			String sql2 = "";
			if (examRoom != null) {
				if (examRoom.getElclass() != null
						&& examRoom.getElclass().getName() != null
						&& !examRoom.getElclass().getName().equals("")) { // 培训班名称
					sql2 += " and cl.name like '%"
							+ examRoom.getElclass().getName() + "%'";
				}
				if (examRoom.getCourse() != null
						&& examRoom.getCourse().getName() != null
						&& !examRoom.getCourse().getName().equals("")) {// 课程名称
					sql2 += " and c.name like '%"
							+ examRoom.getCourse().getName() + "%'";
				}
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {// 考场名称
					sql2 += " and er.title like '%" + examRoom.getTitle()
							+ "%'";
				}
				if (examRoom.getValid() != -1) {// 考场状态
					sql2 += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getSvalid() != -1) {// 选拨状态
					sql2 += " and er.svalid=" + examRoom.getSvalid();
				}
				if (examRoom.getBegintime() != null) {
					sql2 += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql2 += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sql2 += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sql2 += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sql2 += " and er.classid>0";
				}
			} else {
				sql2 += " and er.classid=-1";
			}
			if (role != 1) {
				sql = " select * from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name , "
						+ "er.type,er.uvalid,el.realname,er.valid,c.id cid,c.name cname,cl.id clid, cl.name clname,er.svalid,er.isApplication  from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid  left join course c on c.id=er.courseid  left join elclass cl on cl.id=er.classid left join exam_selectings ev on ev.roomid = er.id "
						+ "where ev.userid=? ";
				sql += sqlW;
				sql += sql2;
				sql += " )t where rownum<= ? ) where rn>=? ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			} else {// 超级管理员通道
				sqlW = "where 1=1 " + sqlW;
				sql = " select * from (select t.* ,rownum rn from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name , "
						+ "er.type,er.uvalid,el.realname,er.valid,c.id cid,c.name cname,cl.id clid, cl.name clname,er.svalid,er.isApplication  from eluser el inner join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid  left join course c on c.id=er.courseid  left join elclass cl on cl.id=er.classid ";
				// "er.type,er.uvalid,el.realname,er.valid,c.id cid,c.name cname
				// from eluser el inner join exam_room er on el.id=er.createrid
				// left join eroom_lib erlib on erlib.id=er.erlibid left join
				// course c on c.id=er.courseid ";
				sql += sqlW;
				sql += sql2;
				sql += " )t where rownum<= ? ) where rn>=? ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setUvalid(rs.getInt(10));
				er.setValid(rs.getInt(12));
				user = new ELUser();
				er
						.setCourse(new Course(rs.getInt("cid"), rs
								.getString("cname")));
				er.setElclass(new ElClass(rs.getInt("clid"), rs
						.getString("clname")));
				user.setRealname(rs.getString("realname"));
				er.setCreater(user);
				er
						.setCourse(new Course(rs.getInt("cid"), rs
								.getString("cname")));
				er.setUsersize(this.getExamAllStudy(er.getId()));
				er.setSvalid(rs.getInt("svalid"));
				er.setIsApplication(rs.getInt("isApplication"));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int listExamRoomValidsize(int userid, int role, String sqlW)
			throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			if (role != 1) {
				ps = ct
						.prepareStatement(" select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid "
								+ " left join course co on er.courseid = co.id  left join elclass cl on er.classid = cl.id left join exam_valids ev on ev.roomid = er.id where ev.userid =? "
								+ sqlW);
				ps.setInt(1, userid);
			} else {
				sqlW = "where 1=1 " + sqlW;
				ps = ct
						.prepareStatement(" select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid "
								+ "left join elclass cl on er.classid = cl.id left join course co on er.courseid = co.id "
								+ sqlW);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	/**
	 * 场次复核list数量
	 * 
	 * @param userid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	public int listExamRoomValidsize(int userid, int role, String sqlW,
			ExamRoom examRoom) throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "";
			String sql2 = "";
			if (examRoom != null) {
				if (examRoom.getElclass() != null
						&& examRoom.getElclass().getName() != null
						&& !examRoom.getElclass().getName().equals("")) { // 培训班名称
					sql2 += " and cl.name like '%"
							+ examRoom.getElclass().getName() + "%'";
				}
				if (examRoom.getCourse() != null
						&& examRoom.getCourse().getName() != null
						&& !examRoom.getCourse().getName().equals("")) {// 课程名称
					sql2 += " and c.name like '%"
							+ examRoom.getCourse().getName() + "%'";
				}
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {// 考场名称
					sql2 += " and er.title like '%" + examRoom.getTitle()
							+ "%'";
				}
				if (examRoom.getType() != -1) {// 考场类型
					sql2 += " and er.type=" + examRoom.getType();
				}
				if (examRoom.getValid() != -1) {// 考场状态
					sql2 += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sql2 += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql2 += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sql2 += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sql2 += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sql2 += " and er.classid>0";
				}
			} else {
				sql2 += " and er.classid=-1";
			}
			sql2 += " and (cl.isApplication=0 or cl.isApplication is null)";// 结业考场复核列表页只显示分配式的培训班里面的考场
			if (role != 1) {
				// ps = ct
				// .prepareStatement(" select count(er.id) from exam_room er
				// left join eroom_lib erlib on erlib.id=er.erlibid "
				// + "left join exam_valids ev on ev.roomid = er.id where
				// ev.userid =? "+sqlW );
				sql = " select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid  left join course c on c.id=er.courseid  left join elclass cl on cl.id=er.classid left join exam_valids ev on ev.roomid = er.id where ev.userid=? "
						+ sqlW;
				sql += sql2;
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
			} else {
				sqlW = "where 1=1 " + sqlW;
				// ps = ct
				// .prepareStatement(" select count(er.id) from exam_room er
				// left join eroom_lib erlib on erlib.id=er.erlibid "
				// + "left join exam_valids ev on ev.roomid = er.id " +sqlW);
				sql = " select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid  left join course c on c.id=er.courseid  left join elclass cl on cl.id=er.classid  "
						+ sqlW;
				sql += sql2;
				ps = ct.prepareStatement(sql);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	public int listExamRoomSelectingsSize(int userid, int role, String sqlW)
			throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			if (role != 1) {
				ps = ct
						.prepareStatement(" select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid "
								+ "left join exam_selectings ev on ev.roomid = er.id where ev.userid =? "
								+ sqlW);
				ps.setInt(1, userid);
			} else {
				sqlW = "where 1=1 " + sqlW;
				ps = ct
						.prepareStatement(" select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid "
								+ " left join course co on er.courseid = co.id  left join elclass cl on er.classid = cl.id "
								+ sqlW);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	/**
	 * 人员选拔list数量
	 * 
	 * @param userid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	public int listExamRoomSelectingsSize(int userid, int role, String sqlW,
			ExamRoom examRoom) throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "";
			String sql2 = "";
			if (examRoom != null) {
				if (examRoom.getElclass() != null
						&& examRoom.getElclass().getName() != null
						&& !examRoom.getElclass().getName().equals("")) { // 培训班名称
					sql2 += " and cl.name like '%"
							+ examRoom.getElclass().getName() + "%'";
				}
				if (examRoom.getCourse() != null
						&& examRoom.getCourse().getName() != null
						&& !examRoom.getCourse().getName().equals("")) {// 课程名称
					sql2 += " and c.name like '%"
							+ examRoom.getCourse().getName() + "%'";
				}
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {// 考场名称
					sql2 += " and er.title like '%" + examRoom.getTitle()
							+ "%'";
				}
				if (examRoom.getValid() != -1) {// 考场状态
					sql2 += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sql2 += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql2 += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sql2 += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sql2 += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sql2 += " and er.classid>0";
				}
			} else {
				sql2 += " and er.classid=-1";
			}
			if (role != 1) {
				sql = " select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid  left join course c on c.id=er.courseid  left join elclass cl on cl.id=er.classid left join exam_selectings ev on ev.roomid = er.id where ev.userid =?"
						+ sqlW;
				sql += sql2;
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
			} else {
				sqlW = "where 1=1 " + sqlW;
				sql = " select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid  left join course c on c.id=er.courseid  left join elclass cl on cl.id=er.classid left join exam_selectings ev on ev.roomid = er.id "
						+ sqlW;
				sql += sql2;
				ps = ct.prepareStatement(sql);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("人员选拔list数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	public List<ExamRoom> listExamRoomValid(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from (select er.id , er.title,
			// er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name
			// ,row_number() over( order by er.begintime desc)rownum from
			// exam_room er left join eroom_lib erlib on erlib.id=er.erlibid
			// where er.iscommon=1 and erlib.lid>=? and erlib.rid<=?)t where
			// t.rownum between ? and ?");
			ps = ct
					.prepareStatement("select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
							+ ", er.type,er.uvalid from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_valids ev on ev.roomid = er.id "
							+ "where ev.userid =? ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setUvalid(rs.getInt(10));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public List<ExamRoom> listExamRoomRead(int userid, String sqlw,
			int pageNOw, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.* ,rownum rn from ( select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
							+ ", er.type,el.realname from eluser el join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid left join exam_rappraises ev on ev.roomid = er.id "
							+ "where ev.userid =? "
							+ sqlw
							+ " order by er.begintime desc) t where rownum<= ?) where rn>=?");
			ps.setInt(1, userid);
			ps.setInt(2, pageNOw);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				user = new ELUser();
				user.setRealname(rs.getString("realname"));
				er.setCreater(user);
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 考核考试阅卷list
	 * 
	 * @param userid
	 * @param examRoom
	 * @param pageNOw
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoomRead(int userid, String sqlw,
			ExamRoom examRoom, int roleid, int pageNOw, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			String sql = "";
			if (roleid == 1) {
				sql = "select * from (select t.* ,rownum rn from ( select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name , er.type,el.realname,c.id cid,c.name cname,er.isApplication,er.depname,er.jingzhong,er.valid from eluser el join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid left join course c on c.id=er.courseid where 1=1 ";
			} else {
				sql = "select * from (select t.* ,rownum rn from ( select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name , er.type,el.realname,c.id cid,c.name cname,er.isApplication,er.depname,er.jingzhong,er.valid from eluser el join exam_room er on el.id=er.createrid left join eroom_lib erlib on erlib.id=er.erlibid left join course c on c.id=er.courseid left join exam_rappraises ev on ev.roomid = er.id where ev.userid =? ";
			}
			if (examRoom != null) {
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {
					sql += " and er.title like '%" + examRoom.getTitle() + "%'";
				}
				if (examRoom.getValid() != -1) {
					sql += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sql += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sqlw = "and er.iscommon=1 and er.valid != 9 ";
					sql += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sqlw = "and er.iscommon=0 and er.valid != 9 ";
					sql += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sqlw = "and er.iscommon=0 and er.valid != 9 ";
					sql += " and er.classid>0";
				} else {
					sqlw = " and er.valid != 9 ";
					sql += " and (er.classid=-1 or er.classid>=0)";
				}
			} else {
				sqlw = "and er.iscommon=1 and er.valid != 9 ";
				sql += " and er.classid=-1";
			}
			sql += sqlw;
			sql += " order by er.begintime desc) t where rownum<= ?) where rn>=?";
			ps = ct.prepareStatement(sql);
			if (roleid != 1) {
				ps.setInt(1, userid);
				ps.setInt(2, pageNOw);
				ps.setInt(3, pageSize);
			} else {
				ps.setInt(1, pageNOw);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				user = new ELUser();
				user.setRealname(rs.getString(10));
				er.setCreater(user);
				er.setCourse(new Course(rs.getInt(11), rs.getString(12)));
				er.setUsersize(this.getExamAllStudy(er.getId()));
				er.setIsApplication(rs.getInt(13));
				er.setPlanNumber(getEroomPlanNumber(er.getId()));
				er.setDepName(rs.getString(14));
				er.setJingzhong(rs.getString(15));
				er.setValid(rs.getInt(16));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("考核考试阅卷list失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int listExamRoomReadsize(int userid, String sqlw) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) as id from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
							+ ", er.type from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_rappraises ev on ev.roomid = er.id "
							+ "where ev.userid =? " + sqlw + ")");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 考核考试阅卷list数量
	 */
	public int listExamRoomReadsize(int userid, String sqlw, ExamRoom examRoom,
			int roleid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "";
			if (roleid == 1) {
				sql = "select count(*) as id from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name , er.type from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid where 1=1 ";
			} else {
				sql = "select count(*) as id from (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name , er.type from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_rappraises ev on ev.roomid = er.id where ev.userid =? ";
			}
			if (examRoom != null) {
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {
					sql += " and er.title like '%" + examRoom.getTitle() + "%'";
				}
				if (examRoom.getValid() != -1) {
					sql += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sql += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sqlw = "and er.iscommon=1 and er.valid != 9 ";
					sql += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sqlw = "and er.iscommon=0 and er.valid != 9 ";
					sql += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sqlw = "and er.iscommon=0 and er.valid != 9 ";
					sql += " and er.classid>0";
				} else {
					sqlw = " and er.valid != 9 ";
					sql += " and (er.classid=-1 or er.classid>=0)";
				}
			} else {
				sqlw = "and er.iscommon=1 and er.valid != 9 ";
				sql += " and er.classid=-1";
			}
			sql += sqlw;
			sql += ")";
			ps = ct.prepareStatement(sql);
			if (roleid != 1) {
				ps.setInt(1, userid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("考核考试阅卷list数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<MyExamPaper> listReadPapers(int roomid, int pN, int pS)
			throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.QPAPER_READ_BYRID));
			ps.setInt(1, roomid);
			ps.setInt(2, pN);
			ps.setInt(3, pS);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2), rs.getString(8)));
				mep.setExamRoom(new ExamRoom(rs.getInt(3)));
				mep.setExamPaper(new ExamPaper(rs.getInt(4)));
				mep.setStatus(rs.getInt(5));
				mep.setMyScore(rs.getInt(6));
				mep.setEndtime(rs.getTimestamp(7));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public int listReadPapersSize(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.QPAPER_READ_BYRID_SIZE));
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void requiz(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.QPAPER_REQUIZ));
			ps.setInt(1, id);
			ps.executeUpdate();
			ps = ct
					.prepareStatement("select id from study_quizinfo where id= ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				int sqid = rs.getInt(1);
				ps = ct
						.prepareStatement("delete from study_questions where sqid = ?");
				ps.setInt(1, sqid);
				ps.executeUpdate();
				ps = ct
						.prepareStatement("delete from study_blocks where sqid = ?");
				ps.setInt(1, sqid);
				ps.executeUpdate();

			}
		} catch (Exception e) {
			logger.error("监考管理失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int listExamRoomShSize(int libid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from eroom_lib where id = ?");
			int lid = 0, rid = 0;
			ps.setInt(1, libid);
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps = ct
					.prepareStatement(" select count(er.id) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid where erlib.lid>=? and erlib.rid<=?");

			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ExamRoom> listExamRoomSh(int pageNow, int pageSize, int libid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from eroom_lib where id = ?");
			int lid = 0, rid = 0;
			ps.setInt(1, libid);
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps = ct
					.prepareStatement("select * from (select t.* ,rownum rn from ( select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
							+ ", er.type,er.valid,er.uvalid from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid where erlib.lid>=? and erlib.rid<=? order by er.valid ) t where rownum <=?) where rn>=?");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setValid(rs.getInt(10));
				er.setUvalid(rs.getInt(11));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public List<ExamRoom> listExamRoomSh(EroomLib eroomLibTree, int pageNow,
			int pageSize, int erlibid, int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.* ,rownum rn from ( "
							+ "select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name, er.type,er.valid,er.uvalid,el.realname, "
							+ "from eluser el inner join exam_room er on el.id=er.createrid ,(select * from eroom_lib where  id in("
							+ ids
							+ ") ) erlib "
							+ "where erlib.id=er.erlibid order by er.begintime desc) t where rownum <=?) where rn>=?");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);

			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setPassgrade(rs.getFloat(6));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				er.setType(rs.getInt(9));
				er.setValid(rs.getInt(10));
				er.setUvalid(rs.getInt(11));
				user = new ELUser();
				user.setRealname(rs.getString("realname"));
				er.setCreater(user);
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int listExamRoomShSize(EroomLib eroomLibTree, int erlibid, int role)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from exam_room er ,(select * from eroom_lib where  id in("
							+ ids
							+ ") ) erlib "
							+ "where erlib.id=er.erlibid order by er.valid ");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ExamRoom> listExamRoomSh(int pageNow, int pageSize,
			EroomLib eroomLibTree, int[] erids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			if (erids != null) {
				for (int i = 0; i < erids.length; i++) {
					ps = ct
							.prepareStatement("select * from (select t.* ,rownum rn from ( select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
									+ ", er.type,er.valid,er.uvalid from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid where erlib.id in ("
									+ createEroomTypeId(eroomLibTree, erids[i])
									+ ") order by er.valid ) t where rownum <=?) where rn>=?");
					ps.setInt(1, pageNow);
					ps.setInt(2, pageSize);
					rs = ps.executeQuery();
					while (rs.next()) {
						ExamRoom er = new ExamRoom(rs.getInt(1), rs
								.getString(2));
						er.setBegintime(rs.getTimestamp(3));
						er.setEndtime(rs.getTimestamp(4));
						er.setLocation(rs.getString(5));
						er.setPassgrade(rs.getFloat(6));
						er.setEroomLib(new EroomLib(rs.getInt(7), rs
								.getString(8)));
						er.setType(rs.getInt(9));
						er.setValid(rs.getInt(10));
						er.setUvalid(rs.getInt(11));
						ers.add(er);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;

	}

	public int listExamRoomShCount(int pageNow, int pageSize,
			EroomLib eroomLibTree, int[] erids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int counts = 0;
		try {
			ct = DBConnection.getConnection();
			if (erids != null) {
				for (int i = 0; i < erids.length; i++) {
					ps = ct
							.prepareStatement("select count(*) from (select t.* ,rownum rn from ( select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name "
									+ ", er.type,er.valid,er.uvalid from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid where erlib.id in ("
									+ createEroomTypeId(eroomLibTree, erids[i])
									+ ") order by er.valid ) t where rownum <=?) where rn>=?");
					ps.setInt(1, pageNow);
					ps.setInt(2, pageSize);
					rs = ps.executeQuery();
					if (rs.next()) {
						counts += rs.getInt(1);
					}
				}
			}
			return counts;
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void examRoomSh(int roomid, int valid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set valid= ? where id = ?");
			ps.setInt(1, valid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void examRoomS(int roomid, int svalid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set svalid= ? where id = ?");
			ps.setInt(1, svalid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void examRoomUvalid(int roomid, int uvalid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set uvalid= ? where id = ?");
			ps.setInt(1, uvalid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void examRoomavalid(int roomid, int avalid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set avalid= ? where id = ?");
			ps.setInt(1, avalid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void examRoomisNormal(int roomid, int isNormal) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set isNormal = ? where id = ?");
			ps.setInt(1, isNormal);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void assign2dep(int roomid, int depid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into exam_dep(roomid,depid,epid) values(?,?,?)");
			ps.setInt(1, roomid);
			ps.setInt(2, depid);
			ps.setInt(3, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void unassign2dep(int roomid, int depid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from exam_dep where roomid = ? and depid=? and epid =?");
			ps.setInt(1, roomid);
			ps.setInt(2, depid);
			ps.setInt(3, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Department> listAssigned2dep(int roomid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select depid from exam_dep where roomid = ? and epid = ?");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new Department(rs.getInt(1)));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<Department> listCanAssign2dep(int roomid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from department where id not in(select depid from exam_dep where roomid = ? and epid =?)");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new Department(rs.getInt(1)));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<ELUser> listUsersBydep(int depid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> deps = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id =?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0, rid = 0;
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps = ct
					.prepareStatement("select eu.id from eluser eu left join department dep on dep.id = eu.depid where dep.lid>=? and dep.rid<=?");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new ELUser(rs.getInt(1)));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public void pracassign2dep(int pracid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into examprac_dep(pracid,depid) values(?,?)");
			ps.setInt(1, pracid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void pracunassign2dep(int pracid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from examprac_dep where roomid = ? and depid=?");
			ps.setInt(1, pracid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Department> listpracAssigned2dep(int pracid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select depid from examprac_dep where  pracid = ?");
			ps.setInt(1, pracid);
			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new Department(rs.getInt(1)));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<Department> listpracCanAssign2dep(int pracid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from department where id not in(select depid from examprac_dep where pracid = ?)");
			ps.setInt(1, pracid);
			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new Department(rs.getInt(1)));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	// 9999000
	public void addErbatchLib(EroomBatchLib erbatchLib) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// addNode(ct, erbatchLib, "erbatch_lib", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_ADD));
			ps.setString(1, erbatchLib.getName());
			ps.setString(2, erbatchLib.getDescription());
			ps.setInt(3, erbatchLib.getParent().getId());
			ps.setInt(4, erbatchLib.getLid());
			ps.setInt(5, erbatchLib.getRid());
			ps.executeUpdate();
			// TODO 获取 刚添加的id
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('erbatch_lib') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select erbatch_lib_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				erbatchLib.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterErbatchLib(EroomBatchLib ErbatchLib) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// alterNode(ct, ErbatchLib, "Erbatch_lib", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_ALTER));
			ps.setString(1, ErbatchLib.getName());
			ps.setString(2, ErbatchLib.getDescription());
			ps.setInt(3, ErbatchLib.getParent().getId());
			ps.setInt(4, ErbatchLib.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void deleteErbatchLib(Connection ct, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {// TODO 删除课程类别
			// 删除节点信息
			// EroomBatchLib ErbatchLib = new EroomBatchLib(id);
			// deleteNode(ct, ErbatchLib, "Erbatch_lib", "1 = 1");
			// 删除基本信息
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除课程类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteErbatchLib(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 删除课程类别
			ct = DBConnection.getConnection();
			int parentid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				parentid = rs.getInt(4);
			}
			rs.close();
			// 将该类别下课程设置成上级类别
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_ERBATCH_QUERY_BYCTID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				PreparedStatement ps1 = ct
						.prepareStatement(ElQuerySql
								.getSQL(CourseConstants.ERBATCHLIB_ERBATCH_ERBATCHLIB_SET));
				ps1.setInt(1, parentid);
				ps1.setInt(2, rs.getInt(1));
				ps1.executeUpdate();
				ps1.close();
			}
			rs.close();
			// 将该类别下类别设置成上级类别
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_PARENT_SET));
			ps.setInt(1, parentid);
			ps.setInt(2, id);

			deleteErbatchLib(ct, id);
		} catch (Exception e) {
			logger.error("删除课程类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteErbatchLibAndSub(int id) throws ElException {
		// TODO 删除课程类别
	}

	public EroomBatchLib getErbatchLibById(int id) throws ElException {
		EroomBatchLib ErbatchLib = new EroomBatchLib();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				ErbatchLib.setId(rs.getInt(1));
				ErbatchLib.setName(rs.getString(2));
				ErbatchLib.setDescription(rs.getString(3));
				ErbatchLib.setParent(new EroomBatchLib(rs.getInt(4), rs
						.getString(5)));
				ErbatchLib.setLid(rs.getInt(6));
				ErbatchLib.setRid(rs.getInt(7));
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ErbatchLib;
	}

	public List<EroomBatchLib> getErbatchLibChilds(int parentid)
			throws ElException {
		List<EroomBatchLib> cts = new ArrayList<EroomBatchLib>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_QUERY_CHILD));
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				EroomBatchLib ErbatchLib = new EroomBatchLib();
				ErbatchLib.setId(rs.getInt(1));
				ErbatchLib.setName(rs.getString(2));
				ErbatchLib.setParent(new EroomBatchLib(rs.getInt(3)));
				ErbatchLib.setLid(rs.getInt(4));
				ErbatchLib.setRid(rs.getInt(5));
				cts.add(ErbatchLib);
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cts;
	}

	public EroomBatchLib getErbatchLibRoot() throws ElException {
		EroomBatchLib ErbatchLib = new EroomBatchLib();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCHLIB_QUERY_BYPID));
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			while (rs.next()) {
				ErbatchLib.setId(rs.getInt(1));
				ErbatchLib.setName(rs.getString(2));
				ErbatchLib.setParent(new EroomBatchLib(rs.getInt(3)));
				ErbatchLib.setLid(rs.getInt(4));
				ErbatchLib.setRid(rs.getInt(5));
			}
		} catch (Exception e) {
			logger.error("获取课程类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ErbatchLib;
	}

	public EroomBatchLib getErbatchLibTree(int from, int stop,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		EroomBatchLib cltype = null;
		try {
			if (from == 0) {
				cltype = getErbatchLibRoot();
			} else {
				cltype = getErbatchLibById(from);
			}
			ct = DBConnection.getConnection();
			cltype.setChild(getErblChilds(ct, cltype.getId(), stop,
					containStop, 0));
		} catch (Exception e) {
			logger.error("培训班类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}

	private EroomBatchLib getErbatchLibTree(int from, int stop,
			boolean containStop, int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		EroomBatchLib cltype = null;
		try {
			cltype = getErbatchLibById(from);
			cltype.setLevel(level);
			ct = DBConnection.getConnection();
			cltype.setChild(getErblChilds(ct, cltype.getId(), stop,
					containStop, level));
		} catch (Exception e) {
			logger.error("培训班类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}

	public EroomBatchLib getErbatchLibTree(int userid, String op, int stopid,
			boolean containStop) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		EroomBatchLib dep = op.equals("op") ? new EroomBatchLib(-2, "可操作的考场库")
				: new EroomBatchLib(-2, "可使用的考场库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from questionlib_" + op
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<EroomBatchLib> list = new ArrayList<EroomBatchLib>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					EroomBatchLib depc = getErbatchLibTree(depid, stopid,
							containStop, 1);
					depc.setParent(dep);
					list.add(depc);
				}
			}
			dep.setChild(list);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return null;
	}

	private List<EroomBatchLib> getErblChilds(Connection ct, int from,
			int stop, boolean containStop, int level) throws Exception {
		List<EroomBatchLib> deps = new ArrayList<EroomBatchLib>();
		PreparedStatement ps = ct.prepareStatement(ElQuerySql
				.getSQL(CourseConstants.ERBATCHLIB_QUERY_BYPID));
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			EroomBatchLib dep = new EroomBatchLib(rstemp.getInt(1), rstemp
					.getString(2));
			// dep.setDescription(rstemp.getString(3));
			dep.setParent(new EroomBatchLib(rstemp.getInt(3)));
			dep.setLevel(level);
			if (dep.getId() != stop)
				dep.setChild(getErblChilds(ct, dep.getId(), stop, containStop,
						level));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		}
		ps.close();
		rstemp.close();
		return deps;
	}

	public void addErblOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into erbatchlib_" + type
					+ "_user(userid,depid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkErblOpUsers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from Erbatchlib_" + type
					+ "_user where userid = ? and depid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteErblOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from Erbatchlib_" + type
					+ "_user where userid = ? and depid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> getErblOpUsers(String type, int depid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from erbatchlib_"
							+ type
							+ "_user du left join eluser eu on eu.id = du.userid where du.depid = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return us;
	}

	public void addErbatch(EroomBatch erbatch) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCH_ADD));
			ps.setString(1, erbatch.getName());
			ps.setString(2, erbatch.getDescription());
			ps.setInt(3, 0);// erbatch.getBatchlib().getId();
			ps.setInt(4, erbatch.getCreater().getId());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('erbatch') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select erbatch_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				erbatch.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void alterErbatch(EroomBatch erbatch) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCH_ALTER));
			ps.setString(1, erbatch.getName());
			ps.setString(2, erbatch.getDescription());
			ps.setInt(3, 0);// erbatch.getBatchlib().getId()
			ps.setInt(4, erbatch.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void deleteErbatch(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCH_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
			ps = ct
					.prepareStatement("delete from erbatch_room where ERBID = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public EroomBatch getErbatchById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		EroomBatch eb = new EroomBatch();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eb.id ebid,eb.title,eb.description,eb.erblid,eb.creater,eu.realname from erbatch eb left join eluser eu on eu.id = eb.creater where eb.id= ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				eb.setId(rs.getInt(1));
				eb.setTitle(rs.getString(2));
				eb.setDescription(rs.getString(3));
				eb.setCreater(new ELUser(rs.getInt(4), rs.getString(5)));
				// eb
				// .setBatchlib(new EroomBatchLib(rs.getInt(4), rs
				// .getString(5)));
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eb;
	}

	public List<EroomBatch> listErbatch(int pageNow, int pageSize)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<EroomBatch> ebs = new ArrayList<EroomBatch>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCH_LIST));
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				EroomBatch eb = new EroomBatch();
				eb.setId(rs.getInt(1));
				eb.setTitle(rs.getString(2));
				// eb
				// .setBatchlib(new EroomBatchLib(rs.getInt(3), rs
				// .getString(4)));
				ebs.add(eb);
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ebs;
	}

	public int listErbatchCount() throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int i = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(eb.id) from erbatch  eb left join erbatch_lib ebl on eb.erblid = ebl.id  ");
			rs = ps.executeQuery();
			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return i;
	}

	public void addErbatchRoom(int roomid, int erblid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCH_EROOM_ADD));
			ps.setInt(1, roomid);
			ps.setInt(2, erblid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkErbatchRoom(int roomid, int erblid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCH_EROOM_CHECK));
			ps.setInt(1, roomid);
			ps.setInt(2, erblid);
			rs = ps.executeQuery();
			if (rs.next())
				b = true;
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	public void deleteErbatchRoom(int roomid, int erblid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCH_EROOM_DELETE));
			ps.setInt(1, roomid);
			ps.setInt(2, erblid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public List<ExamRoom> listErbatchRooms(int erblid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.ERBATCH_EROOM_LIST));
			ps.setInt(1, erblid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setCreater(new ELUser(rs.getInt(5), rs.getString(6)));
				er.getCreater().setUsername(rs.getString(7));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("添加课程类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public Department listAssignedDep(int depid, int courseid, int state,
			List<Integer> userid, String starttime, String endtime,
			String classname) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer deptsql = new StringBuffer();
			deptsql.append("select * from DEPARTMENT");
			ps = ct.prepareStatement(deptsql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dept = new Department();
				dept.setId(rs.getInt(1));
				dept.setName(rs.getString(2));
				ElNode node = new ElNode(rs.getInt(4));
				dept.setParent(node);
				deptList.add(dept);
			}

			StringBuffer usersql = new StringBuffer();
			usersql
					.append(
							"select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname from study_course sc ")
					.append(" left join eluser eu on sc.userid=eu.id")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id where sc.courseid=? ");
			if (starttime != null && !"".equals(starttime)) {
				usersql.append(" and to_char(sc.starttime,'yyyy-MM-dd')>='"
						+ starttime + "'");
			}
			if (endtime != null && !"".equals(endtime)) {
				usersql.append(" and to_char(sc.finishtime,'yyyy-MM-dd')<='"
						+ endtime + "'");
			}
			if (classname != null && !"".equals(classname)) {
				usersql
						.append(" and sc.classid in (select id from elclass where name like '%"
								+ classname + "%' )");
			}
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, courseid);
			// ps.setInt(2, state);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser();
				user.setId(rs.getInt(1));
				user.setRealname(rs.getString(2));
				user
						.setDepartment(new Department(rs.getInt(3), rs
								.getString(4)));
				user.setUsername(rs.getString(5));
				userList.add(user);
			}

		} catch (Exception e) {
			logger.error("培训学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return createDepartment(depid, deptList, userList, userid);
	}

	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, String classname, int examRoomId, int examPaperId,
			ELUser elUser, ElClType cltype, ElClType cltypeTree)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer deptsql = new StringBuffer();
			deptsql.append("select * from DEPARTMENT where id=?");
			ps = ct.prepareStatement(deptsql.toString());
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			rs.next();
			Department dept = new Department();
			dept.setId(rs.getInt(1));
			dept.setName(rs.getString(2));
			ElNode node = new ElNode(rs.getInt(4));
			dept.setParent(node);
			dept.setLid(rs.getInt("lid"));
			dept.setRid(rs.getInt("rid"));
			deptList.add(dept);

			StringBuffer usersql = new StringBuffer();
			usersql
					.append(
							"select * from(select t.*,rownum rn from ( select distinct eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from study_course sc ")
					.append(" left join eluser eu on sc.userid=eu.id")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id left join elclass c on c.id = sc.classid where sc.courseid=? and dp.lid>=? and dp.rid<=?");
			if (starttime != null && !"".equals(starttime)) {
				usersql.append(" and to_char(sc.starttime,'yyyy-MM-dd')>='"
						+ starttime + "'");
			}
			if (endtime != null && !"".equals(endtime)) {
				usersql.append(" and to_char(sc.finishtime,'yyyy-MM-dd')<='"
						+ endtime + "'");
			}
			if (classname != null && !"".equals(classname)) {
				usersql
						.append(" and sc.classid in (select id from elclass where name like '%"
								+ classname + "%' )");
			}
			if (cltype != null && cltype.getId() != -1
					&& cltype.getName() != null) {
				usersql.append(" and c.name like '%" + cltype.getName() + "%'");
				// usersql.append(" and sc.classid in ("
				// + createPerTypeId(cltypeTree, cltype.getId()) + ")");
			}
			if (elUser != null) {
				if (!elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (!elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (!elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				if (elUser.getJingzhong() > 0) {
					usersql.append(" and eu.jingzhong ="
							+ elUser.getJingzhong());
				}
				if (!elUser.getIsAssign().equals("")) {
					if (elUser.getIsAssign().equals("0")) {
						usersql
								.append(" and eu.id in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId + ")");
					} else {
						usersql
								.append(" and eu.id not in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId + ")");
					}
				}
			}
			usersql.append(" )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, courseid);
			ps.setInt(2, dept.getLid());
			ps.setInt(3, dept.getRid());
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			// ps.setInt(2, state);
			rs = ps.executeQuery();

			while (rs.next()) {
				ELUser user = new ELUser();
				user.setId(rs.getInt(1));
				user.setRealname(rs.getString(2));
				user
						.setDepartment(new Department(rs.getInt(3), rs
								.getString(4)));
				user.setUsername(rs.getString(5));
				user.setJingzhong(rs.getInt(6));
				user.setRole(new ElRole(rs.getInt(7), rs.getString(8)));
				user.setSex(rs.getString(9));
				user.setShenfenzheng(String.valueOf(getAge(rs.getString(10))));
				user.setAge(rs.getInt(11));
				user.setIsAssign("未分配");
				userList.add(user);
			}
			ps = ct
					.prepareStatement("select userid from study_quizinfo where roomid = ? and epid = ?");
			ps.setInt(1, examRoomId);
			ps.setInt(2, examPaperId);
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
					}
				}
			}

		} catch (Exception e) {
			logger.error("培训学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	/**
	 * 查询可以培训的学员(加培训班)
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, String classname, int examRoomId, int examPaperId,
			ELUser elUser, ElClType cltype, ElClType cltypeTree, int classid,
			Department depTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		String LidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			ct = DBConnection.getConnection();
			// if (depTree.getId() == -2) {
			// for (int i = 0; i < depTree.getChild().size(); i++) {
			// if (depids.equals("")) {
			// depids = depids + depTree.getChild().get(i).getId();
			// } else {
			// depids = depids + ","
			// + depTree.getChild().get(i).getId();
			// }
			// }
			// ps = ct
			// .prepareStatement("select lid,rid from DEPARTMENT where id in ("
			// + depids + ")");
			// rs = ps.executeQuery();
			// while (rs.next()) {
			// if(depTree.getChild().size()==1){
			// LidRid = LidRid + " (dp.lid >= " + rs.getInt(1)
			// + " and dp.rid <= " + rs.getInt(2) + ")";
			// break;
			// }
			// if (depTree.getChild().size() > 1
			// && depTree.getChild().size() != x && x > 1) {// 中间不用加
			// LidRid = LidRid + " or (dp.lid >= " + rs.getInt(1)
			// + " and dp.rid <= " + rs.getInt(2) + ")";
			// } else if (depTree.getChild().size() == x) {// 结束前面加 ）
			// LidRid = LidRid + " or (dp.lid >= " + rs.getInt(1)
			// + " and dp.rid <= " + rs.getInt(2) + "))";
			// } else {// 开始前面加 （
			// LidRid = LidRid + " ( (dp.lid >= " + rs.getInt(1)
			// + " and dp.rid <= " + rs.getInt(2) + ")";
			// }
			// x++;
			// }
			// } else {
			// // 获取 部门的左右值
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			// ps.setInt(1, depTree.getId());
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// dep.setId(rs.getInt(1));
			// dep.setLid(rs.getInt(2));
			// dep.setRid(rs.getInt(3));
			// LidRid = LidRid + " dp.lid>=" + rs.getInt(2)
			// + " and dp.rid<= " + rs.getInt(3);
			// }
			// }
			// ps.close();

			StringBuffer usersql = new StringBuffer();
			usersql
					.append(
							"select * from(select t.*,rownum rn from ( select distinct eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from study_course sc ")
					.append(" left join eluser eu on sc.userid=eu.id")
					.append(
							" inner join ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean(ElConstants.CLASS_ELNODESQL))
											.generateSQLByTree("DEPARTMENT",
													depTree, true)
									+ ") dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id left join elclass c on c.id = sc.classid where sc.courseid=? "
									+ "" + " and sc.classid=?");
			if (starttime != null && !"".equals(starttime)) {
				usersql.append(" and to_char(sc.starttime,'yyyy-MM-dd')>='"
						+ starttime + "'");
			}
			if (endtime != null && !"".equals(endtime)) {
				usersql.append(" and to_char(sc.finishtime,'yyyy-MM-dd')<='"
						+ endtime + "'");
			}
			if (classname != null && !"".equals(classname)) {
				usersql
						.append(" and sc.classid in (select id from elclass where name like '%"
								+ classname + "%' )");
			}
			if (cltype != null && cltype.getId() != -1
					&& cltype.getName() != null) {
				usersql.append(" and c.name like '%" + cltype.getName() + "%'");
				// usersql.append(" and sc.classid in ("
				// + createPerTypeId(cltypeTree, cltype.getId()) + ")");
			}
			if (elUser != null) {
				if (!elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (!elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (!elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				// if (null != elUser.getJingzhong()
				// && !elUser.getJingzhong().equals("0")
				// && !elUser.getJingzhong().equals("")) {
				// usersql.append(" and eu.jingzhong = '"
				// + elUser.getJingzhong().trim() + "' ");
				// }
				// if (null != elUser.getDishi() &&
				// !elUser.getDishi().equals("0")) {
				// usersql.append(" and eu.dishi = '"
				// + elUser.getDishi().trim() + "'");
				// }
				// if (null != elUser.getZhiji() &&
				// !elUser.getZhiji().equals("0")) {
				// usersql.append(" and eu.zhiji = '"
				// + elUser.getZhiji().trim() + "' ");
				// }
				// if (null != elUser.getZhiwu() &&
				// !elUser.getZhiwu().equals("0")) {
				// usersql.append(" and eu.zhiwu = '"
				// + elUser.getZhiwu().trim() + "' ");
				// }
				if (elUser.getJingzhong() > 0) {
					usersql.append(" and eu.jingzhong = "
							+ elUser.getJingzhong());
				}
				if (elUser.getDishi() > 0) {
					usersql.append(" and eu.dishi = " + elUser.getDishi());
				}
				if (elUser.getZhiji() > 0) {
					usersql.append(" and eu.zhiji = " + elUser.getZhiji());
				}
				if (elUser.getZhiwu() > 0) {
					usersql.append(" and eu.zhiwu = " + elUser.getZhiwu());
				}
				if (null != elUser.getGangwei()
						&& !elUser.getGangwei().equals("0")) {
					usersql.append(" and eu.gangwei = '"
							+ elUser.getGangwei().trim() + "' ");
				}
				if (!elUser.getIsAssign().equals("")) {
					if (elUser.getIsAssign().equals("0")) {
						usersql
								.append(" and eu.id in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId + ")");
					} else {
						usersql
								.append(" and eu.id not in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId + ")");
					}
				}
				if (!elUser.getIsQualified().equals("")) {
					if (elUser.getIsQualified().equals("0")) {
						usersql
								.append(" and eu.id in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId
										+ ""
										+ " and practimes >= "
										+ elUser.getPractimes()
										+ " and pracscore >= "
										+ elUser.getPracscore() + " )");
					} else {
						usersql
								.append(" and eu.id not in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId
										+ ""
										+ " and practimes >= "
										+ elUser.getPractimes()
										+ " and pracscore >= "
										+ elUser.getPracscore() + " )");
					}
				}
			}
			usersql.append(" )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, courseid);
			ps.setInt(2, classid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			// ps.setInt(2, state);
			rs = ps.executeQuery();

			while (rs.next()) {
				ELUser user = new ELUser();
				user.setId(rs.getInt(1));
				user.setRealname(rs.getString(2));
				user
						.setDepartment(new Department(rs.getInt(3), rs
								.getString(4)));
				user.setUsername(rs.getString(5));
				user.setJingzhong(rs.getInt(6));
				user.setRole(new ElRole(rs.getInt(7), rs.getString(8)));
				user.setSex(rs.getString(9));
				user.setShenfenzheng(String.valueOf(getAge(rs.getString(10))));
				user.setAge(rs.getInt(11));
				user.setIsAssign("未分配");
				userList.add(user);
			}
			// ps = ct.prepareStatement("select userid , practimes , pracscore
			// from study_quizinfo where roomid = ? and epid = ? and
			// classid=?");
			ps = ct
					.prepareStatement("select userid from study_exampaper where roomid = ? and epid = ? and classid=?");
			ps.setInt(1, examRoomId);
			ps.setInt(2, examPaperId);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
						// users.setPractimes(rs.getInt(2));
						// users.setPracscore(rs.getInt(3));
					}
				}
			}

		} catch (Exception e) {
			logger.error("培训学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int role,
			Department depTree, int depid, int courseid, int state,
			List<Integer> userid, String starttime, String endtime,
			String classname, int examRoomId, int examPaperId, ELUser elUser,
			ElClType cltype, ElClType cltypeTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer deptsql = new StringBuffer();
			deptsql.append("select * from DEPARTMENT where id=?");
			ps = ct.prepareStatement(deptsql.toString());
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			rs.next();
			Department dept = new Department();
			dept.setId(rs.getInt(1));
			dept.setName(rs.getString(2));
			ElNode node = new ElNode(rs.getInt(4));
			dept.setParent(node);
			dept.setLid(rs.getInt("lid"));
			dept.setRid(rs.getInt("rid"));
			deptList.add(dept);

			// hwc
			String x = Integer.toString(depid);
			String ids = createDepartmentId(depTree, depid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer usersql = new StringBuffer();
			usersql
					.append(
							"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from  ")
					.append(
							" ELUSER eu left join  DEPARTMENT dp on eu.depid = dp.id left join elrole role on role.id = eu.role")
					.append("  where  dp.id in(" + ids + ")");
			if (starttime != null && !"".equals(starttime)) {
				usersql.append(" and to_char(sc.starttime,'yyyy-MM-dd')>='"
						+ starttime + "'");
			}
			if (endtime != null && !"".equals(endtime)) {
				usersql.append(" and to_char(sc.finishtime,'yyyy-MM-dd')<='"
						+ endtime + "'");
			}
			if (classname != null && !"".equals(classname)) {
				usersql
						.append(" and sc.classid in (select id from elclass where name like '%"
								+ classname + "%' )");
			}
			if (cltype != null && cltype.getId() != -1
					&& cltype.getName() != null) {
				usersql.append(" and c.name like '%" + cltype.getName() + "%'");
				// usersql.append(" and sc.classid in ("
				// + createPerTypeId(cltypeTree, cltype.getId()) + ")");
			}
			if (elUser != null) {
				if (!elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (!elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (!elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				if (elUser.getJingzhong() > 0) {
					usersql.append(" and eu.jingzhong ="
							+ elUser.getJingzhong());
				}
				if (!elUser.getIsAssign().equals("")) {
					if (elUser.getIsAssign().equals("0")) {
						usersql
								.append(" and eu.id in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId + ")");
					} else {
						usersql
								.append(" and eu.id not in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId + ")");
					}
				}
			}
			usersql.append(" )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			// ps.setInt(1, courseid);
			// ps.setInt(2, dept.getLid());
			// ps.setInt(3, dept.getRid());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			// ps.setInt(2, state);
			rs = ps.executeQuery();

			while (rs.next()) {
				ELUser user = new ELUser();
				user.setId(rs.getInt(1));
				user.setRealname(rs.getString(2));
				user
						.setDepartment(new Department(rs.getInt(3), rs
								.getString(4)));
				user.setUsername(rs.getString(5));
				user.setJingzhong(rs.getInt(6));
				user.setRole(new ElRole(rs.getInt(7), rs.getString(8)));
				user.setSex(rs.getString(9));
				user.setShenfenzheng(String.valueOf(getAge(rs.getString(10))));
				user.setAge(rs.getInt(11));
				user.setIsAssign("未分配");
				userList.add(user);
			}
			ps = ct
					.prepareStatement("select userid from study_quizinfo where roomid = ? and epid = ?");
			ps.setInt(1, examRoomId);
			ps.setInt(2, examPaperId);
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
					}
				}
			}

		} catch (Exception e) {
			logger.error("培训学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	private static int getAge(String IDCardNum) {
		if (IDCardNum == null) {
			return -1;
		}
		int year, month, day, idLength = IDCardNum.length();
		Calendar cal1 = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		if (idLength == 18) {
			year = Integer.parseInt(IDCardNum.substring(6, 10));
			month = Integer.parseInt(IDCardNum.substring(10, 12));
			day = Integer.parseInt(IDCardNum.substring(12, 14));
		} else if (idLength == 15) {
			year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
			month = Integer.parseInt(IDCardNum.substring(8, 10));
			day = Integer.parseInt(IDCardNum.substring(10, 12));
		} else {
			return -1;
		}
		cal1.set(year, month, day);
		return getYearDiff(today, cal1);
	}

	private static int getYearDiff(Calendar cal, Calendar cal1) {
		int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));
		int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));
		return (y * 12 + m) / 12;
	}

	/**
	 * 查询出从ctid开始的有权限的培训班类型ID
	 * 
	 * @author luocw
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String createPerTypeId(ElClType ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree != null && ctypeTree.getId() != ctid) {
				ctypeTree = getClassTypeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree != null && ctypeTree.getChild() != null) {
				return createTypeId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree != null ? ctypeTree.getId() : null);
		} else {
			return null;
		}
	}

	private String createEroomTypeId(EroomLib ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree != null && ctypeTree.getId() != ctid) {
				ctypeTree = getEroomTypeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree != null && ctypeTree.getChild() != null) {
				return createLibId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree != null ? ctypeTree.getId() : null);
		} else {
			return null;
		}
	}

	/**
	 * 如果不是根节点开始 要找出开始节点
	 * 
	 * @author luocw
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private ElClType getClassTypeById(List<ElClType> listType, int ctid) {
		for (ElClType type : listType) {
			if (type.getId() != ctid) {
				return getClassTypeById(type.getChild(), ctid);
			} else {
				return type;
			}
		}
		return null;
	}

	private EroomLib getEroomTypeById(List<EroomLib> listType, int ctid) {
		for (EroomLib type : listType) {
			if (type.getId() != ctid) {
				return getEroomTypeById(type.getChild(), ctid);
			} else {
				return type;
			}
		}
		return null;
	}

	//
	/**
	 * 构建有权的培训班类型ID
	 * 
	 * @author luocw
	 * @param ctypeTree
	 * @return
	 */
	private String createTypeId(List<ElClType> listType, int id) {
		String ids = id + "";
		for (ElClType type : listType) {
			ids = ids + "," + createTypeId(type.getChild(), type.getId());
		}
		return ids;
	}

	private String createLibId(List<EroomLib> listType, int id) {
		String ids = id + "";
		for (EroomLib type : listType) {
			ids = ids + "," + createLibId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	// public int listAssignedUserSize(int depid, int courseid, int state,
	// List<Integer> userid, String starttime, String endtime,
	// String classname, int examRoomId, int examPaperId, ELUser elUser,
	// ElClType cltype, ElClType cltypeTree) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<Department> deptList = new ArrayList<Department>();
	// List<ELUser> userList = new ArrayList<ELUser>();
	// try {
	// ct = DBConnection.getConnection();
	// StringBuffer deptsql = new StringBuffer();
	// deptsql.append("select * from DEPARTMENT where id=?");
	// ps = ct.prepareStatement(deptsql.toString());
	// ps.setInt(1, depid);
	// rs = ps.executeQuery();
	// rs.next();
	// Department dept = new Department();
	// dept.setId(rs.getInt(1));
	// dept.setName(rs.getString(2));
	// ElNode node = new ElNode(rs.getInt(4));
	// dept.setParent(node);
	// dept.setLid(rs.getInt("lid"));
	// dept.setRid(rs.getInt("rid"));
	//
	// StringBuffer usersql = new StringBuffer();
	// usersql
	// .append(
	// "select count(*) from ( select distinct eu.id userid,eu.realname
	// username,dp.id deptid,dp.name,eu.username
	// deptname,eu.jingzhong,role.id,role.name rolename from study_course sc ")
	// .append(" left join eluser eu on sc.userid=eu.id")
	// .append(
	// " left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on
	// eu.role=role.id left join elclass c on c.id = sc.classid where
	// sc.courseid=? and dp.lid>=? and dp.rid<=?");
	// if (starttime != null && !"".equals(starttime)) {
	// usersql.append(" and to_char(sc.starttime,'yyyy-MM-dd')>='"
	// + starttime + "'");
	// }
	// if (endtime != null && !"".equals(endtime)) {
	// usersql.append(" and to_char(sc.finishtime,'yyyy-MM-dd')<='"
	// + endtime + "'");
	// }
	// if (classname != null && !"".equals(classname)) {
	// usersql
	// .append(" and sc.classid in (select id from elclass where name like '%"
	// + classname + "%' )");
	// }
	// if (cltype != null && cltype.getId() != -1
	// && cltype.getName() != null) {
	// usersql.append(" and c.name like '%" + cltype.getName() + "%'");
	// // usersql.append(" and sc.classid in ("
	// // + createPerTypeId(cltypeTree, cltype.getId()) + ")");
	// }
	// if (elUser != null) {
	// if (!elUser.getSex().equals("")) {
	// usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
	// }
	// if (!elUser.getRealname().equals("")) {
	// usersql.append(" and eu.realname like '%"
	// + elUser.getRealname() + "%'");
	// }
	// if (!elUser.getUsername().equals("")) {
	// usersql.append(" and eu.username like '%"
	// + elUser.getUsername() + "%'");
	// }
	// if (elUser.getJingzhong()>0) {
	// usersql.append(" and eu.jingzhong ="
	// + elUser.getJingzhong());
	// }
	// if (!elUser.getIsAssign().equals("")) {
	// if (elUser.equals("0")) {
	// usersql
	// .append(" and eu.id in (select userid from study_quizinfo where roomid =
	// "
	// + examRoomId
	// + " and epid = "
	// + examPaperId + ")");
	// } else {
	// usersql
	// .append(" and eu.id not in (select userid from study_quizinfo where
	// roomid = "
	// + examRoomId
	// + " and epid = "
	// + examPaperId + ")");
	// }
	// }
	// }
	// usersql.append(" )t ");
	// ps = ct.prepareStatement(usersql.toString());
	// ps.setInt(1, courseid);
	// ps.setInt(2, dept.getLid());
	// ps.setInt(3, dept.getRid());
	// // ps.setInt(2, state);
	// rs = ps.executeQuery();
	// rs.next();
	// return rs.getInt(1);
	// } catch (Exception e) {
	// logger.error("培训学员失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }
	/**
	 * 查询可以培训的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	// public int listAssignedUserSize(int role, Department depTree, int depid,
	// int courseid, int state, List<Integer> userid, String starttime,
	// String endtime, String classname, int examRoomId, int examPaperId,
	// ELUser elUser, ElClType cltype, ElClType cltypeTree)
	// throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<Department> deptList = new ArrayList<Department>();
	// List<ELUser> userList = new ArrayList<ELUser>();
	// try {
	// ct = DBConnection.getConnection();
	// StringBuffer deptsql = new StringBuffer();
	// deptsql.append("select * from DEPARTMENT where id=?");
	// ps = ct.prepareStatement(deptsql.toString());
	// ps.setInt(1, depid);
	// rs = ps.executeQuery();
	// rs.next();
	// Department dept = new Department();
	// dept.setId(rs.getInt(1));
	// dept.setName(rs.getString(2));
	// ElNode node = new ElNode(rs.getInt(4));
	// dept.setParent(node);
	// dept.setLid(rs.getInt("lid"));
	// dept.setRid(rs.getInt("rid"));
	//
	// String x = Integer.toString(depid);
	// String ids = createDepartmentId(depTree, depid);
	// if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
	// // ,当角色不为1时ids的只有一个根节点时也不截取
	// ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
	// : ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
	//
	// StringBuffer usersql = new StringBuffer();
	// usersql
	// .append(
	// "select count(*) from ( select eu.id userid,eu.realname username,dp.id
	// deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name
	// rolename from ")
	// .append(
	// " ELUSER eu left join DEPARTMENT dp on eu.depid = dp.id left join elrole
	// role on role.id = eu.role")
	// .append(" where dp.id in (" + ids + ")");
	// if (starttime != null && !"".equals(starttime)) {
	// usersql.append(" and to_char(sc.starttime,'yyyy-MM-dd')>='"
	// + starttime + "'");
	// }
	// if (endtime != null && !"".equals(endtime)) {
	// usersql.append(" and to_char(sc.finishtime,'yyyy-MM-dd')<='"
	// + endtime + "'");
	// }
	// if (classname != null && !"".equals(classname)) {
	// usersql
	// .append(" and sc.classid in (select id from elclass where name like '%"
	// + classname + "%' )");
	// }
	// if (cltype != null && cltype.getId() != -1
	// && cltype.getName() != null) {
	// usersql.append(" and c.name like '%" + cltype.getName() + "%'");
	// // usersql.append(" and sc.classid in ("
	// // + createPerTypeId(cltypeTree, cltype.getId()) + ")");
	// }
	// if (elUser != null) {
	// if (!elUser.getSex().equals("")) {
	// usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
	// }
	// if (!elUser.getRealname().equals("")) {
	// usersql.append(" and eu.realname like '%"
	// + elUser.getRealname() + "%'");
	// }
	// if (!elUser.getUsername().equals("")) {
	// usersql.append(" and eu.username like '%"
	// + elUser.getUsername() + "%'");
	// }
	// if (elUser.getJingzhong()>0) {
	// usersql.append(" and eu.jingzhong ="
	// + elUser.getJingzhong());
	// }
	// if (!elUser.getIsAssign().equals("")) {
	// if (elUser.equals("0")) {
	// usersql
	// .append(" and eu.id in (select userid from study_quizinfo where roomid =
	// "
	// + examRoomId
	// + " and epid = "
	// + examPaperId + ")");
	// } else {
	// usersql
	// .append(" and eu.id not in (select userid from study_quizinfo where
	// roomid = "
	// + examRoomId
	// + " and epid = "
	// + examPaperId + ")");
	// }
	// }
	// }
	// usersql.append(" )t ");
	// ps = ct.prepareStatement(usersql.toString());
	// // ps.setInt(1, courseid);
	// // ps.setInt(2, dept.getLid());
	// // ps.setInt(3, dept.getRid());
	// // ps.setInt(2, state);
	// rs = ps.executeQuery();
	// rs.next();
	// return rs.getInt(1);
	// } catch (Exception e) {
	// logger.error("培训学员失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }
	/**
	 * 查询可以培训的学员(classid)
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public int listAssignedUserSize(int depid, int courseid, int state,
			List<Integer> userid, String starttime, String endtime,
			String classname, int examRoomId, int examPaperId, ELUser elUser,
			ElClType cltype, ElClType cltypeTree, int classid,
			Department depTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String LidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			if (depTree.getId() == -2) {
				for (int i = 0; i < depTree.getChild().size(); i++) {
					if (depids.equals("")) {
						depids = depids + depTree.getChild().get(i).getId();
					} else {
						depids = depids + ","
								+ depTree.getChild().get(i).getId();
					}
				}
				ps = ct
						.prepareStatement("select lid,rid from DEPARTMENT where id in ("
								+ depids + ")");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (depTree.getChild().size() == 1) {
						LidRid = LidRid + "  (dp.lid >= " + rs.getInt(1)
								+ " and  dp.rid <= " + rs.getInt(2) + ")";
						break;
					}
					if (depTree.getChild().size() > 1
							&& depTree.getChild().size() != x && x > 1) {// 中间不用加
						LidRid = LidRid + " or (dp.lid >= " + rs.getInt(1)
								+ " and  dp.rid <= " + rs.getInt(2) + ")";
					} else if (depTree.getChild().size() == x) {// 结束前面加 ）
						LidRid = LidRid + "  or (dp.lid >= " + rs.getInt(1)
								+ " and  dp.rid <= " + rs.getInt(2) + "))";
					} else {// 开始前面加 （
						LidRid = LidRid + "  ( (dp.lid >= " + rs.getInt(1)
								+ " and  dp.rid <= " + rs.getInt(2) + ")";
					}
					x++;
				}
			} else {
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, depid);
				rs = ps.executeQuery();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
					dep.setLid(rs.getInt(2));
					dep.setRid(rs.getInt(3));
					LidRid = LidRid + " dp.lid>=" + rs.getInt(2)
							+ " and dp.rid<= " + rs.getInt(3);
				}
			}
			ps.close();

			StringBuffer usersql = new StringBuffer();
			usersql
					.append(
							"select count(*) from  ( select distinct eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename from study_course sc ")
					.append(" left join eluser eu on sc.userid=eu.id")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id left join elclass c on c.id = sc.classid where sc.courseid=? "
									+ LidRid + " and sc.classid=?");
			if (starttime != null && !"".equals(starttime)) {
				usersql.append(" and to_char(sc.starttime,'yyyy-MM-dd')>='"
						+ starttime + "'");
			}
			if (endtime != null && !"".equals(endtime)) {
				usersql.append(" and to_char(sc.finishtime,'yyyy-MM-dd')<='"
						+ endtime + "'");
			}
			if (classname != null && !"".equals(classname)) {
				usersql
						.append(" and sc.classid in (select id from elclass where name like '%"
								+ classname + "%' )");
			}
			if (cltype != null && cltype.getId() != -1
					&& cltype.getName() != null) {
				usersql.append(" and c.name like '%" + cltype.getName() + "%'");
				// usersql.append(" and sc.classid in ("
				// + createPerTypeId(cltypeTree, cltype.getId()) + ")");
			}
			if (elUser != null) {
				if (!elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (!elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (!elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				// if (null != elUser.getJingzhong()
				// && !elUser.getJingzhong().equals("0")
				// && !elUser.getJingzhong().equals("")) {
				// usersql.append(" and eu.jingzhong = '"
				// + elUser.getJingzhong().trim() + "' ");
				// }
				// if (null != elUser.getDishi() &&
				// !elUser.getDishi().equals("0")
				// && !elUser.getJingzhong().equals("")) {
				// usersql.append(" and eu.dishi = '"
				// + elUser.getDishi().trim() + "'");
				// }
				// if (null != elUser.getZhiji() &&
				// !elUser.getZhiji().equals("0")
				// && !elUser.getJingzhong().equals("")) {
				// usersql.append(" and eu.zhiji = '"
				// + elUser.getZhiji().trim() + "' ");
				// }
				// if (null != elUser.getZhiwu() &&
				// !elUser.getZhiwu().equals("0")
				// && !elUser.getJingzhong().equals("")) {
				// usersql.append(" and eu.zhiwu = '"
				// + elUser.getZhiwu().trim() + "' ");
				// }
				if (elUser.getJingzhong() > 0) {
					usersql.append(" and eu.jingzhong = "
							+ elUser.getJingzhong());
				}
				if (elUser.getDishi() > 0) {
					usersql.append(" and eu.dishi = " + elUser.getDishi());
				}
				if (elUser.getZhiji() > 0) {
					usersql.append(" and eu.zhiji = " + elUser.getZhiji());
				}
				if (elUser.getZhiwu() > 0) {
					usersql.append(" and eu.zhiwu = " + elUser.getZhiwu());
				}
				if (null != elUser.getGangwei()
						&& !elUser.getGangwei().equals("0")
						&& !elUser.getGangwei().equals("")) {
					usersql.append(" and eu.gangwei = '"
							+ elUser.getGangwei().trim() + "' ");
				}
				if (!elUser.getIsAssign().equals("")) {
					if (elUser.getIsAssign().equals("0")) {
						usersql
								.append(" and eu.id  in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId + ")");
					} else {
						usersql
								.append(" and eu.id not in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId + ")");
					}
				}
				if (!elUser.getIsQualified().equals("")) {
					if (elUser.getIsQualified().equals("0")) {
						usersql
								.append(" and eu.id in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId
										+ ""
										+ " and practimes >= "
										+ elUser.getPractimes()
										+ " and pracscore >= "
										+ elUser.getPracscore() + " )");
					} else {
						usersql
								.append(" and eu.id not in (select userid from study_quizinfo where roomid = "
										+ examRoomId
										+ " and epid = "
										+ examPaperId
										+ ""
										+ " and practimes >= "
										+ elUser.getPractimes()
										+ " and pracscore >= "
										+ elUser.getPracscore() + " )");
					}
				}
			}
			usersql.append(" )t ");
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, courseid);
			ps.setInt(2, classid);
			// ps.setInt(2, state);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("培训学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 构建部门树
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param deptList
	 * @param userList
	 * @return
	 */
	private Department createDepartment(int depid, List<Department> deptList,
			List<ELUser> userList, List<Integer> userid) {
		Department rdept = new Department();
		for (Department dept : deptList) {
			if (depid == dept.getId()) {
				rdept = dept;
				addUser(rdept, userList, userid);
				rdept.setChild(createChildDepartment(rdept.getId(), deptList,
						userList, 0, userid));
			}
		}
		return rdept;
	}

	/**
	 * 递归构建下级部门节点
	 * 
	 * @author jiahaijiang
	 * @param pareid
	 * @param deptList
	 * @param userList
	 * @return
	 */
	private List<Department> createChildDepartment(int pareid,
			List<Department> deptList, List<ELUser> userList, int level,
			List<Integer> userid) {
		List<Department> listDept = new ArrayList<Department>();
		level++;
		for (Department dept : deptList) {
			if (pareid == dept.getParent().getId()) {
				addUser(dept, userList, userid);
				dept.setChild(createChildDepartment(dept.getId(), deptList,
						userList, level, userid));
				dept.setLevel(level);
				listDept.add(dept);
			}
		}
		return listDept;
	}

	/**
	 * 部门上添加用户
	 * 
	 * @author jiahaijiang
	 * @param dept
	 * @param userList
	 */
	private void addUser(Department dept, List<ELUser> userList,
			List<Integer> userid) {
		List<ELUser> list = new ArrayList<ELUser>();
		for (ELUser user : userList) {
			if (dept.getId() == user.getDepartment().getId()) {
				if (userid != null) {
					for (Integer uid : userid) {
						if (uid == user.getId()) {
							list.add(user);
						}
					}
				} else {
					list.add(user);
				}
			}
		}
		dept.setUsers(list);
	}

	/**
	 * 查询已经分配的学员
	 * 
	 * @author jiahaijiang
	 * @param depid
	 * @param courseid
	 * @param state
	 * @return
	 * @throws ElException
	 */
	public Department listCanAssignDep(int depid, int courseid, int state,
			int examRoomId, int examPaperId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Integer> userid = new ArrayList<Integer>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select userid from study_quizinfo where roomid = ? and epid = ?");
			ps.setInt(1, examRoomId);
			ps.setInt(2, examPaperId);
			rs = ps.executeQuery();
			while (rs.next()) {
				userid.add(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("查询结业考试已经分配的学员报错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return listAssignedDep(depid, courseid, state, userid, "", "", "");
	}

	public List<MyRoom> listEroomjks(int roomid, int pageNow, int pageSize)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> us = new ArrayList<MyRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from(select t.*,rownum rn from (select eu.id euid,eu.realname,eu.username,dep.id depid,dep.name,sr.macAddress,sr.ipAddress "
							+ "from study_room sr left join eluser eu on sr.userid = eu.id left join department dep on dep.id = eu.depid "
							+ "where sr.roomid =? group by eu.id ,eu.realname,eu.username,dep.id  ,dep.name,sr.macAddress,sr.ipAddress)t where rownum<=? ) where rn>=?");
			ps.setInt(1, roomid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom user = new MyRoom();
				user.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				user.getTester().setUsername(rs.getString(3));
				user.getTester().setDepartment(
						new Department(rs.getInt(4), rs.getString(5)));
				// user.setEpsize(rs.getInt(6));
				user.setMacAddress(rs.getString("macAddress"));
				user.setIpAddress(rs.getString("ipAddress"));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	/**
	 * 获取考场学员信息
	 * 
	 * @param roomid
	 * @param eluser
	 * @param epstatus
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyRoom> listEroomjks(int roomid, ELUser eluser, int epstatus,
			int pageNow, int pageSize, Timestamp beginTime, Timestamp endTime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyRoom> us = new ArrayList<MyRoom>();
		String username = "";
		String realname = "";
		String sqlw = "";
		if (eluser != null) {
			username = eluser.getUsername();
			realname = eluser.getRealname();
			if (epstatus != -2) {
				// status = epstatus + "";
				sqlw = " and sr.status=" + epstatus;
			}
		}
		if (beginTime != null) {
			sqlw += " and sr.begintime>= to_date('"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(beginTime) + "','yyyy-mm-dd hh24:mi:ss') ";
		}
		if (endTime != null) {
			sqlw += " and sr.begintime<=to_date('"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(endTime) + "','yyyy-mm-dd hh24:mi:ss') ";
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from(select t.*,rownum rn from (select eu.id euid,eu.realname,eu.username,dep.id depid,dep.name,sr.macAddress,sr.ipAddress,sr.status,(select count(id) from study_quizinfo where roomid =? and userid=sr.userid) sqicount "
							+ "from study_room sr left join eluser eu on sr.userid = eu.id left join department dep on dep.id = eu.depid "
							+ "where sr.roomid =? and eu.username like ? and eu.realname like ? "
							+ sqlw
							+ " order by eu.id)t where rownum<=? ) where rn>=?");

			ps.setInt(1, roomid);
			// ps.setString(2, "%" + status + "%");
			ps.setInt(2, roomid);
			ps.setString(3, "%" + username + "%");
			ps.setString(4, "%" + realname + "%");
			// ps.setString(4, "%"+status+"%");
			ps.setInt(5, pageNow);
			ps.setInt(6, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyRoom user = new MyRoom();
				user.setTester(new ELUser(rs.getInt(1), rs.getString(2)));
				user.getTester().setUsername(rs.getString(3));
				user.getTester().setDepartment(
						new Department(rs.getInt(4), rs.getString(5)));
				// user.setEpsize(rs.getInt(6));
				user.setMacAddress(rs.getString("macAddress"));
				user.setIpAddress(rs.getString("ipAddress"));
				user.setStatus(rs.getInt(8));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	/**
	 * 解除用户mac地址
	 * 
	 * @param userid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int updateMacAddr(int userid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_room set macaddress=null where userid=? and roomid=?");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("更新用户mac地址出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	public int eroom_pwdalter(String pwd, Date d, int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set pwd=? ,pwdtime=? where id=?");
			ps.setString(1, pwd);
			ps.setTimestamp(2, new Timestamp(d.getTime()));
			ps.setInt(3, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新用户mac地址出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	public void eroom_epcacherefresh(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epid,status from exam_reps where roomid=? ");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				PreparedStatement ps1 = ct
						.prepareStatement("call setcacheep(?,?)");
				ps1.setInt(1, roomid);
				ps1.setInt(2, rs.getInt(1));
				ps1.executeUpdate();
				ps1.close();
				ps1 = null;
			}
		} catch (Exception e) {
			logger.error("更新用户mac地址出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int listEroomjksize(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(sr.userid) from study_room sr left join eluser eu on sr.userid = eu.id left join department dep on dep.id = eu.depid "
							+ " where sr.roomid =? ");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	/**
	 * 查询考场学员的数量
	 * 
	 * @param roomid
	 * @param eluser
	 * @param epstatus
	 * @return
	 * @throws ElException
	 */
	public int listEroomjksize(int roomid, ELUser eluser, int epstatus)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		String username = "";
		String realname = "";
		String status = "";
		if (eluser != null) {
			username = eluser.getUsername();
			realname = eluser.getRealname();
			if (epstatus != -2) {
				// status = epstatus + "";
				status = " and sr.status=" + epstatus;
			}
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select count(sr.userid) from study_room sr left join eluser eu on sr.userid = eu.id left join department dep on dep.id = eu.depid "
							+ " where sr.roomid =? and eu.username like ? and eu.realname like ? "
							+ status);
			ps.setInt(1, roomid);
			// ps.setString(2, "%" + status + "%");
			ps.setString(2, "%" + username + "%");
			ps.setString(3, "%" + realname + "%");
			// ps.setString(4, "%"+status+"%");
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	public String getDztest(Question question, int age) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String x = "";
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select getDzScoreandStatus(?,?,?,? ) from dual");
			ps.setString(1, question.getStuAnswer());
			ps.setString(2, question.getRulestring());
			ps.setInt(3, age);
			ps.setFloat(4, question.getScore());
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getString(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	public void testerAddTime(int id, int time) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set jiashi =jiashi+ ? where id = ?");
			ps.setInt(1, time);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void testersAddTime(int roomid, int time) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set jiashi =jiashi+ ? where roomid = ?");
			ps.setInt(1, time);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ExamRoom> listMyDepExamRoom(EroomLib eroomLibTree, int erlibid,
			int role, String sqlW, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();

		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.* ,rownum rn from (select er.id, er.createrid, er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid) ,c.name cname,er.courseid,er.avalid,er.uvalid,er.classid from (exam_room er left join course c on c.id = er.courseid) left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id , (select * from eroom_lib where id in ("
					+ ids
					+ ") ) ct where ct.id=er.erlibid "
					+ sqlW
					+ " group by er.id ,er.createrid,er.title,er.begintime,er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid,er.avalid,er.uvalid,er.classid order by er.begintime desc) t where rownum<=?) where rn>=?";
			// ps = ct.prepareStatement("select * from (select t.* ,rownum rn
			// from (select er.createrid, er.id , er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid)"
			// +
			// " ,c.name cname,er.courseid from (exam_room er left join course c
			// on c.id = er.courseid) left join eroom_lib erlib on
			// erlib.id=er.erlibid left join exam_reps erep on erep.roomid =
			// er.id ," +
			// " (select * from eroom_lib where id in("+ids+") ) ct where
			// ct.id=er.erlibid " +
			// " group by er.createrid , er.id , er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid
			// order by er.begintime desc) t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(3));
				user = new ELUser();
				user.setId(rs.getInt("createrid"));
				UserDao ud = new UserDaoImpl();
				user = ud.getUserById(user.getId());
				er.setCreater(user);
				er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				// er.setBegintime(rs.getTimestamp(3));
				// er.setEndtime(rs.getTimestamp(4));
				// er.setLocation(rs.getString(5));
				// er.setPassgrade(rs.getFloat(6));
				// er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				// er.setType(rs.getInt(9));
				// er.setValid(rs.getInt(10));
				// er.setEpsize(rs.getInt(11));
				// er.setCourse(new Course(rs.getInt(13), rs.getString(12)));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setLocation(rs.getString(6));
				er.setPassgrade(rs.getFloat(7));
				er.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt(11));
				er.setEpsize(rs.getInt(12));
				er.setAvalid(rs.getInt(15));
				er.setUvalid(rs.getInt(16));
				er.setClassid(rs.getInt(17));
				er.setCourse(new Course(rs.getInt(14), rs.getString(13)));
				ers.add(er);
				// er.getCreater().getUsername();
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 考场初审list
	 * 
	 * @param eroomLibTree
	 * @param erlibid
	 * @param role
	 * @param sqlW
	 * @param examEoom
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listMyDepExamRoom(EroomLib eroomLibTree, int erlibid,
			int role, String sqlW, ExamRoom examRoom, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();

		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.* ,rownum rn from (select er.id, er.createrid, er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid) ,c.name cname,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication from (exam_room er left join course c on c.id = er.courseid) left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id , (select * from eroom_lib where id in ("
					+ ids + ") ) ct where ct.id=er.erlibid " + sqlW + " ";
			if (examRoom != null) {
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {
					sql += " and er.title like '%" + examRoom.getTitle() + "%'";
				}
				if (examRoom.getValid() != -1) {
					sql += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getSvalid() != -1) {
					sql += " and er.svalid=" + examRoom.getSvalid();
				}
				if (examRoom.getBegintime() != null) {
					sql += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sql += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sql += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sql += " and er.classid>0";
				}
			} else {
				sql += " and er.classid=-1";
			}
			sql += " group by er.id ,er.createrid,er.title,er.begintime,er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication order by er.begintime desc) t where rownum<=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(3));
				user = new ELUser();
				user.setId(rs.getInt("createrid"));
				UserDao ud = new UserDaoImpl();
				user = ud.getUserById(user.getId());
				er.setCreater(user);
				er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				// er.setBegintime(rs.getTimestamp(3));
				// er.setEndtime(rs.getTimestamp(4));
				// er.setLocation(rs.getString(5));
				// er.setPassgrade(rs.getFloat(6));
				// er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				// er.setType(rs.getInt(9));
				// er.setValid(rs.getInt(10));
				// er.setEpsize(rs.getInt(11));
				// er.setCourse(new Course(rs.getInt(13), rs.getString(12)));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setLocation(rs.getString(6));
				er.setPassgrade(rs.getFloat(7));
				er.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt(11));
				er.setEpsize(rs.getInt(12));
				er.setAvalid(rs.getInt(15));
				er.setUvalid(rs.getInt(16));
				er.setClassid(rs.getInt(17));
				er.setCourse(new Course(rs.getInt(14), rs.getString(13)));
				er.setUsersize(this.getExamAllStudy(er.getId()));
				er.setSvalid(rs.getInt("svalid"));
				er.setIsApplication(rs.getInt("isApplication"));
				er.setPlanNumber(getEroomPlanNumber(rs.getInt(1)));
				ers.add(er);
				// er.getCreater().getUsername();
			}
		} catch (Exception e) {
			logger.error("考场初审list失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 检测参数
	 */
	private void checkParams(StringBuffer sql, List<Object> params,
			ExamRoom examRoom) {
		if (examRoom != null) {
			if (examRoom.getTitle() != null && !"".equals(examRoom.getTitle())) {
				// sql.append(" and er.title like '%"+examRoom.getTitle()+"%'");
				sql.append(" and er.title like ?");
				params.add("%" + StringUtil.toLikeStr(examRoom.getTitle()).trim()
						+ "%");
			}
			if (examRoom.getCreater() != null
					&& !"".equals(examRoom.getCreater().getRealname())) {
				sql.append(" and eu.realname like ?");
				params.add("%"
						+ StringUtil.toLikeStr(examRoom.getCreater()
								.getRealname()).trim() + "%");
			}
			if (examRoom.getValid() != -1) {
				// sql.append(" and er.valid="+examRoom.getValid());
				sql.append(" and er.valid=?");
				params.add(examRoom.getValid());
			}
			if (examRoom.getSvalid() != -1) {
				// sql.append(" and er.svalid="+examRoom.getSvalid());
				sql.append(" and er.svalid=?");
				params.add(examRoom.getSvalid());
			}
			if (examRoom.getBegintime() != null) {
				// sql.append(" and er.begintime >= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(examRoom.getBegintime())+ "','yyyy-MM-dd
				// HH24:mi:ss')");
				sql.append(" and er.begintime >=?");
				params.add(examRoom.getBegintime());
			}
			if (examRoom.getEndtime() != null) {
				// sql.append(" and er.endtime <= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(examRoom.getEndtime())+ "','yyyy-MM-dd
				// HH24:mi:ss')");
				sql.append(" and er.endtime <=?");
				params.add(examRoom.getEndtime());
			}
			if (examRoom.getClassid() == -1) {
				sql.append(" and er.classid=-1 and er.courseid=-1 and er.cpid=0 and (er.isband=0 or er.isband is null) and er.iscommon=1");//考核考场
			} else if (examRoom.getClassid() == 0) {
				sql.append(" and er.classid=0 and er.courseid>0 and er.cpid>0");//章节考场
			} else if (examRoom.getClassid() == 1) {
				sql.append(" and er.classid>0 and er.courseid>0 and er.cpid=0");//单纯课程考场
		//	} else if(examRoom.getClassid() == -2){
		//		sql.append(" and er.classid=-2");
			} else if(examRoom.getClassid() == 2){
				sql.append(" and er.classid=-1 and er.courseid=-1 and er.cpid=0 and er.isnormal=1 and isband=1");//培训班考场
			}
		} else {
			sql.append(" and er.classid=-1");
		}
	}

	/**
	 * 获取考场集合信息
	 */
	public List<ExamRoom> listExamRoom(ElNode eroomLibTree, int sublibs,
			String sqlW, ExamRoom examRoom, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			// er.id, er.createrid, er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name
			// cname,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication
			// StringBuffer basesql = new StringBuffer(
			// "select er.id, er.createrid, er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid ,erlib.name
			// erbname,er.type,er.valid,c.name
			// cname,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication
			// from exam_room er left join eroom_lib erlib on
			// erlib.id=er.erlibid left join course c on c.id = er.courseid
			// where 1=1 "+sqlW);
			// this.checkParams(basesql, params, examRoom);
			// basesql.append(" and ");
			// //basesql.append(" group by er.id
			// ,er.createrid,er.title,er.begintime,er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication
			// order by er.begintime desc ");
			// ps=((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generatePSByTree("select
			// * from (select tt.*,rownum rn from(select t.* ,count(sr.userid)
			// srcnt from (", basesql.toString(), ") t left join study_room sr
			// on sr.roomid=t.id group by t.id, t.createrid, t.title,
			// t.begintime,
			// t.endtime,t.location,t.passgrade,t.erlibid,t.erbname,t.type,t.valid,t.cname,t.courseid,t.avalid,t.uvalid,t.classid,t.svalid,t.isApplication
			// )tt where rownum<=?) where rn>=?", "erlib", eroomLibTree, consub,
			// params, ct, pageNow, pageSize);
			StringBuffer basesql = new StringBuffer(
					"select * from (select t.* ,rownum rn from (select er.id, er.createrid, er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid ,erlib.name erbname,er.type,er.valid,c.name cname,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication,count(sr.userid),eu.realname,er.jingzhong,er.depname from exam_room er inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"eroom_lib", eroomLibTree, consub)
							+ ") erlib on erlib.id=er.erlibid left join course c on c.id = er.courseid left join eluser eu on er.createrid=eu.id left join study_room sr on sr.roomid=er.id "
							+ " left join eroom_registration erg on er.id=erg.eroomid where 1=1 and er.classid!=-3 "
							+ sqlW);
			this.checkParams(basesql, params, examRoom);
			basesql
					.append(" group by er.id ,er.createrid,er.title,er.begintime,er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication,eu.realname,er.jingzhong,er.depname order by er.begintime desc) t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(basesql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			ps.setInt(params.size() + 1, pageNow);
			ps.setInt(params.size() + 2, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(3));
				user = new ELUser(rs.getInt("createrid"), rs
						.getString("realname"));
				er.setCreater(user);
				// er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				// er.setBegintime(rs.getTimestamp(3));
				// er.setEndtime(rs.getTimestamp(4));
				// er.setLocation(rs.getString(5));
				// er.setPassgrade(rs.getFloat(6));
				// er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(8)));
				// er.setType(rs.getInt(9));
				// er.setValid(rs.getInt(10));
				// er.setEpsize(rs.getInt(11));
				// er.setCourse(new Course(rs.getInt(13), rs.getString(12)));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setLocation(rs.getString(6));
				er.setPassgrade(rs.getFloat(7));
				er.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt(11));
				// er.setEpsize(rs.getInt(12));
				er.setEpsize(1);
				er.setAvalid(rs.getInt(14));
				er.setUvalid(rs.getInt(15));
				er.setClassid(rs.getInt(16));
				er.setCourse(new Course(rs.getInt(14), rs.getString(12)));
				// er.setUsersize(this.getExamAllStudy(er.getId()));
				er.setUsersize(rs.getInt(19));
				er.setSvalid(rs.getInt(17));
				er.setIsApplication(rs.getInt(18));
				er.setPlanNumber(getEroomPlanNumber(rs.getInt(1)));
				er.setJingzhong(rs.getString(21));
				er.setDepName(rs.getString(22));
				ers.add(er);
				// er.getCreater().getUsername();
			}
		} catch (Exception e) {
			logger.error("获取考场集合信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}
	
	
	/**
	 * 获取考场集合信息
	 */
	public List<ExamRoom> listExamRoom2(ElNode eroomLibTree, int sublibs,
			String sqlW, ExamRoom examRoom, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			StringBuffer basesql = new StringBuffer(
					"select * from (select t.* ,rownum rn from (select er.id, er.createrid, er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid ,erlib.name erbname,er.type,er.valid,c.name cname,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication,count(sr.userid),eu.realname,er.jingzhong,er.depname from exam_room er inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"eroom_lib", eroomLibTree, consub)
							+ ") erlib on erlib.id=er.erlibid left join course c on c.id = er.courseid left join eluser eu on er.createrid=eu.id left join study_room sr on sr.roomid=er.id "
							+ " left join eroom_registration erg on er.id=erg.eroomid where 1=1 and er.classid=-3 "
							+ sqlW);
	//		this.checkParams(basesql, params, examRoom);
			basesql
					.append(" group by er.id ,er.createrid,er.title,er.begintime,er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication,eu.realname,er.jingzhong,er.depname order by er.begintime desc) t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(basesql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			ps.setInt(params.size() + 1, pageNow);
			ps.setInt(params.size() + 2, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(3));
				user = new ELUser(rs.getInt("createrid"), rs
						.getString("realname"));
				er.setCreater(user);
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setLocation(rs.getString(6));
				er.setPassgrade(rs.getFloat(7));
				er.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt(11));
				// er.setEpsize(rs.getInt(12));
				er.setEpsize(1);
				er.setAvalid(rs.getInt(14));
				er.setUvalid(rs.getInt(15));
				er.setClassid(rs.getInt(16));
				er.setCourse(new Course(rs.getInt(14), rs.getString(12)));
				// er.setUsersize(this.getExamAllStudy(er.getId()));
				er.setUsersize(rs.getInt(19));
				er.setSvalid(rs.getInt(17));
				er.setIsApplication(rs.getInt(18));
				er.setPlanNumber(getEroomPlanNumber(rs.getInt(1)));
				er.setJingzhong(rs.getString(21));
				er.setDepName(rs.getString(22));
				ers.add(er);
				// er.getCreater().getUsername();
			}
		} catch (Exception e) {
			logger.error("获取考场集合信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}
	

	/*
	 * 获取考场集合信息数量
	 */
	public int listExamRoomSize(ElNode eroomLibTree, int sublibs, String sqlW,
			ExamRoom examRoom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			// StringBuffer basesql = new StringBuffer(
			// "select er.id, er.createrid, er.title, er.begintime,
			// er.endtime,er.location,er.passgrade,er.erlibid ,erlib.name
			// erbname,er.type,er.valid,c.name
			// cname,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication
			// from exam_room er left join eroom_lib erlib on
			// erlib.id=er.erlibid left join course c on c.id = er.courseid
			// where 1=1 "+sqlW);
			// this.checkParams(basesql, params, examRoom);
			// basesql.append(" and ");
			// //basesql.append(" group by er.id
			// ,er.createrid,er.title,er.begintime,er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication
			// order by er.begintime desc ");
			// ps=((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generatePSByTree("select
			// count(*) from (select t.* ,count(sr.userid) srcnt from (",
			// basesql.toString(), ") t left join study_room sr on
			// sr.roomid=t.id group by t.id, t.createrid, t.title, t.begintime,
			// t.endtime,t.location,t.passgrade,t.erlibid,t.erbname,t.type,t.valid,t.cname,t.courseid,t.avalid,t.uvalid,t.classid,t.svalid,t.isApplication)",
			// "erlib", eroomLibTree, consub, params, ct);
			StringBuffer basesql = new StringBuffer(
					"select count(*) from exam_room er inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"eroom_lib", eroomLibTree, consub)
							+ ") erlib on erlib.id=er.erlibid left join course c on c.id = er.courseid left join eluser eu on er.createrid=eu.id "
							+ " left join eroom_registration erg on er.id=erg.eroomid where 1=1 and er.classid!=-3 "
							+ sqlW);
			this.checkParams(basesql, params, examRoom);
			// basesql.append(" group by er.id
			// ,er.createrid,er.title,er.begintime,er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid,er.avalid,er.uvalid,er.classid,er.svalid,er.isApplication
			// order by er.begintime desc");
			ps = ct.prepareStatement(basesql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考场集合信息数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	
	public int listExamRoomSize2(ElNode eroomLibTree, int sublibs, String sqlW,
			ExamRoom examRoom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			StringBuffer basesql = new StringBuffer(
					"select count(*) from exam_room er inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"eroom_lib", eroomLibTree, consub)
							+ ") erlib on erlib.id=er.erlibid left join course c on c.id = er.courseid left join eluser eu on er.createrid=eu.id "
							+ " left join eroom_registration erg on er.id=erg.eroomid where 1=1 and er.classid=-3"
							+ sqlW);
		//	this.checkParams(basesql, params, examRoom);
			ps = ct.prepareStatement(basesql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考场集合信息数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	

	public int listMyDepExamRoomSize(EroomLib eroomLibTree, int erlibid,
			int role, String sqlW) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from  (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid)"
							+ " from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id ,"
							+ " (select * from eroom_lib where  id in("
							+ ids
							+ ") ) ct where ct.id=er.erlibid"
							+ sqlW
							+ " group by er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid order by er.begintime desc)");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 考场初审list数量
	 * 
	 * @param eroomLibTree
	 * @param erlibid
	 * @param role
	 * @param sqlW
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	public int listMyDepExamRoomSize(EroomLib eroomLibTree, int erlibid,
			int role, String sqlW, ExamRoom examRoom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		String sql = "";
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			sql = "select count(*) from  (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid) from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id , (select * from eroom_lib where  id in("
					+ ids + ") ) ct where ct.id=er.erlibid " + sqlW;
			if (examRoom != null) {
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {
					sql += " and er.title like '%" + examRoom.getTitle() + "%'";
				}
				if (examRoom.getValid() != -1) {
					sql += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sql += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sql += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sql += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sql += " and er.classid>0";
				}
			} else {
				sql += " and er.classid=-1";
			}
			sql += " group by er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid order by er.begintime desc)";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ExamRoom> listMyDepExamRoomAvalid(EroomLib eroomLibTree,
			int erlibid, int role, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();

		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.* ,rownum rn from (select er.id, er.createrid, er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid) ,"
					+ "c.name cname,er.courseid,er.avalid from (exam_room er left join course c on c.id = er.courseid) "
					+ "left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id , "
					+ "(select * from eroom_lib where id in ("
					+ ids
					+ ") ) ct where ct.id=er.erlibid and er.avalid = 1 group by er.id ,er.createrid,er.title,"
					+ "er.begintime,er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,c.name,er.courseid"
					+ ",er.avalid order by er.begintime desc) t where rownum<=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(3));
				user = new ELUser();
				user.setId(rs.getInt("createrid"));
				UserDao ud = new UserDaoImpl();
				user = ud.getUserById(user.getId());
				er.setCreater(user);
				er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setLocation(rs.getString(6));
				er.setPassgrade(rs.getFloat(7));
				er.setEroomLib(new EroomLib(rs.getInt(8), rs.getString(9)));
				er.setType(rs.getInt(10));
				er.setValid(rs.getInt(11));
				er.setEpsize(rs.getInt(12));
				er.setAvalid(rs.getInt(15));
				er.setCourse(new Course(rs.getInt(14), rs.getString(13)));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int listMyDepExamRoomSizeAvalid(EroomLib eroomLibTree, int erlibid,
			int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		String x = Integer.toString(erlibid);
		String ids = createExamRoomLibId(eroomLibTree, erlibid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = erlibid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from  (select er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid,count(erep.epid)"
							+ " from exam_room er left join eroom_lib erlib on erlib.id=er.erlibid left join exam_reps erep on erep.roomid = er.id ,"
							+ " (select * from eroom_lib where  id in("
							+ ids
							+ ") ) ct where ct.id=er.erlibid and er.avalid =1"
							+ " group by er.id , er.title, er.begintime, er.endtime,er.location,er.passgrade,er.erlibid,erlib.name,er.type,er.valid order by er.begintime desc)");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String createExamRoomLibId(EroomLib ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = getEroomLibById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree.getChild() != null) {
				return createEroomLibId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 构建有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @return
	 */
	private String createEroomLibId(List<EroomLib> listType, int id) {
		String ids = id + "";
		for (EroomLib type : listType) {
			ids = ids + "," + createEroomLibId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * 
	 * @author jiahaijiang
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private EroomLib getEroomLibById(List<EroomLib> listType, int ctid) {
		EroomLib eroomLib = null;
		for (EroomLib type : listType) {
			if (type.getId() != ctid) {
				eroomLib = getEroomLibById(type.getChild(), ctid);
				if (eroomLib != null) {
					return eroomLib;
				}
			} else {
				eroomLib = type;
				return eroomLib;
			}
		}
		return eroomLib;
	}

	public List<ExamRoom> combinationSearchExamroom(ExamRoom examRoom,
			EroomLib eroomLibTree, int role, int Lid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			// String sqlstr="select * from (select t.*, rownum rn from (select
			// er.id , er.title, er.begintime,
			// er.endtime,er.location,er.courseid,c.name,er.iscommon from
			// (exam_room er left join course c on c.id = er.courseid) left join
			// eluser eu on er.createrid=eu.id where ";

			String x = Lid + "";
			String ids = createExamRoomLibId(eroomLibTree, Lid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = Lid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String sqlstr = "select * from (select t.*, rownum rn from (select er.id ,er.title, er.begintime, er.endtime,er.location,er.courseid,c.name,er.iscommon,er.valid,er.avalid,er.isApplication from ((exam_room er left join course c on c.id = er.courseid) left join eluser eu on er.createrid=eu.id) left join eroom_lib el on er.erlibid=el.id where ";
			if (examRoom == null) {
				sqlstr += "";
			} else {
				sqlstr += examRoom.getTitle() == null ? ""
						: " er.title like '%" + examRoom.getTitle() + "%'";
				sqlstr += examRoom.getValid() == -1 ? "" : " and er.valid = "
						+ examRoom.getValid();
				sqlstr += examRoom.getCreater() == null ? "" : examRoom
						.getCreater() == null ? "" : examRoom.getCreater()
						.getRealname().equals("") ? ""
						: " and eu.realname like '%"
								+ examRoom.getCreater().getRealname() + "%'";
				sqlstr += examRoom.getBegintime() == null
						|| examRoom.getEndtime() == null ? ""
						: " and er.begintime >= to_date('"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(examRoom.getBegintime())
								+ "','yyyy-MM-dd HH24:mi:ss')  "
								+ " and er.endtime <= to_date('"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(examRoom.getEndtime())
								+ "','yyyy-MM-dd HH24:mi:ss')  ";
				sqlstr += examRoom.getEroomLib() == null ? ""
						: " and el.id in (" + ids + ") ";
			}
			sqlstr += " order by er.begintime desc ) t where rownum <= ? ) where rn>=? ";
			ps = ct.prepareStatement(sqlstr);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				er.setIscommon(rs.getInt(8));
				er.setValid(rs.getInt(9));
				er.setAvalid(rs.getInt(10));
				er.setSupervisorrealname(getSupervisorrealname(rs.getInt(1)));
				er.setIsApplication(rs.getInt("isApplication"));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int combinationSearchExamroomCount(ExamRoom examRoom,
			EroomLib eroomLibTree, int role, int Lid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String x = Lid + "";
			String ids = createExamRoomLibId(eroomLibTree, Lid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = Lid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String sqlstr = "select count(*) from (select t.*, rownum rn from (select er.id ,er.title, er.begintime, er.endtime,er.location,er.courseid,c.name,er.iscommon from ((exam_room er left join course c on c.id = er.courseid) left join eluser eu on er.createrid=eu.id) left join eroom_lib el on er.erlibid=el.id where ";
			if (examRoom == null) {
				sqlstr += "";
			} else {
				sqlstr += examRoom.getTitle() == null ? ""
						: " er.title like '%" + examRoom.getTitle() + "%'";
				sqlstr += examRoom.getValid() == -1 ? "" : " and er.valid = "
						+ examRoom.getValid();
				sqlstr += examRoom.getCreater() == null ? "" : examRoom
						.getCreater() == null ? "" : examRoom.getCreater()
						.getRealname().equals("") ? ""
						: " and eu.realname like '%"
								+ examRoom.getCreater().getRealname() + "%'";
				sqlstr += examRoom.getBegintime() == null
						|| examRoom.getEndtime() == null ? ""
						: " and er.begintime >= to_date('"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(examRoom.getBegintime())
								+ "','yyyy-MM-dd HH24:mi:ss')  "
								+ " and er.endtime <= to_date('"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(examRoom.getEndtime())
								+ "','yyyy-MM-dd HH24:mi:ss')  ";
				sqlstr += examRoom.getEroomLib() == null ? ""
						: " and el.id in (" + ids + ") ";
			}
			sqlstr += " order by er.begintime desc ) t)";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 查询出从depid开始的有权的部门ID
	 * 
	 * @author HeweiCheng
	 * @param depTree
	 * @param depid
	 * @return
	 */
	private String createDepartmentId(Department depTree, int depid) {
		if (depTree != null) {
			if (depTree.getId() != depid) {
				depTree = getDepartmentById(depTree.getChild(), depid);
			}
			if (depTree.getChild() != null) {
				return createDepartmentId(depTree.getChild(), depTree.getId());
			}
			return String.valueOf(depTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 构建有权限的部门ID
	 * 
	 * @author Heweicheng
	 * @param ctypeTree
	 * @return
	 */
	private String createDepartmentId(List<Department> listType, int id) {
		String ids = id + "";
		for (Department type : listType) {
			ids = ids + "," + createDepartmentId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是根节点开始 要找出开始节点
	 * 
	 * @author Heweicheng
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private Department getDepartmentById(List<Department> listType, int depid) {
		Department dep = null;
		for (Department type : listType) {
			if (type.getId() != depid) {
				dep = getDepartmentById(type.getChild(), depid);
				if (dep != null) {
					return dep;
				}
			} else {
				dep = type;
				return dep;
			}
		}
		return dep;
	}

	public List<ExamRoom> combinationSearchExamroom(ExamRoom examRoom,
			int pageNow, int pageSize) throws ElException {
		// TODO Auto-generated method stub
		return null;
	}

	public int combinationSearchExamroomCount(ExamRoom examRoom)
			throws ElException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" delete from eroomlib_op_user where userid= ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserUseGrant(int userId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" delete from eroomlib_use_user where userid= ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ExamRoomAuditDescribes getExamRoomAuditDescribesByRoomid(int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoomAuditDescribes audit = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select erad.ID,erad.EXAMROOMID,erad.USERID,erad.SUBMITTIME,erad.FEEDBACKTIME,erad.TITLE,erad.STATUS,erad.CONTENT,erad.REPLYCONTENT from exam_room_audit_describes erad where erad.EXAMROOMID = ?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			audit = new ExamRoomAuditDescribes();
			if (rs.next()) {
				audit.setId(rs.getInt(1));
				ExamRoom er = new ExamRoom();
				er.setId(rs.getInt(2));
				ELUser u = new ELUser();
				u.setId(rs.getInt(3));
				audit.setExamroom(er);
				audit.setUser(u);
				audit.setSubimttime(rs.getTimestamp(4));
				audit.setFeedbacktime(rs.getTimestamp(5));
				audit.setTitle(rs.getString(6));
				audit.setStatus(rs.getInt(7));
				audit.setContent(rs.getString(8));
				audit.setReplycontent(rs.getString(9));
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return audit;
	}

	public void UExamRoomAuditContents(ExamRoomAuditDescribes erAuditdes)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room_audit_describes set REPLYCONTENT = ? , CONTENT = ? where id  = ?");
			ps.setString(1, erAuditdes.getReplycontent());
			ps.setString(2, erAuditdes.getContent());
			ps.setInt(3, erAuditdes.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void openExamRoomAudit(ExamRoomAuditDescribes examRoomAudit)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into exam_room_audit_describes(examroomid,userid,title,content,replycontent) values(?,?,?,?,?)");
			ps.setInt(1, examRoomAudit.getExamroom().getId());
			ps.setInt(2, examRoomAudit.getUser().getId());
			ps.setString(3, examRoomAudit.getTitle());
			ps.setString(4, examRoomAudit.getContent());
			ps.setString(5, examRoomAudit.getReplycontent());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setClassBindingCourse(int classId, int courseid, int eroomid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update class_course set binding = ? , eroomid = ? where classId  = ?  and courseid = ?");
			ps.setInt(1, 1);
			ps.setInt(2, eroomid);
			ps.setInt(3, classId);
			ps.setInt(4, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkuserClassBindingCourse(int classId, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from  class_course where classId  = ? and courseid = ? and binding = 1");
			ps.setInt(1, classId);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public ELUser getUserById(int id) throws ElException {
		ELUser elUser = new ELUser();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				// eu.id,eu.username,eu.password,eu.realname,eu.role,eu.depid,dep.name,eu.valid,er.name,
				// eu.sex
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setPassword(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setRole(new ElRole(rs.getInt(5), rs.getString(9)));
				elUser.setDepartment(new Department(rs.getInt(6), rs
						.getString(7)));
				elUser.setValid(rs.getBoolean(8));
				elUser.setSex(rs.getString(10));
				elUser.setXuhao(rs.getString(11));
				elUser.setDishi(rs.getInt(12));
				elUser.setDanwei(rs.getString(13));
				elUser.setShenfenzheng(rs.getString(14));
				elUser.setShengri(rs.getDate(15));
				elUser.setZhiji(rs.getInt(16));
				elUser.setZhiwu(rs.getInt(17));
				elUser.setJingzhong(rs.getInt(18));
				elUser.setGangwei(rs.getString(19));
				elUser.setJy(rs.getInt(20));
				elUser.setAge(rs.getInt(21));
			}
		} catch (Exception e) {
			logger.error("通过用户id查询用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}

	public ExamRoom getClassBindingCourseByRoomId(int classId, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom eroom = new ExamRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eroomid from class_course where classId  = ?  and courseid = ?");
			ps.setInt(1, classId);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				eroom.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("更新内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eroom;
	}

	public int getClassBindingCourseByRoomId(int classId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eroomid from class_course where classId  = ?  and binding = 1 and status=0");// 由于是必修
			// ，加个状态为0
			ps.setInt(1, classId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("更新内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取课程被绑定的考场
	 */
	public String getBindingCourseByRoomId(int classId, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer roomIds = new StringBuffer("");
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eroomid from class_course where classId  = ?  and binding = 1 and status=?");//
			ps.setInt(1, classId);
			ps.setInt(2, status);
			rs = ps.executeQuery();

			while (rs.next()) {
				roomIds.append(rs.getInt(1) + ",");
			}
			// 去掉最后一个逗号
			if (roomIds.length() > 1)
				roomIds.deleteCharAt(roomIds.length() - 1);
		} catch (Exception e) {
			logger.error("获取课程被绑定的考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return roomIds.toString();
	}

	public void setEroomBindingQuizinfo(int classId, int courseid, int sqiid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_course set sqiid=? where classId  = ?  and courseid = ?");
			ps.setInt(1, sqiid);
			ps.setInt(2, classId);
			ps.setInt(3, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int getExamRoomByQuizinfoId(int roomid, int classid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from study_quizinfo where classid = ? and  roomid = ? and epid = ?");
			ps.setInt(1, classid);
			ps.setInt(2, roomid);
			ps.setInt(3, epid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 更新考场信息的绑定班级
	 * 
	 * @param examRoomId
	 * @param classId
	 * @param courseid
	 * @throws ElException
	 */
	public void updateExamRoomInBandClassid(int examRoomId, int bandclassid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set isband=1,bandclassid=? where id=?");
			ps.setInt(1, bandclassid);
			ps.setInt(2, examRoomId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新考场信息的绑定班级出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 检测该考场的是否有绑定班级课程或单独课程(根据班级和课程检测)
	 * 
	 * @param examRoomId
	 * @return
	 * @throws ElException
	 */
	public boolean checkExamRoomIsBand(int courseId, int bandClassid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from exam_room where courseid = ? and bandclassid=? and isband=1");
			ps.setInt(1, courseId);
			ps.setInt(2, bandClassid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测该考场的是否有绑定班级课程或单独课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 取消考场的班级绑定
	 * 
	 * @param examRoomId
	 * @throws ElException
	 */
	public void cancelExamRoomBandClass(int courseId, int bandClassid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set isband=0,bandclassid=0 where courseid = ? and bandclassid=?");
			ps.setInt(1, courseId);
			ps.setInt(2, bandClassid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("取消考场的班级绑定出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 根据考场，班级查出学员
	 * 
	 * @param roomid
	 * @param courseid
	 * @param classid
	 * @throws ElException
	 */
	public void updateStudySqiidInit(int roomid, int courseid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,userid from study_quizinfo where roomid=? and classid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			int sqiid = 0;
			int userid = 0;
			while (rs.next()) {
				sqiid = rs.getInt("id");
				userid = rs.getInt("userid");
				// 更新该学员的sqiid
				updateStudySqiid(userid, courseid, classid, sqiid);
			}
		} catch (Exception e) {
			logger.error(" 根据考场，班级查出学员出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新学员课程的sqiid
	 * 
	 * @param roomid
	 * @param courseid
	 * @param classid
	 * @throws ElException
	 */
	public void updateStudySqiid(int userid, int courseid, int classid,
			int sqiid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_course set sqiid=? where courseid = ? and userid = ? and classid=?");
			ps.setInt(1, sqiid);
			ps.setInt(2, courseid);
			ps.setInt(3, userid);
			ps.setInt(4, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新学员课程的sqiid出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 备注getbyid
	 * 
	 * @param id
	 * @param type
	 * @throws ElException
	 */
	public List<CRE_note> getById(int id, String type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<CRE_note> nlist = new ArrayList<CRE_note>();
		try {
			if (type.equals("考场备注"))
				sql = "select cn.id,elclassid,courseid,eroomid,createid,operate,type,createtime,cn.phone,content,eu.username,eu.realname,rl.id rlid,rl.name rlname "
						+ " from CRE_note cn left join eluser eu on cn.createid=eu.id left join elrole rl on eu.role=rl.id where type ='考场备注' and courseid = 0 and elclassid = 0 and eroomid = "
						+ id;
			if (type.equals("课程备注"))
				sql = "select cn.id,elclassid,courseid,eroomid,createid,operate,type,createtime,cn.phone,content,eu.username,eu.realname,rl.id rlid,rl.name rlname "
						+ " from CRE_note cn left join eluser eu on cn.createid=eu.id left join elrole rl on eu.role=rl.id where type ='课程备注' and eroomid = 0 and elclassid = 0 and courseid = "
						+ id;
			if (type.equals("培训班备注"))
				sql = "select cn.id,elclassid,courseid,eroomid,createid,operate,type,createtime,cn.phone,content,eu.username,eu.realname,rl.id rlid,rl.name rlname "
						+ " from CRE_note cn left join eluser eu on cn.createid=eu.id left join elrole rl on eu.role=rl.id where type ='培训班备注' and eroomid = 0 and  courseid= 0 and elclassid = "
						+ id;
			sql += " order by createtime desc ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				CRE_note cre_note = new CRE_note();
				cre_note.setId(rs.getInt(1));
				cre_note.setElclass(new ElClass(rs.getInt("elclassid")));
				cre_note.setCourse(new Course(rs.getInt("courseid")));
				cre_note.setEroom(new ExamRoom(rs.getInt("eroomid")));
				// cre_note.setUser(getUserById(rs.getInt("createid")));
				cre_note.setUser(new ELUser(rs.getInt("createid"), rs
						.getString("username"), rs.getString("realname")));
				cre_note.getUser().setRole(
						new ElRole(rs.getInt("rlid"), rs.getString("rlname")));
				cre_note.setOperate(rs.getString("operate"));
				cre_note.setType(rs.getString("type"));
				cre_note.setCreatetime(rs.getTimestamp("createtime"));
				cre_note.setPhone(rs.getString("phone"));
				cre_note.setContent(rs.getString("content"));
				nlist.add(cre_note);
			}
		} catch (Exception e) {
			logger.error("获取备注类别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nlist;
	}

	/**
	 * 备注getbyid
	 * 
	 * @param elclassid
	 * @param courseid
	 * @param eroomid
	 * @param type
	 * @throws ElException
	 */
	public List<CRE_note> getById(int elclassid, int courseid, int eroomid,
			String type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<CRE_note> nlist = new ArrayList<CRE_note>();
		try {
			sql = "select id,elclassid,courseid,eroomid,createid,operate,type,createtime,phone,content "
					+ "from CRE_note where type = '结业考场备注' and elclassid = ? and  courseid= ?  and eroomid = ? order by createtime desc";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elclassid);
			ps.setInt(2, courseid);
			ps.setInt(3, eroomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				CRE_note cre_note = new CRE_note();
				cre_note.setId(rs.getInt(1));
				cre_note.setElclass(new ElClass(rs.getInt("elclassid")));
				cre_note.setCourse(new Course(rs.getInt("courseid")));
				cre_note.setEroom(new ExamRoom(rs.getInt("eroomid")));
				cre_note.setUser(getUserById(rs.getInt("createid")));
				cre_note.setOperate(rs.getString("operate"));
				cre_note.setType(rs.getString("type"));
				cre_note.setCreatetime(rs.getTimestamp("createtime"));
				cre_note.setPhone(rs.getString("phone"));
				cre_note.setContent(rs.getString("content"));
				nlist.add(cre_note);
			}
		} catch (Exception e) {
			logger.error("获取备注别列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nlist;
	}

	public void addCRE_note(CRE_note cre_note) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			sql = "insert into CRE_note(elclassid,courseid,eroomid,createid,type,createtime,phone,content,operate) values(?,?,?,?,?,?,?,?,?)";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, cre_note.getElclass().getId());
			ps.setInt(2, cre_note.getCourse().getId());
			ps.setInt(3, cre_note.getEroom().getId());
			ps.setInt(4, cre_note.getUser().getId());
			ps.setString(5, cre_note.getType());
			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			ps.setString(7, cre_note.getPhone());
			ps.setString(8, cre_note.getContent());
			ps.setString(9, cre_note.getOperate());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加备注失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 考场申请记录验证
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassRegistration(int eroomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from eroom_registration where eroomid = ?");
			ps.setInt(1, eroomid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 考场申请记录
	 * 
	 * @param erRegistration
	 * @throws ElException
	 */
	public void addEroomRegistration(EroomRegistration erRegistration)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into eroom_registration(eroomid,PlanRecruitStudents,RegistrationStartTime,RegistrationStopTime,StartAge ,StopAge,sex,jingzhong,dishi,zhiwu,zhiji,gangwei,treeType,examroomIds,elclassIds,classScreeningWay,eroomScreeningWay,isAudit,isselectep,examepids) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, erRegistration.getEroom().getId());
			ps.setString(2, erRegistration.getPlanRecruitStudents() + "");
			ps.setTimestamp(3, erRegistration.getRegistrationStartTime());
			ps.setTimestamp(4, erRegistration.getRegistrationStopTime());
			ps.setInt(5, erRegistration.getStartAge());
			ps.setInt(6, erRegistration.getStopAge());
			ps.setString(7, erRegistration.getSex());
			ps.setString(8, erRegistration.getJingzhong());
			ps.setString(9, erRegistration.getDishi());
			ps.setString(10, erRegistration.getZhiwu());
			ps.setString(11, erRegistration.getZhiji());
			ps.setString(12, erRegistration.getGangwei());
			ps.setString(13, erRegistration.getTreeType());
			// ps.setString(14, erRegistration.getExamRooms());
			// ps.setString(15, erRegistration.getElclasss());
			ps.setString(14, erRegistration.getErParasstr());
			// ps.setString(15, erRegistration.getElclasss());
			ps.setString(15, erRegistration.getClassParasstr());
			ps.setInt(16, erRegistration.getClassScreeningWay());
			ps.setInt(17, erRegistration.getEroomScreeningWay());
			ps.setInt(18, erRegistration.getIsAudit());
			ps.setInt(19, erRegistration.getIsselectep());
			ps.setString(20, erRegistration.getErepParasstr());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("考场申请条件增加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新考场申请条件
	 * 
	 * @param erRegistration
	 * @throws ElException
	 */
	public void alterEroomRegistration(EroomRegistration erRegistration)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update eroom_registration set PlanRecruitStudents=?,RegistrationStartTime=?,RegistrationStopTime=?,StartAge=?,StopAge=?,sex=?,jingzhong=?,dishi=?,zhiwu=?,zhiji=?,gangwei=?,treeType=?,examroomIds=?,elclassIds=?,classScreeningWay=?,eroomScreeningWay=?,isAudit=?,isselectep=?,examepids=? where eroomid=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, erRegistration.getPlanRecruitStudents() + "");
			ps.setTimestamp(2, erRegistration.getRegistrationStartTime());
			ps.setTimestamp(3, erRegistration.getRegistrationStopTime());
			ps.setInt(4, erRegistration.getStartAge());
			ps.setInt(5, erRegistration.getStopAge());
			ps.setString(6, erRegistration.getSex());
			ps.setString(7, erRegistration.getJingzhong());
			ps.setString(8, erRegistration.getDishi());
			ps.setString(9, erRegistration.getZhiwu());
			ps.setString(10, erRegistration.getZhiji());
			ps.setString(11, erRegistration.getGangwei());
			ps.setString(12, erRegistration.getTreeType());
			// ps.setString(13, erRegistration.getExamRooms());
			ps.setString(13, erRegistration.getErParasstr());
			// ps.setString(14, erRegistration.getElclasss());
			ps.setString(14, erRegistration.getClassParasstr());
			ps.setInt(15, erRegistration.getClassScreeningWay());
			ps.setInt(16, erRegistration.getEroomScreeningWay());
			ps.setInt(17, erRegistration.getIsAudit());
			ps.setInt(18, erRegistration.getIsselectep());
			ps.setString(19, erRegistration.getErepParasstr());
			ps.setInt(20, erRegistration.getEroom().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("考场申请条件修改失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 查询考场申请条件
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public EroomRegistration getEroomRegistration(int classid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		EroomRegistration elR = new EroomRegistration();
		try {
			String sql = "  select eroomid,PlanRecruitStudents,RegistrationStartTime,RegistrationStopTime,StartAge,StopAge,sex,jingzhong,dishi,zhiwu,zhiji,gangwei,treeType,examroomIds,elclassIds,classScreeningWay,eroomScreeningWay,isAudit,isselectep,examepids from eroom_registration where eroomid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				elR.setEroom(new ExamRoom(rs.getInt(1)));
				elR.setPlanRecruitStudents(rs.getInt(2));
				elR.setRegistrationStartTime(rs.getTimestamp(3));
				elR.setRegistrationStopTime(rs.getTimestamp(4));
				elR.setStartAge(rs.getInt(5));
				elR.setStopAge(rs.getInt(6));
				elR.setSex(rs.getString(7));
				elR.setJingzhong(rs.getString(8));
				elR.setDishi(rs.getString(9));
				elR.setZhiwu(rs.getString(10));
				elR.setZhiji(rs.getString(11));
				elR.setGangwei(rs.getString(12));
				elR.setTreeType(rs.getString(13));
				// 考场
				// elR.setExamRoomIds(rs.getString(14));
				elR.setErParasstr(rs.getString(14));
				// List<ExamRoom> ers = new ArrayList<ExamRoom>();
				// if (rs.getString(14) != null) {
				// List<String> listR = new ArrayList<String>(Arrays.asList(rs
				// .getString(14).split(",")));
				// for (int i = 0; i < listR.size(); i++) {
				// ers.add(new ExamRoom(Integer.parseInt(listR.get(i))));
				// }
				// }
				// elR.setExamRoom(ers);
				// 培训班
				// elR.setElclassIds(rs.getString(15));
				// List<ElClass> elc = new ArrayList<ElClass>();
				// if (rs.getString(15) != null) {
				// List<String> listC = new ArrayList<String>(Arrays.asList(rs
				// .getString(15).split(",")));
				// for (int i = 0; i < listC.size(); i++) {
				// elc.add(new ElClass(Integer.parseInt(listC.get(i))));
				// }
				// }
				// elR.setElclass(elc);
				elR.setClassParasstr(rs.getString(15));
				elR.setClassScreeningWay(rs.getInt(16));
				elR.setEroomScreeningWay(rs.getInt(17));
				elR.setIsAudit(rs.getInt(18));
				elR.setIsselectep(rs.getInt(19));
				elR.setErepParasstr(rs.getString(20));
				return elR;
			}
		} catch (Exception e) {
			logger.error("查询考场申请条件失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elR;
	}

	/**
	 * 获取可申请的考场详细信息（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public ElClass getApplyForeElclassById(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElClass elc = new ElClass();
		try {
			ct = DBConnection.getConnection();
			String sql = "select elc.id,elc.name,elc.description,elc.certificatename,elc.createtime,elc.starttime,elc.finishtime, "
					+ "elr.planRecruitStudents,elr.registrationStartTime,elr.registrationStopTime,elr.startAge,elr.stopAge,elr.sex, "
					+ "elr.jingzhong,elr.dishi,elr.zhiwu,elr.zhiji,elr.gangwei,elu.realname,elc.creater,clt.id,clt.name "
					+ "from elclass elc,ELCLASS_registration elr,elclasstype clt,eluser elu "
					+ "where elc.id = elr.classid and elc.cltype = clt.id and elc.creater = elu.id and elc.id =? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setDescription(rs.getString(3));
				elc.setCertificatename(rs.getString(4));
				elc.setCreatetime(rs.getTimestamp(5));
				elc.setStarttime(rs.getTimestamp(6));
				elc.setFinishtime(rs.getTimestamp(7));
				ELClassRegistration elR = new ELClassRegistration();
				elR.setPlanRecruitStudents(rs.getInt(8));
				elR.setRegistrationStartTime(rs.getTimestamp(9));
				elR.setRegistrationStopTime(rs.getTimestamp(10));
				elR.setStartAge(rs.getInt(11));
				elR.setStopAge(rs.getInt(12));
				elR.setSex(rs.getString(13));
				elR.setJingzhong(rs.getString(14));
				elR.setDishi(rs.getString(15));
				elR.setZhiwu(rs.getString(16));
				elR.setZhiji(rs.getString(17));
				elR.setGangwei(rs.getString(18));
				ELUser user = new ELUser(rs.getInt(20), rs.getString(19));
				ElClType elt = new ElClType(rs.getInt(21), rs.getString(22));
				elc.setElRegistration(elR);
				elc.setCreater(user);
				elc.setCltype(elt);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elc;
	}

	/**
	 * 获取可申请的考场列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getApplyForeEroom(EroomLib eroomLibTree, int erlibid,
			ExamRoom eroom, int role, String sqlw, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> erlist = new ArrayList<ExamRoom>();
		try {
			String x = Integer.toString(erlibid);
			String ids = createExamRoomLibId(eroomLibTree, erlibid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = erlibid == 1 ? ids
						.substring(x.length() + 1, ids.length()) : ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			if (erlibid == 1) {
				ids = "1," + ids;
			}
			// if(elclass!=null){
			// if(
			// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
			// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
			// }
			// if(elclass.getStatus()!=-1){//考场状态
			// sqls+=" and cl.status="+elclass.getStatus();
			// }
			// if(elclass.getBegintime()!=null){
			// sqls+=" and cl.STARTTIME >= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// if(elclass.getEndtime()!=null){
			// sqls+=" and cl.FINISHTIME <= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// }
			String conditions = "";
			if (eroom != null) {
				if (eroom.getTitle() != null && !eroom.getTitle().equals("")) {
					conditions = conditions + " and er.title like '%"
							+ eroom.getTitle() + "%' ";
				}
			}
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*, rownum rn from ( "
					+ "select  er.ID,er.TITLE,er.DESCRIPTION,er.BEGINTIME,er.ENDTIME,er.CREATERID,er.ERLIBID,er.iscommon,"
					+ "eu.realname,el.name,elr.planRecruitStudents,elr.registrationStartTime,elr.registrationStopTime,elr.startAge,elr.stopAge,elr.sex,"
					+ "elr.jingzhong,elr.dishi,elr.zhiwu,elr.zhiji,elr.gangwei,elr.treetype,"
					+ " elr.examroomIds,elr.elclassIds,elr.classScreeningWay,elr.eroomScreeningWay,er.mainimg,elr.isAudit,er.depname erdep,er.jingzhong erjz,examepids "
					+ " from exam_room er,Eroom_registration elr,eroom_lib el ,eluser eu "
					+ "where er.id = elr.eroomid and el.id= er.ERLIBID and er.erlibid = el.id  and er.valid in (5)  "
					+ "and er.ISAPPLICATION =1 and eu.id=createrid and el.id in("
					+ ids + ") " + conditions + sqlw +
					// "and elr.registrationStartTime < sysdate and
					// elr.registrationStopTime > sysdate" +
					")t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom();
				er.setId(rs.getInt(1));
				er.setTitle(rs.getString(2));
				er.setDescription(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setCreater(new ELUser(rs.getInt(6), rs.getString(9)));
				er.setIscommon(rs.getInt(8));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(10)));
				EroomRegistration elR = new EroomRegistration();
				elR.setPlanRecruitStudents(rs.getInt(11));
				elR.setRegistrationStartTime(rs.getTimestamp(12));
				elR.setRegistrationStopTime(rs.getTimestamp(13));
				elR.setStartAge(rs.getInt(14));
				elR.setStopAge(rs.getInt(15));
				elR.setSex(rs.getString(16));
				elR.setJingzhong(rs.getString(17));
				elR.setDishi(rs.getString(18));
				elR.setZhiwu(rs.getString(19));
				elR.setZhiji(rs.getString(20));
				elR.setGangwei(rs.getString(21));
				elR.setTreeType(rs.getString(22));
				// 考场
				// List<ExamRoom> ers = new ArrayList<ExamRoom>();
				// if (rs.getString(23) != null) {
				// List<String> listR = new ArrayList<String>(Arrays.asList(rs
				// .getString(23).split(",")));
				// for (int i = 0; i < listR.size(); i++) {
				// ers.add(new ExamRoom(Integer.parseInt(listR.get(i))));
				// }
				// }
				// elR.setExamRoom(ers);
				elR.setErParasstr(rs.getString(23));
				// 培训班
				// List<ElClass> elc = new ArrayList<ElClass>();
				// if (rs.getString(24) != null) {
				// List<String> listC = new ArrayList<String>(Arrays.asList(rs
				// .getString(24).split(",")));
				// for (int i = 0; i < listC.size(); i++) {
				// elc.add(new ElClass(Integer.parseInt(listC.get(i))));
				// }
				// }
				// elR.setElclass(elc);
				elR.setClassParasstr(rs.getString(24));
				elR.setClassScreeningWay(rs.getInt(25));
				elR.setEroomScreeningWay(rs.getInt(26));
				er.setErRegistration(elR);
				er.setMainimg(rs.getString(27));
				elR.setIsAudit(rs.getInt(28));
				er.setDepName(rs.getString(29));
				er.setJingzhong(rs.getString(30));
				er.setJingzhong(rs.getString(31));
				elR.setJoinNumber(((EroomDao) SpringContextUtil
						.getBean("eroomDao")).getJoinNumber(er.getId()));
				erlist.add(er);
			}
		} catch (Exception e) {
			logger.error("可申请考场列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return erlist;
	}

	/**
	 * 获取可申请的考场列表Size
	 * 
	 * @return
	 * @throws ElException
	 */
	public int getApplyForeEroomSize(EroomLib eroomLibTree, int erlibid,
			ExamRoom eroom, int role, String sqlw) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int Size = 0;
		try {
			String x = Integer.toString(erlibid);
			String ids = createExamRoomLibId(eroomLibTree, erlibid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = erlibid == 1 ? ids
						.substring(x.length() + 1, ids.length()) : ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			if (erlibid == 1) {
				ids = "1," + ids;
			}
			// if(elclass!=null){
			// if(
			// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
			// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
			// }
			// if(elclass.getStatus()!=-1){//考场状态
			// sqls+=" and cl.status="+elclass.getStatus();
			// }
			// if(elclass.getBegintime()!=null){
			// sqls+=" and cl.STARTTIME >= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// if(elclass.getEndtime()!=null){
			// sqls+=" and cl.FINISHTIME <= to_date('"+ new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
			// }
			// }
			//					
			String conditions = "";
			if (eroom != null) {
				if (eroom.getTitle() != null && !eroom.getTitle().equals("")) {
					conditions = conditions + " and er.title like '%"
							+ eroom.getTitle() + "%' ";
				}
			}
			ct = DBConnection.getConnection();
			String sql = "select  count(er.ID) from exam_room er,Eroom_registration elr,eroom_lib el ,eluser eu "
					+ "where er.id = elr.eroomid and el.id= er.ERLIBID and er.erlibid = el.id  and er.valid in (5)  "
					+ "and er.ISAPPLICATION =1 and eu.id=createrid and el.id in("
					+ ids + ") " + conditions + sqlw;
			// "and elr.registrationStartTime < sysdate and
			// elr.registrationStopTime > sysdate" +
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				Size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("可申考场班Size失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return Size;
	}

	/**
	 * 获取可申请的培训班详细信息（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getApplyForeEroomById(int eroomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom er = new ExamRoom();
		try {
			ct = DBConnection.getConnection();

			String sql = "select  er.ID,er.TITLE,er.DESCRIPTION,er.BEGINTIME,er.ENDTIME,er.CREATERID,er.ERLIBID,er.iscommon,"
					+ "eu.realname,elib.name,elr.planRecruitStudents,elr.registrationStartTime,elr.registrationStopTime,elr.startAge,elr.stopAge,elr.sex,"
					+ "elr.jingzhong,elr.dishi,elr.zhiwu,elr.zhiji,elr.gangwei,elr.treeType,elr.examroomIds,elr.elclassIds "
					+ ",elr.classScreeningWay,elr.eroomScreeningWay,er.mainimg,elr.isAudit,er.depname,er.jingzhong,elr.isselectep,elr.examepids,er.location "
					+ "from exam_room er,Eroom_registration elr,eroom_lib el ,eluser eu,eroom_lib elib "
					+ "where er.id = elr.eroomid and elib.id= er.ERLIBID and er.erlibid = el.id  and er.valid !=9  "
					+ "and er.ISAPPLICATION =1 and eu.id=createrid and er.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, eroomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				er.setId(rs.getInt(1));
				er.setTitle(rs.getString(2));
				er.setDescription(rs.getString(3));
				er.setBegintime(rs.getTimestamp(4));
				er.setEndtime(rs.getTimestamp(5));
				er.setCreater(new ELUser(rs.getInt(6), rs.getString(9)));
				er.setIscommon(rs.getInt(8));
				er.setEroomLib(new EroomLib(rs.getInt(7), rs.getString(10)));
				EroomRegistration elR = new EroomRegistration();
				elR.setPlanRecruitStudents(rs.getInt(11));
				elR.setRegistrationStartTime(rs.getTimestamp(12));
				elR.setRegistrationStopTime(rs.getTimestamp(13));
				elR.setStartAge(rs.getInt(14));
				elR.setStopAge(rs.getInt(15));
				elR.setSex(rs.getString(16));
				elR.setJingzhong(rs.getString(17));
				elR.setDishi(rs.getString(18));
				elR.setZhiwu(rs.getString(19));
				elR.setZhiji(rs.getString(20));
				elR.setGangwei(rs.getString(21));
				elR.setTreeType(rs.getString(22));
				// 考场
				// List<ExamRoom> ers = new ArrayList<ExamRoom>();
				// if (rs.getString(23) != null) {
				// List<String> listR = new ArrayList<String>(Arrays.asList(rs
				// .getString(23).split(",")));
				// for (int i = 0; i < listR.size(); i++) {
				// ers.add(new ExamRoom(Integer.parseInt(listR.get(i))));
				// }
				// }
				// elR.setExamRoom(ers);
				elR.setErParasstr(rs.getString(23));
				// 培训班
				// List<ElClass> elc = new ArrayList<ElClass>();
				// if (rs.getString(24) != null) {
				// List<String> listC = new ArrayList<String>(Arrays.asList(rs
				// .getString(24).split(",")));
				// for (int i = 0; i < listC.size(); i++) {
				// elc.add(new ElClass(Integer.parseInt(listC.get(i))));
				// }
				// }
				// elR.setElclass(elc);
				elR.setClassParasstr(rs.getString(24));
				elR.setClassScreeningWay(rs.getInt(25));
				elR.setEroomScreeningWay(rs.getInt(26));
				er.setErRegistration(elR);
				er.setMainimg(rs.getString(27));
				elR.setIsAudit(rs.getInt(28));// 是否需要审核
				er.setDepName(rs.getString(29));
				er.setJingzhong(rs.getString(30));
				elR.setIsselectep(rs.getInt(31));
				elR.setErepParasstr(rs.getString(32));
				er.setLocation(rs.getString(33));
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}

	/**
	 * 获取参加了考试的人数
	 * 
	 * @param eroomid
	 * @return
	 * @throws ElException
	 */
	public int getJoinNumber(int eroomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(ca.userid) from study_room ca where ca.roomid=?");
			ps.setInt(1, eroomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取参加了考试的员信息
	 * 
	 * @param eroomid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getEluserByExamRoomId(int eroomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> users = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ca.userid from study_room ca where ca.roomid=?");
			ps.setInt(1, eroomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1));
				users.add(user);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return users;
	}

	/**
	 * 校验userid(用户)在eroomids(多个考场)考场里是有否通过某个考场 当eroomids为一个考场时， 验证该用户是否通过该考场
	 * 
	 * @param eroomids
	 *            考场id字符串
	 * @param userid
	 *            用户id
	 * @return
	 * @throws ElException
	 */
	public boolean checkEroomIspassed(String eroomids, int userid,
			String sqlWhere) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean isOK = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select ispassed from study_room where roomid in("
							+ eroomids + ") and userid =? " + sqlWhere);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				isOK = true;
			} else {
				isOK = false;
			}
		} catch (Exception e) {
			logger.error("验证考场里" + userid + "用户(id)出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return isOK;
	}

	/**
	 * 校验userid(用户)在elclassids(多个培训班)培训班里是有否通过某个培训班 当eroomids为一个培训班时，
	 * 验证该用户是否通过该培训班
	 * 
	 * @param eroomids
	 *            考场id字符串
	 * @param userid
	 *            用户id
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassIspassed(String elclassids, int userid,
			String sqlWhere) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean isOK = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select certificateno from study_class where classid in ("
							+ elclassids + ") and userid = ?" + sqlWhere);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				isOK = true;
			}
		} catch (Exception e) {
			logger.error("验证培训班里" + userid + "用户(id)出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return isOK;
	}

	/**
	 * 更新考场库的父节点
	 * 
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateElibParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eroom_lib set parentid=? where parentid=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新考场库的父节点出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新考场的类别
	 * 
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateExamroomParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set erlibid=? where erlibid=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新考场的类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 根据类型删除考场
	 * 
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteExamroomByTypeid(Connection ct, int typeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Connection ct = null;
		try {
			// ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from exam_room where erlibid=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类型删除考场出错！", e);
			throw new ElException(e);
		}
	}

	/**
	 * 根据类型假删除考场
	 * 
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteExamroomByTypeidNot(Connection ct, int typeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Connection ct = null;
		try {
			// ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set valid=9 where erlibid=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类型假删除考场出错！", e);
			throw new ElException(e);
		}
	}

	/**
	 * 删除考场库
	 * 
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deleteElibAndSub(int id) throws ElException {
		// 查出该类别的左右id，然后查出所有子类别，然后循环根据id删除子类别，删除所有类别下的课程最后删除该类别
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid = this.getLidRid(ct, id, "eroom_lib");
			List<Integer> typelist = this.getTypeByLidRid(ct,
					typelrid.getLid(), typelrid.getRid(), "eroom_lib");
			for (int i = 0; i < typelist.size(); i++) {
				// 根据id删除类别以及类别下的资源(先删资源)
				this.deleteExamroomByTypeid(ct, typelist.get(i));
				this.deleteeroomLib(ct, typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("删除试卷库以及下级试卷库和试卷失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 假删除考场库
	 * 
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	public void deleteElibAndSubNot(int id) throws ElException {
		// 查出该类别的左右id，然后查出所有子类别，然后循环根据id更新子类别状态，更新所有类别下的考场最后更新除该类别
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid = this.getLidRid(ct, id, "eroom_lib");
			List<Integer> typelist = this.getTypeByLidRid(ct,
					typelrid.getLid(), typelrid.getRid(), "eroom_lib");
			for (int i = 0; i < typelist.size(); i++) {
				// 根据id更新类别以及类别下的资源(先更新资源)
				this.deleteExamroomByTypeidNot(ct, typelist.get(i));
				this.deleteeroomLibNot(typelist.get(i));

			}
		} catch (Exception e) {
			logger.error("假删除考场库失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取树的左右id
	 * 
	 * @param typeId
	 * @param tabName
	 * @return
	 * @throws ElException
	 */
	public Typelrid getLidRid(Connection ct, int typeId, String tabName)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Connection ct = null;
		Typelrid type = null;
		try {
			// ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select lid,rid from " + tabName
					+ " where id=?");
			ps.setInt(1, typeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				type = new Typelrid(rs.getInt(1), rs.getInt(2));
			}
		} catch (Exception e) {
			logger.error("获取树的左右id失败！", e);
			throw new ElException(e);
		}
		return type;
	}

	/**
	 * 根据左右id获取树的id集合
	 * 
	 * @param lid
	 * @param rid
	 * @param tabName
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getTypeByLidRid(Connection ct, int lid, int rid,
			String tabName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Connection ct = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			// ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from " + tabName
					+ " where lid>=? and rid<=? ");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("获取树的左右id失败！", e);
			throw new ElException(e);
		}
		return list;
	}

	/**
	 * 获取考场计划招收人数
	 * 
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int getEroomPlanNumber(int erid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select planrecruitstudents from eroom_registration where eroomid = ?");
			ps.setInt(1, erid);
			rs = ps.executeQuery();
			if (rs.next())
				number = rs.getInt(1);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取与当前考场考试时间重叠的考场
	 * 
	 * @param elclass
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getExamRoomTimeoverList(ExamRoom examRoom,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ExamRoom> list = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*, rownum rn from (select er.id erid,er.title ertitle,er.begintime,er.endtime,er.valid,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname from exam_room er left join eluser eu on er.createrid=eu.id left join department dep on eu.depid=dep.id where ?<er.endtime and ?>er.begintime and er.valid=5) t where rownum <= ?) where rn >= ? ");
			ps.setTimestamp(1, examRoom.getBegintime());
			ps.setTimestamp(2, examRoom.getEndtime());
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt("erid"), rs
						.getString("ertitle"));
				er.setBegintime(rs.getTimestamp("begintime"));
				er.setEndtime(rs.getTimestamp("endtime"));
				er.setValid(rs.getInt("valid"));
				user = new ELUser(rs.getInt("euid"), rs.getString("realname"));
				user.setUsername(rs.getString("username"));
				user.setDepartment(new Department(rs.getInt("depid"), rs
						.getString("depname")));
				er.setCreater(user);
				er.setUsersize(this.getExamAllStudy(er.getId()));
				list.add(er);
			}
		} catch (Exception e) {
			logger.error("获取与当前考场考试时间重叠的考场失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 获取与当前考场考试时间重叠的考场数量
	 * 
	 * @param elclass
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getExamRoomTimeoverListCount(ExamRoom examRoom)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from exam_room er left join eluser eu on er.createrid=eu.id left join department dep on eu.depid=dep.id where ?<er.endtime and ?>er.begintime and er.valid=5 ");
			ps.setTimestamp(1, examRoom.getBegintime());
			ps.setTimestamp(2, examRoom.getEndtime());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取与当前考场考试时间重叠的考场数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取考场信息以及创建者信息
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ExamRoom getExamRoomByid2(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ExamRoom er = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select er.id erid,er.title ertitle,er.begintime,er.endtime,er.valid,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname from exam_room er left join eluser eu on er.createrid=eu.id left join department dep on eu.depid=dep.id where er.id=? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				er = new ExamRoom(rs.getInt("erid"), rs.getString("ertitle"));
				er.setBegintime(rs.getTimestamp("begintime"));
				er.setEndtime(rs.getTimestamp("endtime"));
				er.setValid(rs.getInt("valid"));
				ELUser user = new ELUser(rs.getInt("euid"), rs
						.getString("realname"));
				user.setUsername(rs.getString("username"));
				user.setDepartment(new Department(rs.getInt("depid"), rs
						.getString("depname")));
				er.setCreater(user);
				er.setUsersize(this.getExamAllStudy(er.getId()));
			}
		} catch (Exception e) {
			logger.error("获取考场信息以及创建者信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}

	/**
	 * 根据学员培训班相关信息搜索学员(分页)
	 * 
	 * @param oldClassParas
	 * @param roomid
	 * @param epid
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnClassSeach(List<ClassPara> oldClassParas,
			int roomid, int epid, ELUser elUser, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> elUsers = new ArrayList<ELUser>();
		StringBuffer sql = new StringBuffer();
		List<ClassPara> classParas = new ArrayList<ClassPara>();
		for (int i = 0; i < oldClassParas.size(); i++) {
			if (oldClassParas.get(i) != null) {
				classParas.add(oldClassParas.get(i));
			}
		}
		try {
			ct = DBConnection.getConnection();
			String tempTerm = "";// 存储上一次的条件
			int tempi = 0;// 记录索引，用户表的别名
			for (int i = 0; i < classParas.size(); i++) {
				classParas.get(i).setElUser(elUser);
				if (i == 0) {// 开始
					if (i == classParas.size() - 1) {// 只有1个考场条件
						sql.append(classParas.get(i).getTermSql());
						break;
					}
					sql.append(classParas.get(i).getTermSql());
					if (classParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				} else if (i == classParas.size() - 1) { // 最后
					if (tempTerm.equals("or")) {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
				} else {// 中间
					if (tempTerm.equals("or")) {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
					if (classParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				}
				tempTerm = classParas.get(i).getLinkTerm();// 存储上一次的条件
			}
			// 学员查出来 然后再连当前学员考场表
			sql.insert(0, "select tt.*,sr.joinway from (");
			sql
					.append(") tt left join (select * from study_room where roomid=?) sr on tt.euid=sr.userid");
			// 分页
			sql.insert(0, "select * from (select t.* ,rownum rn from ( ");
			sql.append(")t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, roomid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				user = new ELUser(rs.getInt(1), rs.getString(2), rs
						.getString(3));
				user
						.setDepartment(new Department(rs.getInt(4), rs
								.getString(5)));
				user.setRole(new ElRole(rs.getInt(6), rs.getString(7)));
				user.setSex(rs.getString(8));
				user.setJingzhong(rs.getInt(9));
				user.setShengri(rs.getDate(10));
				user.setJoinwayInt(rs.getString("joinway") == null ? 2 : rs
						.getInt("joinway"));
				// 查询是否分配到该考场
				if (this.getStudyExampaperIsAssign(roomid, epid, user.getId())) {
					user.setIsAssign("已分配");
				} else {
					user.setIsAssign("未分配");
				}
				elUsers.add(user);
			}
		} catch (Exception e) {
			logger.error("根据学员考场相关信息搜索学员(分页)失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUsers;
	}

	/**
	 * 根据学员培训班相关信息搜索学员数量
	 * 
	 * @param oldErParas
	 * @param queryManner
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int listUserOnClassSeachSize(List<ClassPara> oldClassParas,
			ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		List<ClassPara> classParas = new ArrayList<ClassPara>();
		for (int i = 0; i < oldClassParas.size(); i++) {
			if (oldClassParas.get(i) != null) {
				classParas.add(oldClassParas.get(i));
			}
		}
		try {
			ct = DBConnection.getConnection();
			String tempTerm = "";
			int tempi = 0;
			for (int i = 0; i < classParas.size(); i++) {
				classParas.get(i).setElUser(elUser);
				if (i == 0) {// 开始
					if (i == classParas.size() - 1) {// 只有1个考场条件
						sql.append(classParas.get(i).getTermSql());
						break;
					}
					sql.append(classParas.get(i).getTermSql());
					if (classParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				} else if (i == classParas.size() - 1) { // 最后
					if (tempTerm.equals("or")) {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
				} else {// 中间
					if (tempTerm.equals("or")) {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
					if (classParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				}
				tempTerm = classParas.get(i).getLinkTerm();// 存储上一次的条件
			}
			// 查数量
			sql.insert(0, "select count(*) from ( ");
			sql.append(" )");
			ps = ct.prepareStatement(sql.toString());
			System.out.println("sql:" + sql.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据学员考场相关信息搜索学员数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 根据学员培训班相关信息搜索学员
	 * 
	 * @param erParas
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnClassSeach(List<ClassPara> oldClassParas,
			ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> elUsers = new ArrayList<ELUser>();
		StringBuffer sql = new StringBuffer();
		List<ClassPara> classParas = new ArrayList<ClassPara>();
		for (int i = 0; i < oldClassParas.size(); i++) {
			if (oldClassParas.get(i) != null) {
				classParas.add(oldClassParas.get(i));
			}
		}
		try {
			ct = DBConnection.getConnection();
			String tempTerm = "";
			int tempi = 0;
			for (int i = 0; i < classParas.size(); i++) {
				classParas.get(i).setElUser(elUser);
				if (i == 0) {// 开始
					if (i == classParas.size() - 1) {// 只有1个考场条件
						sql.append(classParas.get(i).getTermSql());
						break;
					}
					sql.append(classParas.get(i).getTermSql());
					if (classParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				} else if (i == classParas.size() - 1) { // 最后
					if (tempTerm.equals("or")) {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
				} else {// 中间
					if (tempTerm.equals("or")) {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(classParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
					if (classParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				}
				tempTerm = classParas.get(i).getLinkTerm();// 存储上一次的条件
			}
			ps = ct.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				user = new ELUser(rs.getInt(1));
				elUsers.add(user);
			}
		} catch (Exception e) {
			logger.error("根据学员考场相关信息搜索学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUsers;
	}

	/**
	 * 根据学员考场相关信息搜索学员
	 * 
	 * @param erParas
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnEroomSeach(List<ErPara> oldErParas,
			int queryManner, ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> elUsers = new ArrayList<ELUser>();
		StringBuffer sql = new StringBuffer();
		List<ErPara> erParas = new ArrayList<ErPara>();
		for (int i = 0; i < oldErParas.size(); i++) {
			if (oldErParas.get(i) != null) {
				oldErParas.get(i).setQueryManner(queryManner);
				erParas.add(oldErParas.get(i));
			}
		}
		try {
			ct = DBConnection.getConnection();
			// String str="(select euu.* from (select * from study_room where
			// roomid="+erParas.get(i).getExamRoom().getId()+" and
			// ispassed="+erParas.get(i).getIsPassed()+" and
			// eroomcount"+erParas.get(i).getExamCountTerm()+erParas.get(i).getExamCount()+"
			// and
			// avgscore"+erParas.get(i).getAvgScoreTerm()+erParas.get(i).getAvgScore()+"
			// and
			// maxscore"+erParas.get(i).getMaxScoreTerm()+erParas.get(i).getMaxScore()+")
			// sr left join (select eu.id euid,eu.username,eu.realname,dep.id
			// depid,dep.name depname,el.id elid,el.name
			// elname,eu.sex,eu.jingzhong,eu.shengri from eluser eu left join
			// department dep on eu.depid=dep.id left join elrole el on
			// eu.role=el.id) euu on sr.userid=euu.euid)";
			String tempTerm = "";
			int tempi = 0;
			for (int i = 0; i < erParas.size(); i++) {
				erParas.get(i).setElUser(elUser);
				if (i == 0) {// 开始
					if (i == erParas.size() - 1) {// 只有1个考场条件
						// sql.append(" (select euu.* from (select * from
						// study_room where
						// roomid="+erParas.get(i).getExamRoom().getId()+tempStr+"
						// and
						// eroomcount"+erParas.get(i).getExamCountTerm()+erParas.get(i).getExamCount()+"
						// and
						// avgscore"+erParas.get(i).getAvgScoreTerm()+erParas.get(i).getAvgScore()+"
						// and
						// maxscore"+erParas.get(i).getMaxScoreTerm()+erParas.get(i).getMaxScore()+")
						// sr left join (select eu.id
						// euid,eu.username,eu.realname,dep.id depid,dep.name
						// depname,el.id elid,el.name
						// elname,eu.sex,eu.jingzhong,eu.shengri from eluser eu
						// left join department dep on eu.depid=dep.id left join
						// elrole el on eu.role=el.id) euu on
						// sr.userid=euu.euid) ");
						sql.append(erParas.get(i).getTermSimpleSql());
						break;
					}
					sql.append(erParas.get(i).getTermSimpleSql());
					if (erParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				} else if (i == erParas.size() - 1) { // 最后
					if (tempTerm.equals("or")) {
						sql.append(erParas.get(i).getTermSimpleSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(erParas.get(i).getTermSimpleSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
				} else {// 中间
					if (tempTerm.equals("or")) {
						sql.append(erParas.get(i).getTermSimpleSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(erParas.get(i).getTermSimpleSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
					if (erParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				}
				tempTerm = erParas.get(i).getLinkTerm();// 存储上一次的条件
			}
			ps = ct.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				user = new ELUser(rs.getInt(1));
				elUsers.add(user);
			}
		} catch (Exception e) {
			logger.error("根据学员考场相关信息搜索学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUsers;
	}

	/**
	 * 根据学员考场相关信息搜索学员(分页)
	 * 
	 * @param oldErParas
	 * @param roomid
	 * @param epid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnEroomSeach(List<ErPara> oldErParas,
			int roomid, int epid, int queryManner, ELUser elUser, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> elUsers = new ArrayList<ELUser>();
		StringBuffer sql = new StringBuffer();
		List<ErPara> erParas = new ArrayList<ErPara>();
		for (int i = 0; i < oldErParas.size(); i++) {
			if (oldErParas.get(i) != null) {
				oldErParas.get(i).setQueryManner(queryManner);
				erParas.add(oldErParas.get(i));
			}
		}
		try {
			ct = DBConnection.getConnection();
			String tempTerm = "";// 存储上一次的条件
			int tempi = 0;// 记录索引，用户表的别名
			for (int i = 0; i < erParas.size(); i++) {
				erParas.get(i).setElUser(elUser);
				if (i == 0) {// 开始
					if (i == erParas.size() - 1) {// 只有1个考场条件
						// sql.append(" (select euu.* from (select * from
						// study_room where
						// roomid="+erParas.get(i).getExamRoom().getId()+tempStr+"
						// and
						// eroomcount"+erParas.get(i).getExamCountTerm()+erParas.get(i).getExamCount()+"
						// and
						// avgscore"+erParas.get(i).getAvgScoreTerm()+erParas.get(i).getAvgScore()+"
						// and
						// maxscore"+erParas.get(i).getMaxScoreTerm()+erParas.get(i).getMaxScore()+")
						// sr left join (select eu.id
						// euid,eu.username,eu.realname,dep.id depid,dep.name
						// depname,el.id elid,el.name
						// elname,eu.sex,eu.jingzhong,eu.shengri from eluser eu
						// left join department dep on eu.depid=dep.id left join
						// elrole el on eu.role=el.id) euu on
						// sr.userid=euu.euid) ");
						sql.append(erParas.get(i).getTermSql());
						break;
					}
					sql.append(erParas.get(i).getTermSql());
					if (erParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				} else if (i == erParas.size() - 1) { // 最后
					if (tempTerm.equals("or")) {
						sql.append(erParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(erParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
				} else {// 中间
					if (tempTerm.equals("or")) {
						sql.append(erParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(erParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
					if (erParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				}
				tempTerm = erParas.get(i).getLinkTerm();// 存储上一次的条件
			}
			// 学员查出来 然后再连当前学员考场表
			sql.insert(0, "select tt.*,sr.joinway from (");
			sql
					.append(") tt left join (select * from study_room where roomid=?) sr on tt.euid=sr.userid");
			// 分页
			sql.insert(0, "select * from (select t.* ,rownum rn from ( ");
			sql.append(")t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, roomid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				user = new ELUser(rs.getInt(1), rs.getString(2), rs
						.getString(3));
				user
						.setDepartment(new Department(rs.getInt(4), rs
								.getString(5)));
				user.setRole(new ElRole(rs.getInt(6), rs.getString(7)));
				user.setSex(rs.getString(8));
				user.setJingzhong(rs.getInt(9));
				user.setShengri(rs.getDate(10));
				user.setJoinwayInt(rs.getString("joinway") == null ? 2 : rs
						.getInt("joinway"));
				// 查询是否分配到该考场
				if (this.getStudyExampaperIsAssign(roomid, epid, user.getId())) {
					user.setIsAssign("已分配");
				} else {
					user.setIsAssign("未分配");
				}
				elUsers.add(user);
			}
		} catch (Exception e) {
			logger.error("根据学员考场相关信息搜索学员(分页)失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUsers;
	}

	/**
	 * 根据学员考场相关信息搜索学员数量
	 * 
	 * @param oldErParas
	 * @return
	 * @throws ElException
	 */
	public int listUserOnEroomSeachSize(List<ErPara> oldErParas,
			int queryManner, ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		List<ErPara> erParas = new ArrayList<ErPara>();
		for (int i = 0; i < oldErParas.size(); i++) {
			if (oldErParas.get(i) != null) {
				oldErParas.get(i).setQueryManner(queryManner);
				erParas.add(oldErParas.get(i));
			}
		}
		try {
			ct = DBConnection.getConnection();
			String tempTerm = "";
			int tempi = 0;
			for (int i = 0; i < erParas.size(); i++) {
				erParas.get(i).setElUser(elUser);
				if (i == 0) {// 开始
					if (i == erParas.size() - 1) {// 只有1个考场条件
						sql.append(erParas.get(i).getTermSimpleSql());
						break;
					}
					sql.append(erParas.get(i).getTermSimpleSql());
					if (erParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				} else if (i == erParas.size() - 1) { // 最后
					if (tempTerm.equals("or")) {
						sql.append(erParas.get(i).getTermSimpleSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(erParas.get(i).getTermSimpleSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
				} else {// 中间
					if (tempTerm.equals("or")) {
						sql.append(erParas.get(i).getTermSimpleSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(erParas.get(i).getTermSimpleSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
					if (erParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				}
				tempTerm = erParas.get(i).getLinkTerm();// 存储上一次的条件
			}
			// 查数量
			sql.insert(0, "select count(*) from ( ");
			sql.append(" )");
			ps = ct.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据学员考场相关信息搜索学员数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取学员是否分配了该考场试卷
	 * 
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public boolean getStudyExampaperIsAssign(int roomid, int epid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epid from study_exampaper where roomid=? and epid=? and userid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("获取学员是否分配了该考场试卷失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 根据学员考场相关信息搜索学员(分页，适应培训班分配人员)
	 * 
	 * @param oldErParas
	 * @param roomid
	 * @param epid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listUserOnEroomSeach(List<ErPara> oldErParas,
			int classid, int queryManner, ELUser elUser, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> elUsers = new ArrayList<ELUser>();
		StringBuffer sql = new StringBuffer();
		List<ErPara> erParas = new ArrayList<ErPara>();
		for (int i = 0; i < oldErParas.size(); i++) {
			if (oldErParas.get(i) != null) {
				oldErParas.get(i).setQueryManner(queryManner);
				erParas.add(oldErParas.get(i));
			}
		}
		try {
			ct = DBConnection.getConnection();
			String tempTerm = "";// 存储上一次的条件
			int tempi = 0;// 记录索引，用户表的别名
			for (int i = 0; i < erParas.size(); i++) {
				erParas.get(i).setElUser(elUser);
				if (i == 0) {// 开始
					if (i == erParas.size() - 1) {// 只有1个考场条件
						// sql.append(" (select euu.* from (select * from
						// study_room where
						// roomid="+erParas.get(i).getExamRoom().getId()+tempStr+"
						// and
						// eroomcount"+erParas.get(i).getExamCountTerm()+erParas.get(i).getExamCount()+"
						// and
						// avgscore"+erParas.get(i).getAvgScoreTerm()+erParas.get(i).getAvgScore()+"
						// and
						// maxscore"+erParas.get(i).getMaxScoreTerm()+erParas.get(i).getMaxScore()+")
						// sr left join (select eu.id
						// euid,eu.username,eu.realname,dep.id depid,dep.name
						// depname,el.id elid,el.name
						// elname,eu.sex,eu.jingzhong,eu.shengri from eluser eu
						// left join department dep on eu.depid=dep.id left join
						// elrole el on eu.role=el.id) euu on
						// sr.userid=euu.euid) ");
						sql.append(erParas.get(i).getTermSql());
						break;
					}
					sql.append(erParas.get(i).getTermSql());
					if (erParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				} else if (i == erParas.size() - 1) { // 最后
					if (tempTerm.equals("or")) {
						sql.append(erParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(erParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
				} else {// 中间
					if (tempTerm.equals("or")) {
						sql.append(erParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(")");
					} else {
						sql.append(erParas.get(i).getTermSql());
						sql.insert(0, "(");
						sql.append(" t" + i + " on t" + tempi + ".euid=t" + i
								+ ".euid )");
					}
					if (erParas.get(i).getLinkTerm().equals("or")) {
						sql.append(" union ");
					} else {
						sql.insert(0, "select t" + i + ".* from( ");
						sql.append(") t" + i + " inner join ");
						tempi = i;
					}
				}
				tempTerm = erParas.get(i).getLinkTerm();// 存储上一次的条件
			}
			// 学员查出来 然后再连当前学员考场表
			sql.insert(0, "select tt.*,sc.joinway from (");
			sql
					.append(") tt left join (select * from study_class where classid=?) sc on tt.euid=sc.userid");
			// 分页
			sql.insert(0, "select * from (select t.* ,rownum rn from ( ");
			sql.append(")t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(sql.toString());
			ps.setInt(1, classid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				user = new ELUser(rs.getInt(1), rs.getString(2), rs
						.getString(3));
				user
						.setDepartment(new Department(rs.getInt(4), rs
								.getString(5)));
				user.setRole(new ElRole(rs.getInt(6), rs.getString(7)));
				user.setSex(rs.getString(8));
				user.setJingzhong(rs.getInt(9));
				user.setShengri(rs.getDate(10));
				user.setJoinwayInt(rs.getString("joinway") == null ? 2 : rs
						.getInt("joinway"));
				// 查询是否分配到该考场
				if (user.getJoinwayInt() != 2) {
					user.setIsAssign("已分配");
				} else {
					user.setIsAssign("未分配");
				}
				elUsers.add(user);
			}
		} catch (Exception e) {
			logger.error("根据学员考场相关信息搜索学员(分页)失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUsers;
	}

	public ExamRoom getExamRoom(int courseid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ExamRoom er = null;
		try {
			String sql = "select er.id,er.title,er.begintime,er.endtime,er.location,er.courseid,er.description,"
					+ " er.passgrade,er.score,er.erlibid,er.type,er.valid,er.uvalid,er.avalid,er.isMacBand,er.isIpLimit,"
					+ " er.ipStart,er.ipEnd,er.classid,er.isApplication,er.examcount,er.markingManner,er.mainimg,er.passmanner,er.iscommon"
					+ " from exam_room er where er.courseid = ? and er.bandclassid = ?";
			ct = DBConnection.getConnection();
			// rs = ps.executeQuery();
			// ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				er.setDescription(rs.getString(8));
				er.setPassgrade(rs.getFloat(9));
				er.setScore(rs.getInt(10));
				er.setEroomLib(new EroomLib(rs.getInt(11), rs.getString(14)));
				er.setType(rs.getInt(12));
				er.setValid(rs.getInt(13));
				er.setUvalid(rs.getInt(15));
				er.setAvalid(rs.getInt(16));
				er.setIsMacBand(rs.getInt("isMacBand"));
				er.setIsIpLimit(rs.getInt("isIpLimit"));
				er.setIpStart(rs.getString("ipStart"));
				er.setIpEnd(rs.getString("ipEnd"));
				er.setClassid(rs.getInt("classid"));
				er.setIsApplication(rs.getInt("isApplication"));
				er.setExamcount(rs.getInt("examcount"));
				er.setMarkingManner(rs.getInt("markingManner"));
				er.setMainimg(rs.getString("mainimg"));
				er.setPassmanner(rs.getInt("passmanner"));
				er.setIscommon(rs.getInt("iscommon"));
			}
		} catch (Exception e) {
			logger.error("获取培训班课程考场列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}

	public int copyEroom(int id) throws ElException {
		// 获取考场基本信息
		// ExamRoom examRoom = getExamRoomByid(id);
		// examRoom.setTitle(examRoom.getTitle() + "_副本");
		// //考场id获取申请考场
		// EroomRegistration erRegistration = getEroomRegistration(id);
		// //添加考场
		// addExamRoom(examRoom);
		// //获取试卷列表
		// List<ExamPaper> eList = getEroomeps(id);
		// for(int i = 0; i < eList.size(); i++){
		// ExamPaper ep = eList.get(i);
		// //新的考场
		// addEroomeps(examRoom.getId(), ep.getId(), 0,
		// ep.getPractimes(), ep.getPracscore(), ep
		// .getPassgrade(), ep.getStuview(), ep
		// .getQuizlook(), ep.getScorelook(), ep
		// .getQuizcount(), ep.getPassmanner());
		// }
		// //设置监考人员
		// examRoom.setInvigilators(getEroomUsers("rinvigilators",id));
		// //设置阅卷人员
		// examRoom.setAppraises(getEroomUsers("rappraises", id));
		// //设置复核人员
		// examRoom.setValids(getEroomUsers("valids", id));
		//		
		// //监考人员
		// if (null != examRoom.getInvigilators()) {
		// for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
		// if (checkEroomUsers("rinvigilators", examRoom
		// .getInvigilators().get(i).getId(), id))
		// addEroomusers("rinvigilators", examRoom
		// .getInvigilators().get(i).getId(), examRoom.getId());
		// }
		// }
		// //阅卷人员
		// if (null != examRoom.getAppraises()) {
		// for (int i = 0; i < examRoom.getAppraises().size(); i++) {
		// if (checkEroomUsers("rappraises", examRoom
		// .getAppraises().get(i).getId(), id))
		// addEroomusers("rappraises", examRoom
		// .getAppraises().get(i).getId(), examRoom.getId());
		// }
		// }
		// //复核人员
		// if (null != examRoom.getValids()) {
		// for (int i = 0; i < examRoom.getValids().size(); i++) {
		// if (checkEroomUsers("valids", examRoom.getValids()
		// .get(i).getId(), id))
		// addEroomusers("valids", examRoom.getValids()
		// .get(i).getId(), examRoom.getId());
		// }
		// }
		// //可申请
		// if(examRoom.getIsApplication() == 1){
		// if(erRegistration != null){
		// erRegistration.setEroom(examRoom);
		// //添加申请考场信息
		// addEroomRegistration(erRegistration);
		// }
		// }

		// return examRoom.getId();
		return copyEroom(id, -2, -2);
	}

	public int copyEroom(int id, int classid, int courseid) throws ElException {

		// 获取考场基本信息
		ExamRoom examRoom = getExamRoomByid(id);
		if (classid != -2)// 设置培训班
			examRoom.setClassid(classid);
		if (courseid != -2)// 设置课程
			examRoom.setCourse(new Course(courseid));
		// examRoom.setTitle(examRoom.getTitle() + "_副本");
		examRoom.setTitle(examRoom.getTitle());
		// 考场id获取申请考场
		EroomRegistration erRegistration = getEroomRegistration(id);
		// 添加考场
		addExamRoom(examRoom);
		// 获取试卷列表
		List<ExamPaper> eList = getEroomeps(id);
		for (int i = 0; i < eList.size(); i++) {
			ExamPaper ep = eList.get(i);
			// 新的考场
			addEroomeps(examRoom.getId(), ep.getId(), 0, ep.getPractimes(), ep
					.getPracscore(), ep.getPassgrade(), ep.getStuview(), ep
					.getQuizlook(), ep.getScorelook(), ep.getQuizcount(), ep
					.getPassmanner(), ep.getSortid());
		}
		// 设置监考人员
		examRoom.setInvigilators(getEroomUsers("rinvigilators", id));
		// 设置阅卷人员
		examRoom.setAppraises(getEroomUsers("rappraises", id));
		// 设置复核人员
		examRoom.setValids(getEroomUsers("valids", id));

		// 监考人员
		if (null != examRoom.getInvigilators()) {
			for (int i = 0; i < examRoom.getInvigilators().size(); i++) {
				if (checkEroomUsers("rinvigilators", examRoom.getInvigilators()
						.get(i).getId(), id))
					addEroomusers("rinvigilators", examRoom.getInvigilators()
							.get(i).getId(), examRoom.getId());
			}
		}
		// 阅卷人员
		if (null != examRoom.getAppraises()) {
			for (int i = 0; i < examRoom.getAppraises().size(); i++) {
				if (checkEroomUsers("rappraises", examRoom.getAppraises()
						.get(i).getId(), id))
					addEroomusers("rappraises", examRoom.getAppraises().get(i)
							.getId(), examRoom.getId());
			}
		}
		// 复核人员
		if (null != examRoom.getValids()) {
			for (int i = 0; i < examRoom.getValids().size(); i++) {
				if (checkEroomUsers("valids", examRoom.getValids().get(i)
						.getId(), id))
					addEroomusers("valids",
							examRoom.getValids().get(i).getId(), examRoom
									.getId());
			}
		}
		// 可申请
		if (examRoom.getIsApplication() == 1) {
			if (erRegistration != null) {
				erRegistration.setEroom(examRoom);
				// 添加申请考场信息
				addEroomRegistration(erRegistration);
			}
		}
		return examRoom.getId();
	}

	/**
	 * 考场是否满足申请要求
	 * 
	 * @author
	 * @return
	 * @throws ElException
	 */
	public boolean checkIsuserApp(ExamRoom eroom, ELUser elUser)
			throws ElException {
		boolean IsuserApp = true;
		boolean jz = true;
		boolean ds = true;
		boolean zj = true;
		boolean zw = true;
		boolean gw = true;
		boolean nl = true;
		boolean xb = true;
		boolean bm = true;
		boolean erooms = true;
		boolean elclass = true;
		if (eroom.getErRegistration().getDslist() == null) {// 地市不限
			ds = true;
		} else {
			if (eroom.getErRegistration().getDslist() != null
					&& elUser.getDishi() > 0
					&& eroom.getErRegistration().getDslist().contains(
							elUser.getDishi() + "")) {
				ds = true;// dslist不为空 uds不为空 dslist 里没有该地市
			}
		}
		if (eroom.getErRegistration().getJzlist() == null) {
			jz = true;// 不限
		} else {
			if (eroom.getErRegistration().getJzlist() != null
					&& elUser.getJingzhong() > 0
					&& eroom.getErRegistration().getJzlist().contains(
							elUser.getJingzhong() + "")) {
				jz = true;
			}
		}
		if (eroom.getErRegistration().getZjlist() == null) {
			zj = true;// 不限
		} else {
			if (eroom.getErRegistration().getZjlist() != null
					&& elUser.getZhiji() > 0
					&& eroom.getErRegistration().getZjlist().contains(
							elUser.getZhiji() + "")) {
				zj = true;
			}
		}
		if (eroom.getErRegistration().getZwlist() == null) {
			zw = true;// 不限
		} else {
			if (eroom.getErRegistration().getZwlist() != null
					&& elUser.getZhiwu() > 0
					&& eroom.getErRegistration().getZwlist().contains(
							elUser.getZhiwu() + "")) {
				zw = true;
			}
		}
		if (eroom.getErRegistration().getGwlist() == null) {
			gw = true;
		} else {
			if (eroom.getErRegistration().getGwlist() != null
					&& elUser.getGangwei() != null
					&& eroom.getErRegistration().getGwlist().contains(
							elUser.getGangwei())) {
				gw = true;
			}
		}
		// 年龄段
		if (eroom.getErRegistration().getStartAge() == 0
				&& eroom.getErRegistration().getStopAge() == 0) {
			nl = true;
		} else {
			if (elUser.getAGE() > eroom.getErRegistration().getStartAge()
					&& eroom.getErRegistration().getStopAge() > elUser.getAGE()) {
				nl = true;
			}
		}
		// 性别
		if (eroom.getErRegistration().getSex().equals("不限")) {
			xb = true;
		} else if (eroom.getErRegistration().getSex().equals(elUser.getSex())) {
			xb = true;
		}

		// 部门
		if (eroom.getErRegistration().getTreeType() == null) {// 部门不限
			bm = true;
		} else {
			// 检测部门条件是否通过
			if (eroom.getErRegistration().getTreeType() != null
					&& elUser.getDepartment() != null
					&& ((UserDao) SpringContextUtil.getBean("userDao"))
							.checkUserIsInDep(elUser.getId(), eroom
									.getErRegistration().getTreeType())) {
				bm = true;
			}
		}
		// 考场
		if (eroom.getErRegistration().getExamRooms() == null
				|| eroom.getErRegistration().getExamRooms().equals("")
				|| eroom.getErRegistration().getExamRooms().equals("0")) {// 考场不限
			erooms = true;
		} else {
			String sqlWhere = "";
			if (eroom.getErRegistration().getEroomScreeningWay() == 1) {
				sqlWhere = " and ispassed  = 1";
			} else if (eroom.getErRegistration().getEroomScreeningWay() == 2) {
				sqlWhere = " and ispassed  = 0";
			}
			if (!eroom.getErRegistration().getExamRooms().equals("")
					&& checkEroomIspassed(eroom.getErRegistration()
							.getExamRooms(), elUser.getId(), sqlWhere)) {
				erooms = true;
			}
		}
		// 培训班
		if (eroom.getErRegistration().getElclasss() == null
				|| eroom.getErRegistration().getElclasss().equals("")
				|| eroom.getErRegistration().getElclasss().equals("0")) {// 培训班不限
			elclass = true;
		} else {

			String sqlWhere = "";
			if (eroom.getErRegistration().getClassScreeningWay() == 1) {
				sqlWhere = "and certificateno is not null";
			} else if (eroom.getErRegistration().getClassScreeningWay() == 2) {
				sqlWhere = "and certificateno is null";
			}

			if (!eroom.getErRegistration().getElclasss().equals("")
					&& checkElclassIspassed(eroom.getErRegistration()
							.getElclasss(), elUser.getId(), sqlWhere)) {
				elclass = true;
			}
		}

		if (jz && ds && zj && zw && gw && nl && xb && bm && erooms && elclass) { //  
			IsuserApp = true;
		} else {
			IsuserApp = false;
		}
		return IsuserApp;
	}

	/**
	 * 获取可报名的考场数量
	 * 
	 * @param userid
	 * @param roleid
	 * @return
	 * @throws ElException
	 */
	public int getEroomAppcount(int userid, int roleid) throws ElException {
		EroomLib eroomLibTree = getEroomLibTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		List<ExamRoom> examRooms = getApplyForeEroom(
				eroomLibTree,
				1,
				null,
				roleid,
				" and elr.registrationStartTime < sysdate and elr.registrationStopTime > sysdate ",
				999999, 1);
		ELUser elUser = ((UserDao) SpringContextUtil.getBean("userDao"))
				.getUserById(userid);
		if (examRooms.size() != 0) {
			String eroomids = "";
			for (int i = 0; examRooms.size() > i; i++) {
				if (checkIsuserApp(examRooms.get(i), elUser)) {// 如果返回false证明有某条不符合条件
					if (eroomids.equals(""))
						eroomids = eroomids + examRooms.get(i).getId();
					else
						eroomids = eroomids + "," + examRooms.get(i).getId();
				}
			}
			if (!eroomids.equals("")) {
				return getApplyForeEroomSize(eroomLibTree, 1, null, roleid,
						" and er.id in (" + eroomids + ")");
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 添加备注（可申请且需审核的考场（培训班）不通过原因）
	 * 
	 * @param simpleRemack
	 * @throws ElException
	 */
	public void addSimpleRemack(SimpleRemack simpleRemack) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into simpleRemack(type,typeid,Createid,Touserid,createtime,Title,Phone,Content) values(?,?,?,?,?,?,?,?)");
			ps.setInt(1, simpleRemack.getType());
			ps.setInt(2, simpleRemack.getTypeid());
			ps.setInt(3, simpleRemack.getCreater().getId());
			ps.setInt(4, simpleRemack.getToUser().getId());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setString(6, simpleRemack.getTitle());
			ps.setString(7, simpleRemack.getPhone());
			ps.setString(8, simpleRemack.getContent());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加备注（可申请且需审核的考场（培训班）不通过原因）失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 查询可申请且需审核的考场（培训班）不通过原因备注信息
	 * 
	 * @param simpleRemack
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<SimpleRemack> listSimpleRemack(SimpleRemack simpleRemack,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<SimpleRemack> simpleRemacks = new ArrayList<SimpleRemack>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from(select sr.id srid,sr.title srtitle,sr.phone,sr.createtime,sr.content,eu.id euid,eu.username,eu.realname,erl.id erlid,erl.name erlname "
							+ " from simpleRemack sr inner join eluser eu on sr.createid=eu.id "
							+ " inner join elrole erl on eu.role=erl.id where sr.type=? and sr.typeid=? and sr.touserid=?"
							+ " order by sr.createtime desc)t where rownum<=? ) where rn>=?");
			ps.setInt(1, simpleRemack.getType());
			ps.setInt(2, simpleRemack.getTypeid());
			ps.setInt(3, simpleRemack.getToUser().getId());
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			SimpleRemack tempSr = null;
			while (rs.next()) {
				tempSr = new SimpleRemack(rs.getInt(1), rs.getString(2));
				tempSr.setPhone(rs.getString(3));
				tempSr.setCreatetime(rs.getTimestamp(4));
				tempSr.setContent(rs.getString(5));
				tempSr.setCreater(new ELUser(rs.getInt(6), rs.getString(7), rs
						.getString(8)));
				tempSr.getCreater().setRole(
						new ElRole(rs.getInt(9), rs.getString(10)));
				simpleRemacks.add(tempSr);
			}
		} catch (Exception e) {
			logger.error("查询可申请且需审核的考场（培训班）不通过原因备注信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return simpleRemacks;
	}

	/**
	 * 获取该考场学员报名人数
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getStudyApplyCount(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(sra.userid) from study_room_apply sra where sra.roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取该考场学员报名人数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取考场人数
	 * 
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int getEroomUserSize(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(userid) from study_room where roomid = ?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if (rs.next())
				number = rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取考场人数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取考场人员信息
	 * 
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listEroomStudyInfo(int roomid, int status, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			String tempSql = "";
			if (status >= 0) {
				tempSql = " and sr.status=? ";
			}
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from ("
							+ "select eu.id euid,eu.username,eu.realname,eu.sex,eu.movephone,eu.shenfenzheng,eu.xuhao,dep.id depid,dep.name depname,bt.id,bt.basevalue "
							+ " from study_room sr inner join eluser eu on eu.id=sr.userid inner join department dep on eu.depid=dep.id left join basedatat bt on bt.id=eu.jingzhong where sr.roomid=? "
							+ tempSql + " )t where rownum<=? ) where rn>=?");
			int i = 0;
			ps.setInt(1, roomid);
			if (status >= 0) {
				i++;
				ps.setInt(i + 1, status);
			}
			ps.setInt(i + 2, pageNow);
			ps.setInt(i + 3, pageSize);
			rs = ps.executeQuery();
			ELUser elUser = null;
			while (rs.next()) {
				elUser = new ELUser(rs.getInt(1), rs.getString(2), rs
						.getString(3));
				elUser.setSex(rs.getString(4));
				elUser.setMovephone(rs.getString(5));
				elUser.setShenfenzheng(rs.getString(6));
				elUser.setXuhao(rs.getString(7));
				elUser.setDepartment(new Department(rs.getInt(8), rs
						.getString(9)));
				elUser.setJingzhong(rs.getInt(10));
				userList.add(elUser);
			}
		} catch (Exception e) {
			logger.error("获取考场人员信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	/**
	 * 获取考场人员信息数量
	 * 
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int listEroomStudyInfoSize(int roomid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String tempSql = "";
			if (status >= 0) {
				tempSql = " and sr.status=? ";
			}
			ps = ct
					.prepareStatement("select count(eu.id) "
							+ " from study_room sr inner join eluser eu on eu.id=sr.userid where sr.roomid=? "
							+ tempSql);
			int i = 0;
			ps.setInt(1, roomid);
			if (status >= 0) {
				i++;
				ps.setInt(i + 1, status);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考场人员信息数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 删除学员考场记录
	 * 
	 * @param roomid
	 * @param userid
	 * @throws ElException
	 */
	public void deleteStudyRoomApply(int roomid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from study_room_apply where roomid=? and userid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除学员考场记录出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 添加考场警种信息
	 * 
	 * @param roomid
	 * @param jsIds
	 * @throws ElException
	 */
	public void addEroomJingzhong(int roomid, String[] jzIds)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		this.deleteEroomJingzhong(roomid);
		if (jzIds == null) {
			return;
		}
		try {
			ct = DBConnection.getConnection();
			for (int i = 0; i < jzIds.length; i++) {
				ps = ct
						.prepareStatement("insert into exam_jingzhong(roomid,jingzhong,status) values(?,?,0)");
				ps.setInt(1, roomid);
				ps.setInt(2, Integer.parseInt(jzIds[i]));
				ps.executeUpdate();
				ps.close();
			}
		} catch (Exception e) {
			logger.error("添加考场警种信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 检测是否存在考场警种信息
	 * 
	 * @param roomid
	 * @param jingzhong
	 * @return
	 * @throws ElException
	 */
	public boolean checkEroomJingzhong(int roomid, int jingzhong)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select jingzhong from exam_jingzhong where roomid=? and jingzhong=?");
			ps.setInt(1, roomid);
			ps.setInt(2, jingzhong);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测是否存在考场警种信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 删除考场所有警种信息
	 * 
	 * @param roomid
	 * @throws ElException
	 */
	public void deleteEroomJingzhong(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from exam_jingzhong where roomid=?");
			ps.setInt(1, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除考场所有警种信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 查询考场所有警种信息
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public List<Integer> listEroomAllJingzhong(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select jingzhong from exam_jingzhong where roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("查询考场所有警种信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 查询考场所有警种信息(加状态)
	 * 
	 * @param roomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Integer> listEroomAllJingzhong(int roomid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select jingzhong from exam_jingzhong where roomid=? and status=?");
			ps.setInt(1, roomid);
			ps.setInt(2, status);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("查询考场所有警种信息(加状态)出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 更新考场警种状态
	 * 
	 * @param roomid
	 * @param jzIds
	 * @param status
	 * @throws ElException
	 */
	public void updateEroomJingzhong(int roomid, String[] jzIds, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (jzIds == null) {
			return;
		}
		try {
			ct = DBConnection.getConnection();
			for (int i = 0; i < jzIds.length; i++) {
				ps = ct
						.prepareStatement("update exam_jingzhong set status=? where roomid=? and jingzhong=?");
				ps.setInt(1, status);
				ps.setInt(2, roomid);
				ps.setInt(3, Integer.parseInt(jzIds[i]));
				ps.executeUpdate();
				ps.close();
			}
		} catch (Exception e) {
			logger.error("更新考场警种状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkEroomIsTimeOut(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select endtime from exam_room where id= ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getTimestamp(1).getTime() < System.currentTimeMillis()) {
					b = true;
				}
			}
		} catch (Exception e) {
			logger.error("更新考场警种状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	/**
	 * 更新考场试卷的序号
	 * 
	 * @param sortid
	 * @param manner
	 *            1：上移 2：下移
	 * @throws ElException
	 */
	public void updateEroomEpSortid(int roomid, int epid, int sortid, int manner)
			throws ElException {
		ExamPaper examPaper = null;
		if (manner == 1) {
			if (sortid == 1) {
				// 不用移动
				return;
			}
			// 先查出比他序号小一位的考场试卷，然后更新序号加1
			// 更新本考场试卷 序号减一
			examPaper = getEroomepInfo(roomid, sortid - 1);
			downSortid(roomid, examPaper.getId());
			upSortid(roomid, epid);
		} else {
			// 先查出比他序号大一位的考场试卷，然后更新序号减1
			// 更新本考场试卷 序号加一
			examPaper = getEroomepInfo(roomid, sortid + 1);
			upSortid(roomid, examPaper.getId());
			downSortid(roomid, epid);
		}
	}

	/**
	 * 下移考场试卷序列
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public void downSortid(int roomid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_reps set sortid=sortid+1 where roomid=? and epid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("下移考场试卷序列出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 上移考场试卷序列
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public void upSortid(int roomid, int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_reps set sortid=sortid-1 where roomid=? and epid=?");
			ps.setInt(1, roomid);
			ps.setInt(2, epid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("上移考场试卷序列出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取考场试卷信息
	 * 
	 * @param roomid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public ExamPaper getEroomepInfo(int roomid, int sortid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamPaper ep = new ExamPaper();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epid,sortid from exam_reps where sortid = ? and roomid = ?");
			ps.setInt(1, sortid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ep.setId(rs.getInt(1));
				ep.setSortid(rs.getInt(2));
			}
		} catch (Exception e) {
			logger.error("获取考场试卷信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ep;
	}

	/**
	 * 获取课程的考场
	 */
	public int getCourseByRoomId(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from exam_room where  courseid = ?");//
			ps.setInt(1, courseid);
			rs = ps.executeQuery();

			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取课程被绑定的考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void updateExamroom(int courseid, int elclssid, int eroomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set classid=?, isband=1,bandclassid=? where classid= 0 and  courseid=? and id = ? ");
			ps.setInt(1, elclssid);
			ps.setInt(2, elclssid);
			ps.setInt(3, courseid);
			ps.setInt(4, eroomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新课程考场给培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void updateStudyroom(MyRoom myroom)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_room set myscore=? where roomid=? and userid=? and classid=?");
			ps.setFloat(1, myroom.getMyScore());
			ps.setInt(2, myroom.getExamroom().getId());
			ps.setInt(3, myroom.getTester().getId());
			ps.setInt(4, myroom.getExamroom().getClassid());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新课程考场给培训班出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setClassBindingCourse(int classId, int courseid, int eroomid,
			String tableName) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update "
							+ tableName
							+ " set binding = ? , eroomid = ? where classId  = ?  and courseid = ?");
			ps.setInt(1, 1);
			ps.setInt(2, eroomid);
			ps.setInt(3, classId);
			ps.setInt(4, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ExamRoom> gettitles(int roomid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ExamRoom er = new ExamRoom();
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from exam_room where id=? and courseid=?");
			rs = ps.executeQuery();
			while (rs.next()) {
				er.setTitle(rs.getString("title"));
				ers.add(er);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 获取课程被绑定的考场
	 */
	public String getBindingCourseByRoomId(int classId, int status,
			String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		StringBuffer roomIds = new StringBuffer("");
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select eroomid from " + tableName
					+ " where classId  = ?  and binding = 1 and status=?");//
			ps.setInt(1, classId);
			ps.setInt(2, status);
			rs = ps.executeQuery();

			while (rs.next()) {
				roomIds.append(rs.getInt(1) + ",");
			}
			// 去掉最后一个逗号
			if (roomIds.length() > 1)
				roomIds.deleteCharAt(roomIds.length() - 1);
		} catch (Exception e) {
			logger.error("获取课程被绑定的考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return roomIds.toString();
	}

	public void assignRoom(int examroomid, int classid,int firstLearnLaterExam,int standardLine) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "{call assign_examroom(?,?,?,?)}";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, examroomid);
			ps.setInt(3, firstLearnLaterExam);
			ps.setInt(4, standardLine);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("培训班分配考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	/**
	 * 获取该培训班中所有被绑定的考场
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> listExamRoomByClass_cisco(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select er.id , er.title,er.begintime, er.endtime" +
							",er.location,er.courseid,er.passgrade,er.isband,er.bandclassid," +
							"er.valid,er.uvalid,er.avalid ,sr.ispassed,sr.myscore " +
							"from exam_room er  ,study_room sr" +
							" where    " +
							"  er.id=sr.roomid(+)  and sr.classid=? and sr.userid = ?");
//			
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6)));
				er.setPassgrade(rs.getFloat(7));
				er.setIsBand(rs.getInt(8));
				er.setBandClassid(rs.getInt(9));
				er.setValid(rs.getInt(10));
				er.setUvalid(rs.getInt(11));
				er.setAvalid(rs.getInt(12));
//				er.setFirstLearnLaterExam(rs.getInt(13));
//				er.setStandardLine(rs.getInt(14));
				er.setScore(rs.getFloat(14));
				er.setIsPassed(rs.getInt(13));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取该培训班中所有被绑定的考场失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public int getRoomidByClassid_cisco(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select examroomid from elclass_assign_examroom where classid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("培训班分配考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public int getIsPass(int userid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int isPassed = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select ispassed from study_room where userid=? and roomid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				isPassed = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("考场是否通过出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return isPassed;
	}

	public MyRoom getMyRoom(int roomid,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyRoom myroom = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select roomid,status,myscore from study_room where userid=? and roomid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				myroom = new MyRoom();
				myroom.setId(rs.getInt(1));
				myroom.setStatus(rs.getInt(2));
				myroom.setMyScore(rs.getFloat(3));
			}
		} catch (Exception e) {
			logger.error("根据考场id获取我的考场信息出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myroom;
	}

	public void updateMyCPage(int userid, int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call sc_setmycpage(?,?)}");
			ps.setInt(1, userid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新我的章节信息出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public MyExamPaper getExamPaperByRoomid(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		MyExamPaper myExamPaper = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from  exampaper  where roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if(rs.next()){
				myExamPaper = new MyExamPaper(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("获取试卷信息出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myExamPaper;
	}

	public MyCPage getBindingExamRooms(MyCPage myCpage, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> rooms = new ArrayList<ExamRoom>();
		ExamRoom er = null;
		ExamPaper ep = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select  er.id erid ,er.title ertitle,er.cpid , se.epid ,er.sortid " +
					" from exam_room er " +
					" left join study_exampaper se on se.roomid = er.id and se.userid = ? " +
					" where er.courseid=? and er.cpid=?  order by er.sortid asc");
			ps.setInt(1, userid);
			ps.setInt(2, myCpage.getCourseid());
			ps.setInt(3, myCpage.getCpage().getId());
			rs = ps.executeQuery();
			while(rs.next()){
				er = new ExamRoom(rs.getInt(1),rs.getString(2));
				er.setCpid(rs.getInt(3));
				ep = new ExamPaper(rs.getInt(4));
				er.setExamPaper(ep);
				er.setSortid(rs.getInt(5));
				rooms.add(er);
			}
			myCpage.setExamRooms(rooms);
		} catch (Exception e) {
			logger.error("章节关联考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myCpage;
	}

	public List<ExamRoom> getCPageRooms(int cpageid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> rooms = new ArrayList<ExamRoom>();
		ExamRoom er = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select  er.id erid ,er.title ertitle  " +
					" from exam_room er " +
					" where er.courseid=? and er.cpid=? and er.classid=0 ");
			ps.setInt(1, courseid);
			ps.setInt(2, cpageid);
			rs = ps.executeQuery();
			while(rs.next()){
				er = new ExamRoom(rs.getInt(1),rs.getString(2));
				rooms.add(er);
			}
		} catch (Exception e) {
			logger.error("章节关联考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return rooms;
	}

	public int getStudyQueSize(int eroomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from STUDY_QUIZINFO where roomid=?");
			ps.setInt(1, eroomid);
			rs = ps.executeQuery();
			if(rs.next()){
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public int getEpidByRoomid(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int epid = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from STUDY_EXAMPAPER where roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if(rs.next()){
				epid = rs.getInt("epid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return epid;
	}

	public List<Integer> getQuestionids(int epid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		List<Integer> questionids = new ArrayList<Integer>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select epbq.* from exampaperblock epb left join exampaperblockquestion epbq on epb.id=epbq.blockid where epb.exampaperid=?");
			ps.setInt(1, epid);
			rs = ps.executeQuery();
			while(rs.next()){
				int questionid = rs.getInt("questionid");
				questionids.add(questionid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return questionids;
	}

	public boolean setExamRoomCanExam(ExamRoom examRoom,int courseid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call checkexamroomcanexam(?,?,?,?,?,?)}");  
			cs.setInt(1, examRoom.getId());
			cs.setInt(2, examRoom.getCpid());
			cs.setInt(3, examRoom.getSortid());
			cs.setInt(4, courseid);
			cs.setInt(5, userid);
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			flag = cs.getBoolean(6);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public int maxExamRoomSortid(int courseid, int cpid) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		int sortid=0;
//		try {
//			ct = DBConnection.getConnection();
//			CallableStatement cs = ct.prepareCall("call addExamRoomSortid(?,?,?)");  
//			ps.setInt(1, courseid);
//			ps.setInt(2, cpid);
//			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);  
//			cs.execute(); 
//			System.out.println(cs.getInt(3));
//			sortid = cs.getInt(3);
//		} catch (Exception e) {
//			logger.error("获取章节考场最大sortid出错", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return sortid;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int sortid = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select max(sortid) from exam_room where courseid=? and cpid=? and classid=0");
			ps.setInt(1, courseid);
			ps.setInt(2, cpid);
			rs = ps.executeQuery();
			if(rs.next()){
				sortid = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("章节关联考场最大sortid出错", e);
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sortid;
		
	}

	public void deleteEroomepByErid(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from EXAM_REPS where roomid=?");
			ps.setInt(1, roomid);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteErEpUsersByErid(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from STUDY_EXAMPAPER where roomid=?");
			ps.setInt(1, roomid);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void deleteErUserByErid(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from study_room where roomid=?");
			ps.setInt(1, roomid);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void addStudyRoom(MyRoom myroom) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into study_room(roomid,userid,ispassed,status,myscore,classid) values(?,?,?,?,?,?)");
			ps.setInt(1, myroom.getExamroom().getId());
			ps.setInt(2, myroom.getTester().getId());
			ps.setInt(3, myroom.getIspassed());
			ps.setInt(4, myroom.getStatus());
			ps.setFloat(5, myroom.getMyScore());
			ps.setInt(6, myroom.getExamroom().getClassid());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean cheEroomIsXianxia(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select islink from exam_room where id=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)==0){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public ExamRoom getFinishExamRoom(int userid,int courseid ,MyCPage myCPage)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ExamRoom examRoom = new ExamRoom();
		int id = 0;
		int sortid = 0;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call getFinishExamRoom(?,?,?,?,?)}");  
			cs.setInt(1, userid);
			cs.setInt(2, courseid);
			cs.setInt(3, myCPage.getCpid());
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);  
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			id = cs.getInt(4);
			sortid = cs.getInt(5);
			examRoom.setId(id);
			examRoom.setSortid(sortid);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return examRoom;
	}

}
