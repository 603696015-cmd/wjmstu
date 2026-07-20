package com.sopia.lable.entites;

import java.util.List;

import com.google.gson.annotations.Expose;

public class TreeNode {
	@Expose
	private   String   			name;
	@Expose
	private	   boolean			open ;
	@Expose
	private   List<TreeNode>  	children;
	@Expose
	private   int				id;
	private   TreeNode			parent;
	private   int		        level;

	
	
	
	
	

	public TreeNode() {
		
		
	}
	public TreeNode(int id) {
		// TODO Auto-generated constructor stub
		this.id=id;
	}
	public TreeNode(int id,String name) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.name=name;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	
	

}
