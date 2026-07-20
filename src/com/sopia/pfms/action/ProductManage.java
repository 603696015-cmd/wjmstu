package com.sopia.pfms.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sopia.common.SystemConfOp;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.ProductDao;
import com.sopia.pfms.dao.SheBeiDao;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Suoshulanmu;

public class ProductManage extends BaseAction {
	private SheBeiDao sheBeiDao;
	private ProductDao productDao;
	private BaoxianProductDao baoxianProductDao;
	private List<Product> productList;
	private List<Suoshulanmu> suoshulanmuList;
	private List<Shenhezhuangtai> shenhezhuangtaiList;
	private Product product;
	private Suoshulanmu suoshulanmu;
	private int count;
	private ELUser elUser;
	
	private int size;
	private int start;
	
	private int id;
	private String productids;
	
	private Date starttime;
	private Date endtime;
	
	private ProductType ptypeTree;
	private ProductType ptype;
	
	private int sublibs;//是否包含下级节点
	
	private int productIsDel;//下属新闻类别与子类别操作  1.并入上级类别  2.与本类别同时删除
	private boolean is_product_fabu_can_alter;//产品发布后是否允许修改
	
	private IndexDataUtil indexDataUtil;
	
	private boolean is_product_sh;//发布产品默认是需要审核的
	private int product_sh;
	
	private String delete_inallList;
	private int update_inallList;
	private int updateType;
	
	private String optype;
	private String check_json_result;//检验删除的产品审核状态是否是2的返回值
	
	private int roleId;
	private int change_id;//修改保险产品id
	private String select_tuijian;//修改的值
	
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

	public String getSelect_tuijian() {
		return select_tuijian;
	}

	public void setSelect_tuijian(String select_tuijian) {
		this.select_tuijian = select_tuijian;
	}

	public String getCheck_json_result() {
		return check_json_result;
	}

	public void setCheck_json_result(String check_json_result) {
		this.check_json_result = check_json_result;
	}



	public SheBeiDao getSheBeiDao() {
		return sheBeiDao;
	}

	public void setSheBeiDao(SheBeiDao sheBeiDao) {
		this.sheBeiDao = sheBeiDao;
	}

	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}

	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
	}

	public List<Shenhezhuangtai> getShenhezhuangtaiList() {
		return shenhezhuangtaiList;
	}

	public void setShenhezhuangtaiList(List<Shenhezhuangtai> shenhezhuangtaiList) {
		this.shenhezhuangtaiList = shenhezhuangtaiList;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public String getProductids() {
		return productids;
	}

	public void setProductids(String productids) {
		this.productids = productids;
	}

	public int getProduct_sh() {
		return product_sh;
	}

	public void setProduct_sh(int product_sh) {
		this.product_sh = product_sh;
	}

	public void setIs_product_sh(boolean is_product_sh) {
		this.is_product_sh = is_product_sh;
	}

	public String getDelete_inallList() {
		return delete_inallList;
	}

	public void setDelete_inallList(String delete_inallList) {
		this.delete_inallList = delete_inallList;
	}

	public int getUpdate_inallList() {
		return update_inallList;
	}

	public void setUpdate_inallList(int update_inallList) {
		this.update_inallList = update_inallList;
	}

	public int getUpdateType() {
		return updateType;
	}

	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	} 

	public boolean isIs_product_fabu_can_alter() {
		return is_product_fabu_can_alter;
	}

	public void setIs_product_fabu_can_alter(boolean is_product_fabu_can_alter) {
		this.is_product_fabu_can_alter = is_product_fabu_can_alter;
	}

	public Boolean getIs_product_sh() {
		return is_product_sh;
	}

	public void setIs_product_sh(Boolean is_product_sh) {
		this.is_product_sh = is_product_sh;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}
	
	public int getProductIsDel() {
		return productIsDel;
	}

	public void setProductIsDel(int productIsDel) {
		this.productIsDel = productIsDel;
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

	public Suoshulanmu getSuoshulanmu() {
		return suoshulanmu;
	}

	public void setSuoshulanmu(Suoshulanmu suoshulanmu) {
		this.suoshulanmu = suoshulanmu;
	}

	public List<Suoshulanmu> getSuoshulanmuList() {
		return suoshulanmuList;
	}

	public void setSuoshulanmuList(List<Suoshulanmu> suoshulanmuList) {
		this.suoshulanmuList = suoshulanmuList;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获取产品列表
	 * @return
	 * @throws ElException
	 */
	public String productList() throws ElException{
		shenhezhuangtaiList = baoxianProductDao.shenhezhuangtaiList();
		//product树
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());
		
		is_product_sh = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_NEED_SH);
		
		String myUserId = String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
		is_product_fabu_can_alter = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER);
		
		if(product==null){
			product = new Product();
			sublibs = 1;
		}
		if (product.getPtype() == null
				|| product.getPtype().getId() <= 0) {
			product.setPtype(ptypeTree);
		} else {
			product.setPtype(productDao.getPtypeLibById(product.getPtype().getId()));
		}
		
		productList = productDao.getProductByUidByPerOrShar(null,product.getPtype(),sublibs,myUserId, nid, ptypeTree,
				null, getPageNow(), getPageSize(),product,starttime,endtime);
		count = productDao.getProductCountByUidByPerOrShar(null,product.getPtype(),sublibs,myUserId, nid,
				ptypeTree, null,getPageNow(),getPageSize(),product,starttime,endtime);
		return "success";
	}
	
	
	public String getAllProductAction() throws ElException{
		roleId = baoxianProductDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		
		is_product_fabu_can_alter = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER);
		shenhezhuangtaiList = baoxianProductDao.shenhezhuangtaiList();
		//product树
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		if(product == null){
			product = new Product();
			sublibs = 1;
		}
		
		if (product.getPtype() == null
				|| product.getPtype().getId() <= 0) {
			product.setPtype(ptypeTree);
		} else {
			product.setPtype(productDao.getPtypeLibById(product.getPtype().getId()));
		}
		
		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());
		is_product_sh = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_NEED_SH);
		
		productList = productDao.getAllProductByUidByPerOrShar(product.getPtype(),sublibs, nid, ptypeTree,
				null, getPageNow(), getPageSize(),product,starttime,endtime);
		count = productDao.getAllProductCountByUidByPerOrShar(product.getPtype(),sublibs, nid,
				ptypeTree, null,getPageNow(),getPageSize(),product,starttime,endtime);
		
		return "success";
	}
	
	
	
	
	/**
	 * 显示需要更新的产品信息
	 * @return
	 * @throws ElException
	 */
	public String updateChanpinView() throws ElException{
		product = productDao.showChanpin(id,1);
		suoshulanmuList = productDao.suoshulanmuList();
		if(new Integer(update_inallList) != null){
			if(update_inallList == 1)
				updateType = 1;
		}
		return "success";
	}
	
	/**
	 * 更新产品信息
	 * @return
	 * @throws ElException
	 */
	public String updateChanpin() throws ElException{
		ptype = productDao.getProTypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		int roleId = productDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		elUser = productDao.getELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		
		productDao.updateChanpin(roleId,product,
				productDao.showChanpin(product.getId(),1).getShenhezhuangtai(),
				SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER));
		if(new Integer(updateType) != null){
			if(updateType == 1)
				return "updateProduct_success_in_allList";
		}
		return "updateProduct_success_in_List";
	}
	
	/**
	 * 删除产品
	 * @return
	 * @throws ElException
	 */
	public String deleteChanpin() throws ElException{
		if(productids!=null){
			String[] productidss=productids.split(",");
			for (int i = 0; i < productidss.length; i++) {
				productDao.deleteChanpin(Integer.parseInt(productidss[i]));
			}
		}
		if(id != 0){
			productDao.deleteChanpin(id);
		}
		if(delete_inallList != null){
			if(delete_inallList.equals("all")){
				return "allProductList";
			}
		}
		return "productList";
	}
	
	public String addProductView() throws ElException{
		if("ajax".equals(optype)){
			ptype = productDao.getPtypeByid(ptype.getId());
			try {
				getResponse().setContentType("text/html;charset=UTF-8");
				PrintWriter localPrintWriter = getResponse().getWriter();
				String d= "{\"id\":\"" + ptype.getId() + "\",\"name\":\"" + ptype.getName()
								+  "\"}";
				localPrintWriter.println(d);
				localPrintWriter.flush();
				localPrintWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		return "success";
	}
	
	public String addProduct() throws ElException{
		elUser = productDao.getELUser(getSessionIntValue(ElConstants.SESSION_USERID));
		is_product_sh = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_NEED_SH);
		if(is_product_sh){
			this.product_sh = 1;//需要审核
		}else{
			this.product_sh = 2;//不需要审核
		}
		
		//判断介绍是否为空，空的话自动从简介中截取200个字符
		if(product != null){
			if(product.getJieshao() != null)
				if(product.getJieshao().equals("")){
					if(product.getJianjie()!=null)
						if(!product.getJianjie().equals("")){
							String temp = CheckHtml.getString(product.getJianjie()).length()>200 ? 
									CheckHtml.getString(product.getJianjie()).substring(0, 200):CheckHtml.getString(product.getJianjie()).substring(0);
							product.setJieshao(temp);
						}
				}
		}
		
		productDao.addProduct(is_product_sh, product, elUser,ptype.getParent().getId());
		
		return "success";
	}
	
	public String shenheChanpin() throws ElException{
		int roleId = productDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		productDao.updateShenhezhuangtai(roleId,id);
		return "success";
	}
	
	public String shenheChanpinNotPass() throws ElException{
		int roleId = productDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		productDao.updateShenhezhuangtaiNotPass(roleId,id);
		return "success";
	}
	
	public String getProductTypeList() throws ElException{
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		return "productTypeList";
	}
	
	public String productType_view() throws ElException{
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		ptype = productDao.getPtypeByid(ptype.getId());
		
		ptype.setOpusers(productDao.getOpUsers( ptype
				.getId()));
		return "productType_view";
	}
	
	public String productType_addInit() throws ElException{
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		if(ptypeTree.getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的产品所属栏目");
			 return "error"; 
		}
		return "productType_add";
	}
	
	public String productType_add() throws ElException{
		productDao.addProducttype(ptype);
		
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).updatetlrid("product_lanmu");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
		ElLoggerConstants.LOG_MOD_PRODUCTLIB,
		ElLoggerConstants.LOG_TYPE_ADD, ptype.getName(),
		ElLoggerConstants.LOG_RES_SUCC, ptype.getId());
		return "productType_add_success";
	}
	
	public String productType_alterInit() throws ElException{
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		ptype = productDao.getPtypeByid(ptype.getId());
		ptype
				.setOpusers(productDao.getOpUsers( ptype
						.getId()));
//		ntype.setUseusers(ctypeDao.getOpUsers("newstype_use_type", ntype
//				.getId()));
		return "productType_alter";
	}
	
	public String productType_alter() throws ElException{
//		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
//		if(ens.checkNodeisChild(ptype.getId(), ptype.getParent().getId(), "newstype")){
//			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
//			return "error";
//		}
		productDao.alterProductType(ptype);

		ptype=productDao.getPtypeByid(ptype.getId());
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_USERINFO);
		if(ptype!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_PRODUCTLIB,
					ElLoggerConstants.LOG_TYPE_ALTER, ptype.getName(),
					ElLoggerConstants.LOG_RES_SUCC,ptype.getId());
		}
		return "productType_alter_success";
	}
	
	public String productType_delete() throws ElException{
		ptype=productDao.getPtypeByid(ptype.getId());
		if(productIsDel == 1){
			//并入上级	3	1
			productDao.updateProductTypeParentid(ptype.getId(), ptype.getParent().getId());
			productDao.updateProductParentid(ptype.getId(), ptype.getParent().getId());
			productDao.deletePtype(ptype.getId());
		}else{
			//一起删除
			productDao.deleteProductTypeAndSub(ptype.getId());
		}
//		更新新闻树左右id
//		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("newstype");
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_USERINFO);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_PRODUCTLIB,
				ElLoggerConstants.LOG_TYPE_DELETE, ptype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ptype.getId());
		return "productType_list";
	}
	
	public String searchLanmuInit() throws ElException{
		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		return "searchLanmuInit";
	}
	
	public String checkShztBeforeDelete_product() throws ElException{
		boolean result = false;
		if(id != 0 ){
			result = sheBeiDao.checkShzt(id,"product");//true	审核状态2，不可删除
		}
		
		check_json_result = String.valueOf(result);
		
		getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter localPrintWriter;
		try {
			localPrintWriter = getResponse().getWriter();
			String d = "{\"check_json_result\":" + check_json_result + "}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String change_tuijian() throws ElException{
		if(productids != null && !productids.equals("")){
			String[] ids_array = productids.split(",");
			for(int i=0;i<ids_array.length;i++){
				baoxianProductDao.change_tuijian(Integer.parseInt(ids_array[i]),select_tuijian,"product");
			}
		}
		return "success";
	}
	
//	public String search_zhengzhantuijian_init() throws ElException{
//		roleId = baoxianProductDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
//		
//		is_product_fabu_can_alter = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER);
//		shenhezhuangtaiList = baoxianProductDao.shenhezhuangtaiList();
//		//product树
//		ptypeTree = productDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
//		if(product == null){
//			product = new Product();
//			sublibs = 1;
//		}
//		
//		if (product.getPtype() == null
//				|| product.getPtype().getId() <= 0) {
//			product.setPtype(ptypeTree);
//		} else {
//			product.setPtype(productDao.getPtypeLibById(product.getPtype().getId()));
//		}
//		
//		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());
//		is_product_sh = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_NEED_SH);
//		
//		productList = productDao.getAllProductByUidByPerOrShar(product.getPtype(),sublibs, nid, ptypeTree,
//				null, getPageNow(), getPageSize(),product,starttime,endtime);
//		count = productDao.getAllProductCountByUidByPerOrShar(product.getPtype(),sublibs, nid,
//				ptypeTree, null,getPageNow(),getPageSize(),product,starttime,endtime);
//		
//		 
//		return "search_zhengzhantuijian_init";
//	}
	
	

}
