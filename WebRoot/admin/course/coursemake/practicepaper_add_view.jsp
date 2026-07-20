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
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
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
				<!--	<tr>
					<td width="160" height="30" align="center" >
						试卷说明
					</td>
					<td >
						<label>
							<s:property value="examPaper.description" />
						</label>
					</td>
				</tr>
			 <tr>
																<td width="160" height="30" align="center"
																	>
																	是否限制答题时间段
																</td>
																<td >
																	<div>
																		<s:if test="examPaper.opentimelimit">
																		<label>
																			是
																		</label>
																		<label>
																			<s:date name = "begintime" format="yyyy-MM-dd HH:mm:ss"/>
																		</label>
																		<label>
																			<s:date name = "endtime" format="yyyy-MM-dd HH:mm:ss"/>
																		</label>
																		</s:if>
																		<s:else>
																		<label>
																			否
																		</label>
																		</s:else>
																	</div>
																</td>
															</tr> -->
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
						是否可跳过
					</td>
					<td >
						<label>
							<input type="radio" value="1" name="ppsa">是
							<input type="radio" value="0" name="ppsa" checked="checked">否
						</label>
					</td>
				</tr>
			</table>
			<input type="button" onclick="practiceAdd();" value="添加到该课程">
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
