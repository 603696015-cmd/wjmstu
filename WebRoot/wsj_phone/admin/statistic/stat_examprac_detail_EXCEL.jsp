<%@ page language="java" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
	response.setHeader("Content-disposition",
			"attachment; filename=PracticeDetails.xls");
%>
		<!-- 内容 -->
			<table border="1"
				>
				<tr>
					<th width="130" align="center" >
						考生姓名
					</th>
					<th align="center" >
						部门
					</th>
					<th align="center" >
						批次名称
					</th>
					<th align="center" >
						平均分
					</th>
					<th align="center" >
						及格
					</th>
				</tr>
				<tbody>
					<s:iterator value="myexampracs">
						<tr>
							<td>
								<s:property value="tester.realname" />
							</td>
							<td align="center" >
								<s:property value="tester.department.name" />
							</td>
							<td align="center" >
								<s:property value="tester.department.name" />
							</td>
							<td align="center" >
								<s:property value="avgscore" />
							</td>
							<td align="center" >
								<s:if test="avgscore>60">及格</s:if>
								<s:else>不及格</s:else>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		<!-- 内容 -->