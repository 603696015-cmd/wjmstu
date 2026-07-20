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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>友情链接列表</title>
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
				fl.action="flink_list.action";
				fl.submit();
			}
			function upSort(id)
			{
				document.getElementById("flid").value=id;
				fl.action="flink_upSort.action";
				fl.submit();
			}
			function deleteF(id)
			{
				document.getElementById("flid").value=id;
				fl.action="flink_delete.action";
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="链接列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">友情链接管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="flink_addInit.action">友情链接添加</a>
			</li>-->
		</ul>
		<div class="operateLine"></div>
		<form action="flink_list.action" method="post" name="fl">
			<s:hidden name="pageNow" id = "pageNow"></s:hidden>
			<s:hidden name="pageSize"></s:hidden>
			<s:hidden name="flink.id" id="flid"></s:hidden>
		</form>
		<table width="60%" align="center" cellpadding="2" cellspacing="2"
			>
			<tr>
				<td height="20" align="center" >
					链接名
				</td>
				<td height="20" align="center" >
					链接描述
				</td>
				<td height="20" align="center" >
					链接地址
				</td>
				<td height="20" align="center" >
					排序号
				</td>
				<td height="20" colspan="4" align="center" >
				</td>
			</tr>
			<s:iterator value="flinks" status="memSt">
				<tr>
					<td height="20" align="center" >
						<script>document.writeln("<s:property value="flname" />".substring(0,20));</script>
					</td>
					<td height="20" align="center" >
						<s:property value="fldesc" />
					</td>
					<td height="20" align="center" >
						<s:property value="fhref" />
					</td>
					<td height="20" align="center" >
						<s:property value="sort" />
					</td>
					<td height="20" align="center" >
						<a
							href="flink_view.action?flink.id=<s:property value="id"/>">查看</a>
					</td>
					<td height="20" align="center" >
						<a
							href="flink_alterInit.action?flink.id=<s:property value="id"/>">修改</a>
					</td>
					<td height="20" align="center" >
						<a
							href="javascript:deleteF(<s:property value="id"/>)">删除</a>
					</td>
					<td height="20" align="center" >
						<a
							href="javascript:upSort(<s:property value="id"/>)">上移</a>
					</td>
				</tr>
			</s:iterator>
		</table>
		<div style="text-align: center;">
		<SCRIPT type="text/javascript">
						function page(i){
							document.location="flink_list.action?pN="+i+"&pS="+<s:property value="pS"/>;
						}
					</SCRIPT>
		<wysLib:page></wysLib:page> </div>
	
	</body>
</html>
