<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@ page contentType="application/msexcel"%>
<%
	//就是靠这一行，让前端浏览器以为接收到一个excel档   
	response.setHeader("Content-disposition",
			"attachment; filename=ctime_detail_list.xls");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<body>
		<table width="90%" align="center" cellpadding="1" cellspacing="1"
			border="1">
			<tr>
				<td align="center">
					姓名
				</td>
				<td align="center">
					账号
				</td>
				<td align="center">
					部门
				</td>
				<td align="center" width="150px;">
					课程名称
				</td>
				<td align="center" width="50">
					课程时长
				</td>
				<td align="center" width="50">
					已学时长
				</td>
				<td align="center" width="50">
					学习进度
				</td>
				<td align="center">
					课程总时长
				</td>
				<td align="center">
					已学总时长
				</td>
			</tr>
			<s:iterator value="elUsers" status="st">
				<tr>
					<td align="center">
						<s:property value="realname" />
					</td>
					<td align="center">
						<s:property value="username" />
						&nbsp;
					</td>
					<td align="center">
						<s:property value="department.name" />
					</td>
					<td colspan="4" align="center" bgcolor="#FFFFFF"
						style="padding: 0px;">
						<table width="100%" align="center" cellpadding="1"
							style="margin: 0px;" cellspacing="1" bgcolor="#EBEBEB">
							<s:iterator value="myCourses">
								<tr>
									<td width="150px;" align="center">
										<s:property value="course.name" />
									</td>
									<td width="50" align="center">
										<s:property value="course.during" />
										分钟
									</td>
									<td width="50" align="center">
										<s:property value="passtime" />
										分钟
									</td>
									<td width="50" align="center">
										<s:property value="processStr" />
										%
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
					<td align="center">
						<s:property value="ct_time" />
					</td>
					<td align="center">
						<s:property value="xx_time" />
					</td>
				</tr>
			</s:iterator>
		</table>
		<!-- 内容 -->
	</BODY>
</HTML>
