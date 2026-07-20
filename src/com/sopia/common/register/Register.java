package com.sopia.common.register;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 授权验证功能
 * @author Administrator
 *
 */
public class Register implements Runnable {
	private static final Log logger = LogFactory.getLog(Register.class);
	public static final int STATUS_NOFILE = -1;
	public static final int STATUS_MACNOTRIGHT = 1;
	public static final int STATUS_ALLRIGHT = 0;
	public static final int STATUS_TIMEOUT = 2;
	public static final int STATUS_SYSERROR = -2;

	public static int status;

	public static void init() {
		Thread t = new Thread(new Register());
		t.start();
		status=0;
	}

	public void run() {
		while (true) {
			logger.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date())
					+ "检查产品是否注册！");
			//checkReg();
			try {
				Thread.sleep(60*60*1000L);
			} catch (Exception e) {
				status = STATUS_SYSERROR;
				logger.error("产品注册检查 睡眠失败！");
			}

		}
	}

	public static void checkReg() {
		try {
			String path = Register.class.getResource("").toString();
			path = path.substring(6, path.length() - 43);
			File theFile = new File(path + "/WEB-INF/config/license.inc");
			status = STATUS_ALLRIGHT ;
			if (!theFile.exists()) {
				logger.error("检查产品出错了=文件不存在");
				status = STATUS_NOFILE;
				return;
			}
			String registerInfo = EP.unepFromFile(path + "/WEB-INF/config/license.inc");
			String sss[] = registerInfo.split("=.=");
			String macadd = EP.unep(sss[0]);
			String macAddress = MACID.getMacAddress();
			/*if (!macAddress.equals(macadd)) {
				logger.error("mac地址不符合");
				status = STATUS_MACNOTRIGHT;
				return;
			}*/
			long startD = new Long(sss[1]);
			long timeOut = new Long(sss[2]);
			long nowD = System.currentTimeMillis();
			logger.error(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startD))+"=="+(timeOut/1000/60/60/24));
			if (timeOut != -1 && (nowD - startD) > timeOut) {
				logger.error("使用过期了!");
				status = STATUS_TIMEOUT;
				return ; 
			}
		} catch (Exception e) {
			status = STATUS_SYSERROR;
			logger.error("检查产品出错了", e);
		}
	}
	
}
