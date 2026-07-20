<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.common.SystemConfOp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<LINK href="css/hz_header.css" type=text/css rel=stylesheet>
	<LINK href="css/pay.css" type=text/css rel=stylesheet>
	<LINK href="elfrontimages/index.css" rel=stylesheet type="text/css">
	<link href="elfrontimages/menu.css" rel=stylesheet type="text/css" />
	<link href="images/dtree.css" type="text/css" rel="stylesheet" />
	<LINK rel=stylesheet type=text/css href="images/shopping/index.css">
	<LINK rel=stylesheet type=text/css href="images/shopping/menu.css">
	<LINK rel=stylesheet type=text/css
		href="images/shopping/book_index.css">
	<LINK rel=stylesheet type=text/css
		href="images/shopping/nav_style_0903.css">
	<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
	<LINK href="css/fudong.css" type=text/css rel=stylesheet>
	<LINK href="css/regist.css" type=text/css rel=stylesheet>
	<STYLE>
.sp {
	CLEAR: both;
	DISPLAY: block;
	MARGIN: 10px auto;
	WIDTH: 948px
}

.box {
	MARGIN-TOP: -125px;
	DISPLAY: none;
	Z-INDEX: 101;
	BACKGROUND: white;
	LEFT: 50%;
	MARGIN-LEFT: -200px;
	WIDTH: 400px;
	POSITION: absolute;
	TOP: 50%;
	HEIGHT: 250px
}

.closebtn {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px;
	RIGHT: 10px;
	CURSOR: pointer;
	COLOR: #333;
	POSITION: absolute;
	TOP: 10px
}

.box LABEL {
	PADDING-RIGHT: 30px;
	DISPLAY: block;
	PADDING-LEFT: 30px;
	PADDING-BOTTOM: 10px;
	PADDING-TOP: 10px
}

.box DIV {
	PADDING-RIGHT: 30px;
	DISPLAY: block;
	PADDING-LEFT: 30px;
	PADDING-BOTTOM: 10px;
	PADDING-TOP: 10px
}

.box INPUT {
	PADDING-RIGHT: 2px;
	PADDING-LEFT: 2px;
	PADDING-BOTTOM: 1px;
	PADDING-TOP: 1px
}

#li_qq {
	DISPLAY: none
}

.STYLE10 {
	color: #006699;
	font-weight: bold;
	font-size: 18px;
}

.kc_content2 {
	BORDER-BOTTOM: #cfdbe2 1px solid;
	BORDER-LEFT: #cfdbe2 1px solid;
	MARGIN-BOTTOM: 11px;
	OVERFLOW: hidden;
	BORDER-TOP: #4789ab 0px solid;
	BORDER-RIGHT: #cfdbe2 1px solid
}

.kc_content3 {
	BORDER-BOTTOM: #cfdbe2 1px solid;
	BORDER-LEFT: #cfdbe2 1px solid;
	MARGIN-BOTTOM: 0px;
	OVERFLOW: hidden;
	BORDER-TOP: #4789ab 1px solid;
	BORDER-RIGHT: #cfdbe2 1px solid
}
</STYLE>
	<SCRIPT type="text/javascript">
		function check(){
			if(document.getElementById("shouuser").value==""){
				alert("请输入收件人姓名");
				return;
			}
			if(document.getElementById("tel").value==""){
				alert("请输入联系电话");
				return;
			}
			if(document.getElementById("shouuser").value==""){
				alert("请输入地址");
				return;
			}
			orderinfo.submit();
		
		
		}
		function check1(){
		var aaa=$('#countinfo').val();
			if(isNaN(aaa)){
				alert("请输入正确的数量");
				return  false;
			}
			if(aaa<1){
				alert("数量不能少于1件");
				return  false;
			}
			if(aaa%1>0){
				alert("请输入正确的数量");
				return  false;
			}
			return true;
		
		}
</SCRIPT>
	<head>
		<title>购物车</title>
	</HEAD>
	<body>
		<%@include file="../../elfrontman/frontheader.jsp"%>

		<table cellSpacing=0 cellPadding=0 width=100% border=0 align="center"
			style="margin-top: 8px;">
			<tr>
				<td>
					<s:if test="listC.size!=0">
						<A title=清空购物车
							href="getShoppingCart.action?orderid=<s:property value="orderid" />&dele=2"><IMG
								height=23 src="images/shopping/pic_03.gif" width=80> </A>
					</s:if>
				</td>
			</tr>

			<tr>
				<td>
					<div class="gouwu_xb center" align="center">
					  <H2 align="center">&nbsp;</H2>
						<s:if test="listC.size==0">
							<div style="MARGIN: 85px 260px 148px">
								<img src="images/shopping/gwc_ico.gif" align="middle">
								<span style="FONT-SIZE: 20px; FONT-FAMILY: Microsoft Yahei">您没有选择任何商品<a
									style="COLOR: #ee4e00"
									href="newcourseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=1">继续选择&gt;&gt;</a>
								</span>
							</div>
						</s:if>
						<s:else>

							<TABLE cellSpacing=2 cellPadding=0 width=100% border=0
								align="center">
								<TBODY>
								<TR>
										  <TD align="center" bgcolor="#ECF6FD">名 称</TD>
										  <TD height=35 align="center" bgcolor="#ECF6FD">价 格</TD>
										  <TD height=35 align="center" bgcolor="#ECF6FD">数 量</TD>
										  <TD align="center" bgcolor="#ECF6FD">操作</TD>
								  </TR>
									<s:iterator value="listC">
										
										<TR>
											<TD bgcolor="#ECF6FD">
												<P align="center">
													<s:property value="commodityName" />
												</P>											</TD>
											<TD height=35 align="center" bgcolor="#ECF6FD">
												<EM>￥ <s:property value="nowp" /> </EM>											</TD>
											<TD height=35 align="center" bgcolor="#ECF6FD">
												<s:if test="commoditytype==4||commoditytype==3">
													<s:form action="commoditycount_upd.action" method="post"
														onsubmit="return check1();">
														<s:hidden name="orderid" />
														<s:hidden name="id"></s:hidden>

														<s:textfield theme="simple" size="8" name="count"
															id="countinfo" />
														<s:submit value="确认修改" theme="simple" />
													</s:form>
												</s:if>
												<s:else>
													<EM><s:property value="count" /> </EM>												</s:else>											</TD>
											<TD align="center" bgcolor="#ECF6FD">
												<A
													href="getShoppingCart.action?orderid=<s:property value="orderid" />&dele=1&commodity.id=<s:property value="id" />">删
													除</A>
												<a
													href="shoppingCartToOrder.action?orderid=<s:property value="id" />">
													付款 </a>											</TD>
										</TR>
									</s:iterator>
									<tr align="center">
										<td colspan="4" align="center">
											<P class=onesp_a>
												<SPAN>应付总金额 <EM>合计：￥<s:property value="zongjia" />元</EM>												</SPAN>											</P>
											<s:if test="status==2">
												<form name="orderinfo" action="shoppingCartToOrder.action"
													method="post">
													<table width="960" height="39" border="0" align="center"
														cellpadding="0" cellspacing="0" class=kc_content3
														style="margin-top: 10px">
														<tr>
															<td background="images/shopping/pic_34.gif"
																class="STYLE10">
																请填写收件人信息															</td>
														</tr>
													</table>
													<table width="100%" border="0" align="center"
														cellpadding="0" cellspacing="0" class=kc_content2>
														<tr>
															<td valign="top" style="padding: 15px;">
																<table width=100%>
																	<tr>
																		<td width="120" height="30" align="center"
																			bgcolor="#ECF6FD">
																			收件人																		</td>
																		<td align="left" bgcolor="#ECF6FD"
																			style="padding-left: 10px;">
																			<s:textfield style="width:200px;height:25px;"
																				id="shouuser" theme="simple"
																				name="order.shoujianren" />																		</td>
																	</tr>

																	<tr>
																		<td height="30" align="center" bgcolor="#ECF6FD">
																			联系电话																		</td>
																		<td align="left" bgcolor="#ECF6FD"
																			style="padding-left: 10px;">
																			<s:textfield style="width:200px;height:25px;"
																				id="tel" theme="simple" name="order.tel" />																		</td>
																	</tr>
																	<tr>
																		<td width="120" height="30" align="center"
																			bgcolor="#ECF6FD">
																			收件地址																		</td>
																		<td align="left" bgcolor="#ECF6FD"
																			style="padding-left: 10px;">
																			<s:textfield style="width:200px;height:25px;"
																				id="note" theme="simple" name="order.note" />																		</td>
																	</tr>
																</table>															</td>
														</tr>
													</table>
												</form>
												<P class=onesp_a align="center">
													<a onClick="check()"><img
															src="images/shopping/pic_27.gif"> </a>												</P>
											</s:if>
											<s:else>
												<P class=onesp_a align="center">
													<!-- 
													<a
														href="shoppingCartToOrder.action"><img
															src="images/shopping/pic_27.gif"> </a>
													 -->
												</P>
											</s:else>										</td>
									</tr>
								</TBODY>
							</TABLE>

						</s:else>
						<DIV class="gouwu_xc center">

						</DIV>
					</div>
				</td>
			</tr>

	</table>
	
	</body>
</HTML>
