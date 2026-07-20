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
		<TITLE>五矿发展员工职业发展系统--管理端--用户添加</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
			
			function myload(){
				var worktime = document.getElementById("attendance.worktime");
				var outworktime = document.getElementById("attendance.outworktime");
				
				//worktime.value = getYYR("<s:property value='attendance.worktime'/>");
				//outworktime.value = getYYR("<s:property value='attendance.outworktime'/>");
			}
			
			function getYYR(str){
				var str_ = "";
				var array;
				var returnValue = "";
				if(str != ""){
					str_ = str.split(" ")[1];
					array = str_.split(":");
					if(array != null){
						returnValue = array[0]+"时"+array[1] + "分" + array[2].split(".")[0] + "秒";
					}
				}
				
				return returnValue;
				
			}
			
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body onload="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="考勤设置" />
				</div>
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
				function toexcel(exprot) { 
					document.getElementById("exprot").value=exprot;
					acc_list.submit();
				} 
			 	function page(i){ 
			 		document.getElementById("pageNow").value=i;
			 		acc_list.submit();
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
				
				
				
				//考勤设置
				/**
				function show(number){
					if(number == 1){
						document.getElementById("show1").style.display = "block";
					}else if(number == 2){
						document.getElementById("show2").style.display = "block";
					}else if(number == 3){
						document.getElementById("show3").style.display = "block";
					}else if(number == 4){
						document.getElementById("show4").style.display = "block";
					}
				}
				*/
				
				function doOnclick(){
					attendance.submit();
				}
				
				//选择节假日
				function select_weekdaytime(){
					width=600;
					height=500;
					var url = "select_weekdaytime.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("attendance.holidays").value=rv;
					}
				}
				
				function view_weekdaytime(){
					width=600;
					height=500;
					var url = "view_weekdaytime.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("attendance.holidays").value=rv;
					}
				}
			</script>
			<s:if test="#session.roleid==1">
				<form action="attendanceSetup.action" name="attendance" method="post">
				<table width="100%" align="center" cellspacing="1" cellpadding="1">
					<caption>考勤设置</caption>
					<tr>
						<th height="30" align="center">
							上班时间
						</th>
						<th height="30" align="center">
							下班时间
						</th>
						<th height="30" align="center">
							周末时间
						</th>
						<th height="30" align="center">
							节假日
						</th>
						<th height="30" align="center">
							操作
						</th>
					</tr>
					<tr>
						<td>
							<center>
								<span style='color:red;CURSOR: hand' >设置上班时间</span>
								<!-- <input type="text" name='attendance.worktime'  onclick="setday(this)" value="<s:property value='attendance.worktime'/>"/> -->
								<input type="text" name='attendance.worktime'   onclick="setday(this)" value="<s:date name='attendance.worktime' format='yyyy-MM-dd HH:mm:ss'/>"/>
							</center>
						</td>
						<td>
							<center>
								<span style='color:red;CURSOR: hand' >设置下班时间</span>
								<!-- <input type="text" name='attendance.outworktime'   onclick="setday(this)" value="<s:property value='attendance.outworktime'/>"/> -->
								<input type="text" name='attendance.outworktime'   onclick="setday(this)" value="<s:date name='attendance.outworktime' format='yyyy-MM-dd HH:mm:ss'/>"/>
							</center>
						</td>
						<td>
							<center>
								<span style='color:red;CURSOR: hand' >设置周末时间</span>
								<input type="checkbox" name='attendance.weekdaytime'  value="1" checked/>星球六
								<input type="checkbox" name='attendance.weekdaytime'  value="2" checked/>星期天
								<s:iterator var='u' value='attendance.weekdaytime'>
									<s:if test="#u == 1">
										<span style='color:red'>星期六</span>
										<!-- <input disabled type="checkbox" name='attendance.weekdaytime'  value="1" checked/>星期六 -->
									</s:if>
									<s:else>
										<span style='color:red'>星期天</span>
									</s:else>
								</s:iterator>
							</center>
						</td>
						<td width='35%'>
							<center>
								<span style='color:red;CURSOR: hand' >设置节假日时间</span>
								<input type="hidden" name='attendance.holidays'  id="attendance.holidays" value='<s:property value='attendance.holidays'/>'/>
								<input type='button' value='选择节假日' onclick='select_weekdaytime();'/>
								<s:if test="attendance.holidays != null">
									<input type='button' value='查看节假日' onclick='view_weekdaytime();'/>
								</s:if>
							</center>
						</td>
						<td >
							<center>
								<input type='button' value='确认设置' class='textbg6' onclick='doOnclick();'/>
							</center>
						</td>
					</tr>
				</table>
				</form>
			</s:if>
			
			<s:form action="attendanceSetupInit.action" method="post" name="acc_list"
				theme="simple">
				<table width="100%" cellpadding="1" cellspacing="1">
					<caption>
						考勤信息
					</caption>
					<tr>
						<td valign="top" width="120" id="tree_list_td">
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="attendanceSetupInit.action?sub_department=1&elUser.valid2=0&department.id="
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" />
						</td>
						<td valign="top">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="elUser.email" />
							<s:hidden name="department.id" />
							<div style="text-align: center;">
								角色：
								<select name="elUser.role.id">
									<option value="0">
										请选择
									</option>
									<s:iterator value="roles">
										<option <s:if test="elUser.role.id==id">selected='selected'</s:if>
											value="<s:property value="id"/>">
											<s:property value="name" />
										</option>
									</s:iterator>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 用户名：
								<s:textfield name="elUser.username" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 姓名：
								<s:textfield name="elUser.realname" />
								&nbsp;&nbsp;&nbsp;
								<br />
								开通状态：
								<label>
									<s:radio list="#{1:'开通',2:'关闭',0:'全部'}" name="elUser.valid2"
										value="elUser.valid2" />
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 包含下属部门：
								<label>
									<input type="checkbox" name="sub_department"
										<s:if test="sub_department==1">checked="checked"</s:if>
										id="sub_department" value="1">
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input class="textbg4"
									onclick="document.getElementById('exprot').value='false';document.getElementById('pageNow').value=0;"
									type="submit" value="搜 索">
							</div>

							<table align="center" cellpadding="1" cellspacing="1"
								width="100%" height="100%">
								<tr>
									<th width="90" >
										用户名
									</th>
									<th width="100" >
										姓名
									</th>
									<th width="120" >
										单位/部门
									</th>
									<th width="120" >
										MAC地址
									</th>
									<th width="120" >
										操作
									</th>
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="elUsers">
										<tr>
											<td height="30" align="center">
												<s:property value="username" />
											</td>
											<td height="20" align="center">
												<s:property value="realname" />
											</td>
											<td height="20" align="center">
												<s:property value="department.name" />
											</td>
											<td height="20" align="center">
												<s:property value="mac" />
											</td>
											<td height="20" align="center">
												<a href="updateMacInit.action?id=<s:property value='id'/>" class="textbg6" >修改MAC</a>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</s:form>
			<wysLib:page></wysLib:page>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>