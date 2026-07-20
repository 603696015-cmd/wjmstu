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
		<TITLE>标签管理</TITLE>
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
				<span style="font-weight: bold;">标签添加</span>
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
 							$("#contentType").html("<option value=\"0\">普通</option> <option value=\"1\">推荐</option> <option value=\"2\">热门</option> <option value=\"3\">重点</option> <option value=\"4\">头条</option>");
 							
 							$("#viewType").html("<option value=\"TW\">图文</option><option value=\"LB\">列表</option>");
 							
 							$("#modelId").html($("#newsTypeList").val());
 						}else if($("#modelType").attr("selected","selected").val()=='ZS'){
 							$("#contentType").html("<option value=\"0\">普通</option> <option value=\"1\">推荐</option> <option value=\"2\">热门</option> <option value=\"3\">重点</option>");
 							
 							$("#viewType").html("<option value=\"TW\">图文</option><option value=\"LB\">列表</option>");
 							
 							$("#modelId").html($("#knowledgeTypeList").val());
 						}else if($("#modelType").attr("selected","selected").val()=='KC'){
 							$("#contentType").html("<option value=\"0\">--无--</option>");
 							
 							$("#viewType").html("<option value=\"TW\">图文</option><option value=\"LB\">列表</option>");
 							
 							$("#modelId").html($("#courseTypeList").val());
 						}else if($("#modelType").attr("selected","selected").val()=='LT'){
 							$("#contentType").html("<option value=\"0\">普通</option> <option value=\"1\">推荐</option> <option value=\"2\">热门</option>");
 							
 							$("#viewType").html("<option value=\"LB\">列表</option>");
 							
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
			<form name="message_management" id="label_add_form"
				action="label_add.action" method="post">
				<input type="hidden" name="operate" value="send" />
				<label>
					标签名
					<input name="label.name" type="text"  value="" size="30" id="name"/>
				</label> 
				<label>
					 显示类型
					 <select name="label.viewType" id="viewType">
					 	<option value="LB">列表</option>
					 	<option value="TW">图文</option>
					 </select> 
				</label> 
				<br/>
				<label>
					模块类型
					<select name="label.modelType" id="modelType">
					 	<option value="XW">新闻</option>
					 	<option value="ZS">知识</option> 
					 	<option value="KC">课程</option> 
					 	<option value="LT">论坛</option> 
					 </select> 
				</label>
				<label id="modelIdLabel">
					数据类型
					<select name="label.modelId" id="modelId">
						<s:iterator value="newsTypeList">
							<option value="<s:property value="id"/>"><s:property value="name"/></option>
						</s:iterator> 
					</select>
					<textarea id="newsTypeList" style="display: none;">
						<s:iterator value="newsTypeList">
							<option value="<s:property value="id"/>"><s:property value="name"/></option>
						</s:iterator> 
					</textarea>
					<textarea id="courseTypeList" style="display: none;">
						<s:iterator value="courseTypeList">
							<option value="<s:property value="id"/>"><s:property value="name"/></option>
						</s:iterator> 
					</textarea>
					<textarea id="knowledgeTypeList" style="display: none;">
						<s:iterator value="knowledgeTypeList">
							<option value="<s:property value="id"/>"><s:property value="name"/></option>
						</s:iterator> 
					</textarea>
					<textarea id="forumBlockTypeList" style="display: none;">
						<s:iterator value="forumBlockTypeList">
							<option value="<s:property value="id"/>"><s:property value="name"/></option>
						</s:iterator> 
					</textarea>
				</label>
				<br/>
				<label>
					类别
					<select name="label.contentType" id="contentType"> 
						<option value="0">普通</option>  
						<option value="1">推荐</option>  
						<option value="2">热门</option>  
						<option value="3">重点</option>  
						<option value="4">头条</option>
					</select> 
				</label>
				<label>
					显示条数
					<input type="text" name="label.record" style="width: 30px;" 
					value="10" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" id="record">
				</label>
				<br/>
				<label>
					标题字数
					<input type="text" name="label.titleLength" style="width: 30px;"
					 onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="10" id="titleLength">
				</label>
				<label>
					样式名
					<input type="text" name="label.style">
				</label>
				<!-- 
				<label>
					内容字数
					<input type="text" name="label.contentLength" style="width: 30px;" value="20" id="contentLength">
				</label> 
				 -->
				 <label> 
					<select name="label.row"  style="display: none;">
						<option value="1">1</option>  
					</select>
				</label>
				<br/>  
				<label>
				说明
				<textarea rows="10" cols="40" name="label.remark" id="remark"></textarea>
				</label>
				<br/>
				<input class="textbg4" type="submit" name="" id="btnSubmit" value="添加" />
				<input class="textbg4" type="button" onclick="window.location.href='label_list.action'" value="返回" />
			</form>
		</div>

		<!-- 内容 -->
	
	</body>
</HTML>
