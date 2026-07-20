package com.sopia.pfms.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.CheckHtml;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.courseman.entities.CourseComment;
import com.sopia.duman.DUConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.entities.News;
import com.sopia.pfms.dao.PfmsFrontDao;
import com.sopia.pfms.entities.MessageBoard;
import com.sopia.pfms.entities.PfmsUser;
import com.sopia.pfms.entities.Product;
import com.sopia.pfms.entities.ProductType;

public class PfmsFrontDaoImpl implements PfmsFrontDao {
	private static final Log logger = LogFactory.getLog(PfmsFrontDaoImpl.class);

	public List<PfmsUser> userlist(Department department,int pageNow,int pageSize,PfmsUser pfmsUser) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<PfmsUser> pfmsUserList = new ArrayList<PfmsUser>();
		
		String sql = "";
		String sqlAppend = "";
		String note = "";
		try {
				
			ct = DBConnection.getConnection();
			
			if(pfmsUser != null && pfmsUser.getUser() != null && pfmsUser.getUser().getRealname() != null && !pfmsUser.getUser().getRealname().equals("")){
				sqlAppend = sqlAppend + " and eu.realname like  '%" + pfmsUser.getUser().getRealname() + "%'";
			}
			
			sql = "select b.*,rn from (" +
					" select a.*,rownum rn from (" +
					" select pfu.*,eu.username,eu.realname,dep.name,dep.id depid,dep.description from eluser eu  right join pfmsuser pfu on pfu.userid=eu.id inner join (" +
					((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", department, true)+ 
					" ) dep on eu.depid=dep.id "+ 
					" where pfu.is_qiye_huiyuan=1 and eu.valid=1 " + sqlAppend + 
					" order by id desc ) a " +
					" where rownum<=?) b " +
					" where rn>=?"; 
			ps = ct.prepareStatement(sql);
			System.out.println(sql);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				PfmsUser p = new PfmsUser();
				p.setId(rs.getInt("id"));
				p.setUserId(rs.getInt("userid"));
				p.setHuiyuandanwei(rs.getString("huiyuandanwei"));
				p.setHuiyuanleixing(rs.getString("huiyuanleixing"));
				p.setProvince_city_county(rs.getString("province_city_county"));
				p.setLogo(rs.getString("logo"));
				p.setHead(rs.getString("head"));
				
				ELUser elUser = new ELUser();
				Department dep = new Department(rs.getInt("depid"),rs.getString("name"));
				dep.setDescription(rs.getString("description"));
				elUser.setRealname(rs.getString("realname"));
				elUser.setDepartment(dep);
				 //过滤
				note = new OracleBlob().getContent(rs.getBlob("note"));
				
				note = CheckHtml.getString(note);
				p.setNote((note.length() > 21) ? note.substring(0, 19)+ "..." : note);
				
//				p.setNote(new OracleBlob().getContent(rs.getBlob("note")));
				p.setUser(elUser);
				pfmsUserList.add(p);
			}
		} catch (Exception e) {
			logger.error("我的会员列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pfmsUserList;
	}
	
	public int userCount(Department department,PfmsUser pfmsUser) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			
//			if(pfmsUser != null && pfmsUser.getUser() != null && pfmsUser.getUser().getDepartment() != null && pfmsUser.getUser().getDepartment().getName() != null && !pfmsUser.getUser().getDepartment().getName().equals("")){
//				sqlAppend = sqlAppend + " and dep.name like  '%" + pfmsUser.getUser().getDepartment().getName() + "%'";
//			}
			if(pfmsUser != null && pfmsUser.getUser() != null && pfmsUser.getUser().getRealname() != null && !pfmsUser.getUser().getRealname().equals("")){
				sqlAppend = sqlAppend + " and eu.realname like  '%" + pfmsUser.getUser().getRealname() + "%'";
			}
			
			sql = "select count(*) from pfmsuser pfu left join eluser eu  on pfu.userid=eu.id  join (" +
			((ElNodeSQL) SpringContextUtil.getBean("elnodesql")).generateSQLByTree("department", department, true)+
			" ) dep on eu.depid=dep.id "+
					" where  pfu.is_qiye_huiyuan=1 and eu.valid=1 " + sqlAppend;
			ps = ct.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public PfmsUser getUser(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		PfmsUser pfmsUser = new PfmsUser();
		try {
				
			ct = DBConnection.getConnection();
			sql = "select pfu.* from pfmsuser pfu left join eluser eu on pfu.userid=eu.id where pfu.userid=?";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				pfmsUser.setId(rs.getInt("id"));
				pfmsUser.setUserId(rs.getInt("userid"));
				pfmsUser.setHuiyuandanwei(rs.getString("huiyuandanwei"));
				pfmsUser.setNote(new OracleBlob().getContent(rs.getBlob("note")));
				pfmsUser.setHuiyuanleixing(rs.getString("huiyuanleixing"));
				pfmsUser.setProvince_city_county(rs.getString("province_city_county"));
			}
		} catch (Exception e) {
			logger.error("我的会员列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return pfmsUser;
	}


	public List<Product> productList(ElNode productLibTree,int pageNow, int pageSize, int userid ,Product product,ElNode department)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Product> productList = new ArrayList<Product>();
		
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			
			if(product != null){
				if(product.getName() != null)
					if(!product.getName().equals(""))
						sqlAppend = sqlAppend + " and  p.name like '%" +product.getName()+ "%' ";
			}
				
			
			sql = "select b.*,rn from (" +
					" select a.*,rownum rn from (" +
					" select p.*,pfu.province_city_county,d.id as did from product p ,eluser e,department d ,pfmsuser pfu " +
					
					" where  p.userid=e.id and e.id=pfu.userid and  e.depid=d.id and p.shenhezhuangtai=2  " + sqlAppend + 
					" order by p.fabushijian desc) a " + 
					" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("product_lanmu", productLibTree, true)+") clt on a.suoshulanmu = clt.id "+
					" join ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)
					+ ") dep on dep.id=a.did " + 
					" where rownum<=?) b " +
				  " where rn>=?"; 
			
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, userid);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			String jianjie = "";
			while (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setShichangjia(rs.getDouble("shichangjia"));
				product.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				product.setShuliang(rs.getInt("shuliang"));
				product.setProductCompanyName(rs.getString("productcompanyname"));
				product.setChanpintupian(rs.getString("chanpintupian"));
				
				//过滤
				jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
				jianjie = CheckHtml.getString(jianjie);
				product.setJianjie(jianjie.length()>=19?jianjie.substring(0, 19)+ "...":jianjie);
				//公司名称
				PfmsUser pfmsUser = new PfmsUser();
				pfmsUser.setProvince_city_county(rs.getString("province_city_county"));
				product.setPfmsUser(pfmsUser);
				productList.add(product);
			}
		} catch (Exception e) {
			logger.error("我的产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return productList;
	}
	
	public List<Product> productList(int pageNow, int pageSize, int userid,boolean is_tuijian,ElNode department)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Product> productList = new ArrayList<Product>();
		
		String sql = "";
		String jianjie = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from (" +
					" select a.*,rownum rn from (" +
					" select p.*,eu.province_city_county from product p,pfmsuser eu ,eluser e,department d " +
					" join ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)
					+ ") dep on dep.id=d.id " + 
					"where e.id=p.userid and e.depid=d.id and p.userid=eu.userid and p.shenhezhuangtai=2 and p.dianneituijian='店内推荐'  " +
					" order by p.fabushijian desc) a " +
					" where rownum<=?) b " +
				  " where rn>=?"; 
			
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, userid);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setShichangjia(rs.getDouble("shichangjia"));
				product.setHuiyuanjia(rs.getDouble("huiyuanjia"));
				product.setShuliang(rs.getInt("shuliang"));
				product.setProductCompanyName(rs.getString("productcompanyname"));
				product.setChanpintupian(rs.getString("chanpintupian"));
				//过滤
				jianjie = new OracleBlob().getContent(rs.getBlob("jianjie"));
				
				jianjie = CheckHtml.getString(jianjie);
				product.setJianjie((jianjie.length() > 21) ? jianjie.substring(0, 19)+ "..." : jianjie);
//				product.setJianjie(new OracleBlob().getContent(rs.getBlob("jianjie")));
				//公司名称
				PfmsUser pfmsUser = new PfmsUser();
				pfmsUser.setProvince_city_county(rs.getString("province_city_county"));
				product.setPfmsUser(pfmsUser);
				productList.add(product);
			}
		} catch (Exception e) {
			logger.error("我的产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return productList;
		}

	public int productListCount(ElNode productLibTree,int userId,Product product,ElNode department ) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			
			if(product != null){
				if(product.getName() != null)
					if(!product.getName().equals(""))
						sqlAppend = sqlAppend + " and  p.name like '%" +product.getName()+ "%' ";
			}
			
			sql = " select count(1) from (" +
			" select p.*,pfu.province_city_county,d.id as did from product p ,eluser e,department d ,pfmsuser pfu " +
			
			" where  p.userid=e.id and e.id=pfu.userid and  e.depid=d.id and p.shenhezhuangtai=2  " + sqlAppend + 
			" order by p.fabushijian desc) a " + 
			" inner join ("+((ElNodeSQL)SpringContextUtil.getBean("elnodesql")).generateSQLByTree("product_lanmu", productLibTree, true)+") clt on a.suoshulanmu = clt.id "+
			" join ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ ") dep on dep.id=a.did " ;
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, userId);
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的产品列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<News> newsList(int pageNow,int pageSize,int userid,ElNode department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newsList = new ArrayList<News>();
		
		String sql = "";
		String content = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select b.*,rn from (" +
					" select a.*,rownum rn from (" +
					" select n.* from news n,eluser e,department d " +
					" join ("
					+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
							.generateSQLByTree("department", department, true)
					+ ") dep on dep.id=d.id " + 
					" where n.userid=e.id and e.depid=d.id  " +
					" order by n.releasetime desc) a " +
					" where rownum<=?) b " +
				  " where rn>=?"; 
			
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, userid);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setTitle(rs.getString("title"));
				news.setReleasetime(rs.getTimestamp("releasetime"));
				news.setId(rs.getInt("id"));
				
				content = new OracleBlob().getContent(rs.getBlob("content"));
				content = CheckHtml.getString(content);
				news.setContent((content.length() > 21) ? content.substring(0, 19)+ "..." : content);
//				news.setContent(new OracleBlob().getContent(rs.getBlob("content")));
				newsList.add(news);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newsList;
	}

	public int newsListCount(int userid,ElNode department) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select count(*) from news n ,eluser e,department d " +
			" join ("
			+ ((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
					.generateSQLByTree("department", department, true)
			+ ") dep on dep.id=d.id " + 
			" where n.userid=e.id and e.depid=d.id  " ;
			ps = ct.prepareStatement(sql);
//			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public int messageBoardsCount(int shopid,int productId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int size = 0;
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			if(productId != 0)
				sqlAppend = sqlAppend + " and c.courseid = '" + productId +"'";
			sql = "select count(*) from course_comment c where c.shopid=?  " + sqlAppend;
			ps = ct.prepareStatement(sql);
			ps.setInt(1, shopid);
			
			rs = ps.executeQuery();
			if(rs.next())
				size = rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的留言板失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}

	public List<CourseComment> messageBoardsList(int shopid, int pageNow,
			int pageSize,int productId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<CourseComment> messageBoardsList = new ArrayList<CourseComment>();
		String sql = "";
		String sqlAppend = "";
		try {
			ct = DBConnection.getConnection();
			if(productId != 0)
				sqlAppend = sqlAppend + "  and c.courseid = '" + productId +"'";
			sql = "select b.*,rn from (" +
					" select a.*,rownum rn from (" +
					" select c.*,e.username,pu.head  from course_comment c " +
					" left join eluser e on c.userid=e.id " +
					" left join pfmsuser pu on c.userid=pu.userid " +
					" where c.shopid = ? " 
					+ sqlAppend + 
					" order by c.commentdate desc) a " +
					" where rownum<=?) b " +
				  " where rn>=?"; 
			
			ps = ct.prepareStatement(sql);
			ps.setInt(1, shopid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				CourseComment messageBoard = new CourseComment();
				messageBoard.setId(rs.getInt("id"));
				messageBoard.setContent(rs.getString("content"));
				messageBoard.setCommentdate(rs.getTimestamp("commentdate"));
				messageBoard.setCommentpoint(rs.getInt("commentpoint"));
				
				PfmsUser pfmsUser = new PfmsUser();
				pfmsUser.setHead(rs.getString("head"));
				ELUser elUser = new ELUser();
				elUser.setUsername(rs.getString("username"));
				pfmsUser.setUser(elUser);
				messageBoard.setPfmsUser(pfmsUser);
				messageBoardsList.add(messageBoard);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return messageBoardsList;
	}
	
	public float getAvgPoint(int productId) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		float point = 0;
		try {
				ct = DBConnection.getConnection();
				sql=" select round(avg(commentpoint),2) from course_comment where courseid = ?";
				ps=ct.prepareStatement(sql);
				ps.setInt(1, productId);
				rs=ps.executeQuery();
				if(rs.next())
					point = rs.getFloat(1);
		} catch (Exception e) {
			logger.error("获取平均商品平均分出错", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return point;
	}

	public Department getDepTree(int pid, int stopid, boolean containStop)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = null;
		try {
			if (pid == 0) {
				dep = getDepRootByCid();
			} else {
				dep = getDepById(pid);
			}
			ct = DBConnection.getConnection();
			dep.setChild(listdepChildsByPId(dep.getId()));
		} catch (Exception e) {
			logger.error("获取部门树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public Department getDepRootByCid() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_ROOT));
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setManager(new ELUser(rs.getInt(4), rs.getString(11)));
				dep.setParent(new Department(rs.getInt(5)));
				dep.setAddress(rs.getString(8));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
				dep.setBh(rs.getString(12));
				dep.setLid(rs.getInt(13));
				dep.setRid(rs.getInt(14));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public Department getDepById(int id) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Department dep = new Department();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(DUConstants.DEP_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
//				dep.setManager(new ELUser(rs.getInt(5), rs.getString(11)));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setAddress(rs.getString(5));
				dep.setPostalcode(rs.getString(6));
				dep.setPhone(rs.getString(7));
				dep.setFax(rs.getString(8));
				dep.setEmail(rs.getString(9));
				dep.setBh(rs.getString(10));
				dep.setLid(rs.getInt(11));
				dep.setRid(rs.getInt(12));
				dep.setClassCount(rs.getInt(13));
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	public List<Department> listdepChildsByPId(int parentid) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			// PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_BYPIDANDCID));
			ps = ct
					.prepareStatement("select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email,d.bh,count(c.id),d.lid,d.rid " +
							"from DEPARTMENT d left join department c on c.parentid = d.id where d.parentid = ? group by d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email,d.bh,d.lid,d.rid order by d.bh");
			ps.setInt(1, parentid);
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setManager(new ELUser(rs.getInt(5)));
				dep.setAddress(rs.getString(6));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
				dep.setBh(rs.getString(11));
				dep.setClassCount(rs.getInt(12));
				dep.setLid(rs.getInt(13));
				dep.setRid(rs.getInt(14));
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<Department> getAllDepTree(boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Department> deps = new ArrayList<Department>();
		try {
			ct = DBConnection.getConnection();
			// PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_BYPIDANDCID));
			ps = ct
					.prepareStatement("select d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email,d.bh,count(c.id),d.lid,d.rid " +
							"from DEPARTMENT d left join department c on c.parentid = d.id  group by d.id,d.name,d.description,d.parentid,d.manager,d.address,d.postalcode,d.phone,d.fax,d.email,d.bh,d.lid,d.rid order by d.bh");
			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = new Department(rs.getInt(1), rs.getString(2));
				dep.setDescription(rs.getString(3));
				dep.setParent(new Department(rs.getInt(4)));
				dep.setManager(new ELUser(rs.getInt(5)));
				dep.setAddress(rs.getString(6));
				dep.setPostalcode(rs.getString(7));
				dep.setPhone(rs.getString(8));
				dep.setFax(rs.getString(9));
				dep.setEmail(rs.getString(10));
				dep.setBh(rs.getString(11));
				dep.setClassCount(rs.getInt(12));
				dep.setLid(rs.getInt(13));
				dep.setRid(rs.getInt(14));
				deps.add(dep);
			}
		} catch (Exception e) {
			logger.error("查看部门信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return deps;
	}

	public List<ProductType> getAllPTypeTree(boolean containStop)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ProductType> ptypes = new ArrayList<ProductType>();
		try {
			ct = DBConnection.getConnection();
			// PreparedStatement pstemp = ct.prepareStatement(ElQuerySql
			// .getSQL(DUConstants.DEP_QUERY_BYPIDANDCID));
			ps = ct
					.prepareStatement("select * from product_lanmu ");
			rs = ps.executeQuery();
			while (rs.next()) {
				ProductType p = new ProductType(rs.getInt("id"), rs.getString("lanmu"));
				p.setDescription(rs.getString("description"));
				p.setParent(new ProductType(rs.getInt("parentid"),rs.getString("lanmu")));
				p.setLid(rs.getInt("lid"));
				p.setRid(rs.getInt("rid"));
				ptypes.add(p);
			}
		} catch (Exception e) {
			logger.error("查看栏目信息出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ptypes;
	}
}
