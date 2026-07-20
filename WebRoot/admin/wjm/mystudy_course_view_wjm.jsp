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
		<script type="text/javascript">
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
				window.location.href="mystudy_course_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id" />&elClass.id=<s:property value="elClass.id" />";
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
			<!--中部开始-->
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" bgcolor="#f2f3ed">
					<tr>
						<td valign="top">

							<ul >
								<table width="100%" border="0" align="center" cellpadding="0"
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
															background="images/bg002.jpg" bgcolor="#E9F5FC">&nbsp;
															
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
																<s:if test="initCompliance">
																	<div
																		style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
																		<img src="images/jd.gif" width="100%" height="14" />
																	</div>
																</s:if>
																<s:else>
																	<div
																		style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
																		<img src="images/jd.gif" width="<s:property value="process" />%" height="14" />
																	</div>
																</s:else>
																
															</td>
															<td align="center" bgcolor="#F8FCFE">
																<s:if test="initCompliance">
																	100%
																</s:if>
																<s:else>
																	<s:property value="process" />%
																</s:else>
															</td>
															<td width="150" align="center" bgcolor="#F8FCFE">
																<table width="95" border="0" cellspacing="0"
																	cellpadding="0">
																	<tr>
																		<s:if test="initCompliance">
																			<td height="28" align="center"
																				background="images/textbg.jpg">
																				<a href="mystudy_page_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id"/>&course.id=<s:property value="course.id" />&elClass.id=<s:property value="elClass.id" />" class="zc01 STYLE3" style="color: white;">进入学习</a>
																			</td>
																		</s:if>
																		<s:else>
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
																		</s:else>
																		
																	</tr>
																</table>
															</td>
															<s:if test="initCompliance">
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
															<s:else>
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
																	<td align="center" bgcolor="#F8FCFE" class="bt001">&nbsp;
																		
																  </td>
																	<td width="90" align="center" bgcolor="#F8FCFE"
																		class="bt001">&nbsp;
																		
																  </td>
																</s:elseif>
																<s:else>
																	<td width="90" align="center" bgcolor="#F8FCFE"
																		class="bt001">
																		<span class="STYLE1">学习中</span>
																	</td>
																	<td align="center" bgcolor="#F8FCFE" class="bt001">&nbsp;
																		
																  </td>
																	<td width="90" align="center" bgcolor="#F8FCFE"
																		class="bt001">&nbsp;
																		
																  </td>
																</s:else>
															</s:else>
															
															<s:if test="initCompliance">
																<td width="100" align="center" bgcolor="#F8FCFE">
																	<!-- 
																	<a  href="quizpaperinit.action?course.id=<s:property value="course.id" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elClass.id"/>"><img src="images/xtb021.gif"
																			width="28" height="27" border="0" /> </a>
																	 -->
																	 <a name="startExam" href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examRoom.examPaper.id" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);"><img src="images/xtb021.gif"
																			width="28" height="27" border="0" /> </a>
																</td>
															</s:if>
															<s:else>
																<s:if test="passed">
																	<td width="100" align="center" bgcolor="#F8FCFE">
																		<!-- 
																		<a  href="quizpaperinit.action?course.id=<s:property value="course.id" />&myroom.examroom.id=<s:property value="examRoom.id" />&iscommon=0&elclass.id=<s:property value="elClass.id"/>"><img src="images/xtb021.gif"
																				width="28" height="27" border="0" /> </a>
																		 -->
																		 <a name="startExam" href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examRoom.examPaper.id" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);"><img src="images/xtb021.gif"
																			width="28" height="27" border="0" /> </a>
																	</td>
																</s:if>
																<s:else>
																	<td width="100" align="center" bgcolor="#F8FCFE">
																		<img src="images/xtb0212.jpg"
																				width="28" height="27" border="0" />
																	</td>
																</s:else>
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
			<!--中部结束-->
		
				
			

	</body>
</html>


