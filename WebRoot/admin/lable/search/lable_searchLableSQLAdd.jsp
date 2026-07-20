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
		<link href="css/lable/Admin_Style.CSS" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/message.js"></script>
		<SCRIPT src="quiz_searchlist.files/cexampaper.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<SCRIPT type="text/javascript" src="js/jquery/jquery-1.7.2.min.js" ></script>
		<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
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


$(
function aaa(){
			var rcount=0;
			var ccount=0;
			var name=$('#lablename').val();
			$.ajax({
			  type: 'POST',
			  url: "lableajax_showfile.action",
			  data: {tableName:"lable_search",lableName:name},
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

		$.ajax({
			  type: 'POST',
			  url: "lableajax_getallorderfield.action",
			  data: {tableName:"lable_search",'lable.name':name},
			  async:true,//
			  success: function(data){
					
				var areaListObj1 = eval("("+data+")").jsonsorderField;
			 $.each(areaListObj1,function(i,table){
					
					
					$("<optgroup style='color:blue' label='=="+table.name+"=='><optgroup/>")
                   		.appendTo($("#dbname1"));
					
				
					
					$.each(table.field,function(i,field){
						$("<option ></option>").val(table.tableName+"."+field.fieldName).text(field.name)
                   		.appendTo($("#dbname1"));
							});
							});
}
});



 

}
)
/*
$(
function bbb(){

			var name=$('#lablename').val();
		$.ajax({
			  type: 'POST',
			  url: "lableajax_getallorderfield.action",
			  data: {tableName:"lable_search",'lable.name':name},
			  async:true,//
			  success: function(data){
					
				var areaListObj = eval("("+data+")").jsonsorderField;
			 $.each(areaListObj,function(i,table){
					if(i==0){
						$("<option  style='color:red' value=''>选择一个用于排序的字段</option>")
						.appendTo($("#dbname1"));
					}
					
					$("<optgroup style='color:blue' label='=="+table.name+"=='><optgroup/>")
                   		.appendTo($("#dbname1"));
					
				
					
					$.each(table.field,function(i,field){
						$("<option ></option>").val(table.name+"."+field.fieldName).text(field.name)
                   		.appendTo($("#dbname1"));
							});

}
});

});
})
*/
$(
function(){
	
$("#orderset").click(function(){
var name=$('#lablename').val();
var seleval=$("#order").val();
var ltype=$("#labletype").val();
			$.ajax({
			  type: 'POST',
			  url: "lableajax_updorder.action",
			  data: {tableName:"lable_search",'lable.name':name,orderstr:seleval,'type':ltype},
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
			}
			else{
			var rcount=0;
			var ccount=0;
			var  fieldsele=$("#dbname1").val();
			var name=$('#lablename').val();
		$.ajax({
			  type: 'POST',
			  url: "lableajax_updorderfield.action",
			  data: {tableName:"lable_search",'lable.name':name,fieldName:fieldsele},
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
							
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+field.name+"</td>")
		                   		.appendTo($('#ortr'+rcount));
		                   		ccount++;					 
			  });
			  for(var a=ccount;a<3;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#ortr'+rcount));
					
				}
		var ltype=$("#labletype").val();
		$.ajax({
		
			  type: 'POST',
			  url: "lableajax_getnewsql.action",
			  data: {tableName:"lable_search",'lable.name':name,'type':ltype},
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



function   onms(obj){

 var title=$(obj).attr("title");
 $("#deldiv").attr("title",title);
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
			$.ajax({
			  type: 'POST',
			  url: "lableajax_updorderfielddele.action",
			  data: {tableName:"lable_search",'lable.name':name,fieldName:divtitle},
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
							
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+field.name+"</td>")
		                   		.appendTo($('#ortr'+rcount));
		                   		ccount++;					 
			  });
			  for(var a=ccount;a<3;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#ortr'+rcount));
					
				}
				var ltype=$("#labletype").val();
										$.ajax({
			  type: 'POST',
			  url: "lableajax_getnewsql.action",
			  data: {tableName:"lable_search",'lable.name':name,'type':ltype},
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
			  url: "lableajax_updorderfielddele.action",
			  data: {tableName:"lable_search",'lable.name':name,fieldName:""},
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
							
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='or"+field.fieldName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+field.name+"</td>")
		                   		.appendTo($('#ortr'+rcount));
		                   		ccount++;					 
			  });
			  for(var a=ccount;a<3;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#ortr'+rcount));
					
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
  else if(type=="数字"||type=="实数"){
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
 
</script>		
		
	<div style="DISPLAY: none;" title="" id ="fielddiv"><table width="250">
	<caption id="fielddivtit"></caption>
	<tr><td>数据类型</td>
		<td><select name='' id='seletype'  class="textbox" style="WIDTH: 150px; " >
			<option value='0'>文本</option>
			<option value='1'>日期</option>
			<option value='2'>数字</option>
			  </select></td></tr>
	<tr>
	<td colspan="2"> 
	<table id="wenbenshezhi" style="display:none">
	<tr><td>截取长度</td><td><input type="text" id="textjiequ"/></td></tr>
	<tr><td>截断显示字符串</td><td><input type="text" value="..." id="textjiequstr"/></td></tr>
	<tr><td>是否过滤</td><td>
			<select name='' id='seleguolv'  class="textbox" style="WIDTH: 150px; " >
			<option value="0">不过滤html</option>
			<option value="1">过滤html代码</option>
			</select></td></tr>
	<tr><td>为空显示</td><td><input type="text" id="textnull"/></td></tr>
	</table>
	<table id="riqishezhi" style="display:none">
	<tr><td>日期格式</td><td><input type="text" id="textriqi"/></td></tr>
	<tr><td>为空设置</td><td><input type="text" value="" id="textriqinull"/></td></tr>
	</table>
	<table id="shuzishezhi" style="display:none">
	<tr><td>数字类型</td><td><input type="radio" name="shu" value="0" id="radioshuzi" checked="checked"/> 原数
<input type="radio" name="shu" value="1" /> 小数
<input type="radio" name="shu" value="2" /> 百分数
</td></tr>
	<tr id ="xiaoshuweishi" style="display:none"><td>小数位数</td><td><input type="text" value="2" id="testxiaoshu"/></td></tr>
	</table>
	</td>
	</tr>
	<tr><td><input type="button" value="插入" id="intolable"/></td><td><input type="button" value="取消" id="quxiao"/></td></tr>		  
	</table></div>
	<div style="DISPLAY: none;" title="" id ="deldiv"><img src="images/lable/close.gif"/></div>
	标签名称：<s:property value="searchLable.name" />
	<form action="lable_searchLableHTML.action" name ="myform" method="post">
			<s:hidden name="searchLable.name" id="lablename"></s:hidden>
			<s:hidden name="searchLable.type" id="labletype" value='2'></s:hidden>
			
			<table width="1060">
					<tr>
						<td valign="bottom">sql语句预览
							<s:textarea rows="10" cols="100" name="searchLable.sql"  id="sql" style="white-space: 10px;border-color: black;" theme="simple"></s:textarea>
						
						</td>
					</tr>
			</table>
			<table style="margin-top:10px" width="1060" border=0 cellpadding='2' cellspacing='1' class='border'>	
			<tr>
				<td>			
				<table width="100%" style="margin-top:0px">			
				<caption>可用字段</caption>
				</table>
				<table id="showfieldname" width="100%" style="margin-top:0px" >
				
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
					<td rowspan="2" width="200"><s:textarea rows="10" cols="80" name="searchLable.lable" id="circulationListLablelable" style="white-space: 10px;border-color: black;" theme="simple" onclick='setPos()' onkeyup='setPos()'></s:textarea></td>
					<td width="100px">当前排序状态状态:<span id="orderstatus"><s:property  value="searchLable.Ordername" /></span><br>
					<select name='order' id='order'  class="textbox" style="WIDTH: 100px;" >
			  <option value=''>无设置</option>			  
			  <option value='desc'>降序</option>
			  <option value='asc'>升序</option>
			  </select>	
			  <input type="button" id="orderset" value="确定设置"/>
					</td>
					<td rowspan="2" valign="top" width="250"> <table id="showorderfield" >
					
					
					</table></td>
				</tr>
				<tr>
					<td >
					选择排序字段<br>
					<select name='dbname1' id='dbname1'  class="textbox" style="WIDTH: 150px; " >
			  
			<option value="">选择排序字段</option>
			  </select>	
			   <input type="button" id="orderfield" value="加入字段"/>	
					</td>
				</tr>
			</table>
				
				</td>
		</tr>	
			
		<tr>
			<td align="left" >
			<s:submit value="完成创建"></s:submit>
			</td>
		</tr>	
		</table>
	</form>
	</body>
</HTML>
										   