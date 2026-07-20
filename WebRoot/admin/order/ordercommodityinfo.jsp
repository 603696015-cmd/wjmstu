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
		<TITLE>我的订单信息</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>

		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="订单详情列表" /></div>
			</li>
			<!--<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>-->
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
				
				<td valign="top">
		<s:form action="order_myorderlistinit.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<tr>
				
				   <td>  订单号：</td>
				     <td><s:property value="order.id"  /></td>
				      <td>地 址： 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select> -->  
				           
				           </td>
				 
				    <td><s:property value="order.note" /></td>
				    <td>联系电话</td><td><s:property value="order.tel"  /></td>
				</tr>
			</table>
			</s:form>
						
							<table width="80%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="160" height="60" align="center" >
										货物名称									</th>
									<th width="80" height="60" align="center" >
										货物价格（单价：元）									</th>
									<th width="80" height="60" align="center" >
										货物数量									</th>
									<th width="80" height="60" align="center" >
										类型								</th>
									<th width="80" height="60" align="center" >
										总价									</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="commodity">
									<tr>
									<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="commodityName" />
									  </td>
										<td height="30" align="center" >										
											<s:property value="nowp" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="count" />
										</td>
										<td height="30" align="center" >
											<s:property value="CommoditytypeName" />
										</td>
										<td height="30" align="center" >
											<s:property value="allp" />
										</td>
										
									</tr>
								</s:iterator>
								<tr>
								<td>订单总价：</td>
								<td><s:property value="order.sumpeice"  />元</td>
								<td >我的余额</td>
								<td ><s:property value="balance" /></td>
								<td >
								<s:if test="balance>=order.sumpeice">您可以使用余额支付</s:if><s:else>余额不足</s:else>
								</td>
								</tr>
								
								<tr>
								<td colspan="5">
								 <s:if test="balance>=order.sumpeice">
								 <a onclick="return window.confirm('确定支付${order.sumpeice} 元？');" href="houtaiuserPay.action?order.id=<s:property value="order.id" />" class="textbg">确认支付</a> 
								 </s:if><s:else>
								 <a onclick="javascript:#;" class=textbg>充值</a>
								 </s:else>
				
								<a onclick="javascript:history.back(-1);" class=textbg>返回</a> </td>
								</tr>
								</tbody>
						  </table>
					</td>
				</tr>
				
			</table>
		</div>
	</BODY>
</HTML>
										   