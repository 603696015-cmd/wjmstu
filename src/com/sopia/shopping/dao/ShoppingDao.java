package com.sopia.shopping.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.ElConstants;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseType;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.shopping.entities.ClassOrder;
import com.sopia.shopping.entities.Commodity;
import com.sopia.shopping.entities.CourseOrder;
import com.sopia.shopping.entities.Order;
import com.sopia.statman.entities.MyClass;
import com.sopia.studyman.entities.MyCourse;

public interface  ShoppingDao {
	
	/**
	 * 分配课程
	 * @param cid	课程id
	 * @param userid	用户id
	 * @param status	0：必修 1：选修
	 * @throws ElException
	 */
	public void assignedUser(int cid, int userid, int status,int roomid) throws ElException;
	public void assignedUser(int cid, int userid, int status
	) throws ElException ;
	/**
	 * 得到该课程的已分配学生
	 * @param ctypeTree
	 * @param ctid
	 * @param name
	 * @param cid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> course_user_list_BYCtypePage( ELUser user , Department depTree, int depid,CourseType ctypeTree,int ctid, int cid,int pageNow, int pageSize,int role) throws ElException;
	/**
	 * 得到count
	 * @param ctypeTree
	 * @param ctid
	 * @param name
	 * @param cid
	 * @return
	 * @throws ElException
	 */
	public int course_user_list_BYCtypeCount(ELUser user , Department depTree, int depid,CourseType ctypeTree,int ctid, int cid,int role) throws ElException;//hwc
	/**
	 * 生成订单 返回ID
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int markorder(int userid, int status,float peice,int distribution,String  shoujianren,String tel,String note) throws ElException;
	
	public int markorder1(int orderid,int userid, int status,float peice,int distribution,String  shoujianren,String tel,String note) throws ElException;
	/**
	 * 
	 * @param commodityid 商品ID
	 * @param orderid  订单id
	 * @param Commoditytype  商品类型ID
	 * @param price  单价
	 * @param count		数量
	 * @param ifdelivery  是否发货
	 * @param ifreceiv		是否收货
	 * @throws ElException
	 */
	public void markorderInfo(int commodityid ,int orderid,int Commoditytype ,float price,int
			count ,int ifdelivery, int ifreceiv ) throws ElException;
	/**
	 * 添加人员课程订单关系表
	 * @param userid
	 * @param courseid
	 * @param orderid
	 * @throws ElException
	 */
	public void addUserOrder(int userid, int courseid,int orderid) throws ElException;
	/**
	 * 获得课程价格
	 * @param cid
	 * @return
	 * @throws ElException
	 */
	public float getPeiceValue(int cid)throws ElException;
	/**
	 * 获得该课程订单学员列表
	 * @param orderid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<MyCourse> getOrderUserList(int orderid, int pageNow, int pageSize) throws ElException;
	public int getOrderUserListCount(int orderid) throws ElException;
	/**
	 * 通过ID获得课程信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Course getCourseById(int id) throws ElException ;
	/**
	 * 获得我的分配课程订单
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public List<CourseOrder> getMyOrderCourseList(Department depTree, int depid,int userid,CourseOrder corder,Timestamp stime,Timestamp otime,int pageNow,int pageSize) throws ElException;
	public int getMyOrderCourseListCount(Department depTree, int depid,int userid,CourseOrder corder,Timestamp stime,Timestamp otime) throws ElException;
	/**
	 * 去支付课程分配订单信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public CourseOrder  getcourseOrderinfo(int id) throws ElException;
	/**
	 * 去支付培训班订单信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public ClassOrder  getclassOrderinfo(int id) throws ElException;
	/**
	 * 删除人员课程培训班关系记录
	 * @param orderid
	 * @throws ElException
	 */
	

	public void deleOrderCourseClass(int orderid)throws ElException;
	/**
	 * 删除人员课程培训班订单关系记录
	 * @param orderid
	 * @throws ElException
	 */	
	public void deleOrderCourseClassOrder(int orderid)throws ElException;
	/**
	 * 订单记录
	 * @param orderid
	 * @throws ElException
	 */	
	public void deleOrder(int orderid)throws ElException;
	/**
	 * 订单详情记录
	 * @param orderid
	 * @throws ElException
	 */	
	public void deleOrderInfo(int orderid)throws ElException;
	/**
	 * 得到该课程可操作人员列表
	 * @param pageNow
	 * @param pageSize
	 * @param depid
	 * @param courseid
	 * @param state
	 * @param userid
	 * @param starttime
	 * @param endtime
	 * @param elUser
	 * @param depTree
	 * @param role
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> listAssignedUser(int pageNow, int pageSize,int depid, int courseid, int state,
			List<Integer> userid, String starttime, String endtime,ELUser elUser,Department depTree,int role)
	throws ElException;
	public int listAssignedUserSize(int depid,
			int courseid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser,Department depTree,int role) throws ElException ;
	/**
	 * 查询有权限的培训班关联相关课程类型
	 * 培训班选择课程用
	 * @author luocw
	 */
	public List<Course> listAllSelectCourse(CourseType ctypeTree,int depid, String name, int ctid,
			int pageNow, int pageSize,int status,int classId ,int role) throws ElException;
	/**
	 * 查询分页
	 * @param ctypeTree
	 * @param depid
	 * @param name
	 * @param ctid
	 * @param status
	 * @param classId
	 * @param role
	 * @return
	 * @throws ElException
	 */
	public int listAllSelectCourseSize(CourseType ctypeTree, int depid,
			String name, int ctid, int status, int classId, int role)
			throws ElException;
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
	public List<ELUser> gettoEroomInfoselectUser(Department depTree,Department dep,String table ,int tid, int eroomid, ELUser elUser, String starttime,String endtime,int pageNow, int pageSize) throws ElException ;
	public int gettoEroomInfoselectUserSize(Department depTree,Department dep,String table,int id, int eroomid, ELUser elUser, String starttime,String endtime) throws ElException;
	
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
	public List<ELUser> gettoClassInfoselectUser(Department depTree,Department dep,String table ,int tid, int classid,ELUser elUser, String starttime,String endtime,int pageNow, int pageSize) throws ElException ;
	public int gettoClassInfoselectUserSize(Department depTree,Department dep,String table ,int tid, int classid,ELUser elUser, String starttime,String endtime) throws ElException;
	
	/**
	 * 得到当前管理员所能操作的该培训班待订购用户
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
			String endtime, ELUser elUser,int sub_department,Department depTree) throws ElException ;
	public int listAssignedUserSize(int depid,
			int classid, int state, List<Integer> userid, String starttime,
			String endtime, ELUser elUser,int sub_department,Department depTree) throws ElException;
	/**
	 * 添加用户培训班关系
	 * @param userid
	 * @param classid
	 * @param orderid
	 * @throws ElException
	 */
	public void addUserOrderClass(int userid, int classid,int orderid) throws ElException;
	/**
	 * 得到培训班课程总价，免费课程数量，课程总数。
	 * @param classid
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public ClassOrder  getAllPriceCourseByClassidID (int classid,int userid) throws ElException;
	
	
	/**
	 * 得到我的培训班订单
	 * @param userid
	 * @param corder
	 * @param stime
	 * @param otime
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<ClassOrder> getMyOrderClassList(Department depTree, int depid,int userid,ClassOrder corder,Timestamp stime,Timestamp otime,int pageNow,int pageSize) throws ElException;
	public int getMyOrderClassListSize(Department depTree, int depid,int userid,ClassOrder corder,Timestamp stime,Timestamp otime) throws ElException;
	/**
	 * 得到培训班订单内课程价格信息
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<Course>  getClassOrderCourseByid(int classid) throws ElException;
	/**
	 * 删除培训班订单
	 * @param orderid
	 * @throws ElException
	 */
	public void deleClassOrder(int orderid) throws ElException ;
	/**
	 * 只删除订单表通过ID
	 * @param orderid
	 * @throws ElException
	 */
	public void delespOrder(int  orderid) throws ElException;
	/**
	 * 得到我的个人订单列表
	 * @param userid
	 * @param start
	 * @param end
	 * @param orderid
	 * @param orderstatus
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public   List<Order>  myorderlist(int userid,Timestamp start,Timestamp end ,Integer orderid,
			int orderstatus,int pageNow, int pageSize) throws ElException;
	public   int  myorderlistsize(int userid,Timestamp start,Timestamp end ,Integer orderid,int orderstatus)
	throws ElException;
	/**
	 * 修改订单状态
	 * @param status
	 * @param orderid
	 * @throws ElException
	 */
	public void orderstatusupd(int status,int orderid) throws ElException;
	/**
	 * 得到所有个人订单
	 * @param start
	 * @param end
	 * @param orderid
	 * @param orderstatus
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public   List<Order>  allorderlist(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end ,Integer orderid,
			int orderstatus,int pageNow, int pageSize) throws ElException;
	
	public   int  allorderlistsize(ElNode tree, 
			int sublibs,ELUser elUser,Timestamp start,Timestamp end ,Integer orderid,
			int orderstatus) throws ElException;
	/**
	 * 得到订单中的商品
	 * @param orderid
	 * @return
	 * @throws ElException
	 */
	public List<Commodity> getorderCommoditybyid(int  orderid) throws ElException;
	/**
	 * 删除个人订单
	 * @param orderid
	 * @throws ElException
	 */
	public void order_userorderdele(int  orderid) throws ElException;
	/**
	 * 培训班订单内学员信息
	 * @param orderid
	 * @return
	 * @throws ElException
	 */
	public  List<MyCourse>  getclassorderuserinfolist(int orderid ,int pageNow , int pageSize) throws ElException;
	public  int getclassorderuserinfolistsize(int orderid) throws ElException;
	
	/**
	 * 查询单独分配课程有没有考场， 并返回考场，没有返回NULL
	 * @param courseid
	 * @return
	 * @throws ElException
	 */
	public  ExamRoom  getroomid(int  courseid ) throws ElException;
	/**
	 * 查询该培训班所对应的所有考场
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public  List<ExamRoom>  getroomlistbyclassid(int  classid ) throws ElException;
	
	//9月20 后加
	public List<Course> newlistAllSelectCourse(CourseType ctypeTree, int depid,
			String name, int ctid, int pageNow, int pageSize, int status,
			int classId, int role) throws ElException ;
	public int newlistAllSelectCourseSize(CourseType ctypeTree, int depid,
			String name, int ctid, int status, int classId, int role)
			throws ElException ;
	
	public List<MyCourse> listMyCourse(int userid, int pageNow, int pageSize)
	throws ElException ;
	public int listMyCourseSize(int userid) throws ElException ;
	public List<MyClass> listMyStudyClass(int userid, int pageNow, int pageSize)
	throws ElException ;
	public int listMyStudyClassSize(int userid) throws ElException;
	//一下为部门分配培训班
	/**
	 * 查出该培训班分配的部门情况
	 */
	public Department getExampracDepTree(int pracid, int stopid,boolean containStop) throws ElException ;
	/**
	 * 查询class_department表中该培训班已分配的部门节点ID
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public List<Integer>  getdepartmenttoclass(int  classid) throws ElException;
	/**
	 * 删除培训班部门分配关系
	 * @param classid
	 * @throws ElException
	 */
	public  void  delete_deptclass(int classid) throws ElException;
	/**
	 * 培训班分配给部门
	 * @param deptid
	 * @param classid
	 * @throws ElException
	 */
	public void addExamprac_dep(int deptid,int classid) throws ElException ;
	/**
	 * 通过部门id找到该部门分配的培训班
	 * @param deptid
	 * @return
	 * @throws ElException
	 */
	public List<Integer>  getdepartmenttoclassbydepid(int  deptid) throws ElException;
	
	public  List<Integer>  getdempParentid(int  mydepid) throws ElException;
	public ElClass getApplyForeElclassById(int classid) throws ElException;
	
	
}
