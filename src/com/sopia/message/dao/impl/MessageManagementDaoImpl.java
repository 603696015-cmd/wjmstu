package com.sopia.message.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.dao.impl.CourseDaoImpl;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.courseman.entities.CourseType;
import com.sopia.duman.entities.ELUser;
import com.sopia.message.dao.MessageManagementDao;
import com.sopia.shopping.entities.Commodity;

public class MessageManagementDaoImpl implements  MessageManagementDao{
	private static final Log logger = LogFactory.getLog(CourseDaoImpl.class);
	public void auditUserComment(int id) throws ElException {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			ct = DBConnection.getConnection();
			String  sql = "update course_comment set status=1 where  id=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			logger.error("ÉóºËÁôÑÔÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}


	public void deleUserComment(int id) throws ElException {
		// TODO Auto-generated method stub
		

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try{
			ct = DBConnection.getConnection();
			String  sql = "delete course_comment  where  id=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			logger.error("ÉóºË,É¾³ýÁôÑÔÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	public CourseComment commentView(int id) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CourseComment c = null;
		try{
			ct = DBConnection.getConnection();
			String  sql = "select cc.content," +
			" cc.commentdate,eu.realname ,cc.status from course_comment cc left " +
			" join eluser  eu on cc.userid=eu.id where cc.id=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			rs.next();
			c=new CourseComment();
			ELUser el = new ELUser();
			el.setRealname(rs.getString(3));
			c.setUser(el);
			c.setContent(rs.getString(1));
			c.setStatus(rs.getInt(4));
			c.setCommentdate(rs.getTimestamp(2));
			return  c;
		} catch (Exception e) {
			logger.error("ä¯ÀÀÁôÑÔÊ§°Ü£¡", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
		
	}

	public List<CourseComment> userCommentList(CourseType tree ,CourseComment courseComment, 
			Timestamp  start,Timestamp end,int pageNow, int pageSize) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseComment> lcc = new ArrayList<CourseComment>();
		String str="";
		try{
			ct = DBConnection.getConnection();
			if(courseComment!=null){
				if(courseComment.getCommodity()!=null&&courseComment.getCommodity().getCommodityName()!=null){
					
					
					str+= "  and c.name like '%"+courseComment.getCommodity().getCommodityName()+"%'  ";
				}
				if(courseComment.getStatus()==1){
					str+="  and cc.status=1   ";
					
					
				}
				if(courseComment.getStatus()==2){
					str+="  and cc.status=2   ";
					
				}
				
				
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(cc.commentdate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(cc.commentdate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
			}
			String  sql = " select * from (select t.*, rownum rn from (select cc.userid,cc.content," +
			" cc.commentdate,cc.commentpoint,eu.realname ,cc.id,cc.status,c.name from course_comment cc left " +
			" join eluser  eu on cc.userid=eu.id left join  course c on c.id=cc.courseid,("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													tree, true) + ")ct   where cc.type=1 and c.ctypeid=ct.id  "+str+" order by" +
			" cc.commentdate  desc)t where rownum <= ? ) where rn>=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				CourseComment c = new CourseComment();
				c.setCommentpoint(rs.getInt(4));
				c.setCommentdate(rs.getTimestamp(3));
				c.setContent(rs.getString(2));
				ELUser e = new ELUser();
				e.setId(rs.getInt(1));
				e.setRealname(rs.getString(5));
				c.setUser(e);
				c.setStatus(rs.getInt(7));
				c.setId(rs.getInt(6));
				Commodity  co=new Commodity();
				co.setCommodityName(rs.getString(8));
				c.setCommodity(co);
				lcc.add(c);
				
			}
	
		} catch (Exception e) {
				logger.error("²éÑ¯¿Î³ÌÆÀÂÛ±ð±íÊ§°Ü£¡", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		return lcc;
	}

	public int userCommentListSize(CourseType tree ,CourseComment courseComment, 
			Timestamp  start,Timestamp end)
			throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str="";
		try{
			if(courseComment!=null){
				if(courseComment.getCommodity()!=null&&courseComment.getCommodity().getCommodityName()!=null){
					
					
					str+= "  and c.name like '%"+courseComment.getCommodity().getCommodityName()+"%'  ";
				}
				if(courseComment.getStatus()==1){
					str+="  and cc.status=1   ";
					
					
				}
				if(courseComment.getStatus()==2){
					str+="  and cc.status=2   ";
					
				}
				
				
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(cc.commentdate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(cc.commentdate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
			}
			ct = DBConnection.getConnection();
			String  sql = " select count(*) from (select cc.userid,cc.content," +
			" cc.commentdate,cc.commentpoint,eu.realname ,cc.id,cc.status from course_comment cc left " +
			" join eluser  eu on cc.userid=eu.id left join  course c on c.id=cc.courseid,("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													tree, true) + ")ct  where cc.type=1 and c.ctypeid=ct.id  order by" +
			" commentdate  desc)";
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
			return rs.getInt(1);
	
		} catch (Exception e) {
				logger.error("²éÑ¯¿Î³ÌÆÀÂÛ±ð±í´óÐ¡Ê§°Ü£¡", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
	}
	public List<CourseComment> userClassCommentList(ElNode tree ,CourseComment courseComment, 
			Timestamp  start,Timestamp end,int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseComment> lcc = new ArrayList<CourseComment>();
		String str="";
		try{
			ct = DBConnection.getConnection();
			if(courseComment!=null){
				if(courseComment.getCommodity()!=null&&courseComment.getCommodity().getCommodityName()!=null){
					
					
					str+= "  and cl.name like '%"+courseComment.getCommodity().getCommodityName()+"%'  ";
				}
				if(courseComment.getStatus()==1){
					str+="  and cc.status=1   ";
					
					
				}
				if(courseComment.getStatus()==2){
					str+="  and cc.status=2   ";
					
				}
				
				
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(cc.commentdate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(cc.commentdate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
			}
			String  sql = " select * from (select t.*, rownum rn from (select cc.userid,cc.content," +
			" cc.commentdate,cc.commentpoint,eu.realname ,cc.id,cc.status,cl.name from course_comment cc left " +
			" join eluser  eu on cc.userid=eu.id left join  elclass cl  on cl.id=cc.courseid,("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("elclasstype", tree, true)+") clt  where cc.type=2 and  cl.cltype = clt.id   "+str+" order by" +
			" cc.commentdate  desc)t where rownum <= ? ) where rn>=? ";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				CourseComment c = new CourseComment();
				c.setCommentpoint(rs.getInt(4));
				c.setCommentdate(rs.getTimestamp(3));
				c.setContent(rs.getString(2));
				ELUser e = new ELUser();
				e.setId(rs.getInt(1));
				e.setRealname(rs.getString(5));
				c.setUser(e);
				c.setStatus(rs.getInt(7));
				c.setId(rs.getInt(6));
				Commodity  co=new Commodity();
				co.setCommodityName(rs.getString(8));
				c.setCommodity(co);
				lcc.add(c);
				
			}
	
		} catch (Exception e) {
				logger.error("²éÑ¯¿Î³ÌÆÀÂÛ±ð±íÊ§°Ü£¡", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		return lcc;
	
	}
	public int userClassCommentListSize(ElNode tree ,CourseComment courseComment, 
			Timestamp  start,Timestamp end) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseComment> lcc = new ArrayList<CourseComment>();
		String str="";
		try{
			ct = DBConnection.getConnection();
			if(courseComment!=null){
				if(courseComment.getCommodity()!=null&&courseComment.getCommodity().getCommodityName()!=null){
					
					
					str+= "  and cl.name like '%"+courseComment.getCommodity().getCommodityName()+"%'  ";
				}
				if(courseComment.getStatus()==1){
					str+="  and cc.status=1   ";
					
					
				}
				if(courseComment.getStatus()==2){
					str+="  and cc.status=2   ";
					
				}
				
				
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(cc.commentdate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(cc.commentdate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
			}
			String  sql = " select count(1) from (select cc.userid from course_comment cc left " +
			" join eluser  eu on cc.userid=eu.id left join  elclass cl  on cl.id=cc.courseid " +
			",("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("elclasstype", tree, true)+")clt " +
			"    where cc.type=2 and  cl.cltype = clt.id "+str+" order by" +
			" cc.commentdate  desc)";
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
				return rs.getInt(1);
	
		} catch (Exception e) {
				logger.error("²éÑ¯¿Î³ÌÆÀÂÛ±ð±íÊ§°Ü£¡", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
	
	}

}
