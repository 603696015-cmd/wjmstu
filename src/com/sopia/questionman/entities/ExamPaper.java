package com.sopia.questionman.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sopia.courseman.entities.ExamRoom;
import com.sopia.duman.entities.ELUser;
import com.sopia.studyman.entities.MyExamPaper;

public class ExamPaper {
	private int id;
	private String title;
	private String queryurl;
	private String description;
	private ELUser elUser;
	private ExamPaperLib epl;
	private int during;
	private Timestamp modifytime;
	private Timestamp createtime;
	private Timestamp createtimeEnd;
	private boolean opentimelimit;
	private List<ExamPaperBlock> epBlocks;
	// --试卷上显示信息
	private float ep_tscore;
	private boolean courseHasEp;
	private float mep_tscore;
	private float ep_zscore;
	private float mepZscore;
	private float ep_kscore;
	private float mepKscore;
	private int showmod;
	private float ep_realscore;
	private float ep_questionscore;
	private float passgrade;
	private int stuview;
	private ExamPaper prac ;
	private int practimes;
	private float pracscore;
	private int userage ;
	private int status ;
	private int quizlook;
	private int scorelook;
	private int passmanner ;
	private int quizcount;
	private int isEditor;//是否可编辑
	private int usersize;//试卷分配的人数
	private int sortid;//排序用的
	private int showType;//试卷呈现方式，默认为0：一屏一题，5一屏一卷，10知识竞赛
	private int questionTotalCount;//题目总量
	
	public int getQuestionTotalCount() {
		return questionTotalCount;
	}
	public void setQuestionTotalCount(int questionTotalCount) {
		this.questionTotalCount = questionTotalCount;
	}
	public String getShowTypeName(){
		if(showType == 0){
			return "一屏一题";
		}else if(showType == 5){
			return "一屏一卷";
		}else if(showType == 10){
			return "知识竞赛";
		}else{
			return "未知";
		}
	}
	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	public int getUsersize() {
		return usersize;
	}
	public void setUsersize(int usersize) {
		this.usersize = usersize;
	}
	public int getIsEditor() {
		return isEditor;
	}
	public void setIsEditor(int isEditor) {
		this.isEditor = isEditor;
	}
	public int getPassmanner() {
		return passmanner;
	}
	public void setPassmanner(int passmanner) {
		this.passmanner = passmanner;
	}
	public int getQuizcount() {
		return quizcount;
	}
	public void setQuizcount(int quizcount) {
		this.quizcount = quizcount;
	}
	public int getQuizlook() {
		return quizlook;
	}
	public void setQuizlook(int quizlook) {
		this.quizlook = quizlook;
	}
	public int getScorelook() {
		return scorelook;
	}
	public void setScorelook(int scorelook) {
		this.scorelook = scorelook;
	}
	public int getUserage() {
		return userage;
	}
	public void setUserage(int userage) {
		this.userage = userage;
	}
	public int getShowmod() {
		return showmod;
	}
	public void setShowmod(int showmod) {
		this.showmod = showmod;
	}
	public void sortMep(List<MyExamPaper> meps){
		for (int i = 0; i < meps.size(); i++) {
			for (int j = i; j < meps.size(); j++) {
				if(meps.get(i).getId()>meps.get(j).getId()){
					MyExamPaper temp = meps.get(i);
					meps.set(i, meps.get(j));
					meps.set(j, temp);
				}
			}
		}
		
	} 
	public float getEp_tscore() {
	 
		return ep_tscore;
	}

	public void setEp_tscore(float ep_tscore) {
		this.ep_tscore = ep_tscore;
	}


	public ExamPaper() {
	}

	public ExamPaper(int id, String title) {
		this.id = id;
		this.title = title;
	}
	public ExamPaper(int id, String title,int showmod) {
		this.id = id;
		this.title = title;
		this.showmod = showmod;
	}
 
	public ExamPaper(int id) {
		this.id = id;
	}

	public boolean getOpentimelimit() {
		return opentimelimit;
	}

	public void setOpentimelimit(boolean opentimelimit) {
		this.opentimelimit = opentimelimit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}

	public ExamPaperLib getEpl() {
		return epl;
	}

	public void setEpl(ExamPaperLib epl) {
		this.epl = epl;
	}
	public int getDuring() {
		return during;
	}

	public void setDuring(int during) {
		this.during = during;
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
 
	public List<ExamPaperBlock> getEpBlocks() {
		return epBlocks;
	}

	public void setEpBlocks(List<ExamPaperBlock> epBlocks) {
		this.epBlocks = epBlocks;
	}
/*
	public boolean getErHasEp() {
		return erHasEp;
	}

	public void setErHasEp(boolean erHasEp) {
		this.erHasEp = erHasEp;
	}

	public int getEpZscore() {
		epZscore =0;
		if (null != epBlocks) {
			for (int i = 0; i < epBlocks.size(); i++) {
				ExamPaperBlock epbi = epBlocks.get(i);
				if(epbi.getType()==6) {
					epZscore+=epbi.getEachscore()*epbi.getQuestionamount();
				}
				if (epbi.getType()==7) {
					List<Question> qs = epbi.getQuestions();
					for (int j = 0; j < qs.size(); j++) {
						List<Question> qsc = qs.get(j).getChilds();
						if (qsc.get(j).getQtype()==6) {
							epZscore+=(int)((epbi.getEachscore()*qsc.get(j).getScoreper())/100);
						}
					}
				}
			}
		}
		return epZscore;
	}
	public void setEpZscore(int epZscore) {
		this.epZscore = epZscore;
	}

	public int getEpKscore() {
		epKscore = getEp_tscore()-getEpZscore();
		return epKscore ; 
	}

	public void setEpKscore(int epKscore) {
		
		this.epKscore = epKscore;
	}

	public int getMepZscore() {
		return mepZscore;
	}

	public void setMepZscore(int mepZscore) {
		this.mepZscore = mepZscore;
	}

	public int getMepKscore() {
		return mepKscore;
	}

	public void setMepKscore(int mepKscore) {
		this.mepKscore = mepKscore;
	}

	public int getMep_tscore() {
		return mep_tscore;
	}

	public void setMep_tscore(int mep_tscore) {
		this.mep_tscore = mep_tscore;
	}
*/
	public boolean isCourseHasEp() {
		return courseHasEp;
	}
	public void setCourseHasEp(boolean courseHasEp) {
		this.courseHasEp = courseHasEp;
	}
	public float getMep_tscore() {
		return mep_tscore;
	}
	public void setMep_tscore(float mep_tscore) {
		this.mep_tscore = mep_tscore;
	}
	public float getMepZscore() {
		return mepZscore;
	}
	public void setMepZscore(float mepZscore) {
		this.mepZscore = mepZscore;
	}
	public float getMepKscore() {
		return mepKscore;
	}
	public void setMepKscore(float mepKscore) {
		this.mepKscore = mepKscore;
	}
	public float getEp_zscore() {
		return ep_zscore;
	}
	public void setEp_zscore(float ep_zscore) {
		this.ep_zscore = ep_zscore;
	}
	public float getEp_kscore() {
		return ep_kscore;
	}
	public void setEp_kscore(float ep_kscore) {
		this.ep_kscore = ep_kscore;
	}
	public void sortBlocks() {
		if (null != epBlocks)
			for (int i = 0; i < epBlocks.size() - 1; i++) {
				for (int j = 1; j < epBlocks.size() - i; j++) {
					 ExamPaperBlock q ;
					    if(epBlocks.get(j-1).getSortid()>epBlocks.get(j).getSortid()) {   //比较两个整数的大小
					     q=epBlocks.get(j-1);
					     epBlocks.set((j-1),epBlocks.get(j));
					     epBlocks.set(j,q);
					    }
				}
			}
		if(null!=epBlocks)
			for (int i = 0; i < epBlocks.size(); i++) {
				epBlocks.get(i).sortQuestions();
			}
	}
	public float getEp_realscore() {
		return ep_realscore;
	}
	public void setEp_realscore(float ep_realscore) {
		this.ep_realscore = ep_realscore;
	}
	public ExamPaper getPrac() {
		return prac;
	}
	public void setPrac(ExamPaper prac) {
		this.prac = prac;
	}
	public int getPractimes() {
		return practimes;
	}
	public void setPractimes(int practimes) {
		this.practimes = practimes;
	}
	public float getPracscore() {
		return pracscore;
	}
	public void setPracscore(float pracscore) {
		this.pracscore = pracscore;
	}
	public String getQueryurl() {
		return queryurl;
	}
	public ArrayList<Qurl> getQueryurls(){
		ArrayList<Qurl> qus = new ArrayList<Qurl>();
		if(queryurl!=null)
		{
			String qs [] = queryurl.split("-th=") ;
			for (int i = 0; i < qs.length; i++) {
				String url[] = qs[i].split("=th-") ; 
				Qurl ql= new Qurl();
				if(null!=url){
					ql.setTitle(url[0]);
					ql.setHref(url[1]);
					qus.add(ql);
				}
			}
		}
		return qus;
	}
	public void setQueryurl(String queryurl) {
		this.queryurl = queryurl;
	}
	public float getPassgrade() {
		return passgrade;
	}
	public void setPassgrade(float passgrade) {
		this.passgrade = passgrade;
	}
	public int getStuview() {
		return stuview;
	}
	public void setStuview(int stuview) {
		this.stuview = stuview;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusName() { 
//		if(status == 0)return "已创建";
//		if(status == 2)return "编辑中";
//		else if(status == 1)return "已删除";
//		return "未知类型";
		if(status == 0)return "正常使用";
		else if(status == 2)return "编辑中";
		else if(status == 1)return "作废";
		return "未知类型";
	}
	public Timestamp getCreatetimeEnd() {
		return createtimeEnd;
	}
	public void setCreatetimeEnd(Timestamp createtimeEnd) {
		this.createtimeEnd = createtimeEnd;
	}
	public float getPassScore(){
		return  (int)((ep_tscore* passgrade/100)*100)/100.00f;
	}
	public float getEp_questionscore() {
		return ep_questionscore;
	}
	public void setEp_questionscore(float ep_questionscore) {
		this.ep_questionscore = ep_questionscore;
	}
}
class Qurl{
	private String title;
	private String href;
	public Qurl() {
		// TODO Auto-generated constructor stub
	}
	public Qurl(String title,String href){
		this.title= title;
		this.href = href;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}  
	
}
