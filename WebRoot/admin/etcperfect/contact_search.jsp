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
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
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
					<wysLib:Navigation ivalue="日志列表" />
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
			<script type="text/javascript"> 
			
			function view(id){
					document.getElementById("contactid").value=id;
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
			</script>
			<s:form action="viewContact.action" method="post" name="viewContact"
				theme="simple">
				<s:hidden name="contact.id" id="contactid"/>
			</s:form>
			
			<s:form action="searchContact.action" method="post" name="searchLog_form"
				theme="simple">
				<s:hidden name="pN" id="pageNow"  />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				
				<table width="100%">
					<tr>
						<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td" style="padding:8px;">  
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="searchContact.action?department.id="
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
							
							
							<s:hidden name="exprot" id="exprot" />
							<div >
							
							联系主题：	<input name="contact.theme"  value="<s:property value="contact.theme" />" size="40">
				联系类型：
				
				<s:select  name="contact.type" 
				list="{'销售机会','报价','竞争分析','需求分析','方案管理','投标管理','中标事宜'}"
				theme="simple" headerKey="" headerValue="请选择"></s:select>			
				联系内容：	<input name="contact.content"  value="<s:property value="contact.content" />" size="40">
				
				<br>
				联系时间：<input class="Wdate" name="contact.begintime" readonly="readonly"
							type="text" onClick="setday(this)" id="releasetime"  width="40" value="<s:property value="contact.begintime" />" />
							到
							<input class="Wdate" name="contact.endtime" readonly="readonly"
							type="text" onClick="setday(this)" id="releasetime"  width="40" value="<s:property value="contact.endtime" />" />
			<!-- 
								时间：<input class="Wdate" name="log.log_createtime" readonly="readonly"
											type="text" onClick="setday(this)" id="releasetime"  width="40" value="<s:property value="log.log_createtime" />" />
								日志标题：	<input name="log.log_title"  size="40" value="<s:property value="log.log_title"/>" />
								<br>
								关联客户：	<s:textfield name="log.log_co_client"  size="40"></s:textfield>
								关联计划：<s:textfield name="log.log_co_plan"  size="40"></s:textfield>
							
								<br> -->
								<input type="button" value="搜索"  onClick="search();">
							</div>

							<table width="95%" align="center" cellpadding="1" cellspacing="1">
				<tr>
					<th width="280">
						操作
					</th>
					<th width="200" height="30" align="center">
						联系主题
					</th>

					<th width="200" height="30" align="center">
						联系类型
					</th>
					<th width="200" height="30" align="center">
						联系内容
					</th>
					<th width="200" height="30" align="center">
						联系时间
					</th>
					<th width="200" height="30" align="center">
						预期金额
					</th>
					<th width="200" height="30" align="center">
						相关客户
					</th>
					
					
				</tr>
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
					<s:iterator value="list_contact">	
						<tr>
						
						<td >
								
								<a href="javascript:view('<s:property value="id" />')">查看</a>    
							<!-- 	<a href="javascript:update('<s:property value="id" />')">修改 </a> 
								<a href="javascript:del('<s:property value="id" />')">删除</a>
								 -->
						</td>
						
						
						<td width="100" height="30" align="center">
								<s:property value="theme" />
							</td>
							<td width="100" height="30" align="center">
							<s:property value="type" />
							<!-- 
								<s:select  name="type" 
								list="{'销售机会','报价','竞争分析','需求分析','方案管理','投标管理','中标事宜'}"  
								theme="simple" headerKey="" headerValue="请选择"  />
								 -->
							</td>
							<td width="100" height="30" align="center">
								<s:property value="content" />
							</td>
							<td width="100" height="30" align="center">
								<s:property value="time" />
								<!-- 
								<input class="Wdate" name="time" readonly="readonly"
							type="text" onClick="setday(this);" id="relea"  value="<s:property value="time" />"   />
							 -->
							</td>
							<td width="100" height="30" align="center">
								<s:property value="money" />
							</td>
						
						<td width="100" height="30" align="center">
								<s:property value="re_client" />
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
			
			<br />
			
			
			
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>