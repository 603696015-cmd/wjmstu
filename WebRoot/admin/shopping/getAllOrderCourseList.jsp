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
					<td valign="top"> 
			<s:if test="myCourses.size==0">您没有课程分配订单</s:if>
			<s:else>
			<s:form action="getMyOrderCourseList.action" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<s:hidden name="corder.id" id="corder.id"></s:hidden>
				<s:hidden name="dstatus" id="dstatus"></s:hidden>
				<s:hidden name="all" ></s:hidden>
				<tr>
				   <td> 订单号：<input name="corder.sid" id="corder.sid"></td>
				     <td>课程名：<input name="corder.course.name" id="corder.course.name"></td>
				      <td>订单状态： 
				         <!-- <select name="corder.sstatus">
				               <option></option>
				               <option value="0">已提交</option>
				               <option value="3">已支付</option>
				           </select> -->  
				           <s:select theme="simple"  headerValue="全部" headerKey=""
									list="#{0:'已提交',3:'已支付'}"
									name="corder.sstatus" value="corder.sstatus" /></td>
				 
				    <td></td>
					<td rowspan="2"><input id="find" name="find" type="submit" value="搜索" class="textbg6"></td>
				</tr>
				<tr>
				   <td align="left">订购时间范围搜索  开始时间：</td>
				     <td><input type="text" size="16" name="stime" onClick="setday(this)" readonly></td>
				       <td>结束时间：</td>
				         <td> <input type="text" size="16" name="otime" onClick="setday(this)" readonly></td>
				</tr>
			</table>
			</s:form>
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th width="5%" height="30" align="center" >
						订单编号</th>
					<th width="10%" align="center" >课程名称</th>
					<th width="5%" height="30" align="center" >
						课程价格					</th>
					<th width="5%" height="30" align="center" >
						学员人数					</th>
					<th width="5%" height="30" align="center" >
						总价					</th> 
					<th width="10%" height="30" align="center" >
						查看学员					</th> 
					<th width="10%" height="30" align="center" >
						课程概况					</th> 
					<th width="10%" height="30" align="center" >
						课程预览					</th> 	
					<th width="10%" height="30" align="center" >
						订单状态					</th> 
					<th width="5%" height="30" align="center" >
						操作					</th> 
					<th width="5%" height="30" align="center" >
						支付					</th> 																					
				</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="myOrders">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="id" /></td>
							<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="course.name" /></td>
						<td align="center" ><s:property value="price" /></td>
							<td  height="30" align="center" >
							<s:property value="count" />		
							</td>
						
						<td height="30" align="center" >
							<s:property value="zprice" />		
							</td>
						<td  height="30" align="center" >
								<a href="getOrderUserList.action?corder.id=<s:property value="id" />" class="textbg6">查看学员</a>	</td> 
						<td  height="30" align="center" >
						<a href=#>课程概况</a>
									</td> 
						<td  height="30" align="center" >
						<a target="_blank" href="course_preview.action?course.id=<s:property value="course.id" />" class="textbg4">预 览</a>
						</td> 
						<td height="30" align="center" >
							<s:if test="status==0">已提交  </s:if><s:else>已发货</s:else>		
						</td>
						<td height="30" align="center" >
								<s:if test="status==0"><a style="cursor:pointer;"  onClick="sh(<s:property value="id" />, 1);"  class="textbg6">删除</a>  </s:if>	
							</td>
						<td height="30" align="center" >
								<s:if test="status==0"><a href=#>去支付</a>  </s:if>	
							</td>	
				</s:iterator></tbody> 
		  </table>
		  <form action="getOrderUserList.action" method="post" name="ddd">
						<s:hidden name="pN" id="pageNow"></s:hidden>
						<s:hidden name="pS"></s:hidden>
						<s:hidden name="stime"></s:hidden>
						<s:hidden name="otime"></s:hidden>
						<s:hidden name="corder.sid"></s:hidden>
						<s:hidden name="corder.course.name"></s:hidden>
						<s:hidden name="corder.sstatus"></s:hidden>
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
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
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
				