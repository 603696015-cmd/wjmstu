package com.sopia.bookinfo.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.bookman.entities.BookType;
import com.sopia.common.ElTag;

public class BookTypeList extends ElTag{

	private static final long serialVersionUID = 3119679319963664116L;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			BookTypeTree qlb = (BookTypeTree) request.getAttribute("bookTypeTree");
			writeChilds(out,qlb ) ;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		BookTypeTree qlb = (BookTypeTree)obj;
		List<BookTypeTree> qlbChild= qlb.getChild();
		for (int j = 0; j < qlb.getLevel(); j++) {
			out.println("--");
		}
		if (qlb.getLevel()==0&&!getRootAble()) {
			out.println(qlb.getName()+"<br>");
			}
		else
		out.println("<a href='"+getHref()+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
		for (int i = 0; i < qlbChild.size(); i++) {
			BookTypeTree qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}
	

}
