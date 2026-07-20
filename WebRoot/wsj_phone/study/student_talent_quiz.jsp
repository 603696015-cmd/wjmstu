<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
	<HEAD>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>">
		<title>中国食品安全培训网--管理端--查看试卷信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
body {
	height: 100%;
	margin-top: 10px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	font-size: 14px;
	font-family: Arial, Helvetica, sans-serif;
	text-align: center;
}

.main {
	background-color: #FFFFFF;
	width: 828px;
	margin-right: auto;
	margin-left: auto;
}

.title {
	font-family: Arial, Helvetica, sans-serif, "新宋体";
	font-size: 28px;
	width: 700px;
	font-weight: bolder;
	text-align: center;
	color: #f30;
	line-height: 40px;
}

.block {
	width: 100%;
	border: dotted 1px buttonface;
	text-align: center;
}

.block_name {
	width: 100%;
	font-weight: bolder;
	font-size: 20px;
	text-align: left;
}

.block_desc {
	width: 98%;
	background-color: #DDDDDD;
	font-size: 15px;
	height: 24px;
	border: solid 1px buttonface;
	padding-top: 5px;
	text-align: left;
}

.question {
	width: 95%;
	border: dotted 1px buttonface;
	text-align: left;
}

.question1 {
	width: 90%;
	border: dotted 1px buttonface;
	text-align: left;
	font: 12px;
}

.answer {
	background-color: #DDDDDD;
	padding-top: 5px;
	padding-bottom: 5px;
	border: solid 1px buttonface;
}

.inputOver {
	height: 24px;
	padding: 2px 2 0 2;
	padding-top: 3px;
	border: 1px solid #dea303;
	background: url(exam_paper/input_bg3_.jpg);
	font-size: 12px;
	color: #000;
	cursor: pointer;
}

.input {
	height: 24px;
	padding: 2px 2 0 2;
	padding-top: 3px;
	border: 1px solid #adb9c2;
	background: url(exam_paper/input_bg3.jpg);
	font-size: 12px;
	color: #000;
	cursor: pointer;
}

.regbutton3 {
	cursor: pointer;
	margin-top: 3px;
	margin-bottom: 2px;
	PADDING-RIGHT: 0px;
	PADDING-LEFT: 0px;
	FONT-SIZE: 18px;
	PADDING-BOTTOM: 0px;
	PADDING-TOP: 0px;
	FONT-FAMILY: "黑体" color :     #000;
	width: 135px;
	border: 0px;
	height: 40px;
	background-image: url(exam_paper/btn_2.gif)
}

.menu {
	background-color: #f7f7f7;
	line-height: 22px;
	text-align: right;
	padding: 5px;
	position: absolute;
	width: 85px;
	top: 15px;
	visibility: visible;
	z-index: 400;
	border: green 1px solid;
	left: 86%;
}
</style>
	</HEAD>
	<body onload="myload();">
		<div class="main">
			<div style="width: 195px; float: left">
				<img src="images/exam/d_1.jpg" border="0" width="195" height="92" />
			</div>

			<div
				style="background-image: url('images/exam/d_1_bg.jpg'); height: 92px; width: 552px; float: left; padding-top: 50px; text-align: center">
				考试时间：
				<s:property value="examPaper.during" />
				分钟 &nbsp;&nbsp;&nbsp; 考生：
				<s:property value="#session.realname" />
				&nbsp;&nbsp;&nbsp; 总分 ：
				<s:property value="examPaper.ep_tscore" />
				分 &nbsp;
			</div>
			<div style="height: 92px; float: right; width: 81px;">
				<img src="images/exam/d_2.jpg" width="81" height="92" />
			</div>
			<div class="contentcenter">
				<div style="text-align: center; width: 100%">
					客观题总分：
					<s:property value="examPaper.epKscore" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主观题总分：
					<s:property value="examPaper.epZscore" />
				</div>
				<div>
					<p class="title">
						&nbsp;
						<s:property value="examPaper.title" />
					</p>
				</div>
				<br />
				<div
					style="padding: 2px; border: 0px; width: 788px; margin: 4px 8 4 8; margin-top: 0px; padding-left: 10px; font-size: 12px;">
					<s:property value="examPaper.description" />
					<br>
					<hr width="760" size="1" noshade="noshade" class="line" />
				</div>
				<br />
			</div>
			<SCRIPT type="text/javascript">
	function showBlocks(id){
		for(var i = 0 ; i < <s:property value="examPaper.epBlocks.size"/>;i++){
		document.getElementById("block_"+i).style.display="none";
		 document.getElementById("b_t_"+i).className ="input";
		 document.getElementById("b_t2_"+i).className ="input";
		}
		document.getElementById("b_t_a").className ="input";
		document.getElementById("b_t2_a").className ="input";
		document.getElementById("block_"+id).style.display="block";
		document.getElementById("b_t_"+id).className ="inputOver";
		document.getElementById("b_t2_"+id).className ="inputOver";
	}
	function showAllBlocks( ){
		for(var i = 0 ; i < <s:property value="examPaper.epBlocks.size"/>;i++)
		{
		document.getElementById("block_"+i).style.display="block";
		document.getElementById("b_t_"+i).className ="input";
		document.getElementById("b_t2_"+i).className ="input";
		}
	 	document.getElementById("b_t_a").className ="inputOver";
		document.getElementById("b_t2_a").className ="inputOver";
	}
	/*var totalTime=<s:property value="examPaper.during"/>*60;
	var nowPassTime = <s:property value="myExamPaper.passTime"/>;
	var passM = 
	function setPassTime(){
		var ptM = (totalTime-nowPassTime)/60;
		var ptS = 60-(totalTime - ptM*60);
		document.getElementById("ExamTime").innerHTML = (ptM+"").split(".")[0]+":"+(ptS+"").split(".")[0];
		nowPassTime=nowPassTime+1;
		window.setTimeout("setPassTime()",1000);
	}*/
var min = (<s:property value="examPaper.during"/>*60- <s:property value="myExamPaper.passTime"/>)/60;
var sec=0;
var passTime = '<s:property value="myExamPaper.passTime"/>'==''?0:'<s:property value="myExamPaper.passTime"/>';
var min1= "";
var sec1="";
//alert(passTime);
function setPassTime() {
	
	if(sec==0){
		min--;
		sec=60;
	}
	sec--;
	if(sec<10) sec1 = "0"+sec;
	else sec1=sec+"";
	if(min<10) min1 = "0"+min;
	else min1 = min+"";
	if(min<10) document.getElementById("ExamTime").style.color="#ff0000" ;
	document.getElementById("ExamTime").innerHTML = "<acronym title='就剩这么多时间啦，加油哦！'>"+(min1.split(".")[0])+":"+(sec1.split(".")[0])+"</acronym>";
	passTime++;
	document.getElementById("passTime").value= passTime ;
	if(min<=0&&sec<=0){
	alert("时间到，强制交卷!");
	submitQuizPaper();
	}
	window.setTimeout("setPassTime()",1000) ;
}	
	
	function saveQuizPaper(){
		/*quizform.action="student_talent_quiz_save.action";
		document.getElementById("passTime").value= passTime ;
		quizform.submit();*/
		var pa1 =getFormData();
		action("student_talent_quiz_save.action", pa1 ,"save3" );
		alert("保存成功！");
	}
	function submitQuizPaper(){
		quizform.action="student_talent_quiz_submit.action";
		quizform.submit();
	}
</SCRIPT>

			<div class="contentcenter">
				<div>
					<p align="center">
						<input type="button" id="b_t_a" class="inputOver" value="全部试题"
							onClick="showAllBlocks()" />
						<s:iterator value="examPaper.epBlocks" status="stepb">
							<input type="button" id="b_t_<s:property value="#stepb.index"/>"
								class="input" value="<s:property value="title" />"
								onClick="showBlocks(<s:property value="#stepb.index"/>)" />
						</s:iterator>
					</p>
				</div>
				<br />
				<div style="display:none" id="save3"> </div>
			</div>
			<form action="student_talent_quiz_submit.action" method="post" name="quizform"
				id="exam_show">
				<wysLib:quizPaper></wysLib:quizPaper>
				<input type="hidden" name="myExamPaper.examRoom.id"  value="<s:property value="qtroom.id"/>"/>
				<s:hidden name="myExamPaper.passTime" id="passTime" />
				<div>
					<p align="center">
						<input type="button" id="b_t2_a" class="inputOver" value="全部试题"
							onClick="showAllBlocks()" />
						<s:iterator value="examPaper.epBlocks" status="stepb2">
							<input type="button"
								id="b_t2_<s:property value="#stepb2.index"/>" class="input"
								value="<s:property value="title" />"
								onClick="showBlocks(<s:property value="#stepb2.index"/>)" />
						</s:iterator>
					</p>
				</div>
				<br />
				<div align="center">
					<s:hidden name="examPaper.id"></s:hidden>
					<input type="button" value="确认交卷" onclick="submitQuizPaper();" class="regbutton3">
					<input type="button" value="保存答卷" onclick="saveQuizPaper();" class="regbutton3">
				</div>

			</form>
			<br>
			<br>
		</div>
		<SCRIPT type="text/javascript">
var imgheight;
var imgwidth;
document.ns = navigator.appName == "Netscape";
window.screen.width>800 ? imgheight=510:imgheight=250;
window.screen.width>800 ? imgwidth=120:imgwidth=745;
function myload()
{
	if (navigator.appName == "Netscape")
	{
		document.mymenu.pageY=pageYOffset+window.innerHeight-imgheight;
		document.mymenu.pageX=+window.innerWidth-imgwidth;
	}
	else
	{
		mymenu.style.top=document.documentElement.offsetHeight-imgheight;
		mymenu.style.left=document.documentElement.offsetWidth-imgwidth;
	}
	leftmove();
	setPassTime();
	autoSave();
}

function leftmove()
{
	if(document.ns)
	{
		document.mymenu.top=pageYOffset+window.innerHeight-imgheight
		document.mymenu.left=pageXOffset+window.innerWidth-imgwidth;
	}
	else
	{
		mymenu.style.top=document.documentElement.scrollTop+document.documentElement.offsetHeight-imgheight;
		mymenu.style.left=document.documentElement.scrollLeft+document.documentElement.offsetWidth-imgwidth;
	}
	setTimeout("leftmove();",80);
	
}

//=============ajax封装========================================
var request = false;
var component = null;
function createRequest() {
	if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		if (window.XMLHttpRequest) {
			request = new XMLHttpRequest();
		}
	}
	if (!request) {
		alert("Error initializing XMLHttpRequest!");
	}
}
function action(url, param, component) {
	this.component = component;
	createRequest();
	request.open("POST", url, true);
	request.onreadystatechange = action_cl;
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
	request.send(param);
}
function action_cl() {
	if (request.readyState == 4) {
		document.getElementById(component).innerHTML = "";
		document.getElementById(component).innerHTML = request.responseText;
	}
}
//=============ajax封装结束========================================
	
	var dataString = "";
	function addParam(name, value) {
        dataString += (dataString.length > 0 ? "&" : "")
            + escape(name).replace(/\+/g, "%2B") + "="
            + escape(value ? value : "").replace(/\+/g, "%2B");
    }
	function getFormData(){
	dataString = "";
    var elemArray = quizform.elements;
    for (var i = 0; i < elemArray.length; i++) {
        var element = elemArray[i];
        var elemType = element.type.toUpperCase();
        var elemName = element.name;
        if (elemName) {
            if (elemType == "TEXT"
                    || elemType == "TEXTAREA"
                    || elemType == "PASSWORD"
                    || elemType == "HIDDEN")
                addParam(elemName, element.value);
            else if (elemType == "CHECKBOX" && element.checked)
                addParam(elemName, 
                    element.value ? element.value : "");
            else if (elemType == "RADIO" && element.checked)
                addParam(elemName, element.value);
            else if (elemType.indexOf("SELECT") != -1)
                for (var j = 0; j < element.options.length; j++) {
                    var option = element.options[j];
                    if (option.selected)
                        addParam(elemName,
                            option.value ? option.value : option.text);
                }
        }
    }
    return dataString;
}
var a_time = 0
function autoSave(){
		var pa1 =getFormData();
		if(a_time%10==9){
			//alert(pa1+a_time);
			action("student_talent_quiz_save.action", pa1 ,"save3" );
		}
		a_time++;	
		window.setTimeout("autoSave()",1000) ;
	}
</SCRIPT>
		<div id=mymenu class="menu">
			<font color="green" size=1>答题剩余时间</font>
			<div id="ExamTime"
				style="background-color: #FFCC00; HEIGHT: 20px; text-align: center; color: red; font-size: 14px;">
				s
			</div>
			<div id="ExamSubmit1" class="SubmitShow">
				<input class=inputOver onClick="submitQuizPaper();"
					style="CURSOR: pointer" type=button value=完成答卷 name=submit1>
					<input class=inputOver onClick="saveQuizPaper();"
					style="CURSOR: pointer" type=button value=保存答卷 name=submit1>
			</div>
		</div>
		<!--<script type="text/javascript"></script>-->
	
	</body>
</HTML>