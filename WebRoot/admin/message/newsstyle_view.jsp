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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/libutil.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		
		
		
		</script>
		<style type="text/css">
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="新闻类型简介" />
				</div>
			</li>
			
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td valign="top">
					<table width="100%" cellpadding="1" cellspacing="1">
						<tr>
							<td width="120" height="30" align="right">
								类型名称：
							</td>
							<td>
								<label>
									<s:property value="nstyle.name" />
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" height="30" align="right">
								类型介绍：
							</td>
							<td>
								<label>
									<s:property value="nstyle.description" />
								</label>
							</td>
						</tr>
						<tr>
							<td width="120" height="50" align="center">&nbsp;
								

							</td>
							<td>
								<a
									href="newsstyle_alterInit.action?nstyle.id=<s:property value="nstyle.id"/>"
									class="textbg4">修 改</a>
									  <input class="textbg6" type="button" onClick="document.location='newsstyle_list.action'" value="返回类型">
								      <input class="textbg6" type="button" onClick="document.location='newsManage_list.action?ntype.id=<s:property value="ntype.id"/>'" value="新闻列表">
								<a onClick="return window.confirm('确定删除？');"
											href="newsstyle_delete.action?nstyle.id=<s:property value="nstyle.id"/>"
											class="textbg4">删 除</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</HTML>
