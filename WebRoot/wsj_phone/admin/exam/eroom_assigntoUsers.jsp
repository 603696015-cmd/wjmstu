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
		<script type="text/javascript" src="js/stuffman.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>

	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="分配学员" /></div>
			</li>
			<!--<li>
			<span style="font-weight: bold;">分配给学员</span>
			</li>
			<li >
			</li>
			<li>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px;">
			<s:form action="course_assigntoUsers" method="post"
				name="course_assignment" id="course_assignment">
				<script type="text/javascript">
function assign(st){
	if(window.confirm("确定分配？！")){
	document.getElementById("status").value=st;
	course_assignment.action="examroom_assign.action";
	course_assignment.submit();
	}
}
function unassign(st){
	if(window.confirm("确定删除分配？！")){
	document.getElementById("status").value=st;
	course_assignment.action="examroom_unassign.action";
	course_assignment.submit();
	}
}
  function assignSearch(st){
	document.getElementById("status").value=st;
	course_assignment.action="examroom_assignSearchInit.action";
	course_assignment.submit();
  }

</script>
				<input type="hidden" name="course.id" id="source"
					value="<s:property value="course.id"/>">
				<input type="hidden" name="status" id="status" value="0">
				<s:hidden name="examRoom.id"></s:hidden>
				<s:hidden name="examPaper.id"></s:hidden>
				<table width="90%" border="1" align="center">
				    <tr>
				       <td>时间范围：</td>
				       <td colspan="2">
				          开始时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				          <input type="text" size="16" name="starttime" onClick="setday(this)">
						 结束时间 
				          <input type="text" size="16" name="endtime" onClick="setday(this)">
				          <br>
				          培训班名称
				          <input type="text" size="16" name="classname" >
						  <input id="find" name="find" type="button" value="搜索" onClick="assignSearch(0);">
				       </td>
				    </tr>
					<tr>
						<td valign="top" align="left">
							<wysLib:depuserlist inputname="canAssignUsers.id"
								attrname="canAssignDep"></wysLib:depuserlist>
						</td>
						<td align="center">
							<input id="submit_add_required" name="submit_add_required"
								type="button" value="<< 添加" onClick="assign(0);">
							<br>
							<input id="submit_delete_required" name="submit_delete_required"
								type="button" value="删除 >>" onClick="unassign(0);">
						</td>
						<td rowspan=6 valign="top">
						
								<wysLib:depuserlist attrname="bassignedDep"
								inputname="bassignedUsers.id" ></wysLib:depuserlist>
						</td>
					</tr>

				</table>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
