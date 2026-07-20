package com.sopia.knowledgeManage.entities;

import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

/**
 * 权限
 * @author Administrator
 *
 */
public class Competence {
	//1：查看、2：修改、3：删除、4复制、5下载
	private int type;			//权限类型
	private int userCount;		
	private boolean can_op;		//是否有该操作的权限
	private int userid;
	private ELUser eu;
//	private String userids;
	private int depid;
	private Department dt;
//	private String depids;
	
	public Competence(){}
	
	public Competence(boolean can_op){
		this.can_op = can_op;
	}
	


	public ELUser getEu() {
		return eu;
	}

	public void setEu(ELUser eu) {
		this.eu = eu;
	}

	public Department getDt() {
		return dt;
	}

	public void setDt(Department dt) {
		this.dt = dt;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public boolean isCan_op() {
		return can_op;
	}
	public void setCan_op(boolean can_op) {
		this.can_op = can_op;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getDepid() {
		return depid;
	}
	public void setDepid(int depid) {
		this.depid = depid;
	}

//	public String getUserids() {
//		return userids;
//	}
//
//	public void setUserids(String userids) {
//		this.userids = userids;
//	}
//
//	public String getDepids() {
//		return depids;
//	}
//
//	public void setDepids(String depids) {
//		this.depids = depids;
//	}
//	
	
	
	
}
