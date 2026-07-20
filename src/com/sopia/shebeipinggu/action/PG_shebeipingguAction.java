package com.sopia.shebeipinggu.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.PfmsUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.dao.SheBeiDao;
import com.sopia.shebeipinggu.dao.PG_BaoxianProductDao;
import com.sopia.shebeipinggu.dao.PG_InsuranceCategoriesDao;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.InsuranceCategories;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Suoshulanmu;

public class PG_shebeipingguAction extends BaseAction {
	private SheBeiDao sheBeiDao;
	private PG_BaoxianProductDao PG_baoxianProductDao;
	private PG_InsuranceCategoriesDao PG_ICDao;
	private List<BaoxianProduct> baoxianProductList = new ArrayList<BaoxianProduct>();
	private List<Suoshulanmu> suoshulanmuList = new ArrayList<Suoshulanmu>();
	private BaoxianProduct baoxianProduct;
	private List<Shenhezhuangtai> shenhezhuangtaiList = new ArrayList<Shenhezhuangtai>();
	private int count;
	
	private int size;
	private int start;
	
	private Date starttime;
	private Date endtime;
	
	
	private int id;
	private ProductType ptypeTree;
	private ProductType ptype;
	
	private boolean is_baoxian_product_sh = true;//发布保险产品是否需要审核
	private int product_sh;
	private boolean is_product_fabu_can_alter;//产品发布后是否允许修改
	
	private String delete_inallList;
	private int update_inallList;
	private int updateType;
	
	private List<InsuranceCategories> ICList;
	private InsuranceCategories IC;
	
	private File logo;
	private String logoFileName;
	
	private String[] cptsArray;
	private int sublibs;//是否包含下级节点
	
	private String check_json_result;//检验删除的产品审核状态是否是2的返回值
	private String baoxianProductids;
	
	private String optype;
	
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

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public String getBaoxianProductids() {
		return baoxianProductids;
	}

	public void setBaoxianProductids(String baoxianProductids) {
		this.baoxianProductids = baoxianProductids;
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

	public int getSublibs() {
		return sublibs;
	}

	public void setSublibs(int sublibs) {
		this.sublibs = sublibs;
	}

	public String[] getCptsArray() {
		return cptsArray;
	}

	public void setCptsArray(String[] cptsArray) {
		this.cptsArray = cptsArray;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public InsuranceCategories getIC() {
		return IC;
	}

	public void setIC(InsuranceCategories ic) {
		IC = ic;
	}
	public List<InsuranceCategories> getICList() {
		return ICList;
	}

	public void setICList(List<InsuranceCategories> list) {
		ICList = list;
	}
	
	public String searchLanmu_baoxianProductInit() throws ElException{
//		ptypeTree = baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ptypeTree = PG_baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else {
			ptypeTree = PG_baoxianProductDao.getProTypeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		return "searchLanmu_baoxianProductInit";
	}
	

	public String baoxianProductList() throws ElException{
		//product树
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ptypeTree = PG_baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else {
			ptypeTree = PG_baoxianProductDao.getProTypeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		
		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());
		
		if(baoxianProduct==null){
			baoxianProduct = new BaoxianProduct();
			sublibs = 1;
		}
		if (baoxianProduct.getPtype() == null
				|| baoxianProduct.getPtype().getId() <= 0) {
			baoxianProduct.setPtype(ptypeTree);
		} else {
			nid = baoxianProduct.getPtype().getId();
			baoxianProduct.setPtype(PG_baoxianProductDao.getPtypeLibById(nid));
		}
		is_product_fabu_can_alter = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER);
		String myUserId = String.valueOf(getSessionIntValue(ElConstants.SESSION_USERID));
		
		is_baoxian_product_sh = SystemConfOp.getBooleanValue(ElConstants.BAOXIANPRODUCT_NEED_SH);
		is_product_fabu_can_alter = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER);
		shenhezhuangtaiList = PG_baoxianProductDao.shenhezhuangtaiList();
		
		count = PG_baoxianProductDao.getBaoxianProductCountByUidByPerOrShar(is_baoxian_product_sh,myUserId, nid,
				 baoxianProduct.getPtype(), null,getPageNow(),getPageSize(),baoxianProduct,starttime,endtime);
		
		baoxianProductList = PG_baoxianProductDao.getBaoxianProductByUidByPerOrShar(is_baoxian_product_sh,myUserId, nid, baoxianProduct.getPtype(),
				null, getPageNow(), getPageSize(),baoxianProduct,starttime,endtime);
		
		
		return "success";
//		size = getPageNow();
//		start =  getPageSize();
//		suoshulanmuList = baoxianProductDao.suoshulanmuList();
//		baoxianProductList = baoxianProductDao.baoxianProductList(start, size,getSessionIntValue(ElConstants.SESSION_USERID),baoxianProduct);
//		count = baoxianProductDao.getCount(getSessionIntValue(ElConstants.SESSION_USERID),baoxianProduct);
//		return "success";
	}
	
	public String baoxianAllProductList() throws ElException{
		roleId = PG_baoxianProductDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		is_product_fabu_can_alter = SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER);
		is_baoxian_product_sh = SystemConfOp.getBooleanValue(ElConstants.BAOXIANPRODUCT_NEED_SH);
		
		//product树
		ptypeTree = PG_baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
		
		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());
		
		if(baoxianProduct==null){
			baoxianProduct = new BaoxianProduct();
			sublibs = 1;
		}
		if (baoxianProduct.getPtype() == null
				|| baoxianProduct.getPtype().getId() <= 0) {
			baoxianProduct.setPtype(ptypeTree);
		} else {
			baoxianProduct.setPtype(PG_baoxianProductDao.getPtypeLibById(nid));
		}
		
		shenhezhuangtaiList = PG_baoxianProductDao.shenhezhuangtaiList();
		baoxianProductList = PG_baoxianProductDao.getAllBaoxianProductByUidByPerOrShar( nid, ptypeTree, null, getPageNow(), getPageSize(), baoxianProduct,starttime,endtime);
		count = PG_baoxianProductDao.getAllBaoxianProductCountByUidByPerOrShar( nid, ptypeTree, null, getPageNow(), getPageSize(), baoxianProduct,starttime,endtime);
		
		return "success";
//		size = getPageNow();
//		start =  getPageSize();
//		suoshulanmuList = baoxianProductDao.suoshulanmuList();
//		baoxianProductList = baoxianProductDao.baoxianAllProductList(start, size,baoxianProduct);
//		count = baoxianProductDao.getCount(baoxianProduct);
//		return "success";
	}
	
	public String updateBaoxianProductView()throws ElException{
		IC = IC == null ? new InsuranceCategories() : IC;
		ICList = PG_ICDao.getICList(IC,getPageNow(), getPageSize());
		suoshulanmuList = PG_baoxianProductDao.suoshulanmuList();
		baoxianProduct = PG_baoxianProductDao.showBaoxianProduct(id);
		
		if(baoxianProduct.getChanpinliangdian() != null){
			cptsArray = new PfmsUtil().changeStringToStringArray(baoxianProduct.getChanpinliangdian());
		}
		if(new Integer(update_inallList) != null){
			if(update_inallList == 1)
				updateType = 1;
		}
		return "success";
	}
	
	public String updateBaoxianProduct() throws ElException{
		int roleId = PG_baoxianProductDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		if(cptsArray != null){
			baoxianProduct.setChanpinliangdian(new PfmsUtil().changeToString(cptsArray));
		}
		
//		//审核后允许修改，那么当用户更新已经发布的产品后，其审核状态	从审核通过变更为已创建
//		is_baoxian_product_sh = SystemConfOp.getBooleanValue(ElConstants.BAOXIANPRODUCT_NEED_SH);
//		if(is_baoxian_product_sh){
//			this.product_sh = 1;//需要审核
//		}else{
//			this.product_sh = 2;//不需要审核
//		}
		
		PG_baoxianProductDao.updateBaoxianProduct(roleId, baoxianProduct,
				PG_baoxianProductDao.showBaoxianProduct(baoxianProduct.getId()).getShenhezhuangtai(),
				SystemConfOp.getBooleanValue(ElConstants.PRODUCT_FABU_CAN_ALTER));
		if(new Integer(updateType) != null){
			if(updateType == 1)
				return "updateBaoxianProduct_success_in_allList";
		}
		return "updateBaoxianProduct_success_in_List";
	}
	
	public String deleteBaoxianProduct() throws ElException{
		if(baoxianProductids!=null){
			String[] baoxianProductidss=baoxianProductids.split(",");
			for (int i = 0; i < baoxianProductidss.length; i++) {
				PG_baoxianProductDao.deleteBaoxianProduct(Integer.parseInt(baoxianProductidss[i]));
			}
		}
		
		if(delete_inallList != null){
			if(delete_inallList.equals("all")){
				return "allBaoxianProductList";
			}
		}
		return "baoxianProductList";
	}
	
	public String addBaoxianProductView() throws ElException{
		if("ajax".equals(optype)){
			ptype = PG_baoxianProductDao.getPtypeLibById(ptype.getId());
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
		
		IC = IC == null ? new InsuranceCategories() : IC;
		ICList = PG_ICDao.getICList(IC,getPageNow(), getPageSize());
		
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) 
			ptypeTree = PG_baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		else {
			ptypeTree = PG_baoxianProductDao.getProTypeLibTree(getSessionIntValue(ElConstants.SESSION_USERID), "op",ElConstants.TREE_FIANL, true);
		}
		if(ptypeTree.getChild().size() == 0){    
			 setElmessage("没有可操作的保险产品栏目");
			 return "error"; 
		}
		return "success";
	}
	
	public String addBaoxianProduct() throws Exception{
		
		if(cptsArray == null){
			baoxianProduct.setChanpinliangdian("");
		}else{
			for(int i=0;i<cptsArray.length;i++){
				if(cptsArray[i].contains(" ")){
					cptsArray[i] = cptsArray[i].replaceAll("\\s", "");
				}
			}
			baoxianProduct.setChanpinliangdian(new PfmsUtil().changeToString(cptsArray));
		}
		
		
//		System.out.println(baoxianProduct.getInsuranceCategoryId());
//		System.out.println(logo);
//		System.out.println(logoFileName);
//		if(logo == null){
//			setElmessage("您上传的文件为空！");
//			return "failure";
//		}else if (logo.length() > 100 * 100) {
//			setElmessage("您上传的文件过大！");
//			return "failure";
//		} else {
//			String ext = J2EEFileUtil.getExtention(logoFileName); 
//			int id = getSessionIntValue(ElConstants.SESSION_USERID);
//			J2EEFileUtil.upload_was(logo, ext, "images/pfms/baoxianProduct", baoxianProduct.getName()+"_logo_"+id);
			ELUser elUser  = new ELUser();
			elUser = PG_baoxianProductDao.getELUser(getSessionIntValue(ElConstants.SESSION_USERID));
//			baoxianProduct.setLogo(ext);
			is_baoxian_product_sh = SystemConfOp.getBooleanValue(ElConstants.BAOXIANPRODUCT_NEED_SH);
			if(is_baoxian_product_sh){
				this.product_sh = 1;//需要审核
			}else{
				this.product_sh = 2;//不需要审核
			}
			
			//判断介绍是否为空，空的话自动从间接中截取200个字符
			if(baoxianProduct != null && baoxianProduct.getJieshao() != null  ){
				if(baoxianProduct.getJieshao().equals("")){
					if(baoxianProduct.getJianjie()!=null && !baoxianProduct.getJianjie().equals("")){
						String temp = CheckHtml.getString(baoxianProduct.getJianjie()).length()>200 ? 
								CheckHtml.getString(baoxianProduct.getJianjie()).substring(0, 200):CheckHtml.getString(baoxianProduct.getJianjie()).substring(0);
								baoxianProduct.setJieshao(temp);
					}
				}
			}
			
			PG_baoxianProductDao.addBaoxianProduct(is_baoxian_product_sh,baoxianProduct,elUser,ptype.getParent().getId());
//		}
		return "success";
	}
	
	public String shenheBaoxianProduct() throws ElException{
		int roleId = PG_baoxianProductDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		PG_baoxianProductDao.shenheBaoxianProduct(roleId, id);
		return "success";
	}
	
	public String shenheBaoxianProductNotPass() throws ElException{
		int roleId = PG_baoxianProductDao.getRoleId(getSessionIntValue(ElConstants.SESSION_USERID));
		PG_baoxianProductDao.shenheBaoxianProductNotPass(roleId, id);
		return "success";
	}
	
	
	
	
	public String getBaoxianProductTypeList() throws ElException{
		ptypeTree = PG_baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		return "baoxianProductTypeList";
	}
	
	public String baoxianProductType_view() throws ElException{
		ptypeTree = PG_baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		ptype = PG_baoxianProductDao.getPtypeLibById(ptype.getId());
		
		ptype.setOpusers(PG_baoxianProductDao.getOpUsers( ptype
				.getId()));
		return "baoxianProductType_view";
	}
	
	public String baoxianProductType_addInit() throws ElException{
		ptypeTree = PG_baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		if(ptypeTree.getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的产品所属栏目");
			 return "error"; 
		}
		return "baoxianProductType_add";
	}
	
	public String baoxianProductType_add() throws ElException{
		PG_baoxianProductDao.addProducttype(ptype);
		
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).updatetlrid("baoxian_product_lanmu");
		
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_NEWSTYPE,
				ElLoggerConstants.LOG_TYPE_ADD, ptype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ptype.getId());//**//**//
		return "baoxianProductType_add_success";
	}
	
	public String baoxianProductType_alterInit() throws ElException{
		ptypeTree = PG_baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		ptype = PG_baoxianProductDao.getPtypeLibById(ptype.getId());
		ptype
				.setOpusers(PG_baoxianProductDao.getOpUsers( ptype
						.getId()));
//		ntype.setUseusers(ctypeDao.getOpUsers("newstype_use_type", ntype
//				.getId()));
		return "baoxianProductType_alter";
	}
	
	public String baoxianProductType_alter() throws ElException{
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(ptype.getId(), ptype.getParent().getId(), "newstype")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return "error";
		}
		PG_baoxianProductDao.alterProductType(ptype);

		ptype=PG_baoxianProductDao.getPtypeLibById(ptype.getId());
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_USERINFO);
		if(ptype!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_NEWSTYPE,
					ElLoggerConstants.LOG_TYPE_ALTER, ptype.getName(),
					ElLoggerConstants.LOG_RES_SUCC,ptype.getId());
		}
		return "baoxianProductType_alter_success";
	}
	
	public String baoxianProductType_delete() throws ElException{
		ptype=PG_baoxianProductDao.getPtypeLibById(ptype.getId());
		if(productIsDel == 1){
			//并入上级	3	1
			PG_baoxianProductDao.updateProductTypeParentid(ptype.getId(), ptype.getParent().getId());
			PG_baoxianProductDao.updateProductParentid(ptype.getId(), ptype.getParent().getId());
			PG_baoxianProductDao.deletePtype(ptype.getId());
		}else{
			//一起删除
			PG_baoxianProductDao.deleteProductTypeAndSub(ptype.getId());
		}
//		更新新闻树左右id
//		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("newstype");
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_USERINFO);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_NEWSTYPE,
				ElLoggerConstants.LOG_TYPE_DELETE, ptype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ptype.getId());
		return "baoxianProductType_list";
	}
	
	public String insuranceCategoriesTree() throws ElException{
		IC = IC == null ? new InsuranceCategories() : IC;
		ICList = PG_ICDao.getICList(IC,getPageNow(), getPageSize());
		count = PG_ICDao.getICListSize(IC);
		return "insuranceCategoriesTree";
	}
	
	public String insuranceCategory_view() throws ElException{
		IC = PG_baoxianProductDao.getInsuranceCategoryByid(IC.getId());
		try {
			getResponse().setContentType("text/html;charset=UTF-8");
			PrintWriter localPrintWriter = getResponse().getWriter();
			String d= "{\"id\":\"" + IC.getId() + "\",\"name\":\"" + IC.getName()
							 + "\"}";
			localPrintWriter.println(d);
			localPrintWriter.flush();
			localPrintWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String checkShztBeforeDelete_baoxianProduct() throws ElException{
		boolean result = false;
		if(id != 0 ){
			result = sheBeiDao.checkShzt(id,"product_baoxian");//true	审核状态2，不可删除
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
		if(baoxianProductids != null && !baoxianProductids.equals("")){
			String[] ids_array = baoxianProductids.split(",");
			for(int i=0;i<ids_array.length;i++){
				PG_baoxianProductDao.change_tuijian(Integer.parseInt(ids_array[i]),select_tuijian,"product_baoxian");
			}
		}
		return "success";
	}
	

	public List<Suoshulanmu> getSuoshulanmuList() {
		return suoshulanmuList;
	}

	public void setSuoshulanmuList(List<Suoshulanmu> suoshulanmuList) {
		this.suoshulanmuList = suoshulanmuList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}



	public PG_BaoxianProductDao getPG_baoxianProductDao() {
		return PG_baoxianProductDao;
	}

	public void setPG_baoxianProductDao(PG_BaoxianProductDao productDao) {
		PG_baoxianProductDao = productDao;
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

	public ProductType getPtypeTree() {
		return ptypeTree;
	}

	public void setPtypeTree(ProductType ptypeTree) {
		this.ptypeTree = ptypeTree;
	}

	public ProductType getPtype() {
		return ptype;
	}

	public void setPtype(ProductType ptype) {
		this.ptype = ptype;
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
	private IndexDataUtil indexDataUtil;
	private int productIsDel;//下属新闻类别与子类别操作  1.并入上级类别  2.与本类别同时删除

	public int getProductIsDel() {
		return productIsDel;
	}

	public void setProductIsDel(int productIsDel) {
		this.productIsDel = productIsDel;
	}

	public IndexDataUtil getIndexDataUtil() {
		return indexDataUtil;
	}

	public void setIndexDataUtil(IndexDataUtil indexDataUtil) {
		this.indexDataUtil = indexDataUtil;
	}

	public boolean isIs_baoxian_product_sh() {
		return is_baoxian_product_sh;
	}

	public void setIs_baoxian_product_sh(boolean is_baoxian_product_sh) {
		this.is_baoxian_product_sh = is_baoxian_product_sh;
	}

	public List<Shenhezhuangtai> getShenhezhuangtaiList() {
		return shenhezhuangtaiList;
	}

	public void setShenhezhuangtaiList(List<Shenhezhuangtai> shenhezhuangtaiList) {
		this.shenhezhuangtaiList = shenhezhuangtaiList;
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

	public int getProduct_sh() {
		return product_sh;
	}

	public void setProduct_sh(int product_sh) {
		this.product_sh = product_sh;
	}

	public PG_InsuranceCategoriesDao getPG_ICDao() {
		return PG_ICDao;
	}

	public void setPG_ICDao(PG_InsuranceCategoriesDao dao) {
		PG_ICDao = dao;
	}



	

}
