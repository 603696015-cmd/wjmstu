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
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>



		<table width="100%" height="245" border="0" align="center"
			cellpadding="0" cellspacing="0" class="juhuangk">
			<tr>
				<td height="35">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="150" height="35" background="images/hyxxh.gif"
								style="padding-left: 20px;">
								<span class="gqtitle">我的培训班</span>
							</td>
							<td align="right" background="images/hyxxh2.gif"
								style="padding-right: 8px;">
								<a href="class_listbytypeid_isPass.action?pN=0&pS=10&isCorrespond=1" target="_blank"
									class=textbg>培训班报名</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" valign="top" bgcolor="#F7F9F9">
					<table width="100%" border="0" align="center" cellpadding="1"
						cellspacing="1" bgcolor="#FFFFFF" >
						<tbody>
							<s:if test="myClasses.size>0">
								<tr class="table1">
									<td width="20" align="center" valign="center" background="images/bghui001.jpg">&nbsp;
										
									</td>
									<td height="25" align="center" valign="center" background="images/bghui001.jpg">
										<span class="STYLE3">培训班名称</span>
									</td>
									<td width="240" align="center" valign="center" background="images/bghui001.jpg" class="STYLE3">
										起止时间
									</td>
									<td align="center" valign="center" background="images/bghui001.jpg" class="STYLE3">&nbsp;
										
									</td>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="myClasses">
									<tr class="table2">
										<td width="20" align="center" valign="center" bgcolor="#F7F9F9">
											<span style="font-size: 12px; color: red;">*</span>
										</td>
										<td height="25" align="left" valign="center" bgcolor="#F7F9F9"
											style="PADDING-LEFT: 10px; font-size: 12px;"> 
											<s:if test="elClass.isApplication == 1">
												<SPAN style="color:red">【申请】</SPAN>
											</s:if><s:else>
												<SPAN style="color:gray">【分配】</SPAN>
											</s:else>
											<s:property value="elClass.name" />
										</td>
										<td align="center" valign="center" bgcolor="#F7F9F9"
											class="STYLE7">
											<s:date name="elClass.starttime" format="yyyy-MM-dd HH:mm" />
											&nbsp;至&nbsp;
											<s:date name="elClass.finishtime" format="yyyy-MM-dd HH:mm" />
										</td>
										<td width="80" align="center" valign="center" bgcolor="#F7F9F9">

											<table border="0">
												<tr>
													<td width="20" align="right" valign="middle">
														<a target="_parent"
															href="myelclass_view.action?elclass.id=<s:property value="elClass.id" />"><img
																src="images/xtb021.gif" width="20" height="20" />
														</a>
													</td>
													<td width="60" valign="middle" bgcolor="#F7F9F9">
														<a target="_parent"
															href="myelclass_view.action?elclass.id=<s:property value="elClass.id" />"
															onclick="return iselClass('<s:property value="elClass.status" />');"
															style="font-size: 12px;">进入学习</a>												  </td>
												</tr>
											</table>
										</td>
									</tr>
								</s:iterator> </tbody>
							</s:if>
							<s:else>
								<tr class="table2">
									<td height="25" colspan="4" align="center" valign="center" bgcolor="#F7F9F9" style="font-size:14px;">
										暂时没有需要参加的培训班
									</td>
								</tr>
							</s:else>
						</tbody>
				  </table>
					<form action="onloadUcenter_pxb.action" name="kaoshi" method="post">
						<s:hidden name="pN" id="pageNow1">
						</s:hidden>
						<s:hidden name="pS" value="5">
						</s:hidden>
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
	</BODY>
</HTML>
