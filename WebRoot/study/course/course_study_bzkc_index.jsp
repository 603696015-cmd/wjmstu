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
		<base href="<%=basePath%>" target="_self">
		<TITLE>标准课程学习首页</TITLE>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px;
	WORD-BREAK: break-all;
	HEIGHT: 100%;
	WORD-WRAP: break-word;
	MARGIN: 0px
}

.bt {
	FONT-SIZE: 23px;
	font-weight: bolder; COLOR : #ffffff;
	LINE-HEIGHT: 26px;
	FONT-FAMILY: "黑体";
	COLOR: #ffffff;
}

.STYLE5 {
	color: #0000FF;
	font-size: 12px;
}

A.LI:link {
	FONT-SIZE: 12px;
	COLOR: #ff0000;
	TEXT-DECORATION: none
}

A.LI:visited {
	FONT-SIZE: 12px;
	COLOR: #ff0000;
	TEXT-DECORATION: none
}

A.LI:hover {
	FONT-SIZE: 12px;
	COLOR: #ff0000;
	TEXT-DECORATION: underline
}

A.LI:active {
	FONT-SIZE: 12px;
	COLOR: #ff0000;
	TEXT-DECORATION: underline
}

.leibie {
	PADDING-RIGHT: 20px;
	FONT-SIZE: 12px;
	OVERFLOW: auto;
	WIDTH: 100%;
	PADDING-TOP: 0px;
	TEXT-ALIGN: right
}

.unnamed1 {
	FONT-SIZE: 12px;
	LINE-HEIGHT: 24px;
	FONT-FAMILY: "宋体"
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px
}

.STYLE10 {
	FONT-SIZE: 12px;
	LINE-HEIGHT: 24px
}
</STYLE>
<script type="text/javascript">
			var _cpst;
			function myload(){
				//_cpst=new CourseStudy();
			}
			window.onunload=function(){
				//_cpst.exitStudy(); 
			}
		</script>
	</HEAD>
	<BODY onload="myload();">
	<form name="forward_form" method="post" action="course_study.action">
		<input type="hidden" name="course.isLogout" value="1" />
		<input type="hidden" name="coursePage.id" value="${coursePage.id}" />
		<input type="hidden" name="course.id" value="${course.id}" />
		<input type="hidden" name="classid" value="${classid}" />
	</form>
	<script type="text/javascript">if(window.confirm("继续学习？")){
		//document.location="";
		forward_form.submit();
	}</script>
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
												<TD width=192 height=47>
													<IMG height=47 src="images/img/t-6.jpg" width=192>
												</TD>
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
																	</SPAN> </A>

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
											<TD width=300 height="250" vAlign=top bgColor=#dae9fe
												id="page_file">
													<a href="javascript:document.forward_form.submit();"><img border="0" src="images/begin.jpg" width="100%" height="100%" /></a>
											</TD>
										</tr>
										<tr>
											<TD width=300 height="30" vAlign=top bgColor=#dae9fe>
												
												<div style="width: 100%; height: 10px; text-align: center;margin-top: 5px;font-size:12px;">
													<%-- 
													<div style="width: 200px; height: 10px; background: buttonface; text-align: left;"
														id="processDiv">
													</div>
													<div id="processDiv1" style="font-size: 12px; color: blue;">
													</div>
													<div id="timer" style="font-size: 12px;height: 20px;">
														已学时间：加载中..秒
													</div>
													 --%>
													课程进度
													<span style="width:150px;border: 1px dotted #FF6633;text-align:left;">
														<IMG height=12 src="images/jd.gif" width="<s:property value="myCourse.process" />%">
													</span>
													<s:property value="myCourse.process_" />%
												</div>
											</TD>
										</tr>
										<tr>
											<TD vAlign="top" bgColor="#dae9fe" height="100%">
												<div style="width: 100%;height:100%;overflow: auto;">
												<div style="font: 12px; text-align: center;">
													<img src="images/img//studied.gif" width="15" height="13">
													<span>已完成</span>
													<img src="images/img/studying.gif" width="15" height="13">
													<span>学习中</span>
													<img src="images/img/unstudied.gif" width="12" height="13">
													<span>未学习</span>
												</div>
												<ul>
														<s:iterator value="myCPages" id="cp">
															<LI style="font-size: 14px;">
																<s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
																<A 
																	href="course_study.action?course.isLogout=1&coursePage.id=<s:property value="cpage.id" />&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">
																	<s:property value="cpage.title" /> </A>
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
																<!--<s:if test="cpage.skipable==1">【可以跳过】</s:if>
																	<s:else>【不可跳过】</s:else>-->
															</LI>
															<!-- 练习 -->
															<!-- 
															<s:if test="myPracs[0].ppaper.id!=0">
																<LI style="font-size: 12px;">
																	<i>
																		&nbsp;&nbsp;&nbsp;&nbsp;[练习]<a href="practice_paper.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>&coursePage.id=<s:property value="cpage.id"/>&examPaper.id=<s:property value="examPaper.id"/>&myPractice.ppaper.id=<s:property value="myPracs[0].ppaper.id"/>"><s:property value="myPracs[0].ppaper.title"/></a>
																	</i>
																</LI>
															</s:if>
															 -->
															 <!-- 放章节考场 -->
															 <s:if test="examRooms.size()>0">
																 <s:iterator value="examRooms">
																 		<LI style="font-size: 12px;">
																			<i>
																				&nbsp;&nbsp;&nbsp;&nbsp;[考试]<a href="quizpaperinit.action?course.id=<s:property value="courseid"/>&myroom.examroom.id=<s:property value="id"/>&coursePage.id=<s:property value="cpage.id"/>"><s:property value="title"/></a>
																			</i>
																		</LI>
																 </s:iterator>
															 </s:if>
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
														<s:iterator value="course.myPracs" id="pp">
															<LI style="font-size: 12px;">
																<i> [练习]<A 
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
													</ul>
													</div>
											</TD>
										</tr>
									</table>
								</div>
							</td>
							<td width="10" valign="middle"
								background="images/img/bf_r12_c17.jpg">
								<a onClick="javascript:catalog_switch();"><img id=switch_button
										src="images/img/yincang.jpg" width="10" height="24" border="0">
								</a>
							</td>
							<td width="100%" valign="top" bgColor=#dae9fe>
								<div style="width: 100%; height: 100%; overflow: auto;">
									<h2 style="width: 100%; text-align: center;">
										课程简介
									</h2>
									<br>
									${course.description }
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
													<a href="displayStudyCpageInfo.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">我的章节学习详情</a>
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
	</BODY>
</HTML>
