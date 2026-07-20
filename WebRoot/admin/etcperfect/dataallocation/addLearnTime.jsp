<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>分配人员</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dtree.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
			function do_sure(){
				if(document.getElementById("begintime").value == "" || document.getElementById("endtime").value == ""){
					alert("开始时间和结束实际不能为空,请填写!!!");
					document.getElementById("begintime").focus();
					return;
				}else{
					window.returnValue = document.getElementById("begintime").value + "==" + document.getElementById("endtime").value;
					window.close();
				}
			}
			
			function do_cancel(){
				window.close();
			}
		</script>
		<link rel="StyleSheet" href="js/tree/dtree.css" type="text/css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<BODY >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="添加开始时间和结束时间" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<center>
		<table >
			<tr>
				<td>开始时间：</td>
				<td>
					<input type="text" name="begintime" id="begintime" value="" onclick="setday(this)">
				</td>
			</tr>
			<tr>
				<td>结束时间：</td>
				<td>
					<input type="text" name="endtime" id="endtime" value="" onclick="setday(this)">
				</td>
			</tr>
		</table>
		<input type="button" value="确定" onclick="do_sure();" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="取消" onclick="do_cancel();" />
		</center>
	</BODY>
</HTML>
