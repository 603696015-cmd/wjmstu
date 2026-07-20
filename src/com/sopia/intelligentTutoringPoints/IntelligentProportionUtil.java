package com.sopia.intelligentTutoringPoints;

import com.sopia.common.ElException;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.intelligentTutoringPoints.dao.IntelligentProportionDao;

/**
 * ÖÇÄÜ¸¨µ¼·ÖÖ®Ñ§Ï°Ï°¹ßUtil
 * @author TMK
 *
 */
public class IntelligentProportionUtil {
	public final static IntelligentProportionDao intelligentProportionDao = (IntelligentProportionDao)SpringContextUtil.getBean(IntelligentTutoringPointsConstants.INTELLIGENT_PROPORTION);

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
	 * @param qtype
	 * @throws ElException
	 */
	public static void intelligentProportion(int userid,int myExamPaperid,int examPaperid,int blockid,int questionid,int classid,int courseid,int pageid,int roomid,int qtype) throws ElException{
		intelligentProportionDao.intelligentProportion(userid,myExamPaperid,examPaperid,blockid,questionid,classid,courseid,pageid,roomid,qtype);
	}
	
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
	public static void intelligentRecoding(int userid,int myExamPaperid,int examPaperid,int blockid,int questionid,int classid,int courseid,int pageid,int roomid) throws ElException{
		intelligentProportionDao.intelligentRecoding(userid,myExamPaperid,examPaperid,blockid,questionid,classid,courseid,pageid,roomid);
	}
}
