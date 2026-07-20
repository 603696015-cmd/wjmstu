package com.sopia.bookinfo.action;



import java.sql.Timestamp;
import java.util.List;
import java.io.File;
import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.bookinfo.dao.BookInfoDao;
import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.bookinfo.entities.Bookinfo;

import com.sopia.common.CheckHtml;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.common.spring.SpringContextUtil;

public class BookInfoAction  extends BaseAction {
	
	private BookInfoDao bookInfoDao; 
	private BookTypeTree bookTypeTree;
	private BookTypeTree btype;
	private Timestamp release;
	private File imgFile;//上传文件
	private String imgFileFileName;//上传文件
	private Bookinfo bookinfo;
	private List<Bookinfo> listb;
	private Timestamp  start;//修改开始时间
	private Timestamp  end;//修改结束时间
	private int        delestatus;//删除判断
	private Bookinfo flagbookinfo;//保存之前的bookinf对象
	private int authorinfostatus;//判断是否修改作者信息
	private int bookinfostatus;//判断是否修改图书信息
	private int directoryinfostatus;//判断是否修改目录信息
	private String sbookinfo;
	private String sauthorinfo;
	private String sdirectoryinfo;
	private String bookids;
	
	
	
	public String getBookids() {
		return bookids;
	}



	public void setBookids(String bookids) {
		this.bookids = bookids;
	}



	public String getSbookinfo() {
		return sbookinfo;
	}



	public void setSbookinfo(String sbookinfo) {
		this.sbookinfo = sbookinfo;
	}



	public String getSauthorinfo() {
		return sauthorinfo;
	}



	public void setSauthorinfo(String sauthorinfo) {
		this.sauthorinfo = sauthorinfo;
	}



	public String getSdirectoryinfo() {
		return sdirectoryinfo;
	}



	public void setSdirectoryinfo(String sdirectoryinfo) {
		this.sdirectoryinfo = sdirectoryinfo;
	}



	public Bookinfo getFlagbookinfo() {
		return flagbookinfo;
	}



	public void setFlagbookinfo(Bookinfo flagbookinfo) {
		this.flagbookinfo = flagbookinfo;
	}



	public int getAuthorinfostatus() {
		return authorinfostatus;
	}



	public void setAuthorinfostatus(int authorinfostatus) {
		this.authorinfostatus = authorinfostatus;
	}



	public int getBookinfostatus() {
		return bookinfostatus;
	}



	public void setBookinfostatus(int bookinfostatus) {
		this.bookinfostatus = bookinfostatus;
	}



	public int getDirectoryinfostatus() {
		return directoryinfostatus;
	}



	public void setDirectoryinfostatus(int directoryinfostatus) {
		this.directoryinfostatus = directoryinfostatus;
	}



	public int getDelestatus() {
		return delestatus;
	}



	public void setDelestatus(int delestatus) {
		this.delestatus = delestatus;
	}



	public Timestamp getStart() {
		return start;
	}



	public void setStart(Timestamp start) {
		this.start = start;
	}



	public Timestamp getEnd() {
		return end;
	}



	public void setEnd(Timestamp end) {
		this.end = end;
	}



	public List<Bookinfo> getListb() {
		return listb;
	}



	public void setListb(List<Bookinfo> listb) {
		this.listb = listb;
	}



	public Bookinfo getBookinfo() {
		return bookinfo;
	}



	public void setBookinfo(Bookinfo bookinfo) {
		this.bookinfo = bookinfo;
	}



	public File getImgFile() {
		return imgFile;
	}



	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}



	public String getImgFileFileName() {
		return imgFileFileName;
	}



	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}



	public Timestamp getRelease() {
		return release;
	}



	public void setRelease(Timestamp release) {
		this.release = release;
	}



	public BookTypeTree getBtype() {
		return btype;
	}



	public void setBtype(BookTypeTree btype) {
		this.btype = btype;
	}



	public BookInfoDao getBookInfoDao() {
		return bookInfoDao;
	}



	public void setBookInfoDao(BookInfoDao bookInfoDao) {
		this.bookInfoDao = bookInfoDao;
	}



	public BookTypeTree getBookTypeTree() {
		return bookTypeTree;
	}



	public void setBookTypeTree(BookTypeTree bookTypeTree) {
		this.bookTypeTree = bookTypeTree;
	}


	/**
	 * 得到图书目录
	 * @return
	 * @throws ElException
	 */
	public  String  bookTypeTree() throws ElException{
		bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		return "bookTypeTree_success";
	}
	/**
	 * 显示图书类型的详细信息
	 * @return
	 * @throws ElException
	 */
	public String bookType_view() throws ElException {
		bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		btype = bookInfoDao.getClTypeById(btype.getId());
		return "booktype_view_success";
	}
	/**
	 * 添加新图书类型页面
	 * @return
	 * @throws ElException
	 */
	public  String  bookType_addInit() throws ElException{
		bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		
		
		return "bookType_addInit_success";
	}
	/**
	 * 添加图书类别
	 * @return
	 * @throws ElException
	 */
	public String  bookType_add() throws ElException{
		
		bookInfoDao.addBtype(btype);
		btype=bookInfoDao.getClTypeById(btype.getId());
		ElLogger.busilogger(getSessionIntValue(ElConstants.SESSION_USERID),
				ElLoggerConstants.LOG_MOD_COURSELIB,
				ElLoggerConstants.LOG_TYPE_ADD,btype.getName(),
				ElLoggerConstants.LOG_RES_SUCC,btype.getId());
		return "booktype_add_success";
		
	}
	
	/**
	 * 修改无图书类别 显示图书类型的详细信息
	 * @return
	 * @throws ElException
	 */
	public String bookType_updinit() throws ElException {
		bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		btype = bookInfoDao.getClTypeById(btype.getId());
		return "bookType_updinit_success";
	}
	/**
	 * 修改图书类别 
	 * @return
	 * @throws ElException
	 */
	public String bookType_upd() throws ElException {
		bookInfoDao.updBookType(btype);
		return "bookType_upd_success";
	}
	
	/**
	 * 删除图书类别
	 * @return
	 * @throws ElException
	 */
	public String deleteBooktype() throws ElException{
		
		bookInfoDao.deleteBtype(btype.getId());
		return "deleteBooktype_success";
	}
	/**
	 * 图书信息添加页
	 * @return
	 */
	public String bookinfo_addinit(){
		//返回当前时间为发布时间
		 bookinfo = new Bookinfo();
		
		release = new  Timestamp(System.currentTimeMillis());
		bookinfo.setRelease(release);
		return "bookinfo_addinit_success";
	}
	/**
	 * 图书类型信息添加页
	 * @return
	 * @throws ElException 
	 */
	public String bookinfo_typeaddinit() throws ElException{
		bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		
		return "bookinfo_typeaddinit_success";
	}
	/**
	 * 图书信息添加
	 * @return
	 * @throws Exception 
	 */
	public String bookinfo_add() throws Exception{
		bookinfo.setUserid(getSessionIntValue(ElConstants.SESSION_USERID));
		bookInfoDao.addbookinfo(bookinfo);
		return "bookinfo_add_success";
	}
	/**
	 * 图书显示
	 * @return
	 * @throws Exception 
	 */
	public String bookinfo_view() throws Exception{
		bookinfo=bookInfoDao.bookinfo_view(bookinfo.getId());
		return "bookinfo_view_success";
	}
	/**
	 * 图书状态信息修改（删除，审核）
	 * @return
	 * @throws Exception 
	 */
	public String bookinfo_dele() throws Exception{
		
		
		if(delestatus==1){//等于1 代表我发布的图书删除
			bookInfoDao.delebookinfo(bookinfo.getId(),3);
			return bookinfo_listview();
			
		}else if(delestatus==2){//管理员页面删除
			bookInfoDao.delebookinfo(bookinfo.getId(),3);
			return bookinfo_alllistview();
			
		}else if(delestatus==3){//管理员页面审核通过
			bookInfoDao.delebookinfo(bookinfo.getId(),2);
			return bookinfo_alllistview();
		}else{
			bookInfoDao.delebookinfo(bookinfo.getId(),1);
			return bookinfo_alllistview();
			
		}
		
	}
	/**
	 * 批量审核
	 * @return
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public  String  bookinfo_check() throws NumberFormatException, ElException{
		String bookinfo[] = this.getBookids().split(",");
		for (int i = 0; i < bookinfo.length; i++) {
			bookInfoDao.delebookinfo(Integer.valueOf(bookinfo[i]),2);
		}
		return bookinfo_alllistview();
	}
	/**
	 * 批量审核不通过
	 * @return
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public  String  bookinfo_check2() throws NumberFormatException, ElException{
		String bookinfo[] = this.getBookids().split(",");
		for (int i = 0; i < bookinfo.length; i++) {
			bookInfoDao.delebookinfo(Integer.valueOf(bookinfo[i]),1);
		}
		return bookinfo_alllistview();
	}
	/**
	 * 批量推荐
	 * @return
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public  String  bookinfo_tuijian() throws NumberFormatException, ElException{
		String bookinfo[] = this.getBookids().split(",");
		for (int i = 0; i < bookinfo.length; i++) {
			bookInfoDao.tuijian(Integer.valueOf(bookinfo[i]),2);
		}
		return bookinfo_alllistview();
	}
	/**
	 * 批量取消推荐
	 * @return
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public  String  bookinfo_notuijian() throws NumberFormatException, ElException{
		String bookinfo[] = this.getBookids().split(",");
		for (int i = 0; i < bookinfo.length; i++) {
			bookInfoDao.tuijian(Integer.valueOf(bookinfo[i]),1);
		}
		return bookinfo_alllistview();
	}
	/**
	 * 图书修改页面
	 * @return
	 * @throws Exception 
	 */
	public String bookinfo_updinit() throws Exception{
		flagbookinfo=new Bookinfo();
		//获得图书信息
		bookinfo=bookInfoDao.bookinfo_view(bookinfo.getId());
		//保存 一些 在修改的过程中 会并发改变的图书信息
		flagbookinfo.setStatuse(bookinfo.getStatuse());
		flagbookinfo.setRecommend(bookinfo.getRecommend());
		flagbookinfo.setClick(bookinfo.getClick());
		sbookinfo=bookinfo.getBookinfo();
		sauthorinfo=bookinfo.getAuthorinfo();
		sdirectoryinfo=bookinfo.getDirectoryinfo();
		return "bookinfo_updinit_success";
	}
	
	/**
	 * 图书信息修改
	 * @return
	 * @throws Exception 
	 */
	public String bookinfo_upd() throws Exception{
		
		//图书基本信息修改
		bookInfoDao.updbookinfo(bookinfo, flagbookinfo.getClick(), flagbookinfo.getRecommend(), flagbookinfo.getStatuse(), authorinfostatus,
				bookinfostatus, directoryinfostatus);
		//图书封面修改
		
		return "bookinfo_updinit_success";
	}
	/**
	 * 得到我的图书列表
	 * @return
	 * @throws ElException
	 */
	public String bookinfo_listview() throws ElException{
		bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		int nid = btype == null ? bookTypeTree.getId() : (btype.getId() == 0 ? 1 : btype.getId());
		listb=bookInfoDao.bookinfoList(getSessionIntValue(ElConstants.SESSION_USERID), bookinfo, start, end, bookTypeTree, nid,getPageNow(), getPageSize());
		count=bookInfoDao.bookinfoListsize(getSessionIntValue(ElConstants.SESSION_USERID), bookinfo, start, end, bookTypeTree, nid);
		return "bookinfo_listview_success";
		
	}
	/**
	 * 得到所有图书列表
	 * @return
	 * @throws ElException
	 */
	public String bookinfo_alllistview() throws ElException{
		bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		int nid = btype == null ? bookTypeTree.getId() : (btype.getId() == 0 ? 1 : btype.getId());
		listb=bookInfoDao.bookinfoAllList(bookinfo, start, end, bookTypeTree, nid,getPageNow(), getPageSize());
		count=bookInfoDao.bookinfoAllListSize(bookinfo, start, end, bookTypeTree, nid);
		
		
		return "bookinfo_alllistview_success";
		
	}
	/**
	 * 图书中心列表 查询
	 * @return
	 * @throws ElException
	 */
	public String front_bookinfo_allistview() throws ElException{
		if(bookinfo!=null&&"填写图书名称....".equals(bookinfo.getName()))
		{ 
			bookinfo.setName("");
			}
		bookTypeTree = bookInfoDao.getCltypeTree(ElConstants.TREE_ROOT,ElConstants.TREE_FIANL, true);
		int nid = btype == null ? bookTypeTree.getId() : (btype.getId() == 0 ? 1 : btype.getId());
		listb=bookInfoDao.front_bookinfoAllList(bookinfo, bookTypeTree, nid,getPageNow(), getPageSize());
		for (Bookinfo b : listb) {
			//过滤图书简介
			sbookinfo=CheckHtml.getString(b.getBookinfo());
			//截取长度
			b.setBookinfo((sbookinfo.length() > 120) ? sbookinfo.substring(0, 117)+ "..." : sbookinfo) ;
		}
		count=bookInfoDao.front_bookinfoAllListSize(bookinfo, bookTypeTree, nid);
		return "front_bookinfo_allistview_success";
	}
	public String front_bookinfo_view() throws ElException{
		//得到图书信息
		bookinfo=bookInfoDao.bookinfo_view(bookinfo.getId());
//		//过滤图书简介 截取长度
//		sbookinfo=CheckHtml.getString(bookinfo.getAuthorinfo());
//		bookinfo.setBookinfo((sbookinfo.length() > 300) ? sbookinfo.substring(0, 297)+ "..." : sbookinfo) ;
//		//过滤作者简介 截取长度
//		sauthorinfo=CheckHtml.getString(bookinfo.getAuthorinfo());
//		bookinfo.setAuthorinfo((sauthorinfo.length() > 300) ? sauthorinfo.substring(0, 297)+ "..." : sauthorinfo) ;
//		
//		//过滤目录信息 截取长度
//		sdirectoryinfo=CheckHtml.getString(bookinfo.getDirectoryinfo());
//		bookinfo.setDirectoryinfo((sdirectoryinfo.length() > 100) ? sdirectoryinfo.substring(0, 97)+ "..." : sdirectoryinfo) ;
		//增加点击数
		
		return "front_bookinfo_view";
	}
		
}
