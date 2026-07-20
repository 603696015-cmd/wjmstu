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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="考场成绩列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 考试成绩</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%" align="center" cellspacing="1" cellpadding="1" bgcolor="#D1E4F5">
				<caption>考试成绩</caption>
				<tr>
					<th width="200" height="30" align="center" bgcolor="#F8FCFE" >
						考场标题					</th>
					<th width="150" height="30" align="center" bgcolor="#F8FCFE" >
					  创建者					</th>
					<th width="80" height="30" align="center" bgcolor="#F8FCFE" >
					  试卷数					</th>
					<th width="80" height="30" align="center" bgcolor="#F8FCFE" >
					  成绩</th>
					<th width="80" height="30" align="center" bgcolor="#F8FCFE" >
					  是否通过</th>
					<th width="120" height="30" align="center" bgcolor="#F8FCFE" >
						查看详情					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myrooms">
					<tr>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;">
							<s:property value="examroom.title" />
						</td>
						<td height="30" align="center" bgcolor="#F8FCFE" >
				    <s:property value="examroom.creater.realname" />					  </td>
						<td height="30" align="center" bgcolor="#F8FCFE" >
				    <s:property value="epsize" />					  </td>
						<td height="30" align="center" bgcolor="#F8FCFE" >
				    <s:property value="myScore" />					  </td>
						<td height="30" align="center" bgcolor="#F8FCFE" >
					    <s:if test="ispassed==1">是</s:if>
							<s:else>否</s:else>					  </td>
						<td width="120" height="30" align="center" bgcolor="#F8FCFE" >
							<a href="quizpapwithoutC_result_list_detail.action?examRoom.id=<s:property value="examroom.id"/>" class="textbg">查看详情</a>						</td>
					</tr>
				</s:iterator></tbody>
		  </table>
		  <form action="quizpapwithoutC_result_list.action" name="erform" method="post">
					<s:hidden name="pN" id="pageNow"> 
					</s:hidden>
					<s:hidden name="pS">
					</s:hidden>
				</form>
				<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					erform.submit();
				}
				</script>			  
				<wysLib:page_cisco></wysLib:page_cisco>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
