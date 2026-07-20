package com.sopia.duman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.DUConstants;
import com.sopia.duman.dao.StationDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Station;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.studyman.entities.MyCourse;

public class StationDaoImpl implements StationDao{

	private static final Log logger = LogFactory.getLog(StationDaoImpl.class);
	public Station getStTree_level1(int pid, int stopid, boolean containStop)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Station st = null;
		try {
			st = getStRootByCid();
			ct = DBConnection.getConnection();
			st.setChild(liststChildsByPId(st.getId()));
		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return st;
	}
	
	
	public Station getStRootByCid() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Station st = new Station();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.ST_QUERY_ROOT));
			rs = ps.executeQuery();
			if (rs.next()) {
				st = new Station(rs.getInt(1), rs.getString(2));
				st.setDescription(rs.getString(3));
				st.setManager(new ELUser(rs.getInt(5), rs.getString(11)));
				st.setParent(new Station(rs.getInt(4)));
				st.setAddress(rs.getString(8));
				st.setPostalcode(rs.getString(7));
				st.setPhone(rs.getString(8));
				st.setFax(rs.getString(9));
				st.setEmail(rs.getString(10));
				st.setBh(rs.getString(12));
				st.setLid(rs.getInt(13));
				st.setRid(rs.getInt(14));
			}
		} catch (Exception e) {
			logger.error("查看岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return st;
	}
	
	
	public List<Station> liststChildsByPId(int parentid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Station> sts = new ArrayList<Station>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select s.id,s.name,s.description,s.parentid,s.manager,s.address,s.postalcode,s.phone,s.fax,s.email,s.bh,count(c.id),s.lid,s.rid " +
							"from STATION s left join station c on c.parentid = s.id and c.status!=1 where s.parentid = ? and s.status!=1 group by s.id,s.name,s.description,s.parentid,s.manager,s.address,s.postalcode,s.phone,s.fax,s.email,s.bh,s.lid,s.rid order by s.bh");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Station st = new Station(rs.getInt(1), rs.getString(2));
				st.setDescription(rs.getString(3));
				st.setParent(new Department(rs.getInt(4)));
				st.setManager(new ELUser(rs.getInt(5)));
				st.setAddress(rs.getString(6));
				st.setPostalcode(rs.getString(7));
				st.setPhone(rs.getString(8));
				st.setFax(rs.getString(9));
				st.setEmail(rs.getString(10));
				st.setBh(rs.getString(11));
				st.setClassCount(rs.getInt(12));
				st.setLid(rs.getInt(13));
				st.setRid(rs.getInt(14));
				sts.add(st);
			}
		} catch (Exception e) {
			logger.error("查看岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sts;
	}

	
	
	public Station getStTree_level1(int userid, String type, int stopid,
			boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		Station st = null;		
		if (type.equals("op")) {
			st = new Station(ElConstants.USER_OP_LIB, "可操作的岗位");
		} else {//type.equals("use")
			st = new Station(ElConstants.USER_OP_LIB, "可使用的岗位");
		};

		
		st.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select staid from station_" + type
					+ "_user where userid = ?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			List<Station> list = new ArrayList<Station>();
			List<ElNode> nlist = new ArrayList<ElNode>();
			while (rs.next()) {
				int depid = rs.getInt(1);
				if (depid == stopid && !containStop) {
				} else {
					Station depc = getStById(rs.getInt(1));// getDepTree(rs.getInt(1),
																// stopid,
																// false, 1);
					if(depc.getId()==0){
						continue;
					}
					depc.setParent(st);
					nlist .add(depc);
					list.add(depc);
				}
			}
			st.setNchild(nlist);
			st.setChild(list);
		} catch (Exception e) {
			logger.error("查看岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return st;
	}
	
	
	
	public Station getStById(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Station st = new Station();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select d.id,d.name,d.description,d.parentid,d.address,d.postalcode,d.phone,d.fax,d.email, d.bh,d.lid,d.rid,count(c.id),d.issp " +
					" from STATION d left join station c on c.parentid = d.id and c.status!=1 where d.id = ? and d.status!=1 group by d.id,d.name,d.description,d.parentid,d.address,d.postalcode,d.phone,d.fax,d.email, d.bh,d.lid,d.rid,d.issp");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				st = new Station(rs.getInt(1), rs.getString(2));
				st.setDescription(rs.getString(3));
				st.setParent(new Department(rs.getInt(4)));
				st.setAddress(rs.getString(5));
				st.setPostalcode(rs.getString(6));
				st.setPhone(rs.getString(7));
				st.setFax(rs.getString(8));
				st.setEmail(rs.getString(9));
				st.setBh(rs.getString(10));
				st.setLid(rs.getInt(11));
				st.setRid(rs.getInt(12));
				st.setClassCount(rs.getInt(13));
				st.setIssp(rs.getInt(14));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return st;
	}

	
	public List<Station> getStByIssp() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Station> list = new ArrayList<Station>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description from station where issp=1 and status!=1");
			rs = ps.executeQuery();
			while(rs.next()){
				Station st = new Station();
				st.setId(rs.getInt(1));
				st.setName(rs.getString(2));
				st.setDescription(rs.getString(3));
				list.add(st);
			}
		} catch (Exception e) {
			logger.error("获取二级页面部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	/**
	 * 检测岗位编号是否存在
	 * 
	 * @param bh
	 * @return
	 * @throws ElException
	 */
	public boolean checkStBh(String bh) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select s.bh from STATION s where s.bh =?");
			ps.setString(1, bh.trim());
			rs = ps.executeQuery();
			if (rs.next()) {
				// 存在
				return true;
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	public void addSt(Station station) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 本系统由于 有很多的包含下级的的功能，用左右值办法 很好解决代码复杂性问题。简化开发。
			// 先阅读http://www.cnblogs.com/hendy/archive/2009/10/30/1592819.html
			// 文章。我们的系统中树状结构基本是按此文档信息实现
			// 本系统中的树形结构实例。本dao实现类需要继承ElNodeDao，elNodeDao 的方法主要是更新系统左右值的信息。
			// 实体类Station 必须是ElNode 的子类，ElNodeDao 操作的主要是ElNode类
			// 下面 方法主要是更新相关树形结构表中的数据的处理。进入看注释（按住ctrl,点击方法名）。
			// 添加节点
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.ST_ADD));
			ps.setString(1, station.getName());
			ps.setString(2, station.getDescription());
			ps.setInt(3, station.getParent().getId());
			ps.setInt(4, station.getManager().getId());
			ps.setString(5, station.getAddress());
			ps.setString(6, station.getPostalcode());
			ps.setString(7, station.getPhone());
			ps.setString(8, station.getFax());
			ps.setString(9, station.getEmail());
			ps.setInt(10, station.getLid());
			ps.setInt(11, station.getRid());
			ps.setString(12, station.getBh());
			ps.setInt(13, station.getIssp());
			ps.setString(14,station.getLeibie());
			ps.setInt(15,station.getDepid());
			ps.setString(16,station.getCengji());
			ps.executeUpdate();
		
			
			
		} catch (Exception e) {
			logger.error("插入岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<ELUser> getOpUsers(String type, int stid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select eu.id,eu.realname,eu.username from station_"
							+ type
							+ "_user du left join eluser eu on eu.id = du.userid where du.staid = ?");
			ps.setInt(1, stid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查看岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		return us;
	}
	
	
	public void alterSta(Station station) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 一个部门只有一个管理员 是存储在 department 的manager字段里的 1. 2.
			// 1.----------------------------------------------------------------------
			// 起
			// 修改管理员信息
			// ELUser man = new ELUser();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_BYID));
			// ps.setInt(1, department.getId());
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// man.setId(rs.getInt(5));
			// }
			// rs.close();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_ROLE_SET));//update eluser set role = ?
			// where id = ?
			// ps.setInt(1, DUConstants.USER_ROLE_STU);
			// //DUConstants.USER_ROLE_STU = 4
			// ps.setInt(2, man.getId());
			// ps.executeUpdate();
			// ----------------------------------------------------------------------
			// 终
			// 修改节点信息
//			alterNode(ct, department, "department", "1=1");
			// 修改基本信息
			ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.ST_ALTER));
			ps.setString(1, station.getName());
			ps.setString(2, station.getDescription());
			ps.setInt(3, station.getParent().getId());
			ps.setInt(4, station.getManager().getId());
			ps.setString(5, station.getAddress());
			ps.setString(6, station.getPostalcode());
			ps.setString(7, station.getPhone());
			ps.setString(8, station.getFax());
			ps.setString(9, station.getEmail());
			ps.setString(10, station.getBh());
			ps.setInt(11, station.getIssp());
			ps.setInt(12, station.getId());
			ps.executeUpdate();

			// 2.----------------------------------------------------------------------起
			// 删除原来的管理员
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_ROLE_DEPMANGER_BACK));//update eluser
			// set role = ? where depid = ? and role = 2

			// ps.setInt(1,
			// DUConstants.USER_ROLE_STU);//DUConstants.USER_ROLE_STU=4
			// ps.setInt(2, department.getId());
			ps.executeUpdate();
			// 修改管理员信息
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.USER_ROLE_SET));//update eluser set role = ?
			// where role = ?
			// ps.setInt(1,
			// DUConstants.USER_ROLE_DEPMAN);//DUConstants.USER_ROLE_DEPMAN = 2
			// ps.setInt(2, department.getManager().getId());
			// ps.executeUpdate();
			// ----------------------------------------------------------------------终
//			ps = ct.prepareStatement("call updatetlrid('department') ");
//			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 删除部门(并入上级)
	 * @param staid
	 * @param staParentid
	 * @throws ElException
	 */
	public void deleteSta(int staid,int staParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			//1.获取子节点(更新)
			this.updateStaParent(staid, staParentid);
			//2.更新用户
			this.updateUserSta(staid, staParentid);
			//3.删除该节点
			//deleteDep(ct, depid);
			//deleteDepById(ct,depid);
			deleteStaNot(staid);
//			ps = ct.prepareStatement("call updatetlrid('department') ");
//			ps.executeUpdate();
//			ps.close();
		} catch (Exception e) {
			logger.error("修改岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 假删除部门
	 * @param id
	 * @throws ElException
	 */
	public void deleteStaAndSubNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Station dep = new Station();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.ST_QUERY_LRID_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			ps.close();
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.ST_QUERY_SUBS));
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			// 删除子部
			while (rs.next()) {
				int idc = rs.getInt(1);
//				deleteDep(ct, idc);
				deleteStaNot(idc);
			}
			rs.close();
		} catch (Exception e) {
			logger.error("假删除岗位出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 更新岗位的父节点
	 * @param staid
	 * @throws ElException
	 */
	public void updateStaParent(int staid,int staParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update station set parentid=? where parentid=?");
			ps.setInt(1, staParentid);
			ps.setInt(2, staid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新岗位的父节点出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 更新用户的staid
	 * @param staid
	 * @throws ElException
	 */
	public void updateUserSta(int staid,int staParentid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update eluser set staid=? where staid=?");
			ps.setInt(1, staParentid);
			ps.setInt(2, staid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新用户的staid出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 更新岗位以及岗位下人员的状态
	 * @param id
	 * @throws ElException
	 */
	public void deleteStaNot(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Station dep=new Station();
			ps = ct.prepareStatement("select id,bh from station where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setBh(rs.getString(2));
			}
			rs.close();
			ps.close();
			ps = ct.prepareStatement("update eluser set valid1=0 where staid=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			ps = ct.prepareStatement("update station set status=1,lid=0,rid=0,bh=? where id=?");
			ps.setString(1, dep.getBh()+"D");
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新岗位以及岗位下人员的状态出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void deleteOpusers(String type, int userid, int staid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from station_" + type
					+ "_user where userid = ? and staid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, staid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}


	public List<Course> getCourseList() throws ElException {
		List<Course> courses = new ArrayList<Course>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from course where status=5");
			rs = ps.executeQuery();
			if(rs.next()){
				Course ce = new Course();
				ce.setName(rs.getString(1));
				ce.setId(rs.getInt(2));
				ce.setCtype(new CourseType(rs.getInt(3),rs.getString(7)));
				ce.setCreatetime(rs.getTimestamp(4));
				ce.setModifytime(rs.getTimestamp(5));
				ce.setCreater(new ELUser(rs.getInt(6), rs.getString(8)));
				ce.setCredit(rs.getInt(9));
				ce.setHot(rs.getInt(10));
				ce.setRoomstart(rs.getTimestamp(11));
				ce.setRoomend(rs.getTimestamp(12));
				ce.setTeacherName(rs.getString(13));
				ce.setIslink(rs.getInt(14));
				ce.setMainimg(rs.getString(15));
				ce.setDescription(rs.getString(16));
				ce.setDuring(rs.getInt(17));
				courses.add(ce);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}


//	public int getCourseCount() throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			ct = DBConnection.getConnection();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return 0;
//	}


	public void addCourse(int courseid,int jieyeid,int staid,int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> users = new ArrayList<ELUser>();
		try {
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from eluser where staid=?");
			ps.setInt(1, staid);
			rs = ps.executeQuery();
			while(rs.next()){
				ELUser user = new ELUser();
				user.setId(rs.getInt(1));
				user.setRealname(rs.getString(2));
				users.add(user);
			}
			rs.close();
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into study_course(userid,jieyeid,courseid,classid,starttime,finishtime) values(?,?,?,?,?,?)");
			for(int i=0;i<users.size();i++){
				int userid = users.get(i).getId();
				ps.setInt(1, userid);
				ps.setInt(2, jieyeid);
				ps.setInt(3, courseid);
				ps.setInt(4,classid);
				ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}


	public List<ELUser> listUser(int staid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		List<ELUser> users = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from eluser where staid = ?");
			ps.setInt(1,staid);
			rs = ps.executeQuery();
			while(rs.next()){
				ELUser user = new ELUser();
				user.setId(rs.getInt(1));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return users;
	}


	public void addUserRoom(int examRoomid, int userid,int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into study_room(roomid,userid,classid) values(?,?,?)");
			ps.setInt(1, examRoomid);
			ps.setInt(2, userid);
			ps.setInt(3, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}


	public List<Station> getAllSta() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Station> stations = new ArrayList<Station>();
		try {
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from station where id!=1");
			rs = ps.executeQuery();
			while(rs.next()){
				Station sta = new Station();
				sta.setId(rs.getInt("id"));
				sta.setName(rs.getString("name"));
				stations.add(sta);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return stations;
	}


	public void addStationCourse(int staid, int courseid,int jieyeid,int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into stationcourse(stationid,courseid,jieye,classid) values(?,?,?,?)");
			ps.setInt(1, staid);
			ps.setInt(2, courseid);
			ps.setInt(3, jieyeid);
			ps.setInt(4, classid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}


	public List<Course> getCourseList(int staid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from stationcourse where stationid=?");
			ps.setInt(1,staid);
			rs = ps.executeQuery();
			while(rs.next()){
				Course course = new Course();
				course.setId(rs.getInt("courseid"));
				course.setJieye(rs.getInt("jieye"));
				course.setClassid(rs.getInt("classid"));
				courses.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}


	public int getClassid(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int classid = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from study_course where userid=?");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next()){
				classid = rs.getInt("classid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classid;
	}


	public ExamRoom getExamRoom(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom er = new ExamRoom();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from exam_room where courseid=?");
			ps.setInt(1,courseid);
			rs = ps.executeQuery();
			if(rs.next()){
				er.setId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return er;
	}


	public void addCourse2(int courseid, int jieyeid,int userid, int classid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into study_course(userid,jieyeid,courseid,classid,starttime,finishtime) values(?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, jieyeid);
			ps.setInt(3, courseid);
			ps.setInt(4,classid);
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}


	public List<ExamPaper> getAllExamPaper(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamPaper> eps = new ArrayList<ExamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from EXAM_REPS where roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while(rs.next()){
				ExamPaper ep =  new ExamPaper(); 
				ep.setId(rs.getInt("epid"));
				ep.setPractimes(rs.getInt("practimes"));
				ep.setPracscore(rs.getFloat("pracscore"));
				ep.setPassgrade(rs.getFloat("passgrade"));
				ep.setStuview(rs.getInt("stuview"));
				ep.setQuizlook(rs.getInt("quizlook"));
				ep.setScorelook(rs.getInt("scorelook"));
				ep.setQuizcount(rs.getInt("quizcount"));
				ep.setPassmanner(rs.getInt("passmanner"));
				eps.add(ep);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eps;
	}


	public int getStationCourse(int staid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		try {
			ct =DBConnection.getConnection();
			String sql = "select count(*) from(select st.id,st.name from station st left join eluser el on el.staid=st.id  where st.id=?) ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, staid);
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


	public int getBiXiuCourse(int staid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		try {
			ct =DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from (select sc.courseid,sc.classid from study_course sc left join stationcourse st on st.courseid=sc.courseid where sc.classid=? and st.stationid=?)");
			ps.setInt(1, classid);
			ps.setInt(2, staid);
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


	public int getBiXiuScore(int staid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select sum(credit) from (select c.credit from course c left join study_course sc on sc.courseid=c.id left join stationcourse stc on stc.courseid=c.id where stc.stationid=? and sc.classid=?)");
			ps.setInt(1, staid);
			ps.setInt(2, classid);
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
	
	public boolean checkStaName(String bh)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select bh from station where bh=?");
			ps.setString(1, bh);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	public int getParentidByBh(String bh)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from station where bh=?");
			ps.setString(1,bh);
			rs = ps.executeQuery();
			if(rs.next()){
				return  rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 1;
	}

	public boolean checkSta(String name,int depid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from station where name=? and depid=?");
			ps.setString(1, name);
			ps.setInt(2,depid);
			rs = ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	public int getStationId(int depid,String name)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from station where name=? and depid=?");
			ps.setString(1,name);
			ps.setInt(2, depid);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			DBConnection.closeConnectInfo(ct, ps, rs);
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}
	
	public List<Department> getDepInSta(int id)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from department where id!=? order by bh asc");
			ps.setInt(1,id);
			rs = ps.executeQuery();
			while(rs.next()){
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setBh(rs.getString("bh"));
				dep.setParent(new ElNode(rs.getInt("parentid")));
				dep.setParentid(rs.getInt("parentid"));
				dep.setBh(rs.getString("bh"));
				deps.add(dep);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}
	
	public void addSt(Department dep,int parentid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into STATION(name,parentid,lid,rid,bh,depid) values(?,?,?,?,?,?)";
			//ps = ct.prepareStatement(ElQuerySql.getSQL(DUConstants.ST_ADD));
			ps = ct.prepareStatement(sql);
			ps.setString(1, dep.getName());
			ps.setInt(2, parentid);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.setString(5, dep.getBh());
			ps.setInt(6,dep.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public int getStationIdByBh(String bh)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from station where bh=?");
			ps.setString(1,bh);
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 1;
	}


	public void updateSta(int staid,int classid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update station set classid=? where id=?");
			ps.setInt(1, classid);
			ps.setInt(2, staid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}


	public int getClassid2(int staid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int classid = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from station where id=?");
			ps.setInt(1, staid);
			rs = ps.executeQuery();
			if(rs.next()){
				classid = rs.getInt("classid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return classid;
	}


	public Station getStationRoot() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Station station = new Station();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from station where parentid=?");
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			if(rs.next()){
				station.setId(rs.getInt("id"));
				station.setName(rs.getString("name"));
				station.setParent(new ElNode(rs.getInt("parentid")));
				station.setLid(rs.getInt("lid"));
				station.setRid(rs.getInt("rid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return station;
	}


	public Station getStTreeById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Station station = new Station();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select el1.id,el1.name,el1.description,el1.parentid,el2.name, el1.lid,el1.rid from station el1 left join station el2 on el1.parentid = el2.id where el1.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				station.setId(rs.getInt("id"));
				station.setName(rs.getString("name"));
				station.setParent(new ElNode(rs.getInt("parentid")));
				station.setLid(rs.getInt("lid"));
				station.setRid(rs.getInt("rid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return station;
	}
	/**
	 * 删除用户可操作的权限
	 */
	public void deleteUserOpGrant(int userId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" delete from station_op_user where userid= ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public void addOpusers(String type, int userid, int staid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into station_" + type
					+ "_user(userid,staid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, staid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("查看岗位信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
}
	
	/**
	 * 删除用户可使用的权限
	 */
	public void deleteUserOpOrUseGrant(int userId,String type ,int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from station_"+type+"_user where userid= ? and depid=?");
			ps.setInt(1, userId);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除用户权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
