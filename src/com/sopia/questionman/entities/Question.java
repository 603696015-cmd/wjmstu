package com.sopia.questionman.entities;

import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.common.CheckHtml;
import com.sopia.common.J2EEFileUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.courseman.entities.MultiUserPapers;
import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.QtypeUtil;
import com.sopia.record.service.MscRecodServiceImpl;

public class Question {
	private static final Log logger = LogFactory.getLog(Question.class);
	private int id;
	private int qtype;
	private String title;
	private String content;
	private String subject;
	private String qexplain;
	private ELUser eluser;
	private QuestionLib qlib;
	private Timestamp modifytime;
	private Timestamp createtime; 
	private Timestamp createtimeEnd;
	private int qlevel;
	private String qtypeName;
	private String[] answers;
	private String answer;
	private boolean eqbHave;
	private int scoreper;
	private Question parent;
	private String[] options;
	private String optionsU;
	private int minWord;
	private List<Question> childs;
	private int sortid;
	private String stuAnswer;
	private String[] stuAnswers;
	private float score;
	private float myScore;
	private String rulestring;
	private String[] rules;
	private String oldrulestring;
	private String[] oldrules;
	private float oldscore;
	private String[][] dazirule;
	private ExamPaperBlock epblock;
	private int mystatus;
	private int status; 
	private String testsupport;
	private int opstatus;//0未答，1已答，2存疑
	private int fwsize ;
	private List<MultiUserPapers> multiUserPapers;
	private String subjectContent;
	
	//外经贸
	private String fashengQuestion;
	private String rightAnswer;
	private String mediaFile;
	private String frontHalfMediaFile;//前半截动画文件
	private String modelVoice;//样音文字
	private String modelVoiceText;//样音文件
	
	private String [] modelVoiceTexts;
	private String voicePath;
	private String [] voicePaths;
	
	private String fenContent;
	private String [] fenContents;
	private int atime;
	
	private int hasVoice;//用户是否上传了语音
	private int similary;//上传的语音解析后的文本与正确答案文本的相似度
	private int myExamPaperid;//试卷id
	private String voiceFile;//问题上传的文件
	private String stuVoiceText;//用户上传文件解析的文本
	
	private String stemText;//题干文本
	private String fileName;//录音文件
	
	private String voiceAnswer; //录音文本
	private String sentenceText;//句子文本
	private String standardAnswer;

	
	//投票
	private String subjects;
	
	private int questionNumber;//试卷中当前题目的序号
	
	private String piyu;//批语
	
	
	public String getPiyu() {
		return piyu;
	}

	public void setPiyu(String piyu) {
		this.piyu = piyu;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public String getStandardAnswer() {
		return standardAnswer;
	}

	public void setStandardAnswer(String standardAnswer) {
		this.standardAnswer = standardAnswer;
	}

	public String getSentenceText() {
		return sentenceText;
	}

	public void setSentenceText(String sentenceText) {
		this.sentenceText = sentenceText;
	}

	public String getVoiceAnswer() {
		return voiceAnswer;
	}

	public void setVoiceAnswer(String voiceAnswer) {
		this.voiceAnswer = voiceAnswer;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFrontHalfMediaFile() {
		return frontHalfMediaFile;
	}

	public void setFrontHalfMediaFile(String frontHalfMediaFile) {
		this.frontHalfMediaFile = frontHalfMediaFile;
	}

	public String getStemText() {
		return stemText;
	}

	public void setStemText(String stemText) {
		this.stemText = stemText;
	}

	public String getVoiceFile() {
		return voiceFile;
	}

	public void setVoiceFile(String voiceFile) {
		this.voiceFile = voiceFile;
	}

	public String getStuVoiceText() {
		return stuVoiceText;
	}

	public void setStuVoiceText(String stuVoiceText) {
		this.stuVoiceText = stuVoiceText;
	}

	public int getMyExamPaperid() {
		return myExamPaperid;
	}

	public void setMyExamPaperid(int myExamPaperid) {
		this.myExamPaperid = myExamPaperid;
	}

	public int getHasVoice() {
		return hasVoice;
	}

	public void setHasVoice(int hasVoice) {
		this.hasVoice = hasVoice;
	}


	public int getSimilary() {
		return similary;
	}

	public void setSimilary(int similary) {
		this.similary = similary;
	}

	public int getAtime() {
		return atime;
	}

	public void setAtime(int atime) {
		this.atime = atime;
	}

	private int qindex;//是否为第二次提交
	
	public int getQindex() {
		return qindex;
	}

	public void setQindex(int qindex) {
		this.qindex = qindex;
	}

	public String[] getVoicePaths() {
		if (null != voicePath) {
			voicePaths = voicePath.split(ElConstants.optSplit);
		}
		System.out.println(Arrays.toString(voicePaths));
		return voicePaths;
	}

	public void setVoicePaths(String[] voicePaths) {
		this.voicePaths = voicePaths;
	}

	public String getVoicePath() {
		switch (qtype) {
		case 18: {
			voicePath =voicePath==null?"":voicePath;
			if (null != voicePaths)
				for (int i = 0; i < voicePaths.length; i++) {
					voicePath += voicePaths[i] + ElConstants.optSplit;
				}
			break;
		}
		default:
			break;
		}
		return voicePath;
	}

	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}

	
	public String[] getModelVoiceTexts() {
		if (null != modelVoiceText) {
			modelVoiceTexts = modelVoiceText.split(ElConstants.optSplit);
		}
		System.out.println(Arrays.toString(modelVoiceTexts));
		return modelVoiceTexts;
	}

	public void setModelVoiceTexts(String[] modelVoiceTexts) {
		this.modelVoiceTexts = modelVoiceTexts;
	}
	
	
	public String[] getFenContents() {
		if (null != fenContent) {
			fenContents = fenContent.split(ElConstants.optSplit);
		}
		System.out.println(Arrays.toString(fenContents));
		return fenContents;
	}

	public void setFenContents(String[] fenContents) {
		this.fenContents = fenContents;
	}

	public String getFenContent() {
		switch (qtype) {
		case 19: {
			fenContent =fenContent==null?"":fenContent;
			if (null != fenContents)
				for (int i = 0; i < fenContents.length; i++) {
					fenContent += fenContents[i] + ElConstants.optSplit;
				}
			break;
		}
		default:
			break;
		}
		return fenContent;
	}

	public void setFenContent(String fenContent) {
		this.fenContent = fenContent;
	}
	
	public String getCompeleteURL() {
		if(fashengQuestion!=null&&(fashengQuestion.indexOf("http://")==0||fashengQuestion.indexOf("https://")==0))
			return fashengQuestion;
		return  SystemConfOp.getStuffUrl()+fashengQuestion;
	}
	
	public String getModelVoice() {
		return modelVoice;
	}

	public void setModelVoice(String modelVoice) {
		this.modelVoice = modelVoice;
	}

	public String getModelVoiceText() {
		return modelVoiceText;
	}

	public void setModelVoiceText(String modelVoiceText) {
		this.modelVoiceText = modelVoiceText;
	}

	public String getMediaFile() {
		return mediaFile;
	}

	public void setMediaFile(String mediaFile) {
		this.mediaFile = mediaFile;
	}

	public String getFashengQuestion() {
		return fashengQuestion;
	}

	public void setFashengQuestion(String fashengQuestion) {
		this.fashengQuestion = fashengQuestion;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	/**
	 * @return the multiUserPapers
	 */
	public List<MultiUserPapers> getMultiUserPapers() {
		return multiUserPapers;
	}

	/**
	 * @param multiUserPapers the multiUserPapers to set
	 */
	public void setMultiUserPapers(List<MultiUserPapers> multiUserPapers) {
		this.multiUserPapers = multiUserPapers;
	}

	public int getFwsize() {
		return fwsize;
	}

	public void setFwsize(int fwsize) {
		this.fwsize = fwsize;
	}

	public int getOpstatus() {
		return opstatus;
	}

	public void setOpstatus(int opstatus) {
		this.opstatus = opstatus;
	}

	public String getTestsupport() {
		return testsupport;
	}

	public void setTestsupport(String testsupport) {
		this.testsupport = testsupport;
	}

	public int getMystatus() {
		return mystatus;
	}

	public String getMystatusStr() {
		//

		switch (mystatus) {
		case -1:// -1 未评分

			return "未评分";
		case -2:
			return "未知题型";
		case -3:
			return "没设定评分规则";
		case 0:
			return "未通过";// （打字题）
		case 1:
			return "已通过";
		case 2:
			return "已提交";
		case -4:
			return "年龄未知";
		case -5:
			return "错误未知";
		case -6:
			return "小题题型不对";
		case -7:
			return "年龄段规则未设置";
		default:
			return "未知状态";
		}
	}
	public String getMystaclass() {
		//
		if(myScore>0)
			return "ca_td_yd";
		else
			return "ca_td_cy";
		/*switch (mystatus) {
		case -1:// -1 未评分

			return "ca_td_cy";
		case -2:
			return "ca_td_cy";
		case -3:
			return "ca_td_cy";
		case 0:
			return "ca_td_cy";// （打字题）
		case 1:
			return "ca_td_yd";
		case -4:
			return "ca_td_cy";
		case -5:
			return "ca_td_cy";
		case -6:
			return "ca_td_cy";
		default:
			return "ca_td_cy";*/
//		}
	}
	public void setMystatus(int mystatus) {
		this.mystatus = mystatus;
	}

	public ExamPaperBlock getEpblock() {
		return epblock;
	}

	public void setEpblock(ExamPaperBlock epblock) {
		this.epblock = epblock;
	}

	public String[][] getDazirule() {
		try {
			String[][] xxxx = null;
			if (null != getRules() && getRules().length > 3) {
				String x = getRules()[3];
				String[] xx = x.split(":");
				if (null != xx) {
					int row = 0, col = 0;
					int _row = xx.length / 5;
					xxxx = new String[_row][5];
					for (int i = 0; i < xx.length; i++) {
						// "=="
						// + xx[i]);
						xxxx[row][col] = xx[i];
						if (i % 5 == 4) {
							row++;
						}
						col++;
						if (col >= 5)
							col = 0;
					}
				}
			}
			dazirule = xxxx;
		} catch (Exception e) {
			logger.error("获取打字题评分规则错误",e);
		}
		return dazirule;
	}

	public void setDazirule(String[][] dazirule) {
		this.dazirule = dazirule;
	}

	public String getRulestring() {
		return rulestring;
	}

	public void setRulestring(String rulestring) {
		this.rulestring = rulestring;
	}

	public float getMyScore() {
		return myScore;
	}

	public void setMyScore(float myScore) {
		this.myScore = myScore;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String[] getStuAnswers() {
		if (null != stuAnswer) {
			return stuAnswer.split(ElConstants.optSplit);
		}
		return stuAnswers;
	}
	
	public String[] getStuAnswers2() {
		if (null != stuAnswer) {
			return stuAnswer.split(ElConstants.valSplit);
		}
		return stuAnswers;
	}

	public void setStuAnswers(String[] stuAnswers) {
		this.stuAnswers = stuAnswers;
	}

//	public String getStuAnswer() {
//		if (null == stuAnswer && null != stuAnswers) {
//			stuAnswer = "";
//			for (int i = 0; i < stuAnswers.length; i++) {
//				stuAnswer += "".equals(stuAnswers[i]) ? " " : stuAnswers[i]
//						+ ElConstants.optSplit;
//			}
//		}
//		return stuAnswer;
//	}
	
	public String getStuAnswer() {
		if (null == stuAnswer && null != stuAnswers) {
			stuAnswer = "";
			for (int i = 0; i < stuAnswers.length; i++) {
				stuAnswer += "".equals(stuAnswers[i]) ? " "+ ElConstants.optSplit : stuAnswers[i]
						+ ElConstants.optSplit;
			}
		}
		return stuAnswer;
	}
	
	public String getSingleStuAnswer(){
		return stuAnswer;
	}

	public void setStuAnswer(String stuAnswer) {
		this.stuAnswer = stuAnswer;
	}

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}

	public int getMinWord() {
		return minWord;
	}

	public void setMinWord(int minWord) {
		this.minWord = minWord;
	}

	public String getOptionsU() {
		return optionsU;
	}

	public void setOptionsU(String optionsU) {
		this.optionsU = optionsU;
	}

	public int getScoreper() {
		return scoreper;
	}

	public void setScoreper(int scoreper) {
		this.scoreper = scoreper;
	}

	public Question getParent() {
		return parent;
	}

	public void setParent(Question parent) {
		this.parent = parent;
	}

	public boolean getEqbHave() {
		return eqbHave;
	}

	public void setEqbHave(boolean eqbHave) {
		this.eqbHave = eqbHave;
	}

	public Question() {
	}

	public Question(int id) {
		this.id = id;
	}

	public Question(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQtype() {
		return qtype;
	}

	public void setQtype(int qtype) {
		this.qtype = qtype;
	}

	public String getTitle() {
		if (null == title || "".equals(title.trim()))
			if (null != content)
				return content;
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		if (null == content || "".equals(content.trim()))
			if (null != title)
				return title;
		return content;
	}
	
	public String getContent_() {
		if (null == content || "".equals(content.trim()))
			if (null != title)
				return title;
		return  SystemConfOp.toStuffUrl(content);
	}

	
	public void setContent(String content) {
		this.content = content;
	}
	public String getMailContent(){
		if(answers==null)
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append("<b>发 给：</b>");
		sb.append(answers[0]);
		sb.append("<br/><b>抄 送：</b>");
		sb.append(answers[1]);
		sb.append("<br/><b>密 送：</b>");
		sb.append(answers[2]);
		sb.append("<br/><b>附 件：</b>");
		sb.append(answers[3]);
		sb.append("<br/><b>主 题：</b>");
		sb.append(answers[4]);
		sb.append("<br/><b>正 文：</b>");
		sb.append(answers[5]);
		return sb.toString();
	}
	public String getMailTitle(){
		if(answers==null)
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append("发 给：");
		sb.append(answers[0]);
		sb.append("抄 送：");
		sb.append(answers[1]);
		sb.append("密 送：");
		sb.append(answers[2]);
		sb.append("附 件：");
		sb.append(answers[3]);
		sb.append("主 题：");
		sb.append(answers[4]);
		sb.append("正 文：");
		sb.append(answers[5]);
		return sb.toString();
	}
	public String getQexplain() {
		return qexplain;
	}

	public void setQexplain(String qexplain) {
		this.qexplain = qexplain;
	}

	public ELUser getEluser() {
		return eluser;
	}

	public void setEluser(ELUser eluser) {
		this.eluser = eluser;
	}

	public QuestionLib getQlib() {
		return qlib;
	}

	public void setQlib(QuestionLib qlib) {
		this.qlib = qlib;
	}

	public Timestamp getModifytime() {
		return modifytime;
	}

	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public int getQlevel() {
		return qlevel;
	}

	public void setQlevel(int qlevel) {
		this.qlevel = qlevel;
	}

	public void setQlevel(String qlevel) {
		int ql = 1;
		try {
			ql = new Integer(qlevel);
		} catch (Exception e) {
			ql = 1;
		}
		this.qlevel = ql;
	}
	
	public String getOption(){
		String temp="";
		if (null != options){
			for (int i = 0; i < options.length; i++) {
				temp += options[i] + ElConstants.optSplit;
			}
		}else{
			temp=subject;
		}
		return temp;
	}
	
	public String getRealSubject(){
		return this.subject;
	}

	public String getSubject() {
		switch (qtype) {
		case 1:
			break;
		case 2: {
			subject =subject==null?"":subject;
			if (null != options)
				for (int i = 0; i < options.length; i++) {
					subject += options[i] + ElConstants.optSplit;
				}
			break;
		}
		case 3: {
			subject = "";
			if (null != options)
				for (int i = 0; i < options.length; i++) {
					subject += options[i] + ElConstants.optSplit;
				}
			break;
		}
		case 4: {
			subject =subject==null?"":subject;
			if (null != options)
				for (int i = 0; i < options.length; i++) {
					subject += options[i] + ElConstants.optSplit;
				}
			break;
		}
		case 15: {
			subject =subject==null?"":subject;
			if (null != options)
				for (int i = 0; i < options.length; i++) {
					subject += options[i] + ElConstants.optSplit;
				}
			break;
		}
		case 16: {
			subject =subject==null?"":subject;
			if (null != options)
				for (int i = 0; i < options.length; i++) {
					subject += options[i] + ElConstants.optSplit;
				}
			break;
		}
		case 18: {
			subject =subject==null?"":subject;
			if (null != options)
				for (int i = 0; i < options.length; i++) {
					subject += options[i] + ElConstants.optSplit;
				}
			break;
		}
		case 19: {
			subject =subject==null?"":subject;
			if (null != options)
				for (int i = 0; i < options.length; i++) {
					subject += options[i] + ElConstants.optSplit;
				}
			break;
		}
		case 115:{
			subject =subject==null?"":subject;
			if (null != options)
				for (int i = 0; i < options.length; i++) {
					subject += options[i] + ElConstants.optSplit;
				}
			break;
		}
		default:
			break;
		}
		return subject;
	}
	public String getSubject_() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAnswer() {
		if (null == answer && null != answers) {
			answer = "";

			for (int i = 0; i < answers.length; i++) {
				answer += answers[i] + ElConstants.optSplit;
			}
		}
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQtypeName() {
		switch (qtype) {
		case 1:// 判断题

			qtypeName = "判断题";
			break;
		case 2:// 选择题

			qtypeName = "单选题";
			break;
		case 3:// 不定项选择题
			qtypeName = "不定项选择题";
			break;
		case 4:// 多项选择题

			qtypeName = "多选题";
			break;
		case 5:// 填空题

			qtypeName = "填空题";
			break;
		case 6:// 问答题

			qtypeName = "问答题";
			break;
		case 7:// 材料题
			qtypeName = "材料题";
			break;
		case 8:// 材料题
			qtypeName = "打字题";
			break;
		case 9:// 材料题
			qtypeName = "邮件题";
			break;
		case 10:// 材料题
			qtypeName = "搜索题";
			break;
		case 11:// 材料题
			qtypeName = "office题";
			break;
		case 15:// 材料题
			qtypeName = "看图选择";
			break;
		case 16:// 材料题
			qtypeName = "看动画选择";
			break;
		case 17:// 材料题
			qtypeName = "角色扮演";
			break;
		case 18:// 材料题
			qtypeName = "听音选图";
			break;
		case 19:// 材料题
			qtypeName = "拖拽题";
			break;
		case 20:// 材料题
			qtypeName = "排序题";
			break;
		case 115://阅读题
			qtypeName = "阅读题";
			break;
		default:
			qtypeName = "未知题型";
			break;
		}
		return qtypeName;
	}

	public void setQtypeName(String qtypeName) {
		this.qtypeName = qtypeName;
	}

	//听音选图设置题支的时候，保存数据库的时候，不添加资源服务器地址
	public String[] getOptions() {
		if (null != subject) {
			options = subject.split(ElConstants.optSplit);
		}
		switch (qtype) {
		case 18://听音选图
			if (null != subject) {
				options= subject.split(ElConstants.optSplit);
//				for(int i=0;i<options.length;i++){
//					options[i]=SystemConfOp.toStuffUrl(options[i]);
//				}
			}
			System.out.println("-----------"+Arrays.toString(options));
			break;
		default:
			if (null != subject) {
				options = subject.split(ElConstants.optSplit);
			}
			break;
		}
		return options;
	}
	
	//听音选图，在考试和查看试卷页面上，添加资源服务器地址
	public String[] getOptions1() {
		switch (qtype) {
		case 18://听音选图
			if (null != subject) {
				options= subject.split(ElConstants.optSplit);
				for(int i=0;i<options.length;i++){
					options[i]=SystemConfOp.toStuffUrl(options[i]);
				}
			}
			System.out.println("-----------"+Arrays.toString(options));
			break;
		default:
			if (null != subject) {
				options = subject.split(ElConstants.optSplit);
			}
			break;
		}
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public String[] getAnswers() {
		if (null != answer) {
			answers = answer.split(ElConstants.optSplit);
		}
		return answers;
	}
	public String[] getAnswers_() {
		return answers;
	}
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public List<Question> getChilds() {
		return childs;
	}

	public void setChilds(List<Question> childs) {
		this.childs = childs;
	}

//	public boolean equals(Object obj) {
//		if (obj instanceof Question) {
//			if (this.id == ((Question) obj).getId()) {
//				return true;
//			}
//		}
//		return false;
//	}

	public String[] getRules() {
		if (null != rulestring) {
			rules = rulestring.split(ElConstants.ruleSplit);
			// rules =
			// "1-=SpRule-3-=SpRule-2-=SpRule-5-=SpRule-3-=SpRule-1-=SpRule-".split(ElConstants.ruleSplit);
		}
		return rules;
	}

	public void setRules(String[] rules) {
		this.rules = rules;
	}

	public String getOldrulestring() {
		if (null == oldrulestring && null != oldrules) {
			oldrulestring = "";

			for (int i = 0; i < oldrules.length; i++) {
				oldrulestring += oldrules[i] + ElConstants.ruleSplit;
			}
		}
		return oldrulestring;
	}

	public void setOldrulestring(String oldrulestring) {
		this.oldrulestring = oldrulestring;
	}

	public String[] getOldrules() {
		if (null != oldrulestring) {
			oldrules = oldrulestring.split(ElConstants.ruleSplit);
		}
		return oldrules;
	}

	public void setOldrules(String[] oldrules) {
		this.oldrules = oldrules;
	}

	public float getOldscore() {
		return oldscore;
	}

	public void setOldscore(float oldscore) {
		this.oldscore = oldscore;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusName() {
//		if(status == 0)return "已创建";
//		else if(status == 1)return "已作废";
//		else if(status == 2)return "编辑中";
		if(status == 0)return "正常使用";
		else if(status == 1)return "作废";
		else if(status == 2)return "编辑中";
		return "未知类型";
	}
	
//	public String getTitle_cn(String content){
//		if(content.indexOf("<p>")>=0){
//			content=content.replaceAll("<p>", "");
//			content=content.replaceAll("</p>", "\r\n");
//		}
//		if(content.indexOf("<br />")>=0){
//			content=content.replaceAll("<br />", "\r\n");
//		}
//		return content;
//	}

	public Timestamp getCreatetimeEnd() {
		return createtimeEnd;
	}

	public void setCreatetimeEnd(Timestamp createtimeEnd) {
		this.createtimeEnd = createtimeEnd;
	}
	private int age;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getMansize(){
		int s=  0;
		String xx[][] = getDazirule();
		if(xx!=null)
		for (int i = 0; i < xx.length; i++) {
			String xxi[]=xx[i];
			if(xxi!=null)
					if(age>=Integer.parseInt(xxi[0])&&age<=Integer.parseInt(xxi[1])){
						s = Integer.parseInt(xxi[4])*Integer.parseInt(getRules()[2]);
						break;
					}
		}
		return s;
	}

	public String getSubjectContent() {
		return subjectContent;
	}

	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}
	
	//特殊题型获取标准答案
	//看图选择、看动画选择
	public String getStandardAnswer1516(){
		return CheckHtml.getString(this.options[Integer.parseInt(this.answer.replace(ElConstants.optSplit, ""))]);
	}
	//角色扮演、拖拽、排序
	public String getStandardAnswer171920(){
		return this.modelVoiceText;
	}
	//听音选图
	public String getStandardAnswer18(){
		int i = Integer.parseInt(this.answer.replace(ElConstants.optSplit, ""))+1;
		return "第" + i + "幅图";
	}
	
	//用户答案
	@SuppressWarnings("static-access")
	public String getStuAnswer_(){
		if(this.qtype == 15 || this.qtype == 16){
			return CheckHtml.getString(this.options[Integer.parseInt(this.stuAnswer.replace(ElConstants.optSplit, ""))]);
		}else if(this.qtype == 18){
			int i = Integer.parseInt(this.stuAnswer.replace(ElConstants.optSplit, ""))+1;
			return "第" + i + "幅图";
		}else if(this.qtype == 17 || this.qtype == 19 || this.qtype == 20){
			//已废弃
			if(this.fileName!=null && !this.fileName.equals("")){
				String voiceText = new MscRecodServiceImpl().getMscObj().recognize(fileName);
				return voiceText == null ? "":voiceText;
			}else{
				return "学员未录音";
			}
		}else {
			return "";
		}
	}
	
	/**
	 * 获取pcm转wma的filename
	 * @return
	 */
	public String getRecoding(){
		String filename = QtypeUtil.getRecodingWmaFilename(this);
		if(filename != null && !filename.equals("")){
			return  "elstuffs/audio/" +  QtypeUtil.getRecodingWmaFilename(this);
		}else{
			return "";
		}
	}


	
}
