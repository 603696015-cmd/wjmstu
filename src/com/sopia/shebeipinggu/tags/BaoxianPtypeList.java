package com.sopia.shebeipinggu.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;

import com.sopia.cms.entities.ColumnTemplate;
import com.sopia.cms.impl.TemplateDaoImpl;
import com.sopia.common.ElTag;
import com.sopia.pfms.entities.ProductType;

public class BaoxianPtypeList extends ElTag{
	private static final long serialVersionUID = 3119679319963664116L;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ProductType qlb = (ProductType) request.getAttribute("ptypeTree");
			
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();
			String role = "";
			Object roleObj=session.getAttribute("roleid");
			if(roleObj!=null){
				role =roleObj.toString();
			}
			String itype=getItype();
//			if(getItype()!= null && getItype().equals("ra_1no")){
//				 itype = (session.getAttribute("roleid").toString().equals("1")&&getItype().equals("ra_1no"))?"ra":"ra_1no";
//				}else{
//					itype =getItype();
//					//itype="...";
//				}
//				if(getItype()==null){
//					setItype("OP");
//				}
//				if(session.getAttribute("roleid").toString().equals("1")&&"OP".equals(getItype())==false){
//					itype="ra";
//				}
//				if(getItype()==null){
//					//setItype("ra1");
//					itype="ra1";
//				}
			if("cb_2".equals(getItype())){
				itype="cb_2";
			}
			if("font".equals(getTreeType())){ 
				writeChildsTemplate(out, qlb); 
				return TagSupport.SKIP_BODY;
			}
			if(itype==null||"".equals(itype)){
				writeChilds(out, qlb);
			}else if("OP".equals(itype)&&!"001".equals(itype)){//OP  可操作类型
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ProductType qlb = (ProductType)obj;
		if(qlb!=null){
			List<ProductType> qlbChild= qlb.getChild();
			for (int j = 0; j < qlb.getLevel(); j++) {
				out.println("--");
			}
			if (qlb.getLevel()==0&&!getRootAble())out.println(qlb.getName()+"<br>");
			else
			out.println("<a href='"+getHref()+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
			for (int i = 0; i < qlbChild.size(); i++) {
				ProductType qlbi = qlbChild.get(i);
				writeChilds(out, qlbi );
			}
		}
	}
	
	public void writeChilds2(JspWriter out, Object obj) throws Exception {
		ProductType qlb = (ProductType)obj;
		if(qlb!=null){
			List<ProductType> qlbChild= qlb.getChild();
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
				ProductType qlbi = qlbChild.get(i);
				writeChilds2(out, qlbi );
			}
		}
	}
	
	//用户角色不为1（超级管理员）时，可操作节点时根节点无超链接
	public void writeChildsOP(JspWriter out, Object obj ,String role) throws Exception {
		ProductType qlb = (ProductType)obj;
		List<ProductType> qlbChild= qlb.getChild();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
				if(role.equals("1")){
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
				}else{
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
				}
			}
		else
			out.print("d" + getDid() + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ProductType qlbi = qlbChild.get(i);
			writeChilds2(out, qlbi );
		} 
	}
	
	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		ProductType qlb = (ProductType)obj;
		if(qlb!=null){
			List<ProductType> qlbChild= qlb.getChild();
			String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
			if(qlb.getLevel()==0)
				if(!getRootAble()){
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
				}
				else{
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
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
				ProductType qlbi = qlbChild.get(i);
				writeChildsCb_2(out, qlbi );
			}
		}
	}
	
	public void writeChildsTemplate(JspWriter out, Object obj) throws Exception {
		ProductType qlb = (ProductType)obj;
		if(qlb!=null){
			List<ProductType> qlbChild= qlb.getChild(); 
			if (qlb.getLevel()==0&&!getRootAble()) {
				out.println(qlb.getName()+"<br>");
				}
			else{
				List<ColumnTemplate> t = new TemplateDaoImpl().listColumnTmpByType("xw");
				for (ColumnTemplate columnTemplate : t) {
					if(qlb.getId()==columnTemplate.getColumnId()){
						for (int j = 0; j < qlb.getLevel(); j++) {
							out.println("--");
						}
						out.println("<a href='elfrontman/cms/XW"+qlb.getId()+".jsp'>"+qlb.getName()+"</a><br>");
					}
				} 
			}for (int i = 0; i < qlbChild.size(); i++) {
				ProductType qlbi = qlbChild.get(i);
				writeChildsTemplate(out, qlbi );
			}
		}
	}
}
