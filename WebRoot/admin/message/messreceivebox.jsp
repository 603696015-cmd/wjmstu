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
		<link rel="stylesheet" type="text/css" href="css/system01.css" />
		<link rel="stylesheet" type="text/css" href="css/manage01.css" />
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="收件列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;"> 收件箱 </span>
			</li>
			<li class="sep">
			</li>
			<!--<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="mess_SendBox.action?pN=0&pS=20"> 发件箱 </a>
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
		<script type="text/javascript">
			function page(pN){
				messRec.pN.value=pN;
				messRec.action = "mess_Rec.action";
				messRec.submit();
			}
		</script>
		<div style="margin-top: 0px; text-align: center;">
			<s:form action="mess_Rec" method="post" theme="simple"
				name="messRec">
				<s:hidden name="pN"></s:hidden>
				<s:hidden name="pS"></s:hidden>
			</s:form>
			<table width="100%" align="center" >
				<tr>
					<th align="center" >
						发件人
					</th>
					<th align="center" >
						标 题
				  </th>
					<th align="center" >
						时间
				  </th>
					<th align="center" >
						已读
				  </th>
					<th align="center" >
						回复状态
				  </th>
					<th width="90" align="center" >
						删除					
				  </th>
					<th width="80" align="center" >
						回复					
					</th>
				</tr>
				<s:iterator value="messs">
					<tr>
						<td height="30" align="center" >
							<s:property value="mess_from.realname" />
						</td>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							<a
								href="mess_info.action?mess.mess_id=<s:property value="mess_id" />&deleteType=1"><s:property
									value="mess_title" /> </a>
						</td>
						<td height="30" align="center" >
							<s:date name="mess_time" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td height="30" align="center" >
							<s:if test="is_read">已读</s:if>
							<s:else>未读</s:else>
						</td>
						<td height="30" align="center" >
							<s:property value="isreplyName" />
						</td>
						<td width="90" height="30" align="center" >
							<a onClick="return window.confirm('确定删除？');"
								href="mess_delete.action?mess.mess_id=<s:property value="mess_id" />&deleteType=1&pN=${pN}&pS=${pS }">删除
							</a>						</td>
						<td width="80" height="30" align="center" >
							<a
								href="mess_revertInit.action?mess.mess_id=<s:property value="mess_id" />">回复</a>						</td>
					</tr>
				</s:iterator>
		  </table>
			<wysLib:page_cisco></wysLib:page_cisco>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
