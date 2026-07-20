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
			tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
		</style>
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
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
				var oCtl = document.getElementById(oid);
				oCtl.value = timeString;
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
         	if($("#name").val()==""){
         		alert("公司名称不能为空！");
         		return false;
         	}
         	if($("#tel").val()==""){
         		alert("公司号码不能为空！");
         		return false;
         	}
         	if($("#name_"+numofman).val()==""){
         		alert("联系人姓名不能为空！");
         		return false;
         	}
         	
         	return true;
         	//return false;
         }

		function getModule(){
			width=600;	
		 	height=400;	
		 	var url = "getModule.action?rn="+Math.random();
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
		  	var rv = window.showModalDialog(url,null,sFeature);	
		  	if(rv != null && rv != "undefined"){
		  		if(rv.indexOf("==")>=0){
		  			var array = rv.split("==");
	  				document.getElementById("moduleManage.relatetablename").value = array[1];
	  				document.getElementById("relatetablename_chinese").innerHTML = "您选择的模块为："+array[0];
		  		}
		  	}
		}
		
		function getModule1(){
			width=600;	
		 	height=400;	
		 	var url = "getModule.action?rn="+Math.random();
		  	var sFeature="dialogWidth:"+width+"px;dialogHeight:"+height+"px;Status:0;resizable:1;help:0";	
		  	var rv = window.showModalDialog(url,null,sFeature);	
		  	if(rv != null && rv != "undefined"){
		  		if(rv.indexOf("==")>=0){
		  			var array = rv.split("==");
	  				document.getElementById("moduleManage.fromtablename").value = array[1];
	  				document.getElementById("moduleManage.fromtablename_chinese").innerHTML = "您选择的模块为："+array[0];
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加客户" /></div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
    	
		<!-- 内容 -->
		<s:form action="updateModuleManage" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
		<div style="margin-top: 0px; text-align: center;">
				<table cellpadding="1" cellspacing="1" width="850">
				
				<tr>
				<td>
				模块名称：
				</td>
				<td>
					<s:textfield name="moduleManage.modulename"></s:textfield>
				</td>
				</tr>
				<tr>
				<td>
				 备注：
				</td>
				<td>
					<s:textfield name="moduleManage.remark"></s:textfield>
				</td>
				</tr>
				<tr>
				<td>
				表名：
				</td>
				<td>
					<span style="color:red"><s:property value='moduleManage.tablename'/></span>
				</td>
				</tr>
				<tr>
				<td>
				数据名称：
				</td>
				<td>
					<s:textfield name="moduleManage.shujuname" id="moduleManage.shujuname" ></s:textfield>
				</td>
				</tr>
				<tr>
				<td>
				数据自动读取表：
				</td>
				<td>
					<input type="text" id='moduleManage.fromtablename' name="moduleManage.fromtablename" value="<s:property value='moduleManage.fromtablename'/>" onclick="getModule1();"/>
				</td>
				</tr>
				<tr>
				<td>
				 自定义模板：
				</td>
				<td>
					<input type="radio" name="moduleManage.ondemo" <s:if test="moduleManage.ondemo==1">checked=true</s:if> value=1 />启用 
					<input type="radio" name="moduleManage.ondemo" <s:if test="moduleManage.ondemo==0">checked=true</s:if> value=0 />不启用
				</td>
				</tr>
				<!-- 
				<tr>
				<td>
				 是否能添加其他功能：
				</td>
				<td>
					<input type="radio" name="moduleManage.can_add_other_func" value=1 <s:if test='moduleManage.can_add_other_func==1'>checked</s:if>/>启用 
					<input type="radio" name="moduleManage.can_add_other_func" value=0 <s:if test='moduleManage.can_add_other_func==0'>checked</s:if>/>不启用
				</td>
				</tr>
				 -->
				<tr>
					<td>
					 表类型：
					</td>
					<td>
						<span style="color:red">
						<s:if test="moduleManage.tableType == 1">
							业务表
						</s:if>
						<s:if test="moduleManage.tableType == 2">
							过程表
						</s:if>
						<s:if test="moduleManage.tableType == 3">
							结果表
						</s:if>
						</span>
					</td>
				</tr>
				<tr>
				<td>
					<span style="color:red">
					<s:if test="moduleManage.tableType == 1">
						<span style="color:red">修改过程表</span>
						<input type="button" value="选择" class="textbg4" onclick="getModule();"/>
					</s:if>
					<s:if test="moduleManage.tableType == 2">
						<span style="color:red">修改结果表</span>
						<input type="button" value="选择" class="textbg4" onclick="getModule();"/>
					</s:if>
					<s:if test="moduleManage.tableType == 3">
					</s:if>
					</span>
				</td>
				<td>
					<input readOnly type = "text" value='<s:property value='moduleManage.relatetablename'/>' name="moduleManage.relatetablename" id="moduleManage.relatetablename"/>
					<span style='color:blue' id="relatetablename_chinese"></span>
				</td>
				</tr>
				</table>
				<s:hidden name="moduleManage.id" />
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认修改" class=textbg6 />
		</div>
		</s:form>
  
	</body>
</html>