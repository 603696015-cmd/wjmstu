package com.sopia.courseman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.courseman.entities.EroomBatchLib;

public class ErbatchLibTree extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(ErbatchLibTree.class);

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			EroomBatchLib cts = (EroomBatchLib) request.getAttribute("erbatchLibTree");
			// writeChilds(out,cts) ;
			out
					.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
							+ "<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n"
							+ "	<script type=\"text/javascript\">\n" + "<!--\n");
			out.println("var d" + getDid() + " = new dTree('"
					+ getItype()
					+ "','"
					+ getIname()
					+ "',"
					+ (getIvalue() == null || "".equals(getIvalue()) ? 1
							: getIvalue()) + ", 'd" + getDid() + "');\n");
			writeChilds(out, cts);
			out.println("document.write(d" + getDid() + ");\n"+
			"//-->\n"+
			"</script>");
		} catch (Exception ex) {
			logger.error("批量场次类别树显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		EroomBatchLib qlb = (EroomBatchLib)obj;
		List<EroomBatchLib> qlbChild= qlb.getChild();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
			}
		else
			out.print("d" + getDid() + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			EroomBatchLib qlbi = qlbChild.get(i);
			if(qlbi.getId()==this.getIid()){
				writeChilds2(out, qlbi );
			}else{
				writeChilds(out, qlbi );
			}
		}
	}
	
	public void writeChilds2(JspWriter out, Object obj) throws Exception {
		EroomBatchLib qlb = (EroomBatchLib)obj;
		List<EroomBatchLib> qlbChild= qlb.getChild();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
			}
		else
			out.print("d" + getDid() + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"','-3');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			EroomBatchLib qlbi = qlbChild.get(i);
			writeChilds2(out, qlbi );
		}
	}

}
