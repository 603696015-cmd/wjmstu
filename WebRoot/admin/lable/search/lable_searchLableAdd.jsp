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
			if(dbname1==""){
				alert("请选择一个表");
			
			}else{
			
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
			  url: "lableajax_Fieldaddlable.action",
			  data: {tableName:"lable_search",lableName:name,'lable.fieldstr':fieldstr,'lable.tablestr':dbname1},
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
			  url: "lableajax_Fieldaddlable.action",
			  data: {tableName:"lable_search",lableName:name,'lable.fieldstr':"",'lable.tablestr':""},
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
			  data: {tableName:"lable_search",lableName:name,delestr:divtitle},
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


		<div style="DISPLAY: none;" title="" id="deldiv">
			<img src="images/lable/close.gif" />
		</div>
		<form action="" name="myform">
			<s:hidden name="searchLable.type"></s:hidden>
			标签名称：
			<s:property value="searchLable.name" />
			<table style="margin-top: 10px" width="1060" border=0 cellpadding='2'
				cellspacing='1' class='border'>
				<tr class="tdbg">
					<td width="24%">
						<select name='dbname1' id='dbname1' onChange="changedb()"
							class="textbox" style="WIDTH: 250px;">
							<option value=''>
								请选择一个数据表
							</option>
							<optgroup style="color: blue;"
								label="=============系统表=============">
								<s:iterator value="systableList">
									<option value='<s:property value="tableName"/>'>
										<s:property value="name" />
										(
										<s:property value="tableName" />
										)
									</option>
								</s:iterator>
								<optgroup label="============自定义表============">
									<s:iterator value="usertableList">
										<option value='<s:property value="tableName"/>'>
											<s:property value="name" />
											(
											<s:property value="tableName" />
											)
										</option>
									</s:iterator>
						</select>
					</td>
					<td width="76%" rowspan="3" valign="top">
						<div id="show" style="margin-top: 0px">
							<table width="100%" style="margin-top: 0px">

								<caption>
									已加入的查询表
								</caption>
							</table>
							<table id="showtablename" width="100%" style="margin-top: 0px">


							</table>
							<table id="showfieldname" width="100%">
							</table>
						</div>
					</td>
				</tr>
				<tr class="tdbg">
					<td>
						<Select class="textbox" style="WIDTH: 250px; HEIGHT: 210px"
							multiple size=1 name="sfield" id="sfield">
						</Select>
					</td>
				</tr>
				<tr>
					<td>
						<input value="加入字段" id="join" name="123" type="button" />
					</td>
				</tr>
			</table>
		</form>
		<form action="lable_searchLablesearchAdd.action" name="myform1"
			method="post">
			<s:hidden name="searchLable.name" id="lablename"></s:hidden>
			<table style="margin-top: 10px" width="1060" border=0 cellpadding='2'
				cellspacing='1' class='border'>
				<tr>
					<td align="left">
						<table width="100%">
							<tr>
								<td>
									查询及关联条件
								</td>
								<td bordercolor="black">
									<s:textarea rows="5" cols="80" name="searchLable.sqlCondition"
										id="" style="white-space: 10px;border-color: black;"
										theme="simple"></s:textarea>
								</td>
								<td>
									一页条数：
									<s:if test="searchLable.pageSize!=0">
										<input type="text" name="searchLable.pageSize"
											value='<s:property value="searchLable.pageSize"/>' />
									</s:if>
									<s:else>
										<input type="text" name="searchLable.pageSize" value='10' />
									</s:else>
								</td>
								<td>
									查询模式
									<s:if test="searchLable.type==1">
										<input type="radio" name="searchLable.type" value="1"
											id="radioshuzi" checked="checked" /> 精确查询
										<input type="radio" name="searchLable.type" value="2" /> 模糊查询
									</s:if>
														<s:else>
															<input type="radio" name="searchLable.type" value="1"
																id="radioshuzi" /> 精确查询
										<input type="radio" name="searchLable.type" value="2"
																checked="checked" /> 模糊查询
									</s:else>
								</td>
							</tr>
							<tr>
								<td colspan=4>
									<label>
										<wysLib:lableTree_list_aj rootAble="true"
											iname="klTree.parent.id" itype="ra"></wysLib:lableTree_list_aj>
										<script type="text/javascript">
											w0.setValues([new DEP(<s:property value="searchLable.lableTree.id"/>,<s:property value="searchLable.lableTree.lid"/>,<s:property value="searchLable.lableTree.rid"/>)]);
										</script>
									</label>
								</td>
							</tr>
							<tr>
								<td>关键词：</td>
								<td colspan=2 >
									<label>
										<input type="text" name="searchLable.keyword"
											value='<s:property value="searchLable.keyword"/>' />
									</label>
								</td>
								<td>
									<span style="color:red">设置多个关键词的时候请以空格分开!!!</span>
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td align="left">
						<s:submit value="下一步"></s:submit>
					</td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
