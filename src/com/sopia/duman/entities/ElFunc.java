package com.sopia.duman.entities;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.sopia.common.SystemConfOp;

public class ElFunc {
	private ElRole role;
	@Expose
	private int id;
	@Expose
	private String funccode;
	@Expose
	private String name;
	@Expose
	private String description;
	private List<ElFunc > child;
	private int level;
	@Expose
	private ElFunc parent ;
	private boolean needCheck;
	@Expose
	private String params;
	private String target;
	private ELUser elUser;
	
	
	private int count;//存储该树的深度为1的节点的数量
	@Expose
	private String dyimg;//对应图片
	@Expose
	private String bgimg;//背景图片
	@Expose
	private String linkimg;//链接图片
	
	private int classCount;
	
	public int getClassCount() {
		return classCount;
	}

	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}

	public String getDyMainimg() {
		return SystemConfOp.getStuffUrl() + dyimg;
	}

	public String getBgMainimg() {
		return SystemConfOp.getStuffUrl() + bgimg;
	}

	public String getLinkMainimg() {
		return SystemConfOp.getStuffUrl() + linkimg;
	}


	public String getDyimg() {
		return dyimg;
	}
	
	public void setDyimg(String dyimg) {
		this.dyimg = dyimg;
	}

	public String getBgimg() {
		return bgimg;
	}

	public void setBgimg(String bgimg) {
		this.bgimg = bgimg;
	}

	public String getLinkimg() {
		return linkimg;
	}

	public void setLinkimg(String linkimg) {
		this.linkimg = linkimg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean getNeedCheck() {
		return needCheck;
	}

	public void setNeedCheck(boolean needCheck) {
		this.needCheck = needCheck;
	}

	public ElFunc getParent() {
		return parent;
	}

	public void setParent(ElFunc parent) {
		this.parent = parent;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ElRole getRole() {
		return role;
	}

	public void setRole(ElRole role) {
		this.role = role;
	}

	public String getFunccode() {
		return funccode;
	}

	public void setFunccode(String funccode) {
		this.funccode = funccode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ElFunc> getChild() {
		return child;
	}

	public void setChild(List<ElFunc> child) {
		this.child = child;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ElFunc() {
	}

	public ElFunc(int id) {
		this.id = id;
	}

	public ELUser getElUser() {
		return elUser;
	}

	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	
}
