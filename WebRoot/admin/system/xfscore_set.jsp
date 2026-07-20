<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>会员中心-会员注册</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			
			}
		
		</SCRIPT>
	</HEAD>
	<BODY onLoad="myload();" style="text-align: center;">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="设置数值" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">学分设置</span>

			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="xfscore_set" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="60%" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<tr>
						<td align="center" >
							<strong>学满一门课程规定的时长，是（否）获得课程设定的学分数</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
								<s:radio name="scoreset.course_studied" list="#{'true':'是','false':'否'}"/>
						</td>
					</tr>
					<tr>
						<td align="center" ><!--
							<strong>参加一门课程规定的结业考试并且成绩达到及格线，是（否）获得课程设定的学分数</strong>-->
						<strong>参加一门课程规定的结业考试并且成绩达到及格线，是（否）获得学分奖励
奖励标准：60-75%分，按1：1奖励学分，76-90%分，按1：1.5奖励学分；91-100%分，按1：2奖励学分。</strong></td>
						<td bgcolor="#FFFFFF" width="200">
							<s:radio name="scoreset.course_quizpassed" list="#{'true':'是','false':'否'}"/>
						</td>
					</tr>
					<tr>
						<td width="120" align="center" >

						</td>
						<td >
							<label>
								<input type="submit" value="保存设置">
							</label>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
</HTML>
