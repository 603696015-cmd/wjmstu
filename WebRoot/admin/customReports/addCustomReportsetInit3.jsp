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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE></TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link href="css/lable/Admin_Style.CSS" rel="stylesheet"
			type="text/css">
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js"></script>
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
		<script type="text/javascript">

/*function changedb(){
alert("ssssssssssssss");
 var dbname1=$('#dbname1').val();
 ar checkText=$("#dbname1").find("option:selected").text();   
			
			
			
		}*/
</script>
		<script type="text/javascript">
//查询所有自定义表
$(function(){
	var rcount=0;
	var ccount=0;
	
	$.ajax({
		  type: 'POST',
		  url: "list_modulemanage.action",
		  async:true,
		  success:function(data){
		  	var listObj = eval("("+data+")").json;
		  	$("<tr><td colspan='5' ><font color='red'>可用自定义表</font></td></tr>")
					.appendTo($("#settongjitable_biaojiantongji"));
		  	$.each(listObj,function(i,modulemanage){
		  		if(i==0){
		  			$("<tr  id='ftr"+rcount+"'></tr>")
						.appendTo($("#settongjitable_biaojiantongji"));
		  		}
				else {
					if(i%5==0){
						rcount++;
						$("<tr  id='ftr"+rcount+"'></tr>")
						.appendTo($("#settongjitable_biaojiantongji"));
						
						ccount=0;
					}
				}
				$("<td width='20%' title='"+modulemanage.tablename+"' onclick='addfieldbytablename(this);'><span style='cursor:hand'>"+modulemanage.modulename+"</span></td>")
                  		.appendTo($('#ftr'+rcount));
                ccount++;
                
                
			});
			
			for(var a=ccount;a<5;a++){
					$("<td width='20%'>&nbsp</td>")
                  			.appendTo($('#ftr'+rcount));
            }
		  }
	});
})

function addfieldbytablename(obj){
	$("#t_name").html($(obj).attr("title"));
	$("#settongjitable_biaojiantongji_field").html("");
	var rcount=0;
	var ccount=0;
	var tableName = $(obj).attr("title");
	$.ajax({
		  type: 'POST',
		  url: "list_field_by_tablename.action",
		  data: {'tableName':tableName},
		  async:true,
		  success:function(data){
		  		var listObj = eval("("+data+")").json;
		  		$("<tr><td colspan='5' ><font color='red'>可用自定义字段</font></td></tr>")
					.appendTo($("#settongjitable_biaojiantongji_field"));
				$.each(listObj,function(i,field){
			  		if(i==0){
			  			$("<tr  id='ftr_field"+rcount+"'></tr>")
							.appendTo($("#settongjitable_biaojiantongji_field"));
			  		}
					else {
						if(i%5==0){
							rcount++;
							$("<tr  id='ftr_field"+rcount+"'></tr>")
							.appendTo($("#settongjitable_biaojiantongji_field"));
							
							ccount=0;
						}
					}
					$("<td width='20%' title='"+tableName + "." + field.fieldName+"' onclick='addfield(this);'><input type='hidden' id='field_id_"+field.fieldName+"' value="+field.id+" /><span style='cursor:hand'>"+field.name+"</span></td>")
	                  		.appendTo($('#ftr_field'+rcount));
	                ccount++;
			    });
			    
			    for(var a=ccount;a<5;a++){
						$("<td width='20%'>&nbsp</td>")
	                  			.appendTo($('#ftr_field'+rcount));
	            }
		  }
	});
}

function addfield(obj){
	if(document.getElementById('tellmessage').innerHTML=='可以选择'){
		$("#relatetype2_select_columnname").text($(obj).text());
		$("#relatecolumnname").val($(obj).attr("title").split(".")[1]);
		document.getElementById('tellmessage').innerHTML='暂时不能选择';
		return;
	}
	//验证列是否是相关字段
	var id = "";
	var title = $(obj).attr("title");
	if(title != undefined && title != ""){
		id = $("#field_id_"+title).val();
		if(id != undefined && id != ""){
			id = parseInt(id);
		}
	};
	/**
	if(checkColumnIsJutileixingById("相关字段",id)==0){
		alert("您选择的字段不是相关字段,请重新选择!!!");
		return;
	}
	*/
	/**
	if(type == 2){
		if(checkColumnIsJutileixingById("整数",id)==0 || checkColumnIsJutileixingById("实数",id)==0){
			alert("您选择的字段不是数值字段,请重新选择!!!");
			return;
		}
	}else{
		if(checkColumnIsJutileixingById("相关字段",id)==0){
			alert("您选择的字段不是相关字段,请重新选择!!!");
			return;
		}
	}
	*/
	var html = $("#t_f_name").text();
	if(html==undefined||html==""||html=="暂无设置")    html = "";
	$("#t_f_name").text(html+title);
	
}

//判断字段是否是相关字段
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

//统计信息
function load(){
	var rcount=0;
	var ccount=0;
	var id = $("#lableid").val();
	var formula;
	if(id != null || id != ""){
		id = parseInt(id);
		$.ajax({
			  type: 'POST',
			  url: "showzijisuan.action",
			  data: {'customReport.id':id},
			  async:true,
			  success:function(data){
			  	var areaListObj = eval("("+data+")").jsonsorderField;
			  	$("#showtongjiname").empty();
			  	$.each(areaListObj,function(i,jsz){
			  		if(jsz.type == undefined )	jsz.type=0;
			  		var viewjindutiao = jsz.viewjindutiao;
			  		var formatnumber = jsz.formatnumber;
			  		var showview = jsz.showview;
			  		var relatetype = jsz.relatetype;
			  		if(jsz == undefined) jsz.relatecolumnname = "";
			  		var relatecolumnname = jsz.relatecolumnname;
			  		formula = jsz.formula;
			  		if(formula == undefined)	formula = "暂无设置";
			  		
			  		if(i==0){
			  			$("<tr id='tongjiname"+rcount+"'></tr>").appendTo($("#showtongjiname"));
			  		}
					else {
						if(i%5==0){
							rcount++;
							$("<tr  id='tongjiname"+rcount+"'></tr>")
							.appendTo($("#showtongjiname"));
							
							ccount=0;
						}
					}
			  		$("<td id="+jsz.columnname+" width='20%' title="+formula+" onclick='setjisuanzu(this,"+jsz.type+","+jsz.customreportid+");'><input type='hidden' id='"+jsz.columnname+"relatetype' value="+relatetype+" /><input type='hidden' id='"+jsz.columnname+"relatecolumnname' value="+relatecolumnname+" /><input type='hidden' id='"+jsz.columnname+"showview' value="+showview+" /><input type='hidden' id='"+jsz.columnname+"formatnumber' value="+formatnumber+" /><input type='hidden' id='"+jsz.columnname+"viewjindutiao' value="+viewjindutiao+" /><span  style='cursor:hand' >"+jsz.columnname+"</span></td>").appendTo($('#tongjiname'+rcount));
			  		ccount++;
			  	});
			  	for(var a=ccount;a<5;a++){
					$("<td width='20%'>&nbsp</td>")
                  			.appendTo($('#tongjiname'+rcount));
                }
			  }
		});
	}
}

$(
function aaa(){
			var rcount=0;
			var ccount=0;
			var name=$('#lablename').val();
			$.ajax({
			  type: 'POST',
			  url: "lableajax_showfile.action",
			  data: {tableName:"customreport",lableName:name},
			  async:true,//
			  success: function(data){
			  	var areaListObj = eval("("+data+")").jsonsnewField;
			  	
				/*构建查询字段显示*/
				$("#showfieldname").empty();
				$.each(areaListObj,function(i,table){
					var ftrid="";			
					$("<tr ><td colspan='5' ><font color='red'>"+table.name+"</font>的可用字段</td></tr>")
					.appendTo($("#showfieldname"));

					$.each(table.field,function(i,field){
							
							if(i%5==0){
								rcount++;
								ftrid=table.tableName+"-"+rcount
								$("<tr  id='ftr"+ftrid+"'></tr>")
									.appendTo($("#showfieldname"));
							
								ccount=0;
							}
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"' name='"+field.fieldType+"' id='fe"+field.tableName+"."+field.fieldName+"' onclick='addlable(this)' >"+field.name+"</td>")
		                   		.appendTo($('#ftr'+ftrid));
		                   		ccount++;
							});
					
							for(var a=ccount;a<5;a++){
								$("<td width='20%'>&nbsp</td>")
                   					.appendTo($('#ftr'+ftrid));
								
							}	
					});
				
			  }
			  
			});
		load();
		$.ajax({
			  type: 'POST',
			  url: "getallorderfield.action",
			  data: {tableName:"customreport",'customReport.name':name},
			  async:true,//
			  success: function(data){
				var areaListObj1 = eval("("+data+")").jsonsorderField;
			 	$.each(areaListObj1,function(i,table){
			 		//排序字段
					$("<optgroup style='color:blue' label='=="+table.name+"=='><optgroup/>")
                   		.appendTo($("#dbname1"));
					
					$.each(table.field,function(i,field){
						$("<option ></option>").val(table.tableName+"."+field.fieldName).text(field.name)
                   		.appendTo($("#dbname1"));
					});
					
					//分组字段
					$("<optgroup style='color:blue' label='=="+table.name+"=='><optgroup/>")
                   		.appendTo($("#groupname"));
                   	$.each(table.field,function(i,field){
						$("<option ></option>").val(table.tableName+"."+field.fieldName).text(field.name)
                   		.appendTo($("#groupname"));
					});
				});
			  }
		});

}
)



$(function(){
	
$("#orderset").click(function(){
var name=$('#lablename').val();
var seleval=$("#order").val();
var ltype=$("#labletype").val();
			$.ajax({
			  type: 'POST',
			  url: "updateorder.action",
			  data: {tableName:"customreport",'customReport.name':name,orderstr:seleval,'type':ltype},
			  async:true,//
			  success: function(data){

			 
			  var sele = $("#order").find('option:selected').text();
			$("#orderstatus").text(sele);
			$("#sql").text(data);
			  	
			  	

}
});

if(seleval==""){
 $("#showorderfield").empty();


}
});


})


$(
	function(){
		$("#orderfield").click(function(){
			if($("#orderstatus").text()=="无设置"){
				alert("请先设置排序方式");
				return;
			}
			else if($("#dbname1").val()==""){
				alert("请选择字段");
			}else{
				var rcount=0;
				var ccount=0;
				var  fieldsele=$("#dbname1").val();
				var name=$('#lablename').val();
				$.ajax({
			  		type: 'POST',
			  		url: "addorderfield.action",
			  		data: {tableName:"customreport",'customReport.name':name,fieldName:fieldsele},
			  		async:true,//
			  		success: function(data){
			  			$("#showorderfield").empty();
					
						var areaListObj = eval("("+data+")").jsonsorderField;
			  			$.each(areaListObj,function(i,field){
				  			if(i%3==0){
								rcount++;
								
								$("<tr  id='ortr"+rcount+"'></tr>")
								.appendTo( $("#showorderfield"));
								
								ccount=0;
							}
							
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this,1)' onmouseout='mout(this)'>"+field.name+"</td>")
		                   		.appendTo($('#ortr'+rcount));
		                   		ccount++;					 
			  			});
			  			for(var a=ccount;a<3;a++){
							$("<td width='20%'>&nbsp</td>")
	                   			.appendTo($('#ortr'+rcount));
						
						}
						$.ajax({
				  			type: 'POST',
				  			url: "getnewsql.action",
				  			data: {tableName:"customreport",'customReport.name':name},
				  			async:true,//
				  			success: function(data){
								$("#sql").text(data);
							}
						});
					}
				});
			}
		});
	}
)

$(
	function(){
		$("#groupfield").click(function(){
			if($("#groupname").val()==""){
				alert("请选择字段");
			}else{
				var rcount=0;
				var ccount=0;
				var  fieldsele=$("#groupname").val();
				var name=$('#lablename').val();
				if(window.confirm("添加分组字段后sql语句将改动，确定添加？")){
					$.ajax({
				  		type: 'POST',
				  		url: "addgroupfield.action",
				  		data: {tableName:"customreport",'customReport.name':name,fieldName:fieldsele},
				  		async:true,//
				  		success: function(data){
				  			$("#showgroupfield").empty();
						
							var areaListObj = eval("("+data+")").jsongroupField;
							
				  			$.each(areaListObj,function(i,field){
				  				
					  			if(i%3==0){
									rcount++;
									
									$("<tr  id='grouptr"+rcount+"'></tr>")
									.appendTo( $("#showgroupfield"));
									
									ccount=0;
								}
								
								$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this,2)' onmouseout='mout(this)'>"+field.name+"</td>")
			                   		.appendTo($('#grouptr'+rcount));
			                   		ccount++;					 
				  			});
				  			for(var a=ccount;a<3;a++){
								$("<td width='20%'>&nbsp</td>")
		                   			.appendTo($('#grouptr'+rcount));
							
							}
							$.ajax({
					  			type: 'POST',
					  			url: "getnewsql.action",
					  			data: {tableName:"customreport",'customReport.name':name},
					  			async:true,//
					  			success: function(data){
									$("#sql").text(data);
								}
							});
							
						}
					});
				}
			}
		});
	}
)



function   onms(obj,type){

 var title=$(obj).attr("title");
 $("#deldiv").attr("title",title+"-"+type);
 var offset =$(obj).offset(); 
 var left=offset.left+$(obj).width()-13;
	showdele(left,offset.top);
}
function  showdele(left,top){

 $("#deldiv").css({ position:"absolute" , left:left, top:top, display:"block" });
 var offset1 =$("#deldiv").offset();


}
function mout(){
	
$("#deldiv").css({ position:"absolute", display:"none" });


}
$(function() {
$('#deldiv').hover(function(){
                       $("#deldiv").css({ position:"absolute" , display:"block" });
                      },function(){
                             	
$("#deldiv").css({ position:"absolute", display:"none" });
                           });

})

$(function(){
 $("#deldiv").click(function(){
			var rcount=0;
			var ccount=0;
			var name=$('#lablename').val();
			var divtitle= $("#deldiv").attr("title");
			var divtitletype = 0;
			var divtitle_array = null;
			var url = "";
			if(divtitle.indexOf("-")!=-1){
				divtitle_array = divtitle.split("-");
				divtitle = divtitle_array[0];
				divtitletype = parseInt(divtitle_array[1]);
			}
			if(divtitletype == 1)	url = "updorderfielddele.action";
			else if(divtitletype == 2)	url = "updgroupfielddele.action";
			
			$.ajax({
			  type: 'POST',
			  url: url,
			  data: {tableName:"customreport",'customReport.name':name,fieldName:divtitle},
			  async:true,//
			  success: function(data){
			  		if(divtitletype == 1){
			  			$("#showorderfield").empty();
					
							var areaListObj = eval("("+data+")").jsonsorderField;
						  $.each(areaListObj,function(i,field){
						  if(i%3==0){
										rcount++;
										
										$("<tr  id='ortr"+rcount+"'></tr>")
										.appendTo( $("#showorderfield"));
										
										ccount=0;
										}
										
										$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this,1)' onmouseout='mout(this)'>"+field.name+"</td>")
					                   		.appendTo($('#ortr'+rcount));
					                   		ccount++;					 
						  });
						  for(var a=ccount;a<3;a++){
								$("<td width='20%'>&nbsp</td>")
			                   			.appendTo($('#ortr'+rcount));
								
							}
			  		}else{
			  			$("#showgroupfield").empty();
					
							var areaListObj = eval("("+data+")").jsongroupField;
						  $.each(areaListObj,function(i,field){
						  if(i%3==0){
										rcount++;
										
										$("<tr  id='grouptr"+rcount+"'></tr>")
										.appendTo( $("#showgroupfield"));
										
										ccount=0;
										}
										
										$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this,1)' onmouseout='mout(this)'>"+field.name+"</td>")
					                   		.appendTo($('#grouptr'+rcount));
					                   		ccount++;					 
						  });
						  for(var a=ccount;a<3;a++){
								$("<td width='20%'>&nbsp</td>")
			                   			.appendTo($('#grouptr'+rcount));
								
							}
			  		}
			  
				var ltype=$("#labletype").val();
			$.ajax({
			  type: 'POST',
			  url: "getnewsql.action",
			  data: {tableName:"customreport",'customReport.name':name,'type':ltype},
			  async:true,//
			  success: function(data){

			 
			  
			$("#sql").text(data);
			  	
			  	

}
});
			  
				}
				

	
			  });
			  
		  
			});
 
 })
 
 
 $( function(){
			var rcount=0;
			var ccount=0;
			var name=$('#lablename').val();
			$.ajax({
			  type: 'POST',
			  url: "updorderfielddele.action",
			  data: {tableName:"customreport",'customReport.name':name,fieldName:""},
			  async:true,//
			  success: function(data){
			  	$("#showorderfield").empty();
					
				var areaListObj = eval("("+data+")").jsonsorderField;
				if(areaListObj != undefined){
					$.each(areaListObj,function(i,field){
				  		if(i%3==0){
							rcount++;
							$("<tr  id='ortr"+rcount+"'></tr>").appendTo( $("#showorderfield"));
							ccount=0;
						}
								
						$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this,1)' onmouseout='mout(this)'>"+field.name+"</td>")
			                   		.appendTo($('#ortr'+rcount));
			                   		ccount++;					 
				  	});
				}
			  	
			  	for(var a=ccount;a<3;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#ortr'+rcount));
					
				}
				
 			   }
 			});
 
 })
 
 $( function(){
			var rcount=0;
			var ccount=0;
			var name=$('#lablename').val();
			$.ajax({
			  type: 'POST',
			  url: "updgroupfielddele.action",
			  data: {tableName:"customreport",'customReport.name':name,fieldName:""},
			  async:true,
			  success: function(data){
			  	$("#showgroupfield").empty();
					
				var areaListObj = eval("("+data+")").jsongroupField;
				if(areaListObj != undefined){
					$.each(areaListObj,function(i,field){
				  		if(i%3==0){
							rcount++;
							$("<tr  id='grouptr"+rcount+"'></tr>").appendTo( $("#showgroupfield"));
							ccount=0;
						}
								
						$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this,2)' onmouseout='mout(this)'>"+field.name+"</td>")
			                   		.appendTo($('#grouptr'+rcount));
			                   		ccount++;					 
				  	});	
				}
			  	for(var a=ccount;a<3;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#grouptr'+rcount));
					
				}
 			   }
 			});
 
 })
 
 
 
 
 $(
 function(){
 $("#seletype").change(function(){
 var selval=$("#seletype").val();
 if(selval==0){
 	 $("#wenbenshezhi").css({ display:"block" });
 	 $("#riqishezhi").css({ display:"none" });
 	 $("#shuzishezhi").css({ display:"none" });
 }
  if(selval==1){
 	 $("#wenbenshezhi").css({ display:"none" });
 	 $("#riqishezhi").css({ display:"block" });
 	 $("#shuzishezhi").css({ display:"none" });
 }
   if(selval==2){
 	 $("#wenbenshezhi").css({ display:"none" });
 	 $("#riqishezhi").css({ display:"none" });
 	 $("#shuzishezhi").css({ display:"block" });
 }
 
 });
 $("#quxiao").click(function(){
 
  $("#fielddiv").css({ position:"absolute", display:"none" });
 });
 $("#intolable").click(function(){
 	 var selval=$("#seletype").val();
 	 var divtitle=$("#fielddiv").attr("title");
 	 var strgeshi="";
 	 if(selval==0){
 	 var jiequgeshu = $("#textjiequ").val();
 	 var textjiequstr = $("#textjiequstr").val();
 	  var seleguolv = $("#seleguolv").val(); 
 	  var textnull = $("#textnull").val();	 
 	 	strgeshi="@"+divtitle+",text,"+jiequgeshu+","+textjiequstr+","+seleguolv+","+textnull+",^";
 	 
 	 }else if(selval==1){
 	 
	 	 var textriqi = $("#textriqi").val();
	 	 var textriqinull=$("#textriqinull").val();
 	 	strgeshi="@"+divtitle+",date,"+textriqi+","+textriqinull+",^";
 	 }else if(selval==2){
 	 var shu = $("input[name='shu'][checked]").val(); 
 	 var testxiaoshu = $("#testxiaoshu").val();
 	 strgeshi="@"+divtitle+",num,"+shu+","+testxiaoshu+",^";
 	 }
 	  if (document.all){
			  pos.text=strgeshi;
			 }else{
			   var obj=$("#LabelContent");
			   var lstr=obj.val().substring(0,pos);
			   var rstr=obj.val().substring(pos);
			   obj.val(lstr+strgeshi+rstr);
			 }
			 $("#fielddiv").css({ position:"absolute", display:"none" });
			 
 });
 $("input[name='shu']").change(function(){
 var valord=$("input[name='shu'][checked]").val(); 

 if(valord==0){
  $("#xiaoshuweishi").css({ display:"none" });
 }
 if(valord==1){
  $("#xiaoshuweishi").css({ display:"block" });
 }
 if(valord==2){
  $("#xiaoshuweishi").css({ display:"none" });
 }
 
 
 });
 }
 
 
 )
 
 function addlable(objf){
  
		 
 if(pos==null) {alert('请先定位插入位置!');return false;}
 else{
 
 var offset =$(objf).offset(); 
var type=$(objf).attr("name");
 $("#fielddiv").css({ position:"absolute" ,left:offset.left,top:offset.top, display:"block" });
  
  $("#wenbenshezhi").css({ display:"none" });
   $("#riqishezhi").css({ display:"none" });
   $("#shuzishezhi").css({ display:"none" });
 if(type=="文本"||type=="富文本"){
  $("#wenbenshezhi").css({ display:"block" });
  $("#seletype").val("0");
 }
  else if(type=="日期"){
  $("#riqishezhi").css({ display:"block" });
  $("#seletype").val("1");
  
 }
  else if(type=="数字"||type=="实数"||type=="整数"){
  $("#shuzishezhi").css({ display:"block" });
   $("#seletype").val("2");
 }
 else{
	 $("#wenbenshezhi").css({ display:"block" });
 }
 var val=$(objf).attr("title");
 $("#fielddivtit").text("字段名称："+val);
 $("#fielddiv").attr("title",val);

			
		
 
 }

 
 }
 
 
var pos=null;
		function setPos()
		{ if (document.all){
			$("#circulationListLablelable").focus();
		    pos = document.selection.createRange();
		  }else{
		    pos = document.getElementById("circulationListLablelable").selectionStart;
		  }
		}
 
 //设置统计信息
 function settongji(objf){
 	var offset =$(objf).offset(); 
 	$("#tongjidiv").css({ position:"absolute" ,left:offset.left,top:offset.top, display:"block" });
 	//$("<tr ><td colspan='5' ><font color='red'>sss</font>的可用字段</td></tr>").appendTo($("#showfieldname"));
 }
 
 function show_hide(){
 	$("#relatetype2_select").css({display:"none"});
 	$("#relatetype3_select").css({display:"none"});
 	
 	$("#viewjindutiao").attr("checked",false);
 	$("#viewjindutiao").val(0);
 }
 
 function setjisuanzu(objf,type,id){
 	show_hide();
 	columnname = $(objf).text();
 	var formula = $(objf).attr("title");
 	var id = $(objf).attr("id");
 	var offset =$(objf).offset(); 
 	$("#settongjidiv").css({ position:"absolute" ,left:offset.left,top:offset.top, display:"block" });
 	
 	if(type != 0 ){
		if(type == 1){
			var formatnumber = $("#"+columnname+"formatnumber").val();
		 	var viewjindutiao = $("#"+columnname+"viewjindutiao").val();
			$("#settongji_radio_biaonei").attr("checked","checked");
 			biaoneitongji();
 			$("#jisuanzu_jisuan").html(formula);
 			$("#formatnumber").val(formatnumber);
 			if(parseInt($("#"+id+"viewjindutiao").val())==1){
 				$("#viewjindutiao").attr("checked",true);
 				$("#viewjindutiao").val(1);
 			}
		}else if(type == 2){
			$("#settongji_radio_biaojian").attr("checked","checked");
 			biaojiantongji(formula,type);
 			if(parseInt($("#"+id+"showview").val())==1){
 				$("#showview").attr("checked",true);
 				$("#showview").val(1);
 			}
 			var radios = document.getElementsByName("customReportJSZ.relatetype");
 			var radio;
 			for(var i=0;i<radios.length;i++){
 				radio = radios[i];
 				if(parseInt(radio.value) == parseInt($("#"+id+"relatetype").val())){
 					radio.checked = true;
 				}else{
 					radio.checked = false;
 				}
 			}
 			
			if(parseInt(parseInt($("#"+id+"relatetype").val())) == 2){
				$("#relatetype2_select").css({display:"block"});
				$("#relatetype2_select_columnname").text(columnname);
				$("#relatecolumnname").val($("#"+id+"relatecolumnname").val());
			}else if(parseInt(parseInt($("#"+id+"relatetype").val()))==3){
				$("#relatetype3_select").css({display:"block"});
				//将相关的统计字段查询
				getRelateTongji();
			}
			$("#t_f_name").html("<td colspan=2 align='center'><span>"+formula+"</span></td>");
		}
	}
 }
 
 var haha = "<tr>"+
												"<td align='center' width='16%' onclick='relatetype3(this,1);'>"+
													"<span title='加' style='cursor: hand'>+</span>"+
												"</td>"+
												"<td align='center' width='16%' onclick='relatetype3(this,1);'>"+
													"<span title='减' style='cursor: hand'>-</span>"+
												"</td>"+
												"<td align='center' width='16%' onclick='relatetype3(this,1);'>"+
													"<span title='乘' style='cursor: hand'>*</span>"+
												"</td>"+
												"<td align='center' width='16%' onclick='relatetype3(this,1);'>"+
													"<span title='除' style='cursor: hand'>/</span>"+
												"</td>"+
												"<td align='center' width='16%' onclick='relatetype3(this,1);'>"+
													"<span title='左括号' style='cursor: hand'>(</span>"+
												"</td>"+
												"<td align='center' width='16%' onclick='relatetype3(this,1);'>"+
													"<span title='右括号' style='cursor: hand'>)</span>"+
												"</td>"+
											"</tr>";
 
 function getRelateTongji(){
 	$("#relatetype2").html(haha);
 											
 	var cusreportid = $("#lableid").val();
 	if(cusreportid!=undefined&&cusreportid!=""){
 		cusreportid = parseInt(cusreportid);
 	}
 	var rcount=0;
	var ccount=0;
 	$.ajax({
		  type: 'POST',
		  url: "select_relatetype2.action",
		  data: {'customReport.id':cusreportid},
		  async:true,
		  success:function(data){
			  	var areaListObj = eval("("+data+")").json;
			  	$.each(areaListObj,function(i,jsz){
			  		if(jsz.type == undefined )	jsz.type=0;
			  		var viewjindutiao = jsz.viewjindutiao;
			  		var formatnumber = jsz.formatnumber;
			  		var showview = jsz.showview;
			  		var relatetype = jsz.relatetype;
			  		if(jsz == undefined) jsz.relatecolumnname = "";
			  		var relatecolumnname = jsz.relatecolumnname;
			  		formula = jsz.formula;
			  		if(formula == undefined)	formula = "暂无设置";
			  		
			  		if(i==0){
			  			$("<tr id='field_relatetype"+rcount+"'></tr>").appendTo($("#relatetype2"));
			  		}
					else {
						if(i%6==0){
							rcount++;
							$("<tr  id='field_relatetype"+rcount+"'></tr>")
							.appendTo($("#relatetype2"));
							
							ccount=0;
						}
					}
			  		$("<td id="+jsz.columnname+" width='16.6%' title="+formula+" onclick='relatetype3(this,2);'><input type='hidden' id='"+jsz.columnname+"relatetype' value="+relatetype+" /><input type='hidden' id='"+jsz.columnname+"relatecolumnname' value="+relatecolumnname+" /><input type='hidden' id='"+jsz.columnname+"showview' value="+showview+" /><input type='hidden' id='"+jsz.columnname+"formatnumber' value="+formatnumber+" /><input type='hidden' id='"+jsz.columnname+"viewjindutiao' value="+viewjindutiao+" /><span  style='cursor:hand' >"+jsz.columnname+"</span></td>").appendTo($('#field_relatetype'+rcount));
			  		ccount++;
			  	});
			  	for(var a=ccount;a<6;a++){
					$("<td width='20%'>&nbsp</td>")
                  			.appendTo($('#field_relatetype'+rcount));
                }
			  }
	});
 }
 
 function relatetype3(obj,type){
 	var value = "";
 	if(type == 1){
 		value = $(obj).text().trim();
 	}else if(type == 2){
 		value = $(obj).attr("title").trim();
 	}
 	var t_f_name_html =  $("#t_f_name").text();
 	if(t_f_name_html==undefined||t_f_name_html==""||t_f_name_html=="暂无设置")		t_f_name_html = "";
 	$("#t_f_name").text(t_f_name_html + value);
 }
 
 function charu(){
 	//$("#showtongjiname").empty();
 	var value = $("#tongjiname").val();
 	if(value == null || value == ""){
 		alert("请填写您要添加的统计信息字段的名称");
 		return;
 	}
 	
 	var type = 0;
 	var radios = document.getElementsByName("table_tongji_type");
 	var radio ;
 	for(var i=0;i<radios.length;i++){
 		radio = radios[i];
 		if(radio.checked){
 			type = radio.value;
 		}
 	}
 	if(type == 0){
 		alert("请选择统计方式!!!");
 		return;
 	}
 	
 	if($("#checkmessage").text()=="名字已存在请重新输入"){
 		return;
 	}
 	
 	if(window.confirm("确认插入?")){
 		charu_submit(value,type);
 		
 		load();
 	}
 	$("#tongjidiv").css({ display:"none" });
 }
 
 function quxiao(objf){
 	$(objf).css({ display:"none" });
 }
 
 function charu_submit(value,type){
 	var id = "<s:property value="customReport.id"/>";
 	if(id != null || id != ""){
 		id = parseInt(id);
 	}
 			$.ajax({
			  type: 'POST',
			  url: "insertjisuanzu.action",
			  data: {jisuanzuname:value,'customReport.id':id,type:type},
			  async:false,//同步
			  success: function(data){
			  	alert("插入成功!!!");
			  }
			});
 }
 
 //根据标签id查找分组信息
 function getLableById(id){
 	var value = "";
 	$.ajax({
			  type: 'POST',
			  url: "getLableById.action",
			  data: {'customReport.id':id},
			  async:false,
			  success: function(data){
			  	if(data != undefined ){
			  		value = data;
			  	}
			  }
	});
 	return value;
 }
 
 var globle_type ;//全局保存
 var globle_group_name;
 var columnname;
 function addToJisuan(obj,number){
 	var title;
 	var fieldName ;
 	if(number == 1){//字段
 		title  = $(obj).attr("title");
 		fieldName = title.substring(title.indexOf(".")+1,title.length);
 		if(globle_type != undefined ){
 			if(globle_type == 3){
 				fieldName = globle_group_name + "(" + fieldName + ")";
 			}
 		}
 		var fieldType = $(obj).attr("name");
 		/**
	 	if(fieldType != "实数" && fieldType != "整数"){
	 		alert("字段类型不为'整数'或者'实数'，请重新选择!!!");
	 		return ;
	 	}
	 	*/
 	}else if(number == 2){//+,-,*,/,(,)
 		fieldName = $(obj).html();
 	}else if(number == 3){//count,min,max,sum,avg
 		//判断有无分组信息
 		if(getLableById(parseInt('${customReport.id}')) != ""){
 			globle_type = 3;
	 		globle_group_name = $(obj).html();
	 		$("#operator").html("您选择的操作符是'"+$(obj).html()+"'");
 		}else{
 			alert("标签暂无分组设置，无法选择分组操作符");
 			return;
 		}
 	}
 	
 	if(number == 1 || number == 2){
 		if($("#jisuanzu_jisuan").html() == undefined || $("#jisuanzu_jisuan").html() == "暂无设置"){
 			$("#jisuanzu_jisuan").html("");
 		}
 		
 		$("#jisuanzu_jisuan").html($("#jisuanzu_jisuan").html() + "" + fieldName);
 	}
 }
 
 //表内统计
 function biaoneitongji(){
 		$("#biaojiantongjitr").css({display:"none"});
 		$("#biaoneitongjitr").css({display:"block"});
 			var rcount=0;
			var ccount=0;
			var name=$('#lablename').val();
			$.ajax({
			  type: 'POST',
			  url: "lableajax_showfile.action",
			  data: {tableName:"customreport",lableName:name},
			  async:true,//
			  success: function(data){
			  	var areaListObj = eval("("+data+")").jsonsnewField;
			  	
			  	$("#settongjitable_biaoneitongji").html("");
				/*构建查询字段显示*/
				$.each(areaListObj,function(i,table){
					var ftrid="";			
					$("<tr ><td colspan='5' ><font color='red'>"+table.name+"</font>的可用字段</td></tr>")
					.appendTo($("#settongjitable_biaoneitongji"));

					$.each(table.field,function(i,field){
							
							if(i%5==0){
							rcount++;
							ftrid=table.tableName+"-"+rcount
							$("<tr  id='ftr"+ftrid+"'></tr>")
							.appendTo($("#settongjitable_biaoneitongji"));
							
							ccount=0;
							}
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"' name='"+field.fieldType+"' id='fe"+field.tableName+"."+field.fieldName+"' onclick='addToJisuan(this,1)' >"+field.name+"</td>")
		                   		.appendTo($('#ftr'+ftrid));
		                   		ccount++;
							});
					
					for(var a=ccount;a<5;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#ftr'+ftrid));
					
				}	
				});
				
			  }
			  
			});
 	
 }
 
 //表间统计
 function biaojiantongji(formula){
 	$("#biaoneitongjitr").css({display:"none"});
 	$("#biaojiantongjitr").css({display:"block"});
 	
 	
 	$("#settongjitable_biaojiantongji_field").html("");
	var rcount=0;
	var ccount=0;
	var tableName = formula.split(".")[0];
	$.ajax({
		  type: 'POST',
		  url: "list_field_by_tablename.action",
		  data: {'tableName':tableName},
		  async:true,
		  success:function(data){
		  		var listObj = eval("("+data+")").json;
		  		$("<tr><td colspan='5' ><font color='red'>可用自定义字段</font></td></tr>")
					.appendTo($("#settongjitable_biaojiantongji_field"));
				$.each(listObj,function(i,field){
			  		if(i==0){
			  			$("<tr  id='ftr_field"+rcount+"'></tr>")
							.appendTo($("#settongjitable_biaojiantongji_field"));
			  		}
					else {
						if(i%5==0){
							rcount++;
							$("<tr  id='ftr_field"+rcount+"'></tr>")
							.appendTo($("#settongjitable_biaojiantongji_field"));
							
							ccount=0;
						}
					}
					$("<td width='20%' title='"+tableName+"."+field.fieldName+"' onclick='addfield(this);'><input type='hidden' id='field_id_"+field.fieldName+"' value="+field.id+" /><span style='cursor:hand'>"+field.name+"</span></td>")
	                  		.appendTo($('#ftr_field'+rcount));
	                ccount++;
			    });
			    
			    for(var a=ccount;a<5;a++){
						$("<td width='20%'>&nbsp</td>")
	                  			.appendTo($('#ftr_field'+rcount));
	            }
		  }
	});
 }
 
 
 //插入计算公式
 function formula_insert_by_ajax(){
 	var type = 0;
 	var value = "";
 	if($("#settongji_radio_biaonei").attr("checked")=="checked"){
 		type = 1;
 		value = $("#jisuanzu_jisuan").text();
 		
 		if(value == null || value == ""){
	 		alert("计算公式不能为空，操作即将终止!!!");
	 		return;
	 	}
 		
 		var formatnumber = document.getElementById("formatnumber").value;
	 	if(formatnumber == undefined || formatnumber == "")	formatnumber=0;
	 	var checkbox = document.getElementById("viewjindutiao");
	 	var checkvalue = 0;
	 	if(checkbox.checked){
	 		checkvalue=1;
	 	}
	 	
	 	if(columnname == undefined || columnname == ""){return;}
		$.ajax({
		  type: 'POST',
		  url: "formula_insert.action",
		  data: {value:value,type:type,'customReportJSZ.columnname':columnname,formatnumber:formatnumber,checkvalue:checkvalue},
		  async:true,//同步
		  success: function(data){
		  	//更改sql
		  	var name=$('#lablename').val();
		  	$.ajax({
		 			type: 'POST',
		 			url: "getnewsql.action",
		 			data: {tableName:"customreport",'customReport.name':name},
		 			async:true,//
		 			success: function(data){
					$("#sql").text(data);
				}
			});
		  	alert("插入计算公式成功!!!");
		  	$("#"+columnname).attr("title",$("#jisuanzu_jisuan").text());
		  }
		});
 	}else if($("#settongji_radio_biaojian").attr("checked")=="checked"){
 		type = 2;
 		value = $("#t_f_name").text();
 		
 		var checkbox = document.getElementById("showview");
	 	var checkvalue = 0;
	 	if(checkbox.checked){
	 		checkvalue=1;
	 	}
	 	
	 	var radios = document.getElementsByName("customReportJSZ.relatetype");
	 	var radio ;
	 	var radiovalue = 1;
	 	for(var i=0;i<radios.length;i++){
	 		radio = radios[i];
	 		if(radio.checked){
	 			radiovalue = parseInt(radio.value);
	 		}
	 	}
	 	
	 	var customReportJSZ_relatecolumnname = "";
	 	if(radiovalue == 2){
	 		customReportJSZ_relatecolumnname = $("#relatecolumnname").val();
	 		if(customReportJSZ_relatecolumnname == undefined)  customReportJSZ_relatecolumnname = "";
	 	}
 		
 		if(value == null || value == ""){
	 		alert("计算公式不能为空，操作即将终止!!!");
	 		return;
	 	}else{
	 		//var partten = /^[A-Z]+{.}[A-Z]+$/;
	 		if(value.indexOf(".")==-1){
			//if(!partten.test(value)){
			 	alert("格式错误,请重新填写!");
			    return false;
			}else{
				var arr = value.split(".");
				if(arr[0] != arr[1].split("_")[0]){
					alert("您选择的字段名不属于您选择的表，请查看!!!");
					return;
				}
			}
	 	}
	 	$.ajax({
		  type: 'POST',
		  url: "formula_insert_biaojian.action",
		  data: {value:value,type:type,'customReportJSZ.columnname':columnname,checkvalue:checkvalue,'customReportJSZ.relatetype':radiovalue,'customReportJSZ.relatecolumnname':customReportJSZ_relatecolumnname},
		  async:true,//同步
		  success: function(data){
		  	alert("插入计算公式成功!!!");
		  }
		});
	 	
 	}
 	
 	
 }
 //将自己填写的数值加入计算
 function addSomeValueTojisuanzu(){;
 	if(document.getElementById("some_value").value == ""){
 		alert("填写的数值不能为空,请填写!!!");
 		document.getElementById("some_value").focus();
 		return;
 	}else{
 		if(isNaN(document.getElementById("some_value").value)){
   			alert("填写的数值只能为数字,请重新填写!!!");
   			document.getElementById("some_value").value=="";
   			document.getElementById("some_value").focus();
   			return ;
   		}
 	}
 	
 	$("#jisuanzu_jisuan").html($("#jisuanzu_jisuan").html() + "" + document.getElementById("some_value").value);
 }
 
 //清空操作符
 function clearupOperator(){
 	globle_type = 0;
 	$("#operator").html("");
 }
 
 function qingkong(){
 	if(document.getElementById('jisuanzu_jisuan').innerHTML!=''){
 		document.getElementById('jisuanzu_jisuan').innerHTML = "";
 	}else{
 		document.getElementById('t_f_name').innerHTML = "";
 	}
 }
 
 
 function checkJSZNameIsExist(obj){
 	var value = obj.value;
 	var customreportid = parseInt('${customReport.id}');
 	$.ajax({
		  type: 'POST',
		  url: "checkJSZNameIsExist.action",
		  data: {value:value,'customReport.id':customreportid},
		  async:true,//同步
		  success: function(data){
		  	if(data!=""){
		  		$("#checkmessage").html(data);
		  	}
		  }
		});
 }
 
 //添加的统计字段重新排序
 function reorder(){
 	var lableid = parseInt('${customReport.id}');
 	width=600;	
 	height=400;	
 	var url = "getJSZByLableid.action?customReport.id="+lableid+"&rn="+Math.random();
  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
  	var rv = window.showModalDialog(url,null,sFeature);
  	if(rv!=undefined){
  		if(rv = 1){
  			alert("修改排序成功!!!");
  			load();
  		}
  	}
 }
 
 function show_relatetype(type){
 	if(type == 2){
 		$("#relatetype2_select").css({display:"block"});
 		$("#relatetype3_select").css({display:"none"});
 	}else if(type == 3){
 		$("#relatetype2_select").css({display:"none"});
 		$("#relatetype3_select").css({display:"block"});
 		
		//将相关的统计字段查询
		getRelateTongji();
 	}
 }
</script>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<span>首页</span>&nbsp;>>&nbsp;
					<span>系统管理</span>&nbsp;>>&nbsp;
					<span>自定义报表</span>&nbsp;>>&nbsp;
					<span>设置自定义报表</span>&nbsp;>>&nbsp;
					<span>步骤4</span>
				</div>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<div style="DISPLAY: none;" title="" id="settongjidiv">
			<table width="600">
				<caption>
					设置计算组
				</caption>
				<tr>
					<td align="center" colspan=3>
						<input type="radio" id="settongji_radio_biaonei"
							name="settongji_radio" onClick="biaoneitongji();" />
						表内统计
						<input type="radio" id="settongji_radio_biaojian"
							name="settongji_radio" onClick="biaojiantongji();" />
						表间统计
					</td>
				</tr>
				<tr id="biaoneitongjitr" style="display: none;">
					<td colspan=3>
						<table width="100%">
							<tr>
								<td width="50%">
									<table id="settongjitable_biaoneitongji">
									</table>
								</td>
								<td width="50%">
									<table width="100%">
										<tr>
											<td colspan=6>
												显示几位小数
												<input type="text" id="formatnumber" style="width: 80px" />
												是否显示进度条
												<input type="checkbox" name="viewjindutiao"
													id="viewjindutiao" value=0 />
											</td>
										</tr>
										<tr>
											<td colspan=6 align="right">
												输入您想要计算的数值：
												<input type="text" id="some_value" style="width: 80px" />
												<input type="button" value="加入计算"
													onclick="addSomeValueTojisuanzu()" />
											</td>
										</tr>
										<tr>
											<td colspan=6 align="right">
												<input type="button" value="清空操作符"
													onclick="clearupOperator()" />
											</td>
										</tr>
										<tr>
											<td colspan=6 align="center">
												<span style="color: red" id="operator"></span>
											</td>
										</tr>
										<tr>
											<td align="center" width="16%" onclick="addToJisuan(this,2)">
												<span title="加" style="cursor: hand">+</span>
											</td>
											<td align="center" width="16%" onclick="addToJisuan(this,2)">
												<span title="减" style="cursor: hand">-</span>
											</td>
											<td align="center" width="16%" onclick="addToJisuan(this,2)">
												<span title="乘" style="cursor: hand">*</span>
											</td>
											<td align="center" width="16%" onclick="addToJisuan(this,2)">
												<span title="除" style="cursor: hand">/</span>
											</td>
											<td align="center" width="16%" onclick="addToJisuan(this,2)">
												<span title="左括号" style="cursor: hand">(</span>
											</td>
											<td align="center" width="16%" onclick="addToJisuan(this,2)">
												<span title="右括号" style="cursor: hand">)</span>
											</td>
										</tr>
										<tr>
											<td align="center" width="20%" onclick="addToJisuan(this,3)">
												<span title="记录数" style="cursor: hand">count</span>
											</td>
											<td align="center" width="20%" onclick="addToJisuan(this,3)">
												<span title="最小值" style="cursor: hand">min</span>
											</td>
											<td align="center" width="20%" onclick="addToJisuan(this,3)">
												<span title="最大值" style="cursor: hand">max</span>
											</td>
											<td align="center" width="20%" onclick="addToJisuan(this,3)">
												<span title="单列和" style="cursor: hand">sum</span>
											</td>
											<td align="center" width="20%" onclick="addToJisuan(this,3)">
												<span title="平均值" style="cursor: hand">avg</span>
											</td>
											<td align="center" width="20%" onclick="addToJisuan(this,3)">
												<span title="总和" style="cursor: hand">total</span>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan=2 align="center">
									<span id="jisuanzu_jisuan"></span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="biaojiantongjitr" style="display: none;">
					<td colspan=3>
						<table>
							<tr>
								<td width="50%">
									<table id="settongjitable_biaojiantongji" width="100%"
										style="margin-top: 0px"></table>
								</td>
								<td width="50%">
									<table id='settongjitable_biaojiantongji_field' width="100%"
										style="margin-top: 0px"></table>
									<br>
									是否显示查看按钮
									<input type='checkbox' name='showview' id='showview' value=0 />
									<br>
									<input type='radio' name='customReportJSZ.relatetype' value=1 />
									求总数量
									<br>

									<input type='radio' name='customReportJSZ.relatetype' value=2 onclick="show_relatetype(2);"/>
									求总和(单纯的求添加的统计字段)
									<br>
									<div style='display:none' id='relatetype2_select'>
										<span>请选择统计字段的关联字段:</span>
										<br>
										<span style='color:red' id='tellmessage'>暂时不能选择</span>
										<input type='button' value='选择' onclick="document.getElementById('tellmessage').innerHTML = '可以选择'" />
										<br>
										<span>您选择的关联字段是:</span><span style='color:red' id='relatetype2_select_columnname'></span><input type='text' id='relatecolumnname' />
									</div>

									<input type='radio' name='customReportJSZ.relatetype' value=3 onclick="show_relatetype(3);"/>
									求总和(表内字段与添加的统计字段再进行统计)
									<br>
									<div style='display:none' id='relatetype3_select'>
										<table width='100%' id='relatetype2'>
											<tr>
												<td align="center" width="16%" onclick="relatetype3(this,1);">
													<span title="加" style="cursor: hand">+</span>
												</td>
												<td align="center" width="16%" onclick="relatetype3(this,1);">
													<span title="减" style="cursor: hand">-</span>
												</td>
												<td align="center" width="16%" onclick="relatetype3(this,1);">
													<span title="乘" style="cursor: hand">*</span>
												</td>
												<td align="center" width="16%" onclick="relatetype3(this,1);">
													<span title="除" style="cursor: hand">/</span>
												</td>
												<td align="center" width="16%" onclick="relatetype3(this,1);">
													<span title="左括号" style="cursor: hand">(</span>
												</td>
												<td align="center" width="16%" onclick="relatetype3(this,1);">
													<span title="右括号" style="cursor: hand">)</span>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td id="t_f_name" colspan=2 align='center'>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center">
						<input type="button" value="提交" onClick="formula_insert_by_ajax()" />
					</td>
					<td align="center">
						<input type="button" value="清空" onClick="qingkong();" />
					</td>
					<td align="center">
						<input type="button" value="取消"
							onClick="quxiao(document.getElementById('settongjidiv'));" />
					</td>
				</tr>
			</table>
		</div>

		<div style="DISPLAY: none;" title="" id="tongjidiv">
			<table width="350">
				<caption>
					添加显示统计信息的名称
				</caption>
				<tr>
					<td align="center">
						<input type="radio" name="table_tongji_type" value=1 />
						表内统计
						<input type="radio" name="table_tongji_type" value=2 />
						表间统计
					</td>
				</tr>
				<tr>
					<td align="center">
						统计字段名称:
						<input id="tongjiname" name="tongjiname" style="width: 50%"
							onkeyup="checkJSZNameIsExist(this);" />
						<br>
						<span style='color: red' id='checkmessage'></span>
					</td>
				</tr>
				<tr>
					<td align="center">
						<input type="button" value="插入" onClick="charu();" />
						<input type="button" value="取消"
							onClick="quxiao(document.getElementById('tongjidiv'));" />
					</td>
				</tr>
			</table>
		</div>
		<div style="DISPLAY: none;" title="" id="fielddiv">
			<table width="250">
				<caption id="fielddivtit"></caption>
				<tr>
					<td>
						数据类型
					</td>
					<td>
						<select name='' id='seletype' class="textbox"
							style="WIDTH: 150px;">
							<option value='0'>
								文本
							</option>
							<option value='1'>
								日期
							</option>
							<option value='2'>
								数字
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table id="wenbenshezhi" style="display: none">
							<tr>
								<td>
									截取长度
								</td>
								<td>
									<input type="text" id="textjiequ" />
								</td>
							</tr>
							<tr>
								<td>
									截断显示字符串
								</td>
								<td>
									<input type="text" value="..." id="textjiequstr" />
								</td>
							</tr>
							<tr>
								<td>
									是否过滤
								</td>
								<td>
									<select name='' id='seleguolv' class="textbox"
										style="WIDTH: 150px;">
										<option value="0">
											不过滤html
										</option>
										<option value="1">
											过滤html代码
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									为空显示
								</td>
								<td>
									<input type="text" id="textnull" />
								</td>
							</tr>
						</table>
						<table id="riqishezhi" style="display: none">
							<tr>
								<td>
									日期格式
								</td>
								<td>
									<input type="text" id="textriqi" />
								</td>
							</tr>
							<tr>
								<td>
									为空设置
								</td>
								<td>
									<input type="text" value="" id="textriqinull" />
								</td>
							</tr>
						</table>
						<table id="shuzishezhi" style="display: none">
							<tr>
								<td>
									数字类型
								</td>
								<td>
									<input type="radio" name="shu" value="0" id="radioshuzi"
										checked="checked" />
									原数
									<input type="radio" name="shu" value="1" />
									小数
									<input type="radio" name="shu" value="2" />
									百分数
								</td>
							</tr>
							<tr id="xiaoshuweishi" style="display: none">
								<td>
									小数位数
								</td>
								<td>
									<input type="text" value="2" id="testxiaoshu" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<input type="button" value="插入" id="intolable" />
					</td>
					<td>
						<input type="button" value="取消" id="quxiao" />
					</td>
				</tr>
			</table>
		</div>
		<div style="DISPLAY: none;" title="" id="deldiv">
			<img src="images/lable/close.gif" />
		</div>
		标签名称：
		<s:property value="customReport.name" />
		<form action="updateCustomReportFinal.action" name="myform"
			method="post">
			<s:hidden name="customReport.name" id="lablename"></s:hidden>

			<s:hidden name="customReport.id" id="lableid" />
			<table width="1060">
				<tr>
					<td valign="bottom">
						sql语句预览
						<s:textarea rows="10" cols="100" name="customReport.sql" id="sql"
							style="white-space: 10px;border-color: black;" theme="simple"
							readOnly="true"></s:textarea>
					</td>
				</tr>
			</table>
			<table style="margin-top: 10px" width="1060" border=0 cellpadding='2'
				cellspacing='1' class='border'>
				<tr>
					<td>
						<table width="100%" style="margin-top: 0px">
							<caption>
								可用字段
							</caption>
						</table>
						<table id="showfieldname" width="100%" style="margin-top: 0px">

						</table>

						<span style="color: red; cursor: hand" onClick="settongji(this);">添加统计信息</span>
						<input type='button' value='操作' onclick='reorder();' />
						<table id="showtongjiname" width="100%" style="margin-top: 0px">

						</table>
					</td>
				</tr>
				<tr class="tdbg">
					<td>
						标签编辑框
					</td>
				</tr>
				<tr>
					<td bordercolor="black">
						<table width="100%">
							<tr>
								<td rowspan="3" width="200">
									<s:textarea rows="10" cols="80" name="customReport.lable"
										id="circulationListLablelable"
										style="white-space: 10px;border-color: black;" theme="simple"
										onclick='setPos()' onkeyup='setPos()'></s:textarea>
								</td>
								<td width="100px">
									当前排序状态:
									<span id="orderstatus" style='color: red'><s:property
											value="customReport.Ordername" />
									</span>
									<br>
									<select name='order' id='order' class="textbox"
										style="WIDTH: 100px;">
										<option value=''>
											无设置
										</option>
										<option value='desc'>
											降序
										</option>
										<option value='asc'>
											升序
										</option>
									</select>
									<input type="button" id="orderset" value="确定设置" />
								</td>
								<td rowspan="3" valign="top" width="250">
									<span style="color: red">排序字段如下：</span>
									<table id="showorderfield">

									</table>
									<span style="color: red">分组字段如下：</span>
									<table id="showgroupfield">

									</table>
								</td>
							</tr>
							<tr>
								<td>
									选择排序字段
									<br>
									<select name='dbname1' id='dbname1' class="textbox"
										style="WIDTH: 150px;">
										<option value="">
											选择排序字段
										</option>
									</select>
									<input type="button" id="orderfield" value="加入字段" />
								</td>
							</tr>
							<tr>
								<td>
									选择分组字段
									<br>
									<select name='groupname' id='groupname' class="textbox"
										style="WIDTH: 150px;">
										<option value="">
											选择分组字段
										</option>
									</select>
									<input type="button" id="groupfield" value="加入字段" />
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td align="left">
						<table width="100%">
							<tr>
								<td align="right">
									<input type="button" value="上一步" onclick='history.go(-1);' />
								</td>
								<td width="100px">
									<input type="submit" value="下一步" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
