package com.sopia.schedule.tags;


import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.ElFunc;
import com.sopia.schedule.NavigateForZDYUtil;
import com.sopia.schedule.entities.Tags;

public class Title extends TagSupport{
	private String tablename;
	private String actionName;
	private Integer rx;
	private ElFunc ef;
	
	public Integer getRx() {
		return rx;
	}

	public void setRx(Integer rx) {
		this.rx = rx;
	}

	public ElFunc getEf() {
		return ef;
	}

	public void setEf(ElFunc ef) {
		this.ef = ef;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
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
			rx = (Integer)request.getAttribute("rx");
			System.out.println(tablename);
			System.out.println(actionName);
			
			NavigateForZDYUtil util = new NavigateForZDYUtil();
			
			ef = util.getElFuncByTableNameAndParams(tablename,actionName,rx);
//			if(ef != null){
//				request.setAttribute("eeff", ef);
//			}else {
//				ef = (ElFunc)request.getAttribute("eeff");
//			}
//			
//			if(ef != null){
//				out.print(getNav(ef));
//			}
//			
			out.println(ef.getName());
			
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
		s =s +ef.getName();
		return s;
	}

}
