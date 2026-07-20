package com.sopia.peice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.peice.dao.PeiceDao;
import com.sopia.peice.entities.Peice;


public class PeiceDaoImpl implements PeiceDao {
	private static final Log logger = LogFactory.getLog(PeiceDaoImpl.class);
	
	public List<Peice> getMyAll(CourseType ctypeTree ,int typeid , String name, String status,
			String userid,int dprice ,int role, int pageNow,int pageSize) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Peice> css = new ArrayList<Peice>();
		String sql = "";
		String sqlW = "";
		try {
			if (name == null)
				name = "";
			else
				name = name.trim(); 
			logger.debug(dprice);
			//课程定价条件
			if(dprice == 1){//未定价课程
				sqlW = sqlW + " and  cp.status is null ";
			}
			if(dprice == 2){//审核中
				sqlW = sqlW + " and  cp.status = 3";
			}
			if(dprice == 3){//审核通过
				sqlW = sqlW + " and  cp.status = 1";
			}
			if(dprice == 4){//已定价
				sqlW = sqlW + " and  cp.status = 4";
			}

	
		
			ct = DBConnection.getConnection();
			sql="select * from (select t.*, rownum rn from ( select c.id,c.name cname,c.ctypeid, c.status,c.createtime,c.modifytime," +
				"c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description," +
				"cp.coursenowPrice , cp.courseoldPrice,ct.name,cp.status cpstatus,eu.realname" +
				" from (course c left join course_price cp on " +
				" c.id= cp.courseid ) " +
				" left join eluser eu on eu.id=c.creater ,("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")ct "+
				" where  c.creater = ? and ct.id = c.ctypeid "+
				" and c.name like ? "+sqlW+
				" order by c.createtime desc )t  where rownum <= ? ) where rn>=?"; 
			ps = ct.prepareStatement(sql);
			ps.setInt(1,Integer.valueOf(userid));
			ps.setString(2, "%"+name+"%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(17)));
				//c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				//c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(7));
				c.setHot(rs.getInt(8));
				c.setRoomstart(rs.getTimestamp(9));
				c.setRoomend(rs.getTimestamp(10));
				c.setTeacherName(rs.getString(11));
				c.setIslink(rs.getInt(12));
				c.setMainimg(rs.getString(13));
				c.setDescription(rs.getString(14));
				c.setCreater(new ELUser(1,rs.getString(19)));
				Peice p = new Peice();
				p.setCourse(c);
				p.setCoursenowPrice(rs.getInt(15));
				p.setCourseoldPrice(rs.getInt(16));
				p.setStatus(rs.getInt(18));
				
				css.add(p);
			}
			
		} catch (Exception e) {
			logger.error("获取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return css;
	}

	public int getMyAllSize(CourseType ctypeTree ,int typeid , String name, String status,
			String userid,int dprice ,int role) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "";
		String sqlW = "";
		int size = 0 ;
		try {
			if (name == null)
				name = "";
			else
				name = name.trim(); 
			//课程定价条件
			if(dprice == 1){//未定价课程
				sqlW = sqlW + " and  cp.status is null ";
			}
			if(dprice == 2){//审核中
				sqlW = sqlW + " and  cp.status = 3";
			}
			if(dprice == 3){//审核通过
				sqlW = sqlW + " and  cp.status = 1";
			}
			if(dprice == 4){//已定价
				sqlW = sqlW + " and  cp.status = 4";
			}

			ct = DBConnection.getConnection();
			sql="select count(c.id) " +
				" from ( course c left join course_price cp on " +
				" c.id=cp.courseid ) " +
				" left join eluser eu on eu.id=c.creater ,("
				+ ((ElNodeSQL) SpringContextUtil
						.getBean("elnodesql"))
						.generateSQLByTree("course_type",
								ctypeTree, true) + ")ct "+
				" where  c.creater = ? and ct.id =c.ctypeid "+
				" and c.status not in ( "+status+") and c.name like ? "+sqlW+
				" order by c.createtime desc"; 
			ps = ct.prepareStatement(sql);
			ps.setInt(1,Integer.valueOf(userid));
			ps.setString(2, "%"+name+"%"); 
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			} 
		} catch (Exception e) {
			logger.error("获取列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return size;
	}
	public List<Peice> peice_AuditList(CourseType ctypeTree ,int typeid ,String name, String status,
			 int dprice, int role,int pageNow, int pageSize) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Peice> css = new ArrayList<Peice>();
		String sql = "";
		String sqlW = "";
		try {
			if (name == null)
				name = "";
			else
				name = name.trim(); 
			//课程定价条件
			if(dprice == 1){//未定价课程
				sqlW = sqlW + " and  cp.status is null ";
			}
			if(dprice == 2){//审核中
				sqlW = sqlW + " and  cp.status = 3";
			}
			if(dprice == 3){//审核通过
				sqlW = sqlW + " and  cp.status = 1";
			}
			if(dprice == 4){//已定价
				sqlW = sqlW + " and  cp.status = 4";
			}
			String x = Integer.toString(typeid);
			String ids = createPerTypeId(ctypeTree, typeid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = typeid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String str=typeid==-1?"not in":"in";
			ct = DBConnection.getConnection();
			sql="select * from (select t.*, rownum rn from ( select c.id,c.name,c.ctypeid, c.status,c.createtime,c.modifytime," +
				"c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description," +
				"cp.coursenowPrice , cp.courseoldPrice,ct.name ctname,eu.realname,cp.status cpstatus,eus.realname eusrealname" +
				",exr.id exrid from ( course c left join (course_price cp left join eluser eus on cp.userid=eus.id) on " +
				" c.id=cp.courseid ) " +
				" left join eluser eu on eu.id=c.creater left join EXAM_ROOM  exr on  exr.courseid=c.id and classid=0,("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")ct  where  c.ctypeid=ct.id  " +
				"  and c.name like ? "+sqlW+
				" order by c.createtime desc )t  where rownum <= ? ) where rn>=?"; 
			ps = ct.prepareStatement(sql);
			
			ps.setString(1, "%"+name+"%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				//课程信息
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(17)));
				//c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				//c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(7));
				c.setHot(rs.getInt(8));
				c.setRoomstart(rs.getTimestamp(9));
				c.setRoomend(rs.getTimestamp(10));
				c.setTeacherName(rs.getString(11));
				c.setIslink(rs.getInt(12));
				c.setMainimg(rs.getString(13));
				c.setDescription(rs.getString(14));
				ExamRoom er = new ExamRoom();
				er.setId(rs.getInt(21));
				c.setEroom(er);
				ELUser eu= new ELUser();
				//创作者信息
				eu.setRealname(rs.getString(18));
				c.setCreater(eu);
				//价格信息
				Peice p = new Peice();
				p.setUserName(rs.getString(20));
				p.setCourse(c);
				p.setCoursenowPrice(rs.getFloat(15));
				p.setCourseoldPrice(rs.getFloat(16));
				p.setStatus(rs.getInt(19));	
				css.add(p);
			}
			
		} catch (Exception e) {
			logger.error("获取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return css;
	}
	public void peice_change(float peicevale, int courseid, int peicetype)
			throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection ct=null;
		int flag=0;
		String peic = "";
		try {//查询价格表里有没有该课程的价格信息
			ct = DBConnection.getConnection();
			String sql="select count(1) from course_price where courseid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			rs=ps.executeQuery();
			 if(rs.next()) {
				flag=rs.getInt(1);
			}
			 rs.close();
			 ps.close();
			try{
				if(flag==0){//如果没有，则执行增加记录语句
					if(peicetype==1){ 
						peic = "coursenowPrice"; 
					}else{ 
						peic = "courseoldPrice";
					}
					sql="insert into course_price(courseid,"+peic+",status) values(?,?,?)"; 
					ps=ct.prepareStatement(sql); 
					ps.setInt(1, courseid);
					ps.setFloat(2, peicevale); 
					ps.setInt(3,4);
					ps.executeUpdate();
					
				}else{//否则执行修改价格信息
					if(peicetype==1){ 
						peic = "coursenowPrice"; 
					}else{ 
						peic = "courseoldPrice";
					}
					sql="select status from course_price where courseid=?";

					ps=ct.prepareStatement(sql);
					ps.setInt(1, courseid);
					rs=ps.executeQuery();
					rs.next();
					if(rs.getInt(1)==1){
						sql="update course_price set "+peic+"=? where courseid=?";
					}
					else{
						sql="update course_price set "+peic+"=?,status=4 where courseid=?";
					}
					
				
					ps=ct.prepareStatement(sql);
					ps.setFloat(1, peicevale);
					ps.setInt(2, courseid);
					ps.executeUpdate();
				}
				}catch(Exception e){
					logger.error("修改价格失败！", e);
					throw new ElException(e);
				}finally {
					DBConnection.closeConnectInfo(ct, ps, rs);
				}
	
		} catch (Exception e) {
			logger.error("查询价格失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public void peice_audit(int courseid, int userid,int setstatus) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection ct=null;
		int flag=0;
		try {//查询价格表里有没有该课程的价格信息
			ct = DBConnection.getConnection();
			String sql="select count(1) from course_price where courseid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			rs=ps.executeQuery();
			 if(rs.next()) {
				flag=rs.getInt(1);
			}
			 rs.close();
			 ps.close();
			 
			 try {
				 if(flag==0){
						ct = DBConnection.getConnection();
						sql="insert into course_price(courseid,status,userid) values(?,?,?)";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, courseid);
						ps.setInt(2, setstatus);
						ps.setInt(3, userid);
						ps.executeUpdate();
				 }else{
					 	ct = DBConnection.getConnection();
						sql="update course_price set status=?,userid=? where courseid=?";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, setstatus);
						ps.setInt(2, userid);
						ps.setInt(3, courseid);
						ps.executeUpdate();
				 }
				
			} catch (Exception e) {
					logger.error("查询价格失败！", e);
					throw new ElException(e);
			} finally {
					DBConnection.closeConnectInfo(ct, ps, rs);
			}
		} catch (Exception e) {
			logger.error("价格审核失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}


	/**
	 * 查询出从ctid开始的有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String createPerTypeId(CourseType ctypeTree, int ctid) {
		if (ctypeTree != null) {
			if (ctypeTree.getId() != ctid) {
				ctypeTree = getCourseTypeById(ctypeTree.getChild(), ctid);
			}
			if (ctypeTree.getChild() != null) {
				return createTypeId(ctypeTree.getChild(), ctypeTree.getId());
			}
			return String.valueOf(ctypeTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 构建有权的课程类型ID
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @return
	 */
	private String createTypeId(List<CourseType> listType, int id) {
		String ids = id + "";
		for (CourseType type : listType) {
			ids = ids + "," + createTypeId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * 
	 * @author jiahaijiang
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private CourseType getCourseTypeById(List<CourseType> listType, int ctid) {
		// CourseType courseType=null;
		CourseType courseType = new CourseType();
		for (CourseType type : listType) {
			if (type.getId() != ctid) {
				courseType = getCourseTypeById(type.getChild(), ctid);
				if (courseType != null) {
					return courseType;
				}
			} else {
				courseType = type;
				return courseType;
			}
		}
		return courseType;
	}



	public int peice_AuditListSize(CourseType ctypeTree, int  typeid,
			String name, String status, int dprice, int role)
			throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql = "";
		String sqlW = "";
		int size = 0 ;
		try {
			if (name == null)
				name = "";
			else
				name = name.trim(); 
			//课程定价条件
			if(dprice == 1){//未定价课程
				sqlW = sqlW + " and  cp.status is null ";
			}
			if(dprice == 2){//审核中
				sqlW = sqlW + " and  cp.status = 3";
			}
			if(dprice == 3){//审核通过
				sqlW = sqlW + " and  cp.status = 1";
			}
			if(dprice == 4){//已定价
				sqlW = sqlW + " and  cp.status = 4";
			}

			String x = Integer.toString(typeid);
			String ids = createPerTypeId(ctypeTree, typeid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = typeid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			String str=typeid==-1?"not in":"in";
			ct = DBConnection.getConnection();
			sql="select count(c.id) " +
			" from ( course c left join course_price cp on " +
			" c.id=cp.courseid ) " +
			" left join eluser eu on eu.id=c.creater left join EXAM_ROOM  exr on  exr.courseid=c.id and classid=0,("
								+ ((ElNodeSQL) SpringContextUtil
										.getBean("elnodesql"))
										.generateSQLByTree("course_type",
												ctypeTree, true) + ")ct  where  c.ctypeid=ct.id and c.status not in ( "+status+" ) " +
			"  and c.name like ? "+sqlW+
				" order by c.createtime desc"; 
			ps = ct.prepareStatement(sql);
			ps.setString(1, "%"+name+"%"); 
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			} 
		} catch (Exception e) {
			logger.error("获取列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return size;
	}

	public void peice_Submit(int courseid) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection ct=null;
		int flag=0;
		try {//查询价格表里有没有该课程的价格信息
			ct = DBConnection.getConnection();
			String sql="select count(1) from course_price where courseid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			rs=ps.executeQuery();
			 if(rs.next()) {
				flag=rs.getInt(1);
			}
			 rs.close();
			 ps.close();
			 
			 try {
				 if(flag==0){
						ct = DBConnection.getConnection();
						sql="insert into course_price(courseid,status) values(?,3)";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, courseid);
						ps.executeUpdate();
				 }else{
					 	ct = DBConnection.getConnection();
						sql="update course_price set status=3 where courseid=?";
						ps=ct.prepareStatement(sql);
						ps.setInt(1, courseid);
						ps.executeUpdate();
				 }
				
			} catch (Exception e) {
					logger.error("查询价格失败！", e);
					throw new ElException(e);
			} finally {
					DBConnection.closeConnectInfo(ct, ps, rs);
			}
		} catch (Exception e) {
			logger.error("申请价格审核失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
