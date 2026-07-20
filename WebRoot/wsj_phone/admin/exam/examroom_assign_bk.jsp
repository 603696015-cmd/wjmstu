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
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">为考场分配学员 </span>
			</li>
			<li class="sep">
			</li>
			<s:if test="examRoom.course.id!=-1">
				<li>
					<a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="examroom_listbyc.action?course.id=<s:property value="examRoom.course.id"/>">考试考场管理</a>
				</li>
			</s:if>
			<s:else>
				<li>
					<a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="examroomwithoutcourse_list.action">一般考试管理</a>
				</li>
			</s:else>
		</ul>

		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 16px;">
				为考场
			<b>
				<s:property value="examRoom.title" />
</b> 添加补考学员
			</label>
			<br>
			<form action="examroom_assign.action" method="post"
				name="course_assignment" id="course_assignment">
				<script type="text/javascript">
function unselect_options(name)
{
	var obj = document.getElementById(name);
	while(obj.selectedIndex >= 0)
		obj[obj.selectedIndex].selected = false;
}

function switch_assigned()
{
	unselect_options("room_assigned_user_list");
	document.getElementById("submit_delete").disabled = true;
	document.getElementById("submit_add").disabled = false;
}

function switch_candidate()
{
	unselect_options("room_candidate_user_list");
	document.getElementById("submit_delete").disabled = false;
	document.getElementById("submit_add").disabled = true;
}
function assign(){
	course_assignment.action = "examroom_assign.action";
	course_assignment.submit();
}
function unassign(){
	course_assignment.action = "examroom_unassign.action";
	course_assignment.submit();
}
</script>
				选择考试没通过的考场
				<select onChange="document.location.href='examroom_assign_bkInit.action?examRoom_bk.id='+this.options[this.selectedIndex].value+'&examRoom.id=<s:property value="examRoom.id"/>'">
					<option value="0">
						选择考场
					</option>
					<s:iterator value="examRooms">
						<option <s:if test="id==examRoom_bk.id">selected='selected'</s:if> value="<s:property value="id"/>">
							<s:property value="title" />
						</option>
					</s:iterator>
				</select>
				<table width="90%" border="1" cellpadding="1" cellspacing="1"
					align="center">
					<tr>
						<td width="50%">
							已分配学员
						</td>
						<td></td>
						<td width="50%">
							可分配学员
						</td>
					</tr>
					<tr>
						<td>
							<select name="bassignedUsers.id" id="room_assigned_user_list"
								size="40" style="width: 100%" multiple="multiple"
								onclick="switch_candidate();">
								<s:iterator value="bassignedUsers">
									<option value="<s:property value="id"/>">
										<s:property value="realname" />
										【
										<s:property value="department.name" />
										】
									</option>
								</s:iterator>
							</select>
						</td>
						<td align="center">
							<input id="submit_add" name="submit_add" type="button"
								value="<< 添加" onClick="assign();">
							<br>
							<input id="submit_delete" name="submit_delete" type="button"
								onclick="unassign();" value="删除 >>">
						</td>
						<td>
							<select name="canAssignUsers.id" id="room_candidate_user_list"
								size="40" style="width: 100%" multiple="multiple"
								onclick="switch_assigned();">
								<s:iterator value="canAssignUsers">
									<option value="<s:property value="id"/>">
										<s:property value="realname" />
										【
										<s:property value="department.name" />
										】
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
				</table>
				<s:hidden name="examRoom.id"></s:hidden>
			</form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
