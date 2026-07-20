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
		<title>模块学习页</title>
		<meta name="keywords" content="OA,OA办公系统,OA系统" />
		<meta name="description"
			content="通达OA系统代表了协同OA的先进理念,是中国用户群最广泛的OA软件,协同OA软件行业唯一央企团队研发,多次摘取国内OA软件金奖,拥有300万终端OA用户,十年研发铸就成熟OA产品" />
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

.STYLE7 {
	color: #CCCCCC
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
										<span class="zc01"><s:property value="elUser.realname" /></span> +++
										<br />
										<span class="zp"><s:property value="elUser.department.name" /> ，<s:property value="elUser.role.name" /><br /> </span>
										<br />
										<SPAN class=STYLE1>*</SPAN> 已完成的等级 <s:property value="peixunBatch.doneClass.name" />
										<A href="<s:if test="peixunBatch.doneClass.id!=-1">mystudy_course_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id"/>&elClass.id=<s:property value='peixunBatch.doneClass.id' /></s:if><s:else>javascript:void(0);</s:else>">查看</A>
										<BR>
										<SPAN class=STYLE1>*</SPAN> 正在学习的等级 <s:property value="peixunBatch.nowClass.name" />
										<A href="<s:if test="peixunBatch.nowClass.id!=-1">mystudy_course_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id"/>&elClass.id=<s:property value='peixunBatch.nowClass.id' /></s:if><s:else>javascript:void(0);</s:else>">查看</A>
										<br />
										<SPAN class=STYLE1>*</SPAN> 智能辅导分 159 分
										<A
											href="javascript:void(0);">查看</A>
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
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" style="margin-top: 12px; margin-bottom: 30px;">
									<tr>
										<td height="240" valign="top" bgcolor="#F8FCFE">
											<s:if test="myCPages.size()==0">
											暂无
											</s:if>
											<s:else>
												<table width="100%" border="0" cellpadding="5"
													cellspacing="1" bgcolor="#CFDBE2">
													<tr>
														<td height="40" colspan="2" align="left"
															background="images/bg002.jpg" bgcolor="#E9F5FC"
															style="padding-left: 25px;">
															<a href="#"><span class="STYLE1"><s:property value="elClass.name"/>-<s:property  value="course.name" /></span> </a>
														</td>
														<td width="150" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC"
															class="STYLE2">
															学 习
														</td>
														<td width="80" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">进 度 </span>
														</td>
														<td width="180" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															完成时间
														</td>
														<td width="80" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															成 绩
														</td>
														<td width="80" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															答 卷
														</td>
														<td width="120" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															&nbsp;
														</td>
													</tr>
													<s:iterator value="myCPages">
														<tr>
															<td width="50" rowspan="<s:property value="examRooms.size()+1" />" align="center" valign="middle"
																bgcolor="#F8FCFE">
																<img src="images/jiantou01.jpg" width="28" height="80" />
															</td>
															<td height="40" valign="middle" bgcolor="#F8FCFE">
																<a href="#" class="sx" style="padding-left: 15px;"><s:property
																		value="cpage.title" />
																</a>
															</td>
															<td width="150" align="center" bgcolor="#F8FCFE">
																<table width="95" border="0" cellspacing="0"
																	cellpadding="0">
																	<tr>
																		<s:if test="canLearn==1">
																			<td height="28" align="left"
																				background="images/textbg002.jpg">
																				<a target="_blank"
																					href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"
																					style="padding-left: 30px; color: white; font-weight: bold; font-size: 13px;">进入学习</a>
																			</td>
																		</s:if>
																		<s:else>
																			<td height="28" align="left"
																				background="images/textbg02.jpg"
																				style="padding-left: 30px; font-weight: bold; font-size: 13px;">
																				<span class="STYLE7">进入学习</span>
																			</td>
																		</s:else>
																	</tr>
																</table>
															</td>
															<s:if test="passed == 1">
																<td width="80" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	<img src="images/wancheng.jpg" width="30" height="25" />
																</td>
															</s:if>
															<s:elseif test="process==0.00">
																<td width="80" align="center" bgcolor="#F8FCFE">
																	---
																</td>
															</s:elseif>
															<s:else>
																<td width="80" align="center" bgcolor="#F8FCFE">
																	<img src="images/xuexizhong.jpg" width="29" height="25" />
																</td>
															</s:else>
															<td width="180" align="center" bgcolor="#F8FCFE"
																class="bt001">
																&nbsp;
															</td>
															<td width="80" align="center" bgcolor="#F8FCFE"
																class="bt001">
																&nbsp;
															</td>
															<td width="80" align="center" bgcolor="#F8FCFE"
																class="bt001">
																&nbsp;
															</td>
															<td width="120" align="center" bgcolor="#F8FCFE"
																class="bt001">
																&nbsp;
															</td>
														</tr>
														<s:iterator value="examRooms" status="status">
														<tr>
															<td height="40" valign="middle" bgcolor="#F8FCFE">
																<a href="#" class="bt001" style="padding-left: 15px;"><s:property value="title" /></a>
															</td>
															<td width="150" align="center" bgcolor="#F8FCFE">
																<table width="95" border="0" cellspacing="0"
																	cellpadding="0">
																	<tr>
																		<s:if test="canExam == 1">
																			<td height="28" align="left"
																				background="images/textbg001.jpg">
																				<a href='quizpaperinit.action?course.id=<s:property value="courseid" />&myroom.examroom.id=<s:property value="id" />&iscommon=0&elclass.id=<s:property value="elClass.id"/>'
																					style="padding-left: 35px; color: white; font-weight: bold; font-size: 14px;">
																					开 始</a>
																			</td>
																		</s:if>
																		<s:else>
																			<td height="28" align="left"
																				background="images/textbg01.jpg"
																				style="padding-left: 35px; font-weight: bold; font-size: 14px;">
																				<span class="STYLE7">开 始</span>
																			</td>
																		</s:else>
																	</tr>
																</table>
															</td>
															<s:if test="myExamPaper.ispassed==1">
																<td width="80" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	<img src="images/wancheng.jpg" width="30" height="25" />
																</td>
															</s:if>
															<s:else>
																<s:if test="myExamPaper.examPaper.passgrade!=0">
																	<td width="80" align="center" bgcolor="#F8FCFE">
																		<img src="images/xuexizhong.jpg" width="29" height="25" />
																	</td>
																</s:if>
																<s:else>
																	<td width="80" align="center" bgcolor="#F8FCFE">
																		---
																	</td>
																</s:else>
															</s:else>
															<td width="180" align="center" bgcolor="#F8FCFE"
																class="bt001">
																<s:if test="myExamPaper.endtime!=null">
																	<s:date name="myExamPaper.endtime" format="yyyy年MM月dd日 HH时mm分"/>
																</s:if>
															</td>
															<td width="80" align="center" bgcolor="#F8FCFE"
																class="bt001">
																<s:property value="myExamPaper.myScore" />
															</td>
															<td width="80" align="center" bgcolor="#F8FCFE"
																class="bt001">
																<s:if test="myExamPaper.endtime!=null">
																	<a target="_blank" href="myquizpaperview.action?myExamPaper.id=<s:property value="myExamPaper.id" />">
																		<img src="images/dajuan.jpg" width="30" height="25" />
																	</a>
																</s:if>
																<s:else>
																暂无答卷
																</s:else>
															</td>
															<td width="120" align="center" bgcolor="#F8FCFE"
																class="bt001">
																<table width="95" border="0" cellspacing="0"
																	cellpadding="0">
																	<tr>
																		<s:if test="myExamPaper.endtime!=null">
																			<td height="28" align="left"
																				background="images/textbg002.jpg">
																				<a target="_blank" href="myquizpaperlist.action?examPaper.id=<s:property value="examPaper.id" />&examRoom.id=<s:property value="id" />"
																						style="padding-left: 30px; color: white; font-weight: bold; font-size: 13px;">历次记录</a>
																			</td>
																		</s:if>
																		<s:else>
																			<td height="28" align="left">
																				还未考试
																			</td>
																		</s:else>
																	</tr>
																</table>
															</td>
														</tr>
														</s:iterator>
													</s:iterator>
													<tr>
														<td height="40" align="center" valign="middle"
															bgcolor="#F8FCFE">
															<img src="images/danyuanceyan.jpg" width="40" height="35" />
														</td>
														<td valign="middle" bgcolor="#F8FCFE">
															<a href="#" class="STYLE1" style="padding-left: 15px;">单元测验</a>
														</td>
														<td width="150" align="center" bgcolor="#F8FCFE">
															<table width="95" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<s:if test="mycourse.passed">
																		<td height="28" align="left"
																			background="images/textbg001.jpg">
																			<a
																				href='quizpaperinit.action?course.id=<s:property value="course.id" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elClass.id"/>'
																				style="padding-left: 35px; color: white; font-weight: bold; font-size: 14px;">
																				开 始</a>
																		</td>
																	</s:if>
																	<s:else>
																		<td height="28" align="left"
																			background="images/textbg01.jpg"
																			style="padding-left: 35px; font-weight: bold; font-size: 14px;">
																			<span class="STYLE7">开 始</span>
																		</td>
																	</s:else>
																</tr>
															</table>
														</td>



														<s:if test="mycourse.myExamPaper.ispassed==1">
															<td width="80" align="center" bgcolor="#F8FCFE"
																class="bt001">
																<img src="images/wancheng.jpg" width="30" height="25" />
															</td>
														</s:if>
														<s:else>
															<s:if test="mycourse.myExamPaper.examPaper.passgrade!=0">
																<td width="80" align="center" bgcolor="#F8FCFE">
																	<img src="images/xuexizhong.jpg" width="29" height="25" />
																</td>
															</s:if>
															<s:else>
																<td width="80" align="center" bgcolor="#F8FCFE">
																	---
																</td>
															</s:else>
														</s:else>
														<td width="180" align="center" bgcolor="#F8FCFE"
															class="bt001">
															<s:if test="mycourse.endtime!=null">
																<s:date name="mycourse.endtime"
																	format="yyyy年MM月dd日 HH时mm分" />
															</s:if>
														</td>
														<td width="80" align="center" bgcolor="#F8FCFE"
															class="bt001">
															<s:if test="mycourse.endtime!=null">
																<s:property value="mycourse.myRoom.myScore" />
															</s:if>
														</td>
														<td width="80" align="center" bgcolor="#F8FCFE" class="bt001">
															<s:if test="mycourse.endtime!=null">
																<a target="_blank"
																	href="myquizpaperview.action?myExamPaper.id=<s:property value="mycourse.myExamPaper.id" />">
																	<img src="images/dajuan.jpg" width="30" height="25" />
																</a>
															</s:if>
															<s:else>
															暂无答卷
															</s:else>
														</td>
														<td width="120" align="center" bgcolor="#F8FCFE"
															class="bt001">
															<table width="95" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<s:if test="mycourse.endtime!=null">
																		<td height="28" align="left"
																			background="images/textbg002.jpg">
																			<a target="_blank"
																				href="myquizpaperlist.action?examPaper.id=<s:property value="mycourse.myExamPaper.id" />&examRoom.id=<s:property value="myroom.examroom.id" />"
																				style="padding-left: 30px; color: white; font-weight: bold; font-size: 13px;">历次记录</a>
																		</td>
																	</s:if>
																	<s:else>
																		<td height="28" align="left">
																			还未考试
																		</td>
																	</s:else>
																</tr>
															</table>
														</td>
													</tr>

												</table>
											</s:else>
										</td>
									</tr>
								</table>
							</ul>
						</td>
					</tr>
				</table>





			</div>
		</div>
		<script src="js/113.js"></script>
		<script src="js/switch.combo.js"></script>
	
	</body>
</html>
