<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
	<script type="text/javascript" src="js/cexampaper.js"></script>
			<script type="text/javascript" src="js/calendar.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="分配未订购学员列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程学员列表</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_stat_view.action?course.id=<s:property value="course.id"/>">基本信息</a>
			</li>-->
		</ul>
		<!-- 内容 --> 
		<table width="100%">
				<tr> 
			<s:if test="all==2"><td valign="top" width="150px;">
			<wysLib:dep_list_aj rootAble="true"
			href="myClassOrder_list.action?all=2&deptid=" iname="department.id" ></wysLib:dep_list_aj>	
			</td></s:if>
					<td valign="top"> 
			
			<s:form action="myClassOrder_list.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%" cellpadding="0" cellspacing="1"> 
				<s:hidden name="classOrder.id" id="corder.id" value="0" ></s:hidden>
				<s:hidden name="deptid" ></s:hidden>
				<s:hidden name="dstatus" id="dstatus"></s:hidden>
				<s:hidden name="all" ></s:hidden>
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<tr>
				
				   <td width="230" bgcolor="#F4F4F4"> 订单号：
			         <s:textfield name="classOrder.sid" /></td>
				     <td width="230" bgcolor="#F4F4F4">课程名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		               <s:textfield name="classOrder.elClass.name" /></td>
				      <td width="70" bgcolor="#F4F4F4">订单状态： 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select> -->	              </td>
				 
				    <td rowspan="2" bgcolor="#F4F4F4"><input id="find" name="find" type="button" onClick="newsSubmit()" value="搜索" class="textbg6"></td>
				</tr>
				<tr>
				   <td bgcolor="#F4F4F4">订购时间范围搜索：</td>
				     <td bgcolor="#F4F4F4"> 开始时间：
					<input name='stime'
						value="<s:date name="stime" format="yyyy-MM-dd" />"
						onclick='setday(this)' readonly/>				  </td>
				       <td width="70" align="left" bgcolor="#F4F4F4">结束时间：<input name='otime'value="<s:date name="otime" format="yyyy-MM-dd"/>" onclick='setday(this)' readonly/></td>
	            </tr>
			</table>
			</s:form>
			<s:if test="classOrders.size==0"><table width="100%" height="80" align="center">
			  <tr align="center"><td align="center" bgcolor="#F8FCFE" > 没有课程分配订单</td>
			  </tr></table></s:if>
			<s:else>
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
				
					<th width="8%" height="30" align="center" >
						订单编号 </th>
					<th width="10%" align="center" >培训班名称</th>
					<th width="8%" height="30" align="center" >
						课程数量					</th>
					<th width="8%" height="30" align="center" >
						培训班价格					</th>
						<th width="8%" height="30" align="center" >
						订单总价					</th>
					
					<th width="10%" height="30" align="center" >
						查看学员					</th> 
						<th width="10%" height="30" align="center" >
						订购者					</th> 
						<th width="10%" height="30" align="center" >
						订购者部门					</th> 
						<th width="10%" height="30" align="center" >
						订购时间					</th> 
					<th width="10%" height="30" align="center" >
						培训班概况					</th> 
					
					<th width="10%" height="30" align="center" >
						订单详情					</th> 
					<th width="10%" height="30" align="center" >
						订单状态					</th> 
					<th width="5%" height="30" align="center" >
						操作					</th> 
					<s:if test="all!=1"><th width="5%" height="30" align="center" >
						支付					</th> </s:if>																					
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="classOrders">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="id" /></td>
							<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="elClass.name" /></td>
						
							<td  height="30" align="center" >
							<s:property value="CountCourse" />		
							</td>
						
						<td height="30" align="center" >
							<s:property value="price" />		
							</td>
						<td height="30" align="center" >
							<s:property value="zprice" />		
							</td>
						
						<td  height="30" align="center" >
								<a href="getclassuserinfo_list.action?orderID=<s:property value="id" />" class="textbg6">查看学员</a>	</td> 
						
						<td height="30" align="center" >
							<s:property value="user.realname" />		
							</td>
						<td height="30" align="center" >
							<s:property value="user.department.name" />		
							</td>
						<td  height="30" align="center" >
						<a target="_blank" href="newclass_view2.action?elclass.id=<s:property value="elClass.id" />&ctype=2">培训班概况</a>
									</td> 
						<td  height="30" align="center" >
						<a href="getclass_order_courseprice.action?elClass.id=<s:property value="elClass.id" />">查看</a>
									</td> 
						
							<td height="30" align="center" >
							<s:date name="odate" format="yyyy-MM-dd hh:mm"/>	
							</td>
						<td height="30" align="center" >
							<s:if test="status==0">已提交  </s:if><s:else>已收货</s:else>		
						</td>
						<td height="30" align="center" >
								<s:if test="status==0"><a style="cursor:pointer;"  onClick="sh(<s:property value="id" />, 1);"  class="textbg6">删除</a>  </s:if>	
							</td>
						<s:if test="all != 1"><td height="30" align="center" >
								<s:if test="status==0"><a  href="getclassorderinfobyid.action?classorcourse=1&orderID=<s:property value="id" />">去支付</a>  </s:if>	
							</td>	</s:if>
				</s:iterator></tbody> 
		  </table>
		  <form action="myClassOrder_list.action" method="post" name="ddd">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="stime"></s:hidden>
						<s:hidden name="otime"></s:hidden>
						<s:hidden name="classOrder.sid"></s:hidden>
						<s:hidden name="classOrder.elClass.name"></s:hidden>
						<s:hidden name="classOrder.sstatus"></s:hidden>
						<s:hidden name="all" ></s:hidden>

			  </form>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="200">&nbsp;
						
					</td>
					<td width="140">

					</td>
					<td width="400">
						

					</td>
					<td>&nbsp;
						
					</td>
				</tr>
			</table>
					<script type="text/javascript">
					function newsSubmit(){
				document.getElementById("pageNow").value=0;
				assignSearch_assignment.submit();
			}
						function page(i){
							document.getElementById("pageNow").value=i;
							assignSearch_assignment.submit();
						}
						function toexcel(){
							if(document.getElementById("corder.id").value==""){
								document.getElementById("corder.id").value=-1;
							}
							assignSearch_assignment.submit();
						}
						function sh(id,status){
								    document.getElementById("corder.id").value=id;
								    document.getElementById("dstatus").value=status; 
								 	if(status==1 && window.confirm("确定要删除该订单？")){
								 		document.forms.assignSearch_assignment.submit();
								 	}  
								}
		 		   </script>
		  <wysLib:page></wysLib:page>
		  </s:else></td></tr></table> 
		<!-- 内容 -->
	</BODY>
</HTML>
				