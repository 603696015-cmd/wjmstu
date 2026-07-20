package com.sopia.newversion.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.ElFunc;

public class NewVersionCenterMenuDiv extends TagSupport {
	public final static int size = 12;
	private List<ElFunc> menus;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		menus = (List<ElFunc>) request.getAttribute("menus_three");

		try {
			outPut(out, menus);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TagSupport.SKIP_BODY;
	}
	public void outPutOne(JspWriter out,int j,List<ElFunc> menus,int mod,int level) throws IOException {
		String actionName = "";
		String params = "";
		String picsrc = "";
		String target = "";
		int openType ;
		int start = 0;
		int end = 0;
		start = (j - 1) * size;
		end = start + size;
		if(j == level){
			end = start + mod;
		}
		for(int i = start;i<end;i++){
			actionName = menus.get(i).getFunccode() ;
			params = menus.get(i).getParams();
			target = menus.get(i).getTarget();
			target = (target!=null&&target.equals("_blank"))?"_blank":"";
			openType = target.equals("_blank")?1:0;
			if(params!=null&&!params.equals("")){
				actionName = actionName + ".action?" + params+"&ppp=nihao";
			}else{
				actionName = actionName + ".action?ppp=nihao" ;
			}
			if(menus.get(i).getLinkimg()==null||menus.get(i).getLinkimg().equals("")){
				picsrc = "images/default_link.jpg";
			}else{
				if(!menus.get(i).getLinkimg().equals("default_link.jpg")){
					picsrc = menus.get(i).getLinkMainimg();
				}else{
					picsrc = "images/default_link.jpg";
				}
			}
			if(i == start){
				out.println("<div class='slide slide-"+j+"' id='tabs_1_cont_"+j+"'>");
				out.println("<DIV id=menu_bg>");
				out.println("<DIV class=menu>");
				if(openType == 1){
					out.println("<LI>"+
							"<A href=\""+actionName+"\" target=\""+target+"\"><img style=\"margin-top: 10px;\""+
									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
								"<br />"+
								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
									menus.get(i).getName()+
								"</div> </A>"+
						"</LI>");
				}else{
					out.println("<LI>"+
							"<A href=\"javascript:open('"+actionName+"');\" ><img style=\"margin-top: 10px;\""+
									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
								"<br />"+
								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
									menus.get(i).getName()+
								"</div> </A>"+
						"</LI>");
				}
			}else if(i == end-1){
				if(openType == 1){
					out.println("<LI>"+
							"<A href=\""+actionName+"\" target=\""+target+"\"><img style=\"margin-top: 10px;\""+
									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
								"<br />"+
								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
									menus.get(i).getName()+
								"</div> </A>"+
						"</LI>");
				}else{
					out.println("<LI>"+
							"<A href=\"javascript:open('"+actionName+"');\" ><img style=\"margin-top: 10px;\""+
									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
								"<br />"+
								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
									menus.get(i).getName()+
								"</div> </A>"+
						"</LI>");
				}
				out.println("</DIV>");
				out.println("</DIV>");
				out.println("</div>");
			}else{
				if(openType == 1){
					out.println("<LI>"+
							"<A href=\""+actionName+"\" target=\""+target+"\"><img style=\"margin-top: 10px;\""+
									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
								"<br />"+
								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
									menus.get(i).getName()+
								"</div> </A>"+
						"</LI>");
				}else{
					out.println("<LI>"+
							"<A href=\"javascript:open('"+actionName+"');\" ><img style=\"margin-top: 10px;\""+
									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
								"<br />"+
								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
									menus.get(i).getName()+
								"</div> </A>"+
						"</LI>");
				}
			}
		}
	}

	public void outPut(JspWriter out, List<ElFunc> menus) throws IOException {
		out.println("<div class=\"in widget-slide\" data-jss=\"tabSelector : '.slide-nav li',"+
			"viewSelector : '.slide',"+
			"autoPlay: true,"+
			"supportMouseenter: false,"+
			"mouseenterSwitchTime: 10,"+
			"animType:'scroll',"+
			"autoPlayTime:4000\">");
		out.println("<div class=\"slide-content\" style=\"overflow:hidden;width:710px;position:relative;\" id=\"picslide1_scroll_div\">");
		out.println("<div style=\"width:5000px;position:relative;\">");
		int level_count = 0;
		int mod = 0;
		
		if (menus != null && menus.size() > 0) {
			level_count = menus.size() / size;
			mod =  menus.size() % size;
			if(mod > 0 ){
				level_count++;
			}
			for(int j=1;j<=level_count;j++){
				outPutOne(out,j,menus,mod,level_count);
			}
		}
		out.println("</div>");
		out.println("</div>");
		
		out.println("<ul class=\"tab slide-nav\">");
		for(int i=1;i<=level_count;i++){
			if(i == 1){
				out.println("<li class=\"selected\"><a href=\"javascript:void(0);\" class1=\"item-1\">菜单导航</a></li>");
			}else{
				if(i>3){
					out.println("<li><a href=\"javascript:void(0);\" class=item-3 >更多菜单"+(i-1)+"</a></li>");
				}else{
					out.println("<li><a href=\"javascript:void(0);\" class=item-"+i+">更多菜单"+(i-1)+"</a></li>");
				}
			}
		}
		out.println("</ul>");
		
		out.println("</div>");
	}

	public List<ElFunc> getMenus() {
		return menus;
	}

	public void setMenus(List<ElFunc> menus) {
		this.menus = menus;
	}

}
