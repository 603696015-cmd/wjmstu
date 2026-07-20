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
				<span style="font-weight: bold;">选课排行</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
		<s:if test="courses.size==0">当前没有课程</s:if>
		<s:else>
			<table width="96%" align="center" cellpadding="2" cellspacing="1"
				>
				<tr>
					<th align="center" >
						课程名称
					</th>
					<th align="center" >
						课程类别
					</th>
					<th align="center" >
						创建者
					</th>
					<th align="center" >
						创建时间
					</th>
					<!--<th align="center" >
						&nbsp;
					</th>
					--><th align="center" >
						学习人数
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
							<s:property value="ctype.name" />
						</td>
						<td align="center" >
							<s:property value="creater.realname" />
						</td>
						<td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center" >
							<s:property value="userCount" />
						</td>
						<!--<td align="center" >
							<a target="_parent" href="courseman.action?course.id=<s:property value="id"/>">课件制作</a>
						</td>
					 	--><td align="center" >
						 <a href="submitAppalyCourse_front.action?course.id=<s:property value="id"/>">申请课程</a>
						</td>
					</tr>
				</s:iterator>
			</table>
			<form action="course_phlist.action" name="myclist">
				<s:hidden name="pN" id = "pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>
				
			</form>
			<script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
			<wysLib:page></wysLib:page>
			</s:else>
		</div>

		<!-- 内容 -->
	
	</body>
</HTML>
