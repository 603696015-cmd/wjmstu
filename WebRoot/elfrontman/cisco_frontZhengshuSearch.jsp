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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>苏柏亚云管理平台--论坛首页</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=X-UA-Compatible content=IE=EmulateIE7>
		<META content=name=keywords>
		<META content=name=description>
		<LINK href="elfrontimages/menu.css" rel=stylesheet type="text/css">
		<LINK href="elfrontimages/index.css" type=text/css rel=stylesheet>
		<STYLE type=text/css>
BODY {
	FONT-SIZE: 12px
}

UL {
	LIST-STYLE-TYPE: none
}
</STYLE>
		<LINK href="elfrontimages/book_index.css" type=text/css rel=stylesheet>
		<LINK href="elfrontimages/nav_style_0903.css" type=text/css
			rel=stylesheet>
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 20px;
	color: #a00201;
	font-weight: bold;
}

.STYLE3 {
	color: #0000FF
}

.STYLE4 {
	color: #DFDFDF
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

.STYLE7 {
	font-size: 12px
}

.STYLE10 {
	font-size: 14px;
	font-weight: bold;
	color: #F06920;
}

.textbg {
	background: url("images/textbg.jpg");
	line-height: 25px;
	background-repeat: repeat-x;
	padding-bottom: 0px;
	margin: 5px;
	color: #FFFFFF;
	font-size: 13px;
	font-weight: bold;
	height: 28px;
	width: 95px;
	text-align: center;
	cursor: pointer;
}

.STYLE9 {
	color: #f37800
}

.STYLE11 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	COLOR: #f06920
}
-->
</style>
	</HEAD>
	<BODY onLoad="load_();">
		<%@include file="frontheader.jsp"%>
		<table width="960px" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
			  <td width="10" valign="top">&nbsp;</td>
				<td width="950" valign="top">
					<table style="margin-top: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td width="5" height="5">
									<img height="5" src="images/knowledge/zhao_21.gif" width="5" />
								</td>
								<td width="662" background="images/knowledge/zhao_22.gif"></td>
								<td width="5">
									<img height="5" src="images/knowledge/zhao_23.gif" width="5" />
								</td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td class="renmen2" id="renmen2"
									style="BACKGROUND: url(images/1_015.gif) repeat-x" align="left"
									height="30">
									<table width="96%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td colspan=2>
												<span class="STYLE10">证书</span>
											</td>
										</tr>
									</table>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td align="left" bgcolor="#a2ceea" height="3">
									<img height="3" src="images/knowledge/zhao_29.gif" width="222" />
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td background="images/knowledge/zhao_24.gif"></td>
								<td align="left" valign="top">
									<table style="padding: 8px;" width="100%" height="220"
										border="0" cellpadding="2" cellspacing="2">
										<tr>
											<td width="378" height="25" align="center" bgcolor="#DBEFFB">
												培训班名称											</td>
											<td width="380" align="center" bgcolor="#DBEFFB">
												证书名称											</td>
											<td width="146" align="center" bgcolor="#DBEFFB">
												查看证书											</td>
										</tr>
										<s:if test="myClasses.size==0">
											
											<tr>
												<s:if test="myClass.elClass.id==null">
												<td colspan="6" bgcolor="#F7FBFE">
													暂无学籍证书
												</td>
												</s:if>
												<s:else>
													<td height="164" align="center" bgcolor="#F7FBFE"
													style="padding-left: 8px;">
													<s:property value="myClass.elClass.name" />
											  	</td>
												<td width="380" align="center" bgcolor="#F7FBFE">
													<s:property value="myClass.elClass.certificatename" />
											  </td>
												<td width="146" align="center" bgcolor="#F7FBFE">
													<s:if test="myClass.passed">
														<a target="_blank"
															href="mydiploma_view.action?elclass.id=<s:property value="myClass.elClass.id"/>"
															class=textbg4>查 看</a>
													</s:if>
													<s:else>
														还没能获得证书
													</s:else>
											  </td>
												</s:else>
											</tr>
										</s:if>
										<s:iterator value="myClasses">
											<tr>
												<td height="164" align="center" bgcolor="#F7FBFE"
													style="padding-left: 8px;">
													<s:property value="elClass.name" />
											  	</td>
												<td width="380" align="center" bgcolor="#F7FBFE">
													<s:property value="elClass.certificatename" />
											  </td>
												<td width="146" align="center" bgcolor="#F7FBFE">
													<s:if test="passed">
														<a target="_blank"
															href="mydiploma_view.action?elclass.id=<s:property value="elClass.id"/>"
															class=textbg4>查 看</a>
													</s:if>
													<s:else>
														还没能获得证书
													</s:else>
											  </td>
											</tr>
										</s:iterator>
								  </table>
								</td>
								<td background="images/knowledge/zhao_25.gif"></td>
							</tr>
							<tr>
								<td height="6">
									<img height="5" src="images/knowledge/zhao_26.gif" width="5" />
								</td>
								<td background="images/knowledge/zhao_27.gif"></td>
								<td>
									<img height="5" src="images/knowledge/zhao_28.gif" width="5" />
								</td>
							</tr>
						</tbody>
					</table>
			  </td>
			</tr>
		</table>
		<s:include value="frontbottom.jsp" />

	</BODY>
</HTML>
