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
		<TITLE>新闻组合搜索</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;padding-left: 8px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="新闻组合搜索工具" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">新闻组合搜索</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; ">
			<s:form action="combinationSearchNews" method="post" theme="simple"
				name="department_info" id="department_info">
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				<TABLE cellSpacing=1 cellPadding=1 width="100%" align=center
					bgColor=#ebebeb>
					<TBODY>
					<TBODY onmouseover=changeto() onmouseout=changeback()>
						<TR>
							<TD width="130" height=40 align="right" bgColor=#F8FCFE>
								发布者用户名：							</TD>
							<TD align="left" bgColor=#ffffff height=30>
								<INPUT size=24 name="news.owner.username">
							</TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" bgColor=#F8FCFE>
								发布者姓名：							</TD>
							<TD align="left" bgColor=#ffffff height=30>
								<INPUT size=24 name="news.owner.realname">
							</TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" bgColor=#F8FCFE>
								所属类别：							</TD>
							<TD align="left" bgColor=#ffffff height=30>
								<select name="news.ntype.id" id="parentid">
									<wysLib:newsTypeSelect></wysLib:newsTypeSelect>
								</select>
							</TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" bgColor=#F8FCFE>
								所属类型：							</TD>
							<TD align="left" bgColor=#ffffff height=30>
								<s:select list="lnss" listKey="id" listValue="name" headerKey="0" headerValue="—全部—" name="news.nstyle.id"></s:select>
							</TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" bgColor=#F8FCFE>
								新闻名称：							</TD>
						  <TD align=cent bgColor=#ffffff height=30>
								<INPUT size=24 name="news.title">
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=40 align="right" bgColor=#F8FCFE>
								发布时间段范围：							</TD>
							<TD align="left" bgColor=#ffffff height=30>
								&nbsp;从
								<INPUT onclick=setday(this) name="news.begintime">
								&nbsp;&nbsp;到
								<INPUT onclick=setday(this) name="news.endtime">
							</TD>
							<!--<td height="30" align="center" >
										&#26681;&#32771;&#22330;
									</td>-->
						</TR>
					</TBODY>
					<tr>
						<td height=50 align="center" bgColor=#ffffff colspan="2">
							<s:submit name="sumbit"
								onclick="document.getElementById('pageNow')=0"
								cssClass="textbg4" value="搜索"></s:submit>
							<input type="reset" value="重置" class="textbg4" />
						</td>
					</tr>
				</TABLE>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
