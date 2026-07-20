package com.sopia.statman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.assistman.entities.Offline;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.statman.StatisticConstants;
import com.sopia.statman.dao.StatisticDao;
import com.sopia.statman.entities.Resources;
import com.sopia.studyman.entities.MyCourse;

public class StatisticDaoImpl implements StatisticDao {
	private static final Log logger = LogFactory.getLog(StatisticDaoImpl.class);

	public List<Department> listDepinfo(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_DEP_INFO_BYDID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setUserCount(rs.getInt(3));
				dep.setCourseCount(rs.getInt(4));
				dep.setClassCount(rs.getInt(5));
				deps.add(dep);
			}

		} catch (Exception e) {
			logger.error("用户开课列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	/**
	 * 部门统计基本信息表
	 */
	public Department getDepinfo(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(StatisticConstants.STAT_DEP_INFO_BYID));
			ps = ct
					.prepareStatement("select dep_1.id,dep_1.name,( select count(*) from eluser eu,department dep_u where eu.depid = dep_u.id and dep_u.lid>=dep_1.lid and dep_u.rid<=dep_1.rid ) as userCount ,"
							+ "(select count(distinct (ca.courseid)) from study_course ca,eluser caeu,department dep_ca where  caeu.depid = dep_ca.id and caeu.id = ca.userid and dep_ca.lid>=dep_1.lid and dep_ca.rid<=dep_1.rid ) as courseCount,"
							+ "(select count(distinct(cla.classid)) from study_class cla,eluser claeu,department dep_cla  where claeu.depid = dep_cla.id and claeu.id = cla.userid and  dep_cla.lid>=dep_1.lid and dep_cla.rid<=dep_1.rid) as classcount "
							+ "from department dep_1 where dep_1.id=  ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setUserCount(rs.getInt(3));
				dep.setCourseCount(rs.getInt(4));
				dep.setClassCount(rs.getInt(5));
			}

		} catch (Exception e) {
			logger.error("用户开课列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	/**
	 * 部门学员学习排行榜
	 */
	public List<ELUser> getDepUserCredit(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> eus = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0, rid = 0;
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			// ps = ct
			// .prepareStatement("select
			// eu.id,eu.realname,eu.username,dep.id,dep.name,(select
			// sum(cc.credit) from class_course cc ,study_quizinfo sqi
			// ,exam_room er where sqi.ispassed =true and sqi.roomid =er.id and
			// er.courseid = cc.courseid and sqi.userid = eu.id) as credit from
			// eluser eu,department dep where dep.id = eu.depid and dep.lid>=?
			// and dep.rid<= ? order by credit desc limit ?,? ");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_DEP_USER_CREDIT));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				// eu.setStudentno(rs.getString(3));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				// eu.setXfscore(rs.getInt(6));
				// eu.setCompany(new Company(0, SystemConfOp.getSecondDep(
				// eu.getDepartment().getId()).getName()));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("查看类别课程学员列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public int getDepUserCreditSize(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0, rid = 0;
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_DEP_USER_CREDIT_SIZE));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看类别课程学员列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

//	public List<ELUser> getStatUserByDep(int depid, int subdep, ELUser eu,
//			int pageNow, int pageSize) throws ElException {
//		List<ELUser> eus = new ArrayList<ELUser>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
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
//						.getSQL(StatisticConstants.STAT_USER_DEP_LIST_SUB));
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				// ps.setString(3, "%" + email + "%");
//				ps.setInt(3, dep.getLid());
//				ps.setInt(4, dep.getRid());
//				ps.setInt(5, pageNow);
//				ps.setInt(6, pageSize);
//			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(StatisticConstants.STAT_USER_DEP_LIST));
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setInt(3, depid);
//				ps.setInt(4, pageNow);
//				ps.setInt(5, pageSize);
//			}
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				ELUser elUser = new ELUser();
//				elUser.setId(rs.getInt(1));
//				elUser.setUsername(rs.getString(2));
//				elUser.setRealname(rs.getString(3));
//				elUser.setDepartment(new Department(rs.getInt(4), rs
//						.getString(5)));
//				elUser.setXx_credit(rs.getInt(6));
////				elUser.setCourseSize(rs.getInt(7));
////				elUser.setClassSize(rs.getInt(8));
//				setStatUser(elUser);
//				eus.add(elUser);
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return eus;
//	}
	/**
	 * 检测用户查询的参数
	 */
	public void checkUserParam(StringBuffer sql,ELUser elUser,List<Object> params){
		if (elUser != null) {
			if (elUser.getSex() != null && !elUser.getSex().equals("")) {
				sql.append(" and eu.sex =?");
				params.add(elUser.getSex());
			}
			if (elUser.getRealname() != null && !elUser.getRealname().equals("")) {
				sql.append(" and eu.realname like ?");
				params.add("%"+StringUtil.toLikeStr(elUser.getRealname())+"%");
			}
			if (elUser.getUsername() != null && !elUser.getUsername().equals("")) {
				sql.append(" and eu.username like ?");
				params.add("%"+StringUtil.toLikeStr(elUser.getUsername())+"%");
			}
			if (elUser.getJingzhong()>0) {
				sql.append(" and eu.jingzhong = ?");
				params.add(elUser.getJingzhong());
			}
//			if (null != elUser.getDishi() && !elUser.getDishi().equals("")&& !elUser.getDishi().equals("0")){
//				sql.append("  and eu.dishi = '"+elUser.getDishi().trim()+"' "); 
//			}
//			if (null != elUser.getZhiji() && !elUser.getZhiji().equals("")&& !elUser.getZhiji().equals("0")){
//				sql.append("  and eu.zhiji = '"+elUser.getZhiji().trim()+"' ");
//			}
//			if (null != elUser.getZhiwu() && !elUser.getZhiwu().equals("")&& !elUser.getZhiwu().equals("0")){
//				sql.append("  and eu.zhiwu = '"+elUser.getZhiwu().trim()+"' "); 
//			}
//			if (null != elUser.getGangwei() && !elUser.getGangwei().equals("")&& !elUser.getGangwei().equals("0")){
//				sql.append("  and eu.gangwei = '"+elUser.getGangwei().trim()+"' ");
//			}
			if(elUser.getShengri()!=null){
				sql.append(" and eu.shengri >= ?");
				params.add(elUser.getShengri());
			}
			if(elUser.getShengri_end()!=null){
				sql.append(" and eu.shengri <= ?");
				params.add(elUser.getShengri_end());
			}
			if (elUser.getIsAssign() != null
					&& !"".equals(elUser.getIsAssign())) {
				if ("0".equals(elUser.getIsAssign())) {
					sql.append(" and  scl.certificateno is null");
				} else {
					sql.append(" and  scl.certificateno is not null ");
				}
			}
		}
	}
	
	/**
	 * 学习统计的查询
	 * @param tree
	 * @param sublibs
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStatUserByDep(ElNode tree, int sublibs, ELUser eu,
			int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params=new ArrayList<Object>();
			StringBuffer sql=new StringBuffer("select * from (select t.*, rownum rn from ( "+
				"select eu.id,eu.username, eu.realname,dep.id depid,dep.name,nvl(sc.courseCount,0) courseCount,nvl(scl.classCount,0) classCount " +
				" from ELUSER eu inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, consub)+") dep on eu.depid = dep.id " +
				" left join (select sc.userid,count(*) courseCount from  study_course sc group by sc.userid) sc on sc.userid = eu.id " +
				" left join (select sc.userid,count(*) classCount from elclass ec left join study_class sc on ec.id=sc.classid where ec.status!=9 and ec.isnormal=1 group by sc.userid) scl on scl.userid=eu.id where 1=1");
			this.checkUserParam(sql, eu, params);
			sql.append(")t where rownum <= ? ) where rn>=?");
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			ps.setInt(params.size()+1, pageNow);
			ps.setInt(params.size()+2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setDepartment(new Department(rs.getInt(4), rs
						.getString(5)));
//				elUser.setXx_credit(rs.getInt(6));
				elUser.setCourseSize(rs.getInt(6));
				elUser.setClassSize(rs.getInt(7));
//				setStatUser(elUser);
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("学习统计的查询失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}
	
	/**
	 * 学习统计的查询(数量)
	 * @param tree
	 * @param sublibs
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getStatUserByDepCount(ElNode tree, int sublibs, ELUser eu) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params=new ArrayList<Object>();
			StringBuffer sql=new StringBuffer(
				"select count(eu.id) " +
				" from ELUSER eu inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, consub)+") dep on eu.depid = dep.id where 1=1 ");
			this.checkUserParam(sql, eu, params);
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("学习统计的查询(数量)失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

//	public int getStatUserByDepCount(int depid, int subdep, ELUser eu)
//			throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
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
//				ps = ct
//						.prepareStatement(ElQuerySql
//								.getSQL(StatisticConstants.STAT_USER_DEP_LIST_SUB_SIZE));
//
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setInt(3, dep.getLid());
//				ps.setInt(4, dep.getRid());
//			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(StatisticConstants.STAT_USER_DEP_LIST_SIZE));
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setInt(3, depid);
//			}
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return 0;
//	}

	/**
	 * 人才搜索。。
	 * 
	 * @param depid
	 * @param subdep
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStatTalentByDep(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			// String email = "";
			String zhichengleibie = "";
			String renyuanleibie = "";
			String gangwei = "";
			String age = "";
			String sex = "";
			String peixunleibie = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
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
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST_SUB));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + zhichengleibie + "%");
				ps.setString(4, "%" + renyuanleibie + "%");
				ps.setString(5, "%" + gangwei + "%");
				ps.setString(6, "%" + age + "%");
				ps.setString(7, "%" + sex + "%");
				ps.setString(8, "%" + peixunleibie + "%");
				ps.setInt(9, dep.getLid());
				ps.setInt(10, dep.getRid());
				ps.setInt(11, pageNow);
				ps.setInt(12, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + zhichengleibie + "%");
				ps.setString(4, "%" + renyuanleibie + "%");
				ps.setString(5, "%" + gangwei + "%");
				ps.setString(6, "%" + age + "%");
				ps.setString(7, "%" + sex + "%");
				ps.setString(8, "%" + peixunleibie + "%");
				ps.setInt(9, depid);
				ps.setInt(10, pageNow);
				ps.setInt(11, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				// elUser.setXuehao(rs.getString(1));
				// elUser.setStudentno(rs.getString(2));
				// elUser.setDanweihao(rs.getString(3));
				// elUser.setRealname(rs.getString(4));
				// elUser.setUsername(rs.getString(5));
				// elUser.setPassword(rs.getString(6));
				// elUser.setKuaijihao(rs.getString(7));
				// elUser.setRenyuanleibie(rs.getString(8));
				// elUser.setZhichengleibie(rs.getString(9));
				// elUser.setZhichengjibie(rs.getString(10));
				// elUser.setLianxifangshi(rs.getString(11));
				// elUser.setSex(rs.getString(12));
				// elUser.setMinzu(rs.getString(13));
				// elUser.setPeixunleibie(rs.getString(14));
				// elUser.setShifouzaizhi(rs.getString(15));
				// elUser.setSuozaigangwei(rs.getString(16));
				// elUser.setBiyeyuanxiao(rs.getString(17));
				// elUser.setBiyeshijian(rs.getDate(18));
				// elUser.setSuoxuezhuanye(rs.getString(19));
				// elUser.setXueli(rs.getString(20));
				// elUser.setXuewei(rs.getString(21));
				// elUser.setZhichenghao(rs.getString(22));
				// elUser.setZhiwupinrenriqi(rs.getDate(23));
				// elUser.setZhichengquderiqi(rs.getDate(24));
				// elUser.setBeizhu(rs.getString(25));
				// elUser.setHeadPhoto(rs.getString(26));

				elUser.setId(rs.getInt(27));
				// elUser.setUsername(rs.getString(2));
				// elUser.setRealname(rs.getString(3));
				// elUser.setCompany(new Company(rs.getInt(7),
				// rs.getString(8)));
				elUser.setDepartment(new Department(rs.getInt(28), rs
						.getString(29)));
				// elUser.setCompany(new Company(3, SystemConfOp.getSecondDep(
				// elUser.getDepartment().getId()).getName()));

				// elUser.setXfscore(rs.getInt(6));
				// elUser.setQuizpassedper(rs.getInt(7));
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public int getStatTalentByDepSize(int depid, int subdep, ELUser eu)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			// String email = "";
			String zhichengleibie = "";
			String renyuanleibie = "";
			String gangwei = "";
			String age = "";
			String sex = "";
			String peixunleibie = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
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
								.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST_SUB_SIZE));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + zhichengleibie + "%");
				ps.setString(4, "%" + renyuanleibie + "%");
				ps.setString(5, "%" + gangwei + "%");
				ps.setString(6, "%" + age + "%");
				ps.setString(7, "%" + sex + "%");
				ps.setString(8, "%" + peixunleibie + "%");
				ps.setInt(9, dep.getLid());
				ps.setInt(10, dep.getRid());
			} else {

				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST_SIZE));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + zhichengleibie + "%");
				ps.setString(4, "%" + renyuanleibie + "%");
				ps.setString(5, "%" + gangwei + "%");
				ps.setString(6, "%" + age + "%");
				ps.setString(7, "%" + sex + "%");
				ps.setString(8, "%" + peixunleibie + "%");
				ps.setInt(9, depid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> getStatTalentByDep(int depid, int subdep, ELUser eu)
			throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			// String email = "";
			String zhichengleibie = "";
			String renyuanleibie = "";
			String gangwei = "";
			String age = "";
			String sex = "";
			String peixunleibie = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
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
								.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST_SUB_ALL));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + zhichengleibie + "%");
				ps.setString(4, "%" + renyuanleibie + "%");
				ps.setString(5, "%" + gangwei + "%");
				ps.setString(6, "%" + age + "%");
				ps.setString(7, "%" + sex + "%");
				ps.setString(8, "%" + peixunleibie + "%");
				ps.setInt(9, dep.getLid());
				ps.setInt(10, dep.getRid());
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST_ALL));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setString(3, "%" + zhichengleibie + "%");
				ps.setString(4, "%" + renyuanleibie + "%");
				ps.setString(5, "%" + gangwei + "%");
				ps.setString(6, "%" + age + "%");
				ps.setString(7, "%" + sex + "%");
				ps.setString(8, "%" + peixunleibie + "%");
				ps.setInt(9, depid);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				// elUser.setXuehao(rs.getString(1));
				// elUser.setStudentno(rs.getString(2));
				// elUser.setDanweihao(rs.getString(3));
				elUser.setRealname(rs.getString(4));
				elUser.setUsername(rs.getString(5));
				elUser.setPassword(rs.getString(6));
				// elUser.setKuaijihao(rs.getString(7));
				// elUser.setRenyuanleibie(rs.getString(8));
				// elUser.setZhichengleibie(rs.getString(9));
				// elUser.setZhichengjibie(rs.getString(10));
				// elUser.setLianxifangshi(rs.getString(11));
				// elUser.setSex(rs.getString(12));
				// elUser.setMinzu(rs.getString(13));
				// elUser.setPeixunleibie(rs.getString(14));
				// elUser.setShifouzaizhi(rs.getString(15));
				// elUser.setSuozaigangwei(rs.getString(16));
				// elUser.setBiyeyuanxiao(rs.getString(17));
				// elUser.setBiyeshijian(rs.getDate(18));
				// elUser.setSuoxuezhuanye(rs.getString(19));
				// elUser.setXueli(rs.getString(20));
				// elUser.setXuewei(rs.getString(21));
				// elUser.setZhichenghao(rs.getString(22));
				// elUser.setZhiwupinrenriqi(rs.getDate(23));
				// elUser.setZhichengquderiqi(rs.getDate(24));
				// elUser.setBeizhu(rs.getString(25));
				// elUser.setHeadPhoto(rs.getString(26));
				//
				elUser.setId(rs.getInt(27));
				// elUser.setUsername(rs.getString(2));
				// elUser.setRealname(rs.getString(3));
				// elUser.setCompany(new Company(rs.getInt(7),
				// rs.getString(8)));
				elUser.setDepartment(new Department(rs.getInt(28), rs
						.getString(29)));
				// elUser.setCompany(new Company(3, SystemConfOp.getSecondDep(
				// elUser.getDepartment().getId()).getName()));

				// elUser.setXfscore(rs.getInt(6));
				// elUser.setQuizpassedper(rs.getInt(7));
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public List<ELUser> getStatUcreditUserByDep(int depid, int subdep,
			ELUser eu, int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
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
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_UCREDIT_USER_DEP_SUB));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
				ps.setInt(5, pageNow);
				ps.setInt(6, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_UCREDIT_USER_DEP));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setDepartment(new Department(rs.getInt(4), rs
						.getString(5)));
				// elUser.setCt_time(rs.getInt(6));
				// elUser.setXx_time(rs.getInt(7));
				// elUser.setCt_credit(rs.getInt(8));
				// elUser.setXx_credit(rs.getInt(9));
				// elUser.setXfscore(rs.getInt(10)) ;
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public int getStatUcreditUserByDepCount(int depid, int subdep, ELUser eu)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
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
						.prepareStatement("select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id "
								+ "where eu.username like ? and eu.realname like ? and dep.lid >=? and dep.rid<=?");

				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
			} else {
				ps = ct
						.prepareStatement("select count(*) from ELUSER eu left join DEPARTMENT"
								+ " dep on eu.depid = dep.id where eu.username like ? and eu.realname like ? and dep.id=?");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 的到 会员 相关学习信息
	 * 
	 */
	public void setStatUser(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection(); // 学分
			ps = ct
					.prepareStatement("select sum(sc.mycredit) from study_course sc where sc.userid = ? ");
			ps.setInt(1, elUser.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				elUser.setXx_credit(rs.getInt(1));
			}
			rs.close();
			rs = null; // 排行
			/*ps = ct
					.prepareStatement("select count(*) from eluser eu  where (select  sum(sc.mycredit) from study_course sc where sc.userid =  eu.id ) "
							+ "> ?");
			ps.setInt(1, elUser.getXfscore());
			rs = ps.executeQuery();
			if (rs.next()) {
				elUser.setXfph(rs.getInt(1) + 1);
			}*/
//			rs.close();
//			rs = null; // 课程学习数量
			ps = ct
					.prepareStatement("select count(*) from  study_course where userid = ?");

			ps.setInt(1, elUser.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				elUser.setCourseSize(rs.getInt(1));
			}
			rs.close();
			rs = null; // 班级学习数量
			//ps = ct.prepareStatement("select count(*) from study_class where userid =  ?");
			ps = ct.prepareStatement("select count(*) from elclass ec left join study_class sc on ec.id=sc.classid where ec.status!=9 and ec.isnormal=1 and userid=?");
			ps.setInt(1, elUser.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				elUser.setClassSize(rs.getInt(1));
			}
			rs.close();
			rs = null;
		} catch (Exception e) {
			logger.error("用户学分和排名！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public StatisticDaoImpl() {
	}

	public List<Offline> listStatOfflines(String name, Timestamp begintime,
			Timestamp endtime, int pageB, int pageE) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Offline> offlines = new ArrayList<Offline>();
		try {
			ct = DBConnection.getConnection();
			String con = " o.name like '%" + name + "%' ";
			if (begintime != null)
				con = con
						+ " and o.begintime >= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(begintime)
						+ "','yyyy-MM-dd HH24:mi:ss') ";// '" + begintime + "'
			// ";
			if (endtime != null)
				con = con
						+ " and o.endtime <= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(endtime) + "','yyyy-MM-dd HH24:mi:ss')";// '" +
			// endtime
			// + "'
			// ";
			// String sql = " select * from (select o.id,
			// o.name,o.description,o.during,o.xueshi,o.score,o.begintime,o.endtime,count(ou.userid)
			// oucount,row_number() over(order by o.begintime desc) rownum from
			// eloffline o left join eloffline2user ou on ou.offid = o.id group
			// by o.id,
			// o.name,o.description,o.during,o.xueshi,o.score,o.begintime,o.endtime
			// ) t where "
			// + con + " t.rownum between ? and ?";
			String sql = " select * from(select t.*,rownum rn from (select o.id, o.name,o.description,o.during,o.xueshi,o.score,o.begintime,o.endtime,count(ou.userid) oucount from eloffline o left join eloffline2user ou on ou.offid = o.id where "
					+ con
					+ " group by  o.id, o.name,o.description,o.during,o.xueshi,o.score,o.begintime,o.endtime  order by o.begintime desc)t where rownum<=?) where rn> =?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageB);
			ps.setInt(2, pageE);
			rs = ps.executeQuery();
			while (rs.next()) {
				Offline offline = new Offline();
				offline.setId(rs.getInt(1));
				offline.setName(rs.getString(2));
				offline.setDescription(rs.getString(3));
				offline.setDuring(rs.getInt(4));
				offline.setXueshi(rs.getInt(5));
				offline.setScore(rs.getInt(6));
				offline.setBegintime(rs.getTimestamp(7));
				offline.setEndtime(rs.getTimestamp(8));
				offline.setUsercount(rs.getInt(9));
				offlines.add(offline);
			}
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return offlines;
	}

	public int listStatOfflinesSize(String name, Timestamp begintime,
			Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			String con = " t.name like '%" + name + "%'";
			if (begintime != null)
				con = con
						+ " and  t.begintime >=to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(begintime)
						+ "','yyyy-MM-dd HH24:mi:ss') ";// '" + begintime + "'";
			if (endtime != null)
				con = con
						+ " and  t.endtime <= to_date('"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(endtime)
						+ "','yyyy-MM-dd HH24:mi:ss') ";// '" + endtime + "'";
			String sql = " select count( t.id) from eloffline t where " + con;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public Offline getStatOffline(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Offline offline = new Offline();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_OFFLINE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				offline.setId(rs.getInt(1));
				offline.setName(rs.getString(2));
				offline.setDescription(rs.getString(3));
				offline.setDuring(rs.getInt(4));
				offline.setXueshi(rs.getInt(5));
				offline.setScore(rs.getInt(6));
				offline.setBegintime(rs.getTimestamp(7));
				offline.setEndtime(rs.getTimestamp(8));
				offline.setUsercount(rs.getInt(9));
			}
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return offline;
	}

	public List<ELUser> listStatOffline2Users(int offid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> list = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_OFFLINE_USER_LIST));
			ps.setInt(1, offid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser e = new ELUser(rs.getInt(1), rs.getString(2));
				e.setUsername(rs.getString(3));
				e.setSex(rs.getString(4));
				e.setDepartment(new Department(rs.getInt(5), rs.getString(6)));
				list.add(e);
			}
		} catch (Exception e) {
			logger.error("添加新计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

//	//资源统计
//	public Resources getResourceStatistic()throws ElException{
//		//select (select count(c.id) from course c) as ccount,(select count(cl.id) from elclass cl) as clcount,(select count(q.id) from question q) as qcount,(select count(e.id) from exampaper e) as ecount,(select count(er.id) from exam_room er) as ercount,(select count(k.id) from knowledge k) as kcount,(select count(n.id) from news n) as ncount,(select count(f.id) from forum f) as fcount,(select count(c.id) from course c where c.status!=3 or c.status is null) as ccount_status,(select count(cl.id) from elclass cl where cl.status=1) as clcount_status ,(select count(er.id) from exam_room er where er.valid=1) as ercount_status,(select count(k.id) from knowledge k where k.valid=1) as kcount_status,(select count(n.id) from news n where n.status=3) as ncount_status,(select count(f.id) from forum f where f.valid=1) as fcount_status from dual;
//		Connection ct=null;
//		PreparedStatement ps=null;
//		ResultSet rs=null;
//		try{
//			ct=DBConnection.getConnection();
//			ps=ct.prepareStatement("select (select count(c.id) from course c) as ccount,(select count(cl.id) from elclass cl) as clcount,(select count(q.id) from question q) as qcount,(select count(e.id) from exampaper e) as ecount,(select count(er.id) from exam_room er) as ercount,(select count(k.id) from knowledge k) as kcount,(select count(n.id) from news n) as ncount,(select count(f.id) from forum f) as fcount,(select count(c.id) from course c where c.status!=2 or c.status is not null) as ccount_status,(select count(cl.id) from elclass cl where cl.status=1) as clcount_status ,(select count(er.id) from exam_room er where er.valid=1) as ercount_status,(select count(k.id) from knowledge k where k.valid=1) as kcount_status,(select count(n.id) from news n where n.status=3) as ncount_status,(select count(f.id) from forum f where f.valid=1) as fcount_status from dual");
//			rs=ps.executeQuery();
//			if(rs.next()){
//				Resources resources=new Resources();
//				resources.setCcount(rs.getInt(1));
//				resources.setElcount(rs.getInt(2));
//				resources.setQcount(rs.getInt(3));
//				resources.setEcount(rs.getInt(4));
//				resources.setErcount(rs.getInt(5));
//				resources.setKcount(rs.getInt(6));
//				resources.setNcount(rs.getInt(7));
//				resources.setFcount(rs.getInt(8));
//				resources.setCcount_status(rs.getInt(9));
//				resources.setElcount_status(rs.getInt(10));
//				resources.setErcount_status(rs.getInt(11));
//				resources.setKcount_status(rs.getInt(12));
//				resources.setNcount_status(rs.getInt(13));
//				resources.setFcount_status(rs.getInt(14));
//				return resources;
//			}
//		}catch(Exception e){
//			logger.error("添加新计划失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return null;
//	}
	
	/**
	 * 人才搜索。。
	 * 
	 * @param depid
	 * @param subdep
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStatTalentByDep(int depid, int subdep, ELUser eu,int elclassid,
			int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sqlwhere = ""; 
			String sqlelclass = ""; 
			if (null != eu) {
				 if (null != eu.getUsername() && !eu.getUsername().equals(""))
					sqlwhere = sqlwhere + " and eu.username like '%"+eu.getUsername().trim()+"%' "; 
				 if (null != eu.getRealname() && !eu.getRealname().equals(""))
					sqlwhere = sqlwhere + " and eu.realname like '%"+eu.getRealname().trim()+"%' ";  
				 if (null != eu.getZhichengleibie() && !eu.getZhichengleibie().equals(""))
					sqlwhere = sqlwhere + " and eu.zhichengleibie like '%"+eu.getZhichengleibie().trim()+"%' ";   
				 if (null != eu.getRenyuanleibie() && !eu.getRenyuanleibie().equals(""))
					sqlwhere = sqlwhere + " and eu.renyuanleibie like '%"+eu.getRenyuanleibie().trim()+"%' ";   
				 if (null != eu.getGangwei() && !eu.getGangwei().equals(""))
					sqlwhere = sqlwhere + " and eu.gangwei like '%"+eu.getGangwei().trim()+"%' ";   
				 if (null != eu.getSex() && !eu.getSex().equals("")) 
					sqlwhere = sqlwhere + " and eu.sex like '%"+eu.getSex().trim()+"%' ";   
				 if (null != eu.getPeixunleibie() && !eu.getPeixunleibie().equals(""))
					sqlwhere = sqlwhere + " and eu.peixunleibie like '%"+eu.getPeixunleibie().trim()+"%' ";    
				 if (0 != eu.getAge())
						sqlwhere = sqlwhere + " and (select to_char(sysdate, 'yyyy') - to_char(t1.SHENGRI, 'yyyy') as age " +
								"from eluser t1 where eu.id = t1.id) > "+eu.getAge();    
				 if (0 != eu.getAge_())
						sqlwhere = sqlwhere + " and (select to_char(sysdate, 'yyyy') - to_char(t2.SHENGRI, 'yyyy') as age " +
								"from eluser t2 where eu.id = t2.id) < "+eu.getAge_();   
			}
			if(elclassid != 0){ 
				if ( null != eu && null != eu.getMajor()) {
						if(eu.getMajor().trim().equals("1")){
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" and certificateno is not null)";
						} else 
						if(eu.getMajor().trim().equals("0")){ 
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" and certificateno is  null)";
						}else
						if(eu.getMajor().equals("")){
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" )";
						}
//					sqlwhere = sqlwhere + " and (select count(*) from study_class scl where  scl.classid ="+elclassid+" and scl.userid = eu.id ) = "+eu.getMajor().trim();
				}
				sqlelclass = " ,(select  certificateno from study_class scl where  scl.classid ="+elclassid+" and scl.userid = eu.id ) ispassed  ";
				//以前的代码 sqlelclass = " ,(select count(1) from study_class scl where  scl.classid ="+elclassid+" and scl.userid = eu.id ) ispassed ";
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
				
				String sql = "select * from (select t.*, rownum rn from ( " +
						"select xuehao,studentno,danweihao,realname,username,password,kuaijihao,renyuanleibie,zhichengleibie,zhichengjibie,lianxifangshi, sex,minzu," +
						"peixunleibie,shifouzaizhi,gangwei,school,biyeshijian,specialty, xueli,xuewei,zhichenghao,zhiwupinrenriqi,zhichengquderiqi,beizhu," +
						"headphoto ,eu.id euid,dep.id depid,dep.name,shenfenzheng,age,shengri " +sqlelclass+
						"from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id  " +
						"where  dep.lid >=? and dep.rid<=? " +
						"" +sqlwhere+
						" order by dep.id asc" +
						")t where rownum <= ? ) where rn>=?";
				ps = ct.prepareStatement(sql);
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST_SUB)); 
				ps.setInt(1, dep.getLid());
				ps.setInt(2, dep.getRid());
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			} else {
				String sql = "select * from (select t.*, rownum rn from ( " +
							"select xuehao,studentno,danweihao,realname,username,password,kuaijihao,renyuanleibie,zhichengleibie,zhichengjibie,lianxifangshi, sex,minzu," +
							"peixunleibie,shifouzaizhi,gangwei,school,biyeshijian,specialty, xueli,xuewei,zhichenghao,zhiwupinrenriqi,zhichengquderiqi,beizhu," +
							"headphoto ,eu.id euid,dep.id depid,dep.name,shenfenzheng,age,shengri " +sqlelclass+
							"from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id  " +
							"where  dep.id = ? " +
							"" +sqlwhere+
							" order by dep.id asc" +
							")t where rownum <= ? ) where rn>=?";
				ps = ct.prepareStatement(sql);
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST)); 
				ps.setInt(1, depid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				 elUser.setXuehao(rs.getString(1));
				 elUser.setStudentno(rs.getString(2));
				 elUser.setDanweihao(rs.getString(3));
				 elUser.setRealname(rs.getString(4));
				 elUser.setUsername(rs.getString(5));
				 elUser.setPassword(rs.getString(6));
				 elUser.setKuaijihao(rs.getString(7));
				 elUser.setRenyuanleibie(rs.getString(8));
				 elUser.setZhichengleibie(rs.getString(9));
				 elUser.setZhichengjibie(rs.getString(10));
				 elUser.setLianxifangshi(rs.getString(11));
				 elUser.setSex(rs.getString(12));
				 elUser.setMinzu(rs.getString(13));
				 elUser.setPeixunleibie(rs.getString(14));
				 elUser.setShifouzaizhi(rs.getString(15));
				 elUser.setGangwei(rs.getString(16));
				 elUser.setSchool(rs.getString(17));
				 elUser.setBiyeshijian(rs.getDate(18));
				 elUser.setSpecialty(rs.getString(19));
				 elUser.setXueli(rs.getString(20));
				 elUser.setXuewei(rs.getString(21));
				 elUser.setZhichenghao(rs.getString(22));
				 elUser.setZhiwupinrenriqi(rs.getDate(23));
				 elUser.setZhichengquderiqi(rs.getDate(24));
				 elUser.setBeizhu(rs.getString(25));
				 elUser.setHeadPhoto(rs.getString(26));

				elUser.setId(rs.getInt(27));
				elUser.setDepartment(new Department(rs.getInt(28), rs
						.getString(29)));
			//	 elUser.setCompany(new Company(3, SystemConfOp.getSecondDep( 
			//	 elUser.getDepartment().getId()).getName()));

//				 elUser.setXfscore(rs.getInt(6));
//				 elUser.setQuizpassedper(rs.getInt(7));
				 elUser.setShenfenzheng(rs.getString("shenfenzheng"));
				 elUser.setAge(rs.getInt("age"));
				 elUser.setShengri(rs.getDate("shengri"));
				 if(elclassid != 0){
					 if(rs.getInt("ispassed")==0){
						 elUser.setIspassed(0);
					 }else{
						 elUser.setIspassed(1);
					 }
					 
				 }else { 
					 elUser.setIspassed(-1);
				}
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}
	public int getStatTalentByDepSize(int depid, int subdep, ELUser eu,int elclassid)
	throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			try {
				String sqlwhere = "";  
				if (null != eu) {
					if (null != eu.getUsername() && !eu.getUsername().equals(""))
						sqlwhere = sqlwhere + " and eu.username like '%"+eu.getUsername().trim()+"%' "; 
					if (null != eu.getRealname() && !eu.getRealname().equals(""))
						sqlwhere = sqlwhere + " and eu.realname like '%"+eu.getRealname().trim()+"%' ";  
					 if (null != eu.getZhichengleibie() && !eu.getZhichengleibie().equals(""))
						sqlwhere = sqlwhere + " and eu.zhichengleibie like '%"+eu.getZhichengleibie().trim()+"%' ";   
					 if (null != eu.getRenyuanleibie() && !eu.getRenyuanleibie().equals(""))
						sqlwhere = sqlwhere + " and eu.renyuanleibie like '%"+eu.getRenyuanleibie().trim()+"%' ";   
					 if (null != eu.getSuozaigangwei() && !eu.getSuozaigangwei().equals(""))
						sqlwhere = sqlwhere + " and eu.gangwei like '%"+eu.getGangwei().trim()+"%' ";   
					 if (null != eu.getSex() && !eu.getSex().equals("")) 
						sqlwhere = sqlwhere + " and eu.sex like '%"+eu.getSex().trim()+"%' ";   
					 if (null != eu.getPeixunleibie() && !eu.getPeixunleibie().equals(""))
						sqlwhere = sqlwhere + " and eu.peixunleibie like '%"+eu.getPeixunleibie().trim()+"%' ";    
					 if (0 != eu.getAge())
							sqlwhere = sqlwhere + " and (select to_char(sysdate, 'yyyy') - to_char(t1.SHENGRI, 'yyyy') as age " +
									"from eluser t1 where eu.id = t1.id) > "+eu.getAge();    
					 if (0 != eu.getAge_())
							sqlwhere = sqlwhere + " and (select to_char(sysdate, 'yyyy') - to_char(t2.SHENGRI, 'yyyy') as age " +
									"from eluser t2 where eu.id = t2.id) < "+eu.getAge_();   
				}
				if(elclassid != 0){ 
					if ( null != eu && null != eu.getMajor()) {
						if(eu.getMajor().trim().equals("1")){
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" and certificateno is not null)";
						} else 
						if(eu.getMajor().trim().equals("0")){ 
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" and certificateno is  null)";
						}else
						if(eu.getMajor().equals("")){
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" )";
						}
			//			sqlwhere = sqlwhere + "  and (select count(*) from study_class scl where  scl.classid ="+elclassid+" and scl.userid = eu.id ) = "+eu.getMajor().trim();
					} 
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
					String sql = "select count(*)"+
					"from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id  " +
					"where  dep.lid >=? and dep.rid<=? " +
					"" +sqlwhere ;
					ps = ct.prepareStatement(sql); 
					ps.setInt(1, dep.getLid());
					ps.setInt(2, dep.getRid());
				} else {
			
					String sql = "select count(*)"+
					"from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id  " +
					"where  dep.id =? " +
					"" +sqlwhere ;
					ps = ct.prepareStatement(sql); 
					ps.setInt(1, depid); 
				}
				rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			} catch (Exception e) {
				logger.error("用户列表搜索失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return 0;
		}
	
	/**
	 * 人才搜索。。
	 * 
	 * @param depid
	 * @param subdep
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStatTalentByDep(int depid, int subdep, ELUser eu,int elclassid) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sqlwhere = ""; 
			String sqlelclass = ""; 
			if (null != eu) {
				 if (null != eu.getUsername() && !eu.getUsername().equals(""))
					sqlwhere = sqlwhere + " and eu.username like '%"+eu.getUsername().trim()+"%' "; 
				 if (null != eu.getRealname() && !eu.getRealname().equals(""))
					sqlwhere = sqlwhere + " and eu.realname like '%"+eu.getRealname().trim()+"%' ";  
				 if (null != eu.getZhichengleibie() && !eu.getZhichengleibie().equals(""))
					sqlwhere = sqlwhere + " and eu.zhichengleibie like '%"+eu.getZhichengleibie().trim()+"%' ";   
				 if (null != eu.getRenyuanleibie() && !eu.getRenyuanleibie().equals(""))
					sqlwhere = sqlwhere + " and eu.renyuanleibie like '%"+eu.getRenyuanleibie().trim()+"%' ";   
				 if (null != eu.getSuozaigangwei() && !eu.getSuozaigangwei().equals(""))
					sqlwhere = sqlwhere + " and eu.gangwei like '%"+eu.getGangwei().trim()+"%' ";   
				 if (null != eu.getSex() && !eu.getSex().equals("")) 
					sqlwhere = sqlwhere + " and eu.sex like '%"+eu.getSex().trim()+"%' ";   
				 if (null != eu.getPeixunleibie() && !eu.getPeixunleibie().equals(""))
					sqlwhere = sqlwhere + " and eu.peixunleibie like '%"+eu.getPeixunleibie().trim()+"%' ";    
				 if (0 != eu.getAge())
						sqlwhere = sqlwhere + " and (select to_char(sysdate, 'yyyy') - to_char(t1.SHENGRI, 'yyyy') as age " +
								"from eluser t1 where eu.id = t1.id) > "+eu.getAge();    
				 if (0 != eu.getAge_())
						sqlwhere = sqlwhere + " and (select to_char(sysdate, 'yyyy') - to_char(t2.SHENGRI, 'yyyy') as age " +
								"from eluser t2 where eu.id = t2.id) < "+eu.getAge_();   
			}
			if(elclassid != 0){ 
				if ( null != eu && null != eu.getMajor()) {
						if(eu.getMajor().trim().equals("1")){
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" and certificateno is not null)";
						} else 
						if(eu.getMajor().trim().equals("0")){ 
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" and certificateno is  null)";
						}else
						if(eu.getMajor().equals("")){
							sqlwhere = sqlwhere + " and eu.id in(select ca.userid from study_class ca where ca.classid ="+elclassid+" )";
						}
//					sqlwhere = sqlwhere + " and (select count(*) from study_class scl where  scl.classid ="+elclassid+" and scl.userid = eu.id ) = "+eu.getMajor().trim();
				}
				sqlelclass = " ,(select certificateno from study_class scl where  scl.classid ="+elclassid+" and scl.userid = eu.id ) ispassed ";
				//以前的代码 sqlelclass = " ,(select count(*) from study_class scl where  scl.classid ="+elclassid+" and scl.userid = eu.id ) ispassed ";
				
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
				
				String sql = " " +
						"select xuehao,studentno,danweihao,realname,username,password,kuaijihao,renyuanleibie,zhichengleibie,zhichengjibie,lianxifangshi, sex,minzu," +
						"peixunleibie,shifouzaizhi,gangwei,school,biyeshijian,specialty, xueli,xuewei,zhichenghao,zhiwupinrenriqi,zhichengquderiqi,beizhu," +
						"headphoto ,eu.id euid,dep.id depid,dep.name,shenfenzheng,age,shengri " +sqlelclass+
						"from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id  " +
						"where  dep.lid >=? and dep.rid<=? " +
						"" +sqlwhere+
						" order by dep.id asc" +
						" ";
				ps = ct.prepareStatement(sql);
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(StatisticConstants.STAT_TALENT_DEP_LIST_SUB)); 
				ps.setInt(1, dep.getLid());
				ps.setInt(2, dep.getRid()); 
			} else {
				String sql = "  " +
							"select xuehao,studentno,danweihao,realname,username,password,kuaijihao,renyuanleibie,zhichengleibie,zhichengjibie,lianxifangshi, sex,minzu," +
							"peixunleibie,shifouzaizhi,gangwei,school,biyeshijian,specialty, xueli,xuewei,zhichenghao,zhiwupinrenriqi,zhichengquderiqi,beizhu," +
							"headphoto ,eu.id euid,dep.id depid,dep.name,shenfenzheng,age,shengri " +sqlelclass+
							"from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id  " +
							"where  dep.id = ? " +
							"" +sqlwhere+
							" order by dep.id asc" +
							" ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, depid); 
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				 elUser.setXuehao(rs.getString(1));
				 elUser.setStudentno(rs.getString(2));
				 elUser.setDanweihao(rs.getString(3));
				 elUser.setRealname(rs.getString(4));
				 elUser.setUsername(rs.getString(5));
				 elUser.setPassword(rs.getString(6));
				 elUser.setKuaijihao(rs.getString(7));
				 elUser.setRenyuanleibie(rs.getString(8));
				 elUser.setZhichengleibie(rs.getString(9));
				 elUser.setZhichengjibie(rs.getString(10));
				 elUser.setLianxifangshi(rs.getString(11));
				 elUser.setSex(rs.getString(12));
				 elUser.setMinzu(rs.getString(13));
				 elUser.setPeixunleibie(rs.getString(14));
				 elUser.setShifouzaizhi(rs.getString(15));
				 elUser.setGangwei(rs.getString(16));
				 elUser.setSchool(rs.getString(17));
				 elUser.setBiyeshijian(rs.getDate(18));
				 elUser.setSpecialty(rs.getString(19));
				 elUser.setXueli(rs.getString(20));
				 elUser.setXuewei(rs.getString(21));
				 elUser.setZhichenghao(rs.getString(22));
				 elUser.setZhiwupinrenriqi(rs.getDate(23));
				 elUser.setZhichengquderiqi(rs.getDate(24));
				 elUser.setBeizhu(rs.getString(25));
				 elUser.setHeadPhoto(rs.getString(26));

				elUser.setId(rs.getInt(27));
				elUser.setDepartment(new Department(rs.getInt(28), rs
						.getString(29)));
			//	 elUser.setCompany(new Company(3, SystemConfOp.getSecondDep( 
			//	 elUser.getDepartment().getId()).getName()));

//				 elUser.setXfscore(rs.getInt(6));
//				 elUser.setQuizpassedper(rs.getInt(7));
				 elUser.setShenfenzheng(rs.getString("shenfenzheng"));
				 elUser.setAge(rs.getInt("age"));
				 elUser.setShengri(rs.getDate("shengri"));
				 if(elclassid != 0){
					 if(rs.getInt("ispassed")==0){
						 elUser.setIspassed(0);
					 }else{
						 elUser.setIspassed(1);
					 }
					 
				 }else { 
					 elUser.setIspassed(-1);
				}
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}
	
	
}
