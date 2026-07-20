<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.SystemConfOp"%>
<%@page import="com.sopia.ElConstants"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新版个人中心</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		
		<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet"/>
		<link type="text/css" href="css/qhIndex_newversion.css" rel="stylesheet"/>
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/113.js"></script>
		<script type="text/javascript" src="js/switch.combo.js"></script>
		<script type="text/javascript">
		function show(){
			 width=600;
			 height=500;
		  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			 var rv =  window.showModalDialog("showallWeishen.action?x="+Math.random(),null,sFeature);
		}
		</script>
		<style type="text/css">
</style>
	</HEAD>
	<body >
		
		


			<table width="1000" border="0" align="center" cellpadding="0"
				cellspacing="0" bgcolor="#f2f3ed" class=bg011>
				<tr>
					<td width="270" valign="top">
						<table width="250" border="0" align="center" cellpadding="0"
							cellspacing="0" style="margin-top: 12px;">
							<tr>
								<td height="40" background="images/insideLeft_titlebg.png"
									style="padding-left: 20px;">
									<span class="STYLE1">新帖</span>
								</td>
							</tr>
						</table>
						<table width="250" border="0" align="center" cellpadding="0"
							cellspacing="0" class=border1>
							<tr>
								<td height="205" valign="top" background="images/contentbg.png">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<s:iterator value="forums" status="status">
											<s:if test="#status.index+1<=3">
												<tr>
													<td width="30" height="30" align="center">
														<img src="images/001ico.jpg" width="8" height="9" />
													</td>
													<td>
														<a
															href="forumView.action?forum.id=<s:property value='id' />&pN=0&pS=10" target="_blank">
															<s:if test="title.length()>20">
																<s:property value="title.substring(0,20)" />...
															</s:if> <s:else>
																<s:property value="title" />
															</s:else> </a>
													</td>
												</tr>
											</s:if>
										</s:iterator>
									</table>
									<img src="images/forum.gif" width="250" height="110" />
								</td>
							</tr>
						</table>
						<table width="250" border="0" align="center" cellpadding="0"
							cellspacing="0" style="margin-top: 12px;">
							<tr>
								<td height="40" background="images/insideLeft_titlebg.png"
									style="padding-left: 20px;">
									<span class="STYLE1">课件下载 </span>
								</td>
							</tr>
						</table>
						<table width="250" border="0" align="center" cellpadding="0"
							cellspacing="0" class=border1>
							<tr>
								<td height="462" valign="top" background="images/contentbg2.png">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="60" align="right" valign="bottom"
												style="padding-right: 30px;">
												<a href="knowledge_center.action"
													style="color: orange; font-size: 15px; font-weight: bold;" target='blank' >去课件库看看>>></a>
											</td>
										</tr>
									</table>
									<p>
										<img style="margin-bottom: 10px;" src="images/computer.png"
											width="199" height="224" />
									</p>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										
										 <zdyLib:zdyloop lablename='个人首页知识6条'  xunhuan='5' setnull='暂无课件' switches='' include=''  constraint='true'    ></zdyLib:zdyloop>
										
										
									</table>
								</td>
							</tr>
						</table>
						<p>&nbsp;
							
						</p>
					</td>
					<td valign="top">
						<ul class="kcList clearfix">
							<%
							 if (SystemConfOp.getIntValue(ElConstants.NEWSHOUYE) == 0) {
							%>
							<s:if test="myallcourse == true">
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" style="margin-top: 12px;">
									<tr>
										<td height="240" align="center" valign="top" bgcolor="#F8FCFE">
											<table width="100%" border="0" cellpadding="5"
												cellspacing="1" bgcolor="#CFDBE2">
												<tr>
													<td colspan="2" align="left" background="images/bg002.jpg"
														bgcolor="#E9F5FC" style="padding-left: 25px;">
														<span class="STYLE1">我的课程</span>
													</td>
													<td width="200" height="40" align="center"
														background="images/bg002.jpg" bgcolor="#E9F5FC">
														<span class="STYLE2">进 度</span>													</td>
													<!--<td width="130" align="center"
														background="images/bg002.jpg" bgcolor="#E9F5FC">
														<span class="STYLE2">开始时间</span>
													</td>
													<td width="130" align="center"
														background="images/bg002.jpg" bgcolor="#E9F5FC">
														<span class="STYLE2">结束时间</span>
													</td>-->
													<td width="60" align="center" background="images/bg002.jpg"
														bgcolor="#E9F5FC">
														<span class="STYLE2">进入学习</span>
													</td>
												</tr>
												<s:if test="studyCourseList.size()!=0">
													<s:iterator value="studyCourseList">
														<tr>
															<td width="30" align="center" valign="middle"
																bgcolor="#F8FCFE">
																<img src="images/iconred.gif" width="4" height="6" />
															</td>
															<td align="left" valign="middle" bgcolor="#F8FCFE">
																<a target="_blank"
																	href="course_study.action?course.id=<s:property value='course.id' />&coursePage.id=-1&classid=<s:property value='course.classid' />"
																	class="bt001" style="padding-left: 15px;"> 
																<s:if
																		test="course.name.length()>=20">
																		<s:property value='course.name.substring(0,20)' />...
																</s:if> <s:else>
																		<s:property value='course.name' />
															  </s:else>
</a>
														  </td>
															<td width="200" height="40" align="left" bgcolor="#F8FCFE">
																<div
																	style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
																	<img height=14 src="images/jd.gif"
																		width="<s:property value="processStr" />%" />																</div>
														  </td>
															<!--<td align="center" bgcolor="#F8FCFE">
																<s:date name="course.roomstart"
																	format="yyyy年MM月dd日 HH时mm分" />
															</td>
															<td align="center" bgcolor="#F8FCFE">
																<s:date name="course.roomend"
																	format="yyyy年MM月dd日 HH时mm分" />
															</td>-->
															<td align="center" bgcolor="#F8FCFE">
																<a target="_blank"
																	href="course_study.action?course.id=<s:property value='course.id' />&coursePage.id=-1&classid=<s:property value='course.classid' />"
																	class="bt001" style="padding-left: 15px;"><img
																		src="images/xtb021.gif" width="28" height="27" /> </a>
															</td>
														</tr>
													</s:iterator>
												</s:if>
												
											</table>
											
											<s:else>
														<p>&nbsp;</p>
														<p>暂无课程</p><br />

														<table width="245" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="75" align="center" valign="middle" background="images/button.png"><a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=1" target="_blank" style="font-size:30px;font-weight:bold;">我要选课&gt;&gt;&gt;</a></td>
  </tr>
</table>

														
														</p>
										  </s:else>									  </td>
									</tr>
								</table>
							</s:if>
							<s:if test="myexams == true">
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" style="margin-top: 12px;">
									<tr>
										<td height="240" valign="top" bgcolor="#F8FCFE">
											<ul class="kcList clearfix">
												<table width="100%" border="0" align="center"
													cellpadding="5" cellspacing="1" bgcolor="#CFDBE2">
													<tr>
														<td colspan="2" align="left" background="images/bg002.jpg"
															bgcolor="#E9F5FC" style="padding-left: 25px;">
															<span class="STYLE1">我的考试</span>
														</td>
														<td width="60" height="40" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">成 绩 </span>
														</td>
														<td width="130" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">开始时间</span>
														</td>
														<td width="130" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">结束时间</span>
														</td>
														<td width="60" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">进入考场</span>
														</td>
													</tr>
													<s:if test="myrooms.size() != 0">
														<s:iterator value="myrooms">
															<tr>
																<td width="30" align="center" valign="middle"
																	bgcolor="#F8FCFE">
																	<img src="images/iconred.gif" width="4" height="6" />
																</td>
																<td valign="middle" bgcolor="#F8FCFE">
																	<a
																		href="javascript:window.parent.open('quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list');"
																		style="padding-left: 15px;"> <s:if
																			test="examroom.title.length()>=20">
																			<s:property value='examroom.title.substring(0,20)' />...
																		</s:if> <s:else>
																			<s:property value='examroom.title' />
																		</s:else> </a>
																</td>
																<td height="40" align="center" bgcolor="#F8FCFE"
																	class="<s:if test="myScore>0">STYLE1</s:if>">
																	<s:if test="myScore>0">
																		<s:property value='myScore' />
																	</s:if>
																	<s:else>未参加</s:else>

																</td>
																<td align="center" bgcolor="#F8FCFE">
																	<s:date name="examroom.begintime"
																		format="yyyy年MM月dd日 HH时mm分" />
																</td>
																<td align="center" bgcolor="#F8FCFE">
																	<s:date name="examroom.endtime"
																		format="yyyy年MM月dd日 HH时mm分" />
																</td>
																<td align="center" bgcolor="#F8FCFE">
																	<a
																		href="quizpaperinit.action?myroom.examroom.id=<s:property value="examroom.id"/>&Return=list"
																		style="padding-left: 15px;"> <img
																			src="images/xtb021.gif" width="28" height="27" /> </a>
																</td>
															</tr>
														</s:iterator>
													</s:if>
													<s:else>
															暂无考试
														</s:else>
												</table>
											</ul>
										</td>
									</tr>
								</table>
							</s:if>
							<s:if test="mytrainingcourses == true">
								<table width="98%" border="0" align="center" cellpadding="0"
									cellspacing="0" style="margin-top: 12px;">
									<tr>
										<td height="240" valign="top" bgcolor="#F8FCFE">
											<ul class="kcList clearfix">
												<table width="100%" border="0" align="center"
													cellpadding="5" cellspacing="1" bgcolor="#CFDBE2">
													<tr>
														<td colspan="2" align="left" background="images/bg002.jpg"
															bgcolor="#E9F5FC" style="padding-left: 25px;">
															<span class="STYLE1">我的专题</span>
														</td>
														<td width="60" height="40" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">结 业</span>
														</td>
														<!--<td width="130" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">开始时间</span>
														</td>-->
														<td width="130" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">截止时间</span>
														</td>
														<td width="60" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">进入学习</span>
														</td>
													</tr>
													<s:if test="myClasses.size() != 0">
														<s:iterator value="myClasses">
															<tr>
																<td width="30" align="center" valign="middle"
																	bgcolor="#F8FCFE">
																	<img src="images/iconred.gif" width="4" height="6" />
																</td>
																<td valign="middle" bgcolor="#F8FCFE">
																	<a
																		href="javascript:window.parent.open('myelclass_view.action?elclass.id=<s:property value="elClass.id" />&Return=stclalist');"
																		class="bt001" style="padding-left: 15px;"> <s:if
																			test="elClass.name.length()>=20">
																			<s:property value='elClass.name.substring(0,20)' />...
																		</s:if> <s:else>
																			<s:property value='elClass.name' />
																		</s:else> </a>
																</td>
																<td height="40" align="center" bgcolor="#F8FCFE"
																	class="<s:if test="passed">bt001</s:if>">
																	<s:if test="passed">已结业</s:if>
																	<s:else>未结业</s:else>
																</td>
																<!--<td align="center" bgcolor="#F8FCFE">
																	<s:date name="elClass.starttime" format="yyyy年MM月dd日" />
																</td>-->
																<td align="center" bgcolor="#F8FCFE">
																	<s:date name="elClass.finishtime" format="yyyy年MM月dd日" />
																</td>
																<td align="center" bgcolor="#F8FCFE">
																	<a
																		href="myelclass_view.action?elclass.id=<s:property value="elClass.id" />&Return=stclalist"
																		class="bt001" style="padding-left: 15px;"> <img
																			src="images/xtb021.gif" width="28" height="27" /> </a>
																</td>
															</tr>
														</s:iterator>
													</s:if>
													<s:else>
														暂无培训专题
													</s:else>
												</table>
											</ul>
										</td>
									</tr>
								</table>
							</s:if>
							<%
								} else {
							%>
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0" style="margin-top: 12px;">
								<tr>
									<td height="240" valign="top" bgcolor="#F8FCFE">
										<table width="100%" border="0" align="center" cellpadding="5"
											cellspacing="1" bgcolor="#CFDBE2">
											<s:if test="myDaibanshuwu.size()!=0">
												<tr>
													<td width="20" align="left" background="images/bg002.jpg"
														bgcolor="#E9F5FC">&nbsp;
														
													</td>
													<td align="left" background="images/bg002.jpg"
														bgcolor="#E9F5FC" style="padding-left: 25px;">
														<span class="STYLE1">待办事务</span>
													</td>
													<td width="90" height="40" align="center"
														background="images/bg002.jpg" bgcolor="#E9F5FC">
														<span class="STYLE2">开始时间 </span>
													</td>
													<td width="130" align="center"
														background="images/bg002.jpg" bgcolor="#E9F5FC">
														<span class="STYLE2">相关行为</span>
													</td>
													<td width="130" align="center"
														background="images/bg002.jpg" bgcolor="#E9F5FC">
														<span class="STYLE2">相关客户</span>
													</td>
													<td width="90" align="center" background="images/bg002.jpg"
														bgcolor="#E9F5FC">
														联系人
													</td>
													<td width="60" align="center" background="images/bg002.jpg"
														bgcolor="#E9F5FC">
														<span class="STYLE2">查看</span>
													</td>
												</tr>
												<s:iterator value="myDaibanshuwu">
													<tr>
														<td width="20" align="center" valign="middle"
															bgcolor="#F8FCFE">
															<img src="images/iconred.gif" width="4" height="6" />
														</td>
														<td valign="middle" bgcolor="#F8FCFE">
															<a
																href="viewContactTags.action?tablename=DBSW&id=<s:property value='id'/>"
																class="bt001" style="padding-left: 8px;"> <s:if
																	test="DBSW_SWMC.length()>=15">
																	<s:property value='DBSW_SWMC.substring(0,15)' />...
															</s:if> <s:else>
																	<s:property value='DBSW_SWMC' />
																</s:else> </a>
														</td>
														<td width="100" height="50" align="center"
															bgcolor="#F8FCFE">
															<s:date name="DBSW_KSRQ" format="yyyy年MM月dd日"></s:date>
														</td>
														<td width="150" align="center" valign="center"
															bgcolor="#F7F9F9" class="STYLE7">
															<s:iterator value="DBSW_XGXW" id="value">
																<s:property value="value" />
																<br>
															</s:iterator>
														</td>
														<td width="80" align="center" valign="center"
															bgcolor="#F7F9F9" class="STYLE7">
															<s:iterator value="DBSW_XGKH" id="value">
																<s:property value="value" />
																<br>
															</s:iterator>
														</td>
														<td width="60" align="center" valign="center"
															bgcolor="#F7F9F9" class="STYLE7">
															<s:iterator value="DBSW_XGLXR" id="value">
																<s:property value="value" />
																<br>
															</s:iterator>
														</td>
														<td align="center" bgcolor="#F8FCFE">
															<a
																href="viewContactTags.action?tablename=DBSW&id=<s:property value='id'/>"
																class="bt001" style="padding-left: 8px;"> <img
																	src="images/xtb021.gif" width="28" height="27" /> </a>
														</td>
													</tr>
												</s:iterator>
											</s:if>
											<s:else>暂无待办事务</s:else>
										</table>
									</td>
								</tr>
							</table>
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0" style="margin-top: 12px;">
								<tr>
									<td height="240" valign="top" bgcolor="#F8FCFE">
										<table width="100%" border="0" align="center" cellpadding="5"
											cellspacing="1" bgcolor="#CFDBE2">
											<s:if test="myLog.size() != 0">
												<tr>
													<td width="20" height="40" align="left"
														background="images/bg002.jpg" bgcolor="#E9F5FC">&nbsp;
														
													</td>
													<td width="90" align="left" background="images/bg002.jpg"
														bgcolor="#E9F5FC" style="padding-left: 20px;">
														<span class="STYLE1">工作日志 </span>
													</td>
													<td align="center" background="images/bg002.jpg"
														bgcolor="#E9F5FC">
														<span class="STYLE2">相关行为</span>
													</td>
													<td width="160" align="center"
														background="images/bg002.jpg" bgcolor="#E9F5FC">
														<span class="STYLE2">相关客户</span>
													</td>
													<td width="90" align="center" background="images/bg002.jpg"
														bgcolor="#E9F5FC">
														联系人
													</td>
													<td width="60" align="center" background="images/bg002.jpg"
														bgcolor="#E9F5FC">
														<span class="STYLE2">查看</span>
													</td>
												</tr>
												<s:iterator value="myLog">
													<tr>
														<td width="20" align="center" valign="middle"
															bgcolor="#F8FCFE">
															<img src="images/iconred.gif" width="4" height="6" />
														</td>
														<td width="100" height="50" valign="middle"
															bgcolor="#F8FCFE">
															<a
																href="viewContactTags.action?tablename=GRRZ&id=<s:property value='id'/>"
																class="bt001" style="padding-left: 8px;"> <s:date
																	name="GRRZ_TXRQ" format="yyyy年MM月dd日" /> </a>
														</td>
														
														<td colspan="3" align="center" bgcolor="#F8FCFE">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<td>
																		<s:iterator value="GRRZ_XGXW" id="value">
										                     				<s:property value="value"/><br>
										                              	</s:iterator>
																	</td>
																	<td width="160">
																		<s:iterator value="GRRZ_XGKH" id="value">
										                              		<s:property value="value"/><br>
										                              	</s:iterator>
																	</td>
																	<td>
																		<s:iterator value="GRRZ_XGLXR" id="value">
										                              		<s:property value="value"/><br>
										                              	</s:iterator>
																	</td>
																</tr>
															</table>
														</td>
														<td align="center" bgcolor="#F8FCFE">
															<a
																href="viewContactTags.action?tablename=GRRZ&id=<s:property value='id'/>"
																class="bt001" style="padding-left: 8px;"> <img
																	src="images/xtb021.gif" width="28" height="27" /> </a>
														</td>
													</tr>
												</s:iterator>
											</s:if>
											<s:else>
												暂无工作日志
											</s:else>
										</table>
									</td>
								</tr>
							</table>
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0" style="margin-top: 12px; margin-bottom: 15px;">
								<tr>
									<td height="240" valign="top" bgcolor="#F8FCFE">
										<table width="100%" border="0" align="center" cellpadding="5"
											cellspacing="1" bgcolor="#CFDBE2">
											<s:if test="myPlan.size() != 0">
											<tr>
												<td width="20" align="left" background="images/bg002.jpg"
													bgcolor="#E9F5FC">&nbsp;
													
												</td>
												<td align="left" background="images/bg002.jpg"
													bgcolor="#E9F5FC" style="padding-left: 25px;">
													<span class="STYLE1">工作计划 </span>
												</td>
												<td width="70" height="40" align="center"
													background="images/bg002.jpg" bgcolor="#E9F5FC">
													<span class="STYLE2">周 期 </span>
												</td>
												<td width="130" align="center" background="images/bg002.jpg"
													bgcolor="#E9F5FC">
													<span class="STYLE2">相关行为</span>
												</td>
												<td width="130" align="center" background="images/bg002.jpg"
													bgcolor="#E9F5FC">
													<span class="STYLE2">相关客户</span>
												</td>
												<td width="90" align="center" background="images/bg002.jpg"
													bgcolor="#E9F5FC">
													联系人
												</td>
												<td width="60" align="center" background="images/bg002.jpg"
													bgcolor="#E9F5FC">
													<span class="STYLE2">查看</span>
												</td>
											</tr>
												<s:iterator value="myPlan">
													<tr>
														<td width="20" align="center" valign="middle"
															bgcolor="#F8FCFE">
															<img src="images/iconred.gif" width="4" height="6" />
														</td>
														<td valign="middle" bgcolor="#F8FCFE">
															<a href="viewContactTags.action?tablename=GZJH&id=<s:property value='id'/>" class="bt001" style="padding-left: 8px;">
																<s:if test="GZJH_JHMC.length()>=15">
																	<s:property value='GZJH_JHMC.substring(0,15)' />...
																</s:if> 
																<s:else>
																	<s:property value='GZJH_JHMC' />
																</s:else>
															</a>
														</td>
														<td width="70" height="50" align="center" bgcolor="#F8FCFE">
															<s:property value="GZJH_JHZQ"/>
														</td>
														<td width="150" align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7">
						                                	<s:iterator value="GZJH_XGXW" id="value">
						                                		<s:property value="value"/><br>
						                                	</s:iterator>
						                                </td>
						                                <td width="80" align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7">
						                                	<s:iterator value="GZJH_XGKH" id="value">
						                                		<s:property value="value"/><br>
						                                	</s:iterator>
						                                </td>
						                                <td width="60" align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7">
						                                	<s:iterator value="GZJH_XGLXR" id="value">
						                                		<s:property value="value"/><br>
						                                	</s:iterator>
						                                </td>
														<!-- 
														<td colspan="3" align="center" bgcolor="#F8FCFE">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<td width="130">
																		&nbsp;
																	</td>
																	<td>
																		&nbsp;
																	</td>
																	<td>
																		&nbsp;
																	</td>
																</tr>
																<tr>
																	<td width="130">
																		&nbsp;
																	</td>
																	<td width="130">
																		&nbsp;
																	</td>
																	<td width="90">
																		&nbsp;
																	</td>
																</tr>
															</table>
														</td>
														 -->
														<td align="center" bgcolor="#F8FCFE">
															<a href="viewContactTags.action?tablename=GZJH&id=<s:property value='id'/>" class="bt001" style="padding-left: 8px;">
																<img src="images/xtb021.gif" width="28" height="27" />
															</a>
														</td>
													</tr>
												</s:iterator>
											</s:if>
											<s:else>
												暂无计划
											</s:else>
										</table>
									</td>
								</tr>
							</table>
							<%
								}
							%>
						</ul>

						<p></p>
					</td>
				</tr>
			</table>


<!--

			<table width="1001" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="margin-top: 15px;">
							<tr>
								<td width="170" height="249" align="center">
									<a target="_blank" href="/oa/MYOA2013/"><img
											src="images/news-img3.jpg" width="117" height="164"
											alt="" /> </a>
								</td>
								<td>
									<table width="92%" border="0" align="left" cellpadding="0"
										cellspacing="0">
										<s:iterator value="newMessage">
											<tr>
												<td width="30" align="center" valign="middle">
													<img src="images/iconred.gif" width="4" height="6" />
												</td>
												<td height="30">
													<a
														href="javascript:window.parent.open('mess_info.action?mess.mess_id=<s:property value='mess_id' />&deleteType=1')"
														class="bt003"> <s:if test="mess_title.length()>=20">
															<s:property value='mess_title.substring(0,20)' />...
														</s:if> <s:else>
															<s:property value='mess_title' />
														</s:else> </a>
												</td>
												<td width="100" class="bt001">
													<s:date name="mess_time" format="yyyy年MM月dd日" />
												</td>
											</tr>
										</s:iterator>
										<tr>
						                    <td align="center" valign="middle">&nbsp;</td>
						                    <td height="30">&nbsp;</td>
						                    <td width="90"><a target="_blank" href="newsIndex.action?news.title=null&news.ntype.id=1&ntype.id=1"  title="查看全部新闻"><img src="images/more.gif"  border="0"/></a></td>
						                </tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td width="400">
						<table width="386" border="0" cellpadding="0" cellspacing="1"
							bgcolor="#FFFFFF" class="border-white"
							style="margin-top: 15px; margin-bottom: 20px;">
							<tr>
								<td width="50%" height="100" align="left" bgcolor="#95c3f4"
									style="padding-left: 15px;">
									<a  target="_blank"
										href="newsIndexView.action?news.id=<s:property value='newses[0].id' />"
										class="zp"> <s:if test="newses[0].title.length()>=15">
											<s:property value='newses[0].title.substring(0,15)' />...
													</s:if> <s:else>
											<s:property value='newses[0].title' />
										</s:else> </a>
									<br />
									<s:if
										test="newses[0].nstyle.name!=null||newses[0].nstyle.name!=''">
													栏目：<s:property value='newses[0].nstyle.name' />
									</s:if>
									<br />
									<s:if
										test="newses[0].releasetime!=null||newses[0].releasetime!=''">
													发布：<s:date name="newses[0].releasetime"
											format="yyyy年MM月dd日 HH时mm分"></s:date>
									</s:if>
								</td>
								<td align="left" bgcolor="#cefa7f" style="padding-left: 15px;">
									<a
										href="newsIndexView.action?news.id=<s:property value='newses[1].id' />"
										class="zp"> <s:if test="newses[1].title.length()>=15">
											<s:property value='newses[1].title.substring(0,15)' />...
													</s:if> <s:else>
											<s:property value='newses[1].title' />
										</s:else> </a>
									<br />
									<s:if
										test="newses[0].nstyle.name!=null||newses[0].nstyle.name!=''">
													栏目：<s:property value='newses[0].nstyle.name' />
									</s:if>
									<br />
									<s:if
										test="newses[0].releasetime!=null||newses[0].releasetime!=''">
													发布：<s:date name="newses[0].releasetime"
											format="yyyy年MM月dd日 HH时mm分"></s:date>
									</s:if>
								</td>
							</tr>
							<tr>
								<td width="50%" height="100" align="left" bgcolor="#b9dafb"
									style="padding-left: 15px;">
									<a
										href="newsIndexView.action?news.id=<s:property value='newses[2].id' />"
										class="zp"> <s:if test="newses[2].title.length()>=15">
											<s:property value='newses[2].title.substring(0,15)' />...
													</s:if> <s:else>
											<s:property value='newses[2].title' />
										</s:else> </a>
									<br />
									<s:if
										test="newses[2].nstyle.name!=null||newses[2].nstyle.name!=''">
													栏目：<s:property value='newses[2].nstyle.name' />
									</s:if>
									<br />
									<s:if
										test="newses[2].releasetime!=null||newses[2].releasetime!=''">
													发布：<s:date name="newses[2].releasetime"
											format="yyyy年MM月dd日 HH时mm分"></s:date>
									</s:if>
								</td>
								<td align="left" bgcolor="#d9faa1" style="padding-left: 15px;">
									<a
										href="newsIndexView.action?news.id=<s:property value='newses[3].id' />"
										class="zp"> <s:if test="newses[3].title.length()>=15">
											<s:property value='newses[3].title.substring(0,15)' />...
													</s:if> <s:else>
											<s:property value='newses[3].title' />
										</s:else> </a>
									<br />
									<s:if
										test="newses[3].nstyle.name!=null||newses[3].nstyle.name!=''">
													栏目：<s:property value='newses[3].nstyle.name' />
									</s:if>
									<br />
									<s:if
										test="newses[3].releasetime!=null||newses[3].releasetime!=''">
													发布：<s:date name="newses[3].releasetime"
											format="yyyy年MM月dd日 HH时mm分"></s:date>
									</s:if>
								</td>
							</tr>
							<tr>
								<td width="50%" height="100" align="left" bgcolor="#d4e9fe"
									style="padding-left: 15px;">
									<a
										href="newsIndexView.action?news.id=<s:property value='newses[4].id' />"
										class="zp"> <s:if test="newses[4].title.length()>=15">
											<s:property value='newses[4].title.substring(0,15)' />...
													</s:if> <s:else>
											<s:property value='newses[4].title' />
										</s:else> </a>
									<br />
									<s:if
										test="newses[4].nstyle.name!=null||newses[4].nstyle.name!=''">
													栏目：<s:property value='newses[4].nstyle.name' />
									</s:if>
									<br />
									<s:if
										test="newses[4].releasetime!=null||newses[4].releasetime!=''">
													发布：<s:date name="newses[4].releasetime"
											format="yyyy年MM月dd日 HH时mm分"></s:date>
									</s:if>
								</td>
								<td align="left" bgcolor="#e3fdbe" style="padding-left: 15px;">
									<a
										href="newsIndexView.action?news.id=<s:property value='newses[5].id' />"
										class="zp"> <s:if test="newses[5].title.length()>=15">
											<s:property value='newses[5].title.substring(0,15)' />...
													</s:if> <s:else>
											<s:property value='newses[5].title' />
										</s:else> </a>
									<br />
									<s:if
										test="newses[5].nstyle.name!=null||newses[5].nstyle.name!=''">
													栏目：<s:property value='newses[5].nstyle.name' />
									</s:if>
									<br />
									<s:if
										test="newses[5].releasetime!=null||newses[5].releasetime!=''">
													发布：<s:date name="newses[5].releasetime"
											format="yyyy年MM月dd日 HH时mm分"></s:date>
									</s:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>  -->
		
	</body>
</html>