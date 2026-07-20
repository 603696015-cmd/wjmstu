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
		<TITLE>分配学员</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/message.js"></script>
		
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
			<!--<li>
					 分配学员
				</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<form action="course_assigntoUsersInit.action" method="post" name="course_assignment">
					<s:hidden name="deptid" />
					<s:hidden name="course.id" />
					<s:hidden name="elUser.sex" />
					<s:hidden name="elUser.realname" />
					<s:hidden name="elUser.username" />
					<s:hidden name="elUser.isAssign" />
				 	<s:hidden name="userids" id="userids"> </s:hidden>	 
				 	
				 	<table width="100%"> 
				<tr>
				    <td height="20px">课程名称：</td>
				    <td><s:property value="course.name" /></td>
				    <td>创建者：</td>
				    <td><s:property value="course.creater.realname" /></td>
			   	    <td>创建时间：</td>
			   	    <!-- <s:set name="str" value="#course.createtime"></s:set> -->
				    <td><s:property value="course.createtime" /></td>
				    <td>类别：</td>
			 <td><s:property value="course.ctype.name" /></td>
				</tr>
				<tr>
					<td>时长：</td>
					<td><s:property value="course.during" /></td> 
					<td>价格：</td> 
					<td ><s:property value="course.price.coursenowPrice" /></td>
					<td></td>
					<td></td>
				</tr>
				<!-- 	<tr><td>选择考场：</td><td colspan="7"><s:select name="examRoom.id" theme="simple" list="examRooms" listKey="id" listValue="title" /></td></tr>	 -->
			</table>
	    </form>	
		<s:form action="shoping_assigntoUsersInit.action" method="post" name="assignSearch_assignment" theme="simple">
			<s:hidden name="course.id" />
				<s:hidden name="examRoom.id" />
				<s:hidden name="examPaper.id" />		
			<table width="100%"> 
				
				<tr>
				   <td> 姓名：<input name="elUser.realname" id="elUser.realname"></td>
				     <td>账号：<input name="elUser.username" id="elUser.username"></td>
				      <td>是否已分配 
				           <select name="elUser.isAssign">
				               <option></option>
				               <option value="0">是</option>
				               <option value="1">否</option>
				           </select></td>
				       <td>性别： 
				        <select name="elUser.sex">
					      <option value=""></option>
				       	  <option value="男">男</option>
				          <option value="女">女</option>
						</select>
				       </td> 
				    <td></td>
				</tr>
				<tr>
				   <td>年龄段开始时间:</td>
				     <td><input type="text" size="16" name="starttime" onClick="setday(this)" readonly></td>
				       <td>年龄段结束时间:</td>
				         <td> <input type="text" size="16" name="endtime" onClick="setday(this)" readonly></td>
				         <td colspan="2">
				           	 <input id="find" name="find" type="submit" value="搜索">
				         </td>
				</tr>
			</table>
			</s:form>
		<table width="100%">
			<tr>
			<td valign="top" width="150px;">
			<%
			   Course course=(Course)request.getAttribute("course");
			   String url="shoping_assigntoUsersInit.action?course.id="+course.getId()+"&deptid=";
			 %>
			
						<wysLib:dep_list_aj rootAble="true"
							href="<%=url%>" iname="department.id" ></wysLib:dep_list_aj>	
			</td>
			<td valign="top" align="left"> 
		<s:if test="elusers.size==0">当前还没有分配学员</s:if>
			<s:else>
				<table width="90%" align="center" cellpadding="1" cellspacing="1"
					bgcolor="#EBEBEB">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
						</td>
					    <td height="30" align="center" >
						姓名
						</td>
						 <td height="30" align="center" >
						性别
						</td>
					   <td height="30" align="center" >
							账号
						</td>
					    <td height="30" align="center" >
							部门
						</td>
						<td height="30" align="center" >
							<wysLib:BasetName btid="1" />
						</td>
						
						<td height="30" align="center" >
							年龄
						</td>
						<td height="30" align="center" >
							角色
						</td>
						<td height="30" align="center" >
							分配
						</td>
					</tr>
					<s:if test="elusers.size==0">
						<TR>
							<TD align="center" colspan="4">
								当前还没有分配学员
							</TD>
						</TR>
					</s:if>
					<s:else>
						<s:iterator value="elusers">
							<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							     <input type="checkbox" value="<s:property value="id"/>:<s:property value="isAssign"/>" name="id"> 
						       </td>
							    <td height="30" align="center" >
							      <s:property value="realname" />
						       </td>
						         <td height="30" align="center" >
							      <s:property value="sex" />
						       </td>
                                <td height="30" align="center" >
									<s:property value="username" />
								</td>
                                <td height="30" align="center" >
									<s:property value="department.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="jingzhong_" />
								</td>
								<td height="30" align="center" >
									<s:property value="age" />
								</td>
								<td height="30" align="center" >
									<s:property value="role.name" />
								</td>
								<td height="30" align="center" >
									<s:property value="isAssign" />
								</td>
								
							</tr>
						</s:iterator>
					</s:else>
				</table>
			</s:else></td></tr></table><wysLib:page></wysLib:page>
			<script type="text/javascript">
						function select_All(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= true;
							}
						}
						function select_Fan(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= !cks[i].checked;
							}
						}
						function select_Bux(){
							var cks= document.getElementsByName("id");
							for(var i = 0 ; i < cks.length; i++){
								cks[i].checked= false;
							}
						}
						
						function checkTime(){
								if($("#r_start").val()==''){
									alert("请填写课程开始时间");
									$("#r_start").focus();
									return false;
								}
								if($("#r_end").val()==''){
									alert("请填写课程结束时间");
									$("#r_end").focus();
									return false;
								}
								if($("#r_start").val()>$("#r_end").val()){
									alert("开始时间不能大于结束时间！！！");
									$("#r_start").focus();
									return false;
								}
								return true;
						}
						function assign(){
						 /*if(!checkTime()){
						 	return false;
						 }*/
						 if(window.confirm("确定分配？！")){
							var checkObj = document.getElementsByName("id");
						    var billIDs = "";
						    for (i = 0; i < checkObj.length; i++) {
								if (checkObj[i].checked) {
								    if(billIDs!="")billIDs+=",";
									billIDs += checkObj[i].value;
								}
							 }
							if(billIDs==""){
							  alert("请选择要分配的记录！");
							  return ;
						    }
						    var userids = document.getElementById("userids");
						    userids.value=billIDs;   
							course_assignment.action="shopping_newassigntoUsers.action?status=0";
							course_assignment.submit();

							}
						}
						function unassign(){
						  if(window.confirm("确定取消分配？！")){
						     var checkObj = document.getElementsByName("id");
							    var billIDs = "";
							    for (i = 0; i < checkObj.length; i++) {
									if (checkObj[i].checked) {
									    if(billIDs!="")billIDs+=",";
										billIDs += checkObj[i].value;
									}
								 }
								if(billIDs==""){
								  alert("请选择要取消分配的记录！");
								  return ;
							    }
							  var userids = document.getElementById("userids");
						      userids.value=billIDs;
							  course_assignment.action="shopoing_newunassigntoUsers.action?status=0";
							  course_assignment.submit();
							}
						}
						  function assignSearch(){
						  	/*if(!checkTime()){
						 		return false;
							}*/
							course_assignment.action="course_allassigntoUsers.action?status=0";
							course_assignment.submit();
						  }
					</script>
					<a href="javascript:select_All()" />全选</a>
					<a href="javascript:select_Fan()" />反选</a>
					<a href="javascript:select_Bux()" />全不选</a>
			<br>
			<input value="分配" type="button" onClick="assign()">
			<input value="取消分配" type="button" onClick="unassign()">
			<input value="分配给全部搜索结果" type="button" onClick="assignSearch()">
		</div>
		<!--  	<form action="course_assigntoUsersInit.action" method="post" name="course_assignment">
					<s:hidden name="deptid" />
					<s:hidden name="course.id" />
					<s:hidden name="elUser.sex" />
					<s:hidden name="elUser.realname" />
					<s:hidden name="elUser.username" />
					
					<s:hidden name="starttime" />
					<s:hidden name="endtime" />
					<s:hidden name="elUser.isAssign" />
				 	<s:hidden name="userids" id="userids"></s:hidden>	 
	    	   </form>	
	     -->
		<!-- 内容 -->
	
	</body><script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
		<form action="shoping_assigntoUsersInit.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />				
				<s:hidden name="course.id" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.username" />
				<s:hidden name="starttime" />
				<s:hidden name="endtime" />
				<s:hidden name="elUser.isAssign" />
				<s:hidden name="deptid" />
	  </form>
</HTML>
