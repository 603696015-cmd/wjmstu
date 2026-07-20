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
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="线下培训班查看页" /></div>
			</li>
		</ul>
		<!-- 内容 --> 
		<table width="100%">
			<tr>
			
			
			<td valign="top" align="left"> 
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th width="11%" height="30" align="center" >学员姓名</th>
					<th width="11%" align="center" >是否获证</th>
					<th width="11%" align="center" >成绩</th>
					<th width="11%" align="center" >学分数</th>
					<th width="11%" align="center" >获得学分数</th>
					<th width="45%" height="30" align="center" >相关附件<s:property value='lineTrainingCourse.assign.is_get_certificate'/></th>
				</tr>
				<tr>
					<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						<s:property value="lineTrainingCourse.assign.elUser.realname" />
					</td>
					<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
						<s:if test="lineTrainingCourse.assign.is_get_certificate == 0">未获证</s:if>
						<s:else>已获证</s:else>
					</td>
					<td height="30" align="center" >
						<s:property value="lineTrainingCourse.assign.score" />
					</td>
					<td align="center" >
						<s:property value="lineTrainingCourse.credit" />
					</td>
					<td align="center" >
						<s:property value="lineTrainingCourse.credit" />
					</td>
					<td  height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<form action="accessory_update.action" enctype="multipart/form-data"	method="post"> 
								<input type="file" name="st" > 
								<input type="hidden" name="assign.id" value="<s:property value="lineTrainingCourse.assign.id"/>"> 
								<input type="hidden" name="assign.line_training_course_id" value="<s:property value="lineTrainingCourse.assign.line_training_course_id"/>"> 
								<input type="hidden" name="assign.userId" value="<s:property value="lineTrainingCourse.assign.userId"/>"> 
								<s:if test="lineTrainingCourse.assign.accessory.length() > 0">
									<input type="submit" value="替换">
						 			<a href="downloadInit.action?fileName=<s:property value="lineTrainingCourse.assign.id"/>_accessory_<s:property value="lineTrainingCourse.assign.line_training_course_id"/>_<s:property value="lineTrainingCourse.assign.userId"/>.<s:property value="lineTrainingCourse.assign.accessory"/>" target="_blank" style="color:red">下载</a> 
								</s:if>
								<s:else>
									<input type="submit" value="上传"> 
								</s:else>
							</form>
					</td>
				</tr>
				
		  </table>
		  </td></tr></table> 
		<!-- 内容 -->
	</BODY>
</HTML>
				