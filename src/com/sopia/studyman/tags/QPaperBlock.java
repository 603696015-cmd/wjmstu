package com.sopia.studyman.tags;

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

public class QPaperBlock extends TagSupport {

	private static final long serialVersionUID = 6536487202613250886L;
	private int userid = 0;
	private static final Log logger = LogFactory.getLog(QPaperBlock.class);
	private int sqid = 0;
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Question q = (Question) request.getAttribute("question");
			if(q!=null){
				ExamPaperBlock epb = q.getEpblock();
				writeEPQs(out, epb);
			}
		} catch (Exception ex) {
			logger.error("试卷显示出错",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	private void writeEPQs(JspWriter out,ExamPaperBlock epb) throws Exception {
		List<Question> qs = epb.getQuestions();
		if(qs!=null)
		for (int j = 0; j < qs.size(); j++) {
			Question qj = qs.get(j);
			//请对照范文进行打字，打错的字将被标为红色。打字题提交后将不能修改，也不能再次作答。点击下方“开始答题”进入打字页面。

			out.println("<div class='question' id='question_"+ epb.getId()+"_" + qj.getSortid()
					+ "'>");
			out.println("<b>第" + (qj.getSortid()) + "题</b>("
					+ epb.getEachscore() + "分)<p>" +(qj.getQtype()!=8&&qj.getQtype()!=9&&qj.getQtype()!=10&&qj.getQtype()!=15&&qj.getQtype()!=16&&qj.getQtype()!=17&&qj.getQtype()!=18&&qj.getQtype()!=19&&qj.getQtype()!=20?qj.getContent_():"") + "</p> ");
			out.println("<input name=\"questions_"+ epb.getId()+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
					+ "_epblock_id\" type=\"hidden\" value=\"" + epb.getId()
					+ "\" />");
			out.println("<input name=\"questions_"+ epb.getId()+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
					+ "_epblock_sortid\" type=\"hidden\" value=\"" + epb.getSortid()
					+ "\" />");
			out.println("<input name=\"questions_"+ epb.getId()+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
					+ "_id\" type=\"hidden\" value=\"" + qj.getId()
					+ "\" />");
			out.println("<input name=\"questions_"+ epb.getId()+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
					+ "_sortid\" type=\"hidden\" value=\"" + qj.getSortid()
					+ "\" />");
			if (qj.getQtype() == 7) {// 材料题
				List<Question> qjc = qj.getChilds();
				for (int i = 0; i < qjc.size(); i++) {
					Question qjci = qjc.get(i);
					qjci.setParent(qj);
					out.println("<div class='question1' sortid='"+qjci.getSortid()+"' qtype='"+qjci.getQtype()+"' id='question_c_"+epb.getId()+"_" + (qjci.getParent()==null?"0_":qjci.getParent().getSortid()+"_")+qjci.getSortid()+"'>");
					out.println("<input name=\"questions_"+ epb.getId()+"_" + (qjci.getParent()==null?"0_":qjci.getParent().getSortid()+"_")+qjci.getSortid()
							+ "_epblock_id\" type=\"hidden\" value=\"" + epb.getId()
							+ "\" />");
					out.println("<input name=\"questions_"+ epb.getId()+"_" + (qjci.getParent()==null?"0_":qjci.getParent().getSortid()+"_")+qjci.getSortid()
							+ "_epblock_sortid\" type=\"hidden\" value=\"" + epb.getSortid()
							+ "\" />");
					out.println("<input name=\"questions_"+ epb.getId()+"_" + (qjci.getParent()==null?"0_":qjci.getParent().getSortid()+"_")+qjci.getSortid()
							+ "_id\" type=\"hidden\" value=\"" + qjci.getId()
							+ "\" />");
					out.println("<input name=\"questions_"+ epb.getId()+"_" + (qjci.getParent()==null?"0_":qjci.getParent().getSortid()+"_")+qjci.getSortid()
							+ "_sortid\" type=\"hidden\" value=\"" + qjci.getSortid()
							+ "\" />");
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

	private void writeEPQsComm(JspWriter out, Question qj, int blockid) throws Exception {
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

	private void writeYesOrNo(JspWriter out, Question qj, int blockid) throws Exception {
		out.println("<div class='answer'><b>填写答案</b>：");
		if (null == qj.getStuAnswers() || "".equals(qj.getStuAnswers()[0])) {
			out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
					+ "_stuAnswers\" type=\"radio\" value=\"yes\" />正确");
			out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
					+ "_stuAnswers\" type=\"radio\" value=\"no\" />错误");
			out.println("</div>");
			return;
		} else {
			if (qj.getStuAnswers()[0].equals("yes"))
				out
						.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
								+ "_stuAnswers\"  checked='checked' type=\"radio\" value=\"yes\" />正确");
			else
				out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
						+ "_stuAnswers\" type=\"radio\" value=\"yes\" />正确");

			if (qj.getStuAnswers()[0].equals("no"))
				out
						.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
								+ "_stuAnswers\"  checked='checked' type=\"radio\" value=\"no\" />错误");
			else
				out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
						+ "_stuAnswers\" type=\"radio\" value=\"no\" />错误");
		}
		out.println("</div>");
	}

	private void writeSelect(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println("<p>");
		if(null!=qj.getOptions())
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
					+ qj.getOptions()[i] + "<br/>");
		}
		out.print("</p>");

		out.println("<div  class='answer'><b>填写答案</b>：");
		if(null!=qj.getOptions())
			for (int i = 0; i < qj.getOptions().length; i++) {
			if (qj.getQtype() == 2) {
				if (checkSAnswer(qj, i))
					out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
							+ "_stuAnswers\" type=\"radio\" value=\"" + i
							+ "\"  checked='checked'/>" + ""
							+ ExamPaperUtil.getABC(i));
				else
					out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
							+ "_stuAnswers\" type=\"radio\" value=\"" + i
							+ "\" />" + "" + ExamPaperUtil.getABC(i));
			} else {
				if (checkSAnswer(qj, i))
					out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
							+ "_stuAnswers\" type=\"checkbox\" value=\"" + i
							+ "\" checked='checked'/>" + ""
							+ ExamPaperUtil.getABC(i));
				else
					out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
							+ "_stuAnswers\" type=\"checkbox\" value=\"" + i
							+ "\" />" + "" + ExamPaperUtil.getABC(i));
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

	private void writeBlank(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println("<div class='answer'> <b>填写答案</b>：");

		for (int i = 0; i < qj.getAnswers().length; i++) {
			if (null != qj.getStuAnswers() && null != qj.getStuAnswers()[i]){
				out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
						+ "</font>&nbsp;" + " <input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid() + "_stuAnswers\" type=\"text\"  value=\""
						+ qj.getStuAnswers()[i] + "\"/>  ");
			}else{
				out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
						+ "</font>&nbsp;" + " <input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
						+ "_stuAnswers\" type=\"text\"  value=\"\"/>  ");
			}
		}
		out.println("</div>");
	}

	private void writeEssay(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer'><b>填写答案</b>：<br/>");
		if (null != qj.getStuAnswers())
			out.println("<textarea cols=80 rows=18 onkeyup=\"q_yd("+blockid+","+qj.getId()+");qsave_("+blockid+"," + (qj.getParent()==null?"0,":(qj.getParent().getSortid()+","))+qj.getSortid()+",this);\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
							+ "_stuAnswers\" >"
					+ qj.getStuAnswers()[0] + "</textarea></div>");
		else
			out.println("<textarea cols=80 rows=18 onkeyup=\"q_yd("+blockid+","+qj.getId()+");qsave_("+blockid+"," + (qj.getParent()==null?"0,":(qj.getParent().getSortid()+","))+qj.getSortid()+",this);\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
							+ "_stuAnswers\" ></textarea></div>");

	}

	private void writeDazi(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println("<div class='startNewWindow'>");// <a style='cursor:pointer' class='textbg' onclick=\"openNewWindowQ(this, "+blockid+","+qj.getId()+")\">开始答题"+"</a>");
		out.println("<input name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
				+ "_stuAnswers\" type=\"hidden\" value=\"\"/></div>");
	}

	private void writeMail(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println("<div class='startNewWindow'>");// <a style='cursor:pointer' class='textbg' onclick=\"openNewWindowQ(this, "+blockid+","+qj.getId()+")\">开始答题"+"</a>");
		out.println("<input name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
				+ "_stuAnswers\" type=\"hidden\" value=\"\" />");
		out.println(" </div>");
	}

	private void writeSearch(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println("<div class='startNewWindow'>");// <a style='cursor:pointer' class='textbg' onclick=\"openNewWindowQ(this, "+blockid+","+qj.getId()+")\">开始答题"+"</a>");
		out.println("<input name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
				+ "_stuAnswers\" type=\"hidden\" value=\"\" /></div>");

	}

	private void writeOffice(JspWriter out, Question qj, int blockid)
			throws Exception {
//		out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
//				+ "_stuAnswers\" type=\"hidden\" value=\"\" />");
		out
				.println("<div>下载： <a target='_blank' href='"+SystemConfOp.getStuffUrl()+"download.jsp?filename="
						+ qj.getSubject()
						+ "'>模板文档</a><br/>"
						+ "上传： 我的答案<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" type='file' id='office_"
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
						+ sqid
						+ "_"
						+ blockid
						+ "_"
						+ qj.getId()
						+ "\" name=\"questions_"+ blockid+"_" + (qj.getParent()==null?"0_":qj.getParent().getSortid()+"_")+qj.getSortid()
							+ "_stuAnswers\" />	<input onclick=\"q_yd("+blockid+","+qj.getId()+");upload_offices2( "
						+ blockid + "," + qj.getId() + " )\" type='button' value='上传' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:red;' id='uploadTxt_"+qj.getId()+"'></span></div>");

	}
}
