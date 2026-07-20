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
		<title>查看我的答卷</title>
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
td {font-size:12px;color:#333333;line-height:150%}
tr {background-color:expression((this.sectionRowIndex%2==0)?"#ffffff":"#f4f4f4")} 
</style>
	</HEAD>
	<body>
		<div class="main">
			<div style="width: 195px; float: left">
				<img src="images/exam/d_0.jpg" border="0" width="195" height="92" />
			</div>

			<div
				style="background-image: url('images/exam/d_1_bg.jpg'); height: 92px; width: 552px; float: left; padding-top: 50px; text-align: center">
				考试时间：
				<s:property value="examPaper.during" />
				分钟 &nbsp;&nbsp;&nbsp; 考生：
				<s:property value="#session.realname" />
				&nbsp;&nbsp;&nbsp;试卷满分 ：
				<s:property value="examPaper.ep_tscore" />
				分 &nbsp;
			</div>
			<div style="height: 92px; float: right; width: 81px;">
				<img src="images/exam/d_2.jpg" width="81" height="92" />
			</div>
			<div class="contentcenter">
				<div style="text-align: center; width: 100%">
					客观题总分：
					<s:property value="examPaper.ep_kscore" />
					客观题得分：<s:property value="examPaper.mepKscore" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					主观题总分：
					<s:property value="examPaper.ep_tscore-examPaper.ep_kscore" />
					主观题得分：<s:property value="examPaper.mepZscore" />
					考生得分：<s:property value="examPaper.mepZscore+examPaper.mepKscore" />
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
window.onload=scall;
	
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
				<wysLib:examPaperRead_view></wysLib:examPaperRead_view>
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
			<br>
		</div>
		<div id=mymenu class="menu">
			<div id="ExamSubmit1" class="SubmitShow">
					<input class=inputOver onClick="window.close();"
					style="CURSOR: pointer" type=button value=关闭 name=submit1>
			</div>
		</div>
	
	</body>
</HTML>