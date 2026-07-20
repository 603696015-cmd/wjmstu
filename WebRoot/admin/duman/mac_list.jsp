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
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function addMac(){
				window.location="mac_addInit.action";
			}
		</script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="Mac地址" /></div>
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
			<form action="mac_list.action" method="post" name="maclist">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				
			</form>
			<table width="60%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="81%" height="30" align="center" >
						Mac地址					</th>
					
					<th width="19%" height="30" align="center" >					</th>
				</tr>
				<s:iterator value="macs">
					<tr>
						<td height="30" align="center">
							<s:property value="macaddres" />
						</td>
						<td height="30" align="center" >
							<a href="mac_del.action?id=<s:property value='id'/>">删除</a>
						</td>
					</tr>
				</s:iterator>
		  </table>
		  <br/>
		 	<script type="text/javascript">
		 		function page(i){
		 			document.getElementById("pageNow").value=i;
		 			maclist.submit();
		 		}
		 	</script>
			<wysLib:page></wysLib:page> 
			<input type="button" onclick = "addMac()" value="添加">
		</div>
			
		<!-- 内容 -->
	</BODY>
</HTML>