package com.sopia.intelligentTutoringPoints.dao;

import java.sql.Timestamp;

import com.sopia.common.ElException;
import com.sopia.intelligentTutoringPoints.entities.IntelligentLogin;
/**
 * 智能辅导分登录dao
 * @author TMK
 *
 */
public interface IntelligentLoginDao {
	/**
	 * 最近一次登录时间
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public Timestamp getLastLoginTime(int userid) throws ElException;
	/**
	 * 登录加分减分
	 * @param userid
	 * @param notLogin3day
	 * @param classid
	 * @return
	 * @throws ElException
	 */
	public IntelligentLogin intelligentLogin(int userid,boolean notLogin3day,int classid) throws ElException;
	/**
	 * 退出登录
	 * @param userid
	 * @param loginId
	 * @throws ElException
	 */
	public void intelligentLoginOut(int userid,int loginId) throws ElException;
	
	/**
	 * 获取一条登录信息
	 * @param loginid
	 * @return
	 * @throws ElException
	 */
	public IntelligentLogin getLoginInfoByLoginid(int loginid) throws ElException;

}
