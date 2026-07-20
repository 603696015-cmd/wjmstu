package com.sopia.courseman.entities;

public class QuestionSelect {
	private int selectCount;
	private int selectOptions;
	private String selectOptionsStr;
	public int getSelectCount() {
		return selectCount;
	}
	public void setSelectCount(int selectCount) {
		this.selectCount = selectCount;
	}
	public int getSelectOptions() {
		return selectOptions;
	}
	public String getSelectOptions_() {
		if(selectOptions>=0){
			return "选项："+(char)(65+selectOptions);
		}else{
			return "选项："+getSelectOptionsStr();
		}
	}
	public String getSelectOptions_2() {
		if(selectOptions>=0){
			return "选"+(char)(65+selectOptions);
		}else{
			return "选"+getSelectOptionsStr();
		}
	}
	public void setSelectOptions(int selectOptions) {
		this.selectOptions = selectOptions;
	}
	public String getSelectOptionsStr() {
		if("yes".equals(selectOptionsStr)){
			return "正确";
		}else{
			return "错误";
		}
	}
	public void setSelectOptionsStr(String selectOptionsStr) {
		this.selectOptionsStr = selectOptionsStr;
	}
}
