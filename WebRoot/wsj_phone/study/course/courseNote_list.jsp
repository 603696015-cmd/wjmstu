<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<TITLE>学习课程--<s:property value="course.name" /></TITLE>
	</HEAD>
	<BODY style="overflow: scroll;text-align: center;" >
		<s:if test="cnotes.size==0"><br><br>无笔记</s:if>
		<s:else>
			<table width="100%" align="center" cellpadding="2" cellspacing="1"
			>
			<tr>
				<td align="center" bgcolor="#FFFFFF" style="font: 11px;">
					内容：
				</td>
				<td align="center" bgcolor="#FFFFFF" style="font: 11px;">
					日期
				</td>
				<td align="center" bgcolor="#FFFFFF" style="font: 11px;">
				
				</td>
			</tr>
			<s:iterator value="cnotes">
			<tr>
				<td bgcolor="#FFFFFF" style="font: 11px;">
					<a href="javascript:showContent('content_<s:property value="id"/>')"><s:property value="shotContent"/></a>
				</td>
				<td align="center" bgcolor="#FFFFFF" style="font: 11px;">
					<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td align="center" bgcolor="#FFFFFF" style="font: 11px;">
					<a href="javascript:deleteNote(<s:property value="id"/>)">删除</a>
				</td>
			</tr>
			<tr style="display: none;" id="content_<s:property value="id"/>">
				<td bgcolor="#FFFFFF" style="font: 12px;padding: 5px;" colspan="3" >
					<s:property value="content" />
				</td>
			</tr>
			 </s:iterator>
			</table>
			</s:else>
	
	</body>
</HTML>
