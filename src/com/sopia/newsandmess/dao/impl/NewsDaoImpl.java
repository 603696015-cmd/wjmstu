package com.sopia.newsandmess.dao.impl;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession; 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNode;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElQuerySql; 
import com.sopia.common.OracleBlob;
import com.sopia.common.SystemConfOp;
import com.sopia.common.logger.ElLogger;
import com.sopia.common.logger.ElLoggerConstants;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.Typelrid;
import com.sopia.newsandmess.NmConstants;
import com.sopia.newsandmess.dao.NewsDao;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsStyle;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.questionman.entities.StuffLib;

public class NewsDaoImpl extends ElNodeDao implements NewsDao {
	private static final Log logger = LogFactory.getLog(NewsDaoImpl.class);

	public void addNewstype(NewsType ntype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		List<NewsType> listType = new ArrayList<NewsType>();
		try {
			ct = DBConnection.getConnection();
//			addNode(ct, ntype, "newstype", "1=1");
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(NmConstants.NEWSTYPE_ADD));
			ps=ct.prepareStatement("insert into newstype(name,description,parentid,lid,rid,isshared) values(?,?,?,?,?,?)");
			ps.setString(1, ntype.getName());
			ps.setString(2, ntype.getDescription());
			ps.setInt(3, ntype.getParent().getId());
			ps.setInt(4, ntype.getLid());
			ps.setInt(5, ntype.getRid());
			ps.setInt(6, ntype.getIsshared());
			ps.executeUpdate();
			String sql="select id,name,parentid,isshared from newstype";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				NewsType type=new NewsType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			if(ntype.getIsshared()!=null&&ntype.getIsshared()==1){
				String ids = createSharedId(listType,ntype.getParent().getId(),"");
				if(ids!=null&&!"".equals(ids)){
					ps = ct.prepareStatement("update newstype set isshared=1 where id in ("+ids+")");
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("新闻公告栏目添加失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public int addNewstype2(NewsType ntype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		List<NewsType> listType = new ArrayList<NewsType>();
		int id = 0;
		try {
			ct = DBConnection.getConnection();
//			addNode(ct, ntype, "newstype", "1=1");
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(NmConstants.NEWSTYPE_ADD));
			ps=ct.prepareStatement("insert into newstype(name,description,parentid,lid,rid,isshared) values(?,?,?,?,?,?)");
			ps.setString(1, ntype.getName());
			ps.setString(2, ntype.getDescription());
			ps.setInt(3, ntype.getParent().getId());
			ps.setInt(4, ntype.getLid());
			ps.setInt(5, ntype.getRid());
			ps.setInt(6, ntype.getIsshared());
			ps.executeUpdate();
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("SELECT IDENT_CURRENT('newstype') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			} else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
						.prepareStatement("select newstype_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next()){
				ntype.setId(rs.getInt(1));
				id = rs.getInt(1);
			}
			String sql="select id,name,parentid,isshared from newstype";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				NewsType type=new NewsType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			if(ntype.getIsshared()!=null&&ntype.getIsshared()==1){
				String ids = createSharedId(listType,ntype.getParent().getId(),"");
				if(ids!=null&&!"".equals(ids)){
					ps = ct.prepareStatement("update newstype set isshared=1 where id in ("+ids+")");
					ps.executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("新闻公告栏目添加失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	}

	public void alterNewstype(NewsType ntype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			alterNode(ct, ntype, "newstype", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWSTYPE_ALTER));
			ps.setString(1, ntype.getName());
			ps.setString(2, ntype.getDescription());
			ps.setInt(3, ntype.getParent().getId());
			ps.setInt(4, ntype.getIsshared());
			ps.setInt(5, ntype.getId());
			ps.executeUpdate();
			
			if(ntype.getIsshared()!=null&&ntype.getIsshared()==1){
				updateParentShared(ntype.getId());
			}
			
		} catch (Exception e) {
			logger.error("新闻公告栏目修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 如果当前节点设置为共享节点 则把当前节点的所有父节点设置为共享节点
	 * @author jiahaijiang
	 * @throws ElException
	 */
	private void updateParentShared(int id)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<NewsType> listType = new ArrayList<NewsType>();
		try {
			ct = DBConnection.getConnection();
			String sql="select id,name,parentid,isshared from newstype";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				NewsType type=new NewsType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setParent(new ElNode(rs.getInt(3)));
				type.setIsshared(rs.getInt(4));
				listType.add(type);
			}
			String ids=null;
			for(NewsType type:listType){
			    if(type.getId()==id){
			    	ids=createSharedId(listType,type.getParent().getId(),"");
			    }	
			}
			if(ids!=null&&!"".equals(ids)){
				ps = ct.prepareStatement("update newstype set isshared=1 where id in ("+ids+")");
				ps.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("新闻公告栏目修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 找到所有父节点的ID
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private String createSharedId(List<NewsType> listType,int parentid,String ids)throws ElException {
		if(parentid==0){
			return ids;
		}
		if(!ids.equals("")){
			ids+=",";
		}
		for(NewsType type:listType){
			if(type.getId()==parentid){
				ids+=type.getId();
				return createSharedId(listType,type.getParent().getId(),ids);
			}
		}
		return "";
	}

	/**
	 * 删除新闻类别
	 */
	public void deleteNtype(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			NewsType ntype = getNtypeByid(id);
//			deleteNode(ct, ntype, "newstype", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWSTYPE_DELETE));
			ps.setInt(1, ntype.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除新闻类别失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 更新新闻库的父节点
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateNewstypeParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update newstype set parentid=? where parentid=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新新闻库的父节点出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新新闻的类别id
	 * @param pid
	 * @param npid
	 * @throws ElException
	 */
	public void updateNewsParentid(int pid, int npid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update news set ntid=? where ntid=? ");
			ps.setInt(1, npid);
			ps.setInt(2, pid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("更新新闻的类别id出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 删除新闻类别，包含下级资源
	 * @param id
	 * @throws ElException
	 */
	public void deleteNewsTypeAndSub(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			Typelrid typelrid=this.getLidRid(ct,id, "newstype");
			List<Integer> typelist=this.getTypeByLidRid(ct,typelrid.getLid(), typelrid.getRid(), "newstype");
			for (int i = 0; i < typelist.size(); i++) {
				//根据id删除类别以及类别下的资源(先删资源)
				this.deleteNewsByTypeid(ct,typelist.get(i));
				this.deleteNtype(typelist.get(i));
			}
		} catch (Exception e) {
			logger.error("删除新闻类别失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 根据类型删除新闻
	 * @param typeid
	 * @throws ElException
	 */
	public void deleteNewsByTypeid(Connection ct,int typeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Connection ct = null;
		try {
			//ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from news where ntid=?");
			ps.setInt(1, typeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("根据类型删除新闻出错！", e);
			throw new ElException(e);
		}
	}
	/**
	 * 获取树的左右id
	 * @param typeId
	 * @param tabName
	 * @return
	 * @throws ElException
	 */
	public Typelrid getLidRid(Connection ct,int typeId,String tabName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Connection ct = null;
		Typelrid type=null;
		try {
			//ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select lid,rid from "+tabName+" where id=?");
			ps.setInt(1, typeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				type=new Typelrid(rs.getInt(1),rs.getInt(2));
			}
		} catch (Exception e) {
			logger.error("获取树的左右id失败！", e);
			throw new ElException(e);
		}
		return type;
	}
	/**
	 * 根据左右id获取树的id集合
	 * @param lid
	 * @param rid
	 * @param tabName
	 * @return
	 * @throws ElException
	 */
	public List<Integer> getTypeByLidRid(Connection ct,int lid,int rid,String tabName) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		//Connection ct = null;
		List<Integer>  list=new ArrayList<Integer>();
		try {
			//ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id from "+tabName+" where lid>=? and rid<=? ");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			logger.error("获取树的左右id失败！", e);
			throw new ElException(e);
		}
		return list;
	}

	public NewsType getNtypeByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		NewsType nt = new NewsType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWSTYPE_QUERY_BYID));
			ps.setInt(1, id);
			System.out.println(ElQuerySql.getSQL(NmConstants.NEWSTYPE_QUERY_BYID));
			rs = ps.executeQuery();
			if (rs.next()) {
				nt.setId(rs.getInt(1));
				nt.setName(rs.getString(2));
				nt.setDescription(rs.getString(3));
				nt.setParent(new NewsType(rs.getInt(4), rs.getString(5)));
				nt.setIsshared(rs.getInt(6));
			}
		} catch (Exception e) {
			logger.error("获取栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nt;
	}

	public List<NewsType> getNtypesByPid(int pid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<NewsType> nts = new ArrayList<NewsType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id ,name,description,parentid from newstype where parentid = ?");
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			while (rs.next()) {
				NewsType nt = new NewsType();
				nt.setId(rs.getInt(1));
				nt.setName(rs.getString(2));
				nt.setDescription(rs.getString(3));
				nts.add(nt);
			}
		} catch (Exception e) {
			logger.error("获取栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nts;
	}

	public NewsType getNtypeRoot() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		NewsType nt = new NewsType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWSTYPE_QUERY_BYPARENTID));
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			if (rs.next()) {
				nt.setId(rs.getInt(1));
				nt.setName(rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("获取根栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nt;
	}

	public NewsType getNtypeTree(int from, int stop, boolean constop) 
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		NewsType ntype = null;
		try {
			if (from == 0) {
				ntype = getNtypeRoot();
			} else {
				ntype = getNtypeByid(from);
			}
			ct = DBConnection.getConnection();
			ntype.setChild(getNtChilds(ct, ntype.getId(), stop, constop, 0));
		} catch (Exception e) {
			logger.error("栏目树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ntype;
	}

	private List<NewsType> getNtChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<NewsType> deps = new ArrayList<NewsType>();
		PreparedStatement ps = ct.prepareStatement(ElQuerySql
				.getSQL(NmConstants.NEWSTYPE_QUERY_BYPARENTID));
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			NewsType dep = new NewsType(rstemp.getInt(1), rstemp.getString(2));
			// dep.setParent(new KnowledgeType(rstemp.getInt(4)));
			dep.setLevel(level);
			dep.setParent(new ElNode(from));
			if (dep.getId() != stop)
				dep.setChild(getNtChilds(ct, dep.getId(), stop, containStop,
						level));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		}
		ps.close();
		rstemp.close();
		return deps;
	}

	public void addNews(News news) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(NmConstants.NEWS_ADD));
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getContent());
			ps.setInt(3, news.getNtype().getId());
			ps.setInt(4, news.getOwner().getId());
			//ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(5,news.getReleasetime());
			ps.setString(6, news.getMainimg());
			ps.setInt(7, news.getHot());
			ps.setInt(8, news.getStatus());
			ps.executeUpdate();
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_NEWS, ElLoggerConstants.LOG_TYPE_ADD	, 
			"添加新闻公告失败。!");
			logger.error("添加新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 新闻增加附件
	 * @param addr
	 * @param Newsid
	 * @param title
	 * @throws ElException
	 */
	public void addKstuff(String addr, int Newsid, String title)
	throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("insert into news_stuff(stuffaddr,newsid,title) values(?,?,?)");
			ps.setString(1, addr);
			ps.setInt(2, Newsid);
			ps.setString(3, title);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 添加新闻公告(有第2状态)
	 * @param news
	 * @throws ElException
	 */
	public int addNews2(News news) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		String sql ;
		int id = 0;
		try { 
			sql = "insert into news(title,ntid,userid,releasetime,mainimg,hot,status,status_tow,content,nsid) values(?,?,?,?,?,?,?,?,empty_blob(),?)";
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement(sql);
			ps.setString(1, news.getTitle());
			ps.setInt(2, news.getNtype().getId());
			ps.setInt(3, news.getOwner().getId()); 
			ps.setTimestamp(4,news.getReleasetime());
			ps.setString(5, news.getMainimg());
			ps.setInt(6, news.getHot());
			ps.setInt(7, news.getStatus());
			ps.setInt(8, news.getStatus_tow()); 
			ps.setInt(9, news.getNstyle().getId());
			ps.executeUpdate(); 
 
			OracleBlob setblob = new OracleBlob(ct,"news_sequence","news","id","content",news.getContent(),"添加新闻失败");
			setblob.addContent(); 
			
			ps = ct.prepareStatement("select news_sequence.currval from dual ");
			rs = ps.executeQuery();  
			if (rs.next()){
				id =  rs.getInt(1); 
			}
		} catch (Exception e) {
			ElLogger.syslogger(getSessionValue(ElConstants.SESSION_USERID),
					ElLoggerConstants.LOG_MOD_NEWS, ElLoggerConstants.LOG_TYPE_ADD	, 
			"添加新闻公告失败。! 失败原因："+new ElException(e));
			logger.error("添加新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return id;
	} 
	public void alterNews(News news) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Statement st = null;  
		try {  
			ct = DBConnection.getConnection();
		    ct.setAutoCommit(false);      
		    String sql = "update  news set title = ? , content = empty_blob(),ntid=?,mainimg=?,hot= ?,releasetime=?,nsid=? where id = ? ";  
		    ps = ct.prepareStatement(sql);
			ps.setString(1, news.getTitle()); 
			ps.setInt(2, news.getNtype().getId());
			ps.setString(3, news.getMainimg());
			ps.setInt(4, news.getHot());
			ps.setTimestamp(5, news.getReleasetime());
			ps.setInt(6, news.getNstyle().getId());
			ps.setInt(7, news.getId());  
			ps.executeUpdate(); 
			
			OracleBlob setblob = new OracleBlob("news","id",news.getId()+"","content",news.getContent(),"修改新闻失败",ct);
			setblob.updateContent();   
		} catch (Exception e) {
			logger.error("修改新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteNews(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement(ElQuerySql
							.getSQL(NmConstants.NEWS_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 删除新闻附件
	 * @param id
	 * @throws ElException
	 */
	public void deleteNewsStuff(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from news_stuff where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除新闻附件", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public News getNewsById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		News news = null;   
		try {
			ct = DBConnection.getConnection();
			/*ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWS_QUERY_BYID));*/
			ps=ct.prepareStatement("select n.id, n.title,n.content,n.releasetime,n.ntid ,nt.name,n.mainimg,eu.realname,n.hot,n.browsefor,n.status_tow,n.astatus_tow from news n,newstype nt,eluser eu where eu.id = n.userid and nt.id = n.ntid and n.id =?");
			ps.setInt(1, id); 
			rs = ps.executeQuery();
			if (rs.next()) {
				news = new News(rs.getInt(1), rs.getString(2)); 
				news.setContent(new OracleBlob().getContent(rs.getBlob(3))); 
				news.setReleasetime(rs.getTimestamp(4));
				news.setMainimg(rs.getString(7));
				news.setNtype(new NewsType(rs.getInt(5), rs.getString(6)));
				news.setOwner(new ELUser(0, rs.getString(8)));
				news.setHot(rs.getInt(9));
				news.setBrowsefor(rs.getInt(10));
				news.setStatus_tow(rs.getInt("status_tow"));
				news.setAstatus_tow(rs.getInt("astatus_tow"));
			} 
		} catch (Exception e) {
			logger.error("修改新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return news;
	} 
	public List<News> getNewsByUid(int userid, int nid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWSTYPE_LRID));
			ps.setInt(1, nid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWS_QUERY_BYUID));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, userid);
			ps.setInt(4, pageNow);
			ps.setInt(5, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}

	public int getNewsCountByUid(int userid, int nid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWSTYPE_LRID));
			ps.setInt(1, nid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(NmConstants.NEWS_QUERY_SIZE_BYUID));
			ps.setInt(1, lid);
			ps.setInt(2, rid);

			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public NewsType getNtypeTreeByPerOrShar(int from, int stop,
			boolean constop, String userid, boolean isShared, String permtype)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		NewsType cltype = null;
		List<Integer> perTypeid=new ArrayList<Integer>();//当前用户存放有权限的新闻类型ID
		List<NewsType> typeList=new ArrayList<NewsType>();//所有新闻类型
		try {
			ct = DBConnection.getConnection();
			//当前用户有权限的id
			String perSql="select tr.ctypeid from eluser r,"+permtype+" tr where r.id=tr.userid and tr.userid in ("+userid+")";
			ps = ct.prepareStatement(perSql);
			rs=ps.executeQuery();
			while(rs.next()){
				perTypeid.add(rs.getInt(1));
			}
			//所有新闻类型
			String typeSql=" select id,name,description,parentid,isshared from newstype";
			ps = ct.prepareStatement(typeSql);
			rs=ps.executeQuery();
			while (rs.next()) {
				NewsType type = new NewsType();
				type.setId(rs.getInt(1));
				type.setName(rs.getString(2));
				type.setDescription(rs.getString(3));
				type.setParent(new ElNode(rs.getInt(4)));
				type.setIsshared(rs.getInt(5));
				typeList.add(type);
			}
			
			if (from == 0) {
				cltype = getCtypeRootByPerOrShar(from,perTypeid,typeList,isShared);
			} else {
				cltype = getCtypeByIdByPerOrShar(from,perTypeid,typeList,isShared);
			}
//			ct = DBConnection.getConnection();
//			cltype
//					.setChild(getChilds(ct, cltype.getId(), stop, containStop,
//							0));
		} catch (Exception e) {
			logger.error("新闻公告栏目树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
	/**
	 * 新闻公告栏目
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private NewsType getCtypeByIdByPerOrShar(int from,List<Integer> perTypeid,List<NewsType> typeList,boolean isShared)throws ElException{
		NewsType ctype = null;
		//查找根节点并且判断当前用户是否有权限
	    for(NewsType type:typeList){
	    	if(from==type.getId()){
	    		if(isPerOrShared(type.getId(),perTypeid)||(isShared&&type.getIsshared()==1)){
	    			ctype=type;
	    			ctype.setChild(findChildsType(type.getId(),perTypeid,typeList,isShared,0));
	    		}
	    	}
	    }
		return ctype;
	}
	/**
	 * 新闻公告栏目类型 根节点
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private NewsType getCtypeRootByPerOrShar(int from,List<Integer> perTypeid,List<NewsType> typeList,boolean isShared) throws ElException {
		NewsType ctype = null;
		boolean isPer=false;//是否有权限
		//查找根节点并且判断当前用户是否有权限
	    for(NewsType type:typeList){
	    	//parentid为0是根节点
	    	if(from==type.getParent().getId()){
	    		if(isPerOrShared(type.getId(),perTypeid)||(isShared&&type.getIsshared()==1)){
	    			ctype=type;
	    			ctype.setChild(findChildsType(type.getId(),perTypeid,typeList,isShared,0));
	    		}
    		}
	    }
		return ctype;
	}
	
	/**
	 * 递归 构建树节点
	 * @author jiahaijiang
	 * @param typeid
	 * @param perTypeid
	 * @param typeList
	 * @param isShared
	 * @return
	 */
	private List<NewsType> findChildsType(int typeid,List<Integer> perTypeid,List<NewsType> typeList,boolean isShared,int level){
		List<NewsType> ctypeList = new ArrayList<NewsType>();
		level++;
		for(NewsType type:typeList){
			//查找下级节点
			if(type.getParent().getId()==typeid){
				//如果有权限或是共享节点时
				if(isPerOrShared(type.getId(),perTypeid)||(isShared&&type.getIsshared()==1)){
					type.setLevel(level);
					type.setChild(findChildsType(type.getId(),perTypeid,typeList,isShared,level));
					ctypeList.add(type);
				}
			}
		}
		return ctypeList;
	}
	
	/**
	 * 判断是否有权限
	 * @author jiahaijiang
	 * @param typeid
	 * @param perTypeid
	 * @return
	 */
	private boolean isPerOrShared(int typeid,List<Integer> perTypeid){
		boolean isPerOrShared=false;
		for(Integer ptypeid:perTypeid){
			if(typeid==ptypeid){
				isPerOrShared=true;
			}
		}
		return isPerOrShared;
	}

	public List<News> getNewsByUidByPerOrShar(String userid, int nid,
			NewsType ntypeTree,Integer status,String title , int pageNow, int pageSize) throws ElException {//hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime," )
				  .append(" n.ntid ,nt.name,n.hot,elu.realname,n.status from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id" )
				  .append(" and nt.id in ("+createPerTypeId(ntypeTree,nid)+") ");
			if(status!=null){
				buffer.append(" and n.status="+status);
			}
			if(!title.equals("")){ 
				buffer.append(" and n.title like '%"+title+"%'");
			}
			buffer.append("  order by n.releasetime desc) t where rownum <=?)where rn>=?"); 
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				news.setOwner(user);
				news.setStatus(rs.getInt(8)); 
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	
	public List<News> getNewsByUidByPerOrShar(String userid, int nid,
			NewsType ntypeTree,Integer status, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime," )
				  .append(" n.ntid ,nt.name,n.hot,elu.realname,n.status,n.content  from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id" )
				  .append(" and nt.id in ("+createPerTypeId(ntypeTree,nid)+") ");
			if(status!=null){
				buffer.append(" and n.status="+status);
			} 
			buffer.append("  order by n.releasetime desc) t where rownum <=?)where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				news.setOwner(user);
				news.setStatus(rs.getInt(8));
				news.setContent(new OracleBlob().getContent_list(rs.getBlob(9)));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	
	/**
	 * 根据session用户查出他所能操作的新闻
	 * @param userid
	 * @param nid
	 * @param ntypeTree
	 * @param status
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<News> getNewsByUidByPerOrShar2(String userid, int nid,
			NewsType ntypeTree,Integer status, int pageNow, int pageSize,String displayStatus) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime," )
				  .append(" n.ntid ,nt.name,n.hot,elu.realname,n.status,n.content,n.status_tow,n.nsid,ns.name nsname  from news n,newstype nt,ELUSER elu,newsstyle ns where nt.id = n.ntid  and n.userid=elu.id and n.nsid=ns.id" )
				  .append(" and nt.id in ("+createPerTypeId(ntypeTree,nid)+") ");
			if(displayStatus!=null&&displayStatus.length()>0&&displayStatus.lastIndexOf(",")==displayStatus.length()-1){
				buffer.append(" and n.ispop=0 ");
				displayStatus=displayStatus.substring(0,displayStatus.length()-1);
			}
			if(displayStatus!=null&&!"".equals(displayStatus)){
				buffer.append(" and n.status_tow in ("+displayStatus+")");
			}
			buffer.append("  order by n.releasetime desc) t where rownum <=?)where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				news.setOwner(user);
				news.setStatus(rs.getInt(8));
				//news.setContent(new OracleBlob().getContent(rs.getBlob(9)));
				news.setStatus_tow(rs.getInt("status_tow"));
				NewsStyle nstyle = new NewsStyle();
				nstyle.setId(rs.getInt(11));
				nstyle.setName(rs.getString(12));
				news.setNstyle(nstyle);
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	
	public List<News> listFabuNewses(int status_tow,
			NewsType ntypeTree, int nid,int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime," )
				  .append(" n.ntid ,nt.name,n.hot,elu.realname,n.status,n.content,n.status_tow,n.nsid,ns.name nsname  from news n,newstype nt,ELUSER elu,newsstyle ns where nt.id = n.ntid  and n.userid=elu.id and n.nsid=ns.id" )
				  .append(" and nt.id in ("+createPerTypeId(ntypeTree,nid)+") ");
			buffer.append(" and n.status_tow = ?");
			buffer.append("  order by n.releasetime desc) t where rownum <=?)where rn>=?");
			ps = ct.prepareStatement(buffer.toString());
			ps.setInt(1, status_tow);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News(rs.getInt(1), rs.getString(2));
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				news.setOwner(user);
				news.setStatus(rs.getInt(8));
				news.setStatus_tow(rs.getInt("status_tow"));
				NewsStyle nstyle = new NewsStyle();
				nstyle.setId(rs.getInt(11));
				nstyle.setName(rs.getString(12));
				news.setNstyle(nstyle);
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	
//	/**
//	 * 获取弹窗新闻
//	 */
//	public News getNewsInPop() throws ElException{
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection ct = null;
//		News news = new News();
//		try {
//			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement("select n.id,n.title,n.releasetime,n.ntid ,nt.name,n.hot,elu.realname,n.status,n.content,n.status_tow from news n,newstype nt,ELUSER elu where nt.id = n.ntid and n.userid=elu.id and ispop=1 "); //"+createPerTypeId(ntypeTree,ntypeid)+"
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				String title=rs.getString("title");
//				if(title!=null&&title.length()>18){
//					title=title.substring(0,18)+"...";
//				}
//				news = new News(rs.getInt(1), title);
//				news.setReleasetime(rs.getTimestamp(3));
//				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
//				news.setHot(rs.getInt(6));
//				ELUser user= new ELUser();
//				user.setRealname(rs.getString(7));
//				news.setOwner(user);
//				news.setStatus(rs.getInt(8));
//				news.setContent(new OracleBlob().getContent_index(rs.getBlob(9)));
//				news.setStatus_tow(rs.getInt("status_tow"));
////				if(news.getContent().length()>100){
////					news.setContent(news.getContent().substring(0,100)+"...");
////				}
//			}
//		} catch (Exception e) {
//			logger.error("获取弹窗新闻失败", e);
//			throw new ElException(e);
//		} finally {
//			DBConnection.closeConnectInfo(ct, ps, rs);
//		}
//		return news;
//	}
	
	/**
	 * 获取弹窗新闻
	 */
	public News getNewsInPop(NewsType ntypeTree,int ntypeid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		News news = new News();
		NewsStyle nstyle = new NewsStyle();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select n.id,n.title,n.releasetime,n.ntid ,nt.name,n.hot,elu.realname,n.status,n.content,n.status_tow,n.nsid,ns.name nsname from news n,newstype nt,ELUSER elu,newsstyle ns where nt.id = n.ntid and n.userid=elu.id and n.nsid=ns.id and ispop=1 and nt.id in ("+createPerTypeId(ntypeTree,ntypeid)+")"); //"+createPerTypeId(ntypeTree,ntypeid)+"
			//ps.setObject(1,createPerTypeId(ntypeTree,ntypeid));
			rs = ps.executeQuery();
			if (rs.next()) {
				news = new News(rs.getInt(1), rs.getString(2));
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				news.setOwner(user);
				news.setStatus(rs.getInt(8));
				news.setContent(new OracleBlob().getContent(rs.getBlob(9)));
				news.setStatus_tow(rs.getInt("status_tow"));
				nstyle.setId(rs.getInt(11));
				nstyle.setName(rs.getString(12));
				news.setNstyle(nstyle);
			}
		} catch (Exception e) {
			logger.error("获取弹窗新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return news;
	}
	
	/**
	 * 取消弹窗新闻
	 * @throws ElException
	 */
	public void update_newsIspop() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update news set ispop=0 where ispop=1");
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("跟新新闻状态失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 获取所有信息栏目
	 * @return
	 * @throws ElException
	 */
	public List<NewsType> getAllNewsType() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<NewsType> newsTypeList = new ArrayList<NewsType>();
		NewsType newsType=null;
		try {
			ct = DBConnection.getConnection(); 
			
			ps = ct.prepareStatement("select id,name from newstype order by id");
			rs = ps.executeQuery();
			while (rs.next()) {
				newsType=new NewsType();
				newsType.setId(rs.getInt("id"));
				newsType.setName(rs.getString("name"));
				newsTypeList.add(newsType);
			}
		} catch (Exception e) {
			logger.error("获取所有信息栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newsTypeList;
	}
	/**
	 * 查询出从ctid开始的有权的新闻类型ID
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @param ctid
	 * @return
	 */
	private String createPerTypeId(NewsType ctypeTree, int ctid){
		if(ctypeTree!=null){
			if(ctypeTree.getId()!=ctid){
				ctypeTree = getCourseTypeById(ctypeTree.getChild(),ctid,ctypeTree);
			}
			if(ctypeTree!=null&&ctypeTree.getChild()!=null){
				return createTypeId(ctypeTree.getChild(),ctypeTree.getId());
			}
			return String.valueOf(ctypeTree!=null?ctypeTree.getId():"0");
		}else{
			return null;
		}
	}
	
	/**
	 * 构建有权的新闻类型ID
	 * @author jiahaijiang
	 * @param ctypeTree
	 * @return
	 */
	private String createTypeId(List<NewsType> listType,int id){
		String ids=id+"";
		for(NewsType type:listType){
			ids=ids+","+createTypeId(type.getChild(),type.getId());
		}
		return ids;
	}
	
	/**
	 * 如果不是跟节点开始 要找出开始节点
	 * @author jiahaijiang
	 * @param listType
	 * @param ctid
	 * @return
	 */
	private NewsType getCourseTypeById(List<NewsType> listType,int ctid,NewsType ctypeTree){
		NewsType  newsType=null;
		for(NewsType type:listType){
			if(type.getId()!=ctid){
				newsType = getCourseTypeById(type.getChild(),ctid,ctypeTree);
				if(newsType!=null){
					return newsType;
				}
			}else{
				return type;
			}
		}
		return newsType;
	}

	public int getNewsCountByUidByPerOrShar(String userid, int nid,
			NewsType ntypeTree,Integer status ,String title) throws ElException {//hwc
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select count(*) from(select n.id,n.title,n.releasetime," )
				  .append(" n.ntid ,nt.name,n.hot  from news n,newstype nt where nt.id = n.ntid  " )
				  .append(" and nt.id in ("+createPerTypeId(ntypeTree,nid)+")  ");
			if(status!=null){
				buffer.append(" and n.status="+status);
			}
			if(!title.equals("")){
				buffer.append(" and n.title like '%"+title+"%'");
			}
			buffer.append(" order by n.releasetime desc) t");
			ps = ct.prepareStatement(buffer.toString());
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public int getNewsCountByUidByPerOrShar(String userid, int nid,
			NewsType ntypeTree,Integer status ) throws ElException {
		/*PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select count(*) from(select n.id,n.title,n.releasetime," )
				  .append(" n.ntid ,nt.name,n.hot  from news n,newstype nt where nt.id = n.ntid  " )
				  .append(" and nt.id in ("+createPerTypeId(ntypeTree,nid)+")  ");
			if(status!=null){
				buffer.append(" and n.status="+status);
			} 
			buffer.append(" order by n.releasetime desc) t");
			ps = ct.prepareStatement(buffer.toString());
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}*/
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select count(*) from (select t.*,rownum rn from(select n.id,n.title,n.releasetime," )
				  .append(" n.ntid ,nt.name,n.hot,elu.realname,n.status  from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id" )
				  .append(" and nt.id in ("+createPerTypeId(ntypeTree,nid)+") ");
			if(status!=null){
				buffer.append(" and n.status="+status);
			} 
			buffer.append("  order by n.releasetime desc) t )");
			ps = ct.prepareStatement(buffer.toString());
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 根据条件获取新闻数量
	 * @param userid
	 * @param nid
	 * @param ntypeTree
	 * @param status
	 * @param displayStatus
	 * @return
	 * @throws ElException
	 */
	public int getNewsCountByUidByPerOrShar2(String userid, int nid,
			NewsType ntypeTree,Integer status,String displayStatus) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select count(*) from (select t.*,rownum rn from(select n.id,n.title,n.releasetime," )
				  .append(" n.ntid ,nt.name,n.hot,elu.realname,n.status,n.nsid,ns.name nsname  from news n,newstype nt,ELUSER elu,newsstyle ns where nt.id = n.ntid  and n.userid=elu.id and n.nsid=ns.id" )
				  .append(" and nt.id in ("+createPerTypeId(ntypeTree,nid)+") ");
			if(displayStatus!=null&&displayStatus.length()>0&&displayStatus.lastIndexOf(",")==displayStatus.length()-1){
				buffer.append(" and n.ispop=0");
				displayStatus=displayStatus.substring(0,displayStatus.length()-1);
			}
			if(displayStatus!=null&&!"".equals(displayStatus)){
				buffer.append(" and n.status_tow in ("+displayStatus+")");
			}
			buffer.append("  order by n.releasetime desc) t )");
			ps = ct.prepareStatement(buffer.toString());
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	/**
	 * 查询当前人员所在的二级部门的管理员
	 * @author jiahaijiang
	 * @param deptid
	 * @return
	 * @throws ElException
	 */
	public List<ELUser> findUserByMyDeptid(int deptid)throws ElException{
		List<Department> deptList = findAllDept();
		Department dept = findManagerDept(deptList,deptid);
		List<ELUser> userList = findManagerUser(dept.getId());
		return userList;
	}
	
	/**
	 * 查询出所有部门
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private List<Department> findAllDept()throws ElException{
		List<Department> list=new ArrayList<Department>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from DEPARTMENT");
			rs = ps.executeQuery();
			while(rs.next()){
				Department dept=new Department();
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setParent(new ElNode(rs.getInt("parentid")));
				list.add(dept);
			}
		} catch (Exception e) {
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	} 
	
	/**
	 * 找到当前用户所在的二级管理部门
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private Department findManagerDept(List<Department> list,int deptid)throws ElException{
		Department department=null;
		for(Department dept:list){
			//如果当前部门ID相等 且 父节点为1
			if(dept.getId()==deptid){
				if(dept.getParent().getId()==1){
					return dept;
				}else{
					department = findManagerDept(list,dept.getParent().getId());
					if(department!=null){
						return department;
					}
				}
			}
		}
		return department;
	}
	
	/**
	 * 查询当前人员所在的二级部门的用户ID
	 * @author jiahaijiang
	 * @return
	 * @throws ElException
	 */
	private List<ELUser> findManagerUser(int deptid)throws ElException{
		List<ELUser> list=new ArrayList<ELUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select elu.* from ELUSER elu,ELROLE elr where elu.role=elr.id and elr.name='部门管理员' and elu.depid=?");
			ps.setInt(1, deptid);
			rs = ps.executeQuery();
			while(rs.next()){
				ELUser user=new ELUser();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				list.add(user);
			}
		} catch (Exception e) {
			logger.error("查询用户出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}

	public void update_status(int newsid, int status) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update NEWS set status=? where id=?");
			ps.setInt(1, status);
			ps.setInt(2, newsid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("跟新新闻状态失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 更新新闻的第2状态
	 * @param newsid
	 * @param status_tow
	 * @throws ElException
	 */
	public void updateNewsStatus(int newsid, int status_tow) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update news set status_tow=? where id=?");
			ps.setInt(1, status_tow);
			ps.setInt(2, newsid);
			ps.executeUpdate();
			if(status_tow==6){
				//如果新闻状态为已发布，那么把显示状态改为3（前台页面显示需要）
				this.update_status(newsid, 3);
			}
		} catch (Exception e) {
			logger.error("更新新闻的第2状态失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 保存新闻当前状态
	 * @param newsid
	 * @param status_tow
	 * @throws ElException
	 */
	public void updateNewsAstatus(int newsid, int astatus_tow) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update news set astatus_tow=? where id=?");
			ps.setInt(1, astatus_tow);
			ps.setInt(2, newsid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("保存新闻当前状态失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	//修改新闻公告浏览数
	public void updateNewsBrowseforById(int newsid)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select browsefor from news where id = ?");
			ps.setInt(1, newsid); 
			rs = ps.executeQuery();
			int browsefor = 0;
			while(rs.next()){				
				browsefor = rs.getInt(1); 
			} 
			
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update NEWS set browsefor=? where id=?");
			ps.setInt(1, browsefor+1);
			ps.setInt(2, newsid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("修改新闻浏览数失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	//组合新闻搜索
	public List<News> listCombinationNew(int userid,NewsType ntypeTree,News news, int pageNow, int pageSize)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			if(news==null){
				news=new News();
			}
			if(news.getOwner()==null){
				ELUser eu=new ELUser();
				eu.setUsername("");
				eu.setRealname("");
				news.setOwner(eu);
			}else{
				if(news.getOwner().getUsername()==null){
					news.getOwner().setUsername("");
				}
			}
			String sqlstr="";
				sqlstr+="select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime, ";
				sqlstr+="n.ntid ,nt.name,n.hot,elu.realname,n.status from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id";
				sqlstr+=news.getNtype()==null?"":(news.getNtype().getId()==-1||news.getNtype().getId()==0)?"":" and nt.id in ("+createPerTypeId(ntypeTree,news.getNtype().getId())+")";
				sqlstr+=news.getOwner()==null?"":" and elu.username like '%"+news.getOwner().getUsername()+"%'";
				sqlstr+=news.getOwner()==null?"":" and elu.realname like '%"+news.getOwner().getRealname()+"%'";
				//sqlstr+=news.getNtype()==null?"":(news.getNtype().getId()==-1||news.getNtype().getId()==0)?"":" and nt.id="+news.getNtype().getId();
				sqlstr+=(news.getTitle()==null || news.getTitle().equals(""))?"":" and n.title like '%"+news.getTitle()+"%'";
				sqlstr+=(news.getBegintime()==null&&news.getEndtime()==null)?"":" and to_date(to_char(n.releasetime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+news.getBegintime()+"','yyyy-mm-dd') and to_date('"+news.getEndtime()+"','yyyy-mm-dd')";
				sqlstr+=" order by n.releasetime desc) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				News ns = new News(rs.getInt(1), rs.getString(2));
				ns.setReleasetime(rs.getTimestamp(3));
				ns.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				ns.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				ns.setOwner(user);
				ns.setStatus(rs.getInt(8)); 
				newses.add(ns);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	
	/**
	 * 搜索新闻信息
	 * @param userid
	 * @param ntypeTree
	 * @param news
	 * @param pageNow
	 * @param pageSize
	 * @param displayStatus
	 * @return
	 * @throws ElException
	 */
	public List<News> listCombinationNew2(int userid,NewsType ntypeTree,News news, int pageNow, int pageSize,String displayStatus)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		int newsstyle = 0;
		if(news.getNstyle()!=null){
			newsstyle = news.getNstyle().getId();
		}
		try {
			ct = DBConnection.getConnection();
			if(news==null){
				news=new News();
			}
			if(news.getOwner()==null){
				ELUser eu=new ELUser();
				eu.setUsername("");
				eu.setRealname("");
				news.setOwner(eu);
			}else{
				if(news.getOwner().getUsername()==null){
					news.getOwner().setUsername("");
				}
			}
			String sqlstr="";
				sqlstr+="select * from (select t.*,rownum rn from(select n.id,n.title,n.releasetime, ";
				sqlstr+="n.ntid ,nt.name,n.hot,elu.realname,n.status,n.status_tow,n.nsid,ns.name nsname from news n,newstype nt,ELUSER elu,newsstyle ns where nt.id = n.ntid  and n.userid=elu.id and n.nsid=ns.id ";
				if(news.getNstyle()!=null&&newsstyle!=0){
					sqlstr+="and n.nsid=?";
				}
				sqlstr+=" and n.hot= "+news.getHot()+" ";
				sqlstr+=news.getNtype()==null?"":(news.getNtype().getId()==-1||news.getNtype().getId()==0)?"":" and nt.id in ("+createPerTypeId(ntypeTree,news.getNtype().getId())+")";
				sqlstr+=news.getOwner()==null?"":" and elu.username like '%"+news.getOwner().getUsername()+"%'";
				sqlstr+=news.getOwner()==null?"":" and elu.realname like '%"+news.getOwner().getRealname()+"%'";
				//sqlstr+=news.getNtype()==null?"":(news.getNtype().getId()==-1||news.getNtype().getId()==0)?"":" and nt.id="+news.getNtype().getId();
				sqlstr+=(news.getTitle()==null || news.getTitle().equals(""))?"":" and n.title like '%"+news.getTitle()+"%'";
				sqlstr+=(news.getBegintime()==null&&news.getEndtime()==null)?"":" and to_date(to_char(n.releasetime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+news.getBegintime()+"','yyyy-mm-dd') and to_date('"+news.getEndtime()+"','yyyy-mm-dd')";
				if(displayStatus!=null&&displayStatus.length()>0&&displayStatus.lastIndexOf(",")==displayStatus.length()-1){
					sqlstr+=" and n.ispop=0 ";
					displayStatus=displayStatus.substring(0,displayStatus.length()-1);
				}
				if(displayStatus!=null&&!"".equals(displayStatus)){
					sqlstr+=" and n.status_tow in ("+displayStatus+")";
				}
				sqlstr+=" order by n.releasetime desc) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			if(news.getNstyle()!=null&&newsstyle!=0){
				ps.setInt(1, news.getNstyle().getId());
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				News ns = new News(rs.getInt(1), rs.getString(2));
				ns.setReleasetime(rs.getTimestamp(3));
				ns.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				ns.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				ns.setOwner(user);
				ns.setStatus(rs.getInt(8)); 
				ns.setStatus_tow(rs.getInt("status_tow"));
				NewsStyle nstyle = new NewsStyle();
				nstyle.setId(rs.getInt(10));
				nstyle.setName(rs.getString(11));
				ns.setNstyle(nstyle);
				newses.add(ns);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	
	public int listCombinationNewCount(int userid,NewsType ntypeTree,News news, int pageNow, int pageSize)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if(news==null){
				news=new News();
			}
			if(news.getOwner()==null){
				ELUser eu=new ELUser();
				eu.setUsername("");
				eu.setRealname("");
				news.setOwner(eu);
			}
			String sqlstr="";
				sqlstr+="select count(*) from (select t.*,rownum rn from(select n.id,n.title,n.releasetime, ";
				sqlstr+="n.ntid ,nt.name,n.hot,elu.realname,n.status,n.nsid from news n,newstype nt,ELUSER elu,newsstyle ns where nt.id = n.ntid  and n.userid=elu.id and ns.id=n.nsid and n.nsid=?";
				sqlstr+=news.getNtype()==null?"":(news.getNtype().getId()==-1||news.getNtype().getId()==0)?"":" and nt.id in ("+createPerTypeId(ntypeTree,news.getNtype().getId())+")";
				sqlstr+=news.getOwner()==null?"":" and elu.username like '%"+news.getOwner().getUsername()+"%'";
				sqlstr+=news.getOwner()==null?"":" and elu.realname like '%"+news.getOwner().getRealname()+"%'";
				//sqlstr+=news.getNtype()==null?"":(news.getNtype().getId()==-1||news.getNtype().getId()==0)?"":" and nt.id="+news.getNtype().getId();
				sqlstr+=(news.getTitle()==null || news.getTitle().equals(""))?"":" and n.title like '%"+news.getTitle()+"%'";
				sqlstr+=(news.getBegintime()==null&&news.getEndtime()==null)?"":" and to_date(to_char(n.releasetime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+news.getBegintime()+"','yyyy-mm-dd') and to_date('"+news.getEndtime()+"','yyyy-mm-dd')";
				sqlstr+=" order by n.releasetime desc)t)";
			ps = ct.prepareStatement(sqlstr);
			ps.setInt(1, news.getNstyle().getId());
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	
	/**
	 * 搜索新闻的条数
	 * @param userid
	 * @param ntypeTree
	 * @param news
	 * @param pageNow
	 * @param pageSize
	 * @param displayStatus
	 * @return
	 * @throws ElException
	 */
	public int listCombinationNewCount2(int userid,NewsType ntypeTree,News news,String displayStatus)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		int newsstyle = 0;
		if(news.getNstyle()!=null){
			newsstyle = news.getNstyle().getId();
		}
		try {
			ct = DBConnection.getConnection();
			if(news==null){
				news=new News();
			}
			if(news.getOwner()==null){
				ELUser eu=new ELUser();
				eu.setUsername("");
				eu.setRealname("");
				news.setOwner(eu);
			}
			String sqlstr="";
				sqlstr+="select count(*) from (select t.*,rownum rn from(select n.id,n.title,n.releasetime, ";
				sqlstr+="n.ntid ,nt.name,n.hot,elu.realname,n.status from news n,newstype nt,ELUSER elu,newsstyle ns where nt.id = n.ntid  and n.userid=elu.id and n.nsid=ns.id";
				if(news.getNstyle()!=null&&newsstyle!=0){
					sqlstr+=" and n.nsid=?";
				}
				sqlstr+=news.getNtype()==null?"":(news.getNtype().getId()==-1||news.getNtype().getId()==0)?"":" and nt.id in ("+createPerTypeId(ntypeTree,news.getNtype().getId())+")";
				sqlstr+=news.getOwner()==null?"":" and elu.username like '%"+news.getOwner().getUsername()+"%'";
				sqlstr+=news.getOwner()==null?"":" and elu.realname like '%"+news.getOwner().getRealname()+"%'";
				//sqlstr+=news.getNtype()==null?"":(news.getNtype().getId()==-1||news.getNtype().getId()==0)?"":" and nt.id="+news.getNtype().getId();
				sqlstr+=(news.getTitle()==null || news.getTitle().equals(""))?"":" and n.title like '%"+news.getTitle()+"%'";
				sqlstr+=(news.getBegintime()==null&&news.getEndtime()==null)?"":" and to_date(to_char(n.releasetime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+news.getBegintime()+"','yyyy-mm-dd') and to_date('"+news.getEndtime()+"','yyyy-mm-dd')";
				if(displayStatus!=null&&displayStatus.length()>0&&displayStatus.lastIndexOf(",")==displayStatus.length()-1){
					sqlstr+=" and n.ispop=0 ";
					displayStatus=displayStatus.substring(0,displayStatus.length()-1);
				}
				if(displayStatus!=null&&!"".equals(displayStatus)){
					sqlstr+=" and n.status_tow in ("+displayStatus+")";
				}
				sqlstr+=" order by n.releasetime desc)t)";
			ps = ct.prepareStatement(sqlstr);
			if(news.getNstyle()!=null&&newsstyle!=0){
				ps.setInt(1, newsstyle);
			}
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
		
	}
	/**
	 * 根据新闻id集合更新新闻的热度
	 * @param newIds
	 * @param hot
	 * @throws ElException
	 */
	public void updateNewsHot(String newIds,int hot) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update news set hot=? where id in("+newIds+")");
			//ps = ct.prepareStatement("update news set hot=? where id in(?)");
			ps.setInt(1, hot);
			//ps.setString(2, newIds);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("跟新新闻状态失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 删除新闻
	 * @param newIds
	 * @throws ElException
	 */
	public void delNews(String newIds) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from news where id in("+newIds+")");
			//ps.setString(2, newIds);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("删除新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 设置新闻弹窗
	 * @param newIds
	 * @throws ElException
	 */
	public void NewsSetpop(String newIds) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update news set ispop=0 where ispop=1");//先把原来的清掉
			ps.executeUpdate();
			ps = ct.prepareStatement("update news set ispop=1 where id=?");
			ps.setInt(1, Integer.parseInt(newIds));
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("设置新闻弹窗失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	public List<NewsType> listNewsType() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<NewsType> list=new ArrayList<NewsType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select id,name from newstype");
			rs=ps.executeQuery();
			while(rs.next()){
				NewsType newsType=new NewsType();
				newsType.setId(rs.getInt(1));
				newsType.setName(rs.getString(2));
				list.add(newsType);
			}
		} catch (Exception e) {
			logger.error("修改新闻浏览数失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return list;
	}
	
	//tree---
	//hwc1	 
	public NewsType getNtypeTree(int userid, String op, int stopid,	boolean containStop) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null; 
		Connection ct = null; 
//		NewsType dep = op.equals("op") ? new NewsType(-2, "可操作新闻库")
//				: new NewsType(-1, "可使用新闻库");
		NewsType dep = new NewsType(ElConstants.USER_OP_LIB, "可操作新闻库");
		dep.setLevel(0);
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select ctypeid from newstype_" +op+ "_type where userid = ?");  
			ps.setInt(1, userid);
			rs = ps.executeQuery();       
			List<NewsType> list = new ArrayList<NewsType>(); 
			while (rs.next()) {
				int newsid = rs.getInt(1);
				if (newsid == stopid && !containStop) {
				} else {
					NewsType news = getNtypeTree(newsid, stopid, containStop,1);
					news.setParent(dep);
					list.add(news); 
				}  
			}  
				dep.setChild(list);  	 
		} catch (Exception e) {
			logger.error("查看新闻库出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return dep;
	}
	
	//hwc2
	private NewsType getNtypeTree(int from, int stop, boolean containStop, int level) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		NewsType cltype = null;
		try {
			cltype = getNtypeByid(from);
			cltype.setLevel(level);
			ct = DBConnection.getConnection();
			cltype.setChild(getNtChilds(ct, cltype.getId(), stop, containStop,
					level));
		} catch (Exception e) {
			logger.error("新闻库树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return cltype;
	}
	//搜索资讯
	public List<News> SearchNews(News news,NewsType ntypeTree, int pageNow,int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();
			sqlstr+="select * from (select t.*, rownum rn from(select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where ";
			sqlstr+="nt.id = n.ntid and eu.id = n.userid ";
			sqlstr+=news==null?"":" and n.title like '%"+news.getTitle().trim()+"%'";
			sqlstr+=news==null?"":news.getNtype()==null?"":news.getNtype().getId()==0?"":" and nt.id in ("+createPerTypeId(ntypeTree,news.getNtype().getId())+") ";
			sqlstr+=" and n.status=3 order by n.releasetime desc) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				News ns = new News(rs.getInt(1), rs.getString(2));
				ns.setMainimg(rs.getString(3));
				ns.setOwner(new ELUser(0, rs.getString(4)));
				ns.setReleasetime(rs.getTimestamp(6));
				//ns.setContent(new OracleBlob().getContent(rs.getBlob(7)));
				ns.setContent(new OracleBlob().getContent_list(rs.getBlob(7)));
				newses.add(ns);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	public int SearchNewsCount(News news,NewsType ntypeTree, int pageNow,int pageSize) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();
			sqlstr+="select count(*) from (select t.*, rownum rn from(select n.id,n.title,n.mainimg,eu.realname,nt.name,n.releasetime,n.content from news n,newstype nt,eluser eu where ";
			sqlstr+="nt.id = n.ntid and eu.id = n.userid ";
			sqlstr+=news==null?"":" and n.title like '%"+news.getTitle().trim()+"%'";
			sqlstr+=news==null?"":news.getNtype()==null?"":news.getNtype().getId()==0?"":" and nt.id in ("+createPerTypeId(ntypeTree,news.getNtype().getId())+") ";
			sqlstr+=" and n.status=3 order by n.releasetime desc) t )";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("学员排行列表！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	} 
	public int getSessionValue(String key) {
		HttpServletRequest requset = ServletActionContext.getRequest();
		HttpSession session = requset.getSession();
		return  Integer.parseInt(session.getAttribute(key).toString());
	}
	/**
	 * 获取待审核的新闻数量
	 * @return
	 * @throws ElException
	 */
	public int getNewsEndCount() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from news where status_tow=4");
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			} 
		} catch (Exception e) {
			logger.error("获取待审核的新闻数量出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	

	public List<StuffLib> listKstuff(int newsid) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		List<StuffLib> stuffss = new ArrayList<StuffLib>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select cs.id,cs.title,cs.stuffaddr from news_stuff cs where cs.newsid = ?");
			ps.setInt(1, newsid);
			rs = ps.executeQuery();
			while (rs.next()) {
				StuffLib s = new StuffLib(rs.getInt(1), rs.getString(2));
				s.setDescription(rs.getString(3));
				// s.setFileext(rs.getString(3));
				// s.setType(rs.getInt(4));
				stuffss.add(s);

			}
		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return stuffss;
	}
	
	public void alterNstuff(String title, int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("update news_stuff set title=? where id = ?");
			ps.setString(1, title);
			ps.setInt(2, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public StuffLib getNStuffLib(int id) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		StuffLib s = new StuffLib();
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement("select cs.id,cs.title,cs.stuffaddr  from news_stuff cs where cs.id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				s = new StuffLib(rs.getInt(1), rs.getString(2));
				s.setDescription(rs.getString(3));

			}
		} catch (Exception e) {
			logger.error("获取网页中的sortid！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return s;
	}

	public void addNewsstyle(NewsStyle nstyle) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null; 
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("insert into newsstyle(name,description) values(?,?)");
			ps.setString(1, nstyle.getName());
			ps.setString(2, nstyle.getDescription());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("新闻类型添加失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterNewsstyle(NewsStyle nstyle) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update newsstyle set name=?,description=?  where id=?");
			ps.setString(1, nstyle.getName());
			ps.setString(2, nstyle.getDescription());
			ps.setInt(3, nstyle.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("新闻类型修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteNewsstyle(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from newsstyle where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("新闻类型删除失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<NewsStyle> getNstyles() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<NewsStyle> nss = new ArrayList<NewsStyle>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id ,name,description from newsstyle");
			rs = ps.executeQuery();
			while (rs.next()) {
				NewsStyle ns = new NewsStyle();
				ns.setId(rs.getInt(1));
				ns.setName(rs.getString(2));
				ns.setDescription(rs.getString(3));
				nss.add(ns);
			}
		} catch (Exception e) {
			logger.error("获取新闻类型失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nss;
	}

	public NewsStyle getNstyleByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		NewsStyle nstyle = new NewsStyle();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select name,description from newsstyle where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				nstyle.setId(id);
				nstyle.setName(rs.getString(1));
				nstyle.setDescription(rs.getString(2));
			}
		} catch (Exception e) {
			logger.error("获取新闻类型失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nstyle;
	}

	public List<News> getNewsByNum(int number) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> news = new ArrayList<News>();
		try {
			ct =  DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select * from news order by releasetime desc) where rownum<=?");
			ps.setInt(1, number);
			rs = ps.executeQuery();
			while(rs.next()){
				News n = new News();
				n.setTitle(rs.getString("title"));
				n.setId(rs.getInt("id"));
				n.setNtid(rs.getInt("ntid"));
				news.add(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return news;
	}

	public List<News> getNewsByids(int start, int end) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> news = new ArrayList<News>();
		try {
			ct =  DBConnection.getConnection();
			ps = ct.prepareStatement("select * from news where id>=? and id<=?");
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()){
				News n = new News();
				n.setTitle(rs.getString("title"));
				n.setId(rs.getInt("id"));
				n.setNtid(rs.getInt("ntid"));
				news.add(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return news;
	}

	public List<News> getNewsByNtypeId(int ntid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> news = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from news where ntid=?");
			ps.setInt(1, ntid);
			rs = ps.executeQuery();
			while(rs.next()){
				News n = new News();
				n.setTitle(rs.getString("title"));
				n.setId(rs.getInt("id"));
				n.setNtid(rs.getInt("ntid"));
				news.add(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return news;
	}
	/**
	 * 获取弹窗新闻
	 */
	public News getNewsInPop() throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		News news = new News();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select n.id,n.title,n.releasetime,n.ntid ,nt.name,n.hot,elu.realname,n.status,n.content,n.status_tow from news n,newstype nt,ELUSER elu where nt.id = n.ntid and n.userid=elu.id and ispop=1 "); //"+createPerTypeId(ntypeTree,ntypeid)+"
			rs = ps.executeQuery();
			if (rs.next()) {
				String title=rs.getString("title");
				if(title!=null&&title.length()>18){
					title=title.substring(0,18)+"...";
				}
				news = new News(rs.getInt(1), title);
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				news.setOwner(user);
				news.setStatus(rs.getInt(8));
				news.setContent(new OracleBlob().getContent_index(rs.getBlob(9)));
				news.setStatus_tow(rs.getInt("status_tow"));
//				if(news.getContent().length()>100){
//					news.setContent(news.getContent().substring(0,100)+"...");
//				}
			}
		} catch (Exception e) {
			logger.error("获取弹窗新闻失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return news;
	}

	public void updateNewsIsHtmlById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update news set ishtml=1 where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}

	public List<News> getNewsByIsHtml(int ishtml) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> listnews = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from news where ishtml=?");
			ps.setInt(1, ishtml);
			rs = ps.executeQuery();
			
			while(rs.next()){
				News news = new News();
				news.setId(rs.getInt("id"));
				news.setNtid(rs.getInt("ntid"));
				listnews.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return listnews;
	}

	public List<News> getAllNews() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> listnews = new ArrayList<News>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from news");
			rs = ps.executeQuery();
			while(rs.next()){
				News news = new News();
				news.setId(rs.getInt("id"));
				news.setNtid(rs.getInt("ntid"));
				listnews.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
		return listnews;
	}

	public List<News> listNews() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		News news = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.* ,rownum rn from " +
					"(select id,title from news where status_tow=6 order by releasetime desc ) a where rownum<=5 ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				news = new News(rs.getInt(1), rs.getString(2));
				newses.add(news);
			}
		} catch (Exception e) {
			logger.error("新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newses;
	}
	public List<News> getTjNews(int ntid, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		News news = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.*,rownum rn from (select * from news where ntid=? and hot=? order by releasetime desc) a where rownum<=8 ";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, ntid);
			ps.setInt(2, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				news = new News(rs.getInt(1),rs.getString(2));
				newses.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newses;
	}
	public List<News> getTjNews(int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<News> newses = new ArrayList<News>();
		News news = null;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.*,rownum rn from (select * from news where hot=? order by releasetime desc) a where rownum<=8";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				news = new News(rs.getInt(1),rs.getString(2));
				newses.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newses;
	}

}
