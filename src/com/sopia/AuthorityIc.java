package com.sopia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sopia.common.AuthorityUtil;
import com.sopia.common.DBConnection;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.NavigationUtil;
import com.sopia.common.OnlineStuffUtil;
import com.sopia.common.OnlineUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.register.EP;
import com.sopia.common.register.MACID;
import com.sopia.common.register.Register;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.schedule.TagsUtil;

public class AuthorityIc extends AbstractInterceptor  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2202362319254788424L;
	private static final Log logger = LogFactory.getLog(AuthorityIc.class);
	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	public void destroy() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		long l = System.currentTimeMillis();
		String f = null;
		try {
			if (!SystemConfOp.getBooleanValue(ElConstants.STUFF_OP)) {
				HttpServletRequest req = (HttpServletRequest) request;
				req.setCharacterEncoding("UTF-8");
				if(!checkReg(req)){
					Register.checkReg();
					return "error";
				}
				String pathinfo = req.getPathInfo();
				String servletPath = pathinfo == null ? req.getServletPath()
						: pathinfo;
				String funcCode = servletPath.substring(1, servletPath
						.lastIndexOf("."));
				funcCode = funcCode.substring(funcCode.indexOf("/") + 1,
						funcCode.length());
				f = funcCode;
				//某些action的话拼凑HTML
				if(TagsUtil.checkFunccodeIsHTMLAction(funcCode)){
					funcCode = "html_" + funcCode + ".shtm";
				}
				// 位置导航需要
				ElFunc ef = NavigationUtil.getElfunc(funcCode);
				if (ef != null)
					req.getSession().setAttribute("navfunc", ef);
				Integer role = 7;
				Integer userid = 0;
				if (null != req.getSession().getAttribute(
						ElConstants.SESSION_ROLE)) {
					role = (Integer) req.getSession().getAttribute(
							ElConstants.SESSION_ROLE);
				}
				if (null != req.getSession().getAttribute(
						ElConstants.SESSION_USERID)) {
					userid = (Integer) req.getSession().getAttribute(
							ElConstants.SESSION_USERID);
				}
				if (role == 7
						&& !AuthorityUtil.checkAuthor(role, funcCode, userid)) {
					request.setAttribute("elmessage", "您没登录或您的登录信息已丢失~");
					boolean b = httpRequestDeviceUtils.isMobileDevice(request);
					if(b==true){
						System.out.println("进入authorityic：wsj_phone");
						return "login_phone"; 
					}

					return "login";
				}
				String sessionid = OnlineUtil.getUserSessionid(userid + "");
				HttpSession session = req.getSession();
				if (null != sessionid && !sessionid.equals(session.getId())) {
					request.setAttribute("elmessage", "您的登录信息已丢失~");
					session.removeAttribute(ElConstants.SESSION_USERID);
					session.removeAttribute(ElConstants.SESSION_USERNAME);
					session.removeAttribute(ElConstants.SESSION_REALNAME);
					session.removeAttribute(ElConstants.SESSION_ROLE);
					session.removeAttribute(ElConstants.SESSION_ROLENAME);
					session.removeAttribute(ElConstants.SESSION_MYDEPARTMENT);
					session.removeAttribute(ElConstants.SESSION_AGE);
					boolean b = httpRequestDeviceUtils.isMobileDevice(request);
					if(b==true){
						System.out.println("进入authorityic：wsj_phone");
						return "login_phone"; 
					}
					return "login";
				}
				String result="";
				if (AuthorityUtil.checkAuthor(role, funcCode, userid)) {
					DBConnection.getConnection().setAutoCommit(false);
					result = invocation.invoke() ;
					DBConnection.getConnection().commit();
					 logger.error(f + "处理时间：" + (System.currentTimeMillis() -
					 l)
					 + "毫秒");
					return result;
				} else {
					request.setAttribute("elmessage", "您无权访问~！请与管理员联系");
					return "error";
				}
			} else {// 资源服务器管理部分--
				HttpServletRequest req = (HttpServletRequest) request;
				req.setCharacterEncoding("UTF-8");
				String servletPath = req.getPathInfo() == null ? req
						.getServletPath() : req.getPathInfo();
				String funcCode = servletPath.substring(1, servletPath
						.lastIndexOf("."));
				funcCode = funcCode.substring(funcCode.indexOf("/") + 1,
						funcCode.length());
				if (funcCode.indexOf("question_stuff") <= -1
						&& funcCode.indexOf("mess_sendUserlistInit") <= -1
						&& funcCode.indexOf("mess_sendUserlist") <= -1
						&& funcCode.indexOf("mess_getUserInfo") <= -1) {
					request.setAttribute("elmessage", "本系统为资源服务器，您无权访问其他资源~！");
					return "error";
				}
				Integer role = 7;
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				UserDao userdao = (UserDao) SpringContextUtil
						.getBean("userDao");
				if ((username != null && !"".equals(username))
						|| req.getSession().getAttribute("userId") == null
						|| Integer.parseInt(req.getSession().getAttribute(
								"userId").toString()) <= 0) {
					// 登录
					if (!userdao.check(username, password)) {
						request.setAttribute("elmessage",
								"账号密码不一致，无法进入资源服务器，请联系管理员！");
						return "error";
					} else {
						ELUser elUser = userdao.query(username);
						req.getSession().setAttribute(
								ElConstants.SESSION_USERID, elUser.getId());
						req.getSession().setAttribute(
								ElConstants.SESSION_USERNAME,
								elUser.getUsername());
						req.getSession().setAttribute(
								ElConstants.SESSION_REALNAME,
								elUser.getRealname());
						req.getSession().setAttribute(ElConstants.SESSION_ROLE,
								elUser.getRole().getId());
						req.getSession().setAttribute(
								ElConstants.SESSION_ROLENAME,
								elUser.getRole().getName());
						String stuffcode = req.getParameter("stuffcode");
						req.getSession().setAttribute("stuffcode", stuffcode);
						OnlineStuffUtil.setStuffcode(elUser.getId(), stuffcode);
					}
				}
				int userid = Integer.parseInt(req.getSession().getAttribute(
						ElConstants.SESSION_USERID).toString());
				role = Integer.parseInt(req.getSession().getAttribute(
						ElConstants.SESSION_ROLE).toString());
				if (!OnlineStuffUtil
						.checkStuffcode(userid + "", req.getSession()
								.getAttribute("stuffcode") == null ? "" : req
								.getSession().getAttribute("stuffcode")
								.toString())) {
					request.setAttribute("elmessage", "该账号在其他地方使用，请重新进入！");
					return "error";
				}
				if (role == 7
						&& !AuthorityUtil.checkAuthor(role, funcCode, userid)) {
					request.setAttribute("elmessage", "您没登录或您的登录信息已丢失~");
					boolean b = httpRequestDeviceUtils.isMobileDevice(request);
					if(b==true){
						System.out.println("进入authorityic：wsj_phone");
						return "login_phone"; 
					}
					return "login";
				}
				String result = "";
				if (AuthorityUtil.checkAuthor(role, funcCode, userid)) {
					DBConnection.getConnection().setAutoCommit(false);
					result = invocation.invoke() ;
					DBConnection.getConnection().commit();
					// logger.error(f + "处理时间：" + (System.currentTimeMillis() -
					// l)
					// + "毫秒");
					return result;
				} else {

					request.setAttribute("elmessage", "您无权访问~！请与管理员联系");
					
					return "error";
				}
			}
		} catch (Exception e) {
			logger.error("==系统错误==处理失败，回滚", e);
			try {
				DBConnection.getConnection().rollback();
			} catch (Exception ee) {
				logger.error("系统错误--回滚数据库失败");
			}
			request.setAttribute("elmessage", "系统错误，请与管理员联系-" + e.getMessage());
			
			return "eLexception";
		} finally {
			try {
				DBConnection.getConnection().close();
			} catch (Exception ee) {
				logger.error("系统错误--关闭数据库失败");
			}
//			logger.info("------关闭数据库");
			DBConnection.setNull();
		}

	}
	private boolean checkReg(HttpServletRequest req)
			throws Exception {
		int regstat = Register.status;
		if (regstat == Register.STATUS_NOFILE) {
			req.setAttribute("elmessage", "该系统没有授权！请与生产厂商或代理商联系！<br>"
					+ "您的注册码是：" + EP.ep(MACID.getMacAddress()) + "<br>");
			// req.getRequestDispatcher("error.jsp").forward(req, response);
			return false;
		}
		if (regstat == Register.STATUS_MACNOTRIGHT) {
			req.setAttribute("elmessage", "该系统授权信息不正确！请与生产厂商或代理商联系！<br>"
					+ "您的注册码是：" + EP.ep(MACID.getMacAddress()) + "<br>");
			// req.getRequestDispatcher("error.jsp").forward(req, response);
			return false;
		}
		if (regstat == Register.STATUS_TIMEOUT) {
			req.setAttribute("elmessage", "该系统试用期已过！如果需要继续使用，请与生产厂商或代理商联系！<br>"
					+ "您的注册码是：" + EP.ep(MACID.getMacAddress()) + "<br>");
			// req.getRequestDispatcher("error.jsp").forward(req, response);
			return false;
		}
		return true;
	}

	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}

	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}
}
