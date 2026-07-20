package com.sopia.classman.dao.impl;

import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.classman.entities.ElclassAuditDescribes;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.ClassPara;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElGroup;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.duman.entities.UnitRanking;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.StudyConstants;
import com.sopia.studyman.dao.StudyClassDao;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.studyman.entities.PointsRecord;

public class ClassDaoImpl implements ClassDao {
	private static final Log logger = LogFactory.getLog(ElClTypeDaoImpl.class);

	public int getSessionValue(String key) {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return Integer.parseInt(session.getAttribute(key).toString());
	}

	public int addClass(ElClass elclass) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			// 获取主键值,手动处理主键生成，并返回主键值 modify by lcw
			// ps = ct
			// .prepareStatement("select elClass_sequence.nextval from dual");
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// id = rs.getInt(1);
			// }
			// rs.close();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_ADD));
			// ps.setString(1, elclass.getName());
			// ps.setString(2, elclass.getCertificatename());
			// // name,certificatename,cltype,creater,description
			// // ,optionalcredit,status,createtime
			// ps.setInt(3, elclass.getCltype().getId());
			// ps.setInt(4, elclass.getCreater().getId());
			// ps.setString(5, elclass.getDescription());
			// ps.setInt(6, elclass.getOptionalcredit());
			// //默认创建培训班的状态为制作中
			// ps.setInt(7, 0);
			// ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			// ps.setString(9, elclass.getMainimg());
			// ps.setInt(10, elclass.getGlobal());
			// ps.setInt(11, elclass.getGroup1().getId());
			// ps.setInt(12, elclass.getGroup2().getId());
			// ps.setTimestamp(13, elclass.getDiplomatime());
			// ps.setInt(14, elclass.getIsApplication());
			// ps.setTimestamp(15, new Timestamp(System.currentTimeMillis()));
			// ps.setTimestamp(16, new Timestamp(System.currentTimeMillis()));
			// // ps.setInt(14, id);
			// ps.executeUpdate();
			String sql = "insert into elclass(name,certificatename,cltype,creater,description ,optionalcredit,status,createtime,mainimg,global,group1 , group2,diplomatime ,starttime,finishtime,classtype,isApplication,depName,jingzhong) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_ADD));
			ps = ct.prepareStatement(sql);
			ps.setString(1, elclass.getName());
			ps.setString(2, elclass.getCertificatename());
			// name,certificatename,cltype,creater,description
			// ,optionalcredit,status,createtime
			ps.setInt(3, elclass.getCltype().getId());
			ps.setInt(4, elclass.getCreater().getId());
			ps.setString(5, elclass.getDescription());
			ps.setInt(6, elclass.getOptionalcredit());
			// ps.setInt(7, elclass.getStatus());
			// 创建培训班默认状态为0 制作中
			ps.setInt(7, 0);
			ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			ps.setString(9, elclass.getMainimg());
			ps.setInt(10, elclass.getGlobal());
			ps.setInt(11, elclass.getGroup1().getId());
			ps.setInt(12, elclass.getGroup2().getId());
			ps.setTimestamp(13, elclass.getDiplomatime());
			// ps.setInt(14, id);
			ps.setTimestamp(14, elclass.getStarttime());
			ps.setTimestamp(15, elclass.getFinishtime());
			ps.setInt(16, elclass.getClasstype());
			ps.setInt(17, elclass.getIsApplication());
			ps.setString(18, elclass.getDepName());
			ps.setString(19, elclass.getJingzhong());

			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('elclass') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select elClass_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				elclass.setId(rs.getInt(1));
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"培训班添加失败! 失败方法：addClass(ElClass elclass)  失败原因："
							+ new ElException(e));
			logger.error("培训班添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public int addClass2(ElClass elclass) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			// 获取主键值,手动处理主键生成，并返回主键值 modify by lcw
			// ps = ct
			// .prepareStatement("select elClass_sequence.nextval from dual");
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// id = rs.getInt(1);
			// }
			// rs.close();
			// String sql="insert into
			// elclass(name,certificatename,cltype,creater,description
			// ,optionalcredit,status,createtime,mainimg,global,group1 ,
			// group2,diplomatime ,id,starttime,finishtime)
			// values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String sql = "insert into elclass(name,certificatename,cltype,creater,description ,optionalcredit,status,createtime,mainimg,global,group1 , group2,diplomatime ,starttime,finishtime,classtype,isApplication,depName,jingzhong,credit_bx,credit_xx,learnByOrder,year,nazhengornianjian) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_ADD));
			ps = ct.prepareStatement(sql);
			ps.setString(1, elclass.getName());
			ps.setString(2, elclass.getCertificatename());
			// name,certificatename,cltype,creater,description
			// ,optionalcredit,status,createtime
			ps.setInt(3, elclass.getCltype().getId());
			ps.setInt(4, elclass.getCreater().getId());
			ps.setString(5, elclass.getDescription());
			ps.setInt(6, elclass.getOptionalcredit());
			ps.setInt(7, elclass.getStatus());
			ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			ps.setString(9, elclass.getMainimg());
			ps.setInt(10, elclass.getGlobal());
			ps.setInt(11, elclass.getGroup1().getId());
			ps.setInt(12, elclass.getGroup2().getId());
			ps.setTimestamp(13, elclass.getDiplomatime());
			// ps.setInt(14, id);
			ps.setTimestamp(14, elclass.getStarttime());
			ps.setTimestamp(15, elclass.getFinishtime());
			ps.setInt(16, elclass.getClasstype());
			ps.setInt(17, elclass.getIsApplication());
			ps.setString(18, elclass.getDepName());
			ps.setString(19, elclass.getJingzhong());
			ps.setInt(20, elclass.getCredit_bx());
			ps.setInt(21, elclass.getCredit_xx());
			ps.setInt(22, elclass.getLearnByOrder());
			ps.setInt(23, elclass.getYear());
			ps.setInt(24, elclass.getNazhengornianjian());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('elclass') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select elClass_sequence.currval from dual ");
				rs = ps.executeQuery();
			} else {
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				id = rs.getInt(1);
			// elclass.getId();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"培训班添加失败!失败方法：addClass2(ElClass elclass) 失败原因："
							+ new ElException(e));
			logger.error("培训班添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void addClassRegistration(ELClassRegistration elRegistration)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into elclass_registration(classid,PlanRecruitStudents,RegistrationStartTime,RegistrationStopTime,StartAge ,StopAge,sex,jingzhong,dishi,zhiwu,zhiji,gangwei,treeType,examroomIds,elclassIds,classScreeningWay,eroomScreeningWay,isAudit,examepids) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elRegistration.getElclass().getId());
			ps.setString(2, elRegistration.getPlanRecruitStudents() + "");
			ps.setTimestamp(3, elRegistration.getRegistrationStartTime());
			ps.setTimestamp(4, elRegistration.getRegistrationStopTime());
			ps.setInt(5, elRegistration.getStartAge());
			ps.setInt(6, elRegistration.getStopAge());
			ps.setString(7, elRegistration.getSex());
			ps.setString(8, elRegistration.getJingzhong());
			ps.setString(9, elRegistration.getDishi());
			ps.setString(10, elRegistration.getZhiwu());
			ps.setString(11, elRegistration.getZhiji());
			ps.setString(12, elRegistration.getGangwei());
			ps.setString(13, elRegistration.getTreeType());
			// ps.setString(14, elRegistration.getExamRoomids());
			// ps.setString(15, elRegistration.getElclassids());
			ps.setString(14, elRegistration.getErParasstr());
			ps.setString(15, elRegistration.getClassParasstr());
			ps.setInt(16, elRegistration.getClassScreeningWay());
			ps.setInt(17, elRegistration.getEroomScreeningWay());
			ps.setInt(18, elRegistration.getIsAudit());
			ps.setString(19, elRegistration.getErepParasstr());
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"培训班申请添加失败!失败方法：addClassRegistration(ELClassRegistration elRegistration) 失败原因："
							+ new ElException(e));
			logger.error("培训班申请添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ElClass> getClassesList(ElClType tree, int deptid, int cltid,
			ElClass elclass, String status, int role, String sqlw, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();

			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}

			rs.close();

			// 获取学员人数
			Map map = new HashMap();
			ps = ct
					.prepareStatement("select sc.classid,count(userid) from study_class sc group by sc.classid");
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put(rs.getInt(1), rs.getInt(2));
			}
			rs.close();
			String x = Integer.toString(cltid);
			String ids = ElClTypeById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String sqls = "";
			if (elclass != null) {
				if (elclass.getName() != null && !elclass.getName().equals("")) {// 培训名称
					sqls += " and cl.name like '%" + elclass.getName() + "%'";
				}
				if (elclass.getStatus() != -1) {// 考场状态
					sqls += " and cl.status=" + elclass.getStatus();
				}
				if (elclass.getBegintime() != null) {
					sqls += " and cl.STARTTIME >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (elclass.getEndtime() != null) {
					sqls += " and cl.FINISHTIME <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
			}
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select   cl.id, cl.name, cl.certificatename, cl.cltype, ")
					.append(
							" cl.optionalcredit, cl.status, clt.name cltname,u.realname,cl.createtime , cl.astauts ,cl.starttime,cl.finishtime, (select count(ca.userid) from study_class ca where ca.classid=cl.id) classsize ,cl.isApplication")
					.append(
							" from elclass cl, elclasstype clt, eluser u, department dep ")
					.append(
							" where cl.cltype = clt.id and cl.creater = u.id and u.depid = dep.id ")
					.append(" and cl.status in(" + status + ") ")
					// and dep.lid >= ? and dep.rid <= ?
					.append(
							" and clt.id in (" + ids + ") " + sqls
									+ " order by cl.createtime desc) t ")
					.append(" where rownum <= ?) where rn >= ? ");

			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, dep.getLid());
			// ps.setInt(2, dep.getRid());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				ElClass cl = new ElClass(id, rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setAstatus(rs.getInt(10));
				ElClType clType = new ElClType();
				clType.setName(rs.getString(7));
				cl.setCltype(clType);
				ELUser user = new ELUser();
				// user.setUsername(rs.getString(8));
				user.setRealname(rs.getString(8));
				cl.setCreater(user);
				cl.setCreatetime(rs.getTimestamp(9));
				cl.setStarttime(rs.getTimestamp(11));
				cl.setFinishtime(rs.getTimestamp(12));
				cl.setClassSize(rs.getInt(13));
				cl.setIsApplication(rs.getInt("isApplication"));
				cl.setPlanNumber(getElclassPlanNumber(id));
				if (map.get(id) != null) {
					cl.setStudentCount((Integer) map.get(id));
				} else {
					cl.setStudentCount(0);
				}
				if (isOpen(id)) {
					cl.setOperation(1);// 考场全部开通
				} else {
					if (checkuserClassEroomOperation(id, sqlw))
						cl.setOperation(2);// 有可操作
					else
						cl.setOperation(3);// 无可操作
				}
				cls.add(cl);
			}

		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班列表失败!失败方法：getClassesList(ElClType tree, int deptid, int cltid, ElClass elclass, String status,int role,String sqlw, int pageNow, int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<ElClass> getClassesList3(ElClType tree, int deptid, int cltid,
			ElClass elclass, String status, int role, String sqlw, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			// 获取学员人数
			Map map = new HashMap();
			ps = ct
					.prepareStatement("select sc.classid,count(userid) from study_class sc group by sc.classid");
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put(rs.getInt(1), rs.getInt(2));
			}
			rs.close();
			String x = Integer.toString(cltid);
			String ids = ElClTypeById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String sqls = "";
			if (elclass != null) {
				if (elclass.getName() != null && !elclass.getName().equals("")) {// 培训名称
					sqls += " and cl.name like '%" + elclass.getName() + "%'";
				}
				if (elclass.getStatus() != -1) {// 考场状态
					sqls += " and cl.status=" + elclass.getStatus();
				}
				if (elclass.getBegintime() != null) {
					sqls += " and cl.STARTTIME >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (elclass.getEndtime() != null) {
					sqls += " and cl.FINISHTIME <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
			}
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select   cl.id, cl.name, cl.certificatename, cl.cltype, ")
					.append(
							" cl.optionalcredit, cl.status, clt.name cltname,u.realname,cl.createtime , cl.astauts ,cl.starttime,cl.finishtime, (select count(ca.userid) from study_class ca where ca.classid=cl.id) classsize,cl.isApplication ")
					.append(
							" from elclass cl, elclasstype clt, eluser u, department dep ")
					.append(
							" where cl.cltype = clt.id and cl.creater = u.id and u.depid = dep.id ")
					.append(" and cl.status in(" + status + ")")
					// and dep.lid >= ? and dep.rid <= ?
					.append(
							" and clt.id in (" + ids + ") " + sqls
									+ " order by cl.createtime desc) t ")
					.append(" where rownum <= ?) where rn >= ? ");

			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				ElClass cl = new ElClass(id, rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setAstatus(rs.getInt(10));
				ElClType clType = new ElClType();
				clType.setName(rs.getString(7));
				cl.setCltype(clType);
				ELUser user = new ELUser();
				// user.setUsername(rs.getString(8));
				user.setRealname(rs.getString(8));
				cl.setCreater(user);
				cl.setCreatetime(rs.getTimestamp(9));
				cl.setStarttime(rs.getTimestamp(11));
				cl.setFinishtime(rs.getTimestamp(12));
				cl.setClassSize(rs.getInt(13));
				cl.setIsApplication(rs.getInt("isApplication"));
				cl.setPlanNumber(getElclassPlanNumber(id));
				if (map.get(id) != null) {
					cl.setStudentCount((Integer) map.get(id));
				} else {
					cl.setStudentCount(0);
				}
				if (isOpen(id)) {
					cl.setOperation(1);// 考场全部开通
				} else {
					if (checkuserClassEroomOperation(id, sqlw))
						cl.setOperation(2);// 有可操作
					else
						cl.setOperation(3);// 无可操作
				}
				cls.add(cl);
			}

		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班列表失败!失败方法：getClassesList3(ElClType tree, int deptid, int cltid, ElClass elclass, String status,int role,String sqlw, int pageNow, int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 检测参数
	 * 
	 * @param sql
	 * @param params
	 * @param elclass
	 */
	public void checkClassParam(StringBuffer sql, List<Object> params,
			ElClass elclass) {
		if (elclass != null) {
			if (elclass.getName() != null && !elclass.getName().equals("")) {// 培训名称
				sql.append(" and cl.name like ? escape '/'");
				params.add("%" + StringUtil.toLikeStr(elclass.getName()) + "%");
			}
			if (elclass.getOwner() != null) {
				if (elclass.getOwner().getUsername() != null
						&& !elclass.getOwner().getUsername().equals("")) {// 培训名称
					sql.append(" and u.username like ?");
					params.add("%"
							+ StringUtil.toLikeStr(elclass.getOwner()
									.getUsername()) + "%");
				}
				if (elclass.getOwner().getRealname() != null
						&& !elclass.getOwner().getRealname().equals("")) {// 培训名称
					sql.append(" and u.realname like ?");
					params.add("%"
							+ StringUtil.toLikeStr(elclass.getOwner()
									.getRealname()) + "%");
				}
			}
			if (elclass.getStatus() != -1) {// 考场状态
				sql.append(" and cl.status=?");
				params.add(elclass.getStatus());
			}
			if (elclass.getBegintime() != null) {
				// sql.append(" and cl.STARTTIME >= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getBegintime())+ "','yyyy-MM-dd
				// HH24:mi:ss')");
				sql.append(" and cl.STARTTIME >= ?");
				params.add(elclass.getBegintime());
			}
			if (elclass.getEndtime() != null) {
				// sql.append(" and cl.FINISHTIME <= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')");
				sql.append(" and cl.FINISHTIME <= ?");
				params.add(elclass.getEndtime());
			}
			if (elclass.getElRegistration() != null
					&& elclass.getElRegistration().getIsAudit() == 1) {
				sql.append(" and elr.isAudit=1 ");
			}
		}
	}

	/**
	 * 获取培训班list
	 * 
	 * @param tree
	 * @param elclass
	 * @param sublibs
	 * @param status
	 * @param sqlw
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getClassList(ElNode tree, ElClass elclass,
			int sublibs, String status, String sqlw, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select * from (select t.*, rownum rn from ("
							+ "select cl.id, cl.name, cl.certificatename, cl.cltype, cl.optionalcredit, cl.status,"
							+ " clt.name cltname,u.realname,cl.createtime , cl.astauts ,cl.starttime,cl.finishtime,"
							+ " sc.usercount classsize,cl.isApplication,elr.planrecruitstudents,cl.depName,cl.jingzhong,cl.hot "
							+ " from elclass cl inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"elclasstype", tree, consub)
							+ ") clt on cl.cltype = clt.id "
							+ "left join eluser u on cl.creater = u.id "
							+ "left join department dep on u.depid = dep.id "
							+ "left join (select classid,count(userid) usercount from study_class group by classid) sc on sc.classid=cl.id "
							+ "left join elclass_registration elr on elr.classid=cl.id"
							+ "  where cl.status in(" + status + ") ");
			this.checkClassParam(buffer, params, elclass);
			buffer
					.append(" order by cl.createtime desc) t where rownum <= ?) where rn >= ? ");
			ps = ct.prepareStatement(buffer.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			ps.setInt(params.size() + 1, pageNow);
			ps.setInt(params.size() + 2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				ElClass cl = new ElClass(id, rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setAstatus(rs.getInt(10));
				ElClType clType = new ElClType();
				clType.setName(rs.getString(7));
				cl.setCltype(clType);
				ELUser user = new ELUser();
				user.setRealname(rs.getString(8));
				cl.setCreater(user);
				cl.setCreatetime(rs.getTimestamp(9));
				cl.setStarttime(rs.getTimestamp(11));
				cl.setFinishtime(rs.getTimestamp(12));
				cl.setClassSize(rs.getInt(13));
				cl.setIsApplication(rs.getInt(14));
				cl.setPlanNumber(rs.getInt(15));
				cl.setIsUvalid(checkElclassIsUvalid("valids", id) ? "true"
						: "false");// ???
				cl.setDepName(rs.getString(16));
				cl.setJingzhong(rs.getString(17));
				cl.setHot(rs.getInt(18));
				// cl.setStudentCount(rs.getInt(13));
				if (isOpen(id)) {
					cl.setOperation(1);// 考场全部开通
				} else {
					if (checkuserClassEroomOperation(id, sqlw))
						cl.setOperation(2);// 有可操作
					else
						cl.setOperation(3);// 无可操作
				}
				cl.setExamRooms(getClassRooms(cl.getId()));
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班列表失败!失败方法：getClassList 失败原因：" + new ElException(e));
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 获取培训班list数量
	 * 
	 * @param tree
	 * @param elclass
	 * @param sublibs
	 * @param status
	 * @param sqlw
	 * @return
	 * @throws ElException
	 */
	public int getClassListSize(ElNode tree, ElClass elclass, int sublibs,
			String status) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params = new ArrayList<Object>();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(cl.id) "
							+ " from elclass cl inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean("elnodesql")).generateSQLByTree(
									"elclasstype", tree, consub)
							+ ") clt on cl.cltype = clt.id "
							+ " left join eluser u on cl.creater=u.id "
							+ " left join elclass_registration elr on elr.classid=cl.id "
							+ "  where cl.status in(" + status + ") ");
			this.checkClassParam(buffer, params, elclass);
			ps = ct.prepareStatement(buffer.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班列表数量失败!失败方法：getClassListSize 失败原因："
							+ new ElException(e));
			logger.error("获取培训班列表数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ElClass> getClassesList2(ElClType tree, int deptid, int cltid,
			ElClass elclass, String status, int role, String sqlw, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();

			// Department dep = new Department();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			// ps.setInt(1, deptid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// dep.setId(rs.getInt(1));
			// dep.setLid(rs.getInt(2));
			// dep.setRid(rs.getInt(3));
			// }
			//
			// rs.close();

			// 获取学员人数
			Map map = new HashMap();
			ps = ct
					.prepareStatement("select sc.classid,count(userid) from study_class sc group by sc.classid");
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put(rs.getInt(1), rs.getInt(2));
			}
			rs.close();
			String x = Integer.toString(cltid);
			String ids = ElClTypeById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String sqls = "";
			if (elclass != null) {
				if (elclass.getName() != null && !elclass.getName().equals("")) {// 培训名称
					sqls += " and cl.name like '%" + elclass.getName() + "%'";
				}
				if (elclass.getStatus() != -1) {// 考场状态
					sqls += " and cl.status=" + elclass.getStatus();
				}
				if (elclass.getBegintime() != null) {
					sqls += " and cl.STARTTIME >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (elclass.getEndtime() != null) {
					sqls += " and cl.FINISHTIME <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
			}
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select   cl.id, cl.name, cl.certificatename, cl.cltype, ")
					.append(
							" cl.optionalcredit, cl.status, clt.name cltname,u.realname,cl.createtime,cl.starttime,cl.finishtime, (select count(ca.userid) from study_class ca where ca.classid=cl.id) classsize,cl.isApplication ")
					.append(
							" from elclass cl, elclasstype clt, eluser u, department dep ")
					.append(
							" where cl.cltype = clt.id and cl.creater = u.id and u.depid = dep.id ")
					.append("  and cl.status in(" + status + ") ")
					// and dep.lid >= ? and dep.rid <= ?
					.append(" and clt.id in (" + ids + ") " + sqls)
					.append(
							" order by cl.createtime desc) t  where rownum <= ?) where rn >= ? ");

			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, dep.getLid());
			// ps.setInt(2, dep.getRid());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				ElClass cl = new ElClass(id, rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				ElClType clType = new ElClType();
				clType.setName(rs.getString(7));
				cl.setCltype(clType);
				ELUser user = new ELUser();
				// user.setUsername(rs.getString(8));
				user.setRealname(rs.getString(8));
				cl.setCreater(user);
				cl.setCreatetime(rs.getTimestamp(9));
				cl.setStarttime(rs.getTimestamp(10));
				cl.setFinishtime(rs.getTimestamp(11));
				cl.setClassSize(rs.getInt(12));
				cl.setIsUvalid(checkElclassIsUvalid("valids", id) ? "true"
						: "false");
				cl.setIsApplication(rs.getInt("isApplication"));
				cl.setPlanNumber(getElclassPlanNumber(id));
				if (map.get(id) != null) {
					cl.setStudentCount((Integer) map.get(id));
				} else {
					cl.setStudentCount(0);
				}
				if (isOpen(id)) {
					cl.setOperation(1);// 考场全部开通
				} else {
					if (checkuserClassEroomOperation(id, sqlw))
						cl.setOperation(2);// 有可操作
					else
						cl.setOperation(3);// 无可操作
				}
				cls.add(cl);
			}

		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班列表失败!失败方法：getClassesList2(ElClType tree, int deptid, int cltid, ElClass elclass,String status,int role,String sqlw, int pageNow, int pageSize)  失败原因："
									+ new ElException(e));
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public void alterClass(ElClass elclass) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(ClassConstants.CLASS_ALTER));
			 */
			ps = ct
					.prepareStatement("update elclass set name=? ,certificatename=?,cltype=?,description =?,optionalcredit=?,status =?,mainimg=?,global=?,group1= ?, group2 = ? ,diplomatime=?,starttime=?,finishtime=?,classtype=?,isApplication=?,depName=?,jingzhong=?,credit_bx=?,credit_xx=?,learnByOrder=?,year=?,nazhengornianjian=? where id= ?");
			ps.setString(1, elclass.getName());
			ps.setString(2, elclass.getCertificatename());
			// name,certificatename,cltype,creater,description
			// ,optionalcredit,status,createtime
			ps.setInt(3, elclass.getCltype().getId());
			ps.setString(4, elclass.getDescription());
			ps.setInt(5, elclass.getOptionalcredit());
			ps.setInt(6, elclass.getStatus());
			ps.setString(7, elclass.getMainimg());
			ps.setInt(8, elclass.getGlobal());
			if (elclass.getGroup1() == null) {
				elclass.setGroup1(new ElGroup(0));
			}
			if (elclass.getGroup2() == null) {
				elclass.setGroup2(new ElGroup(0));
			}
			ps.setInt(9, elclass.getGroup1().getId());
			ps.setInt(10, elclass.getGroup2().getId());
			ps.setTimestamp(11, elclass.getDiplomatime());
			ps.setTimestamp(12, elclass.getStarttime());
			ps.setTimestamp(13, elclass.getFinishtime());
			ps.setInt(14, elclass.getClasstype());
			ps.setInt(15, elclass.getIsApplication());
			ps.setString(16, elclass.getDepName());
			ps.setString(17, elclass.getJingzhong());
			ps.setInt(18, elclass.getCredit_bx());
			ps.setInt(19, elclass.getCredit_xx());
			ps.setInt(20, elclass.getLearnByOrder());
			ps.setInt(21, elclass.getYear());
			ps.setInt(22, elclass.getNazhengornianjian());
			ps.setInt(23, elclass.getId());
			// ps.setInt(13, elclass.getCreater().getId());
			ps.executeUpdate();
			// int group2 = elclass.getGroup2().getId();
			// ps = ct
			// .prepareStatement("select userid from elgroup2user where gid =
			// ?");
			// ps.setInt(1, group2);
			// rs = ps.executeQuery();
			// while (rs.next()) {
			// int userid = rs.getInt(1);
			// graduateClassApplay(userid, elclass.getId());
			// }
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ALTER,
					"培训班修改失败!失败方法：alterClass(ElClass elclass) 失败原因："
							+ new ElException(e));
			logger.error("培训班修改失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterClassRegistration(ELClassRegistration elRegistration)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update ELCLASS_registration set PlanRecruitStudents=?,RegistrationStartTime=?,RegistrationStopTime=?,StartAge=?,StopAge=?,sex=?,jingzhong=?,dishi=?,zhiwu=?,zhiji=?,gangwei=?,treeType=?,examroomIds=?,elclassIds=?,classScreeningWay=?,eroomScreeningWay=?,isAudit=?,examepids=? where classid=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, elRegistration.getPlanRecruitStudents() + "");
			ps.setTimestamp(2, elRegistration.getRegistrationStartTime());
			ps.setTimestamp(3, elRegistration.getRegistrationStopTime());
			ps.setInt(4, elRegistration.getStartAge());
			ps.setInt(5, elRegistration.getStopAge());
			ps.setString(6, elRegistration.getSex());
			ps.setString(7, elRegistration.getJingzhong());
			ps.setString(8, elRegistration.getDishi());
			ps.setString(9, elRegistration.getZhiwu());
			ps.setString(10, elRegistration.getZhiji());
			ps.setString(11, elRegistration.getGangwei());
			ps.setString(12, elRegistration.getTreeType());
			// ps.setString(13, elRegistration.getExamRoomids());
			// ps.setString(14, elRegistration.getElclassids());
			ps.setString(13, elRegistration.getErParasstr());
			ps.setString(14, elRegistration.getClassParasstr());
			ps.setInt(15, elRegistration.getClassScreeningWay());
			ps.setInt(16, elRegistration.getEroomScreeningWay());
			ps.setInt(17, elRegistration.getIsAudit());
			ps.setString(18, elRegistration.getErepParasstr());
			ps.setInt(19, elRegistration.getElclass().getId());

			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_ALTER,
							"修改申请培训班条件失败!失败方法：alterClassRegistration(ELClassRegistration elRegistration) 失败原因："
									+ new ElException(e));
			logger.error("修改申请培训班条件失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ELClassRegistration getClassRegistration(int classid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ELClassRegistration elR = new ELClassRegistration();
		try {
			String sql = "  select classid,PlanRecruitStudents,RegistrationStartTime,RegistrationStopTime,StartAge,StopAge,sex,jingzhong,dishi,zhiwu,zhiji,gangwei,treeType,examroomIds,elclassIds,classScreeningWay,eroomScreeningWay,isAudit,examepids from ELCLASS_registration where classid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				elR.setElclass(new ElClass(rs.getInt(1)));
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
				// List<ExamRoom> ers = new ArrayList<ExamRoom>();
				// if(rs.getString(14)!=null){
				// //把字符串数组转换成集合
				// List<String> listR = new
				// ArrayList<String>(Arrays.asList(rs.getString(14).split(",")));
				// for(int i = 0;i< listR.size();i++){
				// ers.add(new ExamRoom(Integer.parseInt(listR.get(i))));
				// }
				// }
				// elR.setExamRoom(ers);
				elR.setErParasstr(rs.getString(14));
				// 培训班
				// elR.setElclassIds(rs.getString(15));
				// List<ElClass> elc = new ArrayList<ElClass>();
				// if(rs.getString(15)!=null){
				// List<String> listC = new
				// ArrayList<String>(Arrays.asList(rs.getString(15).split(",")));
				// for(int i = 0;i< listC.size();i++){
				// elc.add(new ElClass(Integer.parseInt(listC.get(i))));
				// }
				// }
				// elR.setElclasss(elc);
				elR.setClassParasstr(rs.getString(15));
				elR.setClassScreeningWay(rs.getInt(16));
				elR.setEroomScreeningWay(rs.getInt(17));
				elR.setIsAudit(rs.getInt(18));
				elR.setErepParasstr(rs.getString(19));
				return elR;
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取申请培训班条件失败!失败方法：getClassRegistration(int classid) 失败原因："
							+ new ElException(e));
			logger.error("获取申请培训班条件失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elR;
	}

	private void graduateClassApplay(int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// if (isGraduate(userid, classid))
		// return;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select dbo.class_ispassed(?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			int ispassed = 0;
			if (rs.next()) {
				ispassed = rs.getInt(1);
			}
			rs.close();
			if (ispassed == 0) {
				ps = ct
						.prepareStatement("delete from study_class where userid = ? and classid =?");
				ps.setInt(1, userid);
				ps.setInt(2, classid);
				ps.executeUpdate();
			}
			if (ispassed == 1) {
				ps = ct.prepareStatement("{call class_pass_set ?,?,?}");
				ps.setInt(1, classid);
				ps.setInt(2, userid);
				ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				ps.executeUpdate();
			}
			if (ispassed == 2) {
				ps = ct
						.prepareStatement("select diplomatime from elclass where id=?  ");
				ps.setInt(1, classid);
				rs = ps.executeQuery();
				Timestamp diplomatime = new Timestamp(System
						.currentTimeMillis());
				if (rs.next()) {
					diplomatime = rs.getTimestamp(1);
				}
				rs.close();
				ps = ct.prepareStatement("{call class_pass_set ?,?,?}");
				ps.setInt(1, classid);
				ps.setInt(2, userid);
				ps.setTimestamp(3, diplomatime);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"培训班申请结业!失败方法：graduateClassApplay(int userid, int classid) 失败原因："
							+ new ElException(e));
			logger.error("培训班申请结业！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ElClass getClassById(int userid, int id) throws ElException {
		// cl.id,cl.name,cl.certificatename,cl.cltype, ,cl.description
		// ,cl.optionalcredit,cl.status clt.name
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {

			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_MAN_BYUIDANDID));
			ps.setInt(1, userid);
			ps.setInt(2, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(8)));
				cl.setDescription(rs.getString(5));
				cl.setOptionalcredit(rs.getInt(6));
				cl.setStatus(rs.getInt(7));
				cl.setMainimg(rs.getString(9));
				cl.setGlobal(rs.getInt(10));
				cl.setGroup1(new ElGroup(rs.getInt(11)));
				cl.setGroup2(new ElGroup(rs.getInt(12)));
				cl.setDiplomatime(rs.getTimestamp(13));
				cl.setStarttime(rs.getTimestamp("starttime"));
				cl.setFinishtime(rs.getTimestamp("finishtime"));
				cl.setClasstype(rs.getInt("classtype"));
				return cl;
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：getClassById(int userid, int id) 失败原因："
							+ new ElException(e));
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return new ElClass();
	}

	public ElClass getClassById(int id) throws ElException {
		// cl.id,cl.name,cl.certificatename,cl.cltype, ,cl.description
		// ,cl.optionalcredit,cl.status clt.name
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			String sql = "select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,cl.global,cl.group1 ,cl.group2,cl.diplomatime,cl.starttime,cl.finishtime,cl.classtype,cl.isnormal,cl.isApplication,cl.creater,cl.depName,cl.jingzhong,cl.credit_bx,cl.credit_xx,cl.learnByOrder,cl.year,cl.nazhengornianjian,eae.firstlearnlaterexam from elclass cl,elclasstype clt,elclass_assign_examroom eae where cl.cltype = clt.id  and cl.id=eae.classid(+) and cl.id = ?";
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select
			// cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description
			// ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,cl.global,cl.group1
			// ,cl.group2,cl.diplomatime from elclass cl,elclasstype clt where
			// cl.cltype = clt.id and cl.id = ?");
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(8)));
				cl.setDescription(rs.getString(5));
				cl.setOptionalcredit(rs.getInt(6));
				cl.setStatus(rs.getInt(7));
				cl.setMainimg(rs.getString(9));
				cl.setGlobal(rs.getInt(10));
				cl.setGroup1(new ElGroup(rs.getInt(11)));
				cl.setGroup2(new ElGroup(rs.getInt(12)));
				cl.setDiplomatime(rs.getTimestamp(13));
				cl.setStarttime(rs.getTimestamp("starttime"));
				cl.setFinishtime(rs.getTimestamp("finishtime"));
				cl.setClasstype(rs.getInt("classtype"));
				cl.setIsnormal(rs.getInt("isnormal"));
				cl.setIsApplication(rs.getInt("isApplication"));
				cl.setCredit_bx(rs.getInt("credit_bx"));
				cl.setCredit_xx(rs.getInt("credit_xx"));
				cl.setCreater(new ELUser(rs.getInt(19)));
				cl.setDepName(rs.getString(20));
				cl.setJingzhong(rs.getString(21));
				cl.setLearnByOrder(rs.getInt("learnbyorder"));
				cl.setYear(rs.getInt("year"));
				cl.setNazhengornianjian(rs.getInt("nazhengornianjian"));
				cl.setFirstlearnlaterexam(rs.getInt("firstlearnlaterexam"));
				return cl;
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：getClassById(int id) 失败原因："
							+ new ElException(e));
			logger.error("我的培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return new ElClass();
	}

	public ElClass getClassByName(String className) throws ElException {
		// cl.id,cl.name,cl.certificatename,cl.cltype, ,cl.description
		// ,cl.optionalcredit,cl.status clt.name
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			String sql = "select cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,cl.global,cl.group1 ,cl.group2,cl.diplomatime,cl.starttime,cl.finishtime,cl.classtype from elclass cl,elclasstype clt where cl.cltype = clt.id and cl.name = ?";
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select
			// cl.id,cl.name,cl.certificatename,cl.cltype ,cl.description
			// ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,cl.global,cl.group1
			// ,cl.group2,cl.diplomatime from elclass cl,elclasstype clt where
			// cl.cltype = clt.id and cl.id = ?");
			ps = ct.prepareStatement(sql);
			ps.setString(1, className);
			rs = ps.executeQuery();
			if (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(8)));
				cl.setDescription(rs.getString(5));
				cl.setOptionalcredit(rs.getInt(6));
				cl.setStatus(rs.getInt(7));
				cl.setMainimg(rs.getString(9));
				cl.setGlobal(rs.getInt(10));
				cl.setGroup1(new ElGroup(rs.getInt(11)));
				cl.setGroup2(new ElGroup(rs.getInt(12)));
				cl.setDiplomatime(rs.getTimestamp(13));
				cl.setStarttime(rs.getTimestamp("starttime"));
				cl.setFinishtime(rs.getTimestamp("finishtime"));
				cl.setClasstype(rs.getInt("classtype"));
				return cl;
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：getClassByName(String className) 失败原因："
							+ new ElException(e));
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return new ElClass();
	}

	public List<ElClass> listClasses(int userid, int cltid, String name,
			int pageNow, int pageSize) throws ElException {
		// cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit,
		// cl.status
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			int lid = 0;
			int rid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLTYPE_LIRID));
			ps.setInt(1, cltid);
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			if (userid == 0) {
				ps = ct
						.prepareStatement("select * from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname,cl.createtime,cl.starttime,cl.finishtime,cl.creater,el.realname,"
								+ " (select count(ca.userid) from study_class ca where ca.classid=cl.id) classsize "
								+ "from elclass cl, elclasstype clt,eluser el where cl.cltype=clt.id and clt.lid>=? and clt.rid<=? and cl.name  like ? and cl.status !=9 and cl.creater = el.id order by cl.createtime desc )t where rownum <= ? ) where rn>=?");

				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setString(3, "%" + name + "%");
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			} else {
				ps = ct
						.prepareStatement("select * from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname,cl.createtime,cl.starttime,cl.finishtime,cl.creater,el.realname,"
								+ " (select count(ca.userid) from study_class ca where ca.classid=cl.id) classsize "
								+ "from elclass cl, elclasstype clt,eluser el where cl.cltype=clt.id and clt.lid>=? and clt.rid<=?  and cl.creater = ? and cl.name  like ? and cl.status !=9 and cl.creater = el.id order by cl.createtime desc )t where rownum <= ? ) where rn>=?");

				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setInt(3, userid);
				ps.setString(4, "%" + name + "%");
				ps.setInt(5, pageNow);
				ps.setInt(6, pageSize);
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setCreatetime(rs.getTimestamp(8));
				cl.setStarttime(rs.getTimestamp(9));
				cl.setFinishtime(rs.getTimestamp(10));
				ELUser user = new ELUser();
				user.setRealname(rs.getString(12));
				cl.setClassSize(rs.getInt(13));
				cl.setCreater(user);
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法：listClasses(int userid, int cltid, String name,int pageNow, int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<ElClass> listClasses(int userid, int roleid,
			ElClType cltypeTree, int cltid, String name, int pageNow,
			int pageSize) throws ElException {
		// cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit,
		// cl.status
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			String ids = ctypeTypeId(cltypeTree, cltid);
			if (roleid != 1)
				ids = ids.substring(2, ids.length());
			ps = ct
					.prepareStatement("select * from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname,cl.createtime from elclass cl, elclasstype clt where cl.cltype=clt.id and clt.id in ("
							+ ids
							+ ")  and cl.creater = ? and cl.name  like ? and cl.status >=0 order by cl.createtime desc )t where rownum <= ? ) where rn>=?");
			// ps = ct.prepareStatement("select * from (select t.*, rownum rn
			// from (select
			// cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit,
			// cl.status,clt.name cltname,cl.createtime from elclass cl,
			// elclasstype clt where cl.cltype=clt.id and clt.lid>=? and
			// clt.rid<=? and cl.creater = ? and cl.name like ? and cl.status
			// >=0 order by cl.createtime desc )t where rownum <= ? ) where
			// rn>=?");
			// ps.setInt(4, ClassConstants.CLASS_STATUS_DELETE);
			ps.setInt(1, userid);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setCreatetime(rs.getTimestamp(8));
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法：listClasses(int userid,int roleid,ElClType cltypeTree, int cltid, String name,int pageNow, int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public int listClassesCount(int userid, int roleid, ElClType cltypeTree,
			int cltid, String name) throws ElException {
		// cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit,
		// cl.status
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			String ids = ctypeTypeId(cltypeTree, cltid);
			if (roleid != 1)
				ids = ids.substring(2, ids.length());
			ps = ct
					.prepareStatement("select count(*) from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname,cl.createtime from elclass cl, elclasstype clt where cl.cltype=clt.id and clt.id in ("
							+ ids
							+ ")  and cl.creater = ? and cl.name  like ? and cl.status >=0 order by cl.createtime desc )t)");
			// ps = ct.prepareStatement("select * from (select t.*, rownum rn
			// from (select
			// cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit,
			// cl.status,clt.name cltname,cl.createtime from elclass cl,
			// elclasstype clt where cl.cltype=clt.id and clt.lid>=? and
			// clt.rid<=? and cl.creater = ? and cl.name like ? and cl.status
			// >=0 order by cl.createtime desc )t where rownum <= ? ) where
			// rn>=?");
			// ps.setInt(4, ClassConstants.CLASS_STATUS_DELETE);
			ps.setInt(1, userid);
			ps.setString(2, "%" + name + "%");
			/*
			 * ps.setInt(5, pageNow); ps.setInt(6, pageSize);
			 */
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法：listClassesCount(int userid,int roleid,ElClType cltypeTree, int cltid, String name) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	// 培训班组合搜索
	public List<ElClass> listcombinationSearchClass(ElClass elClass,
			ElClType cltypeTree, String sqlw, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			// name = name == null ? "" : name.trim();
			int lid = 0;
			int rid = 0;
			ps = ct
					.prepareStatement("select id,lid,rid from elclasstype where id =?");
			// ps.setInt(1, cltid);
			ps.setInt(1, elClass.getCltype().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}

			String sqlstr = "";
			sqlstr += "select * from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname,cl.createtime,elu.realname,cl.astauts,cl.isApplication from elclass cl, elclasstype clt,eluser elu ";
			sqlstr += " where cl.cltype=clt.id and elu.id=cl.creater and cl.status >=0 ";
			// sqlstr+=elClass.getCltype()==null?"":(elClass.getCltype().getId()==-1||elClass.getCltype().getId()==0)?"":"
			// and clt.id in
			// ("+createPerTypeId(cltypeTree,elClass.getCltype().getId())+")";
			sqlstr += " and clt.lid>=" + lid + " and clt.rid<=" + rid;
			if (elClass.getOwner() != null) {
				sqlstr += elClass.getOwner().getUsername() == null ? ""
						: " and elu.username like '%"
								+ elClass.getOwner().getUsername() + "%'";
				sqlstr += elClass.getOwner().getRealname() == null ? ""
						: " and elu.realname like '%"
								+ elClass.getOwner().getRealname() + "%'";
			}
			sqlstr += elClass.getStatus() == -1 ? "" : " and cl.status = "
					+ elClass.getStatus();
			sqlstr += (elClass.getName() == null || elClass.getName()
					.equals("")) ? "" : " and cl.name like '%"
					+ elClass.getName() + "%'";
			sqlstr += (elClass.getBegintime() == null && elClass.getEndtime() == null) ? ""
					: " and to_date(to_char(cl.releasetime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"
							+ elClass.getBegintime()
							+ "','yyyy-mm-dd') and to_date('"
							+ elClass.getEndtime() + "','yyyy-mm-dd')";
			sqlstr += " order by cl.createtime desc) t where rownum <= "
					+ pageNow + " ) where rn>=" + pageSize + "";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setCreatetime(rs.getTimestamp(8));
				cl.setAstatus(rs.getInt(10));
				cl.setIsApplication(rs.getInt("isApplication"));
				ELUser user = new ELUser();
				user.setRealname(rs.getString(9));
				cl.setCreater(user);
				if (isOpen(rs.getInt(1))) {
					cl.setOperation(1);// 考场全部开通
				} else {
					if (checkuserClassEroomOperation(rs.getInt(1), sqlw))
						cl.setOperation(2);// 有可操作
					else
						cl.setOperation(3);// 无可操作
				}
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班组合搜索列表失败!失败方法：listcombinationSearchClass(ElClass elClass,ElClType cltypeTree,String sqlw ,int pageNow,int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班组合搜索列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public int listcombinationSearchClassCount(ElClass elClass,
			ElClType cltypeTree, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;

		try {
			ct = DBConnection.getConnection();
			// name = name == null ? "" : name.trim();
			int lid = 0;
			int rid = 0;
			ps = ct
					.prepareStatement("select id,lid,rid from elclasstype where id =?");
			// ps.setInt(1, cltid);
			ps.setInt(1, elClass.getCltype().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			String sqlstr = "";
			sqlstr += "select count(*) from (select t.*, rownum rn from (select cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit, cl.status,clt.name cltname from elclass cl, elclasstype clt,eluser elu ";
			sqlstr += " where cl.cltype=clt.id and elu.id=cl.creater and cl.status >=0 ";
			// sqlstr+=elClass.getCltype()==null?"":(elClass.getCltype().getId()==-1||elClass.getCltype().getId()==0)?"":"
			// and clt.id in
			// ("+createPerTypeId(cltypeTree,elClass.getCltype().getId())+")";
			sqlstr += " and clt.lid>=" + lid + " and clt.rid<=" + rid;
			if (elClass.getOwner() != null) {
				sqlstr += elClass.getOwner().getUsername() == null ? ""
						: " and elu.username like '%"
								+ elClass.getOwner().getUsername() + "%'";
				sqlstr += elClass.getOwner().getRealname() == null ? ""
						: " and elu.realname like '%"
								+ elClass.getOwner().getRealname() + "%'";
			}
			sqlstr += elClass.getStatus() == -1 ? "" : " and cl.status = "
					+ elClass.getStatus();
			sqlstr += (elClass.getName() == null || elClass.getName()
					.equals("")) ? "" : " and cl.name like '%"
					+ elClass.getName() + "%'";
			sqlstr += (elClass.getBegintime() == null && elClass.getEndtime() == null) ? ""
					: " and to_date(to_char(cl.releasetime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"
							+ elClass.getBegintime()
							+ "','yyyy-mm-dd') and to_date('"
							+ elClass.getEndtime() + "','yyyy-mm-dd')";
			sqlstr += " order by cl.createtime desc) t )";// where rownum <=
			// "+pageNow+" )
			// where
			// rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班组合搜索列表失败!失败方法：listcombinationSearchClassCount(ElClass elClass,ElClType cltypeTree,int pageNow,int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("获取培训班组合搜索列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 培训班列表(不同状态,有权限)
	 */
	// public List<ElClass> getClassesList(ElClType tree, int deptid, int cltid,
	// String name, int status, int pageNow, int pageSize)
	// throws ElException {
	// PreparedStatement ps = null;
	// Connection ct = null;
	// ResultSet rs = null;
	// List<ElClass> cls = new ArrayList<ElClass>();
	// try {
	// name = name == null ? "" : name.trim();
	// ct = DBConnection.getConnection();
	//
	// Department dep = new Department();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
	// ps.setInt(1, deptid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// dep.setId(rs.getInt(1));
	// dep.setLid(rs.getInt(2));
	// dep.setRid(rs.getInt(3));
	// }
	//
	// rs.close();
	//
	// // 获取学员人数
	// Map map = new HashMap();
	// ps = ct
	// .prepareStatement("select sc.classid,count(userid) from study_class sc
	// group by sc.classid");
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// map.put(rs.getInt(1), rs.getInt(2));
	// }
	// rs.close();
	//
	// StringBuffer buffer = new StringBuffer();
	//
	// buffer
	// .append(
	// "select * from (select t.*, rownum rn from (select cl.id, cl.name,
	// cl.certificatename, cl.cltype, ")
	// .append(" cl.optionalcredit, cl.status, clt.name
	// cltname,u.username,cl.createtime ")
	// .append(
	// " from elclass cl, elclasstype clt, eluser u, department dep ")
	// .append(
	// " where cl.cltype = clt.id and cl.creater = u.id and u.depid = dep.id ")
	// .append(
	// " and cl.status = ? and cl.name like ? and dep.lid >= ? and dep.rid <= ?
	// ")
	// .append(
	// " and clt.id in (" + ctypeTypeId(tree, cltid)
	// + ") order by cl.createtime desc) t ")
	// .append(" where rownum <= ?) where rn >= ? ");
	//
	// ps = ct.prepareStatement(buffer.toString());
	// ps.setInt(1, status);
	// ps.setString(2, "%" + name + "%");
	// ps.setInt(3, dep.getLid());
	// ps.setInt(4, dep.getRid());
	// ps.setInt(5, pageNow);
	// ps.setInt(6, pageSize);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// int id = rs.getInt(1);
	// ElClass cl = new ElClass(id, rs.getString(2));
	// cl.setCertificatename(rs.getString(3));
	// cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
	// cl.setOptionalcredit(rs.getInt(5));
	// cl.setStatus(rs.getInt(6));
	// ElClType clType=new ElClType();
	// clType.setName(rs.getString(7));
	// cl.setCltype(clType);
	// ELUser user=new ELUser();
	// user.setUsername(rs.getString(8));
	// cl.setCreater(user);
	// cl.setCreatetime(rs.getTimestamp(9));
	// if (map.get(id) != null) {
	// cl.setStudentCount((Integer) map.get(id));
	// } else {
	// cl.setStudentCount(0);
	// }
	// cls.add(cl);
	// }
	//
	// } catch (Exception e) {
	// ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
	// ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET,
	// "获取培训班列表失败!失败方法：getClassesList(ElClType tree, int deptid, int
	// cltid,String name, int status, int pageNow, int pageSize) 失败原因："+new
	// ElException(e));
	// logger.error("获取培训班列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return cls;
	// }
	/**
	 * 查询出从ctid开始的有权限的培训班类型ID
	 * 
	 * @author luocw
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String ctypeTypeId(ElClType ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = getClassTypeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree != null && ctypeTree.getChild() != null) {
				return createTypeId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree != null ? ctypeTree.getId() : 0);
		} else {
			return null;
		}
	}

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

	/**
	 * 培训班列表大小(不同状态,有权限)
	 */
	public int getClassesSize(ElClType tree, int deptid, int cltid,
			ElClass elclass, String status, int role) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}

			String x = Integer.toString(cltid);
			String ids = ElClTypeById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String sqls = "";
			if (elclass != null) {
				if (elclass.getName() != null && !elclass.getName().equals("")) {// 培训名称
					sqls += " and cl.name like '%" + elclass.getName() + "%'";
				}
				if (elclass.getStatus() != -1) {// 考场状态
					sqls += " and cl.status=" + elclass.getStatus();
				}
				if (elclass.getBegintime() != null) {
					sqls += " and cl.STARTTIME >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (elclass.getEndtime() != null) {
					sqls += " and cl.FINISHTIME <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
			}

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select count (*) from elclass cl, elclasstype clt, eluser u, department dep ")
					.append(
							" where cl.cltype = clt.id   and cl.creater = u.id  and u.depid = dep.id  ")
					.append(
							"  and cl.status in (" + status
									+ ") and clt.id in ( "// and dep.lid >= ?
									// and dep.rid <= ?
									+ ids + " )" + sqls);

			ps = ct.prepareStatement(buffer.toString());

			// ps.setInt(1, dep.getLid());
			// ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"我的培训班数量失败!失败方法：getClassesSize(ElClType tree, int deptid, int cltid,ElClass elclass, String status ,int role) 失败原因："
									+ new ElException(e));
			logger.error("我的培训班数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int getClassesSize2(ElClType tree, int deptid, int cltid,
			ElClass elclass, String status, int role) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// Department dep = new Department();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			// ps.setInt(1, deptid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// dep.setId(rs.getInt(1));
			// dep.setLid(rs.getInt(2));
			// dep.setRid(rs.getInt(3));
			// }

			String x = Integer.toString(cltid);
			String ids = ElClTypeById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String sqls = "";
			if (elclass != null) {
				if (elclass.getName() != null && !elclass.getName().equals("")) {// 培训名称
					sqls += " and cl.name like '%" + elclass.getName() + "%'";
				}
				if (elclass.getStatus() != -1) {// 状态
					sqls += " and cl.status=" + elclass.getStatus();
				}
				if (elclass.getBegintime() != null) {
					sqls += " and cl.STARTTIME >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (elclass.getEndtime() != null) {
					sqls += " and cl.FINISHTIME <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
			}
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select count (*) from elclass cl, elclasstype clt, eluser u, department dep ")
					.append(
							" where cl.cltype = clt.id   and cl.creater = u.id  and u.depid = dep.id  ")
					.append(
							"   and cl.status in (" + status
									+ ") and clt.id in ( "// and dep.lid >= ?
									// and dep.rid <= ?
									+ ids + " )" + sqls);

			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, dep.getLid());
			// ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法：getClassesSize2(ElClType tree, int deptid, int cltid,ElClass elclass,String status,int role) 失败原因："
									+ new ElException(e));
			logger.error("我的培训班数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int getClassesSize3(ElClType tree, int deptid, int cltid,
			ElClass elclass, String status, int role) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String x = Integer.toString(cltid);
			String ids = ElClTypeById(tree, cltid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String sqls = "";
			if (elclass != null) {
				if (elclass.getName() != null && !elclass.getName().equals("")) {// 培训名称
					sqls += " and cl.name like '%" + elclass.getName() + "%'";
				}
				if (elclass.getStatus() != -1) {// 考场状态
					sqls += " and cl.status=" + elclass.getStatus();
				}
				if (elclass.getBegintime() != null) {
					sqls += " and cl.STARTTIME >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (elclass.getEndtime() != null) {
					sqls += " and cl.FINISHTIME <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(elclass.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
			}

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select count (*) from elclass cl, elclasstype clt, eluser u, department dep ")
					.append(
							" where cl.cltype = clt.id   and cl.creater = u.id  and u.depid = dep.id  ")
					.append(
							"  and cl.status in (" + status
									+ ") and clt.id in ( "//
									+ ids + " )" + sqls);

			ps = ct.prepareStatement(buffer.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法： 失败原因：getClassesSize3(ElClType tree, int deptid, int cltid,	ElClass elclass, String status ,int role)"
									+ new ElException(e));
			logger.error("我的培训班数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 培训班列表(不同状态)
	 */
	// public List<ElClass> getClassesList(int deptid, int cltid, String name,
	// int status, int pageNow, int pageSize) throws ElException {
	// // cl.id,cl.name,cl.certificatename,cl.cltype,cl.optionalcredit,
	// // cl.status
	// PreparedStatement ps = null;
	// Connection ct = null;
	// ResultSet rs = null;
	// List<ElClass> cls = new ArrayList<ElClass>();
	// try {
	// name = name == null ? "" : name.trim();
	// ct = DBConnection.getConnection();
	//
	// Department dep = new Department();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
	// ps.setInt(1, deptid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// dep.setId(rs.getInt(1));
	// dep.setLid(rs.getInt(2));
	// dep.setRid(rs.getInt(3));
	// }
	//
	// int clid = 0, crid = 0;
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(ClassConstants.CLTYPE_LIRID));
	// ps.setInt(1, cltid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// clid = rs.getInt(2);
	// crid = rs.getInt(3);
	// }
	//
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(ClassConstants.CLASS_MAN_MYSHLIST));
	// ps.setInt(1, status);
	// ps.setString(2, "%" + name + "%");
	// ps.setInt(3, dep.getLid());
	// ps.setInt(4, dep.getRid());
	// ps.setInt(5, clid);
	// ps.setInt(6, crid);
	// ps.setInt(7, pageNow);
	// ps.setInt(8, pageSize);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
	// cl.setCertificatename(rs.getString(3));
	// cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
	// cl.setOptionalcredit(rs.getInt(5));
	// cl.setStatus(rs.getInt(6));
	// cls.add(cl);
	// }
	// } catch (Exception e) {
	// ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
	// ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET,
	// "获取培训班失败!失败方法：getClassesList(int deptid, int cltid, String name, int
	// status, int pageNow, int pageSize) 失败原因："+new ElException(e));
	// logger.error("获取培训班列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return cls;
	// }
	/**
	 * 培训班列表大小(不同状态)
	 */
	// public int getClassesSize(int deptid, int cltid, String name, int status)
	// throws ElException {
	// PreparedStatement ps = null;
	// Connection ct = null;
	// ResultSet rs = null;
	// try {
	// ct = DBConnection.getConnection();
	// Department dep = new Department();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
	// ps.setInt(1, deptid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// dep.setId(rs.getInt(1));
	// dep.setLid(rs.getInt(2));
	// dep.setRid(rs.getInt(3));
	// }
	//
	// int clid = 0, crid = 0;
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(ClassConstants.CLTYPE_LIRID));
	// ps.setInt(1, cltid);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// clid = rs.getInt(2);
	// crid = rs.getInt(3);
	// }
	//
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(ClassConstants.CLASS_MAN_MYSHLISTSIZE));
	// ps.setInt(1, status);
	// ps.setString(2, "%" + name + "%");
	// ps.setInt(3, dep.getLid());
	// ps.setInt(4, dep.getRid());
	// ps.setInt(5, clid);
	// ps.setInt(6, crid);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
	// ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET,
	// "我的培训班数量失败!失败方法：getClassesSize(int deptid, int cltid, String name, int
	// status) 失败原因："+new ElException(e));
	// logger.error("我的培训班数量失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }
	public int listClassesSize(int userid, int cltid, String name)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			int lid = 0;
			int rid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLTYPE_LIRID));
			ps.setInt(1, cltid);
			rs = ps.executeQuery();
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			if (userid == 0) {
				ps = ct
						.prepareStatement("select count(*) from elclass cl,elclasstype clt where clt.lid>=? and clt.rid<=? and cl.cltype=clt.id and cl.name  like ? and   cl.status!=9");
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setString(3, "%" + name + "%");
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(ClassConstants.CLASS_MAN_MYLISTSIZE));
				ps.setInt(1, lid);
				ps.setInt(2, rid);
				ps.setInt(3, userid);
				ps.setString(4, "%" + name + "%");
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"我的培训班数量失败!失败方法：listClassesSize(int userid, int cltid, String name) 失败原因："
							+ new ElException(e));
			logger.error("我的培训班数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ElClass> listClassFromSuper(int depid, String name,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGN_SUPER));
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, depid);
			ps.setInt(3, ClassConstants.CLASS_STATUS_HASOPENED);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法：listClassFromSuper(int depid, String name,	int pageNow, int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<ElClass> listClassFromThis(int depid, String name, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGN_THIS));
			// ps.setInt(1, CourseConstants.COURSE_STUDY_STATUS_BX);
			// ps.setInt(2, CourseConstants.COURSE_STUDY_STATUS_BX);
			// ps.setInt(3, CourseConstants.COURSE_STUDY_STATUS_XX);
			// ps.setInt(4, CourseConstants.COURSE_STUDY_STATUS_XX);
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, ClassConstants.CLASS_STATUS_HASOPENED);
			ps.setInt(3, dep.getLid());
			ps.setInt(4, dep.getRid());
			ps.setInt(5, pageNow);
			ps.setInt(6, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				// cl.setBxCount(rs.getInt(8));
				// cl.setBxCredit(rs.getInt(9));
				// cl.setXxCount(rs.getInt(10));
				// cl.setXxCredit(rs.getInt(11));
				// cl.setUserCount(rs.getInt(12));
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"我的培训班列表失败!失败方法：listClassFromThis(int depid, String name, int pageNow,	int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public List<Course> listClassCourses(int classid, int status)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			String sql = "select  c.id, c.name, c.credit, cc.credit, c.createtime,  c.during, ct.id, ct.name, el.id, el.realname,"
					+ "cc.suggestcredit,cc.setcredit,cc.getcredit,c.roomstart,c.roomend,cc.starttime,cc.finishtime,cc.isdel,c.teacherName,c.islink from course c, class_course cc, course_type ct,eluser el  where cc.courseid = c.id and c.creater = el.id and c.ctypeid=ct.id and cc.classid = ? and cc.status = ?";
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_COURSE));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, status);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setDefalutcredit(rs.getInt(3));
				c.setCredit(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setDuring(rs.getInt(6));
				c.setCtype(new CourseType(rs.getInt(7), rs.getString(8)));
				c.setCreater(new ELUser(rs.getInt(9), rs.getString(10)));
				c.setSuggestcredit(rs.getInt(11));
				c.setSetcredit(rs.getInt(12));
				c.setGetcredit(rs.getInt(13));
				// c.setRoomstart(rs.getTimestamp(14));
				// c.setRoomend(rs.getTimestamp(15));
				c.setRoomstart(rs.getTimestamp(16));
				c.setRoomend(rs.getTimestamp(17));
				c.setIsDel(rs.getInt(18));
				c.setTeacherName(rs.getString(19));
				c.setIslink(rs.getInt(20));
				cs.add(c);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"我的培训班课程列表失败!失败方法：listClassCourses(int classid, int status) 失败原因："
							+ new ElException(e));
			logger.error("我的培训班课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}

	/**
	 * 培训班课程列表(不显示已删除的)
	 * 
	 * @param classid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listClassCourses2(int classid, int status)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			String sql = "select  c.id, c.name, c.credit, cc.credit, c.createtime,  c.during, ct.id, ct.name, el.id, el.realname,cc.suggestcredit,cc.setcredit,cc.getcredit,c.roomstart,c.roomend,cc.starttime,cc.finishtime from course c, class_course cc, course_type ct,eluser el  where cc.courseid = c.id and c.creater = el.id and c.ctypeid=ct.id and cc.classid = ? and cc.status = ? and cc.isdel!=-1";
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_COURSE));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, status);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setDefalutcredit(rs.getInt(3));
				c.setCredit(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setDuring(rs.getInt(6));
				c.setCtype(new CourseType(rs.getInt(7), rs.getString(8)));
				c.setCreater(new ELUser(rs.getInt(9), rs.getString(10)));
				c.setSuggestcredit(rs.getInt(11));
				c.setSetcredit(rs.getInt(12));
				c.setGetcredit(rs.getInt(13));
				// c.setRoomstart(rs.getTimestamp(14));
				// c.setRoomend(rs.getTimestamp(15));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				cs.add(c);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"我的培训班课程列表失败!失败方法：listClassCourses2(int classid, int status) 失败原因："
							+ new ElException(e));
			logger.error("我的培训班课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}

	public List<Course> listClassCoursesPage(int classid, int status,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_COURSE_PAGE));
			ps.setInt(1, classid);
			ps.setInt(2, status);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setDefalutcredit(rs.getInt(3));
				c.setCredit(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setDuring(rs.getInt(6));
				c.setCtype(new CourseType(rs.getInt(7), rs.getString(8)));
				c.setCreater(new ELUser(rs.getInt(9), rs.getString(10)));
				c.setSuggestcredit(rs.getInt(11));
				c.setSetcredit(rs.getInt(12));
				c.setGetcredit(rs.getInt(13));
				cs.add(c);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"我的培训班课程列表失败!失败方法：listClassCoursesPage(int classid, int status,int pageNow, int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("我的培训班课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}

	public int listClassCoursesPageSize(int classid, int status)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_COURSE));
			ps.setInt(1, classid);
			ps.setInt(2, status);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setDefalutcredit(rs.getInt(3));
				c.setCredit(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setDuring(rs.getInt(6));
				c.setCtype(new CourseType(rs.getInt(7), rs.getString(8)));
				c.setCreater(new ELUser(rs.getInt(9), rs.getString(10)));
				c.setSuggestcredit(rs.getInt(11));
				c.setSetcredit(rs.getInt(12));
				c.setGetcredit(rs.getInt(13));
				cs.add(c);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"我的培训班课程列表失败!失败方法：listClassCoursesPageSize(int classid, int status) 失败原因："
							+ new ElException(e));
			logger.error("我的培训班课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Course> listAllClassCourse(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select dep.lid,dep.rid from department dep,eluser eu where "
							+ "eu.depid = dep.id and eu.id = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_COURSE_ALL));
			// ps.setInt(1, userid);
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				cs.add(new Course(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：listAllClassCourse(int classid, int userid) 失败原因："
							+ new ElException(e));
			logger.error("我的培训班课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}

	public void addClassCourse(int classid, int courseid, int status)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			int suggestcredit = 0;
			ps = ct.prepareStatement("select credit from course where id= ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				suggestcredit = rs.getInt(1);
			}

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_COURSE_ADD));
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, status);
			ps.setInt(4, suggestcredit);

			ps.executeUpdate();

			ps = ct.prepareStatement("call assign_class_course (?,?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, status);
			ps.executeUpdate();

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：addClassCourse(int classid, int courseid, int status) 失败原因："
							+ new ElException(e));
			logger.error("添加培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addClassCourse2(int classid, int courseid, int status,
			Timestamp starttime, Timestamp finishtime) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			int suggestcredit = 0;
			ps = ct.prepareStatement("select credit from course where id= ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				suggestcredit = rs.getInt(1);
			}

			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_COURSE_ADD));
			String sql = "insert into class_course(classid,courseid,status,suggestcredit,starttime,finishtime,getcredit) values(?,?,?,?,?,?,1)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, status);
			ps.setInt(4, suggestcredit);
			ps.setTimestamp(5, starttime);
			ps.setTimestamp(6, finishtime);

			ps.executeUpdate();

			ps = ct.prepareStatement("call assign_class_course2 (?,?,?,?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, status);
			ps.setTimestamp(4, starttime);
			ps.setTimestamp(5, finishtime);
			ps.executeUpdate();

		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法：addClassCourse2(int classid, int courseid, int status,Timestamp starttime,Timestamp finishtime) 失败原因："
									+ new ElException(e));
			logger.error("添加培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addClassCourse2(int classid, int courseid, int status,
			int getcredit, int setcredit, int suggestcredit,
			Timestamp starttime, Timestamp finishtime) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// int suggestcredit = 0;
			// ps = ct.prepareStatement("select credit from course where id=
			// ?");
			// ps.setInt(1, courseid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// suggestcredit = rs.getInt(1);
			// }

			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_COURSE_ADD));
			String sql = "insert into class_course(classid,courseid,status,suggestcredit,starttime,finishtime,getcredit,setcredit) values(?,?,?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, status);
			ps.setInt(4, suggestcredit);
			ps.setTimestamp(5, starttime);
			ps.setTimestamp(6, finishtime);
			ps.setInt(7, getcredit);
			ps.setInt(8, setcredit);
			ps.executeUpdate();

			ps = ct.prepareStatement("call assign_class_course2 (?,?,?,?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, status);
			ps.setTimestamp(4, starttime);
			ps.setTimestamp(5, finishtime);
			ps.executeUpdate();

		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法：addClassCourse2(int classid, int courseid, int status,Timestamp starttime,Timestamp finishtime) 失败原因："
									+ new ElException(e));
			logger.error("添加培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteClassCourse(int classid, int courseid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_COURSE_DELETE));
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.executeUpdate();
			// ps = ct.prepareStatement("call unassign_class_course (?,?)");
			ps = ct.prepareStatement("call unassign_class_course2 (?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.executeUpdate();

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_DELETE,
					"删除培训班课程失败!失败方法：deleteClassCourse(int classid, int courseid) 失败原因："
							+ new ElException(e));
			logger.error("删除培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 删除培训班课程(假删除)
	 * 
	 * @param classid
	 * @param courseid
	 * @throws ElException
	 */
	public void deleteClassCourse2(int classid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update class_course set isdel=-1 where classid=? and courseid=?");
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_DELETE, "删除培训班课程失败!失败方法： 失败原因："
							+ new ElException(e));
			logger.error("删除培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 恢复培训班课程
	 * 
	 * @param classid
	 * @param courseid
	 * @throws ElException
	 */
	public void restorationClassCourse(int classid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update class_course set isdel=0 where classid=? and courseid=?");
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ALTER, "获取培训班失败!失败方法： 失败原因："
							+ new ElException(e));
			logger.error("恢复培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Course getClassCourse(int classid, int courseid) throws ElException {

		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Course c = null;
		try {
			String sql = "select  c.id, c.name, c.credit, cc.credit, c.createtime,  c.during, ct.id, ct.name, el.id, el.realname,cc.suggestcredit,cc.setcredit,cc.getcredit,c.roomstart,c.roomend,cc.starttime,cc.finishtime,cc.isdel,c.teacherName,cc.orderid,cc.firstlearn "
					+ " from course c, class_course cc, course_type ct,eluser el  where cc.courseid = c.id and c.creater = el.id and c.ctypeid=ct.id and cc.classid = ? and cc.courseid = ?";
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_COURSE));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				c = new Course(rs.getInt(1), rs.getString(2));
				c.setDefalutcredit(rs.getInt(3));
				c.setCredit(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setDuring(rs.getInt(6));
				c.setCtype(new CourseType(rs.getInt(7), rs.getString(8)));
				c.setCreater(new ELUser(rs.getInt(9), rs.getString(10)));
				c.setSuggestcredit(rs.getInt(11));
				c.setSetcredit(rs.getInt(12));
				c.setGetcredit(rs.getInt(13));
				c.setRoomstart(rs.getTimestamp("starttime"));
				c.setRoomend(rs.getTimestamp("finishtime"));
				c.setIsDel(rs.getInt("isdel"));
				c.setTeacherName(rs.getString("teacherName"));
				c.setOrderid(rs.getInt("orderid"));
				c.setFirstLearn(rs.getInt("firstlearn"));
			}
		} catch (Exception e) {
			logger.error("获取我的培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
	}

	public void alterClassCourseCredit(Course course, int classid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_COURSE_CREDIT_ALTER));
			ps.setInt(1, course.getCredit());
			ps.setInt(2, course.getId());
			ps.setInt(3, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"修改培训班课程学分失败!失败方法：deleteClassCourse2(int classid, int courseid) 失败原因："
							+ new ElException(e));
			logger.error("修改培训班课程学分失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void applyClassDelete(int classid, int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_APPLY_DELETE));
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：applyClassDelete(int classid, int userid) 失败原因："
							+ new ElException(e));
			logger.error("申请删除培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> listCanAssignUsers(int classid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> eus = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_CANASSIGN_USER));
			ps.setInt(1, classid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				eus.add(eu);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：listCanAssignUsers(int classid, int depid) 失败原因："
							+ new ElException(e));
			logger.error("可分配培训班的用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	private Department getDepById(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setManager(new ELUser(rs.getInt(5), rs.getString(11)));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setAddress(rs.getString(6));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：getDepById(int id)  失败原因："
							+ new ElException(e));
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	private Department getDepTree(int cid, int did, int type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = null;
		try {
			if (did == 0) {
				dep = getDepById(1);
			} else {
				dep = getDepById(did);
			}
			ct = DBConnection.getConnection();
			if (type == 1)
				dep.setUsers(listCanAssignUsers(cid, dep.getId()));
			if (type == 2)
				dep.setUsers(listAssignedUsers(cid, dep.getId()));
			dep.setChild(listDepartmentsById(cid, dep.getId(), 0, ct, type));

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取部门树失败!失败方法：getDepTree(int cid, int did, int type) 失败原因："
							+ new ElException(e));
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	private List<Department> listDepartmentsById(int cid, int parentid,
			int level, Connection ct, int type) throws Exception {
		List<Department> deps = new ArrayList<Department>();
		PreparedStatement pstemp = ct
				.prepareStatement("select id,name,parentid from department where parentid = ?");
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			Department dep = new Department(rstemp.getInt(1), rstemp
					.getString(2));
			dep.setParent(new Department(rstemp.getInt(3)));
			dep.setLevel(level);
			dep
					.setChild(listDepartmentsById(cid, dep.getId(), level, ct,
							type));
			if (type == 1)
				dep.setUsers(listCanAssignUsers(cid, dep.getId()));
			if (type == 2)
				dep.setUsers(listAssignedUsers(cid, dep.getId()));
			deps.add(dep);
		}
		rstemp.close();
		pstemp.close();
		return deps;
	}

	public void unassignDepsAll(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from class_assign where classid =?");
			ps.setInt(1, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_DELETE,
					"删除分配到部门失败!失败方法：unassignDepsAll(int classid) 失败原因："
							+ new ElException(e));
			logger.error("删除分配到部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Department listAssignedDep(int depid, int classid)
			throws ElException {
		return getDepTree(classid, depid, 2);
	}

	public Department listCanAssignDep(int depid, int classid)
			throws ElException {
		return getDepTree(classid, depid, 1);
	}

	public List<ELUser> listAssignedUsers(int classid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> eus = new ArrayList<ELUser>();
		try {
			// Department dep = new Department();
			ct = DBConnection.getConnection();
			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(DUConstants.DEP_QUERY_LRID_BYID)); ps.setInt(1, depid);
			 * rs = ps.executeQuery(); if (rs.next()) { dep.setId(rs.getInt(1));
			 * dep.setLid(rs.getInt(2)); dep.setRid(rs.getInt(3)); } rs.close();
			 */
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGNED_USER));

			ps.setInt(1, classid);
			ps.setInt(2, depid);// dep.getLid());
			// ps.setInt(3, dep.getRid());
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				eu.setUsername(rs.getString(3));
				eus.add(eu);
				// eus.add(new ELUser(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET, "获取培训班失败!失败方法： 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public void assign2userDelete(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_ASSIGN2USER_DELETE));
			ps = ct.prepareStatement("call unassign_class ( ?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.executeUpdate();
			// assignCourse2UserDelete(ct, classid, userid);
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_DELETE,
					"已分配培训班的用户删除失败!失败方法：assign2userDelete(int userid, int classid) 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的用户删除失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * private void assignCourse2UserDelete(Connection ct, int classid, int
	 * userid) throws ElException { PreparedStatement ps = null; ResultSet rs =
	 * null; try { ct = DBConnection.getConnection(); // 班级下的课程 ps =
	 * ct.prepareStatement(ElQuerySql
	 * .getSQL(ClassConstants.CLASS_ASSIGN2USER_COURSE_BYCLID)); ps.setInt(1,
	 * classid); rs = ps.executeQuery(); while (rs.next()) { int courseid =
	 * rs.getInt(1); PreparedStatement ps1 = ct.prepareStatement(ElQuerySql
	 * .getSQL(CourseConstants.COURSE_ASSIGNE2USER_DELETE)); ps1.setInt(1,
	 * courseid); ps1.setInt(2, userid); ps1.executeUpdate(); ps1.close(); }
	 * rs.close(); ps.close(); } catch (Exception e) { logger.error("分配用户失败！",
	 * e); throw new ElException(e); } }
	 */
	public void assign2userAdd(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// String sql="call assign_class3 (?,?)";
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGN2USER_ADD));
			// ps=ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.executeUpdate();

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"已分配培训班的用户添加成功!失败方法：assign2userAdd(int userid, int classid) 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的用户添加成功！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void assign2userAdd2(int userid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGN2USER_ADD));
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.executeUpdate();

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"已分配培训班的用户添加成功!失败方法：assign2userAdd2(int userid, int classid) 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的用户添加成功！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void assign2userAdd3(int userid, int classid, int joinway)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "call assign_class3 (?,?,?)";
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_ASSIGN2USER_ADD));
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setInt(3, joinway);
			ps.executeUpdate();
			// 更新学员培训班报名状态
			((StudyClassDao) SpringContextUtil.getBean("studyClassDao"))
					.udpateStudyClassApplyStatus(classid, userid, 3);
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"已分配培训班的用户添加成功!失败方法：assign2userAdd2(int userid, int classid) 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的用户添加成功！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void assignCourse2User(Connection ct, int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// 班级下的课程
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGN2USER_COURSE_BYCLID));
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int courseid = rs.getInt(1);
				int status = rs.getInt(2);
				PreparedStatement ps1 = ct.prepareStatement(ElQuerySql
						.getSQL(CourseConstants.COURSE_USER_CHECK));
				ps1.setInt(1, courseid);
				ps1.setInt(2, userid);
				ResultSet rs1 = ps1.executeQuery();
				if (!rs1.next()) {
					ps1 = ct.prepareStatement(ElQuerySql
							.getSQL(CourseConstants.COURSE_ASSIGNE2USER));
					ps1.setInt(1, courseid);
					ps1.setInt(2, userid);
					ps1.setTimestamp(3, new Timestamp(System
							.currentTimeMillis()));
					ps1.setInt(4, status);
					ps1.executeUpdate();
				}
				ps1.close();

			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"分配用户失败!失败方法：assignCourse2User(Connection ct, int classid, int userid) 失败原因："
							+ new ElException(e));
			logger.error("分配用户失败！", e);
			throw new ElException(e);
		}
	}

	public List<Department> listAssignedDeps(int classid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			Department dep = new Department();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGNED_DEPS));

			ps.setInt(1, classid);
			ps.setInt(2, dep.getLid());
			ps.setInt(3, dep.getRid());
			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new Department(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET, "获取培训班失败!失败方法： 失败原因："
							+ new ElException(e));
			logger.error("可分配培训班的部门列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<Department> listCanAssignDeps(int classid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			Department dep = new Department();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_CANASSIGN_DEPS));

			ps.setInt(1, classid);
			ps.setInt(2, dep.getLid());
			ps.setInt(3, dep.getRid());
			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new Department(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"已分配培训班的部门列表!失败方法：listCanAssignDeps(int classid, int depid) 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的部门列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public void assign2depAdd(int depid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGN2DEP_ADD));
			ps.setInt(1, classid);
			ps.setInt(2, depid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"已分配培训班的部门添加失败!失败方法：assign2depAdd(int depid, int classid) 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的部门添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void assign2depDelete(int depid, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_ASSIGN2DEP_DELETE));
			ps.setInt(1, classid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_DELETE,
					"已分配培训班的部门删除失败!失败方法：assign2depDelete(int depid, int classid) 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的部门删除失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ElClass> listCanApplyClassFromSuper(String name, int userid,
			int pageNow, int pageSize) throws ElException {

		return null;
	}

	public List<ElClass> listCanApplyClassFromThis(String name, int userid,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_QUERY_BYID));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(6));
			}
			/*
			 * rs.close(); ct = DBConnection.getConnection(); ps =
			 * ct.prepareStatement(ElQuerySql
			 * .getSQL(ClassConstants.DEP_QUERY_LRID_BYID));
			 * ps.setInt(1,dep.getId()); rs = ps.executeQuery(); if (rs.next()) {
			 * dep.setId(rs.getInt(1)); dep.setLid(rs.getInt(2));
			 * dep.setRid(rs.getInt(3)); }
			 */
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, dep.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_APPLY_SELECT));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			Map map = new HashMap();
			while (rs.next()) {
				map.put(rs.getInt(1), rs.getInt(1));
			}
			rs.close();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_APPLY_THIS));
			// ps.setInt(1, CourseConstants.COURSE_STUDY_STATUS_BX);
			// ps.setInt(2, CourseConstants.COURSE_STUDY_STATUS_BX);
			// ps.setInt(3, CourseConstants.COURSE_STUDY_STATUS_XX);
			// ps.setInt(4, CourseConstants.COURSE_STUDY_STATUS_XX);
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, ClassConstants.CLASS_STATUS_HASOPENED);
			ps.setInt(3, dep.getLid());
			ps.setInt(4, dep.getRid());
			// ps.setInt(5, userid);
			ps.setInt(5, pageNow);
			ps.setInt(6, pageSize);
			rs = ps.executeQuery();
			int classId;
			while (rs.next()) {
				classId = (int) rs.getInt(1);
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				// cl.setBxCount(rs.getInt(8));
				// cl.setBxCredit(rs.getInt(9));
				// cl.setXxCount(rs.getInt(10));
				// cl.setXxCredit(rs.getInt(11));
				// cl.setUserCount(rs.getInt(12));
				if (map.get(classId) != null) {
					cl.setIsSelect(1);
				} else {
					cl.setIsSelect(0);
				}
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"我的培训班列表失败!失败方法：listCanApplyClassFromThis(String name, int userid,	int pageNow, int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public void applyClass(int classid, int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_APPLY));
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			int status = ClassConstants.CLASS_APPLY_STATUS_WAIT;
			if (!SystemConfOp.getBooleanValue(ElConstants.STUDY_CLASS_NEED_SH)) {
				status = ClassConstants.CLASS_APPLY_STATUS_YES;
			}
			ps.setInt(4, status);
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"已分配培训班的用户添加失败!失败方法：applyClass(int classid, int userid) 失败原因："
							+ new ElException(e));
			logger.error("已分配培训班的用户添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ElClass> listApplyedClass(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			Department dep = new Department();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_APPLYED));
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			ps.setInt(3, ClassConstants.CLASS_APPLY_STATUS_WAIT);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			// cl.id,cl.name,cl.certificatename ,eu.id,eu.name
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setStudent(new ELUser(rs.getInt(4), rs.getString(5)));
				cls.add(cl);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_ADD,
							"已分配培训班的用户添加失败!失败方法：listApplyedClass(int depid, int pageNow, int pageSize) 失败原因："
									+ new ElException(e));
			logger.error("已分配培训班的用户添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	// public List<ElClass> listMyStudyClass(int userid, int pageNow, int
	// pageSize)
	// throws ElException {
	// PreparedStatement ps = null;
	// Connection ct = null;
	// ResultSet rs = null;
	// List<ElClass> cls = new ArrayList<ElClass>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(ClassConstants.CLASS_MY_STUDY));
	// ps.setString(1, ClassConstants.CLASS_COURSE_STATUS_BX);
	// ps.setString(2, ClassConstants.CLASS_COURSE_STATUS_BX);
	// ps.setString(3, ClassConstants.CLASS_COURSE_STATUS_XX);
	// ps.setString(4, ClassConstants.CLASS_COURSE_STATUS_XX);
	// ps.setInt(5, userid);
	// ps.setString(6, ClassConstants.CLASS_APPLY_STATUS_YES);
	// ps.setInt(7, pageNow * pageSize);
	// ps.setInt(8, pageSize);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
	// cl.setCertificatename(rs.getString(3));
	// cl.setBxCount(rs.getInt(4));
	// cl.setBxCredit(rs.getInt(5));
	// cl.setXxCount(rs.getInt(6));
	// cl.setXxCredit(rs.getInt(7));
	// cl.setStuCount(rs.getInt(8));
	// cl.setApplyDate(rs.getDate(9));
	// cl.setOptionalcredit(rs.getInt(10));
	// cls.add(cl);
	// }
	// } catch (Exception e) {
	// logger.error("在学培训班列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return cls;
	// }
	// public List<MyClass> listMyCanCraduateClass(int userid) throws
	// ElException {
	// PreparedStatement ps = null;
	// Connection ct = null;
	// ResultSet rs = null;
	// List<MyClass> cls = new ArrayList<MyClass>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(ClassConstants.CLASS_MY_STUDY));
	// ps.setString(1, ClassConstants.CLASS_COURSE_STATUS_BX);
	// ps.setString(2, ClassConstants.CLASS_COURSE_STATUS_BX);
	// ps.setString(3, ClassConstants.CLASS_COURSE_STATUS_XX);
	// ps.setString(4, ClassConstants.CLASS_COURSE_STATUS_XX);
	// ps.setInt(5, userid);
	// ps.setString(6, ClassConstants.CLASS_APPLY_STATUS_YES);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// MyClass cl = new MyClass();
	//				
	//				 
	// cls.add(cl);
	// }
	// } catch (Exception e) {
	// logger.error("在学培训班列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return null;
	// }
	public ElClass getElClassById(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_BYUID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(8)));
				cl.setDescription(rs.getString(5));
				cl.setOptionalcredit(rs.getInt(6));
				cl.setStatus(rs.getInt(7));
				cl.setCreater(new ELUser(rs.getInt(9), rs.getString(10)));
				cl.setGlobal(rs.getInt(11));
				cl.setGroup1(new ElGroup(rs.getInt(12)));
				cl.setGroup2(new ElGroup(rs.getInt(13)));
				cl.setDiplomatime(rs.getTimestamp(14));
				cl.setClasstype(rs.getInt("classtype"));
				return cl;
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：getElClassById(int id) 失败原因："
							+ new ElException(e));
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return new ElClass();
	}
	
	public ElClass getElClassByName(String name) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_BYUNAME));
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(8)));
				cl.setDescription(rs.getString(5));
				cl.setOptionalcredit(rs.getInt(6));
				cl.setStatus(rs.getInt(7));
				cl.setCreater(new ELUser(rs.getInt(9), rs.getString(10)));
				cl.setGlobal(rs.getInt(11));
				cl.setGroup1(new ElGroup(rs.getInt(12)));
				cl.setGroup2(new ElGroup(rs.getInt(13)));
				cl.setDiplomatime(rs.getTimestamp(14));
				cl.setClasstype(rs.getInt("classtype"));
				return cl;
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：getElClassById(int id) 失败原因："
							+ new ElException(e));
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return new ElClass();
	}

	public ElClass getElClassById_cisco(int id, int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_BYUID_CISCO));
			ps.setInt(1, id);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(8)));
				cl.setDescription(rs.getString(5));
				cl.setOptionalcredit(rs.getInt(6));
				cl.setStatus(rs.getInt(7));
				cl.setCreater(new ELUser(rs.getInt(9), rs.getString(10)));
				cl.setGlobal(rs.getInt(11));
				cl.setGroup1(new ElGroup(rs.getInt(12)));
				cl.setGroup2(new ElGroup(rs.getInt(13)));
				cl.setDiplomatime(rs.getTimestamp(14));
				cl.setClasstype(rs.getInt(15));
				cl.setApplyDate(rs.getTimestamp(16));
				return cl;
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班失败!失败方法：getElClassById(int id) 失败原因："
							+ new ElException(e));
			logger.error("获取培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return new ElClass();
	}

	public void setClassApplyStatus(int classid, int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_APPLY_STATUS_SET));
			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			ps.executeUpdate();
			if (status == ClassConstants.CLASS_APPLY_STATUS_YES)
				assignCourse2User(ct, classid, userid);
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"设置培训班状态失败!失败方法：setClassApplyStatus(int classid, int userid, int status) 失败原因："
							+ new ElException(e));
			logger.error("设置培训班状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setClassStatus(int classid, int status) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_STATUS_SET));
			ps.setInt(1, status);
			// ps.setInt(2, userid);
			ps.setInt(2, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"设置培训班状态失败!失败方法：setClassStatus(int classid, int status) 失败原因："
							+ new ElException(e));
			logger.error("设置培训班状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteClass(int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from class_course where classid = ?");
			ps.setInt(1, classid);
			ps.executeUpdate();
			ps = ct
					.prepareStatement("delete from class_assign where classid = ?");
			ps.setInt(1, classid);
			ps.executeUpdate();
			ps = ct
					.prepareStatement("delete from study_class where classid = ?");
			ps.setInt(1, classid);
			ps.executeUpdate();
			ps = ct.prepareStatement("delete from elclass where id = ?");
			ps.setInt(1, classid);
			ps.executeUpdate();

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_DELETE,
					"删除培训班失败!失败方法：deleteClass(int classid) 失败原因："
							+ new ElException(e));
			logger.error("	删除培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 培训班删除待确认列表
	 */
	public List<ElClass> listDeleteApplyClass(int depid, int typeid,
			String name, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
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

			int clid = 0, crid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLTYPE_LIRID));
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			if (rs.next()) {
				clid = rs.getInt(2);
				crid = rs.getInt(3);
			}

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_DELTE_APPLY_LIST));

			ps.setInt(1, ClassConstants.CLASS_STATUS_DELETE_WAIT);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, dep.getLid());
			ps.setInt(4, dep.getRid());
			ps.setInt(5, clid);
			ps.setInt(6, crid);
			ps.setInt(7, pageNow);
			ps.setInt(8, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				// cl.setBxCount(rs.getInt(4));
				// cl.setBxCredit(rs.getInt(5));
				// cl.setXxCount(rs.getInt(6));
				// cl.setXxCredit(rs.getInt(7));
				// cl.setUserCount(rs.getInt(8));
				cl.setOptionalcredit(rs.getInt(4));
				cl.setCreater(new ELUser(rs.getInt(5), rs.getString(6)));
				cls.add(cl);
			}
		} catch (Exception e) {
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 培训班删除列表大小
	 * 
	 * @param deptid
	 * @param cltid
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public int listDeleteApplyClassSize(int deptid, int cltid, String name)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}

			int clid = 0, crid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLTYPE_LIRID));
			ps.setInt(1, cltid);
			rs = ps.executeQuery();
			if (rs.next()) {
				clid = rs.getInt(2);
				crid = rs.getInt(3);
			}

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_DELTE_APPLY_LIST_SIZE));

			ps.setInt(1, ClassConstants.CLASS_STATUS_DELETE_WAIT);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, dep.getLid());
			ps.setInt(4, dep.getRid());
			ps.setInt(5, clid);
			ps.setInt(6, crid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的培训班数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<MyClass> listGraduateClass(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<MyClass> cls = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_GRADUATE_APPLY_LIST));
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				ELUser eu = new ELUser(rs.getInt(4), rs.getString(5));
				MyClass cl1 = new MyClass();
				cl1.setElClass(cl);
				cl1.setUser(eu);
				cls.add(cl1);
			}
		} catch (Exception e) {
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	public int listGraduateClassSize(int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_GRADUATE_APPLY_LIST_SIZE));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void graduateClassApplay(int userid, int classid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_GRADUATE_UPDATE));
			ps.setInt(1, status);
			ps.setInt(2, classid);
			ps.setInt(3, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void autoSetCourse(int classid, int status, int userid)
			throws ElException {
		// PreparedStatement ps = null;
		// ResultSet rs = null;
		// Connection ct = null;
		try {
			// ct = DBConnection.getConnection();
			// 课程
			List<Course> courses = listClassCourses(classid, status);
			for (int i = 0; i < courses.size(); i++) {
				if (!checkCourseInuser(userid, courses.get(i).getId())) {
					setCourse(courses.get(i).getId(), status, userid);
				}
			}
			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(StudyConstants.STUDY_CLASS_GRADUATE_UPDATE));
			 * ps.setInt(1, status); ps.setInt(2, classid); ps.setInt(3,
			 * userid); ps.executeUpdate();
			 */
		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			// DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private boolean checkCourseInuser(int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 课程
			ps = ct
					.prepareStatement("select * from course_apply ca where userid = ? and courseid = ? ");
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

	private void setCourse(int courseid, int status, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 课程
			ps = ct
					.prepareStatement("insert into course_apply(userid, courseid,applyDate,status,valid) values(?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setInt(4, status);
			ps.setInt(5, 1);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ElClass getResentClass() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElClass cl = new ElClass();
		try {
			ct = DBConnection.getConnection();
			// 课程
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_RESENT));

			rs = ps.executeQuery();
			if (rs.next())
				cl = new ElClass(rs.getInt(1), rs.getString(2));

		} catch (Exception e) {
			logger.error("课程排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cl;
	}

	public List<Department> listDepPassPer(int classid, int pageNow,
			int pageSize) throws ElException {
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
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StudyConstants.STUDY_CLASS_DEP_PASSPER));

			// ps.setInt(1, classid);
			// ps.setInt(2, group1);
			ps.setInt(1, classid);
			ps.setInt(2, group1);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setUserCount(rs.getInt(3));
				dep.setUserCredit(rs.getInt(4));
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

	public void shClass(int classid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_STATUS_SET));
			ps.setInt(1, status);
			ps.setInt(2, classid);
			ps.executeUpdate();
			// 现在改成综合审核，所有还得改变该培训班中所有课程结业考场的状态
			ps.close();
			this.shClassEroom(classid, status);
		} catch (Exception e) {
			logger.error("培训班批准操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更改培训班的状态
	 * 
	 * @param classid
	 * @param status
	 * @param isApplication
	 * @throws ElException
	 */
	public void shClass(int classid, int status, int isApplication)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_STATUS_SET));
			ps.setInt(1, status);
			ps.setInt(2, classid);
			ps.executeUpdate();
			// 现在改成综合审核，所有还得改变该培训班中所有课程结业考场的状态
			ps.close();
			if (isApplication == 1) {
				this.shClassEroom(classid, status);
			}
		} catch (Exception e) {
			logger.error("培训班批准操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新培训班中所有绑定考场的状态
	 * 
	 * @param classid
	 * @param status
	 * @throws ElException
	 */
	public void shClassEroom(int classid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update exam_room set valid=? where bandclassid=?");
			ps.setInt(1, status);
			ps.setInt(2, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新培训班中所有绑定考场的状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void shUvalid(int classid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update elclass set uvalid = ? where id =?");
			ps.setInt(1, status);
			ps.setInt(2, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("培训班批准操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setaStatus(int classid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update elclass set astauts = ? where id =?");
			ps.setInt(1, status);
			ps.setInt(2, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("培训班批准操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void setisNormal(int classid, int isNormal) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update elclass set isNormal = ? where id = ?");
			ps.setInt(1, isNormal);
			ps.setInt(2, classid);
			ps.executeUpdate();
			// 刷新首页培训班模块数据
			((IndexDataUtil) SpringContextUtil.getBean("indexDataUtil"))
					.loadIndexInfo(ElConstants.INDEX_MODEL_ELCLASS);
		} catch (Exception e) {
			logger.error("培训班批准操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	/**
	 * 获取统计培训班列表
	 */
	public List<ElClass> getStatClassesList(int deptid, int typeid,
			String name, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();

			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}

			int clid = 0, crid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLTYPE_LIRID));
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			if (rs.next()) {
				clid = rs.getInt(2);
				crid = rs.getInt(3);
			}
			rs.close();

			// 获取必修，选修数据
			ps = ct
					.prepareStatement("select cc.status,cc.classid,count(courseid),sum(cc.setcredit) from class_course cc group by cc.classid,cc.status");
			rs = ps.executeQuery();
			Map bxMap = new HashMap();
			Map xxMap = new HashMap();
			int status = 0;
			String valueStr = "";

			while (rs.next()) {
				status = rs.getInt(1);
				valueStr = rs.getInt(3) + "/" + rs.getInt(4);
				// value:"+valueStr);
				if (status == 0) {
					bxMap.put(rs.getInt(2), valueStr);

				}
				if (status == 1) {
					xxMap.put(rs.getInt(2), valueStr);
				}
			}
			rs.close();

			// 获取培训班学员人数
			ps = ct
					.prepareStatement("select classid ,count(userid) from study_class group by classid");
			rs = ps.executeQuery();
			Map studentMap = new HashMap();

			while (rs.next()) {
				// student count:"+rs.getInt(2));
				studentMap.put(rs.getInt(1), rs.getInt(2));
			}
			rs.close();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_STAT_CLASSLIST));

			ps.setInt(1, ClassConstants.CLASS_STATUS_HASOPENED);
			ps.setInt(2, ClassConstants.CLASS_STATUS_DELETE_WAIT);
			ps.setString(3, "%" + name + "%");
			ps.setInt(4, dep.getLid());
			ps.setInt(5, dep.getRid());
			ps.setInt(6, clid);
			ps.setInt(7, crid);
			ps.setInt(8, pageNow);
			ps.setInt(9, pageSize);
			rs = ps.executeQuery();
			int classId = 0;
			while (rs.next()) {
				classId = rs.getInt(1);
				ElClass cl = new ElClass(classId, rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cl.setCreater(new ELUser(rs.getInt(8), rs.getString(9)));
				cl.setCreatetime(rs.getDate(10));
				cl.setBxStr((String) bxMap.get(classId));
				cl.setXxStr((String) xxMap.get(classId));
				cl.setStudentCount((Integer) studentMap.get(classId));
				cls.add(cl);
			}
			rs.close();

		} catch (Exception e) {
			logger.error("获取培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 获取统计培训班列表数量
	 */
	public int getStatClassesSize(int deptid, int typeid, String name)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}

			int clid = 0, crid = 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLTYPE_LIRID));
			ps.setInt(1, typeid);
			rs = ps.executeQuery();
			if (rs.next()) {
				clid = rs.getInt(2);
				crid = rs.getInt(3);
			}

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_STAT_CLASSLISTSIZE));
			ps.setInt(1, ClassConstants.CLASS_STATUS_HASOPENED);
			ps.setInt(2, ClassConstants.CLASS_STATUS_DELETE_WAIT);
			ps.setString(3, "%" + name + "%");
			ps.setInt(4, dep.getLid());
			ps.setInt(5, dep.getRid());
			ps.setInt(6, clid);
			ps.setInt(7, crid);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的培训班数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void setClassApplyStatusNo(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ClassConstants.CLASS_APPLY_STATUS_SET_NO));
			ps.setInt(1, classid);
			ps.setInt(2, userid);

			rs = ps.executeQuery();

		} catch (Exception e) {
			logger.error("设置培训班状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void updateCourseRelation(Map map) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(ClassConstants.CLASS_COURSE_CREDIT));
			String sql = "update class_course c set c.suggestcredit = ? , c.setcredit=? , c.getcredit=?,c.starttime=?,c.finishtime=?,orderid=?,firstLearn=? where c.classid=? and c.courseid =?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, (Integer) map.get("suggestcredit"));
			ps.setInt(2, (Integer) map.get("setcredit"));
			ps.setInt(3, (Integer) map.get("getcredit"));
			// ps.setTimestamp(4, Timestamp.valueOf(map.get("startTime_2")+""));
			// SimpleDateFormat df = new
			// SimpleDateFormat("yyyy-MM-dd~HH:mm:ss");
			// java.util.Date time = df.parse(map.get("startTime_2")+"");
			String str = map.get("startTime_2").toString();
			str = str.replace('~', ' ') + ".000000000";
			Timestamp ts = Timestamp.valueOf(str);
			ps.setTimestamp(4, ts);

			// ps.setTimestamp(5, Timestamp.valueOf(map.get("endTime_2")+""));
			str = map.get("endTime_2").toString();
			str = str.replace('~', ' ') + ".000000000";
			ts = Timestamp.valueOf(str);
			ps.setTimestamp(5, ts);

			ps.setInt(6, (Integer) map.get("orderid"));
			ps.setInt(7, (Integer) map.get("firstLearn"));
			ps.setInt(8, (Integer) map.get("elclassId"));
			ps.setInt(9, (Integer) map.get("courseId"));

			rs = ps.executeQuery();

		} catch (Exception e) {
			logger.error("设置培训班课程学分失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	/**
	 * 培训批次培训班列表(不同状态,有权限)
	 */
	public List<ElClass> getBatchClassesList(ElClType tree, int deptid,
			int cltid, String name, int status, int pageNow, int pageSize,
			int batchId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();

			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}

			StringBuffer buffer = new StringBuffer();

			buffer
					.append(
							"select * from (select t.*, rownum rn from (select   cl.id, cl.name, cl.certificatename, cl.cltype, ")
					.append(" cl.optionalcredit, cl.status, clt.name cltname ")
					.append(
							" from elclass cl, elclasstype clt, eluser u, department dep ")
					.append(
							" where cl.cltype = clt.id and cl.creater = u.id and u.depid = dep.id ")
					.append(
							" and cl.status = ? and cl.name like ? and dep.lid >= ? and dep.rid <= ? ")
					.append(
							" and clt.id in (" + ctypeTypeId(tree, cltid)
									+ ") ")
					.append(
							" and cl.id not in (select bc.classid from batch_class bc where bc.batchid =? ) order by cl.createtime desc) t ")
					.append(" where rownum <= ?) where rn >= ? ");

			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, status);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, dep.getLid());
			ps.setInt(4, dep.getRid());
			ps.setInt(5, batchId);
			ps.setInt(6, pageNow);
			ps.setInt(7, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(7)));
				cl.setOptionalcredit(rs.getInt(5));
				cl.setStatus(rs.getInt(6));
				cls.add(cl);
			}

		} catch (Exception e) {
			logger.error("获取培训批次选择培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 培训批次培训班列表大小(不同状态,有权限)
	 */
	public int getBatchClassesSize(ElClType tree, int deptid, int cltid,
			String name, int status, int batchId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select count (*) from elclass cl, elclasstype clt, eluser u, department dep ")
					.append(
							" where cl.cltype = clt.id   and cl.creater = u.id  and u.depid = dep.id  ")
					.append(
							" and cl.status = ? and cl.name like ? and dep.lid >= ? and dep.rid <= ? and clt.id in ( "
									+ ctypeTypeId(tree, cltid) + " )")
					.append(
							" and cl.id not in (select bc.classid from batch_class bc where bc.batchid =? )");

			ps = ct.prepareStatement(buffer.toString());

			ps.setInt(1, status);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, dep.getLid());
			ps.setInt(4, dep.getRid());
			ps.setInt(5, batchId);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取培训批次选择培训班列表数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
			int classid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser, int sub_department,
			Department depTree, Station staTree) throws ElException {
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		List<Station> staList = new ArrayList<Station>();
		List<ELUser> userList = new ArrayList<ELUser>();
		String LidRid = " and ";
		String staLidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			ct = DBConnection.getConnection();
			Department dept = new Department();
			Station sta = new Station();
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
						LidRid = LidRid + "  dp.lid >= " + rs.getInt(1)
								+ " and  dp.rid <= " + rs.getInt(2);
					} else {
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
					}
					x++;
				}
			} else {
				ps = ct.prepareStatement("select * from DEPARTMENT where id=?");
				ps.setInt(1, depTree.getId());
				rs = ps.executeQuery();
				rs.next();
				dept.setId(rs.getInt(1));
				dept.setName(rs.getString(2));
				ElNode node = new ElNode(rs.getInt(4));
				dept.setParent(node);
				dept.setLid(rs.getInt("lid"));
				dept.setRid(rs.getInt("rid"));
				LidRid = LidRid + " and dp.lid>=" + rs.getInt("lid")
						+ " and dp.rid<= " + rs.getInt("rid");
				deptList.add(dept);
			}
			ps = ct.prepareStatement("select * from station where id=?");
			ps.setInt(1, staTree.getId());
			rs = ps.executeQuery();
			rs.next();
			sta.setId(rs.getInt("id"));
			sta.setName(rs.getString("name"));
			sta.setLid(rs.getInt("lid"));
			sta.setRid(rs.getInt("rid"));
			staLidRid = staLidRid + " and sta.lid>=" + rs.getInt("lid")
					+ " and sta.rid<= " + rs.getInt("rid");
			staList.add(sta);
			StringBuffer usersql = new StringBuffer();
			// usersql
			// .append(
			// "select * from(select t.*,rownum rn from ( select eu.id
			// userid,eu.realname username,dp.id deptid,dp.name,eu.username
			// deptname,eu.jingzhong,role.id,role.name
			// rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy'
			// ))-floor(to_char(shengri,'yyyy')),-1) age_ from ")
			// .append(" eluser eu ")
			// .append(
			// " left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role
			// on eu.role=role.id where dp.ID is not null ");
			usersql
					.append(
							"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from  ")
					.append(" eluser eu ")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id inner join station sta on sta.id=eu.staid left join ELROLE role on eu.role=role.id where dp.ID is not null ");
			if (sub_department == 1) {
				if (depTree.getId() == -2) {
					usersql.append(LidRid);
				} else {
					usersql.append(" and dp.lid>=" + dept.getLid()
							+ " and dp.rid<= " + dept.getRid());
					usersql.append(" and sta.lid>=" + sta.getLid()
							+ " and sta.rid<= " + sta.getRid());
				}
			} else {
				if (depTree.getId() == -2) {
					usersql.append(LidRid);
				} else {
					usersql.append(" and dp.id=" + depTree.getId());
				}
			}
			List<Object> params = new ArrayList<Object>();
			if (elUser != null) {
				if (elUser.getSex() != null && !elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (elUser.getRealname() != null
						&& !elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (elUser.getUsername() != null
						&& !elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				if (elUser.getXianzhiwei() != null
						&& !elUser.getXianzhiwei().equals("")) {
					usersql.append(" and sta.name like '%"
							+ elUser.getXianzhiwei() + "%'");
				}
				// if (null != elUser.getJingzhong() &&
				// !elUser.getJingzhong().equals("")&&
				// !elUser.getJingzhong().equals("0")) {
				// usersql.append(" and eu.jingzhong =
				// '"+elUser.getJingzhong().trim()+"'");
				// }
				// if (null != elUser.getDishi() &&
				// !elUser.getDishi().equals("")&&
				// !elUser.getDishi().equals("0")){
				// usersql.append(" and eu.dishi =
				// '"+elUser.getDishi().trim()+"' ");
				// }
				// if (null != elUser.getZhiji() &&
				// !elUser.getZhiji().equals("")&&
				// !elUser.getZhiji().equals("0")){
				// usersql.append(" and eu.zhiji =
				// '"+elUser.getZhiji().trim()+"' ");
				// }
				// if (null != elUser.getZhiwu() &&
				// !elUser.getZhiwu().equals("")&&
				// !elUser.getZhiwu().equals("0")){
				// usersql.append(" and eu.zhiwu =
				// '"+elUser.getZhiwu().trim()+"' ");
				// }
				if (elUser.getShengri() != null
						&& !elUser.getShengri().equals("")) {
					usersql.append(" and eu.shengri >=?");
					params.add(elUser.getShengri());
				}
				if (elUser.getShengri_end() != null
						&& !elUser.getShengri_end().equals("")) {
					usersql.append(" and eu.shengri <=?");
					params.add(elUser.getShengri_end());
				}
				if (elUser.getJingzhong() > 0) {
					usersql.append(" and eu.jingzhong = '"
							+ elUser.getJingzhong() + "'");
				}
				if (elUser.getDishi() > 0) {
					usersql.append("  and eu.dishi = '" + elUser.getDishi()
							+ "' ");
				}
				if (elUser.getZhiji() > 0) {
					usersql.append("  and eu.zhiji = '" + elUser.getZhiji()
							+ "' ");
				}
				if (elUser.getZhiwu() > 0) {
					usersql.append("  and eu.zhiwu = '" + elUser.getZhiwu()
							+ "' ");
				}
				if (null != elUser.getGangwei()
						&& !elUser.getGangwei().equals("")
						&& !elUser.getGangwei().equals("0")) {
					usersql.append("  and eu.gangwei = '"
							+ elUser.getGangwei().trim() + "' ");
				}
				if (elUser.getIsAssign() != null
						&& !elUser.getIsAssign().equals("-1")) {
					if (elUser.getIsAssign().equals("0")) {
						usersql
								.append(" and eu.id in(select ca.userid from study_class ca where ca.classid="
										+ classid + ") ");
					} else if (elUser.getIsAssign().equals("1")) {
						usersql
								.append(" and eu.id not in(select ca.userid from study_class ca where ca.classid="
										+ classid + ") ");
					}
				}
			}
			usersql.append(" )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			ps.setInt(params.size() + 1, pageNow);
			ps.setInt(params.size() + 2, pageSize);
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
				user.setIsAssign("未分配");
				user.setAge(rs.getInt(11));
				userList.add(user);
			}
			ps.close();
			rs.close();
			ps = ct
					.prepareStatement("select ca.userid,ca.joinway from study_class ca where ca.classid=?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
						users.setJoinway(rs.getInt("joinway") == 0 ? "分配"
								: "申请");
					}
				}
			}
			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return returnList;
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	public List<ELUser> listAssignedUserIsAssign(int pageNow, int pageSize,
			int depid, int classid, int state, List<Integer> userid,
			String starttime, String endtime, ELUser elUser) throws ElException {
		List<ELUser> returnList = new ArrayList<ELUser>();
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
							"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from  ")
					.append(" eluser eu ")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where  dp.lid>=? and dp.rid<=? and dp.ID is not null");
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
						// usersql
						// .append(" and eu.id in(select ca.userid from
						// study_class ca where ca.classid="
						// + classid + ") ");
					} else {
						usersql
								.append(" and eu.id not in(select ca.userid from study_class ca where ca.classid="
										+ classid + ") ");
					}
				}
			}
			usersql
					.append(" and eu.id in(select ca.userid from study_class ca where ca.classid="
							+ classid + ") ");
			usersql.append(" )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, dept.getLid());
			ps.setInt(2, dept.getRid());
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
				user.setIsAssign("未分配");
				user.setAge(rs.getInt(11));
				userList.add(user);
			}
			ps = ct
					.prepareStatement("select ca.userid,ca.joinway from study_class ca where ca.classid=?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
						users.setJoinway(rs.getInt("joinway") == 0 ? "分配"
								: "申请");
					}
				}
			}
			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return returnList;
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	public int listAssignedUserSize(int depid, int classid, int state,
			List<Integer> userid, String starttime, String endtime,
			ELUser elUser, int sub_department, Department depTree,
			Station staTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		List<Station> staList = new ArrayList<Station>();
		String LidRid = " and ";
		String staLidRid = " and ";
		String depids = "";
		int Rsize = 0;
		int x = 1;
		try {
			ct = DBConnection.getConnection();
			Department dept = new Department();
			Station sta = new Station();
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
						LidRid = LidRid + "  dp.lid >= " + rs.getInt(1)
								+ " and  dp.rid <= " + rs.getInt(2);
					} else {
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
					}
					x++;
				}
			} else {
				ps = ct.prepareStatement("select * from DEPARTMENT where id=?");
				ps.setInt(1, depTree.getId());
				rs = ps.executeQuery();
				rs.next();
				dept.setId(rs.getInt(1));
				dept.setName(rs.getString(2));
				ElNode node = new ElNode(rs.getInt(4));
				dept.setParent(node);
				dept.setLid(rs.getInt("lid"));
				dept.setRid(rs.getInt("rid"));
				LidRid = LidRid + " and dp.lid>=" + rs.getInt("lid")
						+ " and dp.rid<= " + rs.getInt("rid");
				deptList.add(dept);
			}
			ps = ct.prepareStatement("select * from station where id=?");
			ps.setInt(1, staTree.getId());
			rs = ps.executeQuery();
			rs.next();
			sta.setId(rs.getInt("id"));
			sta.setName(rs.getString("name"));
			sta.setLid(rs.getInt("lid"));
			sta.setRid(rs.getInt("rid"));
			staLidRid = staLidRid + " and sta.lid>=" + rs.getInt("lid")
					+ " and sta.rid<= " + rs.getInt("rid");
			staList.add(sta);

			StringBuffer usersql = new StringBuffer();
			usersql
					.append("select count(eu.id)   from  ")
					.append(" eluser eu ")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id inner join station sta on sta.id=eu.staid left join ELROLE role on eu.role=role.id where dp.ID is not null ");
			if (sub_department == 1) {
				if (depTree.getId() == -2) {
					usersql.append(LidRid);
				} else {
					usersql.append(" and dp.lid>=" + dept.getLid()
							+ " and dp.rid<= " + dept.getRid());
					usersql.append(" and sta.lid>=" + sta.getLid()
							+ " and sta.rid<= " + sta.getRid());
				}
			} else {
				if (depTree.getId() == -2) {
					usersql.append(LidRid);
				} else {
					usersql.append(" and dp.id=" + depTree.getId());
				}
			}
			List<Object> params = new ArrayList<Object>();
			if (elUser != null) {
				if (elUser.getSex() != null && !elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (elUser.getRealname() != null
						&& !elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (elUser.getUsername() != null
						&& !elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				if (elUser.getXianzhiwei() != null
						&& !elUser.getXianzhiwei().equals("")) {
					usersql.append(" and sta.name like '%"
							+ elUser.getXianzhiwei() + "%'");
				}
				// if (null != elUser.getJingzhong() &&
				// !elUser.getJingzhong().equals("")&&
				// !elUser.getJingzhong().equals("0")) {
				// usersql.append(" and eu.jingzhong =
				// '"+elUser.getJingzhong().trim()+"'");
				// }
				// if (null != elUser.getDishi() &&
				// !elUser.getDishi().equals("")&&
				// !elUser.getDishi().equals("0")){
				// usersql.append(" and eu.dishi =
				// '"+elUser.getDishi().trim()+"' ");
				// }
				// if (null != elUser.getZhiji() &&
				// !elUser.getZhiji().equals("")&&
				// !elUser.getZhiji().equals("0")){
				// usersql.append(" and eu.zhiji =
				// '"+elUser.getZhiji().trim()+"' ");
				// }
				// if (null != elUser.getZhiwu() &&
				// !elUser.getZhiwu().equals("")&&
				// !elUser.getZhiwu().equals("0")){
				// usersql.append(" and eu.zhiwu =
				// '"+elUser.getZhiwu().trim()+"' ");
				// }
				if (elUser.getShengri() != null) {
					usersql.append(" and eu.shengri >=?");
					params.add(elUser.getShengri());
				}
				if (elUser.getShengri_end() != null) {
					usersql.append(" and eu.shengri <=?");
					params.add(elUser.getShengri_end());
				}
				if (elUser.getJingzhong() > 0) {
					usersql.append(" and eu.jingzhong = '"
							+ elUser.getJingzhong() + "'");
				}
				if (elUser.getDishi() > 0) {
					usersql.append("  and eu.dishi = '" + elUser.getDishi()
							+ "' ");
				}
				if (elUser.getZhiji() > 0) {
					usersql.append("  and eu.zhiji = '" + elUser.getZhiji()
							+ "' ");
				}
				if (elUser.getZhiwu() > 0) {
					usersql.append("  and eu.zhiwu = '" + elUser.getZhiwu()
							+ "' ");
				}
				if (null != elUser.getGangwei()
						&& !elUser.getGangwei().equals("")
						&& !elUser.getGangwei().equals("0")) {
					usersql.append("  and eu.gangwei = '"
							+ elUser.getGangwei().trim() + "' ");
				}
				if (elUser.getIsAssign() != null
						&& !elUser.getIsAssign().equals("-1")) {
					if (elUser.getIsAssign().equals("0")) {
						usersql
								.append(" and eu.id in(select ca.userid from study_class ca where ca.classid="
										+ classid + ") ");
					} else if (elUser.getIsAssign().equals("1")) {
						usersql
								.append(" and eu.id not in(select ca.userid from study_class ca where ca.classid="
										+ classid + ") ");
					}
				}
			}
			ps = ct.prepareStatement(usersql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				Rsize = rs.getInt(1);
			}

			return Rsize;
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int listAssignedUserIsAssignSize(int depid, int classid, int state,
			List<Integer> userid, String starttime, String endtime,
			ELUser elUser) throws ElException {
		List<ELUser> userList = new ArrayList<ELUser>();
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
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
							"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng from  ")
					.append(" eluser eu ")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where  dp.lid>=? and dp.rid<=? and dp.ID is not null");
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
						// usersql
						// .append(" and eu.id in(select ca.userid from
						// study_class ca where ca.classid="
						// + classid + ") ");
					} else {
						usersql
								.append(" and eu.id not in(select ca.userid from study_class ca where ca.classid="
										+ classid + ") ");
					}
				}
			}
			usersql
					.append(" and eu.id in(select ca.userid from study_class ca where ca.classid="
							+ classid + ") ");
			usersql.append(" )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, dept.getLid());
			ps.setInt(2, dept.getRid());
			ps.setInt(3, 999999);
			ps.setInt(4, 1);
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
				user.setShenfenzheng(rs.getString(10));
				user.setAge(getAge(rs.getString(10)));
				user.setIsAssign("未分配");
				userList.add(user);
			}

			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return returnList.size();
			}
			return userList.size();
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
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

	private boolean compareAge(String IDCardNum, Calendar calendar,
			String compare) {
		boolean bo = false;
		if (IDCardNum == null) {
			return bo;
		}
		int year, month, day, idLength = IDCardNum.length();
		Calendar cal = Calendar.getInstance();
		if (idLength == 18) {
			year = Integer.parseInt(IDCardNum.substring(6, 10));
			month = Integer.parseInt(IDCardNum.substring(10, 12));
			day = Integer.parseInt(IDCardNum.substring(12, 14));
		} else if (idLength == 15) {
			year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
			month = Integer.parseInt(IDCardNum.substring(8, 10));
			day = Integer.parseInt(IDCardNum.substring(10, 12));
		} else {
			return bo;
		}
		cal.set(year, month, day);
		if (compare.equals(">")) {
			if (calendar.getTimeInMillis() > cal.getTimeInMillis()) {
				bo = true;
			}
		} else if (compare.equals("<")) {
			if (calendar.getTimeInMillis() < cal.getTimeInMillis()) {
				bo = true;
			}
		}
		return bo;
	}

	private static int getYearDiff(Calendar cal, Calendar cal1) {
		int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));
		int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));
		return (y * 12 + m) / 12;
	}

	// public List<ELUser> classStudent(int pageNow, int pageSize, int depid,
	// int classid, int state, List<Integer> userid, String starttime,
	// String endtime, ELUser elUser) throws ElException {
	// List<ELUser> returnList = new ArrayList<ELUser>();
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
	// deptList.add(dept);
	//
	// StringBuffer usersql = new StringBuffer();
	// // usersql.append("select * from(select t.*,rownum rn from ( select eu.id
	// userid,eu.realname username,dp.id deptid,dp.name,eu.username
	// deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng
	// from ")
	// // .append(" eluser eu ")
	// // .append(" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE
	// role on eu.role=role.id where dp.lid>=? and dp.rid<=? and dp.ID is not
	// null ");
	// //
	// usersql.append("select * from (select t.*, rownum rn from ( select
	// eu.id," +
	// " eu.realname,eu.username, dep.id depid, dep.name,eu.jingzhong," +
	// " cla.classid, sum(cc.credit) as credit ,cla.applyDate from eluser eu
	// left join department dep on dep.id = eu.depid left join (select * from
	// study_class where classid = ? and status=2 ) cla on cla.userid = eu.id
	// left join (select * from study_quizinfo sqi where sqi.ispassed = 1 ) sqi
	// on sqi.userid = eu.id left join exam_room er on sqi.roomid = er.id left
	// join class_course cc on er.courseid = cc.courseid where dep.lid>=? and
	// dep.rid<=? ");
	// if(elUser!=null){
	// if(!elUser.getSex().equals("")){
	// usersql.append(" and eu.sex ='"+elUser.getSex()+"'");
	// }
	// if(!elUser.getRealname().equals("")){
	// usersql.append(" and eu.realname like '%"+elUser.getRealname()+"%'");
	// }
	// if(!elUser.getUsername().equals("")){
	// usersql.append(" and eu.username like '%"+elUser.getUsername()+"%'");
	// }
	// if(!elUser.getJingzhong().equals("")){
	// usersql.append(" and eu.jingzhong like '%"+elUser.getJingzhong()+"%'");
	// }
	// }
	// usersql.append(" group by eu.id, eu.realname, eu.username, dep.id,
	// dep.name, sqi.ispassed, cla.classid,cla.applyDate order by sum(cc.credit)
	// desc )t where rownum<=? ) where rn>=?");
	// ps = ct.prepareStatement(usersql.toString());
	// ps.setInt(1, dept.getLid());
	// ps.setInt(2, dept.getRid());
	// ps.setInt(3, classid);
	// ps.setInt(4, pageNow);
	// ps.setInt(5, pageSize);
	// // ps.setInt(2, state);
	// rs = ps.executeQuery();
	//			
	//			
	// while (rs.next()) {
	// ELUser user = new ELUser();
	// user.setId(rs.getInt(1));
	// user.setRealname(rs.getString(2));
	// user.setUsername(rs.getString(3));
	// user
	// .setDepartment(new Department(rs.getInt(4), rs
	// .getString(5)));
	// user.setJingzhong(rs.getString(6));
	// user.setRole(new ElRole(rs.getInt(7),rs.getString(8)));
	// user.setSex(rs.getString(9));
	// user.setShenfenzheng(String.valueOf(getAge(rs.getString(10))));
	// user.setIsAssign("未分配");
	// userList.add(user);
	// }
	// ps = ct.prepareStatement("select ca.userid from study_class ca where
	// ca.classid=?");
	// ps.setInt(1, classid);
	// rs = ps.executeQuery();
	// while(rs.next()){
	// for(ELUser users:userList){
	// if(users.getId()==rs.getInt(1)){
	// users.setIsAssign("已分配");
	// }
	// }
	// }
	// Calendar calendar = Calendar.getInstance();
	// if (starttime != null && !"".equals(starttime)) {
	// calendar.setTime(DateUtils.parseDate(starttime, new
	// String[]{"yyyy-mm-dd"}));
	// for(ELUser user:userList){
	// if(compareAge(user.getShenfenzheng(),calendar,">")){
	// returnList.add(user);
	// }
	// }
	// }
	// if (endtime != null && !"".equals(endtime)) {
	// calendar.setTime(DateUtils.parseDate(endtime, new
	// String[]{"yyyy-mm-dd"}));
	// for(ELUser user:userList){
	// if(compareAge(user.getShenfenzheng(),calendar,"<")){
	// returnList.add(user);
	// }
	// }
	// }
	// if((starttime != null && !"".equals(starttime))||(endtime != null &&
	// !"".equals(endtime))){
	// return returnList;
	// }
	// } catch (Exception e) {
	// logger.error("分配学员失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return userList;
	// }
	//	
	// public List<ELUser> classStudent(int pageNow, int pageSize, int depid,
	// int classid, int state, List<Integer> userid, String starttime,
	// String endtime, ELUser elUser) throws ElException {
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
	// Department dept = new Department();
	// if (rs.next()) {
	// dept.setId(rs.getInt(1));
	// dept.setName(rs.getString(2));
	// ElNode node = new ElNode(rs.getInt(4));
	// dept.setParent(node);
	// dept.setLid(rs.getInt("lid"));
	// dept.setRid(rs.getInt("rid"));
	// deptList.add(dept);
	//
	// }
	// StringBuffer usersql = new StringBuffer();
	// usersql
	// .append("select * from (select t.*, rownum rn from (select * from (
	// select eu.id euid,eu.username, eu.realname, dep.id depid,dep.name
	// depname,eu.valid ,eu.sex,eu.jingzhong,eu.shengri,nvl(
	// floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
	// + " ,scl.status,scl.applydate,scl.certificateno from study_class scl left
	// join eluser eu on scl.userid =eu.id "
	// + "left join department dep on dep.id = eu.depid where dep.lid>=? and
	// dep.rid<=? and scl.classid = ?");
	// if (elUser != null) {
	// if (elUser.getSex() != null && !"".equals(elUser.getSex())) {
	// usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
	// }
	// if (elUser.getUsername() != null
	// && !"".equals(elUser.getRealname())) {
	// usersql.append(" and eu.realname like '%"
	// + elUser.getRealname() + "%'");
	// }
	// if (elUser.getUsername() != null
	// && !"".equals(elUser.getUsername())) {
	// usersql.append(" and eu.username like '%"
	// + elUser.getUsername() + "%'");
	// }
	// if (elUser.getJingzhong() != null
	// && !"".equals(elUser.getJingzhong())) {
	// usersql.append(" and eu.jingzhong like '%"
	// + elUser.getJingzhong() + "%'");
	// }
	// }
	// if (elUser.getIsAssign() != null
	// && !"".equals(elUser.getIsAssign())) {
	// //结业标准改了
	// // if ("0".equals(elUser.getIsAssign())) {
	// // usersql.append(" and scl.status != 2");
	// // } else {
	// // usersql.append(" and scl.status = 2 ");
	// // }
	// if ("0".equals(elUser.getIsAssign())) {
	// usersql.append(" and scl.certificateno is null");
	// } else {
	// usersql.append(" and scl.certificateno is not null ");
	// }
	// }
	// // usersql
	// // .append(" group by eu.id, eu.realname, eu.username, dep.id,
	// // dep.name, sqi.ispassed, cla.classid,cla.applyDate order by
	// // sum(cc.credit) desc )t where rownum<=? ) where rn>=?");
	// usersql.append(" order by scl.applydate desc )c )t where rownum<=? )
	// where rn>=?");
	// ps = ct.prepareStatement(usersql.toString());
	// ps.setInt(1, dept.getLid());
	// ps.setInt(2, dept.getRid());
	// ps.setInt(3, classid);
	// ps.setInt(4, pageNow);
	// ps.setInt(5, pageSize);
	// // ps.setInt(2, state);
	// rs = ps.executeQuery();
	//
	// while (rs.next()) {
	// ELUser elUser2 = new ELUser();
	// elUser2.setId(rs.getInt(1));
	// elUser2.setUsername(rs.getString(2));
	// elUser2.setRealname(rs.getString(3));
	// elUser2.setDepartment(new Department(rs.getInt(4), rs
	// .getString(5)));
	// elUser2.setValid(rs.getBoolean(6));
	// elUser2.setSex(rs.getString(7));
	// elUser2.setJingzhong(rs.getString(8));
	// elUser2.setShengri(rs.getDate(9));
	// elUser2.setAge(rs.getInt(10));
	// //if(rs.getInt(11)== 2) elUser2.setGraddate(rs.getDate(12));
	// if(rs.getInt("certificateno")>0){
	// elUser2.setGraddate(rs.getDate(12));
	// }
	// //总分
	// elUser2.setXx_time(classStudentScore2(classid,elUser2.getId(), 0) +
	// classStudentScore2(classid,elUser2.getId(), 1));
	// // elUser2.setXx_time(classStudentScore(classid, elUser2.getId(),
	// // -1));
	// //必修分
	// elUser2.setCt_credit(classStudentScore2(classid,
	// elUser2.getId(), 0));
	// //选修分
	// elUser2.setXx_credit(classStudentScore2(classid,
	// elUser2.getId(), 1));
	// elUser2.setIsAssign("未分配");
	// userList.add(elUser2);
	// }
	// } catch (Exception e) {
	// logger.error("分配学员失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// //排序（按学分）
	// userList=this.sortUserByScore(userList);
	// return userList;
	// }
	/**
	 * 检测用户查询的参数
	 */
	public void checkUserParam(StringBuffer sql, ELUser elUser,
			List<Object> params) {
		if (elUser != null) {
			if (elUser.getSex() != null && !elUser.getSex().equals("")) {
				sql.append(" and eu.sex =?");
				params.add(elUser.getSex());
			}
			if (elUser.getRealname() != null
					&& !elUser.getRealname().equals("")) {
				sql.append(" and eu.realname like ?");
				params.add("%" + StringUtil.toLikeStr(elUser.getRealname())
						+ "%");
			}
			if (elUser.getUsername() != null
					&& !elUser.getUsername().equals("")) {
				sql.append(" and eu.username like ?");
				params.add("%" + StringUtil.toLikeStr(elUser.getUsername())
						+ "%");
			}
			if (elUser.getJingzhong() > 0) {
				sql.append(" and eu.jingzhong = ?");
				params.add(elUser.getJingzhong());
			}
			// if (null != elUser.getDishi() && !elUser.getDishi().equals("")&&
			// !elUser.getDishi().equals("0")){
			// sql.append(" and eu.dishi = '"+elUser.getDishi().trim()+"' ");
			// }
			// if (null != elUser.getZhiji() && !elUser.getZhiji().equals("")&&
			// !elUser.getZhiji().equals("0")){
			// sql.append(" and eu.zhiji = '"+elUser.getZhiji().trim()+"' ");
			// }
			// if (null != elUser.getZhiwu() && !elUser.getZhiwu().equals("")&&
			// !elUser.getZhiwu().equals("0")){
			// sql.append(" and eu.zhiwu = '"+elUser.getZhiwu().trim()+"' ");
			// }
			// if (null != elUser.getGangwei() &&
			// !elUser.getGangwei().equals("")&&
			// !elUser.getGangwei().equals("0")){
			// sql.append(" and eu.gangwei = '"+elUser.getGangwei().trim()+"'
			// ");
			// }
			if (elUser.getShengri() != null) {
				sql.append(" and eu.shengri >= ?");
				params.add(elUser.getShengri());
			}
			if (elUser.getShengri_end() != null) {
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
	 * 培训班统计查询学员（学分排序）（不分页、用于导出）
	 * 
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent(int classid, ElNode tree, ELUser elUser)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		List<Object> params = new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql
					.append("select eu.id euid,eu.username, eu.realname, dep.id depid,dep.name depname,eu.valid ,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
							+ " ,scl.status,scl.applydate,scl.certificateno from study_class scl left join eluser eu on scl.userid =eu.id "
							+ "inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("department", tree, true)
							+ ") dep on dep.id = eu.depid where scl.classid = ? ");
			this.checkUserParam(usersql, elUser, params);
			usersql.append(" order by scl.applydate desc ");
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, classid);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 2, params.get(i));
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
				if (rs.getInt("certificateno") > 0) {
					elUser2.setGraddate(rs.getDate(12));
				}
				// 总分
				elUser2.setXx_time(classStudentScore2(classid, elUser2.getId(),
						0)
						+ classStudentScore2(classid, elUser2.getId(), 1));
				// 必修分
				elUser2.setCt_credit(classStudentScore2(classid, elUser2
						.getId(), 0));
				// 选修分
				elUser2.setXx_credit(classStudentScore2(classid, elUser2
						.getId(), 1));
				// elUser2.setIsAssign("未分配");??
				userList.add(elUser2);
			}
		} catch (Exception e) {
			logger.error("培训班统计查询学员（学分排序）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// 排序（按学分）
		userList = this.sortUserByScore(userList);
		return userList;
	}

	/**
	 * 培训班统计查询学员（学分排序）
	 * 
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent(int classid, ElNode tree, ELUser elUser,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		List<Object> params = new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql
					.append("select * from (select t.*, rownum rn from ( select  eu.id euid,eu.username, eu.realname, dep.id depid,dep.name depname,eu.valid ,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
							+ " ,scl.status,scl.applydate,scl.certificateno from study_class scl left join eluser eu on scl.userid =eu.id "
							+ "inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("department", tree, true)
							+ ") dep on dep.id = eu.depid where scl.classid = ? ");
			this.checkUserParam(usersql, elUser, params);
			usersql
					.append(" order by scl.applydate desc )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			logger.info(usersql.toString());
			ps.setInt(1, classid);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 2, params.get(i));
			}
			ps.setInt(params.size() + 2, pageNow);
			ps.setInt(params.size() + 3, pageSize);
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
				// 先查看学员培训班是否通过再查证书编号
				StudyClassDao scd = ((StudyClassDao) SpringContextUtil
						.getBean("studyClassDao"));
				scd.setMyPassclass(elUser2.getId(), classid);
				// cl1.setCertificateno(rs.getInt(7));
				// if(rs.getInt("certificateno")>0){
				// elUser2.setGraddate(rs.getDate(12));
				// }
				if (scd.getStudyClassCertificateno(classid, elUser2.getId()) > 0) {
					elUser2.setGraddate(rs.getDate(12));
				}
				// 必修分
				elUser2.setCt_credit(classStudentScore2(classid, elUser2
						.getId(), 0));
				// 选修分
				elUser2.setXx_credit(classStudentScore2(classid, elUser2
						.getId(), 1));
				// 总分
				elUser2.setXx_time(elUser2.getCt_credit()
						+ elUser2.getXx_credit());
				// elUser2.setIsAssign("未分配");??
				userList.add(elUser2);
			}
		} catch (Exception e) {
			logger.error("培训班统计查询学员（学分排序）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// 排序（按学分）
		userList = this.sortUserByScore(userList);
		return userList;
	}

	/**
	 * 培训班统计查询学员数量
	 * 
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int classStudentSize(int classid, ElNode tree, ELUser elUser)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Object> params = new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql
					.append("select count(scl.userid) from study_class scl left join eluser eu on scl.userid =eu.id "
							+ "inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("department", tree, true)
							+ ") dep on dep.id = eu.depid where scl.classid = ? ");
			this.checkUserParam(usersql, elUser, params);
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, classid);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 2, params.get(i));
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

	/**
	 * 根据用户的学分排序
	 * 
	 * @param deps
	 * @return
	 */
	public List<ELUser> sortUserByScore(List<ELUser> users) {
		ELUser[] objArray = new ELUser[users.size()];
		// 先赋值
		for (int i = 0; i < objArray.length; i++) {
			objArray[i] = users.get(i);
		}

		for (int i = 0; i < objArray.length - 1; i++) {
			// 去掉获取证书的
			if (objArray[i].getGraddate() != null) {
				// 插入到最前面
				ELUser temp = objArray[i];
				for (int j = i; j > 0; j--) {
					objArray[j] = objArray[j - 1];
				}
				objArray[0] = temp;
				continue;
			}
			for (int j = i; j < objArray.length - 1 - i; j++) {
				if (objArray[j].getXx_time() < objArray[j + 1].getXx_time()) {
					ELUser temp = objArray[j];
					objArray[j] = objArray[j + 1];
					objArray[j + 1] = temp;
				}
			}
		}

		// 再回值
		for (int i = 0; i < users.size(); i++) {
			users.set(i, objArray[i]);
		}
		return users;
	}

	// public List<ELUser> classStudent(int depid, int classid, int state,
	// List<Integer> userid, String starttime,
	// String endtime, ELUser elUser) throws ElException {
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
	// Department dept = new Department();
	// if (rs.next()) {
	// dept.setId(rs.getInt(1));
	// dept.setName(rs.getString(2));
	// ElNode node = new ElNode(rs.getInt(4));
	// dept.setParent(node);
	// dept.setLid(rs.getInt("lid"));
	// dept.setRid(rs.getInt("rid"));
	// deptList.add(dept);
	//
	// }
	// StringBuffer usersql = new StringBuffer();
	// usersql
	// .append("select * from (select eu.id euid,eu.username, eu.realname,
	// dep.id depid,dep.name depname,eu.valid
	// ,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy'
	// ))-floor(to_char(shengri,'yyyy')),-1) age_ "
	// + " ,scl.status,scl.applydate from study_class scl left join eluser eu on
	// scl.userid =eu.id "
	// + "left join department dep on dep.id = eu.depid where dep.lid>=? and
	// dep.rid<=? and scl.classid = ? ");
	// if (elUser != null) {
	// if (elUser.getSex() != null && !"".equals(elUser.getSex())) {
	// usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
	// }
	// if (elUser.getUsername() != null
	// && !"".equals(elUser.getRealname())) {
	// usersql.append(" and eu.realname like '%"
	// + elUser.getRealname() + "%'");
	// }
	// if (elUser.getUsername() != null
	// && !"".equals(elUser.getUsername())) {
	// usersql.append(" and eu.username like '%"
	// + elUser.getUsername() + "%'");
	// }
	// if (elUser.getJingzhong()>0) {
	// usersql.append(" and eu.jingzhong ="
	// + elUser.getJingzhong());
	// }
	// }
	// if (elUser.getIsAssign() != null
	// && !"".equals(elUser.getIsAssign())) {
	// if ("0".equals(elUser.getIsAssign())) {
	// usersql.append(" and scl.status != 2");
	// } else {
	// usersql.append(" and scl.status = 2 ");
	// }
	// }
	// // usersql
	// // .append(" group by eu.id, eu.realname, eu.username, dep.id,
	// // dep.name, sqi.ispassed, cla.classid,cla.applyDate order by
	// // sum(cc.credit) desc )t where rownum<=? ) where rn>=?");
	// usersql.append("order by scl.applydate desc )c order by c.status desc ");
	// ps = ct.prepareStatement(usersql.toString());
	// ps.setInt(1, dept.getLid());
	// ps.setInt(2, dept.getRid());
	// ps.setInt(3, classid);
	// // ps.setInt(2, state);
	// rs = ps.executeQuery();
	//
	// while (rs.next()) {
	// ELUser elUser2 = new ELUser();
	// elUser2.setId(rs.getInt(1));
	// elUser2.setUsername(rs.getString(2));
	// elUser2.setRealname(rs.getString(3));
	// elUser2.setDepartment(new Department(rs.getInt(4), rs
	// .getString(5)));
	// elUser2.setValid(rs.getBoolean(6));
	// elUser2.setSex(rs.getString(7));
	// elUser2.setJingzhong(rs.getInt(8));
	// elUser2.setShengri(rs.getDate(9));
	// elUser2.setAge(rs.getInt(10));
	// if(rs.getInt(11)== 2) elUser2.setGraddate(rs.getDate(12));
	// elUser2.setXx_time(classStudentScore(classid,elUser2.getId(), 0) +
	// classStudentScore(classid,elUser2.getId(), 1));
	// // elUser2.setXx_time(classStudentScore(classid, elUser2.getId(),
	// // -1));
	// elUser2.setCt_credit(classStudentScore(classid,
	// elUser2.getId(), 0));
	// elUser2.setXx_credit(classStudentScore(classid,
	// elUser2.getId(), 1));
	// elUser2.setIsAssign("未分配");
	// userList.add(elUser2);
	// }
	// } catch (Exception e) {
	// ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
	// ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET,
	// "分配学员失败!失败方法： 失败原因："+new ElException(e));
	// logger.error("分配学员失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return userList;
	// }

	public int classStudentScore(int classid, int userid, int t)
			throws ElException {
		int sc = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (t == -1) {
				ps = ct
						.prepareStatement("select sum(cc.setcredit) from (select * from class_course where classid =? ) cc left join study_course sc on cc.courseid = sc.courseid left join study_quizinfo sqi on sqi.id= sc.sqiid left join course c on c.id= sc.courseid where sqi.ispassed =1 and sc.userid = ? ");
				// .prepareStatement("select sum(c.credit) from study_course sc
				// left join study_quizinfo sqi on sqi.id= sc.sqiid left join
				// course c on c.id= sc.courseid where sqi.ispassed =1 and
				// sc.classid = ? and sc.userid = ? ");
				ps.setInt(1, classid);
				ps.setInt(2, userid);
				rs = ps.executeQuery();
				if (rs.next()) {
					sc = rs.getInt(1);
				}

			} else {// 在主修课程和必修课程 有多们课程时， 学分要取多门课程的总值
				ps = ct
						.prepareStatement("select sum(setcredit) "
								+ "from (select * from class_course where classid =? and status = ?) cc left join study_course sc  on cc.courseid = sc.courseid left join course c on sc.courseid = c.id left join eluser eu on c.creater = eu.id  "
								+ "where sc.userid = ? and sc.passed in (1,2,3) ");
				// .prepareStatement("select sum(cc.setcredit) from (select *
				// from class_course where classid =? and status = ?) cc left
				// join study_course sc on cc.courseid = sc.courseid left join
				// study_quizinfo sqi on sqi.id= sc.sqiid left join course c on
				// c.id= sc.courseid where sqi.ispassed =1 and sc.userid = ?");
				// .prepareStatement("select sum(c.credit) from study_course sc
				// left join study_quizinfo sqi on sqi.id= sc.sqiid left join
				// course c on c.id= sc.courseid where sqi.ispassed =1 and
				// sc.classid = ? and sc.userid = ? and sc.status =? ");
				ps.setInt(1, classid);
				ps.setInt(2, t);
				ps.setInt(3, userid);
				rs = ps.executeQuery();
				if (rs.next()) {
					sc = rs.getInt(1);
				}
			}

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"我的课程列表出错!失败方法：classStudentScore(int classid, int userid, int t) 失败原因："
							+ new ElException(e));
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sc;
	}

	/**
	 * 获取学员学分（0 必修 1选修 2 必修+选修）
	 */
	public int classStudentScore2(int classid, int userid, int t)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int creditSum = 0;
		try {
			ct = DBConnection.getConnection();
			String ts = "";
			if (t != 2) {// 2 = 必修+选修
				ts = " and status = " + t;
			}
			// 1.先得到该培训班的所有必修（选修）课程，得到其结业方式
			ps = ct
					.prepareStatement("select courseid,getcredit,setcredit from class_course where classid =? "
							+ ts);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			int courseid = 0;
			int getcredit = 0;// 结业方式
			int setcredit = 0;// 学分
			while (rs.next()) {
				courseid = rs.getInt("courseid");
				getcredit = rs.getInt("getcredit");
				setcredit = rs.getInt("setcredit");
				// 判断是否通过，如果通过获取getcredit学分
				if (this.classStudentIsPass(classid, userid, courseid,
						getcredit)) {
					creditSum += setcredit;
				}
			}

		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET, "获取学员学分出错!失败方法： 失败原因："
							+ new ElException(e));
			logger.error("获取学员学分出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return creditSum;
	}

	public boolean classStudentIsPass(int classid, int userid, int courseid,
			int getcredit) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sql = "";
			Vector<Object> params = new Vector<Object>();
			if (getcredit == 1) {
				// 学完
				sql = "select sc.passed from  study_course sc where sc.userid =? and sc.classid=? and sc.passed=1 and courseid=? ";
				params.add(userid);
				params.add(classid);
				params.add(courseid);
			} else {
				// 查出培训班课程绑定的考场
				int roomid = getBandEroomId(classid, courseid);
				if (getcredit == 2) {
					// 考过
					// sql="select sqi.ispassed from study_course sc left join
					// study_quizinfo sqi on sc.sqiid=sqi.id where sc.userid =?
					// and sqi.classid=? and sc.courseid=? and sqi.ispassed=1";
					sql = "select roomid from study_room where userid=? and roomid=? and ispassed=1";
					params.add(userid);
					params.add(roomid);
				} else {
					// 学完且考过
					// sql="select sc.passed from study_course sc left join
					// study_quizinfo sqi on sc.sqiid=sqi.id where sc.userid =?
					// and sc.classid=? and sc.courseid=? and sqi.ispassed=1 and
					// sc.passed=1";
					sql = "select ispassed from (select * from study_quizinfo where userid=? and roomid=?) t1,"
							+ "(select passed from study_course sc where userid=? and courseid=? and classid=?)t2 where t1.ispassed=1 and t2.passed=1";
					params.add(userid);
					params.add(roomid);
					params.add(userid);
					params.add(courseid);
					params.add(classid);
				}
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"我的课程列表出错!失败方法：classStudentIsPass(int classid, int userid,int courseid,int getcredit) 失败原因："
									+ new ElException(e));
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 获取绑定的考场
	 * 
	 * @param classid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public int getBandEroomId(int classid, int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int eroomid = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from exam_room where bandclassid=? and courseid=?");
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				eroomid = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取绑定的考场失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eroomid;
	}

	public int classStudentSize(int depid, int classid, int state,
			List<Integer> userid, String starttime, String endtime,
			ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		List<Department> deptList = new ArrayList<Department>();
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
					.append("  select count(eu.id) from  ")
					.append(
							" study_class scl left join eluser eu on scl.userid =eu.id ")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id where  dp.lid>=? and dp.rid<=? and scl.classid = ? ");
			if (elUser != null) {
				if (elUser.getSex() != null && !"".equals(elUser.getSex())) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (elUser.getRealname() != null
						&& !"".equals(elUser.getRealname())) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (elUser.getUsername() != null
						&& !"".equals(elUser.getUsername())) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				if (elUser.getJingzhong() > 0) {
					usersql.append(" and eu.jingzhong ="
							+ elUser.getJingzhong());
				}
				if (elUser.getIsAssign() != null
						&& !"".equals(elUser.getIsAssign())) {
					if ("0".equals(elUser.getIsAssign())) {
						usersql.append(" and  scl.status != 2");
					} else {
						usersql.append(" and  scl.status = 2 ");
					}
				}
			}
			usersql.append("  ");
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, dept.getLid());
			ps.setInt(2, dept.getRid());
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取数量失败!失败方法：classStudentSize(int depid, int classid, int state,List<Integer> userid, String starttime, String endtime,	ELUser elUser) 失败原因："
									+ new ElException(e));
			logger.error("获取数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	/**
	 * 获取培训班人员ID
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getelClassUser(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> user = new ArrayList();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select userid from study_class where classid = ?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser u = new ELUser();
				u.setId(rs.getInt(1));
				user.add(u);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班人员ID出错!失败方法：getelClassUser(int classid) 失败原因："
							+ new ElException(e));
			logger.error("获取培训班人员ID出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return user;
	}

	//
	// public int classStudentSize(int depid, int classid, int state,
	// List<Integer> userid, String starttime, String endtime,
	// ELUser elUser) throws ElException {
	// List<ELUser> userList = new ArrayList<ELUser>();
	// List<ELUser> returnList = new ArrayList<ELUser>();
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<Department> deptList = new ArrayList<Department>();
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
	// deptList.add(dept);
	//
	// StringBuffer usersql = new StringBuffer();
	// usersql
	// .append(
	// "select * from(select t.*,rownum rn from ( select eu.id
	// userid,eu.realname username,dp.id deptid,dp.name,eu.username
	// deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng
	// from ")
	// .append(" eluser eu ")
	// .append(
	// " left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on
	// eu.role=role.id where dp.lid>=? and dp.rid<=? and dp.ID is not null");
	// if (elUser != null) {
	// if (!"".equals(elUser.getSex())) {
	// usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
	// }
	// if (!"".equals(elUser.getRealname())) {
	// usersql.append(" and eu.realname like '%"
	// + elUser.getRealname() + "%'");
	// }
	// if (!"".equals(elUser.getUsername())) {
	// usersql.append(" and eu.username like '%"
	// + elUser.getUsername() + "%'");
	// }
	// if (!"".equals(elUser.getJingzhong())) {
	// usersql.append(" and eu.jingzhong like '%"
	// + elUser.getJingzhong() + "%'");
	// }
	// if (!"".equals(elUser.getIsAssign())) {
	// if ("0".equals(elUser.getIsAssign())) {
	// usersql
	// .append(" and eu.id in(select ca.userid from study_class ca where
	// ca.classid="
	// + classid + ") ");
	// } else {
	// usersql
	// .append(" and eu.id not in(select ca.userid from study_class ca where
	// ca.classid="
	// + classid + ") ");
	// }
	// }
	// }
	// usersql.append(" )t where rownum<=? ) where rn>=?");
	// ps = ct.prepareStatement(usersql.toString());
	// ps.setInt(1, dept.getLid());
	// ps.setInt(2, dept.getRid());
	// ps.setInt(3, 999999);
	// ps.setInt(4, 1);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ELUser user = new ELUser();
	// user.setId(rs.getInt(1));
	// user.setRealname(rs.getString(2));
	// user
	// .setDepartment(new Department(rs.getInt(3), rs
	// .getString(4)));
	// user.setUsername(rs.getString(5));
	// user.setJingzhong(rs.getString(6));
	// user.setRole(new ElRole(rs.getInt(7), rs.getString(8)));
	// user.setSex(rs.getString(9));
	// user.setShenfenzheng(rs.getString(10));
	// user.setAge(getAge(rs.getString(10)));
	// user.setIsAssign("未分配");
	// userList.add(user);
	// }
	//
	// Calendar calendar = Calendar.getInstance();
	// if (starttime != null && !"".equals(starttime)) {
	// calendar.setTime(DateUtils.parseDate(starttime,
	// new String[] { "yyyy-mm-dd" }));
	// for (ELUser user : userList) {
	// if (compareAge(user.getShenfenzheng(), calendar, ">")) {
	// returnList.add(user);
	// }
	// }
	// }
	// if (endtime != null && !"".equals(endtime)) {
	// calendar.setTime(DateUtils.parseDate(endtime,
	// new String[] { "yyyy-mm-dd" }));
	// for (ELUser user : userList) {
	// if (compareAge(user.getShenfenzheng(), calendar, "<")) {
	// returnList.add(user);
	// }
	// }
	// }
	// if ((starttime != null && !"".equals(starttime))
	// || (endtime != null && !"".equals(endtime))) {
	// return returnList.size();
	// }
	// return userList.size();
	// } catch (Exception e) {
	// logger.error("分配学员失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }

	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String ElClTypeById(ElClType ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = ElClTypeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree.getChild() != null) {
				return createElClTypeId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 构建有权的课程类型ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @return
	 */
	private String createElClTypeId(List<ElClType> listType, int id) {
		String ids = id + "";
		for (ElClType type : listType) {
			ids = ids + "," + createElClTypeId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * 
	 * @author heiweicheng
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private ElClType ElClTypeById(List<ElClType> listType, int ctid) {
		ElClType courseType = null;
		for (ElClType type : listType) {
			if (type.getId() != ctid) {
				courseType = ElClTypeById(type.getChild(), ctid);
				if (courseType != null) {
					return courseType;
				}
			} else {
				courseType = type;
				return courseType;
			}
		}
		return courseType;
	}

	public ElclassAuditDescribes getClassAudit(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElclassAuditDescribes ead = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select cad.id,cad.elclassid,cad.userid,cad.submittime,cad.feedbacktime,cad.title,cad.status,cad.content,cad.REPLYCONTENT  from elclass_audit_describes cad where cad.elclassid = ?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				ead = new ElclassAuditDescribes();
				ELUser u = new ELUser();
				u.setId(rs.getInt(3));
				ead.setId(rs.getInt(1));
				ead.setClassid(rs.getInt(2));
				ead.setUser(u);
				ead.setSubmittime(rs.getTimestamp(4));
				ead.setFeedbacktime(rs.getTimestamp(5));
				ead.setTitle(rs.getString(6));
				ead.setStatus(rs.getInt(7));
				ead.setContent(rs.getString(8));
				ead.setReplycontent(rs.getString(9));
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取申请说明失败!失败方法：getClassAudit(int classid) 失败原因："
							+ new ElException(e));
			logger.error("获取申请说明失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ead;
	}

	public void UClassAuditContents(ElclassAuditDescribes classAudit)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update elclass_audit_describes set REPLYCONTENT = ? , CONTENT = ? where id  = ?");
			ps.setString(1, classAudit.getReplycontent());
			ps.setString(2, classAudit.getContent());
			ps.setInt(3, classAudit.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ALTER,
					"更新内容出错!失败方法：UClassAuditContents(ElclassAuditDescribes classAudit) 失败原因："
							+ new ElException(e));
			logger.error("更新内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setClassAudit(ElclassAuditDescribes classAudit)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into elclass_audit_describes(elclassid,userid,title,content) values(?,?,?,?)");
			ps.setInt(1, classAudit.getClassid());
			ps.setInt(2, classAudit.getUser().getId());
			ps.setString(3, classAudit.getTitle());
			ps.setString(4, classAudit.getContent());
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"培训班增加备注失败!失败方法：setClassAudit(ElclassAuditDescribes classAudit) 失败原因："
							+ new ElException(e));
			logger.error("培训班增加备注失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkuserClassEroomOperation(int classId, String sqlw)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select  (select id from exam_room where valid in ("
							+ sqlw
							+ ") and id=cc.eroomid) evalid  "
							+ "from course c, class_course cc where cc.courseid = c.id  and cc.classid = ? and cc.status in (0,1)");
			ps.setInt(1, classId);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验证培训班课程失败!失败方法：checkuserClassEroomOperation(int classId, String sqlw) 失败原因："
							+ new ElException(e));
			logger.error("验证培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean isOpen(int classId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean is = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select er.id ,er.valid from course c ,class_course cc ,exam_room er  "
							+ "where cc.courseid = c.id and er.id = cc.eroomid and cc.classid = ? and cc.status in (0,1)");
			ps.setInt(1, classId);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt(2) != 5) {
					is = false;
					break;
				} else {
					is = true;
				}
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验是否开通失败!失败方法：isOpen(int classId) 失败原因："
							+ new ElException(e));
			logger.error("验是否开通失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return is;
	}

	public boolean checkElclassUsers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from elclass_" + type
					+ "  where userid = ? and classid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验证培训班学员失败!失败方法：checkElclassUsers(String type, int userid, int depid) 失败原因："
							+ new ElException(e));
			logger.error("验证培训班学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean checkElclassRegistration(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from ELCLASS_registration where classid = ?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验证培训班申请条件失败!失败方法：checkElclassRegistration 失败原因："
							+ new ElException(e));
			logger.error("验证培训班申请条件失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void addElclassusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into ELClass_" + type
					+ " (userid,classid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_ADD,
					"增加培训班学员失败!失败方法：addElclassusers(String type, int userid, int depid) 失败原因："
							+ new ElException(e));
			logger.error("增加培训班学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ELUser> getElclassUsers(String type, int classid)
			throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from ELClass_"
							+ type
							+ "  du left join eluser eu on eu.id = du.userid where du.classid = ?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取培训班学员失败!失败方法：getElclassUsers(String type, int classid) 失败原因："
							+ new ElException(e));
			logger.error("获取培训班学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return us;
	}

	/**
	 * 查看是培训班否有符合人员
	 * 
	 * @param type
	 * @param elclassid
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassIsUvalid(String type, int elclassid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from elclass_" + type
					+ "  where classid = ?");
			ps.setInt(1, elclassid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"验证培训班否有符合人员失败!失败方法：checkElclassIsUvalid(String type, int elclassid) 失败原因："
							+ new ElException(e));
			logger.error("验证培训班否有符合人员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public boolean checkElclassUvalid(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select uvalid from elclass where id = ?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next())
				if (rs.getInt(1) == 1) {
					return true;
				}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验证培训班复核人员失败!失败方法：checkElclassUvalid(int classid) 失败原因："
							+ new ElException(e));
			logger.error("验证培训班复核人员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 获取参加了培训班的人数
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getJoinNumber(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(ca.userid) from study_class ca where ca.classid=?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取参加了培训班的人数失败!失败方法：getJoinNumber(int classid) 失败原因："
							+ new ElException(e));
			logger.error("获取参加了培训班的人数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取可申请的培训班信息（去掉已删除的）
	 * 
	 * @return
	 * @throws ElException
	 */
	public String getXJBM(Department tree, int cltid) {
		String x = Integer.toString(cltid);
		String ids = DepTreeById(tree, cltid);
		if (!ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		return ids;
	}

	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String DepTreeById(Department ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = DepTreeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree.getChild() != null) {
				return createDepTreeId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 构建有权的课程类型ID
	 * 
	 * @author heiweicheng
	 * @param ctypeTree
	 * @return
	 */
	private String createDepTreeId(List<Department> listType, int id) {
		String ids = id + "";
		for (Department type : listType) {
			ids = ids + "," + createDepTreeId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * 
	 * @author heiweicheng
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private Department DepTreeById(List<Department> listType, int ctid) {
		Department courseType = null;
		for (Department type : listType) {
			if (type.getId() != ctid) {
				courseType = DepTreeById(type.getChild(), ctid);
				if (courseType != null) {
					return courseType;
				}
			} else {
				courseType = type;
				return courseType;
			}
		}
		return courseType;
	}

	/**
	 * 根据培训班条件搜索学员
	 * 
	 * @param dep
	 * @param table(study_room
	 *            study_class)
	 * @param tid
	 * @param classid
	 * @param elUser
	 * @param starttime
	 * @param endtime
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> gettoClassInfoselectUser(Department depTree,
			Department dep, String table, int tid, int classid, ELUser elUser,
			String starttime, String endtime, int pageNow, int pageSize)
			throws ElException {
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();
		String LidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			ct = DBConnection.getConnection();
			Department dept = new Department();
			if (dep.getId() == -2) {
				for (int i = 0; i < dep.getChild().size(); i++) {
					if (depids.equals("")) {
						depids = depids + dep.getChild().get(i).getId();
					} else {
						depids = depids + "," + dep.getChild().get(i).getId();
					}
				}
				ps = ct
						.prepareStatement("select lid,rid from DEPARTMENT where id in ("
								+ depids + ")");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (dep.getChild().size() == 1) {
						LidRid = LidRid + " (dp.lid >= " + rs.getInt(1)
								+ " and  dp.rid <= " + rs.getInt(2) + ")";
					} else {
						if (dep.getChild().size() > 1
								&& dep.getChild().size() != x && x > 1) {// 中间不用加
							LidRid = LidRid + " or (dp.lid >= " + rs.getInt(1)
									+ " and  dp.rid <= " + rs.getInt(2) + ")";
						} else if (dep.getChild().size() == x) {// 结束前面加 ）
							LidRid = LidRid + "  or (dp.lid >= " + rs.getInt(1)
									+ " and  dp.rid <= " + rs.getInt(2) + "))";
						} else {// 开始前面加 （
							LidRid = LidRid + "  ( (dp.lid >= " + rs.getInt(1)
									+ " and  dp.rid <= " + rs.getInt(2) + ")";
						}
						x++;
					}
				}
			} else {
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, dep.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					dept.setId(rs.getInt(1));
					dept.setLid(rs.getInt(2));
					dept.setRid(rs.getInt(3));
					LidRid = LidRid + " dp.lid>=" + rs.getInt(2)
							+ " and dp.rid<= " + rs.getInt(3);
				}
			}
			ps.close();
			rs.close();

			String userWhere = "";
			if (elUser != null) {
				if (elUser.getIsPass() != null
						&& !elUser.getIsPass().equals("")) {
					if (elUser.getIsPass().equals("0"))// 通过
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_class ca where ca.classid ="
								+ classid + " and certificateno is not null)";
					else if (elUser.getIsPass().equals("1"))// 不通过
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_class ca where ca.classid ="
								+ classid + " and certificateno is null)";
					else
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_class ca where ca.classid ="
								+ classid + " )";
				}
			}
			String sql = "select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
					+ " from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null "
					+ " " + LidRid + userWhere;

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();

			String userids = "";// 获取符合要求的用户id
			String userSql = "";// 用户sql
			while (rs.next()) {
				boolean isZF = false;
				boolean isBX = false;
				boolean isXX = false;
				// 总学分
				if (elUser.getBtotalscore() != 0
						&& elUser.getBtotalscore_() != 0) {
					int Btotalscore = classStudentScore2(classid, rs.getInt(1),
							2);
					if (elUser.getBtotalscore() <= Btotalscore
							&& Btotalscore <= elUser.getBtotalscore_()) {
						isZF = true;
					}
				} else {
					isZF = true;
				}
				// 必修课总学分
				if (elUser.getBxscore() != 0 && elUser.getBxscore_() != 0) {
					int Bxscore = classStudentScore2(classid, rs.getInt(1), 0);
					if (elUser.getBxscore() <= Bxscore
							&& Bxscore <= elUser.getBxscore_()) {
						isBX = true;
					}
				} else {
					isBX = true;
				}
				// 选修课总学分
				if (elUser.getXxscore() != 0 && elUser.getXxscore_() != 0) {
					int Xxscore = classStudentScore2(classid, rs.getInt(1), 1);
					if (elUser.getXxscore() <= Xxscore
							&& Xxscore <= elUser.getXxscore_()) {
						isXX = true;
					}
				} else {
					isXX = true;
				}
				if (isZF && isBX && isXX) {
					if (userids.equals(""))
						userids = userids + rs.getInt(1);
					else
						userids = userids + "," + rs.getInt(1);
				}
			}
			rs.close();
			if (!userids.equals("")) {
				userSql = " and eu.id in(" + userids + ")";
			} else {
				userSql = " and eu.id in null";
			}

			String sqls = "select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
					+ " from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null "
					+ " "
					+ LidRid
					+ userWhere
					+ userSql
					+ " )t where rownum<=? ) where rn>=?";

			ps = ct.prepareStatement(sqls);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
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
				user.setIsAssign("未分配");
				user.setAge(rs.getInt(11));
				userList.add(user);
			}
			rs.close();

			ps = ct.prepareStatement("select ca.userid,ca.joinway from "
					+ table + " ca where ca.classid in (" + tid + ")"); // study_room
			// study_class
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
						users.setJoinway(rs.getInt("joinway") == 0 ? "分配"
								: "申请");
					}
				}
			}
			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return returnList;
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	/**
	 * 根据培训班条件搜索学员
	 * 
	 * @param dep
	 * @param classid
	 * @param elUser
	 * @param starttime
	 * @param endtime
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int gettoClassInfoselectUserSize(Department depTree, Department dep,
			String table, int tid, int classid, ELUser elUser,
			String starttime, String endtime) throws ElException {
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();

		String LidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			ct = DBConnection.getConnection();
			Department dept = new Department();
			if (dep.getId() == -2) {
				for (int i = 0; i < dep.getChild().size(); i++) {
					if (depids.equals("")) {
						depids = depids + dep.getChild().get(i).getId();
					} else {
						depids = depids + "," + dep.getChild().get(i).getId();
					}
				}
				ps = ct
						.prepareStatement("select lid,rid from DEPARTMENT where id in ("
								+ depids + ")");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (dep.getChild().size() == 1) {
						LidRid = LidRid + " (dp.lid >= " + rs.getInt(1)
								+ " and  dp.rid <= " + rs.getInt(2) + ")";
					} else {
						if (dep.getChild().size() > 1
								&& dep.getChild().size() != x && x > 1) {// 中间不用加
							LidRid = LidRid + " or (dp.lid >= " + rs.getInt(1)
									+ " and  dp.rid <= " + rs.getInt(2) + ")";
						} else if (dep.getChild().size() == x) {// 结束前面加 ）
							LidRid = LidRid + "  or (dp.lid >= " + rs.getInt(1)
									+ " and  dp.rid <= " + rs.getInt(2) + "))";
						} else {// 开始前面加 （
							LidRid = LidRid + "  ( (dp.lid >= " + rs.getInt(1)
									+ " and  dp.rid <= " + rs.getInt(2) + ")";
						}
						x++;
					}
				}
			} else {
				// 获取 部门的左右值
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
				ps.setInt(1, dep.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					dept.setId(rs.getInt(1));
					dept.setLid(rs.getInt(2));
					dept.setRid(rs.getInt(3));
					LidRid = LidRid + " dp.lid>=" + rs.getInt(2)
							+ " and dp.rid<= " + rs.getInt(3);
				}
			}
			ps.close();
			rs.close();

			String userWhere = "";
			if (elUser != null) {
				if (elUser.getIsPass() != null
						&& !elUser.getIsPass().equals("")) {
					if (elUser.getIsPass().equals("0"))// 通过
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_class ca where ca.classid ="
								+ classid + " and certificateno is not null)";
					else if (elUser.getIsPass().equals("1"))// 不通过
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_class ca where ca.classid ="
								+ classid + " and certificateno is null)";
					else
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_class ca where ca.classid ="
								+ classid + " )";
				}
			}
			String sql = "select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
					+ " from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null "
					+ " " + LidRid + userWhere;

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();

			String userids = "";// 获取符合要求的用户id
			String userSql = "";// 用户sql
			while (rs.next()) {
				boolean isZF = false;
				boolean isBX = false;
				boolean isXX = false;
				// 总学分
				if (elUser.getBtotalscore() != 0
						&& elUser.getBtotalscore_() != 0) {
					int Btotalscore = classStudentScore2(classid, rs.getInt(1),
							2);
					if (elUser.getBtotalscore() < Btotalscore
							&& Btotalscore < elUser.getBtotalscore_()) {
						isZF = true;
					}
				} else {
					isZF = true;
				}
				// 必修课总学分
				if (elUser.getBxscore() != 0 && elUser.getBxscore_() != 0) {
					int Bxscore = classStudentScore2(classid, rs.getInt(1), 0);
					if (elUser.getBxscore() < Bxscore
							&& Bxscore < elUser.getBxscore_()) {
						isBX = true;
					}
				} else {
					isBX = true;
				}
				// 选修课总学分
				if (elUser.getXxscore() != 0 && elUser.getXxscore_() != 0) {
					int Xxscore = classStudentScore2(classid, rs.getInt(1), 1);
					if (elUser.getXxscore() < Xxscore
							&& Xxscore < elUser.getXxscore_()) {
						isXX = true;
					}
				} else {
					isXX = true;
				}
				if (isZF && isBX && isXX) {
					if (userids.equals(""))
						userids = userids + rs.getInt(1);
					else
						userids = userids + "," + rs.getInt(1);
				}
			}
			rs.close();
			if (!userids.equals("")) {
				userSql = " and eu.id in(" + userids + ")";
			} else {
				userSql = " and eu.id in null";
			}

			String sqls = "select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
					+ " from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null "
					+ " " + LidRid + userWhere + userSql;

			ps = ct.prepareStatement(sqls);
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
				user.setIsAssign("未分配");
				user.setAge(rs.getInt(11));
				userList.add(user);
			}
			rs.close();

			ps = ct.prepareStatement("select ca.userid,ca.joinway from "
					+ table + " ca where ca.classid in (" + tid + ")");
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
						users.setJoinway(rs.getInt("joinway") == 0 ? "分配"
								: "申请");
					}
				}
			}
			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return userList.size();
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList.size();
	}

	/**
	 * 根据考场条件搜索学员
	 * 
	 * @param dep
	 * @param cid
	 * @param eroomid
	 * @param elUser
	 * @param starttime
	 * @param endtime
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> gettoEroomInfoselectUser(Department depTree,
			Department dep, String table, int tid, int eroomid, ELUser elUser,
			String starttime, String endtime, int pageNow, int pageSize)
			throws ElException {
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();

		String LidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			ct = DBConnection.getConnection();
			Department dept = new Department();
			if (dep.getId() == -2) {
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
				ps.setInt(1, dep.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					dept.setId(rs.getInt(1));
					dept.setLid(rs.getInt(2));
					dept.setRid(rs.getInt(3));
					LidRid = LidRid + " dp.lid>=" + rs.getInt(2)
							+ " and dp.rid<= " + rs.getInt(3);
				}
			}
			ps.close();

			String userWhere = "";
			if (elUser != null) {
				if (elUser.getIsKcPass() != null
						&& !elUser.getIsKcPass().equals("")) {// 通过条件
					if (elUser.getIsKcPass().equals("0"))// 通过
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_room ca where ca.roomid ="
								+ eroomid + " and ispassed != 0)";
					else if (elUser.getIsKcPass().equals("1"))// 不通过
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_room ca where ca.roomid ="
								+ eroomid + " and ispassed = 0)";
					else
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_room ca where ca.roomid ="
								+ eroomid + " )";
				}
				if (elUser.getKcBtotalscore() != -1
						&& elUser.getKcBtotalscore() != 0) { // 总分条件
					userWhere = userWhere
							+ " and eu.id in (select userid from study_quizinfo where roomid = "
							+ eroomid
							+ "  and  (select sum(myscore) from study_quizinfo where roomid = "
							+ eroomid + " and userid = eu.id  ) >= "
							+ elUser.getKcBtotalscore() + ")  ";
				}
				if (elUser.getKcBtotalscore_() != -1
						&& elUser.getKcBtotalscore_() != 0) {
					userWhere = userWhere
							+ " and eu.id in (select userid from study_quizinfo where roomid = "
							+ eroomid
							+ "  and  (select sum(myscore) from study_quizinfo where roomid = "
							+ eroomid + " and userid = eu.id  ) <= "
							+ elUser.getKcBtotalscore_() + ")  ";
				}
				if (elUser.getKcsq() != null) {
					for (int i = 0; i < elUser.getKcsq().size(); i++) { // 试卷条件
						if (!elUser.getKcsq().get(i).equals("不限")
								&& !elUser.getKcsq().get(i).equals("")) {
							userWhere = userWhere
									+ " and eu.id in (select userid from study_quizinfo where roomid = "
									+ eroomid
									+ "  and  (select sum(myscore) from study_quizinfo where roomid = "
									+ eroomid
									+ " and userid = eu.id and epid = "
									+ elUser.getEpids().get(i) + " ) >= "
									+ elUser.getKcsq().get(i) + ") ";
						}
						if (!elUser.getKcsq_().get(i).equals("不限")
								&& !elUser.getKcsq_().get(i).equals("")) {
							userWhere = userWhere
									+ " and eu.id in (select userid from study_quizinfo where roomid = "
									+ eroomid
									+ "  and  (select sum(myscore) from study_quizinfo where roomid = "
									+ eroomid
									+ " and userid = eu.id and epid = "
									+ elUser.getEpids().get(i) + " ) <= "
									+ elUser.getKcsq_().get(i) + ") ";
						}
					}
				}
				if (elUser.getKclxcs() != null) {
					for (int i = 0; i < elUser.getKclxcs().size(); i++) { // 练习次数条件
						if (!elUser.getKclxcs().get(i).equals("不限")
								&& !elUser.getKclxcs().get(i).equals("")) {
							userWhere = userWhere
									+ " and eu.id in (select userid from study_quizinfo where roomid = "
									+ eroomid
									+ " and userid = eu.id and  myexamcount >= "
									+ elUser.getKclxcs().get(i) + " ) ";
						}
						if (!elUser.getKclxcs_().get(i).equals("不限")
								&& !elUser.getKclxcs_().get(i).equals("")) {
							userWhere = userWhere
									+ " and eu.id in (select userid from study_quizinfo where roomid = "
									+ eroomid
									+ " and userid = eu.id and  myexamcount <= "
									+ elUser.getKclxcs().get(i) + " ) ";
						}
					}
				}
			}
			String sql = "select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
					+ " from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null "
					+ " "
					+ LidRid
					+ userWhere
					+ " )t where rownum<=? ) where rn>=?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
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
				user.setIsAssign("未分配");
				user.setAge(rs.getInt(11));
				userList.add(user);
			}
			rs.close();

			ps = ct.prepareStatement("select ca.userid,ca.joinway from "
					+ table + " ca where ca.classid in (" + tid + ")");
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
						users.setJoinway(rs.getInt("joinway") == 0 ? "分配"
								: "申请");
					}
				}
			}
			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return returnList;
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	/**
	 * 根据考场条件搜索学员Size
	 * 
	 * @param dep
	 * @param cid
	 * @param eroomid
	 * @param elUser
	 * @param starttime
	 * @param endtime
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int gettoEroomInfoselectUserSize(Department depTree, Department dep,
			String table, int tid, int eroomid, ELUser elUser,
			String starttime, String endtime) throws ElException {
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();
		String LidRid = " and ";
		String depids = "";
		int x = 1;
		try {
			ct = DBConnection.getConnection();
			Department dept = new Department();
			if (dep.getId() == -2) {
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
				ps.setInt(1, dep.getId());
				rs = ps.executeQuery();
				if (rs.next()) {
					dept.setId(rs.getInt(1));
					dept.setLid(rs.getInt(2));
					dept.setRid(rs.getInt(3));
					LidRid = LidRid + " dp.lid>=" + rs.getInt(2)
							+ " and dp.rid<= " + rs.getInt(3);
				}
			}
			ps.close();

			String userWhere = "";
			if (elUser != null) {
				if (elUser.getIsKcPass() != null
						&& !elUser.getIsKcPass().equals("")) {// 通过条件
					if (elUser.getIsKcPass().equals("0"))// 通过
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_room ca where ca.roomid ="
								+ eroomid + " and ispassed != 0)";
					else if (elUser.getIsKcPass().equals("1"))// 不通过
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_room ca where ca.roomid ="
								+ eroomid + " and ispassed = 0)";
					else
						userWhere = userWhere
								+ " and eu.id in(select ca.userid from study_room ca where ca.roomid ="
								+ eroomid + " )";
				}
				if (elUser.getKcBtotalscore() != -1
						&& elUser.getKcBtotalscore() != 0) { // 总分条件
					userWhere = userWhere
							+ " and eu.id in (select userid from study_quizinfo where roomid = "
							+ eroomid
							+ "  and  (select sum(myscore) from study_quizinfo where roomid = "
							+ eroomid + " and userid = eu.id  ) >= "
							+ elUser.getKcBtotalscore() + ")  ";
				}
				if (elUser.getKcBtotalscore_() != -1
						&& elUser.getKcBtotalscore_() != 0) {
					userWhere = userWhere
							+ " and eu.id in (select userid from study_quizinfo where roomid = "
							+ eroomid
							+ "  and  (select sum(myscore) from study_quizinfo where roomid = "
							+ eroomid + " and userid = eu.id  ) <= "
							+ elUser.getKcBtotalscore_() + ")  ";
				}
				if (elUser.getKcsq() != null) {
					for (int i = 0; i < elUser.getKcsq().size(); i++) { // 试卷条件
						if (!elUser.getKcsq().get(i).equals("不限")
								&& !elUser.getKcsq().get(i).equals("")) {
							userWhere = userWhere
									+ " and eu.id in (select userid from study_quizinfo where roomid = "
									+ eroomid
									+ "  and  (select sum(myscore) from study_quizinfo where roomid = "
									+ eroomid
									+ " and userid = eu.id and epid = "
									+ elUser.getEpids().get(i) + " ) >= "
									+ elUser.getKcsq().get(i) + ") ";
						}
						if (!elUser.getKcsq_().get(i).equals("不限")
								&& !elUser.getKcsq_().get(i).equals("")) {
							userWhere = userWhere
									+ " and eu.id in (select userid from study_quizinfo where roomid = "
									+ eroomid
									+ "  and  (select sum(myscore) from study_quizinfo where roomid = "
									+ eroomid
									+ " and userid = eu.id and epid = "
									+ elUser.getEpids().get(i) + " ) <= "
									+ elUser.getKcsq_().get(i) + ") ";
						}
					}
				}
				if (elUser.getKclxcs() != null) {
					for (int i = 0; i < elUser.getKclxcs().size(); i++) { // 练习次数条件
						if (!elUser.getKclxcs().get(i).equals("不限")
								&& !elUser.getKclxcs().get(i).equals("")) {
							userWhere = userWhere
									+ " and eu.id in (select userid from study_quizinfo where roomid = "
									+ eroomid
									+ " and userid = eu.id and  myexamcount >= "
									+ elUser.getKclxcs().get(i) + " ) ";
						}
						if (!elUser.getKclxcs_().get(i).equals("不限")
								&& !elUser.getKclxcs_().get(i).equals("")) {
							userWhere = userWhere
									+ " and eu.id in (select userid from study_quizinfo where roomid = "
									+ eroomid
									+ " and userid = eu.id and  myexamcount <= "
									+ elUser.getKclxcs().get(i) + " ) ";
						}
					}
				}
			}
			String sql = "select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
					+ " from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null "
					+ " " + LidRid + userWhere;

			ps = ct.prepareStatement(sql);
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
				user.setIsAssign("未分配");
				user.setAge(rs.getInt(11));
				userList.add(user);
			}
			rs.close();

			ps = ct.prepareStatement("select ca.userid,ca.joinway from "
					+ table + " ca where ca.classid in (" + tid + ")");
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
						users.setJoinway(rs.getInt("joinway") == 0 ? "分配"
								: "申请");
					}
				}
			}
			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return userList.size();
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList.size();
	}

	/**
	 * 获取考试的成绩 当epid = 0 时为总成绩
	 * 
	 * @param roomid
	 * @param userid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public int getExamroomUserScore(int roomid, int userid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		String ep = "";
		try {
			if (epid != 0) {
				ep = " and epid = " + epid;
			}
			if (userid == 490) {
			}
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select sum(myscore) from study_quizinfo where roomid = ? and userid = ? "
							+ ep);
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取参加了考场的成绩失败!失败方法：getExamroomUserScore 失败原因："
							+ new ElException(e));
			logger.error("获取参加了考场的成绩失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取考试的练习次数 当epid = 0 时为总共练习次数
	 * 
	 * @param roomid
	 * @param userid
	 * @param epid
	 * @return
	 * @throws ElException
	 */
	public int getExamroomUserPracticeNumber(int roomid, int userid, int epid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		String ep = "";
		try {
			if (epid != 0) {
				ep = " and epid = " + epid;
			}
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select myexamcount from study_quizinfo where roomid = ? and userid = ? "
							+ ep);
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取参加了考试的次数失败!失败方法：getExamroomUserScore 失败原因："
							+ new ElException(e));
			logger.error("获取参加了考试的次数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 验证该培训班是否已存在userid用户
	 * 
	 * @param classid
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public boolean checkElclassIsUsers(int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from study_class  where userid = ? and classid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_OETHER,
					"验证培训班学员失败!失败方法：checkElclassIsUsers(int userid, int classid) 失败原因："
							+ new ElException(e));
			logger.error("验证培训班学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	/**
	 * 获取培训班计划招收人数
	 * 
	 * @param elcid
	 * @return
	 * @throws ElException
	 */
	public int getElclassPlanNumber(int elcid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select planrecruitstudents from elclass_registration where classid = ?");
			ps.setInt(1, elcid);
			rs = ps.executeQuery();
			if (rs.next())
				number = rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取培训班计划招收人数信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取与当前培训班时间重叠的培训班
	 * 
	 * @param elclass
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> getClassTimeoverList(ElClass elclass, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> cls = new ArrayList<ElClass>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*, rownum rn from (select el.id elid,el.name elname,el.starttime,el.finishtime,el.status,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,(select count(sc.userid) from study_class sc where sc.classid=el.id) classsize from elclass el left join eluser eu on el.creater=eu.id left join department dep on eu.depid=dep.id where ?<el.finishtime and ?>el.starttime and el.status=5) t where rownum <= ?) where rn >= ? ");
			ps.setTimestamp(1, elclass.getStarttime());
			ps.setTimestamp(2, elclass.getFinishtime());
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt("elid"), rs
						.getString("elname"));
				cl.setStarttime(rs.getTimestamp("starttime"));
				cl.setFinishtime(rs.getTimestamp("finishtime"));
				cl.setStatus(rs.getInt("status"));
				user = new ELUser(rs.getInt("euid"), rs.getString("realname"));
				user.setUsername(rs.getString("username"));
				user.setDepartment(new Department(rs.getInt("depid"), rs
						.getString("depname")));
				cl.setCreater(user);
				cl.setClassSize(rs.getInt("classsize"));
				cls.add(cl);
			}

		} catch (Exception e) {
			logger.error("获取与当前培训班时间重叠的培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cls;
	}

	/**
	 * 获取与当前培训班时间重叠的培训班数量
	 * 
	 * @param elclass
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getClassTimeoverListCount(ElClass elclass) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(el.id) from elclass el where ?<el.finishtime and ?>el.starttime and el.status=5 ");
			ps.setTimestamp(1, elclass.getStarttime());
			ps.setTimestamp(2, elclass.getFinishtime());
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取与当前培训班时间重叠的培训班数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取培训班信息以及创建者信息
	 * 
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ElClass getClassById2(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ElClass cl = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select el.id elid,el.name elname,el.starttime,el.finishtime,el.status,eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,(select count(sc.userid) from study_class sc where sc.classid=el.id) classsize from elclass el left join eluser eu on el.creater=eu.id left join department dep on eu.depid=dep.id where el.id=? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				cl = new ElClass(rs.getInt("elid"), rs.getString("elname"));
				cl.setStarttime(rs.getTimestamp("starttime"));
				cl.setFinishtime(rs.getTimestamp("finishtime"));
				cl.setStatus(rs.getInt("status"));
				ELUser user = new ELUser(rs.getInt("euid"), rs
						.getString("realname"));
				user.setUsername(rs.getString("username"));
				user.setDepartment(new Department(rs.getInt("depid"), rs
						.getString("depname")));
				cl.setCreater(user);
				cl.setClassSize(rs.getInt("classsize"));
			}
		} catch (Exception e) {
			logger.error("获取培训班信息以及创建者信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cl;
	}

	public int copyClass(int id) throws ElException {
		// 获取培训班基本信息
		ElClass elClass = getClassById(id);
		// 考场对象
		ExamRoom er = null;
		// 考场Dao
		EroomDao eroomDao = (EroomDao) SpringContextUtil.getBean("eroomDao");
		// 通过培训班id获取可以申请信息
		ELClassRegistration eClassRegistration = getClassRegistration(id);
		elClass.setGroup1(new ElGroup());
		elClass.setGroup2(new ElGroup());
		elClass.setName(elClass.getName() + "_副本");
		// 添加培训班
		addClass(elClass);
		// 如果培训班不是申请状态就不添加
		if (eClassRegistration != null) {
			// 重新设置新添加的培训班id
			eClassRegistration.setElclass(elClass);
			// 添加可申请
			addClassRegistration(eClassRegistration);
		}
		// 获取培训班的必须课列表
		List<Course> couList = listClassCourses(id, 0);
		if (couList.size() > 0) {
			for (int i = 0; i < couList.size(); i++) {
				Course c = couList.get(i);
				int bid = c.getId();
				// 根据培训班id和课程id获取考场id
				// 添加培训班课程列表
				addClassCourse2(elClass.getId(), bid, 0, c.getGetcredit(), c
						.getSetcredit(), c.getSuggestcredit(),
						c.getRoomstart(), c.getRoomend());
				er = eroomDao.getExamRoom(bid, id);
				if (er != null) {
					// 添加考场
					int erid = eroomDao.copyEroom(er.getId(), elClass.getId(),
							bid);
					eroomDao.updateExamRoomInBandClassid(erid, elClass.getId());
					eroomDao.setClassBindingCourse(elClass.getId(), bid, erid);
				}
			}
		}
		// 获取培训班的修选课
		List<Course> couList2 = listClassCourses(id, 1);
		if (couList2.size() > 0) {
			for (int i = 0; i < couList2.size(); i++) {
				Course c = couList2.get(i);
				int bid = c.getId();
				// 根据培训班id和课程id获取考场id
				// 添加培训班课程列表
				addClassCourse2(elClass.getId(), bid, 1, c.getGetcredit(), c
						.getSetcredit(), c.getSuggestcredit(),
						c.getRoomstart(), c.getRoomend());
				er = eroomDao.getExamRoom(bid, id);
				if (er != null) {
					// 添加考场
					int erid = eroomDao.copyEroom(er.getId(), elClass.getId(),
							bid);
					eroomDao.updateExamRoomInBandClassid(erid, elClass.getId());
					eroomDao.setClassBindingCourse(elClass.getId(), bid, erid);
				}

			}
		}
		return elClass.getId();
	}

	/**
	 * 获取可申请且人员要审核的培训班里面的需要审核的人员
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	// public List<MyClass> getClassNoAuditUser(int classid,int pageNow, int
	// pageSize)throws ElException{
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<MyClass> myClassList = new ArrayList<MyClass>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement("select * from(select t.*,rownum rn from (" +
	// "select eu.id euid,eu.username,eu.realname,dep.id
	// depid,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl(
	// floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1)
	// age_,sc.status " +
	// " from study_class_apply sc inner join eluser eu on sc.userid=eu.id " +
	// " inner join department dep on eu.depid=dep.id where sc.classid=? and
	// sc.status in(1,2,3))t where rownum<=? ) where rn>=?");
	// ps.setInt(1, classid);
	// ps.setInt(2, pageNow);
	// ps.setInt(3, pageSize);
	//			
	//			
	// System.out.println("classid:"+classid+"\t pageNow:"+pageNow+"\t
	// pageSize:"+pageSize);
	//			
	//			
	// rs = ps.executeQuery();
	// ELUser user =null;
	// MyClass myClass=null;
	// while(rs.next()) {
	// myClass=new MyClass();
	// user = new ELUser(rs.getInt(1),rs.getString(2),rs.getString(3));
	// user.setDepartment(new Department(rs.getInt(4),rs.getString(5)));
	// user.setShenfenzheng(rs.getString(6));
	// user.setSex(rs.getString(7));
	// user.setJingzhong(rs.getInt(8));
	// user.setShengri(rs.getDate(9));
	// user.setAge(rs.getInt(10));
	// myClass.setStatus(rs.getInt(11));
	// myClass.setUser(user);
	// myClassList.add(myClass);
	// }
	// } catch (Exception e) {
	// logger.error("获取可申请且人员要审核的培训班里面的需要审核的人员出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return myClassList;
	// }
	/**
	 * 获取可申请且人员要审核的培训班里面的需要审核的人员
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<MyClass> getClassNoAuditUser(int classid, int pageNow,
			int pageSize, ELUser eu, Department dep, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyClass> myClassList = new ArrayList<MyClass>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select * from(select t.*,rownum rn from
			// (" +
			// "select eu.id euid,eu.username,eu.realname,dep.id
			// depid,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl(
			// floor(to_char(sysdate,'yyyy'
			// ))-floor(to_char(shengri,'yyyy')),-1) age_,sc.status " +
			// " from study_class_apply sc inner join eluser eu on
			// sc.userid=eu.id " +
			// " inner join department dep on eu.depid=dep.id where sc.classid=?
			// and eu.username=? and eu.realname=? and sc.status=?)t where
			// rownum<=? ) where rn>=?");

			String sql = "select * from(select t.*,rownum rn from ("
					+ "select eu.id euid,eu.username,eu.realname,dep.id depid,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,sc.status "
					+ " from study_class_apply sc inner join eluser eu on sc.userid=eu.id "
					+ " inner join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", dep, true)
					+ ") dep on eu.depid=dep.id where sc.classid=? ";
			if (eu != null && null != eu.getUsername()
					&& !"".equals(eu.getUsername().trim()))
				sql += " and eu.username like '%" + eu.getUsername().trim()
						+ "%' ";
			if (eu != null && null != eu.getRealname()
					&& !"".equals(eu.getRealname().trim()))
				sql += " and eu.realname like '%" + eu.getRealname().trim()
						+ "%' ";
			if (status != -1)
				sql += " and sc.status=" + status;

			sql += " )t where rownum<=? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			ELUser user = null;
			MyClass myClass = null;
			while (rs.next()) {
				myClass = new MyClass();
				user = new ELUser(rs.getInt(1), rs.getString(2), rs
						.getString(3));
				user
						.setDepartment(new Department(rs.getInt(4), rs
								.getString(5)));
				user.setShenfenzheng(rs.getString(6));
				user.setSex(rs.getString(7));
				user.setJingzhong(rs.getInt(8));
				user.setShengri(rs.getDate(9));
				user.setAge(rs.getInt(10));
				myClass.setStatus(rs.getInt(11));
				myClass.setUser(user);
				myClassList.add(myClass);
			}
		} catch (Exception e) {
			logger.error("获取可申请且人员要审核的培训班里面的需要审核的人员出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myClassList;
	}

	/**
	 * 获取可申请且人员要审核的培训班里面的需要审核的人员数量
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getClassNoAuditUserSize(int classid, ELUser eu, Department dep,
			int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("select count(userid) from
			// study_class_apply sc where sc.classid=? and sc.status
			// in(1,2,3)");
			// ps.setInt(1, classid);
			String sql = " select count(*) from ("
					+ "select eu.id euid,eu.username,eu.realname,dep.id depid,dep.name,eu.shenfenzheng,eu.sex,eu.jingzhong,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_,sc.status "
					+ " from study_class_apply sc inner join eluser eu on sc.userid=eu.id "
					+ " inner join ("
					+ ((ElNodeSQL) SpringContextUtil
							.getBean(ElConstants.CLASS_ELNODESQL))
							.generateSQLByTree("department", dep, true)
					+ ") dep on eu.depid=dep.id where sc.classid=? ";
			if (eu != null && null != eu.getUsername()
					&& !"".equals(eu.getUsername().trim()))
				sql += " and eu.username like '%" + eu.getUsername().trim()
						+ "%' ";
			if (eu != null && null != eu.getRealname()
					&& !"".equals(eu.getRealname().trim()))
				sql += " and eu.realname like '%" + eu.getRealname().trim()
						+ "%' ";
			if (status != -1)
				sql += " and sc.status=" + status;
			sql += " )t ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取可申请且人员要审核的培训班里面的需要审核的人员数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 获取已报培训班的人数
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int getClassApplyNumber(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(sca.userid) from study_class_apply sca where sca.classid=?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取已报培训班的人数失败!失败方法：getClassApplyNumber(int classid) 失败原因："
							+ new ElException(e));
			logger.error("获取已报培训班的人数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取培训班人数
	 * 
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int getClassUserSize(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(userid) from study_class where classid = ?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			if (rs.next())
				number = rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取培训班人数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 删除学员培训班报名记录
	 * 
	 * @param roomid
	 * @param userid
	 * @throws ElException
	 */
	public void deleteStudyClassApply(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from study_class_apply where classid=? and userid=?");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除学员培训班报名记录出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取培训班中所有被绑定的考场
	 * 
	 * @param classId
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getClassRooms(int classId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> examRooms = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eroomid,er.title,valid,cc.courseid,cc.classid from class_course cc inner join exam_room er on cc.eroomid=er.id where cc.classId  = ?  and cc.binding = 1");//
			ps.setInt(1, classId);
			rs = ps.executeQuery();
			ExamRoom examRoom = null;
			Course course = null;
			while (rs.next()) {
				examRoom = new ExamRoom(rs.getInt(1), rs.getString(2));
				examRoom.setValid(rs.getInt(3));
				course = new Course();
				course.setId(rs.getInt(4));
				course.setClassid(rs.getInt(5));
				examRoom.setCourse(course);
				examRooms.add(examRoom);
			}
		} catch (Exception e) {
			logger.error("获取培训班中所有被绑定的考场出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return examRooms;
	}

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
			// sql.insert(0, "select tt.*,sr.joinway from (");
			// sql.append("");
			// 分页
			// sql.insert(0, "select * from (select t.* ,rownum rn from ( ");
			// sql.append(")t where rownum<=?) where rn>=?");
			ps = ct.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				user = new ELUser(rs.getInt(1), rs.getString(2), rs
						.getString(3));
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

	public List<ELUser> listUserOnClassSeach(List<ClassPara> oldClassParas,
			int classid, ELUser elUser, int pageNow, int pageSize)
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
					.append(") tt left join (select * from study_class where classid=?) sr on tt.euid=sr.userid");
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
				if (this.checkElclassIsUsers(user.getId(), classid)) {
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

	public List<ElClass> getClassByUserid(int userid, int courseid,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> elclassList = new ArrayList<ElClass>();
		try {
			String sql = "select * from (select t.*, rownum rn from (select cl.id,cl.name cname,cl.certificatename,cl.cltype ,cl.description ,cl.optionalcredit,cl.status,clt.name,cl.mainimg,"
					+ "cl.global,cl.group1 ,cl.group2,cl.diplomatime,cl.starttime,cl.finishtime,cl.classtype,cl.isnormal,cl.isApplication,cl.createtime "
					+ " from elclass cl,elclasstype clt,study_class sc "
					+ " where cl.cltype = clt.id and sc.classid = cl.id and cl.classtype = 2"
					+ " and sc.userid = ? and cl.status = 5 order by cl.createtime desc) t where rownum <= ?) where rn >= ? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass cl = new ElClass(rs.getInt(1), rs.getString(2));
				cl.setCertificatename(rs.getString(3));
				cl.setCltype(new ElClType(rs.getInt(4), rs.getString(8)));
				cl.setDescription(rs.getString(5));
				cl.setOptionalcredit(rs.getInt(6));
				cl.setStatus(rs.getInt(7));
				cl.setMainimg(rs.getString(9));
				cl.setGlobal(rs.getInt(10));
				cl.setGroup1(new ElGroup(rs.getInt(11)));
				cl.setGroup2(new ElGroup(rs.getInt(12)));
				cl.setDiplomatime(rs.getTimestamp(13));
				cl.setStarttime(rs.getTimestamp("starttime"));
				cl.setFinishtime(rs.getTimestamp("finishtime"));
				cl.setClasstype(rs.getInt("classtype"));
				cl.setIsnormal(rs.getInt("isnormal"));
				cl.setIsApplication(rs.getInt("isApplication"));
				cl.setIsExists(CheckClassIsExistsCourse(courseid, rs.getInt(1),
						userid) ? 1 : 0);
				elclassList.add(cl);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"获取我有的培训班失败!失败方法：getClassByUserid(int userid) 失败原因："
							+ new ElException(e));
			logger.error("获取我有的培训班失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elclassList;
	}

	/**
	 * 验证该培训是否存在改课程
	 * 
	 * @param courseid
	 * @param elclassid
	 * @return
	 * @throws ElException
	 */
	public boolean CheckClassIsExistsCourse(int courseid, int classid,
			int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean isExists = false;
		try {
			String sql = " select * from class_course_at where classid = ? and  courseid = ? and userid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				isExists = true;
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return isExists;
	}

	/**
	 * 验证该培训是否的某课程是否考过（是否拿到学分）
	 * 
	 * @param courseid
	 * @param elclassid
	 * @return
	 * @throws ElException
	 */
	public List<ElClass> CheckClassIsKs_passCourse(int courseid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ElClass> classList = new ArrayList<ElClass>();
		try {
			String sql = "select sc.classid,el.name from study_course sc left join study_quizinfo sqi on sqi.id = sc.sqiid "
					+ " left join elclass el on el.id=sc.classid where  sc.courseid= ? and sc.userid= ? and sqi.ispassed =1";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElClass elclass = new ElClass(rs.getInt(1), rs.getString(2));
				classList.add(elclass);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classList;
	}

	public int getClassByUseridSize(int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int size = 0;
		try {
			String sql = "select count(cl.id) from elclass cl,elclasstype clt,study_class sc  where cl.cltype = clt.id and sc.classid = cl.id and cl.classtype = 2 and sc.userid = ? and cl.status = 5";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public void addClassCourse_AT(int classid, int courseid, int userid,
			int status, Timestamp starttime, Timestamp finishtime, int setcredit)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			int suggestcredit = 0;
			ps = ct.prepareStatement("select credit from course where id= ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				suggestcredit = rs.getInt(1);
			}

			String sql = "insert into class_course_at(classid,courseid,userid,status,suggestcredit,starttime,finishtime,getcredit,setcredit) values(?,?,?,?,?,?,?,2,?)";// 默认考过
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, userid);
			ps.setInt(4, status);
			ps.setInt(5, suggestcredit);
			ps.setTimestamp(6, starttime);
			ps.setTimestamp(7, finishtime);
			ps.setInt(8, setcredit);

			ps.executeUpdate();

			ps = ct.prepareStatement("call assign_class_course2 (?,?,?,?,?)");
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			ps.setInt(3, status);
			ps.setTimestamp(4, starttime);
			ps.setTimestamp(5, finishtime);
			ps.executeUpdate();

		} catch (Exception e) {
			ElLogger
					.syslogger(
							getSessionValue(ElConstants.SESSION_USERID),
							ElLoggerConstants.LOG_MOD_CLASS,
							ElLoggerConstants.LOG_TYPE_GET,
							"获取培训班失败!失败方法：addClassCourse_AT(int classid, int courseid, int status,Timestamp starttime,Timestamp finishtime) 失败原因："
									+ new ElException(e));
			logger.error("添加培训班课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取某部门的某个培训班的通过率
	 * 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public double getElclassDepPassing(Department department, ElClass elclass)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		double passing = 0;
		try {

			String sql = "select passing  from elclass_dep_passing where classid = ? and depid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elclass.getId());
			ps.setInt(2, department.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				passing = rs.getDouble(1);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return passing;
	}

	public List<ELUser> getClassUser(int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> userlist = new ArrayList<ELUser>();
		try {
			String sql = "select userid from study_class where classid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser u = new ELUser(rs.getInt(1));
				userlist.add(u);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userlist;
	}

	public List<ELUser> getPoints_RecordUsers(int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> userlist = new ArrayList<ELUser>();
		try {
			String sql = "select userid from POINTS_RECORD where classid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser u = new ELUser(rs.getInt(1));
				userlist.add(u);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userlist;
	}

	public List<ELUser> getElclassRecordRankinglist(int depid, int classid,
			String starttime, String endtime, ELUser elUser, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();
		String sql = "";
		String wsql = "";
		try {
			ct = DBConnection.getConnection();
			StringBuffer deptsql = new StringBuffer();
			deptsql.append("select * from DEPARTMENT where id=?");
			ps = ct.prepareStatement(deptsql.toString());
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			Department dept = new Department();
			if (rs.next()) {
				dept.setId(rs.getInt(1));
				dept.setName(rs.getString(2));
				ElNode node = new ElNode(rs.getInt(4));
				dept.setParent(node);
				dept.setLid(rs.getInt("lid"));
				dept.setRid(rs.getInt("rid"));
				deptList.add(dept);
			}
			rs.close();
			ps.close();
			if (elUser != null) {
				if (elUser.getSex() != null && !"".equals(elUser.getSex()))
					wsql = wsql + " and u.sex ='" + elUser.getSex() + "'";
				if (elUser.getUsername() != null
						&& !"".equals(elUser.getRealname()))
					wsql = wsql + " and u.realname like '%"
							+ elUser.getRealname() + "%'";
				if (elUser.getUsername() != null
						&& !"".equals(elUser.getUsername()))
					wsql = wsql + " and u.username like '%"
							+ elUser.getUsername() + "%'";
				// if (elUser.getJingzhong() != null &&
				// !"".equals(elUser.getJingzhong()))
				// wsql = wsql +" and u.jingzhong like '%"+
				// elUser.getJingzhong() + "%'";
				if (elUser.getPeixunleibie() != null
						&& !"".equals(elUser.getPeixunleibie()))
					wsql = wsql + " and u.peixunleibie like '%"
							+ elUser.getPeixunleibie() + "%'";
				if (elUser.getIsAssign() != null
						&& !"".equals(elUser.getIsAssign())) {
					if ("0".equals(elUser.getIsAssign())) {
						wsql = wsql + " and  sc.certificateno is null";
					} else {
						wsql = wsql + " and  sc.certificateno is not null ";
					}
				}
			}
			sql = "select * from (select t.*, rownum rn from ("
					+ "select pr.userid,u.username,u.realname,u.sex,dep.id depid,dep.name depname,elt.id,elt.name,pr.cscore,pr.fscore,pr.addscore "
					+ "from POINTS_RECORD pr,elclass el , eluser u ,department dep,elclasstype elt,study_class sc "
					+ "where pr.classid = el.id and pr.userid= u.id and u.depid = dep.id and el.cltype = elt.id and sc.userid = pr.userid and sc.classid = pr.classid"
					+ " and dep.lid>=? and dep.rid<=? and pr.classid = ? "
					+ wsql
					+ "   order by pr.totalscore desc )t where rownum<=? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, dept.getLid());
			ps.setInt(2, dept.getRid());
			ps.setInt(3, classid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser2 = new ELUser();
				elUser2.setId(rs.getInt(1));
				elUser2.setUsername(rs.getString(2));
				elUser2.setRealname(rs.getString(3));
				elUser2.setSex(rs.getString(4));
				elUser2.setDepartment(new Department(rs.getInt(5), rs
						.getString(6)));
				elUser2.getDepartment().setUnit(
						new UnitRanking(getUnitByUserDepid(rs.getInt(5))));
				ElClass elclass = new ElClass(classid);
				elclass.setCltype(new ElClType(rs.getInt(7), rs.getString(8)));
				elUser2.setElclass(elclass);
				PointsRecord precord = new PointsRecord();
				precord.setElclass(elclass);
				precord.setCscore(rs.getFloat(9));
				precord.setFscore(rs.getFloat(10));
				precord.setAddscore(rs.getFloat(11));
				precord.setTotalscore(precord.getCscore() + precord.getFscore()
						+ precord.getAddscore());
				elUser2.setPrecord(precord);
				userList.add(elUser2);
			}
		} catch (Exception e) {
			logger.error("获取学员积分记录失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	/**
	 * 根据用户查询用所在单位
	 * 
	 * @param depid
	 * @return
	 * @throws ElException
	 */
	public Department getUnitByUserDepid(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		Department depar = new Department();
		String sql = "";
		try {
			if (depid != 1) {
				depar = getParentidByUserDepid(depid);
				for (int i = 0; i < 10; i++) {// 最多循环10次
					if (depar.getParent().getId() != 1) {
						depar = getParentidByUserDepid(depar.getParent()
								.getId());
					} else {
						break;// 父节点 == 1 跳出循环
					}
				}
			} else {
				depar = new Department(depid);
			}
			ct = DBConnection.getConnection();
			sql = "select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email from DEPARTMENT d where d.id =?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, depar.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setManager(new ELUser(rs.getInt(5)));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setAddress(rs.getString(6));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public Department getParentidByUserDepid(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email from DEPARTMENT d where d.id =?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setManager(new ELUser(rs.getInt(5)));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setAddress(rs.getString(6));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public int getElclassRecordRankingSize(int depid, int classid,
			String starttime, String endtime, ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		int Size = 0;
		String sql = "";
		String wsql = "";
		try {
			ct = DBConnection.getConnection();
			StringBuffer deptsql = new StringBuffer();
			deptsql.append("select * from DEPARTMENT where id=?");
			ps = ct.prepareStatement(deptsql.toString());
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			Department dept = new Department();
			if (rs.next()) {
				dept.setId(rs.getInt(1));
				dept.setName(rs.getString(2));
				ElNode node = new ElNode(rs.getInt(4));
				dept.setParent(node);
				dept.setLid(rs.getInt("lid"));
				dept.setRid(rs.getInt("rid"));
				deptList.add(dept);
			}
			rs.close();
			ps.close();
			if (elUser != null) {
				if (elUser.getSex() != null && !"".equals(elUser.getSex()))
					wsql = wsql + " and u.sex ='" + elUser.getSex() + "'";
				if (elUser.getUsername() != null
						&& !"".equals(elUser.getRealname()))
					wsql = wsql + " and u.realname like '%"
							+ elUser.getRealname() + "%'";
				if (elUser.getUsername() != null
						&& !"".equals(elUser.getUsername()))
					wsql = wsql + " and u.username like '%"
							+ elUser.getUsername() + "%'";
				// if (elUser.getJingzhong() != null &&
				// !"".equals(elUser.getJingzhong()))
				// wsql = wsql +" and u.jingzhong like '%"+
				// elUser.getJingzhong() + "%'";
				if (elUser.getPeixunleibie() != null
						&& !"".equals(elUser.getPeixunleibie()))
					wsql = wsql + " and u.peixunleibie like '%"
							+ elUser.getPeixunleibie() + "%'";
				if (elUser.getIsAssign() != null
						&& !"".equals(elUser.getIsAssign())) {
					if ("0".equals(elUser.getIsAssign())) {
						wsql = wsql + " and  sc.certificateno is null";
					} else {
						wsql = wsql + " and  sc.certificateno is not null ";
					}
				}
			}
			sql = ""
					+ "select count(pr.userid) "
					+ "from POINTS_RECORD pr,elclass el , eluser u ,department dep,elclasstype elt,study_class sc "
					+ "where pr.classid = el.id and pr.userid= u.id and u.depid = dep.id and el.cltype = elt.id and sc.userid = pr.userid and sc.classid = pr.classid"
					+ " and dep.lid>=? and dep.rid<=? and pr.classid = ? "
					+ wsql + " ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, dept.getLid());
			ps.setInt(2, dept.getRid());
			ps.setInt(3, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				Size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取学员积分记录Size失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return Size;
	}

	/**
	 * （培训班概况比较用到） 获取某部门下某培训班总人数
	 * 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_CountNumberOfPeople(Department department,
			ElClass elclass) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int CountNumber = 0;
		try {

			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();

			String sql = "select count(*)  from study_class ca,department de,eluser el "
					+ "where ca.userid = el.id and el.depid = de.id and ca.classid=? and de.lid >= ? and de.rid <= ? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elclass.getId());
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
	 * （培训班概况比较用到） 获取某部门下某培训班通过人数
	 * 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_Pass_CountNumberOfPeople(Department department,
			ElClass elclass) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int CountNumber = 0;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();

			String sql = "select count(*)  from study_class ca,department de,eluser el "
					+ "where ca.userid = el.id and el.depid = de.id and ca.classid=?   "
					+ "and de.lid >= ? and de.rid <= ? and ca.certificateno is not null";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elclass.getId());
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
	 * （培训班概况比较用到） 获取某部门下某培训班总高级职称人数
	 * 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_CountNumberOfGaojiPeople(Department department,
			ElClass elclass) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int CountNumber = 0;
		try {

			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();

			String sql = "select count(*)  from study_class ca,department de,eluser el "
					+ "where ca.userid = el.id and el.depid = de.id and ca.classid=? and de.lid >= ? and de.rid <= ? and (el.zhuanyezigejibie  like '%高级%' "
					+ " or  el.peixunleibie ='高级职称') ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elclass.getId());
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
	 * （培训班概况比较用到） 获取某部门下某培训班通过的高级职称人数
	 * 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public int getClassEval_Pass_CountNumberOfGaojiPeople(
			Department department, ElClass elclass) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int CountNumber = 0;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();

			String sql = "select count(*)  from study_class ca,department de,eluser el "
					+ "where ca.userid = el.id and el.depid = de.id and ca.classid=?   "
					+ "and de.lid >= ? and de.rid <= ? and ca.certificateno is not null and (el.zhuanyezigejibie  like '%高级%' "
					+ " or  el.peixunleibie ='高级职称')";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elclass.getId());
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
	 * 验证某部门的某个培训班的通过率是否存在
	 * 
	 * @param department
	 * @param elclass
	 * @return
	 * @throws ElException
	 */
	public boolean CheckElclassDepPassing(Department department, ElClass elclass)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean isPassing = false;
		try {

			String sql = "select *  from elclass_dep_passing where classid = ? and depid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elclass.getId());
			ps.setInt(2, department.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				isPassing = true;
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return isPassing;
	}

	/**
	 * 增加某部门的某个培训班的通过率
	 * 
	 * @param depid
	 * @param elclassid
	 * @param passing
	 * @throws ElException
	 */
	public void addElclassDepPassing(int depid, int elclassid, double passing)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into elclass_dep_passing (classid,depid,passing) values (? ,? ,? )");
			ps.setInt(1, elclassid);
			ps.setInt(2, depid);
			ps.setDouble(3, passing);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 修改某部门的某个培训班的通过率
	 * 
	 * @param depid
	 * @param elclassid
	 * @param passing
	 * @throws ElException
	 */
	public void alterElclassDepPassing(int depid, int elclassid, double passing)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update elclass_dep_passing set passing = ? where depid = ? and classid=?");
			ps.setDouble(1, passing);
			ps.setInt(2, depid);
			ps.setInt(3, elclassid);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/*
	 * 培训班概况用设置通过率进行排序
	 */
	public List<Department> getDepSortByRatioPassing_(List<Department> deps) {
		Department[] depArray = new Department[deps.size()];
		// 先赋值
		for (int i = 0; i < depArray.length; i++) {
			depArray[i] = deps.get(i);
		}

		for (int i = 0; i < depArray.length - 1; i++) {
			for (int j = 0; j < depArray.length - 1 - i; j++) {
				if (depArray[j].getRatioPassing_() < depArray[j + 1]
						.getRatioPassing_()) {
					Department temp = depArray[j];
					depArray[j] = depArray[j + 1];
					depArray[j + 1] = temp;
				}
			}
		}

		// 再回值
		for (int i = 0; i < deps.size(); i++) {
			deps.set(i, depArray[i]);
		}
		return deps;
	}

	public int getUseridByCertificateNo(int no, int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int userid = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id "
							+ "from study_class sc,elclass cl,eluser eu "
							+ "where sc.classid = cl.id and sc.certificateno is not null and sc.userid = eu.id and sc.certificateno = ? and sc.classid=? ");
			ps.setInt(1, no);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if (rs.next()) {
				userid = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userid;
	}

	public boolean checkOrderidIsExist(Course course, ElClass elclass)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select count(1) from class_course where courseid=? and classid=? and orderid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, course.getId());
			ps.setInt(2, elclass.getId());
			ps.setInt(3, course.getOrderid());
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					flag = true;
				}
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public boolean checkcoursecanlearn(int courseid, int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct
					.prepareCall("{call checkcoursecanlearn(?,?,?,?)}");
			cs.setInt(1, courseid);
			cs.setInt(2, classid);
			cs.setInt(3, userid);
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			flag = cs.getBoolean(4);
		} catch (Exception e) {
			System.out.println(courseid+","+classid+","+userid+"报错了");
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public boolean checkStudyCourseIsPassed(int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select passed from study_course where userid=? and courseid=?  ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 1) {
					flag = true;
				}
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public List<Course> listStudyCourses(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			String sql = " select c.id,c.name,er.id as erid from course c,exam_room er, study_course sc "
					+ " where c.id=sc.courseid and c.id=er.courseid and er.classid=? and sc.userid=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				ExamRoom eroom = new ExamRoom(rs.getInt(3));
				c.setEroom(eroom);
				cs.add(c);
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"我的培训班课程列表失败!失败方法：listClassCourses(int classid, int status) 失败原因："
							+ new ElException(e));
			logger.error("我的培训班课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}

	public void updateClassProcessByClassid(int classid, float process,
			int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " update study_class set  process=?,initcompliance=1 where userid=? and classid=? ";
			ps = ct.prepareStatement(sql);
			ps.setFloat(1, process);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkClassCanLearn(int classid, int sortid, int userid,
			int batchid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String sql = "";
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			sql = " select count(1),sc.classid,sc.initcompliance  from study_class sc left join BATCH_ELCLASS_FENPEI bef on sc.classid=bef.elclassid where sc.userid=? and bef.batchid=? and bef.sortid=? and sc.process=100.00 group by sc.classid,sc.initcompliance  ";
			ps = ct.prepareStatement(sql);
			ps.setFloat(1, userid);
			ps.setInt(2, batchid);
			ps.setInt(3, sortid);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					if(rs.getInt(3)==1){
						//是否初始定级通过
						flag = true;
					}else{
						// 判断培训班结业考场是否通过
						if (this.checkClassExamIsPass(rs.getInt(2), userid))
							flag = true;
					}
				}
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public boolean checkClassExamIsPass(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct
					.prepareCall("{call checkClassExamIsPass(?,?,?)}");
			cs.setInt(1, classid);
			cs.setInt(2, userid);
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			flag = cs.getBoolean(3);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public void updateClassProcess(int batchid, int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("{call set_classprocess(?,?)}");
			ps.setFloat(1, batchid);
			ps.setInt(2, userid);
			ps.executeUpdate();
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public String getUsersByUserids(int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String sql = "";
		String userids = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select userid from study_class where classid=?";
			ps = ct.prepareStatement(sql);
			ps.setFloat(1, classid);
			rs = ps.executeQuery();
			while(rs.next()){
				userids += rs.getInt(1) + ",";
			}
			if(userids!=null && !userids.equals("")){
				userids = userids.substring(0,userids.lastIndexOf(","));
			}
		} catch (Exception e) {
			logger.error("获取等级考试用户出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return  userids;
	}

	public int checkClassCanExam(int userid,int classid,int roomid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int flag = 0;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct
					.prepareCall("{call checkClassExamRoomCanExam(?,?,?,?)}");
			cs.setInt(1, userid);
			cs.setInt(2, classid);
			cs.setInt(3, roomid);
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			flag = cs.getInt(4);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public void quitAssignRoom(int classid, int roomid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "delete from elclass_assign_examroom where classid=? and examroomid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("取消培训班绑定考场出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	/**
	 * 培训班对应的考场roomid
	
	* @Title: elclassRoom  
	
	* @Description: TODO 
	
	* @param @param classid
	* @param @param userid
	* @param @return
	* @param @throws ElException      
	
	* @return ExamRoom     
	
	* @throws
	 */
	public ExamRoom elclassRoom(int classid)
		throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
//		List<Course> cs = new ArrayList<Course>();
		ExamRoom eroom = null;
		try {
			String sql = "select examroomid from elclass_assign_examroom where classid=? ";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				eroom = new ExamRoom(rs.getInt(1));
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_CLASS,
					ElLoggerConstants.LOG_TYPE_GET,
					"我的培训班课程列表失败!失败方法：listClassCourses(int classid, int status) 失败原因："
							+ new ElException(e));
			logger.error("我的培训班课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eroom;
		}

	public int getFinishCountInformation(int classid, int type)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(type==1){
				sqlwhere += " and process=100";
			}else if(type == 2){
				sqlwhere += " and process=0";
			}else if(type == 3){
				sqlwhere += " and process>0 and process<100 ";
			}
			sql = " select count(1) from study_class where  classid=? " + sqlwhere ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs= ps.executeQuery();
			if(rs.next()){
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("等级完成情况统计分析失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	public List<ELUser> getStudentInfoByClassid(int classid,int pageNow,int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> elusers = new ArrayList<ELUser>();
		MyClass myclass = null;
		ELUser elUser = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			if(pageNow == -1 && pageSize == -1){
				sql = " select" +
				" eu.id,eu.username,eu.realname,dep.id depid,dep.name, sc.process" +
				" from eluser eu " +
				" left join study_class sc on eu.id=sc.userid" +
				" left join department dep on eu.depid=dep.id" +
				"  where sc.classid=? order by sc.process desc " ;
				ps = ct.prepareStatement(sql);
				ps.setInt(1, classid);
			}else{
				sql = " select b.* from (" +
				" select a.*,rownum rn from " +
				" (select" +
				" eu.id,eu.username,eu.realname,dep.id depid,dep.name, sc.process" +
				" from eluser eu " +
				" left join study_class sc on eu.id=sc.userid" +
				" left join department dep on eu.depid=dep.id" +
				"  where sc.classid=? order by sc.process desc ) a where rownum<=? ) b where rn>=?" ;
				ps = ct.prepareStatement(sql);
				ps.setInt(1, classid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			
			rs= ps.executeQuery();
			while(rs.next()){
				elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setDepartment(new Department(rs.getInt(4),rs.getString(5)));
				myclass = new MyClass();
				myclass.setProcess(rs.getDouble(6));
				elUser.setMyClass(myclass);
				elusers.add(elUser);
			}
		} catch (Exception e) {
			logger.error("等级完成情况统计分析失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elusers;
	}
	

	public int getStudentInfoSizeByClassid(int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select" +
					" count(1)" +
					" from eluser eu " +
					" left join study_class sc on eu.id=sc.userid" +
					" left join department dep on eu.depid=dep.id" +
					"  where sc.classid=? order by id desc " ;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs= ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("等级完成情况统计分析失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
//-------------------sd1230===========xiugai
	/**
	 * 培训班统计查询学员（学分排序）（不分页、用于导出）
	 * 
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent_sd(int classid, ElNode tree, ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		List<Object> params = new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql
					.append(" select  eu.id euid,eu.username, eu.realname, dep.id depid,dep.name depname,eu.valid ,eu.sex,bdt.name,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
							+ " ,scl.status,scl.applydate,scl.certificateno , sr.begintime,sr.myscore ,bd.basevalue from study_class scl left join eluser eu on scl.userid =eu.id " 
							+"  left join study_room sr  on  scl.userid=sr.userid  "
							+"  left join basedatatype bdt  on  eu.gangwei=bdt.id  "
							+"  left join basedatat bd  on  eu.jingzhong=bd.id  "
							+ "inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("department", tree, true)
							+ ") dep on dep.id = eu.depid where scl.classid = ? and sr.classid=? ");
			this.checkUserParam_sd(usersql, elUser, params,tree);
			usersql
					.append(" order by scl.applydate desc ");
			ps = ct.prepareStatement(usersql.toString());
			logger.info(usersql.toString());
			
			ps.setInt(1, classid);
			ps.setInt(2, classid);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 3, params.get(i));
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
				elUser2.setWorkTypeName(rs.getString(8));
				elUser2.setPersonTypeName(rs.getString(16));
				elUser2.setShengri(rs.getDate(9));
				elUser2.setAge(rs.getInt(10));
				// 先查看学员培训班是否通过再查证书编号
				StudyClassDao scd = ((StudyClassDao) SpringContextUtil
						.getBean("studyClassDao"));
				scd.setMyPassclass(elUser2.getId(), classid);
				// cl1.setCertificateno(rs.getInt(7));
				// if(rs.getInt("certificateno")>0){
				// elUser2.setGraddate(rs.getDate(12));
				// }
				if (scd.getStudyClassCertificateno(classid, elUser2.getId()) > 0) {
					elUser2.setGraddate(rs.getDate(12));
				}
				// 必修分
				elUser2.setCt_credit(classStudentScore2(classid, elUser2
						.getId(), 0));
				// 选修分
				elUser2.setXx_credit(classStudentScore2(classid, elUser2
						.getId(), 1));
				// 总分
				elUser2.setXx_time(elUser2.getCt_credit()
						+ elUser2.getXx_credit());
				// elUser2.setIsAssign("未分配");??
				
				//sd1230
				elUser2.setBegintime(rs.getDate("begintime"));
				elUser2.setMyscore(rs.getInt("myscore"));
//				elUser2.setZhiji(rs.getInt("zhiji"));
//				elUser2.setZhiwu(rs.getInt("zhiwu"));
				userList.add(elUser2);
			}
		} catch (Exception e) {
			logger.error("培训班统计查询学员（学分排序）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// 排序（按学分）
		userList = this.sortUserByScore(userList);
		return userList;
	}

	/**
	 * 培训班统计查询学员（学分排序）
	 * 
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> classStudent_sd(int classid, ElNode tree, ELUser elUser,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> userList = new ArrayList<ELUser>();
		List<Object> params = new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql
					.append("select * from (select t.*, rownum rn from ( select  eu.id euid,eu.username, eu.realname, dep.id depid,dep.name depname,eu.valid ,eu.sex,bdt.name,eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ "
							+ " ,scl.status,scl.applydate,scl.certificateno , sr.begintime,sr.myscore,bd.basevalue  from study_class scl left join eluser eu on scl.userid =eu.id " 
							+"  left join study_room sr  on  scl.userid=sr.userid  "
							+"  left join basedatatype bdt  on  eu.gangwei=bdt.id  "
							+"  left join basedatat bd  on  eu.jingzhong=bd.id  "
							+ "inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("department", tree, true)
							+ ") dep on dep.id = eu.depid where scl.classid = ? and sr.classid=? ");
			this.checkUserParam_sd(usersql, elUser, params,tree);
			usersql
					.append(" order by scl.applydate desc )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			logger.info(usersql.toString());
			
			ps.setInt(1, classid);
			ps.setInt(2, classid);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 3, params.get(i));
			}
			ps.setInt(params.size() + 3, pageNow);
			ps.setInt(params.size() + 4, pageSize);
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
				elUser2.setWorkTypeName(rs.getString(8));
				elUser2.setPersonTypeName(rs.getString(16));
				elUser2.setShengri(rs.getDate(9));
				elUser2.setAge(rs.getInt(10));
				// 先查看学员培训班是否通过再查证书编号
				StudyClassDao scd = ((StudyClassDao) SpringContextUtil
						.getBean("studyClassDao"));
				scd.setMyPassclass(elUser2.getId(), classid);
				// cl1.setCertificateno(rs.getInt(7));
				// if(rs.getInt("certificateno")>0){
				// elUser2.setGraddate(rs.getDate(12));
				// }
				if (scd.getStudyClassCertificateno(classid, elUser2.getId()) > 0) {
					elUser2.setGraddate(rs.getDate(12));
				}
				// 必修分
				elUser2.setCt_credit(classStudentScore2(classid, elUser2
						.getId(), 0));
				// 选修分
				elUser2.setXx_credit(classStudentScore2(classid, elUser2
						.getId(), 1));
				// 总分
				elUser2.setXx_time(elUser2.getCt_credit()
						+ elUser2.getXx_credit());
				// elUser2.setIsAssign("未分配");??
				
				//sd1230
				elUser2.setBegintime(rs.getDate("begintime"));
				elUser2.setMyscore(rs.getInt("myscore"));
//				elUser2.setZhiji(rs.getInt("zhiji"));
//				elUser2.setZhiwu(rs.getInt("zhiwu"));
				userList.add(elUser2);
			}
		} catch (Exception e) {
			logger.error("培训班统计查询学员（学分排序）出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// 排序（按学分）
		userList = this.sortUserByScore(userList);
		return userList;
	}

	/**
	 * 培训班统计查询学员数量
	 * 
	 * @param classid
	 * @param tree
	 * @param elUser
	 * @return
	 * @throws ElException
	 */
	public int classStudentSize_sd(int classid, ElNode tree, ELUser elUser)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Object> params = new ArrayList<Object>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer usersql = new StringBuffer();
			usersql
					.append("select count(scl.userid) from study_class scl left join eluser eu on scl.userid =eu.id "
							+"  left join study_room sr  on  scl.userid=sr.userid  "
							+ "inner join ("
							+ ((ElNodeSQL) SpringContextUtil
									.getBean(ElConstants.CLASS_ELNODESQL))
									.generateSQLByTree("department", tree, true)
							+ ") dep on dep.id = eu.depid where scl.classid = ?  and sr.classid=?");
			this.checkUserParam_sd(usersql, elUser, params,tree);
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, classid);
			ps.setInt(2, classid);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 3, params.get(i));
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
	
	/**
	 * 检测用户查询的参数
	 */
	public void checkUserParam_sd(StringBuffer sql, ELUser elUser,
			List<Object> params,ElNode tree) {
		if (elUser != null) {
			logger.info(elUser.getFlag());
			if (elUser.getSex() != null && !elUser.getSex().equals("")) {
				sql.append(" and eu.sex =?");
				params.add(elUser.getSex());
			}
			if (elUser.getRealname() != null
					&& !elUser.getRealname().equals("")) {
				sql.append(" and eu.realname like ?");
				params.add("%" + StringUtil.toLikeStr(elUser.getRealname())
						+ "%");
			}
			if (elUser.getUsername() != null
					&& !elUser.getUsername().equals("")) {
				sql.append(" and eu.username like ?");
				params.add("%" + StringUtil.toLikeStr(elUser.getUsername())
						+ "%");
			}
//			StringBuffer ids= new StringBuffer();
//			String idss="";
//			if(elUser.getJingzhongIds()!=null){
//			for(int i=0;i<elUser.getJingzhongIds().length;i++){
//				
//				if(elUser.getJingzhongIds()[i]!=0){
//					idss=ids.append(elUser.getJingzhongIds()[i]+",").toString();
//					if(idss.lastIndexOf(",")==idss.length()-1){
//						idss=idss.substring(0, idss.length()-1);
//					}
//				}
//			}
//			if (elUser.getJingzhongIds()!=null&&idss!=null&&idss!="") {
//				sql.append(" and eu.jingzhong  in   ("+idss+") ");
//			}
//			}
			// if (null != elUser.getDishi() && !elUser.getDishi().equals("")&&
			// !elUser.getDishi().equals("0")){
			// sql.append(" and eu.dishi = '"+elUser.getDishi().trim()+"' ");
			// }
			// if (null != elUser.getZhiji() && !elUser.getZhiji().equals("")&&
			// !elUser.getZhiji().equals("0")){
			// sql.append(" and eu.zhiji = '"+elUser.getZhiji().trim()+"' ");
			// }
			// if (null != elUser.getZhiwu() && !elUser.getZhiwu().equals("")&&
			// !elUser.getZhiwu().equals("0")){
			// sql.append(" and eu.zhiwu = '"+elUser.getZhiwu().trim()+"' ");
			// }
			// if (null != elUser.getGangwei() &&
			// !elUser.getGangwei().equals("")&&
			// !elUser.getGangwei().equals("0")){
			// sql.append(" and eu.gangwei = '"+elUser.getGangwei().trim()+"'
			// ");
			// }
			if (elUser.getShengri() != null) {
				sql.append(" and eu.shengri >= ?");
				params.add(elUser.getShengri());
			}
			if (elUser.getShengri_end() != null) {
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
			
			logger.info(tree.getId());
//			if(tree!=null&&tree.getId()!=1&&elUser.getFlag()==1){
//				sql.append(" and eu.depid=?");
//				params.add(tree.getId());
//			}
//		//sd1230
//			if (elUser.getZhiji() > 0&&!getBasevalue(elUser.getZhiji()).equals("除外")) {
//				sql.append(" and eu.zhiji = ?");
//				params.add(elUser.getZhiji());
//			}
//			
//			if (elUser.getZhiwu()> 0&&!getBasevalue(elUser.getZhiwu()).equals("除外")) {
//				sql.append(" and eu.zhiwu = ?");
//				params.add(elUser.getZhiwu());
//			}
			
			if (elUser.getBegintime() != null) {
				sql.append(" and sr.begintime >= ?");
				params.add(elUser.getBegintime());
			}
			if (elUser.getBegintime_end() != null) {
				sql.append(" and sr.begintime <= ?");
				params.add(elUser.getBegintime_end());
			}
			logger.info(elUser.getGangwei());
			if (elUser.getGangwei() != null&&!"0".equals(elUser.getGangwei())) {
				sql.append(" and eu.gangwei= ?");
				logger.info(elUser.getGangwei());
				params.add(elUser.getGangwei());
			}
			
			if (elUser.getJingzhong() != 0) {
				sql.append(" and eu.jingzhong= ?");
				params.add(elUser.getJingzhong());
			}
			
			
//			if ("224".equals(elUser.getJingzhong()+"")) {
//				sql.append(" and  eu.jingzhong is null");
//			} else {
//				sql.append(" and  eu.jingzhong  is not null ");
//			}
//			
//			if ("225".equals(elUser.getZhiwu()+"")) {
//				sql.append(" and  eu.zhiwu is null");
//			} else {
//				sql.append(" and  eu.zhiwu  is not null ");
//			}
//			
//			if ("226".equals(elUser.getZhiji()+"")) {
//				sql.append(" and  eu.zhiji is null");
//			} else {
//				sql.append(" and  eu.zhiji  is not null ");
//			}
			
//			if (getBasevalue(elUser.getJingzhong()).equals("除外")) {
//				sql.append(" and  eu.jingzhong is null");
//			} 
////			else {
////				sql.append(" and  eu.jingzhong  is not null ");
////			}
//			
//			if (getBasevalue(elUser.getZhiwu()).equals("除外")) {
//				sql.append(" and  eu.zhiwu is null");
//			} 
////			else {
////				sql.append(" and  eu.zhiwu  is not null ");
////			}
//			
//			
//			if (getBasevalue(elUser.getZhiji()).equals("除外")) {
//				sql.append(" and  eu.zhiji is null");
//			} 
//			else {
//				sql.append(" and  eu.zhiji  is not null ");
//			}
		
		}
	}
	
	public String getBasevalue(int key){ 
		try {
			BaseDatat base = ((UserDao)SpringContextUtil.getBean("userDao")).getBaseDatatById(key); 
			if(base != null){ 
				return base.getBasevalue();
			} 
		} catch (ElException e) {
			// TODO Auto-generated catch block
			logger.error("获取基础数据错误",e);
		}
		return key+"";
	}

	public void elclassHotSet(int classid, int hot) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" update elclass set hot= ? where id = ?");
			ps.setInt(1, hot);
			ps.setInt(2, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 判断培训班是否可以考试20140703
	 * @param userid
	 * @param classid
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int checkClassCanExam_new(int userid,int classid,int classid2,int roomid) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int flag = 0;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct
					.prepareCall("{call checkClassExamRoomCanExam_new(?,?,?,?,?)}");
			cs.setInt(1, userid);
			cs.setInt(2, classid);
			cs.setInt(3, classid2);
			cs.setInt(4, roomid);
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			flag = cs.getInt(5);
			logger.info(flag);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	
	
}
