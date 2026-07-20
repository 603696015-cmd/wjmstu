package com.sopia.freemarker;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.sopia.ElConstants;
import com.sopia.common.SystemConfOp;

import freemarker.template.TemplateException;

public class FreePage {

	public String pageIn(Map map, String htmlName, String ftl){
		
		CreateHtml createHtml = new CreateHtml();
		HttpServletRequest request = ServletActionContext.getRequest();
//		String htmlName = "newsList.html";
//		String ftl = "ftl/news.html";
		String base = request.getContextPath();
		String tableName = "newsindex";
		String dirName = SystemConfOp.getValue(ElConstants.CATALOGUE_PLACE);
		try {
			createHtml.init(ftl, htmlName, map, dirName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dirName+"/newsindex/"+htmlName;
	}

}
