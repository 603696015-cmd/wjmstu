<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>智能辅导分查看</title>
		<meta name="keywords" content="OA,OA办公系统,OA系统" />
		<meta name="description"
			content="通达OA系统代表了协同OA的先进理念,是中国用户群最广泛的OA软件,协同OA软件行业唯一央企团队研发,多次摘取国内OA软件金奖,拥有300万终端OA用户,十年研发铸就成熟OA产品" />
		<script type="text/javascript" src="js/113.js"></script>
		<script type="text/javascript" src="js/switch.combo.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript">
		
		</script>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex_newversion.css"
			rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />

		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
	font-size: 14px;
}

.STYLE2 {
	color: #666666;
	font-weight: bold;
}
-->
</style>
	</head>
	<body>
		<div id="container">





			<table width="1001" height="260" border="0" align="center"
				cellpadding="0" cellspacing="0" class=bg011>
				<tr>
					<td width="270" height="280" align="center" valign="middle"
						background="images/bgheader.jpg">
						<table width="250" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="205" align="left" valign="top"
									background="images/mybg002.jpg"
									style="padding-left: 25px; padding-top: 55px;"">
									<p>
										+++
										<span class="zc01"><s:property value="elUser.realname" />
										</span> +++
										<br />
										<span class="zp"><s:property
												value="elUser.department.name" /> ，<s:property
												value="elUser.role.name" />
											<br /> </span>
										<br />
										<SPAN class=STYLE1>*</SPAN> 已完成的等级
										<s:property value="peixunBatch.doneClass.name" />
										<A
											href="<s:if test="peixunBatch.doneClass.id!=-1">mystudy_course_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id"/>&elClass.id=<s:property value='peixunBatch.doneClass.id' /></s:if><s:else>javascript:void(0);</s:else>">查看</A>
										<BR>
										<SPAN class=STYLE1>*</SPAN> 正在学习的等级
										<s:property value="peixunBatch.nowClass.name" />
										<A
											href="<s:if test="peixunBatch.nowClass.id!=-1">mystudy_course_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id"/>&elClass.id=<s:property value='peixunBatch.nowClass.id' /></s:if><s:else>javascript:void(0);</s:else>">查看</A>
										<br />
										<SPAN class=STYLE1>*</SPAN> 智能辅导分
										<s:property value="intelligentPoints" />
										分
										<s:if test="peixunBatch.nowClass.id!=-1">
											<A
												href="showIntelligent.action?peixunBatch.nowClass.id=<s:property value="peixunBatch.nowClass.id" />">查看</A>
										</s:if>
										<s:else>
											<A href="javascript:void(0);">查看</A>
										</s:else>
									</p>
									<br />
								</td>
							</tr>
						</table>
					</td>
					<td background="images/bgheader.jpg">

						<table width=98% border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td>
									<div class="wrap">
										<div class="slides">
											<newversionLib:newversionCenterMenuDiv></newversionLib:newversionCenterMenuDiv>
										</div>

									</div>


								</td>
							</tr>
						</table>









					</td>
				</tr>
			</table>
			<div class="main">


				<table width="1000" border="0" align="center" cellpadding="0"
					cellspacing="0" bgcolor="#f2f3ed">
					<tr>
						<td valign="top">

							<ul class="kcList clearfix">
								<table width="98%" border="0" align="center" cellpadding="5"
									cellspacing="1" bgcolor="#CFDBE2">
									<tr>
										<td align="center" valign="middle"
											background="images/bg002.jpg" bgcolor="#F8FCFE"
											class="STYLE1">
											<p align="center">
												考查类别
											</p>
										</td>
										<td height="40" align="center" valign="middle"
											background="images/bg002.jpg" bgcolor="#F8FCFE"
											class="STYLE1">
											<p align="center">
												考查指标
											</p>
										</td>
										<td align="center" valign="middle"
											background="images/bg002.jpg" bgcolor="#F8FCFE"
											class="STYLE1">
											<p align="center">
												满分
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											background="images/bg002.jpg" bgcolor="#F8FCFE"
											class="STYLE1">
											<p align="center">
												得分
											</p>
										</td>
									</tr>
									<tr>
										<td rowspan="3" align="center" bgcolor="#F8FCFE" class="di">
											<p>
												学习时间
											</p>
										</td>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												每天学习时间加分
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												10
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											bgcolor="#F8FCFE">
											<p>
												<s:property value="intelligentTutoringPoints.scoreLogin"/>
											</p>
										</td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												每周的学习时间加分
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												10
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											bgcolor="#F8FCFE">
											<p>
												<s:property value="intelligentTutoringPoints.scoreWeek"/>
											</p>
										</td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												等级总学习时间得分
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												10
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											bgcolor="#F8FCFE">
											<p>
												<s:property value="intelligentTutoringPoints.scoreClass"/>
											</p>
										</td>
									</tr>
									<tr>
										<td rowspan="2" align="center" bgcolor="#F8FCFE" class="di">
											<p>
												学习习惯
											</p>
										</td>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												复听智能辅导分
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												20
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											bgcolor="#F8FCFE">
											<p>
												<s:property value="intelligentTutoringPoints.scoreProportion"/>
											</p>
										</td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												录音智能辅导分
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												20
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											bgcolor="#F8FCFE">
											<p>
												<s:property value="intelligentTutoringPoints.scoreRecoding"/>
											</p>
										</td>
									</tr>
									<tr>
										<td rowspan="2" align="center" bgcolor="#F8FCFE" class="di">
											<p>
												学习成绩
											</p>
										</td>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												模块练习成绩智能辅分
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												30
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											bgcolor="#F8FCFE">
											<p>
												<s:property value="intelligentTutoringPoints.scoreAcademic"/>
											</p>
										</td>
									</tr>
									<tr>
										<td height="40" align="center" bgcolor="#F8FCFE">
											<p>
												单元测验辅导分
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												10
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											bgcolor="#F8FCFE">
											<p>
												<s:property value="intelligentTutoringPoints.scoreAcademicCourse"/>
											</p>
										</td>
									</tr>
									<tr>
										<td align="center" bgcolor="#F8FCFE" class="di">
											<p>
												合计
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												---
											</p>
										</td>
										<td align="center" bgcolor="#F8FCFE">
											<p align="center">
												110
											</p>
										</td>
										<td width="120" align="center" valign="middle"
											bgcolor="#F8FCFE">
											<p>
												<s:property value="intelligentTutoringPoints.totalScore"/>
											</p>
										</td>
									</tr>
								</table>
							</ul>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>

