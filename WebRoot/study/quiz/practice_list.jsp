<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>学习课程--练习中心---<s:property value="course.name" /></TITLE>
		<base href="<%=basePath%>">
		<link href="css/bofang2.css" type="text/css" rel="stylesheet">
		<STYLE type=text/css>
.STYLE4 {
	FONT-SIZE: 12px
}

.jiangyi {
	PADDING-RIGHT: 8px;
	PADDING-LEFT: 8px;
	FONT-SIZE: 12px;
	PADDING-BOTTOM: 8px;
	PADDING-TOP: 8px;
	BACKGROUND-COLOR: #ffffff
}

.STYLE5 {
	COLOR: #ff0000
}

#menubox {
	BORDER-RIGHT: #26517b 0px solid;
	BORDER-TOP: #26517b 0px solid;
	BACKGROUND: #ffffff;
	MARGIN: 0px;
	BORDER-LEFT: #26517b 0px solid;
	WIDTH: 180px;
	BORDER-BOTTOM: #26517b 0px solid;
	HEIGHT: auto
}

BODY {
	MARGIN: 0px
}

.STYLE10 {
	FONT-SIZE: 12px;
	LINE-HEIGHT: 24px
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px
}

.STYLE11 {
	FONT-SIZE: 14px;
	COLOR: #ff0000
}

BODY {
	FONT-SIZE: 12px; HEIGHT: 100%;WORD-BREAK: break-all; WORD-WRAP: break-word
}
.unnamed1 {
	FONT-SIZE: 12px; LINE-HEIGHT: 24px; FONT-FAMILY: "宋体"
}
.style1 {
	FONT-WEIGHT: bold; COLOR: #ff0000
}
.bt {
	FONT-SIZE: 18px; COLOR: #ffffff; LINE-HEIGHT: 26px; FONT-FAMILY: "黑体"
}
A:link {
	COLOR: #000000; TEXT-DECORATION: none
}
A:visited {
	COLOR: #000000; TEXT-DECORATION: none
}
A:active {
	COLOR: #000000; TEXT-DECORATION: none
}
A:hover {
	COLOR: #ff0000; TEXT-DECORATION: underline
}
A.dh:link {
	FONT-SIZE: 14px; COLOR: #0033cc; TEXT-DECORATION: none
}
A.dh:visited {
	FONT-SIZE: 14px; COLOR: #0033cc; TEXT-DECORATION: none
}
A.dh:hover {
	FONT-SIZE: 14px; COLOR: #0033cc; TEXT-DECORATION: underline
}
A.dh:active {
	FONT-SIZE: 14px; COLOR: #0033cc; TEXT-DECORATION: underline
}
A.dh2:link {
	FONT-SIZE: 14px; COLOR: #0033cc; TEXT-DECORATION: none
}
A.dh2:visited {
	FONT-SIZE: 14px; COLOR: #0033cc; TEXT-DECORATION: none
}
A.dh2:hover {
	FONT-SIZE: 14px; COLOR: #0033cc; TEXT-DECORATION: underline
}
A.dh2:active {
	FONT-SIZE: 14px; COLOR: #0033cc; TEXT-DECORATION: underline
}

.style2 {
	COLOR: #ff0000
}
.contentdiv {
	PADDING-RIGHT: 15px; PADDING-LEFT: 15px; PADDING-BOTTOM: 15px; OVERFLOW: auto; WIDTH: 100%; LINE-HEIGHT: 20px; HEIGHT: 100%; TEXT-ALIGN: left
}
.contentdiv2 {
	OVERFLOW: auto; WIDTH: 100%; HEIGHT: 100%; TEXT-ALIGN: left
}
.muludiv {
	PADDING-RIGHT: 8px; PADDING-LEFT: 10px; FONT-SIZE: 15px; PADDING-BOTTOM: 8px; OVERFLOW: auto; WIDTH: 100%; COLOR: #666666; LINE-HEIGHT: 30px; PADDING-TOP: 12px; HEIGHT: 100%; BACKGROUND-COLOR: #dae9fe; TEXT-ALIGN: left
}
.muludiv2 {
	PADDING-RIGHT: 8px; PADDING-LEFT: 10px; FONT-SIZE: 14px; PADDING-BOTTOM: 8px; OVERFLOW: auto; WIDTH: 100%; COLOR: #666666; LINE-HEIGHT: 25px; PADDING-TOP: 12px; HEIGHT: 100%; TEXT-ALIGN: left
}
.leibie {
	PADDING-RIGHT: 20px; FONT-SIZE: 12px; OVERFLOW: auto; WIDTH: 100%; PADDING-TOP: 10px; HEIGHT: 100%; TEXT-ALIGN: right
}
.tabtb {
	MARGIN-TOP: 8px; MARGIN-BOTTOM: 8px
}.tabtop {
	MARGIN-TOP: 8px;
}
.tit {
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #FFFFFF;
}
A.LI:link {
	FONT-SIZE: 12px; COLOR: #ff0000; TEXT-DECORATION: none
}
A.LI:visited {
	FONT-SIZE: 12px; COLOR: #ff0000; TEXT-DECORATION: none
}
A.LI:hover {
	FONT-SIZE: 12px; COLOR: #ff0000; TEXT-DECORATION: underline
}
A.LI:active {
	FONT-SIZE: 12px; COLOR: #ff0000; TEXT-DECORATION: underline
}
.STYLE6 {
	color: #0000FF;
	font-size: 12px;
}
.txarea {
	font-size: 12px;
	width: 100%;
}
</STYLE>

		<SCRIPT type=text/javascript>

function catalog_switch()
{
	var oTdCatalog = document.getElementById('td_catalog');
	var oPageFile = document.getElementById('page_file');
	var oSwitchButton = document.getElementById('switch_button');
	
	if(oTdCatalog.style.display != 'none')
	{
		oTdCatalog.style.display='none';
		oPageFile.style.display='none';
		oSwitchButton.src='images/img/yincang2.jpg';
	}
	else
	{
		oTdCatalog.style.display='';
		oPageFile.style.display='';
		oSwitchButton.src='images/img/yincang.jpg';
	}
}
			
function frame_exercise_autoheight()
{
	oFrameExercise = document.getElementById("frame_exercise");				
	if (oFrameExercise.contentDocument && oFrameExercise.contentDocument.body.offsetHeight)
		vheight = oFrameExercise.contentDocument.body.offsetHeight; 
	else if (oFrameExercise.Document && oFrameExercise.Document.body.scrollHeight)
		vheight = oFrameExercise.Document.body.scrollHeight;
	else
		vheight = oFrameExercise.Document.body.scrollHeight;
		
	//if(vheight > 200)
	//	vheight = 250;
	oFrameExercise.height = vheight;
	setTimeout ("frame_exercise_autoheight()",100);
}

</SCRIPT>

		<META content="MSHTML 6.00.2900.5880" name=GENERATOR>
	</HEAD>
	<BODY onload='frame_exercise_autoheight();'>
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<TBODY>
				<TR>
					<TD vAlign=top height=68>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD>
										<TABLE height=68 cellSpacing=0 cellPadding=0 width="100%"
											border=0>
											<TBODY>
												<TR>
													<TD vAlign=center align=middle width=200
														background="images/img/bfz_r1_c11.jpg">
														<A
															href="common/misc.php?action=course_conference&course_id=4"></A>
													</TD>
													<TD align=middle background="images/img/bfz_r1_c11.jpg">
														<FONT class=bt>《<s:property value="course.name" />》——考试练习</FONT>
													</TD>
												</TR>
												<TR>
													<TD background="images/img/t-5.jpg" colSpan=2 height=13>
														<!-- <IMG height=13 src="images/img/t-52.jpg" width=180> -->
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
																		<SPAN class=STYLE5>&gt;&gt;&gt; </SPAN><A
																			href="study.action"><SPAN class=STYLE5>返回学员端</SPAN> </A>
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
					</TD>
				</TR>
				<TR>
					<TD vAlign=top>
						<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%"
							border=0>
							<TBODY>
								<TR>
									<TD width=300 vAlign=top height=1 bgColor=#dae9fe
										id="page_file"></TD>
									<TD width=10 rowspan="2" vAlign=middle
										background="images/img/bf_r12_c17.jpg">
										<a href="javascript:catalog_switch();"><img
												id=switch_button src="images/img/yincang.jpg" width="10"
												height="24" border="0"> </a>
									</TD>
									<TD height="100%" rowspan="2" valign="top" bgColor=#dae9fe>
										<DIV class=contentdiv id="div_frame" height=100%
											style="background-color: #FFFFFF;">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="title">
												<tr>
													<td id="page_lecture">
														<iframe name="frame_exercise" id="frame_exercise"
															frameborder="0" marginheight="0" marginwidth="0"
															border="0" scrolling="no" height="500px" width="100%"
															src="study/blank.jsp"></iframe>
													</td>
												</tr>
											</table>
										</DIV>
									</TD>
								</TR>
								<TR>
									<TD id=td_catalog vAlign=top bgColor=#dae9fe>
										<div class=muludiv2 id=div_catalog>
											<ul>											
											<s:iterator value="myPracs" id="pp">
																	<LI style="font-size: 12px; list-style: none;">
																		<i><s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
																			&nbsp;&nbsp;&nbsp;&nbsp;[练习] <A target="_blank"
																			href="practice_paper.action?course.id=<s:property value="#pp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
																				<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> -->
																		</A> 
																		<s:if test="lasttime==null">
																				<img src="images/img/unstudied.gif" width="12"
																					height="13">
																			</s:if> <s:else>
																				<img src="images/img/studied.gif" width="12"
																					height="13">
																			</s:else>
																			</i>
																		<!-- <img src="images/img/studied.gif" width="15" height="13"> -->
																	</LI>
																</s:iterator>
																<s:iterator value="course.myPracs" id="pp">
																	<LI style="font-size: 12px; list-style: none;">
																		<i>
																			[练习]<A target="_blank"
																			href="practice_paper.action?course.id=<s:property value="ppaper.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
																				<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> -->
																		</A> 
																		<s:if test="lasttime==null">
																				<img src="images/img/unstudied.gif" width="12"
																					height="13">
																			</s:if> <s:else>
																				<img src="images/img/studied.gif" width="12"
																					height="13">
																			</s:else>
																			</i>
																		<!-- <img src="images/img/studied.gif" width="15" height="13"> -->
																	</LI>
																</s:iterator>
											</ul>
										</div>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD vAlign=top align=middle height=28>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD width=18>
										<IMG height=28 alt="" src="images/img/bf_r14_c1.jpg" width=18
											border=0 name=bf_r14_c1>
									</TD>
									<!--TD class=unnamed1 align=middle width=267
										background="images/img/bf_r14_c3.jpg">
										<SPAN class="unnamed1 style2 STYLE8"><SPAN
											class=STYLE10> <a target="_blank" href='simpaperlist.action?course.id=${course.id }'>模拟考试</A> ｜ <a href='javascript:void(window.open("quizpaper.action?course.id=${course.id }","course_exam_5","toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no"))'>结业考试</A> 
										</SPAN> </SPAN>
									</TD-->
									<TD width=48>
										<IMG height=28 alt="" src="images/img/bf_r14_c15.jpg" width=48
											border=0 name=bf_r14_c15>
									</TD>
									<TD vAlign="middle" align=right background="images/img/bf_r14_c21.jpg">
										<DIV class=leibie>
											<A class=LI href="">回首页 </A><SPAN class=STYLE5>&gt;&gt;&gt;</SPAN>
										</DIV>
									</TD>
									<TD width=17>
										<IMG height=28 alt="" src="images/img/bf_r14_c29.jpg" width=19
											border=0 name=bf_r14_c29>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</BODY>
</HTML>
