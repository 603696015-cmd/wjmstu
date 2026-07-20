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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 15px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript">
			function _onsubmit(){
				if($("#pwd").val()==''){
					altert("请输入密码！");
					return false;
				}
			}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<span style="font-weight: bold;">填写考场密码</span>
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
		<form action="quizpaperinit_pwd.action" onSubmit="return _onsubmit();" method="post">
			<table width="400px" align="center" cellspacing="1" cellpadding="1">
				<caption>
					填写考场密码
				</caption>
				<tr>
					<td height="33" align="right">
						考场名称：
					</td>
					<td align="left">
						<s:property value="examRoom.title"/>
					</td>
				</tr>
				<tr>
					<td height="33" align="right">
						考场密码：
					</td>
					<td align="left">
						<input type="text" id="pwd" name="myroom.examroom.pwd"/>
						<input type="hidden" name="myroom.examroom.id" value="<s:property value="myroom.examroom.id"/>"/>
					</td>
				</tr>
				<tr>
					<td height="33" align="left">
					</td>
					<td align="left">
						<input type="submit" value="提交" class="textbg4"/>&nbsp;&nbsp;<input type="button" value="返回" onClick="document.location='study_index.action'" class="textbg4"/>
					</td>
				</tr>
				<tr>
					<td height="33" align="left">
					</td>
					<td align="left">
						<s:property value="elmessage"/>
					</td>
				</tr>
			</table>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
