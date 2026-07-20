<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="Description" content="财贸" />
		<meta name="keywords" content="财贸" />
		<title>课程中心--<s:property value="course.ctype.name" />--列表</title>
		<LINK href="elfrontimages/home2.css" rel=stylesheet type="text/css" />
		<style type="text/css">
<!--
.STYLE2 {
	color: #999999
}

body {
	background-color: #a3cfb7;
}

.STYLE5 {
	color: #ed7b0f;
	font-weight: bold;
}

.textbox {
	BORDER-RIGHT: #666666 1px solid;
	BORDER-TOP: #666666 1px solid;
	FONT-SIZE: 9pt;
	BORDER-LEFT: #666666 1px solid;
	COLOR: #666666;
	BORDER-BOTTOM: #666666 1px solid;
	FONT-FAMILY: verdana;
	HEIGHT: 18px;
	BACKGROUND-COLOR: #ffffff
}

.texwhite {
	color: white;
}

.txtwhite {
	color: white;
}

.lh22 {
	line-height: 22px;
}

img {
	border: none;
}

.STYLE8 {
	color: #CC0099
}
-->
</style>
		<script type="text/javascript">
	var imgs = new Array();
	
	function addImgs(obj){
		imgs[imgs.length]=obj;
	}
	function setImgs(){
		for(var i=0;i<imgs.length;i++){
			if(imgs[i].fileSize<=0){
			 imgs[i].src="elfrontimages/coursedimg.jpg";
			}
		}
	}
</script>
	</HEAD>
	<body oncontextmenu="return false" oncopy="return false"
		oncut="return false" onpaste="return false" onload="setImgs();">
		<%@include file="frontheader.jsp"%>
		<table width="970" border="0" align="center" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td>
					<table width="950" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="245" align="left" valign="top">
								<table width="240" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td height="197" align="center" valign="middle"
											background="images/rembg.jpg">
											<s:if test="#session.username!=null">
												<table bgcolor="red" width="240" border="0" align="left"
													cellpadding="0" cellspacing="0">
													<tr>
														<td height="197" align="center" valign="bottom"
															background="elfrontimages/rembg.jpg">
															<TABLE width="98%" border=0 align=center cellPadding=0
																cellSpacing=0>
																<TBODY>
																	<TR>
																		<TD height=25>
																			用户名：
																			<s:property value="#session.username" />
																		</TD>
																	</TR>
																	<TR>
																		<TD height=25>
																			姓 名：
																			<s:property value="#session.realname" />
																		</TD>
																	</TR>
																	<TR>
																		<TD height=25>
																			&nbsp;
																		</TD>
																	</TR>
																	<TR>
																		<TD height=25>
																			<DIV align=center>
																				<img src="elfrontimages/losspass.gif"
																					align=absMiddle />
																				<A href="study.action" target=_parent>个人中心</A>
																				<img src="elfrontimages/mas.gif" align="middle" />
																				<A href="logout.action" target=_parent>退出</A>
																			</DIV>
																		</TD>
																	</TR>
																	<TR>
																		<TD height=25>
																			<DIV align=center>
																				&nbsp; &nbsp; &nbsp;
																			</DIV>
																		</TD>
																	</TR>
																</TBODY>
															</TABLE>
														</td>
													</tr>
												</table>
											</s:if>
											<s:else>
												<table bgcolor="red" width="240" border="0" align="left"
													cellpadding="0" cellspacing="0">
													<tr>
														<td height="197" align="center" valign="bottom"
															background="elfrontimages/rembg.jpg">
															<FORM name=myform action=login.action method=post>
																<TABLE width="98%" border=0 align=center cellPadding=0
																	cellSpacing=0>
																	<TBODY>
																		<TR>
																			<TD height=25>
																				用户名：
																				<INPUT class=textbox id=Username size=16
																					name="elUser.username">
																			</TD>
																		</TR>
																		<TR>
																			<TD height=25>
																				密 码：
																				<INPUT class=textbox type=password size=16
																					name="elUser.password">
																			</TD>
																		</TR>
																		<TR>
																			<TD height=25>
																				验证码：
																				<INPUT class=textbox id=Verifycode onclick=getCode()
																					size=6 name=Verifycode>
																				<IMG height="25" width="63" align="bottom"
																					src="image2.jsp"
																					onClick="this.src='image.jsp?'+Math.random()"
																					title="点击刷新验证码">
																			</TD>
																		</TR>
																		<TR>
																			<TD height=25>
																				<DIV align=center>
																					<img src="elfrontimages/losspass.gif"
																						align=absMiddle />
																					<!-- 
      <A href="#" 
      target=_parent>忘记密码</A> <img src="elfrontimages/mas.gif" 
      align=absMiddle />-->
																					<A href="registerInit.action" target=_parent>新会员注册</A>
																				</DIV>
																			</TD>
																		</TR>
																		<TR>
																			<TD height=25>
																				<DIV align=center>
																					<input class=inputButton
																						onclick=return(CheckForm()) type=submit value=登录
																						name=Submit />
																					<input type="checkbox" name="checkbox"
																						value="checkbox" />
																					永久登录
																				</DIV>
																			</TD>
																		</TR>
																	</TBODY>
																</TABLE>
															</FORM>
														</td>
													</tr>
												</table>
											</s:else>
										</td>
									</tr>
								</table>
								<table width="240" border="0" cellpadding="0" cellspacing="0"
									class="bkgreen" style="margin-top: 10px;">
									<tr>
										<td height="32" valign="middle"
											background="elfrontimages/b32.jpg">
											<table width="100%" height="32" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="80" align="center" valign="middle">
														<p>
															<a class="titgreen14"
																href="newsIndex.action?pN=0&pS=10&containsub=1&news.ntype.id=2">通知公告</a>
														</p>
													</td>
													<td align="right" valign="middle"
														style="padding-right: 20px;">
														<a
															href="newsIndex.action?pN=0&pS=10&containsub=1&news.ntype.id=2">更多&gt;&gt;</a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="130" valign="top">
											<table style="margin-top: 5px;" width="96%" border="0"
												align="center" cellpadding="0" cellspacing="0">
												<s:iterator value="zxNotices">
													<tr>
														<td width="30" align="center">
															<img src="elfrontimages/dian001.gif" width="13"
																height="13" />
														</td>
														<td height="26" align="left" class="grey4b">
															<a target="_blank" title="<s:property value="title"/>"
																href="newsIndexView.action?news.id=<s:property value="id"/>"><s:property
																	value="title" /> </a>
														</td>
													</tr>
												</s:iterator>
											</table>
										</td>
									</tr>
								</table>
								<table style="margin-top: 10px;" width="240" border="0"
									cellpadding="0" cellspacing="0" class="bkgreen">
									<tr>
										<td height="32" valign="middle"
											background="elfrontimages/b32.jpg">
											<table width="100%" height="32" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="110" align="center" valign="middle">
														<p>
															<a href="#" onclick="return false;" class="titgreen14">课程栏目导航</a>
														</p>
													</td>
													<td align="right" valign="middle">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="130" valign="top">
											<wysLib:ctypeTree rootAble="true"
												href="courseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id="></wysLib:ctypeTree>
										</td>
									</tr>
								</table>
								<table style="margin-top: 5px;" width="240" border="0"
									cellpadding="0" cellspacing="0" class="bkgreen">
									<tr>
										<td height="32" valign="middle"
											background="elfrontimages/b32.jpg">
											<table width="100%" height="32" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td width="110" align="center" valign="middle">
														<p>
															<a href="#" onclick="return false;" class="titgreen14">知识栏目导航</a>
														</p>
													</td>
													<td align="right" valign="middle">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="130" valign="top" style="padding: 10px;">
											<wysLib:kltype_center_list></wysLib:kltype_center_list>
										</td>
									</tr>
								</table>
							</td>
							<td valign="top">
								<table width="700" border="0" align="right" cellpadding="0"
									cellspacing="0">
									<tr>
										<td align="center" valign="top">
											<table width="100%" border="0" align="center" cellpadding="0"
												cellspacing="0" background="elfrontimages/tlbg01.png">
												<tr>
													<td height="35" align="left" style="padding-left: 27px;"
														class="white14">
														当前位置：河北省公安厅工卫局 >> 课程列表 >>
														<s:property value="course.ctype.name" />
													</td>
												</tr>
											</table>
											<table width="100%" border="0" align="center" cellpadding="0"
												cellspacing="0" class="bkgreen">
												<tr>
													<td height="662" align="center" valign="top"
														bgcolor="#FBFEF5" style="padding: 10px;">
														<table width="98%" height="40" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tr>
																<td height="42" align="center">
																	<s:if test="zxCourses.size==0">
																		<br />目前没有<s:property value="news.ntype.name" />栏目相关新闻或公<br />
																	</s:if>
																	<div class="listkk" style="width: 680px; height: 5px;"></div>
																	<s:if test="zxCourses.size==0">
																		<br>
																		<br>目前<s:property value="course.ctype.name" />栏目下没有课程<br>
																		<br>
																	</s:if>
																	<s:iterator value="zxCourses" status="zxcSt">
																		<table width="650" height="100" border="0"
																			align="center" cellpadding="0" cellspacing="0"
																			class="listkk">
																			<tr>
																				<td width="155" height="113" rowspan="2"
																					align="left" valign="middle" class="listit">
																					<img
																						src="<s:property  escape="false" value="mainimg"/>"
																						id="cimg_<s:property value="#zxcSt.index"/>"
																						width="141" height="93" />
																				</td>
																				<td height="30" class="listit">
																					<a class="fl"
																						href="courseIndexView.action?course.id=<s:property value="id"/>&coursePage.id=-1"><s:property
																							value="name" /> </a>
																					<span class="list3 fr"><s:date
																							name="createtime" format="yyyy年MM月dd日 HH:mm:ss" />
																					</span>
																				</td>
																			</tr>
																			<tr>
																				<td align="left" valign="top" class="list3"
																					style="text-indent: 2em;">
																					<SCRIPT type="text/javascript">
													obj = document.getElementById("cimg_<s:property value="#zxcSt.index"/>");
													addImgs(obj);
												</SCRIPT>
																					<span class="list3" style="text-indent: 2em;"><s:property
																							value="descString" />
																					</span>
																				</td>
																			</tr>
																		</table>
																	</s:iterator>
																	<wysLib:page></wysLib:page>
																	<form action="courseIndex.action" method="post"
																		name="ddd">
																		<s:hidden name="pN" id="pageNow"></s:hidden>
																		<s:hidden name="pS"></s:hidden>
																		<s:hidden name="course.ctype.id"></s:hidden>
																		<s:hidden name="containsub"></s:hidden>
																	</form>
																	<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
					</script>
																</td>
															</tr>

														</table>
														<table width="98%" height="40" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tr>
																<td align="left" valign="top"
																	style="line-height: 25px; font-size: 12px;">
																	${news.content }
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="frontbottom.jsp"%>
	</body>
</HTML>

