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
		<TITLE>模板管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<span style="font-weight: bold;">模板管理</span>
			</li>
			<li class="sep">
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div style="margin-top: 40px; text-align: center;">
				页面生成成功
				<br>
				<a href="column_template.action"><span id="time" style="color:red;">3</span>秒钟自动返回模板列表</a> 
			</div>
		</div>
		<!-- 内容 -->
		<script> 
			setInterval("redirectAndTimeCount()",1000);
			var timeCount=3;
			function redirectAndTimeCount(){
				var obj=document.getElementById("time");
				obj.innerHTML=--timeCount;
				if(timeCount==0){
					window.location="column_template.action";
				}
			}
		</script>
	</BODY>
</HTML>
