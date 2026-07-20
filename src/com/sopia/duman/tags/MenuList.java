package com.sopia.duman.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.duman.entities.ElFunc;

public class MenuList extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8870865925373995708L;
	private static final Log logger = LogFactory.getLog(MenuList.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ElFunc cts = (ElFunc) request.getAttribute("menu");
			if(cts!=null){
				out.println("foldersTree=gFld(\"" + cts.getName() + "\", \"\");");
				out.println("foldersTree.xID=\"root\"; ");
			}
			if (null != cts && null != cts.getChild()){
				for (int i = 0; i < cts.getChild().size(); i++) {
					ElFunc ctsi = cts.getChild().get(i);
					if(ctsi!=null){
						out.println("fld0" + (i + 1) + "=gFld(\"" + ctsi.getName()
								+ "\", \"\",\"\");\n" + "fld0" + (i + 1)
								+ ".xID = \"0" + (i + 1) + "\";");
						out.println("node0" + (i + 1) + "=insFld(foldersTree, fld0"
								+ (i + 1) + ");	");
					}
					if (null != ctsi && ctsi.getChild() != null) {
						for (int j = 0; j < ctsi.getChild().size(); j++) {
							ElFunc ctsj = ctsi.getChild().get(j);
							String params = ctsj.getParams();
							String href = ctsj.getFunccode().indexOf(".jsp") >= 0
									|| ctsj.getFunccode().indexOf(".xxx") >= 0 ? ctsj
									.getFunccode().substring(0, ctsj
											.getFunccode().length()-4)
									: ctsj.getFunccode().indexOf(".shtm")>=0?ctsj//自定义模块的静态化TMK
											.getFunccode():ctsj.getFunccode() + ".action";

							if (params != null && params.indexOf("cid") >= 0)
								params = params.replace("cid", request
										.getAttribute("course.id").toString());
							if (null==params||params.indexOf("=") < 0) {
								params = "";
							} else {
								params = "?" + params;
							}

							out.println("fld0" + (i + 1) + "0" + (j + 1)
									+ "=gFld(\"" + ctsj.getName() + "\", \""
									+ href + params + "\",\""
									+ ctsj.getTarget() + "\");");
							out.println("fld0" + (i + 1) + "0" + (j + 1)
									+ ".xID = \"0" + (i + 1) + "0" + (j + 1)
									+ "\";");
							out.println("node0" + (i + 1) + "0" + (j + 1)
									+ "=insFld(node0" + (i + 1) + ", fld0"
									+ (i + 1) + "0" + (j + 1) + ");");
						}

					}
				}
			}
		} catch (Exception ex) {
			logger.error("功能菜单显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
}
