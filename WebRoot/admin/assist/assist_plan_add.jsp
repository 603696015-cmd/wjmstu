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
							<span style="font-weight: bold;">制定培训计划</span>
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
		<s:form action="assist_plan_add" method="post" theme="simple">
		<s:property value="elmessage"/>
			<table cellspacing=1 cellpadding=2 width="70%" align=center
				bgcolor=#ebebeb>
				<tbody>
					<tr>
						<th colspan="2" align=center bgcolor=#ffffff>
							制定培训计划第一步 ：填写培训概况
						</th>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							计划名称
						</td>
						<td align=center bgcolor=#ffffff>
							<input name="plan.name" size="40"  />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							培训内容
						</td>
						<td align=center bgcolor=#ffffff>
							<textarea name="plan.content" cols="30" rows="5"></textarea>
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							参与人
						</td>
						<td align=center bgcolor=#ffffff>
							<input size="40" name="plan.participator"   />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							联系人
						</td>
						<td align=center bgcolor=#ffffff>
							<input size="40" name="plan.contact" />
						</td>
					</tr>
				<tr>
						<td align=center bgcolor=#ffffff>
							计划完成时间
						</td>
						<td align=center bgcolor=#ffffff>
							<input size="40" name="plan.planfinishdate" onclick='setday(this)' />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
						</td>
						<td align=center bgcolor=#ffffff><br>
							<input type="submit"  value="下一步">&nbsp;&nbsp;&nbsp;
							<input type="button" onClick="document.history.back(-1);" value="返回">
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
