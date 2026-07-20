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
		<script type="text/javascript" src="js/menu.js"></script>
	</HEAD>
	<body>
<style type="text/css"> 
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
		<ul class="nav">
			<li>
				<div style="padding-top:3px;color:#077ac7;font-size:12px;"><wysLib:Navigation ivalue="课程列表" /></div>
			</li>
			<!--<li>
				<span style="font-weight: bold;">课程考试阅卷</span>
			</li>-->
		</ul>
		<!-- 内容 -->
		<div style="margin-top: 0px; text-align: center;">
			<table width="100%">
				<tr>
					<td width="120" valign="top" id="tree_list_td">
						<wysLib:ctypeTree href="course_exam_readlistInit.action?ctype.id=" rootAble="true"></wysLib:ctypeTree>				  </td>
					<td valign="middle" width="5px;" style="padding: 0px">
						<img src="images/leftmenu/main_55.gif" style="cursor: hand"
							onclick="changeTreeDisplay(this)" />
					</td>
					<td valign="top">
						<s:if test="courses.size==0">没有找到符合条件的课程</s:if>
						<s:else>
							<table width="100%" align="center" cellpadding="2"
								cellspacing="1" >
								<tr>
									<th align="center" >
										课程名称
									</th>
									<th align="center" >
										课程类别
									</th>
									<th align="center" >
										讲师
									</th>
									<th align="center" >
										创建时间
									</th>
									<!--
									<th align="center" >
										 
									</th>
									-->
									<th align="center" >
										 
									</th>
									<!--<th align="center" >
						修改时间
					</th>
					<th align="center" >
						开放状态
					</th>
					<th align="center" >
						&nbsp;
					</th>
					-->
									<th width="100" align="center" >&nbsp;									</th>
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
											<s:property value="teacherName" />
										</td>
										<td align="center" > 
											<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<!--
										<td align="center" >
											<s:property value="credit" />
										</td>
										-->
										<!-- <td align="center" >
							<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td align="center" >
							<s:date name="modifytime" format="yyyy-MM-dd HH:mm:ss" />
						
						</td> -->
										<td align="center" >
											<s:property value="validName" />
										</td>
										<td align="center" >
										<a
												href="exampaperread_roomlist.action?examRoom.title=&course.id=<s:property value="id"/>" class="textbg">试卷列表</a>
											<!--<a
												href="exampaperreadInit.action?course.id=<s:property value="id"/>">结业考试</a>
											--><!-- |
											<a
												href="simpaperreadlist.action?course.id=<s:property value="id"/>">模拟考试</a> -->
										</td>
									</tr>
								</s:iterator>
							</table>
						</s:else>
					</td>
				</tr>
			</table>
			<form action="course_exam_readlistInit.action" name="myclist">
				<s:hidden name="pN" id="pageNow"></s:hidden>
				<s:hidden name="pS"></s:hidden>

			</form>
			<script>
				function page(i) {
					document.getElementById("pageNow").value=i;
					myclist.submit();
				}
			</script>
			<wysLib:page></wysLib:page>


		</div>

		<!-- 内容 -->
	
	</body>
</HTML>
