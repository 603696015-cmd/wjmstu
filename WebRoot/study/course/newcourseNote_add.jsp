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
<script type="text/javascript">
   function cnote_addcl(){
      
   }
</script>
<html>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>学习课程--<s:property value="course.name" /></TITLE>
	 
	</HEAD>
	<script type="text/javascript">
	   function cnote_addcl(){
	      newcourseNote_addInit.submit();
	      window.close();
	   }
	</script>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	<s:form action="courseNote_add"  method="post" name="newcourseNote_addInit">
	     <s:hidden name="course.id"></s:hidden>
	     <s:hidden name="cnote.id"></s:hidden>
		<table width="100%" align="center" cellpadding="2" cellspacing="1"
			>
			<tr>
				<td align="center" bgcolor="#FFFFFF" style="font: 11px;">
					内容：
				</td>
				<td align="center" >
					<textarea id="cnote.content" name="cnote.content" style="width: 340px;height: 245px;"></textarea>
				</td>
			</tr>
			<tr>
				<td align="center" >
				</td>
				<td align="center" >
					<input type="button" onclick="cnote_addcl();" value="保存">
				</td>
			</tr>
			</table>
		</s:form>
	</BODY>
</HTML>
