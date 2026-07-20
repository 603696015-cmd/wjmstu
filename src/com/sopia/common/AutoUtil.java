package com.sopia.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 自动加载类，2011-8-26 基本丢弃了
 * @author Administrator
 *
 */
public class AutoUtil {
	// 得到我的课程
	private static final Log logger = LogFactory.getLog(AutoUtil.class);

	/*
	 * public int getMyCourse(int userid)throws ElException{ PreparedStatement
	 * ps = null; ResultSet rs = null; Connection ct = null; String year =
	 * Calendar.getInstance().get(Calendar.YEAR)+""; setCourse(userid, year);
	 * try { ct = DBConnection.getConnection(); ps = ct.prepareStatement("select
	 * courseid from course_apply where userid = ? and courseid <>0 order by
	 * applydate desc limit 0,1"); ps.setInt(1, userid); // ps.setString(2,
	 * "%"+year+"%"); rs = ps.executeQuery(); if(rs.next()){ int courseid =
	 * rs.getInt(1); setEroom(courseid, userid); return courseid; } } catch
	 * (Exception e) { logger.error("获取练习列表失败！", e); throw new ElException(e); }
	 * finally { DBConnection.closeConnectInfo(ct, ps, rs); } return -1; }
	 */
	// 设置课程
	public void setCourse(int userid ) throws ElException {/*
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select dep.lid,dep.rid from eluser eu,department dep where eu.id = ? and eu.depid = dep.id ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			int lid = 0, rid = 0;
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			ps = ct
					.prepareStatement("select cd.courseid from course_dep cd ,department dep where "
							+ "dep.id=cd.depid and dep.lid<=? and dep.rid>=?  order by cd.applydate desc ");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			// ps.setString(3, "%"+year+"%");
			rs = ps.executeQuery();
			while (rs.next()) {
				int courseid = rs.getInt(1);
				if (courseid != 0) {
					ps = ct
							.prepareStatement("select count(*) from course_apply where userid  = ? and courseid = ? ");
					ps.setInt(1, userid);
					ps.setInt(2, courseid);
					// ps.setString(3, "%"+year+"%");
					ResultSet rs1 = ps.executeQuery();
					int count = 0;
					if (rs1.next()) {
						count = rs1.getInt(1);
					}
					if (count == 0) {
						ps = ct
								.prepareStatement("insert into course_apply(userid,courseid,applydate,status,valid) values(?,?,?,1,1)");
						ps.setInt(1, userid);
						ps.setInt(2, courseid);
						ps.setTimestamp(3, new Timestamp(System
								.currentTimeMillis()));
						ps.executeUpdate();
					}
					rs1.close();
				}
			}
		} catch (Exception e) {
			logger.error("获取练习列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	*/}
	public void setClass(int userid ) throws ElException {/*
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select dep.lid,dep.rid from eluser eu,department dep where eu.id = ? and eu.depid = dep.id ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			int lid = 0, rid = 0;
			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			ps = ct
					.prepareStatement("select cd.classid from class_assign cd ,department dep where "
							+ "dep.id=cd.depid and dep.lid<=? and dep.rid>=?  order by cd.assigntime desc ");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			// ps.setString(3, "%"+year+"%");
			rs = ps.executeQuery();
			while (rs.next()) {
				int courseid = rs.getInt(1);
				if (courseid != 0) {
					ps = ct
							.prepareStatement("select count(*) from class_apply where userid  = ? and classid = ? ");
					ps.setInt(1, userid);
					ps.setInt(2, courseid);
					// ps.setString(3, "%"+year+"%");
					ResultSet rs1 = ps.executeQuery();
					int count = 0;
					if (rs1.next()) {
						count = rs1.getInt(1);
					}
					if (count == 0) {
						ps = ct
								.prepareStatement("insert into class_apply(userid,classid,applydate,status ) values(?,?,?,2 )");
						ps.setInt(1, userid);
						ps.setInt(2, courseid);
						ps.setTimestamp(3, new Timestamp(System
								.currentTimeMillis()));
						ps.executeUpdate();
					}
					rs1.close();
				}
			}
		} catch (Exception e) {
			logger.error("获取练习列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	*/}
	public void setEroom(int courseid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id from exam_room where  courseid=  ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				int roomid = rs.getInt(1);
				// /检查考试场次是否分给学员
				ps = ct
						.prepareStatement("select * from room_assign where courseid = ? and userid = ?");
				ps.setInt(1, courseid);
				ps.setInt(2, userid);
				ResultSet rs1 = ps.executeQuery();
				if (rs1.next()) {

				} else {
					ps = ct
							.prepareStatement("insert into room_assign(userid,courseid,roomid) values(?,?,?)");
					ps.setInt(1, userid);
					ps.setInt(2, courseid);
					ps.setInt(3, roomid);
					ps.executeUpdate();
				}
				rs1.close();
			}
		} catch (Exception e) {
			logger.error("获取练习列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int getSimid(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select epid from simexampaper where  courseid=  ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取练习列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return -1;
	}

}
