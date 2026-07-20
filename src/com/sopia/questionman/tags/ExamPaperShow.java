package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ExamPaperUtil;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;

public class ExamPaperShow extends TagSupport {

	private static final long serialVersionUID = 6536487202613250886L;
	private static final Log logger = LogFactory.getLog(ExamPaperShow.class);
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
			logger.error("试卷预览显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	private void writeEPBs(JspWriter out, List<ExamPaperBlock> epbs)
			throws Exception {
		for (int j = 0; j < epbs.size(); j++) {
			ExamPaperBlock epbj = epbs.get(j);
			out.println("<div id='block_"+j+"' class='block'>");
			out.println("<div class='block_name'>");
			out.println(epbj.getTitle());
			out.println("</div>");
			out.println("<div class='block_desc'>大题说明：");
			if(epbj.getDescription()!=null){
				out.println(epbj.getDescription());
			}
			out.println("</div><br>");
			if(null!=epbj.getQuestions())
			writeEPQs(out, epbj.getQuestions(), epbj);
			else
				out.println("本大题不包含任何小题！");
			out.println("</div><br>");
		}
		
	}
	private void writeEPQs(JspWriter out,List<Question> qs,ExamPaperBlock epb)	throws Exception {
		for (int j = 0; j < qs.size(); j++) {
			Question qj = qs.get(j);
			out.println("<div class='question'>");
			out.println("<b>第"+qj.getSortid()+"题</b>("+epb.getEachscore()+"分)<p>" +  qj.getContent()+"</p> ");
			if(qj.getQtype()==7){//材料题
				List<Question> qjc =qj.getChilds();
				for (int i = 0; i < qjc.size(); i++) {
					out.println("<div class='question1'>");
					Question qjci = qjc.get(i);
					out.println("<b>第"+(i+1)+"题</b>("+(epb.getEachscore()*qjci.getScoreper()/100)+"分)<p>" +  qjci.getContent()+"</p> ");
					writeEPQsComm(out, qjci,epb.getId() );
					out.println("</div><br>");
				}
			}else{
				writeEPQsComm(out,qj ,epb.getId());
			}
			out.println("</div><br>");
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
			out.println("<b>"+ExamPaperUtil.getABC(i )+":</b>"+qj.getOptions()[i]+"<br>");
		}
		out.print("</p>");
		
		out.println("<div class='answer'><b>答案</b>：");
		for (int i = 0; i < qj.getAnswers().length; i++) {
				out.println(""+ExamPaperUtil.getABC(qj.getAnswers()[i]) +"、");
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
