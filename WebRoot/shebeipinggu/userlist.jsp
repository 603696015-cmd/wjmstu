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
		<!-- 内容 -->
		<div style="margin-top: 10px; text-align: center;">
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
						assignUser.action="assignUser.action?type=1";
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
				
				function sure_submit(){
					if(window.confirm("确定修改热度属性？")){
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
					    var value = document.getElementById("hot").value;
					    if(value == ""){
					    	alert("请选择热度!");
					    	return ;
					    }else{
					    	 document.getElementById("select_tuijian").value = value;
					    }
				        userids.value=billIDs;
				        assignUser.action="change_tuijian_pfmsuser.action";
					    assignUser.submit();
					}
				}
				
				function delPfmsUser(){
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
					   assignUser.action="delPfmsUser.action";
					   assignUser.submit();
					}
				}
				
			</script>
			<s:form action="userlist.action" method="post" name="acc_list" 
				theme="simple">
				<table width="100%">
					<tr>
						<td valign="top" width="120" id="tree_list_td">
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="userlist.action?sub_department=1&elUser.valid2=0&department.id="
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
							<s:hidden name="elUser.pfmsUser.tuijian" id="tuijian" />
							<s:hidden name="elUser.email" />
							<s:hidden name="department.id" />
							<s:hidden name="exprot" id="exprot" />
							<div style="text-align: center;">
								角色：
								<select name="elUser.role.id">
									<option value="0">
										请选择
									</option>
									<s:iterator value="roles">
										<option <s:if test="role.id==id">selected='selected'</s:if>
											value="<s:property value="id"/>">
											<s:property value="name" />
										</option>
									</s:iterator>
								</select>
								会员类型：
								<select name="elUser.pfmsUser.huiyuanleixing" 
									onchange="elUser.pfmsUser.huiyuanleixing.value=this.options[this.selectedIndex].value">
									<option value="">
										请选择
									</option>
									<option value="设备生产">
										设备生产
									</option>
									<option value="设备销售">
										设备销售
									</option>
									<option value="油品生产">
										油品生产
									</option>
									<option value="油品供应">
										油品供应
									</option>
									<option value="配件生产">
										配件生产
									</option>
									<option value="配件销售">
										配件销售
									</option>
									<option value="修理厂">
										修理厂
									</option>
									<option value="保险公司">
										保险公司
									</option>
									<option value="银行">
										银行
									</option>
									<option value="建筑施工单位">
										建筑施工单位
									</option>
									<option value="个体">
										个体
									</option>
								</select>
								<br>
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
									<th width="20"></th>
									<th width="80">
										用户名
									</th>
									<th width="80">
										姓名
									</th>
									<th width="80">
										单位/部门
									</th>
									<th width="80">
										角色
									</th>
									<th width="50">
										状态
									</th>
									<th width="80">
										会员类型
									</th>
									<th width="80">
										热度
									</th>
									<th width="80">
										省市县
									</th>
									<th width="50">
										&nbsp;
									</th>
									<th width="50">
										&nbsp;
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
									<s:iterator value="elUsers" status="status">
										<tr>
											<td width="20" height="20" align="center">
												<input id="change_id_<s:property value='#status.index+1'/>" type="checkbox" value="<s:property value="id"/>"
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
											<td height="20" align="center">
												<s:property value="pfmsUser.huiyuanleixing" />
											</td>
											<td height="20" align="center">
												<p ><s:property value="pfmsUser.tuijian" /></p>
											</td>
											<td height="80" align="center">
												<s:property value="pfmsUser.province_city_county" />
											</td>
											<td width="50" height="20" align="center">
												<a
													href="queryBaseInfo.action?elUser.id=<s:property value="id"/>&showType=1"
													class="textbg4">显示</a>
											</td>
											<td width="50" height="20" align="center">
												<a
													href="updatePfmsUser_init.action?elUser.id=<s:property value="id"/>&showType=1"
													class="textbg4">修改</a>
											</td>
											<td width="50" height="20" align="center">
												<a
													href="javascript:grantManage('<s:property value="id"/>','
										<s:property value="role.id"/>');"
													class="textbg4">授权</a>
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
			<br>
			热度属性:
			<SELECT id="hot" style="WIDTH: 80px" name="elUser.pfmsUser.tuijian" 
	              onchange="this.value=this.options[this.selectedIndex].value;">
	            <OPTION value="">==请选择==</OPTION>
		        <OPTION value=普通>普通</OPTION>
		        <OPTION value=推荐>推荐</OPTION>
		        <OPTION value=重点>重点</OPTION>
		        <OPTION value=热门>热门</OPTION>
		        <OPTION value=幻灯>幻灯</OPTION>
	      	</SELECT>
	      	
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			<a href="javascript:assign()" />开通</a>
			<a href="javascript:unassign()" />关闭</a>
			<a href="javascript:sure_submit()" />确认提交</a>
			<a href="javascript:delPfmsUser()" />删除</a>
			<s:form action="assignUser.action" method="post" name="assignUser">
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
				<s:hidden name="select_tuijian" id="select_tuijian" />
			</s:form>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="200">
						&nbsp;
					</td>
					<td width="140">
						<%-- 	<a href="account_searchInit.action" class="textbg">修改搜索条件</a> --%>
					</td>
					<td width="400">
						<a href="pfmsUser_addInit.action" class="textbg">添加用户</a>
						<a
							href="pfmsUser_importByDepInit.action?elUser.department.id=<s:property value="department.id"/>"
							class="textbg">导入用户</a>
						<a href="javascript:toexcel(true);" class="textbg">导出用户</a>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>