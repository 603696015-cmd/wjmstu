package com.sopia.statman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.batchman.entities.Batch;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.dao.impl.ClassDaoImpl;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.StringUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.statman.StatisticConstants;
import com.sopia.statman.dao.StatisticClassDao;
import com.sopia.studyman.dao.StudyClassDao;

/**
 * Description: 培训班统计 数据处理实现 Copyright (c) Department of Research and
 * Development/wenyishun110@163.com. All Rights Reserved.
 * 
 * @version 1.0 2011-9-4 上午12:27:05 by 闻益舜（wenyishun110@163.com）创建
 */
public class StatisticClassDaoImpl implements StatisticClassDao {
	private static final Log logger = LogFactory
			.getLog(StatisticClassDaoImpl.class);

	public List<ElClass> listClassByDepid(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> clazzes = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_DEP_CLASS_LIST));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setOptionalcredit(rs.getInt(5));
				c.setCreatetime(rs.getTimestamp(6));
				c.setBxCount(rs.getInt(7));
				c.setXxCount(rs.getInt(8));
				c.setXxCredit(rs.getInt(9));
				clazzes.add(c);
			}
		} catch (Exception e) {
			logger.error("获取部门课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return clazzes;
	}

	public List<ElClass> listAllClass() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> clazzes = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select cl.id ,cl.name,euc.id,euc.realname,optionalcredit,createtime"
							+
							// ",(select count(*) from class_course ccb where
							// ccb.status = 0 and ccb.classid=cl.id) as bxCount
							// ," +
							// "(select count(*) from class_course ccx where
							// ccx.status = 1 and ccx.classid=cl.id) as ccxCount
							// ," +
							// "(select sum(ccx1.credit) from class_course ccx1
							// where ccx1.status=1 and ccx1.classid =cl.id ) as
							// ccxCredit" +
							" from elclass cl,eluser euc where cl.creater = euc.id  ");
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setOptionalcredit(rs.getInt(5));
				c.setCreatetime(rs.getTimestamp(6));
				// c.setBxCount(rs.getInt(7));
				// c.setXxCount(rs.getInt(8));
				// c.setXxCredit(rs.getInt(9));
				clazzes.add(c);
			}
		} catch (Exception e) {
			logger.error("获取部门课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return clazzes;
	}

	// public int courseByDepidSize(int depid) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(StatisticConstants.STAT_DEP_COURSE_SIZE));
	// ps.setInt(1, depid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("获取部门课程数量出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }

	// public int userCountByDepid(int depid) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(StatisticConstants.STAT_DEP_USER_SIZE));
	// ps.setInt(1, depid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("获取部门用户数量出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }

	/*
	 * public List<Course> listAllCourse(String name) throws ElException {
	 * PreparedStatement ps = null; ResultSet rs = null; Connection ct = null;
	 * List<Course> cs = new ArrayList<Course>(); try { ct =
	 * DBConnection.getConnection(); ps = ct.prepareStatement(ElQuerySql
	 * .getSQL(StatisticConstants.STAT_COURSE_LIST)); ps.setString(1, "%" + name +
	 * "%"); rs = ps.executeQuery(); while (rs.next()) { Course c = new
	 * Course(rs.getInt(1), rs.getString(2));
	 * c.setCreatetime(rs.getTimestamp(4)); c.setCtype(new
	 * CourseType(rs.getInt(3), rs.getString(5))); cs.add(c); } } catch
	 * (Exception e) { logger.error("查看全部的课程出错！", e); throw new ElException(e); }
	 * finally { DBConnection.closeConnectInfo(ct, ps, rs); } return cs; }
	 */

	public List<ElClass> listClassByCreater(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_CLASS_BYCREATER));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getDate(3));
				c.setBxCount(rs.getInt(4));
				c.setXxCount(rs.getInt(5));
				c.setXxCredit(6);
				c.setOptionalcredit(7);
				c.setUserCount(rs.getInt(8));
				c.setUserPassedCount(rs.getInt(9));
				cls.add(c);
			}
		} catch (Exception e) {
			logger.error("用户开课列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 部门学员学习排行榜
	 */
	public List<ELUser> listClassView(int depid, int classid, int pageNow,
			int pageSize) throws ElException {
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
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_CLASS_VIEW));
			ps.setInt(1, classid);
			ps.setInt(2, lid);
			ps.setInt(3, rid);

			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				// eu.setStudentno(rs.getString(3));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				// eu.setXfscore(rs.getInt(8));
				// eu.setCompany(new Company(0, SystemConfOp.getSecondDep(
				// eu.getDepartment().getId()).getName()));
				// if (classGraduate(eu.getId(), classid)) {
				// graduateClassApplay(eu.getId(), classid);
				// } else {
				// graduateClassDelete(eu.getId(), classid);
				// }
				// eu.setGraddate(rs.getDate(9));
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

	public int listClassViewSize(int depid, int classid) throws ElException {
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
					.getSQL(StatisticConstants.STAT_CLASS_VIEW_SIZE));
			// ps.setInt(1, classid);
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

	public List<ElClass> listClassByGlobal(int gid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select cl.id,cl.name,cl.createtime,"
							+ "(select count(*) from study_class ca where ca.classid = cl.id and ca.status = 2),"
							+ "(select count(*) from study_class scl where scl.classid = cl.id) as passedcount  from elclass cl where cl.global = 1 order by cl.createtime desc");
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getDate(3));
				c.setUserCount(rs.getInt(4));
				c.setUserPassedCount(rs.getInt(5));
				cls.add(c);
			}
		} catch (Exception e) {
			logger.error("用户开课列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<Department> listDepPassPer(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {

			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select group1,group2 from elclass where id = ? ");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			int group1 = 0;
			// int group2 = 0;
			if (rs.next()) {
				group1 = rs.getInt(1);
				// group2 = rs.getInt(2);
			}
			// ps = ct
			// .prepareStatement("select dep.id,dep.name,"
			// + "(select count(*) from class_apply ca,eluser eu,department dep1
			// where ca.classid =? "
			// + "and eu.id= ca.userid and eu.depid = dep1.id and
			// dep1.lid>=dep.lid and dep1.rid<=dep.rid and eu.id not in (select
			// userid from elgroup2user where gid = ?) ) as stucount, "
			// + "(select count(*) from study_class ca,eluser eu,department dep1
			// where ca.classid =? "
			// + "and eu.id= ca.userid and eu.depid = dep1.id and
			// dep1.lid>=dep.lid and dep1.rid<=dep.rid and eu.id not in (select
			// userid from elgroup2user where gid = ?) ) as passcount "
			// + "from department dep where dep.parentid = 1 and dep.id not
			// in(420 ,419) order by passcount/stucount,stucount desc");
			ps = ct
					.prepareStatement("select dep.id,dep.name, count(eu .id) stucount  , count(ca.userid) passcount  from department dep "
							+ "left outer join  department dep1 on dep1.lid>=dep.lid and dep1.rid<=dep.rid "
							+ "left outer join  eluser eu  on eu .depid = dep1.id	"
							+ "left outer join (select * from  study_class where classid =? ) ca on ca.userid = eu .id and eu.id not in (select userid from elgroup2user where gid = ?) "
							+ "where dep.parentid = 1  and dep.id not in(420 ,419)  group by dep.id,dep.name order by cast( count(ca.userid)as  decimal)/cast( count(eu .id)as  decimal) desc");
			// ps.setInt(1, classid);
			// ps.setInt(2, group1);
			ps.setInt(1, classid);
			ps.setInt(2, group1);
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = new Department(rs.getInt(1), rs.getString(2));
				if (dep.getId() == 54 || dep.getId() == 187
						|| dep.getId() == 369)
					dep.setName(dep.getName() + "(试点)");
				dep.setUserCount(rs.getInt(3));
				dep.setUserCredit(rs.getInt(4));
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("单位通过率排行！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<ElClass> listElclassStateByName(String name, int type,
			int pagenow, int pagesize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from (select cl.id,cl.name,cl.createtime,(select count(*) from study_class ca where ca.classid = cl.id and ca.status = 2),"
							+ "(select count(*) from study_class scl where scl.classid = cl.id) as passedcount  "
							+ "from elclass cl where cl.name like ? and cl.cltype = ? and cl.status = 1 order by cl.createtime desc) t where rownum <=? ) where rn >=?");
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, type);
			ps.setInt(3, pagenow);
			ps.setInt(4, pagesize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getDate(3));
				c.setUserCount(rs.getInt(4));
				c.setUserPassedCount(rs.getInt(5));
				cls.add(c);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<ElClass> listElclassStateByName(String name,
			ElClType cltypeTree, int type, int pagenow, int pagesize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from (select cl.id,cl.name,cl.createtime,(select count(*) from study_class ca where ca.classid = cl.id and ca.status = 2),"
							+ "(select count(*) from study_class scl where scl.classid = cl.id) as passedcount  "
							+ "from elclass cl ,elclasstype clt where cl.cltype = clt.id and  cl.name like ? and clt.id in("
							+ createPerTypeId(cltypeTree, type)
							+ ")  order by cl.createtime desc) t where rownum <=? ) where rn >=?");
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, pagenow);
			ps.setInt(3, pagesize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getDate(3));
				c.setUserPassedCount(rs.getInt(4));
				c.setUserCount(rs.getInt(5));
				cls.add(c);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<ElClass> listElclassStateByName(String name,
			ElClType cltypeTree, int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select cl.id,cl.name,cl.createtime,(select count(*) from study_class ca where ca.classid = cl.id and ca.status = 2),"
							+ "(select count(*) from study_class scl where scl.classid = cl.id) as passedcount  "
							+ "from elclass cl ,elclasstype clt where cl.cltype = clt.id and  cl.name like ? and clt.id in("
							+ createPerTypeId(cltypeTree, type)
							+ ")   order by cl.createtime desc");
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getDate(3));
				c.setUserPassedCount(rs.getInt(4));
				c.setUserCount(rs.getInt(5));
				cls.add(c);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	// public List<ElClass> listElclassStateByName(String name, ElClType
	// cltypeTree,int[] types,
	// int pagenow, int pagesize) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<ElClass> cls = new ArrayList<ElClass>();
	// try {
	// ct = DBConnection.getConnection();
	// if(types!=null){
	// for(int i=0;i<types.length;i++){
	// ps = ct.prepareStatement("select * from (select t.*,rownum rn from
	// (select cl.id,cl.name,cl.createtime,(select count(*) from study_class ca
	// where ca.classid = cl.id and ca.status = 2)," +
	// "(select count(*) from study_class scl where scl.classid = cl.id) as
	// passedcount " +
	// "from elclass cl where cl.name like ? and cl.cltype in
	// ("+createPerTypeId(cltypeTree,types[i])+") order by cl.createtime desc) t
	// where rownum <=? ) where rn >=?");
	// ps.setString(1,"%"+name+"%");
	// ps.setInt(2, pagenow);
	// ps.setInt(3, pagesize);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
	// c.setCreatetime(rs.getDate(3));
	// c.setUserPassedCount(rs.getInt(4));
	// c.setUserCount(rs.getInt(5));
	// cls.add(c);
	// }
	// }
	// }
	// } catch (Exception e) {
	// logger.error("获取统计培训批次中的培训班！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return cls;
	// }
	/**
	 * 培训班统计（获取培训班列表）
	 */
	public List<ElClass> listElclassStateByName(String name,
			ElClType cltypeTree, int pagenow, int pagesize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*,rownum rn from (select cl.id,cl.name,cl.createtime,scl.passcount,scll.usercount"
					+ " from elclass cl left join "
					+ " (select sc.classid,count(*) passcount from study_class sc left join elclass cl on sc.classid = cl.id where sc.certificateno is not null group by sc.classid) scl on scl.classid=cl.id "
					+ " left join (select scl.classid,count(*) usercount from study_class scl left join elclass cl on scl.classid = cl.id group by scl.classid) scll on scll.classid=cl.id "
					+ " inner join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("elclasstype", cltypeTree, true)
					+ ") clt on clt.id=cl.cltype where cl.name like ? "
					+ " order by cl.createtime desc) t where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%" + StringUtil.toLikeStr(name) + "%");
			ps.setInt(2, pagenow);
			ps.setInt(3, pagesize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getDate(3));
				c.setUserPassedCount(rs.getInt(4));
				c.setUserCount(rs.getInt(5));
				cls.add(c);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}
	
	
	
	/**
	 * 岗位培训班统计（获取培训班列表）
	 */
	public List<ElClass> listElclassStateByName22(String name,
			Station cltypeTree, int pagenow, int pagesize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*,rownum rn from (select cl.id,cl.name,cl.createtime,scl.passcount,scll.usercount"
					+ " from elclass cl left join "
					+ " (select sc.classid,count(*) passcount from study_class sc left join elclass cl on sc.classid = cl.id where sc.certificateno is not null group by sc.classid) scl on scl.classid=cl.id "
					+ " left join (select scl.classid,count(*) usercount from study_class scl left join elclass cl on scl.classid = cl.id group by scl.classid) scll on scll.classid=cl.id "
					+ " left join (select st.classid,count(*) classcount from station st left join elclass cl on st.classid=cl.id group by st.classid) sclll on sclll.classid=cl.id "
					+ " inner join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("station", cltypeTree, true)
					+ ") clt on clt.id=cl.cltype where cl.name like ? "
					+ " order by cl.createtime desc) t where rownum <=? ) where rn >=?";
			ps = ct.prepareStatement(sql);
			System.out.println(((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("station", cltypeTree, true));
			ps.setString(1, "%" + StringUtil.toLikeStr(name) + "%");
			ps.setInt(2, pagenow);
			ps.setInt(3, pagesize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getDate(3));
				c.setUserPassedCount(rs.getInt(4));
				c.setUserCount(rs.getInt(5));
				cls.add(c);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<ElClass> listElclassStateByName(String name,
			ElClType cltypeTree, int[] types) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			if (types != null) {
				for (int i = 0; i < types.length; i++) {
					ps = ct
							.prepareStatement("select cl.id,cl.name,cl.createtime,(select count(*) from study_class ca where ca.classid = cl.id and ca.status = 2),"
									+ "(select count(*) from study_class scl where scl.classid = cl.id) as passedcount  "
									+ "from elclass cl where cl.name like ? and cl.cltype in ("
									+ createPerTypeId(cltypeTree, types[i])
									+ ")  order by cl.createtime desc");
					ps.setString(1, "%" + name + "%");
					rs = ps.executeQuery();
					while (rs.next()) {
						ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
						c.setCreatetime(rs.getDate(3));
						c.setUserPassedCount(rs.getInt(4));
						c.setUserCount(rs.getInt(5));
						cls.add(c);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	// public int listElclassStateByNamesize(String name,ElClType cltypeTree,
	// int[] types)
	// throws ElException {
	//
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// int s = 0 ;
	// try {
	// if(types!=null){
	// for(int i=0;i<types.length;i++){
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement("select count(cl.id) from elclass cl where
	// cl.name like ? and cl.cltype in
	// ("+createPerTypeId(cltypeTree,types[i])+") ");
	// ps.setString(1,"%"+name+"%");
	// //ps.setInt(2, type);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// s += rs.getInt(1);
	// }
	// }
	// }
	// } catch (Exception e) {
	// logger.error("获取统计培训批次中的培训班！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return s;
	// }
	/**
	 * 培训班统计（获取培训班列表数量）
	 * 
	 * @param name
	 * @param cltypeTree
	 * @return
	 * @throws ElException
	 */
	public int listElclassStateByNamesize(String name, ElClType cltypeTree)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(cl.id) from elclass cl  inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("elclasstype",
											cltypeTree, true)
							+ ") clt on clt.id=cl.cltype where cl.name like ? ");
			ps.setString(1, "%" + StringUtil.toLikeStr(name) + "%");
			// ps.setInt(2, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	
	
	
	
	/**
	 * 课程培训班统计（获取培训班列表数量）
	 * 
	 * @param name
	 * @param cltypeTree
	 * @return
	 * @throws ElException
	 */
	public int listElclassStateByNamesize22(String name, Station cltypeTree)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(cl.id) from elclass cl left join(select st.classid,count(*) classcount from station st left join elclass cl on st.classid=cl.id group by st.classid) scl on scl.classid=cl.id inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("station",
											cltypeTree, true)
							+ ") clt on cl.id=scl.classid where cl.name like ? group by cl.id");
			ps.setString(1, "%" + StringUtil.toLikeStr(name) + "%");
			System.out.println(((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("station",cltypeTree, true));
			// ps.setInt(2, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	

	public int listElclassStateByNamesize(String name, int type)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(cl.id) from elclass cl where cl.name like ? and cl.cltype = ? and cl.status = 1");
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	public int listElclassStateByNamesize(String name, ElClType cltypeTree,
			int type) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(cl.id) from elclass cl ,elclasstype clt where cl.cltype = clt.id and cl.name like ?  and clt.id in("
							+ createPerTypeId(cltypeTree, type) + ") ");
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				s = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	public StatisticClassDaoImpl() {
	}

	private String createPerTypeId(ElClType ctypeTree, int ctid) {
		if (ctypeTree.getId() != ctid) {
			ctypeTree = getElClTypeById(ctypeTree.getChild(), ctid);
		}
		if (ctypeTree == null) {
			return "0";
		}
		if (ctypeTree.getChild() != null) {
			return createTypeId(ctypeTree.getChild(), ctypeTree.getId());
		}
		return String.valueOf(ctypeTree.getId());
	}

	private ElClType getElClTypeById(List<ElClType> listType, int ctid) {
		ElClType ctypeTree = null;
		for (ElClType type : listType) {
			if (type.getId() != ctid) {
				ctypeTree = getElClTypeById(type.getChild(), ctid);
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

	private String createTypeId(List<ElClType> listType, int id) {
		String ids = id + "";
		for (ElClType type : listType) {
			ids = ids + "," + createTypeId(type.getChild(), type.getId());
		}
		return ids;
	}
	
	public List<Batch> listBatchs(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Batch> list = new ArrayList<Batch>();
		try {
			ct = DBConnection.getConnection();
			if (userid > 0) {
				ps = ct
						.prepareStatement("select * from (select t.*, rownum rn from (select b.id, b.name, b.description,scs.scsize,scsp.scpsize from batch b " +
								" left join (select count(sc.userid) scsize,bc.batchid batchid from study_class sc left join batch_class bc on sc.classid = bc.classid group by bc.batchid) scs " +
								" on scs.batchid = b.id left join (select count(sc.userid) scpsize,bc.batchid batchid from study_class sc left join batch_class bc on sc.classid = bc.classid where sc.certificateno is not null group by bc.batchid) scsp on scsp.batchid = b.id where creater=? order by id) t where rownum <= ?) where rn >=?");
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			} else {
				ps = ct
						.prepareStatement("select * from (select t.*, rownum rn from (select b.id, b.name, b.description,scs.scsize,scsp.scpsize from batch b " +
								" left join (select count(sc.userid) scsize,bc.batchid batchid from study_class sc left join batch_class bc on sc.classid = bc.classid group by bc.batchid) scs " +
								" on scs.batchid = b.id left join (select count(sc.userid) scpsize,bc.batchid batchid from study_class sc left join batch_class bc on sc.classid = bc.classid where sc.certificateno is not null group by bc.batchid) scsp on scsp.batchid = b.id  order by id) t where rownum <= ?) where rn >=?");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Batch batch = new Batch(rs.getInt(1), rs.getString(2));
				batch.setDescription(rs.getString(3));
				batch.setUserCount(rs.getInt(4));
				batch.setUserPassedCount(rs.getInt(5));
				list.add(batch);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public int listBatchssize(int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int c = 0;
		try {
			ct = DBConnection.getConnection();
			if (userid > 0) {
				ps = ct
						.prepareStatement(" select count(id) from batch where creater=? order by id ");
				ps.setInt(1, userid);
			} else {
				ps = ct
						.prepareStatement(" select count(id) from batch order by id ");
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				c = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
	}
	public String batchclassids(int batchid) throws ElException{
		String s = "";
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select classid from batch_class where batchid = ?");
			ps.setInt(1, batchid);
			rs = ps.executeQuery();
			while (rs.next()) {
				s+=rs.getInt(1)+",";
			}
		} catch (Exception e) {
			logger.error("获取培训批次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s.length()>0?s.substring(0,s.length()-1):null;
	}
	public List<ELUser> classStudent(String classid, ElNode tree, ELUser elUser)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		List<Object> params=new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql.append("select eu.id euid,eu.username, eu.realname, dep.id depid,dep.name depname,eu.valid ,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
					+ " ,scl.status,scl.applydate,scl.certificateno,scl.classid from study_class scl left join eluser eu on scl.userid =eu.id "
					+ "inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, true)+") dep on dep.id = eu.depid where scl.classid in("+classid+") ");
			new ClassDaoImpl().checkUserParam(usersql, elUser, params);
			usersql.append(" order by scl.applydate desc ");
			ps = ct.prepareStatement(usersql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+2, params.get(i));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser2 = new ELUser();
				elUser2.setId(rs.getInt(1));
				elUser2.setUsername(rs.getString(2));
				elUser2.setRealname(rs.getString(3));
				elUser2.setDepartment(new Department(rs.getInt(4), rs
						.getString(5)));
				elUser2.setValid(rs.getBoolean(6));
				elUser2.setSex(rs.getString(7));
				elUser2.setJingzhong(rs.getInt(8));
				elUser2.setShengri(rs.getDate(9));
				elUser2.setAge(rs.getInt(10)); 
				if(rs.getInt("certificateno")>0){
					elUser2.setGraddate(rs.getDate(12));
				}
				int classidd = rs.getInt(14);
				//总分
				elUser2.setXx_time(classStudentScore2(classidd,elUser2.getId(), 0) + classStudentScore2(classidd,elUser2.getId(), 1));
				//必修分
				elUser2.setCt_credit(classStudentScore2(classidd,
						elUser2.getId(), 0));
				//选修分
				elUser2.setXx_credit(classStudentScore2(classidd,
						elUser2.getId(), 1));
				//elUser2.setIsAssign("未分配");??
				userList.add(elUser2);
			}
		} catch (Exception e) {
			logger.error("培训班统计查询学员（学分排序）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		//排序（按学分）
		userList=new ClassDaoImpl().sortUserByScore(userList);
		return userList;
	}
	public List<ELUser> classStudent(String classid, ElNode tree, ELUser elUser,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		List<Object> params=new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql.append("select * from (select t.*, rownum rn from ( select  eu.id euid,eu.username, eu.realname, dep.id depid,dep.name depname,eu.valid ,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
					+ " ,scl.status,scl.applydate,scl.certificateno,scl.classid from study_class scl left join eluser eu on scl.userid =eu.id "
					+ "inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, true)+") dep on dep.id = eu.depid where scl.classid in( "+classid+") ");
			new ClassDaoImpl().checkUserParam(usersql, elUser, params);
			usersql.append(" order by scl.applydate desc )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			ps.setInt(params.size()+1, pageNow);
			ps.setInt(params.size()+2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser2 = new ELUser();
				elUser2.setId(rs.getInt(1));
				elUser2.setUsername(rs.getString(2));
				elUser2.setRealname(rs.getString(3));
				elUser2.setDepartment(new Department(rs.getInt(4), rs
						.getString(5)));
				elUser2.setValid(rs.getBoolean(6));
				elUser2.setSex(rs.getString(7));
				elUser2.setJingzhong(rs.getInt(8));
				elUser2.setShengri(rs.getDate(9));
				elUser2.setAge(rs.getInt(10));
				//先查看学员培训班是否通过再查证书编号
				int classidd= rs.getInt(14);
				setMyPassclass(elUser2.getId(),classidd);
				//cl1.setCertificateno(rs.getInt(7));
//				if(rs.getInt("certificateno")>0){
//					elUser2.setGraddate(rs.getDate(12));
//				}
				if( getStudyClassCertificateno(classidd,elUser2.getId())>0){
					elUser2.setGraddate(rs.getDate(12));
				}
				//必修分
				elUser2.setCt_credit(classStudentScore2(classidd,
						elUser2.getId(), 0));
				//选修分
				elUser2.setXx_credit(classStudentScore2(classidd,
						elUser2.getId(), 1));
				//总分
				elUser2.setXx_time(elUser2.getCt_credit() + elUser2.getXx_credit());
				//elUser2.setIsAssign("未分配");??
				elUser2.setIsLeader(classidd);
				userList.add(elUser2);
			}
		} catch (Exception e) {
			logger.error("培训班统计查询学员（学分排序）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		//排序（按学分）
		userList=new ClassDaoImpl().sortUserByScore(userList);
		return userList;
	}
	public void setMyPassclass(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("call user_ispass_class2(?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public int getStudyClassCertificateno(int classid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int certificateno=0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select certificateno from study_class where classid = ? and userid = ?");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()){
				certificateno=rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取学员培训班证书编号出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return certificateno;
	}
	public int classStudentScore2(int classid, int userid, int t)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int creditSum=0;
		try {
			ct = DBConnection.getConnection();
			String ts = "";
			if(t != 2){// 2 = 必修+选修
				ts = " and status = "+ t;
			}
			//1.先得到该培训班的所有必修（选修）课程，得到其结业方式
			ps = ct.prepareStatement("select courseid,getcredit,setcredit,classid from class_course where classid =? "+ts);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			int courseid=0;
			int getcredit=0;//结业方式
			int setcredit=0;//学分
			while(rs.next()) {
				courseid=rs.getInt("courseid");
				getcredit=rs.getInt("getcredit");
				setcredit=rs.getInt("setcredit");
				//判断是否通过，如果通过获取getcredit学分
				if(new ClassDaoImpl().classStudentIsPass(rs.getInt(4),userid,courseid,getcredit)){
					creditSum+=setcredit;
				}
			}

		} catch (Exception e) {
			logger.error("获取学员学分出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return creditSum;
	}
	public int classStudentSize(String classids, ElNode tree, ELUser elUser)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Object> params=new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql.append("select count(scl.userid) from study_class scl left join eluser eu on scl.userid =eu.id "
					+ "inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, true)+") dep on dep.id = eu.depid where scl.classid in("+classids+")");
			new ClassDaoImpl().checkUserParam(usersql, elUser, params);
			ps = ct.prepareStatement(usersql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("培训班统计查询学员数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public List<ElClass> listElclassStateByName(String name, ElClType cltypeTree,int[] types,
			int pagenow, int pagesize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			if(types!=null){
				for(int i=0;i<types.length;i++){
					ps = ct.prepareStatement("select * from (select t.*,rownum rn from (select cl.id,cl.name,cl.createtime,(select count(*) from study_class ca where ca.classid = cl.id and ca.status = 2)," +
							"(select count(*) from study_class scl where scl.classid = cl.id) as passedcount  " +
							"from elclass cl where cl.name like ? and cl.cltype in ("+createPerTypeId(cltypeTree,types[i])+")  order by cl.createtime desc) t where rownum <=? ) where rn >=?");
					ps.setString(1,"%"+name+"%");
					ps.setInt(2, pagenow);
					ps.setInt(3, pagesize);
					rs = ps.executeQuery();
					while (rs.next()) {
						ElClass c = new ElClass(rs.getInt(1), rs.getString(2));
						c.setCreatetime(rs.getDate(3));
						c.setUserPassedCount(rs.getInt(4)); 
						c.setUserCount(rs.getInt(5));
						cls.add(c);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}
	public int listElclassStateByNamesize(String name,ElClType cltypeTree, int[] types)
	throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int s = 0 ; 
		try {
			if(types!=null){
				for(int i=0;i<types.length;i++){
					ct = DBConnection.getConnection();
					ps = ct.prepareStatement("select count(cl.id) from elclass cl where cl.name like ? and cl.cltype in ("+createPerTypeId(cltypeTree,types[i])+") ");
					ps.setString(1,"%"+name+"%");
					//ps.setInt(2, type);
					rs = ps.executeQuery();
					if (rs.next()) {
						s += rs.getInt(1);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取统计培训批次中的培训班！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}
}
