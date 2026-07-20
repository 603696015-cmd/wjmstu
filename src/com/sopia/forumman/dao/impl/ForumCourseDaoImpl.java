package com.sopia.forumman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.classman.entities.ELClassRegistration;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.forumman.dao.ForumCourseDao;
import com.sopia.shopping.entities.ShoppingCart;

public class ForumCourseDaoImpl implements ForumCourseDao {
	private static final Log logger = LogFactory
	.getLog(ForumAdminDaoImpl.class);
	
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
			String ids = courseTypeById(ctypeTree, ctid);
			// if(role != 1 && !ids.equals(x) )//角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			// ids = ctid == 1?ids.substring(x.length()+1,ids.length()):ids;
			// //当id等于虚拟根时,从所有的id中去掉虚拟根id

			String conditions = "";
			if (course != null) {
				if (course.getName() != null 
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
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description,c.astatus  from course c, course_type ct,")
					.append(
							" eluser u,department dep,course_price cp where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and cp.courseid=c.id and cp.status =1  " + conditions)
					// "//去掉类型权限
					.append(
							" and ct.id in (" + ids + ") order by c.createtime desc )t ")
					.append(" where rownum <= ? ) where rn>=?");
			// System.out.println(buffer.toString());
			ps = ct.prepareStatement(buffer.toString());
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
	public List<Course> listAllCourseFromThishuiyuanzhongxin(CourseType ctypeTree, int depid,
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
//
//			String x = Integer.toString(ctid);
			String ids = courseTypeById(ctypeTree, ctid);
			// if(role != 1 && !ids.equals(x) )//角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
			// ,当角色不为1时ids的只有一个根节点时也不截取
			// ids = ctid == 1?ids.substring(x.length()+1,ids.length()):ids;
			// //当id等于虚拟根时,从所有的id中去掉虚拟根id

			String conditions = "";
			if (course != null) {
				if (course.getName() != null 
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
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description,c.astatus  from course c, course_type ct,")
					.append(
							" eluser u,department dep,course_price cp where c.ctypeid=ct.id and c.creater = u.id ")
					.append(
							" and u.depid=dep.id and cp.courseid=c.id and cp.status =1  " + conditions)
					// "//去掉类型权限
					.append(
							" and ct.id in (" + ids + ")   and   dep.lid >=? and dep.rid<=?   order by c.createtime desc )t ")
					.append(" where rownum <= ? ) where rn>=?");
			// System.out.println(buffer.toString());
			ps = ct.prepareStatement(buffer.toString());
			// ps.setInt(1, CourseConstants.COURSE_STATUS_OPEN);
			ps.setInt(1, dep.getLid());
			ps.setInt(2, dep.getRid());
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
			List<Course> css = new ArrayList<Course>();
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
								" eluser u,department dep ,course_price cp where c.ctypeid=ct.id and c.creater = u.id ")
						.append(
								" and u.depid=dep.id and cp.courseid=c.id and cp.status=1 " + conditions)//
						.append(
								" and ct.id in (" + ids + ")  order by c.createtime desc )t ");

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
		public int listAllCourseFromThissizehuiyuanzhongxin(CourseType ctypeTree, int depid,
				int role, Course course, int ctid, String status, String sqlw)
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
								" eluser u,department dep ,course_price cp where c.ctypeid=ct.id and c.creater = u.id ")
						.append(
								" and u.depid=dep.id and cp.courseid=c.id and cp.status=1 " + conditions)//
						.append(
								" and ct.id in (" + ids + ") and  dep.lid >=? and dep.rid<=?  order by c.createtime desc )t ");

				ps = ct.prepareStatement(buffer.toString());
				ps.setInt(1, dep.getLid());
				ps.setInt(2, dep.getRid());
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
		 * 获取全部已开通的培训班信息（去掉已删除的）
		 * 
		 * @return
		 * @throws ElException
		 */
		public List<ElClass> getApplyForeElclass(ElClType tree, int cltid,
				ElClass elClass, int role, String sqlw, int pageNow, int pageSize)
				throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<ElClass> classlist = new ArrayList<ElClass>();
			try {
				String x = Integer.toString(cltid);
				String ids = ElClTypeById(tree, cltid);
				if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
					// ,当角色不为1时ids的只有一个根节点时也不截取
					ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
							: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
				if (cltid == 1) {
					ids = "1," + ids;
				}
				// if(elclass!=null){
				// if(
				// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
				// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
				// }
				// if(elclass.getStatus()!=-1){//考场状态
				// sqls+=" and cl.status="+elclass.getStatus();
				// }
				// if(elclass.getBegintime()!=null){
				// sqls+=" and cl.STARTTIME >= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
				// }
				// if(elclass.getEndtime()!=null){
				// sqls+=" and cl.FINISHTIME <= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
				// }
				// }
				String ClassConditions = "";
				if (elClass != null) {
					if (elClass.getName() != null && !elClass.getName().equals("")) {
						ClassConditions = ClassConditions + " and elc.name like '%"
								+ elClass.getName() + "%'";
					}
				}
				ct = DBConnection.getConnection();
				String sql = "select * from (select t.*, rownum rn from ( "
						+ "select elc.id,elc.name,elc.description,elc.certificatename,elc.createtime,elc.starttime,elc.finishtime, "
						+ " elc.mainimg,clt.id cltid "
						+ "from elclass elc,elclasstype clt "
						+ "where elc.cltype = clt.id  and elc.status in (5) and clt.id in ("
						+ ids + ")" + ClassConditions + sqlw +
						// "and elr.registrationStartTime < sysdate and
						// elr.registrationStopTime > sysdate" +
						")t where rownum <= ? ) where rn>=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
				rs = ps.executeQuery();
				while (rs.next()) {
					ElClass elc = new ElClass();
					elc.setId(rs.getInt(1));
					elc.setName(rs.getString(2));
					elc.setDescription(rs.getString(3));
					elc.setCertificatename(rs.getString(4));
					elc.setCreatetime(rs.getTimestamp(5));
					elc.setStarttime(rs.getTimestamp(6));
					elc.setFinishtime(rs.getTimestamp(7));
					elc.setMainimg(rs.getString(8));
					elc.setCltype(new ElClType(rs.getInt("cltid")));
					ELClassRegistration elR = new ELClassRegistration();
					elc.setElRegistration(elR);
					classlist.add(elc);
				}
			} catch (Exception e) {
				logger.error("可申请培训班列表失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return classlist;
		}
		public List<ElClass> getApplyForeElclasshuiyuanfuwu(ElClType tree,int depid,int cltid,
				ElClass elClass, int role, String sqlw, int pageNow, int pageSize)
				throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<ElClass> classlist = new ArrayList<ElClass>();
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
				String x = Integer.toString(cltid);
				String ids = ElClTypeById(tree, cltid);
				if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
					// ,当角色不为1时ids的只有一个根节点时也不截取
					ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
							: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
				if (cltid == 1) {
					ids = "1," + ids;
				}
				// if(elclass!=null){
				// if(
				// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
				// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
				// }
				// if(elclass.getStatus()!=-1){//考场状态
				// sqls+=" and cl.status="+elclass.getStatus();
				// }
				// if(elclass.getBegintime()!=null){
				// sqls+=" and cl.STARTTIME >= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
				// }
				// if(elclass.getEndtime()!=null){
				// sqls+=" and cl.FINISHTIME <= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
				// }
				// }
				String ClassConditions = "";
				if (elClass != null) {
					if (elClass.getName() != null && !elClass.getName().equals("")) {
						ClassConditions = ClassConditions + " and elc.name like '%"
								+ elClass.getName() + "%'";
					}
				}
				String sql = "select * from (select t.*, rownum rn from ( "
						+ "select elc.id,elc.name,elc.description,elc.certificatename,elc.createtime,elc.starttime,elc.finishtime, "
						+ " elc.mainimg,clt.id cltid "
						+ "from elclass elc,elclasstype clt,eluser elu ,DEPARTMENT  dep  "
						+ "where elc.cltype = clt.id and elu.id=elc.creater and elu.depid=dep.id  and elc.status in (5) and clt.id in ("
						+ ids + ")" + ClassConditions + sqlw + 
						// "and elr.registrationStartTime < sysdate and
						// elr.registrationStopTime > sysdate" +
						"    and   dep.lid >=? and dep.rid<=?)t where rownum <= ? ) where rn>=?";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, dep.getLid());
				ps.setInt(2, dep.getRid());
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
				rs = ps.executeQuery();
				while (rs.next()) {
					ElClass elc = new ElClass();
					elc.setId(rs.getInt(1));
					elc.setName(rs.getString(2));
					elc.setDescription(rs.getString(3));
					elc.setCertificatename(rs.getString(4));
					elc.setCreatetime(rs.getTimestamp(5));
					elc.setStarttime(rs.getTimestamp(6));
					elc.setFinishtime(rs.getTimestamp(7));
					elc.setMainimg(rs.getString(8));
					elc.setCltype(new ElClType(rs.getInt("cltid")));
					ELClassRegistration elR = new ELClassRegistration();
					elc.setElRegistration(elR);
					classlist.add(elc);
				}
			} catch (Exception e) {
				logger.error("可申请培训班列表失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return classlist;
		}
		/**
		 * 获取全部已开通的培训班列表大小（去掉已删除的）
		 * 
		 * @return
		 * @throws ElException
		 */
		public int getApplyForeElclasssizehuiyuanfuwu(ElClType tree,int depid, int cltid,
				ElClass elClass, int role, String sqlw)
				throws ElException {
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
				String x = Integer.toString(cltid);
				String ids = ElClTypeById(tree, cltid);
				if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
					// ,当角色不为1时ids的只有一个根节点时也不截取
					ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
							: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
				if (cltid == 1) {
					ids = "1," + ids;
				}
				// if(elclass!=null){
				// if(
				// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
				// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
				// }
				// if(elclass.getStatus()!=-1){//考场状态
				// sqls+=" and cl.status="+elclass.getStatus();
				// }
				// if(elclass.getBegintime()!=null){
				// sqls+=" and cl.STARTTIME >= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
				// }
				// if(elclass.getEndtime()!=null){
				// sqls+=" and cl.FINISHTIME <= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
				// }
				// }
				String ClassConditions = "";
				if (elClass != null) {
					if (elClass.getName() != null && !elClass.getName().equals("")) {
						ClassConditions = ClassConditions + " and elc.name like '%"
								+ elClass.getName() + "%'";
					}
				}
				String sql = "select count(1) from ( "
						+ "select elc.id,elc.name,elc.description,elc.certificatename,elc.createtime,elc.starttime,elc.finishtime, "
						+ " elc.mainimg,clt.id cltid "
						+ "from elclass elc,elclasstype clt ,eluser elu ,DEPARTMENT  dep "
						+ "where  elc.cltype = clt.id and elu.id=elc.creater and elu.depid=dep.id and elc.status in (5) and clt.id in ("
						+ ids + ")" + ClassConditions + sqlw +
						// "and elr.registrationStartTime < sysdate and
						// elr.registrationStopTime > sysdate" +
						"   and   dep.lid >=? and dep.rid<=? )";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, dep.getLid());
				ps.setInt(2, dep.getRid());
				rs = ps.executeQuery();
				rs.next();
				return  rs.getInt(1);
			} catch (Exception e) {
				logger.error("可全部培训班列表失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}

		/**
		 * 获取全部已开通的培训班列表大小（去掉已删除的）
		 * 
		 * @return
		 * @throws ElException
		 */
		public int getApplyForeElclasssize(ElClType tree, int cltid,
				ElClass elClass, int role, String sqlw)
				throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			try {
				String x = Integer.toString(cltid);
				String ids = ElClTypeById(tree, cltid);
				if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
					// ,当角色不为1时ids的只有一个根节点时也不截取
					ids = cltid == 1 ? ids.substring(x.length() + 1, ids.length())
							: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
				if (cltid == 1) {
					ids = "1," + ids;
				}
				// if(elclass!=null){
				// if(
				// elclass.getName()!=null&&!elclass.getName().equals("")){//培训名称
				// sqls+= " and cl.name like '%"+elclass.getName()+"%'";
				// }
				// if(elclass.getStatus()!=-1){//考场状态
				// sqls+=" and cl.status="+elclass.getStatus();
				// }
				// if(elclass.getBegintime()!=null){
				// sqls+=" and cl.STARTTIME >= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getBegintime())+ "','yyyy-MM-dd HH24:mi:ss')";
				// }
				// if(elclass.getEndtime()!=null){
				// sqls+=" and cl.FINISHTIME <= to_date('"+ new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				// .format(elclass.getEndtime())+ "','yyyy-MM-dd HH24:mi:ss')";
				// }
				// }
				String ClassConditions = "";
				if (elClass != null) {
					if (elClass.getName() != null && !elClass.getName().equals("")) {
						ClassConditions = ClassConditions + " and elc.name like '%"
								+ elClass.getName() + "%'";
					}
				}
				ct = DBConnection.getConnection();
				String sql = "select count(1) from ( "
						+ "select elc.id,elc.name,elc.description,elc.certificatename,elc.createtime,elc.starttime,elc.finishtime, "
						+ " elc.mainimg,clt.id cltid "
						+ "from elclass elc,elclasstype clt "
						+ "where  elc.cltype = clt.id and elc.status in (5) and clt.id in ("
						+ ids + ")" + ClassConditions + sqlw +
						// "and elr.registrationStartTime < sysdate and
						// elr.registrationStopTime > sysdate" +
						")";
				ps = ct.prepareStatement(sql);
				
				rs = ps.executeQuery();
				rs.next();
				return  rs.getInt(1);
			} catch (Exception e) {
				logger.error("可全部培训班列表失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}



		/**
		 * 查询出从ctid开始的有权的课程类型ID
		 * 
		 * @author heiweicheng
		 * @param ctypeTree
		 * @param ctid
		 * @return
		 */
		private String ElClTypeById(ElClType ctypeTree, int ctid) {
			if (ctypeTree != null) {
				if (ctypeTree.getId() != ctid) {
					ctypeTree = ElClTypeById(ctypeTree.getChild(), ctid);
				}
				if (ctypeTree.getChild() != null) {
					return createElClTypeId(ctypeTree.getChild(), ctypeTree.getId());
				}
				return String.valueOf(ctypeTree.getId());
			} else {
				return null;
			}
		}
		
		/**
		 * 构建有权的课程类型ID
		 * 
		 * @author heiweicheng
		 * @param ctypeTree
		 * @return
		 */
		private String createElClTypeId(List<ElClType> listType, int id) {
			String ids = id + "";
			for (ElClType type : listType) {
				ids = ids + "," + createElClTypeId(type.getChild(), type.getId());
			}
			return ids;
		}
		/**
		 * 如果不是跟节点开始 要找出开始节点
		 * 
		 * @author heiweicheng
		 * @param listType
		 * @param ctid
		 * @return
		 */
		private ElClType ElClTypeById(List<ElClType> listType, int ctid) {
			ElClType courseType = null;
			for (ElClType type : listType) {
				if (type.getId() != ctid) {
					courseType = ElClTypeById(type.getChild(), ctid);
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
		public List<CourseType> getcourseerjijiedian() throws ElException{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<CourseType>  list = new ArrayList<CourseType>();
			try {
				String  sql ="select  ct.id ,ct.name from  course_type  ct  where  ct.parentid=1 and ct.status!=1  ";
				ct = DBConnection.getConnection();
				ps=ct.prepareStatement(sql);
				rs=ps.executeQuery();
				while(rs.next()){
					CourseType  cut = new  CourseType();
					cut.setId(rs.getInt(1));
					cut.setName(rs.getString(2));
					list.add(cut);
				}
				return list;
			} catch (Exception e) {
				logger.error("可课程二级目录失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			
			
		}  
		
		public List<ElClType> getclasserjijiedian() throws ElException{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<ElClType>  list = new ArrayList<ElClType>();
			try {
				String  sql ="select  ct.id ,ct.name from  elclasstype  ct  where  ct.parentid=1 and status!=1  ";
				ct = DBConnection.getConnection();
				ps=ct.prepareStatement(sql);
				rs=ps.executeQuery();
				while(rs.next()){
					ElClType  cut = new  ElClType();
					cut.setId(rs.getInt(1));
					cut.setName(rs.getString(2));
					list.add(cut);
				}
				return list;
			} catch (Exception e) {
				logger.error("可课程二级目录失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			
			
		}  
		/**
		 * 前台查询推荐课程
		 * 
		 * @param pageNow
		 * @param pageSize
		 * @param status
		 * @return
		 * @throws ElException
		 */
		public List<Course> listAllCourseFromThis( int pageNow, int pageSize
				) throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Course> css = new ArrayList<Course>();
			try {
				ct = DBConnection.getConnection();
				StringBuffer buffer = new StringBuffer();
				buffer
						.append(
								"select * from (select t.*, rownum rn from (select c.id,")
						.append(
								" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
						.append(
								" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description,c.astatus  from course c, course_type ct,")
						.append(
								" eluser u,department dep,course_price cp where c.ctypeid=ct.id and c.creater = u.id ")
						.append(
								" and u.depid=dep.id and cp.courseid=c.id and cp.status =1 and c.hot=1 " )
						// "//去掉类型权限
						.append(
								"  order by c.createtime desc )t ")
						.append(" where rownum <= ? ) where rn>=?");
				// System.out.println(buffer.toString());
				ps = ct.prepareStatement(buffer.toString());
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


}
