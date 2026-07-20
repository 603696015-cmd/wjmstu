package com.sopia.schedule.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElException;
import com.sopia.schedule.TagsResolveHTMLForList;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.ModuleZDY;
import com.sopia.schedule.entities.Tags;
/**我添加和我负责的列表
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class Showlist extends TagSupport {
	private String listname;
	private String delaction;
	private String viewaction;
	private String updateaction;
	private String actionName;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getViewaction() {
		return viewaction;
	}

	public void setViewaction(String viewaction) {
		this.viewaction = viewaction;
	}

	public String getUpdateaction() {
		return updateaction;
	}

	public void setUpdateaction(String updateaction) {
		this.updateaction = updateaction;
	}
	

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			actionName = (String) request.getAttribute("actionName");
			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");


			List<Map<String, String>> list_designe = (List<Map<String, String>>) request.getAttribute("list_designe");
			
			ModuleManage moduleManage =  (ModuleManage)request.getAttribute("moduleManage");
			ModuleZDY moduleZDY = (ModuleZDY)request.getAttribute("moduleZDY");
			
			CustomAudit ca_small = (CustomAudit)request.getAttribute("ca_small");
			CustomAudit ca_big = (CustomAudit)request.getAttribute("ca_big");
			CustomAudit ca = (CustomAudit)request.getAttribute("ca");

			outPut(pageContext,list_tags,out,moduleZDY,list_designe,actionName,moduleManage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	public void outPut(PageContext pageContext,List<Tags> list_tags,JspWriter out,ModuleZDY moduleZDY,List<Map<String,String>> list_designe,String actionName,ModuleManage moduleManage) throws IOException, NumberFormatException, ElException{
		TagsResolveHTMLForList.resolveHTMLForMyContactTags(getType(),pageContext,list_tags, out, moduleZDY, list_designe, actionName,moduleManage);
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public String getDelaction() {
		return delaction;
	}

	public void setDelaction(String delaction) {
		this.delaction = delaction;
	}

}
