package com.sopia.bookman.action;

import java.util.List;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.bookman.dao.BookDao;
import com.sopia.bookman.entities.Book;
import com.sopia.bookman.entities.BookType;
import com.sopia.common.ElException;
import com.sopia.common.ElNodeSQL;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.entities.ELUser;

public class BookAction extends BaseAction {
	private BookType bookTree;
	private BookType btype;
	private BookDao bookDao;
	private List<Book> books;
	private Book book;
	public String booktype_list() throws ElException {
		bookTree = bookDao.getBtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);

		return "booktype_list";
	}

	public String booktype_addInit() throws ElException {
		bookTree = bookDao.getBtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);

		return "booktype_add";
	}

	public String booktype_add() throws ElException {
		bookDao.addBooktype(btype);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("Booktype");
		
		return "booktype_add_success";
	}

	public String booktype_alterInit() throws ElException {
		btype = bookDao.getBtypeByid(btype.getId());
		bookTree = bookDao.getBtypeTree(ElConstants.TREE_ROOT, btype.getId(),
				false);

		return "booktype_alter";
	}

	public String booktype_alter() throws ElException {
		bookDao.alterBooktype(btype);
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("Booktype");
		
		return "booktype_alter_success";
	}

	public String booktype_view() throws ElException {
		bookTree = bookDao.getBtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		btype = bookDao.getBtypeByid(btype.getId());
		return "booktype_view";
	}
	public String booktype_delete() throws ElException {
		bookDao.deleteBtype(btype.getId());
		((ElNodeSQL) SpringContextUtil.getBean("elnodesql"))
		.updatetlrid("Booktype");
		return "booktype_list";
	}
	// TODO É¾³ýÀ¸Ä¿
	/**
	 * @return
	 * @throws ElException
	 */
	public String book_list() throws ElException {
		bookTree = bookDao.getBtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		int nid = btype==null ?bookDao.getBtypeRoot().getId():btype.getId();
//		getPageSize()=getPageSize()==0?10:getPageSize();
		books = bookDao.getBookByUid(
				getSessionIntValue(ElConstants.SESSION_USERID),nid, getPageNow(), getPageSize());
		count = bookDao.getBookCountByUid(getSessionIntValue(ElConstants.SESSION_USERID),nid);
		return "book_list";
	}

	public String book_addInit() throws ElException {
		bookTree = bookDao.getBtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);

		return "book_add";
	}

	public String book_add() throws ElException {
		book.setOwner(new ELUser(getSessionIntValue(ElConstants.SESSION_USERID)));
		bookDao.addBook(book);

		return "book_add_success";
	}
	public String book_delete()throws ElException{
		bookDao.deleteBook(book.getId());
		/*bookes = bookDao.getbookByUid(
				getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());*/
		return "book_delete";
	}
	public String book_alterInit() throws ElException {

		bookTree = bookDao.getBtypeTree(ElConstants.TREE_ROOT,
				ElConstants.TREE_FIANL, true);
		book = bookDao.getBookById(book.getId());

		return "book_alter";
	}

	public String book_alter() throws ElException {

		bookDao.alterBook(book);
		return "book_alter_success";
	}

	public String book_view() throws ElException {
		book = bookDao.getBookById(book.getId());

		return "book_view";
	}
	public String booksethot_list() throws ElException {
//		getPageSize()=getPageSize()==0?10:getPageSize();
		books = bookDao.listBooksByDep(getSessionIntValue(ElConstants.SESSION_USERID), getPageNow(), getPageSize());
		count=bookDao.listBooksByDepSize(getSessionIntValue(ElConstants.SESSION_USERID));
		return "booksethot_list";
	}
	public String booksethot () throws ElException {
		bookDao.setHot(book.getHot(), book.getId());
		return "booksethot_list";
	}
	public BookType getBookTree() {
		return bookTree;
	}

	public void setBookTree(BookType bookTree) {
		this.bookTree = bookTree;
	}

	public BookType getBtype() {
		return btype;
	}

	public void setBtype(BookType btype) {
		this.btype = btype;
	}

	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
