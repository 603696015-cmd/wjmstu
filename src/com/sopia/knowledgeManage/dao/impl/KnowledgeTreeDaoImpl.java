package com.sopia.knowledgeManage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
import com.sopia.knowledgeManage.dao.KnowledgeTreeDao;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public class KnowledgeTreeDaoImpl implements KnowledgeTreeDao{
	private static final Log logger = LogFactory
	.getLog(KnowledgeTreeDaoImpl.class);
	
	public KnowledgeTree getknowledgeTree_level1(int pid, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		KnowledgeTree dep = null;
		try {
			dep = getDepRootByCid();
			ct = DBConnection.getConnection();
			dep.setChild(listKnowledgeTreeChildsByPId(dep.getId()));
		} catch (Exception e) {
			logger.error("获取下拉选项树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public KnowledgeTree getDepRootByCid() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		KnowledgeTree dep = new KnowledgeTree();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh from KnowledgeTree d   where d.parentid=0";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new KnowledgeTree(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setLid(rs.getInt(5));
				dep.setRid(rs.getInt(6));
				dep.setBh(rs.getString(7));
			}
		} catch (Exception e) {
			logger.error("查看知识类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public List<KnowledgeTree> listKnowledgeTreeChildsByPId(int parentid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<KnowledgeTree> deps = new ArrayList<KnowledgeTree>();
		KnowledgeTree dep = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
							"from KnowledgeTree d left join KnowledgeTree c on c.parentid = d.id  where d.parentid = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh order by d.id");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				dep = new KnowledgeTree(rs.getInt(1),rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setLid(rs.getInt(5));
				dep.setRid(rs.getInt(6));
				dep.setBh(rs.getString(7));
				dep.setClassCount(rs.getInt(8));
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("获取知识类别树出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}
	
	public KnowledgeTree getknowledgeTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		System.out.print("userid:"+userid+"     type--------------:"+type+"		stopid:"+stopid+"\t containStop"+containStop);
		
		
		KnowledgeTree dep = null;		
		if (type.equals("op")) {
			dep = new KnowledgeTree(ElConstants.USER_OP_LIB, "可操作的知识类别");
		} else {
			dep = new KnowledgeTree(ElConstants.USER_OP_LIB, "可使用的知识类别");
		};

		
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select KnowledgeTreeid from KnowledgeTree_" + type
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<KnowledgeTree> list = new ArrayList<KnowledgeTree>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					KnowledgeTree depc = getKnowledgeTreeById(rs.getInt(1));// getDepTree(rs.getInt(1),
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
			logger.error("查看知识类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	
	public boolean checkKnowledgeTreeBh(String bh) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.bh from KnowledgeTree d where d.bh =?");
			ps.setString(1, bh.trim());
			rs = ps.executeQuery();
			if (rs.next()) {
				// 存在
				return true;
			}
		} catch (Exception e) {
			logger.error("查看知识类别编号出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	public void addKnowledgeTree(KnowledgeTree klTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into KnowledgeTree (name,parentid,lid,rid,description,bh) values (?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, klTree.getName());
			ps.setInt(2, klTree.getParent().getId());
			ps.setInt(3, klTree.getLid());
			ps.setInt(4, klTree.getRid());
			ps.setString(5, klTree.getDescription());
			ps.setString(6, klTree.getBh());
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("select KnowledgeTree_sequence.currval from dual ");
			rs = ps.executeQuery();
			if (rs.next())
				klTree.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("插入知识类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public KnowledgeTree getKnowledgeTreeById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		KnowledgeTree dep = new KnowledgeTree();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
					" from knowledgetree d left join knowledgetree c on c.parentid = d.id  where d.id = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new KnowledgeTree(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParentid(rs.getInt(4));
				dep.setParent(new SelectLevel(rs.getInt(4)));
				dep.setLid(rs.getInt(5));
				dep.setRid(rs.getInt(6));
				dep.setBh(rs.getString(7));
				dep.setClassCount(rs.getInt(8));
			}
		} catch (Exception e) {
			logger.error("查看知识类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public List<ELUser> getOpUsers(String type, int selectlevelid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from KnowledgeTree_"
							+ type
							+ "_user du left join eluser eu on eu.id = du.userid where du.knowledgeTreeid = ?");
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
	
	
	
	public List<ELUser> getEUsByKnowledgeTreeid(int knowledgeTreeid) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select eu.id,eu.username from ELUSER eu where eu.knowledgeTreeid =?");
			ps.setInt(1, knowledgeTreeid);
			rs = ps.executeQuery();
			while (rs.next()) {
				eus.add(new ELUser(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			logger.error("知识类别用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}
	
	
	public void alterKnowledgeTree(KnowledgeTree klTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update KnowledgeTree set name=?, description=?,parentid=?,bh=? where id = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, klTree.getName());
			ps.setString(2, klTree.getDescription());
			ps.setInt(3, klTree.getParent().getId());
			ps.setString(4, klTree.getBh());
			ps.setInt(5, klTree.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改知识类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteDep(int depid, int klTreeParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//1.获取子节点(更新)
			this.updateDepParent(depid, klTreeParentid);
			//2.更新用户
			this.updateUserDep(depid, klTreeParentid);
			//3.删除该节点
			deleteDepNot(depid);
		} catch (Exception e) {
			logger.error("修改知识类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void updateDepParent(int depid,int depParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update knowledgeTree set parentid=? where parentid=?");
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
	
	public void updateUserDep(int depid,int depParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update eluser set knowledgeTreeid=? where knowledgeTreeid=?");
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
	
	
	public void deleteDepNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			SelectLevel dep=new SelectLevel();
			ps = ct.prepareStatement("select id,bh from knowledgeTree where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setBh(rs.getString(2));
			}
			rs.close();
			ps.close();
			ps = ct.prepareStatement("update eluser set valid=0 where knowledgeTreeid=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("update knowledgeTree set status=1,lid=0,rid=0,bh=? where id=?");
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
	
	public void deleteKnowledgeTreeAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			SelectLevel dep = new SelectLevel();
			ps = ct.prepareStatement("select d.id,d.lid,d.rid from KnowledgeTree d where d.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			ps = ct.prepareStatement("select id from KnowledgeTree where lid>=? and rid<=?");
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
	

}
