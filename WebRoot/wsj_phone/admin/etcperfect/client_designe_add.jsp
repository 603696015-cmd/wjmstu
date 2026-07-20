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

	if(obj.options[7].selected)//图片 
	{
		document.getElementById("divpic").style.visibility="hidden";
	}
	else
	{
		document.getElementById("divpic").style.visibility="";
	}

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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="客户自定义添加" /></div>
			</li>
		
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
    	
		<!-- 内容 -->
		<s:form action="clientDesigneAdd" method="post" name="lineTrainRecord_add"
				theme="simple" id="linetrainrecord" onsubmit="return doSubmit();" enctype="multipart/form-data">
		<div style="margin-top: 0px; text-align: center;">
				<table cellpadding="1" cellspacing="1" width="700">
					<tr>
						<td width="160" height="30" align="center" >
							自定义项目名称：<span  style="color:red">*</span>
						</td>
						<td >
							<label>
								<s:textfield name="tags.name_display"  id="name_display" size="40"></s:textfield>
							</label>
						</td>
					</tr>
					<tr>
						<td width="160" height="30" align="center" >
							自定义项目类型：<span  style="color:red">*</span>
						</td>
						<td width="600">
						
								<s:select  name="tags.display_type" onchange="showselect(this);"  id="selectshow"  
								list="{'实数','日期','大文本','整数','下拉选项','附件上传','图片'}"  
								theme="simple" headerKey="文本" headerValue="文本"  />  
							<!--	<select name="tags.display_type" onchange="showselect(this);"  id="selectshow" >
								<option value="实数">实数</option>
								<option value="下拉选项">下拉选项</option>
								</select>-->
								<div id="selectvalue" style="visibility:hidden">
								<input type="text" width="600" name="tags.default_value"  id="select_default_value" ></input>
								<span  style="color:red">每个选项以"=="隔开</span>
								</div>
						</td>
					</tr>
					<!--  
					<tr id="selectshow" style="visibility:hidden" >
					<td></td>
					<td>
					ff
					</td>
					</tr>
					-->
					<tr>
						<td width="160" height="30" align="center" >
							该项目出现在：
						</td>
						<td >
							<input type="checkbox" name="display" value ="add_display" checked>添加页面是否显示
							<input type="checkbox" name="display" value ="update_display" checked>修改页面是否显示
							<input type="checkbox" name="display" value ="view_display" checked>查看页面是否显示<br>
							<div id="divpic">
								<input type="checkbox" name="display" value ="list_display"  checked>列表页面是否显示
								<input type="checkbox" name="display" value ="mutilsearch_display" checked>组合查询是否显示
								<input type="checkbox" name="display" value ="departsearch_display" checked>部门查询是否显示
							</div>
						</td>
					</tr>
					<!-- 
					<tr>
					<td  align="center" >显示序号：</td>
					<td>
						<s:textfield name="tags.sn"></s:textfield>
					</td>
					</tr>
					 -->
					<tr>
						<td colspan="2"  align="center" >
						<span  style="color:red">注意：新增加的字段只能隐藏不能删除，请慎重添加。</span>
						</td>
					</tr>
	                
					<s:hidden name="tags.table_name" value="tb_client_tags" ></s:hidden>
					
				</table>
				
		</div>
		
		<div style="margin-top: 0px; text-align: center;">
			<input name="submit" type="submit" value="确认添加" class=textbg6 />
		</div>
		</s:form>
  
	</body>
</html>