<%@ page language="java" pageEncoding="UTF-8"%> 
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>险种详情列管理</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/InsuranceCategories.js"></script> 
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style> 
		<SCRIPT type="text/javascript">
			function myload(){ 
				if("${elmessage}"!=""){
				alert( "${elmessage}!");
				} 
			} 
			function myaddColumn(){   
				document.getElementById("addColumn").style.display='block';
				document.getElementById("addColumn_").style.display='block';
			} 
			function myaddColumnClose(){   
				document.getElementById("addColumn").style.display='none';
				document.getElementById("addColumn_").style.display='none';
			} 
			function submit_(){   
				document.getElementById("addColumn").style.display='none';
				document.getElementById("addColumn_").style.display='none';
			} 
			function onviewType(){
				var viewTypeDIV = document.getElementById("viewType");
				var bsqlviewType = document.getElementById("bsqlviewType");
				if(bsqlviewType.value == '下拉列表' || bsqlviewType.value == '单选' || bsqlviewType.value == '复选')
				{   
					if(!document.getElementById("viewType_value")){
						$("<span style='color:red' id ='tishi' >格式：地球-V-中国-V-北京</span><input type='text' name='bsql.viewType_value' id='viewType_value' >").insertAfter($("#viewType"));
					}
					//viewTypeDIV.innerHTML+="<span style='color:red' id ='tishi' >格式：地球-V-中国-V-北京</span><input type='text' name='bsql.viewType_value' id='viewType_value' >";
				}if(bsqlviewType.value == '富文本'){
					document.getElementById("parametersType").value="BLOB";
				}if(bsqlviewType.value == '图片'){
					document.getElementById("parametersType").value="字符类型";
				}else{
					if(document.getElementById("viewType_value")){
						viewTypeDIV.removeChild(document.getElementById("viewType_value"));
						viewTypeDIV.removeChild(document.getElementById("tishi"));
					}
				} 
				
			}
			
			function relateColumn(number){
				var read_auto_toubaoren = "<s:property value='IC.read_auto_toubaoren'/>";
				var read_auto_beibaoren = "<s:property value='IC.read_auto_beibaoren'/>";
				var read_auto_biaodi = "<s:property value='IC.read_auto_biaodi'/>";
			
				var column_name_value = document.getElementById("column_name").value;
				var parametersType_value = document.getElementById("parametersType").value;//中文
				var parametersType_value_ = "";//英文
				if(column_name_value == ""){
					alert("请填写列名称!");
					return ;
				}
				if(parametersType_value == ""){
					alert("请填写列类型!");
					return ;
				}
				
				width=600;
				height=500;
				var url = "";
				if(number == "1"){
					var url = "relateColumnsInit.action?x="+Math.random()+"&IC.read_auto_toubaoren="+read_auto_toubaoren+"&IC.read_auto_beibaoren="+read_auto_beibaoren+"&IC.read_auto_biaodi="+read_auto_biaodi;
				}else if(number == "2"){
					var url = "relateColumnsInit2.action?x="+Math.random()+"&tablename="+document.getElementById("relate_table").value;
				}
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog(url,null,sFeature);
				//var data_type = "";
				if(rv!=undefined&&rv!=""){
					//var value = rv.split(":");
					if(number == "1"){
						document.getElementById("relate_table").value = rv;
					}else if(number == "2"){
						document.getElementById("relate_column").value = rv;
					}
					
					
					//document.getElementById("relate_column").value = value[1];
					//data_type = value[2];
				}
				
				
				if(parametersType_value == "字符类型"){
					parametersType_value_ = "VARCHAR2";
				}else if(parametersType_value == "数字类型" || parametersType_value == "小数类型"){
					parametersType_value_ = "NUMBER";
				}else if(parametersType_value == "时间类型"){
					parametersType_value_ = "DATE";
				}else if(parametersType_value == "BLOB"){
					parametersType_value_ = "BLOB";
				}else{
				}
				
				/**
				if(data_type != parametersType_value_){
					alert("关联字段的类型选择的类型不匹配,请重新选择!");
					document.getElementById("relate_table").value = "";
					document.getElementById("relate_column").value = "";
					return ;
				}
				*/
				
			}
			
			function is_qiuji_qiuhe(value){
				if(value == "数字类型" || value == "小数类型"){
					//如果是数字类型或者是小数类型，询问是否求积求和
					document.getElementById("is_qiuji_qiuhe").style.display = "block";
				}else{
					document.getElementById("is_qiuji_qiuhe").style.display = "none";
				}
			}
			
			function value_qiuji(){
				if(document.getElementById("is_qiuji").checked){
					document.getElementById("is_qiuji").value = 1;
				}else{
					document.getElementById("is_qiuji").value = 0;
				}
				
			}
			
			function value_qiuji_zuowei(){
				if(document.getElementById("is_zuoweiji").checked){
					document.getElementById("is_zuoweiji").value = 1;
				}else{
					document.getElementById("is_zuoweiji").value = 0;
				}
				
			}
			
			function value_qiuhe(){
				if(document.getElementById("is_qiuhe").checked){
					document.getElementById("is_qiuhe").value = 1;
				}else{
					document.getElementById("is_qiuhe").value = 0;
				}
			}
			
			function value_qiuhe_zuowei(){
				if(document.getElementById("is_zuoweihe").checked){
					document.getElementById("is_zuoweihe").value = 1;
				}else{
					document.getElementById("is_zuoweihe").value = 0;
				}
			}
			
			function value_from_entity(){
				if(document.getElementById("from_entity").checked){
					document.getElementById("from_entity").value = 1;
				}else{
					document.getElementById("from_entity").value = 0;
				}
			}
			
			function selectQiujiRelateColumn(){
				var tablename = "<s:property value='IC.tableName'/>";
				var parametersType_value = document.getElementById("parametersType").value;
				if(parametersType_value == "数字类型" || parametersType_value == "小数类型"){
					if(document.getElementById("is_zuoweiji").checked){
						width=600;
						height=500;
						var url = "selectQiujiRelateColumn.action?x="+Math.random()+"&tablename="+tablename;
					  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
						var rv =  window.showModalDialog(url,null,sFeature);
						if(rv!=undefined&&rv!=""){
							document.getElementById("qjrc").style.display = "block";
							var qiujiRelateColumns_input = document.getElementById("qiujiRelateColumn");
							qiujiRelateColumns_input.value = rv;
						}
					}else if(document.getElementById("is_zuoweihe").checked){
						width=600;
						height=500;
						var url = "selectQiuheRelateColumn.action?x="+Math.random()+"&tablename="+tablename;
					  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
						var rv =  window.showModalDialog(url,null,sFeature);
						if(rv!=undefined&&rv!=""){
							document.getElementById("qhrc").style.display = "block";
							var qiuheRelateColumns_input = document.getElementById("qiuheRelateColumn");
							qiuheRelateColumns_input.value = rv;
						}
					}
				}else{
					
				}
			}
			function updateColumn(tablename,columnName,ic_id){
				document.getElementById("tablename").value = tablename;
				document.getElementById("column_name").value = columnName;
				document.getElementById("IC.id").value = ic_id;
				uu.submit();
			}
		</SCRIPT>	
	</HEAD>
	<body onload="myload();">
		<form action="PG_updateColumnInit.action" method='post' name='uu'>
			<input type='hidden' name='tablename' id='tablename'/>
			<input type='hidden' name='column_name' id='column_name'/>
			<input type='hidden' name='IC.id' id='IC.id'/>
		</form>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="险种详情列管理" /></div>
			</li> 
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 30px"> 
			<table width="80%" align="center" cellpadding="1" cellspacing="1"> 
			<caption>
				表名：<s:property value="TC_Infos[0].tableName"/> (<s:property value="IC.name"/>)
			</caption>
				<tr>
					<th height="30" align="center" >
						列名称  
					</th>
					<th height="30" align="center" >
						列类型 
					</th>
					<th height="30" align="center" >
						页面显示名称
					</th>
					<th width="120" height="30" align="center" >
						页面显示方式
					</th>
					<th width="80" height="30" align="center" >
						范围
					</th>
					<th width="120" height="30" align="center" >
						求积/求和关联字段
					</th>
					<!-- <th width="120" height="30" align="center" >
						关联字段
					</th> -->
					<th width="120" height="30" align="center" >
						操作    
				   	  <a href="IC_U_Info_View_Init.action?IC.id=<s:property value="IC.id"/>" target="_blank">预览</a> 
					</th>
				</tr>
				<s:iterator value="TC_Infos" status='status'>
					<tr> 
						<td width="80" height="30" style="padding-left:8px;color:blue;" align="left">
								<center><s:property value="column_name"/></center>
						</td>
						<td width="80" height="30" style="padding-left:8px;color:blue;" align="left">
								<center><s:property value="data_type"/></center>
						</td>
						<td width="80" height="30" align="center" >
								<center><s:property value="CName"/></center>
						</td>
						<td width="80" height="30" align="center" > 
								<center><s:property value="Cview"/></center>
						</td>
						<td width="80" height="30" align="center" > 
								<center><s:property value="Cview_value"/></center>
						</td>
						<td width="120" height="30" align="center" > 
						</td>
						<!-- <td width="80" height="30" align="center" > 
						</td> -->
						<td width="80" height="30" align="center" >
							<s:if test="#status.index != 0">
								<input type = 'button' value='修改' onclick="updateColumn('<s:property value='IC.tableName'/>','<s:property value="column_name"/>','<s:property value="IC.id"/>');" class='textbg4'/>
							</s:if>
						</td>
					</tr>
				</s:iterator> 
				<s:form action="PG_IC_U_AddColumn.action" theme="simple" method="post"  name="acc_list" > 
					<tr style="display: none" id="addColumn"> 
						<td width="80" height="30" style="padding-left:8px;color:blue;" align="left"> 
								<s:textarea name="bsql.column_name" cols="15" rows="1" id="column_name"></s:textarea> 
						</td>
						<td width="80" height="30" align="center" > 
								<s:select theme="simple" name="bsql.parametersType" id="parametersType" cssClass="g-select" list="bsql.parametersTypes_" onchange="is_qiuji_qiuhe(this.value);"/> 
								<div id="is_qiuji_qiuhe" style="display:none;">
									是否求积<input type="checkbox" name="bsql.is_qiuji" id="is_qiuji"   onclick="value_qiuji();"/><br>
									是否作为积<input type="checkbox" name="bsql.is_zuoweiji" id="is_zuoweiji"   onclick="value_qiuji_zuowei();"/><br>
									是否求和<input type="checkbox" name="bsql.is_qiuji" id="is_qiuhe"   onclick="value_qiuhe();"/><br>
									是否作为和<input type="checkbox" name="bsql.is_zuoweihe" id="is_zuoweihe" onclick="value_qiuhe_zuowei();"/><br>
									是否从设备中获取单价<input type="checkbox" name="bsql.from_entity" id="from_entity" onclick="value_from_entity();"/>
								</div>
						</td>
						<td width="80" height="30" align="center" > 
								<s:textarea name="bsql.view_name" cols="15" rows="1"></s:textarea>  
						</td>
						<td width="80" height="30" align="center" > 
								<s:select theme="simple" name="bsql.viewType" id="bsqlviewType" cssClass="g-select" list="bsql.viewType_" onchange="onviewType();"/> 
						</td>
						<td width="80" height="30" align="center" > 
								<div id="viewType" style="direction: none; width: 150">   
								</div>
						</td>
						<td width="120" height="30" align="center">
							<input  type="button" value="选择求积/求和关联字段" onclick="selectQiujiRelateColumn();">
							<div style="display:none;" id="qjrc">
								<input type="text" name="bsql.qiujiRelateColumn" id="qiujiRelateColumn"/>
							</div>
							<div style="display:none;" id="qhrc">
								<input type="text" name="bsql.qiuheRelateColumn" id="qiuheRelateColumn"/>
							</div>
						</td>
						<!-- <td width="80" height="30" align="center" > 
							关联表<input readOnly name="bsql.relateTableName"  id="relate_table" onclick="relateColumn('1');" />
							关联列<input readOnly name="bsql.relateColumnName"  id="relate_column" onclick="relateColumn('2');"/>
						</td> -->
						<td width="180" height="30" align="center" >  
							<!-- <input type="button" value="数据读取设置" onclick="relateColumn();"> -->
							<input type="submit" value="提交"> 
							<input type="button" value="X" style="color:red" onClick="myaddColumnClose()"> 
						</td>
					</tr>
					<tr style="display: none" id="addColumn_">
						<td colspan="7" >  
						<s:hidden name="IC.id"></s:hidden>  
							<span style="color:red"> 注意：  1、填写险种详情表的列名  不可更改 ,不可为中文   2、列类型 不可更改 3、列名不可重复 </span> 
						</td>
					</tr>
				</s:form>
				<tr>
					<td colspan="8" align="right">   
						<input type="button" value="增加列"  onClick="myaddColumn()"> 
					</td>
				</tr>
			  </table> 
		</div> 
		<form action="relateColumnsInit.action"  method="post" name="relateColumns">
			<s:hidden name="IC.read_auto_toubaoren"/>
			<s:hidden name="IC.read_auto_beibaoren"/>
			<s:hidden name="IC.read_auto_biaodi"/>
		</form>
	</BODY>
</HTML>
