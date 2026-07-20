<%@ page language="java" pageEncoding="utf-8"%> 
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
     response.setHeader("Content-disposition","attachment; filename=Batch details.xls"); 
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
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<th width="40" height="30" align="center" >
						排名					</th>
					<th width="80" height="30" align="center" >
						姓名					</th>
					<th width="160" height="30" align="center" >
						用户名			</th>
					<th width="40" height="30" align="center" >
						成绩					</th> 
					<th width="60" height="30" align="center" >
						是否及格					</th>
					<s:iterator value="examRoom.myrooms[0].myExamPapers">
						<td height="30" align="center" style="color:red;" >
							<s:property value="examPaper.title" />状态/成绩
						</td>  
					</s:iterator>
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="examRoom.myrooms" status="ermst">
					<tr>
						<td width="40" height="30" align="center" >
							<s:property value="#ermst.index+1" />
					  </td>
						<td width="80" height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="tester.realname" />
					  </td>
						<td width="160" height="30" align="center" >
							<s:property value="tester.username" />
					  </td>
						<td width="40" height="30" align="center" >
							<s:property value="myScore" />
					  </td> 
						<td width="60" align="center" >
							<s:if test="myScore>=60">及格</s:if>
							<s:else>不及格</s:else>
					  </td>
						<s:set name="userid" value="tester.id"></s:set>
						<s:iterator value="myExamPapers">
							<td height="30" align="center" style="color:red;" >
							<s:if test="id==0">未分配</s:if><s:else>
							<s:property value="statusName" />/<s:property value="myScore" /></s:else>
							</td>
						</s:iterator>
					</tr>
				</s:iterator></tbody>
		  </table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
