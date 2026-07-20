package com.sopia.schedule.tags.template;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.entities.Tags;

/**
 * 添加页面解析字段HTML
 * @author Administrator
 *
 */
public class TBHTMLAdd extends TagSupport{
	
	private String iname;
	private String tablename;
	private List<Tags> list_tags ;
	
	private String uid;
	private String username;
	private String kk;
	private String nowdate;
	
	private List<Integer> list_ricktext;
	
	
	/**
     * 得到给定日期N天后的日期
     * @param num
     * @return
     */
    public String do4(String datestr,int num) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try{
            Date getdate = format.parse(datestr);
            long time = getdate.getTime()+(1000L * 60 * 60 * 24 * num);            
            Date date = new Date();
            if (time > 0) {
                date.setTime(time);
            }
            return format.format(date);
        }catch(Exception e){}
        return "";
    }
	
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			list_tags = (List<Tags>) request.getAttribute("list_tags");
			iname = getIname();
			tablename = (String)request.getAttribute("tablename");
			
			username = (String) request.getAttribute("username");
			uid = (String) request.getAttribute("uid");
			kk = (String)request.getAttribute("kk");
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			nowdate=df.format(new Date());
			
			list_ricktext = new ArrayList<Integer>();
			
			for(int i=0;i<list_tags.size();i++){
				if(list_tags.get(i).getView_display()==1){
					if(list_tags.get(i).getDisplay_type().equals("富文本")){
						out.println("<script type='text/javascript'>" +
								"	function myload(){" +
								"	var oFCKeditor = new FCKeditor('content') ; "+
								"	oFCKeditor.BasePath = 'editor/' ;"	+
								"	oFCKeditor.Height = 400;"	+
								"	oFCKeditor.Width = 980;"	+
								"	oFCKeditor.ReplaceTextarea();" +
								"	} " +
								" </script> ");
						list_ricktext.add(i);
						break;
					}
				}
			}
			
			if(list_tags!=null&&list_tags.size()>0){
				for(int i=0;i<list_tags.size();i++){
					if(list_tags.get(i)!=null&&!list_tags.get(i).getColumn_name().equals("")){
						//遍历各种类型的字段并生成HTML
						if(list_tags.get(i).getColumn_name().equals(iname)){
							createHTML(list_tags.get(i),i,out);
							break;
						}
					}
				}
			}
			
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	//生成HTML代码
	public void createHTML(Tags tags,int i,JspWriter out) throws IOException{
		String mark = "";
		if(tags.getMark() != null && !tags.getMark().equals("")){
			mark = tags.getMark();
		}
		
		if(tags.getDisplay_type().equals("文本")){
			if(tags.getRequired() == 1){
				if(tags.getWritible() == 1){
					out.println("<input  type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"' /><span style='color:red'>*</span><span>"+mark+"</span>");
				}else {
					out.println("<input  type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"' /><span style='color:red'>*</span><span>"+mark+"</span><span style='color:red'>不可填写</span>");
				}
			}else{
				if(tags.getWritible() == 1){
					out.println("<input  type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"' /><span>"+mark+"</span>");
				}else {
					out.println("<input  type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"' /><span>"+mark+"</span><span style='color:red'>不可填写</span>");
				}
			}
		}else if(tags.getDisplay_type().equals("实数") || tags.getDisplay_type().equals("整数")){
			if(tags.getBiaojianqiuhe_check() == 1){
				if(tags.getRequired() == 1){
					if(tags.getWritible() == 1){
						out.println("<input type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"'  onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"+mark+"</span>");
					}else {
						out.println("<input readOnly type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"+mark+"</span>&nbsp;&nbsp;<span style='color:red'>不可填写</span>");
					}
				}else {
					if(tags.getWritible() == 1){
						out.println("<input type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"'  onclick='biaojianqiuhe_calculate_(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"+mark+"</span>");
					}else {
						out.println("<input readOnly type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"+mark+"</span>&nbsp;&nbsp;<span style='color:red'>不可填写</span>");
					}
				}
			}else {
				if(tags.getRequired() == 1){
					if(tags.getWritible() == 1){
						out.println("<input type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"'  onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"+mark+"</span>");
					}else {
						out.println("<input readOnly type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"+mark+"</span>&nbsp;&nbsp;<span style='color:red'>不可填写</span>");
					}
				}else {
					if(tags.getWritible() == 1){
						out.println("<input type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"'  onclick='jisuan_in(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"+mark+"</span>");
					}else {
						out.println("<input readOnly type='text' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"+mark+"</span>&nbsp;&nbsp;<span style='color:red'>不可填写</span>");
					}
				}
			}
		}else if(tags.getDisplay_type().equals("百分比")){
			if(tags.getTime_jindu_ids() != null && !tags.getTime_jindu_ids().equals("")){
				if(tags.getRequired() == 1){
					out.println("<input readOnly id='value_jindutiao__"+tags.getColumn_name()+"' type='text' name='"+tags.getColumn_name()+"' onKeyUp='getJindutiao("+tags.getJindutiao()+",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"+tags.getColumn_name()+"'>%</span><span style='color:red'>*</span><span>"+mark+"</span>");
				}else{
					out.println("<input readOnly id='value_jindutiao__"+tags.getColumn_name()+"' type='text' name='"+tags.getColumn_name()+"' onKeyUp='getJindutiao("+tags.getJindutiao()+",this);' onclick='time_columns(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"+tags.getColumn_name()+"'>%</span><span>"+mark+"</span>");
				}
			
			}else if(tags.getYewu_jindu_ids() != null && !tags.getYewu_jindu_ids().equals("")) {
				if(tags.getRequired() == 1){
					out.println("<input readOnly id='value_jindutiao__"+tags.getColumn_name()+"' type='text' name='"+tags.getColumn_name()+"'  onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"+tags.getColumn_name()+"'>%</span><span style='color:red'>*</span><span>"+mark+"</span>");
				}else {
					out.println("<input readOnly id='value_jindutiao__"+tags.getColumn_name()+"' type='text' name='"+tags.getColumn_name()+"'  onclick='calculate_yewu_jindu(this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"+tags.getColumn_name()+"'>%</span><span>"+mark+"</span>");
				}
			}else {
				if(tags.getRequired() == 1){
					out.println("<input id='value_jindutiao__"+tags.getColumn_name()+"' type='text' name='"+tags.getColumn_name()+"'  onKeyUp='getJindutiao("+tags.getJindutiao()+",this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"+tags.getColumn_name()+"'>%</span><span style='color:red'>*</span><span>"+mark+"</span>");
				}else {
					out.println("<input id='value_jindutiao__"+tags.getColumn_name()+"' type='text' name='"+tags.getColumn_name()+"'  onKeyUp='getJindutiao("+tags.getJindutiao()+",this);' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red;' id='span_red__"+tags.getColumn_name()+"'>%</span><span>"+mark+"</span>");
				}
			}
			
			if(tags.getJindutiao() == 1){//显示进度条
				out.println("<div id='jindutiao_div__"+tags.getColumn_name()+"' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='0%'  id='show_jindutiao__"+tags.getColumn_name()+"' /></div>");
			}
		}else if(tags.getDisplay_type().equals("日期")){
			String value="";
			if(tags.getDefault_value()!=null){
				if(tags.getDefault_value().contains("_"))
					value="value='"+do4(nowdate,Integer.parseInt(tags.getDefault_value().split("_")[1]))+"'";
				else 
					value="value='"+do4(nowdate,0)+"'";
			}
			
			if(tags.getWritible() == 1){
				if(tags.getRequired() == 1){
					out.println("<input class='Wdate'  readonly='readonly' type='text'  name='"+tags.getColumn_name()+"' " +
							" onClick='setday(this)' id='"+tags.getColumn_name()+"'  "+value+"  /><span style='color:red'>*</span><span>"+mark+"</span>");
				}else {
					out.println("<input class='Wdate'  readonly='readonly' type='text'  name='"+tags.getColumn_name()+"' " +
							" onClick='setday(this)' id='"+tags.getColumn_name()+"' "+value+" /><span>"+mark+"</span>");
				}
			}else {
				if(tags.getRequired() == 1){
					out.println("<input class='Wdate'  readonly='readonly' type='text'  name='"+tags.getColumn_name()+"' " +
							"  id='"+tags.getColumn_name()+"'    "+value+" /><span style='color:red'>*</span><span>"+mark+"</span><span style='color:red'>不可填写</span>");
				}else{
					
					out.println("<input class='Wdate'  readonly='readonly' type='text'  name='"+tags.getColumn_name()+"' " +
							"  id='"+tags.getColumn_name()+"'    "+value+" /><span>"+mark+"</span><span style='color:red'>不可填写</span>");
				}
			}
		}else if(tags.getDisplay_type().equals("下拉选项")){
			String str[]=tags.getDefault_value().split("==");
			String str_select_head="";
			
			if(tags.getWritible() == 1){
				if(tags.getRequired() == 1){
					str_select_head="<select id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"' ><span style='color:red'>*</span><span>"+mark+"</span>";
				}else{
					str_select_head="<select id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"' ><span>"+mark+"</span>";
				}
			}else {
				if(tags.getRequired() == 1){
					str_select_head="<select id='"+tags.getColumn_name()+"' disabled name='"+tags.getColumn_name()+"' ><span style='color:red'>*</span><span>"+mark+"</span><span style='color:red'>不可选择</span>";
				}else{
					str_select_head="<select id='"+tags.getColumn_name()+"' disabled name='"+tags.getColumn_name()+"' ><span>"+mark+"</span><span style='color:red'>不可选择</span>";
				}
			}
			
			String str_default="<option value=''>请选择</option>";
			String str_select_tail="</select>";
			String str_select_body="";
			if(str.length>0)
			{
				for(int j=0;j<str.length;j++)
				{
					
					str_select_body+="<option value='"+str[j]+"'>"+str[j]+"</option>";
				}
			}
			
			out.println(str_select_head+str_default+str_select_body+str_select_tail);
			
		}else if(tags.getDisplay_type().equals("单选")){
			String str[] = null;
			if(tags.getDefault_value() != null &&
					!tags.getDefault_value().equals("")){
				str=tags.getDefault_value().split("==");
				String radio_body="";
				if(str.length>0)
				{
					for(int j=0;j<str.length;j++)
					{
						if(tags.getWritible() == 1){
							radio_body+="<input type='radio' name='"+tags.getColumn_name()+"' value='"+str[j]+"'>"+str[j];
						}else {
							radio_body+="<input disabled type='radio' name='"+tags.getColumn_name()+"' value='"+str[j]+"'>"+str[j];
						}
						
					}
				}
				if(tags.getRequired() == 1){
					out.println(radio_body + "<span style='color:red'>*</span><span>"+mark+"</span>");
				}else{
					out.println(radio_body + "<span>"+mark+"</span>");
				}
			}
		}else if(tags.getDisplay_type().equals("复选")){
			String str[] = null;
			if(tags.getDefault_value() != null &&
					!tags.getDefault_value().equals("")){
				str=tags.getDefault_value().split("==");
				String checkbox_body="";
				if(str.length>0)
				{
					for(int j=0;j<str.length;j++)
					{
						if(tags.getWritible() == 1){
							checkbox_body+="<input type='checkbox' name='"+tags.getColumn_name()+"' value='"+str[j]+"'>"+str[j];
						}else {
							checkbox_body+="<input disabled type='checkbox' name='"+tags.getColumn_name()+"' value='"+str[j]+"'>"+str[j];
						}
						
					}
				}
//				
				if(tags.getRequired() == 1){
					out.println(checkbox_body + "<span style='color:red'>*</span><span>"+mark+"</span>");
				}else{
					out.println(checkbox_body + "<span>"+mark+"</span>");
				}
			}
		}else if(tags.getDisplay_type().equals("城市")){
			String province =  "";
			String city = "";
			String county = "";
			if(tags.getDefault_value()!=null && !tags.getDefault_value().equals(""))
			{
				String textcontrol[]=tags.getDefault_value().split(" ");
				province = textcontrol[0];
				city = textcontrol[1];
				county = textcontrol[2];
				
			}
			out.println("<input type='hidden' id='"+tags.getColumn_name()+"' name='"+tags.getColumn_name()+"' >");
			
			String output = "";
			String output_not_required = "";
			String output_disabled = "";
			if(province != "" && city != "" && county != ""){
				output = "" +
				"<select id='"+tags.getColumn_name()+"_province'  name='"+tags.getColumn_name()+"_province' onchange=\"changeProvince('"+tags.getColumn_name()+"');\" style=\"width:100\"> " +
		         "<option id='"+tags.getColumn_name()+"_option_in_province' >  "+
		         	province +
		         "</option>" +  
		         "</select>" +
				"<select id='"+tags.getColumn_name()+"_city' name='"+tags.getColumn_name()+"_city' onchange=\"changeCity('"+tags.getColumn_name()+"');\" style=\"width:100\" >  "+
		        "<option id='"+tags.getColumn_name()+"_option_in_city'  >  "+
		        	city +
		         "</option>"  +
		         "</select>" +
				"<select id='"+tags.getColumn_name()+"_county' name='"+tags.getColumn_name()+"_county' onchange=\"changeCounty('"+tags.getColumn_name()+"');\" style=\"width:100\"> "+ 
		         "<option id='"+tags.getColumn_name()+"_option_in_county' >"  +
		         	county +
		         "</option>"+  
				"</select>" + 
				"<span style='color:red'>*</span><span>"+mark+"</span>";
				
				output_not_required = "" +
				"<select id='"+tags.getColumn_name()+"_province'  name='"+tags.getColumn_name()+"_province' onchange=\"changeProvince('"+tags.getColumn_name()+"');\" style=\"width:100\"> " +
		         "<option id='"+tags.getColumn_name()+"_option_in_province' >  "+
		         province+
		         "</option>" +  
		         "</select>" +
				"<select id='"+tags.getColumn_name()+"_city' name='"+tags.getColumn_name()+"_city' onchange=\"changeCity('"+tags.getColumn_name()+"');\" style=\"width:100\" >  "+
		        "<option id='"+tags.getColumn_name()+"_option_in_city'  >  "+
		        city +   
		         "</option>"  +
		         "</select>" +
				"<select id='"+tags.getColumn_name()+"_county' name='"+tags.getColumn_name()+"_county' onchange=\"changeCounty('"+tags.getColumn_name()+"');\" style=\"width:100\"> "+ 
		         "<option id='"+tags.getColumn_name()+"_option_in_county' >"  +
		         county + 
		         "</option>"+  
				"</select>" + 
				"<span>"+mark+"</span>";
				
				output_disabled = "" +
				"<select disabled id='"+tags.getColumn_name()+"_province'  name='"+tags.getColumn_name()+"_province' onchange=\"changeProvince('"+tags.getColumn_name()+"');\" style=\"width:100\"> " +
		         "<option id='"+tags.getColumn_name()+"_option_in_province' >  "+
		         province +
		         "</option>" +  
		         "</select>" +
				"<select disabled id='"+tags.getColumn_name()+"_city' name='"+tags.getColumn_name()+"_city' onchange=\"changeCity('"+tags.getColumn_name()+"');\" style=\"width:100\" >  "+
		        "<option id='"+tags.getColumn_name()+"_option_in_city'  >  "+
		        city + 
		         "</option>"  +
		         "</select>" +
				"<select disabled id='"+tags.getColumn_name()+"_county' name='"+tags.getColumn_name()+"_county' onchange=\"changeCounty('"+tags.getColumn_name()+"');\" style=\"width:100\"> "+ 
		         "<option id='"+tags.getColumn_name()+"_option_in_county' >"  +
		         county + 
		         "</option>"+  
				"</select>"+
				"<span>"+mark+"</span><span style='color:red'>不可填写</span>";
			}else {
				output = "" +
				"<select id='"+tags.getColumn_name()+"_province'  name='"+tags.getColumn_name()+"_province' onchange=\"changeProvince('"+tags.getColumn_name()+"');\" style=\"width:100\"> " +
		         "<option id='"+tags.getColumn_name()+"_option_in_province' >  "+
		         	"请选择省" +
		         "</option>" +  
		         "</select>" +
				"<select id='"+tags.getColumn_name()+"_city' name='"+tags.getColumn_name()+"_city' onchange=\"changeCity('"+tags.getColumn_name()+"');\" style=\"width:100\" >  "+
		        "<option id='"+tags.getColumn_name()+"_option_in_city'  >  "+
		        "请选择市" +
		         "</option>"  +
		         "</select>" +
				"<select id='"+tags.getColumn_name()+"_county' name='"+tags.getColumn_name()+"_county' onchange=\"changeCounty('"+tags.getColumn_name()+"');\" style=\"width:100\"> "+ 
		         "<option id='"+tags.getColumn_name()+"_option_in_county' >"  +
		         "请选择县" +
		         "</option>"+  
				"</select>" + 
				"<span style='color:red'>*</span><span>"+mark+"</span>";
				
				output_not_required = "" +
				"<select id='"+tags.getColumn_name()+"_province'  name='"+tags.getColumn_name()+"_province' onchange=\"changeProvince('"+tags.getColumn_name()+"');\" style=\"width:100\"> " +
		         "<option id='"+tags.getColumn_name()+"_option_in_province' >  "+
		         "请选择省" +
		         "</option>" +  
		         "</select>" +
				"<select id='"+tags.getColumn_name()+"_city' name='"+tags.getColumn_name()+"_city' onchange=\"changeCity('"+tags.getColumn_name()+"');\" style=\"width:100\" >  "+
		        "<option id='"+tags.getColumn_name()+"_option_in_city'  >  "+
		        "请选择市" + 
		         "</option>"  +
		         "</select>" +
				"<select id='"+tags.getColumn_name()+"_county' name='"+tags.getColumn_name()+"_county' onchange=\"changeCounty('"+tags.getColumn_name()+"');\" style=\"width:100\"> "+ 
		         "<option id='"+tags.getColumn_name()+"_option_in_county' >"  +
		         "请选择县" +
		         "</option>"+  
				"</select>" + 
				"<span>"+mark+"</span>";
				
				output_disabled = "" +
				"<select disabled id='"+tags.getColumn_name()+"_province'  name='"+tags.getColumn_name()+"_province' onchange=\"changeProvince('"+tags.getColumn_name()+"');\" style=\"width:100\"> " +
		         "<option id='"+tags.getColumn_name()+"_option_in_province' >  "+
		         "请选择省" +
		         "</option>" +  
		         "</select>" +
				"<select disabled id='"+tags.getColumn_name()+"_city' name='"+tags.getColumn_name()+"_city' onchange=\"changeCity('"+tags.getColumn_name()+"');\" style=\"width:100\" >  "+
		        "<option id='"+tags.getColumn_name()+"_option_in_city'  >  "+
		        "请选择市" + 
		         "</option>"  +
		         "</select>" +
				"<select disabled id='"+tags.getColumn_name()+"_county' name='"+tags.getColumn_name()+"_county' onchange=\"changeCounty('"+tags.getColumn_name()+"');\" style=\"width:100\"> "+ 
		         "<option id='"+tags.getColumn_name()+"_option_in_county' >"  +
		         "请选择县" +
		         "</option>"+  
				"</select>"+
				"<span>"+mark+"</span><span style='color:red'>不可填写</span>";
			}
			
			
			if(tags.getRequired() == 1){
				if(tags.getWritible() == 1){
					out.println(output);
				}else {
					out.println(output_disabled);
				}
			}else{
				if(tags.getWritible() == 1){
					out.println(output_not_required);
				}else {
					out.println(output_disabled);
				}
			}
		}else if(tags.getDisplay_type().equals("大文本")){
			if(tags.getWritible() == 1){
				out.println("<textarea  id='"+tags.getColumn_name()+"'  name='"+tags.getColumn_name()+"' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"></textarea>");
			}else{
				out.println("<textarea readOnly id='"+tags.getColumn_name()+"'  name='"+tags.getColumn_name()+"' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"></textarea><span style='color:red'>不可填写</span>");
			}
		}else if(tags.getDisplay_type().equals("附件上传")){
			out.println("<script type='text/javascript'>	"	+
					" function addStuff_"+tags.getId()+"() {	 " +
					" 	width=1060;	"	+
					" 	height=500;	"	+
					"  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"	+
					"  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	" +
				
					" 	if(null==rv){	"	+
					" 	 	alert('您没选择东西！'); 	"	+
					" 	 	return ;		"	+
					" 	 } 					"	+
					" 	 document.getElementById('"+tags.getColumn_name()+"_').value=rv; 		"	+
					" 	 } " +
					" </script> ");
		
			if(tags.getWritible() == 1){
				out.println("<input type='text' name='"+tags.getColumn_name()+"' id='"+tags.getColumn_name()+"' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");//addr
				out.println("<input type='text' name='"+tags.getColumn_name()+"_' id='"+tags.getColumn_name()+"_'  readonly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");//addr
				out.println("<a  onClick='addStuff_"+tags.getId()+"()'>浏览资源库</a>");
			}else{
				out.println("<input readOnly type='text' name='"+tags.getColumn_name()+"' id='"+tags.getColumn_name()+"' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");//addr
				out.println("<input readOnly type='text' name='"+tags.getColumn_name()+"_' id='"+tags.getColumn_name()+"_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>不可填写</span>");//addr
			}
		}else if(tags.getDisplay_type().equals("图片")){
			out.println("<script type='text/javascript'>	"	+
					" function addStuff_"+tags.getId()+"() {	 " +
					" 	width=600;	"	+
					" 	height=400;	"	+
					"  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"	+
					"  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	" +
				
					" 	if(null==rv){	"	+
					" 	 	alert('您没选择东西！'); 	"	+
					" 	 	return ;		"	+
					" 	 } 					" +
					"	 var pos = '.' + rv.replace(/.+\\./, ''); " +
					"   	"+
					"	if(!(pos=='.jpg'||pos=='.png'||pos=='.bmp'))	" +
					"	{	" +
					"		alert('只能上传.jpg,.png,.bmp格式的图片');" +
					"		return ;" +
					"	}"	+
					" 	 document.getElementById('"+tags.getColumn_name()+"').value=rv; 		"	+
					" 	 } " +
					" </script> ");
		
			if(tags.getWritible() == 1){
				out.println("高<input type='text' id='"+tags.getColumn_name()+"_h'  name='"+tags.getColumn_name()+"_h'   class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");//height
				out.println("宽<input type='text' id='"+tags.getColumn_name()+"_w' name='"+tags.getColumn_name()+"_w'    class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");//width
				out.println("<input type='text' name='"+tags.getColumn_name()+"' id='"+tags.getColumn_name()+"'  readonly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");//addr
				out.println("<a  onClick='addStuff_"+tags.getId()+"()'>浏览资源库</a>");
			}else {
				out.println("高<input readOnly type='text' id='"+tags.getColumn_name()+"_h' name='"+tags.getColumn_name()+"_h'   class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");//height
				out.println("宽<input readOnly type='text' id='"+tags.getColumn_name()+"_w' name='"+tags.getColumn_name()+"_w'    class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/>");//width
				out.println("<input readOnly type='text' name='"+tags.getColumn_name()+"' id='"+tags.getColumn_name()+"'  readonly class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>不可填写</span>");//addr
			}
		}else if(tags.getDisplay_type().equals("相关字段")){
			String tablename[]=tags.getDefault_value().split("==");

			out.println("<script type='text/javascript'>" +
						" function add_"+tags.getId()+"() "+
						" {			" +
						" 	width=800;	" +
						" 	height=600;	" +
						"   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	" +
						" 	  	var rv = window.showModalDialog('relateColumn.action?tablename="+tablename[0]+"&columnname="+tablename[1]+"&columnName="+tags.getColumn_name()+"" +
								"&control=0&is_judge="+tags.getIs_judge()+"&rn='+Math.random(),null,sFeature);	" +
						"var display='';" +
						"var returnvalue='';" +
						"var str;"+
						"if(rv!=null && rv != '')" +
						"{" +
						"	str = String(rv).split('_--_');" +
						"}" +
						"if(str!=null&&str.length>0)" +
						"{" +
						"	for(i=0;i<str.length;i++)" +
						"	{" +
						"		var tmp =str[i].split('_-_');" +
						"		if(tmp[1] == 'null'){alert('为空,请重新选择!!!');return;}"+
						"		display += tmp[1] ;" +
						"		returnvalue+=tmp[0];" +
						"		if(i+1!=str.length) " +
						"		{" +
						"			display +=',' ;" +
						"			returnvalue+='__-__'" +
						"		}" +
						"	}" +
						"}" +
						"" +
						"" +
						"document.getElementById('relate_"+tags.getId()+"_').value=display;" +
						"document.getElementById('relate_"+tags.getId()+"').value=returnvalue; ");
			if(kk != null && !kk.equals("")){
				if(kk.equals(tags.getColumn_name()))
					out.print("addRelate(returnvalue);");
			}
			out.print("}</script>");
		
			if(tags.getWritible() == 1){
				if(tags.getRequired() == 1){
					out.println("<input  type='text' readonly  id='relate_"+tags.getId()+"_' onclick='add_"+tags.getId()+"();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"+mark+"</span>");
					out.println("<input type='hidden' name='relate_"+tags.getId()+"' id='relate_"+tags.getId()+"'  />");
					out.print("<span style='color:red;cursor: hand' onclick='add_"+tags.getId()+"();'>点此添加</span>");
				}else{
					out.println("<input  type='text' readonly  id='relate_"+tags.getId()+"_' onclick='add_"+tags.getId()+"();' class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"+mark+"</span>");
					out.println("<input type='hidden' name='relate_"+tags.getId()+"' id='relate_"+tags.getId()+"'  />");
					out.print("<span style='color:red;cursor: hand' onclick='add_"+tags.getId()+"();'>点此添加</span>");
				}
			}else {
				if(tags.getRequired() == 1){
					out.println("<input  readOnly type='text' readonly  id='relate_"+tags.getId()+"_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span style='color:red'>*</span><span>"+mark+"</span>");
					out.println("<input type='hidden' name='relate_"+tags.getId()+"' id='relate_"+tags.getId()+"'  />");
				}else{
					out.println("<input  readOnly type='text' readonly  id='relate_"+tags.getId()+"_'  class=\"x_bill_item_input\" onFocus=\"_bill_item_focus_bg(this)\" onBlur=\"_bill_item_nofocus_bg(this);_blur_check(this);\"/><span>"+mark+"</span>");
					out.println("<input type='hidden' name='relate_"+tags.getId()+"' id='relate_"+tags.getId()+"'  />");
				}
			}
		}else if(tags.getDisplay_type().equals("相关负责人")){
			out.println("<script type='text/javascript'>" +
					" function add_"+tags.getId()+"() "+
					" {			" +
					" 	width=800;	" +
					" 	height=600;	" +
					"   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	" +
					" 	  	var rv = window.showModalDialog('getRelateEluserInfo.action?rn='+Math.random(),null,sFeature);	" +
					"var display='';" +
					"var returnvalue='';" +
					"if(rv!=null)" +
					"{" +
					"	var str=String(rv).split('_--_');" +
					"}" +
					"if(str!=null&&str.length>0)" +
					"{" +
					"	for(i=0;i<str.length;i++)" +
					"	{" +
					"		var tmp =str[i].split('_-_');" +
					"		display += tmp[1] ;" +
					"		returnvalue+=tmp[0];" +
					"		if(i+1!=str.length) " +
					"		{" +
					"			display +=',' ;" +
					"			returnvalue+='__-__'" +
					"		}" +
					"	}" +
					"}" +
					"" +
					"" +
					"document.getElementById('relate_"+tags.getId()+"').value=returnvalue; " +
					"}" +
					" </script> ");
	
		
		
			out.println("<span style='color:red' id='relate_"+tags.getId()+"__' >"+username+"</span><span>"+mark+"</span>");
			out.println("<span style='color:red;cursor: hand' id='relate_"+tags.getId()+"___' onclick='changeRelateUser(this);'>点此添加</span>");
			out.println("<input type='hidden' name='relate_"+tags.getId()+"' id='relate_"+tags.getId()+"'  value='"+uid+"'/>");
		}else if(tags.getDisplay_type().equals("富文本")){
			out.println("<div  style='text-align: center; width: 100%'>");
			for(int j=0;j<list_ricktext.size();j++)
			{
				if(list_tags.get(list_ricktext.get(j)).getValue()==null)
					list_tags.get(list_ricktext.get(j)).setValue("");
				out.println("<label>"+list_tags.get(list_ricktext.get(j)).getName_display()+"</label>");
				out.println("<textarea  name='"+list_tags.get(list_ricktext.get(0)).getColumn_name()+"' id='content' cols='60' rows='7' "
								+ " cssStyle='width: 100%; height: 440px;; visibility: hidden;'  > "
								+ list_tags.get(list_ricktext.get(j)).getValue() + " </textarea>");
				break;
			}

			out.println("</div>");
		}
		
	}

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
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

	public String getNowdate() {
		return nowdate;
	}

	public void setNowdate(String nowdate) {
		this.nowdate = nowdate;
	}

}
