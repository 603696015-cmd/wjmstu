<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.duman.entities.Department"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<TITLE>中国食品安全培训网--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript">
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
			function init(){
				document.getElementById("depId").name="department.id";
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%;
	height:30px;
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="用户列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">用户管理</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="account_addInit.action?elUser.department.id=<s:property value="department.id"/>">添加用户</a>
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
			<%-- 
			<form action="account_search.action" method="post" name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.email" />
				<s:hidden name="elUser.realname" />  
				<s:hidden name="elUser.valid" />
				<s:hidden name="exprot" id="exprot"/> 
			</form>
		 --%>
			<script type="text/javascript"> 
			
			function view(id){
					document.getElementById("scheduleid").value=id;
					getSchedule.submit();
			}
				function toexcel(exprot) { 
					document.getElementById("exprot").value=exprot;
					acc_list.submit();
				} 
			 	function page(i){ 
			 		document.getElementById("pageNow").value=i;
			 		searchSchedule_form.submit();
			 	}
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
				function assign(){
				    if(window.confirm("确定开通？")){
						var checkObj = document.getElementsByName("id");
					    var billIDs = "";
					    for (i = 0; i < checkObj.length; i++) {
							if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
						 }
						if(billIDs==""){
						  alert("请至少选择一个复选框！");
						  return ;
					    }
					    var userids = document.getElementById("userids");
					    var status = document.getElementById("status");
					    userids.value=billIDs;
					    status.value=1;
						assignUser.action="assignUser.action";
						assignUser.submit();
					}
				}
				function unassign(){
				  if(window.confirm("确定关闭？")){
				     var checkObj = document.getElementsByName("id");
					    var billIDs = "";
					    for (i = 0; i < checkObj.length; i++) {
							if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
						 }
						if(billIDs==""){
						  alert("请至少选择一个复选框！");
						  return ;
					    }
					   var userids = document.getElementById("userids");
					   var status = document.getElementById("status");
				       userids.value=billIDs;
					   status.value=0;
					   assignUser.action="assignUser.action";
					   assignUser.submit();
					}
				}
				function delUser(){
				  if(window.confirm("确定删除？")){
				     var checkObj = document.getElementsByName("id");
					    var billIDs = "";
					    for (i = 0; i < checkObj.length; i++) {
							if (checkObj[i].checked) {
							    if(billIDs!="")billIDs+=",";
								billIDs += checkObj[i].value;
							}
						 }
						if(billIDs==""){
						  alert("请至少选择一个复选框！");
						  return ;
					    }
					   var userids = document.getElementById("userids");
				       userids.value=billIDs;
					   assignUser.action="delUser.action";
					   assignUser.submit();
					}
				}
				/*function setNov(){
					document.getElementById("elNov").value=1;
				}
				function setNov_no(){
					document.getElementById("elNov").value=0;
				}*/
				function search()
				{
					searchSchedule_form.submit();
				}
			</script>
			<s:form action="getSchedule.action" method="post" name="getSchedule"
				theme="simple">
				<s:hidden name="schedule.id" id="scheduleid"/>
			</s:form>
			
			<s:form action="searchSchedule" method="post" name="searchSchedule_form"
				theme="simple">
				<table width="100%">
					<tr>
						<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td" style="padding:8px;">  
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="searchSchedule.action?department.id="
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td width="5px;" valign="middle" bgcolor="#FAFCFC" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="department.id" />
							
							<s:hidden name="exprot" id="exprot" />
							<div style="text-align: center;">
								日期：<input class="Wdate" name="schedule.datetime" readonly="readonly"
											type="text" onClick="setday(this)" id="releasetime"  width="40" value="<s:property value="schedule.datetime" />" />
								主题：	<s:textfield name="schedule.topic"/>
								<br>
								相关客户：	<s:textfield name="log.log_co_client"  size="40"></s:textfield>
								相关项目：<s:textfield name="log.log_co_plan"  size="40"></s:textfield>
							
								<br>
								状态：
								<input type="button" value="搜索"  onClick="search();">
							</div>

							<table align="center" cellpadding="1" cellspacing="1"
								width="100%" height="100%">
								<tr>
									
									<th width="120">
										日期
									</th>
									<th width="100">
										用户名
									</th>
									
									<th width="90">
										姓名
									</th>
									<th width="100">
										状态
									</th>
									<th width="90">
										日程标题
									</th>
									<th width="100">
										关联客户
									</th>
									<th width="100">
										关联计划
									</th>
									
									
									<th width="50">
										&nbsp;
									</th>
									<!--<th>
						&nbsp;
					</th>
				-->
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="schedules_list">
										<tr>
											
											<td height="20" align="center">
												<s:property value="datetime" />
											</td>
											<td height="20" align="center">
												
											</td>
											
											<td>
											</td>
											<td>
											</td>
											<td>
											</td>
											<td>
											</td>
											<td>
											</td>
											
											
											<td width="50" height="20" align="center">
												<a href="javascript:view('<s:property value="id" />')"/>显示</a>
											</td>
											
											<!--<td height="20" align="center">
							<a
								href="account_deleteInit.action?elUser.id=<s:property value="id"/>">删除</a>
						</td>
					-->
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
			<wysLib:page></wysLib:page>
			
			<br />
			
			<s:form action="assignUser" method="post" name="assignUser">
				<s:hidden name="userids" id="userids" />
				<s:hidden name="status" id="userValid" />
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.email" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.valid" />
			</s:form>
			
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>