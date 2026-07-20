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
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="分配未订购学员列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程学员列表</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_stat_view.action?course.id=<s:property value="course.id"/>">基本信息</a>
			</li>-->
		</ul>
		<!-- 内容 --> 
		<table width="100%">
				<tr> 
					<td valign="top"> 
			<s:if test="myCourses.size==0">没有符合条件的学生</s:if>
			<s:else>
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th width="130" height="30" align="center" >
						姓名</th>
					<th width="100" align="center" >账号</th>
					<th width="150" height="30" align="center" >
						所属培训班					</th>
					<th width="120" height="30" align="center" >
						考场信息					</th>
					<th width="150" height="30" align="center" >
						部门					</th> 
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myCourses">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="user.realname" /></td>
						<td align="center" ><s:property value="user.username" /></td>
						<s:if test="className!=null">
							<td width="150" height="30" align="center" >
							<s:property value="className" />		</td>
						</s:if>
						<s:else>
							<td width="150" height="30" align="center" >
							单独分配而来		</td>
						</s:else>
						<td height="30" align="center" >
							<s:property value="myExamPaper.examRoom.title" />		</td>
						<td width="150" height="30" align="center" >
							<s:property value="user.department.name" />		</td> 
				</s:iterator></tbody> 
		  </table>
		  <form action="Shopping_user_detail_list.action" method="post" name="ddd">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="course.id"></s:hidden>
						<s:hidden name="course.classid"></s:hidden>
												<s:hidden name="elUser.id"></s:hidden>
			  </form>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="200">
						&nbsp;
					</td>
					<td width="140">

					</td>
					<td width="400">
						<a href="mark_course_order.action?elUser.id=<s:property value="elUser.id" />&course.id=<s:property value="course.id" />" class="textbg">确认订购</a>

					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
					<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
						function toexcel(){
							ddd.action = "course_user_list.action?exprot=true";
							ddd.submit();
						}
		 		   </script>
		  <wysLib:page></wysLib:page>
		  </s:else></td></tr></table> 
		<!-- 内容 -->
	
	</body>
</HTML>
				