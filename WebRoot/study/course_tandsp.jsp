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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE><s:if test="course_sourse==0">讲师介绍</s:if>
		<s:else>学习计划</s:else>
		</TITLE>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/study.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
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
.style1 {
	FONT-WEIGHT: bold; COLOR: #ff0000
}
</STYLE>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
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
													<FONT class=bt> <s:property value="course.name" />（<s:if test="course_sourse==0">讲师介绍</s:if>
		<s:else>学习计划</s:else>）

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
																	<A href="study.action"><SPAN class=STYLE5>返回我的学习
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
							<td width="300px" valign="middle" height="100%"  id="left">
									<table width="300px" align="center"  cellpadding="0" cellspacing="0">
										<tr>
 <TD align=middle height=50><SPAN class=style1><A href="course_tandsp.action?course.id=${course.id }&course_sourse=0">讲师简介</A> 
                  &gt;&gt;</SPAN></TD></TR>
              <TR>
                <TD align=middle height=50><SPAN class=style1><A href="course_tandsp.action?course.id=${course.id }&course_sourse=1">教学计划</A> 
                  &gt;&gt;</SPAN></TD>
										</tr>
									</table>
							</td>
							<td width="10" valign="middle"
								background="images/img/bf_r12_c17.jpg">
								<a onClick="javascript:catalog_switch();"><img id=switch_button
										src="images/img/yincang.jpg" width="10" height="24" border="0">
								</a>
							</td>
							<td width="100%" valign="top" bgColor=#dae9fe>
								<div style="width: 100%; height: 100%; overflow: auto;">
								<s:if test="course_sourse==0"><s:property value="course.teacherinfo"/></s:if>
								<s:else><s:property value="course.studyplan"/></s:else>
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
	</BODY>
</HTML>
