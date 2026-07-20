<%@ page language="java" pageEncoding="gbk"%> 
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();  
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() 
			+ path + "/";
%>
<% 
    //���ǿ���һ�У���ǰ���������Ϊ���յ�һ��excel�� 
     response.setHeader("Content-disposition","attachment; filename=import_user.xls");  
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>�γ�������</TITLE>
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
		<!-- ���� -->
		<div style="margin-top: 40px; text-align: center;">
						<table align="center" cellpadding="1" cellspacing="1" width="100%"
							height="100%" >
							<tr> 
								<th width="90">
									�û���	</th>
								<th width="100">
									����	</th>
								<th width="100">
									���	</th>
								<th width="100">
									����	</th>
								<th width="100">
									�Ա�	</th>
								<th width="100">
									����</th>
								<th width="100">
									���֤	</th>
								<th width="100">
									ְ��	</th>
								<th width="100">
									ְ��	</th>
								<th width="100">
									����	</th>
								<%-- 
								<th width="100">
									��λ	</th>
								 --%>
								<th width="100">
									���ű��	</th>
							</tr><tbody> 
							<s:iterator value="elUsers">
								<tr>
									<td height="20" align="center">
										<s:property value="username" />
									</td>
									<td height="20" align="center">
										<s:property value="password" />
									</td>
									<td height="20" align="center">
										<s:property value="xuhao" />
									</td>
									<td height="20" align="center">
										<s:property value="realname" />
									</td>
									<td height="20" align="center">
										<s:property value="sex" />
									</td>
									<td height="20" align="center">
										<s:property value="dishi_" />
									</td>
									<td height="20" align="center">
										<s:property value="shenfenzheng" />&nbsp;
									</td>
									<td height="20" align="center">
										<s:property value="zhiji_" />
									</td>
									<td height="20" align="center">
										<s:property value="zhiwu_" />
									</td>
									<td height="20" align="center">
										<s:property value="jingzhong_" />
									</td>
									<%-- 
									<td height="20" align="center">
										<s:property value="gangwei_" />
									</td>
									 --%>
									<td height="20" align="center">
										<s:property value="department.bh" />&nbsp;
									</td>
								</tr> 
								</s:iterator>
							</tbody>
						</table>
		</div>
		<!-- ���� -->
	
	</body>
</HTML>
