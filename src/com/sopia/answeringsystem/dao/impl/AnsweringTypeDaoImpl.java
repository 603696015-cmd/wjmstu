package com.sopia.answeringsystem.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.answeringsystem.dao.AnsweringTypeDao;
import com.sopia.answeringsystem.entities.AnsweringType;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public class AnsweringTypeDaoImpl implements AnsweringTypeDao {
	private static final Log logger = LogFactory.getLog(AnsweringTypeDaoImpl.class);
	
	public AnsweringType getAnsweringTypeTree_level1(int pid, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		AnsweringType dep = null;
		try {
			dep = getAnsweringTypeTreeRootByCid();
			ct = DBConnection.getConnection();
			dep.setChild(listAnsweringTypeTreeChildsByPId(dep.getId()));
		} catch (Exception e) {
			logger.error("获取问答树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public AnsweringType getAnsweringTypeTreeRootByCid() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		AnsweringType dep = new AnsweringType();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh from AnsweringType d   where d.parentid=0";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new AnsweringType(rs.getInt(1), rs.getString(2));
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
	
	public List<AnsweringType> listAnsweringTypeTreeChildsByPId(int parentid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<AnsweringType> deps = new ArrayList<AnsweringType>();
		AnsweringType dep = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
							"from AnsweringType d left join AnsweringType c on c.parentid = d.id  where d.parentid = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh order by d.id");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				dep = new AnsweringType(rs.getInt(1),rs.getString(2));
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
	
	public AnsweringType getAnsweringTypeTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		System.out.print("userid:"+userid+"     type--------------:"+type+"		stopid:"+stopid+"\t containStop"+containStop);
		
		
		AnsweringType dep = null;		
		if (type.equals("op")) {
			dep = new AnsweringType(ElConstants.USER_OP_LIB, "可操作的问答类别");
		} else {
			dep = new AnsweringType(ElConstants.USER_OP_LIB, "可使用的问答类别");
		};

		
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select KnowledgeTreeid from AnsweringType_" + type
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<AnsweringType> list = new ArrayList<AnsweringType>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					AnsweringType depc = getAnsweringTypeTreeById(rs.getInt(1));// getDepTree(rs.getInt(1),
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
	
	public AnsweringType getAnsweringTypeTreeById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		AnsweringType dep = new AnsweringType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh,count(c.id) " +
					" from AnsweringType d left join AnsweringType c on c.parentid = d.id  where d.id = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.lid,d.rid,d.bh");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new AnsweringType(rs.getInt(1), rs.getString(2));
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
	
	
	public boolean checkAnsweringTypeTreeBh(String bh) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.bh from AnsweringType d where d.bh =?");
			ps.setString(1, bh.trim());
			rs = ps.executeQuery();
			if (rs.next()) {
				// 存在
				return true;
			}
		} catch (Exception e) {
			logger.error("查看问答类别编号出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	public void addAnsweringTypeTree(AnsweringType answeringType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into answeringType (name,parentid,lid,rid,description,bh) values (?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, answeringType.getName());
			ps.setInt(2, answeringType.getParent().getId());
			ps.setInt(3, answeringType.getLid());
			ps.setInt(4, answeringType.getRid());
			ps.setString(5, answeringType.getDescription());
			ps.setString(6, answeringType.getBh());
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("select answeringType_sequence.currval from dual ");
			rs = ps.executeQuery();
			if (rs.next())
				answeringType.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("插入问答类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public List<ELUser> getOpUsers(String type, int answeringTypeTreeid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from answeringType_"
							+ type
							+ "_user du left join eluser eu on eu.id = du.userid where du.answeringTypeTreeid = ?");
			ps.setInt(1, answeringTypeTreeid);
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
	
	public List<ELUser> getEUsByAnsweringTypeTreeid(int answeringTypeTreeid) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select eu.id,eu.username from ELUSER eu where eu.answeringTypeTreeid =?");
			ps.setInt(1, answeringTypeTreeid);
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
	
	public void alterAnsweringTypeTree(AnsweringType answeringType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update answeringType set name=?, description=?,parentid=?,bh=? where id = ?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, answeringType.getName());
			ps.setString(2, answeringType.getDescription());
			ps.setInt(3, answeringType.getParent().getId());
			ps.setString(4, answeringType.getBh());
			ps.setInt(5, answeringType.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改问答类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteAnsweringTypeTreeAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			SelectLevel dep = new SelectLevel();
			ps = ct.prepareStatement("select d.id,d.lid,d.rid from AnsweringType d where d.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			ps = ct.prepareStatement("select id from AnsweringType where lid>=? and rid<=?");
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
	
	public void updateDepParent(int depid,int depParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update AnsweringType set parentid=? where parentid=?");
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
			ps = ct.prepareStatement("update eluser set AnsweringTypeTreeid=? where AnsweringTypeTreeid=?");
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
			ps = ct.prepareStatement("select id,bh from AnsweringType where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setBh(rs.getString(2));
			}
			rs.close();
			ps.close();
			ps = ct.prepareStatement("update eluser set valid=0 where AnsweringTypeTreeid=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("update AnsweringType set status=1,lid=0,rid=0,bh=? where id=?");
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

	public void deleteDep(int depid, int answeringTypeTreeParentid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//1.获取子节点(更新)
			this.updateDepParent(depid, answeringTypeTreeParentid);
			//2.更新用户
			this.updateUserDep(depid, answeringTypeTreeParentid);
			//3.删除该节点
			deleteDepNot(depid);
		} catch (Exception e) {
			logger.error("修改知识类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public List<AnsweringType> listAllAnsweringTypes() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<AnsweringType> answeringTypes = new ArrayList<AnsweringType>();
		AnsweringType answeringType = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name,parentid,lid,rid,description,bh,status from answeringtype where id!=1 and status!=1");
			rs = ps.executeQuery();
			while(rs.next()){
				answeringType = new AnsweringType(rs.getInt(1),rs.getString(2));
				answeringType.setParentid(rs.getInt(3));
				answeringType.setLid(rs.getInt(4));
				answeringType.setRid(rs.getInt(5));
				answeringType.setDescription(rs.getString(6));
				answeringType.setBh(rs.getString(7));
				answeringType.setStatus(rs.getInt(8));
				answeringTypes.add(answeringType);
			}
		} catch (Exception e) {
			logger.error("获取所有问答类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return answeringTypes;
	}

	public int getCountById(int lid, int rid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from ques where answeringtypeid in (select id from answeringtype where lid>=? and lid<=?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1,lid);
			ps.setInt(2,rid);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取二级节点数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

}
