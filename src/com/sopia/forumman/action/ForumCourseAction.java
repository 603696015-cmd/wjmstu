package com.sopia.forumman.action;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;

import com.sopia.bookinfo.dao.BookInfoDao;
import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.bookinfo.entities.Bookinfo;
import com.sopia.classman.dao.ElClTypeDao;
import com.sopia.classman.entities.ElClType;
import com.sopia.classman.entities.ElClass;
import com.sopia.common.CheckHtml;
import com.sopia.common.ElException;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.SystemConfOp;

import com.sopia.courseman.dao.CourseCommentDao;
import com.sopia.courseman.dao.CourseDao;
import com.sopia.courseman.dao.CourseTypeDao;
import com.sopia.courseman.dao.EroomDao;
import com.sopia.courseman.entities.Course;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.courseman.entities.CoursePage;

import com.sopia.courseman.entities.CourseType;


import com.sopia.duman.entities.ELUser;

import com.sopia.forumman.dao.ForumCourseDao;
import com.sopia.forumman.entities.ForumClassClub;
import com.sopia.forumman.entities.ForumCourseClub;
import com.sopia.frontman.dao.FrontDao;
import com.sopia.knowledgeman.dao.KnowledgeDao;

import com.sopia.knowledgeman.entities.KnowledgeType;

import com.sopia.newsandmess.entities.News;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.dao.ProductDao;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;
import com.sopia.questionman.entities.StuffLib;
import com.sopia.shopping.dao.ShoppingDao;
import com.sopia.statman.dao.ShoppingCartDao;


public class ForumCourseAction extends BaseAction {
	private List<Course> zxCourses;
	// private List<Course> rmCourses;
	private Course course;
	private CourseType ctypeTree; 
	private CourseTypeDao ctypeDao;
	private FrontDao frontDao;
	private List<News> zxNotices;
	private ForumCourseDao  forumcourseDao;
	private KnowledgeType kltypeTree;
	private KnowledgeDao knowledgeDao;
	private KnowledgeType kltype;
	private EroomDao eroomDao;
	private CourseDao courseDao;
	private List<CourseType> ctls;
	private ELUser elUser;
	private StringBuffer explain;
	private int shoppingCount;
	private ShoppingCartDao shoppingCartDao;
	private ShoppingDao shoppingDao;
	private CourseCommentDao courseCommentDao;
	private CourseComment courseComment;
	private List<CourseComment> listcc;
	private CourseComment userComment;//当前用户提交的评论
	private int ctype ;//评论类型
	private int mycourse;//判断是否用有该课程，0未拥有，1 已用有
	private int mycourseorder;//判断是否已有该课程订单；
	private boolean audit;//审核判定
	private String  name ;//前台总体查询的名称
	private int     nametype;//前台查询的类别
	private BookTypeTree bookTypeTree;
	private String sbookinfo;
	private List<Bookinfo> listb;
	private BookInfoDao bookInfoDao; 
	
	
	//培训班
	
	private ElClType cltypeTree;
	private ElClTypeDao elClTypeDao;
	private ElClType cltype; 
	private ElClass elclass;
	private List<ElClass> elclasses;
	private List<News> zxtzggs;//最新通知公告
	private List<News> tjtzggs;//推荐通知公告
	
	//前台中心
	private List<ForumCourseClub>   listList;
	private List<ForumClassClub>   classlistList;
	private String type;
	
	//会员服务中心
	private IndexDao indexDao; 
	private PfmsUser pfmsUser;
	private int	  id;
	private Product product;
	private ProductDao productDao;
	private ProductType ptypeTree;
	private int shopId;
	
	private List<CoursePage> cpages;
	private String mod;
	private List<StuffLib> stuffs; 
	private int usercount;
	private List<ELUser> users;
	
	public List<ELUser> getUsers() {
		return users;
	}
	public void setUsers(List<ELUser> users) {
		this.users = users;
	}
	public int getUsercount() {
		return usercount;
	}
	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}
	public List<StuffLib> getStuffs() {
		return stuffs;
	}
	public void setStuffs(List<StuffLib> stuffs) {
		this.stuffs = stuffs;
	}
	public String getMod() {
		return mod;
	}
	public void setMod(String mod) {
		this.mod = mod;
	}
	public List<CoursePage> getCpages() {
		return cpages;
	}
	public void setCpages(List<CoursePage> cpages) {
		this.cpages = cpages;
	}
	
	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	
	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}
	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public ProductDao getProductDao() {
		return productDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	public ProductType getPtypeTree() {
		return ptypeTree;
	}
	public void setPtypeTree(ProductType ptypeTree) {
		this.ptypeTree = ptypeTree;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public IndexDao getIndexDao() {
		return indexDao;
	}
	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}
	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}
	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ForumClassClub> getClasslistList() {
		return classlistList;
	}
	public void setClasslistList(List<ForumClassClub> classlistList) {
		this.classlistList = classlistList;
	}
	public List<ForumCourseClub> getListList() {
		return listList;
	}
	public void setListList(List<ForumCourseClub> listList) {
		this.listList = listList;
	}
	public List<News> getZxtzggs() {
		return zxtzggs;
	}
	public void setZxtzggs(List<News> zxtzggs) {
		this.zxtzggs = zxtzggs;
	}
	public List<News> getTjtzggs() {
		return tjtzggs;
	}
	public void setTjtzggs(List<News> tjtzggs) {
		this.tjtzggs = tjtzggs;
	}
	public ElClType getCltypeTree() {
		return cltypeTree;
	}
	public void setCltypeTree(ElClType cltypeTree) {
		this.cltypeTree = cltypeTree;
	}
	public ElClTypeDao getElClTypeDao() {
		return elClTypeDao;
	}
	public void setElClTypeDao(ElClTypeDao elClTypeDao) {
		this.elClTypeDao = elClTypeDao;
	}
	public ElClType getCltype() {
		return cltype;
	}
	public void setCltype(ElClType cltype) {
		this.cltype = cltype;
	}
	public ElClass getElclass() {
		return elclass;
	}
	public void setElclass(ElClass elclass) {
		this.elclass = elclass;
	}
	public List<ElClass> getElclasses() {
		return elclasses;
	}
	public void setElclasses(List<ElClass> elclasses) {
		this.elclasses = elclasses;
	}
	public BookInfoDao getBookInfoDao() {
		return bookInfoDao;
	}
	public void setBookInfoDao(BookInfoDao bookInfoDao) {
		this.bookInfoDao = bookInfoDao;
	}
	public BookTypeTree getBookTypeTree() {
		return bookTypeTree;
	}
	public void setBookTypeTree(BookTypeTree bookTypeTree) {
		this.bookTypeTree = bookTypeTree;
	}
	public String getSbookinfo() {
		return sbookinfo;
	}
	public void setSbookinfo(String sbookinfo) {
		this.sbookinfo = sbookinfo;
	}
	public List<Bookinfo> getListb() {
		return listb;
	}
	public void setListb(List<Bookinfo> listb) {
		this.listb = listb;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNametype() {
		return nametype;
	}
	public void setNametype(int nametype) {
		this.nametype = nametype;
	}
	public boolean isAudit() {
		return audit;
	}
	public void setAudit(boolean audit) {
		this.audit = audit;
	}
	public int getMycourse() {
		return mycourse;
	}
	public void setMycourse(int mycourse) {
		this.mycourse = mycourse;
	}
	public int getMycourseorder() {
		return mycourseorder;
	}
	public void setMycourseorder(int mycourseorder) {
		this.mycourseorder = mycourseorder;
	}
	public int getCtype() {
		return ctype;
	}
	public void setCtype(int ctype) {
		this.ctype = ctype;
	}
	public CourseComment getUserComment() {
		return userComment;
	}
	public void setUserComment(CourseComment userComment) {
		this.userComment = userComment;
	}
	public List<CourseComment> getListcc() {
		return listcc;
	}
	public void setListcc(List<CourseComment> listcc) {
		this.listcc = listcc;
	}
	public CourseCommentDao getCourseCommentDao() {
		return courseCommentDao;
	}
	public void setCourseCommentDao(CourseCommentDao courseCommentDao) {
		this.courseCommentDao = courseCommentDao;
	}
	public CourseComment getCourseComment() {
		return courseComment;
	}
	public void setCourseComment(CourseComment courseComment) {
		this.courseComment = courseComment;
	}
	public ShoppingDao getShoppingDao() {
		return shoppingDao;
	}
	public void setShoppingDao(ShoppingDao shoppingDao) {
		this.shoppingDao = shoppingDao;
	}
	public int getShoppingCount() {
		return shoppingCount;
	}
	public void setShoppingCount(int shoppingCount) {
		this.shoppingCount = shoppingCount;
	}
	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}
	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}
	public List<Course> getZxCourses() {
		return zxCourses;
	}
	public void setZxCourses(List<Course> zxCourses) {
		this.zxCourses = zxCourses;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public CourseType getCtypeTree() {
		return ctypeTree;
	}
	public void setCtypeTree(CourseType ctypeTree) {
		this.ctypeTree = ctypeTree;
	}
	public CourseTypeDao getCtypeDao() {
		return ctypeDao;
	}
	public void setCtypeDao(CourseTypeDao ctypeDao) {
		this.ctypeDao = ctypeDao;
	}
	public FrontDao getFrontDao() {
		return frontDao;
	}
	public void setFrontDao(FrontDao frontDao) {
		this.frontDao = frontDao;
	}
	public List<News> getZxNotices() {
		return zxNotices;
	}
	public void setZxNotices(List<News> zxNotices) {
		this.zxNotices = zxNotices;
	}
	public ForumCourseDao getForumcourseDao() {
		return forumcourseDao;
	}
	public void setForumcourseDao(ForumCourseDao forumcourseDao) {
		this.forumcourseDao = forumcourseDao;
	}
	public KnowledgeType getKltypeTree() {
		return kltypeTree;
	}
	public void setKltypeTree(KnowledgeType kltypeTree) {
		this.kltypeTree = kltypeTree;
	}
	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}
	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}
	public KnowledgeType getKltype() {
		return kltype;
	}
	public void setKltype(KnowledgeType kltype) {
		this.kltype = kltype;
	}
	public EroomDao getEroomDao() {
		return eroomDao;
	}
	public void setEroomDao(EroomDao eroomDao) {
		this.eroomDao = eroomDao;
	}
	public CourseDao getCourseDao() {
		return courseDao;
	}
	public void setCourseDao(CourseDao courseDao) {
		this.courseDao = courseDao;
	}
	public List<CourseType> getCtls() {
		return ctls;
	}
	public void setCtls(List<CourseType> ctls) {
		this.ctls = ctls;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public StringBuffer getExplain() {
		return explain;
	}
	public void setExplain(StringBuffer explain) {
		this.explain = explain;
	}
	/**
	 * 选课中心选课
	 * @return
	 * @throws ElException
	 */
	public String newcourseIndex() throws ElException {
		int depid = 1; 
	
		int ctid = course.getCtype() == null ? ctypeDao.getCtypeRoot().getId() : course.getCtype().getId();

		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true); 
		if(getSession().getAttribute("roleid")==null){
			getSession().setAttribute("roleid",7);			//为null时设个默认值7给他 
		} 
		if(course!=null && course.getName() !=null && course.getName().equals("填写课程名称....")){
			course.setName("");
		} 
		zxCourses  = forumcourseDao.listAllCourseFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
				getPageNow(), getPageSize(),"5"," and c.isapplication = 1");
		for (Course c : zxCourses) {

			
			//截取长度
			if(c.getDescription()!=null){
			c.setDescription((c.getDescription().length() > 126) ? c.getDescription().substring(0, 123)+ "..." : c.getDescription()) ;
		
			}
		}
		count = forumcourseDao.listAllCourseSizeFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,"5"," and c.isapplication = 1");
		kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
		ctls=courseDao.getCourseType();
		zxNotices = frontDao.listNewsByTid(0, 6, 2, true, ""); 
		if (null != zxNotices)
			for (int i = 0; i < zxNotices.size(); i++) {
				String title = zxNotices.get(i).getTitle();
				title = (title == null) ? "" : (title.length() > 16 ? title
						.substring(0, 15)
						+ "" : title);
				zxNotices.get(i).setTitle(title);
			} 
		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
		for(int i = 0;zxCourses.size() > i ; i++){  
//			列表页不需要申请报名。 注释掉 
			zxCourses.get(i).setCoRegistration(courseDao.getCourseRegistration(zxCourses.get(i).getId()));
			zxCourses.get(i).getCoRegistration().setJoinNumber(courseDao.getJoinNumber(zxCourses.get(i).getId())+"");
			if(courseDao.checkCourseIsUser(zxCourses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){//是否已报名
				zxCourses.get(i).setIsjoin("true");
			}else{
				zxCourses.get(i).setIsjoin("false");
			}
			if(checkIsuserApp(zxCourses.get(i), elUser)){//如果返回false证明有某条不符合条件
				zxCourses.get(i).setIsuserApp(1); 
			}else{
				zxCourses.get(i).setIsuserApp(2);  
			}
		}
		return "courseIndex_success";
	}
	/**
	 * 选课中心选课(2012 12 27) 会员中心的精品课程 
	 * 要求 
	 * ：显示本单位创建的已审核定价的课程列表。
	 * @return
	 * @throws ElException
	 */
	public String newcourseIndexhuiyuanfuwu() throws ElException {
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		if(product == null){
			product = new Product();
		}
		if (product.getPtype() == null
				|| product.getPtype().getId() <= 0) {
			product.setPtype(ptypeTree);
		} else {
			product.setPtype(productDao.getPtypeLibById(product.getPtype().getId()));
		}
		int depid = 1; 
		int ctid = course.getCtype() == null ? ctypeDao.getCtypeRoot().getId() : course.getCtype().getId();

		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true); 
		if(getSession().getAttribute("roleid")==null){
			getSession().setAttribute("roleid",7);			//为null时设个默认值7给他 
		} 
		if(course!=null && course.getName() !=null && course.getName().equals("填写课程名称....")){
			course.setName("");
		} 
		//通过传来的ID  得到该用户的部门
		pfmsUser = indexDao.getUser(id,false);
		depid=pfmsUser.getUser().getDepartment().getId();
		zxCourses  = forumcourseDao.listAllCourseFromThishuiyuanzhongxin(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
				getPageNow(), getPageSize(),"5"," and c.isapplication = 1");
		for (Course c : zxCourses) {

			
			//截取长度
			if(c.getDescription()!=null){
			c.setDescription((c.getDescription().length() > 126) ? c.getDescription().substring(0, 123)+ "..." : c.getDescription()) ;
		
			}
		}
		count = forumcourseDao.listAllCourseFromThissizehuiyuanzhongxin(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,"5"," and c.isapplication = 1");
//		kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
//		ctls=courseDao.getCourseType();
//		zxNotices = frontDao.listNewsByTid(0, 6, 2, true, ""); 
//		if (null != zxNotices)
//			for (int i = 0; i < zxNotices.size(); i++) {
//				String title = zxNotices.get(i).getTitle();
//				title = (title == null) ? "" : (title.length() > 16 ? title
//						.substring(0, 15)
//						+ "" : title);
//				zxNotices.get(i).setTitle(title);
//			} 
//		elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
//		for(int i = 0;zxCourses.size() > i ; i++){  
////			列表页不需要申请报名。 注释掉 
//			zxCourses.get(i).setCoRegistration(courseDao.getCourseRegistration(zxCourses.get(i).getId()));
//			zxCourses.get(i).getCoRegistration().setJoinNumber(courseDao.getJoinNumber(zxCourses.get(i).getId())+"");
//			if(courseDao.checkCourseIsUser(zxCourses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){//是否已报名
//				zxCourses.get(i).setIsjoin("true");
//			}else{
//				zxCourses.get(i).setIsjoin("false");
//			}
//			if(checkIsuserApp(zxCourses.get(i), elUser)){//如果返回false证明有某条不符合条件
//				zxCourses.get(i).setIsuserApp(1); 
//			}else{
//				zxCourses.get(i).setIsuserApp(2);  
//			}
//		}
		shopId = id;
		return "courseIndex_success";
	}
	/**
	 * 培训班是否满足申请要求
	 * @author  
	 * @return
	 * @throws ElException
	 */
	public boolean checkIsuserApp(Course cou , ELUser eluser)throws ElException{  
		boolean IsuserApp =true;
		boolean jz =true;
		boolean ds =true;
		boolean zj =true;
		boolean zw =true;
		boolean gw =true;
		boolean nl =true;
		boolean xb =true;
		boolean bm =true;
		boolean erooms =true;
		boolean elClass =true;
		explain = new StringBuffer();
		if(cou.getCoRegistration().getDslist()==null){//地市不限
			ds =true;
		}else{
			if(cou.getCoRegistration().getDslist()!=null&&elUser.getDishi()>0&&cou.getCoRegistration().getDslist().contains(elUser.getDishi())){ 
				ds = true;//dslist不为空 uds不为空  dslist 里没有该地市
			}else{
				 explain.append("地市 ");
				ds = false;//dslist不为空 uds为空 或者 dslist 里没有该地市
			}	
		}
		if(cou.getCoRegistration().getJzlist()== null){
			jz =true;//不限
		}else{
			if(cou.getCoRegistration().getJzlist()!= null && elUser.getJingzhong()>0 && cou.getCoRegistration().getJzlist().contains(elUser.getJingzhong())){
				jz = true;
			}else{
				 explain.append("工种 ");
				jz = false;
			}
		}
		if(cou.getCoRegistration().getZjlist()==null){
			zj =true;//不限
		}else{
			if(cou.getCoRegistration().getZjlist()!=null&&elUser.getZhiji()>0&&cou.getCoRegistration().getZjlist().contains(elUser.getZhiji())){
				zj = true;
			}else{
				explain.append("职级 ");
				zj = false;
			}	
		}
		if(cou.getCoRegistration().getZwlist()==null){
			zw =true;//不限
		}else{
			if(cou.getCoRegistration().getZwlist()!=null&&elUser.getZhiwu()>0&&cou.getCoRegistration().getZwlist().contains(elUser.getZhiwu())){
				zw = true;
			}else{
				explain.append("职务 ");
				zw = false;
			}
		}
		if(cou.getCoRegistration().getGwlist()==null){
			gw = true;
		}else{			
			if(cou.getCoRegistration().getGwlist()!=null&&elUser.getGangwei()!=null&&cou.getCoRegistration().getGwlist().contains(elUser.getGangwei())){
				gw = true;
			}else{
				explain.append("岗位 ");
				gw = false;	
			}
		}
		//年龄段
		if(cou.getCoRegistration().getStartAge() == 0 && cou.getCoRegistration().getStopAge() == 0){
			nl = true;
		}else{
			if(eluser.getAGE()>cou.getCoRegistration().getStartAge() && cou.getCoRegistration().getStopAge()>eluser.getAGE()){
				nl = true;
			}else{
				explain.append("年龄 ");
				nl = false;	
			}			
		}
		//性别
		if(cou.getCoRegistration().getSex() == null || cou.getCoRegistration().getSex().equals("不限")){
			xb = true;
		}else if(cou.getCoRegistration().getSex().equals(eluser.getSex())){
			xb = true;
		}else{
			explain.append("性别 ");
			xb = false;	
		}
		 
		//部门 
		if(cou.getCoRegistration().getTreeType()==null){//部门不限
			bm =true;
		}else{
			if(cou.getCoRegistration().getTreeTypes()!=null&&
					elUser.getDepartment()!=null&&
					cou.getCoRegistration().getTreeTypelist().contains(elUser.getDepartment().getId()+"")){ 
				bm = true; 
			}else{
				explain.append("部门 ");
				bm = false; 
			}	
		}
		
		//考场 
		if(cou.getCoRegistration().getExamRoom()==null || cou.getCoRegistration().getExamRoom().size() == 0){//考场不限
			erooms =true;
		}else{
			String sqlWhere = "";
			if(cou.getCoRegistration().getEroomScreeningWay()==1){
				sqlWhere = " and ispassed  = 1";
			}else if(cou.getCoRegistration().getEroomScreeningWay()==2) {
				sqlWhere = " and ispassed  = 0"; 
			}
			if(!cou.getCoRegistration().getExamRooms().equals("")&& 
					eroomDao.checkEroomIspassed(cou.getCoRegistration().getExamRooms(), getSessionIntValue(ElConstants.SESSION_USERID),sqlWhere)){ 
				erooms = true; 
			}else{
				explain.append("考场");
				erooms = false; 
			}	
		}
		//培训班 
		if(cou.getCoRegistration().getElclass()==null || cou.getCoRegistration().getElclass().size() == 0){//培训班不限
			elClass = true;
		}else{
			String sqlWhere = "";
			if(cou.getCoRegistration().getClassScreeningWay()==1){
				sqlWhere = "and certificateno is not null";
			}else if(cou.getCoRegistration().getClassScreeningWay()==2) {
				sqlWhere = "and certificateno is null";
			}
			if(!cou.getCoRegistration().getElclasss().equals("")&& 
					eroomDao.checkElclassIspassed(cou.getCoRegistration().getElclasss(), getSessionIntValue(ElConstants.SESSION_USERID),sqlWhere)){ 
				elClass = true; 
			}else{
				explain.append("培训班");
				elClass = false; 
			}	
		}
		
		if(jz&&ds&&zj&&zw&&gw&&nl&&xb&&bm&&erooms&&elClass){ //  
			IsuserApp = true;
		}else{
			IsuserApp = false;
		}
		return IsuserApp;
	}
	/**
	 * 选课中心课程显示
	 * @return
	 * @throws ElException 
	 */
	public String getCourseIndexview() throws ElException{
		audit=SystemConfOp.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		//1得到课程信息
		course=shoppingDao.getCourseById(course.getId());
		//2得到评论星级信息
		courseComment=courseCommentDao.getCourseCommentPoint(course.getId(),ctype);
		//3得到用户评论信息
		listcc=courseCommentDao.getCourseAllComment(course.getId(),ctype,getPageNow6(), getPageSize6());
		count=courseCommentDao.getCourseAllCommentSize(course.getId(),ctype);
		//得到用户人数
		users = courseCommentDao.getEluserByCourseid(course.getId());
		if(shoppingCartDao.checkUserCourse(course.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))mycourse=1;
		if(shoppingCartDao.checkUserCourseOrder(course.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))mycourseorder=1;
		;
	//	return "getCourseIndexview_success";
		return "courseview";
		
	}
	/**
	 * 新选课中心课程显示
	 */
	public String getCourseMessage() throws ElException{
		audit=SystemConfOp.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		//1得到课程信息
		course=shoppingDao.getCourseById(course.getId());
		//2得到评论星级信息
		courseComment=courseCommentDao.getCourseCommentPoint(course.getId(),ctype);
		//3得到用户评论信息
		
		listcc=courseCommentDao.getCourseAllComment(course.getId(),ctype,getPageNow6(), getPageSize6());
		count=courseCommentDao.getCourseAllCommentSize(course.getId(),ctype);
		if(shoppingCartDao.checkUserCourse(course.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))mycourse=1;
		if(shoppingCartDao.checkUserCourseOrder(course.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))mycourseorder=1;
		;
		//课程对应章节
		cpages = courseDao.getPagesByCourseid(course.getId());
		//章节对应的附件
		stuffs = courseDao.getCpageStuffsByCoursid(course.getId());
		//购买该课程的用户
		users = courseCommentDao.getEluserByCourseid(course.getId());
		for(int i=0;i<users.size();i++){
			char first = users.get(i).getUsername().charAt(0);
			char end = users.get(i).getUsername().charAt(users.size()-1);
			users.get(i).setUsername(first+"**"+end);
		}
		if(mod.equals("brief")){
			return "brief";
		}
		if(mod.equals("comment")){
			return "comment";
		}
		if(mod.equals("teacher")){
			return "teacher";
		}
		if(mod.equals("student")){
			return "student";
		}
		if(mod.equals("courseware")){
			return "courseware";
		}
		return "error";
		
	}
	
	/**
	 * 课程评论
	 * @return
	 * @throws ElException 
	 */
	public String saveCourseComment() throws ElException{
		
		
		//1保存课程评论信息
		//设置用户id
		userComment.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		//设置评论类型为 课程
		userComment.setType(1);
		
		audit=SystemConfOp.getBooleanValue(ElConstants.STUFF_ISFTOPIC);
		//设置评论状态
		if(audit){//如果需要审核
			userComment.setStatus(2);
		}else{
			
			userComment.setStatus(1);
			
		}
		//设置课程id
		userComment.setCourseid(course.getId());
		courseCommentDao.saveCourseComment(userComment);
		if(shoppingCartDao.checkUserCourse(course.getId(), getSessionIntValue(ElConstants.SESSION_USERID)))mycourse=1;
		userComment=null;
		return getCourseIndexview();
	}
	
	/**
	 * 对店铺或者产品的留言
	 * @return
	 * @throws ElException
	 */
	public String saveShopComment() throws ElException{
		String resultPage = "";
		userComment.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		courseCommentDao.saveShopComment(userComment);
		if(type != null){
			if(type.equals("content"))
				resultPage = "content";
		}else
			resultPage = "success";
		return resultPage;
	}
	
	/**
	 * 前台总体查询
	 * @return
	 * @throws ElException
	 */
	public String  bookinfocourseclass() throws ElException{
		if(nametype==3){
			if(name==null||"填写名称....".equals(name))
			{ 
				name="";
				}
			bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
			Bookinfo bi = new Bookinfo();
			bi.setName(name);
			listb=bookInfoDao.front_bookinfoAllList(bi, bookTypeTree, 1,getPageNow(), getPageSize());
			for (Bookinfo b : listb) {
				//过滤图书简介
				sbookinfo=CheckHtml.getString(b.getBookinfo());
				//截取长度
				b.setBookinfo((sbookinfo.length() > 120) ? sbookinfo.substring(0, 117)+ "..." : sbookinfo) ;
			}
			count=bookInfoDao.front_bookinfoAllListSize(bi, bookTypeTree, 1);
			return "bookinfocourseclass_success";
		}
		if(nametype==1){
			int depid = 1; 
			
			ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true); 
			if(getSession().getAttribute("roleid")==null){
				getSession().setAttribute("roleid",7);			//为null时设个默认值7给他 
			} 
			course = new Course();
			int ctid = 1;
			
			
			if(name==null || name.equals("填写名称....")){
				
				course.setName("");
			} else{
				course.setName(name);
			}
			zxCourses  = forumcourseDao.listAllCourseFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,
					getPageNow(), getPageSize(),"5"," and c.isapplication = 1");
			for (Course c : zxCourses) {
				
				//截取长度
				if(c.getDescription()!=null){
				c.setDescription((c.getDescription().length() > 126) ? c.getDescription().substring(0, 123)+ "..." : c.getDescription()) ;
			
				}
			}
			count = forumcourseDao.listAllCourseSizeFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, ctid,"5"," and c.isapplication = 1");
			kltypeTree = knowledgeDao.getKltypeTree(1, 0, true);
			ctls=courseDao.getCourseType();
			zxNotices = frontDao.listNewsByTid(0, 6, 2, true, ""); 
			if (null != zxNotices)
				for (int i = 0; i < zxNotices.size(); i++) {
					String title = zxNotices.get(i).getTitle();
					title = (title == null) ? "" : (title.length() > 16 ? title
							.substring(0, 15)
							+ "" : title);
					zxNotices.get(i).setTitle(title);
				} 
			elUser = userDao.getUserById(getSessionIntValue(ElConstants.SESSION_USERID));
			for(int i = 0;zxCourses.size() > i ; i++){  
	//			列表页不需要申请报名。 注释掉 
				zxCourses.get(i).setCoRegistration(courseDao.getCourseRegistration(zxCourses.get(i).getId()));
				zxCourses.get(i).getCoRegistration().setJoinNumber(courseDao.getJoinNumber(zxCourses.get(i).getId())+"");
				if(courseDao.checkCourseIsUser(zxCourses.get(i).getId(), getSessionIntValue(ElConstants.SESSION_USERID))){//是否已报名
					zxCourses.get(i).setIsjoin("true");
				}else{
					zxCourses.get(i).setIsjoin("false");
				}
				if(checkIsuserApp(zxCourses.get(i), elUser)){//如果返回false证明有某条不符合条件
					zxCourses.get(i).setIsuserApp(1); 
				}else{
					zxCourses.get(i).setIsuserApp(2);  
				}
			}
			course=null;
			return "courseIndex_success";
		}
		else {
			cltypeTree = elClTypeDao.getCltypeTree(1,ElConstants.TREE_FIANL, true); 
			//depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
			
			int cltid = cltype == null ? cltypeTree.getId(): cltype.getId();
			//初始化类别id 
			if(cltype==null){
				cltype=new ElClType(cltid);
			}
			elclass = new ElClass();
			if(name==null || name.equals("填写名称....")){
			
				elclass.setName("");
			}
			else{
				elclass.setName(name);
				
			}
			elclasses = forumcourseDao.getApplyForeElclass(cltypeTree, cltid,elclass, 1,"  ",getPageNow(),getPageSize()); //不限制条数， 用于获取到可申请的培训班
			for (ElClass ecl : elclasses) {
				//截取长度
				if(ecl.getDescription()!=null){
				ecl.setDescription((ecl.getDescription().length() > 126) ? ecl.getDescription().substring(0, 123)+ "..." : ecl.getDescription()) ;
				}
			}
			count = forumcourseDao.getApplyForeElclasssize(cltypeTree, cltid, elclass, 1, "");
			  //最新通知公告
		    this.zxtzggs=this.frontDao.listZxNews(8,1);
		    //最新推荐通知公告
		    this.tjtzggs=this.frontDao.listZxNews(8, 1, 1);
			return  "forum_getAllclass_success";
			
		}
		
	}
	public  String   forum_getAllclass() throws ElException{
		cltypeTree = elClTypeDao.getCltypeTree(1,ElConstants.TREE_FIANL, true); 
		//depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
		
		int cltid = cltype == null ? cltypeTree.getId(): cltype.getId();
		//初始化类别id 
		if(cltype==null){
			cltype=new ElClType(cltid);
		}
		if(elclass!=null && elclass.getName().equals("填写培训班名称....")){
			elclass.setName("");
		}
		elclasses = forumcourseDao.getApplyForeElclass(cltypeTree, cltid,elclass, 1,"",getPageNow(),getPageSize());
		for (ElClass ecl : elclasses) {
			//截取长度
			if(ecl.getDescription()!=null){
			ecl.setDescription((ecl.getDescription().length() > 126) ? ecl.getDescription().substring(0, 123)+ "..." : ecl.getDescription()) ;
			}
		}
		count = forumcourseDao.getApplyForeElclasssize(cltypeTree, cltid, elclass, 1, "");
		  //最新通知公告
	    this.zxtzggs=this.frontDao.listZxNews(8,1);
	    //最新推荐通知公告
	    this.tjtzggs=this.frontDao.listZxNews(8, 1, 1);
		return  "forum_getAllclass_success";
	}
	public  String   forum_getAllclasshuiyuanfuwu() throws ElException{
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		if(product == null){
			product = new Product();
		}
		if (product.getPtype() == null
				|| product.getPtype().getId() <= 0) {
			product.setPtype(ptypeTree);
		} else {
			product.setPtype(productDao.getPtypeLibById(product.getPtype().getId()));
		}
		int depid = 1; 
		cltypeTree = elClTypeDao.getCltypeTree(1,ElConstants.TREE_FIANL, true); 
		//depTree = departmentDao.getDepTree(getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,true);
		
		int cltid = cltype == null ? cltypeTree.getId(): cltype.getId();
		//初始化类别id 
		if(cltype==null){
			cltype=new ElClType(cltid);
		}
//		if(elclass!=null && elclass.getName().equals("填写培训班名称....")){
//			elclass.setName("");
//		}
		//通过传来的ID  得到该用户的部门
		pfmsUser = indexDao.getUser(id,false);
		depid=pfmsUser.getUser().getDepartment().getId();
		elclasses = forumcourseDao.getApplyForeElclasshuiyuanfuwu(cltypeTree,depid, cltid,elclass, 1,"",getPageNow(),getPageSize());
		for (ElClass ecl : elclasses) {
			//截取长度
			if(ecl.getDescription()!=null){
			ecl.setDescription((ecl.getDescription().length() > 126) ? ecl.getDescription().substring(0, 123)+ "..." : ecl.getDescription()) ;
			}
		}
		count = forumcourseDao.getApplyForeElclasssizehuiyuanfuwu(cltypeTree,depid, cltid, elclass, 1, "");
//		  //最新通知公告
//	    this.zxtzggs=this.frontDao.listZxNews(8,1);
//	    //最新推荐通知公告
//	    this.tjtzggs=this.frontDao.listZxNews(8, 1, 1);
		return  "forum_getAllclass_success";
	}
	
	/**
	 * 前台课程中心
	 * @return
	 * @throws ElException
	 */
	public  String  forum_courseclub() throws ElException{
		List<CourseType>   listid  = new ArrayList<CourseType>();
		//首先得到二级课程目录类别
		listid=forumcourseDao.getcourseerjijiedian();
		int depid = 1; 
		ctypeTree = ctypeDao.getCtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true); 
		listList = new ArrayList<ForumCourseClub>(); //初始化课程中心总集合
		if(listid!=null){
		for (CourseType typeid : listid) {
			ForumCourseClub f = new ForumCourseClub();
			f.setName(typeid);
			//设置10条最新课程
			f.setZuixincours(forumcourseDao.listAllCourseFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), course, typeid.getId(),
					10, 1,"5"," and c.isapplication = 1"));
			Course c= new Course();
			c.setHot(2);
			//设置最热的三条课程信息
			f.setHotcours(forumcourseDao.listAllCourseFromThis(ctypeTree,depid,getSessionIntValue(ElConstants.SESSION_ROLE), c, typeid.getId(),
					3, 1,"5"," and c.isapplication = 1"));
			for (Course ccc : f.getHotcours()) {
				
				//截取长度
				ccc.setDescription((ccc.getDescription().length() > 126) ? ccc.getDescription().substring(0, 123)+ "..." : ccc.getDescription()) ;
			}
			listList.add(f);//
		}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forum_courseclub_success_phone";
		}
		return  "forum_courseclub_success";
	}
	/**
	 * 前台选班中心
	 * @return
	 * @throws ElException
	 */
	public  String  forum_classclub() throws ElException{
		List<ElClType>   listid  = new ArrayList<ElClType>();
		//首先得到二级课程目录类别
		listid=forumcourseDao.getclasserjijiedian();
		cltypeTree = elClTypeDao.getCltypeTree(1,ElConstants.TREE_FIANL, true); 
		classlistList = new ArrayList<ForumClassClub>(); //初始化课程中心总集合
		if(listid!=null){
		for (ElClType typeid : listid) {
			ForumClassClub f = new ForumClassClub();
			f.setElClType(typeid);
			//设置10条最新班级
			f.setZuixinelClass(forumcourseDao.getApplyForeElclass(cltypeTree, typeid.getId(),elclass, 1,"  ",10,1));
			//设置最热的三条班级信息
			f.setHotelClass(forumcourseDao.getApplyForeElclass(cltypeTree, typeid.getId(),elclass, 1,"  ",3,1));
			String ss =  "";
			for (ElClass ecl : f.getHotelClass()) {
				ss = ecl.getDescription();
				if(ss != null){
					if(ss.length() > 126){
						ss = ss.substring(0, 123)+ "...";
					}
				}else{
					ss = "";
				}
				//截取长度
				ecl.setDescription(ss) ;
//				ecl.setDescription(( ecl.getDescription().length() > 126) ? ecl.getDescription().substring(0, 123)+ "..." : ecl.getDescription()) ;
				
			}
			classlistList.add(f);//
		}
		}
		boolean b = httpRequestDeviceUtils.isMobileDevice(getRequest());
		if(b==true){
			return "forum_classclub_success_phone";
		}
		return  "forum_classclub_success";
	}
	
	
}
