package com.sopia.common;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.FuncDao;
import com.sopia.duman.entities.ElFunc;

public class NavigationUtil {
	private static final Log logger = LogFactory.getLog(NavigationUtil.class);

	private static Map<String, ElFunc > navigates;

	public static void load() {
		try {
			FuncDao rd = (FuncDao) SpringContextUtil.getBean("funcDao");
			navigates = rd .listFuncNavs();
			logger.info("加载导航成功");
		} catch (Exception e) {
			logger.error("加载导航失败！", e);
		}

	}
	public static ElFunc getElfunc(String funccode){
		if (null == navigates)
			load();
		return navigates.get(funccode);
	}
}
