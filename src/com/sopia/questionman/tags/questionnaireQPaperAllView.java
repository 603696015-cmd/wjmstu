package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;

import com.sopia.common.ExamPaperUtil;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.getFloat;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;

public class questionnaireQPaperAllView extends TagSupport {
	private static final Log logger = LogFactory.getLog(QPaperAllView.class);
	private static final long serialVersionUID = 6536487202613250886L;
	int age = 0;

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
			age = ep.getUserage();
			List<ExamPaperBlock> epbs = ep.getEpBlocks();
			writeEPBs(out, epbs);
		} catch (Exception ex) {
			logger.error("答卷查看失败",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	private void writeEPBs(JspWriter out, List<ExamPaperBlock> epbs)
			throws Exception {
		if (null != epbs)
			for (int j = 0; j < epbs.size(); j++) {
				ExamPaperBlock epbj = epbs.get(j);
				out.println("<div id='block_" + j + "' class='block'>");
				out.println("<div class='block_name'>");
				out.println("第"
						+ ExamPaperUtil.getBaseNum2Chinese(epbj.getSortid())
						+ "题:" + epbj.getTitle());
				out.println("</div>");
				out.println("<div class='block_desc'>");
				if (epbj.getDescription() != null) {
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
			qj.setScore(epb.getEachscore());
			out.println("<div class='question'>");
			out.println("<div class='sort'>" + qj.getSortid()
					+ ".</div><div class='content'>");//【" + epb.getEachscore()	+ "分】
			if (qj.getQtype() == 7) {// 材料题
				List<Question> qjc = qj.getChilds();
				out.println("<div>" + qj.getContent_() + "</div> ");
				for (int i = 0; i < qjc.size(); i++) {
					out.println("<div class='question1'>");
					Question qjci = qjc.get(i);
					out.println("<div><b>第" + qjci.getSortid() + "题</b>" + qjci.getContent_() + "</div> ");
//					【"
//					+ (epb.getEachscore() * qjci.getScoreper() / 100)
//					+ "分】
					qjci.setScore((epb.getEachscore() * qjci.getScoreper() / 100));
					writeEPQsComm(out, qjci, epb.getId());
					out.println("</div><br>");
				}
			} else {
				if(qj.getQtype()==8){
					writeDz(out, qj, epb.getId(), qj.getSortid());
				}else{
					out.println("<div>" + qj.getContent_() + "</div> ");
				}
				writeEPQsComm(out, qj, epb.getId());
			}
			out.println("</div></div>");
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
//			writeDazi(out, qj, blockid, qj.getSortid());
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
	
	/**
	 * 输出答案解析
	 * @param out
	 * @param qj
	 * @throws Exception
	 */
	private void printQexplain(JspWriter out, Question qj) throws Exception{
		if(qj.getQexplain()!=null&&!"".equals(qj.getQexplain())){
		//	out.print("<div>");
		//	out.println("<b>答案解析：</b>"+ qj.getQexplain());
		//	out.print("</div>");
		}
	}

	private void writeYesOrNo(JspWriter out, Question qj, int blockid)
			throws Exception {
		// out.println("<p>" + qj.getContent()+ "</p>");
		String myStuAnswer = "";
		if (qj.getStuAnswers() != null) {
			myStuAnswer = qj.getStuAnswers()[0];
		}
		out.println("<div class='answer'"+(qj.getMyScore()==0?" style='color:red'":"")+">");
	//	out.println("<div><b>标准答案</b>：");
	//	if (qj.getAnswers()[0].equals("yes"))
	//		out.println("正确");
	//	else if(qj.getAnswers()[0].equals("no"))
	//		out.println("错误");
		out.print("</div><div><b>考生答案</b>：");
		if (myStuAnswer.equals("yes"))
			out.println("正确");
		else if (myStuAnswer.equals("no"))
			out.println("错误");
//		out.print("&nbsp;&nbsp;&nbsp;【系统打分:" + qj.getMyScore() + "】");
		// if (qj.getStuAnswers() != null) {
		// if ("".equals(qj.getStuAnswers()[0].trim()))
		// out.println("该题未考生答案");
		// else {
		// if (qj.getStuAnswers()[0].equals(qj.getAnswers()[0]))
		// out.println("考生答案正确");
		// else if (!qj.getStuAnswers()[0].equals(qj.getAnswers()[0]))
		// out.println("考生答案不正确");
		// }
		// }
		// out.println("<br/><b>答案解析：</b>"
		// + (qj.getQexplain() == null ? "" : qj.getQexplain()));
//		out.print("</div><div><b>本题分数</b>："+qj.getScore()+"分");
//		out.println("</div></div>");
		out.print("</div>");
		this.printQexplain(out, qj);
		out.println("<b>批语：</b>"+ qj.getPiyu() );
		out.print("</div>");
	}

	private void writeSelect(JspWriter out, Question qj, int blockid)
			throws Exception {
		// out .println("<p>" + qj.getContent()+"</p>");
		String myStuAnswer = "";
		if (qj.getStuAnswers() != null) {
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				if (null != qj.getStuAnswers()[i]
						&& !qj.getStuAnswers()[i].trim().equals(""))
					myStuAnswer += ExamPaperUtil.getABC(qj.getStuAnswers()[i])
							+ "、";
			}
		}
		out.println("<div class='select_opt'>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
					+ qj.getOptions()[i] + "<br>");
		}
		out.print("</div>");
		out.println("<div class='answer'"+(qj.getMyScore()==0?" style='color:red'":"")+">");
	//	out.print("<div><b>标准答案：</b>");
	//	if(qj.getAnswers()!=null){
	//		for (int i = 0; i < qj.getAnswers().length; i++) {
	//			out.print(ExamPaperUtil.getABC(qj.getAnswers()[i]) + "、");
	//		}
	//	}
		out.print("<div><b>考生答案：</b>" + myStuAnswer+"</div>");
//				+ "&nbsp;&nbsp;&nbsp;【系统打分:" + qj.getMyScore() + "】<br/>");
		// if (null != qj.getStuAnswers())
		// for (int i = 0; i < qj.getStuAnswers().length; i++) {
		// if (!"".equals(qj.getStuAnswers()[i].trim()))
		// out.println("" + ExamPaperUtil.getABC(qj.getStuAnswers()[i]) + "、");
		// }

		// out
		// .println("<div style='color:green'><b>答案解析：</b>"
		// + (qj.getQexplain() == null ? "" : qj.getQexplain())
		// + "</div>");
//		out.print("<div><b>本题分数</b>："+qj.getScore()+"分</div>");
		this.printQexplain(out, qj);
//		out.println("<b>批语：</b>"+ qj.getPiyu() );
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
		out.println("<div class='answer'"+(qj.getMyScore()==0?" style='color:red'":"")+">");
		out.println("<div><b>标准答案</b>：");
		if (null != qj.getAnswers())
			for (int i = 0; i < qj.getAnswers().length; i++) {
				out.println(qj.getAnswers()[i] + "、");
			}
		out.println("</div>");
		out.print("<div><b>考生答案</b>："/*
									 * + "【系统打分:" + qj.getMyScore() + "】"
									 */);
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				out.println(qj.getStuAnswers()[i] + "、");
			}
		// out
		// .println("<div style='color:green'><b>答案解析：</b>"
		// + (qj.getQexplain() == null ? "" : qj.getQexplain())
		// + "</div>");
//		out.print("</div><div><b>本题分数</b>："+qj.getScore()+"分</div>");
		out.println("</div>");
		this.printQexplain(out, qj);
		out.println("<b>批语：</b>"+ qj.getPiyu() );
		out.print("</div>");
	}

	private void writeEssay(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer' "+(qj.getMyScore()==0?"style='color:red'":"")+">"
				+ "<div><b>考生答案：</b>");
		if (null != qj.getStuAnswers())
			out.println(qj.getStuAnswers()[0].trim() + "");
		out.println("</div><div><b>标准答案：</b>：" +( qj.getAnswers()!=null?(qj.getAnswers().length>0?qj.getAnswers()[0]:""):""));
		// if (null != qj.getAnswers())
		// for (int i = 0; i < qj.getAnswers().length; i++) {
		// out
		// .print("答案关键字" + (i + 1) + ":" + qj.getAnswers()[i]
		// + "<br>");
		// }
//		out.println("</div><div><b>本题分数:</b>" + qj.getScore() + "</div></div>");
		out.print("</div>");
		this.printQexplain(out, qj);
		out.println("<b>批语：</b>"+ qj.getPiyu() );
		out.print("</div>");
		// out
		// .println("<div style='color:green'><b>答案解析：</b>"
		// + (qj.getQexplain() == null ? "" : qj.getQexplain())
		// + "</div>");
	}

	private void writeDazi(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='startNewWindow' style='color:green;'>("+qj.getMyScore()+")<b>我的作答</b>" + " ");
		if (null != qj.getStuAnswers() && qj.getStuAnswers().length > 3) {
			int t = ExamPaperUtil.getInt(qj.getStuAnswers()[0]);
			int r = ExamPaperUtil.getInt(qj.getStuAnswers()[1]);
			float l = ExamPaperUtil.getFloat(qj.getStuAnswers()[2]);
			// v = t==0?0:r/t;
			int ws = 0;
			String neirong = qj.getStuAnswers()[3];
			if (null != neirong) {
				String s[] = neirong.split("-=wys=-");
				if (s != null)
					for (int i = 0; i < s.length; i++) {
						if (s[i] != null && !"".equals(s[i].trim()))
							ws = ws + s[i].length();
					}
			}
			out.println("成绩:" + qj.getMyScore() + "分(" + qj.getMystatusStr()
					+ ")、");
			int t_ = ExamPaperUtil.getInt(qj.getRules()[2]);
			 
			int sec = t % 60;
			int min = (t / 60) % 60;
			int hour = (t / 60 / 60);
			String xxx = hour + "";
			xxx = xxx + ":" + (min > 9 ? min : "0" + min);
			xxx = xxx + ":" + (sec > 9 ? sec : "0" + sec);
			// float x = l == 0?0:getFloat.GetFloat(r/(l*1.0f)) ;
			qj.setAge(age);
			float x = l == 0 ? 0 : getFloat.GetFloat(r
					/ (qj.getMansize() * 1.0f));
			out.println("范文字数：" + (int) l + "、");
			out.println("满分所需字数：" + qj.getMansize() + "、");
			out.println("规定时间：" + t_ + "分钟、");
			out.println("已打时间：" + xxx + "、");
			out
					.println("试题完成率："
							+ (((int) (100 * x)) > 100 ? 100
									: ((int) (100 * x)) == 0 ? 0.001f
											: (int) (100 * x)) + "%、<br />");
			out.println("输入总字数：" + ws + "个、");
			out.println("正确字数：" + r + "个、");
			out.println("错误字数：" + (ws - r) + "个、");
			out.println("即时速度：" + getFloat.GetFloat((r * 1.0) / (1.0 * t / 60))
					+ "字/分、");
			// 判断正确字数是否大于满分字数
			if (qj.getMansize() <= r) {
				// 实际速度
				out.println("打字速度："
						+ getFloat.GetFloat((r * 1.0) / (1.0 * t / 60))
						+ "字/分<br />");
			} else {
				out.println("打字速度：" + getFloat.GetFloat((r * 1.0) / (1.0 * t_))
						+ "字/分<br />");
			}
			out.print("<br/>");
			out.println("<b>批语：</b>"+ qj.getPiyu() );
		} else
			out.println("该题未作答<br/><br/>");
		out.println("<b>批语：</b>"+ qj.getPiyu() );
		out.println(" </div>");
	}

	private void writeDz(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		writeDazi(out, qj, blockid, _qsort);
		out.print("<div style='font-size:15px;'>");
		TextExtractingVisitor v = new TextExtractingVisitor();
		Parser p = Parser.createParser(qj.getContent_(), "UTF-8");// 创建htmlparser对象
		p.visitAllNodesWith(v);// 大概意思是填充到迭代器
		String daziContent = v.getExtractedText();// 获取解析过滤后的文本//CheckHtml.getString(qj.getContent_());
		// daziContent=daziContent.replaceAll("\r\n\r\n", "");
		daziContent = daziContent.replaceAll("\r", "");
		daziContent = daziContent.replaceAll("\n", "");
		daziContent = daziContent.replaceAll("\t", " ");
		int row = daziContent.length() % 34 == 0 ? (daziContent.length() / 34)
				: (daziContent.length() / 34 + 1);
		String fw[] = new String[row];
		String my[] = new String[row];
		for (int i = 0; i < row - 1; i++) {
			fw[i] = StringUtil
					.toSBC(daziContent.substring(i * 34, i * 34 + 34));
		}
		fw[row - 1] = StringUtil.toSBC(daziContent.substring((row - 1) * 34,
				daziContent.length()));
		if (null != qj.getStuAnswers() && qj.getStuAnswers().length > 3) {
			String myans = qj.getStuAnswers()[3];
			if (myans != null) {
				for (int i = 0; i < myans.split("-=wys=-").length; i++) {
					my[i] = myans.split("-=wys=-")[i];
				}
			}
		}
		for (int i = 0; i < row; i++) {
			StringBuffer bf = new StringBuffer();
			for (int k = 0; k < fw[i].length(); k++) {
				if (my[i] != null && k < my[i].length())
					if ((fw[i].charAt(k) == my[i].charAt(k))) {
						// 正确的
						bf.append("<font color='blue'>" + fw[i].charAt(k)
								+ "</font>");
					} else {
						// 打错的
						bf.append("<font color='red'>" + fw[i].charAt(k)
								+ "</font>");
					}
				else
					bf.append(fw[i].charAt(k));
			}
			// if(my[i]!=null&&my[i].length()<34){
			// for (int k = my[i].length(); k < fw[i].length(); k++) {
			// bf.append(fw[i].charAt(k));
			// }
			// }else
			// bf.append("&nbsp;");
			out.print("<div style='padding-top:3px;'>" + bf.toString()
					+ "</div>");
			out.print("<div style='color:#111111'>"
					+ (my[i] == null || "".equals(my[i].trim()) ? "&nbsp;"
							: my[i]) + "</div>");
		}
		out.print("</div>");
	}

	private void writeDazi2(JspWriter out, Question qj, int blockid)
			throws Exception {

		out.println("<div> 范文： " + qj.getSubject()
				+ "<br/> <div class='answer'><b>考生答案</b>：【系统打分:"
				+ qj.getMyScore() + "】速 度：");
		if ("-=SpEl=-".equals(qj.getStuAnswer()))
			out.print("未作答");
		else
			out.println(qj.getStuAnswers()[0] + "个字/秒，打对的字数："
					+ qj.getStuAnswers()[1] + "个， " + "打字时间："
					+ qj.getStuAnswers()[2] + "秒。<br/><b>我打的字数：</b>"
					+ qj.getStuAnswers()[3]);
		out.println(" </div> </div>");
	}

	private void writeMail(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer' "+(qj.getMyScore()==0?"style='color:red'":"")+"><div><b>考生答案</b>：");
		if(qj.getStuAnswers()!=null)
		out.println("发 给：" + qj.getStuAnswers()[0] + "<br />" + "抄 送："
				+ qj.getStuAnswers()[1] + "<br />" + "密 送："
				+ qj.getStuAnswers()[2] + "<br />" + "主 题："
				+ qj.getStuAnswers()[3] + "<br />" + "附 件："
				+ qj.getStuAnswers()[4] + "<br />" + "正 文："
				+ qj.getStuAnswers()[5] + "  <br>");
		out.println("</div><div><b>标准答案</b> 发 给："
				+ qj.getAnswers()[0] + "<br />" + "抄 送：" + qj.getAnswers()[1]
				+ "<br />" + "密 送：" + qj.getAnswers()[2] + "<br />" + "主 题："
				+ qj.getAnswers()[3] + "<br />" + "附 件：" + qj.getAnswers()[4]
				+ "<br />" + "正 文：" + qj.getAnswers()[5] + "</div>");
//		out.print("<div><b>本题分数</b>："+qj.getScore()+"分</div></div>");
		out.println("<b>批语：</b>"+ qj.getPiyu() );
		out.print("</div>");
	}

	private void writeSearch(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.print(" <div class='answer' "+(qj.getMyScore()==0?"style='color:red'":"")+"><div><b>考生答案：</b>");
		if(qj.getStuAnswers()!=null)
		out.print( qj.getStuAnswers()[0] + "</div>");
		else
			out.print("</div>");
//				+ "答&nbsp;&nbsp;&nbsp;案：" + qj.getStuAnswers()[1]);
		out.print("<div><b>标准答案：</b> "
				+ qj.getAnswers()[0] + "</div>");
//		out.print("<div><b>本题分数</b>："+qj.getScore()+"分</div></div>");
		out.println("<b>批语：</b>"+ qj.getPiyu() );
		out.print("</div>");
	}

	private void writeOffice(JspWriter out, Question qj, int blockid)
			throws Exception {
		
		out.println("<div class='answer' "+(qj.getMyScore()==0?"style='color:red'":"")+"><div><b>要求文档：</b><a target='_blank' href='"
				+ SystemConfOp.getStuffUrl() + "download.jsp?filename="
				+ qj.getSubject() + "'>模板文档</a></div><div>"
				+ "<b>我的答案：</b><a href='download.jsp?filename=elstuffs/quizanswer/"
				+ (qj.getStuAnswers()!=null?qj.getStuAnswers()[0]:"未作答") + "'>下载我的答案</a></div>");
//		out.print("<div><b>本题分数</b>："+qj.getScore()+"分</div></div>");
		this.printQexplain(out, qj);
		out.println("<b>批语：</b>"+ qj.getPiyu() );
		out.print("</div>");
	}
}
