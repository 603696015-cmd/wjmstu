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
		<base href="<%=basePath%>" target="_self"/>
		<title>学习课程（scorm1.2课程）--<s:property value="course.name" /></title>
		<link href="css/bofang2.css" type=text/css rel=stylesheet>
		<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT" />
		<meta http-equiv="Pragma" content="no-cache" />
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
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

function catalog_switch()
{
	var obj = document.getElementById("tree_list_td");
	if(obj.style.display==""||obj.style.display=="block"){
	 	obj.style.display="none";
	 	obj1.src="images/leftmenu/main_55_1.gif";
	}
	else{
		obj.style.display="block";
	 	obj1.src="images/leftmenu/main_55.gif";
	}
}
 function SetWinHeight(obj){
 	var win=obj;
	if (win && !window.opera){
	   	if(win.contentDocument &&document.body.offsetHeight) 
			win.height = document.body.offsetHeight; 
		else if(win.Document && document.body.scrollHeight)
	  		win.height = document.body.scrollHeight;
	}
}
var API = null;
function LMSIsInitialized()
{
   var value = API.LMSGetValue("cmi.core.student_name");
   var errCode = API.LMSGetLastError().toString();
   if (errCode == 301){
      return false;
   }else{
      return true;
   }
}
function initAPI()
{
   API = this.document.APIAdapter;
}
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
var _cpst;
var needsetCp = false;
function myload(){
	_cpst=new CourseStudy(<s:property value="course.classid"/>,<s:property value="course.id"/>, 0,
		 <s:property value="myCourse.passtime"/>,
		 <s:property value="course.during"/>*60,
		 <s:property value="course.querytime"/>,
		 <s:property value="myCourse.passtime2"/>,
		 <s:property value="studyCourseRecordId"/>);
	//_cpst.durtimediv="timer3";
	//_cpst.realtimediv="timer2";
	//_cpst.processdiv="processDiv3";
	_cpst.studyinfo_time=<s:property value="#session.studyinfo_time"/>;
	_cpst.init();
}
window.onbeforeunload=function(){
	window.event.returnValue="确定退出本次学习？"; 
}
window.onunload=function(){
	_cpst.exitStudy(); 
}
</script>
	</head>
	<body style="overflow: visible" onload="initAPI();myload();">
		<object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" width="0"
			height="0" id="APIAdapter"
			codebase="http://java.sun.com/products/plugin/1.3/jinstall-13-win32.cab#Version=1,3,0,0">
			<param name="code"
				value="org/adl/samplerte/client/APIAdapterApplet.class">
			<param name="codebase" value="<%=path%>">
			<param name="type" value="application/x-java-applet;version=1.3">
			<param name="mayscript" value="true">
			<param name="scriptable" value="true">
			<param name="archive" value="elearning.jar">
			<param name="servleturl" value="<%=basePath%>lmscmi">
			<param name="SCOID" value="<s:property value="scormcourse.scoid"/>">
			<param name="USERID" value="<s:property value="scormcourse.userid"/>">
			<param name="COURSEID"
				value="<s:property value="scormcourse.courseid"/>">
			<param name="CID"
				value="<s:property value="course.id"/>">
			<param name="CLASSID" value="<s:property value="scormcourse.classid"/>">
			<comment>
			<applet code="org/adl/samplerte/client/APIAdapterApplet.class"
				archive="elearning.jar" codebase="<%=path%>" height="1"
				id="APIAdapter" name="APIAdapter" width="1" mayscript="true">
			</applet>
			</comment>
		</object>
		<table cellpadding="1" cellspacing="1" width="100%" height="100%">
			<tr>
<!-- 	 <td id="tree_list_td" valign="top" align="left" width="200px">
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
				</td>  -->	
				<td id="tree_list_td" valign="top" align="left" width="200px">
					<iframe width="200px" scrolling="auto" frameborder="0" height="100%" src="course_scorm_study.action?course.id=<s:property value="course.id"/>&classid=<s:property value="scormcourse.classid"/>"></iframe>
				</td>	
				<td width="5px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="catalog_switch(this)" />
				</td>
				<td valign="top">
					<iframe style="z-index: 9999"
						src="<%=basePath%><s:property value="scormcourse.launch"/>"
						name="right" align="middle" width="100%" height="100%"
						scrolling="auto" frameborder="0" id="window3"></iframe>
				</td>
			</tr>
		</table>

	</body>
</html>
