package com.sopia.bookinfo.dao;

import java.sql.Timestamp;
import java.util.List;

import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.bookinfo.entities.Bookinfo;
import com.sopia.common.ElException;

public interface BookInfoDao {
	
	/**
	 * 得到所有的课程树
	 * @param from
	 * @param stop
	 * @param containStop
	 * @return
	 * @throws ElException
	 */
	public BookTypeTree getCltypeTree(int from, int stop, boolean containStop) throws ElException;
	/**
	 * 根据ID得到课程树
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public BookTypeTree getClTypeById(int id) throws ElException;
	/**
	 * 添加图书类别
	 * @param btype
	 * @throws ElException
	 */
	public void addBtype(BookTypeTree btype) throws ElException;
	/**
	 * 删除图书类别
	 * @param id
	 * @throws ElException
	 */
	public void deleteBtype(int id) throws ElException ;
	
	/**
	 * 修改图书类别
	 * @param btype
	 * @throws ElException
	 */
	public  void  updBookType(BookTypeTree btype) throws ElException;
	/**
	 * 添加图书信息
	 * @param bookinfo
	 * @return
	 * @throws ElException
	 */
	public  int addbookinfo(Bookinfo bookinfo) throws ElException;
	/**
	 * 添加图书封面URL
	 * @param id
	 * @param url
	 * @throws ElException
	 */
	public  void addbookinfo_picture(int id ,String url) throws ElException;
	/**
	 * 删除图书信息
	 * @param id
	 * @throws ElException
	 */
	
	public  void  delebookinfo(int id,int status) throws ElException;
	/**
	 * 批量推荐修改
	 * @param bookid
	 * @param value
	 * @throws ElException
	 */
	public  void   tuijian(int  bookid,int value) throws ElException;
	/**
	 * 修改图书信息
	 * @param bookinfo ：图书
	 * @param dianji ：点击数
	 * @param recommend ：推荐属性
	 * @param status ：状态
	 * @param authorinfostatus ：作者信息
	 * @param bookinfostatus ：图书信息
	 * @param directoryinfostatus ：目录信息
	 * @throws ElException
	 */
	public  void  updbookinfo(Bookinfo bookinfo,int dianji,int recommend,int status,
			int authorinfostatus,int bookinfostatus,int directoryinfostatus) throws ElException;
	/**
	 * 图书信息的显示
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public  Bookinfo  bookinfo_view(int id ) throws ElException;
	/**
	 * 得到我发布的图书列表
	 * @param id:用户id
	 * @param b:	图书搜索信息
	 * @param start :修改开始时间
	 * @param end	:修改结束时间
	 * @param bookTypeTree: 图书类别树
	 * @param btid	:类型ID
	 * @param pageNow	
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Bookinfo>  bookinfoList(int id,Bookinfo b,Timestamp start,Timestamp end,
			BookTypeTree bookTypeTree,int btid,int pageNow, int pageSize) throws ElException;
	public int  bookinfoListsize(int id,Bookinfo b,Timestamp start,Timestamp end,
			BookTypeTree bookTypeTree,int btid) throws ElException;
	/**
	 * 得到所有图书列表
	 * @param b
	 * @param start
	 * @param end
	 * @param bookTypeTree
	 * @param btid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Bookinfo>  bookinfoAllList(Bookinfo b,Timestamp start,Timestamp end,
			BookTypeTree bookTypeTree,int btid,int pageNow, int pageSize) throws ElException;
	public int  bookinfoAllListSize(Bookinfo b,Timestamp start,Timestamp end,
			BookTypeTree bookTypeTree,int btid) throws ElException;
	/**
	 * 前台选课中心 搜索图书
	 * @param b
	 * @param bookTypeTree
	 * @param btid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Bookinfo>  front_bookinfoAllList(Bookinfo b,BookTypeTree bookTypeTree,
			int btid,int pageNow, int pageSize) throws ElException;
	public int  front_bookinfoAllListSize(Bookinfo b,BookTypeTree bookTypeTree,
			int btid) throws ElException;
	
	/**
	 * 点击数+1
	 * @param id
	 * @throws ElException
	 */
	public void addclick(int id) throws ElException;
	
	/**
	 * 前台图书推荐列表

	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	
	public List<Bookinfo>  front_bookinfotuijianList(int pageNow, int pageSize) throws ElException;
}
