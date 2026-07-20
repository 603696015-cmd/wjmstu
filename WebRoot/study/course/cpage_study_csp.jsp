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
		<base href="<%=basePath%>" target="_self" />
		<title>学习课程--<s:property value="course.name" /></title>
		
		<link href="css/study_csp.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="js/cvideo/cvideoWrite.js"></script>
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
			
			
			window.onbeforeunload=function(){
				if(<s:property value="coursePage.getcredit"/>!=1){
					if(_cpst.passtime2>=<s:property value="myCPage.cpage.during"/>*60){
						if(_cpst.passed2==0){
							alert("你还有考试没过！");
						}
					}
				}
				window.event.returnValue="确定退出本次学习？";
			}
			window.onunload=function(){
				_cpst.exitStudy(); 
			}
		</script>
	</HEAD>
	<body onLoad="myload('<s:property value="status"/>');">
		<div id="message" style="display: none;"></div>
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
		<table width="100%" height="36" border="0" cellpadding="0"
			cellspacing="0" background="images/bg1.gif">
			<tr>
				<td style="padding-left: 20px;" width="20%" align="left"
					class="STYLE6">
					广东公安远程教育平台
				</td>
				<td width="50%" align="center" style="font-size: 12px;">
					<p>
					</p>
				</td>
				<td style="padding-right: 20px;font-size: 12px;" align="right">
					欢迎学员
					<s:property value="#session.realname" />(<s:property value="#session.username" />)
				</td>
			</tr>
		</table>
		<table width="960" height="35" border="0" align="center"
			cellpadding="0" cellspacing="0" class="tabb2">
			<tr>
				<td class="STYLE6">
					课程名称：
					<s:property value="course.name" />
				</td>
				<td width="340" align="right" class="STYLE6">
					总进度：<span style="width:100px;height:14px;border: 1px dotted #FF6633;text-align:left;">
							<IMG height=14 id="cp_img" src="images/jd.gif" width="<s:property value="myCourse.process" />%">
						</span>
					<span id="cp_img_span"><s:property value="myCourse.process_" />%</span>
			  </td>
				<td width="130">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-size:12px;"><a href="displayStudyCpageInfo.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>" class="STYLE6">各章节学习详情</a></font></td>
			</tr>
		</table>
		<table width="960" height="25" border="0" align="center"
			cellpadding="0" cellspacing="0" class="tabb2">
			<tr>
				<td style="font-size: 12px;">
					当前章节：
					<s:property value="coursePage.title" />
				</td>
				<td width="200" align="right" style="font-size: 12px;">
					<!-- <a href="study.action">返回个人中心</a> -->
				</td>
			</tr>
		</table>
		<table style="margin-top: 8px;" width="960px" height="500px" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="620px" valign="middle" align="center" height="500px">
				<div style="height:490px;width: 610px;" id="page_file"></div>
				</td>
				<td valign="top">
					<table width="100%" height="30" border="0" cellpadding="0"
						cellspacing="0" background="images/bg1.gif" class="tab42">
						<tr>
							<td style="padding-left:15px;">
								课程目录
							</td>
						</tr>
					</table>
					<table width="100%" height="300px;" border="0" cellpadding="0"
						cellspacing="0" bgcolor="f3fcff" class="tabrlb">
						<tr>
							<td valign="top" height="300px;" class=tdpad>
								<div
									style="width: 100%;text-align: center;">
									
									<div
										style="overflow:hidden;width: 200px; height:20px;background: buttonface; text-align: left; margin:0px auto;"
										id="processDiv3">
									</div>
									<div id="processDiv4" style="color: blue;font-size:12px;"><span>结业方式：<s:property value="coursePage.getcreditName" /></span>
									</div>
									<!-- 
									<div id="timer" style="font-size: 12px;">
										已学时间：加载中..秒
									</div>
									 -->
									<div style="font-size: 12px;">
										<span id="timer2">已学时长：加载中..秒</span>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<span id="timer3">规定时长：加载中..秒</span>
									</div>
								</div>
								<div style="width:100%;height:215px;OVERFLOW-y:auto;">
									<div style="font-size:12px; text-align: center;">
										<img src="images/img/studied.gif" width="15" height="13" />
										已完成
										<img src="images/img/studying.gif" width="15" height="13" />
										学习中
										<img src="images/img/unstudied.gif" width="12" height="13" />
										未学习
									</div>
									<ul style="border: 1px black; display: block">
										<s:iterator value="myCPages" id="cp">
											<li style="overflow:hidden;height:20px;line-height:20px;font-size: 14px;">
												<s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
												<A onClick="return addSavetimeToUrl(this);"
													href="course_study.action?course.isLogout=1&coursePage.id=<s:property value="cpage.id" />&course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>">
													<s:property value="cpage.title" /></A>
												<s:if test="passed">
													<img src="images/img/studied.gif" width="15" height="13"/>
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
												<s:if test="myPracs[0].ppaper.id!=0">
										  <li style="font-size: 14px;">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														【练习】<a href="practice_paper.action?course.id=<s:property value="course.id"/>&course.classid=<s:property value="course.classid"/>&coursePage.id=<s:property value="cpage.id"/>&examPaper.id=<s:property value="examPaper.id"/>&myPractice.ppaper.id=<s:property value="myPracs[0].ppaper.id"/>"><s:property value="myPracs[0].ppaper.title"/></a>
										  </li>
												</s:if>
											</LI>
											<%-- 
											<s:iterator value="myPracs" id="pp">
												<LI  style="overflow:hidden;height:20px;line-height:20px;font-size: 14px;">
													<i><s:if test="cpage.property==1">&nbsp;&nbsp;&nbsp;&nbsp;</s:if>
														&nbsp;&nbsp;&nbsp;&nbsp;[练习] <A target="_blank"
														href="practice_paper.action?course.id=<s:property value="#cp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
															<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> --> </A> <s:if
															test="lasttime==null">
															<img src="images/img/unstudied.gif" width="12"
																height="13" />
														</s:if> <s:else>
															<img src="images/img/studied.gif" width="12" height="13" />
														</s:else> </i>
												</LI>
											</s:iterator>
											 --%>
										</s:iterator>
										<%-- 
										<s:iterator value="course.myPracs" id="pp">
											<LI  style="overflow:hidden;height:20px;line-height:20px;font-size: 14px;">
												<i> [练习]<A target="_blank"
													href="practice_paper.action?course.id=<s:property value="#cp.course.id"/>&examPaper.id=<s:property value="ppaper.examPaper.id"/>&myPractice.ppaper.id=<s:property value="ppaper.id"/>">
														<s:property value="ppaper.examPaper.title" /> <!--<s:property
																					value="ppaper.skipable" /> --> </A> <s:if
														test="lasttime==null">
														<img src="images/img/unstudied.gif" width="12" height="13" />
													</s:if> <s:else>
														<img src="images/img/studied.gif" width="12" height="13" />
													</s:else> </i>
											</li>
										</s:iterator>
										 --%>
									</ul>
								</div>
							</td>
						</tr>
					</table>
					
					<table width="100%" height="30" border="0" cellpadding="0"
						cellspacing="0" background="images/bg1.gif" class="tab42">
						<tr>
							<td style="padding-left:15px;">
								课程简介
							</td>
						</tr>
					</table>
					<table width="100%" height="50" border="0" cellpadding="0"
						cellspacing="0" bgcolor="f3fcff" class="tabrlb">
						<tr>
							<td valign="top" class="tdpad">
								<div style="height:100px;OVERFLOW-y:auto;">${course.description }</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table style="margin-top:8px;" width="960" height="50" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
          <tr>
            <td height="102" align="center" background="elfrontimages/botbg.png" style="line-height:25px;font-size:12px;"><p class="foot">五矿发展员工职业发展系统 copyright 2011-2015 all rights reserved<br />
              地址：北京市海淀区三里河路5号B座。服务电话：010-56219458</p></td>
          </tr>
        </table>
	</body>
</html>
