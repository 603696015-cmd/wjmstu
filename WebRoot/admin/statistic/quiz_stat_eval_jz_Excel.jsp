<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
	response.setHeader("Content-disposition",
			"attachment; filename=ExportJzComparison.xls");
%>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<table align="center" border="1">
			<caption>
				考核各单位情况汇总表
			</caption>
			<tr>
				<th align="center">
					排行
				</th>
				<th align="center">
					工种
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
					不及格人数
				</th>
				<th align="center">
					及格率
				</th>
				<th align="center">
					平均分
				</th>
				<s:iterator value="examPapers">
					<th height="30" align="center" style="color: red;">
						<s:property value="title" />
					</th>
				</s:iterator>
			</tr>
			<s:iterator value="jzs1" status="st">
				<s:if test="userCount==0">
					<tr>
						<td align="center">
							<s:property value="#st.index+1" />
						</td>
						<td align="center">
							<s:property value="basevalue" />
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
						<td align="center">
							-
						</td>
						<s:iterator value="myExamPapers">
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
							<s:property value="basevalue" />
						</td>
						<td align="center">
							<s:property value="userCount" />
						</td>
						<td align="center">
							<s:property value="qkuserCount" />
						</td>
						<td align="center">
							<s:property value="userCount_jg" />
						</td>
						<td align="center">
							<s:property value="userCount-userCount_-userCount_jg" />
						</td>
						<td align="center">
							<s:property value="ratio" />

						</td>
						<td align="center">
							<s:if test="userCount-userCount_==0">0</s:if>
							<s:else>
								<s:property value="avg" />
							</s:else>
						</td>
						<s:iterator value="myexampapers">
							<td align="center" style="color: red;">
								<s:property value="avgscore" />
							</td>
						</s:iterator>
					</tr>
				</s:else>
			</s:iterator>
			<tr>
				<td>
					说明：“-”表示该部门无人参加考试
				</td>
			</tr>
		</table>
