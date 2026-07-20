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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>课程列表</title>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex.css" rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
function courseStudy(initCompliance,courseid,pageid,classid){
	window.parent.location.href = "course_study.action?initCompliance="+initCompliance+"&course.id="+courseid+"&coursePage.id="+pageid+"&classid="+classid;
	/**
	var mw = window.open("course_study.action?initCompliance="+initCompliance+"&course.id="+courseid+"&coursePage.id="+pageid+"&classid="+classid+"&datetime="+new Date(),"course_study_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
	if (window.screen){ 
		mw.moveTo(0, 0);
		mw.resizeTo(screen.availWidth,screen.availHeight);
	}
	*/
}
function enterEroom(erid,epid,classid,courseid,pageid){
	window.parent.location.href = "quizpaperinit_byepid_wjm.action?examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid;
	/**
	var mw = window.open("quizpaperinit_byepid_wjm.action?examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
	if (window.screen){ 
		mw.moveTo(0, 0);
		mw.resizeTo(screen.availWidth,screen.availHeight);
	}
	
	setdisable();
	*/
}
function myquizpaperview(myexampaperid){
	window.parent.location.href = "myquizpaperview.action?myExamPaper.id="+myexampaperid;
}
function setdisable(){
	$("a[name='startExam']").each(function(){
		$(this).attr("disabled","disabled");
	});
	window.onbeforeunload=function(){
		window.event.returnValue="**********************************\n\n当前正在考试,不能离开本页面,以免造成错误Currently being exam, can not leave the page in order to avoid errors!\n\n**********************************";
	}
}
function refresh1(){
	window.onbeforeunload = null;
	window.setInterval(function(){
		window.location.href="mystudy_page_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id" />&course.id=<s:property value="course.id" />&elClass.id=<s:property value="elClass.id" />";
	},800);
}
</script>
		<style>
#ddd img {
	display: block;
}
</style>

	</head>

	<body >
		<div id="all">
			<!--中部开始-->
			<div id="center">
				<table width=960 height=80% border="0" align="center" cellpadding="0"
					cellspacing="0" >
					<tr>
						<td valign="top">

							<ul >
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
															background="images/bg002.jpg" bgcolor="#E9F5FC">&nbsp;
															
														</td>
													</tr>
													<s:iterator value="myCPages">
														<s:set name="cpid" value="cpage.id"></s:set>
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
																		<s:if test="initCompliance">
																			<td height="28" align="left"
																					background="images/textbg002.jpg">
																					<s:if test="cpage.isNull == 0">
																						<td height="28" align="left"
																							background="images/textbg002.jpg">
																							<a 
																							href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elClass.id" />);"
																							style="padding-left: 30px; color: white; font-weight: bold; font-size: 13px;">进入学习</a>
																						</td>
																					 </s:if>
																					 <s:else>
																					 	<td height="28" align="left"
																							background="images/textbg02.jpg"
																							style="padding-left: 30px; font-weight: bold; font-size: 13px;">
																							<span class="STYLE7">无练习</span>
																						</td>
																					 </s:else>
																				</td>
																		</s:if>
																		<s:else>
																			<s:if test="canLearn==1">
																				<td height="28" align="left"
																					background="images/textbg002.jpg">
																					<s:if test="cpage.isNull == 0">
																						<td height="28" align="left"
																							background="images/textbg002.jpg">
																							<a 
																								href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elClass.id" />);"
																								style="padding-left: 30px; color: white; font-weight: bold; font-size: 13px;">进入学习</a>
																						</td>
																					 </s:if>
																					 <s:else>
																					 	<td height="28" align="left"
																							background="images/textbg02.jpg"
																							style="padding-left: 30px; font-weight: bold; font-size: 13px;">
																							<span class="STYLE7">无练习</span>
																						</td>
																					 </s:else>
																				</td>
																			</s:if>
																			<s:else>
																				<td height="28" align="left"
																					background="images/textbg02.jpg"
																					style="padding-left: 30px; font-weight: bold; font-size: 13px;">
																					<s:if test="cpage.isNull == 0">
																						<span class="STYLE7">进入学习</span>
																					 </s:if>
																					 <s:else>
																					 	<span class="STYLE7">无练习</span>
																					 </s:else>
																				</td>
																			</s:else>
																		</s:else>
																	</tr>
																</table>
															</td>
															<s:if test="initCompliance">
																<td width="80" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	<img src="images/wancheng.jpg" width="30" height="25" />
																</td>
															</s:if>
															<s:else>
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
															</s:else>
															
															<td width="180" align="center" bgcolor="#F8FCFE"
																class="bt001">&nbsp;
																
															</td>
															<td width="80" align="center" bgcolor="#F8FCFE"
																class="bt001">&nbsp;
																
															</td>
															<td width="80" align="center" bgcolor="#F8FCFE"
																class="bt001">&nbsp;
																
															</td>
															<td width="120" align="center" bgcolor="#F8FCFE"
																class="bt001">&nbsp;
																
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
																		<s:if test="initCompliance">
																			<td height="28" align="left"
																					background="images/textbg001.jpg">
																					<s:if test="0>=isExceedNumberExam">
																						<a name="startExam" href="javascript:alert('考试次数已经足够');"  style="padding-left: 35px; color: white; font-weight: bold; font-size: 14px;">
																						开 始
																						</a>
																					</s:if>
																					<s:else>
																						<a name="startExam" href="javascript:enterEroom(<s:property value="id" />,<s:property value="examPaper.id" />,<s:property value="elClass.id"/>,<s:property value="courseid" />,<s:property value="#cpid"/>);"  style="padding-left: 35px; color: white; font-weight: bold; font-size: 14px;">
																						开 始
																						</a>
																					</s:else>
																						
																				</td>
																		</s:if>
																		<s:else>
																			<s:if test="canExam == 1">
																				<td height="28" align="left"
																					background="images/textbg001.jpg">
																					<s:if test="0>=isExceedNumberExam">
																						<a name="startExam" href="javascript:alert('考试次数已经足够');"  style="padding-left: 35px; color: white; font-weight: bold; font-size: 14px;">
																						开 始
																						</a>
																					</s:if>
																					<s:else>
																						<a name="startExam" href="javascript:enterEroom(<s:property value="id" />,<s:property value="examPaper.id" />,<s:property value="elClass.id"/>,<s:property value="courseid" />,<s:property value="#cpid"/>);"  style="padding-left: 35px; color: white; font-weight: bold; font-size: 14px;">
																						开 始
																						</a>
																					</s:else>
																				</td>
																			</s:if>
																			<s:else>
																				<td height="28" align="left"
																					background="images/textbg01.jpg"
																					style="padding-left: 35px; font-weight: bold; font-size: 14px;">
																					<span class="STYLE7">开 始</span>
																				</td>
																			</s:else>
																		</s:else>
																	</tr>
																</table>
															</td>
															<s:if test="initCompliance">
																<td width="80" align="center" bgcolor="#F8FCFE"
																	class="bt001">
																	<img src="images/wancheng.jpg" width="30" height="25" />
																</td>
															</s:if>
															<s:else>
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
																	<!-- 
																	<a  href="myquizpaperview.action?myExamPaper.id=<s:property value="myExamPaper.id" />">
																		<img src="images/dajuan.jpg" width="30" height="25" />
																	</a>
																	 -->
																	 <a  href="javascript:myquizpaperview(<s:property value="myExamPaper.id" />);">
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
																		<!-- 
																		<s:if test="myExamPaper.endtime!=null">
																			<td height="28" align="left"
																				background="images/textbg002.jpg">
																				<a target="_self" href="myquizpaperlist.action?examPaper.id=<s:property value="examPaper.id" />&examRoom.id=<s:property value="id" />"
																						style="padding-left: 30px; color: white; font-weight: bold; font-size: 13px;">历次记录</a>
																			</td>
																		</s:if>
																		<s:else>
																			<td height="28" align="left">
																				还未考试
																			</td>
																		</s:else>
																		 -->
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
																	<s:if test="initCompliance">
																		<td height="28" align="left"
																			background="images/textbg001.jpg">
																			 <a name="startExam" href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="epid" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);"  style="padding-left: 35px; color: white; font-weight: bold; font-size: 14px;">
																			开 始
																			</a>
																		</td>
																	</s:if>
																	<s:else>
																		<s:if test="mycourse.passed">
																			<td height="28" align="left"
																				background="images/textbg001.jpg">
																				 <a name="startExam" href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="epid" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);"  style="padding-left: 35px; color: white; font-weight: bold; font-size: 14px;">
																				开 始
																				</a>
																			</td>
																		</s:if>
																		<s:else>
																			<td height="28" align="left"
																				background="images/textbg01.jpg"
																				style="padding-left: 35px; font-weight: bold; font-size: 14px;">
																				<span class="STYLE7">开 始</span>
																			</td>
																		</s:else>
																	</s:else>
																	
																</tr>
															</table>
														</td>


														<s:if test="initCompliance">
															<td width="80" align="center" bgcolor="#F8FCFE"
																class="bt001">
																<img src="images/wancheng.jpg" width="30" height="25" />
															</td>
														</s:if>
														<s:else>
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
																<a href="javascript:myquizpaperview(<s:property value="mycourse.myExamPaper.id" />);">
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
																	<!-- 
																	<s:if test="mycourse.endtime!=null">
																		<td height="28" align="left"
																			background="images/textbg002.jpg">
																			<a target="_self" href="myquizpaperlist.action?examPaper.id=<s:property value="mycourse.myExamPaper.id" />&examRoom.id=<s:property value="myroom.examroom.id" />"
																				style="padding-left: 30px; color: white; font-weight: bold; font-size: 13px;">历次记录</a>
																		</td>
																	</s:if>
																	<s:else>
																		<td height="28" align="left">
																			还未考试
																		</td>
																	</s:else>
																	 -->
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td width="120" height="45" align="center" bgcolor="#F8FCFE" colspan=8>
															<table width="95" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<td height="28" align="center" 
																		background="images/textbg001.jpg">
																		<s:if test="#course.id>880">
																			<a href="vocabulary_search.action?vocabulary.status=1&course.id=895"  >--词汇--</a>
																		</s:if>
																		<s:else>
																			<a href="vocabulary_search.action?vocabulary.status=1&course.id=894"  >--词汇--</a>
																		</s:else>
																	<!--  <a href="vocabulary_search.action?vocabulary.status=1&course.id=<s:property value="course.id" />"  >--词汇--</a> -->	
																	</td>
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
			<!--中部结束-->
		</div>

	</body>
</html>


