<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="depSelect" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>用户列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="csses/member.css">
		<link rel="stylesheet" type="text/css" href="csses/common.css">
	</HEAD>

	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<div class="operateMenu">
			<div class="operate1">
				消息发送
			</div>
		</div>
		<div class="operateLine"></div>
		<s:form action="mess_send.action" method="post" theme="simple">
			<table width="96%" align="center" cellpadding="2" cellspacing="2"
				>
				<tr>
					<td height="20" align="center" >
						收件人
					</td>
					<td height="20" align="left" >
					<s:select list="mems" name="mess.mess_to.mem_id" listKey="mem_id" listValue="mem_name"></s:select>
					</td>
				</tr>
				<tr>
					<td height="20" align="center" >
						消息标题
					</td>
					<td height="20" align="left" >
						<input name="mess.mess_title" type="text" id="title" size="40" />
					</td>

				</tr>
					<tr>
					<td height="20" align="center" >
						消息内容
					</td>
					<td height="20" align="left" >
						<textarea name="mess.mess_content" cols="50" rows="10">消息内容</textarea>
					</td>

				</tr>
			</table>
			<input type="submit" value="发送">
		</s:form>
	
	</body>
</html>
