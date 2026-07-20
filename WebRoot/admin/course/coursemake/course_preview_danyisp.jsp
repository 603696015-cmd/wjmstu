<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<base href="<%=basePath %>">
		<TITLE>预览课程--<s:property value="course.name"/></TITLE>
		<LINK href="css/bofang2.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
			
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
 	_cvideo = new CourseVideo(2,"<s:property value="course.exurl_"/>",1);
	_cvideo.show("page_file");
	getCpage(<s:property value="coursePages[0].id" />);
 }
</SCRIPT>
	</HEAD>
	<BODY style="overflow: visible" onload="myload();">
	<!--<s:if test="course.islink==1"><script>document.location='<s:property value="course.exurl"/>'</script></s:if>
	 
		--><TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
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
														<FONT class=bt><s:property value="course.name"/></FONT>
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
												height="24" border="0"></a>
									</TD>
									<TD height="100%" rowspan="3" valign="top" bgColor=#dae9fe>
										<DIV class=contentdiv>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="title">
												<tr>
													<td id="page_lecture">
														<div style="width: 100%;height:550;overflow:scroll;" id="cpage_content">
														</div>
													</td>
												</tr>
											</table>
										</DIV>
									</TD>
								</TR>
								<!-- <tr>
									<TD bgColor=#dae9fe height="20px" style="font-size: 12px;" align="center">
										<a href="javascript:void(0)" onclick="fullScreen('page_file');">全屏</a>
									</TD>
								</tr><TR>
									<TD id=td_process height=40 vAlign=middle bgColor=#dae9fe
										align=center style="padding-top: 10px;">
										<div
											style="background-color: #CCCCCC; width: 250px; height: 15px; text-align: left;">
											<div
												style="background-color: #00CC00; width: 0%; height: 15px;"></div>
										</div>
										<div class="STYLE6"
											style="height: 20px; font: 12px; text-align: center; padding-top: 5px;">
											学习进度&nbsp;&nbsp;0％
										</div>
										<iframe name="frame_save_time" id="frame_save_time"
											frameborder="0" marginheight="0" marginwidth="0" border="0"
											scrolling="no" height="0px" width="0px" src=""></iframe>
									</TD>
								</TR> -->
								<TR>
									<TD id=td_catalog vAlign=top bgColor=#dae9fe>
										<DIV class=muludiv2 id=div_catalog style="width: 300px;padding-left:0px;height: 300px;overflow: scroll;">
										<!-- 	<table valign=top align=center border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td height="20" valign="top">
														<img src="img/studied.gif" width="15" height="13">
														<span class="STYLE4">已完成</span>
														<img src="img/studying.gif" width="15" height="13">
														<span class="STYLE4">学习中</span>
														<img src="img/unstudied.gif" width="12" height="13">
														<span class="STYLE4">未学习</span>
													</td>
												</tr>
											</table>
 -->							
 											<ul style="margin-top:10px;">
 											
 											<s:iterator value="coursePages">
											<LI style="font-size: 14px;">
												 <s:if test="property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if><A
													onclick="javascript:getCpage(<s:property value="id" />)" style="cursor: hand;color: blue;">
													 <s:property value="title"/></A>
												<!-- <img src="img/studied.gif" width="15" height="13"> -->
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
					<TD vAlign=top align=middle height=28>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR>
									<TD width=18><IMG height=28 alt="" src="images/img/bf_r14_c1.jpg" width=18
											border=0 name=bf_r14_c1></TD>
									<TD class=unnamed1 align=middle width=267
										background="images/img/bf_r14_c3.jpg">
										 <SPAN class="unnamed1 style2 STYLE8"><SPAN
											class=STYLE10><!--错题集 ｜ <a href='#'>模拟考试</A> ｜ <a href='#'>结业考试</A> -->
										</SPAN>
										</SPAN>
									</TD>
									<TD width=48><IMG height=28 alt="" src="images/img/bf_r14_c15.jpg" width=48
											border=0 name=bf_r14_c15></TD>
									<TD vAlign=center align=right background="images/img/bf_r14_c21.jpg">
										<DIV class=leibie>
										<span class="li" style="width:100%">&nbsp;</span>
										<!--<A class=LI href="#">练习中心</A><SPAN class=STYLE5>&gt;&gt;&gt;</SPAN>
											<A class=LI href="#">做笔记</A><SPAN class=STYLE5>&gt;&gt;&gt;</SPAN>
											<A class=LI href="#">课程公告 </A><SPAN class=STYLE5>&gt;&gt;&gt;</SPAN>
											<A class=LI href="#">课程交流 </A><SPAN class=STYLE5>&gt;&gt;&gt;</SPAN>
											<A class=LI href="#">回首页 </A><SPAN class=STYLE5>&gt;&gt;&gt;</SPAN>  -->	
											<!--<script language="javascript" type="text/javascript"
												src="http://js.users.51.la/2931035.js"></script>
											<noscript>
												<a href="http://www.51.la/?2931035" target="_blank"><img
														alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;"
														src="http://img.users.51.la/2931035.asp"
														style="border: none" />
												</a>
											</noscript>-->
										</DIV>
									</TD>
									<TD width=17><IMG height=28 alt="" src="images/img/bf_r14_c29.jpg" width=19
											border=0 name=bf_r14_c29></TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	 </BODY>
</HTML>
