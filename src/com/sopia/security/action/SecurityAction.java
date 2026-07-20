package com.sopia.security.action;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.security.dao.SecurityDao;
import com.sopia.security.entity.SecurityBindIp;
import com.sopia.security.util.SecurityUtil;

public class SecurityAction extends BaseAction{
	private SecurityDao securityDao;
	private SecurityBindIp securityBindIp;
	private String[] ip_start_array;
	private String[] ip_end_array;
	
	
	public String securitysetInit() throws ElException{
		
		securityBindIp = securityDao.getSecurityBindIpByRoleid(getSessionIntValue(ElConstants.SESSION_ROLE));
		
		securityBindIp = securityBindIp != null ?
				securityBindIp:
					new SecurityBindIp(getSessionIntValue(ElConstants.SESSION_ROLE),0);
		if(securityBindIp.getIp_start() != null && !securityBindIp.getIp_start().equals("")){
			ip_start_array = new SecurityUtil().getStringArrayFromString(securityBindIp.getIp_start(),"_");
		}
		if(securityBindIp.getIp_end() != null && !securityBindIp.getIp_end().equals("")){
			ip_end_array = new SecurityUtil().getStringArrayFromString(securityBindIp.getIp_end(),"_");
		}
		return "securitysetInit";
	}

	public String securityset() throws ElException{
		//判断是否是超级管理员
		if(getSessionIntValue(ElConstants.SESSION_ROLE) != 1){
			this.setElmessage("对不起，您不是超级管理员，不能进行IP绑定设置!!");
			return "error";
		}
		
		securityBindIp = securityBindIp == null?
				new SecurityBindIp(getSessionIntValue(ElConstants.SESSION_ROLE))
				:securityBindIp;
		if(securityBindIp.getIp_start_array() != null){
			securityBindIp.setIp_start(new SecurityUtil().getStringFromStringArray(securityBindIp.getIp_start_array(), "_"));
		}
		if(securityBindIp.getIp_end_array() != null){
			securityBindIp.setIp_end(new SecurityUtil().getStringFromStringArray(securityBindIp.getIp_end_array(), "_"));
		}
		SecurityBindIp securityBindIp_ = securityDao.getSecurityBindIpByRoleid(getSessionIntValue(ElConstants.SESSION_ROLE));
		
		if(securityBindIp_ == null){
			
			securityDao.inserSecurityBindIpByUser(getSessionIntValue(ElConstants.SESSION_ROLE), securityBindIp);
		}else {
			securityDao.updateSecurityBindIpByUser(getSessionIntValue(ElConstants.SESSION_ROLE), securityBindIp);
		}
		
		return "securityset_success";
	}

	public SecurityDao getSecurityDao() {
		return securityDao;
	}


	public void setSecurityDao(SecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public SecurityBindIp getSecurityBindIp() {
		return securityBindIp;
	}

	public void setSecurityBindIp(SecurityBindIp securityBindIp) {
		this.securityBindIp = securityBindIp;
	}

	public String[] getIp_start_array() {
		return ip_start_array;
	}

	public void setIp_start_array(String[] ip_start_array) {
		this.ip_start_array = ip_start_array;
	}

	public String[] getIp_end_array() {
		return ip_end_array;
	}

	public void setIp_end_array(String[] ip_end_array) {
		this.ip_end_array = ip_end_array;
	}
	
	
	
	

}
