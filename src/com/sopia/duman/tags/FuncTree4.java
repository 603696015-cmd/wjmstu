package com.sopia.duman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.AuthorityUtil;
import com.sopia.common.ElTag;
import com.sopia.courseman.entities.CourseType;
import com.sopia.duman.entities.ElFunc;
import com.sopia.duman.entities.ElRole;
import com.sopia.duman.entities.Station;

public class FuncTree4 extends ElTag{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5714530967503948064L;
	private static final Log logger = LogFactory.getLog(FuncTree4.class);
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
//			for (int i = 0; i < cts.getChild().size(); i++) {
//				cts=cts.getChild().get(i);
//				out.println("<script type=\"text/javascript\">\n" + "<!--\n");
//				out.println("var d"	+ i+ " = new dTree('d" + i + "');\n"); 
//				writeChilds(out,cts,i) ;
//				out.println("document.write(d" + i + ");\n" + "//-->\n" + "</script>");
//				n=0;
//			}
			if(getNodeIndex()>=cts.getChild().size()){
				return SKIP_PAGE;
			}
			cts=cts.getChild().get(getNodeIndex());
			out
			.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/wtree.css\" />\n"
//					+ "<script type=\"text/javascript\" src=\"js/tree/wtree_st.js\"></script>\n"
					+ "<script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n"
					+ "	<script type=\"text/javascript\">\n"
					+ "<!--\n"
					+ "var s"
					+ getDid()
					+ " = new WTree('s"
					+ getDid()
					+ "',"
					+ getRootAble()
					+ ",'"
					+ getItype()
					+ "','"
					+ getIname() + "','"+( getHref() == null || "".equals(getHref()) ? ""
							: getHref())
					+"');\n");
			writeChilds(out, cts);
			 
			// }
			// writeChilds(out,cts) ;
			out
					.println("s" + getDid() + ".doShow();" + "//-->\n"
							+ "");
			
			out.println("</script>");
		} catch (Exception ex) {
			logger.error("功能树显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	
	int n=0;
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ElFunc qlb = (ElFunc)obj;
		List<ElFunc> qlbChild= qlb.getChild();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		int pid = qlb.getParent()==null?0:qlb.getParent().getId();
		String desc=qlb.getName()+"-"+qlb.getDescription()+"==="+AuthorityUtil.checkAuthor(roleid, qlb.getId(),0);
		desc=desc.replace("\n", "");
		desc=desc.replace("\r", "");
		/**
		 * public void writeChilds(JspWriter out, Object obj) throws Exception {
		Station qlb = (Station) obj;
		List<Station> qlbChild = qlb.getChild();
//		String href1 = getHref() == null || "".equals(getHref()) ? ""
//				: getHref() + qlb.getId();
		int pid = qlb.getParent()==null?0:qlb.getParent().getId();
		out.print("s" + getDid() + ".addNode(" + qlb.getId() + ",'" + qlb.getName() + "',"
				+ pid + ","+(qlb.getClassCount()>0?true:false)+",'"+qlb.getBh()+"',"+qlb.getLid()+","+qlb.getRid()+");\n");
		if (qlbChild != null)
			for (int i = 0; i < qlbChild.size(); i++) {
				Station qlbi = qlbChild.get(i);
				writeChilds(out, qlbi);
			}
	}
		
		if(n==0){
			out.println("d" + getDid() + ".add(0,-1,'"+qlb.getName() +"模块功能');");
			n=1;
		}
		out.print("d" + getDid() + ".add2("+qlb.getId()+","+qlb.getParent().getId()+",'"+desc+"','"+href1+"','0','qlib');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ElFunc qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
			if(qlbi.getLevel()==1){
				break;
			}
		}
		 */
		
		
		out.print("s" + getDid() + ".addNode(" + qlb.getId() + ",'" + qlb.getName() + "',"
				+ pid +"" + "," + (qlb.getChild().size()>0?true:false) + 
						");\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ElFunc qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		n=0;
		return EVAL_PAGE;
	}
	
	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		CourseType qlb = (CourseType)obj;
		if(qlb!=null){
			List<CourseType> qlbChild= qlb.getChild();
			String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
			if(qlb.getLevel()==0){
				if(!getRootAble()){
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n"); 
				}
				else{
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
				}
			}
			else{
				//判断是否已有该权限，来决定chkbox的是否选中
				List treeAllId=(List)pageContext.getRequest().getAttribute("treeAllId");
				int ischk=0;
				if(treeAllId.contains(qlb.getId())==true){
					ischk=1;
				}
				out.print("d" + getDid() + ".add2("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"','"+ischk+"','"+this.getTreeType()+"');\n");
			}
			for (int i = 0; i < qlbChild.size(); i++) {
				CourseType qlbi = qlbChild.get(i);
				writeChildsCb_2(out, qlbi );
			}
		} 
	}
	
	public void writeChilds(JspWriter out, Object obj,int i) throws Exception {
		ElFunc qlb = (ElFunc)obj;
		List<ElFunc> qlbChild= qlb.getChild();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
//		for (int j = 0; j < qlb.getLevel(); j++) {
//			out.println("--");
//		}
		String desc=qlb.getName()+"-"+qlb.getDescription()+"==="+AuthorityUtil.checkAuthor(roleid, qlb.getFunccode(),0);
		//String desc=qlb.getName();
		desc=desc.replace("\n", "");
		desc=desc.replace("\r", "");
		//if(qlb.getLevel()==0){
		if(n==0){
			//out.println("d" + getDid() + ".add("+qlb.getId()+",-1,'"+desc +"');\n");
			out.println("d" + i + ".add(0,-1,'"+qlb.getName() +"模块功能');");
			n=1;
		}
		out.println("d" + i + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+desc+"','"+href1+"');\n");
		for (int j = 0; j < qlbChild.size(); j++) {
			ElFunc qlbi = qlbChild.get(j);
			writeChilds(out, qlbi ,i);
			if(qlbi.getLevel()==1){
				break;
			}
		}
	}
}
