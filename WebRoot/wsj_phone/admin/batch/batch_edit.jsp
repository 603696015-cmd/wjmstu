<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
<div class="dh3">
	<div class="newpos"></div>
	<div class="newpos2">
		<a href="cltype_list.action">培训批次管理</a>
		<span style="font-weight: bold;">培训批次添加</span>
	</div>
</div>
<s:form action="batch_saveOrUpdate" method="post" name="batch_info" theme="simple">
	<s:hidden name="batch.id"></s:hidden>
	<table width="95%" cellpadding="2" cellspacing="1" >
		<tr>
			<td width="120" height="30" align="center" >
				批次名称
			</td>
			<td >
				<label> 
					<s:textfield name="batch.name" id="name" size="80" />
				</label>
			</td>
		</tr>
		<tr>
			<td width="120" height="30" align="center" >
				批次介绍
			</td>
			<td >
				<label>
					<s:textarea name="batch.description" cols="60" rows="7" />
				</label>
			</td>
		</tr>
		<tr>
			<td width="120" height="50" align="center" >&nbsp;
			</td>
			<td ><input  class=textbg6 style="height:35px;" type="submit" value="确认保存">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<iframe id="classFrame" src="batch_elclass_list.action?batchId=${batch.id}" width=98% height=320 
					marginwidth="0" marginheight="0" frameborder=0   onload="this.height=classFrame.document.body.scrollHeight + 20"></iframe>
			</td>
		</tr>
	</table>
	<br>
	
	
</s:form>


	</body>
</HTML>
