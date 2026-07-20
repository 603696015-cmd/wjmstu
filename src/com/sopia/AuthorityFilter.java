package com.sopia;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.AuthorityUtil;
import com.sopia.common.DBConnection;
import com.sopia.common.ElQuerySql;
import com.sopia.common.HttpRequestDeviceUtils;
import com.sopia.common.IndexDataUtil;
import com.sopia.common.NavigationUtil;
import com.sopia.common.OnlineStuffUtil;
import com.sopia.common.OnlineUtil;
import com.sopia.common.SystemConfOp;
import com.sopia.common.quiz.EpQStatus;
import com.sopia.common.register.EP;
import com.sopia.common.register.MACID;
import com.sopia.common.register.Register;
import com.sopia.common.spring.SpringContextUtil;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElFunc;
import com.sopia.lable.common.LableCommon;

public class AuthorityFilter implements Filter {
	private static final Log logger = LogFactory.getLog(AuthorityFilter.class);
	private HttpRequestDeviceUtils httpRequestDeviceUtils;
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// long l = System.currentTimeMillis();
		// String f = "";
		try {
			if (!SystemConfOp.getBooleanValue(ElConstants.STUFF_OP)) {
				HttpServletRequest req = (HttpServletRequest) request;
				req.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				String pathinfo = req.getPathInfo();
				String servletPath = pathinfo == null ? req.getServletPath()
						: pathinfo;
				String funcCode = servletPath.substring(1, servletPath
						.lastIndexOf("."));
				funcCode = funcCode.substring(funcCode.indexOf("/") + 1,
						funcCode.length());
				// f = funcCode;
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
					boolean b = httpRequestDeviceUtils.isMobileDevice(req);
					if(b==true){
						req.getRequestDispatcher("/wsj_phone/login.jsp").forward(req,
								response);
					}
					req.getRequestDispatcher("/login.jsp").forward(req,
							response);
					return;
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
					boolean b = httpRequestDeviceUtils.isMobileDevice(req);
					if(b==true){
						System.out.println("进入wsj——phone");
						req.getRequestDispatcher("/wsj_phone/login.jsp").forward(req,
								response);
					}
					req.getRequestDispatcher("/login.jsp").forward(req,
							response);
					return;
				}
				if (AuthorityUtil.checkAuthor(role, funcCode, userid)) {
					DBConnection.getConnection().setAutoCommit(false);
					chain.doFilter(request, response);
					DBConnection.getConnection().commit();
					// logger.error(f + "处理时间：" + (System.currentTimeMillis() -
					// l)
					// + "毫秒");
					return;
				} else {
					request.setAttribute("elmessage", "您无权访问~！请与管理员联系");
					boolean b = httpRequestDeviceUtils.isMobileDevice(req);
					if(b==true){
						req.getRequestDispatcher("/wsj_phone/error.jsp").forward(req,
								response);
					}
					request.getRequestDispatcher("/error.jsp").forward(request,
							response);
					return;
				}
			} else {// 资源服务器管理部分--
				HttpServletRequest req = (HttpServletRequest) request;
				req.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
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
					request.getRequestDispatcher("/error.jsp").forward(request,
							response);
					return;
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
						request.getRequestDispatcher("/error.jsp").forward(
								request, response);
						return;
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
					request.getRequestDispatcher("/error.jsp").forward(request,
							response);
					return;
				}
				if (role == 7
						&& !AuthorityUtil.checkAuthor(role, funcCode, userid)) {
					request.setAttribute("elmessage", "您没登录或您的登录信息已丢失~");
					boolean b = httpRequestDeviceUtils.isMobileDevice(req);
					if(b==true){
						System.out.println("进入wsj——phone");
						req.getRequestDispatcher("/wsj_phone/login.jsp").forward(req,
								response);
					}
					req.getRequestDispatcher("/login.jsp").forward(req,
							response);
					return;
				}

				if (AuthorityUtil.checkAuthor(role, funcCode, userid)) {
					DBConnection.getConnection().setAutoCommit(false);
					chain.doFilter(request, response);
					DBConnection.getConnection().commit();
					// logger.error(f + "处理时间：" + (System.currentTimeMillis() -
					// l)
					// + "毫秒");
					return;
				} else {

					request.setAttribute("elmessage", "您无权访问~！请与管理员联系");
					request.getRequestDispatcher("/error.jsp").forward(request,
							response);
					return;
				}
			}
		} catch (Exception e) {
			logger.error("==系统错误==处理失败，回滚", e);
			try {
				DBConnection.getConnection().rollback();
			} catch (Exception ee) {
				logger.error("filter--回滚数据库失败");
			}
			request.setAttribute("elmessage", "系统错误，请与管理员联系-" + e.getMessage());
			request.getRequestDispatcher("/eLexception.jsp").forward(request,
					response);
			return ;
		} finally {
			try {
				DBConnection.getConnection().close();
			} catch (Exception ee) {
				logger.error("filter--关闭数据库失败");
			}
			logger.info("------关闭数据库");
			DBConnection.setNull();
		}

	}

	/*private boolean checkReg(HttpServletRequest req, ServletResponse response)
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
	}*/

	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			Register.init();
			String path = filterConfig.getServletContext().getRealPath(
					"/WEB-INF/config/");
			LableCommon.LableCommon_init(path);
			logger.error("配置文件路径：" + path);
			SystemConfOp.setPath(path);
			SystemConfOp.load();
			DBConnection.startDatabase(path);
			// 初始化SCORM数据库连接
			DBConnection.getConnection().setAutoCommit(false);
			ElQuerySql.init(path);
			AuthorityUtil.load();
			EpQStatus.init();
			NavigationUtil.load();
			((IndexDataUtil) SpringContextUtil.getBean("indexDataUtil"))
					.loadIndexInfo(ElConstants.INDEX_MODEL_ALL);
			DBConnection.getConnection().commit();

		} catch (Exception e) {
			logger.error("数据库配置加载失败！", e);
			try {
				DBConnection.getConnection().rollback();
			} catch (Exception ee) {
				logger.error("filter-init-回滚数据库失败");
			}
		} finally {
			try {
				DBConnection.getConnection().close();
			} catch (Exception ee) {
				logger.error("filter-init-关闭数据库失败");
			}
			DBConnection.setNull();
		}
	}

	public HttpRequestDeviceUtils getHttpRequestDeviceUtils() {
		return httpRequestDeviceUtils;
	}

	public void setHttpRequestDeviceUtils(
			HttpRequestDeviceUtils httpRequestDeviceUtils) {
		this.httpRequestDeviceUtils = httpRequestDeviceUtils;
	}
}
