package com.sopia.userDemo.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.userDemo.UserDemoConstants;
import com.sopia.userDemo.entities.ELUserColumn;
/**
 * 解析用户表中字段
 * @author Administrator
 *
 */
public class listUserDemoColumns extends ElTag{
	
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(listUserDemoColumns.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			
			List<ELUserColumn> cts = null;
			
			cts = (List<ELUserColumn>) request.getAttribute("columns");
			
			
			if (cts == null)
				return TagSupport.SKIP_BODY;
			
			writeChilds(out, cts);
		} catch (Exception ex) {
			logger.error("",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	@SuppressWarnings("unchecked")
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		String header = "<tr><th></th><th>字段</th><th>列名</th><th>描述</th><th>类型</th><th>格式或长度</th><th>添加页是否显示</th><th>修改页是否显示</th><th>查看页是否显示</th><th>注册页是否显示</th><th>个人修改页是否显示</th><th>个人查看页是否显示</th><th>列表页是否显示</th><th>操作</th></tr>";
		String html = "";
		String html1_begin = "<table cellpadding='1' cellspacing='1' width='100%' id='system_table'>" +
				"<caption>"+UserDemoConstants.SYSTEMCOLUMN+"</caption>" +
				header;
		String html1_center = "";
		String html1_end = "</table>";
		String html2_begin = "<table cellpadding='1' cellspacing='1' width='100%' id='shujuzidian_table'>" +
				"<caption>"+UserDemoConstants.SHUJUZIDIANCOLUMN+"</caption>" +
				header;
		String html2_center = "";
		String html2_end = "</table>";
		String html3_begin = "<table cellpadding='1' cellspacing='1' width='100% id='zdy_table'>" +
				"<caption>"+UserDemoConstants.ZDYCOLUMN+"</caption>" + 
				header;
		String html3_center = "";
		String html3_end = "</table>";
		List<ELUserColumn> qlb = (List<ELUserColumn>) obj;
		ELUserColumn co = null;
		String des  = ""; 
		if(qlb!=null){
			for(int i=0;i<qlb.size();i++){
				co = qlb.get(i);
				des = co.getDescription()==null?"":co.getDescription();
				if(co.getType() == 1){
					html1_center += "<tr>" +
							"<td align='center'><input type='radio' name='radio' value="+co.getColumn_name()+" /></td>" +
							"<input type='hidden' name='column_name' value="+co.getColumn_name()+" />"+
							"<td align='center'>"+co.getTypeName()+"</td>" +
							"<td align='center'>"+co.getColumn_name()+"</td>" +
							"<td align='center'><input type='text' name='description' value="+des+" /></td>" +
							"<td align='center'>"+co.getColumn_typeName()+"</td>" +
							"<td align='center'>"+co.getFormat()+"</td>" +
							"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_add' value="+co.getShow_add()+" " ;
							if(co.getShow_add() == 1){
								html1_center += "checked";
							}
							html1_center += " /></td>" +
							"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_update' value="+co.getShow_update()+" ";
							if(co.getShow_update() == 1){
								html1_center += "checked";
							}
							html1_center += " /></td>" +
							"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_view' value="+co.getShow_view()+" ";
							if(co.getShow_view() == 1){
								html1_center += "checked";
							}
							html1_center += " /></td>" +
							"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_register' value="+co.getShow_register()+" ";
							if(co.getShow_register() == 1){
								html1_center += "checked";
							}
							html1_center += " /></td>" +
							"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_user_update' value="+co.getShow_user_update()+" ";
							if(co.getShow_user_update() == 1){
								html1_center += "checked";
							}
							html1_center += " /></td>" +
							"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_user_view' value="+co.getShow_user_view()+" ";
							if(co.getShow_user_view() == 1){
								html1_center += "checked";
							}
							html1_center += " /></td>" +
							"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_list' value="+co.getShow_list()+" ";
							if(co.getShow_list() == 1){
								html1_center += "checked";
							}
							html1_center += " /></td>" +
							"<td align='center'>" ;
							html1_center += "<a href='javascript:setPageType(\""+co.getColumn_name()+"\");' class='textbg6' >范围</a>";
							html1_center += "<a href='javascript:addJS(\""+co.getColumn_name()+"\");' class='textbg6'>JS校验</a>" ;
							html1_center += "</td>" +
							"</tr>";
				}
				else if(co.getType() == 2){
					html2_center += "<tr>" +
					"<td align='center'><input type='radio' name='radio' value="+co.getColumn_name()+" /></td>" +
					"<input type='hidden' name='column_name' value="+co.getColumn_name()+" />"+
					"<td align='center'>"+co.getTypeName()+"</td>" +
					"<td align='center'>"+co.getColumn_name()+"</td>" +
					"<td align='center'><input type='text' name='description' value="+des+" /></td>" +
					"<td align='center'>"+co.getColumn_typeName()+"</td>" +
					"<td align='center'>"+co.getFormat()+"</td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_add' value="+co.getShow_add()+" " ;
					if(co.getShow_add() == 1){
						html2_center += "checked";
					}
					html2_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_update' value="+co.getShow_update()+" ";
					if(co.getShow_update() == 1){
						html2_center += "checked";
					}
					html2_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_view' value="+co.getShow_view()+" ";
					if(co.getShow_view() == 1){
						html2_center += "checked";
					}
					html2_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_register' value="+co.getShow_register()+" ";
					if(co.getShow_register() == 1){
						html2_center += "checked";
					}
					html2_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_user_update' value="+co.getShow_user_update()+" ";
					if(co.getShow_user_update() == 1){
						html2_center += "checked";
					}
					html2_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_user_view' value="+co.getShow_user_view()+" ";
					if(co.getShow_user_view() == 1){
						html2_center += "checked";
					}
					html2_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_list' value="+co.getShow_list()+" ";
					if(co.getShow_list() == 1){
						html2_center += "checked";
					}
					html2_center += " /></td>" +
					"<td align='center'>" ;
					html2_center += "<a href='javascript:setPageType(\""+co.getColumn_name()+"\");' class='textbg6' >范围</a>";
					html2_center += "<a href='javascript:addJS(\""+co.getColumn_name()+"\");' class='textbg6'>JS校验</a>" ;
					html2_center += "</td>" +
					"</tr>";
				}
				else if(co.getType() == 3){
					html3_center += "<tr>" +
					"<td align='center'><input type='radio' name='radio' value="+co.getColumn_name()+" /></td>" +
					"<input type='hidden' name='column_name' value="+co.getColumn_name()+" />"+
					"<td align='center'>"+co.getTypeName()+"</td>" +
					"<td align='center'>"+co.getColumn_name()+"</td>" +
					"<td align='center'><input type='text' name='description' value="+des+" /></td>" +
					"<td align='center'>"+co.getColumn_typeName()+"</td>" +
					"<td align='center'>"+co.getFormat()+"</td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_add' value="+co.getShow_add()+" " ;
					if(co.getShow_add() == 1){
						html3_center += "checked";
					}
					html3_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_update' value="+co.getShow_update()+" ";
					if(co.getShow_update() == 1){
						html3_center += "checked";
					}
					html3_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_view' value="+co.getShow_view()+" ";
					if(co.getShow_view() == 1){
						html3_center += "checked";
					}
					html3_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_register' value="+co.getShow_register()+" ";
					if(co.getShow_register() == 1){
						html3_center += "checked";
					}
					html3_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_user_update' value="+co.getShow_user_update()+" ";
					if(co.getShow_user_update() == 1){
						html3_center += "checked";
					}
					html3_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_user_view' value="+co.getShow_user_view()+" ";
					if(co.getShow_user_view() == 1){
						html3_center += "checked";
					}
					html3_center += " /></td>" +
					"<td align='center'><input onclick='changeValue(this);' type='checkbox' name='show_list' value="+co.getShow_list()+" ";
					if(co.getShow_list() == 1){
						html3_center += "checked";
					}
					html3_center += " /></td>" +
					"<td align='center'>" ;
					//单选、复选、下拉选项
					if(co.getShow_page_type() == 1 || co.getShow_page_type() == 2 || co.getShow_page_type() == 3){
						html3_center += "<a href='javascript:setPageType(\""+co.getColumn_name()+"\");' class='textbg6' >范围</a>";
					}
					if(co.getColumn_name().equals("depid") || co.getColumn_name().equals("staid") || co.getColumn_name().equals("roleid")){
						html3_center += "<a href='javascript:setPageType(\""+co.getColumn_name()+"\");' class='textbg6' >范围</a>";
					}
					html3_center += "<a href='javascript:addJS(\""+co.getColumn_name()+"\");' class='textbg6'>JS校验</a>" ;
					html3_center += "</td>" +
					"</tr>";
				}
			}
		}
		
		if(html3_center==null|| html3_center.equals("")){
			html3_center += html3_center==null?"":html3_center + "<tr><td colspan=13>暂无"+UserDemoConstants.ZDYCOLUMN+"</td></tr>";
		}
		html += html1_begin + html1_center + html1_end + html2_begin + html2_center + html2_end + html3_begin + html3_center + html3_end ;
		
		out.println(html);
	}



}
