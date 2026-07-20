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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>添加客户</title>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<style type="text/css"> 
			td {font-size:12px;color:#333333;line-height:150%}
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#ffffff")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/makePY.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
  </HEAD>
  <%String message=(String)request.getAttribute("message"); %>
  
  
  <script type="text/javascript">
    
  var numofman = 0;
  
  
  function myload()
  {
   setCurTime("releasetime");
  }
  		
  	function setCurTime(oid){
				var now=new Date();
				var year=now.getYear();
				var month=now.getMonth()+1;
				var day=now.getDate();
				var hours=now.getHours();
				var minutes=now.getMinutes();
				if(minutes<10){
					minutes="0"+minutes;
				}
				var seconds=now.getSeconds();
				if(seconds<10){
					seconds="0"+seconds;
				}
				var timeString = year+"-"+month+"-"+day;//+" "+hours+":"+minutes+":"+seconds;
				//var oCtl = document.getElementById(oid);
				//oCtl.value = timeString;
				//setTimeout("setCurTime('"+oid+"')",1000);
				//alert(oid);
			}
  
  
  
         function message(){
              var message="<%=message %>";
              if(message!="null"&&message!=null&&message!=""){
                 alert(message);
                 document.location="lineTrainRecord_list.action";
              }
         }
         
         function doSubmit(){
         	if(document.getElementById("moduleManage.modulename").value==""){
         		alert("模块名称不能为空！");
         		return false;
         	}else{
         		if(isNaN(document.getElementById("moduleManage.modulename").value)==false){
         			alert("模块名不能全为数字!!!");
         			document.getElementById("moduleManage.modulename").value=="";
         			document.getElementById("moduleManage.modulename").focus();
         			return false;
         		}
         	}
         	if(document.getElementById("moduleManage.tablename").value==""){
         		alert("表名不能为空！");
         		return false;
         	}
         	return true;
         }

		function show_select(this_,number){
			if(number == 3){
				document.getElementById("select_module").style.display = "none";
			}else if(number == 2){
				document.getElementById("select_module").style.display = "block";
				if(this_.checked){
					document.getElementById("select_2").style.display = "block";
					document.getElementById("select_1").style.display = "none";
					document.getElementById("mm").style.display = "none";
					
				}
			}else if(number == 1){
				document.getElementById("select_module").style.display = "block";
				if(this_.checked){
					document.getElementById("select_2").style.display = "none";
					document.getElementById("select_1").style.display = "block";
					document.getElementById("mm").style.display = "none";
				}
			}
		}
		
		function getModule(number){
			width=600;	
		 	height=400;	
		 	var url = "getModule.action?rn="+Math.random();
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
		  	var rv = window.showModalDialog(url,null,sFeature);	
		  	if(rv != null && rv != "undefined"){
		  		if(rv.indexOf("==")>=0){
		  			var array = rv.split("==");
		  			if(number == 2 || number == 3){
		  				document.getElementById("moduleManage.relatetablename").value = array[1];
		  				document.getElementById("select_modulename").innerHTML = "您选择的模块为："+array[0];
		  				document.getElementById("mm").style.display = "block";
		  			}
		  		}
		  	}
		}
		
		function select_fromtablename(){
			width=600;	
		 	height=400;	
		 	var url = "getModule.action?rn="+Math.random();
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
		  	var rv = window.showModalDialog(url,null,sFeature);	
		  	if(rv != null && rv != "undefined"){
		  		if(rv.indexOf("==")>=0){
		  			var array = rv.split("==");
	  				document.getElementById("moduleManage.fromtablename").value = array[1];
	  				document.getElementById("fromtablename_chinese").innerHTML = "您选择的模块为："+array[0] + "(" + array[1] + ")";
		  		}
		  	}
		}
  </script>
  <body onLoad="myload();">
  		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加模块" /></div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
    	
		<!-- 内容 -->
		<s:form action="addModuleManage" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
		<div style="margin-top: 0px; text-align: center;">
				<table cellpadding="0" cellspacing="1" width="100%">
				<tbody onMouseOut="changeback()" onMouseOver="changeto()">
				<tr>
				<td width="14%" align="right">
				模块名称：				</td>
				<td width="85%">
					&nbsp;&nbsp;<s:textfield id="moduleManage.modulename" name="moduleManage.modulename" onKeyUp="query(this,document.getElementById('moduleManage.tablename'),0,'${tablename}');"></s:textfield>
				</td>
				</tr>
				<tr>
				<td align="right">
				 备注：				</td>
				<td>
					&nbsp;&nbsp;<s:textfield name="moduleManage.remark"></s:textfield>
				</td>
				</tr>
				<tr>
				<td align="right">
				表名：				</td>
				<td>
					&nbsp;&nbsp;<s:textfield name="moduleManage.tablename" id="moduleManage.tablename" ></s:textfield>
					<span style="color:red"><s:property value='elmessage'/></span>
				</td>
				</tr>
				<tr>
				<tr>
				<td align="right">
				数据名称：				</td>
				<td>
					&nbsp;&nbsp;<s:textfield name="moduleManage.shujuname" id="moduleManage.shujuname" ></s:textfield>
				</td>
				</tr>
				<tr>
				<td align="right">
				数据自动读取表：				</td>
				<td>
					&nbsp;&nbsp;<input type="text" name="moduleManage.fromtablename" id="moduleManage.fromtablename"  onclick="select_fromtablename();"/>
					<span style="color:blue">该设置用来数据自动读取</span>
					<span style='color:red' id='fromtablename_chinese'></span>
				</td>
				</tr>
				
				<tr>
				<td align="right">
				 自定义模板：				</td>
				<td>
					&nbsp;&nbsp;<input type="radio" name="moduleManage.ondemo" value=1 />启用 
					<input type="radio" name="moduleManage.ondemo" value=0 checked/>不启用
				</td>
				</tr>
				<!-- 
				<tr>
				<td>
				 是否能添加其他功能：
				</td>
				<td>
					<input type="radio" name="moduleManage.can_add_other_func" value=1 />启用 
					<input type="radio" name="moduleManage.can_add_other_func" value=0 checked/>不启用
				</td>
				</tr>
				 -->
				<tr>
					<td align="right">
					 表类型：					</td>
					<td>
						&nbsp;&nbsp;<input type="radio" name="moduleManage.tableType" value=1 onClick="show_select(this,1);"  checked/>业务表
						<input type="radio" name="moduleManage.tableType" value=2 onClick="show_select(this,2);"/>过程表
						<input type="radio" name="moduleManage.tableType" value=3 onClick="show_select(this,3);"/>结果表
						<span style='color:red'>如果业务表参与表间计算，请选择一个过程表;如果过程表，请选择结果表!!!</span>
					</td>
				</tr>
				<tr id="select_module" style="display:block;">
					<td align="right">
						<DIV style="display:block;" id="select_1">
					 		&nbsp;&nbsp;<span style='color:blue'>选择对应的过程表</span><input type="button" value="选择" onClick="getModule(2);" class="textbg4"/>
						</DIV>
					 	<DIV style="display:none;" id="select_2">
					 		<span style='color:blue'>选择对应的结果表</span><input type="button" value="选择" onClick="getModule(3);" class="textbg4"/>
					 	</DIV>
				  </td>
					<td style="display:none;" id='mm'>
						<input type="text" name="moduleManage.relatetablename" id="moduleManage.relatetablename"/>
					 	<span id="select_modulename" style="color:blue"></span>
					</td>
					<td width="1%"></td>
				</tr>
				</tbody>
  </table>
				
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认添加" class=textbg6 />
		</div>
		</s:form>
  </body>
</html>