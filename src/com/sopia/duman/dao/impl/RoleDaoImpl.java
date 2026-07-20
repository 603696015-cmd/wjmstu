package com.sopia.duman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.FuncDao;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElGroup;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;

public class RoleDaoImpl implements RoleDao {
	private static final Log logger = LogFactory.getLog(RoleDaoImpl.class);

	// public void addRole(ElRole role) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(DUConstants.USER_ROLE_ADD));
	// ps.setString(1, role.getName());
	// ps.setString(2, role.getDescription());
	// ps.executeUpdate();
	// } catch (Exception e) {
	// logger.error("检查用户名和密码失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// }
	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 * @throws ElException
	 */
	public int addRole(ElRole role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" insert into elrole(name,description,createrid,beijingimg,tishiyu,common1,common2,common3,common4,common5,common6) values(?,?,?,?,empty_blob(),?,?,?,?,?,?)");
			ps.setString(1, role.getName());
			ps.setString(2, role.getDescription());
			ps.setInt(3, role.getCreater().getId());
			ps.setString(4, role.getBeijingimg());
			ps.setString(5, role.getCommon1());
			ps.setString(6, role.getCommon2());
			ps.setString(7, role.getCommon3());
			ps.setString(8, role.getCommon4());
			ps.setString(9, role.getCommon5());
			ps.setString(10, role.getCommon6());
			ps.executeUpdate();
			ps = ct
					.prepareStatement("select elrole_sequence.currval from dual");
			rs = ps.executeQuery();

			OracleBlob setblob = new OracleBlob(ct, "elrole_sequence", "elrole",
					"id", "tishiyu", role.getTishiyu(), "添加角色失败");
			setblob.addContent();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void alterRole(ElRole role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLE_ALTER));
			ps.setString(1, role.getName());
			ps.setString(2, role.getDescription());
			ps.setString(3, role.getBeijingimg());
			ps.setString(4, role.getCommon1());
			ps.setString(5, role.getCommon2());
			ps.setString(6, role.getCommon3());
			ps.setString(7, role.getCommon4());
			ps.setString(8, role.getCommon5());
			ps.setString(9, role.getCommon6());
			ps.setInt(10, role.getId());
			ps.executeUpdate();

			OracleBlob setblob = new OracleBlob("elrole", "id", role.getId()
					+ "", "tishiyu", role.getTishiyu(), "修改角色失败", ct);
			setblob.updateContent();
		} catch (Exception e) {
			logger.error("角色修改！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteRole(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLE_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLE_SET_BYRID));
			ps.setInt(1, id);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLEFUNC_DELETE_BYRID));
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("角色修改！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addRoleFunc(ElRole role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			int roleid = role.getId();
			List<ElFunc> funcs = role.getFuncs();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLEFUNC_DELETE));
			ps.setInt(1, role.getId());
			ps.executeUpdate();
			if (null != funcs) {
				ps.close();
				for (int i = 0; i < funcs.size(); i++) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.USER_ROLEFUNC_ADD));
					ps.setInt(1, roleid);
					ps.setInt(2, funcs.get(i).getId());
					ps.executeUpdate();
					ps.close();
				}
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addUserRoleFunc(ElRole role, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			int roleid = role.getId();
			List<ElFunc> funcs = role.getFuncs();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_USERFUNC_DELETE));
			ps.setInt(1, userid);
			ps.executeUpdate();
			if (null != funcs) {
				ps.close();
				for (int i = 0; i < funcs.size(); i++) {
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.USER_USERFUNC_ADD));
					ps.setInt(1, userid);
					ps.setInt(2, funcs.get(i).getId());
					ps.executeUpdate();
					ps.close();
				}
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ElRole getRoleById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElRole er = new ElRole();
		FuncDao funcDao = new FuncDaoImpl();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLE_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				er.setId(rs.getInt(1));
				er.setName(rs.getString(2));
				er.setDescription(rs.getString(3));
				er.setBeijingimg(rs.getString(4));
				er.setTishiyu(new OracleBlob().getContent(rs.getBlob(5)));
				er.setCommon1(rs.getString(6));
				er.setCommon2(rs.getString(7));
				er.setCommon3(rs.getString(8));
				er.setCommon4(rs.getString(9));
				er.setCommon5(rs.getString(10));
				er.setCommon6(rs.getString(11));

				if (er.getCommon1() != null && !"".equals(er.getCommon1())) {
					er.setFunc_common1(funcDao.getFuncById(Integer.parseInt(er
							.getCommon1())));
				}
				if (er.getCommon2() != null && !"".equals(er.getCommon2())) {
					er.setFunc_common2(funcDao.getFuncById(Integer.parseInt(er
							.getCommon2())));
				}
				if (er.getCommon3() != null && !"".equals(er.getCommon3())) {
					er.setFunc_common3(funcDao.getFuncById(Integer.parseInt(er
							.getCommon3())));
				}
				if (er.getCommon4() != null && !"".equals(er.getCommon4())) {
					er.setFunc_common4(funcDao.getFuncById(Integer.parseInt(er
							.getCommon4())));
				}
				if (er.getCommon5() != null && !"".equals(er.getCommon5())) {
					er.setFunc_common5(funcDao.getFuncById(Integer.parseInt(er
							.getCommon5())));
				}
				if (er.getCommon6() != null && !"".equals(er.getCommon6())) {
					er.setFunc_common6(funcDao.getFuncById(Integer.parseInt(er
							.getCommon6())));
				}
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}

	public List<ElRole> listRoles() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElRole> ers = new ArrayList<ElRole>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLE_LIST));
			rs = ps.executeQuery();
			while (rs.next()) {
				ElRole er = new ElRole();
				er.setId(rs.getInt(1));
				er.setName(rs.getString(2));
				er.setDescription(rs.getString(3));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("角色列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 根据角色和创建者获取角色列表
	 * 
	 * @param roleid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<ElRole> listRoles(int roleid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		// 如果是超级管理员就获取所有角色，否则只能获取超级管理员和自己创建的角色（不包括超级管理员）
		if (roleid == 1) {
			return listRoles();
		}
		List<ElRole> ers = new ArrayList<ElRole>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description from elrole elr where (createrid=? or createrid in(select id from eluser where role=1)) and id!=1");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElRole er = new ElRole();
				er.setId(rs.getInt(1));
				er.setName(rs.getString(2));
				er.setDescription(rs.getString(3));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("根据角色和创建者获取角色列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	// public List<ElRole> listRoles(int unequalTo) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// List<ElRole> ers = new ArrayList<ElRole>();
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct.prepareStatement("select id,name,description from elrole where id
	// != ?");
	// ps.setInt(1, unequalTo);
	// rs = ps.executeQuery();
	// while (rs.next()) {
	// ElRole er = new ElRole();
	// er.setId(rs.getInt(1));
	// er.setName(rs.getString(2));
	// er.setDescription(rs.getString(3));
	// ers.add(er);
	// }
	// } catch (Exception e) {
	// logger.error("角色列表失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return ers;
	// }
	/**
	 * 获取我创建的所有角色
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<ElRole> listMyRoles(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElRole> ers = new ArrayList<ElRole>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description from elrole where createrid= ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElRole er = new ElRole();
				er.setId(rs.getInt(1));
				er.setName(rs.getString(2));
				er.setDescription(rs.getString(3));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取我创建的所有角色失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public List<ElFunc> getFuncsByRid(int rid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> efs = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLEFUNC_LIST_BYRID));
			ps.setInt(1, rid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setDescription(rs.getString(4));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	private void deleteFuncdo(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_DELETE_BYID));
			ps.setInt(1, id);
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_ROLEFUNC_DELETE_BYID));
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteFunc(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_CHILD));
			ps.setInt(1, id);

			rs = ps.executeQuery();
			deleteFuncdo(id);
			while (rs.next()) {
				id = rs.getInt(1);
				deleteFunc(id);
			}

		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addFunc(ElFunc f) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_ADD));
			ps.setString(1, f.getFunccode());
			ps.setString(2, f.getName());
			ps.setString(3, f.getDescription());
			ps.setInt(4, f.getParent().getId());
			ps.setBoolean(5, f.getNeedCheck());
			ps.setString(6, f.getParams());
			ps.setString(7, f.getTarget());
			ps.setString(8, f.getDyimg());
			ps.setString(9, f.getBgimg());
			ps.setString(10, f.getLinkimg());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加功能失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ElFunc getFuncTree() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc root = new ElFunc();
		root.setName("所有功能");
		root.setDescription("");
		try {
			ct = DBConnection.getConnection();

			root.setChild(getChild(ct, 0, 0));
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return root;
	}
	
	public ElFunc getFuncTree1() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc root = new ElFunc();
		root.setName("所有功能");
		root.setDescription("");
		try {
			ct = DBConnection.getConnection();

			root.setChild(getChild1(ct, 0, 0));
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return root;
	}

	/**
	 * 获取功能权限树(加角色限制)
	 * 
	 * @param roleId
	 * @return
	 * @throws ElException
	 */
	public ElFunc getFuncTreeByRoleId(int roleId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc root = new ElFunc();
		root.setName("该用户的角色功能权限");
		root.setDescription("");
		try {
			ct = DBConnection.getConnection();

			root.setChild(getChild(ct, 0, 0, roleId));
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return root;
	}

	/**
	 * 获取功能权限树(加userid限制)
	 * 
	 * @param roleId
	 * @return
	 * @throws ElException
	 */
	public ElFunc getFuncTreeByUserid(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc root = new ElFunc();
		root.setName("该用户的功能权限");
		root.setDescription("");
		try {
			ct = DBConnection.getConnection();

			root.setChild(getElUserFuncChild(ct, 0, 0, userid));
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return root;
	}

	private List<ElFunc> getChild(Connection ct, int parentid, int level)
			throws Exception {
		PreparedStatement ps = null;
		List<ElFunc> fs = new ArrayList<ElFunc>();
		ps = ct
				.prepareStatement(ElQuerySql
						.getSQL(DUConstants.USER_FUNC_CHILD));
		ps.setInt(1, parentid);
		ResultSet rs = ps.executeQuery();
		level++;
		while (rs.next()) {
			ElFunc f = new ElFunc();
			f.setId(rs.getInt(1));
			f.setFunccode(rs.getString(2));
			f.setName(rs.getString(3));
			f.setDescription(rs.getString(4));
			f.setLevel(level);
			f.setParent(new ElFunc());
			f.getParent().setId(rs.getInt(5));
			f.setNeedCheck(rs.getBoolean(6));
			f.setChild(getChild(ct, f.getId(), level));
			fs.add(f);
		}
		rs.close();
		ps.close();
		return fs;
	}
	
	
	private List<ElFunc> getChild1(Connection ct, int parentid, int level)
		 throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ElFunc> fs = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select s.id,s.name,s.description, s.parentid,count(c.id)   from elfunc s left join elfunc c on c.parentid = s.id  "+          
								"where s.parentid = ? group by s.id,s.name,s.description, s.parentid");
			ps.setInt(1, parentid);
			 rs = ps.executeQuery();
			level++;
			while (rs.next()) {
				ElFunc f = new ElFunc();	
				f.setId(rs.getInt(1));
				f.setName(rs.getString(2));
				f.setDescription(rs.getString(3));
				f.setParent(new ElFunc());
				f.getParent().setId(rs.getInt(4));
				f.setClassCount(rs.getInt(5));
				f.setChild(getChild1(ct, f.getId(), level));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
		}

	/**
	 * 获取该父节点的子节点（权限树，加了角色限制查询）
	 * 
	 * @param ct
	 * @param parentid
	 * @param level
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	private List<ElFunc> getChild(Connection ct, int parentid, int level,
			int roleId) throws Exception {
		PreparedStatement ps = null;
		List<ElFunc> fs = new ArrayList<ElFunc>();
		// ps = ct
		// .prepareStatement(ElQuerySql
		// .getSQL(DUConstants.USER_FUNC_CHILD));
		ps = ct
				.prepareStatement("select id,funccode,name,description,parentid,needcheck from elrolefunc euf left join elfunc ef on euf.funcid=ef.id where parentid =? and roleid=? ");
		ps.setInt(1, parentid);
		ps.setInt(2, roleId);
		ResultSet rs = ps.executeQuery();
		level++;
		while (rs.next()) {
			ElFunc f = new ElFunc();
			f.setId(rs.getInt(1));
			f.setFunccode(rs.getString(2));
			f.setName(rs.getString(3));
			f.setDescription(rs.getString(4));
			f.setLevel(level);
			f.setParent(new ElFunc());
			f.getParent().setId(rs.getInt(5));
			f.setNeedCheck(rs.getBoolean(6));
			// f.setChild(getChild(ct, f.getId(), level));
			f.setChild(getChild(ct, f.getId(), level, roleId));
			fs.add(f);
		}
		rs.close();
		ps.close();
		return fs;
	}

	/**
	 * 获取该父节点的子节点（权限树，加了userid限制查询）
	 * 
	 * @param ct
	 * @param parentid
	 * @param level
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	private List<ElFunc> getElUserFuncChild(Connection ct, int parentid,
			int level, int userid) throws Exception {
		PreparedStatement ps = null;
		List<ElFunc> fs = new ArrayList<ElFunc>();
		// ps = ct
		// .prepareStatement(ElQuerySql
		// .getSQL(DUConstants.USER_FUNC_CHILD));
		ps = ct
				.prepareStatement("select id,funccode,name,description,parentid,needcheck from eluserfunc euf left join elfunc ef on euf.funcid=ef.id where parentid =? and userid=?  order by description asc");
		ps.setInt(1, parentid);
		ps.setInt(2, userid);
		ResultSet rs = ps.executeQuery();
		level++;
		while (rs.next()) {
			ElFunc f = new ElFunc();
			f.setId(rs.getInt(1));
			f.setFunccode(rs.getString(2));
			f.setName(rs.getString(3));
			f.setDescription(rs.getString(4));
			f.setLevel(level);
			f.setParent(new ElFunc());
			f.getParent().setId(rs.getInt(5));
			f.setNeedCheck(rs.getBoolean(6));
			// f.setChild(getChild(ct, f.getId(), level));
			f.setChild(getElUserFuncChild(ct, f.getId(), level, userid));
			fs.add(f);
		}
		rs.close();
		ps.close();
		return fs;
	}

	public void alterFunc(ElFunc f) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_ALTER));
			ps.setString(1, f.getFunccode());
			ps.setString(2, f.getName());
			ps.setString(3, f.getDescription());
			ps.setInt(4, f.getParent().getId());
			ps.setBoolean(5, f.getNeedCheck());
			ps.setString(6, f.getParams());
			ps.setString(7, f.getTarget());
			ps.setString(8, f.getDyimg());
			ps.setString(9, f.getBgimg());
			ps.setString(10, f.getLinkimg());
			ps.setInt(11, f.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改功能失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<String> listFuncs() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<String> efs = new ArrayList<String>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_LIST));
			rs = ps.executeQuery();
			while (rs.next()) {
				// ElFunc ef = new ElFunc();
				// ef.setId(rs.getInt(1));
				// ef.setFunccode(rs.getString(2));
				efs.add(rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	/**
	 * 获取角色-功能表的id
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<Integer> listFuncs_id() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Integer> efi = new ArrayList<Integer>();// --
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_LIST));
			rs = ps.executeQuery();
			while (rs.next()) {
				// ElFunc ef = new ElFunc();
				// ef.setId(rs.getInt(1));
				// ef.setFunccode(rs.getString(2));
				efi.add(rs.getInt(1));// --
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efi;
	}

	public boolean checkUserFuncs(int userid, String funccode)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from eluserfunc euf left join elfunc ef on ef.id= euf.funcid where euf.userid = ? and ef.funccode = ?");
			ps.setInt(1, userid);
			ps.setString(2, funccode);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// return false;
		return false;
	}

	/**
	 * 不需要验证的功能列表
	 * 
	 * @return
	 * @throws ElException
	 */
	public List<String> listUnnCheckFuncs() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<String> efs = new ArrayList<String>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_UNCLIST));
			rs = ps.executeQuery();
			while (rs.next()) {
				efs.add(rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	public ElFunc getFuncById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc f = new ElFunc();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_FUNC_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				f.setId(rs.getInt(1));
				f.setFunccode(rs.getString(2));
				f.setName(rs.getString(3));
				f.setDescription(rs.getString(4));
				f.setParent(new ElFunc());
				f.getParent().setId(rs.getInt(5));
				f.setNeedCheck(rs.getBoolean(6));
				f.setParams(rs.getString(7));
				f.setTarget(rs.getString(8));
				f.setDyimg(rs.getString(9));
				f.setBgimg(rs.getString(10));
				f.setLinkimg(rs.getString(11));

			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}

	public ElFunc getMenu(String funccode, int role, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc root = new ElFunc();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,funccode,name,params from elfunc where funccode = ? order by description asc");
			ps.setString(1, funccode.trim());
			rs = ps.executeQuery();
			if (rs.next()) {
				root.setId(rs.getInt(1));
				root.setFunccode(rs.getString(2));
				root.setName(rs.getString(3));
				root.setParams(rs.getString(4));
			}
			rs.close();
			rs = null;
			root.setChild(getMenuChild(root.getId(), role, userid));
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return root;
	}

	public List<ElFunc> getMenus(int parentid, int role, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> efs = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select
			// ef.id,ef.funccode,ef.name,ef.params,ef.target from elfunc
			// ef,elrolefunc ref where ef.parentid = ?"
			// + " and ef.id=ref.funcid and ref.roleid =? and
			// ef.funccode!='index' and ef.funccode!='courseman' order by
			// ef.description asc");
			ps = ct
					.prepareStatement("(select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description from elfunc ef,elrolefunc ref where ef.parentid = ? "
							+ "and ef.id=ref.funcid and ref.roleid =? and instr(nvl(ef.funccode,'F'),'index') = 0 and instr(nvl(ef.funccode,'F'),'wjm_admin_login') = 0 and instr(nvl(ef.funccode,'F'),'userRegister') = 0 and instr(nvl(ef.funccode,'F'),'courseman') = 0   )  union "
							+ "(select ef1.id,ef1.funccode,ef1.name,ef1.params,ef1.target,ef1.description from elfunc ef1,eluserfunc ref1 where ef1.parentid = ? "
							+ "and ef1.id=ref1.funcid and ref1.userid =? and ef1.funccode!='index' and ef1.funccode!='courseman' ) order by description asc");
			ps.setInt(1, parentid);
			ps.setInt(2, role);
			ps.setInt(3, parentid);
			ps.setInt(4, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setParams(rs.getString(4));
				ef.setTarget(rs.getString(5));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	public List<ElFunc> getMenus_newversion(int parentid, int role, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> efs = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select
			// ef.id,ef.funccode,ef.name,ef.params,ef.target from elfunc
			// ef,elrolefunc ref where ef.parentid = ?"
			// + " and ef.id=ref.funcid and ref.roleid =? and
			// ef.funccode!='index' and ef.funccode!='courseman' order by
			// ef.description asc");
			ps = ct
					.prepareStatement("(select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description from elfunc ef,elrolefunc ref where ef.parentid = ? "
							+ "and ef.id=ref.funcid and ref.roleid =? and ef.funccode!='index' and ef.funccode!='courseman' and ef.funccode!='studentman' )  union "
							+ "(select ef1.id,ef1.funccode,ef1.name,ef1.params,ef1.target,ef1.description from elfunc ef1,eluserfunc ref1 where ef1.parentid = ? "
							+ "and ef1.id=ref1.funcid and ref1.userid =? and ef1.funccode!='index' and ef1.funccode!='courseman' and ef1.funccode!='studentman' ) order by description asc");
			ps.setInt(1, parentid);
			ps.setInt(2, role);
			ps.setInt(3, parentid);
			ps.setInt(4, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setParams(rs.getString(4));
				ef.setTarget(rs.getString(5));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	private List<ElFunc> getMenuChild(int parentid, int role, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> efs = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			// .prepareStatement("select
					// ef.id,ef.funccode,ef.name,ef.params,ef.target from elfunc
					// ef,elrolefunc ref where ef.parentid = ? "
					// + "and ef.id=ref.funcid and ref.roleid =? order by
					// ef.description asc");
					.prepareStatement("(select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description from elfunc ef,elrolefunc ref where ef.parentid = ? "
							+ "and ef.id=ref.funcid and ref.roleid =? and   instr(nvl(ef.funccode,'F'),'index') = 0  and instr(nvl(ef.funccode,'F'),'courseman') = 0)  union "
							+ "(select ef1.id,ef1.funccode,ef1.name,ef1.params,ef1.target,ef1.description from elfunc ef1,eluserfunc ref1 where ef1.parentid = ? "
							+ "and ef1.id=ref1.funcid and ref1.userid =? and ef1.funccode!='index' and ef1.funccode!='courseman' ) order by description asc");
			ps.setInt(1, parentid);
			ps.setInt(2, role);
			ps.setInt(3, parentid);
			ps.setInt(4, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setParams(rs.getString(4));
				ef.setTarget(rs.getString(5));
				ef.setChild(getMenuChild(ef.getId(), role, userid));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	public List<ElGroup> listGroups() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElGroup> efs = new ArrayList<ElGroup>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description,gtype from elgroup");
			rs = ps.executeQuery();
			while (rs.next()) {
				ElGroup ef = new ElGroup();
				ef.setId(rs.getInt(1));
				ef.setName(rs.getString(2));
				ef.setDescription(rs.getString(3));
				ef.setGtype(rs.getInt(4));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	public ElGroup getGroupById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElGroup ef = new ElGroup();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description,gtype from elgroup where id =?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {

				ef.setId(rs.getInt(1));
				ef.setName(rs.getString(2));
				ef.setDescription(rs.getString(3));
				ef.setGtype(rs.getInt(4));
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ef;
	}

	public List<ELUser> listAssignUsers(int gid, int pageNow, int pageSize)
			throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		List<ELUser> assignedUsers = new ArrayList<ELUser>();

		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.USER_GROUP_ASSIGN_LIST));
			// title,description,begintime,endtime,epid,norm,trcid
			ps.setInt(1, gid);
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

	public int listAssignUsersSize(int gid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from elgroup2user gu,eluser eu,department dep where dep.id=eu.depid "
							+ "and eu.id=gu.userid and gu.gid = ? ");
			ps.setInt(1, gid);
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

	public boolean checkUserIngroup(int userid, int groupid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from elgroup2user where userid=? and gid = ? ");
			ps.setInt(1, userid);
			ps.setInt(2, groupid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getBoolean(1);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void groupAssign2User(int userid, int groupid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into elgroup2user(userid,gid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, groupid);
			ps.executeUpdate();
			// 培训班 通过设置
			ps = ct
					.prepareStatement("select cl.id from elclass cl  where  cl.group2 =? ");
			ps.setInt(1, groupid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int classid = rs.getInt(1);
				graduateClassApplay(userid, classid);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
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
			logger.error("培训班申请结业！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void groupUnAssign2User(int userid, int groupid) throws ElException {
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from elgroup2user where userid=? and gid=?");
			ps.setInt(1, userid);
			ps.setInt(2, groupid);
			ps.executeUpdate();
			ps = ct
					.prepareStatement("select cl.id from elclass cl  where  cl.group2 =? ");
			ps.setInt(1, groupid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int classid = rs.getInt(1);
				graduateClassApplay(userid, classid);
			}
		} catch (Exception e) {
			logger.error("人才库-场次集列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addGroup(ElGroup group) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into elgroup(name,description,gtype) values(?,?,?)");
			ps.setString(1, group.getName());
			ps.setString(2, group.getDescription());
			ps.setInt(3, group.getGtype());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterGroup(ElGroup group) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update elgroup set name=?,"
					+ "description=?,gtype= ? where id = ?");
			ps.setString(1, group.getName());
			ps.setString(2, group.getDescription());
			ps.setInt(3, group.getGtype());
			ps.setInt(4, group.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteGroup(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from elgroup where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ElGroup> listGroupsBytype(int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElGroup> efs = new ArrayList<ElGroup>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description,gtype from elgroup where gtype = ?");
			ps.setInt(1, type);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElGroup ef = new ElGroup();
				ef.setId(rs.getInt(1));
				ef.setName(rs.getString(2));
				ef.setDescription(rs.getString(3));
				ef.setGtype(rs.getInt(4));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	public void setUserfunc(int userid, String funccode, int role)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select f.id fid,fp.id pid,fp.funccode from elfunc f left join elfunc fp on f.parentid = fp.id where f.funccode = ? ");
			ps.setString(1, funccode);
			rs = ps.executeQuery();
			if (rs.next()) {
				int fid = rs.getInt(1);
				int fpid = rs.getInt(2);
				String fpcode = rs.getString(3);
				if (!checkRoleFunc(userid, fid)) {
					PreparedStatement ps1 = ct
							.prepareStatement("insert into eluserfunc (userid,funcid) values(?,?)");
					ps1.setInt(1, userid);
					ps1.setInt(2, fid);
					ps1.executeUpdate();
				}
				if (fpid != 0)
					setUserfunc(userid, fpcode, role);

			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private boolean checkRoleFunc(int user, int funcid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from eluserfunc where userid = ? and funcid  = ?");
			ps.setInt(1, user);
			ps.setInt(2, funcid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void checkUserfunc(int userid, String funccode, String table)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			if (!checkHasOpOrUseLib(userid, table)) {
				// 删除功能
				ct = DBConnection.getConnection();
				ps = ct
						.prepareStatement("select f.id fid from eluserfunc euf left join elfunc f on euf.funcid = f.id left join elfunc fp on f.parentid = fp.id where f.funccode = ? and euf.userid = ?");
				ps.setString(1, funccode);
				ps.setInt(2, userid);
				rs = ps.executeQuery();
				if (rs.next()) {
					int fid = rs.getInt(1);
					// int fpid = rs.getInt(2);
					// String fpcode = rs.getString(3);
					deleteUserFunc(userid, fid);
				}
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkRolefunc(int role, String funccode) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean b = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select f.id fid from elrolefunc euf left join elfunc f on euf.funcid = f.id where f.funccode = ? and euf.roleid =?");
			ps.setString(1, funccode);
			ps.setInt(2, role);
			rs = ps.executeQuery();
			if (rs.next()) {
				b = true;
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return b;
	}

	private void deleteUserFunc(int userid, int fid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// 删除功能

			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select f.parentid from eluserfunc euf left join elfunc f on euf.funcid = f.id where f.id = ? and userid = ? ");
			ps.setInt(1, fid);
			ps.setInt(2, userid);
			int fpid = 0;
			rs = ps.executeQuery();
			if (rs.next()) {
				fpid = rs.getInt(1);
			}
			ps = ct
					.prepareStatement("delete from eluserfunc where funcid = ? and userid  = ? ");
			ps.setInt(1, fid);
			ps.setInt(2, userid);
			ps.executeUpdate();
			if (fpid != 0) {
				ps = ct
						.prepareStatement("select * from eluserfunc euf left join elfunc f on euf.funcid = f.id where f.parentid = ? and userid = ? ");
				ps.setInt(1, fpid);
				ps.setInt(2, userid);
				rs = ps.executeQuery();
				if (!rs.next()) {
					// ps = ct
					// .prepareStatement("select f.id fid,fp.id pid,fp.funccode
					// from eluserfunc euf left join elfunc f on euf.funcid =
					// f.id left join elfunc fp on f.parentid = fp.id where f.id
					// = ? ");
					// ps.setInt(1, fpid);
					// rs = ps.executeQuery();
					// if (rs.next()) {
					// int fid1 = rs.getInt(1);
					// }
					deleteUserFunc(userid, fpid);
				}
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	private boolean checkHasOpOrUseLib(int userid, String table)
			throws ElException {
		boolean b = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from " + table
					+ " where userid = ? ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {// 查看分权限。
				b = true;
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return b;
	}

	// 修改这个方法，完成个人功能授权树状的实现 22222
	public List<ElFunc> getMenus(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> efs = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();

			// ps = ct
			// .prepareStatement("(select
			// ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description from
			// elfunc ef,elrolefunc ref where ef.parentid = ? "
			// + "and ef.id=ref.funcid and ref.roleid =? and
			// ef.funccode!='courseman' ) union "
			// + "(select
			// ef1.id,ef1.funccode,ef1.name,ef1.params,ef1.target,ef1.description
			// from elfunc ef1,eluserfunc ref1 where ef1.parentid = ? "
			// + "and ef1.id=ref1.funcid and ref1.userid =? and
			// ef1.funccode!='courseman' ) order by description asc");
			//			

			ps = ct
					.prepareStatement("select t.id,t.funccode,t.name,t.parentid from elfunc t start with t.parentid in (select distinct euf.funcid from eluserfunc euf where euf.userid="
							+ userid + ") connect by prior t.parentid =t.id");

			// ps.setInt(1, parentid);
			//			 
			// ps.setInt(3, parentid);
			// ps.setInt(4, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				// ef.setParams(rs.getString(4));
				// ef.setTarget(rs.getString(5));
				ef.setParent(new ElFunc());
				// ef.setLevel(1);
				// if (sub)
				// ef.setChild(getMenuChild(ef.getId(), role, userid, 2));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	private List<ElFunc> getMenuChild(int parentid, int role, int userid,
			int level) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ElFunc> efs = new ArrayList<ElFunc>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			// .prepareStatement("select
					// ef.id,ef.funccode,ef.name,ef.params,ef.target from elfunc
					// ef,elrolefunc ref where ef.parentid = ? "
					// + "and ef.id=ref.funcid and ref.roleid =? order by
					// ef.description asc");
					.prepareStatement("(select ef.id,ef.funccode,ef.name,ef.params,ef.target,ef.description from elfunc ef,elrolefunc ref where ef.parentid = ? "
							+ "and ef.id=ref.funcid and ref.roleid =? and ef.funccode!='index' and ef.funccode!='courseman' )  union "
							+ "(select ef1.id,ef1.funccode,ef1.name,ef1.params,ef1.target,ef1.description from elfunc ef1,eluserfunc ref1 where ef1.parentid = ? "
							+ "and ef1.id=ref1.funcid and ref1.userid =? and ef1.funccode!='index' and ef1.funccode!='courseman' ) order by description asc");
			ps.setInt(1, parentid);
			ps.setInt(2, role);
			ps.setInt(3, parentid);
			ps.setInt(4, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ElFunc ef = new ElFunc();
				ef.setId(rs.getInt(1));
				ef.setFunccode(rs.getString(2));
				ef.setName(rs.getString(3));
				ef.setParams(rs.getString(4));
				ef.setTarget(rs.getString(5));
				ef.setLevel(level);
				ef.setParent(new ElFunc(parentid));
				ef.setChild(getMenuChild(ef.getId(), role, userid, level + 1));
				efs.add(ef);
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return efs;
	}

	public List<ElFunc> getMenus(int parentid, int role, int userid, boolean sub)
			throws ElException {
		// TODO Auto-generated method stub
		return null;
	}

	public int checkFuncIsTwoOrThree(int gerenzhongxinid, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CallableStatement proc = null;
		int i = 0;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			proc = ct.prepareCall("{call checkFuncIsTwoOrThree(?,?,?)}");
			proc.setInt(1, gerenzhongxinid);
			proc.setInt(2, id);
			proc.registerOutParameter(3, oracle.jdbc.OracleTypes.INTEGER);
			proc.execute();

			i = proc.getInt(3);
			proc.close();
		} catch (Exception e) {
			logger.error("判断是否是二级菜单失败！", e);
			System.out.println(id + "=========");
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return i;
	}
	
	public ElRole getRoleByName(String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElRole er = new ElRole();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name,description from elrole where name = ?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				er.setId(rs.getInt(1));
				er.setName(rs.getString(2));
				er.setDescription(rs.getString(3));
			}
		} catch (Exception e) {
			logger.error("检查用户名和密码失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}
	
	public ElFunc getStById(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElFunc st = new ElFunc();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select el.id,el.name from elfunc  el where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				st.setId(rs.getInt(1));
				st.setName(rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return st;
	}

}
