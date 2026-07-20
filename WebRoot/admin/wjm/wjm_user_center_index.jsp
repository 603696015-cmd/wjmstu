<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%@taglib prefix="newversionLib" uri="/WEB-INF/newversionLib.tld"%>
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
		<title>个人中心新首页</title>
		<link rel="stylesheet" href="css/wjm2.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script src="js/jquery.alerts.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="css/jquery.zdy.dialog.css" rel="stylesheet" type="text/css" media="screen" />
		<script type="text/javascript">
		function needAllocation(){
		debugger;
			var inDingjiRoom = <s:property value="inDingjiRoom" />;
			var time = <s:property value="elUserClassification.time" />;
			var status = <s:property value="elUserClassification.status" />;
			var epid = <s:property value="dingjiExamRoom.examPaper.id" />;
			if(status == -1){//表示定级异常退出
				if(!inDingjiRoom ){
					if(time == 1){
						$.alerts.dialogClass = "style_1"; // set custom style class
						jConfirm("您上次定级异常退出,点击确定重新开始,click OK to enter exam again", 'Information', function(r) {
							$.alerts.dialogClass = null; // reset to default
							if(r){
								enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
							}
						});
						/**
						if(window.confirm("您上次定级异常退出,点击确定重新开始,click OK to enter exam again")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
						}
						*/
					}else if(time == 2){
						$.alerts.dialogClass = "style_1"; // set custom style class
						jConfirm("您上次定级异常退出,点击确定进行重新第2次定级考试,click OK to enter the 2nd exam", 'Information', function(r) {
							$.alerts.dialogClass = null; // reset to default
							if(r){
								enterEroom(<s:property value="roomid" />,epid,0,0,0,2);
							}
						});
						/**
						if(window.confirm("您上次定级异常退出,点击确定进行重新第2次定级考试,click OK to enter the 2nd exam")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,2);
						}
						*/
					}
				}
			}else {
				if(!inDingjiRoom ){
					if(time == 0){
						$.alerts.dialogClass = "style_1"; // set custom style class
						jConfirm("您还未定级,点击确定进行第1次定级考试,click OK to enter grading exam", 'Information', function(r) {
							$.alerts.dialogClass = null; // reset to default
							if(r){
								enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
							}
						});
						/**
						if(window.confirm("您还未定级,点击确定进行第1次定级考试,click OK to enter grading exam")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
						}
						*/
					}
				}
			}
		}
		window.onload = function(){
			 needAllocation();
		}
		function haha(){
			var inDingjiRoom = <s:property value="inDingjiRoom" />;
			if(!inDingjiRoom){
				enterEroom(<s:property value="roomid" />,<s:property value="dingjiExamRoom.examPaper.id" />,0,0,0,2);
			}
		}
	</script>
		<script type="text/javascript">
		function courseStudy(initCompliance,courseid,pageid,classid){
		if(classid==undefined){
			//classid=${elClass.id};
		}
			window.parent.location.href="course_study.action?initCompliance="+initCompliance+"&course.id="+courseid+"&coursePage.id="+pageid+"&classid="+classid;
			//var mw = window.open("course_study.action?initCompliance="+initCompliance+"&course.id="+courseid+"&coursePage.id="+pageid+"&classid="+classid+"&datetime="+new Date(),"course_study_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			/**
			if (window.screen){ 
				mw.moveTo(0, 0);
				mw.resizeTo(screen.availWidth,screen.availHeight);
			}
			*/
		}
		function enterEroom(erid,epid,classid,courseid,pageid,time){
			debugger;
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
		var refresh = 0;
		function refresh1(){
			window.onbeforeunload = null;
			window.setInterval(function(){
				window.location.href="wjm_user_center_index.action";
				refresh = 1;
			},800);
			if(refresh = 0){
				refresh2();
			}
		}
		function refresh2(){
			window.onbeforeunload = null;
			window.setInterval(function(){
				window.parent.location.href="wjm_user_center.action";
			},800);
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
			if(window.confirm("确定退出,Logout，YES or NO?")){
				window.parent.location.href="logout.action";
			}
			*/
		}
		
		function showI(){
			window.location.href="showIntelligent.action?elClass.id=<s:property value="elClass.id" />";
		}
		
		function gotoClassRoom(){
		
		}
		</script>
		<style>
#ddd img {
	display: block;
}

.STYLE1 {
	font-size: 36px;
	font-weight: bold;
}
body{ font-family:"楷体"; font-size:18px;}
#center #center_r #center_bg td p a{font-size:24px;}
#center #center_r #center_bg .zhang_hover p a:hover{ color:red; font-size:26px;}
</style>

	</head>

	<body>
		
		<!--中部开始-->
		<div id="center">
			<div id="center_r" class="pngfix">
				<div id="center_bg" class="clearFix">
					<div style="margin-top:50px; margin-left:100px; overflow:auto;width:30%;float:left;">
						<table width="100%" border="0" cellpadding="0" align="left"
							cellspacing="0">
							<s:iterator value="myCPages" status="status">
								<s:if test="initCompliance">
									<tr>
										<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-weight:bold;color: #000"><s:property value="course.name" /></td>
										<td class="zhang_hover" style="background: url(images/lianxi1.png) no-repeat left;">
											<p>
												<!-- 
												<a target="_blank"
													href="course_study.action?initCompliance=<s:property value="initCompliance" />&course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />">
													<s:property value="#status.index+1" />.<s:property
														value="cpage.title" /> </a>
												 -->
												 <s:if test="cpage.isNull == 0">
												 	<a 
														href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elClass.id" />);">
														<s:property value="#status.index+1" />.<s:property
															value="cpage.title" /> </a>
												 </s:if>
												 <s:else>
												 	<a 
														href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examPaper.id" />,<s:property value="elClass.id" />,<s:property value="courseid" />,<s:property value="cpage.id" />);">
														<s:property value="#status.index+1" />.<s:property
															value="cpage.title" /> </a>
												 </s:else>
											</p>
										</td>
									</tr>
								</s:if>
								<s:else>
									<s:if test="process==100">
										<tr>
											<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat  center 15px; font-weight:bold;color: #000"><s:property value="course.name" /></td>
											<td class="zhang_hover" style="background: url(images/lianxi1.png) no-repeat left;">
												<p>
													<s:if test="cpage.isNull == 0">
													 	<a 
															href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elClass.id" />);">
															<s:property value="#status.index+1" />.<s:property
																value="cpage.title" /> </a>
													 </s:if>
													 <s:else>
													 	<a 
															href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examPaper.id" />,<s:property value="elClass.id" />,<s:property value="courseid" />,<s:property value="cpage.id" />);">
															<s:property value="#status.index+1" />.<s:property
																value="cpage.title" /> </a>
													 </s:else>
												</p>
											</td>
										</tr>
									</s:if>
									<s:elseif test="process==50">
										<!-- 
										<tr>
											<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat  center 15px; font-weight:bold;color: #000"><s:property value="course.name" /></td>
											<td style="background: url(images/lianx.gif) no-repeat left;">
												<p>
													<a target="_blank"
													href="course_study.action?initCompliance=<s:property value="initCompliance" />&course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />">
													<s:property value="#status.index+1" />.<s:property
														value="cpage.title" /> </a>
												</p>
											</td>
										</tr>
										 -->
										 <tr>
											<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat  center 15px; font-weight:bold;color: #000"><s:property value="course.name" /></td>
											<td class="zhang_hover" style="background: url(images/lianxi1_d.gif) no-repeat left;">
												<p>
													<s:if test="cpage.isNull == 0">
													 	<a 
															style="color:red;" href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elClass.id" />);">
															<s:property value="#status.index+1" />.<s:property
																value="cpage.title" /> </a>
													 </s:if>
													 <s:else>
													 	<a 
															style="color:red;" href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examPaper.id" />,<s:property value="elClass.id" />,<s:property value="courseid" />,<s:property value="cpage.id" />);">
															<s:property value="#status.index+1" />.<s:property
																value="cpage.title" /> </a>
													 </s:else>
												</p>
											</td>
										</tr>
									</s:elseif>
									<s:else>
										<s:if test="canLearn == 1">
											<!-- 
											<tr>
												<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-weight:bold;color: #000"><s:property value="course.name" /></td>
												<td style="background: url(images/lianx.gif) no-repeat left;">
													<p>
														<a target="_blank"
															href="course_study.action?initCompliance=<s:property value="initCompliance" />&course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />">
															<s:property value="#status.index+1" />.<s:property
																value="cpage.title" /></a>
													</p>
												</td>
											</tr>
											 -->
											<tr>
												<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat  center 15px; font-weight:bold;color: #000"><s:property value="course.name" /></td>
												<td class="zhang_hover" style="background: url(images/lianxi1_d.gif) no-repeat left;">
													<p>
														<s:if test="cpage.isNull == 0">
														 	<a 
																style="color:red;" href="javascript:courseStudy(<s:property value="initCompliance" />,<s:property value="courseid" />,<s:property value="cpage.id" />,<s:property value="elClass.id" />);">
																<s:property value="#status.index+1" />.<s:property
																	value="cpage.title" /> </a>
														 </s:if>
														 <s:else>
														 	<a 
																style="color:red;" href="javascript:enterEroom(<s:property value="examRoom.id" />,<s:property value="examPaper.id" />,<s:property value="elClass.id" />,<s:property value="courseid" />,<s:property value="cpage.id" />);">
																<s:property value="#status.index+1" />.<s:property
																	value="cpage.title" /> </a>
														 </s:else>
													</p>
												</td>
											</tr>
										</s:if>
										<s:else>
											<!-- 
											<tr>
												<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-weight:bold;color: #000"><s:property value="course.name" /></td>
												<td
													style="background: url(images/lianx_h.png) no-repeat left;">
													<p>
														<s:property value="#status.index+1" />.<s:property value="cpage.title" /></p>
												</td>
											</tr>
											 -->
											 <tr>
												<td align="center" width="40" style="background:url(images/xing_g.png) no-repeat center 15px; font-weight:bold;color: #000"><s:property value="course.name" /></td>
												<td style="background: url(images/lianxi1_h.png) no-repeat left;">
													<p>
														<a href="javascript:void(0);">
														<s:property value="#status.index+1" />.<s:property value="cpage.title" />
														</a>
													</p>
												</td>
											</tr>
										</s:else>
									</s:else>
								</s:else>
							</s:iterator>
						</table>
						<s:if test="inDingjiRoom">
                       
                        <!-- 
                        quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="mycourse.myRoom.examroom.id" />&examPaper.id=<s:property value="epid" />&elclass.id=<s:property value="elClass.id"/>&course.id=<s:property value="course.id" />&coursePage.id=0
                         -->
                        <div style="clear:both;"><s:if test="initCompliance"><a  href="javascript:enterEroom(<s:property value="mycourse.myRoom.examroom.id" />,<s:property value="epid" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);"><img src="images/test.png"  style="margin:30px 0px 0px 80px;" /></a></s:if><s:else><s:if test="mycourse.passed && mycourse.examPass!=1"><a  href="javascript:enterEroom(<s:property value="mycourse.myRoom.examroom.id" />,<s:property value="epid" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);"><img src="images/test.gif"  style="margin:30px 0px 0px 80px;" /></a></s:if><s:elseif test="mycourse.passed && mycourse.examPass==1"><a  href="javascript:enterEroom(<s:property value="mycourse.myRoom.examroom.id" />,<s:property value="epid" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);"><img src="images/test.png"  style="margin:30px 0px 0px 80px;" /></a></s:elseif><s:else><img src="images/test_h.png"  style="margin:30px 0px 0px 80px;" /></s:else></s:else></div>
                        </s:if>
					</div>
				</div>
			</div>
			<br />
		</div>
		<!--中部结束-->
		
	</body>
</html>



