package com.sopia.schedule.tags;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.BaseAction;
import com.sopia.common.ElTag;
import com.sopia.common.BeanGenerator.TableCreateBean;
import com.sopia.schedule.entities.Tags;

public class TBGET extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private String username;
	private String uid;
	private String kk;
	
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

	public String getKk() {
		return kk;
	}

	public void setKk(String kk) {
		this.kk = kk;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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
	
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");  
			int TypeView = (Integer) request.getAttribute("viewType"); 
			
			username = (String) request.getAttribute("username");
			uid = (String) request.getAttribute("uid");
			kk = (String)request.getAttribute("kk");
			//TypeView 页面显示类型
			 if(TypeView == 1){ // 1 . 可提交的显示
				 writeChilds(out, list_tags); 
			 }else if(TypeView == 2){//2 . 可查看的显示
				 writeChilds2(out, list_tags); 
			 }else if(TypeView == 3){//3 .  可修改的显示
//				 writeChilds3(out, list_tags);
			 }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	// 1 . 可提交的显示
	public void writeChilds(JspWriter out, Object obj) throws Exception{
		String nowdate="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		nowdate=df.format(new Date());
		
		int control_tr=0;//0:tr 2:/td
		int control_size=0;//list_tags.size  update_display
		
		List<Tags> list_tags = (List<Tags>)obj;
		List<Integer> list_ricktext = new ArrayList<Integer>();
		String [] cview;
		String iname = getIname();
		String tableName = list_tags.get(0).getTable_name();
		
//		for(int i=0;i<list_tags.size();i++)
//		{
//			if(list_tags.get(i).getUpdate_display()==1)
//			{
//				if(list_tags.get(i).getDisplay_type().equals("富文本"))
//				{
//					out.println("<script type='text/javascript'>" +
//							"	function myload(){" +
//							"	var oFCKeditor = new FCKeditor('content') ; "+
//							"	oFCKeditor.BasePath = 'editor/' ;"	+
//							"	oFCKeditor.Height = 400;"	+
//							"	oFCKeditor.Width = '100%';"	+
//							"	oFCKeditor.ReplaceTextarea();" +
//							"	} " +
//							" </script> ");
//					list_ricktext.add(i);
//				}
//				else
//					control_size++;
//			}
//		}
		
		for(int i=0;i<list_tags.size();i++){
			if(list_tags.get(i).getAdd_display()==1){//display 是否显示
				if(list_tags.get(i).getColumn_name().equals(iname)){
					if(list_tags.get(i).getDisplay_type().equals("文本")){
						String textvalue="";
						String textwidth=" style='width:300px;' ";

						if(list_tags.get(i).getDefault_value()!=null)
						{
							String textcontrol[]=list_tags.get(i).getDefault_value().split("==");//defaultvalue==width
							
							if(!list_tags.get(i).getDefault_value().equals(""))
							{
								if(!textcontrol[0].equals(""))
									textvalue=" value='"+textcontrol[0]+"' ";
								if(textcontrol.length>1)
									textwidth=" style='width:"+textcontrol[1]+"%;' ";
							}
						}
						String colspan="";
						if(control_tr==0&&i+1<=list_tags.size())
						{
							int k=i+1;
							for(int l=i+1;l<list_tags.size();l++)
							{
								if(list_tags.get(l).getAdd_display()==1)
								{
									break;
								}
								else k++;
							}
							if(i+1==list_tags.size()||control_size==0)
							{
								colspan=" colspan='3' ";
							}
							else if(list_tags.get(k).getDisplay_type().equals("大文本")||
									list_tags.get(k).getDisplay_type().equals("图片")||
									list_tags.get(k).getDisplay_type().equals("相关字段"))
							{
								colspan=" colspan='3' ";
							}
						}
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td "+colspan+" >");
						
						out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"'  id=\"CSS_"+list_tags.get(i).getColumn_name()+"\"  />");
//						out.println("<input type=\"text\" size=\"20\" id=\"CSS_"+list_tags.get(i).getColumn_name()+"\" name=\""+tableName+"."+list_tags.get(i).getColumn_name()+"- "+"\" >");
						break;
					}
					if(list_tags.get(i).getDisplay_type().equals("实数")||
							list_tags.get(i).getDisplay_type().equals("整数"))
					{
						int k=i+1;
						for(int l=i+1;l<list_tags.size();l++)
						{
							if(list_tags.get(l).getAdd_display()==1)
							{
								break;
							}
							else k++;
						}
						String colspan="";
						if(control_tr==0&&i+1<=list_tags.size())
						{
							if(i+1==list_tags.size()||control_size==0)
							{
								colspan=" colspan='3' ";
							}
							else if(list_tags.get(k).getDisplay_type().equals("大文本")||
									list_tags.get(k).getDisplay_type().equals("图片")||
									list_tags.get(k).getDisplay_type().equals("相关字段"))
							{
								colspan=" colspan='3' ";
							}
						}
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td "+colspan+" >");
						
						if(list_tags.get(i).getDisplay_type().equals("实数")){
							if(list_tags.get(i).getIs_calculate() == 1){
								out.println("0");
							}else {
								out.println("<input type='text' id='"+list_tags.get(i).getColumn_name()+"' name='"+list_tags.get(i).getColumn_name()+"'  onKeyUp='calculate(this);'/");
							}
						}
						
						
						out.println("</td>");
						control_tr++;
						break;
					}
					if(list_tags.get(i).getDisplay_type().equals("百分比"))
					{
						int k=i+1;
						for(int l=i+1;l<list_tags.size();l++)
						{
							if(list_tags.get(l).getAdd_display()==1)
							{
								break;
							}
							else k++;
						}
						String colspan="";
						if(control_tr==0&&i+1<=list_tags.size())
						{
							if(i+1==list_tags.size()||control_size==0)
							{
								colspan=" colspan='3' ";
							}
							else if(list_tags.get(k).getDisplay_type().equals("大文本")||
									list_tags.get(k).getDisplay_type().equals("图片")||
									list_tags.get(k).getDisplay_type().equals("相关字段"))
							{
								colspan=" colspan='3' ";
							}
						}
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;' >");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td "+colspan+" >");
						
					//	out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"' size='50' /");
						if(list_tags.get(i).getTime_jindu_ids() != null && !list_tags.get(i).getTime_jindu_ids().equals("")){
							out.println("<input readOnly id='value_jindutiao__"+list_tags.get(i).getColumn_name()+"' type='text' name='"+list_tags.get(i).getColumn_name()+"'  onKeyUp='getJindutiao("+list_tags.get(i).getJindutiao()+",this);' onclick='time_columns(this);'/><span style='color:red;' id='span_red__"+list_tags.get(i).getColumn_name()+"'>%</span>");
						
						}else if(list_tags.get(i).getYewu_jindu_ids() != null && !list_tags.get(i).getYewu_jindu_ids().equals("")) {
							out.println("<input readOnly id='value_jindutiao__"+list_tags.get(i).getColumn_name()+"' type='text' name='"+list_tags.get(i).getColumn_name()+"'  onclick='calculate_yewu_jindu(this);'/><span style='color:red;' id='span_red__"+list_tags.get(i).getColumn_name()+"'>%</span>");
						}else {
							out.println("<input id='value_jindutiao__"+list_tags.get(i).getColumn_name()+"' type='text' name='"+list_tags.get(i).getColumn_name()+"'  onKeyUp='getJindutiao("+list_tags.get(i).getJindutiao()+",this);'/><span style='color:red;' id='span_red__"+list_tags.get(i).getColumn_name()+"'>%</span>");
						}
						
						if(list_tags.get(i).getJindutiao() == 1){//显示进度条
							out.println("<div id='jindutiao_div__"+list_tags.get(i).getColumn_name()+"' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='0%'  id='show_jindutiao__"+list_tags.get(i).getColumn_name()+"' /></div>");
						}
				  
						
						out.println("</td>");
						control_tr++;
						break;
					}
					else if(list_tags.get(i).getDisplay_type().equals("日期"))
					{
						
						
						String value="";
						if(list_tags.get(i).getDefault_value()!=null){
							if(list_tags.get(i).getDefault_value().contains("_"))
								value="value='"+do4(nowdate,Integer.parseInt(list_tags.get(i).getDefault_value().split("_")[1]))+"'";
							else 
								value="value='"+do4(nowdate,0)+"'";
						}
						
						int k=i+1;
						for(int l=i+1;l<list_tags.size();l++)
						{
							if(list_tags.get(l).getAdd_display()==1)
							{
								break;
							}
							else k++;
						}
						String colspan="";
						if(control_tr==0&&i+1<=list_tags.size())
						{
							if(i+1==list_tags.size()||control_size==0)
							{
								colspan=" colspan='3' ";
							}
							else if(list_tags.get(k).getDisplay_type().equals("大文本")||
									list_tags.get(k).getDisplay_type().equals("图片")||
									list_tags.get(k).getDisplay_type().equals("相关字段"))
							{
								colspan=" colspan='3' ";
							}
						}
						
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td "+colspan+" >");
			
						
						out.println("<input class='Wdate'  readonly='readonly' type='text'  name='"+list_tags.get(i).getColumn_name()+"' " +
								" onClick='setday(this)' id='"+list_tags.get(i).getColumn_name()+"'    "+value+" />");
						
						
						out.println("</td>");
						control_tr++;
						break;
					}
					
					else if(list_tags.get(i).getDisplay_type().equals("下拉选项"))
					{
						int k=i+1;
						for(int l=i+1;l<list_tags.size();l++)
						{
							if(list_tags.get(l).getAdd_display()==1)
							{
								break;
							}
							else k++;
						}
						String colspan="";
						if(control_tr==0&&i+1<=list_tags.size())
						{
							if(i+1==list_tags.size()||control_size==0)
							{
								colspan=" colspan='3' ";
							}
							else if(list_tags.get(k).getDisplay_type().equals("大文本")||
									list_tags.get(k).getDisplay_type().equals("图片")||
									list_tags.get(k).getDisplay_type().equals("相关字段"))
							{
								colspan=" colspan='3' ";
							}
						}
						
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td "+colspan+" >");
						
						
						String str[]=list_tags.get(i).getDefault_value().split("==");
						String str_select_head="<select  name='"+list_tags.get(i).getColumn_name()+"' >";
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
						
						
						out.println("</td>");
						control_tr++;
						break;
					}
					else if(list_tags.get(i).getDisplay_type().equals("单选"))
					{
						int k=i+1;
						for(int l=i+1;l<list_tags.size();l++)
						{
							if(list_tags.get(l).getAdd_display()==1)
							{
								break;
							}
							else k++;
						}
						String colspan="";
						if(control_tr==0&&i+1<=list_tags.size())
						{
							if(i+1==list_tags.size()||control_size==0)
							{
								colspan=" colspan='3' ";
							}
							else if(list_tags.get(k).getDisplay_type().equals("大文本")||
									list_tags.get(k).getDisplay_type().equals("图片")||
									list_tags.get(k).getDisplay_type().equals("相关字段"))
							{
								colspan=" colspan='3' ";
							}
						}
						
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td "+colspan+" >");
						
						
						String str[]=list_tags.get(i).getDefault_value().split("==");
						String radio_body="";
						if(str.length>0)
						{
							for(int j=0;j<str.length;j++)
							{
								
								radio_body+="<input type='radio' name='"+list_tags.get(i).getColumn_name()+"' value='"+str[j]+"'>"+str[j];
							}
						}
//						
						out.println(radio_body);
						
						
						out.println("</td>");
						control_tr++;
						break;
					}
					else if(list_tags.get(i).getDisplay_type().equals("复选"))
					{
						int k=i+1;
						for(int l=i+1;l<list_tags.size();l++)
						{
							if(list_tags.get(l).getAdd_display()==1)
							{
								break;
							}
							else k++;
						}
						String colspan="";
						if(control_tr==0&&i+1<=list_tags.size())
						{
							if(i+1==list_tags.size()||control_size==0)
							{
								colspan=" colspan='3' ";
							}
							else if(list_tags.get(k).getDisplay_type().equals("大文本")||
									list_tags.get(k).getDisplay_type().equals("图片")||
									list_tags.get(k).getDisplay_type().equals("相关字段"))
							{
								colspan=" colspan='3' ";
							}
						}
						
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td "+colspan+" >");
						
						
						String str[]=list_tags.get(i).getDefault_value().split("==");
						String checkbox_body="";
						if(str.length>0)
						{
							for(int j=0;j<str.length;j++)
							{
								
								checkbox_body+="<input type='checkbox' name='"+list_tags.get(i).getColumn_name()+"' value='"+str[j]+"'>"+str[j];
							}
						}
//						
						out.println(checkbox_body);
						
						
						out.println("</td>");
						control_tr++;
						break;
					}
					else if(list_tags.get(i).getDisplay_type().equals("大文本"))
					{
						
						if(control_tr==1)
							out.println("</tr>");
						out.println("<tr>");
						
						
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td colspan='3'>");
						
						out.println("<textarea cols='100%' rows='10' name='"+list_tags.get(i).getColumn_name()+"'></textarea>");
						
						out.println("</td>");
						control_tr=2;
					}
					else if(list_tags.get(i).getDisplay_type().equals("附件上传"))
					{
						int k=i+1;
						for(int l=i+1;l<list_tags.size();l++)
						{
							if(list_tags.get(l).getAdd_display()==1)
							{
								break;
							}
							else k++;
						}
						String colspan="";
						if(control_tr==0&&i+1<=list_tags.size())
						{
							if(list_tags.get(k).getDisplay_type().equals("大文本")||
									list_tags.get(k).getDisplay_type().equals("图片")||
									list_tags.get(k).getDisplay_type().equals("相关字段")||
									i+1==list_tags.size())
							{
								colspan=" colspan='3' ";
							}
						}
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td "+colspan+" >");
						
						out.println("<script type='text/javascript'>	"	+
									" function addStuff_"+list_tags.get(i).getId()+"() {	 " +
									"   if(document.getElementById('"+list_tags.get(i).getColumn_name()+"').value=='')" +
									"	{" +
									"		alert('请先填写附件名称！！！');" +
									"		return false;" +
									"	}" +
									" 	width=600;	"	+
									" 	height=400;	"	+
									"  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"	+
									"  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	" +
								
									" 	if(null==rv){	"	+
									" 	 	alert('您没选择东西！'); 	"	+
									" 	 	return ;		"	+
									" 	 } 					"	+
						//			" 	 document.getElementById('"+list_tags.get(i).getColumn_name()+"_').innerHTML=rv;	alert('1');		"	+
									" 	 document.getElementById('"+list_tags.get(i).getColumn_name()+"_').value=rv; 		"	+
									" 	 } " +
									" </script> ");
						
						out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"' id='"+list_tags.get(i).getColumn_name()+"' />");//addr
						out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"_' id='"+list_tags.get(i).getColumn_name()+"_'  readonly />");//addr
						out.println("<a  onClick='addStuff_"+list_tags.get(i).getId()+"()'>浏览资源库</a>");
						
						
						out.println("</td>");
						control_tr++;
						break;
					}
					else if(list_tags.get(i).getDisplay_type().equals("图片"))
					{
						
						if(control_tr==1)
							out.println("</tr>");
						out.println("<tr>");
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td colspan='3'>");
						
						
						out.println("<script type='text/javascript'>	"	+
								" function addStuff_"+list_tags.get(i).getId()+"() {	 " +
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
					//			" 	 document.getElementById('"+list_tags.get(i).getColumn_name()+"_').innerHTML=rv;	alert('1');		"	+
								" 	 document.getElementById('"+list_tags.get(i).getColumn_name()+"_').value=rv; 		"	+
								" 	 } " +
								" </script> ");
					
						out.println("高<input type='text' name='"+list_tags.get(i).getColumn_name()+"_h'  size='5' />");//height
						out.println("宽<input type='text' name='"+list_tags.get(i).getColumn_name()+"_w'  size='5'  />");//width
						out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"' id='"+list_tags.get(i).getColumn_name()+"_'  readonly />");//addr
						out.println("<a  onClick='addStuff_"+list_tags.get(i).getId()+"()'>浏览资源库</a>");
						
						out.println("</td>");
						control_tr=2;
						break;
					}
					else if(list_tags.get(i).getDisplay_type().equals("相关字段"))
					{
						
						if(control_tr==1)
							out.println("</tr>");
						out.println("<tr>");
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td colspan='3'>");
						
						
						
						String tablename[]=list_tags.get(i).getDefault_value().split("==");//tb_clientlinkman_tags==tb_clientlinkman_tags_35==联系主题==varchar2(500)

						out.println("<script type='text/javascript'>" +
								" function add_"+list_tags.get(i).getId()+"() "+
								" {			" +
								" 	width=800;	" +
								" 	height=600;	" +
								"   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	" +
								" 	  	var rv = window.showModalDialog('relateColumn.action?tablename="+tablename[0]+"&columnname="+tablename[1]+"" +
										"&control=0&rn='+Math.random(),null,sFeature);	" +
						//		" alert(rv);" +
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
								"document.getElementById('relate_"+list_tags.get(i).getId()+"_').value=display;" +
								"document.getElementById('relate_"+list_tags.get(i).getId()+"').value=returnvalue; ");
					if(kk != null && !kk.equals("")){
						out.print("addRelate(returnvalue);");
					}
					out.print("}</script>");
					
						out.println("<input type='text' readonly  id='relate_"+list_tags.get(i).getId()+"_' onclick='add_"+list_tags.get(i).getId()+"();' />");
						out.println("<input type='text' name='relate_"+list_tags.get(i).getId()+"' id='relate_"+list_tags.get(i).getId()+"'  />");
					
						out.println("</td>");
						control_tr=2;
						break;
					
					}
					else if(list_tags.get(i).getDisplay_type().equals("相关负责人"))
					{
						
						if(control_tr==1)
							out.println("</tr>");
						out.println("<tr>");
						out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
						if(!list_tags.get(i).getDisplay_type().equals("富文本"))
							out.println(list_tags.get(i).getName_display()+":");
						out.println("</td>");
						out.println("<td colspan='3'>");
						
						out.println("<script type='text/javascript'>" +
									" function add_"+list_tags.get(i).getId()+"() "+
									" {			" +
									" 	width=800;	" +
									" 	height=600;	" +
									"   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	" +
									" 	  	var rv = window.showModalDialog('getRelateEluserInfo.action?rn='+Math.random(),null,sFeature);	" +
							//		" alert(rv);" +
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
							//		"alert(returnvalue);" +
									"" +
									"" +
									"document.getElementById('relate_"+list_tags.get(i).getId()+"_').value=display;" +
									"document.getElementById('relate_"+list_tags.get(i).getId()+"').value=returnvalue; " +
									"}" +//id==column==content
									" </script> ");
					
//						out.println("<input type='text' readonly  id='relate_"+list_tags.get(i).getId()+"_' onclick='add_"+list_tags.get(i).getId()+"();' />");
//						out.println("<input type='hidden' name='relate_"+list_tags.get(i).getId()+"' id='relate_"+list_tags.get(i).getId()+"'  />");
						
						
						out.println("<span style='color:red'>"+username+"</span>");
						out.println("<input type='hidden' name='relate_"+list_tags.get(i).getId()+"' id='relate_"+list_tags.get(i).getId()+"'  value='"+uid+"'/>");
						out.println("</td>");
						control_tr=2;
						break;
					}
				}
			}
		}
	}
	
	// 1 . 可查看的显示
	public void writeChilds2(JspWriter out, Object obj) throws Exception{
		String nowdate="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		nowdate=df.format(new Date());
		
		int control_tr=0;//0:tr 2:/td
		int control_size=0;//list_tags.size  update_display
		
		List<Tags> list_tags = (List<Tags>)obj;
		List<Integer> list_ricktext = new ArrayList<Integer>();
		String [] cview;
		String iname = getIname();
		String tableName = list_tags.get(0).getTable_name();
		
		for(int i=0;i<list_tags.size();i++)
		{
			if(list_tags.get(i).getView_display()==1)//display
			{ 	
				control_size--;
				if(control_tr==0)
				{
					out.println("<tr>");
			//		control_tr++;
				}
//				out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
//				if(!list_tags.get(i).getDisplay_type().equals("富文本"))
//					out.println(list_tags.get(i).getName_display()+":");
//				out.println("</td>");
//				
//				out.println("<td>");
				//out.println("<input type='text' />");
				
				String str=list_tags.get(i).getValue();
				if(str==null) str="";
				
				if(list_tags.get(i).getDisplay_type().equals("文本")||
						list_tags.get(i).getDisplay_type().equals("实数")||
						list_tags.get(i).getDisplay_type().equals("整数")||
						list_tags.get(i).getDisplay_type().equals("下拉选项") || 
						list_tags.get(i).getDisplay_type().equals("单选") || 
						list_tags.get(i).getDisplay_type().equals("复选"))
				{
					int k=i+1;
					for(int l=i+1;l<list_tags.size();l++)
					{
						if(list_tags.get(l).getView_display()==1)
						{
							break;
						}
						else k++;
					}
					
					String colspan="";
					if(control_tr==0&&i+1<=list_tags.size())
					{
						if(i+1==list_tags.size()||control_size==0)
						{
							colspan=" colspan='3' ";
						}
						else if(list_tags.get(k).getDisplay_type().equals("大文本")||
								list_tags.get(k).getDisplay_type().equals("图片")||
								list_tags.get(k).getDisplay_type().equals("相关字段"))
						{
							colspan=" colspan='3' ";
						}
					}
					
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if(!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					
					out.println("<td "+colspan+" >");
//					out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"' size='50' readonly " +
//							" value ='"+str+"' /");
					if(list_tags.get(i).getIs_calculate() == 1){
						out.println("<label style='padding-left:10px;color:red' >"+str+"</label>");
					}else{
						out.println("<label style='padding-left:10px' >"+str+"</label>");
					}
					
					out.println("</td>");
					control_tr++;
				}
				if(list_tags.get(i).getDisplay_type().equals("百分比"))
				{
					int k=i+1;
					for(int l=i+1;l<list_tags.size();l++)
					{
						if(list_tags.get(l).getAdd_display()==1)
						{
							break;
						}
						else k++;
					}
					String colspan="";
					if(control_tr==0&&i+1<=list_tags.size())
					{
						if(i+1==list_tags.size()||control_size==0)
						{
							colspan=" colspan='3' ";
						}
						else if(list_tags.get(k).getDisplay_type().equals("大文本")||
								list_tags.get(k).getDisplay_type().equals("图片")||
								list_tags.get(k).getDisplay_type().equals("相关字段"))
						{
							colspan=" colspan='3' ";
						}
					}
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;' >");
					if(!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					out.println("<td "+colspan+" >");
					
					if(list_tags.get(i).getJindutiao() == 1){//显示进度条
						BigDecimal bg = new BigDecimal(str); 
						str = String.valueOf(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
						out.println("<table width='300px' border='0' cellspacing='1' ><tr><td><div  id='jindutiao_div__"+list_tags.get(i).getColumn_name()+":"+i+"' style='border: 1px dotted #FF6633;width:280px'><img height='14' src='images/jd.gif' width='"+str+"%'  id='show_jindutiao__"+list_tags.get(i).getColumn_name()+":"+i+"'/></div></td><td><center><span style='color:red;'>"+str+"%</span></center></td></tr></table>");
					}
			  
					
					out.println("</td>");
					control_tr++;
				}
				else if(list_tags.get(i).getDisplay_type().equals("日期"))
				{
					int k=i+1;
					for(int l=i+1;l<list_tags.size();l++)
					{
						if(list_tags.get(l).getView_display()==1)
						{
							break;
						}
						else k++;
					}
					String colspan="";
					if(control_tr==0&&i+1<=list_tags.size())
					{
						if(i+1==list_tags.size()||control_size==0)
						{
							colspan=" colspan='3' ";
						}
						else if(list_tags.get(k).getDisplay_type().equals("大文本")||
								list_tags.get(k).getDisplay_type().equals("图片")||
								list_tags.get(k).getDisplay_type().equals("相关字段"))
						{
							colspan=" colspan='3' ";
						}
					}
					
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if(!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					
					out.println("<td"+colspan+">");
//					out.println("<input class='Wdate'  readonly='readonly' type='text'  name='"+list_tags.get(i).getColumn_name()+"' " +
//							"  id='releasetime'   size='50'   value ='"+str+"' /");
					out.println("<label style='padding-left:10px' >"+str+"</label>");
					
					out.println("</td>");
					control_tr++;
				}
				else if(list_tags.get(i).getDisplay_type().equals("相关字段"))
				{
					if(control_tr==1)
						out.println("</tr>");
					out.println("<tr>");
					
					
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if(!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					
					out.println("<td colspan='3'>");
					
					//out.println("<label>"+str+"</label>");
					String values[] = str.split(";");
					String tmp="";
					if( list_tags.get(i).getValue2()!=null)
						tmp= list_tags.get(i).getValue2();
					String relateid [] = tmp.split(",");
					String relatename=list_tags.get(i).getColumn_name();
					String relatetablename=list_tags.get(i).getTable_name();
				
					String relate_tname[]=list_tags.get(i).getDefault_value().split("==");
					if(values!=null&&!values[0].equals(""))
					{
						for(int j=0;j<values.length;j++)
						{
//							out.println("<span style='background-color:white'  id='"+relateid[j]+"'>"+values[j]+"" +
//									" <input type='button' value='X' onclick='del("+relateid[j]+");'/></span>");
							out.println("<span style='background-color:white'>" +
									"<a href='viewContactTags.action?tablename="+relate_tname[0]+"&id="+relateid[j]+"'  >"+values[j]+"</a>" +
									"</span>");
							
							if(j+1!=values.length)
								out.println("<span style='background-color:red'>|</span>");
						}
					}
					
					out.println("</td>");
					control_tr=2;
				}
				else if(list_tags.get(i).getDisplay_type().equals("相关负责人"))
				{
					if(control_tr==1)
						out.println("</tr>");
					out.println("<tr>");
					
					
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if(!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					
					out.println("<td colspan='3'>");
					
					//out.println("<label>"+str+"</label>");
					String values[] = str.split(";");
					String tmp="";
					if( list_tags.get(i).getValue2()!=null)
						tmp= list_tags.get(i).getValue2();
//					String relateid [] = tmp.split(",");
//					String relatename=list_tags.get(i).getColumn_name();
//					String relatetablename=list_tags.get(i).getTable_name();
					
//					if(list_tags.get(i).getValue()!=null)
//						;
//					else break;
				//	String relate_tname[]=list_tags.get(i).getValue().split("==");
					
					
					if(values!=null&&!values[0].equals(""))
					{
						for(int j=0;j<values.length;j++)
						{
//							out.println("<span style='background-color:white'  id='"+relateid[j]+"'>"+values[j]+"" +
//									" <input type='button' value='X' onclick='del("+relateid[j]+");'/></span>");
							out.println("<span style='background-color:white'>" +
								//	"<a href='viewContactTags.action?tablename="+relate_tname[0]+"&id="+relateid[j]+"'  target='_blank'>"+values[j]+"</a>" +
									""+values[j]+""+
							"</span>");
							
							if(j+1!=values.length)
								out.println("<span style='background-color:red'>|</span>");
						}
					}
					
					out.println("</td>");
					control_tr=2;
				}
				else if(list_tags.get(i).getDisplay_type().equals("附件上传"))
				{
					int k=i+1;
					for(int l=i+1;l<list_tags.size();l++)
					{
						if(list_tags.get(l).getView_display()==1)
						{
							break;
						}
						else k++;
					}
					String colspan="";
					if(control_tr==0&&i+1<=list_tags.size())
					{
						if(i+1==list_tags.size()||control_size==0)
						{
							colspan=" colspan='3' ";
						}
						else if(list_tags.get(k).getDisplay_type().equals("大文本")||
								list_tags.get(k).getDisplay_type().equals("图片")||
								list_tags.get(k).getDisplay_type().equals("相关字段"))
						{
							colspan=" colspan='3' ";
						}
					}
					
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if(!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					
					out.println("<td>");
					
					String str2[]=str.split("==");
					if(str2.length>1)
					{
						out.println("<label  style='padding-left:10px'  cols='40' rows='10' name='"+list_tags.get(i).getColumn_name()+"' " +
								"  readonly  ><a href='downloadStuff.action?down="+str+"' > "+str2[0]+"</a></label>");
					}
					
					out.println("</td>");
					control_tr++;
				}
				else if(list_tags.get(i).getDisplay_type().equals("大文本"))
				{
					
					
					
					if(control_tr==1)
						out.println("</tr>");
					out.println("<tr>");
					
					
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if(!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					
					out.println("<td colspan='3'>");
//					out.println("<textarea cols='40' rows='10' name='"+list_tags.get(i).getColumn_name()+"' " +
//							"  readonly  >"+str+"</textarea>");
					out.println("<label>"+str+"</label>");
					out.println("</td>");
					control_tr=2;
				}
				else if(list_tags.get(i).getDisplay_type().equals("图片"))
				{
					if(control_tr==1)
						out.println("</tr>");
					out.println("<tr>");
					out.println("<td  width='120' height='30' align='right' style='padding-right:10px;color:#0099CC;'>");
					if(!list_tags.get(i).getDisplay_type().equals("富文本"))
						out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					out.println("<td colspan='3'>");
//					String str2[]=str.split("==");
					String height="";
					String width="";
					
					String str3[]=str.split("==");//0:height  1:width  2:addr
					String str2[]={"","",""};
					if(str3!=null&&str3.length>1)
					{
						str2[0]=str3[0];
						str2[1]=str3[1];
						str2[2]=str3[2];
					}
					
					
					
					if(str2[0].equals("0")||str2[0].equals("")) height="";
					else height=" height='"+str2[0]+"' ";
					if(str2[1].equals("0")||str2[1].equals("")) width="";
					else width=" width='"+str2[1]+"' ";
					if(!str2[2].equals(""))
						out.println("<img  src='"+str2[2]+"' "+height+" "+width+"  />");
					
					out.println("</td>");
					control_tr=2;
				}
				else if(list_tags.get(i).getDisplay_type().equals("富文本"))
				{
					list_ricktext.add(i);
				}
				
				
				if(control_tr==2)
				{
					out.println("</tr>");
					control_tr=0;
				}
			}

		}
	}
}
