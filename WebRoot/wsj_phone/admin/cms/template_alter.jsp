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
				<span style="font-weight: bold;">模板添加</span>
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
 					$("#modelType").attr("value", '<s:property value="template.tmpType" />').attr("selected","selected"); 
					$("#modelId").attr("value", '<s:property value="template.typeId" />').attr("selected","selected"); 
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
				action="template_alter.action" method="post">
				<input type="hidden" name="template.id" value="<s:property value="template.id"/>" /> 
				<label>
					模 板 名
					<input name="template.name" type="text" id="title" value="<s:property value="template.name"/>"
						size="30" />
				</label>  
				<br/>
				<!-- 
				<label>
					模块类型
					<select name="template.tmpType" id="modelType">
					 	<option value="XW">新闻</option>
					 	<option value="ZS">知识</option> 
					 	<option value="KC">课程</option> 
					 	<option value="LT">论坛</option> 
					 </select> 
				</label>
				<label id="modelIdLabel">
					数据类型
					<select name="template.typeId" id="modelId">
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
				-->
				<label>
				说　　明
				<textarea rows="10" cols="40" name="template.remark"><s:property value="template.remark"/></textarea>
				</label> 
				<br/>
				<input class="textbg4" type="submit" name="submit" value="修改" />
				<input class="textbg4" type="button" onclick="window.location.href='template_list.action'" value="返回" />
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
