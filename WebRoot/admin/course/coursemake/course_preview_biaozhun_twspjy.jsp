<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wyslib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBliC "-//W3C//Dtd XHTML 1.0 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>" />
		<title>预览课程--<s:property value="course.name" /></TITLE>
		<link type="text/css" href="css/course_preview.css"  rel="stylesheet" />
		<script type="text/javascript" src="js/cpstudy.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		
		<script type="text/javascript">
			var _cvideo ;
			function myload(){
				_cvideo = new CourseVideo(<s:property value="coursePage.type"/>,"<s:property value="coursePage.page_url_"/>",1);
			 	_cvideo.show("page_file");
			}
			function catalog_switch()
			{
				var otdCatalog = document.getElementById('td_catalog');
				var oPageFile = document.getElementById('page_file');
				var oSwitchButton = document.getElementById('switch_button');
				
				if(otdCatalog.style.display != 'none')
				{
					otdCatalog.style.display='none';
					oPageFile.style.display='none';
					oSwitchButton.src='images/img/yincang2.jpg';
				}
				else
				{
					otdCatalog.style.display='';
					oPageFile.style.display='';
					oSwitchButton.src='images/img/yincang.jpg';
				}
			}
		</script>
	</HEAD>
	<body onload="myload();">
		<table height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tbody>
				<tr>
					<td vAlign=top height=68>
						<table cellSpacing=0 cellPadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td>
										<table height=68 cellSpacing=0 cellPadding=0 width="100%"
											border=0>
											<tbody>
												<tr>
													<td height="55" valign="middle" align="center" width=200
														background="images/img/bfz_r1_c11.jpg">
														&nbsp;
													</td>
													<td height="55" align="center" background="images/img/bfz_r1_c11.jpg">
														<font class=bt><s:property value="course.name" />
														</font>
													</td>
												</tr>
												<tr>
													<td background="images/img/t-5.jpg" colspan=2 height=13>
													</td>
												</TR>
											</tbody>
										</table>
									</td>
									<td width=192>
										<table cellSpacing=0 cellPadding=0 width=192 border=0>
											<tbody>
												<tr>
													<td width=192 height=47><img height=47 src="images/img/t-6.jpg" width=192 /></td>
												</TR>
												<tr>
													<td width=192 background="images/img/t-7.jpg" height=21>
														<table cellSpacing=0 cellPadding=0 width=192 border=0>
															<tbody>
																<tr>
																	<td width=72 height=18>
																		&nbsp;
																	</td>
																	<td style="FONT-SIZE: 12px" vAlign=bottom width=120>
																		<SPAN class=style5>&gt;&gt;&gt; </SPAN>
																		<A onclick="window.close();return false;"
																			href="courseman.action?course.id=<s:property value="course.id"/>"><SPAN
																			class=style5>关闭</SPAN></A>
																	</td>
																</TR>
															</tbody>
														</table>
													</td>
												</TR>
											</tbody>
										</table>
									</td>
								</TR>
							</tbody>
						</table>
					</td>
				</TR>
				<tr>
					<td vAlign=top>
						<table height="100%" cellSpacing=0 cellPadding=0 width="100%"
							border=0>
							<tbody>
								<tr>
									<td width="300" height="250" vAlign=top bgColor=#dae9fe>
									<div style="height:250px;width: 300px;" id="page_file">
									</div>
									</td>
									<td width=10 rowspan="3" vAlign=middle
										background="images/img/bf_r12_c17.jpg">
										<a onclick="javascript:catalog_switch();"><img
												id=switch_button src="images/img/yincang.jpg" width="10"
												height="24" border="0"> </a>
									</td>
									<td height="100%" rowspan="3" valign="top" bgColor=#dae9fe>
										<div class=contentdiv>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="title">
												<tr>
													<td id="page_lecture">
														<div class="cpage_biaozhun_twjy_content">
															<h2 style="width: 100%; text-align: center;">
																<s:property value="coursePage.title" />
															</h2>
															<br>
															${coursePage.page_ }
														</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</TR>
								<tr>
									<td id=td_catalog vAlign=top bgColor=#dae9fe>
										<div class=muludiv2 id=div_catalog
											style="width: 300px; padding-left: 0px; height: 300px;">
											<ul class="cpage_biaozhun_twjy_menu">
												<s:iterator value="coursePages">
													<li style="font-size: 14px;">
														<s:if test="property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
														<A
															href="course_preview_biaozhun.action?coursePage.id=<s:property value="id" />&course.id=<s:property value="course.id"/>">
															<s:property value="title" /> </A>
													</li>
												</s:iterator>
											</ul>
										</div>
									</td>
								</TR>
							</tbody>
						</table>
					</td>
				</TR>
				<tr>
					<td vAlign=top align="center" height=28>
						<table cellSpacing=0 cellPadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td width=18><img height=28 alt="" src="images/img/bf_r14_c1.jpg" width=18
											border=0 name=bf_r14_c1></td>
									<td class=unnamed1 align="center" width=267
										background="images/img/bf_r14_c3.jpg">
										<SPAN class="unnamed1 style2 style8"><SPAN
											class=style10> </SPAN> </SPAN>
									</td>
									<td width=48><img height=28 alt="" src="images/img/bf_r14_c15.jpg" width=48
											border=0 name=bf_r14_c15 /></td>
									<td vAlign="middle" align=right
										background="images/img/bf_r14_c21.jpg">
										<div class=leibie>
											<span class="li" style="width: 100%">&nbsp;</span>
										</div>
									</td>
									<td width=17><img height=28 alt="" src="images/img/bf_r14_c29.jpg" width=19
											border=0 name=bf_r14_c29 /></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>
