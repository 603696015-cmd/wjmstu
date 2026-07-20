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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">制定投票</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px">
		<s:form action="assist_poll_add" method="post" theme="simple">
		<s:property value="elmessage"/>
			<table cellspacing=1 cellpadding=2 width="70%" align=center
				bgcolor=#ebebeb>
				<tbody>
					
					<tr>
						<td align=center bgcolor=#ffffff>
							投票名称
						</td>
						<td align=center bgcolor=#ffffff>
							<input name="poll.title" size="50"  />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							投票描述
						</td>
						<td align=center bgcolor=#ffffff>
							<textarea name="poll.description" cols="40" rows="5"></textarea>
						</td>
					</tr>
					<tr>
					<td align=center bgcolor=#ffffff>
							选择试卷
						</td>
						<td align=center bgcolor=#ffffff>
						<span style="width:200px;" id="eptitle"> </span>
						<input type="hidden" id='epid' name="poll.question.id" value=""/><a href="javascript:searchQuestion();">选择试题</a>
						</td>
					</tr>
					<tr>
					<td align=center bgcolor=#ffffff>
							调查范围
						</td>
						<td align=center bgcolor=#ffffff>
						从<input size="20" name="poll.begintime" onclick='setday(this)' />到<input size="20" name="poll.endtime" onclick='setday(this)' />
						</td>
					</tr>
					<tr>
					<td align=center bgcolor=#ffffff>
						被调查者是否可以查看结果
						</td>
						<td align=center bgcolor=#ffffff>
						<input  name="poll.stureadresult" type="radio" value="true" checked="checked"/>可以&nbsp;&nbsp; &nbsp;&nbsp; <input name="poll.stureadresult" type="radio" value="false"/> 不可以
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
						</td>
						<td align=center bgcolor=#ffffff><br>
							<input type="submit"  value="提交">&nbsp;&nbsp;&nbsp;
							<br>
						</td>
					</tr>
				</tbody>
			</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
