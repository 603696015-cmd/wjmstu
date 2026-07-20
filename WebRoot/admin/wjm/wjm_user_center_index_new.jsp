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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>个人中心</title>
		<meta name="keywords" content="OA,OA办公系统,OA系统" />
		<meta name="description"
			content="通达OA系统代表了协同OA的先进理念,是中国用户群最广泛的OA软件,协同OA软件行业唯一央企团队研发,多次摘取国内OA软件金奖,拥有300万终端OA用户,十年研发铸就成熟OA产品" />

		<link type="text/css" href="css/base.css" rel="stylesheet" />
		<link type="text/css" href="css/qhIndex.css" rel="stylesheet" />
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
						if(window.confirm("您上次定级异常退出,点击确定重新开始,click OK to enter exam again")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
						}
					}else if(time == 2){
						if(window.confirm("您上次定级异常退出,点击确定进行重新第2次定级考试,click OK to enter the 2nd exam")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,2);
						}
					}
				}
			}else {
				if(!inDingjiRoom ){
					if(time == 0){
						if(window.confirm("您还未定级,点击确定进行第1次定级考试,click OK to enter grading exam")){
							enterEroom(<s:property value="roomid" />,epid,0,0,0,1);
						}
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
				//mw.moveTo(0, 0);
				mw.resizeTo(screen.availWidth,screen.availHeight);
			}
			window.onbeforeunload=function(){
				window.event.returnValue="**********************************\n\n当前正在考试,不能离开本页面,以免造成错误,Do not shutdown the page,exam is in process\n\n**********************************";
			}
		}
		
		function refresh1(){
			window.onbeforeunload = null;
			window.setInterval(function(){
				window.location.href="wjm_user_center_index_new.action";
			},800);
		}
		function refresh2(){
			window.onbeforeunload = null;
			window.setInterval(function(){
				window.parent.location.href="wjm_user_center_new.action";
			},800);
		}
		
		function closeIndex(){
			if(window.confirm("确定退出,Logout，YES or NO?")){
				window.parent.location.href="logout.action";
			}
		}
		
		function showI(){
			window.location.href="showIntelligent.action?elClass.id=<s:property value="elClass.id" />";
		}
		
		function gotoClassRoom(){
		
		}
		
		function course_study(courseid,cpid,classid){
			var mw = window.open("course_study.action?course.id="+courseid+"&coursePage.id="+cpid+"&classid="+classid+"&datetime="+new Date(),"course_study","toolbar=no,fullscreen=1,location=no,directories=no,menubar=no,scrollbars=yes,resizable=no,status=no");
			if (window.screen){ 
				//mw.moveTo(0, 0);
				mw.resizeTo(screen.availWidth,screen.availHeight);
			}
		}
		</script>
		<style type="text/css">
<!--
body {
	
}
-->
</style>
	</head>
	<body>
		<div class="main2">
			<div class="kcNav mb20">

				<div class="navsech bd">
					<div style="width: 250px; float: right; padding-top: 0px;">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="60" valign="middle">
									<a href="vocabulary_search.action?vocabulary.status=1&vocabulary.wordid=-1">
										<img src="images/cidian.png" width="35" height="32" />
									</a>
								</td>
								<td width="70" height="40" valign="middle">
									<a href="javascript:showI();" style="font-size: 14px; color: white;"><img
											src="images/jifen.png" width="56" height="32" />
									</a>
								</td>
								<td width="50" valign="middle">
									<a href="wjm_user_center_index_new.action">
										<img src="images/201272510394fdc.png" width="35" height="35" />
									</a>
								</td>
								<td width="50" align="left" valign="middle">
									<a href="javascript:closeIndex();" style="font-size: 14px; color: white;"><img
											src="images/cl_close.png" width="32" height="32" />
									</a>
								</td>
							</tr>
						</table>
					</div>
				</div>

				<div class="contWrap clearfix">

					<div class="left">

						<dl class="slideNav">

							<dt>
								<h2>
									<s:property value="elClass.name" />
								</h2> 
							</dt>
							<s:if test="myCourses!=null && myCourses.size()>0">
								<ul>
								<s:iterator value="myCourses">
									<li <s:if test="nowCourseid==course.id">class="on"</s:if>><a href="wjm_user_center_index_new.action?course.id=<s:property value="course.id" />&elClass.id=<s:property value="elClass.id" />" 
												class="jz-kc" >Unit <s:property value="course.name" /> </a></li>
									<!-- 
									<s:if test="canLearn==1">
											<li <s:if test="nowCourseid==course.id">class="on"</s:if>><a href="wjm_user_center_index_new.action?course.id=<s:property value="course.id" />&elClass.id=<s:property value="elClass.id" />" 
												class="jz-kc" >Unit <s:property value="course.name" /> </a></li>
									</s:if>
									<s:else>
											<li <s:if test="nowCourseid==course.id">class="on"</s:if>><a href="javascript:void(0);" 
												class="jz-kc" >Unit <s:property value="course.name" />
											</a></li>
									</s:else>
									 -->
								</s:iterator>
								</ul>
							</s:if>
							
							<s:if test="elClass.myClass.hasExam == 1">
								<s:if
									test="elClass.myClass.process==100 && elClass.myClass.canExam==1">
									<!-- 进度为100且智能辅导分达标 -->
									<a target="_blank"
										href="quizpaperinit.action?classid=<s:property value="elClass.id" />&myroom.examroom.id=<s:property value="elClass.myClass.examRoom.id"/>">
										<img src="images/ggks1.gif" /> </a>
									<li>
										<a target="_blank" 
											class="xl-kc" 
											href="quizpaperinit.action?classid=<s:property value="elClass.id" />&myroom.examroom.id=<s:property value="elClass.myClass.examRoom.id"/>">* TEST *</a>
									</li>
								</s:if>
								<s:else>
									<li >
										<a class="xl-kc" href="javascript:void(0);">* TEST *</a>
									</li>
								</s:else>
							</s:if>


						</dl>

					</div>

					<div class="tbCont right">

						<ul class="kcList clearfix">
							<s:iterator value="myCPages" status="status">
								<li>
									<div class="kcList-in">
										<div class="bd">
												<s:if test="process==100">
													<s:if test="precCourseOver">
														<a  class="pic" target="_blank" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />">
															<img src="<s:property value="cpage.pic_l" />" alt="" width="50"
																height="50" border="0" />
														</a>
														<p>
															<a target="_blank"  href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><s:property value="#status.index+1" />、<s:property
																value="cpage.title" /> </a>
														</p>
													</s:if>
													<s:else>
														<a class="pic" href="javascript:void(0);">
															<img src="<s:property value="cpage.pic_h" />" alt="" width="50"
																height="50" border="0" />
														</a>
														<p>
															<a href="javascript:void(0);"><s:property value="#status.index+1" />、<s:property
																value="cpage.title" /> </a>
														</p>
													</s:else>
												</s:if>
												<s:elseif test="process==50">
													<s:if test="precCourseOver">
														<a target="_blank" class="pic" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />">
															<img src="<s:property value="cpage.pic_g" />" alt="" width="50"
																height="50" border="0" />
														</a>
														<p>
															<a target="_blank"  href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><s:property value="#status.index+1" />、<s:property
																value="cpage.title" /> </a>
														</p>
													</s:if>
													<s:else>
														<a class="pic" href="javascript:void(0);">
															<img src="<s:property value="cpage.pic_h" />" alt="" width="50"
																height="50" border="0" />
														</a>
														<p>
															<a href="javascript:void(0);"><s:property value="#status.index+1" />、<s:property
																value="cpage.title" /> </a>
														</p>
													</s:else>
													
												</s:elseif>
												<s:else>
													<s:if test="canLearn == 1">
														<s:if test="precCourseOver">
															<a target="_blank" class="pic" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />">
																<img src="<s:property value="cpage.pic_g" />" alt="" width="50"
																	height="50" border="0" />
															</a>
															<p>
																<a target="_blank"  href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><s:property value="#status.index+1" />、<s:property
																	value="cpage.title" /> </a>
															</p>
														</s:if>
														<s:else>
															<a class="pic" href="javascript:void(0);">
																<img src="<s:property value="cpage.pic_h" />" alt="" width="50"
																	height="50" border="0" />
															</a>
															<p>
																<a href="javascript:void(0);"><s:property value="#status.index+1" />、<s:property
																	value="cpage.title" /> </a>
															</p>
														</s:else>
													</s:if>
													<s:else>
														<a class="pic" href="javascript:void(0);">
															<img src="<s:property value="cpage.pic_h" />" alt="" width="50"
																height="50" border="0" />
														</a>
														<p>
															<a href="javascript:void(0);"><s:property value="#status.index+1" />、<s:property
																value="cpage.title" /> </a>
														</p>
													</s:else>
												</s:else>
										</div>
	
										<dl class="info lh200">
	
											<dt>
												<div
													style="BORDER-BOTTOM: #ff6633 1px dotted; BORDER-LEFT: #ff6633 1px dotted; BORDER-TOP: #ff6633 1px dotted; BORDER-RIGHT: #ff6633 1px dotted">
													<img src="images/jd.gif" width="<s:property value="process" />%" height="14" />
												</div>
											</dt>
											
											<s:if test="process==100">
												<s:if test="precCourseOver">
													<dd><a target="_blank" rel="nofollow" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><s:property
																value="cpage.title" /> </a></dd>
												</s:if>
												<s:else>
													<dd><a  rel="nofollow" href="javascript:void(0);"><s:property
																value="cpage.title" /> </a></dd>
												</s:else>
											</s:if>
											<s:elseif test="process==50">
												<s:if test="precCourseOver">
													<dd><a target="_blank" rel="nofollow" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><s:property
																value="cpage.title" /> </a></dd>
												</s:if>
												<s:else>
													<dd><a  rel="nofollow" href="javascript:void(0);"><s:property
																value="cpage.title" /> </a></dd>
												</s:else>
											</s:elseif>
											<s:else>
												<s:if test="canLearn == 1">
													<s:if test="precCourseOver">
														<dd><a target="_blank" rel="nofollow" href="course_study.action?course.id=<s:property value="courseid" />&coursePage.id=<s:property value="cpage.id" />&classid=<s:property value="elClass.id" />"><s:property
																	value="cpage.title" /> </a></dd>
													</s:if>
													<s:else>
														<dd><a  rel="nofollow" href="javascript:void(0);"><s:property
																	value="cpage.title" /> </a></dd>
													</s:else>
												</s:if>
												<s:else>
													<dd><a rel="nofollow" href="javascript:void(0);"><s:property
																value="cpage.title" /> </a></dd>
												</s:else>
											</s:else>
											
											<s:if test="(process==100 || process==50)&& examRoom.id>0 && examPaper.id>0 ">
												<dd><a target="_blank" rel="nofollow" href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="examRoom.id" />&examPaper.id=<s:property value="examPaper.id" />&elclass.id=<s:property value="elClass.id" />&course.id=<s:property value="course.id" />&coursePage.id=<s:property value="cpage.id" />">做练习</a></dd>
											</s:if>
											<s:else>
												<dd><a  rel="nofollow" href="javascript:void(0);">做练习</a></dd>
											</s:else>
											
											<dd>
												<a target="_blank" rel="nofollow" href="#"></a>
											</dd>
										</dl>
									</div>
								</li>
							</s:iterator>
							
							
							
							<s:if test="inDingjiRoom">
								<li>

									<div class="kcList-in">
	
										<div class="bd">
												<s:if test="mycourse.passed && mycourse.examPass!=1">
												
													<a target="_blank" class="pic" href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="mycourse.myRoom.examroom.id" />&examPaper.id=<s:property value="epid" />&elclass.id=<s:property value="elClass.id"/>&course.id=<s:property value="course.id" />&coursePage.id=0" ><img
															src="images/yasi_g.gif" alt="" width="50"
															height="50" />
													</a>
													
													<a target="_blank"
														href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="mycourse.myRoom.examroom.id" />&examPaper.id=<s:property value="epid" />&elclass.id=<s:property value="elClass.id"/>&course.id=<s:property value="course.id" />&coursePage.id=0">
														<p>单元测验</p>
													</a>
													
												</s:if>
												<s:elseif test="mycourse.passed && mycourse.examPass==1">
													<a target="_blank" class="pic" href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="mycourse.myRoom.examroom.id" />&examPaper.id=<s:property value="epid" />&elclass.id=<s:property value="elClass.id"/>&course.id=<s:property value="course.id" />&coursePage.id=0" ><img
															src="images/yasi_l.png" alt="" width="50"
															height="50" />
													</a>
													
													<a target="_blank"
														href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="mycourse.myRoom.examroom.id" />&examPaper.id=<s:property value="epid" />&elclass.id=<s:property value="elClass.id"/>&course.id=<s:property value="course.id" />&coursePage.id=0">
														<p>单元测验</p>
													</a>
													
												</s:elseif>
												<s:else>
													<a class="pic" href="javascript:void(0);" ><img
															src="images/yasi_h.png" alt="" width="50"
															height="50" />
													</a>
													<p>单元测验</p>
												</s:else>
											
										</div>
	
										<dl class="info lh200">
	
											<dt>
												<font color="#FF0000">状态：</font>
												<s:if test="mycourse.passed && mycourse.examPass!=1">
													未通过
												</s:if>
												<s:elseif test="mycourse.passed && mycourse.examPass==1">
													已通过
												</s:elseif>
												<s:else>
													未开始
												</s:else>
												
											</dt>
											
											<s:if test="mycourse.passed && mycourse.examPass!=1">
												<dd>
													<a target="_blank" href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="mycourse.myRoom.examroom.id" />&examPaper.id=<s:property value="epid" />&elclass.id=<s:property value="elClass.id"/>&course.id=<s:property value="course.id" />&coursePage.id=0"  rel="nofollow">LET`S GO </a>
												</dd>
											</s:if>
											<s:elseif test="mycourse.passed && mycourse.examPass==1">
												<dd>
													<a target="_blank" href="quizpaperinit_byepid_wjm.action?examRoom.id=<s:property value="mycourse.myRoom.examroom.id" />&examPaper.id=<s:property value="epid" />&elclass.id=<s:property value="elClass.id"/>&course.id=<s:property value="course.id" />&coursePage.id=0"  rel="nofollow">LET`S GO </a>
												</dd>
											</s:elseif>
											<s:else>
												<dd>
													<a href="javascript:void(0);"  rel="nofollow">LET`S GO </a>
												</dd>
											</s:else>
											
	
											<dd>
												<a href="" target="_blank" rel="nofollow"></a>
											</dd>
										</dl>
									</div>
								</li>
							</s:if>
							
						</ul>

					</div>

				</div>

			</div>

			<div id="index-con">
				<div class="split-line"></div>
			</div>
		</div>




		<div style="display: none;">
			<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Ff6a02979fbfd03743baff69b700c491b' type='text/javascript'%3E%3C/script%3E"));
</script>
		</div>
		<script type="text/javascript"
			src="http://edu.21cn.com/wx/js/jquery_1_7_min.js"></script>
		<script type="text/javascript" src="http://edu.21cn.com/wx/js/main.js"></script>
	</body>
</html>
