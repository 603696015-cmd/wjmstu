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
		<base href="<%=basePath%>">
		<TITLE>学习课程--<s:property value="course.name" />
		</TITLE>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<link href="css/study_dysp.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript">
			var needsetCp = false;
			function myload(){
				var from = 0;
					if(!<s:property value="myCourse.passed"/>) 
					from = <s:property value="myCourse.passtime"/>;
				_cvideo = new CourseVideo(2,"<s:property value="course.exurl_"/>", from);
			 	_cvideo.show("page_file" );
			 	_cpst=new CourseStudy(<s:property value="course.classid"/>,<s:property value="course.id"/>, 0,
					 <s:property value="myCourse.passtime"/>,
					 <s:property value="course.during"/>*60,
					 <s:property value="course.querytime"/>,
					 <s:property value="myCourse.passtime2"/>,
					 <s:property value="studyCourseRecordId"/>);
				_cpst.durtimediv="timer3";
				_cpst.realtimediv="timer2";
				_cpst.processdiv="processDiv";
				_cpst.studyinfo_time=<s:property value="#session.studyinfo_time"/>;
				_cpst.init();
			}
			window.onbeforeunload=function(){
				window.event.returnValue="确定退出本次学习？";
			}
			window.onunload=function(){
				_cpst.exitStudy(); 
			}
		</script>
	</HEAD>
	<BODY onLoad="myload();">
		 <div id="message" style="display: none;"></div>
			<div style="position: absolute;border:solid 1px buttonface; width:400;height:300px;background: white;z-index: 300;display: none;" id="noteadd">
					<div style="width: 100%;background: #eeddaa"><span style="width:380">做笔记</span><span style="cursor:hand;width:15px;" onClick="closediv('noteadd')">X</span> </div>	
					<div style="width: 100%;height:100%" id="noteaddcontent"></div>
					</div>
					<div style="position: absolute;border:solid 1px buttonface; width:600;height:400px;background: white;z-index: 301;display: none;" id="notelist">
					<div style="width: 100%;background: #eeddaa"><span style="width:580">查看笔记</span><span style="cursor:hand;width:15px;" onClick="closediv('notelist')">X</span> </div>	
					<div style="width: 100%;height:100%" id="notelistcontent"></div>
					</div> 
			<table width="100%" height="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td height="68" width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
						<TBODY>
							<TR>
								<TD>
									<TABLE height=68 cellSpacing=0 cellPadding=0 width="100%"
										border=0>
										<TBODY>
											<TR>
												<TD vAlign=center align=middle width=200
													background="images/img/bfz_r1_c11.jpg">&nbsp;
													
												</TD>
												<TD align=middle background="images/img/bfz_r1_c11.jpg">
													<FONT class=bt> <s:property value="course.name" />

													</FONT>
												</TD>
											</TR>
											<TR>
												<TD background="images/img/t-5.jpg" colSpan=2 height=13>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
								</TD>
								<TD width=192>
									<TABLE cellSpacing=0 cellPadding=0 width=192 border=0>
										<TBODY>
											<TR>
												<TD width=192 height=47><IMG height=47 src="images/img/t-6.jpg" width=192></TD>
											</TR>
											<TR>
												<TD width=192 background="images/img/t-7.jpg" height=21>
													<TABLE cellSpacing=0 cellPadding=0 width=192 border=0>
														<TBODY>
															<TR>
																<TD width=72 height=18>&nbsp;
																	
																</TD>
																<TD style="FONT-SIZE: 12px" vAlign=bottom width=120>
																	<SPAN class=STYLE5>&gt;&gt;&gt; </SPAN>
																	<A href="javascript:void(0)" onclick="window.close();"><SPAN class=STYLE5>关闭
																	</SPAN></A>
																</TD>
															</TR>
														</TBODY>
													</TABLE>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
				</td>
			</tr>
			<tr>
				<td bgColor=#dae9fe height="100%">
					<table height="100%" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="300px" valign="top" height="100%"  id="left">
								<div style="width: 300px;height:100%;background: red">
									<table height="100%" bgcolor="red" cellpadding="0" cellspacing="0">
										<tr>
											<TD width="300px" height="250px" vAlign=top bgColor=#dae9fe
												id="page_file">

											</TD>
										</tr>
										<tr>
											<TD width="300px" height="40" vAlign=top bgColor=#dae9fe>
												
												<div style="width: 100%; height: 60px; text-align: center;margin-top: 5px;">
													<div
														style="width: 200px; height: 20px; background: buttonface; text-align: left;margin: 0px auto;"
														id="processDiv">
													</div>
													<div id="processDiv1" style="font-size: 12px;height: 20px; color: blue;">
													</div>
													<!-- 
													<div id="timer" style="font-size: 12px;height: 20px;">
														已学时间：加载中..秒
													</div>
													 -->
													<div style="font-size: 12px;">
														<span id="timer2">实际时长：加载中..秒</span>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<span id="timer3">规定时长：加载中..秒</span>							
													</div>
												</div>
											</TD>
										</tr>
										<tr>
											<TD valign="top" bgColor="#dae9fe" width="300px" height="100%">
												<div style="width: 300px;height:100%;">
												<%-- 
												<div style="font-size:12px;width: 300px;margin:0px auto;text-align:center;">
													<img src="images/img/studied.gif" width="15" height="13">
													<span>已完成</span>
													<img src="images/img/studying.gif" width="15" height="13">
													<span>学习中</span>
													<img src="images/img/unstudied.gif" width="12" height="13">
													<span>未学习</span>
												</div>
												 --%>
												<ul style="margin: 10px 20px;">
														<s:iterator value="myCPages" id="cp">
															<LI style="font-size: 14px;">
																<A href="#"
																	onclick="javascript:getCpage(<s:property value="cpage.id" />);return false;" ><s:property value="cpage.title" /> </A>
																<%-- 
																<s:if test="passed">
																	<img src="images/img/studied.gif" width="15"
																		height="13">
																</s:if>
																<s:else>
																	<s:if test="begintime!=null">
																		<img src="images/img/studying.gif" width="12"
																			height="13">
																	</s:if>
																	<s:else>
																		<img src="images/img/unstudied.gif" width="12"
																			height="13">
																	</s:else>
																</s:else>
																 --%>
																<!--<s:if test="cpage.skipable==1">【可以跳过】</s:if>
																	<s:else>【不可跳过】</s:else>-->
															</LI>
															<%-- 
															<s:iterator value="myPracs" id="pp">
																<LI style="font-size: 12px;">
																	<i><s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
																		&nbsp;&nbsp;&nbsp;&nbsp;[练习] <A target="_blank"
																		href="practice_paper.action?course.id=<s:property value="#cp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
																			<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> --> </A> <s:if
																			test="lasttime==null">
																			<img src="images/img/unstudied.gif" width="12"
																				height="13">
																		</s:if> <s:else>
																			<img src="images/img/studied.gif" width="12"
																				height="13">
																		</s:else> </i>
																	<!-- <img src="images/img/studied.gif" width="15" height="13"> -->
																</LI>
															</s:iterator>
															 --%>
														</s:iterator>
														<%-- 
														<s:iterator value="course.myPracs" id="pp">
															<LI style="font-size: 12px;">
																<i> [练习]<A target="_blank"
																	href="practice_paper.action?course.id=<s:property value="#cp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
																		<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> --> </A> <s:if
																		test="lasttime==null">
																		<img src="images/img/unstudied.gif" width="12"
																			height="13">
																	</s:if> <s:else>
																		<img src="images/img/studied.gif" width="12"
																			height="13">
																	</s:else> </i>
																<!-- <img src="images/img/studied.gif" width="15" height="13"> -->
															</LI>
														</s:iterator>
														 --%>
													</ul>
													</div>
											</TD>
										</tr>
									</table>
								</div>
							</td>
							<td width="10" valign="middle"
								background="images/img/bf_r12_c17.jpg">
								<a onClick="javascript:catalog_switch();"><img id="switch_button"
										src="images/img/yincang.jpg" width="10" height="24" border="0">
								</a>
							</td>
							<td width="100%" valign="top" bgColor=#dae9fe>
								<div style="width: 100%; height: 100%; overflow: auto;" id="cpage_content">
									<script type="text/javascript">getCpage(<s:property value="myCPages[0].cpage.id" />);</script>
								</div>
							</td>
						</tr>
						<tr>
							<td width="100%" colspan="3" height="28px">
								<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
									<TBODY>
										<TR>
											<TD width=18>
												<IMG height=28 alt="" src="images/img/bf_r14_c1.jpg"
													width=18 border=0 name=bf_r14_c1>
											</TD>
											<TD class=unnamed1 align=middle width=267
												background="images/img/bf_r14_c3.jpg">
												<SPAN class="unnamed1 style2 STYLE8"><SPAN
													class=STYLE10>
												</SPAN> </SPAN>
											</TD>
											<TD width=48>
												<IMG height=28 alt="" src="images/img/bf_r14_c15.jpg"
													width=48 border=0 name=bf_r14_c15>
											</TD>
											<TD vAlign="middle" align="right"
												background="images/img/bf_r14_c21.jpg">
												<DIV class="leibie">
													<span  style="width: 100%">&nbsp;</span>
													 
												</DIV>
											</TD>
											<TD width=17>
												<IMG height=28 alt="" src="images/img/bf_r14_c29.jpg"
													width=19 border=0 name=bf_r14_c29>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	
	</body>
</HTML>
