<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>备注列表</TITLE> 
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)> 
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="备注列表" /></div>
			</li> 
		</ul>  
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
			<table width="80%" align="center" cellpadding="2" cellspacing="1"
				>
				<caption>
					<s:if test="simpleRemack.type==1">可申请考场不通过原因</s:if>
					<s:if test="simpleRemack.type==2">可申请培训班不通过原因</s:if>
					备注
				</caption>
				<tr>
					<th align="center" >
						标题
					</th>
					<th align="center" >
						审核者
					</th>
					<th align="center" >
						审核者角色
					</th>
					<th align="center" >
						审核日期 
					</th> 
					<th align="center" >
						电话
					</th>
					<th align="center" > 
					</th>
				</tr> 
				<s:iterator value="simpleRemacks">
					<tr>
						<td height="30" style="padding-left:8px;">
							<a href="javascript:showContent('crelist_<s:property value="id"/>')">
							<s:property value="title" /></a>
						</td>
						<td align="center" >
							<s:property value="creater.realname" />
						</td>
						<td align="center" >
							<s:property value="creater.role.name" />
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center" >
							<s:property value="phone" />
						</td>
						<td align="center" >
							<a
								href="javascript:showContent('crelist_<s:property value="id"/>')">查看</a> 
						</td>
					</tr>
					<tr style="display: none;" id="crelist_<s:property value="id"/>">
						<td style="font: 12px; padding: 5px;"
							colspan="6">
							<s:property value="content" />
						</td>
					</tr>
				</s:iterator>
				<%-- 
				<tr>
					<td bgcolor="#FFFFFF" style="font: 12px; padding: 5px;"	colspan="6">
						<a href="CRE_note_addInit.action?examRoom.id=<s:property value="examRoom.id"/>&course.id=
						<s:property value="course.id"/>&elclass.id=<s:property value="elclass.id"/>&Return=<s:property value="Return"/>">填写备注...</a>
					</td>
				</tr>
				 --%>
			</table> 
			<s:if test="Return=='dia'">
			<div style="text-align: center;">
				<input type="button" class="textbg4" onClick="window.close();" value="关闭"/>
			</div>
			</s:if>
	</BODY>
</HTML>
