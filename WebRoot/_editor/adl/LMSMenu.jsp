<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT">
<meta http-equiv="Pragma" content="no-cache">
<title>SCORM 1.2.2 LMS Menu Page</title>
<script language=javascript>
	/**************************************************************************
	 *  function to confirm that user really wants to clear the database
	 *
	 ***************************************************************************/
	function confirmClearDatabase() {
		if (confirm("你确定要删除所有课程的信息？")) {
			window.parent.frames[3].document.location.href = "clearDatabase.jsp";
		} else {
		}
	}

	/**************************************************************************/
</script>
</HEAD>

<body>
<script language=javascript>
	// Hide or display the relevant controls
	if (document.all != null) {
		//window.parent.frames[0].document.forms[0].logout.style.visibility = "visible";
		window.parent.frames[1].document.location.href = "code.jsp";
		window.top.frames[0].document.forms[0].next.style.visibility = "hidden";
		window.top.frames[0].document.forms[0].previous.style.visibility = "hidden";
		window.top.frames[0].document.forms[0].quit.style.visibility = "hidden";
	}
</script>

<%
	//获取用户信息并删除课程ID
	String userid = String.valueOf(session.getAttribute("userId"));
	String admin = (String) session.getAttribute("RTEADMIN");
	//session.removeAttribute("COURSEID");			//modify by luocw
%>
<p><font face="tahoma" size="3"><b> 请选择下列选项之一: </b></font></p>

<br>

<table width="200">
	<tr>
		<td bgcolor="#5E60BD"><font face="tahoma" size="2"
			color="#ffffff"><b> &nbsp;用户选项： </b></font></td>
	</tr>
	<tr>
		<td><a href="courseRegister.jsp"> 注册一个课程 </a></td>
	</tr>
	<tr>
		<td><a href="viewCourses.jsp">查看注册过的课程</a></td>
	</tr>
	<tr>
		<td><a href="changePwd.jsp">修改密码</a></td>
	</tr>
	<tr>
		<td><i><a href="readme.htm" target="_blank">Sample RTE
		1.2.2 帮助</a></i></td>
	</tr>
</table>

<%
	//if ((!(admin == null)) && (admin.equals("true"))) {
%>
<br>

<table width="200">
	<tr>
		<td bgcolor="#5E60BD"><font face="tahoma" size="2"
			color="#ffffff"><b> &nbsp;管理员选项: </b></font></td>
	</tr>
	<tr>
		<td><a href="importCourse.jsp"> 导入课程 </a></td>
	</tr>
	<tr>
		<td><a href="deleteCourse.jsp">删除课程</a></td>
	</tr>
	<tr>
		<td><a href="newUser.jsp">添加用户</a></td>
	</tr>
	<tr>
		<td><a href="javascript:confirmClearDatabase()">清空数据库</a></td>
	</tr>
	<tr>
		<td><a href="deleteUser.jsp">删除用户</a></td>
	</tr>

</table>

<%
	//}
%>

</body>
</html>
