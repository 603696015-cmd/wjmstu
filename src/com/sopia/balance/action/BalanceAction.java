package com.sopia.balance.action;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.balance.dao.BalanceDao;
import com.sopia.balance.entites.Balance;
import com.sopia.balance.entites.Income;
import com.sopia.balance.entites.RechargeInfo;
import com.sopia.common.ElException;
import com.sopia.duman.dao.RoleDao;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.shopping.entities.Commodity;
import com.sopia.shopping.entities.Order;
import com.sopia.shopping.entities.Shopping;
import com.sopia.statman.dao.ShoppingCartDao;

public class BalanceAction extends BaseAction {
	private Department depTree; //部门树
    private Department department;
    private Integer deptid; //部门ID
    private ELUser elUser;//用户
    private int sub_department;
    private List<Balance> elUsers;
    private BalanceDao balanceDao;
    private List<ElRole> roles;
    private RoleDao roleDao;
    private Float   balanceValue;
    private int username;
    private List<RechargeInfo>  re;
    private int balancestatue;
    private float mybalance;
    private int shoppingCount;
    private Balance   balance;
    private List<Income>	li;
	private ShoppingDao  	shoppingDao;
	private List<Commodity>	commodity;
	private Order			order;//个人订单
	private ShoppingCartDao shoppingCartDao;
	private int		orderid;
	private Shopping      shopping ; 
	private List<Shopping>    ls;
	private String caozuoname;//操作者
	private String caozuousername;//操作者
	private int type;
	private Timestamp		start;
	private Timestamp		end;
	private RechargeInfo 	rechargeInfo;
	private  String userids;
	
	
	private int all;
	
    
	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCaozuousername() {
		return caozuousername;
	}

	public void setCaozuousername(String caozuousername) {
		this.caozuousername = caozuousername;
	}

	public String getCaozuoname() {
		return caozuoname;
	}

	public void setCaozuoname(String caozuoname) {
		this.caozuoname = caozuoname;
	}

	public RechargeInfo getRechargeInfo() {
		return rechargeInfo;
	}

	public void setRechargeInfo(RechargeInfo rechargeInfo) {
		this.rechargeInfo = rechargeInfo;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}

	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}

	public ShoppingDao getShoppingDao() {
		return shoppingDao;
	}

	public void setShoppingDao(ShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}

	public List<Commodity> getCommodity() {
		return commodity;
	}

	public void setCommodity(List<Commodity> commodity) {
		this.commodity = commodity;
	}

	public List<Income> getLi() {
		return li;
	}

	public void setLi(List<Income> li) {
		this.li = li;
	}

	public Balance getBalance() {
		return balance;
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

	public int getShoppingCount() {
		return shoppingCount;
	}

	public void setShoppingCount(int shoppingCount) {
		this.shoppingCount = shoppingCount;
	}

	public float getMybalance() {
		return mybalance;
	}

	public void setMybalance(float mybalance) {
		this.mybalance = mybalance;
	}

	public int getBalancestatue() {
		return balancestatue;
	}

	public void setBalancestatue(int balancestatue) {
		this.balancestatue = balancestatue;
	}

	public List<RechargeInfo> getRe() {
		return re;
	}

	public void setRe(List<RechargeInfo> re) {
		this.re = re;
	}

	public int getUsername() {
		return username;
	}

	public void setUsername(int username) {
		this.username = username;
	}

	public void setBalanceValue(Float balanceValue) {
		this.balanceValue = balanceValue;
	}

	public float getBalanceValue() {
		return balanceValue;
	}

	public void setBalanceValue(float balanceValue) {
		this.balanceValue = balanceValue;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<Balance> getElUsers() {
		return elUsers;
	}

	public void setElUsers(List<Balance> elUsers) {
		this.elUsers = elUsers;
	}

	public BalanceDao getBalanceDao() {
		return balanceDao;
	}

	public void setBalanceDao(BalanceDao balanceDao) {
		this.balanceDao = balanceDao;
	}

	public List<ElRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ElRole> roles) {
		this.roles = roles;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
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
	/**
	 * 获取可操作的部门员工余额信息
	 * @return
	 * @throws Exception 
	 */
	
	public String getMyAllBalance() throws ElException{
		
		
		//获得部门树
		if(balanceValue!=null&&balanceValue!=0){//如果有金额，则进行增资
			int myUserId = getSessionIntValue(ElConstants.SESSION_USERID);
			balanceDao.addbalance(balanceValue,username , myUserId);
			balanceValue=null;
		}
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(this.getDeptid()==null||this.getDeptid()<=0){
			department = depTree;
		}else{
			department  = departmentDao.getDepById(this.getDeptid());
		}
		
		sub_department = elUser == null? 1 : sub_department;
		//查询用户信息和余额
		elUsers = balanceDao.getUserlistBalance(department, elUser,sub_department, getPageNow(), getPageSize());
		count = balanceDao.getUserlistBalanceSize(department, elUser, sub_department);
		roles = roleDao.listRoles();
		
		return "getMyAllBalance";
	}
	public String  allgetMyAllBalance() throws NumberFormatException, ElException{
		String bookinfo[] = userids.split(",");
		for (int i = 0; i < bookinfo.length; i++) {
			
			if(balanceValue!=null&&balanceValue!=0){//如果有金额，则进行增资
				int myUserId = getSessionIntValue(ElConstants.SESSION_USERID);
				balanceDao.addbalance(balanceValue,Integer.valueOf(bookinfo[i] ), myUserId);
				
			}
			
		}
		balanceValue=null;
		return getMyAllBalance();
		
	}
	public String  geteluserzengzi_recharge_info1() throws ElException{
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(this.getDeptid()==null||this.getDeptid()<=0){
			department = depTree;
		}else{
			department  = departmentDao.getDepById(this.getDeptid());
		}
		
		sub_department = elUser == null? 1 : sub_department;
	
		//查询用户增资记录
		re = balanceDao.getUserRechargeInfoById(department, elUser,sub_department,caozuoname,caozuousername,type,getPageNow(), getPageSize());
		count = balanceDao.getUserRechargeInfoById(department, elUser,sub_department,caozuoname,caozuousername,type);
		roles = roleDao.listRoles();
		if(all==0){
			return "geteluserzengzi_recharge_info1_success";
		}else{
			return "selecteluserzengzi_recharge_info1_success";
		}
	}
	/**
	 * 获取用户充值记录信息
	 * @return
	 * @throws ElException 
	 */
	public String geteluser_recharge_info() throws ElException{
		re=balanceDao.getUserRechargeInfoById(username, 3);
		return "geteluser_recharge_info";
		
	}
	/**
	 * 获取用户充值记录信息余额转移
	 * @return
	 * @throws ElException 
	 */
	public String geteluser_subrecharge_info() throws ElException{
		re=balanceDao.getUserRechargeInfoById(username, 2);
		return "geteluser_recharge_info";
		
	}
	/*
	 * 余额转移
	 */
	public String getMyAllBalance2() throws ElException{
		mybalance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
		
		if(balanceValue!=null&&balanceValue!=0&&balanceDao.checkbalance(getSessionIntValue(ElConstants.SESSION_USERID), balanceValue)){
				//进行减资
				balanceDao.subBalance( getSessionIntValue(ElConstants.SESSION_USERID), username, balanceValue);
				//进行增资
				
				int myUserId = getSessionIntValue(ElConstants.SESSION_USERID);
				balanceDao.subaddbalance(balanceValue,username , myUserId);
				balanceValue=null;
			
		}
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(this.getDeptid()==null||this.getDeptid()<=0){
			department = depTree;
		}else{
			department  = departmentDao.getDepById(this.getDeptid());
		}
		
		sub_department = elUser == null? 1 : sub_department;
		//查询用户信息和余额
		mybalance= balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
		elUsers = balanceDao.getUserlistBalance(department, elUser,sub_department, getPageNow(), getPageSize());
		count = balanceDao.getUserlistBalanceSize(department, elUser, sub_department);
		roles = roleDao.listRoles();
		return "getMyAllBalance2";
	}
	public String allgetMyAllBalance2() throws ElException{
		String bookinfo[] = userids.split(",");
		if(balanceValue!=null&&balanceValue!=0&&balanceDao.checkbalance(getSessionIntValue(ElConstants.SESSION_USERID), balanceValue*bookinfo.length)&&bookinfo.length>0){
			//进行减资
			
			balanceDao.subBalance( getSessionIntValue(ElConstants.SESSION_USERID), username, balanceValue*bookinfo.length);
			//进行增资
			int myUserId = getSessionIntValue(ElConstants.SESSION_USERID);
			
			for (int i = 0; i < bookinfo.length; i++) {
				
				if(balanceValue!=null&&balanceValue!=0){//如果有金额，则进行增资
					balanceDao.subaddbalance(balanceValue,Integer.valueOf(bookinfo[i] ) , myUserId);
				}
				
			}
			
			
		}
		if(balanceValue==null){
			setElmessage("请您输入的转移金额");
		}
		if(bookinfo.length==0){
			setElmessage("请选择要转移余额的用户");
		}
		if(!balanceDao.checkbalance(getSessionIntValue(ElConstants.SESSION_USERID), balanceValue*bookinfo.length)){
			setElmessage("您的余额不足");
		}
		balanceValue=null;
		return getMyAllBalance2();

		
	}
	public String getmybalanceinfo() throws ElException{
		
		balance=balanceDao.getbalancebyid(getSessionIntValue(ElConstants.SESSION_USERID));
		
		return "getmybalanceinfo_success";
	}
	/**
	 * 得到个人收支记录
	 * @return
	 * @throws ElException
	 */
	public String balance_incomeList() throws ElException{
		li = balanceDao.myIncomebyuserid(getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count = balanceDao.myIncomebyuseridsize(getSessionIntValue(ElConstants.SESSION_USERID));
		
		
		
		return  "balance_incomeList_success";
	}
	/**
	 * 得到收支明细订单中商品列表
	 * @return
	 * @throws ElException
	 */
	public  String  balance_ordercommodity() throws ElException{
		commodity=shoppingDao.getorderCommoditybyid(orderid);
		order=shoppingCartDao.shoppingCartOrder(orderid);
		return "balance_ordercommodity_success";
	}
	/**
	 * 得到收支明细中详细信息
	 * @return
	 * @throws ElException 
	 */
	public  String  balance_incomerechargeInfo() throws ElException{
		rechargeInfo=balanceDao.getRechargeInfoById(orderid);
		return  "balance_incomerechargeInfo_success";
	}
	/**
	 * 部门消费明细
	 * @return
	 * @throws ElException
	 */
	public  String  balance_dempincomeList() throws ElException{
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
					true);
		}
		if(this.getDeptid()==null||this.getDeptid()<=0){
			department = depTree;
		}else{
			department  = departmentDao.getDepById(this.getDeptid());
		}
		shopping = balanceDao.getdempshopping(department, sub_department,elUser,start,end);
		ls= balanceDao.getdempshopping(department, sub_department,elUser,start,end, getPageNow(),getPageSize());
		count = balanceDao.getdempshoppingSize(department, sub_department,elUser,start,end);
	
		return "balance_dempincomeList_success";
		
	}

	public Shopping getShopping() {
		return shopping;
	}

	public void setShopping(Shopping shopping) {
		this.shopping = shopping;
	}

	public List<Shopping> getLs() {
		return ls;
	}

	public void setLs(List<Shopping> ls) {
		this.ls = ls;
	}
	

}
