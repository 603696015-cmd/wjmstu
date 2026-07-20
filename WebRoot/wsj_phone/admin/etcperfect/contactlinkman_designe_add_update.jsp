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
	   if(obj.options[0].selected || obj.options[1].selected || obj.options[4].selected )  
	  {
	  	document.getElementById("default_value").style.visibility="";
	  	}
	  else  
	  {
	  	document.getElementById("default_value").style.visibility="hidden";
	  	document.getElementById("wenbenwidth").value="";
	  	document.getElementById("wenbendefaultvalue").value="";
	  	}
	  	if(obj.options[15].selected )  
	  {
	  	document.getElementById("default_value").style.visibility="hidden";
	  	}
	  else  
	  {
	  	
	  	}
	  	
	  	if(obj.options[16].selected )  
	  {
	  	document.getElementById("selectlevel_div").style.display="block";
	  	}
	  else  
	  {
	  	document.getElementById("selectlevel_div").style.display="none";
	  	}
	  
	   if(obj.options[2].selected)  
	  {
	  	document.getElementById("span_date").style.visibility="";
	  	}
	  else  
	  {
	  	document.getElementById("span_date").style.visibility="hidden";
	  		document.getElementById("nowdate").checked=false;
	  	}
	  
	  if(obj.options[5].selected )  
	  {
	  	document.getElementById("selectvalue").style.visibility="";
	  	}
	  else  
	  {
	  	document.getElementById("selectvalue").style.visibility="hidden";
	  	document.getElementById("select_default_value").value="";
	  }

	if(obj.options[7].selected||obj.options[8].selected)//7图片  8 富文本 
	{
		document.getElementById("divpic").style.visibility="hidden";
	}
	else
	{
		document.getElementById("divpic").style.visibility="";
	}
//alert(document.getElementById("check").value);
	if(obj.options[8].selected&&!document.getElementById("check").value=="")
	{
		alert("无法定义多个富文本!!!");
		obj.options[0].selected=true;
		
		document.getElementById("required").style.visibility="hidden";
	}
	else
	{
		document.getElementById("required").style.visibility="";
	}
	
	
	 if(obj.options[9].selected)  
	  {
	  	document.getElementById("relatevalue").style.visibility="";
	  	}
	  else  
	  	document.getElementById("relatevalue").style.visibility="hidden";
	  
	 // if(document.getElementById("wenbenwidth").value)	
	 
	  
	  
  }
  
  
         
         function doSubmit()
         {
         	if($("#name_display").val()=="")
         	{
         		alert("自定义项目名称不能为空！");
         		return false;
         	}
         	if(document.getElementById("selectshow").options[5].selected)
         	{ 
         		//if(document.getElementById("select_default_value").val=="")
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
         	
         	var num=document.getElementById("wenbenwidth").value;
         	if(num!="")
         	{
         		 var reg=/^(([1-9]\d*))$/;
         		 if(!reg.test(num))
         		 {
         		 	alert("只能为正整数！！！");
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
         	
         	return true;
         }
         
         	 function addModule()
			 {	
			 	width=600;	
			 	height=400;	
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog("getModule.action?rn="+Math.random(),null,sFeature);	
			//	var str=String(rv);
			//	alert(String(rv));
				var str=String(rv).split("==");
				if(str!=null&&str.length>1)
				{
					document.getElementById("relate_modulename").value=str[0];
					document.getElementById("relate_tablename").value=str[1];
				}
			 } 
			 
			 function addColumn()
			 {	
				if(document.getElementById("relate_modulename").value=="")
				{
				  	alert("请选择模块！！！");
				   	return false;
				}
			 	var tablename=document.getElementById("relate_tablename").value;
			 	width=600;	
			 	height=400;	
			  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
			  	var rv = window.showModalDialog("getModuleColumn.action?tablename="+tablename+"&rn="+Math.random(),null,sFeature);	
				
				//alert(rv);
				var str=String(rv).split("==");
				if(str!=null&&str.length>1)
				{
					document.getElementById("relate_columnname").value=str[2];
					//document.getElementById("relate_tablename").value=str[1];
					document.getElementById("relate_defaultvalue").value=rv;
				}
			 }
			 
			 function onload()
			 {
			// alert("ff");
			
			var ex_fromtablename_columnname = "<s:property value='tags.fromtablename_columnname'/>";
			if(ex_fromtablename_columnname != ""){
				var tablename = ex_fromtablename_columnname.substring(0,ex_fromtablename_columnname.lastIndexOf("_"));
				//alert(tablename);
				//document.getElementById("ex_fromtablename_columnname").innerHTML = ex_fromtablename_columnname;
				document.getElementById("ex_fromtablename_columnname").innerHTML = getColumnByColumnName(tablename,ex_fromtablename_columnname);
				document.getElementById("tags.fromtablename_columnname").value = ex_fromtablename_columnname;
			}
			
			
			 var value="";
			 var valueobj =document.getElementById("d_value");//alert(value);
			 if(valueobj!=null)
			 	value=valueobj.value;
			  var obj=document.getElementById("selectshow");
			      if(obj.options[16].selected){
			      	document.getElementById("selectlevel_div").style.display = "block";
				 	
				 	document.getElementById("writible").style.display = "block";
					
					var writible = "<s:property value='tags.writible'/>";
					var radios = document.getElementsByName("tags.writible");
					for(var i=0;i<radios.length;i++){
						if(parseInt(radios[i].value) == parseInt(writible)){
							radios[i].checked = true;
						}
					}
			      	
			      	document.getElementById("sele.name").innerHTML = "<s:property value='tags.selectLevel.name' />";
			      	document.getElementById("sele.id").value = "<s:property value='tags.selectlevelid' />";
			      	var selectLevelid = parseInt(document.getElementById("sele.id").value);
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
			 	  if(obj.options[10].selected)//如果选择的是百分比
				  {
				  	//获取添加业务进度时候的设置模块和字段
				  	var tagsid = document.getElementById("tags.id").value;
				  	var returnValue = "";
					$.ajax({
						  type: 'POST',
						  url: "IfHasYewuJindu_column.action",
						  data: {id:tagsid},
						  async:false,//同步
						  success: function(data){
					  		data = eval("("+data+")").check_json_result;
					  		if(data != "")
					  			returnValue = data;
						  }
					});
					
					var names_array;
					
					if(returnValue != "" && returnValue.split(",").length>0){
						document.getElementById("select_time_begin_end_yewu").style.visibility = "";
						names_array = returnValue.split(",");
						//赋值
						document.getElementById("select_module_name").innerHTML = "模块名称为："+names_array[6];
						document.getElementById("module_columns").innerHTML = "模块中字段名称为："+names_array[0] + "," + names_array[1] + "," + names_array[2] ;
						document.getElementById("yewu_jindu_relate_id_column").innerHTML = "相关字段为："+names_array[3];
						document.getElementById("yewu_jindu_relate_begintime_column").innerHTML = "开始时间字段为："+names_array[4];
						document.getElementById("yewu_jindu_relate_endtime_column").innerHTML = "结束时间字段为："+names_array[5];
					}
				  	
				  }
			 	  if(obj.options[0].selected)  
				  {
				  	document.getElementById("default_value").style.visibility="";
					document.getElementById("writible").style.display = "block";
					
					var writible = "<s:property value='tags.writible'/>";
					var radios = document.getElementsByName("tags.writible");
					for(var i=0;i<radios.length;i++){
						if(parseInt(radios[i].value) == parseInt(writible)){
							radios[i].checked = true;
						}
					}
					
					if(value!="")
					{
						var values=value.split("==");
						document.getElementById("wenbendefaultvalue").value=values[0];
						if(values.length>1)
						{
							document.getElementById("wenbenwidth").value=values[1];
						}
					}
					else
					{
					  	document.getElementById("wenbenwidth").value="";
					  	document.getElementById("wenbendefaultvalue").value="";
					}

				  }
				  
				  
				  
				  if(obj.options[1].selected||obj.options[4].selected)  
				  {
				  	document.getElementById("span_number").style.visibility="";
					document.getElementById("writible").style.display = "block";
					document.getElementById("number").style.display = "block";
					document.getElementById("default_value").style.visibility="";
					
					
					
					var writible = "<s:property value='tags.writible'/>";
					var biaojianqiuhe_check = "<s:property value='tags.biaojianqiuhe_check'/>";
					var biaojianqiuhe_tablename = "<s:property value='tags.biaojianqiuhe_tablename'/>";
					var biaojianqiuhe_column = "<s:property value='tags.biaojianqiuhe_column'/>";
					var jisuan_result_relate_id = "<s:property value='tags.jisuan_result_relate_id'/>";
					var jisuan_result_table_name = "<s:property value='tags.jisuan_result_table_name'/>";
					var jisuan_relate_type = "<s:property value='tags.jisuan_relate_type'/>";
					
					//表内计算
					var columnName = "<s:property value='tags.column_name'/>";//列名
					var tablename = "<s:property value='tags.table_name'/>";//表名
					var returnValue = "";
					var returnArray = new Array();
					$.ajax({
						  type: 'POST',
						  url: "getQiujiAndHeInfo.action",
						  data: {tablename:tablename,columnName:columnName},
						  async:false,//同步
						  success: function(data){
					  		data = eval("("+data+")").check_json_result;
					  		if(data != "")
					  			returnValue = data;
						  }
					});
					//alert(returnValue);
					if(returnValue != ""){
						returnArray = returnValue.split("==");
						var relate_columns = "";
						for(var i=0;i<returnArray.length;i++){
							if(parseInt(returnArray[0]) == 1){//求积字段
								document.getElementById("is_qiuji").innerHTML = columnName + "字段为求积字段";
								break;
							}else if(parseInt(returnArray[1]) == 1){//求和字段
								document.getElementById("is_qiuhe").innerHTML = columnName + "字段为求和字段";
								break;
							}else if(parseInt(returnArray[2]) == 1){//作为积字段
								document.getElementById("is_zuoweiji_div").style.display = "block";
								document.getElementById("is_zuoweiji").innerHTML = columnName + "字段为积字段";
								if(returnArray[4] != 'undefined'){
									for(var j=0;j<returnArray[4].split(",").length;j++){
										if(j == returnArray[4].split(",").length - 1)
											relate_columns += getColumnByColumnName(tablename,returnArray[4].split(",")[j]);
										else 
											relate_columns += getColumnByColumnName(tablename,returnArray[4].split(",")[j]) + ","; 
									}
								}
								document.getElementById("qiuji_column_name").innerHTML = "您重新选择的相乘字段为："+relate_columns;
								break;
							}else if(parseInt(returnArray[3]) == 1){//作为和字段
								document.getElementById("is_zuoweihe_div").style.display = "block";
								document.getElementById("is_zuoweihe").innerHTML = columnName + "字段为和字段";
								if(returnArray[4] != 'undefined'){
									for(var j=0;j<returnArray[5].split(",").length;j++){
										if(j == returnArray[5].split(",").length - 1)
											relate_columns += getColumnByColumnName(tablename,returnArray[5].split(",")[j]);
										else 
											relate_columns += getColumnByColumnName(tablename,returnArray[5].split(",")[j]) + ","; 
									}
								}
								document.getElementById("qiuhe_column_name").innerHTML = "您重新选择的求和字段为："+relate_columns;
								break;
							}
						}
					}
					
					if(parseInt(biaojianqiuhe_check) == 1){
						document.getElementById("biaojianqiuhe_modulename").innerHTML = "您选择的表间求和模块名为：" + getModuleNameByTablename(biaojianqiuhe_tablename);
						document.getElementById("biaojianqiuhe_tablename").innerHTML = "您选择的表间求和表名为：" + biaojianqiuhe_tablename;
						document.getElementById("biaojianqiuhe_columnname").innerHTML = "您选择的表间求和列名为：" + getNameDisplayById(parseInt(biaojianqiuhe_column));
					}
					
					var radios = document.getElementsByName("tags.writible");
					for(var i=0;i<radios.length;i++){
						if(parseInt(radios[i].value) == parseInt(writible)){
							radios[i].checked = true;
						}
					}
					
					if(jisuan_result_table_name != "" && 
					jisuan_result_relate_id != "" && jisuan_relate_type != ""){
						document.getElementById("biaojianjisuan_resulttable").innerHTML = "您选择的结果表为：" + jisuan_result_table_name;
						document.getElementById("biaojianjisuan_resultcolumn").innerHTML = "您选择的结果表关联字段为：" + getNameDisplayById(parseInt(jisuan_result_relate_id));
						document.getElementById("biaojianjisuan_type").innerHTML  = "计算方式为：" + jisuan_relate_type.split("==")[1];
					}
					
					var sum = "<s:property value='tags.sum_display'/>";
					if(sum == 1){
						document.getElementById("sum").checked = true;
					}else {
						document.getElementById("sum").checked = false;
					}
					
					if(value!="")
					{
						var values=value.split("==");
						document.getElementById("wenbendefaultvalue").value=values[0];
						if(values.length>1)
						{
							document.getElementById("wenbenwidth").value=values[1];
						}
					}
					else
					{
					  	document.getElementById("wenbenwidth").value="";
					  	document.getElementById("wenbendefaultvalue").value="";
					}
					
					/**
					if(value!="")
				  			document.getElementById("sum").checked=true;
				  	*/
				  }
				  
				  
				  if(obj.options[2].selected)  
				  {
				  	var timeformat = '${tags.timeformat}';
				  	document.getElementById("tags.timeformat").value = timeformat;
				  	document.getElementById("writible").style.display = "block";
				  	var writible = "<s:property value='tags.writible'/>";
				  	var radios = document.getElementsByName("tags.writible");
					for(var i=0;i<radios.length;i++){
						if(parseInt(radios[i].value) == parseInt(writible)){
							radios[i].checked = true;
						}
					}
					
				  	document.getElementById("span_date").style.visibility="";
				  	var value = "<s:property value='tags.default_value'/>";
				  	if(value != ""){
				  		document.getElementById("nowdate").checked = true;
				  		document.getElementById("span_date_number").style.display = "block";
				  		
				  		var date_number = document.getElementById("span_date_number");
				  		if(value.indexOf("_")<0){
				  			document.getElementById("date_number").value = 0;
				  		}else {
				  			document.getElementById("date_number").value = value.split("_")[1];
				  		}
				  	}
				  	else 
				  		document.getElementById("nowdate").checked = false;

						//if(value!="")
				  	//		document.getElementById("nowdate").checked=true;
				  }
				  
				  if(obj.options[5].selected || obj.options[11].selected || obj.options[12].selected)  
				  {
				  	document.getElementById("selectvalue").style.visibility="";
				  	document.getElementById("select_default_value").value=value;
				  	document.getElementById("writible").style.display = "block";
				  	var writible = "<s:property value='tags.writible'/>";
				  	var radios = document.getElementsByName("tags.writible");
					for(var i=0;i<radios.length;i++){
						if(parseInt(radios[i].value) == parseInt(writible)){
							radios[i].checked = true;
						}
					}
				  }
				  
				  if(obj.options[7].selected)//7图片  8 富文本 
				  {
					document.getElementById("divpic").style.visibility="hidden";
				  }
				  
				  if(obj.options[8].selected)
				  {
						
						document.getElementById("required").style.visibility="hidden";
						document.getElementById("divpic").style.visibility="hidden";
					//	document.getElementById("required").style.visibility="";
				  }
				  
				  if(obj.options[9].selected){//相关字段
				  		document.getElementById("relate_type").style.display = "block";
						document.getElementById("writible").style.display = "block";
						var writible = "<s:property value='tags.writible'/>";
						var is_judge = "<s:property value='tags.is_judge'/>";
						var showfinalpass = "<s:property value='tags.showfinalpass'/>";
						var default_value = "<s:property value='tags.default_value'/>";
						var relateIsShowComplete = "<s:property value='tags.relateIsShowComplete'/>";
						
						var wanzheng = "<s:property value='tags.wanzheng'/>";
						document.getElementById("tags.wanzheng").value = wanzheng;
						var radios = document.getElementsByName("tags.writible");
						
						for(var i=0;i<radios.length;i++){
							if(parseInt(radios[i].value) == parseInt(writible)){
								radios[i].checked = true;
							}
						}
						
						if(parseInt(is_judge) == 1){
							document.getElementById("tags.is_judge").checked = true;
						}
						
						if(parseInt(showfinalpass) == 1){
							document.getElementById("tags.showfinalpass").checked = true;
						}
						if(default_value != ""){
							var array = default_value.split("==");
							var tablename = array[0];
							var column_chinese = array[2];
							document.getElementById("relate_module").innerHTML = "您选择的关联模块为：" + getModuleNameByTablename(tablename);
							document.getElementById("relate_columnname").innerHTML = "您选择的关联列为：" + column_chinese;
						}
						
						if(parseInt(relateIsShowComplete) == 1){
							if(wanzheng != ""){
								var array = wanzheng.split(",");
								document.getElementById("showCompleteColumns").innerHTML += "完整显示的字段为：";
								for(var i=0;i<array.length;i++){
									if(i == array.length - 1){
										document.getElementById("showCompleteColumns").innerHTML += getNameDisplayById(parseInt(array[i])) ;
									}else {
										document.getElementById("showCompleteColumns").innerHTML += getNameDisplayById(parseInt(array[i])) + ",";
									}
								}
							}
							document.getElementById("relateIsShowComplete").style.display = "block";
							document.getElementById("tags.relateIsShowComplete").checked = true;
						}else {
							document.getElementById("relateIsShowComplete").style.display = "block";
							document.getElementById("tags.relateIsShowComplete").checked = false;
						}
						
						//alert(checkTable(tablename));
						if(document.getElementById("tags.relateIsShowComplete").checked && wanzheng != ""){
							//if(checkTable(tablename) == "2"){//过程表
								document.getElementById("re_completeColumns").style.display = "block";
							//}
						}
				  }
				  if(obj.options[13].selected){
				  	document.getElementById("writible").style.display = "block";
				  	document.getElementById("is_judge_for_user").style.display = "block";
				  	var writible = "<s:property value='tags.writible'/>";
					var is_judge_for_user = "<s:property value='tags.is_judge_for_user'/>";
					var radios = document.getElementsByName("tags.writible");
					for(var i=0;i<radios.length;i++){
						if(parseInt(radios[i].value) == parseInt(writible)){
							radios[i].checked = true;
						}
					}
					if(parseInt(is_judge_for_user) == 1){
						document.getElementById("tags.is_judge_for_user").checked = true;
					}
				  }
				  
				  //城市类型的字段
				 if(obj.options[14].selected){
				 	document.getElementById("writible").style.display = "block";
				 	document.getElementById("default_value").style.visibility="hidden";
				 	document.getElementById("shengshixian").style.display = "block";
				 	
				 	var writible = "<s:property value='tags.writible'/>";
				 	var radios = document.getElementsByName("tags.writible");
					for(var i=0;i<radios.length;i++){
						if(parseInt(radios[i].value) == parseInt(writible)){
							radios[i].checked = true;
						}
					}
				 	
				 	//省市县初始化赋值到相应的select
					var province_in_back ;
					var city_in_back ;
					var county_in_back  ;
					
					
					var shengshixian_array;
					var value_shengshixian = document.getElementById("d_value").value;
					if(value_shengshixian != ""){
						//将==转化为空格
						shengshixian_array = value_shengshixian.split("==");
						province_in_back = shengshixian_array[0];
						city_in_back = shengshixian_array[1];
						county_in_back = shengshixian_array[2];
						$("#option_in_province").text(province_in_back) ;
						$("#option_in_province").attr("value",province_in_back);
						
						
						$("#option_in_city").text(city_in_back) ;
						$("#option_in_city").attr("value",city_in_back);
						
						$("#option_in_county").text(county_in_back) ;
						$("#option_in_county").attr("value",county_in_back);
						
						document.getElementById("wenbendefaultvalue").value = shengshixian_array[0] + " " + shengshixian_array[1] + " " + shengshixian_array[2];
					}
				 }
				 
				  
			 }
			 
			 function checkTable(tablename){
			 	var returnValue = "";
				$.ajax({
					  type: 'POST',
					  url: "checkTable.action",
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
			 
			 function getNameDisplayById(id){
				var returnValue = "";
				$.ajax({
					  type: 'POST',
					  url: "getNameDisplayById.action",
					  data: {id:id},
					  async:false,//同步
					  success: function(data){
					  		data = eval("("+data+")").check_json_result;
					  		returnValue = data;
					  }
				});
				return returnValue;
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
			 
			 function Ifshowtime(this_){
			 	if(this_.checked) {
			 		document.getElementById('span_date_number').style.display = 'block';
			 	} else {
			 		document.getElementById('span_date_number').style.display = 'none';
			 		document.getElementById("date_number").value = 0;
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
			
			function selectRelateColumn(number){
			 	var tablename = "<s:property value="tablename"/>";
			 	var value = "";
			 	var array=new Array();
			 	if(number == 1){
			 		width=600;
					height=500;
					var url = "selectQiujiRelateColumn_zidingyi.action?x="+Math.random()+"&tablename="+tablename;
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("tags.qiuji_column_name").value=rv;
						array = rv.split(",");
						for(var i=0;i<array.length;i++){
							if(i == array.length - 1)
								value += getColumnByColumnName(tablename,array[i]);
							else 
								value += getColumnByColumnName(tablename,array[i]) + ","
						}
						document.getElementById("re_qiuji_column_name").innerHTML = "您选择的求积字段为："+value;
					}
			 	}else if(number == 2){
			 		width=600;
					height=500;
					var url = "selectQiujiRelateColumn_zidingyi.action?x="+Math.random()+"&tablename="+tablename;
				  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						document.getElementById("tags.qiuhe_column_name").value=rv;
						array = rv.split(",");
						for(var i=0;i<array.length;i++){
							if(i == array.length - 1)
								value += getColumnByColumnName(tablename,array[i]);
							else 
								value += getColumnByColumnName(tablename,array[i]) + ","
						}
						document.getElementById("re_qiuhe_column_name").innerHTML = "您选择的求和字段为："+value;
					}
			 	}
				
			}
			
			function select_ziduan_wanzheng(){
				type="ziduan_wanzheng";
				//获取过程表
				tablename = "<s:property value='tablename'/>";
				$.ajax({
					  type: 'POST',
					  url: "getProduceTableByTable.action",
					  data: {tablename:tablename},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		tablename = data;
					  }
				});
				select_time_begin_end(type,tablename);
			}
			
			function select_time_begin_end(type,tablename){
				if(type == "ziduan_wanzheng"){
					width=600;
					height=500;
					var url = "";
					
					url = "select_time_begin_end.action?x="+Math.random()+"&tablename="+tablename+"&type="+type;
					var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";
					var rv =  window.showModalDialog(url,null,sFeature);
					if(rv!=undefined&&rv!=""){
						var array = rv.split("=");
						document.getElementById("tags.wanzheng").value=array[0];
						document.getElementById("wanzheng_columnName").innerHTML = "您重新设置的字段为："+array[1];
						
					}
				}
				
			}
			
			function show_beizhu(){
				if(document.getElementById("column_name").innerHTML==""){
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
				 	if(relates!=""){
				 		document.getElementById("tagsMark.relates").value = relates;
				 		document.getElementById("tagsMark.relates_info").value = relates_info;
				 	}
					document.getElementById("tagsMark.columnname").value = document.getElementById("column_name").innerHTML;
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
				 //删除层级下拉列表
				 if(document.getElementById("tags.jibieshu")!=null){
					document.getElementById("tags.jibieshu").parentNode.removeChild(document.getElementById("tags.jibieshu"));
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
  <body onLoad="onload();" >
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
		<s:form action="updateDesigneTags" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
				<input type="hidden" value=<s:property value="tags.default_value"/> id="d_value" />
				<input type="hidden" value=<s:property value="tags.table_name"/> name="tags.table_name" />
				<input type="hidden" value=<s:property value="tags.column_name"/> name="tags.column_name" />
		<div style="margin-top: 0px; text-align: center;">
				<table cellpadding="1" cellspacing="1" width="700">
					<tr>
						<td width="300px" height="30" align="center" >
							自定义项目名称：<span  style="color:red">*</span>
						</td>
						<td width="400px">
							<label>
								<s:textfield name="tags.name_display"  id="name_display" size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							自定义列名：<span  style="color:red">*</span>
						</td>
						<td >
							<label>
								<span style='color:red' id="column_name"><s:property value='tags.column_name'/></span>
							</label>
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
										<input type="hidden"  name="tagsMark.columnname" id="tagsMark.columnname" value="<s:property value='tags.column_name' />"/>
										<input type="hidden"  name="tagsMark.tablename" value="<s:property value='tablename'/>" />
										<input type="hidden"  name="tagsMark.relates" id="tagsMark.relates"/>
										<input type="hidden"  name="tagsMark.relates_info" id="tagsMark.relates_info"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
					<tr>
						<td width="160" height="30" align="center" >
							自定义项目类型：<span  style="color:red">*</span>
						</td>
						<td width="600">
								<s:select  name="tags.display_type" onchange="showselect(this);"  id="selectshow"  
								list="{'实数','日期','大文本','整数','下拉选项','附件上传','图片','富文本','相关字段','百分比','单选','复选','相关负责人','城市','当前用户信息','分级下拉选项'}" 
								theme="simple" headerKey="文本" headerValue="文本"  disabled="true" />
								<input type="hidden" name="display_type" value="<s:property value="tags.display_type"/>" />  
								<input type="hidden" name="tags.id" value="<s:property value="tags.id"/>" id="tags.id"/>  
								
							<span id="span_date" style="visibility:hidden">
								<input id="nowdate" type="checkbox" name="timecheck" value='nowdate' onClick="Ifshowtime(this)"/>是否显示当前时间&nbsp;&nbsp;<span id='span_date_number' style="color:red;display:none;">往后<input  type='text' id='date_number' name='date_number' width='50px' value='0' />天</span><br>
								日期格式<input id="tags.timeformat" name="tags.timeformat" style="width:250px;" /><br>
								<span style="color:red">
									格式如：<br>
									yyyy-MM-dd<br>
									yyyy/MM/dd<br>
									yyyy年MM月dd日<br>
								</span>
							</span>
							<span id="span_number" style="visibility:hidden">
								<input id="sum" type="checkbox" name="sumcheck" value="sum"  />是否显示求和
							</span>
							
							<span id="select_time_begin_end_yewu" style="visibility:hidden">
								<br>
								<span id="select_module_name" style="color:blue"></span>
								<br>
								<span id="module_columns" style="color:blue"></span>
								<br>
								<span  id="yewu_jindu_relate_id_column" style="color:blue"></span>
								<br>
								<span  id="yewu_jindu_relate_begintime_column" style="color:blue"></span>
								<br>
								<span  id="yewu_jindu_relate_endtime_column" style="color:blue"></span>
							</span>
							
							<div id='writible' style="display:none;">
									不限<input type='radio' name='tags.writible' value='1' />
									初审可写<input type='radio' name='tags.writible'  value='2' />
									终审可写<input type='radio' name='tags.writible'  value='3'/>
							</div>
							<div style='display:none;' id="selectlevel_div">
								<span style="color:red">选择起始节点</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="选择" onclick="select_qishijiedian();" /> 
								<span id="sele.name"></span>
								<input type="hidden" name="tags.selectlevelid" id="sele.id" value=0/>
								<br>
								<span style="color:red">可供选择的级别数</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="点击获取级别数" onclick="select_jiebieshu();" />
							</div>
							<br>
							<div id='relate_type' style="display:none;">
								<div id='is_judge' >
									<input type="checkbox" name="tags.is_judge" id="tags.is_judge" value='1'/>是否进行权限判断 
									<input type="checkbox" name="tags.showfinalpass" id="tags.showfinalpass" value='1'/>只显示终审通过的数据 
								</div>
								<div id='module_info' >
									<span style="color:red" id="relate_module"></span><br>
									<span style="color:red" id="relate_columnname"></span>
								</div>
								<div id='completeColumns' >
									<span style="color:red" id="showCompleteColumns"></span>
									<div id='relateIsShowComplete' style="display:none;">
										<input type="checkbox" name="tags.relateIsShowComplete"  value=1 onclick="if(this.checked) {document.getElementById('re_completeColumns').style.display='block';} else {document.getElementById('re_completeColumns').style.display='none';document.getElementById('tags.wanzheng').value='';}"/>是否完整显示
									</div>
									<div id='re_completeColumns' style="display:none;">
										<input type="button" value="选择" class="textbg4" onClick="select_ziduan_wanzheng();"/>
										<input type="hidden" name="tags.wanzheng" id="tags.wanzheng"/><br>
										<span style="color:blue" id="wanzheng_columnName"></span>
									</div>
								</div>
							</div>
							<div id='is_judge_for_user' style='display:none;'>
								<input type="checkbox" name="tags.is_judge_for_user" id="tags.is_judge_for_user" value='1'/>是否进行权限判断 
							</div>
							
							<div id='number' style="display:none;">
								<span id="biaojianqiuhe_modulename" style="color:red"></span><br>
								<span id="biaojianqiuhe_tablename" style="color:red"></span><br>
								<span id="biaojianqiuhe_columnname" style="color:red"></span>
								<br><br><br>
								<span id="biaojianjisuan_resulttable" style="color:red"></span><br>
								<span id="biaojianjisuan_resultcolumn" style="color:red"></span><br>
								<span id="biaojianjisuan_type" style="color:red"></span>
								<br>
								<span id='is_qiuji' style="color:blue"></span>
								<div >
									<span id='is_zuoweiji' style="color:blue"></span><br>
									<span id='qiuji_column_name' style="color:blue"></span><br>
									<div style='display:none;' id="is_zuoweiji_div">
										<input type="button" value="重新选择" class='textbg6' onclick='selectRelateColumn(1);'/>
										<input type='hidden' name='tags.qiuji_column_name' id='tags.qiuji_column_name'/>
										<span id='re_qiuji_column_name' style="color:red"></span>
									</div>
								</div>
								<span id='is_qiuhe' style="color:blue"></span><br>
								<div >
									<span id='is_zuoweihe' style="color:blue"></span><br>
									<span id='qiuhe_column_name' style="color:blue"></span>
									<div style='display:none;' id="is_zuoweihe_div">
										<input type="button" value="重新选择" class='textbg6' onclick='selectRelateColumn(2);'/><br>
										<input type='hidden' name='tags.qiuhe_column_name' id='tags.qiuhe_column_name'/>
										<span id='re_qiuhe_column_name' style="color:red"></span>
									</div>
								</div>
							</div>
							<div id="shengshixian" style="display:none;">
								省&nbsp;市&nbsp;县&nbsp;
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
							<div id="default_value" style="visibility:hidden">
								默认值(选填)<input type="text" id="wenbendefaultvalue" name="wbdefault" >
								文本框宽度(选填，百分比)<input id="wenbenwidth" name="wbwidth" type="text" style="width:40px;" >
							</div>
								<div id="selectvalue" style="visibility:hidden">
								<input type="text" style="width:400px;" name="tags.default_value"  id="select_default_value" ></input>
								<span  style="color:red">每个选项以"=="隔开</span>
								</div>
								
								<div id="relatevalue" style="visibility:hidden">
								模块<span  style="color:red">*</span>
								<input type="text" width="600"  onclick="addModule();" id="relate_modulename" readonly ></input>
								<input type="hidden" id="relate_tablename">
								字段<span  style="color:red">*</span>
								<input type="text" width="600"  onclick="addColumn();"  id="relate_columnname" readonly ></input>
								<input type="hidden" name="tags.default_value_2" id="relate_defaultvalue">
								</div>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							字段自动读取设置：<span  style="color:red">*</span>
						</td>
						<td >
							<span id="ex_fromtablename_columnname" style="color:blue"></span>
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
							<input type="checkbox" name="display" value ="add_display"  <s:if test="tags.add_display==1">checked</s:if> >添加页面是否显示
							<input type="checkbox" name="display" value ="update_display" <s:if test="tags.update_display==1">checked</s:if>  >修改页面是否显示
							<input type="checkbox" name="display" value ="view_display"  <s:if test="tags.view_display==1">checked</s:if> >查看页面是否显示
							<div id="divpic">
								<input type="checkbox" name="display" value ="list_display"  <s:if test="tags.list_display==1">checked</s:if>  >列表页面是否显示
								<input type="checkbox" name="display" value ="mutilsearch_display"  <s:if test="tags.mutilsearch_display==1">checked</s:if> >组合查询是否显示
								<input type="checkbox" name="display" value ="departsearch_display" <s:if test="tags.departsearch_display==1">checked</s:if>  >部门查询是否显示
							</div>
							<div id="required">
								<input type="checkbox" name="display" value ="required"  <s:if test="tags.required==1">checked</s:if> >是否必填<br>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2"  align="center" >
						<span  style="color:red">注意：新增加的字段只能隐藏不能删除，请慎重添加。</span>
						</td>
					</tr>
	                
				</table>
				
		</div>
		
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认修改" class=textbg6 />
		</div>
		</s:form>
  
	</body>
</html>