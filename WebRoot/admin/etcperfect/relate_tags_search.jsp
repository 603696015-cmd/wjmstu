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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript">
				function showtree(flag){
					var tree = document.getElementById("tree_list_td");
					var img = document.getElementById("showimg");
					if(flag){
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(false);" class="textbg">隐藏部门</a>';
					}
					else{
						changeTreeDisplay(img);
						document.getElementById("showtree").innerHTML = '<a href="javascript:showtree(true);" class="textbg">显示部门</a>';
					}
				}
		</script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
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
			function search()
			{
				//alert("hello");
				
				
				
				searchLog_form.submit();
			}
			
			
			function load_(){
				var tablename = "<s:property value='tablename'/>";
				var returnValue = "";
				$.ajax({
					  type: 'POST',
					  url: "getModuleShujuNameByTablename.action",
					  data: {tablename:tablename},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != "")
				  			returnValue = data;
					  }
				});
				var chinese_columnName = returnValue;
				//alert(chinese_columnName);
				if(chinese_columnName != 'null'){
					document.getElementById("addRelate_value").setAttribute("value","添加相关"+chinese_columnName);
				}else {
					document.getElementById("addRelate_value").setAttribute("value","添加相关");
				}
				
			}
			
		</script>

		
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
	
	<body onload="load_();">
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
			
			
			 function setRv()
		{
			//alert("ggg");
			var str="";
			var flag=false;
			var code_Values = document.getElementsByName("check");
			for(i = 0;i < code_Values.length;i++)
			{ 
				if(code_Values[i].checked ) 
				{ 
					if(flag)
					{
						str +="_--_";
					}
					 str +=code_Values[i].value;
					 flag=true;
				} 
			}
			
			//alert(str);
			window.returnValue = str;
			window.close();
	 	}
			
			
			function addRelate(){
				var tablename = "<s:property value='tablename'/>";
				width=1000;	
			 	height=800;	
			 	var url = "addContactTagsInit.action?tablename=" + tablename + "&relate=1";
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog(url,null,sFeature);
			  	if(rv == "success"){
			  		searchLog_form.submit();
			  		//window.location.reload();
			  		alert("添加相关成功!!!");
			  	}
			}
			</script>
			<s:form action="viewContactTags.action" method="post" name="viewContact"
				theme="simple">
				<s:hidden name="id" id="contactid"/>
				<s:hidden name="tablename" />
			</s:form>
			
			<s:form action="relateColumn.action" method="post" name="searchLog_form"
				theme="simple">
				<%
					String search_control = (String)request.getAttribute("search_control");
				%>
				<s:hidden name="pN" id="pageNow"  />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="tablename" />
				<s:hidden name="columnName" />
				<s:hidden name="is_judge" />
				<input type='hidden' name="control" value="<%=search_control %>"/>
				<s:hidden name="tags.is_judge" id="is_judge" />
				<table width="100%">
					<tr>
						<%
							String is_judge = (String)request.getAttribute("is_judge");
							request.setAttribute("is_judge",is_judge);
							if(is_judge.equals("1")) 
						{%>
						<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td" style="padding:8px;display:none;">  
							<%
								Department dep = (Department) request.getAttribute("department");
								String depid="";
								if(null!=dep)
									 depid = dep.getId() + "";
								
								String tablename=(String)request.getAttribute("tablename");
								String columnname=(String)request.getAttribute("columnname");
								String columnName=(String)request.getAttribute("columnName");
								String url ="relateColumn.action?tablename="+tablename+"&columnname="+columnname+"&control=0&is_judge="+is_judge+"&department.id=";
							%>
							
							<input type="hidden" name="columnname" value="<%=columnname %>" />
							<wysLib:myrelatetree tablename="<%=tablename   %>"   columnName="<%=columnName   %>" is_judge="<%=is_judge   %>"/>
							
							<wysLib:dep_list_aj rootAble="true"
								href="<%=url %>" 
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
							<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
								document.getElementById("tags.is_judge").value = <%=is_judge%>; 
							</script>
							 
							
						</td>
						<td width="5px;" valign="middle" bgcolor="#FAFCFC" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg" />
						</td>
						<%}%>
						
						<td valign="top">
							<div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg">显示部门</a>
								</div>
							
							<s:hidden name="exprot" id="exprot" />
							<div id="searchdiv" >
								<table width="100%" border="0" cellspacing="1" cellpadding="5">
									<wysLib:combinesearchForRelate></wysLib:combinesearchForRelate>
								</table>
							</div>

							<table width="95%" align="center" cellpadding="1" cellspacing="1">
								
								<wysLib:relatedepartshowlist />
								
							</table>
							<center>
								<input type='button' onclick='addRelate();' value='' id='addRelate_value'/>
							</center>
						</td>
					</tr>
				</table>
			</s:form>
			<wysLib:page></wysLib:page>
			
			
			<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="button" value="确认添加" class=textbg6  onclick="setRv();"  />
		</div>
			<br />
			
			
			
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>