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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/menu.js"></script>
		<script type="text/javascript" src="js/tree/dep.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/etc/citiesJson.js"></script>
		<script type="text/javascript" src="js/newversion/jquery.toolsbar.js"></script>
		<script type="text/javascript" src="js/newversion/customauditToolsbar.js"></script>
		
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
			//var citiesString = '${areaList}';
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
			
				function download(tablename)
			{
				document.getElementById("pageNow").value=0;
				if(document.getElementById("relate") != null){
					if(document.getElementById("relate").value == '${realname}'){
						document.getElementById("relate").value = "";
					}
				}
				document.getElementById("downloadcontrol").value=tablename;
				searchLog_form.submit();
			}
			function onload()
			{
				var down=document.getElementById("downloadcontrol").value;
				if(down!="")
				  window.location.href="downloadExcel.action?down="+down+"&tablename=<s:property value='tablename'/>"; 
				
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
	<body onLoad="onload();add_info();" >
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
			//批量修改审核状态
			function access_ALL(changeValue,type){
				//首先清空
				document.getElementById("ids").value = "";
				document.getElementById("tags.status").value = "";
				
				var alertValue = "";
				if(type == 'access_ALL')
					alertValue = "确定全部通过？";
				else if(type == 'noaccess_ALL')
					alertValue = "确定全部不通过？";
				else if(type == 'access_SELECT')
					alertValue = "确定选中数据通过？";
				else if(type == 'noaccess_SELECT')
					alertValue = "确定选中数据不通过？";
					
					
				if(window.confirm(alertValue)){
					var billIDs = "";
			     	var cks= document.getElementsByName("id_");
					for(var i = 0 ; i < cks.length; i++){
						if(type == 'access_ALL' || type == 'noaccess_ALL'){
							cks[i].checked= true;
						}
					}
					for(var i = 0 ; i < cks.length; i++){
						if(cks[i].checked){
							if(i == cks.length - 1)
								billIDs += cks[i].value;
							else 
								billIDs += cks[i].value + ",";
						}
					}
					
					if(billIDs == ""){
						alert("请选择需要变更状态的行！！！");
						return ;
					}
					
					document.getElementById("ids").value = billIDs;
					document.getElementById("tags.status").value = changeValue;
					xx.submit();
				}
			}
			
			function select_All(){
				var cks= document.getElementsByName("id_");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= true;
				}
			}
			
			function select_Bux(){
				var cks= document.getElementsByName("id_");
				for(var i = 0 ; i < cks.length; i++){
					cks[i].checked= false;
				}
			}
			
			function allow_update(id){
				document.getElementById("contactid").value=id;
				viewContact.action="myaddAllowUpate.action";
				viewContact.submit();
			}
			function allow_del(id){
				if(window.confirm("确认删除？")){
					document.getElementById("contactid").value=id;
					viewContact.action="myaddAllowDel.action";
					viewContact.submit();
				}
			}
			function noallow_update(id){
				document.getElementById("contactid").value=id;
				viewContact.action="myaddNoAllowUpate.action";
				viewContact.submit();
			}
			function noallow_del(id){
				document.getElementById("contactid").value=id;
				viewContact.action="myaddNoAllowDel.action";
				viewContact.submit();
			}
			function finalpass(id){
				document.getElementById("contactid").value=id;
				viewContact.action="verifypassFinalTags.action";
				viewContact.submit();
			}
			function finalnopass(id){
				document.getElementById("contactid").value=id;
				viewContact.action="verifynopassFinalTags.action";
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
			
			function update(id){
				document.getElementById("contactid").value=id;
				//document.getElementById("ismodify").value="modify";
				viewContact.action="updateContactTagsInit.action";
				viewContact.submit();
			}
			
			function update_(id,final_,actionName){
				document.getElementById("contactid").value=id;
				document.getElementById("final_").value=final_;
				document.getElementById("actionName").value=actionName;
				viewContact.action="updateContactTagsInit.action";
				viewContact.submit();
			}
			function update_ZDY(id,final_,actionName){
				document.getElementById("contactid").value=id;
				document.getElementById("final_").value=final_;
				document.getElementById("actionName").value=actionName;
				viewContact.action="updateContactTagsInitZDY.action";
				viewContact.submit();
			}
			
			
			function verify_pass(id,auditOrder){
				width=500;
					height=400;
					var url = "fieldAuditMark.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("auditMark.audit_mark").value=rv;
					}
					
					
				document.getElementById("contactid").value=id;
				document.getElementById("auditMark.entityid").value=id;
				document.getElementById("auditOrder").value=auditOrder;
				viewContact.action="verifypassTags.action";
				if(window.confirm("确认提交？")){
					if(document.getElementById("auditMark.audit_mark").value == null || 
						document.getElementById("auditMark.audit_mark").value == ""){
						alert("请重新填写备注!!!");
						return ;
					}
					viewContact.submit();
				}
			}
			function verify_nopass(id,auditOrder){
				width=500;
					height=400;
					var url = "fieldAuditMark.action?x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("auditMark.audit_mark").value=rv;
					}
			
				document.getElementById("contactid").value=id;
				document.getElementById("auditMark.entityid").value=id;
				document.getElementById("auditOrder").value=auditOrder;
				viewContact.action="verifynopassTags.action";
				
				if(window.confirm("确认提交？")){
					if(document.getElementById("auditMark.audit_mark").value == null || 
						document.getElementById("auditMark.audit_mark").value == ""){
						alert("请重新填写备注!!!");
						return ;
					}
					viewContact.submit();
				}
			}
			
			
				function toexcel(exprot) { 
					document.getElementById("exprot").value=exprot;
					searchLog_form.submit();
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
				
				function show_beizhu(entityid){
					var tablename = "<s:property value='tablename'/>";
					width=800;
					height=600;
					var url = "select_audit_mark_by_entityid.action?id="+entityid+"&tablename="+tablename+"&x="+Math.random();
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
				}
				
		
			 function importdata(tablename)
			 {
			 	window.location.href="importExcelDataInit.action?tablename="+tablename;
			 }
			 
			 function updateManagePerson(updateType){
			 	var checkObj = document.getElementsByName("id_");
			    var billIDs = "";
			    if(updateType == "all"){
			    	for (i = 0; i < checkObj.length; i++) {
						checkObj[i].checked = true;
					}
			    }
			    
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
				 
				 width=800;	
				 height=600;	
				 var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';
				 var rv = window.showModalDialog('getRelateEluserInfo.action?rn='+Math.random(),null,sFeature);
				 
				 var array;
				 var ids = "";
				 var names = "";
				 if(rv != null && rv != 'undefined'){
			 		array = rv.split("_--_");
			 		for(var i=0;i<array.length;i++){
			 			if(array[i].indexOf("_-_")){
			 				if(i == array.length -1){
			 					ids += array[i].split("_-_")[0];
			 					names += array[i].split("_-_")[1];
			 				}else {
			 					ids += array[i].split("_-_")[0] + ",";
			 					names += array[i].split("_-_")[1] + ",";
			 				}
			 			}
			 		}
				 }
			     //批量修改
			     document.getElementById("update_user_ids").value = ids;//更新后的负责人ids
			     document.getElementById("update_ids").value = billIDs;//数据行ids
			     document.getElementById("updateType").value = updateType;//全部更新还是部分更新
			     xxx.submit();
				 
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
			
			
			function goto_audit(){
				tablename = "<s:property value='tablename'/>";
				window.location.href = "customAuditManageInit.action?tablename="+tablename+"&custype=1";
			}
			</script>
			<s:form action="updateManagePerson.action" name="xxx" method="post">
				<input type="hidden" name="tablename" value="<s:property value='tablename'/>"/>
				<input type="hidden" name="update_ids" id="update_ids"/>
				<input type="hidden" name="updateType" id="updateType"/>
				<input type="hidden" name="update_user_ids" id="update_user_ids"/>
			</s:form>
			<s:form action="viewContactTags.action" method="post" name="viewContact"
				theme="simple">
				<s:hidden name="id" id="contactid"/>
				<s:hidden name="tablename" />
				<input type="hidden" name="final_" value=1 id="final_"/>
				<s:hidden name="actionName" id="actionName"></s:hidden>
				<input type="hidden" name="auditMark.audit_mark" id="auditMark.audit_mark"/>
				<input type="hidden" name="auditMark.moduleid" value="<s:property value='tablename'/>" />
				<input type="hidden" name="auditMark.entityid" id="auditMark.entityid" />
				<s:hidden name="auditOrder" />
			</s:form>
			<table width="100%" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align='center'><input type="button" value="查看审核流程" onClick="javascript:goto_audit();"/></td>
				</tr>
					</table>
			<s:form action="customAuditListContactTags.action" method="post" name="searchLog_form"
				theme="simple">
				<s:hidden name="pN" id="pageNow"  />
				<s:hidden name="pS" />
				<s:hidden name="department.id" />
				<s:hidden name="tablename" />
					
				<s:hidden id="ordersc" name="tags.ordersc" />
				<s:hidden id="ordercolumn" name="tags.ordercolumn"  />
				
				<input type="hidden" id="downloadcontrol" name="tags.downloadcontrol"  value="<s:property value="tags.downloadcontrol"/>"/>
				<s:hidden name="auditOrder" />
				<table width="100%">
					<tr>
						
						<td valign="top">
							<s:hidden name="exprot" id="exprot" />
							
							<s:if test="table_type == 2">
								<table width="100%" border="0" cellspacing="1" cellpadding="5" style="margin-bottom:50px;">
									<tbody onMouseOut="changeback()" onMouseOver="changeto()">
									<wysLib:combinesearchForProduce></wysLib:combinesearchForProduce>
									</tbody>
								</table>
							</s:if>
							<div id="searchdiv" style="display:block;margin-top:-5px;" >
							<table width="100%" border="0" cellspacing="1" cellpadding="5">
								<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<wysLib:combinesearch></wysLib:combinesearch>
								</tbody>
							</table>
							</div>
					
				<!--   <table width="95%" align="center" cellpadding="1" cellspacing="1">-->
					<s:if test="table_type == 2">
						<wysLib:finaldepartshowlistForProduce />
					</s:if>
					<s:else>
						<wysLib:finaldepartshowlistForAudit type="1"/>
					</s:else>
					
			<!--  	</table> -->
						</td>
					</tr>
				</table>
			</s:form>
			<wysLib:page></wysLib:page>
			<br />
			<center><a class='textbg' href="addContactTagsInit.action?tablename=<s:property value='tablename'/>"><span id="add_info"></span></a></center>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>