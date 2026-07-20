package com.sopia.userDemo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sopia.userDemo.entities.ELUserColumn;
import com.sopia.userDemo.entities.ELUserPage;

public class UserDemoUtil {
	
	/**
	 * 判断某文件夹下是否存在某文件
	 * @param pageid
	 * @return
	 */
	public static boolean checkFilenameIsExist(int pageid){
		//验证admin\\eluserdemo下是否存在pageid.jsp文件,存在返回true
		boolean flag = false;
		String folder = UserDemoConstants.FOLDER;
		if(new File(folder + pageid + ".jsp").exists()){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 查询列表、添加、修改、查看页面显示
	 * @param aliases
	 * @param show
	 * @return
	 */
	public static String getShowWhere(String aliases,int show){
		aliases = (aliases==null||aliases.equals(""))?"":aliases + ".";
		if(show == 1){
			return aliases +  "show_add = 1";
		}else if(show == 2){
			return aliases +  "show_update = 1";
		}else if(show == 3){
			return aliases +  "show_view = 1";
		}else if(show == 4){
			return aliases +  "show_register = 1";
		}else if(show == 5){
			return aliases +  "show_user_update = 1";
		}else if(show == 6){
			return aliases +  "show_user_view = 1";
		}else if(show == 7){
			return aliases +  "show_list = 1";
		}
		return "";
	}
	
	
	/**
	 * 获取拼凑的列
	 * @param cols
	 * @param aliases
	 * @return
	 */
	public static String getSqlcolumns(List<ELUserColumn> cols,String aliases){
		aliases = (aliases==null||aliases.equals(""))?"":aliases + ".";
		String columns = "";
		for(int i=0;i<cols.size();i++){
			if(i == cols.size() - 1){
				columns += aliases + cols.get(i).getColumn_name() ;
			}else{
				columns += aliases + cols.get(i).getColumn_name() + ",";
			}
		}
		return columns;
	}
	
	/**
	 * 获取列的值
	 * @param elUser
	 * @param columnname
	 * @return
	 */
	public static String getColumnValue(Map<String,Object> object,ELUserColumn column){
		if(column.getColumn_type()!=null){
			if(column.getColumn_type().equals("varchar2")){
				return (String)object.get(column.getColumn_name());
			}else if(column.getColumn_type().equals("number")){
				return (String)object.get(column.getColumn_name());
			}else if(column.getColumn_type().equals("date")){
				return (String)object.get(column.getColumn_name());
			}else {
				return "";
			}
		}else{
			return "";
		}
	}
	
	/**
	 * ary中是否包含str
	 * @param str
	 * @param ary
	 * @return
	 */
	public static boolean selected(String str,String[] ary){
		boolean flag = false;
		return flag;
	}
	
	/**
	 * 根据pageid显示同一字段在不同页面的显示HTML
	 * @param pageid
	 * @param object
	 * @param column
	 * @return
	 */
	public static Map<String,String> getColumnHTMLByPageid(int pageid,Map<String,Object> object,ELUserColumn column,Map<String,Object> params){
		Map<String,String> map = new HashMap<String,String>();
		String[] csses = null;//主要是针对图片或者附件，点击浏览资源库时候的按钮样式
		List<ELUserPage> elUserPages = null;
		ELUserPage elUserPage = null;
		String[] array = null;//保存单选、复选、下拉选项的范围
		String[] default_select = null;//保存默认值
		String value = getColumnValue(object,column);//获取列显示的值
		if(value==null)	value = "";
		String columnHTML = "";
		//列表页面不处理
		if(pageid == 1){//添加页面
			if(column.getShow_page_type() == 0){
				columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+params.get("css")+" id=\""+column.getColumn_name()+"\"  />";
				if(column.getShow_page_type() == 0){//0：文本框
					columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+params.get("css")+" id=\""+column.getColumn_name()+"\"  />";
				}else if(column.getShow_page_type() == 1){//单选
					elUserPages = column.getElUserPages();
					for(int j=0;j<elUserPages.size();j++){
						if(elUserPages.get(j).getPageid() == pageid){
							elUserPage = elUserPages.get(j);
						}
					}
					if(elUserPage!=null){
						array = elUserPage.getRange().split("==");
						if(array!=null){
							for(int k=0;k<array.length;k++){
								if(elUserPage.getDefault_select()!=null&&elUserPage.getDefault_select().equals(array[k])){
									//只能选择一个
									columnHTML +=  array[k] + "：<input type='radio' name=\""+column.getColumn_name()+"\" "+params.get("css")+" checked />";
								}else {
									columnHTML +=  array[k] + "：<input type='radio' name=\""+column.getColumn_name()+"\" "+params.get("css")+"  />";
								}
							}
						}
					}
					map.put(column.getColumn_name(), columnHTML);
				}else if(column.getShow_page_type() == 2){//复选
					elUserPages = column.getElUserPages();
					for(int j=0;j<elUserPages.size();j++){
						if(elUserPages.get(j).getPageid() == pageid){
							elUserPage = elUserPages.get(j);
						}
					}
					if(elUserPage!=null){
						array = elUserPage.getRange().split("==");
						if(array!=null){
							for(int k=0;k<array.length;k++){
								//可以选择多个
								if(elUserPage.getDefault_select()!=null&&!elUserPage.getDefault_select().equals("")){
									default_select = elUserPage.getDefault_select().split("==");
								}
								if(default_select == null){//没有默认选中值
									columnHTML +=  array[k] + "：<input type='radio' name=\""+column.getColumn_name()+"\" "+params.get("css")+"  />";
								}else{//有默认选中值
									if(selected(array[k], default_select)){
										columnHTML +=  array[k] + "：<input type='radio' name=\""+column.getColumn_name()+"\" "+params.get("css")+" checked />";
									}else{
										columnHTML +=  array[k] + "：<input type='radio' name=\""+column.getColumn_name()+"\" "+params.get("css")+"  />";
									}
								}
							}
						}
					}
					map.put(column.getColumn_name(), columnHTML);
				}else if(column.getShow_page_type() == 3){//下拉选项
					elUserPages = column.getElUserPages();
					for(int j=0;j<elUserPages.size();j++){
						if(elUserPages.get(j).getPageid() == pageid){
							elUserPage = elUserPages.get(j);
						}
					}
					columnHTML += "<select name=\""+column.getColumn_name()+"\" "+params.get("css")+" onchange='this.value=this.options[this.selectedIndex].value;'>";
					if(elUserPage!=null){
						array = elUserPage.getRange().split("==");
						if(array!=null){
							for(int k=0;k<array.length;k++){
								if(selected(elUserPage.getDefault_select(), array)){
									columnHTML += "<option value='"+default_select[k]+"' selected>" + default_select[k] + "</option>";
								}else{
									columnHTML += "<option value='"+default_select[k]+"'>" + default_select[k] + "</option>";
								}
							}
						}
					}
					columnHTML += "</select>";
					map.put(column.getColumn_name(), columnHTML);
				}else if(column.getShow_page_type() == 4){//大文本
					columnHTML += "<textarea name=\""+column.getColumn_name()+"\" id=\""+column.getColumn_name()+"\" "+params.get("css")+"></textarea>";
					map.put(column.getColumn_name(), columnHTML);
				}else if(column.getShow_page_type() == 5){//数字
					columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+params.get("css")+" id=\""+column.getColumn_name()+"\" />";
					map.put(column.getColumn_name(), columnHTML);
				}else if(column.getShow_page_type() == 6){//图片
					if(params.get("css")!=null&&!params.get("css").equals("")){
						csses = String.valueOf(params.get("css")).split("==");
					}
					if(csses!=null){
						columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+csses[0]+" id=\""+column.getColumn_name()+"\" />";
						columnHTML += "<a onclick='addPic_"+column.getColumn_name()+"(this);' "+csses[1]+" >浏览资源库</a>";
					}else{
						columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\"  id=\""+column.getColumn_name()+"\" />";
						columnHTML += "<a onclick='addPic_"+column.getColumn_name()+"(this);' >浏览资源库</a>";
					}
					//控制点击'浏览资源库JS'
					columnHTML += "<script type=\"text/javascript\">";
					columnHTML += "function addPic_"+column.getColumn_name()+"(obj){ \n" 
									+ "width=1060;	\n"
									+ "height=500;	\n"
									+ "var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	\n"
									+ "var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	\n"
									+ " 	if(null==rv){	\n"
									+ " 	 	alert('您没选择图片！'); 	\n"
									+ " 	 	return ;		" + " 	 \n"
									+ "		} \n "
									+ " 	document.getElementById('"+column.getColumn_name()+"').value=rv;  \n " +
									"} \n";
					columnHTML += "</script>";
					map.put(column.getColumn_name(), columnHTML);
				}else if(column.getShow_page_type() == 7){//附件
					if(params.get("css")!=null&&!params.get("css").equals("")){
						csses = String.valueOf(params.get("css")).split("==");
					}
					if(csses!=null){
						columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+csses[0]+" id=\""+column.getColumn_name()+"\" />";
						columnHTML += "<a onclick='addPic_"+column.getColumn_name()+"(this);' "+csses[1]+" >浏览资源库</a>";
					}else{
						columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\"  id=\""+column.getColumn_name()+"\" />";
						columnHTML += "<a onclick='addPic_"+column.getColumn_name()+"(this);' >浏览资源库</a>";
					}
					//控制点击'浏览资源库JS'
					columnHTML += "<script type=\"text/javascript\">";
					columnHTML += "function addPic_"+column.getColumn_name()+"(obj){ \n" 
									+ "width=1060;	\n"
									+ "height=500;	\n"
									+ "var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	\n"
									+ "var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	\n"
									+ " 	if(null==rv){	\n"
									+ " 	 	alert('您没选择附件！'); 	\n"
									+ " 	 	return ;		" + " 	 \n"
									+ "		} \n "
									+ " 	document.getElementById('"+column.getColumn_name()+"').value=rv;  \n " +
									"} \n";
					columnHTML += "</script>";
				}else if(column.getShow_page_type() == 8){//视频
					if(params.get("css")!=null&&!params.get("css").equals("")){
						csses = String.valueOf(params.get("css")).split("==");
					}
					if(csses!=null){
						columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+csses[0]+" id=\""+column.getColumn_name()+"\" />";
						columnHTML += "<a onclick='addPic_"+column.getColumn_name()+"(this);' "+csses[1]+" >浏览资源库</a>";
					}else{
						columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\"  id=\""+column.getColumn_name()+"\" />";
						columnHTML += "<a onclick='addPic_"+column.getColumn_name()+"(this);' >浏览资源库</a>";
					}
					//控制点击'浏览资源库JS'
					columnHTML += "<script type=\"text/javascript\">";
					columnHTML += "function addPic_"+column.getColumn_name()+"(obj){ \n" 
									+ "width=1060;	\n"
									+ "height=500;	\n"
									+ "var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	\n"
									+ "var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	\n"
									+ " 	if(null==rv){	\n"
									+ " 	 	alert('您没选择视频！'); 	\n"
									+ " 	 	return ;		" + " 	 \n"
									+ "		} \n "
									+ " 	document.getElementById('"+column.getColumn_name()+"').value=rv;  \n " +
									"} \n";
					columnHTML += "</script>";
				}
			}
		}else if(pageid == 2){//修改页面
			columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+params.get("css")+" id=\""+column.getColumn_name()+"\" value='"+value+"' />";
		}else if(pageid == 3){//查看页面
			columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+params.get("css")+" id=\""+column.getColumn_name()+"\" value='"+value+"' />";
		}else if(pageid == 4){//注册页面
			columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+params.get("css")+" id=\""+column.getColumn_name()+"\"  />";
		}else if(pageid == 5){//个人修改页面
			columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+params.get("css")+" id=\""+column.getColumn_name()+"\" value='"+value+"' />";
		}else if(pageid == 6){//个人查看页面
			columnHTML = "<input type=\"text\" name=\""+column.getColumn_name()+"\" "+params.get("css")+" id=\""+column.getColumn_name()+"\" value='"+value+"' />";
		}
		return map;
	}
	
	public static String setValueByColumn(HttpServletRequest request,ELUserColumn column){
		if(column.getShow_page_type() == 0){//文本框
			return (String)request.getParameter(column.getColumn_name());
		}else if(column.getShow_page_type() == 1){//单选
			return (String)request.getParameter(column.getColumn_name());
		}else if(column.getShow_page_type() == 2){//复选
			String html = "";
			String[] values = (String[])request.getParameterValues(column.getColumn_name());
			if(values!=null && !values.equals("")){
				html = values.toString().replace(",", "==");
			}
			return html;
		}else if(column.getShow_page_type() == 3){//下拉选项
			return (String)request.getParameter(column.getColumn_name());
		}else if(column.getShow_page_type() == 4){//大文本
			return (String)request.getParameter(column.getColumn_name());
		}else if(column.getShow_page_type() == 5){//数字
			return (String)request.getParameter(column.getColumn_name());
		}else if(column.getShow_page_type() == 6){//图片
			return (String)request.getParameter(column.getColumn_name());
		}else if(column.getShow_page_type() == 7){//附件
			return (String)request.getParameter(column.getColumn_name());
		}else if(column.getShow_page_type() == 8){//视频
			return (String)request.getParameter(column.getColumn_name());
		}else{
			return "";
		}
	}

}
