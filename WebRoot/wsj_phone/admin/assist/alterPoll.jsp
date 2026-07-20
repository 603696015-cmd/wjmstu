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
			function _onsubmit(){
				if($("#ptitle").val()==''){
					alert("投票名称不能为空");
					$("#poll_title").focus();
					return false; 
				}
				if($("#poll_begintime").val()==''){
					alert("开始时间不要为空");
					$("#poll_begintime").focus();
					return false; 
				}
				if($("#poll_endtime").val()==''){
					alert("结束时间不要为空");
					$("#poll_endtime").focus();
					return false; 
				}
				return true;
			}
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
	<body onLoad="init();">
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
						<td >
							<label>
								<input name="poll.title" type="text" id="ptitle"
									value="<s:property value="poll.title" />" size="60" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							投票说明：
						</td>
						<td align="left">
							<s:textarea theme="simple" name="poll.remack" cols="60" rows="7" />
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							投票时间段：
						</td>
						<td >
							<label>
								投票开始时间
								<input class="Wdate" name="poll.begintime" type="text" readonly="readonly"
									onclick="setday(this)" id="poll_begintime" value="<s:date name="poll.begintime"/>" />
							</label>
							<br />
							<label>
								投票结束时间
								<input class="Wdate" name="poll.endtime" type="text" readonly="readonly"
									onclick="setday(this)" id="poll_endtime" value="<s:date name="poll.endtime"/>" />
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							查看结果：
						</td>
						<td bgcolor="#FFFFFF" style="padding: 0px;">
							<input type="radio" name="poll.stuViewResult" value="1" 
								<s:if test="poll.stuViewResult==1">
									checked="checked"
								</s:if>
							 />允许
							<input type="radio" name="poll.stuViewResult" value="0" 
								<s:if test="poll.stuViewResult==0">
									checked="checked"
								</s:if>
							 />不允许
						</td>
					</tr>
					<tr>
						<td width="160" height="100" align="center" >
							试题信息
						</td>
						<td bgcolor="#FFFFFF" style="padding: 0px;">
							<div id="qtitle" style="vertical-align:top;height:100px;">
								<s:property value="poll.question.title"/><br />
								
							</div>
						</td>
					</tr>
					<tr>
						<td width="160" height="50" align="center" >&nbsp;
							
						</td>
						<td >
							<input type="submit" name="button" id="button" value="确认添加" />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
