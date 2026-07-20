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
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/assist.js"></script>
		<script type="text/javascript">
			function init(){
				 var subject="<s:property value="poll.question.subject" />";
				 var subArray=subject.split("-=SpEl=-");
				 for(var i=0;i<subArray.length-1;i++){
				 	//alert(subArray[i]);
				 	var d=$("<div>");
				 	var temp= String.fromCharCode(65+i);
				 	d.html(temp+"："+subArray[i]);
				 	$("#qtitle").append(d);
				 }
			}
		</script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<body onload="init();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加投票信息" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<form id="form_poll_alter" name="form_poll_alter" method="post"
				action="alterPoll.action" onSubmit="return _onsubmit();">
				<s:hidden name="poll.id" />
				<span style="color: #ff0000;"></span>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td width="160" height="30" align="center" >
							投票名称：
						</td>
						<td style="padding-left:5px;">
							<s:property value="poll.title" />
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							投票说明：
						</td>
						<td align="left" style="padding-left:5px;">
							<s:property value="poll.remack" />
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							投票时间段：
						</td>
						<td style="padding-left:5px;">
							<label>
								开始时间&nbsp;&nbsp;&nbsp;<s:date name="poll.begintime"/>
							</label>
							<br />
							<label>
								结束时间&nbsp;&nbsp;&nbsp;<s:date name="poll.endtime"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							查看结果：
						</td>
						<td bgcolor="#FFFFFF" style="padding-left:5px;">
							<s:if test="poll.stuViewResult==1">
								允许
							</s:if>
							<s:if test="poll.stuViewResult==0">
								不允许
							</s:if>
						</td>
					</tr>
					<tr>
						<td width="160" height="100" align="center" >
							试题信息
						</td>
						<td bgcolor="#FFFFFF" style="padding-left:5px;">
							<div id="qtitle" style="vertical-align:top;height:100px;">
								<s:property value="poll.question.title"/><br />
								
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div style="margin-top:20px;">
				<a href="pollList.action" class="textbg4">返回</a>
			</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
