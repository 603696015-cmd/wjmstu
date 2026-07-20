package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ExamPaperUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;

public class ExamPaperRead_view extends TagSupport {

	private static final long serialVersionUID = 6536487202613250886L;
	private static final Log logger = LogFactory.getLog(ExamPaperRead_view.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ExamPaper ep = (ExamPaper) request.getAttribute("examPaper");
			if (ep == null || ep.getId() == 0) {
				out.print("<h3>试卷不存在了!!!</h3>");
				return TagSupport.SKIP_BODY;
			}
			List<ExamPaperBlock> epbs = ep.getEpBlocks();
			writeEPBs(out, epbs);
		} catch (Exception ex) {
			logger.error("试卷批阅内容显示错误！",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	private void writeEPBs(JspWriter out, List<ExamPaperBlock> epbs)
			throws Exception {
		if(null!=epbs)
		for (int j = 0; j < epbs.size(); j++) {
			ExamPaperBlock epbj = epbs.get(j);
			out.println("<div id='block_" + j + "' class='block'>");
			out.println("<div class='block_name'>");
			out.println(epbj.getSortid() + ":" + epbj.getTitle());
			out.println("</div>");
			out.println("<div class='block_desc'>大题说明：");
			if(epbj.getDescription()!=null){
				out.println(epbj.getDescription());
			}
			out.println("</div><br>");
			writeEPQs(out, epbj.getQuestions(), epbj);
			out.println("</div><br>");
		}

	}

	private void writeEPQs(JspWriter out, List<Question> qs, ExamPaperBlock epb)
			throws Exception {
		for (int j = 0; j < qs.size(); j++) {
			Question qj = qs.get(j);
			out.println("<div class='question'>");
			out.println("<b>第" + qj.getSortid() + "题</b>(" + epb.getEachscore()
					+ "分)<p>" + qj.getContent_() + "</p> ");
			if (qj.getQtype() == 7) {// 材料题
				List<Question> qjc = qj.getChilds();
				for (int i = 0; i < qjc.size(); i++) {
					out.println("<div class='question1'>");
					Question qjci = qjc.get(i);
					out.println("<b>第" + qjci.getSortid() + "题</b>("
							+ (epb.getEachscore() * qjci.getScoreper() / 100)
							+ "分)<p>" + qjci.getContent_() + "</p> ");
					writeEPQsComm(out, qjci, epb.getId());
					out.println("</div><br>");
				}
			} else {
				writeEPQsComm(out, qj, epb.getId());
			}
			out.println("</div><br>");
		}

	}

	private void writeEPQsComm(JspWriter out, Question qj, int blockid)
			throws Exception {
		if (qj.getQtype() == 1) {
			writeYesOrNo(out, qj, blockid);
		}
		if (qj.getQtype() == 2 || qj.getQtype() == 3 || qj.getQtype() == 4|| qj.getQtype() == 15|| qj.getQtype() == 16) {
			writeSelect(out, qj, blockid);
		}
		if (qj.getQtype() == 5|| qj.getQtype() == 17) {
			writeBlank(out, qj, blockid);
		}
		if (qj.getQtype() == 6) {
			writeEssay(out, qj, blockid);
		}
		if (qj.getQtype() == 8) {
			writeDazi(out, qj, blockid);
		}
		if (qj.getQtype() == 9) {
			writeMail(out, qj, blockid);
		}
		if (qj.getQtype() == 10) {
			writeSearch(out, qj, blockid);
		}
		if (qj.getQtype() == 11) {
			writeOffice(out, qj, blockid);
		}
	}

	private void writeYesOrNo(JspWriter out, Question qj, int blockid)
			throws Exception {
		// out.println("<p>" + qj.getContent()+ "</p>");
		String myStuAnswer = "";
		if (qj.getStuAnswers() != null) {
			myStuAnswer =qj.getStuAnswers()[0];
		}
		out.println("<div class='answer'><b>回答</b>：" + myStuAnswer
				+ "&nbsp;&nbsp;&nbsp;【系统打分:" + qj.getMyScore() + "】");
		if (qj.getStuAnswers() != null) {
			if ("".equals(qj.getStuAnswers()[0].trim()))
				out.println("该题未回答");
			else {
				if (qj.getStuAnswers()[0].equals(qj.getAnswers()[0]))
					out.println("回答正确");
				else if (!qj.getStuAnswers()[0].equals(qj.getAnswers()[0]))
					out.println("回答不正确");
			}
		}

		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (qj.getAnswers()[0].equals("yes"))
			out.println("正确");
		else
			out.println("不正确");
		out.println("</div>");
		out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
				+ "</div>");
		out.println("</div>");

	}

	private void writeSelect(JspWriter out, Question qj, int blockid)
			throws Exception {
		// out .println("<p>" + qj.getContent()+"</p>");
		String myStuAnswer = "";
		if (qj.getStuAnswers() != null) {
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				if (null != qj.getStuAnswers()[i]
						&& !qj.getStuAnswers()[i].trim().equals(""))
					myStuAnswer += ""
							+ ExamPaperUtil.getABC(qj.getStuAnswers()[i]) + "、";
			}
		}
		out.println("<p>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
					+ qj.getOptions()[i] + "<br>");
		}
		out.print("</p>");

		out.println("<div class='answer'><b>回答</b>：" + myStuAnswer
				+ "&nbsp;&nbsp;&nbsp;【系统打分:" + qj.getMyScore() + "】");
		// if (null != qj.getStuAnswers())
		// for (int i = 0; i < qj.getStuAnswers().length; i++) {
		// if (!"".equals(qj.getStuAnswers()[i].trim()))
		// out.println("" + ExamPaperUtil.getABC(qj.getStuAnswers()[i]) + "、");
		// }
		out.println(" <div style='color:green'><b>正确答案</b>：");
		for (int i = 0; i < qj.getAnswers().length; i++) {
			out.println("" + ExamPaperUtil.getABC(qj.getAnswers()[i]) + "、");
		}
		out.println("</div>");
		out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
				+ "</div>");
		out.println("</div>");
	}

	/*
	 * private boolean checkSAnswer(Question q,int i){
	 * if(null!=q.getStuAnswers()) for (int j = 0; j < q.getStuAnswers().length;
	 * j++) { if(!q.getStuAnswers()[j].trim().equals("")) if(i==new
	 * Integer(q.getStuAnswers()[j])) return true; } return false; }
	 */
	private void writeBlank(JspWriter out, Question qj, int blockid)
			throws Exception {
		// out.println("<p>" + qj.getContent()+"</p> ");
		out.println("<div class='answer'> <b>回答</b>：" + "【系统打分:"
				+ qj.getMyScore() + "】");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				out.println(" <br><font class=\"xuxian\">空白答案" + (i + 1)
						+ ":</font>&nbsp;" + qj.getStuAnswers()[i]);
			}
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (null != qj.getAnswers())
			for (int i = 0; i < qj.getAnswers().length; i++) {
				out.println(" <br><font class=\"xuxian\">空白答案" + (i + 1)
						+ ":</font>&nbsp;" + qj.getAnswers()[i]);
			}
		out.println("</div>");
		out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
				+ "</div>");
		out.println("</div>");
	}

	private void writeEssay(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer'>" + "【系统打分:" + qj.getMyScore()
				+ "】<br><b>我的回答</b>");
		if (null != qj.getStuAnswers())
			out.println(qj.getStuAnswers()[0].trim() + "</div>");
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (null != qj.getAnswers())
			for (int i = 0; i < qj.getAnswers().length; i++) {
				out
						.print("答案关键字" + (i + 1) + ":" + qj.getAnswers()[i]
								+ "<br>");
			}
		out.println("</div>");
		out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
				+ "</div>");
	}

	private void writeDazi(JspWriter out, Question qj, int blockid)
			throws Exception {
		
		out.println("<div> 范文： " + qj.getSubject()
				+ "<br/> <div class='answer'><b>回答</b>：【系统打分:"
				+ qj.getMyScore() + "】速 度：" );
		if("-=SpEl=-".equals(qj.getStuAnswer()))
			out.print("未作答");
		else
			out.println(qj.getStuAnswers()[0]
				+ "个字/秒，打对的字数：" + qj.getStuAnswers()[1] + "个， " + "打字时间："
				+ qj.getStuAnswers()[2] + "秒。<br/><b>我打的字数：</b>"
				+ qj.getStuAnswers()[3]);
						out.println(" </div> </div>");
	}

	private void writeMail(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer'><b>回答</b>：【系统打分:" + qj.getMyScore()
				+ "】<br/>");
		out.println("发 给：" + qj.getStuAnswers()[0] + "<br />" + "抄 送："
				+ qj.getStuAnswers()[1] + "<br />" + "密 送："
				+ qj.getStuAnswers()[2] + "<br />" + "主 题："
				+ qj.getStuAnswers()[3] + "<br />" + "附 件："
				+ qj.getStuAnswers()[4] + "<br />" + "正 文："
				+ qj.getStuAnswers()[5] + "  <br>");
		out.println(" <div style='color:green'><b>正确答案</b> 发 给："
				+ qj.getAnswers()[0] + "<br />" + "抄 送：" + qj.getAnswers()[1]
				+ "<br />" + "密 送：" + qj.getAnswers()[2] + "<br />" + "主 题："
				+ qj.getAnswers()[3] + "<br />" + "附 件：" + qj.getAnswers()[4]
				+ "<br />" + "正 文：" + qj.getAnswers()[5] + "</div> </div>");

	}

	private void writeSearch(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer'><b>回答</b>：【系统打分:" + qj.getMyScore()
				+ "】");
		out.println("关键字：" + qj.getStuAnswers()[0] + "<br />"
				+ "答&nbsp;&nbsp;&nbsp;案：" + qj.getStuAnswers()[1]);
		out.println(" <div style='color:green'><b>正确答案</b> 关键字："
				+ qj.getAnswers()[0] + "<br />" + "答&nbsp;&nbsp;&nbsp;案："
				+ qj.getAnswers()[1] + "<br />" + "</div> </div>");

	}

	private void writeOffice(JspWriter out, Question qj, int blockid)
			throws Exception {
		out
				.println("<div>要求文档： <a target='_blank' href='"+SystemConfOp.getStuffUrl()+"download.jsp?filename="
						+ qj.getSubject()
						+ "'>模板文档</a><br/>"
						+ " 我的答案<a href='download.jsp?filename=elstuffs/quizanswer/"+qj.getStuAnswers()[0]+"'>下载我的答案</a></div>");

	}
}
