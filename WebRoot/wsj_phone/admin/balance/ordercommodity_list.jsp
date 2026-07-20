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
		<TITLE>订单货物信息</TITLE>
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
	<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="订单详情列表" /></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>-->
		

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align:left;width:320px;">
			<table width="100%">
				<tr>
				
				<td valign="top">
		<!--<s:form action="order_myorderlistinit.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="320" border="0" cellpadding="0" cellspacing="1"> 
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<tr>
				
				   <td bgcolor="#F8FCFE">  订单号：</td>
				     <td bgcolor="#F8FCFE"><s:property value="order.id"  /></td>
				      <td bgcolor="#F8FCFE">地 址： 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select>  
				           
				           </td>
				 
				    <td bgcolor="#F8FCFE"><s:property value="order.note" /></td>
				    <td bgcolor="#F8FCFE">联系电话</td><td bgcolor="#F8FCFE"><s:property value="order.tel"  /></td>
				</tr>
</table>
			</s:form>-->
						
							<table width="320" border="0" align="left" cellpadding="0"
								cellspacing="1" bgcolor="#D1E4F5">
								<tr>
									<td height="30" align="center" bgcolor="#F8FCFE" >
										货物名称									</td>
									<td height="30" align="center" bgcolor="#F8FCFE" >
									  货物价格（单价）									</td>
									<td height="30" align="center" bgcolor="#F8FCFE" >
									  货物数量									</td>
									<td height="30" align="center" bgcolor="#F8FCFE" >
									  类型								</td>
									<td height="30" align="center" bgcolor="#F8FCFE" >
										总价									</td>
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
			<table width="268" border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5">
			<tr><td width="58" bgcolor="#F8FCFE">总价：</td><td width="207" bgcolor="#F8FCFE"><s:property value="order.sumpeice"  />元</td></tr>
				<tr><td>

				</td></tr>
				</table>
				<a onClick="javascript:history.back(-1);" class=textbg4>返回</a>

		</div>
	
	</body>
</HTML>
										   