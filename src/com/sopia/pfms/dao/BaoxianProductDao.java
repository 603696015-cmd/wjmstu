package com.sopia.pfms.dao;

import java.sql.Date;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.entities.BaoxianProduct;
import com.sopia.pfms.entities.InsuranceCategories;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Suoshulanmu;

public interface BaoxianProductDao {
	
	public abstract void deleteBaoxianProduct(int id) throws ElException;
	
	public abstract BaoxianProduct showBaoxianProduct(int id) throws ElException;
	
	public abstract void updateBaoxianProduct(int roleId,BaoxianProduct baoxianProduct,int shenhezhuangtai,boolean is_product_fabu_can_alter) throws ElException;
	
	public abstract int getRoleId(int id) throws ElException;
	public abstract ELUser getELUser(int id) throws ElException;
	public abstract List<Shenhezhuangtai> shenhezhuangtaiList() throws ElException;
	public abstract List<Suoshulanmu> suoshulanmuList() throws ElException;
	
	public abstract void addBaoxianProduct(boolean is_baoxian_prouct_sh,BaoxianProduct baoxianProduct,ELUser elUser,int ptype_parent_id) throws ElException;
	
	public abstract void shenheBaoxianProduct(int roleId,int id) throws ElException;
	public abstract void shenheBaoxianProductNotPass(int roleId,int id) throws ElException;
	
	public abstract ProductType getProTypeTree(int from,int stop,boolean constop) throws ElException;
	public ProductType getProTypeLibTree(int userid, String op, int stopid,boolean containStop) throws ElException;
	public abstract List<BaoxianProduct> getBaoxianProductByUidByPerOrShar(boolean is_baoxian_product_sh,String userid, int nid,
			ProductType ptypeTree,Integer status, int pageNow, int pageSize,BaoxianProduct baoxianProduct,Date starttime,Date endtime) throws ElException;
	
	public abstract int getBaoxianProductCountByUidByPerOrShar(boolean is_baoxian_product_sh,String userid, int nid,
			ProductType ptypeTree,Integer status,int pageNow, int pageSize,BaoxianProduct baoxianProduct,Date starttime,Date endtime) throws ElException;
	
	public abstract List<BaoxianProduct> getAllBaoxianProductByUidByPerOrShar( int nid,
			ProductType ptypeTree,Integer status, int pageNow, int pageSize,BaoxianProduct baoxianProduct,Date starttime,Date endtime) throws ElException;
	
	public abstract int getAllBaoxianProductCountByUidByPerOrShar( int nid,
			ProductType ptypeTree,Integer status,int pageNow, int pageSize,BaoxianProduct baoxianProduct,Date starttime,Date endtime) throws ElException;
	
	public abstract ProductType getPtypeLibById(int id) throws ElException;
	
	public abstract List<ELUser> getOpUsers( int typeid)throws ElException;
	
	public abstract void addProducttype(ProductType ptype) throws ElException;
	
	public abstract void alterProductType(ProductType ptype) throws ElException;
	
	
	public abstract void updateProductTypeParentid(int pid, int npid) throws ElException;
	public abstract void updateProductParentid(int pid, int npid) throws ElException;
	public void deletePtype(int id) throws ElException;
	public void deleteProductTypeAndSub(int id) throws ElException;
	
	public abstract BaoxianProduct getBaoxianProductByid(int id) throws ElException;
	public abstract InsuranceCategories getInsuranceCategoryByid(int id) throws ElException;
	
	/**
	 * 判断权限是否存在
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @return
	 * @throws ElException
	 */
	public boolean checkOpUsers(String type, int userid, int ptypeid) throws ElException;
	
	/**
	 * 添加权限
	 * @param type
	 * @param userid
	 * @param ctypeid
	 * @throws ElException
	 */
	public void addOpusers(String type, int userid, int ptypeid) throws ElException;
	
	/**
	 * 修改推荐状态
	 * @param change_id
	 * @param select_tuijian
	 * @param table
	 * @throws ElException
	 */
	public void change_tuijian(int change_id,String select_tuijian,String table) throws ElException;
	
	public List<BaoxianProduct> getSixFrontBaoxianProductList(int pageNow, int pageSize) throws ElException;
	public List<BaoxianProduct> getFrontBaoxianProductList(BaoxianProduct baoxianProduct,int pageNow, int pageSize) throws ElException;
	public int getFrontBaoxianProductCount() throws ElException;
	
	public int getChushenCount() throws ElException;
	public int getZhongshenCount() throws ElException;
	
	
	public int getFrontbaoxianProductListSize(BaoxianProduct baoxianProduct,int pageNow, int pageSize) throws ElException;
	
}
