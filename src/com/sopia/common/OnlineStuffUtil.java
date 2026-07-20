package com.sopia.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OnlineStuffUtil {
	public final static Map<String, String> online = new HashMap<String,String>();
	private static final Log logger = LogFactory.getLog(OnlineStuffUtil.class);
	public synchronized static boolean checkStuffcode(String userid,String stuffcode) {
		if (!online.containsKey(userid))
			return false;
		try {
			String stuffcode1 = online.get(userid);
			if (null != stuffcode1
					&& stuffcode1.equals(stuffcode)) {
					return true;
			}
		} catch (Exception e) {
			logger.error("检查资源服务器，用户是否登录",e);
			return false;
		}

		return false;
	}
	public static void setStuffcode(int userid ,String stuffcode){
		online.put(userid+"", stuffcode);
	}
}
