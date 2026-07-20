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
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression((     this .     sectionRowIndex %     2 ==     0)
		?  
		  "#ffffff" :     "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="培训证书到期情况查询汇总" />
				</div>
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
			<table width="100%" align="center" cellpadding="0" cellspacing="1"
				bgcolor="#EBEBEB">
				<caption style="font-weight: normal;">
						培训证书到期情况查询
				</caption>
				
				<tr>
					<th height="30"  align="center" colspan="2">
						半年到期
					</th>
					<th height="30"  align="center" colspan="2">
						三个月到期
					</th>
					<th height="30"  align="center" colspan="2">
						一个月到期
					</th>
					<th height="30" align="center" colspan="2">
						半个月到期
					</th>
					<th height="30" align="center" colspan="2">
						一周到期
					</th>
					<th height="30" align="center" colspan="2">
						证书过期
					</th>
					
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
						<tr>
    <td  valign="middle" height="30" align="center" bgcolor="#FFFFFF"><s:property value="isSixMonths"/> 人</td>
    <td  valign="middle" align="center"  bgcolor="#FFFFFF"><a href="isSixMonths.action" class="textbg4">详情</a></td>
    <td  valign="middle" align="center" bgcolor="#FFFFFF"><s:property value="isThreeMonths"/> 人</td>
    <td valign="middle" align="center" bgcolor="#FFFFFF"><a href="isThreeMonths.action" class="textbg4">详情</a></td>
    <td valign="middle" align="center" align="center" bgcolor="#FFFFFF"><s:property value="isOneMonths"/> 人</td>
    <td valign="middle" align="center" bgcolor="#FFFFFF"><a href="isOneMonths.action" class="textbg4">详情</a></td>
    <td  valign="middle" align="center" bgcolor="#FFFFFF"><s:property value="isHalfMonths"/> 人</td>
    <td  valign="middle" align="center" bgcolor="#FFFFFF"><a href="isHalfMonths.action" class="textbg4">详情</a></td>
    <td  valign="middle" align="center" bgcolor="#FFFFFF"><s:property value="isOneWeek"/> 人</td>
    <td  valign="middle" align="center" bgcolor="#FFFFFF"><a href="isOneWeek.action" class="textbg4">详情</a></td>
     <td  valign="middle" align="center" bgcolor="#FFFFFF"><s:property value="isValid"/> 人</td>
    <td  valign="middle" align="center" bgcolor="#FFFFFF"><a href="isValid.action" class="textbg4">详情</a></td>
  </tr>
				</tbody>
			</table>
			
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
