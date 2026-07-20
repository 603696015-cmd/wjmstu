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
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<div class="dh3">
			<div class="newpos"></div>
			<div class="newpos2">
				<a href="cltype_list.action">培训班类别管理</a>
				<a href="elclass_course.action">培训班类别添加</a>
				<span style="font-weight: bold;">管理培训班课程</span>
				<a href="elclass_course_credit.action?elclass.id=<s:property value="elclass.id"/>">培训班课程学分 </a>
			</div>
		</div>
		<form action="" method="post" name="class_course" id="class_course">
			<input name="status" id="status" type="hidden" />
			<script type="text/javascript">
function addBxCourse(){
	document.getElementById("status").value="1";
	class_course.action="elclass_course_add.action";
	class_course.submit();
}
function addXxCourse(){
	document.getElementById("status").value="0";
	class_course.action="elclass_course_add.action";
	class_course.submit();
}
function deleteBxCourse(){
	class_course.action="elclass_course_delete.action";
	class_course.submit();
}
function deleteXxCourse(){
	class_course.action="elclass_course_delete.action";
	class_course.submit();
}
function unselect_options(name)
{
	var obj = document.getElementById(name);
	while(obj.selectedIndex >= 0)
		obj[obj.selectedIndex].selected = false;
}

function switch_course()
{
	unselect_options("class_course_list_required");
	unselect_options("class_course_list_optional");
	document.getElementById("submit_delete_required").disabled = true;
	document.getElementById("submit_add_required").disabled = false;
	document.getElementById("submit_delete_optional").disabled = true;
	document.getElementById("submit_add_optional").disabled = false;
}

function switch_candidate_required()
{
	unselect_options("candidate_course_list");
	unselect_options("class_course_list_optional");
	document.getElementById("submit_delete_required").disabled = false;
	document.getElementById("submit_add_required").disabled = true;
	document.getElementById("submit_delete_optional").disabled = true;
	document.getElementById("submit_add_optional").disabled = true;
}

function switch_candidate_optional()
{
	unselect_options("candidate_course_list");
	unselect_options("class_course_list_required");
	document.getElementById("submit_delete_optional").disabled = false;
	document.getElementById("submit_add_optional").disabled = true;
	document.getElementById("submit_delete_required").disabled = true;
	document.getElementById("submit_add_required").disabled = true;
}
</script>
			<input type="hidden" name="source" id="source" value="class_course">
			<table width="90%" border="1" cellpadding="2" cellspacing="1"
				align="center">
				<tr>
					<td width="50%">
						已添加课程（作为必修）
					</td>
					<td></td>
					<td width="50%">
						可添加课程
					</td>
				</tr>
				<tr>
					<td>
						<select name="bxCourses.id" id="class_course_list_required"
							size="19" style="width: 100%" multiple="multiple"
							onclick="switch_candidate_required();">
							<s:iterator value="bxCourses">
								<option value="<s:property value="id"/>">
									<s:property value="name" />
								</option>
							</s:iterator>
						</select>
					</td>
					<td align="center">
						<input id="submit_add_required" name="submit_add_required"
							type="button" onclick="addBxCourse();" value="<< 添加">
						<br>
						<input id="submit_delete_required" name="submit_delete_required"
							type="button"  onclick="deleteBxCourse();"  value="删除 >>">
					</td>
					<td rowspan=4>
						<select name="myCourses.id" id="candidate_course_list" size="41"
							style="width: 100%" multiple="multiple"
							onclick="switch_course();">
							<s:iterator value="myCourses">
								<option value="<s:property value="id"/>">
									<s:property value="name" />
								</option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td width="50%">
						已添加课程（作为选修）
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						<select name="xxCourses.id" id="class_course_list_optional"
							size="19" style="width: 100%" multiple="multiple"
							onclick="switch_candidate_optional();">
							<s:iterator value="xxCourses">
								<option value="<s:property value="id"/>">
									<s:property value="name" />
								</option>
							</s:iterator>
						</select>
					</td>
					<td align="center">
						<input id="submit_add_optional" name="submit_add_optional"
							type="button"  onclick="addXxCourse();" value="<< 添加">
						<br>
						<input id="submit_delete_optional" name="submit_delete_optional"
							type="button"  onclick="deleteXxCourse();" value="删除 >>">
					</td>
				</tr>
			</table>
			<s:hidden name="elclass.id"></s:hidden>
		</form>
	
	</body>
</HTML>
