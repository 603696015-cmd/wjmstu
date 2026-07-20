<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="zdyLib" uri="/WEB-INF/zdyLib.tld"%>

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
		<SCRIPT type="text/javascript" src="js/zidingyipage.js" ></script>
				<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body onload="">
<script type="text/javascript">
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
							$("<td width='20%' title='"+field.tableName+"."+field.fieldName+"' name='"+field.fieldType+"' id='fe"+field.tableName+"."+field.fieldName+"' onclick='addlable(this)' ><span  style='cursor:hand' >"+field.name+"</span></td>")
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

}
)

function loadsearchtype(){
	var searchtype = parseInt('${customReport.searchtype}');
	var searchtypes = document.getElementsByName("customReport.searchtype");
	for(var i=0;i<searchtypes.length;i++){
		if(searchtype == searchtypes[i].value){
			searchtypes[i].checked = "checked";
		}
	}
}

//统计信息
function load(){
	loadsearchtype();
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
			  		$("<td id="+jsz.columnname+"  title="+formula+" onclick='addlable(this);'><input type='hidden' id='"+jsz.columnname+"formatnumber' value="+formatnumber+" /><input type='hidden' id='"+jsz.columnname+"viewjindutiao' value="+viewjindutiao+" /><span  style='cursor:hand' >"+jsz.columnname+"</span></td>").appendTo($('#tongjiname'+rcount));
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



//设置搜索标签
var number=0;
var lable = "@searchlable#";
var lable_="#searchlable^";
var html = "";
function addlable(obj){
	var title = $(obj).attr("title");
	
	if(pos==null) {alert('请先定位插入位置!');return false;}
 	else{
 		//普通字段判断是否有分组
 		if(title.indexOf(".")!=-1){
 			if(getLableById(parseInt($("#lableid").val()))!=""){
	 			var groupby = '${customReport.groupby_}';
	 			if(groupby.indexOf(title)==-1){
	 				alert("您选择的字段不是分组字段,请重新选择!!!");
	 				return;
	 			}
	 		}
 		}
 		
 		pos.text = lable+$(obj).attr("title")+lable_;
 	}
}

var pos=null;
function setPos(){ 
  if (document.all){
	$("#searchhtml").focus();
    pos = document.selection.createRange();
  }else{
    pos = document.getElementById("searchhtml").selectionStart;
  }
}


function donext(){
	clable.submit();
}
</script>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
		<li>
			<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
				<span>首页</span>&nbsp;>>&nbsp;<span>系统管理</span>&nbsp;>>&nbsp;<span>自定义报表</span>&nbsp;>>&nbsp;<span>设置自定义报表</span>&nbsp;>>&nbsp;<span>步骤5</span>
			</div>
		</li>
	</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
	
	<form action="setover.action" name="clable" method="post" >
	标签名称：<s:property value="customReport.name" /><br>
		<s:hidden name="customReport.id" id="lableid"/>
		<s:hidden name="customReport.name" id="lablename"></s:hidden>
		是否显示上搜索：<input type="checkbox" name="customReport.showsearch" 
			<s:if test="customReport.showsearch==1">checked=true</s:if> value="1" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		设置搜索方式：<input type='radio' name='customReport.searchtype' value=1 />精确查询&nbsp;&nbsp;<input type='radio' name='customReport.searchtype' value=2 />模糊查询
	
	<!-- 设置搜索 -->
	<div style="display:block;">
		<table style="width:1000px;">
			<tr>
				<td style="width:50%">
					<table width="100%" style="margin-top:0px">
						<tr>
							<td>			
								<table width="100%" style="margin-top:0px">			
									<caption>可用字段</caption>
								</table>
								<table id="showfieldname" width="100%" style="margin-top:0px" >
								
								</table>
								
								<span style="color:red;" >统计</span><span>的可用字段</span>
								<table id="showtongjiname" width="100%" style="margin-top:0px" >
									
								</table>
							</td>
						</tr>	
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div>
		<table width="1060">
			<tr>
				<td valign="bottom">搜索标签编辑框
					<s:textarea onclick='setPos()' rows="20"  cols="100" name="customReport.searchhtml"   id="searchhtml" style="white-space: 10px;border-color: black;" theme="simple" ></s:textarea>
				</td>
			</tr>
			<tr>
			<td align="left" >
				<table width="100%">
					<tr>
						<td align="right"><input type="button" value="上一步" onclick='history.go(-1);'/></td>
						<td width="100px"><input value="创建完成"  id="nextb" name ="123" type="button"  onclick="donext();"/></td>
					</tr>
			  </table>
			</td>
		</tr>	
		</table>
	</div>
	</form>
	
	</body>
</HTML>
										   