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
		<TITLE>课程组合搜索</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	padding-right: 8px;
	padding-left: 8px;
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
				<span style="font-weight: bold;">考场组合搜索</span>
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
			<s:form action="combinationSearchExamroom" method="post"
				theme="simple" name="department_info" id="department_info">

				<TABLE cellSpacing=1 cellPadding=1 width="100%" align=center
					 bgcolor="#D1E4F5">
					<TBODY>
					<TBODY onmouseover=changeto() onmouseout=changeback()>

						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								创建者：
							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
								<INPUT size=24 name="examRoom.creater.realname">
							</TD>
						</TR>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								考场标题：
							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
								<INPUT size=24 name="examRoom.title">
							</TD>
						</TR>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								考场状态：
							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
							  <select name="examRoom.valid">
									<option value="-1" selected="selected">
										全部
									</option>
									<option value="0">
										制作中
									</option>
									<option value="1">
										申请等待中
									</option>
									<option value="2">
										待修改
									</option>
									<option value="3">
										审核等待中
									</option>
									<option value="4">
										审核不通过
									</option>
									<option value="5">
										已开通
									</option>
									<option value="6">
										修改等待中
									</option>
									<option value="7">
										修改中
									</option>
									<option value="8">
										删除等待中
									</option>
									<option value="9">
										已删除
									</option>
									<option value="10">
										关闭
									</option>
								</select>
							</TD>
						</TR>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								发布时间段范围：
							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
								&nbsp;从
							  <INPUT onclick=setday(this) name="examRoom.begintime">
								&nbsp;&nbsp;到
								<INPUT onclick=setday(this) name="examRoom.endtime">
							</TD>
						</TR>
						<tr>
							<td colspan="2" align="center" bgcolor="#F8FCFE">
								<s:submit name="submit" cssClass="textbg4" value="搜索"></s:submit>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="reset" value="重置" class="textbg4" />
							</td>
						</tr>
					</TBODY>
				</TABLE>
			</s:form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
