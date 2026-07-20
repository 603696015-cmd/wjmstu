package com.sopia.questionman.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.htmlparser.Parser;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.http.HttpHeader;
import org.htmlparser.util.ParserUtils;
import org.htmlparser.visitors.TextExtractingVisitor;


import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.ExamPaperUtil;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.UserExcelUtil;
import com.sopia.common.getFloat;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;

public class QPaper1b1ViewWjm extends TagSupport {

	private static final long serialVersionUID = 6536487202613250886L;
	private static final Log logger = LogFactory.getLog(QPaper1b1ViewWjm.class);
	int age = 0 ;
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		JspWriter out=null;
		try {
			out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ExamPaper ep = (ExamPaper) request.getAttribute("examPaper");
			age = ep.getUserage();
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
		}finally{
//			try {
//				out.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//			}
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
					+ epb.getEachscore() + "分)");
			if(qj.getQtype()==8){
				//先去掉打字内容的html标记
//				TextExtractingVisitor v = new TextExtractingVisitor();
//				Parser p = Parser.createParser(qj.getContent_(), "UTF-8");//创建htmlparser对象
//				p.visitAllNodesWith(v);//大概意思是填充到迭代器
//				String daziContent=CheckHtml.getString(qj.getContent_());
//				//String daziContent=v.getExtractedText();//获取解析过滤后的文本//CheckHtml.getString(qj.getContent_());
//				daziContent=daziContent.replaceAll("\r\n\r\n", " ");
//				daziContent=daziContent.replaceAll("\r", "");
//				daziContent=daziContent.replaceAll("\n", "");
////				daziContent=daziContent.replaceAll(" \t", " ");
////				daziContent=daziContent.replaceAll("\t\t", " ");
////				daziContent=daziContent.replaceAll("\t\t\t", " ");
//				daziContent=daziContent.replaceAll("\t", " ");
//				String neirong=null;
//				StringBuffer newdaziContent=new StringBuffer("");
//				//String[] mydaziArray=null;
//				if(null != qj.getStuAnswers() && qj.getStuAnswers().length > 3){
//					neirong = qj.getStuAnswers()[3];
//					updateDaziColor(daziContent,neirong,newdaziContent);
//				}else{
//					newdaziContent.append(daziContent);
//				}
//				out.println("<p><div name='dazicontent'>" + newdaziContent.toString() + "</div></p> ");
				writeDz(out, qj, epb.getId(), _qsort);
			}else{
				out.println("<p>" + qj.getContent_() + "</p> ");
			}
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
							+ "分)<p>" + qjci.getContent_() + "</p> ");
					writeEPQsComm(out, qjci, epb.getId(), _qsort);
					out.println("</div><br/>");

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
//			writeDazi(out, qj, blockid, _qsort);
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
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
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

	// private boolean checkSAnswer(Question q, int i) {
	// if (null != q.getStuAnswers())
	// for (int j = 0; j < q.getStuAnswers().length; j++) {
	// if (!q.getStuAnswers()[j].trim().equals(""))
	// if (i == new Integer(q.getStuAnswers()[j]))
	// return true;
	// }
	// return false;
	// }

	private void writeBlank(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {//hdl
		out.println("<div class='answer'> <b>我的作答</b>：");

		if(qj.getStuAnswer()!=null&&qj.getStuAnswers()!=null){
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				// if (null != qj.getStuAnswers() && null != qj.getStuAnswers()[i])
				// out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
				// + "</font>&nbsp;" + " <input name=\"questions["
				// + _qsort + "].stuAnswers\" type=\"text\" value=\""
				// + qj.getStuAnswers()[i] + "\"/> ");
				// else
				out.println(" <font class=\"xuxian\">空白作答" + (i + 1)
						+ "</font>&nbsp;" + " <input onclick=\"q_yd(" + blockid
						+ "," + qj.getId() + ")\" name=\"questions[" + _qsort
						+ "].stuAnswers\" type=\"text\" readonly=\"readonly\" value=\""+qj.getStuAnswers()[i]+"\"/>  ");
	
			}
			out.println("</div>");
		}else{
			out.println("</div>");
		}
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		//标准答案
		out.println("<div class='answer'><b>标准答案</b>：");
		if (null != qj.getAnswers())
			for (int i = 0; i < qj.getAnswers().length; i++) {
				if(qj.getAnswers().length>1){
					out.println(qj.getAnswers()[i]+"、");
				}else{
					out.println(qj.getAnswers()[i]);
				}
			}
		out.println(" </div>");
		
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");
	}

	private void writeEssay(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println(" <div class='answer'><b>我的作答</b>：");
		if (null != qj.getStuAnswers())
			out.println(qj.getStuAnswers()[0] + "</div>");
		// else
		// out.println("<textarea cols=30 rows=5 name=\"" + blockid + "_"
		// + qj.getId() + "_" + qj.getSortid()
		// + "\" ></textarea></div>");
		else
			out.println("未作答</div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		out.println(" <div class='answer'><b>正确答案</b>：");
		if(qj.getAnswers()!=null&&qj.getAnswers().length>=1){
			out.println(qj.getAnswers()[0] + "</div>");
		}else{
			out.println("</div>");
		}
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");

	}

	private void writeDazi(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='startNewWindow'> 我的作答" + " ");
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
//
//			} else
//				neirong = "";
			out.println( "成绩:" + qj.getMyScore() + "分("+qj.getMystatusStr()+")、");
			int t_ = ExamPaperUtil.getInt(qj.getRules()[2]);
//			int age = ExamPaperUtil.getInt(requestion);;
//			int vimax = 0;
//			if(qj.getDazirule()!=null)
//			for (int i = 0; i < qj.getDazirule().length; i++) {
//				int age_b = ExamPaperUtil.getInt(qj.getDazirule()[i][0]);
//				int age_e = ExamPaperUtil.getInt(qj.getDazirule()[i][1]);
//				if (age_b <= age && age <= age_e) {
//					vimax = ExamPaperUtil.getInt(qj.getDazirule()[i][3]);
//				}
//			}
//			int mfzs = t_ * vimax;
			int sec = t % 60;
			int min = (t / 60) % 60;
			int hour = (t / 60 / 60);
			String xxx = hour + "";
			xxx = xxx + ":" + (min > 9 ? min : "0" + min);
			xxx = xxx + ":" + (sec > 9 ? sec : "0" + sec);
			//float  x = l == 0?0:getFloat.GetFloat(r/(l*1.0f)) ;
			qj.setAge(age);
			float  x = l == 0?0:getFloat.GetFloat(r/(qj.getMansize()*1.0f)) ;
			//qj.setAge(UserExcelUtil.getAgeBySfz());
			out.println("范文字数：" + (int)l + "、");
			out.println("满分所需字数：" + qj.getMansize() + "、");
			out.println("规定时间：" + t_ + "分钟、");
			out.println("已打时间：" + xxx + "、");
			out.println("试题完成率：" + ( ((int)(100* x ))>100?100:((int)(100* x ))==0?0.001f:(int)(100* x ))  + "%、<br />");
			out.println("输入总字数：" + ws + "个、");
			out.println("正确字数：" + r + "个、");
			out.println("错误字数：" + (ws - r) + "个、");
			out.println("即时速度：" + getFloat.GetFloat((r*1.0)/(1.0*t/60)) + "字/分、");
			//判断正确字数是否大于满分字数
			if(qj.getMansize()<=r){
				//实际速度
				out.println("打字速度：" + getFloat.GetFloat((r*1.0)/(1.0*t/60)) + "字/分<br />");
			}else{
				out.println("打字速度：" + getFloat.GetFloat((r*1.0)/(1.0*t_)) + "字/分<br />");
			}
//			out.println("<br/>我打的内容：" + neirong);

		} else
			out.println("该题未作答");
		out.println(" </div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
//		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() );
//		out.println("</div>");
	}
	private void writeDz(JspWriter out,Question qj,int blockid,int _qsort)throws Exception{
		writeDazi(out, qj, blockid, _qsort);
		TextExtractingVisitor v = new TextExtractingVisitor();
		Parser p = Parser.createParser(qj.getContent_(), "UTF-8");//创建htmlparser对象
		p.visitAllNodesWith(v);//大概意思是填充到迭代器
		String daziContent= v.getExtractedText();//获取解析过滤后的文本//CheckHtml.getString(qj.getContent_());
//		daziContent=daziContent.replaceAll("\r\n\r\n", "");
		daziContent=daziContent.replaceAll("\r", "");
		daziContent=daziContent.replaceAll("\n", "");
		daziContent=daziContent.replaceAll("\t", " ");
		int row = daziContent.length()%34==0?(daziContent.length()/34):(daziContent.length()/34+1);
		String fw[]=new String[row];
		String my[]=new String[row];
		for (int i = 0; i < row-1; i++) {
			fw[i] = StringUtil.toSBC(daziContent.substring(i*34,i*34+34));
		}
		fw[row-1]= StringUtil.toSBC(daziContent.substring((row-1)*34,daziContent.length()));
		if(null != qj.getStuAnswers() && qj.getStuAnswers().length > 3){
			String myans = qj.getStuAnswers()[3];
			if(myans!=null)
			{
				for (int i = 0; i < myans.split("-=wys=-").length; i++) {
					my[i] = myans.split("-=wys=-")[i];
				}
			}
		}
		for (int i = 0; i < row; i++) {
			StringBuffer bf = new StringBuffer();
			for(int k=0;k<fw[i].length();k++){
				if(my[i]!=null&&k<my[i].length())
				if((fw[i].charAt(k)==my[i].charAt(k) )){
					//正确的
					bf.append("<font color='blue'>"+fw[i].charAt(k)+"</font>");
				}else{
					//打错的
					bf.append("<font color='red'>"+fw[i].charAt(k)+"</font>");
				}else
					bf.append(fw[i].charAt(k));
			}
//			if(my[i]!=null&&my[i].length()<34){
//				for (int k = my[i].length(); k < fw[i].length(); k++) {
//					bf.append(fw[i].charAt(k));
//				}
//			}else
//				bf.append("&nbsp;");
			out.print("<div style='padding-top:6px;'>"+bf.toString()+"</div>");
			out.print("<div style='color:#111111'>"+(my[i]==null||"".equals(my[i].trim())?"&nbsp;":my[i])+"</div>");
		}
	}
	private void writeMail(JspWriter out, Question qj, int blockid, int _qsort)
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
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");
	}

	private void writeSearch(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='startNewWindow'> 我的作答" + "： ");
//		if (null != qj.getStuAnswers())
//			for (int i = 0; i < qj.getStuAnswers().length; i++) {
//				out.println(qj.getStuAnswers()[i] + "、");
//			}
		//去掉逗号
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				if(qj.getStuAnswers().length>1){
					out.println(qj.getStuAnswers()[i]+"、");
				}else{
					out.println(qj.getStuAnswers()[i]);
				}
				
			}
		out.println(" </div>");
		/*out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + "("
				+ qj.getMystatusStr() + ")");*/
		//标准答案
		out.println("<div class='answer'><b>标准答案</b>：");
		if (null != qj.getAnswers())
			for (int i = 0; i < qj.getAnswers().length; i++) {
				if(qj.getAnswers().length>1){
					out.println(qj.getAnswers()[i]+"、");
				}else{
					out.println(qj.getAnswers()[i]);
				}
				
			}
		out.println(" </div>");
		
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");

	}

	private void writeOffice(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<input onclick=\"q_yd(" + blockid + "," + qj.getId()
				+ ")\" name=\"questions[" + _qsort
				+ "].stuAnswers\" type=\"hidden\" value=\"\" />");
		out
				.println("<div>下载： <a target='_blank' href='"+SystemConfOp.getStuffUrl()+"download.jsp?filename="
						+ qj.getSubject()
						+ "'>模板文档</a><br/>"
						+ " 我的作答"
						+ (qj.getStuAnswers() != null ? " <a target='_blank' href='download.jsp?filename=/elstuffs/quizanswer/"
								+ qj.getStuAnswers()[0] + "'>下载</a>"
								: "") + " </div>");
		out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore());
		out.println("</div>");
	}
	
	private void updateDaziColor(String daziContent,String mydaziContent,StringBuffer newdaziContent){
		String[] mydaziArray=mydaziContent.split("-=wys=-");//把我打的子拆成数组（后面没打的 会不计入长度，不过不影响此功能实现）
		String[] daziArray=new String[mydaziArray.length];
		for(int i=0;i<mydaziArray.length;i++){
			if(i==mydaziArray.length-1){
				daziArray[i]=daziContent.substring(34*i,daziContent.length());
			}else{
				daziArray[i]=daziContent.substring(34*i,34*(i+1));
			}
		}
		for(int i=0;i<mydaziArray.length;i++){
			for(int k=0;k<mydaziArray[i].length();k++){
					if((StringUtil.toSBC(daziArray[i].charAt(k)+"")).equals(mydaziArray[i].charAt(k)+"")){
						//正确的
						newdaziContent.append("<font color='blue'>"+daziArray[i].charAt(k)+"</font>");
					}else{
						//打错的
						newdaziContent.append("<font color='red'>"+daziArray[i].charAt(k)+"</font>");
					}
			}
			if(mydaziArray[i].length()<34){
				for (int k = mydaziArray[i].length(); k < daziArray[i].length(); k++) {
					newdaziContent.append(daziArray[i].charAt(k));
				}
			}
		}
		//判断题目实际行数是否大于所打字的行数
//		if(daziArray[daziArray.length-1].length()>34){
//			//newdaziContent.delete(newdaziContent.length()-34, newdaziContent.length());
//			newdaziContent.append(daziArray[daziArray.length-1].substring(34,daziArray[daziArray.length-1].length()));
//		}
	}
}
/**
 * 
 * 
 * 
 * private static final long serialVersionUID = 6536487202613250886L; private
 * int userid = 0; private static final Log logger =
 * LogFactory.getLog(QPaper1b1View.class);
 * 
 * @SuppressWarnings("unchecked") public int doStartTag() { try { JspWriter out =
 *                                pageContext.getOut(); ServletRequest request =
 *                                pageContext.getRequest(); ExamPaper ep =
 *                                (ExamPaper) request.getAttribute("examPaper");
 *                                userid = (Integer) ((HttpServletRequest)
 *                                request).getSession()
 *                                .getAttribute(ElConstants.SESSION_USERID); //
 *                                epid = ep.getId(); List<ExamPaperBlock> epbs =
 *                                ep.getEpBlocks(); out .print("<script
 *                                type=\"text/javascript\">var ep = new
 *                                EXAMPAPER(" + ep.getId() + ");</script>\n");
 *                                writeEPBs(out, epbs); out .print("<script
 *                                type=\"text/javascript\"> ep.showCa()</script>\n"); }
 *                                catch (Exception ex) {
 *                                logger.error("试卷显示出错",ex); } return
 *                                TagSupport.SKIP_BODY; }
 * 
 * private void writeEPBs(JspWriter out, List<ExamPaperBlock> epbs) throws
 * Exception { int _qsort = 0; for (int j = 0; j < epbs.size(); j++) {
 * ExamPaperBlock epbj = epbs.get(j); out.print("<script
 * type=\"text/javascript\">var block" + epbj.getId() + "= new BLOCK(" +
 * epbj.getId() + "," + epbj.getSortid() + ",'"+epbj.getTitle()+"');</script>\n");
 * out.println("<div id='block_" + epbj.getId() + "' class='block'>");
 * out.println("<div class='block_name'>"); out.println(epbj.getSortid() + ":" +
 * epbj.getTitle()); out.println("</div>"); out.println("<div
 * class='block_desc'>大题说明："); out.println(epbj.getDescription()); out.println("</div>");
 * int _qsort1 = writeEPQs(out, epbj.getQuestions(), epbj, _qsort); _qsort =
 * _qsort + _qsort1; out.println("</div>"); out.print("<script
 * type=\"text/javascript\"> ep.addBlock(block" + epbj.getId() + ");
 * </script>\n"); } }
 * 
 * private int writeEPQs(JspWriter out, List<Question> qs, ExamPaperBlock epb,
 int _qsort) throws Exception {
 int _qsort1 = 0;
 for (int j = 0; j < qs.size(); j++) {
 Question qj = qs.get(j);
 out
 .print("<script type=\"text/javascript\">var question"
 + qj.getId() + "= new QUESTION(" + qj.getId() + ","
 + qj.getSortid() + ",block" + epb.getId()
 + ");</script>\n");
 out.println("<div class='question' id='question_"+ epb.getId()+"_" + qj.getId()
 + "'>");
 out.println("<b>第" + (qj.getSortid()) + "题</b>("
 + epb.getEachscore() + "分)<p>" + qj.getContent() + "</p> ");
 out.println("<input name=\"questions[" + _qsort
 + "].epblock.id\" type=\"hidden\" value=\"" + epb.getId()
 + "\" />");
 out.println("<input name=\"questions[" + _qsort
 + "].epblock.sortid\" type=\"hidden\" value=\"" + epb.getSortid()
 + "\" />");
 out.println("<input name=\"questions[" + _qsort
 + "].id\" type=\"hidden\" value=\"" + qj.getId()
 + "\" />");
 out.println("<input name=\"questions[" + _qsort
 + "].sortid\" type=\"hidden\" value=\"" + qj.getSortid()
 + "\" />");
 if (qj.getQtype() == 7) {// 材料题
 List<Question> qjc = qj.getChilds();
 for (int i = 0; i < qjc.size(); i++) {
 _qsort++;
 _qsort1++;
 Question qjci = qjc.get(i);
 out.println("<div class='question1'>");
 out.println("<input name=\"questions[" + _qsort
 + "].epblock.id\" type=\"hidden\" value=\"" + epb.getId()
 + "\" />");
 out.println("<input name=\"questions[" + _qsort
 + "].epblock.sortid\" type=\"hidden\" value=\"" + epb.getSortid()
 + "\" />");
 out.println("<input name=\"questions[" + _qsort
 + "].id\" type=\"hidden\" value=\"" + qjci.getId()
 + "\" />");
 out.println("<input name=\"questions[" + _qsort
 + "].sortid\" type=\"hidden\" value=\"" + qjci.getSortid()
 + "\" />");
 out.println("<b>第" + (qjci.getSortid()) + "题</b>("
 + (epb.getEachscore() * qjci.getScoreper() / 100)
 + "分)<p>" + qjci.getContent() + "</p> ");
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
 if (qj.getStuAnswers()[0].equals("yes"))
 out
 .println(" 正确");

 if (qj.getStuAnswers()[0].equals("no"))
 out
 .println(" 错误");
 out.println("</div>");
 }

 private void writeSelect(JspWriter out, Question qj, int blockid, int _qsort)
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
 out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
 + "].stuAnswers\" type=\"radio\" value=\"" + i
 + "\"  checked='checked'/>" + ""
 + ExamPaperUtil.getABC(i));
 else
 out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
 + "].stuAnswers\" type=\"radio\" value=\"" + i
 + "\" />" + "" + ExamPaperUtil.getABC(i));
 } else {
 if (checkSAnswer(qj, i))
 out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
 + "].stuAnswers\" type=\"checkbox\" value=\"" + i
 + "\" checked='checked'/>" + ""
 + ExamPaperUtil.getABC(i));
 else
 out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
 + "].stuAnswers\" type=\"checkbox\" value=\"" + i
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

 private void writeBlank(JspWriter out, Question qj, int blockid, int _qsort)
 throws Exception {
 out.println("<div class='answer'> <b>填写答案</b>：");

 for (int i = 0; i < qj.getAnswers().length; i++) {
 //		if (null != qj.getStuAnswers() && null != qj.getStuAnswers()[i])
 //			out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
 //					+ "</font>&nbsp;" + " <input name=\"questions["
 //					+ _qsort + "].stuAnswers\" type=\"text\"  value=\""
 //					+ qj.getStuAnswers()[i] + "\"/>  ");
 //		else
 out.println(" <font class=\"xuxian\">空白答案" + (i + 1)
 + "</font>&nbsp;" + " <input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions["
 + _qsort
 + "].stuAnswers\" type=\"text\"  value=\"\"/>  ");

 }
 out.println("</div>");
 }

 private void writeEssay(JspWriter out, Question qj, int blockid, int _qsort)
 throws Exception {
 out.println(" <div class='answer'><b>填写答案</b>：");
 if (null != qj.getStuAnswers())
 out.println("<textarea cols=30 rows=5 name=\"" + blockid + "_"
 + qj.getId() + "_" + qj.getSortid() + "\" >"
 + qj.getStuAnswers()[0] + "</textarea></div>");
 else
 out.println("<textarea cols=30 rows=5 name=\"" + blockid + "_"
 + qj.getId() + "_" + qj.getSortid()
 + "\" ></textarea></div>");

 }

 private void writeDazi(JspWriter out, Question qj, int blockid, int _qsort)
 throws Exception {
 out.println("<div class='startNewWindow'> <a href='' target='_blank' onclick=\"openNewWindowQ(this, "+blockid+","+qj.getId()+")\">开始答题"+"</a>");
 out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
 + "].stuAnswers\" type=\"hidden\" value=\"\"/></div>");
 }

 private void writeMail(JspWriter out, Question qj, int blockid, int _qsort)
 throws Exception {
 out.println("<div class='startNewWindow'> <a href='' target='_blank' onclick=\"openNewWindowQ(this, "+blockid+","+qj.getId()+")\">开始答题"+"</a>");
 out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
 + "].stuAnswers\" type=\"hidden\" value=\"\" />");
 out.println(" </div>");
 }

 private void writeSearch(JspWriter out, Question qj, int blockid, int _qsort)
 throws Exception {
 out.println("<div class='startNewWindow'> <a href='' target='_blank' onclick=\"openNewWindowQ(this, "+blockid+","+qj.getId()+")\">开始答题"+"</a>");
 out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
 + "].stuAnswers\" type=\"hidden\" value=\"\" /></div>");

 }

 private void writeOffice(JspWriter out, Question qj, int blockid, int _qsort)
 throws Exception {
 out.println("<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" name=\"questions[" + _qsort
 + "].stuAnswers\" type=\"hidden\" value=\"\" />");
 out
 .println("<div>下载： <a target='_blank' href='download_office_stuff.action?filename="
 + qj.getSubject()
 + "'>模板文档</a><br/>"
 + "上传： 我的作答<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" type='file' id='office_"
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
 + blockid
 + "_"
 + qj.getId()
 + "\" name=\""
 + blockid
 + "_"
 + qj.getId()
 + "_"
 + qj.getSortid()
 + "\" size=\"45\" name=\"question.answers\" />	<input onclick=\"q_yd("+blockid+","+qj.getId()+")\" type='button' value='上傳' onclick=\"upload_offices( "
 + blockid + "," + qj.getId() + " )\"/></div>");

 }
 */
