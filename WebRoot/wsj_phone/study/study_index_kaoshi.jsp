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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>个人中心首页</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/manage2.css" />
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<STYLE type="text/css">
.mess {
	background: #99d3fb;
	margin-top: -5px !important;
	margin-top: 10px;
}

.mess td {
	padding: 3px;
	background: #fff;
	font-size: 14px;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE3 {
	font-size: 12px;
	color: #0099FF;
}

.STYLE7 {
	FONT-SIZE: 12px
}

.gqtitle {
	color: #0099FF;
	font-size: 14px;
	margin-top: 6px;
	display: block;
	font-weight: bold
}

.juhuangk {
	border: 1px solid #D4CCFB
}
</STYLE>
		
	</HEAD>
	<BODY style="text-align: left">





		<table width="100%" height="234" border="0" align="center"
			cellpadding="0" cellspacing="0" class="juhuangk">
			<tr>
				<td height="35">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="150" height="35" background="images/hyxxh.gif"
								style="padding-left: 20px;">
								<span class="gqtitle">我的考试</span>
							</td>
							<td align="right" valign="middle" background="images/hyxxh2.gif"
								style="padding-right: 8px;">
								<a href="exam_listbytitle_isPass.action?pN=0&pS=10&isCorrespond=1" target="_blank"
									class=textbg>考试报名</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" valign="top" bgcolor="#F7F9F9">
					<table width="100%" border="0" align="center" cellpadding="1"
						cellspacing="2" >
						<tbody>
							<s:if test="myrooms.size > 0">
								<tr class="table1">
									<td width="20" align="center" valign="center" background="images/bghui001.jpg">&nbsp;
										
									</td>
									<td height="25" align="center" valign="center" background="images/bghui001.jpg">
										<span class="STYLE3">考场名称</span>
									</td>
									<td width="240" align="center" valign="center" background="images/bghui001.jpg" class="STYLE3">
										起止时间
									</td>
									<td align="center" valign="center"background="images/bghui001.jpg" class="STYLE3">&nbsp;
										
									</td>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="myrooms">
									<tr class="table2">
										<td width="20" align="center" valign="center" bgcolor="#F7F9F9">
											<img src="images/switch.png" width="8" height="8" />
										</td>
										<td height="25" align="left" valign="center" bgcolor="#F7F9F9"
											style="PADDING-LEFT: 10px; font-size: 12px;"> 
											<s:if test="examroom.isApplication == 1">
												<SPAN style="color:red">【申请】</SPAN>
											</s:if><s:else>
												<SPAN style="color:gray">【分配】</SPAN>
											</s:else>
											<s:property value="examroom.title" />
										</td>
										<td align="center" valign="center" bgcolor="#F7F9F9" class="STYLE7">
											<s:date name="examroom.begintime" format="yyyy-MM-dd HH:mm" />
											&nbsp;至&nbsp;
											<s:date name="examroom.endtime" format="yyyy-MM-dd HH:mm" />
										</td>
										<td width="80" align="center" valign="middle" bgcolor="#F7F9F9">
										 	<table border="0">
												<tr>
													<td width="20" align="right" valign="middle" bgcolor="#F7F9F9">
														<a target="_parent"
															onClick="return isEroom2('<s:property value="examroom.valid"/>','<s:property value="examroom.svalid"/>','<s:property value="examroom.isnormal"/>','<s:property value="examroom.type"/>');"
															href="quizpaperinit.action?myroom.examroom.id=<s:property value='examroom.id'/>"><img
																src="images/xtb013.png" width="16" height="16" />
														</a>
												  </td>
													<td width="60" valign="middle" bgcolor="#F7F9F9">
														<a target="_parent"
															onClick="return isEroom2('<s:property value="examroom.valid"/>','<s:property value="examroom.svalid"/>','<s:property value="examroom.isnormal"/>','<s:property value="examroom.type"/>');"
															href="quizpaperinit.action?myroom.examroom.id=<s:property value='examroom.id'/>"
															style="font-size: 12px;">进入考场</a>
												  </td>
												</tr>
											</table>
										</td>
									</tr>
								</s:iterator> </tbody>
							</s:if>
							<s:else>
								<tr class="table2">
									<td height="25" colspan="4" align="center" valign="center" bgcolor="#F7F9F9" style="font-size:14px;">
										暂时没有需要参加的考试
									</td>
								</tr>
							</s:else>
						</tbody>
					</table>
					<form action="onloadUcenter_kaoshi.action" name="kaoshi" method="post">
						<s:hidden name="pN" id="pageNow1" />
						<s:hidden name="pS" value="5" />
					</form>
					<script>
						function page(i){
							document.getElementById("pageNow1").value=i;
							kaoshi.submit();
						}
					</script>
					<div style="text-align: center; font-size: 12px;">
						<wysLib:page></wysLib:page>
					</div>
				</td>
			</tr>
		</table>
	
	</body>
</HTML>
