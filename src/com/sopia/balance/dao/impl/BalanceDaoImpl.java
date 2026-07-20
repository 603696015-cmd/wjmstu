package com.sopia.balance.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.balance.dao.BalanceDao;
import com.sopia.balance.entites.Balance;
import com.sopia.balance.entites.Income;
import com.sopia.balance.entites.RechargeInfo;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.shopping.entities.Shopping;
import com.sopia.studyman.dao.impl.StudyClassDaoImpl;
import com.sopia.studyman.entities.Schoolrolls;

public class BalanceDaoImpl implements BalanceDao{
	private static final Log logger = LogFactory.getLog(StudyClassDaoImpl.class);
	
	public Balance  getbalancebyid(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Balance b= new Balance();
		try {
			ct = DBConnection.getConnection();
			String sql=" select eu.username,eu.realname,eb.userbalance from eluser_balance eb " +
					" left  join eluser eu on eu.id=eb.userid  where eb.userid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				ELUser u = new ELUser();
				u.setUsername(rs.getString(1));
				u.setRealname(rs.getString(2));
				b.setUser(u);
				b.setBalance(rs.getFloat(3));
				
				
			}else{
				

				sql = "insert into eluser_balance(userid) values(?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, id);
				ps.executeUpdate();
				getbalancebyid(id);
				
			}
			return b;
			
		}catch (Exception e) {
			logger.error("查询我的余额信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public List<Balance> getUserlistBalance(ElNode tree, ELUser eluser,
			int sublibs, int pN, int pS) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Balance> list=new ArrayList<Balance>();
		try {
			
			
			ct = DBConnection.getConnection();
			String sql="select * from (select t.*, rownum rn from  " +
				" (select eu.id,eu.username,eu.realname,eu.valid ,dept.name,eub.userbalance,er.name ername from " +
				" ((eluser eu left join DEPARTMENT dept on  eu.depid=dept.id)" +
				" left  join   eluser_balance eub on eu.id=eub.userid) " +
				"  left join elrole er on er.id=eu.role" +
				" where "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"  ";
		if(eluser==null){
			sql+=" and eu.realname like '%"+""+"%'";
			sql+=" and eu.username like '%"+""+"%'";
		}
		else{
			sql+=eluser.getRealname()==""?"":" and eu.realname like '%"+eluser.getRealname()+"%'";
			sql+=eluser.getUsername()==""?"":" and eu.username like '%"+eluser.getUsername()+"%'";
			if(eluser.getRole()!=null){
				sql+=eluser.getRole().getId()==0?"":" and er.id ="+eluser.getRole().getId()+"";
			}
		}
		sql+="  )t where rownum <= ? )" +
			" where rn>=?";
		ps=ct.prepareStatement(sql);
		ps.setInt(1, pN);
		ps.setInt(2, pS);
		rs = ps.executeQuery();
		while(rs.next()){
			Balance balance = new Balance();
			ELUser eu = new ELUser();
			eu.setId(rs.getInt(1));
			eu.setUsername(rs.getString(2));
			eu.setRealname(rs.getString(3));
			eu.setValid(rs.getBoolean(4));
			ElRole r = new ElRole();
			r.setName(rs.getString(7));
			eu.setRole(r);
			Department d =new Department();
			d.setName(rs.getString(5));
			eu.setDepartment(d);
			balance.setUser(eu);
			balance.setBalance(rs.getFloat(6));
			list.add(balance);
		}
		return list;
		}catch (Exception e) {
			logger.error("查询用户列表信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public int getUserlistBalanceSize(ElNode tree, ELUser eluser,
			int sublibs) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Balance> list=new ArrayList<Balance>();
		try {
			ct = DBConnection.getConnection();
			String sql=" select count(*)  from  " +
			" (select eu.id,eu.username,eu.realname,eu.valid ,dept.name,eub.userbalance,er.name ername from " +
			" ((eluser eu left join DEPARTMENT dept on  eu.depid=dept.id)" +
			" left  join   eluser_balance eub on eu.id=eub.userid) " +
			" left join elrole er on er.id=eu.role" +
			" where "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"  ";
		if(eluser==null)
			sql+="";
		else{
			sql+=eluser.getRealname()==""?"":" and eu.realname like '%"+eluser.getRealname()+"%'";
			sql+=eluser.getUsername()==""?"":" and eu.username like '%"+eluser.getUsername()+"%'";
			if(eluser.getRole()!=null){
				sql+=eluser.getRole().getId()==0?"":" and er.id ="+eluser.getRole().getId()+"";
			}
		}
		sql+=" ) ";
		ps=ct.prepareStatement(sql);
		rs = ps.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		}
		}catch (Exception e) {
			logger.error("查询用户列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public void addbalance(Float balanceValue,int username ,int auserid)throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		int flag=0;
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1)  from eluser_balance where userid=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, username);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0) flag=1;
			}
			if(flag==0){//如果不存在余额信息
				sql = "insert into eluser_balance(userid,userbalance) values(?,?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, username);
				ps.setFloat(2, balanceValue);
				ps.executeUpdate();
				sql = "insert into eluser_Recharge_info(type,userid,Rechargedate,Addbalance,Rechargeuserid)" +
						" values(3,?,sysdate,?,?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, auserid);
				ps.setFloat(2, balanceValue);
				ps.setInt(3, username);
				ps.executeUpdate();
			}else{
				//如果存在
				sql = "update eluser_balance set userbalance=(userbalance+?) where userid =? ";
				ps=ct.prepareStatement(sql);
				ps.setFloat(1,balanceValue);
				ps.setInt(2,username);
				ps.executeUpdate();
				sql = "insert into eluser_Recharge_info(type,userid,Rechargedate,Addbalance,Rechargeuserid)" +
				" values(3,?,sysdate,?,?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, auserid);
				ps.setFloat(2, balanceValue);
				ps.setInt(3, username);
				ps.executeUpdate();
			}
			
		}catch (Exception e) {
			logger.error("手工增资失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	
	public List<RechargeInfo> getUserRechargeInfoById(int username, int type)
			throws ElException {
		// TODO Auto-generated method stub
 		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		List<RechargeInfo>  re = new ArrayList<RechargeInfo>();
		try {
			
			ct = DBConnection.getConnection();
			sql = "select rei.type,rei.rechargedate,rei.addbalance ,eu.username  euusername , " +
					" eu.realname  ,eus.username  eususername   from     " +
					" (eluser_Recharge_info  rei  left join  eluser  eu on rei.Rechargeuserid=eu.id) " +
					" left join  eluser  eus  on rei.userid=eus.id  where rei.Rechargeuserid=? and rei.type=? order by rei.rechargedate desc";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, username);
			ps.setInt(2, type);
			rs = ps.executeQuery();
			while(rs.next()){
				RechargeInfo r = new RechargeInfo();
				r.setRechargedate(rs.getTimestamp(2));
				r.setAddbalance(rs.getFloat(3));
				r.setReusername(rs.getString(5));
				r.setReuserid(rs.getString(4));
				r.setUsername(rs.getString(6));
				r.setType(rs.getInt(1));
				re.add(r);
			}
			
			
		}catch (Exception e) {
			logger.error("查询增资失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return re;
	}
	/**
	 * 查询转移金额是否大于用户余额
	 * @param userid
	 * @param balance
	 * @return
	 * @throws ElException
	 */
	public boolean checkbalance(int userid,float balance) throws ElException{
		boolean flag = false;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		try {
			ct = DBConnection.getConnection();//先查询操作用户信息是否存在
			sql = "select count(1)  from eluser_balance where userid=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0){
				
				sql="select userbalance  from eluser_balance where userid=? ";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, userid);
				rs=ps.executeQuery();
				rs.next();
				if(rs.getFloat(1)>balance){
					flag=true;
				}
				
			}else{
				
				sql = "insert into eluser_balance(userid) values(?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, userid);
				ps.executeUpdate();
				
			}
		
		
			return flag;
		}catch (Exception e) {
			logger.error("金额合理性判断失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public float getmybalance(int userid) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		float balance=0;
		try {
			ct = DBConnection.getConnection();//查询操作用户余额
			sql = "select userbalance  from eluser_balance where userid=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			if(rs.next()){
				balance=rs.getFloat(1);
				
				
			}

			return balance;
		}catch (Exception e) {
			logger.error("查询操作用户余额失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	/**
	 * 余额转移先减自己的资金
	 * @param userid
	 * @param username
	 * @param balance
	 * @throws ElException 
	 */
	public void subBalance(int userid ,int username,float balance) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		try {
			ct = DBConnection.getConnection();//查询操作用户余额
			sql = "update eluser_balance set userbalance=(userbalance-?) where userid =? ";
			ps=ct.prepareStatement(sql);
			ps.setFloat(1,balance);
			ps.setInt(2,userid);
			ps.executeUpdate();
			
				

		}catch (Exception e) {
			logger.error("余额转移减资失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	public void subaddbalance(Float balanceValue,int username ,int auserid)throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		int flag=0;
		try {
			ct = DBConnection.getConnection();
			sql = "select count(1)  from eluser_balance where userid=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, username);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)>0) flag=1;
			}
			if(flag==0){//如果不存在余额信息
				sql = "insert into eluser_balance(userid,userbalance) values(?,?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, username);
				ps.setFloat(2, balanceValue);
				ps.executeUpdate();
				sql = "insert into eluser_Recharge_info(type,userid,Rechargedate,Addbalance,Rechargeuserid)" +
						" values(2,?,sysdate,?,?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, auserid);
				ps.setFloat(2, balanceValue);
				ps.setInt(3, username);
				ps.executeUpdate();
			}else{
				//如果存在
				sql = "update eluser_balance set userbalance=(userbalance+?) where userid =? ";
				ps=ct.prepareStatement(sql);
				ps.setFloat(1,balanceValue);
				ps.setInt(2,username);
				ps.executeUpdate();
				sql = "insert into eluser_Recharge_info(type,userid,Rechargedate,Addbalance,Rechargeuserid)" +
				" values(2,?,sysdate,?,?)";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, auserid);
				ps.setFloat(2, balanceValue);
				ps.setInt(3, username);
				ps.executeUpdate();
			}
			
		}catch (Exception e) {
			logger.error("余额转移增资失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public List<Income> myIncomebyuserid(int userid, int pN, int pS) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Income> li=new ArrayList<Income>();
		String sql=" select * from (select t.*, rownum rn from( select  (1) as t,id, " +
				" type  ,Addbalance,Rechargedate  as time from   eluser_Recharge_info " +
				" where Rechargeuserid=? " +
				" union all select (2) as t,id,  type , Addbalance,Rechargedate  as time from   eluser_Recharge_info" +
				" where userid=?  and type=2 " +
				" union all " +
				" select (2) as t, so.id ,(4) as type, so.sumpeice,so.buydate  as time from  sp_order  so  where  " +
				" so.userid=? and so.buydate is not null  order by  time desc) t where rownum <= ? ) where rn>=? ";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,userid);
			ps.setInt(2,userid);
			ps.setInt(3,userid);
			ps.setInt(4,pN);
			ps.setInt(5,pS);
			rs=ps.executeQuery();
			while(rs.next()){
				Income  i = new Income();
				i.setTypeflag(rs.getInt(1));
				i.setId(rs.getInt(2));
				i.setType(rs.getInt(3));
				i.setBalance(rs.getFloat(4));
				i.setDate(rs.getTimestamp(5));
				li.add(i);	
			}
				return  li;

		}catch (Exception e) {
			logger.error("获得个人收支明细失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	public int myIncomebyuseridsize(int userid) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Income> li=new ArrayList<Income>();
		String sql=" select count(*) from( select  (1) as t,id, " +
				" type  ,Addbalance,Rechargedate  as time from   eluser_Recharge_info " +
				" where Rechargeuserid=? " +
				" union all select (2) as t,id,  type , Addbalance,Rechargedate  as time from   eluser_Recharge_info" +
				" where userid=?  and type=2 " +
				" union all " +
				" select (2) as t, so.id ,(4) as type, so.sumpeice,so.buydate  as time from  sp_order  so  where  " +
				" so.userid=? and so.buydate is not null  order by  time desc) ";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1,userid);
			ps.setInt(2,userid);
			ps.setInt(3,userid);
			rs=ps.executeQuery();
			rs.next();
				
				return  rs.getInt(1);

		}catch (Exception e) {
			logger.error("获得个人收支明细列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	/**
	 * 通过记录ID获得余额记录信息
	 * @param id
	 * @param type
	 * @return
	 * @throws ElException
	 */
	public RechargeInfo getRechargeInfoById(int id) throws ElException {
// TODO Auto-generated method stub
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	String sql="";
	RechargeInfo r = null;
	try {
		
	ct = DBConnection.getConnection();
	sql = "select rei.type,rei.rechargedate,rei.addbalance ,eu.username  euusername , " +
			" eu.realname  ,eus.username  eususername   from     " +
			" (eluser_Recharge_info  rei  left join  eluser  eu on rei.Rechargeuserid=eu.id) " +
			" left join  eluser  eus  on rei.userid=eus.id  where rei.id=?  order by rei.rechargedate desc";
		ps=ct.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		if(rs.next()){
			 r = new RechargeInfo();
			 r.setType(rs.getInt(1));
			r.setRechargedate(rs.getTimestamp(2));
			r.setAddbalance(rs.getFloat(3));
			r.setReusername(rs.getString(5));
			r.setReuserid(rs.getString(4));
			r.setUsername(rs.getString(6));
			
			
		}
		return r;
		
	}catch (Exception e) {
		logger.error("查询余额记录失败！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	
	}
	public Shopping getdempshopping(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Shopping s=null;
		try {
			String str = "";
			if(elUser!=null){
				if(elUser.getRealname()!=null&&!elUser.getRealname().equals("")){
					str+="   and eu.realname like '%"+elUser.getRealname()+"%' ";	
				}
				if(elUser.getUsername()!=null&&!elUser.getUsername().equals("")){
					
					str+=" and  eu.username like '%"+elUser.getUsername()+"%' ";
				}
				if(elUser.getSex()!=null&&!elUser.getSex().equals("")){
					
					str+=" and  eu.sex like '%"+elUser.getSex()+"%' ";
				}
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(shengri,'yyyy-MM-dd HH:mm:ss') => '"+start+"' ";
				
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(shengri,'yyyy-MM-dd HH:mm:ss') <= '"+end+"' ";
			}
			
			ct = DBConnection.getConnection();
			String sql=" select count(*)  from  eluser eu left join DEPARTMENT dept on  " +
					"eu.depid=dept.id where  "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"   "+str+" " ;
			ps=ct.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
				s = new Shopping();
				s.setCount(rs.getInt(1));
			sql="select sum(elb.userbalance)  from ( eluser_balance elb left join " +
					"  eluser eu  on  eu.id=elb.userid) left join  DEPARTMENT dept on eu.depid=dept.id " +
					"where  "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"     "+str+" ";
			ps=ct.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()){
				
				s.setBalance(rs.getFloat(1));
			}
			sql="select sum( spo.sumpeice ) from  sp_order  spo left join  eluser eu  on " +
					"eu.id=spo.userid left join DEPARTMENT dept on eu.depid=dept.id   " +
					"where  "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"   and  spo.buydate is not null   "+str+" ";
			ps=ct.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()){
				
				s.setPrice(rs.getFloat(1)+s.getPrice());
			}
			s.setAllprice(s.getBalance()+s.getPrice());
			return s;
			
			
			}catch (Exception e) {
				logger.error("查询用户列表信息失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		
		
	}
	public List<Shopping> getdempshopping(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end, int pN, int pS) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Shopping> ls=new ArrayList<Shopping>();
		try {
			
			String str = "";
			if(elUser!=null){
				if(elUser.getRealname()!=null&&!elUser.getRealname().equals("")){
					str+="   and eu.realname like '%"+elUser.getRealname()+"%' ";	
				}
				if(elUser.getUsername()!=null&&!elUser.getUsername().equals("")){
					
					str+=" and  eu.username like '%"+elUser.getUsername()+"%' ";
				}
				if(elUser.getSex()!=null&&!elUser.getSex().equals("")){
					
					str+=" and  eu.sex like '%"+elUser.getSex()+"%' ";
				}
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(shengri,'yyyy-MM-dd HH:mm:ss') => '"+start+"' ";
				
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(shengri,'yyyy-MM-dd HH:mm:ss') <= '"+end+"' ";
			}
			
			ct = DBConnection.getConnection();
			String sql="select * from ( select t.*,rownum  rn from (select distinct * from " +
					" (select  eu.id,eu.username,eu.realname  ,dept.name ,(select sum(eubl.userbalance) " +
					" from eluser_balance eubl where eubl.userid=eu.id) as  euub ," +
					" (select sum(spod.sumpeice) from sp_order  spod where  spod.buydate is not null " +
					" and spod.userid=eu.id  )assposum  from ((eluser eu left join DEPARTMENT dept on   " +
					" eu.depid=dept.id) left  join    eluser_balance eub on eu.id=eub.userid)     " +
					" left join sp_order spo on 	spo.userid=eub.userid   where " +
					"  "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"  "+str+" ))t where rownum <= ? ) where rn>=?";
		ps=ct.prepareStatement(sql);
		ps.setInt(1, pN);
		ps.setInt(2, pS);
		rs = ps.executeQuery();
		while(rs.next()){
			Shopping s = new Shopping();
			ELUser eu = new ELUser();
			eu.setId(rs.getInt(1));
			eu.setUsername(rs.getString(2));
			eu.setRealname(rs.getString(3));
			Department d =new Department();
			d.setName(rs.getString(4));
			eu.setDepartment(d);
			s.setUser(eu);
			s.setBalance(rs.getFloat(5));
			s.setPrice(rs.getFloat(6));
			s.setAllprice(s.getBalance()+s.getPrice());
			ls.add(s);
		}
		return ls;
		}catch (Exception e) {
			logger.error("查询部门用户消费信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	
	public int getdempshoppingSize(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String str = "";
			if(elUser!=null){
				if(elUser.getRealname()!=null&&!elUser.getRealname().equals("")){
					str+="   and eu.realname like '%"+elUser.getRealname()+"%' ";	
				}
				if(elUser.getUsername()!=null&&!elUser.getUsername().equals("")){
					
					str+=" and  eu.username like '%"+elUser.getUsername()+"%' ";
				}
				if(elUser.getSex()!=null&&!elUser.getSex().equals("")){
					
					str+=" and  eu.sex like '%"+elUser.getSex()+"%' ";
				}
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(shengri,'yyyy-MM-dd HH:mm:ss') => '"+start+"' ";
				
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(shengri,'yyyy-MM-dd HH:mm:ss') <= '"+end+"' ";
			}
			
			ct = DBConnection.getConnection();
			String sql="select count(*) from (select distinct * from (select  eu.id" +
					"  from ((eluser eu left join DEPARTMENT dept on  " +
					"  eu.depid=dept.id) left  join    eluser_balance eub on eu.id=eub.userid)   " +
					"  left join sp_order spo on " +
					"	spo.userid=eub.userid " +
					"  where "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+" "+str+"))";
		ps=ct.prepareStatement(sql);
		rs = ps.executeQuery();
		rs.next();
			
		return rs.getInt(1);
		}catch (Exception e) {
			logger.error("查询部门用户消费信息列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	
		public List<RechargeInfo> getUserRechargeInfoById(ElNode tree, ELUser elUser,
				int sublibs,String caozuoname,String caozuousername,int type, int pN, int pS) throws ElException {
		// TODO Auto-generated method stub
			PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		List<RechargeInfo>  re = new ArrayList<RechargeInfo>();
		String  str = "";
		if(elUser!=null){
			if(elUser.getUsername()!=null&&!elUser.getUsername().equals("")){
				
				str+= "  and eu.username like  '%"+elUser.getUsername()+"%'   ";
			}
			if(elUser.getRealname()!=null&&!elUser.getRealname().equals("")){
				
				str+= "   and  eu.realname  like '%"+elUser.getRealname()+"%'  ";
			
				
			}
			if(elUser.getRole()!=null&&elUser.getRole().getId()!=0){
				
				str+= "  and eu.role ="+elUser.getRole().getId()+" ";
				
			}
			
			
			
			
		}
		
		if(caozuoname!=null&&!caozuoname.equals("")){
			
			str+= "  and eus.realname like '%"+caozuoname+"%' ";
			
		}
		if(caozuousername!=null&&!caozuousername.equals("")){
			
			str+= "  and eus.username like '%"+caozuousername+"%' ";
			
		}
		if(type!=0){
			
			str+="  and  rei.type="+type+" ";
			
		}
		try {
			
			ct = DBConnection.getConnection();
			sql = "select * from  (select t.*,rownum rn from( " +
					" select rei.type,rei.rechargedate,rei.addbalance ,eu.username  euusername , " +
					" eu.realname  ,eus.username  eususername ,dept.name  depname,elrs.name   elrname,eus.realname  eusrealname  from     " +
					" (eluser_Recharge_info  rei  left join  eluser  eu on rei.Rechargeuserid=eu.id) " +
					" left join  eluser  eus  on rei.userid=eus.id " +
					"  left join elrole  elrs  on elrs.id = eu.role " +
					" left join  DEPARTMENT dept on dept.id=eus.depid  where  "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+" " +
					" "+str+"  order by rei.rechargedate desc)t where rownum <= ?) where rn >=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, pN);
			ps.setInt(2, pS);
			rs = ps.executeQuery();
			while(rs.next()){
				ELUser  u= new ELUser();//增资用户
				u.setRealname(rs.getString(5));
				u.setUsername(rs.getString(4));
				Department d = new Department();
				d.setName(rs.getString(7));
				ElRole er = new ElRole();
				er.setName(rs.getString(8));
				u.setDepartment(d);
				u.setRole(er);
				RechargeInfo r = new RechargeInfo();
				r.setRechargedate(rs.getTimestamp(2));
				r.setAddbalance(rs.getFloat(3));
				ELUser  u2= new ELUser();//操作用户
				u2.setUsername(rs.getString(6));
				u2.setRealname(rs.getString(9));
				r.setType(rs.getInt(1));
				r.setUser(u2);
				r.setRechargeuserid(u);
				re.add(r);
			}
			
			
		}catch (Exception e) {
			logger.error("查询增资失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return re;
		}
public int getUserRechargeInfoById(ElNode tree, ELUser elUser,int sublibs,String caozuoname,String caozuousername,int type) throws ElException {
// TODO Auto-generated method stub
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	String sql="";

String  str = "";
if(elUser!=null){
	if(elUser.getUsername()!=null&&!elUser.getUsername().equals("")){
		
		str+= "  and eu.username like  '%"+elUser.getUsername()+"%'   ";
	}
	if(elUser.getRealname()!=null&&!elUser.getRealname().equals("")){
		
		str+= "   and  eu.realname  like '%"+elUser.getRealname()+"%'  ";
	
		
	}
	if(elUser.getRole()!=null&&elUser.getRole().getId()!=0){
		
		str+= "  and eu.role ="+elUser.getRole().getId()+" ";
		
	}
}


if(caozuoname!=null&&caozuoname.equals("")){
	
	str+= "  and eus.realname like '%"+caozuoname+"%' ";
	
}
if(caozuousername!=null&&caozuousername.equals("")){
	
	str+= "  and eus.username like '%"+caozuousername+"%' ";
	
}
if(type!=0){
	
	str+="  and  rei.type="+type+" ";
	
}

try {
	
	ct = DBConnection.getConnection();
	sql = " select count(*) from( " +
			" select rei.type from     " +
			" (eluser_Recharge_info  rei  left join  eluser  eu on rei.Rechargeuserid=eu.id) " +
			" left join  eluser  eus  on rei.userid=eus.id " +
			"  left join elrole  elrs  on elrs.id = eu.role " +
			" left join  DEPARTMENT dept on dept.id=eus.depid  where  "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+" " +
			" "+str+"  order by rei.rechargedate desc)";
	ps=ct.prepareStatement(sql);
	rs = ps.executeQuery();
	rs.next();
	return rs.getInt(1);

	
	
}catch (Exception e) {
	logger.error("查询增资记录列表大小失败！", e);
	throw new ElException(e);
} finally {
	DBConnection.closeConnectInfo(ct, ps, rs);
}
}
public void updatemybalance(int userid, float balance) throws ElException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	String sql="";
	try {
		ct = DBConnection.getConnection();
		sql = "update eluser_balance set userbalance=? where userid=? ";
		ps=ct.prepareStatement(sql);
		ps.setFloat(1, balance);
		ps.setInt(2, userid);
		ps.executeUpdate();
		
	}catch (Exception e) {
		logger.error("手工增资失败！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	
}
	
	

}
