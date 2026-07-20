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
		<TITLE>单位代码查询</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<div style="margin-top: 0px; text-align: center;">
			<form action="depDisplayByName.action" method="post" name="ddb">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<div>
					单位名称：<s:textfield name="department.name" />
					<input onClick="initPN();" type="button" value="搜索" />（说明：请输入单位或部门名称的关键字）
				</div>
			</form>	

			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				>
				<tr>
					<th width="230" height="30" align="center" >
						单位名称
					</th>
					<th width="230" height="30" align="center" >
						单位代码
					</th>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="departments">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
									<s:property value="name"/>
							</td>
							<td height="30" align="center" >
									<s:property value="bh"/>
							</td>
						</tr>
					</s:iterator>
				</tbody>
		  </table>
		  <script>
				function page(i){
					document.getElementById("pageNow").value=i;
					ddb.submit();
				}
				function initPN(){
					document.getElementById("pageNow").value=0;
					ddb.submit();
				}
			</script>
			<wysLib:page></wysLib:page> 
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>