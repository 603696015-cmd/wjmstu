<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBliC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/Dtd/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>预览课程--<s:property value="course.name" />
		</TITLE>
		<script type="text/javascript" src="js/course.js"></script>
		<STYLE type=text/css>

.STYLE5 {
	COLOR: #ff0000
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
</STYLE>

<SCRIPT type=text/javascript>
function catalog_switch()
{
	var oTdCatalog = document.getElementById('td_catalog');
	//var oTdProcess = document.getElementById('td_process');
	var oPageFile = document.getElementById('page_file');
	var oSwitchButton = document.getElementById('switch_button');
	
	if(oTdCatalog.style.display != 'none')
	{
		oTdCatalog.style.display='none';
		//oTdProcess.style.display='none';
		oPageFile.style.display='none';
		oSwitchButton.src='images/img/yincang2.jpg';
	}
	else
	{
		oTdCatalog.style.display='';
		//oTdProcess.style.display='';
		oPageFile.style.display='';
		oSwitchButton.src='images/img/yincang.jpg';
	}
}
function myload(){
	var cv = new CourseVideo(<s:property value="coursePage.type"/>,"<s:property value="coursePage.page_url_"/>")
	cv.show("page_file");
}
</SCRIPT>
	</HEAD>
	<BODY onload="myload();" style="overflow: visible">
		<!--<s:if test="course.islink==1"><script>document.location='<s:property value="course.exurl"/>'</script></s:if>
	 
		-->
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
														&nbsp;
													</TD>
													<TD align=middle background="images/img/bfz_r1_c11.jpg">
														<FONT class=bt><s:property value="course.name" />
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
																	<TD width=72 height=18>
																		&nbsp;
																	</TD>
																	<TD style="FONT-SIZE: 12px" vAlign=bottom width=120>
																		<SPAN class=STYLE5>&gt;&gt;&gt; </SPAN>
																		<A
																			onclick="window.close();return false;" href="courseman.action?course.id=<s:property value="course.id"/>"><SPAN
																			class=STYLE5>关闭</SPAN></A>

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
						<script type="text/javascript" src="exam_js/course.js"></script>
						<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%"
							border=0>
							<TBODY>
								<TR>
									<TD width=300 height="250" vAlign=top bgColor=#dae9fe
										id="page_file">

									</TD>
									<TD width=10 rowspan="3" vAlign=middle
										background="images/img/bf_r12_c17.jpg">
										<a onclick="javascript:catalog_switch();"><img
												id=switch_button src="images/img/yincang.jpg" width="10"
												height="24" border="0"> </a>
									</TD>
									<TD height="100%" rowspan="3" valign="top" bgColor=#dae9fe>
										<DIV class=contentdiv>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="title">
												<tr>
													<td id="page_lecture">
														<div style="width: 100%; height: 550px;">
															<h2 style="width: 100%; text-align: center;">
																课程简介
															</h2>
															<br>
															${course.description }
														</div>
													</td>
												</tr>
											</table>
										</DIV>
									</TD>
								</TR>
								<TR>
									<TD id=td_catalog vAlign=top bgColor=#dae9fe>
										<DIV class=muludiv2 id=div_catalog
											style="width: 300px; padding-left: 0px; height: 300px; overflow-y: scroll;">
											<ul style="margin-top: 10px;">

												<s:iterator value="coursePages">
													<LI style="font-size: 14px;">
														<s:if test="property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
														<A
															href="course_preview_zhuhewaibu.action?coursePage.id=<s:property value="id" />&course.id=<s:property value="course.id"/>">
															<s:property value="title" />
														</A>
													</LI>
												</s:iterator>
											</ul>
										</DIV>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD vAlign=top valign=middle height=28>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD width=18>
										<IMG height=28 alt="" src="images/img/bf_r14_c1.jpg" width=18
											border=0 name=bf_r14_c1>
									</TD>
									<TD class=unnamed1 valign=middle width=267
										background="images/img/bf_r14_c3.jpg">
										<SPAN class="unnamed1 style2 STYLE8"><SPAN
											class=STYLE10> </SPAN> </SPAN>
									</TD>
									<TD width=48>
										<IMG height=28 alt="" src="images/img/bf_r14_c15.jpg" width=48
											border=0 name=bf_r14_c15>
									</TD>
									<TD vAlign=center align=right
										background="images/img/bf_r14_c21.jpg">
										<DIV class=leibie>
											<span class="li" style="width: 100%">&nbsp;</span>
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
