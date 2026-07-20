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
		<TITLE>考场批次类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="确认删除" /></div>
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroom_batchlib_view.action?erbatchLib.id=<s:property value="erbatchLib.id" />">显示考场批次类别信息</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<span style="font-weight: bold;">类别删除 </span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="eroom_batchlib_delete" method="post" name="catalog_info"
				theme="simple">
				<input type="hidden" name="erbatchLib.id" value="<s:property value="erbatchLib.id"/>">
				<table width="50%" align="center" cellpadding="2" cellspacing="1"
					>
					<tr>
						<td height="30" align="left" >
							<label>
								<strong> 确定要删除这个类别吗</strong> &nbsp;&nbsp;<s:property value="erbatchLib.name"/>
							</label>
							&nbsp;
						</td>
					</tr>
					<!--<tr>
						<td height="30" align="left" >
							<strong> 下属考场批次与子类别操作</strong> 并入上级类别
							<input type="radio" name="course_sourse" checked="true" value="0">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label>
								与本类别同时删除
							<input type="radio" name="course_sourse" value="1">
							</label>
						</td>
					</tr>
					--><tr>
						<td height="50" align="left" >
							<input type="submit" value="确认删除">
							&nbsp;
						</td>
					</tr>
				</table>
			</s:form>

		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
