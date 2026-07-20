package com.sopia.schedule;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.sopia.BaseAction;
import com.sopia.ElConstants;
import com.sopia.common.ElException;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.ModuleZDY;
import com.sopia.schedule.entities.Tags;

/**
 * 解析自定义模块列表HTML
 * 
 * @author Administrator
 * 
 */
public class TagsResolveHTMLForList {
	public static boolean getBetweenStatus(CustomAudit ca_small,
			CustomAudit ca_big, int status) {
		boolean flag = false;
		if ((status >= Integer.parseInt(ca_small.getAuditOrder()) * 2 + 10)
				|| (status <= Integer.parseInt(ca_big.getAuditOrder()) * 2 + 10 + 1)) {
			flag = true;
		}
		return flag;
	}

	public static String getStatus_chinese(PageContext pageContext, int status)
			throws ElException {
		TagsDaoImpl tagsDao = new TagsDaoImpl();
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		List<CustomAudit> cas = tagsDao
				.get_audits_by_tablename((String) request
						.getAttribute("tablename"));
		if (status == 0) {
			return "已创建";
		}
		if (status == 2) {
			return "修改等待中";
		}
		if (status == 3) {
			return "删除等待中";
		}
		if (status == 5) {
			return "初审等待中";
		}
		if (status == 6) {
			return "初审通过";
		}
		if (status == 7) {
			return "初审不通过";
		}
		if (status == 8) {
			return "终审等待中";
		}
		if (status == 9) {
			return "终审通过";
		}
		if (status == 10) {
			return "终审不通过";
		}
		for (int i = 0; i < cas.size(); i++) {
			if (status == Integer.parseInt(cas.get(i).getAuditOrder()) * 2 + 10) {
				return cas.get(i).getAuditName() + "通过";
			}
			if (status == Integer.parseInt(cas.get(i).getAuditOrder()) * 2 + 10 + 1) {
				return cas.get(i).getAuditName() + "不通过";
			}
		}
		return "";
	}

	/**
	 * 我添加的和我负责的列表标签解析HTML
	 * 
	 * @param type
	 *            "1"表示新版
	 * @param pageContext
	 * @param list_tags
	 * @param out
	 * @param moduleZDY
	 * @param list_designe
	 * @param actionName
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public static void resolveHTMLForMyContactTags(String type,
			PageContext pageContext, List<Tags> list_tags, JspWriter out,
			ModuleZDY moduleZDY, List<Map<String, String>> list_designe,
			String actionName, ModuleManage moduleManage) throws IOException,
			NumberFormatException, ElException {
		// 显示table标题
		String width = "";
		int k = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getList_display() == 1)
				k++;
		}

		k = k * 100;
		if (k < 1000)
			width = " width='100%'";
		else
			width = " width='" + k + "px'";

		out.println("<table " + width
				+ " align='center' cellpadding='1' cellspacing='1'>");

		if (type != null && type.equals("1")) {
			out.println("<tr>");
			out.println("<td colspan=20><div id=\"Div_ToolsBar\"></div></td>");
			out.println("</tr>");
		}
		out.println("<tr id='column_name'>");
		out.println("<th width='20'></th>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getList_display() == 1)// display
			{
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<th><a id= '"
							+ list_tags.get(i).getColumn_name() + "_" + i
							+ "' href=\"javascript:columnsearch('"
							+ list_tags.get(i).getColumn_name() + "');\" >"
							+ list_tags.get(i).getName_display() + "</a></th>");
				}
			}
			// 当为工作日志表的时候增加一列显示差值
			if (i == list_tags.size() - 1)
				if (list_tags.get(i).getTable_name().equals("GRRZ")) {
					out.println("<th style='color:red'><span>差值</span></th>");
				}
		}// for
		out
				.println("<th><a href=\"javascript:columnsearch('status');\" >状态</a></th>");

		out.println("</tr>");

		out
				.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");

		// 显示列表值
		double chazhi = 0.0;
		for (int i = 0; i < list_designe.size(); i++) {
			if (i == list_designe.size() - 1) {
				out.println("<tr id = 'last_tr'>");
			} else {
				out.println("<tr>");
			}
			out.println("<td width='20' height='20' align='center'>"
							+ "<input type='checkbox' onclick='clickcheckbox();' value='"
							+ list_designe.get(i).get("id") + "' name='id_'/>"
							+ "</td>");
			for (int j = 0; j < list_tags.size(); j++) {
				if (list_tags.get(j).getList_display() == 1) {
					if (!list_tags.get(j).getDisplay_type().equals("当前用户信息")) {
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (list_tags.get(j).getDisplay_type().equals("日期")) {
							str = ScheduleUtil.dateFormat(str, list_tags.get(j)
									.getTimeformat());
						} else {
							if (str == null)
								str = "";
						}

						if (list_tags.get(j).getJindutiao() == 1) {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='left'>");
						} else {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='center'>");
						}
						// 显示类型为附件的时候
						if (list_tags.get(j).getDisplay_type().equals("附件上传")) {
							String str2[] = str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
						// 百分比
						if (list_tags.get(j).getDisplay_type().equals("百分比")) {
							if (list_tags.get(j).getJindutiao() == 1) {// 显示进度条
								if (str == null || str.equals(""))
									str = "0";
								BigDecimal bg = new BigDecimal(str);
								str = String
										.valueOf(bg.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue());
								out
										.println("<center><table  border='0' cellspacing='0'  ><tr><td><div  id='jindutiao_div__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
												+ str
												+ "%'  id='show_jindutiao__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "'/></div></td><td width=\"50\" align=\"left\"><span style='color:red;'>"
												+ str
												+ "%</span></td></tr></table></center>");
							} else {
								if (str.equals("")) {
									out.println("<label>" + 0 + "%</label>");
								} else {
									out.println("<label>" + str + "%</label>");
								}
							}
						} else if (list_tags.get(j).getDisplay_type().equals(
								"分级下拉选项")) {
							// 获取最后一级的选项
							if (!str.equals("")) {
								String[] ary = str.split("___");
								if (ary != null && ary.length > 0) {
									for (int m = 0; m < ary.length; m++) {
										if (m == ary.length - 1)
											str = ary[m].split("__")[1];
									}
								}
								if (str.equals("请选择"))
									str = "";
							}
							out.println("<label>" + str + "</label>");
						} else {
							out.println("<label>" + str + "</label>");
						}
						out.println("</td>");
					}
				}
			}

			// 当为工作日志表的时候增加一列显示差值
			if (list_tags.get(0).getTable_name().equals("GRRZ")) {
				String a = "0";
				String b = "0";
				if (list_designe.get(i).get("GRRZ_LDPF") != null
						&& !list_designe.get(i).get("GRRZ_LDPF").equals("")) {
					a = list_designe.get(i).get("GRRZ_LDPF");
				}
				if (list_designe.get(i).get("GRRZ_LDPF") != null
						&& !list_designe.get(i).get("GRRZ_ZWPF").equals("")) {
					b = list_designe.get(i).get("GRRZ_ZWPF");
				}
				double d = Double.parseDouble(a) - Double.parseDouble(b);
				chazhi += d;
				out.println("<td align='center'><span style='color:red'>" + d
						+ "</span></td>");
			}
			out.println("<td align='center'>"
					+ getStatus_chinese(pageContext, Integer
							.parseInt(list_designe.get(i).get("status")))
					+ "</td>");// 状态

		}
		out.println("</tr>");

		out.println("<tr>");

		out.println("<td  align='center'>合计</td>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getList_display() == 1) {
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<td align='center'>");
					if (list_tags.get(i).getSum_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number"))
							out.println(list_tags.get(i).getSum_i());
						else if (list_tags.get(i).getColumn_type().equals("float"))
							out.println(list_tags.get(i).getSum_f());

					}
					out.println("</td>");
				}
			}
		}
		// 当为工作日志表的时候增加一列显示差值
		if (list_tags.get(0).getTable_name().equals("GRRZ")) {
			out.println("<td align='center'><span style='color:red'>" + chazhi
					+ "</span></td>");
		}
		out.println("<td  colspan=3></td>");
		out.println("</tr>");

		out.println("</tbody>");
		out.println("</table>");
	}

	/**
	 * 公共查询页列表显示
	 * 
	 * @param pageContext
	 * @param list_tags
	 * @param out
	 * @param moduleZDY
	 * @param list_designe
	 * @param actionName
	 * @param moduleManage
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public static void resolveHTMLForMyPassContactTags(String type,
			PageContext pageContext, List<Tags> list_tags, JspWriter out,
			ModuleZDY moduleZDY, List<Map<String, String>> list_designe,
			String actionName, ModuleManage moduleManage) throws IOException,
			NumberFormatException, ElException {
		// 显示table标题
		String width = "";
		int k = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getList_display() == 1)
				k++;
		}

		k = k * 100;
		if (k < 1000)
			width = " width='100%'";
		else
			width = " width='" + k + "px'";

		out.println("<table " + width
				+ " align='center' cellpadding='1' cellspacing='1'>");
		if (type != null && type.equals("1")) {
			out.println("<tr>");
			out.println("<td colspan=20><div id=\"Div_ToolsBar\"></div></td>");
			out.println("</tr>");
		}
		// 显示table标题
		out.println("<tr>");
		out.println("<th width='20'></th>");
		// out.println("<th>操作</th>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getList_display() == 1)// display
			{
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<th><a id= '"
							+ list_tags.get(i).getColumn_name() + "_" + i
							+ "' href=\"javascript:columnsearch('"
							+ list_tags.get(i).getColumn_name() + "');\" >"
							+ list_tags.get(i).getName_display() + "</a></th>");
				}

			}
		}
		out.println("</tr>");
		out
				.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");
		// 显示列表值
		for (int i = 0; i < list_designe.size(); i++) {

			out.println("<tr>");
			out
					.println("<td width='20' height='20' align='center'>"
							+ "<input type='checkbox' onclick='clickcheckbox();' value='"
							+ list_designe.get(i).get("id") + "' name='id_'/>"
							+ "</td>");
			// out.println("<td align='center'>");
			//			
			//			
			//			
			// if(moduleManage.getOndemo()==1&&moduleZDY!=null&&moduleZDY.getAddjsp()!=null&&!moduleZDY.getAddjsp().equals("")){
			// out.println("<a href='javascript:view_ZDY("
			// + list_designe.get(i).get("id") + ")'>查看</a>");
			// }else{
			// out.println("<a href='javascript:view("
			// + list_designe.get(i).get("id") + ")'>查看</a>");
			// }
			// out.println("</td>");

			for (int j = 0; j < list_tags.size(); j++) {
				if (list_tags.get(j).getList_display() == 1) {
					if (!list_tags.get(j).getDisplay_type().equals("当前用户信息")) {
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (list_tags.get(j).getDisplay_type().equals("日期")) {
							str = ScheduleUtil.dateFormat(str, list_tags.get(j)
									.getTimeformat());
						} else {
							if (str == null)
								str = "";
						}
						if (list_tags.get(j).getJindutiao() == 1) {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='left'>");
						} else {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='center'>");
						}
						// 显示类型为附件的时候
						if (list_tags.get(j).getDisplay_type().equals("附件上传")) {
							String str2[] = str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
						// 百分比
						if (list_tags.get(j).getDisplay_type().equals("百分比")) {
							if (list_tags.get(j).getJindutiao() == 1) {// 显示进度条
								BigDecimal bg = new BigDecimal(str);
								str = String
										.valueOf(bg.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue());
								out
										.println("<center><table  border='0' cellspacing='0' ><tr><td><div  id='jindutiao_div__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
												+ str
												+ "%'  id='show_jindutiao__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "'/></div></td><td width=\"50\" align=\"left\"><span style='color:red;'>"
												+ str
												+ "%</span></td></tr></table></center>");
							} else {
								if (str.equals("")) {
									out.println("<label>" + 0 + "%</label>");
								} else {
									out.println("<label>" + str + "%</label>");
								}
							}
						} else if (list_tags.get(j).getDisplay_type().equals(
								"分级下拉选项")) {
							// 获取最后一级的选项
							if (!str.equals("")) {
								String[] ary = str.split("___");
								if (ary != null && ary.length > 0) {
									for (int m = 0; m < ary.length; m++) {
										if (m == ary.length - 1)
											str = ary[m].split("__")[1];
									}
								}
								if (str.equals("请选择"))
									str = "";
							}
							out.println("<label>" + str + "</label>");
						} else {
							out.println("<label>" + str + "</label>");
						}
						out.println("</td>");
					}
				}
			}

			out.println("</tr>");
		}
		out.println("</tbody>");
		out.println("</table>");
	}

	/**
	 * 初审列表显示
	 * 
	 * @param pageContext
	 * @param list_tags
	 * @param out
	 * @param moduleZDY
	 * @param list_designe
	 * @param actionName
	 * @param moduleManage
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public static void resolveHTMLForSearchContactTags(String type,
			PageContext pageContext, List<Tags> list_tags, JspWriter out,
			ModuleZDY moduleZDY, List<Map<String, String>> list_designe,
			String actionName, ModuleManage moduleManage) throws IOException,
			NumberFormatException, ElException {
		// 显示table标题
		String width = "";
		int k = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)
				k++;
		}

		k = k * 100;
		if (k < 1000)
			width = " width='100%'";
		else
			width = " width='" + k + "px'";

		out.println("<table " + width
				+ " align='center' cellpadding='1' cellspacing='1'>");
		if (type != null && type.equals("1")) {
			out.println("<tr>");
			out.println("<td colspan=20><div id=\"Div_ToolsBar\"></div></td>");
			out.println("</tr>");
		}

		out.println("<tr>");
		out.println("<th width='20'></th>");
		// out.println("<th>操作</th>");
		// out.println("<th>备注</th>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)// display
			{
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<th><a href=\"javascript:columnsearch('"
							+ list_tags.get(i).getColumn_name() + "');\" >"
							+ list_tags.get(i).getName_display() + "</a></th>");
				}

			}
			// 当为工作日志表的时候增加一列显示差值
			if (i == list_tags.size() - 1)
				if (list_tags.get(i).getTable_name().equals("GRRZ")) {
					out.println("<th style='color:red'><span>差值</span></th>");
				}
		}
		out
				.println("<th><a href=\"javascript:columnsearch('t.status');\" >状态</a></th>");

		out.println("</tr>");

		out
				.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");
		// 显示列表值
		double chazhi = 0.0;
		for (int i = 0; i < list_designe.size(); i++) {

			out.println("<tr>");
			out
					.println("<td width='20' height='20' align='center'>"
							+ "<input type='checkbox' onclick='clickcheckbox();' value='"
							+ list_designe.get(i).get("id") + "' name='id_'/>"
							+ "</td>");


			for (int j = 0; j < list_tags.size(); j++) {
				if (list_tags.get(j).getDepartsearch_display() == 1) {
					if (!list_tags.get(j).getDisplay_type().equals("当前用户信息")) {
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (list_tags.get(j).getDisplay_type().equals("日期")) {
							str = ScheduleUtil.dateFormat(str, list_tags.get(j)
									.getTimeformat());
						} else {
							if (str == null)
								str = "";
						}
						if (list_tags.get(j).getJindutiao() == 1) {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='left'>");
						} else {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='center'>");
						}
						// 显示类型为附件的时候
						if (list_tags.get(j).getDisplay_type().equals("附件上传")) {
							String str2[] = str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
						// 百分比
						if (list_tags.get(j).getDisplay_type().equals("百分比")) {
							if (list_tags.get(j).getJindutiao() == 1) {// 显示进度条
								if (str == null || str.equals(""))
									str = "0";
								BigDecimal bg = new BigDecimal(str);
								str = String
										.valueOf(bg.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue());
								out
										.println("<center><table  border='0' cellspacing='0' ><tr><td><div  id='jindutiao_div__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
												+ str
												+ "%'  id='show_jindutiao__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "'/></div></td><td width=\"50\" align=\"left\"><span style='color:red;'>"
												+ str
												+ "%</span></td></tr></table></center>");
							} else {
								if (str.equals("")) {
									out.println("<label>" + 0 + "%</label>");
								} else {
									out.println("<label>" + str + "%</label>");
								}
							}
						} else if (list_tags.get(j).getDisplay_type().equals(
								"分级下拉选项")) {
							// 获取最后一级的选项
							if (!str.equals("")) {
								String[] ary = str.split("___");
								if (ary != null && ary.length > 0) {
									for (int m = 0; m < ary.length; m++) {
										if (m == ary.length - 1)
											str = ary[m].split("__")[1];
									}
								}
								if (str.equals("请选择"))
									str = "";
							}
							out.println("<label>" + str + "</label>");
						} else {
							out.println("<label>" + str + "</label>");
						}
						out.println("</td>");
					}
				}
			}

			// 当为工作日志表的时候增加一列显示差值
			if (list_tags.get(0).getTable_name().equals("GRRZ")) {
				String a = "0";
				String b = "0";
				if (list_designe.get(i).get("GRRZ_LDPF") != null
						&& !list_designe.get(i).get("GRRZ_LDPF").equals("")) {
					a = list_designe.get(i).get("GRRZ_LDPF");
				}
				if (list_designe.get(i).get("GRRZ_LDPF") != null
						&& !list_designe.get(i).get("GRRZ_ZWPF").equals("")) {
					b = list_designe.get(i).get("GRRZ_ZWPF");
				}
				double d = Double.parseDouble(a) - Double.parseDouble(b);
				chazhi += d;
				out.println("<td align='center'><span style='color:red'>" + d
						+ "</span></td>");
			}

			out.println("<td align='center'>"
					+ getStatus_chinese(pageContext, Integer
							.parseInt(list_designe.get(i).get("status")))
					+ "</td>");// 状态

			out.println("</tr>");
		}// for

		out.println("<tr>");

		out.println("<td  align='center'>合计</td>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1) {
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<td align='center'>");
					if (list_tags.get(i).getSum_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number"))
							out.println(list_tags.get(i).getSum_i());
						else if (list_tags.get(i).getColumn_type().equals("float"))
							out.println(list_tags.get(i).getSum_f());

					}
					out.println("</td>");
				}
			}
		}
		// 当为工作日志表的时候增加一列显示差值
		if (list_tags.get(0).getTable_name().equals("GRRZ")) {
			out.println("<td align='center'><span style='color:red'>" + chazhi
					+ "</span></td>");
		}
		out.println("<td  colspan=3></td>");
		out.println("</tr>");

		out.println("</tbody>");
		out.println("</table>");
	}

	/**
	 * 终审列表显示
	 * 
	 * @param pageContext
	 * @param list_tags
	 * @param out
	 * @param moduleZDY
	 * @param list_designe
	 * @param actionName
	 * @param moduleManage
	 * @param final_
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public static void resolveHTMLForFinalSearchContactTags(String type,
			PageContext pageContext, List<Tags> list_tags, JspWriter out,
			ModuleZDY moduleZDY, List<Map<String, String>> list_designe,
			String actionName, ModuleManage moduleManage, int final_)
			throws IOException, NumberFormatException, ElException {
		// 显示table标题
		String width = "";
		int k = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)
				k++;
		}

		k = k * 100;
		if (k < 1000)
			width = " width='100%'";
		else
			width = " width='" + k + "px'";

		out.println("<table " + width
				+ " align='center' cellpadding='1' cellspacing='1'>");
		if (type != null && type.equals("1")) {
			out.println("<tr>");
			out.println("<td colspan=20><div id=\"Div_ToolsBar\"></div></td>");
			out.println("</tr>");
		}

		out.println("<tr>");
		out.println("<th width='20'></th>");
		// out.println("<th>操作</th>");
		// out.println("<th>备注</th>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)// display
			{
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<th><a href=\"javascript:columnsearch('"
							+ list_tags.get(i).getColumn_name() + "');\" >"
							+ list_tags.get(i).getName_display() + "</a></th>");
				}
			}
			// 当为工作日志表的时候增加一列显示差值
			if (i == list_tags.size() - 1)
				if (list_tags.get(i).getTable_name().equals("GRRZ")) {
					out.println("<th style='color:red'><span>差值</span></th>");
				}
		}// for

		out
				.println("<th><a href=\"javascript:columnsearch('t.status');\" >状态</a></th>");

		out.println("</tr>");
		out
				.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");

		// 显示列表值
		double chazhi = 0.0;
		for (int i = 0; i < list_designe.size(); i++) {

			out.println("<tr>");
			out
					.println("<td width='20' height='20' align='center'>"
							+ "<input type='checkbox' onclick='clickcheckbox();' value='"
							+ list_designe.get(i).get("id") + "' name='id_'/>"
							+ "</td>");


			for (int j = 0; j < list_tags.size(); j++) {
				if (list_tags.get(j).getDepartsearch_display() == 1) {
					if (!list_tags.get(j).getDisplay_type().equals("当前用户信息")) {
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (list_tags.get(j).getDisplay_type().equals("日期")) {
							str = ScheduleUtil.dateFormat(str, list_tags.get(j)
									.getTimeformat());
						} else {
							if (str == null)
								str = "";
						}
						if (list_tags.get(j).getJindutiao() == 1) {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='left'>");
						} else {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='center'>");
						}
						// 显示类型为附件的时候
						if (list_tags.get(j).getDisplay_type().equals("附件上传")) {
							String str2[] = str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
						// 百分比
						if (list_tags.get(j).getDisplay_type().equals("百分比")) {
							if (list_tags.get(j).getJindutiao() == 1) {// 显示进度条
								if (str == null || str.equals(""))
									str = "0";
								BigDecimal bg = new BigDecimal(str);
								str = String
										.valueOf(bg.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue());
								out
										.println("<center><table  border='0' cellspacing='0' ><tr><td><div  id='jindutiao_div__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
												+ str
												+ "%'  id='show_jindutiao__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "'/></div></td><td width=\"50\" align=\"left\"><span style='color:red;'>"
												+ str
												+ "%</span></td></tr></table></center>");
							} else {
								if (str.equals("")) {
									out.println("<label>" + 0 + "%</label>");
								} else {
									out.println("<label>" + str + "%</label>");
								}
							}
						} else if (list_tags.get(j).getDisplay_type().equals(
								"分级下拉选项")) {
							// 获取最后一级的选项
							if (!str.equals("")) {
								String[] ary = str.split("___");
								if (ary != null && ary.length > 0) {
									for (int m = 0; m < ary.length; m++) {
										if (m == ary.length - 1)
											str = ary[m].split("__")[1];
									}
								}
								if (str.equals("请选择"))
									str = "";
							}
							out.println("<label>" + str + "</label>");
						} else {
							out.println("<label>" + str + "</label>");
						}
						out.println("</td>");
					}
				}
			}

			// 当为工作日志表的时候增加一列显示差值
			if (list_tags.get(0).getTable_name().equals("GRRZ")) {
				String a = "0";
				String b = "0";
				if (list_designe.get(i).get("GRRZ_LDPF") != null
						&& !list_designe.get(i).get("GRRZ_LDPF").equals("")) {
					a = list_designe.get(i).get("GRRZ_LDPF");
				}
				if (list_designe.get(i).get("GRRZ_LDPF") != null
						&& !list_designe.get(i).get("GRRZ_ZWPF").equals("")) {
					b = list_designe.get(i).get("GRRZ_ZWPF");
				}
				double d = Double.parseDouble(a) - Double.parseDouble(b);
				chazhi += d;
				out.println("<td align='center'><span style='color:red'>" + d
						+ "</span></td>");
			}

			out.println("<td align='center'>"
					+ getStatus_chinese(pageContext, Integer
							.parseInt(list_designe.get(i).get("status")))
					+ "</td>");// 状态

			out.println("</tr>");
		}

		out.println("<tr>");

		out.println("<td  align='center'>合计</td>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1) {
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<td align='center'>");
					if (list_tags.get(i).getSum_display() == 1) {
						if (list_tags.get(i).getColumn_type().equals("number"))
							out.println(list_tags.get(i).getSum_i());
						else if (list_tags.get(i).getColumn_type().equals("float"))
							out.println(list_tags.get(i).getSum_f());

					}
					out.println("</td>");
				}
			}
		}
		// 当为工作日志表的时候增加一列显示差值
		if (list_tags.get(0).getTable_name().equals("GRRZ")) {
			out.println("<td align='center'><span style='color:red'>" + chazhi
					+ "</span></td>");
		}
		out.println("<td  colspan=3></td>");
		out.println("</tr>");
		out.println("</tbody>");
		out.println("</table>");
	}

	/**
	 * 自定义审核列表显示
	 * 
	 * @param pageContext
	 * @param list_tags
	 * @param out
	 * @param moduleZDY
	 * @param list_designe
	 * @param actionName
	 * @param moduleManage
	 * @param final_
	 * @param ca_small
	 * @param ca_big
	 * @param ca
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ElException
	 */
	public static void resolveHTMLForCustomAuditList(String type,
			PageContext pageContext, List<Tags> list_tags, JspWriter out,
			ModuleZDY moduleZDY, List<Map<String, String>> list_designe,
			String actionName, ModuleManage moduleManage, int final_,
			CustomAudit ca_small, CustomAudit ca_big, CustomAudit ca)
			throws IOException, NumberFormatException, ElException {
		// 显示table标题
		String width = "";
		int k = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)
				k++;
		}

		k = k * 100;
		if (k < 1000)
			width = " width='100%'";
		else
			width = " width='" + k + "px'";

		out.println("<table " + width
				+ " align='center' cellpadding='1' cellspacing='1'>");
		// if(type!=null&&type.equals("1")){
		// out.println("<tr>");
		// out.println("<td colspan=20><div id=\"Div_ToolsBar\"></div></td>");
		// out.println("</tr>");
		// }
		out.println("<tr>");
		out.println("<th width='20'></th>");
		out.println("<th>操作</th>");
		out.println("<th>备注</th>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)// display
			{
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<th><a href=\"javascript:columnsearch('"
							+ list_tags.get(i).getColumn_name() + "');\" >"
							+ list_tags.get(i).getName_display() + "</a></th>");
				}

			}
			// 当为工作日志表的时候增加一列显示差值
			if (i == list_tags.size() - 1)
				if (list_tags.get(i).getTable_name().equals("GRRZ")) {
					out.println("<th style='color:red'><span>差值</span></th>");
				}
		}// for

		out
				.println("<th><a href=\"javascript:columnsearch('t.status');\" >状态</a></th>");
		out
				.println("<th><a href=\"javascript:columnsearch('t.audituserid');\" >审核人</a></th>");
		out
				.println("<th><a href=\"javascript:columnsearch('t.auditdepid');\" >审核人部门</a></th>");
		out
				.println("<th><a href=\"javascript:columnsearch('t.audittime');\" >审核时间</a></th>");

		out.println("</tr>");
		out
				.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");

		// 显示列表值
		double chazhi = 0.0;
		for (int i = 0; i < list_designe.size(); i++) {

			out.println("<tr>");
			out.println("<td width='20' height='20' align='center'>"
					+ "<input type='checkbox' value='"
					+ list_designe.get(i).get("id") + "' name='id_'/>"
					+ "</td>");

			out.println("<td align='center'>");

			if (moduleZDY != null && moduleZDY.getAddjsp() != null
					&& !moduleZDY.getAddjsp().equals("")) {
				out.println("<a href='javascript:view_ZDY("
						+ list_designe.get(i).get("id") + ")'>查看</a>");
			} else {
				out.println("<a href='javascript:view("
						+ list_designe.get(i).get("id") + ")'>查看</a>");
			}

			if (new BaseAction().getSessionIntValue(ElConstants.SESSION_ROLE) == 1) {// 超管

				if (moduleZDY != null && moduleZDY.getUpdatejsp() != null
						&& !moduleZDY.getUpdatejsp().equals("")) {
					out.println("<a href='javascript:update_ZDY("
							+ list_designe.get(i).get("id") + "," + final_
							+ ",\"" + actionName + "\")'>修改</a>");
				} else {
					out.println("<a href='javascript:update_("
							+ list_designe.get(i).get("id") + "," + final_
							+ ",\"" + actionName + "\")'>修改</a>");
				}

				out.println("<a href='javascript:del("
						+ list_designe.get(i).get("id") + ")'>删除</a>");
				out.println("<br>");
				out.println("<a href='javascript:verify_pass("
						+ list_designe.get(i).get("id") + ",\""
						+ ca_big.getAuditOrder() + "\")'>"
						+ ca_big.getAuditName() + "通过</a><br>");
				out.println("<a href='javascript:verify_nopass("
						+ list_designe.get(i).get("id") + ",\""
						+ ca_big.getAuditOrder() + "\")'>"
						+ ca_big.getAuditName() + "不通过</a><br>");
			} else {
				if (list_designe.get(i).get("status").equals("0")) {// 已创建
					if (ca != null
							&& ca_big != null
							&& ca.getAuditOrder() != null
							&& ca_big.getAuditOrder() != null
							&& ca.getAuditOrder()
									.equals(ca_big.getAuditOrder())) {// 审核级别最大用户
						out.println("<br>");
						out.println("<a href='javascript:verify_pass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca_big.getAuditOrder() + "\")'>"
								+ ca_big.getAuditName() + "通过</a><br>");
						out.println("<a href='javascript:verify_nopass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca_big.getAuditOrder() + "\")'>"
								+ ca_big.getAuditName() + "不通过</a><br>");
					}
				}
				if (list_designe.get(i).get("status").equals("6")) {// 初审通过
					if (ca != null
							&& ca_small != null
							&& ca.getAuditOrder() != null
							&& ca_small.getAuditOrder() != null
							&& ca.getAuditOrder().equals(
									ca_small.getAuditOrder())) {// 审核级别最小用户
						out.println("<br>");
						out.println("<a href='javascript:verify_pass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca_small.getAuditOrder() + "\")'>"
								+ ca_small.getAuditName() + "通过</a><br>");
						out.println("<a href='javascript:verify_nopass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca_small.getAuditOrder() + "\")'>"
								+ ca_small.getAuditName() + "不通过</a><br>");
					}
					if (ca != null
							&& ca_big != null
							&& ca.getAuditOrder() != null
							&& ca_big.getAuditOrder() != null
							&& ca.getAuditOrder()
									.equals(ca_big.getAuditOrder())) {// 审核级别最大用户
						out.println("<br>");
						out.println("<a href='javascript:verify_pass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca_big.getAuditOrder() + "\")'>"
								+ ca_big.getAuditName() + "通过</a><br>");
						out.println("<a href='javascript:verify_nopass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca_big.getAuditOrder() + "\")'>"
								+ ca_big.getAuditName() + "不通过</a><br>");
					}
				}
				if (list_designe.get(i).get("status").equals("9")) {// 终审通过
					if(ca != null
							&& ca_big != null
							&& ca.getAuditOrder() != null
							&& ca_big.getAuditOrder() != null
							&& ca.getAuditOrder()
									.equals(ca_big.getAuditOrder())|| new BaseAction().getSessionIntValue(ElConstants.SESSION_ROLE) == 1){
					out.println("<a href='javascript:update_("
							+ list_designe.get(i).get("id") + "," + final_
							+ ",\"" + actionName + "\")'>修改</a>");
					out.println("<a href='javascript:del("
							+ list_designe.get(i).get("id") + ")'>删除</a>");
					}
					if (ca != null
							&& ca_big != null
							&& ca.getAuditOrder() != null
							&& ca_big.getAuditOrder() != null
							&& ca.getAuditOrder()
									.equals(ca_big.getAuditOrder())) {// 审核级别最大用户
						out.println("<br>");
						out.println("<a href='javascript:verify_pass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca_big.getAuditOrder() + "\")'>"
								+ ca_big.getAuditName() + "通过</a><br>");
						out.println("<a href='javascript:verify_nopass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca_big.getAuditOrder() + "\")'>"
								+ ca_big.getAuditName() + "不通过</a><br>");
					}

				}
				if (Integer.parseInt(list_designe.get(i).get("status")) > 10) {// 自定义审核
					// 一审通过，当前审核级别为二级审核；二审通过，当前审核为三级审核
					if (ca != null
							&& ca.getAuditOrder() != null
							&& Integer.parseInt(ca.getAuditOrder()) * 2 + 10 == Integer
									.parseInt(list_designe.get(i).get("status")) + 2) {
						out.println("<br>");
						out.println("<a href='javascript:verify_pass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca.getAuditOrder() + "\")'>"
								+ ca.getAuditName() + "通过</a><br>");
						out.println("<a href='javascript:verify_nopass("
								+ list_designe.get(i).get("id") + ",\""
								+ ca.getAuditOrder() + "\")'>"
								+ ca.getAuditName() + "不通过</a><br>");
					}
				}
				if (list_designe.get(i).get("status").equals("2")) {
					out.println("<a href='javascript:allow_update("
							+ list_designe.get(i).get("id") + ")'>允许修改</a>");
					out.println("<a href='javascript:noallow_update("
							+ list_designe.get(i).get("id") + ")'>不允许修改</a>");
				}
				if (list_designe.get(i).get("status").equals("3")) {
					out.println("<a href='javascript:allow_del("
							+ list_designe.get(i).get("id") + ")'>允许删除</a>");
					out.println("<a href='javascript:noallow_del("
							+ list_designe.get(i).get("id") + ")'>不允许删除</a>");
				}
			}

			out.println("</td>");
			out.println("<td align='center'><a href=\"javascript:show_beizhu("
					+ list_designe.get(i).get("id")
					+ ");\" class='textbg6'>查看备注</a></td>");// 审核时间

			for (int j = 0; j < list_tags.size(); j++) {
				if (list_tags.get(j).getDepartsearch_display() == 1) {
					if (!list_tags.get(j).getDisplay_type().equals("当前用户信息")) {
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (list_tags.get(j).getDisplay_type().equals("日期")) {
							str = ScheduleUtil.dateFormat(str, list_tags.get(j)
									.getTimeformat());
						} else {
							if (str == null)
								str = "";
						}
						if (list_tags.get(j).getJindutiao() == 1) {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='left'>");
						} else {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='center'>");
						}
						// 显示类型为附件的时候
						if (list_tags.get(j).getDisplay_type().equals("附件上传")) {
							String str2[] = str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
						// 百分比
						if (list_tags.get(j).getDisplay_type().equals("百分比")) {
							if (list_tags.get(j).getJindutiao() == 1) {// 显示进度条
								if (str == null || str.equals(""))
									str = "0";
								BigDecimal bg = new BigDecimal(str);
								str = String
										.valueOf(bg.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue());
								out
										.println("<center><table  border='0' cellspacing='0' ><tr><td><div  id='jindutiao_div__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
												+ str
												+ "%'  id='show_jindutiao__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "'/></div></td><td width=\"50\" align=\"left\"><span style='color:red;'>"
												+ str
												+ "%</span></td></tr></table></center>");
							} else {
								if (str.equals("")) {
									out.println("<label>" + 0 + "%</label>");
								} else {
									out.println("<label>" + str + "%</label>");
								}
							}
						} else if (list_tags.get(j).getDisplay_type().equals(
								"分级下拉选项")) {
							// 获取最后一级的选项
							if (!str.equals("")) {
								String[] ary = str.split("___");
								if (ary != null && ary.length > 0) {
									for (int m = 0; m < ary.length; m++) {
										if (m == ary.length - 1)
											str = ary[m].split("__")[1];
									}
								}
								if (str.equals("请选择"))
									str = "";
							}
							out.println("<label>" + str + "</label>");
						} else {
							out.println("<label>" + str + "</label>");
						}
						out.println("</td>");
					}
				}
			}

			// 当为工作日志表的时候增加一列显示差值
			if (list_tags.get(0).getTable_name().equals("GRRZ")) {
				String a = "0";
				String b = "0";
				if (!list_designe.get(i).get("GRRZ_LDPF").equals("")) {
					a = list_designe.get(i).get("GRRZ_LDPF");
				}
				if (!list_designe.get(i).get("GRRZ_ZWPF").equals("")) {
					b = list_designe.get(i).get("GRRZ_ZWPF");
				}
				double d = Double.parseDouble(a) - Double.parseDouble(b);
				chazhi += d;
				out.println("<td align='center'><span style='color:red'>" + d
						+ "</span></td>");
			}

			out.println("<td align='center'>"
					+ getStatus_chinese(pageContext, Integer
							.parseInt(list_designe.get(i).get("status")))
					+ "</td>");// 状态
			out.println("<td align='center'>"
					+ list_designe.get(i).get("e1_username") + "</td>");// 审核人
			out.println("<td align='center'>"
					+ list_designe.get(i).get("d1_name") + "</td>");// 审核人部门
			out.println("<td align='center'>"
					+ list_designe.get(i).get("audittime") + "</td>");// 审核时间

			out.println("</tr>");
		}// for
		out.println("<tr>");

		out.println("<td  align='center'>合计</td>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1) {
				out.println("<td align='center'>");
				if (list_tags.get(i).getSum_display() == 1) {
					if (list_tags.get(i).getColumn_type().equals("number"))
						out.println(list_tags.get(i).getSum_i());
					else if (list_tags.get(i).getColumn_type().equals("float"))
						out.println(list_tags.get(i).getSum_f());

				}
				out.println("</td>");
			}
		}
		// 当为工作日志表的时候增加一列显示差值
		if (list_tags.get(0).getTable_name().equals("GRRZ")) {
			out.println("<td align='center'><span style='color:red'>" + chazhi
					+ "</span></td>");
		}
		out.println("<td  colspan=6></td>");
		out.println("</tr>");
		out.println("</tbody>");
		out.println("</table>");
	}

	/**
	 * 数据申请列表显示
	 * 
	 * @param list_tags
	 * @param list_designe
	 * @param out
	 * @throws IOException
	 */
	public static void resolveHTMLForDataApplication(String type,
			List<Tags> list_tags, List<Map<String, String>> list_designe,
			JspWriter out) throws IOException {
		// 显示table标题
		String width = "";
		int k = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)
				k++;
		}

		k = k * 100;
		if (k < 1000)
			width = " width='100%'";
		else
			width = " width='" + k + "px'";

		out.println("<table " + width
				+ " align='center' cellpadding='1' cellspacing='1'>");
		if (type != null && type.equals("1")) {
			out.println("<tr>");
			out.println("<td colspan=20><div id=\"Div_ToolsBar\"></div></td>");
			out.println("</tr>");
		}
		out.println("<tr>");
		out.println("<th width='20'></th>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)// display
			{
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<th><a href=\"javascript:columnsearch('"
							+ list_tags.get(i).getColumn_name() + "');\" >"
							+ list_tags.get(i).getName_display() + "</a></th>");
				}
			}
			// 当为工作日志表的时候增加一列显示差值
			if (i == list_tags.size() - 1)
				if (list_tags.get(i).getTable_name().equals("GRRZ")) {
					out.println("<th style='color:red'><span>差值</span></th>");
				}
		}// for

		out
				.println("<th><a href=\"javascript:columnsearch('t.status');\" >状态</a></th>");
		// out.println("<th >申请</th>");
		// out.println("<th >查看</th>");
		// out.println("<th >学习</th>");

		out.println("</tr>");
		out
				.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");

		// 显示列表值
		double chazhi = 0.0;
		for (int i = 0; i < list_designe.size(); i++) {

			out.println("<tr>");
			out.println("<td width='20' height='20' align='center'>"
					+ "<input type='checkbox' value='"
					+ list_designe.get(i).get("id") + "' name='id_'/>"
					+ "</td>");

			for (int j = 0; j < list_tags.size(); j++) {
				if (list_tags.get(j).getDepartsearch_display() == 1) {
					if (!list_tags.get(j).getDisplay_type().equals("当前用户信息")) {
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (list_tags.get(j).getDisplay_type().equals("日期")) {
							str = ScheduleUtil.dateFormat(str, list_tags.get(j)
									.getTimeformat());
						} else {
							if (str == null)
								str = "";
						}
						if (list_tags.get(j).getJindutiao() == 1) {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='left'>");
						} else {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='center'>");
						}
						// 显示类型为附件的时候
						if (list_tags.get(j).getDisplay_type().equals("附件上传")) {
							String str2[] = str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
						// 百分比
						if (list_tags.get(j).getDisplay_type().equals("百分比")) {
							if (list_tags.get(j).getJindutiao() == 1) {// 显示进度条
								if (str == null || str.equals(""))
									str = "0";
								BigDecimal bg = new BigDecimal(str);
								str = String
										.valueOf(bg.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue());
								out
										.println("<center><table  border='0' cellspacing='0' ><tr><td><div  id='jindutiao_div__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
												+ str
												+ "%'  id='show_jindutiao__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "'/></div></td><td width=\"50\" align=\"left\"><span style='color:red;'>"
												+ str
												+ "%</span></td></tr></table></center>");
							} else {
								if (str.equals("")) {
									out.println("<label>" + 0 + "%</label>");
								} else {
									out.println("<label>" + str + "%</label>");
								}
							}
						} else if (list_tags.get(j).getDisplay_type().equals(
								"分级下拉选项")) {
							// 获取最后一级的选项
							if (!str.equals("")) {
								String[] ary = str.split("___");
								if (ary != null && ary.length > 0) {
									for (int m = 0; m < ary.length; m++) {
										if (m == ary.length - 1)
											str = ary[m].split("__")[1];
									}
								}
								if (str.equals("请选择"))
									str = "";
							}
							out.println("<label>" + str + "</label>");
						} else {
							out.println("<label>" + str + "</label>");
						}
						out.println("</td>");
					}
				}
			}

			// 当为工作日志表的时候增加一列显示差值
			if (list_tags.get(0).getTable_name().equals("GRRZ")) {
				String a = "0";
				String b = "0";
				if (!list_designe.get(i).get("GRRZ_LDPF").equals("")) {
					a = list_designe.get(i).get("GRRZ_LDPF");
				}
				if (!list_designe.get(i).get("GRRZ_ZWPF").equals("")) {
					b = list_designe.get(i).get("GRRZ_ZWPF");
				}
				double d = Double.parseDouble(a) - Double.parseDouble(b);
				chazhi += d;
				out.println("<td align='center'><span style='color:red'>" + d
						+ "</span></td>");
			}

			if (list_designe.get(i).get("tdastatus") == null) {
				out.println("<td align='center'>未申请</td>");// 状态
			} else {
				if (list_designe.get(i).get("tdastatus").equals("已审核"))
					out.println("<td align='center'><span style='color:red'>"
							+ list_designe.get(i).get("tdastatus")
							+ "</span></td>");// 状态
				else
					out.println("<td align='center'>"
							+ list_designe.get(i).get("tdastatus") + "</td>");// 状态
			}

			// out.println("<td align='center'>");
			// if(list_designe.get(i).get("application")!=null){
			// if(Integer.parseInt(list_designe.get(i).get("application"))==3 ||
			// Integer.parseInt(list_designe.get(i).get("application"))==4){
			// out.println("<a
			// href='javascript:application("+Integer.parseInt(list_designe.get(i).get("id"))+");'
			// >申请</a>");
			// }
			// }else{
			// out.println("<a
			// href='javascript:application("+Integer.parseInt(list_designe.get(i).get("id"))+");'
			// >申请</a>");
			// }
			// out.println("</td>");
			//			
			// out.println("<td align='center'>");
			// if(Integer.parseInt(list_designe.get(i).get("application"))==1 ||
			// Integer.parseInt(list_designe.get(i).get("application"))==2){
			// out.println("<a
			// href='javascript:view("+list_designe.get(i).get("id")+","+list_designe.get(i).get("userid")+");'
			// >查看</a>");
			// }
			// out.println("</td>");
			//			
			// out.println("<td align='center'>");
			// if(Integer.parseInt(list_designe.get(i).get("application"))==1 ||
			// Integer.parseInt(list_designe.get(i).get("application"))==2){
			// out.println("<a
			// href='javascript:learn("+list_designe.get(i).get("id")+","+list_designe.get(i).get("userid")+")'
			// >学习</a>");
			// }
			// out.println("</td>");

			out.println("</tr>");
		}

		out.println("</tbody>");
		out.println("</table>");
	}

	/**
	 * 数据分配列表显示
	 * 
	 * @param pageContext
	 * @param list_tags
	 * @param list_designe
	 * @param out
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ElException
	 */
	public static void resolveHTMLForDataAllocation(String type,
			PageContext pageContext, List<Tags> list_tags,
			List<Map<String, String>> list_designe, JspWriter out)
			throws NumberFormatException, IOException, ElException {
		// 显示table标题
		String width = "";
		int k = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)
				k++;
		}

		k = k * 100;
		if (k < 1000)
			width = " width='100%'";
		else
			width = " width='" + k + "px'";

		out.println("<table " + width
				+ " align='center' cellpadding='1' cellspacing='1'>");
		if (type != null && type.equals("1")) {
			out.println("<tr>");
			out.println("<td colspan=20><div id=\"Div_ToolsBar\"></div></td>");
			out.println("</tr>");
		}
		out.println("<tr>");
		out.println("<th width='20'></th>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getDepartsearch_display() == 1)// display
			{
				if (!list_tags.get(i).getDisplay_type().equals("当前用户信息")) {
					out.println("<th><a href=\"javascript:columnsearch('"
							+ list_tags.get(i).getColumn_name() + "');\" >"
							+ list_tags.get(i).getName_display() + "</a></th>");
				}
			}
			// 当为工作日志表的时候增加一列显示差值
			if (i == list_tags.size() - 1)
				if (list_tags.get(i).getTable_name().equals("GRRZ")) {
					out.println("<th style='color:red'><span>差值</span></th>");
				}
		}// for

		out
				.println("<th><a href=\"javascript:columnsearch('t.status');\" >状态</a></th>");
		out.println("<th colspan=>分配人数</th>");
		// out.println("<th colspan=>操作</th>");

		out.println("</tr>");
		out
				.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");

		// 显示列表值
		double chazhi = 0.0;
		for (int i = 0; i < list_designe.size(); i++) {

			out.println("<tr>");
			out
					.println("<td width='20' height='20' align='center'>"
							+ "<input type='checkbox' onclick='clickcheckbox();' value='"
							+ list_designe.get(i).get("id") + "' name='id_'/>"
							+ "</td>");

			for (int j = 0; j < list_tags.size(); j++) {
				if (list_tags.get(j).getDepartsearch_display() == 1) {
					if (!list_tags.get(j).getDisplay_type().equals("当前用户信息")) {
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (list_tags.get(j).getDisplay_type().equals("日期")) {
							str = ScheduleUtil.dateFormat(str, list_tags.get(j)
									.getTimeformat());
						} else {
							if (str == null)
								str = "";
						}
						if (list_tags.get(j).getJindutiao() == 1) {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='left'>");
						} else {
							out.println("<td  id='"
									+ list_tags.get(j).getColumn_name() + ":"
									+ j + "' align='center'>");
						}
						// 显示类型为附件的时候
						if (list_tags.get(j).getDisplay_type().equals("附件上传")) {
							String str2[] = str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
						// 百分比
						if (list_tags.get(j).getDisplay_type().equals("百分比")) {
							if (list_tags.get(j).getJindutiao() == 1) {// 显示进度条
								if (str == null || str.equals(""))
									str = "0";
								BigDecimal bg = new BigDecimal(str);
								str = String
										.valueOf(bg.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.doubleValue());
								out
										.println("<center><table  border='0' cellspacing='0' ><tr><td><div  id='jindutiao_div__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
												+ str
												+ "%'  id='show_jindutiao__"
												+ list_tags.get(j)
														.getColumn_name()
												+ ":"
												+ j
												+ "'/></div></td><td width=\"50\" align=\"left\"><span style='color:red;'>"
												+ str
												+ "%</span></td></tr></table></center>");
							} else {
								if (str.equals("")) {
									out.println("<label>" + 0 + "%</label>");
								} else {
									out.println("<label>" + str + "%</label>");
								}
							}
						} else if (list_tags.get(j).getDisplay_type().equals(
								"分级下拉选项")) {
							// 获取最后一级的选项
							if (!str.equals("")) {
								String[] ary = str.split("___");
								if (ary != null && ary.length > 0) {
									for (int m = 0; m < ary.length; m++) {
										if (m == ary.length - 1)
											str = ary[m].split("__")[1];
									}
								}
								if (str.equals("请选择"))
									str = "";
							}
							out.println("<label>" + str + "</label>");
						} else {
							out.println("<label>" + str + "</label>");
						}
						out.println("</td>");
					}
				}
			}

			// 当为工作日志表的时候增加一列显示差值
			if (list_tags.get(0).getTable_name().equals("GRRZ")) {
				String a = "0";
				String b = "0";
				if (!list_designe.get(i).get("GRRZ_LDPF").equals("")) {
					a = list_designe.get(i).get("GRRZ_LDPF");
				}
				if (!list_designe.get(i).get("GRRZ_ZWPF").equals("")) {
					b = list_designe.get(i).get("GRRZ_ZWPF");
				}
				double d = Double.parseDouble(a) - Double.parseDouble(b);
				chazhi += d;
				out.println("<td align='center'><span style='color:red'>" + d
						+ "</span></td>");
			}

			out.println("<td align='center'>"
					+ getStatus_chinese(pageContext, Integer
							.parseInt(list_designe.get(i).get("status")))
					+ "</td>");// 状态

			out.println("<td align='center'>已分配人数："
					+ "<span style='color:red'>【"
					+ list_designe.get(i).get("dataallocation")
					+ "】</span></td>");
			// out.println("<td align='center'><a
			// href='javascript:fenpei("+Integer.parseInt(list_designe.get(i).get("id"))+");'
			// >分配</a></td>");

			out.println("</tr>");
		}

		out.println("</tbody>");
		out.println("</table>");
	}

	/**
	 * 查看页面上显示相关字段完整显示
	 * 
	 * @param out
	 * @param pageContext
	 * @param list_designe_relate
	 * @param list_tags_relate
	 * @param list_tags
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ElException
	 */
	public static void resolveHTMLForShowlistRelate(JspWriter out,
			PageContext pageContext,
			Map<String, List<Map<String, String>>> list_designe_relate,
			Map<String, List<Tags>> list_tags_relate, List<Tags> list_tags)
			throws NumberFormatException, IOException, ElException {
		// 显示列表值
		for (String key : list_designe_relate.keySet()) {
			List<Map<String, String>> list_map = list_designe_relate.get(key);
			List<Tags> ts = list_tags_relate.get(key);
			if (list_map != null && ts != null && list_map.size() != 0
					&& ts.size() != 0) {
				// 显示table标题
				String width = "";
				width = " width='100%'";
				out.println("<table " + width
						+ " align='center' cellpadding='1' cellspacing='1'>");
				for (int i = 0; i < list_tags.size(); i++) {
					if (list_tags.get(i).getDisplay_type().equals("相关字段")
							&& list_tags.get(i).getRelateIsShowComplete() == 1
							&& list_tags.get(i).getDefault_value().split("==")[0]
									.equals(ts.get(0).getTable_name())) {
						out.println("<caption>"
								+ list_tags.get(i).getName_display()
								+ "</caption>");
					}
				}
				out.println("<tr>");
				for (int i = 0; i < ts.size(); i++) {
					if (ts.get(i).getList_display() == 1)// display
					{
						if (ts.get(i).getBiaojianqiuhe_check() != 1)
							if (!ts.get(i).getDisplay_type().equals("当前用户信息")) {
								out.println("<th><a >"
										+ ts.get(i).getName_display()
										+ "</a></th>");
							}
					}
				}// for

				out.println("<th><a  >状态</a></th>");
				out.println("<th>操作</th>");
				out.println("</tr>");

				out
						.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");

				for (int i = 0; i < list_map.size(); i++) {
					out.println("<tr>");

					for (int j = 0; j < ts.size(); j++) {
						if (ts.get(j).getList_display() == 1)
							if (ts.get(j).getBiaojianqiuhe_check() != 1) {
								{
									if (!ts.get(j).getDisplay_type().equals(
											"当前用户信息")) {
										String str = list_map.get(i).get(
												ts.get(j).getColumn_name());
										if (ts.get(j).getDisplay_type().equals(
												"日期")) {
											str = ScheduleUtil.dateFormat(str,
													list_tags.get(j)
															.getTimeformat());
										} else {
											if (str == null)
												str = "";
										}
										if (str == null)
											str = "";
										if (ts.get(j).getJindutiao() == 1) {
											out.println("<td align='left'>");
										} else {
											out.println("<td  align='center'>");
										}
										// 显示类型为附件的时候
										if (ts.get(j).getDisplay_type().equals(
												"附件上传")) {
											String str2[] = str.split("==");
											out.println("<label>" + str2[0]
													+ "</label>");
										}
										// 百分比
										if (ts.get(j).getDisplay_type().equals(
												"百分比")) {
											if (ts.get(j).getJindutiao() == 1) {// 显示进度条
												BigDecimal bg = new BigDecimal(
														str);
												str = String
														.valueOf(bg
																.setScale(
																		2,
																		BigDecimal.ROUND_HALF_UP)
																.doubleValue());
												out
														.println("<center><table border='0' cellspacing='0' ><tr><td><div  id='jindutiao_div__"
																+ list_tags
																		.get(j)
																		.getColumn_name()
																+ ":"
																+ j
																+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
																+ str
																+ "%'  id='show_jindutiao__"
																+ list_tags
																		.get(j)
																		.getColumn_name()
																+ ":"
																+ j
																+ "'/></div></td><td width=\"50\" align=\"left\"><span style='color:red;'>"
																+ str
																+ "%</span></td></tr></table></center>");
											} else {
												if (str.equals("")) {
													out.println("<label>" + 0
															+ "%</label>");
												} else {
													out.println("<label>" + str
															+ "%</label>");
												}
											}
										} else if (ts.get(j).getDisplay_type()
												.equals("分级下拉选项")) {
											// 获取最后一级的选项
											if (!str.equals("")) {
												String[] ary = str.split("___");
												if (ary != null
														&& ary.length > 0) {
													for (int m = 0; m < ary.length; m++) {
														if (m == ary.length - 1)
															str = ary[m]
																	.split("__")[1];
													}
												}
												if (str.equals("请选择"))
													str = "";
											}
											out.println("<label>" + str
													+ "</label>");
										} else {
											out.println("<label>" + str
													+ "</label>");
										}
										out.println("</td>");
									}
								}
							}
					}
					out.println("<td align='center'>"
							+ getStatus_chinese(pageContext, Integer
									.parseInt(list_map.get(i).get("status")))
							+ "</td>");// 状态
					out.println("<td align='center'>");
					out.println("<a href='viewContactTags.action?id="
							+ list_map.get(i).get("id") + "&tablename="
							+ ts.get(0).getTable_name() + "'>查看</a>");
					out.println("</td>");
					out.println("</tr>");
				}
				out.println("</tbody>");
				out.println("</table>");
			}
		}
	}

}
