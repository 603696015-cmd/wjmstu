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
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>我的订单管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/system003.css" />
		<link rel="stylesheet" type="text/css" href="wsj_phone/css/manage003.css" />
        <link href="wsj_phone/css/listlable.css" type="text/css" rel="stylesheet">
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="我的订单列表" /></div>
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
		<div style="margin-top: 0px; text-align: center;width:320px;">
			<table width="320">
				<tr>
				
				<td valign="top">
		<s:form action="order_myorderlistinit.action" method="post" name="assignSearch_assignment" theme="simple">	
			<!--<table width="320" border="0" cellpadding="0" cellspacing="1" bgcolor="#D1E4F5"> 
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="orderdeleid" id="btype.id" />
				<tr>
				
				   <td height="30" align="right" bgcolor="#F8FCFE">  订单号：</td>
		      <td bgcolor="#F8FCFE"><s:textfield name="orderid" /></td>
				      <td align="right" bgcolor="#F8FCFE">订单状态 
				         ：<!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select>  
				           
				           </td>
				 
				    <td bgcolor="#F8FCFE"><s:select theme="simple"  headerValue="全部" headerKey="1"
									list="#{0:'已订购',2:'已支付',3:'已发货',4:'已收货'}"
									name="orderstatus" value="orderstatus" /></td>
                                    <td bgcolor="#F8FCFE"></td>
				</tr>
				<tr>
				   <td height="30" align="right" bgcolor="#F8FCFE">订购时间  开始时间：</td>
				     <td bgcolor="#F8FCFE"> 
				   <input width="100" name='start'
						value="<s:date name="start" format="yyyy-MM-dd" />"
						onclick='setday(this)' readonly/>
					 </td>
				       <td align="right" bgcolor="#F8FCFE">结束时间：</td>
				         <td bgcolor="#F8FCFE">
				         	<input width="100" name='end'value="<s:date name="end" format="yyyy-MM-dd"/>" onclick='setday(this)' readonly/>
						</td>
				         <td colspan="2" align="left" bgcolor="#F8FCFE">
				           	 <input id="find" name="find" type="button" value="搜索" onClick="newsSubmit();" >
				         </td>
				</tr>
</table>-->
			</s:form>
						<s:if test="listo.size==0"><h3 align="center" style="margin-top:10px;">没有订单信息</h3></s:if>
						<s:else>
							<table width="320" align="left" cellpadding="2"
								cellspacing="1" bgcolor="#D1E4F5">
								<tr>
									<th height="30" align="center" bgcolor="#F8FCFE" >
										订单号									</th>
									<th height="30" align="center" bgcolor="#F8FCFE" >
									  订单总价									</th>
									<th height="30" align="center" bgcolor="#F8FCFE" >
									  状态									</th>
									<th height="30" align="center" bgcolor="#F8FCFE" >
										详情								</th>
								</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
								<s:iterator value="listo">
									<tr>
									<td height="30" align="left" bgcolor="#F8FCFE" style="padding-left:8px;color:blue;">
											<img src="images/iconred.gif" width="4" height="6" /> &nbsp;&nbsp;&nbsp;<s:property value="id" />
									  </td>
										<td height="30" align="center" bgcolor="#F8FCFE" >
											
											<s:property value="sumpeice" />
										</td>
										<td height="30" align="center" bgcolor="#F8FCFE" >
											<s:property value="Statusname" />
										</td>
										<td height="30" align="center" bgcolor="#F8FCFE" >
											<a style="cursor:pointer;"  href="order_ordercommodity.action?orderid=<s:property value="id"/>"  class="textbg6">查看</a>
											<s:if test="status==0" > 
											<a style="cursor:pointer;"  onClick="sh(<s:property value="id"/>);"  class="textbg6">删除</a> 
											<a  href="order_ordercommodityinfo.action?orderid=<s:property value="id" />" class="textbg6">支付</a>  
											</s:if>
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
			function sh(id){
									if(window.confirm('确定删除？')){
									    document.getElementById("btype.id").value=id;
									    
									 	assignSearch_assignment.action="order_orderdele.action";
									 	assignSearch_assignment.submit();
								 	}
								} 
		</script>
			<wysLib:page_cisco></wysLib:page_cisco>
		</div>
	
	</body>
</HTML>
										   