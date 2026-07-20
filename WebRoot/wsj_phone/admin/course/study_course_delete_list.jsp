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
        <link href="css/listlable.css" type="text/css" rel="stylesheet">
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
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">处理删除学习课程</span>
			</li>-->
		</ul>
				</td>
    			<td width="120" valign="middle" class="tablequiz">
    				<A id=quit  href="javascript:window.parent.full_screen(false);" class="textbg6" style="display:none">退出全屏</A>
    			</td>
  			</tr>
		</table>
		
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<s:if test="mycourses.size==0">没有需要处理的课程</s:if>
			<s:else>
				<table width="90%" align="center" cellpadding="2" cellspacing="2">
					<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
							课程名称
						</td>
						<td height="30" align="center" >
							申请删除人
						</td>
						<td height="30" align="center" >
							删除时间
						</td>
						<td height="30" align="center" >
							删除
						</td>
					</tr>
					<s:iterator value="mycourses">
						<tr>
						<td height="30" style="padding-left:8px;color:blue;" align="left">
								<s:property value="course.name" />
						  </td>
							<td height="30" align="center" >
								<s:property value="user.realname" />
							</td>
							<td height="30" align="center" >
								<s:date name="deletedate" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td height="30" align="center" >
								<a
									href='study_course_delete_op.action?mycourse.course.id=<s:property value="course.id"/>&mycourse.user.id=<s:property value="user.id"/>&pN=${pN}&pS=${pS}'>批准删除</a>
							</td>
								<td height="30" align="center" >
								<a
									href='study_course_delete_unop.action?mycourse.course.id=<s:property value="course.id"/>&mycourse.user.id=<s:property value="user.id"/>&pN=${pN}&pS=${pS}'>不批准</a>
							</td>
						</tr>
					</s:iterator>
			  </table>
				<form action="study_course_delete_list.action" method="post" name="scdlist">
					<s:hidden name="pN" id="pageNow"></s:hidden>
					<s:hidden name="pS"></s:hidden>
				</form>
				<script>
					function page(i)
					{
						document.getElementById("pageNow").value=i;
						scdlist.submit();
					}
				
				</script>
				<wysLib:page_cisco></wysLib:page_cisco>
			</s:else>
		</div>
		<!-- 内容 -->
	
	</body>
</HTML>
