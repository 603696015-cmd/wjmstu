<%@ page language="java" pageEncoding="gbk"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
	response.setHeader("Content-disposition",
			"attachment; filename=CourseStatistics.xls");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML>
	<HEAD>
		<TITLE>课程统计</TITLE>
		<base href="<%=basePath%>">
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
		<table width="100%" border="1">
			<tr>
				<td height="30"	align="center">
					课程名称
				</td>
				<td width="120" height="30" align="center">
					课程类别
				</td>
				<td width="120" height="30" align="center">
					创建时间
				</td>
				<td height="30" align="center">
					学员人数
				</tD>
			</tr>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<s:iterator value="courses">
					<tr>
						<td height="30" align="center">
							<s:property value="name" />
						</td>
						<td width="120" height="30" align="center">
							<s:property value="ctype.name" />
						</td>
						<td width="120" height="30" align="center">
							<s:date name="createtime" format="yyyy-MM-dd" />
						</td>
						<td height="30" align="center">
							<s:property value="userCount" />
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</BODY>
</HTML>
