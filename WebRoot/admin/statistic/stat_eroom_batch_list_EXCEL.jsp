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
     response.setHeader("Content-disposition","attachment; filename=CourseUserList.xls"); 
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
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th  width="180" height="30" align="center" >
						����</th>
					<th  width="120" align="center" >�˺�</th>
					<th width="200" height="30" align="center" >
						����					</th>
					<th width="130" height="30" align="center" >
						��ʱ��/��ѧʱ��					</th>
					<th width="70" height="30" align="center" >
						ѧϰ����					</th>
					<!--<th height="30" align="center" >
						�ѻ�ѧ��
					</th>-->
					<th width="50" height="30" align="center" >
						�ɼ�					</th>
					<th width="80" height="30" align="center" >
						�Ƿ񼰸�				</th>
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myCourses">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="user.realname" /></td>
						<td align="center" ><s:property value="user.username" /></td>
						<td width="150" height="30" align="center" >
							<s:property value="user.department.name" />					  </td>
						<td width="130" height="30" align="center" >
							<s:property value="course.during" />
							���� /
							<s:property value="passtime" />
							����					  </td>
						<td width="70" height="30" align="center" >
							<s:property value="processStr" /> 
						<!--<td height="30" align="center" >
							<s:property value="myCredit" />-->
						</td>
						<td align="center" >
							<s:property value="myExamPaper.myScore" />		
						</td>
						<td>
							<s:if test="myExamPaper.ispassed==0">������</s:if>
							<s:else>����</s:else>
						</td>
					</tr>
				</s:iterator></tbody>
		  </table>
		</div>
		<!-- ���� -->
	</BODY>
</HTML>
