package com.sopia.bookman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.newsandmess.entities.NewsType;

public class NoticeNtypeList extends ElTag{
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(NoticeNtypeList.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			NewsType qlb = (NewsType) request.getAttribute("noticeTree");
			writeChilds(out,qlb ) ;
		} catch (Exception ex) {
			logger.error("通知类别出错",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		NewsType qlb = (NewsType)obj;
		List<NewsType> qlbChild= qlb.getChild();
		for (int j = 0; j < qlb.getLevel(); j++) {
			out.println("--");
		}
		out.println("<a href='newstype_view.action?ntype.id="+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
		for (int i = 0; i < qlbChild.size(); i++) {
			NewsType qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}
}
