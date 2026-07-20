package com.sopia.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import org.apache.struts2.ServletActionContext;

import com.sopia.common.SystemConfOp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CreateHtml {

	/**
	 * 
	 * @param ftl  模板文件
	 * @param htmlName html文件名称
	 * @param map    map保存数据
	 * @param relaPath  //在这里没有用到
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void init(String ftl, String htmlName, Map map, String dirName) throws IOException, TemplateException {
		
		
		//创建Configuration对象
		Configuration cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "/");
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		
		//创建Template对象
		Template template = cfg.getTemplate(ftl);
		template.setEncoding("UTF-8");
		
		//生成静态页面   path:静态页面存放的位置
		
		String path = ServletActionContext.getServletContext().getRealPath("/")+dirName;
//		File fileName = new File(path + htmlName);
		new File(path).mkdir();
		new File(path+"/newsindex").mkdir();
		String cachFileName = path+"/newsindex" + File.separator + htmlName;
		File fileName = new File(cachFileName);
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
		template.process(map, out);
		out.flush();
		out.close();
		
	}
}