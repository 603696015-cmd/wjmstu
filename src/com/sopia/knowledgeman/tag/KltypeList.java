package com.sopia.knowledgeman.tag;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.knowledgeman.entities.KnowledgeType;

public class KltypeList extends ElTag{
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(KltypeList.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			KnowledgeType qlb = (KnowledgeType) request.getAttribute("kltypeTree");
			
			String itype=getItype();
			if(itype==null||"".equals(itype)){
				writeChilds(out, qlb);
			}
//			else if("cb_2".equals(itype)){
//				itype="cb_2";
//			}
			else if("OP".equals(itype)&&!"001".equals(itype)){//OP  可操作类型
				//writeChildsOP(out, qlb , role);
				writeChilds(out, qlb);
			}else{
				if(getItype()!= null && getItype().equals("001")){
					itype="OP";
				}
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
						+ "<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n"
						+ "	<script type=\"text/javascript\">\n" + "<!--\n");
			out.println("var d"	+ getDid()+ " = new dTree('"+ itype+ "','"+ getIname()+ "',"+ (getIvalue() == null || "".equals(getIvalue()) ? 1
							: getIvalue()) + ", 'd" + getDid() + "');\n");
				if(getItype()!= null && getItype().equals("cb_2")){
					writeChildsCb_2(out, qlb);
				}else if(getItype()!= null && getItype().equals("001")){
					//writeChildsOP(out, qlb , role);
					writeChilds2(out, qlb);
				}else{
					writeChilds(out, qlb);
				}
				//writeChilds(out,qlb ) ;
				
				out.println("document.write(d" + getDid() + ");\n" + 
						"//-->\n"
						+ "</script>");
			}
			//writeChilds(out,qlb ) ;
		} catch (Exception ex) {
			logger.error("知识类别列表显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		KnowledgeType qlb = (KnowledgeType)obj;
		List<KnowledgeType> qlbChild= qlb.getChild();
		for (int j = 0; j < qlb.getLevel(); j++) {
			out.println("--");
		}
		if(qlb.getLevel()==0&&!getRootAble()) out.println(qlb.getName()+"<br>");
		else
		out.println("<a href='"+getHref()+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
		for (int i = 0; i < qlbChild.size(); i++) {
			KnowledgeType qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}
	
	public void writeChilds2(JspWriter out, Object obj) throws Exception {
		KnowledgeType qlb = (KnowledgeType)obj;
		if(qlb!=null){
			List<KnowledgeType> qlbChild= qlb.getChild();
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
				KnowledgeType qlbi = qlbChild.get(i);
				writeChilds2(out, qlbi );
			}
		}
	}
	
	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		KnowledgeType qlb = (KnowledgeType)obj;
		if(qlb!=null){
			List<KnowledgeType> qlbChild= qlb.getChild();
			String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
			if(qlb.getLevel()==0)
				if(!getRootAble()){
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
					//out.print("d" + getDid() + ".add2("+qlb.getId()+","+-1+",'"+qlb.getName()+"','"+href1+"','"+1+"','"+this.getTreeType()+"');\n");
				}
				else{
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
				}
			else{
				//判断是否已有该权限，来决定chkbox的是否选中
				List treeAllId=(List)pageContext.getRequest().getAttribute("treeAllId");
				int ischk=0;
				if(treeAllId!=null){
					if(treeAllId.contains(qlb.getId())==true){
						ischk=1;
					}
				}
				out.print("d" + getDid() + ".add2("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"','"+ischk+"','"+this.getTreeType()+"');\n");
			}
			for (int i = 0; i < qlbChild.size(); i++) {
				KnowledgeType qlbi = qlbChild.get(i);
				writeChildsCb_2(out, qlbi );
			}
		}
	}
}
