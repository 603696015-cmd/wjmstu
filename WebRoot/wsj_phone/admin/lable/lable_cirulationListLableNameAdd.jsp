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

		<script type="text/javascript">
		/*$("input").keyup(function(){
		alert("sss");
		var value=$("input").val();
		var ax={lablename:value}
			$("#ms").load(
					"getShoppingCarCount.action",ax
				);

		});*/
/*		$('#input').focus(function(){
     
		$(this).bind("keyup", function(){
 			alert(2);
                 })
 });*/

		/*$("#next").click(function(){
		if($("#ms").val=="名字已存在请重新输入"){
			alert("标签名不合法");
			$("input").focus();
			
		}
		});*/
		</script>
	</HEAD>
	<body>
<script type="text/javascript">
$(
	function (){
		var value=$("#input1").val();
		if(value!=""){
		var ax={lableName:value,tableName:"lable_circulation"}
			$("#ms").load(
					"lableajax_checklablename.action",ax
				);
				
		}
		else{
			$("#ms").html("(请设置一个唯一的名称)");
		}

})
$(function() {

$("#input1").keyup(function (){
$("#789").empty();
		var value=$("#input1").val();
		if(value!=""){
		var ax={lableName:value,tableName:"lable_circulation"}
			$("#ms").load(
					"lableajax_checklablename.action",ax
				);
				
		}
		else{
			$("#ms").html("(请设置一个唯一的名称)");
			
		}

		});
		
	$("#nextb").click(function(){
		var value=$("#ms").text();		
		
		if(value=="该名称可以使用"){	
			clable.submit();
			
		}
		else{
			alert("标签名不合法");
			$("#input1").focus();
		}
		})	
 

})


</script>

	<form action="lable_cirulationListLableNameAdd.action" name="clable" method="post" >
	
	
	<s:hidden name="type" />

		标签名称：<s:textfield   id= "input1" name="circulationListLable.name"/> <span  id="ms">(请设置一个唯一的名称) </span>
		
		<input value="下一步"  id="nextb" name ="123" type="button"  />
	</form>
			
<!-- 	<table width="100%" align="center" cellpadding="2"
								cellspacing="2" bgcolor="#EBEBEB">
								<tr>
								  
									<th width="30" height="30" align="center" >
										编号									</th>
									<th  height="30" align="center" >
										图书名称									</th>
									<th width="90" height="30" align="center" >
										作者									</th>
									<th width="60" height="30" align="center" >
										图书类型名称								</th>
									<th width="70" height="30" align="center" >
										图书修改时间									</th>
									<th width="70" height="30" align="center" >
										图书出版时间								</th>
										<th width="150" height="30" align="center" >
										图书简介							</th>
										</tr>
										<zdyLib:zdyloop lablename='列表标签1'  xunhuan='' setnull='暂无数据'></zdyLib:zdyloop>
										</table>
										 -->
<!-- <table  width=800px ><tr><td >
		
	<zdyLib:zdypage lablename='分页06' setnull="暂无数据" ></zdyLib:zdypage>
	
	</td>
	<td><zdyLib:zdypage lablename='分页06' setnull="暂无数据" ></zdyLib:zdypage>
	</td>
	<td><zdyLib:zdypage lablename='分页06' setnull="暂无数据" ></zdyLib:zdypage>
	</td>
	</tr></table>
	 -->	
	
	</body>
</HTML>
										   