package com.sopia.knowledgeman.tag;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.common.ElTag;
import com.sopia.knowledgeman.entities.KnowledgeType;

public class KltypeSelect extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(KltypeSelect.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			KnowledgeType obj = (KnowledgeType) request.getAttribute("kltypeTree");
			if(obj==null){
				return TagSupport.SKIP_BODY;
			}
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession(); 
			String role;
			if(session.getAttribute("roleid") != null ){
				role = session.getAttribute("roleid").toString();				
			}else{//前台未登录  游客显示
				role = "1";
			}
			//当角色不为1（超级管理员）时，select的根不允许选用
			if(role.equals("1")){
				if(getSelectid() != 1){//在添加发布资源的时候， 因为根节点是虚拟节点为0 。在数据库有PK限制 所以把0屏蔽。
					writeChilds(out, obj);
				}else{
					writeChilds_1no(out,obj);
				}
			}else{
				if(getSelectid() != 0){
					writeChilds_1no(out,obj);
				}else{//在用户不为超级管理员时，在搜索页面是需要显示虚拟根节点 
					writeChilds(out, obj);
				}
			}		
		} catch (Exception ex) {
			logger.error("知识类别下拉列表显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		KnowledgeType qlb = (KnowledgeType) obj;
		List<KnowledgeType> qlbChild = qlb.getChild();
		if(qlb.getId()<=0){
			out.println("<optgroup label=\""+qlb.getName()+"\">"+qlb.getName()+"</optgroup>");
		}else{
			out.println("<option value='" + qlb.getId() + "'");
			if(getSelectid()==qlb.getId()){
				out.println("selected = 'selected'");
			}
					out.println(">");
			for (int j = 0; j < qlb.getLevel(); j++) {
				out.println("--");
			}
			out.println(qlb.getName() + " </option>");
		}
		if(qlbChild!=null){
			for (int i = 0; i < qlbChild.size(); i++) {
				KnowledgeType qlbi = qlbChild.get(i);
				writeChilds(out, qlbi);
			}
		}
	}
	
	public void writeChilds_1no(JspWriter out, Object obj) throws Exception {
		KnowledgeType qlb = (KnowledgeType) obj;
		List<KnowledgeType> qlbChild = qlb.getChild();

		if (qlb.getId() <= 0) {
			out.println("<optgroup label='" + qlb.getName() + "'></optgroup>");
		} else {
			out.println("<option value='" + qlb.getId() + "'");
			if(getSelectid()==qlb.getId()){
				out.println("selected = 'selected'");
			}
					out.println(">");
			for (int j = 0; j < qlb.getLevel(); j++) {
				out.println("--");
			}
			out.println(qlb.getName() + " </option>");
		}
		for (int i = 0; i < qlbChild.size(); i++) {
			KnowledgeType qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}

}
