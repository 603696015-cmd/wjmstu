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
		<div class="dh3">
			<div class="newpos"></div>
			<div class="newpos2">
				<a href="cltype_list.action">培训班类别管理</a>
				<span style="font-weight: bold;">培训班删除申请</span>
				<a href="elclass_course.action?elclass.id=<s:property value="elclass.id"/>">管理培训班课程</a>
				<!--<a href="elclass_applydeleteInit.action?elclass.id=<s:property value="elclass.id"/>"">申请删除培训班</a>
			--></div>
		</div>
		<table cellpadding="2" cellspacing="1" width="50%" >
			<tr>
				<td width="160" height="30" align="center" >
					培训班名称：
				</td>
				<td height="30" >
					<label>
						<s:property value="elclass.name" />
					</label>
				</td>
			</tr>

			<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					培训班介绍：
				</td>
				<td height="30" >
					<label>
						<s:property value="elclass.description" />
					</label>
				</td>
			</tr>
			<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					结业证书名称：
				</td>
				<td height="30" >
					<label>
						<s:property value="elclass.certificatename" />
					</label>
				</td>
			</tr>
			<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					所属类别：
				</td>
				<td height="30" >
					<s:property value="elclass.cltype.name" />
				</td>
			</tr>
			<!-- <tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						选班收费（个人）：
					</td>
					<td height="30" >
					</td>
				</tr> -->
			<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					结业条件：
				</td>
				<td height="30" >
					<label>
						<s:property value="elclass.optionalcredit" />
					</label>
				</td>
			</tr>
			<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
					培训班状态：
				</td>
				<td height="30" >
					<label>
						<s:if test="elclass.status==1">开通</s:if>
						<s:else>关闭</s:else>
					</label>
				</td>
			</tr>

			<tr>
				<td height="50" align="center" >
					&nbsp;
				</td>
				<td height="30" >
					<a href="elclass_delete_apply.action?elclass.id=<s:property value="elclass.id"/>">确认删除</a>
				</td>
			</tr>
		</table>
	
	</body>
</HTML>
