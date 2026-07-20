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
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="练习试题统计详情" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="margin-top: 0px;">
			<div style="text-align:center;">
				<div>试题名称：<s:property value="questionRanking.question.title" /></div>
				<table width="600" align="center" cellpadding="1" cellspacing="1">
					<tr>
						  <th></th> 
						<th width="150" height="30" align="center" >
							选择人次						</th>
						<th width="120" height="30" align="center" >
							比例						</th>
					</tr>
					<s:iterator value="questionRanking.answerInfo">
						<tr>
							<td height="30" align="center" >
								<s:property value="selectOptions_" />
							</td>
							<td height="30" align="center" >
								<s:property value="selectCount" />
							</td>
							<td height="30" align="center" >
								<s:if test="selectCount>0">
									<s:property value="(((selectCount/1.0)/(questionRanking.answerCount/1.0)*100*100)+'').substring(0,(((selectCount/1.0)/(questionRanking.answerCount/1.0)*100*100)+'').indexOf('.'))/100" />%
								</s:if>
								<s:else>0.0%</s:else>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td height="30" align="center" >
								未答
							</td>
						<td height="30" align="center" >
							<s:property value="questionRanking.answerWrong" />
						</td>
						<td height="30" align="center" >
							<s:property value="questionRanking.sqRate" />%
						</td>
					</tr>
					<tr>
						<td height="30" align="center" >
								合计
							</td>
						<td height="30" align="center" >
							<s:property value="questionRanking.answerCount" />
						</td>
						<td height="30" align="center" >
							--
						</td>
					</tr>
			  </table>
			</div>
		</div>
	
	</body>
</HTML>
					