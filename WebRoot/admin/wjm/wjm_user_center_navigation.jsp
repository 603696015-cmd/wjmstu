<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="js/jquery.js" type="text/javascript"></script>
		<script src="js/jquery.ui.draggable.js" type="text/javascript"></script>
		<script src="js/jquery.alerts.js" type="text/javascript"></script>
		<script src="js/jquery.pop.js" type="text/javascript"></script>
		<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css"
			media="screen" />
		<link href="css/jquery.zdy.dialog.css" rel="stylesheet"
			type="text/css" media="screen" />
		<style>
body,div,ul,li,img,a,h2,h1,h3,p,input,form,dl,dt,dd {
	margin: 0;
	padding: 0;
	border: 0;
}

body {
	font-size: 12px;
	font-famile: Arial, '宋体', sans-serif;
	background: url(images/cisco_img/bg.jpg) no-repeat;
}

ul,li {
	list-style: none;
}

a {
	text-decoration: none;
	color: #fff;
}

.clearFix {
	zoom: 1;
}

.clearFix:after {
	clear: both;
	display: block;
	visibility: hidden;
	height: 0;
	line-height: 0;
	content: "";
}

#all {
	width: 650px;
	margin: 360px auto 0px;
	overflow: auto;
	zoom: 1;
}

#all div {
	float: left;
	overflow: hidden;
}

#all div a img {
	_display: inline-block;
}

#all div a img:hover,#all div a img.hover {
	border: 1px red solid;
	zoom: 1;
}

#top_three a img {
	_display: inline-block;
}

#top_three a img:hover,#top_three a img.hover {
	border: 1px red solid;
	zoom: 1;
}

.main {
	position: absolute;
	width: 100%;
	top: 0px;
	bottom: 0;
	overflow: auto;
}

.main .main_1 {
	position: absolute;
	width: 100%;
	top: 0px;
	bottom: 0;
	overflow: auto;
	background:url(images/cisco_img/bg2.jpg) no-repeat;
}

* html .main {
	background: #F90;
	position: static;
	height: 100%;
} /*for ie6*/
</style>
		<script type="text/javascript">
function load(){
	/*var inDingjiRoom = false;
			var time = 0;
			var status = 1;
			var epid = <s:property value="dingjiExamRoom.examPaper.id" />;
			if(status == -1){//表示定级异常退出
				if(!inDingjiRoom ){
					if(time == 1){
						if(window.confirm("您上次定级异常退出,点击确定进行重新第1次定级考试Your last grading abnormal exit, click OK to re-grading exam 1st")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
						}
					}else if(time == 2){
						alert("您上次定级异常退出,点击确定进行重新第2次定级考试Your last grading abnormal exit, click OK to re-grading exam 2st");
						enterEroom(<s:property value="roomid" />,epid,0,0,0,2);
					}
				}
			}else {
				if(!inDingjiRoom ){
				debugger;
					if(time == 0){
						if(window.confirm("您还未定级,点击确定进行第1次定级考试You have not grading, click OK to 1st grading exams")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
						}
					}
				}else{
					var loginType = <s:property value="intelligentLogin.loginType" />;
					if(loginType == 1){
						jjAlert("您今天登录加0.1分,add 0.1 point for login");
						//alert("您今天登录加0.1分,add 0.1 point for login");
					}else if(loginType == -1){
						jjAlert("您已3天未登录减0.9分,Over 3 days no login,minus 0.9 point");
						//alert("您已3天未登录减0.9分,Over 3 days no login,minus 0.9 point");
					}
				}
			}*/

	var inDingjiRoom = <s:property value="inDingjiRoom" />;
	
	var time = <s:property value="elUserClassification.time" />;
	var status = <s:property value="elUserClassification.status" />;
	var epid = <s:property value="dingjiExamRoom.examPaper.id" />;
	
	//右下角弹窗
	var pop;
	if(inDingjiRoom){
		pop = new Pop("智能辅导分","wjm_user_center.action?module=showIntelligent.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />","您当前学习的等级为<s:property value="peixunBatch.nowClass.name" />,智能辅导分<s:property value="intelligentPoints" />分");
	}
	var init = <s:property value="init" />;
	if(init == 1){
		var loginType = <s:property value="intelligentLogin.loginType" />;
		if(loginType == 1){
			jjAlert("您今天登录加0.1分,add 0.1 point for login");
			//alert("您今天登录加0.1分,add 0.1 point for login");
		}else if(loginType == -1){
			jjAlert("您已3天未登录减0.9分,Over 3 days no login,minus 0.9 point");
			//alert("您已3天未登录减0.9分,Over 3 days no login,minus 0.9 point");
		}
	}
}
function closeIndex(){
	$.alerts.dialogClass = "style_1"; // set custom style class
	jConfirm("确定退出,Logout，YES or NO?", 'Information', function(r) {
		$.alerts.dialogClass = null; // reset to default
		if(r){
			window.parent.location.href="logout.action";
		}
	});
	/**
	if(window.confirm("确定退出LOGOUT，YES,NO?")){
		window.location.href="logout.action";
	}
	*/
}
function jjAlert(message){
	$.alerts.dialogClass = "style_1"; // set custom style class
	jAlert(message, 'Information', function() {
		$.alerts.dialogClass = null; // reset to default
	});
}

function enterEroom(erid,epid,classid,courseid,pageid,time){
			if(time!=undefined){
				window.parent.location.href="quizpaperinit_byepid_wjm.action?myroom.examroom.id="+erid+"&examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid+"&time="+time+"&noCpageTest=1";
				//var mw = window.open("quizpaperinit_byepid_wjm.action?myroom.examroom.id="+erid+"&examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid+"&time="+time+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			}else{
				window.parent.location.href="quizpaperinit_byepid_wjm.action?myroom.examroom.id="+erid+"&examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid+"&noCpageTest=1";
				//var mw = window.open("quizpaperinit_byepid_wjm.action?myroom.examroom.id="+erid+"&examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			}
			/**
			if (window.screen){ 
				mw.moveTo(0, 0);
				mw.resizeTo(screen.availWidth,screen.availHeight);
			}
			window.onbeforeunload=function(){
				window.event.returnValue="**********************************\n\n当前正在考试,不能离开本页面,以免造成错误,Do not shutdown the page,exam is in process\n\n**********************************";
			}
			*/
		}

</script>

		<!--淡入效果-->
		<script type="text/javascript">
$(document).ready(function(){
	$(".main").hover(function(){
	  $(".main_1").fadeIn(3000);
	  });
 //$(".btn1").hover(function(){
//  $("p").fadeOut(1000);
//  });
});
</script>

		<title>个人中心首页123456</title>

	</head>

	<body onload="load();">
		<div class="main">
			<div style="display: none;" class="main_1">
				<table width="1000" border="0" align="center" cellpadding="0"
					cellspacing="0" id="top_three">
					<tr>
						<td height="130">&nbsp;						</td>
						<td width="84">&nbsp;
							
						</td>
						<td width="84">&nbsp;
							
						</td>
						<td width="84" align="left">&nbsp;
							
						</td>
						<td width="84">&nbsp;
							
						</td>
					</tr>
					<tr>
						<td width="644">&nbsp;
							
						</td>
						<td height="84" colspan="4" align="center">
							<table border="0" align="left" cellpadding="0" cellspacing="0">
								<tr>
									<td width="84" height="82" align="left" valign="top">
										<a href="javascript:closeIndex();"><img
												src="images/cisco_img/cl_close.png" width="80" height="80"
												align="top" />
										</a>
									</td>
									<td width="84" align="left" valign="top">
										<a
											href="wjm_user_center.action?module=student_mypwdalterInit.action"><img
												src="images/cisco_img/password.png" width="80" height="80"
												align="middle" />
										</a>
									</td>
									<td width="84" align="left" valign="top">
										<a
											href="wjm_user_center.action?module=wjm_student_myalterInit.action"><img
												src="images/cisco_img/data.png" width="80" height="80"
												align="top" />
										</a>
									</td>
									
								</tr>
						  </table>
						</td>
					</tr>
				</table>
				<div style="clear: both;"></div>
				<div class="clearFix" id="all">
					
					
					
						
						
						<table border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:110px;">
  <tr>
    <td width="160" height="150" align="center" valign="top">
							<div>
								<a href="wjm_user_center.action"><img
										src="images/cisco_img/one.png" />
								</a>
	</div></td>
    <td width="160" height="150" align="center" valign="top"><div class="anniu">
								<a
									href="wjm_user_center.action?module=learnPinyin.action?elClass.id=2010"><img
										src="images/cisco_img/two.png" />
								</a>
	</div></td>
    <td width="160" height="150" align="center" valign="top"><div>
								<a
									href="wjm_user_center.action?module=learnHanzi.action?elClass.id=2011"><img
										src="images/cisco_img/three.png" />
								</a>
	</div></td>
    <td width="160" height="150" align="center" valign="top"><div>
								<!-- <a href="wjm_user_center.action?module=simulationExam.action"><img
										src="images/cisco_img/four.png" />
								</a> -->
								<a href="simulation_index.action"><img
										src="images/cisco_img/four.png" />
								</a>
	</div></td>
  </tr>
</table>

					
					
					
					</div>
					<div style="clear: both;"></div>
				</div>
			</div>
		</div>
		<s:if test="inDingjiRoom">
			<div id="pop" style="display: none;">
				<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

#pop {
	background: #fff;
	width: 260px;
	border: 1px solid #e0e0e0;
	font-size: 12px;
	position: fixed;
	right: 10px;
	bottom: 10px;
}

#popHead {
	line-height: 32px;
	background: #f6f0f3;
	border-bottom: 1px solid #e0e0e0;
	position: relative;
	font-size: 12px;
	padding: 0 0 0 10px;
}

#popHead h2 {
	font-size: 14px;
	color: #666;
	line-height: 32px;
	height: 32px;
}

#popHead #popClose {
	position: absolute;
	right: 10px;
	top: 1px;
}

#popHead a#popClose :hover {
	color: #f00;
	cursor: pointer;
}

#popContent {
	padding: 5px 10px;
}

#popTitle a {
	line-height: 24px;
	font-size: 14px;
	font-family: '微软雅黑';
	color: #333;
	font-weight: bold;
	text-decoration: none;
}

#popTitle a:hover {
	color: #f60;
}

#popIntro {
	text-indent: 18px;
	line-height: 160%;
	margin: 5px 0;
	color: #666;
}

#popMore {
	text-align: right;
	border-top: 1px dotted #ccc;
	line-height: 24px;
	margin: 8px 0 0 0;
}

#popMore a {
	color: #f60;
}

#popMore a:hover {
	color: #f00;
}
</style>
				<div id="popHead">
					<a id="popClose" title="关闭Close" style="color: red">关闭Close</a>
					<h2>
						温馨提示Tips
					</h2>
				</div>
				<div id="popContent">
					<dl>
						<dt id="popTitle">
							<a href="#">这里是参数</a>
						</dt>
						<dd id="popIntro">
							这里是内容简介
						</dd>
					</dl>
					<p id="popMore">
						<a target="_self"
							href="wjm_user_center.action?module=showIntelligent.action?elClass.id=<s:property value="peixunBatch.nowClass.id" />">查看
							»</a>
					</p>
				</div>
			</div>
		</s:if>
	</body>
</html>

