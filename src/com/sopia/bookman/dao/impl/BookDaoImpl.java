package com.sopia.bookman.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.bookman.BookConstants;
import com.sopia.bookman.dao.BookDao;
import com.sopia.bookman.entities.Book;
import com.sopia.bookman.entities.BookType;
import com.sopia.common.DBConnection;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeDao;
import com.sopia.common.ElQuerySql;
import com.sopia.duman.entities.ELUser;

public class BookDaoImpl extends ElNodeDao implements BookDao {
	private static final Log logger = LogFactory.getLog(BookDaoImpl.class);

	public void addBooktype(BookType ntype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			addNode(ct, ntype, "Booktype", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOKTYPE_ADD));
			ps.setString(1, ntype.getName());
			ps.setString(2, ntype.getDescription());
			ps.setInt(3, ntype.getParent().getId());
			ps.setInt(4, ntype.getLid());
			ps.setInt(5, ntype.getRid());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("图书公告栏目添加失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterBooktype(BookType ntype) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
//			alterNode(ct, ntype, "Booktype", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOKTYPE_ALTER));
			ps.setString(1, ntype.getName());
			ps.setString(2, ntype.getDescription());
			ps.setInt(3, ntype.getParent().getId());
			ps.setInt(4, ntype.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("图书公告栏目修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteBtype(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			BookType ntype = getBtypeByid(id);
//			deleteNode(ct, ntype, "Booktype", "1=1");
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOKTYPE_DELETE));
			ps.setInt(1, ntype.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("图书公告栏目修改失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public BookType getBtypeByid(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BookType nt = new BookType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOKTYPE_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				nt.setId(rs.getInt(1));
				nt.setName(rs.getString(2));
				nt.setDescription(rs.getString(3));
				nt.setParent(new BookType(rs.getInt(4), rs.getString(5)));
			}
		} catch (Exception e) {
			logger.error("获取栏目失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return nt;
	}

	public BookType getBtypeRoot() throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		BookType nt = new BookType();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOKTYPE_QUERY_BYPARENTID));
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

	public BookType getBtypeTree(int from, int stop, boolean constop)
			throws ElException {
		PreparedStatement ps = null;
		Connection ct = null;
		ResultSet rs = null;
		BookType ntype = null;
		try {
			if (from == 0) {
				ntype = getBtypeRoot();
			} else {
				ntype = getBtypeByid(from);
			}
			ct = DBConnection.getConnection();
			ntype.setChild(getBtChilds(ct, ntype.getId(), stop, constop, 0));
		} catch (Exception e) {
			logger.error("栏目树失败！", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return ntype;
	}

	private List<BookType> getBtChilds(Connection ct, int from, int stop,
			boolean containStop, int level) throws Exception {
		List<BookType> deps = new ArrayList<BookType>();
		PreparedStatement ps = ct.prepareStatement(ElQuerySql
				.getSQL(BookConstants.BOOKTYPE_QUERY_BYPARENTID));
		ps.setInt(1, from);
		ResultSet rstemp = ps.executeQuery();
		level++;
		while (rstemp.next()) {
			BookType dep = new BookType(rstemp.getInt(1), rstemp.getString(2));
			// dep.setParent(new KnowledgeType(rstemp.getInt(4)));
			dep.setLevel(level);
			if (dep.getId() != stop)
				dep.setChild(getBtChilds(ct, dep.getId(), stop, containStop,
						level));
			if (!containStop && dep.getId() == stop) {

			} else
				deps.add(dep);
		}
		ps.close();
		rstemp.close();
		return deps;
	}

	public void addBook(Book book) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql.getSQL(BookConstants.BOOK_ADD));
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getContent());
			ps.setInt(3, book.getNtype().getId());
			ps.setInt(4, book.getOwner().getId());
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.setString(6, book.getMainimg());
			// ,n.pubhouse,n.writer,n.pubtime
			ps.setString(7, book.getPubhouse());
			ps.setString(8, book.getWriter());
			ps.setTimestamp(9, book.getPubtime());

			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("添加图书失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void alterBook(Book book) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOK_ALTER));
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getContent());
			ps.setInt(3, book.getNtype().getId());
			ps.setString(4, book.getMainimg());
			ps.setString(5, book.getPubhouse());
			ps.setString(6, book.getWriter());
			ps.setTimestamp(7, book.getPubtime());

			ps.setInt(8, book.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("修改图书失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public void deleteBook(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOK_DELETE));
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除图书失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}

	public Book getBookById(int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		Book book = new Book();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOK_QUERY_BYID));
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				book = new Book(rs.getInt(1), rs.getString(2));
				book.setContent(rs.getString(3));
				book.setReleasetime(rs.getTimestamp(4));
				book.setMainimg(rs.getString(7));
				book.setNtype(new BookType(rs.getInt(5), rs.getString(6)));
				book.setOwner(new ELUser(0, rs.getString(8)));
				// .getPubhouse
				book.setPubhouse(rs.getString(9));
				book.setWriter(rs.getString(10));
				book.setPubtime(rs.getTimestamp(11));
			}
		} catch (Exception e) {
			logger.error("修改图书失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return book;
	}

	public List<Book> getBookByUid(int userid, int nid, int pageNow,
			int pageSize) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Book> Bookes = new ArrayList<Book>();
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOKTYPE_LRID));
			ps.setInt(1, nid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOK_QUERY_BYUID));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, userid);
			ps.setInt(4, pageNow   );
			ps.setInt(5, pageSize );
			rs = ps.executeQuery();
			while (rs.next()) {
				Book Book = new Book(rs.getInt(1), rs.getString(2));
				Book.setReleasetime(rs.getTimestamp(3));
				Book.setNtype(new BookType(rs.getInt(4), rs.getString(5)));
				Bookes.add(Book);
			}
		} catch (Exception e) {
			logger.error("我的图书列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return Bookes;
	}

	public int getBookCountByUid(int userid, int nid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOKTYPE_LRID));
			ps.setInt(1, nid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct.prepareStatement(ElQuerySql
					.getSQL(BookConstants.BOOK_QUERY_SIZE_BYUID));
			ps.setInt(1, lid);
			ps.setInt(2, rid);

			ps.setInt(3, userid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的图书列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public List<Book> listBooksByDep(int depid, int pageNow, int pageSize)
			throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		List<Book> bookes = new ArrayList<Book>();
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select id,lid,rid from department where id = ?");
			ps.setInt(1, depid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct
					.prepareStatement(ElQuerySql.getSQL(BookConstants.BOOK_DEP_LIST));
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			ps.setInt(3, pageNow );
			ps.setInt(4, pageSize );
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book(rs.getInt(1), rs.getString(2));
				book.setReleasetime(rs.getTimestamp(3));
				book.setNtype(new BookType(rs.getInt(4), rs.getString(5)));
				book.setOwner(new ELUser(rs.getInt(9), rs.getString(10)));
				book.setHot(rs.getInt(11));
				bookes.add(book);
			}
		} catch (Exception e) {
			logger.error("我的图书列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return bookes;
	}

	public int listBooksByDepSize(int userid) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct
					.prepareStatement("select dep.id,dep.lid,dep.rid from department dep,eluser eu where eu.id = ? and  dep.id = eu.depid");
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			int lid = 0;
			int rid = 0;
			if (rs.next()) {
				lid = rs.getInt(2);
				rid = rs.getInt(3);
			}
			ps = ct
					.prepareStatement("select count(*) "
							+ "from book n,booktype nt,eluser eu,department dep where nt.id = n.btid and eu.id = n.userid and dep.id = eu.depid and dep.lid>=? and dep.rid <=?");
			ps.setInt(1, lid);
			ps.setInt(2, rid);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			logger.error("我的图书列表失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
		return 0;
	}

	public void setHot(int hot,int id) throws ElException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection ct = null;
		try {
			ct = DBConnection.getConnection();
			ps = ct.prepareStatement("update book set hot = ? and id = ?");
			ps.setInt(1, hot);
			ps.setInt(2, id);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("删除图书失败", e);
			throw new ElException(e);
		} finally {
			DBConnection.closeConnectInfo(ct, ps, rs);
		}
	}
}
