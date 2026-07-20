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
 * 修改页面标签
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class Update  extends TagSupport 
{
	private String listname;
	private Integer final_;
	private String uid;
	private String username;
	private String kk;
	private String fromActionName;

	

	public String getFromActionName() {
		return fromActionName;
	}

	public void setFromActionName(String fromActionName) {
		this.fromActionName = fromActionName;
	}

	public String getKk() {
		return kk;
	}

	public void setKk(String kk) {
		this.kk = kk;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	
	@SuppressWarnings("unchecked")
	public int doStartTag() 
	{
		try 
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");
			
			CurrentUser currentUser = (CurrentUser)request.getAttribute("currentUser");
			
			int id=(Integer)request.getAttribute("id");
			List<Integer> list_ricktext = new ArrayList<Integer>();
			
			String nowdate="";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			nowdate=df.format(new Date());
			
			final_ = (Integer)request.getAttribute("final_");
			username = (String) request.getAttribute("username");
			uid = (String) request.getAttribute("uid");
			
			kk = (String)request.getAttribute("kk");
			
			fromActionName = (String)request.getAttribute("fromActionName");
			
			outPut(list_tags,out,currentUser,kk,nowdate,list_ricktext,final_,fromActionName,id);
			
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
	 * @param nowdate
	 * @param list_ricktext
	 * @param final_
	 * @param actionName
	 * @param id
	 * @throws IOException
	 */
	public void outPut(List<Tags> list_tags,JspWriter out,CurrentUser currentUser,String kk,String nowdate,List<Integer> list_ricktext,int final_,String fromActionName,int id) throws IOException{
		//----------------------------js 验证
		TagsUtil.outPutUpdateJs(list_tags, out);
		
		int v = TagsUtil.outPutMyloadJs(list_tags, out);
		if(v!=-1){
			list_ricktext.add(v);
		}
		
//-------修改显示---------jsp		
		int control_tr=0;
		int control_size=0;
		TagsUtil.outPutUpdateHTML(list_tags, out, control_tr, control_size, currentUser, nowdate, list_ricktext, kk, final_, fromActionName, id);
	}
	
	public void outPut() throws IOException{
		
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public Integer getFinal_() {
		return final_;
	}

	public void setFinal_(Integer final_) {
		this.final_ = final_;
	}


	


}

