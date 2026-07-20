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

public class PracPaper1b1View extends TagSupport {

	private static final long serialVersionUID = 6536487202613250886L;
	private int userid = 0;
	private static final Log logger = LogFactory.getLog(PracPaper1b1View.class);

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
							+ ep.getId()
							+ ","
							+ ep.getEp_tscore()
							+ ","
							+ ep.getMep_tscore() + ");</script>\n");
			writeEPBs(out, epbs);
			out
					.print("<script type=\"text/javascript\"> ep.showCa()</script>\n");
		} catch (Exception ex) {
			logger.error("试卷显示出错", ex);
		}
		return TagSupport.SKIP_BODY;
	}

	/**输出大题列表
	 * @param out
	 * @param epbs
	 * @throws Exception
	 */
	private void writeEPBs(JspWriter out, List<ExamPaperBlock> epbs)
			throws Exception {
		int _qsort = 0;
		for (int j = 0; j < epbs.size(); j++) {
			ExamPaperBlock epbj = epbs.get(j);
			out.print("<script type=\"text/javascript\">var block"
					+ epbj.getId() + "= new BLOCK(" + epbj.getId() + ","
					+ epbj.getSortid() + ",'" + epbj.getTitle() + "',"
					+ epbj.getEachscore() * epbj.getQuestions().size() + ","
					+ epbj.getMyscore() + ");</script>\n");
			out.println("<div id='block_" + epbj.getId() + "' class='block'>");
			out.println("<div class='block_name'>");
			out.println(epbj.getSortid() + ":" + epbj.getTitle());
			out.println("</div>");
			out.println("<div class='block_desc'>大题说明：");
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

	/**输出小题信息
	 * @param out
	 * @param qs
	 * @param epb
	 * @param _qsort
	 * @return  返回结束序号
	 * @throws Exception
	 */
	private int writeEPQs(JspWriter out, List<Question> qs, ExamPaperBlock epb,
			int _qsort) throws Exception {
		int _qsort1 = 0;
		for (int j = 0; j < qs.size(); j++) {
			Question qj = qs.get(j);
			out.print("<script type=\"text/javascript\">var question"
					+ qj.getId() + "= new QUESTION(" + qj.getId() + ","
					+ qj.getSortid() + ",block" + epb.getId() + ",'"
					+ qj.getMystaclass() + "');</script>\n");
			out.println("<div class='question' id='question_" + epb.getId()
					+ "_" + qj.getId() + "'>");
			out.println("<b>第" + (qj.getSortid()) + "题</b>("
					+ epb.getEachscore() + "分)<p>" + qj.getContent_() + "</p> ");
			// out.println("<input name=\"questions[" + _qsort
			// + "].epblock.id\" type=\"hidden\" value=\"" + epb.getId()
			// + "\" />");
			// out.println("<input name=\"questions[" + _qsort
			// + "].epblock.sortid\" type=\"hidden\" value=\""
			// + epb.getSortid() + "\" />");
			// out.println("<input name=\"questions[" + _qsort
			// + "].id\" type=\"hidden\" value=\"" + qj.getId() + "\" />");
			// out.println("<input name=\"questions[" + _qsort
			// + "].sortid\" type=\"hidden\" value=\"" + qj.getSortid()
			// + "\" />");
			if (qj.getQtype() == 7) {// 材料题
				List<Question> qjc = qj.getChilds();
				for (int i = 0; i < qjc.size(); i++) {
					_qsort++;
					_qsort1++;
					Question qjci = qjc.get(i);
					out.println("<div class='question1'>");
					// out.println("<input name=\"questions[" + _qsort
					// + "].epblock.id\" type=\"hidden\" value=\""
					// + epb.getId() + "\" />");
					// out.println("<input name=\"questions[" + _qsort
					// + "].epblock.sortid\" type=\"hidden\" value=\""
					// + epb.getSortid() + "\" />");
					// out.println("<input name=\"questions[" + _qsort
					// + "].id\" type=\"hidden\" value=\"" + qjci.getId()
					// + "\" />");
					// out.println("<input name=\"questions[" + _qsort
					// + "].sortid\" type=\"hidden\" value=\""
					// + qjci.getSortid() + "\" />");
					out.println("<b>第" + (qjci.getSortid()) + "题</b>("
							+ (epb.getEachscore() * qjci.getScoreper() / 100)
							+ "分)<p>" + qjci.getContent_() + "</p> ");
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

	/**输出普通小题（按各种题型输出）
	 * @param out
	 * @param qj
	 * @param blockid
	 * @param _qsort
	 * @throws Exception
	 */
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
		out.println("<div class='answer'><b>我的作答</b>：");
		if (null != qj.getStuAnswers()) {
			if (qj.getStuAnswers()[0].equals("yes"))
				out.println(" 正确");
			else if (qj.getStuAnswers()[0].equals("no"))
				out.println(" 错误");
			else
				out.println("该题未作答");
		} else {
			out.println("该题未作答");
		}

		out.println("</div>");
		out.println("<div  class='answer'><b>标准答案</b>：");
		if (null != qj.getAnswers()) {
			if (qj.getAnswers()[0].equals("yes"))
				out.println(" 正确");
			else if (qj.getAnswers()[0].equals("no"))
				out.println(" 错误");
			else
				out.println("该题设置答案");
		} else {
			out.println("该题设置答案");
		}
		out.println("</div>");
	/*	out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");
	}

	private void writeSelect(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<p>");
		if (null != qj.getOptions())
			for (int i = 0; i < qj.getOptions().length; i++) {
				out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
						+ qj.getOptions()[i] + "<br/>");
			}
		out.print("</p>");

		out.println("<div  class='answer'><b>我的作答</b>：");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				out.println(ExamPaperUtil.getABC(ExamPaperUtil.getInt(qj
						.getStuAnswers()[i]))
						+ "、");
			}
		else
			out.println("该题未做答");
		out.println("</div>");
		out.println("<div  class='answer'><b>标准答案</b>：");
		if (null != qj.getAnswers())
			for (int i = 0; i < qj.getAnswers().length; i++) {
				out.println(ExamPaperUtil.getABC(ExamPaperUtil.getInt(qj
						.getAnswers()[i]))
						+ "、");
			}
		else
			out.println("该题设置答案");
		out.println("</div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
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
//		out.println("<div class='answer'> <b>我的作答</b>：");
//		for (int i = 0; i < qj.getAnswers().length; i++) {
//			// if (null != qj.getStuAnswers() && null != qj.getStuAnswers()[i])
//			// out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
//			// + "</font>&nbsp;" + " <input name=\"questions["
//			// + _qsort + "].stuAnswers\" type=\"text\" value=\""
//			// + qj.getStuAnswers()[i] + "\"/> ");
//			// else
//			out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
//					+ "</font>&nbsp;" +
//					// " <input onclick=\"q_yd(" + blockid
//					// + "," + qj.getId() + ")\" name=\"questions[" + _qsort
//					// + "].stuAnswers\" type=\"text\" value=\"\"/>
//					"");
//
//		}
//		out.println("</div>");
		out.println("<div class='answer'> <b>我的作答</b>：");
		if(qj.getStuAnswer()!=null&&qj.getStuAnswers()!=null){
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				out.println(" <font class=\"xuxian\">空白作答" + (i + 1)
						+ "</font>&nbsp;" + " <input onclick=\"q_yd(" + blockid
						+ "," + qj.getId() + ")\" name=\"questions[" + _qsort
						+ "].stuAnswers\" type=\"text\" readonly=\"readonly\" value=\""+qj.getStuAnswers()[i]+"\"/>  ");
			}
			out.println("</div>");
		}else{
			out.println("</div>");
		}
		//标准答案
		out.println("<div class='answer'><b>标准答案</b>：");
		if (null != qj.getAnswers()){
			for (int i = 0; i < qj.getAnswers().length; i++) {
				if(qj.getAnswers().length>1){
					out.println(qj.getAnswers()[i]+"、");
				}else{
					out.println(qj.getAnswers()[i]);
				}
			}
		}
		out.println(" </div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");
	}

	private void writeEssay(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println(" <div class='answer'><b>我的作答</b>：");
		if (null != qj.getStuAnswers()){
			out.print(qj.getStuAnswers()[0] + " </div>");
		}
		else{
			out.println(" 未作答</div>");
		}
		//标准答案
		out.println(" <div class='answer'><b>正确答案</b>：");
		if(qj.getAnswers()!=null){
			if(qj.getAnswers()!=null&&qj.getAnswers().length>0)
			out.println(qj.getAnswers()[0] + "</div>");
			else
				out.print("</div>");
		}else{
			out.println("</div>");
		}
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")</div>");*/
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "</div>");

	}

	private void writeDazi(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='startNewWindow'> 我的作答" + " ");
		if (null != qj.getStuAnswers() && qj.getStuAnswers().length > 3) {
			out.println("已打时间：" + qj.getStuAnswers()[0] + "、");
			out.println("打对字数：" + qj.getStuAnswers()[1] + "、");
//			out.println("速度：" + qj.getStuAnswers()[2] + "<br/>");
			String neirong = qj.getStuAnswers()[3];
			if(null!=neirong){
				neirong= neirong.replace("-=wys=-", "<br/>");
			}else
				neirong="";
			out.println("我打的内容：" +neirong);
		} else
			out.println("该题未作答");
		out.println(" </div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");
	}

	private void writeMail(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='startNewWindow'> 我的作答" + "： ");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				out.println(qj.getStuAnswers()[i] + "、");
			}
		out.println(" </div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		out.println("<div  class='answer'><b>标准答案</b>：");
		if (null != qj.getAnswers())
			for (int i = 0; i < qj.getAnswers().length; i++) {
				if(qj.getAnswers().length>1){
					out.println(qj.getAnswers()[i]+"、");
				}else{
					out.println(qj.getAnswers()[i]);
				}
			}
		out.println("</div>");
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() );
		out.println("</div>");
	}

	private void writeSearch(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='startNewWindow'> 我的作答" + "： ");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				out.println(qj.getStuAnswers()[i] + "、");
			}
		out.println(" </div>");
		out.println("<div  class='answer'><b>标准答案</b>：");
		if (null != qj.getAnswers())
			for (int i = 0; i < qj.getAnswers().length; i++) {
				if(qj.getAnswers().length>1){
					out.println(qj.getAnswers()[i]+"、");
				}else{
					out.println(qj.getAnswers()[i]);
				}
			}
		out.println("</div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() );
		out.println("</div>");

	}

	private void writeOffice(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		// out.println("<input onclick=\"q_yd(" + blockid + "," + qj.getId()
		// + ")\" name=\"questions[" + _qsort
		// + "].stuAnswers\" type=\"hidden\" value=\"\" />");
		out
				.println("<div>下载： <a target='_blank' href='"+SystemConfOp.getStuffUrl()+"download.jsp?filename="
						+ qj.getSubject()
						+ "'>模板文档</a><br/>"
						+ " 我的作答 "
						+ (qj.getStuAnswers() != null ? " <a target='_blank' href='download.jsp?filename=elstuffs/epracanswer/"
								+ qj.getStuAnswers()[0] + "'>下载</a>"
								: "") + "</div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");

	}
}
