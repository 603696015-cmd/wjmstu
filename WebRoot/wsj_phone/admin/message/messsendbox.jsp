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
		<link href="css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="发件列表页" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">发件箱 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="mess_Rec.action?pN=0&pS=20">收件箱 </a>

			</li>
				<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="messsend2groupInit.action"> 发送消息 </a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<script type="text/javascript">
			function page(pN){
				messSend.pN.value=pN;
				messSend.action = "mess_SendBox.action";
				messSend.submit();
			}
		</script>
			<s:form action="mess_Send.action" method="post" theme="simple"
				name="messSend">
				<s:hidden name="pN"></s:hidden>
				<s:hidden name="pS"></s:hidden>
			</s:form>

			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#CFDBE2">
				<tr>
					<td height="40" align="center" bgcolor="#D5EDF9" >
						收件人					</td>
					<td height="40" align="center" bgcolor="#D5EDF9" >
						标题					</td>
					<td height="40" align="center" bgcolor="#D5EDF9" >
						时间					</td>
					<td height="40" align="center" bgcolor="#D5EDF9" >					</td>

				</tr>
				<s:iterator value="messs">
					<tr>
						<td height="50" align="center" bgcolor="#F8FCFE" >
							<s:property value="mess_to.realname" />
					  </td>
						<td height="50" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;"> 
							<a
								href="mess_info.action?mess.mess_id=<s:property value="mess_id" />&deleteType=2">
							<s:property
									value="mess_title" /> </a>
					  </td>
						<td height="50" align="center" bgcolor="#F8FCFE" >
							<s:date name="mess_time" format="yyyy-MM-dd HH:mm:ss" />
					  </td>
						<td height="50" align="center" bgcolor="#F8FCFE" >
							<a onClick="return window.confirm('确定删除？');"
								href="mess_delete.action?mess.mess_id=<s:property value="mess_id" />&deleteType=2&pN=${pN}&pS=${pS }">删除
							</a>						</td>
					</tr>
				</s:iterator>
		  </table>
			<wysLib:page_cisco></wysLib:page_cisco>
		</div>
		<!-- 内容 -->

	
	</body>
</html>