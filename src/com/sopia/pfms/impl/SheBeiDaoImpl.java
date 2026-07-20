package com.sopia.pfms.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.OracleBlob;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.dao.SheBeiDao;
import com.sopia.pfms.entities.SheBei;
import com.sopia.pfms.entities.Shebeileixing;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Toubaozhuangtai;

public class SheBeiDaoImpl implements SheBeiDao{
	private static final Log logger = LogFactory.getLog(SheBeiDaoImpl.class);

	public void updateAllShebeiShenhezhuangtai() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = 
				"update shebei set " +
					"shenhezhuangtai = 2" ;
			
			ps = ct.prepareStatement(sql);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<SheBei> shebeilist(boolean is_shebei_sh,int start,int size,int id,SheBei shebei,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<SheBei> shebeilist = new ArrayList<SheBei>();
		try {
			ct = DBConnection.getConnection();
//			if(is_shebei_sh){//需要审核
//				sqlAppend = sqlAppend + " and sb.shenhezhuangtai = 2 ";
//			}else{
//				this.updateAllShebeiShenhezhuangtai();
//			}
			if(shebei != null){
				if(!shebei.getName().equals(""))
					sqlAppend = sqlAppend + " and sb.name like '%" + shebei.getName() + "%'";
				if(shebei.getToubaozhuangtai() != 0)
					sqlAppend = sqlAppend + " and sb.toubaozhuangtai = '" + shebei.getToubaozhuangtai() + "'";
				if(shebei.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and sb.shenhezhuangtai = '" + shebei.getShenhezhuangtai() + "'";
				if(!shebei.getShebeileixing().equals(""))
					sqlAppend = sqlAppend + " and sb.shebeileixing = '" + shebei.getShebeileixing() + "'";
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = 
				"select b.* from  (" +
					"select a.id,a.name,a.userid,a.fabuzhe,a.shebeileibie,a.fabushijian," +
					"a.xiugaishijian,a.toubaozhuangtai,a.tbtoubaozhuangtai,a.shenhezhuangtai,a.psshenhezhuangtai," +
					"a.shebeileixing,rownum rn from  (" +
						"select sb.id,sb.name,sb.userid,sb.fabuzhe,  " +
							"sb.shebeileibie,sb.fabushijian,sb.xiugaishijian," +
							"sb.toubaozhuangtai,tb.toubaozhuangtai as tbtoubaozhuangtai," +
							"sb.shenhezhuangtai,ps.shenhezhuangtai as psshenhezhuangtai," +
							"sb.shebeileixing  " +
							"from shebei sb " +
							"left join toubaozhuangtai tb on sb.toubaozhuangtai=tb.id " +
							"left join product_shenhezhuangtai ps on sb.shenhezhuangtai=ps.id " +
						"where sb.userid=? " + sqlAppend + 
						" order by sb.fabushijian desc) a  " +
					"where rownum<=?) b " +
				"where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, size);
			ps.setInt(3, start);
			rs = ps.executeQuery();
			while (rs.next()) {
				shebei = new SheBei();
				Toubaozhuangtai toubaozhuangtai_entity = new Toubaozhuangtai();
				toubaozhuangtai_entity.setToubaozhuangtai(rs.getString("tbtoubaozhuangtai"));
				Shenhezhuangtai shenhezhuangtai_entity = new Shenhezhuangtai();
				shenhezhuangtai_entity.setShenhezhuangtai(rs.getString("psshenhezhuangtai"));
				
				shebei.setFabushijian(rs.getTimestamp("fabushijian"));
				shebei.setFabuzhe(rs.getString("fabuzhe"));
				shebei.setId(rs.getInt("id"));
				shebei.setName(rs.getString("name"));
				shebei.setShebeileixing(rs.getString("shebeileixing"));
				shebei.setShebeilleibie(rs.getString("shebeileibie"));
				shebei.setXiugaishijian(rs.getTimestamp("xiugaishijian"));
				shebei.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				
				shebei.setToubaozhuangtai_entity(toubaozhuangtai_entity);
				shebei.setShenhezhuangtai_entity(shenhezhuangtai_entity);
				shebeilist.add(shebei);
			}
		} catch (Exception e) {
			logger.error("我的设备列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return shebeilist;
	}

	public int getCount(boolean is_shebei_sh,int id,SheBei shebei,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int size = 0;
		try {
			ct = DBConnection.getConnection();
//			if(is_shebei_sh)
//				sqlAppend = sqlAppend + " and sb.shenhezhuangtai = 2 ";
			
			if(shebei != null){
				if(!shebei.getName().equals(""))
					sqlAppend = sqlAppend + " and sb.name like '%" + shebei.getName() + "%'";
				if(shebei.getToubaozhuangtai() != 0)
					sqlAppend = sqlAppend + " and sb.toubaozhuangtai = '" + shebei.getToubaozhuangtai() + "'";
				if(shebei.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and sb.shenhezhuangtai = '" + shebei.getShenhezhuangtai() + "'";
				if(!shebei.getShebeileixing().equals(""))
					sqlAppend = sqlAppend + " and sb.shebeileixing = '" + shebei.getShebeileixing() + "'";
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = "select count(1) from shebei sb join product_shenhezhuangtai ps on sb.shenhezhuangtai=ps.id  where userid=? " + sqlAppend;
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的设备列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	public ELUser getELUser(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ELUser elUser  = new ELUser();
		try {
			ct = DBConnection.getConnection();
			String sql = "select id,username,realname from eluser where id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				elUser.setUsername(rs.getString("username"));
				elUser.setUsername(rs.getString("realname"));
				elUser.setId(rs.getInt("id"));
			}
		} catch (Exception e) {
			logger.error("获取用户失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elUser;
	}

	public void addShebei(boolean is_shebei_sh,SheBei shebei,ELUser elUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into shebei (id," +
									  "name,xinghao,shebeidizhi,postalcode," +
									  "shebeiusezhenghao,shebeiuseyouxiaoqi,shebeiusequyu,shebeiuseleixing,shebeileixing," +
									  "shebeidengji,shengchanchangjia,chuchangriqi,jianyanqixian,jianyanriqi," +
									  "shebeitese,beizhu,shebeijianjie," +
									  "fabuzhe,shenhezhuangtai,fabushijian,userid,toubaozhuangtai,dengjibianhao,kaishishijian,jieshushijian)" + 
			" values (shebei_sequence.nextval," +
					 "?,?,?,?," +
					 "?,?,?,?,?," +
					 "?,?,?,?,?," +
					 "?,?,empty_blob()," +
					 "?,?,sysdate,?," +
					 "?,?,?,?)";
			ps = ct.prepareStatement(sql);
			
			ps.setString(1, shebei.getName());
			ps.setString(2, shebei.getXinghao());
			ps.setString(3, shebei.getShebeidizhi());
			ps.setString(4, shebei.getPostalcode());
			
			ps.setString(5, shebei.getShebeiusezhenghao());
			ps.setInt(6, shebei.getShebeiuseyouxiaoqi());
			ps.setString(7, shebei.getShebeiusequyu());
			ps.setString(8, shebei.getShebeiuseleixing());
			ps.setString(9, shebei.getShebeileixing());
			
			ps.setString(10, shebei.getShebeidengji());
			ps.setString(11, shebei.getShengchanchangjia());
			ps.setTimestamp(12, shebei.getChuchangriqi());
			ps.setInt(13, shebei.getJianyanqixian());
			ps.setTimestamp(14, shebei.getJianyanriqi());
			
			ps.setString(15, shebei.getShebeitese());
			ps.setString(16, shebei.getBeizhu());
			
			ps.setString(17, elUser.getUsername());
			if(is_shebei_sh){
				ps.setInt(18, 1);
			}else{
				ps.setInt(18, 2);
			}
			ps.setInt(19, elUser.getId());
			
			ps.setInt(20, shebei.getToubaozhuangtai());
			ps.setInt(21, shebei.getDengjibianhao());
			ps.setTimestamp(22, shebei.getKaishishijian());
			ps.setTimestamp(23, shebei.getJieshushijian());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob(ct,"shebei_sequence","shebei","id","shebeijianjie",shebei.getShebeijianjie(),"添加设备失败");
			setblob.addContent(); 
		} catch (Exception e) {
			logger.error("添加设备失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteShebei(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"delete from shebei where id=?"
					);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除设备失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public SheBei showShebei(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SheBei shebei = new SheBei();
		try {
			ct = DBConnection.getConnection();
			String sql = "select s.*,t.id as tid,t.toubaozhuangtai as ttoubaozhuangtai from shebei s join toubaozhuangtai t on s.toubaozhuangtai=t.id  where s.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
//				shebei = new SheBei();
				shebei.setId(rs.getInt("id"));
				shebei.setName(rs.getString("name"));
				shebei.setXinghao(rs.getString("xinghao"));
				shebei.setShebeidizhi(rs.getString("shebeidizhi"));
				shebei.setPostalcode(rs.getString("postalcode"));
				shebei.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				shebei.setShebeiusezhenghao(rs.getString("shebeiusezhenghao"));
				shebei.setShebeiuseyouxiaoqi(rs.getInt("shebeiuseyouxiaoqi"));
				shebei.setShebeiusequyu(rs.getString("shebeiusequyu"));
				shebei.setShebeiuseleixing(rs.getString("shebeiuseleixing"));
				shebei.setShebeileixing(rs.getString("shebeileixing"));
				shebei.setShebeidengji(rs.getString("shebeidengji"));
				shebei.setShengchanchangjia(rs.getString("shengchanchangjia"));
				shebei.setChuchangriqi(rs.getTimestamp("chuchangriqi"));
				shebei.setJianyanqixian(rs.getInt("jianyanqixian"));
				shebei.setJianyanriqi(rs.getTimestamp("jianyanriqi"));
				shebei.setShebeijianjie(new OracleBlob().getContent(rs.getBlob("shebeijianjie")));
				shebei.setShebeitese(rs.getString("shebeitese"));
				shebei.setBeizhu(rs.getString("beizhu"));
				shebei.setToubaozhuangtai(rs.getInt("toubaozhuangtai"));
				shebei.setDengjibianhao(rs.getInt("dengjibianhao"));
				shebei.setKaishishijian(rs.getTimestamp("kaishishijian"));
				shebei.setJieshushijian(rs.getTimestamp("jieshushijian"));
				
				Toubaozhuangtai toubaozhuangtai = new Toubaozhuangtai();
				toubaozhuangtai.setId(rs.getInt("tid"));
				toubaozhuangtai.setToubaozhuangtai(rs.getString("ttoubaozhuangtai"));
				
				shebei.setToubaozhuangtai_entity(toubaozhuangtai);
				
				return shebei;
			}
		} catch (Exception e) {
			logger.error("显示设备失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return shebei;
	}

	public void updateShebei(SheBei shebei,int userid,int shenhezhuangtai,boolean is_product_fabu_can_alter) throws ElException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			
			if(is_product_fabu_can_alter == true){//审核后允许修改
					sqlAppend = sqlAppend + ",shenhezhuangtai=?,fabushijian=sysdate ";
			}
			
			sql = "update shebei set name = ?,xinghao=?,shebeidizhi=?,postalcode=?,shebeiusezhenghao=?," +
				"shebeiuseyouxiaoqi=?,shebeiusequyu=?,shebeiuseleixing=?,shebeileixing=?,shebeidengji=?," +
				"shengchanchangjia=?,chuchangriqi=?,jianyanqixian=?,jianyanriqi=?,shebeijianjie=empty_blob(),shebeitese=?,beizhu=?,toubaozhuangtai=?,dengjibianhao=?,kaishishijian=?,jieshushijian=? " + sqlAppend + 
				" where id=?";
			ps = ct.prepareStatement(sql);
			
			ps.setString(1,shebei.getName());
			ps.setString(2,shebei.getXinghao());
			ps.setString(3,shebei.getShebeidizhi());
			ps.setString(4,shebei.getPostalcode());
			ps.setString(5,shebei.getShebeiusezhenghao());
			ps.setInt(6,shebei.getShebeiuseyouxiaoqi());
			ps.setString(7,shebei.getShebeiusequyu());
			ps.setString(8,shebei.getShebeiuseleixing());
			ps.setString(9,shebei.getShebeileixing());
			ps.setString(10,shebei.getShebeidengji());
			ps.setString(11,shebei.getShengchanchangjia());
			ps.setTimestamp(12,shebei.getChuchangriqi());
			ps.setInt(13,shebei.getJianyanqixian());
			ps.setTimestamp(14,shebei.getJianyanriqi());
			ps.setString(15,shebei.getShebeitese());
			ps.setString(16,shebei.getBeizhu());
			
			ps.setInt(17,shebei.getToubaozhuangtai());
			ps.setInt(18,shebei.getDengjibianhao());
			ps.setTimestamp(19,shebei.getKaishishijian());
			ps.setTimestamp(20,shebei.getJieshushijian());
			
			if(is_product_fabu_can_alter == true){
				ps.setInt(21,1);//设置为1，修改后将其他状态变更为已创建状态
				ps.setInt(22, shebei.getId());
			}else{
				ps.setInt(21, shebei.getId());
			}
			
			
			ps.executeUpdate();
			
			
			OracleBlob setblob = new OracleBlob("shebei","id",shebei.getId()+"","shebeijianjie",shebei.getShebeijianjie(),"修改计划失败",ct);
			setblob.updateContent();
			
		} catch (Exception e) {
			logger.error("修改计划失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}


	public List<Shenhezhuangtai> shenhezhuangtaiList() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Shenhezhuangtai> shenhezhuangtaiList = new ArrayList<Shenhezhuangtai>();
		try {
			ct = DBConnection.getConnection();
			sql = "select * from product_shenhezhuangtai";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Shenhezhuangtai shenhezhuangtai = new Shenhezhuangtai();
				shenhezhuangtai.setId(rs.getInt("id"));
				shenhezhuangtai.setShenhezhuangtai(rs.getString("shenhezhuangtai"));
				shenhezhuangtaiList.add(shenhezhuangtai);
			}
		} catch (Exception e) {
			logger.error("审核状态查询失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return shenhezhuangtaiList;
	}

	public List<Toubaozhuangtai> toubaozhuangtaiList() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Toubaozhuangtai> toubaozhuangtaiList = new ArrayList<Toubaozhuangtai>();
		try {
			ct = DBConnection.getConnection();
			sql = "select * from toubaozhuangtai";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Toubaozhuangtai toubaozhuangtai = new Toubaozhuangtai();
				toubaozhuangtai.setId(rs.getInt("id"));
				toubaozhuangtai.setToubaozhuangtai(rs.getString("toubaozhuangtai"));
				toubaozhuangtaiList.add(toubaozhuangtai);
			}
		} catch (Exception e) {
			logger.error("产品所属栏目查询失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return toubaozhuangtaiList;
	}

	public int getCount(SheBei shebei,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			if(shebei != null){
				if(!shebei.getFabuzhe().equals(""))
					sqlAppend = sqlAppend + " and fabuzhe like '%" + shebei.getFabuzhe() + "%'";
				if(!shebei.getName().equals(""))
					sqlAppend = sqlAppend + " and name like '%" + shebei.getName() + "%'";
				if(shebei.getToubaozhuangtai() != 0)
					sqlAppend = sqlAppend + " and toubaozhuangtai = '" + shebei.getToubaozhuangtai() + "'";
				if(shebei.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and shenhezhuangtai = '" + shebei.getShenhezhuangtai() + "'";
				if(!shebei.getShebeileixing().equals(""))
					sqlAppend = sqlAppend + " and shebeileixing = '" + shebei.getShebeileixing() + "'";
				if(!shebei.getFabuzhesuozaidanwei().equals(""))
					sqlAppend = sqlAppend + " and fabuzhesuozaidanwei like '%" + shebei.getFabuzhesuozaidanwei() + "%'";
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = "select count(1) from shebei where 1=1 " + sqlAppend;
			
				ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的设备列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<SheBei> shebeilist(int start, int size, SheBei shebei,Timestamp starttime,Timestamp endtime)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<SheBei> shebeilist = new ArrayList<SheBei>();
		try {
			ct = DBConnection.getConnection();
			if(shebei != null){
				if(!shebei.getFabuzhe().equals(""))
					sqlAppend = sqlAppend + " and sb.fabuzhe like '%" + shebei.getFabuzhe() + "%'";
				if(!shebei.getName().equals(""))
					sqlAppend = sqlAppend + " and sb.name like '%" + shebei.getName() + "%'";
				if(shebei.getToubaozhuangtai() != 0)
					sqlAppend = sqlAppend + " and sb.toubaozhuangtai = '" + shebei.getToubaozhuangtai() + "'";
				if(shebei.getShenhezhuangtai() != 0)
					sqlAppend = sqlAppend + " and sb.shenhezhuangtai = '" + shebei.getShenhezhuangtai() + "'";
				if(!shebei.getShebeileixing().equals(""))
					sqlAppend = sqlAppend + " and sb.shebeileixing = '" + shebei.getShebeileixing() + "'";
				if(!shebei.getFabuzhesuozaidanwei().equals(""))
					sqlAppend = sqlAppend + " and sb.fabuzhesuozaidanwei like '%" + shebei.getFabuzhesuozaidanwei() + "%'";
			}
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(fabushijian,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = 
				"select b.* from  (" +
					"select a.id,a.name,a.userid,a.fabuzhe,a.fabuzhesuozaidanwei,a.shebeileibie,a.fabushijian," +
					"a.xiugaishijian,a.toubaozhuangtai,a.tbtoubaozhuangtai,a.shenhezhuangtai,a.psshenhezhuangtai," +
					"a.shebeileixing,rownum rn from  (" +
						"select sb.id,sb.name,sb.userid,sb.fabuzhe,sb.fabuzhesuozaidanwei,  " +
							"sb.shebeileibie,sb.fabushijian,sb.xiugaishijian," +
							"sb.toubaozhuangtai,tb.toubaozhuangtai as tbtoubaozhuangtai," +
							"sb.shenhezhuangtai,ps.shenhezhuangtai as psshenhezhuangtai," +
							"sb.shebeileixing  " +
							"from shebei sb " +
							"left join toubaozhuangtai tb on sb.toubaozhuangtai=tb.id " +
							"left join product_shenhezhuangtai ps on sb.shenhezhuangtai=ps.id " +
						"where 1=1 " + sqlAppend + 
						" order by sb.fabushijian desc) a  " +
					"where rownum<=?) b " +
				"where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, size);
			ps.setInt(2, start);
			rs = ps.executeQuery();
			while (rs.next()) {
				shebei = new SheBei();
				Toubaozhuangtai toubaozhuangtai_entity = new Toubaozhuangtai();
				toubaozhuangtai_entity.setToubaozhuangtai(rs.getString("tbtoubaozhuangtai"));
				Shenhezhuangtai shenhezhuangtai_entity = new Shenhezhuangtai();
				shenhezhuangtai_entity.setShenhezhuangtai(rs.getString("psshenhezhuangtai"));
				
				shebei.setFabushijian(rs.getTimestamp("fabushijian"));
				shebei.setFabuzhe(rs.getString("fabuzhe"));
				shebei.setFabuzhesuozaidanwei(rs.getString("fabuzhesuozaidanwei"));
				shebei.setId(rs.getInt("id"));
				shebei.setName(rs.getString("name"));
				shebei.setShebeilleibie(rs.getString("shebeileibie"));
				shebei.setShebeileixing(rs.getString("shebeileixing"));
				shebei.setXiugaishijian(rs.getTimestamp("xiugaishijian"));
				shebei.setShenhezhuangtai(rs.getInt("shenhezhuangtai"));
				
				shebei.setToubaozhuangtai_entity(toubaozhuangtai_entity);
				shebei.setShenhezhuangtai_entity(shenhezhuangtai_entity);
				shebeilist.add(shebei);
			}
		} catch (Exception e) {
			logger.error("我的全部设备列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return shebeilist;
	}

	public int getRoleId(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int roleId = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select e.role from shebei s join eluser e on s.userid=e.id where e.id=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				roleId = rs.getInt("role");
			}
		} catch (Exception e) {
			logger.error("获取roleId失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return roleId;
	}

	public void shenheShebei(int roleId, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
//			if(roleId == 1){
				sql = 
					"update shebei set " +
						"shenhezhuangtai = (select id from product_shenhezhuangtai where shenhezhuangtai='审核通过')" + 
						" where id=?";
//			}
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改设备审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void shenheShebeiNotPass(int roleId, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
//			if(roleId == 1){
				sql = 
					"update shebei set " +
						"shenhezhuangtai = (select id from product_shenhezhuangtai where shenhezhuangtai='审核未通过')" + 
						" where id=?";
//			}
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("修改设备审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public boolean checkShzt(int id,String table) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select shenhezhuangtai from "+table+" where id = ?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				if(rs.getInt("shenhezhuangtai") == 2){
					return true;
				}
			}
			
		} catch (Exception e) {
			logger.error("删除前查看审核状态失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	
	/**
	 * 投保时选择设备
	 */
	public List<SheBei> searchShebei(int userid,String tableName, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<SheBei> shebeilist = new ArrayList<SheBei>();
		try {
			ct = DBConnection.getConnection();
			//通过审核的、未投保的、当期用户发布的
			sql = "select b.*,rn from (select a.*,rownum rn from (select * from "+tableName+" where userid=? ) a where rownum<=?) b where rn>=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				SheBei shebei = new SheBei();
				shebei.setId(rs.getInt("id"));
				shebei.setName(rs.getString("name"));
				shebei.setShebeileixing(rs.getString("shebeileixing"));
				shebei.setShebeiuseleixing(rs.getString("shebeiuseleixing"));
				shebeilist.add(shebei);
			}
		} catch (Exception e) {
			logger.error("我的设备列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return shebeilist;
	}

	public int searchShebeiSize(int userid,String tableName) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int size = 0;
		try {
			ct = DBConnection.getConnection();
			
			sql = "select count(1) from "+tableName+" where userid=? ";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的设备列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

}
