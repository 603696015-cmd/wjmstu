package com.sopia.duman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.AuthorityUtil;
import com.sopia.common.ElTag;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElRole;

public class FuncTree extends ElTag{
	/**
	 * 
	 */
	private static final Log logger = LogFactory.getLog(FuncTree.class);
	private static final long serialVersionUID = 5714530967503948064L;
	private int roleid;
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
//			HttpServletRequest req = (HttpServletRequest) request;
			roleid=0;
			if (null != request.getAttribute("role")) {
				ElRole er = (ElRole ) request.getAttribute("role");
				roleid= er.getId();
			}
			ElFunc cts = (ElFunc) request.getAttribute("funcTree");
			out.println("	<script type=\"text/javascript\">\n"+
		"<!--\n"+
		"var d = new dTree('d');\n");
			writeChilds(out,cts) ;
			out.println("document.write(d);\n"+
			"//-->\n"+
			"</script>");
		} catch (Exception ex) {
			logger.error("¹¦ÄÜÊ÷ÏÔÊ¾´íÎó");
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ElFunc qlb = (ElFunc)obj;
		List<ElFunc> qlbChild= qlb.getChild();
//		for (int j = 0; j < qlb.getLevel(); j++) {
//			out.println("--");
//		}
		String desc=qlb.getName()+"-"+qlb.getDescription()+"==="+AuthorityUtil.checkAuthor(roleid, qlb.getFunccode(),0);
		desc=desc.replace("\n", "");
		desc=desc.replace("\r", "");
		if(qlb.getLevel()==0)
			out.println("d.add("+qlb.getId()+",-1,'"+desc +"');\n");
		else
			out.println("d.add("+qlb.getId()+","+qlb.getParent().getId()+",'"+desc+"','');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ElFunc qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}
}
