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
				if(!window.confirm("确定导入的题型是：“"+choose.options[choose.selectedIndex].text+"”，文件为：“"+stf+"的试题文件吗”？"))
					return false;
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
		<div style="margin-top: 0px; margin-left: 80px;">
			<h3>
				此处导入的试题将会在题库【
				  <s:property value="questionLib.name" />
				】里面
		  </h3>
			<br>
			<form action="question_importByqlibcheck.action" onSubmit="return check_xx();"
				enctype="multipart/form-data" method="post">

				选择需要导入的文档：
				<input type="file" name="st" id="stf" size="50">
				<br>
				<br>
				<input class=textbg style="border: none;" type="submit" value="导入题库">　　				
				　<a href="download.jsp?filename=elstuffs/all_type_question.xls"  class=textbg>题库格式下载</a>
				　<a href="question_list.action?questionLib.id=${questionLib.id }&sublibs=1&question.status=-1"  class=textbg>返回试题列表</a>
				<font color="red"><br>
				<br>
				注意：请严格按照题库格式内容来制作题库，注意题库格式中的最后一行在制作完成后需去掉!</font>
				<br>
				<s:hidden name="questionLib.id" />
				<div style="font-size:12px;color:red;"><s:property value="elmessage" escape="false"/></div>
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
