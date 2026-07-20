<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻列表页" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">培训班结业申请</span>
			</li>
		</ul> 
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="76%" align="center" cellpadding="2" cellspacing="1"
			>
			<tr>
				<th align="center" >
					培训班名称
				</th>
				<th align="center" >
					证书名称
				</th>
				<th align="center" >
					学员姓名
				</th>
				<th align="center" >
					&nbsp;
				</th>
				<th align="center" >
					&nbsp;
				</th>
			</tr>
			<s:iterator value="myClasses">
			<tr>
				<td align="center" >
					<s:property value="elClass.name"/>
				</td>
				<td align="center" >
					<s:property value="elClass.certificatename"/>
				</td>
				<td align="center" >
					<s:property value="user.realname"/> 
				</td>
				<td align="center" >
					<!--<a
						href="elclass_view.action?elclass.id=<s:property value="id" />">详情</a>
				-->
					<a
						href="elclass_graduate_apply_op.action?myClasse.elClass.id=<s:property value="elClass.id"/>&myClasse.user.id=<s:property value="user.id"/>&status=2">批准结业</a>
				</td>
				<td align="center" >
					<a
						href="elclass_graduate_apply_op.action?myClasse.elClass.id=<s:property value="elClass.id"/>&myClasse.user.id=<s:property value="user.id"/>&status=3">不批准结业</a>
				</td>
			</tr>
			</s:iterator>
		</table>
		<Br>
		<br>
		<div style="width:76%;text-align: center;">	<wysLib:page></wysLib:page></div>
	
	
	</body>
</HTML>
