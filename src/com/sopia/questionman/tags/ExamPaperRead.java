package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;

import com.jspsmart.upload.Request;
import com.sopia.ElConstants;
import com.sopia.common.ExamPaperUtil;
import com.sopia.common.StringUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.getFloat;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.ExamPaper;
import com.sopia.questionman.entities.ExamPaperBlock;
import com.sopia.questionman.entities.Question;
import com.sopia.studyman.dao.StudyQuizDao;
import com.sopia.studyman.entities.MyExamPaper;
import com.sopia.BaseAction;
 
public class ExamPaperRead extends TagSupport {
	private static final Log logger = LogFactory.getLog(ExamPaperRead.class);
	private boolean readonly;
	int age = 0;
	// private int essayBlockId;
	// private int officeBlockId;
	// int e = 0;
	// int o = 0;
	//	
	// private float yesOrNoScore;
	// private float select1Score;//单选
	// private float select2Score;//多选
	// private float blankScore;
	// private float essayScore;
	// private float daziScore;
	// private float mailScore;
	// private float searchScore;
	// private float officeScore;
	//	
	// private int yesOrNoCount;
	// private int select1Count;//单选
	// private int select2Count;//多选
	// private int blankCount;
	// private int essayCount;
	// private int daziCount;
	// private int mailCount;
	// private int searchCount;
	// private int officeCount;
	//	
	// private int yesOrNoBlockId;
	// private int select1BlockId;//单选
	// private int select2BlockId;//多选
	// private int blankBlockId;
	// private int daziBlockId;
	// private int mailBlockId;
	// private int searchBlockId;

	private float tempEachscore;// 用于临时存储题目的分值
	private int tempBlockId;// 用于每道题的所在版块id
	private int tempQuesSortid;// 题目的序号，用于提示功能
	private int tempQuesClSortid;// 材料题的小题序号
	private String tempBlockName;// 用于提示功能

	// 材料题处理
	private int clCount;// 材料题数量
	private int clBlockId;// 材料题版块
	
	private int isLeader;//判断阅卷人员类型（系统管理员或阅卷组长为1，普通管理员为0）
	private int papersTeacherCount;//针对每一场考试，评卷老师的个数
	private StudyQuizDao studyQuizDao;
	private MyExamPaper myPaper;
 
	
	public int getClCount() {
		return clCount;
	}

	public void setClCount(int clCount) {
		this.clCount = clCount;
	}

	public int getClBlockId() {
		return clBlockId;
	}

	public void setClBlockId(int clBlockId) {
		this.clBlockId = clBlockId;
	}

	
	// public int getYesOrNoBlockId() {
	// return yesOrNoBlockId;
	// }
	//
	// public void setYesOrNoBlockId(int yesOrNoBlockId) {
	// this.yesOrNoBlockId = yesOrNoBlockId;
	// }
	//
	// public int getSelect1BlockId() {
	// return select1BlockId;
	// }
	//
	// public void setSelect1BlockId(int select1BlockId) {
	// this.select1BlockId = select1BlockId;
	// }
	//
	// public int getSelect2BlockId() {
	// return select2BlockId;
	// }
	//
	// public void setSelect2BlockId(int select2BlockId) {
	// this.select2BlockId = select2BlockId;
	// }
	//
	// public int getBlankBlockId() {
	// return blankBlockId;
	// }
	//
	// public void setBlankBlockId(int blankBlockId) {
	// this.blankBlockId = blankBlockId;
	// }
	//
	// public int getDaziBlockId() {
	// return daziBlockId;
	// }
	//
	// public void setDaziBlockId(int daziBlockId) {
	// this.daziBlockId = daziBlockId;
	// }
	//
	// public int getMailBlockId() {
	// return mailBlockId;
	// }
	//
	// public void setMailBlockId(int mailBlockId) {
	// this.mailBlockId = mailBlockId;
	// }
	//
	// public int getSearchBlockId() {
	// return searchBlockId;
	// }
	//
	// public void setSearchBlockId(int searchBlockId) {
	// this.searchBlockId = searchBlockId;
	// }
	//
	// public int getYesOrNoCount() {
	// return yesOrNoCount;
	// }
	//
	// public void setYesOrNoCount(int yesOrNoCount) {
	// this.yesOrNoCount = yesOrNoCount;
	// }
	//
	// public int getSelect1Count() {
	// return select1Count;
	// }
	//
	// public void setSelect1Count(int select1Count) {
	// this.select1Count = select1Count;
	// }
	//
	// public int getSelect2Count() {
	// return select2Count;
	// }
	//
	// public void setSelect2Count(int select2Count) {
	// this.select2Count = select2Count;
	// }
	//
	// public int getBlankCount() {
	// return blankCount;
	// }
	//
	// public void setBlankCount(int blankCount) {
	// this.blankCount = blankCount;
	// }
	//
	// public int getEssayCount() {
	// return essayCount;
	// }
	//
	// public void setEssayCount(int essayCount) {
	// this.essayCount = essayCount;
	// }
	//
	// public int getDaziCount() {
	// return daziCount;
	// }
	//
	// public void setDaziCount(int daziCount) {
	// this.daziCount = daziCount;
	// }
	//
	// public int getMailCount() {
	// return mailCount;
	// }
	//
	// public void setMailCount(int mailCount) {
	// this.mailCount = mailCount;
	// }
	//
	// public int getSearchCount() {
	// return searchCount;
	// }
	//
	// public void setSearchCount(int searchCount) {
	// this.searchCount = searchCount;
	// }
	//
	// public int getOfficeCount() {
	// return officeCount;
	// }
	//
	// public void setOfficeCount(int officeCount) {
	// this.officeCount = officeCount;
	// }
	//
	// public float getYesOrNoScore() {
	// return yesOrNoScore;
	// }
	//
	// public void setYesOrNoScore(float yesOrNoScore) {
	// this.yesOrNoScore = yesOrNoScore;
	// }
	//
	// public float getSelect1Score() {
	// return select1Score;
	// }
	//
	// public void setSelect1Score(float select1Score) {
	// this.select1Score = select1Score;
	// }
	//
	// public float getSelect2Score() {
	// return select2Score;
	// }
	//
	// public void setSelect2Score(float select2Score) {
	// this.select2Score = select2Score;
	// }
	//
	// public float getBlankScore() {
	// return blankScore;
	// }
	//
	// public void setBlankScore(float blankScore) {
	// this.blankScore = blankScore;
	// }
	//
	// public float getEssayScore() {
	// return essayScore;
	// }
	//
	// public void setEssayScore(float essayScore) {
	// this.essayScore = essayScore;
	// }
	//
	// public float getDaziScore() {
	// return daziScore;
	// }
	//
	// public void setDaziScore(float daziScore) {
	// this.daziScore = daziScore;
	// }
	//
	// public float getMailScore() {
	// return mailScore;
	// }
	//
	// public void setMailScore(float mailScore) {
	// this.mailScore = mailScore;
	// }
	//
	// public float getSearchScore() {
	// return searchScore;
	// }
	//
	// public void setSearchScore(float searchScore) {
	// this.searchScore = searchScore;
	// }
	//
	// public float getOfficeScore() {
	// return officeScore;
	// }
	//
	// public void setOfficeScore(float officeScore) {
	// this.officeScore = officeScore;
	// }
	//
	// public int getE() {
	// return e;
	// }
	//
	// public void setE(int e) {
	// this.e = e;
	// }
	//
	// public int getO() {
	// return o;
	// }
	//
	// public void setO(int o) {
	// this.o = o;
	// }
	//
	// public int getEssayBlockId() {
	// return essayBlockId;
	// }
	//
	// public void setEssayBlockId(int essayBlockId) {
	// this.essayBlockId = essayBlockId;
	// }
	//
	// public int getOfficeBlockId() {
	// return officeBlockId;
	// }
	//
	// public void setOfficeBlockId(int officeBlockId) {
	// this.officeBlockId = officeBlockId;
	// }

	public boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			// e=0;
			// o=0;
			// this.setYesOrNoCount(0);
			// this.select1Count=0;
			// this.select2Count=0;
			// this.blankCount=0;
			// this.daziCount=0;
			// this.mailCount=0;
			// this.searchCount=0;
			this.clCount = 0;
			this.tempBlockId = 0;
			this.tempEachscore = 0f;
			this.tempQuesSortid = 0;
			this.tempQuesClSortid = 0;
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ExamPaper ep = (ExamPaper) request.getAttribute("examPaper");
			ELUser elUser = (ELUser) request.getAttribute("elUser");
			myPaper = (MyExamPaper) request.getAttribute("myExamPaper");
			this.isLeader=elUser.getIsLeader();
		 
			
			
			age = ep.getUserage();
			List<ExamPaperBlock> epbs = ep.getEpBlocks();
			writeEPBs(out, epbs);
		} catch (Exception ex) {
			logger.error("阅卷显示错误",ex);
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
			if (epbj.getDescription() != null) {
				out.println(epbj.getDescription());
			}
			out.println("</div><br>");
			writeEPQs(out, epbj.getQuestions(), epbj);
			out.println("</div><br>");
		}

		// 提交数据
		// 把blockid传上去,用于设置问答题块的总分
		// out.println("<input type='hidden' name='essayBlockId'
		// value='"+getEssayBlockId()+"' />");
		// //把blockid传上去,用于设置问答题块的总分
		// out.println("<input type='hidden' name='officeBlockId'
		// value='"+getOfficeBlockId()+"' />");
		// out.println("<input type='hidden' id='Essayforid'
		// value='"+getE()+"'/>");
		// out.println("<input type='hidden' id='Officeforid'
		// value='"+getO()+"'/>");
		// out.println("<input type='hidden' id='isEssay'
		// value='"+getEssayScore()+"'/>");
		// out.println("<input type='hidden' id='isOffice'
		// value='"+getOfficeScore()+"'/>");
		// out.println("<input type='hidden' id='yesOrNoforid'
		// value='"+this.getYesOrNoCount()+"'/>");
		// out.println("<input type='hidden' id='select1forid'
		// value='"+this.getSelect1Count()+"'/>");
		// out.println("<input type='hidden' id='select2forid'
		// value='"+this.getSelect2Count()+"'/>");
		// out.println("<input type='hidden' id='blankforid'
		// value='"+this.getBlankCount()+"'/>");
		// out.println("<input type='hidden' id='daziforid'
		// value='"+this.getDaziCount()+"'/>");
		// out.println("<input type='hidden' id='mailforid'
		// value='"+this.getMailCount()+"'/>");
		// out.println("<input type='hidden' id='searchforid'
		// value='"+this.getSearchCount()+"'/>");
		//		
		// out.println("<input type='hidden' id='isyesOrNo'
		// value='"+getYesOrNoScore()+"'/>");
		// out.println("<input type='hidden' id='isselect1'
		// value='"+getSelect1Score()+"'/>");
		// out.println("<input type='hidden' id='isselect2'
		// value='"+getSelect2Score()+"'/>");
		// out.println("<input type='hidden' id='isblank'
		// value='"+getBlankScore()+"'/>");
		// out.println("<input type='hidden' id='isdazi'
		// value='"+getDaziScore()+"'/>");
		// out.println("<input type='hidden' id='ismail'
		// value='"+getMailScore()+"'/>");
		// out.println("<input type='hidden' id='issearch'
		// value='"+getSearchScore()+"'/>");
		//		
		// out.println("<input type='hidden' name='yesOrNoBlockId'
		// value='"+getYesOrNoBlockId()+"' />");
		// out.println("<input type='hidden' name='select1BlockId'
		// value='"+getSelect1BlockId()+"' />");
		// out.println("<input type='hidden' name='select2BlockId'
		// value='"+getSelect2BlockId()+"' />");
		// out.println("<input type='hidden' name='blankBlockId'
		// value='"+getBlankBlockId()+"' />");
		// out.println("<input type='hidden' name='daziBlockId'
		// value='"+getDaziBlockId()+"' />");
		// out.println("<input type='hidden' name='mailBlockId'
		// value='"+getMailBlockId()+"' />");
		// out.println("<input type='hidden' name='searchBlockId'
		// value='"+getSearchBlockId()+"' />");
		//		
		// out.println("<input type='hidden' id='clforid'
		// value='"+this.getClCount()+"'/>");
		// out.println("<input type='hidden' name='clBlockId'
		// value='"+getClBlockId()+"' />");
	}

	private void writeEPQs(JspWriter out, List<Question> qs, ExamPaperBlock epb)
			throws Exception {

		for (int j = 0; j < qs.size(); j++) {
			Question qj = qs.get(j);
			out.println("<div class='question'>");
			out.println("<b>第" + qj.getSortid() + "题</b>(" + epb.getEachscore()
					+ "分)<p>" + qj.getTitle() + "</p> ");
			// 设置值
			this.setTempEachscore(epb.getEachscore());
			this.setTempBlockId(epb.getId());
			this.setTempQuesSortid(qj.getSortid());
			this.setTempBlockName(epb.getTitle());
			if (qj.getQtype() == 7) {// 材料题
				List<Question> qjc = qj.getChilds();
				for (int i = 0; i < qjc.size(); i++) {
					out.println("<div class='question1'>");
					Question qjci = qjc.get(i);
					out.println("<b>第" + qjci.getSortid() + "题</b>("
							+ (epb.getEachscore() * qjci.getScoreper() / 100)
							+ "分)<p>" + qjci.getContent_() + "</p> ");
					// 设置值
					this.setTempEachscore(epb.getEachscore()
							* qjci.getScoreper() / 100);
					this.setTempQuesClSortid(qjci.getSortid());
					// writeEPQsComm(out, qjci, epb.getId());
					writeEPQsComm_cl(out, qjci, epb.getId());
					out.println("</div><br>");
					out.println("<input type='hidden' id='cltEachScore_"
							+ getClCount() + "_" + qjci.getSortid()
							+ "' value='"
							+ (epb.getEachscore() * qjci.getScoreper() / 100)
							+ "' />");
				}
				// out.println("<input type='hidden' name='cltIds'
				// value='"+qj.getId()+"' />");
				// this.clCount++;
				// this.setClBlockId(epb.getId());
				// this.setSelect1Score(epb.getEachscore());
			} else {
				writeEPQsComm(out, qj, epb.getId());
			}
			// hwc
			// if(qj.getQtype() == 6){
			// this.e=this.e+1;
			// out.println("<input type='hidden' id='Esubject"+e+"'
			// value='"+qj.getSortid()+"'/>");
			// this.essayBlockId=epb.getId();
			// this.setEssayScore(epb.getEachscore());
			// }
			// if(qj.getQtype() == 11){
			// this.o=this.o+1;
			// //out.println("<input type='hidden' id='Osubject"+o+"'
			// value='"+epb.getSortid()+"'/><input type='hidden' id='isOffice'
			// value='"+epb.getEachscore()+"'/>");
			// out.println("<input type='hidden' id='Osubject"+o+"'
			// value='"+epb.getSortid()+"'/>");
			// this.officeBlockId=epb.getId();
			// this.setOfficeScore(epb.getEachscore());
			// }
			//			
			// if(qj.getQtype() == 1){//
			// this.yesOrNoCount++;
			// this.setYesOrNoBlockId(epb.getId());
			// //out.println("<input type='hidden'
			// id='yesOrNoScore"+yesOrNoCount+"' value='"+qj.getSortid()+"'/>");
			// this.setYesOrNoScore(epb.getEachscore());
			// }
			// if(qj.getQtype() == 2){
			// this.select1Count++;
			// this.setSelect1BlockId(epb.getId());
			// this.setSelect1Score(epb.getEachscore());
			// }
			// if(qj.getQtype() == 4){
			// this.select2Count++;
			// this.setSelect2BlockId(epb.getId());
			// this.setSelect2Score(epb.getEachscore());
			// }
			// if(qj.getQtype() == 5){
			// this.blankCount++;
			// this.setBlankBlockId(epb.getId());
			// this.setBlankScore(epb.getEachscore());
			// }
			// if(qj.getQtype() == 8){
			// this.daziCount++;
			// this.setDaziBlockId(epb.getId());
			// this.setDaziScore(epb.getEachscore());
			// }
			// if(qj.getQtype() == 9){
			// this.mailCount++;
			// this.setMailBlockId(epb.getId());
			// this.setMailScore(epb.getEachscore());
			// }
			// if(qj.getQtype() == 10){
			// this.searchCount++;
			// this.setSearchBlockId(epb.getId());
			// this.setSearchScore(epb.getEachscore());
			// }
			out.println("</div><br>");
		}
		// out.println("<input type='hidden' id='Essayforid' value='"+e+"'/>");
		// out.println("<input type='hidden' id='Officeforid' value='"+o+"'/>");

	}

	private void writeEPQsComm(JspWriter out, Question qj, int blockid)
			throws Exception {
		if (qj.getQtype() == 1) {
			writeYesOrNo(out, qj, blockid);
		}
		// if (qj.getQtype() == 2 || qj.getQtype() == 3 || qj.getQtype() == 4) {
		// //writeSelect(out, qj, blockid);
		// }
		if (qj.getQtype() == 2) {
			writeSelect1(out, qj, blockid);
		}
		if (qj.getQtype() == 4) {
			writeSelect2(out, qj, blockid);
		}
		if (qj.getQtype() == 5) {
			writeBlank(out, qj, blockid);
		}
		if (qj.getQtype() == 6) {
			writeEssay(out, qj, blockid);
		}
		int _qsort = 0;
		if (qj.getQtype() == 8) {
			writeDz(out, qj, blockid, _qsort);
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

	private void writeHiddenInput(JspWriter out, Question qj) throws Exception {
		out.println("<input type='hidden' name='qids' value='" + qj.getId()
				+ "' />");
		out.println("<input type='hidden' name='qBlockids' value='"
				+ getTempBlockId() + "' />");
		out.println("<input type='hidden' id='" + getTempBlockId() + "_"
				+ getTempQuesSortid() + "_score' value='"
				+ this.getTempEachscore() + "' />");
		out.println("<input type='hidden' id='" + getTempBlockId() + "_"
				+ getTempQuesSortid() + "_mess' value='"
				+ this.getTempBlockName() + "版块：第" + this.getTempQuesSortid()
				+ "题' />");// ,打分成绩不能超过该题目分数!
	}

	private void writeHiddenInputCl(JspWriter out, Question qj)
			throws Exception {
		out.println("<input type='hidden' name='qids' value='" + qj.getId()
				+ "' />");
		out.println("<input type='hidden' name='qBlockids' value='"
				+ getTempBlockId() + "' />");
		out.println("<input type='hidden' id='" + getTempBlockId() + "_"
				+ getTempQuesSortid() + "_" + getTempQuesClSortid()
				+ "_score' value='" + this.getTempEachscore() + "' />");
		out.println("<input type='hidden' id='" + getTempBlockId() + "_"
				+ getTempQuesSortid() + "_" + getTempQuesClSortid()
				+ "_mess' value='" + this.getTempBlockName() + "版块：第"
				+ this.getTempQuesSortid() + "题,第" + this.getTempQuesClSortid()
				+ "小题' />");// ,打分成绩不能超过该题目分数!
	}
	/**
	 * 输出答案解析
	 * @param out
	 * @param qj
	 * @throws Exception
	 */
	private void printQexplain(JspWriter out, Question qj) throws Exception{
		if(qj.getQexplain()!=null&&!"".equals(qj.getQexplain())){
			out.print("<div>");
			out.println("<b>答案解析：</b>"+ qj.getQexplain());
			out.print("</div>");
		}
	}

	//判断题
	private void writeYesOrNo(JspWriter out, Question qj, int blockid)
			throws Exception {
		String tempStr="";
		//获取当前用户名：
		if (this.isLeader ==0) { //普通阅卷人
			tempStr=" readonly=true";
		} 
		
		
		// out.println("<p>" + qj.getContent()+ "</p>");
		out.println("<div class='answer'>"
				+ "【系统打分:"
				+ qj.getMyScore()
				// + "】&nbsp;&nbsp;&nbsp;最后得分：<input"+( readonly ?"
				// readonly='readonly'":"")+" type='text' name='thescore'
				// size='3' value='"
				// + qj.getMyScore() + "'/>");
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thescore' size='3' value='" + qj.getMyScore()  
				+ "' "+tempStr+"/>");
		// + "】&nbsp;&nbsp;&nbsp;最后得分："+qj.getMyScore()+"");
		this.writeHiddenInput(out, qj);
		out.println("<b><br>我的答案</b>：");
		if (qj.getStuAnswers() != null)
			if (qj.getStuAnswers()[0].equals("yes"))
				out.println("正确");
			else if (qj.getStuAnswers()[0].equals("no"))
				out.println("不正确");
			else if (!"".equals(qj.getStuAnswers()[0].trim()))
				out.println("该题未回答");
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (qj.getAnswers()[0].equals("yes"))
			out.println("正确");
		else
			out.println("不正确");
		out.println("</div>");
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' "+tempStr+"/>");
		out.println("</div>");

	}

	//选择题
	private void writeSelect1(JspWriter out, Question qj, int blockid)
			throws Exception {
		// out .println("<p>" + qj.getContent()+"</p>");
		out.println("<p>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
					+ qj.getOptions()[i] + "<br>");
		}
		out.print("</p>");
		
		String tempStr="";
		//获取当前用户名：
		if (this.isLeader ==0) { //普通阅卷人
			tempStr=" readonly=true";
		} 

		out.println("<div class='answer'>" + "【系统打分:" + qj.getMyScore()
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input id='" + getTempBlockId()
				+ "_" + getTempQuesSortid()
				+ "' type='text' name='thescore' size='3' value='"				
				+ qj.getMyScore() + "'"+tempStr+" />" +
				
				// "<input type='hidden' name='qids' value='"+qj.getId()+"' />"
				// +
				// "<input type='hidden'
				// id='"+getTempBlockId()+"_"+getTempQuesSortid()+"_score'
				// value='"+this.getTempEachscore()+"' />" +
				// "<input type='hidden'
				// id='"+getTempBlockId()+"_"+getTempQuesSortid()+"_mess'
				// value='"+this.getTempBlockName()+"版块：第"+this.getTempQuesSortid()+"题,打分成绩不能超过该题目分数!'
				// />" +
				"");
 
	    
		this.writeHiddenInput(out, qj);
		// + "】&nbsp;&nbsp;&nbsp;最后得分："+qj.getMyScore()+"");
		out.println("<b><br>我的答案</b>：");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				if (!"".equals(qj.getStuAnswers()[i].trim()))
					out
							.println(""
									+ ExamPaperUtil
											.getABC(qj.getStuAnswers()[i])
									+ "、");
			}
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (qj.getAnswers() != null) {
			for (int i = 0; i < qj.getAnswers().length; i++) {
				out
						.println("" + ExamPaperUtil.getABC(qj.getAnswers()[i])
								+ "、");
			}
		}
		out.println("</div>");
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' "+tempStr+"/>");
		out.println("</div>");
	}

	private void writeSelect2(JspWriter out, Question qj, int blockid)
			throws Exception {
		
		String tempStr="";
		//获取当前用户名：
		if (this.isLeader ==0) { //普通阅卷人
			tempStr=" readonly=true";
		} 
		
		// out .println("<p>" + qj.getContent()+"</p>");
		out.println("<p>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
					+ qj.getOptions()[i] + "<br>");
		}
		out.print("</p>");
		out.println("<div class='answer'>" + "【系统打分:" + qj.getMyScore()
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input id='" + getTempBlockId()
				+ "_" + getTempQuesSortid()
				+ "' type='text' name='thescore' size='3' value='"
				+ qj.getMyScore() + "' "+tempStr+"/>");
		// + "】&nbsp;&nbsp;&nbsp;最后得分："+qj.getMyScore()+"");
		this.writeHiddenInput(out, qj);
		out.println("<b><br>我的答案</b>：");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				if (!"".equals(qj.getStuAnswers()[i].trim()))
					out
							.println(""
									+ ExamPaperUtil
											.getABC(qj.getStuAnswers()[i])
									+ "、");
			}
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (qj.getAnswers() != null) {
			for (int i = 0; i < qj.getAnswers().length; i++) {
				out
						.println("" + ExamPaperUtil.getABC(qj.getAnswers()[i])
								+ "、");
			}
		}
		out.println("</div>");
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' "+tempStr+"/>");
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
		
		String tempStr="";
		//获取当前用户名：
		if (this.isLeader ==0) { //普通阅卷人
			tempStr=" readonly=true";
		} 
		
		out.println("<div class='answer'> " + "【系统打分:" + qj.getMyScore()
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input id='" + getTempBlockId()
				+ "_" + getTempQuesSortid()
				+ "' type='text' name='thescore' size='3' value='"
				+ qj.getMyScore() + "' "+tempStr+"/>");
		// + "】&nbsp;&nbsp;&nbsp;最后得分："+qj.getMyScore()+"");
		this.writeHiddenInput(out, qj);
		out.println("<b><br>我的答案</b>：");
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
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' "+tempStr+"/>");
		out.println("</div>");
	}


	
	//问答题 
	private void writeEssay(JspWriter out, Question qj, int blockid)
			throws Exception {
			out.println(" <div class='answer'>"
						+ "【系统打分:"
						+ qj.getMyScore()
						// + "】&nbsp;&nbsp;&nbsp;最后得分：<input type='text'
						// name='thescore' size='3' id =
						// '"+qj.getSortid()+"Essay' value='"
						// + qj.getMyScore() + "'/><br><b>我的回答</b>");Esubject
						// + "】&nbsp;&nbsp;&nbsp;最后得分：<input type='text'
						// name='thescoreEssay' size='3' id =
						// '"+qj.getSortid()+"Essay' value='"
						// + qj.getMyScore() + "'/><input type='hidden'
						// name='qidsEssay' value='"+qj.getId()+"'
						// /><br><b>我的回答</b>");
						+ "】&nbsp;&nbsp;&nbsp;最后得分：<input type='text' name='thescore' size='3' id = '"
						+ getTempBlockId() + "_" + getTempQuesSortid()
						+ "' value='" + qj.getMyScore() + "'/> ");
						
						if (this.isLeader == 1) {//阅卷组长和超级管理员
							String scores="";
							out.print( "<table width=300 border=1><tr>");
							for (int i = 0; i < qj.getMultiUserPapers().size(); i++) {
								out.print("<td>"+qj.getMultiUserPapers().get(i).getElUser().getRealname()+"</td>");
							}
							out.print("</tr><tr>");
							for (int i = 0; i < qj.getMultiUserPapers().size(); i++) {
								out.print("<td>"+qj.getMultiUserPapers().get(i).getScore()+"</td>");
								if(i==0){
									scores+=qj.getMultiUserPapers().get(i).getScore();
								}else{
									scores+=","+qj.getMultiUserPapers().get(i).getScore();
								}
								
							}
							out.print("</tr></table><input type='button' id='txtAVg' name='txtAVg'  onclick=giveScore('"+getTempBlockId() + "_" + getTempQuesSortid()+"','"+scores+"','avg');  value='取平均分' />"
								+"<input type='button' id='txtHigh' name='txtHigh'  onclick=giveScore('"+getTempBlockId() + "_" + getTempQuesSortid()+"','"+scores+"','high');   value='取最高分'/>"
								+"<input type='button' id='txtAVg2' name='txtAVg2'  onclick=giveScore('"+getTempBlockId() + "_" + getTempQuesSortid()+"','"+scores+"','avg2');   value='去掉最高最低分取平均分'/>");
						}						
						 
					   out.print("<br><b>我的回答</b>");
		
//		for (int i = 0; i < qj.getMultiUserPapers().size(); i++) {
//			 System.out.println("普通阅卷人姓名："+qj.getMultiUserPapers().get(i).getElUser().getRealname()+"\t 普通阅卷人评分："+qj.getMultiUserPapers().get(i).getScore());
//		}		
		
		this.writeHiddenInput(out, qj);
		if (null != qj.getStuAnswers()) {
			out.println(qj.getStuAnswers()[0].trim() + "</div>");
		} else {
			out.println("</div>");
		}
		// out.println(" <div style='color:green'><b>正确答案</b>：");
		// if (null != qj.getAnswers()){
		// for (int i = 0; i < qj.getAnswers().length; i++) {
		// out.print("答案关键字" + (i + 1) + ":" + qj.getAnswers()[i]
		// + "<br>");
		// }
		// }
		// out.println("</div>");
		out.println(" <div class='answer'><b>正确答案</b>：");
		if (qj.getAnswers() != null && qj.getAnswers().length >= 1) {
			out.println(qj.getAnswers()[0] + "</div>");
			out.println("<div class='answer'>"
					+"批语：<input type='text' id='"
					+ getTempBlockId() + "_" + getTempQuesSortid()
					+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "'/>");
		} else {
			out.println("</div>");
			out.println("<div class='answer'>"
					+"批语：<input type='text' id='"
					+ getTempBlockId() + "_" + getTempQuesSortid()
					+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' />");
		}
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		
	}

	private void writeDazi(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		String tempStr="";
		if (this.isLeader ==0) { //普通阅卷人
			tempStr=" readonly=true";
		}
		out.println("【系统打分:" + qj.getMyScore() + "分】最后得分：<input id='" + getTempBlockId() + "_"
				+ getTempQuesSortid()
				+ "' type='text' name='thescore' size='3' value='"
				+ qj.getMyScore() + "' "+tempStr+" /><br/>");
		this.writeHiddenInput(out, qj);
		out.println("<div class='startNewWindow' style='color:blue;'><b>评分规则</b>");
		out.print("时长："+qj.getRules()[2]+"分钟 <br/>");
		
		for (int i = 0; i < qj.getDazirule().length; i++) {
			out.print("<strong>年龄段:</strong>"+(i+1)+"：");
			out.print(qj.getDazirule()[i][0]+"到"+qj.getDazirule()[i][1]+ 
					"及格速度："+qj.getDazirule()[i][2]+"优秀速度："+qj.getDazirule()[i][3]+"满分速度："+qj.getDazirule()[i][4]);
			out.print("<br/>");
		}
		out.println("</div>");
		out.println("<div class='startNewWindow' style='color:green;'><b>我的答案</b>" + " ");
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
		} else
			out.println("该题未作答");
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' "+tempStr+"/>");
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

	private void writeDazi__(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='startNewWindow'> 我的答案" + " ");
		if (null != qj.getStuAnswers() && qj.getStuAnswers().length > 3) {
			out.println("已打时间：" + qj.getStuAnswers()[0] + "、");
			out.println("打对字数：" + qj.getStuAnswers()[1] + "、");
			// out.println("速度：" + qj.getStuAnswers()[2] + "<br />");
			String neirong = qj.getStuAnswers()[3];
			if (null != neirong) {
				neirong = neirong.replace("-=wys=-", "<br/>");
			} else
				neirong = "";
			out.println("<br/>我打的内容：" + neirong);
		} else
			out.println("该题未作答");
		out.println(" </div>");
		/*
		 * out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() +
		 * "(" + qj.getMystatusStr() + ") 最后得分：<input "+( readonly ?"
		 * readonly='readonly'":"")+" type='text' name='thescore' size='3'
		 * value='" + qj.getMyScore() + "'/><br>");
		 */
		out.println("<div class='answer'>【<b>系统打分</b>：" + qj.getMyScore()
				+ "】 最后得分：<input id='" + getTempBlockId() + "_"
				+ getTempQuesSortid()
				+ "' type='text' name='thescore' size='3' value='"
				+ qj.getMyScore() + "'/><br>");
		// out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + ")
		// 最后得分："+qj.getMyScore()+"<br>");
		this.writeHiddenInput(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' />");
		out.println("</div>");
	}

	private void writeMail(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<div class='startNewWindow' style='color:blue;'><b>评分规则</b>");
		out.print("发 给："+qj.getRules()[0]+"分、");
		out.print("抄 送："+qj.getRules()[1]+"分、");
		out.print("密 送："+qj.getRules()[2]+"分、");
		out.print("主 题："+qj.getRules()[3]+"分、");
		out.print("附 件："+qj.getRules()[4]+"分、");
		out.print("正 文："+qj.getRules()[5]+"分、");
		out.println("</div>");
		out.println("<div class='startNewWindow'> 我的答案" + "： ");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				out.println(qj.getStuAnswers()[i] + "、");
			}
		out.println(" </div>");
		/*
		 * out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() +
		 * "(" + qj.getMystatusStr() + ") 最后得分：<input type='text'"+( readonly ?"
		 * readonly='readonly'":"")+" name='thescore' size='3' value='" +
		 * qj.getMyScore() + "'/><br>");
		 */
		String tempStr="";
		if (this.isLeader ==0) { //普通阅卷人
			tempStr=" readonly=true";
		}
		out.println("<div class='answer'>【<b>系统打分</b>：" + qj.getMyScore()
				+ "】 最后得分：<input type='text' id='" + getTempBlockId() + "_"
				+ getTempQuesSortid() + "' name='thescore' size='3' value='"
				+ qj.getMyScore() + "' "+tempStr+" /><br>");
		// out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() +")
		// 最后得分："+qj.getMyScore()+"<br>");
		this.writeHiddenInput(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' "+tempStr+"/>");
		out.println("</div>");
	}

	private void writeSearch(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		String tempStr="";
		//获取当前用户名：
		if (this.isLeader ==0) { //普通阅卷人
			tempStr=" readonly=true";
		} 
		
		out.println("<div class='startNewWindow'> 我的答案" + "： ");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				out.println(qj.getStuAnswers()[i] + "、");
			}
		out.println(" </div>");
		/*
		 * out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() +
		 * "(" + qj.getMystatusStr() + ") 最后得分：<input type='text'"+( readonly ?"
		 * readonly='readonly'":"")+" name='thescore' size='3' value='" +
		 * qj.getMyScore() + "'/><br>");
		 */
		out.println("<div class='answer'>【<b>系统打分</b>：" + qj.getMyScore()
				+ "】 最后得分：<input type='text' id='" + getTempBlockId() + "_"
				+ getTempQuesSortid() + "' name='thescore' size='3' value='"
				+ qj.getMyScore() + "' "+tempStr+"/><br>");
		// out.println("<div class='answer'><b>我的得分</b>：" + qj.getMyScore() + ")
		// 最后得分："+qj.getMyScore()+"<br>");
		this.writeHiddenInput(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' "+tempStr+"/>");
		out.println("</div>");

	}

	private void writeOffice(JspWriter out, Question qj, int blockid, int _qsort)
			throws Exception {
		out.println("<input onclick=\"q_yd(" + blockid + "," + qj.getId()
				+ ")\" name=\"questions[" + _qsort
				+ "].stuAnswers\" type=\"hidden\" value=\"\" />");
		out
				.println("<div>下载： <a target='_blank' href='"
						+ SystemConfOp.getStuffUrl()
						+ "download.jsp?filename="
						+ qj.getSubject()
						+ "'>模板文档</a><br/>"
						+ " 我的答案"
						+ (qj.getStuAnswers() != null ? " <a target='_blank' href='download.jsp?filename=elstuffs/quizanswer/"
								+ qj.getStuAnswers()[0] + "'>下载</a>"
								// : "") + " </div> 最后得分：<input type='text'
								// name='thescore' size='3' id='Office' value='"
								// + qj.getMyScore() + "'/><br>");
								// : "") + " </div> 最后得分：<input type='text'
								// name='thescoreOffice' size='3' id='Office'
								// value='"
								// + qj.getMyScore() + "'/><input type='hidden'
								// name='qidsOffice' value='"+qj.getId()+"'
								// /><br>");
								: "")
						+ " </div> 最后得分：<input type='text' name='thescore' size='3' id='"
						+ getTempBlockId() + "_" + getTempQuesSortid()
						+ "' value='" + qj.getMyScore() + "'/><br>");
		if (this.isLeader == 1) {//阅卷组长和超级管理员
			String scores="";
			out.print( "<table width=300 border=1><tr>");
			for (int i = 0; i < qj.getMultiUserPapers().size(); i++) {
				out.print("<td>"+qj.getMultiUserPapers().get(i).getElUser().getRealname()+"</td>");
			}
			out.print("</tr><tr>");
			for (int i = 0; i < qj.getMultiUserPapers().size(); i++) {
				out.print("<td>"+qj.getMultiUserPapers().get(i).getScore()+"</td>");
				if(i==0){
					scores+=qj.getMultiUserPapers().get(i).getScore();
				}else{
					scores+=","+qj.getMultiUserPapers().get(i).getScore();
				}
				
			}
			out.print("</tr></table><input type='button' id='txtAVg' name='txtAVg'  onclick=giveScore('"+getTempBlockId() + "_" + getTempQuesSortid()+"','"+scores+"','avg');  value='取平均分' />"
				+"<input type='button' id='txtHigh' name='txtHigh'  onclick=giveScore('"+getTempBlockId() + "_" + getTempQuesSortid()+"','"+scores+"','high');   value='取最高分'/>"
				+"<input type='button' id='txtAVg2' name='txtAVg2'  onclick=giveScore('"+getTempBlockId() + "_" + getTempQuesSortid()+"','"+scores+"','avg2');   value='去掉最高最低分取平均分'/>");
		}
		this.writeHiddenInput(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' />");
	}

	private void writeEPQsComm_cl(JspWriter out, Question qj, int blockid)
			throws Exception {
		if (qj.getQtype() == 1) {
			writeYesOrNo_cl(out, qj, blockid);
		}
		if (qj.getQtype() == 2) {
			writeSelect1_cl(out, qj, blockid);
		}
		if (qj.getQtype() == 4) {
			writeSelect2_cl(out, qj, blockid);
		}
		if (qj.getQtype() == 5) {
			writeBlank_cl(out, qj, blockid);
		}
		if (qj.getQtype() == 6) {
			writeEssay_cl(out, qj, blockid);
		}
	}

	private void writeYesOrNo_cl(JspWriter out, Question qj, int blockid)
			throws Exception {
		
		String tempStr="";
		//获取当前用户名：
		if (this.isLeader ==0) { //普通阅卷人
			tempStr=" readonly=true";
		} 
		out.println("<div class='answer'>" + "【系统打分:" + qj.getMyScore()
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid() + "_"
				+ getTempQuesClSortid() + "' name='thescore' size='3' value='"
				+ qj.getMyScore() + "' "+tempStr+"/>");
		this.writeHiddenInputCl(out, qj);
		out.println("<b><br>我的答案</b>：");
		if (qj.getStuAnswers() != null)
			if (qj.getStuAnswers()[0].equals("yes"))
				out.println("正确");
			else if (qj.getStuAnswers()[0].equals("no"))
				out.println("不正确");
			else if (!"".equals(qj.getStuAnswers()[0].trim()))
				out.println("该题未回答");
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (qj.getAnswers()[0].equals("yes"))
			out.println("正确");
		else
			out.println("不正确");
		out.println("</div>");
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' "+tempStr+"/>");
		out.println("</div>");

	}

	private void writeSelect1_cl(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println("<p>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
					+ qj.getOptions()[i] + "<br>");
		}
		out.print("</p>");

		out.println("<div class='answer'>" + "【系统打分:" + qj.getMyScore()
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input id='" + getTempBlockId()
				+ "_" + getTempQuesSortid() + "_" + getTempQuesClSortid()
				+ "' name='thescore' type='text' size='3' value='"
				+ qj.getMyScore() + "'/>");
		this.writeHiddenInputCl(out, qj);
		out.println("<b><br>我的答案</b>：");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				if (!"".equals(qj.getStuAnswers()[i].trim()))
					out
							.println(""
									+ ExamPaperUtil
											.getABC(qj.getStuAnswers()[i])
									+ "、");
			}
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (qj.getAnswers() != null) {
			for (int i = 0; i < qj.getAnswers().length; i++) {
				out
						.println("" + ExamPaperUtil.getABC(qj.getAnswers()[i])
								+ "、");
			}
		}
		out.println("</div>");
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		out.println("<div class='answer'>"
				+"批语：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid()
				+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' />");
		out.println("</div>");
	}

	private void writeSelect2_cl(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println("<p>");
		for (int i = 0; i < qj.getOptions().length; i++) {
			out.println("<b>" + ExamPaperUtil.getABC(i) + ":</b>"
					+ qj.getOptions()[i] + "<br>");
		}
		out.print("</p>");

		out.println("<div class='answer'>" + "【系统打分:" + qj.getMyScore()
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input id='" + getTempBlockId()
				+ "_" + getTempQuesSortid() + "_" + getTempQuesClSortid()
				+ "' name='thescore' type='text' size='3' value='"
				+ qj.getMyScore() + "'/>");
		this.writeHiddenInputCl(out, qj);
		out.println("<b><br>我的答案</b>：");
		if (null != qj.getStuAnswers())
			for (int i = 0; i < qj.getStuAnswers().length; i++) {
				if (!"".equals(qj.getStuAnswers()[i].trim()))
					out
							.println(""
									+ ExamPaperUtil
											.getABC(qj.getStuAnswers()[i])
									+ "、");
			}
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (qj.getAnswers() != null) {
			for (int i = 0; i < qj.getAnswers().length; i++) {
				out
						.println("" + ExamPaperUtil.getABC(qj.getAnswers()[i])
								+ "、");
			}
		}
		out.println("</div>");
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		out.println("</div>");
	}

	private void writeBlank_cl(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println("<div class='answer'> " + "【系统打分:" + qj.getMyScore()
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input id='" + getTempBlockId()
				+ "_" + getTempQuesSortid() + "_" + getTempQuesClSortid()
				+ "' name='thescore' type='text' size='3' value='"
				+ qj.getMyScore() + "'/>");
		this.writeHiddenInputCl(out, qj);
		out.println("<b><br>我的答案</b>：");
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
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
		out.println("</div>");
	}

	private void writeEssay_cl(JspWriter out, Question qj, int blockid)
			throws Exception {
		out.println(" <div class='answer'>" + "【系统打分:" + qj.getMyScore()
				+ "】&nbsp;&nbsp;&nbsp;最后得分：<input type='text' id='"
				+ getTempBlockId() + "_" + getTempQuesSortid() + "_"
				+ getTempQuesClSortid() + "' name='thescore' size='3' value='"
				+ qj.getMyScore() + "'/><br><b>我的回答</b>");
		this.writeHiddenInputCl(out, qj);
		if (null != qj.getStuAnswers()) {
			out.println(qj.getStuAnswers()[0].trim() + "</div>");
			out.println("<div class='answer'>"
					+"批语：<input type='text' id='"
					+ getTempBlockId() + "_" + getTempQuesSortid()
					+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' />");
		} else {
			out.println("</div>");
			out.println("<div class='answer'>"
					+"批语：<input type='text' id='"
					+ getTempBlockId() + "_" + getTempQuesSortid()
					+ "' name='thepiyu' size='50'  value='" + qj.getPiyu() + "' />");
		}
//		out.println(" <div style='color:green'><b>正确答案</b>：");
//		if (null != qj.getAnswers()){
//			for (int i = 0; i < qj.getAnswers().length; i++) {
//				out
//						.print("答案关键字" + (i + 1) + ":" + qj.getAnswers()[i]
//								+ "<br>");
//			}
//		}
//		out.println("</div>");
		out.println(" <div style='color:green'><b>正确答案</b>：");
		if (qj.getAnswers() != null && qj.getAnswers().length >= 1) {
			out.println(qj.getAnswers()[0] + "</div>");
		} else {
			out.println("</div>");
		}
		
		// out.println("<div style='color:blue'>答案解析：" + qj.getQexplain()
		// + "</div>");
		this.printQexplain(out, qj);
	}

	public float getTempEachscore() {
		return tempEachscore;
	}

	public void setTempEachscore(float tempEachscore) {
		this.tempEachscore = tempEachscore;
	}

	public int getTempBlockId() {
		return tempBlockId;
	}

	public void setTempBlockId(int tempBlockId) {
		this.tempBlockId = tempBlockId;
	}

	public int getTempQuesSortid() {
		return tempQuesSortid;
	}

	public void setTempQuesSortid(int tempQuesSortid) {
		this.tempQuesSortid = tempQuesSortid;
	}

	public int getTempQuesClSortid() {
		return tempQuesClSortid;
	}

	public void setTempQuesClSortid(int tempQuesClSortid) {
		this.tempQuesClSortid = tempQuesClSortid;
	}

	public String getTempBlockName() {
		return tempBlockName;
	}

	public void setTempBlockName(String tempBlockName) {
		this.tempBlockName = tempBlockName;
	}

	/**
	 * @return the isLeader
	 */
	public int getIsLeader() {
		return isLeader;
	}

	/**
	 * @param isLeader the isLeader to set
	 */
	public void setIsLeader(int isLeader) {
		this.isLeader = isLeader;
	}

	/**
	 * @return the papersTeacherCount
	 */
	public int getPapersTeacherCount() {
		return papersTeacherCount;
	}

	/**
	 * @param papersTeacherCount the papersTeacherCount to set
	 */
	public void setPapersTeacherCount(int papersTeacherCount) {
		this.papersTeacherCount = papersTeacherCount;
	}
	


}
