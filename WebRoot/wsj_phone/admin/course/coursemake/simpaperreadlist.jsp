<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="试卷列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">试卷评阅 </span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<form action="simpaperreadlist.action" name="epreadform"
			method="post">
		<div style="margin-top: 40px; text-align: center;">
		<s:set name="cid" value="course.id"></s:set>
			<s:hidden id="pageNow" name="pN"></s:hidden>
			<s:hidden id="pageSize" name="pS"></s:hidden>
			<s:hidden name="course.id"></s:hidden>
			<table width="90%" align="center" cellspacing="2">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">&nbsp;
						
					</td>
					<td height="30" align="center" >
						试卷
					</td>
					<td height="30" align="center" >
						学员
					</td>
					<td height="30" align="center" >
						考试时间
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
							<s:property value="examPaper.title" />
						</td>
						<td height="30" align="center" >
							<s:property value="tester.realname" />
						</td>
						<td height="30" align="center" >
							<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="center" >
							<s:property value="statusName" />
						</td>
						<td height="30" align="center" >
							<s:property value="myScore" />
						</td>
						<td height="30" align="center" >
							<a target="_blank"
								href="simpaperreadInit.action?myExamPaper.tester.id=<s:property value="tester.id"/>&myExamPaper.examPaper.id=<s:property value="examPaper.id"/>&course.id=<s:property value="#cid"/>">阅卷</a>
						</td>
					</tr>
				</s:iterator>
			</table>
		
			<script>
				function page(i){
					document.getElementById("pageNow").value=i;
					epreadform.action="simpaperreadlist.action";
					epreadform.submit();
				}
				function reQuiz( ){
					epreadform.action="reSimquiz.action";
					epreadform.submit();
				}
			</script>
			<wysLib:page></wysLib:page>
			<br>
			<input type="button" onClick="reQuiz();" value="删除重考"><br>
		</div>
			</form>
		<!-- 内容 -->
	
	</body>
</HTML>
