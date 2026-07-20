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
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">添加用户组</span>

			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="group_list.action">用户组管理</a>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
	
		<div style="margin-top: 40px;">
			<s:form action="group_add" method="post" theme="simple">

				<table width="60%" cellpadding="2" cellspacing="2"
					>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>用户组名称</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textfield cssStyle="width:300px" name="group.name"/>
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>描    述</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:textarea cssStyle="width:300px;height:80px;" name="group.description"
									 />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="30" align="center" >
							<strong>类    别</strong>
						</td>
						<td height="30" align="left" >
							<label>
								<s:select name="group.gtype" list="#{1:'特殊用户组一',2:'特殊用户组二',3:'一般用户组'}"
									 />
							</label>
						</td>
					</tr>
					<tr>
						<td width="120" height="50" align="center" >
							&nbsp;
						</td>
						<td height="50" align="left" >
							<input type="submit" value="确认添加">
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
