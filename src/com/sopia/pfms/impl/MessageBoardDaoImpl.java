package com.sopia.pfms.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.OracleBlob;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.dao.MessageBoardDao;
import com.sopia.pfms.entities.PfmsUser;

public class MessageBoardDaoImpl implements MessageBoardDao {
	private static final Log logger = LogFactory.getLog(MessageBoardDaoImpl.class);

	public List<CourseComment> messageList(int type1,CourseComment courseComment,int userid,int pageNow,int pageSize,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<CourseComment> messageList = new ArrayList<CourseComment>();
		
		try{
			ct = DBConnection.getConnection();
			if(type1 == 0){
				sqlAppend = sqlAppend + " and c.productid is null ";
			}else{
				sqlAppend = sqlAppend + " and c.productid > 0 ";
			}
			
			if(courseComment != null){
				if(courseComment.getPfmsUser().getUser().getRealname() != null && !courseComment.getPfmsUser().getUser().getRealname().equals("")){
					sqlAppend = sqlAppend + " and e.realname like  '%" +courseComment.getPfmsUser().getUser().getRealname() + "%' ";
				}
				if(courseComment.getStatus() != 0){
					sqlAppend = sqlAppend + " and c.status = '"+courseComment.getStatus()+"' ";
				}
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(commentdate,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(commentdate,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = "select c.*,rn from (select b.*,rownum rn from (select c.*,e.realname from course_comment c " +
					" join eluser e on e.id = c.userid where c.shopid=?  " + sqlAppend + 
					" order by c.commentdate desc) b where rownum <= ?) c where rn >=? " ;
				
			ps = ct.prepareStatement(sql);
			
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				CourseComment c = new CourseComment();
				c.setContent(rs.getString("content"));
				c.setId(rs.getInt("id"));
				c.setCommentdate(rs.getTimestamp("commentdate"));
				c.setStatus(rs.getInt("status"));
				PfmsUser pfmsUser = new PfmsUser();
				ELUser elUser = new ELUser();
				elUser.setRealname(rs.getString("realname"));
				pfmsUser.setUser(elUser);
				c.setPfmsUser(pfmsUser);
				messageList.add(c);
			}
		}catch(Exception e){
			logger.error("ÁôÑÔÁÐ±íÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return messageList;
	}

	public int messageCount(int type1,CourseComment courseComment,int userid,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int size = 0;
		
		try{
			ct = DBConnection.getConnection();
			if(type1 == 0){
				sqlAppend = sqlAppend + " and c.productid is null ";
			}else{
				sqlAppend = sqlAppend + " and c.productid is not null ";
			}
			if(courseComment != null){
				if(courseComment.getPfmsUser().getUser().getRealname() != null && !courseComment.getPfmsUser().getUser().getRealname().equals("")){
					sqlAppend = sqlAppend + " and e.realname like  '%" +courseComment.getPfmsUser().getUser().getRealname() + "%' ";
				}
				if(courseComment.getStatus() != 0){
					sqlAppend = sqlAppend + " and c.status = '"+courseComment.getStatus()+"' ";
				}
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(commentdate,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(commentdate,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = " select count(1) from course_comment c " +
					" join eluser e on c.userid=e.id "+
					" where c.shopid = ?  " +sqlAppend ;
				
			ps = ct.prepareStatement(sql);
			
			ps.setInt(1, userid);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				size = rs.getInt(1);
			}
		}catch(Exception e){
			logger.error("ÁôÑÔÁÐ±íÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public void deleMessageComment(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"delete from course_comment where id=?"
					);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("É¾³ýÁôÑÔÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public void courseCommentPass(CourseComment courseComment) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update course_comment set status = 1 where id = ? ";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseComment.getId());
			
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			logger.error("ÉóºËÍ¨¹ýÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public void courseCommentNotPass(CourseComment courseComment) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "update course_comment set status = 2 where id = ? ";
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseComment.getId());
			
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			logger.error("ÉóºË²»Í¨¹ýÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public List<CourseComment> allMessageList(CourseComment courseComment,int pageNow,int pageSize,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<CourseComment> messageList = new ArrayList<CourseComment>();
		
		try{
			ct = DBConnection.getConnection();
			
			if(courseComment != null){
				if(courseComment.getPfmsUser().getUser().getRealname() != null && !courseComment.getPfmsUser().getUser().getRealname().equals("")){
					sqlAppend = sqlAppend + " and e.realname like  '%" +courseComment.getPfmsUser().getUser().getRealname() + "%' ";
				}
				if(courseComment.getStatus() != 0){
					sqlAppend = sqlAppend + " and c.status = '"+courseComment.getStatus()+"' ";
				}
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(commentdate,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(commentdate,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = "select c.*,rn from (select b.*,rownum rn from (select c.id,c.status,c.content,c.commentdate,e.realname from course_comment c " +
					" join eluser e on e.id = c.userid " +
					" where  productid is null " + sqlAppend + 
					" order by c.commentdate desc) b where rownum <= ?) c where rn >=? " ;
				
			ps = ct.prepareStatement(sql);
			
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				CourseComment c = new CourseComment();
				c.setContent(rs.getString("content"));
				c.setId(rs.getInt("id"));
				c.setCommentdate(rs.getTimestamp("commentdate"));
				c.setStatus(rs.getInt("status"));
				PfmsUser pfmsUser = new PfmsUser();
				ELUser elUser = new ELUser();
				elUser.setRealname(rs.getString("realname"));
				pfmsUser.setUser(elUser);
				c.setPfmsUser(pfmsUser);
				messageList.add(c);
			}
		}catch(Exception e){
			logger.error("ÁôÑÔÁÐ±íÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return messageList;
	}
	
	
	public int allMessageCount(CourseComment courseComment,Timestamp starttime,Timestamp endtime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int size = 0;
		
		try{
			ct = DBConnection.getConnection();
			if(courseComment != null){
				if(courseComment.getPfmsUser().getUser().getRealname() != null && !courseComment.getPfmsUser().getUser().getRealname().equals("")){
					sqlAppend = sqlAppend + " and e.realname like  '%" +courseComment.getPfmsUser().getUser().getRealname() + "%' ";
				}
				if(courseComment.getStatus() != 0){
					sqlAppend = sqlAppend + " and c.status = '"+courseComment.getStatus()+"' ";
				}
			}
			
			if(starttime != null)
				sqlAppend  = sqlAppend + " and to_char(commentdate,'yyyy-MM-dd HH:mm:ss') > '" + starttime+"'";
			if(endtime != null)
				sqlAppend  = sqlAppend + " and to_char(commentdate,'yyyy-MM-dd HH:mm:ss') < '" + endtime+"'";
			
			sql = " select count(1) from course_comment c " +
					" join eluser e on c.userid=e.id "+
					" where c.productid is null " +sqlAppend ;
				
			ps = ct.prepareStatement(sql);
			
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				size = rs.getInt(1);
			}
		}catch(Exception e){
			logger.error("ÁôÑÔÁÐ±íÊ§°Ü", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

}
