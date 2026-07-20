<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">制定培训计划</span>
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
			<table cellspacing=1 cellpadding=2 width="70%" align=center
				bgcolor=#ebebeb>
				<tbody>
					<tr>
						<td width="100" align=center bgcolor=#ffffff>
							计划名称
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.name" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							培训内容
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.content" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							参加人
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.participator" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							负责人
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.manager.realname" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							联系人
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.contact" />
						</td>
					</tr>
					<tr>
						<td align=center bgcolor=#ffffff>
							状态
						</td>
						<td align=center bgcolor=#ffffff>
							<s:property value="plan.statusName" />
						</td>
					</tr>
				</tbody>
			</table>
			<table cellspacing=1 cellpadding=2 width="70%" align=center
				bgcolor=#ebebeb>
				<caption>
					审核过程
				</caption>
				<tbody>
					<tr>
						<td align=center bgcolor=#ffffff height=30>
							审核人
						</td>
						<td align=center bgcolor=#ffffff>
							审核时间
						</td>
						<td align=center bgcolor=#ffffff>
							审核状态
						</td>
					</tr>
					<s:iterator value="planVerifys">
						<tr>
							<td align=center bgcolor=#ffffff>
								<s:property value="user.realname" />
							</td>
							<td align=center bgcolor=#ffffff>
								<s:date name="verifydate" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align=center bgcolor=#ffffff>
								<s:property value="statusName" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<form action="assist_plan_verify.action" method="post">
				<table cellspacing=1 cellpadding=2 width="70%" align=center>
					<tr>
						<td width="200px;">
							请选择是否通过审核
						</td>
						<td>
							<input value="2" checked="checked" name="planVerify.status" type="radio" />
							通过
							<input value="3" name="planVerify.status" type="radio" />
							不通过
						</td>
						</tr>
						<tr>
						<td width="200px;">
						</td>
						<td>
						<s:hidden name="plan.id"></s:hidden>
							<input type="submit" value="提交" />
						</td>

					</tr>
				</table>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
