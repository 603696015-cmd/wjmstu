package com.sopia.userDemo.tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.duman.entities.ELUserDemo;
import com.sopia.userDemo.UserDemoUtil;
import com.sopia.userDemo.entities.ELUserColumn;
import com.sopia.userDemo.entities.ELUserColumnJs;
import com.sopia.userDemo.entities.ELUserJs;
import com.sopia.userDemo.entities.ELUserPage;
import com.sun.star.io.IOException;

/**
 * 各个页面解析字段的HTML输出
 * @author Administrator
 *
 */
public class UserDemoHTML extends ElTag{
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(UserDemoHTML.class);
	
	private String columnname;
	private int pageid;
	private String css;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			List<ELUserColumn> columns = null;
			List<ELUserJs> jsTypes = null;
			Map<Integer,String> jsHTML = null;
			Map<String,String> columnHTML = null;
			
			Map<String,Object> object = null;
			object = (Map<String,Object>) request.getAttribute("object");//修改Init、个人修改Init时候查询到的eluser
			
			columns = (List<ELUserColumn>)request.getAttribute("columns");
			if(columns!=null){
				columnHTML = ColumnHTMLMap(columns,object);
			}
			
			jsTypes = (List<ELUserJs>)request.getAttribute("jsTypes");
			if(jsTypes !=null){
				out.println("<script type=\"text/javascript\">");
				jsHTML = this.JsHTMLMap(jsTypes,columns);
				out.println("</script>");
			}
			
			if (columns == null)
				return TagSupport.SKIP_BODY;
			
			
			writeChilds(out, columns,jsHTML,columnHTML);
		} catch (Exception ex) {
			logger.error("",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	
	//JS验证MAP
	public Map<Integer,String> JsHTMLMap(List<ELUserJs> jsTypes,List<ELUserColumn> columns){
		Map<Integer,String> map = new HashMap<Integer,String>();
		String jsHTML = "";
		if(getPageid()==1 || getPageid()==2 || getPageid()==4 || getPageid()==5)//添加页面、修改页面、注册页面、个人修改页面
		for(int i=0;i<jsTypes.size();i++){
			if(jsTypes.get(i).getId() == 1){//数字
				jsHTML = "jquery.validator.addMethod("+getColumnname()+"_1,function(value, element){) \n" +
						" 	return this.optional(element) || /^[\\d]{"+jsTypes.get(i).getLlength()+","+jsTypes.get(i).getRlength()+"}$/.test(value); \n" +
						" },\"输入错误，只能输入数字且区间为"+jsTypes.get(i).getLlength()+"到"+jsTypes.get(i).getRlength()+"位\"); \n";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}else if(jsTypes.get(i).getId() == 2){//字母
				jsHTML = "jquery.validator.addMethod("+getColumnname()+"_2,function(value, element){) \n" +
						" 	return this.optional(element) || /^\\w{"+jsTypes.get(i).getLlength()+","+jsTypes.get(i).getRlength()+"}$/.test(value); \n" +
						" },\"输入错误，应输入"+jsTypes.get(i).getLlength()+"到"+jsTypes.get(i).getRlength()+"个字母\"); \n";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}else if(jsTypes.get(i).getId() == 3){//数字和字母
				jsHTML = "jquery.validator.addMethod("+getColumnname()+"_3,function(value, element){) \n" +
						" 	return this.optional(element) || "+getColumnname()+"_3(value);  \n" +
						"},\"输入错误，应输入"+jsTypes.get(i).getLlength()+"到"+jsTypes.get(i).getRlength()+"个数字,"+jsTypes.get(i).getLlength2()+"到"+jsTypes.get(i).getRlength2()+"个字母\"); \n";
				jsHTML  += getColumnname() + "_3(){ \n" + 
								" if(/^[\\d]{"+jsTypes.get(i).getLlength()+","+jsTypes.get(i).getRlength()+"}$/.test(value) && /^\\w{"+jsTypes.get(i).getLlength2()+","+jsTypes.get(i).getRlength2()+"}$/.test(value) ) \n" + 
								"	return true; \n" +
								"}else{ \n" + 
								"	return false; \n" +
								"} \n" + 
							"} \n";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}else if(jsTypes.get(i).getId() == 4){//中文和字母
				jsHTML = "jquery.validator.addMethod("+getColumnname()+"_4,function(value, element){) \n" +
						" 	return this.optional(element) || "+getColumnname()+"_4(value);  \n" +
						"},\"输入错误，应输入"+jsTypes.get(i).getLlength()+"到"+jsTypes.get(i).getRlength()+"个中文,"+jsTypes.get(i).getLlength2()+"到"+jsTypes.get(i).getRlength2()+"个字母\"); \n";
				jsHTML  += getColumnname() + "_4(){ \n" + 
						" 	if(/^[^u4E00-u9FA5\\w]{"+jsTypes.get(i).getLlength()+","+jsTypes.get(i).getRlength()+"}$/.test(value) && /^\\w{"+jsTypes.get(i).getLlength2()+","+jsTypes.get(i).getRlength2()+"}$/.test(value) )" + 
						"		return true; \n" +
						"	}else{ \n" + 
						"		return false; \n" +
						"	} \n" +
						"} \n";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}else if(jsTypes.get(i).getId() == 5){//中文和数字
				jsHTML = "jquery.validator.addMethod("+getColumnname()+"_5,function(value, element){) \n" +
						" 	return this.optional(element) || "+getColumnname()+"_5(value);  \n" +
						"},\"输入错误，应输入"+jsTypes.get(i).getLlength()+"到"+jsTypes.get(i).getRlength()+"个中文,"+jsTypes.get(i).getLlength2()+"到"+jsTypes.get(i).getRlength2()+"个字母\"); \n";
				jsHTML  += getColumnname() + "_5(){ \n" + 
						" 	if(/^[^u4E00-u9FA5\\w]{"+jsTypes.get(i).getLlength()+","+jsTypes.get(i).getRlength()+"}$/.test(value) && /^[\\d]{"+jsTypes.get(i).getLlength2()+","+jsTypes.get(i).getRlength2()+"}$/.test(value) )" + 
						"		return true; \n" +
						"	}else{ \n" + 
						"		return false; \n" +
						"	} \n" +
						"} \n";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}else if(jsTypes.get(i).getId() == 6){//中文
				jsHTML = "jquery.validator.addMethod("+getColumnname()+"_6,function(value, element){) \n" +
						" 	return this.optional(element) || /^[^u4E00-u9FA5\\w]{"+jsTypes.get(i).getLlength()+","+jsTypes.get(i).getRlength()+"}$/.test(value); \n" +
						" },\"输入错误，应输入"+jsTypes.get(i).getLlength()+"到"+jsTypes.get(i).getRlength()+"个中文\"); \n";
			}else if(jsTypes.get(i).getId() == 7){//身份证
				jsHTML = "jquery.validator.addMethod("+getColumnname()+"_7,function(value, element){) \n" +
						" 	return this.optional(element) || isIdCardNo(value); \n" +
						" },\"请正确输入身份证号码\"); \n";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}else if(jsTypes.get(i).getId() == 8){//唯一
				jsHTML = "";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}else if(jsTypes.get(i).getId() == 9){//数字和字母和中文
				jsHTML = "jquery.validator.addMethod("+getColumnname()+"_9,function(value, element){) \n" +
						" 	return this.optional(element) || "+getColumnname()+"_9(value);  \n" +
						"},\"输入错误，应输入"+jsTypes.get(i).getLlength()+"到"+jsTypes.get(i).getRlength()+"个数字,"+jsTypes.get(i).getLlength1()+"到"+jsTypes.get(i).getRlength1()+"个字母,"+jsTypes.get(i).getLlength2()+"到"+jsTypes.get(i).getRlength2()+"个中文\"); \n";
				jsHTML  += getColumnname() + "_9(){ \n" + 
						" 	if(/^[\\d]{"+jsTypes.get(i).getLlength()+","+jsTypes.get(i).getRlength()+"}$/.test(value) && /^\\w{"+jsTypes.get(i).getLlength1()+","+jsTypes.get(i).getRlength1()+"}$/.test(value) && /^[^u4E00-u9FA5\\w]{"+jsTypes.get(i).getLlength2()+","+jsTypes.get(i).getRlength2()+"}$/.test(value) )" + 
						"		return true; \n" +
						"	}else{ \n" + 
						"		return false; \n" +
						"	} \n" +
						"} \n";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}else if(jsTypes.get(i).getId() == 10){//不限
				jsHTML = "";
				map.put(jsTypes.get(i).getId() , jsHTML);
			}
		}
		return map;
	}
	
	
	/**
	 * 参数构造
	 * @param css
	 * @param csses
	 * @param array
	 * @param default_select
	 * @return
	 */
	public static Map<String,Object> putKey(String css,int pageid,String columnname){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("css", css);
		params.put("pageid", pageid);
		params.put("columnname", columnname);
		return params;
	}
	
	//columnname输出Map
	@SuppressWarnings("static-access")
	public Map<String,String> ColumnHTMLMap(List<ELUserColumn> columns,Map<String,Object> object) {
		Map<String,String> map = new HashMap<String,String>();
		String css = getCss()==null?"":getCss();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params = putKey(css,getPageid(),getColumnname());
		//0：文本框、1：单选、2：复选、3：下拉选项、4：大文本、5：数字、6：图片、7：附件、8：视频
		for(int i=0;i<columns.size();i++){
			map = UserDemoUtil.getColumnHTMLByPageid(getPageid(),object,columns.get(i), params);
		}
		return map;
	}
	
	
	
	
	
	@Override
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		// TODO Auto-generated method stub
	}


	@SuppressWarnings("unchecked")
	public void writeChilds(JspWriter out, Object obj,Map<Integer,String> jsHTMLMap,Map<String,String> columnHTML) throws java.io.IOException {
		List<ELUserColumn> columns = (List<ELUserColumn>)(obj);
		if(columns != null){
			for(int i=0;i<columns.size();i++){
				//输出JS校验HTML
				outPutJs(columns.get(i),jsHTMLMap,out);
				//输出HTML
				outPutHTML(getColumnname(),columnHTML,out);
			}
		}
	}
	
	//输出JS校验HTML
	public void outPutJs(ELUserColumn column,Map<Integer,String> jsHTMLMap, JspWriter out) throws java.io.IOException{
		List<ELUserColumnJs> elUserJses = column.getElUserJses();
		if(elUserJses!=null){
			for(int i=0;i<elUserJses.size();i++){
				//输出某一个JS验证
				outPutJs(elUserJses.get(i),jsHTMLMap,out);
			}
		}
	}
	
	//输出某一个JS校验
	public void outPutJs(ELUserColumnJs js,Map<Integer,String> jsHTMLMap,JspWriter out) throws java.io.IOException{
		String check_js_type = js.getCheck_js_type();
		String[] types = null;
		if(check_js_type!=null&&!check_js_type.equals("")){
			types = check_js_type.split("===");
			for(int i=0;i<types.length;i++){
				outPutJs(Integer.parseInt(types[i]),jsHTMLMap,out);
			}
		}
	}
	
	//根据系统JS，判断该输出的JS
	public void outPutJs(int jsid,Map<Integer,String> jsHTMLMap,JspWriter out) throws  java.io.IOException{
		out.println(jsHTMLMap.get(jsid));
	}
	
	//输出HTML
	public void outPutHTML(String columnname,Map<String,String> columnHTML,JspWriter out) throws java.io.IOException{
		out.println(columnHTML.get(columnname));
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public int getPageid() {
		return pageid;
	}

	public void setPageid(int pageid) {
		this.pageid = pageid;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}
	
	
	

}
