package com.sopia.courseman.entities;

import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElNodeSQL;
import com.sopia.duman.entities.ELUser;

public class ClassPara {
	private ElClass elClass;
	private ELUser elUser;
	private int isPassed;//是否通过
	private float sumScoreStart;//总学分开始
	private float sumScoreEnd;//总学分结束
	private float bsumScoreStart;//必修总学分开始
	private float bsumScoreEnd;//必修总学分结束
	private float xsumScoreStart;//选修总学分开始
	private float xsumScoreEnd;//选修总学分结束
	private String linkTerm;//培训班与培训班之间的连接条件
	public String getLinkTerm() {
		return linkTerm;
	}
	public void setLinkTerm(String linkTerm) {
		this.linkTerm = linkTerm;
	}
	public ELUser getElUser() {
		return elUser;
	}
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	public ElClass getElClass() {
		return elClass;
	}
	public void setElClass(ElClass elClass) {
		this.elClass = elClass;
	}
	public float getSumScoreStart() {
		return sumScoreStart;
	}
	public void setSumScoreStart(float sumScoreStart) {
		this.sumScoreStart = sumScoreStart;
	}
	public float getSumScoreEnd() {
		return sumScoreEnd;
	}
	public void setSumScoreEnd(float sumScoreEnd) {
		this.sumScoreEnd = sumScoreEnd;
	}
	public float getBsumScoreStart() {
		return bsumScoreStart;
	}
	public void setBsumScoreStart(float bsumScoreStart) {
		this.bsumScoreStart = bsumScoreStart;
	}
	public float getBsumScoreEnd() {
		return bsumScoreEnd;
	}
	public void setBsumScoreEnd(float bsumScoreEnd) {
		this.bsumScoreEnd = bsumScoreEnd;
	}
	public float getXsumScoreStart() {
		return xsumScoreStart;
	}
	public void setXsumScoreStart(float xsumScoreStart) {
		this.xsumScoreStart = xsumScoreStart;
	}
	public float getXsumScoreEnd() {
		return xsumScoreEnd;
	}
	public void setXsumScoreEnd(float xsumScoreEnd) {
		this.xsumScoreEnd = xsumScoreEnd;
	}
	
	public int getIsPassed() {
		return isPassed;
	}
	public void setIsPassed(int isPassed) {
		this.isPassed = isPassed;
	}
	
	public String getTermSql(){
		String tempStr="";
		if(this.getIsPassed()==2){
			tempStr=" and certificateno is null ";
		}else if(this.getIsPassed()==1){
			tempStr=" and certificateno is not null ";
		}
		return " (select euu.* from " +
				"(select * from study_class where classid="+this.getElClass().getId()+" and tcredit>="+this.sumScoreStart+" and tcredit<="+this.sumScoreEnd+" and bcredit>="+this.bsumScoreStart+" and bcredit<="+this.bsumScoreEnd+" and xcredit>="+this.xsumScoreStart+" and xcredit<="+this.xsumScoreEnd+" "+tempStr+") scl " +
				" left join (select eu.id euid,eu.username,eu.realname,dep.id depid,dep.name depname,el.id elid,el.name elname,eu.sex,eu.jingzhong,eu.shengri,dep.lid,dep.rid from eluser eu left join department dep on eu.depid=dep.id left join elrole el on eu.role=el.id) euu " +
				" on scl.userid=euu.euid where "+ElNodeSQL.getWhereSql_use(this.getElUser().getDepartment().getId(),this.getElUser().getId(),"euu")+") ";
	}
}
