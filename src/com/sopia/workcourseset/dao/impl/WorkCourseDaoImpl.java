package com.sopia.workcourseset.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.BaseDataType;
import com.sopia.duman.entities.BaseDatat;
import com.sopia.studyman.entities.MyRoom;
import com.sopia.workcourseset.dao.WorkCourseDao;
import com.sopia.workcourseset.entity.WorkCourse;

public class WorkCourseDaoImpl  implements WorkCourseDao{
	private static final Log logger = LogFactory.getLog(WorkCourseDaoImpl.class);
	
	public List<WorkCourse> listWorkCourse(int pageNow, int pageSize) throws ElException {
		List<WorkCourse> workCourses = new ArrayList<WorkCourse>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select c.name, wc.isuse, wc.id, tmp.name ,wc.WORK_ANNIU_NAME,wc.DESCRIPTION,wc.work_type  \n" +
						"  from work_course wc,\n" + 
						"       course c,\n" + 
						"       (select bdt.id, bdt.name\n" + 
						"          from basedatatype bdt" + 
						"        ) tmp\n" + 
						" where wc.work_type = tmp.id(+)\n" + 
						"   and wc.work_course_id = c.id(+) ";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				WorkCourse wc=new WorkCourse();
				wc.setCoursename(rs.getString(1));
				wc.setIsuse(rs.getInt(2));
				wc.setId(rs.getInt(3));
				wc.setWorkTypeName(rs.getString(4));
				wc.setWork_anniu_name(rs.getString(5));
				wc.setDescription(rs.getString(6));
				wc.setWork_type(rs.getInt(7));
				workCourses.add(wc);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return workCourses;
	}
	
	public List<WorkCourse> listWorkCourse2(int pageNow, int pageSize) throws ElException {
		List<WorkCourse> workCourses = new ArrayList<WorkCourse>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select c.name, wc.isuse, wc.id, tmp.name ,wc.WORK_ANNIU_NAME,wc.DESCRIPTION,wc.work_type  \n" +
						"  from work_course wc,\n" + 
						"       course c,\n" + 
						"       (select bdt.id, bdt.name\n" + 
						"          from basedatatype bdt" + 
						"        ) tmp\n" + 
						" where wc.work_type = tmp.id(+)\n" + 
						"   and wc.work_course_id = c.id(+) and wc.isuse=1";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				WorkCourse wc=new WorkCourse();
				wc.setCoursename(rs.getString(1));
				wc.setIsuse(rs.getInt(2));
				wc.setId(rs.getInt(3));
				wc.setWorkTypeName(rs.getString(4));
				wc.setWork_anniu_name(rs.getString(5));
				wc.setDescription(rs.getString(6));
				wc.setWork_type(rs.getInt(7));
				workCourses.add(wc);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return workCourses;
	}
	
	
	
	
	
	
	public ElClass getElclassInfo(String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ElClass elClass = new ElClass();
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select el.id, el.name,  el.description\n" +
						"  from elclass el\n" + 
//						" where el.name like '%"+name+"%'";
						" where el.name = '"+name+"'";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				elClass.setId(rs.getInt(1));
				elClass.setName(rs.getString(2));
				elClass.setDescription(rs.getString(3));
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elClass;
	}
	
	public WorkCourse getInfoById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		WorkCourse wc=new WorkCourse();
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select wc.id,c.name, wc.isuse, tmp.name,wc.description,wc.work_anniu_name ,wc.work_course_id \n" +
						"  from work_course wc,\n" + 
						"       course c,\n" + 
						"       (select bdt.id, bdt.name \n" + 
						"          from basedatatype bdt \n" + 
						"         ) tmp\n" + 
						" where wc.work_type = tmp.id(+)\n" + 
						"   and wc.work_course_id = c.id(+) and wc.id="+id+"";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				wc.setCoursename(rs.getString(2));
				wc.setIsuse(rs.getInt(3));
				wc.setId(rs.getInt(1));
				wc.setWorkTypeName(rs.getString(4));
				wc.setDescription(rs.getString(5));
				wc.setWork_anniu_name(rs.getString(6));
				wc.setWork_course_id(rs.getInt(7));
				
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return wc;
	}

	public List<Course> listWorkCourseByClass(int elclassId) throws ElException{
		List<Course> Courses = new ArrayList<Course>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select c.id,c.name,c.credit\n" +
						"  from course c, class_course cc\n" + 
						" where cc.courseid = c.id(+)\n" + 
						"   and cc.classid = "+elclassId+"";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c=new Course();
				c.setId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setCredit(rs.getInt(3));
				Courses.add(c);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return Courses;
		
	}

	public Course CourseById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Course c=new Course();
		try {
			ct = DBConnection.getConnection();
			String sql ="select c.id,c.name from course c where id="+id+"";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				c.setId(rs.getInt(1));
				c.setName(rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
	}
	
	/**
	 * 编辑基础数据
	 * 
	 * @param bd
	 * @throws ElException
	 */
	public void updateWorkCourse(WorkCourse wc) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(
							"update work_course wc\n" +
							"   set wc.isuse           = ?,\n" + 
							"       wc.work_course_id  = ?,\n" + 
							"       wc.description     = ?,\n" + 
							"       wc.work_anniu_name = ?\n" + 
							" where wc.id = ?"
);
			ps.setInt(1, wc.getIsuse());
			ps.setInt(2, wc.getWork_course_id());
			ps.setString(3, wc.getDescription());
			ps.setString(4, wc.getWork_anniu_name());
			ps.setInt(5, wc.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("编辑基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 删除基础数据
	 * 
	 * @param id
	 * @throws ElException
	 */
	public void delWorkCourse(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int typeid = 0;
			int sortid = 0;
			ps = ct
					.prepareStatement("select typeid,sortid from basedatat where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				typeid = rs.getInt("typeid");
				sortid = rs.getInt("sortid");
			}
//			ps = ct
//					.prepareStatement("update basedatat set status=1 where id=?");
			ps = ct
				.prepareStatement("delete from basedatat where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			// 更新所有比他大的sort，往上移动
			if (sortid > 0) {
				ps = ct
						.prepareStatement("update basedatat set sortid=sortid-1 where typeid=? and sortid>?");
				ps.setInt(1, typeid);
				ps.setInt(2, sortid);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("删除基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	
	public void addWorkCourse(WorkCourse wc) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(
					"insert into work_course\n" +
					"  (id, work_type, work_course_id, isuse, description, work_anniu_name)\n" + 
					"values\n" + 
					"  (work_course_sequence.nextval, ?, ?, ?, ?, ?)");
			ps.setInt(1, wc.getWork_type());
			ps.setInt(2, wc.getWork_course_id());
			ps.setInt(3, wc.getIsuse());
			ps.setString(4, wc.getDescription());
			ps.setString(5, wc.getWork_anniu_name());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("添加课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public ExamRoom courseByRoom(int classid,int courseid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ExamRoom examRoom=new ExamRoom();
		try {
			ct = DBConnection.getConnection();
			String sql =
				"select cc.classid, cc.courseid, cc.eroomid\n" +
				"  from class_course cc\n" + 
				" where cc.classid = ?\n" + 
				"   and cc.courseid = ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				examRoom.setClassid(rs.getInt(1));
				examRoom.setCourse(new Course(rs.getInt(2)));
	            examRoom.setEroomid(rs.getInt(3));	
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return examRoom;
	}
	
	//根据课程类型和培训班id得到相关数据
	public int getCourseid(int type) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int courseid=0;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select "+
						"       wc.work_course_id \n" + 
						"  from work_course wc \n" + 
						"  where wc.work_type = ?";

			ps = ct.prepareStatement(sql);
			ps.setInt(1, type);
			rs = ps.executeQuery();
			while (rs.next()) {
				courseid=rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return courseid;
	}
	
	public int getEroomid(int courseid,int classid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int eroomid=0;
		try {
			ct = DBConnection.getConnection();
			String sql ="select id from exam_room where courseid=? and classid=?";
						

			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				eroomid=rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return eroomid;
	}
	
	public int getEpid(int roomid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int epid=0;
		try {
			ct = DBConnection.getConnection();
			String sql ="select epid from exam_reps where roomid=?";
						

			ps = ct.prepareStatement(sql);
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			while (rs.next()) {
				epid=rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return epid;
	}
	
	
	public List<BaseDataType> getBaseTypeList() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDataType> bdtList= new ArrayList<BaseDataType>();
		
		try {
			ct = DBConnection.getConnection();
			String sql ="select id,name from basedatatype";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				BaseDataType bdt =new BaseDataType();
				bdt.setId(rs.getInt(1));
				bdt.setName(rs.getString(2));
				bdtList.add(bdt);
			}
			
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return bdtList;
	}
	
	//sd0110
	public Map<String,List<BaseDatat>> getBaseTypeAndDataList() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDataType> bdtList= new ArrayList<BaseDataType>();
		List<BaseDatat> bdList= null;
		Map<String,List<BaseDatat>> bdtAndbd  =  new HashMap<String,List<BaseDatat>>();
		
		try {
			ct = DBConnection.getConnection();
			String sql ="select bdt.id,bdt.name from work_course wc ,basedatatype bdt where bdt.id=wc.work_type(+) and wc.isuse=1";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				BaseDataType bdt =new BaseDataType();
				bdt.setId(rs.getInt(1));
				bdt.setName(rs.getString(2));
				bdtList.add(bdt);
				
			}
			System.out.println(bdtList);
			for(int i=0;i<bdtList.size();i++){
				sql ="select  id,basevalue from Basedatat where typeid=?";

				ps = ct.prepareStatement(sql);
				ps.setInt(1, bdtList.get(i).getId());
				rs = ps.executeQuery();
				bdList = new ArrayList<BaseDatat>();
				while (rs.next()) {
					BaseDatat bd =new BaseDatat();
					bd.setId(rs.getInt(1));
					bd.setBasevalue(rs.getString(2));
					bdList.add(bd);
				}
				
				bdtAndbd.put(bdtList.get(i).getName(), bdList);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return bdtAndbd;
	}
	
	public List<WorkCourse> listWorkCourseUser() throws ElException {
		List<WorkCourse> workCourses = new ArrayList<WorkCourse>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql =
						"select  wc.work_type, tmp.name " +
						"  from work_course wc,\n" + 
						"       course c,\n" + 
						"       (select bdt.id, bdt.name\n" + 
						"          from basedatatype bdt" + 
						"        ) tmp\n" + 
						" where wc.work_type = tmp.id(+)\n" + 
						"   and wc.work_course_id = c.id(+) and wc.isuse=1";

			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				WorkCourse wc=new WorkCourse();
				wc.setWork_type(rs.getInt(1));
				wc.setWorkTypeName(rs.getString(2));
				workCourses.add(wc);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return workCourses;
	}
	
}
