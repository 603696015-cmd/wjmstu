package com.sopia.schedule;

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

import com.sopia.CreatorHtml;
import com.sopia.ElConstants;
import com.sopia.common.SystemConfOp;
import com.sopia.common.ZdyStaticHtmlSystemConf;
import com.sopia.common.ZdyStaticHtmlSystemConfOp;



/**
 * 自定义模块静态化处理
 * @author Administrator
 *
 */
public class ZDYHtmlCreatorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ZDYHtmlCreatorServlet.class);
	public  static final String SAVEDIR = "/zdy";
	
	//这个方法是把http://xyz.com/html_addContactTagsInit.shtm
	//变成  http://xyz.com/addContactTagsInit.action?tablename=KHDA
	protected String simpleURLReWrite(HttpServletRequest request) throws ServletException,IOException{
		String uri = request.getRequestURI();//html_addContactTagsInit.shtm
		String contextPath = request.getContextPath();
		logger.debug("HtmlCreator contextPath ="+contextPath);
		if(contextPath!=null&& contextPath.length()>0)
			uri = uri.substring(contextPath.length());
			uri = uri.substring(0, uri.length()-5);
			String[] urls = uri.split("_");
			if(urls.length==1){
				uri = urls[0]+".action";
			}else if(urls.length==2){
				uri = "/"+urls[1]+".action" ;
			}
			logger.debug("ZDYHtmlCreatorServlet get uri="+uri);
		return uri;
	}
	// 这个方法就是根据 http://xyz.com/html_addContactTagsInit.shtm
	// 来得到生成的html文件名字，也就是 html_addContactTagsInit.html
	private String getHtmlFileName(HttpServletRequest request) throws ServletException,IOException{
		String uri = request.getRequestURI();//html_addContactTagsInit.shtm
		String contextPath = request.getContextPath();
		if(contextPath!=null&& contextPath.length()>0)
			uri = uri.substring(contextPath.length());
			uri = uri.substring(1, uri.length()-5);
			uri+=".html";
			
			return uri;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		String encoding = "UTF-8";
		
		//得到项目所在服务器位置
		String servPath = getServletContext().getRealPath("/");
		String tablename = request.getParameter("tablename");
		//生成总目录
		String dirname = SystemConfOp.getValue(ElConstants.ZDYHTML)+SAVEDIR;
		
		if(!new File(servPath + SystemConfOp.getValue(ElConstants.ZDYHTML)).exists()){
			new File(servPath + SystemConfOp.getValue(ElConstants.ZDYHTML)).mkdir();
		}
		
		new File(servPath+dirname).mkdir();
		
		if(!new File(servPath+dirname+"/"+tablename).exists()){
			new File(servPath+dirname+"/"+tablename).mkdir();
		}
		
//		//得到真实的请求地址
		String templatePath = simpleURLReWrite(request);
		
		ZdyStaticHtmlSystemConf zdyStaticHtmlSysConf = new ZdyStaticHtmlSystemConf();
		zdyStaticHtmlSysConf.setOpen_all(ZdyStaticHtmlSystemConfOp.getBooleanValue(ElConstants.SYSTEM_ZDY_STATIC_HTML_ALL));
		if(zdyStaticHtmlSysConf.isOpen_all()){
			String realPath = request.getSession().getServletContext().getRealPath("/")+dirname+"/"+tablename;

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
				logger.debug("ZDYHtmlCreatorServlet RequestDispatcher="+templatePath);
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
						dispatcher = getServletContext().getRequestDispatcher(dirname+"/"+tablename+"/"+htmlName);
						dispatcher.include(request, response);
					}
				}finally{
					if(fos!=null){
						fos.close();
					}
				}
			}else{
				RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(dirname+"/"+tablename+"/"+htmlName);
				dispatcher.include(request, response);
			}
		}else{//不开启静态页
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(templatePath);
			dispatcher.forward(request, response);
		}
		
		
	}
}
