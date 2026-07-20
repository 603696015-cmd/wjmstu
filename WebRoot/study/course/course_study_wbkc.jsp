<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="wysLib" uri="/WEB-INF/wysLib.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

   <!-- <!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN">  -->
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<base href="<%=basePath%>" />
		<TITLE>学习课程（外部）--<s:property value="course.name" />
		</TITLE>
		<LINK href="css/study_wbkc.css" type=text/css rel=stylesheet />
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/CourseStudy.js"></script>
		<script type="text/javascript" src="js/course.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script> 
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<style type="text/css">
.jiangyi {
	PADDING-RIGHT: 8px;
	PADDING-LEFT: 8px;
	FONT-SIZE: 12px;
	PADDING-BOTTOM: 8px;
	PADDING-TOP: 8px;
	BACKGROUND-COLOR: #ffffff
}

.STYLE5 {
	COLOR: #ff0000
}

#menubox {
	BORDER-RIGHT: #26517b 0px solid;
	BORDER-TOP: #26517b 0px solid;
	BACKGROUND: #ffffff;
	MARGIN: 0px;
	BORDER-LEFT: #26517b 0px solid;
	WIDTH: 180px;
	BORDER-BOTTOM: #26517b 0px solid;
	HEIGHT: auto
}

BODY {
	MARGIN: 0px
}

.STYLE8 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 14px
}

A {
	FONT-SIZE: 12px
}

.STYLE12 {
	FONT-SIZE: 12px;
	COLOR: #ffffff
}
</style>
		<script type="text/javascript">
			var _cpst;
			var needsetCp = false;
			function div_ifr(){
			 	_cpst=new CourseStudy(<s:property value="course.classid"/>,<s:property value="course.id"/>, 0,
					 <s:property value="myCourse.passtime"/>,
					 <s:property value="course.during"/>*60,
					 <s:property value="course.querytime"/>,
					 <s:property value="myCourse.passtime2"/>,
					 <s:property value="studyCourseRecordId"/>);
				_cpst.durtimediv="timer3";
				_cpst.realtimediv="timer2";
				_cpst.studyinfo_time=<s:property value="#session.studyinfo_time"/>;
				_cpst.processdiv="processDiv3";
				_cpst.init();
			 var from = 0;
			 if(!<s:property value="myCourse.passed"/>) 
			 	 from = <s:property value="myCourse.passtime"/>;
			 var _cvideo = new CourseVideo(2,"<s:property value="course.exurl_"/>", from);
			 if(!_cvideo.show("ifr")){
			 	 var qas = "<iframe width='100%' height='100%' ";
				 var asd = " style='margin: 0px;' ";
				 var aqw = "marginheight='0' marginwidth='0' ";
				 var awq = "frameborder='0' id='course_content' src="; 
				 var gsd = "'> </iframe>";
				 document.getElementById("ifr").innerHTML = qas+asd+aqw+awq+" '<s:property value="course.exurl_"/>"+gsd;
			 }
			}
			
			function hiddenCat(){
				var cat1=document.getElementById("cat");
				if(cat1.style.display=="block"){
					cat1.style.display="none";
				}
			}
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
			window.onbeforeunload=function(){
				window.event.returnValue="确定退出本次学习？";
			}
			window.onunload=function(){
				_cpst.exitStudy(); 
			} 
		</script>
	</HEAD>
	<body onload="div_ifr();" style="overflow: visible;height:100%; padding: 0px; margin: 0px">
		<div style="position:absolute; margin:2px;">
				<div style=" width: 200px; height: 15px; margin: 0px auto; background: buttonface; text-align: left; float: left;"
						id="processDiv3"> 
				</div>
				<div id="processDiv4"  
					style="margin-top:0px;margin-left:20px;font-size: 12px; height: 20px; color: blue;">
				</div>
				<div style="font-size: 12px;">
					<span id="timer2">实际时长：加载中..秒</span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="timer3">规定时长：加载中..秒</span>
				</div>
		</div>
			<div
			style="border: solid 1px buttonface; z-index: 1000; position: absolute; background: #ffffff; width: 300px; height: 300px; overflow: auto; display: none"
			id="cat">
			<div style="width: 100%; height: 20px;border-bottom: solid 1px buttonface;">
				<a href="#" style="float: right;" onclick='hiddenCat();return false;'>关闭</a>
			</div>
			
			<div
				style="width: 100%; height: 60px; text-align: center; margin-top: 10px;">
				<div
					style="width: 180px; height: 20px; margin: 0px auto; background: buttonface; text-align: left;"
					id="processDiv">
				</div>
				<div id="processDiv1"
					style="font-size: 12px; height: 20px; color: blue;">
				</div>
				<div id="timer" style="font-size: 12px; height: 20px;">
					已学时间：加载中..秒
				</div>
			</div>
			<div
				style="font-size: 12px; height: 20px; overflow: hidden; text-align: center;">
				<img src="images/img//studied.gif" width="15" height="13">
				<span>已完成</span>
				<img src="images/img/studying.gif" width="15" height="13">
				<span>学习中</span>
				<img src="images/img/unstudied.gif" width="12" height="13">
				<span>未学习</span>
			</div>
			<div>${course.description }</div>
			</div>
		<div
			style="position: absolute; border: solid 1px buttonface; width: 400; height: 300px; background: white; z-index: 300; display: none;"
			id="noteadd">
			<div style="width: 100%; background: #eeddaa">
				<span style="width: 380">做笔记</span><span
					style="cursor: hand; width: 15px;" onclick="closediv('noteadd')">X</span>
			</div>
			<div style="width: 100%; height: 100%" id="noteaddcontent"></div>
		</div>
		<div
			style="position: absolute; border: solid 1px buttonface; width: 600; height: 400px; background: white; z-index: 301; display: none;"
			id="notelist">
			<div style="width: 100%; background: #eeddaa">
				<span style="width: 580">查看笔记</span><span
					style="cursor: hand; width: 15px;" onclick="closediv('notelist')">X</span>
			</div>
			<div style="width: 100%; height: 100%" id="notelistcontent"></div>
		</div>
		<div id="message" style="display: none;"></div>
		<table height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tbody>
				<tr>
					<td vAlign=top height="48px">
						<table cellSpacing=0 cellPadding=0 width="100%" border=0>
							<tbody>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0 width="100%" border=0>
											<tbody>
												<tr>
													<td valign="bottom" align="center"
														background=images/img/bfz_r1_c11.jpg>
														<table height=42 cellSpacing=0 cellPadding=0 width="100%">
															<tbody>
																<tr>
																	<td width="250">&nbsp;</td>
																	<td class=bt valign="middle" align="center"  
																		style="font-weight: bolder; color: #ffffff; padding-top: 10px; line-height: 26px; font-family: '黑体'; color: #ffffff;">
																		 <s:property value="course.name" /> 
																	</td>
																	<td valign="middle" align="center" width=480>
																		<!-- 
																		<a class=dh target="_blank"
																			href="course_tandsp.action?course.id=${course.id }&course_sourse=0">讲师简介</a><span
																			class="STYLE12"> &gt;&gt;</SPAN>
																		<a class=dh href="#" style="cursor: hand"
																			onclick="javascript:showCat(this);return false;">进度</a><span
																			class="STYLE12"> &gt;&gt;</SPAN>
																		<a class=dh target="_blank"
																			href="course_tandsp.action?course.id=${course.id }&course_sourse=1">教学计划</a><span
																			class="STYLE12"> &gt;&gt;</SPAN>
																		<a class=dh target="_blank"
																			href="practice_listInit.action?course.id=${course.id }">练习中心</a><span
																			class="STYLE12"> &gt;&gt;</SPAN>
																		<a class=dh style="cursor: hand"
																			onclick="window.open('course_study_noteAddInit.action?course.id=${course.id }','')">做笔记</a><span
																			class="STYLE12"> &gt;&gt; </span><a class="dh"
																			style="cursor: hand"
																			onclick="window.open('course_study_notelist.action?course.id=${course.id }','')">查看笔记</A>
																		<span class="STYLE12">&gt;&gt;</span>
																		<A class=dh href="study.action">学习任务</A>
																		 -->
																	</td>
																</tr>
															</tbody>
														</table>
													</td>
												</tr>
												<tr>
													<td background=images/img/t-5.jpg height=13><img height=13 src="images/img/t-52.jpg" width=180/></td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td height="100%" style="padding: 0px;">
					<div id="ifr" style="width:100%;height:100%;"> 
					</div> 
					</td>
				</tr>
			</tbody>
		</table>
	</BODY>
</HTML>
