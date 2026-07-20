package com.sopia.simulation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 看图选题
 * @author zahj
 *
 */
public class Paper implements Serializable{

	private String id;
	
	private String serialNum;
	
	private String title;
	
	/**
	 * 1看图选题，2.xxx
	 */
	private String type;
	
	/**
	 * 试题类型
	 */
	private String quesType;
	
	/**
	 * 隐藏属性只限于阅读题
	 */
	private String hiddenType;
	
	private String[]options;
	
	private String mp3Url;
	
	private String imageUrl;
	
	private String score;
	
	/**
	 * 正确答案
	 */
	private String answer;
	
	/**
	 * 是否为听力题型
	 */
	private boolean isAudio;
	
	private List<Paper> childQuestion;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public String getMp3Url() {
		return mp3Url;
	}

	public void setMp3Url(String mp3Url) {
		this.mp3Url = mp3Url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public boolean isAudio() {
		return isAudio;
	}

	public void setAudio(boolean isAudio) {
		this.isAudio = isAudio;
	}

	public String getQuesType() {
		return quesType;
	}

	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}

	public List<Paper> getChildQuestion() {
		return childQuestion;
	}

	public void setChildQuestion(List<Paper> childQuestion) {
		this.childQuestion = childQuestion;
	}

	public String getHiddenType() {
		return hiddenType;
	}

	public void setHiddenType(String hiddenType) {
		this.hiddenType = hiddenType;
	}
	
	
}
