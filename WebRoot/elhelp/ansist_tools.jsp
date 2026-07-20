<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<TITLE>课程类别管    理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<style type="text/css">
<!--
.font-botton {
	font-size: 13px;
	color: #666666;
	line-height: 30px;
	font-weight: normal;
}

.font-botton2 {
	font-size: 13px;
	color: #000000;
	line-height: 26px;
	font-weight: normal;
}

.font-biaoti {
	font-size: 12px;
	color: #666666;
	line-height: 22px;
	font-weight: normal;
}

.font-biaoti2 {
	font-size: 12px;
	color: #12419D;
	line-height: 22px;
	font-weight: normal;
}

.font-normal {
	font-size: 12px;
	font-weight: normal;
	line-height: 18px;
	color: #444444;
}

.font-chajian {
	font-size: 12px;
	font-weight: normal;
	line-height: 14px;
	color: #000000;
}

.font-chajian2 {
	font-size: 12px;
	font-weight: normal;
	line-height: 14px;
	color: #ff0000;
}

.font-list {
	font-size: 12px;
	font-weight: normal;
	line-height: 24px;
	color: #444444;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE2 {
	font-size: 12px;
	font-weight: bold;
	line-height: 14px;
	color: #444444;
}
-->
</style>

	</HEAD>
	<BODY>
		<ul class="nav">
			<li>
				<span style="font-weight: bold;"> 辅助工具下载 </span>
			</li>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 40px;">
			<table width="98%" height="880" border="0" cellpadding="0"
				cellspacing="0" bgcolor="#FFFFFF">
				<tr>
					<td valign="top">
						<table width="675" border="0" align="center" cellpadding="0"
							cellspacing="0" bgcolor="#FFFFFF" style="margin: 0 0 8 0">
							<tr>
								<td align="center">
									<br />
									<label>
										<fieldset>
											<legend>
												&nbsp;
												<img src="images/link.gif" align="absmiddle" />
												<span
													style="font-size: 12px; font-weight: bold; color: #666666;">
													<strong> 您当前的位置 ： </strong> <a href="index.action"
													target="_blank">首页</a> &gt; 必装软件</span>&nbsp;
											</legend>
											<br />
											<table border="0" cellspacing="0" cellpadding="0"
												style="font-size: 12px">
												<tr>
													<td align="center">
														<TABLE width=400 border=0 cellPadding=0 cellSpacing=0>
															<TBODY>
																<TR align="center">
																	<TD height="30" colSpan=3 class="font-botton">
																		<STRONG>系统必要控件检测安装&nbsp;&nbsp;</STRONG>
																	</TD>
																</TR>

																<TR align="center">
																	<td align="left" width="72" height="30">
																		★
																	</td>
																	<TD height="25" align="left">
																		<span class="STYLE2">点击安装</span>
																	</TD>
																	<TD height="18">
																		<span class="font-biaoti"><STRONG>是否安装</STRONG>
																		</span>
																	</TD>
																</TR>
																<TR>
																	<td height="30" align="left">
																		<img src="images/soft_pic3.gif" width="72" height="66" />
																	</td>

																	<TD height="25">
																		<a href="/soft/MP10Setup_skycn.exe"
																			class="font-chajian">视频播放器</a>
																	</TD>
																	<TD height="18" align="center">
																		<SPAN class="font-biaoti" id=mediacheck></SPAN>
																	</TD>
																</TR>
																<TR>
																	<td height="45" align="left">
																		<img src="images/soft_pic1.gif" width="90" height="33" />
																	</td>
																	<TD height="25">
																		<a href="/soft/install_flash_player_10_active_x.exe"
																			class="font-chajian">Flash动画插件</a>
																	</TD>
																	<TD height="18" align="center">
																		<SPAN class="font-biaoti" id=flashcheck></SPAN>
																	</TD>
																</TR>
																<TR id="trjvm">
																	<td height="59" align="left">
																		<img src="images/soft_pic2.gif" width="48" height="48" />
																	</td>

																	<TD height="25">
																		<a href="/soft/msjavx86.exe" class="font-chajian">虚拟机MsJVM</a>
																	</TD>
																	<TD height="18" align="center">
																		<SPAN class="font-biaoti" id=recordcheck></SPAN>
																	</TD>
																</TR>


																<TR id="trjre" style="display: none">
																	<td height="59" align="left">
																		<img src="images/soft_pic2.gif" width="48" height="48" />
																	</td>

																	<TD height="25">
																		<a href="/soft/jre-6u20-windows-i586-s.exe"
																			class="font-chajian">64位虚拟机jre(windows 7)</a>
																	</TD>
																	<TD height="18" align="center">
																		<SPAN class="font-biaoti" id=jrecheck></SPAN>
																	</TD>
																</TR>

																<TR>
																	<TD height="18" colSpan=2>
																		<DIV align=center class="font-chajian2">
																			<SPAN id=message></SPAN>
																		</DIV>
																	</TD>
																</TR>
															</TBODY>

														</TABLE>

													</td>
												</tr>
											</table>
										</fieldset>
									</label>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
