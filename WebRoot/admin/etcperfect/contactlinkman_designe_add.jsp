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
		<META http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加日志</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/makePY.js"></script>
		<script type="text/javascript" src="js/etc/citiesJson.js"></script>
  </HEAD>
  <%String message=(String)request.getAttribute("message"); %>
  
  <script type="text/javascript">
  		function setChengshi(){
  			var province = document.getElementById("province");
  			var city = document.getElementById("city");
  			var county = document.getElementById("county");
  			var province_value = province.options[province.selectedIndex].value;
  			var city_value = city.options[city.selectedIndex].value;
  			var county_value = county.options[county.selectedIndex].value;
  			
  			/**
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
  			*/
  			if(province_value != "" && city_value != "" && county_value != "")
				document.getElementById("wenbendefaultvalue").value = province_value.split(" ")[1] + 
														" " + 
														city_value.split(" ")[1] +
														" " + 
														county_value.split(" ")[1];
  		}
  		
  		
  
		var provinces  = new Array();
		var cities = new Array();
		var counties = new Array();
		$(document).ready(function(){
			var citiesString = ss;
			
			var array = eval("("+citiesString+")") ;//array数组
			
			var o = 0;//将定义的provinces数组下标从0开始
			var p = 0;
			var q = 0;
			$.each(array,function(i,n){
				if(array[i].type == "PROVINCE"){
					provinces[o] = array[i];
					$("<option ></option>").val(n.id+" "+n.name).text(n.name)
                  		.appendTo($("#province"));
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
		
		function changeProvince(){
			var id = $("#province").children('option:selected').val().split(" ")[0];
			id = parseInt(id);
			$("#city").empty();
			$.each(cities,function(i,city){
				if(city.parent_id == id){
					$("<option ></option>").val(city.id+" "+city.name).text(city.name)
                   		.appendTo($("#city"));
				}
			});
			changeCity();
		}
		
		function changeCity(){
			id = parseInt($("#city").children('option:selected').val().split(" ")[0]);
			$("#county").empty();
			$.each(counties,function(i,county){
				if(county.parent_id == id){
					$("<option ></option>").val(county.id+" "+county.name).text(county.name)
                   		.appendTo($("#county"));
				}
			});
			setChengshi();
		}
		
		function changeCounty(){
			setChengshi();
		}
		
</script>
  
  <script type="text/javascript">
  		
  
  function showselect(obj)
  {  
	  	 var returnValue; 
	   if(obj.options[0].selected )  
	  {
	  	document.getElementById("default_value").style.visibility="";
	  	}
	  else  
	  {
	  	document.getElementById("default_value").style.visibility="hidden";
	  	document.getElementById("wenbenwidth").value="";
	  	document.getElementById("wenbendefaultvalue").value="";
	  	}
	  if( obj.options[15].selected){//当前用户信息
	  	document.getElementById("default_value").style.visibility="hidden";
	  }
	  if( obj.options[16].selected){//分级下拉选项
	  	document.getElementById("selectlevel_div").style.display="block";
	  }else{
	  	document.getElementById("selectlevel_div").style.display="none";
	  }
	   if(obj.options[2].selected)  
	  {
	  	document.getElementById("span_date").style.visibility="";
	  	//document.getElementById("span_show_time_jindu").style.visibility="";
	  	}
	  else  
	  {
	  	document.getElementById("span_date").style.visibility="hidden";
	  	//document.getElementById("span_show_time_jindu").style.visibility="hidden";
	  		//document.getElementById("show_time_jindu").checked=false;
	  		/**
	  		document.getElementById("nowdate").checked=false;
	  		document.getElementById("nowdate_3").checked=false;
	  		document.getElementById("nowdate_5").checked=false;
	  		document.getElementById("nowdate_7").checked=false;
	  		*/
	  	}
	  	
	  if(obj.options[1].selected||obj.options[4].selected)  
	  {
	  	document.getElementById("span_number").style.visibility="";
	  	//document.getElementById("span_calculate").style.visibility="";
	  	document.getElementById("span_qiuji").style.visibility="";
	  	//document.getElementById("relatevalue_calculate").style.visibility="";
	  	
	  	}
	  else  
	  {
	  	document.getElementById("span_number").style.visibility="hidden";
	  	//document.getElementById("span_calculate").style.visibility="hidden";
	  	document.getElementById("span_qiuji").style.visibility="hidden";
	  	//document.getElementById("relatevalue_calculate").style.visibility="hidden";
	  		document.getElementById("sum").checked=false;
	  		//document.getElementById("calculate").checked=false;
	  		
	  		document.getElementById("is_qiuji").checked=false;
	  		document.getElementById("is_qiuhe").checked=false;
	  		document.getElementById("zuowei_ji").checked=false;
	  		document.getElementById("zuowei_he").checked=false;
	  	}
	  	
	  	
	  
	  if(obj.options[5].selected || obj.options[12].selected || obj.options[13].selected)  
	  {
	  	document.getElementById("selectvalue").style.visibility="";
	  	}
	  else  
	  {
	  	document.getElementById("selectvalue").style.visibility="hidden";
	  	document.getElementById("select_default_value").value="";
	  }

	if(obj.options[7].selected||obj.options[8].selected || obj.options[3].selected || obj.options[6].selected)//7图片  8 富文本 3大文本  6附件上传
	{
		document.getElementById("divpic").style.visibility="hidden";
	}
	else
	{
		document.getElementById("divpic").style.visibility="";
	}
//alert(document.getElementById("check").value);
	if(obj.options[8].selected)//&&!document.getElementById("check").value=="")
	{	
		document.getElementById("writible").style.display = 'none';
		document.getElementById("is_judge_for_user").style.display = "none";
		document.getElementById("is_judge").style.display = "none";
		if(!document.getElementById("check").value=="")
		{
		alert("无法定义多个富文本!!!");
		obj.options[0].selected=true;
		document.getElementById("divpic").style.visibility="";
		document.getElementById("required").style.visibility="";
		}
		else 
			document.getElementById("required").style.visibility="hidden";
		
		return ;
	}
	else
	{
		///alert("123");
		document.getElementById("required").style.visibility="";
		document.getElementById("writible").style.display = 'block';
	}
	
	
	 if(obj.options[9].selected )  
	  {
	  	//alert("演示版没有开放表间字段的功能，请与客服人员联系!!!");
	  	//window.location.href = "designeContactTagsInit.action?tablename=<s:property value='tablename'/>";
	  	document.getElementById("is_judge").style.display = "block";
	  	document.getElementById("relatevalue").style.visibility="";
	  	}
	  else  {
	  		document.getElementById("relatevalue").style.visibility="hidden";
	  		document.getElementById("is_judge").style.display = "none";
	  }
	  
	 // if(document.getElementById("wenbenwidth").value)
	 if(obj.options[11].selected){
	  	document.getElementById("span_baifenbi").style.visibility="";
	 }else{
	  	document.getElementById("span_baifenbi").style.visibility="hidden";
	  		document.getElementById("baifenbi").checked=false;
	 }
	 
	 if(obj.options[3].selected || obj.options[6].selected || 
	 obj.options[7].selected || obj.options[8].selected){
	 	document.getElementById("tr_mark").style.display = "none";
	 	document.getElementById("tr_wanzheng_mark").style.display = "none";
	 }else {
	 	document.getElementById("tr_mark").style.display = "block";
	 	document.getElementById("tr_wanzheng_mark").style.display = "block";
	 }
	 
	 if(obj.options[10].selected){
	 	document.getElementById("is_judge_for_user").style.display = "block";
	 }else{
	 	document.getElementById("is_judge_for_user").style.display = "none";
	 }
	 
	 //城市类型的字段
	 if(obj.options[14].selected){
	 	document.getElementById("default_value").style.visibility="hidden";
	 	document.getElementById("shengshixian").style.display = "block";
	 }
	  
  }
  
  
         
         function doSubmit()
         {
         	if($("#name_display").val()=="")
         	{
         		alert("自定义项目名称不能为空！");
         		return false;
         	}
         	if($("#column_name").val()=="")
         	{
         		alert("自定义列名不能为空！");
         		return false;
         	}
         	if($("#column_name").val()!=""){
         		var value = $("#column_name").val();
         		if(value.indexOf(",")>=0){
         			alert("您输入的列名称为多音字,请重新输入或者将列名项'表名+_'后面的拼音选择其中一个！！！");
         			return false;
         		}
         	}
         	if(document.getElementById("selectshow").options[5].selected)
         	{ 
         		if($("#select_default_value").val()=="")
         		{
         			alert("下拉选项不能为空！！！");
         			return false;
         			}
         	}
         	if(document.getElementById("selectshow").options[16].selected)
         	{ 
         		if(document.getElementById("sele.id").value=="")
         		{
         			alert("下拉选项不能为空！！！");
         			return false;
         		}
         		if(document.getElementById("tags.jibieshu").value=="")
         		{
         			alert("级别数不能为空！！！");
         			return false;
         		}
         	}
         	
         	if(document.getElementById("selectshow").options[9].selected)
         	{ 
         		//if(document.getElementById("select_default_value").val=="")
         		if($("#relate_modulename").val()=="")
         		{
         			alert("模块名为空！！！");
         			return false;
         		}
         		if($("#relate_columnname").val()=="")
         		{
         			alert("字段名为空！！！");
         			return false;
         		}
         			
         	}
         	
         	
         	//如果选择百分比
         	if(document.getElementById("selectshow").options[11].selected){
         		/**
         		if((document.getElementById("tags.yewu_jindu_ids").value == "" || 
         			document.getElementById("tags.yewu_jindu_relate_id").value == "" || 
         			document.getElementById("tags.yewu_jindu_relate_begintime").value == "" || 
         			document.getElementById("tags.yewu_jindu_relate_endtime").value == "") && 
         			document.getElementById("tags.time_jindu_ids").value == ""){
         			alert("您还有未选择的模块或字段，请继续选择！！！");
         			return false;
         		}
         		*/
         	}
         	
         	
         	var num=document.getElementById("wenbenwidth").value;
         	if(num!="")
         	{
         		 var reg=/^(([1-9]\d*))$/;
         		 if(!reg.test(num))
         		 {
         		 	alert("只能为正整数");
         		 	return false;
         		 }
         		 else
         		 {
         		 	if(num>99)
         		 	{
         		 		alert("不能超过99！！！");
         		 		return false;
         		 	}
         		 }
         	}
         	
         	
         	//判断相关负责人字段数据库里是否已经存在，存在，不能添加
         	if(document.getElementById("selectshow").value == "相关负责人"){
         		if(check_relate()){
         			return false;
         		}
         	}
         	
         	
         	return true;
         }
         
         function check_relate(){
         	var returnValue = false;
         	var tablename = '${tablename}';
        	var display_type = document.getElementById("selectshow").value;
         	$.ajax({
			  type: 'POST',
			  url: "check_relate_has_in_table.action",
			  data: {display_type:display_type,tablename:tablename},
			  async:false,//同步
			  success: function(data){
		  		data = eval("("+data+")").check_json_result;
		  		if(data){//如果为true,表示已经存在相关字段,不能再继续添加
		  			alert("相关负责人只能添加一次,不能重复添加!");
		  			returnValue = true;
		  		}
			  }
			});
			return returnValue;
         }
         
         	 function addModule(type)
			 {	
			 	width=600;	
			 	height=400;	
			 	var url = "getModule.action?rn="+Math.random();
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog(url,null,sFeature);	
			//	var str=String(rv);
			//	alert(String(rv));
				var str=String(rv).split("==");
				if(str!=null&&str.length>1)
				{
					if(type == 'calculate'){
						document.getElementById("relate_modulename_calculate").value=str[0];
						document.getElementById("relate_tablename_calculate").value=str[1];
					}else{
						document.getElementById("relate_modulename").value=str[0];
						document.getElementById("relate_tablename").value=str[1];
					}
					
				}
			 } 
			 
			 function addColumn(type)
			 {	
			 	if(type == 'calculate'){
			 		if(document.getElementById("relate_modulename_calculate").value=="")
					{
					  	alert("请选择模块！！！");
					   	return false;
					}
			 	}else{
			 		if(document.getElementById("relate_modulename").value=="")
					{
					  	alert("请选择模块！！！");
					   	return false;
					}
			 	}
			 	var tablename = "";
				if(type == 'calculate'){
					tablename=document.getElementById("relate_tablename_calculate").value;
				}else{
					tablename=document.getElementById("relate_tablename").value;
				}
			 	
			 	width=600;	
			 	height=400;	
			 	var url = "getModuleColumn.action?tablename="+tablename+"&rn="+Math.random();
			 	if(type == 'calculate'){//模块间计算
			 		url = url + "&type=calculate";
			 	}
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog(url,null,sFeature);	
				
				//alert(rv);
				var str=String(rv).split("==");
				if(str!=null&&str.length>1)
				{
					if(type == 'calculate'){
						document.getElementById("relate_columnname_calculate").value=str[1];
						document.getElementById("relate_defaultvalue_calculate").value=rv;
					}else{
						document.getElementById("relate_columnname").value=str[2];
						document.getElementById("relate_defaultvalue").value=rv;
					}
				}
			 }
			 
			 function select_column(str){
			 	if( str == 'zuowei_ji'){
			 		document.getElementById("select_column").style.visibility="";
			 	}else if(str == 'zuowei_he'){
			 		document.getElementById("select_column").style.visibility="";
			 	}
			 }
			 
			 function selectRelateColumn(){
			 	var tablename = "<s:property value="tablename"/>";
			 	var value = "";
			 	var array=new Array();
			 	if(document.getElementById("zuowei_ji").checked){
			 		width=600;
					height=500;
					var url = "selectQiujiRelateColumn_zidingyi.action?x="+Math.random()+"&tablename="+"<s:property value="tablename"/>";
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("qiuji_column_name").value=rv;
						array = rv.split(",");
						for(var i=0;i<array.length;i++){
							if(i == array.length - 1)
								value += getColumnByColumnName(tablename,array[i]);
							else 
								value += getColumnByColumnName(tablename,array[i]) + ","
						}
						document.getElementById("select_columns").innerHTML = "您选择的求积字段为："+value;
					}
			 	}else if(document.getElementById("zuowei_he").checked){
			 		width=600;
					height=500;
					var url = "selectQiujiRelateColumn_zidingyi.action?x="+Math.random()+"&tablename="+"<s:property value="tablename"/>";
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("qiuhe_column_name").value=rv;
						array = rv.split(",");
						for(var i=0;i<array.length;i++){
							if(i == array.length - 1)
								value += getColumnByColumnName(tablename,array[i]);
							else 
								value += getColumnByColumnName(tablename,array[i]) + ","
						}
						document.getElementById("select_columns").innerHTML = "您选择的求和字段为："+value;
					}
			 	}
				
			}
			
			function getColumnByColumnName(tablename,columnName){
				var returnValue = "";
				$.ajax({
					  type: 'POST',
					  url: "getColumnByColumnName.action",
					  data: {tablename:tablename,columnName:columnName},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != "")
				  			returnValue = data;
					  }
				});
				return returnValue;
			}
			
			//选择开始时间和结束时间字段
			function select_time_begin_end(type,tablename){
				if(type == 'biaojianqiuhe'){
					width=600;
					height=500;
					var url = "";
					
					url = "select_time_begin_end.action?x="+Math.random()+"&tablename="+tablename+"&type="+type;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						if(rv.indexOf(",")>=0){
							document.getElementById("tags.biaojianqiuhe_column").value = rv.split(",")[0];
							document.getElementById("biaojianqiuhe_column").innerHTML = "您选择的字段名称为："+rv.split(",")[1];
						}
					}
				}else if(type == 'time'){
					//判断该表中是否已经有两个时间字段
					var date_number = 0;
					
					$.ajax({
						  type: 'POST',
						  url: "checkIfHasTwoDateField.action",
						  data: {tablename:"<s:property value='tablename'/>"},
						  async:false,//同步
						  success: function(data){
					  		data = eval("("+data+")");
					  		date_number = data.check_json_result;
						  }
					});
					
					if(date_number < 2){
						alert("该表中的时间字段必须为2个以上，请重新添加！！！");
						return ;
					}
					width=600;
					height=500;
					var url = "";
					
					url = "select_time_begin_end.action?x="+Math.random()+"&tablename="+"<s:property value="tablename"/>&type="+type;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("tags.time_jindu_ids").value = rv;
					}
				}else if(type == 'yewu'){
				
					var date_number = 0;
					
					$.ajax({
						  type: 'POST',
						  url: "checkIfHasTwoDateField.action",
						  data: {tablename:"<s:property value='tablename'/>"},
						  async:false,//同步
						  success: function(data){
					  		data = eval("("+data+")");
					  		date_number = data.check_json_result;
						  }
					});
					
					if(date_number < 2){
						alert("该表中的时间字段必须为2个以上，请重新添加！！！");
						return ;
					}
					width=600;
					height=500;
					var url = "";
					
					url = "select_time_begin_end.action?x="+Math.random()+"&tablename="+tablename+"&type="+type;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						var array;
						var ids;
						var columns;
						if(rv.indexOf("=")>=0){
							array = rv.split("=");
							ids = array[0];
							columns = array[1];
						}
						document.getElementById("tags.yewu_jindu_ids").value = ids;
						document.getElementById("module_columns").innerHTML = "模块中字段名称为："+columns;
					}
				}else if(type=="jisuan_produce"){
					width=600;
					height=500;
					var url = "";
					
					url = "select_time_begin_end.action?x="+Math.random()+"&tablename="+tablename+"&type="+type;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						var array = rv.split(",");
						document.getElementById("tags.jisuan_produce_relate_id").value = array[0];
						document.getElementById("jisuan_produce_relate_id").innerHTML = "模块中字段名称为："+array[1];
					}
				}else if(type == "jisuan_result"){
					width=600;
					height=500;
					var url = "";
					
					url = "select_time_begin_end.action?x="+Math.random()+"&tablename="+tablename+"&type="+type;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						var array = rv.split(",");
						document.getElementById("tags.jisuan_result_relate_id").value = array[0];
						document.getElementById("jisuan_result_relate_id").innerHTML = "模块中字段名称为："+array[1];
					}
				}else if(type == "ziduan_wanzheng"){
					width=600;
					height=500;
					var url = "";
					
					url = "select_time_begin_end.action?x="+Math.random()+"&tablename="+tablename+"&type="+type;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						var array = rv.split("=");
						document.getElementById("tags.wanzheng").value=array[0];
						document.getElementById("wanzheng_columnName").innerHTML = "您设置的字段为："+array[1];
						
					}
				}
				
			}
			
			function select_table(){
				width=600;	
			 	height=400;	
			 	var url = "getModule.action?rn="+Math.random();
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog(url,null,sFeature);	
			  	if(rv!=undefined&&rv!=""){
			  		if(rv.indexOf("==")>0){
			  			var arr = rv.split("==");
			  			var tablename = arr[1];
			  			
			  			document.getElementById("select_module_name").innerHTML = "模块名称为："+getModuleNameByTablename(tablename);
			  			select_time_begin_end('yewu',tablename);
			  		}
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
			
			function select_inner_table_column(number){
				tablename = "<s:property value='tablename'/>";
				width=600;
				height=500;
				var url = "";
				var type = 'yewu_relate_'+number;
				
				url = "select_time_begin_end.action?x="+Math.random()+"&tablename="+tablename+"&type="+type;
				var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog(url,null,sFeature);
				
				var rv_array = new Array();
				var id ;
				var returnValue ;
				if(rv!=undefined&&rv!=""){
					if(rv.split(",").length = 2){
						rv_array = rv.split(",");
						id = rv_array[0];
						returnValue = rv_array[1];
					}
					if(number == 1){//关联字段
						//判断该字段是否是相关字段
						if(checkColumnIsJutileixingById('相关字段',id) == 1){
							document.getElementById("tags.yewu_jindu_relate_id").value = rv_array[0];
							document.getElementById("yewu_jindu_relate_id_column").innerHTML = "字段名称为："+rv_array[1];
						}
						else {
							alert("您选择的字段不是相关字段，请重新选择！！！");
							return ;
						}
					}else if(number == 2){//开始时间字段
						//判断该id的字段是否是时间字段
						if(checkColumnIsJutileixingById('日期',id) == 1){
							document.getElementById("tags.yewu_jindu_relate_begintime").value = rv_array[0];
							document.getElementById("yewu_jindu_relate_begintime_column").innerHTML = "字段名称为："+rv_array[1];
						}
						else {
							alert("您选择的字段不是时间字段，请重新选择！！！");
							return ;
						}
					}else if(number == 3){//结束时间字段
						//判断该id的字段是否是时间字段
						if(checkColumnIsJutileixingById('日期',id) == 1){
							document.getElementById("tags.yewu_jindu_relate_endtime").value = rv_array[0];
							document.getElementById("yewu_jindu_relate_endtime_column").innerHTML = "字段名称为："+rv_array[1];
						}
						else {
							alert("您选择的字段不是时间字段，请重新选择！！！");
							return ;
						}
					}
				}
			}
			
			
			function checkColumnIsJutileixingById(type,id){
				var returnValue = 0;
				$.ajax({
					  type: 'POST',
					  url: "checkColumnIsJutileixingById.action",
					  data: {id:id,type:type},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data == "true")
				  			returnValue = 1;
					  }
				});
				return returnValue;
			}
			
			function select_show(this_,jindu_type){
				var select_time_begin_end = document.getElementById('select_time_begin_end');
				var select_time_begin_end_yewu = document.getElementById('select_time_begin_end_yewu');
				if(jindu_type == "time_jindu"){
					if(this_.checked) {
						select_time_begin_end.style.visibility = '';
						
						//如果选择时间进度的时候，业务进度不显示
						document.getElementById('yewu_jindu').checked = false;
						select_time_begin_end_yewu.style.visibility='hidden';
					} else {
						select_time_begin_end.style.visibility='hidden';
					}
					
					
				}else if(jindu_type == "yewu_jindu"){
					if(this_.checked) {
						select_time_begin_end_yewu.style.visibility='';
						
						document.getElementById('time_jindu').checked = false;
						select_time_begin_end.style.visibility='hidden';
					} else {
						select_time_begin_end_yewu.style.visibility='hidden';
					}
				}
			}
			
			function show_biaojianjisuan(this_){
				if(this_.checked){
					document.getElementById("jisuan_type").style.display = "block";
					document.getElementById("sum").checked = false;
				}else {
					document.getElementById("jisuan_type").style.display = "none";
				}
			}
			
			/**
			表间计算
			*/
			function jisuan_select_relate_column(){
				tablename = "<s:property value='tablename'/>";
				width=600;
				height=500;
				var url = "";
				type = "jisuan_relate";
				url = "jisuan_select_relate.action?x="+Math.random()+"&tablename="+tablename+"&type="+type;
				var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				var rv =  window.showModalDialog(url,null,sFeature);
				if(rv!=undefined&&rv!=""){
					var rv_array = rv.split(",");
					var id = rv_array[0];
					var returnValue = rv_array[1];
					//判断该字段是否是相关字段
					if(checkColumnIsJutileixingById('相关字段',id) == 1){
						document.getElementById("tags.jisuan_relate_id").value = rv_array[0];
						document.getElementById("jisuan_relate_id_column").innerHTML = "字段名称为："+rv_array[1];
					}
					else {
						alert("您选择的字段不是相关字段，请重新选择！！！");
						return ;
					}
				}
			}
			
			function select_produce_table(){
				width=600;	
			 	height=400;	
			 	var url = "getModule.action?rn="+Math.random();
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog(url,null,sFeature);	
			  	if(rv!=undefined&&rv!=""){
			  		if(rv.indexOf("==")>0){
			  			var arr = rv.split("==");
			  			var tablename = arr[1];
			  			document.getElementById("tags.jisuan_produce_table_name").value = tablename;
			  			document.getElementById("jisuan_produce_table_name").innerHTML = "模块名称为："+getModuleNameByTablename(tablename);
			  			select_time_begin_end('jisuan_produce',tablename);
			  		}
			  	}
			}
			
			function select_result_table(){
				width=600;	
			 	height=400;	
			 	var url = "getModule.action?rn="+Math.random();
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog(url,null,sFeature);	
			  	if(rv!=undefined&&rv!=""){
			  		if(rv.indexOf("==")>0){
			  			var arr = rv.split("==");
			  			var tablename = arr[1];
			  			document.getElementById("tags.jisuan_result_table_name").value = tablename;
			  			document.getElementById("jisuan_result_table_name").innerHTML = "模块名称为："+getModuleNameByTablename(tablename);
			  			select_time_begin_end('jisuan_result',tablename);
			  		}
			  	}
			}
			
			function getAddMinusNo(type){
				var value = "";
				var radios = document.getElementsByName("jisuan_relate_t");
				for(var i=0;i<radios.length;i++){
					if(radios[i].checked){
						value = radios[i].value;
					}
				}
			
				width=600;	
			 	height=400;	
			 	var url = "getModule_checkbox.action?rn="+Math.random();
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog(url,null,sFeature);	
			  	if(rv!=undefined&&rv!=""){
		  			var modulenames = "";
		  			var tablenames = "";
		  			var arr = rv.split(",");
		  			var modulename_array = new Array(arr.length);
		  			var tablename_array = new Array(arr.length);
		  			for(var i=0;i<arr.length ;i++){
		  				modulename_array[i] = arr[i].split("==")[0];
		  				tablename_array[i] = arr[i].split("==")[1];
		  			}
		  			for(var i=0;i<modulename_array.length;i++){
		  				if(i == modulename_array.length - 1){
		  					modulenames += modulename_array[i];
		  				}else {
		  					modulenames += modulename_array[i] + ",";
		  				}
		  			}
		  			for(var i=0;i<tablename_array.length;i++){
		  				if(i == tablename_array.length - 1){
		  					tablenames += tablename_array[i];
		  				}else {
		  					tablenames += tablename_array[i] + ",";
		  				}
		  			}
		  			if(type == 1){
		  				document.getElementById("addValue").value = tablenames;
		  				document.getElementById("addValue_modulename").innerHTML = "模块名称为："+modulenames;
		  			}else if(type==2){
		  				document.getElementById("minusValue").value = tablenames;
		  				document.getElementById("minusValue_modulename").innerHTML = "模块名称为："+modulenames;
		  			}else if(type==3){
		  				document.getElementById("noValue").value = tablenames;
		  				document.getElementById("noValue_modulename").innerHTML = "模块名称为："+modulenames;
		  			}
		  			document.getElementById("jisuan_relate_type").value = tablenames + "==" + value;
			  	}
			}
			
			function select_ziduan_wanzheng(){
				type="ziduan_wanzheng";
				tablename = document.getElementById("relate_tablename").value;//结果表
				//获取过程表
				$.ajax({
					  type: 'POST',
					  url: "getProduceTableByResultTable.action",
					  data: {tablename:tablename},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		tablename = data;
					  }
				});
				if(tablename == ""){
					alert("请选择模块名称！！！");
					return ;
				}
				select_time_begin_end(type,tablename);
			}
			
			function show_biaojianqiuhe(this_){
				if(this_.checked){
					document.getElementById("biaojianqiuhe").value=1;
					document.getElementById("biaojianqiuhe_span").style.display = "block";
				}
				else {
					document.getElementById("biaojianqiuhe").value=0;
				
					document.getElementById("biaojianqiuhe_span").style.display = "none";
					document.getElementById("jisuan_relate_type").value = "";
					document.getElementById("tags.jisuan_result_table_name").value = "";
					document.getElementById("tags.jisuan_result_relate_id").value = "";
					
					document.getElementById("addValue_modulename").innerHTML = "";
					document.getElementById("minusValue_modulename").innerHTML = "";
					document.getElementById("noValue_modulename").innerHTML = "";
					document.getElementById("jisuan_result_table_name").innerHTML = "";
					document.getElementById("jisuan_result_relate_id").innerHTML = "";
				}
			}
			
			function select_produce_table(){
				width=600;	
			 	height=400;	
			 	var type = "biaojianqiuhe";//表间求和
			 	var url = "getModule.action?rn="+Math.random();
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog(url,null,sFeature);	
			  	if(rv!=undefined&&rv!=""){
			  		var tablename = rv.split("==")[1];
			  		document.getElementById("tags.biaojianqiuhe_tablename").value = tablename;
			  		select_time_begin_end(type,tablename);
			  		document.getElementById("biaojianqiuhe_tablename").innerHTML = "您选择的模块为：" + rv.split("==")[0];
			  	}
			}
			
			//判断该字段是否在该表中已经存在
			function checkColumnIsExistByTable(this_){
				var value =  this_.value;
				if(value.indexOf("<s:property value='tablename'/>"+"_")<0){
					alert("列名必须以" + '${tablename}' + "_开始，请重新填写!!!");
         			$("#column_name").val('${tablename}' + "_");
         			this_.focus();
					return ;
				}
				if(value == '${tablename}' + "_"){
					alert("列名填写有错误，请重新填写!!!");
					this_.focus();
					return ;
				}
				$.ajax({
					  type: 'POST',
					  url: "checkColumnIsExistByTable.action",
					  data: {tablename:"<s:property value='tablename'/>",columnName:value},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data){
				  			alert("列名已经存在，请重新填写!!!");
				  			this_.focus();
				  			return ;
				  		}
					  }
				});
			}
			
			function getValue(this_){
				document.getElementById("column_name").value = "<s:property value='tablename'/>" + "_" + pinyin(this_.value);
			}
			
			//设置自动读取
			function select_relate_column(){
				var type="fromtablename";
				var tablename = "<s:property value='tablename'/>";//合同
				$.ajax({
					  type: 'POST',
					  url: "getFromtablenameByTablename.action",
					  data: {tablename:tablename},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data == ""){
				  			alert("设置字段自动读取还未设置数据来自的模块，请重新设置!!!");
				  			return ;
				  		}else {
				  			width=600;	
						 	height=400;	
						 	var url = "getModuleColumn.action?tablename="+data+"&rn="+Math.random() + "&type=" + type;
						  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
						  	var rv = window.showModalDialog(url,null,sFeature);	
						  	if(rv!=undefined&&rv!=""){
						  		var array = rv.split("==");
						  		//表名==列名==字段名称==字段类型
						  		//判断当前添加列的字段类型和选择的字段类型是否一致
						  		var leixing = checkSelectColumnIsRight(array[0],array[1]);
						  		var selectshow = document.getElementById("selectshow").value;
						  		if(leixing != selectshow){
						  			alert("您要选择自动读取字段的类型和您要添加的字段的类型不一致，请重新选择");
						  			return ;
						  		}
						  		document.getElementById("tags.fromtablename_columnname").value = array[1];
						  		document.getElementById("fromtablename_columnname").innerHTML = "您选择的字段为：" + array[2] + "(" + array[1] + ")";
						  	}
				  		}
					  }
				});
			}
			
			function checkSelectColumnIsRight(tablename,columnName){
				var returnValue = "";
				$.ajax({
					  type: 'POST',
					  url: "checkSelectColumnIsRight.action",
					  data: {tablename:tablename,columnName:columnName},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != ""){
				  			returnValue = data;
				  		}
					  }
				});
				return returnValue;
			}
			
			function show_addMinusNo(number){
				if(number == 1){
					document.getElementById("span_1").style.display = "block";
					document.getElementById("span_2").style.display = "none";
					document.getElementById("span_3").style.display = "none";
					
					document.getElementById("div_1").style.display = "block";
					document.getElementById("div_2").style.display = "none";
					document.getElementById("div_3").style.display = "none";
				}else if(number == 2){
					document.getElementById("span_1").style.display = "none";
					document.getElementById("span_2").style.display = "block";
					document.getElementById("span_3").style.display = "none";
					
					document.getElementById("div_1").style.display = "none";
					document.getElementById("div_2").style.display = "block";
					document.getElementById("div_3").style.display = "none";
				}else if(number == 3){
					document.getElementById("span_1").style.display = "none";
					document.getElementById("span_2").style.display = "none";
					document.getElementById("span_3").style.display = "block";
					
					document.getElementById("div_1").style.display = "none";
					document.getElementById("div_2").style.display = "none";
					document.getElementById("div_3").style.display = "block";
				}
			}
			
			
			function show_beizhu(){
				if(document.getElementById("column_name").value==""){
					alert("请先填写列名!");
					return;
				}
				 width=screen.availWidth * 0.8;
				 height=screen.availHeight * 0.8;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("relateColumn.action?tablename=BZGL&columnname=BZGL_MC&control=0&is_judge=1&rn="+Math.random(),null,sFeature);
				 var arr;
				 var relates = "";
				 var relates_info = "";
				 if(rv!=undefined&&rv!=""){
				 alert(rv);
				 	arr = rv.split("_--_");
				 	if(arr!=null && arr.length>0){
				 		for(var i=0;i<arr.length;i++){
				 			if(i == arr.length - 1){
				 				relates += arr[i].split("==")[0];
				 				relates_info += arr[i].split("==")[1].split("_-_")[1];
				 			}else{
				 				relates += arr[i].split("==")[0] + ",";
				 				relates_info += arr[i].split("==")[1].split("_-_")[1] + "_-_";
				 			}
				 		}
				 	}
				 	if(relates!="" && relates_info!=""){
				 		document.getElementById("tagsMark.relates").value = relates;
				 		document.getElementById("tagsMark.relates_info").value = relates_info;
				 	}
					document.getElementById("tagsMark.columnname").value = document.getElementById("column_name").value;
				 }
			}
			
			//选择起始节点
			function select_qishijiedian(){
				 width=600;
				 height=500;
			  	 var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
				 var rv =  window.showModalDialog("list_selectLevel.action?showDialog=1&x="+Math.random(),null,sFeature);
				 //alert(rv);
				 if(rv!=undefined&&rv!=""){
					 //var bh=rv.split("_");
					 var bh=rv.split("-=wys=-");
					 document.getElementById("sele.name").innerHTML=bh[1];
					 document.getElementById("sele.id").value=bh[2];
				 }
			}
			//选择级别数
			function select_jiebieshu(){
				var selectLevelid = parseInt(document.getElementById("sele.id").value);
				if(selectLevelid == 0){
					alert("请先选择起始节点");
					return ;
				}else{
					//查找当前选择的节点下有多少级
					var icount = 0;
					var selectHtml_begin = "<select name='tags.jibieshu' id='tags.jibieshu'>";
					var selectHtml_end = "</select>";
					var selectHtml_body = "";
					var selectHtml = "";
					$.ajax({
					  type: 'POST',
					  url: "checkJibieshu.action",
					  data: {'selectLevel.id':selectLevelid},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		icount = parseInt(data);//有多少个级别
				  		//构建select标签
				  		selectHtml_body += "<option value='1' selected='selected'>1</option>";
				  		if(icount !=0){
				  			for(var i=2;i<icount+2-1;i++){
				  				selectHtml_body += "<option value='"+i+"'>"+i+"</option>";
				  			}
				  		}
					  }
					});
					selectHtml = selectHtml_begin + selectHtml_body + selectHtml_end;
					if(document.getElementById("tags.jibieshu")!=null){
						document.getElementById("tags.jibieshu").parentNode.removeChild(document.getElementById("tags.jibieshu"));
					}
					$(selectHtml).appendTo($("#selectlevel_div"));
				}
			}
  </script>
  <body onLoad="setChengshi();">
  		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="管理列表" /></div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
    	
		<!-- 内容 -->
		<s:form action="contactlinkmanDesigneAdd" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
				<input type="hidden" name="qiuji_column_name" id="qiuji_column_name"/>
				<input type="hidden" name="qiuhe_column_name" id="qiuhe_column_name"/>
				<div style="margin-top: 0px; text-align: center;">
				<table cellpadding="1" cellspacing="1" width="700px">
					<tr>
						<td width="160" height="30" align="center" >
							自定义项目名称：<span  style="color:red">*</span>
						</td>
						<td >
							<label>
								<s:textfield onKeyUp="query(this,document.getElementById('column_name'),1,'${tablename}');" name="tags.name_display"  id="name_display" size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							自定义列名：<span  style="color:red">*</span>
						</td>
						<td >
							<label>
								<input type="text" name="tags.column_name"  id="column_name"   style='width:300px'/>
							</label>
							<span style="color:red"><s:property value='elmessage'/></span>
						</td>
					</tr>
					<tr id='tr_mark' style="display:block;">
						<td width="160" height="30" align="center" >
							字段简单备注：
						</td>
						<td >
							<label>
								<s:textfield name="tags.mark"  id="mark"></s:textfield>
							</label>
						</td>
					</tr>
					<tr id='tr_wanzheng_mark' style="display:block;">
						<td width="160" height="30" align="center" >
							字段完整备注：
						</td>
						<td >
							<table>
								<tr>
									<td>
										<label>
											<s:textarea rows="5" cols="50%" name="tags.wanzheng_mark"  id="wanzheng_mark"></s:textarea>
										</label>
									</td>
									<td>
										<input type="button" onclick="show_beizhu();" value="选择相关备注"></input>
										<input type="hidden"  name="tagsMark.columnname" id="tagsMark.columnname" value=""/>
										<input type="hidden"  name="tagsMark.tablename" value="<s:property value='tablename'/>" />
										<input type="hidden"  name="tagsMark.relates" id="tagsMark.relates" value=""/>
										<input type="hidden"  name="tagsMark.relates_info" id="tagsMark.relates_info" value=""/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							自定义项目类型：<span  style="color:red">*</span>
						</td>
						<td>
						<input type="hidden" id="check" value="<s:property value="checkRichtext"/>"/>
								<s:select  name="tags.display_type" onchange="showselect(this);"  id="selectshow"  
								list="{'实数','日期','大文本','整数','下拉选项','附件上传','图片','富文本','相关字段','相关负责人','百分比','单选','复选','城市','当前用户信息','分级下拉选项'}" 
								theme="simple" headerKey="文本" headerValue="文本"  />  
							<span id="span_date" style="visibility:hidden">
								<input id="nowdate" type="checkbox" name="timecheck" value="nowdate" onClick="if(this.checked) document.getElementById('date_number').value=0; else document.getElementById('date_number').value='';"/>是否显示当前时间&nbsp;&nbsp;<span style="color:red"><br>
								往后<input type='text' id='date_number' name='date_number' width='50px'/>天</span><br>
								日期格式<input id="tags.timeformat" name="tags.timeformat" style="width:250px;" /><br>
								<span style="color:red">
									格式如：<br>
									yyyy-MM-dd<br>
									yyyy/MM/dd<br>
									yyyy年MM月dd日<br>
								</span>
							</span>
							<br>
							<div style='display:none;' id="selectlevel_div">
								<span style="color:red">选择起始节点</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="选择" onclick="select_qishijiedian();" /> 
								<span id="sele.name"></span>
								<input type="hidden" name="tags.selectlevelid" id="sele.id" value=0 />
								<br>
								<span style="color:red">可供选择的级别数</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="点击获取级别数" onclick="select_jiebieshu();" />
							</div>
							<br>
							<div id='is_judge' style='display:none;'>
								<input type="checkbox" name="tags.is_judge" id="tags.is_judge" value='1'/>是否进行权限判断 
								<input type="checkbox" name="tags.showfinalpass" id="tags.showfinalpass" value='1'/>只显示终审通过的数据 
							</div>
							<div id='is_judge_for_user' style='display:none;'>
								<input type="checkbox" name="tags.is_judge_for_user" id="tags.is_judge_for_user" value='1'/>是否进行权限判断 
							</div>
							<div id='writible' style='display:block;'>
								不限<input type='radio' name='tags.writible' checked value='1' />
								初审可写<input type='radio' name='tags.writible'  value='2' />
								终审可写<input type='radio' name='tags.writible'  value='3'/>
							</div>
							<br>
							<span id="span_number" style="visibility:hidden">
								<input id="sum" type="checkbox" name="sumcheck" value="sum"/>是否显示求和
								<br>
								<input id="biaojianjisuan" type="checkbox" name="biaojianjisuancheck" value="biaojianjisuan" onClick="show_biaojianjisuan(this);"/>是否表间计算
								<br>
								<span id="jisuan_type" style="display:none;">
									<span style="color:red">增加、减少、不增减处理是针对终审通过时候的操作</span><br>
									<input type="radio" name="jisuan_relate_t" value="add" checked onclick="show_addMinusNo(1)"/>增加
									<input type="radio" name="jisuan_relate_t" value="minus" onclick="show_addMinusNo(2)"/>减少
									<input type="radio" name="jisuan_relate_t" value="no" onclick="show_addMinusNo(3)"/>不增减
									<span id= 'span_1' style='color:red;display:block'>选择表间计算为增加的模块</span>
									<div id='div_1' style="display:block;"><input type='button' value='选择' onclick='getAddMinusNo(1);' class='textbg4'/><input type='hidden' id='addValue'/><span style='color:blue' id='addValue_modulename'></span></div>
									<span id= 'span_2' style='color:red;display:none'>选择表间计算为减少的模块</span>
									<div id='div_2' style="display:none;"><input type='button' value='选择' onclick='getAddMinusNo(2);' class='textbg4'/><input type='hidden' id='minusValue'/><span style='color:blue' id='minusValue_modulename'></span></div>
									<span id= 'span_3' style='color:red;display:none'>选择表间计算为不增减的模块</span>
									<div id='div_3' style="display:none;"><input type='button' value='选择' onclick='getAddMinusNo(3);' class='textbg4'/><input type='hidden' id='noValue'/><span style='color:blue' id='noValue_modulename'></span></div>
									<input type='text' id='jisuan_relate_type' name="tags.jisuan_relate_type"/>
									<span >
									<br>
										<!--<span style="color:red">指定本表关联字段</span>
										<input type="button" value="选择" onclick="jisuan_select_relate_column();" class="textbg4"/>
										<input type="text" name="tags.jisuan_relate_id" id="tags.jisuan_relate_id"/>
										<br>
										<span id="jisuan_relate_id_column" style="color:blue"></span>
										<br>
										<span style="color:red">指定过程表及过程表中字段</span>
										<br>
										<input type="button" value="选择" onclick="select_produce_table();" class="textbg4"/>
										<input type="text" name="tags.jisuan_produce_table_name" id="tags.jisuan_produce_table_name"/>
										<input type="text" name="tags.jisuan_produce_relate_id" id="tags.jisuan_produce_relate_id"/>
										<br>
										<span id="jisuan_produce_table_name" style="color:blue"></span><br>
										<span id="jisuan_produce_relate_id" style="color:blue"></span>
										<br>-->
										<span style="color:red">指定结果表及结果表中字段</span>
										<input type="button" value="选择" onClick="select_result_table();" class="textbg4"/>
										<input type="hidden" name="tags.jisuan_result_table_name" id="tags.jisuan_result_table_name"/>
										<input type="hidden" name="tags.jisuan_result_relate_id" id="tags.jisuan_result_relate_id"/>
										<br>
										<span id="jisuan_result_table_name" style="color:blue"></span><br>
										<span id="jisuan_result_relate_id" style="color:blue"></span>
									</span>
								</span>
								<br>
								<input id="biaojianqiuhe" type="checkbox" name="tags.biaojianqiuhe_check" value=0 onClick="show_biaojianqiuhe(this);"/>是否表间求和
								<span id="biaojianqiuhe_span" style="display:none;">
									<input type="button" value="选择" onClick="select_produce_table();" class="textbg4"/>
									<input type="hidden" name="tags.biaojianqiuhe_tablename" id="tags.biaojianqiuhe_tablename"/>
									<input type="hidden" name="tags.biaojianqiuhe_column" id="tags.biaojianqiuhe_column"/><br>
									<span id="biaojianqiuhe_tablename" style="color:red"></span><br>
									<span id="biaojianqiuhe_column" style="color:red"></span>
								</span>
							</span>
							<span id="span_baifenbi" style="visibility:hidden">
								<br>
								<input id="baifenbi" type="checkbox" name="baifenbicheck" value="baifenbi" onClick="if(this.checked) document.getElementById('teshu_jindu').style.visibility = ''; else document.getElementById('teshu_jindu').style.visibility = 'hidden';"/>是否显示进度条
								<br>
								<span id="teshu_jindu" style="visibility:hidden">
									<input id="time_jindu" type="checkbox" name="time_jindu" value="time_jindu" onClick="select_show(this,'time_jindu');"/>时间进度条
									<br>
									<span id="select_time_begin_end" style="visibility:hidden">
										<span style="color:red">选择开始时间和结束时间字段</span>
										<input type="button"  value="选择" class="textbg4" onClick="select_time_begin_end('time','');"/>
										<input type="text" name="tags.time_jindu_ids" id="tags.time_jindu_ids"/>
									</span>
									<br>
									<input id="yewu_jindu" type="checkbox" name="yewu_jindu" value="yewu_jindu" onClick="select_show(this,'yewu_jindu');"/>业务进度条
									<br>
									<span id="select_time_begin_end_yewu" style="visibility:hidden">
										<span style="color:red">选择业务进度关联的模块中字段</span>
										<input type="button"  value="选择" class="textbg4" onClick="select_table();"/>
										<input type="hidden" name="tags.yewu_jindu_ids" id="tags.yewu_jindu_ids"/>
										<br>
										<span id="select_module_name" style="color:blue"></span>
										<br>
										<span id="module_columns" style="color:blue"></span>
										<br>
										<span style="color:red">选择业务进度本表的相关字段</span>
										<input type="button"  value="选择" class="textbg4" onClick="select_inner_table_column(1);"/>
										<input type="hidden" name="tags.yewu_jindu_relate_id" id="tags.yewu_jindu_relate_id"/>
										<span  id="yewu_jindu_relate_id_column" style="color:blue"></span>
										<br>
										<span style="color:red">选择业务进度本表的开始时间字段</span>
										<input type="button"  value="选择" class="textbg4" onClick="select_inner_table_column(2);"/>
										<input type="hidden" name="tags.yewu_jindu_relate_begintime" id="tags.yewu_jindu_relate_begintime"/>
										<span  id="yewu_jindu_relate_begintime_column" style="color:blue"></span>
										<br>
										<span style="color:red">选择业务进度本表的结束时间字段</span>
										<input type="button"  value="选择" class="textbg4" onClick="select_inner_table_column(3);"/>
										<input type="hidden" name="tags.yewu_jindu_relate_endtime" id="tags.yewu_jindu_relate_endtime"/>
										<span  id="yewu_jindu_relate_endtime_column" style="color:blue"></span>
									</span>
								</span>
							</span><br>
							<span id="span_qiuji" style="visibility:hidden">
								<input id="is_qiuji" type="checkbox" name="qiujicheck" value="is_qiuji"/>该字段是否用于求积
								<input id="is_qiuhe" type="checkbox" name="qiuhecheck" value="is_qiuhe"/>该字段是否用于求和
								<input id="zuowei_ji" type="checkbox" name="zuoweijicheck" value="zuowei_ji" onClick="select_column('zuowei_ji')"/>该字段是否作为积
								<input id="zuowei_he" type="checkbox" name="zuoweihecheck" value="zuowei_he" onClick="select_column('zuowei_he')"/>该字段是否作为和
								<br>
								<span id="select_column" style="visibility:hidden">
									<input type="button" class="textbg" value="选择字段" onClick="selectRelateColumn();"/>
									<span id='select_columns' style='color:red'></span>
								</span>
							</span>
							<div id="shengshixian" style="display:none;">
								默认&nbsp;&nbsp;省市县&nbsp;
								<select id="province" name="province" onchange="changeProvince();" style="width:100">  
							         <option id="option_in_province">  
							         	请选择省
							         </option>  
								</select>&nbsp;
								<select id="city" name="city" onchange="changeCity();" style="width:100" >  
							         <option id="option_in_city" >  
							              请选择市  
							         </option>  
								</select>&nbsp;
								<select id="county" name="county" onchange="changeCounty();" style="width:100">  
							         <option id="option_in_county">  
							              请选择县
							         </option>  
								</select>&nbsp;
							</div>
							<div id="default_value" style="visibility:visible">
								默认值(选填)<input type="text" id="wenbendefaultvalue" name="wbdefault" >
								文本框宽度(选填，百分比)<input id="wenbenwidth" name="wbwidth" type="text" style="width:40px;" >
							</div>
								<div id="selectvalue" style="visibility:hidden">
								<input type="text" style="width:400px;" name="tags.default_value_2"  id="select_default_value" ></input>
								<span  style="color:red">每个选项以"=="隔开</span>
								</div>
								
								<div id="relatevalue" style="visibility:hidden">
								模块<span  style="color:red">*</span>
								<input type="text" width="600"  onclick="addModule('');" id="relate_modulename" readonly name="relate_tablename"></input>
								<input type="hidden" id="relate_tablename">
								字段<span  style="color:red">*</span>
								<input type="text" width="600"  onclick="addColumn('');"  id="relate_columnname" readonly name="relate_columnname"></input>
								<input type="hidden" name="tags.default_value" id="relate_defaultvalue">
								
								是否完整显示<span  style="color:red">*</span>
								<input id="relateIsShowComplete" type="checkbox" name="relateIsShowComplete" value="relateIsShowComplete" onClick="if(this.checked){document.getElementById('ziduan_wanzheng_show').style.display='block';} else{document.getElementById('ziduan_wanzheng_show').style.display='none';}"/>
								<br>
								<s:if test="can_select_show_columns == 1">
									<span style="color:red" style="display:none;" id="ziduan_wanzheng_show">设置完整显示的字段<input type="button" value="选择" class="textbg4" onClick="select_ziduan_wanzheng();"/></span>
									<input type="hidden" name="tags.wanzheng" id="tags.wanzheng"/><br>
									<span style="color:blue" id="wanzheng_columnName"></span>
									<input type="hidden" name="tags.default_value" id="relate_defaultvalue_calculate">
								</s:if>
								</div>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							字段自动读取设置：<span  style="color:red">*</span>
						</td>
						<td >
							<label>
								<input type="button" value="选择" onclick='select_relate_column();' class='textbg4'/>
							</label>
							<input type="hidden" name="tags.fromtablename_columnname" id="tags.fromtablename_columnname"/>
							<span style='color:red' id="fromtablename_columnname"></span>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							该项目出现在：
						</td>
						<td >
							<input type="checkbox" name="display" value ="add_display" checked>添加页面是否显示
							<input type="checkbox" name="display" value ="update_display" checked>修改页面是否显示
							<input type="checkbox" name="display" value ="view_display" checked>查看页面是否显示
							<div id="divpic">
								<input type="checkbox" name="display" value ="list_display"  checked>列表页面是否显示
								<input type="checkbox" name="display" value ="mutilsearch_display" checked>组合查询是否显示
								<input type="checkbox" name="display" value ="departsearch_display" checked>部门查询是否显示
							</div>
							<div id="required">
								<input type="checkbox" name="display" value ="required">是否必填<br>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"  align="center" >
						<span  style="color:red">注意：新增加的字段只能隐藏不能删除，请慎重添加。</span>
						</td>
					</tr>
	                
					<input type="hidden" name="tags.table_name" value="<s:property value="tablename"/>" />
					<input type="hidden" name="module_ids" id="module_ids"/>
				</table>
				
		</div>
		
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认添加" class=textbg6 />
		</div>
		</s:form>
  </body>
</html>