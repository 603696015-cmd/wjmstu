package com.sopia.pfms.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.MD5;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.entities.Area;
import com.sopia.pfms.entities.PfmsUser;

public class IndexDaoImpl implements IndexDao {
	private static final Log logger = LogFactory.getLog(IndexDaoImpl.class);
	
	public PfmsUser getUser(int id,boolean show_some_user_note) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PfmsUser pfmsUser = new PfmsUser();
		String note = "";
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "SELECT ELU.*,PFU.*,el.id as elid,el.name as elname,d.id as did,d.name as dname" +
			" FROM ELUSER ELU " +
			" JOIN PFMSUSER PFU ON ELU.ID=PFU.USERID " +
			" join elrole el on elu.role=el.id " +
			" join department d on elu.depid=d.id " +
			" where ELU.ID=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {   
				if(show_some_user_note){//取部分简介
					note = new OracleBlob().getContent(rs.getBlob("note"));
					note = CheckHtml.getString(note);
					pfmsUser.setNote((note.length() > 21) ? note.substring(0, 19)+ "..." : note);
				}else{//取滤掉HTML后简介
//					if(type.equals("check")){
//						pfmsUser.setNote(CheckHtml.getString(new OracleBlob().getContent(rs.getBlob("note"))));
//					}else{//取全部简介
						pfmsUser.setNote(new OracleBlob().getContent(rs.getBlob("note")));
//					}
				}
				pfmsUser.setId(rs.getInt("id"));
				pfmsUser.setAddress(rs.getString("ADDRESS"));
				pfmsUser.setEmail(rs.getString("EMAIL"));
				pfmsUser.setFex(rs.getString("FEX"));
				pfmsUser.setMobile(rs.getString("MOBILE"));
				pfmsUser.setRespName(rs.getString("RESPNAME"));
				pfmsUser.setHuiyuandanwei(rs.getString("huiyuandanwei"));
				pfmsUser.setHuiyuanleixing(rs.getString("huiyuanleixing"));
				pfmsUser.setUserId(rs.getInt("USERID"));
				pfmsUser.setProvince_city_county(rs.getString("province_city_county"));
				pfmsUser.setYingyezhizhao(rs.getString("yingyezhizhao"));
				pfmsUser.setShuiwudengjizheng(rs.getString("shuiwudengjizhengshu"));
				pfmsUser.setZizhidengjizhengshu(rs.getString("zizhidengjizhengshu"));
				pfmsUser.setXinyongdengjipingguzhengshu(rs.getString("xinyongdengjipingguzhengshu"));
				pfmsUser.setQitazhengshu(rs.getString("qitazhengshu"));
				pfmsUser.setZuzhijigoudaimazheng(rs.getString("zuzhijigoudaimazheng"));
				pfmsUser.setFarenshenfenzheng(rs.getString("farenshenfenzheng"));
				
				pfmsUser.setBanner(rs.getString("banner"));
				pfmsUser.setLogo(rs.getString("logo"));
				pfmsUser.setDianpujianjietupian(rs.getString("dianpujianjietupian"));
				pfmsUser.setHead(rs.getString("head"));
				ELUser elUser = new ELUser(id,rs.getString("realname"),rs.getString("username"),
						rs.getString("sex"),rs.getString("shenfenzheng"),
						rs.getString("movephone"),rs.getString("danwei"));
				elUser.setPassword(rs.getString("password"));
				ElRole role = new ElRole();
				role.setId(rs.getInt("elid"));
				role.setName(rs.getString("elname"));
				elUser.setRole(role);
				Department d = new Department();
				d.setId(rs.getInt("did"));
				d.setName(rs.getString("dname"));
				elUser.setDepartment(d);
				
				pfmsUser.setUser(elUser);
			}
		} catch (Exception e) {
			logger.error("查询会员信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pfmsUser;
	}

	@SuppressWarnings("static-access")
	public void alterPassword(String newpassword,int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update eluser set PASSWORD = ? WHERE  ID = ?");
			ps.setString(1, new MD5().crypt(newpassword));
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void alterMemberProfile(PfmsUser pfmsUser, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update PFMSUSER SET NOTE = empty_blob() WHERE USERID = ?"); 
			ps.setInt(1, id);
			ps.executeUpdate();  
//			OracleBlob setblob = new OracleBlob(ct,"PFMSUSER_SEQUENCE","PFMSUSER","ID","NOTE",content,"修改计划失败");

			OracleBlob setblob = new OracleBlob("pfmsUser","userid",id+"","note",pfmsUser.getNote(),"修改计划失败",ct);
			setblob.updateContent(); 
 
			 
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterBaseInfo(PfmsUser pfmsUser,int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			
			ps = ct.prepareStatement("UPDATE ELUSER SET" + 
							" REALNAME=?,DANWEI=?,SEX=?,SHENFENZHENG=?,MOVEPHONE=?,depid=?,role=? WHERE ID=?");
			
			ps.setString(1, pfmsUser.getUser().getRealname());
			ps.setString(2, pfmsUser.getUser().getDanwei());
			ps.setString(3, pfmsUser.getUser().getSex());
			ps.setString(4, pfmsUser.getUser().getShenfenzheng());
			ps.setString(5, pfmsUser.getUser().getMovephone());
			ps.setInt(6, pfmsUser.getUser().getDepartment().getId());
			ps.setInt(7, pfmsUser.getUser().getRole().getId());
			ps.setInt(8, id);
			ps.executeUpdate();
			
			ps = ct
			.prepareStatement("UPDATE PFMSUSER SET" + 
					" RESPNAME=?,ADDRESS=?,MOBILE=?,FEX=?,EMAIL=?,huiyuanleixing=?,province_city_county=?,head=?,altertime=sysdate WHERE USERID=?");
			ps.setString(1, pfmsUser.getRespName());
			ps.setString(2, pfmsUser.getAddress());
			ps.setString(3, pfmsUser.getMobile());
			ps.setString(4, pfmsUser.getFex());
			ps.setString(5, pfmsUser.getEmail());
			ps.setString(6, pfmsUser.getHuiyuanleixing());
			ps.setString(7, pfmsUser.getProvince_city_county());
			ps.setString(8, pfmsUser.getHead());
			ps.setInt(9, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void deleteUser(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			// TODO 删除用户做那些事？
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("delete from study_course where userid = ?");
			// ps.setInt(1, id);
			// ps.executeUpdate();
			// // 删除基本信息
			ps = ct
					.prepareStatement("update ELUSER set active=0 where id=?");
			
			ps.setInt(1, id);
			ps.executeUpdate();
			// 删除相关...

		} catch (Exception e) {
			logger.error("删除用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void alterPfmsUserZhengshu(PfmsUser pfmsUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		if(pfmsUser.getYingyezhizhao() == null)
			pfmsUser.setYingyezhizhao("");
		if(pfmsUser.getShuiwudengjizheng() == null)
			pfmsUser.setShuiwudengjizheng("");
		if(pfmsUser.getZuzhijigoudaimazheng() == null)
			pfmsUser.setZuzhijigoudaimazheng("");
		if(pfmsUser.getFarenshenfenzheng() == null)
			pfmsUser.setFarenshenfenzheng("");
		if(pfmsUser.getZizhidengjizhengshu() == null)
			pfmsUser.setZizhidengjizhengshu("");
		if(pfmsUser.getXinyongdengjipingguzhengshu() == null)
			pfmsUser.setXinyongdengjipingguzhengshu("");
		if(pfmsUser.getQitazhengshu() == null)
			pfmsUser.setQitazhengshu("");
		try {
			ct = DBConnection.getConnection();
			sql = "update pfmsuser set yingyezhizhao = ?,shuiwudengjizhengshu=?,zuzhijigoudaimazheng=?," +
					"farenshenfenzheng=?,zizhidengjizhengshu=?,xinyongdengjipingguzhengshu=?,qitazhengshu=? where userid=?";
			ps = ct.prepareStatement(sql);
			
			ps.setString(1, pfmsUser.getYingyezhizhao());
			ps.setString(2, pfmsUser.getShuiwudengjizheng());
			ps.setString(3, pfmsUser.getZuzhijigoudaimazheng());
			ps.setString(4, pfmsUser.getFarenshenfenzheng());
			ps.setString(5, pfmsUser.getZizhidengjizhengshu());
			ps.setString(6, pfmsUser.getXinyongdengjipingguzhengshu());
			ps.setString(7, pfmsUser.getQitazhengshu());
			ps.setInt(8, pfmsUser.getUserId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<ELUser> listUsers(ElNode dep, int subdep, ELUser eu,
			int pageNow, int pageSize)
			throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlW = "";
		try {
			boolean consub = subdep == 1 ? true : false;  
			if (null != eu) {
				if (null != eu.getUsername())
					sqlW = sqlW +" and eu.username like '%"+eu.getUsername()+"%'"; 
				if (null != eu.getRealname())
					sqlW = sqlW +" and eu.realname like '%"+eu.getRealname()+"%'";  
				if (null != eu.getPfmsUser() && null != eu.getPfmsUser().getTuijian())
					sqlW = sqlW +" and pu.tuijian like '%"+eu.getPfmsUser().getTuijian()+"%'";
				if (null != eu.getSex())
					sqlW = sqlW +" and eu.sex like '%"+eu.getSex()+"%'";   
				if (eu.getShengri() != null) 
					sqlW = sqlW +" and eu.shengri >="+eu.getShengri()+")";   
				if (eu.getShengri_end() != null) {
					sqlW = sqlW +" and eu.shengri <="+eu.getShengri_end();  
				}
				if (eu.getValid2() != 0) {
					if (eu.getValid2() == 1) {
						sqlW = sqlW +" and eu.valid = 1";   
					} else {
						sqlW = sqlW +" and eu.valid = 0";    
					}
				}
				if (eu.getRole() != null && eu.getRole().getId() > 0) {
					sqlW = sqlW +" and eu.role = "+eu.getRole().getId(); 
				}
				if (null != eu.getPfmsUser()){
					if(!eu.getPfmsUser().getHuiyuanleixing().equals("")){
						sqlW = sqlW +" and pu.huiyuanleixing = '"+eu.getPfmsUser().getHuiyuanleixing()+"'";  
					}
				}
			}  
			sql = "select * from (select t.*,rownum rn from(select  eu.id euid,eu.username, eu.realname," +
					" eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong," +
					" eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ ," +
					" pu.huiyuanleixing,pu.tuijian,pu.province_city_county  from pfmsuser pu,eluser eu " +
					"  join elrole er on er.id = eu.role  " +
					" join ("+
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, consub)+
					" ) dep on eu.depid = dep.id " +
					"  where eu.id=pu.userid "+sqlW+
					" order by pu.altertime desc)t where "; 
			if(pageNow != 0 || pageSize != 0){
				sql = sql + " rownum<=?) where rn >=?";
			}
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(sql);   
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize); 
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
				elUser.setDepartment(new Department(rs.getInt(5), rs
						.getString(6)));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(9));
				elUser.setJingzhong(rs.getInt(10));
				elUser.setShengri(rs.getDate(11));
				elUser.setAge(rs.getInt(12));
				PfmsUser pfmsUser = new PfmsUser();
				pfmsUser.setHuiyuanleixing(rs.getString("huiyuanleixing"));
				pfmsUser.setTuijian(rs.getString("tuijian"));
				pfmsUser.setProvince_city_county(rs.getString("province_city_county"));
				elUser.setPfmsUser(pfmsUser);
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
	
	public int listUsersSize(ElNode dep, int subdep, ELUser eu)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlW = "";
		try {
			boolean consub = subdep == 1 ? true : false;
			if (null != eu) {
				if (null != eu.getUsername())
					sqlW = sqlW +" and eu.username like '%"+eu.getUsername()+"%'"; 
				if (null != eu.getRealname())
					sqlW = sqlW +" and eu.realname like '%"+eu.getRealname()+"%'";  
				if (null != eu.getPfmsUser() && null != eu.getPfmsUser().getTuijian())
					sqlW = sqlW +" and pu.tuijian like '%"+eu.getPfmsUser().getTuijian()+"%'";
				if (null != eu.getSex())
					sqlW = sqlW +" and eu.sex like '%"+eu.getSex()+"%'";   
				if (eu.getShengri() != null) 
					sqlW = sqlW +" and eu.shengri >="+eu.getShengri()+")";   
				if (eu.getShengri_end() != null) {
					sqlW = sqlW +" and eu.shengri <="+eu.getShengri_end();  
				}
				if (eu.getValid2() != 0) {
					if (eu.getValid2() == 1) {
						sqlW = sqlW +" and eu.valid = 1";   
					} else {
						sqlW = sqlW +" and eu.valid = 0";    
					}
				}
				if (eu.getRole() != null && eu.getRole().getId() > 0) {
					sqlW = sqlW +" and eu.role = "+eu.getRole().getId(); 
				}
				if (null != eu.getPfmsUser()){
					if(!eu.getPfmsUser().getHuiyuanleixing().equals("")){
						sqlW = sqlW +" and pu.huiyuanleixing = '"+eu.getPfmsUser().getHuiyuanleixing()+"'";  
					}
				}
			}
			sql = "select count(*) from pfmsuser pu ,eluser eu " +
			"  join elrole er on er.id = eu.role "+
					"join (" +
			((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, consub)+
			" ) dep on eu.depid = dep.id "+
			"where eu.id=pu.userid and 1=1 " + sqlW;
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	@SuppressWarnings("static-access")
	public int addPfmsUser(PfmsUser pfmsUser,int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int id=0;
		try {
			ct = DBConnection.getConnection();
			sql = "call add_pfmsuser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setString(1, pfmsUser.getUser().getUsername());
			ps.setString(2, new MD5().crypt(pfmsUser.getUser().getPassword()));
			
			ps.setInt(3, depid);
			
			ps.setInt(4, pfmsUser.getUser().getRole().getId());
			ps.setString(5, pfmsUser.getUser().getRealname());
			ps.setString(6, pfmsUser.getUser().getDanwei());
			ps.setString(7, pfmsUser.getUser().getSex());
			ps.setString(8, pfmsUser.getUser().getShenfenzheng());
			ps.setString(9, pfmsUser.getUser().getMovephone());
			
			
			ps.setString(10, pfmsUser.getHead());
			ps.setString(11, pfmsUser.getHuiyuanleixing());
			ps.setString(12, pfmsUser.getProvince_city_county());
			ps.setString(13, pfmsUser.getRespName());
			ps.setString(14, pfmsUser.getAddress());
			ps.setString(15, pfmsUser.getMobile());
			ps.setString(16, pfmsUser.getFex());
			ps.setString(17, pfmsUser.getEmail());
			ps.setInt(18, pfmsUser.getUser().getUsertype());
			ps.executeUpdate();
			
			if(pfmsUser.getNote() != null){
				this.alterMemberProfile(pfmsUser, id);
			}
			
			ps = ct.prepareStatement("select eluser_sequence.currval from dual");
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			logger.error("添加会员失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
		
	}

	public List<Area> areaList(String selected,String city_type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Area> areaList = new ArrayList<Area>();
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			
			if(selected != null && !selected.equals("") )
				sqlAppend = " where parent_id = (select parent_id from edone_area where name = '"+ selected +"' and type='"+ city_type +"')";
			
			sql = "select * from edone_area" + sqlAppend ;
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) { 
				Area area = new Area();
				area.setId(rs.getString("id"));
				area.setName(rs.getString("name"));
				area.setParent_id(rs.getInt("parent_id"));
				area.setType(rs.getString("type"));
				areaList.add(area);
			}
		} catch (Exception e) {
			logger.error("查询省市县失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return areaList;
	}

	public void alterPictures(PfmsUser pfmsUser,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update PFMSUSER";
			ps = ct.prepareStatement("update PFMSUSER SET banner=?,logo=?,dianpujianjietupian=? WHERE USERID = ?"); 
			ps.setString(1, pfmsUser.getBanner());
			ps.setString(2, pfmsUser.getLogo());
			ps.setString(3, pfmsUser.getDianpujianjietupian());
			ps.setInt(4, userid);
			ps.executeUpdate();  
			 
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<PfmsUser> listAllPfmsUsers(ElNode dep, int subdep) throws ElException {
		List<PfmsUser> pfmsUsers = new ArrayList<PfmsUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlW = "";
		try {
			boolean consub = subdep == 1 ? true : false;
			
			sql = "select  eu.id euid,eu.username, eu.realname," +
					" eu.role,dep.id depid,dep.name depname,eu.valid,er.name ername,eu.sex,eu.jingzhong," +
					" eu.shengri,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ ," +
					" pu.huiyuanleixing,pu.province_city_county,eu.password,eu.xuhao,eu.shenfenzheng,eu.depid  from ELUSER eu join ("+
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", dep, consub)+
					" ) dep on eu.depid = dep.id " +
					" left join elrole er on er.id = eu.role  " +
					" right join pfmsuser pu on eu.id=pu.userid";
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(sql);   
			rs = ps.executeQuery();
			while (rs.next()) {
				PfmsUser pfmsUser = new PfmsUser();
				ELUser elUser = new ELUser();
				
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setRole(new ElRole(rs.getInt(4), rs.getString(8)));
				elUser.setDepartment(new Department(rs.getInt(5), rs
						.getString(6)));
				elUser.setValid(rs.getBoolean(7));
				elUser.setSex(rs.getString(9));
				elUser.setJingzhong(rs.getInt(10));
				elUser.setShengri(rs.getDate(11));
				elUser.setAge(rs.getInt(12));
				elUser.setPassword(rs.getString("password"));
				elUser.setXuhao(rs.getString("xuhao"));
				elUser.setShenfenzheng(rs.getString("shenfenzheng"));
				pfmsUser.setHuiyuanleixing(rs.getString("huiyuanleixing"));
				pfmsUser.setProvince_city_county(rs.getString("province_city_county"));
				
				pfmsUser.setUser(elUser);
				pfmsUsers.add(pfmsUser);
//				elUser.setPfmsUser(pfmsUser);
//				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("会员列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pfmsUsers;
	}

	public void addPfmsUser(ELUser eu, ELUser elUser) throws ElException {
		// TODO Auto-generated method stub
		
	}
	
	public List<PfmsUser> listFrontUsers(int pageNow, int pageSize) throws ElException {
		List<PfmsUser> pfmsUserList = new ArrayList<PfmsUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			sql = "select * from (select t.*,rownum rn from (select p.*,e.username from pfmsuser p " +
					"left join eluser e on p.userid=e.id " +
			" where  p.tuijian='推荐' "+
			" order by p.altertime desc) t where rownum <=?) where rn>=?";
			ct = DBConnection.getConnection();
			ps =ct.prepareStatement(sql);   
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize); 
			rs = ps.executeQuery();
			while (rs.next()) {
				PfmsUser pfmsUser = new PfmsUser();
				pfmsUser.setId(rs.getInt("id"));
				pfmsUser.setUserId(rs.getInt("userid"));
				ELUser elUser = new ELUser();
				elUser.setUsername(rs.getString("username"));
				pfmsUser.setHead(rs.getString("head"));
				pfmsUser.setUser(elUser);
				pfmsUserList.add(pfmsUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pfmsUserList;
	}
	
	public void delUser(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"delete from pfmsUser where userid = ?"
					);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			ps = ct.prepareStatement(
					"delete from eluser where id = ?"
					);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("删除会员失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public int getRoleId(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int roleid = 0;
		try {
			ct = DBConnection.getConnection();
			sql = "select role from eluser where id = ?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();  
			if(rs.next()){
				roleid = rs.getInt(1);
			}
			 
		} catch (Exception e) {
			logger.error("根据userid获取roleid失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return roleid;
	}

	public void insert_into_pfmsUser(ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into pfmsuser (id,userid,tuijian,respname,address,email,huiyuanleixing,province_city_county) values (pfmsuser_sequence.nextval,?,?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, elUser.getId());
//			ps.setString(2, elUser.getRealname());
			ps.setString(2, "普通");
			ps.setString(3, elUser.getUsername());
			ps.setString(4, elUser.getDepartment().getName());
			ps.setString(5, elUser.getEmail());
			ps.setString(6, elUser.getJingzhong_());
			ps.setString(7, elUser.getDishi_());
			ps.executeUpdate();  


			 
		} catch (Exception e) {
			logger.error("添加pfmsuser失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
