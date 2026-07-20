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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">试卷评阅 </span>
			</li>-->
		</ul>
		<div style="margin-top: 40px; text-align: center;">
			<script>
													function page(i){
														document.getElementById("pageNow").value=i;
														epreadform.action="exampaperreadlist.action";
														epreadform.submit();
													}
													function reQuiz( ){
														epreadform.action="requiz.action";
														epreadform.submit();
													}
												</script>
		<!-- 内容 -->
		<form action="exampaperreadlist.action" name="epreadform"
			method="post">
			<s:hidden id="pageNow" name="pN"></s:hidden>
			<s:hidden id="pageSize" name="pS"></s:hidden>
			<s:hidden name="examRoom.id"></s:hidden>
				<table width="90%" align="center" cellspacing="2">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">&nbsp;
							
						</td>
						<td height="30" align="center" >
							学员
						</td>
						<td height="30" align="center" >
							所在考场
						</td>
						<td height="30" align="center" >
							试卷状态
						</td>
						<td height="30" align="center" >
							得分
						</td>
						<td height="30" align="center" >&nbsp;
							
						</td>
					</tr>
					<s:iterator value="myExampapers">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<input type="checkbox" name="myExampapers.id" value="<s:property value="id"/>">
							</td>
							<td height="30" align="center" >
								<s:property value="tester.realname" />
							</td>
							<td height="30" align="center" >
								<s:property value="examRoom.title" />
							</td>
							<td height="30" align="center" >
								<s:property value="statusName" />
							</td>
							<td height="30" align="center" >
								<s:property value="myScore" />
							</td>
							<td height="30" align="center" >
								<a target="_blank"
									href="exampaperread.action?myExamPaper.tester.id=<s:property value="tester.id"/>&myExamPaper.examRoom.id=<s:property value="examRoom.id"/>">阅卷</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<br>
				<input type="button" onClick="reQuiz();" value="删除重考"><br>
		<wysLib:page></wysLib:page>
													</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
