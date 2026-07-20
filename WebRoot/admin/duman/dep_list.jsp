<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
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
		<script type="text/javascript" src="js/jquery.js"></script>
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="部门管理" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="margin-top: 0px;">
			<wysLib:dep_list_aj iname="" itype="" ivalue=""
				href="dep_view.action?department.id=" rootAble="true"></wysLib:dep_list_aj>
		</div>
		<br/><br/>
		<a class="textbg5" href="dep_addInit.action?department.id=<s:property value="depTree.id"/>">添加新部门</a>
		<a class="textbg5" href="account_search.action?department.id=<s:property value="depTree.id"/>&sub_department=1&elUser.valid2=0">用户管理</a>
		<s:if test="roleid==1"><a class="textbg5" href="account_importInit2.action">导入部门</a></s:if>
		<br/><br/>
<!-- 	<div style="color:#5097D8;font-size:15px;font-weight:600;">
			二级页面的部门
		</div>
		<div style="font-size: 13px;text-align:center; margin-top: 0px;">

			<table align="left" cellpadding="1" cellspacing="1" width="600">
				<tr>
					<th>
						部门名称
					</th>
					<th>
						描述
					</th>
					<th width="200">
						功能
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="depsp">
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
									href="dep_view.action?department.id=<s:property value="id"/>"
									class="textbg6">查看</a>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
		 -->	
	
	</body>
</HTML>
<!--		 系统管理---用户与部门---部门管理(jsp页面)    	  -->