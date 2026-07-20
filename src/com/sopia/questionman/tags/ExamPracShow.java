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

public class ExamPracShow extends TagSupport {

	private static final long serialVersionUID = 6536487202613250886L;
	private static final Log logger = LogFactory.getLog(ExamPracShow.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ExamPaper ep = (ExamPaper) request.getAttribute("examPaper");
			List<ExamPaperBlock> epbs = ep.getEpBlocks();
			writeEPBs(out, epbs);
		} catch (Exception ex) {
			logger.error("练习查看显示错误");
		}
		return TagSupport.SKIP_BODY;
	}

	private void writeEPBs(JspWriter out, List<ExamPaperBlock> epbs)
			throws Exception {
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
			out.println("</div>");
			writeEPQs(out, epbj.getQuestions(), epbj);
			out.println("</div>");
		}

	}

	private void writeEPQs(JspWriter out, List<Question> qs, ExamPaperBlock epb)
			throws Exception {
		for (int j = 0; j < qs.size(); j++) {
			Question qj = qs.get(j);
			out.println("<div class='question'>");
			out.println("<b>第" + (qj.getSortid()) + "题</b>("
					+ epb.getEachscore() + "分)<p>" + qj.getContent_() + "</p> ");
			if (qj.getQtype() == 7) {// 材料题
				List<Question> qjc = qj.getChilds();
				for (int i = 0; i < qjc.size(); i++) {
					out.println("<div class='question1'>");
					Question qjci = qjc.get(i);
					out.println("<b>第" + (qjci.getSortid()) + "题</b>("
							+ (epb.getEachscore() * qjci.getScoreper() / 100)
							+ "分)<p>" + qjci.getContent_() + "</p> ");
					writeEPQsComm(out, qjci, epb.getId());
					out.println("</div>");
				}
			} else {
				writeEPQsComm(out, qj, epb.getId());
			}
			out.println("</div>");
		}

	}

	private void writeEPQsComm(JspWriter out, Question qj, int blockid)
			throws Exception {
		if (qj.getQtype() == 1) {
			writeYesOrNo(out, qj, blockid);
		}
		if (qj.getQtype() == 2 || qj.getQtype() == 3 || qj.getQtype() == 4) {
			writeSelect(out, qj, blockid);
		}
		if (qj.getQtype() == 5) {
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
		out.println("<div class='answer'><b>填写答案</b>：");
		if (null == qj.getStuAnswers() || "".equals(qj.getStuAnswers()[0])) {
			out.println("<input name=\"" + blockid + "_" + qj.getId() + "_"
					+ qj.getSortid() + "\" type=\"radio\" value=\"yes\" />正确");
			out.println("<input name=\"" + blockid + "_" + qj.getId() + "_"
					+ qj.getSortid() + "\" type=\"radio\" value=\"no\" />错误");
			out
					.println("<input name=\""
							+ blockid
							+ "_"
							+ qj.getId()
							+ "_"
							+ qj.getSortid()
							+ "\" type=\"radio\" checked='checked' style='display:none' value=\"\" /></div>");

			return;
		} else {
			if (qj.getStuAnswers()[0].equals("yes"))
				out
						.println("<input name=\""
								+ blockid
								+ "_"
								+ qj.getId()
								+ "_"
								+ qj.getSortid()
								+ "\"  checked='checked' type=\"radio\" value=\"yes\" />正确");
			else
				out.println("<input name=\"" + blockid + "_" + qj.getId() + "_"
						+ qj.getSortid()
						+ "\" type=\"radio\" value=\"yes\" />正确");

			if (qj.getStuAnswers()[0].equals("no"))
				out
						.println("<input name=\""
								+ blockid
								+ "_"
								+ qj.getId()
								+ "_"
								+ qj.getSortid()
								+ "\"  checked='checked' type=\"radio\" value=\"no\" />错误");
			else
				out.println("<input name=\"" + blockid + "_" + qj.getId() + "_"
						+ qj.getSortid()
						+ "\" type=\"radio\" value=\"no\" />错误");
			if (qj.getStuAnswers()[0].trim().equals(""))
				out
						.println("<input name=\""
								+ blockid
								+ "_"
								+ qj.getId()
								+ "_"
								+ qj.getSortid()
								+ "\" type=\"radio\" style='display:none' checked='checked' value=\"\" /></div>");
			else
				out
						.println("<input name=\""
								+ blockid
								+ "_"
								+ qj.getId()
								+ "_"
								+ qj.getSortid()
								+ "\" type=\"radio\" style='display:none' value=\"\" /></div>");
		}
	}

	private void writeSelect(JspWriter out, Question qj, int blockid)
			throws Exception {
		// out .println("<p>" + qj.getContent()+"</p>");
		out.println("<p>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
					+ qj.getOptions()[i] + "<br>");
		}
		out.print("</p>");

		out.println("<div  class='answer'><b>填写答案</b>：");
		for (int i = 0; i < qj.getOptions().length; i++) {
			if (qj.getQtype() == 2) {
				if (checkSAnswer(qj, i))
					out.println("<input name=\"" + blockid + "_" + qj.getId()
							+ "_" + qj.getSortid()
							+ "\" type=\"radio\" value=\"" + i
							+ "\"  checked='checked'/>" + ""
							+ ExamPaperUtil.getABC(i));
				else
					out.println("<input name=\"" + blockid + "_" + qj.getId()
							+ "_" + qj.getSortid()
							+ "\" type=\"radio\" value=\"" + i + "\" />" + ""
							+ ExamPaperUtil.getABC(i));
			} else {
				if (checkSAnswer(qj, i))
					out.println("<input name=\"" + blockid + "_" + qj.getId()
							+ "_" + qj.getSortid()
							+ "\" type=\"checkbox\" value=\"" + i
							+ "\" checked='checked'/>" + ""
							+ ExamPaperUtil.getABC(i));
				else
					out.println("<input name=\"" + blockid + "_" + qj.getId()
							+ "_" + qj.getSortid()
							+ "\" type=\"checkbox\" value=\"" + i + "\" />"
							+ "" + ExamPaperUtil.getABC(i));
			}
		}
		if (qj.getQtype() == 2) {
			if (null == qj.getStuAnswer() || null == qj.getStuAnswers()[0]
					|| "".equals(qj.getStuAnswers()[0].trim()))
				out
						.println("<input name=\""
								+ blockid
								+ "_"
								+ qj.getId()
								+ "_"
								+ qj.getSortid()
								+ "\" type=\"radio\" value=\"\" style='display:none;'  checked='checked'/>");
		} else
			out
					.println("<input name=\""
							+ blockid
							+ "_"
							+ qj.getId()
							+ "_"
							+ qj.getSortid()
							+ "\" type=\"checkbox\" value=\"\" style='display:none;'  checked='checked' />");

		out.println("</div>");
	}

	private boolean checkSAnswer(Question q, int i) {
		if (null != q.getStuAnswers())
			for (int j = 0; j < q.getStuAnswers().length; j++) {
				if (!q.getStuAnswers()[j].trim().equals(""))
					if (i == new Integer(q.getStuAnswers()[j]))
						return true;
			}
		return false;
	}

	private void writeBlank(JspWriter out, Question qj, int blockid)
			throws Exception {
		// out.println("<p>" + qj.getContent()+"</p> ");
		out.println("<div class='answer'> <b>填写答案</b>：");

		for (int i = 0; i < qj.getAnswers().length; i++) {
			if (null != qj.getStuAnswers() && null != qj.getStuAnswers()[i])
				out.println(" <br><font class=\"xuxian\">空白答案" + (i + 1)
						+ "</font>&nbsp;" + " <input name=\"" + blockid + "_"
						+ qj.getId() + "_" + qj.getSortid()
						+ "\" type=\"text\"  value=\"" + qj.getStuAnswers()[i]
						+ "\"/>  ");
			else
				out.println(" <br><font class=\"xuxian\">空白答案" + (i + 1)
						+ "</font>&nbsp;" + " <input name=\"" + blockid + "_"
						+ qj.getId() + "_" + qj.getSortid()
						+ "\" type=\"text\"  value=\"\"/>  ");

		}
		out.println("</div>");
	}

	private void writeEssay(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer'><b>填写答案</b>：<br/>");
		if (null != qj.getStuAnswers())
			out.println("<textarea cols=80 rows=18 name=\"" + blockid + "_"
					+ qj.getId() + "_" + qj.getSortid() + "\" >"
					+ qj.getStuAnswers()[0] + "</textarea></div>");
		else
			out.println("<textarea cols=80 rows=18 name=\"" + blockid + "_"
					+ qj.getId() + "_" + qj.getSortid()
					+ "\" ></textarea></div>");

	}

	private void writeDazi(JspWriter out, Question qj, int blockid)
			throws Exception {
		out
				.println("<div><a id='dazi_a" + "_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "' onclick=\"dazi"
						+ "_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ ".dazi_open();return false;\" href='#'>开始答题</a> &nbsp;&nbsp;&nbsp;"
						+ "<span id='dazi_djs_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "'>倒计时：</span> "
						+ "<input type=\"hidden\" id=\"dazi_answer1_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "\" size=\"4\" name=\""
						+ blockid
						+ "_"
						+ qj.getId()
						+ "_"
						+ qj.getSortid()
						+ "\" />"
						+ " "
						+ " "
						+ "<input type=\"hidden\" id=\"dazi_answer2_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "\" size=\"4\" name=\""
						+ blockid
						+ "_"
						+ qj.getId()
						+ "_"
						+ qj.getSortid()
						+ "\" />"
						+ " "
						+ " "
						+ "<input type=\"hidden\" id=\"dazi_answer3_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "\" size=\"4\" name=\""
						+ blockid
						+ "_"
						+ qj.getId()
						+ "_"
						+ qj.getSortid()
						+ "\" />"
						+ " </div>"
						+ " <div id='dazi_area_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "' style='display:none;' class='answer'><b>请在下面区域按范文打字</b>：");
		out.println("<div id='dazi_fanwen_" + blockid + "_" + qj.getId() + "'>"
				+ qj.getSubject() + "</div>");
		out.println("<textarea cols=90 rows=20 id=\"dazi_answer4_" + blockid
				+ "_" + qj.getId() + "\" name=\"" + blockid + "_" + qj.getId()
				+ "_" + qj.getSortid() + "\" ></textarea></div>");

		out.println("<script type=\"text/javascript\">" + "dazi" + "_"
				+ blockid + "_" + qj.getId() + "=new Dazi ('" + "_" + blockid
				+ "_" + qj.getId() + "',500);" + "</script>");

	}

	private void writeMail(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer'><b>填写答案</b>：<br/>");
		out.println("发 给：" + "<input type=\"text\" name=\"" + blockid + "_"
				+ qj.getId() + "_" + qj.getSortid() + "\" size=\"65\" />"
				+ "<br />" + "抄 送：" + "<input type=\"text\" name=\"" + blockid
				+ "_" + qj.getId() + "_" + qj.getSortid() + "\" size=\"65\" />"
				+ "<br />" + "密 送：" + "<input type=\"text\" name=\"" + blockid
				+ "_" + qj.getId() + "_" + qj.getSortid() + "\" size=\"65\" />"
				+ "<br />" + "主 题：" + "<input type=\"text\" name=\"" + blockid
				+ "_" + qj.getId() + "_" + qj.getSortid() + "\" size=\"65\" />"
				+ "<br />" + "附 件：" + "<input type=\"text\" name=\"" + blockid
				+ "_" + qj.getId() + "_" + qj.getSortid() + "\" size=\"65\" />"
				+ "<br />" + "正 文：" + "<textarea rows=\"4\" name=\"" + blockid
				+ "_" + qj.getId() + "_" + qj.getSortid()
				+ "\" cols=\"50\" ></textarea></div>");

	}

	private void writeSearch(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer'><b>填写答案</b>：<br/>");
		out
				.println("关键字：" + "<input type=\"text\" name=\""
						+ blockid
						+ "_"
						+ qj.getId()
						+ "_"
						+ qj.getSortid()
						+ "\" size=\"35\" name=\"question.answers\"  id=\"qanswer_title_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "\"/>&nbsp;&nbsp;&nbsp;<input type=\"button\" onclick=\"qsearch_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ ".qanswer_search();\" value=\"搜索\" />"
						+ "<br />"
						+ "答&nbsp;&nbsp;&nbsp;案："
						+ "<input type=\"text\" id=\"qanswer_ans_" + blockid + "_"
				+ qj.getId() 
				+ "\" name=\""
						+ blockid
						+ "_"
						+ qj.getId()
						+ "_"
						+ qj.getSortid()
						+ "\" size=\"45\" name=\"question.answers\" />	"
						+ "<div id=\"answer_list_" + blockid + "_"
				+ qj.getId() 
				+ "\" style=\"padding: 10px;background:#ffffff\"></div></div>");
		out.println("<script type=\"text/javascript\">" + "qsearch_" + blockid + "_"
				+ qj.getId() 
				+ "=new Qsearch ('" + "_" + blockid + "_"
				+ qj.getId() 
				+ "');" + "</script>");

	}

	private void writeOffice(JspWriter out, Question qj, int blockid)
			throws Exception {
		out
				.println("<div>下载： <a target='_blank' href='"+SystemConfOp.getStuffUrl()+"download.jsp?filename="
						+ qj.getSubject()
						+ "'>模板文档</a><br/>"
						+ "上传： 我的答案<input type='file' id='office_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "' size='30' name='st' ><input type='button' value='上傳' onclick=\"upload_offices( "
						+ blockid + "," + qj.getId() + " )\"/></div>");

	}
}
