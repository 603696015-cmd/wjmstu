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
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>  
	</HEAD>
	<body>
		<!-- 内容 -->
		<div  > 
		<table width="950" align="center" cellpadding="1" cellspacing="1"	bgcolor="#EBEBEB">
		<tr><td　align="center">　
		注意：<span style="color:red;">退回修改</span>   是退回让创建者修改考场详细信息.
		     <span style="color:red;">修改并申请</span>   需确认下面已修改完成后提交到终审继续审核！<br/>
		<a onClick="return confirm('确定退回修改？')" href="examroom_sh_p.action?examRoom.id=<s:property value="examRoom.id"/>&examRoom.valid=0" class="textbg6">退回修改</a> 
		<a onClick="return confirm('确定修改已完成并提交审核？')" href="examroom_sh_p.action?examRoom.id=<s:property value="examRoom.id"/>&examRoom.valid=3" class="textbg6">修改并申请</a> <br/>
		</td> 
		</tr> 
		</table>
			<iframe id="xuanxiuFrame" src="erwithout_prima_alterInit.action?examRoom.id=<s:property value="examRoom.id"/>" width=100% height=780 
								marginwidth="0" marginheight="0" frameborder=0  onload="this.height=xuanxiuFrame.document.body.scrollHeight + 20"></iframe>
			<iframe id="xuanxiuFrame" src="examroom_assignwcInit.action?examRoom.id=<s:property value="examRoom.id"/>" width=100%   
								marginwidth="0" marginheight="0" frameborder=0  onload="this.height=xuanxiuFrame.document.body.scrollHeight + 20"></iframe>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
