<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">

		<title>课件服务器列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		
		<script type="text/javascript">
			function page(i)
			{
				document.getElementById("pageNow").value=i;
				fl.action="course_server_list.action";
				fl.submit();
			}
			function deleteF(id)
			{
				document.getElementById("flid").value=id;
				fl.action="course_server_delete.action";
				fl.submit();
			}
		</script>
	</HEAD>

	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="选择课件服务器" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">选择课件服务器管理</span>
			</li>-->
		</ul>
		<div class="operateLine"></div>
		<form action="course_server_list.action" method="post" name="fl">
			<s:hidden name="pageNow" id = "pageNow"></s:hidden>
			<s:hidden name="pageSize"></s:hidden>
			<s:hidden name="cserver.id" id="flid"></s:hidden>
		</form>
		<table width="90%" align="center" cellpadding="2" cellspacing="2"
			>
			<tr>
				<td height="20" align="center" >
					名称
				</td>
				<td height="20" align="center" >
					描述
				</td>
				<td height="20" align="center" >
					地址
				</td>
				<td height="20" align="center" >
					选择
				</td>
			</tr>
			<s:iterator value="cservers" status="memSt">
				<tr>
					<td height="20" align="center" >
						<script>document.writeln("<s:property value="name" />".substring(0,20));</script>
					</td>
					<td height="20" align="center" >
						<s:property value="description" />
					</td>
					<td height="20" align="center" >
						<s:property value="url" />
					</td>
					<td height="20" align="center" >
					<input type="radio" value="<s:property value="id"/>" name="cs_id">
					</td>
				</tr>
			</s:iterator>
		</table>
		
		<div style="text-align: center;">
		<a onClick="javascript:queding()">确定</a>
		<SCRIPT type="text/javascript">
		document.getElementsByName("cs_id")[0].checked='checked';
		function queding(){
				var cks= document.getElementsByName("cs_id");
				var csid =0;
				for(var i = 0 ; i < cks.length; i++){
					if(cks[i].checked){
						csid=cks[i].value;
					
					}
				}
				window.returnValue = csid;
				window.close();
			}
						function page(i){
							document.location="course_server_list.action?pN="+i+"&pS="+<s:property value="pS"/>;
						}
					</SCRIPT>
		</div>
	
	</body>
</html>
