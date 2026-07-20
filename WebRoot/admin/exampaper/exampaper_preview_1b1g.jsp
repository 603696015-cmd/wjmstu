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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
		<title>五矿发展员工职业发展系统--管理端--查看试卷信息</title>
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
	background-color: #878C93;
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
.block{
	width: 100%;
	border: dotted 1px buttonface;
	text-align: center;
}
.block_name{
	width: 100%;
	font-weight:  bolder;
	font-size: 20px;
	text-align: left;
}
.block_desc{
	width: 98%;
	background-color: #DDDDDD;
	font-size: 15px;
	height: 24px;
	border: solid 1px buttonface;
	padding-top:5px;
	text-align: left;
}
.question{
	width: 95%;
	border: dotted 1px buttonface;
	text-align: left;
	display: none;
}
.question1{
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

</style>
<script type="text/javascript">
	var questions = new Array() ;
	function init(){
		var objs = document.getElementsByTagName("div");
		var i = 0;
		for(var j=0;j<objs.length; j++)
		{
			if(objs[j].className=="question"){
				questions[i]=objs[j];
				i++;	
			}
		}
	}
	var now = 0;
	function showQN(){
		for(var i = 0 ; i<questions.length;i++){
			if(now==i) questions[i].style.display="block";
			else
			questions[i].style.display="none";
		}
		now++;
	}
	function showQP(){
		for(var i = 0 ; i<questions.length;i++){
			if((now+1)==i) questions[i].style.display="block";
			else
			questions[i].style.display="none";
		}
		now--;
	}
</script>
	</HEAD>

	<body onLoad="init();showQN()">
		<div class="main">
			<div style="width: 195px; float: left">
				<a href="/"><img src="images/exam/d_1.jpg" border="0"
						width="195" height="92" /> </a>
			</div>
			
			<div
				style="background-image: url('images/exam/d_1_bg.jpg'); height: 92px; width: 552px; float: left; padding-top: 50px; text-align: center">
				考试时间：
				<s:property value="examPaper.during" />
				分钟 &nbsp;&nbsp;&nbsp; 考生：
				<s:property value="#session.realname" />
				&nbsp;&nbsp;&nbsp; 试卷满分 ：
				<s:property value="examPaper.ep_tscore" />
				考生得分 &nbsp;
			</div>
			<div style="height: 92px; float: right; width: 81px;">
				<img src="images/exam/d_2.jpg" width="81" height="92" />
			</div>
			<div class="contentcenter">
				<!--<div style="text-align: center; width: 100%">
					客观题总分：
					<s:property value="examPaper.epKscore" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主观题总分：
					<s:property value="examPaper.epZscore" />
				</div>-->
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
				<wysLib:epShow1b1></wysLib:epShow1b1>
				<div style="text-align: center;margin-top:40px; ">
					<input type="button" value="上一题" onClick="showQP();">
					<input type="button" value="下一题" onClick="showQN()"> 
				</div>
			</div>
		</div>
	</body>
</HTML>