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
	<head>
		<title>购物车</title>
	</head>
	<body>
		<%@include file="../../elfrontman/frontheader.jsp"%>
		<table cellSpacing=0 cellPadding=0 width=100% border=0 align="center">
			<tr align="center">
				<td align="center">
					<table width="100%" height="39" border="0" align="center"
						cellpadding="0" cellspacing="0" class=kc_content3>
						<tr>
							<td background="images/shopping/pic_34.gif" class="STYLE10">
								订单信息
							</td>
						</tr>
				  </table>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" class=kc_content2>
						<tr>
							<td valign="top" style="padding: 0px;"></H2>
<s:if test="listC.size==0">
									<div>
										<img src="images/shopping/gwc_ico.gif" align="middle">
										<span style="FONT-SIZE: 20px; FONT-FAMILY: Microsoft Yahei">您的购物车没有商品<br>
<a
											style="COLOR: #ee4e00"
											href="newcourseIndex.action?pN=0&pS=10&containsub=0&course.ctype.id=1">继续购物&gt;&gt;</a>
										</span>
									</div>
							  </s:if>
								<s:if test="successcount==0">
									<P class=onesp_a>
										本次订购没有成功的商品，未生成订单
									</P>
								</s:if>
								<s:if test="successcount!=0">
									<TABLE width=100% border=0 cellPadding=0 cellSpacing=2>
										<TBODY>
											<TR>
												<TD height=35 align="left" bgcolor="#ECF6FD" name="order.id">
													订单号：<EM> <s:property value="order.id" />
													</EM>												</TD>
												<TD height=35 align="left" bgcolor="#ECF6FD" name="order.sumpeice">
												金 额：<EM>￥ <s:property value="order.sumpeice" /> </EM>												</TD>
											</TR>
										</TBODY>
							  </TABLE>
									<s:if test="listsuccess.size!=0">
										<table width=100% bgcolor="#DEF0FC" style="margin-top: 20px;">

											<tr>
												<td colspan="2">
													<P class=onesp_a>
														<SPAN>定购成功的商品信息</SPAN>
													</P>
												</td>
											</tr>

											<tr>
												<td height="30" align="center" bgcolor="#ECF6FD">
													商品名称
												</td>
												<td align="center" bgcolor="#ECF6FD">
													商品类型
												</td>
											</tr>
											<s:iterator value="listsuccess">

												<tr>
													<td height="30" align="center" bgcolor="#ECF6FD">
														<s:property value="commodityName" />
													</td>
													<td align="center" bgcolor="#ECF6FD">
														<s:property value="CommoditytypeName" />
													</td>
												</tr>


											</s:iterator>

									  </table>
									</s:if>
						  </td>
						</tr>
				  </table>
					<table width="100%" height="39" border="0" align="center"
						cellpadding="0" cellspacing="0" class=kc_content3>
						<tr>
							<td background="images/shopping/pic_34.gif" class="STYLE10">
								支付信息
							</td>
						</tr>
				  </table>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" class=kc_content2>
						<tr>
							<td valign="top" style="padding: 15px;">


								<p></p>
								<p></p>
								<p></p>
									<P class=onesp_a id="money_p">
										<SPAN> <EM>我的余额：￥<s:property value="balance" />
										</EM>元,实际应支付<EM><s:property value="order.sumpeice" />
										</EM>元。<br>
 <s:if test="balance< order.sumpeice">您的余额不足请<br>

									 	<a target="_blank" href="chongzhi.action?orderid=<s:property value='order.id' />"><IMG
														src="images/shopping/pic_14.gif">
										  <a target="_blank" href="order_chongzhi.action?orderid=<s:property value='order.id' />"><IMG
														src="images/shopping/pic_14.gif">
			    </a>   
										</s:if> <s:else>
												<a
													href="userPay.action?order.id=<s:property value="order.id" />"><IMG
														src="images/shopping/pic_05.gif">
												</a>
											</s:else>
										</SPAN>
									</P>
								</s:if>
								<s:if test="listC.size!=0">
							</td>
						</tr>
				  </table>


					<table width="100%" height="39" border="0" align="center"
						cellpadding="0" cellspacing="0" class=kc_content3>
						<tr>
							<td background="images/shopping/pic_34.gif" class="STYLE10">
								订单备注
							</td>
						</tr>
				  </table>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" class=kc_content2>
						<tr>
							<td valign="top" style="padding: 0px;">
								<H2>
									<table width=100% border=0 align="center" cellPadding=0
										cellSpacing=0>

										<TBODY>
											<TR>
												<TD width=900 height=39 align="center"
													background="images/shopping/pic_34.gif">
													<P class=onesp_a>
														<SPAN>订单生成信息</SPAN>
													</P>
												</TD>
											</TR>
										</TBODY>
							  </table>

									<TABLE cellSpacing=２ cellPadding=0 width=100% border=0
										align="center">
										<TBODY>
											<TR>
												<TD width=900 height="30" bgcolor="#ECF6FD"
													style="padding-top: 8px">
													<span class="STYLE10">成功信息</span>
												</TD>
											</TR>
											<TR>
												<TD width=900 height="30" bgcolor="#ECF6FD">
													<span class="STYLE10">本此订购中，成功添加<EM> <s:property
																value="successcount" />种</EM>商品</span>
												</TD>
											</TR>
											<TR>
												<TD width=900 height="30" bgcolor="#ECF6FD"
													style="padding-top: 8px">
													<s:if test="guoqicount!=0||cccount!=0||ordercount!=0">　失败信息</s:if>
												</TD>
											</TR>
											<TR>
												<TD width=900 height="30" bgcolor="#ECF6FD">
													<s:if test="guoqicount!=0||cccount!=0||ordercount!=0">　有<EM>
															<s:property value="guoqicount" />
															个
												  </EM>已经过期的培训班</s:if>
												</TD>

											</TR>
											<tr>
											  <TD width=900 height="30" bgcolor="#ECF6FD">
<s:if test="guoqicount!=0||cccount!=0||ordercount!=0">　有<EM>
															<s:property value="cccount" />
												  个</EM>已经拥有的课程或培训班</s:if></TD>

											</tr>
											<tr>
												<TD width=900 height="30" bgcolor="#ECF6FD">
													<s:if test="guoqicount!=0||cccount!=0||ordercount!=0">　有<EM>
															<s:property value="ordercount" />
												  个</EM>课程或培训班已存在其他订单中</s:if>
												</TD>

											</tr>
										</TBODY>
							  </TABLE>
							</td>
						</tr>
				  </table>

					<P class=onesp_a id="shibaiview">
						<a href="">继续购物</a>
					</P>



					</s:if>
					<s:if test="listfalse.size!=0">
						<table width="100%" height="39" border="0" align="center"
							cellpadding="0" cellspacing="0" class=kc_content3>
							<tr>
								<td background="images/shopping/pic_34.gif" class="STYLE10">
									失败商品信息
								</td>
							</tr>
					  </table>
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0" class=kc_content2>
							<tr>
								<td valign="top" style="padding: 0px;">
									<table width=100%>


										<tr>
											<td height="30" align="center" bgcolor="#CEE9FB">
												商品名称
											</td>
											<td align="center" bgcolor="#B9DFF9">
												商品类型
											</td>
										</tr>
										<s:iterator value="listfalse">

											<tr>
												<td height="30" align="center" bgcolor="#ECF6FD" name="commodityName">
													<s:property value="commodityName" />
												</td>
												<td align="center" bgcolor="#ECF6FD">
													<s:property value="CommoditytypeName" />
												</td>
											</tr>
										</s:iterator>
								  </table>
								</td>
							</tr>
					  </table>
					</s:if>
				</td>
			</tr>
	</table>
		<s:include value="../../elfrontman/frontbottom.jsp" />

	
	</body>

</HTML>
