package com.sopia.schedule.dao.impl.xialajibie;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElQuerySql;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.schedule.dao.xialajibie.SelectLevelDao;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public class SelectLevelDaoImpl implements SelectLevelDao {
	private static final Log logger = LogFactory
	.getLog(SelectLevelDaoImpl.class);

	public SelectLevel getSelectLevelTree_level1(int pid, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SelectLevel dep = null;
		try {
			dep = getDepRootByCid();
			ct = DBConnection.getConnection();
			dep.setChild(listdepChildsByPId(dep.getId()));
		} catch (Exception e) {
			logger.error("获取下拉选项树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public SelectLevel getSelectLevelTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		System.out.print("userid:"+userid+"     type--------------:"+type+"		stopid:"+stopid+"\t containStop"+containStop);
		
		
		SelectLevel dep = null;		
		if (type.equals("op")) {
			dep = new SelectLevel(ElConstants.USER_OP_LIB, "可操作的部门");
		} else {
			dep = new SelectLevel(ElConstants.USER_OP_LIB, "可使用的部门");
		};

		
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select selectlevelid from SelectLevel_" + type
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<SelectLevel> list = new ArrayList<SelectLevel>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					SelectLevel depc = getDepById(rs.getInt(1));// getDepTree(rs.getInt(1),
																// stopid,
																// false, 1);
					if(depc.getId()==0){
						continue;
					}
					depc.setParent(dep);
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
	
	public SelectLevel getDepRootByCid() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SelectLevel dep = new SelectLevel();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh from SelectLevel d   where d.parentid=0";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new SelectLevel(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setLid(rs.getInt(5));
				dep.setRid(rs.getInt(6));
				dep.setBh(rs.getString(7));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public List<SelectLevel> listdepChildsByPId(int parentid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<SelectLevel> deps = new ArrayList<SelectLevel>();
		SelectLevel dep = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
							"from selectlevel d left join selectlevel c on c.parentid = d.id  where d.parentid = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh order by d.id");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				dep = new SelectLevel(rs.getInt(1),rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setLid(rs.getInt(5));
				dep.setRid(rs.getInt(6));
				dep.setBh(rs.getString(7));
				dep.setClassCount(rs.getInt(8));
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("获取下拉选项树出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}
	
	public SelectLevel getDepById(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SelectLevel dep = new SelectLevel();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
					" from SelectLevel d left join SelectLevel c on c.parentid = d.id  where d.id = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new SelectLevel(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParentid(rs.getInt(4));
				dep.setParent(new SelectLevel(rs.getInt(4)));
				dep.setLid(rs.getInt(5));
				dep.setRid(rs.getInt(6));
				dep.setBh(rs.getString(7));
				dep.setClassCount(rs.getInt(8));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	public void addSelectLevel(SelectLevel selectLevel) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into selectLevel (name,parentid,lid,rid,description,bh) values (?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, selectLevel.getName());
			ps.setInt(2, selectLevel.getParent().getId());
			ps.setInt(3, selectLevel.getLid());
			ps.setInt(4, selectLevel.getRid());
			ps.setString(5, selectLevel.getDescription());
			ps.setString(6, selectLevel.getBh());
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("select selectLevel_sequence.currval from dual ");
			rs = ps.executeQuery();
			if (rs.next())
				selectLevel.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("插入下拉选项出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public boolean checkSelectLevelBh(String bh) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.bh from selectlevel d where d.bh =?");
			ps.setString(1, bh.trim());
			rs = ps.executeQuery();
			if (rs.next()) {
				// 存在
				return true;
			}
		} catch (Exception e) {
			logger.error("查看下拉选项编号出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<ELUser> getOpUsers(String type, int selectlevelid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from selectlevel_"
							+ type
							+ "_user du left join eluser eu on eu.id = du.userid where du.selectlevelid = ?");
			ps.setInt(1, selectlevelid);
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

	public void deleteSelectLevelAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			SelectLevel dep = new SelectLevel();
			ps = ct.prepareStatement("select d.id,d.lid,d.rid from selectLevel d where d.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			ps = ct.prepareStatement("select id from selectlevel where lid>=? and rid<=?");
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			// 删除子部
			while (rs.next()) {
				int idc = rs.getInt(1);
				deleteDepNot(idc);
			}
			rs.close();
		} catch (Exception e) {
			logger.error("假删除部门出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void deleteDepNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			SelectLevel dep=new SelectLevel();
			ps = ct.prepareStatement("select id,bh from SelectLevel where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setBh(rs.getString(2));
			}
			rs.close();
			ps.close();
			ps = ct.prepareStatement("update eluser set valid=0 where selectlevelid=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("update SelectLevel set status=1,lid=0,rid=0,bh=? where id=?");
			ps.setString(1, dep.getBh()+"S");
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新部门以及部门下人员的状态出错！", e);
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
			deleteDepNot(depid);
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
			ps = ct.prepareStatement("update selectLevel set parentid=? where parentid=?");
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
			ps = ct.prepareStatement("update eluser set selectlevelid=? where selectlevelid=?");
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
	
	public List<ELUser> getEUsBySelectLevelid(int depid) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_QUERY_BYSELECTLEVELID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				eus.add(new ELUser(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			logger.error("下拉选项用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}
	
	
	public void alterSelectLevel(SelectLevel selectLevel) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update selectLevel set name=?, description=?,parentid=?,bh=? where id = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, selectLevel.getName());
			ps.setString(2, selectLevel.getDescription());
			ps.setInt(3, selectLevel.getParent().getId());
			ps.setString(4, selectLevel.getBh());
			ps.setInt(5, selectLevel.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改下拉选项出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int checkJibieshu(int selectlevelid) throws ElException {
		CallableStatement cs  = null;
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		int icount = 0;
		try {
			ct = DBConnection.getConnection();
			cs = ct.prepareCall("{call selectlevel_jibieshu(?,?)}"); 
			cs.setInt(1, selectlevelid);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			icount = cs.getInt(2);
		} catch (Exception e) {
			logger.error("获取级别数出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return icount;
	}
	

}
