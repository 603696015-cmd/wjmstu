<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" target="_self">
		<title>学习课程（外部）--<s:property value="course.name" /></title>
		<link href="css/study_course.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="js/cvideo/cvideoWrite.js"></script>
		<link href="css/study_wbkc.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript">
			var _cpst;
			var needsetCp = true;
			var pass = <s:property value="myCPage.passed"/>;
			var passtime = <s:property value="myCPage.passtime"/>;
			var type = <s:property value="coursePage.type"/>;
			var pageUrl='<s:property value="coursePage.page_url_Encoder"/>';
			var classid = <s:property value="course.classid"/>;
			var courseid=<s:property value="course.id"/>;
			var coursePageid = <s:property value="coursePage.id"/>;
			var during=<s:property value="myCPage.cpage.during"/>;
			var queryTime=<s:property value="coursePage.queryTime"/>;
			var passtime2=<s:property value="myCPage.passtime2"/>;
			var studyCourseRecordId=<s:property value="studyCourseRecordId"/>;
			var passed2 = <s:property value="myCPage.passed2"/>;
			var studyinfo_time = <s:property value="#session.studyinfo_time"/>;
			
			function myload(status){
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
				//_cpst.autoSetCprocess();
			}
			function hiddenCat(){
				var cat1=document.getElementById("cat");
				if(cat1.style.display=="block"){
					cat1.style.display="none";
				}
			}
			window.onbeforeunload=function(){
				if(<s:property value="coursePage.getcredit"/>!=1){
					if(_cpst.passtime2>=<s:property value="myCPage.cpage.during"/>*60){
						if(_cpst.passed2==0){
							alert("你还有考试没过！");
						}
					}
				}
					//window.event.returnValue="确定退出本次学习？";
			}
			window.onunload=function(){
				_cpst.exitStudy(); 
			}
		</script>
	</HEAD>
	<body onLoad="myload('<s:property value="status"/>');div_ifr();" style="overflow: visible; padding: 0px; margin: 0px">
		
		<div
			style="border: solid 1px buttonface; z-index: 1000; position: absolute; background: #ffffff; width: 300; height: 300px; overflow: auto; display: none"
			id="cat">
			<div style="width: 100%; height: 20px;">
				<a href="#" style="float: right;" onclick='hiddenCat();return false;'>关闭</a>
			</div>
			<div
				style="width: 100%; height: 40px; text-align: center; margin-top: 10px;">
				<div
					style="width: 180px; height: 20px; margin: 0px auto; background: buttonface; text-align: left;"
					id="processDiv">
				</div>
				<div id="processDiv1"
					style="font-size: 12px; height: 20px; color: blue;">
				</div>
				<div id="timer" style="font-size: 12px;">
					已学时间：加载中..秒
				</div>
			</div>
			<div
				style="font-size: 12px; height: 26px; overflow: hidden; text-align: center;">
				<img src="images/img//studied.gif" width="15" height="13">
				<span>已完成</span>
				<img src="images/img/studying.gif" width="15" height="13">
				<span>学习中</span>
				<img src="images/img/unstudied.gif" width="12" height="13">
				<span>未学习</span>
			</div>
			<ul style="margin: 10px 20px;">
				<s:iterator value="myCPages" id="cp">
					<li style="font-size: 14px;">
						<s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
						<a
							href="course_study.action?course.isLogout=1&coursePage.id=<s:property value="cpage.id" />&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">
							<s:property value="cpage.title" /> </a>
						<s:if test="passed">
							<img src="images/img/studied.gif" width="15" height="13" />
						</s:if>
						<s:else>
							<s:if test="begintime!=null">
								<img src="images/img/studying.gif" width="12" height="13" />
							</s:if>
							<s:else>
								<img src="images/img/unstudied.gif" width="12" height="13" />
							</s:else>
						</s:else>
						<!--<s:if test="cpage.skipable==1">【可以跳过】</s:if>
																	<s:else>【不可跳过】</s:else>-->
					</li>
					<s:iterator value="myPracs" id="pp">
						<li style="font-size: 12px;">
							<i><s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
								&nbsp;&nbsp;&nbsp;&nbsp;[练习] <a 
								href="practice_paper.action?course.id=<s:property value="#cp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
									<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> --> </a> <s:if
									test="lasttime==null">
									<img src="images/img/unstudied.gif" width="12" height="13" />
								</s:if> <s:else>
									<img src="images/img/studied.gif" width="12" height="13" />
								</s:else> </i>
							<!-- <img src="images/img/studied.gif" width="15" height="13"> -->
						</li>
					</s:iterator>
				</s:iterator>
				<s:iterator value="course.myPracs" id="pp">
					<li style="font-size: 12px;">
						<i> [练习]<a 
							href="practice_paper.action?course.id=<s:property value="#cp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
								<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> --> </a> <s:if
								test="lasttime==null">
								<img src="images/img/unstudied.gif" width="12" height="13" />
							</s:if> <s:else>
								<img src="images/img/studied.gif" width="12" height="13" />
							</s:else> </i>
						<!-- <img src="images/img/studied.gif" width="15" height="13"> -->
					</li>
				</s:iterator>
			</ul>
		</div>
		<div
			style="position: absolute; border: solid 1px buttonface; width: 400; height: 300px; background: white; z-index: 300; display: none;"
			id="noteadd">
			<div style="width: 100%; background: #eeddaa">
				<span style="width: 380">做笔记</span><span
					style="cursor: hand; width: 15px;" onClick="closediv('noteadd')">X</span>
			</div>
			<div style="width: 100%; height: 100%" id="noteaddcontent"></div>
		</div>
		<div
			style="position: absolute; border: solid 1px buttonface; width: 600; height: 400px; background: white; z-index: 301; display: none;"
			id="notelist">
			<div style="width: 100%; background: #eeddaa">
				<span style="width: 580">查看笔记</span><span
					style="cursor: hand; width: 15px;" onClick="closediv('notelist')">X</span>
			</div>
			<div style="width: 100%; height: 100%" id="notelistcontent"></div>
		</div>
		<div id="message" style="display: none;"></div>
		<script type="text/javascript">
		function showCat(obj){
			var cat1=document.getElementById("cat");
			if(cat1.style.display=="block"){
				cat1.style.display="none";
			}else
				cat1.style.display="block";
				var left = (obj.offsetLeft + obj.clientWidth);
				var top = (obj.offsetTop);
				while (obj = obj.offsetParent) {
					left += obj.offsetLeft;
					top += obj.offsetTop;
				}
			cat1.style.left = left;
			cat1.style.top = top+10;
		}
		</script>
		<table height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tbody>
				<tr>
					<td vAlign=top height=38>
						<table cellSpacing=0 cellPadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0 width="100%" border=0>
											<tbody>
												<tr>
													<td vAlign=bottom align="center"
														background=images/img/bfz_r1_c11.jpg>
														<table height=42 cellSpacing=0 cellPadding=0 width="100%">
															<tbody>
																<tr>																
																	<td width="250"><table width="100%" border="0">
                                                                      <tr>
                                                                        <td><div style=" width: 200px; height: 15px; margin: 0px auto; background: buttonface; text-align: left;" id="processDiv3">
																			</div>
																			<div id="processDiv4" 
																				style="margin-left:30px;font-size: 12px; height: 15px; color: blue;">
																				<s:if test="myCPage.pracp.id>0">
																					<font style="font-size:14px;"><a href="practice_paper.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>&coursePage.id=<s:property value="myCPage.cpage.id"/>&examPaper.id=<s:property value="myCPage.pracp.examPaper.id"/>&myPractice.ppaper.id=<s:property value="myCPage.pracp.id"/>"><s:property value="myCPage.pracp.title"/></a></font>
																				</s:if>
																			</div>
																		</td>
                                                                      </tr>
                                                                      <tr>
                                                                        <td style="font-size:12px;"><span id="timer2">已学：加载中..秒</span>
																			&nbsp;&nbsp;&nbsp;
																		<span id="timer3">规定时长：加载中..秒</span><br>
																	
																		</td>
                                                                      </tr>
                                                                    </table></td>
																	<td class=bt vAlign="middle" align="center">
																		<s:property value="course.name" />
																	<td>
																	<td vAlign="middle" width=370
																		style="font-size: 12px;text-align:right;padding-right:15px;">
																		<!-- 
																		<a class=dh href="#" style="cursor: hand"
																			onclick="javascript:showCat(this);return false;">目录/进度</a>
																		<a class=dh target="_blank"
																			href="course_tandsp.action?course.id=${course.id }&course_sourse=0">讲师简介</a><SPAN
																			class=STYLE12> &gt;&gt;</SPAN>
																		<a class=dh target="_blank"
																			href="course_tandsp.action?course.id=${course.id }&course_sourse=1">教学计划</a><SPAN
																			class=STYLE12> &gt;&gt;</SPAN><a class=dh
																			href="study.action">学习任务</a>
																		 -->
																		 
																		 总进度：
																			<span style="width:150px;border: 1px dotted #FF6633;text-align:left;">
																			<IMG height=14 src="images/jd.gif" id="cp_img" width="<s:property value="myCourse.process" />%">																			</span>
																			<span id="cp_img_span"><s:property value="myCourse.process_" />%</span>
																		 <br><a style="color:blue;" href="displayStudyCpageInfo.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">
																		 各章节学习详情</a>
																		 &nbsp;&nbsp;&nbsp;
																		 <a class=dh href="course_study.action?course.isLogout=1&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>" style="cursor: hand"
																			onclick="javascript:;">课程首页</a>
																  <td>
																</tr>
															</tbody>
														</table>
													<td>
												</tr>
												<tr>
													<td background=images/img/t-5.jpg height=13><img height=13 src="images/img/t-52.jpg" width=180 /><td>
												</tr>
											</tbody>
										</table>
									<td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%" height="100%">  
					<div id="ifr" style="width:100%;height:100%;"> 
					</div> 
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>
