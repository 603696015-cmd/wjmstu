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
import com.sopia.schedule.dao.ScheduleDao;
import com.sopia.schedule.entities.Schedule;
import com.sopia.studyman.dao.impl.StudyQuizDaoImpl;

public class ScheduleDaoImpl implements ScheduleDao {

	private static final Log logger = LogFactory.getLog(ScheduleDaoImpl.class);

	public void addSchedule(Schedule schedule) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into tb_schedule "
					+ "(sc_datetime,sc_timeout,sc_topic,sc_content,sc_status,"
					+ "sc_re_client,sc_re_plan,sc_alert_time,sc_createtime,sc_userid )"
					+ "values(" +
					// "?," + // id
					"to_date(?,'yyyy-MM-dd hh24:mi:ss')," + // 1
															// "to_date('1987-11-14
															// 12:12:12',"
															// +"'yyyy-MM-dd
															// hh:mi:ss')," +
					"?," + // 2 timeout "23," +
					"?," + // 3 "'topic'," +
					"?," + // 4 "'content'," +
					"?," + // 5 "'status'," +
					"?," + // 6 "'client'," +
					"?," + // 7 "'plan'," +
					"to_date(?,'yyyy-MM-dd hh24:mi:ss')," + // 8
															// "to_date('1987-11-14
															// 12:12:12','yyyy-MM-dd
															// hh:mi:ss')," +
					"to_date(?,'yyyy-MM-dd hh24:mi:ss')," + // 9
															// "to_date('1987-11-14
															// 12:12:12','yyyy-MM-dd
															// hh:mi:ss'));" +
					"?" + // userid
					")";

			// sql="insert into tb_schedule
			// (sc_datetime,sc_timeout,sc_topic,sc_content," +
			// "sc_status,sc_re_client,sc_re_plan,sc_alert_time,sc_createtime) "
			// +
			// "values(" +
			// "to_date('2012-8-3 12:14:55','yyyy-MM-dd hh:mi:ss')," +
			// "24," +
			// "'topic'," +
			// "null," +
			// "'status'," +
			// "'client'," +
			// "'plan',"+
			// "to_date('1987/11/14 12:12:12','yyyy-MM-dd hh:mi:ss')," +
			// "to_date('1987/11/14 12:12:12','yyyy-MM-dd hh:mi:ss')" +
			// ")";

			ps = ct.prepareStatement(sql);

			ps.setString(1, schedule.getDatetime());
			ps.setString(2, schedule.getTimeout());
			ps.setString(3, schedule.getTopic());
			ps.setString(4, schedule.getContent());
			ps.setString(5, null);
			ps.setString(6, null);
			ps.setString(7, null);
			ps.setString(8, schedule.getAlertdate());
			ps.setString(9, schedule.getDatetime());
			ps.setInt(10, schedule.getUserid());
			// ps.setInt(1, 1);
			// ps.setString(2, schedule.getDatetime());
			// ps.setInt(3,Integer.valueOf(schedule.getTimeout()));
			// ps.setString(4,schedule.getTopic());
			// ps.setString(5,schedule.getContent());
			// ps.setString(6, null);
			// ps.setString(7, null);
			// ps.setString(8, null);
			// ps.setString(9, schedule.getAlertdate());
			// ps.setString(10, schedule.getDatetime());

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("日程添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public void addScheduleToDb(Schedule schedule) throws ElException {

	}

	public List<Schedule> selectMyAllSchedule(int userid) throws ElException 
	{
		List<Schedule> schedules = new ArrayList<Schedule>();
		Schedule schedule = null;// = new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select * from tb_schedule where sc_userid=?";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, userid);

			rs = ps.executeQuery();
			while (rs.next()) {
				schedule = new Schedule();

				String str = rs.getString("sc_datetime");
				schedule
						.setDatetime((str.charAt(str.length() - 2)) == '.' ? str
								.substring(0, str.length() - 2)
								: str);

				schedule.setTimeout(rs.getString("sc_timeout"));

				schedules.add(schedule);
			}
		} catch (Exception e) {
			logger.error("日程查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return schedules;
	}

	// 分页查询 查询所有日程
	public List<Schedule> selectMyAllSchedule(int userid, int pageNow,
			int pageSize) throws ElException {
		List<Schedule> schedules = new ArrayList<Schedule>();
		Schedule schedule = null;// = new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = " SELECT * FROM (SELECT A.*, ROWNUM RN "+ 
				" FROM (" +
				" SELECT  " +
				" id, " +
				" to_char(sc_datetime,'yyyy-MM-dd') sc_datetime, " +
				" sc_timeout,sc_topic,sc_content, " +
				" sc_status, " +
				" sc_re_client,sc_re_plan, " +
				" to_char(sc_alert_time,'yyyy-MM-dd') sc_alert_time," +
				" to_char(sc_createtime,'yyyy-MM-dd') sc_createtime " +
				" FROM tb_schedule where sc_userid=?) A "+ 
				" WHERE ROWNUM <= ?)" +
				" WHERE RN >= ?";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				schedule = new Schedule();
				schedule.setId(rs.getInt("id"));
				schedule.setDatetime(rs.getString("sc_datetime"));
				schedule.setTimeout(rs.getString("sc_timeout"));
				schedule.setTopic(rs.getString("sc_topic"));
				schedule.setContent(rs.getString("sc_content"));
				
				schedule.setAlertdate(rs.getString("sc_alert_time"));
				schedules.add(schedule);
			}
		} catch (Exception e) {
			logger.error("日程查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return schedules;
	}

	// 分页查询 查询 日程总数
	public int selectMyAllScheduleCount(int userid) throws ElException {
		int count = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "select count(*) from tb_schedule t where t.sc_userid=?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("日程查询COUNT出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	// 删除日程 根据id
	public void delScheduleById(int scheduleid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();

			sql = "delete from tb_schedule where id=?";

			ps = ct.prepareStatement(sql);

			ps.setInt(1, scheduleid);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("日程删除出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Schedule selectScheduleById(int scheduleid) throws ElException {
		Schedule schedule = new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			//sql = "select * from tb_schedule where id=?";
			
			sql=" select " +
					" id, " +
					" to_char(sc_datetime,'yyyy-MM-dd') sc_datetime, " +
					" sc_timeout,sc_topic,sc_content, " +
					" sc_status, " +
					" sc_re_client,sc_re_plan, " +
					" to_char(sc_alert_time,'yyyy-MM-dd') sc_alert_time, " +
					" to_char(sc_createtime,'yyyy-MM-dd') sc_createtime " +
					" from tb_schedule where id=? ";
			
			
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, scheduleid);
			rs = ps.executeQuery();
			if (rs.next()) {
				
				
				schedule.setId(rs.getInt("id"));
				schedule.setDatetime(rs.getString("sc_datetime"));
				schedule.setTimeout(rs.getString("sc_timeout"));
				schedule.setTopic(rs.getString("sc_topic"));
				schedule.setContent(rs.getString("sc_content"));
				
				schedule.setAlertdate(rs.getString("sc_alert_time"));
				
				
			}
		} catch (Exception e) {
			logger.error("日程查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return schedule;
	}

	public void updateSchedule(Schedule schedule) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " update tb_schedule  " +
					" set " +
					" sc_datetime=to_date(?,'yyyy-MM-dd hh24:mi:ss'), " +	//1
					" sc_timeout=?, " +	//2
					" sc_topic=?, " +	//3
					" sc_content=? ," +	//4
					" sc_alert_time=to_date(?,'yyyy-MM-dd hh24:mi:ss') " +	//5
					" where id =? ";	//6
			ps = ct.prepareStatement(sql);
			ps.setString(1, schedule.getDatetime());
			ps.setString(2, schedule.getTimeout());
			ps.setString(3, schedule.getTopic());
			ps.setString(4, schedule.getContent());
			ps.setString(5, schedule.getAlertdate());
			ps.setInt(6, schedule.getId());
			//System.out.print("\n>>>"+sql+"<<<<\n");
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("日程修改出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Schedule> searchScheduleByDepid(int depid, int pageNow,
			int pageSize) throws ElException {
		List<Schedule> schedules = new ArrayList<Schedule>();
		Schedule schedule = null;// = new Schedule();
		// Schedule schedule=new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			// sql="select * from tb_schedule where sc_userid in (select id from
			// eluser where depid = ? and valid=1 )";
			sql = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM " +
					" (select " +
					" id, " +
					" to_char(sc_datetime,'yyyy-MM-dd') sc_datetime, " +
					" sc_timeout,sc_topic,sc_content, " +
					" sc_status, " +
					" sc_re_client,sc_re_plan, " +
					" to_char(sc_alert_time,'yyyy-MM-dd') sc_alert_time," +
					" to_char(sc_createtime,'yyyy-MM-dd') sc_createtime" +
					" from tb_schedule where sc_userid in (select id from eluser where depid = ? and valid=1 )) A "+
					" WHERE ROWNUM <= ?) " + 
					" WHERE RN >= ? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, depid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();

			while (rs.next()) 
			{
				schedule = new Schedule();
//				schedule.setId(rs.getInt("id"));
//
//				String str = rs.getString("sc_datetime");
//				schedule
//						.setDatetime((str.charAt(str.length() - 2)) == '.' ? str
//								.substring(0, str.length() - 2)
//								: str);
//
//				schedule.setTimeout(rs.getString("sc_timeout"));
				schedule.setId(rs.getInt("id"));
				schedule.setDatetime(rs.getString("sc_datetime"));
				schedule.setTimeout(rs.getString("sc_timeout"));
				schedule.setTopic(rs.getString("sc_topic"));
				schedule.setContent(rs.getString("sc_content"));
				schedule.setAlertdate(rs.getString("sc_alert_time"));
				schedules.add(schedule);
			}

		} catch (Exception e) {
			logger.error("日程部门查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return schedules;
	}

	public int searchScheduleByDepidcount(int depid) throws ElException {
		int count = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(*) from tb_schedule where sc_userid in (select id from eluser where depid = ? and valid=1 )";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, depid);

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);

			}

		} catch (Exception e) {
			logger.error("日程部门COUNT查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public List<Schedule> searchScheduleByDepid(int lid, int rid, int pageNow,
			int pageSize) throws ElException {
		List<Schedule> schedules = new ArrayList<Schedule>();
		Schedule schedule = null;// = new Schedule();
		// Schedule schedule=new Schedule();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			// sql="select * from tb_schedule where sc_userid in (select id from
			// eluser where depid = ? and valid=1 )";
//			sql = " SELECT * FROM (SELECT A.*, ROWNUM RN "+ 
//				" FROM (" +
//				" select * " +
//				" from tb_schedule where sc_userid in (select id from eluser where valid=1 "+ 
//				" and depid in"+ 
//				" (select id from department where lid >= ? and rid <= ?)   )) A " +
//				" WHERE ROWNUM <= ?)" + 
//				" WHERE RN >= ?";
			
			sql = " SELECT * FROM (SELECT A.*, ROWNUM RN "+ 
			" FROM (" +
			" select  " +
			" id, " +
			" to_char(sc_datetime,'yyyy-MM-dd') sc_datetime, " +
			" sc_timeout,sc_topic,sc_content, " +
			" sc_status, " +
			" sc_re_client,sc_re_plan, " +
			" to_char(sc_alert_time,'yyyy-MM-dd') sc_alert_time," +
			" to_char(sc_createtime,'yyyy-MM-dd') sc_createtime" +
			" from tb_schedule where sc_userid in (select id from eluser where valid=1 "+ 
			" and depid in"+ 
			" (select id from department where lid >= ? and rid <= ?)   )) A " +
			" WHERE ROWNUM <= ?)" + 
			" WHERE RN >= ?";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);

			rs = ps.executeQuery();

			while (rs.next()) {
				schedule = new Schedule();
				schedule.setId(rs.getInt("id"));
				schedule.setDatetime(rs.getString("sc_datetime"));
				schedule.setTimeout(rs.getString("sc_timeout"));
				schedule.setTopic(rs.getString("sc_topic"));
				schedule.setContent(rs.getString("sc_content"));
				schedule.setAlertdate(rs.getString("sc_alert_time"));
				schedules.add(schedule);
			}

		} catch (Exception e) {
			logger.error("日程部门查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return schedules;
	}

	public int searchScheduleByDepidcount(int lid, int rid) throws ElException {
		int count = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			// sql="select count(*) from tb_schedule where sc_userid in (select
			// id from eluser where depid = ? and valid=1 )";
			sql = "select count(*) from tb_schedule where sc_userid in "
					+ " ("
					+ "   select id from eluser where valid=1 and depid in "
					+ " ( select id from department where lid >= ? and rid <= ?)"
					+ " ) ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, lid);
			ps.setInt(2, rid);

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);

			}

		} catch (Exception e) {
			logger.error("日程部门COUNT查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}
	
	
	
	public List<Schedule> selectMyAllSchedule(Schedule schedule,int pageNow,int pageSize)throws ElException
	{
		List<Schedule> schedules = new ArrayList<Schedule>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin = "";
		String sqlEnd = "";
		String sql = " ";
		try {
			ct = DBConnection.getConnection();

			sqlBegin = " SELECT * FROM (SELECT A.*, ROWNUM RN "+ 
				" FROM (" +
				" SELECT  " +
				" id, " +
				" to_char(sc_datetime,'yyyy-MM-dd') sc_datetime, " +
				" sc_timeout,sc_topic,sc_content, " +
				" sc_status, " +
				" sc_re_client,sc_re_plan, " +
				" to_char(sc_alert_time,'yyyy-MM-dd') sc_alert_time," +
				" to_char(sc_createtime,'yyyy-MM-dd') sc_createtime " +
				" FROM tb_schedule where sc_userid=?" ;
			
			sqlEnd=" ) A "+ 
				" WHERE ROWNUM <= ?)" +
				" WHERE RN >= ?";

			
			
			
			ps = ct.prepareStatement(sqlBegin+sql+sqlEnd);

			ps.setInt(1, schedule.getId());
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				schedule = new Schedule();
				schedule.setId(rs.getInt("id"));
				schedule.setDatetime(rs.getString("sc_datetime"));
				schedule.setTimeout(rs.getString("sc_timeout"));
				schedule.setTopic(rs.getString("sc_topic"));
				schedule.setContent(rs.getString("sc_content"));
				
				schedule.setAlertdate(rs.getString("sc_alert_time"));
				schedules.add(schedule);
			}
		} catch (Exception e) {
			logger.error("日程查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return schedules;
	}
	public int selectMyAllScheduleCount(Schedule schedule)throws ElException
	{
		int count = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlBegin="";
		String sql = " ";
		try {
			ct = DBConnection.getConnection();

			sqlBegin = "select count(*) from tb_schedule t where t.sc_userid=?";

			
			
			
			ps = ct.prepareStatement(sqlBegin+sql);
			ps.setInt(1, schedule.getId());

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("日程查询COUNT出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

}
