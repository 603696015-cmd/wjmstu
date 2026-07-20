<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
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
			"attachment; filename=CourseUserList.xls");
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
	</HEAD>
	<body>
		<!-- 内容 -->
			<table width="100%" border="1">
				<tr>
					<th width="180" height="30" align="center">
						姓名
					</th>
					<th width="120" align="center">
						账号
					</th>
					<th width="200" height="30" align="center">
						部门
					</th>
					<th width="130" height="30" align="center">
						总时间/已学时间
					</th>
					<th width="70" height="30" align="center">
						学习进度
					</th>
					<!--<th height="30" align="center" >
						已获学分
					</th>-->
					<th width="50" height="30" align="center">
						成绩
					</th>
					<th width="80" height="30" align="center">
						是否及格
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="myCourses">
						<tr>
							<td height="30" align="center" bgcolor="#FFFFFF"
								style="color: #CC0099;">
								<s:property value="user.realname" />
							</td>
							<td align="center">
								<s:property value="user.username" />
							</td>
							<td width="150" height="30" align="center">
								<s:property value="user.department.name" />
							</td>
							<td width="130" height="30" align="center">
								<s:property value="course.during" />
								分钟 /
								<s:property value="passtime" />
								分钟
							</td>
							<td width="70" height="30" align="center">
								<s:property value="processStr" />
								<!--<td height="30" align="center" >
							<s:property value="myCredit" />-->
							</td>
							<td align="center">
								<s:property value="myExamPaper.myScore" />
							</td>
							<td>
								<s:if test="myExamPaper.ispassed==0">不及格</s:if>
								<s:else>及格</s:else>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		<!-- 内容 -->
	
	</body>
</HTML>
