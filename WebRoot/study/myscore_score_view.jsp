<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--学员显示</TITLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">我的积分</span>
			</li>-->
		</ul>

		<div style="margin-top: 0px;">
			<table width="60%" cellpadding="1" cellspacing="1" >
			<tr>
						<td align="center" >
							<strong>得分原因 </strong>
						</td>
						<td >
							<strong>单次得分 </strong>	
						</td>
						<td align="center" >
							<strong>得分次数 </strong>
						</td>
							<td align="center" >
							<strong>	总得分 </strong>
						</td>
				</tr>
			<tr>
						<td align="center" >
							<strong>一篇帖子被加为精华，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_forum_jh"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_forum_jh_m/elUser.scoreset.score_forum_jh"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_forum_jh_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>一篇知识文章被设为推荐，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_knowledge_tj"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_knowledge_tj_m/elUser.scoreset.score_knowledge_tj"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_knowledge_tj_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每申请学习一门课程，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_course_apply"/>
						</td>
							<td >
							<s:property value="elUser.scoreset.score_course_apply_m/elUser.scoreset.score_course_apply"/>
						</td>
							<td >
							<s:property value="elUser.scoreset.score_course_apply_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每做一次练习，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_prac_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_prac_do_m/elUser.scoreset.score_prac_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_prac_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每做一次模拟考试，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_simp_do"/>
						</td>
							<td >
							<s:property value="elUser.scoreset.score_simp_do_m/elUser.scoreset.score_simp_do"/>
						</td>
							<td >
							<s:property value="elUser.scoreset.score_simp_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>每发一条站内短信，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_mess_send"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_mess_send_m/elUser.scoreset.score_mess_send"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_mess_send_m"/>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>每做一张调查问卷，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_survey_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_survey_do_m/elUser.scoreset.score_survey_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_survey_do_m"/>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>每参加一次投票，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_poll_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_poll_do_m/elUser.scoreset.score_poll_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_poll_do_m"/>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>每做一张客观测评试卷，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_ktroom_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_ktroom_do_m/elUser.scoreset.score_ktroom_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_ktroom_do_m"/>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>每参加一次民主评议，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_ztroom_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_ztroom_do_m/elUser.scoreset.score_ztroom_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_ztroom_do_m"/>
						</td>
					</tr>
						<tr>
						<td align="center" >
							<strong>记一次课程小结，奖励</strong>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_note_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_note_do_m/elUser.scoreset.score_note_do"/>
						</td>
						<td >
							<s:property value="elUser.scoreset.score_note_do_m"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							<strong>总分</strong>
						</td>
						<td bgcolor="#FFFFFF" colspan="2">
						</td>
						<td >
							<s:property value="elUser.scoreset.score_course_apply_m+elUser.scoreset.score_forum_jh_m+elUser.scoreset.score_knowledge_tj_m+elUser.scoreset.score_ktroom_do_m+elUser.scoreset.score_mess_send_m+elUser.scoreset.score_note_do_m+elUser.scoreset.score_poll_do_m+elUser.scoreset.score_prac_do_m+elUser.scoreset.score_simp_do_m+elUser.scoreset.score_survey_do_m+elUser.scoreset.score_ztroom_do_m"/>
						</td>
					</tr>
			</table>
		</div>
	</BODY>
</HTML>
