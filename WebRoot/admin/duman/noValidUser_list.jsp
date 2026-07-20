<%@ page language="java" pageEncoding="UTF-8"%>
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
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
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
		<script type="text/javascript" src="eltree/dtree.js"></script>
		<script type="text/javascript">
			function grantManage(userId,roleId){
				if(roleId==1){
					alert('超级管理员，拥有所有权限，不需要赋权！！！');
					return;
				}
				document.location.href="showUserGrant.action?elUser.id="+userId;
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
	</HEAD>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="开通用户" />
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
			<script type="text/javascript">
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
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg5">隐藏部门</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg5">显示部门</a>';
					}
				}
			</script>
			<table width="100%" cellpadding="1" cellspacing="1">
				<tr>
					<td valign="top" width="120" id="tree_list_td" style="display:none">
						<wysLib:dep_list_aj rootAble="true"
							href="displayNoValidUser.action?elUser.valid2=2&department.id="></wysLib:dep_list_aj>
					</td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" id="showimg"/>
					</td>
					<td valign="top">
						<div style="text-align: left;" id="showtree">
							<a href="javascript:showtree(true);" class="textbg5">显示部门</a>
						</div>
						<table style="margin-bottom: 8px;" align="center" cellpadding="1"
							cellspacing="1" width="100%" height="100%">
							<tr>
								<th width="20"></th>
								<th>
									用户名
								</th>
								<th>
									姓名
								</th>
								<th width="120">
									单位/部门
								</th>
								<th width="100">
									角色
								</th>
								<th width="50">
									状态
								</th>
								<%-- 
								<th width="70">&nbsp;								</th>
								<th width="70">&nbsp;								</th>
								<th width="70">&nbsp;								</th>
								--%>
								<th width="70">&nbsp;
									
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="elUsers">
									<tr>
										<td width="20" height="20" align="center">
											<input type="checkbox" value="<s:property value="id"/>"
												name="id">
										</td>
										<td height="20" align="center">
											<s:property value="username" />
										</td>
										<td height="20" align="center">
											<s:property value="realname" />
										</td>
										<td height="20" align="center">
											<s:property value="department.name" />
										</td>
										<td height="20" align="center">
											<s:property value="role.name" />
										</td>
										<td height="20" align="center">
											<s:property value="validName" />
										</td>
										<%-- 
									<td height="20" align="center">
										<a
											href="account_view.action?elUser.id=<s:property value="id"/>" class="textbg4">显示</a>
									</td>
									<td height="20" align="center">
										<a
											href="account_alterInit.action?elUser.id=<s:property value="id"/>" class="textbg4">修改</a>
									</td>
									<td height="20" align="center">
										<a href="javascript:grantManage('<s:property value="id"/>','<s:property value="role.id"/>');" class="textbg4">授权</a>
									</td>
									 --%>
										<td height="20" align="center">
											<a
												href="account_alterInit.action?elUser.id=<s:property value="id"/>"
												class="textbg4">修改</a>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			<wysLib:page></wysLib:page>
			<a href="javascript:select_All()" class="textbg4">全选</a>
			<a href="javascript:select_Fan()" class="textbg4">反选</a>
			<a href="javascript:select_Bux()" style="width:60px" class="textbg4">全不选</a>
			 <a
				href="javascript:assign()" class="textbg4">开通</a>

			<%-- 
			<a href="javascript:unassign()" />关闭</a>
			 --%>
			<form action="assignUser.action" method="post" name="assignUser">
				<s:hidden name="resultPage" value="2" />
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
				<input type="hidden" name="roleid"
					value="<s:property value="role.id"/>" />
			</form>
			<form action="displayNoValidUser.action" method="post"
				name="acc_list">
				<s:hidden name="pN" id="pageNow" />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="sub_department" />
				<s:hidden name="elUser.username" />
				<s:hidden name="elUser.email" />
				<s:hidden name="elUser.realname" />
				<s:hidden name="elUser.valid" />
				<input type="hidden" name="roleid"
					value="<s:property value="role.id"/>" />
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
<!--		 系统管理---用户与部门---用户开通管理(jsp页面)    	  -->

