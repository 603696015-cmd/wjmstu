package com.sopia.talentman.entities;

import java.sql.Timestamp;

import com.sopia.questionman.entities.ExamPaper;
/**
 * 客观场次
 * @author Administrator
 *
 */
public class KTRoom {
	private int id;
	private String title;
	private String description;

	private KTRoomColl trcoll;

	private ExamPaper exampaper;
	private Timestamp begintime;
	private Timestamp endtime;


	public KTRoom() {
	}

	public KTRoom(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

 

	public ExamPaper getExampaper() {
		return exampaper;
	}

	public void setExampaper(ExamPaper exampaper) {
		this.exampaper = exampaper;
	}



	public KTRoomColl getTrcoll() {
		return trcoll;
	}

	public void setTrcoll(KTRoomColl trcoll) {
		this.trcoll = trcoll;
	}

	public Timestamp getBegintime() {
		return begintime;
	}

	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
