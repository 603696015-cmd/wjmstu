package com.sopia.statman.dao.impl;

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
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.StringUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CoursePage;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.courseman.entities.PracticePaper;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.statman.StatisticConstants;
import com.sopia.statman.dao.StatisticCourseDao;
import com.sopia.studyman.entities.MyCPage;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;

public class StatisticCourseDaoImpl implements StatisticCourseDao {
	private static final Log logger = LogFactory
			.getLog(StatisticCourseDaoImpl.class);

	public List<Course> listCourseByDepid(int depid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> courses = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_DEP_COURSE_LIST));
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				// c.setPassgrade(rs.getFloat(6));
				// c.setEuStatus(rs.getInt(7));
				c.setCreatetime(rs.getTimestamp(6));
				courses.add(c);
			}
		} catch (Exception e) {
			logger.error("获取部门课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return courses;
	}

	public List<Course> listCourseBYCtype(int ctid, String name)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			name = (null == name) ? "" : name.trim();
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select lid,rid from course_type where id = ?");
			ps.setInt(1, ctid);
			int lid = 0, rid = 0;
			rs = ps.executeQuery();

			if (rs.next()) {
				lid = rs.getInt(1);
				rid = rs.getInt(2);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_COURSE_LIST_BYTID));
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, lid);
			ps.setInt(3, rid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getTimestamp(4));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(5)));
				c.setUserCount(rs.getInt(6));
				cs.add(c);
			}
		} catch (Exception e) {
			logger.error("查看类别课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}

	/**
	 * 课程统计分页
	 * 
	 * @author jiahaijiang
	 * @param ctid
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseBYCtypePage(CourseType ctypeTree, int ctid,
			String name, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			name = (null == name) ? "" : name.trim();
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select lid,rid from course_type where id =
			// ?");
			// ps.setInt(1, ctid);
			// int lid = 0, rid = 0;
			// rs = ps.executeQuery();

			// if (rs.next()) {
			// lid = rs.getInt(1);
			// rid = rs.getInt(2);
			// }
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select * from (select t.*, rownum rn from (select c.id, c.name cname,c.ctypeid,c.createtime,")
					.append(
							" ct.name ctname,count(sc.userid) sccount from COURSE c left ")
					.append(
							" join COURSE_TYPE ct on c.ctypeid = ct.id left join study_course sc ")
					.append(
							" on sc.courseid = c.id where c.name like ? and  ct.id in ("
									+ createPerTypeId(ctypeTree, ctid) + ") ")
					.append(
							" group by  c.id, c.name,c.ctypeid,c.createtime,ct.name )t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getTimestamp(4));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(5)));
				c.setUserCount(rs.getInt(6));
				cs.add(c);
			}
		} catch (Exception e) {
			logger.error("查看类别课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}

//	public List<Course> listCourseBYCtypePage(CourseType ctypeTree,
//			int[] ctids, String name, int pageNow, int pageSize)
//			throws ElException {
//		//ctids=new int[]{1};
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		List<Course> cs = new ArrayList<Course>();
//		try {
//			name = (null == name) ? "" : name.trim();
//			ct = DBConnection.getConnection();
//			// ps = ct
//			// .prepareStatement("select lid,rid from course_type where id =
//			// ?");
//			// ps.setInt(1, ctid);
//			// int lid = 0, rid = 0;
//			// rs = ps.executeQuery();
//
//			// if (rs.next()) {
//			// lid = rs.getInt(1);
//			// rid = rs.getInt(2);
//			// }
//			if (ctids != null) {
//				for (int i = 0; i < ctids.length; i++) {
//					StringBuffer buffer = new StringBuffer();
//					buffer
//							.append(
//									" select * from (select t.*, rownum rn from (select c.id, c.name cname,c.ctypeid,c.createtime,")
//							.append(
//									" ct.name ctname,count(sc.userid) sccount from COURSE c left ")
//							.append(
//									" join COURSE_TYPE ct on c.ctypeid = ct.id left join study_course sc ")
//							.append(
//									" on sc.courseid = c.id where c.name like ? and  ct.id in ("
//											+ createPerTypeId(ctypeTree, 
//													ctids[i]) + ") ")
//							.append(
//									" group by  c.id, c.name,c.ctypeid,c.createtime,ct.name )t where rownum <= ? ) where rn>=?");
//					ps = ct.prepareStatement(buffer.toString());
//					ps.setString(1, "%" + name + "%");
//					ps.setInt(2, pageNow);
//					ps.setInt(3, pageSize);
//					rs = ps.executeQuery();
//					while (rs.next()) {
//						Course c = new Course(rs.getInt(1), rs.getString(2));
//						c.setCreatetime(rs.getTimestamp(4));
//						c
//								.setCtype(new CourseType(rs.getInt(3), rs
//										.getString(5)));
//						c.setUserCount(rs.getInt(6));
//						cs.add(c);
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("查看类别课程出错！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return cs;
//	}
	
	/**
	 * 课程统计列表查询（未分页，用于导出）
	 * @param tree
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseBYCtypePage(ElNode tree, String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			name = (null == name) ? "" : name.trim();
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer(" select c.id, c.name cname,c.ctypeid,c.createtime, ct.name ctname,count(sc.userid) sccount " +
					" from COURSE c inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("COURSE_TYPE", tree, true)+") ct on c.ctypeid = ct.id " +
					" left join study_course sc  on sc.courseid = c.id where c.name like ? group by  c.id, c.name,c.ctypeid,c.createtime,ct.name");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%" + StringUtil.toLikeStr(name) + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getTimestamp(4));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(5)));
				c.setUserCount(rs.getInt(6));
				cs.add(c);
			}
		} catch (Exception e) {
			logger.error("课程统计列表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}
	

	/**
	 * 课程统计列表查询
	 * @param tree
	 * @param name
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Course> listCourseBYCtypePage(ElNode tree, String name, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			name = (null == name) ? "" : name.trim();
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer("select * from (select t.*, rownum rn from ("+
					" select c.id, c.name cname,c.ctypeid,c.createtime, ct.name ctname,count(sc.userid) sccount " +
					" from COURSE c inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("COURSE_TYPE", tree, true)+") ct on c.ctypeid = ct.id " +
					" left join study_course sc  on sc.courseid = c.id where c.name like ? group by  c.id, c.name,c.ctypeid,c.createtime,ct.name"+ 
					" )t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%" + StringUtil.toLikeStr(name) + "%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreatetime(rs.getTimestamp(4));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(5)));
				c.setUserCount(rs.getInt(6));
				cs.add(c);
			}
		} catch (Exception e) {
			logger.error("课程统计列表查询出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
	}
	
	/**
	 * 课程统计列表查询数量
	 * @param tree
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public int listCourseBYCtypePageSize(ElNode tree, String name) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = (null == name) ? "" : name.trim();
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer(" select count(c.id) " +
					" from COURSE c inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("COURSE_TYPE", tree, true)+") ct on c.ctypeid = ct.id " +
					" where c.name like ? ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%" + StringUtil.toLikeStr(name) + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("课程统计列表查询数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	public List<Course> listCourseBYCtypePage(CourseType ctypeTree,
			int[] ctids, String name)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> cs = new ArrayList<Course>();
		try {
			name = (null == name) ? "" : name.trim();
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select lid,rid from course_type where id =
			// ?");
			// ps.setInt(1, ctid);
			// int lid = 0, rid = 0;
			// rs = ps.executeQuery();

			// if (rs.next()) {
			// lid = rs.getInt(1);
			// rid = rs.getInt(2);
			// }
			if (ctids != null) {
				for (int i = 0; i < ctids.length; i++) {
					StringBuffer buffer = new StringBuffer();
					buffer
							.append(
									" select c.id, c.name cname,c.ctypeid,c.createtime,")
							.append(
									" ct.name ctname,count(sc.userid) sccount from COURSE c left ")
							.append(
									" join COURSE_TYPE ct on c.ctypeid = ct.id left join study_course sc ")
							.append(
									" on sc.courseid = c.id where c.name like ? and  ct.id in ("
											+ createPerTypeId(ctypeTree,
													ctids[i]) + ") ")
							.append(
									" group by  c.id, c.name,c.ctypeid,c.createtime,ct.name ");
					ps = ct.prepareStatement(buffer.toString());
					ps.setString(1, "%" + name + "%"); 
					rs = ps.executeQuery();
					while (rs.next()) {
						Course c = new Course(rs.getInt(1), rs.getString(2));
						c.setCreatetime(rs.getTimestamp(4));
						c
								.setCtype(new CourseType(rs.getInt(3), rs
										.getString(5)));
						c.setUserCount(rs.getInt(6));
						cs.add(c);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查看类别课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cs;
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
		if (ctypeTree.getId() != ctid) {
			ctypeTree = getCourseTypeById(ctypeTree.getChild(), ctid);
		}
		if (ctypeTree == null) {
			return "0";
		}
		if (ctypeTree.getChild() != null) {
			return createTypeId(ctypeTree.getChild(), ctypeTree.getId());
		}
		return String.valueOf(ctypeTree.getId());
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
		CourseType ctypeTree = null;
		for (CourseType type : listType) {
			if (type.getId() != ctid) {
				ctypeTree = getCourseTypeById(type.getChild(), ctid);
				if (ctypeTree != null) {
					return ctypeTree;
				}
			} else {
				ctypeTree = type;
				return ctypeTree;
			}
		}
		return ctypeTree;
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
	 * 课程统计分页
	 * 
	 * @author jiahaijiang
	 * @param ctid
	 * @param name
	 * @return
	 * @throws ElException
	 */
	public int listCourseBYCtypePageCount(CourseType ctypeTree, int ctid,
			String name, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = (null == name) ? "" : name.trim();
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select lid,rid from course_type where id =
			// ?");
			// ps.setInt(1, ctid);
			// int lid = 0, rid = 0;
			// rs = ps.executeQuery();

			// if (rs.next()) {
			// lid = rs.getInt(1);
			// rid = rs.getInt(2);
			// }
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" SELECT count(*) FROM (select c.id, c.name cname,c.ctypeid,c.createtime,")
					.append(
							"   ct.name ctname,count(sc.userid) sccount from COURSE c left ")
					.append(
							" join COURSE_TYPE ct on c.ctypeid = ct.id left join study_course sc ")
					.append(
							" on sc.courseid = c.id where c.name like ? and  ct.id in ("
									+ createPerTypeId(ctypeTree, ctid)
									+ ") group by  c.id, c.name,c.ctypeid,c.createtime,ct.name )t ");
			ps = ct.prepareStatement(buffer.toString());
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("查看类别课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// return 0;
	}

	public int listCourseBYCtypePageCount(CourseType ctypeTree, int[] ctids,
			String name, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			name = (null == name) ? "" : name.trim();
			ct = DBConnection.getConnection();
			// ps = ct
			// .prepareStatement("select lid,rid from course_type where id =
			// ?");
			// ps.setInt(1, ctid);
			// int lid = 0, rid = 0;
			// rs = ps.executeQuery();

			// if (rs.next()) {
			// lid = rs.getInt(1);
			// rid = rs.getInt(2);
			// }
			int counts = 0;
			if (ctids != null) {
				for (int i = 0; i < ctids.length; i++) {
					StringBuffer buffer = new StringBuffer();
					buffer
							.append(
									" SELECT count(*) FROM (select c.id, c.name cname,c.ctypeid,c.createtime,")
							.append(
									"   ct.name ctname,count(sc.userid) sccount from COURSE c left ")
							.append(
									" join COURSE_TYPE ct on c.ctypeid = ct.id left join study_course sc ")
							.append(
									" on sc.courseid = c.id where c.name like ? and  ct.id in ("
											+ createPerTypeId(ctypeTree,
													ctids[i])
											+ ") group by  c.id, c.name,c.ctypeid,c.createtime,ct.name )t ");
					ps = ct.prepareStatement(buffer.toString());
					ps.setString(1, "%" + name + "%");
					rs = ps.executeQuery();
					rs.next();
					counts += rs.getInt(1);
				}
			}
			return counts;
		} catch (Exception e) {
			logger.error("查看类别课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		// return 0;
	}

	// public int getUserCountByCid(int course) throws ElException {
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// Connection ct = null;
	// try {
	// ct = DBConnection.getConnection();
	// ps = ct
	// .prepareStatement("select count(*) from COURSE_APPLY where courseid =
	// ?");
	// ps.setInt(1, course);
	// rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// } catch (Exception e) {
	// logger.error("查看类别课程学员数出错！", e);
	// throw new ElException(e);
	// } finally {
	// DBConnection.closeConnectInfo(ct, ps, rs);
	// }
	// return 0;
	// }

	public List<MyCourse> course_user_list(int cid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_COURSE_USER_LIST));
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setInt(1, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(3));
				eu.setUsername(rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));

				Course c = new Course();
				c.setCredit(rs.getInt(6));
				c.setDuring(rs.getInt(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				mc.setMyCredit(rs.getFloat(11));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
				mc.getMyExamPaper().setMyScore(rs.getInt(13));
				mc.getMyExamPaper().setIspassed(rs.getInt(14));
				mc.setUser(eu);
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<MyCourse> course_user_list_BYCtypePage(CourseType ctypeTree,
			int ctid, String name, int cid, int pageNow, int pageSize)
			throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select * from (select t.*, rownum rn from (select eu.id,eu.username,eu.realname,eu.depid,dep.name ")
					.append(
							" depname,c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_,sqi.myScore,sqi.ispassed,ec.name as className from ")
					.append(
							" COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join")
					.append(
							" eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi")
					.append(
							" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid  where eu.realname like ? and sc.courseid =?)t where rownum <= ? ) where rn>=?");
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")

			ps = ct.prepareStatement(buffer.toString());
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, cid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(3));
				eu.setUsername(rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));

				Course c = new Course();
				c.setCredit(rs.getInt(6));
				c.setDuring(rs.getInt(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				mc.setMyCredit(rs.getFloat(11));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
				mc.getMyExamPaper().setMyScore(rs.getFloat(13));
				mc.getMyExamPaper().setIspassed(rs.getInt(14));
				mc.setClassName(rs.getString("className")); 
				mc.setUser(eu);
				
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}
	/**
	 * 获取课程中所有学员
	 * @param ctypeTree
	 * @param ctid
	 * @param name
	 * @param cid
	 * @param classid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> course_user_list_BYCtypePage(CourseType ctypeTree,
			int ctid, String name, int cid,int classid, int pageNow, int pageSize,String roomTitle)
			throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select * from (select t.*, rownum rn from (select eu.id,eu.username,eu.realname,eu.depid,dep.name ")
					.append(
							" depname,c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_,sqi.myScore,sqi.ispassed,ec.id as classId,ec.name as className from ")
					.append(
							" COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join")
					.append(
							" eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi")
					.append(
							" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid  where eu.realname like ? and sc.courseid =? and sc.classid like ?)t where rownum <= ? ) where rn>=?");
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")

			ps = ct.prepareStatement(buffer.toString());
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, cid);
			if(classid==-1){
				ps.setString(3, "%" + "%");
			}else{
				ps.setString(3, "" + classid + "");
			}
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(3));
				eu.setUsername(rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));

				Course c = new Course();
				c.setCredit(rs.getInt(6));
				c.setDuring(rs.getInt(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				mc.setMyCredit(rs.getFloat(11));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
				mc.getMyExamPaper().setMyScore(rs.getFloat(13));
				mc.getMyExamPaper().setIspassed(rs.getInt(14));
				mc.setClassId(rs.getInt("classId"));
				mc.setClassName(rs.getString("className"));
				mc.setUser(eu);
				mc.setMyExamPaperList(this.getExamPaperList(cid, mc.getClassId(), eu.getId(),roomTitle));//获取该课程该学员该班级的绑定考场的考试集合（因为1个考场可能有多张试卷需要考试）
				if(mc.getClassId()>0&&mc.getMyExamPaperList().size()==0){
					//没搜到考场，跳过
					continue;
				}
				if(mc.getMyExamPaperList()!=null&&mc.getMyExamPaperList().size()>0){
					mc.getMyExamPaper().setExamRoom(mc.getMyExamPaperList().get(0).getExamRoom());
				}
				if(mc.getClassId()==0){
					//特殊处理
					//获取考场集合
					List<ExamRoom> erlist=this.getExamRoom(cid, eu.getId(),roomTitle);
					for (int i = 0; i < erlist.size(); i++) {
						ExamRoom er=erlist.get(i);
						//if(er.getId()==mc.getMyExamPaper().getExamRoom().getId()){
							//跳过
							//continue;
						//}
						//查出该考场该学员所对应的所有试卷
						MyCourse mc2 = new MyCourse();
						//mc2=mc;
						mc2.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
						mc2.setCourse(c);
						mc2.setPasstime(rs.getInt(8));
						mc2.setProcess(rs.getInt(9));
						mc2.setUser(eu);
						mc2.setMyExamPaperList(this.getExamPaperList(er.getId(), eu.getId()));//获取该课程该学员该班级的绑定考场的考试集合（因为1个考场可能有多张试卷需要考试）
						if(mc2.getMyExamPaperList()!=null&&mc2.getMyExamPaperList().size()>0){
							mc2.getMyExamPaper().setExamRoom(mc2.getMyExamPaperList().get(0).getExamRoom());
						}
						myBxc.add(mc2);
					}
					continue;
				}
				myBxc.add(mc); 
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}
	
	
	/**
	 * 获取课程中所有学员(未分页)
	 * @param ctypeTree
	 * @param ctid
	 * @param name
	 * @param cid
	 * @param classid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> course_user_list_BYCtypePageCount(CourseType ctypeTree,
			int ctid, String name, int cid,int classid,String roomTitle)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select eu.id,eu.username,eu.realname,eu.depid,dep.name ")
					.append(
							" depname,c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_,sqi.myScore,sqi.ispassed,ec.id as classId,ec.name as className,sc.passtime_2/60 passtime2, er.title, er.id as examroomid,sc.jieyeid from")
					.append(
							" COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join")
					.append(
							" eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi")
					.append(
							" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid left join exam_room er on er.courseid=c.id where eu.realname like ? and sc.courseid =? and sc.classid like ?");
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")

			ps = ct.prepareStatement(buffer.toString());
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, cid);
			if(classid==-1){
				ps.setString(3, "%" + "%");
			}else{
				ps.setString(3, "" + classid + "");
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(3));
				eu.setUsername(rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				eu.setId(rs.getInt("id"));
				Course c = new Course();
				c.setCredit(rs.getInt(6));
				c.setDuring(rs.getInt(7));
				c.setJieye(rs.getInt("jieyeid"));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getFloat(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				mc.setMyCredit(rs.getFloat(11));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
				mc.getMyExamPaper().setMyScore(rs.getFloat(13));
				mc.getMyExamPaper().setIspassed(rs.getInt(14));
				mc.setClassId(rs.getInt("classId"));
				mc.setClassName(rs.getString("className"));
				mc.setPasstime2(rs.getInt("passtime2"));
				mc.setUser(eu);
				mc.setMyExamPaperList(this.getExamPaperList(cid, mc.getClassId(), eu.getId(),roomTitle));//获取该课程该学员该班级的绑定考场的考试集合（因为1个考场可能有多张试卷需要考试）
				mc.setExamRoom(new ExamRoom(rs.getInt("examroomid"),rs.getString("title")));
				if(mc.getClassId()>0&&mc.getMyExamPaperList().size()==0){
					//没搜到考场，跳过
					if(!"".equals(roomTitle)){
						continue;
					}
				}
				if(mc.getMyExamPaperList()!=null&&mc.getMyExamPaperList().size()>0){
					mc.getMyExamPaper().setExamRoom(mc.getMyExamPaperList().get(0).getExamRoom());
				}
				if(mc.getClassId()==0&&mc.getMyExamPaperList().size()>0){
					//特殊处理
					//获取考场集合
					List<ExamRoom> erlist=this.getExamRoom(cid, eu.getId(),roomTitle);
					for (int i = 0; i < erlist.size(); i++) {
						ExamRoom er=erlist.get(i);
						//if(er.getId()==mc.getMyExamPaper().getExamRoom().getId()){
							//跳过
							//continue;
						//}
						//查出该考场该学员所对应的所有试卷
						MyCourse mc2 = new MyCourse();
						//mc2=mc;
						mc2.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
						mc2.setCourse(c);
						mc2.setPasstime(rs.getInt(8));
						mc2.setProcess(rs.getInt(9));
						mc2.setUser(eu);
						mc2.setMyExamPaperList(this.getExamPaperList(er.getId(), eu.getId()));//获取该课程该学员该班级的绑定考场的考试集合（因为1个考场可能有多张试卷需要考试）
						if(mc2.getMyExamPaperList()!=null&&mc2.getMyExamPaperList().size()>0){
							mc2.getMyExamPaper().setExamRoom(mc2.getMyExamPaperList().get(0).getExamRoom());
						}
						myBxc.add(mc2);
					}
					continue;
				}
				myBxc.add(mc); 
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}
	
	/**
	 * 获取考场集合
	 * @param courseid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<ExamRoom> getExamRoom(int courseid,int userid,String roomTitle) throws ElException {
		List<ExamRoom> list=new ArrayList<ExamRoom>();
//		MyExamPaper myExamPaper=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sql="";
//			if(roomTitle==null||"".equals(roomTitle)){
				sql="select distinct er.id erid, er.title from (select * from exam_room where iscommon = 0 and courseid=? and title like ?) er inner join course c on c.id = er.courseid inner join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id  where sqi.classid=0";
//			}else{
//				sql="select distinct er.id erid, er.title from (select * from exam_room where iscommon = 0 and courseid=? and title like ?) er inner join course c on c.id = er.courseid inner join (select * from study_quizinfo where userid = ?) sqi on sqi.roomid =er.id  where sqi.classid=0";
//			}
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setInt(1, courseid);
			if("".equals(roomTitle)){
				ps.setString(2, "%"+roomTitle+"%");
			}else{
				ps.setString(2, ""+roomTitle+"");
			}
			ps.setInt(3, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ExamRoom examRoom=new ExamRoom(rs.getInt("erid"),rs.getString("title"));
				list.add(examRoom);
			}
		} catch (Exception e) {
			logger.error("查询相对应的考试出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	/**
	 * 查询相对应的考试（根据考场）
	 * @param courseid
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> getExamPaperList(int roomid,int userid) throws ElException {
		List<MyExamPaper> list=new ArrayList<MyExamPaper>();
		MyExamPaper myExamPaper=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sql="select sqi.id  sqiid_,sqi.myScore,sqi.ispassed,ep.id epid,ep.title,er.id erid,er.title erTitle from exam_room er left join study_quizinfo sqi on er.id=sqi.roomid left join exampaper ep on sqi.epid=ep.id where er.id=? and sqi.userid=? and sqi.classid=0";
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setInt(1, roomid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				myExamPaper=new MyExamPaper();
				myExamPaper.setId(rs.getInt("sqiid_"));
				myExamPaper.setMyScore(rs.getFloat("myScore"));
				myExamPaper.setIspassed(rs.getInt("ispassed"));
				myExamPaper.setExamPaper(new ExamPaper(rs.getInt("epid"),rs.getString("title")));
				myExamPaper.setExamRoom(new ExamRoom(rs.getInt("erid"),rs.getString("erTitle")));
				list.add(myExamPaper);
			}
		} catch (Exception e) {
			logger.error("查询相对应的考试出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	/**
	 * 查询相对应的考试
	 * @param courseid
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<MyExamPaper> getExamPaperList(int courseid,int classid,int userid,String roomTitle) throws ElException {
		List<MyExamPaper> list=new ArrayList<MyExamPaper>();
		MyExamPaper myExamPaper=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String sql="";
			if(classid==0){
				sql="select sqi.id  sqiid_,sqi.myScore,sqi.ispassed,ep.id epid,ep.title,er.id erid,er.title erTitle from exam_room er left join study_quizinfo sqi on er.id=sqi.roomid left join exampaper ep on sqi.epid=ep.id where er.courseid=? and er.isband=1 and (er.bandclassid=0 or er.bandclassid is null) and sqi.userid=? and sqi.classid=0";//此数据作废
			}else{
				//sql="select sqi.id  sqiid_,sqi.myScore,sqi.ispassed,ep.id epid,ep.title,er.id erid,er.title erTitle from exam_room er left join study_quizinfo sqi on er.id=sqi.roomid left join exampaper ep on sqi.epid=ep.id where er.courseid=? and er.isband=1 and er.bandclassid=? and sqi.userid=? and sqi.classid=?";
				sql="select sqi.id  sqiid_,sqi.myScore,sqi.ispassed,ep.id epid,ep.title,er.id erid,er.title erTitle from exam_room er left join study_quizinfo sqi on er.id=sqi.roomid left join exampaper ep on sqi.epid=ep.id where er.courseid=? and er.isband=1 and er.bandclassid=? and sqi.userid=? and sqi.classid=? and er.title like ?";
			}
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql);
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			if(classid==0){
				ps.setInt(1, courseid);
				ps.setInt(2, userid);
			}else{
				ps.setInt(1, courseid);
				ps.setInt(2, classid);
				ps.setInt(3, userid);
				ps.setInt(4, classid);
				if("".equals(roomTitle)){
					ps.setString(5, "%"+roomTitle+"%");
				}else{
					ps.setString(5, ""+roomTitle+"");
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				myExamPaper=new MyExamPaper();
				myExamPaper.setId(rs.getInt("sqiid_"));
				myExamPaper.setMyScore(rs.getFloat("myScore"));
				myExamPaper.setIspassed(rs.getInt("ispassed"));
				myExamPaper.setExamPaper(new ExamPaper(rs.getInt("epid"),rs.getString("title")));
				myExamPaper.setExamRoom(new ExamRoom(rs.getInt("erid"),rs.getString("erTitle")));
				list.add(myExamPaper);
			}
		} catch (Exception e) {
			logger.error("查询相对应的考试出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	public List<MyCourse> course_user_list_BYCtypePage(CourseType ctypeTree,
			int ctid, String name, int cid)
			throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select t.*, rownum rn from (select eu.id,eu.username,eu.realname,eu.depid,dep.name ")
					.append(
							" depname,c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_,sqi.myScore,sqi.ispassed from ")
					.append(
							" COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join")
					.append(
							" eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi")
					.append(
							" on sqi.id=sc.sqiid where eu.realname like ? and sc.courseid =?)t");
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+") 

			ps = ct.prepareStatement(buffer.toString());

			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, cid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(3));
				eu.setUsername(rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));

				Course c = new Course();
				c.setCredit(rs.getInt(6));
				c.setDuring(rs.getInt(7));
				MyCourse mc = new MyCourse();
				mc.setPasstime(rs.getInt(8));
				mc.setProcess(rs.getFloat(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				mc.setMyCredit(rs.getFloat(11));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(12)));
				mc.getMyExamPaper().setMyScore(rs.getFloat(13));
				mc.getMyExamPaper().setIspassed(rs.getInt(14));
				mc.setUser(eu);
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public int course_user_list_BYCtypeCount(CourseType ctypeTree, int ctid,
			String name, int cid) throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select count(*) from (select eu.id,eu.username,eu.realname,eu.depid,dep.name ")
					.append(
							" depname,c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_,sqi.myScore,sqi.ispassed from ")
					.append(
							" COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join")
					.append(
							" eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi")
					.append(
							" on sqi.id=sc.sqiid where eu.realname like ? and sc.courseid =?)");

			ps = ct.prepareStatement(buffer.toString());
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, cid);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("查看类别课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 获取课程中学员数量
	 * @param ctypeTree
	 * @param ctid
	 * @param name
	 * @param cid
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public int course_user_list_BYCtypeCount(CourseType ctypeTree, int ctid,
			String name, int cid,int classid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select count(*) from (select eu.id,eu.username,eu.realname,eu.depid,dep.name ")
					.append(
							" depname,c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_,sqi.myScore,sqi.ispassed from ")
					.append(
							" COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join")
					.append(
							" eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi")
					.append(
							" on sqi.id=sc.sqiid where eu.realname like ? and sc.courseid =? and sc.classid like ?)");

			ps = ct.prepareStatement(buffer.toString());
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, cid);
			if(classid==-1){
				ps.setString(3, "%" + "%");
			}else{
				ps.setString(3, "" + classid + "");
			}
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("查看类别课程出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<MyCourse> listMyCourse(int userid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select c.id cid, c.name,c.creater, eu.realname,c.credit, ")
					.append(
							" c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,ec.id as classid,ec.name as className  ")
					.append(
							" from study_course sc left join course c on sc.courseid = c.id  ")
					.append(
							" left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid left join elclass ec on sc.classid=ec.id where sc.userid = ? order by sc.status asc, c.createtime desc ");
			ps = ct.prepareStatement(buffer.toString());

			ps.setInt(1, userid);

			rs = ps.executeQuery();
			while (rs.next()) {
				// 标准课程
				// 43 现场管理与现场改善实务 1 管理员 60 710 32 4 1
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCreater(new ELUser(rs.getInt(3), rs.getString(4)));
				// c.setPassgrade(rs.getFloat(5));
				c.setCredit(rs.getInt(5));
				c.setDuring(rs.getInt(6));
				c.setTeacherName(rs.getString(7));
				MyCourse mc = new MyCourse();
				int t = rs.getInt(8);
				mc.setPasstime(t>c.getDuring()?c.getDuring():t);
				mc.setProcess(rs.getInt(9));
				mc.setCourse(c);
				mc.setStatus(rs.getInt(10));
				c.setIslink(rs.getInt(11));
				mc.setMyCredit(rs.getFloat(12));
				mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
				mc.getMyExamPaper().setMyScore(rs.getInt(14));
				mc.getMyExamPaper().setIspassed(rs.getInt(15));
				c.setRoomstart(rs.getTimestamp(16));
				c.setRoomend(rs.getTimestamp(17));
				c.setClassid(rs.getInt("classid"));
				c.setClassName(rs.getString("className"));
				myBxc.add(mc);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}

	public List<Course> listCourseByCreater(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Course> cses = new ArrayList<Course>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(StatisticConstants.STAT_COURSE_BYCREATER));
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Course c = new Course(rs.getInt(1), rs.getString(2));
				c.setCtype(new CourseType(rs.getInt(3), rs.getString(4)));
				c.setCredit(rs.getInt(5));
				c.setCreatetime(rs.getTimestamp(6));
				c.setUserCount(rs.getInt(7));
				c.setUserPassedCount(rs.getInt(8));
				cses.add(c);
			}
		} catch (Exception e) {
			logger.error("用户开课列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cses;
	}

//	public List<ELUser> getStatCtimeUserByDep(int depid, int subdep, ELUser eu,
//			int pageNow, int pageSize) throws ElException {
//		List<ELUser> eus = new ArrayList<ELUser>();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//			}
//			ct = DBConnection.getConnection();
//			if (subdep == ElConstants.SUBOP_YES) {
//				Department dep = new Department();
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//				ps.setInt(1, depid);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					dep.setId(rs.getInt(1));
//					dep.setLid(rs.getInt(2));
//					dep.setRid(rs.getInt(3));
//				}
//				ps.close();
//				rs.close();
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(StatisticConstants.STAT_CTIME_USER_DEP_SUB));
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setInt(3, dep.getLid());
//				ps.setInt(4, dep.getRid());
//				ps.setInt(5, pageNow);
//				ps.setInt(6, pageSize);
//			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(StatisticConstants.STAT_CTIME_USER_DEP));
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setInt(3, depid);
//				ps.setInt(4, pageNow);
//				ps.setInt(5, pageSize);
//			}
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				ELUser elUser = new ELUser();
//				elUser.setId(rs.getInt(1));
//				elUser.setUsername(rs.getString(2));
//				elUser.setRealname(rs.getString(3));
//				elUser.setDepartment(new Department(rs.getInt(4), rs
//						.getString(5)));
//				elUser.setCt_time(rs.getInt(6));
//				int t  = rs.getInt(7);
//				elUser.setXx_time(t>elUser.getCt_time()?elUser.getCt_time():t);
//				elUser.setCt_credit(rs.getInt(8));
//				elUser.setXx_credit(rs.getInt(9));
//
//				eus.add(elUser);
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return eus;
//	}
	
	/**
	 * 检测用户查询的参数
	 */
	public void checkUserParam(StringBuffer sql,ELUser elUser,List<Object> params){
		if (elUser != null) {
			if (elUser.getSex() != null && !elUser.getSex().equals("")) {
				sql.append(" and eu.sex =?");
				params.add(elUser.getSex());
			}
			if (elUser.getRealname() != null && !elUser.getRealname().equals("")) {
				sql.append(" and eu.realname like ?");
				params.add("%"+StringUtil.toLikeStr(elUser.getRealname())+"%");
			}
			if (elUser.getUsername() != null && !elUser.getUsername().equals("")) {
				sql.append(" and eu.username like ?");
				params.add("%"+StringUtil.toLikeStr(elUser.getUsername())+"%");
			}
			if (elUser.getJingzhong()>0) {
				sql.append(" and eu.jingzhong = ?");
				params.add(elUser.getJingzhong());
			}
//			if (null != elUser.getDishi() && !elUser.getDishi().equals("")&& !elUser.getDishi().equals("0")){
//				sql.append("  and eu.dishi = '"+elUser.getDishi().trim()+"' "); 
//			}
//			if (null != elUser.getZhiji() && !elUser.getZhiji().equals("")&& !elUser.getZhiji().equals("0")){
//				sql.append("  and eu.zhiji = '"+elUser.getZhiji().trim()+"' ");
//			}
//			if (null != elUser.getZhiwu() && !elUser.getZhiwu().equals("")&& !elUser.getZhiwu().equals("0")){
//				sql.append("  and eu.zhiwu = '"+elUser.getZhiwu().trim()+"' "); 
//			}
//			if (null != elUser.getGangwei() && !elUser.getGangwei().equals("")&& !elUser.getGangwei().equals("0")){
//				sql.append("  and eu.gangwei = '"+elUser.getGangwei().trim()+"' ");
//			}
			if(elUser.getShengri()!=null){
				sql.append(" and eu.shengri >= ?");
				params.add(elUser.getShengri());
			}
			if(elUser.getShengri_end()!=null){
				sql.append(" and eu.shengri <= ?");
				params.add(elUser.getShengri_end());
			}
			if (elUser.getIsAssign() != null
					&& !"".equals(elUser.getIsAssign())) {
				if ("0".equals(elUser.getIsAssign())) {
					sql.append(" and  scl.certificateno is null");
				} else {
					sql.append(" and  scl.certificateno is not null ");
				}
			}
		}
	}
	/**
	 * 学时统计用户查询
	 * @param tree
	 * @param sublibs
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> getStatCtimeUserByDep(ElNode tree, int sublibs, ELUser eu,
			int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params=new ArrayList<Object>();
			StringBuffer sql=new StringBuffer("select * from (select t.*, rownum rn from ( "+
					"select eu.id,eu.username, eu.realname,dep.id depid,dep.name,NVL(sum(c.during),0) as t_time,NVL(sum(sc.passtime/60),0)as x_time ,NVL(sum(c.credit),0) as t_credit,NVL(sum(sc.mycredit ),0)as x_score from ELUSER eu " +
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, consub)+") dep on eu.depid = dep.id left join study_course sc on sc.userid = eu.id " +
					" left join course c on sc.courseid =c.id where 1=1 ");
			this.checkUserParam(sql, eu, params);
			sql.append(" group by eu.id,eu.username, eu.realname, dep.id,dep.name order by NVL(sum(sc.passtime/60),0) desc)t where rownum <= ? ) where rn>=?");
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			ps.setInt(params.size()+1, pageNow);
			ps.setInt(params.size()+2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setDepartment(new Department(rs.getInt(4), rs
						.getString(5)));
				elUser.setCt_time(rs.getInt(6));
				int t  = rs.getInt(7);
				elUser.setXx_time(t>elUser.getCt_time()?elUser.getCt_time():t);
				elUser.setCt_credit(rs.getInt(8));
				elUser.setXx_credit(rs.getInt(9));
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("学时统计用户查询失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public List<ELUser> getStatCtimeUserByDep(int depid, int subdep, ELUser eu)
			throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
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
				ps.close();
				rs.close();
				ps = ct
						.prepareStatement(ElQuerySql
								.getSQL(StatisticConstants.STAT_CTIME_USER_DEP_SUB_ALL));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_CTIME_USER_DEP_ALL));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setDepartment(new Department(rs.getInt(4), rs
						.getString(5)));
				 elUser.setCt_time(rs.getInt(6));
				 elUser.setXx_time(rs.getInt(7));
				// elUser.setCt_credit(rs.getInt(8));
				// elUser.setXx_credit(rs.getInt(9));
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

//	public int getStatCtimeUserByDepCount(int depid, int subdep, ELUser eu)
//			throws ElException {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		try {
//			String username = "";
//			String realname = "";
//			if (null != eu) {
//				if (null != eu.getUsername())
//					username = eu.getUsername().trim();
//				if (null != eu.getRealname())
//					realname = eu.getRealname().trim();
//			}
//			ct = DBConnection.getConnection();
//			if (subdep == ElConstants.SUBOP_YES) {
//				Department dep = new Department();
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
//				ps.setInt(1, depid);
//				rs = ps.executeQuery();
//				if (rs.next()) {
//					dep.setId(rs.getInt(1));
//					dep.setLid(rs.getInt(2));
//					dep.setRid(rs.getInt(3));
//				}
//				ps.close();
//				rs.close();
//				ps = ct
//						.prepareStatement(ElQuerySql
//								.getSQL(StatisticConstants.STAT_CTIME_USER_DEP_SUB_SIZE));
//
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setInt(3, dep.getLid());
//				ps.setInt(4, dep.getRid());
//			} else {
//				ps = ct.prepareStatement(ElQuerySql
//						.getSQL(StatisticConstants.STAT_CTIME_USER_DEP_SIZE));
//				ps.setString(1, "%" + username + "%");
//				ps.setString(2, "%" + realname + "%");
//				ps.setInt(3, depid);
//			}
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				return rs.getInt(1);
//			}
//		} catch (Exception e) {
//			logger.error("用户列表搜索失败！", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return 0;
//	}
	
	/**
	 * 学时统计的查询(数量)
	 * @param tree
	 * @param sublibs
	 * @param eu
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int getStatCtimeUserByDepCount(ElNode tree, int sublibs, ELUser eu) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			boolean consub = sublibs == 1 ? true : false;
			List<Object> params=new ArrayList<Object>();
			StringBuffer sql=new StringBuffer(
				"select count(eu.id) " +
				" from ELUSER eu inner join ("+((ElNodeSQL)SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL)).generateSQLByTree("department", tree, consub)+") dep on eu.depid = dep.id where 1=1 ");
			this.checkUserParam(sql, eu, params);
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(sql.toString());
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i+1, params.get(i));
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("学时统计的查询(数量)失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<ELUser> getStatCnoteUserByDep(int depid, int subdep, ELUser eu,
			int pageNow, int pageSize) throws ElException {
		List<ELUser> eus = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
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
				ps.close();
				rs.close();
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_CNOTE_USER_DEP_SUB));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
				ps.setInt(5, pageNow);
				ps.setInt(6, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(StatisticConstants.STAT_CNOTE_USER_DEP));
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser elUser = new ELUser();
				elUser.setId(rs.getInt(1));
				elUser.setUsername(rs.getString(2));
				elUser.setRealname(rs.getString(3));
				elUser.setDepartment(new Department(rs.getInt(4), rs
						.getString(5)));
				// elUser.setCt_time(rs.getInt(6));
				// elUser.setXx_time(rs.getInt(7));
				// elUser.setCt_credit(rs.getInt(8));
				// elUser.setXx_credit(rs.getInt(9));
				eus.add(elUser);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return eus;
	}

	public int getStatCnoteUserByDepCount(int depid, int subdep, ELUser eu)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			String username = "";
			String realname = "";
			if (null != eu) {
				if (null != eu.getUsername())
					username = eu.getUsername().trim();
				if (null != eu.getRealname())
					realname = eu.getRealname().trim();
			}
			ct = DBConnection.getConnection();
			if (subdep == ElConstants.SUBOP_YES) {
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
				ps.close();
				rs.close();
				ps = ct
						.prepareStatement("select count(*) from ELUSER eu left join DEPARTMENT dep on eu.depid = dep.id "
								+ "where eu.username like ? and eu.realname like ? and dep.lid >=? and dep.rid<=?");

				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, dep.getLid());
				ps.setInt(4, dep.getRid());
			} else {
				ps = ct
						.prepareStatement("select count(*) from ELUSER eu left join DEPARTMENT"
								+ " dep on eu.depid = dep.id where eu.username like ? and eu.realname like ? and dep.id=?");
				ps.setString(1, "%" + username + "%");
				ps.setString(2, "%" + realname + "%");
				ps.setInt(3, depid);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("用户列表搜索失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 统计学员培训班学习和章节练习轨迹
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyCPage> statisticStudyLearnLocus(int userid,int classid,int courseid,int pageNow, int pageSize)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCPage> myCPages = new ArrayList<MyCPage>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer sql=new StringBuffer("select * from (select t.*, rownum rn from (select * from ("+
					"select ec.id ecid,ec.name className,c.id cid,c.name courseName,cp.id cpid,cp.title cpTitle,pp.id ppid,pp.title ppTitle,cqr.begintime,cqr.endtime,cqr.myscore,cqr.passed,-1 passtime,cq.userid from cprac_quizinfo_record cqr left join cprac_quizinfo cq on cqr.sqid=cq.id left join practicepaper pp on cq.ppid=pp.id "+
					"join elclass ec on cq.classid=ec.id left join course c on pp.courseid=c.id left join course_page cp on pp.cpid=cp.id "+
					"union "+
					"select ec.id ecid,ec.name className,c.id cid,c.name courseName,cp.id cpid,cp.title cpTitle,null,null,scr.begintime,scr.endtime,-1,-1,scr.passtime passtime,scr.userid from study_course_record scr left join elclass ec on scr.classid=ec.id left join course c on scr.courseid=c.id left join course_page cp on scr.cpid=cp.id "+
					") t where t.userid=? ");
			if(classid>0){
				sql.append(" and t.ecid=? ");
			}
			if(courseid>0){
				sql.append(" and t.cid=? ");
			}
			sql.append("order by t.begintime desc) t where rownum <= ? ) where rn>=?");
			ps = ct.prepareStatement(sql.toString());
			int i=0;
			ps.setInt(1, userid);
			if(classid>0){
				i++;
				ps.setInt(1+i, classid);
			}
			if(courseid>0){
				i++;
				ps.setInt(1+i, courseid);
			}
			ps.setInt(2+i, pageNow);
			ps.setInt(3+i, pageSize);
			rs = ps.executeQuery();
			MyCPage myCpage=null;
			while (rs.next()) {
				myCpage=new MyCPage();
				myCpage.setCpage(new CoursePage(rs.getInt(5),rs.getString(6)));
				myCpage.getCpage().setCourse(new Course(rs.getInt(3),rs.getString(4)));
				myCpage.getCpage().getCourse().setClassid(rs.getInt(1));
				myCpage.getCpage().getCourse().setClassName(rs.getString(2));
				myCpage.setPracp(new PracticePaper(rs.getInt(7),rs.getString(8)));
				myCpage.setBegintime(rs.getTimestamp(9));
				myCpage.setEndtime(rs.getTimestamp(10));
				myCpage.setMyscore(rs.getFloat(11));
				myCpage.setPassed2(rs.getInt(12));
				myCpage.setPasstime2(rs.getInt(13));
				myCPages.add(myCpage);
			}
		} catch (Exception e) {
			logger.error("统计学员培训班学习和章节练习轨迹出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myCPages;
	}
	/**
	 * 统计学员培训班学习和章节练习轨迹数据数量
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public int statisticStudyLearnLocusSize(int userid,int classid,int courseid)throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql="";
			if(classid>0){
				sql=" and t.ecid=? ";
			}
			if(courseid>0){
				sql+=" and t.cid=? ";
			}
			ps = ct.prepareStatement("select count(userid) from (select id,eu.id userid,ec.id ecid,-1 cid from cprac_quizinfo_record cqr left join cprac_quizinfo cq on cqr.sqid=cq.id left join eluser eu on cq.userid=eu.id left join elclass ec on cq.classid=ec.id "+
				"union "+
				"select id,userid userid,classid,courseid cid from study_course_record ) t where t.userid=?"+sql);
			int i=0;
			ps.setInt(1, userid);
			if(classid>0){
				i++;
				ps.setInt(1+i, classid);
			}
			if(courseid>0){
				i++;
				ps.setInt(1+i, courseid);
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("统计学员培训班学习和章节练习轨迹数据数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Integer> getroomid(int userid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Integer> roomids = new ArrayList<Integer>();
		try {
			int id = 0;
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from study_room where userid=?");
			rs = ps.executeQuery();
			while(rs.next()){
				id = rs.getInt("roomid");
				roomids.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return roomids;
	}
}
