package com.sopia.shebeipinggu.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.ElException;
import com.sopia.common.PfmsUtil;
import com.sopia.pfms.dao.BaoxianProductDao;
import com.sopia.pfms.dao.InsureDao;
import com.sopia.pfms.dao.ProductDao;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;

public class PG_InsureAction extends BaseAction {
	private InsureDao insureDao;
	private BaoxianProductDao baoxianProductDao;
	private ProductDao productDao;
	
	private List<BaoxianProduct> baoxianProductList = new ArrayList<BaoxianProduct>();
	private BaoxianProduct baoxianProduct;
	private int count;
	private int id;
	
	private String[] sArray;
	private String jiequ_jianjie;//截取的简介
	private String all_jianjie;//数据库中取出的简介
	
	private Timestamp starttime;
	private Timestamp endtime;
	
	private ProductType ptypeTree;
	private ProductType ptype;
	
	private int shebei_type;//辨别是否是设备险
	
	
	
	public String insure_online() throws ElException{
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
		
		baoxianProductList = insureDao.getBaoxianProductListByPage(
				getPageNow(), getPageSize(),baoxianProduct,starttime,endtime);
		
		count = insureDao.getBaoxianProductCount(baoxianProduct,starttime,endtime);
		
		return "insure_online";
	}
	
	public String combinationSearchBaoxianProductInit() throws ElException{
		//保险产品所属栏目树
		ptypeTree = baoxianProductDao.getProTypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		return "combinationSearchBaoxianProductInit";
	}
	
	
	public String insure_baodan() throws ElException{
		baoxianProduct = baoxianProductDao.getBaoxianProductByid(id);
		all_jianjie = baoxianProduct.getJianjie();
		
		String jianjie = CheckHtml.getString(all_jianjie);
		jiequ_jianjie = jianjie.length() > 200 ? jianjie.substring(0, 200)+ "......" : jianjie;
		
		
		if(baoxianProduct.getChanpinliangdian() != null){
			sArray = new PfmsUtil().changeStringToStringArray(baoxianProduct.getChanpinliangdian());
		}
		
		if(sArray == null)
			sArray = new String[1];
		
		//如果是设备险
//		if(baoxianProduct.getInsuranceCategories() != null && baoxianProduct.getInsuranceCategories().getName() != null && baoxianProduct.getInsuranceCategories().getName().equals("设备险")){
//			shebei_type = 1;
//		}
		
		return "insure_baodan";
	}
	
	
	

	public ProductType getPtype() {
		return ptype;
	}

	public void setPtype(ProductType ptype) {
		this.ptype = ptype;
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

	public InsureDao getInsureDao() throws ElException{
		return insureDao;
	}

	public void setInsureDao(InsureDao insureDao) {
		this.insureDao = insureDao;
	}
	public BaoxianProductDao getBaoxianProductDao() {
		return baoxianProductDao;
	}
	public void setBaoxianProductDao(BaoxianProductDao baoxianProductDao) {
		this.baoxianProductDao = baoxianProductDao;
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

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public int getShebei_type() {
		return shebei_type;
	}

	public void setShebei_type(int shebei_type) {
		this.shebei_type = shebei_type;
	}
	

}
