package com.sopia.courseman.entities;

import java.sql.Timestamp;

import com.sopia.common.CheckHtml;
import com.sopia.duman.entities.ELUser;

public class CourseAuditDescribes {
	private int id;
	private String title;
	private String content;
	private String replycontent;
	private Course course;
	private Timestamp submittime;
	private Timestamp feedbacktime;
	private ELUser creater;
	private int status ;
	
	private float score;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public CourseAuditDescribes() {
	}

	public CourseAuditDescribes(int id, String content, Timestamp submittime,
			Timestamp feedbacktime) {
		this.id = id;
		this.content = content;
		this.submittime = submittime;
		this.feedbacktime = feedbacktime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public ELUser getCreater() {
		return creater;
	}

	public void setCreater(ELUser creater) {
		this.creater = creater;
	}

	public String getShotContent() {
		if (null != content)
			content = CheckHtml.getString(content);
		String s = (null == content || "".equals(content.trim())) ? "ÎÞÄÚÈÝ~"
				: (content.length() > 20 ? content.substring(0, 20) + "..."
						: content);
		return s;
	}
	public int getContentLen() {
		if (null == content)
			return 0;
		return content.length();
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Timestamp submittime) {
		this.submittime = submittime;
	}

	public Timestamp getFeedbacktime() {
		return feedbacktime;
	}

	public void setFeedbacktime(Timestamp feedbacktime) {
		this.feedbacktime = feedbacktime;
	}

	public String getReplycontent() {
		return replycontent;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
}
