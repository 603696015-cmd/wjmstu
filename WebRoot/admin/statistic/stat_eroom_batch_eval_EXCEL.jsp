<%@ page language="java" pageEncoding="GBK"%> 
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
     response.setHeader("Content-disposition","attachment; filename=Department is.xls"); 
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
		<script type="text/javascript" src="js/message.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!-- ���� -->
		<div style="margin-top: 40px; text-align: center;">
							<table width="700px" align="center" cellpadding="1"
								cellspacing="1" >
								<tr>
									<th align="center" >
										����
									</th>
									<th align="center" >
										����
									</th>
									<th align="center" >
										��������
									</th>
									<th align="center" >
										��������
									</th>
									<th align="center" >
										������
									</th>
									<th align="center" >
										ƽ����
									</th>
								</tr>
								<s:iterator value="departments" status="st">
									<tr>
										<td align="center" >
											<s:property value="#st.index+1" />
										</td>
										<td align="center" >
											<s:property value="name" />
										</td>
										<td align="center" >
											<s:property value="userCount" />
										</td>
										<td align="center" >
											<s:property value="userCredit" />
										</td>
										<td align="center" >
											<s:property value="userCredit/userCount" />
										</td>
										<td align="center" >
											<s:property value="avg" />
										</td>
									</tr>
								</s:iterator>
							</table>
		</div>
		<!-- ���� -->
	</BODY>
</HTML>
