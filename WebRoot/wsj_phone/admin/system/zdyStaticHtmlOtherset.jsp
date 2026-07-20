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
		<TITLE>自定义模块静态页配置</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<SCRIPT type="text/javascript">
				function setParent(dd,id){
					document.getElementById("parentid"+dd).value=id;
				}
		</SCRIPT>
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
					alert( "${elmessage}!");
				}
			
			}
		
			function do_submit(){
				zdyStaticHtmlOtherset.submit();
			}
		</SCRIPT>
	</HEAD>
	<body onLoad="myload();">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="JTM接口地址设置" /></div>
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
			<s:form action="zdyStaticHtmlOtherset.action" method="post" name="zdyStaticHtmlOtherset" theme="simple">
				<table width="60%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td height="30" align="center"> 
							自定义模块静态页是否开启：
						</td>
						
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="zdyStaticHtmlSysConf.open_all"></s:radio>
						</td>
					</tr>
					<!-- <tr>
						<td height="30" align="center"> 
							添加页面静态页是否开启：
						</td>
						
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="zdyStaticHtmlSysConf.open_addContactTagsInit"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" align="center"> 
							修改页面静态页是否开启：
						</td>
						
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="zdyStaticHtmlSysConf.open_updateContactTagsInit"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" align="center"> 
							查看页面静态页是否开启：
						</td>
						
						<td height="30" style="padding-left:8px;" align="left">
							<s:radio list="#{'true':'需要','false':'不需要'}"
								name="zdyStaticHtmlSysConf.open_viewContactTags"></s:radio>
						</td>
					</tr> -->
				</table>
				<br>
				<input type="button" onClick="do_submit();" value="保存设置"  class="textbg6">
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
