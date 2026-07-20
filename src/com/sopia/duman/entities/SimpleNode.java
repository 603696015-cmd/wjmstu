package com.sopia.duman.entities;


public class SimpleNode {
	private int id;
	private String name;
	private SimpleNode parent ;
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
	public SimpleNode getParent() {
		return parent;
	}
	public void setParent(SimpleNode parent) {
		this.parent = parent;
	}
	public SimpleNode(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public SimpleNode() {
	}
	public SimpleNode(int id) {
		this.id = id;
	}
}
