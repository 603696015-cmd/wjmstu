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
		<title>险种修改列</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/InsuranceCategories.js"></script> 
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style> 
		<SCRIPT type="text/javascript">
			function updateColumn(tablename,columnname,ic_id){
				document.getElementById("column_name").value = document.getElementById("TC_Info.column_name").innerHTML ;
				var value1 = document.getElementById("TC_Info.CName").value;
				var value2 = document.getElementById("TC_Info.Cview").innerHTML;
				var value3 = document.getElementById("TC_Info.Cview_value").innerHTML;
				
				if(value3 == ""){
					document.getElementById("change_value").value = value1 + "==" + value2 ;
				}else {
					document.getElementById("change_value").value = value1 + "==" + value2 + "==" + value3;
				}
				uu.submit();
			}
		</SCRIPT>	
	</HEAD>
	<body onload="myload();">
		<!-- 内容 -->
		<div style="margin-top: 30px"> 
			<form action="PG_updateColumn.action" method="post" name="uu">
			<input type='hidden' name='IC.id' value="<s:property value='IC.id'/>"/>
			<input type='hidden' name='IC.tableName' value="<s:property value='tablename'/>"/>
			<input type='hidden' name='column_name' id="column_name" />
			<input type='hidden' name='change_value' id="change_value" />
			<table width="80%" align="center" cellpadding="1" cellspacing="1"> 
				<tr>
					<th height="30" align="center" >
						列名称  
					</th>
					<th height="30" align="center" >
						列类型 
					</th>
					<th height="30" align="center" >
						页面显示名称
					</th>
					<th width="120" height="30" align="center" >
						页面显示方式
					</th>
					<th width="80" height="30" align="center" >
						范围
					</th>
					<th width="120" height="30" align="center" >
						操作    
					</th>
				</tr>
				<tr> 
					<td width="80" height="30" style="padding-left:8px;color:blue;" align="left">
							<center><span  id="TC_Info.column_name"><s:property value='TC_Info.column_name'/></span></center>
					</td>
					<td width="80" height="30" style="padding-left:8px;color:blue;" align="left">
							<center><s:property value="TC_Info.data_type"/></center>
					</td>
					<td width="80" height="30" align="center" >
							<center><input type='text' id="TC_Info.CName" name='TC_Info.CName' value="<s:property value='TC_Info.CName'/>"/></center>
					</td>
					<td width="80" height="30" align="center" > 
							<center><span  id="TC_Info.Cview"><s:property value="TC_Info.Cview"/></span></center>
					</td>
					<td width="80" height="30" align="center" > 
							<center><span  id="TC_Info.Cview_value"><s:property value="TC_Info.Cview_value"/></span></center>
					</td>
					<td width="80" height="30" align="center" >
						<center><input type = 'button' value='确认修改' onclick="updateColumn('<s:property value='tablename'/>','<s:property value='column_name'/>',<s:property value="IC.id"/>);" class='textbg6'/></center>
					</td>
				</tr>
			  </table> 
			  </form>
		</div> 
	
	</body>
</HTML>
