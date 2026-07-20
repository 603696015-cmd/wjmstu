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
  </HEAD>
  <%String message=(String)request.getAttribute("message"); %>
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
  
  
         
         function doSubmit()
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
  </script>
  <body >
  		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="客户自定义管理" /></div>
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
				<table cellpadding="1" cellspacing="1" width="700">
					<wysLib:designemanage />
				</table>
				
		</div>
		
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认修改" class=textbg6 />
		</div>
		</s:form>
  </body>
</html>