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
     response.setHeader("Content-disposition","attachment; filename=Batch situation.xls"); 
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
			<table width="80%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						��������
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="erbatch.usersize" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						ȫ����Աƽ���ֵ�ƽ����
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="erbatch.avgscore" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						��������
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="erbatch.passsize" />
					</td>
				</tr>
				<tr>
					<td height="30" width="150px;" align="center" >
						90������
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass9_" />

					</td>
					<td height="30" align="center" >
						90������
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						80-90��
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass8_9" />

					</td>
					<td height="30" align="center" >
						80-90��
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						70-80��
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass7_8" />

					</td>
					<td height="30" align="center" >
						70-80��
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						60-70��
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass6_7" />

					</td>
					<td height="30" align="center" >
						60-70��
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						60����
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass_6" />
					</td>
					<td height="30" align="center" >
						60����
					</td>
				</tr>
		  </table>
			<table cellpadding="1" cellspacing="1" width="80%">
				<tr>
					<th height="30" align="center" >
						��������
					</th> 
					<th width="70" height="30" align="center" >
						��������					</th>
					<th width="100" height="30" align="center" >
						�������					</th>  
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="examRooms">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="title" />
						</td> 
						<td width="70" height="30" align="center" >
							<s:property value="userSize" />
					  </td>
						<td width="100" height="30" align="center" >
							<s:property value="eroomLib.name" />
						</td>  
					</tr>
				</s:iterator></tbody>
		  </table>
		</div>
		<!-- ���� -->
	
	</body>
</HTML>
