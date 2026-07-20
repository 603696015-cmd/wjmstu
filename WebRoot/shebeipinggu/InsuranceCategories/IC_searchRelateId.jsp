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
		<base target="_self">
		<TITLE>联系行为查询</TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
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
			function search()
			{
				//alert("hello");
				
				
				
				searchLog_form.submit();
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
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
					<wysLib:Navigation ivalue="日志列表" />
				</div>
			</li>
		
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 10px; text-align: center;">
			<script type="text/javascript"> 
			
			function view(id){
					document.getElementById("contactid").value=id;
					viewContact.submit();
			}
			function del(id){
				if(window.confirm("确认删除？")){
					document.getElementById("contactid").value=id;
					viewContact.action="deleteContactTags.action";
					viewContact.submit();
				}
			}
			
			function update(id){
				document.getElementById("contactid").value=id;
				//document.getElementById("ismodify").value="modify";
				viewContact.action="updateContactTagsInit.action";
				viewContact.submit();
			}
			
			
			function verify_pass(id){
				document.getElementById("contactid").value=id;
				//document.getElementById("ismodify").value="modify";
				viewContact.action="verifypassContactTags.action";
				viewContact.submit();
			}
			function verify_nopass(id){
				document.getElementById("contactid").value=id;
				//document.getElementById("ismodify").value="modify";
				viewContact.action="verifynopassContactTags.action";
				viewContact.submit();
			}
			
			
				function toexcel(exprot) { 
					document.getElementById("exprot").value=exprot;
					acc_list.submit();
				} 
			 	function page(i){ 
			 		document.getElementById("pageNow").value=i;
			 		searchLog_form.submit();
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
				
				function opensearchdiv()
			{
				//alert("hello");
				if((document.getElementById("searchdiv").style.display)=="")
					document.getElementById("searchdiv").style.display="none";
				else document.getElementById("searchdiv").style.display="";
			}
			
			
			function setRv(){
				var str="";
				var code_Values = document.getElementsByName("check");
				for(i = 0;i < code_Values.length;i++){ 
					if(code_Values[i].checked ) 
						str = str + code_Values[i].value;
				}
				
				if(str == ""){
					alert("请选择一条记录!!!");
					return ;
				}
				
				window.returnValue = str;
				window.close();
		 	}
	 	
	 	function do_submit(){
	 		searchLog_form.submit();
	 	}
			
			</script>
			<s:form action="viewContactTags.action" method="post" name="viewContact"
				theme="simple">
				<s:hidden name="id" id="contactid"/>
				<s:hidden name="tablename" />
			</s:form>
			
			<s:form action="searchRelateId.action" method="post" name="searchLog_form"
				theme="simple">
				<s:hidden name="pN" id="pageNow"  />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="tablename" />
				
				<center style="color:red">
					选择<s:property value='tablename'/>表具体信息
				</center>
				<table width="100%">
					<tr>
						<td valign="top" width="120" id="tree_list_td"> 
							
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="searchUserId.action?tablename=${tablename}&sub_department=1&department.id="
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
							
							
							<s:hidden name="exprot" id="exprot" />
						<!--  	<input type="button" onClick="opensearchdiv();" value="搜索" />  -->
							<div id="searchdiv" style="display:none" >
								<table>
								<!-- <wysLib:combinesearch></wysLib:combinesearch>  -->
								</table>
								<input type="button" value="开始搜索"  onClick="search();">
							</div>
				
				<table width="95%"> 
					<tr>
						<s:if test="teblename = 'ELUSER'">
							<td>姓名<input  name="eluser.realname" /></td>
						   <td>
						   		角色
								<SELECT  style="WIDTH: 100px" name="eluser.role" 
							      onchange="this.value=this.options[this.selectedIndex].value;">
							        <OPTION value="" selected>选择角色</OPTION>
							        <s:iterator value="roles">
							        	<option value="<s:property value="id"/>">
											<s:property value="name"/> 
										</option>
							        </s:iterator>
							    </SELECT>			           
					       </td>
					       <td>
					       		职务
								<SELECT  style="WIDTH: 100px" name="eluser.zhiwu" 
							      onchange="this.value=this.options[this.selectedIndex].value;">
							        <OPTION value="" selected>选择职务</OPTION>
							        <s:iterator value="zhiwus">
							        	<option value="<s:property value="id"/>">
											<s:property value="basevalue"/> 
										</option>
							        </s:iterator>
							    </SELECT>
					       </td>
					       <td>
					       		工种
								<SELECT  style="WIDTH: 100px" name="eluser.jingzhong" 
							      onchange="this.value=this.options[this.selectedIndex].value;">
							        <OPTION value="" selected>选择工种</OPTION>
							        <s:iterator value="jingzhongs">
							        	<option value="<s:property value="id"/>">
											<s:property value="basevalue"/> 
										</option>
							        </s:iterator>
							    </SELECT>
					       </td>
						</s:if>
						<s:else>
							<td>姓名<input  name="eluser.pfmsUser.respName" /></td>
						</s:else>
					   
					   <td colspan="2">
			           	 	<input  type="button" onclick="do_submit();" value="搜索" >
			           </td>
					</tr>
				</table>
				
				<table width="95%" align="center" cellpadding="1" cellspacing="1">
					<s:if test="tablename == 'ELUSER'">
						<tr>
							<th>
							</th>
							<th>
							姓名
							</th>
							<th>
							单位
							</th>
							<th>
							岗位
							</th>
							<th>
							职务
							</th>
							<th>
							部门
							</th>
							<th>
							角色
							</th>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<th>
							</th>
							<th>
							负责人姓名
							</th>
							<th>
							地址
							</th>
							<th>
							电话
							</th>
							<th>
							传真
							</th>
						</tr>
					</s:else>
					<s:iterator value="list_eluser">
					<s:if test="tablename == 'ELUSER'">
						<tr>
							<td>
							<input type="radio" name="check" value="<s:property value='id'/>" />
							</td>
							<td>
							<s:property value="realname"/>
							</td>	
							<td>
							<s:property value="danwei"/>
							</td>
							<td>
							<s:property value="gangwei"/>
							</td>
							<td>
							<s:property value="zhiwu"/>
							</td>
							<td>
							<s:property value="departmentname"/>
							</td>
							<td>/
							<s:property value="rolename"/>
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td>
								<input type="radio" name="check" value="<s:property value='pfmsUser.id'/>" />
							</td>
							<td>
							<s:property value="pfmsUser.respName"/>
							</td>	
							<td>
							<s:property value="pfmsUser.address"/>
							</td>
							<td>
							<s:property value="pfmsUser.mobile"/>
							</td>
							<td>
							<s:property value="pfmsUser.fex"/>
							</td>
						</tr>
					</s:else>
					</s:iterator>
				</table>
						</td>
					</tr>
				</table>
			</s:form>
			<wysLib:page></wysLib:page>
			
			
			<div style="margin-top: 30px; text-align: center;">
			<input name="submit" type="button" value="确认选择" class=textbg6  onclick="setRv();"  />
		</div>
			<br />
			
			
			
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>