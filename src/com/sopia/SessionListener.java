package com.sopia;

import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.DBConnection;
import com.sopia.common.OnlineUtil;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.UserDao;

/**
 * @author puderty
 */
public class SessionListener implements HttpSessionListener,
		SSLSessionBindingListener, HandshakeCompletedListener {
	// 获取application对象
	private static final Log logger = LogFactory.getLog(SessionListener.class);

	Set<String> online = new HashSet<String>(); // 存放在线已注册用户的用户名

	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		if (arg0.getSession().getAttribute("userId") != null) {
			int userid = Integer.parseInt(arg0.getSession().getAttribute(
					"userId")
					+ "");
			// arg0.getSession().removeAttribute(ElConstants.SESSION_USERID);
			// arg0.getSession().removeAttribute(ElConstants.SESSION_USERNAME);
			// arg0.getSession().removeAttribute(ElConstants.SESSION_REALNAME);
			// arg0.getSession().removeAttribute(ElConstants.SESSION_ROLE);
			// arg0.getSession().removeAttribute(ElConstants.SESSION_ROLENAME);
			// arg0.getSession().removeAttribute(ElConstants.SESSION_MYDEPARTMENT);
			// arg0.getSession().removeAttribute(ElConstants.SESSION_AGE);
			// 记录用户退出信息
			try {
				((UserDao) SpringContextUtil.getBean("userDao"))
						.updateSessionUserExittime(userid);
				OnlineUtil.removeOnlineUser(userid+"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("用户退出出错！");
			} finally {
				try {
					DBConnection.getConnection().close();
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					DBConnection.setNull();
				}
			}
		}
	}

	public void valueBound(SSLSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void valueUnbound(SSLSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void handshakeCompleted(HandshakeCompletedEvent event) {
		// TODO Auto-generated method stub
	}
}
