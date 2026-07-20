package com.sopia.schedule.tags.template;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.entities.Tags;

/**
 * 查看页面解析字段HTML
 * @author Administrator
 *
 */
public class TBHTMLView extends TagSupport{
	private List<Tags> list_tags ;
	private String iname;
	
	private List<Integer> list_ricktext;
	
	public int doStartTag() {
		try {
			
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			list_tags = (List<Tags>) request.getAttribute("list_tags");
			iname = getIname();
			
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
	
	public void createHTML(Tags tags,int i,JspWriter out) throws IOException{
		
		String str=tags.getValue();
		if(str==null) str="";
		
		if(tags.getDisplay_type().equals("文本")||
				tags.getDisplay_type().equals("实数")||
				tags.getDisplay_type().equals("整数")||
				tags.getDisplay_type().equals("下拉选项") || 
				tags.getDisplay_type().equals("单选") || 
				tags.getDisplay_type().equals("复选") || 
				tags.getDisplay_type().equals("城市")){
			if(tags.getIs_calculate() == 1){
				out.println("<label style='padding-left:10px;color:red' >"+str+"</label>");
			}else{
				out.println("<label style='padding-left:10px' >"+str+"</label>");
			}
		}else if(tags.getDisplay_type().equals("百分比")){
			if(tags.getJindutiao() == 1){//显示进度条
				
				str = !str.equals("") ?String.valueOf(new BigDecimal(str).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()):"";
				out.println("<table width='300px' border='0' cellspacing='1' ><tr><td><div  id='jindutiao_div__"+tags.getColumn_name()+":"+i+"' style='border: 1px dotted #FF6633;width:280px'><img height='14' src='images/jd.gif' width='"+str+"%'  id='show_jindutiao__"+tags.getColumn_name()+":"+i+"'/></div></td><td><center><span style='color:red;'>"+str+"%</span></center></td></tr></table>");
			}
		}else if(tags.getDisplay_type().equals("日期")){
			out.println("<label style='padding-left:10px' >"+str+"</label>");
		}else if(tags.getDisplay_type().equals("相关字段")){
			if(!str.equals("")){
				String values[] = str.split(";");
				String tmp="";
				if( tags.getValue2()!=null)
					tmp= tags.getValue2();
				String relateid [] = tmp.split(",");
				String relatename=tags.getColumn_name();
				String relatetablename=tags.getTable_name();
			
				String relate_tname[]=tags.getDefault_value().split("==");
				if(values!=null&&!values[0].equals(""))
				{
					for(int j=0;j<values.length;j++)
					{
						out.println("<span style='background-color:white'>" +
								"<a href='viewContactTags.action?tablename="+relate_tname[0]+"&id="+relateid[j]+"'  >"+values[j]+"</a>" +
								"</span>");
						
						if(j+1!=values.length)
							out.println("<span style='background-color:red'>|</span>");
					}
				}
			}
		}else if(tags.getDisplay_type().equals("相关负责人")){
			if(!str.equals("")){
				String values[] = str.split(";");
				String tmp="";
				if( tags.getValue2()!=null)
					tmp= tags.getValue2();
				
				
				if(values!=null&&!values[0].equals(""))
				{
					for(int j=0;j<values.length;j++)
					{
						out.println("<span style='background-color:white'>" +
								""+values[j]+""+
						"</span>");
						
						if(j+1!=values.length)
							out.println("<span style='background-color:red'>|</span>");
					}
				}
			}
		}else if(tags.getDisplay_type().equals("附件上传")){
			if(!str.equals("")){
				String str2[]=str.split("==");
				if(str2.length>1)
				{
					out.println("<label  style='padding-left:10px'  cols='40' rows='10' name='"+tags.getColumn_name()+"' " +
							"  readonly  ><a href='downloadStuff.action?down="+str+"' > "+str2[0]+"</a></label>");
				}
			}
		}else if(tags.getDisplay_type().equals("大文本")){
			out.println("<label>"+str+"</label>");
		}else if(tags.getDisplay_type().equals("图片")){
			String height="";
			String width="";
			
			if(!str.equals("")){
				String str3[]=str.split("==");
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
			}
		}else if(tags.getDisplay_type().equals("富文本")){
			out.println(" <TABLE cellSpacing=1 cellPadding=3 width=100%>");
			for(int j=0;j<list_ricktext.size();j++)
			{
				if(list_tags.get(list_ricktext.get(j)).getValue()==null)
					list_tags.get(list_ricktext.get(j)).setValue("");
				out.println(" <TBODY><TR><TD height='30' colspan='2' align='center'>"+list_tags.get(list_ricktext.get(j)).getName_display()+"</TD></TR><TR>");
				
				out.println("<TD colspan='2' align='left' bgcolor='#FFFFFF' style='padding:10px;'>"+list_tags.get(list_ricktext.get(j)).getValue()+"</TD></TR> "
						+	" <TR>  <TD></TD> <TD></TD> </TR> </TBODY></TABLE>");
				break;
			}

			out.println("</div>");
		}
	}

	public List<Tags> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<Tags> list_tags) {
		this.list_tags = list_tags;
	}

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public List<Integer> getList_ricktext() {
		return list_ricktext;
	}

	public void setList_ricktext(List<Integer> list_ricktext) {
		this.list_ricktext = list_ricktext;
	}
	
	

}
