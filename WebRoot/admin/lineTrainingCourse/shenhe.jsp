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
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript">
			function page(i){ 
		 		document.getElementById("pageNow").value=i;
		 		assignSearch_assignment.submit();
		 	}
		
			function do_submit(){
				assignSearch_assignment.submit();
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="我的分配列表" /></div>
			</li>
		</ul>
		<!-- 内容 --> 
		<table width="100%">
				<tr> 
				<td valign="top" width="150px;">
					<wysLib:dep_list_aj rootAble="true"
					href="goto_shenhe.action?sub_department=1&lineTrainingCourse.id=${lineTrainingCourse.id}&department.id=" iname="department.id" ></wysLib:dep_list_aj>	
				</td>
					<td valign="top"> 
			
			<s:form action="goto_shenhe.action?sub_department=1" method="post" name="assignSearch_assignment" theme="simple">	
			<table width="100%"> 
				<input type="hidden" name="lineTrainingCourse.id" value="<s:property value='lineTrainingCourse.id'/>"/>
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<tr>
				
				   <td>姓名<input  name="assign.elUser.realname" /></td>
				   <td>用户名<input  name="assign.elUser.username" /></td>
				   <td></td>
				</tr>
				<tr>
				   <td>
				   	缴费状态
				   	<SELECT  style="WIDTH: 110px" name="assign.pay_status" 
				      onchange="this.value=this.options[this.selectedIndex].value;">
				        <OPTION value=-1 selected>选择缴费状态</OPTION>
			        	<option value=0>
							未缴费 
						</option>
						<option value=1>
							已缴费
						</option>
				    </SELECT>
				   </td>
				   <td colspan="2">
		           	 	<input style="CURSOR: hand"  type="button" onclick="do_submit();" value="查询" >
		           </td>
				</tr>
			</table>
			</s:form>
		<table width="100%">
			<tr>
			
			
			<td valign="top" align="left"> 
			<s:if test="assignList.size==0"><table height="80" align="center" width="150"><tr align="center"><td align="center" > 没有分配的人员</td></tr></table></s:if>
			<s:else>
			<table width="100%" cellpadding="1" cellspacing="1" bgcolor="#EBEBEB">
				<tr>
					<th width="10%" height="30" align="center" >姓名</th>
					<th width="10%" align="center" >用户名</th>
					<th width="10%" align="center" >部门</th>
					<th width="10%" align="center" >性别</th>
					<th width="10%" height="30" align="center" >角色</th>
					<th width="10%" height="30" align="center" >缴费状态</th> 
					<th width="10%" height="30" align="center" >审核状态</th>
					<th width="10%" height="30" align="center" >来源</th>
					<th width="20%" height="30" align="center" >操作</th> 
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()" >
				<s:iterator value="assignList">
					<tr>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="elUser.realname" />
						</td>
						<td height="30" align="center" bgcolor="#FFFFFF" style="color:#CC0099;">
							<s:property value="elUser.username" />
						</td>
						
						<td align="center" ><s:property value="elUser.department.name" /></td>
						<td align="center" ><s:property value="elUser.sex" /></td>
						<td align="center" ><s:property value="elUser.role.name" /></td>
						<td  height="30" align="center" >
							<s:if test="pay_status == 0">
								未缴费
							</s:if>
							<s:else>
								已缴费
							</s:else>
						</td> 
						<td align="center" >
							<s:if test="approval_status == 0">
								未审核
							</s:if>
							<s:else>
								已审核
							</s:else>
						</td>
						<td align="center" >
							<s:if test="allocation_type == 0">
								分配
							</s:if>
							<s:else>
								报名
							</s:else>
						</td>
						<td height="30" align="center" colspan="2">
							<s:if test="pay_status == 0">
								<a href="javascript:option_in_shenhePage(<s:property value='id'/>,'pay_status',1);" class="textbg4">已缴费</a>
							</s:if>
							<s:else>
								<a href="javascript:option_in_shenhePage(<s:property value='id'/>,'pay_status',0);" class="textbg4">未缴费</a>
							</s:else>
							<s:if test="approval_status == 0">
								<a href="javascript:option_in_shenhePage(<s:property value='id'/>,'approval_status',1);" class="textbg4">审核通过</a>
							</s:if>
							<s:else>
								<a href="javascript:option_in_shenhePage(<s:property value='id'/>,'approval_status',0);" class="textbg6">审核不通过</a>
							</s:else>
						</td>
				</s:iterator></tbody> 
		  </table>
		  
		  <wysLib:page></wysLib:page>
		  <form action="option_in_shenhePage.action" name="assign_option" method="post">
		  		<input type="hidden" name="ids" id="ids" />
		  		<input type="hidden" name="assign.id" id="assign_id"/>
		  		<input type="hidden" name="fieldName" id="fieldName"/>
		  		<input type="hidden" name="status" id="status"/>
		  		<input type="hidden" name="lineTrainingCourse.id" value="<s:property value='lineTrainingCourse.id'/>"/>
		  </form>
		  <script type="text/javascript">
		  	function option_in_shenhePage(assign_id,fieldname,status){
		  		document.getElementById("assign_id").value=assign_id;
		  		document.getElementById("fieldName").value=fieldname;
		  		document.getElementById("status").value=status;
		  		assign_option.submit();
		  	}
		  </script>
		  </s:else></td></tr></table> 
		<!-- 内容 -->
	</BODY>
</HTML>
				