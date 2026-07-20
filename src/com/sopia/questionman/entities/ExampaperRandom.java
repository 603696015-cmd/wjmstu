package com.sopia.questionman.entities;

public class ExampaperRandom {
	private int id;
	private QuestionLib qlib;
	private ExamPaperBlock epBlock;
	private int qlevel1;
	private int qlevel2;
	private int qlevel3;
	private int qlevel4;
	private int qlevel5;
	private int qlevel;
	private int sortid;
	private int suboperate;
	private String errorMessage;
	private ExampaperRandom epRandom1 ;
	private int qlevel1_;
	private int qlevel2_;
	private int qlevel3_;
	private int qlevel4_;
	private int qlevel5_;
	private int qlevel_;
	
	public ExampaperRandom getEpRandom1() {
		return epRandom1;
	}
	public void setEpRandom1(ExampaperRandom epRandom1) {
		this.epRandom1 = epRandom1;
	}
	public ExampaperRandom() {
	}
	public ExampaperRandom(int id) {
		this.id= id;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public QuestionLib getQlib() {
		return qlib;
	}

	public void setQlib(QuestionLib qlib) {
		this.qlib = qlib;
	}

	public ExamPaperBlock getEpBlock() {
		return epBlock;
	}

	public void setEpBlock(ExamPaperBlock epBlock) {
		this.epBlock = epBlock;
	}

	public int getQlevel1() {
		return qlevel1;
	}

	public void setQlevel1(int qlevel1) {
		this.qlevel1 = qlevel1;
	}

	public int getQlevel2() {
		return qlevel2;
	}

	public void setQlevel2(int qlevel2) {
		this.qlevel2 = qlevel2;
	}

	public int getQlevel3() {
		return qlevel3;
	}

	public void setQlevel3(int qlevel3) {
		this.qlevel3 = qlevel3;
	}

	public int getQlevel4() {
		return qlevel4;
	}

	public void setQlevel4(int qlevel4) {
		this.qlevel4 = qlevel4;
	}

	public int getQlevel5() {
		return qlevel5;
	}

	public void setQlevel5(int qlevel5) {
		this.qlevel5 = qlevel5;
	}

	public int getQlevel() {
		return qlevel;
	}

	public void setQlevel(int qlevel) {
		this.qlevel = qlevel;
	}

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}

	public int getSuboperate() {
		return suboperate;
	}

	public void setSuboperate(int suboperate) {
		this.suboperate = suboperate;
	}

	public boolean compareTo(ExampaperRandom obj) {
		errorMessage = "";
		boolean ok = false;
		boolean ok1 = false;
		boolean ok2 = false;
		boolean ok3 = false;
		boolean ok4 = false;
		boolean ok5 = false;
		if (obj.qlevel < this.qlevel)
			errorMessage += "我的qlevel过大<br>";
		else
			ok = true;
		if (obj.qlevel1 < this.qlevel1)
			errorMessage += "我的qlevel1过大<br>";
		else
			ok1= true;
		if (obj.qlevel2 < this.qlevel2)
			errorMessage += "我的qlevel2过大<br>";
		else
			ok2 = true;
		if (obj.qlevel3 < this.qlevel3)
			errorMessage += "我的qlevel3过大<br>";
		else
			ok3= true;
		if (obj.qlevel4 < this.qlevel4)
			errorMessage += "我的qlevel4过大<br>";
		else
			ok4 = true;
		if (obj.qlevel5 < this.qlevel5)
			errorMessage += "我的qlevel5过大<br>";
		else
			ok5 = true;
			return ok&&ok1&&ok2&&ok3&&ok4&&ok5;
	}
	public int getQlevel1_() {
		return qlevel1_;
	}
	public void setQlevel1_(int qlevel1_) {
		this.qlevel1_ = qlevel1_;
	}
	public int getQlevel2_() {
		return qlevel2_;
	}
	public void setQlevel2_(int qlevel2_) {
		this.qlevel2_ = qlevel2_;
	}
	public int getQlevel3_() {
		return qlevel3_;
	}
	public void setQlevel3_(int qlevel3_) {
		this.qlevel3_ = qlevel3_;
	}
	public int getQlevel4_() {
		return qlevel4_;
	}
	public void setQlevel4_(int qlevel4_) {
		this.qlevel4_ = qlevel4_;
	}
	public int getQlevel5_() {
		return qlevel5_;
	}
	public void setQlevel5_(int qlevel5_) {
		this.qlevel5_ = qlevel5_;
	}
	public int getQlevel_() {
		return qlevel_;
	}
	public void setQlevel_(int qlevel_) {
		this.qlevel_ = qlevel_;
	}

}
