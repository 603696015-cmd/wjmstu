package com.sopia.intelligentTutoringPoints.dao;

import com.sopia.common.ElException;

/**
 * ÖÇÄÜ¸¨µ¼·ÖÑ§Ï°Ï°¹ßdao
 * @author TMK
 *
 */
public interface IntelligentProportionDao {
	/**
	 * ¸´Ìý
	 * @param userid
	 * @param myExamPaperid
	 * @param examPaperid
	 * @param blockid
	 * @param questionid
	 * @param classid
	 * @param courseid
	 * @param pageid
	 * @param roomid
	 * @throws ElException
	 */
	public void intelligentProportion(int userid,int myExamPaperid,int examPaperid,int blockid,int questionid,int classid,int courseid,int pageid,int roomid,int qtype) throws ElException;

	/**
	 * Â¼Òô
	 * @param userid
	 * @param myExamPaperid
	 * @param examPaperid
	 * @param blockid
	 * @param questionid
	 * @param classid
	 * @param courseid
	 * @param pageid
	 * @param roomid
	 * @throws ElException
	 */
	public void intelligentRecoding(int userid,int myExamPaperid,int examPaperid,int blockid,int questionid,int classid,int courseid,int pageid,int roomid) throws ElException;
}
