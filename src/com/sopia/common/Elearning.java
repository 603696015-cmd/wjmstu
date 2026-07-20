package com.sopia.common;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ÒÑ·ÏÆú
 * @author Administrator
 *
 */
public class Elearning extends HttpServlet{
	private static final long serialVersionUID = -4181008931609698754L;
	public Elearning() {
		
	}
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
//			String basePath = config.getServletContext().getRealPath("/");
//			DBConnection.startDB(basePath);
		} catch (Exception e) {
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
