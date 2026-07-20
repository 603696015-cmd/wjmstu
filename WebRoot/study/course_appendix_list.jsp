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
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<TITLE>课程类别管理</TITLE>
		<base href="<%=basePath%>">
		<META http-equiv=Page-Enter
			content=RevealTrans(Duration=0.5,Transition=14)>
		<link rel="stylesheet" type="text/css" href="css/system.css" />
		<link rel="stylesheet" type="text/css" href="css/manage.css" />
		<script type="text/javascript" src="js/menu.js"></script>
		<link rel="StyleSheet" href="eltree/dtree.css" type="text/css" />
		<script type="text/javascript" src="eltree/dtree.js"></script>
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
				<span style="font-weight: bold;"><s:if test="course_sourse==1">课件包下载</s:if>
				<s:else>课程讲义下载</s:else></span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		 
			<s:form action="course_appendix_list" name="myclist" theme="simple">
				<s:hidden name="pN" id = "pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				课程名称：<s:textfield name="course.name"></s:textfield> <s:submit value="搜索"></s:submit>
			<s:hidden name="course_sourse"></s:hidden>
			</s:form>
				
			<s:if test="courses.size==0">没有找到符合条件的课程</s:if>
		<s:else>
				<table width="96%" align="center" cellpadding="2" cellspacing="1"
				>
				<tr>
					<th align="center" >
						课程名称
					</th>
					<th align="center" >
						讲师
					</th>
					<th align="center" >
						&nbsp;
					</th>
				</tr>
				<s:iterator value="courses">
					<tr>
						<td align="center" >
							<s:property value="name" />
						</td>
						<td align="center" >
							<s:property value="teacherName" />
						</td>
					 	<td align="center" >
						<s:if test="course_sourse==1"><a target="_blank" href="<s:property value="kj_appendix" />">课件包下载</a></s:if>
				<s:else><a target="_blank" href="<s:property value="jy_appendix" />">课程讲义下载</a></s:else>
				</td>
					</tr>
				</s:iterator>
			</table>
			</s:else> 
			<script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
			<wysLib:page></wysLib:page>
			
		</div>

		<!-- 内容 -->
	</BODY>
</HTML>
