package com.sopia.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.duman.entities.SimpleNode;

/**
 * 位置导航标签(树)
 * 
 * @author Administrator
 * 
 */
public class TreeNavigation extends TagSupport {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(TreeNavigation.class);
	private int oid;
	private String ivalue;
	private String itype;
	private String href;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getItype() {
		return itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public String getIvalue() {
		return ivalue;
	}

	public void setIvalue(String ivalue) {
		this.ivalue = ivalue;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
//			ServletRequest request = pageContext.getRequest();
			HttpSession session = ServletActionContext.getRequest().getSession();
			Object tempObj=session.getAttribute("roleid");
			int roleId =0;
			if(tempObj!=null){
				roleId = Integer.parseInt(session.getAttribute("roleid")+"");
			}
			String tabName = "";
			String nodeName="";
			if (getItype() != null && !"".equals(getItype())) {
				if ("courseTree".equals(getItype())) {
					tabName = "course_type";
					nodeName="可查看的课程库";
				} else if ("examRoomTree".equals(getItype())) {
					tabName = "eroom_lib";
					nodeName="可查看的考场库";
				} else if ("classTree".equals(getItype())) {
					tabName = "elclasstype";
					nodeName="可查看的培训班类别";
				} else if ("newsTree".equals(getItype())) {
					tabName = "newstype";
					nodeName="可查看的新闻类别";
				} else if ("knowledgeTree".equals(getItype())) {
					tabName = "knowledgetype";
					nodeName="可查看的资料库";
				} else {
					return TagSupport.SKIP_BODY;
				}
			} else {
				return TagSupport.SKIP_BODY;
			}
			TreeNavigationUtil tnu = new TreeNavigationUtil();
			List<SimpleNode> simpleNodes=null;
			if(this.oid>0){
				simpleNodes = tnu.getsimpleNodes(this.oid, tabName);
				if(roleId!=1){
					simpleNodes.set(simpleNodes.size()-1, new SimpleNode(-2,nodeName));
				}
			}else{
				simpleNodes=new ArrayList<SimpleNode>();
				simpleNodes.add(new SimpleNode(-2,nodeName));
			}
			out.print("<div><a href='index.action'>首页</a>&nbsp;>>&nbsp;");
			for (int i = simpleNodes.size() - 1; i >= 0; i--) {
				if (simpleNodes.get(i) != null) {
					if (i == 0) {
						if (getHref() == null) {
							out.print("<span><a>"
									+ simpleNodes.get(i).getName()
									+ "</a></span>&nbsp;&nbsp;");
						} else {
							out.print("<span><a href='" + this.getHref()
									+ simpleNodes.get(i).getId() + "'>"
									+ simpleNodes.get(i).getName()
									+ "</a></span>&nbsp;&nbsp;");
						}
					} else {
						if (getHref() == null) {
							out.print("<span><a>"
									+ simpleNodes.get(i).getName()
									+ "</a></span>&nbsp;>>&nbsp;");
						} else {
							out.print("<span><a href='" + this.getHref()
									+ simpleNodes.get(i).getId() + "'>"
									+ simpleNodes.get(i).getName()
									+ "</a></span>&nbsp;>>&nbsp;");
						}
					}
				}
			}
			// out.print("<span>"+this.ivalue+"</span>");
			out.print("</div>");

		} catch (Exception ex) {
			logger.error("位置导航标签(树)错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
}
