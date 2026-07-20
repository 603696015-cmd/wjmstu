package com.sopia.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sopia.ElConstants;
import com.sopia.duman.dao.UserDao;
import com.sopia.duman.dao.impl.UserDaoImpl;
import com.sopia.duman.entities.ELUser;
import com.sopia.duman.entities.ElRole;




/**
 * 本系统 对外 单点登录接口
 * @author Administrator
 *
 */
public class SSOUtil {
	public void checkSSo(HttpServletRequest request) throws ElException {
		UserDao userDao = new UserDaoImpl();
		if(null != request.getSession().getAttribute(ElConstants.SESSION_ROLE))
			return ;
		
		Cookie usernamec = getCookie(ElConstants.ELEARNING_COOKIE_USERNAME, request);
		if(usernamec==null)
			return ;
		String username = usernamec.getValue();
		ELUser eu  = new ELUser();
		if(!userDao.checkUsername(username)){
			eu.setUsername(username);
			eu.setRealname(username);
			eu.setPassword(username);
//			eu.setEmail("");
			eu.setValid(true);
			eu.setRole(new ElRole(4,"学员"));
//			eu.setDepartment(new DepartmentDaoImpl().getDepRootByCid(1));
			userDao.insert(eu);
		}else{
			eu = userDao.query(username);
		}
		HttpSession session = request.getSession();
		session.setAttribute(ElConstants.SESSION_USERID,
				eu.getId());
		session.setAttribute(ElConstants.SESSION_USERNAME,
				eu.getUsername());
		session.setAttribute(ElConstants.SESSION_REALNAME,
				eu.getRealname());
		session.setAttribute(ElConstants.SESSION_ROLE,
				eu.getRole().getId());
		session.setAttribute(ElConstants.SESSION_ROLENAME,
				eu.getRole().getName());
		session.setAttribute(ElConstants.SESSION_MYDEPARTMENT,
				eu.getDepartment().getId());
	}
	public static Cookie getCookie(String name,HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];

				if (c.getName().equals(name)) {
					return c;
				}
			}
		}

		return null;
	}
}
