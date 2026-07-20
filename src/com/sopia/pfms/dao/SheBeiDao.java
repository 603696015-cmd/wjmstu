package com.sopia.pfms.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.duman.entities.ELUser;
import com.sopia.pfms.entities.SheBei;
import com.sopia.pfms.entities.Shenhezhuangtai;
import com.sopia.pfms.entities.Toubaozhuangtai;

public interface SheBeiDao {
	
	public abstract List<SheBei> shebeilist(boolean is_shebei_sh,int start,int size,int id,SheBei shebei,Timestamp starttime,Timestamp endtime) throws ElException;
	public abstract List<SheBei> shebeilist(int start,int size,SheBei shebei,Timestamp starttime,Timestamp endtime) throws ElException;
	public abstract int getCount(boolean is_shebei_sh,int id,SheBei shebei,Timestamp starttime,Timestamp endtime) throws ElException;
	public abstract int getCount(SheBei shebei,Timestamp starttime,Timestamp endtime) throws ElException;
	
	public abstract List<Toubaozhuangtai> toubaozhuangtaiList() throws ElException;
	
	public abstract List<Shenhezhuangtai> shenhezhuangtaiList() throws ElException;
	
	public abstract void addShebei(boolean is_shebei_sh,SheBei shebei,ELUser elUser) throws ElException;
	public abstract ELUser getELUser(int id) throws ElException;
	
	public abstract void deleteShebei(int id) throws ElException;
	
	public abstract SheBei showShebei(int id) throws ElException; 
	
	public abstract void updateShebei(SheBei shebei,int userid,int shenhezhuangtai,boolean is_product_fabu_can_alter) throws ElException;
	public abstract int getRoleId(int id) throws ElException;
	public abstract void shenheShebei(int roleId,int id) throws ElException;
	public abstract void shenheShebeiNotPass(int roleId,int id) throws ElException;
	
	public abstract boolean checkShzt(int id,String table) throws ElException;
	
	
	public abstract List<SheBei> searchShebei(int userid,String tablename,int pageNow,int pageSize ) throws ElException;
	public abstract int searchShebeiSize(int userid,String tablename) throws ElException;
	

}
