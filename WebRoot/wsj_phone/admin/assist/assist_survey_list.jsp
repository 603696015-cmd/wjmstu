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
				<span style="font-weight: bold;">我发布的问卷调查</span>
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
			<s:if test="surveys.size==0">没有符合条件问卷调查</s:if>
			<s:else>
				<table width="85%" cellpadding="2" cellspacing="1" bgcolor="#EBEBEB">
					<tr>
						<th align="center" >
							名称
						</th>
						<th align="center" >
							开始时间
						</th>
						<th align="center" >
							结束时间
						</th>
						<th align="center" >
							问卷标题
						</th>
						<th align="center" >
							学员可否查看
						</th>
						<th colspan="4" align="center" >
						</th>
					</tr>
					<s:iterator value="surveys">
						<tr>
							<td align="center" >
								<s:property value="title" />
							</td>
							<td align="center" >
								<s:date name="begintime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center" >
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
							<td align="center" >
								<s:property value="examPaper.title" />
							</td>
							<td align="center" >
								<s:if test="stureadresult">
									可以	
								</s:if>
								<s:else>不可以</s:else>
							</td>
							<td align="center" >
								<a
									href="assist_survey_alterInit.action?survey.id=<s:property value="id" />">修改</a>
								<a
									href="assist_survey_delete.action?survey.id=<s:property value="id" />&pN=<s:property value="pN"/>&pS=10">删除</a>
							</td>
						</tr>
					</s:iterator>
				</table>
				<wysLib:page></wysLib:page>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
