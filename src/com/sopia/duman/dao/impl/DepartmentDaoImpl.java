package com.sopia.duman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.taskdefs.rmic.WLRmic;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConf;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

public class DepartmentDaoImpl extends ElNodeDao implements DepartmentDao {
	private static final Log logger = LogFactory
			.getLog(DepartmentDaoImpl.class);

	public int getDepId(String name, int pid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from department where name= ? and parentid = ?");
			ps.setString(1, name);
			ps.setInt(2, pid);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);

		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return -1;
	}

	public int getDepByName(String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from department where name= ?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}

	public Department getDepTree(int did, int stopid, boolean containStop)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = null;
		try {
			if (did == 0) {
				dep = getDepRootByCid();
			} else {
				dep = getDepById(did);
			}
			ct = DBConnection.getConnection();
			dep.setChild(listDepartmentsById(dep.getId(), stopid, containStop,
					0, ct));
		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public Department getDepTree_level1(int pid, int stopid, boolean containStop)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = null;
		try {
//			if (pid == 0) {
				dep = getDepRootByCid();
//			} else {
//				dep = getDepById(pid);
//			}
			ct = DBConnection.getConnection();
			dep.setChild(listdepChildsByPId(dep.getId()));
		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public Department getDepTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
//		Department dep = type.equals("op") ? new Department(-2, "可操作的部门")
//				: new Department(-2, "可使用的部门");
		
		System.out.print("userid:"+userid+"     type--------------:"+type+"		stopid:"+stopid+"\t containStop"+containStop);
		
		//Department dep = new Department(ElConstants.USER_OP_LIB, "aaa可操作的部门");
		
		Department dep = null;		
		if (type.equals("op")) {
			dep = new Department(ElConstants.USER_OP_LIB, "可操作的部门");
		} else {//type.equals("use")
			dep = new Department(ElConstants.USER_OP_LIB, "可使用的部门");
		};

		
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from department_" + type
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<Department> list = new ArrayList<Department>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					Department depc = getDepById(rs.getInt(1));// getDepTree(rs.getInt(1),
																// stopid,
																// false, 1);
					if(depc.getId()==0){
						continue;
					}
					depc.setParent(dep);
//					ElNode nn = (ElNode) depc ;
					nlist .add(depc);
					list.add(depc);
				}
			}
			dep.setNchild(nlist);
			dep.setChild(list);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public Department getDepTree_level1_(int knowledgemanageid, String type, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
//		Department dep = type.equals("op") ? new Department(-2, "可操作的部门")
//				: new Department(-2, "可使用的部门");
		
		System.out.print("knowledgemanageid:"+knowledgemanageid+"     type--------------:"+type+"		stopid:"+stopid+"\t containStop"+containStop);
		
		//Department dep = new Department(ElConstants.USER_OP_LIB, "aaa可操作的部门");
		
		Department dep = null;		
		if (type.equals("op")) {
			dep = new Department(ElConstants.USER_OP_LIB, "可操作的部门");
		} else {//type.equals("use")
			dep = new Department(ElConstants.USER_OP_LIB, "可使用的部门");
		};

		
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from "+type+"_competence " 
					+ " where knowledgemanageid = ?");
			ps.setInt(1, knowledgemanageid);
			rs = ps.executeQuery();
			List<Department> list = new ArrayList<Department>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					Department depc = getDepById(rs.getInt(1));// getDepTree(rs.getInt(1),
																// stopid,
																// false, 1);
					if(depc.getId()==0){
						continue;
					}
					depc.setParent(dep);
//					ElNode nn = (ElNode) depc ;
					nlist .add(depc);
					list.add(depc);
				}
			}
			dep.setNchild(nlist);
			dep.setChild(list);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public Department getDepTree_level_competence(int kledgeid,String tablename,int stopid,boolean containStop) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
//		Department dep = type.equals("op") ? new Department(-2, "可操作的部门")
//				: new Department(-2, "可使用的部门");
		
		System.out.print("userid:"+kledgeid+"     type--------------:"+tablename+"		stopid:"+stopid+"\t containStop"+containStop);
		
		//Department dep = new Department(ElConstants.USER_OP_LIB, "aaa可操作的部门");
		
		Department dep = null;		
//		if (type.equals("op")) {
//			dep = new Department(ElConstants.USER_OP_LIB, "可操作的部门");
//		} else {//type.equals("use")
//			dep = new Department(ElConstants.USER_OP_LIB, "可使用的部门");
//		};
		dep = new Department(ElConstants.USER_OP_LIB, "拥有该权限的部门");

		
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from "+tablename +" " 
					+ " where knowledgemanageid = ? and depid is not null");
			ps.setInt(1, kledgeid);
			rs = ps.executeQuery();
			List<Department> list = new ArrayList<Department>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					Department depc = getDepById(rs.getInt(1));// getDepTree(rs.getInt(1),
																// stopid,
																// false, 1);
					if(depc.getId()==0){
						continue;
					}
					depc.setParent(dep);
//					ElNode nn = (ElNode) depc ;
					nlist .add(depc);
					list.add(depc);
				}
			}
			dep.setNchild(nlist);
			dep.setChild(list);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public List<Department> listdepChildsByPId(int parentid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		int level = 0;
		level++;
		try {
			ct = DBConnection.getConnection();
			// PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_BYPIDANDCID));
			ps = ct
					.prepareStatement("select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email,d.bh,count(c.id),d.lid,d.rid " +
							"from DEPARTMENT d left join department c on c.parentid = d.id and c.status!=1 where d.parentid = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email,d.bh,d.lid,d.rid order by d.bh");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setManager(new ELUser(rs.getInt(5)));
				dep.setAddress(rs.getString(6));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
				dep.setBh(rs.getString(11));
				dep.setClassCount(rs.getInt(12));
				dep.setLid(rs.getInt(13));
				dep.setRid(rs.getInt(14));
				dep.setLevel(level);
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	private Department getDepTree(int did, int stopid, boolean containStop,
			int level) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = null;
		try {
			// if (did == 0) {
			// dep = getDepRootByCid();
			// } else {
			dep = getDepById(did);
			// }
			dep.setLevel(level);
			ct = DBConnection.getConnection();
			dep.setChild(listDepartmentsById(dep.getId(), stopid, containStop,
					level, ct));
		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	/**
	 * 获取练习分配给的部门树
	 * @param userid
	 * @param type
	 * @param stopid
	 * @param containStop
	 * @return
	 * @throws ElException
	 */
	public Department getExampracDepTree(int pracid, int stopid,boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep =  new Department(-2, "已分配的部门");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from examprac_dep where pracid = ?");
			ps.setInt(1, pracid);
			rs = ps.executeQuery();
			List<Department> list = new ArrayList<Department>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					Department depc = getDepTree(rs.getInt(1), stopid,
							containStop, 1);
					depc.setParent(dep);
					list.add(depc);
				}
			}
			dep.setChild(list);
		} catch (Exception e) {
			logger.error("获取练习分配给的部门树出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public Department getDepTree(int userid, String type, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
//		Department dep = type.equals("op") ? new Department(-2, "可操作的部门")
//				: new Department(-2, "可使用的部门");
		Department dep = new Department(ElConstants.USER_OP_LIB, "可操作的部门");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select depid from department_" + type
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<Department> list = new ArrayList<Department>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					Department depc = getDepTree(rs.getInt(1), stopid,
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
		return dep;
	}

	private List<Department> listDepartmentsById(int parentid, int stopid,
			boolean isContainStop, int level, Connection ct) throws Exception {
		List<Department> deps = new ArrayList<Department>();
		// PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
		// .getSQL(DUConstants.DEP_QUERY_BYPIDANDCID));
		PreparedStatement pstemp = ct
				.prepareStatement("select id,name,description,parentid,manager,address,postalcode,phone,fax,email,bh,lid,rid from DEPARTMENT where parentid = ?  and status!=1 order by bh");
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			Department dep = new Department(rstemp.getInt(1), rstemp
					.getString(2));
			dep.setDescription(rstemp.getString(3));
			dep.setParent(new Department(rstemp.getInt(4)));
			dep.setManager(new ELUser(rstemp.getInt(5)));
			dep.setAddress(rstemp.getString(6));
			dep.setPostalcode(rstemp.getString(7));
			dep.setPhone(rstemp.getString(8));
			dep.setFax(rstemp.getString(9));
			dep.setEmail(rstemp.getString(10));
			dep.setLevel(level);
			dep.setBh(rstemp.getString(11));
			dep.setLid(rstemp.getInt(12));
			dep.setRid(rstemp.getInt(13));
			if (dep.getId() != stopid)
				dep.setChild(listDepartmentsById(dep.getId(), stopid,
						isContainStop, level, ct));
			if (!isContainStop && dep.getId() == stopid) {

			} else
				deps.add(dep);
		}
		rstemp.close();
		pstemp.close();
		return deps;
	}

	/**
	 * 根据部门名称模糊获取部门信息
	 * 
	 * @param depName
	 * @return
	 * @throws ElException
	 */
	public List<Department> listDepartmentsByName(String depName, int pageNow,
			int pageSize) throws ElException {
		List<Department> deps = new ArrayList<Department>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from (select t.*, rownum rn from (select dep.id,dep.name,dep.bh from department dep where name like ? )t where rownum <= ? ) where rn>=?");
			ps.setString(1, "%" + depName + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setBh(rs.getString("bh"));
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("根据部门名称模糊获取部门信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	/**
	 * 根据部门名称模糊获取部门信息数量
	 * 
	 * @param depName
	 * @return
	 * @throws ElException
	 */
	public int getDepartmentCount(String depName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(dep.id) from department dep where name like ?");
			ps.setString(1, "%" + depName + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据部门名称模糊获取部门信息数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void addDep(Department department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 本系统由于 有很多的包含下级的的功能，用左右值办法 很好解决代码复杂性问题。简化开发。
			// 先阅读http://www.cnblogs.com/hendy/archive/2009/10/30/1592819.html
			// 文章。我们的系统中树状结构基本是按此文档信息实现
			// 本系统中的树形结构实例。本dao实现类需要继承ElNodeDao，elNodeDao 的方法主要是更新系统左右值的信息。
			// 实体类Department 必须是ElNode 的子类，ElNodeDao 操作的主要是ElNode类
			// 下面 方法主要是更新相关树形结构表中的数据的处理。进入看注释（按住ctrl,点击方法名）。
//			addNode(ct, department, "department", "1=1");
			// 添加节点
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_ADD));
			ps.setString(1, department.getName());
			ps.setString(2, department.getDescription());
			ps.setInt(3, department.getParent().getId());
			ps.setInt(4, department.getManager().getId());
			ps.setString(5, department.getAddress());
			ps.setString(6, department.getPostalcode());
			ps.setString(7, department.getPhone());
			ps.setString(8, department.getFax());
			ps.setString(9, department.getEmail());
			ps.setInt(10, department.getLid());
			ps.setInt(11, department.getRid());
			ps.setString(12, department.getBh());
			ps.setInt(13, department.getIssp());
			ps.setString(14, department.getImage());
			ps.setString(15, department.getTitle());
			ps.setString(16, department.getLuokuanwenzi());
			ps.setString(17, department.getLingyu());
			ps.setString(18, department.getDishi());
			ps.executeUpdate();
			ps.close();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('department') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select department_sequence.currval from dual ");
				rs = ps.executeQuery();
				
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				department.setId(rs.getInt(1));
//			ps = ct.prepareStatement("call updatetlrid('department') ");
//			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("插入部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public static String getBhReturnValue(){
		String returnValue = "";
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH)  + 1);
		String date = String.valueOf(cal.get(Calendar.DATE));
		if(Integer.parseInt(month) <10){
			month = "0" + month;
		}
		if(Integer.parseInt(date) <10){
			date = "0" + date;
		}
		//六位随机数
		String random ="";
		Random r = new Random(); 
		random = String.valueOf(r.nextInt(999999));
		if(Integer.parseInt(random) <10){
			random = "00000" + random;
		}else if(Integer.parseInt(random)<100){
			random = "0000" + random;
		}else if(Integer.parseInt(random)<1000){
			random = "000" + random;
		}else if(Integer.parseInt(random)<10000){
			random = "00" + random;
		}else if(Integer.parseInt(random)<100000){
			random = "0" + random;
		}else if(Integer.parseInt(random)<1000000){
			random = random;
		}
		returnValue = random;
		return year + month + date + returnValue;
	}
	
	public int addDep1(Department department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int returnValue = 0;
		try {
			ct = DBConnection.getConnection();
			// 本系统由于 有很多的包含下级的的功能，用左右值办法 很好解决代码复杂性问题。简化开发。
			// 先阅读http://www.cnblogs.com/hendy/archive/2009/10/30/1592819.html
			// 文章。我们的系统中树状结构基本是按此文档信息实现
			// 本系统中的树形结构实例。本dao实现类需要继承ElNodeDao，elNodeDao 的方法主要是更新系统左右值的信息。
			// 实体类Department 必须是ElNode 的子类，ElNodeDao 操作的主要是ElNode类
			// 下面 方法主要是更新相关树形结构表中的数据的处理。进入看注释（按住ctrl,点击方法名）。
//			addNode(ct, department, "department", "1=1");
			// 添加节点
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_ADD));
			ps.setString(1, department.getName());
			ps.setString(2, department.getDescription());
			ps.setInt(3, department.getParent().getId());
			ps.setInt(4, department.getManager().getId());
			ps.setString(5, department.getAddress());
			ps.setString(6, department.getPostalcode());
			ps.setString(7, department.getPhone());
			ps.setString(8, department.getFax());
			ps.setString(9, department.getEmail());
			ps.setInt(10, department.getLid());
			ps.setInt(11, department.getRid());
			ps.setString(12, getBhReturnValue());
			ps.setInt(13, department.getIssp());
			ps.setString(14, department.getImage());
			ps.setString(15, department.getTitle());
			ps.setString(16, department.getLuokuanwenzi());
			ps.setString(17, department.getLingyu());
			ps.setString(18, department.getDishi());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('department') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select department_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				department.setId(rs.getInt(1));
//			ps = ct.prepareStatement("call updatetlrid('department') ");
//			ps.executeUpdate();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select department_SEQUENCE.currval from dual");
			rs = ps.executeQuery();
			if (rs.next()) {
				returnValue = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("插入部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return returnValue;
	}

	public int updateDep(Department department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,bh from department where id = ?");
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			int id = 0;
			String bh = null;
			if (rs.next()) {
				id = rs.getInt(1);
				bh = rs.getString(2);
			}
			rs.close();
			ps.close();
			if (bh == null || "".equals(bh.trim())) {
				return -1;
			}
			if (bh.length() != 6) {
				return -2;
			}
			ps = ct.prepareStatement("call dep_set(?,? )");
			String bh_ = getBh(bh);
			ps.setString(1, bh_);
			// ps.setString(2, bh);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
//			ps = ct.prepareStatement("call updatetlrid('department')");
//			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("插入部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 1;
	}

	public int updateDepUser(Department department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			int lid = 0, rid = 0;
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps.close();
			ps = ct.prepareStatement("call dep_userset(?,? )");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("插入部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 1;
	}

	private String getBh(String bh) {
		if (null == bh)
			return null;
		int count = 0;
		for (int i = bh.length() - 1; i >= 4; i--) {
			if ("0".equals(bh.charAt(i) + "")) {
				count++;
			} else
				break;
		}
		count = (count % 2) == 0 ? count : count - 1;
		bh = bh.substring(0, bh.length() - count);
		for (int i = 0; i < count; i++) {
			if (i < 2)
				bh = bh + "_";
			else
				// if(count<6)
				// bh = bh+"_";
				// else
				bh = bh + "0";
		}
		return bh;
	}


	public Department getDepById(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(DUConstants.DEP_QUERY_BYID));
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.address,d.postalcode,d.phone,d.fax,d.email, d.bh,d.lid,d.rid,count(c.id),d.issp,d.image,d.title,d.luokuanwenzi,d.lingyu,d.dishi " +
					" from DEPARTMENT d left join department c on c.parentid = d.id and c.status!=1 where d.id = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.address,d.postalcode,d.phone,d.fax,d.email, d.bh,d.lid,d.rid,d.issp,d.image,d.title,d.luokuanwenzi,d.lingyu,d.dishi");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
//				dep.setManager(new ELUser(rs.getInt(5), rs.getString(11)));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setAddress(rs.getString(5));
				dep.setPostalcode(rs.getString(6));
				dep.setPhone(rs.getString(7));
				dep.setFax(rs.getString(8));
				dep.setEmail(rs.getString(9));
				dep.setBh(rs.getString(10));
				dep.setLid(rs.getInt(11));
				dep.setRid(rs.getInt(12));
				dep.setClassCount(rs.getInt(13));
				dep.setIssp(rs.getInt(14));
				dep.setImage(rs.getString(15));
				dep.setTitle(rs.getString(16));
				dep.setLuokuanwenzi(rs.getString(17));
				dep.setLingyu(rs.getString(18));
				dep.setDishi(rs.getString(19));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	/**
	 * 获取部门的左右id
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Department getDepLRid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name,lid,rid from department dep where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setLid(rs.getInt(3));
				dep.setRid(rs.getInt(4));
			}
		} catch (Exception e) {
			logger.error("获取部门的左右id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public String getDepInId(String ids) throws ElException { 
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String values = "";
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select name from department where id in("+ids+") "); 
			rs = ps.executeQuery();
			while (rs.next()) { 
				if(values.equals("") ){
					values = rs.getString("name");
				}else{					
					values = values +","+rs.getString("name"); 
				}
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return values;
	}

	public Department getDepByBH(String bh) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email from DEPARTMENT d where d.bh =?");
			ps.setString(1, bh.trim());
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
			DBConnection.closeConnectInfo(ct, ps, rs);
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	/**
	 * 检测部门编号是否存在
	 * 
	 * @param bh
	 * @return
	 * @throws ElException
	 */
	public boolean checkDepBh(String bh) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select d.bh from DEPARTMENT d where d.bh =?");
			ps.setString(1, bh.trim());
			rs = ps.executeQuery();
			if (rs.next()) {
				// 存在
				return true;
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void alterDep(Department department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 一个部门只有一个管理员 是存储在 department 的manager字段里的 1. 2.
			// 1.----------------------------------------------------------------------
			// 起
			// 修改管理员信息
			// ELUser man = new ELUser();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_BYID));
			// ps.setInt(1, department.getId());
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// man.setId(rs.getInt(5));
			// }
			// rs.close();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_ROLE_SET));//update eluser set role = ?
			// where id = ?
			// ps.setInt(1, DUConstants.USER_ROLE_STU);
			// //DUConstants.USER_ROLE_STU = 4
			// ps.setInt(2, man.getId());
			// ps.executeUpdate();
			// ----------------------------------------------------------------------
			// 终
			// 修改节点信息
//			alterNode(ct, department, "department", "1=1");
			// 修改基本信息
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_ALTER));
			ps.setString(1, department.getName());
			ps.setString(2, department.getDescription());
			ps.setInt(3, department.getParent().getId());
			ps.setInt(4, department.getManager().getId());
			ps.setString(5, department.getAddress());
			ps.setString(6, department.getPostalcode());
			ps.setString(7, department.getPhone());
			ps.setString(8, department.getFax());
			ps.setString(9, department.getEmail());
			ps.setString(10, department.getBh());
			ps.setInt(11, department.getIssp());
			ps.setString(12, department.getImage());
			ps.setString(13, department.getTitle());
			ps.setString(14, department.getLuokuanwenzi());
			ps.setString(15, department.getLingyu());
			ps.setString(16, department.getDishi());
			ps.setInt(17, department.getId());
			ps.executeUpdate();

			// 2.----------------------------------------------------------------------起
			// 删除原来的管理员
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_ROLE_DEPMANGER_BACK));//update eluser
			// set role = ? where depid = ? and role = 2

			// ps.setInt(1,
			// DUConstants.USER_ROLE_STU);//DUConstants.USER_ROLE_STU=4
			// ps.setInt(2, department.getId());
			ps.executeUpdate();
			// 修改管理员信息
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_ROLE_SET));//update eluser set role = ?
			// where role = ?
			// ps.setInt(1,
			// DUConstants.USER_ROLE_DEPMAN);//DUConstants.USER_ROLE_DEPMAN = 2
			// ps.setInt(2, department.getManager().getId());
			// ps.executeUpdate();
			// ----------------------------------------------------------------------终
//			ps = ct.prepareStatement("call updatetlrid('department') ");
//			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/***************************************************************************
	 * 下级部门合并到上级
	 */
	public void deleteDep(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			deleteDep(ct, id);
		} catch (Exception e) {
			logger.error("修改部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 删除部门(并入上级)
	 * @param depid
	 * @param depParentid
	 * @throws ElException
	 */
	public void deleteDep(int depid,int depParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//1.获取子节点(更新)
			this.updateDepParent(depid, depParentid);
			//2.更新用户
			this.updateUserDep(depid, depParentid);
			//3.删除该节点
			//deleteDep(ct, depid);
			//deleteDepById(ct,depid);
			deleteDepNot(depid);
//			ps = ct.prepareStatement("call updatetlrid('department') ");
//			ps.executeUpdate();
//			ps.close();
		} catch (Exception e) {
			logger.error("修改部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新部门的父节点
	 * @param depid
	 * @throws ElException
	 */
	public void updateDepParent(int depid,int depParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update department set parentid=? where parentid=?");
			ps.setInt(1, depParentid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新部门的父节点出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 更新用户的depid
	 * @param depid
	 * @throws ElException
	 */
	public void updateUserDep(int depid,int depParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update eluser set depid=? where depid=?");
			ps.setInt(1, depParentid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新用户的depid出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据部门id删除部门
	 * @param ct
	 * @param id
	 * @throws ElException
	 */
	private void deleteDepById(Connection ct, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 删除基本信息
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
			//rs.close();
			ps.close();
		} catch (Exception e) {
			logger.error("删除部门信息出错！", e);
			throw new ElException(e);
		}
	}
	/**
	 * 根据部门id删除用户
	 * @param depid
	 * @throws ElException
	 */
	public void deleteUserByDepid(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from eluser where depid=?");
			ps.setInt(1, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新用户的depid出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteDepAndSub(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_SUBS));
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			// 删除子部
			while (rs.next()) {
				int idc = rs.getInt(1);
				deleteDep(ct, idc);
			}
			rs.close();
//			ps = ct.prepareStatement("call updatetlrid('department') ");
//			ps.executeUpdate();
//			ps.close();
			// 删除本部
			// deleteDep(ct,id);
		} catch (Exception e) {
			logger.error("修改部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 假删除部门
	 * @param id
	 * @throws ElException
	 */
	public void deleteDepAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_SUBS));
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			// 删除子部
			while (rs.next()) {
				int idc = rs.getInt(1);
//				deleteDep(ct, idc);
				deleteDepNot(idc);
			}
			rs.close();
//			ps = ct.prepareStatement("call updatetlrid('department') ");
//			ps.executeUpdate();
//			ps.close();
			// 删除本部
			// deleteDep(ct,id);
		} catch (Exception e) {
			logger.error("假删除部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void deleteDep(Connection ct, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Department dep = new Department();
			// 删除相关信息
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_PARENT_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setParent(new Department(rs.getInt(2)));
			}
			rs.close();
			ps.close();
			ps = ct.prepareStatement("delete from eluser where depid=?");
			ps.setInt(1, dep.getId());
			ps.executeUpdate();
			//rs.close();
			ps.close();
			// 用户的
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_DELETE_USER_SET));
			ps.setInt(1, dep.getParent().getId());
			ps.setInt(2, dep.getId());
			ps.executeUpdate();
			ps.close();
			// 课程的
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_DELETE_COURSE_SET));
			// ps.setInt(1, dep.getParent().getId());
			ps.setInt(1, dep.getId());
			ps.executeUpdate();
			ps.close();
			// 班级的
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_DELETE_CLASS_SET));
			// ps.setInt(1, dep.getParent().getId());
			ps.setInt(1, dep.getId());
			ps.executeUpdate();
			ps.close();
			/*
			 * // 知识的 ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(DUConstants.DEP_DELETE_KLTYPE_SET)); // ps.setInt(1,
			 * dep.getParent().getId()); ps.setInt(1, dep.getId());
			 * ps.executeUpdate(); ps.close();
			 */
			/*
			 * // 部门的
			 * 
			 * ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_PARENT_SET));
			 * ps.setInt(1, dep.getParent().getId()); ps.setInt(2, dep.getId());
			 * ps.executeUpdate();
			 */

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_DSUBS));
			ps.setInt(1, dep.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				Department cdep = new Department(rs.getInt(1));
				cdep.setParent(dep.getParent());
//				alterNode(ct, cdep, "department", "1=1");
			}
			rs.close();
			ps.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_DELETE_DEP_SET));
			ps.setInt(1, dep.getParent().getId());
			ps.setInt(2, dep.getId());
			ps.executeUpdate();
			ps.close();//--
			// 删除节点信息
//			deleteNode(ct, dep, "department", "1=1");
			// 删除基本信息
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.DEP_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
			rs.close();
			ps.close();
		} catch (Exception e) {
			logger.error("删除部门信息出错！", e);
			throw new ElException(e);
		}
	}
	/**
	 * 更新部门以及部门下人员的状态
	 * @param id
	 * @throws ElException
	 */
	public void deleteDepNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Department dep=new Department();
			ps = ct.prepareStatement("select id,bh from department where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setBh(rs.getString(2));
			}
			rs.close();
			ps.close();
			ps = ct.prepareStatement("update eluser set valid=0 where depid=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("update department set status=1,lid=0,rid=0,bh=? where id=?");
			ps.setString(1, dep.getBh()+"D");
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新部门以及部门下人员的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setParent(int id, int parentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_PARENT_SET));
			ps.setInt(1, parentid);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置上级部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Department getDepRootByCid() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_ROOT));
			rs = ps.executeQuery();
			System.out.println(ElQuerySql.getSQL(DUConstants.DEP_QUERY_ROOT));
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setManager(new ELUser(rs.getInt(5), rs.getString(11)));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setAddress(rs.getString(8));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
				dep.setBh(rs.getString(12));
				dep.setLid(rs.getInt(13));
				dep.setRid(rs.getInt(14));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public void alterSystemconf(SystemConf sc) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.SYSTEM_ALTER));
			ps.setString(1, sc.getContent());
			ps.setInt(2, sc.getType());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public SystemConf getSystemConfByType(int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SystemConf sc = new SystemConf();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.SYSTEM_BYTYPE));
			ps.setInt(1, type);
			rs = ps.executeQuery();
			if (rs.next()) {
				sc.setType(rs.getInt(1));
				sc.setContent(rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sc;
	}

	public void addOpusers(String type, int userid, int depid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into department_" + type
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
	
	public void addCompetenceOpusers(String tablename,int type ,int kledgeid, int depid)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into " + tablename
					+ "(knowledgemanageid,depid,type) values(?,?,?)");
			ps.setInt(1, kledgeid);
			ps.setInt(2, depid);
			ps.setInt(3, type);
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
			ps = ct.prepareStatement("select * from department_" + type
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
			ps = ct.prepareStatement("delete from department_" + type
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
					.prepareStatement("select eu.id,eu.realname,eu.username from department_"
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
					.prepareStatement(" delete from department_op_user where userid= ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void deleteCompetenceUserOpGrant(int kledgeid,String tablename) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" delete from "+tablename+" where knowledgemanageid= ? and depid is not null");
			ps.setInt(1, kledgeid);
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
					.prepareStatement(" delete from department_use_user where userid= ?");
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
	public void deleteUserOpOrUseGrant(int userId,String type ,int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from department_"+type+"_user where userid= ? and depid=?");
			ps.setInt(1, userId);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public String getByIdXiaJi(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String ids = "";
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from department where id = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0; 
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			rs.close();
			ps.close();
			
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" select id  from department where lid > ? and rid < ? ");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();  
			while (rs.next()) {   
				if(ids.equals("")){
					ids = rs.getInt(1)+"";
				}else{
					ids = ids +","+ rs.getInt(1);
				} 
			}   
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally{
			DBConnection.closeConnectInfo(ct, ps, rs); 
			return ids;
		}
	} 
	/**
	 * 根据部门树获取包含下级的查询sql
	 * @param depTree
	 * @return
	 */
//	public String getDepTreeWhereSql(Department depTree) throws ElException{
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null; 
//		String depids = "";
//		StringBuffer whereSql= new StringBuffer();
//		try {
//			ct = DBConnection.getConnection();
//			if(depTree.getId() == -2){ 
//				whereSql.append("(");
//				for(int i = 0;i < depTree.getChild().size();i++){
//					if(depids.equals("")){
//						depids = depids + depTree.getChild().get(i).getId(); 
//					}else{
//						depids = depids + "," + depTree.getChild().get(i).getId(); 
//					}
//				}
//				whereSql.append(")");
//				ps = ct.prepareStatement("select lid,rid from DEPARTMENT where id in ("+depids+")"); 
//				rs = ps.executeQuery();
//				int x=0;
//				while(rs.next()){  
//					if(depTree.getChild().size() > 1 && depTree.getChild().size() != x && x >= 1){// 中间不用加			
//						whereSql.append(" or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"); 
//					}else if (depTree.getChild().size() == x){//结束前面加     ） 
//						whereSql.append(" or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+"))"); 
//					}else{//开始前面加   （			
//						whereSql.append(" ((dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"); 
//					}
//					x++;
//				}  
//			}else{  
//				// 获取 部门的左右值
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//				ps.setInt(1, depTree.getId());
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					whereSql.append(" dp.lid>="+rs.getInt(2)+" and dp.rid<= " +rs.getInt(3)); 
//				}
//			}
//		}catch (Exception e) {
//			logger.error("删除用户权限出错！", e);
//			throw new ElException(e);
//		} finally{
//			DBConnection.closeConnectInfo(ct, ps, rs); 
//		}
//		return whereSql.toString();
//	}

	public int checkDepForMonth(String month) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int id = -1;
		Department d = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select id from department where name = '" + month + "' and parentid = 1";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}else {
				d = new Department();
				d.setName(month);
				d.setParent(new ElNode(1));
				d.setManager(new ELUser(1));
				d.setIssp(0);
				id = this.addDep1(d);
			}

		} catch (Exception e) {
			logger.error("没有节点，创建节点；有节点，获取节点失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return id;
	}
	
	public List<Department> getDepByIssp() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> list = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description from department where issp=1 and status!=1");
			rs = ps.executeQuery();
			while(rs.next()){
				Department dep = new Department();
				dep.setId(rs.getInt(1));
				dep.setName(rs.getString(2));
				dep.setDescription(rs.getString(3));
				list.add(dep);
			}
		} catch (Exception e) {
			logger.error("获取二级页面部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public boolean checkDepidIsThreeNode(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
//			ps = ct
//					.prepareStatement("call checkDepIdIsThreeNode(?,?)");
			CallableStatement cs = ct.prepareCall("{call checkDepIdIsThreeNode(?,?)}");  
			cs.setInt(1, id);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);  
//			rs = ps.executeQuery();
			cs.execute(); 
			flag = cs.getString(2).equals("true")?true:false;

		} catch (Exception e) {
			logger.error("判断选择的是否是三级节点失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public boolean checkDepName(String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean bool = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select d.name from DEPARTMENT d where d.name =?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				// 存在
				bool =  true;
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bool;
	}

	public int getDepId(String sjbh) throws ElException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from department where bh=?");
			ps.setString(1, sjbh);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);

		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 1;
	}
	
	public String getBhByParentid(int parentid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sjbh = null;
		try {
			ct= DBConnection.getConnection();
			ps = ct.prepareStatement("select * from department where id=?");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			if(rs.next()){
				sjbh = rs.getString("bh");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sjbh;
	}
	/**
	 * 根据用户查询用所在单位
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
		String sql ="";
		try {
			depar = getParentidByUserDepid(depid);
			for(int i = 0 ; i < 10 ; i++){//最多循环10次				
				if(depar.getParent().getId() != 1){ 
					depar = getParentidByUserDepid(depar.getParent().getId());
				}else{
					break;//父节点 == 1 跳出循环
				}
			}
			ct = DBConnection.getConnection();  
			sql ="select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email from DEPARTMENT d where d.id =?";
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
		String sql ="";
		try {
			ct = DBConnection.getConnection();  
			sql ="select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email from DEPARTMENT d where d.id =?";
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
	
}
