package com.sopia.answeringsystem.tags;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.answeringsystem.entities.Answer;
import com.sopia.answeringsystem.entities.AnsweringType;
import com.sopia.answeringsystem.entities.Ques;
import com.sopia.common.ElTag;

@SuppressWarnings("serial")
public class NewestAnswersTags extends TagSupport{
	
	@SuppressWarnings("unchecked")
	public int doStartTag(){
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		Map<String,List> listMap = (Map<String,List>) request.getAttribute("listMap");
		List<AnsweringType> types = (List<AnsweringType>) request.getAttribute("answeringTypes");
		try {
			output(out, listMap,types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ElTag.SKIP_BODY;
	}

	@SuppressWarnings("unchecked")
	public void output(JspWriter out, Object obj,Object obj1) throws Exception {
		// TODO Auto-generated method stub
		Map<String,List<Answer>> listMap  = (Map<String,List<Answer>>)obj;
		List<Answer> answers = listMap.get("newestAnswers");
		List<AnsweringType> types = (List<AnsweringType>)obj1;
		String html  = "";
		String m = "";
		String c = "";
		if(types!=null ){
			int temp = 100;
			int t = 100;
			out.println("<div class=\"scrollFrame\">");
			out.println("<ul class=\"scrollUl\">");
			out.println("<div class=\"blankdiv\">&nbsp;</div>");
			for(int i=0;i<types.size();i++){
				if(types.get(i).getParentid() == 1){
					if(temp == t){
						temp++;
						out.println("<li class=\"sd01\" id=m0"+temp+">"+types.get(i).getName()+"</li>");
					}else{
						temp++;
						out.println("<li class=\"sd02\" id=m0"+temp+">"+types.get(i).getName()+"</li>");
					}
					m += "\"m0"+temp+"\",";
				}
			}
			out.println("</ul>");
			
			temp = 100;
			t = 100;
			out.println("<div class=\"bor03 cont\">");
			for(int i=0;i<types.size();i++){
				if(types.get(i).getParentid() == 1){
					if(answers!=null&&answers.size()>0){
						if(temp == t){
							temp++;
							html += "<div class=\"display\" id=c0"+temp+"><ul class=\"anspdlist\">";
							for(int j = 0;j<answers.size();j++){
								if(types.get(i).getId() == answers.get(j).getQues().getAnsweringType().getId()){
									html += "<li><p>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answers.get(j).getAnswerTime())+"</p>用户<span class=\"name\">"+answers.get(j).getAnswerUser().getRealname()+"</span> 对问题<a href=\"#\">“"+answers.get(j).getQues().getName()+"</a>”进行回复　 </li>";
								}
							}
							html += "</ul></div>";
						}else{
							temp++;
							html += "<div class=\"hidden\" id=c0"+temp+"><ul class=\"anspdlist\">";
							for(int j = 0;j<answers.size();j++){
								if(types.get(i).getId() == answers.get(j).getQues().getAnsweringType().getId()){
									html += "<li><p>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answers.get(j).getAnswerTime())+"</p>用户<span class=\"name\">"+answers.get(j).getAnswerUser().getRealname()+"</span> 对问题<a href=\"#\">“"+answers.get(j).getQues().getName()+"</a>”进行回复　 </li>";
								}
							}
							html += "</ul></div>";
						}
						c += "\"c0"+temp+"\",";
					}
				}
			}
			if(!m.equals("")&&String.valueOf(m.charAt(m.lastIndexOf(","))).equals(",")){
				m = m.substring(0,m.lastIndexOf(","));
			}
			if(!c.equals("")&&String.valueOf(c.charAt(c.lastIndexOf(","))).equals(",")){
				c = c.substring(0,c.lastIndexOf(","));
			}
			out.println(html);
			out.println("</div>");
			out.println("</div>");
			
			out.println("<script type=text/javascript>");
			out.println(
					"	jQuery(document).ready(function(){"+
							"SDmodel.sd(["+m+"],["+c+"],\"sd01\",\"sd02\");"+
						"})");
			out.println("</script>");
		}
	}

}
