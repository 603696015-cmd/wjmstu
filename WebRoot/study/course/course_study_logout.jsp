<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>操作提示</TITLE>

		<base href="<%=basePath%>" target="_self">

		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<LINK href="elfrontimages/style.css" type=text/css rel=stylesheet>
	
		<script type="text/javascript" src="js/message.js"></script>
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 12px
}

.STYLE4 {
	font-size: 12px;
	color: #FFFFFF;
	font-weight: bold;
}

.STYLE12 {
	color: #000000;
	font-weight: bold;
	font-size: 12px;
}

.STYLE15 {
	color: #003366;
	font-weight: bold;
	font-size: 12pt;
}
-->
</style>
<script type="text/javascript">
		function load_(){
			//alert(document.parentWindow.name);
			if(document.parentWindow.name!=''){
				document.getElementById("xx").style.display="none";
			}
		} 
		function fanghui(){
			
			window.history.go(-1);
		}
				</script>
	</HEAD>
	<BODY onLoad="load_();">
		<table width="1000" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" valign="top">
					<table width="1000" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="20" align="center" valign="middle">&nbsp;
								
							</td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<table width="80%" height="80%" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="41" height="70">
											<img src="elfrontimages/t_left_bg.jpg" width="41" height="70" />
										</td>
										<td background="elfrontimages/t02_bg.jpg">&nbsp;										</td>
										<td width="41" height="70">
											<img src="elfrontimages/t_right_bg.jpg" width="41"
												height="70" />
										</td>
									</tr>
									<tr>
										<td background="elfrontimages/t_l_bg.jpg">&nbsp;
											
										</td>
										<td align="center" valign="top"
											background="elfrontimages/bg.jpg">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="50" align="left" valign="middle">
														<table width="200" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="100" height="50" align="center"
																	valign="middle">
																	<img src="elfrontimages/login.jpg" width="41"
																		height="41" />
																</td>
																<td>
																	<span class="STYLE15">提示信息</span>																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td height="2" align="center" valign="middle"
														bgcolor="EC8A1B"></td>
												</tr>
												<tr>
													<td height="200" align="center" valign="middle">
														<h3
															style="text-align: center; padding-top: 40px; width: 100%;color: gray">
															您当前有课程章节正在学习，<br>
														  如需学习新章节请点<a style="font-size:17px;" onClick="return confirm('确定注销当前学习信息？');" href="course_study.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>&coursePage.id=<s:property value="coursePage.id" />&course.isLogout=1"  class="textbg">进入学习</a>并注销当前学习
													  </h3>
														<div
															style="text-align: center; padding-top: 40px; width: 100%">
															<a href="javascript:window.close();" onClick="fanghui();">返回</a>
															<br>
															<a href="javascript:window.close();" id="xx">关闭页面</a>
														</div>
													</td>
												</tr>

											</table>
										</td>
										<td background="elfrontimages/t_r_bg.jpg">&nbsp;
											
										</td>
									</tr>
									<tr>
										<td width="41" height="70">
											<img src="elfrontimages/t02_left_bg.jpg" width="41"
												height="70" />
										</td>
										<td background="elfrontimages/t03_bg.jpg">&nbsp;
											
										</td>
										<td width="41" height="70">
											<img src="elfrontimages/t02_right_bg.jpg" width="41"
												height="70" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="20" align="center" valign="top">&nbsp;
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</BODY>
</HTML>
