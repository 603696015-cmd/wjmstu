package com.sopia.userDemo.entities;

/**
 * 页面信息
 * @author Administrator
 *
 */
public class ELUserColumnPage {
	
	private int pageid;		//页面id
	private String jspName;	//jsp名称
	private int upload;  	//是否已上传
	
	public ELUserColumnPage(){}
	public ELUserColumnPage(int pageid,String jspName){
		this.pageid = pageid;
		this.jspName = jspName;
	}
	public String getPageName(){
		if(this.pageid == 1){
			return "添加页面";
		}else if(this.pageid == 2){
			return "修改页面";
		}else if(this.pageid == 3){
			return "查看页面";
		}else if(this.pageid == 4){
			return "注册页面";
		}else if(this.pageid == 5){
			return "个人修改页面";
		}else if(this.pageid == 6){
			return "个人查看页面";
		}else if(this.pageid == 7){
			return "列表页面";
		}else{
			return "未知页面";
		}
	}
	
	public int getUpload() {
		return upload;
	}
	public void setUpload(int upload) {
		this.upload = upload;
	}
	public int getPageid() {
		return pageid;
	}
	public void setPageid(int pageid) {
		this.pageid = pageid;
	}
	public String getJspName() {
		return jspName;
	}
	public void setJspName(String jspName) {
		this.jspName = jspName;
	}
	
	

}
