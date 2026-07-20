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
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="角色管理" />
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="font-size: 13px;text-align:center; margin-top: 0px;">

			<table align="center" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<th>
						角色名
					</th>
					<th>
						描述
					</th>
					<th width="200">
						分配功能
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="roles">
						<tr>
							<td height="30" style="padding-left: 8px; color: blue;"
								width="200" align="left">
								<s:property value="name" />
							</td>
							<td height="30" style="padding-left: 8px;" align="left">
								<s:property value="description" />
							</td>
							<td width="200" height="20" align="center">
								<a
									href="rolefunc_addInit.action?role.id=<s:property value="id"/>"
									class="textbg6">分配功能</a>
								<a href="role_alterInit.action?role.id=<s:property value="id"/>"
									class="textbg4">修 改</a>
								<a onClick="return confirm('确定删除该角色？');"
									href="role_delete.action?role.id=<s:property value="id"/>"
									class="textbg4">删 除</a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
	    <br/>
			<a class="textbg4" style="width:90px;"
					href="role_addInit.action">添加新角色</a>
		</div>
		<SCRIPT type="text/javascript">
			if("${elmessage}"!='null'&&"${elmessage}"!='')
				 alert("${elmessage}!");
		</SCRIPT>
	
	</body>
</HTML>
