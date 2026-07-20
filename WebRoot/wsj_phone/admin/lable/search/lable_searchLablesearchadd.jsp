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
			  url: "lableajax_getallsearchfield.action",
			  data: {tableName:"lable_search",lableName:name},
			  async:true,//
			  success: function(data){
			  	var areaListObj = eval("("+data+")").jsonsorderField;
			  	
				/*构建查询字段显示*/
				$("#showfieldname").empty();
				$.each(areaListObj,function(i,table){
					var ftrid="";			
					$("<tr ><td colspan='5' ><font color='red'>"+table.name+"</font>的字段</td></tr>")
					.appendTo($("#showfieldname"));

					$.each(table.field,function(i,field){
							
							if(i%5==0){
							rcount++;
							ftrid=table.tableName+"-"+rcount
							$("<tr  id='ftr"+ftrid+"'></tr>")
							.appendTo($("#showfieldname"));
							
							ccount=0;
							}
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"' name='"+field.fieldType+"' id='"+field.value+"' onclick='addlable(this)' >"+field.name+"</td>")
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
)


var falg=0;
 var xialavalue="";
 function addlable(objf){
   if(falg==0){
 if(pos==null) {alert('请先定位插入位置!');return false;}
 else{
 
 var offset =$(objf).offset(); 
var type=$(objf).attr("name");

var val=$(objf).attr("title");
 if(type=="下拉选项"){
 xialavalue=$(objf).attr("id");
 
  $("#fielddiv").attr("title",val);
   $("#fielddiv").css({ position:"absolute" ,left:offset.left,top:offset.top, display:"block" });
   falg=1;
 }
 else if(type=="日期"){
  xialavalue=$(objf).attr("id");
 $("#sealddiv").attr("title",val);
  $("#sealddiv").css({ position:"absolute" ,left:offset.left,top:offset.top, display:"block" });
  falg=1;
 }
 else{
 var lablename=$("#lablename").val();
 var strlable="<input  id='@lable"+lablename+""+val+"lable@' />"
  if (document.all){
			  pos.text=strlable;
			 }else{
			   var obj=$("#LabelContent");
			   var lstr=obj.val().substring(0,pos);
			   var rstr=obj.val().substring(pos);
			   obj.val(lstr+strlable+rstr);
			 }
 
 }
			
		
 
 }

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
		
 $(
 function(){
 $('#addsearch').click(function(){

 
 var lablename=$("#lablename").val();
 var val=$("input[name='ss'][checked]").val();

 var str=$("#fielddiv").attr("title");
 var strlable="";
 var arr=xialavalue.split("==");
 if(val==1){
 strlable="<select id='@lable"+lablename+""+str+"^select^selecttypelable@'  style='WIDTH: 100px;' ><option value=''>不限</option>"
	
	for(var i =0;i<arr.length;i++){
		strlable+="<option value='"+arr[i]+"'>"+arr[i]+"</option>";
	}
	strlable+="</select>";
 }
 else if(val==2){
 strlable="<input type='radio' name='@lable"+lablename+""+str+"^radio^radiotypelable@' value=''>不限 "
 
 for(var i =0;i<arr.length;i++){
		strlable+="<input type='radio' name='@lable"+lablename+""+str+"^radio^radiotypelable@' value='"+arr[i]+"'>"+arr[i]+" ";
	}
 
 }
 
  if (document.all){
			  pos.text=strlable;
			 }else{
			   var obj=$("#LabelContent");
			   var lstr=obj.val().substring(0,pos);
			   var rstr=obj.val().substring(pos);
			   obj.val(lstr+strlable+rstr);
			 }
			 
  $("#fielddiv").css({ position:"absolute", display:"none" });
 
  falg=0;
 
 })
 $('#quxiao').click(function(){
 
 $("#fielddiv").css({ position:"absolute", display:"none" });
  falg=0;
 });
 $('#sjaddsearch').click(function(){

 
 var lablename=$("#lablename").val();
 var val=$("input[name='sj'][checked]").val();

 var str=$("#sealddiv").attr("title");
 var strlable="";
 if(val==1){
 
 strlable="@lable"+lablename+""+str+"^time^timetypesstartslable@";
 

	
 }
 else if(val==2){
 strlable="@lable"+lablename+""+str+"^time^timetypesendslable@";
 

 
 }
 else if(val==3){
 strlable="@lable"+lablename+""+str+"^time^timetypesdengyuslable@";
 }
  
strlable= "<input id='"+strlable+"'  onclick='setday(this)' readonly='readonly'/>";
  if (document.all){
			  pos.text=strlable;
			 }else{
			   var obj=$("#LabelContent");
			   var lstr=obj.val().substring(0,pos);
			   var rstr=obj.val().substring(pos);
			   obj.val(lstr+strlable+rstr);
			 }
			 
  $("#sealddiv").css({ position:"absolute", display:"none" });
 
  falg=0;
 
 })
 
  $('#sjquxiao').click(function(){
 
 $("#sealddiv").css({ position:"absolute", display:"none" });
  falg=0;
 });
 
 
 }
 
 
 )
</script>		
		
	<div style="DISPLAY: none;" title="" id ="fielddiv"><table width="300">
	<tr><td>选择该字段的搜索样式</td>

	<td><input type="radio" name='ss' value="1" id="radioshuzi" checked="checked"/> 下拉选项
		<input type="radio" name='ss' value="2" /> 单选按钮</td>
	</tr>
	<tr><td><input type="button" id='addsearch' value='确定'></td>
	<td><input type="button" id='quxiao' value='取消'></td>
	</tr>
	
	</table>
	
	</div>
	<div style="DISPLAY: none;" title="" id ="sealddiv"><table width="250">
	<tr><td>选择时间类型的搜索模式</td>

	<td><input type="radio" name='sj' value="1" id="radioshuzi" checked="checked"/> 起始时间
		<input type="radio" name='sj' value="2" /> 结束时间
		<input type="radio" name='sj' value="3" /> 等于该时间</td>
	</tr>
	<tr><td><input type="button" id='sjaddsearch' value='确定'></td>
	<td><input type="button" id='sjquxiao' value='取消'></td>
	</tr>
	
	</table>
	
	</div>
	<div style="DISPLAY: none;" title="" id ="deldiv"><img src="images/lable/close.gif"/></div>

	标签名称：<s:property value="searchLable.name" />
	<form action="lable_searchLablesearchsetAdd.action" name ="myform" method="post">
			<s:hidden name="searchLable.name" id="lablename"></s:hidden>

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
				搜索编辑框		 
			</td>
		</tr>
		<tr>
			<td bordercolor="black">
			<table width="100%">
				<tr>
					<td rowspan="2" width="200"><s:textarea rows="20" cols="80" name="searchLable.searchlable" id="circulationListLablelable" style="white-space: 10px;border-color: black;" theme="simple" onclick='setPos()' onkeyup='setPos()'></s:textarea></td>
					
				</tr>
			</table>
				
				</td>
		</tr>	
			
		<tr>
			<td align="left" >
			<s:submit value="下一步"></s:submit>
			</td>
		</tr>	
		</table>
	</form>
	
	</body>
</HTML>
										   