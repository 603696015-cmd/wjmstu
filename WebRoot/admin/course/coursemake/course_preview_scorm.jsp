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
		<title>预览课程(scorm1.2)--<s:property value="course.name" /></title>
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
function setScoid(scoid){
	API.setScoid(scoid);
}
function initAPI()
{
   API = this.document.APIAdapter;
}
function toPreOrNext(x){
	if(x==1)
		document.location="course_preview_scorm.action?course.id=<s:property value="course.id"/>&scormcourse.navitype=next&scormcourse.nowScoid=<s:property value="scormcourse.scoid"/>"
	else
		document.location="course_preview_scorm.action?course.id=<s:property value="course.id"/>&scormcourse.navitype=prev&scormcourse.nowScoid=<s:property value="scormcourse.scoid"/>"
}
function showmenu(obj){
	if($("#catalog").css("display")=='none')
		$("#catalog").css("display","block");
	else
		$("#catalog").css("display","none");
	$("#catalog").css("left",$(obj).offset().left);
	$("#catalog").css("top",$(obj).offset().top);
}
</script>
	</head>
	<body style="overflow: visible" onload="initAPI();">
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
			<param name="SCOID" value="ccc">
			<param name="USERID" value="<s:property value="scormcourse.userid"/>">
			<param name="COURSEID"
				value="<s:property value="scormcourse.courseid"/>">
			<param name="CLASSID" value="preview">
			<param name="CID"
				value="<s:property value="course.id"/>">
			<comment>
			<applet code="org/adl/samplerte/client/APIAdapterApplet.class"
				archive="elearning.jar" codebase="<%=path%>" height="1"
				id="APIAdapter" name="APIAdapter" width="1" mayscript="true">
			</applet>
			</comment>
		</object>
		<table cellpadding="1" cellspacing="1" width="100%" height="100%">
			<tr>
				<td id="tree_list_td" valign="top" align="left" width="200px">
					<iframe width="200px" scrolling="auto" frameborder="0" height="100%" src="course_scorm_catalog.action?course.id=<s:property value="course.id"/>"></iframe>
				</td>
				<td width="5px">
					<img src="images/leftmenu/main_55.gif" style="cursor: hand"
						onclick="catalog_switch(this)" />
				</td>
				<td valign="top">
					<iframe style="z-index: 9999"
						src="blank.html"
						name="right" align="middle" width="100%" height="100%"
						scrolling="auto" frameborder="0" id="window3"></iframe>
				</td>
			</tr>
		</table>

	</body>
</html>
