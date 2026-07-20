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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" style="height:20px;">
  			<tr>
    			<td>
    				<ul class="nav">
						<li>
							<span style="font-weight: bold;">试卷评阅 </span>
						</li>
					</ul>
				</td>
    			<td width="120">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
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
				<table width="90%" align="center" cellspacing="1" cellpadding="1">
					<caption>
						答卷列表
					</caption>
					<tr>
						<td height="30" align="center" bgcolor="#ECEDEB">
							&nbsp;
						</td>
						<td height="30" align="center" bgcolor="#ECEDEB">
							学员
						</td>
						<td height="30" align="center" bgcolor="#ECEDEB">
							考试时间
						</td>
						<td height="30" align="center" bgcolor="#ECEDEB">
							试卷状态
						</td>
						<td height="30" align="center" bgcolor="#ECEDEB">
							得分
						</td>
						<td height="30" align="center" bgcolor="#ECEDEB">
							&nbsp;
						</td>
					</tr>
					<s:iterator value="myExampapers">
						<tr>
							<td height="30" align="center" bgcolor="#ECEDEB">
								<input type="checkbox" name="myExampapers.id"
									value="<s:property value="id"/>">
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								<s:property value="tester.realname" />
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								<s:property value="statusName" />
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								<s:property value="myScore" />
							</td>
							<td height="30" align="center" bgcolor="#ECEDEB">
								<a target="_blank"
									href="exampaper_read.action?myExamPaper.id=<s:property value="id"/>">阅卷</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<br><!--
				<input type="button" onclick="reQuiz();" value="重考">
				--><br>
				<wysLib:page></wysLib:page>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
