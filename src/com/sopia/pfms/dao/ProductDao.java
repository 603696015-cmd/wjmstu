package com.sopia.pfms.dao;

import java.sql.Date;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;
import com.sopia.pfms.entities.Suoshulanmu;

public interface ProductDao {
	
	public abstract void deleteChanpin(int id) throws ElException;
	
	public abstract Product showChanpin(int id,int showType) throws ElException;
	
	public abstract void updateChanpin(int roleId,Product product,int shenhezhuangtai,boolean is_product_fabu_can_alter) throws ElException;
	
	public abstract int getRoleId(int id) throws ElException;
	
	public abstract List<Suoshulanmu> suoshulanmuList() throws ElException;
	
	public abstract void addProduct(boolean is_product_sh,Product product,ELUser elUser,int ptype_parent_id) throws ElException;
	
	public abstract ELUser getELUser(int id) throws ElException;
	
	public abstract void updateShenhezhuangtai(int roleId,int id) throws ElException;
	public abstract void updateShenhezhuangtaiNotPass(int roleId,int id) throws ElException;
	
	public abstract ProductType getProTypeTree(int from,int stop,boolean constop) throws ElException;
	public abstract ProductType getPtypeByid(int id) throws ElException;
	
	public abstract List<Product> getProductByUidByPerOrShar(String type,ElNode productLibTree,int sublibs,String userid, int nid,
			ProductType ptypeTree,Integer status, int pageNow, int pageSize,Product product,Date starttime,Date endtime) throws ElException;
	
	public abstract int getProductCountByUidByPerOrShar(String type,ElNode productLibTree,int sublibs,String userid, int nid,
			ProductType ptypeTree,Integer status,int pageNow, int pageSize,Product product,Date starttime,Date endtime) throws ElException;
	
	public abstract List<Product> getAllProductByUidByPerOrShar(ElNode productLibTree,int sublibs, int nid,
			ProductType ptypeTree,Integer status, int pageNow, int pageSize,Product product,Date starttime,Date endtime) throws ElException;
	
	public abstract int getAllProductCountByUidByPerOrShar(ElNode productLibTree,int sublibs, int nid,
			ProductType ptypeTree,Integer status,int pageNow, int pageSize,Product product,Date starttime,Date endtime) throws ElException;
	
	public abstract List<ELUser> getOpUsers( int typeid)throws ElException;
	
	public abstract void addProducttype(ProductType ptype) throws ElException;
	
	public abstract void alterProductType(ProductType ptype) throws ElException;
	
	public abstract ProductType getPtypeLibById(int id) throws ElException;
	
	public abstract void updateProductTypeParentid(int pid, int npid) throws ElException;
	public abstract void updateProductParentid(int pid, int npid) throws ElException;
	public void deletePtype(int id) throws ElException;
	public void deleteProductTypeAndSub(int id) throws ElException;
	
	public List<Product> getFrontProductList( int pageNow, int pageSize) throws ElException;
}
