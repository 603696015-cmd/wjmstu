package com.sopia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

import com.sopia.common.SystemConfOp;



/**
 * 静态化处理
 * @author Administrator
 *
 */
public class HtmlCreatorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HtmlCreatorServlet.class);

	//这个方法是把http://xyz.com/product_pageNumber=1.shtm
	//变成  http://xyz.com/product.do?pageNumber=1
	protected String simpleURLReWrite(HttpServletRequest request) throws ServletException,IOException{
		String uri = request.getRequestURI();
		String params = request.getParameter("tablename");
		String contextPath = request.getContextPath();
		logger.debug("HtmlCreator contextPath ="+contextPath);
		if(contextPath!=null&& contextPath.length()>0)
			uri = uri.substring(contextPath.length());
			uri = uri.substring(0, uri.length()-5);
			String[] urls = uri.split("_");
			if(urls.length==1){
				uri = urls[0]+".action";
			}
			logger.debug("HtmlCreatorServlet get uri="+uri);
		return uri;
	}
	// 这个方法就是根据 http://xyz.com/product_pageNumber_1.shtm
	// 来得到生成的html文件名字，也就是 product_pageNumber_1.html
	private String getHtmlFileName(HttpServletRequest request) throws ServletException,IOException{
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if(contextPath!=null&& contextPath.length()>0)
			uri = uri.substring(contextPath.length());
			uri = uri.substring(1, uri.length()-5);
			String[] urls = uri.split("_");
			if(urls.length==3){
				uri = urls[2];
			}
			if(urls.length==2){
				uri = urls[1];
			}
			uri+=".html";
			
			return uri;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{

		//编码方式，可以配置到web.xml里
		String encoding = "UTF-8";
		
		//得到项目所在服务器位置
		String servPath = getServletContext().getRealPath("/");
		//生成总目录
		String dirname = SystemConfOp.getValue(ElConstants.CATALOGUE_PLACE);
		new File(servPath+dirname).mkdir();
		
//		//得到真实的请求地址
		String templatePath = simpleURLReWrite(request);
		String realPath = request.getSession().getServletContext().getRealPath("/")+dirname;

		//想要生成的静态html文件的名字
		String htmlName = getHtmlFileName(request);
		//静态html的名字，包含绝对路径
		String cachFileName = realPath + File.separator + htmlName;
		
		logger.debug("cachFileName="+cachFileName);
		File cacheFile = new File(cachFileName);
		boolean load = true;
		//如果静态html存在，就直接显示html，否则，生成
		if(cacheFile.exists()){
			load = false;
		}
		if(load){
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ServletOutputStream stream = new ServletOutputStream(){
				public void write(byte[] data,int offset,int length){
					os.write(data,offset,length);
				}
				public void write(int b)throws IOException{
					os.write(b);
				}
			};
			final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,encoding));
			HttpServletResponse rep = new HttpServletResponseWrapper(response){
				public ServletOutputStream getOutputStream(){
					return stream;
				}
				public PrintWriter getWriter(){
					return pw;
				}			
			};
			logger.debug("HtmlCreatorServlet RequestDispatcher="+templatePath);
			// 使用 RequestDispatcher 去处理真正的请求。
			// 例如 index.shtm ，则转发到 index.action
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(templatePath);
			//RequestDispatcher dispatcher = request.getRequestDispatcher(templatePath);
			dispatcher.include(request, rep);
			//dispatcher.forward(request, rep);
			pw.flush();
			FileOutputStream fos = null;
			try{
				if(os.size() ==0){
					// 如果请求的地址无效，那么就发送一个404错误。
					response.sendError(HttpServletResponse.SC_NOT_FOUND,"");
				}else{
					// 生成静态文件，并且显示这个静态文件
					fos = new FileOutputStream(cachFileName);
					os.writeTo(fos);
					dispatcher = getServletContext().getRequestDispatcher(dirname+"/"+htmlName);
					dispatcher.include(request, response);
				}
			}finally{
				if(fos!=null){
					fos.close();
				}
			}
		}else{
			RequestDispatcher dispatcher =getServletContext().getRequestDispatcher("/html/"+htmlName);
			dispatcher.include(request, response);
		}
	}
}
