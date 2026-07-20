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
		<TITLE>模板管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.validate.js"></script>
	</HEAD>
	<BODY>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<span style="font-weight: bold;">首页绑定模板</span>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		

		<!-- 内容 -->
		<script type="text/javascript">
 				$(function(){
 					$("#modelType").change(function(){
 						if($("#modelType").attr("selected","selected").val()=='XW'){ 
 							$("#modelId").html($("#newsTypeList").val());
 						}else if($("#modelType").attr("selected","selected").val()=='ZS'){
 							 $("#modelId").html($("#knowledgeTypeList").val());
 						}else if($("#modelType").attr("selected","selected").val()=='KC'){
 							 $("#modelId").html($("#courseTypeList").val());
 						}else if($("#modelType").attr("selected","selected").val()=='LT'){
 							 $("#modelId").html($("#forumBlockTypeList").val());
 						}
 					});
 				});
				function close(){
					document.getElementById('messUserF').style.display='none';
				} 
			</script>
		<div
			style="position: absolute; width: 400px; height: 300px; left: 600px; top: 100px; border: solid 1px buttonface; background: white; display: none;"
			id="messUserF">
			<div style="width: 400px; text-align: right;">
				<a href="javascript:close()">关闭</a>
			</div>
			<div id="messUser"></div>
		</div>
		<div style="margin-top: 40px; text-align: left; margin-left: 20px;">
			<form name="message_management" id="message_management"
				action="column_bindTemp.action" method="post">
				<input type="hidden" name="columnTmp.columnType" value="index" />  
				<input type="hidden" name="columnTmp.columnName" value="0-=lwh=-首页" />  
				 首页
				<label>
					模板
					 <select id="" name="columnTmp.tmpName">
					 	<s:iterator value="templateList">
					 		<option value="<s:property value="id"/>-=lwh=-<s:property value="name"/>-=lwh=-<s:property value="jspTmp"/>"><s:property value="name"/></option>
					 	</s:iterator> 
					 </select>
				</label>
				<br/>   
				<input class="textbg4" type="submit" name="submit" value="绑定" />
				<!-- <input class="textbg4" type="submit" name="submit" value="绑定/生成" /> -->
				<input class="textbg4" type="button" onclick="window.location.href='column_template.action'" value="返回" />
			</form>
		</div>
		<!-- 内容 -->
	</BODY>
</HTML>
