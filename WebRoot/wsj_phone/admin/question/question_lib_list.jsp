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
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="试题库目录树" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">管理试题库</span>
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
			<wysLib:qlibtree itype="OP"
				href="question_lib_view.action?questionLib.id=" rootAble="true"></wysLib:qlibtree>
			<!--<br/>
				<a href="questionlib_importInit.action">导入题库目录</a>&nbsp;&nbsp;&nbsp;<a href="question_lib_exportExcel.action">导出题库目录</a>&nbsp;&nbsp;&nbsp;<a href="question_importInit.action">导入试题库</a>&nbsp;&nbsp;&nbsp;<a href="question_exportExcel.action">导出试题库</a>
		-->
		</div>
		<br />
		<br />
		<a href="question_lib_addInit.action" class="textbg">添加试题类别</a>
		<a href="question_list.action" class="textbg">试题列表</a>
		<br />
		<font color="red"><b>提示:</b>点击名称后查看类别详情</font>
		<!-- 内容 -->
	
	</body>
</HTML>
<!--  	 系统管理---资源目录---题库管理(jsp页面)    	  --> 