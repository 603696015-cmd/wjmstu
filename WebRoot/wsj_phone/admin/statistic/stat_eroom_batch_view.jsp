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
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="用户列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">批次详情</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="stat_eroom_batch_gk.action?erbatch.id=<s:property value="erbatch.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">批次概况</a>
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
			<a
				href="stat_eroom_batch_view.action?exprot=true&erbatch.id=<s:property value="erbatch.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>">导出列表</a>
			<table width="100%" align="center" cellpadding="1" cellspacing="1"
				bgcolor="#EBEBEB">
				<caption>
					考场批次:【
					<s:property value="erbatch.title" />
					】详情
				</caption>
				<tr>
					<th width="40" height="30" align="center" >
						排名
					</th>
					<th width="80" height="30" align="center" >
						姓名
					</th>
					<th width="160" height="30" align="center" >
						用户名
					</th>
					<th width="70" height="30" align="center" >
						考场
					</th>
					<th width="70" height="30" align="center" >
						总分成绩
					</th>
					<!--<th width="100" height="30" align="center" >
						试卷数量					</th>
					<th width="60" height="30" align="center" >
						是否及格
					</th>-->
					<s:iterator value="myExamPapers">
						<td height="30" align="center" style="color: red;"
							>
							<s:property value="examPaper.title" />
						</td>
					</s:iterator>
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="myrooms" status="ermst">
						<tr>
							<td width="40" height="30" align="center" >
								<s:property value="#ermst.index+1" />
							</td>
							<td width="80" height="30" align="center" bgcolor="#FFFFFF"
								style="color: #CC0099;">
								<s:property value="tester.realname" />
							</td>
							<td width="160" height="30" align="center" >
								<s:property value="tester.username" />
							</td>
							<td width="40" height="30" align="center" >
								<s:property value="examroom.title" />
							</td>
							<td width="40" height="30" align="center" >
								<s:property value="myScore" />
							</td>
							<!--<td width="100" height="30" align="center" >
							<s:property value="epsize" />
							个
							<a
								href="quiz_paper_detail_view.action?elUser.id=<s:property value="tester.id"/>&examRoom.id=<s:property value="examRoom.id"/>" class=textbg4>详情</a>
					  </td>
							<td width="60" align="center" >
								<s:if test="myScore>=60">及格</s:if>
								<s:else>不及格</s:else>
							</td>-->
							<s:set name="userid" value="tester.id"></s:set>
							<s:iterator value="myExamPapers">
								<td height="30" align="center" style="color: red;"
									>
									<s:if test="id==0">未分配</s:if>
									<s:else>
										<s:property value="statusName" />/<s:property value="myScore" />/<a
											href="quizpaper_view.action?elUser.id=<s:property value="#userid"/>&myExamPaper.id=<s:property value="id"/>"
											target=_blank class=textbg4>查 看</a>/<a
											href="exampaperread.action?myExamPaper.id=<s:property value="id"/>"
											target=_blank class=textbg4>改 分</a>
									</s:else>
								</td>
							</s:iterator>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<SCRIPT type="text/javascript">
				function page(i){
					document.location.href="stat_eroom_batch_view.action?erbatch.id=<s:property value="erbatch.id"/>&department.id=<s:property value="department.id"/>&sub_department=<s:property value="sub_department"/>&pN="+i
				}
			</SCRIPT>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
