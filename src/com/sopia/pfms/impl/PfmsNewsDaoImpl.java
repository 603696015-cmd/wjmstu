package com.sopia.pfms.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.OracleBlob;
import com.sopia.duman.entities.ELUser;
import com.sopia.newsandmess.entities.News;
import com.sopia.newsandmess.entities.NewsType;
import com.sopia.pfms.dao.PfmsNewsDao;

public class PfmsNewsDaoImpl implements PfmsNewsDao {
	private static final Log logger = LogFactory.getLog(PfmsNewsDaoImpl.class);
	
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
	
	private String createTypeId(List<NewsType> listType,int id){
		String ids=id+"";
		for(NewsType type:listType){
			ids=ids+","+createTypeId(type.getChild(),type.getId());
		}
		return ids;
	}
	
	public List<News> newsList(int userid, int nid, NewsType ntypeTree,
			News news, int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		List<News> newsList = new ArrayList<News>();
		
		try{
			ct = DBConnection.getConnection();
			if(news != null){
				if(news.getTitle() != null)
					if(!news.getTitle().equals(""))
						sqlAppend = sqlAppend + " and title like '%" + news.getTitle() + "%'";
				if(news.getNtype() != null)
					if(news.getNtype().getId() != 0)
							sqlAppend = sqlAppend + " and ntid = '" + news.getNtype().getId() + "'";
			}
			
			sql = " select b.*,rn from (select a.*,rownum rn from (select n.id,n.title,n.releasetime,n.ntid ,nt.name,n.hot,elu.realname,n.status_tow,n.content  " +
					" from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id and n.userid=? " +
					" and nt.id in ("+createPerTypeId(ntypeTree,nid)+")" + sqlAppend + 
					" order by n.releasetime desc " + 
							" ) a where rownum <=?) b where rn>=?";
				
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				news = new News(rs.getInt(1), rs.getString(2));
				news.setReleasetime(rs.getTimestamp(3));
				news.setNtype(new NewsType(rs.getInt(4), rs.getString(5)));
				news.setHot(rs.getInt(6));
				ELUser user= new ELUser();
				user.setRealname(rs.getString(7));
				news.setOwner(user);
				news.setStatus_tow(rs.getInt(8));
				news.setContent(new OracleBlob().getContent_list(rs.getBlob(9)));
				newsList.add(news);
			}
		}catch(Exception e){
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newsList;
	}

	public int newsListCount(int userid, int nid, NewsType ntypeTree, News news)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		String sqlAppend = "";
		int size = 0;
		
		try{
			ct = DBConnection.getConnection();
			if(news != null){
				if(news.getTitle() != null)
					if(!news.getTitle().equals(""))
						sqlAppend = sqlAppend + " and title like '%" + news.getTitle() + "%'";
				if(news.getNtype() != null)
					if(news.getNtype().getId() != 0)
							sqlAppend = sqlAppend + " and ntid = '" + news.getNtype().getId() + "'";
			}
			
			sql = " select count(*)  " +
					" from news n,newstype nt,ELUSER elu where nt.id = n.ntid  and n.userid=elu.id and n.userid=? " +
					" and nt.id in ("+createPerTypeId(ntypeTree,nid)+")" + sqlAppend + 
					" order by n.releasetime desc " ;
				
			ps = ct.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				size = rs.getInt(1);
			}
		}catch(Exception e){
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
	}
	
	public List<News> newsPersonerList(boolean is_show, int number) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		List<News> newsList = new ArrayList<News>();
		
		try{
			ct = DBConnection.getConnection();
			
			if(!is_show){
				return newsList;
			}
			sql = " select a.*,rownum rn from ( select n.id,n.title,n.ntid,n.releasetime,np.name " +
					" from news n  left join newstype np on n.ntid=np.id" +
					" where n.hot = 1 "+
					" order by n.releasetime desc " + 
							" ) a where rownum <=?";
				
			ps = ct.prepareStatement(sql);
			ps.setInt(1, number);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt("id"));
				news.setReleasetime(rs.getTimestamp("releasetime"));
				news.setTitle(rs.getString("title"));
				NewsType ntype = new NewsType();
				ntype.setName(rs.getString("name"));
				news.setNtype(ntype);
				newsList.add(news);
			}
		}catch(Exception e){
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return newsList;
	}
	
	public int newsPersonerCount()
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int size = 0;
		
		try{
			ct = DBConnection.getConnection();
			
			sql = " select count(*)  " +
					" from news n,newstype nt where nt.id = n.ntid  and n.hot = 1 " +
					" order by n.releasetime desc " ;
				
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				size = rs.getInt(1);
			}
		}catch(Exception e){
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
		}
	
	public int getNeedToShenheNewsCount()
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql = "";
		int size = 0;
		
		try{
			ct = DBConnection.getConnection();
			
			sql = " select count(*)  " +
					" from news n  where n.status_tow = 4 ";
				
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				size = rs.getInt(1);
			}
		}catch(Exception e){
			logger.error("我的新闻列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return size;
		}
	
}
