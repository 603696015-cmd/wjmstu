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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
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
	<BODY onload="alertmess('<s:property value="elmessage"/>')">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">分配给下属部门 </span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_assigntoUsersInit.action?course.id=<s:property value="course.id" />">分配给学员</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="course_assigntoDeps" method="post"
				name="course_assignment" id="course_assignment" theme="simple">
				<script type="text/javascript">
function unselect_options(name)
{
	var obj = document.getElementById(name);
	while(obj.selectedIndex >= 0)
		obj[obj.selectedIndex].selected = false;
}

function switch_course()
{
	unselect_options("course_user_list_required");
	document.getElementById("submit_delete_required").disabled = true;
	document.getElementById("submit_add_required").disabled = false;
}

function switch_candidate_required()
{
	unselect_options("candidate_user_list");
	document.getElementById("submit_delete_required").disabled = false;
	document.getElementById("submit_add_required").disabled = true;
}

function switch_candidate_optional()
{
	unselect_options("candidate_user_list");
	unselect_options("course_user_list_required");
	document.getElementById("submit_delete_required").disabled = true;
	document.getElementById("submit_add_required").disabled = true;
}
function assign(st){
	if(window.confirm("确定分配？注意：本部门下所有用户將被分配该部门！")){
	course_assignment.action="course_assigntoDeps.action";
	course_assignment.submit();
	}
}
function unassign(st){
	if(window.confirm("确定删除分配？注意：删除后学员的相关学习信息将会删除，慎用！")){
	course_assignment.action="course_unassigntoDeps.action";
	course_assignment.submit();
	}
}
</script>
				<input type="hidden" name="course.id" id="source"
					value="<s:property value="course.id"/>">
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
							<wysLib:dep_list_cb attrname="assignDeps"
								inputname="assignDeps.id"></wysLib:dep_list_cb>
						</td>
						<td align="center">
							<input id="submit_add" name="submit_add" type="button"
								value="<< 添加" onclick="assign()">
							<br>
							<input id="submit_delete" name="submit_delete" type="button"
								value="删除 >>" onclick="unassign()">
						</td>
						<td valign="top">
							<wysLib:dep_list_cb attrname="canassignedDeps"
								inputname="canAssignDeps.id"></wysLib:dep_list_cb>
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
				<s:iterator value="assignDeps">
				<script> setabled('assignDeps',<s:property value="id" />);</script>
				</s:iterator>
				<br/>
				<s:iterator value="canAssignDeps">
				<script> setabled('canassignedDeps',<s:property value="id" />);</script>
				</s:iterator>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
