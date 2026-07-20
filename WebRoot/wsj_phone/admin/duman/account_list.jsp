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
		<TITLE>用户管理</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/userCheck.js"></script>
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
	line-height: 150%
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
				/*function setNov(){
					document.getElementById("elNov").value=1;
				}
				function setNov_no(){
					document.getElementById("elNov").value=0;
				}*/
				
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
			<s:form action="account_search" method="post" name="acc_list"
				theme="simple">
				<table width="100%" cellpadding="1" cellspacing="1">
					<tr>
						<td valign="top" width="200" id="tree_list_td" style="display:none;">
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="account_search.action?sub_department=1&elUser.valid2=0&department.id="
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td valign="middle" width="5px;" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg"/>
						</td>
						<td valign="top">
							<s:hidden name="pN" id="pageNow" />
							<s:hidden name="pS" />
							<s:hidden name="elUser.email" />
							<s:hidden name="department.id" />
							<s:hidden name="station.id" id="staid" />
							<s:hidden name="exprot" id="exprot" />
                            <table width="100%" border="0" cellpadding="0" cellspacing="1">
  <tr>
    <td width="100" rowspan="2"><div style="text-align: left;" id="showtree">                           
								<a href="javascript:showtree(true);" class="textbg5">显示部门</a>
							</div></td>
    <td width="240"><span style="text-align: center;">角色：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <select name="elUser.role.id">
          <option value="0"> 请选择 </option>
          <s:iterator value="roles">
            <option >
              <s:if test="elUser.role.id==id">selected='selected'</s:if>
              value="
              <s:property value="id"/>
              ">
              <s:property value="name" />
              </option>
          </s:iterator>
        </select>
    </span></td>
    <td width="210">用户名：<s:textfield name="elUser.username" /></td>
    <td>岗位：
										<s:textfield theme="simple" name="station.name"
											size="10" id="gangweiName" readonly="true" />
										<a href="#" class="textbg4" style="width: 90px;"
											onClick="searchUserInit2();return false;">点此进行选择</a></td>
    <td width="260">包含下属部门：
								<label>
									<input type="checkbox" name="sub_department"
										<s:if test="sub_department==1">checked="checked"</s:if>
										id="sub_department" value="1">
								</label></td>
  </tr>
  <tr>
    <td>岗位名称：<s:textfield name="elUser.xianzhiwei" /></td>
    <td>姓名：&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield name="elUser.realname" /></td>
    <td>开通状态：<label>
									<s:radio list="#{1:'开通',2:'关闭',0:'全部'}" name="elUser.valid2"
										value="elUser.valid2" />
			  </label></td>
    <td><span style="text-align: center;">
      <input class="textbg4"
									onclick="document.getElementById('exprot').value='false';document.getElementById('pageNow').value=0;"
									type="submit" style="width:45px" value="搜 索">
    </span></td>
  </tr>
</table>


							<table align="center" cellpadding="1" cellspacing="1"
								width="100%" height="100%">
						  <tr>
									<th width="20"></th>
									<th width="90">
										用户名
									</th>
									<th width="100">
										姓名
									</th>
									<th width="120">
										单位/部门
									</th>
									<th width="100">
										角色
									</th>
									<th width="50">
										性别
									</th>
									<th width="50">
										年龄
									</th>
						<!-- 		<th width="50">
										工种
									</th> -->	
									<th width="50">
										状态
									</th>
									<th width="50">&nbsp;
										
									</th>
									<th width="50">&nbsp;
										
									</th>
									<th width="50">&nbsp;
										
									</th>
									<!--<th>
						&nbsp;
					</th>
				-->
								</tr>
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<s:iterator value="elUsers">
										<tr>
											<td width="20" height="20" align="center">
												<input type="checkbox" value="<s:property value="id"/>"
													name="id">
											</td>
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
												<s:property value="role.name" />
											</td>
											<td height="20" align="center">
												<s:property value="sex" />
											</td>
											<td height="20" align="center">
												<s:property value="age" />
											</td>
									<!-- 	<td height="20" align="center">
												<s:property value="jingzhong_" />
											</td> -->	
											<td height="20" align="center">
												<s:property value="validName" />
											</td>
											<td width="50" height="20" align="center">
												<!-- 
												<a
													href="account_view.action?elUser.id=<s:property value="id"/>"
													class="textbg4">显示</a>
													 -->
												<a
													href="account_view_cisco.action?elUser.id=<s:property value="id"/>"
													class="textbg4">显示</a>
											</td>
											<td width="50" height="20" align="center">
												<!-- 
												<a
													href="account_alterInit.action?elUser.id=<s:property value="id"/>"
													class="textbg4">修改</a>
												 -->
												 <a
													href="account_alterInit_cisco.action?elUser.id=<s:property value="id"/>"
													class="textbg4">修改</a>
											</td>
											<td width="50" height="20" align="center">
												<a
													href="javascript:grantManage('<s:property value="id"/>','
														<s:property value="role.id"/>');"
													class="textbg4">授权</a>
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
			<div><a href="javascript:select_All()" >全选</a>
			<a href="javascript:select_Fan()" >反选</a>
			<a href="javascript:select_Bux()" >全不选</a>
			<a href="javascript:assign()" class="textbg4">开通</a>
			<a href="javascript:unassign()" class="textbg4">关闭</a>
			<a href="javascript:delUser()" class="textbg4">删除</a>
		<!-- <font color="red">注意：没有操作和被操作过的用户会被真删除，否则就关闭</font> -->	
			</div> <s:form theme="simple" cssStyle="margin:0px;padding:0px;" action="assignUser" method="post" name="assignUser">
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
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="200">&nbsp;
						
					</td>
					<td width="140">
						<%-- 	<a href="account_searchInit.action" class="textbg">修改搜索条件</a> --%>
					</td>
					<td width="400">
						<!-- 
						<a href="account_addInit.action" class="textbg">添加用户</a>
						 -->
						 <a href="account_addInit_cisco.action" class="textbg5">添加用户</a>
				 		<a
							href="account_importBydepInit.action?elUser.department.id=<s:property value="department.id"/>"
							class="textbg5">导入用户</a>
						
						<a href="javascript:toexcel(true);" class="textbg5">导出用户</a>
					</td>
					<td>&nbsp;
						
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>	
</HTML>
<!--		 系统管理---用户与部门---用户管理(jsp页面)    	  -->
