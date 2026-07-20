package com.sopia.pfms.tags;


import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;

import com.sopia.common.ElTag;
import com.sopia.pfms.entities.PolicyLib;
import com.sopia.pfms.entities.PolicyLib;

public class PolicySelect extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			PolicyLib obj = (PolicyLib) request.getAttribute("policyLib");
			
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
					writeChilds(out, obj); 
			}else{
				if(getSelectid() != 0){// 把0屏蔽。
					if(getRootAble())//在非超级管理员的时，修改2级节点需要显示上级节点。
						writeChilds(out, obj);
					else
						writeChilds_1no(out,obj);
				}else{//在用户不为超级管理员时，在搜索页面是需要显示虚拟根节点 
					writeChilds(out, obj);
				}
			}		
//			writeChilds(out, obj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		PolicyLib qlb = (PolicyLib) obj;
		List<PolicyLib> qlbChild = qlb.getChild();
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
		for (int i = 0; i < qlbChild.size(); i++) {
			PolicyLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}
	
	public void writeChilds_1no(JspWriter out, Object obj) throws Exception {
		PolicyLib qlb = (PolicyLib) obj;
		List<PolicyLib> qlbChild = qlb.getChild();

		if (qlb.getId() <=0) {//根节点不可选
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
			PolicyLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}

}

