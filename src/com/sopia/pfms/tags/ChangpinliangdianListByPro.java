package com.sopia.pfms.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;

import com.sopia.common.ElTag;
import com.sopia.pfms.entities.ProductType;

@SuppressWarnings("serial")
public class ChangpinliangdianListByPro extends ElTag {

	@SuppressWarnings("unchecked")
	public int doStartTag(){
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		String str = getIname();
		try {
			writeChilds(out, str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ElTag.SKIP_BODY;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		String str_liangdian = (String)obj;
		String[] str_array;
		if(str_liangdian.equals("")){
			str_array = null;
		}else{
			str_array = str_liangdian.split(" ");
		}
		
		if(str_array != null ){
			if(str_array.length<=3){//小于等于3个
				out.println("<tr>");
				for(int i=0;i<str_array.length;i++){
					if(str_array[i].length()>=8){
						str_array[i] = str_array[i].substring(0,8);
					}
					out.println("<td width=\"40\"  align=\"center\"><img src=\"images/dhgou.gif\" width=\"15\" ></td><td width=\"28%\">"+str_array[i]+"</td>");
					
					if(3 - str_array.length == 1){//加两个空td
						out.println("<td width=\"40\">&nbsp;</td><td width=\"28%\">&nbsp;</td>");
					}else{
						out.println("<td width=\"40\">&nbsp;</td><td width=\"28%\">&nbsp;</td><td width=\"40\">&nbsp;</td><td width=\"28%\">&nbsp;</td>");
					}
				}
				out.println("</tr>");
			}else{//元素从第4个开始
				out.println("<tr>");
				
				for(int i=0;i<3;i++){
					if(str_array[i].length()>=8){
						str_array[i] = str_array[i].substring(0,8);
					}
					out.println("<td width=\"40\"  align=\"center\"><img src=\"images/dhgou.gif\" width=\"15\" ></td><td width=\"28%\">"+str_array[i]+"</td>");
				}
				out.println("</tr>");
				
				for(int level=0;level<str_array.length / 3;level++){
					if(str_array.length % 3 != 0){//不是3的倍数
						
						
						out.println("<tr>");
						
						for(int i=level * 3 + 3;i<str_array.length  ;i++){
							if(str_array[i].length()>=8){
								str_array[i] = str_array[i].substring(0,8);
							}
							out.println("<td width=\"40\"  align=\"center\"><img src=\"images/dhgou.gif\" width=\"15\" ></td><td width=\"28%\">"+str_array[i]+"</td>");
						}
						if(str_array.length - 3 == 1){//加两个空td
							out.println("<td width=\"40\">&nbsp;</td><td width=\"28%\">&nbsp;</td><td width=\"40\">&nbsp;</td><td width=\"28%\">&nbsp;</td>");
						}else{
							out.println("<td width=\"40\">&nbsp;</td><td width=\"28%\">&nbsp;</td>");
						}
						
						out.println("</tr>");
					}else{//是3的倍数
						out.println("<tr>");
						for(int i= level * 3;i<level + 3;i++){
							if(str_array[i].length()>=8){
								str_array[i] = str_array[i].substring(0,8);
							}
							out.println("<td width=\"40\"  align=\"center\"><img src=\"images/dhgou.gif\" width=\"15\" ></td><td width=\"28%\">"+str_array[i]+"</td>");
						}
						out.println("</tr>");
					}
				}
			}
		}
	}

}
