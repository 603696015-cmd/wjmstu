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
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" /> 
		<script type="text/javascript" src="js/calendar.js"></script> 
		<script type="text/javascript" src="eltree/dtree.js"></script>
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
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
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
				<s:hidden name="elUser.nov" />
				<s:hidden name="exprot" id="exprot"/> 
				<input type="hidden" name="roleid" value="<s:property value="role.id"/>"/>   
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
						assignUser.action="assignUser2.action";
						assignUser.submit();
					}
				}
				function assignAll(){
				    if(window.confirm("确定开通全部搜索结果？")){   
						document.getElementById("toAll").value='true';
						acc_list.submit();
					}
				}
				function MustOpenAssign(){//准开通
				    if(window.confirm("确定准开通？")){
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
					    status.value=2;
						assignUser.action="MustOpenAssignUser.action";
						assignUser.submit();
					}
				}
				function ApplicationAssign(){//审核开通
				    if(window.confirm("确定审核开通？")){
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
					    status.value=3;
						assignUser.action="ApplicationAssignUser.action";
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
					   assignUser.action="assignUser2.action";
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
					   assignUser.action="delUser2.action";
					   assignUser.submit();
					}
				}
				function setNov(){
					document.getElementById("elNov").value=1;
				}
				function setNov_no(){
					document.getElementById("elNov").value=0;
				}
				function Lockfor(){
					 if(window.confirm("确定要锁定账号，请先填入锁定时间！")){
					 document.getElementById("lockTime").style.display='block'
					 }
				}
				function ToLockfor(){
				/*	var tologintime = document.getElementById("tologintime"); 
					if(tologintime.value != undefined){*/
					    if(window.confirm("确定锁定？")){
						 /*   if(tologintime.value==""){
							    alert("锁定时间不能为空！");
							    return ;
						    }*/
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
						/*	var logintime = document.getElementById("logintime");*/
						    userids.value=billIDs; 
						/*    logintime.value=tologintime.value; */
							assignUser.action="toLockfor.action";
							alert("开始提交");
							assignUser.submit();
						}
				  /*  } */
				}
				function unlock(){
				    if(window.confirm("确定解锁？")){
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
						assignUser.action="unlock.action";
						assignUser.submit();
					}
				}
			</script>
			 <s:form action="account_search2.action" method="post" name="acc_list" theme="simple">
			  <table width="100%">
				<tr>
					<td valign="top" width="120" id="tree_list_td">
					<%-- 
						<wysLib:dep_list_aj rootAble="true"
							href="account_search.action?elUser.nov=1&department.id="></wysLib:dep_list_aj>
					 --%>
					 <%
					 	Department dep=(Department)request.getAttribute("department");
					 	String depid=dep.getId()+"";
					 %>
						<wysLib:dep_list_aj rootAble="true"
							href="account_search2.action?sub_department=1&elUser.nov=1&department.id=" iname="department.idd" ivalue="<%=depid %>" itype="nihao"></wysLib:dep_list_aj>
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
						<s:hidden name="elUser.nov" id="elNov"/>
						<s:hidden name="exprot" id="exprot"/>
						<s:hidden name="toAll" id="toAll"/>
						<input type="hidden" name="roleid" value="<s:property value="role.id"/>" />
						<div style="text-align:center;">
							角色：
							<select name="role.id">
								<option value="0">
									请选择
								</option>
								<s:iterator value="roles">
									<option <s:if test="role.id==id">selected='selected'</s:if>
										value="<s:property value="id"/>">
										<s:property value="name" />
									</option>
								</s:iterator>
							</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							是否已修改：
							<select name="elUser.isAlter">
								<option value="0">全部</option>
								<option <s:if test="elUser.isAlter==2">selected='selected'</s:if>	value="<s:property value="2"/>">
									已修改
								</option>
								<option <s:if test="elUser.isAlter==1">selected='selected'</s:if>	value="<s:property value="1"/>">
									未修改
								</option>
							</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							用户名：<s:textfield name="elUser.username" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							姓名：<s:textfield name="elUser.realname" />&nbsp;&nbsp;&nbsp;<br />
							培训类别:
							<select name="elUser.peixunleibie"> 
								<option value="0">全部</option>
								<s:iterator value="elUser.peixunleibies" status="pxlb">
									<option
										<s:if test="elUser.peixunleibie==elUser.peixunleibies[#pxlb.index]">selected = 'selected'</s:if>
										value="<s:property />">
										<s:property />
									</option>
								</s:iterator>
							</select>
							人员状态: 
							<select name="elUser.shifouzaizhi"> 
								<option value="0">全部</option>
								<s:iterator value="elUser.shifouzaizhis" status="sfzz">
									<option
										<s:if test="elUser.shifouzaizhi==elUser.shifouzaizhis[#sfzz.index]">selected = 'selected'</s:if>
										value="<s:property />">
										<s:property />
									</option>
								</s:iterator>
							</select>
							开通状态：
							<label>
								<s:radio list="#{1:'开通',2:'关闭',0:'全部'}" name="elUser.valid2" value="elUser.valid2" />
								<%-- 
								<input type="radio" name="elUser.valid" value="true" checked="checked" onClick="setNov_no();">开通
								<input type="radio" name="elUser.valid" value="false" onClick="setNov_no();">关闭
								<input type="radio" name="elUser.valid" value="true" onClick="setNov();">全部
								 --%>
							</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							包含下属部门：
							<label>
								<input type="checkbox" name="sub_department" 
									<s:if test="sub_department==1">checked="checked"</s:if>
									id="sub_department" value="1">
							</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="textbg4" type="submit" value="搜 索">
							</div>
					
						<table align="center" cellpadding="1" cellspacing="1" width="100%"
							height="100%" bgcolor="#ECEDEB">
							<tr>
								<th></th>
								<th width="90">
									用户名								</th>
								<th width="100">
									姓名								</th>
								<th width="100">
									单位名称								</th>
								<th width="120">
									单位/部门								</th>
								<th width="100">
									角色								</th>
								<!--<th width="50">
									状态								</th>-->
								<th width="70">
									是否修改						 </th>
								<th width="70">
									锁定/解锁						 </th>
								<th width="70">&nbsp;								</th>
								<th width="70">&nbsp;								</th>
								<th width="70">&nbsp;								</th>
								<!--<th>
						&nbsp;
					</th>
				-->
							</tr><tbody onMouseOut="changeback()" onMouseOver="changeto()" >
							<s:iterator value="elUsers"> 
								<tr>
									<td height="20" align="center">
										<input type="checkbox" value="<s:property value="id"/>" name="id">
									</td>
									<td height="20" align="center">
										<s:property value="username" />
									</td>
									<td height="20" align="center">
										<s:property value="realname" />
									</td>
									<td height="20" align="center">
										<s:property value="company.name" />
									</td>
									<td height="20" align="center">
										<s:property value="department.name" />
									</td>
									<td height="20" align="center">
										<s:property value="role.name" />
									</td>
									<!--<td height="20" align="center">
										<s:property value="validsName" />
									</td>-->
									<td height="30" align="center">
										<s:property value="isAlterName" />
									</td>
									<td height="30" align="center">
										<s:property value="isLockName" />
									</td>
									<td height="20" align="center">
										<a
											href="account_view2.action?elUser.id=<s:property value="id"/>" class="textbg4">显示</a>
									</td>
									<td height="20" align="center"> 
										<a href="account_alterInit2.action?elUser.id=<s:property value="id"/>" class="textbg4">修改</a> 
									</td>
									<td height="20" align="center">
										<a href="javascript:grantManage('<s:property value="id"/>','<s:property value="role.id"/>');" class="textbg4">授权</a>
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
			<a href="javascript:select_All()" />全选</a>
			<a href="javascript:select_Fan()" />反选</a>
			<a href="javascript:select_Bux()" />全不选</a>
			<a href="javascript:MustOpenAssign()" />准开通</a>
			<a href="javascript:ApplicationAssign()" />初审通过</a> 
			<s:if test="elUser.role.id == 1">
				<a href="javascript:delUser()" />删除</a>
				<a href="javascript:unassign()" />关闭</a>
				<a href="javascript:assign()" />开通</a>
				<a href="javascript:assignAll()" />开通全部搜索结果</a>
			</s:if><br/>
			<a href="javascript:unlock()" />解 锁</a>
			<a href="javascript:ToLockfor()" />锁 定</a> 
	<!-- 		<div id="lockTime" style="display:'none'">
				锁定时间：
						<input name="tologintime" id="tologintime" type="text" size="20"
							onClick="setday(this)" value="" > 
			  <input type="button" value="确定锁定" onClick="ToLockfor()">
		 	</div>  -->
		 	<s:form action="assignUser2.action" method="post" name="assignUser">
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
				<s:hidden name="elUser.nov" />  
				<s:hidden name="elUser.logintime" id="logintime"/>  
				<input type="hidden" name="roleid" value="<s:property value="role.id"/>"/> 
	        </s:form> 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td width="200">&nbsp;				  </td>
					<td width="140">
					<%-- 	<a href="account_searchInit.action" class="textbg">修改搜索条件</a> --%>
					</td>
					<td width="400">
						<a href="account_addInit2.action" class="textbg">添加用户</a>
						<%-- <a
							href="account_importBydepInit.action?elUser.department.id=<s:property value="department.id"/>" class="textbg">导入用户</a>	
						<a href="javascript:toexcel(true);"  class="textbg">导出用户</a>					
						--%>
					</td>
				  <td>&nbsp;</td>
				</tr>
			</table>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>