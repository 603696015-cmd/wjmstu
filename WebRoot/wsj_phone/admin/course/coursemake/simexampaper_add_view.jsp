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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<!-- 内容 -->
		<div style="">
			<span style="color: #ff0000; text-align: center"><s:property
					value="elmessage" />
			</span>
			<table width="90%" align="center" cellpadding="2" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
					<td width="160" height="30" align="center" >
						试卷标题
					</td>
					<td >
						<label>
							<s:property value="examPaper.title" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						所属试卷库
					</td>
					<td >
						<label>
							<s:property value="examPaper.epl.name" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						试卷时长（分钟）
					</td>
					<td >
						<label>
							<s:property value="examPaper.during" />
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						试题总分
					</td>
					<td >
						<s:property value="examPaper.ep_tscore" />
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						出题方式
					</td>
					<td >
						<label>
							<s:if test="examPaper.random">
																		随机
																	</s:if>
							<s:else>
																		手工
																	</s:else>
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						开始时间
					</td>
					<td >
						<label><input id="begintime" type="text" name="begintime" Width="200px" onclick="setday(this)">
						</label>
					</td>
				</tr>
				<tr>
					<td width="160" height="30" align="center" >
						结束时间
					</td>
					<td >
						<label><input id="endtime" type="text" name="endtime" Width="200px" onclick="setday(this)">
						</label>
					</td>
				</tr>
			</table>
			<input type="button" onclick="simpaperAdd();" value="添加到该课程">
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
