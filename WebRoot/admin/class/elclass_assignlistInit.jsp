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
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
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
			<span style="font-weight: bold;">可分配培训班</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div>
			<br>
			<br>
			<form action="elclass_assignlist.action" method="post" name="class_can_assignment_list"
				id="class_can_assignment_list">
				<input type="hidden" name="operate_search" value="ok">
				<label>
					培训班来源
					<select name="classSource">
						<option value="1" id="class_sourse_0">
							上级部门分配的
						</option>
						<option value="2" id="class_sourse_0">
							本部门范围内的
						</option>
					</select>
				</label>
				<br>
				<br>
				<label>
					培训班名称
					<input type="text" name="elclass.name" value="">
					<br />
				</label>
				<input type="hidden" name="pN" value="0">
				<input type="hidden" name="pS" value="10">
				<br />
				<input type="submit" value="搜 搜 看">
			</form>

		</div>

	</body>
</HTML>
