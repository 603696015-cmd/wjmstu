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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="questions.size==0">没有符合条件的试题</s:if>
			<s:else>
				<table width="500px" align="left" border="1" cellspacing="1" cellpadding="1">
					<tr>
						<th height="20" align="left" >
							标题
						</th>
					</tr>
					<s:iterator value="questionarts">
						<tr>
							<td height="20" align="left" >
								<a href="" onclick="qanswer_setans(this);return false;"><s:property value="title" /></a>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<th height="20" colspan="2" align="left" >
							<wysLib:page1></wysLib:page1>
						</th>
					</tr>
				</table>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
