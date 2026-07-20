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
		<script type="text/javascript" src="js/tree/dtreedep.js"></script>
		<script type="text/javascript">
			function alertmess(mess){
				if(mess=='success') alert('分配成功');
			}
			function setabled(idstr,id){
					document.getElementById(idstr+id).checked=false;
					document.getElementById(idstr+id).disabled=false;
			}
			function assign( ){
				if(window.confirm('确定分配到该部门？注意，部门及下级部门的学员将会被分配进来！')){
				course_assignment.action="examroom_assigndep_add.action";
				course_assignment.submit();
				}
			}
			function unassign( ){
				if(window.confirm('确定不分配到该部门？注意，之前分配的学员将会被除掉！')){
				course_assignment.action="examroom_assigndep_delete.action";
				course_assignment.submit();
				}
			}
		</script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="部门增减" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">为考场分配部门 </span>
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
						href="examroomwithoutcourse_list.action">考场列表</a>
				</li>
			</s:else>
			<li>
					<a style="cursor: hand"
						onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
						onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
						href="examroom_assignuserlist.action?examPaper.id=<s:property value="examPaper.id"/>&examRoom.id=<s:property value="examRoom.id"/>">人员列表</a>
				</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<label style="font-size: 15px;">
				为考场
				<b>
				<s:property value="examRoom.title" /> </b> 分配部门
			</label>

			<s:form action="" method="post" name="course_assignment"
				id="course_assignment" theme="simple">
				<s:hidden name="examRoom.id"></s:hidden>
				<s:hidden name="examPaper.id"></s:hidden>
				<table width="600" height="500" border="1" align="center"
					cellpadding="1" cellspacing="1">
					<tr>
						<td height="20" width="50%">
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
								value="<< 添加" onClick="assign()">
							<br>
							<input id="submit_delete" name="submit_delete" type="button"
								value="删除 >>" onClick="unassign()">
						</td>
						<td valign="top">
							<wysLib:dep_list_cb attrname="canassignedDeps"
								inputname="canAssignDeps.id"></wysLib:dep_list_cb>
						</td>
					</tr>
				</table>
				<s:iterator value="assignDeps">
					<script> setabled('assignDeps',<s:property value="id" />);</script>
				</s:iterator>
				<s:iterator value="canAssignDeps">
					<script> setabled('canassignedDeps',<s:property value="id" />);</script>
				</s:iterator>
			</s:form>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
