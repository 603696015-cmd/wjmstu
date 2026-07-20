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
		<LINK rel=stylesheet type=text/css 
href="images/shopping/index.css"><LINK rel=stylesheet type=text/css 
href="images/shopping/menu.css">
<LINK rel=stylesheet type=text/css href="images/shopping/book_index.css"><LINK 
rel=stylesheet type=text/css href="images/shopping/nav_style_0903.css">

	<head>
	<title>购物车</title>
	</HEAD>
	<body>
	<%@include file="../../elfrontman/frontheader.jsp"%>
	<table cellSpacing=0 cellPadding=0 width=100% border=0 align="center">
<tr align="center">
<td  align="center">
<div class="gouwu_xb center">
<H2>&nbsp;</H2>
<TABLE cellSpacing=0 cellPadding=0 width=100% border=0>
  <TBODY>
  <TR>   
      	<TD width=150 height=35 align="left">订单号：<EM><s:property value="order.id" /></EM></TD>
    	</TR>
  <TR>
    <TD height=35 align="left">金 额：<EM>￥<s:property value="order.sumpeice" /> </EM></TD>
    </TR>
  <TR>
    <TD height=35 align="left"><EM>
    	<s:if test="order.status==0">已提交</s:if>
    	<s:if test="order.status==2">已支付</s:if>
    	<s:if test="order.status==4">已收货</s:if>
    	</EM></TD>
    </TR>
  <TR>
    <TD height=35 align="left"><A href="#" />查看商品</A></TD>
    </TR>
      </TBODY>
      </TABLE> 
      <p></p>
      <p></p>
      <p></p>
<DIV class="gouwu_xc center">
<H2>
<table cellSpacing=0 cellPadding=0 width=100% border=0>
 <TBODY>
  <TR>
  	<TD width=900 height=31 align="center">支付信息</TD>
  
   </TR>
   </TBODY>
  </table>
</H2>
<p>&nbsp;</p>
<TABLE cellSpacing=0 cellPadding=0 width=100% border=0>
  <TBODY>
  <TR>   
    	<TD width=900  ><P class=onesp_a align="left"><SPAN>您的余额为<EM>￥<s:property value="balance" />元</EM></SPAN></P></TD>
  </TR> 

    <TR> 
    <s:if test="status!=0"> 
    	<TD width=900  ><P class=onesp_a align="left"><SPAN>支付成功<a  href="index.action">返回首页</a></SPAN></P></TD>
    	</s:if>
    	 <s:else>
    	 <TD width=900  ><P class=onesp_a align="left"><SPAN>未支付成功</P></TD>
    	</s:else>
  </TR> 	
    </TBODY>
</TABLE> 
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
</DIV>

</div>
</td>
</tr>
</table>
   <s:include value="../../elfrontman/frontbottom.jsp" />
	
	</body></HTML>
