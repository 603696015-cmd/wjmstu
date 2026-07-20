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
		<TITLE>查看笔记
		</TITLE>
			<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
	<script type="text/javascript" src="js/course.js"></script>
	</HEAD>
	<BODY style="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">查看笔记</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:if test="cnotes.size==0">
			<br>
			<br>无笔记</s:if>
		<s:else>
			
			<table width="80%" align="center" cellpadding="2" cellspacing="1"
				>
				<caption>学员<s:property value="elUser.realname"/>在课程:<s:property value="course.name"/>中的笔记</caption>
				<tr>
					<th align="center" >
						内容：
					</th>
					<th align="center" >
						编写日期
					</th>
					<th align="center" >
						最后修改日期
					</th>
				</tr>
				<s:set name="courseid" value="course.id"></s:set>
				<s:iterator value="cnotes">
					<tr>
						<td >
							<a
								href="javascript:showContent('content_<s:property value="id"/>')"><s:property
									value="shotContent" />
							</a>
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center" >
							<s:date name="modifytime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					<tr style="display: none;" id="content_<s:property value="id"/>">
						<td bgcolor="#FFFFFF" style="font: 12px; padding: 5px;"
							colspan="3">
							<s:property value="content" />
						</td>
					</tr>
				</s:iterator>
			</table>
		</s:else>
	</BODY>
</HTML>
