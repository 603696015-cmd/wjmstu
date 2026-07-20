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
		<TITLE>用户订单信息</TITLE>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="我的订单列表" /></div>
			</li>
			
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
				
				<td valign="top">
		<s:form action="order_myorderlistinit.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="userid"/>
				<tr>
				
				   <td>  订单号：</td>
				     <td><s:textfield name="orderid" /></td>
				      <td>订单状态 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select> -->  
				           
				           </td>
				 
				    <td><s:select theme="simple"  headerValue="全部" headerKey="1"
									list="#{0:'已订购',2:'已支付',3:'已发货',4:'已收货'}"
									name="orderstatus" value="orderstatus" /></td>
				</tr>
				<tr>
				   <td>订购时间  开始时间:</td>
				     <td> 
					<input name='start'
						value="<s:date name="start" format="yyyy-MM-dd" />"
						onclick='setday(this)' readonly/>
					 </td>
				       <td>结束时间:</td>
				         <td>
				         	<input name='end'value="<s:date name="end" format="yyyy-MM-dd"/>" onclick='setday(this)' readonly/>
						</td>
				         <td colspan="2">
				           	 <input id="find" name="find" type="button" value="搜索" onClick="newsSubmit();" >
				         </td>
				</tr>
			</table>
			</s:form>
						<s:if test="listo.size==0"><h3 align="center" style="margin-top:10px;">没有订单信息</h3></s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
									<th width="260" height="30" align="center" >
										订单号									</th>
									<th width="100" height="30" align="center" >
								    订购时间									</th>
									<th width="90" height="30" align="center" >
										订单总价									</th>
									<th width="80" height="30" align="center" >
										支付时间								</th>
									<th width="30" height="30" align="center" >
										状态									</th>
									<th width="70" height="30" align="center" >
										详情								</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="listo">
									<tr>
									<td height="30" style="padding-left:8px;color:blue;" align="left">
											<s:property value="id" />
									  </td>
										<td height="30" align="center" >
											<s:date name="orderdate" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											
											<s:property value="sumpeice" />
										</td>
										<td height="30" align="center" >
											<s:date name="buydate" format="yyyy-MM-dd HH:mm" />
										</td>
										<td height="30" align="center" >
											<s:property value="Statusname" />
										</td>
										<td width="70" height="30" align="center" >
											<a style="cursor:pointer;"  href="balance_ordercommodity.action?orderid=<s:property value="id"/>"  class="textbg6">查看</a>
									  </td>
									</tr>
								</s:iterator></tbody>
						  </table>
						</s:else>
					</td>
				</tr>
			</table>
			<%-- 
			<form action="newsManage_list.action" method="post" name="nlist">
				<s:hidden name="ntype.id" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
			</form>
			 --%>
			<script type="text/javascript">
			function page(i){
				document.getElementById("pageNow").value=i;
				assignSearch_assignment.submit();
			}
			function newsSubmit(){
				document.getElementById("pageNow").value=0;
				assignSearch_assignment.submit();
			}
		</script>
			<wysLib:page></wysLib:page>
		</div>
	
	</body>
</HTML>
										   