<%@ page language="java" pageEncoding="gbk"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
	response.setHeader("Content-disposition",
			"attachment; filename=ExportDepartmentComparison.xls");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
	</HEAD>
	<body>

		<table width="700px" align="center" border="1"  >
			<tr>
				<th align="center">
					排行
				</th>
				<th align="center">
					部门
				</th>
				<th align="center">
					应考人数
				</th>
				<th align="center">
					缺考人数
				</th>
				<th align="center">
					及格人数
				</th>
				<th align="center">
					及格率
				</th>
				<th align="center">
					平均分
				</th>
				<s:iterator value="myExamPapers">
					<th height="30" align="center" style="color: red;">
						<s:property value="examPaper.title" />
					</th>
				</s:iterator>
			</tr>
			<s:iterator value="departments" status="st">
				<s:if test="userCount==0">
					<tr>
						<td align="center">
							<s:property value="#st.index+1" />
						</td>
						<td align="center">
							<s:property value="name" />
						</td>
						<td align="center">
							-
						</td>
						<td align="center">
							-
						</td>
						<td align="center">
							-
						</td>
						<td align="center">
							-
						</td>
						<td align="center">
							-
						</td>
						<s:iterator value="myexampapers">
							<td align="center" style="color: red;">
								-
							</td>
						</s:iterator>
					</tr>
				</s:if>
				<s:else>
					<tr>
						<td align="center">
							<s:property value="#st.index+1" />
						</td>
						<td align="center">
							<s:property value="name" />
						</td>
						<td align="center">
							<s:property value="userCount" />
						</td>
						<td align="center">
							<s:property value="userCount_" />
						</td>
						<td align="center">
							<s:property value="userCount_jg" />
						</td>
						<td align="center">
							<s:property value="ratio" />

						</td>
						<td align="center">
							<s:property value="avg" />
						</td>
						<s:iterator value="myexampapers">
							<td align="center" style="color: red;">
								<s:property value="avgscore" />
							</td>
							 
						</s:iterator>
					</tr>
				</s:else>
			</s:iterator>
		</table>

	
	</body>
</HTML>
