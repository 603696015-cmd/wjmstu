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
		<script type="text/javascript" src="js/tree/depuserlist.js"></script>

	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz">
    				<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="学员增减" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">分配给学员</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_assigntoDepsInit.action?course.id=<s:property value="course.id" />">分配给下属部门</a>
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
			<s:form action="course_assigntoUsers" method="post"
				name="course_assignment" id="course_assignment">
				<script type="text/javascript">
function assign(st){
	if(window.confirm("确定分配？！")){
	document.getElementById("status").value=st;
	course_assignment.action="course_assigntoUsers.action";
	course_assignment.submit();
	}
}
function unassign(st){
	if(window.confirm("确定删除分配？！")){
	document.getElementById("status").value=st;
	course_assignment.action="course_unassigntoUsers.action";
	course_assignment.submit();
	}
}

</script>
				<input type="hidden" name="course.id" id="source"
					value="<s:property value="course.id"/>"> 
				<input type="hidden" name="status" id="status" value="0">
				<table width="90%" border="1" align="center">
					<tr>
						<td width="50%">
							已分配学员（作为必修）
						</td>
						<td>
						</td>
						<td width="50%">
							可分配学员
						</td>
					</tr>
					<tr>
						<td valign="top" align="left">
							<wysLib:depuserlist attrname="bassignedDep"
								inputname="bassignedUsers.id" ></wysLib:depuserlist>
						</td>
						<td align="center">
							<input id="submit_add_required" name="submit_add_required"
								type="button" value="<< 添加" onClick="assign(0);">
							<br>
							<input id="submit_delete_required" name="submit_delete_required"
								type="button" value="删除 >>" onClick="unassign(0);">
						</td>
						<td rowspan=6 valign="top">
							<wysLib:depuserlist inputname="canAssignUsers.id"
								attrname="canAssignDep"></wysLib:depuserlist>
						</td>
					</tr>
					<!--tr>
						<td width="50%">
							已分配用户（作为主修）
						</td>
						<td></td>
					</tr-->
					<!--tr>
						<td valign="top" align="left">
							<wysLib:depuserlist inputname="zassignedUsers.id"
								attrname="zassignedDep"></wysLib:depuserlist>
						</td>
						<!--td align="center">
							<input id="submit_add_zhuxiu" name="submit_add_zhuxiu"
								type="button" value="<< 添加" onclick=" assign(2);">
							<br>
							<input id="submit_delete_zhuxiu" name="submit_delete_zhuxiu"
								type="button" value="删除 >>" onclick="unassign(2);">
						</td>
					</tr-->
					<!--tr>
						<td width="50%">
							已分配用户（作为选修）
						</td>
						<td></td>
					</tr-->
					<!--tr>
						<td valign="top" align="left">
							<wysLib:depuserlist inputname="xassignedUsers.id"
								attrname="xassignedDep"></wysLib:depuserlist>
						</td>
						<!--td align="center">
							<input id="submit_add_optional" name="submit_add_optional"
								type="button" value="<< 添加" onclick=" assign(1);">
							<br>
							<input id="submit_delete_optional" name="submit_delete_optional"
								type="button" value="删除 >>" onclick="unassign(1);">
						</td>
					</tr-->

				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
