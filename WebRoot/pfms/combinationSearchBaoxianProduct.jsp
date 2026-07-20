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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="保险产品组合搜索工具" /></div>
			</li>
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 40px; margin-left: 40px;">
			<s:form action="insure_online.action" method="post" theme="simple"
				name="department_info" id="department_info">
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				<TABLE cellSpacing=1 cellPadding=1 width="600" align=center
					bgColor=#ebebeb>
					<TBODY>
					<TBODY onmouseover=changeto() onmouseout=changeback()>
						<TR>
							<TD width="130" height=30 align=middle bgColor=#ffffff
								style="COLOR: #cc0099">
								发布者用户名
							</TD>
							<TD align=middle bgColor=#ffffff height=30>
								<INPUT size=24 name="baoxianProduct.fabuzhe">
							</TD>
						</TR>
						<TR>
							<TD width="130" height=30 align=middle bgColor=#ffffff
								style="COLOR: #cc0099">
								发布者所在单位
							</TD>
							<TD align=middle bgColor=#ffffff height=30>
								<INPUT size=24 name="baoxianProduct.fabuzhesuozaidanwei">
							</TD>
						</TR>
						<TR>
							<TD width="130" height=30 align=middle bgColor=#ffffff
								style="COLOR: #cc0099">
								保险产品所属栏目
							</TD>
							<TD align=middle bgColor=#ffffff height=30> 
							<select name="baoxianProduct.ptype.id" id="parentid">
								<wysLib:productTypeSelect selectid="${baoxianProduct.ptype.id}"></wysLib:productTypeSelect>
							</select> 
							</TD>
						</TR>
						<TR>
							<TD width="130" height=30 align=middle bgColor=#ffffff
								style="COLOR: #cc0099">
								保险产品名称
							</TD>
							<TD align=middle bgColor=#ffffff height=30>
								<INPUT size=24 name="baoxianProduct.name">
							</TD>
						</TR>
						<TR>
							<TD width="130" height=30 align=middle bgColor=#ffffff
								style="COLOR: #cc0099">
								发布时间段范围
							</TD>
							<TD align=middle bgColor=#ffffff height=30>
								&nbsp;从
								<INPUT onclick=setday(this) name="starttime">
								&nbsp;&nbsp;到
								<INPUT onclick=setday(this) name="endtime">
							</TD>
							<!--<td height="30" align="center" >
										&#26681;&#32771;&#22330;
									</td>-->
						</TR>
					</TBODY>
					<tr>
						<td width="130" height=30 align=middle bgColor=#ffffff
							style="COLOR: #cc0099" colspan="2">
							<s:submit name="sumbit"
								onclick="document.getElementById('pageNow')=0" value="搜索"></s:submit>
						</td>
					</tr>
				</TABLE>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
