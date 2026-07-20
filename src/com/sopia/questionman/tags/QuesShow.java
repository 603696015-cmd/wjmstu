package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ExamPaperUtil;
import com.sopia.questionman.entities.Question;

public class QuesShow extends TagSupport {
	private static final Log logger = LogFactory.getLog(QuesShow.class);
	private static final long serialVersionUID = 934253425346L;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Question q = (Question) request.getAttribute("question");
			writeEPQs(out, q);
		} catch (Exception ex) {
			logger.error("试题显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	
	private void writeEPQs(JspWriter out,  Question  qj)	throws Exception {
			out.println("<div class='question'>");
			out.println("<b>标题</b><p>" +  qj.getContent_()+"</p> ");
			if(qj.getQtype()==7){//材料题
				List<Question> qjc =qj.getChilds();
				for (int i = 0; i < qjc.size(); i++) {
					out.println("<div class='question1'>");
					Question qjci = qjc.get(i);
					out.println("<b>第"+(i+1)+"题</b> <p>" +  qjci.getContent_()+"</p> ");
					writeEPQsComm(out, qjci,0 );
					out.println("</div><br>");
				}
			}else{
				writeEPQsComm(out,qj ,0);
			}
			out.println("</div><br>");
	}
	private void writeEPQsComm(JspWriter out,Question qj,int blockid ) throws Exception{
		if(qj.getQtype()==1){
			writeYesOrNo(out, qj,blockid );
		}
		if(qj.getQtype()==2||qj.getQtype()==3||qj.getQtype()==4 ){
			writeSelect(out, qj,blockid );
		}
		if(qj.getQtype()==5){
			writeBlank(out, qj,blockid );
		}
		if(qj.getQtype()==6){
			writeEssay(out, qj,blockid );
		}
	}
	private void writeYesOrNo(JspWriter out,Question qj ,int blockid ) throws Exception{
//		out.println("<p>" + qj.getContent()+ "</p>");
		out.println("<div class='answer'><b>填写答案</b>：");
		if(null==qj.getStuAnswers()||"".equals(qj.getStuAnswers()[0])){
			out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" value=\"yes\" />正确");
			out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" value=\"no\" />错误");
			out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" checked='checked' style='display:none' value=\"\" /></div>");
			
			return ;
		}else{
			if(qj.getStuAnswers()[0].equals("yes"))
				out.println("<input name=\""+blockid+"_"+qj.getId()+"\"  checked='checked' type=\"radio\" value=\"yes\" />正确");
			else 
				out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" value=\"yes\" />正确");
			
			if(qj.getStuAnswers()[0].equals("no"))
				out.println("<input name=\""+blockid+"_"+qj.getId()+"\"  checked='checked' type=\"radio\" value=\"no\" />错误");
			else 
				out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" value=\"no\" />错误");
			if(qj.getStuAnswers()[0].trim().equals(""))
				out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" style='display:none' checked='checked' value=\"\" /></div>");
			else
				out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" style='display:none' value=\"\" /></div>");
			}
	}
	private void writeSelect(JspWriter out,Question qj ,int blockid ) throws Exception{
//		out	.println("<p>" +  qj.getContent()+"</p>");
		out.println("<p>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>"+ExamPaperUtil.getABC(i)+":</b>"+qj.getOptions()[i]+"<br>");
		}
		out.print("</p>");
		
		out.println("<div  class='answer'><b>填写答案</b>：");
		for (int i = 0; i < qj.getOptions().length; i++) {
			if(qj.getQtype()==2){
				if(checkSAnswer(qj, i))
					out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" value=\""+i+"\"  checked='checked'/>"+""+ExamPaperUtil.getABC(i ));
				else
					out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" value=\""+i+"\" />"+""+ExamPaperUtil.getABC(i));
			}
			else{
				if(checkSAnswer(qj, i))
					out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"checkbox\" value=\""+i+"\" checked='checked'/>"+""+ExamPaperUtil.getABC(i));
				else
					out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"checkbox\" value=\""+i+"\" />"+""+ExamPaperUtil.getABC(i));
			}
		}
		if(qj.getQtype()==2){
			if(null==qj.getStuAnswer()||null==qj.getStuAnswers()[0]||"".equals(qj.getStuAnswers()[0].trim()))
			out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"radio\" value=\"\" style='display:none;'  checked='checked'/>" );
		}
			else
			out.println("<input name=\""+blockid+"_"+qj.getId()+"\" type=\"checkbox\" value=\"\" style='display:none;'  checked='checked' />") ;
		
		out.println("</div>");
	}
	private boolean checkSAnswer(Question q,int i){
		if(null!=q.getStuAnswers())
		for (int j = 0; j < q.getStuAnswers().length; j++) {
			if(!q.getStuAnswers()[j].trim().equals(""))
				if(i==new Integer(q.getStuAnswers()[j])) return true;
		}
		return false;
	}
	private void writeBlank(JspWriter out,Question qj ,int blockid ) throws Exception{
//		out.println("<p>" +  qj.getContent()+"</p> ");
		out.println( "<div class='answer'> <b>填写答案</b>：");
		
		for (int i = 0; i < qj.getAnswers().length; i++) {
			if(null!=qj.getStuAnswers())
				out.println(" <br><font class=\"xuxian\">空白答案"+( i +1)+"</font>&nbsp;"
						+ " <input name=\""+blockid+"_"+qj.getId()+"\" type=\"text\"  value=\""+qj.getStuAnswers()[i]+"\"/>  ");
			else
				out.println(" <br><font class=\"xuxian\">空白答案"+( i +1)+"</font>&nbsp;"
						+ " <input name=\""+blockid+"_"+qj.getId()+"\" type=\"text\"  value=\"\"/>  ");
				
		}
		out.println("</div>");
	}
	private void writeEssay(JspWriter out,Question qj ,int blockid ) throws Exception{
		out.println(" <div class='answer'><b>填写答案</b>：");
		if(null!=qj.getStuAnswers())
			out.println("<textarea cols=30 rows=5 name=\""+blockid+"_"+qj.getId()+"\" >"+qj.getStuAnswers()[0]+"</textarea></div>");
		else
			out.println("<textarea cols=30 rows=5 name=\""+blockid+"_"+qj.getId()+"\" ></textarea></div>");
		
	}
}
