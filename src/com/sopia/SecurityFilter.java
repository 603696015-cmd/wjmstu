package com.sopia;

import java.io.IOException;

import javax.print.attribute.ResolutionSyntax;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.portable.ApplicationException;

public class SecurityFilter implements Filter {
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		String referer=req.getHeader("referer");
		System.out.println("referer:"+referer);
		HttpSession session=((HttpServletRequest)request).getSession();
		session.setAttribute("wgxx", "非法访问，请与管理员联系");
		  if(referer==null||"".equals(referer)){//属于盗链行为
		   System.out.println("盗链来自："+req.getRemoteAddr());
		   //将请求转为另一个图片，此处的JsCssImage和WEB-INF处于同一个目录下
		   request.getRequestDispatcher("/nodownload.jsp").forward(req, res);//注意用法
		  }else{//正常行为
		   chain.doFilter(req, res);//放行请求
		  }

	}

	public void init(FilterConfig config) throws ServletException {
	//System.out.println("拦截初始化："+config.getInitParameter("fangdao"));
		  
	}

}
