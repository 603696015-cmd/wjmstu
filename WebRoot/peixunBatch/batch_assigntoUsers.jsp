<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%> 
<%@page import="com.sopia.peixunBatch.entities.PeixunBatch"%> 
<%@page import="com.sopia.duman.entities.Department"%>
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
		<script type="text/javascript" src="js/userCheck.js"></script>
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员列表" /></div>
			</li>
			<!--<li>
					 分配学员
				</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
			
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<form action="batch_assigntoUsersInit.action" method="post" name="batch_assignment">
					<s:hidden name="deptid" />
					<s:hidden name="peixunBatch.id" />
					<s:hidden name="elUser.sex" />
					<s:hidden name="elUser.realname" />
					<s:hidden name="elUser.username" />
					<s:hidden name="elUser.jingzhong" />
					<s:hidden name="starttime" />
					<s:hidden name="endtime" />
					<s:hidden name="elUser.isAssign" />
				 	<s:hidden name="userids" id="userids"></s:hidden>	 
				 	
				</form>
		<s:form action="batch_assigntoUsersInit.action" method="post" name="assignSearch_assignment" theme="simple">
			<s:hidden name="peixunBatch.id" />
				<s:hidden name="station.id" id="staid" />	
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
				   <td>年龄段开始时间:<input type="text" size="16" name="starttime" onClick="setday(this)"></td>
				       <td>年龄段结束时间:<input type="text" size="16" name="endtime" onClick="setday(this)"></td>
				         <td>
				    	岗位：
										<s:textfield theme="simple" name="station.name"
											size="10" id="gangweiName" readonly="true" />
										<a href="#" class="textbg4" style="width: 90px;"
											onClick="searchUserInit2();return false;">点此进行选择</a>
				 	   </td>
				         <td>
							岗位名称：
										<s:textfield name="elUser.xianzhiwei" />
						</td>
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
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="batch_assigntoUsersInit.action?sub_department=1&elUser.valid2=0&department.id="
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
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
							     <input type="checkbox" value="<s:property value="id"/>" name="id"> 
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
						 if(!checkTime()){
						 	return false;
						 }
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
						    var myselect = document.getElementById("jieye");
							var s = myselect.options[myselect.selectedIndex].value
							course_assignment.action="course_newassigntoUsers.action?status=0&jieyeid="+s;
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
							  batch_assignment.action="course_newunassigntoUsers.action?status=0";
							  course_assignment.submit();
							}
						}
						  function assignSearch(){
						  	if(!checkTime()){
						 		return false;
							}
							batch_assignment.action="course_allassigntoUsers.action?status=0";
							batch_assignment.submit();
						  }
					</script>
					<a href="javascript:select_All()" />全选</a>
					<a href="javascript:select_Fan()" />反选</a>
					<a href="javascript:select_Bux()" />全不选</a>
			<br>
		<!-- <input value="分配" type="button" onClick="assign()">
			<input value="取消分配" type="button" onClick="unassign()">
			<input value="分配给全部搜索结果" type="button" onClick="assignSearch()"> -->	
			 <input type="submit" value="分  配" class="textbg6"
								style="margin-top: 20px; margin-left: 40px;"
								onclick="selectUser();">
							
			<input type="button" value="关  闭" class="textbg6"
								style="margin-top: 20px; margin-left: 40px;"
								onclick="window.close();">
		</div>
		<form action="batch_eluser_add.action" name="eca" method="post">
				<s:hidden name="ids" id="ids"></s:hidden>
				<s:hidden name="status" id="status"></s:hidden>
				<s:hidden name="peixunBatchId" id="peixunBatchId"></s:hidden>
				<s:hidden name="elclassId" id="elclassId"></s:hidden>
			</form>
		<script type="text/javascript">
			    function selectUser(){
			       var checkObj = document.getElementsByName("id");
				   var billIDs = "";
				   for (i = 0; i < checkObj.length; i++) {
						if (checkObj[i].checked) {
						    if(billIDs!="")billIDs+=",";
							billIDs += checkObj[i].value;
						}
					}
				   if(billIDs==""){
					  alert("请选择要添加的记录！");
					  return ;
				   }
				   if(confirm('确定选择？')){
				   		$("#ids").val(billIDs);
				   		//$("#status").val(${status});
				   		$("#peixunBatchId").val(${peixunBatch.id});
				   		//$("#elclassId").val(${peixunBatch.elclassId});
				   		
				   		eca.submit();
				   }
			    }
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
		</script>
		<!--  	<form action="course_assigntoUsersInit.action" method="post" name="course_assignment">
					<s:hidden name="deptid" />
					<s:hidden name="course.id" />
					<s:hidden name="elUser.sex" />
					<s:hidden name="elUser.realname" />
					<s:hidden name="elUser.username" />
					<s:hidden name="elUser.jingzhong" />
					<s:hidden name="starttime" />
					<s:hidden name="endtime" />
					<s:hidden name="elUser.isAssign" />
				 	<s:hidden name="userids" id="userids"></s:hidden>	 
	    	   </form>	
	     -->
		<!-- 内容 -->
	</BODY><script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					acc_list.submit();
				}
			</script>
		<form action="batch_assigntoUsersInit.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="deptid" />
				<s:hidden name="course.id" />
				<s:hidden name="elUser.sex" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.jingzhong" />
				<s:hidden name="starttime" />
				<s:hidden name="endtime" />
				<s:hidden name="elUser.isAssign" />
	  </form>
</HTML>
