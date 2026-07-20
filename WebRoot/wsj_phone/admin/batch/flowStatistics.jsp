<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
	<HEAD>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	<script type="text/javascript" src="js/cexampaper.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="流量统计" /></div>
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
		
				<table width="98%" align="left" cellpadding="1" cellspacing="1" >
					<tr>
						<td align="center" >
							首页访问人次：
						</td>
						<td align="center" >
							<!--<input type="text" value=""/>
						-->
							<s:property value="flow.homevisit"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							用户总数：
						</td>
						<td align="center" >
							<!--<input type="text" value=""/>
						-->
						<s:property value="flow.totalusers"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							当前在线用户数：
						</td>
						<td align="center" >
							<!--<input type="text" value=""/>
						-->
						<s:property value="flow.currentonline"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							课程总量：
						</td>
						<td align="center" >
							<!--<input type="text" value=""/>
						-->
						<s:property value="flow.totalcourse"/>
						</td>
					</tr>
					<tr>
						<td align="center" >
							知识总量：
						</td>
						<td align="center" >
							<!--<input type="text" value=""/>
						-->
						<s:property value="flow.totalknowledge"/>
						</td>
						
					</tr>
				</table>
	
	
	</body>
</HTML>
