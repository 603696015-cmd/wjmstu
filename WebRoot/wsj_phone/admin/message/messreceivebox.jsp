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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system01.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage01.css" />
        <link href="wsj_phone/css/listlable.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
	</HEAD>
	<BODY>
	
<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>-->
		
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
			<table width="320" border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5">
				<tr>
					<td width="40" align="center" bgcolor="#F8FCFE" >
						发件人
					</td>
					<td align="center" bgcolor="#F8FCFE" >
						标 题
			    </td>
					<td width="40" align="center" bgcolor="#F8FCFE" >
						删除					
			    </td>
					<td width="40" align="center" bgcolor="#F8FCFE" >
						回复					
					</td>
				</tr>
				<s:iterator value="messs">
					<tr>
						<td height="30" align="center" bgcolor="#F8FCFE" >
							<s:property value="mess_from.realname" />
						</td>
						<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;">
							<a
								href="mess_info.action?mess.mess_id=<s:property value="mess_id" />&deleteType=1"><s:property
									value="mess_title" /> </a>
						</td>
						<td height="30" align="center" bgcolor="#F8FCFE" >
							<a onClick="return window.confirm('确定删除？');"
								href="mess_delete.action?mess.mess_id=<s:property value="mess_id" />&deleteType=1&pN=${pN}&pS=${pS }" >删除
							</a>						</td>
						<td height="30" align="center" bgcolor="#F8FCFE" >
							<a
								href="mess_revertInit.action?mess.mess_id=<s:property value="mess_id" />">回复</a>						</td>
					</tr>
				</s:iterator>
		  </table>
			<div style="width:320px; text-align: center; margin-top: 10px;">
			  <wysLib:page_cisco></wysLib:page_cisco>
		  <!-- 内容 -->	</div>
		</div>
	
	</body>
</HTML>
