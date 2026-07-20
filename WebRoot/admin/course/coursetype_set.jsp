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
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
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
		
		</SCRIPT>
	</HEAD>
	<BODY onLoad="myload();">
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="类别顺序设置" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursetype_list.action">课程类别管理</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="coursetype_addInit.action">添加课程新类别</a>
			</li>
			<li class="sep">
			</li>
			-->
			<li>
				<span style="font-weight: bold;">首页类别设置</span>
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
			<form action="coursetype_set.action" method="post">
				<table width="500px" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<td align="center" >
							位置
						</td>
						<td align="center" >
							类别
						</td>
					</tr>
					<tr>
						<td height="30">
							位置 1						</td>
						<td>
							<wysLib:ctypeTree did="1" itype="ra" ivalue="${ctypes[0].id}" iname="ctypes[0].id"></wysLib:ctypeTree>
						</td>
					</tr>
					<tr>
						<td height="30">
							位置 2						</td>
						<td>
							<wysLib:ctypeTree did="2" itype="ra"  ivalue="${ctypes[1].id}" iname="ctypes[1].id"></wysLib:ctypeTree>
						</td>
					</tr>
					<tr>
						<td height="30">
							位置 3						</td>
						<td>
							<wysLib:ctypeTree did="3" itype="ra"  ivalue="${ctypes[2].id}" iname="ctypes[2].id"></wysLib:ctypeTree>
						</td>
					</tr>
					<tr>
						<td height="30">
							位置 4						</td>
						<td>
							<wysLib:ctypeTree did="4" itype="ra"  ivalue="${ctypes[3].id}" iname="ctypes[3].id"></wysLib:ctypeTree>
						</td>
					</tr>
					<tr>
						<td height="30">
							位置 5						</td>
						<td>
							<wysLib:ctypeTree did="5" itype="ra"  ivalue="${ctypes[4].id}" iname="ctypes[4].id"></wysLib:ctypeTree>
						</td>
					</tr>
					<tr>
						<td height="30">
							位置 6						</td>
						<td>
							<wysLib:ctypeTree did="6" itype="ra"  ivalue="${ctypes[5].id}" iname="ctypes[5].id"></wysLib:ctypeTree>
						</td>
					</tr>
					<tr>
						<td height="30">
							位置 7						</td>
						<td>
							<wysLib:ctypeTree did="7" itype="ra"  ivalue="${ctypes[6].id}" iname="ctypes[6].id"></wysLib:ctypeTree>
						</td>
					</tr>
					<tr>
						<td height="30">
							位置 8						</td>
						<td>
							<wysLib:ctypeTree did="8"  itype="ra"  ivalue="${ctypes[7].id}" iname="ctypes[7].id"></wysLib:ctypeTree>
						</td>
					</tr>
			  </table>
				<br>
				<br>
				<br>
				<input type="submit" value="保存设置">
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
