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
		<TITLE>JTM设置</TITLE>
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
				JTMOtherset.submit();
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
			<s:form action="JTMOtherset.action" method="post" name="JTMOtherset" theme="simple">
				<table width="60%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td height="30" align="center"> 
							是否启用JTM接口：
						</td>
						
						<td height="30" align="center">
							<s:radio list="#{'true':'开启','false':'不开启'}"
								name="jtmSysConf.open_jtm"></s:radio>
						</td>
					</tr>
					<tr>
						<td height="30" align="center"> 
							测评接口地址：
						</td>
						
						<td height="30" align="center">
							<input style="width:600px;" type="text" name="jtmSysConf.my_EvaluationInit_URL" value="<s:property value='jtmSysConf.my_EvaluationInit_URL' />"  />
						</td>
					</tr>
					<tr>
						<td height="30" align="center"> 
							人岗接口地址：
						</td>
						
						<td height="30" align="center">
							<input style="width:600px;" type="text" name="jtmSysConf.peoplePostInit_URL" value="<s:property value='jtmSysConf.peoplePostInit_URL' />"  />
						</td>
					</tr>
					<tr>
						<td height="30" align="center"> 
							个人量身评价地址：
						</td>
						
						<td height="30" align="center">
							<input style="width:600px;" type="text" name="jtmSysConf.reportEvalInit_URL" value="<s:property value='jtmSysConf.reportEvalInit_URL' />"  />
						</td>
					</tr>
					<tr>
						<td height="30" align="center"> 
							个人查看报告接口地址：
						</td>
						
						<td height="30" align="center">
							<input style="width:600px;" type="text" name="jtmSysConf.my_ReportInit_URL" value="<s:property value='jtmSysConf.my_ReportInit_URL' />"  />
						</td>
					</tr>
					<tr>
						<td height="30" align="center"> 
							课程同步接口地址：
						</td>
						
						<td height="30" align="center">
							<input style="width:600px;" type="text" name="jtmSysConf.courses_synchronization_URL" value="<s:property value='jtmSysConf.courses_synchronization_URL' />"  />
						</td>
					</tr>
					<tr>
						<td height="30" align="center"> 
							我的测评课程接口地址：
						</td>
						
						<td height="30" align="center">
							<input style="width:600px;" type="text" name="jtmSysConf.myCepingCourses_URL" value="<s:property value='jtmSysConf.myCepingCourses_URL' />"  />
						</td>
					</tr>
				</table>
				<br>
				<input type="button" onClick="do_submit();" value="保存设置"  class="textbg6">
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
