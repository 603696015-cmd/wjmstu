package com.sopia.schedule.tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.entities.CurrentUser;
import com.sopia.schedule.entities.Tags;

/**
 * 添加页面标签
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class AddInfo  extends TagSupport 
{
	private String listname;
	
	private String uid;
	private String username;
	private String kk;
	
	private String fromtablename;
	
	

	
	@SuppressWarnings("unchecked")
	public int doStartTag() 
	{
		try 
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			CurrentUser currentUser = (CurrentUser)request.getAttribute("currentUser");
			
			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");
			List<Integer> list_ricktext = new ArrayList<Integer>();
			
			String nowdate="";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			nowdate=df.format(new Date());
			
			
			username = (String) request.getAttribute("username");
			uid = (String) request.getAttribute("uid");
			
			kk = (String)request.getAttribute("kk");
			
			fromtablename = (String)request.getAttribute("fromtablename");
			
			outPut(list_tags,out,currentUser,kk,username,uid,nowdate,list_ricktext);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	/**
	 * 输出HTML和js
	 * @param list_tags
	 * @param out
	 * @param currentUser
	 * @param kk
	 * @param username
	 * @param uid
	 * @throws IOException
	 */
	public void outPut(List<Tags> list_tags,JspWriter out,CurrentUser currentUser,String kk,String username,String uid,String nowdate,List<Integer> list_ricktext) throws IOException{
		//----------------------------js 验证
		TagsUtil.outPutAddInfoJs(list_tags, out);
		
		int v = TagsUtil.outPutMyloadJs(list_tags, out);
		if(v!=-1){
			list_ricktext.add(v);
		}
		
//-----------------------------------jsp---------------------------
		int control_tr=0;
		int control_size=0;
		
		TagsUtil.outPutAddInfoHTML(list_tags,out,control_tr,control_size,currentUser,nowdate,list_ricktext,kk,username,uid);
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}




	public String getFromtablename() {
		return fromtablename;
	}


	public void setFromtablename(String fromtablename) {
		this.fromtablename = fromtablename;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getKk() {
		return kk;
	}


	public void setKk(String kk) {
		this.kk = kk;
	}



}

