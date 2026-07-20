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
		<TITLE><wysLib:Title  /></TITLE>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript">
			var moduleManage_ondemo = "<s:property value='moduleManage.ondemo' />";
			var moduleZDY_addjsp = "<s:property value='moduleZDY.addjsp' />";
			var moduleZDY_updateJsp = "<s:property value='moduleZDY.updatejsp' />";
			var moduleZDY_viewJsp = "<s:property value='moduleZDY.viewjsp' />";
			var actionName = "<s:property value='actionName' />";
			var tbname = "<s:property value='tablename' />";
		</script>
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
		<script type="text/javascript" src="js/etc/citiesJson.js"></script>
		
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript" src="js/newversion/searchcontacttags.js"></script>
		
		<script type="text/javascript">
  		
  		function setChengshi(columnname){
  			var province = document.getElementById(columnname+"_province");
  			var city = document.getElementById(columnname+"_city");
  			var county = document.getElementById(columnname+"_county");
  			var province_value = province.options[province.selectedIndex].value;
  			var city_value = city.options[city.selectedIndex].value;
  			var county_value = county.options[county.selectedIndex].value;
  			
  			
  			if(province_value == ""){
  				province_value = "请选择省";
  			}else {
  				province_value = province_value.split(" ")[1];
  			}
  			if(city_value == ""){
  				city_value = "请选择市";
  			}else {
  				city_value = city_value.split(" ")[1];
  			}
  			if(county_value == ""){
  				county_value = "请选择区";
  			}else {
  				county_value = county_value.split(" ")[1];
  			}
  			document.getElementById(columnname).value = province_value + 
  														" " + 
  														city_value +
  														" " + 
  														county_value;
  		}
  		
  
  
  		var columns = "";//城市字段
		var provinces  = new Array();
		var cities = new Array();
		var counties = new Array();
		
		var columns_array;
		var table_columns_is_chengshi = select_columnname_by_tablename_chengshi("<s:property value='tablename'/>");
			
		if(table_columns_is_chengshi != ""){
			columns_array = table_columns_is_chengshi.split(",");
		}
		
		$(document).ready(function(){
			var citiesString = ss;
			
			var array = eval("("+citiesString+")") ;//array数组
			
			var o = 0;//将定义的provinces数组下标从0开始
			var p = 0;
			var q = 0;
			var province_column = "";
			$.each(array,function(i,n){
				if(array[i].type == "PROVINCE"){
					provinces[o] = array[i];
					if(columns_array != undefined){
						$.each(columns_array,function(ii,nn){
							province_column = "#"+columns_array[ii]+"_province";
							$("<option ></option>").val(n.id+" "+n.name).text(n.name)
	                  		.appendTo($(province_column));
						});
					}
                  	o++;
				}
				
				else if(array[i].type == "CITY"){
					cities[p] = array[i];
					p++;
				}
				else if(array[i].type == "DISTRICT"){
					counties[q] = array[i];
					q++;
				}
				
			});
			
		});
		
		function select_columnname_by_tablename_chengshi(tablename){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "select_columnname_by_tablename_chengshi.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		if(data != "")
			  			returnValue = data;
				  }
			});
			return returnValue;
		}
		
		function changeProvince(columnname){
			
			var id = $("#"+columnname + "_province").children('option:selected').val().split(" ")[0];
			id = parseInt(id);
			$("#"+columnname + "_city").empty();
			$.each(cities,function(i,city){
				if(city.parent_id == id){
					$("<option ></option>").val(city.id+" "+city.name).text(city.name)
                   		.appendTo($("#"+columnname + "_city"));
				}
			});
			setChengshi(columnname);
			
			changeCity(columnname);
			
		}
		
		function changeCity(columnname){
			
			id = parseInt($("#"+columnname + "_city").children('option:selected').val().split(" ")[0]);
			$("#"+columnname + "_county").empty();
			$.each(counties,function(i,county){
				if(county.parent_id == id){
					$("<option ></option>").val(county.id+" "+county.name).text(county.name)
                   		.appendTo($("#"+columnname + "_county"));
				}
			});
			setChengshi(columnname);
			
		}
		
		function changeCounty(columnname){
			setChengshi(columnname);
		}
		
</script>
		
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
				if(document.getElementById("relate") != null){
					if(document.getElementById("relate").value == '${realname}'){
						document.getElementById("relate").value = "";
					}
				}
				
				document.getElementById("pageNow").value=0;
				searchLog_form.submit();
			}
			function columnsearch(col)
			{
			//	alert(col);
				document.getElementById("ordercolumn").value=col;
				var sc=document.getElementById("ordersc").value;
				if(sc=="")
				{	
					document.getElementById("ordersc").value="desc";
				}
				if(sc=="desc") document.getElementById("ordersc").value="";
				
				search();
			}
			
			
			function add_info(){
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
				if(chinese_columnName != 'null'){
					document.getElementById("add_info").innerHTML = "添加"+chinese_columnName;
				}else {
					document.getElementById("add_info").innerHTML = "添加" + getModuleNameByTablename(tablename);
				}
			}
			
			function getModuleNameByTablename(tablename){
				var returnValue = "";
				$.ajax({
					  type: 'POST',
					  url: "getModuleNameByTablename.action",
					  data: {tablename:tablename},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != "")
				  			returnValue = data;
					  }
				});
				return returnValue;
			}
		</script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
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
	<body onLoad="add_info();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:NavigationForZDY  />
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
			
			function show_beizhu(entityid){
					var tablename = "<s:property value='tablename'/>";
					width=800;
					height=600;
					var url = "select_audit_mark_by_entityid.action?id="+entityid+"&tablename="+tablename+"&x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
				}
			function pass(id){
				document.getElementById("contactid").value=id;
				viewContact.action="verifysearchpassTags.action";
				viewContact.submit();
			}
			function nopass(id){
				document.getElementById("contactid").value=id;
				viewContact.action="verifysearchnopassTags.action";
				viewContact.submit();
			}
			
			
			function view(id){
					document.getElementById("contactid").value=id;
					viewContact.action = "viewContactTags.action";
					viewContact.submit();
			}
			function view_ZDY(id){
					document.getElementById("contactid").value=id;
					viewContact.action = "viewContactTagsZDY.action";
					viewContact.submit();
			}
			function del(id){
				if(window.confirm("确认删除？")){
					document.getElementById("contactid").value=id;
					viewContact.action="deleteContactSearchTags.action";
					viewContact.submit();
				}
			}
			
			function update(id,actionName){
				document.getElementById("contactid").value=id;
				document.getElementById("actionName").value=actionName;
				//document.getElementById("ismodify").value="modify";
				viewContact.action="updateContactTagsInit.action";
				viewContact.submit();
			}
			
			function update_ZDY(id,actionName){
				document.getElementById("contactid").value=id;
				document.getElementById("actionName").value=actionName;
				//document.getElementById("ismodify").value="modify";
				viewContact.action="updateContactTagsInitZDY.action";
				viewContact.submit();
			}
			
			
			function verify_pass(ids){
				width=500;
					height=400;
					var url = "fieldAuditMark.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("auditMark.audit_mark").value=rv;
					}
			
				document.getElementById("contactids").value=ids;
				document.getElementById("auditOrder").value="000";
				viewContact.action="verifypassContactTags.action";
				
				if(window.confirm("确认提交？")){
					if(document.getElementById("auditMark.audit_mark").value == null || 
						document.getElementById("auditMark.audit_mark").value == ""){
						alert("请重新填写备注!!!");
						return ;
					}
					viewContact.submit();
				}
			}
			function verify_nopass(ids){
				width=500;
					height=400;
					var url = "fieldAuditMark.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("auditMark.audit_mark").value=rv;
					}
					
					
				document.getElementById("contactids").value=ids;
				document.getElementById("auditOrder").value="000";
				viewContact.action="verifynopassContactTags.action";
				if(window.confirm("确认提交？")){
					if(document.getElementById("auditMark.audit_mark").value == null || 
						document.getElementById("auditMark.audit_mark").value == ""){
						alert("请重新填写备注!!!");
						return ;
					}
					viewContact.submit();
				}
			}
			
			
			function allow_update(ids){
				document.getElementById("contactids").value=ids;
				viewContact.action="myaddAllowUpate.action";
				viewContact.submit();
			}
			function allow_del(ids){
				if(window.confirm("确认删除？")){
					document.getElementById("contactids").value=ids;
					viewContact.action="myaddAllowDel.action";
					viewContact.submit();
				}
			}
			function noallow_update(ids){
				document.getElementById("contactids").value=ids;
				viewContact.action="myaddNoAllowUpate.action";
				viewContact.submit();
			}
			function noallow_del(ids){
				document.getElementById("contactids").value=ids;
				viewContact.action="myaddNoAllowDel.action";
				viewContact.submit();
			}
			
			
				function toexcel(exprot) { 
					document.getElementById("exprot").value=exprot;
					acc_list.submit();
				} 
			 	function page(i){ 
			 		document.getElementById("pageNow").value=i;
			 		if(document.getElementById("relate") != null){
						if(document.getElementById("relate").value == '${realname}'){
							document.getElementById("relate").value = "";
						}
					}
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
			</script>
			<s:form action="viewContactTags.action" method="post" name="viewContact"
				theme="simple">
				<s:hidden name="id" id="contactid"/>
				<s:hidden name="ids" id="contactids"/>
				<s:hidden name="tablename" />
				<s:hidden name="actionName" id="actionName"></s:hidden>
				<input type="hidden" name="fromActionName" value="<s:property value='actionName' />" />
				<input type="hidden" name="auditMark.audit_mark" id="auditMark.audit_mark"/>
				<input type="hidden" name="auditMark.moduleid" value="<s:property value='tablename'/>" />
				<input type="hidden" name="auditOrder" id="auditOrder"/>
			</s:form>
			
			<s:form action="searchContactTags.action" method="post" name="searchLog_form"
				theme="simple">
				<s:hidden name="pN" id="pageNow"  />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="tablename" />
				
				<s:hidden id="ordersc" name="tags.ordersc" />
				<s:hidden id="ordercolumn" name="tags.ordercolumn"  />
				
				<table width="100%">
					<tr>
						<td width="120" valign="top" bgcolor="#FAFCFC" id="tree_list_td" style="padding:8px;display:none;"> 
							<%
								Department dep = (Department) request
											.getAttribute("department");
									String depid = dep.getId() + "";
								String tablename=(String)request.getAttribute("tablename");
								String url ="searchContactTags.action?tablename="+tablename+"&department.id=";
							%>
							<wysLib:dep_list_aj rootAble="true"
								href="<%=url%>"
								iname="department.idd" ivalue="<%=depid%>"></wysLib:dep_list_aj>
					  		<script type="text/javascript">
								w0.setValues([new DEP(<s:property value="department.id"/>,<s:property value="department.lid"/>,<s:property value="department.rid"/>)]);
							</script>
						</td>
						<td width="5px;" valign="middle" bgcolor="#FAFCFC" style="padding: 0px">
							<img src="images/leftmenu/main_55_1.gif" style="cursor: hand"
								onclick="changeTreeDisplay(this)" id="showimg"/>						
						</td>
						<td valign="top">
							<div style="text-align: left;" id="showtree">
									<a href="javascript:showtree(true);" class="textbg">显示部门</a>
							</div>
							<s:hidden name="exprot" id="exprot" />
							<div id="searchdiv" style="display:block;margin-top:-5px;">
								<table width="100%" border="0" cellspacing="1" cellpadding="5">
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<wysLib:combinesearch></wysLib:combinesearch>
								</tbody>
								</table>
							</div>
					
							<wysLib:departshowlist type="1"/>
					
						</td>
					</tr>
				</table>
			</s:form>
			<wysLib:page></wysLib:page>
			
			<br />
			<center><a class='textbg' href="addContactTagsInit.action?tablename=<s:property value='tablename'/>"><span id="add_info"></span></a></center>
			
			
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>