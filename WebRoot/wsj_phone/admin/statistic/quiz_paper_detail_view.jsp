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
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="成绩排行榜" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考试概况</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<th width="40" height="30" align="center" >
						排名					</th>
					<th width="100" height="30" align="center" >
						姓名					</th>
					<th width="180" align="center" >用户名</th>
					<th height="30" align="center" >
						试卷名称					</th>
					<th width="50" height="30" align="center" >
						成绩					</th>
					 <th height="30" align="center" >
						是否达标
					</th> <th width="120" height="30" align="center" >
						查看考卷
					</th>
				</tr>
<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="examRoom.meps" status="ermst">
					<tr>
						<td width="40" height="30" align="center" >
						<s:property value="#ermst.index+1" />						</td>
						<td width="100" height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						<s:property value="tester.realname" />						</td>
					  <td width="180" align="center" ><s:property value="tester.username" /></td>
						<td height="30" align="center" >
							<s:property value="examPaper.title" />						</td>
						<td width="50" height="30" align="center" >
					  <s:property value="myScore" />					  </td>
						 <td align="center" >
							<s:if test="ispassed==1">达标</s:if>
							<s:else>不达标</s:else>
						</td>
						 <td width="120" height="30" align="center" >
						 <a
								href="quizpaper_view.action?elUser.id=<s:property value="tester.id"/>&myExamPaper.id=
						 <s:property value="id"/>"
								target=_blank class=textbg4>查 看</a>
							<a
								href="exampaperread.action?myExamPaper.id=<s:property value="id"/>"
								target=_blank class=textbg4>改 分</a>
						 </td>
					</tr>
				</s:iterator></tbody>
		  </table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
