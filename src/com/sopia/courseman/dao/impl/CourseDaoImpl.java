
package com.sopia.courseman.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.ExamPaperUtil;
import com.sopia.common.OracleBlob;
import com.sopia.common.SystemConfOp;
import com.sopia.common.getFloat;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CoursePageDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseAuditDescribes;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.CourseRegistration;
import com.sopia.courseman.entities.CourseServer;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.EroomLib;
import com.sopia.courseman.entities.EroomRegistration;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.PracticePaper;
import com.sopia.courseman.entities.QuizPaper;
import com.sopia.courseman.entities.SimexamPaper;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.BaseDataTypeCourse;
import com.sopia.duman.entities.BaseDatatCourse;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;
import com.sopia.openmeetings.Rooms;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.Question;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.studyman.entities.MyRoom;

public class CourseDaoImpl implements CourseDao {
	private static final Log logger = LogFactory.getLog(CourseDaoImpl.class);

	public void addCourse(Course course) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_ADD));
			ps.setString(1, course.getName());
			ps.setInt(2, course.getCtype().getId());
			ps.setInt(3, course.getCreater().getId());
			ps.setString(4, course.getDescription());
			// ps.setFloat(5, course.getPassgrade());
			// ps.setInt(6, course.getStatus());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setInt(6, course.getCredit());
			ps.setString(7, course.getMainimg());
			ps.setInt(8, course.getIslink());
			ps.setString(9, course.getExurl());
			ps.setInt(10, course.getDuring());
			ps.setInt(11, course.getQuerytime());
			ps.setString(12, course.getTeacherinfo());
			ps.setString(13, course.getStudyplan());
			ps.setString(14, course.getTeacherName());
			ps.setString(15, course.getKj_appendix());
			ps.setString(16, course.getJy_appendix());
			ps.setInt(17, course.getCreditmod());
			ps.setInt(18, course.getNotenumber());
			ps.setTimestamp(19, course.getNotedate());
			ps.setInt(20, course.getRoom().getId());
			ps.setTimestamp(21, course.getRoomstart());
			ps.setTimestamp(22, course.getRoomend());
			ps.setInt(23, course.getTeacherId());
			//默认课程状态为 0制作中
			ps.setInt(24, 0);
			ps.setInt(25, course.getIsApplication());
			ps.setInt(26, course.getCourseForm());
			ps.setString(27, course.getWeidu()==null?"":course.getWeidu());//课程维度信息
			ps.setString(28, course.getHtml5());
			
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob(ct,"course_sequence","course","id","course_detail",course.getCourseDetail(),"添加课程详情失败");
			setblob.addContent(); 
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('course') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select course_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				course.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("添加课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 我创建的课程 根据有权限的课程类型树查找出课程
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 *            有权限的课程树
	 * @param creater
	 * @param ctid
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> listMyCourse(CourseType ctypeTree, int creater,
			int ctid, String name, int role, int pageNow, int pageSize)
			throws ElException {
		List<Course> courses = new ArrayList<Course>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();

			String x = Integer.toString(ctid);
			String ids = createPerTypeId(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName,c.astatus from course c,(select * from course_type where id in("
									+ ids + ")) ct")
					.append(" where c.ctypeid=ct.id and c.creater=?")
					// where c.ctypeid=ct.id and c.creater=?
					.append(
							"  and c.name like ? and c.islink!=4 order by c.id desc)t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, creater);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				// c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7)));
				c.setCredit(rs.getInt(9));
				c.setDuring(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setAstatus(rs.getInt(15));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	/**
	 * 我创建的课程 根据有权限的课程类型树查找出课程
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 *            有权限的课程树
	 * @param creater
	 * @param ctid
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> listMyCourse(CourseType ctypeTree, int ctid,
			String name, int role, int pageNow, int pageSize)
			throws ElException {
		List<Course> courses = new ArrayList<Course>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();

			String x = Integer.toString(ctid);
			String ids = createPerTypeId(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName from course c,(select * from course_type where id in("
									+ ids + ")) ct")
					.append(" where c.ctypeid=ct.id ")
					.append(
							"  and c.name like ?  order by c.id desc)t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				// c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7)));
				c.setCredit(rs.getInt(9));
				c.setDuring(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	/**
	 * 我创建的课程 根据有权限的课程类型树查找出课程合计
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 *            有权限的课程树
	 * @param creater
	 * @param ctid
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listMyCourseCount(CourseType ctypeTree, int creater, int ctid,
			String name, int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = name == null ? "" : name.trim();

			String x = Integer.toString(ctid);
			String ids = createPerTypeId(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) rn from (select c.id,")
					.append(
							" c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,c.credit,c.during,c.islink from course c,(select * from course_type where  id in("
									+ ids + ") )ct").append(
							" where c.ctypeid=ct.id  and c.creater=?").append(
							"  and c.name like ? and c.islink!=4 "
									+ "order by c.id desc)t ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, creater);
			ps.setString(2, "%" + name + "%");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int listMyCourseCount(CourseType ctypeTree, int ctid, String name)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) rn from (select c.id,")
					.append(
							" c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,c.credit,c.during,c.islink from course c,(select * from course_type where  id in("
									+ createPerTypeId(ctypeTree, ctid)
									+ ") )ct").append(" where c.ctypeid=ct.id")
					.append("  and c.name like ?" + "order by c.id desc)t ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
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

	public List<Course> listMyCourse(int creater, int ctid, String name,
			int pageNow, int pageSize) throws ElException {
		List<Course> courses = new ArrayList<Course>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_LRID));
			ps.setInt(1, ctid);
			int lid = 0;
			int rid = 0;

			rs = ps.executeQuery();

			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_MAN_MYLIST));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, creater);
			ps.setString(4, "%" + name + "%");
			ps.setInt(5, pageNow);
			ps.setInt(6, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				// c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7)));
				c.setCredit(rs.getInt(9));
				c.setDuring(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public int listMyCourseCount(int creater, int ctid, String name)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int x = 0;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.CTYPE_LRID));
			ps.setInt(1, ctid);
			int lid = 0;
			int rid = 0;

			rs = ps.executeQuery();

			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_MAN_MYLIST_SIZE));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, creater);
			ps.setString(4, "%" + name + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return x;
	}

	/**
	 * 查询有课程类型权限的课程
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listAllCourseFromThis(CourseType ctypeTree, int depid,
			String name, int ctid, int pageNow, int pageSize, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();

			String x = Integer.toString(ctid);
			String ids = createPerTypeId(ctypeTree, ctid);
			if (!ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
								// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg  from course c, course_type ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status = ? and c.name like ? ")
					.append(
							" and ct.id in (" + ids
									+ ") order by c.createtime desc )t ")
					.append(" where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, CourseConstants.COURSE_STATUS_OPEN);
			ps.setInt(1, status);// modify by jiahaijiang
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setIslink(rs.getInt(15));
				c.setMainimg(rs.getString(16));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	/**
	 * 查询有课程类型权限的课程
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listAllCourseFromThis(CourseType ctypeTree, int depid,
			int role, Course course, int ctid, int pageNow, int pageSize,
			String status, String sqlw) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();

//			String x = Integer.toString(ctid);
			String ids = courseTypeById(ctypeTree, ctid);
			// if(role != 1 && !ids.equals(x) )//角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			// ids = ctid == 1?ids.substring(x.length()+1,ids.length()):ids;
			// //当id等于虚拟根时,从所有的id中去掉虚拟根id

			String conditions = "";
			if (course != null) {
				if (course.getName() != null && course.getName() != null
						&& !course.getName().equals("")) {
					conditions = conditions + " and c.name like '%"
							+ course.getName() + "%' ";
				}
				if (course.getHot() != 0) {
					conditions = conditions + " and c.hot = 2 ";
				}
			}
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description,c.astatus,c.during  from course c, course_type ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") " + conditions)
					//
					.append(
							" and ct.id in (" + ids + ") " + sqlw
									+ " order by c.createtime desc )t ")
					.append(" where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			System.out.println(buffer.toString()+"======688");
			// ps.setInt(1, CourseConstants.COURSE_STATUS_OPEN);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setIslink(rs.getInt(15));
				c.setMainimg(rs.getString(16));
				c.setDescription(rs.getString(17));
				c.setAstatus(rs.getInt(18));
				c.setDuring(rs.getInt(19));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	public List<Course> listAllCourseFromThisStatus(CourseType ctypeTree,
			int depid, int role, String name, int ctid, int pageNow,
			int pageSize, String status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
//			Department dep = new Department();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//			ps.setInt(1, depid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				dep.setId(rs.getInt(1));
//				dep.setLid(rs.getInt(2));
//				dep.setRid(rs.getInt(3));
//			}
//			rs.close();

//			String x = Integer.toString(ctid);
			// String ids = courseTypeById(ctypeTree,ctid);
//			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
//											// ,当角色不为1时ids的只有一个根节点时也不截取
//				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
//						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description,c.during from course c, ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") and c.name like ? ")//
					// .append(" and ct.id in ("+ids+") order by c.createtime
					// desc )t ")
					.append(" order by c.createtime desc )t ").append(
							" where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%"+name+"%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setIslink(rs.getInt(15));
				c.setMainimg(rs.getString(16));
				c.setDescription(rs.getString(17));
				c.setDuring(rs.getInt(18));
				c.setCpagesize(this.getCpsize(c.getId()));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}
	/**
	 * 获取课程列表
	 * @param ctypeTree
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseFromThisStatus(CourseType ctypeTree, String name,int pageNow,
			int pageSize, String status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description,c.astatus  from course c, ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") and c.name like ? ")
					.append(" order by c.createtime desc )t ").append(
							" where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%"+name+"%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setIslink(rs.getInt(15));
				c.setMainimg(rs.getString(16));
				c.setDescription(rs.getString(17));
				c.setAstatus(rs.getInt(18));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}
	
	/**
	 * 获取课程列表
	 * @param ctypeTree
	 * @param course
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseFromThisStatus1(CourseType ctypeTree, Course course,int pageNow,
			int pageSize, String status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		String sqlAppend = "";
		
		if(course != null){
			if(course.getName() != null && !course.getName().equals(""))
				sqlAppend = sqlAppend + " and c.name like '%" + course.getName() + "%' ";
			if(course.getBegintime() != null)
				sqlAppend  = sqlAppend + " and to_char(c.createtime,'yyyy-MM-dd HH:mm:ss') > '" + course.getBegintime()+"'";
			if(course.getEndtime() != null)
				sqlAppend  = sqlAppend + " and to_char(c.createtime,'yyyy-MM-dd HH:mm:ss') < '" + course.getEndtime()+"'";
			if(course.getStatus_type() != null && !course.getStatus_type().equals("") && Integer.parseInt(course.getStatus_type())>=0)
				sqlAppend = sqlAppend + " and c.status = '" + course.getStatus_type() + "' ";
			if(course.getCreater() != null && course.getCreater().getRealname() != null && !course.getCreater().getRealname().equals(""))
				sqlAppend = sqlAppend + " and u.realname like '%" + course.getCreater().getRealname() + "%' ";
		}
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description  from course c, ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
				//	.append(
				//			" eluser u,department dep where exists(select status from course) ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") ")
					.append(sqlAppend)
					.append(" order by c.createtime desc )t ").append(
							" where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
//			ps.setString(1, "%"+name+"%");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setIslink(rs.getInt(15));
				c.setMainimg(rs.getString(16));
				c.setDescription(rs.getString(17));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	/**
	 * 查询有课程类型权限的课程合计
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int listAllCourseSizeFromThis(CourseType ctypeTree, int depid,
			int role, Course course, int ctid, String status, String sqlw)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// Department dep = new Department();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			// ps.setInt(1, depid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// dep.setId(rs.getInt(1));
			// dep.setLid(rs.getInt(2));
			// dep.setRid(rs.getInt(3));
			// }
			// rs.close();

			String x = Integer.toString(ctid);
			String ids = courseTypeById(ctypeTree, ctid);
			// if(role != 1 && !ids.equals(x) )//角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			// ids = ctid == 1?ids.substring(x.length()+1,ids.length()):ids;
			// //当id等于虚拟根时,从所有的id中去掉虚拟根id

			String conditions = "";
			if (course != null) {
				if (course.getName() != null && course.getName() != null
						&& !course.getName().equals("")) {
					conditions = conditions + " and c.name like '%"
							+ course.getName() + "%' ";
				}
				if (course.getHot() != 0) {
					conditions = conditions + " and c.hot = 2 ";
				}
			}
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot  from course c, course_type ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") " + conditions)//
					.append(
							" and ct.id in (" + ids + ") " + sqlw
									+ " order by c.createtime desc )t ");

			ps = ct.prepareStatement(buffer.toString());
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int listAllCourseSizeFromThisStatus(CourseType ctypeTree, int depid,
			int role, String name, int ctid, String status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();

//			String x = Integer.toString(ctid);
//			String ids = courseTypeById(ctypeTree, ctid);
//			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
//											// ,当角色不为1时ids的只有一个根节点时也不截取
//				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
//						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot  from course c, ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")  ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") and c.name like ? ")//
					.append(
							" order by c.createtime desc )t ");

			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%"+name+"%");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 获取课程列表数量
	 * @param ctypeTree
	 * @param name
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int listCourseSizeFromThisStatus(CourseType ctypeTree, String name, String status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot  from course c, ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")  ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") and c.name like ? ")//
					.append(
							" order by c.createtime desc )t ");

			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%"+name+"%");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("获取课程列表数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 获取课程列表数量
	 * @param ctypeTree
	 * @param course
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int listCourseSizeFromThisStatus1(CourseType ctypeTree, Course course, String status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlAppend = "";
		if(course != null){
			if(course.getName() != null && !course.getName().equals(""))
				sqlAppend = sqlAppend + " and c.name like '%" + course.getName() + "%' ";
			if(course.getBegintime() != null)
				sqlAppend  = sqlAppend + " and to_char(c.createtime,'yyyy-MM-dd HH:mm:ss') > '" + course.getBegintime()+"'";
			if(course.getEndtime() != null)
				sqlAppend  = sqlAppend + " and to_char(c.createtime,'yyyy-MM-dd HH:mm:ss') < '" + course.getEndtime()+"'";
			if(course.getStatus_type() != null && !course.getStatus_type().equals("") && Integer.parseInt(course.getStatus_type())>=0)
				sqlAppend = sqlAppend + " and c.status = '" + course.getStatus_type() + "' ";
			if(course.getCreater() != null && course.getCreater().getRealname() != null && !course.getCreater().getRealname().equals(""))
				sqlAppend = sqlAppend + " and u.realname like '%" + course.getCreater().getRealname() + "%' ";
		}
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot  from course c, ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")  ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ")  ")
					.append(sqlAppend)
					.append(
							" order by c.createtime desc )t ");

			ps = ct.prepareStatement(buffer.toString());
//			ps.setString(1, "%"+name+"%");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("获取课程列表数量失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
//	public int listAllCourseSizeFromThisStatus(CourseType ctypeTree, int depid,
//			int role, String name, int ctid, String status) throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<Course> css = new ArrayList<Course>();
//		if (name == null)
//			name = "";
//		else
//			name = name.trim();
//		try {
//			ct = DBConnection.getConnection();
//			Department dep = new Department();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//			ps.setInt(1, depid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				dep.setId(rs.getInt(1));
//				dep.setLid(rs.getInt(2));
//				dep.setRid(rs.getInt(3));
//			}
//			rs.close();
//
//			String x = Integer.toString(ctid);
//			String ids = courseTypeById(ctypeTree, ctid);
//			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
//											// ,当角色不为1时ids的只有一个根节点时也不截取
//				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
//						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
//
//			StringBuffer buffer = new StringBuffer();
//			buffer
//					.append("select count(*) from (select c.id,")
//					.append(
//							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
//					.append(
//							" ct.name ctname,u.realname,c.credit,c.hot  from course c, course_type ct,")
//					.append(
//							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
//					.append(
//							" and u.depid=dep.id and c.status in (" + status
//									+ ") and c.name like ? ")//
//					.append(
//							" and ct.id in (" + ids
//									+ ") order by c.createtime desc )t ");
//
//			ps = ct.prepareStatement(buffer.toString());
//			ps.setString(1, "%" + name + "%");
//			rs = ps.executeQuery();
//			rs.next();
//			return rs.getInt(1);
//
//		} catch (Exception e) {
//			logger.error("从本部门上下级的到可分配课程失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//	}
	/**
	 * modify by jiahaijiang add conditon :status
	 */
	public List<Course> listAllCourseFromThis(int depid, String name, int ctid,
			int pageNow, int pageSize, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			int clid = 0, crid = 0;
			ps = ct
					.prepareStatement("select lid,rid from course_type where id = ?");
			ps.setInt(1, ctid);
			rs = ps.executeQuery();
			if (rs.next()) {
				clid = rs.getInt(1);
				crid = rs.getInt(2);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_APPLY_THIS));
			// ps.setInt(1, CourseConstants.COURSE_STATUS_OPEN);
			ps.setInt(1, status);// modify by jiahaijiang
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, dep.getLid());
			ps.setInt(4, dep.getRid());
			ps.setInt(5, clid);
			ps.setInt(6, crid);
			ps.setInt(7, pageNow);
			ps.setInt(8, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	public int listAllCourseSizeFromThis(int depid, String name, int ctid,
			int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			int clid = 0, crid = 0;
			ps = ct
					.prepareStatement("select lid,rid from course_type where id = ?");
			ps.setInt(1, ctid);
			rs = ps.executeQuery();
			if (rs.next()) {
				clid = rs.getInt(1);
				crid = rs.getInt(2);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_APPLY_SIZE_THIS));
			// ps.setInt(1, CourseConstants.COURSE_STATUS_OPEN);
			ps.setInt(1, status);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, dep.getLid());
			ps.setInt(4, dep.getRid());
			ps.setInt(5, clid);
			ps.setInt(6, crid);

			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// return 0;
	}

	public List<Course> listAllCourseFromSuper(int depid, String name,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_APPLY_SUPER));
			ps.setInt(1, CourseConstants.COURSE_STATUS_HASOPENED);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, depid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				css.add(c);
			}
		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	public int listAllCourseSizeFromSuper(int depid, String name)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_APPLY_SIZE_SUPER));
			ps.setInt(1, CourseConstants.COURSE_STATUS_HASOPENED);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> listCanAssignUser(int cid, int depid)
			throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// Department dep = new Department();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			// ps.setInt(1, depid);
			// rs = ps.executeQuery();
			// if (rs.next()) {
			// dep.setId(rs.getInt(1));
			// dep.setLid(rs.getInt(2));
			// dep.setRid(rs.getInt(3));
			// }
			// rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_CANASSIGN_USERS));
			// ps.setInt(1, dep.getLid());
			ps.setInt(1, depid);

			// ps.setInt(2, dep.getRid());
			ps.setInt(2, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser();
				eu.setId(rs.getInt(1));
				eu.setRealname(rs.getString(2));
				eu.setDepartment(new Department(0, rs.getString(3)));
				eu.setUsername(rs.getString(4));

				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("可分配用户列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	private Department getDepById(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setManager(new ELUser(rs.getInt(5), rs.getString(11)));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setAddress(rs.getString(6));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	private Department getDepTree(int cid, int did, int type, int state)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = null;
		try {
			if (did == 0) {
				dep = getDepById(1);
			} else {
				dep = getDepById(did);
			}
			ct = DBConnection.getConnection();
			if (type == 1)
				dep.setUsers(listCanAssignUser(cid, dep.getId()));
			if (type == 2)
				dep.setUsers(listAssignedUser(dep.getId(), cid, state));
			dep.setChild(listDepartmentsById(cid, dep.getId(), 0, ct, type,
					state));

		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}

	private List<Department> listDepartmentsById(int cid, int parentid,
			int level, Connection ct, int type, int state) throws Exception {
		List<Department> deps = new ArrayList<Department>();
		PreparedStatement pstemp = ct
				.prepareStatement("select id,name,parentid from department where parentid = ?");
		pstemp.setInt(1, parentid);
		ResultSet rstemp = pstemp.executeQuery();
		level++;
		while (rstemp.next()) {
			Department dep = new Department(rstemp.getInt(1), rstemp
					.getString(2));
			dep.setParent(new Department(rstemp.getInt(3)));
			dep.setLevel(level);
			dep.setChild(listDepartmentsById(cid, dep.getId(), level, ct, type,
					state));
			if (type == 1)
				dep.setUsers(listCanAssignUser(cid, dep.getId()));
			if (type == 2)
				dep.setUsers(listAssignedUser(dep.getId(), cid, state));
			deps.add(dep);
		}
		rstemp.close();
		pstemp.close();
		return deps;
	}

	public Department listAssignedDep(int depid, int courseid, int state)
			throws ElException {
		return getDepTree(courseid, depid, 2, state);
	}

	public Department listCanAssignDep(int depid, int courseid)
			throws ElException {
		return getDepTree(courseid, depid, 1, 0);
	}

	public List<ELUser> listAssignedUser(int depid, int cid, int state)
			throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// Department dep = new Department();
			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(DUConstants.DEP_QUERY_LRID_BYID)); ps.setInt(1, depid);
			 * rs = ps.executeQuery(); if (rs.next()) { dep.setId(rs.getInt(1));
			 * dep.setLid(rs.getInt(2)); dep.setRid(rs.getInt(3)); }
			 */
			// rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_ASSIGNED_USERS));
			ps.setInt(1, cid);
			ps.setInt(2, state);
			ps.setInt(3, depid);
			// ps.setInt(3, dep.getLid());
			// ps.setInt(4, dep.getRid());
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser();
				eu.setId(rs.getInt(1));
				eu.setRealname(rs.getString(2));
				eu.setDepartment(new Department(0, rs.getString(3)));
				eu.setUsername(rs.getString(4));
				eus.add(eu);
			}
		} catch (Exception e) {
			logger.error("已分配用户列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	/**
	 * 分配课程
	 */
	public void assignedUser(int cid, int userid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_ASSIGNE2USER));
			ps.setInt(1, userid);
			ps.setInt(2, cid);
			ps.setInt(3, 0);
			ps.setInt(4, status);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("分配用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 分配课程
	 */
	public void assignedUser3(int cid, int userid, int cepingid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_ASSIGNE3USER));
			ps.setInt(1, userid);
			ps.setInt(2, cid);
			ps.setInt(3, cepingid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("分配用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 分配课程
	 * 
	 * @param cid
	 *            课程id
	 * @param userid
	 *            用户id
	 * @param status
	 *            0：必修 1：选修
	 * @param startTime
	 *            分配课程后 课程开始时间
	 * @param finishTime
	 *            分配课程后 课程结束时间
	 * @throws ElException
	 */
	public void assignedUser(int cid, int userid, int status,
			Timestamp startTime, Timestamp finishTime, int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("call into_sc2(?,?,?,?,?,?)");
			ps = ct.prepareStatement("call into_sc4(?,?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, cid);
			ps.setInt(3, 0);
			ps.setInt(4, status);
			ps.setTimestamp(5, startTime);
			ps.setTimestamp(6, finishTime);
			ps.setInt(7, roomid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("分配用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	
	public void assignedUser2(int cid, int userid, int status,
			Timestamp startTime, Timestamp finishTime, int roomid,int classid,int jieyeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("call into_sc2(?,?,?,?,?,?)");
			ps = ct.prepareStatement("call into_sc5(?,?,?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, cid);
			ps.setInt(3, classid);
			ps.setInt(4, status);
			ps.setTimestamp(5, startTime);
			ps.setTimestamp(6, finishTime);
			ps.setInt(7, roomid);
			ps.setInt(8, jieyeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("分配用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	

	/**
	 * 分配课程
	 * 
	 * @param cid
	 *            课程id
	 * @param userid
	 *            用户id
	 * @param status
	 *            0：必修 1：选修
	 * @param startTime
	 *            分配课程后 课程开始时间
	 * @param finishTime
	 *            分配课程后 课程结束时间
	 * @throws ElException
	 */
	public void assignedUser(int cid, int userid, int status,
			Timestamp startTime, Timestamp finishTime) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("call into_sc2(?,?,?,?,?,?)");
			ps = ct.prepareStatement("call into_sc2(?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, cid);
			ps.setInt(3, 0);
			ps.setInt(4, status);
			ps.setTimestamp(5, startTime);
			ps.setTimestamp(6, finishTime);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("分配用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterassignedUser(int cid, int userid, int status, boolean is)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int IS = is == true ? 1 : 0;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_ASSIGNE2USER));
			ps.setInt(1, userid);
			ps.setInt(2, cid);
			ps.setInt(3, IS);
			ps.setInt(4, status);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("分配用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void unassignedUser(int cid, int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.COURSE_ASSIGNE2USER_DELETE));
			ps = ct
					.prepareStatement("delete from study_course where courseid = ? and userid = ? and classid=0");// 只处理直接分配过去的
			ps.setInt(1, cid);
			ps.setInt(2, userid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("取消分配用户失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private int getCpsize(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(id) from course_page where courseid = ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public Course getCourseById(int id) throws ElException {
		Course c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(5));
				c.setCreatetime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
//				c.setPassgrade(rs.getFloat(11));
				c.setMainimg(rs.getString(11));
				c.setIslink(rs.getInt(12));
				c.setExurl(rs.getString(13));
				c.setDuring(rs.getInt(14));
				c.setQuerytime(rs.getInt(15));
				c.setTeacherinfo(rs.getString(16));
				c.setStudyplan(rs.getString(17));
				c.setTeacherName(rs.getString(18));
				c.setKj_appendix(rs.getString(19));
				c.setJy_appendix(rs.getString(20));
				c.setCreditmod(rs.getInt(21));
				c.setCpagesize(getCpsize(c.getId()));
				c.setNotenumber(rs.getInt(22));
				c.setNotedate(rs.getTimestamp(23));
				c.setRoom(new Rooms(rs.getInt(24)));
				c.setRoomstart(rs.getTimestamp(25));
				c.setRoomend(rs.getTimestamp(26));
				c.setTeacherId(rs.getInt(27));
				c.setScormId(rs.getString(28));
				c.setIsApplication(rs.getInt(29));
				c.setCourseForm(rs.getInt(30)); 
				c.setShihegangwei(rs.getString(31));//适合岗位
				c.setZhuanyeleibie(rs.getString(32));//专业类别
				c.setZhuanyejibie(rs.getString(33));//专业级别
				c.setShihebumen(rs.getString(34));//适合部门
				c.setNeirongleixing(rs.getString(35));//内容类型
				c.setPeixunleibie(rs.getString(36));//培训类别
				c.setShihexuewei(rs.getString(37));//适合学位
				c.setKechengxingzhi(rs.getString(38));//课程性质  
				c.setCourseCss(rs.getInt(39));
				c.setLecturerMainimg(rs.getString(40));//讲师图片
				c.setWeidu(rs.getString(41)==null?"":rs.getString(41));//课程维度
				c.setHtml5(rs.getString(42));
				c.setCourseDetail(new OracleBlob().getContent(rs.getBlob(43)));
				c.setCpagesize(getCpsize(c.getId()));
			}
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
	}
	
	public Course getCourseByName(String name) throws ElException {
		Course c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_QUERY_BYNAME));
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(5));
				c.setCreatetime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
//				c.setPassgrade(rs.getFloat(11));
				c.setMainimg(rs.getString(11));
				c.setIslink(rs.getInt(12));
				c.setExurl(rs.getString(13));
				c.setDuring(rs.getInt(14));
				c.setQuerytime(rs.getInt(15));
				c.setTeacherinfo(rs.getString(16));
				c.setStudyplan(rs.getString(17));
				c.setTeacherName(rs.getString(18));
				c.setKj_appendix(rs.getString(19));
				c.setJy_appendix(rs.getString(20));
				c.setCreditmod(rs.getInt(21));
				c.setCpagesize(getCpsize(c.getId()));
				c.setNotenumber(rs.getInt(22));
				c.setNotedate(rs.getTimestamp(23));
				c.setRoom(new Rooms(rs.getInt(24)));
				c.setRoomstart(rs.getTimestamp(25));
				c.setRoomend(rs.getTimestamp(26));
				c.setTeacherId(rs.getInt(27));
				c.setScormId(rs.getString(28));
				c.setIsApplication(rs.getInt(29));
				c.setCourseForm(rs.getInt(30)); 
				c.setShihegangwei(rs.getString(31));//适合岗位
				c.setZhuanyeleibie(rs.getString(32));//专业类别
				c.setZhuanyejibie(rs.getString(33));//专业级别
				c.setShihebumen(rs.getString(34));//适合部门
				c.setNeirongleixing(rs.getString(35));//内容类型
				c.setPeixunleibie(rs.getString(36));//培训类别
				c.setShihexuewei(rs.getString(37));//适合学位
				c.setKechengxingzhi(rs.getString(38));//课程性质  
				c.setCourseCss(rs.getInt(39));
				c.setLecturerMainimg(rs.getString(40));//讲师图片
				c.setWeidu(rs.getString(41)==null?"":rs.getString(41));//课程维度
				c.setCpagesize(getCpsize(c.getId()));
			}
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
	}

	/**
	 * @author jiahaijiang 根据课程ID批量找出需要删除的课程
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public List<Course> getCourseById(String id) throws ElException {
		List<Course> list = new ArrayList<Course>();
		Course c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select c.id,c.name,c.ctypeid,c.description,c.status,"
					+ "c.createtime,c.creater,ct.name,u.realname,c.credit,c.mainimg,"
					+ "c.islink,c.exurl,c.during,c.querytime,c.teacherinfo,c.studyplan,c.teachername,"
					+ "c.kj_appendix,c.jy_appendix,c.creditmod,c.notenumber,c.notedate from course c,"
					+ "course_type ct,eluser u where c.ctypeid=ct.id and c.creater = u.id and c.id in ("
					+ id + ")";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(5));
				c.setCreatetime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				// c.setPassgrade(rs.getFloat(11));
				c.setMainimg(rs.getString(11));
				c.setIslink(rs.getInt(12));
				c.setExurl(rs.getString(13));
				c.setDuring(rs.getInt(14));
				c.setQuerytime(rs.getInt(15));
				c.setTeacherinfo(rs.getString(16));
				c.setStudyplan(rs.getString(17));
				c.setTeacherName(rs.getString(18));
				c.setKj_appendix(rs.getString(19));
				c.setJy_appendix(rs.getString(20));
				c.setCreditmod(rs.getInt(21));
				c.setCpagesize(getCpsize(c.getId()));
				c.setNotenumber(rs.getInt(22));
				c.setNotedate(rs.getTimestamp(23));
				list.add(c);
			}
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void openCourse(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update course set status = ? where id = ?");
			ps.setInt(1,
					CourseConstants.COURSE_STATUS_PRELIMINARYEXAMINATION_WAIT);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void openCourse(int id, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update course set status = ? where id = ?");
			ps.setInt(1, status);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void openCourseAudit(CourseAuditDescribes courseAudit)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into course_audit_describes(courseid,userid,title,content) values(?,?,?,?)");
			ps.setInt(1, courseAudit.getCourse().getId());
			ps.setInt(2, courseAudit.getCreater().getId());
			ps.setString(3, courseAudit.getTitle());
			ps.setString(4, courseAudit.getContent());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void UCourseAuditContents(CourseAuditDescribes courseAudit)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update course_audit_describes set REPLYCONTENT = ? , CONTENT = ? where id  = ?");
			ps.setString(1, courseAudit.getReplycontent());
			ps.setString(2, courseAudit.getContent());
			ps.setInt(3, courseAudit.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新内容出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public CourseAuditDescribes getCourseAudit(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CourseAuditDescribes cad = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select cad.id,cad.courseid,cad.userid,cad.submittime,cad.feedbacktime,cad.title,cad.status,cad.content,cad.REPLYCONTENT  from course_audit_describes cad where cad.courseid = ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				cad = new CourseAuditDescribes();
				Course c = new Course();
				c.setId(rs.getInt(2));
				ELUser u = new ELUser();
				u.setId(rs.getInt(3));
				cad.setId(rs.getInt(1));
				cad.setCourse(c);
				cad.setCreater(u);
				cad.setSubmittime(rs.getTimestamp(4));
				cad.setFeedbacktime(rs.getTimestamp(5));
				cad.setTitle(rs.getString(6));
				cad.setStatus(rs.getInt(7));
				cad.setContent(rs.getString(8));
				cad.setReplycontent(rs.getString(9));
			}
		} catch (Exception e) {
			logger.error("获取申请说明失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cad;
	}

	public void courseDelete(int id, int deleter) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STATUS_SET_BYUSER));
			ps.setInt(1, CourseConstants.COURSE_STATUS_DELETE_WAIT);
			ps.setInt(2, id);
			ps.setInt(3, deleter);
			ps.executeUpdate();
			// TODO 删除练习，试卷等？
		} catch (Exception e) {
			logger.error("申请删除课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * @author jiahaijiang 批量删除
	 * @param ids
	 * @param deleter
	 * @throws ElException
	 */
	public void courseDelete(String ids, int deleter) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sql = "update course set status = ? where id in (" + ids
					+ ") and creater = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, CourseConstants.COURSE_STATUS_DELETE);
			ps.setInt(2, deleter);
			ps.executeUpdate();
			// TODO 删除练习，试卷等？
		} catch (Exception e) {
			logger.error("申请删除课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void courseDeleteOp(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from course where id = ?");
			// ps.setInt(1, CourseConstants.COURSE_STATUS_DELETE);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请删除课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Course> listDeleteCourse(int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_DELETE_LIST));
			ps.setInt(1, CourseConstants.COURSE_STATUS_DELETE_WAIT);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(7)));
				// c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setCreater(new ELUser(rs.getInt(6), rs.getString(8)));
				c.setModifytime(rs.getTimestamp(9));
				c.setCredit(rs.getInt(10));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	/*
	 * public int listDeleteCourseSize() throws ElException { PreparedStatement
	 * ps = null; ResultSet rs = null; Connection ct = null; try { ct =
	 * DBConnection.getConnection(); ps = ct.prepareStatement("select count(*)
	 * from COURSE_DELETE"); rs = ps.executeQuery(); if (rs.next()) { return
	 * rs.getInt(1); } } catch (Exception e) { logger.error("课程列表失败！", e); throw
	 * new ElException(e); } finally { DBConnection.closeConnectInfo(ct, ps,
	 * rs); } return 0; }
	 */

	public void alterCourse(Course course) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(CourseConstants.COURSE_ALTER));
			 */
			ps = ct
					.prepareStatement("update course set name = ?,ctypeid = ?,description  = ?, credit = ?,mainimg = ?,exurl=?,during = ?,querytime=?,teacherinfo=?,studyplan=?,teachername=?,kj_appendix=?,jy_appendix=? ,creditmod = ?,notenumber=?,notedate=?,roomid=?,roomstart=?,roomend=?,teacherid = ?,courseForm = ?,islink=?,weidu=?,html5=? ,course_detail=empty_blob() where id = ?");
			ps.setString(1, course.getName());
			ps.setInt(2, course.getCtype().getId());
			ps.setString(3, course.getDescription());
			// ps.setFloat(4, course.getPassgrade());
			// ps.setInt(5, course.getStatus());
			ps.setInt(4, course.getCredit());
			ps.setString(5, course.getMainimg());
			// ps.setInt(8, course.getIslink());
			ps.setString(6, course.getExurl());
			ps.setInt(7, course.getDuring());
			ps.setInt(8, course.getQuerytime());
			ps.setString(9, course.getTeacherinfo());
			ps.setString(10, course.getStudyplan());
			ps.setString(11, course.getTeacherName());
			ps.setString(12, course.getKj_appendix());
			ps.setString(13, course.getJy_appendix());
			ps.setInt(14, course.getCreditmod());
			ps.setInt(15, course.getNotenumber());
			ps.setTimestamp(16, course.getNotedate());
			ps.setInt(17, course.getRoom().getId());
			ps.setTimestamp(18, course.getRoomstart());
			ps.setTimestamp(19, course.getRoomend());
			ps.setInt(20, course.getTeacherId());
			// ps.setInt(21, course.getCreater().getId());
			ps.setInt(21, course.getCourseForm());
			ps.setInt(22, course.getIslink());
			ps.setString(23, course.getWeidu());
			ps.setString(24, course.getHtml5());
//			ps.setString(25, new OracleBlob().getContent(course.getCourseDetail()));
			ps.setInt(25, course.getId());
			ps.executeUpdate();
			// 设置总时长
			OracleBlob setblob = new OracleBlob("course","id",course.getId()+"","course_detail",course.getCourseDetail(),"课程修改出错",ct);
			setblob.updateContent(); 
		} catch (Exception e) {
			logger.error("修改课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void alterCourse_S(Course course) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update course set exurl=?,during = ? where id = ?");
			ps.setString(1, course.getExurl());
			ps.setInt(2, course.getDuring());
			ps.setInt(3, course.getId());
			ps.executeUpdate();
			// 设置总时长
		} catch (Exception e) {
			logger.error("修改课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void alterCourseStatus(int id, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(CourseConstants.COURSE_ALTER));
			 */
			ps = ct
					.prepareStatement("update course set status = ? where id = ?");
			ps.setInt(1, status);
			ps.setInt(2, id);
			ps.executeUpdate();
			// 设置总时长

		} catch (Exception e) {
			logger.error("修改课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkDep2course(int depid, int course) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_DEP_CHECK));
			ps.setInt(1, depid);
			ps.setInt(2, course);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测课程是否已分配到部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public List<Department> listCanAssignDeps(int depid, int cid)
			throws ElException {
		List<Department> deps = new ArrayList<Department>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_CANASSIGN_DEPS));
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			ps.setInt(3, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new Department(rs.getInt(1), rs.getString(2)));
			}

		} catch (Exception e) {
			logger.error("检测课程是可分配到部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<Department> listAssignedDeps(int depid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_ASSIGNED_DEPS));
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			ps.setInt(3, courseid);

			rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(new Department(rs.getInt(1), rs.getString(2)));
			}
		} catch (Exception e) {
			logger.error("检测课程是否已分配到部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public void assignDeps(int courseid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_DEP_ADD));
			ps.setInt(1, courseid);
			ps.setInt(2, depid);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error(" 分配到部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void unassignDeps(int courseid, int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_DEP_DELETE));
			ps.setInt(1, courseid);
			ps.setInt(2, depid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除分配到部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void unassignDepsAll(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("delete from course_dep where courseid =?");
			ps.setInt(1, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除分配到部门失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ExamRoom> listERbyCidandTitle(int cid, String title)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.EROOM_QUERY_BYCIDANDT));
			ps.setString(1, "%" + title + "%");
			ps.setInt(2, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setDescription(rs.getString(3));
				er.setLocation(rs.getString(4));
				er.setBegintime(rs.getTimestamp(5));
				er.setEndtime(rs.getTimestamp(6));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public List<MyExamPaper> listReadSimPapers(int courseid, int pageNow,
			int pageSize) throws ElException {
		List<MyExamPaper> meps = new ArrayList<MyExamPaper>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.SPAPER_READ_LIST));
			ps.setInt(1, courseid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper mep = new MyExamPaper(rs.getInt(1));
				mep.setTester(new ELUser(rs.getInt(2), rs.getString(7)));
				mep.setExamPaper(new ExamPaper(rs.getInt(3), rs.getString(8)));
				mep.setStatus(rs.getInt(4));
				mep.setMyScore(rs.getInt(5));
				mep.setEndtime(rs.getTimestamp(6));
				meps.add(mep);
			}
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return meps;
	}

	public int listReadSimPapersSize(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from  STUDENT_SIMINFO sqi left join "
							+ "ELUSER eu on sqi.userid = eu.id where sqi.courseid = ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取课程考试场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void reSimquiz(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.SPAPER_REQUIZ));
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("监考管理失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ExamRoom> listMyExamroom(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select er.id , er.title,  er.begintime, er.endtime,er.location,er.courseid,c.name from exam_room er "
							+ "left join course c on c.id=er.courseid left join exam_rinvigilators erv on erv.roomid = er.id where erv.userid=? ");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	public List<ExamRoom> listMyExamroomPages(int userid, int role,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			if (role == 1) {// 超级管理员通道 //hwc
				ps = ct
						.prepareStatement("select * from ( select t1.* ,rownum rn from(select distinct er.id , er.title,  er.begintime, er.endtime,er.location,er.courseid,c.name,el.realname from eluser el join exam_room er on el.id=er.createrid"
								+ " left join course c on c.id=er.courseid left join exam_rinvigilators erv on erv.roomid = er.id where er.valid = 5  ) t1 where rownum <=? ) where rn >=?");//
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			} else {
				ps = ct
						.prepareStatement("select * from ( select t1.* ,rownum rn from(select distinct er.id , er.title,  er.begintime, er.endtime,er.location,er.courseid,c.name,el.realname "
								+ " from eluser el left join exam_room er on el.id=er.createrid left join course c on c.id=er.courseid left join exam_rinvigilators erv on erv.roomid = er.id where er.valid = 5  and el.id = ? ) t1 where rownum <=? ) where rn >=?");//
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				user = new ELUser();
				user.setRealname(rs.getString("realname"));
				er.setCreater(user);
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 监考大厅list
	 */
	public List<ExamRoom> listMyExamroomPages(int userid, int role,
			ExamRoom examRoom, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ExamRoom> ers = new ArrayList<ExamRoom>();
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			String sql = "";
			String sql2 = "";
			if (examRoom != null) {
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {
					sql2 += " and er.title like '%" + examRoom.getTitle()
							+ "%'";
				}
				if (examRoom.getValid() != -1) {
					sql2 += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sql2 += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql2 += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sql2 += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sql2 += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sql2 += " and er.classid>0";
				}
			} else {
				sql2 += " and er.classid=-1";
			}
			if (role == 1) {// 超级管理员通道 //hwc
				sql = "select * from ( select t1.* ,rownum rn from(select distinct er.id , er.title,  er.begintime, er.endtime,er.location,er.courseid,c.name,el.realname,erlib.id erid,erlib.name ername,er.valid,er.isApplication,dep.id depid,dep.name depname,er.depname erdep,er.jingzhong erjingzhong from eluser el join exam_room er on el.id=er.createrid inner join department dep on dep.id=el.depid left join eroom_lib erlib on er.erlibid=erlib.id left join course c on c.id=er.courseid where er.valid = 5 ";
				sql += sql2;
				sql += " order by er.begintime desc) t1 where rownum <=? ) where rn >=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			} else {
				sql = "select * from ( select t1.* ,rownum rn from(select distinct er.id , er.title,  er.begintime, er.endtime,er.location,er.courseid,c.name," +
						"el.realname,erlib.id erid,erlib.name ername,er.valid,er.isApplication,dep.id depid,dep.name depname,er.depname erdep,er.jingzhong erjingzhong from eluser el join exam_room er on el.id=er.createrid inner join department dep on dep.id=el.depid left join eroom_lib erlib on er.erlibid=erlib.id left join course c on c.id=er.courseid left join exam_rinvigilators erv on erv.roomid = er.id where er.valid = 5 and erv.userid = ? ";
				sql += sql2;
				sql += " order by er.begintime desc) t1 where rownum <=? ) where rn >=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			ELUser user = null;
			while (rs.next()) {
				ExamRoom er = new ExamRoom(rs.getInt(1), rs.getString(2));
				er.setBegintime(rs.getTimestamp(3));
				er.setEndtime(rs.getTimestamp(4));
				er.setLocation(rs.getString(5));
				er.setCourse(new Course(rs.getInt(6), rs.getString(7)));
				user = new ELUser();
				user.setRealname(rs.getString(8));
				user.setDepartment(new Department(rs.getInt(13),rs.getString(14)));
				er.setCreater(user);
				er.setEroomLib(new EroomLib(rs.getInt(9), rs
						.getString(10)));
				er.setUsersize(this.getExamAllStudy(er.getId()));//考场人数
				er.setValid(rs.getInt(11));
				er.setIsApplication(rs.getInt(12));
				//er.setPlanNumber(getEroomPlanNumber(er.getId()));//计划招收人数
				er.setUsize(this.getExamStudy(er.getId(), 0));//缺考人数
				er.setDepName(rs.getString(15));
				er.setJingzhong(rs.getString(16));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("获取考试场次列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}

	/**
	 * 获取考场计划招收人数
	 * 
	 * @param erid
	 * @return
	 * @throws ElException
	 */
	public int getEroomPlanNumber(int erid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select planrecruitstudents from eroom_registration where eroomid = ?");
			ps.setInt(1, erid);
			rs = ps.executeQuery();
			if (rs.next())
				number = rs.getInt(1);
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 获取考场所有学员数
	 * 
	 * @param roomid
	 * @return
	 * @throws ElException
	 */
	public int getExamAllStudy(int roomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(*) from study_room where roomid=?");
			ps.setInt(1, roomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考场所有学员数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 获取考场学员数
	 * 
	 * @param roomid
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int getExamStudy(int roomid,int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from study_room where roomid=? and status=?");
			ps.setInt(1, roomid);
			ps.setInt(2, status);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取考场学员数失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listMyExamroomPage(int userid, int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (role == 1) {// 超级管理员通道 //hwc
				ps = ct
						.prepareStatement("select count(distinct er.id)"
								+ "from eluser el left join exam_room er on el.id=er.createrid left join course c on c.id=er.courseid left join exam_rinvigilators erv on erv.roomid = er.id where er.valid = 5");//
			} else {
				ps = ct
						.prepareStatement("select count(distinct er.id) "
								+ "from eluser el left join exam_room er on el.id=er.createrid left join course c on c.id=er.courseid left join exam_rinvigilators erv on erv.roomid = er.id where er.valid = 5 and el.id = ?");//
				ps.setInt(1, userid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	/**
	 * 监考大厅list数量
	 * 
	 * @param userid
	 * @param role
	 * @param examRoom
	 * @return
	 * @throws ElException
	 */
	public int listMyExamroomPage(int userid, int role, ExamRoom examRoom)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "";
			String sql2 = "";
			if (examRoom != null) {
				if (examRoom.getTitle() != null
						&& !examRoom.getTitle().equals("")) {
					sql2 += " and er.title like '%" + examRoom.getTitle()
							+ "%'";
				}
				if (examRoom.getValid() != -1) {
					sql2 += " and er.valid=" + examRoom.getValid();
				}
				if (examRoom.getBegintime() != null) {
					sql2 += " and er.begintime >= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getBegintime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getEndtime() != null) {
					sql2 += " and er.endtime <= to_date('"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(examRoom.getEndtime())
							+ "','yyyy-MM-dd HH24:mi:ss')";
				}
				if (examRoom.getClassid() == -1) {
					sql2 += " and er.classid=-1";
				} else if (examRoom.getClassid() == 0) {
					sql2 += " and er.classid=0";
				} else if (examRoom.getClassid() == 1) {
					sql2 += " and er.classid>0";
				}
			} else {
				sql2 += " and er.classid=-1";
			}
			if (role == 1) {// 超级管理员通道 //hwc
				sql = "select count(distinct er.id)from eluser el left join exam_room er on el.id=er.createrid left join course c on c.id=er.courseid left join exam_rinvigilators erv on erv.roomid = er.id where er.valid = 5 ";
				sql += sql2;
				ps = ct.prepareStatement(sql);
			} else {
				sql = "select count(distinct er.id)from eluser el left join exam_room er on el.id=er.createrid left join course c on c.id=er.courseid left join exam_rinvigilators erv on erv.roomid = er.id where er.valid = 5 and erv.userid = ? ";
				sql += sql2;
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<MyExamPaper> listMyEpsByRid(int roomid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyExamPaper> ers = new ArrayList<MyExamPaper>();
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
			// .prepareStatement("select sqi.id sqid,eu.id
			// euid,eu.username,eu.realname,
			// sqi.endtime,sqi.status,sqi.epid,ep.title eptitle,
			// count(sqqw.qid), count(sqqy.qid) from "
					// + "eluser eu left join study_quizinfo sqi on sqi.userid =
					// eu.id left join exampaper ep on ep.id = sqi.epid " +
					// "left join (select * from study_questions where status=
					// -1) sqqw on sqqw.sqid = sqi.id " +
					// "left join (select * from study_questions where status!=
					// -1) sqqy on sqqy.sqid = sqi.id where sqi.roomid = ? and
					// sqi.userid= ? group by
					// sqi.id,eu.id,eu.username,eu.realname,
					// sqi.endtime,sqi.status,sqi.epid,ep.title order by
					// sqi.epid ");
				.prepareStatement(" select sqi.id sqid,eu.id euid,eu.username,eu.realname, sqi.endtime,sqi.status,sqi.epid,ep.title eptitle,sqi.begintime,sqi.jiashi from " +
						"eluser eu left join study_quizinfo sqi on sqi.userid = eu.id left join exampaper ep on ep.id = sqi.epid left join study_questions sq on sq.sqid=sqi.id " +
						"where sqi.id in (select max(id) from study_quizinfo where roomid= ? and userid=? group by epid) group by sqi.id ,eu.id,eu.username,eu.realname, sqi.endtime,sqi.status,sqi.epid,ep.title,sqi.begintime,sqi.jiashi");
//					.prepareStatement("select sqi.id sqid,eu.id euid,eu.username,eu.realname, sqi.endtime,sqi.status,sqi.epid,ep.title eptitle,sqi.begintime,sqi.jiashi from "
//							+ "eluser eu left join study_quizinfo sqi on sqi.userid = eu.id left join exampaper ep on ep.id = sqi.epid  "
//							+ " where sqi.roomid = ? and sqi.userid= ?  group by  sqi.id,eu.id,eu.username,eu.realname, sqi.endtime,sqi.status,sqi.epid,ep.title,sqi.begintime,sqi.jiashi order by sqi.epid  ");
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				MyExamPaper er = new MyExamPaper(rs.getInt(1));
				er.setTester(new ELUser(rs.getInt(2), rs.getString(4)));
				er.getTester().setUsername(rs.getString(3));
				// er.getTester().setUserno(rs.getString(5));
				er.setBegintime(rs.getTimestamp(9));
				er.setEndtime(rs.getTimestamp(5));
				er.setStatus(rs.getInt(6));
				er.setExamRoom(new ExamRoom(roomid));
				er.setExamPaper(new ExamPaper(rs.getInt(7), rs.getString(8)));
				er.setWd(doOrNot(er.getId(), 0));
				er.setYd(doOrNot(er.getId(), 1));
				er.setJiashi(rs.getInt(10));
				er.setMyScore(getScore(er.getId()));
				er.setAvgscore(getSpeed(er.getId()));
				ers.add(er);
			}
		} catch (Exception e) {
			logger.error("监考管理失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ers;
	}
	private float getScore(int sqid)throws ElException{
		float f = -1f;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
				ps = ct
						.prepareStatement("select sum(sq.myscore) from study_blocks sq where sqid = ?");
			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				f = rs.getFloat(1);
			}
		} catch (Exception e) {
			logger.error("监考管理失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}
	private float getSpeed(int sqid)throws ElException{
		float f = -1f;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
				ps = ct
						.prepareStatement("select sq.myanswer from study_questions sq join (select * from question where qtype=8) q on q.id = sq.qid where sqid = ?");
			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				try {
					Question qj = new Question();
					qj.setStuAnswer(rs.getString(1)) ;
					int t = ExamPaperUtil.getInt(qj.getStuAnswers()[0]);
					int r = ExamPaperUtil.getInt(qj.getStuAnswers()[1]);
					f = getFloat.GetFloat((r * 1.0f) / (1.0f * t / 60));
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("获取打字速度失败！");
					//throw new ElException(e);
					f = 0;
				}
			}
		} catch (Exception e) {
			logger.error("监考管理失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}
	private int doOrNot(int sqid, int x) throws ElException {
		int size = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			if (x == 0)
				ps = ct
						.prepareStatement("select count( qid) from study_questions where status= -1 and sqid = ? ");
			else
				ps = ct
						.prepareStatement("select count( qid) from study_questions where status != -1 and sqid = ? ");
			ps.setInt(1, sqid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("监考管理失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	// public int listMyEpsByRidSize(int roomid) throws ElException {
	//
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// int size = 0 ;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement(" select count(sqi.id) from study_quizinfo sqi where
	// sqi.roomid = ?");
	// ps.setInt(1, roomid);
	// rs = ps.executeQuery();
	// if(rs.next()) {
	// size = rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("监考管理失败！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return size;
	// }
	public void setTesterStatus(int status, int roomid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update study_quizinfo set status = ? ,MYSCORE = 0,ISPASSED=0,PASSTIME=0 where id = ?");
			ps.setInt(1, status);
			ps.setInt(2, userid);
			ps.executeUpdate();
			ps = ct.prepareStatement("delete from study_blocks where sqid= ?");
			ps.setInt(1, userid);
			ps.executeUpdate();
			ps = ct
					.prepareStatement("delete from study_questions where sqid= ?");
			ps.setInt(1, userid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("监考管理失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 清除用户答卷
	 * 
	 * @param sqiId
	 * @throws ElException
	 */
	public void rsetStudyExamPaper(int sqiId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 阅卷
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("update study_quizinfo set status = ?
			// ,MYSCORE = 0,ISPASSED=0,PASSTIME=0 where id = ?");
			// ps.setInt(1, status);
			// ps.setInt(2, userid);
			// ps.executeUpdate();
			ps = ct.prepareStatement("delete from study_blocks where sqid= ?");
			ps.setInt(1, sqiId);
			ps.executeUpdate();
			ps = ct
					.prepareStatement("delete from study_questions where sqid= ?");
			ps.setInt(1, sqiId);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("监考管理失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyCourse> listselectedCourse(int status, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> courses = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_SELECTED_LIST));
			ps.setInt(1, status);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(6), rs.getString(7)));
				MyCourse mc = new MyCourse();
				ELUser eluser = new ELUser(rs.getInt(3), rs.getString(4));
				eluser.setUsername(rs.getString(10));
				mc.setUser(eluser);
				mc.setApplyDate(rs.getTimestamp(5));
				Department department = new Department();
				department.setName(rs.getString(11));
				mc.setDepartment(department);
				c.setCreater(new ELUser(rs.getInt(8), rs.getString(9)));
				mc.setCourse(c);
				c.setIslink(rs.getInt(10));
				c.setRoomstart(rs.getTimestamp(11));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));

				courses.add(mc);
			}
		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public int listselectedCourseSize(int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_SELECTED_SIZE));
			ps.setInt(1, status);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void setSelectedCoruse(int status, MyCourse myCourse)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_SELECTED_SET));
			ps.setInt(1, status);
			ps.setInt(2, myCourse.getUser().getId());
			ps.setInt(3, myCourse.getCourse().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("课程审核！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void courseHotSet(int id, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_HOT_SET));
			ps.setInt(1, hot);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("课程推荐设置失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyCourse> listStudycoursedelete(int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> courses = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STUDY_DELETE));
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(2));
				Course c = new Course(rs.getInt(3), rs.getString(4));
				// c.setCtype(new CourseType(rs.getInt(6), rs.getString(7)));
				MyCourse mc = new MyCourse();
				// mc.setUser(new ELUser(rs.getInt(3), rs.getString(4)));
				// mc.setApplyDate(rs.getTimestamp(5));
				// c.setCreater(new ELUser(rs.getInt(8), rs.getString(9)));
				mc.setDeletedate(rs.getTimestamp(5));
				mc.setCourse(c);
				mc.setUser(eu);
				courses.add(mc);
			}
		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public int listStudycoursedeleteSize() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STUDY_DELETE_SIZE));
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void Studycoursedelete_Unop(MyCourse mc) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STUDY_DELETE_OP));
			ps.setInt(1, mc.getUser().getId());
			ps.setInt(2, mc.getCourse().getId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void Studycoursedelete_Op(MyCourse mc) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STUDY_DELETE_OP));
			ps.setInt(1, mc.getUser().getId());
			ps.setInt(2, mc.getCourse().getId());
			ps.executeUpdate();
			// 删除学习相关
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STUDY_DELETE_OP_YES_CA));
			ps.setInt(1, mc.getUser().getId());
			ps.setInt(2, mc.getCourse().getId());
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STUDY_DELETE_OP_YES_SC));
			ps.setInt(1, mc.getUser().getId());
			ps.setInt(2, mc.getCourse().getId());
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STUDY_DELETE_OP_YES_SCP));
			ps.setInt(1, mc.getUser().getId());
			ps.setInt(2, mc.getCourse().getId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("申请删除课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Course> listShCourse(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			ps = ct
					.prepareStatement("select * from (select t.*,rownum rn from(select c.id ,c.name,c.ctypeid,ct.name ctname,c.createtime,c.creater,eu.realname,c.credit "
							+ "from course c,course_type ct,eluser eu,department dep where c.ctypeid = ct.id and "
							+ "c.creater = eu.id and eu.depid = dep.id and  dep.lid>=? and dep.rid <=? and c.status = 2 order by c.createtime desc)t where  rownum <?)where rn>=? ");
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			// add by jiahaijiang
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(4)));
				// c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				// c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				c.setCredit(rs.getInt(8));
				// c.setHot(rs.getInt(11));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("待审核课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	/**
	 * @author jiahaijiang 合计
	 * @param depid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int listShCourseSize(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			ps = ct
					.prepareStatement("select count(*) "
							+ "from course c,course_type ct,eluser eu,department dep where c.ctypeid = ct.id and "
							+ "c.creater = eu.id and eu.depid = dep.id and  dep.lid>=? and dep.rid <=? and c.status = 2 order by c.createtime desc ");
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (Exception e) {
			logger.error("待审核课程列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return count;
	}

	public void shCourse(int courseid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.COURSE_STATUS_SET));
			ps.setInt(1, status);
			ps.setInt(2, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请不通过课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setisNormal(int courseid, int isNormal) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" update course set isNormal = ? where id = ?");
			ps.setInt(1, isNormal);
			ps.setInt(2, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请開通课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void setaStatus(int courseid, int astatus) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(" update course set astatus = ? where id = ?");
			ps.setInt(1, astatus);
			ps.setInt(2, courseid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请開通课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void addCourseServer(CourseServer courseServer) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into course_server(name,description,url) values(?,?,?)");
			ps.setString(1, courseServer.getName());
			ps.setString(2, courseServer.getDescription());
			ps.setString(3, courseServer.getUrl());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请删除课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterCourseServer(CourseServer courseServer) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("update  course_server set name=?,description=?,url=? where id = ?");
			ps.setString(1, courseServer.getName());
			ps.setString(2, courseServer.getDescription());
			ps.setString(3, courseServer.getUrl());

			ps.setInt(4, courseServer.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请删除课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteCourseServer(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from course_server where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("申请删除课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 根据课程类别删除课程
	 * 
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteCourseByTypeid(Connection ct, int typeid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// Connection ct = null;
		try {
			// ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from course where ctypeid = ?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据课程类别删除课程失败！", e);
			throw new ElException(e);
		}
	}
	/**
	 * 根据课程类别更新课程状态
	 * 
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteCourseByTypeidNot(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update course set status=9 where ctypeid = ?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据课程类别更新课程状态失败！", e);
			throw new ElException(e);
		}
	}

	public CourseServer getCourseServer(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CourseServer c = new CourseServer();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description,url from course_server where id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				c.setId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setDescription(rs.getString(3));
				c.setUrl(rs.getString(4));
			}
		} catch (Exception e) {
			logger.error("申请删除课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
	}

	public List<CourseServer> listCourseServer() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseServer> cs = new ArrayList<CourseServer>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name,description,url from course_server");
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseServer c = new CourseServer();
				c.setId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setDescription(rs.getString(3));
				c.setUrl(rs.getString(4));
				cs.add(c);
			}
		} catch (Exception e) {
			logger.error("申请删除课程操作失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}

	public List<PracticePaper> getPracticePaperByCid(int course, int cpid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<PracticePaper> pps = new ArrayList<PracticePaper>();
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement(ElQuerySql
			// .getSQL(CourseConstants.PPAPER_QUERY_BYCIDANDPID));
			ps = ct
					.prepareStatement("select pp.id, ep.id,ep.title,ep.showmod,ep. modifytime,ep.createtime,pp.sortid,pp.skipable,ep_tscore,pp.title pptitle,pp.passgrade,pp.epid,pp.courseid,pp.cpid from practicepaper pp left join exampaper ep on pp.epid = ep.id where pp.courseid=? and pp.cpid=? order by pp.sortid");
			ps.setInt(1, course);
			ps.setInt(2, cpid);
			rs = ps.executeQuery();
			// pp.id, ep.id,ep.title,ep.israndom,ep.
			// modifytime,ep.createtime,pp.sortid
			while (rs.next()) {
				PracticePaper pp = new PracticePaper(rs.getInt(1));
				ExamPaper ep = new ExamPaper(rs.getInt(2), rs.getString(3));
				ep.setShowmod(rs.getInt(4));
				ep.setModifytime(rs.getTimestamp(5));
				ep.setCreatetime(rs.getTimestamp(6));
				ep.setEp_tscore(rs.getFloat(9));
				pp.setExamPaper(ep);
				pp.setSortid(rs.getInt(7));
				pp.setSkipable(rs.getInt(8));
				pp.setTitle(rs.getString("pptitle"));
				pp.setPassgrade(rs.getFloat("passgrade"));
				pp.setCourse(new Course(rs.getInt(13)));
				pp.setCpage(new CoursePage(rs.getInt(14)));
				pps.add(pp);
			}
		} catch (Exception e) {
			logger.error("获取练习列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pps;
	}

	public PracticePaper getPracticePaperById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		PracticePaper pp = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,epid,courseid,cpid,sortid,skipable,title,passgrade from practicepaper where id= ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			// id,epid,courseid,cpid,sortid,skipable
			if (rs.next()) {
				pp = new PracticePaper(rs.getInt(1));
				pp.setExamPaper(new ExamPaper(rs.getInt(2)));
				pp.setCourse(new Course(rs.getInt(3)));
				pp.setCpage(new CoursePage(rs.getInt(4)));
				pp.setSortid(rs.getInt(5));
				pp.setSkipable(rs.getInt(6));
				pp.setTitle(rs.getString("title"));
				pp.setPassgrade(rs.getFloat("passgrade"));
			}
		} catch (Exception e) {
			logger.error("获取练习列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pp;
	}

	public void addPracticePaper(PracticePaper pracPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int sortid = 1;
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.PPAPER_MSORTID_INCP));
			ps.setInt(1, pracPaper.getCourse().getId());
			ps.setInt(2, pracPaper.getCpage().getId());
			rs = ps.executeQuery();
			if (rs.next())
				sortid = rs.getInt(1);
			rs.close();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.PPAPER_ADD));
			ps.setInt(1, pracPaper.getExamPaper().getId());
			ps.setInt(2, pracPaper.getCourse().getId());
			ps.setInt(3, pracPaper.getCpage().getId());
			ps.setInt(4, pracPaper.getSkipable());
			ps.setInt(5, sortid + 1);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("添加练习失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 添加章节的练习
	 * 
	 * @param pracPaper
	 * @throws ElException
	 */
	public void addPracticePaper2(PracticePaper pracPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("insert into practicepaper( epid,courseid,cpid,skipable,sortid,title,passgrade) values(?,?,?,0,1,?,?)");
			ps.setInt(1, pracPaper.getExamPaper().getId());
			ps.setInt(2, pracPaper.getCourse().getId());
			ps.setInt(3, pracPaper.getCpage().getId());
			// ps.setInt(4, 0);
			// ps.setInt(5, 1);
			ps.setString(4, pracPaper.getTitle());
			ps.setFloat(5, pracPaper.getPassgrade());
			rs = ps.executeQuery();
		} catch (Exception e) {
			logger.error("添加练习失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void practicepaper_sort(PracticePaper pp, int upordown)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			int courseid = pp.getCourse().getId();
			int cpid = pp.getCpage().getId();
			int sortid = pp.getSortid();
			if (upordown == ElConstants.SORT_UP) {
				ppupSort(ct, courseid, cpid, sortid);

			} else {
				ppdownSort(ct, courseid, cpid, sortid);
			}
		} catch (Exception e) {
			logger.error("移动网页失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void ppupSort(Connection ct, int courseid, int cpid, int sortid)
			throws ElException {
		try {
			Statement st = ct.createStatement();
			if (sortid > 0) {
				String sql = "select id from practicepaper where courseid = "
						+ courseid + " and cpid=" + cpid + " and sortid = "
						+ (sortid - 1);
				ResultSet rs = st.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update practicepaper set sortid=sortid-1 "
							+ " where courseid = " + courseid + " and cpid="
							+ cpid + " and sortid=" + sortid;
					st.executeUpdate(sql);
					sql = "update practicepaper set sortid=sortid+1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);
				}
			}
			st.close();

		} catch (Exception e) {
			logger.error("网页上移失败！", e);
			throw new ElException("网页上移失败", e);
		}
	}

	private void ppdownSort(Connection ct, int courseid, int cpid, int sortid)
			throws ElException {
		try {
			Statement st = ct.createStatement();
			String sql = "select max(sortid) from practicepaper where courseid= "
					+ courseid + " and cpid=" + cpid;
			ResultSet rs = st.executeQuery(sql);
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select id from practicepaper where courseid = "
						+ courseid + " and cpid=" + cpid + " and sortid = "
						+ (sortid + 1);
				rs = st.executeQuery(sql);
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update practicepaper set sortid=sortid+1 "
							+ " where courseid = " + courseid + " and cpid="
							+ cpid + " and sortid=" + sortid;
					st.executeUpdate(sql);
					sql = "update practicepaper set sortid=sortid-1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			logger.error("网页下移失败！", e);
			throw new ElException("网页下移失败", e);
		}
	}

	public boolean checkPpInCourse(PracticePaper practicePaper)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.PPAPER_CHECK_INCP));
			ps.setInt(1, practicePaper.getExamPaper().getId());
			ps.setInt(2, practicePaper.getCourse().getId());
			ps.setInt(3, practicePaper.getCpage().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测课程是否包含该试卷练习失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deletePracticePaper(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			PracticePaper pp = getPracticePaperById(id);
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.PPAPER_BIGSORT_SET));
			ps.setInt(1, pp.getSortid());
			ps.setInt(2, pp.getCourse().getId());
			ps.setInt(3, pp.getCpage().getId());
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.PPAPER_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除课程练习失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<SimexamPaper> getSimexampaperByCid(int course)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<SimexamPaper> pps = new ArrayList<SimexamPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.SPAPER_QUERY_BYCID));
			ps.setInt(1, course);
			rs = ps.executeQuery();

			while (rs.next()) {
				SimexamPaper sp = new SimexamPaper(rs.getInt(6));
				ExamPaper ep = new ExamPaper(rs.getInt(1), rs.getString(2));
				ep.setShowmod(rs.getInt(3));
				sp.setBegintime(rs.getTimestamp(4));
				sp.setEndtime(rs.getTimestamp(5));
				sp.setExamPaper(ep);
				pps.add(sp);
			}
		} catch (Exception e) {
			logger.error("获取模考列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pps;
	}

	public void addSimexampaper(SimexamPaper simexamPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.SPAPER_ADD));
			ps.setInt(1, simexamPaper.getExamPaper().getId());
			ps.setInt(2, simexamPaper.getCourse().getId());
			ps.setTimestamp(3, simexamPaper.getBegintime());
			ps.setTimestamp(4, simexamPaper.getEndtime());

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加模考！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public SimexamPaper getSimexamPaperById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		SimexamPaper sp = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.SPAPER_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				sp = new SimexamPaper(rs.getInt(1));
				ExamPaper ep = new ExamPaper(rs.getInt(2));
				sp.setCourse(new Course(rs.getInt(3)));
				sp.setBegintime(rs.getTimestamp(4));
				sp.setEndtime(rs.getTimestamp(5));
				sp.setExamPaper(ep);
			}
		} catch (Exception e) {
			logger.error("获取模考列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return sp;
	}

	public boolean checkSpInCourse(SimexamPaper simexamPaper)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// simexamPaper = getSimexamPaperById(simexamPaper.getId());
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.SPAPER_CHECK_INSP));
			ps.setInt(1, simexamPaper.getExamPaper().getId());
			ps.setInt(2, simexamPaper.getCourse().getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测课程是否包含该试卷模考失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteSimexampaper(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.SPAPER_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除课程模考失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<QuizPaper> getQuizpaperByCid(int course) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<QuizPaper> pps = new ArrayList<QuizPaper>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.QPAPER_QUERY_BYCID));
			ps.setInt(1, course);
			rs = ps.executeQuery();

			while (rs.next()) {
				QuizPaper paper = new QuizPaper(rs.getInt(1));
				ExamPaper ep = new ExamPaper(rs.getInt(2), rs.getString(3));
				ep.setShowmod(rs.getInt(4));
				ep.setModifytime(rs.getTimestamp(5));
				ep.setCreatetime(rs.getTimestamp(6));
				ep.setDuring(rs.getInt(7));
				paper.setExamPaper(ep);
				pps.add(paper);
			}
		} catch (Exception e) {
			logger.error("获取试卷列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pps;
	}

	public void addQuizpaper(QuizPaper quizPaper) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.QPAPER_ADD));
			ps.setInt(1, quizPaper.getExamPaper().getId());
			ps.setInt(2, quizPaper.getCourse().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("检测课程是否包含该试卷模考失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public boolean checkQpInCourse(int exampaper, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.QPAPER_CHECK_INQP));
			ps.setInt(1, exampaper);
			ps.setInt(2, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("检测课程是否包含该试卷模考失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteQuizpaper(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(CourseConstants.QPAPER_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除课程模考失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyCourse> listselectedCourse(CourseType ctypeTree, int ctid,
			int status, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> courses = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select * from (select t.*, rownum rn from( ")
					.append(
							" select c.id,c.name,eu.id euid,eu.realname,ca.starttime,")
					.append(
							" ct.id ctid,ct.name ctname,euc.id eucid,euc.realname eucrealname,")
					.append(
							" eu.username,dep.name depname,c.islink,c.roomstart,c.roomend,c.teacherName  from study_course ca,course c,eluser eu,")
					.append(
							" course_type ct,eluser euc,department dep  where c.creater = euc.id ")
					.append(
							" and ct.id = c.ctypeid and ca.courseid = c.id and ca.userid = eu.id ")
					.append(
							" and euc.depid=dep.id and ca.valid = ? and ct.id in("
									+ createPerTypeId(ctypeTree, ctid)
									+ ") order by ca.starttime  desc ) t")
					.append("  where  rownum<=?) where rn>= ?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, status);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(6), rs.getString(7)));
				MyCourse mc = new MyCourse();
				ELUser eluser = new ELUser(rs.getInt(3), rs.getString(4));
				eluser.setUsername(rs.getString(10));
				mc.setUser(eluser);
				mc.setApplyDate(rs.getTimestamp(5));
				Department department = new Department();
				department.setName(rs.getString(11));
				mc.setDepartment(department);
				c.setCreater(new ELUser(rs.getInt(8), rs.getString(9)));
				mc.setCourse(c);
				c.setIslink(rs.getInt(12));
				c.setRoomstart(rs.getTimestamp(13));
				c.setRoomend(rs.getTimestamp(14));
				c.setTeacherName(rs.getString(15));

				courses.add(mc);
			}
		} catch (Exception e) {
			logger.error("选课审核列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public int listselectedCourseSize(CourseType ctypeTree, int ctid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from( ")
					.append(
							" select c.id,c.name,eu.id euid,eu.realname,ca.starttime,")
					.append(
							" ct.id ctid,ct.name ctname,euc.id eucid,euc.realname eucrealname,")
					.append(
							" eu.username,dep.name depname  from study_course ca,course c,eluser eu,")
					.append(
							" course_type ct,eluser euc,department dep  where c.creater = euc.id ")
					.append(
							" and ct.id = c.ctypeid and ca.courseid = c.id and ca.userid = eu.id ")
					.append(
							" and euc.depid=dep.id and ca.valid = ? and ct.id in("
									+ createPerTypeId(ctypeTree, ctid)
									+ ") order by ca.starttime  desc ) t");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, status);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("选课审核列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 查询有权限的培训班关联相关课程类型
	 * 
	 * @author luocw
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public List<Course> listAllSelectCourse(CourseType ctypeTree, int depid,
			String name, int ctid, int pageNow, int pageSize, int status,
			int classId, int role) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();

			String x = Integer.toString(ctid);
			String ids = courseTypeById(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
											// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend ,c.islink from course c, course_type ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(" and u.depid=dep.id  and c.name like ? ")
					// and c.status = ? 2012-1-3 需要显示所有课程
					.append(" and c.status != 9 ")
					.append(" and ct.id in (" + ids + ") ")
					// and dep.lid>=? and dep.rid <=?
					.append(
							" and c.id not in (select cc.courseid from class_course cc where cc.classid =? ) order by c.createtime desc )t ")
					.append(" where rownum <= ? ) where rn>=?");

			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, status);
			ps.setString(1, "%" + name + "%");
			// ps.setInt(2, dep.getLid());
			// ps.setInt(3, dep.getRid());
			ps.setInt(2, classId);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setIslink(rs.getInt(14));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	/**
	 * 查询有权限的培训班关联相关课程类型合计
	 * 
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param pageNow
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws ElException
	 */
	public int listAllSelectCourseSize(CourseType ctypeTree, int depid,
			String name, int ctid, int status, int classId, int role)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();

			String x = Integer.toString(ctid);
			String ids = courseTypeById(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
											// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot  from course c, course_type ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(" and u.depid=dep.id  and c.name like ? ")// and
																		// c.status
																		// = ?
																		// 2012-1-3
																		// 需要显示所有课程
					//.append(" and c.status = 5 ")
					.append(" and ct.id in (" + ids + ")  ") // and
																// dep.lid>=?
																// and dep.rid
																// <=?
					.append(
							" and c.id not in  (select cc.courseid from class_course cc where cc.classid =? ) order by c.createtime desc )t ");

			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, status);
			ps.setString(1, "%" + name + "%");
			// ps.setInt(2, dep.getLid());
			// ps.setInt(3, dep.getRid());
			ps.setInt(2, classId);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	

	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser, Department depTree, Station staTree,int role)
			throws ElException {
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String staLidRid = " and ";
		List<ELUser> userList = new ArrayList<ELUser>();
		List<Station> staList = new ArrayList<Station>();
		try {
			ct = DBConnection.getConnection();
			Station sta = new Station();
			// StringBuffer deptsql = new StringBuffer();
			// deptsql.append("select * from DEPARTMENT where id=?");
			// ps = ct.prepareStatement(deptsql.toString());
			// ps.setInt(1, depid);
			// rs = ps.executeQuery();
			// rs.next();
			// Department dept = new Department();
			// dept.setId(rs.getInt(1));
			// dept.setName(rs.getString(2));
			// ElNode node = new ElNode(rs.getInt(4));
			// dept.setParent(node);
			// dept.setLid(rs.getInt("lid"));
			// dept.setRid(rs.getInt("rid"));
			// deptList.add(dept);

			String x = Integer.toString(depid);
		//	String ids = createDepartmentId(depTree, depid);
		
		//	if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
											// ,当角色不为1时ids的只有一个根节点时也不截取
		//		ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
		//				: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			ps = ct.prepareStatement("select * from station where id=?");
			ps.setInt(1, staTree.getId());
			rs = ps.executeQuery();
			rs.next();
			sta.setId(rs.getInt("id"));
			sta.setName(rs.getString("name"));
			sta.setLid(rs.getInt("lid"));
			sta.setRid(rs.getInt("rid"));
			staLidRid = staLidRid + " and sta.lid>="+rs.getInt("lid")+" and sta.rid<= "+rs.getInt("rid");
			staList.add(sta);
			
			StringBuffer usersql = new StringBuffer();
			usersql
					.append(
							"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ from  ")
					.append(" eluser eu ")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id inner join station sta on sta.id=eu.staid left join ELROLE role on eu.role=role.id where  exists (select id from department) and dp.ID is not null");
			if (elUser != null) {
				if (elUser.getSex()!=null&&!elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (elUser.getRealname()!=null&&!elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (elUser.getUsername()!=null&&!elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				if(elUser.getXianzhiwei()!=null&&!elUser.getXianzhiwei().equals("")){
					usersql.append(" and sta.name like '%"
							+ elUser.getXianzhiwei() + "%'");
				}
//				if (null != elUser.getJingzhong()
//						&& !elUser.getJingzhong().equals("0")
//						&& !elUser.getJingzhong().equals("")) {
//					usersql.append(" and eu.jingzhong = '"
//							+ elUser.getJingzhong().trim() + "' ");
//				}
//				if (null != elUser.getDishi() && !elUser.getDishi().equals("0")) {
//					usersql.append(" and eu.dishi = '"
//							+ elUser.getDishi().trim() + "' ");
//				}
//				if (null != elUser.getZhiji() && !elUser.getZhiji().equals("0")) {
//					usersql.append(" and eu.zhiji = '"
//							+ elUser.getZhiji().trim() + "' ");
//				}
//				if (null != elUser.getZhiwu() && !elUser.getZhiwu().equals("0")) {
//					usersql.append(" and eu.zhiwu = '"
//							+ elUser.getZhiwu().trim() + "' ");
//				}
				if (elUser.getJingzhong()>0) {
					usersql.append(" and eu.jingzhong = "
							+ elUser.getJingzhong());
				}
				if (elUser.getDishi()>0) {
					usersql.append(" and eu.dishi = "
							+ elUser.getDishi());
				}
				if (elUser.getZhiji()>0) {
					usersql.append(" and eu.zhiji = "
							+ elUser.getZhiji());
				}
				if (elUser.getZhiwu()>0) {
					usersql.append(" and eu.zhiwu = "
							+ elUser.getZhiwu());
				}
				if (null != elUser.getGangwei()
						&& !elUser.getGangwei().equals("0")) {
					usersql.append(" and eu.gangwei = '"
							+ elUser.getGangwei().trim() + "' ");
				}
				if (elUser.getIsAssign()!=null&&!elUser.getIsAssign().equals("")) {
					if (elUser.getIsAssign().equals("0")) {
						usersql
								.append(" and eu.id in(select userid from study_course where courseid="
										+ courseid + " and classid=0) ");
					} else {
						usersql
								.append(" and eu.id not in(select userid from study_course where courseid="
										+ courseid + " and classid=0) ");
					}
				}
			}
			usersql.append(" and sta.lid>="+sta.getLid()+" and sta.rid<= "+sta.getRid() );
			usersql.append(" )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			// ps.setInt(1, dept.getLid());
			// ps.setInt(2, dept.getRid());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			// ps.setInt(2, state);
			rs = ps.executeQuery();

			while (rs.next()) {
				ELUser user = new ELUser();
				user.setId(rs.getInt(1));
				user.setRealname(rs.getString(2));
				user
						.setDepartment(new Department(rs.getInt(3), rs
								.getString(4)));
				user.setUsername(rs.getString(5));
				user.setJingzhong(rs.getInt(6));
				user.setRole(new ElRole(rs.getInt(7), rs.getString(8)));
				user.setSex(rs.getString(9));
				user.setShenfenzheng(String.valueOf(getAge(rs.getString(10))));
				user.setAge(rs.getInt(11));
				user.setIsAssign("未分配");
				userList.add(user);
			}
			ps = ct
					.prepareStatement("select userid from study_course where courseid=? and classid=0");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				for (ELUser users : userList) {
					if (users.getId() == rs.getInt(1)) {
						users.setIsAssign("已分配");
					}
				}
			}
			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return returnList;
			}
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return userList;
	}

	public int listAssignedUserSize(int depid, int courseid, int state,
			List<Integer> userid, String starttime, String endtime,
			ELUser elUser, Department depTree,Station staTree, int role) throws ElException {
		List<ELUser> userList = new ArrayList<ELUser>();
		List<ELUser> returnList = new ArrayList<ELUser>();
		List<Station> staList = new ArrayList<Station>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String staLidRid = " and ";
		try {
			ct = DBConnection.getConnection();
			Station sta = new Station();
			// StringBuffer deptsql = new StringBuffer();
			// deptsql.append("select * from DEPARTMENT where id=?");
			// ps = ct.prepareStatement(deptsql.toString());
			// ps.setInt(1, depid);
			// rs = ps.executeQuery();
			// rs.next();
			// Department dept = new Department();
			// dept.setId(rs.getInt(1));
			// dept.setName(rs.getString(2));
			// ElNode node = new ElNode(rs.getInt(4));
			// dept.setParent(node);
			// dept.setLid(rs.getInt("lid"));
			// dept.setRid(rs.getInt("rid"));
			// deptList.add(dept);

			String x = Integer.toString(depid);
		//	String ids = createDepartmentId(depTree, depid);
		
		//	if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
											// ,当角色不为1时ids的只有一个根节点时也不截取
		//		ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
		//				: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			
			ps = ct.prepareStatement("select * from station where id=?");
			ps.setInt(1, staTree.getId());
			rs = ps.executeQuery();
			rs.next();
			sta.setId(rs.getInt("id"));
			sta.setName(rs.getString("name"));
			sta.setLid(rs.getInt("lid"));
			sta.setRid(rs.getInt("rid"));
			staLidRid = staLidRid + " and sta.lid>="+rs.getInt("lid")+" and sta.rid<= "+rs.getInt("rid");
			staList.add(sta);
			
			StringBuffer usersql = new StringBuffer();
			
				usersql
				.append(
						"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng from  ")
				.append(" eluser eu ")
				.append(
						" left join DEPARTMENT dp on eu.depid=dp.id inner join station sta on sta.id=eu.staid left join ELROLE role on eu.role=role.id where  exists (select id from department) and dp.ID is not null");
			
			
			if (elUser != null) {
				if (elUser.getSex()!=null&&!elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (elUser.getRealname()!=null&&!elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (elUser.getUsername()!=null&&!elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
				}
				if(elUser.getXianzhiwei()!=null&&!elUser.getXianzhiwei().equals("")){
					usersql.append(" and sta.name like '%"
							+ elUser.getXianzhiwei() + "%'");
				}
//				if (null != elUser.getJingzhong()
//						&& !elUser.getJingzhong().equals("0")
//						&& !elUser.getJingzhong().equals("")) {
//					usersql.append(" and eu.jingzhong = '"
//							+ elUser.getJingzhong().trim() + "' ");
//				}
//				if (null != elUser.getDishi() && !elUser.getDishi().equals("0")) {
//					usersql.append(" and eu.dishi = '"
//							+ elUser.getDishi().trim() + "' ");
//				}
//				if (null != elUser.getZhiji() && !elUser.getZhiji().equals("0")) {
//					usersql.append(" and eu.zhiji = '"
//							+ elUser.getZhiji().trim() + "' ");
//				}
//				if (null != elUser.getZhiwu() && !elUser.getZhiwu().equals("0")) {
//					usersql.append(" and eu.zhiwu = '"
//							+ elUser.getZhiwu().trim() + "' ");
//				}
				if (elUser.getJingzhong()>0) {
					usersql.append(" and eu.jingzhong = "
							+ elUser.getJingzhong());
				}
				if (elUser.getDishi()>0) {
					usersql.append(" and eu.dishi = "
							+ elUser.getDishi());
				}
				if (elUser.getZhiji()>0) {
					usersql.append(" and eu.zhiji = "
							+ elUser.getZhiji());
				}
				if (elUser.getZhiwu()>0) {
					usersql.append(" and eu.zhiwu = "
							+ elUser.getZhiwu());
				}
				if (null != elUser.getGangwei()
						&& !elUser.getGangwei().equals("0")) {
					usersql.append(" and eu.gangwei = '"
							+ elUser.getGangwei().trim() + "' ");
				}
				if (elUser.getIsAssign()!=null&&!elUser.getIsAssign().equals("")) {
					if (elUser.getIsAssign().equals("0")) {
						usersql
								.append(" and eu.id in(select userid from study_course where courseid="
										+ courseid + " and classid=0) ");
					} else {
						usersql
								.append(" and eu.id not in(select userid from study_course where courseid="
										+ courseid + " and classid=0) ");
					}
				}
			}
			usersql.append(" and sta.lid>="+sta.getLid()+" and sta.rid<= "+sta.getRid() );
			usersql.append(" )t where rownum<=? ) where rn>=?");
			ps = ct.prepareStatement(usersql.toString());
			ps.setInt(1, 999999);
			ps.setInt(2, 1);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser();
				user.setId(rs.getInt(1));
				user.setRealname(rs.getString(2));
				user
						.setDepartment(new Department(rs.getInt(3), rs
								.getString(4)));
				user.setUsername(rs.getString(5));
				user.setJingzhong(rs.getInt(6));
				user.setRole(new ElRole(rs.getInt(7), rs.getString(8)));
				user.setSex(rs.getString(9));
				user.setShenfenzheng(rs.getString(10));
				user.setAge(getAge(rs.getString(10)));
				user.setIsAssign("未分配");
				userList.add(user);
			}

			Calendar calendar = Calendar.getInstance();
			if (starttime != null && !"".equals(starttime)) {
				calendar.setTime(DateUtils.parseDate(starttime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, ">")) {
						returnList.add(user);
					}
				}
			}
			if (endtime != null && !"".equals(endtime)) {
				calendar.setTime(DateUtils.parseDate(endtime,
						new String[] { "yyyy-mm-dd" }));
				for (ELUser user : userList) {
					if (compareAge(user.getShenfenzheng(), calendar, "<")) {
						returnList.add(user);
					}
				}
			}
			if ((starttime != null && !"".equals(starttime))
					|| (endtime != null && !"".equals(endtime))) {
				return returnList.size();
			}
			return userList.size();
		} catch (Exception e) {
			logger.error("分配学员失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private static int getAge(String IDCardNum) {
		if (IDCardNum == null) {
			return -1;
		}
		int year, month, day, idLength = IDCardNum.length();
		Calendar cal1 = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		if (idLength == 18) {
			year = Integer.parseInt(IDCardNum.substring(6, 10));
			month = Integer.parseInt(IDCardNum.substring(10, 12));
			day = Integer.parseInt(IDCardNum.substring(12, 14));
		} else if (idLength == 15) {
			year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
			month = Integer.parseInt(IDCardNum.substring(8, 10));
			day = Integer.parseInt(IDCardNum.substring(10, 12));
		} else {
			return -1;
		}
		cal1.set(year, month, day);
		return getYearDiff(today, cal1);
	}

	private boolean compareAge(String IDCardNum, Calendar calendar,
			String compare) {
		boolean bo = false;
		if (IDCardNum == null) {
			return bo;
		}
		int year, month, day, idLength = IDCardNum.length();
		Calendar cal = Calendar.getInstance();
		if (idLength == 18) {
			year = Integer.parseInt(IDCardNum.substring(6, 10));
			month = Integer.parseInt(IDCardNum.substring(10, 12));
			day = Integer.parseInt(IDCardNum.substring(12, 14));
		} else if (idLength == 15) {
			year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
			month = Integer.parseInt(IDCardNum.substring(8, 10));
			day = Integer.parseInt(IDCardNum.substring(10, 12));
		} else {
			return bo;
		}
		cal.set(year, month, day);
		if (compare.equals(">")) {
			if (calendar.getTimeInMillis() > cal.getTimeInMillis()) {
				bo = true;
			}
		} else if (compare.equals("<")) {
			if (calendar.getTimeInMillis() < cal.getTimeInMillis()) {
				bo = true;
			}
		}
		return bo;
	}

	private static int getYearDiff(Calendar cal, Calendar cal1) {
		int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));
		int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));
		return (y * 12 + m) / 12;
	}

	// 我创建的多媒体教室
	public List<Course> MyMultis(CourseType ctypeTree, int creater, int ctid,
			String name, int pageNow, int pageSize) throws ElException {
		List<Course> courses = new ArrayList<Course>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();

			String x = Integer.toString(ctid);
			String ids = courseTypeById(ctypeTree, ctid);
			if (creater != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
												// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName from course c,(select * from course_type where id in("
									+ ids + ")) ct")
					.append(" where c.ctypeid=ct.id  and c.creater=?")
					.append(
							"  and c.name like ? and c.islink=4  order by c.id desc)t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, creater);
			ps.setString(2, "%" + name + "%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				// c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7)));
				c.setCredit(rs.getInt(9));
				c.setDuring(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	// 我创建的多媒体教室数量
	public int listMyMultisCount(CourseType ctypeTree, int creater, int ctid,
			String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();

			String x = Integer.toString(ctid);
			String ids = courseTypeById(ctypeTree, ctid);
			if (creater != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
												// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) rn from (select c.id,")
					.append(
							" c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,c.credit,c.during,c.islink from course c,(select * from course_type where  id in("
									+ ids + ") )ct")
					.append(" where c.ctypeid=ct.id  and c.creater=?")
					.append(
							"  and c.name like ? and c.islink=4 order by c.id desc)t ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, creater);
			ps.setString(2, "%" + name + "%");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 组合搜索课程
	public List<Course> listCombinationCourse(CourseType ctypeTree,
			Course course, int role, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();

			String x = Integer.toString(course.getCtype().getId());
			String ids = courseTypeById(ctypeTree, course.getCtype().getId());
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
											// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = course.getCtype().getId() == 1 ? ids.substring(
						x.length() + 1, ids.length()) : ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			if (course.getOwner() == null) {
				ELUser eu = new ELUser();
				eu.setUsername("");
				eu.setRealname("");
				course.setOwner(eu);
			}
			String sqlstr = "";
			// sqlstr+="select * from (select t.*, rownum rn from (select
			// c.id,c.name,c.ctypeid
			// ,c.status,c.createtime,c.modifytime,c.creater,ct.name
			// ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName
			// from course c,(select * from course_type ";
			/*
			 * sqlstr+="select * from (select t.*, rownum rn from (select
			 * c.id,c.name,c.ctypeid
			 * ,c.status,c.createtime,c.modifytime,c.creater,ct.name
			 * ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName
			 * from course c,course_type ct"; //sqlstr+="where id
			 * in("+createPerTypeId(ctypeTree,course.getCtype().getId())+"))
			 * ct"; sqlstr+=" where c.ctypeid=ct.id ";
			 * sqlstr+=course.getOwner()==null?"":" and c.creater in(select id
			 * from eluser eu where eu.username like
			 * '%"+course.getOwner().getUsername()+"%')";
			 * sqlstr+=course.getOwner()==null?"":" and c.creater in(select id
			 * from eluser eu where eu.realname like
			 * '%"+course.getOwner().getRealname()+"%')";
			 * //sqlstr+=course.getCtype()==null?"":(course.getCtype().getId()==-1||course.getCtype().getId()==0)?"":"
			 * and ct.id="+course.getCtype().getId();
			 * sqlstr+=course.getCtype()==null?"":(course.getCtype().getId()==-1||course.getCtype().getId()==0)?"":"
			 * and ct.id in
			 * ("+createPerTypeId(ctypeTree,course.getCtype().getId())+")";
			 * sqlstr+=(course.getName()==null ||
			 * course.getName().equals(""))?"":" and c.name like
			 * '%"+course.getName()+"%'";
			 * sqlstr+=(course.getBegintime()==null&&course.getEndtime()==null)?"":"
			 * and to_date(to_char(c.createtime,'yyyy-mm-dd'),'yyyy-mm-dd')
			 * between to_date('"+course.getBegintime()+"','yyyy-mm-dd') and
			 * to_date('"+course.getEndtime()+"','yyyy-mm-dd')"; sqlstr+=" order
			 * by c.createtime) t where rownum <= "+pageNow+" ) where
			 * rn>="+pageSize+"";
			 */

			sqlstr += "select * from (select t.*, rownum rn from (select c.id,c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,ct.name ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName,eu.realname,c.astatus from course c,course_type ct,eluser eu ";
			// sqlstr+="where id
			// in("+createPerTypeId(ctypeTree,course.getCtype().getId())+"))
			// ct";
			sqlstr += " where c.ctypeid=ct.id and c.creater=eu.id ";
			sqlstr += course.getOwner() == null ? ""
					: " and eu.username like '%"
							+ course.getOwner().getUsername() + "%'";
			sqlstr += course.getOwner() == null ? ""
					: " and eu.realname like '%"
							+ course.getOwner().getRealname() + "%'";
			sqlstr += course.getStatus() == -1 ? "" : " and c.status = "
					+ course.getStatus();
			// sqlstr+=course.getCtype()==null?"":(course.getCtype().getId()==-1||course.getCtype().getId()==0)?"":"
			// and ct.id="+course.getCtype().getId();
			sqlstr += course.getCtype() == null ? "" : (course.getCtype()
					.getId() == -1 || course.getCtype().getId() == 0) ? ""
					: " and ct.id in (" + ids + ")";
			sqlstr += (course.getName() == null || course.getName().equals("")) ? ""
					: " and c.name like '%" + course.getName() + "%'";
			sqlstr += (course.getBegintime() == null && course.getEndtime() == null) ? ""
					: " and to_date(to_char(c.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"
							+ course.getBegintime()
							+ "','yyyy-mm-dd') and to_date('"
							+ course.getEndtime() + "','yyyy-mm-dd')";
			sqlstr += " order by c.createtime desc) t where rownum <= "
					+ pageNow + " ) where rn>=" + pageSize + "";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				// c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7)));
				c.setCredit(rs.getInt(9));
				c.setDuring(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setAstatus(rs.getInt(16));
				ELUser user = new ELUser();
				user.setRealname(rs.getString(15));
				c.setCreater(user);
				courses.add(c);
			}
			return courses;
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	// 组合搜索课程
	public int listCombinationCourseCount(CourseType ctypeTree, Course course,
			int role, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String x = Integer.toString(course.getCtype().getId());
			String ids = courseTypeById(ctypeTree, course.getCtype().getId());
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
											// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = course.getCtype().getId() == 1 ? ids.substring(
						x.length() + 1, ids.length()) : ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			if (course.getOwner() == null) {
				ELUser eu = new ELUser();
				eu.setUsername("");
				eu.setRealname("");
				course.setOwner(eu);
			}
			String sqlstr = "";
			// sqlstr+="select * from (select t.*, rownum rn from (select
			// c.id,c.name,c.ctypeid
			// ,c.status,c.createtime,c.modifytime,c.creater,ct.name
			// ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName
			// from course c,(select * from course_type ";
			/*
			 * sqlstr+="select * from (select t.*, rownum rn from (select
			 * c.id,c.name,c.ctypeid
			 * ,c.status,c.createtime,c.modifytime,c.creater,ct.name
			 * ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName
			 * from course c,course_type ct"; //sqlstr+="where id
			 * in("+createPerTypeId(ctypeTree,course.getCtype().getId())+"))
			 * ct"; sqlstr+=" where c.ctypeid=ct.id ";
			 * sqlstr+=course.getOwner()==null?"":" and c.creater in(select id
			 * from eluser eu where eu.username like
			 * '%"+course.getOwner().getUsername()+"%')";
			 * sqlstr+=course.getOwner()==null?"":" and c.creater in(select id
			 * from eluser eu where eu.realname like
			 * '%"+course.getOwner().getRealname()+"%')";
			 * //sqlstr+=course.getCtype()==null?"":(course.getCtype().getId()==-1||course.getCtype().getId()==0)?"":"
			 * and ct.id="+course.getCtype().getId();
			 * sqlstr+=course.getCtype()==null?"":(course.getCtype().getId()==-1||course.getCtype().getId()==0)?"":"
			 * and ct.id in
			 * ("+createPerTypeId(ctypeTree,course.getCtype().getId())+")";
			 * sqlstr+=(course.getName()==null ||
			 * course.getName().equals(""))?"":" and c.name like
			 * '%"+course.getName()+"%'";
			 * sqlstr+=(course.getBegintime()==null&&course.getEndtime()==null)?"":"
			 * and to_date(to_char(c.createtime,'yyyy-mm-dd'),'yyyy-mm-dd')
			 * between to_date('"+course.getBegintime()+"','yyyy-mm-dd') and
			 * to_date('"+course.getEndtime()+"','yyyy-mm-dd')"; sqlstr+=" order
			 * by c.createtime) t where rownum <= "+pageNow+" ) where
			 * rn>="+pageSize+"";
			 */
			sqlstr += "select count(*) from (select t.*, rownum rn from (select c.id,c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,ct.name ctname,c.credit,c.during,c.islink,c.roomstart,c.roomend,c.teacherName from course c,course_type ct,eluser eu ";
			// sqlstr+="where id
			// in("+createPerTypeId(ctypeTree,course.getCtype().getId())+"))
			// ct";
			sqlstr += " where c.ctypeid=ct.id and c.creater=eu.id ";
			sqlstr += course.getOwner() == null ? ""
					: " and eu.username like '%"
							+ course.getOwner().getUsername() + "%'";
			sqlstr += course.getOwner() == null ? ""
					: " and eu.realname like '%"
							+ course.getOwner().getRealname() + "%'";
			// sqlstr+=course.getCtype()==null?"":(course.getCtype().getId()==-1||course.getCtype().getId()==0)?"":"
			// and ct.id="+course.getCtype().getId();
			sqlstr += course.getCtype() == null ? "" : (course.getCtype()
					.getId() == -1 || course.getCtype().getId() == 0) ? ""
					: " and ct.id in (" + ids + ")";
			sqlstr += (course.getName() == null || course.getName().equals("")) ? ""
					: " and c.name like '%" + course.getName() + "%'";
			sqlstr += (course.getBegintime() == null && course.getEndtime() == null) ? ""
					: " and to_date(to_char(c.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"
							+ course.getBegintime()
							+ "','yyyy-mm-dd') and to_date('"
							+ course.getEndtime() + "','yyyy-mm-dd')";
			sqlstr += " order by c.createtime) t ) ";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<CourseType> getCourseType() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseType> list = new ArrayList<CourseType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,name from course_type order by id");
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseType courseType = new CourseType();
				courseType.setId(rs.getInt(1));
				courseType.setName(rs.getString(2));
				list.add(courseType);
			}
			return list;
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Course> listCourseByName(int pageNow, int pageSize,
			Course course, CourseType ctypeTree) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> zxc = new ArrayList<Course>();
		String sqlstr = "";
		try {
			ct = DBConnection.getConnection();

			/*
			 * ps = ct.prepareStatement(ElQuerySql
			 * .getSQL(FrontConstants.FRONT_COURSE_BYNAME)); ps.setString(1, "%" +
			 * "%"); ps.setInt(3, pageNow); ps.setInt(4, pageSize);
			 */
			sqlstr += "select * from (select t.*, rownum rn from(select c.id,c.name,c.description,eu.realname, c.ctypeid,ct.name ctname,c.createtime,c.mainimg,c.teachername,c.kj_appendix, c.jy_appendix from course c,eluser eu,course_type ct";
			sqlstr += " where c.ctypeid = ct.id and c.creater =eu.id and c.status=1";
			sqlstr += course == null ? "" : course.getName() == null ? ""
					: " and c.name like '%" + course.getName() + "%'";
			sqlstr += course == null ? ""
					: course.getDescription() == null ? ""
							: "or c.description like '%"
									+ course.getDescription() + "%')";
			sqlstr += course == null ? "" : course.getCtype() == null ? ""
					: course.getCtype().getId() == 0
							|| course.getCtype().getId() == -1 ? ""
							: " and ct.id=" + course.getCtype().getId();
			sqlstr += " order by c.createtime desc ) t where rownum <= "
					+ pageNow + " ) where rn>=" + pageSize + "";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course cs = new Course(rs.getInt(1), rs.getString(2));
				cs.setDescription(rs.getString(3));
				cs.setCreater(new ELUser(0, rs.getString(4)));
				cs.setCtype(new CourseType(rs.getInt(5), rs.getString(6)));
				cs.setCreatetime(rs.getTimestamp(7));
				cs.setMainimg(rs.getString(8));
				cs.setTeacherName(rs.getString(9));
				String kj_appendix = rs.getString(10);
				kj_appendix = kj_appendix == null ? "assist_plan_stuff_download.action?fileName="
						: (kj_appendix.indexOf("//") < 0 ? "assist_plan_stuff_download.action?fileName="
								+ kj_appendix
								: kj_appendix);
				cs.setKj_appendix(kj_appendix);
				kj_appendix = rs.getString(11);
				kj_appendix = kj_appendix == null ? "assist_plan_stuff_download.action?fileName="
						: (kj_appendix.indexOf("//") < 0 ? "assist_plan_stuff_download.action?fileName="
								+ kj_appendix
								: kj_appendix);
				cs.setJy_appendix(kj_appendix);
				zxc.add(cs);
			}
		} catch (Exception e) {
			logger.error("最新课程列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return zxc;
	}

	public List<Course> readlistInitlistMyCourse(CourseType ctypeTree,
			int ctid, int role, String name, int pageNow, int pageSize)
			throws ElException {
		List<Course> courses = new ArrayList<Course>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
//			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(CourseConstants.CTYPE_LRID));
//			ps.setInt(1, ctid);
//			int lid = 0;
//			int rid = 0;
//
//			rs = ps.executeQuery();
//
//			if (rs.next()) {
//				lid = rs.getInt(2);
//				rid = rs.getInt(3);
//			}

			String x = Integer.toString(ctid);
			String ids = createPerTypeId(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			ps = ct
					.prepareStatement("select * from (select t.*, rownum rn from ("
							+ "select c.id,c.name,c.ctypeid ,c.status,c.createtime,c.modifytime,c.creater,ct.name ctname,c.credit,c.during,c.islink,c.TEACHERNAME "
							+ "from course c, (select * from course_type where  id in("
							+ ids
							+ ") ) ct "
							+ "where c.ctypeid=ct.id and c.status !=2 and c.status is not null  order by c.id desc "
							+ ")t where rownum <= ? ) where rn>=?");
			// ps.setInt(1, lid);
			// ps.setInt(2, rid);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				// c.setDescription(rs.getString(4));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7)));
				c.setCredit(rs.getInt(9));
				c.setDuring(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				c.setTeacherName(rs.getString(12));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public int readlistInitlistMyCourseCount(CourseType ctypeTree, int ctid,
			int role, String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int t = 0;
		try {
//			name = name == null ? "" : name.trim();
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(CourseConstants.CTYPE_LRID));
//			ps.setInt(1, ctid);
//			int lid = 0;
//			int rid = 0;
//
//			rs = ps.executeQuery();
//
//			if (rs.next()) {
//				lid = rs.getInt(2);
//				rid = rs.getInt(3);
//			}

			String x = Integer.toString(ctid);
			String ids = createPerTypeId(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			ps = ct
					.prepareStatement("select count(*) from course c, (select * from course_type where  id in("
							+ ids
							+ ") ) ct "
							+ "where c.ctypeid=ct.id  and c.status !=2 and c.status is not null order by c.id desc ");
			// ps.setInt(1, lid);
			// ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				t = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return t;
	}

	public List<Course> examroom_listAllCourseFromThis(CourseType ctypeTree,
			int depid, int role, String name, int ctid, int pageNow,
			int pageSize, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			String x = Integer.toString(ctid);
			String ids = courseTypeById(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg  from course c, course_type ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(" and u.depid=dep.id  and c.name like ? ")
					// and c.status = ?
					.append(
							" and ct.id in (" + ids
									+ ") order by c.createtime desc )t ")
					.append(" where rownum <= ? ) where rn>=?");

			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, CourseConstants.COURSE_STATUS_OPEN);
			// ps.setInt(1, status);//modify by jiahaijiang
			ps.setString(1, "%" + name + "%");
			// ps.setInt(3, dep.getLid());
			// ps.setInt(4, dep.getRid());
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setIslink(rs.getInt(15));
				c.setMainimg(rs.getString(16));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}

	public int examroom_listAllCourseSizeFromThis(CourseType ctypeTree,
			int depid, int role, String name, int ctid, int status)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();
			String x = Integer.toString(ctid);
			String ids = createPerTypeId(ctypeTree, ctid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot  from course c, course_type ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(" and u.depid=dep.id  and c.name like ? ")// and
																		// c.status
																		// = ?
					.append(
							" and ct.id in (" + ids
									+ ") order by c.createtime desc )t ");

			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, CourseConstants.COURSE_STATUS_OPEN);
			// ps.setInt(1, status);//modify by jiahaijiang
			ps.setString(1, "%" + name + "%");
			// ps.setInt(3, dep.getLid());
			// ps.setInt(4, dep.getRid());
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public int listAllCourseSizeFromThis(CourseType ctypeTree, int depid,
			String name, int ctid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();

			String x = Integer.toString(ctid);
			String ids = createPerTypeId(ctypeTree, ctid);
			if (!ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
								// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot  from course c, course_type ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status = ? and c.name like ? ")
					.append(
							" and ct.id in (" + ids
									+ ") order by c.createtime desc )t ");

			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, CourseConstants.COURSE_STATUS_OPEN);
			ps.setInt(1, status);// modify by jiahaijiang
			ps.setString(2, "%" + name + "%");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
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
	 private String courseTypeById(CourseType ctypeTree, int ctid){
	 if(ctypeTree!=null){
	 if(ctypeTree.getId()!=ctid){
	 ctypeTree = courseTypeById(ctypeTree.getChild(),ctid);
	 }
	 if(ctypeTree.getChild()!=null){
	 return createCourseTypeId(ctypeTree.getChild(),ctypeTree.getId());
	 }
	 return String.valueOf(ctypeTree.getId());
	 }else{
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
	private String createCourseTypeId(List<CourseType> listType, int id) {
		String ids = id + "";
		for (CourseType type : listType) {
			ids = ids + "," + createCourseTypeId(type.getChild(), type.getId());
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
	private CourseType courseTypeById(List<CourseType> listType, int ctid) {
		CourseType courseType = null;
		for (CourseType type : listType) {
			if (type.getId() != ctid) {
				courseType = courseTypeById(type.getChild(), ctid);
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

	/**
	 * 根据课程id获取所对应的所有场次
	 * 
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getRoomsByCourseid(int courseid) throws ElException {
		List<ExamRoom> list = new ArrayList<ExamRoom>();
		ExamRoom room = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sql = " select id,title from exam_room where courseid=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				room = new ExamRoom();
				room.setId(rs.getInt("id"));
				room.setTitle(rs.getString("title"));
				list.add(room);
			}
		} catch (Exception e) {
			logger.error("获取课程的所有场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public List<Course> getClassByCourseid(int classid) throws ElException {
		List<Course> list = new ArrayList<Course>();
		Course course = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select courseid from class_course where  classid = ?");
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while (rs.next()) {
				course = new Course();
				course.setId(rs.getInt(1));
				list.add(course);
			}
		} catch (Exception e) {
			logger.error("获取课程的所有场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 获取课程的所有章节
	 * 
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public List<CoursePage> getCourseAllCpage(int courseid) throws ElException {
		List<CoursePage> list = new ArrayList<CoursePage>();
		CoursePage coursePage = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,title,getcredit from course_page where courseid=?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			while (rs.next()) {
				coursePage = new CoursePage(rs.getInt(1),rs.getString(2));
				coursePage.setGetcredit(rs.getInt(3));
				list.add(coursePage);
			}
		} catch (Exception e) {
			logger.error("获取课程的所有场次失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	/**
	 * 课程申请记录验证
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public boolean checkCourseRegistration(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select * from course_registration where courseid = ?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("课程申请记录验证出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	/**
	 * 课程申请记录
	 * 
	 * @param coRegistration
	 * @throws ElException
	 */
	public void addCourseRegistration(CourseRegistration coRegistration)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "insert into course_registration(courseid,PlanRecruitStudents,RegistrationStartTime,RegistrationStopTime,StartAge ,StopAge,sex,jingzhong,dishi,zhiwu,zhiji,gangwei,treeType,examroomIds,elclassIds,classScreeningWay,eroomScreeningWay) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, coRegistration.getCourse().getId());
			ps.setString(2, coRegistration.getPlanRecruitStudents());
			ps.setTimestamp(3, coRegistration.getRegistrationStartTime());
			ps.setTimestamp(4, coRegistration.getRegistrationStopTime());
			ps.setInt(5, coRegistration.getStartAge());
			ps.setInt(6, coRegistration.getStopAge());
			ps.setString(7, coRegistration.getSex());
			ps.setString(8, coRegistration.getJingzhong());
			ps.setString(9, coRegistration.getDishi());
			ps.setString(10, coRegistration.getZhiwu());
			ps.setString(11, coRegistration.getZhiji());
			ps.setString(12, coRegistration.getGangwei());
			ps.setString(13, coRegistration.getTreeType());
			ps.setString(14, coRegistration.getExamRooms());
			ps.setString(15, coRegistration.getElclasss());
			ps.setInt(16, coRegistration.getClassScreeningWay());
			ps.setInt(17, coRegistration.getEroomScreeningWay());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("课程申请条件增加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 更新课程申请条件
	 * 
	 * @param eoRegistration
	 * @throws ElException
	 */
	public void alterCourseRegistration(CourseRegistration eoRegistration)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "update course_registration set PlanRecruitStudents=?,RegistrationStartTime=?,RegistrationStopTime=?,StartAge=?,StopAge=?,sex=?,jingzhong=?,dishi=?,zhiwu=?,zhiji=?,gangwei=?,treeType=?,examroomIds=?,elclassIds=?,classScreeningWay=?,eroomScreeningWay=? where courseid=?";
			ps = ct.prepareStatement(sql);
			ps.setString(1, eoRegistration.getPlanRecruitStudents());
			ps.setTimestamp(2, eoRegistration.getRegistrationStartTime());
			ps.setTimestamp(3, eoRegistration.getRegistrationStopTime());
			ps.setInt(4, eoRegistration.getStartAge());
			ps.setInt(5, eoRegistration.getStopAge());
			ps.setString(6, eoRegistration.getSex());
			ps.setString(7, eoRegistration.getJingzhong());
			ps.setString(8, eoRegistration.getDishi());
			ps.setString(9, eoRegistration.getZhiwu());
			ps.setString(10, eoRegistration.getZhiji());
			ps.setString(11, eoRegistration.getGangwei());
			ps.setString(12, eoRegistration.getTreeType());
			ps.setString(13, eoRegistration.getExamRooms());
			ps.setString(14, eoRegistration.getElclasss());
			ps.setInt(15, eoRegistration.getClassScreeningWay());
			ps.setInt(16, eoRegistration.getEroomScreeningWay());
			ps.setInt(17, eoRegistration.getCourse().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("课程申请条件修改失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 查询考场申请条件
	 * 
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public CourseRegistration getCourseRegistration(int courseid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		CourseRegistration elR = new CourseRegistration();
		try {
			String sql = "select courseid,PlanRecruitStudents,RegistrationStartTime,RegistrationStopTime,StartAge,StopAge,sex,jingzhong,dishi,zhiwu,zhiji,gangwei,treeType,examroomIds,elclassIds,classScreeningWay,eroomScreeningWay from course_registration where courseid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if (rs.next()) {
				elR.setCourse(new Course(rs.getInt(1)));
				elR.setPlanRecruitStudents(rs.getString(2));
				elR.setRegistrationStartTime(rs.getTimestamp(3));
				elR.setRegistrationStopTime(rs.getTimestamp(4));
				elR.setStartAge(rs.getInt(5));
				elR.setStopAge(rs.getInt(6));
				elR.setSex(rs.getString(7));
				elR.setJingzhong(rs.getString(8));
				elR.setDishi(rs.getString(9));
				elR.setZhiwu(rs.getString(10));
				elR.setZhiji(rs.getString(11));
				elR.setGangwei(rs.getString(12));
				elR.setTreeType(rs.getString(13));
				// 考场
				List<ExamRoom> ers = new ArrayList<ExamRoom>();
				if (rs.getString(14) != null) {
					List<String> listR = new ArrayList<String>(Arrays.asList(rs
							.getString(14).split(",")));
					for (int i = 0; i < listR.size(); i++) {
						ers.add(new ExamRoom(Integer.parseInt(listR.get(i))));
					}
				}
				elR.setExamRoom(ers);
				// 培训班
				List<ElClass> elc = new ArrayList<ElClass>();
				if (rs.getString(15) != null) {
					List<String> listC = new ArrayList<String>(Arrays.asList(rs
							.getString(15).split(",")));
					for (int i = 0; i < listC.size(); i++) {
						elc.add(new ElClass(Integer.parseInt(listC.get(i))));
					}
				}
				elR.setElclass(elc);
				elR.setClassScreeningWay(rs.getInt(16));
				elR.setEroomScreeningWay(rs.getInt(17));
				return elR;
			}
		} catch (Exception e) {
			logger.error("查询考场申请条件失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return elR;
	}

	/**
	 * 校验该课程内是否有userid学员
	 * 
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public boolean checkCourseIsUser(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean is = false;
		try {
			String sql = "select ca.userid from study_course ca where ca.courseid=? and ca.userid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				is = true;
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return is;
	}

	/**
	 * 获取参加了课程的人数
	 * 
	 * @param eroomid
	 * @return
	 * @throws ElException
	 */
	public int getJoinNumber(int eroomid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int number = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select count(ca.userid) from study_course ca where ca.courseid=?");
			ps.setInt(1, eroomid);
			rs = ps.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return number;
	}

	/**
	 * 查询出从depid开始的有权的部门ID
	 * 
	 * @author HeweiCheng
	 * @param depTree
	 * @param depid
	 * @return
	 */
	private String createDepartmentId(Department depTree, int depid) {
		// String id=depTree.getId()+"";
		if (depTree != null) {
			if (depTree.getId() != depid) {
				depTree = getDepartmentById(depTree.getChild(), depid);
			}
			if (depTree.getChild() != null) {
				return createDepartmentId(depTree.getChild(), depTree.getId());
			}
			return String.valueOf(depTree.getId());
		} else {
			return null;
		}
	}

	/**
	 * 构建有权限的部门ID
	 * 
	 * @author Heweicheng
	 * @param ctypeTree
	 * @return
	 */
	private String createDepartmentId(List<Department> listType, int id) {
		String ids = id + "";
		for (Department type : listType) {
			ids = ids + "," + createDepartmentId(type.getChild(), type.getId());
		}
		return ids;
	}

	/**
	 * 如果不是根节点开始 要找出开始节点
	 * 
	 * @author Heweicheng
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private Department getDepartmentById(List<Department> listType, int depid) {
		Department dep = null;
		for (Department type : listType) {
			if (type.getId() != depid) {
				dep = getDepartmentById(type.getChild(), depid);
				if (dep != null) {
					return dep;
				}
			} else {
				dep = type;
				return dep;
			}
		}
		return dep;
	}

	public List<Course> registeredCourse(int userid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> courselist = new ArrayList<Course>();
		try {
			String sql = "select * from (select t.*, rownum rn from ("
					+ "select c.id,c.name,c.creater,u.realname,c.description,c.status "
					+ "from study_course sc, course c,eluser u  where sc.courseid = c.id and u.id = c.creater and  c.isapplication = 1 and sc.userid = ?"
					+ ")t where rownum <= ? ) where rn>=?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course elc = new Course();
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				elc.setDescription(rs.getString(5));
				elc.setStatus(rs.getInt(6));
				elc.setIsjoin("true");
				courselist.add(elc);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courselist;
	}

	public int registeredCourseSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		try {
			String sql = "select count(c.id)"
					+ "from study_course sc, course c,eluser u  where sc.courseid = c.id and u.id = c.creater and  c.isapplication = 1 and sc.userid = ?"
					+ "";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("可申请培训班列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	/**
	 * 查看我的课列表
	 * 
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<Course> mytbcourses(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<Course> courselist = new ArrayList<Course>();
		try {
			sql = "select id,name,createtime,islink,teachername,roomstart,roomend from course  where islink = 4 and teacherid = ?";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course elc = new Course();
				elc.setId(rs.getInt(1));
				elc.setName(rs.getString(2));
				elc.setCreatetime(rs.getTimestamp(3));
				elc.setIslink(rs.getInt(4));
				elc.setTeacherName(rs.getString(5));
				elc.setRoomstart(rs.getTimestamp(6));
				elc.setRoomend(rs.getTimestamp(7));
				courselist.add(elc);
			}
		} catch (Exception e) {
			logger.error("可我的课列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courselist;
	}

	public int courseCopy(int id) throws ElException {
		// 获取课程的基本信息
		Course course = getCourseById(id);
		course.setName(course.getName() + "_副本");
		CoursePage cp = null;
		//添加课程
		addCourse(course);
		//获取所有的章节列表
		List<CoursePage> cList = getCourseAllCpage(id);
		for(int i = 0; i < cList.size(); i++){
			//把原来章节id记录
			int cpid = cList.get(i).getId();
			CoursePageDao cbd = (CoursePageDao)SpringContextUtil.getBean("coursePageDao");
			//根据章节id获取课程章节
			cp = cbd.getCp(cpid);
			//设置课程id
			cp.setCourse(course);
			//添加章节
			cbd.addCoursePage2(cp);
			//根据课程id和章节id查出练习列表
			List<PracticePaper> list = getPracticePaperByCid(id,cpid);
			for(int j = 0; j < list.size(); j++){
				//添加章节练习系信息
				PracticePaper p =list.get(j);
				p.setCourse(course);
				p.setCpage(cp);
				addPracticePaper2(p);
			}
		}
		List<PracticePaper> list = getPracticePaperByCid(id,0);
		for(int j = 0; j < list.size(); j++){
			PracticePaper p =list.get(j);
			p.setCourse(course);
			//添加课程练习
			addPracticePaper2(p);
		}
		return course.getId();
	}
	/**
	 * 根据id删除课程信息
	 * @param id
	 * @throws ElException
	 */
	public void deleteCourseByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from course where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据id删除课程信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 检测课程是否被用过
	 * @param courseid
	 * @throws ElException
	 */
	public boolean checkCourseIsUse(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select count(*) cc from class_course where courseid=?) t1," +
					" (select count(*) cd from course_dep where courseid=?) t2," +
					" (select count(*) cn from course_note where courseid=?) t3," +
					" (select count(*) sc from study_course where courseid=?) t4 " +
					" where t1.cc>0 or t2.cd>0 or t3.cn>0 or t4.sc>0");
			ps.setInt(1, courseid);
			ps.setInt(2, courseid);
			ps.setInt(3, courseid);
			ps.setInt(4, courseid);
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			logger.error("检测课程是否被用过出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	/**
	 * 根据课程和章节id检测该章节是否有练习
	 * @param courseid
	 * @param cpageid
	 * @throws ElException
	 */
	public boolean checkCpageIsPrac(int courseid,int cpageid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from practicepaper where courseid=? and cpid=?");
			ps.setInt(1, courseid);
			ps.setInt(2, cpageid);
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (Exception e) {
			logger.error("根据课程和章节id检测该章节是否有练习失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}
	
	/**
	 * 检测课程章节的完整性
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public String checkCoursePage(int courseid) throws ElException {
		StringBuffer message=new StringBuffer();
		//获取该课程的所有章节
		List<CoursePage> cpages=this.getCourseAllCpage(courseid);
		for (int i = 0; i < cpages.size(); i++) {
			//判断章节的结业方式，如果是考过或者学完且考过，那么就一定要有章节练习
			if(cpages.get(i).getGetcredit()==2){
				//查询该章节是否有练习
				if(!this.checkCpageIsPrac(courseid, cpages.get(i).getId())){
					message.append("<br />该课程的章节["+cpages.get(i).getTitle()+"]结业方式为考过，但章节没有练习!");
				}
			}else if(cpages.get(i).getGetcredit()==3){
				//查询该章节是否有练习
				if(!this.checkCpageIsPrac(courseid, cpages.get(i).getId())){
					message.append("<br />该课程的章节["+cpages.get(i).getTitle()+"]结业方式为学完且考过，但章节没有练习!");
				}
			}
		}
		return message.toString();
	}
	
	/**
	 * 根据类别查询数据(分页)
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatatCourse> getBaseCourseByTypeid(int typeid,int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatatCourse> baseList=new ArrayList<BaseDatatCourse>();
		try {
			ct = DBConnection.getConnection();
			if(typeid==-1){
				ps = ct.prepareStatement("select * from (select t.*,rownum rn from( select bd.id bdid,bd.typeid bdtypeid,bd.basevalue,bd.remack bdremack,bd.sortid,bt.id btid,bt.name from basedatat_course bd left join basedatatype_course bt on bd.typeid=bt.id where bd.status!=1 order by typeid,sortid )t where rownum <=? ) where rn >=?");
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
			}else{
				ps = ct.prepareStatement("select * from (select t.*,rownum rn from( select bd.id bdid,bd.typeid bdtypeid,bd.basevalue,bd.remack bdremack,bd.sortid,bt.id btid,bt.name from basedatat_course bd left join basedatatype_course bt on bd.typeid=bt.id where bd.typeid=? and bd.status!=1 order by sortid )t where rownum <=? ) where rn >=?");
				ps.setInt(1, typeid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
			}
			rs = ps.executeQuery();
			BaseDatatCourse bd=null;
			while (rs.next()){
				bd=new BaseDatatCourse();
				bd.setId(rs.getInt("bdid"));
				bd.setTypeid(rs.getInt("bdtypeid"));
				bd.setBaseCourseType(new BaseDataTypeCourse(rs.getInt("btid"),rs.getString("name")));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("bdremack"));
				bd.setSortid(rs.getInt("sortid"));
				baseList.add(bd);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baseList;
	}
	
	/**
	 * 根据类别查询数据数量
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public int getBaseCourseByTypeidCount(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if(typeid==-1){
				ps = ct.prepareStatement("select count(*) from basedatat_course where status!=1 ");
			}else{
				ps = ct.prepareStatement("select count(*) from basedatat_course where typeid=? and status!=1 ");
				ps.setInt(1, typeid);
			}
			rs = ps.executeQuery();
			if (rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 获取所有基础数据类别
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataTypeCourse> getAllBaseDataTypeCourse() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDataTypeCourse> baseTypeList=new ArrayList<BaseDataTypeCourse>(8);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name,remack from basedatatype_course order by id ");
			rs = ps.executeQuery();
			BaseDataTypeCourse bt=null;
			while (rs.next()){
				bt=new BaseDataTypeCourse();
				bt.setId(rs.getInt("id"));
				bt.setName(rs.getString("name"));
				bt.setRemack(rs.getString("remack"));
				baseTypeList.add(bt);
			}
		} catch (Exception e) {
			logger.error("获取所有基础数据类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baseTypeList;
	}
	
	/**
	 * 获取所有基础数据类别
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<BaseDataTypeCourse> getAllBaseDataTypeCourse(int pageNow,int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDataTypeCourse> baseTypeList=new ArrayList<BaseDataTypeCourse>(8);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*,rownum rn from( select id,name,remack from basedatatype_course order by id )t where rownum <=? ) where rn >=?");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			BaseDataTypeCourse bt=null;
			while (rs.next()){
				bt=new BaseDataTypeCourse();
				bt.setId(rs.getInt("id"));
				bt.setName(rs.getString("name"));
				bt.setRemack(rs.getString("remack"));
				baseTypeList.add(bt);
			}
		} catch (Exception e) {
			logger.error("获取所有基础数据类别出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baseTypeList;
	}
	/**
	 * 添加基础数据
	 * @param bd
	 * @throws ElException
	 */
	public void addBaseCourseDb(BaseDatatCourse bd) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select max(sortid) from basedatat_course where typeid=? and status!=1";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bd.getTypeid());
			rs = ps.executeQuery();
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			ps = ct.prepareStatement("insert into basedatat_course(typeid,basevalue,remack,sortid) values(?,?,?,?)");
			ps.setInt(1, bd.getTypeid());
			ps.setString(2, bd.getBasevalue());
			ps.setString(3, bd.getRemack());
			ps.setInt(4, maxSortid+1);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加基础数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/** 
	 * 根据id查询数据
	 * 
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public BaseDatatCourse getBaseDatatCourseById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BaseDatatCourse bd=null;
		try {
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement("select bt.id btid,bt.typeid,bt.basevalue,bt.remack,bdt.id bdtid,bdt.name bdtname from basedatat_course bt left join basedatatype_course bdt on bt.typeid=bdt.id where bt.id=? ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()){
				bd=new BaseDatatCourse();
				bd.setId(rs.getInt("btid"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setBaseCourseType(new BaseDataTypeCourse(rs.getInt("bdtid"),rs.getString("bdtname")));
			}
		} catch (Exception e) {
			logger.error("根据id串查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bd;
	}
	/**
	 * 编辑基础数据
	 * @param bd
	 * @throws ElException
	 */
	public void updateBaseDbCourse(BaseDatatCourse bd) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update basedatat_course set basevalue=?,remack=? where id=? ");
			ps.setString(1, bd.getBasevalue());
			ps.setString(2, bd.getRemack());
			ps.setInt(3, bd.getId());
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
	 * @param id
	 * @throws ElException
	 */
	public void delBaseDbCourse(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			int typeid=0;
			int sortid=0;
			ps = ct.prepareStatement("select typeid,sortid from basedatat_course where id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			while(rs.next()){
				typeid=rs.getInt("typeid");
				sortid=rs.getInt("sortid");
			}
			ps = ct.prepareStatement("update basedatat_course set status=1 where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			//更新所有比他大的sort，往上移动
			if(sortid>0){
				ps = ct.prepareStatement("update basedatat_course set sortid=sortid-1 where typeid=? and sortid>?");
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
	
	/**
	 * 根据类别查询数据
	 * @param typeid
	 * @return
	 * @throws ElException
	 */
	public List<BaseDatatCourse> getBaseDatatCourseByTypeid(int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<BaseDatatCourse> baseList=new ArrayList<BaseDatatCourse>();
		try {
			ct = DBConnection.getConnection();
			if(typeid==-1){
				ps = ct.prepareStatement("select * from basedatat_course where status!=1 order by typeid,sortid ");
			}else{
				ps = ct.prepareStatement("select * from basedatat_course where typeid=? and status!=1 order by sortid ");
				ps.setInt(1, typeid);
			}
			rs = ps.executeQuery();
			BaseDatatCourse bd=null;
			while (rs.next()){
				bd=new BaseDatatCourse();
				bd.setId(rs.getInt("id"));
				bd.setTypeid(rs.getInt("typeid"));
				bd.setBasevalue(rs.getString("basevalue"));
				bd.setRemack(rs.getString("remack"));
				bd.setSortid(rs.getInt("sortid"));
				baseList.add(bd);
			}
		} catch (Exception e) {
			logger.error("根据类别查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return baseList;
	}
	//课程库
	public List<Course> getCourseAll (CourseType ctypeTree,int ctid,Course course,String sqlWhere,int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		List<Course> CList = new ArrayList<Course>();
		String sql = "";
		try { 
			String ids = courseTypeById(ctypeTree,ctid);
			if(course != null){
				if(course.getName() != null && !course.getName().equals("填写课程名称....")){
					sqlWhere = sqlWhere + " and c.name like '%"+course.getName()+"%'";
				}
				if(course.getShihegangwei() != null && !course.getShihegangwei().equals("")){
					sqlWhere = sqlWhere + " and c.shihegangwei = "+course.getShihegangwei();
				}
				if(course.getZhuanyeleibie() != null && !course.getZhuanyeleibie().equals("")){
					sqlWhere = sqlWhere + " and c.zhuanyeleibie = "+course.getZhuanyeleibie();
				}
				if(course.getZhuanyejibie() != null && !course.getZhuanyejibie().equals("")){
					sqlWhere = sqlWhere + " and c.zhuanyejibie = "+course.getZhuanyejibie();
				}
				if(course.getShihebumen() != null && !course.getShihebumen().equals("")){
					sqlWhere = sqlWhere + " and c.shihebumen = "+course.getShihebumen();
				}
				if(course.getNeirongleixing() != null && !course.getNeirongleixing().equals("")){
					sqlWhere = sqlWhere + " and c.neirongleixing = "+course.getNeirongleixing();
				}
				if(course.getPeixunleibie() != null && !course.getPeixunleibie().equals("")){
					sqlWhere = sqlWhere + " and c.peixunleibie = "+course.getPeixunleibie();
				}
				if(course.getShihexuewei() != null && !course.getShihexuewei().equals("")){
					sqlWhere = sqlWhere + " and c.shihexuewei = "+course.getShihexuewei();
				}
				if(course.getKechengxingzhi() != null && !course.getKechengxingzhi().equals("")){
					sqlWhere = sqlWhere + " and c.kechengxingzhi = "+course.getKechengxingzhi();
				}
				if(course.getCourseCss() != -1){
					sqlWhere = sqlWhere + " and c.courseCss = "+course.getCourseCss();
				}
			}
			
			sql = "select * from (select t.*,rownum rn from( " +
					" select c.id,c.name cname,c.ctypeid,ct.name,c.creater,el.realname,c.description,c.mainimg,c.credit,c.status," +
					" c.createtime,c.exurl,c.during,c.querytime,c.teacherinfo,c.teachername,c.teacherid,c.shihegangwei ,c.zhuanyeleibie ,c.zhuanyejibie ,c.shihebumen ,c.neirongleixing ,c.peixunleibie ,c.shihexuewei ,c.kechengxingzhi ,c.courseCss  " +
					" from course c, course_type ct ,eluser el " +
					" where c.ctypeid = ct.id and c.creater = el.id "+sqlWhere+
					" and ct.id in ("+ids+") order by createtime desc )t where rownum <=? ) where rn >=?";
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement(sql); 			
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()){
				Course c = new Course();
				c.setId(rs.getInt("id")); 
				c.setName(rs.getString("cname")); 
				c.setCtype(new CourseType(rs.getInt("id"),rs.getString("name"))); 
				c.setCreater(new ELUser(rs.getInt("creater") , rs.getString("realname"))); 
				c.setDescription(rs.getString("description")); 
				c.setMainimg(rs.getString("mainimg")); 
				c.setCredit(rs.getInt("credit")); 
				c.setStatus(rs.getInt("status")); 
				c.setCreatetime(rs.getTimestamp("createtime")); 
				c.setExurl(rs.getString("exurl")); 
				c.setDuring(rs.getInt("during")); 
				c.setQuerytime(rs.getInt("querytime")); 
				c.setTeacherinfo(rs.getString("teacherinfo")); 
				c.setTeacherName(rs.getString("teachername")); 
				c.setTeacherId(rs.getInt("teacherid"));  
				c.setShihegangwei(rs.getString("shihegangwei"));//适合岗位
				c.setZhuanyeleibie(rs.getString("zhuanyeleibie"));//专业类别
				c.setZhuanyejibie(rs.getString("zhuanyejibie"));//专业级别
				c.setShihebumen(rs.getString("shihebumen"));//适合部门
				c.setNeirongleixing(rs.getString("neirongleixing"));//内容类型
				c.setPeixunleibie(rs.getString("peixunleibie"));//培训类别
				c.setShihexuewei(rs.getString("shihexuewei"));//适合学位
				c.setKechengxingzhi(rs.getString("kechengxingzhi"));//课程性质  
				c.setCourseCss(rs.getInt("courseCss"));
				CList.add(c);
			}
		} catch (Exception e) {
			logger.error("根据id串查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return CList;
	}
	
	public int getCourseAllSize (CourseType ctypeTree,int ctid,Course course,String sqlWhere) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;  
		String sql = "";
		int size = 0;
		try {
			String ids = courseTypeById(ctypeTree,ctid);
			if(course != null){
				if(course.getName() != null && !course.getName().equals("填写课程名称....")){
					sqlWhere = sqlWhere + " and c.name like '%"+course.getName()+"%'";
				}
				if(course.getShihegangwei() != null && !course.getShihegangwei().equals("")){
					sqlWhere = sqlWhere + " and c.shihegangwei = "+course.getShihegangwei();
				}
				if(course.getZhuanyeleibie() != null && !course.getZhuanyeleibie().equals("")){
					sqlWhere = sqlWhere + " and c.zhuanyeleibie = "+course.getZhuanyeleibie();
				}
				if(course.getZhuanyejibie() != null && !course.getZhuanyejibie().equals("")){
					sqlWhere = sqlWhere + " and c.zhuanyejibie = "+course.getZhuanyejibie();
				}
				if(course.getShihebumen() != null && !course.getShihebumen().equals("")){
					sqlWhere = sqlWhere + " and c.shihebumen = "+course.getShihebumen();
				}
				if(course.getNeirongleixing() != null && !course.getNeirongleixing().equals("")){
					sqlWhere = sqlWhere + " and c.neirongleixing = "+course.getNeirongleixing();
				}
				if(course.getPeixunleibie() != null && !course.getPeixunleibie().equals("")){
					sqlWhere = sqlWhere + " and c.peixunleibie = "+course.getPeixunleibie();
				}
				if(course.getShihexuewei() != null && !course.getShihexuewei().equals("")){
					sqlWhere = sqlWhere + " and c.shihexuewei = "+course.getShihexuewei();
				}
				if(course.getKechengxingzhi() != null && !course.getKechengxingzhi().equals("")){
					sqlWhere = sqlWhere + " and c.kechengxingzhi = "+course.getKechengxingzhi();
				}
				if(course.getCourseCss() != -1){
					sqlWhere = sqlWhere + " and c.courseCss = "+course.getCourseCss();
				}
			}
			sql = "select count(c.id) from course c, course_type ct ,eluser el " +
					"where c.ctypeid = ct.id and c.creater = el.id and ct.id in ("+ids+") "+sqlWhere;
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement(sql); 
			rs = ps.executeQuery();
			if (rs.next()){ 
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("根据id串查询数据出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	
	public List<ExamRoom> getRoom(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;  
		List<ExamRoom> examRooms = new ArrayList<ExamRoom>();
		try {
			ExamRoom er = new ExamRoom();
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from exam_room where courseid=?");
			ps.setInt(1, courseid);
			rs = ps.executeQuery();
			if(rs.next()){
				er.setId(rs.getInt("id"));
				examRooms.add(er);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return examRooms;
	}
	
	/**
	 * 基础数据排序
	 * @param typeid
	 * @param sortid
	 * @param upordown
	 * @throws ElException
	 */
	public void sortBaseDbsCourse(int typeid, int sortid, int upordown) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			if (upordown == ElConstants.SORT_UP) {//=1 向上移
				upSort(ct, typeid, sortid);
			} else {
				downSort(ct, typeid, sortid);
			}
		} catch (Exception e) {
			logger.error("移动网页失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	private void upSort(Connection ct, int typeid, int sortid) throws ElException {
		try {
			Statement st = ct.createStatement();
			if (sortid > 0) {
				String sql = "select id from basedatat_course where typeid = "
						+ typeid + " and sortid = " + (sortid - 1) + " and status!=1 ";
				ResultSet rs = st.executeQuery(sql);//先得出该对象上1对象
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update basedatat_course set sortid=sortid-1 "
							+ " where typeid = " + typeid + " and sortid="
							+ sortid + " and status!=1 ";
					st.executeUpdate(sql);//上移该对象
					sql = "update basedatat_course set sortid=sortid+1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);//下移该对象的上一对象
				}
			}
			st.close();
		
		} catch (Exception e) {
			logger.error("网页上移失败！", e);
			throw new ElException("网页上移失败", e);
		}
	}
	private void downSort(Connection ct, int typeid, int sortid) throws ElException {
		try {
			Statement st = ct.createStatement();
			String sql = "select max(sortid) from basedatat_course where typeid= "
					+ typeid;
			ResultSet rs = st.executeQuery(sql);
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select id from basedatat_course where typeid = " + typeid
						+ " and sortid = " + (sortid + 1) + " and status!=1 ";
				rs = st.executeQuery(sql);//先得到该对象的下一对象
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update basedatat_course set sortid=sortid+1 "
							+ " where typeid = " + typeid + " and sortid="
							+ sortid + " and status!=1 ";
					st.executeUpdate(sql);//下移该对象
					sql = "update basedatat_course set sortid=sortid-1 "
							+ " where id = " + nextId;
					st.executeUpdate(sql);//上移该对象的上一对象
				}
			}
			st.close();
		} catch (Exception e) {
			logger.error("网页下移失败！", e);
			throw new ElException("网页下移失败", e);
		}
	}

	public void updateCourseWeiduById(Course course) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update course set weidu=? where id=?");
			ps.setString(1, course.getWeidu());
			ps.setInt(2, course.getId());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据id修改课程维度失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	public int getUserSCInfo(String userid,String courseid,String status)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from sc_userscoinfo where userid=? and courseid=? and LESSONSTATUS like ?");
			ps.setString(1, userid);
			ps.setString(2, courseid);
			ps.setString(3, status);
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

	public int getSCItemInfo(String courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int all = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from sc_iteminfo where courseid=?");
			ps.setString(1, courseid);
			rs = ps.executeQuery();
			if(rs.next()){
				all = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return all;
	}

	public int getSCPasstime(int courseid, int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct  =null;
		int passtime = 0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select sum(passtime) from study_course_record where courseid=? and classid=? and status=0");
			ps.setInt(1, courseid);
			ps.setInt(2, classid);
			rs = ps.executeQuery();
			if(rs.next()){
				passtime = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return passtime;
	}

	public boolean checkCoursesIsAllPass(int roomid, int classid ,int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call checkCoursesIsAllPass(?,?,?,?)}");  
			cs.setInt(1, roomid);
			cs.setInt(2, classid);
			cs.setInt(3, userid);
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getInt(4));
			flag = cs.getBoolean(4);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public ExamRoom getEroomByCP(int courseid, int cpid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ExamRoom room = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,title,isband from exam_room where courseid=? and cpid=?");
			ps.setInt(1, courseid);
			ps.setInt(2, cpid);
			rs = ps.executeQuery();
			if(rs.next()){
				room = new ExamRoom(rs.getInt(1),rs.getString(2));
				room.setIsBand(rs.getInt(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return room;
	}
	
	public List<ExamRoom> getEroomListByCP(int courseid,int cpid) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ExamRoom> rooms = new ArrayList<ExamRoom>();
		ExamRoom room = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,title,isband,sortid from exam_room where courseid=? and cpid=? order by sortid asc");
			ps.setInt(1, courseid);
			ps.setInt(2, cpid);
			rs = ps.executeQuery();
			while(rs.next()){
				room = new ExamRoom(rs.getInt(1),rs.getString(2));
				room.setIsBand(rs.getInt(3));
				room.setSortid(rs.getInt(4));
				rooms.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return rooms;
	}

	public void setBand(int roomid,int courseid, int cpid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update exam_room set isband=? where id=? and courseid=? and cpid=?");
			ps.setInt(1, 1);
			ps.setInt(2, roomid);
			ps.setInt(3, courseid);
			ps.setInt(4, cpid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<MyCourse> listMyCoursees_wjm(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<MyCourse> mycourses = new ArrayList<MyCourse>();
		MyCourse mycourse = null;
		Course c = null;
		MyRoom r = null;
		ExamRoom er = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select c.id cid,c.name cname,round(sc.process,2) scprocess,sr.myscore srmyscore,er.id erid,er.title ertitle,sc.passed scpassed,c.description , c.course_detail "
				+" from study_course sc   " +
						" left join course c on sc.courseid=c.id " +
						" left join exam_room er on er.courseid=sc.courseid and er.classid=? " +
						" left join study_room sr on  sc.userid=sr.userid and sr.roomid=er.id " +
						" left join class_course cc on cc.courseid=c.id and cc.classid=? " +
						" where sc.classid=? and sc.userid=? " +
						" order by cc.orderid asc";
			ps = ct.prepareStatement(sql);
			logger.info(sql);
			ps.setInt(1, classid);
			ps.setInt(2, classid);
			ps.setInt(3, classid);
			ps.setInt(4, userid);
			rs = ps.executeQuery();
			while(rs.next()){
				mycourse = new MyCourse();
				c = new Course(rs.getInt(1),rs.getString(2));
				c.setDescription(rs.getString(8));
				c.setCourseDetail(new OracleBlob().getContent(rs.getBlob(9)));
				mycourse.setCourse(c);
				mycourse.setProcess(rs.getFloat(3));
				mycourse.setPassed(rs.getBoolean(7));
//				r = new MyRoom();
//				r.setMyScore(rs.getFloat(4));
				er = new ExamRoom(rs.getInt(5),rs.getString(6));
//				r.setExamroom(er);
//				mycourse.setMyRoom(r);
				mycourse.setExamRoom(er);
				mycourses.add(mycourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return mycourses;
	}

	public boolean checkCpagesIsAllPass(int classid, int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call checkpageisallpass(?,?,?,?)}");  
			cs.setInt(1, courseid);
			cs.setInt(2, classid);
			cs.setInt(3, userid);
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getInt(4));
			flag = cs.getBoolean(4);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public boolean checkCourseIsPass(int classid, int userid, int courseid)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		boolean flag = false;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select passed from study_course where classid=? and userid=? and courseid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setInt(3, courseid);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) == 1)
					flag = true;
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}

	public int getNowCourseid(int classid, int userid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int courseid = 0;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call getNowCourseid(?,?,?)}");  
			cs.setInt(1, classid);
			cs.setInt(2, userid);
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			System.out.println(cs.getInt(3));
			courseid = cs.getInt(3);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courseid;
	}

	public List<Course> getCoursesByClassid(int classid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> courses = new ArrayList<Course>();
		Course c = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select c.id,c.name from class_course cc" +
					"	left join course c on cc.courseid=c.id " +
					"	where cc.classid=? ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs = ps.executeQuery();
			while(rs.next()){
				c = new Course(rs.getInt(1),rs.getString(2));
				courses.add(c);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}
	
	public List<CoursePage> getPagesByCourseid(int course) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<CoursePage> coursePages = new ArrayList<CoursePage>();
		CoursePage cp = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = " select cp.id,cp.title,cp.page,cp.islive,cp.isfree,cp.roomid,cp.courseid,rownum rn from course_page cp" +
					" 	where cp.courseid=? order by rn";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, course);
			rs = ps.executeQuery();
			while(rs.next()){
				cp = new CoursePage(rs.getInt(1),rs.getString(2));
				cp.setPage(rs.getString(3));
				cp.setIslive(rs.getInt(4));
				cp.setIsfree(rs.getInt(5));
				cp.setRoom(new Rooms(rs.getInt(6)));
				cp.setCourse(new Course(rs.getInt(7)));
				cp.setRn(rs.getInt(8));
				coursePages.add(cp);
			}
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return coursePages;
	}

	public List<StuffLib> getCpageStuffsByCoursid(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<StuffLib> stuffs = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select cp.id cpid,cp.courseid,cp.title cptitle,ks.title kstitle,ks.stuffaddr from course_page cp left join knowledge_stuff ks on cp.id=ks.cpageid where cp.courseid=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				StuffLib sf = new StuffLib();
				sf.setTitle(rs.getString(4));
				sf.setDescription(rs.getString(5));
				stuffs.add(sf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuffs;
	}

	public int getPrecCourseid(int classid, int userid,int nowCourseid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		int courseid = 0;
		try {
			ct = DBConnection.getConnection();
			CallableStatement cs = ct.prepareCall("{call getprecCourseid(?,?,?,?)}");  
			cs.setInt(1, classid);
			cs.setInt(2, userid);
			cs.setInt(3, nowCourseid);
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);  
			cs.execute(); 
			courseid = cs.getInt(4);
		} catch (Exception e) {
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courseid;
	}

	public void updateCourseProcessByClassid(int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update study_course set passed=1,process=100,tprocess=100.00,initcompliance=1 where classid=? and userid=?");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("定级结束后更新定的等级之前的培训班进度为100失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public boolean getCourseInitCompliance(int courseid, int classid, int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(1) from study_course where initcompliance=1 and classid=? and userid=? and courseid=?");
			ps.setInt(1, classid);
			ps.setInt(2, userid);
			ps.setInt(3, courseid);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1) == 1)
					flag = true;
			}
		} catch (Exception e) {
			logger.error("判断课程是否初始化进度100失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	/**
	 * 我添加的课程
	 */
	public List<Course> myListAllCourse(CourseType ctypeTree,int depid,int role, String name, int ctid,
			int pageNow, int pageSize,String status,int userid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
//			Department dep = new Department();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//			ps.setInt(1, depid);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				dep.setId(rs.getInt(1));
//				dep.setLid(rs.getInt(2));
//				dep.setRid(rs.getInt(3));
//			}
//			rs.close();

//			String x = Integer.toString(ctid);
			// String ids = courseTypeById(ctypeTree,ctid);
//			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
//											// ,当角色不为1时ids的只有一个根节点时也不截取
//				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
//						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							"select * from (select t.*, rownum rn from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description,c.during from course c, ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") and c.name like ?  and c.creater=?  ")//
					// .append(" and ct.id in ("+ids+") order by c.createtime
					// desc )t ")
					.append(" order by c.createtime desc )t ").append(
							" where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			System.out.println(buffer.toString());
			ps.setString(1, "%"+name+"%");
			ps.setInt(2, userid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(8)));
				c.setStatus(rs.getInt(4));
				c.setCreatetime(rs.getTimestamp(5));
				c.setModifytime(rs.getTimestamp(6));
				c.setCreater(new ELUser(rs.getInt(7), rs.getString(9)));
				c.setCredit(rs.getInt(10));
				c.setHot(rs.getInt(11));
				c.setRoomstart(rs.getTimestamp(12));
				c.setRoomend(rs.getTimestamp(13));
				c.setTeacherName(rs.getString(14));
				c.setIslink(rs.getInt(15));
				c.setMainimg(rs.getString(16));
				c.setDescription(rs.getString(17));
				c.setDuring(rs.getInt(18));
				c.setCpagesize(this.getCpsize(c.getId()));
				css.add(c);
			}

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return css;
	}
	public int myListAllCourseSize(CourseType ctypeTree,int depid , int role, String name, int ctid,String status,int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		if (name == null)
			name = "";
		else
			name = name.trim();
		try {
			ct = DBConnection.getConnection();
			Department dep = new Department();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt(1));
				dep.setLid(rs.getInt(2));
				dep.setRid(rs.getInt(3));
			}
			rs.close();

//			String x = Integer.toString(ctid);
//			String ids = courseTypeById(ctypeTree, ctid);
//			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
//											// ,当角色不为1时ids的只有一个根节点时也不截取
//				ids = ctid == 1 ? ids.substring(x.length() + 1, ids.length())
//						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select count(*) from (select c.id,")
					.append(
							" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
					.append(
							" ct.name ctname,u.realname,c.credit,c.hot  from course c, ("
									+ ((ElNodeSQL) SpringContextUtil
											.getBean("elnodesql"))
											.generateSQLByTree("course_type",
													ctypeTree, true) + ")  ct,")
					.append(
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and c.status in (" + status
									+ ") and c.name like ? and  c.creater=? ")//
					.append(
							" order by c.createtime desc )t ");

			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%"+name+"%");
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (Exception e) {
			logger.error("从本部门上下级的到可分配课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Course> getTjCourses(int ctypeid,int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> css = new ArrayList<Course>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.*,rownum rn from (select * from course where ctypeid=? and hot=? order by createtime desc)a where rownum<=8";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, ctypeid);
			ps.setInt(2, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3)));
				css.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return css;
	}
}

