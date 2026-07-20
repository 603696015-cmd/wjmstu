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
				<span style="font-weight: bold;">投票结果</span>
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
			<s:if test="survey.canViewResult==false">您不能查看投票结果！</s:if>
			<s:else>
				投票标题：<s:property value="poll.title" />
				<table width="95%" cellpadding="2" cellspacing="1" bgcolor="#EBEBEB">
					<caption>
						<s:property value="qstatInfo.title" />
					</caption>
					<tr>
						<th align="center" width="400" >
							选项
						</th>
						<th align="center" width="40"  >
							比例
						</th>
						<th align="center" width="40" >
							份数
						</th>
					</tr>
					<s:iterator value="qstatInfo.options" status="optst">
						<tr>
							<td align="center" >
								<s:property />
							</td>
							<td align="center" >
							<s:property value="qstatInfo.answerPer[#optst.index]"/>%
							</td>
							<td align="center" >
								<s:property value="qstatInfo.answerCount[#optst.index]"/>
							</td>
						</tr>
					</s:iterator>
					<tr>
							<td align="right" style="text-align: right;" colspan="3" >
								<b>共：<s:property value="qstatInfo.totalCount"/>人回答</b>
							</td>
						</tr>
				</table>
			</s:else>
			
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
