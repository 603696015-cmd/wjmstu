package com.sopia.schedule.tags;


import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.ElFunc;
import com.sopia.schedule.NavigateForZDYUtil;
import com.sopia.schedule.entities.Tags;

public class NavigationForViewAndUpdate extends TagSupport{
	private String tablename;
	private ElFunc ef;
	private String actionName;
	
	

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}


	public ElFunc getEf() {
		return ef;
	}

	public void setEf(ElFunc ef) {
		this.ef = ef;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public int doStartTag()
	{
		try
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			tablename = (String)request.getAttribute("tablename");
			actionName = (String)request.getAttribute("actionName");
			System.out.println(tablename);
			NavigateForZDYUtil util = new NavigateForZDYUtil();
			out.print("<div><a target='_blank' href='index.action'>首页</a>&nbsp;>>&nbsp;");
			
			ef = util.getElFuncByTableNameForViewOrUpdate(tablename,actionName);
			if(ef != null){
				request.setAttribute("eeff", ef);
			}else {
				ef = (ElFunc)request.getAttribute("eeff");
			}
			
			String result = "";
			if(ef != null){
				result = getNav(ef);
				if(!result.equals(""))
					out.print(result.substring(0,result.lastIndexOf("&nbsp;>>&nbsp;")));
			}
//			//添加退出全屏按钮
//			out.println("-----<a  id='quit' href='javascript:window.parent.full_screen(false);' class='textbg6'>退出全屏</a>");
			out.print("</div>");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	
	/**
	 * 递归，获取导航的
	 * Description: 
	 * @param ef
	 * @return
	 */
	private String getNav(ElFunc ef){
		String s = "";
		if(ef.getParent()!=null){
			s = getNav(ef.getParent())+s;
		}
		if(ef.getName()!=null)
		s =s +"<span>"+ef.getName()+"</span>&nbsp;>>&nbsp;";
		return s;
	}

}
