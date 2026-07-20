package com.sopia.questionman.entities;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.ElConstants;

public class ExamPaperBlock {
	private static final Log logger = LogFactory.getLog(ExamPaperBlock.class);
	private int id;
	private ExamPaper examPaper;
	private String title;
	private String description;
	private int type;
	private int questionamount;
	private int realqamount;
	private float eachscore;
	private int sortid;
	private int random;
	@SuppressWarnings("unused")
	private String typeName;
	private List<Question> questions;
	private List<ExampaperRandom> epRandom;
	private String rulestring;
	private String[] rules;
	private float realscore;
	private float myscore;
	private String[][] dazirule;
	private int fwsize;
	//外经贸
	private int answerTime;
	private double secondScore;
	
	private String cosPlayRemark;//角色扮演标注
	
	/**
	 * 阅读使用
	 */
	private int readsort;
	
	
	
	public int getReadsort() {
		return readsort;
	}
	public void setReadsort(int readsort) {
		this.readsort = readsort;
	}
	public String getCosPlayRemark() {
		return cosPlayRemark;
	}
	public void setCosPlayRemark(String cosPlayRemark) {
		this.cosPlayRemark = cosPlayRemark;
	}
	public int getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(int answerTime) {
		this.answerTime = answerTime;
	}
	public double getSecondScore() {
		return secondScore;
	}
	public void setSecondScore(double secondScore) {
		this.secondScore = secondScore;
	}
	public int getRow() {
		int row = questionamount%10==0?questionamount/10:(int)(questionamount/10)+1;
		return row;
	}
	public int getFwsize() {
		return fwsize;
	}
	
	public float getQuestionscoresum(){
		return questionamount*(eachscore*1000)/1000.0f;
	}
	public float getRealqscoresum(){
		return realqamount*(eachscore*1000)/1000.0f;
	}
	public void setFwsize(int fwsize) {
		this.fwsize = fwsize;
	}

	public float getMyscore() {
		return myscore;
	}

	public void setMyscore(float myscore) {
		this.myscore = myscore;
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
			logger.error("获取打字评分规则错误",e);
		}
		return dazirule;
	}

	public void setDazirule(String[][] dazirule) {
		this.dazirule = dazirule;
	}
	public float getRealscore() {
		return realscore;
	}

	public void setRealscore(float realscore) {
		this.realscore = realscore;
	}

	public String[] getRules() {
		if (null != rulestring) {
			rules = rulestring.split(ElConstants.ruleSplit);
		}
		return rules;
	}

	public void setRules(String[] rules) {
		this.rules = rules;
	}

	public String getRulestring() {
		return rulestring;
	}

	public void setRulestring(String rulestring) {
		this.rulestring = rulestring;
	}

	public List<ExampaperRandom> getEpRandom() {
		return epRandom;
	}

	public void setEpRandom(List<ExampaperRandom> epRandom) {
		this.epRandom = epRandom;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public ExamPaperBlock() {
	}

	public String getTypeName() {

		switch (type) {
		case 1:
			return "判断题";
		case 2:
			return "单选题";
		case 3:
			return "不定项选择题";
		case 4:
			return "多选题";
		case 5:
			return "填空题";
		case 6:
			return "问答题";
		case 7:
			return "材料题";
		case 8: 
			return "打字题";
		case 9: 
			return "邮件题";
		case 10: 
			return "搜索题";
		case 11: 
			return "office题";
		case 12: 
			return "选做题";
		case 15: 
			return "看图选择";
		case 16: 
			return "看动画选择";
		case 17: 
			return "角色扮演";
		case 18: 
			return "听音选图";
		case 19: 
			return "拖拽题";
		case 20: 
			return "排序题";
		default:
			return "未知类型";
		}
	}

	public ExamPaperBlock(int id) {
		this.id = id;
	}

	public ExamPaperBlock(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getQuestionamount() {
		return questionamount;
	}

	public void setQuestionamount(int questionamount) {
		this.questionamount = questionamount;
	}

	public float getEachscore() {
		return eachscore;
	}

	public void setEachscore(float eachscore) {
		this.eachscore = eachscore;
	}

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRealqamount() {
		return realqamount;
	}

	public void setRealqamount(int realqamount) {
		this.realqamount = realqamount;
	}

//	public boolean equals(Object obj) {
//		if (obj instanceof ExamPaperBlock) {
//			if (((ExamPaperBlock) obj).getId() == this.getId()) {
//				return true;
//			} else
//				return false;
//		} else
//			return false;
//	}

//	public boolean hasQuestion(int id) {
//		if (null != this.questions) {
//			for (int i = 0; i < questions.size(); i++) {
//				if (id == questions.get(i).getId())
//					return true;
//			}
//		}
//		return false;
//	}

	public boolean hasQuestion(Question question) {
		if (null != this.questions && null != question) {
			for (int i = 0; i < questions.size(); i++) {
				if (question.equals(questions.get(i)))
					return true;
			}
		}
		return false;
	}

	public void sortQuestions() {
		if (null != questions)
			for (int i = 0; i < questions.size() - 1; i++) {
				for (int j = 1; j < questions.size() - i; j++) {
					Question q;
					if (questions.get(j - 1).getSortid() >= questions.get(j)
							.getSortid()) { // 比较两个整数的大小

						q = questions.get(j - 1);
						questions.set((j - 1), questions.get(j));
						questions.set(j, q);
					}

				}
			}
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}
}
