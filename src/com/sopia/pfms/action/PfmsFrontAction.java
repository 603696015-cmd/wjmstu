package com.sopia.pfms.action;

import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.PfmsUtil;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.entities.News;
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.IndexDao;
import com.sopia.pfms.dao.PfmsFrontDao;
import com.sopia.pfms.dao.ProductDao;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.MessageBoard;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;
import com.sopia.statman.dao.ShoppingCartDao;

public class PfmsFrontAction extends BaseAction {
	private PfmsFrontDao pfmsFrontDao;
	private ProductDao productDao;
	private BaoxianProductDao baoxianProductDao;
	private NewsDao newsDao;
	private IndexDao indexDao;
	private ShoppingCartDao shoppingCartDao;
	private List<PfmsUser> pfmsUserList = new ArrayList<PfmsUser>();
	private int count;
	
	private int id;//userid
	private PfmsUser pfmsUser;
	private List<Product> productlist = new ArrayList<Product>();
	
	private List<Product> sixProductlist = new ArrayList<Product>();
	
	private List<News> newsList = new ArrayList<News>();
	
	private boolean is_tuijian;//推荐产品查询
	
	private List<CourseComment> courseComments = new ArrayList<CourseComment>();
	private int productId;
	private Product product;
	private BaoxianProduct baoxianProduct;
	
	private CourseComment courseComment;
	
	private Department depTree;
	private Department department;
	private int sub_department;
	private List<Department> departments;
	
	private ProductType ptypeTree;
	private List<ProductType> productTypes;
	private ProductType ptype;
	private int sublibs;//是否包含下级节点
	private List<Product> productList;
	
	private int showType;//1为全部、2为部分、特指简介的截取
	
	private List<BaoxianProduct> sixBaoxianProductList;
	private List<BaoxianProduct> baoxianProductList;
	
	private String[] sArray;
	private String jiequ_jianjie;//截取的简介
	private String all_jianjie;//数据库中取出的简介
	
	private int session_userid;
	private int shopId;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getSession_userid() {
		return session_userid;
	}

	public void setSession_userid(int session_userid) {
		this.session_userid = session_userid;
	}

	public List<BaoxianProduct> getSixBaoxianProductList() {
		return sixBaoxianProductList;
	}

	public void setSixBaoxianProductList(List<BaoxianProduct> sixBaoxianProductList) {
		this.sixBaoxianProductList = sixBaoxianProductList;
	}

	public List<BaoxianProduct> getBaoxianProductList() {
		return baoxianProductList;
	}

	public void setBaoxianProductList(List<BaoxianProduct> baoxianProductList) {
		this.baoxianProductList = baoxianProductList;
	}

	public BaoxianProduct getBaoxianProduct() {
		return baoxianProduct;
	}

	public void setBaoxianProduct(BaoxianProduct baoxianProduct) {
		this.baoxianProduct = baoxianProduct;
	}

	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}

	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
	}

	public ShoppingCartDao getShoppingCartDao() {
		return shoppingCartDao;
	}

	public void setShoppingCartDao(ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public List<ProductType> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(List<ProductType> productTypes) {
		this.productTypes = productTypes;
	}


	public ProductType getPtype() {
		return ptype;
	}

	public void setPtype(ProductType ptype) {
		this.ptype = ptype;
	}

	public ProductType getPtypeTree() {
		return ptypeTree;
	}

	public void setPtypeTree(ProductType ptypeTree) {
		this.ptypeTree = ptypeTree;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDepTree() {
		return depTree;
	}

	public void setDepTree(Department depTree) {
		this.depTree = depTree;
	}

	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}



	public List<CourseComment> getCourseComments() {
		return courseComments;
	}

	public void setCourseComments(List<CourseComment> courseComments) {
		this.courseComments = courseComments;
	}

	public CourseComment getCourseComment() {
		return courseComment;
	}

	public void setCourseComment(CourseComment courseComment) {
		this.courseComment = courseComment;
	}

	public boolean isIs_tuijian() {
		return is_tuijian;
	}

	public void setIs_tuijian(boolean is_tuijian) {
		this.is_tuijian = is_tuijian;
	}

	public String user_center_list() throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_USERID) == 0){
			session_userid = 0;
		}else{
			session_userid = productDao.getELUser(getSessionIntValue(ElConstants.SESSION_USERID)).getId();
		}
		depTree = departmentDao.getDepTree(
				0, -1, true);
//		Department depTree1 = new Department();
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			depTree1 = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		else {
//			depTree1 = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		else {
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
		
		departments = pfmsFrontDao.getAllDepTree(true); 	
		
		
		if(department==null||department.getId()<=0){
			sub_department = ElConstants.SUBOP_YES;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		
		
		pfmsUserList = pfmsFrontDao.userlist(department,getPageNow(), getPageSize(),pfmsUser);
		count = pfmsFrontDao.userCount(department,pfmsUser);
		
		return "user_center_list_success";
	}
	
	public String jg_center_list() throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_USERID) == 0){
			session_userid = 0;
		}else{
			session_userid = productDao.getELUser(getSessionIntValue(ElConstants.SESSION_USERID)).getId();
		}
		depTree = departmentDao.getDepTree(
				0, -1, true);
//		Department depTree1 = new Department();
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			depTree1 = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		else {
//			depTree1 = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
//		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1)
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
//					true);
//		else {
//			depTree = departmentDao.getDepTree_level1(
//					getSessionIntValue(ElConstants.SESSION_USERID), "op", -1,
//					true);
//		}
		
		departments = pfmsFrontDao.getAllDepTree(true); 	
		
		
		if(department==null||department.getId()<=0){
			sub_department = ElConstants.SUBOP_YES;
			department = depTree;
		}else
			department  = departmentDao.getDepById(department.getId());
		
		
		pfmsUserList = pfmsFrontDao.userlist(department,getPageNow(), getPageSize(),pfmsUser);
		count = pfmsFrontDao.userCount(department,pfmsUser);
		
		return "user_center_list_success";
	}
	
	public String shopIndex() throws ElException{
		int roleid = indexDao.getRoleId(id);
		if (roleid == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					id, "op", -1,
					true);
		}

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		//店内推荐
		sixProductlist = pfmsFrontDao.productList(6,1,id,false,department);
		
		if(product == null){
			product = new Product();
		}
		if (product.getPtype() == null
				|| product.getPtype().getId() <= 0) {
			product.setPtype(ptypeTree);
		} else {
			product.setPtype(productDao.getPtypeLibById(product.getPtype().getId()));
		}
		
		productlist = pfmsFrontDao.productList(product.getPtype(),getPageNow(), getPageSize(),id,product,department);
		count = pfmsFrontDao.productListCount(product.getPtype(),id,product,department);
		
		
		pfmsUser = indexDao.getUser(id,true);//true为显示部分，false为显示全部
		shopId = id;
		return "shopIndex";
	}
	
	public String shopIndex_jianjie() throws ElException{
		int roleid = indexDao.getRoleId(id);
		if (roleid == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					id, "op", -1,
					true);
		}

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
		
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
		//店内推荐
		sixProductlist = pfmsFrontDao.productList(6,1,id,false,department); 
		pfmsUser = indexDao.getUser(id,false);
		shopId = id;
		return "shopIndex_jianjie";
	}
	
	public String shopIndex_news() throws ElException{
		int roleid = indexDao.getRoleId(id);
		if (roleid == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					id, "op", -1,
					true);
		}

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
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
		//店内推荐
		sixProductlist = pfmsFrontDao.productList(6,1,id,false,department); 
		
		newsList = pfmsFrontDao.newsList(getPageNow(), getPageSize(),id,department);
		count = pfmsFrontDao.newsListCount(id,department);
		
		pfmsUser = indexDao.getUser(id,false);
		shopId = id;
		return "shopIndex_news";
	}
	
	public String shopIndex_product_zhantin() throws ElException{
		int roleid = indexDao.getRoleId(id);
		if (roleid == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					id, "op", -1,
					true);
		}

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		//店内推荐
		sixProductlist = pfmsFrontDao.productList(6,1,id,false,department);
		
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
		
		count = pfmsFrontDao.productListCount(product.getPtype(),id,product,department);
		productlist = pfmsFrontDao.productList(product.getPtype(),getPageNow(), getPageSize(),id,product,department);
		
		pfmsUser = indexDao.getUser(id,false);
		shopId = id;
		return "shopIndex_product_zhantin";
	}
	
	public String shopIndex_product_tuijian() throws ElException{
		int roleid = indexDao.getRoleId(id);
		if (roleid == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					id, "op", -1,
					true);
		}

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
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
		
		pfmsUser = indexDao.getUser(id,false);
		shopId = id;
		
		//10条店内最新新闻
		newsList = pfmsFrontDao.newsList(10, 1,id,department);
		
		//十个推荐产品
		is_tuijian = true;
		productlist = pfmsFrontDao.productList(10, 1,id,is_tuijian,department);
		
		pfmsUser = indexDao.getUser(id,false);
		return "shopIndex_product_tuijian";
	}
	
	public String shopIndex_contact() throws ElException{
		int roleid = indexDao.getRoleId(id);
		if (roleid == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					id, "op", -1,
					true);
		}

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
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
		
		pfmsUser = indexDao.getUser(id,false);
		//店内推荐
		sixProductlist = pfmsFrontDao.productList(6,1,id,false,department); 
		
		pfmsUser = indexDao.getUser(id,false);
		shopId = id;
		return "shopIndex_contact";
	}
	
	public String shopIndex_zhengjian() throws ElException{
		int roleid = indexDao.getRoleId(id);
		if (roleid == 1)
			depTree = departmentDao.getDepTree_level1(
					getSessionIntValue(ElConstants.SESSION_MYDEPARTMENT), -1,
					true);
		else {
			depTree = departmentDao.getDepTree_level1(
					id, "op", -1,
					true);
		}

		if (department == null || department.getId() <= 0) {
			sub_department = 1;
			department = depTree;
		} else
			department = departmentDao.getDepById(department.getId());
		
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
		
		pfmsUser = indexDao.getUser(id,false);
		sixProductlist = pfmsFrontDao.productList(6,1,id,false,department); 
		
		pfmsUser = indexDao.getUser(id,false);
		shopId = id;
		return "shopIndex_zhengjian";
	}
	
	
	public String shopIndex_messageBoard() throws ElException{
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
		
		courseComments = pfmsFrontDao.messageBoardsList(id,getPageNow6(), getPageSize6(),0);
		count = pfmsFrontDao.messageBoardsCount(id,0);
		
		pfmsUser = indexDao.getUser(id,false);
		shopId = id;
		return "shopIndex_messageBoard";
	}
	
	
	//产品中心
	public String product_center_list() throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_USERID) == 0){
			session_userid = 0;
		}else{
			session_userid = productDao.getELUser(getSessionIntValue(ElConstants.SESSION_USERID)).getId();
		}
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		sublibs = 1;
		productTypes = pfmsFrontDao.getAllPTypeTree(true);
		
		
		if(ptype==null||ptype.getId()<=0){
			ptype = ptypeTree;
		}else{
			ptype  = productDao.getPtypeByid(ptype.getId());
		}
		
		if(product==null){
			product = new Product();
		}
		if (product.getPtype() == null
				|| product.getPtype().getId() <= 0) {
			product.setPtype(ptype);
		} else {
			product.setPtype(productDao.getPtypeLibById(product.getPtype().getId()));
		}
		
		count = productDao.getProductCountByUidByPerOrShar
			(
			"front",
			product.getPtype(), 
			sublibs, 
			null,
			0, 
			ptype, 
			null, 
			getPageNow(), 
			getPageSize(), 
			product, 
			null, 
			null);
		productList = productDao.getProductByUidByPerOrShar("front",product.getPtype(), sublibs,null, 0, ptypeTree, null, getPageNow(), getPageSize(), product, null, null);
		
		return "product_center_list_success";
	}
	
	public String baoxianProduct_center_list() throws ElException{
		
		ptypeTree = baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
		if(baoxianProduct==null){
			baoxianProduct = new BaoxianProduct();
		}
		if (baoxianProduct.getPtype() == null
				|| baoxianProduct.getPtype().getId() <= 0) {
			baoxianProduct.setPtype(ptypeTree);
		} else {
			baoxianProduct.setPtype(baoxianProductDao.getPtypeLibById(baoxianProduct.getPtype().getId()));
		}
		
		sixBaoxianProductList = baoxianProductDao.getSixFrontBaoxianProductList(6,1);
		
		baoxianProductList = baoxianProductDao.getFrontBaoxianProductList(baoxianProduct,getPageNow(),getPageSize());
		count = baoxianProductDao.getFrontbaoxianProductListSize(baoxianProduct,getPageNow(),getPageSize());
		return "baoxianProduct_center_list_success";
	}
	
	public String baoxianProduct_content() throws ElException{
		ptypeTree = baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		sixBaoxianProductList = baoxianProductDao.getSixFrontBaoxianProductList(6,1);
		baoxianProduct = baoxianProductDao.getBaoxianProductByid(id);
		all_jianjie = baoxianProduct.getJianjie();
		
		String jianjie = CheckHtml.getString(all_jianjie);
		jiequ_jianjie = jianjie.length() > 200 ? jianjie.substring(0, 200)+ "......" : jianjie;
		
		
		if(baoxianProduct.getChanpinliangdian() != null){
			sArray = new PfmsUtil().changeStringToStringArray(baoxianProduct.getChanpinliangdian());
		}
		
		if(sArray == null)
			sArray = new String[1];
		return "baoxianProduct_content";
	}
	
	
	public String shopIndex_content() throws ElException{
		if(getSessionIntValue(ElConstants.SESSION_USERID) == 0){
			session_userid = 0;
		}else{
			session_userid = productDao.getELUser(getSessionIntValue(ElConstants.SESSION_USERID)).getId();
		}
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
//		courseComments = pfmsFrontDao.messageBoardsList(getSessionIntValue(ElConstants.SESSION_USERID),getPageNow(), getPageSize(),productId);
//		count = pfmsFrontDao.messageBoardsCount(getSessionIntValue(ElConstants.SESSION_USERID),productId);
		
		if(product == null ){
			product = new Product();
		}
		if (product.getPtype() == null
				|| product.getPtype().getId() <= 0) {
			product.setPtype(ptypeTree);
		} else {
			product.setPtype(productDao.getPtypeLibById(product.getPtype().getId()));
		}
		
		product = productDao.showChanpin(productId,2);
		
		float point_avg = pfmsFrontDao.getAvgPoint(productId);
		
		courseComment = new CourseComment();
		courseComment.setAvg(point_avg);
		
		courseComments = pfmsFrontDao.messageBoardsList(id,getPageNow(), getPageSize(),productId);
		count = pfmsFrontDao.messageBoardsCount(id,productId);
		
		
		return "shopIndex_content";
	}
	
	public PfmsFrontDao getPfmsFrontDao() {
		return pfmsFrontDao;
	}

	public void setPfmsFrontDao(PfmsFrontDao pfmsFrontDao) {
		this.pfmsFrontDao = pfmsFrontDao;
	}

	public List<PfmsUser> getPfmsUserList() {
		return pfmsUserList;
	}

	public void setPfmsUserList(List<PfmsUser> pfmsUserList) {
		this.pfmsUserList = pfmsUserList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PfmsUser getPfmsUser() {
		return pfmsUser;
	}

	public void setPfmsUser(PfmsUser pfmsUser) {
		this.pfmsUser = pfmsUser;
	}

	public List<Product> getProductlist() {
		return productlist;
	}

	public void setProductlist(List<Product> productlist) {
		this.productlist = productlist;
	}

	public List<Product> getSixProductlist() {
		return sixProductlist;
	}

	public void setSixProductlist(List<Product> sixProductlist) {
		this.sixProductlist = sixProductlist;
	}

	public IndexDao getIndexDao() {
		return indexDao;
	}

	public void setIndexDao(IndexDao indexDao) {
		this.indexDao = indexDao;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getSub_department() {
		return sub_department;
	}

	public void setSub_department(int sub_department) {
		this.sub_department = sub_department;
	}

	public String[] getSArray() {
		return sArray;
	}

	public void setSArray(String[] array) {
		sArray = array;
	}

	public String getJiequ_jianjie() {
		return jiequ_jianjie;
	}

	public void setJiequ_jianjie(String jiequ_jianjie) {
		this.jiequ_jianjie = jiequ_jianjie;
	}

	public String getAll_jianjie() {
		return all_jianjie;
	}

	public void setAll_jianjie(String all_jianjie) {
		this.all_jianjie = all_jianjie;
	}

}
