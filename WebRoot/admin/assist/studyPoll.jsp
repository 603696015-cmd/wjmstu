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
				 var qtype="<s:property value="poll.question.qtype" />";
				 for(var i=0;i<subArray.length-1;i++){
				 	//alert(subArray[i]);
				 	var chkbox="";
				 	if(qtype==2){
				 		chkbox="<input type='radio' name='answer' value='"+i+"' />";
				 	}else{
				 		chkbox="<input type='checkbox' name='answer' value='"+i+"' />";
				 	}
				 	var d=$("<div>");
				 	var temp= String.fromCharCode(65+i);
				 	d.html(chkbox+temp+"："+subArray[i]);
				 	d.css("padding","3px");
				 	$("#qtitle").append(d);
				 }
			}
			function _onsubmit(){
				var answers=$("input[name=\"answer\"]:checked");
				if(answers.length<=0){
					alert("请选择投票！");
					return;
				}
				form_poll.submit();
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
			<form id="form_poll" name="form_pollr" method="post"
				action="studyPollDo.action">
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
							创建者：
						</td>
						<td align="left" style="padding-left:5px;">
							<s:property value="poll.creater.realname" />
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
						<td bgcolor="#FFFFFF" style="padding-left:120px;" colspan="2">
							<div id="qtitle" style="vertical-align:top;height:100px;">
								<s:property value="poll.question.title"/><br />
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div style="margin-top:20px;">
				<a href="javascript:_onsubmit();" class="textbg6">确认投票</a>
				<a href="studyPollList.action" class="textbg4">返回</a>
			</div>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
