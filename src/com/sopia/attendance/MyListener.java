package com.sopia.attendance;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {

	private Timer timer = null;

	
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
		sce.getServletContext().log("¶¨Ê±Æ÷Ïú»Ù");
	}

	public void contextInitialized(ServletContextEvent sce) {
		new TimeManager();
	}

}
