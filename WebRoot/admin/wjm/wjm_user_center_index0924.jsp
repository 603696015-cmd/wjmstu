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
		<link rel="stylesheet" href="css/wjm.css" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.blockUI.js"></script>
		<script type="text/javascript">
		function needAllocation(){
			var inDingjiRoom = <s:property value="inDingjiRoom" />;
			var time = <s:property value="elUserClassification.time" />;
			var status = <s:property value="elUserClassification.status" />;
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
					if(time == 0){
						if(window.confirm("您还未定级,点击确定进行第1次定级考试You have not grading, click OK to 1st grading exams")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
						}
					}
				}else{
					var loginType = <s:property value="intelligentLogin.loginType" />;
					if(loginType == 1){
						alert("您今天登录加0.1分You landed an increase of 0.1 points today");
					}else if(loginType == -1){
						alert("您已3天未登录减0.9分You are not logged minus 0.9 points 3 days");
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
		function enterEroom(erid,epid,classid,courseid,pageid,time){
			var mw = window.open("quizpaperinit_byepid_wjm.action?myroom.examroom.id="+erid+"&examRoom.id="+erid+"&examPaper.id="+epid+"&elclass.id="+classid+"&course.id="+courseid+"&coursePage.id="+pageid+"&time="+time+"&datetime="+new Date(),"course_exam_5","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			if (window.screen){ 
				mw.moveTo(0, 0);
				mw.resizeTo(screen.availWidth,screen.availHeight);
			}
			window.onbeforeunload=function(){
				window.event.returnValue="**********************************\n\n当前正在考试,不能离开本页面,以免造成错误Currently being exam, can not leave the page in order to avoid errors!\n\n**********************************";
			}
		}
		
		function refresh1(){
			window.onbeforeunload = null;
			window.setInterval(function(){
				window.location.href="wjm_user_center_index.action";
			},800);
		}
		function refresh2(){
			window.onbeforeunload = null;
			window.setInterval(function(){
				window.parent.location.href="wjm_user_center.action";
			},800);
		}
		
		function closeIndex(){
			if(window.confirm("确定退出LOGOUT，YES,NO?")){
				window.parent.location.href="logout.action";
			}
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
</style>

	</head>

	<body >
		<div id="all">
			<!--中部开始-->
			<div id="center">
				<!--中部左侧-->
				<div id="center_l">
					<a href="#">
						<div
							style="background: url(images/score.png) no-repeat top; height: 35px; float: left; width: 189px; margin: 0 5px;" onclick="showI();"></div>
					</a>
					<form>
						<table width="100%" border="0" cellpadding="0" cellspacing="5"
							style="margin-top: 10px;">
							<tr>
								<td align="center">
									<a href="vocabulary_search.action?vocabulary.status=1&vocabulary.wordid=-1"><h1>
											<p align="center">
												词汇库
											</p>
										</h1>
									</a>
								</td>
							</tr>
							<tr>
								<td align="center">
									<a href="wjm_student_myalterInit.action"><h1>
											<p align="center">
												资 料
											</p>
										</h1>
									</a>
								</td>
							</tr>
							<tr>
								<td align="center">
									<a href="student_mypwdalterInit.action"><h1>
											<p align="center">
												密 码
											</p>
										</h1>
									</a>
								</td>
							</tr>
							<tr>
								<td align="center">
									<a href="learnPinyin.action?elClass.id=2010"><h1>
											<p align="center">
												学拼音
											</p>
										</h1>
									</a>
								</td>
							</tr>
							<tr>
								<td align="center">
									<a href="learnHanzi.action?elClass.id=2011"><h1>
											<p align="center">
												学汉字
											</p>
										</h1>
									</a>
								</td>
							</tr>
							<tr>
								<td align="center">
									<a href="simulationExam.action"><h1>
											<p align="center">
												模拟考试
											</p>
										</h1>
									</a>
								</td>
							</tr>
						</table>
					</form>
					<div id="close">
						<input type="image" src="images/cl_close.png" onclick="closeIndex();"/>
					</div>
				</div>
				<!--中部右侧-->
				<div id="center_r">
					<div id="name">
							<h2>
								<a href="mystudy_course_view_wjm.action?elClass.id=<s:property value="elClass.id" />"><s:property value="elClass.name" />&nbsp;-&nbsp;<s:property value="elClass.myClass.process" />%</a>
							</h2>
					</div>
					<div id="center_rdj">
						<table width="100" border="0" align="right" cellpadding="0"
							cellspacing="1">
							<tr>
								<td height="48" align="center" co>
									<H1>
										<!-- 
										<a href="javascript:window.parent.listCoursesByClassid(<s:property value="peixunBatch.id" />,<s:property value="elClass.id" />)"><s:property value="elClass.name" /></a>
										 -->
										<a href="mystudy_course_view_wjm.action?elClass.id=<s:property value="elClass.id" />"><s:property value="elClass.name" /></a>
									</H1>
								</td>
							</tr>
							<tr>
								<td height="48" align="center">
									<H1 style="color: red;">
										<a href="mystudy_page_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id"/>&course.id=<s:property value="course.id" />&elClass.id=<s:property value="elClass.id" />"><s:property value="course.name" /></a>
									</H1>
								</td>
							</tr>
						</table>
					</div>
					<table id="anniu" width="68%" border="0" align="right"
						cellpadding="0" cellspacing="4" style="margin-top: 40px;">
						<s:set name="cpagesize" value="myCPages.size()"></s:set>
						<s:iterator value="myCPages" status="status">
							<s:if test="#status.index == 0">
								<tr>
									<td width="58%" valign="middle">
										<!-- 
										<s:if test="canLearn == 1">
											<a target="_blank" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><h1>
													<p style="background: url(images/<s:if test="cpage.passed == 1">lianx.gif</s:if><s:else>lianx1.png</s:else>) no-repeat;">
														&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#status.index+1" />、<s:property value="cpage.title" />
													</p>
												</h1>
											</a>
										</s:if>
										<s:else>
											<a href="javascript:void(0);"><h1>
													<p style="background: url(images/lianx.gif) no-repeat;">
														&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#status.index+1" />、<s:property value="cpage.title" />
													</p>
												</h1>
											</a>
										</s:else>
										 -->
										<s:if test="process==100">
											<a target="_blank" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><h1>
													<p style="background: url(images/lianx1.png) no-repeat;">
														<s:property value="#status.index+1" />、<s:property value="cpage.title" />
													</p>
												</h1>
											</a>
										</s:if>
										<s:elseif test="process==50">
											<a target="_blank" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><h1>
													<p style="background: url(images/lianx.gif) no-repeat;">
														<s:property value="#status.index+1" />、<s:property value="cpage.title" />
													</p>
												</h1>
											</a>
										</s:elseif>
										<s:else>
											<s:if test="canLearn == 1">
												<a target="_blank" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><h1>
														<p style="background: url(images/lianx.gif) no-repeat;">
															<s:property value="#status.index+1" />、<s:property value="cpage.title" />
														</p>
													</h1>
												</a>
											</s:if>
											<s:else>
												<a href="javascript:void(0);"><h1>
														<p style="background: url(images/lianx_h.png) no-repeat;">
															<s:property value="#status.index+1" />、<s:property value="cpage.title" />
														</p>
													</h1>
												</a>
											</s:else>
										</s:else>
									</td>
									<td width="44%" rowspan="4">
										<s:if test="mycourse.passed && mycourse.examPass!=1">
											<a href="javascript:enterEroom(<s:property value="mycourse.myRoom.examroom.id" />,<s:property value="epid" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);">
												<div id="ceyan">
													<h1>
														<img src="images/dycs.gif" width="68" height="68" />
													</h1>
													<br />
													<h2>
														<img src="images/tset.gif" width="68" height="68" />
													</h2>
												</div>
											</a>
										</s:if>
										<s:elseif test="mycourse.passed && mycourse.examPass==1">
											<a href="javascript:enterEroom(<s:property value="mycourse.myRoom.examroom.id" />,<s:property value="epid" />,<s:property value="elClass.id"/>,<s:property value="course.id" />,0);">
												<div id="ceyan">
													<h1>
													单元测验
													</h1>
													<br />
													<h2>
														TEST
													</h2>
												</div>
											</a>
										</s:elseif>
										<s:else>
											<div id="ceyan">
												<h1>
													单元测验
												</h1>
												<br />
												<h2>
													TEST
												</h2>
											</div>
										</s:else>
									</td>
								</tr>
							</s:if>
							<s:else>
								<tr>
									<td>
										<s:if test="process==100">
											<a target="_blank" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><h1>
													<p style="background: url(images/lianx1.png) no-repeat;">
														<s:property value="#status.index+1" />、<s:property value="cpage.title" />
													</p>
												</h1>
											</a>
										</s:if>
										<s:elseif test="process==50">
											<a target="_blank" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><h1>
													<p style="background: url(images/lianx.gif) no-repeat;">
														<s:property value="#status.index+1" />、<s:property value="cpage.title" />
													</p>
												</h1>
											</a>
										</s:elseif>
										<s:else>
											<s:if test="canLearn == 1">
												<a target="_blank" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><h1>
														<p style="background: url(images/lianx.gif) no-repeat;">
															<s:property value="#status.index+1" />、<s:property value="cpage.title" />
														</p>
													</h1>
												</a>
											</s:if>
											<s:else>
												<a href="javascript:void(0);"><h1>
														<p style="background: url(images/lianx_h.png) no-repeat;">
															<s:property value="#status.index+1" />、<s:property value="cpage.title" />
														</p>
													</h1>
												</a>
											</s:else>
										</s:else>
									</td>
								</tr>
							</s:else>
						</s:iterator>
					</table>

				</div>
			</div>
			<!--中部结束-->
			<!--底部开始-->
			<div id="bottom">
				<table width="946" border="0" cellspacing="0" cellpadding="0"
					style="margin: 0 auto;">
					<tr>
						<td width="100">
							<div style="width: 100%; text-align: right; font-size: 30px;">
								<h1>
									<p>
										<s:property value="elClass.name" />
									</p>
								</h1>
							</div>
						</td>
						<td align="left">
							<div id="bottom_b">
								<ul>
									<s:if test="myCourses!=null && myCourses.size()>0">
										<s:iterator value="myCourses">
											<s:if test="canLearn==1">
												<!-- 
												<a
													href="mystudy_page_view_wjm.action?peixunBatch.id=<s:property value="peixunBatch.id"/>&course.id=<s:property value="course.id" />&elClass.id=<s:property value="elClass.id" />">
													<li>
														<p class="STYLE1"
															style='width: <s:property value ="process"/>%'>
															<s:property value="course.name" />
														</p>
													</li> 
												</a>
												 -->
												 <a
													href="wjm_user_center_index.action?course.id=<s:property value="course.id" />&elClass.id=<s:property value="elClass.id" />">
													<li>
														<p class="STYLE1"
															style='width: <s:property value ="process"/>%'>
															<s:property value="course.name" />
														</p>
													</li> 
												</a>
											</s:if>
											<s:else>
												<a href="javascript:void(0);">
													<li>
														<p class="STYLE1"
															style='width: <s:property value ="process"/>%'>
															<s:property value="course.name" />
														</p>
													</li> 
												</a>
											</s:else>
										</s:iterator>
									</s:if>
								</ul>
							</div>
						</td>
							<s:if test="elClass.myClass.hasExam == 1">
								<s:if test="elClass.myClass.process==100 && elClass.myClass.canExam"><!-- 进度为100且智能辅导分达标 -->
									<td width="80" align="center">
										<a target="_blank" href="quizpaperinit.action?classid=<s:property value="elClass.id" />&myroom.examroom.id=<s:property value="elClass.myClass.examRoom.id"/>">
											<img src="images/ggks.gif" width="68" height="68" border="0" />
										</a>
									</td>
								</s:if>
								<s:else>
										<td width="80" align="center"><img src="images/ggks_h.png" width="68" height="68" /></td>
								</s:else>
							</s:if>
							<s:else>
									<td width="80" align="center"></td>
							</s:else>
							<!--  -->
					</tr>
				</table>
			</div>
			<!--底部结束-->
		</div>

	</body>
</html>



