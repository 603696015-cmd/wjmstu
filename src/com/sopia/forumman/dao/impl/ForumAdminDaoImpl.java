package com.sopia.forumman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElQuerySql;
import com.sopia.common.OracleBlob;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;
import com.sopia.forumman.ForumConstants;
import com.sopia.forumman.dao.ForumAdminDao;
import com.sopia.forumman.entities.Forum;
import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.forumman.entities.Topic;

public class ForumAdminDaoImpl implements ForumAdminDao {
	private static final Log logger = LogFactory
	.getLog(ForumAdminDaoImpl.class);

	public List<ForumBlockType> listFbtypes() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ForumBlockType> fbts = new ArrayList<ForumBlockType>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_LIST));
			rs = ps.executeQuery();
			while (rs.next()) {
				ForumBlockType fbt = new ForumBlockType(rs.getInt(1), rs
						.getString(2));
				fbt.setDescription(rs.getString(3));
				fbt.setSortid(rs.getInt(4));
				fbts.add(fbt);
			}
		} catch (Exception e) {
			logger.error("论坛版块列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fbts;
	}
	public List<Forum> listShForums(String fbts, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select * from (select t.*, rownum rn from ( select fm.id ,fm.title fmtitle ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=0 "+fbts+" order by fm.createtime desc)t where rownum <= ? ) where rn>=?");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public int listShForumsCount(String fbts)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select count(*) from (select t.*, rownum rn from ( select fm.id ,fm.title fmtitle ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=0 "+fbts+" order by fm.createtime desc)t)");
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void addFbtype(ForumBlockType fbtype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 最大sortid
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_MAXSORTID));
			rs = ps.executeQuery();
			int maxsortid = 0;
			if (rs.next())
				maxsortid = rs.getInt(1);
			rs.close();
			ps.close();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_ADD));
			ps.setString(1, fbtype.getName());
			ps.setString(2, fbtype.getDescription());
			ps.setInt(3, maxsortid + 1);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块类别添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void sortFbtype(int id, int sorttype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {

			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			int sortid = 0;
			if (rs.next()) {
				sortid = rs.getInt(4);
			}
			rs.close();

			if (sorttype == ElConstants.SORT_DOWN) {
				downSortFbtype(ct, id, sortid);
			}
			if (sorttype == ElConstants.SORT_UP) {
				upSortFbtype(ct, id, sortid);
			}
		} catch (Exception e) {
			logger.error("论坛版块类别移动！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	private void upSortFbtype(Connection ct, int id, int sortid)
	throws ElException {
		try {
			PreparedStatement ps = null;
			if (sortid > 0) {
				String sql = "select id from forumblocktype where sortid = "
					+ (sortid - 1);
				ps = ct.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update forumblocktype set sortid=sortid-1 "
						+ " where id = " + id;
					ps = ct.prepareStatement(sql);
					ps.executeUpdate( );
					sql = "update forumblocktype set sortid=sortid+1 "
						+ " where id = " + nextId;
					ps = ct.prepareStatement(sql);
					ps.executeUpdate( );
				}
			}
		} catch (Exception e) {
			logger.error("论坛版块类别失败！", e);
			throw new ElException("论坛版块类别上移", e);
		}
	}

	private void downSortFbtype(Connection ct, int id, int sortid)
	throws ElException {
		try {
			PreparedStatement ps = null;

			String sql = "select max(sortid) from forumblocktype";
			ps = ct.prepareStatement(sql);
			ResultSet rs = ps.executeQuery( );
			int maxSortid = 0;
			if (rs.next()) {
				maxSortid = rs.getInt(1);
			}
			rs.close();
			if (sortid < maxSortid) {
				sql = "select id from forumblocktype where sortid = "
					+ (sortid + 1);
				ps = ct.prepareStatement(sql);
				rs = ps.executeQuery( );
				int nextId = 0;
				if (rs.next())
					nextId = rs.getInt(1);
				rs.close();
				if (nextId != 0) {
					sql = "update forumblocktype set sortid=sortid+1 "
						+ " where id = " + id;
					ps = ct.prepareStatement(sql);
					ps.executeUpdate( );
					sql = "update forumblocktype set sortid=sortid-1 "
						+ " where id = " + nextId;
					ps = ct.prepareStatement(sql);
					ps.executeUpdate( );
				}
			}
		} catch (Exception e) {
			logger.error("论坛版块类别失败！", e);
			throw new ElException("论坛版块类别下移", e);
		}
	}

	public void deleteFbtype(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			int sortid = 0;
			if (rs.next()) {
				sortid = rs.getInt(4);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_DELETE_SORT));
			ps.setInt(1, sortid);
			ps.executeUpdate();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
			// TODO 删除下面的版面
		} catch (Exception e) {
			logger.error("论坛版块类别添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterFbtype(ForumBlockType fbtype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 最大sortid
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_ALTER));
			ps.setString(1, fbtype.getName());
			ps.setString(2, fbtype.getDescription());
			ps.setInt(3, fbtype.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块类别添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ForumBlockType getFbtypeByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ForumBlockType fbt = new ForumBlockType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBTYPE_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				fbt = new ForumBlockType(rs.getInt(1), rs.getString(2));
				fbt.setDescription(rs.getString(3));
				fbt.setSortid(rs.getInt(4));
			}
		} catch (Exception e) {
			logger.error("论坛版块列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fbt;
	}

	public List<ForumBlock> listFbsByFbtid(int fbtid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ForumBlock> fbs = new ArrayList<ForumBlock>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBLOCK_BYFBTID));
			ps.setInt(1, fbtid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ForumBlock fb = new ForumBlock(rs.getInt(1), rs.getString(2));
				fb.setDescription(rs.getString(3));
				fb.setManager(new ELUser(rs.getInt(4), rs.getString(5)));
				fb.setSortid(rs.getInt(6));
				fbs.add(fb);
			}
		} catch (Exception e) {
			logger.error("论坛版块列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fbs;
	}
	public void deleteFblock(int fblock) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 最大sortid
			ps = ct.prepareStatement("delete from forumblock where id = ?");
			ps.setInt(1, fblock);
			ps.executeUpdate();
			ps = ct.prepareStatement("delete from forum where fblockid = ?");
			ps.setInt(1, fblock);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public void addFblock(ForumBlock fblock) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String[] ids = null;
		int id = 0;
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			// 最大sortid
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBLOCK_MAXSORTID_BYFBTID));
			ps.setInt(1, fblock.getFbtype().getId());
			rs = ps.executeQuery();
			int maxsortid = 0;
			if (rs.next())
				maxsortid = rs.getInt(1);
			rs.close();
			ps.close();

			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBLOCK_ADD));

			ps.setString(1, fblock.getTitle());
			ps.setString(2, fblock.getDescription());
			ps.setInt(3, fblock.getFbtype().getId());
			ps.setInt(4, fblock.getManager().getId());
			ps.setInt(5, fblock.getIsshared());
			ps.setInt(6, maxsortid + 1);
			ps.setString(7, fblock.getLuntanjibies());
			ps.executeUpdate();
			
			sql = "select FORUMBLOCK_SEQUENCE.Currval from dual ";
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			//绑定帖子id和会员级别id
			if(fblock.getLuntanjibies() != null && !fblock.getLuntanjibies().equals("")){
				ids = fblock.getLuntanjibies().split(",");
				for(int i=0;i<ids.length;i++){
					System.out.println(Integer.parseInt(ids[i].trim()));
					this.addFblock_huiyuanjibie(id,Integer.parseInt(ids[i].trim()));
				}
			}
			
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public ForumBlock getFblockById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		ForumBlock fb = new ForumBlock();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBLOCK_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				fb = new ForumBlock(rs.getInt(1), rs.getString(2));
				fb.setDescription(rs.getString(3));
				fb.setManager(new ELUser(rs.getInt(4), rs.getString(5)));
				fb.setSortid(rs.getInt(6));
				fb.setFbtype(new ForumBlockType(rs.getInt(7), rs.getString(8)));
				fb.setIsshared(rs.getInt(9));
				fb.setLuntanjibies(rs.getString(10));
			}
		} catch (Exception e) {
			logger.error("论坛版块列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fb;
	}

	public void alterFblock(ForumBlock fblock) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String[] ids = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_FBLOCK_ALTER));
			ps.setString(1, fblock.getTitle());
			ps.setString(2, fblock.getDescription());
			ps.setInt(3, fblock.getFbtype().getId());
			ps.setInt(4, fblock.getManager().getId());
			ps.setInt(5, fblock.getIsshared());
			ps.setString(6, fblock.getLuntanjibies());
			ps.setInt(7, fblock.getId());
			ps.executeUpdate();
			
			//删除之前绑定的数据
			this.deleteFblock_huiyuanjibie(fblock.getId());
			//绑定帖子id和会员级别id
			if(fblock.getLuntanjibies() != null && !fblock.getLuntanjibies().equals("")){
				ids = fblock.getLuntanjibies().split(",");
				for(int i=0;i<ids.length;i++){
					System.out.println(Integer.parseInt(ids[i].trim()));
					this.addFblock_huiyuanjibie(fblock.getId(),Integer.parseInt(ids[i].trim()));
				}
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Forum> listForumsByJh(int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYJH));

			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, ElConstants.HOT_TJ);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);

			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}

	public List<Forum> listForumsByRm(int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from (  select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,fm.jiajingtime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=1 and fm.hot = 1 order by fm.receipttime desc,fm.readtime desc,fm.id desc )t where rownum <=? ) where rn>=?  ");
			ps.setInt(1, pageSize);
			ps.setInt(2, pageNow);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("推荐交流文章获取失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public List<Forum> listForumsByRm(int pageNow, int pageSize,int depid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from (  select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,fm.jiajingtime,d.id depid from forum fm,eluser eu,forumblock bl,department d where fm.creater=eu.id and fm.fblockid = bl.id  and eu.depid=d.id and depid=? order by fm.receipttime desc,fm.readtime desc,fm.id desc )t where rownum <=? ) where rn>=?  ");
			ps.setInt(1, depid);
			ps.setInt(2, pageSize);
			ps.setInt(3, pageNow);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("推荐交流文章获取失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}

	public void addForum(Forum forum) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql ="";
		try {
			sql ="insert into forum (title,description,createtime,creater,fblockid,valid) values(?,empty_blob(),?,?,?,?)";
			ct = DBConnection.getConnection(); 
			ps = ct.prepareStatement(sql);
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(ForumConstants.FORUM_ADD)); 
			ps.setString(1, forum.getTitle()); 
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, forum.getCreater().getId());
			ps.setInt(4, forum.getFblock().getId());
			ps.setBoolean(5, forum.getValid());
			ps.executeUpdate();
			
			OracleBlob setblob = new OracleBlob(ct,"forum_sequence","forum","id","description",forum.getDescription(),"论坛添加失败");
			setblob.addContent(); 
			
			if ("mssql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct.prepareStatement("SELECT IDENT_CURRENT('forum') AS id");
				rs = ps.executeQuery();

			} else if ("mysql".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				rs = ps.getGeneratedKeys();
			}else if ("oracle".equals(SystemConfOp
					.getValue(ElConstants.SYSTEM_CONF_DATABASE_TYPE))) {
				ps = ct
				.prepareStatement("select forum_sequence.currval from dual ");
				rs = ps.executeQuery();
			}else{
				logger.error("数据库配置有误,请确认是否为oracle,mysql或者sqlserver数据库。");
				throw new ElException("数据库配置有误！！！");
			}
			if (rs.next())
				forum.setId(rs.getInt(1));
		} catch (Exception e) {
			logger.error("论坛添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 论坛帖子修改
	 */
	public void alterForum(Forum forum) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			// 最大sortid
			ps = ct.prepareStatement("update forum set title=?,description=empty_blob(),fblockid= ? where id = ?");
			ps.setString(1, forum.getTitle());
			//ps.setString(2, forum.getDescription());
			ps.setInt(2, forum.getFblock().getId());
			ps.setInt(3, forum.getId());
			ps.executeUpdate();
			OracleBlob setblob = new OracleBlob("forum","id",forum.getId()+"","description",forum.getDescription(),"修改帖子失败",ct);
			setblob.updateContent();
		} catch (Exception e) {
			logger.error("论坛帖子修改失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Forum> listForumsByZx(int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYZX));

			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}

	public List<Forum> listForumsByBid(int bid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYBID));
			ps.setInt(1, bid);
			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public List<Forum> listForumsByBid(int bid,String title, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			/*ps = ct.prepareStatement(ElQuerySql
			.getSQL(ForumConstants.FORUM_LIST_BYBID));*/
			String sqlstr="select * from (select t.*, rownum rn from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id="+bid+" and fm.title like '%"+title+"%' and  fm.valid=1 order by fm.createtime)t where rownum <= "+pageNow+" ) where rn>="+pageSize;
			ps = ct.prepareStatement(sqlstr);
			/*ps.setInt(1, bid);
			ps.setString(2, "'%"+title+"%'");
			//ps.setString(2, )
			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);*/
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	
	public List<Forum> listForumsByBid_list(int bid,String title, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			/*ps = ct.prepareStatement(ElQuerySql
			.getSQL(ForumConstants.FORUM_LIST_BYBID));*/
			String sqlstr="select * from (select t.*, rownum rn from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id="+bid+" and fm.title like '%"+title+"%' and  fm.valid=1 order by fm.createtime desc)t where rownum <= "+pageNow+" ) where rn>="+pageSize;
			ps = ct.prepareStatement(sqlstr);
			/*ps.setInt(1, bid);
			ps.setString(2, "'%"+title+"%'");
			//ps.setString(2, )
			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);*/
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				//f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public int listForumsByBidCount(int bid,String title)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			/*ps = ct.prepareStatement(ElQuerySql
			.getSQL(ForumConstants.FORUM_LIST_BYBID));*/
			ps = ct.prepareStatement("select count(*) from (select t.*, rownum rn from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id=? and fm.title like ? and  fm.valid=1 order by fm.createtime) t )");
			ps.setInt(1, bid);
			ps.setString(2, "%"+title+"%");
			//ps.setString(2, )
			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			/*ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);*/
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public int listForumsByBidSize(int bid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYBID_SIZE));
			ps.setInt(1, bid);

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Forum> listForumsByJhBid(int bid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYJH_BYBID));

			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, bid);
			ps.setInt(2, ElConstants.HOT_TJ);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	//最新的精华帖子
	public List<Forum> listForumsByJhBid2(int bid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,forumblock bl,eluser eu where fm.fblockid=bl.id and fm.fblockid=? and fm.hot=? and fm.creater=eu.id order by fm.createtime desc");
			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, bid);
			ps.setInt(2, ElConstants.HOT_TJ);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	
	//最新的精华帖子
	public List<Forum> listForumsByJhBid2_list(int bid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,forumblock bl,eluser eu where fm.fblockid=bl.id and fm.fblockid=? and fm.hot=? and fm.creater=eu.id order by fm.createtime desc");
			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, bid);
			ps.setInt(2, ElConstants.HOT_TJ);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				//f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}

	public List<Forum> listForumsByRmBid(int bid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYRM_BYBID));

			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, bid);
			ps.setInt(3, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	
	public List<Forum> listForumsByRmBid_list(int bid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYRM_BYBID));

			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, bid);
			ps.setInt(3, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				//f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}

	public Forum getForumsByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Forum f = new Forum();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				ELUser eu = new ELUser(rs.getInt(6), rs.getString(7));
				eu.setDepartment(new Department(rs.getInt(13)));
				f.setCreater(eu);
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return f;
	}

	public List<Topic> listTopicByFid(int fid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Topic> tps = new ArrayList<Topic>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_TOPIC_BYFID));
			ps.setInt(1, fid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Topic tp = new Topic(rs.getInt(1), rs.getString(2));
				tp.setCreatetime(rs.getTimestamp(3));
				tp.setCreater(new ELUser(rs.getInt(4), rs.getString(5)));
				tps.add(tp);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tps;
	}
	
	/**
	 * 根据帖子获取所有回复(已审核的)
	 * @param fid
	 * @param pageNow
	 * @param pageSize
	 * @param disValid
	 * @return
	 * @throws ElException
	 */
	public List<Topic> listTopicByFid(int fid, int pageNow, int pageSize,int disValid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Topic> tps = new ArrayList<Topic>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select * from (select t.*, rownum rn from ( select tp.id,tp.content,tp.createtime,tp.creater,eu.realname from ftopic tp,eluser eu where eu.id = tp.creater and tp.forumid = ? and tp.disvalid=? order by tp.createtime desc  )t where rownum <= ? ) where rn>=?");
			ps.setInt(1, fid);
			ps.setInt(2, disValid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Topic tp = new Topic(rs.getInt(1), rs.getString(2));
				tp.setCreatetime(rs.getTimestamp(3));
				tp.setCreater(new ELUser(rs.getInt(4), rs.getString(5)));
				tps.add(tp);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tps;
	}

	public int listTopicByIdSize(int fid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_TOPIC_BYFID_SIZE));
			ps.setInt(1, fid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 根据帖子获取所有回复数量(已审核的)
	 * @param fid
	 * @param disvalid
	 * @return
	 * @throws ElException
	 */
	public int listTopicByIdSize(int fid,int disvalid) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from ftopic tp,eluser eu where eu.id = tp.creater and tp.forumid = ? and disvalid=?");
			ps.setInt(1, fid);
			ps.setInt(2, disvalid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 更新回帖的状态
	 * @param newsid
	 * @param status_tow
	 * @throws ElException
	 */
	public void upTopicValid(int topicId, int valid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sql="";
			if(valid==1){
				//如果新闻状态为已发布，那么把显示状态改为3（前台页面显示需要）
				sql="update ftopic set valid=?,disvalid=1 where id=?";
			}else{
				sql="update ftopic set valid=? where id=?";
			}
			ps = ct.prepareStatement(sql);
			ps.setInt(1, valid);
			ps.setInt(2, topicId);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("更新回帖的状态失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 查询我的所有回帖
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Topic> myListTopic(int userid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Topic> tps = new ArrayList<Topic>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select tp.id,tp.content,tp.createtime,tp.creater,eu.realname,fr.id frid,fr.title frtitle,tp.valid from ftopic tp left join eluser eu on eu.id = tp.creater left join forum fr on tp.forumid=fr.id where eu.id=? order by tp.createtime desc ) t where rownum <= ? ) where rn>=? ");
			ps.setInt(1, userid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			String tpContent="";
			while (rs.next()) {
				Topic tp = new Topic();
				tp.setId(rs.getInt(1));
				tpContent=rs.getString(2);
				if(tpContent.length()>15){
					tpContent=tpContent.substring(0,15)+"...";
				}
				tp.setContent(tpContent);
				tp.setCreatetime(rs.getTimestamp(3));
				tp.setCreater(new ELUser(rs.getInt(4), rs.getString(5)));
				tp.setForum(new Forum(rs.getInt("frid"),rs.getString("frtitle")));
				tp.setValid(rs.getInt("valid"));
				tps.add(tp);
			}
		} catch (Exception e) {
			logger.error("查询我的所有回帖失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tps;
	}
	
	/**
	 * 查询我的所有回帖数量
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int myListTopicCount(int userid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from ftopic tp left join eluser eu on eu.id = tp.creater left join forum fr on tp.forumid=fr.id where eu.id=? order by tp.createtime desc");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询我的所有回帖失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	/**
	 * 查询所有回帖(已审核,左树右表)
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Topic> listTopic(String fbt, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Topic> tps = new ArrayList<Topic>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select tp.id,tp.content,tp.createtime,tp.creater,eu.realname,fr.id frid,fr.title frtitle,tp.valid,fr.creater frcreaterid from ftopic tp left join eluser eu on eu.id = tp.creater left join forum fr on tp.forumid=fr.id where fr.valid=1 and fr.fblockid in("+fbt+") order by tp.createtime desc ) t where rownum <= ? ) where rn>=? ");
			//ps.setString(1, fbt);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			String tpContent="";
			while (rs.next()) {
				Topic tp = new Topic();
				Forum forum = new Forum();
				tp.setId(rs.getInt(1));
				tpContent=StringUtil.htmlParse_(rs.getString(2));
				if(tpContent.length()>15){
					tpContent=tpContent.substring(0,15)+"...";
				}
				tp.setContent(tpContent);
				tp.setCreatetime(rs.getTimestamp(3));
				tp.setCreater(new ELUser(rs.getInt(4), rs.getString(5)));
				forum.setCreater(new ELUser(rs.getInt("frcreaterid")));
				forum.setId(rs.getInt("frid"));
				forum.setTitle(rs.getString("frtitle"));
			//	tp.setForum(new Forum(rs.getInt("frid"),rs.getString("frtitle")));
				tp.setForum(forum);
				tp.setValid(rs.getInt("valid"));
				tps.add(tp);
			}
		} catch (Exception e) {
			logger.error("查询所有回帖(已审核,左树右表)失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tps;
	}
	
	/**
	 * 查询所有回帖数量(已审核,左树右表)
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int listTopicCount(String fbt) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from ftopic tp left join eluser eu on eu.id = tp.creater left join forum fr on tp.forumid=fr.id where fr.valid=1 and fr.fblockid in("+fbt+") order by tp.createtime desc");
			//ps.setInt(1, userid);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询我的所有回帖失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void addTopic(Topic topic) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_TOPIC_ADD));
			ps.setString(1, topic.getContent());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, topic.getCreater().getId());
			ps.setInt(4, topic.getForum().getId());
			//valid默认为0，所以在此不处理
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 添加帖子回复（不用户审核）
	 * @param topic
	 * @throws ElException
	 */
	public void addTopic2(Topic topic) throws ElException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into ftopic(content, createtime,creater,forumid,valid,disvalid) values(?,?,?,?,1,1)");
			ps.setString(1, topic.getContent());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, topic.getCreater().getId());
			ps.setInt(4, topic.getForum().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<Forum> listForumsByManager(int userid, String title, int bid,
			int pageNow, int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			if (bid == 0) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(ForumConstants.FORUM_LIST_BYMANAGER_ALL));
				title = (title == null) ? "" : title.trim();
				ps.setInt(1, userid);
				ps.setString(2, "%" + title + "%");
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(ForumConstants.FORUM_LIST_BYMANAGER));
				title = (title == null) ? "" : title.trim();
				ps.setInt(1, bid);
				ps.setInt(2, userid);
				ps.setString(3, "%" + title + "%");
				ps.setInt(4, pageNow);
				ps.setInt(5, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}

	public int listForumsByManagerSize(int userid, String title, int bid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (bid == 0) {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(ForumConstants.FORUM_LIST_BYMANAGER_ALL_SIZE));
				title = (title == null) ? "" : title.trim();
				ps.setInt(1, userid);
				ps.setString(2, "%" + title + "%");
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(ForumConstants.FORUM_LIST_BYMANAGER_SIZE));
				title = (title == null) ? "" : title.trim();
				ps.setInt(1, bid);
				ps.setInt(2, userid);
				ps.setString(3, "%" + title + "%");
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void forumJhSet(int fid) throws ElException {//jiajing
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update forum set hot = ?,jiajingtime = ? where id = ?");
			ps.setInt(1, ElConstants.HOT_TJ);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setInt(3, fid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void forumDelete(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();
			// TODO 删除回帖
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void readtimeAdd(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_READTIME_ADD));
			ps.setInt(1, id);
			ps.executeUpdate();
			// TODO 删除回帖
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void receipttimeAdd(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_RECEIPTTIME_ADD));
			ps.setInt(1, id);
			ps.executeUpdate();
			// TODO 删除回帖
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	/**
	 * 帖子的回复数减1
	 * @param id
	 * @throws ElException
	 */
	public void receipttimeDel1(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" update forum set receipttime =receipttime-1 where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			// TODO 删除回帖
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	
	/**
	 * 根据id获取回贴信息
	 * @param id
	 * @return
	 * @throws ElException
	 */
	public Topic getTopicById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Topic topic = new Topic();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select fp.id fpid,fp.valid fpvalid,fp.disvalid fpdisvalid,fr.id frid,fr.title frtitle from ftopic fp left join forum fr on fp.forumid=fr.id where fp.id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				topic.setId(rs.getInt("fpid"));
				topic.setValid(rs.getInt("fpvalid"));
				topic.setDisvalid(rs.getInt("fpdisvalid"));
				topic.setForum(new Forum(rs.getInt("frid"),rs.getString("frtitle")));
			}
		} catch (Exception e) {
			logger.error("根据id获取回贴信息！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return topic;
	}

	public List<Forum> listForumsByUid(int userid, String title, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYUID));
			title = (title == null) ? "" : title.trim();
			ps.setInt(1, userid);
			ps.setString(2, "%" + title + "%");
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}

	public int listForumsByUidSize(int userid, String title) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYUID_SIZE));
			title = (title == null) ? "" : title.trim();
			ps.setInt(1, userid);
			ps.setString(2, "%" + title + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public int listShForumsSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement(" select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id  and fm.valid= 0");
//			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	public int listShForumsSize(int userid,int fblockid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement(" select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.valid= 0");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public List<Forum> listShForums(int userid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
//			ps = ct.prepareStatement(ElQuerySql
//					.getSQL(ForumConstants.FORUM_SH_LIST));
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select fm.id ,fm.title fmtitle ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=0  order by fm.createtime desc)t where rownum <= ? ) where rn>=?");
//			ps.setInt(1, userid);
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public List<Forum> listShForums(int userid,int fblockid, int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select fm.id ,fm.title fmtitle ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=0 and fm.fblockid=? order by fm.createtime desc)t where rownum <= ? ) where rn>=?");
//			ps.setInt(1, userid);
			ps.setInt(1,fblockid);
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public int listShForumsCount(int userid,int fblockid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from (select t.*, rownum rn from ( select fm.id ,fm.title fmtitle ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=0 and fm.fblockid=? order by fm.createtime desc)t )");
//			ps.setInt(1, userid);
			ps.setInt(1,fblockid);
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public void shForumset(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update forum set valid= 1 where id =?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteTopic(int topicid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from ftopic where id =?");
			ps.setInt(1, topicid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void receipttimeDelete(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
			.prepareStatement(" update forum set receipttime =receipttime-1 where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	/**
	 * 获取使用权限的用户
	 */
	public List<ELUser> getOpUsers(String type, int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ELUser> us = new ArrayList<ELUser>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select eu.id,eu.realname,eu.username from "+type+" du left join eluser eu on eu.id = du.userid where du.fblockid = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				ELUser user = new ELUser(rs.getInt(1), rs.getString(2));
				if (user.getRealname() == null || "".equals(user.getRealname()))
					user.setRealname(rs.getString(3));
				us.add(user);
			}
		} catch (Exception e) {
			logger.error("查询论坛可使用权限的用户出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return us;
	}

	public void addOpusers(String type, int userid, int ctypeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("insert into " + type
					+ "(userid,fblockid) values(?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, ctypeid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加版面权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

	}

	public boolean checkOpUsers(String type, int userid, int ctypeid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from " + type
					+ " where userid = ? and fblockid = ?");
			ps.setInt(1, userid);
			ps.setInt(2, ctypeid);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (Exception e) {
			logger.error("查询版面权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return false;
	}

	public void deleteOpusers(String optype, int userId, int fblockId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from " + optype
					+ " where userid = ? and fblockid = ?");
			ps.setInt(1, userId);
			ps.setInt(2, fblockId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("版面管理可使用人员删除失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}


	}
	/**
	 * 删除用户所有版块权限
	 * @param optype
	 * @param userId
	 * @throws ElException
	 */
	public void deleteOpusers(String optype, int userId) throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("delete from " + optype
					+ " where userid = ?");
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("版面管理可使用人员删除失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public List<ForumBlock> fblockByPerOrShare(int fbtid, int userid, boolean isShared) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<ForumBlock> fbs = new ArrayList<ForumBlock>();
		try {
			ct = DBConnection.getConnection();
			if(isShared){
				ps = ct.prepareStatement(ElQuerySql.getSQL(ForumConstants.FORUM_FBLOCK_BYFBTID_AND_PER_OR_SHA));
//				ps = ct.prepareStatement("select   fb.id, fb.title, fb.description, fb.manager, eu.realname, fb.sortid from forumblock fb left join eluser eu on eu.id = fb.manager");
			}else{
				ps = ct.prepareStatement(ElQuerySql.getSQL(ForumConstants.FORUM_FBLOCK_BYFBTID_AND_PER));
			}
			ps.setInt(1, fbtid);
			ps.setInt(2, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ForumBlock fb = new ForumBlock(rs.getInt(1), rs.getString(2));
				fb.setDescription(rs.getString(3));
				fb.setManager(new ELUser(rs.getInt(4), rs.getString(5)));
				fb.setSortid(rs.getInt(6));
				fbs.add(fb);
			}
		} catch (Exception e) {
			logger.error("论坛版块列表失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fbs;
	}

	/**
	 * 获取有权限的id列表
	 * @return
	 */
	public String getFblockIds(int userid, boolean isshared)  throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String ids = "";
		try {
			ct = DBConnection.getConnection();
			if(isshared){
				ps = ct.prepareStatement("select id from forumblock fb where fb.id in (select fblockid from FBLOCK_USE_TYPE where userid = ? ) or fb.isshared = 1");
			}else{
				ps = ct.prepareStatement("select id from forumblock fb where fb.id in (select fblockid from FBLOCK_USE_TYPE where userid = ? ) ");
			}

			ps.setInt(1, userid);
			rs = ps.executeQuery();

			while(rs.next()){
				int id = rs.getInt(1);
				if(!ids.equals("")){
					ids+=",";
				}
				ids += id;
			}

		} catch (Exception e) {
			logger.error("查询版面权限出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}

		if("".equals(ids)){
			return null;
		}
		return ids;
	}

	public List<Forum> listForumsByManagerPer(int userid, String title, int bid,int role ,
			int pageNow, int pageSize, boolean isshared) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			if (bid == 0) {//第一次进来  = 0 
				if(isshared == true){//
					String ids = getFblockIds(userid, isshared);//获取模块ID
					ps = ct.prepareStatement("select * from (select t.*, rownum rn from  ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.title like ? and fm.fblockid in ("+ ids +") order by fm.createtime )t where rownum <= ? ) where rn>=?");
					title = (title == null) ? "" : title.trim();
					ps.setInt(1, userid);
					ps.setString(2, "%" + title + "%");
					ps.setInt(3, pageNow);
					ps.setInt(4, pageSize);
				}else{
					if(role == 1){//超级管理员
						ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.title like ? order by fm.createtime )t where rownum <= ? ) where rn>=?");
						title = (title == null) ? "" : title.trim(); 
						ps.setString(1, "%" + title + "%"); 
						ps.setInt(2, pageNow);
						ps.setInt(3, pageSize);
					}else{
						ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.title like ? and fm.fblockid in (select fblockid from fblock_use_type where userid = ? ) order by fm.createtime )t where rownum <= ? ) where rn>=?");
						title = (title == null) ? "" : title.trim(); 
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, userid);
						ps.setInt(3, pageNow);
						ps.setInt(4, pageSize);
					}
				}
			}else{
				ps = ct.prepareStatement("select * from (select t.*, rownum rn from  ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.fblockid = ? and fm.title like ? order by fm.createtime )t where rownum <= ? ) where rn>=?");//ElQuerySql.getSQL(ForumConstants.FORUM_LIST_BYMANAGER)
				title = (title == null) ? "" : title.trim();
				ps.setInt(1, bid); 
				ps.setString(2, "%" + title + "%");
				ps.setInt(3, pageNow);
				ps.setInt(4, pageSize);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块帖子列表获取失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}

	public int listForumsByManagerPerSize(int userid, String title, int bid,int role , boolean isshared)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			if (bid == 0) {
				if(isshared == true){
					String ids = getFblockIds(userid, isshared);
					ps = ct.prepareStatement("select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.manager= ? and fm.title like ? and fm.fblockid in ("+ids+")");
					title = (title == null) ? "" : title.trim();
					ps.setInt(1, userid);
					ps.setString(2, "%" + title + "%");
				}else{
					if(role == 1){//超级管理员
						ps = ct.prepareStatement(" select count(fm.id) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id  and fm.title like ?  ");
						title = (title == null) ? "" : title.trim();
						ps.setString(1, "%" + title + "%");
					}else{
						ps = ct.prepareStatement(" select count(fm.id) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id  and fm.title like ? and fm.fblockid in (select fblockid from fblock_use_type where userid = ? ) ");
	//					ps = ct.prepareStatement(ElQuerySql.getSQL(ForumConstants.FORUM_LIST_BYMANAGER_ALL_SIZE));
						title = (title == null) ? "" : title.trim();
						ps.setString(1, "%" + title + "%");
						ps.setInt(2, userid);
					}
				}
			} else {
				ps = ct.prepareStatement(ElQuerySql
						.getSQL(ForumConstants.FORUM_LIST_BYMANAGER_SIZE));
				title = (title == null) ? "" : title.trim();
				ps.setInt(1, bid);
				ps.setInt(2, userid);
				ps.setString(3, "%" + title + "%");
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	//本版最新的帖子
	public List<Forum> listForumsByZx2(int pageNow, int pageSize)
	throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		int i=0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=1 order by fm.createtime desc");

			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime

			rs = ps.executeQuery();
			while (rs.next()) {
				if(i>pageSize){
					break;
				}
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				//f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));//论坛列表页不需要
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
				i++;
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	//最新的精华帖子
	public List<Forum> listForumsByJh2(int pageNow, int pageSize)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		int i=0;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.hot=? and  fm.valid=1 order by fm.createtime desc");

			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, ElConstants.HOT_TJ);


			rs = ps.executeQuery();
			while (rs.next()) {
				if(i>pageSize){
					break;
				}
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				//f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));//论坛列表页不需要
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
				i++;
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;

	}
	//搜索帖子
	public List<Forum> searchlistForums(int pageNow, int pageSize,String title,String str) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			/*ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYBID));*/
			ps=ct.prepareStatement("select * from (select t.*, rownum rn from(select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=1 and fm.title like ? order by fm.createtime) t where rownum <= ? ) where rn>=?");
			ps.setString(1, "%"+title+"%");
			ps.setInt(2, pageNow);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public int searchlistForumsSize(String title) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select count(*) from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=1 and fm.title like ?) t");
			ps.setString(1, "%"+title+"%");

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public List<Forum> searchlistForumsByJh(int pageNow, int pageSize)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from(select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.hot=? and  fm.valid=1 order by fm.createtime ) t where rownum <= ? ) where rn>=?");

			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(1, ElConstants.HOT_TJ);
			ps.setInt(3, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public List<Forum> searchlistForumsByRm(int pageNow, int pageSize)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from(select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and  fm.valid=1 order by readtime desc) t where rownum <= ? ) where rn>=?");
			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(2, pageNow);
			ps.setInt(1, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	//论坛组合搜索
	public int listCombinationForumCount(Forum forum,int pageNow,int pageSize)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			String sqlstr="select count(*) from (select t.*, rownum rn from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl,forumblocktype blt ";
			sqlstr+=" where fm.creater=eu.id and fm.fblockid = bl.id and bl.fbtid=blt.id and";
			sqlstr+=forum.getCreater()==null?"":" eu.username like '%"+forum.getCreater().getUsername()+"%'";
			sqlstr+=forum.getCreater()==null?"":" and eu.realname like '%"+forum.getCreater().getRealname()+"%'";
			//sqlstr+=forum.getFblock()==null?"":(forum.getFblock().getFbtype().getId()==-1||forum.getFblock().getFbtype().getId()==0)?"":" and blt.id="+forum.getFblock().getFbtype().getId();
			sqlstr+=forum.getFblock()==null?"":forum.getFblock().getId()==0?(forum.getFblock().getFbtype().getId()==-1||forum.getFblock().getFbtype().getId()==0)?"":" and blt.id="+forum.getFblock().getFbtype().getId():" and bl.id="+forum.getFblock().getId();
			sqlstr+=(forum.getTitle()==null || forum.getTitle().equals(""))?"":" and fm.title like '%"+forum.getTitle()+"%'";
			sqlstr+=(forum.getBegintime()==null&&forum.getEndtime()==null)?"":" and to_date(to_char(fm.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+forum.getBegintime()+"','yyyy-mm-dd') and to_date('"+forum.getEndtime()+"','yyyy-mm-dd')";
			sqlstr+=" order by fm.createtime) t)";
			ps = ct.prepareStatement(sqlstr);
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
	public List<Forum> listCombinationForum(Forum forum,int pageNow,int pageSize)throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			String sqlstr="select * from (select t.*, rownum rn from ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl,forumblocktype blt ";
			sqlstr+=" where fm.creater=eu.id and fm.fblockid = bl.id and bl.fbtid=blt.id and";
			sqlstr+=forum.getCreater()==null?"":" eu.username like '%"+forum.getCreater().getUsername()+"%'";
			sqlstr+=forum.getCreater()==null?"":" and eu.realname like '%"+forum.getCreater().getRealname()+"%'";
			sqlstr+=forum.getFblock()==null?"":forum.getFblock().getId()==0?(forum.getFblock().getFbtype().getId()==-1||forum.getFblock().getFbtype().getId()==0)?"":" and blt.id="+forum.getFblock().getFbtype().getId():" and bl.id="+forum.getFblock().getId();
			sqlstr+=(forum.getTitle()==null || forum.getTitle().equals(""))?"":" and fm.title like '%"+forum.getTitle()+"%'";
			sqlstr+=(forum.getBegintime()==null&&forum.getEndtime()==null)?"":" and to_date(to_char(fm.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+forum.getBegintime()+"','yyyy-mm-dd') and to_date('"+forum.getEndtime()+"','yyyy-mm-dd')";
			sqlstr+=" order by fm.createtime) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps = ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	//论坛类型搜索
	public List<ForumBlockType> getForum()throws ElException{
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<ForumBlockType> list=new ArrayList<ForumBlockType>();
		try{
			ct=DBConnection.getConnection();
			ps=ct.prepareStatement("select id,name from forumblocktype");
			rs=ps.executeQuery();
			while(rs.next()){
				ForumBlockType blockType=new ForumBlockType();
				blockType.setId(rs.getInt(1));
				blockType.setName(rs.getString(2));
				list.add(blockType);
			}
			return list;
		}catch(Exception e){
			logger.error("获取知识库添加出错！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	//搜索帖子
	public List<Forum> searchlistForums(int pageNow, int pageSize,Forum forum,String str) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();
			/*ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYBID));*/
			sqlstr+="select * from (select t.*, rownum rn from(select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl,forumblocktype blt where fm.creater=eu.id and fm.fblockid = bl.id and bl.fbtid=blt.id and fm.valid=1 ";
			sqlstr+=forum==null?"":forum.getTitle()==null?"":" and fm.title like '%"+forum.getTitle()+"%'";
			sqlstr+=forum==null?"":forum.getFblock()==null?"":forum.getFblock().getFbtype()==null?"":forum.getFblock().getFbtype().getId()==0?"":" and blt.id="+forum.getFblock().getFbtype().getId();
			sqlstr+=" order by fm.createtime) t where rownum <= "+pageNow+" ) where rn>="+pageSize+"";
			ps=ct.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public int searchlistForumsSize(Forum forum) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sqlstr="";
		try {
			ct = DBConnection.getConnection();
			/*ps = ct.prepareStatement(ElQuerySql
					.getSQL(ForumConstants.FORUM_LIST_BYBID));*/
			sqlstr+="select count(*) from (select t.*, rownum rn from(select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl,forumblocktype blt where fm.creater=eu.id and fm.fblockid = bl.id and bl.fbtid=blt.id and fm.valid=1 ";
			sqlstr+=forum==null?"":forum.getTitle()==null?"":" and fm.title like '%"+forum.getTitle()+"%'";
			sqlstr+=forum==null?"":forum.getFblock()==null?"":forum.getFblock().getFbtype()==null?"":forum.getFblock().getFbtype().getId()==0?"":" and blt.id="+forum.getFblock().getFbtype().getId();
			sqlstr+=" order by fm.createtime) t)";
			ps=ct.prepareStatement(sqlstr);
			rs=ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	public void addFblock_huiyuanjibie(int fblockid,int luntanjibieid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		try {
			ct = DBConnection.getConnection();
			sql = "insert into FBLOCK_USE_HUIYUANJIBIE_TYPE (fblockid,HUIYUANJIBIEID) values(?,?)";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, fblockid);
			ps.setInt(2, luntanjibieid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("论坛版块与用户论坛级别进行匹配失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		
	}
	
	//每次提交绑定的时候删除之前绑定的数据
	public void deleteFblock_huiyuanjibie(int fblockid) throws ElException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		try {
			ct = DBConnection.getConnection();
			sql = "delete from FBLOCK_USE_HUIYUANJIBIE_TYPE where fblockid=?";
			ps=ct.prepareStatement(sql);
			ps.setInt(1, fblockid);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("每次提交绑定的时候删除之前绑定的数据失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
	public boolean checkLuntanjibieHasSelectForumBlock(int fblockid,
			int luntanjibieid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		String sql="";
		boolean flag = false;
		try {
			ct = DBConnection.getConnection();
			sql = "select * from FBLOCK_USE_HUIYUANJIBIE_TYPE where fblockid="+fblockid;
			ps=ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getInt("huiyuanjibieid") == luntanjibieid){
					flag = true;
				}
			}
		} catch (Exception e) {
			logger.error("验证该用户的论坛级别是否有权限发布帖子失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return flag;
	}
	public List<Forum> listForumsByRmByDept(int pageNow, int pageSize,
			int deptid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select * from " +
					" (select t.*, rownum rn from " +
					" ( select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime,fm.jiajingtime  from  forum fm,eluser eu,forumblock bl where fm.creater in " +
					" (select id from eluser where depid in(select id from department dep inner join (select lid,rid from department dep where id=?) t2 on dep.lid>=t2.lid and dep.rid<=t2.rid)) and  fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=1 and fm.hot = 1 order by fm.receipttime desc,fm.readtime desc,fm.id desc )t " +
					" where rownum <=? ) where rn>=? ");
			ps.setInt(1, deptid);
			ps.setInt(2, pageSize);
			ps.setInt(3, pageNow);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("根据部门ID获取推荐交流文章获取失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public List<Forum> listForumsByZxByDept(int pageNow, int pageSize,
			int deptid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(" select * from " +
					" (select t.*, rownum rn from " +
					" (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,  " +
					" fm.readtime,fm.receipttime,fm.jiajingtime  from  forum fm,eluser eu,forumblock bl where fm.creater in (select id from eluser where depid in  " +
					" (select id from department dep inner join (select lid,rid from department dep where id=?) t2 on dep.lid>=t2.lid and dep.rid<=t2.rid)) and  fm.creater=eu.id  " +
					"  and fm.fblockid = bl.id and fm.valid=1  order by fm.createtime desc,fm.receipttime desc,fm.readtime desc,fm.id desc  )t where rownum <=? ) where rn>=?   ");
			ps.setInt(1, deptid);
			ps.setInt(2, pageSize);
			ps.setInt(3, pageNow);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("根据部门ID获取最新交流文章获取失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public List<Forum> newVersionGetForums(int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			ps=ct.prepareStatement("select * from (select t.*, rownum rn from ( select fm.id ,fm.title fmtitle ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and fm.valid=1  order by fm.createtime desc)t where rownum <= ? ) where rn>=?");
			ps.setInt(1, pageNow);
			ps.setInt(2, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
		
		
	}
	//-----------------外联------------------------
	/**
	 * 查询我的所有回帖
	 * @param userid
	 * @param pageNow
	 * @param pageSize
	 * @return
	 * @throws ElException
	 */
	public List<Topic> myListTopic_(int userid, int pageNow, int pageSize,int valid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Topic> tps = new ArrayList<Topic>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from ( select tp.id,tp.content,tp.createtime,tp.creater,eu.realname,fr.id frid,fr.title frtitle,tp.valid from ftopic tp left join eluser eu on eu.id = tp.creater left join forum fr on tp.forumid=fr.id where eu.id=? and tp.valid=? order by tp.createtime desc ) t where rownum <= ? ) where rn>=? ");
			ps.setInt(1, userid);
			ps.setInt(2, valid);
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);
			rs = ps.executeQuery();
			String tpContent="";
			while (rs.next()) {
				Topic tp = new Topic();
				tp.setId(rs.getInt(1));
				tpContent=rs.getString(2);
				if(tpContent.length()>15){
					tpContent=tpContent.substring(0,15)+"...";
				}
				tp.setContent(tpContent);
				tp.setCreatetime(rs.getTimestamp(3));
				tp.setCreater(new ELUser(rs.getInt(4), rs.getString(5)));
				tp.setForum(new Forum(rs.getInt("frid"),rs.getString("frtitle")));
				tp.setValid(rs.getInt("valid"));
				tps.add(tp);
			}
		} catch (Exception e) {
			logger.error("查询我的所有回帖失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return tps;
	}
	
	/**
	 * 查询我的所有回帖数量
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public int myListTopicCount_(int userid,int valid)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("select count(*) from ftopic tp left join eluser eu on eu.id = tp.creater left join forum fr on tp.forumid=fr.id where eu.id=? and tp.valid=? order by tp.createtime desc");
			ps.setInt(1, userid);
			ps.setInt(2, valid);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("查询我的所有回帖失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}
	
	//--------------------------wsj----------------------------------------------------------------
	public List<Forum> listForumsList_wsj(int bid,int pageNow, int pageSize)
	throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		try {
			ct = DBConnection.getConnection();
			/*ps = ct.prepareStatement(ElQuerySql
			.getSQL(ForumConstants.FORUM_LIST_BYBID));*/
			String sqlstr="select * from (select t.*, rownum rn from (select fm.id ,fm.title ,fm.description,fm.createtime,fm.modifytime,fm.creater,eu.realname,fm.fblockid,bl.title bltitle,fm.hot,fm.readtime,fm.receipttime  from forum fm,eluser eu,forumblock bl where fm.creater=eu.id and fm.fblockid = bl.id and bl.id="+bid+" and  fm.valid=1 order by fm.createtime)t where rownum <= "+pageNow+" ) where rn>="+pageSize;
			ps = ct.prepareStatement(sqlstr);
			/*ps.setInt(1, bid);
			ps.setString(2, "'%"+title+"%'");
			//ps.setString(2, )
			// fm.description,fm.createtime,fm.modifytime,
			// fm.creater,eu.realname,fm.fblockid,bl.title,fm.hot,fm.readtime,fm.receipttime
			ps.setInt(3, pageNow);
			ps.setInt(4, pageSize);*/
			rs = ps.executeQuery();
			while (rs.next()) {
				Forum f = new Forum(rs.getInt(1), rs.getString(2).length()>13?rs.getString(2).substring(0, 13)+"...":rs.getString(2));
				//f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(4));
				f.setModifytime(rs.getTimestamp(5));
				f.setCreater(new ELUser(rs.getInt(6), rs.getString(7)));
				f.setFblock(new ForumBlock(rs.getInt(8), rs.getString(9)));
				f.setHot(rs.getInt(10));
				f.setReadtime(rs.getInt(11));
				f.setReceipttime(rs.getInt(12));
				fs.add(f);
			}
		} catch (Exception e) {
			logger.error("论坛版块添加失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public List<Forum> getTjForums(int bid, int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.*,rownum rn from (select * from forum where fblockid=? and hot=? and valid=1 order by createtime desc)a where rownum<=8";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, bid);
			ps.setInt(2, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				//f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(3));
				f.setModifytime(rs.getTimestamp(4));
				f.setCreater(new ELUser(rs.getInt(5)));
				f.setFblock(new ForumBlock(rs.getInt(6)));
				f.setHot(rs.getInt(7));
				f.setReadtime(rs.getInt(8));
				f.setReceipttime(rs.getInt(9));
				fs.add(f);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
	public List<Forum> getTjForums(int hot) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Forum> fs = new ArrayList<Forum>();
		String sql = "";
		try {
			ct = DBConnection.getConnection();
			sql = "select a.*,rownum rn from (select * from forum where  hot=? and valid=1 order by createtime desc)a where rownum<=8";
			ps = ct.prepareStatement(sql);
			ps.setInt(1, hot);
			rs = ps.executeQuery();
			while(rs.next()){
				Forum f = new Forum(rs.getInt(1), rs.getString(2));
				//f.setDescription(new OracleBlob().getContent(rs.getBlob(3)));
				f.setCreatetime(rs.getTimestamp(3));
				f.setModifytime(rs.getTimestamp(4));
				f.setCreater(new ELUser(rs.getInt(5)));
				f.setFblock(new ForumBlock(rs.getInt(6)));
				f.setHot(rs.getInt(7));
				f.setReadtime(rs.getInt(8));
				f.setReceipttime(rs.getInt(9));
				fs.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return fs;
	}
}
