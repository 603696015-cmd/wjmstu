package com.sopia.bookman.dao;

import java.util.List;

import com.sopia.bookman.entities.Book;
import com.sopia.bookman.entities.BookType;
import com.sopia.common.ElException;

public interface BookDao {
	public void addBooktype(BookType ntype) throws ElException;

	public void alterBooktype(BookType ntype) throws ElException;

	public void deleteBtype(int id) throws ElException;

	public BookType getBtypeTree(int from, int stop, boolean constop)
			throws ElException;

	public BookType getBtypeRoot() throws ElException;

	public BookType getBtypeByid(int id) throws ElException;

	public void addBook(Book Book) throws ElException;

	public void alterBook(Book Book) throws ElException;

	public void deleteBook(int id) throws ElException;

	public Book getBookById(int id) throws ElException;

	public List<Book> getBookByUid(int userid, int nid, int pageNow,
			int pageSize) throws ElException;

	public int getBookCountByUid(int userid, int nid) throws ElException;
	public List<Book> listBooksByDep(int depid,int pageNow, int pageSize)
	throws ElException;
	public int listBooksByDepSize(int depid) throws ElException ;
	public void setHot(int hot,int id) throws ElException ;
}
