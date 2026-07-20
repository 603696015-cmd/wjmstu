package com.sopia.forumman.tag;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;

import com.sopia.forumman.entities.ForumBlock;
import com.sopia.forumman.entities.ForumBlockType;

public class fbtype_Select extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(fbtype_Select.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			List<ForumBlockType> qlb = (List<ForumBlockType>) request.getAttribute("fbtypes");
			writeChilds(out, qlb);
		} catch (Exception ex) {
			logger.error("论坛版块下拉列表显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		List<ForumBlockType> clb = (List<ForumBlockType>) obj;
		//if(clb.size()<0)
			
		for(int i=0;i<clb.size();i++){
			//out.println("<optgroup label='" +clb.get(i).getName()+ "'></optgroup>");
			out.println("<option value='" + clb.get(i).getName() + "'/>");
			if(clb.get(i).getFblocks()!=null){
//				if(clb.get(i).getFblocks().size()>0)
				List<ForumBlock> fbs = clb.get(i).getFblocks();
				if(fbs!=null)
					for (int j = 0; j < fbs.size(); j++) {
						out.println("--");
						out.println("<option value='" +fbs.get(j).getTitle() + "'>"  +fbs.get(j).getTitle() + " </option>");
					}
						
			}
		}
		//List<ForumBlock> clbChild = clb.getFblocks();
		/*if (clb.getId() < 0) {
			out.println("<optgroup label='" +clb.getName()+ "'></optgroup>");
		} else {
			out.println("<option value='" + clb.getId() + "'");
			if (getSelectid() == clb.getId()) {
				out.println("selected = 'selected'");
			}
			out.println(">");
			int j=0;
			for(int i=0;i<clb.)
			for (ForumBlock forumBlock : clb.getFblocks()) {
				out.println(clb.getName() + " </option>");
				if(forumBlock!=null)
					out.println(clb.getName() + " </option>");
			}
			
		}*/

		/*for (int i = 0; i < clbChild.size(); i++) {
			CourseType clbi = clbChild.get(i);
			writeChilds(out, clbi);
		}*/
	}
}
