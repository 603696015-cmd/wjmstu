<%@ page language="java" pageEncoding="UTF-8"   %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>" />
		<title>预览课程--<s:property value="course.name" /></title>
		<link href="css/study_csp.css" type="text/css" rel="stylesheet" />
		<link href="elfrontimages/index.css" type=text/css rel=stylesheet />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/cpstudy.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		
		<script type="text/javascript">
			var _cvideo;
			function myload(){
				_cvideo = new CourseVideo(<s:property value="coursePage.type"/>,"<s:property value="coursePage.page_url_"/>", 1);
				_cvideo.show("page_file");
			}
		</script>
		<style type="text/css">
<!--
.STYLE2 {
	font-size: 20px;
	color: #a00201;
	font-weight: bold;
}

.STYLE3 {
	color: #0000FF
}

.STYLE4 {
	color: #DFDFDF
}

.STYLE5 {
	font-size: 14px;
	font-weight: bold;
}

.STYLE6 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
	</HEAD>
	<body  onload="myload();">
		<table width="100%" height="36" border="0" cellpadding="0"
			cellspacing="0" background="images/bg1.gif">
			<tr>
				<td style="padding-left: 20px;" width="20%" align="left"
					class="STYLE6">
					广东公安远程教育平台
				</td>
				<td width="50%" align="center">
					<!--<p><a href="#">首 页</a>｜<a href="#">记笔记</a>｜<a href="#">查看笔记</a>｜<a href="#">练习中心</a>｜<a href="#">模考中心</a>｜<a href="#">结业考试</a></p>-->
				</td>
				<td style="padding-right: 20px;" align="right">
					<!--欢迎学员XXXX，当前时间XXXX年XX月XX日-->
				</td>
			</tr>
		</table>
		<table width="960" height="35" border="0" align="center"
			cellpadding="0" cellspacing="0" class="tabb2">
			<tr>
				<td class="STYLE6">
					课程名称：
					<s:property value="course.name" />
				</td>
				<td width="340" align="right" class="STYLE6">
				</td>
			</tr>
		</table>
		<table width="960" height="25" border="0" align="center"
			cellpadding="0" cellspacing="0" class="tabb2">
			<tr>
				<td>
					当前章节：
					<s:property value="coursePage.title" />
				</td>
				<td width="200" align="right">
					<!--<a href="#">返回个人中心</a>-->
				</td>
			</tr>
		</table>
		<table style="margin-top: 8px;" width="960" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="620px" valign="top" height="500px" style="padding:0px 5px;" id="page_file">
				</td>
				<td valign="top">
					<table width="100%" height="30" border="0" cellpadding="0"
						cellspacing="0" background="images/bg1.gif" class="tab42">
						<tr>
							<td>
								课程目录
							</td>
						</tr>
					</table>
					<table width="100%" height="100" border="0" cellpadding="0"
						cellspacing="0" bgcolor="f3fcff" class="tabrlb">
						<tr>
							<td valign="top" class=tdpad>
								<ul style="margin-top: 0px; list-style: none;">
									<s:iterator value="coursePages">
										<li style="font-size: 14px;">
											<s:if test="property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
											<a
												href="course_preview_biaozhun.action?coursePage.id=<s:property value="id" />&course.id=<s:property value="course.id"/>">
												<s:property value="title" />
											</a>
											<!-- <img src="img/studied.gif" width="15" height="13"> -->
										</li>
									</s:iterator>
								</ul>
							</td>
						</tr>
					</table>
					<table width="100%" height="30" border="0" cellpadding="0"
						cellspacing="0" background="images/bg1.gif" class="tab42">
						<tr>
							<td>
								课程简介
							</td>
						</tr>
					</table>
					<table width="100%" height="276" border="0" cellpadding="0"
						cellspacing="0" bgcolor="f3fcff" class="tabrlb">
						<tr>
							<td valign="top" class="tdpad">
								${course.description }
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%@include file="../../../elfrontman/frontbottom.jsp"%>
	</body>
</html>
