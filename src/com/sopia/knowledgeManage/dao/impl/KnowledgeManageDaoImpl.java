package com.sopia.knowledgeManage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.DepartmentDao;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeManage.KnowledgeManageUtil;
import com.sopia.knowledgeManage.dao.KnowledgeManageDao;
import com.sopia.knowledgeManage.entities.Competence;
import com.sopia.knowledgeManage.entities.Kledge;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.schedule.entities.AuditMark;

public class KnowledgeManageDaoImpl implements KnowledgeManageDao{
	private static final Log logger = LogFactory.getLog(KnowledgeManageDaoImpl.class);
	
	public int addKledge(Kledge kledge) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int id=0;
		try {
			ct = DBConnection.getConnection();
			sql = "insert into knowledgemanage (name,knowledgetreeid,begintime,endtime,depname,zhizuoren,fabuuserid,fabutime,xiugaiuserid,xiugaitime,fujian,jianjie) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,empty_blob())";
			ps = ct.prepareStatement(sql);
			ps.setString(1, kledge.getName());
			ps.setInt(2, kledge.getKlTree().getId());
			ps.setTimestamp(3, kledge.getBegintime());
			ps.setTimestamp(4, kledge.getEndtime());
			ps.setString(5, kledge.getDepname());
			ps.setString(6, kledge.getZhizuoren());
			ps.setInt(7, kledge.getFabuuserid());
			ps.setTimestamp(8, kledge.getFabutime());
			ps.setInt(9, kledge.getXiugaiuserid());
			ps.setTimestamp(10, kledge.getXiugaitime());
			ps.setString(11, kledge.getFujian());
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob(ct,"knowledgemanage_sequence","knowledgemanage","id","jianjie",kledge.getJianjie(),"添加知识失败");
			setblob.addContent(); 
			
			ps = ct.prepareStatement("select knowledgemanage_sequence.currval from dual");
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("添加知识失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	
	public int updateKledge(Kledge kledge) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			//name,knowledgetreeid,begintime,endtime,depname,zhizuoren,fabuuserid,fabutime,xiugaiuserid,xiugaitime,fujian,jianjie
			sql = "update knowledgemanage set " +
					"name=?,knowledgetreeid=?,begintime=?,endtime=?,depname=?," +
					"zhizuoren=?,xiugaiuserid=?,xiugaitime=?,fujian=?,jianjie=empty_blob() " +
					"where id=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, kledge.getName());
			ps.setInt(2, kledge.getKlTree().getId());
			ps.setTimestamp(3, kledge.getBegintime());
			ps.setTimestamp(4, kledge.getEndtime());
			ps.setString(5, kledge.getDepname());
			ps.setString(6, kledge.getZhizuoren());
			ps.setInt(7, kledge.getXiugaiuserid());
			ps.setTimestamp(8, kledge.getXiugaitime());
			ps.setString(9, kledge.getFujian());
			ps.setInt(10, kledge.getId());
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob("knowledgemanage","id",kledge.getId()+"","jianjie",kledge.getJianjie(),"修改知识失败",ct);
			setblob.updateContent();  
			
			
			
		} catch (Exception e) {
			logger.error("修改知识失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kledge.getId();
	}

	public void addCompetenceByDepartmentid(String tablename, int id,int depid,
			int competenceType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into " + tablename + " (knowledgemanageid,depid,type) values (?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, depid);
			ps.setInt(3, competenceType);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("添加权限失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void addCompetenceByUserid(String tablename, int id,int userid,
			int competenceType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into " + tablename + " (knowledgemanageid,userid,type) values (?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, userid);
			ps.setInt(3, competenceType);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("添加权限失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<Kledge> listMyKledge(int userid, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Kledge> kledges = new ArrayList<Kledge>();
		Kledge kl = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from (select a.*,rownum rn from (select km.id,km.name,km.knowledgetreeid,km.begintime,km.endtime," +
					"km.depname,km.zhizuoren,km.fabuuserid,km.fabutime,km.xiugaiuserid,km.xiugaitime,km.fujian,kt.id as ktid,kt.name as ktname,km.status,km.jianjie " +
					" from knowledgemanage km join knowledgetree kt on km.knowledgetreeid=kt.id where fabuuserid=? ) a where rownum<=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				kl = new Kledge();
				kl.setId(rs.getInt(1));
				kl.setName(rs.getString(2));
				kl.setKnowledgeTreeid(rs.getInt(3));
				kl.setBegintime(rs.getTimestamp(4));
				kl.setEndtime(rs.getTimestamp(5));
				kl.setDepname(rs.getString(6));
				kl.setZhizuoren(rs.getString(7));
				kl.setFabuuserid(rs.getInt(8));
				kl.setFabutime(rs.getTimestamp(9));
				kl.setXiugaiuserid(rs.getInt(10));
				kl.setXiugaitime(rs.getTimestamp(11));
				kl.setFujian(rs.getString(12));
				kl.setKlTree(new KnowledgeTree(rs.getInt(13),rs.getString(14)));
				kl.setStatus(rs.getInt(15));
				kl.setJianjie(rs.getString(16));
				kl.setFabuUser(new UserDaoImpl().getUserById(kl.getFabuuserid()));
				kl.setXiugaiUser(new UserDaoImpl().getUserById(kl.getXiugaiuserid()));
				kledges.add(kl);
			}
			
		} catch (Exception e) {
			logger.error("我添加的知识", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kledges;
	}

	public int listMyKledgeSize(int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from knowledgemanage km join knowledgetree kt on km.knowledgetreeid=kt.id where fabuuserid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("我添加的知识Size", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public Kledge getKledgeById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Kledge kl = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select km.id,km.name,km.knowledgetreeid,km.begintime,km.endtime," +
			"km.depname,km.zhizuoren,km.fabuuserid,km.fabutime,km.xiugaiuserid,km.xiugaitime,km.fujian,kt.id as ktid,kt.name as ktname,km.status,km.jianjie " +
			"from knowledgemanage km join knowledgetree kt on km.knowledgetreeid=kt.id where km.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				kl = new Kledge();
//				UserDao userDao = new UserDaoImpl();
				kl.setId(rs.getInt(1));
				kl.setName(rs.getString(2));
				kl.setKnowledgeTreeid(rs.getInt(3));
				kl.setBegintime(rs.getTimestamp(4));
				kl.setEndtime(rs.getTimestamp(5));
				kl.setDepname(rs.getString(6));
				kl.setZhizuoren(rs.getString(7));
				kl.setFabuuserid(rs.getInt(8));
				kl.setFabutime(rs.getTimestamp(9));
				kl.setXiugaiuserid(rs.getInt(10));
				kl.setXiugaitime(rs.getTimestamp(11));
				kl.setFujian(rs.getString(12));
				kl.setKlTree(new KnowledgeTree(rs.getInt(13),rs.getString(14)));
				kl.setStatus(rs.getInt(15));
				kl.setJianjie(new OracleBlob().getContent(rs.getBlob(16)));
//				kl.setFabuUser(userDao.getUserById(kl.getFabuuserid()));
//				kl.setXiugaiUser(userDao.getUserById(kl.getXiugaiuserid()));
//				kl.setCt_views(this.getCompetencesById(id, KnowledgeManageConstants.VIEW_TABLE, KnowledgeManageConstants.VIEW_COMPETENCETYPE));
//				kl.setCt_updates(this.getCompetencesById(id, KnowledgeManageConstants.UPDATE_TABLE, KnowledgeManageConstants.UPDATE_COMPETENCETYPE));
//				kl.setCt_deletes(this.getCompetencesById(id, KnowledgeManageConstants.DELETE_TABLE, KnowledgeManageConstants.DELETE_COMPETENCETYPE));
//				kl.setCt_copys(this.getCompetencesById(id, KnowledgeManageConstants.COPY_TABLE, KnowledgeManageConstants.COPY_COMPETENCETYPE));
//				kl.setCt_downloads(this.getCompetencesById(id, KnowledgeManageConstants.DOWNLOAD_TABLE, KnowledgeManageConstants.DOWNLOAD_COMPETENCETYPE));
			}
			
		} catch (Exception e) {
			logger.error("根据id查看知识", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kl;
	}

	public Competence getCompetenceByUseridOrDepid(ELUser elUser,
			String competenceTable,int competenceType,String sqlappend) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection ct2 = null;
		Competence competence = null;
		String sql = "";
		String sql2 = "";
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			
			//先查询userid、在查询depid
			sql = "select * from "+competenceTable+" where type="+competenceType+" and userid="+elUser.getId();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				flag = true;
				competence = new Competence(true);
				competence.setUserid(rs.getInt("userid"));
				competence.setType(competenceType);
			}
			DBConnection.closeConnectInfo(ct, ps, rs);
			if(!flag){
				ct = DBConnection.getConnection();
				if(sqlappend!=null && !sqlappend.trim().equals("")){
					sql = "select id from eluser where depid in ( " + sqlappend + ")";
					ps = ct.prepareStatement(sql);
					rs = ps.executeQuery();
					while(rs.next()){
						if(rs.getInt(1) == elUser.getId()){
							competence = new Competence(true);
//							competence.setDepid(rs.getInt("depid"));
							competence.setType(competenceType);
							break;
						}
					}
				}
//				sql = "select * from "+competenceTable + "  where type="+competenceType+" and depid= "+elUser.getDepartment().getId() ;
//				ps = ct.prepareStatement(sql);
//				rs = ps.executeQuery();
//				if(rs.next()){
//					competence = new Competence(true);
//					competence.setDepid(rs.getInt("depid"));
//					competence.setType(competenceType);
//				}
			}
			competence = competence==null?new Competence(false):competence;
		} catch (Exception e) {
			logger.error("查看权限知识", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return competence;
	}

	public void deleteKledgeById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "{call deleteknowledgemanage(?)}";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
//			sql = "delete from knowledgemanage where id=" + id;
			//删除知识的时候，同时删除权限表相关数据
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("删除知识失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void deleteCompetenceById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "{call delcompetence(?)}";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			//修改前删除之前的授权信息
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("删除知识失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<Competence> getCompetencesById(int id,
			String competenceTable, int competenceType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Competence> cts = new ArrayList<Competence>();
		Competence competence = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			
			sql = "select * from "+competenceTable+" where type="+competenceType + " and knowledgemanageid="+id;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				competence = new Competence();
				competence.setType(competenceType);
				competence.setUserid(rs.getInt("userid"));
				if(competence.getUserid()!=0){
					competence.setEu(new UserDaoImpl().getUserById(competence.getUserid()));
				}
				competence.setDepid(rs.getInt("depid"));
				if(competence.getDepid()!=0){
					competence.setDt(new DepartmentDaoImpl().getDepById(competence.getDepid()));
				}
//				competence.setUserCount(this.getCompetencesSize(id, competenceType));
				cts.add(competence);
			}
			
		} catch (Exception e) {
			logger.error("查看权限知识", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cts;
	}


	public List<Kledge> listKledgeAll(ElNode department,int status,int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Kledge> kledges = new ArrayList<Kledge>();
		Kledge kl = null;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(status != -1){
				sqlwhere = " and km.status=" +status + " " ;
			}
			sql = "select b.*,rn from (select a.*,rownum rn from (select km.id,km.name,km.knowledgetreeid,km.begintime,km.endtime," +
					"km.depname,km.zhizuoren,km.fabuuserid,km.fabutime,km.xiugaiuserid,km.xiugaitime,km.fujian,kt.id as ktid,kt.name as ktname,km.status,km.jianjie,km.hot " +
					" from knowledgemanage km ,eluser eu,department dt,knowledgetree kt," +
					"  ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)
					+ ") dep  " +
					" where km.knowledgetreeid=kt.id and eu.id=km.fabuuserid and eu.depid=dt.id and dep.id=dt.id " + sqlwhere + 
					") a where rownum<=?) b where rn>=?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				kl = new Kledge();
				kl.setId(rs.getInt(1));
				kl.setName(rs.getString(2));
				kl.setKnowledgeTreeid(rs.getInt(3));
				kl.setBegintime(rs.getTimestamp(4));
				kl.setEndtime(rs.getTimestamp(5));
				kl.setDepname(rs.getString(6));
				kl.setZhizuoren(rs.getString(7));
				kl.setFabuuserid(rs.getInt(8));
				kl.setFabutime(rs.getTimestamp(9));
				kl.setXiugaiuserid(rs.getInt(10));
				kl.setXiugaitime(rs.getTimestamp(11));
				kl.setFujian(rs.getString(12));
				kl.setKlTree(new KnowledgeTree(rs.getInt(13),rs.getString(14)));
				kl.setStatus(rs.getInt(15));
				kl.setJianjie(rs.getString(16));
				kl.setHot(rs.getInt(17));
				kl.setFabuUser(new UserDaoImpl().getUserById(kl.getFabuuserid()));
				kl.setXiugaiUser(new UserDaoImpl().getUserById(kl.getXiugaiuserid()));
				kledges.add(kl);
			}
			
		} catch (Exception e) {
			logger.error("全部知识", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kledges;
	}


	public int listKledgeAllSize(ElNode department,int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(status != -1){
				sqlwhere = " and km.status=" +status + " " ;
			}
			sql = "select count(1) from knowledgemanage km ,eluser eu,department dt,knowledgetree kt, " +
			"  ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ ") dep  " +
			" where km.knowledgetreeid=kt.id and eu.id=km.fabuuserid and eu.depid=dt.id and  dep.id=dt.id " + sqlwhere;
			
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("全部知识Size", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	public List<Kledge> listKledgeSearch(String ids,int pageNow, int pageSize)
		throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Kledge> kledges = new ArrayList<Kledge>();
		Kledge kl = null;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(ids!=null && !ids.equals("")){
				sqlwhere = " and km.id in ("+ids+") ";
			}else{
				sqlwhere = " and 1 != 1 ";
			}
			sql = "select b.*,rn from (select a.*,rownum rn from (select km.id,km.name,km.knowledgetreeid,km.begintime,km.endtime," +
					"km.depname,km.zhizuoren,km.fabuuserid,km.fabutime,km.xiugaiuserid,km.xiugaitime,km.fujian,kt.id as ktid,kt.name as ktname,km.status,km.jianjie " +
					" from knowledgemanage km join knowledgetree kt on km.knowledgetreeid=kt.id " +
					" where km.status=9 "+ sqlwhere + 
					"  ) a where rownum<=?) b where rn>=?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			while(rs.next()){
				kl = new Kledge();
				kl.setId(rs.getInt(1));
				kl.setName(rs.getString(2));
				kl.setKnowledgeTreeid(rs.getInt(3));
				kl.setBegintime(rs.getTimestamp(4));
				kl.setEndtime(rs.getTimestamp(5));
				kl.setDepname(rs.getString(6));
				kl.setZhizuoren(rs.getString(7));
				kl.setFabuuserid(rs.getInt(8));
				kl.setFabutime(rs.getTimestamp(9));
				kl.setXiugaiuserid(rs.getInt(10));
				kl.setXiugaitime(rs.getTimestamp(11));
				kl.setFujian(rs.getString(12));
				kl.setKlTree(new KnowledgeTree(rs.getInt(13),rs.getString(14)));
				kl.setStatus(rs.getInt(15));
				kl.setJianjie(rs.getString(16));
				kl.setFabuUser(new UserDaoImpl().getUserById(kl.getFabuuserid()));
				kl.setXiugaiUser(new UserDaoImpl().getUserById(kl.getXiugaiuserid()));
				kledges.add(kl);
			}
			
		} catch (Exception e) {
			logger.error("获得授权知识", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return kledges;
	}


	public int listKledgeSearchSize(String ids) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		String sql = "";
		String sqlwhere = "";
		try {
			ct = DBConnection.getConnection();
			if(ids!=null && !ids.equals("")){
				sqlwhere = " and km.id in ("+ids+") ";
			}else{
				sqlwhere = " and 1 != 1 ";
			}
			sql = "select count(1) from knowledgemanage km join knowledgetree kt on km.knowledgetreeid=kt.id where km.status=9 " + sqlwhere;
			
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("获取授权知识Size", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}



	public Map<String,String> getCompetencesSizeByDepid(int id,String competenceTable,int competenceType) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int depid = 0;
		int count = 0;
		String userids = "";
		String sql = "";
		DepartmentDao departmentDao = new DepartmentDaoImpl();
		Department dep = null;
		Map<String,String> map = new HashMap<String,String>();
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			sql = "select depid from "+competenceTable+"  where knowledgemanageid="+id+" and type="+competenceType + " and depid is not null " ;
			
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				//如果部门包含（即例如数据库中保存两条部门相关记录，一个部门属于另一个部门，那么人数如何计算）
				depid = rs.getInt(1);
				if(depid != 0){
					deps.add(departmentDao.getDepById(depid));
				}
			}
			DBConnection.closeConnectInfo(ct, ps, rs);
			
			sql = KnowledgeManageUtil.getSqlByDeps(deps);
			if(sql!=null&&!sql.trim().equals("")){
				count = this.getUserCountByDepid(sql);
				userids = this.getUseidsByDepid(sql);
			}
			
			if(userids!=null&&!userids.equals("")&&String.valueOf(userids.charAt(userids.length()-1)).equals(",")){
				userids = userids.substring(0,userids.lastIndexOf(","));
			}
			map.put("count", ""+count);
			map.put("userids", userids);
			
		} catch (Exception e) {
			logger.error("权限数量", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return map;
	}
	
	public int getUserCountByDepid(String sql_) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1) from eluser e " +
					" where e.depid in (" + sql_ + ")";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("部门下人数", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	public String getUseidsByDepid(String sql_) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String userids = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select e.id  from eluser e" +
					" where depid in (" + sql_ + ")";
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				userids += rs.getInt("id")+",";
			}
			
		} catch (Exception e) {
			logger.error("部门下人数", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userids;
	}
	
	public void changeStatus(int id,int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "update knowledgemanage " + " set status=? "
					+ " where id=? ";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, status);
			ps.setInt(2, id);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("按钮操作更改状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}


	public void addMark(int id, AuditMark auditMark, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "insert into tb_audit_mark (moduleid,entityid,auditmark,time,status,ord) " +
					"values (?,?,?,?,?,?)";

			ps = ct.prepareStatement(sql);

			ps.setString(1, auditMark.getModuleid());
			ps.setInt(2, auditMark.getEntityid());
			ps.setString(3, auditMark.getAudit_mark()==null?"":auditMark.getAudit_mark());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			ps.setInt(5, status);
			if(status == 6 || status == 7){
				ps.setString(6, "初审");
			}else if(status == 9 || status == 10){
				ps.setString(6, "终审");
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("添加备注出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}


	public List<AuditMark> getKnowledgeMark(String tablename, int id,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<AuditMark> list = new ArrayList<AuditMark>();
		AuditMark am = null;
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from " +
					" (select a.* ,rownum rn from " +
					" (select tam.id,tam.moduleid,tam.entityid,tam.auditmark,tam.time,tam.status,tam.ord" +
					" from tb_audit_mark tam " +
					" where   tam.entityid=" + id + " and tam.moduleid='"+tablename+"' "+
							" order by time desc ) a where rownum <=?) b where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			
			int status = 0;
			while(rs.next()){
				am = new AuditMark();
				am.setId(rs.getInt("id"));
				am.setModuleid(rs.getString("moduleid"));
				am.setEntityid(rs.getInt("entityid"));
				am.setAudit_mark(rs.getString("auditmark"));
				am.setAudittime(rs.getTimestamp("time"));
				am.setAuditName_chinese(rs.getString("ord"));
				status = rs.getInt("status");
				am.setStatus(status);
				
				if(status == 0){am.setStatus_chinese("已创建");}
				if(status == 2){am.setStatus_chinese("修改等待中");}
				if(status == 3){am.setStatus_chinese("删除等待中");}
				if(status == 5){am.setStatus_chinese("初审等待中");}
				if(status == 6){am.setStatus_chinese("初审通过");}
				if(status == 7){am.setStatus_chinese("初审不通过");}
				if(status == 8){am.setStatus_chinese("终审等待中");}
				if(status == 9){am.setStatus_chinese("终审通过");}
				if(status == 10){am.setStatus_chinese("终审不通过");}
				
				list.add(am);
			}
			
		} catch (Exception e) {
			logger.error("根据表名查询多级审核信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}


	public int getKnowledgeMarkSize(String tablename, int id)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			sql = " select count(1) from tb_audit_mark tam " +
					" where   tam.entityid= "+id + " and tam.moduleid='" + tablename + "'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("根据表名查询多级审核信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	//复制知识
	public int knowledgeManageCopy(int id,int userid) throws ElException {
		Kledge  kledge = this.getKledgeById(id);
		if(kledge!=null){
			kledge.setName(kledge.getName() + "_副本");
			kledge.setFabutime(new Timestamp(System.currentTimeMillis()));
			kledge.setFabuuserid(userid);
			kledge.setXiugaitime(null);
			kledge.setXiugaiuserid(0);
		}
		int status = this.addKledge(kledge);
		//插入对应的权限表
		List<Competence> cts = null;
		Competence ct = null;
		String competenceTable = "";
		for(int i=1;i<6;i++){
			competenceTable = KnowledgeManageUtil.getCompetenceTableByCompetenceType(i);
			cts = this.getCompetencesById(id, competenceTable, i);
			if(cts!=null){
				for(int j=0;j<cts.size();j++){
					ct = cts.get(j);
					if(ct.getDepid()==0){//插入userid
						this.addCompetenceByUserid(competenceTable, status, ct.getUserid(), i);
					}else if(ct.getUserid() == 0){//插入depid
						this.addCompetenceByDepartmentid(competenceTable, status, ct.getDepid(), i);
					}
				}
			}
		}
		return status;
	}


	public String getKledgeByCompetence(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String ids = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select knowledgemanageid from viewcompetence where userid="+userid +
				" union all " +
				"	select knowledgemanageid from updatecompetence where userid="+userid +
				" union all " +
				"	select knowledgemanageid from deletecompetence where userid="+userid +
				" union all " +
				"	select knowledgemanageid from copycompetence where userid="+userid +
				" union all " +
				"	select knowledgemanageid from downloadcompetence where userid="+userid ;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				ids += rs.getInt("knowledgemanageid")+",";
			}
			
		} catch (Exception e) {
			logger.error("本人获得授权的知识ids出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ids;
	}
	
	public String getKledgeByCompetence1(Department dep,DepartmentDao departmentDao) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String ids = "";
		int departmentid = 0;
//		Department dep = null;
//		DepartmentDao departmentDao = new DepartmentDaoImpl();
		Department d = null;
		try {
			ct = DBConnection.getConnection();
			sql = " select knowledgemanageid,depid from viewcompetence where depid is not null "+
				" union all" +
				"	select knowledgemanageid,depid from updatecompetence where depid is not null " +
				" union all" +
				"	select knowledgemanageid,depid from deletecompetence where depid is not null " +
				" union all" +
				"	select knowledgemanageid,depid from copycompetence where depid is not null " +
				" union all" +
				"	select knowledgemanageid,depid from downloadcompetence where depid is not null " ;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				departmentid = rs.getInt("depid");
				if(departmentid!=0){
					d = departmentDao.getDepById(departmentid);
					if(dep.getLid()>=d.getLid() && dep.getRid()<=d.getRid()){
						ids += rs.getInt("knowledgemanageid");
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("所在部门获得授权的知识ids！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ids;
	}


	public void kledgeHotSet(int id, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update KNOWLEDGEMANAGE set hot=? where id=?");
			ps.setInt(1, hot);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

}
