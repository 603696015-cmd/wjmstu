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
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT type="text/javascript">
			function check_xx(){
				var choose=document.getElementById("qtype");
				var stf=document.getElementById("stf").value;
				stf =stf.substring(stf.lastIndexOf('\\')+1);
				if(stf.indexOf('.xls')<0){
					alert("文档必须“.xls”格式的！");
					return false;
				}
				//if(!window.confirm("确定导入的题型是：“"+choose.options[choose.selectedIndex].text+"”，文件为：“"+stf+"的试题文件吗”？"))
				//if(!window.confirm("确定导入试题？"))
				//	return false;
				return true;
			}
		</SCRIPT>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="选择文件" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">试题批量导入</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<h3>
				词汇导入检测详情
		   </h3>
			<form action="word_import.action"
				enctype="multipart/form-data" method="post" onSubmit="return window.confirm('确定导入？');">
				<div style="font-size:12px;color:red;"><s:property value="elmessage" escape="false"/></div>
				
				<input class=textbg style="border: none;" type="submit" value="确认导入">　　				
				　<a href="question_importByqlibInit.action?questionLib.id=${questionLib.id }"  class=textbg>取消</a>
				<br/>
				<br/>
				<s:hidden name="questionLib.id" />
				<s:hidden name="stfilename" />
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
