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
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷列表(培训班课程)" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">为考场分配学员 </span>
			</li><!-- 
			<li class="sep">
			</li>
			<s:if test="examRoom.course.id!=-1">
				<li>
					<a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="examroom_listbyc.action?course.id=<s:property value="examRoom.course.id"/>">考试考场管理</a>
				</li>
			</s:if>
			<s:else>
				<s:if test="optype!='valid'">
					<li>
						<a style="cursor: hand"
							onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
							onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
							href="examroomwithoutcourse_list.action">考场列表</a>
					</li>
				</s:if>
			</s:else> -->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 15px;">
				为考场
			<b>
				<s:property value="examRoom.title" />
</b> 添加学员
			</label>
			<table width="600px" align="center" cellpadding="1" cellspacing="1"
				>
				<caption>
					本考场使用的试卷如下
				</caption>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						试卷名称
					</td>
					<td height="30" align="center" >
						已分配人数
					</td>
					<td height="30" align="center" >
					</td>
				</tr>
				<s:iterator value="examRoom.exampapers">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<s:property value="title" />
						</td>
						<td height="30" align="center" >
							<s:property value="usersize" />

						</td>
						<td height="30" align="center" >
							<!-- <a href="examroom_assignwcSearchInit.action?examPaper.id=<s:property value="id"/>&examRoom.id=<s:property value="examRoom.id"/>">分配人员</a> -->
							<a class="textbg4" style="width:80px;color: red;" href="examroom_assignInit.action?examPaper.id=<s:property value="id"/>&examRoom.id=<s:property value="examRoom.id"/>&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">分配人员</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			<input class="textbg" style="border: none;" type="button"
				value="班级课程列表"
				onclick="document.location='elclass_course.action?elclass.id=${examRoom.classid }'" />
			<input class="textbg" style="border: none;" type="button"
				value="返回绑定列表"
				onclick="document.location='examroom_choose_listbycInit.action?course.id=${examRoom.course.id }&classId=${examRoom.classid }&Return=elclass_alterInit'" />	
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
