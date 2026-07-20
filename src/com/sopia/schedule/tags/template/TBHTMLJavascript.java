package com.sopia.schedule.tags.template;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.entities.Tags;

/**
 * 解析添加页面和修改页面的javascript
 * @author Administrator
 *
 */
public class TBHTMLJavascript extends TagSupport{
	private String tablename;
	private List<Tags> list_tags ;
	private String itype;
	
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			list_tags = (List<Tags>) request.getAttribute("list_tags");
			tablename = (String)request.getAttribute("tablename");
			itype = getItype();
			if(itype.equals("add")){
				createJS_add(out,list_tags);
			}else if(itype.equals("update")){
				createJS_update(out,list_tags);
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	//添加页面提交的时候js验证信息
	public void createJS_add(JspWriter out,List<Tags> list_tags) throws IOException{
		out.println("<script type='text/javascript'>");
		out.println("function doSubmit(){");
		out.println("addToProduce(iii,type);");
		if(list_tags!=null&&list_tags.size()>0){
			for(Tags tags:list_tags){
				//添加的时候是否显示
				if(tags.getAdd_display()==1){
					//必填项
					if(tags.getRequired()==1){
						
						if(tags.getDisplay_type().equals("文本")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("实数") || tags.getDisplay_type().equals("整数")){
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+".value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+"只能为数字！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("百分比")){
							
						}else if(tags.getDisplay_type().equals("日期")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("下拉选项")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("单选")){
							
						}else if(tags.getDisplay_type().equals("复选")){
							
						}else if(tags.getDisplay_type().equals("城市")){
							out.println(
									"if(document.all."+tags.getColumn_name()+"_province.value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"'省'不能为空！！！');" +
									"	return false;" +
									"}"+
									"if(document.all."+tags.getColumn_name()+"_city.value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"'市'不能为空！！！');" +
									"	return false;" +
									"}"+
									"if(document.all."+tags.getColumn_name()+"_province.value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"'县'不能为空！！！');" +
									"	return false;" +
									"}"
							);
						}else if(tags.getDisplay_type().equals("大文本")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("富文本")){
							
						}else if(tags.getDisplay_type().equals("附件上传")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value!=''" +
										"		&&document.all."+tags.getColumn_name()+"_.value=='')" +
										"{" +
										"   alert('"+tags.getName_display()+"，路径不能为空！！！');" +
										"	return false;" +
								"}");
						}else if(tags.getDisplay_type().equals("图片")){
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+"_h.value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+",高只能为数字！！！');" +
									"	return false;" +
							"}");
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+"_w.value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+",宽只能为数字！！！');" +
									"	return false;" +
							"}");
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("相关字段")){
							
						}else if(tags.getDisplay_type().equals("相关负责人")){
							
						}
						
					}else {
						if(tags.getDisplay_type().equals("文本")){
							
						}else if(tags.getDisplay_type().equals("实数") || tags.getDisplay_type().equals("整数")){
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+".value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+"只能为数字！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("百分比")){
							
						}else if(tags.getDisplay_type().equals("日期")){
							
						}else if(tags.getDisplay_type().equals("下拉选项")){
							
						}else if(tags.getDisplay_type().equals("单选")){
							
						}else if(tags.getDisplay_type().equals("复选")){
							
						}else if(tags.getDisplay_type().equals("城市")){
							
						}else if(tags.getDisplay_type().equals("大文本")){
							
						}else if(tags.getDisplay_type().equals("富文本")){
							
						}else if(tags.getDisplay_type().equals("附件上传")){
							
						}else if(tags.getDisplay_type().equals("图片")){
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+"_h.value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+",高只能为数字！！！');" +
									"	return false;" +
							"}");
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+"_w.value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+",宽只能为数字！！！');" +
									"	return false;" +
							"}");
							
						}else if(tags.getDisplay_type().equals("相关字段")){
							
						}else if(tags.getDisplay_type().equals("相关负责人")){
							
						}
					}
				}
			}
		}
		out.println("}");
		out.println("</script>");
	}
	
	//修改页面提交的时候js验证信息
	public void createJS_update(JspWriter out,List<Tags> list_tags) throws IOException{
		out.println("<script type='text/javascript'>");
		out.println("function doSubmit(){");
		out.println("addToProduce();");
		out.println("addToProduce_();");
		
		if(list_tags!=null&&list_tags.size()>0){
			for(Tags tags:list_tags){
				//添加的时候是否显示
				if(tags.getUpdate_display()==1){
					//必填项
					if(tags.getRequired()==1){
						
						if(tags.getDisplay_type().equals("文本")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("实数") || tags.getDisplay_type().equals("整数")){
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+".value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+"只能为数字！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("百分比")){
							
						}else if(tags.getDisplay_type().equals("日期")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("下拉选项")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("单选")){
							
						}else if(tags.getDisplay_type().equals("复选")){
							
						}else if(tags.getDisplay_type().equals("城市")){
							out.println(
									"if(document.all."+tags.getColumn_name()+"_province.value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"'省'不能为空！！！');" +
									"	return false;" +
									"}"+
									"if(document.all."+tags.getColumn_name()+"_city.value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"'市'不能为空！！！');" +
									"	return false;" +
									"}"+
									"if(document.all."+tags.getColumn_name()+"_province.value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"'县'不能为空！！！');" +
									"	return false;" +
									"}"
							);
						}else if(tags.getDisplay_type().equals("大文本")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("富文本")){
							
						}else if(tags.getDisplay_type().equals("附件上传")){
							out.println(
									"if(document.all."+tags.getColumn_name()+".value!=''" +
										"		&&document.all."+tags.getColumn_name()+"_.value=='')" +
										"{" +
										"   alert('"+tags.getName_display()+"，路径不能为空！！！');" +
										"	return false;" +
								"}");
						}else if(tags.getDisplay_type().equals("图片")){
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+"_h.value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+",高只能为数字！！！');" +
									"	return false;" +
							"}");
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+"_w.value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+",宽只能为数字！！！');" +
									"	return false;" +
							"}");
							out.println(
									"if(document.all."+tags.getColumn_name()+".value=='')" +
									"{" +
									"   alert('"+tags.getName_display()+"不能为空！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("相关字段")){
							
						}else if(tags.getDisplay_type().equals("相关负责人")){
							
						}
						
					}else {
						if(tags.getDisplay_type().equals("文本")){
							
						}else if(tags.getDisplay_type().equals("实数") || tags.getDisplay_type().equals("整数")){
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+".value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+"只能为数字！！！');" +
									"	return false;" +
							"}");
						}else if(tags.getDisplay_type().equals("百分比")){
							
						}else if(tags.getDisplay_type().equals("日期")){
							
						}else if(tags.getDisplay_type().equals("下拉选项")){
							
						}else if(tags.getDisplay_type().equals("单选")){
							
						}else if(tags.getDisplay_type().equals("复选")){
							
						}else if(tags.getDisplay_type().equals("城市")){
							
						}else if(tags.getDisplay_type().equals("大文本")){
							
						}else if(tags.getDisplay_type().equals("富文本")){
							
						}else if(tags.getDisplay_type().equals("附件上传")){
							
						}else if(tags.getDisplay_type().equals("图片")){
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+"_h.value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+",高只能为数字！！！');" +
									"	return false;" +
							"}");
							out.println(
									"if(isNaN(document.all."+tags.getColumn_name()+"_w.value))" +//true:非数字
									"{" +
									"   alert('"+tags.getName_display()+",宽只能为数字！！！');" +
									"	return false;" +
							"}");
							
						}else if(tags.getDisplay_type().equals("相关字段")){
							
						}else if(tags.getDisplay_type().equals("相关负责人")){
							
						}
					}
				}
			}
		}
		out.println("}");
		out.println("</script>");
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<Tags> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<Tags> list_tags) {
		this.list_tags = list_tags;
	}

	public String getItype() {
		return itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

}
