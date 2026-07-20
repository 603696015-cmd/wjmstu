<%@ page language="java" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
	response.setHeader("Content-disposition",
			"attachment; filename=TrainingClass.xls");
%>
<HTML>
	<body>
		<table align="center" border="1">
			<tr>
				<th align="center">
					培训班名称
				</th>
				<th align="center">
					创建时间
				</th>
				<th align="center">
					学员人数
				</th>
				<th align="center">
					通过人数
				</th>
				<th align="center">
					通过率
				</th>
			</tr>
			<tbody>
				<s:iterator value="classes">
					<tr>
						<td align="center">
							<s:property value="name" />
						</td>
						<td align="center">
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center">
							<s:property value="userCount" />
						</td>
						<td align="center">
							<s:property value="userPassedCount" />
						</td>
						<td align="center">
							<s:property value="passper" />
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<!-- 内容 -->
	
	</body>
</HTML>
