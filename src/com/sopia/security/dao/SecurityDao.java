package com.sopia.security.dao;

import com.sopia.common.ElException;
import com.sopia.security.entity.SecurityBindIp;

public interface SecurityDao {
	
	/**
	 * 根据userid获取用户ip绑定信息
	 * @param userid
	 * @return
	 * @throws ElException
	 */
	public SecurityBindIp getSecurityBindIpByRoleid(int roleid) throws ElException;
	
	/**
	 * 根据userid添加用户ip绑定
	 * @param userid
	 * @param SecurityBindIp
	 * @throws ElException
	 */
	public void inserSecurityBindIpByUser(int roleid,SecurityBindIp securityBindIp) throws ElException;
	
	/**
	 * 根据userid修改绑定信息
	 * @param userid
	 * @param securityBindIp
	 * @throws ElException
	 */
	public void updateSecurityBindIpByUser(int roleid,SecurityBindIp securityBindIp) throws ElException;

}
