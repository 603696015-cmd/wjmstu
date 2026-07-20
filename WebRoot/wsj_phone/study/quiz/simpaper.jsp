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
		<title>中国食品安全培训网--模拟考试</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="ca che-control" content="no-cache">
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

.answer_wh {
	background-color: red;
	padding-top: 5px;
	padding-bottom: 5px;
	border: solid 1px buttonface;
}
.answer_yd {
	background-color: #9999ff;
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
	<body oncontextmenu="return false" 
oncopy="return false" 
oncut="return false" 
onpaste="return false">
		<div class="main">
			<div style="width: 195px; float: left">
				<img src="images/exam/d_4.jpg" border="0" width="195" height="92" />
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
var passTime = <s:property value="myExamPaper.passTime"/>;
var min1= "";
var sec1="";

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
	window.setTimeout("setPassTime()",1000) ;
}	

window.onblur=function(){
	if(document.activeElement==document.body)
		setTimeout("window.focus()",100)
	else
	{
		document.activeElement.onblur = function(){
		setTimeout("var oActive=document.activeElement;oActive.onblur=null;window.focus();oActive.focus();",100)};
	}
}
window.moveTo(0,0);
window.resizeTo(window.screen.availWidth,window.screen.availHeight);
	function saveQuizPaper(){
		quizform.action="simpaper_save.action";
		document.getElementById("passTime").value= passTime ;
		quizform.submit();
	}
	function submitQuizPaper(){
		quizform.action="simpaper_submit.action";
		quizform.submit();
	}
	function autoSaveSim(){
		
	
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
			</div>
			<form action="quiz_submit.action" method="post" name="quizform"
				id="exam_show">
				<wysLib:quizPaper></wysLib:quizPaper>
				<s:hidden name="myExamPaper.passTime" id="passTime" />
				<input type="hidden" name="myExamPaper.course.id" value="<s:property value="course.id" />">
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
		function showYw(obj) {
				obj = obj.parentNode;
				if(obj.className!='answer_wh')obj.className='answer_wh';
				else if(obj.className=='answer_wh'){
					var inputs =obj.childNodes;
					var value='';
					for(var i=0;i<inputs.length;i++){
						if(inputs[i].type+"" !='undefined'){
						if(inputs[i].type.toUpperCase()=='RADIO'){
							if(inputs[i].checked) value= inputs[i].value;
						}
						if(inputs[i].type.toUpperCase()=='CHECKBOX'){
							if(inputs[i].checked) value+= inputs[i].value;
						}
						if(inputs[i].type.toUpperCase() == "TEXT"
		                    || inputs[i].type.toUpperCase() == "TEXTAREA"
		                    || inputs[i].type.toUpperCase() == "PASSWORD"
		                    || inputs[i].type.toUpperCase()== "HIDDEN"){
                   		 if(inputs[i].checked) value = inputs[i].value;
                    	}
                    	}
                    }
                    if(trim(value )!='')
					obj.className='answer_yd';
					else
					obj.className='answer';
				}				
			}
			 function ltrim(s)
			 { 
			return s.replace(/(^\s*)/g, "");
			 } 
			function rtrim(s)
			 { 
			  return s.replace(/(\s*$)/g, "");
			 } 
			 function trim(s) { 
			    //s.replace(/(^\s*)|(\s*$)/g, "");
			  return rtrim(ltrim(s)); 
			 } 
			function showYd(obj) {
				obj = obj.parentNode;
				if(obj.className!='answer_wh')obj.className='answer_yd';
			}

var imgheight;
var imgwidth;
document.ns = navigator.appName == "Netscape";
window.screen.width>800 ? imgheight=510:imgheight=250;
window.screen.width>800 ? imgwidth=120:imgwidth=745;
function myload()
{
		setPassTime();
		scall();
}

function sc4(){
	document.getElementById("mymenu").style.top=document.body.scrollTop;
	document.getElementById("mymenu").style.left=document.body.scrollLeft
	+document.body.clientWidth-document.getElementById("mymenu").offsetWidth;
}
function scall(){
	sc4();
}
window.onscroll=scall;
window.onresize=scall;
window.onload=myload;

</SCRIPT>
		<div id="mymenu" class="menu">
			<font color="green" size=1>答题剩余时间</font>
			<div id="ExamTime"
				style="background-color: #FFCC00; HEIGHT: 20px; text-align: center; color: red; font-size: 14px;">
				
			</div>
			<div id="ExamSubmit1" class="SubmitShow">
				<input class=inputOver onClick="submitQuizPaper();"
					style="CURSOR: pointer" type=button value=完成答卷 name=submit1>
					<input class=inputOver onClick="saveQuizPaper();"
					style="CURSOR: pointer" type=button value=保存答卷 name=submit1>
			</div>
		</div>
		<script type="text/javascript">myload();</script>
	
	</body>
</HTML>