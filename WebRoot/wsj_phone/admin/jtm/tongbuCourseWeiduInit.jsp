<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>课程维度导入</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			function doClick(){
				ff.submit();
			}
		</script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height:30px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
<STYLE type=text/css>
HTML {
 HEIGHT: 100%
}
BODY {
 HEIGHT: 100%
}
BODY {
 FONT-SIZE: 14px; FONT-FAMILY: Tahoma, Verdana, sans-serif
}
DIV.neat-dialog-cont {
 Z-INDEX: 98; BACKGROUND: none transparent scroll repeat 0% 0%; LEFT: 0px; WIDTH: 100%; POSITION: absolute; TOP: 0px; HEIGHT: 100%
}
DIV.neat-dialog-bg {
 Z-INDEX: -1; FILTER: alpha(opacity=70); LEFT: 0px; WIDTH: 100%; POSITION: absolute; TOP: 0px; HEIGHT: 100%; BACKGROUND-COLOR: #eee; opacity: 0.7
}
DIV.neat-dialog {
 BORDER-RIGHT: #555 1px solid; BORDER-TOP: #555 1px solid; Z-INDEX: 99; MARGIN-LEFT: auto; BORDER-LEFT: #555 1px solid; WIDTH: 30%; MARGIN-RIGHT: auto; BORDER-BOTTOM: #555 1px solid; POSITION: relative; TOP: 25%; BACKGROUND-COLOR: #fff
}
DIV.neat-dialog-title {
 PADDING-RIGHT: 0.3em; PADDING-LEFT: 0.3em; FONT-SIZE: 0.8em; PADDING-BOTTOM: 0.1em; MARGIN: 0px; LINE-HEIGHT: 1.2em; PADDING-TOP: 0.1em; BORDER-BOTTOM: #444 1px solid; POSITION: relative
}
IMG.nd-cancel {
 RIGHT: 0.2em; POSITION: absolute; TOP: 0.2em
}
DIV.neat-dialog P {
 PADDING-RIGHT: 0.2em; PADDING-LEFT: 0.2em; PADDING-BOTTOM: 0.2em; PADDING-TOP: 0.2em; TEXT-ALIGN: center
}
</STYLE>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				</div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		
			 <div style="background-color:#F8FCFE; border:1px  solid #D1E4F5;">
	<s:form action="tongbuCourseWeidu.action" method="post" name="ff" enctype= "multipart/form-data" >			 
				<span style="color:red">导入时请注意Excel文件格式！！！</span><br /><br>
				&nbsp;<input type="file" name="sst" id="sst" class="textbg4"/>&nbsp;
				<input type="button" value="导入" onClick="doClick();"  class="textbg4"/>
				<span style="color:red"><s:property value='elmessage'/></span>
		</s:form>
			 </div>
	
	</body>
</HTML>