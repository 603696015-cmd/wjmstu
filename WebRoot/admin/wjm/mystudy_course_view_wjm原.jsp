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
		<title>单元学习页</title>
		<meta name="keywords" content="OA,OA办公系统,OA系统" />
		<meta name="description"
			content="通达OA系统代表了协同OA的先进理念,是中国用户群最广泛的OA软件,协同OA软件行业唯一央企团队研发,多次摘取国内OA软件金奖,拥有300万终端OA用户,十年研发铸就成熟OA产品" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex_newversion.css"
			rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/113.js"></script>
		<script type="text/javascript" src="js/switch.combo.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
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

.STYLE3 {
	color: #666666
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
										<SPAN class=STYLE1>*</SPAN> 智能辅导分 <s:property value="intelligentPoints" /> 分
										<s:if test="peixunBatch.nowClass.id!=-1">
											<A href="showIntelligent.action?peixunBatch.nowClass.id=<s:property value="peixunBatch.nowClass.id" />">查看</A>
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
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" style="margin-top: 12px; margin-bottom: 30px;">
									<tr>
										<td height="240" valign="top" bgcolor="#F8FCFE">
											<s:if test="myCourses.size() == 0">
											暂无
											</s:if>
											<s:else>
												<table width="100%" border="0" cellpadding="5"
													cellspacing="1" bgcolor="#CFDBE2">
													<tr>
														<td colspan="2" align="left" background="images/bg002.jpg"
															bgcolor="#E9F5FC" style="padding-left: 25px;">
															<a href="#"><span class="STYLE1">自学升级</span> </a>
														</td>
														<td width="150" height="40" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">进 度</span>
														</td>
														<td width="60" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															&nbsp;
														</td>
														<td width="150" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC"
															class="STYLE2">
															学 习
														</td>
														<td width="90" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">完 成 </span>
														</td>
														<td width="130" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															完成时间
														</td>
														<td width="90" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															测验成绩
														</td>
														<td width="100" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															单元测验
														</td>
													</tr>
													<s:iterator value="myCourses">
														<tr>
															<td width="30" align="center" valign="middle"
																bgcolor="#F8FCFE">
																<img src="images/iconred.gif" width="4" height="6" />
															</td>
															<td valign="middle" bgcolor="#F8FCFE">
																<a href="#" class="bt001" style="padding-left: 15px;"><s:property value="course.name" /></a>
															</td>
															<td width="150" height="40" align="left"
																bgcolor="#F8FCFE">
																<div
																	style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
																	<img src="images/jd.gif" width="<s:property value="process" />%" height="14" />
																</div>
															</td>
															<td align="center" bgcolor="#F8FCFE">
																<s:property value="process" />%
															</td>
															<td width="150" align="center" bgcolor="#F8FCFE">
																<table width="95" border="0" cellspacing="0"
																	cellpadding="0">
																	<tr>
																		<s:if test="canLearn==1">
																			<td height="28" align="center"
																				background="images/textbg.jpg">
																				<a href="mystudy_page_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id"/>&course.id=<s:property value="course.id" />&elClass.id=<s:property value="elClass.id" />" class="zc01 STYLE3" style="color: white;">进入学习</a>
																			</td>
																		</s:if>
																		<s:else>
																			<td height="28" align="center"
																				background="images/textbg2.jpg">
																				进入学习
																			</td>
																		</s:else>
																	</tr>
																</table>
															</td>
															<s:if test="process==100.00">
																<td width="90" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	已完成
																</td>
																<td align="center" bgcolor="#F8FCFE" class="bt001">
																	<s:if test="endtime!=null">
																		<s:date name="endtime" format="yyyy年MM月dd日 HH时mm分" />
																	</s:if>
																</td>
																<td width="90" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	<s:if test="endtime!=null">
																		<s:property value="myRoom.myScore" />
																	</s:if>
																</td>
															</s:if>
															<s:elseif test="process==0.00">
																<td width="90" align="center" bgcolor="#F8FCFE">
																	未开始
																</td>
																<td align="center" bgcolor="#F8FCFE" class="bt001">
																	&nbsp;
																</td>
																<td width="90" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	&nbsp;
																</td>
															</s:elseif>
															<s:else>
																<td width="90" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	<span class="STYLE1">学习中</span>
																</td>
																<td align="center" bgcolor="#F8FCFE" class="bt001">
																	&nbsp;
																</td>
																<td width="90" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	&nbsp;
																</td>
															</s:else>
															
															<s:if test="passed">
																<td width="100" align="center" bgcolor="#F8FCFE">
																	<a href="quizpaperinit.action?course.id=<s:property value="course.id" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elClass.id"/>"><img src="images/xtb021.gif"
																			width="28" height="27" border="0" /> </a>
																</td>
															</s:if>
															<s:else>
																<td width="100" align="center" bgcolor="#F8FCFE">
																	<img src="images/xtb0212.jpg"
																			width="28" height="27" border="0" />
																</td>
															</s:else>
														</tr>
													</s:iterator>
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
	</body>
</html>
