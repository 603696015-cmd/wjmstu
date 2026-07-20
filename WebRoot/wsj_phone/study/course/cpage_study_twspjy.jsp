<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" target="_self" >
		<TITLE>学习课程--<s:property value="course.name" />
		</TITLE>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<link href="css/study_twspjy.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script type="text/javascript">
			var _cpst;
			var needsetCp = true;
			function myload(status){
				var from = 0;
				if(!<s:property value="myCPage.passed"/>) 
					from = <s:property value="myCPage.passtime"/>;
				_cvideo = new CourseVideo(<s:property value="coursePage.type"/>,"<s:property value="coursePage.page_url_"/>",from);
			 	_cvideo.show("page_file" );
				_cpst=new CourseStudy(<s:property value="course.classid"/>,<s:property value="course.id"/>, <s:property value="coursePage.id"/>,
					 <s:property value="myCPage.passtime"/>,
					 <s:property value="myCPage.cpage.during"/>*60,
					 <s:property value="coursePage.queryTime"/>,
					 <s:property value="myCPage.passtime2"/>,
					 <s:property value="studyCourseRecordId"/>);
				_cpst.passed2=<s:property value="myCPage.passed2"/>;
				_cpst.durtimediv="timer3";
				_cpst.realtimediv="timer2";
				_cpst.processdiv="processDiv3";
				_cpst.studyinfo_time=<s:property value="#session.studyinfo_time"/>;
				_cpst.init();
				//_cpst.autoSetCprocess();
			}
			window.onbeforeunload=function(){
				if(<s:property value="coursePage.getcredit"/>!=1){
					if(_cpst.passtime2>=<s:property value="myCPage.cpage.during"/>*60){
						if(_cpst.passed2==0){
							alert("你还有考试没过！");
						}
					}
				}
				window.event.returnValue="确定退出本次学习？";
			}
			window.onunload=function(){
					_cpst.exitStudy(); 
			}
			function openPrac(id){
			    if(confirm('确定开始练习？')){
			        window.open("pracPaperinto.action?pracPaper.id="+id,"exampracpaper","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			 	    window.close();
			 	}
			}
		</script>
		<script type="text/javascript" src="js/ccquiz_1b1.js"></script>
	</HEAD>
	<BODY onLoad="myload('<s:property value="status"/>');" style="overflow: hidden;">
		
		<!--
		<div
			style="position: absolute; border: solid 1px buttonface; width: 400; height: 300px; background: white; z-index: 300; display: none;"
			id="noteadd">
			<div style="width: 100%; background: #eeddaa">
				<span style="width: 380">做笔记</span><span
					style="cursor: hand; width: 15px;" onclick="closediv('noteadd')">X</span>
			</div>
			<div style="width: 100%; height: 100%" id="noteaddcontent"></div>
		</div>
		<div
			style="position: absolute; border: solid 1px buttonface; width: 600; height: 400px; background: white; z-index: 301; display: none;"
			id="notelist">
			<div style="width: 100%; background: #eeddaa">
				<span style="width: 580">查看笔记</span><span
					style="cursor: hand; width: 15px;" onclick="closediv('notelist')">X</span>
			</div>
			<div style="width: 100%; height: 100%" id="notelistcontent"></div>
		</div>
		-->
		<table width="320" height="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td height="68px" width="100%">
					<table cellSpacing=0 cellPadding=0 width="320" border=0>
						<tbody>
							<tr>
								<td width="335">
									<table width="320" height=68
										border=0 align="left" cellPadding=0 cellspacing=0>
										<tbody>
											<tr>
												<td vAlign="middle" align="center" width=300
													background="images/img/bfz_r1_c11.jpg"><table width="320" height="50" border="0" align="left" cellpadding="0" cellspacing="0">
                                                  <tr>
                                                    <td valign="middle" style="font-size:12px;"><font class=bt><s:property value="course.name" />
													</font></td>
                                                  </tr>
                                                  <tr>
                                                    <td valign="middle" style="font-size:12px;">课程进度：<span style="width:150px;border: 1px dotted #FF6633;text-align:left;">
																			<IMG height=14 id="cp_img" src="images/jd.gif" width="<s:property value="myCourse.process" />%">
																			</span>
                                                   <span id="cp_img_span"> <s:property value="myCourse.process_" />%</span></td>
                                                  </tr>
                                                  <tr>
                                                    <td align="right" valign="middle"><font style="font-size:12px;"><a href="displayStudyCpageInfo.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">各章节学习详情</a></font></td>
                                                  </tr>
                                            </table>												</td>
											<!--	<td align="center" background="images/img/bfz_r1_c11.jpg">
													<font class=bt><s:property value="course.name" />
													</font>
													
												</td>-->
											</tr>
											<tr>
												<td background="images/img/t-5.jpg" colspan=2 height=13></td>
											</tr>
										</tbody>
									</table>
								</td>
								
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td bgColor=#dae9fe height="100%">
					<table height="100%" width="320" cellpadding="0" cellspacing="0">
						<tr>
							<td width="300px" valign="top" height="100%" id="left"
								>
								<table width="320" height="100%" border="0"  cellpadding="0"
									cellspacing="1">
									<tr>
										<td width="300px" height="250px" valign=top bgColor=#F8FCFE
											id="page_file">
										</td>
									</tr>
									<tr>
										<td bgcolor="#F8FCFE"><div
									style="width: 100%; height: 100%; ">
									<h2 style="width: 100%; text-align: center; margin-top:10px;">
										<s:property value="coursePage.title" />
									</h2>
									<br>
									${coursePage.page_ }
								</div></td>
									</tr>
									<tr>
									
										<td width="300px" height="40" vAlign=top bgColor=#F8FCFE>
										
											<div
												style="width: 100%; height: 40px; text-align: center; margin-top: 10px;">
												<div
													style="width: 200px; height: 20px; margin: 0px auto; background: buttonface; text-align: left;"
													id="processDiv3">
												</div>
												<div id="processDiv4"
													style="font-size: 12px; height: 20px; color: blue;"><span>结业方式：<s:property value="coursePage.getcreditName" /></span>
												</div>
												<!-- 
												<div id="timer" style="font-size: 12px;">
													已学时间：加载中..秒
												</div>
												 -->
												<div style="font-size: 12px;">
													<span id="timer2">已学时长：加载中..秒</span>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<span id="timer3">规定时长：加载中..秒</span>
													<br/>
													
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td width="300px" valign="top" bgcolor="#F8FCFE" height="100%">
										<div style="width: 100%;height:100%;overflow: auto;">
											<div
												style="font-size: 12px; height: 26px; overflow: hidden; text-align: center;">
												<img src="images/img//studied.gif" width="15" height="13">
												<span>已完成</span>
												<img src="images/img/studying.gif" width="15" height="13">
												<span>学习中</span>
												<img src="images/img/unstudied.gif" width="12" height="13">
												<span>未学习</span>
											</div>
											<ul style="margin: 10px 20px;">
												<s:iterator value="myCPages" id="cp">
													<li style="font-size: 14px;">
														<s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
														<a  onclick="return addSavetimeToUrl(this);" href="course_study.action?course.isLogout=1&coursePage.id=<s:property value="cpage.id" />&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">
															<s:property value="cpage.title" /> </a>
														<s:if test="passed">
															<img src="images/img/studied.gif" width="15" height="13" />
														</s:if>
														<s:else>
															<s:if test="begintime!=null">
																<img src="images/img/studying.gif" width="12"
																	height="13" />
															</s:if>
															<s:else>
																<img src="images/img/unstudied.gif" width="12"
																	height="13" />
															</s:else>
														</s:else>
														<!--<s:if test="cpage.skipable==1">【可以跳过】</s:if>
																	<s:else>【不可跳过】</s:else>-->
													</li>
													<s:if test="myPracs[0].ppaper.id!=0">
													<li style="font-size: 14px;">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														【练习】<a href="practice_paper.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>&coursePage.id=<s:property value="cpage.id"/>&examPaper.id=<s:property value="examPaper.id"/>&myPractice.ppaper.id=<s:property value="myPracs[0].ppaper.id"/>"><s:property value="myPracs[0].ppaper.title"/></a>							
													</li>
													</s:if>
													<!-- <s:property value="examPaper.id"/> 练习的试卷id -->
													<%-- 
													<s:iterator value="myPracs" id="pp">
														<li style="font-size: 12px;">
															<i><s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
																&nbsp;&nbsp;&nbsp;&nbsp;[练习] <a target="_blank"
																href="practice_paper.action?course.id=<s:property value="#cp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
																	<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> --> </a> <s:if
																	test="lasttime==null">
																	<img src="images/img/unstudied.gif" width="12"
																		height="13" />
																</s:if> <s:else>
																	<img src="images/img/studied.gif" width="12"
																		height="13" />
																</s:else> </i>
															<!-- <img src="images/img/studied.gif" width="15" height="13"> -->
														</li>
													</s:iterator>
													 --%>
												</s:iterator>
												<%-- 
												<s:iterator value="course.myPracs" id="pp">
													<li style="font-size: 12px;">
														<i> [练习]<a target="_blank"
															href="practice_paper.action?course.id=<s:property value="#cp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
																<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> --> </a> <s:if
																test="lasttime==null">
																<img src="images/img/unstudied.gif" width="12"
																	height="13" />
															</s:if> <s:else>
																<img src="images/img/studied.gif" width="12" height="13" />
															</s:else> </i>
														<!-- <img src="images/img/studied.gif" width="15" height="13"> -->
													</li>
												</s:iterator>
												 --%>
											</ul>
											</div>
										</td>
									</tr>
								</table>
							</td>
							<!--<td width="10" valign="middle"
								background="images/img/bf_r12_c17.jpg">
								<a onClick="javascript:catalog_switch();"><img
										id=switch_button src="images/img/yincang.jpg" width="10"
										height="24" border="0"> </a>
							</td>-->
							<!--<td width="100%" valign="top" bgColor=#dae9fe>
								<div
									style="width: 100%; height: 100%;  overflow: auto;">
									<h2 style="width: 100%; text-align: center;">
										<s:property value="coursePage.title" />
									</h2>
									<br>
									${coursePage.page_ }
								</div>
							</td>-->
						</tr>
						<tr>
							<!--<td width="100%" colspan="3" height="28px">
								<table cellSpacing=0 cellPadding=0 width="100%" border=0>
									<tbody>
										<tr>
											<td width=18>
												<img height=28 alt="" src="images/img/bf_r14_c1.jpg"
													width=18 border=0 name=bf_r14_c1>
											</td>
											<td class=unnamed1 align=middle width=267
												background="images/img/bf_r14_c3.jpg">
												<span class="unnamed1 style2 STYLE8"><span
													class="STYLE10"> <!-- <a
														href='simpaperlist.action?course.id=${course.id }'>模拟考试</a>
														｜ <a
														href='javascript:void(window.open("quizpaper.action?course.id=${course.id }","course_exam_5","toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no"))'>结业考试</a>
												</span> </span>
											</td>
										<td width=48>
												<img height=28 alt="" src="images/img/bf_r14_c15.jpg"
													width=48 border=0 name=bf_r14_c15>
											</td>
											<td vAlign="middle" align="right"
												background="images/img/bf_r14_c21.jpg">
												<div class="leibie">
													<span style="width: 100%">&nbsp;</span>
													<a class=LI
														href="practice_listInit.action?course.id=${course.id }">练习中心</a><span
														class=STYLE5>&gt;&gt;&gt;</span>
													<a class=LI style="cursor: hand"
														onclick="javascript:cnote_add('${course.id }',this)">做笔记</a><span
														class=STYLE5>&gt;&gt;&gt;</span>
													<a class=LI style="cursor: hand"
														onclick="javascript:cnote_list('${course.id }',this)">显示笔记</a><span
														class=STYLE5>&gt;&gt;&gt;</span>
													<a class=LI href="forumListByBlockid.action?fblock.id=1">课程公告
													</a><span class=STYLE5>&gt;&gt;&gt;</span>
													<a class=LI href="forumIndex.action">课程交流 </a><span
														class=STYLE5>&gt;&gt;&gt;</span>
													<a class=LI href="index.action">回首页 </a>
													<span class=STYLE5>&gt;&gt;&gt;</span>
													
												</div>
											</td>
											<td width=17>
												<img height=28 alt="" src="images/img/bf_r14_c29.jpg"
													width=19 border=0 name=bf_r14_c29 />
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>-->
					</table>
			  </td>
		  </tr>
		</table> 
	
	</body>
</HTML>
