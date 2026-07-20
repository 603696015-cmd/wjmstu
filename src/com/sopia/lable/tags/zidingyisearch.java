package com.sopia.lable.tags;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElTag;

import com.sopia.forumman.entities.ForumBlockType;
import com.sopia.lable.common.LableCommon;
import com.sopia.lable.dao.CustomLableDao;
import com.sopia.lable.dao.impl.CustomLableDaoImpl;
import com.sopia.lable.entites.Lable;
import com.sopia.lable.entites.SearchLable;
import com.sopia.lable.entites.TableField;

public class zidingyisearch extends TagSupport {
	private static final long serialVersionUID = 3119679319963664116L;
	private String lablename;

	private String setnull;

	public String getSetnull() {
		return setnull;
	}

	public void setSetnull(String setnull) {
		this.setnull = setnull;
	}

	public String getLablename() {
		return lablename;
	}

	public void setLablename(String lablename) {
		this.lablename = lablename;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			CustomLableDao c = new CustomLableDaoImpl();
			if (getLablename() != null && !"".equals(getLablename())) {
				SearchLable l = new SearchLable();
				l = c.lable_getlablesearchlable("lable_search", getLablename());
				if (l != null) {
					// 得到搜索框内容标签
					String searchlablestr = LableCommon
							.lablecommon_getsearchlable(l.getSearchlable());
					if (l.getType() == 2) {
						String arr[] = l.getFieldstr().split("-");
						List<TableField> list = c
								.lable_getTableFieldByField(arr);
						List<Map> listMap = c.getpageMap(list, l.getSql(), l
								.getPageSize(), 1);
						// 得到总页数信息
						String sqlcount = LableCommon
								.lablecommon_pagegetcountsql(l.getSql());
						int count = c.lable_getsqlsagecount(sqlcount);
						int page = 0;
						if (count % l.getPageSize() == 0) {
							page = count / l.getPageSize();
						} else {
							page = count / l.getPageSize() + 1;
						}
						writeChilds(out, listMap, count, page, l.getLable(),
								searchlablestr);
					} else {
						// 如果是精确查询
						writeChilds(out, searchlablestr);

					}
				}
			}

			System.out.println(getLablename());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, List<Map> obj, int count, int page,
			String biaoqianti, String searchlablestr) throws Exception {
		List<Map> listMap = (List<Map>) obj;

		if (listMap != null) {
			Random random = new Random();
			int aaaa = random.nextInt(999999 - 0 + 1) + 0;
			String lname = aaaa + getLablename();
			out.println("<table   width='100%' valign='top'><tr><td>");
			// 修改搜索框里的搜索按钮给其加入单击事件，和参数
			searchlablestr = LableCommon.lablecommon_addstr(searchlablestr,
					"id='zidingyisearchsub'", " onclick='zdysearchsubmit("
							+ aaaa + ",1)' ");
			out.println(searchlablestr);
			out.println("</td ></tr>");
			out.println("<tr><td valign='top'>");
			out.println("<table id='" + lname
					+ "' width='100%' ><tr><td valign='top'>");
			out.println("<table   width='100%' valign='top'>");
			for (Map map : listMap) {
				out.println(LableCommon.lablecommon_getlable(map, biaoqianti));
			}
			out.println("</table></td></tr>");

			out.print("<tr><td valign='baseline'><table width='100%'><tr><td>");

			out.println("[首页]");
			out.print("[上一页]");
			if (page > 0) {
				out.print("<select  onchange='searchlablepage(" + aaaa
						+ ",this.options[this.selectedIndex].value)'>");
				for (int i = 1; i <= page; i++) {
					if (i == 1)
						out.println("<option value='" + i
								+ "' selected='selected'>" + i + "</option>");
					else {
						out.println("<option value='" + i + "'>" + i
								+ "</option>");
					}

				}
				out.println("</select> ");
			}

			if (page > 1) {
				out
						.print("<a   style='cursor: hand' href='javascript:searchlablepage("
								+ aaaa + "," + 2 + " )'>[下一页]</a>");

				out
						.print("<a style='cursor: hand' href='javascript:searchlablepage("
								+ aaaa + "," + page + ")'>[末页]</a>");
			} else {
				out.print("[下一页]");
				out.print("[末页]");
			}

			out.print("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
					+ "<b>条</b></span>");

			out.print("<s:hidden id='" + aaaa + "' value='" + getLablename()
					+ "' />");
			out.print("</td></tr></table>");

			out.print("</td></tr></table></td></tr></table>");

		} else {

			if (getSetnull() != null && !"".equals(getSetnull())) {

				out.println(getSetnull());
			} else {
				out.println("&nbsp");
			}
		}

	}

	public void writeChilds(JspWriter out, String searchlablestr)
			throws Exception {

		Random random = new Random();
		int aaaa = random.nextInt(999999 - 0 + 1) + 0;
		String lname = aaaa + getLablename();
		out.println("<table   width='100%' valign='top'><tr><td>");
		// 修改搜索框里的搜索按钮给其加入单击事件，和参数
		searchlablestr = LableCommon.lablecommon_addstr(searchlablestr,
				"id='zidingyisearchsub'", " onclick='zdysearchsubmit(" + aaaa
						+ ",1)' ");
		out.println(searchlablestr);
		out.println("</td ></tr>");
		out.println("<tr><td valign='top'>");
		out.println("<table id='" + lname
				+ "' width='100%' ><tr><td valign='top'>");
		out.println("<table   width='100%' valign='top'>");
		out.println("</table></td></tr>");
		
//		out.print("<tr><td valign='baseline'><table width='100%'><tr><td>");
//		out.println("[首页]");
//		out.print("[上一页]");
//		out.print("[下一页]");
//		out.print("[末页]");
//
//		out
//				.print("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b><b>条</b></span>");
//
		out.print("<input type='hidden' id='" + aaaa + "' value='"
				+ getLablename() + "' />");
//		out.print("</td></tr></table>");
		
		
		out.print("</td></tr></table></td></tr></table>");
	}
}
