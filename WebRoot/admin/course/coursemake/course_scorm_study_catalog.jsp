<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>" />
		<title>学习课程(scorm1.2)--<s:property value="course.name" />
		</title>
		<link href="css/bofang2.css" type=text/css rel=stylesheet>
		<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT" />
		<meta http-equiv="Pragma" content="no-cache" />
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<style type="text/css">
.STYLE4 {
	FONT-SIZE: 12px
}

.jiangyi {
	PADDING-RIGHT: 8px;
	PADDING-LEFT: 8px;
	FONT-SIZE: 12px;
	PADDING-BOTTOM: 8px;
	PADDING-TOP: 8px;
	BACKGROUND-COLOR: #ffffff
}

.STYLE5 {
	COLOR: #ff0000
}

#menubox {
	BORDER-RIGHT: #26517b 0px solid;
	BORDER-TOP: #26517b 0px solid;
	BACKGROUND: #ffffff;
	MARGIN: 0px;
	BORDER-LEFT: #26517b 0px solid;
	WIDTH: 180px;
	BORDER-BOTTOM: #26517b 0px solid;
	HEIGHT: auto
}

BODY {
	MARGIN: 0px
}

.STYLE10 {
	FONT-SIZE: 12px;
	LINE-HEIGHT: 24px
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px
}

.STYLE11 {
	FONT-SIZE: 14px;
	COLOR: #ff0000
}

.menu {
	font-size: 13px;
	margin: 0px;
	background: #fff;
	border: solid 1px buttonface;
}

.menu li {
	list-style: none;
	padding: 3px 2px 3px 10px;
}

.menu li a {
	text-decoration: none;
	color: #00f;
}
</STYLE>

		<script type="text/javascript">
function toPreOrNext(x){
	if(x==1)
		document.location="course_scorm_study.action?course.id=<s:property value="course.id"/>&classid=<s:property value="scormcourse.classid"/>&coursePage.id=-1&course.isLogout=1&scormcourse.navitype=next&scormcourse.nowScoid=<s:property value="scormcourse.scoid"/>"
	else
		document.location="course_scorm_study.action?course.id=<s:property value="course.id"/>&classid=<s:property value="scormcourse.classid"/>&coursePage.id=-1&course.isLogout=1&scormcourse.navitype=prev&scormcourse.nowScoid=<s:property value="scormcourse.scoid"/>"
}
function showmenu(obj){
	if($("#catalog").css("display")=='none')
		$("#catalog").css("display","block");
	else
		$("#catalog").css("display","none");
	$("#catalog").css("left",$(obj).offset().left);
	$("#catalog").css("top",$(obj).offset().top);
}
function myload(){
	window.top.frames["window3"].document.location.href="<%=basePath%><s:property value="scormcourse.launch"/>";
	//window.top.API.SCOID="<s:property value="scormcourse.scoid"/>";
	//window.top.setScoid("<s:property value="scormcourse.scoid"/>");
}
</script>
	</head>
	<body style="overflow: visible" onload="myload();">
		<s:if test="!scormcourse.haspreSco">
			<input type="button" onclick="toPreOrNext(0)" value="上一章" />
		</s:if>
		<s:if test="!scormcourse.hasnextSco">
			<input type="button" onclick="toPreOrNext(1)" value="下一章" />
		</s:if>
		<ul class="menu" id="catalog">
			<s:iterator value="scormcourse.scoList">
				<li>
					<s:if test="scormcourse.scoid==scoid">
						<b><s:property value="title" />
						</b>【<s:property value="lessonStatusName" />】</s:if>
					<s:else>
						<a href="course_scorm_study.action?course.id=<s:property value="course.id"/>&scormcourse.scoid=<s:property value="scoid"/>&classid=<s:property value="scormcourse.classid"/>"><s:property
								value="title" /> </a>【<s:property value="lessonStatusName" />】</s:else>
				</li>
			</s:iterator>
		</ul>
	</body>
</html>
