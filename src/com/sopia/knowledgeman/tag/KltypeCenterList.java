package com.sopia.knowledgeman.tag;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.courseman.entities.CourseType;
import com.sopia.knowledgeman.entities.KnowledgeType;

public class KltypeCenterList extends ElTag{
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(KltypeCenterList.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			KnowledgeType qlb = (KnowledgeType) request.getAttribute("kltypeTree");
			if(qlb==null){
				return TagSupport.SKIP_BODY;
			}
			out
			.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
					+ "<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n"
					+ "	<script type=\"text/javascript\">\n" + "<!--\n");
			out.println("var d = new dTree(' ',' ', 0, 'd');\n");
			writeChilds(out,qlb ) ;
			out.println("document.write(d);\n"+
					"//-->\n"+
					"</script>");
		} catch (Exception ex) {
			logger.error("知识中心类别列表显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		KnowledgeType qlb = (KnowledgeType)obj;
		List<KnowledgeType> qlbChild= qlb.getChild();

		String href1 = "knowledge_center_list.action?kltype.id="+qlb.getId();
		if(qlb.getLevel()==0)
			if(getRootAble()){
				out.print("d.add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
				out.print("d.add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
			}
		else
			out.print("d.add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"');\n");
		if(qlbChild!=null){
			for (int i = 0; i < qlbChild.size(); i++) {
				KnowledgeType qlbi = qlbChild.get(i);
				writeChilds(out, qlbi );
			}
		}
	}
	/**

	for (int j = 0; j < qlb.getLevel(); j++) {
		out.println("d--");
	}
	out.println("<a href='knowledge_center_list.action?kltype.id="+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
	for (int i = 0; i < qlbChild.size(); i++) {
		KnowledgeType qlbi = qlbChild.get(i);
		writeChilds(out, qlbi );
	}
	*/
}
