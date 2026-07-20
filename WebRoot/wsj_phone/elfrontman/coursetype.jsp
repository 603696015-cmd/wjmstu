<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<base href="<%=basePath%>" />
		<title>课程中心</title>
		<meta name="keywords"
			content="家具,家具网,中国家具网,家具展,家具品牌,家具品牌排名,家具网站,中国家具,家居网,深圳家具,家具展会,家具十大品牌,深圳家具行业协会,深圳家具报" />
		<meta name="description"
			content="中国家具网依托于深圳家具行业协会官方平台，面向全国家具行业用户。拥有5万家具企业会员和27万买家资源，国内最大的家具行业产品供求、品牌代理、招商加盟平台。" />
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<link rel="shortcut icon" href="/favicon.ico" />
		<link rel="bookmark" href="/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="elfrontimages/images/style.css" />
		<link rel="stylesheet" type="text/css"
			href="http://www.szfa.com/skin/default/index.css" />
	</HEAD>
	<body>
		<!--网上展厅开始-->
		<div class="m">
			<table width="960" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="46">
						<a href="zhanting" target="_blank" title="网上展厅"><img
								style="vertical-align: middle;"
								src="elfrontimages/images/left_title.gif" /> </a>
					</td>
					<td width="914">
						<div>
							<!--B-STAR-->
							<table
								background="http://www.szfa.com/skin/default/index/zhanting/mid_bg.gif"
								height="212" width="914" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<td rowspan="2" width="8">&nbsp;
										
									</td>
									<td height="60">
										<div
											style="width: 850px; height: 56px; padding-left: 10px; margin-top: 4px;">
											<table width="850" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td>
														<div id="nav_li_1">
															<a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=2" target="_blank"><span></span>
															</a>
														</div>
													</td>
													<td>
														<div id="nav_li_2">
															<a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=2" target="_blank"><span></span>
															</a>
														</div>
													</td>
													<td>
														<div id="nav_li_3">
															<a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=2" target="_blank"><span></span>
															</a>
														</div>
													</td>
													<td>
														<div id="nav_li_4">
															<a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=2" target="_blank"><span></span>
															</a>
														</div>
													</td>
													<td>
														<div id="nav_li_5">
															<a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=2" target="_blank"><span></span>
															</a>
														</div>
													</td>
													<td>
														<div id="nav_li_6">
															<a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=2" target="_blank"><span></span>
															</a>
														</div>
													</td>
													<td>
														<div id="nav_li_7">
															<a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=2" target="_blank"><span></span>
															</a>
														</div>
													</td>
													<td width="76">
														<span id="more"><a href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=1"
															target="_blank"><img
																	src="http://www.szfa.com/skin/default/index/zhanting/more.gif" />
														</a> </span>
													</td>
												</tr>
											</table>
										</div>
									</td>
									<td rowspan="2" width="6">
										<img style="vertical-align: middle;"
											src="http://www.szfa.com/skin/default/index/zhanting/right_bg.gif" />
									</td>
								</tr>
								<tr height="141">
									<td>
										<!--C-->
										<div>
											<script src="http://www.szfa.com/file/script/zt_ScrollPic.js"
												type="text/javascript"></script>
											<table width="900" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="28" id="zt_LeftArr">
														<img style="vertical-align: middle; cursor: pointer;"
															src="http://www.szfa.com/skin/default/index/zhanting/box1_left_bg.gif" />
													</td>
													<td
														background="http://www.szfa.com/skin/default/index/zhanting/box1_mid_bg.gif"
														class="zt_photoc">
														<!--D-->
														<ul id="ISL_Cont_2">
															<s:iterator value="zxCourses">
															<li>
																<div>
																	<a href="courseIndexView.action?course.id=<s:property value="id"/>" target="_blank"><img
																			src="<s:property value="mainimg_"/>"
																			border="0" height=85 width=140/> </a>
																</div>
																<span><a href="courseIndexView.action?course.id=<s:property value="id"/>"
																	target="_blank"><s:property value="name"/></a> </span>
															</li>
															</s:iterator>
														</ul>
														<!--D-->
													</td>
													<td width="28" id="zt_RightArr">
														<img style="vertical-align: middle; cursor: pointer;"
															src="http://www.szfa.com/skin/default/index/zhanting/box1_right_bg.gif" />
													</td>
												</tr>
											</table>
											<script language="javascript" type="text/javascript">var scrollPic_01 = new zt_ScrollPic();scrollPic_01.scrollContId = "ISL_Cont_2";scrollPic_01.arrLeftId= "zt_LeftArr";scrollPic_01.arrRightId = "zt_RightArr";scrollPic_01.frameWidth = 844;scrollPic_01.pageWidth= 170;scrollPic_01.speed= 10;scrollPic_01.space= 20;scrollPic_01.autoPlay = true;scrollPic_01.autoPlayTime = 2;scrollPic_01.initialize();</script>
										</div>
										<!--C-->
									</td>
								</tr>
							</table>
							<!--B-EDN-->
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!--网上展厅结束-->

	
	</body>
</html>