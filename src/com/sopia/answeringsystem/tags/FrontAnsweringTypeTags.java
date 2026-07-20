package com.sopia.answeringsystem.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;

import com.sopia.answeringsystem.entities.AnsweringType;
import com.sopia.common.ElTag;

@SuppressWarnings("serial")
public class FrontAnsweringTypeTags extends ElTag{
	@SuppressWarnings("unchecked")
	public int doStartTag(){
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		List<AnsweringType> types = (List<AnsweringType>) request.getAttribute("answeringTypes");
		try {
			writeChilds(out, types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ElTag.SKIP_BODY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		// TODO Auto-generated method stub
		List<AnsweringType> list = (List<AnsweringType>)obj;
		AnsweringType type = null;
		if(list!=null){
			for(int i=0;i<list.size();i++){
				type = list.get(i);
				if(type.getParentid() == 1){
					out.println("<dt><a href=\"ques_index.action?ansType.id="+type.getId()+"\">"+type.getName()+"&raquo;</a> <span class=\"num\">("+type.getHasTotalCount()+")</span></dt>");
					int id = type.getId();
					out.println("<dd>");
					for(int j=0;j<list.size();j++){
						if(id == list.get(j).getParentid()){
							out.println("<a href=\"ques_index.action?ansType.id="+list.get(j).getId()+"\">"+list.get(j).getName()+"</a>|");
						}
					}
					out.println("</dd>");
				}
				
			}
		}
	}

}
