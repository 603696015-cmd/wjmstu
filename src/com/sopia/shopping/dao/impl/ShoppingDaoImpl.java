package com.sopia.shopping.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.elclasspeice.entities.ElClassPeice;
import com.sopia.openmeetings.Rooms;
import com.sopia.peice.entities.Peice;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.shopping.entities.ClassOrder;
import com.sopia.shopping.entities.Commodity;
import com.sopia.shopping.entities.CourseOrder;
import com.sopia.shopping.entities.Order;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.StudyConstants;
import com.sopia.studyman.dao.impl.StudyClassDaoImpl;
import com.sopia.studyman.entities.MyCourse;
import com.sopia.studyman.entities.MyExamPaper;

public class ShoppingDaoImpl implements ShoppingDao {
	private static final Log logger = LogFactory.getLog(StudyClassDaoImpl.class);
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
			 int roomid)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("call into_sc2(?,?,?,?,?,?)");
			ps = ct.prepareStatement("call into_sc4(?,?,?,?,sysdate,add_months(sysdate,24),?)");
			ps.setInt(1, userid);
			ps.setInt(2, cid);
			ps.setInt(3, 0);
			ps.setInt(4, status);

			ps.setInt(5, roomid);
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
	public void assignedUser(int cid, int userid, int status
			) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// ps = ct.prepareStatement("call into_sc2(?,?,?,?,?,?)");
			ps = ct.prepareStatement("call into_sc2(?,?,?,?,sysdate,add_months(sysdate,24))");
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
	
	public List<MyCourse> course_user_list_BYCtypePage( ELUser elUser ,Department depTree, int depid,CourseType ctypeTree,
			int ctid,  int cid, int pageNow, int pageSize,int role)
			throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();

		String x = Integer.toString(depid);
		String ids = createDepartmentId(depTree, depid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
										// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

		try {
			String usersql = "";
			if (elUser != null) {
				if (!(elUser.getSex().equals("")||elUser.getSex()==null)) {
					usersql+=" and eu.sex ='" + elUser.getSex() + "'" ;
				}
				if (!(elUser.getRealname().equals("")||elUser.getRealname()==null)) {
					usersql+= " and eu.realname like '%" + elUser.getRealname() + "%'";
				}
				if (!(elUser.getUsername().equals("")&&elUser.getUsername()==null)) {
					usersql+=" and eu.username like '%" + elUser.getUsername() + "%'";
				}
				
			}
			ct = DBConnection.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select * from (select t.*, rownum rn from (select eu.id,eu.username,eu.realname,eu.depid,dep.name ")
					.append(
							" depname,c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_,sqi.myScore," +
							"sqi.ispassed,ec.name as className,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ ,eu.sex from ")
					.append(
							" COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join")
					.append(
							" eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi")
					.append(
							" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid  where sc.courseid =? and eu.id not in (select userid from eluser_couser_order eco where eco.classid=0 and eco.courseid="+cid+") and  dep.id in ("
									+ ids + ") "+usersql+"  and sc.classid=0)t where rownum <= ? ) where rn>=?");
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")

			//System.out.println("::" + buffer.toString());
			ps = ct.prepareStatement(buffer.toString());
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed
			//ps.setString(1, "%" + name + "%");
			ps.setInt(1, cid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser eu = new ELUser(rs.getInt(1), rs.getString(3));
				eu.setUsername(rs.getString(2));
				eu.setDepartment(new Department(rs.getInt(4), rs.getString(5)));
				eu.setAge(rs.getInt(16));
				eu.setSex(rs.getString(17));
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
			logger.error("学员列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return myBxc;
	}
	public int course_user_list_BYCtypeCount( ELUser elUser ,Department depTree, int depid,CourseType ctypeTree, int ctid,
			 int cid,int role) throws ElException {// hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<MyCourse> myBxc = new ArrayList<MyCourse>();
		String x = Integer.toString(depid);
		String ids = createDepartmentId(depTree, depid);
		if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
										// ,当角色不为1时ids的只有一个根节点时也不截取
			ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
					: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id
		try {
			ct = DBConnection.getConnection();
			String usersql = "";
			if (elUser != null) {
				if (!(elUser.getSex().equals("")||elUser.getSex()==null)) {
					usersql+=" and eu.sex ='" + elUser.getSex() + "'" ;
				}
				if (!(elUser.getRealname().equals("")||elUser.getRealname()==null)) {
					usersql+= " and eu.realname like '%" + elUser.getRealname() + "%'";
				}
				if (!(elUser.getUsername().equals("")&&elUser.getUsername()==null)) {
					usersql+=" and eu.username like '%" + elUser.getUsername() + "%'";
				}
				
			}
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
							" on sqi.id=sc.sqiid where sc.courseid =? and eu.id not in (select userid from eluser_couser_order eco where eco.classid=0 and eco.courseid="+cid+") and   exists(select id from department) "+usersql+" and sc.classid=0)");

			ps = ct.prepareStatement(buffer.toString());
			// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")
			// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
			// ,c.credit,
			// c.during, sc.passtime/60
			// passtime,sc.process,sc.status,sc.mycredit,sqi.id
			// _sqiid,sqi.myScore,sqi.ispassed

			ps.setInt(1, cid);
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
	 * 添加一个课程分配订单，并且返回该订单ID
	 * @param userid
	 * @return
	 */
	public int markorder(int userid,int status,float price,int distribution,String  shoujianren,String tel,String note) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int orderid=0;
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("insert into sp_order(userid,orderdate,status,Distribution,sumpeice,shoujianren,tel,note) values(?,sysdate,?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, status);
			ps.setInt(3, distribution);
			ps.setFloat(4, price);
			ps.setString(5, shoujianren);
			ps.setString(6, tel);
			ps.setString(7, note);
			ps.executeUpdate();
			ps = ct
			.prepareStatement("select sp_order_sequence.currval from dual ");
			rs=ps.executeQuery();
			if(rs.next()){
				orderid=rs.getInt(1);
			}
			return orderid;
		} catch (Exception e) {
			logger.error("添加课程订单出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		
	}
	/**
	 * 添加一个课程分配订单，并且返回该订单ID
	 * @param userid
	 * @return
	 */
	public int markorder1(int orderid,int userid,int status,float price,int distribution,String  shoujianren,String tel,String note) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("insert into sp_order(userid,orderdate,status,Distribution,sumpeice,shoujianren,tel,note,id) values(?,sysdate,?,?,?,?,?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, status);
			ps.setInt(3, distribution);
			ps.setFloat(4, price);
			ps.setString(5, shoujianren);
			ps.setString(6, tel);
			ps.setString(7, note);
			ps.setInt(8, orderid);
			ps.executeUpdate();
			
			return orderid;
		} catch (Exception e) {
			logger.error("添加课程订单出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		
	}
	public void markorderInfo(int commodityid ,int orderid,int Commoditytype ,float price,int
			count ,int ifdelivery, int ifreceiv ) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("{call markorderInfo(?,?,?,?,?,?,?)}");
			ps.setInt(1, orderid);
			ps.setInt(2, commodityid);
			ps.setInt(3, Commoditytype);
			ps.setFloat(4, price);
			ps.setInt(5, count);
			ps.setFloat(6, ifdelivery);
			ps.setFloat(7, ifreceiv);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加订单详情出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	
	}
	public void addUserOrder(int userid, int courseid,int orderid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
		ps=ct.prepareStatement("call into_scorder(?,?,?)");
		ps.setInt(1, userid);
		ps.setInt(2, courseid);
		ps.setInt(3, orderid);
		ps.executeQuery();
		} catch (Exception e) {
			logger.error("添加订单人员关系出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public float getPeiceValue(int cid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		float price=0;
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select coursenowPrice from course_price where courseid=?");
			ps.setInt(1, cid);
			rs=ps.executeQuery();
			while(rs.next()){
				price=rs.getFloat(1);
				
			}
			return price;
		} catch (Exception e) {
			logger.error("获得课程价格出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
//	public List<MyCourse> getOrderUserList(int orderid, int pageNow, int pageSize) throws ElException{
		public List<MyCourse> getOrderUserList( int orderid, int pageNow, int pageSize)throws ElException {// hwc
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
								" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid  where  eu.id in (select userid from eluser_couser_order eco where eco.orderid=?)" +
								" and sc.courseid in (select courseid from eluser_couser_order eco where eco.orderid=?))t where rownum <= ? ) where rn>=?");
				// and ct.id in ("+createPerTypeId(ctypeTree,ctid)+")

				//System.out.println("::" + buffer.toString());
				ps = ct.prepareStatement(buffer.toString());
				// eu.id, eu.username,eu.realname,eu.depid,dep.name depname
				// ,c.credit,
				// c.during, sc.passtime/60
				// passtime,sc.process,sc.status,sc.mycredit,sqi.id
				// _sqiid,sqi.myScore,sqi.ispassed
				//ps.setString(1, "%" + name + "%");
				ps.setInt(1, orderid);
				ps.setInt(2, orderid);
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
				logger.error("学员列表出错！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return myBxc;
		}
	public int getOrderUserListCount(int orderid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int count=0;
		try {
			ct = DBConnection.getConnection();
					StringBuffer buffer = new StringBuffer();
			buffer
					.append(
							" select count(*) from  (select eu.id,eu.username,eu.realname,eu.depid,dep.name ")
					.append(
							" depname,c.credit,c.during,sc.passtime/60 passtime,sc.process,sc.status,sc.mycredit,sqi.id  sqiid_,sqi.myScore,sqi.ispassed,ec.name as className from ")
					.append(
							" COURSE c left join COURSE_TYPE ct on c.ctypeid = ct.id  left join study_course sc on sc.courseid = c.id left join")
					.append(
							" eluser eu on sc.userid = eu.id left join department dep on dep.id = eu.depid left join study_quizinfo sqi")
					.append(
							" on sqi.id=sc.sqiid left join elclass ec on ec.id=sc.classid  where  eu.id in (select userid from eluser_couser_order eco where eco.orderid=?)" +
							" and sc.courseid in (select courseid from eluser_couser_order eco where eco.orderid=?))");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, orderid);
			ps.setInt(2, orderid);
			rs=ps.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
			return count;
		} catch (Exception e) {
			logger.error("获得分页大小出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	
	public Course getCourseById(int id) throws ElException {
		Course c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select c.id,c.name,c.ctypeid,c.description,c.status,c.createtime," +
//					"c.creater,ct.name,u.realname,c.credit,c.mainimg,c.islink,c.exurl,c.during,c.querytime," +
//					"c.teacherinfo,c.studyplan,c.teachername,c.kj_appendix,c.jy_appendix,c.creditmod," +
//					"c.notenumber,c.notedate,c.roomid,c.roomstart,c.roomend,c.teacherid,c.scormid," +
//					"c.isApplication,c.courseForm ,cp.coursenowPrice,cp.courseoldPrice,c.LECTURERMAINIMG from course c,course_type ct,eluser u  ,course_price cp where " +
//					"c.ctypeid=ct.id and c.creater = u.id and c.id=cp.courseid and c.id = ?");
			ps = ct.prepareStatement("select c.id,c.name,c.ctypeid,c.description,c.status,c.createtime," +
					"c.creater,ct.name,u.realname,c.credit,c.mainimg,c.islink,c.exurl,c.during,c.querytime," +
					"c.teacherinfo,c.studyplan,c.teachername,c.kj_appendix,c.jy_appendix,c.creditmod," +
					"c.notenumber,c.notedate,c.roomid,c.roomstart,c.roomend,c.teacherid,c.scormid," +
					"c.isApplication,c.courseForm ,cp.coursenowPrice,cp.courseoldPrice,c.LECTURERMAINIMG from course c,course_type ct,eluser u  ,course_price cp where " +
					"c.ctypeid=ct.id and c.creater = u.id and c.id = ?");
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
				c.setRoom(new Rooms(rs.getInt(24)));
				c.setRoomstart(rs.getTimestamp(25));
				c.setRoomend(rs.getTimestamp(26));
				c.setTeacherId(rs.getInt(27));
				c.setScormId(rs.getString(28));
				c.setIsApplication(rs.getInt(29));
				c.setCourseForm(rs.getInt(30));
				Peice pe = new Peice();
				pe.setCoursenowPrice(rs.getFloat(31));
				pe.setCourseoldPrice(rs.getFloat(32));
				c.setLecturerMainimg(rs.getString(33));
				c.setPrice(pe);

			}
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return c;
	}
	private int getCpsize(int courseid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(id) from course_page where courseid = ?");
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
	public CourseOrder  getcourseOrderinfo(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		CourseOrder co =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select spo.id spoid,cs.name,cs.id,spoi.Price,spoi.count,spo.sumpeice,el.realname,spo.orderdate " +
					"from sp_order spo, sp_order_info spoi,course cs  ,eluser el where  spo.id=spoi.orderid " +
					" and  cs.id=spoi.commodityid  and  spo.id = ? and el.id=spo.userid ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				co = new CourseOrder();
				co.setId(rs.getInt(1));
				co.setPrice(rs.getFloat(4));
				co.setCount(rs.getInt(5));
				Course c= new Course();
				c.setName(rs.getString(2));
				c.setId(rs.getInt(3));
				co.setCourse(c);
				co.setZprice(rs.getFloat(6));
				ELUser e=new ELUser();
				e.setRealname(rs.getString(7));
				co.setElUser(e);
				co.setOdate(rs.getTimestamp(8));

				
			}
			return  co;
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public ClassOrder  getclassOrderinfo(int id) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ClassOrder co =null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select spo.id spoid,cs.name,cs.id,spoi.Price,spoi.count,spo.sumpeice,el.realname,spo.orderdate " +
					"from sp_order spo, sp_order_info spoi,elclass cs  ,eluser el where  spo.id=spoi.orderid " +
					" and  cs.id=spoi.commodityid  and  spo.id = ? and el.id=spo.userid ");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				co = new ClassOrder();
				co.setId(rs.getInt(1));
				co.setPrice(rs.getFloat(4));
				co.setCount(rs.getInt(5));
				ElClass c= new ElClass();
				c.setName(rs.getString(2));
				c.setId(rs.getInt(3));
				co.setElClass(c);
				co.setZprice(rs.getFloat(6));
				ELUser e=new ELUser();
				e.setRealname(rs.getString(7));
				co.setUser(e);
				co.setOdate(rs.getTimestamp(8));

				
			}
			return  co;
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public List<CourseOrder> getMyOrderCourseList(Department depTree, int depid,int userid,CourseOrder corder,Timestamp stime,Timestamp otime,int pageNow,int pageSize) throws ElException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseOrder> myOrder=new ArrayList<CourseOrder>();
		String str="";
		String ids = createDepartmentId(depTree, depid);
		if(corder!=null){
			if(!corder.getSid().equals("")){
				str+=" and spo.id like '%"+corder.getSid()+"%' ";
			}
			if(!corder.getCourse().getName().equals("")){
				str+=" and cs.name like '%"+corder.getCourse().getName()+"%'";
			}
			if(!corder.getSstatus().equals("")){
				str+="  and spo.status="+corder.getSstatus()+" ";
			}
			
		}
		if (stime != null && !"".equals(stime)){
			str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') > '"+stime+"'";
		}
		if (otime != null && !"".equals(otime)){
			str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') < '"+otime+"'";
			
		}
		String str1="";
		if(userid!=0){
			str1= " and  spo.userid="+userid+" ";
		}
		
		
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*, rownum rn from (select spo.id spoid,spo.Status, cs.name,cs.id,spoi.Price,spoi.count ," +
					"el.realname,spo.orderdate ,spo.sumpeice" +
					" from sp_order spo, sp_order_info spoi,course cs,department dep ,eluser el where  spo.id=spoi.orderid and dep.id = el.depid and el.id=spo.userid" +
					" and Distribution=0 and cs.id=spoi.commodityid "+str+" and  spoi.Commoditytype=1 "+str1+" and dep.id in ("+ids+") order by spo.orderdate desc )t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseOrder co = new CourseOrder();
				co.setId(rs.getInt(1));
				co.setStatus(rs.getInt(2));
				co.setPrice(rs.getFloat(5));
				co.setCount(rs.getInt(6));
				Course c= new Course();
				c.setName(rs.getString(3));
				c.setId(rs.getInt(4));
				co.setCourse(c);
				ELUser   e= new ELUser();
				e.setRealname(rs.getString(7));
				co.setElUser(e);
				co.setOdate(rs.getTimestamp(8));
				co.setZprice(rs.getFloat(9));
				myOrder.add(co);
				
				
			}
			return  myOrder;
		} catch (Exception e) {
			logger.error("读取课程失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public int getMyOrderCourseListCount(Department depTree, int depid,int userid, CourseOrder corder,
			Timestamp stime, Timestamp otime) throws ElException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String ids = createDepartmentId(depTree, depid);
		String str=" ";
		if(corder!=null){
			if(!corder.getSid().equals("")){
				str+=" and spo.id like '%"+corder.getSid()+"%' ";
			}
			if(!corder.getCourse().getName().equals("")){
				str+=" and cs.name like '%"+corder.getCourse().getName()+"%'";
			}
			if(!corder.getSstatus().equals("")){
				str+="  and spo.status="+corder.getSstatus()+" ";
			}
			
		}
		if (stime != null && !"".equals(stime)){
			str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') > '"+otime+"'";
		}
		if (otime != null && !"".equals(otime)){
			str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') < '"+otime+"'";
			
		}
		String str1="";
		if(userid!=0){
			str1= " and  spo.userid="+userid+" ";
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select count(*) from  (select spo.id spoid,spo.Status, cs.name,cs.id,spoi.Price,spoi.count " +
					"from sp_order spo, sp_order_info spoi,course cs ,department dep ,eluser el where  spo.id=spoi.orderid and dep.id = el.depid and el.id=spo.userid" +
					" and Distribution=0 and cs.id=spoi.commodityid "+str+" and  spoi.Commoditytype=1 "+str1+" and dep.id in ("+ids+"))");

			rs = ps.executeQuery();
			rs.next();
				
				
				
			
			return  rs.getInt(1);
		} catch (Exception e) {
			logger.error("读取分页大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public void deleOrderCourseClass(int orderid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str = "delete from study_course where study_course.courseid in " +
				"(select eco.courseid from eluser_couser_order eco where orderid =  ?   )" +
				" and study_course.userid  in (select eco.userid from eluser_couser_order eco " +
				" where orderid =  ? ) and study_course.classid in (select eco.classid from " +
				" eluser_couser_order eco where orderid =  ?)";
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(str);
			ps.setInt(1, orderid);
			ps.setInt(2, orderid);
			ps.setInt(3, orderid);
			ps.executeUpdate();
				
				

		} catch (Exception e) {
			logger.error("删除学员课程培训班关系表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public void deleOrderCourseClassOrder(int orderid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str = "delete from eluser_couser_order where orderid = ?" ;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(str);
			ps.setInt(1, orderid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除学员课程培训班订单关系表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public void deleOrder(int orderid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str = "delete from sp_order where id = ?" ;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(str);
			ps.setInt(1, orderid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除订单失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public void deleOrderInfo(int orderid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str = "delete from sp_order_info where orderid =?" ;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(str);
			ps.setInt(1, orderid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除订单信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser, Department depTree, int role)
			throws ElException {
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deptList = new ArrayList<Department>();
		List<ELUser> userList = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
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
			String ids = createDepartmentId(depTree, depid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
											// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer usersql = new StringBuffer();
			usersql
					.append(
							"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ from  ")
					.append(" eluser eu ")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where eu.id not in (select userid from eluser_couser_order eco where eco.classid=0 and eco.courseid="+courseid+" ) and  dp.id in ("
									+ ids + ") and dp.ID is not null");
			if (elUser != null) {
				if (!elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (!elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (!elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
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
				if (!elUser.getIsAssign().equals("")) {
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
			ELUser elUser, Department depTree, int role) throws ElException {
		List<ELUser> userList = new ArrayList<ELUser>();
		List<ELUser> returnList = new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;

		try {
			ct = DBConnection.getConnection();
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
			String ids = createDepartmentId(depTree, depid);
			if (role != 1 && !ids.equals(x))// 角色为1（超级管理员）时没有虚拟根节点，所以不需要截取
											// ,当角色不为1时ids的只有一个根节点时也不截取
				ids = depid == 1 ? ids.substring(x.length() + 1, ids.length())
						: ids; // 当id等于虚拟根时,从所有的id中去掉虚拟根id

			StringBuffer usersql = new StringBuffer();
			usersql
					.append(
							"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng from  ")
					.append(" eluser eu ")
					.append(
							" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where eu.id not in (select userid from eluser_couser_order eco where eco.classid=0 and eco.courseid="+courseid+" ) and  dp.id in ("
									+ ids + ") and dp.ID is not null");
			if (elUser != null) {
				if (!elUser.getSex().equals("")) {
					usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
				}
				if (!elUser.getRealname().equals("")) {
					usersql.append(" and eu.realname like '%"
							+ elUser.getRealname() + "%'");
				}
				if (!elUser.getUsername().equals("")) {
					usersql.append(" and eu.username like '%"
							+ elUser.getUsername() + "%'");
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
				if (!elUser.getIsAssign().equals("")) {
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
							" eluser u,department dep where c.ctypeid=ct.id and c.creater = u.id and c.id in (select courseid from course_price where course_price.status=1)")
					.append(" and u.depid=dep.id  and c.name like ?  ")
					// and c.status = ? 2012-1-3 需要显示所有课程
					.append(" and c.status = 5 ")
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
					.append(" and c.status = 5 ")
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
			logger.error("查询分页信息失败！", e);
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
		 * 根据考场条件搜索学员 
		 * @param dep
		 * @param cid
		 * @param eroomid
		 * @param elUser
		 * @param starttime
		 * @param endtime
		 * @param pageNow
		 * @param pageSize
		 * @return
		 * @throws ElException
		 */
		public List<ELUser> gettoEroomInfoselectUser(Department depTree,Department dep,String table , int tid, int eroomid, ELUser elUser, String starttime,String endtime,int pageNow, int pageSize) throws ElException {
			List<ELUser> returnList = new ArrayList<ELUser>();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Department> deptList = new ArrayList<Department>();
			List<ELUser> userList = new ArrayList<ELUser>();
			
			String LidRid = " and ";
			String depids = "";
			int x = 1;
			try {
				ct = DBConnection.getConnection();   
				Department dept = new Department();   
				if(dep.getId() == -2){ 
					for(int i = 0;i < depTree.getChild().size();i++){
						if(depids.equals("")){
							depids = depids + depTree.getChild().get(i).getId(); 
						}else{
							depids = depids + "," + depTree.getChild().get(i).getId(); 
						}
					} 
					ps = ct.prepareStatement("select lid,rid from DEPARTMENT where id in ("+depids+")"); 
					rs = ps.executeQuery();
					while(rs.next()){  
						if(depTree.getChild().size() > 1 && depTree.getChild().size() != x && x > 1){// 中间不用加			
							LidRid = LidRid + " or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
						}else if (depTree.getChild().size() == x){//结束前面加     ） 
							LidRid = LidRid + "  or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+"))"; 
						}else{//开始前面加   （			
							LidRid = LidRid + "  ( (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
						}
						x++;
					}  
				}else{  
					// 获取 部门的左右值
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
					ps.setInt(1, dep.getId());
					rs = ps.executeQuery();
					if (rs.next()) {
						dept.setId(rs.getInt(1));
						dept.setLid(rs.getInt(2));
						dept.setRid(rs.getInt(3));
						LidRid = LidRid + " dp.lid>="+rs.getInt(2)+" and dp.rid<= " +rs.getInt(3); 
					}
				}
				ps.close(); 
				
				String userWhere = "";  
				if (elUser != null) {  
					 if(elUser.getIsKcPass() != null && !elUser.getIsKcPass().equals("")){//通过条件
						if(elUser.getIsKcPass().equals("0"))//通过
							userWhere = userWhere +" and eu.id in(select ca.userid from study_room ca where ca.roomid ="+ eroomid + " and ispassed != 0)";
						else if(elUser.getIsKcPass().equals("1"))//不通过
							userWhere = userWhere +" and eu.id in(select ca.userid from study_room ca where ca.roomid ="+ eroomid + " and ispassed = 0)";
						else
							userWhere = userWhere +" and eu.id in(select ca.userid from study_room ca where ca.roomid ="+ eroomid + " )";
					}
					if(elUser.getKcBtotalscore() != -1 && elUser.getKcBtotalscore() != 0 ){ //总分条件
						userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + "  and  (select sum(myscore) from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id  ) >= "+elUser.getKcBtotalscore()+")  ";
					} 
					if(elUser.getKcBtotalscore_() != -1 && elUser.getKcBtotalscore_() != 0 ){ 
						userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + "  and  (select sum(myscore) from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id  ) <= "+elUser.getKcBtotalscore_()+")  ";
					} 
					if(elUser.getKcsq() != null){
						for(int i = 0 ;i< elUser.getKcsq().size() ; i++){ //试卷条件
							if(!elUser.getKcsq().get(i).equals("不限") && !elUser.getKcsq().get(i).equals("")){
								userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + "  and  (select sum(myscore) from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id and epid = "+elUser.getEpids().get(i)+" ) >= "+elUser.getKcsq().get(i)+") ";
							}
							if(!elUser.getKcsq_().get(i).equals("不限") && !elUser.getKcsq_().get(i).equals("")){
								userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + "  and  (select sum(myscore) from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id and epid = "+elUser.getEpids().get(i)+" ) <= "+elUser.getKcsq_().get(i)+") ";
							}
						} 
					}
					if(elUser.getKclxcs() != null){
						for(int i = 0 ;i< elUser.getKclxcs().size() ; i++){ //练习次数条件
							if(!elUser.getKclxcs().get(i).equals("不限") && !elUser.getKclxcs().get(i).equals("")){
								userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id and  myexamcount >= "+elUser.getKclxcs().get(i)+" ) ";
							}
							if(!elUser.getKclxcs_().get(i).equals("不限") && !elUser.getKclxcs_().get(i).equals("")){
								userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id and  myexamcount <= "+elUser.getKclxcs().get(i)+" ) "; 
							}
						}  
					}
				}	
				String sql = "select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ " +
						" from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null " +
						" "+LidRid +userWhere+
						" )t where rownum<=? ) where rn>=?";
				   
				ps = ct.prepareStatement(sql);  
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize); 
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
					user.setIsAssign("未分配");
					user.setAge(rs.getInt(11)); 
					userList.add(user);  
				}
				rs.close();
				
				ps = ct
				.prepareStatement("select ca.userid,ca.joinway from "+table+" ca where ca.classid in ("+tid+")"); 
				rs = ps.executeQuery();
				while (rs.next()) {
					for (ELUser users : userList) {
						if (users.getId() == rs.getInt(1)) {
							users.setIsAssign("已分配"); 
							users.setJoinway(rs.getInt("joinway")== 0?"分配":"申请"); 
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
		/**
		 * 根据考场条件搜索学员Size 
		 * @param dep
		 * @param cid
		 * @param eroomid
		 * @param elUser
		 * @param starttime
		 * @param endtime
		 * @param pageNow
		 * @param pageSize
		 * @return
		 * @throws ElException
		 */
		public int gettoEroomInfoselectUserSize(Department depTree,Department dep,String table ,int tid, int eroomid, ELUser elUser, String starttime,String endtime) throws ElException {
			List<ELUser> returnList = new ArrayList<ELUser>();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Department> deptList = new ArrayList<Department>();
			List<ELUser> userList = new ArrayList<ELUser>();
			String LidRid = " and ";
			String depids = "";
			int x = 1;
			try {
				ct = DBConnection.getConnection();   
				Department dept = new Department();   
				if(dep.getId() == -2){ 
					for(int i = 0;i < depTree.getChild().size();i++){
						if(depids.equals("")){
							depids = depids + depTree.getChild().get(i).getId(); 
						}else{
							depids = depids + "," + depTree.getChild().get(i).getId(); 
						}
					} 
					ps = ct.prepareStatement("select lid,rid from DEPARTMENT where id in ("+depids+")"); 
					rs = ps.executeQuery();
					while(rs.next()){  
						if(depTree.getChild().size() > 1 && depTree.getChild().size() != x && x > 1){// 中间不用加			
							LidRid = LidRid + " or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
						}else if (depTree.getChild().size() == x){//结束前面加     ） 
							LidRid = LidRid + "  or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+"))"; 
						}else{//开始前面加   （			
							LidRid = LidRid + "  ( (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
						}
						x++;
					}  
				}else{  
					// 获取 部门的左右值
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
					ps.setInt(1, dep.getId());
					rs = ps.executeQuery();
					if (rs.next()) {
						dept.setId(rs.getInt(1));
						dept.setLid(rs.getInt(2));
						dept.setRid(rs.getInt(3));
						LidRid = LidRid + " dp.lid>="+rs.getInt(2)+" and dp.rid<= " +rs.getInt(3); 
					}
				}
				ps.close(); 
				
				String userWhere = "";  
				if (elUser != null) {  
					 if(elUser.getIsKcPass() != null && !elUser.getIsKcPass().equals("")){//通过条件
						if(elUser.getIsKcPass().equals("0"))//通过
							userWhere = userWhere +" and eu.id in(select ca.userid from study_room ca where ca.roomid ="+ eroomid + " and ispassed != 0)";
						else if(elUser.getIsKcPass().equals("1"))//不通过
							userWhere = userWhere +" and eu.id in(select ca.userid from study_room ca where ca.roomid ="+ eroomid + " and ispassed = 0)";
						else
							userWhere = userWhere +" and eu.id in(select ca.userid from study_room ca where ca.roomid ="+ eroomid + " )";
					}
					if(elUser.getKcBtotalscore() != -1 && elUser.getKcBtotalscore() != 0  ){ //总分条件
						userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + "  and  (select sum(myscore) from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id  ) >= "+elUser.getKcBtotalscore()+")  ";
					} 
					if(elUser.getKcBtotalscore_() != -1 && elUser.getKcBtotalscore_() != 0 ){ 
						userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + "  and  (select sum(myscore) from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id  ) <= "+elUser.getKcBtotalscore_()+")  ";
					} 
					if(elUser.getKcsq() != null){
						for(int i = 0 ;i< elUser.getKcsq().size() ; i++){ //试卷条件
							if(!elUser.getKcsq().get(i).equals("不限") && !elUser.getKcsq().get(i).equals("")){
								userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + "  and  (select sum(myscore) from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id and epid = "+elUser.getEpids().get(i)+" ) >= "+elUser.getKcsq().get(i)+") ";
							}
							if(!elUser.getKcsq_().get(i).equals("不限") && !elUser.getKcsq_().get(i).equals("")){
								userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + "  and  (select sum(myscore) from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id and epid = "+elUser.getEpids().get(i)+" ) <= "+elUser.getKcsq_().get(i)+") ";
							}
						} 
					}
					if(elUser.getKclxcs() != null){
						for(int i = 0 ;i< elUser.getKclxcs().size() ; i++){ //练习次数条件
							if(!elUser.getKclxcs().get(i).equals("不限") && !elUser.getKclxcs().get(i).equals("")){
								userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id and  myexamcount >= "+elUser.getKclxcs().get(i)+" ) ";
							}
							if(!elUser.getKclxcs_().get(i).equals("不限") && !elUser.getKclxcs_().get(i).equals("")){
								userWhere = userWhere +" and eu.id in (select userid from study_quizinfo where roomid = "+ eroomid + " and userid = eu.id and  myexamcount <= "+elUser.getKclxcs().get(i)+" ) "; 
							}
						}  
					}
				}	
				String sql = "select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ " +
						" from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null " +
						" "+LidRid +userWhere;
				   
				ps = ct.prepareStatement(sql);  
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
						user.setIsAssign("未分配");
						user.setAge(rs.getInt(11)); 
						userList.add(user); 
				}
				rs.close();
				
				ps = ct
				.prepareStatement("select ca.userid,ca.joinway from "+table+" ca where ca.classid in ("+tid+")"); 
				rs = ps.executeQuery();
				while (rs.next()) {
					for (ELUser users : userList) {
						if (users.getId() == rs.getInt(1)) {
							users.setIsAssign("已分配"); 
							users.setJoinway(rs.getInt("joinway")== 0?"分配":"申请"); 
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
					return userList.size();
				}
			} catch (Exception e) {
				logger.error("分配学员失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return userList.size();
		}
		/**
		 * 根据培训班条件搜索学员 
		 * @param dep
		 * @param table(study_room    study_class)
		 * @param tid
		 * @param classid
		 * @param elUser
		 * @param starttime
		 * @param endtime
		 * @param pageNow
		 * @param pageSize
		 * @return
		 * @throws ElException
		 */
		public List<ELUser> gettoClassInfoselectUser(Department depTree,Department dep,String table ,int tid, int classid,ELUser elUser, String starttime,String endtime,int pageNow, int pageSize) throws ElException {
			List<ELUser> returnList = new ArrayList<ELUser>();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Department> deptList = new ArrayList<Department>();
			List<ELUser> userList = new ArrayList<ELUser>();		
			String LidRid = " and ";
			String depids = "";
			int x = 1;
			try {
				ct = DBConnection.getConnection();   
				Department dept = new Department();   
				if(dep.getId() == -2){ 
					for(int i = 0;i < depTree.getChild().size();i++){
						if(depids.equals("")){
							depids = depids + depTree.getChild().get(i).getId(); 
						}else{
							depids = depids + "," + depTree.getChild().get(i).getId(); 
						}
					} 
					ps = ct.prepareStatement("select lid,rid from DEPARTMENT where id in ("+depids+")"); 
					rs = ps.executeQuery();
					while(rs.next()){  
						if(depTree.getChild().size() > 1 && depTree.getChild().size() != x && x > 1){// 中间不用加			
							LidRid = LidRid + " or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
						}else if (depTree.getChild().size() == x){//结束前面加     ） 
							LidRid = LidRid + "  or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+"))"; 
						}else{//开始前面加   （			
							LidRid = LidRid + "  ( (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
						}
						x++;
					}  
				}else{  
					// 获取 部门的左右值
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
					ps.setInt(1, dep.getId());
					rs = ps.executeQuery();
					if (rs.next()) {
						dept.setId(rs.getInt(1));
						dept.setLid(rs.getInt(2));
						dept.setRid(rs.getInt(3));
						LidRid = LidRid + " dp.lid>="+rs.getInt(2)+" and dp.rid<= " +rs.getInt(3); 
					}
				}
				ps.close();
				rs.close(); 
				
				String userWhere = "";  
				if (elUser != null) {  
					 if(elUser.getIsPass() != null && !elUser.getIsPass().equals("")){
						if(elUser.getIsPass().equals("0"))//通过
							userWhere = userWhere +" and eu.id in(select ca.userid from study_class ca where ca.classid ="+ classid + " and certificateno is not null)";
						else if(elUser.getIsPass().equals("1"))//不通过
							userWhere = userWhere +" and eu.id in(select ca.userid from study_class ca where ca.classid ="+ classid + " and certificateno is null)";
						else
							userWhere = userWhere +" and eu.id in(select ca.userid from study_class ca where ca.classid ="+ classid + " )";
					}
				}	
				String sql = "select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ " +
						" from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null " +
						" " +LidRid+userWhere;
				   
				ps = ct.prepareStatement(sql);   
				rs = ps.executeQuery();

				String userids = "";//获取符合要求的用户id
				String userSql = "";//用户sql 
				while (rs.next()) { 
					boolean isZF = false;
					boolean isBX = false;
					boolean isXX = false; 
					//总学分
					if(elUser.getBtotalscore() != 0  && elUser.getBtotalscore_() != 0 ){
						int Btotalscore = classStudentScore2(classid, rs.getInt(1), 2);
						if(elUser.getBtotalscore()<= Btotalscore && Btotalscore <= elUser.getBtotalscore_()){
							isZF = true;
						}
					}else{isZF = true;}
					//必修课总学分
					if(elUser.getBxscore() != 0  && elUser.getBxscore_() != 0 ){
						int Bxscore = classStudentScore2(classid, rs.getInt(1), 0);
						if(elUser.getBxscore()<= Bxscore && Bxscore <= elUser.getBxscore_()){
							isBX = true;
						}
					}else{isBX = true;}
					//选修课总学分
					if(elUser.getXxscore() != 0  && elUser.getXxscore_() != 0 ){
						int Xxscore = classStudentScore2(classid, rs.getInt(1), 1);
						if(elUser.getXxscore()<= Xxscore && Xxscore <= elUser.getXxscore_()){
							isXX = true;
						}
					}else{isXX = true;}
					if(isZF&&isBX&&isXX){ 
						if(userids.equals(""))
							userids = userids + rs.getInt(1);
						else
							userids = userids +","+ rs.getInt(1);  
					}  
				}
				rs.close();
				if(!userids.equals("")){
					userSql = " and eu.id in("+userids+")";
				}else{
					userSql = " and eu.id in null";
				}

				String sqls = "select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ " +
						" from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null " +
						" " +LidRid+userWhere+userSql+
						" and eu.id not in (select userid from  eluser_couser_order eco where eco.classid="+classid+" ))t where rownum<=? ) where rn>=?";

				ps = ct.prepareStatement(sqls);  
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize); 
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
						user.setIsAssign("未分配");
						user.setAge(rs.getInt(11)); 
						userList.add(user);  
				}
				rs.close();
				
				ps = ct
				.prepareStatement("select ca.userid,ca.joinway from "+table+" ca where ca.classid in ("+tid+")"); //study_room    study_class
				rs = ps.executeQuery();
				while (rs.next()) {
					for (ELUser users : userList) {
						if (users.getId() == rs.getInt(1)) {
							users.setIsAssign("已分配"); 
							users.setJoinway(rs.getInt("joinway")== 0?"分配":"申请"); 
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
		
		/**
		 * 根据培训班条件搜索学员 
		 * @param dep
		 * @param classid
		 * @param elUser
		 * @param starttime
		 * @param endtime
		 * @param pageNow
		 * @param pageSize
		 * @return
		 * @throws ElException
		 */
		public int gettoClassInfoselectUserSize(Department depTree,Department dep,String table ,int tid, int classid,ELUser elUser, String starttime,String endtime) throws ElException {
			List<ELUser> returnList = new ArrayList<ELUser>();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Department> deptList = new ArrayList<Department>();
			List<ELUser> userList = new ArrayList<ELUser>();
			
			String LidRid = " and ";
			String depids = "";
			int x = 1;
			try {
				ct = DBConnection.getConnection();   
				Department dept = new Department();   
				if(dep.getId() == -2){ 
					for(int i = 0;i < depTree.getChild().size();i++){
						if(depids.equals("")){
							depids = depids + depTree.getChild().get(i).getId(); 
						}else{
							depids = depids + "," + depTree.getChild().get(i).getId(); 
						}
					} 
					ps = ct.prepareStatement("select lid,rid from DEPARTMENT where id in ("+depids+")"); 
					rs = ps.executeQuery();
					while(rs.next()){  
						if(depTree.getChild().size() > 1 && depTree.getChild().size() != x && x > 1){// 中间不用加			
							LidRid = LidRid + " or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
						}else if (depTree.getChild().size() == x){//结束前面加     ） 
							LidRid = LidRid + "  or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+"))"; 
						}else{//开始前面加   （			
							LidRid = LidRid + "  ( (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
						}
						x++;
					}  
				}else{  
					// 获取 部门的左右值
					ps = ct.prepareStatement(ElQuerySql
							.getSQL(DUConstants.DEP_QUERY_LRID_BYID));
					ps.setInt(1, dep.getId());
					rs = ps.executeQuery();
					if (rs.next()) {
						dept.setId(rs.getInt(1));
						dept.setLid(rs.getInt(2));
						dept.setRid(rs.getInt(3));
						LidRid = LidRid + " dp.lid>="+rs.getInt(2)+" and dp.rid<= " +rs.getInt(3); 
					}
				}
				ps.close();
				rs.close(); 
				
				String userWhere = "";  
				if (elUser != null) {  
					 if(elUser.getIsPass() != null && !elUser.getIsPass().equals("")){
						if(elUser.getIsPass().equals("0"))//通过
							userWhere = userWhere +" and eu.id in(select ca.userid from study_class ca where ca.classid ="+ classid + " and certificateno is not null)";
						else if(elUser.getIsPass().equals("1"))//不通过
							userWhere = userWhere +" and eu.id in(select ca.userid from study_class ca where ca.classid ="+ classid + " and certificateno is null)";
						else
							userWhere = userWhere +" and eu.id in(select ca.userid from study_class ca where ca.classid ="+ classid + " )";
					}
				}	
				String sql = "select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ " +
						" from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null " +
						" "+LidRid +userWhere ;
				   
				ps = ct.prepareStatement(sql);  
				rs = ps.executeQuery();

				String userids = "";//获取符合要求的用户id
				String userSql = "";//用户sql
				while (rs.next()) { 
					boolean isZF = false;
					boolean isBX = false;
					boolean isXX = false;
					//总学分
					if(elUser.getBtotalscore() != 0  && elUser.getBtotalscore_() != 0 ){
						int Btotalscore = classStudentScore2(classid, rs.getInt(1), 2);
						if(elUser.getBtotalscore()< Btotalscore && Btotalscore < elUser.getBtotalscore_()){
							isZF = true;
						}
					}else{isZF = true;}
					//必修课总学分
					if(elUser.getBxscore() != 0  && elUser.getBxscore_() != 0 ){
						int Bxscore = classStudentScore2(classid, rs.getInt(1), 0);
						if(elUser.getBxscore()< Bxscore && Bxscore < elUser.getBxscore_()){
							isBX = true;
						}
					}else{isBX = true;}
					//选修课总学分
					if(elUser.getXxscore() != 0  && elUser.getXxscore_() != 0 ){
						int Xxscore = classStudentScore2(classid, rs.getInt(1), 1);
						if(elUser.getXxscore()< Xxscore && Xxscore < elUser.getXxscore_()){
							isXX = true;
						}
					}else{isXX = true;}
					if(isZF&&isBX&&isXX){ 
						if(userids.equals(""))
							userids = userids + rs.getInt(1);
						else
							userids = userids +","+ rs.getInt(1);  
					} 
				}
				rs.close();
				if(!userids.equals("")){
					userSql = " and eu.id in("+userids+")";
				}else{
					userSql = " and eu.id in null";
				}

				String sqls = "select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_ " +
						" from eluser eu left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null " +
						" and eu.id not in (select userid from  eluser_couser_order eco where eco.classid="+classid+" ) "+LidRid +userWhere+userSql ;

				ps = ct.prepareStatement(sqls);   
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
						user.setIsAssign("未分配");
						user.setAge(rs.getInt(11)); 
						userList.add(user);  
				}
				rs.close();
				
				ps = ct
				.prepareStatement("select ca.userid,ca.joinway from "+table+" ca where ca.classid in ("+tid+")"); 
				rs = ps.executeQuery();
				while (rs.next()) {
					for (ELUser users : userList) {
						if (users.getId() == rs.getInt(1)) {
							users.setIsAssign("已分配"); 
							users.setJoinway(rs.getInt("joinway")== 0?"分配":"申请"); 
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
					return userList.size();
				}
			} catch (Exception e) {
				logger.error("分配学员失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return userList.size();
		}
		/**
		 * 获取学员学分（0 必修  1选修  2 必修+选修）
		 */
		public int classStudentScore2(int classid, int userid, int t)
		throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			int creditSum=0;
			try {
				ct = DBConnection.getConnection();
				String ts = "";
				if(t != 2){// 2 = 必修+选修
					ts = " and status = "+ t;
				}
				//1.先得到该培训班的所有必修（选修）课程，得到其结业方式
				ps = ct.prepareStatement("select courseid,getcredit,setcredit from class_course where classid =? "+ts);
				ps.setInt(1, classid); 
				rs = ps.executeQuery();
				int courseid=0;
				int getcredit=0;//结业方式
				int setcredit=0;//学分
				while(rs.next()) {
					courseid=rs.getInt("courseid");
					getcredit=rs.getInt("getcredit");
					setcredit=rs.getInt("setcredit");
					//判断是否通过，如果通过获取getcredit学分
					if(this.classStudentIsPass(classid,userid,courseid,getcredit)){
						creditSum+=setcredit;
					}
				}

			} catch (Exception e) {
				ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET, 
				"获取学员学分出错!失败方法： 失败原因："+new ElException(e));
				logger.error("获取学员学分出错！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return creditSum;
		}
		public boolean classStudentIsPass(int classid, int userid,int courseid,int getcredit)
		throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			try {
				String sql="";
				if(getcredit==1){
					//学完
					sql="select sc.passed from  study_course sc where sc.userid =? and sc.classid=? and sc.passed=1 and courseid=? ";
				}else if(getcredit==2){
					//考过
					sql="select sqi.ispassed from study_course sc left join study_quizinfo sqi on sc.sqiid=sqi.id where sc.userid =? and sqi.classid=? and sc.courseid=? and sqi.ispassed=1";
				}else{
					//学完且考过
					sql="select sc.passed from study_course sc left join study_quizinfo sqi on sc.sqiid=sqi.id where sc.userid =? and sc.classid=? and sc.courseid=? and sqi.ispassed=1 and sc.passed=1";
				}
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
				ps.setInt(2, classid);
				ps.setInt(3, courseid);
				rs = ps.executeQuery();
				if(rs.next()) {
					return true;
				}
			} catch (Exception e) {
				ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
						ElLoggerConstants.LOG_MOD_CLASS, ElLoggerConstants.LOG_TYPE_GET, 
				"我的课程列表出错!失败方法：classStudentIsPass(int classid, int userid,int courseid,int getcredit) 失败原因："+new ElException(e));
				logger.error("我的课程列表出错！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return false;
		}
		public int getSessionValue(String key) {
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();
			return  Integer.parseInt(session.getAttribute(key).toString());
		}
		/**
		 * 查看培训班学员
		 * @param pageNow
		 * @param pageSize
		 * @param depid
		 * @param classid
		 * @param state
		 * @param userid
		 * @param starttime
		 * @param endtime
		 * @param elUser
		 * @param sub_department
		 * @param depTree
		 * @return
		 * @throws ElException
		 */
		public List<ELUser> listAssignedUser(int pageNow, int pageSize, int depid,
				int classid, int state, List<Integer> userid, String starttime,
				String endtime, ELUser elUser,int sub_department,Department depTree) throws ElException {
			List<ELUser> returnList = new ArrayList<ELUser>();
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Department> deptList = new ArrayList<Department>();
			List<ELUser> userList = new ArrayList<ELUser>();
			String LidRid = " and ";
			String depids = "";
			int x = 1;
			try {
				ct = DBConnection.getConnection(); 
				Department dept = new Department();   
				if(depid == -2){ 
					for(int i = 0;i < depTree.getChild().size();i++){
						if(depids.equals("")){
							depids = depids + depTree.getChild().get(i).getId(); 
						}else{
							depids = depids + "," + depTree.getChild().get(i).getId(); 
						}
					} 
					ps = ct.prepareStatement("select lid,rid from DEPARTMENT where id in ("+depids+")"); 
					rs = ps.executeQuery();
					while(rs.next()){  
						if(depTree.getChild().size() == 1){
							LidRid = LidRid + "  dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2); 
						}else{
							if(depTree.getChild().size() > 1 && depTree.getChild().size() != x && x > 1){// 中间不用加			
								LidRid = LidRid + " or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
							}else if (depTree.getChild().size() == x){//结束前面加     ） 
								LidRid = LidRid + "  or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+"))"; 
							}else{//开始前面加   （			
								LidRid = LidRid + "  ( (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
							}
						}
						x++;
					}  
				}else{ 
					ps = ct.prepareStatement("select * from DEPARTMENT where id=?");
					ps.setInt(1, depid);
					rs = ps.executeQuery();
					rs.next(); 
					dept.setId(rs.getInt(1));
					dept.setName(rs.getString(2));
					ElNode node = new ElNode(rs.getInt(4));
					dept.setParent(node);
					dept.setLid(rs.getInt("lid"));
					dept.setRid(rs.getInt("rid"));
					LidRid = LidRid + " and dp.lid>="+rs.getInt("lid")+" and dp.rid<= " +rs.getInt("rid"); 
					deptList.add(dept);    
				}
				
				StringBuffer usersql = new StringBuffer();
				usersql
				.append(
						"select * from(select t.*,rownum rn from ( select eu.id userid,eu.realname username,dp.id deptid,dp.name,eu.username deptname,eu.jingzhong,role.id,role.name rolename,eu.sex,eu.shenfenzheng,nvl( floor(to_char(sysdate,'yyyy' ))-floor(to_char(shengri,'yyyy')),-1) age_  from  ")
						.append(" eluser eu ")
						.append(
						" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null "); 
				if(sub_department == 1){
					if(depid == -2){ 
						usersql.append(LidRid);
					}else{
						usersql.append(" and dp.lid>="+dept.getLid()+" and dp.rid<= " +dept.getRid() );
					}
				}else{
					if(depid == -2){ 
						usersql.append(LidRid);
					}else{							
						usersql.append(" and dp.id="+depid );
					}
				}			
				if (elUser != null) {
					if (elUser.getSex() != null && !elUser.getSex().equals("")) {
						usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
					}
					if (elUser.getRealname() != null && !elUser.getRealname().equals("")) {
						usersql.append(" and eu.realname like '%"
								+ elUser.getRealname() + "%'");
					}
					if (elUser.getUsername() != null && !elUser.getUsername().equals("")) {
						usersql.append(" and eu.username like '%"+ elUser.getUsername() + "%'");
					}
//					if (null != elUser.getJingzhong() && !elUser.getJingzhong().equals("")&& !elUser.getJingzhong().equals("0")) {
//						usersql.append(" and eu.jingzhong = '"+elUser.getJingzhong().trim()+"'");
//					}
//					if (null != elUser.getDishi() && !elUser.getDishi().equals("")&& !elUser.getDishi().equals("0")){
//						usersql.append("  and eu.dishi = '"+elUser.getDishi().trim()+"' "); 
//					}
//					if (null != elUser.getZhiji() && !elUser.getZhiji().equals("")&& !elUser.getZhiji().equals("0")){
//						usersql.append("  and eu.zhiji = '"+elUser.getZhiji().trim()+"' ");
//					}
//					if (null != elUser.getZhiwu() && !elUser.getZhiwu().equals("")&& !elUser.getZhiwu().equals("0")){
//						usersql.append("  and eu.zhiwu = '"+elUser.getZhiwu().trim()+"' "); 
//					}
					if (elUser.getJingzhong()>0) {
						usersql.append(" and eu.jingzhong = '"+elUser.getJingzhong()+"'");
					}
					if (elUser.getDishi()>0){
						usersql.append("  and eu.dishi = '"+elUser.getDishi()+"' "); 
					}
					if (elUser.getZhiji()>0){
						usersql.append("  and eu.zhiji = '"+elUser.getZhiji()+"' ");
					}
					if (elUser.getZhiwu()>0){
						usersql.append("  and eu.zhiwu = '"+elUser.getZhiwu()+"' "); 
					}
					if (null != elUser.getGangwei() && !elUser.getGangwei().equals("")&& !elUser.getGangwei().equals("0")){
						usersql.append("  and eu.gangwei = '"+elUser.getGangwei().trim()+"' ");
					}	
					if (elUser.getIsAssign() != null && !elUser.getIsAssign().equals("-1")) {
						if (elUser.getIsAssign().equals("0")) {
							usersql
							.append(" and eu.id in(select ca.userid from study_class ca where ca.classid="
									+ classid + ") and eu.id not in(select eco.userid from eluser_couser_order eco where eco.classid="
									+ classid + ") ");
						} else if (elUser.getIsAssign().equals("1")) {
							usersql
							.append(" and eu.id  in(select eco.userid from eluser_couser_order eco where eco.classid="
									+ classid + ") ");
						}
						 else if (elUser.getIsAssign().equals("2")) {
								usersql
								.append(" and eu.id not in(select ca.userid from study_class ca where ca.classid="
									+ classid + ") ");
							}
					}
				}
				usersql.append(" )t where rownum<=? ) where rn>=?");
				ps = ct.prepareStatement(usersql.toString()); 
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize); 
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
					user.setIsAssign("未分配");
					user.setAge(rs.getInt(11));
					userList.add(user);
				}
				ps.close();
				rs.close();
				ps = ct
				.prepareStatement("select ca.userid,ca.joinway from study_class ca where ca.classid=? and " +
						" ca.userid in (select ca.userid from study_class ca where ca.classid="+classid+")");
				ps.setInt(1, classid);
				rs = ps.executeQuery();
				while (rs.next()) {
					for (ELUser users : userList) {
						if (users.getId() == rs.getInt(1)) {
							users.setIsAssign("待订购"); 
							users.setJoinway(rs.getInt("joinway")== 0?"分配":"申请"); 
						}
					}
				}
				ps.close();
				rs.close();
				ps = ct
				.prepareStatement("select ca.userid,ca.joinway from study_class ca where ca.classid=? and" +
						" ca.userid in (select eco.userid from eluser_couser_order eco where eco.classid="
										+ classid + ") ");
				ps.setInt(1, classid);
				rs = ps.executeQuery();
				while (rs.next()) {
					for (ELUser users : userList) {
						if (users.getId() == rs.getInt(1)) {
							users.setIsAssign("已订购"); 
							users.setJoinway(rs.getInt("joinway")== 0?"分配":"申请"); 
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
		public int listAssignedUserSize(int depid, int classid, int state,
				List<Integer> userid, String starttime, String endtime,
				ELUser elUser,int sub_department,Department depTree) throws ElException { 
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Department> deptList = new ArrayList<Department>(); 
			String LidRid = " and ";
			String depids = "";
			int Rsize = 0;
			int x = 1;
			try {
				ct = DBConnection.getConnection(); 
				Department dept = new Department();   
				if(depid == -2){ 
					for(int i = 0;i < depTree.getChild().size();i++){
						if(depids.equals("")){
							depids = depids + depTree.getChild().get(i).getId(); 
						}else{
							depids = depids + "," + depTree.getChild().get(i).getId(); 
						}
					}
					ps = ct.prepareStatement("select lid,rid from DEPARTMENT where id in ("+depids+")"); 
					rs = ps.executeQuery();
					while(rs.next()){  
						if(depTree.getChild().size() == 1){
							LidRid = LidRid + "  dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2); 
						}else{
							if(depTree.getChild().size() > 1 && depTree.getChild().size() != x && x > 1){// 中间不用加			
								LidRid = LidRid + " or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
							}else if (depTree.getChild().size() == x){//结束前面加     ） 
								LidRid = LidRid + "  or (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+"))"; 
							}else{//开始前面加   （			
								LidRid = LidRid + "  ( (dp.lid >= "+rs.getInt(1)+" and  dp.rid <= " +rs.getInt(2)+")"; 
							}
						}
						x++;
					}  
				}else{ 
					ps = ct.prepareStatement("select * from DEPARTMENT where id=?");
					ps.setInt(1, depid);
					rs = ps.executeQuery();
					rs.next(); 
					dept.setId(rs.getInt(1));
					dept.setName(rs.getString(2));
					ElNode node = new ElNode(rs.getInt(4));
					dept.setParent(node);
					dept.setLid(rs.getInt("lid"));
					dept.setRid(rs.getInt("rid"));
					LidRid = LidRid + " and dp.lid>="+rs.getInt("lid")+" and dp.rid<= " +rs.getInt("rid"); 
					deptList.add(dept);    
				}

				StringBuffer usersql = new StringBuffer();
				usersql
				.append(
						"select count(eu.id)   from  ")
						.append(" eluser eu ")
						.append(
						" left join DEPARTMENT dp on eu.depid=dp.id left join ELROLE role on eu.role=role.id where dp.ID is not null "); 
						if(sub_department == 1){
							if(depid == -2){ 
								usersql.append(LidRid);
							}else{
								usersql.append(" and dp.lid>="+dept.getLid()+" and dp.rid<= " +dept.getRid() );
							}
						}else{
							if(depid == -2){ 
								usersql.append(LidRid);
							}else{							
								usersql.append(" and dp.id="+depid );
							}
						}		
				if (elUser != null) {
					if (elUser.getSex() != null && !elUser.getSex().equals("")) {
						usersql.append(" and eu.sex ='" + elUser.getSex() + "'");
					}
					if (elUser.getRealname() != null && !elUser.getRealname().equals("")) {
						usersql.append(" and eu.realname like '%"
								+ elUser.getRealname() + "%'");
					}
					if (elUser.getUsername() != null && !elUser.getUsername().equals("")) {
						usersql.append(" and eu.username like '%"+ elUser.getUsername() + "%'");
					}
//					if (null != elUser.getJingzhong() && !elUser.getJingzhong().equals("")&& !elUser.getJingzhong().equals("0")) {
//						usersql.append(" and eu.jingzhong = '"+elUser.getJingzhong().trim()+"'");
//					}
//					if (null != elUser.getDishi() && !elUser.getDishi().equals("")&& !elUser.getDishi().equals("0")){
//						usersql.append("  and eu.dishi = '"+elUser.getDishi().trim()+"' "); 
//					}
//					if (null != elUser.getZhiji() && !elUser.getZhiji().equals("")&& !elUser.getZhiji().equals("0")){
//						usersql.append("  and eu.zhiji = '"+elUser.getZhiji().trim()+"' ");
//					}
//					if (null != elUser.getZhiwu() && !elUser.getZhiwu().equals("")&& !elUser.getZhiwu().equals("0")){
//						usersql.append("  and eu.zhiwu = '"+elUser.getZhiwu().trim()+"' "); 
//					}
					if (elUser.getJingzhong()>0) {
						usersql.append(" and eu.jingzhong = '"+elUser.getJingzhong()+"'");
					}
					if (elUser.getDishi()>0){
						usersql.append("  and eu.dishi = '"+elUser.getDishi()+"' "); 
					}
					if (elUser.getZhiji()>0){
						usersql.append("  and eu.zhiji = '"+elUser.getZhiji()+"' ");
					}
					if (elUser.getZhiwu()>0){
						usersql.append("  and eu.zhiwu = '"+elUser.getZhiwu()+"' "); 
					}
					if (null != elUser.getGangwei() && !elUser.getGangwei().equals("")&& !elUser.getGangwei().equals("0")){
						usersql.append("  and eu.gangwei = '"+elUser.getGangwei().trim()+"' ");
					}	
					if (elUser.getIsAssign() != null && !elUser.getIsAssign().equals("-1")) {
						if (elUser.getIsAssign().equals("0")) {
							usersql
							.append(" and eu.id in(select ca.userid from study_class ca where ca.classid="
									+ classid + ") and eu.id not in(select eco.userid from eluser_couser_order eco where eco.classid="
									+ classid + ") ");
						} else if (elUser.getIsAssign().equals("1")) {
							usersql
							.append(" and eu.id  in(select eco.userid from eluser_couser_order eco where eco.classid="
									+ classid + ") ");
						}
						 else if (elUser.getIsAssign().equals("2")) {
								usersql
								.append(" and eu.id not in(select ca.userid from study_class ca where ca.classid="
									+ classid + ") ");
							}
						
					}
				} 
				usersql
				.append("and eu.id not in (select classid from  eluser_couser_order eco where eco.classid="+classid+" )");
				
				ps = ct.prepareStatement(usersql.toString());    
				rs = ps.executeQuery();
				if (rs.next()) { 
					Rsize = rs.getInt(1);
				}
	 
				return Rsize;
			} catch (Exception e) {
				logger.error("分配学员失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
		/**
		 * 添加培训班订单学员关系
		 */
		public void addUserOrderClass(int userid, int classid,int orderid) throws ElException{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			try {
				ct = DBConnection.getConnection();
			ps=ct.prepareStatement("call into_scorder2(?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, classid);
			ps.setInt(3, orderid);
			ps.executeQuery();
			} catch (Exception e) {
				logger.error("添加订单人员培训班关系出错！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
		/**
		 * 得到培训班课程总价格，免费课程数量，课程总数
		 * @param classid
		 * @param userid
		 * @return
		 * @throws ElException
		 */
		public ClassOrder  getAllPriceCourseByClassidID (int classid,int userid) throws ElException{
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			ClassOrder co =new ClassOrder();
			float price = 0;
			int count=0;
			int acount=0;
			try {
			ct = DBConnection.getConnection();
				ps=ct.prepareStatement("  select   cp.coursenowPrice  from " +
						"( class_course cc left join course_price  cp on cp.courseid=cc.courseid) " +
						" left join course cs on cs.id=cp.courseid where classid=?");
				ps.setInt(1, classid);
				rs=ps.executeQuery();
				while(rs.next()){
					
						price+=(rs.getFloat(1));
						acount++;
					
						
					
				}
				co.setCountCourse(acount);
				co.setPrice(price);
				return co;
			} catch (Exception e) {
				logger.error("查询培训班总价格出错！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}

		}
		
		public List<ClassOrder> getMyOrderClassList(Department depTree, int depid,int userid,ClassOrder corder,Timestamp stime,Timestamp otime,int pageNow,int pageSize) throws ElException{

			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<ClassOrder> myOrder=new ArrayList<ClassOrder>();
			String str="";
			String ids = createDepartmentId(depTree, depid);
			if(corder!=null){
				if(!corder.getSid().equals("")){
					str+=" and spo.id like '%"+corder.getSid()+"%' ";
				}
				if(!corder.getClass().getName().equals("")){
					str+=" and ec.name like '%"+corder.getClass().getName()+"%'";
				}
				if(!corder.getSstatus().equals("")){
					str+="  and spo.status="+corder.getSstatus()+" ";
				}
				
			}
			if (stime != null && !"".equals(stime)){
				str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') > '"+stime+"'";
			}
			if (otime != null && !"".equals(otime)){
				str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') < '"+otime+"'";
				
			}
			String str1="";
			if(userid!=0){
				str1= " and  spo.userid="+userid+" ";
			}
			try {
				ct = DBConnection.getConnection();
				String sql=" select * from(select t.*,rownum rn from(" +
				" select spo.id spoid,spo.Status, ec.name,ec.id ecid ,spoi.Price,spoi.count,spo.sumpeice ," +
				" (select  count(1) from class_course cco  where  cco.classid=ec.id) ac,spo.orderdate,el.realname,dep.name  depname" +					
				" from sp_order spo, sp_order_info spoi, elclass ec ,department dep ,eluser el where  spo.id=spoi.orderid and dep.id = el.depid and el.id=spo.userid" +
				" and Distribution=0 and ec.id=spoi.commodityid  and  dep.id in("+ids+")  and  spoi.Commoditytype=2 "+str1+"  order by spo.orderdate desc )t " +
				" where rownum <= ? ) where rn>=?"; 
				ps = ct.prepareStatement(sql);
				ps.setInt(1, pageNow);
				ps.setInt(2, pageSize);
				rs = ps.executeQuery();
				while (rs.next()) {
					ClassOrder co = new ClassOrder();
					co.setId(rs.getInt(1));
					co.setStatus(rs.getInt(2));
					co.setPrice(rs.getFloat(5));
					co.setCount(rs.getInt(6));
					co.setZprice(rs.getFloat(7));
					co.setCountCourse(rs.getInt(8));
					co.setOdate(rs.getTimestamp(9));
					ELUser  e =new ELUser();
					e.setRealname(rs.getString(10));
					Department  d  = new  Department();
					d.setName(rs.getString(11));
					e.setDepartment(d);
					co.setUser(e);
					ElClass c= new ElClass();
					c.setName(rs.getString(3));
					c.setId(rs.getInt(4));
					co.setElClass(c);
					myOrder.add(co);
				}
				return  myOrder;
			} catch (Exception e) {
				logger.error("读取培训班订单失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
		public int getMyOrderClassListSize(Department depTree, int depid,int userid,ClassOrder corder,Timestamp stime,Timestamp otime) throws ElException{

			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			
			String str="";
			String ids = createDepartmentId(depTree, depid);
			if(corder!=null){
				if(!corder.getSid().equals("")){
					str+=" and spo.id like '%"+corder.getSid()+"%' ";
				}
				if(!corder.getClass().getName().equals("")){
					str+=" and ec.name like '%"+corder.getClass().getName()+"%'";
				}
				if(!corder.getSstatus().equals("")){
					str+="  and spo.status="+corder.getSstatus()+" ";
				}
				
			}
			if (stime != null && !"".equals(stime)){
				str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') > '"+stime+"'";
			}
			if (otime != null && !"".equals(otime)){
				str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') < '"+otime+"'";
				
			}
			String str1="";
			if(userid!=0){
				str1= " and  spo.userid="+userid+" ";
			}
			try {
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement(" select count(*) from(" +
						" select spo.id "+
						" from sp_order spo, sp_order_info spoi, elclass ec ,department dep ,eluser el where spo.id=spoi.orderid and dep.id = el.depid and el.id=spo.userid " +
						" and Distribution=0 and ec.id=spoi.commodityid and  dep.id in("+ids+") and  spoi.Commoditytype=2 "+str1+"  )");

				rs = ps.executeQuery();
					rs.next();
					return rs.getInt(1);
				
				
			} catch (Exception e) {
				logger.error("读取培训班订单失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
		public void deleClassOrder(int orderid) throws ElException {
			PreparedStatement ps = null;
			Connection ct = null;
			ResultSet rs = null;
			try {
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement("call dele_classorder (?)");
				ps.setInt(1, orderid);
				ps.executeUpdate();
				// assignCourse2UserDelete(ct, classid, userid);
			} catch (Exception e) {
				logger.error("删除培训班订单失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
		public void delespOrder(int  orderid) throws ElException{
			
			PreparedStatement ps = null;
			Connection ct = null;
			ResultSet rs = null;
			try {
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement("delete sp_order where id=?");
				ps.setInt(1, orderid);
				ps.executeUpdate();
				// assignCourse2UserDelete(ct, classid, userid);
			} catch (Exception e) {
				logger.error("删除培训班订单失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			
		}
		public   List<Order>  myorderlist(int userid,Timestamp start,Timestamp end ,Integer orderid,
				int orderstatus,int pageNow, int pageSize) throws ElException{
			
			PreparedStatement ps = null;
			Connection ct = null;
			ResultSet rs = null;
			List<Order> lo =new ArrayList<Order>();
			String str="";
			if(orderid!=null){
				
				str+="  and id like '%"+orderid+"%' ";
				
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
				
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
			}
			if(orderstatus!=1){
				
				str+=" and status= "+orderstatus+" ";
				
			}
			try {
				String sql="select * from (select t.*, rownum rn from (select id ,orderdate," +
						"sumpeice,buydate,status from sp_order where userid=? and distribution=1  "+str+" " +
						" order by orderdate desc )t where rownum <= ? ) where rn>=?";
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement(sql);
				ps.setInt(1, userid);
				ps.setInt(2, pageNow);
				ps.setInt(3, pageSize);
				rs=ps.executeQuery();
				while(rs.next()){
					
					Order o = new Order();
					o.setId(rs.getInt(1));
					o.setOrderdate(rs.getTimestamp(2));
					o.setSumpeice(rs.getFloat(3));
					o.setBuydate(rs.getTimestamp(4));
					o.setStatus(rs.getInt(5));
					lo.add(o);
					
					
				}
				return lo;
				// assignCourse2UserDelete(ct, classid, userid);
			} catch (Exception e) {
				logger.error("得到我的个人订单列表失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
	public   int  myorderlistsize(int userid,Timestamp start,Timestamp end ,Integer orderid,int orderstatus) throws ElException{
			
			PreparedStatement ps = null;
			Connection ct = null;
			ResultSet rs = null;
			String str="";
			if(orderid!=null){
				
				str+="  and id like '%"+orderid+"%' ";
				
			}
			if(start!=null&&!"".equals(start)){
				str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
				
			}
			if(end!=null&&!"".equals(end)){
				str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
			}
			if(orderstatus!=1){
				
				str+=" and status= "+orderstatus+" ";
				
			}
			try {
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement("select count(1) from (select id ,orderdate," +
						"sumpeice,buydate,status from sp_order where userid=? and distribution=1  "+str+" )");
				ps.setInt(1, userid);
				rs=ps.executeQuery();
				rs.next();
				return rs.getInt(1);
				// assignCourse2UserDelete(ct, classid, userid);
			} catch (Exception e) {
				logger.error("得到我的个人订单列表大小失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
	public void orderstatusupd(int status,int orderid) throws ElException{

		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update sp_order set status = ? where id =? ");
			ps.setInt(1, status);
			ps.setInt(2, orderid);
			ps.executeUpdate();
			
			
			// assignCourse2UserDelete(ct, classid, userid);
		} catch (Exception e) {
			logger.error("修改订单信息失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public   List<Order>  allorderlist(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end ,Integer orderid,
			int orderstatus,int pageNow, int pageSize) throws ElException{
		
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Order> lo =new ArrayList<Order>();
		String str="";
		if(orderid!=null){
			
			str+="  and id like '%"+orderid+"%' ";
			
		}
		if(start!=null&&!"".equals(start)){
			str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			
		}
		if(end!=null&&!"".equals(end)){
			str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
		}
		if(orderstatus!=1){
			
			str+=" and status= "+orderstatus+" ";
			
		}
		if(elUser!=null){
			if(elUser.getRealname()!=null&&!elUser.getRealname().equals("")){
				str+="   and eu.realname like '%"+elUser.getRealname()+"%' ";	
			}
			if(elUser.getUsername()!=null&&!elUser.getUsername().equals("")){
				
				str+=" and  eu.username like '%"+elUser.getUsername()+"%' ";
			}
			if(elUser.getSex()!=null&&!elUser.getSex().equals("")){
				
				str+=" and  eu.sex like '%"+elUser.getSex()+"%' ";
			}
			if(elUser.getRole()!=null&&elUser.getRole().getId()!=0){
				
				str+=" and  eu.role ="+elUser.getRole().getId()+" ";
			}
		}
		try {
			ct = DBConnection.getConnection();
			String sql = "select * from (select t.*, rownum rn from (select spo.id ,spo.orderdate," +
			" spo.sumpeice,spo.buydate,spo.status ,eu.realname,eu.username,dept.name deptname,elr.name  rolename " +
			" from sp_order spo left join eluser eu on eu.id=spo.userid" +
			" left join DEPARTMENT dept on  dept.id=eu.depid  left join elrole elr  on elr.id=eu.role " +
			" where "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+"  and distribution=1  "+str+" " +
			" order by orderdate desc)t where rownum <= ? ) where rn>=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				
				Order o = new Order();
				o.setId(rs.getInt(1));
				o.setOrderdate(rs.getTimestamp(2));
				o.setSumpeice(rs.getFloat(3));
				o.setBuydate(rs.getTimestamp(4));
				o.setStatus(rs.getInt(5));
				ELUser u= new ELUser();
				u.setRealname(rs.getString(6));
				u.setUsername(rs.getString(7));
				Department d = new  Department();
				d.setName(rs.getString(8));
				ElRole e = new ElRole();
				e.setName(rs.getString(9));
				u.setRole(e);
				u.setDepartment(d);
				o.setUser(u);
				lo.add(o);
				
				
			}
			return lo;
			// assignCourse2UserDelete(ct, classid, userid);
		} catch (Exception e) {
			logger.error("得到我的个人订单列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public   int  allorderlistsize(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end ,Integer orderid,int orderstatus) throws ElException{
		
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		String str="";
		if(orderid!=null){
			
			str+="  and id like '%"+orderid+"%' ";
			
		}
		if(start!=null&&!"".equals(start)){
			str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			
		}
		if(end!=null&&!"".equals(end)){
			str+=" and to_char(orderdate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
		}
		if(orderstatus!=1){
			
			str+=" and status= "+orderstatus+" ";
			
		}
		if(elUser!=null){
			if(elUser.getRealname()!=null&&!elUser.getRealname().equals("")){
				str+="   and eu.realname like '%"+elUser.getRealname()+"%' ";	
			}
			if(elUser.getUsername()!=null&&!elUser.getUsername().equals("")){
				
				str+=" and  eu.username like '%"+elUser.getUsername()+"%' ";
			}
			if(elUser.getSex()!=null&&!elUser.getSex().equals("")){
				
				str+=" and  eu.sex like '%"+elUser.getSex()+"%' ";
			}
			if(elUser.getRole()!=null&&elUser.getRole().getId()!=0){
				
				str+=" and  eu.role ="+elUser.getRole().getId()+" ";
			}
		}
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(1) from (select spo.id  from sp_order spo left join eluser eu on eu.id=spo.userid" +
					" left join DEPARTMENT dept on  dept.id=eu.depid  left join elrole elr  on elr.id=eu.role " +
					"where "+ElNodeSQL.getWhereSql(tree, "dept",sublibs)+" " +
					" and distribution=1  "+str+" )");
			rs=ps.executeQuery();
			rs.next();
			return rs.getInt(1);
			// assignCourse2UserDelete(ct, classid, userid);
		} catch (Exception e) {
			logger.error("得到所有个人订单列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public List<Commodity> getorderCommoditybyid(int  orderid) throws ElException{
		
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Commodity> lo =new ArrayList<Commodity>();
		
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select  cs.name ,soi.price, soi.count,soi.commoditytype ,soi.commodityid from sp_order_info" +
					"  soi left join course cs on cs.id =soi.commodityid  where soi.orderid=? and commoditytype=1 " +
					"union all " +
					" select el.name ,soi.price, soi.count,soi.commoditytype ,soi.commodityid  from  sp_order_info  soi left " +
					" join    elclass el on el.id=soi.commodityid where soi.orderid=?  and commoditytype=2" +
					"   union all " +
					"  select  bk.name ,soi.price, soi.count,soi.commoditytype ,soi.commodityid  from  sp_order_info  soi left " +
					"  join    bookinfo bk on bk.id=soi.commodityid where soi.orderid=?  and commoditytype=3" +
					" union all " +
					" select p.name,soi.price,soi.count,soi.commoditytype,soi.commodityid " +
					" from sp_order_info  soi left " +
					" join product p on p.id=soi.commodityid where soi.orderid=? and commoditytype=4 ");
			ps.setInt(1, orderid);
			ps.setInt(2, orderid);
			ps.setInt(3, orderid);
			ps.setInt(4, orderid);
			rs=ps.executeQuery();
		
				while(rs.next()){
					Commodity sc = new Commodity();
					sc.setCommodityName(rs.getString(1));
					sc.setCommoditytype(rs.getInt(4));
					sc.setNowp(rs.getFloat(2));
					sc.setCount(rs.getInt(3));
					sc.setAllp(sc.getNowp()*sc.getCount());
					sc.setCommodityid(rs.getInt(5));
					lo.add(sc);
			
			}
			return lo;
			// assignCourse2UserDelete(ct, classid, userid);
		} catch (Exception e) {
			logger.error("得到订单中的商品失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void order_userorderdele(int  orderid) throws ElException{
		
		
			PreparedStatement ps = null;
			Connection ct = null;
			ResultSet rs = null;
			try {
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement("call dele_userorder (?)");
				ps.setInt(1, orderid);
				ps.executeUpdate();
				// assignCourse2UserDelete(ct, classid, userid);
			} catch (Exception e) {
				logger.error("删除个人订单失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		
	}
	public List<Course>  getClassOrderCourseByid(int classid) throws ElException{
		

		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<Course> lc= new ArrayList<Course>(); 
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select cs.id ,cs.name,cp.coursenowPrice from course  cs, CLASS_COURSE cl,course_price cp " +
					" where  cp.courseid = cs.id and cl.courseid=cs.id  and cl.classid=? ");
			ps.setInt(1, classid);
			rs=ps.executeQuery();
			 while(rs.next()){
				 Course c = new Course();
				 c.setId(rs.getInt(1));
				 c.setName(rs.getString(2));
				 Peice  p = new Peice();
				 p.setCoursenowPrice(rs.getFloat(3));
				 c.setPrice(p);
				 lc.add(c);
				 
			 }
			return  lc;
			
			
		} catch (Exception e) {
			logger.error("查询培训班分配订单课程详细信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	public  List<MyCourse>  getclassorderuserinfolist(int orderid ,int pageNow , int pageSize) throws ElException{
		
		
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<MyCourse> lc= new ArrayList<MyCourse>(); 
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select *  from( select  t.*,rownum rn  from( select el.realname,dep.name ,elc.name elcname ,el.username" +
					" from department dep ,eluser  el , eluser_couser_order eco,elclass elc  where eco.orderid = ?  " +
					" and  eco.userid = el.id and el.depid=dep.id and elc.id=eco.classid )t where rownum <= ? ) where rn>=?");
			ps.setInt(1, orderid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs=ps.executeQuery();
			 while(rs.next()){
				 ELUser c = new ELUser();
				 MyCourse  cc = new MyCourse();
				 cc.setClassName(rs.getString(3));
				 c.setRealname(rs.getString(1));
				 c.setUsername(rs.getString(4));
				 Department d = new Department();
				 d.setName(rs.getString(2));
				 c.setDepartment(d);
				 cc.setUser(c);
				 lc.add(cc); 
			 }
			return  lc;
			
			
		} catch (Exception e) {
			logger.error("查询培训班分配订单课程详细信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}
	
	public  int getclassorderuserinfolistsize(int orderid) throws ElException{
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ELUser> lc= new ArrayList<ELUser>(); 
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select count (*) from (select el.realname,dep.name  " +
					"from department dep ,eluser  el , eluser_couser_order eco  where eco.orderid = ?  " +
					" and  eco.userid = el.id and el.depid=dep.id ) ");
			ps.setInt(1, orderid);
			rs=ps.executeQuery();
			rs.next();
			return  rs.getInt(1);
		} catch (Exception e) {
			logger.error("查询培训班分配订单课程详细信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
public  ExamRoom  getroomid(int  courseid ) throws ElException{
		
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		ExamRoom examRoom = null;

		try {
			ct = DBConnection.getConnection();
			String  sql = "select id ,title,classid from  exam_room  where courseid=? and classid=0 ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, courseid);
			rs=ps.executeQuery();
			if(rs.next()){
				examRoom= new ExamRoom();
				examRoom.setTitle(rs.getString(2));
				examRoom.setId(rs.getInt(1));
				examRoom.setClassid(rs.getInt(3));
			}
			return examRoom;
		} catch (Exception e) {
			logger.error("查询培训班分配订单课程详细信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		
	}

	public  List<ExamRoom>  getroomlistbyclassid(int  classid ) throws ElException{
		
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<ExamRoom> li=null;
		

		try {
			ct = DBConnection.getConnection();
			String  sql = "select id ,title,classid from  exam_room  where classid=?  ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, classid);
			rs=ps.executeQuery();
			li = new ArrayList<ExamRoom>();
			if(rs.next()){
				ExamRoom examRoom= new ExamRoom();
				examRoom.setTitle(rs.getString(2));
				examRoom.setId(rs.getInt(1));
				examRoom.setClassid(rs.getInt(3));
				li.add(examRoom);
			}
			return li;
		} catch (Exception e) {
			logger.error("查询培训班分配订单课程详细信息失败！", e);
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
	public List<Course> newlistAllSelectCourse(CourseType ctypeTree, int depid,
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
							" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend ,c.islink,cp.coursenowPrice from course c, course_type ct,")
					.append(
							" eluser u,department dep ,course_price cp where c.ctypeid=ct.id and c.creater = u.id ")
					.append(" and u.depid=dep.id  and c.name like ? and cp.courseid=c.id and cp.status=1 ")
					// and c.status = ? 2012-1-3 需要显示所有课程
					.append(" and c.status !=9 ")
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
				Peice  p = new Peice();
				p.setCoursenowPrice(rs.getFloat(15));
				c.setPrice(p);
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
	public int newlistAllSelectCourseSize(CourseType ctypeTree, int depid,
			String name, int ctid, int status, int classId, int role)
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
							" eluser u,department dep ,course_price cp where c.ctypeid=ct.id and c.creater = u.id ")
					.append(" and u.depid=dep.id  and c.name like ? and cp.courseid=c.id and cp.status=1 ")// and
																		// c.status
																		// = ?
																		// 2012-1-3
																		// 需要显示所有课程
					.append(" and c.status != 9 ")
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
	public List<MyCourse> listMyCourse(int userid, int pageNow, int pageSize)
	throws ElException {
PreparedStatement ps = null;
ResultSet rs = null;
Connection ct = null;
List<MyCourse> myBxc = new ArrayList<MyCourse>();
try {
	ct = DBConnection.getConnection();
	StringBuffer buffer = new StringBuffer();
	// buffer.append("select * from (select t.*, rownum rn from (select
	// c.id cid, c.name,c.creater, eu.realname,c.credit, ")
	// .append(" c.during,c.teachername,sc.passtime/60
	// passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id
	// sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime
	// ")
	// .append(" from study_course sc left join course c on sc.courseid
	// = c.id ")
	// .append(" left join eluser eu on c.creater = eu.id left join
	// study_quizinfo sqi on sqi.id=sc.sqiid where sc.userid = ? and
	// sc.status != 3 order by sc.status asc, c.createtime desc) t where
	// rownum <= ? ) where rn>=?");//status = 1 经过人员审核 and sc.classid=0
	buffer
			.append(
					"select * from (select t.*, rownum rn from (select c.id cid, c.name,c.creater, eu.realname,c.credit, ")
			.append(
					" c.during,c.teachername,sc.passtime/60 passtime,sc.process,sc.status,c.islink,sc.mycredit,sqi.id sqiid_,sqi.myScore,sqi.ispassed,c.roomstart,c.roomend,sc.starttime,sc.finishtime,c.mainimg  ")
			.append(
					" from study_course sc left join course c on sc.courseid = c.id  ")
			.append(
					" left join eluser eu on c.creater = eu.id left join study_quizinfo sqi on sqi.id=sc.sqiid " +
			  		" left  join  eluser_couser_order  " +
			  		"  eco on  eco.userid=sc.userid and eco.classid=sc.classid and eco.courseid=sc.courseid left " +
			  		" join  sp_order  spo  on  spo.id=eco.orderid " +						
			" where sc.userid = ?  and c.status != 9 and sc.classid=0 and spo.buydate is not null order by sc.status asc, c.createtime desc) t where rownum <= ? ) where rn>=?");// status
																																																																		// = 1
																																																																		// 经过人员审核
																																																																		// and
																																																																		// sc.classid=0
																																																																		// c.isNormal
																																																																		// ==
																																																																		// 1已開通
																																																																		// and
																																																																		// sc.status
																																																																		// != 3
																																																																		// ?
																																																																		// hwc
	ps = ct.prepareStatement(buffer.toString());
	System.out.println(buffer.toString());
	ps.setInt(1, userid);
	ps.setInt(2, pageNow);
	ps.setInt(3, pageSize);

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
		mc.setPasstime(rs.getInt(8));
		mc.setProcess(rs.getInt(9));
		mc.setCourse(c);
		mc.setStatus(rs.getInt(10));
		c.setIslink(rs.getInt(11));
		mc.setMyCredit(rs.getFloat(12));
		mc.setMyExamPaper(new MyExamPaper(rs.getInt(13)));
		mc.getMyExamPaper().setMyScore(rs.getInt(14));
		mc.getMyExamPaper().setIspassed(rs.getInt(15));
		// c.setRoomstart(rs.getTimestamp(16));
		// c.setRoomend(rs.getTimestamp(17));
		c.setRoomstart(rs.getTimestamp("starttime"));
		c.setRoomend(rs.getTimestamp("finishtime"));
		c.setMainimg(rs.getString("mainimg"));
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
	
	public int listMyCourseSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql = "select count(*) from study_course sc left join course c on sc.courseid = c.id  left  join  eluser_couser_order   eco on " +
					" eco.userid=sc.userid and eco.classid=sc.classid and eco.courseid=sc.courseid left  join  sp_order  spo  on  spo.id=eco.orderid  where sc.userid = ? and sc.classid = 0  and c.status != 9  and spo.buydate is not null";
			ps = ct.prepareStatement(sql);

			ps.setInt(1, userid);

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的课程列表出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	public List<MyClass> listMyStudyClass(int userid, int pageNow, int pageSize)
	throws ElException {
PreparedStatement ps = null;
ResultSet rs = null;
Connection ct = null;
List<MyClass> mcls = new ArrayList<MyClass>();
try {
	String sql = "select * from (select t.*, rownum rn from  (select cl.id,cl.name,cl.optionalcredit,cl.createtime,ca.applyDate ,"
			+ "(select count(*) from class_course ccb where ccb.classid = cl.id and ccb.status = 0) as bxCount,"
			+ "(select sum(ccx.setcredit) from course c left join class_course ccx on ccx.courseid= c.id where ccx.classid = cl.id and ccx.status =1) as xxCredit,"
			+ " eu.id eid,eu.realname,cl.certificatename,cl.starttime,cl.finishtime,cl.status classStatus,cl.isApplication,joinway,cl.mainimg " +
					" from study_class ca ,elclass cl,eluser eu" +
					",eluser_couser_order   eco ,sp_order  spo "
			+ "where eco.classid=ca.classid and eco.userid=ca.userid and eco.orderid=spo.id and spo.buydate is not null and  cl.creater = eu.id and ca.userid = ? and cl.status not in (9)  and ca.classid = cl.id and ca.status!=-1 order by ca.applyDate desc  )t where rownum <= ? ) where rn>=?";// and
	// cl.status
	// in
	// (1,4)
	// 1已开通，4申请修改
	ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(StudyConstants.STUDY_CLASS_BYUID));
	ps = ct.prepareStatement(sql);
	ps.setInt(1, userid);
	ps.setInt(2, pageNow);
	ps.setInt(3, pageSize);
	rs = ps.executeQuery();
	while (rs.next()) {
		MyClass m = new MyClass();
		m.setElClass(new ElClass(rs.getInt(1), rs.getString(2)));
		m.getElClass().setOptionalcredit(rs.getInt(3));
		m.getElClass().setCreatetime(rs.getDate(4));
		m.setBegintime(rs.getDate(5));
		m.getElClass().setBxCount(rs.getInt(6));
		m.setBxCount(getMyclassBxCount(userid, m.getElClass().getId()));
		m.getElClass().setXxCredit(rs.getInt(7));
		m
				.setXxCredit(getMyclassXxCredit(userid, m.getElClass()
						.getId()));
		m.getElClass().setCreater(
				new ELUser(rs.getInt(8), rs.getString(9)));
		m.getElClass().setCertificatename(rs.getString(10));
		// m.setStarttime(rs.getTimestamp("starttime"));
		// m.setFinishtime(rs.getTimestamp("finishtime"));
		m.getElClass().setStarttime(rs.getTimestamp("starttime"));
		m.getElClass().setFinishtime(rs.getTimestamp("finishtime"));
		m.getElClass().setStatus(rs.getInt("classStatus"));
		m.getElClass().setIsApplication(rs.getInt("isApplication"));
		m.getElClass().setIsjoin(rs.getInt("joinway")==1?"申请":"分配");
		m.getElClass().setMainimg(rs.getString("mainimg"));
		PreparedStatement ps1 = ct.prepareStatement(ElQuerySql
				.getSQL(StudyConstants.STUDY_CLASS_FINISH_INFO_BYUID));
		ps1.setInt(1, userid);
		ps1.setInt(2, m.getElClass().getId());
		ResultSet rs1 = ps1.executeQuery();
		if (rs1.next()) {
			m.setStatus(rs1.getInt(1));
			if (m.getStatus() != StudyConstants.STUDY_CLASS_STATUS_WAIT) {
				m.setEndtime(rs1.getTimestamp(2));
			}
		} else {
			m.setStatus(0);
		}
		rs1.close();
		ps1.close();
		mcls.add(m);
	}
} catch (Exception e) {
	logger.error("在学培训班！", e);
	throw new ElException(e);
} finally {
	DBConnection.closeConnectInfo(ct, ps, rs);
}
return mcls;
}

public int listMyStudyClassSize(int userid) throws ElException {
PreparedStatement ps = null;
ResultSet rs = null;
Connection ct = null;
int a = 0;
try {
	String sql = "select count(*) from study_class ca ,elclass cl,eluser eu ,eluser_couser_order   eco ,sp_order  spo "
			+ "where eco.classid=ca.classid and eco.userid=ca.userid and eco.orderid=spo.id and spo.buydate is not null and cl.creater = eu.id and ca.userid = ? and cl.status not in (9) and cl.isNormal = 1 and ca.classid = cl.id and ca.status!=-1";// and
	// cl.status
	// in
	// (1,4)
	// 1已开通，4申请修改
	ct = DBConnection.getConnection();
	// ps = ct.prepareStatement(ElQuerySql
	// .getSQL(StudyConstants.STUDY_CLASS_BYUID));
	ps = ct.prepareStatement(sql);
	ps.setInt(1, userid);
	rs = ps.executeQuery();
	if (rs.next()) {
		a = rs.getInt(1);
	}
} catch (Exception e) {
	logger.error("在学培训班！", e);
	throw new ElException(e);
} finally {
	DBConnection.closeConnectInfo(ct, ps, rs);
}
return a;
}
private int getMyclassBxCount(int userid, int classid) throws ElException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	int b = 0;
	try {
		ct = DBConnection.getConnection();
		// ps = ct
		// .prepareStatement("select count(sc.courseid) from study_course sc
		// left join study_quizinfo sqi on sqi.id= sc.sqiid where
		// sqi.ispassed =1 and sc.classid = ? and sc.userid = ? and
		// sc.status = 0");
		// ps.setInt(1, classid);
		// ps.setInt(2, userid);
		// rs = ps.executeQuery();
		// if (rs.next()) {
		// return rs.getInt(1);
		// }
		ps = ct
				.prepareStatement("SELECT courseid,credit,setcredit,getcredit FROM class_course where classid =? and status = 0");
		ps.setInt(1, classid);
		// ps.setInt(2, userid);
		rs = ps.executeQuery();
		while (rs.next()) {
			// PreparedStatement ps1 = ct
			// .prepareStatement("select user_p_course( ?, ?,?,?) from dual
			// ");
			PreparedStatement ps1 = ct
					.prepareStatement("select user_p_course2( ?, ?,?,?)  from dual ");// !!
			ps1.setInt(1, rs.getInt(1));
			ps1.setInt(2, userid);
			ps1.setInt(3, rs.getInt(4));
			ps1.setInt(4, classid);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next())
				if (rs1.getInt(1) == 1) {
					b = b + 1;
				}
			rs1.close();
			ps1.close();
			// return rs.getInt(1);
		}

	} catch (Exception e) {
		logger.error("我的课程列表出错！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	return b;
}

private int getMyclassXxCredit(int userid, int classid) throws ElException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	int b = 0;
	try {
		ct = DBConnection.getConnection();
		// ps = ct
		// .prepareStatement("select sum(c.credit) from study_course sc left
		// join study_quizinfo sqi on sqi.id= sc.sqiid left join course c on
		// c.id= sc.courseid where sqi.ispassed =1 and sc.classid = ? and
		// sc.userid = ? and sc.status = 1");
		// ps.setInt(1, classid);
		// ps.setInt(2, userid);
		// rs = ps.executeQuery();
		// if (rs.next()) {
		// return rs.getInt(1);
		// }
		ps = ct
				.prepareStatement("SELECT courseid,credit,setcredit,getcredit FROM class_course where classid =? and status = 1");
		ps.setInt(1, classid);
		// ps.setInt(2, userid);
		rs = ps.executeQuery();
		while (rs.next()) {
			// PreparedStatement ps1 = ct
			// .prepareStatement("select user_p_course( ?, ?,?) from dual
			// ");
			PreparedStatement ps1 = ct
					.prepareCall("select  user_p_course2( ?, ?,?,?)  from dual ");
			ps1.setInt(1, rs.getInt(1));
			ps1.setInt(2, userid);
			ps1.setInt(3, rs.getInt(4));
			ps1.setInt(4, classid);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next())
				if (rs1.getInt(1) == 1) {
					b = b + rs.getInt(3);
				}
			// return rs.getInt(1);
			rs1.close();
			ps1.close();
		}

	} catch (Exception e) {
		logger.error("我的课程列表出错！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	return b;
}
/**
 * 获取练习分配给的部门树
 * @param userid
 * @param type
 * @param stopid
 * @param containStop
 * @return
 * @throws ElException
 */
public Department getExampracDepTree(int pracid, int stopid,boolean containStop) throws ElException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	Department dep =  new Department(-2, "已分配的部门");
	dep.setLevel(0);
	try {
		ct = DBConnection.getConnection();
		ps = ct.prepareStatement("select deptid from class_department where classid = ?");
		ps.setInt(1, pracid);
		rs = ps.executeQuery();
		List<Department> list = new ArrayList<Department>();
		while (rs.next()) {
			int depid = rs.getInt(1);
			if (depid == stopid && !containStop) {
			} else {
				Department depc = getDepTree(rs.getInt(1), stopid,
						containStop, 1);
				depc.setParent(dep);
				list.add(depc);
			}
		}
		dep.setChild(list);
	} catch (Exception e) {
		logger.error("获取分配给的部门树出错！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	return dep;
}
private List treeAllId = null;
/**
 * 获取TreeAllId
 */
public List getTreeAllId(Department depTree, boolean config)
		throws ElException {
	// 需要初始化
	if (config == true) {
		treeAllId = new ArrayList();
	}
	for (int i = 0; i < depTree.getChild().size(); i++) {
		Department temp = depTree.getChild().get(i);
		// System.out.println("treeId:"+qs.getId());
		treeAllId.add(temp.getId());
		if (temp.getChild() != null) {
			this.getTreeAllId(temp, false);
		}
	}
	return treeAllId;
}
private Department getDepTree(int did, int stopid, boolean containStop,
		int level) throws ElException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	Department dep = null;
	try {
		// if (did == 0) {
		// dep = getDepRootByCid();
		// } else {
		dep = getDepById(did);
		// }
		dep.setLevel(level);
		ct = DBConnection.getConnection();
		dep.setChild(listDepartmentsById(dep.getId(), stopid, containStop,
				level, ct));
	} catch (Exception e) {
		logger.error("获取部门树失败！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	return dep;
}
public Department getDepById(int id) throws ElException {

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
//			dep.setManager(new ELUser(rs.getInt(5), rs.getString(11)));
			dep.setParent(new Department(rs.getInt(4)));
			dep.setAddress(rs.getString(5));
			dep.setPostalcode(rs.getString(6));
			dep.setPhone(rs.getString(7));
			dep.setFax(rs.getString(8));
			dep.setEmail(rs.getString(9));
			dep.setBh(rs.getString(10));
			dep.setLid(rs.getInt(11));
			dep.setRid(rs.getInt(12));
			dep.setClassCount(rs.getInt(13));
		}
	} catch (Exception e) {
		logger.error("查看部门信息出错！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	return dep;
}
private List<Department> listDepartmentsById(int parentid, int stopid,
		boolean isContainStop, int level, Connection ct) throws Exception {
	List<Department> deps = new ArrayList<Department>();
	// PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
	// .getSQL(DUConstants.DEP_QUERY_BYPIDANDCID));
	PreparedStatement pstemp = ct
			.prepareStatement("select id,name,description,parentid,manager,address,postalcode,phone,fax,email,bh,lid,rid from DEPARTMENT where parentid = ? order by bh");
	pstemp.setInt(1, parentid);
	ResultSet rstemp = pstemp.executeQuery();
	level++;
	while (rstemp.next()) {
		Department dep = new Department(rstemp.getInt(1), rstemp
				.getString(2));
		dep.setDescription(rstemp.getString(3));
		dep.setParent(new Department(rstemp.getInt(4)));
		dep.setManager(new ELUser(rstemp.getInt(5)));
		dep.setAddress(rstemp.getString(6));
		dep.setPostalcode(rstemp.getString(7));
		dep.setPhone(rstemp.getString(8));
		dep.setFax(rstemp.getString(9));
		dep.setEmail(rstemp.getString(10));
		dep.setLevel(level);
		dep.setBh(rstemp.getString(11));
		dep.setLid(rstemp.getInt(12));
		dep.setRid(rstemp.getInt(13));
		if (dep.getId() != stopid)
			dep.setChild(listDepartmentsById(dep.getId(), stopid,
					isContainStop, level, ct));
		if (!isContainStop && dep.getId() == stopid) {

		} else
			deps.add(dep);
	}
	rstemp.close();
	pstemp.close();
	return deps;
}
public List<Integer>  getdepartmenttoclass(int  classid) throws ElException{
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection ct = null;
	List<Integer>  list = new   ArrayList<Integer>();
	try{
		String  sql  = "select  deptid  from class_department  where classid=? ";
		ct = DBConnection.getConnection();
		ps=ct.prepareStatement(sql);
		ps.setInt(1, classid);
		rs=ps.executeQuery();
		if(rs.next()){
			list.add(rs.getInt(1));
			
			
		}
		return  list;
	}
	catch (Exception e) {
		logger.error("查看部门信息出错！", e);
		throw new ElException(e);
	} finally {
		DBConnection.closeConnectInfo(ct, ps, rs);
	}
	
	
} 
		public  void  delete_deptclass(int classid) throws ElException{
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;

			try{
				String  sql  = "delete class_department  where classid=? ";
				ct = DBConnection.getConnection();
				ps=ct.prepareStatement(sql);
				ps.setInt(1, classid);
				ps.executeUpdate();
			}
			catch (Exception e) {
				logger.error("删除分部门培训班配关系出错！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			
			
			
		}
		/**培训班分配给部门
		 * @return
		 * @throws ElException
		 */
		public void addExamprac_dep(int classid,int deptid) throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			try {
				ct = DBConnection.getConnection();
				ps = ct.prepareStatement("insert into class_department(deptid,classid) values(?,?)");
				ps.setInt(1, deptid);
				ps.setInt(2, classid);
				ps.executeUpdate();
			} catch (Exception e) {
				logger.error("添加培训班分配给部门失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
		}
		
		public  List<Integer>  getdempParentid(int  mydepid) throws ElException{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Integer>  list = new   ArrayList<Integer>();
			try{
				ct = DBConnection.getConnection();
				int a = 0;
				list.add(mydepid);//把自己加进去
				while(a==0){
				String  sql  = "select parentid from DEPARTMENT where id=?";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, mydepid);
				rs=ps.executeQuery();
				while(rs.next()){
					
					if(rs.getInt(1)==0) a =1;
					else{ 
						mydepid = rs.getInt(1);
						list.add(rs.getInt(1));
						}
					}
					
				}
				return  list;
			}
			catch (Exception e) {
				logger.error("查看上级部门节点失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			
		}
		
		public List<Integer>  getdepartmenttoclassbydepid(int  deptid) throws ElException{
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			List<Integer>  list = new   ArrayList<Integer>();
			try{
				String  sql  = "select   classid from class_department  where deptid=? ";
				ct = DBConnection.getConnection();
				ps=ct.prepareStatement(sql);
				ps.setInt(1, deptid);
				rs=ps.executeQuery();
				while(rs.next()){
					list.add(rs.getInt(1));
					
					
				}
				return  list;
			}
			catch (Exception e) {
				logger.error("查看部门信息出错！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			
			
		}
		/**
		 * 获取可申请的培训班详细信息（去掉已删除的）
		 * 
		 * @return
		 * @throws ElException
		 */
		public ElClass getApplyForeElclassById(int classid) throws ElException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection ct = null;
			ElClass elc = new ElClass();
			try {
				ct = DBConnection.getConnection();
				String sql = "select elc.id,elc.name,elc.description,elc.certificatename,elc.createtime,elc.starttime,elc.finishtime, "

						+ "elu.realname,elc.creater,clt.id,clt.name "
						+ ",elc.mainimg  ,elcp.elclassid,elcp.elclassnowprice from elclass elc,elclasstype clt,eluser elu ,elclass_price elcp "
						+ "where  elc.cltype = clt.id and elc.creater = elu.id and elc.id=elcp.elclassid and elc.id =? ";
				ps = ct.prepareStatement(sql);
				ps.setInt(1, classid);
				logger.info(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					elc.setId(rs.getInt(1));
					elc.setName(rs.getString(2));
					elc.setDescription(rs.getString(3));
					elc.setCertificatename(rs.getString(4));
					elc.setCreatetime(rs.getTimestamp(5));
					elc.setStarttime(rs.getTimestamp(6));
					elc.setFinishtime(rs.getTimestamp(7));
					
					ELUser user = new ELUser(rs.getInt(9), rs.getString(8));
					ElClType elt = new ElClType(rs.getInt(10), rs.getString(11));	
					ElClassPeice elclasspeice = new ElClassPeice(rs.getInt(13));
					elclasspeice.setElclassnowPrice(rs.getFloat(14));
					elc.setMainimg(rs.getString(12));
					elc.setPrice(elclasspeice);
					elc.setCreater(user);
					elc.setCltype(elt);
				}
			} catch (Exception e) {
				logger.error("可申请培训班列表失败！", e);
				throw new ElException(e);
			} finally {
				DBConnection.closeConnectInfo(ct, ps, rs);
			}
			return elc;
		}
		/**
		 * 推荐课程列表
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
						.append(
								"select * from (select t.*, rownum rn from (select c.id,")
						.append(
								" c.name,c.ctypeid, c.status,c.createtime,c.modifytime,c.creater,")
						.append(
								" ct.name ctname,u.realname,c.credit,c.hot,c.roomstart,c.roomend,c.teacherName,c.islink,c.mainimg,c.description,c.astatus ,cp.coursenowPrice   from course c, course_type ct,")
						.append(
								" eluser u,department dep ,course_price cp where cp.courseid=c.id and c.ctypeid=ct.id and c.creater = u.id  and cp.status=1 ")
						.append(
								" and u.depid=dep.id and c.status in (" + status
										+ ") " + conditions)
						//
						.append(
								" and ct.id in (" + ids + ") " + sqlw
										+ " order by c.createtime desc )t ")
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
					Peice  p = new Peice();
					p.setCoursenowPrice(rs.getFloat(19));
					c.setPrice(p);
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
								" eluser u,department dep,course_price cp where cp.courseid=c.id and c.ctypeid=ct.id and c.creater = u.id  and cp.status=1  ")
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

}
