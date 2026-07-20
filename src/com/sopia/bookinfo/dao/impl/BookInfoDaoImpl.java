package com.sopia.bookinfo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.sopia.bookinfo.dao.BookInfoDao;
import com.sopia.bookinfo.entities.BookType;
import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.bookinfo.entities.Bookinfo;
import com.sopia.classman.ClassConstants;
import com.sopia.classman.dao.impl.ElClTypeDaoImpl;
import com.sopia.classman.entities.ElClType;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.courseman.CourseConstants;
import com.sopia.courseman.entities.CourseType;
import com.sopia.duman.entities.ELUser;
import com.sopia.knowledgeman.entities.KnowledgeType;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class BookInfoDaoImpl implements BookInfoDao{
	private static final Log logger = LogFactory.getLog(ElClTypeDaoImpl.class);
	public BookTypeTree getCltypeTree(int from, int stop, boolean containStop/*,
	String status*/) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		BookTypeTree cltype = null;
		try {
			if (from == 0) {
				cltype = getCltypeRoot();
			} else {
				cltype = getClTypeById(from);
			}
			ct = DBConnection.getConnection();
			cltype.setChild(getChilds(ct, cltype.getId(), stop, containStop, 0/*, status*/)) ;
		} catch (Exception e) {
			logger.error("图书类别树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
		}
	public BookTypeTree getCltypeRoot() throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		BookTypeTree clt = new BookTypeTree();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name,description,fatherid from booktype where fatherid = ?");
			ps.setInt(1, 0);
//			ps.setString(2, "%%") ; 
			rs = ps.executeQuery();
			if (rs.next()) {
				clt.setId(rs.getInt(1));
				clt.setName(rs.getString(2));
				clt.setDescription(rs.getString(3));
				clt.setParent(new ElClType(rs.getInt(4)));
			}
		} catch (Exception e) {
			logger.error("获取书目录树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return clt;
	}
	public BookTypeTree getClTypeById(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		BookTypeTree clt = new BookTypeTree();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select el1.id,el1.name,el1.description,el1.fatherid,el2.name " +
					" from booktype el1 left join booktype el2 on el1.fatherid = el2.id where el1.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				clt.setId(rs.getInt(1));
				clt.setName(rs.getString(2));
				clt.setDescription(rs.getString(3));
				clt.setParent(new BookTypeTree(rs.getInt(4),rs.getString(5)));
			}
		} catch (Exception e) {
			logger.error("获取书目录树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return clt;
	}
	private List<BookTypeTree> getChilds(Connection ct, int from, int stop,
			boolean containStop, int level/*, String status*/) throws Exception {
		List<BookTypeTree> deps = new ArrayList<BookTypeTree>();
		PreparedStatement ps=ct.prepareStatement("select id,name,description,fatherid from booktype where fatherid = ?");
		ps.setInt(1, from) ;

//		ps.setString(2, "%"+status+"%");
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			BookTypeTree dep = new BookTypeTree(rstemp.getInt(1), rstemp
					.getString(2));
			dep.setDescription(rstemp.getString(3));
			dep.setParent(new BookTypeTree(rstemp.getInt(4)));
			dep.setLevel(level);
			if (dep.getId() != stop)
				dep.setChild(getChilds(ct,dep.getId(), stop,
						containStop, level/*, status*/));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		} 
		ps.close();
		rstemp.close();
		return deps;
	}
	
	
	public void addBtype(BookTypeTree btype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("insert into booktype(fatherid,name,description)  values(?,?,?)");
//			addNode(ct, ctype, "course_type", "1=1");
			ps.setInt(1, btype.getParent().getId());
			ps.setString(2, btype.getName());
			ps.setString(3, btype.getDescription());

			ps.executeUpdate();
			
			
		} catch (Exception e) {
			logger.error("添加图书类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void deleteBtype(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {// TODO 删除课程类别
			ct = DBConnection.getConnection();
			int parentid = 0;
			ps = ct.prepareStatement("select el1.id,el1.name,el1.description,el1.fatherid,el2.name " +
					" from booktype el1 left join booktype el2 on el1.fatherid = el2.id where el1.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				parentid = rs.getInt(4);
			}
			rs.close();
			// 将该类别下课程设置成上级类别
			ps = ct.prepareStatement("update bookinfo set typeid = ? where typeid = ?");
			ps.setInt(1, parentid);
			ps.setInt(2, id);
			ps.executeUpdate();

			rs.close();
			// 将该类别下类别设置成上级类别 update course_type set parentid = ? where parentid =?
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(CourseConstants.CTYPE_PARENT_SET));
			ps = ct.prepareStatement(" update booktype set fatherid = ? where fatherid =?");
			ps.setInt(1, parentid);
			ps.setInt(2, id);
			ps.executeQuery();
			deleteBtype(ct, id);
		} catch (Exception e) {
			logger.error("删除图书类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	private void deleteBtype(Connection ct, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {// TODO 删除课程类别
			ps = ct.prepareStatement("delete from booktype where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除图书类别信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public  void  updBookType(BookTypeTree btype) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("update booktype set fatherid = ?, name=?,description=? where id =?");
//			addNode(ct, ctype, "course_type", "1=1");
			ps.setInt(1, btype.getParent().getId());
			ps.setString(2, btype.getName());
			ps.setString(3, btype.getDescription());
			ps.setInt(4, btype.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改图书类别失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
	}
	public  int addbookinfo(Bookinfo bookinfo) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int id=0;
		String sql = "insert   into  bookinfo(userid,typeid,version,format,page,words,click,recommend,status,author," +
				"name,paper,package,readurl,press,pressdate,printdate,release,marketprice,vipprice," +
				"picture,authorinfo,bookinfo,directoryinfo,upddate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
				"empty_blob(),empty_blob(),empty_blob(),sysdate)";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
//			addNode(ct, ctype, "course_type", "1=1");
			ps.setInt(1, bookinfo.getUserid());
			ps.setInt(2, bookinfo.getBookType().getId());
			ps.setInt(3, bookinfo.getVersion());
			ps.setInt(4, bookinfo.getFormat());
			ps.setInt(5, bookinfo.getPage());
			ps.setInt(6, bookinfo.getWords());
			ps.setInt(7, 0);
			ps.setInt(8, 1);
			ps.setInt(9, 1);
			ps.setString(10, bookinfo.getAuthor());
			ps.setString(11, bookinfo.getName());
			ps.setString(12, bookinfo.getPaper());
			ps.setString(13, bookinfo.getSpackage());
			ps.setString(14, bookinfo.getReadurl());
			ps.setString(15, bookinfo.getPress());
			ps.setTimestamp(16, bookinfo.getPressdate());
			ps.setTimestamp(17, bookinfo.getPrintdate());
			ps.setTimestamp(18, bookinfo.getRelease());
			ps.setFloat(19, bookinfo.getMarketprice());
			ps.setFloat(20, bookinfo.getVipprice());
			ps.setString(21, bookinfo.getPicture());
			ps.executeUpdate(); 
			if(bookinfo.getAuthorinfo()!=null){
				OracleBlob setblob1 = new OracleBlob(ct,"bookinfo_sequence","bookinfo","id","authorinfo",bookinfo.getAuthorinfo(),"添加作者简介失败");
				setblob1.addContent(); 
			}
			if(bookinfo.getBookinfo()!=null){
				OracleBlob setblob2 = new OracleBlob(ct,"bookinfo_sequence","bookinfo","id","bookinfo",bookinfo.getBookinfo(),"添加图书简介失败");
				setblob2.addContent(); 
			}
			if(bookinfo.getDirectoryinfo()!=null){
			OracleBlob setblob3 = new OracleBlob(ct,"bookinfo_sequence","bookinfo","id","directoryinfo",bookinfo.getDirectoryinfo(),"添加目录简介失败");

			setblob3.addContent(); 
			}
			ps = ct.prepareStatement("select bookinfo_sequence.currval from dual ");
			rs = ps.executeQuery();  
			if (rs.next()){
				id =  rs.getInt(1); 
			}
			return id;
		} catch (Exception e) {
			logger.error("添加图书信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
		
		
	}
	
	public  void addbookinfo_picture(int id ,String url) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "update bookinfo set picture=? where id = ? ";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setString(1, url);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加图书封面图片地址失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
		
		
	}
public  Bookinfo  bookinfo_view(int id ) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Bookinfo b=null;
		String sql = "select bi.userid,bi.typeid,bi.version,bi.format,bi.page,bi.words,bi.click,bi.recommend,bi.status,bi.author," +
				"bi.name,bi.paper,bi.package,bi.readurl,bi.press,bi.pressdate,bi.printdate,bi.release,bi.marketprice,bi.vipprice," +
				"bi.authorinfo,bi.bookinfo,bi.directoryinfo,bi.upddate,bi.picture,el.realname, bt.name btname from  bookinfo bi,eluser el ,booktype bt " +
				" where bi.id =? and el.id=bi.userid and bi.typeid=bt.id ";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				b=new Bookinfo();
				ELUser  u = new ELUser();
				u.setId(rs.getInt(1));
				u.setRealname(rs.getString(26));
				b.setUser(u);
				BookType bt = new BookType();
				bt.setId(rs.getInt(2));
				bt.setName(rs.getString(27));
				b.setBookType(bt);
				b.setVersion(rs.getInt(3));
				b.setFormat(rs.getInt(4));
				b.setPage(rs.getInt(5));
				b.setWords(rs.getInt(6));
				b.setClick(rs.getInt(7));
				b.setRecommend(rs.getInt(8));
				b.setStatuse(rs.getInt(9));
				b.setAuthor(rs.getString(10));
				b.setName(rs.getString(11));
				b.setPaper(rs.getString(12));
				b.setSpackage(rs.getString(13));
				b.setReadurl(rs.getString(14));
				b.setPress(rs.getString(15));
				b.setPressdate(rs.getTimestamp(16));
				b.setPrintdate(rs.getTimestamp(17));
				b.setRelease(rs.getTimestamp(18));
				b.setUpddate(rs.getTimestamp(24));
				b.setMarketprice(rs.getFloat(19));
				b.setVipprice(rs.getFloat(20));
				b.setAuthorinfo(new OracleBlob().getContent(rs.getBlob(21)));
				b.setBookinfo(new OracleBlob().getContent(rs.getBlob(22)));
				b.setDirectoryinfo(new OracleBlob().getContent(rs.getBlob(23)));
				b.setPicture(rs.getString(25));
				b.setId(id);
			}
			return b;
		} catch (Exception e) {
			logger.error("得到图书详细信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
		
		
	}
	public  void  updbookinfo(Bookinfo bookinfo,int dianji,int recommend,int status,
			int authorinfostatus,int bookinfostatus,int directoryinfostatus) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str ="";
		
		if(bookinfo.getClick()!=null&&bookinfo.getClick()!=dianji){//如果点击数发生改变，则进行修改
			str+=", click="+bookinfo.getClick()+"";
		}
		if(bookinfo.getRecommend()!=null){//如果有值则进行修改
			str+=",recommend="+bookinfo.getRecommend()+"";
		}
		if(bookinfo.getStatuse()!=null){//如果有状态值则进行修改
			str+=",status="+bookinfo.getStatuse()+"";
		}
//		if(authorinfostatus==1){//判断是否要修改作者信息
			
			str+=",authorinfo = empty_blob()";
//		}
//		if(bookinfostatus==1){//判断是否要修改图书信息
			
			str+=",bookinfo = empty_blob()";
//		}
//		if(directoryinfostatus==1){//判断是否要修改目录信息
			
			str+=",directoryinfo = empty_blob()";
//		}
		String sql = "update bookinfo set typeid=?,version=?,format=?,page=?,words=?,author=?," +
				"name=?,paper=?,package=?,readurl=?,press=?,pressdate=?,printdate=?,release=?,marketprice=?,vipprice=?," +
				"picture=?,upddate=sysdate "+str+"  where id=?";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			
			ps.setInt(1, bookinfo.getBookType().getId());
			ps.setInt(2, bookinfo.getVersion());
			ps.setInt(3, bookinfo.getFormat());
			ps.setInt(4, bookinfo.getPage());
			ps.setInt(5, bookinfo.getWords());
			ps.setString(6, bookinfo.getAuthor());
			ps.setString(7, bookinfo.getName());
			ps.setString(8, bookinfo.getPaper());
			ps.setString(9, bookinfo.getSpackage());
			ps.setString(10, bookinfo.getReadurl());
			ps.setString(11, bookinfo.getPress());
			ps.setTimestamp(12, bookinfo.getPressdate());
			ps.setTimestamp(13, bookinfo.getPrintdate());
			ps.setTimestamp(14, bookinfo.getRelease());
			ps.setFloat(15, bookinfo.getMarketprice());
			ps.setFloat(16, bookinfo.getVipprice());
			ps.setString(17, bookinfo.getPicture());
			ps.setInt(18, bookinfo.getId());
			ps.executeUpdate();
			
				OracleBlob setblob1 = new OracleBlob("bookinfo","id",bookinfo.getId()+"","authorinfo",bookinfo.getAuthorinfo(),"修改作者简介信息失败",ct);
				setblob1.updateContent(); 


				OracleBlob setblob2 = new OracleBlob("bookinfo","id",bookinfo.getId()+"","bookinfo",bookinfo.getBookinfo(),"修改图书简介信息失败",ct);
				setblob2.updateContent(); 


				OracleBlob setblob3 = new OracleBlob("bookinfo","id",bookinfo.getId()+"","directoryinfo",bookinfo.getDirectoryinfo(),"修改目录简介信息失败",ct);
				setblob3.updateContent(); 

		} catch (Exception e) {
			logger.error("修改图书详细信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
	}
	public  void  delebookinfo(int id,int status) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "update bookinfo set status="+status+"  where id=?";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		
		} catch (Exception e) {
			logger.error("修改图书状态信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	

	}
	/**
	 * 得到我的发布图书列表
	 * @param id
	 * @return
	 * @throws ElException 
	 */
	public List<Bookinfo>  bookinfoList(int id,Bookinfo b,Timestamp start,Timestamp end,
			BookTypeTree bookTypeTree,int btid,int pageNow, int pageSize) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str="";
		List<Bookinfo> listb= new  ArrayList<Bookinfo>();
		if(b!=null){
			if(b.getName()!=null){
				str+=" and bi.name like '%"+b.getName()+"%' ";
				
			}
			if(b.getRecommend()!=null){
				str+=" and bi.recommend ="+b.getRecommend()+" ";
				
			}
		}
		if(start!=null&&!"".equals(start)){
			str+=" and to_char(upddate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			
		}
		if(end!=null&&!"".equals(end)){
			str+=" and to_char(upddate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
		}
	
		String sql = "select * from (select t.*, rownum rn from (select  bi.name biname,bi.upddate,eu.realname,de.name dename ,bi.click,bi.id,bi.recommend,bi.status" +
				" from bookinfo bi , eluser eu,department de where eu.id=bi.userid and " +
				"eu.depid=de.id and bi.userid=? and bi.status !=3  and bi.typeid in ("+createPerTypeId(bookTypeTree,btid)+")  "+str+" )t where rownum <= ? ) where rn>=?";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				Bookinfo bi=new Bookinfo();
				bi.setName(rs.getString(1));
				bi.setUpddate(rs.getTimestamp(2));
				ELUser e =new ELUser();
				e.setRealname(rs.getString(3));
				bi.setDename(rs.getString(4));
				bi.setClick(rs.getInt(5));
				bi.setId(rs.getInt(6));
				bi.setRecommend(rs.getInt(7));
				bi.setUser(e);
				bi.setStatuse(rs.getInt(8));
				listb.add(bi);
			}
			return listb;
			
		} catch (Exception e) {
			logger.error("得到我的发布图书列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
		}
	public int  bookinfoListsize(int id,Bookinfo b,Timestamp start,Timestamp end,
			BookTypeTree bookTypeTree,int btid) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str="";
		if(b!=null){
			if(b.getName()!=null){
				str+=" and bi.name like '%"+b.getName()+"%' ";
				
			}
			if(b.getRecommend()!=null){
				str+=" and bi.recommend ="+b.getRecommend()+" ";
				
			}
		}
		if(start!=null&&!"".equals(start)){
			str+=" and to_char(upddate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			
		}
		if(end!=null&&!"".equals(end)){
			str+=" and to_char(upddate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
		}
		String sql = " select count(*) from( select bi.name " +
				" from bookinfo bi , eluser eu,department de where eu.id=bi.userid and " +
				"eu.depid=de.id and bi.userid=? and bi.status !=3 and bi.typeid in ("+createPerTypeId(bookTypeTree,btid)+") "+str+")";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			rs.next();
				
			
			return rs.getInt(1);
			
		} catch (Exception e) {
			logger.error("得到我的发布图书列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
		}
	public List<Bookinfo>  bookinfoAllList(Bookinfo b,Timestamp start,Timestamp end,
			BookTypeTree bookTypeTree,int btid,int pageNow, int pageSize) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str="";
		List<Bookinfo> listb= new  ArrayList<Bookinfo>();
		if(b!=null){
			if(b.getName()!=null){
				str+=" and bi.name like '%"+b.getName()+"%' ";
				
			}
			if(b.getUser()!=null&&b.getUser().getRealname()!=null){
				
				str+=" and eu.realname like '%"+b.getUser().getRealname()+"%'  ";
				
			}
			if(b.getRecommend()!=null){
				str+=" and bi.recommend ="+b.getRecommend()+"  ";
				
			}
		}
		if(start!=null&&!"".equals(start)){
			str+=" and to_char(upddate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			
		}
		if(end!=null&&!"".equals(end)){
			str+=" and to_char(upddate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
		}

		
		String sql = "select * from (select t.*, rownum rn from ( select bi.name biname,bi.upddate," +
				" eu.realname,de.name dename ,bi.click,bi.id,bi.recommend ,bi.status" +
				" from bookinfo bi , eluser eu,department de where eu.id=bi.userid and " +
				"eu.depid=de.id and bi.status !=3  and bi.typeid in ("+createPerTypeId(bookTypeTree,btid)+")  "+str+" )t where rownum <= ? ) where rn>=?";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				Bookinfo bi=new Bookinfo();
				bi.setName(rs.getString(1));
				bi.setUpddate(rs.getTimestamp(2));
				ELUser e =new ELUser();
				e.setRealname(rs.getString(3));
				bi.setDename(rs.getString(4));
				bi.setClick(rs.getInt(5));
				bi.setId(rs.getInt(6));
				bi.setRecommend(rs.getInt(7));
//				bi.setBookinfo(new OracleBlob().getContent(rs.getBlob(8)));
				bi.setStatuse(rs.getInt(8));
				bi.setUser(e);
				listb.add(bi);
			}
			return listb;
			
		} catch (Exception e) {
			logger.error("得到所有图书列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
	}
	public int  bookinfoAllListSize(Bookinfo b,Timestamp start,Timestamp end,
			BookTypeTree bookTypeTree,int btid) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str="";
		List<Bookinfo> listb= new  ArrayList<Bookinfo>();
		if(b!=null){
			if(b.getName()!=null){
				str+=" and bi.name like '%"+b.getName()+"%' ";
				
			}
			if(b.getUser()!=null&&b.getUser().getRealname()!=null){
				
				str+=" and eu.realname like '%"+b.getUser().getRealname()+"%'  ";
				
			}
			if(b.getRecommend()!=null){
				str+=" and bi.recommend ="+b.getRecommend()+"  ";
				
			}
		}
		if(start!=null&&!"".equals(start)){
			str+=" and to_char(upddate,'yyyy-MM-dd HH:mm:ss') > '"+start+"' ";
			
		}
		if(end!=null&&!"".equals(end)){
			str+=" and to_char(upddate,'yyyy-MM-dd HH:mm:ss') < '"+end+"' ";
		}
		
		String sql = "select count(*) from ( select bi.name " +
				" from bookinfo bi , eluser eu,department de where eu.id=bi.userid and " +
				"eu.depid=de.id and bi.status !=3  and bi.typeid in ("+createPerTypeId(bookTypeTree,btid)+") "+str+" )";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
				
			
			return rs.getInt(1);
			
		} catch (Exception e) {
			logger.error("得到所有图书列表大小失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
	}
	/**
	 * 前台图书中心列表
	 * @param b
	 * @param bookTypeTree
	 * @param btid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	
	public List<Bookinfo>  front_bookinfoAllList(Bookinfo b,BookTypeTree bookTypeTree,
			int btid,int pageNow, int pageSize) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str="";
		List<Bookinfo> listb= new  ArrayList<Bookinfo>();
		if(b!=null&&b.getName()!=null){
			str+=" and bi.name like '%"+b.getName()+"%' ";
			
		}
		String sql = "select * from (select t.*, rownum rn from ( select bi.name biname," +
				" bi.id,bi.bookinfo , " +
				" bi.author ,bi.press,bi.vipprice ,bi.picture  from bookinfo bi " +
				" where  bi.status =2  and bi.typeid in" +
				" ("+createPerTypeId(bookTypeTree,btid)+")  "+str+" order by" +
				" release  desc)t where rownum <= ? ) where rn>=?";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				Bookinfo bi=new Bookinfo();
				bi.setName(rs.getString(1));
				bi.setId(rs.getInt(2));
				bi.setBookinfo(new OracleBlob().getContent(rs.getBlob(3)));
				bi.setAuthor(rs.getString(4));
				bi.setPress(rs.getString(5));
				bi.setVipprice(rs.getFloat(6));
				bi.setPicture(rs.getString(7));
				listb.add(bi);
			}
			return listb;
			
		} catch (Exception e) {
			logger.error("课程中心得到所有图书列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
	}
	public void addclick(int id) throws ElException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		
		String sql = "update  bookinfo set click=(click+1) "+
				" where bookinfo.id=? " ;
				
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();


			
		} catch (Exception e) {
			logger.error("得到所有图书列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
		
	}
	public int  front_bookinfoAllListSize(Bookinfo b,BookTypeTree bookTypeTree,
			int btid) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String str="";

		if(b!=null&&b.getName()!=null){
			str+=" and bi.name like '%"+b.getName()+"%' ";
			
		}
		String sql = "select count(*) from ( select bi.name from bookinfo bi"+
				" where  bi.status =2  and bi.typeid in" +
				" ("+createPerTypeId(bookTypeTree,btid)+")  "+str+" )";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
			return rs.getInt(1);
			
		} catch (Exception e) {
			logger.error("得到所有图书列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
	}
	private BookTypeTree getCourseTypeById(List<BookTypeTree> listType,int ptid,BookTypeTree ptypeTree){
		BookTypeTree  productType=null;
		for(BookTypeTree type:listType){
			if(type.getId()!=ptid){
				productType = getCourseTypeById(type.getChild(),ptid,ptypeTree);
				if(productType!=null){
					return productType;
				}
			}else{
				return type;
			}
		}
		return productType;
	}
	private String createPerTypeId(BookTypeTree bookTypeTree, int ptid){
		if(bookTypeTree!=null){
			if(bookTypeTree.getId()!=ptid){
				bookTypeTree = getCourseTypeById(bookTypeTree.getChild(),ptid,bookTypeTree);
			}
			if(bookTypeTree!=null&&bookTypeTree.getChild()!=null){
				return createTypeId(bookTypeTree.getChild(),bookTypeTree.getId());
			}
			return String.valueOf(bookTypeTree!=null?bookTypeTree.getId():"0");
		}else{
			return null;
		}
	}
	private String createTypeId(List<BookTypeTree> listType,int id){
		String ids=id+"";
		for(BookTypeTree type:listType){
			ids=ids+","+createTypeId(type.getChild(),type.getId());
		}
		return ids;
	}
	public  void   tuijian(int  bookid,int value) throws ElException{
		

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "update  bookinfo  set recommend = ? where id = ?";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, value);
			ps.setInt(2, bookid);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改推荐信息失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
		
	}
	
	/**
	 * 前台图书推荐列表

	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	
	public List<Bookinfo>  front_bookinfotuijianList(int pageNow, int pageSize) throws ElException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Bookinfo> listb= new  ArrayList<Bookinfo>();
	
		String sql = "select * from (select t.*, rownum rn from ( select bi.name biname," +
				" bi.id,bi.bookinfo , " +
				" bi.author ,bi.press,bi.vipprice ,bi.picture  from bookinfo bi " +
				" where  bi.status =2  and recommend=2 order by" +
				" release  desc)t where rownum <= ? ) where rn>=?";
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()){
				Bookinfo bi=new Bookinfo();
				bi.setName(rs.getString(1));
				bi.setId(rs.getInt(2));
				bi.setBookinfo(new OracleBlob().getContent(rs.getBlob(3)));
				bi.setAuthor(rs.getString(4));
				bi.setPress(rs.getString(5));
				bi.setVipprice(rs.getFloat(6));
				bi.setPicture(rs.getString(7));
				listb.add(bi);
			}
			return listb;
			
		} catch (Exception e) {
			logger.error("首页得到推荐图书列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}	
		
	}
		
}



