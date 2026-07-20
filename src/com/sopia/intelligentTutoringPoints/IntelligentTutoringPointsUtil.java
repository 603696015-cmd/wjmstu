package com.sopia.intelligentTutoringPoints;

import com.sopia.classman.entities.ElClass;
import com.sopia.common.ElException;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.intelligentTutoringPoints.dao.IntelligentTutoringPointsDao;
import com.sopia.peixunBatch.dao.PeixunBatchDao;
import com.sopia.peixunBatch.entities.PeixunBatch;



/**
 * 智能辅导分Util
 * @author TMK
 *
 */
public class IntelligentTutoringPointsUtil {
	public final static IntelligentTutoringPointsDao intelligentTutoringPointsDao = (IntelligentTutoringPointsDao)SpringContextUtil.getBean(IntelligentTutoringPointsConstants.INTELLIGENT_TUTORING_POINTS);;
	public final static PeixunBatchDao peixunBatchDao = (PeixunBatchDao)SpringContextUtil.getBean(IntelligentTutoringPointsConstants.PEIXUNBATCHDAO);
	
	/**
	 * 获取用户当前等级的智能辅导分
	 * @param userid
	 * @return
	 * @throws ElException 
	 */
	public static float intelligentTutoringPoints(int userid) throws ElException {
		float points = 0.0f;
		//判断是否有正在学习的培训班
		PeixunBatch peixunBatch = peixunBatchDao.getPeixunBatchById(1);
		ElClass elClass = peixunBatchDao.getDoneOrNowElClass(peixunBatch.getId(),userid,0);
		if(elClass!=null&&elClass.getId()>0){
			points = intelligentTutoringPointsDao.getPoints(userid,elClass.getId());
		}
		return points;
	}

}
