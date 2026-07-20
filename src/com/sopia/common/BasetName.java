package com.sopia.common;


import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.duman.entities.BaseDataType;

/**
 * 基础数据类别名显示标签
 * @author Administrator
 *
 */
public class BasetName extends TagSupport {
	private static final Log logger = LogFactory.getLog(BasetName.class);
	private static final long serialVersionUID = 3119679319963664116L;
	private int btid;

	public int getBtid() {
		return btid;
	}

	public void setBtid(int btid) {
		this.btid = btid;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			//ServletRequest request = pageContext.getRequest();
			//HttpServletRequest request=ServletActionContext.getRequest();
			BasetNameUtil bnu=new BasetNameUtil();
			BaseDataType baseType=bnu.getBaseTypeById(this.getBtid());
			out.print("<span>");
			out.print(baseType.getName());
			out.print("</span>");
			
		} catch (Exception ex) {
			logger.error("基础数据标签错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
}
