package com.sopia.courseman.entities;

import java.sql.Timestamp;

import com.sopia.common.CheckHtml;
import com.sopia.duman.entities.ELUser;

public class CourseNote {
	private int id;
	private String title;
	private String content;
	private Course course;
	private Timestamp createtime;
	private Timestamp modifytime;
	private ELUser creater;
	private int status ;
	
	private float score;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public CourseNote() {
	}

	public CourseNote(int id, String content, Timestamp createtime,
			Timestamp modifytime) {
		this.id = id;
		this.content = content;
		this.createtime = createtime;
		this.modifytime = modifytime;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Timestamp getModifytime() {
		return modifytime;
	}

	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
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
		String s = (null == content || "".equals(content.trim())) ? "ÎŞÄÚÈİ~"
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
}
