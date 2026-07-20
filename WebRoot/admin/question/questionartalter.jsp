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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<a>修改文章</a>
			<a onclick="qsearch_list();return false;" href="#"> 文章列表</a>
			<s:if test="questions.size==0">没有符合条件的试题</s:if>
			<s:else>
				<table width="500px" align="center" cellspacing="1" cellpadding="1">
					<tr>
						<th height="20" width="70" align="left" >
							标题
						</th>
						<th height="20" align="left" >
							<input size="40" type="text" id="add_title" value="<s:property value="questionart.title"/>">
						</th>
					</tr>
					<tr>
						<td height="20" align="left" >
							内容
						</td>
						<td height="20" align="left" >
							<textarea rows="10" cols="40" id="add_content"><s:property value="questionart.content"/></textarea>
						</td>
					</tr>
					<tr>
						<td height="20" align="left" >
						</td>
						<td height="20" align="left" >
							<input size="0" value="<s:property value="questionart.id"/>" type="hidden" id="add_id">
							<input type="button" onclick="qsearch_alter();" value="修改" />
						</td>
					</tr>
				</table>
			</s:else>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
