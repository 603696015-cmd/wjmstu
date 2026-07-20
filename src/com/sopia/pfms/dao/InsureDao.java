package com.sopia.pfms.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.common.ElException;
import com.sopia.pfms.entities.BaoxianProduct;

public interface InsureDao {
	
	public abstract List<BaoxianProduct> getBaoxianProductListByPage(int pageNow,int pageSize,BaoxianProduct baoxianproduct,Timestamp starttime,Timestamp endtime) throws ElException;
	
	public abstract int getBaoxianProductCount(BaoxianProduct baoxianproduct,Timestamp starttime,Timestamp endtime) throws ElException;

}
