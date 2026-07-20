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
		var ax={lableName:value,tableName:"lable_search"}
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

		var value=$("#input1").val();
		if(value!=""){
		var ax={lableName:value,tableName:"lable_search"}
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

	<form action="lable_searchLableNameAdd.action" name="clable" method="post" >

		标签名称：<s:textfield   id= "input1" name="searchLable.name"/> <span  id="ms">(请设置一个唯一的名称) </span>
		
		<input value="下一步"  id="nextb" name ="123" type="button"  />
	</form>
	

	
	
	</body>
</HTML>
										   