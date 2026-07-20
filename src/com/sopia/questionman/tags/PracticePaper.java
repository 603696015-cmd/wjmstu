package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;
import com.sopia.common.ExamPaperUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;
import com.sopia.studyman.entities.MyExamPaper;

public class PracticePaper extends TagSupport {

	private static final long serialVersionUID = 6536487202613250886L;
	private int userid = 0;
	private static final Log logger = LogFactory.getLog(QPaper1b1.class);

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ExamPaper ep = (ExamPaper) request.getAttribute("examPaper");
			userid = (Integer) ((HttpServletRequest) request).getSession()
					.getAttribute(ElConstants.SESSION_USERID);
			// epid = ep.getId();
			List<ExamPaperBlock> epbs = ep.getEpBlocks();
			out
					.print("<script type=\"text/javascript\">var ep = new EXAMPAPER("
							+ ep.getId() + ");</script>\n");
			writeEPBs(out, epbs);
			out
					.print("<script type=\"text/javascript\"> ep.showCa()</script>\n");
		} catch (Exception ex) {
			logger.error("试卷显示出错", ex);
		}
		return TagSupport.SKIP_BODY;
	}

	private void writeEPBs(JspWriter out, List<ExamPaperBlock> epbs)
			throws Exception {
		int _qsort = 0;
		for (int j = 0; j < epbs.size(); j++) {
			ExamPaperBlock epbj = epbs.get(j);
			out.print("<script type=\"text/javascript\">var block"
					+ epbj.getId() + "= new BLOCK(" + epbj.getId() + ","
					+ epbj.getSortid() + ",'" + epbj.getTitle()
					+ "');</script>\n");
			out.println("<div id='block_" + epbj.getId() + "' class='block'>");
			out.println("<div class='block_name'>");
			out.println(epbj.getSortid() + ":" + epbj.getTitle());
			out.println("</div>");
			out.println("<div class='block_desc'>大题说明：");
			//out.println(epbj.getDescription());
			if(epbj.getDescription()!=null){
				out.println(epbj.getDescription());
			}
			out.println("</div>");
			int _qsort1 = writeEPQs(out, epbj.getQuestions(), epbj, _qsort);
			_qsort = _qsort + _qsort1;
			out.println("</div>");
			out.print("<script type=\"text/javascript\"> ep.addBlock(block"
					+ epbj.getId() + "); </script>\n");
		}
	}

	private int writeEPQs(JspWriter out, List<Question> qs, ExamPaperBlock epb,
			int _qsort) throws Exception {
		int _qsort1 = 0;
		for (int j = 0; j < qs.size(); j++) {
			Question qj = qs.get(j);
			out.print("<script type=\"text/javascript\">var question"
					+ qj.getId() + "= new QUESTION(" + qj.getId() + ","
					+ qj.getSortid() + ",block" + epb.getId() + "," + _qsort
					+ ");</script>\n");
			// 请对照范文进行打字，打错的字将被标为红色。打字题提交后将不能修改，也不能再次作答。点击下方“开始答题”进入打字页面。

			out.println("<div class='question' id='question_" + epb.getId()
					+ "_" + qj.getId() + "'>");
			out.println("<b>第" + (qj.getSortid()) + "题</b>("
					+ epb.getEachscore() + "分)<p>" + SystemConfOp.toStuffUrl(qj.getContent()) + "</p> ");
			out.println("<input name=\"questions[" + _qsort
					+ "].epblock.id\" type=\"hidden\" value=\"" + epb.getId()
					+ "\" />");
			out.println("<input name=\"questions[" + _qsort
					+ "].epblock.sortid\" type=\"hidden\" value=\""
					+ epb.getSortid() + "\" />");
			out.println("<input name=\"questions[" + _qsort
					+ "].id\" type=\"hidden\" value=\"" + qj.getId() + "\" />");
			out.println("<input name=\"questions[" + _qsort
					+ "].sortid\" type=\"hidden\" value=\"" + qj.getSortid()
					+ "\" />");
			if (qj.getQtype() == 7) {// 材料题
				List<Question> qjc = qj.getChilds();
				if(qjc!=null)
				for (int i = 0; i < qjc.size(); i++) {
					_qsort++;
					_qsort1++;
					Question qjci = qjc.get(i);
					out.println("<div class='question1'>");
					out.println("<input name=\"questions[" + _qsort
							+ "].epblock.id\" type=\"hidden\" value=\""
							+ epb.getId() + "\" />");
					out.println("<input name=\"questions[" + _qsort
							+ "].epblock.sortid\" type=\"hidden\" value=\""
							+ epb.getSortid() + "\" />");
					out.println("<input name=\"questions[" + _qsort
							+ "].id\" type=\"hidden\" value=\"" + qjci.getId()
							+ "\" />");
					out.println("<input name=\"questions[" + _qsort
							+ "].sortid\" type=\"hidden\" value=\""
							+ qjci.getSortid() + "\" />");
					out.println("<b>第" + (qjci.getSortid()) + "题</b>("
							+ (epb.getEachscore() * qjci.getScoreper() / 100)
							+ "分)<p>" +  SystemConfOp.toStuffUrl(qjci.getContent()) + "</p> ");
					writeEPQsComm(out, qjci, epb.getId(), _qsort);
					out.println("</div>");

				}
			} else {
				writeEPQsComm(out, qj, epb.getId(), _qsort);
			}
			_qsort++;
			_qsort1++;
			out.print("<script type=\"text/javascript\"> block" + epb.getId()
					+ ".addQuestion(question" + qj.getId() + "); </script>\n");
			out.println("</div>");
		}
		return _qsort1;
	}

	private void writeEPQsComm(JspWriter out, Question qj, int blockid,
			int _qsort) throws Exception {
		if (qj.getQtype() == 1) {
			writeYesOrNo(out, qj, blockid, _qsort);
		}
		if (qj.getQtype() == 2 || qj.getQtype() == 3 || qj.getQtype() == 4) {
			writeSelect(out, qj, blockid, _qsort);
		}
		if (qj.getQtype() == 5) {
			writeBlank(out, qj, blockid, _qsort);
		}
		if (qj.getQtype() == 6) {
			writeEssay(out, qj, blockid, _qsort);
		}
		if (qj.getQtype() == 8) {
			writeDazi(out, qj, blockid, _qsort);
		}
		if (qj.getQtype() == 9) {
			writeMail(out, qj, blockid, _qsort);
		}
		if (qj.getQtype() == 10) {
			writeSearch(out, qj, blockid, _qsort);
		}
		if (qj.getQtype() == 11) {
			writeOffice(out, qj, blockid, _qsort);
		}
	}

	private void writeYesOrNo(JspWriter out, Question qj, int blockid,
			int _qsort) throws Exception {
		out.println("<div class='answer'><b>填写答案</b>：");
		if (null == qj.getStuAnswers() || "".equals(qj.getStuAnswers()[0])) {
			out.println("<input onclick=\"q_yd(" + blockid + "," + qj.getId()
					+ ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"radio\" value=\"yes\" />正确");
			out.println("<input onclick=\"q_yd(" + blockid + "," + qj.getId()
					+ ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"radio\" value=\"no\" />错误");
			out.println("</div>");
			return;
		} else {
			if (qj.getStuAnswers()[0].equals("yes"))
				out
						.println("<input onclick=\"q_yd("
								+ blockid
								+ ","
								+ qj.getId()
								+ ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\"  checked='checked' type=\"radio\" value=\"yes\" />正确");
			else
				out.println("<input onclick=\"q_yd(" + blockid + ","
						+ qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"radio\" value=\"yes\" />正确");

			if (qj.getStuAnswers()[0].equals("no"))
				out
						.println("<input onclick=\"q_yd("
								+ blockid
								+ ","
								+ qj.getId()
								+ ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\"  checked='checked' type=\"radio\" value=\"no\" />错误");
			else
				out.println("<input onclick=\"q_yd(" + blockid + ","
						+ qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"radio\" value=\"no\" />错误");
		}
		out.println("</div>");
	}

	private void writeSelect(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<p>");
		if (null != qj.getOptions())
			for (int i = 0; i < qj.getOptions().length; i++) {
				out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
						+ SystemConfOp.toStuffUrl(qj.getOptions()[i]) + "<br/>");
			}
		out.print("</p>");

		out.println("<div  class='answer'><b>填写答案</b>：");
		if (null != qj.getOptions())
			for (int i = 0; i < qj.getOptions().length; i++) {
				if (qj.getQtype() == 2) {
					if (checkSAnswer(qj, i))
						out.println("<input onclick=\"q_yd(" + blockid + ","
								+ qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"radio\" value=\"" + i
								+ "\"  checked='checked'/>" + ""
								+ ExamPaperUtil.getABC(i));
					else
						out.println("<input onclick=\"q_yd(" + blockid + ","
								+ qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"radio\" value=\"" + i
								+ "\" />" + "" + ExamPaperUtil.getABC(i));
				} else {
					if (checkSAnswer(qj, i))
						out.println("<input onclick=\"q_yd(" + blockid + ","
								+ qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"checkbox\" value=\""
								+ i + "\" checked='checked'/>" + ""
								+ ExamPaperUtil.getABC(i));
					else
						out.println("<input onclick=\"q_yd(" + blockid + ","
								+ qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"checkbox\" value=\""
								+ i + "\" />" + "" + ExamPaperUtil.getABC(i));
				}
			}
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

	private void writeBlank(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='answer'> <b>填写答案</b>：");

		for (int i = 0; i < qj.getAnswers().length; i++) {
			// if (null != qj.getStuAnswers() && null != qj.getStuAnswers()[i])
			// out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
			// + "</font>&nbsp;" + " <input name=\"questions["
			// + _qsort + "].stuAnswers\" type=\"text\" value=\""
			// + qj.getStuAnswers()[i] + "\"/> ");
			// else
			out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
					+ "</font>&nbsp;" + " <input onclick=\"q_yd(" + blockid
					+ "," + qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"text\"  value=\"\"/>  ");

		}
		out.println("</div>");
	}

	private void writeEssay(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println(" <div class='answer'><b>填写答案</b>：<br/>");
		if (null != qj.getStuAnswers())
			out.println("<textarea cols=80 rows=18 onkeyup=\"q_yd(" + blockid
					+ "," + qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" >" + qj.getStuAnswers()[0]
					+ "</textarea></div>");
		else
			out.println("<textarea cols=80 rows=18 onkeyup=\"q_yd(" + blockid
					+ "," + qj.getId() + ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" ></textarea></div>");

	}

	private void writeDazi(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out
				.println("<div class='startNewWindow'> <a style='color:blue;cursor:pointer' onclick=\"openNewWindowQ(this, "
						+ blockid + "," + qj.getId() + ")\">开始答题" + "</a>");
		out.println("<input onclick=\"q_yd(" + blockid + "," + qj.getId()
				+ ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"hidden\" value=\"\"/></div>");
	}

	private void writeMail(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out
				.println("<div class='startNewWindow'> <a style='color:blue;cursor:pointer' onclick=\"openNewWindowQ(this, "
						+ blockid + "," + qj.getId() + ")\">开始答题" + "</a>");
		out.println("<input onclick=\"q_yd(" + blockid + "," + qj.getId()
				+ ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"hidden\" value=\"\" />");
		out.println(" </div>");
	}

	private void writeSearch(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out
				.println("<div class='startNewWindow'> <a style='color:blue;cursor:pointer' onclick=\"openNewWindowQ(this, "
						+ blockid + "," + qj.getId() + ")\">开始答题" + "</a>");
		out.println("<input onclick=\"q_yd(" + blockid + "," + qj.getId()
				+ ")\" name=\""+blockid+"_"+qj.getId()+"_"+qj.getSortid()+"\" type=\"hidden\" value=\"\" /></div>");

	}

	private void writeOffice(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		// out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\"
		// name=\"questions[" + _qsort
		// + "].stuAnswers\" type=\"hidden\" value=\"\" />");
		out.println("<div>下载： <a target='_blank' href='"+SystemConfOp.getStuffUrl()+"download.jsp?filename="
				+ qj.getSubject()
				+ "'>模板文档</a><br/>"
				+ "上传： 我的答案<input onclick=\"q_yd("
				+ blockid
				+ ","
				+ qj.getId()
				+ ")\" type='file' id='office_"
				+ blockid
				+ "_"
				+ qj.getId()
				+ "' size='30' name='st' ><input type=\"hidden\" id='a_office_"
				+ blockid
				+ "_"
				+ qj.getId()
				+ "' value=\""
				+ userid
				+ "/"
				// + sqid
				// + "_"
				// + blockid
				// + "_"
				// + qj.getId()
				// + "\" name=\"questions[" + _qsort
				// + "].stuAnswers\" /> " +
				+ "<input onclick=\"q_yd(" + blockid + "," + qj.getId()
				+ ");upload_offices( " + blockid + "," + qj.getId()
				+ " )\" type='button' value='上传' />" + "" + "</div>");

	}
}
