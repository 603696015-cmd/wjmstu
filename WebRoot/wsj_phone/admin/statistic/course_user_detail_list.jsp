<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.sopia.courseman.entities.Course"%>
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
		<script type="text/javascript" src="js/message.js"></script>
		<script type="text/javascript" src="js/cexampaper.js"></script>
		<style type="text/css">
td {
	font-size: 12px;
	color: #333333;
	line-height: 150%
}

tr {
	background-color: expression(( this . sectionRowIndex % 2 == 0) ?
		"#ffffff" : "#f4f4f4" )
}
</style>
	</HEAD>
	<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  			<tr>
    			<td valign="middle" class="tablequiz"><ul class="nav">
			<li>
				<div style="padding-top: 3px; color: #077ac7; font-size: 12px;">
					<wysLib:Navigation ivalue="学员列表" />
				</div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程学员列表</span>
			</li>
			<li class="sep">
			</li>
			<li>
				<a style="cursor: hand"
					onMouseOver="this.style.backgroundImage='url(images/bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#a6d0e7'; "
					onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'"
					href="course_stat_view.action?course.id=<s:property value="course.id"/>">基本信息</a>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<table width="100%" cellpadding="1" cellspacing="1">
			<tr>
				<td valign="top" align="center">
					<s:if test="courses.size==0">没有符合条件的课程</s:if>
					<s:else>
						<table width="100%" cellpadding="1" cellspacing="1"
							bgcolor="#EBEBEB">
							<tr>
								<th width="130" height="30" align="center">
									姓名
								</th>
								<th width="100" align="center">
									账号
								</th>
								<th width="150" height="30" align="center">
									所属培训班
								</th>
								<th width="120" height="30" align="center">
									考场信息
								</th>
								<th width="150" height="30" align="center">
									部门
								</th>
							</tr>
							<tbody onMouseOut="changeback()" onMouseOver="changeto()">
								<s:iterator value="myCourses">
									<tr>
										<td height="30" align="center" bgcolor="#FFFFFF"
											style="color: #CC0099;">
											<s:property value="user.realname" />
										</td>
										<td align="center">
											<s:property value="user.username" />
										</td>
										<s:if test="className!=null">
											<td width="150" height="30" align="center">
												<s:property value="className" />
											</td>
										</s:if>
										<s:else>
											<td width="150" height="30" align="center">
												单独分配而来
											</td>
										</s:else>
										<td height="30" align="center">
											<s:property value="myExamPaper.examRoom.title" />
										</td>
										<td width="150" height="30" align="center">
											<s:property value="user.department.name" />
										</td>
								</s:iterator>
							</tbody>
						</table>
						<form action="course_user_detail_list.action" method="post"
							name="ddd">
							<s:hidden name="pN" id="pageNow"></s:hidden>
							<s:hidden name="pS"></s:hidden>
							<s:hidden name="course.id"></s:hidden>
							<s:hidden name="course.classid"></s:hidden>
						</form>
						<script type="text/javascript">
						function page(i){
							document.getElementById("pageNow").value=i;
							ddd.submit();
						}
						function toexcel(){
							ddd.action = "course_user_list.action?exprot=true";
							ddd.submit();
						}
						function submit___(){
							//ddd.action="combinationSearchCourselist.action";
							//ddd.submit();
							window.history.back(-1);
						}
		 		   </script>
						<wysLib:page></wysLib:page>
					</s:else>
					<a href="#" onClick="submit___();return false;" style="width:80px" class="textbg4">返回列表</a>
				</td>
			</tr>
		</table>
		
		<!-- 内容 -->
	
	</body>
</HTML>
