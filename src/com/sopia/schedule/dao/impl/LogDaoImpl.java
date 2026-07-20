package com.sopia.schedule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.entities.Pop;
import com.sopia.schedule.dao.LogDao;
import com.sopia.studyman.dao.impl.StudyQuizDaoImpl;
import com.sopia.schedule.entities.LogStuff;
import com.sopia.schedule.entities.Logfile;
import com.sopia.schedule.entities.Schedule;


public class LogDaoImpl implements LogDao
{
	
	private static final Log logger = LogFactory.getLog(LogDaoImpl.class);
	//Logfile log=new Logfile();
	
	public int addLog(Logfile log) throws ElException
	{
		 int currentid=-1;
		 PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql ="";
		 try {
			 ct = DBConnection.getConnection();
			 sql = "insert into tb_log " +
			 		"(log_title,log_plan,log_result,log_analysis,log_co_client,log_co_plan,log_createtime,log_upload,log_userid )" +
			 		"values(" +
					"?,"+	//1		log_title		
					"?,"+	//2		log_plan
					"?,"+	//3		log_result	 	
					"?,"+	//4		log_analysis 		
					"?,"+	//5		log_co_client		 		
					"?,"+	//6		log_co_plan	 	
					"to_date(?,'yyyy-MM-dd hh24:mi:ss'),"+	//7		log_createtime     //"to_date(?,'yyyy-MM-dd hh24:mi:ss')," 
					"?,"+	//8		log_upload	 	
					"?"+	//	log_userid
			 		")";

		 	 ps = ct.prepareStatement(sql);
		 	 
			 ps.setString(1,log.getLog_title());
			 ps.setString(2,log.getLog_plan());
			 ps.setString(3,log.getLog_result());
			 ps.setString(4,log.getLog_analysis());
			 ps.setString(5, null);
			 ps.setString(6, null);
			 ps.setString(7, log.getLog_createtime());
			 ps.setString(8,null);
			 ps.setInt(9,log.getLog_userid());

			 ps.executeUpdate();
			 
			 //---------------------
			 sql="select tb_log_sequence.currval  from dual";
			 ps = ct.prepareStatement(sql);
			 rs=ps.executeQuery();
			 if(rs.next())
			 {
				 currentid=rs.getInt(1);
			 }

		 } catch (Exception e) {
			 logger.error("日志添加出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
		return currentid;
	}
	
	public void addLogStuff(int logid,String stuffaddr,String title) throws ElException
	{
		 PreparedStatement ps = null;
		 ResultSet rs = null;
		 Connection ct = null;
		 String sql ="";
		 try {
			 ct = DBConnection.getConnection();
			 sql = "insert into tb_log_stuff " +
			 		" (log_id,tb_stuffaddr,tb_title) " +
			 		" values( " +
					" ?, "+	//1		logid		
					" ?, "+	//2		stuffaddr	
					" ? "+	//3		title	 	
			 		" ) ";

		 	 ps = ct.prepareStatement(sql);
		 	 
			 ps.setInt(1,logid);
			 ps.setString(2,stuffaddr);
			 ps.setString(3,title);

			 ps.executeUpdate();
			 
			 //---------------------

		 } catch (Exception e) {
			 logger.error("日志附件添加出错！", e);
			 throw new ElException(e);
		 } finally {
			 DBConnection.closeConnectInfo(ct, ps, rs);
		 }
	}
	
//	public void addLog(Logfile log) throws ElException
//	{
//		 PreparedStatement ps = null;
//		 ResultSet rs = null;
//		 Connection ct = null;
//		 String sql ="";
//		 try {
//			 ct = DBConnection.getConnection();
//			 sql = "insert into tb_log " +
//			 		"(log_title,log_plan,log_result,log_analysis,log_co_client,log_co_plan,log_createtime,log_upload,log_userid )" +
//			 		"values(" +
//					"?,"+	//1		log_title		
//					"?,"+	//2		log_plan
//					"?,"+	//3		log_result	 	
//					"?,"+	//4		log_analysis 		
//					"?,"+	//5		log_co_client		 		
//					"?,"+	//6		log_co_plan	 	
//					"to_date(?,'yyyy-MM-dd hh24:mi:ss'),"+	//7		log_createtime     //"to_date(?,'yyyy-MM-dd hh24:mi:ss')," 
//					"?,"+	//8		log_upload	 	
//					"?"+	//	log_userid
//			 		")";
//
//		 	 ps = ct.prepareStatement(sql);
//		 	 
//			 ps.setString(1,log.getLog_title());
//			 ps.setString(2,log.getLog_plan());
//			 ps.setString(3,log.getLog_result());
//			 ps.setString(4,log.getLog_analysis());
//			 ps.setString(5, null);
//			 ps.setString(6, null);
//			 ps.setString(7, log.getLog_createtime());
//			 ps.setString(8,null);
//			 ps.setInt(9,log.getLog_userid());
//
//			 ps.executeUpdate();
//
//		 } catch (Exception e) {
//			 logger.error("日志添加出错！", e);
//			 throw new ElException(e);
//		 } finally {
//			 DBConnection.closeConnectInfo(ct, ps, rs);
//		 }
//		
//	}
	
	public List<Logfile> selectMyLogsByUserId(int userid,int pageNow,int pageSize)throws ElException
	{
		List<Logfile> list_log=new ArrayList<Logfile>();
		Logfile log=null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			// sql="select * from tb_log where log_userid=?";
			 sql="SELECT * FROM (" +
			 		"SELECT A.*, ROWNUM RN " +
			 		"FROM (" +
			 		"select * from tb_log where log_userid=?" +
			 		") A " +
			 		"WHERE ROWNUM <= ?" +
			 		")WHERE RN >= ?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, userid);
			 ps.setInt(2, pageNow);
			 ps.setInt(3, pageSize);
			 
			 
			 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				 log= new Logfile();
				 log.setId(rs.getInt("id"));
				 
				 String str=rs.getString("log_createtime");
				 log.setLog_createtime((str.charAt(str.length()-2))=='.'?str.substring(0, str.length()-2):str);
				 
				 log.setLog_title(rs.getString("log_title"));
				 
				 
				 list_log.add(log);
			}
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return list_log;
		
	}
	public int selectMyLogsByUserIdCount(int userid)throws ElException
	{
		int count=0;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="select count(*) from tb_log where log_userid=?";

			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, userid);
			 
			 
			 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	
	public void delLogByUserId(int id)throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="delete from tb_log where id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, id);
			
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public Logfile getLogByLogId(int id) throws ElException
	{
		
		Logfile log=new Logfile();;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			// sql="select * from tb_log where log_userid=?";
			 sql="select * from tb_log where id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1,id);
			 
			 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				 log.setId(rs.getInt("id"));
				 
				 String str=rs.getString("log_createtime");
				 log.setLog_createtime((str.charAt(str.length()-2))=='.'?str.substring(0, str.length()-2):str);
				 
				 log.setLog_title(rs.getString("log_title"));
				 log.setLog_plan(rs.getString("log_plan"));
				 log.setLog_result(rs.getString("log_result"));
				 log.setLog_analysis(rs.getString("log_analysis"));
				 
			}
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return log;
	}
	
	public void updateLogById(Logfile log)throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="update tb_log  set " +
			 		" log_title=?, " +	//1
			 		" log_plan=?, " +		//2
			 		" log_result=?, " +	//3
			 		" log_analysis=? " +	//4
			 		" where id =? ";	//5
			 
			 ps = ct.prepareStatement(sql);
			 ps.setString(1, log.getLog_title());
			 ps.setString(2, log.getLog_co_plan());
			 ps.setString(3, log.getLog_result());
			 ps.setString(4, log.getLog_analysis());
			 
			 ps.setInt(5, log.getId());
			 
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志修改出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<Logfile> searchLogByDepid(int lid,int rid,int pageNow,int pageSize) throws ElException
	{
		List<Logfile> list_log=new ArrayList<Logfile>();
		Logfile log =null;//= new Schedule();
		//Schedule schedule=new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select * from tb_schedule where sc_userid in (select id from eluser where depid = ? and valid=1 )";		
			 sql="SELECT * FROM (SELECT A.*, ROWNUM RN " +
			 		"FROM (select * from tb_log where log_userid in (select id from eluser where valid=1 " +
			 		" and depid in" +
			 		"(select id from department where lid >= ? and rid <= ?)   )) A " +
			 		"WHERE ROWNUM <= ?)" +
			 		"WHERE RN >= ?";
			 ps = ct.prepareStatement(sql);
			 ps.setInt(1, lid);
			 ps.setInt(2, rid);
			 ps.setInt(3, pageNow);
			 ps.setInt(4, pageSize);
			 
			 rs=ps.executeQuery();
			 
			 while (rs.next()) 
			 {
				 log = new Logfile();
				 
				 log.setId(rs.getInt("id"));
				 
				 String str=rs.getString("log_createtime");
				 log.setLog_createtime((str.charAt(str.length()-2))=='.'?str.substring(0, str.length()-2):str);
				 
				 log.setLog_title(rs.getString("log_title"));
				 log.setLog_plan(rs.getString("log_plan"));
				 log.setLog_result(rs.getString("log_result"));
				 log.setLog_analysis(rs.getString("log_analysis"));
				 
				 list_log.add(log);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志部门查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list_log;
	}
	
	public int searchLogByDepidCount(int lid,int rid) throws ElException
	{
		int count=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select count(*) from tb_log where log_userid in (select id from eluser where lid >= ? and rid <= ? and valid=1 )";	
			 sql="select count(*) from tb_log where log_userid in " +
			 		" (" +
			 		"   select id from eluser where valid=1 and depid in " +
			 		" ( select id from department where lid >= ? and rid <= ?)" +
			 		" ) ";
			 ps = ct.prepareStatement(sql);
			 ps.setInt(1, lid);
			 ps.setInt(2,rid);
			 
			 rs=ps.executeQuery();
			 
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志部门COUNT查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	
	public List<LogStuff> getListLogStuff(int logid) throws ElException
	{
		List<LogStuff> list_logStuff=new ArrayList<LogStuff>();
		LogStuff logStuff =null;//= new Schedule();
		//Schedule schedule=new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select * from tb_schedule where sc_userid in (select id from eluser where depid = ? and valid=1 )";		
			 sql="select * from tb_log_stuff  where log_id=?";
			 ps = ct.prepareStatement(sql);
			 ps.setInt(1, logid);
			 
			 
			 rs=ps.executeQuery();
			 
			 while (rs.next()) 
			 {
				 logStuff = new LogStuff();
				 
				 logStuff.setId(rs.getInt("id"));
				 logStuff.setLogid(rs.getInt("log_id"));
				 logStuff.setStuffaddr(rs.getString("tb_stuffaddr"));
				 logStuff.setTitle(rs.getString("tb_title"));
				 
				 list_logStuff.add(logStuff);
				 
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志部门查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list_logStuff;
	}
	
	public void delLogStuffByLogId(int logid) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="delete from tb_log_stuff where log_id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, logid);
			
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志附件删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void delLogStuffById(int id) throws ElException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="delete from tb_log_stuff where id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, id);
			
			 ps.executeUpdate();
			 
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志附件删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
	public int getNumOfLogStuffByLogId(int logid) throws ElException
	{
		int count=0;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 sql="select count(*) from tb_log_stuff where log_id=?";
			 
			 ps = ct.prepareStatement(sql);
			 
			 ps.setInt(1, logid);
			
			 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志附件删除出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return count;
	}
	
	
	public List<Logfile> selectMyLogsByUserId(Logfile log,int pageNow,int pageSize)throws ElException
	{
		List<Logfile> list_log=new ArrayList<Logfile>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sqlBegin = "";
	 	String sqlEnd = ""; 
	 	String sql =" ";
	 	int sqlCount=0;
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			// sql="select * from tb_log where log_userid=?";
//			 sql="SELECT * FROM (" +
//			 		"SELECT A.*, ROWNUM RN " +
//			 		"FROM (" +
//			 		"select * from tb_log where log_userid=?" +
//			 		") A " +
//			 		"WHERE ROWNUM <= ?" +
//			 		")WHERE RN >= ?";
			 
			 
			 sqlBegin ="SELECT * FROM (" +
		 		"SELECT A.*, ROWNUM RN " +
		 		"FROM (" +
		 		"select * from tb_log where log_userid=? " ;
			 
		 	sqlEnd =	" ) A " +
		 		"WHERE ROWNUM <= ?" +
		 		")WHERE RN >= ?";
			 
		 	if(log.getLog_createtime()!=null&&!log.getLog_createtime().equals(""))
		 	{
		 		if(log.getLog_createtime().length()>12)
		 			sql += " and to_char(log_createtime,'yyyy-mm-dd hh24:mm:ss')=";
		 		else
		 			sql += " and to_char(log_createtime,'yyyy-mm-dd')=";
		 		
		 		sql += " '"+log.getLog_createtime()+"' ";
		 	}
			if(log.getLog_title()!=null&&!log.getLog_title().equals(""))
			{
				sql += " and log_title='"+log.getLog_title()+"' ";
			}
		 	
			 ps = ct.prepareStatement(sqlBegin+sql+sqlEnd);
//		System.out.print("\nsql:"+sqlBegin+sql+sqlEnd+"\n");	 
			 ps.setInt(1, log.getLog_userid());
			 ps.setInt(2, pageNow);
			 ps.setInt(3, pageSize);
			 
			 
			 rs=ps.executeQuery();
			 while (rs.next()) 
			 {
				 log= new Logfile();
				 log.setId(rs.getInt("id"));
				 
				 String str=rs.getString("log_createtime");
				 log.setLog_createtime((str.charAt(str.length()-2))=='.'?str.substring(0, str.length()-2):str);
				 
				 log.setLog_title(rs.getString("log_title"));
				 
				 
				 list_log.add(log);
			}
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return list_log;
	}
	
	public int selectMyLogsByUserIdCount(Logfile log)throws ElException
	{
		int count=0;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
	 	String sqlBegin =" ";
	 	String sql = " ";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 
			 //sql="select count(*) from tb_log where log_userid=?";
			 
			 sqlBegin ="select count(*) from tb_log where log_userid=?";
			 
			if(log.getLog_createtime()!=null&&!log.getLog_createtime().equals(""))
			{
				if(log.getLog_createtime().length()>12)
					sql += " and to_char(log_createtime,'yyyy-mm-dd hh24:mm:ss')=";
				else
					sql += " and to_char(log_createtime,'yyyy-mm-dd')=";
				
				sql += " '"+log.getLog_createtime()+"' ";
			}
			if(log.getLog_title()!=null&&!log.getLog_title().equals(""))
			{
				sql += " and log_title='"+log.getLog_title()+"' ";
			}
			 
			 
			 ps = ct.prepareStatement(sqlBegin+sql);
			 
			 ps.setInt(1, log.getLog_userid());
			 
			 
			 rs=ps.executeQuery();
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志查询COUNT出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	
	public List<Logfile> searchLogByDepid(Logfile log,int lid,int rid,int pageNow,int pageSize) throws ElException
	{
		List<Logfile> list_log=new ArrayList<Logfile>();
		//Schedule schedule=new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sqlBegin = "";
		String sqlEnd = "";
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select * from tb_schedule where sc_userid in (select id from eluser where depid = ? and valid=1 )";		
//			 sql="SELECT * FROM (SELECT A.*, ROWNUM RN " +
//			 		"FROM (select * from tb_log where log_userid in (select id from eluser where valid=1 " +
//			 		" and depid in" +
//			 		"(select id from department where lid >= ? and rid <= ?)   )) A " +
//			 		"WHERE ROWNUM <= ?)" +
//			 		"WHERE RN >= ?";
			 
			 sqlBegin=" SELECT * FROM (SELECT A.*, ROWNUM RN FROM ( " +	//分页
		 		" select * from tb_log where log_userid in (select id from eluser where valid=1 " +
		 		" and depid in" +
		 		" (select id from department where lid >= ? and rid <= ?)   ) " ;//+
		 	sqlEnd=" ) A " +	//分页
		 		" WHERE ROWNUM <= ?)" +
		 		" WHERE RN >= ?";
			 
		 	
		 	if(log.getLog_createtime()!=null&&!log.getLog_createtime().equals(""))
			{
				if(log.getLog_createtime().length()>12)
					sql += " and to_char(log_createtime,'yyyy-mm-dd hh24:mm:ss')=";
				else
					sql += " and to_char(log_createtime,'yyyy-mm-dd')=";
				
				sql += " '"+log.getLog_createtime()+"' ";
			}
			if(log.getLog_title()!=null&&!log.getLog_title().equals(""))
			{
				sql += " and log_title='"+log.getLog_title()+"' ";
			}
			 
			 
			 ps = ct.prepareStatement(sqlBegin+sql+sqlEnd);
		 
			 ps.setInt(1, lid);
			 ps.setInt(2, rid);
			 ps.setInt(3, pageNow);
			 ps.setInt(4, pageSize);
	//System.out.print("\nsql:"+sqlBegin+sql+sqlEnd+"\n");		 
			 rs=ps.executeQuery();
			 
			 while (rs.next()) 
			 {
				 log = new Logfile();
				 
				 log.setId(rs.getInt("id"));
				 
				 String str=rs.getString("log_createtime");
				 log.setLog_createtime((str.charAt(str.length()-2))=='.'?str.substring(0, str.length()-2):str);
				 
				 log.setLog_title(rs.getString("log_title"));
				 log.setLog_plan(rs.getString("log_plan"));
				 log.setLog_result(rs.getString("log_result"));
				 log.setLog_analysis(rs.getString("log_analysis"));
				 
				 list_log.add(log);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志部门查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list_log;
	}
	public int searchLogByDepidCount(Logfile log,int lid,int rid) throws ElException
	{
		int count=0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sqlBegin = "";
	 	String sql ="";
	 	try 
	 	{
			 ct = DBConnection.getConnection();
			 //sql="select count(*) from tb_log where log_userid in (select id from eluser where lid >= ? and rid <= ? and valid=1 )";	
//			 sql="select count(*) from tb_log where log_userid in " +
//			 		" (" +
//			 		"   select id from eluser where valid=1 and depid in " +
//			 		" ( select id from department where lid >= ? and rid <= ?)" +
//			 		" ) ";
			 
			 sqlBegin=" select count(*) from tb_log where log_userid in " +
		 		" (" +
		 		"   select id from eluser where valid=1 and depid in " +
		 		" ( select id from department where lid >= ? and rid <= ?)" +
		 		" ) ";
			 
				
			 	if(log.getLog_createtime()!=null&&!log.getLog_createtime().equals(""))
				{
					if(log.getLog_createtime().length()>12)
						sql += " and to_char(log_createtime,'yyyy-mm-dd hh24:mm:ss')=";
					else
						sql += " and to_char(log_createtime,'yyyy-mm-dd')=";
					
					sql += " '"+log.getLog_createtime()+"' ";
				}
				if(log.getLog_title()!=null&&!log.getLog_title().equals(""))
				{
					sql += " and log_title='"+log.getLog_title()+"' ";
				}
			 
			 
			 ps = ct.prepareStatement(sqlBegin+sql);
			 ps.setInt(1, lid);
			 ps.setInt(2,rid);
			 
			 rs=ps.executeQuery();
			 
			 if (rs.next()) 
			 {
				 count=rs.getInt(1);
			 }
	
	 	} 
	 	catch (Exception e) 
	 	{
			 logger.error("日志部门COUNT查询出错！", e);
			 throw new ElException(e);
		} finally 
		{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
}
