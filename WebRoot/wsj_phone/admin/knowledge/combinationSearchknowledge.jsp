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
		<TITLE>知识组合搜索</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="组合搜索工具" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">知识组合搜索</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<form action="combinationSearch.action" name="klform" method="post">
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#CFDBE2">
					<tr>
						<td height=40 align="right" bgColor="#F8FCFE">
							发布者用户名：						</td>
						<td align="left" bgColor="#F8FCFE" height="40">
							<input size="24" name="knowledge.owner.username" />
					  </td>
					</tr>
					<tr>
						<td height=40 align="right" bgColor=#F8FCFE>
							发布者姓名：						</td>
						<td align="left" bgColor=#F8FCFE height=40>
							<input size="24" name="knowledge.owner.realname" />
					  </td>
					</tr>
					<tr>
						<td height=40 align="right" bgColor=#F8FCFE>
							知识栏目：						</td>
						<td align="left" bgcolor="#F8FCFE" height="40">
							<select name="kltype.id" id="parentid" style="width:150px">
								<wysLib:kltype_select />
							</select>
					  </td>
					</tr>
					<tr>
						<td width="130" height=40 align="right" bgColor=#F8FCFE>
							知识名称：						</td>
						<td align="left" bgColor=#F8FCFE height=40>
							<input size="24" name="knowledge.title">
					  </td>
					</tr>
					<%-- 
					<tr>
						<td height=30 align=middle bgColor=#ffffff style="COLOR: #cc0099">
							发布时间段范围
						</td>
						<td align=middle bgColor=#ffffff height=30>
							&nbsp;从
							<input onClick="setday(this)" name="knowledge.begintime">
							&nbsp;&nbsp;到
							<INPUT onClick="setday(this)" name="knowledge.endtime">
						</td>
					</tr>
					 --%>
					<tr>
						<td height="40" colspan="2" align="center" bgcolor="#F8FCFE">
							<input type="submit" class="textbg5"
								onclick="document.getElementById('pageNow')=0" value="搜索" />
						<input type="reset" class="textbg5" value="重置" />
					  </td>
					</tr>
			  </table>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
