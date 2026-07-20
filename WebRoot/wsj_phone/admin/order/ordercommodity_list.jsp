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
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
		
		</script>
	</HEAD>
	<BODY>
	
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>

		<!--<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="订单详情列表" /></div>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="news_addInit.action">新闻公告添加</a>

			</li>
		</ul>-->

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: left;width:320px;">
			<table width="320" border="0" cellpadding="0" cellspacing="00">
				<tr>
				
				<td valign="top">
		<s:form action="order_myorderlistinit.action" method="post" name="assignSearch_assignment" theme="simple">	
			 <!--<table width="320"> 
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<tr>
				
				   <td>  订单号：</td>
				     <td><s:property value="order.id"  /></td>
				      <td>地 址： 
				         <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select>   
				           
				           </td>
				 
				    <td><s:property value="order.note" /></td>
				    <td>联系电话</td><td><s:property value="order.tel"  /></td>
				</tr>
	    </table>-->
			</s:form>
						
							<table width="320" align="left" cellpadding="0"
								cellspacing="1" bgcolor="#D1E4F5">
								<tr>
									<th height="60" align="center" bgcolor="#F8FCFE" >
										名称									</th>
									<th height="60" align="center" bgcolor="#F8FCFE" >
										价格（单价：元）									</th>
									<th height="60" align="center" bgcolor="#F8FCFE" >
										数量									</th>
									<th height="60" align="center" bgcolor="#F8FCFE" >
										类型								</th>
									<th height="60" align="center" bgcolor="#F8FCFE" >
										总价									</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="commodity">
									<tr>
									<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;">
											<s:property value="commodityName" />
									  </td>
										<td height="30" align="center" bgcolor="#F8FCFE" >										
											<s:property value="nowp" />
										</td>
										<td height="30" align="center" bgcolor="#F8FCFE" >
											
											<s:property value="count" />
										</td>
										<td height="30" align="center" bgcolor="#F8FCFE" >
											<s:property value="CommoditytypeName" />
										</td>
										<td height="30" align="center" bgcolor="#F8FCFE" >
											<s:property value="allp" />
										</td>
										
									</tr>
								</s:iterator></tbody>
          </table>
					</td>
				</tr>
				
			</table>
			<table width="320" border="0" cellpadding="0" cellspacing="1">
  <tr><td bgcolor="#F8FCFE">总价：</td><td bgcolor="#F8FCFE"><s:property value="order.sumpeice"  />元</td></tr>
				<tr><td>
				<s:if test="order.status==2">
					<s:if test="statusflag==1"><a  onclick="return window.confirm('确定发货？');" href="order_orderstatusupd.action?orderstatus=3&orderid=<s:property value="order.id" />&statusflag=1">确认发货</a></s:if>
				</s:if>
				<s:if test="order.status==3">
					<s:if test="statusflag !=1"><a  onclick="return window.confirm('确定收货？');" href="order_orderstatusupd.action?orderstatus=4&orderid=<s:property value="order.id"    />" >确认收货</a></s:if>
				</s:if>
				</td></tr>
				</table>
		</div>
	
	</body>
</HTML>
										   