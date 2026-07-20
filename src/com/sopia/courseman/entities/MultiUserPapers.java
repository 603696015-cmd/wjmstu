package com.sopia.courseman.entities;
import com.sopia.duman.entities.ELUser;

public class MultiUserPapers {
	private ELUser elUser;
//	private int sqid ;
//	private int blockid;
//	private int qid ;	
	private float score;
	/**
	 * @return the elUser
	 */
	public ELUser getElUser() {
		return elUser;
	}
	/**
	 * @param elUser the elUser to set
	 */
	public void setElUser(ELUser elUser) {
		this.elUser = elUser;
	}
	/**
	 * @return the score
	 */
	public float getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(float score) {
		this.score = score;
	}
}
