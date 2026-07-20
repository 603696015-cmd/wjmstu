package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;

public class ExamPaperShow1b1 extends TagSupport {
	private static final Log logger = LogFactory.getLog(ExamPaperShow1b1.class);
	private static final long serialVersionUID = 6536487202613250886L;
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ExamPaper ep = (ExamPaper) request.getAttribute("examPaper");
			List<ExamPaperBlock> epbs = ep.getEpBlocks();
			if(null!=epbs)
			writeEPBs(out, epbs);
			else
				out.println("本试卷不包括任何试题！");
		} catch (Exception ex) {
			logger.error("试卷预览1题1题显示显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	private void writeEPBs(JspWriter out, List<ExamPaperBlock> epbs)
			throws Exception {
		//目录
		out.println("<TD id=page_file vAlign=top width=300 bgColor=#dae9fe "+
				"height=250>");
		int sortid = 0;
		for (int j = 0; j < epbs.size(); j++) {
			ExamPaperBlock epbj = epbs.get(j);
			out.println(epbj.getTitle()+"<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">");
			if(null!=epbj.getQuestions()){
				int count = epbj.getQuestions().size();
				int cols=7;
				int rows = count/cols+1;
				int count1=0;
				for (int i = 0; i <rows; i++) {
					 out.println("<tr>");
					for (int k = 0; k < cols; k++) {
						count1++;
						
						if(count+1<=count1) out.println("<td width=45 height=\"45\" align=\"center\" bgcolor=\"#00CCFF\">"+
								"&nbsp;" +
								"</td>");
						else{
							out.println("<td width=45 height=\"45\" align=\"center\" bgcolor=\"#00CCFF\">" +
									"<a href='javascript:showQ("+sortid+")' >"+
									count1 +
									"</a></td>");
							sortid++;
						}
					}
					out.println("</tr>");
				}
			}
			out.println("</table>") ;
		}
		out.println("</TD>");
		out.println("<TD vAlign=center width=10 "+
										"background=img/bf_r12_c17.jpg rowSpan=2> "+
										"<A href=\"javascript:catalog_switch();\"><IMG "+
										"id=switch_button height=24"+
										" src=\"images/img/yincang.jpg\" width=10 border=0>"+
										"</A>"+
										"</TD>");
		//试题内容
		out.println("<TD vAlign=top height=\"100%\" rowSpan=2 bgColor=#dae9fe><DIV class=contentdiv>");
		for (int j = 0; j < epbs.size(); j++) {
			ExamPaperBlock epbj = epbs.get(j);
			if(null!=epbj.getQuestions())
				writeEPQs(out, epbj.getQuestions(), epbj);
			else
				out.println("本大题不包含任何小题！");
		}
		out.print("<div style=\"text-align: center;margin-top:40px; \">"+
					"<input type=\"button\" value=\"上一题\" onclick=\"showQP();\">"+
					"<input type=\"button\" value=\"下一题\" onclick=\"showQN()\"> "+
				"</div></DIV></TD>");
		
	}
	private void writeEPQs(JspWriter out,List<Question> qs,ExamPaperBlock epb)	throws Exception {
		for (int j = 0; j < qs.size(); j++) {
			Question qj = qs.get(j);
			out.println("<div class='question' >");
			out.println("<b>第"+(j+1)+"题</b>("+epb.getEachscore()+"分)<p>" +  qj.getContent()+"</p> ");
			if(qj.getQtype()==7){//材料题
				List<Question> qjc =qj.getChilds();
				for (int i = 0; i < qjc.size(); i++) {
					out.println("<div class='question1'>");
					Question qjci = qjc.get(i);
					out.println("<b>第"+(i+1)+"题</b>("+(epb.getEachscore()*qjci.getScoreper()/100)+"分)<p>" +  qjci.getContent()+"</p> ");
					writeEPQsComm(out, qjci,epb.getId() );
					out.println("</div>");
				}
			}else{
				writeEPQsComm(out,qj ,epb.getId());
			}
			out.println("</div>");
		}
		
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
		out.println("<div class='answer'><b>答案</b>：");
		if(qj.getAnswers()[0].equals("yes"))
			out.println("正确");
		else
			out.println("不正确");
		out.println("</div>");
	}
	private void writeSelect(JspWriter out,Question qj ,int blockid ) throws Exception{
//		out	.println("<p>" +  qj.getContent()+"</p>");
		out.println("<p>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>"+(i+1)+":</b>"+qj.getOptions()[i]+"<br>");
		}
		out.print("</p>");
		
		out.println("<div class='answer'><b>答案</b>：");
		for (int i = 0; i < qj.getAnswers().length; i++) {
				out.println(""+(new Integer(qj.getAnswers()[i])+1)+"、");
		}
		out.println("</div>");
	}
	private void writeBlank(JspWriter out,Question qj ,int blockid ) throws Exception{
//		out.println("<p>" +  qj.getContent()+"</p> ");
		out.println( "<div class='answer'> <b>答案</b>：");
		
		for (int i = 0; i < qj.getAnswers().length; i++) {
				out.println(" <br><font class=\"xuxian\">空白答案"+( i +1)+":</font>&nbsp;"
						+qj.getAnswers()[i]);
		}
		out.println("</div>");
	}
	private void writeEssay(JspWriter out,Question qj ,int blockid ) throws Exception{
		out.println(" <div class='answer'><b>答案</b>：");
		for (int i = 0; i < qj.getAnswers().length; i++) {
			out.print("答案关键字"+(i+1)+":"+qj.getAnswers()[i]+"<br>");
		}
		out.println("</div>");
	}
}
