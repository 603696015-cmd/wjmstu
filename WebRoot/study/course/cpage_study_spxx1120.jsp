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
<html>
	<head>
		<base href="<%=basePath%>" target="_self" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script src="js/jquery.alerts.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/course_spxx.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script>
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="js/cvideo/cvideoWrite.js"></script>
		<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="css/jquery.zdy.dialog.css" rel="stylesheet" type="text/css" media="screen" />
		<title>商务汉语学习系统</title>
		<style type="text/css">
<!--
body {
	background-color: #033B5E;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(images/images1113/xuexiye.png);
	background-position: center top;
	background-repeat: repeat-y;
}

.STYLE1 {
	color: #FFFFFF;
	font-size: 16px;
	font-weight: bold;
}
#stem_Voc {
	position:absolute;
	top:50%;
	left:50%;
	margin:-150px 0 0 -70px;
	width:400px;
	height:300px;
	background:url(images/datigaiban/text_bg.png) ;
}

#view_Voc {
	position:absolute;
	top:50%;
	left:50%;
	margin:-150px 0 0 -70px;
	width:400px;
	height:300px;
	background:url(images/datigaiban/text_bg.png) ;
}
-->
</style>
		<script type="text/javascript">
			var second=1000; //间隔时间1秒钟
			var _cpst;
			var needsetCp = true;
			var _cvideo;
			function myload_(status){
				var from = 0;
				if(!<s:property value="myCPage.passed"/>)
					from = <s:property value="myCPage.passtime"/>;
				_cvideo = new CourseVideo(1,utf8to16(base64decode("<s:property value="coursePage.page_url_Encoder"/>")), 60*60);
				_cvideo.show("page_file");
				
				_cpst=new CourseStudy(<s:property value="course.classid"/>,<s:property value="course.id"/>, <s:property value="coursePage.id"/>,
					 <s:property value="myCPage.passtime"/>,
					 <s:property value="myCPage.cpage.during"/>*60,
					 <s:property value="coursePage.queryTime"/>,
					 <s:property value="myCPage.passtime2"/>,
					 <s:property value="studyCourseRecordId"/>);
				_cpst.passed2=<s:property value="myCPage.passed2"/>;
				_cpst.durtimediv="timer3";
				_cpst.realtimediv="timer2";
				_cpst.processdiv="processDiv3";
				_cpst.studyinfo_time=<s:property value="#session.studyinfo_time"/>;
				_cpst.init();
				var classid = <s:property value="course.classid"/>;
				//classid为2010或者2011的是学拼音或者学汉字培训班
				if(classid!=2010 && classid!=2011){
					_cpst.intelligent_learn_begin();
				}
				//_cpst.autoSetCprocess();
				document.getElementById("next_course").src="images/bf/anniu_32.png";
				
				if(<s:property value="initCompliance"/>){
					complianceInit();
				}else{
					loadInterval();
				}
				
			}
			function complianceInit(){
				document.getElementById("next_course").src="images/bf/anniu_32.gif";
				$('#next_course').bind("click",function(){
					$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
						type:"post",
						url:"course_checkExamRoom.action",
						data: {
							"myCPage.cpid":<s:property value="coursePage.id"/>, 
							"myCPage.courseid":<s:property value="course.id"/>, 
							"myCPage.classid":<s:property value="classid"/>,
							"x":Math.random
							}, 
						success:
							function (data) {
								var jsondata = eval("("+data+")");
								if(jsondata.flag==1){//本章节绑定考试
									document.getElementById("choose_menu").href="quizpaperinit_byepid_wjm.action?examRoom.id="+jsondata.roomid+"&examPaper.id="+jsondata.epid+"&elclass.id=<s:property value='course.classid'/>&course.id=<s:property value='course.id'/>&coursePage.id=<s:property value='coursePage.id' />&datetime="+new Date();
								}else{//下一个章节学习
									var nextCpid = jsondata.cpid;
									if(nextCpid !=-1){
										document.getElementById("choose_menu").href="course_study.action?course.isLogout=1&course.id=<s:property value='course.id'/>&classid=<s:property value='course.classid'/>&elclass.id=<s:property value='course.classid'/>&coursePage.id="+nextCpid+"&datetime="+new Date();
									}else{
										alert("这是最后一个章节了,This is the last chapter of the course");
										return ;
									}
								}
							}
					});
				});
			}
			
			var timeObj;
			function loadInterval(){
				 timeObj= setInterval('check_pass()',second);
			}
			
			
			window.onbeforeunload=function(){
				/**
				if(<s:property value="coursePage.getcredit"/>!=1){
					if(_cpst.passtime2>=<s:property value="myCPage.cpage.during"/>*60){
						if(_cpst.passed2==0){
							alert("你还有考试没过！");
						}
					}
				}
				*/
				window.event.returnValue="确定退出本次学习？";
			}
			window.onunload=function(){
				_cpst.exitStudy(); 
				var classid = <s:property value="course.classid"/>;
				//classid为2010或者2011的是学拼音或者学汉字培训班
				if(classid!=2010 && classid!=2011){
					_cpst.intelligent_learn_end();
				}
				if(window.opener){
					window.opener.refresh1();
				}
			}
			
	var status=1;
	var course_status;
	function check_pass(){
	$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
		url:"course_checkPass.action",data: {
		"myCPage.cpid":<s:property value="coursePage.id"/>, 
		"x":Math.random
		}, success:
		function (data) {
			var d = eval("("+data+")");
			if(d.msg==1){
			    window.clearInterval(timeObj);
			    timeObj = null;
				document.getElementById("next_course").src="images/bf/anniu_32.gif";
				course_status=document.getElementById("next_course").src;
				
				if(status==1){//章节学习通过
				$('#next_course').bind("click",function(){
					$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
						type:"post",
						url:"course_checkExamRoom.action",
						data: {
							"myCPage.cpid":<s:property value="coursePage.id"/>, 
							"myCPage.courseid":<s:property value="course.id"/>, 
							"myCPage.classid":<s:property value="classid"/>,
							"x":Math.random
							}, 
						success:
							function (data) {
								var jsondata = eval("("+data+")");
								if(jsondata.flag==1){//本章节绑定考试
									//var mw = window.open("quizpaperinit_byepid.action?course.isLogout=1&coursePage.id=<s:property value='coursePage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>&myCPage.cpid=<s:property value='coursePage.id'/>&examRoom.id=1476&examPaper.id="+jsondata.epid+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
								//	if (window.screen){ 
									//	mw.moveTo(0, 0);
									//	mw.resizeTo(screen.availWidth,screen.availHeight);
									//	}
									document.getElementById("choose_menu").href="quizpaperinit_byepid_wjm.action?examRoom.id="+jsondata.roomid+"&examPaper.id="+jsondata.epid+"&elclass.id=<s:property value='course.classid'/>&course.id=<s:property value='course.id'/>&coursePage.id=<s:property value='coursePage.id' />&datetime="+new Date();
									//document.getElementById("choose_menu").href="quizpaperinit_byepid.action?myCPage.classid=<s:property value='course.classid'/>&course.isLogout=1&coursePage.id=<s:property value='coursePage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>&myCPage.cpid=<s:property value='coursePage.id'/>&examRoom.id="+jsondata.roomid+"&examPaper.id="+jsondata.epid+"&datetime="+new Date();
									//quizpaperinit_byepid_wjm.action?examRoom.id="+jsondata.roomid+"&examPaper.id="+jsondata.epid+"&elclass.id=<s:property value='course.classid'/>&course.id=<s:property value='course.id'/>&coursePage.id=<s:property value='coursePage.id' />
									//setdisable();
								}else{//下一个章节学习
									//window.href="course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>";
									//window.open("course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>","course_exam_6","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
									//window.close();	
									//document.getElementById("choose_menu").href="course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>";
									var nextCpid = jsondata.cpid;
									if(nextCpid !=-1){
										document.getElementById("choose_menu").href="course_study.action?course.isLogout=1&course.id=<s:property value='course.id'/>&classid=<s:property value='course.classid'/>&elclass.id=<s:property value='course.classid'/>&coursePage.id="+nextCpid+"&datetime="+new Date();
									}else{
										alert("这是最后一个章节了,This is the last chapter of the course");
										return ;
									}
								}
							}
					});
				})}
			}else if(d.msg =="error"){
				alert("学习进度信息,停止记录！将关闭页面！");
				window.onbeforeunload=null;
				window.onunload=null;
				window.clearInterval(this.setIt);
				window.close();
			}
		}});
}
 			
				 
	function saveToUrl(obj){
		$(obj).attr("href",$(obj).attr("href"));
		return true;
	}

	function check_back(){
			$.ajax({async:false,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
			url:"course_checkExamRoom.action",data: {
			"myCPage.cpid":<s:property value="coursePage.id"/>, 
			"myCPage.courseid":<s:property value="course.id"/>, 
			"myCPage.classid":<s:property value="course.classid"/>,
			"x":Math.random
			}, success:
			function (data) {
			var jsondata = eval("("+data+")");
			if(jsondata.flag==1){
				var mw = window.open("quizpaperinit_byepid_wjm.action?examRoom.id="+jsondata.roomid+"&examPaper.id="+jsondata.epid+"&elclass.id=<s:property value='course.classid'/>&course.id=<s:property value='course.id'/>&coursePage.id=<s:property value='coursePage.id' />&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				//var mw = window.open("quizpaperinit_byepid.action?course.isLogout=1&coursePage.id=<s:property value='coursePage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>&myCPage.cpid=<s:property value='coursePage.id'/>&examRoom.id="+jsondata.roomid+"&examPaper.id="+jsondata.epid+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
				if (window.screen){ 
					mw.moveTo(0, 0);
					mw.resizeTo(screen.availWidth,screen.availHeight);
				}
				//setdisable();
			}else{
				//window.href="course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>";
				window.open("course_study.action?course.isLogout=1&coursePage.id=<s:property value='cpage.id' />&course.id=<s:property value='course.id'/>&course.classid=<s:property value='course.classid'/>","_self");
				//window.close();	
			}
	}});
	
	}
		</script>
		<script type="text/javascript">
/**
js调用flash内置函数需要访问官网进行授权
http://www.macromedia.com/support/documentation/cn/flashplayer/help/settings_manager04.html
*/

//停止OK

function js_stop(){
	var image = document.getElementById("pause_play");
	image.src = "images/bf/anniu_36.png";
	video.Rewind();
	
}
//播放暂停切换OK

function js_pause_play(){
	var image = document.getElementById("pause_play");
	if(video.IsPlaying()){
		image.src = "images/bf/ann_36.png";
		video.StopPlay();
	}else{
		image.src = "images/bf/anniu_36.png";
		video.play();
	}
}
//从头开始播放 OK

function js_reset(){
	var image = document.getElementById("pause_play");
	image.src = "images/bf/anniu_36.png";
	video.Rewind();
	video.play();
}

function showV(){
	/**
	var courseid = <s:property value="course.id" />;
	var iWidth = 1000; //弹出窗口的宽度;
	var iHeight = 600 ; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	window.open("vocabulary_search.action?vocabulary.status=1&course.id="+courseid,"词汇列表",'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
	*/
	/**
	var courseid = <s:property value="course.id" />;
	window.location.href='wjm_user_center.action?module=vocabulary_search.action?course.id='+courseid;
	*/
	if($("#stem_Voc").css("display")=="block"){
		closeVoc();
	}else{
		var i = document.getElementById("add").value;
		var courseid = <s:property value="course.id" />;
		$.ajax({async:true,  //使用同步请求，因为异步请求不能将返回值传给全局变量；   
			type:"post",
			url:"vocabulary_search2.action",data:{"course.id":courseid},success:function (data) {
		   		if(i==0){
					$("#stem_Voc").append(data);
				}
		   		$("#stem_Voc").css("display","block");
		},error:function(msg){
			//alert("出现出错，出现此错误请检查本机网络，或联系管理员检查服务！");
			//$("#loading_"+blockid).css("display","none");
		}});
	}
//	window.location.href='wjm_user_center.action?module=vocabulary_search.action?vocabulary.wordid=-1';
}
function closeVoc(){
	if($("#stem_Voc").css("display")=="block"){
		$("#stem_Voc").css({  display:"none" });
	}else{
		$("#stem_Voc").css({  display:"block" });
	}
	if($("#view_Voc").css("display")=="block"){
		$("#view_Voc").css({  display:"none" });
		$("#stem_Voc").css({  display:"none" });
	}
	document.getElementById("add").value = 1;
}
</script>
		<style type="text/css">
.daan {
	font-weight: bold;
	color: red;
	padding-left: 20px;
}
</style>
	</head>

	

	<body OnLoad="myload_('<s:property value="status"/>');">
		<table width="998" height="605" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td height="507">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:85px;">
						<tr>
							<td height="507" align="right" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="109" height="36">&nbsp;										</td>
										<td>&nbsp;
											
										</td>
										<td>&nbsp;
											
										</td>
									</tr>
									<tr>
										<td height="463">&nbsp;
											<div id="stem_Voc" style="display:none;overflow-x:hidden;overflow-y:auto; scrollbar-face-color: #dbebfe; scrollbar-shadow-color: #b8d6fa; scrollbar-highlight-color: #ffffff; scrollbar-3dlight-color: #dbebfe; scrollbar-darkshadow-color:#458ce4; scrollbar-track-color: #ffffff; scrollbar-arrow-color: #458ce4  ">
												<div>
													<span style="margin-left: 360px; color: #000;"><a  onclick="closeVoc();">
														<img src="images/datigaiban/wrong.png" width="15" height="15"  />
													</a></span>
												</div>
												<input type="hidden" value="0" id="add"/>
											</div>
										
											<div id="view_Voc" style="display:none;overflow-x:hidden;overflow-y:auto; scrollbar-face-color: #dbebfe; scrollbar-shadow-color: #b8d6fa; scrollbar-highlight-color: #ffffff; scrollbar-3dlight-color: #dbebfe; scrollbar-darkshadow-color:#458ce4; scrollbar-track-color: #ffffff; scrollbar-arrow-color: #458ce4  ">
												<div>
													<span style="margin-left: 380px; color: #000;"><a  onClick="closeVoc();">
														<img src="images/datigaiban/wrong.png" width="15" height="15"  />
													</a></span>
												</div>
											</div>
										</td>
										<td width="785" align="left" valign="top" style="padding-top:3px;">
											<div style="height: 455px; width: 778px;" id="page_file"></div>
									  </td>
										<td>&nbsp;										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="100" valign="top">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="margin-top: 10px;">
						<tr>
							<td width="288" height="50" align="center" valign="middle">&nbsp;
								
							</td>
							<!-- 
							<td width="85" align="left" valign="middle">
								<input type="image"
									src="images/bf/ann_37.png"
									onmousedown="this.src='images/bf/anniu_27.png'"
									onmouseup="this.src='images/bf/ann_37.png'"
									onclick="check_back()" />
							</td>
							 -->
							
							<td width="85" align="center" valign="middle">
								<!-- 
								<input type="image"
									src="images/bf/anniu_35.png"
									onmousedown="this.src='images/bf/ann_35.png'"
									onmouseup="this.src='images/bf/anniu_35.png'"
									onclick="js_stop();" />
								 -->
								<img src="images/bf/anniu_35.png"
									onmousedown="this.src='images/bf/ann_35.png'"
									onmouseup="this.src='images/bf/anniu_35.png'"
									onclick="js_stop();" width="50" height="50" />
							</td>
							<td width="85" align="center" valign="middle">
								<!-- 
								<input type="image"
									src="images/bf/anniu_36.png"
									id="pause_play" onclick="js_pause_play();" type="image"
									src="images/bf/anniu_36.png"
									old="images/bf/ann_36.png" />
								 -->
								<img src="images/bf/anniu_36.png" width="50" height="50" id="pause_play"
											 onclick="js_pause_play();" />
							</td>
							<td width="85" align="center" valign="middle">
								<!-- 
								<input type="image"
									src="images/bf/reset.png"
									onmousedown="this.src='images/bf/reseth.png'"
									onmouseup="this.src='images/bf/reset.png'"
									onClick="js_reset()" />
								 -->
								<img src="images/bf/reset.png"
									onmousedown="this.src='images/bf/reseth.png'"
									onmouseup="this.src='images/bf/reset.png'"
									onClick="js_reset()" width="50" height="50" />
							</td>
							
							<td width="85" align="center" valign="middle">
								<A id="choose_menu" onClick="return saveToUrl(this);"> <img
										src="images/bf/anniu_32h.png"
										id="next_course" width="50" height="50" /> </A>
							</td>
							<td width="85" align="center" valign="middle">
								<img src="images/bf/cidian.png"
									onClick="showV();" width="50" height="50" />
							</td>
							<td align="center" valign="middle">&nbsp;
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
