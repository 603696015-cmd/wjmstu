<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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
		<META http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加日志</title>
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
		<script type="text/javascript" src="js/cexampaper.js"></script>
  </HEAD>
  <script type="text/javascript">
  
  function showselect(obj)
  {  
	  	 var returnValue; 
	//  for(iIndex=0;iIndex<obj.length;iIndex++)  
	 // {   alert((options[iIndex]).value);
	   //   if(obj.options[iIndex].selected){  
	    //      returnValue=obj.options[iIndex].value;  
	     // }  
	  //}
	  
	  if(obj.options[5].selected)  
	  {
	  	document.getElementById("selectvalue").style.visibility="";
	  	}
	  else  
	  	document.getElementById("selectvalue").style.visibility="hidden";

  }
  
  
         
         function doSubmit1()
         {
         	if($("#name_display").val()=="")
         	{
         		alert("自定义项目名称不能为空！");
         		return false;
         	}
         	if(document.getElementById("selectshow").options[5].selected)
         	{ 
         		//if(document.getElementById("select_default_value").val=="")
         		if($("#select_default_value").val()=="")
         		{
         			alert("下拉选项不能为空！！！");
         			return false;
         			}
         }
         	return true;
         }
         
         function addcolumn(table)
         {
         	//alert(table);
         	window.location.href="contactlinkmanDesigneAddInit.action?tablename="+table;
         	//document.location.href="./admin/etcperfect/contactlinkman_designe_add.jsp";
         }
         
         function load_(){
         	if("${elmessage}" != ""){
         		alert("${elmessage}");
         	}
         
         	var tablename = "<s:property value='tablename'/>";
         	var tablename_chinese = getModuleNameByTablename(tablename);
         	document.getElementById("caption").innerHTML = tablename_chinese + "(" + tablename + ")";
         }
         
         function getModuleNameByTablename(tablename){
				var returnValue = "";
				$.ajax({
					  type: 'POST',
					  url: "getModuleNameByTablename.action",
					  data: {tablename:tablename},
					  async:false,//同步
					  success: function(data){
				  		data = eval("("+data+")").check_json_result;
				  		if(data != "")
				  			returnValue = data;
					  }
				});
				return returnValue;
			}
			
			function check_user(this_){
				if(this_.checked){
					this_.checked = true;
					this_.value = 1
				}else {
					this_.checked = false;
					this_.value = 0;
				}
			}
  </script>
  <body onload="load_();">
  		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="添加日志" /></div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
    	
		<!-- 内容 -->
		<s:form action="designeContactTags" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
		<div style="margin-top: 0px; text-align: center;">
				
					<wysLib:designemanage />
					
				
		</div>
		
		<div style="margin-top: 0px; text-align: center;">
			<input type="hidden" name="tablename" value="<s:property value="tablename"/>" />
			
			<input name="submit" type="submit" value="确认修改" class=textbg6 />
				
			<input onclick="addcolumn('<s:property value="tablename"/>');" type="button" value="添加字段" class=textbg6 />
		</div>
		</s:form>
		
  
	</body>
</html>