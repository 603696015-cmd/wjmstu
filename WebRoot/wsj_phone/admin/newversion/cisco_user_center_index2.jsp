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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>个人中心</title>
		<meta name="keywords" content="OA,OA办公系统,OA系统" />
		<meta name="description"
			content="通达OA系统代表了协同OA的先进理念,是中国用户群最广泛的OA软件,协同OA软件行业唯一央企团队研发,多次摘取国内OA软件金奖,拥有300万终端OA用户,十年研发铸就成熟OA产品" />
		<script type="text/javascript" src="js/113.js"></script>
		<script type="text/javascript" src="js/switch.combo.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript">
		function needAllocation(){
			var needAllocation = <s:property value="needAllocation" />;
		   if(!needAllocation){
				if(window.confirm("即将进入报名环节，如果您已有证书，请点击取消!")){
					//跳转报名培训班
					//var classid; 
					//<s:if test="new_cla!=null">
					//	classid= <s:property value="new_cla.elClass.id" />;
				//	</s:if>
				//	<s:else>
				//		classid= 0;
				//	</s:else>
					
			//if(classid == 0){
				//		alert("出现错误，将关闭此页面");
				//		window.close();
				//	}else{
						window.parent.location.href = "changeElclass.action?ctype=2";
				//	}
				}
			//wsj1218修改
				//var width=420;
				//	var height=360;
				//	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0;scroll:no;";
					//window.showModalDialog ("changeElclass.action?ctype=2&x="+Math.random(),null,sFeature);
				//	window.open("changeElclass.action?ctype=2");
			
			
		}
					
		}
		
		function disNopassInfo(classid){
			width=420;
			height=360;
		   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
		}
		
		function alertMsg(){
			alert('还未购买培训班,请先购买');
		}
		
		
		function alertMsg1(){
			alert('培训班考试还未考过');
		}
		//查看证书
		function show_zhengshu(size,ispass,passed){
			var classid = <s:property value="new_cla.elClass.id" />;
			if(size==1 && ispass==1 && passed ){
				width=420;
				height=360;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("mydiploma_view.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
			}else{
				width=420;
				height=360;
			   	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				window.showModalDialog("classNoPassRemack.action?elclass.id="+classid+"&x="+Math.random(),null,sFeature);
			}
		}
		</script>
		<script type="text/javascript">
			function jieyekaoshi(){
				//检查是否可以参加考试
			}
		</script>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex_newversion.css"
			rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />

		<style type="text/css">
		<!--
		.STYLE1 {
			color: #FF0000;
			font-weight: bold;
			font-size: 14px;
		}
		
		.STYLE2 {
			color: #666666;
			font-weight: bold;
		}
		-->
		</style>
	</head>
	<body onload="needAllocation();">
		<div id="container">

			<table width="1000" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-top: 8px; background-color:#F2F3ED;">
				<tr>
					<td align="center" bgcolor="#F2F3ED">
						<img src="images/step.jpg" width="980" height="50" border="0"
								usemap="#Map" />
						<!-- 
						<s:if test="step==1">
							<img src="images/step01.jpg" width="980" height="50" border="0"
								usemap="#Map" />
						</s:if>
						<s:elseif test="step == 2">
							<img src="images/step02.jpg" width="980" height="50" border="0"
								usemap="#Map" />
						</s:elseif>
						<s:elseif test="step == 3">
							<img src="images/step03.jpg" width="980" height="50" border="0"
								usemap="#Map" />
						</s:elseif>
						<s:elseif test="step == 4">
							<img src="images/step04.jpg" width="980" height="50" border="0"
								usemap="#Map" />
						</s:elseif>
						<s:else>
							<img src="images/step01.jpg" width="980" height="50" border="0"
								usemap="#Map" />
						</s:else>
						 -->
					</td>
				</tr>
			</table>



			<table width="1001" border="0" align="center"
				cellpadding="0" cellspacing="0" class=bg011>
				<tr>
					<td width="270" height="155" align="center" valign="top"
						background="images/bgheader.jpg">
						<table width="250" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="145" align="left" valign="top" background="images/mybg002.jpg"
									style="padding-left: 25px; padding-top: 55px;"">
									
										+++
										<span class="zc01">
										<s:property value="elUser.realname" />
								  </span> +++
										<br />
										<span class="zp"><s:property
												value="elUser.department.name" /> <br /> 
										<s:property
												value="elUser.role.name" /> <br /> 
										</span>
										<br />
										<SPAN class=STYLE1>*</SPAN> 未读短消息
										<s:property value="message_no" />
										条
									
										<span class="STYLE1">*</span> 已读
										<s:property value="message_yes" />
										条。
										<A href="mess_Rec.action">查看</A>
							  </td>
							</tr>
						</table>
				  </td>
					<td valign="top" background="images/bgheader.jpg">

						<table width=98% border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td>
									<div class="wrap">
										<div class="slides">
											<newversionLib:newversionCenterMenuDiv></newversionLib:newversionCenterMenuDiv>
										</div>

									</div>


								</td>
							</tr>
						</table>
				  </td>
				</tr>
		  </table>
			
			<div class="main">


				<table width="1000" border="0" align="center" cellpadding="0"
					cellspacing="0" bgcolor="#f2f3ed" class=bg011>
					<tr>
						<td width="270" valign="top">
							<table width="250" border="0" align="center" cellpadding="0"
								cellspacing="0" style="margin-top: 12px;">
								<tr>
									<td height="40" background="images/insideLeft_titlebg.png"
										style="padding-left: 20px;">
										<span class="STYLE1">培训概况</span>
									</td>
								</tr>
							</table>
							<table width="250" border="0" align="center" cellpadding="0"
								cellspacing="0" class=border1>
								<tr>
									<td height="205" valign="top" background="images/contentbg.png">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="30" height="30" align="center">
													<img src="images/001ico.jpg" width="8" height="9" />
												</td>
												<td align="left">
													<div
														style="width: 96%; BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
														<img src="images/jd.gif"
															width="<s:property value="scoreProcess" />%" height="14" />
														<a href="#"></a>
													</div>
												</td>
											</tr>
											<tr>
												<td width="30" height="30" align="center">
													<img src="images/001ico.jpg" width="8" height="9" />
												</td>
												<!-- <td>
													应学
													<span class="STYLE1"><s:property
															value="map.zong_xueshi" /> </span>学时，已学
													<span class="STYLE1"><s:property value="map.process" />%</span>共
													<span class="STYLE1"><s:property
															value="map.learned_xueshi" /> </span>学时
												</td> -->
												<td>
													应得<span class="STYLE1"><s:property
															value="sumScore" /></span>学分,已得<span class="STYLE1">
															<s:property value="hasSumScore" /></span>学分,
													已学<span class="STYLE1"><s:property value="scoreProcess" />%</span>
												</td>
											</tr>
											<tr>

											</tr>
										</table>
										<s:if test="step!=1">
											<s:if test="step == 0">
												<a
												href="javascript:alertMsg();"><img
													src="images/forum.jpg" width="250" height="122" border="0" />
											</a>
											</s:if>
											<s:else>
												<s:if test="isBuyNianjianClass==0">
												<a
												href="myelclass_view.action?type=1&elclass.id=<s:property value="new_cla.elClass.id" />&Return=stclalist"><img
													src="images/forum.jpg" width="250" height="122" border="0" />
											</a>
											</s:if>
											<s:else>
												<a
												href="myelclass_view.action?type=1&elclass.id=<s:property value="nianjian_cla.elClass.id" />&Return=stclalist"><img
													src="images/forum.jpg" width="250" height="122" border="0" />
											</a>
											</s:else>
											</s:else>
											
										</s:if>
										<s:else>
											<a href="javascript:alertMsg();"><img
													src="images/forum.jpg" width="250" height="122" border="0" />
											</a>
										</s:else>
									</td>
								</tr>
							</table>
							<p>&nbsp;
								
							</p>
						</td>
						<td valign="top">

							<ul class="kcList clearfix">
								<s:if test="myClassAll.myCourseB.size()==0&&myClassAll.myCourseX.size()==0"><table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:12px;">
  <tr>
    <td height="40" align="center" background="images/bg002.jpg" bgcolor="#F8FCFE" style="color:red;font-size:18px;font-weight:bold;">&nbsp;</td>
  </tr>
  <tr>
    <td height="210" align="center" bgcolor="#F8FCFE" style="color:red;font-size:18px;font-weight:bold;">暂无课程</td>
  </tr>
</table>
								</s:if>
								<s:else>
									<table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0" style="margin-top: 12px;">
										<tr>
											<td height="270" valign="top" bgcolor="#F8FCFE">
												<table width="100%" border="0" cellpadding="5"
													cellspacing="1" bgcolor="#CFDBE2">
													<tr>
														<td colspan="2" align="left" background="images/bg002.jpg"
															bgcolor="#E9F5FC"
															style="padding-left: 25px; padding-right: 25px;">
															<table width="100%" border="0" cellspacing="0"
																cellpadding="0">
																<tr>
																	<td>
																		<span class="STYLE1">我的课程</span>																	</td>
																</tr>
															</table>														</td>
														<td width="120" height="40" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">进 度</span>														</td>
														<td width="60" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">进入学习</span>														</td>
														<!--<td width="60" align="center"
															background="images/bg002.jpg" bgcolor="#E9F5FC">
															<span class="STYLE2">考试</span>														</td>-->
													</tr>
													<s:iterator value="myClassAll.myCourseB">
														<tr>
															<td width="30" height="50" align="center" valign="middle"
																bgcolor="#F8FCFE">
																<img src="images/iconred.gif" width="4" height="6" />															</td>
															<td valign="middle" bgcolor="#F8FCFE">
																<a href="#" class="zc01" style="padding-left: 15px;"><s:property
																		value="course.name" /> </a>															</td>
															<td width="120" height="40" align="left"
																bgcolor="#F8FCFE">
																<div
																	style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
																	<img src="images/jd.gif"
																		width="<s:property value="process" />%" height="14" />																</div>															</td>
															<td align="center" bgcolor="#F8FCFE">
																<s:if test="isBuyNianjianClass==0">
																	<a
																	href="course_study.action?course.id=<s:property value="course.id" />&coursePage.id=-1&classid=<s:property value="new_cla.elClass.id" />"
																	target="_blank"><img src="images/xtb021.gif"
																		width="28" height="27" /> </a>	
																</s:if>
																<s:else>
																	<a
																	href="course_study.action?course.id=<s:property value="course.id" />&coursePage.id=-1&classid=<s:property value="nianjian_cla.elClass.id" />"
																	target="_blank"><img src="images/xtb021.gif"
																		width="28" height="27" /> </a>	
																</s:else>
																														
															</td>
															
														</tr>
													</s:iterator>
													<s:iterator value="myClassAll.myCourseX">
														<tr>
															<td width="30" height="50" align="center" valign="middle"
																bgcolor="#F8FCFE">
																<img src="images/iconred.gif" width="4" height="6" />															</td>
															<td valign="middle" bgcolor="#F8FCFE">
																<a href="#" class="zc01" style="padding-left: 15px;"><s:property
																		value="course.name" /> </a>															</td>
															<td width="120" height="40" align="left"
																bgcolor="#F8FCFE">
																<div
																	style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
																	<img src="images/jd.gif"
																		width="<s:property value="process" />%" height="14" />																</div>															</td>
															<td align="center" bgcolor="#F8FCFE">
																<s:if test="isBuyNianjianClass==0">
																	<a
																	href="course_study.action?course.id=<s:property value="course.id" />&coursePage.id=-1&classid=<s:property value="new_cla.elClass.id" />"
																	target="_blank"><img src="images/xtb021.gif"
																		width="28" height="27" /> </a>	
																</s:if>
																<s:else>
																	<a
																	href="course_study.action?course.id=<s:property value="course.id" />&coursePage.id=-1&classid=<s:property value="nianjian_cla.elClass.id" />"
																	target="_blank"><img src="images/xtb021.gif"
																		width="28" height="27" /> </a>	
																</s:else>														
															</td>
													  </tr>
													</s:iterator>
										  </table>								
							  			  </td>
										</tr>
									</table>
								</s:else>
							</ul>

						</td>
					</tr>
				</table>



				<table width="1001" border="0" cellpadding="0" cellspacing="0" style="background-color:#F2F3ED;">
					<tr>
						<td valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								style="margin-top: 15px;">
								<tr>
									<td width="170" height="249" align="center">
										<a target="_blank" href="/oa/MYOA2013/"><img
												src="images/news-img3.jpg" width="117" height="164"
												alt="通达OA2011版专区" /> </a>
									</td>
									<td>
										<table width="92%" border="0" align="left" cellpadding="0"
											cellspacing="0">
											<s:iterator value="newMessage">
												<tr>
													<td width="30" align="center" valign="middle">
														<img src="images/iconred.gif" width="4" height="6" />
													</td>
													<td height="30">
														<a
															href="mess_info.action?mess.mess_id=<s:property value='mess_id' />&deleteType=1"
															class="bt003"> <s:if test="mess_title.length()>=20">
																<s:property value='mess_title.substring(0,20)' />...
														</s:if> <s:else>
																<s:property value='mess_title' />
															</s:else> </a>
													</td>
													<td width="100" class="bt001">
														<s:date name="mess_time" format="yyyy年MM月dd日" />
													</td>
												</tr>
											</s:iterator>
											<tr>
												<td align="center" valign="middle">&nbsp;
													
												</td>
												<td height="30">&nbsp;
													
												</td>
												<td width="90">
													<a target="_blank"
														href="newsIndex.action?news.title=null&news.ntype.id=1&ntype.id=1"
														title="查看全部新闻"><img src="images/more.gif" border="0" />
													</a>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
						<td width="400">
							<table width="386" border="0" cellpadding="0" cellspacing="1"
								bgcolor="#FFFFFF" class="border-white"
								style="margin-top: 15px; margin-bottom: 20px;">
								<tr>
									<td width="50%" height="100" align="left" bgcolor="#95c3f4"
										style="padding-left: 15px;">
										<a
											href="newsIndexView.action?news.id=<s:property value='newses[0].id' />"
											class="zp"> <s:if test="newses[0].title.length()>=15">
												<s:property value='newses[0].title.substring(0,15)' />...
													</s:if> <s:else>
												<s:property value='newses[0].title' />
											</s:else> </a>
										<br />
										<s:if
											test="newses[0].nstyle.name!=null||newses[0].nstyle.name!=''">
													栏目：<s:property value='newses[0].nstyle.name' />
										</s:if>
										<br />
										<s:if
											test="newses[0].releasetime!=null||newses[0].releasetime!=''">
													发布：<s:date name="newses[0].releasetime"
												format="yyyy年MM月dd日 HH时mm分"></s:date>
										</s:if>
									</td>
									<td align="left" bgcolor="#cefa7f" style="padding-left: 15px;">
										<a
											href="newsIndexView.action?news.id=<s:property value='newses[1].id' />"
											class="zp"> <s:if test="newses[1].title.length()>=15">
												<s:property value='newses[1].title.substring(0,15)' />...
													</s:if> <s:else>
												<s:property value='newses[1].title' />
											</s:else> </a>
										<br />
										<s:if
											test="newses[0].nstyle.name!=null||newses[0].nstyle.name!=''">
													栏目：<s:property value='newses[0].nstyle.name' />
										</s:if>
										<br />
										<s:if
											test="newses[0].releasetime!=null||newses[0].releasetime!=''">
													发布：<s:date name="newses[0].releasetime"
												format="yyyy年MM月dd日 HH时mm分"></s:date>
										</s:if>
									</td>
								</tr>
								<tr>
									<td width="50%" height="100" align="left" bgcolor="#b9dafb"
										style="padding-left: 15px;">
										<a
											href="newsIndexView.action?news.id=<s:property value='newses[2].id' />"
											class="zp"> <s:if test="newses[2].title.length()>=15">
												<s:property value='newses[2].title.substring(0,15)' />...
													</s:if> <s:else>
												<s:property value='newses[2].title' />
											</s:else> </a>
										<br />
										<s:if
											test="newses[2].nstyle.name!=null||newses[2].nstyle.name!=''">
													栏目：<s:property value='newses[2].nstyle.name' />
										</s:if>
										<br />
										<s:if
											test="newses[2].releasetime!=null||newses[2].releasetime!=''">
													发布：<s:date name="newses[2].releasetime"
												format="yyyy年MM月dd日 HH时mm分"></s:date>
										</s:if>
									</td>
									<td align="left" bgcolor="#d9faa1" style="padding-left: 15px;">
										<a
											href="newsIndexView.action?news.id=<s:property value='newses[3].id' />"
											class="zp"> <s:if test="newses[3].title.length()>=15">
												<s:property value='newses[3].title.substring(0,15)' />...
													</s:if> <s:else>
												<s:property value='newses[3].title' />
											</s:else> </a>
										<br />
										<s:if
											test="newses[3].nstyle.name!=null||newses[3].nstyle.name!=''">
													栏目：<s:property value='newses[3].nstyle.name' />
										</s:if>
										<br />
										<s:if
											test="newses[3].releasetime!=null||newses[3].releasetime!=''">
													发布：<s:date name="newses[3].releasetime"
												format="yyyy年MM月dd日 HH时mm分"></s:date>
										</s:if>
									</td>
								</tr>
								<tr>
									<td width="50%" height="100" align="left" bgcolor="#d4e9fe"
										style="padding-left: 15px;">
										<a
											href="newsIndexView.action?news.id=<s:property value='newses[4].id' />"
											class="zp"> <s:if test="newses[4].title.length()>=15">
												<s:property value='newses[4].title.substring(0,15)' />...
													</s:if> <s:else>
												<s:property value='newses[4].title' />
											</s:else> </a>
										<br />
										<s:if
											test="newses[4].nstyle.name!=null||newses[4].nstyle.name!=''">
													栏目：<s:property value='newses[4].nstyle.name' />
										</s:if>
										<br />
										<s:if
											test="newses[4].releasetime!=null||newses[4].releasetime!=''">
													发布：<s:date name="newses[4].releasetime"
												format="yyyy年MM月dd日 HH时mm分"></s:date>
										</s:if>
									</td>
									<td align="left" bgcolor="#e3fdbe" style="padding-left: 15px;">
										<a
											href="newsIndexView.action?news.id=<s:property value='newses[5].id' />"
											class="zp"> <s:if test="newses[5].title.length()>=15">
												<s:property value='newses[5].title.substring(0,15)' />...
													</s:if> <s:else>
												<s:property value='newses[5].title' />
											</s:else> </a>
										<br />
										<s:if
											test="newses[5].nstyle.name!=null||newses[5].nstyle.name!=''">
													栏目：<s:property value='newses[5].nstyle.name' />
										</s:if>
										<br />
										<s:if
											test="newses[5].releasetime!=null||newses[5].releasetime!=''">
													发布：<s:date name="newses[5].releasetime"
												format="yyyy年MM月dd日 HH时mm分"></s:date>
										</s:if>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table> 



			</div>



		</div>
		
		<map name="Map" id="Map">
			<s:if test="isBuyNianjianClass==0">
				<!-- 选班中心列表页 -->
				<s:if test="isChangeElclass == 0" >
				<area shape="rect" coords="5,3,218,46"
				href="javascript:needAllocation();"
				target="_blank" />
				</s:if>
				<s:else>
					<area shape="rect" coords="5,3,218,46"
					href="javascript:isChange();"
					target="_self" />	
				</s:else>
			
				<!-- 最新一期培训班的学习详情页 -->
				<s:if test="step == 2|| step==3" >
					<area shape="rect" coords="250,2,468,53"
					href="myelclass_view.action?type=1&elclass.id=<s:property value="new_cla.elClass.id" />&Return=stclalist"
					target="_self" />	
				</s:if>
				<s:else>
					<area shape="rect" coords="250,2,468,53"
					href="javascript:alertMsg();"
					target="_self" />	
				</s:else>
				
				<!-- 最新一期证书查看页 -->
				<s:if test="isChangeElclass == 0" >
					<area shape="rect" coords="493,3,711,52"
					href="javascript:alertMsg();"
					target="_self" />	
				</s:if>
				<s:else>
					<s:if test="step == 3">
					<area shape="rect" coords="493,3,711,52"
					href="mydiploma_view.action?elclass.id=<s:property value="new_cla.elClass.id" />"
					target="_blank" />
				</s:if>
				<s:else>
					<area shape="rect" coords="493,3,711,52"
					href="javascript:disNopassInfo('<s:property value="new_cla.elClass.id"/>');"
					 />
				</s:else>
				</s:else>
				
				
				
				<!-- 最新一期培训班购买页 -->
				<area shape="rect" coords="739,2,978,54"
				href="newclass_view2.action?elclass.id=<s:property value="nianjian_cla.elClass.id" />"
				target="_blank" />
				
			</s:if>
				<s:else>
				 	<!-- 选班中心列表页 -->
				<s:if test="isChangeElclass == 0" >
				<area shape="rect" coords="5,3,218,46"
				href="newclass_view2.action?elclass.id=<s:property value='nianjian_cla.elClass.id'/>&ctype=2"
				target="_blank" />
				</s:if>
				<s:else>
					<area shape="rect" coords="5,3,218,46"
					href="javascript:isChange();"
					target="_self" />	
				</s:else>
			
				<!-- 最新一期培训班的学习详情页 -->
				<s:if test="step == 2 || step==3" >
					<area shape="rect" coords="250,2,468,53"
					href="myelclass_view.action?type=1&elclass.id=<s:property value="nianjian_cla.elClass.id" />&Return=stclalist"
					target="_blank" />	
				</s:if>
				<s:else>
					<area shape="rect" coords="250,2,468,53"
					href="javascript:alertMsg();"
					target="_self" />	
				</s:else>
				
<!--				 最新一期证书查看页 -->
<!--				<s:if test="step == 3">-->
<!--					 <s:if test="flag_ispass== 1">-->
<!--												 		-->
<!--						<area shape="rect" coords="493,3,711,52"-->
<!--					href="javascript:alertMsg1();"/>-->
<!--				 		-->
<!--				 </s:if>-->
<!--				 <s:else>-->
<!--				 	<area shape="rect" coords="493,3,711,52"-->
<!--					href="mydiploma_view.action?elclass.id=<s:property value="new_cla.elClass.id" />"-->
<!--					target="_blank" />-->
<!--				 </s:else>-->
<!--					-->
<!--				</s:if>-->
<!--				<s:else>-->
<!--					<area shape="rect" coords="493,3,711,52"-->
<!--					href="javascript:disNopassInfo('<s:property value="new_cla.elClass.id"/>');"-->
<!--					 />-->
<!--				</s:else>-->
				<s:if test="isChangeElclass == 0" >
					<area shape="rect" coords="493,3,711,52"
					href="javascript:alertMsg();"
					target="_self" />	
				</s:if>
				<s:else>
					<s:if test="step == 3">
					<area shape="rect" coords="493,3,711,52"
					href="mydiploma_view.action?elclass.id=<s:property value="nianjian_cla.elClass.id" />"
					target="_blank" />
				</s:if>
				<s:else>
					<area shape="rect" coords="493,3,711,52"
					href="javascript:disNopassInfo('<s:property value="nianjian_cla.elClass.id"/>');"
					 />
				</s:else>
				</s:else>
				
				
				
				<!-- 最新一期培训班购买页 -->
				<area shape="rect" coords="739,2,978,54"
				href="newclass_view2.action?elclass.id=<s:property value="nianjian_cla.elClass.id" />"
				target="_blank" />
				</s:else>
			
				
		</map>
		
		
	</body>
	<script type="text/javascript">
		function isChange(){
			alert("您已报名，不需要重复报名！！！");
		}
	</script>
</html>

