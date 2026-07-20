package com.sopia.duman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.duman.entities.ElFunc;

public class FuncTreeList extends ElTag{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5714530967503948064L;
	private static final Log logger = LogFactory.getLog(FuncTreeList.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ElFunc cts = (ElFunc) request.getAttribute("funcTree");
			writeChilds(out,cts) ;
		} catch (Exception ex) {
			logger.error("功能显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ElFunc qlb = (ElFunc)obj;
		List<ElFunc> qlbChild= qlb.getChild();
		for (int j = 0; j < qlb.getLevel(); j++) {
			out.println("--");
		}
		if(qlb.getLevel()==0)
			out.println( qlb.getName()+" <br>");
		else
			out.print("<a href='func_alterInit.action?func.id="+qlb.getId()+"'>"+qlb.getName()+"=="+qlb.getFunccode()+"</a>【<a href='func_delete.action?func.id="+qlb.getId()+"' onclick=\"return confirm('确定删除？')\">删除</a>】<br>");
		for (int i = 0; i < qlbChild.size(); i++) {
			ElFunc qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}
}
