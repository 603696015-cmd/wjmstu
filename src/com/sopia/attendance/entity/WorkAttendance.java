package com.sopia.attendance.entity;

import java.sql.Timestamp;

import com.sopia.common.ElException;
import com.sopia.duman.dao.impl.DepartmentDaoImpl;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.ELUser;

/**
 * 我的考勤列表entity
 * @author Administrator
 *
 */
public class WorkAttendance {
	
	private int id;
	private int userid;
	private String realname;
	private int depid;
	private String depname;
	private Timestamp riqi;//日期
	private Timestamp signdaotime;//签到时间
	private Timestamp signtuitime;//签退时间
	private String relateleave;//相关请假
	private String relateout;//相关外出
	private String relateretroactive;//相关补签
	private String relateretroactive_type;//补签类型
	private String leaveType;//请假性质
	private String result;//结果
	private String mark;//备注
	
	public WorkAttendance(){
		
	}
	
	public WorkAttendance(int userid){
		this.userid = userid;
		try {
			ELUser eluser = new UserDaoImpl().getUserById(userid);
			this.realname = eluser.getRealname();
			this.depname = new DepartmentDaoImpl().getDepById(eluser.getDepartment().getId()).getName();
		} catch (ElException e) {
			e.printStackTrace();
		}
	}
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getDepid() {
		return depid;
	}
	public void setDepid(int depid) {
		this.depid = depid;
	}
	
	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getRelateretroactive_type() {
		return relateretroactive_type;
	}
	public void setRelateretroactive_type(String relateretroactive_type) {
		this.relateretroactive_type = relateretroactive_type;
	}
	public String getRelateout() {
		return relateout;
	}
	public void setRelateout(String relateout) {
		this.relateout = relateout;
	}
	public String getRelateretroactive() {
		return relateretroactive;
	}
	public void setRelateretroactive(String relateretroactive) {
		this.relateretroactive = relateretroactive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Timestamp getRiqi() {
		return riqi;
	}
	public void setRiqi(Timestamp riqi) {
		this.riqi = riqi;
	}
	
	public Timestamp getSigndaotime() {
		return signdaotime;
	}
	public void setSigndaotime(Timestamp signdaotime) {
		this.signdaotime = signdaotime;
	}
	public Timestamp getSigntuitime() {
		return signtuitime;
	}
	public void setSigntuitime(Timestamp signtuitime) {
		this.signtuitime = signtuitime;
	}
	public String getRelateleave() {
		return relateleave;
	}
	public void setRelateleave(String relateleave) {
		this.relateleave = relateleave;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	

}
