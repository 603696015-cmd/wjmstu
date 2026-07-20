package com.sopia.courseman.entities;
public class ErepBlock {
	private int id;
	private String title;
	private String blocktitles;
	private String blockids;
	private EroomBlock erblock;
	public ErepBlock( int id ,String title) {
		this.id = id;
		this.title= title;
	}
	public ErepBlock() {
	}
	public ErepBlock(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBlocktitles() {
		return blocktitles;
	}
	public void setBlocktitles(String blocktitles) {
		this.blocktitles = blocktitles;
	}
	public String getBlockids() {
		return blockids;
	}
	public void setBlockids(String blockids) {
		this.blockids = blockids;
	}
	public EroomBlock getErblock() {
		return erblock;
	}
	public void setErblock(EroomBlock erblock) {
		this.erblock = erblock;
	}
}
