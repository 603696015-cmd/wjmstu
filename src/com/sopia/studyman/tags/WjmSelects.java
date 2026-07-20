package com.sopia.studyman.tags;


import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ExamPaperUtil;
import com.sopia.questionman.entities.Question;

public class WjmSelects extends TagSupport {

	private static final long serialVersionUID = 6536487202613250886L;
	private static final Log logger = LogFactory.getLog(WjmSelects.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Question q = (Question) request.getAttribute("question");
			Integer view = (Integer)request.getAttribute("view");
			Integer defaultSelect = (Integer)request.getAttribute("defaultSelect");
			if(q!=null){
				if(q.getQtype() == 15 || q.getQtype() == 16){
					writeEPQs_1516(out, q, view,defaultSelect);
				}else if(q.getQtype() == 18){
					writeEPQs_18(out, q, view,defaultSelect);
				}
			}
		} catch (Exception ex) {
			logger.error("加载看图选择、看动画选择、听音选图题型选项出错",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	/**
	 * 加载看图选择、看动画选择选项
	 * @param out
	 * @param q
	 * @throws Exception
	 */
	private void writeEPQs_1516(JspWriter out, Question q, int view, int defaultSelect)
    	throws Exception
	  {
	    for (int i = 0; i < q.getOptions().length; i++) {
	      out.println("<tr>");
	      out.println("<td align=\"right\" style=\"display: none;\"><input type=\"radio\" name=\"questions_" + 
	        q.getEpblock().getId() + "_0_" + q.getSortid() + "_stuAnswers\" " + 
	        "id=\"radio" + i + "\" " + 
	        "value=\"" + i + "\" ");
	
	      if ((i == defaultSelect) && (view == 0))
	        out.println("checked=\"checked\" />");
	     else {
	        out.println("/>");
	      }
	      out.println("</td>");
	      out.println("<td height=\"45\" align=\"left\" ");
	    //  if ((i == defaultSelect) && (view == 0))
	    //    out.println("bgcolor=\"#0099CC\" ");
	      //else {
	        out.println("bgcolor=\"\" ");
	      //}
	      if (view == 0) {
	        out.println("onclick=\"setSelect(" + i + ");\" ");
	      }
	      out.println("align=\"left\" name=\"r1\" id=\"option" + i + "\" > ");
	
	      out.println("<table style=\"color:#FFFFFF; font-size: 22px; font-weight: bold;\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	      out.println("  <tr> <td width=\"30\" valign=\"top\"  style=\"line-height: 35px;\">" );
	      out.println(ExamPaperUtil.getABC(i) + ".</td>");
	      out.println(" <td align=\"left\" valign=\"top\" style=\"line-height: 35px;\">");
	      out.println(q.getOptions()[i]);
	      out.println("    </td> </tr></table>");
	      out.println("</td>");
	      out.println("<tr>");
	    }
	  }
	
	/**
	 * 加载听音选图选项
	 * @param out
	 * @param q
	 * @throws Exception
	 */
	private void writeEPQs_18(JspWriter out,Question q,int view,int defaultSelect) throws Exception {
		int length = q.getOptions1().length ; 
		if(length >=2 && length <=3){//两张或三张图片
			out.println("<table width=\"80%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
			out.println("<tr>");
			for(int i=0;i<q.getOptions1().length;i++){
				if(i == defaultSelect && view == 0){
					out.println("<td><img width=\"250\" height=\"200\" style=\"border:solid 2px #CF9;padding:2px\" " +
							" onclick=\"setVal(this,"+i+");\" " +
									" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
				}else{
					out.println("<td><img width=\"250\" height=\"200\"  " +
							" onclick=\"setVal(this,"+i+");\" " +
									" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
				}
			}
			out.println("</tr>");
			out.println("</table>");
		}else if(length == 4){//四张图片
			out.println("<table width=\"80%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
			for(int i=0;i<q.getOptions1().length;i++){
				if(i == 0 || i == 2){
					out.println("<tr>");
					if(i == defaultSelect && view == 0){
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\" style=\"border:solid 2px #CF9;padding:2px\" " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}else{
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\"  " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}
				}else{
					if(i == defaultSelect && view == 0){
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\" style=\"border:solid 2px #CF9;padding:2px\" " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}else{
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\"  " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}
					out.println("</tr>");
				}
			}
			out.println("</table>");
		}else if(length == 5){//五张图片
			out.println("<table width=\"80%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
			for(int i=0;i<q.getOptions1().length;i++){
				if(i == 0 || i == 3){
					out.println("<tr>");
					if(i == defaultSelect && view == 0){
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\" style=\"border:solid 2px #CF9;padding:2px\" " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}else{
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\"  " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}
				}else{
					if(i == defaultSelect && view == 0){
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\" style=\"border:solid 2px #CF9;padding:2px\" " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}else{
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\"  " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}
					if(i == 2 || i == 4){
						out.println("</tr>");
					}
				}
			}
			out.println("</table>");
		}else if(length == 6){//六张图片
			out.println("<table width=\"80%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">");
			for(int i=0;i<q.getOptions1().length;i++){
				if(i == 0 || i == 3){
					out.println("<tr>");
					if(i == defaultSelect && view == 0){
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\" style=\"border:solid 2px #CF9;padding:2px\" " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}else{
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\"  " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}
				}else{
					if(i == defaultSelect && view == 0){
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\" style=\"border:solid 2px #CF9;padding:2px\" " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}else{
						out.println("<td align=\"center\"><img width=\"210\" height=\"150\"  " +
								" onclick=\"setVal(this,"+i+");\" " +
										" alt=\"\" src="+q.getOptions1()[i]+" /></td>");
					}
					if(i == 2 || i == 5){
						out.println("</tr>");
					}
				}
			}
			out.println("</table>");
		}
	}

}
