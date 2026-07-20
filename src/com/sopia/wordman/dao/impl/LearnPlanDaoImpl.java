package com.sopia.wordman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.MyLogin;
import com.sopia.wordman.dao.LearnPlanDao;
import com.sopia.wordman.entities.LearnPlan;

public class LearnPlanDaoImpl extends ElNodeDao implements LearnPlanDao{

	public void addLearnPlan(LearnPlan learnplan) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into learnplan(name,starttime,endtime,period,content,hours,userid) values(?,?,?,?,?,?,?)");
			ps.setString(1, learnplan.getName());
			ps.setTimestamp(2, learnplan.getStarttime());
			ps.setTimestamp(3, learnplan.getEndtime());
			ps.setString(4, learnplan.getPeriod());
			ps.setString(5, learnplan.getContent());
			ps.setDouble(6, learnplan.getHours());
			ps.setInt(7, learnplan.getUserid());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<LearnPlan> getallPlan(int pagenow,int pagesize,int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<LearnPlan> lps = new ArrayList<LearnPlan>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select * from learnplan where userid=?) t where rownum<=?) where rn>=?");
			ps.setInt(1, userid);
			ps.setInt(2, pagenow);
			ps.setInt(3, pagesize);
			rs = ps.executeQuery();
			while(rs.next()){
				LearnPlan lp = new LearnPlan();
				lp.setId(rs.getInt(1));
				lp.setName(rs.getString(2));
				lp.setStarttime(rs.getTimestamp(3));
				lp.setEndtime(rs.getTimestamp(4));
				lp.setPeriod(rs.getString(5));
				lp.setContent(rs.getString(6));
				lp.setHours(rs.getDouble(7));
				lp.setUserid(rs.getInt(8));
				lps.add(lp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lps;
	}

	public int getCount(int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from learnplan where userid=?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return count;
	}

	public LearnPlan getPlanById(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		LearnPlan lp = new LearnPlan();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from learnplan where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				lp.setId(rs.getInt(1));
				lp.setName(rs.getString(2));
				lp.setStarttime(rs.getTimestamp(3));
				lp.setEndtime(rs.getTimestamp(4));
				lp.setPeriod(rs.getString(5));
				lp.setContent(rs.getString(6));
				lp.setHours(rs.getDouble(7));
				lp.setUserid(rs.getInt(8));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lp;
	}

	public void alterPlan(LearnPlan learnplan) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update learnplan set name=?,starttime=?,endtime=?,period=?,content=?,hours=? where id=?");
			ps.setString(1, learnplan.getName());
			ps.setTimestamp(2, learnplan.getStarttime());
			ps.setTimestamp(3, learnplan.getEndtime());
			ps.setString(4, learnplan.getPeriod());
			ps.setString(5, learnplan.getContent());
			ps.setDouble(6, learnplan.getHours());
			ps.setInt(7, learnplan.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<LearnPlan> getallPlanXS(int pagenow,int pagesize,int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<LearnPlan> lps = new ArrayList<LearnPlan>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from(select t.*,rownum rn from (select * from learnplan where userid=?) t where rownum<=?) where rn>=?");
			ps.setInt(1, userid);
			ps.setInt(2, pagenow);
			ps.setInt(3, pagesize);
			rs = ps.executeQuery();
			while(rs.next()){
				LearnPlan lp = new LearnPlan();
				lp.setId(rs.getInt(1));
				lp.setName(rs.getString(2));
				lp.setStarttime(rs.getTimestamp(3));
				lp.setEndtime(rs.getTimestamp(4));
				lp.setPeriod(rs.getString(5));
				lp.setContent(rs.getString(6));
				lp.setHours(rs.getDouble(7));
				lp.setUserid(rs.getInt(8));
				lp.setSjhours(getsjhours(lp));
				lps.add(lp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return lps;
	}

	public Double getsjhours(LearnPlan lp)throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		Double hours = 0.0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select sum((cast(el.exittime as date)-cast(el.logintime as date))*24) xs from learnplan lp,eluserlogininfo el where to_char(cast(el.logintime as date),'yyyymmdd:hh24:mi:ss')>to_char(?,'yyyymmdd:hh24:mi:ss') and to_char(cast(el.exittime as date),'yyyymmdd:hh24:mi:ss')<to_char(?,'yyyymmdd:hh24:mi:ss') and lp.userid=el.userid and lp.userid=?";  
			ps = ct.prepareStatement(sql);
			ps.setTimestamp(1, lp.getStarttime());
			ps.setTimestamp(2, lp.getEndtime());
			ps.setInt(3, lp.getUserid());
			rs = ps.executeQuery();
			if(rs.next()){
				DecimalFormat df = new DecimalFormat("##.##");
				String fr = df.format(rs.getDouble(1));
				hours = Double.parseDouble(fr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return hours;
	}

	public List<MyLogin> getMyloginInfo(int pagenow,int pagesize,LearnPlan learnplan) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<MyLogin> mylogins = new ArrayList<MyLogin>();
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*,rownum rn from (";
			sql+="select el.logintime,el.exittime,u.username,u.id,(cast(el.exittime as date)-cast(el.logintime as date))*24 sc from learnplan lp,eluserlogininfo el,eluser u where to_char(cast(el.logintime as date),'yyyymmdd:hh24:mi:ss')>to_char(?,'yyyymmdd:hh24:mi:ss') and to_char(cast(el.exittime as date),'yyyymmdd:hh24:mi:ss')<to_char(?,'yyyymmdd:hh24:mi:ss') and lp.userid=el.userid and lp.userid=u.id and lp.userid=? order by el.logintime desc";
			sql+=" )t where rownum<=?) where rn>=?";
			ps = ct.prepareStatement(sql);
			if(learnplan.getStarttime()!=null&&learnplan.getEndtime()!=null){
				ps.setTimestamp(1, learnplan.getStarttime());
				ps.setTimestamp(2, learnplan.getEndtime());
			}else{
				learnplan = getPlanById(learnplan.getId());
				ps.setTimestamp(1, learnplan.getStarttime());
				ps.setTimestamp(2, learnplan.getEndtime());
			}
			ps.setInt(3, learnplan.getUserid());
			ps.setInt(4, pagenow);
			ps.setInt(5, pagesize);
			rs = ps.executeQuery();
			while(rs.next()){
				MyLogin ml = new MyLogin();
				ELUser el = new ELUser();
				el.setUsername(rs.getString(3));
				el.setId(rs.getInt(4));
				ml.setElUser(el);
				ml.setLogintime(rs.getTimestamp(1));
				ml.setExittime(rs.getTimestamp(2));
				DecimalFormat df = new DecimalFormat("##.##");
				String fr = df.format(rs.getDouble(5));
				ml.setShichang(Double.parseDouble(fr));
				mylogins.add(ml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mylogins;
	}

	public int getLoginInfoCount(LearnPlan learnplan) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from (";
			sql+="select el.logintime,el.exittime,u.username,u.id,(cast(el.exittime as date)-cast(el.logintime as date))*24 sc from learnplan lp,eluserlogininfo el,eluser u where to_char(cast(el.logintime as date),'yyyymmdd:hh24:mi:ss')>to_char(?,'yyyymmdd:hh24:mi:ss') and to_char(cast(el.exittime as date),'yyyymmdd:hh24:mi:ss')<to_char(?,'yyyymmdd:hh24:mi:ss') and lp.userid=el.userid and lp.userid=u.id and lp.userid=? order by el.logintime desc";
			sql+=" )";
			ps = ct.prepareStatement(sql);
			if(learnplan.getStarttime()!=null&&learnplan.getEndtime()!=null){
				ps.setTimestamp(1, learnplan.getStarttime());
				ps.setTimestamp(2, learnplan.getEndtime());
			}else{
				learnplan = getPlanById(learnplan.getId());
				ps.setTimestamp(1, learnplan.getStarttime());
				ps.setTimestamp(2, learnplan.getEndtime());
			}
			ps.setInt(3, learnplan.getUserid());
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
}
