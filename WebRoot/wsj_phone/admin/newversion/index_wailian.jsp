<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.NewSystemConfOp"%>
<%@page import="com.sopia.ElConstants"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>外联单位个人中心首页</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/index_newversion.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex_newversion.css"
			rel="stylesheet" />
		<link href="css/style2013.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/113.js"></script>
		<script type="text/javascript" src="js/switch.combo.js"></script>
		<script type="text/javascript">
		function show(){
			 width=600;
			 height=500;
		  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
			 var rv =  window.showModalDialog("showallWeishen.action?x="+Math.random(),null,sFeature);
		}
		</script>
		<style type="text/css">
.STYLE1 {
	font-weight: bold
}

.STYLE2 {
	font-weight: bold;
}

.STYLE3 {
	font-size: 14px;
	font-weight: bold;
	color: #FF0000;
}

.STYLE5 {
	font-weight: bold;
	color: #0000FF;
	font-size: 14px;
}
</style>
	</HEAD>
	<body>
		<table width="1001" height="260" border="0" align="center"
				cellpadding="0" cellspacing="0" class=bg011>
				<tr>
					<td width="270" height="280" align="center" valign="middle"
						background="images/bgheader.jpg">
						<table width="250" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="205" align="left" valign="top" background="images/mybg002.jpg"
									style="padding-left: 25px; padding-top: 55px;"">
									<p>
										+++
										<span class="zc01"><s:property value="elUser.realname" />
										</span> +++
										<br />
										<span class="zp"><s:property
												value="elUser.department.name" /> <br /> <s:property
												value="elUser.role.name" /> <br /> 
										</span>
										<br />
										<SPAN class=STYLE1>*</SPAN> 未读短消息
										<s:property value="message_no" />
										条
									<p>
										<span class="STYLE1">*</span> 已读
										<s:property value="message_yes" />
										条。
										<A href="mess_Rec.action">查看</A><A
											href="listErsWithoutC.action"><BR> </A><SPAN
											class=STYLE1>*</SPAN> 已结业培训班
										<s:property value="class_yes" />
										个
										<br />
							  </td>
							</tr>
						</table>
					</td>
					<td background="images/bgheader.jpg">

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
					<td width="330" valign="top">
						<table width="310" border="0" align="center" cellpadding="0"
							cellspacing="0" style="margin-top: 12px;">
							<tr>
								<td height="40" background="images/insideLeft_titlebg.png"
									style="padding-left: 20px;">
									<table width="95%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td height="40" align="left">
												<span class="STYLE5">最新回复</span>
											</td>
											<td width="100">
												<a href="myTopicList.action">全部回复 &gt;&gt;</a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table width="310" border="0" align="center" cellpadding="0"
							cellspacing="0" class=border1>
							<tr>
								<td height="230" valign="top"
									background="images/contentbg2.png">
									<table width="95%" border="0" align="center" cellpadding="3"
										cellspacing="1">
									<s:if test="topics.size()!=0">
												<s:iterator value="topics">
													<tr>
														
														<td height="35">
															<s:property value="content" />
														</td>
														<td width="100">

															<s:date name="createtime" format="yyyy-MM-dd hh:mm" />
														</td>
														
													</tr>
												</s:iterator>
											</s:if>
											<s:else>
												<tr>	<td>您暂未有回复</td></tr>										
											</s:else>
										
									</table>
								</td>
							</tr>
						</table>
						<p>
							&nbsp;

						</p>
					</td>
					<td valign="top">
						<ul class="kcList clearfix">
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0" style="margin-top: 12px;">
								<tr>
									<td height="270" valign="top" bgcolor="#F8FCFE">
										<table width="100%" border="0" cellpadding="5" cellspacing="1"
											bgcolor="#CFDBE2">
											<tr>
												<td height="40" colspan="2" align="left"
													background="images/bg002.jpg" bgcolor="#E9F5FC"
													style="padding-left: 25px;">
													<span class="STYLE3"><a href="forum_list_byuid.action?pN=0&pS=10">我的咨询</a>----<a href="forumAddInit.action" target="_blank">发布咨询
															&gt;&gt;&gt;</a>
													</span>
												</td>
												<td width="130" align="center" background="images/bg002.jpg"
													bgcolor="#E9F5FC">
													<span class="STYLE2">发布时间</span>
												</td>
												<td width="130" align="center" background="images/bg002.jpg"
													bgcolor="#E9F5FC">
													<span class="STYLE2">浏览数</span>
												</td>
												<td width="60" align="center" background="images/bg002.jpg"
													bgcolor="#E9F5FC">
													<span class="STYLE2">回复数</span>
												</td>
											</tr>
											<s:if test="forums.size()!=0">
												<s:iterator value="forums">
													<tr>
														<td width="30" height="40" align="center" valign="middle"
															bgcolor="#F8FCFE">
															<img src="images/iconred.gif" width="4" height="6" />
														</td>
														<td valign="middle" bgcolor="#F8FCFE">
<!--															<a-->
<!--																href="forum_alterInit.action?forum.id=<s:property value="id"/>"-->
<!--																class="bt001" style="padding-left: 15px;"><s:property value="title" /> </a>-->
																<span class="bt001" style="padding-left: 15px;"><s:property value="title" /></span>
																
														</td>
														<td align="center" bgcolor="#F8FCFE">

															<s:date name="createtime" format="yyyy-MM-dd" />
														</td>
														<td align="center" bgcolor="#F8FCFE">
															<s:property value="receipttime" />
														</td>
														<td align="center" bgcolor="#F8FCFE">
															<s:property value="readtime" />
														</td>
													</tr>
												</s:iterator>
											</s:if>
											<s:else>
												<tr>	<td>您暂未发布咨询</td></tr>										
											</s:else>
										</table>
									</td>
								</tr>
							</table>
							</ul>
					</td>
				</tr>
			</table>




			<table width="1001" border="0" cellpadding="0" cellspacing="0">
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
											<td align="center" valign="middle">
												&nbsp;
											</td>
											<td height="30">
												&nbsp;
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
	</body>
</html>
