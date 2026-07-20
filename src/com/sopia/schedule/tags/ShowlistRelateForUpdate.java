package com.sopia.schedule.tags;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElException;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.Tags;

public class ShowlistRelateForUpdate extends TagSupport {
	private String listname;
	private String delaction;
	private String viewaction;
	private String updateaction;

	public String getViewaction() {
		return viewaction;
	}

	public void setViewaction(String viewaction) {
		this.viewaction = viewaction;
	}

	public String getUpdateaction() {
		return updateaction;
	}

	public void setUpdateaction(String updateaction) {
		this.updateaction = updateaction;
	}

	public boolean getBetweenStatus(CustomAudit ca_small, CustomAudit ca_big,
			int status) {
		boolean flag = false;
		if ((status >= Integer.parseInt(ca_small.getAuditOrder()) * 2 + 10)
				|| (status <= Integer.parseInt(ca_big.getAuditOrder()) * 2 + 10 + 1)) {
			flag = true;
		}
		return flag;
	}

	public String getStatus_chinese(int status) throws ElException {
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

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			List<Tags> list_tags = (List<Tags>) request
					.getAttribute("list_tags");

			Map<String, List<Tags>> list_tags_relate = (Map<String, List<Tags>>) request
					.getAttribute("list_tags_relate");
			Map<String, List<Map<String, String>>> list_designe_relate = (Map<String, List<Map<String, String>>>) request
					.getAttribute("list_designe_relate");


			// 显示列表值
			for (String key : list_designe_relate.keySet()) {
				List<Map<String, String>> list_map = list_designe_relate
						.get(key);
				List<Tags> ts = list_tags_relate.get(key);
				// 显示table标题
				String width = "";
				int k = 0;
				for (int i = 0; i < ts.size(); i++) {
					if (ts.get(i).getList_display() == 1)
						if (ts.get(i).getBiaojianqiuhe_check() != 1)
							k++;
				}

				k = k * 100;
				if (k < 1000)
					width = " width='100%'";
				else
					width = " width='" + k + "px'";

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
							 out.println("<th><a href=\"javascript:columnsearch('"
								+ ts.get(i).getColumn_name() + "');\" >"
								+ ts.get(i).getName_display() + "</a></th>");

					 }
				}// for

				out
						.println("<th><a href=\"javascript:columnsearch('status');\" >状态</a></th>");
				out.println("<th>操作</th>");
				out.println("</tr>");

				out
						.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");

				String biaojianqiuhe_table = "";// 表间求和的表名
				String biaojianqiuhe_column_id = "";// 表间求和的列id
				// 显示列表值
				for (int i = 0; i < list_map.size(); i++) {
					out.println("<tr>");

					for (int j = 0; j < ts.size(); j++) {
						if (ts.get(j).getBiaojianqiuhe_check() == 1) {
							// biaojianqiuhe_column =
							// list_tags_relate.get(j).getColumn_name();
							biaojianqiuhe_table = ts.get(j)
									.getBiaojianqiuhe_tablename();
							biaojianqiuhe_column_id = ts.get(j)
									.getBiaojianqiuhe_column();
						}
						if (ts.get(j).getList_display() == 1) {
							String str = list_map.get(i).get(
									ts.get(j).getColumn_name());
							if (str == null)
								str = "";
							if (ts.get(j).getJindutiao() == 1) {
								out.println("<td align='left'>");
							} else {
								out.println("<td  align='center'>");
							}
							// 显示类型为附件的时候
							if (ts.get(j).getDisplay_type().equals("附件上传")) {
								String str2[] = str.split("==");
								out.println("<label>" + str2[0] + "</label>");
							}
							// 百分比
							if (ts.get(j).getDisplay_type().equals("百分比")) {
								if (ts.get(j).getJindutiao() == 1) {// 显示进度条
									BigDecimal bg = new BigDecimal(str);
									str = String.valueOf(bg.setScale(2,
											BigDecimal.ROUND_HALF_UP)
											.doubleValue());
									out
											.println("<center><table width='100px' border='0' cellspacing='1' ><tr><td><div  id='jindutiao_div__"
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
													+ "'/></div></td><td><center><span style='color:red;'>"
													+ str
													+ "%</span></center></td></tr></table></center>");
								} else {
									if (str.equals("")) {
										out
												.println("<label>" + 0
														+ "%</label>");
									} else {
										out.println("<label>" + str
												+ "%</label>");
									}
								}
							} else {
								if (ts.get(j).getBiaojianqiuhe_check() != 1) {
									if (ts.get(j).getFromResultTable() != null
											&& ts.get(j).getFromResultTable()
													.equals("1")) {
										out
												.println("<input type='text' onclick='jisuan(this);' id='"
														+ ts
																.get(j)
																.getTable_name()
														+ "=="
														+ ts
																.get(j)
																.getColumn_name()
														+ "=="
														+ i
														+ "' value='"
														+ str
														+ "'/>");
									} else {
										out.println("<span id='"
												+ ts.get(j).getTable_name()
												+ "=="
												+ ts.get(j).getColumn_name()
												+ "==" + i + "'>" + str
												+ "</span>");
									}
								}

							}
							out.println("</td>");
						}
						// }
					}
					out.println("<td align='center'>");
					out.println(getStatus_chinese(Integer.parseInt(list_map
							.get(i).get("status"))));
					out.println("</td>");
					out.println("<span style='display:none' id='"
							+ ts.get(0).getTable_name() + "==id==" + i + "'>"
							+ list_map.get(i).get("id") + "</span>");
					out.println("<td align='center'>");
					out.println("<a href='viewContactTags.action?id="
							+ list_map.get(i).get("id") + "&tablename="
							+ ts.get(0).getTable_name() + "'>查看</a>");
					out.println("</td>");
					out.println("</tr>");
				}// for
				// String tr_ = "";
				// for(int i = 0; i < list_tags_relate.size(); i++){
				// if(ts.get(i).getBiaojianqiuhe_check() == 1){
				// tr_ += "<tr><td
				// align='center'>"+ts.get(i).getName_display()+"</td><td
				// align='center'><input value='"+f+"' type='text'
				// name='"+biaojianqiuhe_table+"=="+map_.get("1")+"==0'
				// id='"+biaojianqiuhe_table+"=="+map_.get("1")+"==0'
				// onclick='biaojianqiuhe_calculate(this,"+list_designe_relate.size()+");'/></td></tr>";
				// }
				// }
				// out.println(tr_);
				out.println("</tbody>");
				out.println("</table>");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public String getDelaction() {
		return delaction;
	}

	public void setDelaction(String delaction) {
		this.delaction = delaction;
	}

}
