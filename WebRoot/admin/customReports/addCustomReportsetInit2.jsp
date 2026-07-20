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
		<TITLE>学籍查询管理</TITLE>
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

function changedb(){
var dbname1=$('#dbname1').val();
 var checkText=$("#dbname1").find("option:selected").text();   
			
			$.ajax({
			  type: 'POST',
			  url: "lableajax_getFieldByTableName.action",
			  data: {tableName:dbname1},
			  async:true,//
			  success: function(data){
			  	var areaListObj = eval("("+data+")").jsonsField;
			  	$("#sfield").empty();
				$.each(areaListObj,function(i,field){
				if(i==0){
				$("<optgroup style='color:blue' label='====="+checkText+"====='><optgroup/>")
                   		.appendTo($("#sfield"));
					
				}
				
                   	$("<option ></option>").val(field.fieldName).text(field.name)
                   		.appendTo($("#sfield"));
				});
				
			  }
			});
			
}
$(function() {
$('#join').click(function(){
			var dbname1=$('#dbname1').val();
			var db = "<s:property value='customReport.tableinfo' />";
			if(dbname1==""){
				alert("请选择一个表");
			
			}else{
			
			//判断表中是否已经存在表名，存在返回
			if(db != undefined && db!=""&&db!=dbname1+"-"){
				alert("只能添加一个表,您已经添加!");
				return;
			}
			  var select=document.myform.sfield;
			  var fieldstr="";
				for(i=0;i<select.length;i++){
					if(document.myform.sfield[i].selected==true){
						fieldstr=fieldstr+dbname1+"."+document.myform.sfield[i].value+"-";
						
					}
			
				
				}
			}
			addfield(dbname1,fieldstr);
			
			
			
		});
		})
		
		function addfield(dbname1,fieldstr){
			var rcount=0;
			var ccount=0;
			var name=$('#lablename').val();
			$.ajax({
			  type: 'POST',
			  url: "fieldaddlable.action",
			  data: {tableName:"customreport",lableName:name,'customReport.tablefield':fieldstr,'customReport.tableinfo':dbname1},
			  async:true,//
			  success: function(data){
			  	var areaListObj = eval("("+data+")").jsonsnewField;
			  	/*构建查询表显示*/
			  	$("#showtablename").empty();
				$.each(areaListObj,function(i,table){
				
				if(i%5==0){
				rcount++;
				$("<tr  id='tr"+rcount+"'></tr>")
                   		.appendTo($("#showtablename"));
                   		ccount=0;

				}
				$("<td width='20%' id='"+table.tableName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+table.name+"</td>")
                   		.appendTo($('#tr'+rcount));
                   		ccount++;
				
				});
				for(var a=ccount;a<5;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#tr'+rcount));
					
				}
				/*构建查询字段显示*/
				$("#showfieldname").empty();
				rcount=0;
				ccount=0;
				$.each(areaListObj,function(i,table){
					var ftrid="";
					
					
					
					$("<tr ><td colspan='5' >已加入<font color='red'>"+table.name+"</font>的查询字段</td></tr>")
					.appendTo($("#showfieldname"));
					$.each(table.field,function(i,field){
							
							if(i%5==0){
							rcount++;
							ftrid=table.tableName+"-"+rcount
							$("<tr  id='ftr"+ftrid+"'></tr>")
							.appendTo($("#showfieldname"));
							
							ccount=0;
							}
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='"+field.tableName+"."+field.fieldName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+field.name+"</td>")
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

$(function(){
	var rcount=0;
			var ccount=0;
			var name=$('#lablename').val();
			$.ajax({
			  type: 'POST',
			  url: "fieldaddlable.action",
			  data: {tableName:"customreport",lableName:name,'customReport.tablefield':"",'customReport.tableinfo':""},
			  async:true,//
			  success: function(data){
			  	var areaListObj = eval("("+data+")").jsonsnewField;
			  	$("#showtablename").empty();
				$.each(areaListObj,function(i,table){
				
				if(i%5==0){
				rcount++;
				$("<tr  id='tr"+rcount+"'></tr>")
                   		.appendTo($("#showtablename"));
                   		ccount=0;

				}
				$("<td width='20%' id='"+table.tableName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+table.name+"</td>")
                   		.appendTo($('#tr'+rcount));
                   		ccount++;
				
				});
				for(var a=ccount;a<5;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#tr'+rcount));
					
				}
				//构建查询字段显示
				$("#showfieldname").empty();
				rcount=0;
				ccount=0;
				$.each(areaListObj,function(i,table){
					var ftrid="";
					
					
					
					$("<tr ><td colspan='5' >已加入<font color='red'>"+table.name+"</font>的查询字段</td></tr>")
					.appendTo($("#showfieldname"));
					$.each(table.field,function(i,field){
							
							if(i%5==0){
							rcount++;
							ftrid=table.tableName+"-"+rcount
							$("<tr  id='ftr"+ftrid+"'></tr>")
							.appendTo($("#showfieldname"));
							
							ccount=0;
							}
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='"+field.tableName+"."+field.fieldName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+field.name+"</td>")
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

function   onms(obj){

 var title=$(obj).attr("id");
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
			  url: "lableajax_tableFielddele.action",
			  data: {tableName:"customreport",lableName:name,delestr:divtitle},
			  async:true,//
			  success: function(data){
			  	var areaListObj = eval("("+data+")").jsonsnewField;
			  	/*构建查询表显示*/
			  	$("#showtablename").empty();
				$.each(areaListObj,function(i,table){
				
				if(i%5==0){
				rcount++;
				$("<tr  id='tr"+rcount+"'></tr>")
                   		.appendTo($("#showtablename"));
                   		ccount=0;

				}
				$("<td width='20%' id='"+table.tableName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+table.name+"</td>")
                   		.appendTo($('#tr'+rcount));
                   		ccount++;
				
				});
				for(var a=ccount;a<5;a++){
					$("<td width='20%'>&nbsp</td>")
                   			.appendTo($('#tr'+rcount));
					
				}
				/*构建查询字段显示*/
				$("#showfieldname").empty();
				rcount=0;
				ccount=0;
				$.each(areaListObj,function(i,table){
					var ftrid="";
					
					
					
					$("<tr ><td colspan='5' >已加入<font color='red'>"+table.name+"</font>的查询字段</td></tr>")
					.appendTo($("#showfieldname"));
					$.each(table.field,function(i,field){
							
							if(i%5==0){
							rcount++;
							ftrid=table.tableName+"-"+rcount
							$("<tr  id='ftr"+ftrid+"'></tr>")
							.appendTo($("#showfieldname"));
							
							ccount=0;
							}
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"'  id='"+field.tableName+"."+field.fieldName+"' onmousemove='onms(this)' onmouseout='mout(this)'>"+field.name+"</td>")
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



 
 });
}

) 


</script>		
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
		<li>
			<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				<span>首页</span>&nbsp;>>&nbsp;<span>系统管理</span>&nbsp;>>&nbsp;<span>自定义报表</span>&nbsp;>>&nbsp;<span>设置自定义报表</span>&nbsp;>>&nbsp;<span>步骤3</span>
			</div>
		</li>
	</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
	
	
	<div style="DISPLAY: none;" title="" id ="deldiv"><img src="images/lable/close.gif"/></div>
	<form action="" name ="myform">
	标签名称：<s:property value="customReport.name" />
				<table style="margin-top:10px" width="1060" border=0 cellpadding='2' cellspacing='1' class='border' >
			<tr class="tdbg">
			<td width="24%">
			<select name='dbname1' id='dbname1' onChange="changedb()" class="textbox" style="WIDTH: 250px;" >
			  <option value=''>请选择一个数据表</option>
			  <optgroup  label="============自定义表============">
 			  <s:iterator value="usertableList">
			  	<option value='<s:property value="tableName"/>' ><s:property value="name"/>(<s:property value="tableName"/>)</option>
			  </s:iterator>
			  </select>			</td>
			<td width="76%" rowspan="3" valign="top"  >
			<div id="show"  style="margin-top:0px">
			<table width="100%" style="margin-top:0px">
			
			<caption>已加入的查询表</caption>
			</table>
			<table id="showtablename" width="100%" style="margin-top:0px" >
			
			
			</table>
			<table id="showfieldname" width="100%" >
			</table>
			</div>			</td>
		</tr>
		<tr class="tdbg">
			<td>
				<Select class="textbox" style="WIDTH: 250px; HEIGHT: 210px"  multiple size=1 name="sfield" id="sfield">
				  </Select>			  </td>
			  </tr>
		<tr>
			<td>
				<input value="加入字段" id="join" name ="123" type="button" />			</td>
			</tr>	
		</table>
		</form>
		<form action="updateCustomReportById.action" name ="myform1" method="post">
			<s:hidden name="customReport.name" id="lablename"></s:hidden>
			<s:hidden name="customReport.id"></s:hidden>
		<table style="margin-top:10px" width="1060" border=0 cellpadding='2' cellspacing='1' class='border'>
		<tr>
			<td align="left">
			<table width="100%">
				<tr>
				<td>
				查询及关联条件
				</td>
				<td bordercolor="black">
				<s:textarea rows="5" cols="80" name="customReport.sqlcondition" id=""  style="white-space: 10px;border-color: black;" theme="simple"></s:textarea>
				</td>
				<td>
				查询条数：
					<input type="text"  name="customReport.pageSize"  value='<s:property value="customReport.pageSize"/>'/>
				</td>
				</tr>
			</table>
			
			</td>
		</tr>	
		<tr>
			<td align="left" >
				<table width="100%">
					<tr>
						<td align="right"><input type="button" value="上一步" onclick='history.go(-1);'/></td>
						<td width="100px"><input type="submit" value="下一步"/></td>
					</tr>
			  </table>
			</td>
		</tr>	
		</table>
	</form>
	</body>
</HTML>
										   