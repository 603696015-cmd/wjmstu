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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>险种管理</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />  
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				} 
			} 
		</SCRIPT>	
	</HEAD>
	<BODY onload="myload();">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="增加险种信息列" /></div>
			</li> 
		</ul>
		<!-- 内容 -->
		<div>
			<s:form action="IC_U_AddColumn.action" theme="simple" method="post"  name="acc_list"> 
				 <div>
					<B>注意：</B> 
					<br/>1、填写险种详情表的列名  不可更改 ,不可为中文； 
					<br/>2、列类型 不可更改
					<br/>3、列名不可重复
				</div> 
				<table cellpadding="1" cellspacing="1" bgcolor="#ECEDEB" width="700">
					<tr>
						<td width="150" height="30" align="center" bgcolor="#FFFFFF">
							列名称：
						</td>
						<td bgcolor="#FFFFFF">
							<label> 
								<s:textarea name="bsql.column_name" cols="30" rows="1"></s:textarea> 
							</label>
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center" bgcolor="#FFFFFF">
							页面显示列名称：
						</td>
						<td bgcolor="#FFFFFF">
								<s:textarea name="bsql.view_name" cols="30" rows="1"></s:textarea>  
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center" bgcolor="#FFFFFF">
							列类型：
						</td>
						<td bgcolor="#FFFFFF"> 
							<label>  
								<s:select theme="simple" name="bsql.parametersType" cssClass="g-select" list="bsql.parametersTypes_"/> 
							</label> 
						</td>
					</tr>
					<tr>
						<td width="150" height="30" align="center" bgcolor="#FFFFFF">
							页面显示方式：
						</td>
						<td bgcolor="#FFFFFF">
							<label>  
								<s:select theme="simple" name="bsql.viewType" cssClass="g-select" list="bsql.viewType_"/> 
							</label>
						</td>
					</tr> 
					</table>  <br>
						<input class="textbg6" name="submit" type="submit" value="确认添加" /> 
						<s:hidden name="IC.id"/>  
				</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
