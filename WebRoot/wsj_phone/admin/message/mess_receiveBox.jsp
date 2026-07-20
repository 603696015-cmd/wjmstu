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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="我的邮件列表" /></div>
			</li>
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

			<table width="100%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<td height="20" align="center" >
						标题
					</td>
					<td height="20" align="center" >
						时间
					</td>

				</tr>
				<s:iterator value="messs">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="center"> 
							<a
								href="mess_info.action?mess.mess_id=<s:property value="mess_id" />&deleteType=2"><s:property
									value="mess_title" /> </a>
						</td>
						<td height="20" align="center" >
							<s:date name="mess_time" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
				</s:iterator>
		  </table>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->

	
	</body>
</html>