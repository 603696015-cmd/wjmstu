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
		<TITLE>考试概况</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="查看概况" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">批次概况</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_eroom_batch_view.action?erbatch.id=<s:property value="erbatch.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">批次详情</a>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_eroom_batch_eval.action?erbatch.id=<s:property value="erbatch.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">批次部门评比</a>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="80%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						批次人数
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="erbatch.usersize" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						全部人员平均分的平均分
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="erbatch.avgscore" />
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						及格人数
					</td>
					<td height="30" colspan="2" align="center" >
						<s:property value="erbatch.passsize" />
					</td>
				</tr>
				<tr>
					<td height="30" width="150px;" align="center" >
						90分以上
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass9_" />

					</td>
					<td height="30" align="center" >
						90分以上
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						80-90分
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass8_9" />

					</td>
					<td height="30" align="center" >
						80-90分
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						70-80分
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass7_8" />

					</td>
					<td height="30" align="center" >
						70-80分
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						60-70分
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass6_7" />

					</td>
					<td height="30" align="center" >
						60-70分
					</td>
				</tr>
				<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						60以下
					</td>
					<td height="30" align="center" >
						<s:property value="erbatch.pass_6" />
					</td>
					<td height="30" align="center" >
						60以下
					</td>
				</tr>
		  </table>
			<table cellpadding="1" cellspacing="1" width="80%">
				<tr>
					<th height="30" align="center" >
						考场名称
					</th>
					<!--<th height="30" align="center" >
						创建者					</th>
					<th height="30" align="center" >
						课程
					</th>-->
					<th width="70" height="30" align="center" >
						考试人数					</th>
					<th width="100" height="30" align="center" >
						考场类别					</th>
					<!--
					<th height="30" align="center" >
						考试概况
					</th>
					--><th width="80" height="30" align="center" >
						考试详情
					</th>
					<th width="80" height="30" align="center" >
						部门比较					</th>
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="examRooms">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="title" />
						</td>
						<!--<td height="30" align="center" >
							<s:property value=" creater.realname" />
						</td>
						<td height="30" align="center" >
							<s:property value="course.name" />
						</td>-->
						<td width="70" height="30" align="center" >
							<s:property value="userSize" />
					  </td>
						<td width="100" height="30" align="center" >
							<s:property value="eroomLib.name" />
</td><!--
						<td height="30" align="center" >
							<a
								href="quiz_stat_view.action?examRoom.id=<s:property value="id"/>&department.id=1&sub_department=1">查看概况</a>
						</td>
						--><td width="80" height="30" align="center" >
							<a
								href="quiz_detail_view.action?examRoom.id=<s:property value="id"/>&department.id=1&sub_department=1" class=textbg6>查看详情</a>
						</td>
						<td width="80" height="30" align="center" >
							<a
								href="quiz_stat_eval.action?examRoom.id=<s:property value="id"/>&department.id=1&sub_department=1" class=textbg4>查看</a>						</td>
					</tr>
				</s:iterator></tbody>
		  </table>
		  <a href="stat_eroom_batch_gk.action?exprot=true&erbatch.id=<s:property value="erbatch.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">导出列表</a>
		  <br/>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>

