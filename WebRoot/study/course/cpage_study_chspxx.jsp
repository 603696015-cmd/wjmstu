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
		<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css"
			media="screen" />
		<link href="css/jquery.zdy.dialog.css" rel="stylesheet"
			type="text/css" media="screen" />
		<title>商务汉语学习系统</title>
		
		<style type="text/css">
<!--
a {
	text-decoration: none;
}

.cntSeparator {
	font-size: 20px;
	margin: 10px 7px;
	color: #000;
	
}

body {
	background-color: #033B5E;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(images/images1113/chspxx.jpg);
	background-position: center top;
	background-repeat: repeat-y;
}

.STYLE1 {
	color: #FFFFFF;
	font-size: 16px;
	font-weight: bold;
}

#stem_text {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -150px 0 0 -200px;
	width: 400px;
	height: 300px;
	background: url(images/datigaiban/text_bg.png) no-repeat 0 0;
}

#stem_Voc {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -150px 0 0 -200px;
	width: 400px;
	height: 300px;
	background-color: #e7e8ed;
}

#view_Voc {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -150px 0 0 -200px;
	width: 400px;
	height: 300px;
	background-color: #e7e8ed;
}

#dt_zhang tr td {
	height: 54px;
	line-height: 54px;
}

#dt_zhang tr td a {
	color: #076179;
	font-size: 24px;
	font-weight:bold;
}

#dt_zhang tr td a:hover {
	color: red;
	font-size: 26px;
}
#dt_zhang tr .xing{ 
	width:42px;
	color: #000;
	padding:0;
	font-weight: bold;
	background: url(images/xing_g.png) no-repeat center 12px;
	text-align:center;
 }

.xxdt{text-align:center; vertical-align:middle; height:50px;line-height:50px; width:130px;background:url(images/quiz/startquiz.png) no-repeat center center; font-weight:bold;}
.xxdt:hover{color:red;}
.xxdt_g{text-align:center; vertical-align:middle; height:50px;line-height:50px; width:130px;background:url(images/quiz/startquiz.gif) no-repeat center center; font-weight:bold;}
.xxdt_g:hover{color:red;}
.xxdt_h{text-align:center; vertical-align:middle; height:50px;line-height:50px; width:130px;background:url(images/quiz/startquiz_h.png) no-repeat center center; font-weight:bold;}
.xxdt_h:hover{color:red;}
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
//elstuffs/1779/1879/1883.swf
//
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
				document.getElementById("next_course").src="images/bf/anniuch_32h.png";
				
				if(<s:property value="initCompliance"/>){
					complianceInit();
				}else{
					loadInterval();
				}
				
			}
			function complianceInit(){
				var roomSize = <s:property value="myCPage.examRooms.size()" />;
				//var roomSize =0;
				if(roomSize!=undefined && roomSize>0){
					var roomid;
					var epid;
					var title;
					for(var i=1;i<roomSize+1;i++){
						//document.getElementById(""+i).src="images/bf/startquiz.png";
						$("#"+i).css("background","url(images/quiz/startquiz.png) no-repeat center center");
						$('#'+i).bind("click",{index:i},function(event){
							roomid = document.getElementById(""+event.data.index).alt;
							epid = document.getElementById(""+event.data.index).epid;
							title = document.getElementById(""+event.data.index).name;
							if(window.confirm("开始进入练习,Confirm To Start Exam")){
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
												document.getElementById("haha"+event.data.index).href="quizpaperinit_byepid_wjm.action?examRoom.id="+roomid+"&examPaper.id="+epid+"&elclass.id=<s:property value='course.classid'/>&course.id=<s:property value='course.id'/>&coursePage.id=<s:property value='coursePage.id' />&datetime="+new Date();
											}
										}
								});
							}
						});
					}
				}
				document.getElementById("next_course").src="images/bf/anniuch_32.gif";
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
				//window.event.returnValue="确定退出本次学习？";
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
		"course.id":<s:property value="course.id"/>,
		"x":Math.random
		}, success:
		function (data) {
			var d = eval("("+data+")");
			if(d.msg==1){
			    window.clearInterval(timeObj);
			    timeObj = null;
				document.getElementById("next_course").src="images/bf/anniuch_32.gif";
				var roomSize = <s:property value="myCPage.examRooms.size()" />;
				//var roomSize=0;
				if(roomSize!=undefined && roomSize>0){
					for(var i=1;i<roomSize+1;i++){
						if(i<d.finishCpExamSortid){
							//document.getElementById(""+i).src="images/bf/startquiz.png";
							$("#"+i).css("background","url(images/quiz/startquiz.png) no-repeat center center");
						}else{
							//document.getElementById(""+i).src="images/bf/startquiz.gif";
							$("#"+i).css("background","url(images/quiz/startquiz.gif) no-repeat center center");
						}
					}
				}
				course_status=document.getElementById("next_course").src;
				
				if(status==1){//章节学习通过
					if(roomSize!=undefined && roomSize>0){
						var roomid;
						var epid;
						var title;
						for(var i=1;i<roomSize+1;i++){
							if(i<d.finishCpExamSortid){
							}else{
								$('#'+i).bind("click",{index:i},function(event){
									roomid = document.getElementById(""+event.data.index).alt;
									epid = document.getElementById(""+event.data.index).epid;
									title = document.getElementById(""+event.data.index).name;
									if(window.confirm("开始进入练习,Confirm To Start Exam")){
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
														document.getElementById("haha1").href="quizpaperinit_byepid_wjm.action?examRoom.id="+roomid+"&examPaper.id="+epid+"&elclass.id=<s:property value='course.classid'/>&course.id=<s:property value='course.id'/>&coursePage.id=<s:property value='coursePage.id' />&datetime="+new Date();
													}
												}
										});
									}
								});
							}
						}
					}
					$('#next_course').bind("click",function(){
						if(window.confirm("开始进入练习,Confirm To Start Exam")){
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
										}
									}
							});
						}
					});
				}
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
	
		function courseStudy(initCompliance,courseid,pageid,classid){
			window.parent.location.href="course_study.action?course.isLogout=1&initCompliance="+initCompliance+"&course.id="+courseid+"&coursePage.id="+pageid+"&classid="+classid;
		}
		function enterEroom(erid,epid,classid,courseid,pageid,time){
			if(time!=undefined){
				window.parent.location.href="quizpaperinit_byepid_wjm.action?myroom.examroom.id="+erid+"&examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid+"&time="+time;
			}else{
				window.parent.location.href="quizpaperinit_byepid_wjm.action?myroom.examroom.id="+erid+"&examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid;
			}
		}
		//返回
		function backup(){
			_cpst.exitStudy(); 
			var classid = <s:property value="course.classid"/>;
			var courseid = <s:property value="course.id"/>;
			//classid为2010或者2011的是学拼音或者学汉字培训班
			if(classid!=2010 && classid!=2011){
				_cpst.intelligent_learn_end();
			}
			window.parent.location.href='wjm_user_center.action?elClass.id='+classid+"&course.id="+courseid+"&backuptoindex=1";
			/**
			$.alerts.dialogClass = "style_1"; // set custom style class
			jConfirm("确认回到首页?,Confirm Return Home Page,Sure?", 'Information', function(r) {
				$.alerts.dialogClass = null; // reset to default
				if(r){
					_cpst.exitStudy(); 
					var classid = <s:property value="course.classid"/>;
					//classid为2010或者2011的是学拼音或者学汉字培训班
					if(classid!=2010 && classid!=2011){
						_cpst.intelligent_learn_end();
					}
					window.parent.location.href='wjm_user_center.action';
				}
			});
			*/
		}
		</script>
		<script type="text/javascript">

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
		image.src = "images/bf/anniu_36.png";
		video.StopPlay();
	}else{
		image.src = "images/bf/anniu_31.png";
		video.play();
	}
}
//从头开始播放 OK

function js_reset(){
	var image = document.getElementById("pause_play");
	image.src = "images/bf/anniu_31.png";
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
		<!-- 新版页面格局 -->
		<table width="1003" height="610" border="0" align="center"
			cellpadding="0" cellspacing="0" style="margin-top:113px;display:none;">
			<tr>
				<td height="507">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="600" height="507" align="right" valign="top">
								<table width="566" border="0" cellpadding="0" cellspacing="0"
									style="margin-right: 8px;">
									<tr>
										<td height="51" align="center" valign="middle">
										</td>
									</tr>
									<tr>
										<td  height="460">
											
										</td>
									</tr>
								</table>
							</td>
							<td valign="top">
								<table width="93%" border="0" align="right" cellpadding="0"
									cellspacing="0" style="margin-right: 15px;" >
									<tr>
									  <td height="60" align="left" valign="top" class="STYLE1">&nbsp;</td>
									</tr>
									<tr>
									  <td width="390" height="460" align="center" valign="middle"
											style="padding-top: 12px;">
											<table border="0" id="dt_zhang">
												<s:iterator value="myCPages" status="status">
													<s:if test="initCompliance">
														<tr>
                                                        <td class="xing"><s:property value="course.name" /></td>
															<td width="210" style="background: url(images/lianxi1.png) no-repeat center;padding-left:30px;">
																<s:if test="cpage.isNull == 0">
																	<a
																		href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elclass.id" />);">
																		<s:property value="#status.index+1" />.<s:property
																			value="cpage.title" /> </a>
																</s:if>
																<s:else>
																	<a
																		href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examPaper.id" />,<s:property value="elclass.id" />,<s:property value="courseid" />,<s:property value="cpage.id" />);">
																		<s:property value="#status.index+1" />.<s:property
																			value="cpage.title" /> </a>
																</s:else>
															</td>
														</tr>
													</s:if>
													<s:else>
														<s:if test="process==100">
															<tr>
                                                            <td class="xing"><s:property value="course.name" /></td>
																<td width="210"
																	style="background: url(images/lianxi1.png) no-repeat center;padding-left:30px;">
																	<s:if test="cpage.isNull == 0">
																		<a
																			href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elclass.id" />);">
																			<s:property value="#status.index+1" />.<s:property
																				value="cpage.title" /> </a>
																	</s:if>
																	<s:else>
																		<a
																			href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examPaper.id" />,<s:property value="elclass.id" />,<s:property value="courseid" />,<s:property value="cpage.id" />);">
																			<s:property value="#status.index+1" />.<s:property
																				value="cpage.title" /> </a>
																	</s:else>
																</td>
															</tr>
														</s:if>
														<s:elseif test="process==50">
															<tr>
                                                            <td class="xing"><s:property value="course.name" /></td>
																<td width="210"
																	style="background: url(images/lianxi2_d.gif) no-repeat center;padding-left:30px;">
																	<s:if test="cpage.isNull == 0">
																		<a
																			href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elclass.id" />);">
																			<s:property value="#status.index+1" />.<s:property
																				value="cpage.title" /> </a>
																	</s:if>
																	<s:else>
																		<a
																			href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examPaper.id" />,<s:property value="elclass.id" />,<s:property value="courseid" />,<s:property value="cpage.id" />);">
																			<s:property value="#status.index+1" />.<s:property
																				value="cpage.title" /> </a>
																	</s:else>
																</td>
															</tr>
														</s:elseif>
														<s:else>
															<s:if test="canLearn == 1">
																<tr>
                                                                <td class="xing"><s:property value="course.name" /></td>
																	<td width="210"
																		style="background: url(images/lianxi2_d.gif) no-repeat center;padding-left:30px;">
																		<s:if test="cpage.isNull == 0">
																			<a
																				href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elclass.id" />);">
																				<s:property value="#status.index+1" />.<s:property
																					value="cpage.title" /> </a>
																		</s:if>
																		<s:else>
																			<a
																				href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examPaper.id" />,<s:property value="elclass.id" />,<s:property value="courseid" />,<s:property value="cpage.id" />);">
																				<s:property value="#status.index+1" />.<s:property
																					value="cpage.title" /> </a>
																		</s:else>
																	</td>
																</tr>
															</s:if>
															<s:else>
																<tr>
                                                                <td class="xing"><s:property value="course.name" /></td>
																	<td width="210"
																		style="background: url(images/lianxi1_h.png) no-repeat center;padding-left:30px;">
																		<a href="javascript:void(0);"> <s:property
																				value="#status.index+1" />.<s:property
																				value="cpage.title" /> </a>
																	</td>
																</tr>
															</s:else>
														</s:else>
													</s:else>
												</s:iterator>
												<tr>
                                                <td ></td>
													<s:if test="initCompliance">
														<td width="210" valign="middle">
																<img src="images/test.png" alt="test" 
																	align="middle" onClick="enterEroom(<s:property value="mycourse.myRoom.examroom.id" />,<s:property value="epid" />,<s:property value="elclass.id"/>,<s:property value="course.id" />,0);">
														</td>
													</s:if>
													<s:else>
														<s:if test="mycourse.passed && mycourse.examPass!=1">
															<td  align="center" valign="middle" style="padding:0;">
																	<img src="images/test1.gif" alt="test"
																		align="middle" onClick="enterEroom(<s:property value="mycourse.myRoom.examroom.id" />,<s:property value="epid" />,<s:property value="elclass.id"/>,<s:property value="course.id" />,0);"> 
															</td>
														</s:if>
														<s:elseif test="mycourse.passed && mycourse.examPass==1">
															<td align="center" valign="middle" style="padding:0;">
																<img src="images/test.png" alt="test" style="cursor:hand"
																		align="middle" onClick="enterEroom(<s:property value="mycourse.myRoom.examroom.id" />,<s:property value="epid" />,<s:property value="elclass.id"/>,<s:property value="course.id" />,0);" />
															</td>
														</s:elseif>
														<s:else>
															<td align="center" valign="middle" style="padding:0;">
																	<img src="images/test_h.png" alt="test"
																		align="middle" onClick="javascript:void(0);">
															</td>
														</s:else>
													</s:else>
												</tr>
											</table>
									  </td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
			  <td height="100" valign="top">&nbsp;</td>
			</tr>
	</table>
		<table width="1146" height="605" border="0" align="center"
			cellpadding="0" cellspacing="0" style="margin-top:80px;">
          <tr>
            <td height="507"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="507" align="right" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="83" height="81">						</td>
                        <td align="right" valign="middle"><table border="0" cellspacing="0" style="margin-right:20px;margin-top:0px;">
                          <tr>
                            <td width="85" align="left" valign="top" style="padding-top:10px;"><img height="55" src="images/datigaiban/fanhui.png" width="55" onClick="backup();" style=" cursor: pointer;" /></td>
                            <td width="50" align="left" valign="top" style="padding-top:9px;"><A id="choose_menu" onClick="return saveToUrl(this);"> <img
										src="images/bf/anniuch_32h.png" name="next_course" style="border:0px; cursor:pointer;" width="55" height="55"
										id="next_course" /> </A></td>
                          </tr>
                        </table></td>
                        <td width="81">&nbsp;</td>
                      </tr>
                      <tr>
                        <td height="440">&nbsp;</td>
                        <td align="center" valign="top" style="padding-left: 0px;padding-right:0px;padding-top:0px;"><div id="page_file" style="width: 976px; height: 541px; overflow: hidden"></div></td>
                        <td width="8">&nbsp;</td>
                      </tr>
						

										
                  </table></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td height="100" valign="top">&nbsp;</td>
          </tr>
    </table>
		<div id="stem_Voc" style="display:none;overflow-x:hidden;overflow-y:auto; scrollbar-face-color: #dbebfe; scrollbar-shadow-color: #b8d6fa; scrollbar-highlight-color: #ffffff; scrollbar-3dlight-color: #dbebfe; scrollbar-darkshadow-color:#458ce4; scrollbar-track-color: #ffffff; scrollbar-arrow-color: #458ce4  "><div><span style="margin-left: 360px; color: #000;"><a  onclick="closeVoc();"><img src="images/datigaiban/wrong.png" width="15" height="15"  />
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
	</body>
</html>
