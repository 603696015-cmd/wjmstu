package com.sopia.shebeipinggu.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;

import com.sopia.common.ElTag;
import com.sopia.pfms.entities.ProductType;

@SuppressWarnings("serial")
public class FrontPtypeList extends ElTag {
	
	@SuppressWarnings("unchecked")
	public int doStartTag(){
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		List<ProductType> qlb = (List<ProductType>) request.getAttribute("productTypes");
		try {
			writeChilds(out, qlb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ElTag.SKIP_BODY;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		@SuppressWarnings("unused")
		List<ProductType> productTypes = (List<ProductType>)obj;
		
		if(productTypes != null){
			
			for (int i = 0; i < productTypes.size(); i++) {
				
				ProductType qlbi = productTypes.get(i);
				if(qlbi.getParent().getId() == 1){
					out.println("<tr>" +
							"<td width=\"30\" align=\"left\" valign=\"middle\"><img src=\"front/images/arrow_r.gif\" width=\"11\" height=\"8\"></td>" +
							"<td width=\"220\" height=\"33\" align=\"left\" valign=\"middle\" class=\"STYLE5\"><a href='"+ getHref() + "" + qlbi.getId() + "'>"+qlbi.getName()+"</a></td>" +
							"</tr>");
					
					int id = qlbi.getId();
					
					out.println("<tr>" +
					"<td height=\"33\" colspan=\"2\" align=\"left\" valign=\"middle\">");
					
					for(int j=0;j<productTypes.size();j++){
						ProductType q = productTypes.get(j);
						
							
						if( id == q.getParent().getId()){
							out.println("<a href='" + getHref() + "" + q.getId() + "'>" + q.getName() + "</a> £ü ");
						}
						
					}
					
					out.println("</td></tr>");
				}
				
			}
		}

	}

}
