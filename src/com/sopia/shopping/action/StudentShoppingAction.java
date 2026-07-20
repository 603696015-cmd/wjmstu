package com.sopia.shopping.action;

import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.balance.dao.BalanceDao;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.ClassDao;
import com.sopia.common.ElException;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.shopping.entities.Commodity;
import com.sopia.shopping.entities.Order;
import com.sopia.statman.dao.ShoppingCartDao;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyCourse;
/**
 * 购物车操作
 * @author Administrator
 *
 */
public class StudentShoppingAction extends BaseAction {
	private ShoppingCartDao shoppingCartDao;
	private List<Commodity> listC; //购入车内的商品
	private int             dele;//判断是否是删除操作
	private Commodity       commodity;//商品
	private ShoppingDao  	shoppingDao;
	private float 			zongjia;
	private Order 			order;    
	private BalanceDao 		balanceDao;
	private float 			balance;
	private ClassDao 		classDao;    
	private int             cccount;//购物车内， 已被后台分配且订购支付成功的课程数；
	private int             guoqicount;//过期培训班数量；
	private int             ordercount;//已订购课程或培训班数量；
	private List<Commodity> listfalse; //未成功加入的商品
	private List<Commodity> listsuccess; //成功加入的商品	
	private int             successcount;//成功加入商品数
	private int				status;//订单状态
	private int 			type;//种类
	
	private int 			id;
	private int 			count;
	
	private ExamRoom		examRoom;
	private List<ExamPaper> examPapers;
	private EroomDao 		eroomDao;
	private StudyQuizDao 	studyQuizDao;
	private List<ELUser>    elUsers;
	private List<MyCourse>  myCourses;//分配的学员
	private int orderid;
	
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public ExamRoom getExamRoom() {
		return examRoom;
	}
	public void setExamRoom(ExamRoom examRoom) {
		this.examRoom = examRoom;
	}
	public List<ExamPaper> getExamPapers() {
		return examPapers;
	}
	public void setExamPapers(List<ExamPaper> examPapers) {
		this.examPapers = examPapers;
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
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
	public List<MyCourse> getMyCourses() {
		return myCourses;
	}
	public void setMyCourses(List<MyCourse> myCourses) {
		this.myCourses = myCourses;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSuccesscount() {
		return successcount;
	}
	public void setSuccesscount(int successcount) {
		this.successcount = successcount;
	}
	public int getOrdercount() {
		return ordercount;
	}
	public void setOrdercount(int ordercount) {
		this.ordercount = ordercount;
	}
	public List<Commodity> getListsuccess() {
		return listsuccess;
	}
	public void setListsuccess(List<Commodity> listsuccess) {
		this.listsuccess = listsuccess;
	}
	public List<Commodity> getListfalse() {
		return listfalse;
	}
	public void setListfalse(List<Commodity> listfalse) {
		this.listfalse = listfalse;
	}
	public int getGuoqicount() {
		return guoqicount;
	}
	public void setGuoqicount(int guoqicount) {
		this.guoqicount = guoqicount;
	}
	public ClassDao getClassDao() {
		return classDao;
	}
	public void setClassDao(ClassDao classDao) {
		this.classDao = classDao;
	}
	public int getCccount() {
		return cccount;
	}
	public void setCccount(int cccount) {
		this.cccount = cccount;
	}
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
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public float getZongjia() {
		return zongjia;
	}
	public void setZongjia(float zongjia) {
		this.zongjia = zongjia;
	}
	public ShoppingDao getShoppingDao() {
		return shoppingDao;
	}
	public void setShoppingDao(ShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}
	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}
	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}
	public List<Commodity> getListC() {
		return listC;
	}
	public void setListC(List<Commodity> listC) {
		this.listC = listC;
	}
	public int getDele() {
		return dele;
	}
	public void setDele(int dele) {
		this.dele = dele;
	}
	public Commodity getCommodity() {
		return commodity;
	}
	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}
	
	
	
	
	
	
	/**
	 * 用户购物车信息操作
	 * @return
	 * @throws ElException
	 */
	public String getShoppingCart() throws ElException{
		//判断是否登录
		if(getSessionIntValue(ElConstants.SESSION_USERID)==0){
			return "login";
		}
		int userid=getSessionIntValue(ElConstants.SESSION_USERID);
		//等于1则执行删除一条货物
		if(dele==1){
			shoppingCartDao.deleCommodityToShoppingCart(commodity.getId());			
		}
		//等于二则清空购物车
		if(dele==2){
			shoppingCartDao.deleALLCommodityToShoppingCart(userid);
		}
		//等于3 则修改货物数量
		if(dele==3){
			
			
		}
		
		listC=shoppingCartDao.getMyShoppingCart(userid);
		
		for (Commodity co : listC) {
			if(co.getCommoditytype()==3 || co.getCommoditytype()==4) {//如果有实物商品
				status=2;//添加此状态用于判断是否显示 加入地址 联系方式等信息
				break;
			}
			
		}
		
		if(listC.size()>0){
			
			zongjia = this.getZongJia(listC);
		}
		return "shoppingCart_success";
	}
	public float getZongJia( List<Commodity> listC){
		
		float price = 0;
		for (Commodity c : listC) {
			price=price+c.getAllp();
			
		}
		return price;
		
	} 
	public  String  commoditycount_upd() throws ElException{
		shoppingCartDao.shopping_count_upd(id, count);
		
		return getShoppingCart();
	}
	/**
	 * 确认订购
	 * @return
	 * @throws ElException
	 */
	public String shoppingCartToOrder() throws ElException{
		//判断是否登录
		if(getSessionIntValue(ElConstants.SESSION_USERID)==0){
			return "login";
		}		
		int userid=getSessionIntValue(ElConstants.SESSION_USERID);
		//得到支付宝回调页面传过来的orderid
		if(orderid==0){
			orderid = Integer.parseInt(getRequest().getParameter("orderid")==null?"0":getRequest().getParameter("orderid"));
		}
		//1得到用户购物车的所有商品信息  购买时不考虑已有的培训班内是否存在相同课程
		listC=shoppingCartDao.getMyShoppingCartByOrder(userid,orderid);
		if(listC.size()!=0){//判断购物车内是否有货物	
			listsuccess=new ArrayList<Commodity>();
			listfalse=new ArrayList<Commodity>();

			//2计算总价
			zongjia=this.getZongJia(listC);
			//3生成订单，返回订单ID 0 代表 已订购  1, 代表个人订单	
			if (order==null){
				if(!shoppingCartDao.checkMakeOrder(orderid,userid)){
					shoppingDao.markorder1(orderid,userid, 0, zongjia, 1,"","","");
				}
			}else{
				if(!shoppingCartDao.checkMakeOrder(orderid,userid)){
					shoppingDao.markorder1(orderid,userid, 0, zongjia, 1,order.getShoujianren(),order.getTel(),order.getNote());
				}
			}

			
			for (Commodity co : listC) {//先判断商品类型
				if(co.getCommoditytype()==1){//如果是课程，插入学生课程关系表，
					//若已存在学员或管理员分配，或订购，但未支付，或者已支付 但课程时间已过期
					if(shoppingCartDao.checkUserCourse(co.getCommodityid(), userid)){//若已经支付则 且尚在使用期 给总价减去 该课程的价格
						//给总价减去要去掉的商品价格
						zongjia-=co.getNowp();
						//给已拥有课程数 加一
						cccount++;
						//将添加失败课程加入 失败列表
						listfalse.add(co);						
						
					}else{
						//判断该课程是否存在在该用户的其他订单中，并且订单时间未过期，如果在，则不生成该课程的新订购信息，从该次订单中去掉该课程						
						if(shoppingCartDao.checkUserCourseOrder(co.getCommodityid(), userid)){
							//如果存在，则不生成该课程的新订购信息，从该次订单中去掉该课程
							//将添加失败课程加入 失败列表							
							listfalse.add(co);	
							//给以存在订单的商品数+1
							ordercount++;
							//给总价减去要去掉的商品价格
							zongjia-=co.getNowp();
						}else{
							//将添加成功课程加入 成功列表
							listsuccess.add(co);
							//4插入信息到订单详情表
							shoppingDao.markorderInfo(co.getCommodityid(), orderid, 
									co.getCommoditytype(), co.getNowp(), co.getCount(), 0, 0);
						}
					}
				}else if(co.getCommoditytype()==2){//如果是培训班
					//如果该学员之前已经被管理员分配该培训班，不对培训班与学员关系做修改，生成新订单，修改原订单关系表
					if(shoppingCartDao.checkUserClassfinishDate(co.getCommodityid())){
						//如果该培训班已经过期
						guoqicount++;//过期商品加一
						listfalse.add(co);	//将添加失败课程加入 失败列表
						//给总价减去要去掉的商品价格
						zongjia-=co.getNowp();
					}
					else{
//						//判断用户是否已拥有该培训班
//						if(shoppingCartDao.checkUserClass(co.getCommodityid(), userid)){
//							//如果拥有则
//							//将添加失败课程加入 失败列表							
//							listfalse.add(co);	
//							//给以拥有的商品数+1
//							cccount++;
//							//给总价减去要去掉的商品价格
//							zongjia-=co.getNowp();
//						}else if(shoppingCartDao.checkUserClassOrder(co.getCommodityid(), userid)){//判断是否存在于其他订单
//							//如果存在
//							//将添加失败课程加入 失败列表							
//							listfalse.add(co);	
//							//给以拥有的订单商品数+1
//							ordercount++;
//							//给总价减去要去掉的商品价格
//							zongjia-=co.getNowp();
//						}else{
//							//会造成管理员分配订单学员的丢失
//							shoppingDao.addUserOrderClass(userid,co.getCommodityid(), orderid);
//							//将添加成功课程加入 成功列表
//							
//							listsuccess.add(co);
//							//4插入信息到订单详情表
//							shoppingDao.markorderInfo(co.getCommodityid(), orderid, 
//									co.getCommoditytype(), co.getNowp(), co.getCount(), 0, 0);
//						}
						//会造成管理员分配订单学员的丢失
						shoppingDao.addUserOrderClass(userid,co.getCommodityid(), orderid);
						//将添加成功课程加入 成功列表
						
						listsuccess.add(co);
						//4插入信息到订单详情表
						shoppingDao.markorderInfo(co.getCommodityid(), orderid, 
								co.getCommoditytype(), co.getNowp(), co.getCount(), 0, 0);
						
					}
					
				}
				else if(co.getCommoditytype()==5){//考场订单
					listsuccess.add(co);
					//4插入信息到订单详情表
					shoppingDao.markorderInfo(co.getCommodityid(), orderid, 
							co.getCommoditytype(), co.getNowp(), co.getCount(), 0, 0);
				}
				
				
			}
			shoppingCartDao.updOrderzongjia(zongjia, orderid);
			if(listsuccess.size()==0){//如果没有成功加入任何商品，则删除此次订单
				shoppingDao.delespOrder(orderid);
				
			}
			successcount=listsuccess.size();
			
			//5清空购物车内的货物
//			shoppingCartDao.deleALLCommodityToShoppingCart(userid);		
			//6返回订单信息		
			order=shoppingCartDao.shoppingCartOrder(orderid);
			//7得到用户余额信息
			balance=balanceDao.getmybalance(userid);
		}
			
		return "shoppingCartToOrder";
		
		
		
	}
	/**
	 * 支付订单
	 * @return
	 * @throws ElException
	 */
	public synchronized  String userPay() throws ElException{
		//判断是否登录
		if(getSessionIntValue(ElConstants.SESSION_USERID)==0){
			return "login";
		}	
		order=shoppingCartDao.shoppingCartOrder(order.getId());
		balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
		if(order.getBuydate()==null){//判断该订单是否已支付
			if(shoppingCartDao.checkIfBook(order.getId())){//判断所购买商品里是否存在实体商品
				status = 2;
			}else{//如果不存在直接变为已收货状态
				status = 4;
			}
			if(balance>=order.getSumpeice()){
				/**
				 * create or replace procedure userpay( userid_ number,userbalance_ float,orderid_ number,status_ number)
					as isinsc number ;
					begin
					  isinsc :=0;
					  select count(1)  into isinsc from sp_order  where
					  id = orderid_ and buydate is null ;--得出这个商品是否被支付
					  if isinsc=1 then--如果没有
					     update sp_order set  buydate = sysdate ,status=status_  where sp_order.id=orderid_  ;
					      update eluser_balance set eluser_balance.userbalance=(eluser_balance.userbalance-userbalance_)
					  			where eluser_balance.userid=userid_ ;
					  end if;
					end ;
				 */
				shoppingCartDao.userPay(getSessionIntValue(ElConstants.SESSION_USERID)
						, order.getSumpeice(), order.getId(),status);
				balance=balance-order.getSumpeice();
//				balanceDao.updatemybalance(getSessionIntValue(ElConstants.SESSION_USERID),balance);
				order=shoppingCartDao.shoppingCartOrder(order.getId());
//				this.classorcourse(order,getSessionIntValue(ElConstants.SESSION_USERID));
			}
			
		}
		
		boolean flag = true;
		if(flag){//付款成功后，插入分配表
			
			listC=shoppingCartDao.getMyShoppingCart(getSessionIntValue(ElConstants.SESSION_USERID));
			if(listC.size()!=0){//判断购物车内是否有货物	

				
				for (Commodity co : listC) {//先判断商品类型
					if(co.getCommoditytype()==1){
						//插入分配关系
						shoppingDao.assignedUser(co.getCommodityid(),getSessionIntValue(ElConstants.SESSION_USERID) ,0,0);
						//插入订单学员课程关系表
						shoppingDao.addUserOrder(getSessionIntValue(ElConstants.SESSION_USERID) , co.getCommodityid(), order.getId());
					}else if(co.getCommoditytype()==2){//如果是培训班
						//如果该学员之前已经被管理员分配该培训班，不对培训班与学员关系做修改，生成新订单，修改原订单关系表
						//插入各个分配表
						//以下分配关系应当在调用支付接口成功后再执行
						classDao.assign2userAdd3(getSessionIntValue(ElConstants.SESSION_USERID),co.getCommodityid(),ClassConstants.CLASS_SQFS_FP);//插入培训班分配表的同时插入课程分配表
						//1  加入培训班人员关系表	study_class   co.getCommodityid()
						//2  加入课程人员关系表		study_course  
						//3  加入考场人员关系表		study_room     classid和courseid
						//4  加入考场中的试卷分配表	study_exampaper
						//1、2之前代码已经处理过  只需要处理3、4即可
//						int roomid = eroomDao.getRoomidByClassid_cisco(co.getCommodityid());
						//获取--》培训班中所有的课程--》然后分配到所有课程对应的考场
						List<Course> ccs = classDao.listStudyCourses(co.getCommodityid(),getSessionIntValue(ElConstants.SESSION_USERID));
						//wsj 1011修改 获取培训班考场
//						if(ccs!=null&&ccs.size()>0){
//							for(int i=0;i<ccs.size();i++){
//								roomid = ccs.get(i).getEroom().getId();
//								if(roomid!=0){
//									
//									eroomDao.adduser2eroom_cisco(co.getCommodityid(),roomid, getSessionIntValue(ElConstants.SESSION_USERID),3);//3为购买
//									List<ExamPaper> papers  = eroomDao.getEroomeps(roomid);
//									if(papers!=null){
//										for(int j=0;j<papers.size();j++){
//											// 检测该学员是否分配了该试卷
//											if (!studyQuizDao.checkStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), papers.get(j).getId(), roomid, co.getCommodityid())) {
//												// 添加该学员到 学员试卷表中
//												studyQuizDao.addStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), papers.get(j).getId(), roomid,co.getCommodityid());
//											}
//										}
//									}
//								}
//							}
//						}
						ExamRoom eroom = classDao.elclassRoom(co.getCommodityid());
						int roomid = 0;
						if(eroom!=null){
						roomid = eroom.getId();
						}
						if(roomid!=0){
							
							eroomDao.adduser2eroom_cisco(co.getCommodityid(),roomid, getSessionIntValue(ElConstants.SESSION_USERID),3);//3为购买
							List<ExamPaper> papers  = eroomDao.getEroomeps(roomid);
							if(papers!=null){
								for(int j=0;j<papers.size();j++){
									// 检测该学员是否分配了该试卷
									if (!studyQuizDao.checkStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), papers.get(j).getId(), roomid, co.getCommodityid())) {
										// 添加该学员到 学员试卷表中
										studyQuizDao.addStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), papers.get(j).getId(), roomid,co.getCommodityid());
									}
								}
							}
						}
						
						
					}else if(co.getCommoditytype()==5){//考场
						eroomDao.adduser2eroom_cisco(0,co.getCommodityid(), getSessionIntValue(ElConstants.SESSION_USERID),3);//3为购买
						List<ExamPaper> papers  = eroomDao.getEroomeps(co.getCommodityid());
						if(papers!=null){
							for(int i=0;i<papers.size();i++){
								// 检测该学员是否分配了该试卷
								if (!studyQuizDao.checkStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), papers.get(i).getId(), co.getCommodityid(), -1)) {
									// 添加该学员到 学员试卷表中
									studyQuizDao.addStudyExamPaper(getSessionIntValue(ElConstants.SESSION_USERID), papers.get(i).getId(), co.getCommodityid(),-1);
								}
							}
						}
					}
					
				}
			}
			
			//添加支出明细
//			balanceDao.addbalance(order.getSumpeice(), getSessionIntValue(ElConstants.SESSION_USERID), getSessionIntValue(ElConstants.SESSION_USERID));
			
			//5清空购物车内的货物
			shoppingCartDao.deleALLCommodityToShoppingCart(getSessionIntValue(ElConstants.SESSION_USERID));	//分配完成后，删除购物车中的培训班或者考场
			
//			balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
//			System.out.println(balance);
		}
		
		return "userpay_success";
	}
	public String houtaiuserPay() throws ElException{
		//判断是否登录
		if(getSessionIntValue(ElConstants.SESSION_USERID)==0){
			return "login";
		}	
		order=shoppingCartDao.shoppingCartOrder(order.getId());
		balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
		if(order.getBuydate()==null){//判断该订单是否已支付
			if(shoppingCartDao.checkIfBook(order.getId())){//判断所购买商品里是否存在实体商品
				status = 2;
			}else{//如果不存在直接变为已收货状态
				status = 4;
			}
			balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
			if(balance>=order.getSumpeice()){
				shoppingCartDao.userPay(getSessionIntValue(ElConstants.SESSION_USERID)
						, order.getSumpeice(), order.getId(),status);
				balance=balance-order.getSumpeice();
				order=shoppingCartDao.shoppingCartOrder(order.getId());
				this.classorcourse(order,getSessionIntValue(ElConstants.SESSION_USERID));
			}
			
		}
		
		return "houtaiuserPay_success";
	}
	public String classcoursePay() throws ElException{
		order=shoppingCartDao.shoppingCartOrder(order.getId());
		balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
		if(order.getBuydate()==null){//判断该订单是否已支付
			
			balance=balanceDao.getmybalance(getSessionIntValue(ElConstants.SESSION_USERID));
			if(balance>=order.getSumpeice()){
				shoppingCartDao.userPay(getSessionIntValue(ElConstants.SESSION_USERID)
						, order.getSumpeice(), order.getId(),4);
				balance=balance-order.getSumpeice();
				order=shoppingCartDao.shoppingCartOrder(order.getId());
				
				//得到该订单内的学生列表
				myCourses=shoppingDao.getOrderUserList(order.getId(), 9999999, 1);
				for (MyCourse m : myCourses) {
					this.classorcourse(order,m.getUser().getId());
				}
			}
			
		}
		
		return "classcoursePay_success";
		
		
	}
	/**
	 * 添加商品并去购物车
	 * @return
	 * @throws ElException
	 */
	public String shopping_addandto() throws ElException{
		commodity.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		//判断商品订单是否已存在
		orderid = shoppingCartDao.checkUserOrder(commodity);
		if(orderid<=0){
			orderid = shoppingCartDao.addCommodityToShoppingCart(commodity);
		}
		return getShoppingCart();
	}
	
	
	/**
	 * 分配学员进入课程对应的考场和试卷
	 * @param course
	 * @param userid
	 * @throws ElException
	 */
	public void examroom_assignwcInit(int course,int userid) throws ElException {
		
		examRoom = shoppingDao.getroomid(course);//根据课程id找出该课程的考场
		if(null!=examRoom){
		examPapers = eroomDao.getEroomepwithusizes(examRoom.getId());
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		if (examPapers == null || examPapers.size() == 0) {
			//如果该考场没有试卷则什么都不做
		}else{//否则 判断试卷有没有被添加进去
			if (!eroomDao.checkuser2eroom(examRoom.getId(), userid, examRoom.getClassid())) {
				eroomDao.adduser2eroom(examRoom.getId(), userid, 1, examRoom.getClassid(),
						CourseConstants.EXAMROOM_FPFS_SQ);
			}
			for (ExamPaper examPaper : examPapers) {
				if (!studyQuizDao.checkStudyExamPaper(userid, examPaper.getId(), examRoom.getId(), examRoom
						.getClassid())) {
					// 添加该学员到 学员试卷表中
					studyQuizDao.addStudyExamPaper(userid, examPaper.getId(), examRoom.getId(),
							examRoom.getClassid());
				}
			}
			}
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
			ElLoggerConstants.LOG_MOD_EROOM,
			ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle() + "（添加学员）",
			ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
		}
		
	}
	/**
	 * 分配学员进入培训班对应的考场和试卷
	 * @param course
	 * @param userid
	 * @throws ElException
	 */
	public void examroom_classassignwcInit(int classid,int userid) throws ElException {
		
		List<ExamRoom>  examRooms = shoppingDao.getroomlistbyclassid(classid);//根据培训班id找出该课程的考场
		examPapers =null;
		if(examRooms!=null){
		for (ExamRoom examRoom : examRooms) {
			 examPapers=eroomDao.getEroomepwithusizes(examRoom.getId());
			 if (examPapers == null || examPapers.size() == 0) {
					//如果该考场没有试卷则什么都不做
				}else{//否则 判断试卷有没有被添加进去
					if (!eroomDao.checkuser2eroom(examRoom.getId(), userid, examRoom.getClassid())) {
						eroomDao.adduser2eroom(examRoom.getId(), userid, 1, examRoom.getClassid(),
								CourseConstants.EXAMROOM_FPFS_SQ);
					}
					for (ExamPaper examPaper : examPapers) {
						if (!studyQuizDao.checkStudyExamPaper(userid, examPaper.getId(), examRoom.getId(), examRoom
								.getClassid())) {
							// 添加该学员到 学员试卷表中
							studyQuizDao.addStudyExamPaper(userid, examPaper.getId(), examRoom.getId(),
									examRoom.getClassid());
						}
					}
					}
					ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_EROOM,
					ElLoggerConstants.LOG_TYPE_ADD, examRoom.getTitle() + "（添加学员）",
					ElLoggerConstants.LOG_RES_SUCC, examRoom.getId());
			 
			}
		}
		
		// canAssignUsers = eroomDao.listCanAssignToRoomUsers(examRoom.getId());
		// bassignedUsers = eroomDao.listAssignToRoomUsers(examRoom.getId());
		
		
	}
	
	
	public  void classorcourse(Order order,int userid) throws ElException{
		//得到个人订单内的货物
		listC=shoppingDao.getorderCommoditybyid(order.getId());
		for (Commodity c : listC) {//循环商品， 根据商品类型进行考场试卷分配
			if(c.getCommoditytype()==1){//是课程
				examroom_assignwcInit(c.getCommodityid(),userid);
			}
			else if(c.getCommoditytype()==2){//是培训班
				examroom_classassignwcInit(c.getCommodityid(),userid);
			}
			
		}
		
	}



}
