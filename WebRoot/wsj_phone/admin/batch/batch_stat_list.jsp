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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训批次统计列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">培训批次统计列表</span>
			</li>-->
			
		</ul>  
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="batch_stat_list" name="myclist" theme="simple">
			<s:hidden name="pN" id = "pageNow"></s:hidden>
			<s:hidden name="pS"></s:hidden>
			培训批次名称：
			<s:textfield name="batch.name"></s:textfield>
			<s:submit value="搜索"></s:submit>
		
		</s:form>
		<table width="95%">
			<tr><td>
			<s:if test="batchList.size==0">没有符合条件的培训批次</s:if>
			<s:else>
				<table width="98%" align="left" cellpadding="1" cellspacing="1" >
					<tr>
						<th align="center" bgcolor="#FFFFFF" width="30%">
							批次名称
						</th>
						<th align="center" bgcolor="#FFFFFF" width="50%">
							批次介绍
						</th>
						<th align="center" bgcolor="#FFFFFF" width="20%">&nbsp;
							
					  </th>
					</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="batchList">
						<tr>
							<td align="center" >
								<s:property value="name"/>
							</td>
							<td align="center" >
								<s:property value="description"/>
							</td>
							<td align="center" >
								<a href="batch_class_stat.action?batchId=${id}" class=textbg>培训班详情</a>
							</td>
						</tr>
					</s:iterator></tbody>
				</table>
			</s:else>
			</td></tr>
		</table>
		<wysLib:page></wysLib:page>
	
	</body>
</HTML>
