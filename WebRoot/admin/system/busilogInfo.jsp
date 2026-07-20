<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--日志详情</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%;padding-left:5px;}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="日志详情" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="margin-top: 0px;text-align:center;">
			<div style="margin-top: 0px;">
				<table id="info1" width="800px" cellpadding="1" cellspacing="1">
					<caption>
						日志详情
					</caption>
					<tr>
						<td width="120" height="30" align="center" >
							姓名
						</td>
						<td height="30" align="left" >
							<s:property value="ellog.user.username" />
							(
							<s:property value="ellog.user.realname" />
							)
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							部门
						</td>
						<td height="30" align="left" >
							<s:property value="ellog.user.department.name" />
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							日期
						</td>
						<td height="30" align="left" >
							<s:date name="ellog.optime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							操作类型
						</td>
						<td height="30" align="left" >
							<s:property value="ellog.optypeStr" />
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							操作模块
						</td>
						<td height="30" align="left" >
							<s:property value="ellog.opmodStr" />
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							详细内容
						</td>
						<td height="30" align="left" >
							<s:property value="ellog.content" />
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							结果
						</td>
						<td height="30" align="left" >
							<s:property value="ellog.opresultStr" />
						</td>
					</tr>
				</table>
				
			</div>
		</div>
	</BODY>
</HTML>
