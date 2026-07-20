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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="批次列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">考场批次列表 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="eroom_batch_addInit.action">考场批次添加</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<div>


				<table width="80%" align="center" cellpadding="1" cellspacing="1"
					>
					<tr>
						<th height="30" align="center" >
							考场批次名称
						</th>
						<th height="30" align="center" >
							类别
						</th>
						<th width="120" height="30" align="center" >&nbsp;						</th>
					</tr>
					<s:iterator value="erbatchs">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="title" />
							</td>
							<td height="30" align="center" >
						<s:property value="batchlib.name" />
							</td>
							<td width="120" height="30" align="center" >
								<a
									href="eroom_batch_alterInit.action?erbatch.id=<s:property value="id"/>" class="textbg4">修 改</a>
								<a onClick="return window.confirm('确定删除？')"
									href="eroom_batch_delete.action?erbatch.id=<s:property value="id"/>" class="textbg4">删 除</a>							</td>
						</tr>
					</s:iterator>
			  </table>
				<DIV style="text-align: center">
					<wysLib:page></wysLib:page>
					<br>
					<a href="eroom_batch_addInit.action" class="textbg">考场批次添加</a>
				</DIV>
				<script>
					function page(i){
						document.getElementById("pageNow").value=i;
						erform.submit();
					}
				</script>
			</div>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
