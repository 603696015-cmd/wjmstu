<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>五矿发展员工职业发展系统--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看详情" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">流量统计</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<table width="98%" align="left" cellpadding="1" cellspacing="1">
			<tr>
				<td align="center" >
					课程总数：
				</td>
				<td align="center" >
					<s:property value="resources.ccount" />
				</td>
			</tr>
			<tr>
				<td align="center" >
					已审核课程总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.ccount_status" />
				</td>
			</tr>
			<tr>
				<td align="center" >
					培训班总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.elcount" />
				</td>
			</tr>
			<tr>
				<td align="center" >
					已审核培训班总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.elcount_status" />
				</td>
			</tr>
			<tr>
				<td align="center" >
					试题总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.qcount" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					试卷总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.ecount" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					考场总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.ercount" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					已审核考场总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.ercount_status" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					资源总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.kcount" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					已审核资源总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.kcount_status" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					新闻总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.ncount" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					已审核新闻总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.ncount_status" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					帖子总数：
				</td>
				<td align="center" >
					<s:property value="resources.fcount" />
				</td>

			</tr>
			<tr>
				<td align="center" >
					已审核帖子总数：
				</td>
				<td align="center" >
					<!--<input type="text" value=""/>
						-->
					<s:property value="resources.fcount_status" />
				</td>

			</tr>
		</table>

	</body>
</HTML>
