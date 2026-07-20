package com.sopia.common;

public class JTMSystemConf {
	private boolean open_jtm;//是否开启JTM
	private String my_EvaluationInit_URL;		//我的测评接口
	private String peoplePostInit_URL;			//人岗匹配接口
	private String reportEvalInit_URL;			//个人量身评价接口
	private String my_ReportInit_URL;			//查看个人报告接口
	private String courses_synchronization_URL;	//课程同步接口
	private String myCepingCourses_URL;			//我的测评课程接口
	
	public boolean isOpen_jtm() {
		return open_jtm;
	}
	public void setOpen_jtm(boolean open_jtm) {
		this.open_jtm = open_jtm;
	}
	public String getMy_EvaluationInit_URL() {
		return my_EvaluationInit_URL;
	}
	public void setMy_EvaluationInit_URL(String my_EvaluationInit_URL) {
		this.my_EvaluationInit_URL = my_EvaluationInit_URL;
	}
	public String getPeoplePostInit_URL() {
		return peoplePostInit_URL;
	}
	public void setPeoplePostInit_URL(String peoplePostInit_URL) {
		this.peoplePostInit_URL = peoplePostInit_URL;
	}
	public String getMy_ReportInit_URL() {
		return my_ReportInit_URL;
	}
	public void setMy_ReportInit_URL(String my_ReportInit_URL) {
		this.my_ReportInit_URL = my_ReportInit_URL;
	}
	public String getCourses_synchronization_URL() {
		return courses_synchronization_URL;
	}
	public void setCourses_synchronization_URL(String courses_synchronization_URL) {
		this.courses_synchronization_URL = courses_synchronization_URL;
	}
	public String getMyCepingCourses_URL() {
		return myCepingCourses_URL;
	}
	public void setMyCepingCourses_URL(String myCepingCourses_URL) {
		this.myCepingCourses_URL = myCepingCourses_URL;
	}
	public String getReportEvalInit_URL() {
		return reportEvalInit_URL;
	}
	public void setReportEvalInit_URL(String reportEvalInit_URL) {
		this.reportEvalInit_URL = reportEvalInit_URL;
	}
	
	

}
