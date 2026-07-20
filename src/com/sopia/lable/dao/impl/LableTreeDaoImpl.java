package com.sopia.lable.dao.impl;

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
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.lable.dao.LableTreeDao;
import com.sopia.lable.entites.LableTree;
import com.sopia.schedule.dao.impl.xialajibie.SelectLevelDaoImpl;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public class LableTreeDaoImpl implements LableTreeDao {
	private static final Log logger = LogFactory
	.getLog(LableTreeDaoImpl.class);
	
	public List<LableTree> listdepChildsByPId(int parentid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LableTree> deps = new ArrayList<LableTree>();
		LableTree dep = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
							"from labletree d left join labletree c on c.parentid = d.id  where d.parentid = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh order by d.id");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				dep = new LableTree(rs.getInt(1),rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setLid(rs.getInt(5));
				dep.setRid(rs.getInt(6));
				dep.setBh(rs.getString(7));
				dep.setClassCount(rs.getInt(8));
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("获取lable树错误！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}
	
	public LableTree getLableTree_level1(int pid, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		LableTree dep = null;
		try {
			dep = getDepRootByCid();
			ct = DBConnection.getConnection();
			dep.setChild(listLableTreeChildsByPId(dep.getId()));
		} catch (Exception e) {
			logger.error("获取下拉选项树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public LableTree getLableTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		System.out.print("userid:"+userid+"     type--------------:"+type+"		stopid:"+stopid+"\t containStop"+containStop);
		
		
		LableTree dep = null;		
		if (type.equals("op")) {
			dep = new LableTree(ElConstants.USER_OP_LIB, "可操作的自定义类别树");
		} else {
			dep = new LableTree(ElConstants.USER_OP_LIB, "可使用的自定义类别树");
		};

		
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select KnowledgeTreeid from KnowledgeTree_" + type
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<LableTree> list = new ArrayList<LableTree>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					LableTree depc = getLableTreeById(rs.getInt(1));// getDepTree(rs.getInt(1),
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
	
	public LableTree getDepRootByCid() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		LableTree dep = new LableTree();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh from LableTree d   where d.parentid=0";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new LableTree(rs.getInt(1), rs.getString(2));
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
	
	public List<LableTree> listLableTreeChildsByPId(int parentid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<LableTree> deps = new ArrayList<LableTree>();
		LableTree dep = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
							"from LableTree d left join LableTree c on c.parentid = d.id  where d.parentid = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh order by d.id");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				dep = new LableTree(rs.getInt(1),rs.getString(2));
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
	

	public boolean checkLableTreeBh(String bh) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.bh from LableTree d where d.bh =?");
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
	
	
	public void addLableTree(LableTree lTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into LableTree (name,parentid,lid,rid,description,bh) values (?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, lTree.getName());
			ps.setInt(2, lTree.getParent().getId());
			ps.setInt(3, lTree.getLid());
			ps.setInt(4, lTree.getRid());
			ps.setString(5, lTree.getDescription());
			ps.setString(6, lTree.getBh());
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("select LableTree_sequence.currval from dual ");
			rs = ps.executeQuery();
			if (rs.next())
				lTree.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("插入知识类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public LableTree getLableTreeById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		LableTree dep = new LableTree();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
					" from LableTree d left join LableTree c on c.parentid = d.id  where d.id = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new LableTree(rs.getInt(1), rs.getString(2));
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
	
	public void alterLableTree(LableTree lTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update LableTree set name=?, description=?,parentid=?,bh=? where id = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, lTree.getName());
			ps.setString(2, lTree.getDescription());
			ps.setInt(3, lTree.getParent().getId());
			ps.setString(4, lTree.getBh());
			ps.setInt(5, lTree.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改知识类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void deleteDep(int depid, int lTreeParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//1.获取子节点(更新)
			this.updateDepParent(depid, lTreeParentid);
//			//2.更新用户
//			this.updateUserDep(depid, klTreeParentid);
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
			ps = ct.prepareStatement("update labletree set parentid=? where parentid=?");
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
	
	
	public void deleteDepNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			SelectLevel dep=new SelectLevel();
			ps = ct.prepareStatement("select id,bh from labletree where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setBh(rs.getString(2));
			}
			rs.close();
			ps.close();
//			ps = ct.prepareStatement("update eluser set valid=0 where knowledgeTreeid=?");
//			ps.setInt(1, id);
//			ps.executeUpdate();
//			ps.close();
			ps = ct.prepareStatement("update labletree set status=1,lid=0,rid=0,bh=? where id=?");
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
	
	
	public void deleteLableTreeAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			SelectLevel dep = new SelectLevel();
			ps = ct.prepareStatement("select d.id,d.lid,d.rid from LableTree d where d.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			ps = ct.prepareStatement("select id from LableTree where lid>=? and rid<=?");
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
