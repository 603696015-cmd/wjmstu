package com.sopia.shebeipinggu.tags;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest; 
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport; 

import com.sopia.common.ElTag;
import com.sopia.common.BeanGenerator.CglibBean; 
import com.sopia.common.BeanGenerator.TableCreateBean;

public class GET extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			List<TableCreateBean> TC_Infos = (List<TableCreateBean>) request.getAttribute("TC_Infos");  
			int TypeView = (Integer) request.getAttribute("TypeView"); 
			//TypeView 页面显示类型
			 if(TypeView == 1){ // 1 . 可提交的显示
				 writeChilds(out, TC_Infos); 
			 }else if(TypeView == 2){  // 2 . 可修改的显示
				 writeChilds2(out, TC_Infos);
			 }else if(TypeView == 3){  // 3 . 查看显示
				 writeChilds3(out, TC_Infos); 
			 }else if(TypeView == 4){  // 4 . 预览
				 writeChilds4(out, TC_Infos); 
			 }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	// 1 . 可提交的显示
	public void writeChilds(JspWriter out, Object obj) throws Exception { 
		List<TableCreateBean> TC_Infos = (List<TableCreateBean>) obj;
		String [] cview;
		String iname = getIname();
		String tableName = TC_Infos.get(0).getTableName();
		//单行文本==多行文本==下拉列表==数字类型==小数==日期==单选==复选
		for(int i = 0 ; TC_Infos.size() > i ; i++){
			if(TC_Infos.get(i).getColumn_name().equals(iname)){
				if(TC_Infos.get(i).getCview().equals("单行文本")||TC_Infos.get(i).getCview().equals("数字类型")||TC_Infos.get(i).getCview().equals("小数")){
					out.println("<input onKeyUp='qiuji_keyup();'type=\"text\" size=\"20\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >");
				}else if(TC_Infos.get(i).getCview().equals("多行文本")){   
					out.println("<textarea name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" cols=\"40\" rows=\"5\"></textarea>"); 
				}else if(TC_Infos.get(i).getCview().equals("下拉列表")){
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-"); 
					out.println("<SELECT id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\">"); 
						for(int x = 0 ; cview.length > x ; x++){
							out.println("<option value=\""+cview[x]+"\">"+cview[x]+"</option>");
						}
					out.println("</SELECT>"); 
				}else if(TC_Infos.get(i).getCview().equals("日期")){ 
					out.println("<input type=\"text\" size=\"16\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"-riqi"+"\" onclick=\"setday(this)\">");
				}else if(TC_Infos.get(i).getCview().equals("单选")){ 
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-");
					for(int x = 0 ; cview.length > x ; x++){
						out.println("<input type=\"radio\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+cview[x]+"\" />"+cview[x]);
					}
				}else if(TC_Infos.get(i).getCview().equals("复选")){ 
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-");
					for(int x = 0 ; cview.length > x ; x++){
						out.println("<input type=\"checkbox\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+cview[x]+"\" />"+cview[x]);
					}
				}else if(TC_Infos.get(i).getCview().equals("图片")){   
					out.println("<input type=\"text\" size=\"20\" id=\"pic\" size=\"60\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >" +
					"<a href=\"javascript:setUrl('pic');\" class=\"textbg\">浏览资源库</a>");
				}else if(TC_Infos.get(i).getCview().equals("富文本")){   
					out.println("<textarea name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"-BLOB"+"\" id=\"content\" cols=\"60\" rows=\"7\" " +
							"style=\"width: 100%; height: 440px;; visibility: hidden;\"></textarea>");
				}
			}
		} 
	} 
	// 2 . 可修改的显示
	public void writeChilds2(JspWriter out, Object obj) throws Exception { 
		List<TableCreateBean> TC_Infos = (List<TableCreateBean>) obj;
		String [] cview;
		String iname = getIname();
		String tableName = TC_Infos.get(0).getTableName();
		//单行文本==多行文本==下拉列表==数字类型==小数==日期==单选==复选
		for(int i = 0 ; TC_Infos.size() > i ; i++){
			if(TC_Infos.get(i).getColumn_name().equals(iname)){
				if(TC_Infos.get(i).getCview().equals("单行文本")||TC_Infos.get(i).getCview().equals("数字类型")||TC_Infos.get(i).getCview().equals("小数")){
					out.println("<input type=\"text\" size=\"20\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+TC_Infos.get(i).getData_value()+"\" >");
				}else if(TC_Infos.get(i).getCview().equals("多行文本")){   
					out.println("<textarea id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" cols=\"40\" rows=\"5\"  >"+TC_Infos.get(i).getData_value()+"</textarea>"); 
				}else if(TC_Infos.get(i).getCview().equals("下拉列表")){
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-"); 
					out.println("<SELECT id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\">"); 
						for(int x = 0 ; cview.length > x ; x++){
							if(cview[x].equals(TC_Infos.get(i).getData_value()))
								out.println("<option value=\""+cview[x]+"\" selected=\"selected\">"+cview[x]+"</option>");
							else
								out.println("<option value=\""+cview[x]+"\">"+cview[x]+"</option>"); 
						}
					out.println("</SELECT>"); 
				}else if(TC_Infos.get(i).getCview().equals("日期")){ 
					out.println("<input type=\"text\" size=\"16\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"-riqi"+"\" onclick=\"setday(this)\" value=\""+TC_Infos.get(i).getData_value()+"\" >");
				}else if(TC_Infos.get(i).getCview().equals("单选")){ 
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-");
					for(int x = 0 ; cview.length > x ; x++){
						if(cview[x].equals(TC_Infos.get(i).getData_value()))
							out.println("<input type=\"radio\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+cview[x]+"\"  checked=\"true\" />"+cview[x]);
						else
							out.println("<input type=\"radio\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+cview[x]+"\" />"+cview[x]);
					}
				}else if(TC_Infos.get(i).getCview().equals("复选")){ 
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-");
					for(int x = 0 ; cview.length > x ; x++){
						if(cview[x].equals(TC_Infos.get(i).getData_value()))
							out.println("<input type=\"checkbox\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" checked=\"true\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >"+cview[x]);
						else
							out.println("<input type=\"checkbox\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >"+cview[x]);
					}
				}else if(TC_Infos.get(i).getCview().equals("图片")){   
					out.println("<input type=\"text\" size=\"20\" id=\"pic\" size=\"60\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >" +
					"<a href=\"javascript:setUrl('pic');\" class=\"textbg\">浏览资源库</a>");
				}else if(TC_Infos.get(i).getCview().equals("富文本")){   
					out.println("<div style=\"text-align: center; width: 100%\">" +
							"<textarea name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"-BLOB"+"\" id=\"content\" cols=\"60\" rows=\"7\" " +
							"style=\"width: 100%; height: 440px;; visibility: hidden;\">"+TC_Infos.get(i).getData_value()+"</textarea></div>");
				}
			}
		}  
	} 
	// 3 . 查看显示
	public void writeChilds3(JspWriter out, Object obj) throws Exception { 
		List<TableCreateBean> TC_Infos = (List<TableCreateBean>) obj;
		String [] cview;
		String iname = getIname();
		String tableName = TC_Infos.get(0).getTableName();
		//单行文本==多行文本==下拉列表==数字类型==小数==日期==单选==复选
		for(int i = 0 ; TC_Infos.size() > i ; i++){
			if(TC_Infos.get(i).getColumn_name().equals(iname)){
				String value = TC_Infos.get(i).getData_value();
				if(value == null) value = "";
				if(TC_Infos.get(i).getCview().equals("单行文本")||TC_Infos.get(i).getCview().equals("数字类型")||TC_Infos.get(i).getCview().equals("小数")){
					out.println("<span id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" alt=\""+TC_Infos.get(i).getColumn_name()+"\">"+value+"</span>");
				}else if(TC_Infos.get(i).getCview().equals("多行文本")){   
					out.println("<span id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" alt=\""+TC_Infos.get(i).getColumn_name()+"\">"+value+"</span>"); 
				}else if(TC_Infos.get(i).getCview().equals("下拉列表")){
					out.println("<span id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" alt=\""+TC_Infos.get(i).getColumn_name()+"\">"+value+"</span>"); 
				}else if(TC_Infos.get(i).getCview().equals("日期")){ 
					out.println("<span id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" alt=\""+TC_Infos.get(i).getColumn_name()+"\">"+value+"</span>");
				}else if(TC_Infos.get(i).getCview().equals("单选")){ 
					out.println("<span id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" alt=\""+TC_Infos.get(i).getColumn_name()+"\">"+value+"</span>"); 
				}else if(TC_Infos.get(i).getCview().equals("复选")){ 
					out.println("<span id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" alt=\""+TC_Infos.get(i).getColumn_name()+"\">"+value+"</span>"); 
				}else if(TC_Infos.get(i).getCview().equals("图片")){   
					out.println("<img src=\""+TC_Infos.get(i).getData_value()+"\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" alt=\""+TC_Infos.get(i).getColumn_name()+"\">"); 
				}else if(TC_Infos.get(i).getCview().equals("富文本")){   
					out.println(value); 
				}
			}
		}   
	} 
//	public void writeChilds3(JspWriter out, Object obj) throws Exception { 
//		List<TableCreateBean> TC_Infos = (List<TableCreateBean>) obj;
//		String [] cview;
//		String iname = getIname();
//		String tableName = TC_Infos.get(0).getTableName();
//		//单行文本==多行文本==下拉列表==数字类型==小数==日期==单选==复选
//		for(int i = 0 ; TC_Infos.size() > i ; i++){
//			if(TC_Infos.get(i).getColumn_name().equals(iname)){
//				if(TC_Infos.get(i).getCview().equals("单行文本")||TC_Infos.get(i).getCview().equals("数字类型")||TC_Infos.get(i).getCview().equals("小数")){
//					out.println("<input type=\"text\" size=\"20\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+TC_Infos.get(i).getData_value()+"\" readOnly>");
//				}else if(TC_Infos.get(i).getCview().equals("多行文本")){   
//					out.println("<textarea id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" cols=\"40\" rows=\"5\"  readOnly>"+TC_Infos.get(i).getData_value()+"</textarea>"); 
//				}else if(TC_Infos.get(i).getCview().equals("下拉列表")){
//					out.println("<input type=\"text\" size=\"20\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+TC_Infos.get(i).getData_value()+"\" readOnly>");
////					cview = null;
////					cview = TC_Infos.get(i).getCview_value().split("-V-"); 
////					out.println("<SELECT id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\">"); 
////						for(int x = 0 ; cview.length > x ; x++){
////							if(cview[x].equals(TC_Infos.get(i).getData_value()))
////								out.println("<option value=\""+cview[x]+"\" selected=\"selected\">"+cview[x]+"</option>");
////							else
////								out.println("<option value=\""+cview[x]+"\">"+cview[x]+"</option>"); 
////						}
////					out.println("</SELECT>"); 
//				}else if(TC_Infos.get(i).getCview().equals("日期")){ 
//					out.println("<input type=\"text\" size=\"16\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"-riqi"+"\" onclick=\"setday(this)\" value=\""+TC_Infos.get(i).getData_value()+"\" readOnly>");
//				}else if(TC_Infos.get(i).getCview().equals("单选")){ 
//					out.println("<input type=\"text\" size=\"20\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+TC_Infos.get(i).getData_value()+"\" readOnly>");
////					cview = null;
////					cview = TC_Infos.get(i).getCview_value().split("-V-");
////					for(int x = 0 ; cview.length > x ; x++){
////						if(cview[x].equals(TC_Infos.get(i).getData_value()))
////							out.println("<input type=\"radio\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+cview[x]+"\"  checked=\"true\" readOnly/>"+cview[x]);
////						else
////							out.println("<input type=\"radio\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+cview[x]+"\" readOnly/>"+cview[x]);
////					}
//				}else if(TC_Infos.get(i).getCview().equals("复选")){ 
//					out.println("<input type=\"text\" size=\"20\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+TC_Infos.get(i).getData_value()+"\" readOnly>");
////					cview = null;
////					cview = TC_Infos.get(i).getCview_value().split("-V-");
////					for(int x = 0 ; cview.length > x ; x++){
////						if(cview[x].equals(TC_Infos.get(i).getData_value()))
////							out.println("<input type=\"checkbox\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" checked=\"true\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" readOnly>"+cview[x]);
////						else
////							out.println("<input type=\"checkbox\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" readOnly>"+cview[x]);
////					}
//				}else if(TC_Infos.get(i).getCview().equals("图片")){   
//					out.println("<input type=\"text\" size=\"20\" id=\"pic\" size=\"60\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >" +
//					"<a href=\"javascript:setUrl('pic');\" class=\"textbg\">浏览资源库</a>");
//				}else if(TC_Infos.get(i).getCview().equals("富文本")){   
//					out.println("<div style=\"text-align: center; width: 100%\">" +
//							"<textarea name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"-BLOB"+"\" id=\"content\" cols=\"60\" rows=\"7\" " +
//							"style=\"width: 100%; height: 440px;; visibility: hidden;\">"+TC_Infos.get(i).getData_value()+"</textarea></div>");
//				}
//			}
//		}  
//	} 

	// 4 . 预览
	public void writeChilds4(JspWriter out, Object obj) throws Exception { 
		List<TableCreateBean> TC_Infos = (List<TableCreateBean>) obj;
		String [] cview;
		String iname = getIname();
		String tableName = TC_Infos.get(0).getTableName();
		//单行文本==多行文本==下拉列表==数字类型==小数==日期==单选==复选
		for(int i = 0 ; TC_Infos.size() > i ; i++){
			if(TC_Infos.get(i).getColumn_name().equals(iname)){
				if(TC_Infos.get(i).getCview().equals("单行文本")||TC_Infos.get(i).getCview().equals("数字类型")||TC_Infos.get(i).getCview().equals("小数")){
					out.println("<input type=\"text\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" size=\"20\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >");
				}else if(TC_Infos.get(i).getCview().equals("多行文本")){   
					out.println("<textarea name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" cols=\"40\" rows=\"5\"></textarea>"); 
				}else if(TC_Infos.get(i).getCview().equals("下拉列表")){
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-"); 
					out.println("<SELECT id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\">"); 
						for(int x = 0 ; cview.length > x ; x++){
							out.println("<option value=\""+cview[x]+"\">"+cview[x]+"</option>");
						}
					out.println("</SELECT>"); 
				}else if(TC_Infos.get(i).getCview().equals("日期")){ 
					out.println("<input type=\"text\" size=\"16\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"-riqi"+"\" onclick=\"setday(this)\">");
				}else if(TC_Infos.get(i).getCview().equals("单选")){ 
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-");
					for(int x = 0 ; cview.length > x ; x++){
						out.println("<input type=\"radio\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" value=\""+cview[x]+"\" />"+cview[x]);
					}
				}else if(TC_Infos.get(i).getCview().equals("复选")){ 
					cview = null;
					cview = TC_Infos.get(i).getCview_value().split("-V-");
					for(int x = 0 ; cview.length > x ; x++){
						out.println("<input type=\"checkbox\" id=\"CSS_"+TC_Infos.get(i).getColumn_name()+"\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >"+cview[x]);
					}
				}else if(TC_Infos.get(i).getCview().equals("图片")){   
					out.println("<input type=\"text\" size=\"20\" id=\"pic\" size=\"60\" name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"- "+"\" >" +
					"<a href=\"javascript:setUrl('pic');\" class=\"textbg\">浏览资源库</a>");
				}else if(TC_Infos.get(i).getCview().equals("富文本")){   
					out.println("<div style=\"text-align: center; width: 100%\">" +
							"<textarea name=\""+tableName+"."+TC_Infos.get(i).getColumn_name()+"-BLOB"+"\" id=\"content\" cols=\"60\" rows=\"7\" " +
							"style=\"width: 100%; height: 440px;; visibility: hidden;\"></textarea></div>");
				}
			}
		} 
	} 
}
