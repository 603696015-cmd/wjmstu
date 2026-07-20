package com.sopia.pfms.dao;

import java.util.List;

import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.duman.entities.Department;
import com.sopia.newsandmess.entities.News;
import com.sopia.pfms.entities.MessageBoard;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;

public interface PfmsFrontDao {
	
	public abstract List<PfmsUser> userlist(Department department,int pageNow,int pageSize,PfmsUser pfmsUser) throws ElException;
	public abstract int userCount(Department department,PfmsUser pfmsUser) throws ElException;
	
	public abstract PfmsUser getUser(int userid) throws ElException;
	public abstract List<Product> productList(ElNode productLibTree,int pageNow,int pageSize,int userid,Product product,ElNode department) throws ElException;
	public abstract List<Product> productList(int pageNow,int pageSize,int userid,boolean is_tuijian ,ElNode department) throws ElException;
	public abstract int productListCount(ElNode productLibTree,int userId,Product product,ElNode department) throws ElException;
	
	public abstract List<News> newsList(int pageNow,int pageSize,int userid,ElNode department) throws ElException;
	public abstract int newsListCount(int userid,ElNode department) throws ElException;
	
	/**
	 * 
	 * @param shopid被评论者id
	 * @param userid评论者id
	 * @param pageNow
	 * @param pageSize
	 * @param productId被评论商品id
	 * @return
	 * @throws ElException
	 */
	public abstract List<CourseComment> messageBoardsList(int shopid,int pageNow,int pageSize,int productId) throws ElException;
	public abstract int messageBoardsCount(int shopid,int productId) throws ElException;
	
	public abstract float getAvgPoint(int productId) throws ElException;
	
	public Department getDepTree(int pid, int stopid, boolean containStop)
		throws ElException;
	
	public List<Department> getAllDepTree(boolean containStop) throws ElException;
	
	public List<ProductType> getAllPTypeTree(boolean containStop) throws ElException;

}
