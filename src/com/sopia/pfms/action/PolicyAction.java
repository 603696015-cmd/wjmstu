package com.sopia.pfms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.sopia.BaseAction; 
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser; 
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.PolicyDao;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.Policy;
import com.sopia.pfms.entities.PolicyLib;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Shenhezhuangtai;

public class PolicyAction extends BaseAction {
	private PolicyDao policyDao;
	private PolicyLib policyLib;
	private PolicyLib ptype;
	private Policy policy;
	private List<Policy> policys;
	private ELUser elUser;
	private List<PolicyLib> pLibs;
	private IndexDataUtil indexDataUtil;
	private int productIsDel;//下属新闻类别与子类别操作  1.并入上级类别  2.与本类别同时删除
	
	//保险产品 
	private ProductType ptypeTree;
	private List<Shenhezhuangtai> shenhezhuangtaiList = new ArrayList<Shenhezhuangtai>();
	private List<BaoxianProduct> baoxianProductList = new ArrayList<BaoxianProduct>();
	private BaoxianProductDao baoxianProductDao;
	private BaoxianProduct baoxianProduct;
	private Date starttime;
	private Date endtime;
	private String actionName;

	private File st;
	private String stFileName;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getSt() {
		return st;
	}
	public void setSt(File st) {
		this.st = st;
	}
	public String getStFileName() {
		return stFileName;
	}
	public void setStFileName(String stFileName) {
		this.stFileName = stFileName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
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
	public PolicyDao getPolicyDao() {
		return policyDao;
	}
	public void setPolicyDao(PolicyDao policyDao) {
		this.policyDao = policyDao;
	}
	public PolicyLib getPolicyLib() {
		return policyLib;
	}
	public void setPolicyLib(PolicyLib policyLib) {
		this.policyLib = policyLib;
	}
	public Policy getPolicy() {
		return policy;
	}
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public List<PolicyLib> getPLibs() {
		return pLibs;
	}
	public void setPLibs(List<PolicyLib> libs) {
		pLibs = libs;
	}
	public PolicyLib getPtype() {
		return ptype;
	}
	public void setPtype(PolicyLib ptype) {
		this.ptype = ptype;
	} 

	public ProductType getPtypeTree() {
		return ptypeTree;
	}
	public void setPtypeTree(ProductType ptypeTree) {
		this.ptypeTree = ptypeTree;
	}
	public List<Shenhezhuangtai> getShenhezhuangtaiList() {
		return shenhezhuangtaiList;
	}
	public void setShenhezhuangtaiList(List<Shenhezhuangtai> shenhezhuangtaiList) {
		this.shenhezhuangtaiList = shenhezhuangtaiList;
	}
	public List<BaoxianProduct> getBaoxianProductList() {
		return baoxianProductList;
	}
	public void setBaoxianProductList(List<BaoxianProduct> baoxianProductList) {
		this.baoxianProductList = baoxianProductList;
	}
	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}
	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
	}
	public BaoxianProduct getBaoxianProduct() {
		return baoxianProduct;
	}
	public void setBaoxianProduct(BaoxianProduct baoxianProduct) {
		this.baoxianProduct = baoxianProduct;
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
	
	public String policyLib_addInit() throws ElException {
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			policyLib = policyDao.getPolicyLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			policyLib = policyDao.getPolicyLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true); 
		}
		if(policyLib.getChild().size() == 0  && getSessionIntValue(ElConstants.SESSION_ROLE) != 1){    
			 setElmessage("没有可操作的栏目树");
			 return "error"; 
		}
		return "policyLib_add";
	}
	public String policyLib_add() throws ElException{
		policyDao.addPolicyLib(ptype); 
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("policylib");
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_POLICYLIB,
				ElLoggerConstants.LOG_TYPE_ADD, ptype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ptype.getId());//**//**//
		return "policyLib_add_success";
	}
	public String policyLibListInit() throws ElException{
		policyLib = policyDao.getPolicyLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		return "policyLibList";
	}
	public String policyLib_view() throws ElException{
		policyLib = policyDao.getPolicyLibTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		ptype = policyDao.getPtypeByid(ptype.getId()); 
		ptype.setOpusers(policyDao.getOpUsers( ptype.getId()));
		return "policyLib_view";
	}
	public String policyLib_alterInit() throws ElException { 
		if (getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {
			policyLib = policyDao.getPolicyLibTree(ElConstants.TREE_ROOT,
					ElConstants.TREE_FIANL, true);
		} else {
			policyLib = policyDao.getPolicyLibTree(
					getSessionIntValue(ElConstants.SESSION_USERID), "op",
					ElConstants.TREE_FIANL, true); 
		}
		ptype = policyDao.getPtypeByid(ptype.getId());
		ptype.setOpusers(policyDao.getOpUsers( ptype.getId()));
		return "policyLib_alter";
	}
	public String policyLib_alter() throws ElException {
		ElNodeSQL ens=((ElNodeSQL) SpringContextUtil.getBean(ElConstants.CLASS_ELNODESQL));//
		if(ens.checkNodeisChild(ptype.getId(), ptype.getParent().getId(), "policyLib")){
			setElmessage("不可以将父节点设置成自己或者下级节点，请重新选择。");
			return "error";
		}
		policyDao.alterpolicylib(ptype);
		ens.updatetlrid("policyLib"); 
		ptype=policyDao.getPtypeByid(ptype.getId());
		//刷新首页模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_NEWS); 
		if(ptype!=null){
			ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_POLICYLIB,
					ElLoggerConstants.LOG_TYPE_ALTER, ptype.getName(),
					ElLoggerConstants.LOG_RES_SUCC,ptype.getId());
		}
		return "policyLib_alter_success";
	}
	public String policyLib_delete() throws ElException{
		ptype=policyDao.getPtypeByid(ptype.getId());
		if(productIsDel == 1){
			//并入上级	3	1
			policyDao.updatePolicyTypeParentid(ptype.getId(), ptype.getParent().getId());
			policyDao.updatePolicyParentid(ptype.getId(), ptype.getParent().getId());
			policyDao.deletePtype(ptype.getId());
		}else{
			//一起删除
			policyDao.deletePolicylibAndSub(ptype.getId());
		}
		//更新树左右id
		((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).updatetlrid("policylib");
		//刷新首页新闻模块
		indexDataUtil.loadIndexInfo(ElConstants.INDEX_MODEL_USERINFO);
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_POLICYLIB,
				ElLoggerConstants.LOG_TYPE_DELETE, ptype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,ptype.getId());
		return "policyLibListInit";
	}
//--------------下面是保单信息 
	public String buyPolicyListInit() throws ElException{ 
		//product树
		ptypeTree = baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());
		shenhezhuangtaiList = baoxianProductDao.shenhezhuangtaiList();
		baoxianProductList = baoxianProductDao.getAllBaoxianProductByUidByPerOrShar( nid, ptypeTree, null, getPageNow(), getPageSize(), baoxianProduct,starttime,endtime);
		count = baoxianProductDao.getAllBaoxianProductCountByUidByPerOrShar( nid, ptypeTree, null, getPageNow(), getPageSize(), baoxianProduct,starttime,endtime);
		return "buyPolicyList";
	} 

	public String MyPolicyListInit() throws ElException{ 
		//product树
		ptypeTree = baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		
		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());  
		policy = new Policy();
		policy.setCreateId(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));  
		policys = policyDao.getPolicyList(policy,nid, ptypeTree,true,getPageNow(), getPageSize());
		count = policyDao.getPolicyListSize(policy,nid, ptypeTree,true);
		return "MyPolicyList";
	} 
	
	public String Policy_AuditListInit() throws ElException{ 
		//product树
		ptypeTree = baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true); 
		
		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());  
		policy = new Policy();
		policy.setCreateId(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));  
		policys = policyDao.getPolicyList(policy,nid, ptypeTree,false,getPageNow(), getPageSize());
		count = policyDao.getPolicyListSize(policy,nid, ptypeTree,false);
		return "Policy_AuditList";
	} 
	
	public String Policy_Audit2ListInit() throws ElException{ 
		//product树
		ptypeTree = baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true); 
		
		int nid = ptype == null ? ptypeTree.getId() : (ptype.getId() == 0 ? 1 : ptype.getId());  
		policy = new Policy();
		policy.setCreateId(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));  
		policys = policyDao.getPolicyList(policy,nid, ptypeTree,false,getPageNow(), getPageSize());
		count = policyDao.getPolicyListSize(policy,nid, ptypeTree,false);
		return "Policy_Audit2List";
	} 
	
	public String Policy_AuditInit() throws ElException{ 
		
		if(policy !=null)
		policyDao.alterPolicyValid(policy.getId(), policy.getValid());
		 
		return "Policy_Audit";
	} 
	
	public String Policy_UpdateScanning() throws ElException, Exception {
		if(st == null){  
			return "Policy_UpdateScanning";
		}
		if (st.length() > 10 * 1024 * 1024) {
			setElmessage("您上传的文件过大！");
			return "Policy_UpdateScanning";
		} else {
			String ext = J2EEFileUtil.getExtention(stFileName);
			if(!ext.equals("jpg")){ 
				setElmessage("扫描文件格式只能为jpg！");
				return "IC_U_UpdateDemo";
			} 
			policyDao.updateScanning(policy.getId(), ".jpg"); 
			J2EEFileUtil.upload(st, ext, "pfms\\policy\\scanning", policy.getId()+"");
		} 
		return "Policy_UpdateScanning";
	}  

	public String Policy_downloadScanningInit() throws Exception{
		try {
			getInputStream(); 
		} catch (Exception e) {
			setElmessage("文件不存在或其他原因导致文件不能下载！");
			return "error";
		}
		return "Policy_downloadScanning";
	}

	public InputStream getInputStream() throws ElException { 
		InputStream is = null;
		String path=ServletActionContext.getServletContext().getRealPath("pfms\\policy\\scanning\\"+fileName);
		try { 
			is = new FileInputStream(path);
		} catch (Exception e) {
			throw new ElException("下载资料出错",e);
		}
        return is;
	}
	public String buyPolicyView()throws ElException{
		baoxianProduct = baoxianProductDao.showBaoxianProduct(baoxianProduct.getId());
		return "buyPolicyView";
	}


	public String Immediately_insurance()throws ElException{
		baoxianProduct = baoxianProductDao.showBaoxianProduct(baoxianProduct.getId());
		return "buyPolicyView";
	}
	public List<Policy> getPolicys() {
		return policys;
	}
	public void setPolicys(List<Policy> policys) {
		this.policys = policys;
	}












}
