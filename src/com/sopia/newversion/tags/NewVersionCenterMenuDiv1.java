package com.sopia.newversion.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.ElFunc;

public class NewVersionCenterMenuDiv1 extends TagSupport {
	public final static int size = 12;
	private List<ElFunc> menus;
	private List<ElFunc> menus_three_cycz;
	private List<ElFunc> menus_all;
	private ElFunc menu;
	private String funcName;

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		menus = (List<ElFunc>) request.getAttribute("menus_three");
		menus_three_cycz = (List<ElFunc>) request.getAttribute("menus_three_cycz");
		menus_all = (List<ElFunc>) request.getAttribute("menus");
		funcName  = (String) request.getAttribute("funcName");
		System.out.println(menus.size());
		System.out.println(menus_three_cycz.size());
		System.out.println(menus_all.size());
		for(ElFunc el:menus_all){
			System.out.println(el.getFunccode());
		}
		System.out.println(menus_all.get(0).getFunccode());
		System.out.println(menus_all.get(1).getFunccode());

		try {
			outPut(out, menus);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TagSupport.SKIP_BODY;
	}
	
	/**
	 * <div class="slide slide-1" id="tabs_1_cont_1">
			<DIV id=menu_bg>
				<DIV class=menu>
					<LI>
						<A href="/"><img style="margin-top: 10px;"
								src="images/caidan012.png" width="48" height="48" />
							<br />
							<div style="margin-top: -8px; font-size: 12px;">
								全部课程
							</div> </A>
					</LI>

				</DIV>
			</DIV>
		</div>
	 */
//	public void outPutOne(JspWriter out,int j,List<ElFunc> menus,int mod,int level) throws IOException {
//		String actionName = "";
//		String params = "";
//		String picsrc = "";
//		int start = 0;
//		int end = 0;
//		start = (j - 1) * size;
//		end = start + size;
//		if(j == level){
//			end = start + mod;
//		}
//		for(int i = start;i<end;i++){
//			actionName = menus.get(i).getFunccode() ;
//			params = menus.get(i).getParams();
////			if(params!=null&&!params.equals("")){
////				actionName = "newversion.action?"+"funccode="+actionName + "&"  + params;
////			}else{
////				actionName = "newversion.action?"+"funccode="+actionName;
////			}
//			if(params!=null&&!params.equals("")){
//				actionName = actionName + ".action?" + params+"&ppp=nihao";
//			}else{
//				actionName = actionName + ".action?ppp=nihao" ;
//			}
//			if(menus.get(i).getLinkimg()==null||menus.get(i).getLinkimg().equals("")){
//				picsrc = "images/default_link.jpg";
//			}else{
//				if(!menus.get(i).getLinkimg().equals("default_link.jpg")){
//					picsrc = menus.get(i).getLinkMainimg();
//				}else{
//					picsrc = "images/default_link.jpg";
//				}
//			}
//			if(i == start){
//				out.println("<div class='slide slide-"+j+"' id='tabs_1_cont_"+j+"'>");
//				out.println("<DIV id=menu_bg>");
//				out.println("<DIV class=menu>");
//				out.println("<LI>"+
//						"<A href=\""+actionName+"\"><img style=\"margin-top: 10px;\""+
//								"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
//							"<br />"+
//							"<div style=\"margin-top: -8px; font-size: 12px;\">"+
//								menus.get(i).getName()+
//							"</div> </A>"+
//					"</LI>");
//			}else if(i == end-1){
//				out.println("<LI>"+
//						"<A href=\""+actionName+"\"><img style=\"margin-top: 10px;\""+
//								"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
//							"<br />"+
//							"<div style=\"margin-top: -8px; font-size: 12px;\">"+
//								menus.get(i).getName()+
//							"</div> </A>"+
//					"</LI>");
//				out.println("</DIV>");
//				out.println("</DIV>");
//				out.println("</div>");
//			}else{
//				out.println("<LI>"+
//						"<A href=\""+actionName+"\"><img style=\"margin-top: 10px;\""+
//								"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
//							"<br />"+
//							"<div style=\"margin-top: -8px; font-size: 12px;\">"+
//								menus.get(i).getName()+
//							"</div> </A>"+
//					"</LI>");
//			}
//		}
//	}

	
	public void outPutOne(JspWriter out,int j,List<ElFunc> menus,int mod,int level) throws IOException {
		String actionName = "";
		String params = "";
		String picsrc = "";
		int start = 0;
		int end = 0;
		start = (j - 1) * size;
		end = start + size;
		
		System.out.println(start);
		System.out.println(end);
		if(j == level){
			end = start + mod;
		}
		System.out.println(end);
		for(int i = start;i<menus_three_cycz.size();i++){
			actionName = menus_three_cycz.get(i).getFunccode() ;
			params = menus_three_cycz.get(i).getParams();
//			if(params!=null&&!params.equals("")){
//				actionName = "newversion.action?"+"funccode="+actionName + "&"  + params;
//			}else{
//				actionName = "newversion.action?"+"funccode="+actionName;
//			}
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
				out.println("<LI>"+
						"<A href=\""+actionName+"\"><img style=\"margin-top: 10px;\""+
								"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
							"<br />"+
							"<div style=\"margin-top: -8px; font-size: 12px;\">"+
							menus_three_cycz.get(0).getName()+
							"</div> </A>"+
					"</LI>");
			}else {
				
				
				out.println("<LI>"+
						"<A href=\""+actionName+"\"><img style=\"margin-top: 10px;\""+
								"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
							"<br />"+
							"<div style=\"margin-top: -8px; font-size: 12px;\">"+
							menus_three_cycz.get(i).getName()+
							"</div> </A>"+
					"</LI>");
			}}
		
		for(int i= menus_three_cycz.size();i<end;i++){
			if(i==end-1){
				out.println("<LI>"+
						"<A onclick=\"showStemText('"+menus_all.get(end-i).getFunccode()+"');\"><img style=\"margin-top: 10px;\""+
								"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
							"<br />"+
							"<div style=\"margin-top: -8px; font-size: 12px;\">"+
							menus_all.get(end-i).getFunccode()+
							"</div> </A>"+
					"</LI>");
				out.println("</DIV>");
				out.println("</DIV>");
				out.println("</div>");
			}else{
				out.println("<LI>"+
//						"<A onclick=\"document.getElementById('div_"+(end-i-1)+"').style.display=(document.getElementById('div_"+(end-i-1)+"').style.display=='none')?'':'none';return false;\"><img style=\"margin-top: 10px;\""+
						//"<A onclick=\"check('"+menus_all.get(end-i).getFunccode()+"')\"><img style=\"margin-top: 10px;\""+
						"<A onclick=\"showStemText('"+menus_all.get(end-i).getFunccode()+"');\"><img style=\"margin-top: 10px;\""+
						"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
							"<br />"+
							"<div style=\"margin-top: -8px; font-size: 12px;\">"+
							menus_all.get(end-i).getFunccode()+
							"</div> </A>"+
					"</LI>");
			}
			
			
			System.out.println(menus_all.get(end-i).getFunccode());
			if(menus_all.get(end-i).getFunccode().equals("我的培训班")){
				menu=menus_all.get(end-i);
				for(int k=0;k<menu.getChild().size();k++){
					System.out.println("======"+menu.getChild().get(k).getFunccode());
					System.out.println("======"+menu.getChild().get(k).getName());
				}
			}
			if(menus_all.get(end-i).getFunccode().equals("我的课程")){
				menu=menus_all.get(end-i);
				for(int k=0;k<menu.getChild().size();k++){
					System.out.println("======"+menu.getChild().get(k).getFunccode());
					System.out.println("======"+menu.getChild().get(k).getName());
				}
			}
		}
//		out.println("<script type=\"text/javascript\">\n");
//		out.println(" function check(username){");
//		out.println(" alert(username);");
//		out.println(" document.getElementById(id).style.display='block';");
//		out.println(" document.getElementById('tabs_1_cont_0').style.display='block';");
//		
//		out.println(" } </script>");
		
		
		
	}
	
	
	public void outPut(JspWriter out, List<ElFunc> menus) throws IOException {
		out.println("<div class=\"in widget-slide\"  id=\"div_11111\" data-jss=\"tabSelector : '.slide-nav li',"+
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
		
//		if (menus != null && menus.size() > 0) {
//			level_count = menus.size() / size;
//			mod =  menus.size() % size;
//			if(mod > 0 ){
//				level_count++;
//			}
//			for(int j=1;j<=level_count;j++){
//				outPutOne(out,j,menus,mod,level_count);
//			}
//		}
		
		if (menus != null && menus_three_cycz.size()> 0&&menus_all.size()>0) {
			level_count = (menus_three_cycz.size()+menus_all.size()-1) / size;
			mod =  (menus_three_cycz.size()+menus_all.size()-1) % size;
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
//		out.println("<div   class=\"slide-content\" style=\" overflow:hidden;width:710px;position:relative;\" id=\"picslide1_scroll_div\">");
//		out.println("<div style=\"width:5000px;position:relative;\">");
//		out.println("</div>");
//		out.println("</div>");
//		
//		for(int h=0;h<menus_all.size()-1;h++){
//			String actionName = "";
//			String params = "";
//			String picsrc = "";
//			System.out.println("end===="+(menus_three_cycz.size()+menus_all.size()-1));
//			menu=menus_all.get(menus_three_cycz.size()+menus_all.size()-1-menus_three_cycz.size()-h);
//			out.println("<div class=\"in widget-slide\"   id=\"div_"+h+"\" data-jss=\"tabSelector : '.slide-nav li',"+
//					"viewSelector : '.slide',"+
//					"autoPlay: true,"+
//					"supportMouseenter: false,"+
//					"mouseenterSwitchTime: 10,"+
//					"animType:'scroll',"+
//					"autoPlayTime:4000\">");
//				out.println("<div class=\"slide-content\" style=\"overflow:hidden;width:710px;position:relative;\" id=\"picslide1_scroll_div\">");
//				out.println("<div style=\"width:5000px;position:relative;\">");
//			for(int k=0;k<menu.getChild().size();k++){
//				actionName = menu.getChild().get(k).getFunccode();
//				params = menu.getChild().get(k).getParams();
////				if(params!=null&&!params.equals("")){
////					actionName = "newversion.action?"+"funccode="+actionName + "&"  + params;
////				}else{
////					actionName = "newversion.action?"+"funccode="+actionName;
////				}
//				if(params!=null&&!params.equals("")){
//					actionName = actionName + ".action?" + params+"&ppp=nihao";
//				}else{
//					actionName = actionName + ".action?ppp=nihao" ;
//				}
//				if(menus.get(k).getLinkimg()==null||menus.get(k).getLinkimg().equals("")){
//					picsrc = "images/default_link.jpg";
//				}else{
//					if(!menus.get(k).getLinkimg().equals("default_link.jpg")){
//						picsrc = menus.get(k).getLinkMainimg();
//					}else{
//						picsrc = "images/default_link.jpg";
//					}
//				}
//				
//				if(k == 0){
//					out.println("<div class='slide slide-"+h+"' id='tabs_1_cont_"+h+"'  >");
//					out.println("<DIV id=menu_bg>");
//					out.println("<DIV class=menu>");
//					out.println("<LI>"+
//							"<A href=\""+actionName+"\"><img style=\"margin-top: 10px;\""+
//									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
//								"<br />"+
//								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
//								menu.getChild().get(k).getName()+
//								"</div> </A>"+
//						"</LI>");
//				}else if(k == menu.getChild().size()-1){
//					out.println("<LI>"+
//							"<A href=\""+actionName+"\"><img style=\"margin-top: 10px;\""+
//									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
//								"<br />"+
//								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
//								menu.getChild().get(k).getName()+
//								"</div> </A>"+
//						"</LI>");
//					out.println("</DIV>");
//					out.println("</DIV>");
//					out.println("</div>");
//				}else{
//					out.println("<LI>"+
//							"<A href=\""+actionName+"\"><img style=\"margin-top: 10px;\""+
//									"src=\""+picsrc+"\" width=\"48\" height=\"48\" />"+
//								"<br />"+
//								"<div style=\"margin-top: -8px; font-size: 12px;\">"+
//								menu.getChild().get(k).getName()+
//								"</div> </A>"+
//						"</LI>");
//				}
//				
//			}
//			out.println("</div>");
//			out.println("</div>");
//			out.println("</div>");
//			
//		}
//		out.println("<script type=\"text/javascript\">\n" + "<!--\n");
//		out.println(" function clickDiv(div){");
//		out.println(" alert(div);");
//		out.println(" document.getElementById('tabs_1_cont_1').style.display='none';");
//		out.println(" document.getElementById('tabs_1_cont_0').style.display='block';");
//		
//		out.println(" } </script>");
		
	}

//	public void writeChilds(JspWriter out, Object obj) throws Exception {
//		String actionName = "";
//		String params = "";
//		String picsrc = "";
//		int start = 0;
//		int end = 0;
//		start = (j - 1) * size;
//		end = start + size;
//		
//		System.out.println(start);
//		System.out.println(end);
//		if(j == level){
//			end = start + mod;
//		}
//		System.out.println(end);
//		if(menus_all.get(end-i).getFunccode().equals("我的培训班")){
//			menu=menus_all.get(end-i);
//			for(int k=0;k<menu.getChild().size();k++){
//				System.out.println("======"+menu.getChild().get(k).getFunccode());
//				System.out.println("======"+menu.getChild().get(k).getName());
//			}
//		}
//	}
	
	public List<ElFunc> getMenus() {
		return menus;
	}

	public void setMenus(List<ElFunc> menus) {
		this.menus = menus;
	}


	public List<ElFunc> getMenus_all() {
		return menus_all;
	}

	public void setMenus_all(List<ElFunc> menus_all) {
		this.menus_all = menus_all;
	}

	public List<ElFunc> getMenus_three_cycz() {
		return menus_three_cycz;
	}

	public void setMenus_three_cycz(List<ElFunc> menus_three_cycz) {
		this.menus_three_cycz = menus_three_cycz;
	}

	public ElFunc getMenu() {
		return menu;
	}

	public void setMenu(ElFunc menu) {
		this.menu = menu;
	}

}
