package com.sopia.assistman.dao.impl;

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
import com.sopia.assistman.PlanContants;
import com.sopia.assistman.dao.PlanDao;
import com.sopia.assistman.entities.Plan;
import com.sopia.assistman.entities.PlanStage;
import com.sopia.assistman.entities.PlanStuff;
import com.sopia.assistman.entities.PlanVerify;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.StuffLib;

public class PlanDaoImpl implements PlanDao {
	private static final Log logger = LogFactory.getLog(PlanDaoImpl.class);

	public int addPlan(Plan plan) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(PlanContants.PLAN_ADD));
			ps.setString(1, plan.getName());
			ps.setString(2, plan.getContent());
			ps.setInt(3, plan.getManager().getId());
			ps.setString(4, plan.getContact());
			ps.setString(5, plan.getParticipator());
			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			ps.setDate(7, new Date(plan.getPlanfinishdate().getTime()));
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('elplan') AS id");
				rs = ps.executeQuery();
			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public Plan getPlanByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Plan pl = new Plan();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(PlanContants.PLAN_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				pl = new Plan(rs.getInt(1), rs.getString(2));
				pl.setContent(rs.getString(3));
				pl.setManager(new ELUser(rs.getInt(4), rs.getString(7)));
				pl.setContact(rs.getString(5));
				pl.setParticipator(rs.getString(6));
				pl.setCreatetime(rs.getTimestamp(8));
				pl.setStatus(rs.getInt(9));
			}
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pl;
	}

	public void alterPlan(Plan plan) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(PlanContants.PLAN_ALTER));
			ps.setString(1, plan.getName());
			ps.setString(2, plan.getContent());
			ps.setString(3, plan.getContact());
			ps.setString(4, plan.getParticipator());
			ps.setDate(5, new Date(plan.getPlanfinishdate().getTime()));
			ps.setInt(6, plan.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addPlanStage(PlanStage planStage) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTAGE_ADD));
			ps.setString(1, planStage.getContent());
			ps.setInt(2, planStage.getPlandays());
			ps.setDate(3, planStage.getPlanfinishdate());
			ps.setInt(4, planStage.getPlan().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addPlanStageStuff(PlanStuff planStuff) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTUFF_ADD));
			ps.setInt(1, planStuff.getPlanStage().getId());
			ps.setInt(2, planStuff.getStuff().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deletePlanStageStuff(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTUFF_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<PlanStuff> listPStuffByPsId(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<PlanStuff> pss = new ArrayList<PlanStuff>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTUFF_LIST_BYPSID));

			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				PlanStuff ps1 = new PlanStuff(rs.getInt(1));
				ps1.setStuff(new StuffLib(rs.getInt(2), rs.getString(3)));
				ps1.getStuff().setFileext(rs.getString(4));
				ps1.getStuff().setType(rs.getInt(5));
				pss.add(ps1);
			}
		} catch (Exception e) {
			logger.error("获取计划阶段实施相关材料失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pss;
	}

	public void alterPlanStage(PlanStage planStage) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTAGE_ALTER));
			ps.setString(1, planStage.getContent());
			ps.setInt(2, planStage.getPlandays());
			ps.setDate(3, planStage.getPlanfinishdate());
			ps.setInt(4, planStage.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void planStageCarryout(PlanStage planStage) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTAGE_CARRYOUT));
			ps.setInt(1, planStage.getRealdays());
			ps.setDate(2, planStage.getRealfinishdate());
			ps.setInt(3, planStage.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Plan> listPlansByUid(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Plan> plans = new ArrayList<Plan>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLAN_LIST_BYUID));

			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			// ps.setInt(3, pageSize);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				Plan pl = new Plan(rs.getInt(1), rs.getString(2));
				pl.setContent(rs.getString(3));
				pl.setManager(new ELUser(rs.getInt(4), rs.getString(7)));
				pl.setContact(rs.getString(5));
				pl.setParticipator(rs.getString(6));
				pl.setCreatetime(rs.getTimestamp(8));
				pl.setStatus(rs.getInt(9));
				plans.add(pl);
			}
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return plans;
	}

	public List<PlanStage> listPlanStageBYPid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<PlanStage> planStages = new ArrayList<PlanStage>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTAGE_LIST_BYPID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				PlanStage pls = new PlanStage(rs.getInt(1), rs.getString(2));
				pls.setPlandays(rs.getInt(3));
				pls.setRealdays(rs.getInt(4));
				pls.setPlanfinishdate(rs.getDate(5));
				pls.setRealfinishdate(rs.getDate(6));
				planStages.add(pls);
			}
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return planStages;
	}

	public PlanStage getpStageById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PlanStage pls = new PlanStage();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTAGE_LIST_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				pls = new PlanStage(rs.getInt(1), rs.getString(2));
				pls.setPlandays(rs.getInt(3));
				pls.setRealdays(rs.getInt(4));
				pls.setPlanfinishdate(rs.getDate(5));
				pls.setRealfinishdate(rs.getDate(6));
			}
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pls;
	}

	public void deletePlanStage(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLANSTAGE_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加新计划阶段失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void planStatusSet(Connection ct, int plid, int status)
			throws Exception {

		ct = DBConnection.getConnection();
		PreparedStatement ps = ct.prepareStatement(ElQuerySql
				.getSQL(PlanContants.PLAN_STATUS_SET));
		ps.setInt(1, status);
		ps.setInt(2, plid);
		ps.executeUpdate();
		ps.close();

	}

	private void planVerifyStatus(Connection ct, int plid, int userid,
			int status) throws Exception {
		PreparedStatement ps = ct.prepareStatement(ElQuerySql
				.getSQL(PlanContants.PLAN_VERIFY_ADD));
		ps.setInt(1, plid);
		ps.setInt(2, userid);
		ps.setInt(3, status);
		ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		ps.executeUpdate();
	}

	public void planVerifySet(int plid, int status, int userid, int role)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 审核
			planSuperVerifySet(ct, plid);
			planVerifyStatus(ct, plid, userid, status);
			if (role == 1) {
				planStatusSet(ct, plid, status);
			} else {
				planStatusSet(ct, plid, PlanContants.PLAN_STATUS_SHWAITING);
			}
		} catch (Exception e) {
			logger.error("计划审核失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void planSuperVerifySet(Connection ct, int plid) throws Exception {
		PreparedStatement ps = ct.prepareStatement(ElQuerySql
				.getSQL(PlanContants.PLAN_SUPERVERIFIED_SET));
		ps.setInt(1, plid);
		ps.executeUpdate();
	}

	public List<Plan> listVerfiyPlans(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Plan> plans = new ArrayList<Plan>();
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLAN_VERIFY_LIST));
			ps.setInt(1, depid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Plan pl = new Plan(rs.getInt(1), rs.getString(2));
				pl.setManager(new ELUser(rs.getInt(3), rs.getString(6)));
				pl.setCreatetime(rs.getTimestamp(4));
				pl.setStatus(rs.getInt(5));
				plans.add(pl);
			}
		} catch (Exception e) {
			logger.error("计划审核列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return plans;
	}

	public List<PlanVerify> getPlanVerfiysByPid(int pid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<PlanVerify> planvs = new ArrayList<PlanVerify>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLAN_VERIFIED_LIST));
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			while (rs.next()) {
				PlanVerify pv = new PlanVerify(rs.getInt(1));
				pv.setPlan(new Plan(rs.getInt(2), rs.getString(3)));
				pv.setUser(new ELUser(rs.getInt(4), rs.getString(5)));
				pv.setStatus(rs.getInt(6));
				pv.setVerifydate(rs.getTimestamp(7));
				planvs.add(pv);
			}
		} catch (Exception e) {
			logger.error("计划审核列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return planvs;
	}

	public List<Plan> listPlansByDepid(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Plan> plans = new ArrayList<Plan>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			int lid = 0;
			int rid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(PlanContants.PLAN_CARRYOUT_LIST));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Plan pl = new Plan(rs.getInt(1), rs.getString(2));
				pl.setManager(new ELUser(rs.getInt(3), rs.getString(4)));
				pl.setCreatetime(rs.getTimestamp(5));
				pl.setStatus(rs.getInt(6));
				pl.setPlandays(rs.getInt(7));
				pl.setRealdays(rs.getInt(8));
				pl.setPlanfinishdate(rs.getDate(9));
				pl.setRealfinishdate(rs.getDate(10));
				plans.add(pl);
			}
		} catch (Exception e) {
			logger.error("计划审核列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return plans;
	}
}
