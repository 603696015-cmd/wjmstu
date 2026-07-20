package com.sopia.courseman.entities;

import java.util.List;

import com.sopia.common.ElNode;
import com.sopia.duman.entities.ELUser;


public class EroomLib extends ElNode {
	private String name;
	private String description;
	private List<EroomLib> child;
	private List<ELUser> opusers;
	private List<ELUser> useusers;

	public List<ELUser> getOpusers() {
		return opusers;
	}
	public void setOpusers(List<ELUser> opusers) {
		this.opusers = opusers;
	}
	public List<EroomLib> getChild() {
		return child;
	}
	public void setChild(List<EroomLib> child) {
		this.child = child;
	}
	public EroomLib( int id ,String name) {
		super(id);
		this.name = name;
	}
	public EroomLib() {
	}
	public EroomLib(int id) {
		super(id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ELUser> getUseusers() {
		return useusers;
	}
	public void setUseusers(List<ELUser> useusers) {
		this.useusers = useusers;
	}
}
