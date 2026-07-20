<%@ page language="java" pageEncoding="UTF-8"%>
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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">选课情况</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:if test="myCourses.size==0">该学员没有课程</s:if>
			<s:else>
				<table width="90%" align="center" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<caption><s:property value="elUser.realname"/>的课程</caption>
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							课程名称
						</td>
						<td height="30" align="center" >
							任课老师
						</td>
						<td height="30" align="center" >
							推荐学分
						</td>
						<td height="30" align="center" >
							总时间/已学时间 
						</td>
						<td height="30" align="center" >
							学习进度
						</td>
						<td height="30" align="center" >
							考试时间
						</td>
						<td height="30" align="center" >
						</td>
					</tr>
					<s:iterator value="myCourses">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="course.name" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.creater.realname" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.credit" />
							</td>
							<td height="30" align="center" >
								<s:property value="course.during" />分钟
								 /
								<s:property value="passtime" />分钟
							</td>
							<td height="30" align="center" >
								<s:property value="processStr" />
								%
							</td>
							<td height="30" align="center" >
							<s:if test="examRoom.begintime==null"> 未被安排考试</s:if>
							<s:else>
							<s:date name="examRoom.begintime" format="yyyy-MM-dd HH:mm:ss"/>
							</s:else>
							</td>
							<td height="30" align="center" >
								<a style="cursor: hand" onClick="notequery(<s:property value="course.id"/>,<s:property value="elUser.id"/>,'<s:property value="elUser.realname"/>')">查看笔记</a>
								</td>
						</tr>
					</s:iterator>
				</table>
				<form action="course_notequeryView.action" method="post" name="course_notequeryView">
				    <s:hidden name="course.id" id="course.id"/>
					<s:hidden name="elUser.id" id="elUser.id"/>
					<s:hidden name="elUser.realname" id="elUser.realname"/>
				</form>
				<script type="text/javascript">
				    function notequery(coruseid,elUserid,realname){
				      document.getElementById("course.id").value=coruseid;
				      document.getElementById("elUser.id").value=elUserid;
				      document.getElementById("elUser.realname").value=realname;
				      document.forms.course_notequeryView.submit();
				    }
			   </script>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
