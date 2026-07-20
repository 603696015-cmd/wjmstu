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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>   
		<SCRIPT type="text/javascript">
			function myload(){
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				}
			
			}
			function searchRelateTableInit(elementId,type){
				width=600;
				height=500;
				var url = "searchRelateTableInit.action?x="+Math.random()+"&type="+type;
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog(url,null,sFeature);
				if(rv!=undefined&&rv!=""){
					document.getElementById(elementId).value=rv;
				}
				
			}
		</SCRIPT>	
	</HEAD>
	<BODY onload="myload();">
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="增加险种信息" /></div>
			</li> 
		</ul>
		<!-- 内容 -->
		<div>
			<s:form action="IC_add" method="post" name="IC_info" theme="simple" id="course_info">
				 <div>
					<B>注意：</B> 1、填写险种表名 , 表名不可更改； 
				</div> 
				<table cellpadding="1" cellspacing="1" bgcolor="#ECEDEB" width="700">
					<tr>
						<td width="100" height="30" align="center" bgcolor="#FFFFFF">
							险种名称：
						</td>
						<td bgcolor="#FFFFFF">
							<label> 
								<input name="IC.name" type="text" id="ICname" value="${IC.name}" size="60">
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" height="30" align="center" bgcolor="#FFFFFF">
							险种介绍：
						</td>
						<td bgcolor="#FFFFFF">
							<label>
								<textarea name="IC.description" cols="60" rows="7"><s:property value="IC.description"/></textarea>
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" height="30" align="center" bgcolor="#FFFFFF">
							险种表名：
						</td>
						<td bgcolor="#FFFFFF">
							<label> 
								IC_U_<input name="IC.tableName" type="text" id="ICTableName" value="${IC.tableName}" size="60" >
							</label>
						</td>
					</tr> 
					<!-- <tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							投保人数据是否读取：
						</td>
						<td bgcolor="#FFFFFF">
							<label> 
								<input name="IC.read_auto_toubaoren" type="text" id="read_auto_toubaoren"  size="60" >
								<a href="#" onClick="searchRelateTableInit('read_auto_toubaoren','user');return false;" class="textbg6">点此进行选择</a>
							</label>
						</td>
					</tr> 
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							被保人数据是否读取：
						</td>
						<td bgcolor="#FFFFFF">
							<label> 
								<input name="IC.read_auto_beibaoren" type="text" id="read_auto_beibaoren"  size="60" >
								<a href="#" onClick="searchRelateTableInit('read_auto_beibaoren','user');return false;" class="textbg6">点此进行选择</a>
							</label>
						</td>
					</tr> 
					<tr>
						<td width="160" height="30" align="center" bgcolor="#FFFFFF">
							标地数据是否读取：
						</td>
						<td bgcolor="#FFFFFF">
							<label> 
								<input name="IC.read_auto_biaodi" type="text" id="read_auto_biaodi"  size="60" >
								<a href="#" onClick="searchRelateTableInit('read_auto_biaodi','biaodi');return false;" class="textbg6">点此进行选择</a>
							</label>
						</td>
					</tr> -->
					</table>  <br>
						<input class="textbg6" name="submit" type="submit" value="确认添加" /> 
				</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
