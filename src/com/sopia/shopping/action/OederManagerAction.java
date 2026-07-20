package com.sopia.shopping.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.balance.dao.BalanceDao;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.common.ElException;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.shopping.entities.Commodity;
import com.sopia.shopping.entities.Order;
import com.sopia.statman.dao.ShoppingCartDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.util.AlipayNotify;

public class OederManagerAction extends BaseAction {
	private ShoppingDao  	shoppingDao;
	private Timestamp       start;//搜索时间  开始时间
	private Timestamp       end;//结束时间
	private Integer			orderid;//订单id
	private int 			orderstatus;//订单状态
	private List<Order>     listo;//个人订单列表
	private Order			order;//个人订单
	private List<Commodity>	commodity;
	private int             statusflag;//判断是否管理员操作
	private ShoppingCartDao shoppingCartDao;
	private int             orderdeleid;
	private Integer				userid;
	private Department depTree; //部门树
    private Department department;
    private Integer deptid; //部门ID
    private ELUser elUser;//用户
    private int sub_department;
    private List<ElRole> roles;
    private RoleDao roleDao;
	private BalanceDao 		balanceDao;
	private float 			balance;
	private String WIDseller_email;//卖家支付宝账号
	private String WIDout_trade_no;//商品订单号
	private String WIDsubject;//订单名称
	private double WIDtotal_fee;//付款金额
	private String WIDbody;//订单描述
	private String WIDshow_url;//商品展示地址
	public BalanceDao getBalanceDao() {
		return balanceDao;
	}
	public void setBalanceDao(BalanceDao balanceDao) {
		this.balanceDao = balanceDao;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public List<ElRole> getRoles() {
		return roles;
	}
	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public Department getDepTree() {
		return depTree;
	}
	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public int getSub_department() {
		return sub_department;
	}
	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getOrderdeleid() {
		return orderdeleid;
	}
	public void setOrderdeleid(int orderdeleid) {
		this.orderdeleid = orderdeleid;
	}
	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}
	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}
	public int getStatusflag() {
		return statusflag;
	}
	public void setStatusflag(int statusflag) {
		this.statusflag = statusflag;
	}
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public int getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(int orderstatus) {
		this.orderstatus = orderstatus;
	}
	public List<Order> getListo() {
		return listo;
	}
	public void setListo(List<Order> listo) {
		this.listo = listo;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<Commodity> getCommodity() {
		return commodity;
	}
	public void setCommodity(List<Commodity> commodity) {
		this.commodity = commodity;
	}
	public ShoppingDao getShoppingDao() {
		return shoppingDao;
	}
	public void setShoppingDao(ShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}
	/**
	 * 得到我的个人订单列表
	 * @return
	 * @throws ElException
	 */
	public  String  order_myorderlistinit() throws ElException{
		int a = 0;
		if(userid==null){
			a=1;
			userid = getSessionIntValue(ElConstants.SESSION_USERID);
		}
		listo=shoppingDao.myorderlist(userid, start, end, orderid, orderstatus, getPageNow(), getPageSize());
		count=shoppingDao.myorderlistsize(userid, start, end, orderid, orderstatus);
		if(a==1)return "order_myorderlistinit_success";
		else{
			return "balance_myorderlistinit_success";
		}
	} 
	/**
	 * 得到订单中商品列表
	 * @return
	 * @throws ElException
	 */
	public  String  order_ordercommodity() throws ElException{
		commodity=shoppingDao.getorderCommoditybyid(orderid);
		order=shoppingCartDao.shoppingCartOrder(orderid);
		return "ordercommodity_success";
	}
	/**
	 * 得到订单中商品列表，和用户余额信息
	 * @return
	 * @throws ElException
	 */
	public  String  order_ordercommodityinfo() throws ElException{
		commodity=shoppingDao.getorderCommoditybyid(orderid);
		order=shoppingCartDao.shoppingCartOrder(orderid);
		/**
		 * 得到余额信息
		 */
		balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
		return "order_ordercommodityinfo_success";
	}
	/**
	 * 得到所有个人订单列表
	 * @return
	 * @throws ElException
	 */
	public  String  order_allorderlistinit() throws ElException{
		
		
		depTree = departmentDao.getDepTree_level1(
				getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
				true);
		if(this.getDeptid()==null||this.getDeptid()<=0){
			department = depTree;
		}else{
			department  = departmentDao.getDepById(this.getDeptid());
		}
		listo=shoppingDao.allorderlist(department, sub_department,elUser,start, end, orderid, orderstatus, getPageNow(), getPageSize());
		count=shoppingDao.allorderlistsize(department, sub_department,elUser, start, end, orderid, orderstatus);
		
		roles = roleDao.listRoles();
		return "order_allorderlistinit_success";
	}
	/**
	 * 修改订单状态
	 * @return
	 * @throws ElException
	 */
	public String order_orderstatusupd() throws ElException{
		shoppingDao.orderstatusupd(orderstatus, orderid);
		return  order_ordercommodity();
	}
	/**
	 * 删除订单
	 * @return
	 * @throws ElException
	 */
	public String order_orderdele() throws ElException{
		shoppingDao.order_userorderdele(orderdeleid);
		return  order_myorderlistinit();
	}
	public String order_chongzhi() throws ElException{
		return  "order_chongzhi_success";
	}
	
	public synchronized String doChongzhi() throws Exception{
		boolean flag = true;
					
				if(flag){//充值成功后
					ELUser elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
					//站内添加充值金额
					balanceDao.addbalance(balance, elUser.getId(), elUser.getId());
				}
		
		return "doChongzhi_success";
	}
	
	//充值成功后回调父页面重构充值还是付款
	public String reload_money() throws ElException{
		//orderid
		order=shoppingCartDao.shoppingCartOrder(orderid);
		//7得到用户余额信息
		balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
//		float f = balance - order.getSumpeice();
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			String d= "{\"balance\":\"" + balance + "\",\"sumpeice\":\""+order.getSumpeice()+"\"}";
			 System.out.println(d);
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ClassDao classDao;
	private EroomDao eroomDao;
	private StudyQuizDao studyQuizDao;


	public ClassDao getClassDao() {
		return classDao;
	}
	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}
	public EroomDao getEroomDao() {
		return eroomDao;
	}
	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}
	public StudyQuizDao getStudyQuizDao() {
		return studyQuizDao;
	}
	public void setStudyQuizDao(StudyQuizDao studyQuizDao) {
		this.studyQuizDao = studyQuizDao;
	}
	
	public String getWIDseller_email() {
		return WIDseller_email;
	}
	public void setWIDseller_email(String dseller_email) {
		WIDseller_email = dseller_email;
	}
	public String getWIDout_trade_no() {
		return WIDout_trade_no;
	}
	public void setWIDout_trade_no(String dout_trade_no) {
		WIDout_trade_no = dout_trade_no;
	}
	public String getWIDsubject() {
		return WIDsubject;
	}
	public void setWIDsubject(String dsubject) {
		WIDsubject = dsubject;
	}
	public double getWIDtotal_fee() {
		return WIDtotal_fee;
	}
	public void setWIDtotal_fee(double dtotal_fee) {
		WIDtotal_fee = dtotal_fee;
	}
	public String getWIDbody() {
		return WIDbody;
	}
	public void setWIDbody(String dbody) {
		WIDbody = dbody;
	}
	public String getWIDshow_url() {
		return WIDshow_url;
	}
	public void setWIDshow_url(String dshow_url) {
		WIDshow_url = dshow_url;
	}
	
	public String chongzhi()throws ElException{
		WIDseller_email="437570249@qq.com";  //支付宝账号
		WIDout_trade_no = getOrderid()+"";  //订单号
		WIDsubject = "培训班";	//订单名称
		WIDbody = "培训班购买"; //订单描述
		WIDshow_url = "";  //商品展示地址
		
		return "chongzhi";
	}
}
