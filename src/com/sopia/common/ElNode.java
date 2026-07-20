package com.sopia.common;

import java.util.List;

import com.sopia.duman.entities.ELUser;
import com.sopia.questionman.entities.QuestionLib;



/**
 * 树型节点父类
 * @author Administrator
 *
 */
public class ElNode {
	
	private int id;
	private int level;
	private String preStr;
	private int lid;
	private int rid;
	private ElNode parent;
	private List<ELUser> elUsers;
	private List<ElNode> nchild;//提高代码重用(麻烦 要改好多子类)
	
 	
	public List<ElNode> getNchild() {
		return nchild;
	}
	public void setNchild(List<ElNode> nchild) {
		this.nchild = nchild;
	}
	public ElNode() {
	}
	public ElNode(int id) {
		this.id = id;
	}
	public ElNode getParent() {
		return parent;
	}
	public void setParent(ElNode parent) {
		this.parent = parent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPreStr() {
		preStr="";
		for (int i = 0; i < level; i++) {
			preStr += "....";
		}
		return preStr;
	}
	public void setPreStr(String preStr) {
		this.preStr = preStr;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public List<ELUser> getElUsers() {
		return elUsers;
	}
	public void setElUsers(List<ELUser> elUsers) {
		this.elUsers = elUsers;
	}
}
