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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE><wysLib:Title  /></TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" href="css/haha/style.css" />
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="stylesheet" type="text/css" href="css/haha/jquery.autocomplete.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")}
			.bottom{
				border:none;
				background-image:url(images/bofang.jpg);
				width:20px;
				height:20px; 
			} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript">
	  	  var t = "<s:property value='tablename'/>";
	    </script>
		<script type="text/javascript" src="js/haha/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="js/etc/getAuto.js"></script>
		<script type="text/javascript" src="js/etc/beizhu.js"></script>
		<script type="text/javascript" src="js/etc/selectlevel.js"></script>
		<script type="text/javascript" src="js/etc/citiesJson.js"></script>
		<script type="text/javascript" src="js/etc/autoSign.js"></script>
  </HEAD>
  <%String message=(String)request.getAttribute("message"); %>
  
  <script type="text/javascript">
  		var websites;
  		if("<s:property value='tablename'/>" == "KHDA"){
	  		$.ajax({
				  type: 'POST',
				  url: "getZidongbuqiValue.action",
				  data: {tablename:"<s:property value='tablename'/>",columnName:"KHDA_GSMC"},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != ""){
				  			zidongbuqi_array = data.split(",");
				  			websites = zidongbuqi_array;
				  		}
				  }
			});
	        $().ready(function() {   
	            $("#KHDA_GSMC").autocomplete(websites);      
	        });  
  		}else if("<s:property value='tablename'/>" == "KHDJB"){
  			$.ajax({
				  type: 'POST',
				  url: "getZidongbuqiValue.action",
				  data: {tablename:"<s:property value='tablename'/>",columnName:"KHDJB_KHMC"},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != ""){
				  			zidongbuqi_array = data.split(",");
				  			websites = zidongbuqi_array;
				  		}
				  }
			});
	        $().ready(function() {   
	            $("#KHDJB_KHMC").autocomplete(websites);      
	        }); 
  		}else if("<s:property value='tablename'/>" == "LJR"){
  			$.ajax({
				  type: 'POST',
				  url: "getZidongbuqiValue.action",
				  data: {tablename:"<s:property value='tablename'/>",columnName:"LJR_XM"},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != ""){
				  			zidongbuqi_array = data.split(",");
				  			websites = zidongbuqi_array;
				  		}
				  }
			});
	        $().ready(function() {   
	            $("#LJR_XM").autocomplete(websites);      
	        }); 
  		}
  		


</script>
  
  <script type="text/javascript">
  		function show(obj){
  			var id=$(obj).attr("id");
  			var showDivId = id.substring(1,id.length);
  			$(obj).myHoverTip(showDivId);
  		}
  		
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
	                  		
	                  		//默认值初始化
							var value = getDefaultValue_shengshixian("<s:property value='tablename'/>",columns_array[ii]);
							if(value != ""){
								document.getElementById(columns_array[ii]).value = value;
							}
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
		function alertMessage(){
			if("${elmessage}"!='null'&&"${elmessage}"!=''){
				 alert("${elmessage}!");
			}
		}
		function getDefaultValue_shengshixian(tablename,columnName){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getDefaultValue_shengshixian.action",
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
  		function changeRelateUser(this_){
  			var id = this_.getAttribute("id");
  			id = id.split("_")[1].split("___")[0];//567
  			width=screen.availWidth * 0.8;
			height=screen.availHeight * 0.8;
			var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	
			var rv = window.showModalDialog('getRelateEluserInfo.action?tablename=${tablename}&rn='+Math.random(),null,sFeature);	
			var display='';
			var returnvalue='';
			if(rv!=null)
			{
				var str=String(rv).split('_--_');
			}
			if(str!=null&&str.length>0)
			{
				for(i=0;i<str.length;i++)
				{
					var tmp =str[i].split('_-_');
					display += tmp[1] ;
					returnvalue+=tmp[0];
					if(i+1!=str.length) 
					{
						display +=',' ;
						returnvalue+='__-__';
					}
				}
			}
			if(display !="" && returnvalue !=""){
				document.getElementById("relate_"+id+"__").innerHTML = display;
				//this_.innerHTML = display;
				document.getElementById("relate_"+id).value=returnvalue; 
			}
  		}
  
  		function getJindutiao(jindutiao,this_){
  			var id = this_.getAttribute("id");
  			var array = id.split("__");
  			var value = document.getElementById(id).value;
  			if(isNaN(value)){
  				alert("请输入数字!!!");
  				document.getElementById('value_jindutiao__'+array[1]).value = "";
  				if(jindutiao == 1){
	  				$("#jindutiao_div__"+array[1]).remove();
	  				$("<div id='jindutiao_div__"+array[1]+"' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='"+value+"%'  id='show_jindutiao__"+array[1]+"'/></div>").insertAfter($("#span_red__"+array[1]));
	  			}
	  			return ;
  			}
  			if(value <0 || value > 100){
  				alert("选填写大于0小于100的数!!!");
  				document.getElementById('value_jindutiao'+array[1]).value = "";
  				if(jindutiao == 1){
	  				$("#jindutiao_div__"+array[1]).remove();
	  				$("<div id='jindutiao_div__"+array[1]+"' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='"+value+"%'  id='show_jindutiao__"+array[1]+"'/></div>").insertAfter($("#span_red__"+array[1]));
	  			}
	  			return ;
  			}
  			if(jindutiao == 1){
  				$("#jindutiao_div__"+array[1]).remove();
  				$("<div id='jindutiao_div__"+array[1]+"' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='"+value+"%'  id='show_jindutiao__"+array[1]+"'/></div>").insertAfter($("#span_red__"+array[1]));
  			}
  		}
  
  
         function message(){
              var message="<%=message %>";
              if(message!="null"&&message!=null&&message!=""){
                 alert(message);
                 document.location="lineTrainRecord_list.action";
              }
         }
         
         function doSubmit1(){
         	if($("#theme").val()==""){
         		alert("联系主题不能为空！");
         		return false;
         	}
         	if($("#content").val()==""){
         		alert("联系内容不能为空！");
         		return false;
         	}
         	var stuffArray=$("#stuff").find("input");
         	for(var i=0;i<stuffArray.length;i++){
         		//if(stuffArray[i].name=="linetrainrecord_stuff.title"){
         			if(stuffArray[i].value==""){
         				alert("附件名称和附件不能为空！");
         				return false;
         			}
         			if(stuffArray[i].name=="myFile"&&stuffArray[i].value.indexOf(".")!=-1){
         				//判断是否exe
         				var fileExName=stuffArray[i].value.substring(stuffArray[i].value.indexOf("."),stuffArray[i].value.length);
         				//alert(fileExName);
         				if(fileExName==".exe"){
         					alert("请不要上传.exe文件!");
         					return false;
         				}
         			}
         		//}
         	}
         	return true;
         }
        var ii = 0;
		function addSt(){
			ii++;
			var stuff = document.createElement("div");
			stuff.id="ds_"+ii;
			stuff.innerHTML="附件标题：<input type='text' style='width:200px;' name='linetrainrecord.lineTrainRecordStuffs.title' id='stufftt_"+ii+"' />"+
			"&nbsp;&nbsp;&nbsp;附件：<input type='file' name='myFile' />";
			document.getElementById("stuff").appendChild(stuff);
			
		}
		function deleteSt(){
			if(ii<=0)return ;
			var stuff = document.getElementById("ds_"+ii);
			document.getElementById("stuff").removeChild(stuff);
			ii--;
				
		}
		function delStuff(trainid,id){
			document.location.href="lineTrainRecordStuff_delete.action?linetrainrecord.trainid="+trainid+"&lineTrainRecordStuff.id="+id;
		}

		/**
		function calculate(input){
			var this_columnName = input.getAttribute("id");
			var is_zuoweiji_value;//作为积
			var is_zuoweihe_value;//作为和
			var relateQiujiColumn="";//乘积字段  数量+单价
			var danjia;//单价字段
			var ji_column_name;//作为积的字段
			var he_column_name;//作为和的字段
			var result = "";
			var result_array = new Array();
			var result_qiuhe = new Array();
			var relateQiuheColumn;//求和字段
			var he_value = 0;//求和的值
			var ji_value = 0;//求积的值
			//自动计算
			qiujiRelateColumns_string = '${qiujiRelateColumns}';
			array = eval("("+qiujiRelateColumns_string+")") ;
			tablename = "<s:property value='tablename'/>";
			
			
			$.each(array,function(i,arr){
				//判断字段是否是模块间计算
				var columnName = array[i].columnName;//获取列名
				var tablename = array[i].tableName;//获取列名
				$.ajax({
				  type: 'POST',
				  url: "check_column_is_calculate_in_module.action",
				  data: {columnName:columnName,tablename:tablename},
				  async:false,//同步
				  success: function(data){
				  	//如果当前的keyUp是当前的column才验证该字段是否是模块间计算
				  	if(this_columnName == columnName){
				  		data = eval("("+data+")");
				  		var tb_calculates = data.check_json_result;
				  		//alert(tb_calculates.length);
				  		if(tb_calculates.length != 0){
				  			var ids = "";
				  			for(var i=0;i<tb_calculates.length;i++){
				  				width=600;	
							 	height=400;	
							 	var url = "getValue.action?tablename="+tb_calculates[i].tableName+"&columnName="+tb_calculates[i].columnName+"&rn="+Math.random();
							  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
							  	var rv = window.showModalDialog(url,null,sFeature);	
							  	if(rv != null && rv != ''){
							  		ids += rv + ":" + tb_calculates[i].tableName + ":"+tb_calculates[i].columnName+";";
							  	}
					  		}
					  		//进行计算
					  		$.ajax({
							  type: 'POST',
							  url: "calculate.action",
							  data: {ids:ids},
							  //async:false,//同步
							  success: function(data){
							  	data = eval("("+data+")").check_json_result;
							  	$("#"+columnName).val(data);
							  	
							  }
							});
							
							//将input标签设置为readonly
							$("#"+columnName).attr("disabled",true);
				  		}
				  	}
			  		
				  }
				});
				
				//用于模块间计算的字段最后填的话获取的是原来的值
				
				is_zuoweihe_value = array[i].is_zuoweihe;
				is_zuoweiji_value = array[i].is_zuoweiji;
				if(is_zuoweiji_value == 1){
					ji_column_name = array[i].columnName;
					relateQiujiColumn = array[i].qiujiColumnName;
					if(relateQiujiColumn != ""){
						result_array = relateQiujiColumn.split(",");
					}
					//求积
					var ji = 0;
					for(var i=0;i<result_array.length;i++){
						if(i == 0){
							ji = 1;
						}
						ji = ji * $("#"+result_array[i]).val();
					}
					var ji = Math.round(ji * 100) / 100;
					$("#"+ji_column_name).val(ji);
					
				}
				
				if(is_zuoweihe_value == 1){
					
					he_column_name = array[i].columnName;
					relateQiuheColumn = array[i].qiuheColumnName;
					if(relateQiuheColumn != ""){
						result_qiuhe = relateQiuheColumn.split(",");
					}
					
					//求和
					$.each(result_qiuhe,function(i,arr){
						
						he_value = he_value + new Number($("#CSS_"+result_qiuhe[i]).val());
					});
					$("#CSS_"+he_column_name).val(he_value);
					
				}
				
			});
			
		}
		*/
		
		
		
		/**
		function checkColumnIsCalculate(tablename,columnName){
			var returnValue = "";
		  	$.ajax({
				  type: 'POST',
				  url: "checkColumnIsCalculate.action",
				  data: {tablename:tablename,columnName:columnName},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		returnValue = data;
				  }
			});
			return returnValue;
		}
		*/
		
		//表内计算==求积、求和
		function jisuan_in(this_){
			var columnName = this_.getAttribute("name");
			var tablename = "<s:property value='tablename'/>";
			var value ;
			$.ajax({
				  type: 'POST',
				  url: "biaoneijisuan.action",
				  data: {tablename:tablename,columnName:columnName},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		data = eval("("+data+")");
				  		value = data;
				  }
			});
			
			if(value != null){
				var columnName_return = value.columnName;
				var qiujiColumnName = value.qiujiColumnName;
				var qiuheColumnName = value.qiuheColumnName;
				if(typeof(qiujiColumnName) != "undefined"){
			  		var jisuan_array  = new Array();
			  		if(qiujiColumnName.indexOf(",")>=0){
			  			jisuan_array = qiujiColumnName.split(",");
			  		}
			  		
			  		var ji =  0 ;
			  		for(var i=0;i<jisuan_array.length;i++){
			  			if(ji == 0 && i == 0){
			  				ji = 1;
			  			}
			  			ji = ji * parseInt(document.getElementById(tablename+"=="+jisuan_array[i] ).value);
			  		}
			  		if(columnName_return  == columnName){
		  				document.getElementById(tablename+"=="+columnName ).value=ji ;
		  			}
		  		}
		  		if(typeof(qiuheColumnName) != "undefined"){
			  		var jisuan_array  = new Array();
			  		if(qiuheColumnName.indexOf(",")>=0){
			  			jisuan_array = qiuheColumnName.split(",");
			  		}
			  		
			  		var ji =  0 ;
			  		for(var i=0;i<jisuan_array.length;i++){
			  			ji = ji + parseInt(document.getElementById(tablename+"=="+jisuan_array[i] ).value);
			  		}
			  		if(columnName_return  == columnName){
		  				document.getElementById(tablename+"=="+columnName ).value=ji ;
		  			}
		  		}
			}
		}
		
		
		function time_columns(time_jindu_column_input){
			var time_colums = "<s:property value='time_columns'/>";
			
			var now_date = new Date();//当前时间
			var now_value_number = now_date.getTime();
			var array = time_colums.split(",");//string数组
			
			var time1 = document.getElementById(array[0]).value;
			var time2 = document.getElementById(array[1]).value;
			var time_value1;
			var time_value2;
			var time1_value_number1;
			var time1_value_number2;
			
			var value = 0;
			if(time1 != "" && time2 != ""){
				time_value1 = new Date(Date.parse(time1.replace(/-/g,"/")));
				time_value2 = new Date(Date.parse(time2.replace(/-/g,"/")));
				
				time1_value_number1 = time_value1.getTime();
				time1_value_number2 = time_value2.getTime();
				
				if(now_date < time1_value_number1 && now_date<time1_value_number2){//未开始，时间进度为0
					value = 0;
				}else if(now_date > time1_value_number1 && now_date>time1_value_number2){//已结束，时间进度为100
					value = 100;
				}else {//时间进度为	0-100
					value = (Math.abs((now_date - time1_value_number1))/Math.abs((time1_value_number2 - time1_value_number1)))*100;
				}
			
			}
			var xx;
			xx = document.getElementById("value_jindutiao__"+array[2]).value = ((Math.round(value*100)/100)).toFixed(2);//时间进度的值
			/**
			if(xx >= 100){
				xx = 100;
				document.getElementById("value_jindutiao__"+array[2]).value = xx;
			}
			*/
			
			$("#jindutiao_div__"+array[2]).remove();
	  		$("<div id='jindutiao_div__"+array[2]+"' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='"+xx+"%'  id='show_jindutiao__"+array[2]+"'/></div>").insertAfter($("#span_red__"+array[2]));
	  		
	  		
	  		//业务进度计算
	  		//calculate_yewu_jindu();
		}
		
		function calculate_yewu_jindu(this_){
			var columnName_ = this_.getAttribute("id").split("__")[1];
			var tablename = "<s:property value='tablename'/>";
			//查找表中有没有业务进度字段
			var returnValue = "";
			var returnArray = new Array();
			$.ajax({
				  type: 'POST',
				  url: "IfHasYewuJindu_column.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		returnValue = data;//3个计算字段+1个表内相关字段的id+1个开始时间字段id+1个结束时间字段id
				  		returnArray = returnValue.split(",");
				  }
			});
			var relate;
			if($("#relate_"+returnArray[3]).val() != "")
				relate = $("#relate_"+returnArray[3]).val();//相关字段复杂值
			else {
				alert("请选择相关数据！！！");
				return ;
			}
			var reualt_array ;
			if(relate != "" ){
				if(relate.indexOf("__-__")>0)
					relate_array = relate.split("__-__");
				else {
					relate_array = new Array(1);
					relate_array[0] = relate;
				}
					
			}
			
			//返回相关阶段
			var relate_tablename = "";
			
			relate_tablename = relate_array[0].split("==")[1];
			relate_tablename = relate_tablename.substring(0,relate_tablename.lastIndexOf('_'));
			
			var relateIds = "(";
			for(var i = 0;i<relate_array.length;i++){
				if(i == relate_array.length - 1)
					relateIds += relate_array[i].split("==")[0];
				else 
					relateIds += relate_array[i].split("==")[0] + ",";
			}
			
			relateIds += ")";
			
			//如何获取页面上的时间字段和值
			var time_begin_id = returnArray[4];
			var time_end_id = returnArray[5];
			
			var time_begin_column = getColumnNameById_ajax(time_begin_id);
			var time_end_column = getColumnNameById_ajax(time_end_id);
			
			var time1 = $("#"+time_begin_column).val();
			var time2 = $("#"+time_end_column).val();
			
			if(time1 != ""){
				if(time1.indexOf(":")<0 ){
					time1 = time1 + " 00:00:00";
				}
			}else {
				time1 = "";
			}
			if(time2 != ""){
				if(time2.indexOf(":")<0 ){
					time2 = time2 + " 00:00:00";
				}
			}else {
				time2 = "";
			}
			
			if(time1 != "" && time2 != ""){
				var time1_value = getDateByTime(time1);
				var time2_value = getDateByTime(time2);
				if(time1 == "" || time2 == ""){
					alert("请选择时间！！！");
					return ;
				}else if(time1_value > time2_value){
					alert("开始时间必须小于结束时间，请重新填写！！！");
					return ;
				}
			}
			
			/**
			var time1 = $("#TB_MM_146_711").val();
			var time2 = $("#TB_MM_146_713").val();
			if(time1 == "" || time2 == ""){
				alert("请选择时间！！！");
				return ;
			}
			*/
			$.ajax({
				  type: 'POST',
				  url: "getRelateInfoByIds.action",
				  data: {columnName:columnName_,relateIds:relateIds,tablename:relate_tablename,time1:time1,time2:time2},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result.toFixed(2);
				  		$("#jindutiao_div__"+columnName_).remove();
				  		$("#value_jindutiao__"+columnName_).val(data);
				  		$("<div id='jindutiao_div__"+columnName_+"' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='"+data+"%'  id='show_jindutiao__"+columnName_+"'/></div>").insertAfter($("#span_red__"+columnName_));
				  }
			});
			
		}
		
		function getDateByTime(time){
			var    strArray=time.split(" ");   
			var    strDate=strArray[0].split("-");   
			var    strTime=strArray[1].split(":");   
			var    a=new Date(strDate[0],(strDate[1]-parseInt(1)),strDate[2],strTime[0],strTime[1],strTime[2])    
			var    d   =   new Date(Date.parse(time.replace(/-/g,   "/")));
			var    value = d.getTime();  
			return value;
		}
		
		function getColumnNameById_ajax(id){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getColumnNameById_ajax.action",
				  data: {id:id},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		var ooo = new Array();
		var ppp = new Array();
		var iii = 0;//计算有多少行
		var result_table = "";//结果表
		function addRelate(returnvalue,type,id){
			if(returnvalue == ""){return;}
			var array;
			var ids = "";
			var tablename = "";
			var ids_array  = new Array();
			
			array = returnvalue.split("__-__");
			for(var i=0;i<array.length;i++){
				if(array[i].indexOf("==")>=0){
					ids_array[i] = array[i].split("==")[0];
					if(i == array.length - 1){
						ids  += array[i].split("==")[0];
						tablename = array[i].split("==")[1].substring(0,array[i].split("==")[1].lastIndexOf("_"));
						result_table = tablename;
					}
					else {
						ids += array[i].split("==")[0] + ",";
					}
				}
			}
			if(document.getElementById("insertAfter") != null || document.getElementById("column_name") != null){
				$("#insertAfter").remove();
				$("#column_name").remove();
			}
			$.ajax({
				  type: 'POST',
				  url: "getRelateListByTablenameAndIds.action",
				  data: {ids:ids,tablename:tablename},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;//obj数组
				  		iii = data.length;
				  		var tr = "";
				  		var tr_head = "<tr id='column_name'>";
				  		$.each(data,function(i,obj){
				  			
				  			var gg = -1;
				  			var pro_array = new Array();
				  			for( mm in obj){
				  				gg++;
				  				pro_array[gg] = mm.toString();
				  			}
				  			ooo = pro_array;
				  			tr += "<tr>";
				  			for(var j = 0;j<pro_array.length;j++){
				  				if(obj[pro_array[j]] != "undefined"){
				  					if(pro_array[j] == 'id' || pro_array[j] == 'department' || pro_array[j] == 'name' || pro_array[j] == 'status' || pro_array[j] == 'username'){
				  						tr += "<td ><span style='display:none;' id='"+tablename+"=="+pro_array[j]+"=="+i+"' onclick='change_(this)' >"+obj[pro_array[j]]+"</span></td>" ;
				  					}else {
				  						tr += "<td align='center' ><span id='"+tablename+"=="+pro_array[j]+"=="+i+"' onclick='change_(this)' >"+obj[pro_array[j]]+"</span></td>" ;
				  					}
				  					//获取表头
				  					if(i == 0){
				  						$.ajax({
											  type: 'POST',
											  url: "getColumnByColumnName.action",
											  data: {tablename:tablename,columnName:pro_array[j]},
											  async:false,//同步
											  success: function(data){
											  		data = eval("("+data+")").check_json_result;
											  		if(data!=undefined&&data!="undefined")
											  			tr_head += "<th><a>"+data+"</a></th>";
											  }
										});
				  					}
				  				}
				  			}
				  			
				  			
				  			//获取过程表中与结果表不对应的字段
				  			var produce_columns = new Array();
			  				$.ajax({
								  type: 'POST',
								  url: "getProduceColumns.action",
								  data: {tablename:tablename,yewu_tablename:"<s:property value='tablename'/>" },
								  async:false,//同步
								  success: function(data){
								  		var result = eval("("+data+")").check_json_result.substring(0,eval("("+data+")").check_json_result.lastIndexOf("_--_"));
								  		produce_columns = result.split("_--_");
								  		ppp = produce_columns;
								  }
							});
				  			
				  			for(var jj = 0;jj<produce_columns.length;jj++){
				  				var value = "";//为option的text
				  				var value_ = "";//为option的value
				  				if(type == 1){//自动读取
				  					value = getColumnValueByAuto("<s:property value='tablename'/>",produce_columns[jj].split("_-_")[2],produce_columns[jj].split("_-_")[0],ids_array[i],id);
					  				if(value == "null" || value==undefined)   {
					  					value="请选择";
					  				}else {
					  					value_ = value;
					  				}
				  				}
			  					if(produce_columns[jj].split("_-_")[3] == "下拉选项"){
			  						if(value=='') {
			  							value="请选择";
			  						}
			  						var default_value = produce_columns[jj].split("_-_")[4];
			  						default_value = default_value.split("==");
			  						var str = "";
			  						for (var y=0;y<default_value.length;y++){
			  							str += "<option value = "+default_value[y]+">" + default_value[y] + "<o/ption>";
			  						}
			  						tr +=   "<td align='center' >" + 
			  									"<select onchange='this.value=this.options[this.selectedIndex].value' id='"+produce_columns[jj].split("_-_")[2]+"=="+produce_columns[jj].split("_-_")[0]+"=="+i+"'>" + 
			  									"<option value="+value_+">" + 
			  										value + 
			  									"</option>" + str
			  								"</td>" ;
			  					}else if(produce_columns[jj].split("_-_")[3] == "整数" ||
			  							produce_columns[jj].split("_-_")[3] == "实数" || 
			  							produce_columns[jj].split("_-_")[3] == "文本"){
			  						tr += "<td align='center' ><input onclick='jisuan(this);' id='"+produce_columns[jj].split("_-_")[2]+"=="+produce_columns[jj].split("_-_")[0]+"=="+i+"' value="+value+"></input></td>" ;
			  					}
			  					//获取表头
			  					if(i == 0){
		  							tr_head += "<th><a>"+produce_columns[jj].split("_-_")[1]+"</a></th>";
			  					}
				  			}
				  			tr += "</tr>";
						});
						
						
						tr_head += "</tr>";
						var jqueryAppend = "<table  width='100%' align='center' cellpadding='1' cellspacing='1' id='insertAfter'>" +
						tr_head+ 
				  		"<tbody >";
			  			$(tr_head).insertAfter("#before");
						jqueryAppend += tr+"</tbody></table>";
						$(jqueryAppend).insertAfter($("#before"));
						
				  }
			});
		}
		
		function getColumnValueByAuto(yewu_tablename,produce_table,produce_column,id,danjuid){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getColumnValueByAuto.action",
				  data: {id:parseInt(id),tablename:produce_table,columnName:produce_column,yewu_tablename:yewu_tablename,danjuid:parseInt(danjuid)},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		if(data != "")
			  			returnValue = data;
				  }
			});
			return returnValue;
		}
		
		
		function biaojianqiuhe_calculate_(this_){
			var id = this_.getAttribute("id");
			var tablename = "<s:property value='tablename' />";
			var columnName = id;
			var value = 0;
			var biaojianqiuhe_column = "";
			var biaojianqiuhe_tablename = "";
			$.ajax({
				  type: 'POST',
				  url: "getBiaojianqiuheValue.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;//TB_MM_233_HJJE==TB_MM_231==TB_MM_231_1067===...
			  		var array = data.split("===");
			  		for(var i=0;i<array.length;i++){
			  			if(columnName == array[i].split("==")[0]){
			  				biaojianqiuhe_column = array[i].split("==")[2];
			  				biaojianqiuhe_tablename = array[i].split("==")[1];
			  			}
			  		}
			  		for(var j=0;j<iii;j++){
						value += Math.round(document.getElementById(biaojianqiuhe_tablename + "==" + biaojianqiuhe_column + "==" + j).value*Math.pow(10, 2))/Math.pow(10, 2);
					}
			  		this_.value = value;
				  }
			});
		}
		
		/**
		function biaojianqiuhe_calculate(this_,leng){
			var value = 0;
			var name = this_.getAttribute("name");
			var id = "";
			var array = name.split("==");
			for(var i=0;i<array.length - 1;i++){
				if(i == array.length - 2){
					id += array[i];
				}else {
					id += array[i] + "==";
				}
			}
			if(leng >0){
				for(var i=0;i<leng;i++){
					value += parseInt(document.getElementById(id + "==" + i).value);
				}
			}
			this_.value = value;
		}
		*/
		
		function getColumn_name_by_id(id){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getColumn_name_by_id.action",
				  data: {id:parseInt(id)},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		if(data != "")
			  			returnValue = data;
				  }
			});
			return returnValue;
		}
		
		//构建添加到过程表参数
		function addToProduce(index,type){
			//alert(ppp_array);
			var tablename = result_table;//结果表
			//var id = document.getElementById(tablename+"==id").value;
			//获取过程表名
			$.ajax({
				  type: 'POST',
				  url: "getProduceTableByResultTable.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
				  		if(eval("("+data+")").check_json_result != "")
				  			tablename += "," + eval("("+data+")").check_json_result;//结果表,过程表
				  }
			});
			if(tablename == "")   return;
			//构建参数
			var parameters = "";
			for(var x=0;x<index;x++){
				if(x == index - 1){
					for(var i=0;i<ooo.length;i++){
						if(document.getElementById(tablename.split(",")[0]+"=="+ooo[i]+"=="+x).innerHTML != ""){
							if(i == ooo.length - 1){
								parameters += ooo[i] + "==" + document.getElementById(tablename.split(",")[0]+"=="+ooo[i]+"=="+x).innerHTML;
							}else {
								parameters += ooo[i] + "==" + document.getElementById(tablename.split(",")[0]+"=="+ooo[i]+"=="+x).innerHTML + ",";
							}
						}else {
							if(i == ooo.length - 1){
								parameters += ooo[i] ;
							}else {
								parameters += ooo[i] + ",";
							}
						}
					}
					parameters += ",";
					for(var j=0;j<ppp.length;j++){
						if(j == ppp.length - 1){
							/**
							if(type == 1){
								if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).innerHTML != ""){
									parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).innerHTML;
								}else {
									parameters += ppp[j].split("_-_")[0];
								}
							}else {
							*/
								if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value != ""){
									parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value;
								}else {
									parameters += ppp[j].split("_-_")[0];
								}
							//}
							
						}else {
							/**
							if(type == 1){
								if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).innerHTML != ""){
									parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).innerHTML + ",";
								}else {
									parameters += ppp[j].split("_-_")[0] + ",";
								}
							}else {
							*/
								if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value != ""){
									parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value + ",";
								}else {
									parameters += ppp[j].split("_-_")[0] + ",";
								}
							//}
						}
					}
					//添加模块id,即tablename采购
					parameters += ",";
					parameters += "MODULEID==" + "<s:property value='tablename'/>";
				}else {
					for(var i=0;i<ooo.length;i++){
						if(document.getElementById(tablename.split(",")[0]+"=="+ooo[i]+"=="+x).innerHTML != ""){
							if(i == ooo.length - 1){
								parameters += ooo[i] + "==" + document.getElementById(tablename.split(",")[0]+"=="+ooo[i]+"=="+x).innerHTML;
							}else {
								parameters += ooo[i] + "==" + document.getElementById(tablename.split(",")[0]+"=="+ooo[i]+"=="+x).innerHTML + ",";
							}
						}else {
							if(i == ooo.length - 1){
								parameters += ooo[i] ;
							}else {
								parameters += ooo[i] + ",";
							}
						}
					}
					parameters += ",";
					for(var j=0;j<ppp.length;j++){
						if(j == ppp.length - 1){
							/**
							if(type == 1){
								if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).innerHTML != ""){
									parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).innerHTML;
								}else {
									parameters += ppp[j].split("_-_")[0];
								}
							}else {
							*/
								if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value != ""){
									parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value;
								}else {
									parameters += ppp[j].split("_-_")[0];
								}
							//}
						}else {
							/**
							if(type == 1){
								if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).innerHTML != ""){
									parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).innerHTML+ ",";
								}else {
									parameters += ppp[j].split("_-_")[0]+ ",";
								}
							}else {
							*/
								if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value != ""){
									parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value+ ",";
								}else {
									parameters += ppp[j].split("_-_")[0]+ ",";
								}
							//}
						}
					}
					//添加模块id,即tablename采购
					parameters += ",";
					parameters += "MODULEID==" + "<s:property value='tablename'/>" + "===";
				}
			}
			//alert(parameters);
			if(parameters != "")
				document.getElementById("tablename").value = tablename + "," + "<s:property value='tablename'/>";
			else 
				document.getElementById("tablename").value =  "<s:property value='tablename'/>";
				
			document.getElementById("parameters").value = parameters;
			
			/**
			$.ajax({
				  type: 'POST',
				  url: "addToProduce.action",
				  data: {parameters:parameters,tablename:tablename},
				  //async:false,//同步
				  success: function(data){
				  		//添加到过程表成功后，修改结果表中表间计算的字段值
				  		updateResultTableBiaojianValue(parameters,tablename.split(",")[0],document.getElementById(tablename.split(",")[0]+"==id").value);
				  }
			});
			*/
		}
		
		//修改结果表中表间计算字段值
		function updateResultTableBiaojianValue(parameters,tablename,id_String){
			var id = parseInt(id_String);
			$.ajax({
				  type: 'POST',
				  url: "updateResultTableBiaojianValue.action",
				  data: {id:id,tablename:tablename,parameters:parameters},
				  //async:false,//同步
				  success: function(data){
				  }
			});
		}
		
		function change_(this_){
			jisuan(this_);
			var id = this_.getAttribute("id");
			var value = this_.value;
			//this_.innerHTML = "<input type='text' width='10px' value='"+value+"'>";
			//this_.setAttribute("onclick","");
			//todo 判断字段存在表中，并且是number或者float类型
			var array = id.split("==");
			var tablename = array[0];
			var columnName = array[1];
			document.getElementById(id).value = value;
		}
		
		function jisuan(this_){
			var id = this_.getAttribute("id");	
			var tablename = id.split("==")[0];
			var columnName = id.split("==")[1];
			var index = id.split("==")[2];
			$.ajax({
				  type: 'POST',
				  url: "biaoneijisuan.action",
				  data: {tablename:tablename,columnName:columnName},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		data = eval("("+data+")");
				  		var columnName_return = data.columnName;
				  		var qiujiColumnName = data.qiujiColumnName;
				  		var jisuan_array  = new Array();
				  		//alert(qiujiColumnName);
				  		if(qiujiColumnName.indexOf(",")>=0){
				  			jisuan_array = qiujiColumnName.split(",");
				  		}
				  		//var arr = new Array(jisuan_array.length);
				  		
				  		var ji =  0 ;
				  		for(var i=0;i<jisuan_array.length;i++){
				  			if(ji == 0){
				  				ji = 1;
				  			}
				  			
				  			ji = ji * Math.round(document.getElementById(tablename+"=="+jisuan_array[i] + "==" + index).value*Math.pow(10, 2))/Math.pow(10, 2);
				  		}
				  		if(columnName_return  == columnName){
			  				document.getElementById(tablename+"=="+columnName + "==" + index).value=ji ;
			  			}
				  }
			});
		}
		
		
		
		function addContactTags_ajas(){
			var tablename = "<s:property value='tablename'/>";
			var parameters = "";
			var array = new Array();
			var array_= new Array();
			$.ajax({
				  type: 'POST',
				  url: "getDesigneColumns.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
			  		data = eval("("+data+")").check_json_result;
			  		if(data != ""){
		  				array = data.split(";");
		  			}
				  }
			});
			for(var i=0;i<array.length;i++){
				array_ = array[i].split("==");
				if(array_[2] == "文本" || array_[2] == "实数" || array_[2] == "整数" || array_[2] == "大文本"){
					//alert(array_[1] +"=="+ document.getElementById(array_[1]).value);
					if(document.getElementById(array_[1]).value != "")
						parameters += array_[1] + "===" + array_[2] + "===" + document.getElementById(array_[1]).value + ";";
				}else if(array_[2] == "相关字段"){
					var vvv = "";
					if(document.getElementById("relate_" + array_[0] + "_").value != ""){
						//141==LJR_XM__-__131==LJR_XM===>141,131
						var vvv = document.getElementById("relate_" + array_[0] ).value;
						var real_vvv = "";
						var a;
						if(vvv!=null&&vvv!=""){
							a = vvv.split("__-__");
							if(a!=undefined){
								for(var m=0;m<a.length;m++){
									real_vvv += a[m].split("==")[0] + ",";
								}
							}
						}
						if(real_vvv!="")	real_vvv=real_vvv.substring(0,real_vvv.lastIndexOf(","));
						parameters += array_[1] + "===" + array_[2] + "===" + real_vvv + ";";
					}
					//alert(document.getElementById("relate_" + array_[0] + "_").value);
				}else if(array_[2] == "相关负责人"){
					if(document.getElementById("relate_" + array_[0]).value != "")
						parameters += array_[1] + "===" + array_[2] + "===" + document.getElementById("relate_" + array_[0]).value + ";";
				}else if(array_[2] == "单选" || array_[2] == "复选" || array_[2] == "下拉选项"){
					var str = "";
					var ary = document.getElementsByName(array_[1]);
					if(array_[2] == "下拉选项"){
						if(ary[0].value != "")
							parameters += array_[1] + "===" + array_[2] + "===" + ary[0].value + ";";
					}else {
						for(var j=0;j<ary.length;j++){
							if(ary[j].checked){
								str += ary[j].value + "=";
							}
						}
						if(str.charAt(str.length -1 ) == "="){
							str = str.substring(0,str.length - 1);
						}
						if(str != "")
							parameters += array_[1] + "===" + array_[2] + "===" + str + ";";
					}
				}else if(array_[2] == "百分比"){
					if(document.getElementById("value_jindutiao__"+array_[1]).value != "")
						parameters += array_[1] + "===" + array_[2] + "===" + document.getElementById("value_jindutiao__"+array_[1]).value + ";";
				}else if(array_[2] == "日期"){
					if(document.getElementById(array_[1]).value != "")
						parameters += array_[1] + "===" + array_[2] + "===" + document.getElementById(array_[1]).value + ";";
				}
				/**
				else if(array_[2] == "富文本"){
					alert(document.getElementById("content").value);
					if(document.getElementById("content").value != "")
						parameters += array_[1] + "===" + array_[2] + "===" + document.getElementById("content").value + ";";
				}
				*/
				else if(array_[2] == "附件上传"){
					if(document.getElementById(array_[1]).value =="" && document.getElementById(array_[1]+"_").value !=""){
						parameters += array_[1] + "===" + array_[2] + "===" +  document.getElementById(array_[1]+"_").value + ";";
					}else if(document.getElementById(array_[1]).value !="" && document.getElementById(array_[1]+"_").value ==""){
						parameters += array_[1] + "===" + array_[2] + "===" + document.getElementById(array_[1]).value + ";";
					}else if(document.getElementById(array_[1]).value !="" && document.getElementById(array_[1]+"_").value !=""){
						parameters += array_[1] + "===" + array_[2] + "===" + document.getElementById(array_[1]).value+ "==" + document.getElementById(array_[1]+"_").value + ";";
					}
				}else if(array_[2] == "图片"){
					var h = 0;
					var w = 0;
					if(document.getElementById(array_[1]+"_h").value == ""){
						document.getElementById(array_[1]+"_h").value = h;
					}
					if(document.getElementById(array_[1]+"_w").value == ""){
						document.getElementById(array_[1]+"_w").value  =w;
					}	
					if(document.getElementById(array_[1] + "_").value != "")
						parameters += array_[1] + "===" + array_[2] + "===" + document.getElementById(array_[1]+"_h").value+ "==" + document.getElementById(array_[1]+"_w").value + "==" + document.getElementById(array_[1] + "_").value + ";";
				}
			}
			
			//执行插入操作
			//本窗口关闭
			//父窗口reload
			$.ajax({
				  type: 'POST',
				  url: "addContactTags_ajas.action",
				  data: {tablename:tablename,parameters:parameters},
				  async:false,//同步
				  success: function(data){
			  		window.returnValue = "success";
			  		window.close();
				  }
			});
		}
		
		function ready(add){
			//	var add = document.getElementById("mp3").value;
				document.getElementById("alarmPlayer").url=add;
				document.getElementById("alarmPlayer").controls.play();
			}
		
  </script>
  <body onLoad="myload();">
  	<object classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6"type="application/x-oleobject" id="alarmPlayer" height="0" width="0">
		<param name="autoStart" value="true">
		<param name="balance" value="0">
		<param name="currentPosition" value="0">
		<param name="currentMarker" value="0">
		<param name="enableContextMenu" value="true">
		<param name="enableErrorDialogs" value="false">
		<param name="enabled" value="true">
		<param name="fullScreen" value="false">
		<param name="invokeURLs" value="false">
		<param name="mute" value="true">
		<param name="playCount" value="1">
		<param name="rate" value="1">
		<param name="uiMode" value="none">
		<param name="volume" value="100">
	</object>
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
    	
		
		
		<div style="margin-top:0px;"> <s:form action="addContactTags" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
			<input type="hidden" name="tablename" value="<s:property value="tablename"/>" id="tablename"/>
			<input type="hidden" name="parameters" id="parameters"/>	
			<input type="hidden" name="relate" value="<s:property value="relate"/>"/>
			<input type="hidden" name="relateIds" id="relateIds"/>
			<input type="hidden" name="ex_columnname" id="ex_columnname"/>
			<input type="hidden" name="actionName" value="<s:property value="actionName"/>"/>
			<input type="hidden" name="fromcopy" id="fromcopy" value="<s:property value="fromcopy"/>" />	
			<!-- 用户签名信息 -->
			<s:if test="currentUser !=null && currentUser.user_add == 1">
			<table width='100%' cellpadding='1' align='center' cellspacing='1'>
			<caption>用户信息与部门</caption>
			<jsp:include page="userinfo.jsp" flush="true" />
			</table>
			</s:if>
			
			<table width='100%' cellpadding='1' align='center' cellspacing='1' id='before'>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()">
			<wysLib:addinfo />
			</tbody>
			</table>
			
			
			<div style="margin-top: 5px; text-align: center;">
				<s:if test="fromtablename != null && fromtablename != ''">
					<input type="button" value="自动读取" class="textbg6" onClick="autoGet();"/>
				</s:if>
				<s:if test="relate == 1">
					<input name="submit" type="button" value="确认添加" class=textbg6 onClick="addContactTags_ajas();"/>
				</s:if>
				<s:else>
					<input name="submit" type="submit" value="确认添加" class=textbg6 />
				</s:else>
			</div>
		</s:form></div>
		


		
  </body>
</html>