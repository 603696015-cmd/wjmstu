<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<TITLE>中国食品安全培训网--管理端</TITLE>
<link rel="stylesheet" type="text/css" href="css/system.css" />
<link rel="stylesheet" type="text/css" href="css/manage.css" />
<script type="text/javascript" src="js/menu.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="培训班通过率排行榜" /></div>
			</li>
</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>

<table width="100%">
	<tr>
		<td valign="top">
				<table width="100%" height="100%" align="center" cellpadding="1"
					cellspacing="1" >
					<tr>
						<th align="center" >培训班名称</th>
						<th align="center" >创建时间</th>
						<th align="center" >学员人数</th>
						<th align="center" >通过人数</th>
						<th align="center" >通过率</th>
						<th align="center" >&nbsp;</th>
					</tr>
			<s:if test="elclassList.size==0">
				</table>
			</s:if>
			<s:else><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
					<s:iterator value="elclassList">
						<tr>
							<td align="center" bgcolor="#FFFFFF" style="color:#CC0099;"><s:property value="name" /></td>
							<td align="center" ><s:date name="createtime" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td align="center" ><s:property value="userCount" /></td>
							<td align="center" ><s:property value="userPassedCount" /></td>
							<td align="center" ><s:property value="passper" /></td>
							<td align="center" >
								<a href="class_student.action?elUser.id=0&elclass.id=<s:property value="id" />&elClassId=<s:property value="id" />&elClassName=<s:property value="name"/>" class=textbg4>查 看</a>
							</td>
						</tr>
					</s:iterator></tbody>
				</table>
			</s:else>
		</td>
	</tr>
</table>

	</body>
</HTML>
