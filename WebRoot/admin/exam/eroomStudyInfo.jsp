<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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
		<base target="_top" href="<%=basePath%>">
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
			td {font-size: 12px;color: #333333;line-height: 150%}
			tr {background-color: expression(( this . sectionRowIndex % 2 == 0) ?"#ffffff" : "#f4f4f4" )}
		</style>
		<script type="text/javascript"> 
		 	function page(i){ 
		 		document.getElementById("pageNow").value=i;
		 		acc_list.submit();
		 	}
		</script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="用户列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">用户管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_addInit.action?elUser.department.id=<s:property value="department.id"/>">添加用户</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="eroomStudyInfo" method="post" name="acc_list">
			<s:hidden name="pN" id="pageNow" />
			<s:hidden name="pS" />
			<s:hidden name="examRoom.id" />
			<s:hidden name="myroom.status" />
		</s:form>
		<!-- 内容 -->
		<div style="margin-top: 0px;text-align:center;width:820px;">
			<table align="center" cellpadding="1" cellspacing="1" width="800">
				<caption>考场：<s:property value="examRoom.title"/></caption>
				<tr>
					<th align="center">
						姓名
					</th>
					<th align="center">
						单位/部门
					</th>
					<th width="50">
						性别
					</th>
					<th width="50">
						工种
					</th>
					<th width="50">
						工号
					</th>
					<th width="120">
						身份证
					</th>
					<th width="150">
						联系方式
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="elusers">
						<tr>
							<td align="center">
								<s:property value="realname" />
							</td>
							<td align="center">
								<s:property value="department.name" />
							</td>
							<td align="center">
								<s:property value="sex" />
							</td>
							<td align="center">
								<s:property value="jingzhong_" />
							</td>
							<td align="center">
								<s:property value="xuhao" />
							</td>
							<td align="center">
								<s:property value="shenfenzheng" />
							</td>
							<td align="center">
								<s:property value="movePhone" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<wysLib:page></wysLib:page>
			<br/>
			<a
						href="#" onClick="window.close();return false;"
						style="width: 80px;padding: 3px 0px 3px 0px;" class="textbg4">关闭</a>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>