<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="userdemoLib" uri="/WEB-INF/userdemoLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加日志</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<script type="text/javascript">
		
  		function changeValue(obj){
  			if(obj!=undefined){
  				if(obj.checked){
  					obj.value = 1;
  				}else{
  					obj.value = 0;
  				}
  			}
  		}
  		//添加自定义列
  		function addcolumn(){
  			window.location.href = "addUserDemoColumnInit.action";
  		}
  		function load(){
  			if("${elmessage}"!='null'&&"${elmessage}"!=''){
			 alert("${elmessage}!");
			 }
		}
		//添加CSS
		function addCSS(column_name){
			
		}
		//添加JS验证
		function addJS(column_name){
			var radios = document.getElementsByName("radio_");
			var flag = false ;
			if(radios!=undefined && radios.length>0){
				for(var i=0;i<radios.length;i++){
					if(radios[i].checked){
						flag = true;
						document.getElementById("elUserJs.show_type").value = radios[i].value;
					}
				}
			}
			jj.action = "addColumnJsInit.action";
			document.getElementById("elUserJs.column_name").value = column_name;
			jj.submit();
		}
		//设置字段范围
		function setPageType(column_name){
			var radios = document.getElementsByName("radio_");
			var flag = false ;
			if(radios!=undefined && radios.length>0){
				for(var i=0;i<radios.length;i++){
					if(radios[i].checked){
						flag = true;
						document.getElementById("elUserJs.show_type").value = radios[i].value;
					}
				}
			}
			jj.action = "setPageTypeInit.action";
			document.getElementById("elUserJs.column_name").value = column_name;
			jj.submit();
		}
		
		
  </script>
	<body onload="load();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle" class="tablequiz">
					<ul class="nav">
						<li>
							<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
								<wysLib:Navigation ivalue="用户表字段管理" />
							</div>
						</li>

					</ul>
				</td>
				<td width="120" valign="middle" class="tablequiz">
					<A id=quit href="javascript:window.parent.full_screen(false);"
						class="textbg6" style="display: none">退出全屏</A>
				</td>
			</tr>
		</table>
		<!-- 内容 -->
		<form action="updateUserDemoColumns.action" method="post" name="column_info">
		<div style="margin-top: 0px; text-align: center;">
			<userdemoLib:listcolumns />
			<table cellpadding='1' cellspacing='1' width='100%'>
				<caption>验证规则所在的页面</caption>
				<tr><th>添加页面</th><th>修改页面</th><th>注册页面</th><th>个人修改页面</th></tr>
				<tr>
					<td align=center><input type="radio" name="radio_" value= 1 checked/></td>
					<td align=center><input type="radio" name="radio_" value= 2 /></td>
					<td align=center><input type="radio" name="radio_" value= 4 /></td>
					<td align=center><input type="radio" name="radio_" value= 5 /></td>
				</tr>
				<tr><td align=center colspan=4>注意：此单选只在JS校验设置的时候起作用</td></tr>
			</table>
		</div>
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认修改" class=textbg6 />
			<input onclick="addcolumn();" type="button" value="添加字段" class=textbg6 />
		</div>
		</form>
		<form action="addColumnJsInit.action" name="jj" method="post">
			<s:hidden name="elUserJs.column_name" id="elUserJs.column_name"/>
			<s:hidden name="elUserJs.show_type" id="elUserJs.show_type"/>
		</form>
	</body>
</html>