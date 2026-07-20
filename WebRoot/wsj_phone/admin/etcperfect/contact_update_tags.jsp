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
<HTML>
	<HEAD>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>数据修改</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="editor/fckeditor.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<script type="text/javascript" src="js/hotkey.js"></script>
		<script type="text/javascript" src="js/etc/beizhu.js"></script>
		<script type="text/javascript" src="js/etc/selectlevel.js"></script>
		<script type="text/javascript" src="js/etc/citiesJson.js"></script>
		<script type="text/javascript" src="js/etc/autoSign.js"></script>
		
		
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
			//省市县初始化赋值到相应的select
			var province_in_back ;
			var city_in_back ;
			var county_in_back  ;
			
		
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
							//原来的省
							var shengshixian_array;
							var value = getShengshixian(parseInt("<s:property value='id'/>"),"<s:property value='tablename'/>",columns_array[ii]);
							if(value != ""){
								shengshixian_array = value.split(" ");
								province_in_back = shengshixian_array[0];
								city_in_back = shengshixian_array[1];
								county_in_back = shengshixian_array[2];
								$("#"+columns_array[ii]+"_option_in_province").text(province_in_back) ;
								$("#"+columns_array[ii]+"_option_in_province").attr("value",province_in_back);
								
								
								$("#"+columns_array[ii]+"_option_in_city").text(city_in_back) ;
								$("#"+columns_array[ii]+"_option_in_city").attr("value",city_in_back);
								
								$("#"+columns_array[ii]+"_option_in_county").text(county_in_back) ;
								$("#"+columns_array[ii]+"_option_in_county").attr("value",county_in_back);
							}
							
							
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
		
		function getShengshixian(id,tablename,columnName){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getShengshixian.action",
				  data: {tablename:tablename,id:id,columnName:columnName},
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
			
			function setid(i)
			{
				//alert(i);
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
						value = 0.00;
					}else if(now_date > time1_value_number1 && now_date>time1_value_number2){//已结束，时间进度为100
						value = 100.00;
					}else {//时间进度为	0-100
						value = ((Math.abs((now_date - time1_value_number1))/Math.abs((time1_value_number2 - time1_value_number1)))*100).toFixed(2);
					}
				
				}
				
				var xx = document.getElementById("value_jindutiao__"+array[2]).value = (Math.round(value*100)/100) ;//时间进度的值
				
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
				  		returnValue = data;//3个计算字段+1个表内相关字段的id
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
			/**
			for(var j=0;j<relate_tablename.split("_").length - 1;i++){
				if(j == relate_tablename.split("_").length - 2)
					relate_tablename += relate_tablename.split("_")[i];
				else 
					relate_tablename += relate_tablename.split("_")[i] +"_"; 
			}
			*/
			var relateIds = "(";
			for(var i = 0;i<relate_array.length;i++){
				if(i == relate_array.length - 1)
					relateIds += relate_array[i].split("==")[0];
				else 
					relateIds += relate_array[i].split("==")[0] + ",";
			}
			
			relateIds += ")";
			
			if(relateIds == "()"){
				relateIds = "";
			}
			
			
			//如何获取页面上的时间字段和值
			var time_begin_id = returnArray[4];
			var time_end_id = returnArray[5];
			
			var time_begin_column = getColumnNameById_ajax(time_begin_id);
			var time_end_column = getColumnNameById_ajax(time_end_id);
			
			
			var time1 = $("#"+time_begin_column).val() ;
			var time2 = $("#"+time_end_column).val() ;
			
			if(time1.indexOf(":")<0 ){
				time1 = time1 + " 00:00:00";
			}
			if(time2.indexOf(":")<0 ){
				time2 = time2 + " 00:00:00";
			}
			
			var time1_value = getDateByTime(time1);
			var time2_value = getDateByTime(time2);
			
			if(time1 == "" || time2 == ""){
				alert("请选择时间！！！");
				return ;
			}else if(time1_value > time2_value){
				alert("开始时间必须小于结束时间，请重新填写！！！");
				return ;
			}
			$.ajax({
				  type: 'POST',
				  url: "getRelateInfoByIds.action",
				  data: {columnName:columnName_,relateIds:relateIds,tablename:relate_tablename,time1:time1,time2:time2},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
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
		
		
		
		
		function jisuan(this_){
			var id = this_.getAttribute("id");	
			var tablename = id.split("==")[0];//过程表
			var columnName = id.split("==")[1];
			var index = parseInt(id.split("==")[2]) ;
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
				  			if(ji == 0 && i == 0){
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
			  		for(var j=0;j<iii + iii_;j++){
						value += Math.round(document.getElementById(biaojianqiuhe_tablename + "==" + biaojianqiuhe_column + "==" + j).value*Math.pow(10, 2))/Math.pow(10, 2);
					}
					
			  		this_.value = value;
				  }
			});
		}
		
		
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
		
		var relate_thing_id = "";
		
		function getRelate_thing_id(){//获取业务表中相关字段的id
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getRelate_thing_id.action",
				  data: {tablename:"<s:property value='tablename'/>"},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		var iii = 0;//计算有多少行
		//获取该记录中有多少条相关数据，主要是获取全局变量iii
		function load_(){
			relate_thing_id = getRelate_thing_id();
			var returnvalue ;
			if(relate_thing_id != undefined && relate_thing_id != ""){
				returnvalue = document.getElementById("relate_"+relate_thing_id).value;//如何获取983？？
				var array;
				var ids = "";
				var tablename = "";
				
				array = returnvalue.split("__-__");
				for(var i=0;i<array.length;i++){
					if(array[i].indexOf("==")>=0){
						if(i == array.length - 1){
							ids  += array[i].split("==")[0];
							tablename = array[i].split("==")[1].substring(0,array[i].split("==")[1].lastIndexOf("_"));
							result_table = tablename;
						}
						else 
							ids += array[i].split("==")[0] + ",";
					}
				}
				$.ajax({
					  type: 'POST',
					  url: "getRelateListByTablenameAndIds.action",
					  data: {ids:ids,tablename:tablename},
					  async:false,//同步
					  success: function(data){
					  		data = eval("("+data+")").check_json_result;//obj数组
					  		iii = data.length;
					  }
				});
			}
		}
		
		//构建参数
		var parameters = "";
		var ooo = new Array();
		var ppp = new Array();
		var result_table = "";//结果表
		//构建添加到过程表参数
		function addToProduce(){
			//获取参数入口
			//var returnvalue = "1==TB_MM_204_891__-__2==TB_MM_204_891";
			relate_thing_id = getRelate_thing_id();
			var returnvalue ;
			if(relate_thing_id != undefined && relate_thing_id != ""){
				returnvalue = document.getElementById("relate_"+relate_thing_id).value;//如何获取983？？
				var array;
				var ids = "";
				var tablename = "";
				
				array = returnvalue.split("__-__");
				for(var i=0;i<array.length;i++){
					if(array[i].indexOf("==")>=0){
						if(i == array.length - 1){
							ids  += array[i].split("==")[0];
							tablename = array[i].split("==")[1].substring(0,array[i].split("==")[1].lastIndexOf("_"));
							result_table = tablename;
						}
						else 
							ids += array[i].split("==")[0] + ",";
					}
				}
				//获取过程表中与结果表中对应的字段及值
				$.ajax({
					  type: 'POST',
					  url: "getRelateListByTablenameAndIds.action",
					  data: {ids:ids,tablename:tablename},
					  async:false,//同步
					  success: function(data){
					  		data = eval("("+data+")").check_json_result;//obj数组
					  		//iii = data.length;
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
					  			var param = "";
					  			var result_ooo_String = "";
					  			//将204改为231====将结果表字段该为过程表字段，通过resulttable_producetable
					  			for(var h=0;h<ooo.length;h++){
					  				if(h == ooo.length - 1){
					  					param += ooo[h] ;
					  					$.ajax({
											  type: 'POST',
											  url: "getProduceColumnByResultColumn.action",
											  data: {tablename:tablename,param:param },
											  async:false,//同步
											  success: function(data){
											  		result_ooo_String = eval("("+data+")").check_json_result;
											  }
										});
					  				}else {
					  					param += ooo[h] + ",";
					  				}
					  			}
					  			if(result_ooo_String != ""){
					  				
					  				var result_ooo_array = result_ooo_String.split(",");
					  				ooo = result_ooo_array;
					  				
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
							});
							//alert(ppp);
							//判断过程表中是否有表间求和
							var biaojianqiuhe_returnValue = "";
							var return_array = new Array();
							$.ajax({
								  type: 'POST',
								  url: "getProduceTableByResultTable.action",
								  data: {tablename:tablename},
								  async:false,//同步
								  success: function(data){
								  		tablename =  eval("("+data+")").check_json_result;//过程表
								  		$.ajax({
											  type: 'POST',
											  url: "getBiaojianqiuheResultTableAndColumn.action",
											  data: {tablename:tablename},
											  async:false,//同步
											  success: function(data){
											  	return_array = eval("("+data+")").check_json_result.split("_--_");
											  }
										});
								  }
							});
							
							tablename = result_table + "," + tablename;
							for(var x=0;x<iii;x++){
								if(x == iii - 1){
									for(var i=0;i<ooo.length;i++){
										if(document.getElementById(tablename.split(",")[1]+"=="+ooo[i]+"=="+x).innerHTML != ""){
											if(i == ooo.length - 1){
												parameters += ooo[i] + "==" + document.getElementById(tablename.split(",")[1]+"=="+ooo[i]+"=="+x).innerHTML;
											}else {
												parameters += ooo[i] + "==" + document.getElementById(tablename.split(",")[1]+"=="+ooo[i]+"=="+x).innerHTML + ",";
											}
										}else {
											if(i == ooo.length - 1){
												parameters += ooo[i] ;
											}else {
												parameters += ooo[i]  + ",";
											}
										}
										
									}
									parameters += ",";
									for(var j=0;j<ppp.length;j++){
										if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value != ""){
											if(j == ppp.length - 1){
												parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value;
											}else {
												parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value + ",";
											}
										}else {
											if(j == ppp.length - 1){
											parameters += ppp[j].split("_-_")[0] ;
										}else {
											parameters += ppp[j].split("_-_")[0]  + ",";
										}
										}
									}
									//添加模块id,即tablename采购
									parameters += ",";
									parameters += "MODULEID==" + "<s:property value='tablename'/>";
								}else {
									for(var i=0;i<ooo.length;i++){
										if(i == ooo.length - 1){
											if(document.getElementById(tablename.split(",")[1]+"=="+ooo[i]+"=="+x).innerHTML != ""){
												parameters += ooo[i] + "==" + document.getElementById(tablename.split(",")[1]+"=="+ooo[i]+"=="+x).innerHTML;
											}else {
												parameters += ooo[i];
											}
										}else {
											if(document.getElementById(tablename.split(",")[1]+"=="+ooo[i]+"=="+x).innerHTML != ""){
												parameters += ooo[i] + "==" + document.getElementById(tablename.split(",")[1]+"=="+ooo[i]+"=="+x).innerHTML + ",";
											}else {
												parameters += ooo[i]  + ",";
											}
										}
									}
									parameters += ",";
									for(var j=0;j<ppp.length;j++){
										if(j == ppp.length - 1){
											if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value != ""){
												parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value;
											}else {
												parameters += ppp[j].split("_-_")[0];
											}
										}else {
											if(document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value != ""){
												parameters += ppp[j].split("_-_")[0] + "==" + document.getElementById(ppp[j].split("_-_")[2]+"=="+ppp[j].split("_-_")[0]+"=="+x).value + ",";
											}else {
												parameters += ppp[j].split("_-_")[0] + ",";
											}
										}
									}
									//添加模块id,即tablename采购
									parameters += ",";
									parameters += "MODULEID==" + "<s:property value='tablename'/>" + "===";
								}
							}
							document.getElementById("tablename").value = tablename + "," + "<s:property value='tablename'/>";
							document.getElementById("parameters").value = parameters;
					  }
				});
			}
		}
		
		var ooo_ = new Array();
		var ppp_ = new Array();
		var iii_ = 0;
		function addRelate(returnvalue){
			if(returnvalue == ""){return;}
			var array;
			var ids = "";
			var tablename = "";
			
			array = returnvalue.split("__-__");
			for(var i=0;i<array.length;i++){
				if(array[i].indexOf("==")>=0){
					if(i == array.length - 1){
						ids  += array[i].split("==")[0];
						tablename = array[i].split("==")[1].substring(0,array[i].split("==")[1].lastIndexOf("_"));
						result_table = tablename;
					}
					else 
						ids += array[i].split("==")[0] + ",";
				}
			}
			if(document.getElementById("insertAfter") != null || document.getElementById("column_name") != null){
				$("#insertAfter").remove();
				$("#column_name").remove();
			}
			$.ajax({
				  type: 'POST',
				  url: "getRelateListByTablenameAndIds.action",
				  data: {ids:ids,tablename:tablename,id:"<s:property value='id'/>"},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;//obj数组
				  		iii_ = data.length;
				  		var tr = "";
				  		var tr_head = "<tr id='column_name'>";
				  		$.each(data,function(i,obj){
				  			var gg = -1;
				  			var pro_array = new Array();
				  			for( mm in obj){
				  				gg++;
				  				pro_array[gg] = mm.toString();
				  			}
				  			ooo_ = pro_array;
				  			tr += "<tr>";
				  			for(var j = 0;j<pro_array.length;j++){
				  				if(obj[pro_array[j]] != "undefined"){
				  					if(pro_array[j] == 'id' || pro_array[j] == 'department' || pro_array[j] == 'name' || pro_array[j] == 'status' || pro_array[j] == 'username'){
				  						tr += "<td  ><span style='display:none;' id='"+tablename+"=="+pro_array[j]+"=="+parseInt(i+iii)+"' onclick='' >"+obj[pro_array[j]]+"</span></td>" ;
				  					}else {
				  						tr += "<td align='center' ><span id='"+tablename+"=="+pro_array[j]+"=="+parseInt(i+iii)+"' onclick='' >"+obj[pro_array[j]]+"</span></td>" ;
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
								  		ppp_ = produce_columns;
								  }
							});
				  			for(var jj = 0;jj<produce_columns.length;jj++){
				  				//alert(produce_columns[jj].split("_-_")[2] + "==" + produce_columns[jj].split("_-_")[0] + "==" + parseInt(i+iii));
			  					tr += "<td align='center' ><input onclick='jisuan(this);' id='"+produce_columns[jj].split("_-_")[2]+"=="+produce_columns[jj].split("_-_")[0]+"=="+parseInt(i+iii)+"' ></input></td>" ;
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
			//load_();//添加一条记录的时候重新获取iii的值
		}
		
		var parameters_ = "";
		
		//构建修改时候添加的参数
		function addToProduce_(){
			var tablename = result_table;//结果表
			//var id = document.getElementById(tablename+"==id").value;
			//获取过程表名
			$.ajax({
				  type: 'POST',
				  url: "getProduceTableByResultTable.action",
				  data: {tablename:tablename},
				  async:false,//同步
				  success: function(data){
				  		tablename += "," + eval("("+data+")").check_json_result;//结果表,过程表
				  }
			});
			for(var x=0;x<iii_;x++){
				if(x == iii_ - 1){
					for(var i=0;i<ooo_.length;i++){
						if(document.getElementById(tablename.split(",")[0]+"=="+ooo_[i]+"=="+parseInt(x+iii)).innerHTML != ""){
							if(i == ooo_.length - 1){
								parameters_ += ooo_[i] + "==" + document.getElementById(tablename.split(",")[0]+"=="+ooo_[i]+"=="+parseInt(x+iii)).innerHTML;
							}else {
								parameters_ += ooo_[i] + "==" + document.getElementById(tablename.split(",")[0]+"=="+ooo_[i]+"=="+parseInt(x+iii)).innerHTML + ",";
							}
						}else {
							if(i == ooo_.length - 1){
								parameters_ += ooo_[i] ;
							}else {
								parameters_ += ooo_[i]  + ",";
							}
						}
					}
					parameters_ += ",";
					for(var j=0;j<ppp_.length;j++){
						if(document.getElementById(ppp_[j].split("_-_")[2]+"=="+ppp_[j].split("_-_")[0]+"=="+parseInt(x+iii)).value != ""){
							if(j == ppp_.length - 1){
								parameters_ += ppp_[j].split("_-_")[0] + "==" + document.getElementById(ppp_[j].split("_-_")[2]+"=="+ppp_[j].split("_-_")[0]+"=="+parseInt(x+iii)).value;
							}else {
								parameters_ += ppp_[j].split("_-_")[0] + "==" + document.getElementById(ppp_[j].split("_-_")[2]+"=="+ppp_[j].split("_-_")[0]+"=="+parseInt(x+iii)).value + ",";
							}
						}else {
							if(j == ppp_.length - 1){
								parameters_ += ppp_[j].split("_-_")[0] ;
							}else {
								parameters_ += ppp_[j].split("_-_")[0]  + ",";
							}
						}
					}
					//添加模块id,即tablename采购
					parameters_ += ",";
					parameters_ += "MODULEID==" + "<s:property value='tablename'/>";
				}else {
					for(var i=0;i<ooo_.length;i++){
						if(i == ooo_.length - 1){
							if(document.getElementById(tablename.split(",")[0]+"=="+ooo_[i]+"=="+parseInt(x+iii)).innerHTML != ""){
								parameters_ += ooo_[i] + "==" + document.getElementById(tablename.split(",")[0]+"=="+ooo_[i]+"=="+parseInt(x+iii)).innerHTML;
							}else {
								parameters_ += ooo_[i];
							}
						}else {
							if(document.getElementById(tablename.split(",")[0]+"=="+ooo_[i]+"=="+parseInt(x+iii)).innerHTML != ""){
								parameters_ += ooo_[i] + "==" + document.getElementById(tablename.split(",")[0]+"=="+ooo_[i]+"=="+parseInt(x+iii)).innerHTML + ",";
							}else {
								parameters_ += ooo_[i] +  ",";
							}
						}
					}
					parameters_ += ",";
					for(var j=0;j<ppp_.length;j++){
						if(j == ppp_.length - 1){
							if(document.getElementById(ppp_[j].split("_-_")[2]+"=="+ppp_[j].split("_-_")[0]+"=="+parseInt(x+iii)).value != ""){
								parameters_ += ppp_[j].split("_-_")[0] + "==" + document.getElementById(ppp_[j].split("_-_")[2]+"=="+ppp_[j].split("_-_")[0]+"=="+parseInt(x+iii)).value;
							}else {
								parameters_ += ppp_[j].split("_-_")[0] ;
							}
						}else {
							if(document.getElementById(ppp_[j].split("_-_")[2]+"=="+ppp_[j].split("_-_")[0]+"=="+parseInt(x+iii)).value != ""){
								parameters_ += ppp_[j].split("_-_")[0] + "==" + document.getElementById(ppp_[j].split("_-_")[2]+"=="+ppp_[j].split("_-_")[0]+"=="+parseInt(x+iii)).value + ",";
							}else {
								parameters_ += ppp_[j].split("_-_")[0] + ",";
							}
						}
					}
					//添加模块id,即tablename采购
					parameters_ += ",";
					parameters_ +=  "MODULEID==" + "<s:property value='tablename'/>" + "===";
				}
			}
			document.getElementById("parameters_").value = parameters_ ;
		}
		
		function getColumnByRelateColumnAndTable(tablename,columnName){
			var returnValue = "";
			$.ajax({
				  type: 'POST',
				  url: "getColumnByRelateColumnAndTable.action",
				  data: {tablename:tablename,columnName:columnName},
				  async:false,//同步
				  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		returnValue = data;
				  }
			});
			return returnValue;
		}
		
		//修改删除相关数据的时候
		//初始化加载业务进度
		function load_yewu_jindu(){
			var columnName = '${columnName}';
			var tablename = '${tablename}';
			if(columnName != undefined && columnName != ""){
				//根据相关字段获取与之关联的业务进度column
				columnName = getColumnByRelateColumnAndTable(tablename,columnName);
				//var id = "value_jindutiao__" + columnName;
				var columnName_ = columnName;
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
					  		returnValue = data;//3个计算字段+1个表内相关字段的id
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
				/**
				for(var j=0;j<relate_tablename.split("_").length - 1;i++){
					if(j == relate_tablename.split("_").length - 2)
						relate_tablename += relate_tablename.split("_")[i];
					else 
						relate_tablename += relate_tablename.split("_")[i] +"_"; 
				}
				*/
				var relateIds = "(";
				for(var i = 0;i<relate_array.length;i++){
					if(i == relate_array.length - 1)
						relateIds += relate_array[i].split("==")[0];
					else 
						relateIds += relate_array[i].split("==")[0] + ",";
				}
				
				relateIds += ")";
				
				if(relateIds == "()"){
					relateIds = "";
				}
				
				
				//如何获取页面上的时间字段和值
				var time_begin_id = returnArray[4];
				var time_end_id = returnArray[5];
				
				var time_begin_column = getColumnNameById_ajax(time_begin_id);
				var time_end_column = getColumnNameById_ajax(time_end_id);
				
				
				var time1 = $("#"+time_begin_column).val() ;
				var time2 = $("#"+time_end_column).val() ;
				
				if(time1.indexOf(":")<0 ){
					time1 = time1 + " 00:00:00";
				}
				if(time2.indexOf(":")<0 ){
					time2 = time2 + " 00:00:00";
				}
				
				var time1_value = getDateByTime(time1);
				var time2_value = getDateByTime(time2);
				
				if(time1 == "" || time2 == ""){
					alert("请选择时间！！！");
					return ;
				}else if(time1_value > time2_value){
					alert("开始时间必须小于结束时间，请重新填写！！！");
					return ;
				}
				$.ajax({
					  type: 'POST',
					  url: "getRelateInfoByIds.action",
					  data: {columnName:columnName_,relateIds:relateIds,tablename:relate_tablename,time1:time1,time2:time2},
					  async:false,//同步
					  success: function(data){
					  		data = eval("("+data+")").check_json_result;
					  		$("#jindutiao_div__"+columnName_).remove();
					  		$("#value_jindutiao__"+columnName_).val(data);
					  		$("<div id='jindutiao_div__"+columnName_+"' style='border: 1px dotted #FF6633;width:300px'><img height='14' src='images/jd.gif' width='"+data+"%'  id='show_jindutiao__"+columnName_+"'/></div>").insertAfter($("#span_red__"+columnName_));
					  }
				});
			}
		}
		</script>
		<style type="text/css"> 
		td {font-size:12px;color:#333333;line-height:150%}
		tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")} 
		</style>
	</HEAD>
	<body onLoad="myload();load_();load_yewu_jindu();">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:NavigationForViewAndUpdate/></div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<s:form action="updateContactTags.action" method="post" theme="simple" onsubmit="return doSubmit();">
			<s:hidden name="id"   />
			<input type="hidden" name="tablename" value="<s:property value="tablename"/>" id="tablename"/>
			<input type="hidden" name="parameters" id="parameters"/>
	 		<input type="hidden" name="parameters_" id="parameters_"/>
			<input type="hidden" name="rx" value="<s:property value="rx"/>"/>
			
			<input type="hidden" name="userSign.moduleid" id="userSign.moduleid" value="<s:property value='moduleManage.id' />" />
			
			<s:if test="currentUser !=null && currentUser.user_add == 1">
			<table width='100%' cellpadding='1' align='center' cellspacing='1'>
			<caption>用户信息与部门</caption>
			<jsp:include page="userinfo.jsp" flush="true" />
			</table>
			</s:if>
			
			<table width='100%' cellpadding='1' align='center' cellspacing='1' id='before'>
			<tbody onMouseOut="changeback()" onMouseOver="changeto()">
			<wysLib:update />
			</tbody>
			</table>
			
				
			<br>
			
			<div style="margin-top: 0px; text-align: center;">
				<input class="textbg6" type="submit" value="确认修改" >
			</div>
		</s:form>
		
		<div style="margin-top: 0px; text-align: center;">
			<wysLib:showlistRelateForUpdate />
		</div>	
	
	</body>
</HTML>
