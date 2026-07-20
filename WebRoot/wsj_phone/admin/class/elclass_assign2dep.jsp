<%@ page language="java" pageEncoding="UTF-8"   %>
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
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<TITLE>中国食品安全培训网--管理端--</TITLE>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="js/tree/dtreedep.js"></script>
		<script type="text/javascript">
		function alertmess(mess){
			if(mess=='success') alert('分配成功');
		}
	</script>
	</HEAD>
	<body onload="alertmess('<s:property value="elmessage"/>')">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="新闻列表页" /></div>
			</li>
			<li>
				<span style="font-weight: bold;">分配培训班到部门</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="elclass_assign2userInit.action?elclass.id=<s:property value="elclass.id" />">分配给学员</a>
			</li>
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<form action="elclass_assign2dep_add.action" method="post"
			name="class_assignment" id="class_assignment">
			<script type="text/javascript">
			
function elclass_assign2dep_add( ){
	if(window.confirm("确定分配？注意：本部门下所有用户將被分配该部门！")){
	class_assignment.action="elclass_assign2dep_add.action";
	class_assignment.submit();
	}
}
function elclass_assign2dep_delete(){
	if(window.confirm("确定删除分配？注意：删除后学员的相关学习信息将会删除，慎用！")){
	class_assignment.action="elclass_assign2dep_delete.action"
	class_assignment.submit();
	}
}
</script>
			<!--<input type="hidden" name="source" id="source" value="assignment">-->
			<table width="90%" border="1" align="center">
				<tr>
					<td width="50%">
						已分配部门
					</td>
					<td></td>
					<td width="50%">
						可分配部门
					</td>
				</tr>
				<tr>
					<td valign="top">
						<wysLib:dep_list_cb attrname="assignedDeps"
							inputname="assignedDeps.id"></wysLib:dep_list_cb>
					</td>
					<td align="center">
						<input id="submit_add" name="submit_add" type="button"
							value="<< 添加" onclick="elclass_assign2dep_add()">
						<br>
						<input id="submit_delete" name="submit_delete" type="button"
							value="删除 >>" onclick="elclass_assign2dep_delete()">
					</td>
					<td valign="top">
						<wysLib:dep_list_cb attrname="canassignedDeps"
							inputname="canAssignDeps.id"></wysLib:dep_list_cb>
						<s:hidden name="elclass.id" />
					</td>
				</tr>
			</table>
			<br>
			<script type="text/javascript">
				function setabled(idstr,id){
					document.getElementById(idstr+id).checked=false;
					document.getElementById(idstr+id).disabled=false;
				}
			</script>
			<s:iterator value="assignedDeps">
				<script> setabled('assignedDeps',<s:property value="id" />);</script>
			</s:iterator>
			<s:iterator value="canAssignDeps">
				<script> setabled('canassignedDeps',<s:property value="id" />);</script>
			</s:iterator>
			<br>
			<br>
			<!--<input type="submit" value="提交" />
		-->
		</form>
	
	</body>
</HTML>
