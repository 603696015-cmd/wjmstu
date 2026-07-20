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
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	padding-left: 8px
}

tr {
	background-color: expression((   this .   sectionRowIndex %   2 ==   0)
		? 
		 "#ffffff" :   "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="组合搜索工具" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程组合搜索</span>
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
			<s:form action="combinationSearchCourselist" method="post"
				theme="simple" name="department_info" id="department_info">
				<TABLE cellSpacing=1 cellPadding=1 width="100%" align=center
					 bgcolor="#D1E4F5">
					<TBODY>
					<TBODY onmouseover=changeto() onmouseout=changeback()>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								发布者用户名：							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
								<INPUT size=24 name="course.owner.username">
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								发布者姓名：							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
								<INPUT size=24 name="course.owner.realname">
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								课程类别：							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
							  <select name="course.ctype.id" id="parentid" style="width:150px">
									<wysLib:ct_select />
								</select>
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								课程状态：							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
							  <select name="course.status" style="width:150px">
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
								</select>
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								课程名称：							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
								<INPUT size=24 name="course.name">
						  </TD>
						</TR>
						<TR>
							<TD width="130" height=30 align="right" bgColor=#F8FCFE>
								发布时间段范围：							</TD>
							<TD align="left" bgColor=#F8FCFE height=30>
								&nbsp;从
							  <INPUT onclick=setday(this) name="course.begintime">
								&nbsp;&nbsp;到
								<INPUT onclick=setday(this) name="course.endtime">
						  </TD>
						</TR>
						<tr>
							<td height="40px" colspan="2" align="center" bgcolor="#F8FCFE">
								<s:submit name="submit" value="搜索" cssClass="textbg4"></s:submit>
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
