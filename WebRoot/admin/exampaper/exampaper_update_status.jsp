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
		<TITLE>试卷创建完成</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="下一步操作" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">管理试卷</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			 <h3>试卷【<s:property value="examPaper.title"/>】成功创建完成</h3>
			 <br/>
			 <input class=textbg style="border: none;" type="button"
							value="继续添加试卷" onClick="document.location='exampaper_addInit.action'">
		 <input class=textbg style="border: none;" type="button"
							value="返回试卷列表" onClick="document.location='exampaper_list.action?sublibs=1'">
		 <input class=textbg style="border: none;color: red;" type="button"
							value="创建考场" onClick="document.location='erwithout_addInit.action?course.id=-1'">
		
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
