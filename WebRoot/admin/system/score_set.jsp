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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="数值设置" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">积分设置</span>

			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="score_set" method="post" theme="simple"
				name="department_info" id="department_info">
				<table border="0" width="60%" cellpadding="2" cellspacing="2"
					bgcolor="#EBEBEB">
					<tr>
						<td align="center" >
							<strong>一篇帖子被加为精华，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_forum_jh"></s:textfield>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>一篇知识文章被设为推荐，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_knowledge_tj"></s:textfield>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每申请学习一门课程，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_course_apply"></s:textfield>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每做一次练习，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_prac_do"></s:textfield>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每做一次模拟考试，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_simp_do"></s:textfield>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每发一条站内短信，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_mess_send"></s:textfield>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>每做一张调查问卷，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_survey_do"></s:textfield>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>每参加一次投票，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_poll_do"></s:textfield>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>每做一张客观测评试卷，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_ktroom_do"></s:textfield>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>每参加一次民主评议，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_ztroom_do"></s:textfield>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>记一次课程小结，奖励</strong>
						</td>
						<td bgcolor="#FFFFFF" width="200">
							<s:textfield name="scoreset.score_note_do"></s:textfield>
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
