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
     response.setHeader("Content-disposition","attachment; filename=GeneralPractice.xls"); 
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
		<div>
			<table width="600" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						Ӧ��ϰ����
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="examprac.totalnumber" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						����ϰ����
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="examprac.usersize" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						ƽ����ϰ�˴�
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="examprac.avgnumber" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						������Աƽ����
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="examprac.avgscore" />
					</td>
				</tr>��
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						��������
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="examprac.passsize" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						������ 
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="examprac.passreta" />%
					</td>
				</tr>
				<tr>
					<td height="30" width="150px;" align="center" >
						90������
					</td>
					<td height="30" align="center" >
						<s:property value="examprac.pass9_" />

					</td> 
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						80-90��
					</td>
					<td height="30" align="center" >
						<s:property value="examprac.pass8_9" />

					</td> 
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						70-80��
					</td>
					<td height="30" align="center" >
						<s:property value="examprac.pass7_8" />

					</td> 
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						60-70��
					</td>
					<td height="30" align="center" >
						<s:property value="examprac.pass6_7" />

					</td> 
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						60����
					</td>
					<td height="30" align="center" >
						<s:property value="examprac.pass_6" /> 
					</td> 
				</tr>

			</table> 
		</div>
		<!-- ���� -->
	</BODY>
</HTML>
